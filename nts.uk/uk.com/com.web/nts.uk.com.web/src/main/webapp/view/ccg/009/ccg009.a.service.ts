module nts.uk.com.view.ccg009.a.service {

    var paths: any = {
        find: "sys/share/toppagealarm/find",
        updateWorkType: "sys/share/toppagealarm/add",
    }

    export function find() {
        return nts.uk.request.ajax(paths.find);
    }

    export function update(command: Array<viewmodel.TopPageAlarmSetDto>): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.updateWorkType, command);
    };

}