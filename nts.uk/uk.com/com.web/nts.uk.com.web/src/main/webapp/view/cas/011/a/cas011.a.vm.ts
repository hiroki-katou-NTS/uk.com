module nts.uk.com.view.cas016.a.viewmodel {
    import resource = nts.uk.resource;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        //list of Role Set
        listRoleSets: KnockoutObservableArray<IRoleSet> = ko.observableArray([]);
        listWebMenus: KnockoutObservableArray<IWebMenu> = ko.observableArray([]);
    
        currentRoleSet: KnockoutObservable<RoleSet> = ko.observable(new RoleSet({
            roleSetCd: ''
            , roleSetName:'' 
            , approvalAuthority: false
            , officeHelperRoleId : null 
            , myNumberRoleId: null
            , hRRoleId: null
            , personInfRoleId: null 
            , employmentRoleId: null
            , salaryRoleId: null
            , webMenuCds: null
                }));

        swApprovalAuthority: KnockoutObservableArray<any>;
        gridColumns: KnockoutObservableArray<NtsGridListColumn>;
        isNewMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this,
            currentRoleSet: IRoleSet = self.currentRoleSet();

            // A2_003, A2_004, A2_005, A2_006 
            self.gridColumns = ko.observableArray([
                                                {headerText: resource.getText('CAS011_09'), key: 'roleSetCd', formatter: _.escape, width: 20},
                                                {headerText: resource.getText('CAS011_10'), key: 'roleSetName', formatter: _.escape, width: 225}
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
            self.swApprovalAuthority = ko.observableArray([
                                              { code: 0, name: resource.getText('CAS011_22') },
                                              { code: 1, name: resource.getText('CAS011_23') }
                                          ]);
        }

        /**
         * 開始
         **/
        start(): JQueryPromise<any> {
            let self = this,
                currentRoleSet: IRoleSet = self.currentRoleSet(),
                listRoleSets = self.listRoleSets,
                dfd = $.Deferred();
            
            listRoleSets.removeAll();
            errors.clearAll();
            
            //実行時情報をチェックする

            service.getLoginUserCompanyId().done((companyId : any) => {
                if (companyId === null) {
                    // TODO back to previous page - トップページに戻る

                    return; 
                } 
            });
            
            // アルゴリズム「ロールセットをすべて取得する」を実行する
            
            service.getAllRoleSets().done((itemList: Array<IRoleSet>) => {
               
                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {

                    self.listRoleSets(itemList);
                    // 先頭のロールセットを選択する
                    
                    self.settingSelectedRoleSet();

                } else { //in case number of RoleSet is zero
                    //画面を新規モードで起動する
                    self.isNewMode(true);
                    self.currentRoleSet.roleSetCd('');
                }
                
                dfd.resolve();

            }).fail(error => {
              //画面を新規モードで起動する

                self.isNewMode(true);
                self.currentRoleSet.roleSetCd('');
            });
            
            return dfd.promise();
        }  
 
         /**
          * Setting selected role set.
         */
        private settingSelectedRoleSet(selectedRoleSetCd? : String) {
            let self = this,
                currentRoleSet: IRoleSet = self.currentRoleSet(),
                listRoleSets = self.listRoleSets;
            if (self.listRoleSets &&  listRoleSets.length > 0) {
                if (!selectedRoleSetCd) {
                    self.currentRoleSet.roleSetCd(self.listRoleSets()[0].roleSetCd);
                } else {
                    let _item: IRoleSet = _.find(ko.toJS(self.listRoleSets), (x: IRoleSet) => x.roleSetCd == roleSetCd);
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
        public saveRoleSet() {
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
        public deleteRoleSet() {
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

        openDialogCLD025(roleType?: number, selectedRoleId?: string) {
            dialog.alertError("---------TODO-------------");
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

       openDialogC(roleSetCd?: string) {
           let self = this;
           if (!roleSetCd) {
               return;
           }
           windows.setShared('roleSetCd', roleSetCd);
           block.invisible();
           windows.sub.modal.modal('/view/cas/011/c/index.xhtml', { title: '' }).onClosed(function(): any {
               block.clear();
           });
       }

        /**
         * create a new Role Set
         */
        public createNewRoleSet() {
            let self = this;
            self.currentRoleSet().roleSetCd('');
        }
        
        /**
         * BindNoData to currentRoleSet
         */

        public createNewCurrentRoleSet() {
            let self = this,
                currentRoleSet: IRoleSet = self.currentRoleSet();
            
            self.currentRoleSet.roleSetCd('');
            self.currentRoleSet.roleSetName('');
            self.currentRoleSet.approvalAuthority(false);
            self.currentRoleSet.officeHelperRoleId(null);
            self.currentRoleSet.myNumberRoleId(null);
            self.currentRoleSet.hRRoleId(null);
            self.currentRoleSet.personInfRoleId(null);
            self.currentRoleSet.employmentRoleId(null);
            self.currentRoleSet.salaryRoleId(null);
            self.currentRoleSet.webMenuCds(null);
            
        }
        /**
         * BindData to currentRoleSet
         * @param _roleSet
         */
        private createCurrentRoleSet(_roleSet: IRoleSet) {
            let self = this,
                currentRoleSet: IRoleSet = self.currentRoleSet(),
                listWebMenus = self.listWebMenus();
            self.currentRoleSet.roleSetCd(_roleSet.roleSetCd);
            self.currentRoleSet.roleSetName(_roleSet.roleSetName);
            self.currentRoleSet.approvalAuthority(_roleSet.approvalAuthority);
            self.currentRoleSet.officeHelperRoleId(self.getRoleById(_roleSet.officeHelperRoleId));
            self.currentRoleSet.myNumberRoleId(getRoleById(self._roleSet.myNumberRoleId));
            self.currentRoleSet.hRRoleId(getRoleById(self._roleSet.hRRoleId));
            self.currentRoleSet.personInfRoleId(getRoleById(self._roleSet.personInfRoleId));
            self.currentRoleSet.employmentRoleId(getRoleById(self._roleSet.employmentRoleId));
            self.currentRoleSet.salaryRoleId(getRoleById(self._roleSet.salaryRoleId));
            self.currentRoleSet.webMenuCds(_roleSet.webMenuCds);
            
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
            //TODO - set web menu to control
            
            $('#inpRoleSetCd').focus();
        }
 
        var isSelectedWebMenu = function(_webMenuCd : string) : boolean {
            let self = this,
            currentRoleSet: IRoleSet = self.currentRoleSet();
            if (!_webMenuCd || !currentRoleSet) {
                return false;
            }
            let webMenuCds : Array<string> = self.currentRoleSet.webMenuCds;
            for(var i = 0; i < webMenuCds.length; i++) {
                if (webMenuCds[i] === _webMenuCd) {
                    return true;
                }
            }
            return false;
        }
         /**
          * Get role from server by roleId
         */

        var getRoleById = function(_roleId? :string ) : IRole {
            if (_roleId) {
                return service.getRoleById(roleId).done ((_role : IRole) => {
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

    //Role Set
    export interface IRoleSet {
        roleSetCd: string;
        roleSetName: string;
        approvalAuthority: boolean;
        officeHelperRoleId: IRole;
        myNumberRoleId: IRole;
        hRRoleId: IRole;
        personInfRoleId: IRole;
        employmentRoleId: IRole;
        salaryRoleId: IRole;
        webMenuCds: Array<string>;
    }

    export class RoleSet {
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');
        approvalAuthority: KnockoutObservable<boolean> = ko.observable(false);
        officeHelperRoleId : KnockoutObservable<IRole> = ko.observable(null);
        myNumberRoleId:KnockoutObservable<IRole> = ko.observable(null);
        hRRoleId: KnockoutObservable<IRole> = ko.observable(null);
        personInfRoleId:KnockoutObservable<IRole> = ko.observable(null);
        employmentRoleId: KnockoutObservable<IRole> = ko.observable(null);
        salaryRoleId: KnockoutObservable<IRole> = ko.observable(null);
    
        webMenuCds: KnockoutObservableArray<string> = ko.observable(null);

        constructor(param: IRoleSet) {
            let self = this;
            self.roleSetCd(param.roleSetCd || '');
            self.roleSetName(param.roleSetName || '');
            self.approvalAuthority(param.approvalAuthority || false);
            self.officeHelperRoleId(param.officeHelperRoleId || null);
            self.myNumberRoleId(param.myNumberRoleId || null);
            self.hRRoleId(param.hRRoleId || null);
            self.personInfRoleId(param.personInfRoleId || null);
            self.employmentRoleId(param.employmentRoleId || null);
            self.salaryRoleId(param.salaryRoleId || null);
            self.webMenuCds(param.webMenuCds || null);
        }
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

}

