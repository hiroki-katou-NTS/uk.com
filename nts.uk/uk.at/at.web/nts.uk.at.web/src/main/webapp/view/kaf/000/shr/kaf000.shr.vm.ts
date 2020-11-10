module nts.uk.at.view.kaf000.shr.viewmodel {
    export class Application {
        appID: KnockoutObservable<string>;
        prePostAtr: KnockoutObservable<number>;
        employeeIDLst: KnockoutObservableArray<string>;
        appType: number;
        inputDate: string;
        appDate: KnockoutObservable<string>;
        opAppReason: KnockoutObservable<string>;
        opAppStandardReasonCD: KnockoutObservable<number>;
        opReversionReason: KnockoutObservable<string>;
        opAppStartDate: KnockoutObservable<string>;
        opAppEndDate: KnockoutObservable<string>;
        opStampRequestMode: KnockoutObservable<number>
        constructor(appType: number) {
            this.appID = ko.observable(null);
            this.prePostAtr = ko.observable(null);
            this.employeeIDLst = ko.observableArray([]);
            this.appType = appType;
            this.appDate = ko.observable("");
            this.opAppReason = ko.observable(null);
            this.opAppStandardReasonCD = ko.observable(null);
            this.opReversionReason = ko.observable(null);
            this.opAppStartDate = ko.observable("");
            this.opAppEndDate = ko.observable("");
            this.opStampRequestMode = ko.observable(null);
        }        
    }
    
    export class ApplicationSub {
        appID: string;
        appType: number;
        constructor(appID: string, appType: number) {
            this.appID = appID;
            this.appType = appType;
        }      
    }

	export class ActualContentDisplayDto {
		/** 年月日 */
		date: string;
		/** 実績詳細 */
		opAchievementDetail: AchievementDetailDto;
	}    
	
	export interface AchievementDetailDto {
		/** 1勤務種類コード */
		workTypeCD: string;
		/** 3就業時間帯コード */
		workTimeCD: string;
		/** 休憩時間帯 */
		breakTimeSheets: Array<any>;
		/** 勤怠時間内容 */
		timeContentOutput: any;
		/** 実績スケ区分 */
		trackRecordAtr: number;
		/** 打刻実績 */
		stampRecordOutput: any;
		/** 短時間勤務時間帯 */
		shortWorkTimeLst: Array<any>;
		/** 遅刻早退実績 */
		achievementEarly: any;
		/** 10退勤時刻2 */
		opDepartureTime2: number;
		/** 2勤務種類名称 */
		opWorkTypeName: string;
		/** 4就業時間帯名称 */
		opWorkTimeName: string;
		/** 5出勤時刻 */
		opWorkTime: number;
		/** 6退勤時刻 */
		opLeaveTime: number;
		/** 8実績状態 */
		opAchievementStatus: number;
		/** 9出勤時刻2 */
		opWorkTime2: number;
		/** 残業深夜時間 */
		opOvertimeMidnightTime: number;
		/** 法内休出深夜時間 */
		opInlawHolidayMidnightTime: number;
		/** 法外休出深夜時間 */
		opOutlawHolidayMidnightTime: number;
		/** 祝日休出深夜時間 */
		opPublicHolidayMidnightTime: number;
		/** 7勤怠時間 */
		opOvertimeLeaveTimeLst: Array<any>;	
	}

	export interface PrintContentOfEachAppDto {
		/**
		 * 休暇申請の印刷内容
		 */
		
		/**
		 * 勤務変更申請の印刷内容
		 */
		opPrintContentOfWorkChange: any;
		
		/**
		 * 時間休暇申請の印刷内容
		 */
		
		/**
		 * 打刻申請の印刷内容
		 */
		opAppStampOutput: any;
		
		/**
		 * 遅刻早退取消申請の印刷内容
		 */
		opArrivedLateLeaveEarlyInfo: any;
	
		/**
		 * 直行直帰申請の印刷内容
		 */
		opInforGoBackCommonDirectOutput: any;
		
		opBusinessTripInfoOutput: any;

        opOptionalItemOutput: any;
	}
	
	export interface AppInitParam {
		appType: number,
        employeeIds : Array<string>;
        baseDate: string;
		isAgentMode?: boolean;
	}
    
    export module model {
        // loại người đăng nhập
        // người đại diện tương đương người approver, người confirm có ưu tiên cao hơn
        export enum UserType { 
            APPLICANT_APPROVER = 0, // 申請本人&承認者
            APPROVER = 1, // 承認者
            APPLICANT = 2, // 申請本人
            OTHER = 3, // その他        
        }; 
        
        // trạng thái của phase chứa user
        export enum ApprovalAtr { 
            UNAPPROVED = 0, // 未承認   
            APPROVED = 1, // 承認済
            DENIAL = 2, // 否認
            REMAND = 3, // 差し戻し
        };
        
        export enum Status {
            NOTREFLECTED = 0, // 未反映
            WAITREFLECTION = 1, //反映待ち
            REFLECTED = 2, //反映済
            CANCELED = 3, //取消済
            REMAND = 4,//差し戻し
            DENIAL = 5, //否認
            PASTAPP = 99, //過去申請 
        };
        
        export enum AppType {
            OVER_TIME_APPLICATION = 0, // 残業申請
            ABSENCE_APPLICATION = 1, // 休暇申請
            WORK_CHANGE_APPLICATION = 2, // 勤務変更申請
            BUSINESS_TRIP_APPLICATION = 3, // 出張申請
            GO_RETURN_DIRECTLY_APPLICATION = 4, // 直行直帰申請
            LEAVE_TIME_APPLICATION = 6, // 休出時間申請
            STAMP_APPLICATION = 7, // 打刻申請
            ANNUAL_HOLIDAY_APPLICATION = 8, // 時間休暇申請
            EARLY_LEAVE_CANCEL_APPLICATION = 9, // 遅刻早退取消申請
            COMPLEMENT_LEAVE_APPLICATION = 10, // 振休振出申請
            OPTIONAL_ITEM_APPLICATION = 15, // 任意項目申請    
        }
    }
    
    export class CommonProcess {
        public static initCommonSetting() {
            return {
                appDetailScreenInfo: {
                    application: {},
                    approvalLst: [],
                    authorComment: "",
                    user: 0, 
                    reflectPlanState: 0,
                    outputMode: 0,
                    authorizableFlags: false,
                    approvalATR: 0,
                    alternateExpiration: false    
                },
                appDispInfoNoDateOutput: {
                    employeeInfoLst: [],
                    requestSetting: {},
                    appReasonLst: []    
                },
                appDispInfoWithDateOutput: {
                    approvalFunctionSet: {},
                    employmentSet: {},
                    workTimeLst: [],
                    listApprovalPhaseState: [],
                    errorFlag: 0,
                    prePostAtr: 1,
                    baseDate: "",
                    achievementOutputLst: [],
                    appDetailContentLst: [],
                    empHistImport: {}
                }  
            }    
        }    
        
        public static initDeadlineMsg(value: any, vm: any) {
            vm.message(value.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].memo);
            if(_.isEmpty(vm.message())) {
                vm.displayMsg(false);         
            } else {
                vm.displayMsg(true);
            }
            
            let advanceAppAcceptanceLimit = value.appDispInfoNoDateOutput.advanceAppAcceptanceLimit == 1;
			let receptionRestrictionSetting = _.find(value.appDispInfoNoDateOutput.applicationSetting.receptionRestrictionSetting, (o: any) => o.appType == vm.appType());
            let allowFutureDay = receptionRestrictionSetting.afterhandRestriction.allowFutureDay;
            let appDeadlineUseCategory = value.appDispInfoWithDateOutput.appDeadlineUseCategory == 1;
            // 注意：申請表示情報(基準日関係なし)．事前申請の受付制限が利用しない && 申請表示情報．申請設定（基準日関係なし）．申請承認設定．申請設定．受付制限設定．事後の受付制限．未来日許可しないがfalse && 申請表示情報(基準日関係あり)．申請締め切り日利用区分が利用しない
            if(!advanceAppAcceptanceLimit && !allowFutureDay && !appDeadlineUseCategory) {
                // 締め切りエリア全体に非表示
                vm.displayDeadline(false);        
            } else {
                vm.displayDeadline(true);     
            }
            // {1}事前受付日
            let prePart = "";
            if(advanceAppAcceptanceLimit) {
                // ・申請表示情報(基準日関係なし)．事前受付時分がNull
                if(_.isNull(value.appDispInfoNoDateOutput.opAdvanceReceptionHours)) {
                    prePart = vm.$i18n('KAF000_38', [value.appDispInfoNoDateOutput.opAdvanceReceptionDate]);        
                } 
                // ・申請表示情報(基準日関係なし)．事前受付時分がNullじゃない
                else {
                    prePart = vm.$i18n('KAF000_41', [value.appDispInfoNoDateOutput.opAdvanceReceptionHours]);  
                }             
            }
            // {2}事後受付日
            let postPart = "";
			if(allowFutureDay) {
				postPart = vm.$i18n('KAF000_39', [moment(vm.$date.today()).format("YYYY/MM/DD")]);	
			}
            // {3}締め切り期限日
            let deadlinePart = "";
			if(appDeadlineUseCategory) {
				deadlinePart = vm.$i18n('KAF000_40', [value.appDispInfoWithDateOutput.opAppDeadline]);	
			}
            vm.deadline(prePart + postPart + deadlinePart);    
        }
        
        public static checkUsage(
            mode: boolean, // true: new, false: detail 
            element: string, // element select to set error
            vm: any
        ) {
            vm.$errors("clear", [element]);
            let appDispInfoStartupOutput = vm.appDispInfoStartupOutput(),
                useDivision = appDispInfoStartupOutput.appDispInfoWithDateOutput.approvalFunctionSet.appUseSetLst[0].useDivision,
                recordDate = appDispInfoStartupOutput.appDispInfoNoDateOutput.applicationSetting.recordDate,
                empHistImport = appDispInfoStartupOutput.appDispInfoWithDateOutput.empHistImport,
                opErrorFlag = appDispInfoStartupOutput.appDispInfoWithDateOutput.opErrorFlag,
                msgID = "";
            if(mode && useDivision == 0) {
                vm.$errors(element, "Msg_323");
                vm.$dialog.error({ messageId: "Msg_323" }).then(() => {
                    if(recordDate == 0) {
                        vm.$jump("com", "/view/ccg/008/a/index.xhtml");    
                    }
                });   
				if(recordDate == 0) {
					return false;
				}
                return true;
            }
            
            if(_.isNull(opErrorFlag)) {
                return true;    
            }
            switch(opErrorFlag){
                case 1:
                    msgID = "Msg_324";
                    break;
                case 2: 
                    msgID = "Msg_238";
                    break;
                case 3:
                    msgID = "Msg_237";
                    break;
                default: 
                    break;
            }  
            if(_.isEmpty(msgID)) { 
                return true;
            }
            vm.$errors(element, msgID);
            vm.$dialog.error({ messageId: msgID }).then(() => {
                if(recordDate == 0) {
                    vm.$jump("com", "/view/ccg/008/a/index.xhtml");    
                }    
            });
			if(recordDate == 0) {
				return false;
			}
			return true;
        }

		public static showMailResult(mailResult: Array<any>, vm: any) {
			return new Promise((resolve: any) => {
				if(_.isEmpty(mailResult)) {
					resolve(true);
				}
				let msg = mailResult[0].value,
					type = mailResult[0].type;
				if(type=='info') {
					return vm.$dialog.info(msg).then(() => {
		           		return CommonProcess.showMailResult(_.slice(mailResult, 1), vm);	
		        	});	
				} else {
					return vm.$dialog.error(msg).then(() => {
		            	return CommonProcess.showMailResult(_.slice(mailResult, 1), vm);
		        	});	
				}
	        });
		}
		
		public static showConfirmResult(mailResult: Array<any>, vm: any) {
			return new Promise((resolve: any) => {
				if(_.isEmpty(mailResult)) {
					resolve(true);
				}
				let msg = mailResult[0].value,
					type = mailResult[0].type;
				return vm.$dialog.confirm(msg).then((result: 'no' | 'yes' | 'cancel') => {
					if (result === 'yes') {
		            	return CommonProcess.showConfirmResult(_.slice(mailResult, 1), vm);
		            }
					resolve();
	        	});	
	        }).then((data: any) => {
				if(data) {
					alert('yes');
				} else {
					alert('no');
				}		
			});
		}
    }
}