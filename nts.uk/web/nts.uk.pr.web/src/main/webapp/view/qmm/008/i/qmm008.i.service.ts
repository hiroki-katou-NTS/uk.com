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
        export function updatePensionAvgearn(list: model.ListPensionAvgearnDto, officeCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { listPensionAvgearnDto: list.listPensionAvgearnDto,
                historyId: list.historyId, 
                officeCode: officeCode };
            nts.uk.request.ajax(paths.updatePensionAvgearn, data).done(() =>
                dfd.resolve());
            return dfd.promise();
        }

        /**
        *  Find list PensionAvgearn by historyId
        */
        export function findPensionAvgearn(id: string): JQueryPromise<model.ListPensionAvgearnDto> {
            var dfd = $.Deferred<model.ListPensionAvgearnDto>();
            nts.uk.request.ajax(paths.findPensionAvgearn + '/' + id)
                .done(function(res: model.ListPensionAvgearnDto) {
                    dfd.resolve(res);
                })
                .fail(function(res: any) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {

            export interface PensionAvgearnDto {
                levelCode: number;
                companyFund: PensionAvgearnValue;
                companyFundExemption: PensionAvgearnValue;
                companyPension: PensionAvgearnValue;
                personalFund: PensionAvgearnValue;
                personalFundExemption: PensionAvgearnValue;
                personalPension: PensionAvgearnValue;
                childContributionAmount: number;
            }

            export interface ListPensionAvgearnDto {
                listPensionAvgearnDto: Array<PensionAvgearnDto>;
                historyId: string;
            }

            export interface PensionAvgearnValue {
                maleAmount: number;
                femaleAmount: number;
                unknownAmount: number;
            }
        }
    }

}
