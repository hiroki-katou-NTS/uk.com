module nts.uk.at.view.kaf002_ref.c.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import GridItem = nts.uk.at.view.kaf002_ref.m.viewmodel.GridItem;
    import TimePlaceOutput = nts.uk.at.view.kaf002_ref.m.viewmodel.TimePlaceOutput;
    import STAMPTYPE = nts.uk.at.view.kaf002_ref.m.viewmodel.STAMPTYPE;
    import TabM = nts.uk.at.view.kaf002_ref.m.viewmodel.TabM;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;
    import AppStampDto = nts.uk.at.view.kaf002_ref.a.viewmodel.AppStampDto;
    @component({
        name: 'kaf002-c',
        template: '/nts.uk.at.web/view/kaf_ref/002/c/index.html'
    })
    class Kaf002CViewModel extends ko.ViewModel {
       appType: KnockoutObservable<number> = ko.observable(AppType.STAMP_APPLICATION);
       appDispInfoStartupOutput: any;
       approvalReason: KnockoutObservable<string>;
       application: KnockoutObservable<Application>;
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
      comment1: KnockoutObservable<string> = ko.observable('comment1');
      comment2: KnockoutObservable<string> = ko.observable('comment2');
      data: any;
    
       fetchData() {
            const self = this;
            self.$blockui('show');
            let appplication = ko.toJS(self.application) as Application;
            let appId = appplication.appID;
            let companyId = __viewContext.user.companyId;
            let appDispInfoStartupDto = ko.toJS(self.appDispInfoStartupOutput);
            let recoderFlag = false;
            let command = {
                    appId,
                    companyId,
                    appDispInfoStartupDto,
                    recoderFlag
            }
            self.$ajax(API.getDetail, command)
                .done(res => {
                    console.log(res);
                    self.data = res;
                    self.bindActualData();
                }).fail(res => {
                    console.log('fail');
                }).always(() => {
                    self.$blockui('hide');
                });
            
            
        }
    
    bindActualData() {
        const self = this;
        let opActualContentDisplayLst = ko.toJS(self.appDispInfoStartupOutput).appDispInfoWithDateOutput.opActualContentDisplayLst;
        let opAchievementDetail = opActualContentDisplayLst[0].opAchievementDetail;
        if (_.isNull(opAchievementDetail)) {
            
            return;
        }
        let stampRecord = opAchievementDetail.stampRecordOutput;
        
        let items1 = (function() {
            let list = [];
            let timePlaceList = stampRecord.workingTime;
            for (let i = 1; i < 3; i++) {
                let dataObject = new TimePlaceOutput(i);
                _.forEach(timePlaceList, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push(new GridItem(dataObject, STAMPTYPE.ATTENDENCE)); 
            }
            
            return list;
        })();
        let items2 = (function() {
            let list = [];
            let extraordinaryTime = stampRecord.extraordinaryTime;
            for (let i = 3; i < 6; i++) {
                let dataObject = new TimePlaceOutput(i);
                _.forEach(extraordinaryTime, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push(new GridItem(dataObject, STAMPTYPE.EXTRAORDINARY));
    
            }
            
            return list;
        })();
        
        let items3 = ( function() {
            let list = [];
            let outingTime = stampRecord.outingTime;
            for ( let i = 1; i < 11; i++ ) {
                let dataObject = new TimePlaceOutput( i );
                _.forEach(outingTime, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push( new GridItem( dataObject, STAMPTYPE.GOOUT_RETURNING ) );
            }

            return list;
        } )();

        let items4 = ( function() {
            let list = [];
            let breakTime = stampRecord.breakTime;
            for ( let i = 1; i < 11; i++ ) {
                let dataObject = new TimePlaceOutput( i );
                _.forEach(breakTime, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push( new GridItem( dataObject, STAMPTYPE.BREAK ) );
            }

            return list;
        } )();

        let items5 = ( function() {
            let list = [];
            let parentingTime = stampRecord.parentingTime;
            for ( let i = 1; i < 3; i++ ) {
                let dataObject = new TimePlaceOutput( i );
                _.forEach(parentingTime, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push( new GridItem( dataObject, STAMPTYPE.PARENT ) );
            }

            return list;
        } )();
        
        let items6 = (function() {
            let list = [];
            let nursingTime = stampRecord.nursingTime;
            for (let i = 1; i < 3; i++) {
                let dataObject = new TimePlaceOutput(i);
                _.forEach(nursingTime, item => {
                    if (item.frameNo == i) {
                        dataObject.opStartTime = item.opStartTime;
                        dataObject.opEndTime = item.opEndTime;
                        dataObject.opWorkLocationCD = item.opWorkLocationCD;
                        dataObject.opGoOutReasonAtr = item.opGoOutReasonAtr;
                    }
                });
                list.push(new GridItem(dataObject, STAMPTYPE.NURSE));
            }
            
            return list;
        })();
        
        
        let dataSource = [];
        dataSource.push( items1.concat(items2) );
        dataSource.push( items3 );
        dataSource.push( items4 );
        dataSource.push( items5 );
        dataSource.push( items6 );
        dataSource.push([]);
        self.dataSourceOb( dataSource );
    }
    
    
    
    
    
        created(
                params: { 
                    application: any,
                    printContentOfEachAppDto: PrintContentOfEachAppDto,
                    approvalReason: any,
                    appDispInfoStartupOutput: any, 
                    eventUpdate: (evt: () => void ) => void
                }
                ) {
            const self = this;
            
            self.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            self.application = params.application;
            self.approvalReason = params.approvalReason;
            
            self.isPreAtr(self.application().prePostAtr() == 0);
            self.dataSourceOb = ko.observableArray( [] );
            self.fetchData();
//            self.bindActualData();
            
        }
        
        bindDataRequest() {
            
        }
        
        

    }
    const API = {
            start: "at/request/application/stamp/startStampApp",
            checkRegister: "at/request/application/stamp/checkBeforeRegister",
            register: "at/request/application/stamp/register",
            getDetail: "at/request/application/stamp/detailAppStamp"
            
        }
}