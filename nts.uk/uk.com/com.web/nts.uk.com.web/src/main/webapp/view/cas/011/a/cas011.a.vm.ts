module nts.uk.com.view.cas011.a.viewmodel {
    import resource = nts.uk.resource;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;

    export class ScreenModel {
        //list of Role Set
        listRoleSets: KnockoutObservableArray<IRoleSet> = ko.observableArray([]);
        listWebMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);

        currentRoleSet: KnockoutObservable<RoleSet> = ko.observable(new RoleSet({
            roleSetCd: ''
            , roleSetName:'' 
            , approvalAuthority: false
            , officeHelperRole : null 
            , myNumberRole: null
            , hRRole: null
            , personInfRole: null 
            , employmentRole: null
            , salaryRole: null
            , webMenus: null
                }));

        gridColumns: KnockoutObservableArray<NtsGridListColumn>;
        swapColumns: KnockoutObservableArray<NtsGridListColumn>;
        swApprovalAuthority: KnockoutObservableArray<any>;    
        isNewMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet();

            // A2_003, A2_004, A2_005, A2_006 
            self.gridColumns = ko.observableArray([
                                                {headerText: resource.getText('CAS011_09'), key: 'roleSetCd', formatter: _.escape, width: 40},
                                                {headerText: resource.getText('CAS011_10'), key: 'roleSetName', formatter: _.escape, width: 180}
                                           ]);
            
            self.swapColumns = ko.observableArray([
                                               { headerText: 'コード', key: 'webMenuCode', width: 100 },
                                               { headerText: '名称', key: 'webMenuName', width: 150 }
                                           ]);
            
            // ---A3_024, A3_025 
            self.swApprovalAuthority = ko.observableArray([
                                              { code: true, name: resource.getText('CAS011_22') },
                                              { code: false, name: resource.getText('CAS011_23') }
                                          ]);

            self.isNewMode = ko.observable(false);
            //Subscribe: 項目変更→項目
 
            currentRoleSet.roleSetCd.subscribe(roleSetCd => {
                errors.clearAll();
                if (roleSetCd) {
                    service.getRoleSetByRoleSetCd(roleSetCd).done ((_roleSet : IRoleSet) => {
                        if (_roleSet && _roleSet.roleSetCd) {
                            self.createCurrentRoleSet(_roleSet);
                            self.settingUpdateMode(_roleSet.roleSetCd);
                        } else {
                            //TODO???
                            self.settingCreateMode();
                        }
                    });
                } else {
                    self.createNewCurrentRoleSet();
                    self.settingCreateMode();
                }
                self.setFocus();
            });
        }

        /**
         * 開始
         **/
        start(): JQueryPromise<any> {
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet(),
                listRoleSets = self.listRoleSets,
                dfd = $.Deferred();
            
            listRoleSets.removeAll();
            errors.clearAll();
                     
            // 実行時情報をチェックする- check runtime

/*
            service.getLoginUserCompanyId().done((companyId : any) => {
                dialog.info("checkRuntime 00: " + companyId);
                if (!companyId || companyId === 'undefined') {
                    self.backToTopPage();
                 } else {
                     // initial screen
                     self.initialScreen(dfd);
                 }
                //dialog.info("checkRuntime 20: " + retChk);
             }).fail(error => {
                 dfd.reject();
                 dialog.info("checkRuntime 21: " );
                 //retChk = false;
             });
*/
            /*
            if (!self.checkRuntime(dfd)) {
                self.backToTopPage();
            }
*/
           return dfd.promise();
        }  
 
        /**
         * アルゴリズム「ロールセットをすべて取得する」を実行する - Process check runtime at start
         */
        /*
        checkRuntime(deferred : any) : boolean {
            let self = this;
            var retChk : boolean = true;
            service.getLoginUserCompanyId().done((companyId : any) => {
                dialog.info("checkRuntime 00: " + companyId);
                if (!companyId || companyId === 'undefined') {
                    retChk = false;
                 } else {
                     // initial screen
                     self.initialScreen(deferred);
                 }
                dialog.info("checkRuntime 20: " + retChk);
             }).fail(error => {
                 dialog.info("checkRuntime 21: " + retChk);
                 retChk = false;
             });

            dialog.info("checkRuntime 22: " + retChk);
             return retChk;
        }
        */
        /**
         * back to top page - トップページに戻る
         */
        backToTopPage() {
            windows.sub.modeless("/view/ccg/008/a/index.xhtml");
        }

        /**
         * Initial screen
         * - アルゴリズム「ロールセットをすべて取得する」を実行する - Execute the algorithm Get All Roll Sets
         * - 先頭のロールセットを選択する - Select the first roll set
         * - 画面を新規モードで起動する - Start screen in new mode
         */
        initialScreen(deferred : any) {
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet(),
            listRoleSets = self.listRoleSets;

            service.getAllRoleSets().done((itemList: Array<IRoleSet>) => {
                
                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {
                    self.listRoleSets(itemList);
                    // 先頭のロールセットを選択する
                    
                    self.settingUpdateMode(self.listRoleSets()[0].roleSetCd);

                } else { //in case number of RoleSet is zero
                    //画面を新規モードで起動する

                    self.settingCreateMode();
                }

                deferred.resolve();

            }).fail(error => {
              //画面を新規モードで起動する

                deferred.reject();
                self.settingCreateMode();
            });
        }
        /**
         * Save
         */
        saveRoleSet() {
            let self = this;
            block.invisible();
            $('.nts-input').trigger("validate");
            if (errors.hasError() === false) {
                if (self.isNewMode()) {
                    // create new role set
                    service.addRoleSet(ko.toJS(self.currentRoleSet())).done((roleSetCd) => {
                        dialog.info({ messageId: "Msg_15" });
                        self.settingUpdateMode(roleSetCd);
                    }).fail(function(error) {
                        if (error.messageId == 'Msg_583') {
                            //$('#ctrSelectionMenu').ntsError('set', error);
                        }
                        dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateRoleSet(ko.toJS(self.currentRoleSet())).done((roleSetCd) => {
                        dialog.info({ messageId: "Msg_15" });
                        self.settingUpdateMode(roleSetCd);
                    }).fail(function(error) {
                        if (error.messageId == 'Msg_583') {
                          //$('#ctrSelectionMenu').ntsError('set', error);
                        }
                        dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                }
            }
            
        }
        
        /**
         * delete the role set
         */
        deleteRoleSet() {
            let self = this,
                    listRoleSets = self.listRoleSets,
                    currentRoleSet: RoleSet = self.currentRoleSet();
             block.invisible();
            //確認メッセージ（Msg_18）を表示する
            
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (self.currentRoleSet.roleSetCd) {
                    var object : any = {roleSetCd : self.currentRoleSet.roleSetCd}; 
                    service.removeRoleSet(ko.toJS(object)).done(function() {
                        dialog.info({ messageId: "Msg_16" });
                        //select next Role Set
                        let index: number = _.findIndex(listRoleSets(), function (x: IRoleSet) { return x.roleSetCd == self.currentRoleSet.roleSetCd()});
                        // remove the deleted item out of list
                        if (index > -1) {
                            self.listRoleSets.splice(index, 1);
                            if (index >= self.listRoleSets().length) {
                                index = self.listRoleSets().length - 1;
                            }
                            if (self.listRoleSets().length > 0) {
                                self.settingUpdateMode(self.listRoleSets()[index].roleSetCd);
                            } else {
                                self.settingCreateMode();
                            }
                        }
                        
                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                };
            });
        }

        /**
         * setting focus base on screen mode
         */
        setFocus() {
            let self = this;
            if (self.isNewMode()) {
                $('#inpRoleSetCd').focus();
            } else {
                $('#inpRoleSetName').focus();
            }
        }
        
        /** ダイアログ
          * Open dialog CLD025 
         */
        openDialogCLD025(rlType: number) {
            dialog.info("RoleType: " + rlType);
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet();
            
            if (!rlType && rlType < 0) {
                return;
            }
            block.invisible();

            windows.setShared('roleType', rlType); //TODO - using IModel???

            windows.sub.modal('/view/ccg/025/index.xhtml', { title: '' }).onClosed(function(): any {
                var rlId = windows.setShared('roleId');
                var rlName = windows.setShared('roleName');
                
                dialog.info("Data from dialog: " + rlId + " - " + rlName);
                
                if (!rlId || !rlName) {
                    block.clear();
                    return;
                }
                //get data from share window
                var selectedRole : Role = new Role({ roleId: rlId
                                                    , roleName: rlName
                                                    , roleType: rlType
                                                    });

                // set data back 
                switch (rlType) {
                case ROLE_TYPE.EMPLOYMENT:
                    dialog.info("EMPLOYMENT TYPE: " + rlId + " - " + rlName);
                    self.currentRoleSet.employmentRole(selectedRole);
                    break;
                case ROLE_TYPE.HR:
                    dialog.info("HR TYPE" + rlId + " - " + rlName);
                    self.currentRoleSet.hRRole(selectedRole);
                    break;
                case ROLE_TYPE.SALARY:
                    dialog.info("SALARY TYPE" + rlId + " - " + rlName);
                    self.currentRoleSet.salaryRole(selectedRole);
                    break;
                case ROLE_TYPE.PERSON_INF:
                    dialog.info("PERSON_INF TYPE" + rlId + " - " + rlName);
                    self.currentRoleSet.personInfRole(selectedRole);
                    break;
                case ROLE_TYPE.MY_NUMBER:
                    dialog.info("MY_NUMBER TYPE" + rlId + " - " + rlName);
                    self.currentRoleSet.myNumberRole(selectedRole);
                    break;
                case ROLE_TYPE.OFFICE_HELPER:
                    dialog.info("OFFICE_HELPER TYPE" + rlId + " - " + rlName);
                    self.currentRoleSet.officeHelperRole(selectedRole);
                    break;
                default:
                    dialog.info("NO ROLE TYPE" + rlId + " - " + rlName);
                    break;
                }
                block.clear();
            });

        }

        /** ダイアログ
         * Open dialog C
         * 「設定」ボタンをクリック - Click "Setting" button
        */

       openDialogSettingC() {
           block.invisible();
           windows.sub.modal('/view/cas/011/c/index.xhtml', { title: '' }).onClosed(function(): any {
               block.clear();
           });
       }

        /**
         * create a new Role Set
         * 画面を新規モードで起動する
         */
       settingCreateMode() {
            let self = this;
            
            // clear selected role set
            self.currentRoleSet().roleSetCd('');
            
            // Set new mode
            self.isNewMode(true);
            
            // focus
            self.setFocus();
        }
        
        /**
         * Setting selected role set.
        */
       settingUpdateMode(selectedRoleSetCd) {
           let self = this,
               currentRoleSet: RoleSet = self.currentRoleSet();
           //Setting selected Role set
           self.currentRoleSet.roleSetCd(selectedRoleSetCd);

           //Setting update mode
           self.isNewMode(false);
           
           // set focus
           self.setFocus();
       }
       
        /**
         * BindNoData to currentRoleSet
         */
        createNewCurrentRoleSet() {
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet();
            
            self.currentRoleSet.roleSetCd('');
            self.currentRoleSet.roleSetName('');
            self.currentRoleSet.approvalAuthority(false);
            self.currentRoleSet.officeHelperRole(null);
            self.currentRoleSet.myNumberRole(null);
            self.currentRoleSet.hRRole(null);
            self.currentRoleSet.personInfRole(null);
            self.currentRoleSet.employmentRole(null);
            self.currentRoleSet.salaryRole(null);
            self.currentRoleSet.webMenus(null);
            
        }
        /**
         * BindData to currentRoleSet
         * @param _roleSet
         */
        createCurrentRoleSet(_roleSet: IRoleSet) {
            let self = this,
                currentRoleSet: RoleSet = this.currentRoleSet(),
                listWebMenus = this.listWebMenus();

            self.currentRoleSet.roleSetCd(_roleSet.roleSetCd);
            self.currentRoleSet.roleSetName(_roleSet.roleSetName);
            self.currentRoleSet.approvalAuthority(_roleSet.approvalAuthority);
            self.currentRoleSet.officeHelperRole(_roleSet.officeHelperRole);
            self.currentRoleSet.myNumberRole(_roleSet.myNumberRole);
            self.currentRoleSet.hRRole(_roleSet.hRRole);
            self.currentRoleSet.personInfRole(_roleSet.personInfRole);
            self.currentRoleSet.employmentRole(_roleSet.employmentRole);
            self.currentRoleSet.salaryRole(_roleSet.salaryRole);
            self.currentRoleSet.webMenus(_roleSet.webMenus);
            
            self.listWebMenus.removeAll();

            service.getAllWebMenus().done((itemList: Array<IWebMenu>) => {
                if (itemList && itemList.length > 0) {
                    self.listWebMenus = itemList.filter(item1 => !self.isSelectedWebMenu(item1.webMenuCode)).map(item => { new WebMenu({
                        webMenuCode : item.webMenuCode,
                        webMenuName : item.webMenuName,
                    })
                    });
                }
             });           
            $('#inpRoleSetCd').focus();
        }
 
        /**
         * Check and return true if the Web menu code existed in current selected web menu list.
         * 
         */
        isSelectedWebMenu = function(_webMenuCode : string) : boolean {
            let self = this,
            currentRoleSet: IRoleSet = this.currentRoleSet();
            
            if (!_webMenuCode || !self.currentRoleSet
                    || self.currentRoleSet.webMenus || self.currentRoleSet.webMenus.length === 0){
                return false;
            }
            let arrayWebMenus : Array<IWebMenu> = self.currentRoleSet.webMenus;
            for(var i = 0; i < arrayWebMenus.length; i++) {
                if (arrayWebMenus[i].webMenuCode === _webMenuCode) {
                    return true;
                }
            }
            return false;
        }
    }

    
    /**
     * The enum of ROLE TYPE 
     */
    export enum ROLE_TYPE {
        EMPLOYMENT = 0,
        HR,
        SALARY,
        PERSON_INF,
        MY_NUMBER,
        OFFICE_HELPER
    }

    // The Role
    export interface IRole {
        roleId: string;
        roleName: string;
        roleType: number;
    }

    export class Role {
        roleId: KnockoutObservable<string> = ko.observable('');
        roleName: KnockoutObservable<string> = ko.observable('');
        roleType: KnockoutObservable<number> = ko.observable(null);

        constructor(param: IRole) {
            let self = this;
            self.roleId(param.roleId || '');
            self.roleName(param.roleName || resource.getText('CAS011_23'));
            self.roleType(param.roleType); //null: undefined role type
        }
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
            let self = this;
            self.webMenuCode(param.webMenuCode || '');
            self.webMenuName(param.webMenuName || '');
        }
    }

    // The Role Set
    export interface IRoleSet {
        roleSetCd: string;
        roleSetName: string;
        approvalAuthority: boolean;
        employmentRole: Role;
        hRRole: Role;
        salaryRole: Role;
        personInfRole: Role;
        myNumberRole: Role;
        officeHelperRole: Role;
        webMenus: Array<WebMenu>;
    }

    export class RoleSet {
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');
        approvalAuthority: KnockoutObservable<boolean> = ko.observable(false);
        employmentRole: KnockoutObservable<Role> = ko.observable(null);
        hRRole: KnockoutObservable<Role> = ko.observable(null);
        salaryRole: KnockoutObservable<Role> = ko.observable(null);
        personInfRole:KnockoutObservable<Role> = ko.observable(null);
        myNumberRole:KnockoutObservable<Role> = ko.observable(null);    
        officeHelperRole : KnockoutObservable<Role> = ko.observable(null);   
        webMenus: KnockoutObservableArray<WebMenu> = ko.observableArray([]);

        constructor(param: IRoleSet) {
            let self = this;
            self.roleSetCd(param.roleSetCd || '');
            self.roleSetName(param.roleSetName || '');
            self.approvalAuthority(param.approvalAuthority || true);
            self.employmentRole(param.employmentRole || new Role({
                                                            roleId: ''
                                                            , roleName: ''
                                                            , roleType: ROLE_TYPE.EMPLOYMENT
                                                                }));
            self.hRRole(param.hRRole || new Role({
                                                            roleId: ''
                                                            , roleName: ''
                                                            , roleType: ROLE_TYPE.HR
                                                                }));
            self.salaryRole(param.salaryRole || new Role({
                                                            roleId: ''
                                                            , roleName: ''
                                                            , roleType: ROLE_TYPE.SALARY
                                                                }));
            self.personInfRole(param.personInfRole || new Role({
                                                                roleId: ''
                                                                , roleName: ''
                                                                , roleType: ROLE_TYPE.PERSON_INF
                                                                    }));
            self.myNumberRole(param.myNumberRole || new Role({
                                                                roleId: ''
                                                                , roleName: ''
                                                                , roleType: ROLE_TYPE.MY_NUMBER
                                                                    }));
            self.officeHelperRole(param.officeHelperRole || new Role({
                                                                roleId: ''
                                                                , roleName: ''
                                                                , roleType: ROLE_TYPE.OFFICE_HELPER
                                                                    }));
            self.webMenus(param.webMenus || new Array());            
        }
    }
}

