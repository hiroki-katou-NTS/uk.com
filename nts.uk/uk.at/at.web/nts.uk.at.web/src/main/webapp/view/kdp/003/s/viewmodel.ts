/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const KDP003S_API = {
	GET_STAMP_MANAGEMENT: 'at/record/stamp/management/personal/stamp/getStampData'
};

@bean()
class Kdp003SViewModel extends ko.ViewModel {
	filter: Kdp003SFilter = {
		day: ko.observable(parseInt(moment().format('YYYYMM'))),
		engraving: ko.observable('1')
	};

	dataSources: Kdp003sDataSource = {
		all: ko.observableArray([])
	};

	created() {
		const vm = this;
		const { randomId } = nts.uk.util;

		vm.dataSources.filtereds = ko.computed({
			read: () => {
				const filtereds: Kdp003sStampDisp[] = [];
				const allStamps: Kdp003sStampData[] = ko.unwrap(vm.dataSources.all);
				const engraving: KDP003S_ENGRAVING = ko.unwrap(vm.filter.engraving);

				_.each(allStamps, (item: Kdp003sStampData) => {
					const d = moment(item.stampDate, 'YYYY/MM/DD');
					const day = d.clone().locale('en').format('dddd');

					switch (engraving) {
						default:
						case '1':
							filtereds.push({
								id: randomId(),
								time: `${item.stampHow} ${item.stampTime}`,
								date: `<div class="color-schedule-${day.toLowerCase()}">${d.format('YYYY/MM/DD(dd)')}</div>`,
								name: `<div style="text-align: ${item.changeClockArt === 0 ? 'left' : item.changeClockArt === 1 ? 'right' : 'center'};">${item.stampArtName}</div>`
							});
							break;
						case '2':
						case '3':
						case '4':
							// S1 bussiness logic
							if (engraving === '2') {
								filtereds.push({
									id: randomId(),
									time: `${item.stampHow} ${item.stampTime}`,
									date: `<div class="color-schedule-${day.toLowerCase()}">${d.format('YYYY/MM/DD(dd)')}</div>`,
									name: `<div style="text-align: ${item.changeClockArt === 0 ? 'left' : item.changeClockArt === 1 ? 'right' : 'center'};">${item.stampArtName}</div>`
								});
							}
							break;
					}
				});

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

				vm.$ajax(KDP003S_API.GET_STAMP_MANAGEMENT, { endDate, startDate })
					.then((data: Kdp003sStampData[]) => {
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

type KDP003S_ENGRAVING = '1' | '2' | '3' | '4';

interface Kdp003SFilter {
	day: KnockoutObservable<number>;
	engraving: KnockoutObservable<KDP003S_ENGRAVING>;
}

interface Kdp003sDataSource {
	all: KnockoutObservableArray<Kdp003sStampData>;
	filtereds?: KnockoutComputed<Kdp003sStampDisp[]>;
}

interface Kdp003sStampData {
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

interface Kdp003sStampDisp {
	id: string;
	time: string;
	date: string;
	name: string;
}
