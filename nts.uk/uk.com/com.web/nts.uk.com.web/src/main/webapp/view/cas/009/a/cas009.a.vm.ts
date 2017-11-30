module cas009.a.viewmodel {
    import EnumConstantDto = cas009.a.service.model.EnumConstantDto;
    import service = cas009.a.service;
    import ccg = nts.uk.com.view.ccg025.a;
    export class ScreenModel {
        listRole : KnockoutObservableArray<viewmodel.model.Role>; 
        roleCode: KnockoutObservable<string>;
        roleId: KnockoutObservable<string>;
        name : KnockoutObservable<string>;
        assignAtr : KnockoutObservable<number>;
        employeeReferenceRange: KnockoutObservable<number>;
        referFutureDate : KnockoutObservable<boolean>;
        enumAuthen: KnockoutObservableArray<any>; 
        enumAllow: KnockoutObservableArray<any>;
        enumRange:  KnockoutObservableArray<EnumConstantDto>;
        component: ccg.component.viewmodel.ComponentModel;
        constructor() {
            var self = this;
                self.listRole = ko.observableArray([]);
                self.roleCode = ko.observable("");
                self.roleId = ko.observable("");
                self.employeeReferenceRange = ko.observable(0);
                self.name = ko.observable("");
                self.assignAtr = ko.observable(0);
                self.referFutureDate = ko.observable(false);
                self.enumAuthen = ko.observableArray([
                         { code: '0', name: nts.uk.resource.getText("CAS009_14") }, 
                         { code: '1', name: nts.uk.resource.getText("CAS009_15") },
                    ]);
                self.enumAllow = ko.observableArray([
                         { code: '0', name: nts.uk.resource.getText("CAS009_18") }, 
                         { code: '1', name: nts.uk.resource.getText("CAS009_19") },
                    ]);
                self.enumRange = ko.observableArray([]);
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: 8,
                    multiple: false
                });
        }

        /** Start Page */
       public  startPage(): JQueryPromise<any> {           
            var self = this;
            service.getOptItemEnum().done(function(res){
                self.enumRange(res);    
            });

            let dfd = $.Deferred();
            self.component.startPage().done(function(){
                let roleIds : Array<string> = _.map(self.component.listRole(), function(x){
                    return x.roleId;
                });
                service.getPersonInfoRole(roleIds).done(function(res){
                    
                });

                dfd.resolve();    
            });
            return dfd.promise();
        }

        public remove(): any{
            let self = this;
               nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                service.deleteRole({roleId: self.roleId(), assignAtr: self.assignAtr()});   

               });    
        }
    }

    export module model {
        export class Role {
            createMode : KnockoutObservable<boolean>;
            roleId: KnockoutObservable<string>;
            roleCode: KnockoutObservable<string>;
            employeeReferenceRange: KnockoutObservable<number>;
            name : KnockoutObservable<string>;
            assignAtr : KnockoutObservable<number>;
            referFutureDate : KnockoutObservable<boolean>;
            
            constructor(createMode: boolean, roleId: string, roleCode: string, employeeReferenceRange: number, 
                            name: string, assignAtr: number, referFutureDate: boolean) {
                this.createMode = ko.observable(createMode);
                this.roleId = ko.observable(roleId);
                this.roleCode = ko.observable(roleCode);
                this.employeeReferenceRange = ko.observable(employeeReferenceRange);
                this.name = ko.observable(name);
                this.assignAtr = ko.observable(assignAtr);
                this.referFutureDate = ko.observable(referFutureDate);
            }
        }
    }
}