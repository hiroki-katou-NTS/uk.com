module nts.uk.com.view.jmm018.tabb.service {

    var paths: any = {
        getLatestHistId: "employmentRegulationHistory/getLatestHistId",
        getRelateMaster: "mandatoryRetirementRegulation/getRelateMaster",
        
    }

    export function getLatestHistId(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getLatestHistId);
    }
    export function getRelateMaster(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getRelateMaster);
    }

}