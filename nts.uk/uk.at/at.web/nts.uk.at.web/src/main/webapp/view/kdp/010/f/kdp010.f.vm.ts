module nts.uk.at.view.kdp010.f {
    import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;
	import dialog = nts.uk.ui.dialog;
	import windows = nts.uk.ui.windows;
	import getText = nts.uk.resource.getText;
	
	module viewmodel {
		const paths: any = {
	        saveStampApp: "at/record/stamp/application/saveStampApp",
	        getStampApp: "at/record/stamp/application/getStampApp",
	        getStampFunc: "at/record/stamp/application/getStampFunc",
	        saveStampFunc: "at/record/stamp/application/saveStampFunc",
	        deleteStampFunc: "at/record/stamp/application/delete",
	        getAttendNameByIds:"at/record/attendanceitem/daily/getattendnamebyids",
	        getOptItemByAtr: "at/record/attendanceitem/daily/getlistattendcomparison",
			getNoticeSetAndAupUseArt: "at/record/stamp/application/getNoticeSetAndAupUseArt",
			saveNoticeSetAndAupUseArt: "at/record/stamp/application/saveNoticeSetAndAupUseArt"
	    }
	    export class ScreenModel {
			
	        // E4_1, E4_2, E4_6, E4_7
	        optionImprint: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 0, name: getText("KDP010_59") },
	            { id: 1, name: getText("KDP010_60") }
	        ]);
	        selectedImprint: KnockoutObservable<number> = ko.observable(0);
	        messageValueFirst: KnockoutObservable<string> = ko.observable(getText("KDP010_130"));
	        firstColors: KnockoutObservable<string> = ko.observable('#000000');
	
	        // E5_1, E5_2, E5_6, E5_7
	        optionHoliday: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 0, name: getText("KDP010_59") },
	            { id: 1, name: getText("KDP010_60") }
	        ]);
	        selectedHoliday: KnockoutObservable<number> = ko.observable(0);
	        messageValueSecond: KnockoutObservable<string> = ko.observable(getText("KDP010_131"));
	        secondColors: KnockoutObservable<string> = ko.observable('#000000');
	
	        // E6_1, E6_2, E6_5, E6_6
	        optionOvertime: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 0, name: getText("KDP010_59") },
	            { id: 1, name: getText("KDP010_60") }
	        ]);
	        selectedOvertime: KnockoutObservable<number> = ko.observable(0);
	        messageValueThird: KnockoutObservable<string> = ko.observable(getText("KDP010_132"));
	        thirdColors: KnockoutObservable<string> = ko.observable('#000000');
	
	        workTypeList: KnockoutObservableArray<any> = ko.observableArray([]);
	        workTypeNames: KnockoutObservable<string> = ko.observable();
	        currentItem: KnockoutObservable<DailyItemDto> = ko.observable(new DailyItemDto({}));
	
	        // E31_2
	        optionImp: KnockoutObservableArray<any> = ko.observableArray([
	            { id: 0, name: getText("KDP010_85") },
	            { id: 1, name: getText("KDP010_84") }
	        ]);
	        selectedImp: KnockoutObservable<number> = ko.observable(0);
	        
	        dataKdl: KnockoutObservableArray<any> = ko.observableArray([]);

			//E11 - E20
			supportUseArtOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: getText("KDP010_317") },
                { id: 1, name: getText("KDP010_316") }
            ]);
			supportUseArt: KnockoutObservable<number> = ko.observable(1);

			displayAtrOption: KnockoutObservableArray<any> = ko.observableArray([
                { id: 0, name: getText("KDP010_321") },
                { id: 1, name: getText("KDP010_320") }
            ]);	

			noticeSetAndAupUseArt: NoticeSetAndAupUseArt = new NoticeSetAndAupUseArt();
			
	        constructor() {
	            let self = this;
	
	            self.messageValueFirst.subscribe(function(codeChanged: string) {
	                self.messageValueFirst($.trim(self.messageValueFirst()));
	            });
	            self.messageValueSecond.subscribe(function(codeChanged: string) {
	                self.messageValueSecond($.trim(self.messageValueSecond()));
	            });
	            self.messageValueThird.subscribe(function(codeChanged: string) {
	                self.messageValueThird($.trim(self.messageValueThird()));
	            });
	        }
	
	        /**
	         * Start page.
	         */
	        start(): JQueryPromise<any> {
	            let self = this, dfd = $.Deferred();
	            block.clear();
	            self.getStampApp();
	            $.when(self.getWorkTypeList(), self.getNoticeSetAndAupUseArt()).done(function() {
	                $(document).ready(function() {
	                    $('#imprint-leakage-radio-e').focus();
	                });
	                dfd.resolve();
	            });
	            return dfd.promise();
	        }

			getNoticeSetAndAupUseArt(): JQueryPromise<any>{
				let self = this, dfd = $.Deferred();
				block.grayout();
				ajax("at", paths.getNoticeSetAndAupUseArt).done(function(data:any) {
					self.noticeSetAndAupUseArt.update(data);
	            }).fail(function(res: any) {
	                dialog.error(res.message);
	            }).always(() => {
	                block.clear();
					dfd.resolve();
	            });
				return dfd.promise();
			}
			
			saveNoticeSetAndAupUseArt(): JQueryPromise<any>{
				let self = this, dfd = $.Deferred();
				ajax("at", paths.saveNoticeSetAndAupUseArt, ko.toJS(self.noticeSetAndAupUseArt)).done(function(data:any) {
	            }).fail(function(res: any) {
	                dialog.error(res.message);
	            }).always(() => {
					dfd.resolve();
	            });
				return dfd.promise();
			}
	
	        save() {
	            let self = this, dfd = $.Deferred();
	
	            if (nts.uk.ui.errors.hasError()) {
	                return;
	            }
	            $.when(self.registrationApp(), self.deleteStampFunc(), self.saveNoticeSetAndAupUseArt()).done(function() {
	                if (nts.uk.ui.errors.hasError()) {
	                    block.clear();
	                    return;
	                }
	                self.registrationFunc();
	                dfd.resolve();
	            });
	            return dfd.promise();
	
	        }
	
	        /**
	         * Registration function.
	         */
	        registrationApp() {
	            let self = this;
	            $('#message-text-2').ntsEditor('validate');
	            $('.text-color-1').find('#message-1').find('#message-text-1').ntsEditor('validate');
	            $('#message-text-3').ntsEditor('validate');
	            if (nts.uk.ui.errors.hasError()) {
	                return;
	            }
	            block.invisible();
	            // Data from Screen 
	            let StampRecordDisCommand = {
	                lstStampRecord: [{
	                    useArt: self.selectedImprint(),
	                    checkErrorType: 0,
	                    promptingMssage: {
	                        messageContent: self.messageValueFirst(),
	                        messageColor: self.firstColors()
	                    }
	                }, {
	                        useArt: self.selectedHoliday(),
	                        checkErrorType: 1,
	                        promptingMssage: {
	                            messageContent: self.messageValueSecond(),
	                            messageColor: self.secondColors()
	                        }
	                    }, {
	                        useArt: self.selectedOvertime(),
	                        checkErrorType: 2,
	                        promptingMssage: {
	                            messageContent: self.messageValueThird(),
	                            messageColor: self.thirdColors()
	                        }
	                    }]
	            };
	            ajax("at", paths.saveStampApp, StampRecordDisCommand).done(function() {
	
	            }).fail(function(res: any) {
	                dialog.error(res.message);
	            }).always(() => {
	                block.clear();
	            });
	        }
	
	        registrationFunc() {
	            let self = this;
	            let lstDisplayItem: StampAttenDisplayCommand[] = [];
	            let lstDisplay = new Array<>();
	             _.forEach(self.currentItem().dailyList(), function(item) {
	                    lstDisplay = new StampAttenDisplayCommand({
	                        displayItemId : item
	                    });
	                 lstDisplayItem.push(lstDisplay);
	                });
	            
	            // Data from Screen 
	            let StampRecordDisCommand = {
	                usrAtr: self.selectedImp(),
	                lstDisplayItemId: lstDisplayItem
	            };
	            ajax("at", paths.saveStampFunc, StampRecordDisCommand).done(function() {
	                dialog.info({ messageId: "Msg_15" });
	            }).fail(function(res) {
	                dialog.error(res.message);
	            }).always(() => {
	                block.clear();
	            });
	        }
	
	        getWorkTypeList(): JQueryPromise<any>{
	            let self = this, dfd = $.Deferred();
	            ajax("at", paths.getOptItemByAtr).done(function(res: any) {
	                self.workTypeList.removeAll();
	                _.forEach(res, function(item) {
	                    self.workTypeList.push({
	                        attendanceItemId: item.attendanceItemId,
	                        attendanceName: item.attendanceItemName
	                    });
	                });
	                self.getStampFunc();
	                dfd.resolve();
	            }).fail(function(error: any) {
	                alert(error.message);
	                dfd.reject(error);
	            });
	            return dfd.promise();
	        }
	
	        getStampApp(): JQueryPromise<any> {
	            let self = this;
	            let dfd = $.Deferred();
	            ajax("at", paths.getStampApp).done(function(totalStamp: any) {
	                if (totalStamp.length > 0) {
	                    totalStamp = _.sortBy(totalStamp, item => item.checkErrorType)
	                    self.selectedImprint(totalStamp[0].useArt);
	                    self.messageValueFirst(totalStamp[0].messageContent);
	                    self.firstColors(totalStamp[0].messageColor);
	                    self.selectedHoliday(totalStamp[1].useArt);
	                    self.messageValueSecond(totalStamp[1].messageContent);
	                    self.secondColors(totalStamp[1].messageColor);
	                    self.selectedOvertime(totalStamp[2].useArt);
	                    self.messageValueThird(totalStamp[2].messageContent);
	                    self.thirdColors(totalStamp[2].messageColor);
	                }
	                dfd.resolve();
	            }).fail(function(error: any) {
	                alert(error.message);
	                dfd.reject(error);
	            });
	            return dfd.promise();
	        }
	
	        getStampFunc(): JQueryPromise<any> {
	            let self = this;
	            let dfd = $.Deferred();
	            ajax("at", paths.getStampFunc).done(function(totalStamp: any) {
	                if (totalStamp) {
	                    self.selectedImp(totalStamp.usrAtr);
	                    var workTypeCodes = _.map(totalStamp.lstDisplayItemId, function(item: any) { return item.displayItemId; });
	                    self.generateNameCorrespondingToAttendanceItem(workTypeCodes);
	                    var names = self.getNames(self.workTypeList(), totalStamp.workTypeList);
	                    if (names) {
	                        self.workTypeNames(names);
	                    } else {
	                        self.workTypeNames("");
	                    }
	
	                }
	                dfd.resolve();
	            }).fail(function(error: any) {
	                alert(error.message);
	                dfd.reject(error);
	            });
	            return dfd.promise();
	        }
	
	        deleteStampFunc(): JQueryPromise<any> {
	            let self = this;
	            let dfd = $.Deferred();
	            let data = {
	                displayItemId: 1
	            }
	            ajax("at", paths.deleteStampFunc, data).done(function() {
	                dfd.resolve();
	            }).fail(function(error: any) {
	                alert(error.message);
	                dfd.reject(error);
	            });
	            return dfd.promise();
	        }
	
	        getNames(data: Array<IDailyItemModal>, workTypeCodesSelected: Array<string>) {
	            var name: any = [];
	            var self = this;
	            if (workTypeCodesSelected && workTypeCodesSelected.length > 0) {
	                _.forEach(data, function(item: IDailyItemModal) {
	                    _.forEach(workTypeCodesSelected, function(items: any) {
	                        if (_.includes(items.attendanceItemId, item.attendanceItemId)) {
	                            name.push(item.attendanceName);
	                        }
	                    });
	                });
	            }
	            return name.join(" + ");
	        }
	
	        /**
	         * Open Dialog KDL021
	         */
	        openKDL021Dialog(datas : any) {
	            let self = this;
	            nts.uk.ui.errors.clearAll();
	            block.invisible();
	            var workTypeCodes = _.map(self.workTypeList(), function(item: IDailyItemModal) { return item.attendanceItemId });
	            windows.setShared('Multiple', true);
	            windows.setShared('DailyMode', 0);
	            windows.setShared('AllAttendanceObj', workTypeCodes);
	            
	            if(datas ==1) {
	                let data021 = self.dataKdl.map(i=>Number(i))
	                windows.setShared('SelectedAttendanceId', data021, true);
	            }
	            else{
	                windows.setShared('SelectedAttendanceId', self.currentItem().dailyList(), true);
	            }    
	
	            windows.sub.modal('/view/kdl/021/a/index.xhtml').onClosed(function(): any {
	                block.clear();
	                self.dataKdl = windows.getShared('selectedChildAttendace');
	                self.generateNameCorrespondingToAttendanceItem(self.dataKdl);
	            });
	            block.clear();
	        }
	
	        /**
	       * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
	       * @param List<itemAttendanceId>
	       */
	        private generateNameCorrespondingToAttendanceItem(listCode: Array<any>): JQueryPromise<any> {
	            let self = this,
	                dfd = $.Deferred();
	            if (self.dataKdl && self.dataKdl.length > 5) {
	                    dialog.error({ messageId: "Msg_1631" }).then(() => {
	                        self.openKDL021Dialog(1);
	                    });
	                    return;
	                }
                if (listCode && listCode.length > 0) {
                    ajax("at", paths.getAttendNameByIds, listCode).done((dItems: any) => {
                        let attendanceName = _.get(dItems[dItems.length - 1], 'attendanceItemName', '');
                        let name = _.map(listCode, code => {
                            return _.get(_.find(dItems, dI => dI.attendanceItemId == code), 'attendanceItemName', getText("KDP010_347"));
                        });
                        self.workTypeNames(name.join(" 、 "));
                        self.currentItem().dailyList(listCode);
                        dfd.resolve(attendanceName);
                    }).always(() => {
                        dfd.resolve('');
                    });
                } else {
                    dfd.resolve('');
                }
	            return dfd.promise();
	        }
	
	    }
	    export class DailyItemDto {
	        useAtr: KnockoutObservable<number>;
	        dailyList: KnockoutObservableArray<DailyItemSetDto>;
	        constructor(param: IDailyItemDto) {
	            this.useAtr = ko.observable(param.useAtr || 0);
	            this.dailyList = ko.observableArray(param.dailyList || null);
	        }
	    }
	
	    export interface IDailyItemDto {
	        useAtr?: number;
	        dailyList?: Array<IDailyItemSetDto>;
	    }
	
	    export class DailyItemModal {
	        attendanceItemId: string;
	        attendanceName: string;
	        constructor(param: IDailyItemModal) {
	            this.attendanceItemId = param.attendanceItemId;
	            this.attendanceName = param.attendanceName;
	        }
	    }
	
	    export interface IDailyItemModal {
	        attendanceItemId: string;
	        attendanceName: string;
	    }
	
	    export class DailyItemSetDto {
	        attendanceItemId: string;
	        constructor(param: IDailyItemSetDto) {
	            this.attendanceItemId = param.attendanceItemId;
	        }
	    }
	
	    export interface IDailyItemSetDto {
	        attendanceItemId?: string;
	    }
	    
		export class StampAttenDisplayCommand {
	        displayItemId: number;
	        constructor(param: IStampAttenDisplayCommand) {
	            this.displayItemId = param.displayItemId;
	        }
	    }

		export class ColorSetting {
			textColor: KnockoutObservable<string>;
			backGroundColor: KnockoutObservable<string>;
			constructor(param?: any) {
				let self = this;
				self.textColor = ko.observable(param?param.textColor:'#7F7F7F');
				self.backGroundColor = ko.observable(param?param.backGroundColor:'#E2F0D9');
			}
			update(param: any) {
				let self = this;
				self.textColor = ko.observable(param.textColor);
				self.backGroundColor = ko.observable(param.backGroundColor);
			}
		}
	
	    export interface IStampAttenDisplayCommand {
	        displayItemId?: number;
	    }

		export class NoticeSetAndAupUseArt {
			noticeSet: NoticeSet = new NoticeSet();
			supportUseArt: KnockoutObservable<number> = ko.observable(0);
			constructor() {}
			update(param: any) {
				let self = this;
				self.supportUseArt(param.supportUseArt||0);
				if(param.noticeSet){
					self.noticeSet.update(param.noticeSet);	
				}
			}
		}
		export class NoticeSet {
			companyColor: ColorSetting = new ColorSetting();
			companyTitle: KnockoutObservable<string> = ko.observable(getText("KDP010_333").substr(0, 5));
			personalColor: ColorSetting = new ColorSetting({textColor: '#000000', backGroundColor: '#E2F0D9'});
			workplaceColor: ColorSetting = new ColorSetting();
			workplaceTitle: KnockoutObservable<string> = ko.observable(getText("KDP010_334").substr(0, 5));
			displayAtr: KnockoutObservable<number> = ko.observable(0);
			constructor() {}
			update(param: any) {
				let self = this;
				self.companyColor.update(param.companyColor);
				self.companyTitle(param.companyTitle);
				self.personalColor.update(param.personalColor);
				self.workplaceColor.update(param.workplaceColor);
				self.workplaceTitle(param.workplaceTitle);
				self.displayAtr(param.displayAtr);
			}
		}
	}
	__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
