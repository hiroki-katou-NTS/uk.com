module knr002.test.service {

    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import block = nts.uk.ui.block;
   

    let paths: any = {
        addNewEmployee: 'ctx/pereg/addemployee/addNewEmployee',
    };

    export function addNewEmployee(command: any): JQueryPromise<any> {
        return ajax('com', paths.addNewEmployee, command);
    }

    // export function addNewEmployee(command: any) {
    //     let dfd = $.Deferred<any>();
    //     let self = this;
    //     _.defer(() => block.grayout());
    //     nts.uk.request.ajax("com", paths.addNewEmployee, command)
    //         .done(function(res) {
    //             dfd.resolve(res);
    //         }).fail(function(res) {
    //             dfd.reject(res);
    //         }).always(() => {
    //             _.defer(() => block.clear());
    //         });
    //     return dfd.promise();
    // }
}