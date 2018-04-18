module nts.uk.at.view.kaf018.b {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getAppSttByWorkpace: "at/request/application/approvalstatus/getAppSttByWorkpace",
            
        }

        /**
         * アルゴリズム「承認状況職場別起動」を実行する
         */
        export function getAppSttByWorkpace(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getAppSttByWorkpace, obj);
        }
    }
}