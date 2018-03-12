module nts.uk.pr.view.kmf001.h {
    export module viewmodel {
        import Enum = service.model.Enum;
        import SubstVacationSettingDto = service.model.SubstVacationSettingDto;
        import EmpSubstVacationDto = service.model.EmpSubstVacationDto;
        import EmploymentSettingDto = service.model.EmploymentSettingDto;
        import EmploymentSettingFindDto = service.model.EmploymentSettingFindDto;

        export class ScreenModel {
            //list component option
            selectedItem: KnockoutObservable<string>;
            listComponentOption: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
            
            // Service.
            service: service.Service;

            // Switch button data source
            vacationExpirationEnums: KnockoutObservableArray<Enum>;
            applyPermissionEnums: KnockoutObservableArray<Enum>;
            manageDistinctEnums: KnockoutObservableArray<Enum>;

            // Temp
            employmentList: KnockoutObservableArray<ItemModel>;
            selectedContractTypeCode: KnockoutObservable<string>;

            // Model
            settingModel: KnockoutObservable<SubstVacationSettingModel>;
            empSettingModel: KnockoutObservable<EmpSubstVacationModel>;
 
            isComManaged: KnockoutObservable<boolean>;
            hasEmp: KnockoutObservable<boolean>;
            isEmpManaged: KnockoutObservable<boolean>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;
            constructor() {
                var self = this;
                self.service = service.instance;
                self.selectedItem = ko.observable(null);
                self.alreadySettingList = ko.observableArray([]);
                
                self.vacationExpirationEnums = ko.observableArray([]);
                self.applyPermissionEnums = ko.observableArray([]);
                self.manageDistinctEnums = ko.observableArray([]);

                self.settingModel = ko.observable(null);
                self.empSettingModel = ko.observable(null);
                self.settingModel = ko.observable(
                    new SubstVacationSettingModel({
                        isManage: 0,
                        expirationDate: 0,
                        allowPrepaidLeave: 0
                    }));
                self.empSettingModel = ko.observable(
                    new EmpSubstVacationModel({
                        contractTypeCode: "",
                        isManage: 0,
                        expirationDate: 0,
                        allowPrepaidLeave: 0
                    }));

                self.isComManaged = ko.computed(function() {
                    return self.settingModel().isManage() == 1;
                });

                self.hasEmp = ko.observable(true);
                self.saveDisable = ko.observable(true);
                self.isEmpManaged = ko.computed(function() {
                    return self.hasEmp() && self.empSettingModel().isManage() == 1;
                });

                self.selectedContractTypeCode = ko.observable('');
                self.selectedItem.subscribe(function(data: string) {
                    if (data) {
                        self.empSettingModel().contractTypeCode(data);
                        self.loadEmpSettingDetails(data);
                        self.hasEmp(true);
                        self.saveDisable(true);
                    } else {
                        self.hasEmp(false);
                        self.saveDisable(false);
                    }
                });
                
                //list Emp
                self.listComponentOption = {
                    isShowAlreadySet: true,
                    isMultiSelect: false, 
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: this.selectedItem,
                    isDialog: false,
                    alreadySettingList:  self.alreadySettingList
                };
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
                $.when(self.loadVacationExpirationEnums(), self.loadApplyPermissionEnums(), self.loadManageDistinctEnums(),self.loadEmploymentList()).done(function() {
                    self.loadComSettingDetails();
                    self.selectedItem(self.employmentList()[0].code);
                    $('#company-manage').focus();
                    dfd.resolve();
                });
                return dfd.promise();
            }
            private checkComManaged(): void {
                let self = this;
                let dfd = $.Deferred();
                $('#left-content').ntsListComponent(this.listComponentOption).done(function() {
                    if (!$('#left-content').getDataList() || $('#left-content').getDataList().length == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_146", messageParams: [] });
                        self.hasEmp(false);
                        dfd.resolve();
                    }
                    $('#employment-manage').focus();
                });
            }
            private loadEmploymentList(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                //get list employment
                self.alreadySettingList([]);
                self.service.findAllByEmployment().done((data: Array<string>) => {
                    for (let empContractTypeCode of data) {
                        self.alreadySettingList.push({ code: empContractTypeCode, isAlreadySetting: true });
                    }
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
                    nts.uk.ui.dialog.alertError(res.message);
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
                this.service.findComSetting().done(function(res: SubstVacationSettingDto) {
                    if (res) {
                        self.settingModel().isManage(res.isManage);
                        self.settingModel().expirationDate(res.expirationDate);
                        self.settingModel().allowPrepaidLeave(res.allowPrepaidLeave);
                    } else {
                        self.settingModel().isManage(self.manageDistinctEnums()[0].value);
                        self.settingModel().expirationDate(self.vacationExpirationEnums()[0].value);
                        self.settingModel().allowPrepaidLeave(self.applyPermissionEnums()[0].value);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            private loadEmpSettingDetails(contractTypeCode: string): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.clearErrorEmpSetting();
                this.service.findEmpSetting(contractTypeCode).done(function(res: EmpSubstVacationDto) {
                    if (res) {
                        self.empSettingModel().contractTypeCode(res.contractTypeCode);
                        self.empSettingModel().isManage(res.isManage);
                        self.empSettingModel().expirationDate(res.expirationDate);
                        self.empSettingModel().allowPrepaidLeave(res.allowPrepaidLeave);
                    } else {
                        self.empSettingModel().isManage(self.manageDistinctEnums()[0].value);
                        self.empSettingModel().expirationDate(self.vacationExpirationEnums()[0].value);
                        self.empSettingModel().allowPrepaidLeave(self.applyPermissionEnums()[0].value);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.messageId);
                });
                return dfd.promise();
            }

            public saveComSetting(): void {
                let self = this;
                if (!self.validateComSetting()) {
                    return;
                }
                this.service.saveComSetting(self.settingModel().toSubstVacationSettingDto()).done(function() {
                    // Msg_15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.loadComSettingDetails();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    });
            }

            public saveEmpSetting(): void {
                let self = this;
                let dfd = $.Deferred();
                if (!self.validateEmpSetting()) {
                    return;
                }
                this.service.saveEmpSetting(self.empSettingModel().toEmpSubstVacationDto()).done(function() {
                    self.alreadySettingList.push({ "code": self.selectedItem(), "isAlreadySetting": true });
                    self.loadEmpSettingDetails(self.selectedItem());
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                })
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

            private validateEmpSetting(): boolean {
                let self = this;
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
                super(new SubstVacationSettingDto(dto.isManage, dto.expirationDate, dto.allowPrepaidLeave));
                this.contractTypeCode = ko.observable(dto.contractTypeCode);
            }

            public toEmpSubstVacationDto(): EmpSubstVacationDto {
                return new EmpSubstVacationDto(this.contractTypeCode(),
                    new SubstVacationSettingDto(this.isManage(), this.expirationDate(), this.allowPrepaidLeave()));
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