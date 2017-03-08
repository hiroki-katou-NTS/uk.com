module cmm008.a.service{
    var path = {
        getAllEmployment: "basic/employment/findallemployments",
        createEmployment: "basic/employment/createemployment",
        updateEmployment: "basic/employment/updateemployment",
        deleteEmployment: "basic/employment/deleteemployment" 
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
    //create new employment data
    export function createEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax("com", path.createEmployment).done(function(res: Array<any>){
            dfd.resolve(res);        
        }).fail(function(res){
            dfd.resolve(res);
        })
        return dfd.promise();
    }
    //update employment data
    export function updateEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.updateEmployment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res){
            dfd.resolve(res);
        })
        return dfd.promise();
    }
    //delete employment data
    export function deleteEmployment(employment: model.employmentDto){
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",path.deleteEmployment).done(function(res: Array<any>){
            dfd.resolve(res);
        }).fail(function(res){
            dfd.resolve(res);
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