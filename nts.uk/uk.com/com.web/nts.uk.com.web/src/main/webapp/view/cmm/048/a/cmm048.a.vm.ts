module nts.uk.com.view.cmm048.a {

    import MainDto = nts.uk.com.view.cmm048.a.service.model.MainDto;
    import EmployeeDto = nts.uk.com.view.cmm048.a.service.model.EmployeeDto;
    import EmployeeInfoContactDto = nts.uk.com.view.cmm048.a.service.model.EmployeeInfoContactDto;
    import PersonContactDto = nts.uk.com.view.cmm048.a.service.model.PersonContactDto;
    import PasswordPolicyDto = nts.uk.com.view.cmm048.a.service.model.PasswordPolicyDto;
    import ComplexityDto = nts.uk.com.view.cmm048.a.service.model.ComplexityDto;
    import UserInfoUseMethodDto = nts.uk.com.view.cmm048.a.service.model.UserInfoUseMethodDto;
    import UseContactSettingDto = nts.uk.com.view.cmm048.a.service.model.UseContactSettingDto;
    import MailNoticeSetSaveCommand = nts.uk.com.view.cmm048.a.service.model.MailNoticeSetSaveCommand;

    export module viewmodel {

        export class ScreenModel {

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            mainModel: MainModel;

            constructor() {
                let _self = this;

                _self.mainModel = new MainModel();
                _self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("CMM048_4"), content: '.tab-content-1', enable: ko.observable(true), visible: _self.mainModel.editPassword },
                    { id: 'tab-2', title: nts.uk.resource.getText("CMM048_5"), content: '.tab-content-2', enable: ko.observable(true), visible: _self.mainModel.notSpecialUser }
                ]);
                _self.selectedTab = ko.observable('tab-1');
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.getData().done((data: MainDto) => {
                    _self.mainModel.updateData(data);
                    dfd.resolve();
                }).fail((res: any) => {
                    // Jump to CCG008 in case of error
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                        nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                    });
                    dfd.reject();
                });

                return dfd.promise();
            }

            /**
             * Set focus
             */
            public setInitialFocus(): void {
                $('#button-save').focus();
            }

            /**
             * Save
             */
            public save() {
                let _self = this;

                nts.uk.ui.block.grayout();
                let command: MailNoticeSetSaveCommand = {
                    isPasswordUpdate: _self.mainModel.editPassword(),
                    isContactUpdate: _self.mainModel.notSpecialUser(),
                    oldPassword: _self.mainModel.oldPassword(),
                    newPassword: _self.mainModel.newPassword(),
                    confirmNewPassword: _self.mainModel.confirmNewPassword(),
                    employeeInfoContact: _self.mainModel.employeeInfoContact.toDto(),
                    personContact: _self.mainModel.personContact.toDto(),
                    listUseContactSetting: _.map(_self.mainModel.listUseContactSetting(), item => item.toDto())
                }
                service.save(command)
                    .done((res: any) => {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                    })
                    .fail((err: any) => {
                        nts.uk.ui.block.clear();
                        _self.showMessageError(err);
                    });
            }

            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();

                // check error business exception
                if (!res.businessException) {
                    return;
                }

                // show error message
                if (Array.isArray(res.errors)) {
                     // close current error dialog
                        const buttonCloseDialog = $('#functions-area-bottom>.ntsClose');
                        if (!nts.uk.util.isNullOrEmpty(buttonCloseDialog)) {
                            buttonCloseDialog.click();
                        }
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }

        export class MainModel {
            editPassword: KnockoutObservable<boolean>;
            notSpecialUser: KnockoutObservable<boolean>;
            employee: EmployeeModel;
            employeeInfoContact: EmployeeInfoContactModel;
            personContact: PersonContactModel;
            passwordPolicy: PasswordPolicyModel;
            listUserInfoUseMethod: KnockoutObservableArray<UserInfoUseMethodModel>;
            listUseContactSetting: KnockoutObservableArray<UseContactSettingModel>;
            oldPassword: KnockoutObservable<string>;
            newPassword: KnockoutObservable<string>;
            confirmNewPassword: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.editPassword = ko.observable(false);
                _self.notSpecialUser = ko.observable(false);
                _self.employee = new EmployeeModel();
                _self.employeeInfoContact = new EmployeeInfoContactModel();
                _self.personContact = new PersonContactModel();
                _self.passwordPolicy = new PasswordPolicyModel();
                _self.listUserInfoUseMethod = ko.observableArray([]);
                _self.listUseContactSetting = ko.observableArray([]);
                _self.oldPassword = ko.observable('');
                _self.newPassword = ko.observable('');
                _self.confirmNewPassword = ko.observable('');
            }

            updateData(dto: MainDto) {
                let _self = this;
                _self.editPassword(dto.editPassword);
                _self.notSpecialUser(dto.notSpecialUser);
                if (!nts.uk.util.isNullOrUndefined(dto.employee)) _self.employee.updateData(dto.employee);
                if (!nts.uk.util.isNullOrUndefined(dto.employeeInfoContact)) _self.employeeInfoContact.updateData(dto.employeeInfoContact);
                if (!nts.uk.util.isNullOrUndefined(dto.personContact)) _self.personContact.updateData(dto.personContact);
                if (!nts.uk.util.isNullOrUndefined(dto.passwordPolicy)) _self.passwordPolicy.updateData(dto.passwordPolicy);
                _self.listUserInfoUseMethod(_.map(dto.listUserInfoUseMethod, dto => {
                    let model: UserInfoUseMethodModel = new UserInfoUseMethodModel();
                    model.updateData(dto);
                    return model;
                }));
                _self.listUseContactSetting(_.map(dto.listUseContactSetting, dto => {
                    let model: UseContactSettingModel = new UseContactSettingModel();
                    model.updateData(dto);
                    return model;
                }));
            }
        }

        export class EmployeeModel {
            employeeId: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.employeeCode = ko.observable("");
                _self.employeeName = ko.observable("");
            }

            updateData(dto: EmployeeDto) {
                let _self = this;
                _self.employeeId(dto.employeeId);
                _self.employeeCode(dto.employeeCode);
                _self.employeeName(dto.employeeName);
            }
        }

        export class EmployeeInfoContactModel {
            employeeId: KnockoutObservable<string>;
            mailAddress: KnockoutObservable<string>;
            mobileMailAddress: KnockoutObservable<string>;
            cellPhoneNo: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.mailAddress = ko.observable("");
                _self.mobileMailAddress = ko.observable("");
                _self.cellPhoneNo = ko.observable("");
            }

            updateData(dto: EmployeeInfoContactDto) {
                let _self = this;
                _self.employeeId(dto.employeeId);
                _self.mailAddress(dto.mailAddress);
                _self.mobileMailAddress(dto.mobileMailAddress);
                _self.cellPhoneNo(dto.cellPhoneNo);
            }

            toDto(): EmployeeInfoContactDto {
                let _self = this;
                let dto: EmployeeInfoContactDto = {
                    employeeId: _self.employeeId(),
                    mailAddress: _self.mailAddress(),
                    mobileMailAddress: _self.mobileMailAddress(),
                    cellPhoneNo: _self.cellPhoneNo()
                };
                return dto;
            }
        }

        export class PersonContactModel {
            personId: KnockoutObservable<string>;
            mailAddress: KnockoutObservable<string>;
            mobileMailAddress: KnockoutObservable<string>;
            cellPhoneNo: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.personId = ko.observable("");
                _self.mailAddress = ko.observable("");
                _self.mobileMailAddress = ko.observable("");
                _self.cellPhoneNo = ko.observable("");
            }

            updateData(dto: PersonContactDto) {
                let _self = this;
                _self.personId(dto.personId);
                _self.mailAddress(dto.mailAddress);
                _self.mobileMailAddress(dto.mobileMailAddress);
                _self.cellPhoneNo(dto.cellPhoneNo);
            }

            toDto(): PersonContactDto {
                let _self = this;
                let dto: PersonContactDto = {
                    personId: _self.personId(),
                    mailAddress: _self.mailAddress(),
                    mobileMailAddress: _self.mobileMailAddress(),
                    cellPhoneNo: _self.cellPhoneNo()
                };
                return dto;
            }
        }

        export class PasswordPolicyModel {
            haveLowestDigitsSetting: KnockoutObservable<boolean>;
            historyCountSetting: KnockoutObservable<boolean>;
            validityPeriodSetting: KnockoutObservable<boolean>;
            isUse: KnockoutObservable<boolean>;
            complexity: ComplexityModel;
            lowestDigits: KnockoutObservable<number>;
            historyCount: KnockoutObservable<number>;
            validityPeriod: KnockoutObservable<number>;

            textLowestDigits: KnockoutObservable<string>;
            textHistoryCount: KnockoutObservable<string>;
            textValidityPeriod: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                _self.haveLowestDigitsSetting = ko.observable(true);
                _self.historyCountSetting = ko.observable(true);
                _self.validityPeriodSetting = ko.observable(true);
               
                _self.isUse = ko.observable(true);
                _self.complexity = new ComplexityModel();
                _self.lowestDigits = ko.observable(null);
                _self.historyCount = ko.observable(null);
                _self.validityPeriod = ko.observable(null);
                _self.textLowestDigits = ko.observable("");
                _self.textHistoryCount = ko.observable("");
                _self.textValidityPeriod = ko.observable("");

                _self.lowestDigits.subscribe((v) => _self.textLowestDigits(nts.uk.text.format(nts.uk.resource.getText("CMM048_13"), v)));
                _self.lowestDigits.extend({ notify: 'always' });
                _self.historyCount.subscribe((v) => _self.textHistoryCount(nts.uk.text.format(nts.uk.resource.getText("CMM048_19"), v)));
                _self.historyCount.extend({ notify: 'always' });
                _self.validityPeriod.subscribe((v) => _self.textValidityPeriod(nts.uk.text.format(nts.uk.resource.getText("CMM048_21"), v)));
                _self.validityPeriod.extend({ notify: 'always' });
                _self.lowestDigits(0);
                _self.historyCount(0);
                _self.validityPeriod(0);
            }

            updateData(dto: PasswordPolicyDto) {
                let _self = this;
                _self.isUse(dto.isUse);
                _self.lowestDigits(dto.lowestDigits);
                if(dto.lowestDigits == 0){
                    _self.haveLowestDigitsSetting(false);
                  }
                 if(dto.historyCount == 0){
                    _self.historyCountSetting(false);
                  }
                 if(dto.validityPeriod == 0){
                    _self.validityPeriodSetting(false);
                  }
                _self.complexity.updateData(dto.complexity);
                _self.historyCount(dto.historyCount);
                _self.validityPeriod(dto.validityPeriod);
            }
        }

        export class ComplexityModel {
            alphabetDigit: KnockoutObservable<number>;
            numberOfDigits: KnockoutObservable<number>;
            numberOfChar: KnockoutObservable<number>;
            haveComplexitySetting: KnockoutObservable<boolean>;
            alAhabetDigitSetting: KnockoutObservable<boolean>;
            numberOfDigitsSetting: KnockoutObservable<boolean>;
            numberOfCharSetting: KnockoutObservable<boolean>;
            textAlphabetDigit: KnockoutObservable<string>;
            textNumberOfDigits: KnockoutObservable<string>;
            textNumberOfChar: KnockoutObservable<string>;

            constructor() {
                let _self = this;
                 _self.haveComplexitySetting = ko.observable(true);
                 _self.alAhabetDigitSetting = ko.observable(true);
                 _self.numberOfDigitsSetting = ko.observable(true);
                 _self.numberOfCharSetting = ko.observable(true);
                _self.alphabetDigit = ko.observable(null);
                _self.numberOfDigits = ko.observable(null);
                _self.numberOfChar = ko.observable(null);
                _self.textAlphabetDigit = ko.observable("");
                _self.textNumberOfDigits = ko.observable("");
                _self.textNumberOfChar = ko.observable("");

                _self.alphabetDigit.subscribe((v) => _self.textAlphabetDigit(nts.uk.text.format(nts.uk.resource.getText("CMM048_15"), v)));
                _self.alphabetDigit.extend({ notify: 'always' });
                _self.numberOfDigits.subscribe((v) => _self.textNumberOfDigits(nts.uk.text.format(nts.uk.resource.getText("CMM048_16"), v)));
                _self.numberOfDigits.extend({ notify: 'always' });
                _self.numberOfChar.subscribe((v) => _self.textNumberOfChar(nts.uk.text.format(nts.uk.resource.getText("CMM048_17"), v)));
                _self.numberOfChar.extend({ notify: 'always' });
                _self.alphabetDigit(0);
                _self.numberOfDigits(0);
                _self.numberOfChar(0);
            }

            updateData(dto: ComplexityDto) {
                let _self = this;
                if(dto.alphabetDigit == 0 && dto.numberOfDigits == 0 && dto.numberOfChar == 0){
                    _self.haveComplexitySetting(false);
                }
                if(dto.alphabetDigit == 0 ){
                    _self.alAhabetDigitSetting(false);
                }
                 if(dto.numberOfDigits == 0 ){
                    _self.numberOfDigitsSetting(false);
                }
                 if(dto.numberOfChar == 0 ){
                    _self.numberOfCharSetting(false);
                }
                _self.alphabetDigit(dto.alphabetDigit);
                _self.numberOfDigits(dto.numberOfDigits);
                _self.numberOfChar(dto.numberOfChar);
            }
        }

        export class UserInfoUseMethodModel {
            settingItem: KnockoutObservable<number>;
            selfEdit: KnockoutObservable<number>;
            settingUseMail: KnockoutObservable<number>;
            isNotUse: boolean;
            isUse: boolean;
            isPersonal: boolean;
            enableEdit: KnockoutObservable<boolean>;

            constructor() {
                let _self = this;
                _self.settingItem = ko.observable(0);
                _self.selfEdit = ko.observable(null);
                _self.settingUseMail = ko.observable(null);
                _self.enableEdit = ko.observable(null);

                _self.selfEdit.subscribe((v) => {
                    switch (v) {
                        case 0:
                            _self.enableEdit(false);
                            break;
                        case 1:
                            _self.enableEdit(true);
                            break;
                        default:
                            _self.enableEdit(false);
                    }
                });
                _self.selfEdit(0);
                _self.settingUseMail.subscribe((v) => {
                    switch (v) {
                        case 0:
                            _self.isNotUse = true;
                            _self.isUse = false;
                            _self.isPersonal = false;
                            break;
                        case 1:
                            _self.isNotUse = false;
                            _self.isUse = true;
                            _self.isPersonal = false;
                            break;
                        case 2:
                            _self.isNotUse = false;
                            _self.isUse = false;
                            _self.isPersonal = true;
                            break;
                        default:
                            _self.isNotUse = false;
                            _self.isUse = false;
                            _self.isPersonal = false;
                    }
                });
                _self.settingUseMail(2);
            }

            updateData(dto: UserInfoUseMethodDto) {
                let _self = this;
                _self.settingItem(dto.settingItem);
                _self.selfEdit(dto.selfEdit);
                _self.settingUseMail(dto.settingUseMail);
            }

            toDto(): UserInfoUseMethodDto {
                let _self = this;
                let dto: UserInfoUseMethodDto = {
                    settingItem: _self.settingItem(),
                    selfEdit: _self.selfEdit(),
                    settingUseMail: _self.settingUseMail()
                };
                return dto;
            }
        }

        export class UseContactSettingModel {
            employeeId: KnockoutObservable<string>;
            settingItem: KnockoutObservable<number>;
            useMailSetting: KnockoutObservable<boolean>;

            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.settingItem = ko.observable(0);
                _self.useMailSetting = ko.observable(true);
            }

            updateData(dto: UseContactSettingDto) {
                let _self = this;
                _self.employeeId(dto.employeeId);
                _self.settingItem(dto.settingItem);
                _self.useMailSetting(dto.useMailSetting);
            }

            toDto(): UseContactSettingDto {
                let _self = this;
                let dto: UseContactSettingDto = {
                    employeeId: _self.employeeId(),
                    settingItem: _self.settingItem(),
                    useMailSetting: _self.useMailSetting()
                };
                return dto;
            }
        }
    }
}