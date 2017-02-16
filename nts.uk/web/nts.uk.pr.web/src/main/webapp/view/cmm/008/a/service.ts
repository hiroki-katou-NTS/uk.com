module cmm008.a.service{
    var path = {
        getAllEmployment: "pr/proto/basic/employment/findallemployments",
        createEmployment: "pr/proto/basic/employment/createemployment",
        updateEmployment: "pr/proto/basic/employment/updateemployment",
        deleteEmployment: "pr/proto/basic/employment/deleteemployment" 
    }
    
    export function getAllEmployments(): JQueryPromise<Array<model.employmentDto>>{
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(path.getAllEmployment)
            .done(function(res: Array<any>){
                    
            })
        return dfd.promise();
    }
    
    export module model{
        export class employmentDto{
            employmentCode: string;
            employmentName: string;
            closeDateNo: number;
            processingNo: number;
            statutoryHolidayAtr: number;
            employementOutCd: string;
            displayFlg: number;    
        }    
    }
}