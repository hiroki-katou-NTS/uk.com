/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.s {
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

		created() {
			const vm = this;
			const { randomId } = nts.uk.util;
			const { GOING_TO_WORK, WORKING_OUT } = ChangeClockArt;
			const { GO_OUT, RETURN } = ChangeClockArt;
			const { FIX, END_OF_SUPPORT, SUPPORT, TEMPORARY_SUPPORT_WORK } = ChangeClockArt;

			vm.dataSources.filtereds = ko.computed({
				read: () => {
					const filtereds:StampDisp[] = [];
					const allStamps: StampData[] = ko.unwrap(vm.dataSources.all);
					const engraving: ENGRAVING = ko.unwrap(vm.filter.engraving);

					_.chain(allStamps)
						.orderBy(['stampDate', 'stampTime'], ['desc', 'desc'])
						.each((item: StampData) => {
							const d = moment(item.stampDate, 'YYYY/MM/DD');
							const day = d.clone().locale('en').format('dddd');

							const pushable = {
								id: randomId(),
								time: `${item.stampHow} ${item.stampTime}`,
								date: `<div class="color-schedule-${day.toLowerCase()}">${d.format('YYYY/MM/DD(dd)')}</div>`,
								name: `<div style="text-align: ${item.changeClockArt === 0 ? 'left' : item.changeClockArt === 1 ? 'right' : 'center'};">${item.stampArt}</div>`
							};

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
									// S1 bussiness logic
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
					const baseDate = moment(`${value}`, 'YYYYMM');
					const endDate = baseDate.endOf('month').format(fm);
					const startDate = baseDate.startOf("month").format(fm);

					vm.$ajax(API.GET_STAMP_MANAGEMENT, { endDate, startDate })
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
		GOING_TO_WORK = 0, // 出勤

		/** 退勤 */
		WORKING_OUT = 1,

		/** 入門 */
		OVER_TIME = 2,

		/** 退門 */
		BRARK = 3,

		/** 外出 */
		GO_OUT = 4,

		/** 戻り */
		RETURN = 5,

		/** 応援開始 */
		FIX = 6,

		/** 臨時出勤 */
		TEMPORARY_WORK = 7,

		/** 応援終了 */
		END_OF_SUPPORT = 8,

		/** 臨時退勤 */
		TEMPORARY_LEAVING = 9,

		/** PCログオン */
		PC_LOG_ON = 10,

		/** PCログオフ */
		PC_LOG_OFF = 11,

		/** 応援出勤 */
		SUPPORT = 12,

		/** 臨時+応援出勤 */
		TEMPORARY_SUPPORT_WORK = 13
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
		changeCalArt: number;
		changeClockArt: number;
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
