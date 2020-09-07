module nts.uk.at.view.kdp010.i.service {
    let paths: any = {
        getData: "at/record/stamp/timestampinputsetting/smartphonepagelayoutsettings/get",
        save: "at/record/stamp/timestampinputsetting/smartphonepagelayoutsettings/save",
        del: "at/record/stamp/timestampinputsetting/smartphonepagelayoutsettings/del"
    }

    export function save(param: any): void {
        return nts.uk.request.ajax("at", paths.save, param);
    }

    export function getData(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getData, param);
    }
    
    export function del(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.del);
    }
}
