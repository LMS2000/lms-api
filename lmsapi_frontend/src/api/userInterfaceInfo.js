import request from '@/utils/request'
const api_name = '/userInterfaceInfo'


export function pageCurrentUserInterfaceInfo(data) {
  return request({
    url: `${api_name}/page/user`,
    method: 'post',
    data
  })
}


export function renewalInterface(id) {
  return request({
    url: `${api_name}/renewal/${id}`,
    method: 'post'
  })
}


export function addUserInterface(id) {
  return request({
    url: `${api_name}/add/${id}`,
    method: 'post'
  })
}
