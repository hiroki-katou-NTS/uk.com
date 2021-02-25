module nts.uk.at.view.ksu001.s.sa {
    export module service {
        var paths: any = {
            getData: "ctx/at/schedule/setting/employee/sortsetting/startPage",
            save: "ctx/at/schedule/setting/employee/sortsetting/save"
        }

        export function getData1(workplaceID: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getData, workplaceID);
        }

        export function getData(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getData);
        }
        
        export function save(param): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.save, param);
        }
    }
}