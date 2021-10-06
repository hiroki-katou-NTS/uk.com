/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal012.a {
    import windows = nts.uk.ui.windows;
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
        initData: "exio/exo/condset/getExOutCategory",
        register: "exio/exo/condset/getExOutCategory/exOutCtgAuthSet/register",
        copy: "exio/exo/condset/getExOutCategory/exOutCtgAuthSet/exOutCtgAuthSet/copy"
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        //ccg025
        componentCcg025: ccg025.component.viewmodel.ComponentModel;
        listRole: KnockoutObservableArray<Role> = ko.observableArray([]);
        roleName: KnockoutObservable<string> = ko.observable(null);

        //ccg026
        roleId: KnockoutObservable<string> = ko.observable(null);// role id
        roleType: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.COMPANY_MANAGER);
        // roleType1: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.PERSONAL_INFO);
        // roleType2: KnockoutObservable<ROLE_TYPE> = ko.observable(ROLE_TYPE.PERSONAL_INFO);

        permissionSettings : KnockoutObservableArray<IPermission> = ko.observableArray([]);
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);

        constructor(params: any) {
            super();
            const vm = this;

            vm.listRole = ko.observableArray([]);

            vm.initialCCG025026();
        }

        created(params: any) {
            let vm = this;
        }

        mounted() {
            let vm = this;
        }

        initialCCG025026() {
            let vm = this;
            vm.componentCcg025 = new ccg025.component.viewmodel.ComponentModel({
                roleType: 3,
                multiple: false,
                isAlreadySetting: false
            });
            vm.componentCcg025 = new ccg025.component.viewmodel.ComponentModel({
                roleType: ROLE_TYPE.EMPLOYMENT,
                multiple: false
            });

            vm.getDataCCG025();
            vm.componentCcg025.currentCode.subscribe((roleId: any) => {
                if (vm.listRole().length <= 0) vm.listRole(vm.componentCcg025.listRole());
                vm.roleId(roleId);
                vm.findRole(roleId);
            });

            vm.roleId.subscribe((newRoleId) => {
                if (!_.isEmpty(newRoleId)) vm.getRoleInfor(newRoleId);
            });
        }

        findRole(roleId?: string) {
            let vm = this;
            let role = _.find(vm.listRole(), (x) => { return x.roleId === roleId; });
            if (role) vm.roleName(role.roleName);
        }

        getDataCCG025(): JQueryPromise<any> {
            let vm = this;
            let dfd = $.Deferred();
            vm.componentCcg025.startPage().done(() => {
                vm.listRole(vm.componentCcg025.listRole());
            });
            dfd.resolve();
            return dfd.promise();
        }

        getRoleInfor(roleId: string) {
            let vm = this;
            // vm.$blockui("show");
            // vm.$ajax(fetch.getRoleInfor + roleId).done((data) => {
            //     if (data) {
            //         vm.data = data;
            //         vm.basicFunctionControl(data.useAtr || 0);
            //         vm.dateSelected(data.deadLineDay || 0);
            //         vm.convertToPermissionList(data);
            //     }
            // }).fail(error => {
            //     vm.$dialog.error(error);
            // }).always(() => {
            //     vm.$blockui("hide");
            // });
        }

        save() {

        }

        copy() {

        }

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