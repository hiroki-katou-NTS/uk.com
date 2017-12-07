module nts.uk.com.view.cas013.a.viewmodel {

    export class screenModel {
        // Metadata
        isCreateMode: KnockoutObservable<boolean> = ko.observable(true);

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

        name: KnockoutObservable<string>;

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
                { headerText: 'コード', key: 'userId', width: 80 },
                { headerText: '名称', key: 'name', width: 70 },
                { headerText: '期間', key: 'datePeriod', width: 190 },
            ]);
            self.selectedRoleIndividual = ko.observable('');
            self.name = ko.observable('');
            self.dateValue = ko.observable({});


            self.selectedRoleType.subscribe((code: string) => {
                console.log('selected role type value ' + code);
                self.getDataByRoleType(code.toString());
            });

            self.selectedRole.subscribe((code: string) => {
                console.log('selected role value ' + code);
                self.selectRole(code.toString());
            });
            self.selectedRoleIndividual.subscribe((code: string) => {
                console.log('selected role Grant value ' + code);
                self.selectRoleGrant(code.toString());
            });


        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();

            new service.Service().getAllData('0').done(function(data: ObjectRole) {
                //list role type
                self.listRoleType(data.roleTypeDtos);
                //list role
                self.listRole(data.roleDtos);
                if (data.roleDtos.length > 0) { self.selectedRole(data.roleDtos[0].roleId); }
                //list role individual
                var items = [];
                for (let item of data.roleIndividualGrantDtos) { items.push(new RoleIndividual(item.userId, item.name, item.start, item.end)); }
                self.listRoleIndividual(items);
                if (data.roleIndividualGrantDtos.length > 0) {
                    self.selectedRoleIndividual(data.roleIndividualGrantDtos[0].userId);
                    self.name(data.roleIndividualGrantDtos[0].name);//role detail
                    self.dateValue(new datePeriod(data.roleIndividualGrantDtos[0].start, data.roleIndividualGrantDtos[0].end));
                }
            });
            return dfd.promise();

        }

        private getDataByRoleType(roleType: string): void {
            var self = this;
            if (roleType != '') {
                new service.Service().getAllData(roleType).done(function(data: ObjectRole) {
                    console.log(data);
                    self.listRole(data.roleDtos);
                    if (data.roleDtos.length > 0) { self.selectedRole(data.roleDtos[0].roleId); }
                    //list role individual
                    if (data.roleIndividualGrantDtos.length > 0) {
                        let temp = [];
                        for (let item of data.roleIndividualGrantDtos) { temp.push(new RoleIndividual(item.userId, item.name, item.start, item.end)); }
                        self.listRoleIndividual(temp);
                        self.selectedRoleIndividual(data.roleIndividualGrantDtos[0].userId);
                        //role detail
                        self.name(data.roleIndividualGrantDtos[0].name);
                        self.dateValue(new datePeriod(data.roleIndividualGrantDtos[0].start, data.roleIndividualGrantDtos[0].end));
                        //console.log(data);
                    } else {
                        self.listRoleIndividual([]);
                        self.selectedRoleIndividual('');
                        self.name('');
                        self.dateValue({});
                    }
                });
            } else {
                self.listRole([]);
                self.selectedRole('');
                self.listRoleIndividual([]);
                self.selectedRoleIndividual('');
                self.name('');
                self.dateValue({});
            }
        }

        private selectRole(roleId: string): void {
            var self = this;
            if (roleId != '') {
                new service.Service().getByRoleId(roleId).done(function(data: Array<RoleIndividual>) {
                    self.listRoleIndividual.removeAll();
                    if (data.length > 0) {
                        let items = []
                        for (let item of data) { items.push(new RoleIndividual(item.userId, item.name, item.start, item.end)); }
                        self.listRoleIndividual(items);
                        self.selectedRoleIndividual(data[0].userId);
                        self.name(data[0].name);
                        self.dateValue(new datePeriod(data[0].start, data[0].end));
                    } else {
                        self.selectedRoleIndividual('');
                        self.name('');
                        self.dateValue({});
                    }

                });
            } else {
                self.listRoleIndividual([]);
                self.selectedRoleIndividual('');
                self.name('');
                self.dateValue({});
            }
        }
        private selectRoleGrant(UserId: string): void {
            var self = this;
            var roleId = self.selectedRole();
            if (roleId != '' && UserId != '') {
                new service.Service().getByUserIdAndRoleId(roleId, UserId).done(function(data: RoleIndividual) {
                    if (data != null) {
                        self.name(data.name);
                        self.dateValue(new datePeriod(data.start, data.end));
                    } else {
                        self.name('');
                        self.dateValue({});
                    }

                });
            } else {
                self.name('');
                self.dateValue({});
            }

        }
        openBModal(): void {
            var self = this;
            let param = {
                roleType: 1,
                multiple: false
            };
            nts.uk.ui.windows.setShared("param", param);
            nts.uk.ui.windows.sub.modal("../b/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("userId");
                if (data != null) {
                    self.selectedRoleIndividual(data);
                    self.selectedRoleIndividual(data);
                }
            });
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
        name: string;
        start: string;
        end: string;
        datePeriod: string;

        constructor(userId: string, name: string, start: string, end: string) {
            this.userId = userId;
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

