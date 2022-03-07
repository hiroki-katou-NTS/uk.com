/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.s {
	interface Params {
		employeeId: string;
		regionalTime: number;
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

			const vm = this;
			if (!params) {
				vm.params = { employeeId: '', regionalTime: 0 };
			}

			vm.filter.day = ko.observable(parseInt(moment(vm.$date.now()).add(params.regionalTime, 'm').format('YYYYMM')));
		}

		created() {
			const vm = this;
			const { randomId } = nts.uk.util;

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

							let value = item.buttonValueType;

							const pushable = {
								id: randomId(),
								time: `${item.stampHow} ${item.stampTime}`,
								date: `<div class="color-schedule-${day.toLowerCase()}">${d.format('YYYY/MM/DD(dd)')}</div>`,
								name: `<div style="text-align: 
								${(ButtonType.GOING_TO_WORK == value || ButtonType.RESERVATION_SYSTEM == value) ? 'left' :
										ButtonType.WORKING_OUT == value ? 'right'
											: 'center'};">${item.stampArt}</div>`
							};

							// S1 bussiness logic
							switch (engraving) {
								default:
								case '1':
									filtereds.push(pushable);
									break;
								case '2':
									if ([ChangeClockArt.GOING_TO_WORK, ChangeClockArt.WORKING_OUT].indexOf(item.changeClockArt) > -1) {
										filtereds.push(pushable);
									}
									break;
								case '3':
									if ([ChangeClockArt.GO_OUT, ChangeClockArt.RETURN].indexOf(item.changeClockArt) > -1) {
										filtereds.push(pushable);
									}
									break;
								case '4':
									if ([ChangeClockArt.FIX, ChangeClockArt.END_OF_SUPPORT, ChangeClockArt.SUPPORT, ChangeClockArt.TEMPORARY_SUPPORT_WORK].indexOf(item.changeClockArt) > -1) {
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
					const endDate = moment(moment(vm.$date.now()).add(vm.params.regionalTime, 'm').endOf('month')).format(fm);
					const startDate = moment(moment(vm.$date.now()).add(vm.params.regionalTime, 'm').startOf('month')).format(fm);

					vm.$ajax(API.GET_STAMP_MANAGEMENT, { employeeId: vm.params.employeeId, endDate, startDate })
						.then((data: StampData[]) => {
							if (ko.toJS(vm.filter.day) === value) {
								vm.dataSources.all(data);
							}
						});
				});

			vm.filter.day.valueHasMutated();
		}

		mounted() {
			$('.nts-datepicker-wrapper').first().find('input').focus();
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}
	}

	export type ENGRAVING = '1' | '2' | '3' | '4';

	export enum ButtonType {
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

	export enum ChangeCalArt {
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

	export enum ContentsStampType {
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
		correctTimeStampValue: ContentsStampType;
		buttonValueType: number;
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
