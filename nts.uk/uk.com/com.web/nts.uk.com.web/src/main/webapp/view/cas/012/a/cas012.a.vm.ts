module nts.uk.com.view.cas012.a.viewmodel {
    import block = nts.uk.ui.block;

    export class ScreenModel {

        // Metadata
        isCreate: KnockoutObservable<boolean> = ko.observable(false);

        //ComboBox RollType
        listRoleType: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedRoleType: KnockoutObservable<number> = ko.observable(null);

        //ComboBox Company
        listCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCompany: KnockoutObservable<string> = ko.observable('');

        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<RoleIndividualDto> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();
        selectRoleIndividual: KnockoutObservable<RoleIndividual>;

        // Valid Period
        startValidPeriod: KnockoutObservable<string> = ko.observable("");
        endValidPeriod: KnockoutObservable<string> = ko.observable("");
        datePeriod: KnockoutObservable<any> = ko.observable({});

        constructor() {
            var self = this;
            self.columns = ko.observableArray([
                { headerText: 'GUID', key: 'GUID', width: 100, hidden: true },
                { headerText: 'コード', key: 'loginID', width: 120, columnCssClass: "colStyle" },
                { headerText: '名称', key: 'userName', width: 150, columnCssClass: "colStyle" },
                { headerText: '説明', key: 'datePeriod', width: 230 }
            ]);
            self.selectRoleIndividual = ko.observable(self.buildNewRoleIndividual());

            self.startValidPeriod.subscribe((value) => {
                self.datePeriod().startDate = value;
                self.datePeriod.valueHasMutated();
            });
            self.endValidPeriod.subscribe((value) => { 
                self.datePeriod().endDate = value;
                self.datePeriod.valueHasMutated();
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getMetadata().done((data) => {
                if (data) {
                    self.listRoleType(data.enumRoleType);
                    self.listCompany(data.listCompany);
                    // Select first item
                    self.selectedRoleType(data.enumRoleType[0].value);
                    self.selectedCompany(data.listCompany[0].companyId);

                    self.getData().done(() => {
                        self.createSubscribe();
                        self.selectRoleByIndex(0);
                        
                    });
                } else {
                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                }
                dfd.resolve();
            });

            return dfd.promise();
        }

        private createSubscribe(): void {
            var self = this;
            self.selectedRoleType.subscribe((value) => {
                self.getData().done(() => {
                    self.selectRoleByIndex(0);
                });
            });
            self.selectedCompany.subscribe((value) => {
                self.getData().done(() => {
                    self.selectRoleByIndex(0);
                });
            });
            self.currentCode.subscribe((value) => {
                self.findRoleById(value);
            });
        }

        openCAS012_B() {
            let self = this
            nts.uk.ui.windows.sub.modal("/view/cas/012/b/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("UserInfo");
                if (data !== undefined) {
                    self.selectRoleIndividual().userID(data.decisionUserID);
                    self.selectRoleIndividual().userName(data.decisionName);
                }
            });
        }

        openCAS012_C() {
            let self = this
            nts.uk.ui.windows.sub.modal("/view/cas/012/c/index.xhtml").onClosed(() => {
                let returnDataScreenC = nts.uk.ui.windows.getShared("ReturnData");
                self.selectedCompany(returnDataScreenC.decisionCompanyID);
            });
        }

        createBtn() {
            let self = this;
            self.isCreate(true);
            self.currentCode("");
            self.selectRoleIndividual().userName("");
            self.datePeriod({});
            nts.uk.ui.errors.clearAll();
        }

        registryBtn() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                if (self.isCreate()) {
                    self.createRole();
                } else {
                    self.updateRole();
                }
            }
        }

        private createRole(): void {
            var self = this;
            var param: RoleIndividualGrantBaseCommand = new RoleIndividualGrantBaseCommand(self.selectRoleIndividual());
            param.roleType = self.selectedRoleType();
            param.companyID = self.selectedCompany();
            param.startValidPeriod = nts.uk.time.parseMoment(self.datePeriod().startDate).format();
            param.endValidPeriod = nts.uk.time.parseMoment(self.datePeriod().endDate).format();

            block.invisible();
            if (self.selectedRoleType() == 0) {
                nts.uk.ui.windows.sub.modal("/view/cas/012/c/index.xhtml").onClosed(() => {
                    let data = nts.uk.ui.windows.getShared("CAS012CResult"); 
                    param.setRoleAdminFlag = data.setRoleAdminFlag;
                    param.decisionCompanyID = data.decisionCompanyID;
                    if(!data.isCancel){ 
                        self.createRoleProcess(param);
                    }
                });
            } else {
                self.createRoleProcess(param); 
            }
        }

        private createRoleProcess(param: RoleIndividualGrantBaseCommand): void {
            var self = this;
            service.create(param).done((data: any) => {
                self.getData().done(() => {
                    self.selectRoleByKey(data.companyID, data.userID, data.roleType);
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                });
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
        }

        private updateRole(): void {
            var self = this;
            var param: RoleIndividualGrantBaseCommand = new RoleIndividualGrantBaseCommand(self.selectRoleIndividual());
            param.startValidPeriod = nts.uk.time.parseMoment(self.datePeriod().startDate).format();
            param.endValidPeriod = nts.uk.time.parseMoment(self.datePeriod().endDate).format();

            block.invisible();
            service.update(param).done(() => {
                console.time("Update");
                self.getData().done(() => {
                    self.selectRoleByKey(param.companyID, param.userID, param.roleType);
                });
                nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                console.timeEnd("Update");
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });
        }

        deleteBtn() {
            var self = this;
            if (!nts.uk.text.isNullOrEmpty(self.currentCode())) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    block.invisible();
                    console.time("Delete");
                    let param: RoleIndividualGrantBaseCommand = new RoleIndividualGrantBaseCommand(self.selectRoleIndividual());
                    param.startValidPeriod = nts.uk.time.parseMoment(self.datePeriod().startDate).format();
                    param.endValidPeriod = nts.uk.time.parseMoment(self.datePeriod().endDate).format();
                    service.deleteRoleIndividual(param).done(() => {
                        let index = _.findIndex(self.listRoleType(), ['GUID', self.currentCode()]);
                        index = _.min([self.listRoleType().length - 2, index]);
                        self.getData().done(() => {
                            self.selectRoleByIndex(index);
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                    }).always(() => {
                        block.clear();
                    });
                    console.timeEnd("Delete");
                })
            }

        }

        private getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAll(self.selectedRoleType(), self.selectedCompany()).done((data: any) => {
                let listGrantDto = _.map(data.listGrantDto, (item: IRoleIndividual) => {
                    return new RoleIndividualDto(item);
                });
                self.listRoleIndividual(listGrantDto);
                dfd.resolve(data);
            });
            return dfd.promise();
        }

        private buildNewRoleIndividual(): RoleIndividual {
            return new RoleIndividual({
                GUID: "",
                loginID: "",
                companyID: "",
                roleID: "",
                roleType: 0,
                userID: "",
                userName: "",
                startValidPeriod: "",
                endValidPeriod: "",
            });
        }

        private findRoleById(id: string): void {
            var self = this;
            nts.uk.ui.errors.clearAll();
            var selectedRole: RoleIndividualDto = _.find(self.listRoleIndividual(), ['GUID', id]);
            if (selectedRole !== undefined) {
                self.isCreate(false);
                self.selectRoleIndividual(new RoleIndividual(selectedRole));
                self.startValidPeriod(selectedRole.startValidPeriod);
                self.endValidPeriod(selectedRole.endValidPeriod);
            }
            else {
                self.isCreate(true);
                self.selectRoleIndividual(self.buildNewRoleIndividual());
                self.startValidPeriod("");
                self.endValidPeriod("");
            }
        }

        private selectRoleByKey(companyID: string, userID: string, roleType: number) {
            var self = this;
            nts.uk.ui.errors.clearAll();
            var selectedRole: RoleIndividualDto = _.find(self.listRoleIndividual(), (item) => {
                return item.companyID == companyID && item.userID == userID && item.roleType == roleType;
            });
            var GUID = (selectedRole) ? selectedRole.GUID : "";
            self.currentCode(GUID);
            self.findRoleById(GUID);
        }

        private selectRoleByIndex(index: number) {
            var self = this;
            var selectedRole: RoleIndividualDto = _.nth(self.listRoleIndividual(), index);
            var GUID = (selectedRole) ? selectedRole.GUID : "";
            self.currentCode(GUID);
            self.findRoleById(GUID);
        }
    }

    export interface IRoleIndividual {
        GUID: string;
        loginID: string
        companyID: string;
        roleID: string,
        roleType: number;
        userID: string;
        userName: string;
        startValidPeriod: string;
        endValidPeriod: string;
    }

    export class RoleIndividualDto implements IRoleIndividual {
        GUID: string;
        loginID: string
        companyID: string;
        roleID: string;
        roleType: number;
        userID: string;
        userName: string;
        startValidPeriod: string;
        endValidPeriod: string;
        datePeriod: string;

        constructor(param: IRoleIndividual) {
            this.GUID = param.GUID || nts.uk.util.randomId();
            this.loginID = (param.loginID);
            this.userID = (param.userID);
            this.roleID = param.roleID;
            this.roleType = (param.roleType);
            this.companyID = (param.companyID);
            this.userName = (param.userName);
            this.startValidPeriod = param.startValidPeriod;
            this.endValidPeriod = param.endValidPeriod;
            this.datePeriod = param.startValidPeriod + " ～ " + param.endValidPeriod;
        }
    }

    export class RoleIndividual {
        GUID: string;
        loginID: KnockoutObservable<string>;
        userID: KnockoutObservable<string>;
        userName: KnockoutObservable<string>;
        roleID: KnockoutObservable<string>;
        roleType: KnockoutObservable<number>;
        companyID: KnockoutObservable<string>;

        constructor(param: IRoleIndividual) {
            var self = this;
            this.GUID = param.GUID || nts.uk.util.randomId();
            this.userID = ko.observable(param.userID);
            this.userName = ko.observable(param.userName);
            this.roleID = ko.observable(param.roleID);
            this.roleType = ko.observable(param.roleType);
            this.companyID = ko.observable(param.companyID);
        }
    }

    export class RoleIndividualGrantBaseCommand {
        userID: string;
        roleID: string;
        roleType: number;
        companyID: string;
        startValidPeriod: string;
        endValidPeriod: string;
        setRoleAdminFlag: boolean = false;
        decisionCompanyID: string = "";

        constructor(data: RoleIndividual) {
            this.userID = data.userID();
            this.roleID = data.roleID();
            this.roleType = data.roleType();
            this.companyID = data.companyID();
        }
    }
}

