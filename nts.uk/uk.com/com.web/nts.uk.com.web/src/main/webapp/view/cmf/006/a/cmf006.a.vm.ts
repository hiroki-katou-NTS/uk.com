/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.cmf006.a {
    import windows = nts.uk.ui.windows;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import util = nts.uk.util;
    import getText = nts.uk.resource.getText;

    import ccg025 = nts.uk.com.view.ccg025.a;
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
        roleId: KnockoutObservable<string> = ko.observable(null);
        roleCode: KnockoutObservable<string> = ko.observable(null);
        roleName: KnockoutObservable<string> = ko.observable(null);

        //ccg026
        roleType: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.EMPLOYMENT);
        permissionList: KnockoutObservableArray<IPermission> = ko.observableArray([]);
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(false);
        enableSave: KnockoutObservable<boolean> = ko.observable(true);
        enableCopy: KnockoutObservable<boolean> = ko.observable(false);
        data: any;

        constructor(params: any) {
            super();
            const vm = this;

            vm.$blockui("grayout");
            vm.listRole = ko.observableArray([]);
            vm.componentCcg025 = new ccg025.component.viewmodel.ComponentModel({
                roleType: vm.roleType(),
                multiple: false,
                isResize: false,
                rows: 15,
                tabindex: 1
            });
            vm.fetchPermissionSettingList();
            // vm.componentCcg025.columns([
            //     { headerText: getText("CCG025_3"), prop: 'roleId', width: 50, hidden: true },
            //     { headerText: getText("CCG025_3"), prop: 'roleCode', width: 50 },
            //     { headerText: getText("CCG025_4"), prop: 'name', width: 205 }
            // ]);
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
                vm.findRoleById(roleId);
            });
            vm.isUpdateMode.subscribe((newValue) => {
                if (newValue) {
                    vm.enableSave(true);
                    vm.enableCopy(true);
                }
            });
            vm.listRole.subscribe((newValue) => {
                if (_.isEmpty(newValue)) {
                    vm.enableSave(false);
                    vm.enableCopy(false);
                }
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
            vm.$ajax(fetch.availabilityPermission + roleId).done((data) => {
                if (!_.isEmpty(data)) {
                    vm.mappingAvailabilityPermission(data);
                    vm.enableCopy(true);
                }
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        findRoleById(roleId?: string): void {
            const vm = this;
            let role = _.find(vm.listRole(), (x) => {
                return x.roleId === roleId;
            });
            if (role) {
                vm.roleCode(role.roleCode);
                vm.roleName(role.roleName);
            }
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
            let command: IRegisterExOutCtgAuthSetCommand = {
                roleId: vm.roleId(),
                functionAuthSettings: ko.toJS(vm.permissionList()).map((i: any) => ({
                    functionNo: i.functionNo,
                    available: i.available
                }))
            };
            vm.$ajax(fetch.register, command).then(data => {
                vm.isUpdateMode(true);
                vm.$dialog.info({messageId: 'Msg_15'});
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        openScreenB(): void {
            const vm = this;
            let param = {
                roleType: vm.roleType(),
                sourceRoleId: vm.roleId(),
                sourceRoleCode: vm.roleCode(),
                sourceRolName: vm.roleName()
            };
            setShared('dataShareCMF006B', param);
            vm.$window.modal('com', '/view/cmf/006/b/index.xhtml').then((data: any) => {
                // let result = nts.uk.ui.windows.getShared('dataShareCMF006A');
                // if (result) {
                //     if (result.isSuccess) {
                //         vm.$dialog.info({messageId: 'Msg_15'});
                //     }
                // }
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