module nts.uk.at.view.kaf002_ref.a.viewmodel {
    
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002AViewModel extends Kaf000AViewModel {
        
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        enable1: KnockoutObservable<boolean> = ko.observable(false);
        enable2: KnockoutObservable<boolean> = ko.observable(false);
        enable3: KnockoutObservable<boolean> = ko.observable(false);
        enable4: KnockoutObservable<boolean> = ko.observable(false);
        enable5: KnockoutObservable<boolean> = ko.observable(false);
        isLink1: boolean = true;
        isLink2: boolean = true;
        isLink3: boolean = true;
        isLink4: boolean = true;
        isLink5: boolean = true;
        readonly: KnockoutObservable<boolean>;
    
//    ※M2.1_2 = ※M
//    打刻申請起動時の表示情報.打刻申請設定.取消の機能の使用する　＝　使用する(use)
    // set visible for flag column
        isCondition1: boolean = true;
    
//    ※M2.1_1
//    打刻申請起動時の表示情報.申請設定（基準日関係なし）.複数回勤務の管理　＝　true
    
        isCondition2: boolean = false;

//    打刻申請起動時の表示情報.臨時勤務利用　＝　true
    
        isCondition3: boolean = true;
    
//  ※1打刻申請起動時の表示情報.打刻申請の反映.出退勤を反映する　＝　する
    
        isCondition4: boolean = true;
//   ※2打刻申請起動時の表示情報.打刻申請の反映.外出時間帯を反映する　＝　する
    
        isCondition5: boolean = true;
    
//    ※3打刻申請起動時の表示情報.打刻申請の反映.休憩時間帯を反映する　＝　する
    
        isCondition6: boolean = true;
//    ※4打刻申請起動時の表示情報.打刻申請の反映.育児時間帯を反映する　＝　する
    
        isCondition7: boolean = true;
//    ※5打刻申請起動時の表示情報.打刻申請の反映.介護時間帯を反映する　＝　する
    
        isCondition8: boolean = true;
//    ※6打刻申請起動時の表示情報.打刻申請の反映.終了を反映する　＝　する
    
        isCondition9: boolean = true;
    
        items1 : any;
        items2: any;  
        items3: any;
        items4: any;
        items5: any;
        items6: any;
        
        
        
        
       
        doSomething(s: string) {
            const self = this;
            if (s == '1') {
                self.isLink1 = false;
                self.loadGrid('#grid1', self.items1.concat(self.items2), 1);
                // bind again data
                if (!_.isNull(document.getElementById('grid1_flag'))) {
                    ko.applyBindings(self, document.getElementById('grid1_flag'));                    
                }
                if (!_.isNull(document.getElementById('grid1'))) {
                    ko.applyBindings(self, document.getElementById('grid1'));                    
                }
            } else if (s == '2') {
                self.isLink2 = false;
                self.loadGrid('#grid2', self.items3, 2); 
                if (!_.isNull(document.getElementById('grid2_flag'))) {
                    ko.applyBindings(self, document.getElementById('grid2_flag'));                    
                }
                if (!_.isNull(document.getElementById('grid2'))) {
                    ko.applyBindings(self, document.getElementById('grid2'));                    
                }
            } else if (s == '3') {
                self.isLink3 = false;
                self.loadGrid('#grid3', self.items4, 3);
                if (!_.isNull(document.getElementById('grid3_flag'))) {
                    ko.applyBindings(self, document.getElementById('grid3_flag'));                    
                }
                if (!_.isNull(document.getElementById('grid3'))) {
                    ko.applyBindings(self, document.getElementById('grid3'));                    
                }
            } else if (s == '4') {
                self.isLink4 = false;
                self.loadGrid('#grid4', self.items5, 4);
                if (!_.isNull(document.getElementById('grid4_flag'))) {
                    ko.applyBindings(self, document.getElementById('grid4_flag'));                    
                }
                if (!_.isNull(document.getElementById('grid4'))) {
                    ko.applyBindings(self, document.getElementById('grid4'));                    
                }
            } else if (s == '5') {
                self.isLink5 = false;
                self.loadGrid('#grid5', self.items6, 5); 
                if (!_.isNull(document.getElementById('grid5_flag'))) {
                    ko.applyBindings(self, document.getElementById('grid5_flag'));                    
                }
                if (!_.isNull(document.getElementById('grid5'))) {
                    ko.applyBindings(self, document.getElementById('grid5'));                    
                }
            }
        }
        created() {
            const self = this;
            
            self.items1 = (function() {
                let list = []; 
                for (let i = 1; i < 3; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    if (!(i >= 2 && !self.isCondition2)) {
                        list.push(new GridItem(dataObject, STAMPTYPE.ATTENDENCE)); 
                    } 
                }
                
                return list;
            })();
            self.items2 = (function() {
                let list = [];
                for (let i = 3; i < 6; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    if (self.isCondition3) {
                        list.push(new GridItem(dataObject, STAMPTYPE.EXTRAORDINARY));
                        
                    }
                    
                    
                }
                
                return list;
            })();
            
            self.items3 = (function() {
                let list = [];
                if (!self.isCondition5) {
                    return;
                }
                for (let i = 1; i < 11; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.GOOUT_RETURNING));
                }
                
                return list;
            })();
            
