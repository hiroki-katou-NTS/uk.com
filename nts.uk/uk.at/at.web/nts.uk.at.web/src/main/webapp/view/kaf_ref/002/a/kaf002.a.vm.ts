module nts.uk.at.view.kaf002_ref.a.viewmodel {
    
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002AViewModel extends Kaf000AViewModel {
        
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        texteditor: any;
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        time: KnockoutObservable<number>;
        items0 = (function() {
            let list = [];
            for (let i = 1; i < 50; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.ATTENDENCE));
            }
            return list;
        })();
        
        items1 = (function() {
            let list = [];
            let indexId = 1;
            for (let i = 1; i < 3; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.ATTENDENCE));
                list[(indexId -1)].id = indexId;
                indexId++;
            }
            for (let i = 1; i < 4; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.EXTRAORDINARY));
                list[(indexId -1)].id = indexId;
                indexId++;
            }
            return list;
        })();
        
        items2 = (function() {
            let list = [];
            for (let i = 1; i < 11; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.GOOUT_RETURNING));
            }
            
            return list;
        })();
        
        items3 = (function() {
            let list = [];
            for (let i = 1; i < 11; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.BREAK));
            }
            
            return list;
        })();
        items4 = (function() {
            let list = [];
            for (let i = 1; i < 3; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.PARENT));
            }
            
            return list;
        })();
        
        items5 = (function() {
            let list = [];
            for (let i = 1; i < 3; i++) {
                let dataObject = new TimePlaceOutput(i);
                list.push(new GridItem(dataObject, STAMPTYPE.NURSE));
            }
            
            return list;
        })();
        
       
        
        created() {
            const self = this;
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            self.time = ko.observable(1200);
            
//            self.$blockui("show");
//            self.loadData([], [], AppType.STAMP_APPLICATION)
//            .then((loadDataFlag: any) => {
//                if(loadDataFlag) {
//                    let ApplicantEmployeeID: null,
//                        ApplicantList: null,
//                        appDispInfoStartupOutput = ko.toJS(self.appDispInfoStartupOutput),
//                        command = { ApplicantEmployeeID, ApplicantList, appDispInfoStartupOutput };
//                }
//            })
            self.texteditor = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "Placeholder for text editor",
                        width: "100px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: self.$i18n('KAF002_29'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: self.$i18n('KAF002_31'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: self.$i18n('KAF002_76'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-4', title: self.$i18n('KAF002_32'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-5', title: self.$i18n('KAF002_33'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-6', title: self.$i18n('KAF002_34'), content: '.tab-content-6', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            
            let comboColumns = [{ prop: 'code', length: 4 },
                                { prop: 'name', length: 8 }];
            let comboItems = [ new ItemModel('1', '基本給'),
                               new ItemModel('2', '役職手当'),
                               new ItemModel('3', '基本給2') ];
            
            let statesTable = [];
            for (let i = 1; i < 21; i++) {
                statesTable.push(new CellState(String(i), "text1", ['titleColor']));
            }
                
//            statesTable.push(new CellState(503, "flag", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
//            statesTable.push(new CellState(509, "flag", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
//            statesTable.push(new CellState(511, "flag", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
            
            
            
//            出勤／退勤
            $("#grid1").ntsGrid({ 
                width: '970px',
                height: '400px',
                dataSource: this.items1,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
//                enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                    { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '290px' },
                    { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '230px', 
//                        ntsControl: 'Combobox', tabIndex: 0 
                        },
                    { headerText: self.$i18n('KAF002_72'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' }
                    
                ], 
                features: [{ name: 'Resizing',
                                columnSettings: [{
                                    columnKey: 'id', allowResizing: true, minimumWidth: 30
                                }, {
                                    columnKey: 'flag', allowResizing: false 
                                }] 
                            },
                            { 
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true
                            }
                ],
                ntsFeatures: [
//                    { name: 'CopyPaste' },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    }
                    ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
//                                { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }], 
//                                    optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true,
//                                    distinction: { "503": ['1', '2'], "506": ["2", "3"], "600": ["1", "2"] }},
//                                { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                                { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                                { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            
            
            
//            外出
            
            
            $("#grid2").ntsGrid({ 
                width: '970px',
                height: '400px',
                dataSource: this.items2,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
//                enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                    { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'combobox', dataType: 'string', width: '120px', ntsControl: 'Combobox' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '290px' },
                    { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '230px', 
//                        ntsControl: 'Combobox', tabIndex: 0 
                        },
                    { headerText: self.$i18n('KAF002_72'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' }
                    
                ], 
                features: [{ name: 'Resizing',
                                columnSettings: [{
                                    columnKey: 'id', allowResizing: true, minimumWidth: 30
                                }, {
                                    columnKey: 'flag', allowResizing: false 
                                }] 
                            },
                            { 
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true
                            }
                ],
                ntsFeatures: [
//                    { name: 'CopyPaste' },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    }
                    ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
//                                { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }], 
//                                    optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true,
//                                    distinction: { "503": ['1', '2'], "506": ["2", "3"], "600": ["1", "2"] }},
                                { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                                { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                                { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            
            
//            休憩
            $("#grid3").ntsGrid({ 
                width: '970px',
                height: '400px',
                dataSource: this.items3,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
//                enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                    { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '290px' },
                    { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '230px', 
//                        ntsControl: 'Combobox', tabIndex: 0 
                        },
                    { headerText: self.$i18n('KAF002_72'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' }
                    
                ], 
                features: [{ name: 'Resizing',
                                columnSettings: [{
                                    columnKey: 'id', allowResizing: true, minimumWidth: 30
                                }, {
                                    columnKey: 'flag', allowResizing: false 
                                }] 
                            },
                            { 
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true
                            }
                ],
                ntsFeatures: [
//                    { name: 'CopyPaste' },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    }
                    ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
//                                { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }], 
//                                    optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true,
//                                    distinction: { "503": ['1', '2'], "506": ["2", "3"], "600": ["1", "2"] }},
//                                { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                                { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                                { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            
            
            
            
            
//            育児
            $("#grid4").ntsGrid({ 
                width: '970px',
                height: '400px',
                dataSource: this.items4,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
//                enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                    { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '290px' },
                    { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '230px', 
//                        ntsControl: 'Combobox', tabIndex: 0 
                        },
                    { headerText: self.$i18n('KAF002_72'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' }
                    
                ], 
                features: [{ name: 'Resizing',
                                columnSettings: [{
                                    columnKey: 'id', allowResizing: true, minimumWidth: 30
                                }, {
                                    columnKey: 'flag', allowResizing: false 
                                }] 
                            },
                            { 
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true
                            }
                ],
                ntsFeatures: [
//                    { name: 'CopyPaste' },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    }
                    ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
//                                { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }], 
//                                    optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true,
//                                    distinction: { "503": ['1', '2'], "506": ["2", "3"], "600": ["1", "2"] }},
//                                { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                                { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                                { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            
//            介護
            $("#grid5").ntsGrid({ 
                width: '970px',
                height: '400px',
                dataSource: this.items5,
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
//                enter: 'right',
                columns: [
                    { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                    { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                    { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '290px' },
                    { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '230px', 
//                        ntsControl: 'Combobox', tabIndex: 0 
                        },
                    { headerText: self.$i18n('KAF002_72'), key: 'flag', dataType: 'boolean', width: '200px', showHeaderCheckbox: true, ntsControl: 'Checkbox' }
                    
                ], 
                features: [{ name: 'Resizing',
                                columnSettings: [{
                                    columnKey: 'id', allowResizing: true, minimumWidth: 30
                                }, {
                                    columnKey: 'flag', allowResizing: false 
                                }] 
                            },
                            { 
                                name: 'Selection',
                                mode: 'row',
                                multipleSelection: true
                            }
                ],
                ntsFeatures: [
//                    { name: 'CopyPaste' },
                    { name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    }
                    ],
                ntsControls: [{ name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
//                                { name: 'SwitchButtons', options: [{ value: '1', text: 'Option 1' }, { value: '2', text: 'Option 2' }, { value: '3', text: 'Option 3' }], 
//                                    optionsValue: 'value', optionsText: 'text', controlType: 'SwitchButtons', enable: true,
//                                    distinction: { "503": ['1', '2'], "506": ["2", "3"], "600": ["1", "2"] }},
//                                { name: 'Combobox', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                                { name: 'Button', text: 'Open', click: function() { alert("Button!!"); }, controlType: 'Button' },
                                { name: 'DeleteButton', text: 'Delete', controlType: 'DeleteButton', enable: true }]
                });
            
            
            
            
//            応援 pending
            
            
        }
        
        mounted() {
            
        }
        
    }
    const API = {
            startStampApp: 'at/request/application/stamp/startStampApp',
            checkBeforeRegister: 'at/request/application/stamp/checkBeforeRegister',
            register: 'at/request/application/stamp/register',
            changeDate: 'at/request/application/stamp/changeDate'
    }
    class GridItem {
        id: number;
        flag: boolean;
        startTime: string;
        endTime: string;
        text1: string;
        constructor(dataObject: TimePlaceOutput, typeStamp : STAMPTYPE) {
            this.id = dataObject.frameNo;
            this.flag = false;
            let start = _.isNull(dataObject.opStartTime) ? '--:--' : '10:00';
            let end = _.isNull(dataObject.opEndTime) ? '--:--' : '17:30';
            this.startTime = '<div style="display: block; margin: 5px"> <p>'+ start +'</p><input style="width: 50px" data-name="Time Editor" data-bind= "ntsTimeEditor: { constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', enable: enable, readonly: readonly, required: false}" /></div>';
            this.endTime = '<div style="display: block; margin: 5px"> <p>'+ end +'</p><input style="width: 50px" data-name="Time Editor" data-bind= "ntsTimeEditor: { constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', enable: enable, readonly: readonly, required: false}" /></div>';
            if (typeStamp == STAMPTYPE.ATTENDENCE) {
                this.text1 = nts.uk.resource.getText('KAF002_65', [dataObject.frameNo]); 
            } else if (typeStamp == STAMPTYPE.GOOUT_RETURNING) {
                this.text1 = nts.uk.resource.getText('KAF002_67', [dataObject.frameNo]);
            } else if (typeStamp == STAMPTYPE.BREAK) {
                this.text1 = nts.uk.resource.getText('KAF002_75', [dataObject.frameNo]);
            } else if (typeStamp == STAMPTYPE.PARENT) {
                this.text1 = nts.uk.resource.getText('KAF002_68', [dataObject.frameNo]);
            } else if (typeStamp == STAMPTYPE.NURSE) {
                this.text1 = nts.uk.resource.getText('KAF002_69', [dataObject.frameNo]);
            } else if (typeStamp == STAMPTYPE.EXTRAORDINARY) {
                this.text1 = nts.uk.resource.getText('KAF002_66', [dataObject.frameNo]); 
            }
        }
    }
    class GridItem2 {
        id: number;
        flag: boolean;
        startTime: string;
        endTime: string;
        text1: string;
        constructor(dataObject: TimePlaceOutput) {
            this.id = dataObject.frameNo;
            this.flag = false;
            let start = _.isNull(dataObject.opStartTime) ? '--:--' : '10:00';
            let end = _.isNull(dataObject.opEndTime) ? '--:--' : '17:30';
            this.startTime = '<div style="display: block; margin: 5px"> <p>'+ start +'</p><input style="width: 50px" data-name="Time Editor" data-bind= "ntsTimeEditor: { constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', enable: enable, readonly: readonly, required: false}" /></div>';
            this.endTime = '<div style="display: block; margin: 5px"> <p>'+ end +'</p><input style="width: 50px" data-name="Time Editor" data-bind= "ntsTimeEditor: { constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', enable: enable, readonly: readonly, required: false}" /></div>';
            this.text1 = nts.uk.resource.getText('KAF002_65', [dataObject.frameNo]);
        }
    }
    
    class TimePlaceOutput {
        
        opWorkLocationCD: string;
    
        opGoOutReasonAtr: number;
    
        frameNo: number;
    
        opEndTime: number;
    
        opStartTime: number;
    
        constructor(index: number) {
            this.opWorkLocationCD = null;
            this.opGoOutReasonAtr = null;
            this.frameNo = index;
            this.opStartTime = 1000;
            this.opEndTime = 2000;
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
    
    class CellState {
        rowId: string;
        columnKey: string;
        state: Array<any>
        constructor(rowId: string, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
    
    export enum STAMPTYPE {
        ATTENDENCE = 0,
        EXTRAORDINARY = 1,
        GOOUT_RETURNING = 2,
        CHEERING = 3,
        PARENT = 4,
        NURSE = 5,
        BREAK = 6
        
    }
    
    
}