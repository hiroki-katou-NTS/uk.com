module nts.uk.at.view.kaf018.h {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getMail: "at/request/application/approvalstatus/getMail/{0}",
            getMailBySetting: "at/request/application/approvalstatus/getMailBySetting",
            registerMail: "at/request/application/approvalstatus/registerMail"
        }

        /**
         * アルゴリズム「承認状況メール本文取得」を実行する
         */
        export function getApprovalStatusMail(mailType: number): JQueryPromise<any> {
            let path = format(paths.getMail,mailType);
            return nts.uk.request.ajax("at", path);
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


        export function getIdentityProcessUseSet(): JQueryPromise<any> {
            let dfd = $.Deferred();
            var dataTemp =
                { useIdentityOfMonth: 1 };
            dfd.resolve(dataTemp);
            return dfd.promise();
        }

        export function getApprovalProcessingUseSetting(): JQueryPromise<any> {
            let dfd = $.Deferred();
            var dataTemp =
                { useMonthApproverComfirm: 0, useDayApproverConfirm: 1 };
            dfd.resolve(dataTemp);
            return dfd.promise();
        }
    }
}