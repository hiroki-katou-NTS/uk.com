module nts.uk.at.view.kmk004.a {
    export module viewmodel {

        import UsageUnitSettingService = nts.uk.at.view.kmk004.e.service;

        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            companySelectedTab: KnockoutObservable<string>;
            employmentSelectedTab: KnockoutObservable<string>;
            workplaceSelectedTab: KnockoutObservable<string>;

            itemList: KnockoutObservableArray<ItemModel>;
            employmentCodes: KnockoutObservableArray<any>;

            companyWTSetting: KnockoutObservable<CompanyWTSetting>;
            employmentWTSetting: KnockoutObservable<EmploymentWTSetting>;
            workplaceWTSetting: KnockoutObservable<WorkPlaceWTSetting>;

            usageUnitSetting: UsageUnitSetting;
            listEmploymentOption: any;
            selectedEmploymentCode: KnockoutObservable<string>;
            isNewMode: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.usageUnitSetting = new UsageUnitSetting();
                self.isNewMode = ko.observable(true);
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.itemList = ko.observableArray([
                    new ItemModel('0', '月曜日'),
                    new ItemModel('1', '火曜日'),
                    new ItemModel('2', '水曜日'),
                    new ItemModel('3', '木曜日'),
                    new ItemModel('4', '金曜日'),
                    new ItemModel('5', '土曜日'),
                    new ItemModel('6', '日曜日'),
                    new ItemModel('7', '締め開始日')
                ]);
                self.companyWTSetting = ko.observable(new CompanyWTSetting());
                self.employmentWTSetting = ko.observable(new EmploymentWTSetting());
                self.workplaceWTSetting = ko.observable(new WorkPlaceWTSetting());
                self.companySelectedTab = ko.observable('tab-1');
                self.employmentSelectedTab = ko.observable('tab-1');
                self.workplaceSelectedTab = ko.observable('tab-1');

                self.employmentCodes = ko.observableArray([]);
                self.selectedEmploymentCode = ko.observable('');
                self.setListComponentOption();
                $('#list-employment').ntsListComponent(this.listEmploymentOption);
                self.selectedEmploymentCode.subscribe(code => {
                    self.loadEmploymentSetting(code);
                });
            }

            private setListComponentOption(): void {
                let self = this;
                self.listEmploymentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: 1,
                    selectedCode: this.selectedEmploymentCode,
                    isDialog: false,
                    alreadySettingList: self.employmentCodes
                };
            }

            public onSelectCompany(): void {
                let self = this;
                self.loadCompanySetting();
            }

            public onSelectEmployment(): void {
                let self = this;
                self.setAlreadySettingEmploymentList();
                // Select first employment.
                let list = $('#list-employment').getDataList();
                if (list) {
                    // force to reload.
                    self.selectedEmploymentCode.valueHasMutated();
                    self.selectedEmploymentCode(list[0].code);
                }
            }

            public onSelectWorkplace(): void {
                let self = this;
                self.setAlreadySettingWorkplaceList();
                self.loadWorkplaceSetting('1');
            }

            public gotoE(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/004/e/index.xhtml").onClosed(() => self.loadUsageUnitSetting());
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadUsageUnitSetting(), self.loadCompanySetting()).done(() => dfd.resolve());
                return dfd.promise();
            }

            public saveCompanySetting(): void {
                let self = this;
                service.saveCompanySetting(ko.toJS(self.companyWTSetting)).done(() => {
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            public saveEmployment(): void {
                let self = this;
                service.saveEmploymentSetting(ko.toJS(self.employmentWTSetting)).done(() => {
                    self.setAlreadySettingEmploymentList();
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            public removeEmployment(): void {
                let self = this;
                let empt = self.employmentWTSetting();
                let command = { year: empt.year(), employmentCode: empt.employmentCode() }
                service.removeEmploymentSetting(command).done(() => {
                    self.isNewMode(true);
                    self.setAlreadySettingEmploymentList();
                    // Reserve current code + name.
                    let empt = new EmploymentWTSetting();
                    empt.employmentCode(self.employmentWTSetting().employmentCode());
                    empt.employmentName(self.employmentWTSetting().employmentName());
                    self.employmentWTSetting(empt);
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                });
            }
            public saveWorkplace(): void {
                let self = this;
                service.saveWorkplaceSetting(ko.toJS(self.workplaceWTSetting)).done(() => {
                    self.setAlreadySettingWorkplaceList();
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            public removeWorkplace(): void {
                let self = this;
                let workplace = self.workplaceWTSetting();
                let command = { year: workplace.year(), workplaceId: workplace.workplaceId() }
                service.removeWorkplaceSetting(command).done(() => {
                    self.isNewMode(true);
                    self.setAlreadySettingWorkplaceList();
                    self.workplaceWTSetting(new WorkPlaceWTSetting());
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                });
            }
            public removeCompanySetting(): void {
                let self = this;
                let command = { year: self.companyWTSetting().year() }
                service.removeCompanySetting(command).done(() => {
                    self.companyWTSetting(new CompanyWTSetting());
                    self.isNewMode(true);
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                });
            }

            public loadCompanySetting(): void {
                let self = this;
                service.findCompanySetting(self.companyWTSetting().year()).done(res => {
                    // update mode.
                    if (res) {
                        self.isNewMode(false);
                        self.companyWTSetting(ko.mapping.fromJS(res));
                    }
                    else {
                        // new mode.
                        self.isNewMode(true);
                        self.companyWTSetting(new CompanyWTSetting());
                    }
                    // Sort month.
                    self.getStartMonth().done(month => {
                        //let sorted = self.sortMonthly(self.companyWTSetting().deformationLaborSetting.statutorySetting.monthly(), month);
                        //self.companyWTSetting().deformationLaborSetting.statutorySetting.monthly(sorted);
                    });
                });
            }

            public loadEmploymentSetting(code: string): void {
                let self = this;
                if (code) {
                    let request = { year: self.employmentWTSetting().year(), employmentCode: code };
                    service.findEmploymentSetting(request).done(res => {
                        // update mode.
                        if (res) {
                            self.isNewMode(false);
                            self.employmentWTSetting(ko.mapping.fromJS(res));
                        } 
                        // new mode.
                        else {
                            self.isNewMode(true);
                            self.employmentWTSetting(new EmploymentWTSetting());
                        }
                        // Set code + name.
                        let list = $('#list-employment').getDataList();
                        if (list) {
                            let empt = list.filter(item => item.code == code)[0];
                            self.employmentWTSetting().employmentCode(empt.code);
                            self.employmentWTSetting().employmentName(empt.name);
                        }
                    });
                }
            }

            public loadWorkplaceSetting(id: string): void {
                let self = this;
                let request = { year: self.employmentWTSetting().year(), workplaceId: id };
                service.findWorkplaceSetting(request).done(res => {
                    // update mode.
                    if (res) {
                        self.isNewMode(false);
                        self.workplaceWTSetting(ko.mapping.fromJS(res));
                    } 
                    // new mode.
                    else {
                        self.isNewMode(true);
                        self.workplaceWTSetting(new WorkPlaceWTSetting());
                    }
                    // Set code + name.
                    self.workplaceWTSetting().workplaceCode('code');
                    self.workplaceWTSetting().workplaceName('name');
                });
            }

            public loadUsageUnitSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                UsageUnitSettingService.findUsageUnitSetting().done(function(res: UsageUnitSettingService.model.UsageUnitSettingDto) {
                    self.usageUnitSetting.employee(res.employee);
                    self.usageUnitSetting.employment(res.employment);
                    self.usageUnitSetting.workplace(res.workPlace);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            public setAlreadySettingEmploymentList(): void {
                let self = this;
                service.findAllEmploymentSetting(self.companyWTSetting().year()).done(listCode => {
                    if (listCode) {
                        self.employmentCodes.removeAll();
                        listCode.forEach(item => self.employmentCodes.push({ code: item, isAlreadySetting: true }));
                    }
                });
            }

            private setAlreadySettingWorkplaceList(): void {
                let self = this;
                service.findAllWorkplaceSetting(self.companyWTSetting().year()).done(res => {
                    //TODO...
                });
            }

            private getStartMonth(): JQueryPromise<number> {
                let self = this;
                let dfd = $.Deferred<number>();
                service.getCompany().done(res => {
                    dfd.resolve(res.startMonth);
                }).fail(() => {
                    // Default startMonth..
                    dfd.resolve(1);
                });
                return dfd.promise();
            }

            private sortMonthly(monthly: Array<any>, startMonth: number): Array<any> {
                let self = this;
                let i = startMonth;
                let sortedList: Array<any> = new Array<any>();
                monthly.forEach(month => {
                    i++;
                    if (i == 12) {
                        // Reset
                        i = 1;
                    }
                });
                for (let i = 1; i < 13; i++) {
                    let month = (startMonth + i - 1) % 12;
                    let value = monthly.filter(m => month == m.month())[0];
                    sortedList.push(value);
                }
                return sortedList;
            }

        }
        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class CompanyWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
        }
        export class WorkPlaceWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            workplaceId: KnockoutObservable<string>;
            workplaceCode: KnockoutObservable<string>;
            workplaceName: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.workplaceId = ko.observable('1');
                self.workplaceCode = ko.observable('');
                self.workplaceName = ko.observable('');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
        }
        export class EmployeeWTSetting {
            workingTimeSetting: WorkingTimeSetting;
            yearMonth: KnockoutObservable<number>;
            employeeId: string;

            constructor() {
                let self = this;
                self.yearMonth = ko.observable();
                self.employeeId = '';
            }
        }
        export class EmploymentWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            employmentCode: KnockoutObservable<string>;
            employmentName: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }

            // TODO: testing.
            public reset(): void {
                let self = this;
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
        }
        export class DeformationLaborSetting {
            statutorySetting: WorkingTimeSetting;
            weekStart: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.weekStart = ko.observable(0);
            }
        }
        export class FlexSetting {
            flexDaily: FlexDaily;
            flexMonthly: KnockoutObservableArray<FlexMonth>;

            constructor() {
                let self = this;
                self.flexDaily = new FlexDaily();
                self.flexMonthly = new ko.observableArray<FlexMonth>([]);
                for (let i = 1; i < 13; i++) {
                    let flm = new FlexMonth();
                    flm.month(i);
                    flm.statutoryTime(0);
                    flm.specifiedTime(0);
                    self.flexMonthly.push(flm);
                }
            }
        }
        export class FlexDaily {
            statutoryTime: KnockoutObservable<number>;
            specifiedTime: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.statutoryTime = ko.observable(0);
                self.specifiedTime = ko.observable(0);
            }
        }
        export class FlexMonth {
            month: KnockoutObservable<number>;
            statutoryTime: KnockoutObservable<number>;
            specifiedTime: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.month = ko.observable(0);
                self.statutoryTime = ko.observable(0);
                self.specifiedTime = ko.observable(0);
            }
        }
        export class NormalSetting {
            statutorySetting: WorkingTimeSetting;
            weekStart: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.weekStart = ko.observable(0);
            }
        }
        export class WorkingTimeSetting {
            daily: KnockoutObservable<number>;
            monthly: KnockoutObservableArray<Monthly>;
            startMonth: KnockoutObservable<number>;
            weekly: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.daily = ko.observable(0);
                self.startMonth = ko.observable(0);
                self.weekly = ko.observable(0);
                self.monthly = ko.observableArray<Monthly>([]);
                for (let i = 1; i < 13; i++) {
                    let m = new Monthly();
                    m.month(i);
                    self.monthly.push(m);
                }
            }
            public reset(): void {
                let self = this;
                self.daily(0);
                self.startMonth(0);
                self.weekly(0);
            }
        }
        export class Monthly {
            month: KnockoutObservable<number>;
            time: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.time = ko.observable(0);
                self.month = ko.observable(0);
            }
        }
        export class UsageUnitSetting {
            employee: KnockoutObservable<boolean>;
            employment: KnockoutObservable<boolean>;
            workplace: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.employee = ko.observable(true);
                self.employment = ko.observable(true);
                self.workplace = ko.observable(true);
            }
        }
    }
}