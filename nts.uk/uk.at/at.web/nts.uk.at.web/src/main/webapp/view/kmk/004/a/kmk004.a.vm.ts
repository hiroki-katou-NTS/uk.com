module nts.uk.at.view.kmk004.a {
    export module viewmodel {

        import UsageUnitSettingService = nts.uk.at.view.kmk004.e.service;

        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            itemList: KnockoutObservableArray<ItemModel>;
            employmentCodes: KnockoutObservableArray<any>;

            companyWTSetting: KnockoutObservable<CompanyWTSetting>;
            employmentWTSetting: KnockoutObservable<EmploymentWTSetting>;
            usageUnitSetting: UsageUnitSetting;
            listEmploymentOption: any;
            selectedEmploymentCode: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.usageUnitSetting = new UsageUnitSetting();
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
                self.selectedTab = ko.observable('tab-1');

                self.selectedEmploymentCode = ko.observable('');

                self.employmentCodes = ko.observableArray([]);
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

            public onSelectEmployment(): void {
                let self = this;
                self.setAlreadySettingList();
            }

            public gotoE(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/004/e/index.xhtml").onClosed(() => self.loadUsageUnitSetting());
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                self.loadUsageUnitSetting();
                self.loadCompanySetting();
                dfd.resolve();
                return dfd.promise();
            }

            public save(): void {
                let self = this;
                service.saveCompanySetting(ko.toJS(self.companyWTSetting));
            }

            public saveEmployment(): void {
                let self = this;
                service.saveEmploymentSetting(ko.toJS(self.employmentWTSetting)).done(() => self.setAlreadySettingList());
            }

            public removeEmployment(): void {
                let self = this;
                let empt = self.employmentWTSetting();
                let command = { year: empt.year(), employmentCode: empt.employmentCode() }
                service.removeEmploymentSetting(command).done(() => {
                    self.setAlreadySettingList();
                    let code = self.employmentWTSetting().employmentCode();
                    let name = self.employmentWTSetting().employmentName();
                    let empt = new EmploymentWTSetting();
                    empt.employmentCode(code);
                    empt.employmentName(name);
                    self.employmentWTSetting(empt);
                });
            }
            public remove(): void {
                let self = this;
                let command = {year: self.companyWTSetting().year()}
                service.removeCompanySetting(command);
            }

            public loadCompanySetting(): void {
                let self = this;
                service.findCompanySetting(self.companyWTSetting().year()).done(res => {
                    // update mode.
                    if (res) {
                        let abc = ko.mapping.fromJS(res);
                        self.companyWTSetting(abc);
                    }
                    else {
                        // new mode.
                        self.companyWTSetting(new CompanyWTSetting());
                    }
                });
            }

            public loadEmploymentSetting(code: string): void {
                let self = this;
                let request = { year: self.employmentWTSetting().year(), employmentCode: code };
                service.findEmploymentSetting(request).done(res => {
                    // update mode.
                    if (res) {
                        let abc = ko.mapping.fromJS(res);
                        self.employmentWTSetting(abc);
                    } else {
                        // new mode.
                        let empt = new EmploymentWTSetting();
                        empt.employmentCode(code);
                        empt.employmentName('fake name');
                        self.employmentWTSetting(empt);
                    }
                });
            }

            public loadUsageUnitSetting(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                UsageUnitSettingService.findUsageUnitSetting().done(function(res: UsageUnitSettingService.model.UsageUnitSettingDto) {
                    self.usageUnitSetting.employee(res.employee);
                    self.usageUnitSetting.employment(res.employment);
                    self.usageUnitSetting.workPlace(res.workPlace);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private setAlreadySettingList(): void {
                let self = this;
                service.findAllEmploymentSetting(self.companyWTSetting().year()).done(res => {
                    self.employmentCodes.removeAll();
                    res.forEach(item => self.employmentCodes.push({ code: item, isAlreadySetting: true }));
                });
            }

            private sort(startMonth: number): Array<any> {
                let self = this;
                let list: Array<any> = new Array<any>();
                for (let i = 0; i < 12; i++) {
                    let index = (startMonth + i - 1) % 12;
                    //list.push(self.vidu()[index]);
                }
                return list;
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
            workPlaceId: string;

            constructor() {
                let self = this;
                self.workPlaceId = '';
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
            workPlace: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.employee = ko.observable(true);
                self.employment = ko.observable(true);
                self.workPlace = ko.observable(true);
            }
        }
    }
}