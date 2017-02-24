module nts.uk.pr.view.qmm008.i {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            updatePensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/update",
            findPensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/find"
        };

        /**
         *  Save list pensionAvgearn
         */
        export function updatePensionAvgearn(listPensionAvgearn: model.PensionAvgearnDto, officeCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { listPensionAvgearn: listPensionAvgearn, officeCode: officeCode };
            nts.uk.request.ajax(paths.updatePensionAvgearn, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
        *  Find list PensionAvgearn by historyId
        */
        export function findPensionAvgearn(id: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findPensionAvgearn + '/' + id)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();;
        }

        /**
        * Model namespace.
        */
        export module model {

            export interface PensionAvgearnDto {
                historyId: string;
                levelCode: number;
                companyFund: PensionAvgearnValue;
                companyFundExemption: PensionAvgearnValue;
                companyPension: PensionAvgearnValue;
                personalFund: PensionAvgearnValue;
                personalFundExemption: PensionAvgearnValue;
                personalPension: PensionAvgearnValue;
                childContributionAmount: number;
            }

            export interface PensionAvgearnValue {
                maleAmount: number;
                femaleAmount: number;
                unknownAmount: number;
            }
        }
    }

}
