module nts.uk.com.view.cas009.a.viewmodel {
    import ccg026 = nts.uk.com.view.ccg026.component;
    import ComponentModelCCG025 = nts.uk.com.view.ccg025.a.component.viewmodel.ComponentModel;

    const API = {
        hasRole: 'ctx/sys/auth/role/user/has/role/8',
        getRole: 'ctx/sys/auth/role/find/person/role',
        savePermission: 'ctx/com/screen/person/role/register',
        removePermission: 'ctx/com/screen/person/role/delete',
        permissionPersonInfo: 'ctx/pereg/functions/auth/find-with-role',
        checkPermission: 'ctx/com/screen/person/role/check'
    };

    @bean()
    class Cas009AViewModel extends ko.ViewModel {
        selectedRole: Role;
        listRole: KnockoutObservableArray<IRole>;
        enumRange: KnockoutObservableArray<EnumConstantDto>;

        component025: ComponentModelCCG025 = new ComponentModelCCG025({
            roleType: 8,
            multiple: false,
            rows: 15
        });

        // enableDetail: KnockoutObservable<boolean> = ko.observable(true);

        created() {
            const vm = this;
            vm.selectedRole = new Role();
            vm.listRole = ko.observableArray([]);
            vm.enumRange = ko.observableArray([
                {value: 1, localizedName: vm.$i18n("CAS009_11")},
                {value: 2, localizedName: vm.$i18n("CAS009_12")},
                {value: 3, localizedName: vm.$i18n("CAS009_13")}
            ]);

            _.extend(vm, {
                listRole: vm.component025.listRole
            });

            _.extend(vm.selectedRole, {
                roleId: vm.component025.currentCode,
                assignAtr: vm.component025.roleClassification
            });

            // subscribe and change data
            vm.selectedRole.roleId.subscribe(rid => {
                let roles = ko.toJS(vm.listRole),
                    exist: IRole = _.find(roles, (r: IRole) => _.isEqual(r.roleId, rid));

                if (exist) {
                    vm.selectedRole.roleName(exist.name);
                    vm.selectedRole.roleCode(exist.roleCode)

                    // vm.selectedRole.assignAtr(exist.assignAtr);
                    // vm.selectedRole.referFutureDate(exist.referFutureDate || false);
                    vm.selectedRole.employeeReferenceRange(exist.employeeReferenceRange || 0);
                    vm.$ajax("com", API.permissionPersonInfo, rid).done((data: any) => {
                        vm.selectedRole.permisions(data);
                        vm.selectedRole.permisions.valueHasMutated();
                    });
                } else {
                    vm.selectedRole.roleName('');
                    vm.selectedRole.roleCode('');

                    // if (!_.isEqual(vm.selectedRole.assignAtr(), 0)) {
                    //     vm.selectedRole.assignAtr(0);
                    // } else {
                    //     vm.selectedRole.assignAtr.valueHasMutated();
                    // }
                    // vm.selectedRole.referFutureDate(false);
                    vm.selectedRole.employeeReferenceRange(_.isEqual(vm.selectedRole.assignAtr(), 0) ? 0 : 1);
                    vm.$ajax("com", API.permissionPersonInfo, undefined).done((data: any) => {
                        vm.selectedRole.permisions(data);
                        if (_.isEqual(vm.selectedRole.assignAtr(), 0)) {
                            _.each(vm.selectedRole.permisions(), (p: IPermision) => {
                                if (_.isEqual(p.functionNo, 11)) {
                                    p.available = false;
                                } else {
                                    p.available = true;
                                }
                            });
                        }
                        vm.selectedRole.permisions.valueHasMutated();
                    });
                }

                // subscribe for focus and clear errors
                _.defer(() => {
                    if (_.isEmpty(rid)) {
                        vm.selectedRole.roleCodeFocus(true);
                    } else {
                        vm.selectedRole.roleNameFocus(true);
                    }

                    // clear all error
                    nts.uk.ui.errors.clearAll();
                });
            });

            // call reload data
            vm.start();
        }

        /** Start Page */
        start() {
            let vm = this, role = vm.selectedRole;

            vm.$blockui("show");
            // vm.$ajax(API.hasRole).done(enableDetail => {
            //     vm.enableDetail(enableDetail);

            // get list role
            vm.getListRole().done(() => {
                let roles = ko.toJS(vm.listRole);

                if (!_.size(roles)) {
                    vm.createNew();
                } else {
                    role.roleId.valueHasMutated();
                }

            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
                nts.uk.ui.errors.clearAll();
            });
            // }).fail(error => {
            //     vm.$dialog.error(error).then(() => {
            //         vm.$blockui("hide");
            //     });
            // });
        };

        /**
         * export excel
         */
        exportExcel(){
            // cas009.a.exportExcel().done(function(data) {
            //
            // }).fail(function(res: any) {
            //     nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
            // }).always(()=>{
            //     block.clear();
            // });
        }

