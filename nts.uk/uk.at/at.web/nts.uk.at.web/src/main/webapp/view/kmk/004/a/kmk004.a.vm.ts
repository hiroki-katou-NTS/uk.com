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

            public save(): void {
                let self = this;
                console.log(ko.toJS(self.companyWTSetting));
            }
            
            public remove(): void {
                
            }

            public loadCompanySetting(companySetting: any): void {
                let self = this;
                self.companyWTSetting(new CompanyWTSetting());
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

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
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
            deformationLaborWTSetting: DeformationLaborWTSetting;
            flexWTSetting: FlexWTSetting;
            normalWTSetting: NormalWTSetting;
            year: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.year = ko.observable(201705);
                self.deformationLaborWTSetting = new DeformationLaborWTSetting();
                self.flexWTSetting = new FlexWTSetting();
                self.normalWTSetting = new NormalWTSetting();
            }
        }
        export class WorkPlaceWTSetting {
            deformationLaborWTSetting: DeformationLaborWTSetting;
            flexWTSetting: FlexWTSetting;
            normalWTSetting: NormalWTSetting;
            year: KnockoutObservable<number>;
            workPlaceId: string;

            constructor() {
                let self = this;
                self.workPlaceId = '';
                self.year = ko.observable(201705);
                self.deformationLaborWTSetting = new DeformationLaborWTSetting();
                self.flexWTSetting = new FlexWTSetting();
                self.normalWTSetting = new NormalWTSetting();
            }
        }
        export class EmployeeWTSetting {
            workingTimeSetting: WorkingTimeSetting;
            yearMonth: KnockoutObservable<number>;
            employeeId: string;

            constructor() {
                let self = this;
                self.year = ko.observable(201705);
                self.deformationLaborWTSetting = new DeformationLaborWTSetting();
                self.flexWTSetting = new FlexWTSetting();
                self.normalWTSetting = new NormalWTSetting();
            }
        }
        export class EmploymentWTSetting {
            deformationLaborWTSetting: DeformationLaborWTSetting;
            flexWTSetting: FlexWTSetting;
            normalWTSetting: NormalWTSetting;
            year: KnockoutObservable<number>;
            employmentCode: string;

            constructor() {
                let self = this;
                self.employmentCode = '';
                self.year = ko.observable(201705);
                self.deformationLaborWTSetting = new DeformationLaborWTSetting();
                self.flexWTSetting = new FlexWTSetting();
                self.normalWTSetting = new NormalWTSetting();
            }
        }
        export class DeformationLaborWTSetting {
            statutorySetting: WorkingTimeSetting;
            weekStart: number;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.weekStart = 0;
            }
        }
        export class FlexWTSetting {
            statutorySetting: WorkingTimeSetting;
            specifiedSetting: WorkingTimeSetting;

            constructor() {
                let self = this;
                self.statutorySetting = new WorkingTimeSetting();
                self.specifiedSetting = new WorkingTimeSetting();
            }
        }
        export class NormalWTSetting {
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
                    self.monthly.push(new Monthly(i + '月度', 12));
                }
            }
        }
        export class Monthly {
            month: string;
            time: KnockoutObservable<number>;

            constructor(month: string, value: number) {
                let self = this;
                self.time = ko.observable(value);
                self.month = month;
            }
        }
    }
}