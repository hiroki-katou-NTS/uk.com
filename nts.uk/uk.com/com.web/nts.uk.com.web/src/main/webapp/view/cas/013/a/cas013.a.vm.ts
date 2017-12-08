module nts.uk.com.view.cas013.a.viewmodel {

    export class screenModel {
        // Metadata
        isCreateMode: KnockoutObservable<boolean> = ko.observable(false);
        isSelectedUser: KnockoutObservable<boolean> = ko.observable(false);
        

        //ComboBOx RollType
        listRoleType: KnockoutObservableArray<RollType>;
        selectedRoleType: KnockoutObservable<string>;
        //list Roll
        listRole: KnockoutObservableArray<Role>;
        selectedRole: KnockoutObservable<string>;
        columns: KnockoutObservableArray<NtsGridListColumn>;

        //list Role Individual Grant    
        listRoleIndividual: KnockoutObservableArray<RoleIndividual>;
        selectedRoleIndividual: KnockoutObservable<string>;
        columnsIndividual: KnockoutObservableArray<NtsGridListColumn>;

        userName: KnockoutObservable<string>;
        userId: KnockoutObservable<string>;

        //Date pick
        dateValue: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.listRoleType = ko.observableArray([]);
            self.listRole = ko.observableArray([]);
            self.selectedRoleType = ko.observable('0');
            self.selectedRole = ko.observable('');
            self.listRoleIndividual = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'roleId', hidden: true },
                { headerText: 'コード', key: 'roleCode', width: 80 },
                { headerText: '名称', key: 'name', width: 160 }
            ]);
            self.columnsIndividual = ko.observableArray([
                { headerText: '', key: 'userId', hidden: true },
                { headerText: 'コード', key: 'loginId', width: 80 },
                { headerText: '名称', key: 'name', width: 70 },
                { headerText: '期間', key: 'datePeriod', width: 190 },
            ]);
            self.selectedRoleIndividual = ko.observable('');
            self.userName = ko.observable('');
            self.userId = ko.observable('');
            self.dateValue = ko.observable({});


            self.selectedRoleType.subscribe((code: string) => {
                self.getRoles(code.toString());
                self.isCreateMode(false);
                self.isSelectedUser(false);
            });

            self.selectedRole.subscribe((code: string) => {
                self.selectRole(code.toString());
                self.isCreateMode(false);
                self.isSelectedUser(false);
            });
            self.selectedRoleIndividual.subscribe((code: string) => {
                self.selectRoleGrant(code.toString());
            });


        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            new service.Service().getRoleTypes().done(function(data: Array<RollType>) {
                self.listRoleType(data);
            });
            return dfd.promise();

        }

        private getRoles(roleType: string): void {
            var self = this;
            if (roleType != '') {
                new service.Service().getRole(roleType).done(function(data: any) {
                    if (data != null && data.length > 0) {
                        self.listRole(data);
                        self.selectedRole(data[0].roleId);
                    }
                    else {
                        self.listRole([]);
                        self.selectedRole('')
                    }
                });
            } else {
                self.listRole([]);
                self.selectedRole('');
            }
        }

        private selectRole(roleId: string): void {
            var self = this;
            if (roleId != '') {
                new service.Service().getRoleGrants(roleId).done(function(data: any) {
                    if (data != null && data.length > 0) {
                        let items = [];
                        for (let entry of data) {
                            items.push(new RoleIndividual(entry.userID, entry.loginID, entry.userName, entry.startValidPeriod, entry.endValidPeriod))
                        }
                        self.listRoleIndividual(items);
                        self.selectedRoleIndividual(items[0].userId);
                    } else {
                        self.listRoleIndividual([]);
                        self.selectedRoleIndividual('');
                        self.userName('');
                        self.dateValue({});
                    }
                });
            } else {
                self.listRoleIndividual([]);
                self.selectedRoleIndividual('');
                self.userName('');
                self.dateValue({});
            }
        }
        private selectRoleGrant(UserId: string): void {
            var self = this;
            var roleId = self.selectedRole();
            if (roleId != '' && UserId != '') {
                new service.Service().getRoleGrant(roleId, UserId).done(function(data: any) {
                    if (data != null) {
                        self.userName(data.userName);
                        self.dateValue(new datePeriod(data.startValidPeriod, data.endValidPeriod));
                        self.selectedRoleIndividual(UserId);
                        self.isCreateMode(false);
                    } 
                });
            } 
        }
        New(): void {
            var self = this;
            self.isCreateMode(true);
            self.selectedRoleIndividual('');
            self.userName('');
            self.dateValue({});
            nts.uk.ui.errors.clearAll();
        }
        openBModal(): void {
            var self = this;
            let param = {
                roleType: 1,
                multiple: false
            };
            nts.uk.ui.windows.setShared("param", param);
            nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("UserInfo");
                if (data != null) {
                    self.userName(data.decisionName);
                    self.userId(data.decisionUserID);
                    self.isSelectedUser(true);
                    self.selectRoleGrant(data.decisionUserID);
                }
            });
        }
        save(): void{
            var self = this;
            if(!nts.uk.util.isNullOrUndefined(self.selectedRoleType()) 
                    && !nts.uk.util.isNullOrUndefined(self.selectedRole())
                    && !nts.uk.util.isNullOrUndefined(self.dateValue().startDate)
                    && !nts.uk.util.isNullOrUndefined(self.dateValue().endDate)
                    && self.userName() != ''){
                if(self.isSelectedUser()){
                    console.log('save');
                    self.insert();
                }else{
                    console.log('upDate');
                }
            }else if(self.selectedRole() == ''){
                console.log('Chua Chon Role');
            }else if(self.userName() == ''){
                console.log('Chua Chon user');
            }else if(nts.uk.util.isNullOrUndefined(self.dateValue().startDate) || nts.uk.util.isNullOrUndefined(self.dateValue().endDate)){
                console.log('Chua Chon Ngay Thang');
            }else{
                console.log('Chua Chon day du thong tin');
            }
                
        }
        private insert(): void{
            var self = this;
            if((self.userId() != '') 
                && !nts.uk.util.isNullOrUndefined(self.dateValue().startDate)
                && !nts.uk.util.isNullOrUndefined(self.dateValue().endDate)
                && self.selectedRole() != ''
                && self.selectedRoleType() != ''){
                
            }
        }

    }



    class RollType {
        value: string;
        nameId: string;
        description: string;

        constructor(value: string, nameId: string, description: string) {
            this.value = value;
            this.nameId = nameId;
            this.description = description;
        }
    }
    class Role {
        roleId: string;
        roleCode: string;
        name: string;

        constructor(roleId: string, roleCode: string, name: string) {
            this.roleId = roleId;
            this.roleCode = roleCode;
            this.name = name;
        }
    }
    class RoleIndividual {
        userId: string;
        loginId: string;
        name: string;
        start: string;
        end: string;
        datePeriod: string;

        constructor(userId: string, loginId: string, name: string, start: string, end: string) {
            this.userId = userId;
            this.loginId = loginId;
            this.name = name;
            this.start = start;
            this.end = end;
            this.datePeriod = start + ' ~ ' + end;
        }
    }
    class ObjectRole {
        roleDtos: Array<Role>;
        roleIndividualGrantDtos: Array<RoleIndividual>;
        roleTypeDtos: Array<RollType>;
        constructor(roleDtos: Array<Role>, roleIndividualGrantDtos: Array<RoleIndividual>, roleTypeDtos: Array<RollType>) {
            this.roleDtos = roleDtos;
            this.roleIndividualGrantDtos = roleIndividualGrantDtos;
            this.roleTypeDtos = roleTypeDtos;

        }
    }
    class datePeriod {
        startDate: string;
        endDate: string;
        constructor(startDate: string, endDate: string) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

}

