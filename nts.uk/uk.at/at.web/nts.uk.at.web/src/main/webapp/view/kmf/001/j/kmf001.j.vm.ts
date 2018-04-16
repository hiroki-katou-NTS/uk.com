module nts.uk.pr.view.kmf001.j {
    export module viewmodel {
        import Enum = service.model.Enum;
        import SixtyHourVacationSettingDto = service.model.SixtyHourVacationSettingDto;
        import Emp60HourVacationDto = service.model.Emp60HourVacationDto;

        export class ScreenModel {
            //list component option
            selectedItem: KnockoutObservable<string>;
            alreadySettingList: KnockoutObservableArray<any>;
            // Service.
            service: service.Service;

            // Switch button data source
            timeDigestiveUnitEnums: KnockoutObservableArray<Enum>;
            sixtyHourExtraEnums: KnockoutObservableArray<Enum>;
            manageDistinctEnums: KnockoutObservableArray<Enum>;

            // Temp
            employmentList: KnockoutObservableArray<ItemModel>;
            selectedContractTypeCode: KnockoutObservable<string>;

            // Model
            settingModel: KnockoutObservable<SixtyHourVacationSettingModel>;

            // 
            isComManaged: KnockoutObservable<boolean>;
            isEmpManaged: KnockoutObservable<boolean>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.service = service.instance;
                self.selectedItem = ko.observable(null);
                self.alreadySettingList = ko.observableArray([]);

                self.timeDigestiveUnitEnums = ko.observableArray([]);
                self.sixtyHourExtraEnums = ko.observableArray([]);
                self.manageDistinctEnums = ko.observableArray([]);

                self.settingModel = ko.observable(null);
                self.settingModel = ko.observable(
                    new SixtyHourVacationSettingModel({
                        isManage: 1,
                        digestiveUnit: 0,
                        sixtyHourExtra: 0
                    }));

                self.isComManaged = ko.computed(function() {
                    return self.settingModel().isManage() == 1;
                });

                self.selectedContractTypeCode = ko.observable('');
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.employmentList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 9; i++) {
                    self.employmentList.push(new ItemModel('0' + i, '基本給', i % 3 === 0));
                }
                // Load enums
                $.when(self.loadTimeDigestiveUnitEnums(),self.loadManageDistinctEnums(),self.loadSixtyHourExtraEnums()).done(function() {
                    self.loadComSettingDetails().done(function() {
                        $('#company_manage').focus();
                    });
                    self.selectedItem(self.employmentList()[0].code);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private loadTimeDigestiveUnitEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                this.service.getTimeDigestiveUnitEnum().done(function(res: Array<Enum>) {
                    self.timeDigestiveUnitEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadSixtyHourExtraEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                this.service.getSixtyHourExtraEnum().done(function(res: Array<Enum>) {
                    self.sixtyHourExtraEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadManageDistinctEnums(): JQueryPromise<Array<Enum>> {
                let self = this;
                let dfd = $.Deferred();
                this.service.getManageDistinctEnum().done(function(res: Array<Enum>) {
                    self.manageDistinctEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadComSettingDetails(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.clearErrorComSetting();
                this.service.findComSetting().done(function(res: SixtyHourVacationSettingDto) {
                    if (res) {
                        self.settingModel().isManage(res.isManage);
                        self.settingModel().digestiveUnit(res.digestiveUnit);
                        self.settingModel().sixtyHourExtra(res.sixtyHourExtra);
                    } else {
                        self.settingModel().isManage(self.manageDistinctEnums()[0].value);
                        self.settingModel().digestiveUnit(self.timeDigestiveUnitEnums()[0].value);
                        self.settingModel().sixtyHourExtra(self.sixtyHourExtraEnums()[0].value);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            public saveComSetting(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validateComSetting()) {
                    return;
                }
                
                nts.uk.ui.block.grayout();
                
                this.service.saveComSetting(self.settingModel().toSixtyHourVacationSettingDto()).done(function() {
                    // Msg_15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.loadComSettingDetails();
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            private validateComSetting(): boolean {
                let self = this;
                return true;
            }

            private clearErrorComSetting(): void {
                if (!$('.nts-input').ntsError('hasError')) {
                    return;
                }
                $('input').ntsError('clear');
            }

        }
        // Model class
        export class SixtyHourVacationSettingModel {
            isManage: KnockoutObservable<number>;
            digestiveUnit: KnockoutObservable<number>;
            sixtyHourExtra: KnockoutObservable<number>;

            constructor(dto: SixtyHourVacationSettingDto) {
                this.isManage = ko.observable(dto.isManage);
                this.digestiveUnit = ko.observable(dto.digestiveUnit);
                this.sixtyHourExtra = ko.observable(dto.sixtyHourExtra);
            }

            public toSixtyHourVacationSettingDto(): SixtyHourVacationSettingDto {
                return new SixtyHourVacationSettingDto(this.isManage(), this.digestiveUnit(), this.sixtyHourExtra());
            }
        }

        export class Emp60HVacationModel extends SixtyHourVacationSettingModel {
            contractTypeCode: KnockoutObservable<string>;

            constructor(dto: Emp60HourVacationDto) {
                super(new SixtyHourVacationSettingDto(dto.isManage, dto.digestiveUnit, dto.sixtyHourExtra));
                this.contractTypeCode = ko.observable(dto.contractTypeCode);
            }

            public toEmp60HourVacationDto(): Emp60HourVacationDto {
                return new Emp60HourVacationDto(this.contractTypeCode(),
                    new SixtyHourVacationSettingDto(this.isManage(), this.digestiveUnit(), this.sixtyHourExtra()));
            }
        }
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }
        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
        // Temp
        export class ItemModel {
            code: string;
            name: string;
            alreadySet: boolean;

            constructor(code: string, name: string, alreadySet: boolean) {
                this.code = code;
                this.name = name;
                this.alreadySet = alreadySet;
            }
        }
    }
}