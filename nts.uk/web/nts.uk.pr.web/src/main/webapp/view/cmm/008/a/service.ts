module cmm008.a.service{
    var path = {
        getAllEmployment: "basic/organization/employment/findallemployments",
        createEmployment: "basic/organization/employment/createemployment",
        updateEmployment: "basic/organization/employment/updateemployment",
        deleteEmployment: "basic/organization/employment/deleteemployment/" ,
        getEmploymentByCode: "basic/organization/employment/findemploymentbycode/"
    }
    //find all employment data
    export function getAllEmployments(): JQueryPromise<Array<model.employmentDto>>{
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.getAllEmployment)
            .done(function(res: Array<any>){
                dfd.resolve(res);             
            })
            .fail(function(res){
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
            .fail(function(res){
                dfd.reject(res);    
            })
        return dfd.promise();
    }
    //create new employment data
    export function createEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax("com", path.createEmployment, employment).done(function(res: Array<any>){
            dfd.resolve(res);        
        }).fail(function(res){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //update employment data
    export function updateEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.updateEmployment,employment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res){
            dfd.reject(res);
        })
        return dfd.promise();
    }
    //delete employment data
    export function deleteEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.deleteEmployment, employment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res){
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
        }    
    }
}