module nts.uk.com.view.cas005.a {
    import getText = nts.uk.resource.getText;
    import ccg = nts.uk.com.view.ccg025.a;
    export module viewmodel {
        export class ScreenModel {
            //text
            roleType: KnockoutObservable<number>;
            roleName: KnockoutObservable<string>;
            //switch
            roundingRules: KnockoutObservableArray<any>;
            roundingRules2: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            //combobox
            itemList1: KnockoutObservableArray<model.ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            //table
            currentCodeList: KnockoutObservableArray<any>;
            columns: KnockoutObservableArray<any>;
            items: KnockoutObservableArray<model.ItemModel2>;
            //table-right
            component: ccg.component.viewmodel.ComponentModel;
            //table-left
            columnRoleType : KnockoutObservableArray<any>;
            currentType : KnockoutObservable<any>;
            listEnumRoleType  :KnockoutObservableArray<any>;
            
            constructor() {
                let self = this;
                //table enum RoleType
                self.listEnumRoleType = ko.observableArray(__viewContext.enums.RoleType);
                self.columnRoleType = ko.observableArray([
                    { headerText: 'コード', key: 'value', width: 100 },
                    { headerText: '名称', key: 'name', width: 170 }
                ]);
                self.currentType = ko.observable(null);
                //text
                self.roleName = ko.observable('');
                self.roleType = ko.observable(123);
                self.roleType.subscribe((value) => {
                    let item = _.find(self.listEnumRoleType(), ['value', Number(value)]).name;
                    if(item !== undefined){
                        self.roleName(item);   
                    }else{
                        self.roleName('');
                    }
                });
                

                //switch
                self.roundingRules = ko.observableArray([
                    { code: '1', name: getText('CAS005_35') },
                    { code: '2', name: getText('CAS005_36') }
                ]);
                self.roundingRules2 = ko.observableArray([
                    { code: '1', name: getText('CAS005_41') },
                    { code: '2', name: getText('CAS005_42') }
                ]);
                self.selectedRuleCode = ko.observable(1);
                //combobox
                self.itemList1 = ko.observableArray([
                    new model.ItemModel('1', '基本給'),
                    new model.ItemModel('2', '役職手当'),
                    new model.ItemModel('3', '基本給')
                ]);
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                //table
                self.items = ko.observableArray([]);
                for (let i = 1; i < 100; i++) {
                    this.items.push(new model.ItemModel2('00' + i, '基本給 基本給', "description " + i, i % 3 === 0, "2010/1/1"));
                }
                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '名称', key: 'name', width: 150, columnCssClass: "test" },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150, isDateColumn: true, format: 'YYYY/MM/DD' }
                ]);
                self.currentCodeList = ko.observableArray([]);


            }
            /** Select TitleMenu by Index: Start & Delete case */
            private selectRoleTypeByIndex(index: number) {
                var self = this;
                var selectRoleTypeByIndex = _.nth(self.listEnumRoleType(), index);
                if (selectRoleTypeByIndex !== undefined)
                    self.roleType(selectRoleTypeByIndex.value);
                else
                    self.roleType(null);
            }
            

            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.selectRoleTypeByIndex(0);
                dfd.resolve();
                return dfd.promise();
            }//end start page

            openDialogB() {
                let self = this;
                let param = {

                };
                nts.uk.ui.windows.setShared("openB", param);
                nts.uk.ui.windows.sub.modal("/view/cas/005/b/index.xhtml");
            }



        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemModel2 {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
                this.deletable = deletable;
            }
        }


    }//end module model

}//end module