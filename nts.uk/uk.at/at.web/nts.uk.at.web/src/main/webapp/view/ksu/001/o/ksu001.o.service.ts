module ksu001.o.service {
    var paths: any = {
        getDataForComboBox: "screen/at/schedule/basicschedule"
    }

    export function getDataForComboBox(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getDataForComboBox);
    }
}