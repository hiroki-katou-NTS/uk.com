module nts.uk.pr.view.qsi001.b.viewmodel {

    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        ccg001ComponentOption: GroupOption;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        //

        depNotiAttach: KnockoutObservableArray<any>;
        selectedDepNotoAttach: any;

        basicPension: KnockoutObservable<number>;
        salaryMonthly: KnockoutObservable<number>;
        salaryMonthlyActual: KnockoutObservable<number>;
        totalCompensation: KnockoutObservable<number>;
        depNotiAttach : KnockoutObservableArray<any>;
        selectedDepNotiAttach: any;

        applyToEmployeeOver70: KnockoutObservable<boolean>;
        twoOrMoreEmployee: KnockoutObservable<boolean>;
        shortWorkHours: KnockoutObservable<boolean>;
        continuousEmpAfterRetire: KnockoutObservable<boolean>;
        otherNotes: KnockoutObservable<boolean>;
        textOtherNotes: KnockoutObservable<boolean>;
        shortTermResidence: KnockoutObservable<boolean>;
        otherNotes1: KnockoutObservable<boolean>;
        textOtherNotes1: KnockoutObservable<boolean>;

        livingAbroad: KnockoutObservable<boolean>;
        //kcp009
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;
        //

        texteditor: any;
        simpleValue: KnockoutObservable<string>;

        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            block.invisible();
            let self = this;

            let params = getShared('QSI001_PARAMS_TO_SCREEN_B');

            self.loadKCP009(self.createEmployeeModel(params.listEmpId));


            service.getSocialInsurAcquisiInforById(self.selectedItem()).done(e =>{
                self.otherNotes(e.remarksOther == 1 ? true : false);
                self.textOtherNotes(e.remarksAndOtherContents);
                self.salaryMonthlyActual(e.remunMonthlyAmountKind);
                self.salaryMonthly(e.remunMonthlyAmount);
                self.totalCompensation(e.totalMonthlyRemun);
                self.livingAbroad(e.livingAbroad == 1 ? true : false);
                self.otherNotes1(e.reasonOther == 1 ? true : false);
                self.textOtherNotes1(e.reasonAndOtherContents);
                self.shortTermResidence(e.shortStay == 1 ? true : false);
                self.selectedDepNotiAttach(e.depenAppoint);
                self.shortWorkHours(e.shortTimeWorkers == 1 ? true : false);
                self.continuousEmpAfterRetire(e.continReemAfterRetirement == 1 ? true : false);
            }).fail(e =>{

            });

            service.getPersonInfo(self.selectedItem()).done(r => {
                if(self.getAge(r.birthDay,params.date) >= 70){
                    self.applyToEmployeeOver70(true);
                    self.otherNotes(true);
                }
            }).fail(f =>{
                console.dir(f);
            });



            //init

            self.depNotiAttach = ko.observableArray([]);
            self.selectedDepNotoAttach = ko.observable({});

            self.basicPension  = ko.observable(0);
            self.salaryMonthly = ko.observable(0);
            self.salaryMonthlyActual = ko.observable(0);
            self.totalCompensation = ko.observable(0);
            self.depNotiAttach = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('QSI001_54') },
                { code: '2', name: nts.uk.resource.getText('QSI001_55') }
            ]);
            self.selectedDepNotiAttach = ko.observable(1);

            self.applyToEmployeeOver70 = ko.observable(false);
            self.twoOrMoreEmployee = ko.observable(false);
            self.shortWorkHours = ko.observable(false);
            self.continuousEmpAfterRetire = ko.observable(false);
            self.otherNotes = ko.observable(false);
            self.textOtherNotes = ko.observable("");

            self.livingAbroad = ko.observable(false);
            self.shortTermResidence = ko.observable(false);
            self.otherNotes1 = ko.observable(false);
            self.textOtherNotes1 = ko.observable("");



            //end init
            self.loadCCG001();
            self.simpleValue = ko.observable("123");

            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('QSI001_54') },
                { code: '2', name: nts.uk.resource.getText('QSI001_55') }
            ]);
            self.selectedRuleCode = ko.observable(1);

            block.clear();
        }

        getAge(DOB,date) {
            var today = new Date(date);
            var birthDate = new Date(DOB);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age = age - 1;
            }

            return age;
        }

        cancel(){
            nts.uk.ui.windows.close();
        }
        /* CCG001 */
        loadCCG001(){
            let self = this;
            self.ccg001ComponentOption = {
                /** Common properties */
                systemType: 1,
                showEmployeeSelection: true,
                showQuickSearchTab: false,
                showAdvancedSearchTab: true,
                showBaseDate: false,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,
                tabindex: 5,
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

                    self.loadKCP009(self.createEmployeeModel(data.listEmployee));

                }
            }

            $('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption);

        }



        add(){
            let self = this;
            let data = {
                socialInsurAcquisiInforCommand: {
                    employeeId: self.selectedItem(),
                    percentOrMore: self.applyToEmployeeOver70() == true ? 1 : 0,
                    remarksOther: self.otherNotes() == true ? 1 : 0,
                    remarksAndOtherContents: self.textOtherNotes(),
                    remunMonthlyAmountKind: Number(self.salaryMonthlyActual()),
                    remunMonthlyAmount: Number(self.salaryMonthly()),
                    totalMonthlyRemun: Number(self.totalCompensation()),
                    livingAbroad: self.livingAbroad() == true ? 1 : 0,
                    reasonOther: self.otherNotes1() == true ? 1 : 0,
                    reasonAndOtherContents: self.textOtherNotes1(),
                    shortStay: self.shortTermResidence() == true ? 1 : 0,
                    depenAppoint: self.selectedDepNotiAttach(),
                    qualifiDistin: 0,
                    shortTimeWorkers: self.shortWorkHours() == true ? 1: 0,
                    continReemAfterRetirement: self.continuousEmpAfterRetire() == true ? 1 : 0
                },
                empBasicPenNumInforCommand: {
                    employeeId: self.selectedItem(),
                    basicPenNumber: Number(self.basicPension())

                },
                multiEmpWorkInfoCommand: {
                    employeeId: self.selectedItem(),
                    isMoreEmp: self.twoOrMoreEmployee() == true ? 1 : 0,

                }
            }


            service.add(data).done(e =>{

            }).fail(e =>{

            });
        }
        createEmployeeModel(data){
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.employeeId,
                    code: data.employeeCode,
                    businessName: data.employeeName,
                    workplaceName: data.workplaceName
                });
            });

            return listEmployee;
        }
        loadKCP009(data){
            let self = this;
            self.employeeInputList = ko.observableArray(data);
            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 1;
            // Initial listComponentOption
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


    }

    // Note: Defining these interfaces are optional
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
        maxPeriodRange?: string; // 最長期間
        showSort?: boolean; // 並び順利用
        nameType?: number; // 氏名の種類

        /** Required parameter */
        baseDate?: any; // 基準日 KnockoutObservable<string> or string
        periodStartDate?: any; // 対象期間開始日 KnockoutObservable<string> or string
        periodEndDate?: any; // 対象期間終了日 KnockoutObservable<string> or string
        dateRangePickerValue?: KnockoutObservable<any>;
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameDepartment?: boolean; //同じ部門の社員
        showSameDepartmentAndChild?: boolean; // 同じ部門とその配下の社員
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showDepartment?: boolean; // 部門条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード

        /** Optional properties */
        isInDialog?: boolean;
        showOnStart?: boolean;
        isTab2Lazy?: boolean;
        tabindex?: number;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        affiliationId: string; // departmentId or workplaceId based on system type
        affiliationName: string; // departmentName or workplaceName based on system type
    }
    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
}