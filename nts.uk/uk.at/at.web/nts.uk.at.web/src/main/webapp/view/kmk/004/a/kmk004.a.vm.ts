module nts.uk.at.view.kmk004.a {
    export module viewmodel {

        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;

            companyWTSetting: KnockoutObservable<CompanyWTSetting>;
            usageUnitSetting: UsageUnitSetting;
            currentYear: number;

            constructor() {
                let self = this;
                self.currentYear = new Date().getFullYear();
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
                self.companyWTSetting().year(self.currentYear);
                self.selectedCode = ko.observable('');
                self.selectedTab = ko.observable('tab-1');
            }
            
            public gotoE(): void {
                nts.uk.ui.windows.sub.modal("/view/kmk/004/e/index.xhtml");
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                self.loadCompanySetting();
                dfd.resolve();
                return dfd.promise();
            }

            public save(): void {
                let self = this;
                service.saveCompanySetting(ko.toJS(self.companyWTSetting));
                console.log(ko.toJS(self.companyWTSetting));
            }

            public remove(): void {
                let self = this;
                let command = {year: self.companyWTSetting().year()}
                service.removeCompanySetting(command);
            }

            public loadCompanySetting(): void {
                let self = this;
                service.findCompanySetting(self.companyWTSetting().year()).done(res => {
                    if (res) {
                        let abc = ko.mapping.fromJS(res);
                        self.companyWTSetting(abc);
                        console.log(abc);
                    }
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
                self.year = ko.observable(0);
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
                self.year = ko.observable(201705);
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
            employmentCode: string;

            constructor() {
                let self = this;
                self.employmentCode = '';
                self.year = ko.observable(201705);
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