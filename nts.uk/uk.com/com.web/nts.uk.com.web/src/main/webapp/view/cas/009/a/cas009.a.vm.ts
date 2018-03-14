module cas009.a.viewmodel {
    import EnumConstantDto = cas009.a.service.model.EnumConstantDto;
    import service = cas009.a.service;
    import ccg = nts.uk.com.view.ccg025.a;
    import windows = nts.uk.ui.windows;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;    
    export class ScreenModel {
        listRole : KnockoutObservableArray<viewmodel.model.Role>; 
        roleCode: KnockoutObservable<string>;
        roleId: KnockoutObservable<string>;
        name : KnockoutObservable<string>;
        assignAtr : KnockoutObservable<number>;
        employeeReferenceRange: KnockoutObservable<number>;
        referFutureDate : KnockoutObservable<boolean>;
        createMode : KnockoutObservable<boolean>;
        enumAuthen: KnockoutObservableArray<any>; 
        enumAllow: KnockoutObservableArray<any>;
        enumRange:  KnockoutObservableArray<EnumConstantDto>;
        enumRangeChange:  KnockoutObservableArray<EnumConstantDto>;
        component: ccg.component.viewmodel.ComponentModel;
        enableDetail: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
                self.listRole = ko.observableArray([]);
                self.roleCode = ko.observable(null);
                self.roleId = ko.observable(null);
                self.employeeReferenceRange = ko.observable(0);
                self.name = ko.observable("");
                self.assignAtr = ko.observable(1);
                self.referFutureDate = ko.observable(true);
                self.createMode = ko.observable(true);
                self.enableDetail =  ko.observable(true);
                self.enumAuthen = ko.observableArray([
                         { code: '0', name: nts.uk.resource.getText("CAS009_14") }, 
                         { code: '1', name: nts.uk.resource.getText("CAS009_15") },
                    ]);
                self.enumAllow = ko.observableArray([
                         { code: true, name: nts.uk.resource.getText("CAS009_18") }, 
                         { code: false, name: nts.uk.resource.getText("CAS009_19") },
                    ]);
                self.enumRange = ko.observableArray([]);
                self.enumRangeChange = ko.observableArray([]);
                self.assignAtr.subscribe(function(newValue){
                   if(self.enumRange().length>0){
                        if(newValue ==0)     self.enumRangeChange(self.enumRange().slice(0,1));
                        else  self.enumRangeChange(self.enumRange().slice(1,4));
                   } 
                });        
                self.component = new ccg.component.viewmodel.ComponentModel({ 
                    roleType: 8,
                    multiple: false  
                });                     
        }
        
        public initSubscribe(): void{
                 let self = this;
                 self.component.currentCode.subscribe((newValue) => {                 
                        if(newValue !=null){                            
                            let current = _.find(self.listRole(), function(o){return o.roleId == newValue});
                            if(current != undefined){
                                self.roleCode(current.roleCode)
                                self.roleId(newValue);
                                self.createMode(false);
                                self.name(current.name);
                                self.assignAtr(current.assignAtr);
                                self.employeeReferenceRange(current.employeeReferenceRange);
                                self.referFutureDate(current.referFutureDate );                                                                                                                                     
                            }
                 
                        }else{
                            self.roleCode(null);
                            self.roleId(null);
                            self.employeeReferenceRange(0);
                            self.name("");
                            self.assignAtr(0);
                            self.referFutureDate(false);
                            self.createMode(true);
                             
                        }
                     self.setFocus();                     
   
                });                
        }
       

        /** Start Page */
       public  startPage(): any {           
            let self = this;
            block.invisible();       
            $.when(self.getOpItemEnum(), self.userHasRole()).done(()=>{
                self.getListRole().done(()=>{
                    self.initSubscribe(); 
                    if(self.listRole().length==0) 
                    {
                        self.createNew();
                    }else{
                        self.component.currentCode(self.component.listRole()[0].roleId)   
                    }
                    
                }).always(()=>{
                          block.clear();
                          nts.uk.ui.errors.clearAll();  
                });                
            });


        }
        
        public getOpItemEnum(): JQueryPromise<any>{
            let self = this;    
            let dfd = $.Deferred();
            
            service.getOptItemEnum().done(function(res){
                
                self.enumRange(res);
                
                if (self.assignAtr() == 0) 
                    self.enumRangeChange(self.enumRange().slice(0, 1));
                else
                    self.enumRangeChange(self.enumRange().slice(1, 4));
                
                dfd.resolve();
            }).fail((error) => {
                alertError(error);
            });
            return dfd.promise();            
        }
        
        
        public getListRole():JQueryPromise<any>{
            let  self = this;
            let dfd = $.Deferred();
            self.component.startPage().done(function(){
                let roleIds : Array<string> = _.map(self.component.listRole(), function(x){
                    return x.roleId;
                });
                if(roleIds.length>0){
                    service.getPersonInfoRole(roleIds).done((res) => {
                        
                        self.listRole(_.map(self.component.listRole(), function(x){
                            let personInfo : any = _.find(res, function(o){ return o.roleId == x.roleId  });
                            
                            return new model.Role(true, x.roleId, x.roleCode, x.employeeReferenceRange, x.name, x.assignAtr, personInfo.referFutureDate );                            
                        }));                                                                                                                              
                        dfd.resolve();                                                                                                
                    });
                    self.component.currentCode(null);
                }else{
                    self.listRole([]);
                    self.createNew();
                    dfd.resolve();                        
                }
            });
            return dfd.promise();
                
        } 
        public userHasRole():JQueryPromise<any>{
            let  self = this;
            let dfd = $.Deferred();
            service.userHasRole().done(function(res){
                   self.enableDetail(res);
                   dfd.resolve();
            }).fail((error) => {
                alertError(error);
            });
            return dfd.promise();
                
        }        
        public setFocus(): void{
            let self = this;
            
            if(self.component.currentCode()==null) 
                $('#roleCode').focus();
            else
                $('#roleName').focus();  
            _.defer(()=>{   nts.uk.ui.errors.clearAll();  });
        }  
        
        

        public createNew(): any{
            let self = this;
                self.component.currentCode(null);
                self.setFocus();                               
        }
        
        public save(): any{
            let self = this;
            $(".nts-input").trigger("validate");
            if($(".nts-input").ntsError("hasError")) return ;            
            block.invisible();
            let role = new model.Role(self.createMode(), self.roleId(), self.roleCode(), self.employeeReferenceRange(), self.name(), self.assignAtr(), self.referFutureDate());
            service.saveRole(role).done(function(){
                 nts.uk.ui.dialog.info({ messageId: "Msg_15" });                  
                 
                    self.getListRole().done(()=>{
                           self.component.currentCode(_.find(self.listRole(), function(o){ return o.roleCode == role.roleCode  }).roleId);
                 }).always(()=>{
                           block.clear();
                           nts.uk.ui.errors.clearAll();    
                 });
          
            }).fail((error) => {
                 alertError(error);
                 block.clear();
                 nts.uk.ui.errors.clearAll();
            });    
        }
        public remove(): any{
            let self = this;

               if(self.component.currentCode() !=null){
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                        block.invisible();                        
                        service.deleteRole({roleId: self.roleId(), assignAtr: self.assignAtr()}).done(function(){
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                           
                            let index = _.findIndex(self.listRole(), ['roleId', self.roleId()]);
                            index = _.min([self.listRole().length - 2, index]);
                            self.getListRole().done(function(){
                                 self.selectRoleByIndex(index);                               
                            }).always(() =>{
                                 block.clear();
                                 nts.uk.ui.errors.clearAll();    
                            });
                            
                        }).fail((error) => {
                                alertError(error);
                                block.clear();
                                nts.uk.ui.errors.clearAll();
                        });   
                   }); 
               }   
        }
        private selectRoleByIndex(index: number) {
            var self = this;
            var selectRoleByIndex = _.nth(self.listRole(), index);
            if (selectRoleByIndex !== undefined)
                self.component.currentCode(selectRoleByIndex.roleId);
            else
                self.createNew();
        }
        public open_Dialog(): any {
            windows.sub.modal("/view/cas/009/b/index.xhtml").onClosed(() => {
                
            });
        }
        
    }

    export module model {
        export class Role {
            createMode: boolean;
            roleId: string;
            roleCode: string;
            employeeReferenceRange: number;
            name : string;
            assignAtr : number;
            referFutureDate : boolean;
            
            constructor(createMode: boolean, roleId: string, roleCode: string, employeeReferenceRange: number, 
                            name: string, assignAtr: number, referFutureDate: boolean) {
                this.createMode = createMode;
                this.roleId = roleId;
                this.roleCode = roleCode;
                this.employeeReferenceRange = employeeReferenceRange;
                this.name = name;
                this.assignAtr = assignAtr;
                this.referFutureDate = referFutureDate;
            }
        }
    }
}