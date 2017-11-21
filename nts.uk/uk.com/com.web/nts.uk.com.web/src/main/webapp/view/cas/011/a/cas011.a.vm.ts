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

        swApprovalAuthority: KnockoutObservableArray<any>;
        gridColumns: KnockoutObservableArray<NtsGridListColumn>;
        swapColumns: KnockoutObservableArray<NtsGridListColumn>;
        isNewMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this,
            currentRoleSet: IRoleSet = self.currentRoleSet();

            // A2_003, A2_004, A2_005, A2_006 
            self.gridColumns = ko.observableArray([
                                                {headerText: resource.getText('CAS011_09'), key: 'roleSetCd', formatter: _.escape, width: 50},
                                                {headerText: resource.getText('CAS011_10'), key: 'roleSetName', formatter: _.escape, width: 225}
                                           ]);
            self.columns = ko.observableArray([
                                               { headerText: 'コード', key: 'webMenuCode', width: 100 },
                                               { headerText: '名称', key: 'webMenuName', width: 150 }
                                           ]);
            self.isNewMode = ko.observable(false);
            //Subscribe: 項目変更→項目
 
            currentRoleSet.roleSetCd.subscribe(roleSetCd => {
                errors.clearAll();
                if (roleSetCd) {
                    service.getRoleSetByRoleSetCd(roleSetCd).done ((_roleSet : IRoleSet) => {
                        if (_roleSet) {
                            self.createCurrentRoleSet(_roleSet);
                            self.isNewMode(false);
                        } else {
                            //TODO???
                            self.currentRoleSet.roleSetCd('');
                        }
                    });
                } else {
                    self.isNewMode(true);
                    self.createNewCurrentRoleSet();
                }
                $('#inpRoleSetCd').focus();
            });
            // ---A3_024, A3_025 
            self.swApprovalAuthority = ko.observableArray([
                                              { code: true, name: resource.getText('CAS011_22') },
                                              { code: false, name: resource.getText('CAS011_23') }
                                          ]);
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
            
            //実行時情報をチェックする
            
            /*
            service.getLoginUserCompanyId().done((companyId : any) => {
               if (companyId === null) {
                    // TODO back to previous page - トップページに戻る

                    return; 
                } 
            });
            */
            // アルゴリズム「ロールセットをすべて取得する」を実行する
            
            
           service.getAllRoleSets().done((itemList: Array<IRoleSet>) => {
              
                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {

                    self.listRoleSets(itemList);
                    // 先頭のロールセットを選択する
                    
                    self.settingSelectedRoleSet();

                } else { //in case number of RoleSet is zero
                    //画面を新規モードで起動する

                    self.createNewRoleSetMode();
                }

                dfd.resolve();

            }).fail(error => {
              //画面を新規モードで起動する

                self.createNewRoleSetMode();
            });
            
            return dfd.promise();
        }  
 
         /**
          * Setting selected role set.
         */
        settingSelectedRoleSet(selectedRoleSetCd? : String) {
            let self = this,
                currentRoleSet: IRoleSet = self.currentRoleSet(),
                listRoleSets = self.listRoleSets;
            if (self.listRoleSets &&  self.listRoleSets.length > 0) {
                if (!selectedRoleSetCd) {
                    self.currentRoleSet.roleSetCd(self.listRoleSets()[0].roleSetCd);
                } else {
                    let _item: IRoleSet = _.find(ko.toJS(self.listRoleSets), (x: IRoleSet) => x.roleSetCd == selectedRoleSetCd);
                    if (_item) {
                        self.currentRoleSet.roleSetCd(_item.roleSetCd);
                    } else {
                        self.currentRoleSet.roleSetCd(self.listRoleSets()[0].roleSetCd);
                    }
                }
            } else {
                self.currentRoleSet.roleSetCd('');
            }
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
                        self.currentRoleSet().roleSetCd(roleSetCd);
                        dialog.info({ messageId: "Msg_15" });
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
                        //self.currentRoleSet().roleSetCd(roleSetCd);
                        
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
            $('#inpRoleSetCd').focus();
        }
        
        /**
         * delete the role set
         */
        deleteRoleSet() {
            block.invisible();
                let self = this,
                        listRoleSets = self.listRoleSets,
                        currentRoleSet: IRoleSet = self.currentRoleSet();
            //確認メッセージ（Msg_18）を表示する
            
            dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                if (self.currentRoleSet.roleSetCd) {
                    var object : any = {roleSetCd : self.currentRoleSet.roleSetCd}; 
                    service.removeRoleSet(ko.toJS(object)).done(function() {
                        //???dialog.info({ messageId: "Msg_16" });
                        //select next Role Set
                        let index: number = _.findIndex(listRoleSets(), function (x: IRoleSet) { return x.roleSetCd == self.currentRoleSet.roleSetCd()});
                        // remove the deleted item out of list
                        if (index > -1) {
                            self.listRoleSets.splice(index, 1);
                            if (index >= self.listRoleSets().length) {
                                index = self.listRoleSets().length - 1;
                            }
                            if (self.listRoleSets().length > 0) {
                                self.currentRoleSet.roleSetCd(self.listRoleSets()[index].roleSetCd);
                            } else {
                                self.isNewMode(true);
                                self.currentRoleSet.roleSetCd('');
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
        
        
        /** ダイアログ
          * Open dialog CLD025 
         */

        openDialogCLD025(roleType?: number) {
            dialog.alertError("---------TODO-------------");
            if (!roleType) {
                return;
            }
            
            switch (roleType) {
                case ROLE_TYPE.EMPLOYMENT:
                    dialog.info("EMPLOYMENT TYPE");
                    break;
                case ROLE_TYPE.MY_NUMBER:
                    dialog.info("MY_NUMBER TYPE");
                    break;
                case ROLE_TYPE.HR:
                    dialog.info("HR TYPE");
                    break;
                case ROLE_TYPE.PERSON_INF:
                    dialog.info("PERSON_INF TYPE");
                    break;
                case ROLE_TYPE.EMPLOYMENT:
                    dialog.info("EMPLOYMENT TYPE");
                    break;
                case ROLE_TYPE.SALARY:
                    dialog.info("SALARY TYPE");
                    break;
                default:
                    dialog.info("NO ROLE TYPE");
                    break;
            }
            /*
            let self = this;
            windows.setShared('roleType', roleType); //TODO - using IModel???
            windows.setShared('selectedRoleId', selectedRoleId);
            block.invisible();
            
            windows.sub.modal.modal('/view/cps/017/a/index.xhtml', { title: '' }).onClosed(function(): any {
                if (roleType === 0) {
                    //TODO
                }
                block.clear();
            });
            */
        }

        /** ダイアログ
         * Open dialog C
         * 「設定」ボタンをクリック - Click "Setting" button
        */

       openDialogSettingC() {
           let self = this,
               currentRoleSet: RoleSet = this.currentRoleSet();

           if (!currentRoleSet && !currentRoleSet.roleSetCd) {
               return;
           }
           windows.setShared('roleSetCd', currentRoleSet.roleSetCd);
           block.invisible();
           windows.sub.modal.modal('/view/cas/011/c/index.xhtml', { title: '' }).onClosed(function(): any {
               block.clear();
           });
       }

        /**
         * create a new Role Set
         * 画面を新規モードで起動する
         */
        createNewRoleSetMode() {
            let self = this;
            
            self.isNewMode(true);
            self.currentRoleSet().roleSetCd('');
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
         /**
          * Get role from server by roleId
         */

        getRoleById = function(_roleId? :string ) : IRole {
            if (_roleId) {
                return service.getRoleById(_roleId).done ((_role : IRole) => {
                    return _role;
                });           
            } else {
                var role: Role = {
                        roleId: '',
                        roleName: '',
                        roleType: null
                    };
                return role;
            }
        }
        
        
    }

    export enum ROLE_TYPE {
        OFFICE_HELPER,
        MY_NUMBER,
        HR,
        PERSON_INF,
        EMPLOYMENT,
        SALARY
    }

    // Role
    export interface IRole {
        roleId: string;
        roleName: string;
        roleType: number;
    }

    export class Role {
        roleId: KnockoutObservable<string> = ko.observable('');
        roleName: KnockoutObservable<string> = ko.observable('');
        roleType: KnockoutObservable<number> = ko.observable(0);

        constructor(param: IRole) {
            let self = this;
            self.roleId(param.roleId || '');
            self.roleName(param.roleName || '');
            self.roleType(param.roleType || 0);
        }
    }

    // Web menu
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

    //Role Set
    export interface IRoleSet {
        roleSetCd: string;
        roleSetName: string;
        approvalAuthority: boolean;
        officeHelperRole: IRole;
        myNumberRole: IRole;
        hRRole: IRole;
        personInfRole: IRole;
        employmentRole: IRole;
        salaryRole: IRole;
        webMenus: Array<IWebMenu>;
    }

    export class RoleSet {
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');
        approvalAuthority: KnockoutObservable<boolean> = ko.observable(false);
        officeHelperRole : KnockoutObservable<IRole> = ko.observable(null);
        myNumberRole:KnockoutObservable<IRole> = ko.observable(null);
        hRRole: KnockoutObservable<IRole> = ko.observable(null);
        personInfRole:KnockoutObservable<IRole> = ko.observable(null);
        employmentRole: KnockoutObservable<IRole> = ko.observable(null);
        salaryRole: KnockoutObservable<IRole> = ko.observable(null);
    
        webMenus: KnockoutObservableArray<IWebMenu> = ko.observable(null);

        constructor(param: IRoleSet) {
            let self = this;
            self.roleSetCd(param.roleSetCd || '');
            self.roleSetName(param.roleSetName || '');
            self.approvalAuthority(param.approvalAuthority || false);
            self.officeHelperRole(param.officeHelperRole || null);
            self.myNumberRole(param.myNumberRole || null);
            self.hRRole(param.hRRole || null);
            self.personInfRole(param.personInfRole || null);
            self.employmentRole(param.employmentRole || null);
            self.salaryRole(param.salaryRole || null);
            self.webMenus(param.webMenus || null);
        }
    }
}

