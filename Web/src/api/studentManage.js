import request from '@/utils/request'

export default {
  getStudentList(searchModel) {
    return request({
      url: '/admin/student/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        name:searchModel.name,
        id:searchModel.id,
        academy:searchModel.academy,
        sex:searchModel.sex
      }
    })
  },
  addStudent(student) {
    return request({
      url: '/admin/student',
      method: 'post',
      data: student
    })
  },
  getStudentById(id) {
    return request({
      url: `/admin/student/${id}`,
      method: 'get',
    })
  },
  deleteStudentById(id) {
    return request({
      url: `/admin/student/${id}`,
      method: 'delete',
    })
  },
  updateStudent(student) {
    return request({
      url: '/admin/student',
      method: 'put',
      data: student
    })
  },
  saveStudent(student) {
    if (student.id == null && student.id == undefined){ // 新增请求
      return this.addStudent(student)
    }else
      return this.updateStudent(student)
  }
}
