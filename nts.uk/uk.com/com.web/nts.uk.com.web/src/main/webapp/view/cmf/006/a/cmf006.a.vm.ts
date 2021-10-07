/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal012.a {
    import windows = nts.uk.ui.windows;
    import util = nts.uk.util;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import modal = windows.sub.modal;
    import text = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import confirm = nts.uk.ui.dialog.confirm;
    import errors = nts.uk.ui.errors;

    import ccg025 = nts.uk.com.view.ccg025.a;
    // import model = ccg025.component.model;

    import ccg026 = nts.uk.com.view.ccg026;
    import ROLE_TYPE = ccg026.component.ROLE_TYPE;

    const fetch = {
        permissionInfos: "exio/exo/condset/getExOutCategory/",
        availabilityPermission: "exio/exo/condset/exOutCtgAuthSet/",
        register: "exio/exo/condset/exOutCtgAuthSet/register",
        copy: "exio/exo/condset/exOutCtgAuthSet/copy"
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        //ccg025
        componentCcg025: ccg025.component.viewmodel.ComponentModel;
        listRole: KnockoutObservableArray<Role> = ko.observableArray([]);
        roleName: KnockoutObservable<string> = ko.observable(null);

        //ccg026
        roleId: KnockoutObservable<string> = ko.observable(null); // role id
        roleType: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.EMPLOYMENT);
        // roleType1: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.PERSONAL_INFO);

        permissionList: KnockoutObservableArray<IPermission> = ko.observableArray([]);
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        data: any;

        constructor(params: any) {
            super();
            const vm = this;

            vm.listRole = ko.observableArray([]);
            vm.componentCcg025 = new ccg025.component.viewmodel.ComponentModel({
                roleType: vm.roleType(),
                multiple: false,
                isAlreadySetting: false
            });
            vm.fetchPermissionSettingList();
            vm.fetchRoleList();
        }

        created(params: any) {
            const vm = this;

            vm.roleId.subscribe((newValue) => {
                if (!_.isEmpty(newValue)) vm.fetchAvailabilityPermission(newValue);
            });
            vm.componentCcg025.currentCode.subscribe((roleId: any) => {
                if (vm.listRole().length <= 0) vm.listRole(vm.componentCcg025.listRole());
                vm.roleId(roleId);
                vm.findRoleName(roleId);
            });
        }

        mounted() {
            let vm = this;
        }

        /**
         * CCG025
         * @returns {JQueryPromise<any>}
         */
        fetchRoleList(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred();
            vm.componentCcg025.startPage().done(() => {
                vm.listRole(vm.componentCcg025.listRole());
            });
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * CCG026
         */
        fetchPermissionSettingList(): void {
            const vm = this;
            vm.$blockui("show");
            vm.$ajax(fetch.permissionInfos + vm.roleType()).done((data: any) => {
                if (data) {
                    vm.mappingPermissionList(data);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        /**
         * Find ExOutCtgAuthSet
         * @param {string} roleId
         */
        fetchAvailabilityPermission(roleId: string): void {
            const vm = this;
            vm.$blockui("show");
            console.log("RoleId: ", roleId);
            vm.$ajax(fetch.availabilityPermission + roleId).done((data) => {
                if (data) {
                    vm.mappingAvailabilityPermission(data);
                    vm.isNewMode(false);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        findRoleName(roleId?: string): void {
            const vm = this;
            let role = _.find(vm.listRole(), (x) => {
                return x.roleId === roleId;
            });
            if (role) vm.roleName(role.roleName);
        }

        private mappingPermissionList(data: any): void {
            const vm = this;
            vm.permissionList(data.map((i: any) => ({
                available: false,
                description: i.explanation,
                functionName: i.functionName,
                functionNo: i.functionNo,
                orderNumber: i.displayOrder
            })));
        }

        private mappingAvailabilityPermission(authSet: any): void {
            const vm = this;
            const availableAuths = authSet.filter((i: any) => i.available).map((i: any) => i.functionNo);
            vm.permissionList(vm.permissionList().map((i: any) => ({
                available: availableAuths.indexOf(i.functionNo) >= 0,
                description: i.description,
                functionName: i.functionName,
                functionNo: i.functionNo,
                orderNumber: i.orderNumber
            })));
        }

        save(): void {
            const vm = this;
            vm.$blockui("invisible");
            let command : IRegisterExOutCtgAuthSetCommand = {
                roleId: vm.roleId(),
                functionAuthSettings: ko.toJS(vm.permissionList()).map((i: any) => ({
                    functionNo: i.functionNo,
                    available: i.available
                }))
            };
            vm.$ajax(fetch.register, command).then(data => {
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

    }

    interface IRegisterExOutCtgAuthSetCommand {
        roleId: string;
        functionAuthSettings: Array<IPermissionSetting>;
    }

    interface IPermissionSetting {
        functionNo: number;
        available: boolean;
    }

    export interface IRole {
        name: string;
        roleId: string;
        roleCode: string;
        assignAtr: number;
        createMode?: boolean;
        referFutureDate?: boolean;
        employeeReferenceRange: number;
        permisions: Array<IPermission>;
    }

    export interface IPermission extends ccg026.component.IPermision {
    }

    export class Role extends ccg025.component.model.Role {
    }
}