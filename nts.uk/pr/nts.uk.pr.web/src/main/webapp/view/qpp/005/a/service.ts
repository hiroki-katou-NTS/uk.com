module nts.uk.pr.view.qpp._005 {
    export module service {
        var servicePath = {
            getPaymentData: "/screen/pr/qpp005/paymentdata/find"
        };

        export function getPaymentData(personId: string, employeeCode: string): JQueryPromise<model.PaymentDataResult> {
            var dfd = $.Deferred<model.PaymentDataResult>();
            var query = {
                personId: personId,
                employeeCode: employeeCode
            };
            nts.uk.request.ajax(servicePath.getPaymentData, query).done(function(res) {
                var paymentResult: model.PaymentDataResult = res as model.PaymentDataResult;
                dfd.resolve(paymentResult);
            }).fail(function(res) {
                alert('fail');
            });

            return dfd.promise();
        }


        /**
           * Model namespace.
        */
        export module model {
            export class PaymentDataResult {
                paymenHeader: PaymentDataHeaderModel;
                categories: Array<LayoutMasterCategoryModel>;
            }

            // header
            export class PaymentDataHeaderModel {
                dependentNumber: number;
                specificationCode: string;
                specificationName: string;
                makeMethodFlag: number;
                employeeCode: string;
                comment: string;
            }

            // categories
            export class LayoutMasterCategoryModel {
                categoryAttribute: number;
                categoryPosition: number;
                details: Array<DetailItemModel>;
            }

            // item
            export class DetailItemModel {
                categoryAtr: number;
                itemCode: string;
                itemNme: string;
                value: number;
                itemPosition: DetailItemPositionModel;
                isCreated: boolean;
            }

            export class DetailItemPositionModel {
                linePosition: number;
                columnPosition: number;
            }
        }
    }
}