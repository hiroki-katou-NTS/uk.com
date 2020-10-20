module nts.uk.at.view.kdp002.b {
	export module viewmodel {
		export class ScreenModel {

			// B2_2
			employeeCodeName: KnockoutObservable<string> = ko.observable("基本給");
			// B3_2
			dayName: KnockoutObservable<string> = ko.observable("基本給");
			// B3_3
			timeName: KnockoutObservable<string> = ko.observable("基本給");
			// G4_2
			checkHandName: KnockoutObservable<string> = ko.observable("基本給");
			// G5_2
			numberName: KnockoutObservable<string> = ko.observable("基本給");
			// G6_2
			laceName: KnockoutObservable<string> = ko.observable("基本給");

			items: KnockoutObservableArray<model.ItemModels> = ko.observableArray([]);
			columns2: KnockoutObservableArray<any>;
			currentCode: KnockoutObservable<any> = ko.observable();
			currentCodeList: KnockoutObservableArray<any>;

			listStampRecord: KnockoutObservableArray<any> = ko.observableArray([]);
			currentDate: KnockoutObservable<string> = ko.observable(moment(new Date()).add(-3, 'days').format("YYYY/MM/DD") + " ～ " + moment(new Date()).format("YYYY/MM/DD"));
			currentStampData: KnockoutObservable<any> = ko.observable();
			resultDisplayTime: KnockoutObservable<number> = ko.observable(0);
			disableResultDisplayTime: KnockoutObservable<boolean> = ko.observable(true);
			interval: KnockoutObservable<number> = ko.observable(0);
			infoEmpFromScreenA: any;
			constructor() {
				let self = this;
				self.resultDisplayTime(nts.uk.ui.windows.getShared("resultDisplayTime"));
				self.infoEmpFromScreenA = nts.uk.ui.windows.getShared("infoEmpToScreenB");

				self.disableResultDisplayTime(self.resultDisplayTime() > 0 ? true : false);

				self.columns2 = ko.observableArray([
					{ headerText: "id", key: 'id', width: 100, hidden: true },
					{ headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_45") + "</div>", key: 'stampDate', width: 130 },
					{ headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_46") + "</div>", key: 'stampHowAndTime', width: 90 },
					{ headerText: "<div style='text-align: center;'>" + nts.uk.resource.getText("KDP002_47") + "</div>", key: 'timeStampType', width: 180 }
				]);
				self.currentCode.subscribe(newValue => {
					if (newValue != null && newValue != "") {
						self.getDataById(newValue);
					}
				});
			}

            /**
             * start page  
             */
			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();
				let dfdGetAllStampingResult = self.getAllStampingResult();
				let dfdGetEmpInfo = self.getEmpInfo();
				$.when(dfdGetAllStampingResult, dfdGetEmpInfo).done(function(dfdGetAllStampingResultData, dfdGetEmpInfoData) {
					if (self.resultDisplayTime() > 0) {
						setInterval(self.closeDialog, self.resultDisplayTime() * 1000);
						setInterval(() => {
							self.resultDisplayTime(self.resultDisplayTime() - 1);
						}, 1000);
					}
					dfd.resolve();
				});
				return dfd.promise();
			}
			getDataById(id: any) {
				let self = this;
				for (let j = 0; j < _.size(self.items()); j++) {
					if (self.items()[j].id == id) {
						for (let i = 0; i < _.size(self.listStampRecord()); i++) {
							if (self.listStampRecord()[i].stampDate == self.items()[j].date && self.listStampRecord()[i].stampTime == self.items()[j].time) {
								self.currentStampData(self.listStampRecord()[i]);
								break;
							}
						}
						break;
					}
				}
			}
			getAllStampingResult(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				let sid = self.infoEmpFromScreenA.employeeId;
				service.getAllStampingResult(sid).done(function(data) {
					_.forEach(data, (a) => {
						let items = _.orderBy(a.stampDataOfEmployeesDto.stampRecords, ['stampTimeWithSec'], ['desc']);
						_.forEach(items, (sr) => {
							self.listStampRecord.push(sr);
						});
					});
					if (_.size(self.listStampRecord()) > 0) {
						self.laceName(data[0].workPlaceName);
						self.listStampRecord(_.orderBy(self.listStampRecord(), ['stampTimeWithSec'], ['desc']));
						_.forEach(self.listStampRecord(), (sr) => {

							let changeClockArtDisplay = self.getTextAlign(sr);

							let dateDisplay = nts.uk.time.applyFormat("Short_YMDW", sr.stampDate);
							if (moment(sr.stampDate).day() == 6) {
								dateDisplay = "<span class='color-schedule-saturday' >" + dateDisplay + "</span>";
								sr.stampDate = "<span class='color-schedule-saturday' >" + sr.stampDate + "</span>";
							} else if (moment(sr.stampDate).day() == 0) {
								dateDisplay = "<span class='color-schedule-sunday'>" + dateDisplay + "</span>";
								sr.stampDate = "<span class='color-schedule-sunday'>" + sr.stampDate + "</span>";
							}
							self.items.push(new model.ItemModels(
								dateDisplay,
								"<div class='inline-bl'>" + sr.stampHow + "</div>" + sr.stampTime,
								changeClockArtDisplay,
								sr.stampDate,
								sr.stampTime
							));
						});
						self.currentCode(self.items()[0].id);
						dfd.resolve();
					} else {
						nts.uk.ui.dialog.alertError("Stamp Data Not Found!!!").then(() => {
							nts.uk.ui.windows.close();
						});

					}
				});
				return dfd.promise();
			}

			getTextAlign(sr: any): string {

				let value = sr.buttonValueType;
				if (ButtonType.GOING_TO_WORK == value || ButtonType.RESERVATION_SYSTEM == value) {

					return `<div class='full-width' style='text-align: left'>` + sr.stampArtName + '</div>';

				}

				if (ButtonType.WORKING_OUT == value) {

					return `<div class='full-width' style='text-align: right'>` + sr.stampArtName + '</div>';

				}

				return sr.stampArtName ? `<div class='full-width' style='text-align: center'>` + sr.stampArtName + '</div>' : '';

			}

			getEmpInfo(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				let employeeId = self.infoEmpFromScreenA.employeeId;
				service.getEmpInfo(employeeId).done(function(data) {
					self.employeeCodeName(data.employeeCode + " " + data.personalName);
					dfd.resolve();
				});
				return dfd.promise();
			}

            /**
             * Close dialog
             */
			public closeDialog(): void {
				nts.uk.ui.windows.close();
			}
		}
	}

	enum ButtonType {
		// 系

		GOING_TO_WORK = 1,
		// 系

		WORKING_OUT = 2,
		// "外出系"

		GO_OUT = 3,
		// 戻り系

		RETURN = 4,
		// 予約系

		RESERVATION_SYSTEM = 5
	}



	export module model {

		export class ItemModels {
			id: string;
			stampDate: string;
			stampHowAndTime: string;
			timeStampType: string;
			date: string;
			time: string
			constructor(stampDate: string, stampHowAndTime: string, timeStampType: string, date: string, time: string) {
				this.id = nts.uk.util.randomId();
				this.stampDate = stampDate;
				this.stampHowAndTime = stampHowAndTime;
				this.timeStampType = timeStampType;
				this.date = date;
				this.time = time;
			}
		}
	}
}