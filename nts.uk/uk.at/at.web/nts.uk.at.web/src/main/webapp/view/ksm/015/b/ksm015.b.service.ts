module nts.uk.at.view.ksm015.b.service {
    /**
     *  Service paths
     */
    var paths: any = {
        startPage: 'ctx/at/shared/workrule/shiftmaster/startPage',
        registerShiftMaster: 'ctx/at/shared/workrule/shiftmaster/register',
        getShiftMaster: 'ctx/at/shared/workrule/shiftmaster/getlist',
        deleteShiftMaster: 'ctx/at/shared/workrule/shiftmaster/delete',
        getWorkInfo: 'ctx/at/shared/workrule/shiftmaster/workinfo/get',
		getWorkStyle: 'ctx/at/screen/workrule/shiftmaster/getWorkStyle'
    }
    export function startPage(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startPage);
    }

    export function register(data): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.registerShiftMaster, data);
    }

    export function deleteShiftMaster(data): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.deleteShiftMaster, data);
    }

    export function getlist(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getShiftMaster);
    }

    export function getWorkInfo(data): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkInfo, data);
    }

 	export function getWorkStyle(data): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getWorkStyle, data);
    }

    /**
    * saveAsExcel
    **/
    // export function saveAsExcel(languageId: string): JQueryPromise<any> {
    //     let program= nts.uk.ui._viewModel.kiban.programName().split(" ");
    //     let domainType = "KSM011";
    //     if (program.length > 1){
    //         program.shift();
    //         domainType = domainType + program.join(" ");
    //     }
    //     return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "ScheFuncControl", domainType: domainType,languageId: 'ja', reportType: 0});    
    // }
}
