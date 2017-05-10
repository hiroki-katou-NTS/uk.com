module qmm020.b.service {
    //duong dan   
    var paths: any = {
        getAllotCompanySettingList: "pr/core/allot/findallcompanyallot",
        getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}",
        getMaxDate: "pr/core/allot/findcompanyallotmaxdate",
        updateAllotCompanySetting: "pr/core/allot/update",
        insertAllotCompanySetting: "pr/core/allot/insert"
    }

    //Get list payment date processing.
    export function getAllotCompanyList() {
        let dfd = $.Deferred();
        nts.uk.request.ajax(paths.getAllotCompanySettingList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();
    }

    // Get layout master name
    export function getAllotLayoutName(stmtCode: string) {
        let dfd = $.Deferred();
        nts.uk.request.ajax(nts.uk.text.format(paths.getLayoutName, stmtCode), undefined, {
            dataType: 'text',
            contentType: 'text/plain'
        })
            .done(function(res: string) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();
    }


    // Get Data of Item with Max End date
    export function getAllotCompanyMaxDate() {
        let dfd = $.Deferred();
        nts.uk.request.ajax(paths.getMaxDate)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();
    }

    //Update ALLOT company  
    export function updateComAllot(model: any) {
        let dfd = $.Deferred(), command: any = {
            payStmtCode: model.payCode(),
            bonusStmtCode: model.bonusCode(),
            startDate: model.startYm(),
            endDate: model.endYm(),
            historyId: model.historyId()
        };

        nts.uk.request.ajax(paths.updateAllotCompanySetting, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });

        return dfd.promise();
    }


    export function insertComAllot(model: any) {
        let dfd = $.Deferred(), command: any = {
            historyId: model.historyId(),
            payStmtCode: model.payCode(),
            bonusStmtCode: model.bonusCode(),
            startDate: model.startYm(),
            endDate: model.endYm()
        };

        nts.uk.request.ajax(paths.insertAllotCompanySetting, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();
    }
}