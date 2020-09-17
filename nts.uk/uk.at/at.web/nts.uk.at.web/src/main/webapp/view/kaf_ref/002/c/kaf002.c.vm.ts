module nts.uk.at.view.kaf002_ref.c.viewmodel {
    import Application = nts.uk.at.view.kaf000_ref.shr.viewmodel.Application;
    import GridItem = nts.uk.at.view.kaf002_ref.m.viewmodel.GridItem;
    import TimePlaceOutput = nts.uk.at.view.kaf002_ref.m.viewmodel.TimePlaceOutput;
    import STAMPTYPE = nts.uk.at.view.kaf002_ref.m.viewmodel.STAMPTYPE;
    import TabM = nts.uk.at.view.kaf002_ref.m.viewmodel.TabM;
    import AppType = nts.uk.at.view.kaf000_ref.shr.viewmodel.model.AppType;
    import PrintContentOfEachAppDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.PrintContentOfEachAppDto;
    import AppStampDto = nts.uk.at.view.kaf002_ref.a.viewmodel.AppStampDto;
    import TimeStampAppDto = nts.uk.at.view.kaf002_ref.a.viewmodel.TimeStampAppDto;
    import DestinationTimeAppDto = nts.uk.at.view.kaf002_ref.a.viewmodel.DestinationTimeAppDto;
    import TimeStampAppOtherDto = nts.uk.at.view.kaf002_ref.a.viewmodel.TimeStampAppOtherDto;
    import TimeZone = nts.uk.at.view.kaf002_ref.a.viewmodel.TimeZone;
    import DestinationTimeZoneAppDto = nts.uk.at.view.kaf002_ref.a.viewmodel.DestinationTimeZoneAppDto;
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
       // display condition
       isM: KnockoutObservable<boolean> = ko.observable(false);
       // select tab M
       selectedCode: KnockoutObservable<number> = ko.observable(0);
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
      comment1: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
      comment2: KnockoutObservable<Comment> = ko.observable(new Comment('', true, ''));
      data: any;
    
    
    
        bindComment(data: any) {
            const self = this;
            _.forEach(self.data.appStampSetting.settingForEachTypeLst, i => {
               if (i.stampAtr == ko.toJS(self.selectedCode)) {
                   let commentBot = i.bottomComment;
                   self.comment2(new Comment(commentBot.comment, commentBot.bold, commentBot.colorCode));
                   let commentTop = i.bottomComment;
                   self.comment1(new Comment(commentTop.comment, commentTop.bold, commentTop.colorCode));
               }
            });
        }  
       fetchData() {
            const self = this;
            self.$blockui('show');
            let appplication = ko.toJS(self.application) as Application;
            let appId = appplication.appID;
            let companyId = self.$user.companyId;
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
                    self.bindTabM(self.data);
                    self.bindComment(self.data);
                }).fail(res => {
                    console.log('fail');
                }).always(() => {
                    self.$blockui('hide');
                });
            
            
        }
       
       bindTabM(data: any) {
           const self = this;
           if (data.appStampReflectOptional) {
               let reflect = data.appStampReflectOptional;
               self.tabMs[0].visible = reflect.temporaryAttendence && reflect.attendence;
               self.tabMs[1].visible = reflect.outingHourse;
               self.tabMs[2].visible = reflect.breakTime;
               self.tabMs[3].visible = reflect.parentHours;
               self.tabMs[4].visible = reflect.nurseTime;
               // not use
               self.tabMs[5].visible = false;
               
           }
           self.isM(true);
       }  
    bindDataRequest(element: GridItem, type: number) {
       const self = this;
       let appStampDto = self.data.appStampOptional as AppStampDto,
           timeStampAppDto = appStampDto.listTimeStampApp as Array<TimeStampAppDto>,
           destinationTimeAppDto = appStampDto.listDestinationTimeApp as Array<DestinationTimeAppDto>,
           timeStampAppOtherDto = appStampDto.listTimeStampAppOther as Array<TimeStampAppOtherDto>,
           destinationTimeZoneAppDto = appStampDto.listDestinationTimeZoneApp as Array<DestinationTimeZoneAppDto>;
       if (type ==1) {
           if (timeStampAppDto) {
               let items = _.filter(timeStampAppDto, (x: TimeStampAppDto) => {
                   let destinationTimeAppDto = x.destinationTimeApp as DestinationTimeAppDto;
                   return destinationTimeAppDto.timeStampAppEnum == element.typeStamp.valueOf() 
                           && destinationTimeAppDto.engraveFrameNo == (element.typeStamp.valueOf() != 1 ? element.id : element.id - 2);
               }) as Array<TimeStampAppDto>;
               _.forEach(items, (x: TimeStampAppDto) => {
                     if (x) {
                         let desItem = x.destinationTimeApp as DestinationTimeAppDto;
                         if (desItem.startEndClassification == 0) {
                             element.startTimeRequest(x.timeOfDay);
                         }
                         if (desItem.startEndClassification == 1) {
                             element.endTimeRequest(x.timeOfDay);
                         }             
                     }
               });

           }
           
           if (destinationTimeAppDto) {
               let itemDes = _.findLast(destinationTimeAppDto, (x: any) => {
                   return x.timeStampAppEnum == element.typeStamp.valueOf() 
                   && x.engraveFrameNo == (element.typeStamp.valueOf() != 1 ? element.id : element.id - 2);
               }) as DestinationTimeAppDto;
               
               if (itemDes) {
                   element.flagObservable(true);
               }
           }
           
       } else if (type == 2) {
           if (timeStampAppOtherDto) {
               let items = _.filter(timeStampAppOtherDto, (x: TimeStampAppOtherDto) => {
                   let destinationTimeZoneAppDto = x.destinationTimeZoneApp as DestinationTimeZoneAppDto;
                   return destinationTimeZoneAppDto.timeZoneStampClassification == element.convertTimeStampAppEnum() 
                           && destinationTimeZoneAppDto.engraveFrameNo == (element.typeStamp.valueOf() != 1 ? element.id : element.id - 2);
               }) as Array<TimeStampAppOtherDto>;
               if (items.length > 0) {
                   let item = items[0] as TimeStampAppOtherDto;
                   element.startTimeRequest(item.timeZone.startTime);
                   element.endTimeRequest(item.timeZone.endTime);
               }
           }
           if (destinationTimeZoneAppDto) {
               let itemDes = _.findLast(destinationTimeZoneAppDto, (x: any) => {
                   return x.timeStampAppEnum == element.typeStamp.valueOf() 
                   && x.engraveFrameNo == (element.typeStamp.valueOf() != 1 ? element.id : element.id - 2);
               }) as DestinationTimeZoneAppDto;
               if (itemDes) {
                   element.flagObservable(true);
               }
           }
       }
        
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
                let gridItem = new GridItem(dataObject, STAMPTYPE.ATTENDENCE) as GridItem;
                self.bindDataRequest(gridItem, 1);
                list.push(gridItem); 
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
                let gridItem = new GridItem(dataObject, STAMPTYPE.EXTRAORDINARY);
                self.bindDataRequest(gridItem, 1);
                list.push(gridItem);
    
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
                let gridItem = new GridItem( dataObject, STAMPTYPE.GOOUT_RETURNING );
                self.bindDataRequest(gridItem, 1);
                list.push(gridItem);
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
                let gridItem = new GridItem(dataObject, STAMPTYPE.BREAK);
                self.bindDataRequest(gridItem, 2);
                list.push(gridItem);
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
                let gridItem = new GridItem(dataObject, STAMPTYPE.PARENT);
                self.bindDataRequest(gridItem, 2);
                list.push(gridItem);
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
                let gridItem = new GridItem(dataObject, STAMPTYPE.NURSE);
                self.bindDataRequest(gridItem, 2);
                list.push(gridItem);
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
            self.selectedCode.subscribe(value => {
                if (value && self.data) {
                    self.bindComment(self.data);
                }
            });
            self.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            self.application = params.application;
            self.approvalReason = params.approvalReason;
            
            self.isPreAtr(self.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr == 0);
            self.dataSourceOb = ko.observableArray( [] );
            self.fetchData();

            params.eventUpdate(self.update.bind(self));
            
        }
        
        update() {
            console.log('update');
            const self = this;
            if (!self.appDispInfoStartupOutput().appDetailScreenInfo) {
                return;
            }
            let application = ko.toJS(self.application);
            let applicationDto = self.appDispInfoStartupOutput().appDetailScreenInfo.application as any;
            applicationDto.prePostAtr = application.prePostAtr;
            applicationDto.opAppReason = application.opAppReason;
            applicationDto.opAppStandardReasonCD = application.opAppStandardReasonCD;    
            applicationDto.opReversionReason = application.opReversionReason;
            let recoderFlag = false;
            let appStampOutputDto = self.data;
            let appStampDto = {} as AppStampDto;
            appStampDto = self.createAppStamp() as AppStampDto;
            let command = {
                    applicationDto,
                    recoderFlag,
                    appStampDto,
                    appStampOutputDto
            }
            self.$blockui('show');
            self.$ajax(API.update, command)
                .done(res => {
                    console.log(res);
                }).fail(res => {
                    console.log(res);
                }).always(() => {
                    self.$blockui('hide');
                })
        }
        
        public createAppStamp() {
            const self = this;
            let appStamp = {} as AppStampDto;
            let listTimeStampApp: Array<TimeStampAppDto> = [],
                listDestinationTimeApp: Array<DestinationTimeAppDto> = [],
                listTimeStampAppOther: Array<TimeStampAppOtherDto> = [],
                listDestinationTimeZoneApp: Array<DestinationTimeZoneAppDto> = [];
            _.forEach(self.dataSourceOb(), (items: GridItem, index) => {
//                出勤／退勤 , 外出
                if (index == 0 || index == 1) {                    
                    _.forEach(items, (el: GridItem) => {                       
                        if (!ko.toJS(el.flagObservable)) {
                            if (ko.toJS(el.startTimeRequest)) {
                                let timeStampAppDto = {} as TimeStampAppDto;
                                let destinationTimeApp = {} as DestinationTimeAppDto;
                                destinationTimeApp.timeStampAppEnum = el.convertTimeStampAppEnum();
                                destinationTimeApp.startEndClassification = START_CLASSIFICATION;
                                destinationTimeApp.engraveFrameNo = el.typeStamp == STAMPTYPE.EXTRAORDINARY ? el.id -2 : el.id;
                                timeStampAppDto.destinationTimeApp = destinationTimeApp;
                                timeStampAppDto.timeOfDay = ko.toJS(el.startTimeRequest);
                                timeStampAppDto.workLocationCd = null;
                                timeStampAppDto.appStampGoOutAtr = Number(el.typeReason);
                                listTimeStampApp.push(timeStampAppDto);
                                
                            }
                            
                            if (ko.toJS(el.endTimeRequest)) {
                                let timeStampAppDto = {} as TimeStampAppDto;
                                let destinationTimeApp = {} as DestinationTimeAppDto;
                                destinationTimeApp.timeStampAppEnum = el.convertTimeStampAppEnum();
                                destinationTimeApp.startEndClassification = END_CLASSIFICATION;
                                destinationTimeApp.engraveFrameNo = el.typeStamp == STAMPTYPE.EXTRAORDINARY ? el.id -2 : el.id;
                                timeStampAppDto.destinationTimeApp = destinationTimeApp;
                                timeStampAppDto.timeOfDay = ko.toJS(el.endTimeRequest);
                                timeStampAppDto.workLocationCd = null;
                                timeStampAppDto.appStampGoOutAtr = Number(el.typeReason);
                                listTimeStampApp.push(timeStampAppDto);
                            }
                        } else {
                            if (el.startTimeActual) {
                                let destinationTimeApp = {} as DestinationTimeAppDto;
                                destinationTimeApp.timeStampAppEnum = el.convertTimeStampAppEnum();
                                destinationTimeApp.startEndClassification = START_CLASSIFICATION;
                                destinationTimeApp.engraveFrameNo = el.typeStamp == STAMPTYPE.EXTRAORDINARY ? el.id -2 : el.id;
                                listDestinationTimeApp.push(destinationTimeApp)
                            }
                            if (el.endTimeActual) {
                                let destinationTimeApp = {} as DestinationTimeAppDto;
                                destinationTimeApp.timeStampAppEnum = el.convertTimeStampAppEnum();
                                destinationTimeApp.startEndClassification = END_CLASSIFICATION;
                                destinationTimeApp.engraveFrameNo = el.typeStamp == STAMPTYPE.EXTRAORDINARY ? el.id -2 : el.id;
                                listDestinationTimeApp.push(destinationTimeApp)
                            }
                        }   
                    })
                    
                } else {
                    _.forEach(items, (el: GridItem) => {
                        if (!ko.toJS(el.flagObservable)) {
                            if (ko.toJS(el.startTimeRequest) || ko.toJS(el.endTimeRequest)) {
                                let timeStampAppOtherDto = {} as TimeStampAppOtherDto;
                                let tz = {} as TimeZone;
                                let destinationTimeZoneAppDto = {} as DestinationTimeZoneAppDto;
                                destinationTimeZoneAppDto.timeZoneStampClassification = el.convertTimeZoneStampClassification();
                                destinationTimeZoneAppDto.engraveFrameNo = el.id;
                                timeStampAppOtherDto.destinationTimeZoneApp = destinationTimeZoneAppDto;
                                timeStampAppOtherDto.timeZone = tz;
                                if (ko.toJS(el.startTimeRequest)) {
                                    tz.startTime = ko.toJS(el.startTimeRequest);
                                    
                                }
                                if (ko.toJS(el.endTimeRequest)) {
                                    tz.endTime = ko.toJS(el.endTimeRequest);                             
                                }
                                listTimeStampAppOther.push(timeStampAppOtherDto);                               
                            }
                        } else {
                            let destinationTimeZoneAppDto = {} as DestinationTimeZoneAppDto;
                            destinationTimeZoneAppDto.timeZoneStampClassification = el.convertTimeZoneStampClassification();
                            destinationTimeZoneAppDto.engraveFrameNo = el.id;
                            listDestinationTimeZoneApp.push(destinationTimeZoneAppDto);
                        }
                    });
                }
            });
            appStamp.listTimeStampApp = listTimeStampApp;
            appStamp.listDestinationTimeApp = listDestinationTimeApp;
            appStamp.listTimeStampAppOther = listTimeStampAppOther;
            appStamp.listDestinationTimeZoneApp = listDestinationTimeZoneApp;
            return appStamp;
        }
        

    }
    class Comment{
        public content: string;
        public isBold: boolean;
        public color: string;
        constructor( content: string, isBold: boolean, color: string) {
            this.content = content;
            this.isBold = isBold;
            this.color = color;
        }
        
    }
    const API = {
            start: "at/request/application/stamp/startStampApp",
            checkRegister: "at/request/application/stamp/checkBeforeRegister",
            register: "at/request/application/stamp/register",
            getDetail: "at/request/application/stamp/detailAppStamp",
            update: "at/request/application/stamp/updateNew",
            checkUpdate: "at/request/application/stamp/checkBeforeUpdate"
            
        }
    const START_CLASSIFICATION = 0;
    const END_CLASSIFICATION = 1;
}