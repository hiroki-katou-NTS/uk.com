/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

import characteristics = nts.uk.characteristics;
import IScheduleImport = nts.uk.at.view.kdl055.a.viewmodel.IScheduleImport;

module nts.uk.at.view.kdl055.b.viewmodel {

    @bean()
    export class KDL055BViewModel extends ko.ViewModel {
        overwrite: boolean = false;
        filename: KnockoutObservable<string> = ko.observable(null);
        gridOptions = { dataSource: [], columns: [], features: [], ntsControls: [] };

        created(params: any) {
            const vm = this;

            characteristics.restore('ScheduleImport').then((obj: IScheduleImport) => {
                if (obj) {
                    vm.overwrite = obj.overwrite;
                    vm.filename(obj.mappingFile);
                }
            });

            if (params) {
                vm.$blockui('show');
                vm.$ajax(paths.getCaptureData, { data: params, overwrite: vm.overwrite }).done((res: CaptureDataOutput) => {
                    if (res) {
                        console.log(res);
                        vm.convertToGrid(res);
                    }
                }).fail((err: any) => {
                    if (err) {
                        vm.$dialog.error({ messageId: err.messageId, messageParams: err.parameterIds });
                    }
                })
            }
        }

        mounted() {

        }

        register() {

        }

        openKDL053() {

        }

        close() {
            this.$window.close();
            
        }

        convertToGrid(data: CaptureDataOutput) {
            const vm = this;
        }
    }

    const paths = {
        getCaptureData: 'wpl/schedule/report/getCaptureData'
    }

    export interface CapturedRawDataDto {
        /** 取り込み内容 **/
        contents: CapturedRawDataOfCellDto[];

        /** 社員の並び順(OrderdList) **/
        employeeCodes: string[];
    }

    export interface CapturedRawDataOfCellDto {
        /** 社員コード **/
        employeeCode: string;
        /** 年月日 **/
        ymd: string;
        /** 取り込みコード **/
        importCode: string;
    }

    export interface CaptureDataOutput {
        // 社員リスト　：OrderedList<社員ID, 社員コード, ビジネスネーム>
        listPersonEmp: PersonEmpBasicInfoImport[];
        // 年月日リスト：OrderedList<年月日, 曜日>
        importableDates: string[];
        // 祝日リスト　：List<祝日>
        holidays: PublicHoliday[];
        // 取り込み結果
        importResult: ImportResult;
        // エラーリスト：List<取り込みエラーDto>
        mappingErrorList: MappingErrorOutput[];
    }

    export interface PersonEmpBasicInfoImport {
        // 個人ID
        personId: string;

        // 社員ID
        employeeId: string;

        // ビジネスネーム
        businessName: string;

        // 性別
        gender: number;

        // 生年月日
        birthday: string;

        // 社員コード
        employeeCode: string;

        // 入社年月日
        jobEntryDate: string;

        // 退職年月日
        retirementDate: string;
    }

    export interface PublicHoliday {
        companyId: string;

        date: string;

        holidayName: string;
    }

    export interface ImportResult {
        /** 1件分の取り込み結果 **/
        results: ImportResultDetail[];
        /** 取り込み不可日 **/
        unimportableDates: string[];
        /** 存在しない社員 **/
        unexistsEmployees: string[];
        /** 社員の並び順(OrderdList) **/
        orderOfEmployees: string[];
    }

    export interface ImportResultDetail {
        /** 社員ID **/
        employeeId: string;
        /** 年月日 **/
        ymd: string;
        /** 取り込みコード **/
        importCode: string;
        /** 状態 **/
        status: number;
    }

    export interface MappingErrorOutput {
        // 社員コード
        employeeCode: string;
        // 社員名
        employeeName: string;
        // 年月日
        date: string;
        // エラーメッセージ
        errorMessage: string;
    }
}