module nts.uk.at.view.kaf018_old.h {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getMailTemp: "at/request/application/approvalstatus/getMailTemp",
            registerMail: "at/request/application/approvalstatus/registerMail",
            confirmSenderMail: "at/request/application/approvalstatus/confirmSenderMail",
            sendTestMail: "at/request/application/approvalstatus/sendTestMail/{0}",
            getUseSetting: "at/record/application/realitystatus/getUseSetting"
        }

        /**
         * アルゴリズム「承認状況本文起動」を実行する
         */
        export function getMailTemp(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getMailTemp);
        }

        /**
         * アルゴリズム「承認状況メール本文登録」を実行する
         */
        export function registerApprovalStatusMail(listMail: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.registerMail, listMail);
        }

        /**
         * アルゴリズム「承認状況メールテスト送信実行」を実行する
         */
        export function sendTestMail(mailType: number): JQueryPromise<any> {
            let path = format(paths.sendTestMail, mailType);
            return nts.uk.request.ajax("at", path);
        }

        /**
         * アルゴリズム「承認状況社員メールアドレス取得」を実行する
         */
        export function confirmSenderMail(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.confirmSenderMail);
        }

        export function getUseSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getUseSetting);
        }
    }
}