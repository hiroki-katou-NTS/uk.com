module nts.uk.com.view.cmm022.a.service {

    var paths: any = {
        getListMaster: "bs/employee/group_common_master/get_master",
        
        getRelateMaster: "mandatoryRetirementRegulation/getRelateMaster",
        getMandatoryRetirementRegulation: "mandatoryRetirementRegulation/get",
        add: "mandatoryRetirementRegulation/add",
        update: "mandatoryRetirementRegulation/update"
        
    }

    export function getListMaster(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getListMaster);
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