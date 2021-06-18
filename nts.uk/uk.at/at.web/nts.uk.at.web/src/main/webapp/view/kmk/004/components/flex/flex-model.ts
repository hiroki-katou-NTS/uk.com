module nts.uk.at.kmk004.components.flex {


	export interface ISidebarButtonParam {
		isShowCopyButton: boolean;
	}

	//フレックス勤務所定労働時間取得
	export interface IGetFlexPredWorkTime {
		//参照先
		reference: number;
	}

	//会社別フレックス勤務集計方法
	export interface IComFlexMonthActCalSet {
		//不足設定
		insufficSet: IShortageFlexSetting;
		//集計方法
		aggrMethod: number;
		//フレックス時間の扱い
		flexTimeHandle: IFlexTimeHandle;
		//法定内集計設定
		legalAggrSet: IAggregateTimeSetting;
		//所定労動時間使用区分
		withinTimeUsageAttr: boolean;

	}

	//法定内集計設定
	export interface IAggregateTimeSetting {
		//集計設定 
		aggregateSet: number;
	}

	//フレックス時間の扱い
	export interface IFlexTimeHandle {
		//残業時間をフレックス時間に含める 
		includeOverTime: boolean;
		//法定外休出時間をフレックス時間に含める
		includeIllegalHdwk: boolean;
	}

	//不足設定
	export interface IShortageFlexSetting {
		// 清算期間
		settlePeriod: number;
		//開始月
		startMonth: number;
		//期間 
		period: number;
		//繰越設定
		carryforwardSet: number;
	}

	export class SetCom {
		year: number;
		data: Array<IMonthlyWorkTimeSetCom> = [];
	}

	export class FlexScreenData {

		//会社別フレックス勤務集計方法
		comFlexMonthActCalSet: KnockoutObservable<IComFlexMonthActCalSet> = ko.observable();
		//フレックス勤務所定労働時間取得
		yearList: KnockoutObservableArray<YearItem> = ko.observableArray();
		selectedYear: KnockoutObservable<number> = ko.observable();
		monthlyWorkTimeSetComs: KnockoutObservableArray<MonthlyWorkTimeSetCom> = ko.observableArray();
		serverData: SetCom;
		serverYears: KnockoutObservableArray<Number> = ko.observableArray([]);
		setting: KnockoutObservable<ScreenMonthlySetting> = ko.observable(new ScreenMonthlySetting());
		flexMonthActCalSet: KnockoutObservable<IComFlexMonthActCalSet> = ko.observable();
		selected: KnockoutObservable<any> = ko.observable();
		selectedName: KnockoutObservable<string> = ko.observable();

		unSaveSetComs: Array<SetCom> = [];

		alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);

		constructor(param?: IScreenData) {

			if (param) {
				this.yearList(_.chain(param.yearList).map((item) => { return new YearItem(item); }).orderBy(['year'], ['desc']).value());
				this.comFlexMonthActCalSet(param.flexBasicSetting.comFlexMonthActCalSet);
				this.alreadySettingList(param.alreadySettings);
			}
            this.comFlexMonthActCalSet.subscribe(() => {
                 $('#monthly-list').css('border-right','solid grey 1px');
                 $('#monthly-list').css('border-bottom','solid grey 1px');
                 setTimeout(() => {
                     $('#monthly-list').css('border', 'none');
                 }, 200);
            });

		}

		initDumpData(ym: number) {
			const vm = this;
			let workTimes: Array<IMonthlyWorkTimeSetCom> = [];
			let startMonth = ym % 100;

			for (let i = 0; i < 12; i++) {
				let yearMonth = ((Number(moment().format("YYYY")) * 100 + Math.floor((startMonth + i) / 12) * 100)) + ((startMonth + i) % 12);
				if ((startMonth + i) % 12 == 0) {
					yearMonth = (Number(moment().format("YYYY")) * 100) + 12;
				}
				workTimes.push({ yearMonth: yearMonth, laborTime: { withinLaborTime: null, legalLaborTime: null, weekAvgTime: null, checkbox: false } });
			}
			let setComs = _.map(workTimes, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm, item); });

			vm.monthlyWorkTimeSetComs(setComs);
		}

		setYM(year: number, data: Array<IMonthlyWorkTimeSetCom>) {
			const vm = this;

			vm.serverData = { year: year, data: data };

			vm.monthlyWorkTimeSetComs(_.map(data, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm, item); }));
		}

		clearUpdateYear(year: number) {
			const vm = this,
				yearList = vm.yearList();

			_.remove(yearList, (item) => { return item.year == year; });

			yearList.push(new YearItem(Number(year), false));
			vm.yearList(_.orderBy(yearList, ['year'], ['desc']));
		}

		saveData() {
			const vm = this;

			let saveitem = ko.toJS(vm.monthlyWorkTimeSetComs()),
				year = Number(vm.selectedYear());

			let unsaveItem = _.find(vm.unSaveSetComs, ['year', year]);

			if (unsaveItem) {
				unsaveItem.data = saveitem;
			}


			vm.serverData = { year: year, data: saveitem };

			vm.serverData = { year: year, data: saveitem };
		}

		deleteYear(year: number) {
			const vm = this,
				yearList = vm.yearList();

			_.remove(yearList, (item) => { return item.year == year; });

			vm.yearList(yearList);

			let svYear = vm.serverYears();
			_.remove(svYear, (y) => { return y == year; });

			vm.serverYears(svYear);
		}

		setSelectedAfterRemove(year: number) {
			const vm = this,
				yearList = vm.yearList();
			let index = _.findIndex(yearList, ['year', Number(year)]);


			if (yearList.length == 1) {
				vm.selectedYear(null);
				return;
			}

			if (index == 0) {
				vm.selectedYear(yearList[1].year);
				return;
			}

			if (index == yearList.length - 1) {
				vm.selectedYear(yearList[yearList.length - 2].year);
				return;
			}

			vm.selectedYear(yearList[index + 1].year);

		}

		clearUnSaveList(year: number) {
			const vm = this;

			_.remove(vm.unSaveSetComs, (item) => { return item.year == year; });
		}

		saveToUnSaveList() {
			let vm = this,
				oldData = vm.serverData ? vm.serverData.data : [];
			let isChanged = false;
			if (oldData.length) {
				let compareOldData = ko.toJS(_.map(oldData, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm, item); })),
					newData = ko.toJS(vm.monthlyWorkTimeSetComs()),
					oldYear = vm.serverData.year;
				isChanged = _.differenceWith(compareOldData, newData, _.isEqual).length > 0;
				if (isChanged) {
					let unsaveItem = _.find(vm.unSaveSetComs, ['year', oldYear]);
					if (!unsaveItem) {
						vm.unSaveSetComs.push({ year: oldYear, data: newData });
					} else {
						unsaveItem.data = newData;
					}
				}
			}
			return isChanged;
		}

		setUpdateYear(year: number) {
			const vm = this,
				yearList = vm.yearList();

			_.remove(yearList, (item) => { return item.year == year; });

			yearList.push(new YearItem(Number(year), true));
			vm.yearList(_.orderBy(yearList, ['year'], ['desc']));
		}

		updateMode() {
			const vm = this;
			return vm.serverYears().length > 0 ? vm.serverYears().indexOf(Number(vm.selectedYear())) != -1 : false;
		}

		setFocus(mode?: 'load' | 'change') {
			$('#year-list').focus();
		}

		setNewYear(startYM: number, year: number) {
			const vm = this;
			let workTimes = [];

			let startMonth = startYM % 100;

			for (let i = 0; i < 12; i++) {
				let ym = ((year * 100 + Math.floor((startMonth + i) / 12) * 100)) + ((startMonth + i) % 12);
				if ((startMonth + i) % 12 == 0) {
					ym = (year * 100) + 12;
				}
				workTimes.push({ yearMonth: ym, laborTime: { checkbox: true, withinLaborTime: vm.comFlexMonthActCalSet().withinTimeUsageAttr == false ? null : 0, legalLaborTime: 0, weekAvgTime: 0 } });
			}

			vm.serverData = { year: year, data: workTimes };
			vm.unSaveSetComs.push({ year: year, data: workTimes });
			let setComs = _.map(workTimes, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(vm, item); });
			vm.monthlyWorkTimeSetComs(setComs);
		}

		updateData(param: IScreenData) {
			param.yearList.reverse();
			this.yearList(_.chain(param.yearList).map((item) => { return new YearItem(item); }).orderBy(['year'], ['desc']).value());
			this.serverYears(param.yearList);
			this.selectedYear(param.yearList[0]);
			this.comFlexMonthActCalSet(param.flexBasicSetting.comFlexMonthActCalSet);
			this.flexMonthActCalSet(param.flexBasicSetting.flexMonthActCalSet)
		}
	}

	class ScreenMonthlySetting {
		useRegularWorkingHours: KnockoutObservable<number> = ko.observable(1);
		constructor(param?: IScreenMonthlySetting) {
			if (param) {
				this.useRegularWorkingHours(param.useRegularWorkingHours);
			}
		}
	}
	interface IScreenMonthlySetting {
		useRegularWorkingHours: number;
	}

	export class YearItem {
		isNewText: string;
		isNotSave: boolean;
		year: number;
		yearName: string;

		constructor(year: number, isNotSave?: boolean) {
			this.year = year;
			this.yearName = year.toString() + '年度';
			this.isNewText = isNotSave ? '＊' : '';
			this.isNotSave = isNotSave ? isNotSave : false;
		}

		updateNotSave(isNotSave: boolean) {
			this.isNotSave = isNotSave;
			this.isNewText = isNotSave ? '＊' : '';
		}
	}



	export class MonthlyWorkTimeSetCom {

		laborAttr = 2;
		//年月
		yearMonth: number;

		yearMonthText: string;
		//月労働時間
		laborTime: KnockoutObservable<MonthlyLaborTime>;

		constructor(screen: FlexScreenData, param: IMonthlyWorkTimeSetCom) {
			this.yearMonth = param.yearMonth;
			this.yearMonthText = param.yearMonth.toString().substring(4).replace(/^0+/, "");
			this.laborTime = ko.observable(new MonthlyLaborTime(screen, param.laborTime));
		}
	}

	export class MonthlyLaborTime {
		//所定労働時間
		withinLaborTime: KnockoutObservable<number> = ko.observable();
		//法定労働時間
		legalLaborTime: KnockoutObservable<number> = ko.observable();
		//週平均時間
		weekAvgTime: KnockoutObservable<number> = ko.observable();

		checkbox: KnockoutObservable<boolean> = ko.observable(true);

		constructor(screen: FlexScreenData, param: IMonthlyLaborTime) {
			this.withinLaborTime(param.withinLaborTime);
			this.legalLaborTime(param.legalLaborTime);
			this.weekAvgTime(param.weekAvgTime);
			this.checkbox(param.checkbox ? param.checkbox : true);
			if (param.weekAvgTime == null && param.legalLaborTime == null && param.withinLaborTime == null) {
				this.checkbox(false);
			}

			this.withinLaborTime.subscribe(() => {
				if (screen.selectedYear()) {
					screen.setUpdateYear(screen.selectedYear());
				}
			});

			this.legalLaborTime.subscribe(() => {
				if (screen.selectedYear()) {
					screen.setUpdateYear(screen.selectedYear());
				}
			});

			this.weekAvgTime.subscribe(() => {
				if (screen.selectedYear()) {
					screen.setUpdateYear(screen.selectedYear());
				}
			});

			this.checkbox.subscribe((state) => {
				if (screen.selectedYear()) {
					screen.setUpdateYear(screen.selectedYear());
				}
				if (state) {
					this.withinLaborTime(0);
					this.legalLaborTime(0);
					this.weekAvgTime(0);
				} else {
					this.withinLaborTime(null);
					this.legalLaborTime(null);
					this.weekAvgTime(null);
				}
			});
		}


	}



	export interface IScreenData {

		//会社別基本設定（フレックス勤務）を表示する
		flexBasicSetting: IDisplayFlexBasicSettingByCompanyDto

		isShowCheckbox: boolean;
		//年度リスト
		yearList: Array<number>;
		//会社別月単位労働時間
		monthlyWorkTimeSetComs: Array<IMonthlyWorkTimeSetCom>;

		alreadySettings: Array<string>;
	}

	export interface IDisplayFlexBasicSettingByCompanyDto {
		//会社別フレックス勤務集計方法
		comFlexMonthActCalSet: IComFlexMonthActCalSet
		//フレックス勤務所定労働時間取得
		flexPredWorkTime: IGetFlexPredWorkTime;

		flexMonthActCalSet: IComFlexMonthActCalSet
	}
	//会社別月単位労働時間 
	export interface IMonthlyWorkTimeSetCom {
		//年月．月
		yearMonth: number;
		//労働時間
		laborTime: IMonthlyLaborTime;
	}

	//月労働時間
	export interface IMonthlyLaborTime {
		//所定労働時間
		withinLaborTime: number;
		//法定労働時間
		legalLaborTime: number;
		//週平均時間
		weekAvgTime: number;

		checkbox: boolean;
	}
}