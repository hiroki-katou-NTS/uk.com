module nts.uk.pr.view.qmm020.i.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;

    export class ScreenModel {
        //_______CCG001____
        ccgcomponent: any;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<any>;
        workplaceId: KnockoutObservable<string> = ko.observable("");
        employeeId: KnockoutObservable<string> = ko.observable("");
        listConfirmOfIndividualSetStt :  KnockoutObservableArray<ConfirmOfIndividualSetSttDto> = ko.observableArray([]);

        constructor() {
            let self = this;
            //_____CCG001________
            self.selectedEmployee = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
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
                    let params = getShared(model.PARAMETERS_SCREEN_I.INPUT);
                    if (params == null || params == undefined)
                        return;
                    let dataInput: any = {
                        type: params.modeScreen,
                        employeeIds: data.listEmployee,
                        hisId: params.hisId,
                        baseDate: data.baseDate
                    };
                    nts.uk.com.view.qmm020.i.service.acquiProcess(dataInput).done((resulf : Array<ConfirmOfIndividualSetSttDto>) => {
                        this.listConfirmOfIndividualSetStt(resulf);
                    }).fail((err) => {
                        if (err)
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

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }
    export interface ConfirmOfIndividualSetSttDto {
    /**
     * 給与明細書
     */

    salaryCode :string;

    /**
     * 給与明細書
     */
    salaryCodeMaster :string;
    /**
     * 給与明細書
     */
    salaryCodeIndividual :string;
    /**
     * 賞与明細書
     */
    bonusCodeCompany :string;
    /**
     * 賞与明細書
     */
    bonusCodeMaster :string;
    /**
     * 賞与明細書
     */
    bonusCodeIndividual :string;
    /**
     * 明細書名称
     */
    statementName :string;

    /**
     * マスタコード
     */
    masterCode :string;
    /**
     * 雇用名称
     */
    employmentName :string;
    /**
     * 部門名称
     */
    departmentName :string;
    /**
     * 分類名称
     */
    classificationName :string;
    /**
     * 職位名称
     */
    positionName :string;
    /**
     * 給与分類名称
     */
    salaryClassificationName :string;
}



}