module nts.uk.pr.view.qpp021.h {
    export module service {
        var paths: any = {
            findAllEmployee: "basic/organization/employment/findallemployments"
        };

        //connection service update history 
        export function findAllEmployee(): JQueryPromise<model.EmploymentDto[]> {
            //call service server
            return nts.uk.request.ajax("com", paths.findAllEmployee);
        }
        export module model {

            export class EmploymentDto {
                /*会社コード  */
                companyCode: string;
                /*雇用コード  */
                employmentCode: string;
                /*雇用名称   */
                employmentName: string;
                /*締め日区分  */
                closeDateNo: number;
                /*処理日番号  */
                processingNo: number;
                /*公休管理区分     */
                statutoryHolidayAtr: number;
                /*外部コード  */
                employementOutCd: number;
                /*初期表示   */
                displayFlg: number;
                /*メモ     */
                memo: string;
            }

        }
    }
}