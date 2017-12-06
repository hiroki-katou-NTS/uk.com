module nts.uk.com.view.cas012.a.viewmodel {
    export class ScreenModel {
        //ComboBOx RollType
        listRoleType: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedRoleType: KnockoutObservable<number> = ko.observable(null);
        
        //ComboBox Company
        listCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCompany: KnockoutObservable<string> = ko.observable('');
        
        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<IRoleIndividual> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any> = ko.observable();
        
        //Date time picker
        startDate: KnockoutObservable<string> = ko.observable('');
        endDate: KnockoutObservable<string> = ko.observable('');
        userName: KnockoutObservable<string> = ko.observable('');
        
        //Check Create
        isCreate: KnockoutObservable<boolean> = ko.observable(false);;
        selectRoleIndividual: KnockoutObservable<RoleIndividual>;
        userID: KnockoutObservable<string> = ko.observable('');

        constructor() {
            var self = this;
            self.columns = ko.observableArray([
                { headerText: 'GUID', key: 'GUID', width: 100, hidden: true },
                { headerText: 'コード', key: 'userID', width: 100 },
                { headerText: '名称', key: 'userName', width: 150 },
                { headerText: '説明', key: 'datePeriod', width: 150 }
            ]);
            self.selectRoleIndividual = ko.observable(new RoleIndividual({
                GUID: "",
                companyID: "",
                roleType: 0,
                userID: "",
                userName: "",
                startValidPeriod: "",
                endValidPeriod: "",
            }));

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getMetadata().done((data) => {
                self.listRoleType(data.enumRoleType);
                self.listCompany(data.listCompany);
                // Select first item
                self.selectedRoleType(data.enumRoleType[0].value);
                self.selectedCompany(data.listCompany[0].companyId);

                self.getData().done(() => {
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }

        private getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAll(self.selectedRoleType(), self.selectedCompany()).done((data: any) => {
                _.forEach(data.listGrantDto, (item: IRoleIndividual) => {
                    item.GUID = nts.uk.util.randomId();
                });
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

        deleteBtn() {
            
        }
    }


    export interface IRoleIndividual {
        GUID: string;
        companyID: string;
        roleType: number;
        userID: string;
        userName: string;
        startValidPeriod: string;
        endValidPeriod: string;
    }

    export class RoleIndividual {
        GUID: string;
        userID: KnockoutObservable<string>;
        roleType: KnockoutObservable<number>;
        companyID: KnockoutObservable<string>;
        datePeriod: KnockoutObservable<string>;
        startValidPeriod: KnockoutObservable<string>;
        endValidPeriod: KnockoutObservable<string>;
        
        constructor(param: IRoleIndividual) {
            this.GUID = param.GUID || nts.uk.util.randomId();
            this.userID = ko.observable(param.userID);
            this.roleType = ko.observable(param.roleType);
            this.companyID = ko.observable(param.companyID);
            this.startValidPeriod = ko.observable(param.startValidPeriod);
            this.endValidPeriod = ko.observable(param.endValidPeriod);

        }
    }
}

