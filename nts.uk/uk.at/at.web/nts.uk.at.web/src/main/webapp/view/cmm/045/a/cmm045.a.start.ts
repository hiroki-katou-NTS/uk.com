//module cmm045.a {
//    let __viewContext: any = window["__viewContext"] || {};
//    __viewContext.ready(function() {
//        var screenModel = new cmm045.a.viewmodel.ScreenModel();
//           // Grid cell errors
//        let dialogOptions: any = {
//            forGrid: true,
//            headers: [
//                    new nts.uk.ui.errors.ErrorHeader("rowId", "Row ID", "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("columnKey", "Column Key", "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("message", "Message", "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("ruleCode", "Rule code", "auto", true) 
//                ]
//        };
//            __viewContext.bind(screenModel, dialogOptions);
//        });
//}
module cmm045.a  {
    
    __viewContext.ready(function () {
        
        class GridItem {
            id: number;
            flag: boolean;
            ruleCode: string;
            combo: string;
            text1: string;
            constructor(index: number) {
                this.id = index;
                this.flag = index % 2 == 0;
                this.ruleCode = String(index % 3 + 1);
                this.combo = String(index % 3 + 1);
                this.text1 = "TEXT";
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
        
        class ScreenModel {
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;
            
            items0 = (function() {
                let list = [];
                for (let i = 501; i < 1000; i++) {
                    list.push(new GridItem(i));
                }
                return list;
            })();
            
            items = (function () {
                var list = [];
                for (var i = 0; i < 500; i++) {
                    list.push(new GridItem(i));
                }
                return list;
            })();
            
            constructor() {
                
                $("#grid2").ntsGrid({ 
                            width: '970px',
                            height: '400px',
                            dataSource: this.items,
                            primaryKey: 'id',
                            virtualization: true,
                            virtualizationMode: 'continuous',
                            columns: [
                                { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label'},
//                                { headerText: nts.uk.resource.getText('CMM045_49'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                                { headerText: nts.uk.resource.getText('CMM045_50'), key: 'open', dataType: 'string', width: '40px', unbound: true, ntsControl: 'Button' },
                                { headerText: nts.uk.resource.getText('CMM045_51'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_52'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_53'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_54'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_55'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_56'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_57'), key: 'text1', dataType: 'string', width: '120px' },
                                { headerText: nts.uk.resource.getText('CMM045_58'), key: 'text1', dataType: 'string', width: '120px' },
                                
                            ], 
                            features: [{ name: 'Resizing' },
                                        { 
                                            name: 'Selection',
                                            mode: 'row',
                                            multipleSelection: true
                                        }
                            ],
                            ntsControls: [{ name: 'Checkbox', options: { value: 1, text: 'Custom Check' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                                        { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },                
                            ]});
                $("#grid2").setupSearchScroll("igGrid", true);
                $("#run").on("click", function() {
                    var source = $("#grid2").igGrid("option", "dataSource");
                    alert(source[1].flag);
                });
                $("#update-row").on("click", function() {
                    $("#grid2").ntsGrid("updateRow", 0, { flag: false, ruleCode: '2', combo: '3' });
                });
                $("#enable-ctrl").on("click", function() {
                    $("#grid2").ntsGrid("enableNtsControlAt", 1, "combo", "ComboBox");
                });
                $("#disable-ctrl").on("click", function() {
                    $("#grid2").ntsGrid("disableNtsControlAt", 1, "combo", "ComboBox");
                });
                $("#disable-all").on("click", function() {
                    $("#grid2").ntsGrid("disableNtsControls", "ruleCode", "SwitchButtons");
                });
                $("#enable-all").on("click", function() {
                    $("#grid2").ntsGrid("enableNtsControls", "ruleCode", "SwitchButtons");
                });
            }
        }
        
        var model = new ScreenModel();
        this.bind(model);
    });
}
