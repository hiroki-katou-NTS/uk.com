module nts.uk.pr.view.kmf001.j {
    export module viewmodel {
        import Enum = service.model.Enum;
        import SixtyHourVacationSettingDto = service.model.SixtyHourVacationSettingDto;
        import Emp60HourVacationDto = service.model.Emp60HourVacationDto;

        export class ScreenModel {

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
            empSettingModel: KnockoutObservable<Emp60HVacationModel>;

            // 
            isComManaged: KnockoutObservable<boolean>;
            hasEmp: KnockoutObservable<boolean>;
            isEmpManaged: KnockoutObservable<boolean>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.service = service.instance;

                self.timeDigestiveUnitEnums = ko.observableArray([]);
                self.sixtyHourExtraEnums = ko.observableArray([]);
                self.manageDistinctEnums = ko.observableArray([]);

                self.settingModel = ko.observable(null);
                self.empSettingModel = ko.observable(null);
                self.settingModel = ko.observable(
                    new SixtyHourVacationSettingModel({
                        isManage: 0,
                        digestiveUnit: 0,
                        sixtyHourExtra: 0
                    }));
                self.empSettingModel = ko.observable(
                    new Emp60HVacationModel({
                        contractTypeCode: "",
                        isManage: 0,
                        digestiveUnit: 0,
                        sixtyHourExtra: 0
                    }));

                self.isComManaged = ko.computed(function() {
                    return self.settingModel().isManage() == 1;
                }, self);

                self.hasEmp = ko.computed(function() {
                    // TODO: count emp
                    return true;
                }, self);

                self.isEmpManaged = ko.computed(function() {
                    return self.hasEmp && self.empSettingModel().isManage() == 1;
                }, self);

                self.selectedContractTypeCode = ko.observable('');
                self.selectedContractTypeCode.subscribe(function(data: string) {
                    self.empSettingModel().contractTypeCode(data);
                    self.loadEmpSettingDetails(data);
                });
            }

            /**
             * Start page.
             */
            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                self.employmentList = ko.observableArray<ItemModel>([]);
                for (let i = 1; i < 9; i++) {
                    self.employmentList.push(new ItemModel('0' + i, '基本給', i % 3 === 0));
                }

                // Load enums
                $.when(self.loadTimeDigestiveUnitEnums(), self.loadSixtyHourExtraEnums(), self.loadManageDistinctEnums()).done(function() {
                    self.loadComSettingDetails();
                    self.selectedContractTypeCode(self.employmentList()[0].code);
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
                    nts.uk.ui.dialog.alert(res.message);
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
                    nts.uk.ui.dialog.alert(res.message);
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
                    nts.uk.ui.dialog.alert(res.message);
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

                    nts.uk.ui.dialog.alert(res.message);
                });

                return dfd.promise();
            }

            private loadEmpSettingDetails(contractTypeCode: string): JQueryPromise<any> {
                let self = this;

                let dfd = $.Deferred();

                self.clearErrorEmpSetting();

                this.service.findEmpSetting(contractTypeCode).done(function(res: Emp60HourVacationDto) {
                    if (res) {
                        self.empSettingModel().contractTypeCode(res.contractTypeCode);
                        self.empSettingModel().isManage(res.isManage);
                        self.empSettingModel().digestiveUnit(res.digestiveUnit);
                        self.empSettingModel().sixtyHourExtra(res.sixtyHourExtra);
                    } else {
                        self.empSettingModel().isManage(self.manageDistinctEnums()[0].value);
                        self.empSettingModel().digestiveUnit(self.timeDigestiveUnitEnums()[0].value);
                        self.empSettingModel().sixtyHourExtra(self.sixtyHourExtraEnums()[0].value);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

                return dfd.promise();
            }

            public checkComManaged(): void {
                let self = this;

                if (!self.isComManaged()) {
                    // Show msg #Msg_146
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_146')).then(function() {
                        $("#sidebar").ntsSideBar("active", 0);
                    });
                }
            }

            public back(): void {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml", {});
            }

            public saveComSetting(): void {
                let self = this;

                if (!self.validateComSetting()) {
                    return;
                }

                this.service.saveComSetting(self.settingModel().toSixtyHourVacationSettingDto()).done(function() {
                    // Msg_15
                    nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }

            public saveEmpSetting(): void {
                let self = this;
                let dfd = $.Deferred();

                if (!self.validateEmpSetting()) {
                    return;
                }

                this.service.saveEmpSetting(self.empSettingModel().toEmp60HourVacationDto()).done(function() {
                    // Msg_15
                    nts.uk.ui.dialog.alert("登録しました。");
                    // nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }

            private validateComSetting(): boolean {
                let self = this;

                // Have no input
                // $('input').ntsEditor('validate');
                //
                // if ($('.nts-input').ntsError('hasError')) {
                // return false;
                // }

                return true;
            }

            private clearErrorComSetting(): void {
                if (!$('.nts-input').ntsError('hasError')) {
                    return;
                }

                $('input').ntsError('clear');
            }

            private validateEmpSetting(): boolean {
                let self = this;

                // Have no input
                // $('input').ntsEditor('validate');
                //
                // if ($('.nts-input').ntsError('hasError')) {
                // return false;
                // }

                return true;
            }

            private clearErrorEmpSetting(): void {
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