module nts.uk.com.view.cas004.a {
    import blockUI = nts.uk.ui.block;
    export module viewModel {
        export class ScreenModel {

            userList: KnockoutObservableArray<model.UserDto>;
            comList: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;
            companyCode: KnockoutObservable<any>;
            currentLoginID: KnockoutObservable<string>;
            currentMailAddress: KnockoutObservable<string>;
            currentUserName: KnockoutObservable<string>;
            currentPass: KnockoutObservable<string>;
            currentPeriod: KnockoutObservable<string>;
            isSpecial: KnockoutObservable<boolean>;
            isMultiCom: KnockoutObservable<boolean>;

            constructor() {

                let self = this;
                self.userList = ko.observableArray([]);
                self.comList = ko.observableArray([]);
                self.currentCode = ko.observable('');
                self.currentCode.subscribe(function(codeChanged) {
                    let currentUser = self.userList().filter(i => i.loginID === codeChanged)[0];
                    self.currentLoginID(codeChanged);
                    self.currentMailAddress(currentUser.loginID);
                    self.currentUserName(currentUser.userName);
                    self.currentPass(currentUser.password);
                    self.currentPeriod(currentUser.expirationDate);
                    self.isSpecial(currentUser.specialUser);
                    self.isMultiCom(currentUser.multiCompanyConcurrent);
                });
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CAS004_13'), prop: 'loginID', width: '30%' },
                    { headerText: nts.uk.resource.getText('CAS004_14'), prop: 'userName', width: '70%' }
                ]);
                self.companyCode = ko.observable('');
                self.currentLoginID = ko.observable('');
                self.currentMailAddress = ko.observable('');
                self.currentUserName = ko.observable('');
                self.currentPass = ko.observable('');
                self.currentPeriod = ko.observable('');
                self.isSpecial = ko.observable(false);
                self.isMultiCom = ko.observable(false);

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.loadCompanyList();
                self.loadUserGridList(null);
                dfd.resolve();
                return dfd.promise();
            }

            reset(): void {
                let self = this;
                nts.uk.ui.errors.clearAll();
                blockUI.clear();
                self.currentLoginID('');
                self.currentMailAddress('');
                self.currentUserName('');
                self.currentPass('');
                self.currentPeriod();
                self.isSpecial(false);
                self.isMultiCom(false);
            }

            register(): void {
                let self = this;

                $('.nts-input').trigger("validate");
                _.defer(() => {
                    if (!$('.nts-editor').ntsError("hasError")) {

                    }
                });
            }

            deleteItem(): void {
            }

            private loadCompanyList() {
                let self = this;
                service.getCompanyImportList().done(function(companies) {
                    let comList: Array<model.CompanyImport> = [];
                    companies.forEach((item) => { comList.push(new model.CompanyImport(item.companyCode, item.companyName, item.companyId)) });
                    self.comList(comList);
                });
            }

            private loadUserGridList(cid) {
                let self = this;
                if (cid != null) {
                    service.getUserList(cid).done(function(users) {
                        let userList: Array<model.UserDto> = [];
                        users.forEach((item) => { userList.push(new model.UserDto(item.loginID, item.userName, item.password, item.expirationDate, item.mailAddress, item.personID, item.specialUser, item.multiCompanyConcurrent)) });
                        self.userList(userList);
                        self.currentCode(self.userList()[0].loginID);
                    });
                } else {
                    service.getAllUser().done(function(users) {
                        let userList: Array<model.UserDto> = [];
                        users.forEach((item) => { userList.push(new model.UserDto(item.loginID, item.userName, item.password, item.expirationDate, item.mailAddress, item.personID, item.specialUser, item.multiCompanyConcurrent)) });
                        self.userList(userList);
                        self.currentCode(self.userList()[0].loginID);
                    });
                }

            }
        }
    }
}