module nts.uk.pr.view.qsi003.b.viewmodel {
    import service = nts.uk.pr.view.qsi003.b.service;
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qsi003.share.model;
    import errors = nts.uk.ui.errors;


    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        ccg001ComponentOption: GroupOption;
        employeeInputList: KnockoutObservableArray<EmployeeModel>= ko.observableArray([]);

        //kcp009
        constraint: string = 'LayoutCode';
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>= ko.observable(null);
        tabindex: number;
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        roundingRules: KnockoutObservableArray<model.ItemModel> =  ko.observableArray(model.roundingRules());

        //param
        basicPenNumber : KnockoutObservable<string> = ko.observable('');
        empId : KnockoutObservable<string> = ko.observable('');
        others : KnockoutObservable<boolean> = ko.observable(false);
        listeds : KnockoutObservable<boolean> = ko.observable(false);
        residentCards : KnockoutObservable<number> = ko.observable(1);
        addressOverseas : KnockoutObservable<boolean> = ko.observable(false);
        otherReasons : KnockoutObservable<string> = ko.observable('');
        shortResidents : KnockoutObservable<boolean> = ko.observable(false);

        otherp : KnockoutObservable<boolean> = ko.observable(false);
        listedp : KnockoutObservable<boolean> = ko.observable(false);
        residentCardp : KnockoutObservable<number> = ko.observable(1);
        addressOverseap : KnockoutObservable<boolean> = ko.observable(false);
        otherReasonp : KnockoutObservable<string> = ko.observable('');
        shortResidentp : KnockoutObservable<boolean> = ko.observable(false);

        loadKCP009(){
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            self.systemReference = ko.observable(SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("対象者");
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.tabindex = 3;

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

        cancel(){
            nts.uk.ui.windows.close();
        }

        getDefaultNameReport(){
            let self = this;
            self.others(false);
            self.listeds(false);
            self.residentCards(1);
            self.addressOverseas(false);
            self.otherReasons('');
            self.shortResidents(false);

            self.otherp(false);
            self.listedp(false);
            self.residentCardp(1);
            self.addressOverseap(false);
            self.otherReasonp('');
            self.shortResidentp(false);
        }

        getDefaultBasicPen(){
            let self = this;
            self.basicPenNumber('');
        }

        getDefault(){
            let self = this;
            self.getDefaultNameReport();
            self.getDefaultBasicPen();
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        getDataRomaji(empId: string) {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();
            nts.uk.pr.view.qsi003.b.service.getReasonRomajiName(empId).done(function (data: any) {
                if(data){
                    if (data.basicPenNumber) {
                        self.basicPenNumber(data.basicPenNumber);
                    } else {
                        self.getDefaultBasicPen();
                    }
                    if(data.empNameReport) {
                        self.others(data.empNameReport.spouse.other != 0);
                        self.listeds(data.empNameReport.spouse.listed != 0);
                        self.residentCards(data.empNameReport.spouse.residentCard);
                        self.addressOverseas(data.empNameReport.spouse.addressOverseas != 0);
                        self.otherReasons(data.empNameReport.spouse.otherReason);
                        self.shortResidents(data.empNameReport.spouse.shortResident != 0);

                        self.otherp(data.empNameReport.personalSet.other != 0);
                        self.listedp(data.empNameReport.personalSet.listed != 0);
                        self.residentCardp(data.empNameReport.personalSet.residentCard);
                        self.addressOverseap(data.empNameReport.personalSet.addressOverseas != 0);
                        self.otherReasonp(data.empNameReport.personalSet.otherReason);
                        self.shortResidentp(data.empNameReport.personalSet.shortResident != 0);
                    } else {
                        self.getDefaultNameReport();
                    }
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                } else {
                    self.getDefault();
                }
            }).fail(error => {
                dialog.alertError(error);
                dfd.reject();
            });
            block.clear();
            return dfd.promise();
        }

        constructor() {
            let self = this;
            $("#emp-component").focus();
            self.otherp.subscribe(e =>{
                if (!self.otherp()&& $("#B3_14").ntsError("hasError")){
                    $("#B3_14").ntsError('clear');
                }else{
                    $("#B3_14").trigger("validate");
                }

                if(!self.otherp()){
                    self.otherReasonp('');
                }
            });

            self.others.subscribe(e =>{
                if (!self.others()&& $("#B4_12").ntsError("hasError")){
                    $("#B4_12").ntsError('clear');
                }else{
                    $("#B4_12").trigger("validate");
                }

                if(!self.others()){
                    self.otherReasons('');
                }
            });
            self.selectedItem.subscribe((data) => {
                errors.clearAll();
                self.getDataRomaji(data);
            });
            let list = getShared("QSI003_PARAMS_B");
            if(nts.uk.util.isNullOrEmpty(list) || nts.uk.util.isNullOrEmpty(list.employeeList)) {
                close();
            }
            self.employeeInputList(self.createEmployeeModel(list.employeeList));
            this.loadKCP009();
            self.selectedItem(self.employeeInputList()[0].id);
        }

        createEmployeeModel(data) {
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.id,
                    code: data.code,
                    businessName: data.name,
                    workplaceName: data.workplaceName
                });
            });

            return listEmployee;
        }

        updateReasonRomajiName(){
            var self = this;
            let spouse : any = {
                other :  self.others() ? 1 : 0,
                listed :  self.listeds() ? 1 : 0,
                residentCard : self.residentCards(),
                addressOverseas : self.addressOverseas() ? 1 : 0,
                otherReason : self.others() && self.otherReasons() ? self.otherReasons() : null,
                shortResident : self.shortResidents() ? 1 : 0
            }

            let personalSet : any = {
                other :  self.otherp() ? 1 : 0,
                listed :  self.listedp() ? 1 : 0,
                residentCard : self.residentCardp(),
                addressOverseas : self.addressOverseap() ? 1 : 0,
                otherReason : self.otherp() && self.otherReasonp() ? self.otherReasonp() : null,
                shortResident : self.shortResidentp() ? 1 : 0
            }

            let empNameReportCommand: any = {
                empId: self.selectedItem(),
                personalSet : personalSet,
                spouse : spouse

            };

            let reasonRomajiNameCommand: any = {
                empId: self.selectedItem(),
                basicPenNumber : self.basicPenNumber().length > 0 ? self.basicPenNumber() : null,
                empNameReportCommand : empNameReportCommand,
                screenMode: model.SCREEN_MODE.UPDATE
            };

            nts.uk.pr.view.qsi003.b.service.updateReasonRomajiName(reasonRomajiNameCommand).done( function() {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                    self.getDataRomaji(self.selectedItem());
                });
            }).fail(error => {
                dialog.alertError(error);
            });
            $("#emp-component").focus();
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
    }
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
}