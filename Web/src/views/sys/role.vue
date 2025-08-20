<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-input
        v-model="searchModel.roleName"
        placeholder="角色名称"
        clearable
      ></el-input>
      <el-button @click="getRoleList" type="primary" round icon="el-icon-search"
        >查询</el-button
      >
      <el-button
        @click="open(null)"
        type="primary"
        id="icon-plus"
        icon="el-icon-plus"
        circle
      ></el-button>
    </el-card>

    <!-- 结果列表 -->
    <el-card>
      <el-table :data="roleList" border style="width: 100%">
        <el-table-column label="序号" width="80">
          <template slot-scope="scope">
            <!--作用域插槽，可通过scope在插槽内部访问父组件的数据和方法-->
            <!-- 序号 = (页数 - 1) *(每页数) + 当前索引 + 1 -->
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="roleId" label="角色编号" width="180">
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" width="180">
        </el-table-column>
        <el-table-column prop="roleDesc" label="角色描述" width="180">
        </el-table-column>
        <el-table-column prop="" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
              @click="open(scope.row.roleId)"
              type="primary"
              icon="el-icon-edit"
              circle
              size="mini"
            ></el-button>
            <el-button
              @click="deleteRole(scope.row)"
              type="danger"
              icon="el-icon-delete"
              circle
              size="mini"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!---职位信息信息编辑对话框 -->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="roleForm" :rules="rules" ref="roleFormRef">
        <!-- <el-form-item prop="roleId" label="角色编号" :label-width="formLabelWidth">
                    <el-input v-model="roleForm.roleId" autocomplete="off"></el-input>
                </el-form-item> -->
        <el-form-item
          prop="roleName"
          label="角色名称"
          :label-width="formLabelWidth"
        >
          <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          prop="roleDesc"
          label="角色描述"
          :label-width="formLabelWidth"
        >
          <el-input v-model="roleForm.roleDesc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          prop="menuIdList"
          label="权限设置"
          :label-width="formLabelWidth"
        >
          <el-tree
            :data="menuList"
            :props="menuProps"
            show-checkbox
            default-expand-all
            node-key="menuId"
            style="width: 85%"
            ref="menuRef"
          ></el-tree>
          <!--复选框-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import roleApi from "@/api/roleManage";
import menuApi from "@/api/menuManage";
export default {
  data() {
    return {
      menuList: [],
      menuProps: {
        children: "children",
        label: "title",
      },
      formLabelWidth: "130px",
      roleForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      roleList: [],
      rules: {
        // roleId: [
        //     { required: true, message: '请输入角色编号', trigger: 'blur' }, // blur：离开引发
        //     { min: 1, max: 20, message: '长度在1到20个字符', trigger: 'blur' }
        // ],
        roleName: [
          { required: true, message: "请输入角色名称", trigger: "blur" }, // blur：离开引发
          { min: 3, max: 20, message: "长度在3到20个字符", trigger: "blur" },
        ],
        roleDesc: [
          { required: true, message: "请输入角色描述", trigger: "blur" }, // blur：离开引发
          { min: 3, max: 20, message: "长度在3到20个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 获取权限列表
    getAllMenu() {
      menuApi.getAllMenu().then((response) => {
        this.menuList = response.data;
      });
    },
    deleteRole(role) {
      this.$confirm(`您确认删除该角色 ${role.roleName} 吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          roleApi.deleteRoleById(role.roleId).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getRoleList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveRole() {
      // 触发表单验证
      this.$refs.roleFormRef.validate((valid) => {
        if (valid) {
          let checkedKeys = this.$refs.menuRef.getCheckedKeys(); // 返回被选中节点的key的数组
          let halfCheckedKeys = this.$refs.menuRef.getHalfCheckedKeys(); // 返回被半选中的key的数组
          this.roleForm.menuIdList = checkedKeys.concat(halfCheckedKeys); // 拼接两数组

          //提交请求给后台
          if (this.roleForm.flag)
            roleApi.addRole(this.roleForm).then((response) => {
              // 成功提示
              this.$message({
                message: response.message,
                type: "success",
              });
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新表格
              this.getRoleList();
            });
          else {
            roleApi.updateRole(this.roleForm).then((response) => {
              this.$message({
                message: response.message,
                type: "success",
              });
              this.dialogFormVisible = false;
              this.getRoleList();
            });
          }
        } else {
          console.log("error submit!");
          return false;
        }
      });
    },
    // 表单关闭数据清理
    clearFrom() {
      this.roleForm = {};
      this.$refs.roleFormRef.clearValidate(); // 移除表单项的校验结果
      this.$refs.menuRef.setCheckedKeys(response.data.menuIdList); // 将之前选中的键清楚，即再打开无之前选择的痕迹
    },
    // 加号对话框方法
    open(id) {
      if (id == null) {
        this.title = "新增角色";
        this.roleForm.flag = true;
      } else {
        this.roleForm.flag = false;
        this.title = "修改角色";
        roleApi.getRoleById(id).then((response) => {
          this.roleForm = response.data;
          this.$refs.menuRef.setCheckedKeys(response.data.menuIdList);
        });
      }
      this.dialogFormVisible = true;
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getRoleList();
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getRoleList();
    },
    getRoleList() {
      roleApi.getRoleList(this.searchModel).then((response) => {
        this.roleList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getRoleList();
    this.getAllMenu();
  },
};
</script>
<style>
#search .el-input {
  width: 250px;
  margin-right: 10px;
}

#icon-plus {
  margin-left: 290px;
}

.el-dialog .el-input {
  width: 90%;
}
</style>