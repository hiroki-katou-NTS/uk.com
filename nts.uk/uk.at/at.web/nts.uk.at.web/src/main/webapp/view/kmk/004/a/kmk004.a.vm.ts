module nts.uk.at.view.kmk004.a {
    export module viewmodel {

        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;

            companyWTSetting: KnockoutObservable<CompanyWTSetting>;

            constructor() {
                let self = this;
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.itemList = ko.observableArray([
                    new ItemModel('基本給1', '基本給'),
                    new ItemModel('基本給2', '役職手当'),
                    new ItemModel('0003', '基本給')
                ]);
                self.companyWTSetting = ko.observable(new CompanyWTSetting());
                self.selectedCode = ko.observable('');
                self.selectedTab = ko.observable('tab-1');
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                service.findCompanySetting(2017);
                dfd.resolve();
                return dfd.promise();
            }

            public save(): void {
                let self = this;
                self.companyWTSetting().year(2017);
                service.saveCompanySetting(ko.toJS(self.companyWTSetting));
                console.log(ko.toJS(self.companyWTSetting));
            }
            
            public remove(): void {
                
            }

            public loadCompanySetting(companySetting: any): void {
                let self = this;
                service.findCompanySetting(2017);
                self.companyWTSetting(new CompanyWTSetting());
                let abc = ko.observable();
                ko.mapping.fromJS(companySetting, abc);
                console.log(abc());
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
            ym: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.year = ko.observable(2017);
                self.ym = ko.observable(201706);
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
                self.year = ko.observable(201705);
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
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
            weekStart: number;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.weekStart = 0;
            }
        }
        export class FlexSetting {
            flexDaily: FlexDaily;
            flexMonthly: Array<FlexMonth>;

            constructor() {
                let self = this;
                self.flexDaily = new FlexDaily();
                self.flexMonthly = new Array<FlexMonth>();
                for (let i = 1; i < 13; i++) {
                    self.flexMonthly.push(new FlexMonth());
                }
            }
        }
        export class FlexDaily {
            statutoryTime: KnockoutObservable<number>;
            specifiedTime: KnockoutObservable<number>;
            constructor() {
                let self = this;
                self.statutoryTime = ko.observable(1);
                self.specifiedTime = ko.observable(1);
            }
        }
        export class FlexMonth {
            month: number;
            statutoryTime: KnockoutObservable<number>;;
            specifiedTime: KnockoutObservable<number>;;
            constructor() {
                let self = this;
                self.month = 1
                self.statutoryTime = ko.observable(1);
                self.specifiedTime = ko.observable(1);
            }
        }
        export class NormalSetting {
            statutorySetting: WorkingTimeSetting;
            weekStart: number;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.weekStart = 0;
            }
        }
        export class WorkingTimeSetting {
            daily: KnockoutObservable<number>;
            monthly: Array<Monthly>;
            startMonth: KnockoutObservable<number>;
            weekly: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.daily = ko.observable(0);
                self.startMonth = ko.observable(0);
                self.weekly = ko.observable(0);
                self.monthly = [];
                for (let i = 1; i < 13; i++) {
                    self.monthly.push(new Monthly(i , 12));
                }
            }
        }
        export class Monthly {
            month: number;
            time: KnockoutObservable<number>;

            constructor(month: number, value: number) {
                let self = this;
                self.time = ko.observable(value);
                self.month = month;
            }
        }
    }
}