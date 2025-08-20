<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-input
        v-model="searchModel.name"
        placeholder="学生姓名"
        clearable
      ></el-input>
      <el-input
        v-model="searchModel.academy"
        placeholder="学院"
        clearable
      ></el-input>
      <el-button
        @click="getStudentList"
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
      <el-table :data="studentList" border style="width: 100%">
        <el-table-column label="序号" width="80">
          <template slot-scope="scope">
            <!--作用域插槽，可通过scope在插槽内部访问父组件的数据和方法-->
            <!-- 序号 = (页数 - 1) *(每页数) + 当前索引 + 1 -->
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="id" label="学号" width="180"> </el-table-column>
        <el-table-column prop="name" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="180"> </el-table-column>
        <el-table-column prop="birthday" label="出生日期" width="180">
        </el-table-column>
        <el-table-column prop="academy" label="学院"> </el-table-column>
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
              @click="deleteStudent(scope.row)"
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

    <!-- 学生信息信息编辑对话框 -->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="studentForm" :rules="rules" ref="studentFormRef">
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input v-model="studentForm.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
          <el-input v-model="studentForm.sex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="出生日期"
          prop="birthday"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="studentForm.birthday"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="academy" :label-width="formLabelWidth">
          <el-input v-model="studentForm.academy" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import studentApi from "@/api/studentManage";
export default {
  data() {
    return {
      formLabelWidth: "130px",
      studentForm: {
        id: "",
        name: "",
        sex: "",
        birthday: "",
        academy: "",
        flag: true,
      },
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
        // id:"",
        // name:"",
        // acaedmy:"",
        // sex:""
      },
      studentList: [],
      rules: {
        sex: [
          { required: true, message: "请输入性别", trigger: "blur" }, // blur：离开引发
          { min: 1, max: 1, message: "长度只能为1", trigger: "blur" },
        ],
        birthday: [
          { required: true, message: "请输入出生日期", trigger: "blur" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
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
    deleteStudent(student) {
      this.$confirm(`您确认删除该学生 ${student.name} 吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          studentApi.deleteStudentById(student.id).then((response) => {
            this.$message({
              type: "success",
              message: response.message,
            });
            this.getStudentList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveStudent() {
      // 触发表单验证
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          if (this.studentForm.flag)
            studentApi.addStudent(this.studentForm).then((response) => {
              // 成功提示
              this.$message({
                message: response.message,
                type: "success",
              });
              // 关闭对话框
              this.dialogFormVisible = false;
              // 刷新表格
              this.getStudentList();
            });
          else {
            studentApi.updateStudent(this.studentForm).then((response) => {
              this.$message({
                message: response.message,
                type: "success",
              });
              this.dialogFormVisible = false;
              this.getStudentList();
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
      this.studentForm.name = "";
      // his.studentForm.password = 123456
      this.studentForm.sex = "";
      this.studentForm.birthday = "";
      this.studentForm.academy = "";
      this.studentForm.id = "";
      this.$refs.studentFormRef.clearValidate(); // 移除表单项的校验结果
    },
    // 加号对话框方法
    open(id) {
      if (id == null) {
        this.title = "新增用户";
        this.studentForm.flag = true;
      } else {
        this.studentForm.flag = false;
        this.title = "修改用户";
        studentApi.getStudentById(id).then((response) => {
          this.studentForm = response.data;
        });
      }
      this.dialogFormVisible = true;
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo;
      this.getStudentList();
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize;
      this.getStudentList();
    },
    getStudentList() {
      studentApi.getStudentList(this.searchModel).then((response) => {
        this.studentList = response.data.rows;
        this.total = response.data.total;
      });
    },
  },
  created() {
    this.getStudentList();
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