module nts.uk.at.view.ktg004.a.viewmodel {
    import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;
    import getText = nts.uk.resource.getText;
	import windows = nts.uk.ui.windows;

    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

	const KTG004_API = {
		GET_DATA: 'screen/at/ktg004/getData'
	};
    export class ScreenModel {
        name = ko.observable(''); 
		selectedSwitch = ko.observable(1);
		itemsSetting: KnockoutObservableArray<any> = ko.observableArray([]);
		attendanceInfor = new AttendanceInfor();
		remainingNumberInfor = new RemainingNumberInfor();
		detailedWorkStatusSettings = ko.observable(false);
		specialHolidaysRemainings: KnockoutObservableArray<SpecialHolidaysRemainings> = ko.observableArray([]);
		        
        constructor() {}

        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
			var cacheCcg008 = windows.getShared("cache");
            if (!cacheCcg008 || !cacheCcg008.currentOrNextMonth) {
                self.selectedSwitch(1);
            }
            else {
                self.selectedSwitch(cacheCcg008.currentOrNextMonth);
            }
			block.grayout();
            ajax("at", KTG004_API.GET_DATA, {topPageYearMonthEnum: self.selectedSwitch()}).done(function(data: any){
				self.name(data.name || "");
				self.detailedWorkStatusSettings(data.detailedWorkStatusSettings);
				self.itemsSetting(data.itemsSetting);
				self.attendanceInfor.update(data.attendanceInfor);
				self.remainingNumberInfor.update(data.remainingNumberInfor);
				let tg: SpecialHolidaysRemainings[] = [];
				_.forEach(data.remainingNumberInfor.specialHolidaysRemainings, function(c){
					tg.push(new SpecialHolidaysRemainings(c));
				});
				self.specialHolidaysRemainings(tg);
				let show = _.filter(data.itemsSetting, { 'displayType': true });
				if(show && show.length > 14){
					$("#scrollTable").addClass("scroll");
					$(".widget-table td").each(function() {
						this.setAttribute("style", "width:262px");
					});
				}
				if(data.name == null){
					$('#setting').css("top", "-7px");
				}else{
					$('#setting').css("top", "6px");
				}
				$("#contents").css("display", "");
				dfd.resolve();
            }).always(() => {
				block.clear();
			});
            return dfd.promise();
        }
        
		public setting() {
			let self = this;
			nts.uk.ui.windows.sub.modal('at', '/view/ktg/004/b/index.xhtml').onClosed(() => {
				let data = nts.uk.ui.windows.getShared("KTG004B");
				if(data){
					self.startPage();
				}
			});
		}
		
		public openKDW003() {
			window.top.location = window.location.origin + '/nts.uk.at.web/view/kdw/003/a/index.xhtml';
		}
    }

	class AttendanceInfor {
		//フレックス繰越時間
		flexCarryOverTime = ko.observable(getText('KTG004_4', ['00:00']));
		//フレックス時間
		flexTime = ko.observable('00:00');
		//休出時間
		holidayTime = ko.observable('00:00');
		//残業時間
		overTime = ko.observable('00:00');
		//就業時間外深夜時間
		nigthTime = ko.observable('00:00');
		//早退回数/遅刻回数
		lateEarly = ko.observable(getText('KTG004_8', ['0','0']));
		//日別実績のエラー有無
		dailyErrors = ko.observable(false);
		
		constructor(){}
				
		update(param: any){
			if(param){
				let self = this;
				self.flexCarryOverTime(getText('KTG004_4', [self.convertToTime(param.flexCarryOverTime)]));
				self.flexTime(self.convertToTime(param.flexTime));
				self.holidayTime(self.convertToTime(param.holidayTime));
				self.overTime(self.convertToTime(param.overTime));
				self.nigthTime(self.convertToTime(param.nigthTime));
				self.lateEarly(getText('KTG004_8', [param.late, param.early]));
				self.dailyErrors(param.dailyErrors);	
			}
		}
		
		convertToTime(data:any):string{
			if(data == null || data == undefined || data == 0 || data == '0'){
				return '00:00';
			}else{
				return (data < 0 ? '-': '') + moment(Math.floor(data / (data < 0 ? -60: 60)) + ":" + Math.abs(data % 60),'hh:mm').format('H:mm');
			}
		}
	}
	
	class RemainingNumberInfor {
		//年休残数
		numberOfAnnualLeaveRemain = ko.observable(getText('KTG004_15', [0]));
		//積立年休残日数
		numberAccumulatedAnnualLeave = ko.observable(getText('KTG004_15', [0]));
		//代休残数
		numberOfSubstituteHoliday = ko.observable(getText('KTG004_15', [0]));
		//振休残日数
		remainingHolidays = ko.observable(getText('KTG004_15', [0]));
		//子の看護残数
		nursingRemainingNumberOfChildren = ko.observable(getText('KTG004_15', [0]));
		//介護残数
		longTermCareRemainingNumber = ko.observable(getText('KTG004_15', [0]));
		
		constructor(){}
				
		update(param: any){
			if(param){
				let self = this;
				self.numberOfAnnualLeaveRemain(getText('KTG004_15', [param.numberOfAnnualLeaveRemain.day]));
				self.numberAccumulatedAnnualLeave(getText('KTG004_15', [param.numberAccumulatedAnnualLeave]));
				self.numberOfSubstituteHoliday(getText('KTG004_15', [param.numberOfSubstituteHoliday.day]));
				self.remainingHolidays(getText('KTG004_15', [param.remainingHolidays]));
				self.nursingRemainingNumberOfChildren(getText('KTG004_15', [param.nursingRemainingNumberOfChildren.day]));
				self.longTermCareRemainingNumber(getText('KTG004_15', [param.longTermCareRemainingNumber.day]));
			}
		}
	}
	class SpecialHolidaysRemainings {
		//特別休暇コード
		code: number;
		//特別休暇名称
		name: string;
		//特休残数
		specialResidualNumber: string;
		constructor(param: any){
			if(param){
				let self = this;
				self.code = param.code;
				self.name = param. name;
				self.specialResidualNumber = getText('KTG004_15', [param.specialResidualNumber.day]);
			}
		}
				
	}
	
}
module nts.uk.at.view.ktg004.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
