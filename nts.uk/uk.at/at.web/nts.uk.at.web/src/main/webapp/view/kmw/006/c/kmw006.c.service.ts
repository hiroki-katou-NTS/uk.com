module nts.uk.at.view.kmw006.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getLogInfor: "at/record/monthlyclosure/getLogInfors", 
        getListEmpId: "at/record/monthlyclosure/getListEmpIdByMonthlyClosureLogId/{0}/{1}"
    }

    export function getLogInfor(): JQueryPromise<any> {
        return ajax("at", paths.getLogInfor);
    }
    
    export function getListEmpId(monthlyClosureLogId: string, atr: number): JQueryPromise<any> {
        let _path = format(paths.getListEmpId, monthlyClosureLogId, atr);
        return ajax("at", _path);
    }

}