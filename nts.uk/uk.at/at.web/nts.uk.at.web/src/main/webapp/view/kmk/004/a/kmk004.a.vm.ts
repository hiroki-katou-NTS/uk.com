module nts.uk.at.view.kmk004.a {
    export module viewmodel {

        import UsageUnitSettingService = nts.uk.at.view.kmk004.e.service;

        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            itemList: KnockoutObservableArray<ItemModel>;

            companyWTSetting: CompanyWTSetting;
            employmentWTSetting: EmploymentWTSetting;
            workplaceWTSetting: WorkPlaceWTSetting;
            usageUnitSetting: UsageUnitSetting;

            // Employment list component.
            listEmploymentOption: any;
            employmentCodes: KnockoutObservableArray<any>;
            selectedEmploymentCode: KnockoutObservable<string>;

            // Flag.
            isNewMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            isCompanySelected: KnockoutObservable<boolean>;
            isEmploymentSelected: KnockoutObservable<boolean>;
            isWorkplaceSelected: KnockoutObservable<boolean>;
            isEmployeeSelected: KnockoutObservable<boolean>;

            // Start month.
            startMonth: KnockoutObservable<number>;

            constructor() {
                let self = this;

                // Set start month.
                self.setStartMonth();

                // Flag.
                self.isNewMode = ko.observable(true);
                self.isLoading = ko.observable(true);
                self.isCompanySelected = ko.observable(true);
                self.isEmploymentSelected = ko.observable(false);
                self.isWorkplaceSelected = ko.observable(false);
                self.isEmployeeSelected = ko.observable(false);

                // Datasource.
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK004_3"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK004_4"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK004_5"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.itemList = ko.observableArray([
                    new ItemModel(0, '月曜日'),
                    new ItemModel(1, '火曜日'),
                    new ItemModel(2, '水曜日'),
                    new ItemModel(3, '木曜日'),
                    new ItemModel(4, '金曜日'),
                    new ItemModel(5, '土曜日'),
                    new ItemModel(6, '日曜日'),
                    new ItemModel(7, '締め開始日')
                ]);

                // Data model.
                self.usageUnitSetting = new UsageUnitSetting();
                self.companyWTSetting = new CompanyWTSetting();
                self.employmentWTSetting = new EmploymentWTSetting();
                self.workplaceWTSetting = new WorkPlaceWTSetting();

                // year subscribe.
                self.companyWTSetting.year.subscribe(val => {
                    self.loadCompanySetting();
                });
                self.employmentWTSetting.year.subscribe(val => {
                    self.loadEmploymentSetting();
                });
                self.workplaceWTSetting.year.subscribe(val => {
                    self.loadWorkplaceSetting();
                });

                // Employment list component.
                self.employmentCodes = ko.observableArray([]);
                self.selectedEmploymentCode = ko.observable('');
                self.setListComponentOption();
                self.selectedEmploymentCode.subscribe(code => {
                    if (code) {
                        self.loadEmploymentSetting(code);
                    }
                });
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                $.when(self.loadUsageUnitSetting(),
                    self.loadCompanySetting())
                    .done(() => dfd.resolve());
                return dfd.promise();
            }

            /**
             * Event on select company.
             */
            public onSelectCompany(): void {
                let self = this;
                self.isLoading(true);
                self.loadCompanySetting();
                self.isCompanySelected(true);
                self.isEmploymentSelected(false);
                self.isEmployeeSelected(false);
                self.isWorkplaceSelected(false);
            }

            /**
             * Event on select employment.
             */
            public onSelectEmployment(): void {
                let self = this;
                self.isLoading(true);
                self.isCompanySelected(false);
                self.isEmploymentSelected(true);
                self.isEmployeeSelected(false);
                self.isWorkplaceSelected(false);
                $('#list-employment').ntsListComponent(this.listEmploymentOption).done(() => {
                    // Select first employment.
                    let list = $('#list-employment').getDataList();
                    if (list) {
                        self.selectedEmploymentCode(list[0].code);
                    }
                });
            }

            /**
             * Event on select workplace.
             */
            public onSelectWorkplace(): void {
                let self = this;
                self.isLoading(true);
                self.isCompanySelected(false);
                self.isEmploymentSelected(false);
                self.isEmployeeSelected(false);
                self.isWorkplaceSelected(true);
                // mock data.
                self.loadWorkplaceSetting('1');
            }

            /**
             * Go to screen E (usage unit setting).
             */
            public gotoE(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/004/e/index.xhtml").onClosed(() => self.loadUsageUnitSetting());
            }

            /**
             * Save company setting.
             */
            public saveCompanySetting(): void {
                let self = this;
                service.saveCompanySetting(ko.toJS(self.companyWTSetting)).done(() => {
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            /**
             * Save employment setting.
             */
            public saveEmployment(): void {
                let self = this;
                service.saveEmploymentSetting(ko.toJS(self.employmentWTSetting)).done(() => {
                    self.setAlreadySettingEmploymentList();
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            /**
             * Save workplace setting.
             */
            public saveWorkplace(): void {
                let self = this;
                service.saveWorkplaceSetting(ko.toJS(self.workplaceWTSetting)).done(() => {
                    self.setAlreadySettingWorkplaceList();
                    self.isNewMode(false);
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            /**
             * Remove employment setting.
             */
            public removeEmployment(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                        let empt = self.employmentWTSetting;
                        let command = { year: empt.year(), employmentCode: empt.employmentCode() }
                        service.removeEmploymentSetting(command).done(() => {
                            self.isNewMode(true);
                            self.setAlreadySettingEmploymentList();
                            // Reserve current code + name + year.
                            let newEmpt = new EmploymentWTSetting();
                            newEmpt.employmentCode(empt.employmentCode());
                            newEmpt.employmentName(empt.employmentName());
                            newEmpt.year(empt.year());
                            self.employmentWTSetting.updateData(ko.toJS(newEmpt));
                            // Sort month.
                            self.employmentWTSetting.sortMonth(self.startMonth());
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        });
                    }).ifNo(function() {
                        nts.uk.ui.block.clear();
                        return;
                    })
                }

            /**
             * Remove workplace setting.
             */
            public removeWorkplace(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let workplace = self.workplaceWTSetting;
                    let command = { year: workplace.year(), workplaceId: workplace.workplaceId() }
                    service.removeWorkplaceSetting(command).done(() => {
                        self.isNewMode(true);
                        self.setAlreadySettingWorkplaceList();
                        // Reserve current code + name + year + id.
                        let newSetting = new WorkPlaceWTSetting();
                        newSetting.year(workplace.year());
                        newSetting.workplaceId(workplace.workplaceId());
                        newSetting.workplaceCode(workplace.workplaceCode());
                        newSetting.workplaceName(workplace.workplaceName());
                        self.workplaceWTSetting.updateData(ko.toJS(newSetting));
                        // Sort month.
                        self.workplaceWTSetting.sortMonth(self.startMonth());
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }

            /**
             * Remove company setting.
             */
            public removeCompanySetting(): void {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let selectedYear = self.companyWTSetting.year();
                    let command = { year: selectedYear }
                    service.removeCompanySetting(command).done(() => {
                        // Reserve current year.
                        let newSetting = new CompanyWTSetting();
                        newSetting.year(selectedYear);
                        self.companyWTSetting.updateData(ko.toJS(newSetting));
                        // Sort month.
                        self.companyWTSetting.sortMonth(self.startMonth());
                        self.isNewMode(true);
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }

            /**
             * Load company setting.
             */
            public loadCompanySetting(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findCompanySetting(self.companyWTSetting.year())
                    .done(function(data) {
                        // update mode.
                        if (data) {
                            self.isNewMode(false);
                            self.companyWTSetting.updateData(data);
                        }
                        else {
                            // new mode.
                            self.isNewMode(true);
                            let newSetting = new CompanyWTSetting();
                            // Reserve selected year.
                            newSetting.year(self.companyWTSetting.year());
                            self.companyWTSetting.updateData(ko.toJS(newSetting));
                        }
                        // Sort month.
                        self.companyWTSetting.sortMonth(self.startMonth());
                        self.isLoading(false);
                        dfd.resolve();
                    });
                return dfd.promise();
            }

            /**
             * Load employment setting.
             */
            public loadEmploymentSetting(code?: string): void {
                let self = this;
                let currentSetting = self.employmentWTSetting;
                let request;
                // Code changed.
                if (code) {
                    request = { year: currentSetting.year(), employmentCode: code };
                }
                // Year changed. Code is unchanged
                else {
                    request = { year: currentSetting.year(), employmentCode: currentSetting.employmentCode() };
                }
                service.findEmploymentSetting(request)
                    .done(function(data) {
                        // update mode.
                        if (data) {
                            self.isNewMode(false);
                            self.employmentWTSetting.updateData(data);
                        }
                        // new mode.
                        else {
                            self.isNewMode(true);
                            let newSetting = new EmploymentWTSetting();
                            // reserve selected year.
                            newSetting.year(currentSetting.year());
                            self.employmentWTSetting.updateData(ko.toJS(newSetting));
                        }
                        // Set code + name.
                        let list = $('#list-employment').getDataList();
                        if (list) {
                            let empt = list.filter(item => item.code == request.employmentCode)[0];
                            self.employmentWTSetting.employmentCode(empt.code);
                            self.employmentWTSetting.employmentName(empt.name);
                        }
                        // Sort month.
                        self.employmentWTSetting.sortMonth(self.startMonth());
                        self.isLoading(false);
                    });
                self.setAlreadySettingEmploymentList();
            }

            /**
             * Load workplace setting.
             */
            public loadWorkplaceSetting(id?: string): void {
                let self = this;
                let currentSetting = self.workplaceWTSetting;
                let request;
                // workplaceId changed.
                if (id) {
                    request = { year: currentSetting.year(), workplaceId: id };
                }
                // Year changed. workplaceId is unchanged
                else {
                    request = { year: currentSetting.year(), workplaceId: currentSetting.workplaceId() };
                }
                service.findWorkplaceSetting(request)
                    .done(function(data) {
                        // update mode.
                        if (data) {
                            self.isNewMode(false);
                            self.workplaceWTSetting.updateData(data);
                        }
                        // new mode.
                        else {
                            self.isNewMode(true);
                            let newSetting = new WorkPlaceWTSetting();
                            // reserve selected year.
                            newSetting.year(currentSetting.year());
                            self.workplaceWTSetting.updateData(ko.toJS(newSetting));
                        }
                        // Set code + name.
                        self.workplaceWTSetting.workplaceCode('code');
                        self.workplaceWTSetting.workplaceName('name');
                        // Sort month.
                        self.workplaceWTSetting.sortMonth(self.startMonth());
                        self.isLoading(false);
                    });
                self.setAlreadySettingWorkplaceList();
            }

            /**
             * Load usage unit setting.
             */
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

            /**
             * Set the already setting employment list.
             */
            private setAlreadySettingEmploymentList(): void {
                let self = this;
                service.findAllEmploymentSetting(self.employmentWTSetting.year()).done(listCode => {
                    if (listCode) {
                        self.employmentCodes.removeAll();
                        listCode.forEach(item => self.employmentCodes.push({ code: item, isAlreadySetting: true }));
                    }
                });
            }

            /**
             * Set the already setting workplace list.
             */
            private setAlreadySettingWorkplaceList(): void {
                let self = this;
                service.findAllWorkplaceSetting(self.workplaceWTSetting.year()).done(res => {
                    //TODO...
                });
            }

            /**
             * Set start month.
             */
            private setStartMonth(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.getCompany().done(res => {
                    self.startMonth = ko.observable(res.startMonth);
                    dfd.resolve();
                }).fail(() => {
                    // Default startMonth..
                    self.startMonth = ko.observable(1);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Set list component option.
             */
            private setListComponentOption(): void {
                let self = this;
                self.listEmploymentOption = {
                    isShowAlreadySet: true, // is show already setting column.
                    isMultiSelect: false, // is multiselect.
                    listType: 1, // employment list.
                    selectedCode: this.selectedEmploymentCode,
                    isDialog: false,
                    alreadySettingList: self.employmentCodes
                };
            }
        }

        export class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        export class CompanyWTSetting {
            deformationLaborSetting: DeformationLaborSetting;
            flexSetting: FlexSetting;
            normalSetting: NormalSetting;
            year: KnockoutObservable<number>;
            selectedTab: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }

            public updateData(dto: any): void {
                let self = this;
                self.year(dto.year);
                self.normalSetting.updateData(dto.normalSetting);
                self.deformationLaborSetting.updateData(dto.deformationLaborSetting);
                self.flexSetting.updateData(dto.flexSetting);
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                self.normalSetting.statutorySetting.sortMonth(startMonth);
                self.deformationLaborSetting.statutorySetting.sortMonth(startMonth);
                self.flexSetting.sortMonth(startMonth);
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
            selectedTab: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.workplaceId = ko.observable('1');
                self.workplaceCode = ko.observable('');
                self.workplaceName = ko.observable('');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
            public updateData(dto: any): void {
                let self = this;
                self.year(dto.year);
                self.normalSetting.updateData(dto.normalSetting);
                self.deformationLaborSetting.updateData(dto.deformationLaborSetting);
                self.flexSetting.updateData(dto.flexSetting);
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                self.normalSetting.statutorySetting.sortMonth(startMonth);
                self.deformationLaborSetting.statutorySetting.sortMonth(startMonth);
                self.flexSetting.sortMonth(startMonth);
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
            selectedTab: KnockoutObservable<string>;

            constructor() {
                let self = this;
                self.selectedTab = ko.observable('tab-1');
                self.employmentCode = ko.observable('');
                self.employmentName = ko.observable('');
                self.year = ko.observable(new Date().getFullYear());
                self.deformationLaborSetting = new DeformationLaborSetting();
                self.flexSetting = new FlexSetting();
                self.normalSetting = new NormalSetting();
            }
            public updateData(dto: any): void {
                let self = this;
                self.year(dto.year);
                self.normalSetting.updateData(dto.normalSetting);
                self.deformationLaborSetting.updateData(dto.deformationLaborSetting);
                self.flexSetting.updateData(dto.flexSetting);
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                self.normalSetting.statutorySetting.sortMonth(startMonth);
                self.deformationLaborSetting.statutorySetting.sortMonth(startMonth);
                self.flexSetting.sortMonth(startMonth);
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

            public updateData(dto: any): void {
                let self = this;
                self.weekStart(dto.weekStart);
                self.statutorySetting.updateData(dto.statutorySetting);
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
            public updateData(dto: any): void {
                let self = this;
                self.flexDaily.updateData(dto.flexDaily);
                self.flexMonthly.removeAll();
                dto.flexMonthly.forEach(item => {
                    let m = new FlexMonth();
                    m.month(item.month);
                    m.statutoryTime(item.statutoryTime);
                    m.specifiedTime(item.specifiedTime);
                    self.flexMonthly.push(m);
                });
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                let self = this;
                let month = startMonth;
                let sortedList: Array<any> = new Array<any>();
                for (let i = 0; i < 12; i++) {
                    if (month > 12) {
                        // reset month.
                        month = 1;
                    }
                    let value = self.flexMonthly().filter(m => month == m.month())[0];
                    sortedList.push(value);
                    month++;
                }
                self.flexMonthly(sortedList);
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
            public updateData(dto: any): void {
                let self = this;
                self.statutoryTime(dto.statutoryTime);
                self.specifiedTime(dto.specifiedTime);
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

            public updateData(dto: any): void {
                let self = this;
                self.weekStart(dto.weekStart);
                self.statutorySetting.updateData(dto.statutorySetting);
            }
        }
        export class WorkingTimeSetting {
            daily: KnockoutObservable<number>;
            monthly: KnockoutObservableArray<Monthly>;
            weekly: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.daily = ko.observable(0);
                self.weekly = ko.observable(0);
                self.monthly = ko.observableArray<Monthly>([]);
                for (let i = 1; i < 13; i++) {
                    let m = new Monthly();
                    m.month(i);
                    self.monthly.push(m);
                }
            }
            public updateData(dto: any): void {
                let self = this;
                self.daily(dto.daily);
                self.weekly(dto.weekly);
                self.monthly.removeAll();
                dto.monthly.forEach(item => {
                    let m = new Monthly();
                    m.month(item.month);
                    m.time(item.time);
                    self.monthly.push(m);
                });
            }
            public sortMonth(startMonth: number): void {
                let self = this;
                let self = this;
                let month = startMonth;
                let sortedList: Array<any> = new Array<any>();
                for (let i = 0; i < 12; i++) {
                    if (month > 12) {
                        // reset month.
                        month = 1;
                    }
                    let value = self.monthly().filter(m => month == m.month())[0];
                    sortedList.push(value);
                    month++;
                }
                self.monthly(sortedList);
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