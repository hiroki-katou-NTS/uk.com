module nts.uk.pr.view.qmm020.i.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;

    export class ScreenModel {
        //_______CCG001____
        ccgcomponent: any;
        baseDate: KnockoutObservable<Date>;
        listEmp: KnockoutObservableArray<ConfirmPersonSetStatus> = ko.observableArray([]);

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
                    block.invisible();
                    let empIds = _.map(data.listEmployee, (item: EmployeeSearchDto) => {
                        return item.employeeId;
                    });
                    let dataInput: any = {
                        empIds: empIds,
                        baseDate: data.baseDate
                    };
                    service.getStatementLinkPerson(dataInput).done((res: Array<IConfirmPersonSetStatus>) => {
                        self.listEmp(ConfirmPersonSetStatus.fromApp(data.listEmployee, res));
                        $("#grid").ntsGrid("destroy");
                        self.loadGrid();
                    }).fail((err) => {
                        dialog.alertError(err);
                    }).always(() => {
                        block.clear();
                    });
                }
            };
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
            self.loadGrid();
            dfd.resolve();
            return dfd.promise();
        }

        loadGrid() {
            let self = this;
            $("#grid").ntsGrid({
                width: '1000px',
                height: '400px',
                dataSource: self.listEmp(),
                primaryKey: 'empId',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: '', key: 'empId', dataType: 'string', hidden: true},
                    {headerText: getText("QMM020_26"), key: 'empCd', dataType: 'string', width: '120px'},
                    {headerText: getText("QMM020_50"), key: 'empName', dataType: 'string', width: '200px'},
                    {headerText: getText("QMM020_20"), key: 'salary', dataType: 'string', width: '200px',},
                    {headerText: getText("QMM020_22"), key: 'bonus', dataType: 'string', width: '200px'},
                    {headerText: getText("QMM020_51"), key: 'master', dataType: 'string', width: '280px'}
                ],
                features: [
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false
                    },
                    {
                        name: "Resizing",
                        deferredResizing: false,
                        allowDoubleClickToResize: true,
                        columnSettings: [
                            {columnKey: "empCd", minimumWidth: 100},
                            {columnKey: "empName", minimumWidth: 150}
                        ]
                    },
                    {
                        name: "Sorting",
                        type: "local"
                    }
                ],
            });
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

    interface IConfirmPersonSetStatus {
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
        settingCtg: number;

        /**
         * 適用マスタコード
         */
        masterCode: string;

        /**
         * 適用マスタ名称
         */
        masterName: string;
    }

    class ConfirmPersonSetStatus {
        empId: string;
        empCd: string;
        empName: string;
        salary: string;
        bonus: string;
        master: string;

        constructor(emp: EmployeeSearchDto, data: IConfirmPersonSetStatus) {
            this.empId = emp.employeeId;
            this.empCd = emp.employeeCode;
            this.empName = emp.employeeName;
            this.salary = isNullOrUndefined(data.salaryCode) ? "未設定" : data.salaryCode + "　" + data.salaryName;
            this.bonus = isNullOrUndefined(data.bonusCode) ? "未設定" : data.bonusCode + "　" + data.bonusName;
            this.master = getSettingClsText(data.settingCtg);
            if (data.settingCtg != SettingCls.PERSON && data.settingCtg != SettingCls.COMPANY) {
                this.master += "　" + "(" + data.masterCode + "　" + data.masterName + ")"
            }
        }

        static fromApp(listEmployee: Array<EmployeeSearchDto>, items: Array<IConfirmPersonSetStatus>) {
            let result: Array<ConfirmPersonSetStatus> = [];
            _.each(listEmployee, (emp: EmployeeSearchDto) => {
                let data = _.find(items, (item: IConfirmPersonSetStatus) => {
                    return item.sid == emp.employeeId;
                });
                let dto = new ConfirmPersonSetStatus(emp, data);
                result.push(dto)
            });
            return result;
        }
    }

    enum SettingCls {
        // 個人
        PERSON = 0,
        // 雇用
        EMPLOYEE = 1,
        // 部門
        DEPARMENT = 2,
        // 分類
        CLASSIFICATION = 3,
        // 職位
        POSITION = 4,
        // 給与分類
        SALARY = 5,
        // 会社
        COMPANY = 6,
    }

    function getSettingClsText(e: SettingCls) {
        switch (e) {
            case SettingCls.PERSON:
                return getText("QMM020_11");
            case SettingCls.EMPLOYEE:
                return getText("QMM020_6");
            case SettingCls.DEPARMENT:
                return getText("QMM020_7");
            case SettingCls.CLASSIFICATION:
                return getText("QMM020_8");
            case SettingCls.POSITION:
                return getText("QMM020_9");
            case SettingCls.SALARY:
                return getText("QMM020_10");
            case SettingCls.COMPANY:
                return getText("QMM020_5");
            default:
                return "";
        }
    }
}