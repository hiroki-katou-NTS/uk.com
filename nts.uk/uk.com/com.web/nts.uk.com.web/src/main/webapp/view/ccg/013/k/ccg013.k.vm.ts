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
        currentCode: KnockoutObservable<any>;
        id: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.id = ko.observable(null);
            //combobox
            self.itemList = ko.observableArray([
                // 共通 :COMMON(0) 
                new ItemModel('0', nts.uk.resource.getText("Enum_System_COMMON")),
                // 勤次郎  :TIME_SHEET(1) 
                new ItemModel('1', nts.uk.resource.getText("Enum_System_TIME_SHEET"))
            ]);
            self.selectedCode = ko.observable('0');
            self.isEnable = ko.observable(true);

            // list
            self.listStandardMenu = ko.observableArray([]);
            self.list = ko.observableArray([]);
            self.columns = [
                {headerText:'id', key: 'id', width: 20, hidden: true},
                { headerText: nts.uk.resource.getText("CCG013_51"), key: 'code', width: 80 },
                { headerText: nts.uk.resource.getText("CCG013_52"), key: 'targetItems', width: 150 },
                {
                    headerText: nts.uk.resource.getText("CCG013_53"), key: 'displayName', formatter: _.escape, width: 150
                    //template: "<input class=\"displayName-input\" type=\"text\" value=\"${displayName}\" />"
                }
            ];
            self.currentCode = ko.observable();
            self.selectedCode.subscribe((value) => {
                self.getListStandardMenu(value);
                $("#grid").igGrid("option", "dataSource", self.list()); 
            });
        }

        /** get data number "value" in list **/
        getListStandardMenu(value) {
            let self = this;
            self.id(0);
            self.list([]);
            for (let i = 0; i < self.listStandardMenu().length; i++) {
                if (self.listStandardMenu()[i].system == value){
                    self.list.push(new StandardMenu(self.id(), self.listStandardMenu()[i].code, self.listStandardMenu()[i].targetItems, self.listStandardMenu()[i].displayName, self.listStandardMenu()[i].system, self.listStandardMenu()[i].classification));
                    self.id(self.id()+1);
                }
            }
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            self.id(0);
            let dfd = $.Deferred();
            // Get List StandrdMenu
            service.getAllStandardMenu().done(function(listStandardMenu: Array<viewmodel.StandardMenu>) {
                listStandardMenu = _.orderBy(listStandardMenu, ["code"], ["asc"]);
                _.each(listStandardMenu, function(obj: viewmodel.StandardMenu) {
                    self.listStandardMenu.push(new StandardMenu(self.id(), obj.code, obj.targetItems, obj.displayName, obj.system, obj.classification));
                    self.id(self.id()+1);
                });
                self.getListStandardMenu("0");
                
                self.initGrid();
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
            self.id(0);
            nts.uk.ui.errors.clearAll();
            
            if ($("#grid").igGridUpdating('isEditing')) {
                $("#grid").igGridUpdating('endEdit', true, true);    
            }
            
            let a: Array<any> = $("#grid").igGrid("option", "dataSource");
            _.forEach(a, function(item) {
                 var data = {
                    name: '#[CCG013_53]',
                    value: item.displayName,
                    required: true,  
                    constraint: 'MenuDisplayName'    
                };
                
                var cell = $("#grid").igGrid("cellById", item.id, "displayName");

                validateInput($(cell), data);
            }); 
                   
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            _.defer(() => {
                if (!nts.uk.ui.errors.hasError() ) {
                    nts.uk.ui.block.grayout();
                    service.updateStandardMenu(a).done(function() {
                        service.getAllStandardMenu().done(function(lst) {
                            self.listStandardMenu(lst);
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.getListStandardMenu(self.selectedCode());
                            $("#grid").igGrid("option", "dataSource", self.list());
                            if ($("#search").find('input.ntsSearchBox').val()) {
                                $("button.search-btn").trigger("click");
                            }
                        });                       
//                        _.remove(self.listStandardMenu(), function(item){                              
//                            return item.system == parseInt(self.selectedCode());                         
//                        });                         
//                         for(let i = 0; i < a.length; i++)  {   
//                            self.listStandardMenu().push(new StandardMenu(self.id(self.id()+1), a[i].code, a[i].targetItems, a[i].displayName, a[i].system, a[i].classification));  
//                         }

                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error.message);
//                        self.getListStandardMenu(self.selectedCode());
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            });

        }    

        /** close Dialog **/
        closeDialog() {   
            nts.uk.ui.windows.close(); 
        }
        
        /**
         * Init grid
         */
        initGrid(): void {
            var self = this;
            $("#grid").igGrid({
                primaryKey: "id",
                columns: self.columns,
                dataSource: self.list(),
                features: [
                {
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: true,
                    activation: false       
                },
                {
                    name: "Updating",
                    enableAddRow: false,
                    editMode: "cell",
                    autoCommit: true,
                    enableDeleteRow: false,
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columnSettings: [
                        { columnKey: "displayName", editorType: "text", editorOptions: { type: "text", disabled: false} },
                        { columnKey: "code", editorOptions: { disabled: true} },
                        { columnKey: "targetItems", editorOptions: { disabled: true} },
                    ],
                    editCellEnded: function(evt, ui){ 
                        let dataSource: Array<any> = $("#grid").igGrid("option", "dataSource");
                        if (dataSource && dataSource.length > 0) {
                            let row = _.find(dataSource, function(item:StandardMenu) {
                               return ui.rowID == item.id;     
                            });
                            if (row) {
                                row.displayName = ui.value;
                            }  
                            $("#grid").igGrid("option", "dataSource", dataSource);
                        } 
                    }
                }]
            });   
            
            $("#grid").closest('.ui-iggrid').addClass('nts-gridlist');
            $("#grid").setupSearchScroll("igGrid", true);
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
        id: number;
        code: string;
        targetItems: string;
        displayName: string;
        system: number;
        classification: number;
        constructor(id: number, code: string, targetItems: string, displayName: string, system: number, classification: number) {
            this.id = id;
            this.code = code;
            this.targetItems = targetItems;
            this.displayName = displayName;
            this.system = system;
            this.classification = classification;
        }
    }
}


/**
 * Validate input display name
 */
function validateInput($input: JQuery, data: any) {
    var value: (newText: string) => {} = data.value;
    var immediate: boolean = ko.unwrap(data.immediate !== undefined ? data.immediate : 'false');
    var valueUpdate: string = (immediate === true) ? 'input' : 'change';
    var valueUpdate: string = (immediate === true) ? 'input' : 'change';
    var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
    var constraint = nts.uk.ui.validation.getConstraint(constraintName);   
 
    var newText = data.value;
    let validator = getValidator(data);
    var result = validator.validate(newText);
    if (result.isValid) {
        $input.ntsError('clear');
        $input.removeAttr("style");
        //value(result.parsedValue);
        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
        return true;
    } else {
        let error = $input.ntsError('getError');
        if (nts.uk.util.isNullOrEmpty(error) || error.messageText !== result.errorMessage) {
            $input.ntsError('clear');
            $input.ntsError('set', result.errorMessage, result.errorCode);
            $input.attr("style", "border-color: red !important;");
        }
        
        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
        //value(newText);
        return false;
    }
}

function getValidator(data: any): nts.uk.ui.validation.IValidator {
    var name: string = data.name !== undefined ? ko.unwrap(data.name) : "";
    name = nts.uk.resource.getControlName(name);
    var required: boolean = (data.required !== undefined) ? ko.unwrap(data.required) : false;
    var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
    return new nts.uk.ui.validation.StringValidator(name, constraintName, { required: required });
}
