module nts.uk.pr.view.qpp021.h {
    export module service {
        var paths: any = {
            findAllEmployee: "basic/organization/employment/findallemployments",
            findContactItemSettings: "ctx/pr/report/payment/contact/item/findSettings",
            saveContactItemSettings: "ctx/pr/report/payment/contact/item/save"

        };

        //connection get all employee
        export function findAllEmployee(): JQueryPromise<model.EmploymentDto[]> {
            //call service server
            return nts.uk.request.ajax("com", paths.findAllEmployee);
        }


        //connection get all employee
        export function findContactItemSettings(dto: model.ContactItemsSettingFindDto): JQueryPromise<model.ContactItemsSettingDto> {
            //call service server
            return nts.uk.request.ajax(paths.findContactItemSettings, dto);
        }

        //connection save server
        export function saveContactItemSettings(dto: model.ContactItemsSettingDto): JQueryPromise<void> {
            //call service server
            var data = { dto: dto };
            return nts.uk.request.ajax(paths.saveContactItemSettings, data);
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

            export class EmpCommentDto {
                /** The emp cd. */
                empCd: string;

                /** The employee name. */
                employeeName: string;

                /** The initial comment. */
                initialComment: string;

                /** The monthly comment. */
                monthlyComment: string;
            }

            export class ContactItemsSettingDto {

                /** The processing no. */
                processingNo: number;

                /** The processing ym. */
                processingYm: number;

                /** The comment initial cp. */
                initialCpComment: string;

                /** The comment month cp. */
                monthCpComment: string;

                /** The emp comment dtos. */
                empCommentDtos: EmpCommentDto[];

            }

            export class EmpCommentFindDto {

                /** The employee code. */
                employeeCode: string;

                /** The employee name. */
                employeeName: string;
            }

            export class ContactItemsSettingFindDto {

                /** The emp comment finds. */
                empCommentFinds: EmpCommentFindDto[];

                /** The processing no. */
                processingNo: number;

                /** The processing ym. */
                processingYm: number;
            }
        }
    }
}