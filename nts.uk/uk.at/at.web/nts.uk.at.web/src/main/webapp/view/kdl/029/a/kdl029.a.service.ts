module nts.uk.at.view.kdl029.a.service {

    var paths: any = { 
       findAllEmploymentSystem: "at/request/application/employment/system",
       findByEmployee: "at/request/application/employment/getByEmployee"
    }
    export function findAllEmploymentSystem(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findAllEmploymentSystem, param);
    }
    export function findByEmployee(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.findByEmployee, param);
    }
}