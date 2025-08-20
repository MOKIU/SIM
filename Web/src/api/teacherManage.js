import request from '@/utils/request'

export default {
  getTeacherList(searchModel) {
    return request({
      url: '/admin/teacher/list',
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
  addTeacher(teacher) {
    return request({
      url: '/admin/teacher',
      method: 'post',
      data: teacher
    })
  },
  getTeacherById(id) {
    return request({
      url: `/admin/teacher/${id}`,
      method: 'get',
    })
  },
  deleteTeacherById(id) {
    return request({
      url: `/admin/teacher/${id}`,
      method: 'delete',
    })
  },
  updateTeacher(teacher) {
    return request({
      url: '/admin/teacher',
      method: 'put',
      data: teacher
    })
  },
  saveTeacher(teacher) {
    if (teacher.id == null && teacher.id == undefined){ // 新增请求
      return this.addTeacher(teacher)
    }else
      return this.updateTeacher(teacher)
  }
}