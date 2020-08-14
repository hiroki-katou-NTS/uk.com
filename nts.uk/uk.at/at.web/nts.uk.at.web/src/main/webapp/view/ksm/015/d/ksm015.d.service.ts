module nts.uk.at.view.ksm015.d {
	export module service {
		let rootPath = "bs/schedule/employeeinfo/workplacegroup";
		var paths = {
			exportExcel: "com/employee/workplace/group/workplaceReport",
			getWorkplaceInfo: rootPath + "/getWorkplaceInfo",
			getWorkplaceGroup: rootPath + "/getWorkplaceGroup",
			getListWorkplaceId: rootPath + "/getListWorkplaceId",
			registerWorkplaceGroup: rootPath + "/registerWorkplaceGroup",
			updateWorkplaceGroup: rootPath + "/updateWorkplaceGroup",
			deleteWorkplaceGroup: rootPath + "/deleteWorkplaceGroup",

			register: 'ctx/at/shared/workrule/shiftmaster/register/shiftmaster/org',
			startPage: 'ctx/at/shared/workrule/shiftmaster/startDPage',
			copy: 'ctx/at/shared/workrule/shiftmaster/copy/shiftmaster/org',
			delete: 'ctx/at/shared/workrule/shiftmaster/delete/org',
			getAlreadySettingWplGr: 'ctx/at/shared/workrule/shiftmaster/getAlreadySettingWplGr',
			getShiftMasterWplGroup: 'ctx/at/shared/workrule/shiftmaster/getShiftMasterWplGroup',
			getShiftMasterByWplGroup: 'ctx/at/shared/workrule/shiftmaster/getShiftMasterByWplGroup'
		}

		export function startPages(unit: number): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.startPage + '/' + unit);
		}

		export function exportExcel(): JQueryPromise<any> {
			return nts.uk.request.exportFile("com", paths.exportExcel);
		}

		export function getWorkplaceInfo(data): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.getWorkplaceInfo, data);
		}

		export function getWorkplaceGroupInfo(wkpId): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.getWorkplaceGroup + "/" + wkpId);
		}

		export function getWorkplaceByGroup(wkpId): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.getListWorkplaceId + "/" + wkpId);
		}

		export function registerWorkplaceGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.registerWorkplaceGroup, data);
		}

		export function updateWorkplaceGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.updateWorkplaceGroup, data);
		}

		export function deleteWorkplaceGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("com", paths.deleteWorkplaceGroup, data);
		}

		export function getAlreadyConfigOrg(unit: number): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.getAlreadySettingWplGr + '/' + unit);
		}

		export function registerOrg(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.register, data);
		}

		export function copyOrg(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.copy, data);
		}

		export function deleteOrg(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.delete, data);

		}

		export function getShiftMasterWplGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.getShiftMasterWplGroup, data);
		}
		
		export function getShiftMasterByWplGroup(data): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.getShiftMasterByWplGroup, data);
		}
	}
}