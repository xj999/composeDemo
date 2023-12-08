package com.midai.data

class BaseResponse<T> {
    /**
     * status : 200
     * flag : SUCCESS
     * message : 操作成功
     * action : receiveRemoteTaDataPublic
     * version : v2.0
     * format : json
     * datas : []
     */
    var status = 0
    var flag: String? = null
    var message: String? = null
    var action: String? = null
    var version: String? = null
    var format: String? = null
    var totalRecordCount = 0
    var totalPageCount = 0
    var pageIndex = 0
    var pageSize = 0
    var act: String? = null
    var datas: T? = null
        private set

    fun setDatas(datas: T) {
        this.datas = datas
    }

    override fun toString(): String {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", action='" + action + '\'' +
                ", version='" + version + '\'' +
                ", format='" + format + '\'' +
                ", datas=" + datas +
                '}'
    }
}