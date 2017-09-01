module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<model.SpecialHolidayDto>;
        sphdList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;

        currentItem: KnockoutObservable<model.SpecialHolidayDto>
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        selectedValue: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;

        //Tab Regular
        option1: KnockoutObservable<any>;
        option2: KnockoutObservable<any>;

        //
        date: KnockoutObservable<string>;
        roundingSplitAcquisition: KnockoutObservableArray<any>;
        roundingCarryForward: KnockoutObservableArray<any>;
        roundingGenderAtr: KnockoutObservableArray<any>;
        roundingMakeInvitation: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        //Input Screen
        roundingPeriodic: KnockoutObservableArray<any>;
        workTypeList: KnockoutObservableArray<any>;
        workTypeNames: KnockoutObservable<string>;
        isEnableCode: KnockoutObservable<boolean>;
        inp_grantMethod: KnockoutObservable<number>;

        //Combobox
        itemAgeBaseYearAtr: KnockoutObservableArray<any>;
        visibleGrantSingle: KnockoutObservable<boolean>;
        visibleGrant: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;

            self.visibleGrantSingle = ko.observable(false);
            self.visibleGrant = ko.observable(true);
            self.inp_grantMethod = ko.observable(0);

            self.items = ko.observableArray([]);
            self.sphdList = ko.observableArray([]);
            self.enable = ko.observable(true);

            self.date = ko.observable('');
            self.selectedCode = ko.observable('0002')
            self.isEnable = ko.observable(true);
            self.isEnableCode = ko.observable(false);
            self.isEditable = ko.observable(true);

            self.currentItem = ko.observable(new model.SpecialHolidayDto({}));
            self.workTypeList = ko.observableArray([]);
            self.workTypeNames = ko.observable("");
            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);

            self.itemAgeBaseYearAtr = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('Enum_AgeBaseYearAtr_NEXT_MONTH') },
                { code: 1, name: nts.uk.resource.getText('Enum_AgeBaseYearAtr_THIS_MONTH') }
            ]);
            self.roundingPeriodic = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KMF004_15') },
                { code: 1, name: nts.uk.resource.getText('KMF004_14') },
            ]);

            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'specialHolidayCode', width: 100 },
                { headerText: '名称', key: 'specialHolidayName', width: 150 }
            ]);

            self.roundingSplitAcquisition = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KMF004_38') },
                { code: 1, name: nts.uk.resource.getText('KMF004_39') }
            ]);

            self.roundingCarryForward = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KMF004_51') },
                { code: 1, name: nts.uk.resource.getText('KMF004_52') }
            ]);

            self.roundingGenderAtr = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KMF004_125') },
                { code: 1, name: nts.uk.resource.getText('KMF004_126') }
            ]);

            self.roundingMakeInvitation = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KMF004_61') },
                { code: 1, name: nts.uk.resource.getText('KMF004_62') }
            ]);

            //Tab1
            self.selectedValue = ko.observable(false);
            self.option1 = ko.observable({ value: 0, text: nts.uk.resource.getText('KMF004_23') });

            //Tab2
            self.option2 = ko.observable({ value: 1, text: nts.uk.resource.getText('KMF004_24') });

            self.currentCode = ko.observable();
            self.currentCode.subscribe(function(codeChanged) {
                if (codeChanged !== null && codeChanged !== undefined) {
                    self.changedCode(codeChanged);
                    self.isEnableCode(false);
                }
            });

            self.inp_grantMethod.subscribe(function(value) {
                if (value == 0) {
                    self.visibleGrantSingle(false);
                    self.visibleGrant(true);
                    self.selectedTab('tab-5');
                } else {
                    self.visibleGrantSingle(true);
                    self.visibleGrant(false);
                    self.selectedTab('tab-1');
                }
            })

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText('KMF004_17'), content: '.tab-content-1', enable: ko.observable(true), visible: self.visibleGrantSingle },
                { id: 'tab-2', title: nts.uk.resource.getText('KMF004_18'), content: '.tab-content-2', enable: ko.observable(true), visible: self.visibleGrantSingle },
                { id: 'tab-3', title: nts.uk.resource.getText('KMF004_19'), content: '.tab-content-3', enable: ko.observable(true), visible: self.visibleGrantSingle },
                { id: 'tab-4', title: nts.uk.resource.getText('KMF004_20'), content: '.tab-content-4', enable: ko.observable(true), visible: self.visibleGrantSingle },
                { id: 'tab-5', title: nts.uk.resource.getText('KMF004_21'), content: '.tab-content-5', enable: ko.observable(true), visible: self.visibleGrant }
            ]);
            self.selectedTab = ko.observable('tab-5');
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            $.when(self.getAllSpecialHoliday(), self.getWorkTypeList()).done(function() {
                if (self.items().length > 0) {
                    self.currentCode(self.items()[0].specialHolidayCode());
                } else {
                    self.initSpecialHoliday();
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }

        getAllSpecialHoliday(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.items.removeAll();
            //self.sphdList.removeAll();
            service.findAllSpecialHoliday().done(function(specialHoliday_arr: Array<model.ISpecialHolidayDto>) {
                self.sphdList(specialHoliday_arr);
                _.forEach(specialHoliday_arr, function(specialHolidayRes: model.ISpecialHolidayDto) {
                    var specialHoliday: model.ISpecialHolidayDto = {
                        specialHolidayCode: specialHolidayRes.specialHolidayCode,
                        specialHolidayName: specialHolidayRes.specialHolidayName,
                        grantMethod: specialHolidayRes.grantMethod,
                        memo: specialHolidayRes.memo,
                        workTypeList: specialHolidayRes.workTypeList,
                        grantRegular: self.toGrantRegularDto(specialHolidayRes.grantRegular),
                        grantPeriodic: self.toGrantPeriodicDto(specialHolidayRes.grantPeriodic),
                        sphdLimit: self.toSphdLimitDto(specialHolidayRes.sphdLimit),
                        subCondition: self.toSubConditionDto(specialHolidayRes.subCondition),
                        grantSingle: self.toGrantSingleDto(specialHolidayRes.grantSingle)
                    };
                    self.items.push(new model.SpecialHolidayDto(specialHoliday));
                    
                });
                
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            
            return dfd.promise();
        }

        toGrantRegularDto(grantRegular: model.IGrantRegularDto): model.GrantRegularDto {
            if (!grantRegular) {
                return new model.GrantRegularDto({});
            }
            return new model.GrantRegularDto(grantRegular);
        }

        toGrantPeriodicDto(grantPeriodic: model.IGrantPeriodic): model.GrantPeriodicDto {
            if (!grantPeriodic) {
                return new model.GrantPeriodicDto({});
            }
            return new model.GrantPeriodicDto(grantPeriodic);
        }

        toSphdLimitDto(sphdLimit: model.ISphdLimitDto): model.SphdLimitDto {
            if (!sphdLimit) {
                return new model.SphdLimitDto({});
            }
            return new model.SphdLimitDto(sphdLimit);
        }

        toSubConditionDto(subCondition: any): model.SubConditionDto {
            if (!subCondition) {
                return new model.SubConditionDto({});
            }
            
            subCondition.useGender = Number(subCondition.useGender) == 1;
            subCondition.useEmployee = Number(subCondition.useEmployee) == 1;
            subCondition.useCls = Number(subCondition.useCls) == 1;
            subCondition.useAge = Number(subCondition.useAge) == 1;
            
            // TODO--
            
            return new model.SubConditionDto(subCondition);
        }

        toGrantSingleDto(grantSingle: model.IGrantSingleDto): model.GrantSingleDto {
            if (!grantSingle) {
                return new model.GrantSingleDto({});
            }
            return new model.GrantSingleDto(grantSingle);
        }

        addSpecialHoliday(): JQueryPromise<any> {
            var self = this;
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            if (self.inp_grantMethod() == 0) {
                self.currentItem().grantRegular(null);
                self.currentItem().grantPeriodic(null);
                self.currentItem().sphdLimit(null);
                self.currentItem().subCondition(null);
            } else {
                var useGender = self.currentItem().subCondition().useGender();
                var useEmployee = self.currentItem().subCondition().useEmployee();
                var useCls = self.currentItem().subCondition().useCls();
                var useAge = self.currentItem().subCondition().useAge();
                
                self.currentItem().subCondition().useGender(useGender ? 1 : 0);
                self.currentItem().subCondition().useEmployee(useEmployee ? 1 : 0);
                self.currentItem().subCondition().useCls(useCls ? 1 : 0);
                self.currentItem().subCondition().useAge(useAge ? 1 : 0);
                
                self.currentItem().grantSingle(null);
            }
            self.currentItem().grantMethod(self.inp_grantMethod());
            
            if (self.isEnableCode()) {
                var emptyObjectRegular: model.IGrantRegularDto = {};
                var emptyObjectPeriodic: model.IGrantPeriodic = {};
                var emptyObjectSphdLimit: model.ISphdLimitDto = {};
                var emptyObjectSubCondition: model.ISubConditionDto = {};
                var emptyObjectGrantSingle: model.IGrantSingleDto = {};
                
                var specialHoliday = ko.toJSON(self.currentItem());
                specialHoliday["grantMethod"] = self.inp_grantMethod();
                service.addSpecialHoliday(specialHoliday).done(function(res) {
                    var resObj = ko.toJS(res);
           
                    if (self.currentCode) {
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15"));
                        self.getAllSpecialHoliday().done(function() {
                            self.currentCode(self.currentItem().specialHolidayCode());
                            
                            self.isEnableCode(false);
                        });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
            }
            else {
                service.updateSpecialHoliday(ko.toJSON(self.currentItem())).done(function(res) {
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_15"));
                    self.getAllSpecialHoliday().done(function() {
                        self.currentCode(self.currentItem().specialHolidayCode());
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(function() {
                    nts.uk.ui.block.clear();
                })
            }
        }

        getWorkTypeList() {
            var self = this;
            var dfd = $.Deferred();
            service.findWorkType().done(function(res) {
                _.forEach(res, function(item) {
                    self.workTypeList.push({
                        workTypeCode: item.workTypeCode,
                        name: item.name,
                        memo: item.memo
                    });
                });
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        deleteSpecialHoliday() {
            let self = this;
            var index_of_itemDelete = _.findIndex(self.items(), ['specialHolidayCode', self.currentCode()]);
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                var specialholiday = {
                    specialHolidayCode: self.currentItem().specialHolidayCode()
                };
                service.deleteSpecialHoliday(specialholiday).done(function() {
                    $.when(self.getAllSpecialHoliday()).done(function() {
                        var holidayId: number = null;
                        if (self.items().length == 0) {
                            self.initSpecialHoliday();
                        } else if (self.items().length == 1) {
                            holidayId = self.items()[0].specialHolidayCode;
                        } else if (index_of_itemDelete == self.items().length) {
                            holidayId = self.items()[index_of_itemDelete - 1].specialHolidayCode;
                        } else {
                            holidayId = self.items()[index_of_itemDelete].specialHolidayCode;
                        }
                        self.currentCode(holidayId);
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16"));
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                })
            }).ifNo(function() {
            });
        }

        initSpecialHoliday(): void {
            let self = this;
            var emptyObject: model.ISpecialHolidayDto = {};
            self.currentItem(new model.SpecialHolidayDto(emptyObject))
            self.currentCode("");
            self.inp_grantMethod(0);
            self.visibleGrantSingle(false);
            self.visibleGrant(true);
            self.workTypeNames("");
            nts.uk.ui.errors.clearAll();
            self.isEnableCode(true);
        }

        changedCode(value) {
            var self = this;
            self.currentItem(self.findSpecialHoliday(value));
            
            if (self.currentItem() != null) {
                var names = self.getNames(self.workTypeList(), self.currentItem().workTypeList());
                self.workTypeNames(names);
                self.inp_grantMethod(self.currentItem().grantMethod());
            }
        }

        findSpecialHoliday(value: number): any {
            let self = this;
            var result = _.find(self.items(), function(obj: model.SpecialHolidayDto) {
                return obj.specialHolidayCode() == value;
            });
            if (result) {
                self.inp_grantMethod(result.grantMethod());
                return result;
            }

            return new model.SpecialHolidayDto({});
        }

        openKDL002Dialog() {
            let self = this;
            nts.uk.ui.block.invisible();

            var workTypeCodes = _.map(self.workTypeList(), function(item: IWorkTypeModal) { return item.workTypeCode });
            nts.uk.ui.windows.setShared('KDL002_Multiple', true);
            nts.uk.ui.windows.setShared('KDL002_AllItemObj', workTypeCodes);
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId', []);

            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
                var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                var name = [];
                _.forEach(data, function(item: IWorkTypeModal) {
                    name.push(item.name);
                });
                self.workTypeNames(name.join(" + "));

                var workTypeCodes = _.map(data, function(item: IWorkTypeModal) { return item.code; });
                self.currentItem().workTypeList(workTypeCodes);
            });

        }

        openBDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { height: 600, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        
        
        openDDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared('KMF_004_SPHD', self.currentItem().specialHolidayCode());
            nts.uk.ui.windows.sub.modal('/view/kmf/004/d/index.xhtml', { height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        
        openGDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/g/index.xhtml', {dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        openHDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/h/index.xhtml', { dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        getNames(data: Array<IWorkTypeModal>, workTypeCodesSelected: Array<string>) {
            var name = [];
            if (workTypeCodesSelected && workTypeCodesSelected.length > 0) {
                _.forEach(data, function(item: IWorkTypeModal) {
                    if (_.includes(workTypeCodesSelected, item.workTypeCode)) {
                        name.push(item.name);
                    }
                });
            }
            return name.join(" + ");
        }
    }

    class ItemModelSpecialHoliday {
        specialHolidayCode: number;
        specialHolidayName: string;
        constructor(specialHolidayCode: number, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;
        }
    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class WorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
        constructor(param: IWorkTypeModal) {
            this.workTypeCode = param.workTypeCode;
            this.name = param.name;
            this.memo = param.memo;
        }
    }

    export interface IWorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
    }

    export module model {
        export interface ISpecialHolidayDto {
            specialHolidayCode?: number;
            specialHolidayName?: string;
            grantMethod?: number;
            memo?: string;
            workTypeList?: Array<string>;
            grantRegular?: GrantRegularDto;
            grantPeriodic?: GrantPeriodicDto;
            sphdLimit?: SphdLimitDto;
            subCondition?: SubConditionDto;
            grantSingle?: GrantSingleDto;
        }
        export class SpecialHolidayDto {
            specialHolidayCode: KnockoutObservable<any>;
            specialHolidayName: KnockoutObservable<string>;
            grantMethod: KnockoutObservable<number>;
            memo: KnockoutObservable<string>;
            workTypeList: KnockoutObservableArray<any>;
            grantRegular: KnockoutObservable<GrantRegularDto>;
            grantPeriodic: KnockoutObservable<GrantPeriodicDto>;
            sphdLimit: KnockoutObservable<SphdLimitDto>;
            subCondition: KnockoutObservable<SubConditionDto>;
            grantSingle: KnockoutObservable<GrantSingleDto>;

            constructor(param: ISpecialHolidayDto) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || null);
                this.specialHolidayName = ko.observable(param.specialHolidayName || '');
                this.grantMethod = ko.observable(param.grantMethod || 0);
                this.memo = ko.observable(param.memo || '');
                this.workTypeList = ko.observableArray(param.workTypeList || null);
                this.grantRegular = ko.observable(param.grantRegular || new GrantRegularDto({}));
                this.grantPeriodic = ko.observable(param.grantPeriodic || new GrantPeriodicDto({}));
                this.sphdLimit = ko.observable(param.sphdLimit || new SphdLimitDto({}));
                this.subCondition = ko.observable(param.subCondition || new SubConditionDto({}));
                this.grantSingle = ko.observable(param.grantSingle || new GrantSingleDto({}));
            }
        }

        export interface IGrantRegularDto {
            specialHolidayCode?: number;
            grantStartDate?: string;
            months?: number;
            years?: number;
            grantRegularMethod?: number;
        }
        export class GrantRegularDto {
            specialHolidayCode: KnockoutObservable<any>;
            grantStartDate: KnockoutObservable<Date>;
            months: KnockoutObservable<number>;
            years: KnockoutObservable<number>;
            grantRegularMethod: KnockoutObservable<number>;
            constructor(param: IGrantRegularDto) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || '');
                this.grantStartDate = ko.observable(new Date(param.grantStartDate) || null);
                this.months = ko.observable(param.months || null);
                this.years = ko.observable(param.years || null);
                this.grantRegularMethod = ko.observable(param.grantRegularMethod || 0);
            }
        }

        export interface IGrantPeriodic {
            specialHolidayCode?: number;
            grantDay?: number;
            splitAcquisition?: number;
            grantPeriodicMethod?: number;
        }
        export class GrantPeriodicDto {
            specialHolidayCode: KnockoutObservable<any>;
            grantDay: KnockoutObservable<number>;
            splitAcquisition: KnockoutObservable<number>;
            grantPeriodicMethod: KnockoutObservable<number>;
            constructor(param: IGrantPeriodic) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || null);
                this.grantDay = ko.observable(param.grantDay || null);
                this.splitAcquisition = ko.observable(param.splitAcquisition || 0);
                this.grantPeriodicMethod = ko.observable(param.grantPeriodicMethod || 0);
            }
        }

        export interface ISphdLimitDto {
            specialHolidayCode?: number;
            specialVacationMonths?: number;
            specialVacationYears?: number;
            grantCarryForward?: number;
            limitCarryoverDays?: number;
            specialVacationMethod?: number;
        }
        export class SphdLimitDto {
            specialHolidayCode: KnockoutObservable<number>;
            specialVacationMonths: KnockoutObservable<number>;
            specialVacationYears: KnockoutObservable<number>;
            grantCarryForward: KnockoutObservable<number>;
            limitCarryoverDays: KnockoutObservable<number>;
            specialVacationMethod: KnockoutObservable<number>;
            constructor(param: ISphdLimitDto) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || null);
                this.specialVacationMonths = ko.observable(param.specialVacationMonths || null);
                this.specialVacationYears = ko.observable(param.specialVacationYears || null);
                this.grantCarryForward = ko.observable(param.grantCarryForward || 0);
                this.limitCarryoverDays = ko.observable(param.limitCarryoverDays || null);
                this.specialVacationMethod = ko.observable(param.specialVacationMethod || 0);
            }
        }

        export interface ISubConditionDto {
            specialHolidayCode?: number;
            useGender?: boolean;
            useEmployee?: boolean;
            useCls?: boolean;
            useAge?: boolean;
            genderAtr?: number;
            limitAgeFrom?: number;
            limitAgeTo?: number;
            ageCriteriaAtr?: number;
            ageBaseYearAtr?: number;
            ageBaseDates?: number;
        }
        export class SubConditionDto {
            specialHolidayCode: KnockoutObservable<number>;
            useGender: KnockoutObservable<boolean>;
            useEmployee: KnockoutObservable<boolean>;
            useCls: KnockoutObservable<boolean>;
            useAge: KnockoutObservable<boolean>;
            genderAtr: KnockoutObservable<number>;
            limitAgeFrom: KnockoutObservable<number>;
            limitAgeTo: KnockoutObservable<number>;
            ageCriteriaAtr: KnockoutObservable<number>;
            ageBaseYearAtr: KnockoutObservable<number>;
            ageBaseDates: KnockoutObservable<number>;
            constructor(param: ISubConditionDto) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || null);
                this.useGender = ko.observable(param.useGender || true);
                this.useEmployee = ko.observable(param.useEmployee || true);
                this.useCls = ko.observable(param.useCls || true);
                this.useAge = ko.observable(param.useAge || true);
                this.genderAtr = ko.observable(param.genderAtr || 0);
                this.limitAgeFrom = ko.observable(param.limitAgeFrom || null);
                this.limitAgeTo = ko.observable(param.limitAgeTo || null);
                this.ageCriteriaAtr = ko.observable(param.ageCriteriaAtr || 0);
                this.ageBaseYearAtr = ko.observable(param.ageBaseYearAtr || 0);
                this.ageBaseDates = ko.observable(param.ageBaseDates || null);
            }
        }

        export interface IGrantSingleDto {
            specialHolidayCode?: number;
            grantDaySingleType?: number;
            fixNumberDays?: number;
            makeInvitation?: number;
            holidayExclusionAtr?: number;
        }
        export class GrantSingleDto {
            specialHolidayCode: KnockoutObservable<number>;
            grantDaySingleType: KnockoutObservable<number>;
            fixNumberDays: KnockoutObservable<number>;
            makeInvitation: KnockoutObservable<number>;
            holidayExclusionAtr: KnockoutObservable<number>;
            constructor(param: IGrantSingleDto) {
                this.specialHolidayCode = ko.observable(param.specialHolidayCode || null);
                this.grantDaySingleType = ko.observable(param.grantDaySingleType || 0);
                this.fixNumberDays = ko.observable(param.fixNumberDays || null);
                this.makeInvitation = ko.observable(param.makeInvitation || 0);
                this.holidayExclusionAtr = ko.observable(param.holidayExclusionAtr || 0);
            }
        }
    }
}

