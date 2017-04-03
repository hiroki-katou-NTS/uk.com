module qmm020.k.service {
    var paths:any = {
        delAllotCompanySetting: "pr/core/allot/delete"
    }
    //delete
    export function delComAllot(delAllotCompanyCmd: any) {
        var dfd = $.Deferred<Array<any>>();
        let command = {} as IAllotCompanyDto;
        
        command.payStmtCode = delAllotCompanyCmd.paymentDetailCode;
        command.bonusStmtCode = delAllotCompanyCmd.bonusDetailCode;
        command.startDate = delAllotCompanyCmd.startDate;
        command.endDate = delAllotCompanyCmd.endDate;
        command.historyId = delAllotCompanyCmd.historyId;
        debugger;
    
        nts.uk.request.ajax(paths.delAllotCompanySetting , command)
            .done(function(res: Array<any>) {
                debugger;
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })

        return dfd.promise();
    }
    
    interface IAllotCompanyDto {
        payStmtCode: string;
        bonusStmtCode: string;
        startDate: number;
        endDate: number;
        historyId: string;
    }
}