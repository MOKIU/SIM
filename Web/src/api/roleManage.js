import request from '@/utils/request'

export default {
  getRoleList(searchModel) {
    return request({
      url: '/role/list',
      method: 'get',
      params: {
        roleName: searchModel.roleName,
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
      }
    })
  },
  addRole(role) {
    return request({
      url: '/role',
      method: 'post',
      data: role
    })
  },
  getRoleById(id) {
    return request({
      url: `/role/${id}`,
      method: 'get',
    })
  },
  deleteRoleById(id) {
    return request({
      url: `/role/${id}`,
      method: 'delete',
    })
  },
  updateRole(role) {
    return request({
      url: '/role',
      method: 'put',
      data: role
    })
  },
  saveRole(role) {
    if (role.roleId == null && role.roleId == undefined){ // 新增请求
      return this.addRole(role)
    }else
      return this.updateRole(role)
  },
  getAllRoleList() {
    return request({
      url: '/role/all',
      method: 'get'
    })
  }
}
