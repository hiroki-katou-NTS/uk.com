module nts.uk.at.view.kaf018.h {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getMailBySetting: "at/request/application/approvalstatus/getMailBySetting",
            registerMail: "at/request/application/approvalstatus/registerMail",
            confirmSenderMail: "at/request/application/approvalstatus/confirmSenderMail",
            sendTestMail: "at/request/application/approvalstatus/sendTestMail/{0}"
        }

        /**
         * アルゴリズム「承認状況本文起動」を実行する
         */
        export function getMailBySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getMailBySetting);
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
    }
}