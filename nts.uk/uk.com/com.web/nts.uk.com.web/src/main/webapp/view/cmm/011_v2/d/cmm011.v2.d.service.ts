module nts.uk.com.view.cmm011.v2.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var servicePath: any = {
        workplaceRegister: "",
        departmentRegister: ""
    };
    export function workplaceRegister(command: any) : JQueryPromise<any>{
        let _path = format(servicePath.workplaceRegister, command);
        return ajax("com", _path);
    }
    export function departmentRegister(command: any) : JQueryPromise<any>{
        let _path = format(servicePath.departmentRegister, command);
        return ajax("com", _path);
    }

}