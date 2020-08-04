const DATE_AND_DAY_FORMAT = 'D(ddd)';
class EmbossGridInfo {

	columns: KnockoutObservableArray<any> = ko.observableArray([]);
	currentCode: KnockoutObservable<any> = ko.observable({});
	items: KnockoutObservableArray<any> = ko.observableArray([]);
	displayMethod: KnockoutObservable<any>;
	displayType: any = { HIDE: 0, DISPLAY: 1, SHOW_TIME_CARD: 2 };
	dateValue: KnockoutObservable<any>;
	yearMonth: KnockoutObservable<any>;

	constructor(start: IStartPage) {
		let self = this;
		let setting = start.stampSetting;
		self.displayMethod = ko.observable(setting.historyDisplayMethod);
		self.dateValue = ko.observable({ startDate: moment().add(-3, 'days').format('YYYY/MM/DD'), endDate: moment().format('YYYY/MM/DD') });
		self.yearMonth = ko.observable(moment().format('YYYY/MM'));

		if (self.displayMethod() == self.displayType.DISPLAY) {
			self.columns([
				{ headerText: 'コード', key: 'code', width: 100, hidden: true },
				{ headerText: nts.uk.resource.getText('KDP002_30'), key: 'stampDate', width: 130 },
				{ headerText: nts.uk.resource.getText('KDP002_31'), key: 'stampHowAndTime', width: 80 },
				{ headerText: nts.uk.resource.getText('KDP002_32'), key: 'timeStampType', width: 90 }
			]);
			self.bindItemData(start.stampDataOfEmployees);
		} else if (self.displayMethod() == self.displayType.SHOW_TIME_CARD) {
			self.columns([
				{ headerText: 'コード', key: 'code', width: 100, hidden: true },
				{ headerText: nts.uk.resource.getText('KDP002_30'), key: 'date', width: 60 },
				{ headerText: nts.uk.resource.getText('KDP002_33', ['#Com_WorkIn']), key: 'workIn1', width: 80 },
				{ headerText: nts.uk.resource.getText('KDP002_34', ['#Com_WorkOut']), key: 'workOut1', width: 80 },
				{ headerText: nts.uk.resource.getText('KDP002_35', ['#Com_WorkIn']), key: 'workIn2', width: 80 },
				{ headerText: nts.uk.resource.getText('KDP002_36', ['#Com_WorkOut']), key: 'workOut2', width: 100 }
			]);
			self.bindItemData(start.timeCard.listAttendances);
		}
	}

	setTimeStampType(stampData) {

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


		if (LEFT_ALIGNS.indexOf(stampData.correctTimeStampValue) > -1) {
			stampData.timeStampType = `<div class='full-width' style='text-align: left'>` + stampData.stampArtName + '</div>';
			return;
		}
		if (RIGHT_ALIGNS.indexOf(stampData.correctTimeStampValue) > -1) {
			stampData.timeStampType = `<div class='full-width' style='text-align: right'>` + stampData.stampArtName + '</div>';
			return;
		} else {
			stampData.timeStampType = stampData.stampArtName ? `<div class='full-width' style='text-align: center'>` + stampData.stampArtName + '</div>' : '';
		}

	}

	bindItemData(items: Array<any>) {
		let self = this;
		if (self.displayMethod() == self.displayType.DISPLAY) {
			let idx = 1;
			items = _.orderBy(items, ['stampTimeWithSec'], ['desc']);
			items.forEach(stampData => {
				stampData.code = ++idx;
				let formatedStamp = nts.uk.time.applyFormat("Short_YMDW", stampData.stampDate);
				if (moment(stampData.stampDate).day() == 6) {
					formatedStamp = "<span class='color-schedule-saturday'> " + formatedStamp + "</span>";
				} else if (moment(stampData.stampDate).day() == 0) {
					formatedStamp = "<span class='color-schedule-sunday'> " + formatedStamp + "</span>";
				} else {
					formatedStamp = "<span class=''> " + formatedStamp + "</span>";
				}
				stampData.stampDate = formatedStamp;

				stampData.stampHowAndTime = "<div class='inline-bl'>" + stampData.stampHow + "</div>" + stampData.stampTime;

				self.setTimeStampType(stampData);

			});

			self.items(items);
		} else if (self.displayMethod() == self.displayType.SHOW_TIME_CARD) {
			let idx = 1;
			items.forEach(timeCard => {
				timeCard.code = ++idx;
				let formatedCardTime = moment(timeCard.date).format(DATE_AND_DAY_FORMAT);
				if (moment(timeCard.date).day() == 6) {
					formatedCardTime = "<span class='color-schedule-saturday' >" + formatedCardTime + "</span>";
				} else if (moment(timeCard.date).day() == 0) {
					formatedCardTime = "<span class='color-schedule-sunday'>" + formatedCardTime + "</span>";
				} else {
					formatedCardTime = "<span class=''>" + formatedCardTime + "</span>";
				}
				timeCard.date = formatedCardTime;
				timeCard.workIn1 = timeCard.workIn1 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workIn1) : null;
				timeCard.workOut1 = timeCard.workOut1 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workOut1) : null;
				timeCard.workIn2 = timeCard.workIn2 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workIn2) : null;
				timeCard.workOut2 = timeCard.workOut2 ? nts.uk.time.format.byId("ClockDay_Short_HM", timeCard.workOut2) : null;
			});
			self.items(items);
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

interface IStartPage {
	timeCard: ITimeCard;
	stampSetting: IStampSettingPerson;
	stampDataOfEmployees: IStampHistoryInfo;
	stampToSuppress: IStampToSuppress;
	stampResultDisplay: IStampResultDisplay;

}

interface IStampResultDisplay {
	companyId: string;
	notUseAttr: number;
	displayItemId: Array<number>;
}

interface IStampToSuppress {
	isUse: boolean;
	goingToWork: boolean;
	departure: boolean;
	goOut: boolean;
	turnBack: boolean;
}

interface ITimeCard {
	employeeId: string;
	IAttendanceOneDay: Array<IAttendanceOneDay>;
}

interface IAttendanceOneDay {
	date: string;
	workIn1: string;
	workOut1: string;
	workIn2: string;
	workOut2: string;
}

interface ITimeActualStamp {
	numberOfReflectionStamp: number;

	actualAfterRoundingTime: string;
	actualTimeWithDay: string;
	actualLocationCode: string;
	actualStampSourceInfo: number;

	stampAfterRoundingTime: string;
	stampTimeWithDay: string;
	stampLocationCode: string;
	stampStampSourceInfo: number;
}

interface IStampHistoryInfo {
	code: string;
	stampDate: string;
	stampHowAndTime: string;
	timeStampType: string;
}

interface IStampSettingPerson {
	buttonEmphasisArt: boolean;
	historyDisplayMethod: number;
	correctionInterval: number;
	textColor: string;
	backGroundColor: string;
}