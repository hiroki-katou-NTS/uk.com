module cmm008.a.service{
    var path = {
        getAllEmployment: "basic/organization/employment/findallemployments",
        createEmployment: "basic/organization/employment/createemployment",
        updateEmployment: "basic/organization/employment/updateemployment",
        deleteEmployment: "basic/organization/employment/deleteemployment/" ,
        getEmploymentByCode: "basic/organization/employment/findemploymentbycode/",
        getAllProcessingNo: "pr/core/paydayrocessing/getbyccd",
        getCompanyInfor: "ctx/proto/company/findBycompanyCode"
    }
    //find all employment data
    export function getAllEmployments(): JQueryPromise<Array<model.employmentDto>>{
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.getAllEmployment)
            .done(function(res: Array<any>){
                dfd.resolve(res);             
            })
            .fail(function(res: any){
                dfd.reject(res);    
            })
        return dfd.promise();
    }
    
    export function getEmploymentByCode(employmentCode: string): JQueryPromise<model.employmentDto>{
        var dfd = $.Deferred<model.employmentDto>();
        nts.uk.request.ajax("com",path.getEmploymentByCode + employmentCode)
            .done(function(res: model.employmentDto){
                dfd.resolve(res);             
            })
            .fail(function(res: any){
                dfd.reject(res);    
            })
        return dfd.promise();
    }
    //create new employment data
    export function createEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax("com", path.createEmployment, employment).done(function(res: Array<any>){
            dfd.resolve(res);        
        }).fail(function(res: any){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //update employment data
    export function updateEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.updateEmployment,employment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res: any){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //delete employment data
    export function deleteEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.deleteEmployment, employment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res : any){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //get all 処理日区分
     export function getProcessingNo(){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax('pr',path.getAllProcessingNo).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res : any){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //get 就業権限 by company
    export function getCompanyInfor(){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax('pr',path.getCompanyInfor).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res : any){
            dfd.reject(res);
        })
        return dfd.promise(); 
    }
    
    
    export module model{
        export class employmentDto{
            employmentCode: string;
            employmentName: string;
            memo: string;
            closeDateNo: number;
            processingNo: number;
            statutoryHolidayAtr: number;
            employementOutCd: string;
            displayFlg: number;   
            displayStr: string; 
            closeDateNoStr: string;
            processingStr: string
        }    
    }
}