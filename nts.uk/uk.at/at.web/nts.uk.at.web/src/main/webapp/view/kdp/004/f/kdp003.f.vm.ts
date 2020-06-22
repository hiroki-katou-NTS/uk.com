module kdp003.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import permision = service.getCurrentEmpPermision;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;

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
        companyCdAndName: KnockoutObservable<string> = ko.observable('companyCdAndName');

        // trường hợp select vào label [ 一覧にない社員で打刻する ] PA4 trong component chọn employee
        selectIdMode: KnockoutObservable<boolean> = ko.observable(false);
        employeeCodeInput: KnockoutObservable<string> = ko.observable('employeeCode');

        // trường hợp chọn nhân viên trong list nhân viên PA5.
        selectNameMode: KnockoutObservable<boolean> = ko.observable(true);
        employeeCodeView: KnockoutObservable<string> = ko.observable('employeeCode đây');

        // lấy setting trong domain 共有打刻の打刻設定
        isPasswordRequired: KnockoutObservable<boolean> = ko.observable(false);
        password: KnockoutObservable<string> = ko.observable('');


        constructor() {
            let self = this;

            if (self.modeAdmin) { }
            else if (self.modeEmployee) { }

            if (!self.isPasswordRequired()) {
                var currentDialog = nts.uk.ui.windows.getSelf();
                currentDialog.setHeight(230);
                $("#f20").css("margin-top","14px");

            }

        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        submitLogin() {
            var self = this;

        }

        cancelLogin() {
            var self = this;
        }

    }
    export class CompanyItemModel {
        companyId: string;
        companyCode: string;
        companyName: string;
    }}