            self.items4 = (function() {
                let list = [];
                if (!self.isCondition6) {
                    return;
                }
                for (let i = 1; i < 11; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.BREAK));
                }
                
                return list;
            })();
            
            self.items5 = (function() {
                let list = [];
                for (let i = 1; i < 3; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.PARENT));
                }
                
                return list;
            })();
            
            self.items6 = (function() {
                let list = [];
                for (let i = 1; i < 3; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.NURSE));
                }
                
                return list;
            })();
            self.readonly = ko.observable(false);
            
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
            
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: self.$i18n('KAF002_29'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(self.isCondition4)},
                {id: 'tab-2', title: self.$i18n('KAF002_31'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(self.isCondition5)},
                {id: 'tab-3', title: self.$i18n('KAF002_76'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(self.isCondition6)},
                {id: 'tab-4', title: self.$i18n('KAF002_32'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(self.isCondition7)},
                {id: 'tab-5', title: self.$i18n('KAF002_33'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(self.isCondition8)},
                {id: 'tab-6', title: self.$i18n('KAF002_34'), content: '.tab-content-6', enable: ko.observable(true), visible: ko.observable(self.isCondition9)}
            ]);
            
            self.selectedTab = ko.observable('tab-1');
            
     
//            出勤／退勤
            
            self.loadGrid('#grid1', self.items1.concat(self.items2), 1);

//            外出
            
            self.loadGrid('#grid2', self.items3, 2);
            
  
//            休憩           
            
            self.loadGrid('#grid3', self.items4, 3);

            
//            育児
            
            self.loadGrid('#grid4', self.items5, 4);

              
//            介護
            
            self.loadGrid('#grid5', self.items6, 5);


            
//            応援 pending
            
            
        }
        
        mounted() {
            
        }
        
        
        loadGrid(id: string,items: any, type: number) {
            const self = this;
            if (!items) {
                
                return;
            }
            if($(id + '_container').length > 0){
                $(id).ntsGrid("destroy");
            }
            let statesTable = [];
            let numberDisable = 0;
            let isGreater_10 = items.length > 10;
            
            for (let i = 1; i < items.length + 1; i++) {
//                statesTable.push(new CellState(i, "text1", ['titleColor'])); 
                if (!ko.toJS(items[i-1].flagEnable)) {
                    numberDisable++;
                }
                             
            }
            if (type == 1) {            
                self.enable1.subscribe((value) => {
                    _.forEach(items, item => {
                       if (ko.toJS(item.flagEnable)) {
                           item.flagObservable(value);                       
                       }
                    });
                });
            } else if (type == 2) {
                self.enable2.subscribe((value) => {
                    _.forEach(items, item => {
                       if (ko.toJS(item.flagEnable)) {
                           item.flagObservable(value);                       
                       }
                    });
                });
            } else if (type == 3) {
                self.enable3.subscribe((value) => {
                    _.forEach(items, item => {
                       if (ko.toJS(item.flagEnable)) {
                           item.flagObservable(value);                       
                       }
                    });
                });
            } else if (type == 4) {
                self.enable4.subscribe((value) => {
                    _.forEach(items, item => {
                       if (ko.toJS(item.flagEnable)) {
                           item.flagObservable(value);                       
                       }
                    });
                });
            } else if (type == 5) {
                self.enable5.subscribe((value) => {
                    _.forEach(items, item => {
                       if (ko.toJS(item.flagEnable)) {
                           item.flagObservable(value);                       
                       }
                    });
                });
            }
            let headerFlagContent = numberDisable != items.length ? '<div style="display: block" align="center" data-bind="ntsCheckBox: { checked: enable' + type + '}">' + self.$i18n('KAF002_72')+ '</div>' : '<div style="display: block" align="center">' + self.$i18n('KAF002_72')+ '</div>';
            let dataSource;
            if (type == 1) {
                dataSource = items.length >= 10 && self.isLink1 ? items.slice(0, 3) : items;
            } else if (type == 2) {
                dataSource = items.length >= 10 && self.isLink2 ? items.slice(0, 3) : items;
            } else if (type == 3) {
                dataSource = items.length >= 10 && self.isLink3 ? items.slice(0, 3) : items;
            } else if (type == 4) {
                dataSource = items.length >= 10 && self.isLink4 ? items.slice(0, 3) : items;
            } else if (type == 5) {
                dataSource = items.length >= 10 && self.isLink5 ? items.slice(0, 3) : items;
            }
            let optionGrid = { 
                    width: '100%',
                    height: '360px',
                    dataSource: dataSource,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    hidePrimaryKey: true,
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                        { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                        { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '100px' },
                        { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '100px'},
                        { headerText: headerFlagContent, key: 'flag', dataType: 'string', width: '100px' }
    
                    ], 
                    features: [{ name: 'Resizing',
                                    columnSettings: [{
                                        columnKey: 'id', allowResizing: false, minimumWidth: 30
                                    }, {
                                        columnKey: 'flag', allowResizing: false, minimumWidth: 30
                                    }, {
                                        columnKey: 'startTime', allowResizing: false, minimumWidth: 30
                                    }, {
                                        columnKey: 'endTime', allowResizing: false, minimumWidth: 30
                                    }
                                    ] 
                                },
                                { 
                                    name: 'Selection',
                                    mode: 'row',
                                    multipleSelection: true
                                }
                    ],
                    ntsFeatures: [
                        { 
                            name: 'CellState',
                            rowId: 'rowId',
                            columnKey: 'columnKey',
                            state: 'state',
                            states: statesTable
                        }
                        ],
                    ntsControls: [
                                   
                               ]
            
                    };
            
            let comboColumns = [{ prop: 'code', length: 2 },
                                { prop: 'name', length: 4 }];
            let comboItems = [ new ItemModel('1', '基本給'),
                               new ItemModel('2', '役職手当'),
                               new ItemModel('3', '基本給2') ];
            let option2 = { 
              width: '100%',
              height: '360px',
              dataSource: dataSource,
              primaryKey: 'id',
              virtualization: true,
              virtualizationMode: 'continuous',
              hidePrimaryKey: true,
              columns: [
                  { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                  { headerText: '', key: 'text1', dataType: 'string', width: '120px' }, 
                  { headerText: self.$i18n('KAF002_22'), key: 'typeReason', dataType: 'string', width: '137px', ntsControl: 'Combobox' }, 
                  { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '100px' },
                  { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '100px'},
                  { headerText: headerFlagContent, key: 'flag', dataType: 'string', width: '100px'}
                  
              ], 
              features: [{ name: 'Resizing',
                              columnSettings: [{
                                  columnKey: 'id', allowResizing: true, minimumWidth: 30
                              }, {
                                  columnKey: 'flag', allowResizing: false, minimumWidth: 30
                              }, {
                                  columnKey: 'startTime', allowResizing: false, minimumWidth: 30
                              }, {
                                  columnKey: 'endTime', allowResizing: false, minimumWidth: 30
                              }
                              ] 
                          },
                          { 
                              name: 'Selection',
                              mode: 'row',
                              multipleSelection: true
                          }
              ],
              ntsFeatures: [
                  { name: 'CellState',
                      rowId: 'rowId',
                      columnKey: 'columnKey',
                      state: 'state',
                      states: statesTable
                  }
                  ],
              ntsControls: [
                            { name: 'Combobox', width: '50px', height: '100px', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true, spaceSize: 'small' }
                              ]
              };
            if (!self.isCondition1) {
                if (type == 2){
                    option2.columns.pop();
                }else {                    
                    optionGrid.columns.pop();
                }
            }
            
            if (type == 2) {
                $(id).ntsGrid(option2);
            }else {                
                $(id).ntsGrid(optionGrid);
            }
            // if isCondition2 => error state of text1
            let nameAtr = 'td[aria-describedby ="grid' + type + '_text1"]';
            $(nameAtr).addClass('titleColor');
            // add row to display expand row
            if (items.length >= 10) {
                if (type == 1 && self.isLink1) {
                    $(id).append('<tr><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="3"><div style="display: block" align="center"><a  data-bind="ntsLinkButton: { action: doSomething.bind($data, ' + type + ') }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');
                } else if(type == 2 && self.isLink2) {
                    $(id).append('<tr id="trLink2"><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="4"><div style="display: block" align="center"><a  data-bind="ntsLinkButton: { action: doSomething.bind($data, ' + type + ') }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');

                } else if(type == 3 && self.isLink3) {
                    $(id).append('<tr><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="3"><div style="display: block" align="center"><a  data-bind="ntsLinkButton: { action: doSomething.bind($data, ' + type + ') }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');

                } else if(type == 4 && self.isLink4) {
                    $(id).append('<tr><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="3"><div style="display: block" align="center"><a  data-bind="ntsLinkButton: { action: doSomething.bind($data, ' + type + ') }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');

                } else if(type == 5 && self.isLink5) {
                    $(id).append('<tr><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="3"><div style="display: block" align="center"><a  data-bind="ntsLinkButton: { action: doSomething.bind($data, ' + type + ') }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');

                }
 
            }   
            
            
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
        flag: string;
        startTimeRequest: KnockoutObservable<number> = ko.observable(null);
        endTimeRequest: KnockoutObservable<number> = ko.observable(null);
        startTimeActual: number;
        endTimeActual: number
        typeReason?: string;
        startTime: string;
        endTime: string;
        text1: string;
        flagObservable: KnockoutObservable<boolean> = ko.observable(false);
        flagEnable: KnockoutObservable<boolean> = ko.observable(true);
        constructor(dataObject: TimePlaceOutput, typeStamp : STAMPTYPE) {
            const self = this;
            self.id = dataObject.frameNo;
            self.typeReason = '2';
            self.startTimeActual = dataObject.opStartTime;
            self.endTimeActual = dataObject.opEndTime;
            if (_.isNull(dataObject.opStartTime) && _.isNull(dataObject.opEndTime)) {
                self.flagEnable(false);
            }
            let start = _.isNull(dataObject.opStartTime) ? '--:--' : '10:00';
            let end = _.isNull(dataObject.opEndTime) ? '--:--' : '17:30';
            let idGetList = typeStamp == STAMPTYPE.EXTRAORDINARY ? self.id - 3 : self.id - 1;
            let param = 'items';
            if (typeStamp == STAMPTYPE.ATTENDENCE) {
                this.text1 = nts.uk.resource.getText('KAF002_65', [dataObject.frameNo]); 
                param = param + 1;
            } else if (typeStamp == STAMPTYPE.GOOUT_RETURNING) {
                this.text1 = nts.uk.resource.getText('KAF002_67', [dataObject.frameNo]);
                param = param + 3;
            } else if (typeStamp == STAMPTYPE.BREAK) {
                this.text1 = nts.uk.resource.getText('KAF002_75', [dataObject.frameNo]);
                param = param + 4;
            } else if (typeStamp == STAMPTYPE.PARENT) {
                this.text1 = nts.uk.resource.getText('KAF002_68', [dataObject.frameNo]);
                param = param + 5;
            } else if (typeStamp == STAMPTYPE.NURSE) {
                this.text1 = nts.uk.resource.getText('KAF002_69', [dataObject.frameNo]);
                param = param + 6;
            } else if (typeStamp == STAMPTYPE.EXTRAORDINARY) {
                this.text1 = nts.uk.resource.getText('KAF002_66', [dataObject.frameNo -2]); 
                param = param + 2;
            }
            this.startTime = '<div style="display: block; margin: 0px 5px 5px 5px">'
                                   +'<span style="display: block; text-align: center">'+ start +'</span>'
                                   +'<div align="center">'
                                           +'<input style="width: 50px; text-align: center" data-name="Time Editor" data-bind="'
                                           +'style:{\'background-color\': $data.'+ param +'['+ idGetList +'].flagEnable() ? ($data.'+param+'['+ idGetList + '].startTimeActual ? ($data.' + param + '['+ idGetList +'].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},' 
                                           +'ntsTimeEditor: {value: $data.'+ param +'['+ idGetList +'].startTimeRequest, enable: !$data.' + param +'[' + idGetList +'].flagObservable() , constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', readonly: readonly, required: false}" />'
                                   +'</div>'
                              +'</div>';
            this.endTime = '<div style="display: block; margin: 0px 5px 5px 5px">'
                                +'<span style="display: block; text-align: center">'+ end +'</span>'
                                +'<div align="center">'
                                        +'<input style="width: 50px; text-align: center" data-name="Time Editor" data-bind="'
                                        +'style:{\'background-color\': $data.'+ param +'['+ idGetList +'].flagEnable() ? ($data.'+param+'['+ idGetList + '].endTimeActual ? ($data.' + param + '['+ idGetList +'].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},' 
                                        +'ntsTimeEditor: {value: $data.'+ param +'['+ idGetList +'].endTimeRequest, enable: !$data.' + param +'[' + idGetList +'].flagObservable() , constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', readonly: readonly, required: false}" />'
                                +'</div>'
                           +'</div>';
            
            this.flag = '<div  style="display: block" align="center" data-bind="css: !$data.' + param + '[' + idGetList + '].flagEnable ? \'disableFlag\' : \'enableFlag\' , ntsCheckBox: {enable: $data.' + param + '[' + idGetList + '].flagEnable, checked: $data.' + param + '[' + idGetList + '].flagObservable}"></div>';
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
            if (index == 1) {
                this.opStartTime = 650;
                this.opEndTime = null;
            }else if (index == 3){
                this.opStartTime = 500;
                this.opEndTime = 1500;
                
            } else {
                this.opStartTime = index % 2 == 0 ? 1000 : null;
                this.opEndTime = index % 2 == 0 ? 1500 : null;
            }
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
        rowId: number;
        columnKey: string;
        state: Array<any>;
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