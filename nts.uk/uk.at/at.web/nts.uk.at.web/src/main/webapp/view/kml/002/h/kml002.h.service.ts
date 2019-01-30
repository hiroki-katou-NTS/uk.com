module nts.uk.at.view.kml002.h.service {
    /**
     *  Service paths
     */
    var paths: any = {
        findByCid: "ctx/at/schedule/budget/fixedverticalsetting/findByCid",
        addFixedVertical: "ctx/at/schedule/budget/fixedverticalsetting/addFixedVertical",
        updateFixedVertical: "ctx/at/schedule/budget/fixedverticalsetting/updateFixedVertical"
    }
    
    export function findByCid(): JQueryPromise<Array<JQueryPromise<Array<viewmodel.ITotalTime>> >> {
        return nts.uk.request.ajax("at",paths.findByCid);
    }
    export function addFixedVertical(totaltimes: any) {
        return nts.uk.request.ajax("at", paths.addFixedVertical, totaltimes);
    }

    export function updateFixedVertical(totaltimes: any) {
        return nts.uk.request.ajax("at",paths.updateFixedVertical, totaltimes);
    }
    
    /**
    * saveAsExcel
    **/
    export function saveAsExcel(languageId: string): JQueryPromise<any> {
        let program= nts.uk.ui._viewModel.kiban.programName().split(" ");
        let domainType = "KML002";
        if (program.length > 1){
            program.shift();
            domainType = domainType + program.join(" ");
        }
        return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SettingScheVerticalScale", domainType: domainType,languageId: 'ja', reportType: 0}); 
    }
}
