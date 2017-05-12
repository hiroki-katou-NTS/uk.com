module qmm020.b.service {
    //duong dan   
    var paths: any = {
        getAllotCompanySettingList: "pr/core/allot/findallcompanyallot",
        getLayoutName: "pr/core/allot/findcompanyallotlayoutname/{0}",
        getMaxDate: "pr/core/allot/findcompanyallotmaxdate",
        updateAllotCompanySetting: "pr/core/allot/update",
        insertAllotCompanySetting: "pr/core/allot/insert",
        deleteAllotCompanySetting: "pr/core/allot/delete"
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

    export function saveData(models: Array<any>) {
        let dfd = $.Deferred();
        if (models.length > 0) {
            models.map((m) => {
                let data: any = {
                    payStmtCode: m.paymentDetailCode,
                    bonusStmtCode: m.bonusDetailCode,
                    startDate: m.startDate,
                    endDate: m.endDate,
                    historyId: m.historyId
                };
                if (data.historyId.contains('NEW')) {
                    nts.uk.request.ajax(paths.insertAllotCompanySetting, data);
                }
                else {
                    nts.uk.request.ajax(paths.updateAllotCompanySetting, data);
                }
            });
        }
        return dfd.promise();
    }

    export function deleteData(model: any) {
        let dfd = $.Deferred();
        if (!!model) {
            let data: any = {
                payStmtCode: model.paymentDetailCode,
                bonusStmtCode: model.bonusDetailCode,
                startDate: model.startDate,
                endDate: model.endDate,
                historyId: model.historyId
            };
            nts.uk.request.ajax(paths.deleteAllotCompanySetting, data).done((resp) => { dfd.resolve(resp); }).fail((mes) => { dfd.reject(mes); });
        }
        return dfd.promise();
    }
}