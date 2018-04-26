module nts.uk.at.view.kal001.c {
    export module service {
        var paths = {
            getEmployeeSendEmail : "at/function/alarm/kal/001/get/employee/sendEmail" 
        }
            
        export function getEmployeeSendEmail(query: any ) : JQueryPromise<Array<any>>{
            return nts.uk.request.ajax("at",paths.getEmployeeSendEmail, query);
        }

    
    }
}
