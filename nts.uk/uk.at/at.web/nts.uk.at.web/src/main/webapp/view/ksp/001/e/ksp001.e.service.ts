module nts.uk.at.view.ksp001.e.service {
    import format = nts.uk.text.format;

    let paths: any = {
        getTopPageDetail: "sys/portal/smartphonetoppage/getTopPageDetail/{0}",
        saveDataDetail: "sys/portal/smartphonetoppage/saveDataDetail"
    }

    export function getTopPageDetail(mode: number): JQueryPromise<any> {
        return nts.uk.request.ajax("com", format(paths.getTopPageDetail, mode));
    }
    
    export function saveDataDetail(params: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.saveDataDetail, params);
    }

}