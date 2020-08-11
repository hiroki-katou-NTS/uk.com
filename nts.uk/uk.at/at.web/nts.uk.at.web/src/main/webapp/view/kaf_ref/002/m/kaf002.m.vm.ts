module nts.uk.at.view.kaf002_ref.m.viewmodel {
    @component( {
        name: 'kaf002-m',
        template: '/nts.uk.at.web/view/kaf_ref/002/m/index.html'
    } )
    class Kaf002MViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;

        selectedTab: KnockoutObservable<string>;

        // set enable checkbox all

        enableList: Array<KnockoutObservable<boolean>>;

        // tab can load more >=10

        isLinkList: Array<boolean>;

        //visible of tab

        isVisibleList: Array<boolean>;
        
        nameGrids: KnockoutObservableArray<any> = ko.observableArray(['grid1', 'grid2', 'grid3']);

        //list data

        dataSource: Array<Array<GridItem>>;

        tabMs: Array<TabM> = [new TabM('title1', true, true), new TabM('title2', true, true), new TabM('title3', true, true)];
        
        created() {
           
            
            
        }
        constructor() {
            super();
            const self = this;
            //          const lengthDataSource = _.isNull(self.dataSource) ? 0 : _.isLength(self.dataSource);
            //          // do not display component
            //          if (lengthDataSource == 0) {
            //              return;
            //          }
            let paramTabs = [];
            _.each( self.tabMs, ( item, index ) => {
                let paramTab = {
                    id: 'tab-' + String( index + 1 ), title: item.title, content: '.tab-content-' + String( index + 1 ), enable: ko.observable( item.enable ), visible: ko.observable( item.visible )
                };
                paramTabs.push( paramTab );
            } );

            self.tabs = ko.observableArray( paramTabs );
            // select first tab
            self.selectedTab = ko.observable( paramTabs[0].id );
   
            
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
        constructor( dataObject: TimePlaceOutput, typeStamp: STAMPTYPE ) {
            const self = this;
            self.id = dataObject.frameNo;
            self.typeReason = '2';
            self.startTimeActual = dataObject.opStartTime;
            self.endTimeActual = dataObject.opEndTime;
            if ( _.isNull( dataObject.opStartTime ) && _.isNull( dataObject.opEndTime ) ) {
                self.flagEnable( false );
            }
            let start = _.isNull( dataObject.opStartTime ) ? '--:--' : '10:00';
            let end = _.isNull( dataObject.opEndTime ) ? '--:--' : '17:30';
            let idGetList = typeStamp == STAMPTYPE.EXTRAORDINARY ? self.id - 3 : self.id - 1;
            let param = 'items';
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
                + '<input style="width: 50px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': $data.' + param + '[' + idGetList + '].flagEnable() ? ($data.' + param + '[' + idGetList + '].startTimeActual ? ($data.' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeEditor: {value: $data.' + param + '[' + idGetList + '].startTimeRequest, enable: !$data.' + param + '[' + idGetList + '].flagObservable() , constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';
            this.endTime = '<div style="display: block; margin: 0px 5px 5px 5px">'
                + '<span style="display: block; text-align: center">' + end + '</span>'
                + '<div align="center">'
                + '<input style="width: 50px; text-align: center" data-name="Time Editor" data-bind="'
                + 'style:{\'background-color\': $data.' + param + '[' + idGetList + '].flagEnable() ? ($data.' + param + '[' + idGetList + '].endTimeActual ? ($data.' + param + '[' + idGetList + '].flagObservable() ? \'#b1b1b1\' : \'\') : \'#ffc0cb\') : \'\'},'
                + 'ntsTimeEditor: {value: $data.' + param + '[' + idGetList + '].endTimeRequest, enable: !$data.' + param + '[' + idGetList + '].flagObservable() , constraint: \'SampleTimeDuration\', inputFormat: \'time\', mode: \'time\', required: false}" />'
                + '</div>'
                + '</div>';

            this.flag = '<div  style="display: block" align="center" data-bind="css: !$data.' + param + '[' + idGetList + '].flagEnable ? \'disableFlag\' : \'enableFlag\' , ntsCheckBox: {enable: $data.' + param + '[' + idGetList + '].flagEnable, checked: $data.' + param + '[' + idGetList + '].flagObservable}"></div>';
        }
    }

    class TimePlaceOutput {

        opWorkLocationCD: string;

        opGoOutReasonAtr: number;

        frameNo: number;

        opEndTime: number;

        opStartTime: number;

        constructor( index: number ) {
            this.opWorkLocationCD = null;
            this.opGoOutReasonAtr = null;
            this.frameNo = index;
            if ( index == 1 ) {
                this.opStartTime = 650;
                this.opEndTime = null;
            } else if ( index == 3 ) {
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

        constructor( code: string, name: string ) {
            this.code = code;
            this.name = name;
        }
    }

    class CellState {
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
        ATTENDENCE = 0,
        EXTRAORDINARY = 1,
        GOOUT_RETURNING = 2,
        CHEERING = 3,
        PARENT = 4,
        NURSE = 5,
        BREAK = 6

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
