module nts.uk.at.view.ktg004.a.viewmodel {
    import block = nts.uk.ui.block;
	import ajax = nts.uk.request.ajax;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
	import windows = nts.uk.ui.windows;

    export var STORAGE_KEY_TRANSFER_DATA = "nts.uk.request.STORAGE_KEY_TRANSFER_DATA";

	const KTG004_API = {
		GET_DATA: 'screen/at/ktg004/getData'
	};
    export class ScreenModel {
        name = ko.observable(''); 
		selectedSwitch = ko.observable(1);
		itemsSetting: KnockoutObservableArray<any> = ko.observableArray([]);
		attendanceInfor = new AttendanceInforDto();
		detailedWorkStatusSettings = ko.observable(false);
		        
        constructor() {
            var self = this;
        }

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
				self.name(data.name);
				self.detailedWorkStatusSettings(data.detailedWorkStatusSettings);
				self.itemsSetting(data.itemsSetting);
				self.attendanceInfor.update(data.attendanceInfor);
				
				let show = _.filter(data.itemsSetting, { 'displayType': true });
				if(show && show.length > 14){
					$("#scrollTable").addClass("scroll");
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
			let self = this;
			window.top.location = window.location.origin + '/nts.uk.at.web/view/kdw/003/a/index.xhtml';
		}
    }

	class AttendanceInforDto {
		flexCarryOverTime = ko.observable(0);
		flexTime = ko.observable(0);
		holidayTime = ko.observable(0);
		overTime = ko.observable(0);
		nigthTime = ko.observable(0);
		early = ko.observable(0);
		late = ko.observable(0);
		dailyErrors = ko.observable(false);
		
		constructor(param?: any){
			if(param){
				let self = this;
				self.flexCarryOverTime(param.flexCarryOverTime || 0);
				self.flexTime(param.flexTime || 0);
				self.holidayTime(param.holidayTime || 0);
				self.overTime(param.overTime || 0);
				self.nigthTime(param.nigthTime || 0);
				self.early(param.early || 0);
				self.late(param.late || 0);
				self.dailyErrors(param.dailyErrors);	
			}
				
		}
		update(param: any){
			if(param){
				let self = this;
				self.flexCarryOverTime(param.flexCarryOverTime || 0);
				self.flexTime(param.flexTime || 0);
				self.holidayTime(param.holidayTime || 0);
				self.overTime(param.overTime || 0);
				self.nigthTime(param.nigthTime || 0);
				self.early(param.early || 0);
				self.late(param.late || 0);
				self.dailyErrors(param.dailyErrors);	
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
