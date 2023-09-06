import request from '@/utils/request'
const api_name = '/interfaceInfo'

export function pageInterfaceList(data) {
  return request({
    url: `${api_name}/page`,
    method: 'post',
    data
  })
}
export function updateInterfaceInfo(data) {
  return request({
    url: `${api_name}/update`,
    method: 'post',
    data
  })
}
export function saveInterfaceInfo(data) {
  return request({
    url: `${api_name}/save`,
    method: 'post',
    data
  })
}
export function deleteInterfaceInfos(list) {
  return request({
    url: `${api_name}/delete?ids=${list}`,
    method: 'post'
  })
}
export function getInterfaceInfo(id) {
  return request({
    url: `${api_name}/${id}`,
    method: 'get'
  })
}
export function changeInterface(id,status) {
  return request({
    url: `${api_name}/changeStatus/${id}/${status}`,
    method: 'post'
  })
}
