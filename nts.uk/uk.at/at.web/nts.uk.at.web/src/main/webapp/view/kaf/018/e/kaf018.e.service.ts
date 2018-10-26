module nts.uk.at.view.kaf018.e {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getStatusActivity: "at/record/application/realitystatus/getStatusActivity",
            checkSendUnconfirmedMail: "at/request/application/approvalstatus/checkSendUnConfirMail",
            exeSendUnconfirmedMail: "at/record/application/realitystatus/exeSendUnconfirmedMail",
            getUseSetting: "at/record/application/realitystatus/getUseSetting"
        }

        /**
         * アルゴリズム「承認状況職場実績起動」を実行する
         */
        export function getStatusActivity(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getStatusActivity, obj);
        }

        /**
        * アルゴリズム「承認状況未確認メール送信」を実行する
        */
        export function checkSendUnconfirmedMail(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.checkSendUnconfirmedMail, obj);
        }

        /**
         * アルゴリズム「承認状況未確認メール送信」を実行する
         */
        export function exeSendUnconfirmedMail(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.exeSendUnconfirmedMail, obj);
        }

        export function getUseSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getUseSetting);
        }
    }
}