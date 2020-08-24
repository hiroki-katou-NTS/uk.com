module nts.uk.at.view.kaf002_ref.c.viewmodel {
    import GridItem = nts.uk.at.view.kaf002_ref.m.viewmodel.GridItem;
    import TimePlaceOutput = nts.uk.at.view.kaf002_ref.m.viewmodel.TimePlaceOutput;
    import STAMPTYPE = nts.uk.at.view.kaf002_ref.m.viewmodel.STAMPTYPE;
    import TabM = nts.uk.at.view.kaf002_ref.m.viewmodel.TabM;
    import Kaf000AViewModel = nts.uk.at.view.kaf000_ref.a.viewmodel.Kaf000AViewModel;
    @bean()
    class Kaf002CViewModel extends Kaf000AViewModel {
       dataSourceOb: KnockoutObservableArray<any>;
       tabMs: Array<TabM> = [new TabM(this.$i18n('KAF002_29'), true, true),
                              new TabM(this.$i18n('KAF002_31'), true, true),
                              new TabM(this.$i18n('KAF002_76'), true, true),
                              new TabM(this.$i18n('KAF002_32'), true, true),
                              new TabM(this.$i18n('KAF002_33'), true, true),
                              new TabM(this.$i18n('KAF002_34'), false, true)];
    
    //  ※M2.1_2 = ※M
    //  打刻申請起動時の表示情報.打刻申請設定.取消の機能の使用する　＝　使用する(use)
      // set visible for flag column
      isVisibleComlumn: boolean = true;
      isPreAtr: KnockoutObservable<boolean> = ko.observable(true);
        created() {
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