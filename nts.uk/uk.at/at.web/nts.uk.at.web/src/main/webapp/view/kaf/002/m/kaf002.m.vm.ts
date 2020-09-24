module nts.uk.at.view.kaf002_ref.m.viewmodel {
    const template = `

<div id="tab-panel" style="width: 485px !important;"
    data-bind="ntsTabPanel: { dataSource: tabs, active: selectedTab}, if: tabs">


    <!-- ko foreach: nameGrids, -->
    <div style="padding-bottom: 20px"
        data-bind="attr: {'role': 'tabpanel','class': 'tab-content-'+ String($index() +1) + ' ui-tabs-panel ui-corner-bottom ui-widget-content ' + ($index() +1 == 1 ? '' : 'disappear'), 'id': 'tab-'+ String($index() +1), 'aria-labelledby': 'ui-id-' + String($index() +1) , 'aria-hidden': $index() +1 == 1 ? 'false' : 'true'}">
        <table data-bind="attr: {'id': $data}"></table>

    </div>
    <!-- /ko -->


</div>


    `

    @component( {
        name: 'kaf002-m',
        template: template
    } )
    class Kaf002MViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;

        selectedTab: KnockoutObservable<string>;

        // set enable checkbox all
        enableList: Array<KnockoutObservable<boolean>>;

        // tab can load more >=10

        isLinkList: Array<boolean>;

        nameGrids: KnockoutObservableArray<any>;

        //set param
        // display column checkbox
        isVisibleComlumn: boolean;

        //list data
        // set param
        dataSource: Array<Array<GridItem>> = [];

        dataSourceOb: KnockoutObservableArray<any>;
        // set param
        tabMs: Array<TabM>;

        isPreAtr: KnockoutObservable<boolean>;
        tabsTemp: any;
        selectedTemp: any;
        reasonList: Array<GoOutTypeDispControl>;
        mode: KnockoutObservable<number>;
        created(params) {

            const self = this;
            self.mode = params.mode;
            self.reasonList = params.reasonList
            self.tabMs = params.tabMs;
            self.isPreAtr = params.isPreAtr;
            self.isVisibleComlumn = params.isVisibleComlumn;
            self.dataSourceOb = params.dataSourceOb;
            self.dataSource = self.dataSourceOb();
            self.dataSourceOb.subscribe((data) => {
                if (ko.toJS(data)) {
                    self.dataSource = self.dataSourceOb();
                    self.loadAll();
                }
            });
            self.initDataSource();
            // param of parent
            let nameGridsArray = [];
            let paramTabs = [];
            self.enableList = [];
            self.isLinkList = [];
            _.each( self.tabMs, ( item, index ) => {

                let paramTab = {
                    id: 'tab-' + String( index + 1 ), title: item.title, content: '.tab-content-' + String( index + 1 ), enable: ko.observable( item.enable ), visible: ko.observable( item.visible )
                };
                paramTabs.push( paramTab );
                self.enableList.push(ko.observable(false));
                self.isLinkList.push(true);
                nameGridsArray.push('grid' +String(index +1));
            } );
            self.nameGrids = ko.observableArray(nameGridsArray);
            self.tabs = ko.observableArray(paramTabs);
            // must assign param.tabs at mounted since tabs is not render
            self.tabsTemp = params.tabs;
            // select first tab
            self.selectedTab = ko.observable( paramTabs[0].id );
            self.selectedTemp = params.selectedTab;
            self.selectedTab.subscribe(value => {
                let isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
                
                if (value) {
                    // fix UI on IE
                    let el = $('#' + value);
                    let elChrome = $('#tab-panel');
                    
                    if (value == 'tab-1') {
                        self.selectedTemp(0);
                        if (isChrome) {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                elChrome.css('width', '450px');
                            } else {
                                elChrome.css('width', '550px');
                            }  
                        } else {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                el.css('width', '410px');
                            } else {
                                el.css('width', '510px');
                            }
                        }
                    } else if (value == 'tab-2') {
                        self.selectedTemp(1);
                        if (isChrome) {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                elChrome.css('width', '590px');
                            } else {
                                elChrome.css('width', '690px');
                            }                            
                        } else {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                el.css('width', '550px');
                            } else {
                                el.css('width', '650px');
                            } 
                        }
                    } else if (value == 'tab-3') {
                        self.selectedTemp(5);
                        if (isChrome) {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                elChrome.css('width', '450px');
                            } else {
                                elChrome.css('width', '550px');
                            }                            
                        } else {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                el.css('width', '410px');
                            } else {
                                el.css('width', '510px');
                            }  
                        }
                    } else if (value == 'tab-4') {
                        self.selectedTemp(2);
                        if (isChrome) {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                elChrome.css('width', '450px');
                            } else {
                                elChrome.css('width', '550px');
                            }   
                        } else {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                el.css('width', '410px');
                            } else {
                                el.css('width', '510px');
                            } 
                        }
                    } else if (value == 'tab-5') {
                        self.selectedTemp(4);
                        if (isChrome) {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                elChrome.css('width', '450px');
                            } else {
                                elChrome.css('width', '550px');
                            }                            
                        } else {
                            if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                                el.css('width', '410px');
                            } else {
                                el.css('width', '510px');
                            }
                        }
                    }

                }
            })
            self.isPreAtr.subscribe((value) => {
               if(!_.isNull(value) && self.mode() == 0) {
                   self.loadAll();
               }

            });
            
            

        }
        createdReasonItem(reasonList: Array<GoOutTypeDispControl>) {
            let comboItems = [];
            _.forEach(reasonList, (e: GoOutTypeDispControl) => {
                if (e.goOutType == 0) {
                    comboItems.push(new ItemModel('0', '私用'));
                } else if (e.goOutType == 1) {
                    comboItems.push(new ItemModel('1', '公用'));
                } else if (e.goOutType == 2) {
                    comboItems.push(new ItemModel('2', '有償'));
                } else if (e.goOutType == 3) {
                    comboItems.push(new ItemModel('3', '組合'));
                }
            });
            return comboItems;
        }
        doSomething(s: Array<GridItem>) {
            const self = this;
            if (!s) {
                return;
            }
            const index = s[0].index;
            self.isLinkList[index] = false;
            self.loadGrid(ko.toJS(self.nameGrids)[index], s, s[0].typeStamp);
            self.binding();
            self.disableControl();                
        }
        binding() {
            let self = this;
            _.each(document.getElementsByClassName('startTime'), (item, index) => {
                if(!$('.startTime')[index]) return;
                ko.cleanNode($('.startTime')[index]);
                ko.applyBindings(self, item);
             });

            _.each(document.getElementsByClassName('endTime'), (item, index) => {
                if (!$('.endTime')[index]) return;
                ko.cleanNode($('.endTime')[index]);
                ko.applyBindings(self, item);
             });

            _.each(document.getElementsByClassName('flag'), (item, index) => {
                if (!$('.flag')[index]) return;
                ko.cleanNode($('.flag')[index]);
                ko.applyBindings(self, item);
             });
             _.each(ko.toJS(self.nameGrids), (item, index) => {
                 if (!$('#' + item + '_flag')[0]) return;
                 ko.cleanNode($('#' + item + '_flag')[0]);
                 ko.applyBindings(self, document.getElementById(item+'_flag'));
             });
        }
        mounted() {
            const self = this;
            // change tabs by root component
            self.tabsTemp(self.tabs());
            self.loadAll();

        }
        loadAll() {
            const self = this;
            let isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
            if (isChrome) {
                if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                    if (self.selectedTab() == 'tab-2') {
                        $('#tab-panel').css('width', '590px');
                    } else {
                        $('#tab-panel').css('width', '450px');
                    }
                } else {
                    if (self.selectedTab() == 'tab-2') {
                        $('#tab-panel').css('width', '690px');
                    } else {
                        $('#tab-panel').css('width', '550px');
                    }
                }
                
            } else {
                if (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) {
                    if (self.selectedTab() == 'tab-2') {
                        $('#' + self.selectedTab()).css('width', '550px');
                    } else {
                        $('#' + self.selectedTab()).css('width', '410px');
                    }
                } else {
                    if (self.selectedTab() == 'tab-2') {
                        $('#' + self.selectedTab()).css('width', '650px');
                    } else {
                        $('#' + self.selectedTab()).css('width', '510px');
                    }
                }
                
            }
            if (_.isEmpty(self.dataSource)) return;

            _.each(self.dataSource, (item, index) => {
                if (!_.isEmpty(item)) {
                    _.forEach(item, (i, indexI) => {
                        let startRequest = i.startTimeRequest();
                        let endRequest = i.endTimeRequest();
                        if (_.isNaN(Number(startRequest)) || Number(startRequest) > 4319 || Number(startRequest) < (-720)) {
                            i.startTimeRequest(null);
                        }
                        if (_.isNaN(Number(endRequest)) || Number(endRequest) > 4319 || Number(endRequest) < (-720)) {
                            i.endTimeRequest(null);
                        }
                        if (self.mode() == 0) {
                            if (i.typeStamp == STAMPTYPE.GOOUT_RETURNING) {
                                i.typeReason = String (self.createdReasonItem(self.reasonList)[0].code);                            
                            }
                            
                        }
                        // set again id if remove tab1 with 2 first element
                        i.id = indexI +1;
                        i.index = index;
                        // change text element to know value biding from array
                        i.changeElement();

                        if (ko.toJS(self.isPreAtr)) {
//                            self.isVisibleComlumn = false; 
                            i.changeElementByPreAtr();
                        }

                    });

                }
             });
            _.each(self.dataSource, (item, index) => {
                if(!_.isEmpty(item)) {
                    self.loadGrid(ko.toJS(self.nameGrids)[index], item, item[0].typeStamp);
                }
            })
            self.binding();
            self.disableControl();
        }

        disableControl() {
            const self = this;
            if (self.mode() == 2) {
                var loop = window.setInterval(function () {
                    if ($('.startTime input') && $('.endTime input') && $('.enableFlag input')) {
                        _.forEach($('.startTime input'), i => { $(i).prop('disabled', true)});
                        _.forEach($('.endTime input'), i => { $(i).prop('disabled', true)});
                        _.forEach($('.enableFlag input'), i => { $(i).prop('disabled', true)});
                        window.clearInterval(loop);
                    }
                }, 100);
            }
        }
        constructor() {
            super();
            
        }

        initDataSource() {
            const self = this;
            _.each(self.dataSource, (item, index) => {
               _.forEach(item, i => {
                   i.index = index;

                   // change text element to know value biding from array
                   i.changeElement();
               });
            });

        }

        loadGrid(id: string,items: any, type: number) {
            const self = this;
            let isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
            if (!id) {

                return;
            }
                if (!items) {

                return;
            }
            if($('#'+id + '_container').length > 0){
                $('#'+id).ntsGrid("destroy");
            }
            let statesTable = [];
            let numberDisable = 0;
            let isGreater_10 = items.length > 10;

            for (let i = 1; i < items.length + 1; i++) {
                if (!ko.toJS(items[i-1].flagEnable)) {
                    numberDisable++;
                }

            }
            if (self.enableList) {
                _.each(self.enableList, (item, index) => {
                    if (index == items[0].index) {
                        item.subscribe( (value) => {
                            _.forEach(self.dataSource[index], i => {
                                if (ko.toJS(i.flagEnable)) {
                                    i.flagObservable(value);
                                }
                            });
                        });
                    }

                });
            }
//
            let headerFlagContent = '';
            let dataSource;
            _.each(self.isLinkList, (i, index) => {
                if (items[0].index == index) {
                    let paramString = 'enableList[' + String(index) + ']';
                    headerFlagContent = numberDisable != items.length ? '<div class="ntsCheckbox-002" style="display: block" align="center" data-bind="ntsCheckBox: { checked: ' + paramString +'}">' + self.$i18n('KAF002_72')+ '</div>' : '<div style="display: block" align="center">' + self.$i18n('KAF002_72')+ '</div>';

                    dataSource = items.length >= 10 && self.isLinkList[index] ? items.slice(0, 3) : items;
                }

            });

            let optionGrid = {
                    width: (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) ? '420px' : '517px',
                    height: isChrome ? '310px' : (ko.toJS(self.isPreAtr) ? '300px' : '320px'),
                    dataSource: dataSource,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    hidePrimaryKey: true,
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                        { headerText: '', key: 'text1', dataType: 'string', width: '120px' },
                        { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '140px' },
                        { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '140px'},
                        { headerText: headerFlagContent, key: 'flag', dataType: 'string', width: '100px' }

                    ],
                    features: [{ name: 'Resizing',
                                    columnSettings: [{
                                        columnKey: 'id', allowResizing: false, minimumWidth: 30
                                    },  {
                                        columnKey: 'startTime', allowResizing: false, minimumWidth: 30
                                    }, {
                                        columnKey: 'endTime', allowResizing: false, minimumWidth: 30
                                    }, {
                                        columnKey: 'flag', allowResizing: false, minimumWidth: 30
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

            let comboColumns = [
                                { prop: 'name', length: 6 }];
            let comboItems = self.mode() == 0 ? self.createdReasonItem(self.reasonList)  
                            : 
                            [ new ItemModel('0', '私用'),
                               new ItemModel('1', '公用'),
                               new ItemModel('2', '有償'),
                               new ItemModel('3', '組合')];
            let option2 = {
              width: (((!self.isVisibleComlumn && !ko.toJS(self.isPreAtr)) || ko.toJS(self.isPreAtr))) ? '555px' : '655px',
              height: isChrome ? '310px' : (ko.toJS(self.isPreAtr) ? '300px' : '320px'),
              dataSource: dataSource,
              primaryKey: 'id',
              virtualization: true,
              virtualizationMode: 'continuous',
              hidePrimaryKey: true,
              columns: [
                  { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                  { headerText: '', key: 'text1', dataType: 'string', width: '120px' },
                  { headerText: self.$i18n('KAF002_22'), key: 'typeReason', dataType: 'string', width: '137px', ntsControl: 'Combobox' },
                  { headerText: self.$i18n('KAF002_22'), key: 'startTime', dataType: 'string', width: '140px' },
                  { headerText: self.$i18n('KAF002_23'), key: 'endTime', dataType: 'string', width: '140px'},
                  { headerText: headerFlagContent, key: 'flag', dataType: 'string', width: '100px'}

              ],
              features: [{ name: 'Resizing',
                              columnSettings: [{
                                  columnKey: 'id', allowResizing: true, minimumWidth: 30
                              },  {
                                  columnKey: 'startTime', allowResizing: false, minimumWidth: 30
                              }, {
                                  columnKey: 'endTime', allowResizing: false, minimumWidth: 30
                              }, {
                                  columnKey: 'flag', allowResizing: false, minimumWidth: 30
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
                            { name: 'Combobox', width: '50px', height: '100px', options: comboItems, optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: self.mode() != 2 ? true : false, spaceSize: 'small' }
                              ]
              };
            if (!self.isVisibleComlumn || ko.toJS(self.isPreAtr)) {
                option2.columns.pop();
                optionGrid.columns.pop();
            }


            if (type == STAMPTYPE.GOOUT_RETURNING) {
                if ($('#' + id)) {
                    $('#' + id).ntsGrid(option2);
                }
            }else {
                if ($('#' + id)) {
                    $('#' + id).ntsGrid(optionGrid);
                }
            }
            // if isCondition2 => error state of text1
            let nameAtr = 'td[aria-describedby ="'+ id +'_text1"]';
            if ($(nameAtr)) {
                $(nameAtr).addClass('titleColor');
            }
            // add row to display expand row
            if (items.length >= 10 && self.isLinkList[items[0].index]) {
                if ($('#' + id)) {
//                    $('#' + id).append('<tr id="trLink2"><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="4"><div id="moreRow'+ String(items[0].index) + '" style="display: block" align="center"><a data-bind="ntsLinkButton: { action: doSomething.bind($data, dataSource['+ items[0].index +']) }, text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');
                    $('#' + id).append('<tr id="trLink2"><td></td><td class="titleCorlor" style="height: 50px; background-color: #CFF1A5"><div></div></td><td colspan="4"><div id="moreRow'+ String(items[0].index) + '" style="display: block" align="center"><a style="color: blue; text-decoration: underline" data-bind="click: doSomething.bind($data, dataSource['+ items[0].index +']) , text: \'' + self.$i18n('KAF002_73') + '\'"></a></div></td></tr>');
                }

            } else {
                self.isLinkList[items[0].index] = false;
            }

            let moreRow = document.getElementById('moreRow'+String(items[0].index));
            if (moreRow && self.isLinkList[items[0].index]) {
                    ko.applyBindings(self, moreRow);
            }


        }

    }


    export class GridItem {
        id: number;
        flag: string;
        startTimeRequest: KnockoutObservable<number> = ko.observable( null );
        endTimeRequest: KnockoutObservable<number> = ko.observable( null );
        startTimeActual: number;
        endTimeActual: number
        typeReason?: string;
        startTime: string;
        endTime: string;
        text1: string;
        flagObservable: KnockoutObservable<boolean> = ko.observable( false );
        flagEnable: KnockoutObservable<boolean> = ko.observable( true );
        index: number;

        typeStamp: STAMPTYPE
        constructor( dataObject: TimePlaceOutput, typeStamp: STAMPTYPE ) {
            const self = this;
            self.typeStamp = typeStamp;
            self.id = dataObject.frameNo;
            self.typeReason = dataObject.opGoOutReasonAtr ? String(dataObject.opGoOutReasonAtr) : (STAMPTYPE.GOOUT_RETURNING == typeStamp ? '0' : null);
            self.startTimeActual = dataObject.opStartTime;
            self.endTimeActual = dataObject.opEndTime;
            if ( _.isNull( dataObject.opStartTime ) && _.isNull( dataObject.opEndTime ) ) {
                self.flagEnable( false );
            }
            let parseTime = nts.uk.time.minutesBased.clock.dayattr;
            let start = _.isNull(self.startTimeActual) ? '--:--' : parseTime.create(self.startTimeActual).shortText;
            let end = _.isNull(self.endTimeActual) ? '--:--' : parseTime.create(self.endTimeActual).shortText;
            let idGetList = typeStamp == STAMPTYPE.EXTRAORDINARY ? self.id - 3 : self.id - 1;
            let param = 'dataSource[' + String(self.index) +']';
            if ( typeStamp == STAMPTYPE.ATTENDENCE ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_65', [dataObject.frameNo] );
                param = param + 1;
            } else if ( typeStamp == STAMPTYPE.GOOUT_RETURNING ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_67', [dataObject.frameNo] );
                param = param + 3;
            } else if ( typeStamp == STAMPTYPE.BREAK ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_75', [dataObject.frameNo] );
                param = param + 4;
            } else if ( typeStamp == STAMPTYPE.PARENT ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_68', [dataObject.frameNo] );
                param = param + 5;
            } else if ( typeStamp == STAMPTYPE.NURSE ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_69', [dataObject.frameNo] );
                param = param + 6;
            } else if ( typeStamp == STAMPTYPE.EXTRAORDINARY ) {
                this.text1 = nts.uk.resource.getText( 'KAF002_66', [dataObject.frameNo - 2] );
                param = param + 2;
            }
            this.startTime = '<div style="display: block; margin: 0px 5px 5px 5px">'
                + '<span style="display: block; text-align: center">' + start + '</span>'
                + '<div align="center">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': ' + param + '[' + idGetList + '].flagEnable() ? (' + param + '[' + idGetList + '].startTimeActual ? (' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeEditor: {value: ' + param + '[' + idGetList + '].startTimeRequest, enable: !' + param + '[' + idGetList + '].flagObservable() , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';
            this.endTime = '<div style="display: block; margin: 0px 5px 5px 5px">'
                + '<span style="display: block; text-align: center">' + end + '</span>'
                + '<div align="center">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': ' + param + '[' + idGetList + '].flagEnable() ? (' + param + '[' + idGetList + '].endTimeActual ? (' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeEditor: {value: ' + param + '[' + idGetList + '].endTimeRequest, enable: !' + param + '[' + idGetList + '].flagObservable() , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';

            this.flag = '<div  style="display: block" align="center" data-bind="css: !' + param + '[' + idGetList + '].flagEnable ? \'disableFlag\' : \'enableFlag\' , ntsCheckBox: {enable: ' + param + '[' + idGetList + '].flagEnable, checked: ' + param + '[' + idGetList + '].flagObservable}"></div>';
        }


        public changeElement () {
            let self = this;
            let parseTime = nts.uk.time.minutesBased.clock.dayattr;
            let start = _.isNull(self.startTimeActual) ? '--:--' : parseTime.create(self.startTimeActual).shortText;
            let end = _.isNull(self.endTimeActual) ? '--:--' : parseTime.create(self.endTimeActual).shortText;
            let param = 'dataSource[' + String(self.index) +']';

            let idGetList = self.id - 1;
            this.startTime = '<div class="startTime" style="display: block; margin: 0px 5px 5px 5px">'
                + '<span style="display: block; text-align: center">' + start + '</span>'
                + '<div align="center">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': ' + param + '[' + idGetList + '].flagEnable() ? (' + param + '[' + idGetList + '].startTimeActual ? (' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeWithDayEditor: {value: ' + param + '[' + idGetList + '].startTimeRequest, enable: !' + param + '[' + idGetList + '].flagObservable() , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';
            this.endTime = '<div class="endTime" style="display: block; margin: 0px 5px 5px 5px">'
                + '<span style="display: block; text-align: center">' + end + '</span>'
                + '<div align="center">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': ' + param + '[' + idGetList + '].flagEnable() ? (' + param + '[' + idGetList + '].endTimeActual ? (' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeWithDayEditor: {value: ' + param + '[' + idGetList + '].endTimeRequest, enable: !' + param + '[' + idGetList + '].flagObservable() , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';

            this.flag = '<div class="flag" style="display: block" align="center" data-bind="css: !' + param + '[' + idGetList + '].flagEnable ? \'disableFlag\' : \'enableFlag\' , ntsCheckBox: {enable: ' + param + '[' + idGetList + '].flagEnable, checked: ' + param + '[' + idGetList + '].flagObservable}"></div>';
        }
        public changeElementByPreAtr() {
            const self = this;
            let param = 'dataSource[' + String(self.index) +']';
            let idGetList = self.id - 1;
            self.flagObservable(false);
            this.startTime = '<div class="startTime" style="display: block; margin: 0px 5px 5px 5px">'
                + '<div align="center" style="padding-top: 10px; padding-bottom: 5px">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'ntsTimeWithDayEditor: {value: ' + param + '[' + idGetList + '].startTimeRequest , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';
            this.endTime = '<div class="endTime" style="display: block; margin: 0px 5px 5px 5px">'
                + '<div align="center" style="padding-top: 10px; padding-bottom: 5px">'
                + '<input style="width: 90px; text-align: center" data-name="Time Editor" data-bind="'
                + 'ntsTimeWithDayEditor: {value: ' + param + '[' + idGetList + '].endTimeRequest , constraint: \'TimeWithDayAttr\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';
        }
        public convertTimeZoneStampClassification() {
            const self = this;
            if (self.typeStamp == STAMPTYPE.PARENT) {

                return 0;
            } else if (self.typeStamp == STAMPTYPE.NURSE) {

                return 1;
            } else if (self.typeStamp == STAMPTYPE.BREAK) {

                return 2;
            }
        }
        public convertTimeStampAppEnum() {
            const self = this;
            if (self.typeStamp == STAMPTYPE.ATTENDENCE) {

                return 0;
            } else if (self.typeStamp == STAMPTYPE.EXTRAORDINARY) {

                return 1;
            } else if (self.typeStamp == STAMPTYPE.GOOUT_RETURNING) {

                return 2;
            } else if (self.typeStamp == STAMPTYPE.CHEERING) {

                return 3;
            }
        }

    }
    export class GoOutTypeDispControl {
        display: number;
        goOutType: number;
    }

    export class TimePlaceOutput {

        opWorkLocationCD: string;

        opGoOutReasonAtr: number;

        frameNo: number;

        opEndTime: number;

        opStartTime: number;

        constructor( index: number ) {
            this.opWorkLocationCD = null;
            this.opGoOutReasonAtr = null;
            this.frameNo = index;
            this.opStartTime = null;
            this.opEndTime = null;
        }

    }


    export class ItemModel {
        code: string;
        name: string;

        constructor( code: string, name: string ) {
            this.code = code;
            this.name = name;
        }
    }

    export class CellState {
        rowId: number;
        columnKey: string;
        state: Array<any>;
        constructor( rowId: string, columnKey: string, state: Array<any> ) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
    export enum STAMPTYPE {
//        出勤／退勤
        ATTENDENCE = 0,
//        育児
        PARENT = 2,
//        外出／戻り
        GOOUT_RETURNING = 1,
//        応援
        CHEERING = 3,
//        臨時
        EXTRAORDINARY = 4,
//        休憩
        BREAK = 5,
//        介護
        NURSE = 6,

    }
    export class TabM {
        title?: string;
        enable: boolean;
        visible: boolean
        constructor(title: string, enable: boolean, visible: boolean) {
            this.title = title;
            this.enable = enable;
            this.visible = visible;
        }
    }
}
