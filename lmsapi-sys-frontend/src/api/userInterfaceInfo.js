import request from '@/utils/request'
const api_name = '/userInterfaceInfo'


export function pageUserInterfaceList(data) {
  return request({
    url: `${api_name}/page`,
    method: 'post',
    data
  })
}
export function pageCurrentUserInterfaceList(data) {
  return request({
    url: `${api_name}/page/user`,
    method: 'post',
    data
  })
}
export function changeUserInterface(id,status) {
  return request({
    url: `${api_name}/update/${id}/${status}`,
    method: 'post'
  })
}

export function deleteUserInterfaceInfos(list) {
  return request({
    url: `${api_name}/delete?ids=${list}`,
    method: 'post'
  })
}