module nts.uk.at.view.kdw004.a.service {
    var paths: any = {
        startscreen: "at/record/workrecord/approvalmanagement/startscreen",
        extractApprovalStatusData: "at/record/workrecord/approvalmanagement/extractApprovalStatusData"
    }
    
    export function startscreen() {
        return nts.uk.request.ajax(paths.startscreen);
    }
    
    export function extractApprovalStatusData(param) {
        return nts.uk.request.ajax(paths.extractApprovalStatusData, param);
    }
}