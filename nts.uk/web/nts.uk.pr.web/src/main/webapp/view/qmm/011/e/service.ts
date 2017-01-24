module nts.uk.pr.view.qmm011.e {
    export module service {
        var paths: any = {
            updateInsuranceBusinessType: "pr/insurance/labor/businesstype/update"
        };
        export function updateInsuranceBusinessType(insuranceBusinessType: model.InsuranceBusinessTypeDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = { insuranceBusinessType: insuranceBusinessType, comanyCode: "CC0001" };
            nts.uk.request.ajax(paths.updateInsuranceBusinessType, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        export module model {

            export class InsuranceBusinessTypeDto {
                bizNameBiz1St: string;
                /** The biz name biz 2 nd. */
                bizNameBiz2Nd: string;
                /** The biz name biz 3 rd. */
                bizNameBiz3Rd: string;
                /** The biz name biz 4 th. */
                bizNameBiz4Th: string;
                /** The biz name biz 5 th. */
                bizNameBiz5Th: string;
                /** The biz name biz 6 th. */
                bizNameBiz6Th: string;
                /** The biz name biz 7 th. */
                bizNameBiz7Th: string;
                /** The biz name biz 8 th. */
                bizNameBiz8Th: string;
                /** The biz name biz 9 th. */
                bizNameBiz9Th: string;
                /** The biz name biz 10 th. */
                bizNameBiz10Th: string;
                constructor(bizNameBiz1St: string, bizNameBiz2Nd: string, bizNameBiz3Rd: string, bizNameBiz4Th: string, bizNameBiz5Th: string,
                    bizNameBiz6Th: string, bizNameBiz7Th: string, bizNameBiz8Th: string, bizNameBiz9Th: string, bizNameBiz10Th: string) {
                    this.bizNameBiz1St = bizNameBiz1St;
                    this.bizNameBiz2Nd = bizNameBiz2Nd;
                    this.bizNameBiz3Rd = bizNameBiz3Rd;
                    this.bizNameBiz4Th = bizNameBiz4Th;
                    this.bizNameBiz5Th = bizNameBiz5Th;
                    this.bizNameBiz6Th = bizNameBiz6Th;
                    this.bizNameBiz7Th = bizNameBiz7Th;
                    this.bizNameBiz8Th = bizNameBiz8Th;
                    this.bizNameBiz9Th = bizNameBiz9Th;
                    this.bizNameBiz10Th = bizNameBiz10Th;
                }
            }
        }
    }
}