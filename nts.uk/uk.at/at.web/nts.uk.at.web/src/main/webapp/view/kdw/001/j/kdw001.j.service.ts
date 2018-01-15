module nts.uk.at.view.kdw001.j {
    export module service {
        let servicePath = {
            getAllCaseSpecExeContent: 'at/record/case/getallcase',
            getCaseSpecExeContentById: 'at/record/case/getcasebyid/{0}'
        };

        export function getAllCaseSpecExeContent() {
            return nts.uk.request.ajax("at",servicePath.getAllCaseSpecExeContent);
        };
         export function getCaseSpecExeContentById(caseSpecExeContentID): JQueryPromise<any>  {
            return nts.uk.request.ajax(nts.uk.text.format(servicePath.getCaseSpecExeContentById ,caseSpecExeContentID));
        };
        
    }
}
