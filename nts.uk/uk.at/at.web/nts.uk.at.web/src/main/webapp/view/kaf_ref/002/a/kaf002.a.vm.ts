module nts.uk.at.view.kaf002_ref.a.viewmodel {
    
    import GridItem = nts.uk.at.view.kaf002_ref.m.viewmodel.GridItem;
    import TimePlaceOutput = nts.uk.at.view.kaf002_ref.m.viewmodel.TimePlaceOutput;
    import STAMPTYPE = nts.uk.at.view.kaf002_ref.m.viewmodel.STAMPTYPE;
    import TabM = nts.uk.at.view.kaf002_ref.m.viewmodel.TabM;
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002AViewModel extends Kaf000AViewModel {
        dataSourceOb: KnockoutObservableArray<any>;
        application: KnockoutObservable<Application>;
        tabMs: Array<TabM> = [new TabM(this.$i18n('KAF002_29'), true, true),
                          new TabM(this.$i18n('KAF002_31'), true, true),
                          new TabM(this.$i18n('KAF002_76'), true, true),
                          new TabM(this.$i18n('KAF002_32'), true, true),
                          new TabM(this.$i18n('KAF002_33'), true, true),
                          new TabM(this.$i18n('KAF002_34'), false, true)];

        isVisibleComlumn: boolean = true;
        isPreAtr: KnockoutObservable<boolean> = ko.observable(false);
//    ※M2.1_1
//    打刻申請起動時の表示情報.申請設定（基準日関係なし）.複数回勤務の管理　＝　true
    
        isCondition2: boolean = true;

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
        
    created() {
        const self = this;
        self.application = ko.observable(new Application(AppType.STAMP_APPLICATION));

        self.loadData([], [], AppType.STAMP_APPLICATION)
        .then((loadDataFlag: any) => {
            if(loadDataFlag) {
                let ApplicantEmployeeID: null,
                    ApplicantList: null,
                    appDispInfoStartupOutput = ko.toJS(self.appDispInfoStartupOutput),
                    command = { ApplicantEmployeeID, ApplicantList, appDispInfoStartupOutput };
            }
        })
        self.initData();
    }
        
    initData() {
            const self = this;
            self.dataSourceOb = ko.observableArray( [] );
    
            let items1 = (function() {
                let list = []; 
                for (let i = 1; i < 3; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.ATTENDENCE)); 
                }
                
                return list;
            })();
            let items2 = (function() {
                let list = [];
                for (let i = 3; i < 6; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.EXTRAORDINARY));
        
                }
                
                return list;
            })();
            
            let items3 = ( function() {
                let list = [];
    
                for ( let i = 1; i < 11; i++ ) {
                    let dataObject = new TimePlaceOutput( i );
                    list.push( new GridItem( dataObject, STAMPTYPE.GOOUT_RETURNING ) );
                }
    
                return list;
            } )();
    
            let items4 = ( function() {
                let list = [];
    
                for ( let i = 1; i < 11; i++ ) {
                    let dataObject = new TimePlaceOutput( i );
                    list.push( new GridItem( dataObject, STAMPTYPE.BREAK ) );
                }
    
                return list;
            } )();
    
            let items5 = ( function() {
                let list = [];
                for ( let i = 1; i < 3; i++ ) {
                    let dataObject = new TimePlaceOutput( i );
                    list.push( new GridItem( dataObject, STAMPTYPE.PARENT ) );
                }
    
                return list;
            } )();
            
            let items6 = (function() {
                let list = [];
                for (let i = 1; i < 3; i++) {
                    let dataObject = new TimePlaceOutput(i);
                    list.push(new GridItem(dataObject, STAMPTYPE.NURSE));
                }
                
                return list;
            })();
            
            self.dataSourceOb.subscribe(a => {
               if (ko.toJS(a)) {
                   console.log(ko.toJS(a));
               } 
            });
            let dataSource = [];
            dataSource.push( items1.concat(items2) );
            dataSource.push( items3 );
            dataSource.push( items4 );
            dataSource.push( items5 );
            dataSource.push( items6 );
            dataSource.push([]);
            self.dataSourceOb( dataSource );
        }
    
    
    
    }
    
}