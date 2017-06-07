module cmm044.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths: any = {
        getLayoutName: "pr/core/allotsetting/findcompanyallotlayoutname/{0}",
    }

    export function getData() {

        return $.Deferred().resolve(true).promise();
    }

    export function saveData(models: Array<any>) {
        return $.Deferred().resolve(true).promise();
    }

    export function deleteData(models: Array<any>) {
        let dfd = $.Deferred();
        if (models.length > 0) {
            models.map((m) => {
                let data: any = {
                    payStmtCode: m.paymentDetailCode || '00',
                    bonusStmtCode: m.bonusDetailCode || '00',
                    startDate: m.startDate,
                    endDate: m.endDate,
                    historyId: m.historyId
                };

                ajax(paths.deleteAllotCompanySetting, data)
                    .done(d => dfd.resolve(d)).fail(m => dfd.reject(m));
            });
        }
        return dfd.promise();
    }

    // Get layout master name
    export function getAllotLayoutName(stmtCode: string) {
        let dfd = $.Deferred();
        if (stmtCode) {
            ajax(format(paths.getLayoutName, stmtCode), undefined, {
                dataType: 'text',
                contentType: 'text/plain'
            })
                .done(function(res: string) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                });
        }
        return dfd.promise();
    }
}