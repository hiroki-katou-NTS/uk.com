module nts.uk.pr.view.kmf001.h {
    export module viewmodel {
        import Enum = service.model.Enum;
        import SubstVacationSettingDto = service.model.SubstVacationSettingDto;
        import EmpSubstVacationDto = service.model.EmpSubstVacationDto;

        export class ScreenModel {

            // Service.
            service: service.Service;

            // Switch button data source
            vacationExpirationEnums: KnockoutObservableArray<Enum>;
            applyPermissionEnums: KnockoutObservableArray<Enum>;
            manageDistinctEnums: KnockoutObservableArray<Enum>;

            // Model
            settingModel: KnockoutObservable<SubstVacationSettingModel>;
            empSettingModel: KnockoutObservable<EmpSubstVacationModel>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.service = service.instance;

                self.vacationExpirationEnums = ko.observableArray([]);
                self.applyPermissionEnums = ko.observableArray([]);
                self.manageDistinctEnums = ko.observableArray([]);

                self.settingModel = ko.observable(null);
                self.empSettingModel = ko.observable(null);
            }

            /**
             * Start page.
             */
            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                // Load enums
                $.when(self.loadVacationExpirationEnums(), self.loadApplyPermissionEnums(), self.loadManageDistinctEnums()).done(function() {
                    //                    self.loadComSettingDetails();
                    //                    self.loadEmpSettingDetails("");
                    self.settingModel = ko.observable(
                        new SubstVacationSettingModel({
                            isManage: self.manageDistinctEnums()[0].value,
                            expirationDate: self.vacationExpirationEnums()[0].value,
                            allowPrepaidLeave: self.applyPermissionEnums()[0].value
                        }));
                    self.empSettingModel = ko.observable(
                        new EmpSubstVacationModel({
                            contractTypeCode: "",
                            setting: {
                                isManage: self.manageDistinctEnums()[0].value,
                                expirationDate: self.vacationExpirationEnums()[0].value,
                                allowPrepaidLeave: self.applyPermissionEnums()[0].value
                            }
                        }));
                    dfd.resolve();
                });

                return dfd.promise();
            }

            private loadVacationExpirationEnums(): JQueryPromise<Array<Enum>> {
                let self = this;

                let dfd = $.Deferred();

                this.service.getVacationExpirationEnum().done(function(res: Array<Enum>) {
                    self.vacationExpirationEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

                return dfd.promise();
            }

            private loadApplyPermissionEnums(): JQueryPromise<Array<Enum>> {
                let self = this;

                let dfd = $.Deferred();

                this.service.getApplyPermissionEnum().done(function(res: Array<Enum>) {
                    self.applyPermissionEnums(res);
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

                this.service.findComSetting().done(function(res: SubstVacationSettingDto) {
                    self.settingModel(new SubstVacationSettingModel(res));
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

                return dfd.promise();
            }

            private loadEmpSettingDetails(contractTypeCode: string): JQueryPromise<any> {
                let self = this;

                let dfd = $.Deferred();

                this.service.findEmpSetting(contractTypeCode).done(function(res: EmpSubstVacationDto) {
                    self.empSettingModel(new EmpSubstVacationModel(res));
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

                return dfd.promise();
            }

            public back(): void {
                nts.uk.request.jump("/view/kmf/001/a/index.xhtml", {});
            }

            public saveComSetting(): void {
                let self = this;
                let dfd = $.Deferred();

                if (!self.validateComSetting()) {
                    return;
                }

                this.service.saveComSetting(self.settingModel().toSubstVacationSettingDto()).done(function() {
                    // Msg_15
                    nts.uk.ui.dialog.alert("登録しました。");
                    // nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                    dfd.resolve();
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

                this.service.saveEmpSetting(self.empSettingModel().toEmpSubstVacationDto()).done(function() {
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
//
//                $('input').ntsEditor('validate');
//
//                if ($('.nts-input').ntsError('hasError')) {
//                    return false;
//                }

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

//                $('input').ntsEditor('validate');
//
//                if ($('.nts-input').ntsError('hasError')) {
//                    return false;
//                }

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
        export class SubstVacationSettingModel {
            isManage: KnockoutObservable<number>;
            expirationDate: KnockoutObservable<number>;
            allowPrepaidLeave: KnockoutObservable<number>;

            constructor(dto: SubstVacationSettingDto) {
                this.isManage = ko.observable(dto.isManage);
                this.expirationDate = ko.observable(dto.expirationDate);
                this.allowPrepaidLeave = ko.observable(dto.allowPrepaidLeave);
            }

            public toSubstVacationSettingDto(): SubstVacationSettingDto {
                return new SubstVacationSettingDto(this.isManage(), this.expirationDate(), this.allowPrepaidLeave());
            }
        }

        export class EmpSubstVacationModel extends SubstVacationSettingModel {
            contractTypeCode: KnockoutObservable<string>;

            constructor(dto: EmpSubstVacationDto) {
                super(dto.setting);
                this.contractTypeCode = ko.observable(dto.contractTypeCode);
            }

            public toEmpSubstVacationDto(): EmpSubstVacationDto {
                return new EmpSubstVacationDto(this.contractTypeCode(),
                    new SubstVacationSettingDto(this.isManage(), this.expirationDate(), this.allowPrepaidLeave()));
            }
        }
    }
}