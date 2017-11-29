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
            companyId: ''
            ,roleSetCd: ''
            , roleSetName:'' 
            , approvalAuthority: true
            , employmentRoleId: ''
            , humanResourceRoleId: ''
            , salaryRoleId: '' 
            , personInfRoleId: ''
            , myNumberRoleId: '' 
            , officeHelperRoleId: ''
            , webMenus: new Array()
                }));

        selectedRoleSetCd: KnockoutObservable<string> = ko.observable('');
    
        employmentRoleName: KnockoutObservable<string>;
        hRRoleName:         KnockoutObservable<string>;
        salaryRoleName:     KnockoutObservable<string>;
        personInfRoleName:  KnockoutObservable<string>;
        myNumberRoleName:   KnockoutObservable<string>;
        officeHelperRoleName: KnockoutObservable<string>;
    
        gridColumns: KnockoutObservableArray<NtsGridListColumn>;
        swapColumns: KnockoutObservableArray<NtsGridListColumn>;
        swApprovalAuthority: KnockoutObservableArray<any>;    
        isNewMode:          KnockoutObservable<boolean>;
        roleSetCount: KnockoutObservable<number> = ko.observable(0);
    
        constructor() {
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet();

            // A2_003, A2_004, A2_005, A2_006 
            self.gridColumns = ko.observableArray([
                                                {headerText: resource.getText('CAS011_09'), key: 'roleSetCd', formatter: _.escape, width: 40},
                                                {headerText: resource.getText('CAS011_10'), key: 'roleSetName', formatter: _.escape, width: 180}
                                           ]);
            
            self.swapColumns = ko.observableArray([
                                               { headerText: resource.getText('CAS011_9'), key: 'webMenuCode', width: 100 },
                                               { headerText: resource.getText('CAS011_34'), key: 'webMenuName', width: 150 }
                                           ]);
            
            // ---A3_024, A3_025 
            self.swApprovalAuthority = ko.observableArray([
                                              { code: true, name: resource.getText('CAS011_22') },
                                              { code: false, name: resource.getText('CAS011_23') }
                                          ]);

            self.employmentRoleName = ko.observable(self.getRoleNameByRoleId(''));
            self.hRRoleName         = ko.observable(self.getRoleNameByRoleId(''));
            self.salaryRoleName     = ko.observable(self.getRoleNameByRoleId(''));
            self.personInfRoleName  = ko.observable(self.getRoleNameByRoleId(''));
            self.myNumberRoleName   = ko.observable(self.getRoleNameByRoleId(''));
            self.officeHelperRoleName = ko.observable(self.getRoleNameByRoleId(''));
            
            self.isNewMode = ko.observable(true);

            //Subscribe: 項目変更→項目
 
            //currentRoleSet.roleSetCd.subscribe(roleSetCd => {
            self.selectedRoleSetCd.subscribe(roleSetCd => {
                errors.clearAll();

                // do not process anything if it is new mode.
                if (roleSetCd) {
                    service.getRoleSetByRoleSetCd(roleSetCd).done ((_roleSet : IRoleSet) => {
                        if (_roleSet && _roleSet.roleSetCd) {
                            self.createCurrentRoleSet(_roleSet);
                            self.settingUpdateMode(_roleSet.roleSetCd);
                        } else {
                            self.settingCreateMode();
                        }
                    });
                } else {
                    self.createNewCurrentRoleSet();
                    self.settingCreateMode();
                }
                self.setFocus();
            });
            
            //Setting role name
            currentRoleSet.employmentRoleId.subscribe(employmentRoleId => {
                self.employmentRoleName(self.getRoleNameByRoleId(employmentRoleId));
            });
            currentRoleSet.humanResourceRoleId.subscribe(hRRoleId => {
                self.hRRoleName(self.getRoleNameByRoleId(hRRoleId));
            });
            currentRoleSet.salaryRoleId.subscribe(salaryRoleId => {
                self.salaryRoleName(self.getRoleNameByRoleId(salaryRoleId));
            });
            currentRoleSet.personInfRoleId.subscribe(personInfRoleId => {
                self.personInfRoleName(self.getRoleNameByRoleId(personInfRoleId));
            });
            currentRoleSet.myNumberRoleId.subscribe(myNumberRoleId => {
                self.myNumberRoleName(self.getRoleNameByRoleId(myNumberRoleId));
            });
            currentRoleSet.officeHelperRoleId.subscribe(officeHelperRoleId => {
                self.officeHelperRoleName(self.getRoleNameByRoleId(officeHelperRoleId));
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

            service.getCompanyIdOfLoginUser().done((companyId: any) => {
                if (!companyId) {
                    self.backToTopPage();
                    dfd.reject();
                 } else {
                     // initial screen
                     self.initialScreen(dfd, '');
                 }
             }).fail(error => {
                 self.backToTopPage();
                 dfd.resolve();
             });
             
           return dfd.promise();
        }  
 
        /**
         * back to top page - トップページに戻る
         */
        backToTopPage() {
            windows.sub.modeless("/view/ccg/008/a/index.xhtml");
        }

        /**
         * Initial screen
         * - アルゴリズム「ロールセットをすべて取得する」を実行する - Execute the algorithm Get all Roll Set
         * - 先頭のロールセットを選択する - Select the first roll set
         * - 画面を新規モードで起動する - Start screen in new mode
         */
        initialScreen(deferred : any, roleSetCd : string) {
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet(),
            listRoleSets = self.listRoleSets;

            service.getAllRoleSet().done((itemList: Array<IRoleSet>) => {
                
                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {
                    listRoleSets(itemList);
                    // 先頭のロールセットを選択する
                    
                    self.roleSetCount(itemList.length);
                    let index : number = 0;
                    if (roleSetCd) {
                        index = _.findIndex(listRoleSets(), function (x: IRoleSet) 
                                { return x.roleSetCd == roleSetCd});
                        if (index === -1) index = 0;
                    }
                    self.settingUpdateMode(listRoleSets()[index].roleSetCd);

                } else { //in case number of RoleSet is zero
                    //画面を新規モードで起動する
                    
                    self.createNewCurrentRoleSet();
                    self.settingCreateMode();
                }
            }).fail(error => {
              //画面を新規モードで起動する
                
                self.createNewCurrentRoleSet();
                self.settingCreateMode();
            }).always(()=> {
                self.roleSetCount(self.listRoleSets().length);
                if (deferred) {
                    deferred.resolve();
                }
            });
        }
        /**
         * Save
         */
        saveRoleSet() {
            let self = this,
            currentRoleSet: RoleSet = self.currentRoleSet();
            $('.nts-input').trigger("validate");
            if (errors.hasError() === false) {
                block.invisible();
                if (self.isNewMode()) {
                    // create new role set
                    service.addRoleSet(ko.toJS(currentRoleSet)).done((roleSetCd) => {
                        dialog.info({ messageId: "Msg_15" });
                        // refresh - initial screen
                        self.initialScreen(null, currentRoleSet.roleSetCd());
                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                        if (error.messageId == 'Msg_3') {
                            $('#inpRoleSetCd').ntsError('set', error);
                        }
                        if (error.messageId == 'Msg_583') {
                            //$('#ctrSelectionMenu').ntsError('set', error);
                        }
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    // update
                    service.updateRoleSet(ko.toJS(currentRoleSet)).done((roleSetCd) => {
                        dialog.info({ messageId: "Msg_15" });
                     // refresh - initial screen
                        self.initialScreen(null, currentRoleSet.roleSetCd());
                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                        if (error.messageId == 'Msg_583') {
                          //$('#ctrSelectionMenu').ntsError('set', error);
                        }
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
                if (currentRoleSet.roleSetCd()) {
                    var object : any = {roleSetCd : currentRoleSet.roleSetCd()}; 
                    service.removeRoleSet(ko.toJS(object)).done(function() {
                        dialog.info({ messageId: "Msg_16" });
                        //select next Role Set
                        let index: number = _.findIndex(listRoleSets(), function (x: IRoleSet) 
                                { return x.roleSetCd == currentRoleSet.roleSetCd()});
                        // remove the deleted item out of list
                        if (index > -1) {
                            self.listRoleSets.splice(index, 1);
                            if (index >= listRoleSets().length) {
                                index = listRoleSets().length - 1;
                            }
                            if (listRoleSets().length > 0) {
                                self.settingUpdateMode(listRoleSets()[index].roleSetCd);
                            } else {
                                self.settingCreateMode();
                            }
                        }
                        
                    }).fail(function(error) {
                        dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        block.clear();
                    });
                } else {
                    block.clear();
                }
            }).ifNo(() => {
                block.clear();
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
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet();
            
            if (!rlType && rlType < 0) {
                return;
            }
            block.invisible();

            windows.setShared('roleType', rlType); //TODO - using IModel???

            windows.sub.modal('/view/ccg/025/index.xhtml', { title: '' }).onClosed(function(): any {
              //get data from share window
                var roleId = windows.getShared('roleId');
                var roleName = windows.getShared('roleName');
                
                dialog.info("Data from dialog: " + roleId + " - " + roleName);

                // set data back 
                switch (rlType) {
                case ROLE_TYPE.EMPLOYMENT:
                    currentRoleSet.employmentRoleId(roleId);
                    break;
                case ROLE_TYPE.HR:
                    currentRoleSet.humanResourceRoleId(roleId);
                    break;
                case ROLE_TYPE.SALARY:
                    currentRoleSet.salaryRoleId(roleId);
                    break;
                case ROLE_TYPE.PERSON_INF:
                    currentRoleSet.personInfRoleId(roleId);
                    break;
                case ROLE_TYPE.MY_NUMBER:
                    currentRoleSet.myNumberRoleId(roleId);
                    break;
                case ROLE_TYPE.OFFICE_HELPER:
                    currentRoleSet.officeHelperRoleId(roleId);
                    break;
                default:
                    dialog.info("NO ROLE TYPE" + roleId + " - " + roleName);
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
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet();
            
            // clear selected role set
            self.selectedRoleSetCd('');

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

           self.selectedRoleSetCd(selectedRoleSetCd);

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
            
            currentRoleSet.roleSetCd('');
            currentRoleSet.roleSetName('');
            currentRoleSet.approvalAuthority(true);
            currentRoleSet.officeHelperRoleId('');
            currentRoleSet.myNumberRoleId('');
            currentRoleSet.humanResourceRoleId('');
            currentRoleSet.personInfRoleId('');
            currentRoleSet.employmentRoleId('');
            currentRoleSet.salaryRoleId('');
            currentRoleSet.webMenus(new Array());
            
            // build swap web menu
            self.buildSwapWebMenu();
            
        }
        /**
         * BindData to currentRoleSet
         * @param _roleSet
         */
        createCurrentRoleSet(_roleSet: IRoleSet) {
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet();
            
            // listWebMenus = self.listWebMenus;
            currentRoleSet.companyId = _roleSet.companyId;
            currentRoleSet.roleSetCd(_roleSet.roleSetCd);
            currentRoleSet.roleSetName(_roleSet.roleSetName);
            currentRoleSet.approvalAuthority(_roleSet.approvalAuthority);
            currentRoleSet.officeHelperRoleId(_roleSet.officeHelperRoleId);
            currentRoleSet.myNumberRoleId(_roleSet.myNumberRoleId);
            currentRoleSet.humanResourceRoleId(_roleSet.humanResourceRoleId);
            currentRoleSet.personInfRoleId(_roleSet.personInfRoleId);
            currentRoleSet.employmentRoleId(_roleSet.employmentRoleId);
            currentRoleSet.salaryRoleId(_roleSet.salaryRoleId);
            currentRoleSet.webMenus(_roleSet.webMenus);

            // build swap web menu
            self.buildSwapWebMenu();

        }
 
        /**
         * build swap web menu
         */
        buildSwapWebMenu() {
            let self = this,
                currentRoleSet: RoleSet = self.currentRoleSet();
            
            self.listWebMenus.removeAll();

            service.getAllWebMenu().done((itemList: Array<IWebMenu>) => {
                if (itemList && itemList.length > 0) {
                    self.listWebMenus(itemList.filter(item1 => !self.isSelectedWebMenu(item1.webMenuCode)));
                    // get Web Menu Name for Web menu
                    let listWebMenuRight = itemList.filter(item1 => self.isSelectedWebMenu(item1.webMenuCode));
                    currentRoleSet.webMenus.removeAll();
                    currentRoleSet.webMenus(listWebMenuRight);
                    
                }
             }).fail(function(error) {
                 dialog.alertError({ messageId: error.messageId });
             });  
        }
        /**
         * Check and return true if the Web menu code existed in current selected web menu list.
         * 
         */
        isSelectedWebMenu = function(_webMenuCode : string) : boolean {
            let self = this,
            currentRoleSet: RoleSet = this.currentRoleSet();
            
            if (!_webMenuCode || !currentRoleSet
                    || !currentRoleSet.webMenus() || currentRoleSet.webMenus().length === 0){
                return false;
            }
            let index: number = _.findIndex(currentRoleSet.webMenus(), function (x: IWebMenu) { return x.webMenuCode === _webMenuCode});
            return (index > -1);            
        }
        
        /**
         * Build RoleName by Role Id
         * @param roleId
         */
        getRoleNameByRoleId(roleId : string) : string {
            if (!roleId) {
                return resource.getText('CAS011_23');
            }
            return resource.getText('CAS011_23');
            service.getRoleById(roleId).done((item) => {
                if (item) {
                    return item.roleName;
                } else {
                    return resource.getText('CAS011_23');
                }
            }).fail(function(error) {
                    return resource.getText('CAS011_23')
            });
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
        companyId: string;
        roleSetCd: string;
        roleSetName: string;
        approvalAuthority: boolean;
        employmentRoleId: string;
        humanResourceRoleId: string;
        salaryRoleId: string;
        personInfRoleId: string;
        myNumberRoleId: string;
        officeHelperRoleId: string;
        webMenus: Array<IWebMenu>;
    
    }

    export class RoleSet {
        companyId = '';
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');
        approvalAuthority: KnockoutObservable<boolean> = ko.observable(true);
        employmentRoleId: KnockoutObservable<string> = ko.observable(null);
        humanResourceRoleId: KnockoutObservable<string> = ko.observable(null);
        salaryRoleId: KnockoutObservable<string> = ko.observable(null);
        personInfRoleId: KnockoutObservable<string> = ko.observable(null);
        myNumberRoleId: KnockoutObservable<string> = ko.observable(null);    
        officeHelperRoleId: KnockoutObservable<string> = ko.observable(null);   
        webMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);

        constructor(param: IRoleSet) {
            let self = this;
            self.companyId = param.companyId;
            self.roleSetCd(param.roleSetCd || '');
            self.roleSetName(param.roleSetName || '');
            self.approvalAuthority(param.approvalAuthority || true);
            self.employmentRoleId(param.employmentRoleId || '');
            self.humanResourceRoleId(param.humanResourceRoleId || '');
            self.salaryRoleId(param.salaryRoleId || '');
            self.personInfRoleId(param.personInfRoleId || '');
            self.myNumberRoleId(param.myNumberRoleId || '');
            self.officeHelperRoleId(param.officeHelperRoleId || '');
            self.webMenus(param.webMenus || new Array());            
        }
    }
}

