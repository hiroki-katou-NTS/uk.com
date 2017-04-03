module qmm020.c.service {
        //duong dan   
        var paths = {
            getEmployAllotSettingHeaderList: "pr/core/allot/findallemployeeallotheader",
            getEmployAllotSettingDetailList: "pr/core/allot/findallemployeeallotdetail",
        }
        /**
         * Get list payment date processing.
         */
        export function getEmployeeAllotHeaderList(): JQueryPromise<Array<model.EmployeeAllotSettingHeaderDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getEmployAllotSettingHeaderList)
                .done(function(res: Array<any>){
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
         
        /**
         * Get employee list with payment doc, bunus doc
         */
        export function getEmployeeAllotDetailList(): JQueryPromise<Array<model.EmployeeAllotSettingDetailDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getEmployAllotSettingDetailList)
                .done(function(res: Array<any>){
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise(); 
        }
         export module model{
            export class EmployeeAllotSettingHeaderDto {
                    companyCode: string;
                    startYM: number;
                    endYM:number;
                    historyId: string;
                }
            
            export class EmployeeAllotSettingDetailDto {
                    companyCode: string;
                    historyId: string;
                    employeeCode : string;
                    paymentDetailCode : string;
                    bonusDetailCode : string;
                }
        }
}
