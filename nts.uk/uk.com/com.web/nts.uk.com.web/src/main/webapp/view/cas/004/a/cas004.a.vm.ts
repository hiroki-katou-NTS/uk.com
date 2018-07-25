module nts.uk.com.view.cas004.a {
    import blockUI = nts.uk.ui.block;
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    export module viewModel {
        export class ScreenModel {

            userList: KnockoutObservableArray<model.UserDto>;
            comList: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<any>;
            companyCode: KnockoutObservable<any>;
            currentUserDto: KnockoutObservable<model.UserDto>;
            currentLoginID: KnockoutObservable<string>;
            currentMailAddress: KnockoutObservable<string>;
            currentUserName: KnockoutObservable<string>;
            currentPass: KnockoutObservable<string>;
            currentPeriod: KnockoutObservable<string>;
            currentPersonId: KnockoutObservable<string>;
            isSpecial: KnockoutObservable<boolean>;
            isMultiCom: KnockoutObservable<boolean>;

            constructor() {

                let self = this;
                self.userList = ko.observableArray([]);
                self.comList = ko.observableArray([]);
                self.currentCode = ko.observable('');
                self.currentCode.subscribe(function(codeChanged) {
                    if (codeChanged == "" || codeChanged == null || codeChanged == undefined)
                        return;
                    let currentUser = self.userList().filter(i => i.userID === codeChanged)[0];
                    self.currentUserDto(currentUser);
                    self.currentLoginID(currentUser.loginID);
                    self.currentMailAddress(currentUser.mailAddress);
                    self.currentUserName(currentUser.userName);
                    self.currentPass(currentUser.password);
                    self.currentPeriod(currentUser.expirationDate);
                    self.currentPersonId(currentUser.associatedPersonID);
                    self.isSpecial(currentUser.specialUser);
                    self.isMultiCom(currentUser.multiCompanyConcurrent);
                });
                self.columns = ko.observableArray([
                    { headerText: '', key: 'userID', width: 0, hidden: true },
                    { headerText: nts.uk.resource.getText('CAS004_13'), prop: 'loginID', width: '30%' },
                    { headerText: nts.uk.resource.getText('CAS004_14'), prop: 'userName', width: '70%' }
                ]);
                self.companyCode = ko.observable('');
                self.companyCode.subscribe(function(codeChanged) {
                    let dfd = $.Deferred();
                    if (codeChanged == undefined) {
                        return;
                    }
                    if (codeChanged == null || codeChanged == "No-Selection") {
                        self.companyCode("No-Selection");
                        self.loadUserGridList(null).done(function() {
                            dfd.resolve();
                        });
                        return;
                    }
                    self.companyCode(codeChanged);
                    let currentComId = self.comList().filter(i => i.companyCode === codeChanged)[0].companyId;
                    self.loadUserGridList(currentComId).done(function() {
                        dfd.resolve();
                    });
                });
                self.currentUserDto = ko.observable(null);
                self.currentLoginID = ko.observable('');
                self.currentMailAddress = ko.observable('');
                self.currentUserName = ko.observable('');
                self.currentPass = ko.observable('');
                self.currentPeriod = ko.observable('');
                self.currentPersonId = ko.observable(null);
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

            newMode(): void {
                let self = this;
                nts.uk.ui.errors.clearAll();
                blockUI.clear();
                self.resetData();
                errors.clearAll();
            }
            
            private resetData() {
                let self = this;
                self.currentCode('');
                self.currentUserDto(null);
                self.currentLoginID('');
                self.currentMailAddress('');
                self.currentUserName('');
                self.currentPass('');
                self.currentPeriod('');
                self.currentPersonId(null);
                self.isSpecial(false);
                self.isMultiCom(false);
            }

            register(): void {
                let self = this;

                $('.nts-input').trigger("validate");
                _.defer(() => {
                    if (!$('.nts-editor').ntsError("hasError")) {
                        let userId = self.currentCode();
                        let personalId = self.currentPersonId();
                        if (userId == "" || userId == null || userId == undefined) {
                            let userNew = new model.UserDto(null, self.currentLoginID(), self.currentUserName(), self.currentPass(), self.currentPeriod(), self.currentMailAddress(), personalId, self.isSpecial(), self.isMultiCom());
                            service.registerUser(userNew).done(function(userId) {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                    self.currentCode(userId);
                                });
                            }).fail((res) => {
                                if (res.messageId == "Msg_61") {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: [" " + self.currentLoginID() + " "] });
                                } else if (res.messageId == "Msg_716"){
                                    nts.uk.ui.dialog.alertError({ messageId: "Msg_716", messageParams: [nts.uk.resource.getText("CAS004_13")] });
                                } else {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                                }
                            }).always(() => {
                                blockUI.clear();
                            });
                        }
                        else {
                            let updateUser = new model.UserDto(self.currentCode(), self.currentLoginID(), self.currentUserName(), self.currentPass(), self.currentPeriod(), self.currentMailAddress(), personalId, self.isSpecial(), self.isMultiCom());
                            service.updateUser(updateUser).done(function(userId) {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                    self.currentCode(userId);
                                });
                            }).fail((res) => {
                                if (res.messageId == "Msg_61") {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: [" " + self.currentLoginID() + " "] });
                                } else if (res.messageId == "Msg_716"){
                                    nts.uk.ui.dialog.alertError({ messageId: "Msg_716", messageParams: [nts.uk.resource.getText("CAS004_13")] });
                                } else {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                                }
                            }).always(() => {
                                blockUI.clear();
                            });
                        }
                    };
                });
            }

            deleteItem(): void {
                let self = this;
                blockUI.invisible();
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let userId = self.currentUserDto().userID;
                    let deleteCmd = new model.DeleteCmd(userId, self.currentPersonId());
                    service.deleteUser(deleteCmd).done(function() {
                        blockUI.clear();
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadUserGridList(self.companyCode());
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        blockUI.clear();
                    });
                }).ifNo(function() {
                    blockUI.clear();
                    return;
                })
            }
            
            openDialogB() {
                let self = this;
                blockUI.invisible();
                let currentComId = null;
                let currentCom = self.comList().filter(i => i.companyCode === self.companyCode())[0];
                if (currentCom.companyCode === "No-Selection") {
                    currentComId = null;
                }
                else {
                    currentComId = currentCom.companyId;
                };
                windows.setShared('companyId', currentComId);
                windows.sub.modal('/view/cas/004/b/index.xhtml', { title: '' }).onClosed(function(): any {
                    errors.clearAll();
                    //get data from share window
                    var employee = windows.getShared('EMPLOYEE');
                    self.currentUserName(employee.employeeName);
                    self.currentPersonId(employee.personId);
                    blockUI.clear();
                });

            }

            private loadCompanyList(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.getCompanyImportList().done(function(companies) {
                    let comList: Array<model.CompanyImport> = [];
//                    comList.push(new model.CompanyImport("No-Selection", "�I���Ȃ�",null));
                    companies.forEach((item) => { comList.push(new model.CompanyImport(item.companyCode, item.companyName, item.companyId)) });
                    self.comList(comList);
                });
                return dfd.promise();
            }

            private loadUserGridList(cid): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                if (cid != null) {
                    service.getUserListByCid(cid).done(function(users) {
                        let userList: Array<model.UserDto> = [];
                        if (users.length != 0) {
                            users.forEach((item) => { userList.push(new model.UserDto(item.userID, item.loginID, item.userName, item.password, item.expirationDate, item.mailAddress, item.associatedPersonID, item.specialUser, item.multiCompanyConcurrent)) });
                            self.userList(userList);
                            self.currentCode(self.userList()[0].userID);
                            self.currentUserDto(self.userList()[0]);
                        }
                        else {
                            self.userList([]);
                            self.resetData();
                        }
                    });
                } else {
                    service.getAllUser().done(function(users) {
                        let userList: Array<model.UserDto> = [];
                        if (users.length != 0) {
                            users.forEach((item) => { userList.push(new model.UserDto(item.userID, item.loginID, item.userName, item.password, item.expirationDate, item.mailAddress, item.associatedPersonID, item.specialUser, item.multiCompanyConcurrent)) });
                            self.userList(userList);
                            self.currentCode(self.userList()[0].userID);
                            self.currentUserDto(self.userList()[0]);
                        }
                        else {
                            self.userList(userList);
                            self.resetData();
                        }
                    });
                }
                return dfd.promise();
            }
        }
    }
}