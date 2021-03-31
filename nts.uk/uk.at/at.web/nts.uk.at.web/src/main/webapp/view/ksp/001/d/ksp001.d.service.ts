module nts.uk.at.view.ksp001.d.service {
    let paths: any = {
        getTopPageSet: "sys/portal/smartphonetoppage/getTopPageSet",
        saveData: "sys/portal/smartphonetoppage/saveData"
    }

    export function getTopPageSet(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getTopPageSet);
    }

    export function saveData(params: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.saveData, params);
    }
}
