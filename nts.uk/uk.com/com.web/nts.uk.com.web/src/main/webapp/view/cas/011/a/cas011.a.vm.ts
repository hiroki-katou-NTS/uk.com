/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cas011.a {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import isNullOrUndefined = nts.uk.util.isNullOrUndefined;
    var format = nts.uk.text.format;

    const API = {
        getDtaInit: "screen/com/cas011/get-data-init",
        getRoleSetByRoleSetCd: "screen/com/cas011/get-detail-role-set/{0}",

        getCompanyIdOfLoginUser: "ctx/sys/auth/roleset/companyidofloginuser",

        addRoleSet: "screen/sys/auth/cas011/addroleset",
        updateRoleSet: "screen/sys/auth/cas011/updateroleset",
        removeRoleSet: "screen/sys/auth/cas011/deleteroleset",




    }
    @bean()
    class ViewModel extends ko.ViewModel {
        langId: KnockoutObservable<string> = ko.observable('ja');
        roleIdEmployment: KnockoutObservable<string> = ko.observable('');
        roleIdPerson: KnockoutObservable<string> = ko.observable('');
        //list of Role Set
        listRoleSets: KnockoutObservableArray<IRoleSet> = ko.observableArray([]);
        dataA41: KnockoutObservableArray<any> = ko.observableArray([]);
        dataA51: KnockoutObservableArray<any> = ko.observableArray([]);

        listWebMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);
        listAllWebMenus: Array<IWebMenu> = [];
        listCurrentRoleIds: Array<string> = [];
        currentRoleSet: KnockoutObservable<RoleSet> = ko.observable(new RoleSet({
            companyId: ''
            , roleSetCd: ''
            , roleSetName: ''
            , personInfRoleId: ''
            , employmentRoleId: ''
            , webMenus: []
            , defaultRoleSet:false
        }));

        selectedRoleSetCd: KnockoutObservable<string> = ko.observable('');

        isNewMode: KnockoutObservable<boolean>;
        isCheck: KnockoutObservable<boolean> = ko.observable(false);
        roleSetCount: KnockoutObservable<number> = ko.observable(0);
        swApprovalAuthority: KnockoutObservableArray<any>;
        gridColumns: KnockoutObservableArray<NtsGridListColumn>;
        swapColumns: KnockoutObservableArray<NtsGridListColumn>;

        constructor(params: any) {
            super();
            const vm = this;
            const currentRoleSet: RoleSet = vm.currentRoleSet();
            // A2_003, A2_004, A2_005, A2_006
            vm.gridColumns = ko.observableArray([
                {headerText: resource.getText('CAS011_9'), key: 'roleSetCd',
                    headerCssClass: 'text-center',columnCssClass: 'text-center',formatter: _.escape, width: 65},
                {headerText: resource.getText('CAS011_10'), key: 'roleSetName',
                    headerCssClass: 'text-center',columnCssClass: 'text-center',formatter: _.escape, width: 180},
                {
                    headerText: resource.getText('CAS011_44'), key: 'check', width: 35,
                    template: '{{if ${check} == 1 }}<div class="cssDiv"><i  class="icon icon icon-78 cssI"></i></div>{{/if}}'
                }
            ]);

            vm.swapColumns = ko.observableArray([
                {headerText: resource.getText('CAS011_9'), key: 'webMenuCode', width: 65},
                {headerText: resource.getText('CAS011_34'), key: 'webMenuName', width: 135}
            ]);

            // ---A3_024, A3_025
            vm.swApprovalAuthority = ko.observableArray([
                {code: true, name: resource.getText('CAS011_22')},
                {code: false, name: resource.getText('CAS011_23')}
            ]);
            vm.isNewMode = ko.observable(true);

            /**
             *Subscribe: 項目変更→項目
             */
            let dfd = $.Deferred(),
                listRoleSets = vm.listRoleSets;
            /**
             *実行時情報をチェックする- check runtime
             */
            vm.$ajax('com', API.getCompanyIdOfLoginUser).done((companyId: any) => {
                if (!companyId) {
                    vm.backToTopPage();
                    dfd.resolve();
                } else {
                    // initial screen
                    vm.initialScreen(dfd, '');
                }
            }).fail(error => {
                vm.backToTopPage();
                dfd.resolve();
            });


        }
        getDetail(roleSetCd: string){
            let vm = this,
                dfd = $.Deferred();
                errors.clearAll();
                let listRoleSet = vm.listRoleSets();
                // do not process anything if it is new mode.
                //if (roleSetCd) {
                if (roleSetCd && listRoleSet && listRoleSet.length > 0) {
                    var _path = format(API.getRoleSetByRoleSetCd, roleSetCd);
                    vm.$ajax('com',_path).done((data)=>{

                        if(!isNullOrUndefined(data)){
                            let roleSetDtos :IRoleSet = data.roleSetDtos;
                            let defaultRoleSet = data.defaultRoleSet;
                            let linkWebMenuImportList: any[] = data.linkWebMenuImportList;
                            let listWebMenuValue : IWebMenu[] = [];
                            let _roleSet = roleSetDtos;
                            if (_roleSet && _roleSet.roleSetCd) {
                                vm.settingUpdateMode(_roleSet.roleSetCd);
                                if(!isNullOrUndefined(defaultRoleSet) &&_roleSet.roleSetCd == defaultRoleSet.roleSetCd){
                                    _roleSet.defaultRoleSet = true;
                                    _roleSet.check = 1;
                                }else {
                                    _roleSet.defaultRoleSet = false;
                                }
                                vm.createCurrentRoleSet(_roleSet);
                                if(linkWebMenuImportList && linkWebMenuImportList.length>=0){
                                    for (let i =0; i< linkWebMenuImportList.length; i++ ){
                                        let item = linkWebMenuImportList[i];
                                        let code = item.webMenuCd;
                                        let itemName =  _.find(vm.listAllWebMenus,(e)=>(e.webMenuCode ==code));
                                        if(!isNullOrUndefined(itemName)){
                                            listWebMenuValue.push({
                                                webMenuCode: code,
                                                webMenuName: itemName.webMenuName
                                            })
                                        }
                                    }
                                }
                                vm.currentRoleSet().webMenus(listWebMenuValue);
                            } else {
                                //vm.settingCreateMode();
                                vm.initialScreen(null, '');
                            }
                        }
                    }).fail(error => {
                        vm.backToTopPage();
                        dfd.resolve();
                    });

                } else {
                    vm.createNewCurrentRoleSet();
                    vm.settingCreateMode();
                }
        }

        created(params: any) {
            let vm = this;
            vm.selectedRoleSetCd.subscribe((roleSetCd) => {
                vm.getDetail(roleSetCd)
            })
        }
        mounted() {
            const vm = this;
        }
        initialScreen(deferred: any, roleSetCd: string) {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet(),
                listRoleSets = vm.listRoleSets,
                dataA41 : any[]= [],
                dataA51 : any[]= []
            ;
            listRoleSets.removeAll();
            errors.clearAll();
            // initial screen

            vm.$ajax('com', API.getDtaInit).done((data)=>{
                if(data){
                    let itemList : IRoleSet[] = data.roleDefaultDto.roleSetDtos;
                    let listWebMenu = data.webMenuSimpleDtos;
                    let defaultRoleSet = data.roleDefaultDto.defaultRoleSet;

                    let employmentRole  = data.rolesEmployment;
                    let personRole  = data.rolesPersonalInfo;
                    if(!isNullOrUndefined(employmentRole)){
                        for (let i = 0; i< employmentRole.length; i ++){
                            let item = employmentRole[i];
                            let display = item.roleCode + " " + item.name;
                            dataA41.push({id : item.roleId, code : item.roleCode, display : display})

                        }
                        vm.dataA41(dataA41);
                    }

                    if(!isNullOrUndefined(personRole)){
                        for (let i = 0; i< personRole.length; i ++){
                            let item = personRole[i];
                            let display = item.roleCode + " " + item.name;
                            dataA51.push({id : item.roleId, code : item.roleCode, display : display})

                        }
                        vm.dataA51(dataA51);
                    }
                    if (listWebMenu && listWebMenu.length > 0) {
                        vm.listAllWebMenus = listWebMenu;
                    }
                    if (itemList && itemList.length > 0) {
                        if(!isNullOrUndefined(defaultRoleSet))
                        for (let i = 0; i< itemList.length; i ++){
                            let item = itemList[i];
                            if(item.roleSetCd == defaultRoleSet.roleSetCd){
                                item.defaultRoleSet = true;
                                item.check = 1;
                            }else {
                                item.defaultRoleSet = false;
                            }
                        }
                        vm.listRoleSets(itemList);
                        vm.roleSetCount(itemList.length);
                        let index: number = 0;
                        if (roleSetCd) {
                            index = _.findIndex(listRoleSets(), function (x: IRoleSet) {

                                return x.roleSetCd == roleSetCd
                            });
                            if (index === -1) index = 0;
                        }
                        let _roleSet = listRoleSets()[index];
                        vm.getDetail(_roleSet.roleSetCd);
                        //vm.createCurrentRoleSet(_roleSet);
                        vm.settingUpdateMode(_roleSet.roleSetCd);
                    } else { //in case number of RoleSet is zero
                        vm.createNewCurrentRoleSet();
                        vm.settingCreateMode();
                    }
                }else {
                    vm.createNewCurrentRoleSet();
                    vm.settingCreateMode();
                }
            }).fail((error) => {
                dialog.alertError({messageId: error.messageId});
            }).always(() => {
                vm.roleSetCount(vm.listRoleSets().length);
                if (deferred) {
                    deferred.resolve();
                }
            });
        }
        /**
         * back to top page - トップページに戻る
         */
        backToTopPage() {
            windows.sub.modeless("/view/ccg/008/a/index.xhtml");
        }
        /**
         * Save
         */
        saveRoleSet() {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            $('.nts-input').trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (vm.isNewMode()) {
                    currentRoleSet.defaultRoleSet(vm.isCheck());
                    // create new role set
                    vm.$ajax('com', API.addRoleSet, ko.toJS(currentRoleSet)).done((roleSetCd) => {
                        dialog.info({messageId: "Msg_15"});
                        // refresh - initial screen
                        vm.initialScreen(null, currentRoleSet.roleSetCd());
                    }).fail(function (error) {

                        if (error.messageId == 'Msg_583') {
                            dialog.alertError({messageId: error.messageId, messageParams: ["メニュー"]});
                        } else {
                            if (error.messageId == 'Msg_3') {
                                $('#inpRoleSetCd').ntsError('set', error);
                                $('#inpRoleSetCd').focus();
                            }
                            dialog.alertError({messageId: error.messageId});
                        }
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    // update
                    vm.$ajax('com', API.updateRoleSet, ko.toJS(currentRoleSet)).done((roleSetCd) => {
                        dialog.info({messageId: "Msg_15"});
                        // refresh - initial screen
                        vm.initialScreen(null, currentRoleSet.roleSetCd());

                    }).fail(function (error) {
                        if (error.messageId == 'Msg_583') {
                            dialog.alertError({messageId: error.messageId, messageParams: ["メニュー"]});
                        } else {
                            dialog.alertError({messageId: error.messageId});
                        }
                    }).always(function () {
                        block.clear();
                    });
                }
            }
        }

        /**
         * delete the role set
         */
        deleteRoleSet() {
            let vm = this,
                listRoleSets = vm.listRoleSets,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            block.invisible();
            /**
             * 確認メッセージ（Msg_18）を表示する
             */
            dialog.confirmDanger({messageId: "Msg_18"}).ifYes(() => {
                if (currentRoleSet.roleSetCd()) {
                    var object: any = {roleSetCd: currentRoleSet.roleSetCd()};
                    vm.$ajax('com', API.removeRoleSet, ko.toJS(object)).done(function () {
                        dialog.info({messageId: "Msg_16"});
                        //select next Role Set
                        let index: number = _.findIndex(listRoleSets(), function (x: IRoleSet) {
                            return x.roleSetCd == currentRoleSet.roleSetCd()
                        });
                        // remove the deleted item out of list
                        if (index > -1) {
                            vm.listRoleSets.splice(index, 1);
                            if (index >= listRoleSets().length) {
                                index = listRoleSets().length - 1;
                            }
                            if (listRoleSets().length > 0) {
                                vm.settingUpdateMode(listRoleSets()[index].roleSetCd);
                            } else {
                                vm.settingCreateMode();
                            }
                        }
                    }).fail(function (error) {
                        dialog.alertError({messageId: error.messageId});
                    }).always(function () {
                        block.clear();
                    });
                } else {
                    block.clear();
                }
            }).then(() => {
                block.clear();
            });
            ;
        }
        setFocus() {
            let vm = this;
            if (vm.isNewMode()) {
                $('#inpRoleSetCd').focus();
            } else {
                $('#inpRoleSetName').focus();
            }
            errors.clearAll();
        }

        /**
         * create a new Role Set
         * 画面を新規モードで起動する
         */
        settingCreateMode() {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            // clear selected role set
            vm.createNewCurrentRoleSet();
            vm.selectedRoleSetCd('');
            // Set new mode
            vm.isNewMode(true);
            //focus
            vm.setFocus();
        }

        /**
         * Setting selected role set.
         */
        settingUpdateMode(selectedRoleSetCd: any) {
            let vm = this;
            vm.selectedRoleSetCd(selectedRoleSetCd);
            if (selectedRoleSetCd) {
                //Setting update mode
                vm.isNewMode(false);
                //focus
                vm.setFocus();
            }
        }

        /**
         * BindNoData to currentRoleSet
         */
        createNewCurrentRoleSet() {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            if (currentRoleSet.roleSetCd() === '') {
                return;
            }
            currentRoleSet.roleSetCd('');
            currentRoleSet.roleSetName('');
            currentRoleSet.personInfRoleId('');
            currentRoleSet.employmentRoleId('');
            currentRoleSet.webMenus([]);
            // build swap web menu
            vm.buildSwapWebMenu();

            //build role Name
            vm.listCurrentRoleIds = [];

        }

        /**
         * BindData to currentRoleSet
         * @param _roleSet
         */
        createCurrentRoleSet(_roleSet: IRoleSet) {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            if (currentRoleSet.roleSetCd() === _roleSet.roleSetCd) {
                return;
            }
            currentRoleSet.companyId(_roleSet.companyId);
            currentRoleSet.roleSetCd(_roleSet.roleSetCd);
            currentRoleSet.roleSetName(_roleSet.roleSetName);
            currentRoleSet.personInfRoleId(_roleSet.personInfRoleId);
            currentRoleSet.employmentRoleId(_roleSet.employmentRoleId);
            currentRoleSet.defaultRoleSet(_roleSet.defaultRoleSet);
            currentRoleSet.webMenus(_roleSet.webMenus || []);

            // build swap web menu
            vm.buildSwapWebMenu();


        }

        /**
         * build swap web menu
         */
        buildSwapWebMenu() {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();

            vm.listWebMenus.removeAll();
            if (vm.listAllWebMenus && vm.listAllWebMenus.length > 0) {
                vm.listWebMenus(vm.listAllWebMenus.filter(item1 => !vm.isSelectedWebMenu(item1.webMenuCode)));
                // get Web Menu Name for Web menu
                let listWebMenuRight = vm.listAllWebMenus.filter(item1 => vm.isSelectedWebMenu(item1.webMenuCode));
                //currentRoleSet.webMenus.removeAll();
                currentRoleSet.webMenus(listWebMenuRight);
            }
        }


        /**
         * Check and return true if the Web menu code existed in current selected web menu list.
         *
         */
        isSelectedWebMenu = function (_webMenuCode: string): boolean {
            let vm = this,
                currentRoleSet: RoleSet = this.currentRoleSet();

            if (!_webMenuCode || !currentRoleSet
                || !currentRoleSet.webMenus() || currentRoleSet.webMenus().length === 0) {
                return false;
            }
            let index: number = _.findIndex(currentRoleSet.webMenus(), function (x: IWebMenu) {
                return x.webMenuCode === _webMenuCode
            });
            return (index > -1);
        }

    }

    /**
     * The enum of ROLE TYPE
     */
    export enum ROLE_TYPE {
        EMPLOYMENT = 3,
        SALARY = 4,
        HR = 5,
        OFFICE_HELPER = 6,
        MY_NUMBER = 7,
        PERSON_INF = 8
    }

    // The Web menu
    export interface IWebMenu {
        webMenuCode: string;
        webMenuName: string;
    }

    export class WebMenu {
        webMenuCode: KnockoutObservable<string> = ko.observable('');
        webMenuName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IWebMenu) {
            let vm = this;
            vm.webMenuCode(param.webMenuCode || '');
            vm.webMenuName(param.webMenuName || '');
        }
    }

    // The Role Set
    export interface IRoleSet {
        companyId: string;
        roleSetCd: string;
        roleSetName: string;
        personInfRoleId: string;
        employmentRoleId: string;
        webMenus: Array<IWebMenu>;
        defaultRoleSet: boolean;
        check?: number
    }

    export interface IRoleSetDto {
        companyId: string;
        roleSetCd: string;
        roleSetName: string;
    }

    export class RoleSet {
        companyId: KnockoutObservable<string> = ko.observable('');
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');
        personInfRoleId: KnockoutObservable<string> = ko.observable('');
        employmentRoleId: KnockoutObservable<string> = ko.observable('');
        defaultRoleSet: KnockoutObservable<boolean> = ko.observable(false);
        webMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);

        constructor(param: IRoleSet) {
            let vm = this;
            vm.companyId(param.companyId);
            vm.roleSetCd(param.roleSetCd || '');
            vm.roleSetName(param.roleSetName || '');
            vm.webMenus(param.webMenus || []);
            vm.personInfRoleId(param.personInfRoleId || '');
            vm.employmentRoleId(param.employmentRoleId || '');
            vm.defaultRoleSet(param.defaultRoleSet);
        }
    }
}