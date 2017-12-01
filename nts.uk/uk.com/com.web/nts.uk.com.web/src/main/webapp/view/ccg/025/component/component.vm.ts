module nts.uk.com.view.ccg025.a.component {
    import getText = nts.uk.resource.getText;
    
    export interface Option {
        roleType?: number;
        multiple?: boolean;
    }
    
    export module viewmodel {
        export class ComponentModel { 
            listRole : KnockoutObservableArray<model.Role>;
            currentCode: any;
            private columns: KnockoutObservableArray<any>;
            private defaultOption: Option = {
                multiple: true
            }
            private setting: Option;
            private searchMode: string;
            
            constructor(option: Option) {
                let self = this;
                self.setting = $.extend({}, self.defaultOption, option);
                self.searchMode = (self.setting.multiple) ? "highlight" : "filter";
                self.listRole = ko.observableArray([]);
                if (self.setting.multiple)
                    self.currentCode = ko.observableArray([]);
                else
                    self.currentCode = ko.observable("");
                if(self.setting.multiple){
                    self.columns = ko.observableArray([
                        { headerText: getText("CCG025_3"), prop: 'roleId', width: 100,hidden : true },
                        { headerText: getText("CCG025_3"), prop: 'roleCode', width: 100 },
                        { headerText: getText("CCG025_4"), prop: 'name',  width: 180 }
                    ]);
                }else{
                    self.columns = ko.observableArray([
                        { headerText: getText("CCG025_3"), prop: 'roleId', width: 100, hidden : true },
                        { headerText: getText("CCG025_3"), prop: 'roleCode', width: 100 },
                        { headerText: getText("CCG025_4"), prop: 'name',  width: 200 }
                    ]);    
                }
                
            }

            /** functiton start page */
            startPage(): JQueryPromise<any> {
                let self = this;
                return self.getListRoleByRoleType(self.setting.roleType);
            }//end start page
            
            /** Get list Role by Type */
            private getListRoleByRoleType(roleType :number): JQueryPromise<Array<model.Role>>{
                let self = this;
                let dfd = $.Deferred();
                service.getListRoleByRoleType(roleType).done((data: Array<model.Role>) => {
                    data = _.orderBy(data, ['assignAtr', 'roleCode'], ['asc', 'asc']);
                    self.listRole(data);
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }
            
        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        
        /**
         * class Role
         */
        export class Role {
            roleId : string;
            roleCode : string;
            roleType : number;
            employeeReferenceRange: number;
            name: string;
            contractCode : string;
            assignAtr :number;
            companyId : string;
            constructor(roleId : string,roleCode : string,
            roleType : number,employeeReferenceRange: number,name: string,
            contractCode : string,assignAtr :number,companyId : string) {
                this.roleId = roleId;
                this.roleCode = roleCode;
                this.roleType = roleType;
                this.employeeReferenceRange = employeeReferenceRange;
                this.name = name;
                this.contractCode = contractCode;
                this.assignAtr = assignAtr;
                this.companyId = companyId;
                
                       
            }
        }//end class Role
        

    }//end module model

}//end module