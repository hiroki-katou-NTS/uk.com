module nts.uk.pr.view.qmm020.i.viewmodel {
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        //_______CCG001____
        ccgcomponent: any;
        baseDate: KnockoutObservable<Date>;
        listEmp: KnockoutObservableArray<ConfirmPersonSetStatusDto> = ko.observableArray([]);

        constructor() {
            let self = this;
            //_____CCG001________
            self.ccgcomponent = {

                showEmployeeSelection: false, // 検索タイプ
                systemType: 2, // システム区分 - 就業
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: false, // 基準日利用
                showClosure: false, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: false, // 対象期間利用
                periodFormatYM: true, // 対象期間精度

                /** Required parameter */
                baseDate: moment.utc().toISOString(), // 基準日
                inService: true, // 在職区分
                leaveOfAbsence: true, // 休職区分
                closed: true, // 休業区
                retirement: true, // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: true, // 参照可能な社員すべて
                showOnlyMe: true, // 自分だけ
                showSameWorkplace: true, // 同じ職場の社員
                showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: true, // 雇用条件
                showWorkplace: true, // 職場条件
                showClassification: true, // 分類条件
                showJobTitle: true, // 職位条件
                showWorktype: true, // 勤種条件
                isMutipleCheck: true,
                /**
                 * @param dataList: list employee returned from component.
                 * Define how to use this list employee by yourself in the function's body.
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    let self = this;
                    let empIds = _.map(data.listEmployee, (item: EmployeeSearchDto) => {
                        return item.employeeId;
                    });
                    let dataInput: any = {
                        empIds: empIds,
                        baseDate: data.baseDate
                    };
                    service.getStatementLinkPerson(dataInput).done((resulf: Array<ConfirmPersonSetStatusDto>) => {
                        self.listEmp(resulf);
                    }).fail((err) => {
                        dialog.alertError(err);
                    });

                }
            };
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent); // '#com-ccg-001' is the component container's id
            $("#fixed-table").ntsFixedTable({height: 430, width: 880});
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

    }

    interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    interface ConfirmPersonSetStatusDto {
        sid: string;

        /**
         * 給与明細書コード
         */
        salaryCode: string;

        /**
         * 給与明細書名称
         */
        salaryName: string;

        /**
         * 賞与明細書コード
         */
        bonusCode: string;

        /**
         * 賞与明細書名称
         */
        bonusName: string;

        /**
         * 適用設定区分
         */
        settingCtg: string;

        /**
         * 適用マスタコード
         */
        masterCode: string;

        /**
         * 適用マスタ名称
         */
        masterName: string;
    }
}