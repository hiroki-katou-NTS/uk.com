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
            empSettingModel: KnockoutObservable<SubstVacationSettingModel>;

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
                            isManage: self.manageDistinctEnums()[0].fieldName,
                            expirationDate: self.vacationExpirationEnums()[0].fieldName,
                            allowPrepaidLeave: self.applyPermissionEnums()[0].fieldName
                        }));
                    self.empSettingModel = ko.observable(
                        new EmpSubstVacationModel({
                            contractTypeCode: "",
                            setting: {
                                isManage: self.manageDistinctEnums()[0].fieldName,
                                expirationDate: self.vacationExpirationEnums()[0].fieldName,
                                allowPrepaidLeave: self.applyPermissionEnums()[0].fieldName
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

        }

        // Model class
        export class SubstVacationSettingModel {
            isManage: KnockoutObservable<string>;
            expirationDate: KnockoutObservable<string>;
            allowPrepaidLeave: KnockoutObservable<string>;

            constructor(dto: SubstVacationSettingDto) {
                this.isManage = ko.observable(dto.isManage);
                this.expirationDate = ko.observable(dto.expirationDate);
                this.allowPrepaidLeave = ko.observable(dto.allowPrepaidLeave);
            }
        }

        export class EmpSubstVacationModel extends SubstVacationSettingModel {
            contractTypeCode: KnockoutObservable<string>;

            constructor(dto: EmpSubstVacationDto) {
                super(dto.setting);
                this.contractTypeCode = ko.observable(dto.contractTypeCode);
            }
        }
    }
}