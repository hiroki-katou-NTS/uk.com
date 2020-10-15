module nts.uk.at.view.kal003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllData: "at/function/alarm/checkcondition/findAll/{0}",
        getOneData: "at/function/alarm/checkcondition/findOne/{0}/{1}",
        registerData: "at/function/alarm/checkcondition/register",
        deleteData: "at/function/alarm/checkcondition/delete",
        getAllFixedConData: "at/record/erroralarm/fixeddata/getallfixedcondata",
        getDailyErrorAlarmCheck: "at/function/alarm/checkcondition/findDailyErrorAlarmCheck",
        getClsNameByCodes: "bs/employee/classification/getClsNameByCds",
        getEmpNameByCodes: "bs/employee/employment/findNamesByCodesNew",
        // chua co
        getJobNamesByIds: "bs/employee/jobtitle/getNamesByIds",
        getBusTypeNamesByCodes: "at/record/worktypeselection/getNamesByCodesNew",
          //monthly
        getAllFixedExtraItemMon : "at/record/condition/monthlycheckcondition/getallfixitemmonthly",
        //mastercheck
        getAllFixedMasterCheckItem: "at/function/alarm/mastercheck/getallfixedmastercheckitem",

        //approval
        getAllFixedApprovalItem: "at/function/alarm/approval/findallfixedapprovalcheckitem",
        
        getName: "at/function/alarm/checkcondition/agree36/findName",

    }
        
    export function getName(): JQueryPromise<Array<any>>{
        return ajax("at", paths.getName);
    }

    export function getAllData(category: number): JQueryPromise<any> {
        let _path = format(paths.getAllData, category);
        return ajax("at", _path);
    };
    
    export function getOneData(category: number, code: string): JQueryPromise<any> {
        let _path = format(paths.getOneData, category, code);
        return ajax("at", _path);
    };

    export function registerData(data: any): JQueryPromise<any> {
        return ajax("at", paths.registerData, data);
    };

    export function deleteData(data: any): JQueryPromise<any> {
        return ajax("at", paths.deleteData, data);  
    }
    
    export function getDailyErrorAlarmCheck(): JQueryPromise<any> {
        return ajax("at", paths.getDailyErrorAlarmCheck);
    }
    /**
     * get All Fixed Condition WorkRecord data 
     */
    export function getAllFixedConData(): JQueryPromise<Array<any>>{
        return ajax("at", paths.getAllFixedConData);
    }

    export function getAllFixedMasterCheckItem(): JQueryPromise<Array<any>> {
        return ajax("at", paths.getAllFixedMasterCheckItem)
    }

    export function getAllFixedApprovalItem(): JQueryPromise<Array<any>> {
        return ajax("at", paths.getAllFixedApprovalItem)
    }
    
    export function getClsNameByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("com", paths.getClsNameByCodes, data);
    } 
    
    export function getEmpNameByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("com", paths.getEmpNameByCodes, data);
    } 
    
    export function getBusTypeNamesByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("at", paths.getBusTypeNamesByCodes, data);
    }
    
    export function getJobNamesByIds(data: Array<string>): JQueryPromise<any> {
        return ajax("com", paths.getJobNamesByIds, data);
    }

    
//    export function getAgreementHour(): JQueryPromise<any> {
//        return ajax("at", paths.getAgreementHour);
//    }

//    export function getAgreementError(): JQueryPromise<any> {
//        return ajax("at", paths.getAgreementError);
//    }
    //monthly
    export function getAllFixedExtraItemMon(): JQueryPromise<Array<any>>{
        return ajax("at", paths.getAllFixedExtraItemMon); 

    }
    
   
     export function saveAsExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1]!=null?program[1]:"";
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'AlarmCheckCondition', domainType: 'KAL003' + programName, 
                languageId: 'ja', reportType: 0});
        }
}