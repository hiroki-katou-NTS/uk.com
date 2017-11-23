module nts.uk.com.view.cas011.c.viewmodel {
/*    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
*/
    import resource = nts.uk.resource;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {

        listDefaultRoleSets: KnockoutObservableArray<IDefaultRoleSet> = ko.observableArray([]);
        currentDefaultRoleSet: KnockoutObservable<IDefaultRoleSet> = ko.observable(new DefaultRoleSet({
                roleSetCd: ''
                , roleSetName: ''
                }));

        constructor() {
            //let self = this;
        }

        //initial screen
        start(): JQueryPromise<any> {
            
            let self = this,
            currentDefaultRoleSet: IDefaultRoleSet = self.currentDefaultRoleSet(),
            listDefaultRoleSets = self.listDefaultRoleSets,
            dfd = $.Deferred();

            self.listDefaultRoleSets.removeAll();
            errors.clearAll();
/*        
            service.getAllRoleSet().done((itemList: Array<IDefaultRoleSet>) => {
               
                // in case number of RoleSet is greater then 0
                if (itemList && itemList.length > 0) {
    
                    self.listDefaultRoleSets(itemList);
                    self.settingSelectedDefaultRoleSet();
    
                } else { //in case number of RoleSet is zero
                    self.closeDialog();
                }
                
                dfd.resolve();
    
            }).fail(error => {
                dfd.reject();
                self.closeDialog();
            });
*/
            dfd.resolve();
            return dfd.promise();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        settingSelectedDefaultRoleSet() {
            let self = this,
            currentDefaultRoleSet: IDefaultRoleSet = self.currentDefaultRoleSet(),
            listDefaultRoleSets = self.listDefaultRoleSets();

            service.getCurrentDefaultRoleSet().done((item: IDefaultRoleSet) => {
                
                // in case exist default role set
                if (item) {
                    self.currentDefaultRoleSet(item);
                } else {
                    if (self.listDefaultRoleSets && self.listDefaultRoleSets.length > 0) {
                        self.currentDefaultRoleSet(self.listDefaultRoleSets()[0]);
                    }
                }

            }).fail(error => {
            });
            
        }
        
        /**
         * Request to register Default Role Set
         */
        addDefaultRoleSet() {
            let self = this,
                currentDefaultRoleSet : DefaultRoleSet = self.historySelection();

            service.addDefaultRoleSet(ko.toJS(currentDefaultRoleSet)).done(function() {                
                  dialog.info({ messageId: "Msg_15" }).then(function() {
                        nts.uk.ui.windows.close();
                    });
                
            });

        }
    }

    // Default Role Set:
    interface IDefaultRoleSet {
        roleSetCd: string;
        roleSetName: string;
    }

    class DefaultRoleSet {
        roleSetCd: KnockoutObservable<string> = ko.observable('');
        roleSetName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IDefaultRoleSet) {
            let self = this;
            self.roleSetCd(param.roleSetCd || '');
            self.roleSetName(param.roleSetName || '');

        }
    }
}
