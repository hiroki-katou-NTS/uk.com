/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.s {
	interface Params {
		employeeId: string;
	}

	const API = {
		GET_STAMP_MANAGEMENT: '/at/record/stamp/management/personal/stamp/getStampData'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		filter: Filter = {
			day: ko.observable(parseInt(moment().format('YYYYMM'))),
			engraving: ko.observable('1')
		};

		dataSources: DataSource = {
			all: ko.observableArray([])
		};

		constructor(private params: Params) {
			super();

			if (!params) {
				this.params = { employeeId: '' };
			}
		}

		created() {
			const vm = this;
			const { randomId } = nts.uk.util;
			const { GOING_TO_WORK, WORKING_OUT } = ChangeClockArt;
			const { GO_OUT, RETURN } = ChangeClockArt;
			const { FIX, END_OF_SUPPORT, SUPPORT, TEMPORARY_SUPPORT_WORK } = ChangeClockArt;

			vm.dataSources.filtereds = ko.computed({
				read: () => {
					const filtereds: StampDisp[] = [];
					const allStamps: StampData[] = ko.unwrap(vm.dataSources.all);
					const engraving: ENGRAVING = ko.unwrap(vm.filter.engraving);

					_.chain(allStamps)
						.orderBy(['stampDate', 'stampTime'], ['asc', 'asc'])
						.each((item: StampData) => {
							const d = moment(item.stampDate, 'YYYY/MM/DD');
							const day = d.clone().locale('en').format('dddd');

							// bad algorithm :/
							const LEFT_ALIGNS = ['出勤', '出勤＋直行', '出勤＋早出', '出勤＋休出', '臨時出勤', '応援開始', '応援開始＋休出', '応援開始＋早出', '臨時退勤', '入門'];
							const RIGHT_ALIGNS = ['退勤', '退勤＋直帰', '退勤＋産業', '応援終了', '臨時退勤', '退門'];

							const pushable = {
								id: randomId(),
								time: `${item.stampHow} ${item.stampTime}`,
								date: `<div class="color-schedule-${day.toLowerCase()}">${d.format('YYYY/MM/DD(dd)')}</div>`,
								name: `<div style="text-align: ${LEFT_ALIGNS.indexOf(item.changeClockArtName) > -1 ? 'left' :
									RIGHT_ALIGNS.indexOf(item.changeClockArtName) > -1 ? 'right' : 'center'};">${item.stampArt}</div>`
							};

							// S1 bussiness logic
							switch (engraving) {
								default:
								case '1':
									filtereds.push(pushable);
									break;
								case '2':
									if ([GOING_TO_WORK, WORKING_OUT].indexOf(item.changeClockArt) > -1) {
										filtereds.push(pushable);
									}
									break;
								case '3':
									if ([GO_OUT, RETURN].indexOf(item.changeClockArt) > -1) {
										filtereds.push(pushable);
									}
								case '4':
									if ([FIX, END_OF_SUPPORT, SUPPORT, TEMPORARY_SUPPORT_WORK].indexOf(item.changeClockArt) > -1) {
										filtereds.push(pushable);
									}
									break;
							}
						})
						.value();

					return filtereds;
				},
				owner: vm
			})

			vm.filter.day
				.subscribe((value: number) => {
					const fm = 'YYYY/MM/DD';
					const { employeeId } = vm.params;
					const baseDate = moment(`${value}`, 'YYYYMM');
					const endDate = baseDate.endOf('month').format(fm);
					const startDate = baseDate.startOf("month").format(fm);

					vm.$ajax(API.GET_STAMP_MANAGEMENT, { employeeId, endDate, startDate })
						.then((data: StampData[]) => {
							if (ko.toJS(vm.filter.day) === value) {
								vm.dataSources.all(data);
							}
						});
				});

			vm.filter.day.valueHasMutated();
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}
	}

	export type ENGRAVING = '1' | '2' | '3' | '4';

	export enum ChangeClockArt {
		/** 0. 出勤 */
		GOING_TO_WORK = 0,

		/** 1. 退勤 */
		WORKING_OUT = 1,

		/** 2. 入門 */
		OVER_TIME = 2,

		/** 3. 退門 */
		BRARK = 3,

		/** 4. 外出 */
		GO_OUT = 4,

		/** 5. 戻り */
		RETURN = 5,

		/** 6. 応援開始 */
		FIX = 6,

		/** 7. 臨時出勤 */
		TEMPORARY_WORK = 7,

		/** 8. 応援終了 */
		END_OF_SUPPORT = 8,

		/** 9. 臨時退勤 */
		TEMPORARY_LEAVING = 9,

		/** 10. PCログオン */
		PC_LOG_ON = 10,

		/** 11. PCログオフ */
		PC_LOG_OFF = 11,

		/** 12. 応援出勤 */
		SUPPORT = 12,

		/** 13. 臨時+応援出勤 */
		TEMPORARY_SUPPORT_WORK = 13
	}

	enum ChangeCalArt {
		/** N: なし */
		NONE = 0,

		/** N: 早出 */
		EARLY_APPEARANCE = 1,

		/** N: 残業 */
		OVER_TIME = 2,

		/** N: 休出 */
		BRARK = 3,

		/** N: ﾌﾚｯｸｽ */
		FIX = 4
	}

	const WORKTYPE = {
		'出勤': 1,
		'出勤＋直行': 2,
		'出勤＋早出': 3,
		'出勤＋休出': 4,
		'退勤': 5,
		'退勤＋直帰': 6,
		'退勤＋産業': 7,
		'外出': 8,
		'戻り': 9,
		'入門': 10,
		'退門': 11,
		'臨時出勤': 12,
		'臨時退勤': 13,
		'応援開始': 14,
		'応援終了': 15,
		'出勤＋応援': 16,
		'応援開始＋早出': 17,
		'応援開始＋休出': 18,
		'予約': 19,
		'予約取消': 20,
	};

	export interface Filter {
		day: KnockoutObservable<number>;
		engraving: KnockoutObservable<ENGRAVING>;
	}

	export interface DataSource {
		all: KnockoutObservableArray<StampData>;
		filtereds?: KnockoutComputed<StampDisp[]>;
	}

	export interface StampData {
		attendanceTime: string;
		authcMethod: number;
		cardNumberSupport: string;
		changeCalArt: ChangeCalArt;
		changeClockArt: ChangeClockArt;
		changeClockArtName: string;
		changeHalfDay: boolean;
		corectTtimeStampType: string;
		empInfoTerCode: string;
		goOutArt: string;
		latitude: number;
		locationInfor: string;
		longitude: number;
		outsideAreaAtr: string;
		overLateNightTime: string;
		overTime: string;
		reflectedCategory: boolean;
		revervationAtr: number;
		setPreClockArt: number;
		stampArt: string;
		stampArtName: string;
		stampDate: string;
		stampHow: string;
		stampMeans: number;
		stampNumber: string;
		stampTime: string;
		stampTimeWithSec: string;
		timeStampType: string;
		workLocationCD: string;
		workTimeCode: string;
	}

	export interface StampDisp {
		id: string;
		time: string;
		date: string;
		name: string;
	}
}
