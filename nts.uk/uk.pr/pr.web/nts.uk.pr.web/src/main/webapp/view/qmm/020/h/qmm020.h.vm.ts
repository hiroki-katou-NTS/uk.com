module nts.uk.pr.view.qmm020.h.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import error = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import init = nts.uk.pr.view.qmm008.f.service.init;

    export class ScreenModel {

        bonusCode: KnockoutObservable<string> = ko.observable();
        bonusName: KnockoutObservable<string> = ko.observable();
        salaryCode: KnockoutObservable<string> = ko.observable();
        salaryName: KnockoutObservable<string> = ko.observable();
        columns: KnockoutObservableArray<string>;
        hisIdSelected: KnockoutObservable<string> = ko.observable();
        listStateCorrelationHis: KnockoutObservableArray<any> = ko.observableArray([]);
        mode: KnockoutObservable<number> = ko.observable();
        selectedEmployeeObject: any;

        //--- KCP009 ----->
        employeeInputList: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;

        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;

        //-- CCG001 ---->
        periodEndDate: KnockoutObservable<any> =  ko.observable();
        ccg001ComponentOption: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.targetBtnText = getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 1;
            self.initScreen();
        }

        /* CCG001 */
        loadCCG001(){
           let self = this;
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: true,
                showSameWorkplaceAndChild: true,

                /** Advanced search properties */
                showEmployment: true,
                showWorkplace: true,
                showClassification: true,
                showJobTitle: true,
                showWorktype: true,
                isMutipleCheck: true,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.selectedEmployee(data.listEmployee);
                    console.log(data.listEmployee);
                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

        }

        loadKCP009(){
            let self = this;
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }

        initScreen(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();

            service.getEmployee().done(function(emp) {
                service.getWpName().done(function(wp) {
                    if (wp == null || wp.workplaceId == null || wp.workplaceId == "") {
                        dialog.alertError({ messageId: "Msg_504" }).then(() => {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        });
                    } else {
                        self.selectedEmployeeObject = {
                            employeeId: emp.sid, employeeCode: emp.employeeCode, employeeName: emp.employeeName,
                            workplaceId: wp.workplaceId, workplaceCode: wp.code, workplaceName: wp.name
                        };
                        self.employeeInputList.push(new Employee(emp.sid,
                            emp.employeeCode, emp.employeeName, wp.name, wp.name));
                        self.loadKCP009();
                        dfd.resolve();
                    }
                }).fail(function(result) {
                    dialog.alertError(result.errorMessage);
                    dfd.reject();
                });
            }).fail(function(result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });

            return dfd.promise();
        }



       /* enableRegis() {
            return this.mode() == model.MODE.NO_REGIS;
        }

        enableNew() {
            return this.mode() == model.MODE.NEW;
        }

        enableEdit() {
            return this.mode() == model.MODE.UPDATE;
        }*/
    }

    /*KCP009 */
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }
    export class Employee {
        id: string;
        code: string;
        businessName: string;
        workplaceName: string;
        depName: string;

        constructor(id: string, code: string, businessName: string, workplaceName: string, depName: string) {
            this.id = id;
            this.code = code;
            this.businessName = businessName;
            this.workplaceName = workplaceName;
            this.depName = depName;
        }
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
    /* CCG001*/
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
        isInDialog?: boolean;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
        isTab2Lazy?: boolean;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }
    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }
}