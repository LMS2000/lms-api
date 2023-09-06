import request from '@/utils/request'
const api_name = '/interfaceInfo'


export function pageOnlineInterfaceInfo(data){
  return request({
    url: `${api_name}/page/user`,
    method: 'post',
    data
  })
}



export function getInterfaceById(id){
  return request({
    url: `${api_name}/${id}`,
    method: 'get',
  })
}
export function invoketestRequest(data){
  return request({
    url: `${api_name}/invoke`,
    method: 'post',
    data
  })
}
