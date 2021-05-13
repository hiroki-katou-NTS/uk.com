module knr002.test.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import block = nts.uk.ui.block;
   

    let paths: any = {
        addNewEmployee: 'ctx/pereg/addemployee/addNewEmployee',       
    };

    export function addNewEmployee(command: any): JQueryPromise<any> {
        return ajax(paths.getTimeRecordReqSettings, command);
    }
}