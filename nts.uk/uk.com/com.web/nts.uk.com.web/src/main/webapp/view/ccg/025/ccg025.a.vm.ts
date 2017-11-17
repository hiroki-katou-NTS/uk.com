//module nts.uk.com.view.ccg025.a {
//    import getText = nts.uk.resource.getText;
//    export module viewmodel {
//        export class ScreenModel {
//            listRole : KnockoutObservableArray<model.Role>;
//            columns: KnockoutObservableArray<any>;
//            currentCodeList: KnockoutObservableArray<any>;
//            constructor() {
//                let self = this;
//                self.listRole = ko.observableArray([]);
//                this.currentCodeList = ko.observableArray([]);
//                this.columns = ko.observableArray([
//                    { headerText: 'コード', prop: 'roleCode', width: 100 },
//                    { headerText: '名称', prop: 'name', width: 230 }
//                ]);
//                
//                  
//            }
//
//            /**
//             * functiton start page
//             */
//            startPage(): JQueryPromise<any> {
//                let self = this;
//                let dfd = $.Deferred();
//                let dfdGetListRoleByRoleType = self.getListRoleByRoleType(1);
//                $.when(dfdGetListRoleByRoleType).done((dfdGetListRoleByRoleTypeData) => {
//
//                        dfd.resolve();
//                    });
//                return dfd.promise();
//            }//end start page
//            /**
//             * function getListRoleByRoleType
//             */
//            getListRoleByRoleType(roleType :number){
//                let self = this;
//                let dfd = $.Deferred();
//                service.getListRoleByRoleType(roleType).done(function(data){
//                    _.orderBy(data, ['assignAtr', 'roleCode'], ['asc', 'asc']);
//                    self.listRole(data);
//                    dfd.resolve();
//                }).fail(function(res: any) {
//                    dfd.reject();
//                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
//                });
//            }
//
//            
//            
//        }//end screenModel
//    }//end viewmodel
//
//    //module model
//    export module model {
//        
//        /**
//         * class Role
//         */
//        export class Role {
//            roleId : string;
//            roleCode : string;
//            roleType : number;
//            employeeReferenceRange: number;
//            name: string;
//            contractCode : string;
//            assignAtr :number;
//            companyId : string;
//            constructor(roleId : string,roleCode : string,
//            roleType : number,employeeReferenceRange: number,name: string,
//            contractCode : string,assignAtr :number,companyId : string) {
//                this.roleId = roleId;
//                this.roleCode = roleCode;
//                this.roleType = roleType;
//                this.employeeReferenceRange = employeeReferenceRange;
//                this.name = name;
//                this.contractCode = contractCode;
//                this.assignAtr = assignAtr;
//                this.companyId = companyId;
//                
//                       
//            }
//        }//end class Role
//        
//
//    }//end module model
//
//}//end module