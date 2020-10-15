module nts.uk.at.view.kaf018_old.b {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getAppSttByWorkpace: "at/request/application/approvalstatus/getAppSttByWorkpace",
            exeSendUnconfirmedMail: "at/request/application/approvalstatus/exeSendUnconfirmedMail",
            getCheckSendMail: "at/request/application/approvalstatus/getCheckSendMail"
        }

        /**
         * アルゴリズム「承認状況職場別起動」を実行する
         */
        export function getAppSttByWorkpace(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getAppSttByWorkpace, obj);
        }

        /**
         * アルゴリズム「承認状況未承認メール送信」を実行する
         */
        export function exeSendUnconfirmedMail(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.exeSendUnconfirmedMail, obj);
        }

        export function getCheckSendMail(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getCheckSendMail, obj);
        }
    }
}