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


    const API = {
        getDtaInit: "screen/com/cas011/cas011/get-data-init",
        getRoleSetByRoleSetCd: "screen/com/cas011/cas011/get-detail-role-set/{0}",

        getCompanyIdOfLoginUser: "ctx/sys/auth/roleset/companyidofloginuser",
        getAllRoleSet: "ctx/sys/auth/roleset/findallroleset",

        addRoleSet: "screen/sys/auth/cas011/addroleset",
        updateRoleSet: "screen/sys/auth/cas011/updateroleset",
        removeRoleSet: "screen/sys/auth/cas011/deleteroleset",
        getAllWebMenu: "sys/portal/webmenu/findallwithnomenubar",
        getRoleById: "ctx/sys/auth/role/getrolebyroleid/{0}",
        getRoleNameByListId: "ctx/sys/auth/role/get/rolename/by/roleids",

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
            , salaryRoleId: ''
            , myNumberRoleId: ''
            , personInfRoleId: ''
            , employmentRoleId: ''
            , officeHelperRoleId: ''
            , approvalAuthority: true
            , humanResourceRoleId: ''
            , webMenus: []
            , registered:false
        }));

        selectedRoleSetCd: KnockoutObservable<string> = ko.observable('');

        hRRoleName: KnockoutObservable<string>;
        salaryRoleName: KnockoutObservable<string>;
        myNumberRoleName: KnockoutObservable<string>;
        personInfRoleName: KnockoutObservable<string>;
        employmentRoleName: KnockoutObservable<string>;
        officeHelperRoleName: KnockoutObservable<string>;

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
                    headerText: resource.getText('CAS011_44'), key: 'registered', width: 35,
                    template: '{{if ${registered} }}<div class="cssDiv"><i  class="icon icon icon-78 cssI"></i></div>{{/if}}'
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

            vm.hRRoleName = ko.observable(resource.getText('CAS011_23'));
            vm.salaryRoleName = ko.observable(resource.getText('CAS011_23'));
            vm.myNumberRoleName = ko.observable(resource.getText('CAS011_23'));
            vm.personInfRoleName = ko.observable(resource.getText('CAS011_23'));
            vm.employmentRoleName = ko.observable(resource.getText('CAS011_23'));
            vm.officeHelperRoleName = ko.observable(resource.getText('CAS011_23'));

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

        created(params: any) {
            let vm = this,
             dfd = $.Deferred();
            vm.selectedRoleSetCd.subscribe(roleSetCd => {
                errors.clearAll();
                let listRoleSet = vm.listRoleSets();
                // do not process anything if it is new mode.
                //if (roleSetCd) {

                if (roleSetCd && listRoleSet && listRoleSet.length > 0) {
                    vm.$ajax('com',API.getRoleSetByRoleSetCd,roleSetCd).done((data)=>{
                        let listDate = data;

                    }).fail(error => {
                        vm.backToTopPage();
                        dfd.resolve();
                    });
                    let index: number = 0;
                    if (roleSetCd) {
                        index = _.findIndex(listRoleSet, function (x: IRoleSet) {
                            return x.roleSetCd == roleSetCd
                        });
                        if (index === -1) index = 0;
                    }
                    let _roleSet = listRoleSet[index];


                    if (_roleSet && _roleSet.roleSetCd) {
                        vm.createCurrentRoleSet(_roleSet);
                        vm.settingUpdateMode(_roleSet.roleSetCd);
                    } else {
                        //vm.settingCreateMode();
                        vm.initialScreen(null, '');
                    }
                    //});
                    // }
                } else {
                    vm.createNewCurrentRoleSet();
                    vm.settingCreateMode();
                }
            });
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
                                item.registered = true;
                                vm.isCheck(true);
                            }
                        }
                        listRoleSets(itemList);
                        vm.roleSetCount(itemList.length);
                        let index: number = 0;
                        if (roleSetCd) {
                            index = _.findIndex(listRoleSets(), function (x: IRoleSet) {

                                return x.roleSetCd == roleSetCd
                            });
                            if (index === -1) index = 0;
                        }
                        let _roleSet = listRoleSets()[index];
                        vm.createCurrentRoleSet(_roleSet);
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

            // vm.$ajax('com', API.getAllRoleSet).done((itemList: Array<IRoleSet>) => {
            //     // in case number of RoleSet is greater then 0
            //     if (itemList && itemList.length > 0) {
            //         listRoleSets(itemList);
            //         /**
            //          * 先頭のロールセットを選択する
            //          */
            //         vm.roleSetCount(itemList.length);
            //         let index: number = 0;
            //         if (roleSetCd) {
            //             index = _.findIndex(listRoleSets(), function (x: IRoleSet) {
            //                 return x.roleSetCd == roleSetCd
            //             });
            //             if (index === -1) index = 0;
            //         }
            //         let _roleSet = listRoleSets()[index];
            //         vm.createCurrentRoleSet(_roleSet);
            //         vm.settingUpdateMode(_roleSet.roleSetCd);
            //     } else { //in case number of RoleSet is zero
            //         /**
            //          * 画面を新規モードで起動する
            //          */
            //         vm.createNewCurrentRoleSet();
            //         vm.settingCreateMode();
            //     }
            // }).fail(error => {
            //     /**
            //      * 画面を新規モードで起動する
            //      */
            //     vm.createNewCurrentRoleSet();
            //     vm.settingCreateMode();
            // }).always(() => {
            //     vm.roleSetCount(vm.listRoleSets().length);
            //     if (deferred) {
            //         deferred.resolve();
            //     }
            // });
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
                    vm.$ajax('com', API.updateRoleSet, (ko.toJS(currentRoleSet)).done((roleSetCd) => {
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
                    }));
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
                    vm.$ajax('com', API.removeRoleSet, (ko.toJS(object)).done(function () {
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
                    }));
                } else {
                    block.clear();
                }
            }).then(() => {
                block.clear();
            });
            ;
        }

        /**
         * setting focus base on screen mode
         */
        setFocus() {
            let vm = this;
            if (vm.isNewMode()) {
                $('#inpRoleSetCd').focus();
            } else {
                $('#inpRoleSetName').focus();
            }
            errors.clearAll();
        }

        /** ダイアログ
         * Open dialog CLD025
         */
        openDialogCLD025(roleType: number, roleId: String) {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            if (!roleType && roleType < 0) {
                return;
            }
            block.invisible();
            let param = {
                roleType: roleType,
                multiple: false,
                currentCode: roleId,
                roleAtr: 1
            };
            windows.setShared('paramCdl025', param);
            windows.sub.modal('/view/cdl/025/index.xhtml', {title: ''}).onClosed(function (): any {
                //get data from share window
                var roleId = windows.getShared('dataCdl025');
                if (roleId != undefined) {
                    vm.setRoleId(roleType, roleId);
                }
                vm.setFocusAfterSelectRole(roleType);
                block.clear();
            });
        }

        /**
         * create a new Role Set
         * 画面を新規モードで起動する
         */
        settingCreateMode() {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            // clear selected role set
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
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
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
            currentRoleSet.salaryRoleId('');
            currentRoleSet.myNumberRoleId('');
            currentRoleSet.personInfRoleId('');
            currentRoleSet.employmentRoleId('');
            currentRoleSet.approvalAuthority(true);
            currentRoleSet.officeHelperRoleId('');
            currentRoleSet.humanResourceRoleId('');

            currentRoleSet.webMenus([]);
            // build swap web menu
            vm.buildSwapWebMenu();

            //build role Name
            vm.listCurrentRoleIds = [];
            vm.buildRoleName();
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
            currentRoleSet.salaryRoleId(_roleSet.salaryRoleId);
            currentRoleSet.myNumberRoleId(_roleSet.myNumberRoleId);
            currentRoleSet.personInfRoleId(_roleSet.personInfRoleId);
            currentRoleSet.employmentRoleId(_roleSet.employmentRoleId);
            currentRoleSet.approvalAuthority(_roleSet.approvalAuthority);
            currentRoleSet.officeHelperRoleId(_roleSet.officeHelperRoleId);
            currentRoleSet.humanResourceRoleId(_roleSet.humanResourceRoleId);
            currentRoleSet.registered(_roleSet.registered);
            currentRoleSet.webMenus(_roleSet.webMenus || []);

            // build swap web menu
            vm.buildSwapWebMenu();

            //build role Name
            vm.listCurrentRoleIds = [];
            vm.listCurrentRoleIds.push(_roleSet.salaryRoleId);
            vm.listCurrentRoleIds.push(_roleSet.myNumberRoleId);
            vm.listCurrentRoleIds.push(_roleSet.personInfRoleId);
            vm.listCurrentRoleIds.push(_roleSet.employmentRoleId);
            vm.listCurrentRoleIds.push(_roleSet.officeHelperRoleId);
            vm.listCurrentRoleIds.push(_roleSet.humanResourceRoleId);
            vm.listCurrentRoleIds = vm.listCurrentRoleIds.filter(function (roleid) {
                return roleid ? true : false;
            });
            vm.buildRoleName();
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
         * build Role name base on list Role id
         */
        buildRoleName() {
            let vm = this;
            vm.clearRoleName();
            if (vm.listCurrentRoleIds && vm.listCurrentRoleIds.length > 0) {
                vm.$ajax('com', API.getRoleNameByListId, (vm.listCurrentRoleIds)).done((itemList) => {
                    if (itemList && itemList.length > 0) {
                        for (var i = 0; i < itemList.length; i++) {
                            vm.setRoleName(itemList[i].roleType, itemList[i].name);
                        }
                    }
                });
            }
        }

        clearRoleName() {
            let vm = this;
            let emName = resource.getText('CAS011_23');
            vm.employmentRoleName(emName);
            vm.hRRoleName(emName);
            vm.salaryRoleName(emName);
            vm.personInfRoleName(emName);
            vm.myNumberRoleName(emName);
            vm.officeHelperRoleName(emName);
        }

        /**
         * Execute get all web menu.
         */
        getAllWebMenus() {
            let vm = this;
            vm.$ajax('com', API.getAllWebMenu).done((itemList: Array<IWebMenu>) => {
                if (itemList && itemList.length > 0) {
                    vm.listAllWebMenus = itemList;
                }
            }).fail(function (error) {
            });
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

        /**
         * Build RoleName by Role Id
         * @param roleId
         */
        settingRoleNameByRoleId(roleType: number, roleId: string): JQueryPromise<any> {
            let vm = this,
                dfd = $.Deferred();
            if (!roleId) {
                //vm.setRoleName(roleType, resource.getText('CAS011_23'));
                dfd.resolve(resource.getText('CAS011_23'));
                return dfd.promise();
            }

            vm.$ajax('com', API.getRoleById, (roleId)).done((item) => {
                if (item) {
                    //vm.setRoleName(roleType, item.name);
                    dfd.resolve(item.name);
                } else {
                    //reset
                    //vm.setRoleId(roleType, '');
                    dfd.resolve(resource.getText('CAS011_23'));
                }

            }).fail(function (error) {
                //reset
                //vm.setRoleId(roleType, '');
                dfd.resolve(resource.getText('CAS011_23'));
            });

            return dfd.promise();
        }

        /**
         * Set role type data
         */
        setRoleId(roleType: number, roleId: string) {
            let vm = this,
                currentRoleSet: RoleSet = vm.currentRoleSet();
            switch (roleType) {
                case ROLE_TYPE.EMPLOYMENT: // A3_6
                    currentRoleSet.employmentRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.EMPLOYMENT, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.EMPLOYMENT, name);
                    });
                    break;
                case ROLE_TYPE.HR: // A3-9
                    currentRoleSet.humanResourceRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.HR, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.HR, name);
                    });
                    break;
                case ROLE_TYPE.SALARY: //A3-12
                    currentRoleSet.salaryRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.SALARY, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.SALARY, name);
                    });
                    break;
                case ROLE_TYPE.PERSON_INF: //A3-15
                    currentRoleSet.personInfRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.PERSON_INF, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.PERSON_INF, name);
                    });
                    break;
                case ROLE_TYPE.MY_NUMBER: //A3-18
                    currentRoleSet.myNumberRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.MY_NUMBER, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.MY_NUMBER, name);
                    });
                    break;
                case ROLE_TYPE.OFFICE_HELPER: //A3-21
                    currentRoleSet.officeHelperRoleId(roleId);
                    vm.settingRoleNameByRoleId(ROLE_TYPE.OFFICE_HELPER, roleId).done((name) => {
                        vm.setRoleName(ROLE_TYPE.OFFICE_HELPER, name);
                    });
                    break;
                default:
                    break;
            }
        }

        /**
         * setFocusAfterSelectRole
         */
        setFocusAfterSelectRole(roleType: number) {
            switch (roleType) {
                case ROLE_TYPE.EMPLOYMENT: // A3_6
                    $('#A3_009').focus();
                    break;
                case ROLE_TYPE.HR: // A3-9
                    $('#A3_012').focus();
                    break;
                case ROLE_TYPE.SALARY: //A3-12
                    $('#A3_015').focus();
                    break;
                case ROLE_TYPE.PERSON_INF: //A3-15
                    $('#A3_018').focus();
                    break;
                case ROLE_TYPE.MY_NUMBER: //A3-18
                    $('#A3_021').focus();
                    break;
                case ROLE_TYPE.OFFICE_HELPER: //A3-21
                    $('#swApprovalAuthority').focus();
                    break;
                default:
                    break;
            }
        }

        /**
         * setRoleName
         */
        setRoleName(roleType: number, roleName: string) {
            let vm = this;
            switch (roleType) {
                case ROLE_TYPE.EMPLOYMENT: // A3_6
                    vm.employmentRoleName(roleName);
                    break;
                case ROLE_TYPE.HR: // A3-9
                    vm.hRRoleName(roleName);
                    break;
                case ROLE_TYPE.SALARY: //A3-12
                    vm.salaryRoleName(roleName);
                    break;
                case ROLE_TYPE.PERSON_INF: //A3-15
                    vm.personInfRoleName(roleName);
                    break;
                case ROLE_TYPE.MY_NUMBER: //A3-18
                    vm.myNumberRoleName(roleName);
                    break;
                case ROLE_TYPE.OFFICE_HELPER: //A3-21
                    vm.officeHelperRoleName(roleName);
                    break;
                default:
                    break;
            }
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
        salaryRoleId: string;
        myNumberRoleId: string;
        personInfRoleId: string;
        employmentRoleId: string;
        approvalAuthority: boolean;
        officeHelperRoleId: string;
        humanResourceRoleId: string;
        webMenus: Array<IWebMenu>;
        registered: boolean;
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
        salaryRoleId: KnockoutObservable<string> = ko.observable('');
        myNumberRoleId: KnockoutObservable<string> = ko.observable('');
        personInfRoleId: KnockoutObservable<string> = ko.observable('');
        employmentRoleId: KnockoutObservable<string> = ko.observable('');
        approvalAuthority: KnockoutObservable<boolean> = ko.observable(true);
        officeHelperRoleId: KnockoutObservable<string> = ko.observable('');
        humanResourceRoleId: KnockoutObservable<string> = ko.observable('');
        registered: KnockoutObservable<boolean> = ko.observable(false);
        webMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);

        constructor(param: IRoleSet) {
            let vm = this;
            vm.companyId(param.companyId);
            vm.roleSetCd(param.roleSetCd || '');
            vm.roleSetName(param.roleSetName || '');
            vm.salaryRoleId(param.salaryRoleId || '');
            vm.webMenus(param.webMenus || []);
            vm.myNumberRoleId(param.myNumberRoleId || '');
            vm.personInfRoleId(param.personInfRoleId || '');
            vm.employmentRoleId(param.employmentRoleId || '');
            vm.officeHelperRoleId(param.officeHelperRoleId || '');
            vm.approvalAuthority(param.approvalAuthority || true);
            vm.registered(param.registered);
        }
    }
}