module nts.uk.com.view.cas012.a.viewmodel {
    export class ScreenModel {
        //ComboBOx RollType
        listRoleType: KnockoutObservableArray<any>;
        selectedRoleType: KnockoutObservable<string>;
        //ComboBox Company
        listCompany: KnockoutObservableArray<any>;
        selectedCompany: KnockoutObservable<string>;
        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<RoleIndividual>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        //Date time picker
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        userName: KnockoutObservable<string>;
        //Check Create
        isCreate: KnockoutObservable<boolean>;
        selectRoleIndividual: KnockoutObservable<RoleIndividual>;
        userID : KnockoutObservable<string>; 
        constructor() {
            var self = this;
            self.listRoleType = ko.observableArray([]);
            self.listCompany = ko.observableArray([]);
            self.selectedRoleType = ko.observable('1');
            self.selectedCompany = ko.observable('1');
            this.listRoleIndividual = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'userID', width: 100 },
                { headerText: '名称', key: 'userName', width: 150 },
                { headerText: '説明', key: 'datePeriod', width: 150 }
            ]);
            self.currentCode = ko.observable();
            //Date time picker
            self.startDate = ko.observable('');
            self.endDate = ko.observable('');
            self.userName = ko.observable('');
            //check Create
            self.userID = ko.observable('');
            self.selectRoleIndividual = ko.observable(new RoleIndividual('', 0, '', '', ''));



        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAll(self.selectedCompany(), self.selectedRoleType()).done(function(data) {
                console.log(data);
                self.listRoleType(data.enumRoleType);
                self.listCompany(data.listCompany);
                self.listRoleIndividual(data.listGrantDto);
                dfd.resolve();
            });

            return dfd.promise();
        }
        openCAS012_B() {
            let self = this
            nts.uk.ui.windows.sub.modal("/view/cas/012/b/index.xhtml").onClosed(() => {
                let returnDataScreenB = nts.uk.ui.windows.getShared("UserInfo");
                if (returnDataScreenB !== undefined) {
                    self.userName(returnDataScreenB.decisionName);
                    self.userID(returnDataScreenB.decisionUserID);
                    console.log(returnDataScreenB);
                    nts.uk.ui.block.clear();
                }
                else {
                    nts.uk.ui.block.clear();
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
            self.userName(null);
            self.startDate('');
            self.endDate('');
            nts.uk.ui.errors.clearAll();
        }
        registryBtn() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                self.selectRoleIndividual().userID(self.userID());
                let roleIndividual = ko.mapping.toJS(self.selectRoleIndividual());
                service.create(roleIndividual).done((data) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
                });
            }
        }
        delete() {

        }
    }


    export class RoleIndividual {
        userID: KnockoutObservable<string>;
        roleType: KnockoutObservable<number>;
        companyID: KnockoutObservable<string>;
        datePeriod: KnockoutObservable<string>;
        startValidPeriod: KnockoutObservable<string>;
        endValidPeriod: KnockoutObservable<string>;

        constructor(userID: string, roleType: number, companyID: string, startValidPeriod: string, endValidPeriod: string) {
            this.userID = ko.observable(userID);
            this.roleType = ko.observable(roleType);
            this.companyID = ko.observable(companyID);
            this.startValidPeriod = ko.observable(startValidPeriod);
            this.endValidPeriod = ko.observable(endValidPeriod);

        }
    }
    export interface Screen {

        roleType: number;
        companyID: string;
        userID: string;
    }

}

