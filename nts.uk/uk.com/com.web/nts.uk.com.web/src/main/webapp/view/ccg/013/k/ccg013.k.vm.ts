module nts.uk.com.view.ccg013.k.viewmodel {

    export class ScreenModel {
        //combobox
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        //list
        listStandardMenu: KnockoutObservableArray<StandardMenu>;
        list: KnockoutObservableArray<StandardMenu>;
        columns: Array<any>;
        features: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //combobox
            self.itemList = ko.observableArray([
                // 共通 :COMMON(0) 
                new ItemModel('0', '共通'),
                // 勤次郎  :TIME_SHEET(1) 
                new ItemModel('1', '勤次郎'),
                // オフィスヘルパー :OFFICE_HELPER(2) 
                new ItemModel('2', 'オフィスヘルパー'),
                // Ｑ太郎 :KYUYOU(3) 
                new ItemModel('3', 'Ｑ太郎'),
                //  人事郎  :JINJIROU (4) 
                new ItemModel('4', '人事郎')
            ]);
            self.selectedCode = ko.observable('0');
            self.isEnable = ko.observable(true);

            // list
            self.listStandardMenu = ko.observableArray([]);
            self.list = ko.observableArray([]);
            self.columns = [
                { headerText: nts.uk.resource.getText("CCG013_51"), key: 'code', width: 80 },
                { headerText: nts.uk.resource.getText("CCG013_52"), key: 'targetItems', width: 150 },
                {
                    headerText: nts.uk.resource.getText("CCG013_83"), key: 'displayName', width: 150,
                    template: "<input class=\"displayName-input\" type=\"text\" value=\"${displayName}\" onchange='update(this, \"displayName\")' />"
                }
            ];
            self.features = ko.observableArray([
                {
                    name: 'Updating',
                    enableAddRow: true,
                    editMode: 'row',
                    columnSettings: [
                        {
                            columnKey: "displayName",
                            editorType: "text",
                            editorOptions: {
                                buttonType: "dropdown",
                                listItems: names,
                                readOnly: false
                            }
                        }
                    ]
                }
            ]);
            self.currentCode = ko.observable();
            self.selectedCode.subscribe((value) => {
                self.getListStandardMenu(value);
            });
        }

        /** get data number "value" in list **/
        getListStandardMenu(value) {
            let self = this;
            self.list([]);
            for (let i = 0; i < self.listStandardMenu().length; i++) {
                if (self.listStandardMenu()[i].system == value)
                    self.list.push(new StandardMenu(self.listStandardMenu()[i].code, self.listStandardMenu()[i].targetItems, self.listStandardMenu()[i].displayName, self.listStandardMenu()[i].system, self.listStandardMenu()[i].classification));
            }
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            // Get List StandrdMenu
            service.getAllStandardMenu().done(function(listStandardMenu: Array<viewmodel.StandardMenu>) {
                listStandardMenu = _.orderBy(listStandardMenu, ["code"], ["asc"]);
                _.each(listStandardMenu, function(obj: viewmodel.StandardMenu) {
                    self.listStandardMenu.push(new StandardMenu(obj.code, obj.targetItems, obj.displayName, obj.system, obj.classification));
                });
                self.getListStandardMenu("0");
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
        }  
        
        /** update data when click button register **/
        register() {
            let self = this;  
            let a: Array<any> = $("#multi-list").igGrid("option", "dataSource");
            $('.displayName-input').trigger("validate");
            _.defer(() => {
                if (nts.uk.ui.errors.hasError() === false) {
                    service.updateStandardMenu(a).done(function() {                          
                        _.remove(self.listStandardMenu(), function(item){                              
                            return item.system == parseInt(self.selectedCode());                         
                        });                         
                     for(let i = 0; i < a.length; i++)  {   
                        self.listStandardMenu().push(new StandardMenu(a[i].code, a[i].targetItems, a[i].displayName, a[i].system, a[i].classification));  
                     }
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });     
                        self.getListStandardMenu(self.selectedCode());
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error.message);
                        self.getListStandardMenu(self.selectedCode());
                    });
                }
            });

        }    

        /** close Dialog **/
        closeDialog() {   
            nts.uk.ui.windows.close(); 
        }

    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class StandardMenu {
        code: string;
        targetItems: string;
        displayName: string;
        system: number;
        classification: number;
        constructor(code: string, targetItems: string, displayName: string, system: number, classification: number) {
            this.code = code;
            this.targetItems = targetItems;
            this.displayName = displayName;
            this.system = system;
            this.classification = classification;
        }
    }
}

function update(v, c){ 
     if(v.value.length > __viewContext.primitiveValueConstraints.MenuDisplayName.maxLength){
        $(v).ntsError('set', "max value");
        return;
    } else {
        $(v).ntsError('clear');  
    } 
    let data: Array<any> = $("#multi-list").igGrid("option", "dataSource"),
        row = data[$(v).parents('tr').data('row-idx')];
    if (row) {
        row[c] = v.value;
    }  
    $("#multi-list").igGrid("option", "dataSource", data);  
}