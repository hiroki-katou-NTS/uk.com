module cmm009.a.service {
    var paths = {
        getCodeOfDepWP: "basic/organization/getcode",
        getAllDepartment: "basic/organization/getalldepartment",
        getAllDepartmentByHistId: "basic/organization/getalldepbyhistid/",
        getMemoByHistId: "basic/organization/getmemobyhistid/",
        getAllWorkPlace: "basic/organization/getallworkplace",
        getAllWorkPlaceByHistId: "basic/organization/getallwkpbyhistid/",
        getMemoWkpByHistId: "basic/organization/getmemowkpbyhistid/",
        addDepartment: "basic/organization/adddepartment",
        updateDepartment: "basic/organization/updatedepartment",
        updatelistDepartment: "basic/organization/updatedepartment",
        updateEndDate: "basic/organization/updateenddate",
        updateEndDateByHistId: "basic/organization/updateenddatebyhistoryid",
        deleteHistory: "basic/organization/deletehistory",
        updateStartDateandEndDate: "basic/organization/updatestartdateandenddate",
        deleteDep: "basic/organization/deletedep",
    }
    
    export function deleteDepartment(department: viewmodel.model.DepartmentDeleteDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.deleteDep, department).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


    export function upDateStartDateandEndDate(updateYMD: viewmodel.model.updateDateMY) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updateStartDateandEndDate, updateYMD).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


    export function deleteHistory(historyid: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.deleteHistory, historyid).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function updateEndDateByHistoryId(historyid: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updateEndDateByHistId, historyid).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function addDepartment(department: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.addDepartment, department).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function upDateDepartment(department: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updateDepartment, department).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function upDateEndDate(obj: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updateEndDate, obj).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function upDateListDepartment(listdepartment: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.updatelistDepartment, listdepartment).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function addListDepartment(listdepartment: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.addDepartment, listdepartment).done(
            function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getCodeOfDepWP(): JQueryPromise<viewmodel.model.GetCodeDto> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getCodeOfDepWP)
            .done(function(res: any) {
                dfd.resolve(res);
            }).fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
    * Get list dapartment tại listHistory[0].
    */
    export function getAllDepartment(): JQueryPromise<viewmodel.model.DepartmentQueryResult> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getAllDepartment)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


    /**
     * get list dapartment theo historyID
     */
    export function getAllDepartmentByHistId(historyId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.getAllDepartmentByHistId + historyId)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    /**
     * get Memo theo historyID
     */
    export function getMemoByHistId(historyId: string): JQueryPromise<viewmodel.model.MemoDto> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getMemoByHistId + historyId)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
   * Get list dapartment tại listHistory[0].
   */
    export function getAllWorkplace(): JQueryPromise<viewmodel.model.WorkPlaceQueryResult> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getAllWorkPlace)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
    * get list dapartment theo historyID
    */
    export function getAllWorkPlaceByHistId(historyId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.getAllWorkPlaceByHistId + historyId)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /*
    * get Memo theo historyID
    */
    export function getMemoWkpByHistId(historyId: string): JQueryPromise<viewmodel.model.MemoDto> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getMemoWkpByHistId + historyId)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res: any) {
                dfd.reject(res);
            })
        return dfd.promise();
    }





}