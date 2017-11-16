module nts.uk.at.view.kdw001.j {
    export module service {
        let servicePath = {
            getAllCaseSpecExeContent: 'at/record/case/getallcase'
        };

        export function getAllCaseSpecExeContent() {
            return nts.uk.request.ajax("at",servicePath.getAllCaseSpecExeContent);
        };
    }
}
