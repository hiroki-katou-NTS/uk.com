module nts.uk.com.view.jmm017.c.service {
    var paths: any = {
        updateGuideMsg: "guidance/updateGuideMsg",
        getGuidance: "guidance/getGuidance"
    }
    export function updateGuideMsg(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.updateGuideMsg, param);
    }
    export function getGuidance(): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.getGuidance);
    }
}