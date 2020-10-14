module test.service {
    var paths: any = {
        isMultipleManagement: "",
        startUp: "",
        getShiftMaster: "ctx/at/shared/workrule/shiftmaster/getlistByWorkPlace",
		getShiftMasterByWplGroup: 'ctx/at/shared/workrule/shiftmaster/getShiftMasterByWplGroup'
    }

    export function getShiftMaster(command): JQueryPromise<any> {
        return nts.uk.request.ajax( "at", paths.getShiftMaster, command);
    }
	
	export function getShiftMasterByWplGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.getShiftMasterByWplGroup, data);
		}
}