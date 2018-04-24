module nts.uk.at.view.kaf018.e {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getWorkplaceExperienceStartUp: "at/record/realitystatus/getStatusActivity",
            checkSendUnconfirmedMail: "at/record/realitystatus/checkSendUnconfirmedMail",
            exeSendUnconfirmedMail: "at/record/realitystatus/exeSendUnconfirmedMail"
        }

        /**
         * アルゴリズム「承認状況職場実績起動」を実行する
         */
        export function getStatusActivity(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getWorkplaceExperienceStartUp, obj);
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
    }
}