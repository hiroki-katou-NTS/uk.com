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
        isSendMail: KnockoutObservable<Boolean> = ko.observable(false);
		appType: KnockoutObservable<number> = ko.observable(AppType.STAMP_APPLICATION);
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
        data : any;
        
    created() {
        const self = this;
        self.application = ko.observable(new Application(self.appType()));

        self.loadData([], [], self.appType())
        .then((loadDataFlag: any) => {
            self.appDispInfoStartupOutput.subscribe(value => {
                console.log(value);
                if (value) {
                    self.changeDate();
                }
            });
            if(loadDataFlag) {
                let companyId = __viewContext.user.companyId;
                let command = { 
                        appDispInfoStartupDto: ko.toJS(self.appDispInfoStartupOutput),
                        recoderFlag: false,
                        companyId
                };
            
                return self.$ajax(API.start, command);
            }
        }).then((res: any) => {
            console.log(res);
            self.data = res;
        });
        self.initData();
    }
    changeDataSource() {
       
    }
    
    public createCommandCheckRegister() {
        const self = this;
        let data = _.clone(self.data);
        data.appStampOptional = self.createAppStamp();
        let companyId = __viewContext.user.companyId;
        let agentAtr = false;
        self.application().enteredPerson = __viewContext.user.employeeId;
        self.application().employeeID = __viewContext.user.employeeId;
        self.application().prePostAtr(1);
        let command = {
                companyId,
                agentAtr,
                appStampOutputDto: data,
                applicationDto: ko.toJS(self.application)
                
        };
        self.$ajax(API.checkRegister, command)
            .then(res => {
                console.log(res);
//                if (res) {
                    let command = {
                            applicationDto: ko.toJS(self.application),
                            appStampDto: data.appStampOptional,
                            appStampOutputDto: self.data,
                            recoderFlag: false
                    };
                    return self.$ajax(API.register, command);
//                }
            })
            .then(res => {
                console.log(res);
            })
            .always(err => {
                console.log(err);
            })
        
    }
    public register() {
        const self = this;
        self.createCommandCheckRegister();
    }
    
    
    changeDate() {
        const self = this;
        self.bindActualData();
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
            for (let i = 3; i < 6; i++) {
                let dataObject = new TimePlaceOutput(i);
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
        
        
        let dataSource = [];
        dataSource.push( items1.concat(items2) );
        dataSource.push( items3 );
        dataSource.push( items4 );
        dataSource.push( items5 );
        dataSource.push( items6 );
        dataSource.push([]);
        self.dataSourceOb( dataSource );
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
    
    
        public createAppStamp() {
            const self = this;
            let appStamp = new AppStampDto();
            let listTimeStampApp: Array<TimeStampAppDto> = [],
                listDestinationTimeApp: Array<DestinationTimeAppDto> = [],
                listTimeStampAppOther: Array<TimeStampAppOtherDto> = [],
                listDestinationTimeZoneApp: Array<DestinationTimeZoneAppDto> = [];
            _.forEach(self.dataSourceOb(), (items: GridItem, index) => {
                if (index == 0) {                    
                    _.forEach(items, el => {                       
                        if (!ko.toJS(el.flagObservable)) {
                            if (ko.toJS(el.startTimeRequest)) {
                                let timeStampAppDto = new TimeStampAppDto();
                                let destinationTimeApp = new DestinationTimeAppDto();
                                destinationTimeApp.timeStampAppEnum = el.typeStamp.valueOf();
                                destinationTimeApp.startEndClassification = 1;
                                destinationTimeApp.engraveFrameNo = el.id;
                                timeStampAppDto.destinationTimeApp = destinationTimeApp;
                                timeStampAppDto.timeOfDay = ko.toJS(el.startTimeRequest);
                                timeStampAppDto.workLocationCd = null;
                                timeStampAppDto.appStampGoOutAtr = Number(el.typeReason);
                                listTimeStampApp.push(timeStampAppDto);
                                
                            }
                            
                            if (ko.toJS(el.endTimeRequest)) {
                                let timeStampAppDto = new TimeStampAppDto();
                                let destinationTimeApp = new DestinationTimeAppDto();
                                destinationTimeApp.timeStampAppEnum = el.typeStamp.valueOf();
                                destinationTimeApp.startEndClassification = 0;
                                destinationTimeApp.engraveFrameNo = el.id;
                                timeStampAppDto.destinationTimeApp = destinationTimeApp;
                                timeStampAppDto.timeOfDay = ko.toJS(el.endTimeRequest);
                                timeStampAppDto.workLocationCd = null;
                                timeStampAppDto.appStampGoOutAtr = Number(el.typeReason);
                                listTimeStampApp.push(timeStampAppDto);
                            }
                        } else {
                            if (el.startTimeActual) {
                                let destinationTimeApp = new DestinationTimeAppDto();
                                destinationTimeApp.timeStampAppEnum = el.typeStamp.valueOf();
                                destinationTimeApp.startEndClassification = 1;
                                destinationTimeApp.engraveFrameNo = el.id;
                                listDestinationTimeApp.push(destinationTimeApp)
                            }
                            if (el.endTimeActual) {
                                let destinationTimeApp = new DestinationTimeAppDto();
                                destinationTimeApp.timeStampAppEnum = el.typeStamp.valueOf();
                                destinationTimeApp.startEndClassification = 0;
                                destinationTimeApp.engraveFrameNo = el.id;
                                listDestinationTimeApp.push(destinationTimeApp)
                            }
                        }   
                    })
                }
            });
            appStamp.listTimeStampApp = listTimeStampApp;
            appStamp.listDestinationTimeApp = listDestinationTimeApp;
            appStamp.listTimeStampAppOther = listTimeStampAppOther;
            appStamp.listDestinationTimeZoneApp = listDestinationTimeZoneApp;
            return appStamp;
        }
    
    
    
    }
    
    
    
    class AppStampDto {
        public listTimeStampApp: Array<TimeStampAppDto>;
        public listDestinationTimeApp: Array<DestinationTimeAppDto>;
        public listTimeStampAppOther: Array<TimeStampAppOtherDto>;
        public listDestinationTimeZoneApp: Array<DestinationTimeZoneAppDto>;
        
        public AppStampDto() {
            
        }
    
    }
    class TimeStampAppDto {
        public destinationTimeApp: DestinationTimeAppDto;
        public timeOfDay: number;
        public workLocationCd?: string;
        public appStampGoOutAtr?: number;
        public TimeStampAppDto(destinationTimeApp: DestinationTimeAppDto, timeOfDay: number, workLocationCd?: string, appStampGoOutAtr?: number) {
            this.destinationTimeApp = destinationTimeApp;
            this.timeOfDay = timeOfDay;
            this.workLocationCd = workLocationCd;
            this.appStampGoOutAtr = appStampGoOutAtr;
        }        
    }
    class DestinationTimeAppDto {
        
        public timeStampAppEnum: number;
        public engraveFrameNo: number;
        public startEndClassification: number;
        public supportWork?: number;
        
        public DestinationTimeAppDto(timeStampAppEnum: number, engraveFrameNo: number, startEndClassification: number, supportWork?: number) {
            this.timeStampAppEnum = timeStampAppEnum;
            this.engraveFrameNo = engraveFrameNo;
            this.startEndClassification = startEndClassification;
            this.supportWork = supportWork;
        }
    }
    
    class TimeStampAppOtherDto {
        public destinationTimeZoneApp: DestinationTimeZoneAppDto;
        public timezone: TimeZone;
    }
    class TimeZone {
        public startTime: number;
        public endTime: number;
    }
    class DestinationTimeZoneAppDto {
        public timeZoneStampClassification: number;
        public engraveFrameNo: number;
    }
    
    const API = {
            start: "at/request/application/stamp/startStampApp",
            checkRegister: "at/request/application/stamp/checkBeforeRegister",
            register: "at/request/application/stamp/register"
            
        }
    
}