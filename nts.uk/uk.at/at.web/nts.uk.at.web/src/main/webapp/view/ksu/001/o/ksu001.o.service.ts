 module ksu001.o.service {
    var paths: any = {
        getWorkType: "at/share/worktype/getByCIdAndDisplayAtr",
        getWorkTime: "at/shared/worktime/findByCIdAndDisplayAtr"
    }

    export function getWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkType);
    }
     
     export function getWorkTime(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkTime);
    }
}