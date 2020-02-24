module nts.uk.at.view.ksm015.b.service {
    /**
     *  Service paths
     */
    var paths: any = {
        startPage: 'ctx/at/shared/workrule/shiftmaster/startPage'
    }
    export function startPage(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startPage);
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
