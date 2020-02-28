module nts.uk.com.view.jmm018.tabb.service {

    var paths: any = {
        getLatestHistId: "employmentRegulationHistory/getLatestHistId",
        getRelateMaster: "mandatoryRetirementRegulation/getRelateMaster",
        getMandatoryRetirementRegulation: "mandatoryRetirementRegulation/get",
        add: "mandatoryRetirementRegulation/add",
        update: "mandatoryRetirementRegulation/update"
        
    }

    export function getLatestHistId(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getLatestHistId);
    }
    export function getRelateMaster(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getRelateMaster);
    }
    export function getMandatoryRetirementRegulation(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getMandatoryRetirementRegulation, param);
    }
    export function add(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.add, param);
    }
    export function update(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.update, param);
    }
}