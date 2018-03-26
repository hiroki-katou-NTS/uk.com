module nts.uk.at.view.kaf018.h.service {
    var paths: any = {

    }

    /**
     * アルゴリズム「承認状況メール本文取得」を実行する
     */
    export function getApprovalStatusMail(mailType: number): JQueryPromise<any> {
        let dfd = $.Deferred();
        var dataTemp = [
            { mailSubject: "subject 1", mailContent: "content 1", urlApprovalEmbed: 1, urlDayEmbed: 0, urlMonthEmbed: 0, mailType: 0 },
            //{ mailSubject: "subject 2", mailContent: "content 2", urlApprovalEmbed: 0, urlDayEmbed: 1, urlMonthEmbed: 0, mailType: 1 },
            { mailSubject: "subject 3", mailContent: "content 3", urlApprovalEmbed: 0, urlDayEmbed: 0, urlMonthEmbed: 0, mailType: 2 },
            { mailSubject: "subject 4", mailContent: "content 4", urlApprovalEmbed: 0, urlDayEmbed: 0, urlMonthEmbed: 1, mailType: 3 }
        ];
        var mail = _.filter(dataTemp, function(item) { return item.mailType == mailType })
        dfd.resolve(mail);
        return dfd.promise();
    }

    /**
     * アルゴリズム「承認状況メール本文登録」を実行する
     */
    export function registerApprovalStatusMail(obj: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", "", obj);
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
            { useMonthApproverComfirm: 1, useDayApproverConfirm: 1 };
        dfd.resolve(dataTemp);
        return dfd.promise();
    }
}