module nts.uk.at.view.ksu001.s.sb {
    export module service {
        var paths: any = {
            getData: "at/schedule/employeeinfo/startPage"
           
        }

        export function getData(command): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getData,command);
        }
     
    }
}