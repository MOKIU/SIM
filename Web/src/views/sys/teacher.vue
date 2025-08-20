<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-input
        v-model="searchModel.name"
        placeholder="教师姓名"
        clearable
      ></el-input>
      <el-input
        v-model="searchModel.academy"
        placeholder="学院"
        clearable
      ></el-input>
      <el-button
        @click="getTeacherList"
        type="primary"
        round
        icon="el-icon-search"
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
      <el-table :data="teacherList" border style="width: 100%">
        <el-table-column label="序号" width="80">
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="id" label="工号" width="180"> </el-table-column>
        <el-table-column prop="name" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="180"> </el-table-column>
        <el-table-column prop="position" label="职位" width="180">
        </el-table-column>
        <el-table-column prop="academy" label="学院" width="180">
        </el-table-column>
        <el-table-column prop="" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
              @click="open(scope.row.id)"
              type="primary"
              icon="el-icon-edit"
              circle
              size="mini"
            ></el-button>
            <el-button
              @click="deleteTeacher(scope.row)"
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

    <!--v-if="teacherForm.id == null || teacherForm.id == undefined" -->
    <!-- 教师信息信息编辑对话框 -->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="teacherForm" :rules="rules" ref="teacherFormRef">
        <el-form-item label="工号" prop="id" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="登录密码"
          prop="password"
          v-if="teacherForm.flag == true"
          :label-width="formLabelWidth"
        >
          <!--如果teacherId为null或未被定义则表示当前是新增角色渲染该元素-->
          <el-input
            type="password"
            v-model="teacherForm.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
          <el-input v-model="teacherForm.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="postion" :label-width="formLabelWidth">
          <el-input
            v-model="teacherForm.position"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="academy" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.academy" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" :label-width="formLabelWidth">
          <!--复选框，可以设置最少选几个，最多选几个-->
          <!--设置Size，当一行显示不下时换行(尽快折行)-->
          <el-checkbox-group
            style="width: 85%"
            v-model="teacherForm.roleIdList"
            :max="2"
          >
            <el-checkbox
              v-for="role in roleList"
              :label="role.roleId"
              :key="role.roleId"
              >{{ role.roleDesc }}</el-checkbox
            >
            <!--label:交给后台的值-->
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveTeacher">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import teacherApi from "@/api/teacherManage";
import roleApi from "@/api/roleManage";
export default {
  data() {
    return {
      roleList: [],
      formLabelWidth: "130px",
      teacherForm: {
        id: "",
        password: "",
        name: "",
        sex: "",
        position: "",
        academy: "",
        roleIdList: [],
        flag: true,
      },
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      teacherList: [],
      rules: {
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "长度在 6 到 20 个字符",
            trigger: "blur",
          },
        ],
        sex: [
          { required: true, message: "请输入性别", trigger: "blur" }, // blur：离开引发
          { min: 1, max: 1, message: "长度只能为1", trigger: "blur" },
        ],
        position: [
          { required: true, message: "请输入职位", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "长度在 2 到 20 个字符",
            trigger: "blur",
          },
          // { validator: checkbirthday, trigger: 'blur' }
        ],
        academy: [
          { required: true, message: "请输入学院", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    getAllRoleList() {
      roleApi.getAllRoleList().then((response) => {
        this.roleList = response.data;
      });
    },
    deleteTeacher(teacher) {
      this.$confirm(`您确认删除该教师 ${teacher.name} 吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          teacherApi.deleteTeacherById(teacher.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getTeacherList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveTeacher() {
      // 触发表单验证
      this.$refs.teacherFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          if (this.teacherForm.flag)
            teacherApi.addTeacher(this.teacherForm).then((response) => {
              // 成功提示
              this.$message({
                message: response.message,
                type: "success",
              });
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新表格
              this.getTeacherList();
            });
          else {
            teacherApi.updateTeacher(this.teacherForm).then((response) => {
              this.$message({
                message: response.message,
                type: "success",
              });
              this.dialogFormVisible = false;
              this.getTeacherList();
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
      this.teacherForm.id = "";
      this.teacherForm.password = "";
      this.teacherForm.name = "";
      this.teacherForm.sex = "";
      this.teacherForm.position = "";
      this.teacherForm.academy = "";
      this.teacherForm.roleIdList = [];
      this.$refs.teacherFormRef.clearValidate(); // 移除表单项的校验结果
    },
    // 加号对话框方法
    open(id) {
      if (id == null) {
        this.title = "新增教师信息";
        this.teacherForm.flag = true;
      } else {
        this.teacherForm.flag = false;
        this.title = "修改教师信息";
        teacherApi.getTeacherById(id).then((response) => {
          this.teacherForm = response.data;
        });
      }
      this.dialogFormVisible = true;
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getTeacherList();
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getTeacherList();
    },
    getTeacherList() {
      teacherApi.getTeacherList(this.searchModel).then((response) => {
        this.teacherList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getTeacherList();
    this.getAllRoleList();
  },
};
</script>

<style>
#search .el-input {
  width: 250px;
  margin-right: 10px;
}

#icon-plus {
  margin-left: 450px;
}

.el-dialog .el-input {
  width: 90%;
}
</style>