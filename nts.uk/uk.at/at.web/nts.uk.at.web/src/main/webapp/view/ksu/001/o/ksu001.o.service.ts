 module ksu001.o.service {
    var paths: any = {
        getWorkType: "at/share/worktype/getByCIdAndDisplayAtr"
    }

    export function getWorkType(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkType);
    }
}