        getListRole(roleId?: string) {
            let vm = this, dfd = $.Deferred();

            vm.component025.startPage([], roleId).done(() => {
                let roles: Array<IRole> = ko.toJS(vm.listRole),
                    roleIds: Array<string> = _.map(roles, (x: IRole) => x.roleId);

                if (_.size(roleIds)) {
                    vm.$ajax("com", API.getRole, roleIds).done(resp => {
                        _.each(vm.listRole(), (r: IRole) => {
                            let pinfo: IRole = _.find(resp, (o: IRole) => o.roleId == r.roleId);
                            if (pinfo) {
                                r.referFutureDate = pinfo.referFutureDate;
                            } else {
                                r.referFutureDate = false;
                            }
                        });
                        dfd.resolve();
                    });
                } else {
                    vm.createNew();
                    dfd.resolve();
                }
            });

            return dfd.promise();
        }

        // create new mode
        createNew() {
            let vm = this, role = vm.selectedRole;
            role.roleId(undefined);
            role.roleId.valueHasMutated();
        }

        // open dialog
        // openDialog = () => { modal("../b/index.xhtml").onClosed(() => { }); }

        // save change of role
        save() {
            let vm = this,
                role: Role = vm.selectedRole,
                command = ko.toJS(role);

            $(".nts-input").trigger("validate");
            if ($(".nts-input").ntsError("hasError")) {
                return;
            }

            vm.$blockui("show");

            // fix name
            _.extend(command, {
                name: command.roleName,
                createMode: _.isEmpty(command.roleId),
                // referFutureDate: command.referFutureDate || false,
                functionAuthList: _.map(command.permisions, m => _.pick(m, ['functionNo', 'available']))
            });

            vm.$ajax("com", API.savePermission, command).done(() => {
                vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
                    vm.getListRole(command.roleId).done(() => {
                        let exist: IRole = _.find(vm.listRole(), o => o.roleCode == command.roleCode);

                        if (!exist) {
                            role.roleId(undefined);
                        } else {
                            role.roleId(exist.roleId);
                        }
                    }).always(() => {
                        vm.$blockui("hide");
                        nts.uk.ui.errors.clearAll();
                    });
                });
            }).fail((error) => {
                vm.$dialog.error(error);
                vm.$blockui("hide");
                nts.uk.ui.errors.clearAll();
            });
        }

        // remove selected role
        remove = () => {
            let vm = this,
                roles: Array<IRole> = ko.toJS(vm.listRole),
                role: IRole = ko.toJS(vm.selectedRole),
                index: number = _.findIndex(roles, ["roleId", role.roleId]);

            index = _.min([_.size(roles) - 2, index]);

            if (!_.isNil(role.roleId)) {
                vm.$blockui("show");
                vm.$ajax("com", API.checkPermission, role.roleId).done(() => {
                    vm.$blockui("hide");
                    vm.$dialog.confirm({ messageId: "Msg_18" }).then(value => {
                        if (value == "yes") {
                            vm.$blockui("show");
                            vm.$ajax("com", API.removePermission, _.pick(role, ["roleId", "assignAtr"])).done(() => {
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    vm.getListRole().done(() => {
                                        let roles: Array<IRole> = ko.toJS(vm.listRole),
                                            selected: IRole = roles[index];

                                        if (_.size(roles)) {
                                            if (selected) {
                                                vm.selectedRole.roleId(selected.roleId);
                                            } else {
                                                vm.selectedRole.roleId(roles[0].roleId);
                                            }
                                            vm.selectedRole.roleId.valueHasMutated();
                                        } else {
                                            vm.createNew();
                                        }
                                    }).always(() => {
                                        vm.$blockui("hide");
                                        nts.uk.ui.errors.clearAll();
                                    });
                                });
                            }).fail((error) => {
                                vm.$dialog.error(error);
                                vm.$blockui("hide");
                                nts.uk.ui.errors.clearAll();
                            });
                        }
                    });
                }).fail((error) => {
                    vm.$dialog.error(error);
                    vm.$blockui("hide");
                    nts.uk.ui.errors.clearAll();
                });
            }
        }
    }

    interface EnumConstantDto {
        value: number;
        fieldName?: string;
        localizedName: string;
    }

    interface IRole {
        name: string;
        roleId: string;
        roleCode: string;
        assignAtr: number;
        createMode?: boolean;
        referFutureDate?: boolean;
        employeeReferenceRange: number;
        permisions: Array<IPermision>;
    }

    class Role {
        roleId: KnockoutObservable<string> = ko.observable('');
        roleCode: KnockoutObservable<string> = ko.observable('');
        roleName: KnockoutObservable<string> = ko.observable('');

        assignAtr: KnockoutObservable<number> = ko.observable(1);
        referFutureDate: KnockoutObservable<boolean> = ko.observable(false);
        employeeReferenceRange: KnockoutObservable<number> = ko.observable(1);

        roleCodeFocus: KnockoutObservable<boolean> = ko.observable(true);
        roleNameFocus: KnockoutObservable<boolean> = ko.observable(false);

        permisions: KnockoutObservableArray<IPermision> = ko.observableArray([]);

        constructor() {
            let self = this;
        }
    }

    interface IPermision extends ccg026.IPermision {

    }
}