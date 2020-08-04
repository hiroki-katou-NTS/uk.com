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
				
				const {
					WORK,
					WORK_STRAIGHT,
					WORK_EARLY,
					WORK_BREAK,
					DEPARTURE,
					DEPARTURE_BOUNCE,
					DEPARTURE_OVERTIME,
					OUT,
					RETURN,
					GETTING_STARTED,
					DEPAR,
					TEMPORARY_WORK,
					TEMPORARY_LEAVING,
					START_SUPPORT,
					END_SUPPORT,
					WORK_SUPPORT,
					START_SUPPORT_EARLY_APPEARANCE,
					START_SUPPORT_BREAK,
					RESERVATION,
					CANCEL_RESERVATION
				} = ContentsStampType;

				const LEFT_ALIGNS = [
					WORK,
					WORK_STRAIGHT,
					WORK_EARLY,
					WORK_BREAK,
					GETTING_STARTED,
					TEMPORARY_WORK,
					START_SUPPORT,
					WORK_SUPPORT,
					START_SUPPORT_EARLY_APPEARANCE,
					START_SUPPORT_BREAK
				];

				const RIGHT_ALIGNS = [
					DEPARTURE,
					DEPARTURE_BOUNCE,
					DEPARTURE_OVERTIME,
					DEPAR,
					TEMPORARY_LEAVING,
					END_SUPPORT
				];

				if (LEFT_ALIGNS.indexOf(sr.correctTimeStampValue) > -1) {
					return "<div class='full-width' style='text-align: left'> " + sr.stampArtName + " </div>";
					
				}
				if (RIGHT_ALIGNS.indexOf(sr.correctTimeStampValue) > -1) {
					return "<div class='full-width' style='text-align: right'> " + sr.stampArtName + " </div>";
					
				} else {
					return  "<div class='full-width' style='text-align: center'> " + sr.stampArtName + " </div>";
					
				}

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

	enum ContentsStampType {
		/** 1: 出勤 */
		WORK = 1,

		/** 2: 出勤＋直行 */
		WORK_STRAIGHT = 2,

		/** 3: 出勤＋早出 */
		WORK_EARLY = 3,

		/** 4: 出勤＋休出 */
		WORK_BREAK = 4,

		/** 5: 退勤 */
		DEPARTURE = 5,

		/** 6: 退勤＋直帰 */
		DEPARTURE_BOUNCE = 6,

		/** 7: 退勤＋残業 */
		DEPARTURE_OVERTIME = 7,

		/** 8: 外出 */
		OUT = 8,

		/** 9: 戻り */
		RETURN = 9,

		/** 10: 入門 */
		GETTING_STARTED = 10,

		/** 11: 退門 */
		DEPAR = 11,

		/** 12: 臨時出勤 */
		TEMPORARY_WORK = 12,

		/** 13: 臨時退勤 */
		TEMPORARY_LEAVING = 13,

		/** 14: 応援開始 */
		START_SUPPORT = 14,

		/** 15: 応援終了 */
		END_SUPPORT = 15,

		/** 16: 出勤＋応援 */
		WORK_SUPPORT = 16,

		/** 17: 応援開始＋早出 */
		START_SUPPORT_EARLY_APPEARANCE = 17,

		/** 18: 応援開始＋休出 */
		START_SUPPORT_BREAK = 18,

		/** 19: 予約 */
		RESERVATION = 19,

		/** 20: 予約取消  */
		CANCEL_RESERVATION = 20
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