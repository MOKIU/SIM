
// 用于定义Vuex store中的计算属性
// 可以在组件中轻松地获取对应的状态值，而无需直接访问Vuex store中的原始状态
// 计算属性可以根据store中的状态进行派生计算,并提供一个响应式的结果
// 派生计算:只要依赖的状态发生变化,派生计算的属性就会自动重新计算并更新它的值
const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  menuList: state => state.user.menuList,
  name: state => state.user.name,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,   
  permission_routes: state => state.permission.routes

}
export default getters
