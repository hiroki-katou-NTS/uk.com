module kdp003.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import blockUI = nts.uk.ui.block;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {

        modeAdmin: KnockoutObservable<boolean> = ko.observable(true);
        modeEmployee: KnockoutObservable<boolean> = ko.observable(false);
        modeFingerVein: KnockoutObservable<boolean> = ko.observable(false); // mode tĩnh mạch

        // truong hợp Setting : Select company từ List ＆   Nhiều công ty được đăng ký
        selectCompFromListMode: KnockoutObservable<boolean> = ko.observable(false);
        companyList: KnockoutObservableArray<CompanyItemModel> = ko.observableArray([]);
        selectedCompanyCode: KnockoutObservable<string> = ko.observable('');

        // trường hợp Setting : Input CD company ＆  Nhiều công ty được đăng ký
        inputCompanyCodeMode: KnockoutObservable<boolean> = ko.observable(false);
        inputCompanyCode: KnockoutObservable<string> = ko.observable('000002');

        // trường hợp Chỉ có một công ty được đăng ký
        oneCompanyRegistered: KnockoutObservable<boolean> = ko.observable(true);
        companyCdAndName: KnockoutObservable<string> = ko.observable('truyentumanAsang|laytuserver');

        
        // trường hợp select vào label [ 一覧にない社員で打刻する ] PA4 trong component chọn employee
        selectIdMode: KnockoutObservable<boolean> = ko.observable(true);
        employeeCodeInput: KnockoutObservable<string> = ko.observable('employeeCode');

        // trường hợp chọn nhân viên trong list nhân viên PA5.
        selectNameMode: KnockoutObservable<boolean> = ko.observable(false);
        employeeCodeAndName: KnockoutObservable<string> = ko.observable('employeeCode đây');

        // lấy setting trong domain 共有打刻の打刻設定
        isPasswordRequired: KnockoutObservable<boolean> = ko.observable(true);
        password: KnockoutObservable<string> = ko.observable('');


        constructor() {
            let self = this;
            

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            if (self.modeAdmin) {
                service.getLogginSetting().done(function(data: Array<CompanyItemModel>) {
                    if (_.size(data) == 1) {
                        self.oneCompanyRegistered(true);
                        self.selectCompFromListMode(false);
                        self.inputCompanyCodeMode(false);
                        self.companyCdAndName(data[0].companyCode + ' ' + data[0].companyName);
                    
                    } else if (_.size(data) > 1) {
                        self.oneCompanyRegistered(false);
                        self.selectCompFromListMode(true);
                        self.inputCompanyCodeMode(false);
                        self.companyList(data);
                        if (data.length > 0) {
                            self.selectedCompanyCode(self.companyList()[0].companyCode);
                        }
                    }
                    
                    self.selectIdMode(true);
                    self.selectNameMode(false);
                    self.isPasswordRequired(true);
                    self.employeeCodeInput('');
                    self.password('');
                    
                });
                $("#employee-code-inp-2").focus();
                dfd.resolve();
            } else if (self.modeEmployee) {
                let objShare = nts.uk.ui.windows.getShared('fromScreenBToFScreen');
                self.oneCompanyRegistered(true);
                self.selectCompFromListMode(false);
                self.inputCompanyCodeMode(false);
                self.companyCdAndName(objShare.companyCdAndName);
                
                // setting mode cho employeeCode
                if (objShare.employeeSelected) {
                    self.selectIdMode(false);
                    self.selectNameMode(true);
                    self.employeeCodeAndName(objShare.employeeCodeAndName);
                    
                } else {
                    self.selectIdMode(true);
                    self.selectNameMode(false);
                    self.employeeCodeInput('');
                    
                    $("#employee-code-inp-2").focus();

                }

                // setting ẩn hiên input passWord
                if (objShare.passwordRequiredArt) {
                    self.isPasswordRequired(true);
                    self.password('');

                } else {
                    self.isPasswordRequired(false);
                    let currentDialog = nts.uk.ui.windows.getSelf();
                    currentDialog.setHeight(230);
                    $("#f20").css("margin-top", "14px");
                }
                
                // set focus
                if (objShare.employeeSelected != null && objShare.passwordRequiredArt == true) {
                    $("#password-input").focus();
                }

                if (objShare.employeeSelected != null && objShare.passwordRequiredArt == false) {
                    $("#f20_1").focus();
                }

                dfd.resolve();
            } else if (self.modeFingerVein) {
                // mode tĩnh mạch
                let objShare = nts.uk.ui.windows.getShared('fromScreenBToFScreen');
                self.oneCompanyRegistered(true);
                self.selectCompFromListMode(false);
                self.inputCompanyCodeMode(false);
                self.companyCdAndName(objShare.companyCdAndName);
                
                // setting mode cho employeeCode
                self.selectIdMode(true);
                self.selectNameMode(false);
                self.employeeCodeInput('');

                // setting  input passWord
                self.isPasswordRequired(true);
                self.password('');
                
                $("#employee-code-inp-2").focus();

                dfd.resolve();
            }
            return dfd.promise();
        }
        
        private companySelected() {
            var self = this;
            let objCompany = _.filter(self.companyList(), function(o) { return self.selectedCompanyCode() == o.companyCode });
            return objCompany;
        }
        
        private submitLogin() {
            var self = this;
            var submitData: any = {};
            let objCompany = self.companySelected();
            submitData.companyId = objCompany[0].companyId;
            submitData.companyCode = objCompany[0].companyCode;
            submitData.employeeCode = _.escape(self.employeeCodeInput());
            submitData.password = _.escape(self.password());
            blockUI.invisible();
            service.logginModeAdmin(submitData).done(function(messError: TimeStampInputLoginDto) {
                console.log(messError);
                if (!nts.uk.util.isNullOrUndefined(messError.successMsg) && !nts.uk.util.isNullOrEmpty(messError.successMsg)) {
                    nts.uk.ui.dialog.info({ messageId: messError.successMsg }).then(() => {
                        self.doSuccessLogin(messError);
                    });
                } else {
                    self.doSuccessLogin(messError);
                }
                blockUI.clear();
            }).fail(function(res: any) {
                blockUI.clear();
                console.log(res);
                if (nts.uk.util.isNullOrEmpty(res.messageId)) {
                    nts.uk.ui.dialog.alertError(res.message);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }
            });
        }
        
        private doSuccessLogin(messError) {
            var self = this;
            //Save LoginInfo
            nts.uk.characteristics.save("form3LoginInfo", { companyCode: _.escape(self.selectedCompanyCode()), employeeCode: _.escape(self.employeeCode()) }).done(function() {
                //nts.uk.request.jump("/view/ccg/008/a/index.xhtml", { screen: 'login' });
            });
        }

        cancelLogin() {
            var self = this;
        }

    }
    export class CompanyItemModel {
        companyId: string;
        companyCode: string;
        companyName: string;
        contractCd : string;
        selectUseOfName: boolean;
        fingerAuthStamp: boolean;
        icCardStamp: boolean
    }
    
    interface TimeStampInputLoginDto {
        showChangePass: boolean;
        msgErrorId: string;
        showContract: boolean;
        result: boolean;//・Result (True/False)
        em: any; //・Employees
        errorMessage: string;//·Error message
    }}

