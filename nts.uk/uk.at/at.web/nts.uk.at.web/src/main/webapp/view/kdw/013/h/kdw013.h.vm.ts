module nts.uk.at.view.kdw013.h {
	import getShared = nts.uk.ui.windows.getShared;
	import block = nts.uk.ui.block;
	import info = nts.uk.ui.dialog.info;
	import error = nts.uk.ui.dialog.error;
	import errors = nts.uk.ui.errors;
	import ajax = nts.uk.request.ajax;
	import getText = nts.uk.resource.getText;

	export module viewmodel {
		const paths: any = {
			start: "screen/at/kdw013/h/start",
			save: "screen/at/kdw013/h/save"
		}
		export class ScreenModel {
			itemId28: ItemValue;
			itemId29: ItemValue;

			itemId31_34: StartEndTime;

			itemId157_159: StartEndTime;
			itemId163_165: StartEndTime;
			breakTimeBase: StartEndTime[] = [];

			itemId169_171: StartEndTime;
			itemId175_177: StartEndTime;
			itemId181_183: StartEndTime;
			itemId187_189: StartEndTime;
			itemId193_195: StartEndTime;
			itemId199_201: StartEndTime;
			itemId205_207: StartEndTime;
			itemId211_213: StartEndTime;
			breakTimeOptions: StartEndTime[] = [];

			isShowBreakTimeOptions: KnockoutObservable<boolean> = ko.observable(false);

			itemOptions: ItemValueOption[] = [];

			params: Param;
			dataMaster: DataMaster;

			constructor() {
				let self = this;
				//self.params = getShared('KDW013H');
				self.params = paramFake;
				self.fakeData();//fake data co dinh
				self.orderOptionItems();
			}

			public startPage(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				self.breakTimeBase.push(this.itemId157_159, this.itemId163_165);
				self.breakTimeOptions.push(this.itemId169_171, this.itemId181_183,
					this.itemId187_189, this.itemId193_195,
					this.itemId199_201, this.itemId205_207,
					this.itemId205_207, this.itemId211_213);
				block.invisible();
				let param = attendentItems;
				param.push(28, 29);
				ajax(paths.start, { itemIds: param }).done(function(data: DataMaster) {
					self.dataMaster = data;
					self.setDataMaster();
					console.log(data);
					dfd.resolve();
				}).fail(function(res: any) {
					error({ messageId: res.messageId });
				}).always(() => {
					block.clear();
				});
				return dfd.promise();
			}

			setDataMaster() {
				let self = this;
				if (self.dataMaster && self.dataMaster.workTypes && self.itemId28.value()) {
					let workType = _.find(self.dataMaster.workTypes, w => w.workTypeCode == self.itemId28.value());
					if (workType){
						self.itemId28.itemSelectedDisplay(self.itemId28.value() + ' ' + workType.name);
					} else {
						self.itemId28.itemSelectedDisplay(self.itemId28.value() + ' ' + getText('KDW013_40'));
					}
				} else {
					self.itemId28.itemSelectedDisplay('');
				}

				if (self.dataMaster && self.dataMaster.workTimeSettings && self.itemId29.value()) {
					let workTime = _.find(self.dataMaster.workTimeSettings, w => w.worktimeCode == self.itemId29.value());
					if (workTime) {
						self.itemId29.itemSelectedDisplay(self.itemId29.value() + ' ' + workTime.workTimeDisplayName.workTimeName);
					} else {
						self.itemId29.itemSelectedDisplay(self.itemId29.value() + ' ' + getText('KDW013_40'));
					}
				} else {
					self.itemId29.itemSelectedDisplay('');
				}
				//set name và type cho item tuy ý
				_.forEach(self.itemOptions, item => {
					let itemName = _.find(self.dataMaster.attItemName, a => a.attendanceItemId == item.itemId);
					let itemType = _.find(self.dataMaster.dailyAttendanceItem, d => d.attendanceItemId == item.itemId);
					let use = true;
					item.setUseNameType(use, itemName ? itemName.displayName : null, itemType ? itemType.dailyAttendanceAtr : null);
					let option: Option[] = [];
					let divergenceReasonInputMethods = null;
					//trường hợp đặc biệt
					if (item.itemId == 438 || item.itemId == 439) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 1);
					} else if (item.itemId == 443 || item.itemId == 444) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 2);
					} else if (item.itemId == 448 || item.itemId == 449) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 3);
					} else if (item.itemId == 453 || item.itemId == 454) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 4);
					} else if (item.itemId == 458 || item.itemId == 459) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 5);
					} else if (item.itemId == 801 || item.itemId == 802) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 6);
					} else if (item.itemId == 806 || item.itemId == 807) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 7);
					} else if (item.itemId == 811 || item.itemId == 811) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 8);
					} else if (item.itemId == 816 || item.itemId == 817) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 9);
					} else if (item.itemId == 821 || item.itemId == 822) {
						divergenceReasonInputMethods = _.find(self.dataMaster.divergenceReasonInputMethods, d => d.divergenceTimeNo == 10);
					}

					if (divergenceReasonInputMethods){
						option = _.map(divergenceReasonInputMethods.reasons, (r: any) => { return { code: r.divergenceReasonCode, name: r.reason } });
						let selected = _.find(divergenceReasonInputMethods.reasons, (r: any) => r.divergenceReasonCode == item.value());
						if (!selected) {
							option = [{ code: item.value(), name: getText('KDW013_40') }, ... option];
						}
					}
					if (option.length > 0) {
						item.options = option;
					}
				});

			}

			orderOptionItems() {
				let self = this;
				//order
				let tg: IItemValue[] = [];
				_.forEach(_.sortBy(self.params.displayAttItems, ['order']), (item) => {
					let e = _.find(self.params.itemValues, i => i.itemId == item.attendanceItemId);
					if (e) {
						tg.push(e)
					}
				});
				//set
				_.forEach(tg, (item) => {
					self.itemOptions.push(new ItemValueOption(item));
				});
			}

			fakeData(): void {
				let self = this;

				//fake data các item co dinh
				self.itemId28 = new ItemValue({ itemId: 28, value: 'item 28', valueType: 0, layoutCode: 'layoutCode 28' });
				self.itemId29 = new ItemValue({ itemId: 29, value: 'item 29', valueType: 0, layoutCode: 'layoutCode 29' });

				self.itemId31_34 = new StartEndTime({ itemId: 31, value: 20, valueType: 0, layoutCode: 'layoutCode 31' }, { itemId: 34, value: 30, valueType: 0, layoutCode: 'layoutCode 34' });

				self.itemId157_159 = new StartEndTime({ itemId: 157, value: 20, valueType: 0, layoutCode: 'layoutCode 157' }, { itemId: 159, value: 30, valueType: 0, layoutCode: 'layoutCode 159' }, 1);
				self.itemId163_165 = new StartEndTime({ itemId: 163, value: 20, valueType: 0, layoutCode: 'layoutCode 163' }, { itemId: 165, value: 30, valueType: 0, layoutCode: 'layoutCode 165' }, 2);

				self.itemId169_171 = new StartEndTime({ itemId: 169, value: 20, valueType: 0, layoutCode: 'layoutCode 163' }, { itemId: 171, value: 30, valueType: 0, layoutCode: 'layoutCode 171' }, 3);
				self.itemId175_177 = new StartEndTime({ itemId: 175, value: 20, valueType: 0, layoutCode: 'layoutCode 175' }, { itemId: 177, value: 30, valueType: 0, layoutCode: 'layoutCode 177' }, 4);
				self.itemId181_183 = new StartEndTime({ itemId: 181, value: 20, valueType: 0, layoutCode: 'layoutCode 181' }, { itemId: 183, value: 30, valueType: 0, layoutCode: 'layoutCode 183' }, 5);
				self.itemId187_189 = new StartEndTime({ itemId: 187, value: 20, valueType: 0, layoutCode: 'layoutCode 187' }, { itemId: 189, value: 30, valueType: 0, layoutCode: 'layoutCode 189' }, 6);
				self.itemId193_195 = new StartEndTime({ itemId: 193, value: 20, valueType: 0, layoutCode: 'layoutCode 193' }, { itemId: 195, value: 30, valueType: 0, layoutCode: 'layoutCode 195' }, 7);
				self.itemId199_201 = new StartEndTime({ itemId: 199, value: 20, valueType: 0, layoutCode: 'layoutCode 199' }, { itemId: 201, value: 30, valueType: 0, layoutCode: 'layoutCode 201' }, 8);
				self.itemId205_207 = new StartEndTime({ itemId: 205, value: 20, valueType: 0, layoutCode: 'layoutCode 205' }, { itemId: 207, value: 30, valueType: 0, layoutCode: 'layoutCode 207' }, 9);
				self.itemId211_213 = new StartEndTime({ itemId: 211, value: 20, valueType: 0, layoutCode: 'layoutCode 211' }, { itemId: 213, value: 30, valueType: 0, layoutCode: 'layoutCode 213' }, 10);

			}

			registration() {
				let self = this;
				let data: IItemValue[] = [];

				if (self.itemId28.isChange()) {
					data.push(self.itemId28.toDataSave());
				}

				if (self.itemId29.isChange()) {
					data.push(self.itemId29.toDataSave());
				}

				self.itemId31_34.toDataSave().forEach((e) => data.push(e));

				self.breakTimeBase.forEach((startEnd) => {
					startEnd.toDataSave().forEach((e) => data.push(e));
				});

				self.breakTimeOptions.forEach((startEnd) => {
					startEnd.toDataSave().forEach((e) => data.push(e));
				});

				self.itemOptions.forEach((item) => {
					if (item.use() && item.isChange()){
						data.push(item.toDataSave());
					}
				});

				console.log(data);
				
				block.invisible();
				let param = {
					empTarget: self.params.employeeId, //対象社員
					targetDate: self.params.date, //対象日
					items: data, //実績内容  => List<ItemValue> id và giá trị
					integrationOfDaily: self.params.IntegrationOfDaily
				};
				ajax(paths.save, param).done(() => {
					info({ messageId: 'Msg_15' });
				}).fail(function(res: any) {
					error({ messageId: res.messageId });
				}).always(() => {
					block.clear();
				});
			}

            /**
             * Close dialog
             */
			public closeDialog(): void {
				nts.uk.ui.windows.close();
			}

			showTime() {
				this.isShowBreakTimeOptions(!this.isShowBreakTimeOptions());
			}
		}
	}
	class StartEndTime {
		start: ItemValue;
		end: ItemValue;
		constructor(start: IItemValue, end: IItemValue, breakTimeNo?: number) {
			this.start = new ItemValue(start, breakTimeNo ? getText('KDW013_88', [breakTimeNo]) : undefined);
			this.end = new ItemValue(end, breakTimeNo ? getText('KDW013_89', [breakTimeNo]) : undefined);
			this.end.value.subscribe(() => {
			});
		}
		toDataSave(): IItemValue[] {
			let result: IItemValue[] = [];
			if (this.start.isChange()) {
				result.push(this.start.toDataSave());
			}
			if (this.end.isChange()) {
				result.push(this.end.toDataSave());
			}
			return result;
		}
	}
	class ItemValue {
		itemId: number;
		value: KnockoutObservable<string> = ko.observable(null);
		valueBeforeChange: any;
		valueType: number;
		layoutCode: string;
		itemSelectedDisplay: KnockoutObservable<string> = ko.observable('');
		name: string; //only use for break times
		constructor(itemValue?: IItemValue, name?: string) {
			this.itemId = itemValue.itemId;
			this.value(itemValue.value);
			this.valueBeforeChange = itemValue.value;
			this.valueType = itemValue.valueType;
			this.layoutCode = itemValue.layoutCode;
			this.name = name;
		}
		toDataSave(): IItemValue {
			return {
				itemId: this.itemId,
				value: this.value(),
				valueType: this.valueType,
				layoutCode: this.layoutCode
			};
		}
		isChange(): boolean {
			return this.value() != this.valueBeforeChange;
		}
	}

	class ItemValueOption extends ItemValue {
		lable: KnockoutObservable<string> = ko.observable('');
		use: KnockoutObservable<boolean> = ko.observable(false);
		type: number;
		options: Option[] = [];
		constructor(itemValue: IItemValue) {
			super(itemValue);
		}
		setUseNameType(use: boolean, name?: string, type?: number) {
			if (use) {
				this.use(use);
				this.lable(name);
				this.type = type;
			}
		}
	}
	type Option = {
		code: string;
		name: string;
	}

	type IItemValue = {
		itemId: number;
		value: any;
		valueType: number;
		layoutCode: string;
	}

	type Param = {
		employeeId: string; //対象社員
		date: Date; //対象日
		IntegrationOfDaily: any; //日別実績(Work)
		displayAttItems: DisplayAttItem[]; //実績入力ダイアログ表示項目一覧  => List<表示する勤怠項目> id và thứ tự hiển thị
		itemValues: IItemValue[]; //実績内容  => List<ItemValue> id và giá trị
		lockInfos: DailyLock | null; //日別実績のロック状態 Optional<日別実績のロック状態>
	}

	type DataMaster = {
		attItemName: any[];
		dailyAttendanceItem: any[];
		divergenceReasonInputMethods: any[];
		divergenceTimeRoots: any[];
		workTimeSettings: any[];
		workTypes: any[];
	}

	type DailyLock = {
		employeeId: string;
		date: Date;
		lockDailyResult: number;
		lockWpl: number;
		lockApprovalMontｈ: number;
		lockConfirmMonth: number;
		lockApprovalDay: number;
		lockConfirmDay: number;
		lockPast: number;
	}

	type DisplayAttItem = {
		attendanceItemId: number;
		order: number;
	}

	let paramFake: Param = {
		employeeId: __viewContext.user.employeeId, //対象社員
		date: new Date(), //対象日
		IntegrationOfDaily: null, //日別実績(Work)
		displayAttItems: [//実績入力ダイアログ表示項目一覧  => List<表示する勤怠項目> id và thứ tự hiển thị
			{ attendanceItemId: 216, order: 1 }, { attendanceItemId: 221, order: 2 },{ attendanceItemId: 226, order:3}, { attendanceItemId: 231, order: 4 }, { attendanceItemId: 236, order: 5 },{ attendanceItemId: 241, order: 6 }, { attendanceItemId: 246, order: 7 }, { attendanceItemId: 251, order: 8 }, { attendanceItemId: 256, order: 9 },
			{ attendanceItemId: 261, order: 10 },{attendanceItemId: 266, order: 11}, { attendanceItemId:271, order:12},{attendanceItemId:276, order: 13},{ attendanceItemId: 281, order: 14 }, { attendanceItemId: 286, order: 15}, { attendanceItemId: 291, order: 16 }, { attendanceItemId: 296, order: 17 },
{ attendanceItemId:301, order: 18 },{ attendanceItemId: 306, order: 19 }, { attendanceItemId: 311, order: 20}, { attendanceItemId: 438, order: 21 }, { attendanceItemId: 443, order: 22 }, { attendanceItemId: 448, order: 23 }, { attendanceItemId: 453, order: 24 }, { attendanceItemId: 458, order: 25 },
{ attendanceItemId: 532, order: 26 },{ attendanceItemId: 535, order:27 }, { attendanceItemId: 559, order: 28 },{ attendanceItemId:592, order: 29 }, { attendanceItemId: 598, order: 30 },{attendanceItemId: 604, order: 31}, { attendanceItemId: 610, order: 32 }, { attendanceItemId: 641, order:33 },
{ attendanceItemId:642, order: 34 }, { attendanceItemId: 643, order: 35 },{attendanceItemId: 644, order: 36}, { attendanceItemId: 645, order: 37 }, { attendanceItemId: 646, order:38 },{attendanceItemId: 647, order: 39}, { attendanceItemId: 648, order: 40 },{ attendanceItemId: 649, order:41 },
{ attendanceItemId: 650, order: 42 }, { attendanceItemId: 651, order:43 },{attendanceItemId: 652, order: 44}, { attendanceItemId: 653, order: 45 },{ attendanceItemId: 654, order:46 }, { attendanceItemId: 655, order: 47 }, { attendanceItemId:656, order: 48 },{ attendanceItemId: 657, order: 49 },
{ attendanceItemId: 658, order: 50 },{ attendanceItemId: 659, order:51 }, { attendanceItemId: 660, order: 52 }, { attendanceItemId:661, order: 53 },{ attendanceItemId: 662, order: 54 }, { attendanceItemId: 663, order:55 }, { attendanceItemId:664, order: 56 }, { attendanceItemId: 665, order: 57 },
{ attendanceItemId: 666, order: 58 },{ attendanceItemId: 667, order: 59 }, { attendanceItemId: 668, order: 60 }, { attendanceItemId: 669, order: 61 }, { attendanceItemId:670, order: 62 }, { attendanceItemId: 671, order: 63}, { attendanceItemId: 672, order: 64 }, { attendanceItemId: 673, order: 65 },
{ attendanceItemId:674, order: 66 },{ attendanceItemId: 675, order: 67 }, { attendanceItemId: 676, order:68}, { attendanceItemId:677, order: 69 }, { attendanceItemId: 678, order: 70 },{ attendanceItemId: 679, order: 71}, { attendanceItemId: 680, order: 72 }, { attendanceItemId: 681, order: 73 },
{ attendanceItemId: 682, order: 74 }, { attendanceItemId: 683, order: 75 }, { attendanceItemId: 684, order: 76 }, { attendanceItemId:685, order: 77 },{ attendanceItemId: 686, order:78 }, { attendanceItemId: 687, order: 79}, { attendanceItemId:688, order: 80 },{ attendanceItemId: 689, order: 81 },
{ attendanceItemId: 690, order: 82 },{ attendanceItemId: 691, order:83 }, { attendanceItemId: 692, order: 84 }, { attendanceItemId: 693, order: 85 },{attendanceItemId: 694, order: 86}, { attendanceItemId: 695, order: 87 }, { attendanceItemId:696, order: 88 }, { attendanceItemId: 697, order:89 },
{ attendanceItemId: 698, order: 90 },{ attendanceItemId: 699, order: 91 },{attendanceItemId: 700, order:92}, { attendanceItemId:701, order: 93 }, { attendanceItemId: 702, order:94 }, { attendanceItemId: 703, order: 95}, { attendanceItemId: 704, order: 96 }, {attendanceItemId: 705, order: 97},
{ attendanceItemId:706, order: 98 }, { attendanceItemId: 707, order:99 }, { attendanceItemId: 708, order: 100}, { attendanceItemId: 709, order: 101 },{ attendanceItemId: 710, order: 102 },{attendanceItemId: 711, order: 103}, { attendanceItemId:712, order: 104 }, { attendanceItemId: 713, order: 105 },
{ attendanceItemId: 714, order: 106 },{ attendanceItemId: 715, order: 107 }, { attendanceItemId: 716, order: 108 }, { attendanceItemId:717, order: 109 },{attendanceItemId: 718, order: 110}, { attendanceItemId: 719, order: 111}, { attendanceItemId: 720, order: 112 }, { attendanceItemId: 721, order: 113 },
{ attendanceItemId:722, order: 114 }, { attendanceItemId: 723, order: 115 }, { attendanceItemId: 724, order: 116}, { attendanceItemId: 725, order: 117 }, { attendanceItemId: 726, order: 118 },{attendanceItemId: 727, order: 119}, { attendanceItemId: 728, order: 120 }, { attendanceItemId: 729, order:121 },
{ attendanceItemId: 730, order: 122 }, { attendanceItemId: 731, order: 123 },{attendanceItemId: 732, order: 124}, { attendanceItemId: 733, order: 125 }, { attendanceItemId: 734, order:126 }, { attendanceItemId: 735, order: 127 }, { attendanceItemId: 736, order: 128 },{ attendanceItemId: 737, order:129 },
{ attendanceItemId:738, order: 130 }, { attendanceItemId: 739, order:131 }, { attendanceItemId: 740, order:132 }, { attendanceItemId: 801, order: 133 },{ attendanceItemId: 806, order: 134 }, { attendanceItemId: 811, order: 135 }, { attendanceItemId:816, order: 136 }, { attendanceItemId: 821, order: 137 }],
itemValues: [
	{ itemId: 216, value: 216, valueType: 0, layoutCode: null },			{ itemId: 221, value: 221, valueType: 0, layoutCode: null }, { itemId: 226, value: 226, valueType: 0, layoutCode: null }, { itemId: 231, value: 231, valueType: 0, layoutCode: null },
	{ itemId: 236, value: 236, valueType: 0, layoutCode: null }, { itemId: 241,  value: 241, valueType: 0, layoutCode: null }, { itemId: 246, value: 246, valueType: 0, layoutCode: null }, {itemId: 251, value: 251, valueType: 0, layoutCode: null },
	{ itemId: 256, value: 256, valueType: 0, layoutCode: null }, { itemId: 261, value: 261, valueType: 0, layoutCode: null }, { itemId: 266, value: 266, valueType: 0, layoutCode: null }, { itemId: 271, value: 271, valueType: 0, layoutCode: null },
	{ itemId: 276, value: 276, valueType: 0, layoutCode: null},	{ itemId: 281, value: 281, valueType: 0, layoutCode: null }, { itemId: 286, value: 286, valueType: 0, layoutCode: null },			{itemId: 291,  value: 291, valueType: 0, layoutCode: null},
	{ itemId: 296, value: 296, valueType: 0, layoutCode: null }, { itemId: 301, value: 301, valueType: 0, layoutCode: null }, { itemId: 306, value: 306, valueType: 0, layoutCode: null }, { itemId: 311, value: 311, valueType: 0, layoutCode: null },
	{ itemId: 438,  value: 438, valueType: 0, layoutCode: null }, { itemId: 443, value: 443, valueType: 0, layoutCode: null }, { itemId: 448, value: 448, valueType: 0, layoutCode: null }, { itemId: 453,  value: 453, valueType: 0, layoutCode: null },
	{ itemId: 458, value: 458, valueType: 0, layoutCode: null }, { itemId: 532,  value: 532, valueType: 0, layoutCode: null }, { itemId: 535, value: 535, valueType: 0, layoutCode: null }, { itemId: 559, value: 559, valueType: 0, layoutCode: null },
	{ itemId: 592,  value: 592, valueType: 0, layoutCode: null }, { itemId: 598, value: 598, valueType: 0, layoutCode: null }, { itemId: 604, value: 604, valueType: 0, layoutCode: null},			{ itemId: 610,  value: 610, valueType: 0, layoutCode: null },
	{ itemId: 641,  value: 641, valueType: 0, layoutCode: null }, { itemId: 642, value: 642, valueType: 0, layoutCode: null },			{ itemId: 643, value: 643, valueType: 0, layoutCode: null},			{ itemId: 644, value: 644, valueType: 0, layoutCode: null },
	{ itemId: 645, value: 645, valueType: 0, layoutCode: null }, { itemId: 646, value: 646, valueType: 0, layoutCode: null },			{ itemId: 647, value: 647, valueType: 0, layoutCode: null }, { itemId: 648, value: 648, valueType: 0, layoutCode: null },
	{ itemId: 649,  value: 649, valueType: 0, layoutCode: null }, {itemId: 650, value: 650, valueType: 0, layoutCode: null }, { itemId: 651, value: 651, valueType: 0, layoutCode: null }, { itemId: 652,  value: 652, valueType: 0, layoutCode: null },
	{ itemId: 653, value: 653, valueType: 0, layoutCode: null }, { itemId: 654, value: 654, valueType: 0, layoutCode: null }, { itemId: 655, value: 655, valueType: 0, layoutCode: null},			{ itemId: 656, value: 656, valueType: 0, layoutCode: null },
	{ itemId: 657, value: 657, valueType: 0, layoutCode: null }, { itemId: 658, value: 658, valueType: 0, layoutCode: null },			{ itemId: 659, value: 659, valueType: 0, layoutCode: null }, { itemId: 660, value: 660, valueType: 0, layoutCode: null },
	{ itemId: 661, value: 661, valueType: 0, layoutCode: null }, { itemId: 662, value: 662, valueType: 0, layoutCode: null }, { itemId: 663, value: 663, valueType: 0, layoutCode: null }, { itemId: 664, value: 664, valueType: 0, layoutCode: null },
	{ itemId: 665,  value: 665, valueType: 0, layoutCode: null }, { itemId: 666, value: 666, valueType: 0, layoutCode: null },			{ itemId: 667, value: 667, valueType: 0, layoutCode: null},			{ itemId: 668,  value: 668, valueType: 0, layoutCode: null },
	{ itemId: 669, value: 669, valueType: 0, layoutCode: null }, {itemId: 670, value: 670, valueType: 0, layoutCode: null },			{ itemId: 671, value: 671, valueType: 0, layoutCode: null},			{ itemId: 672, value: 672, valueType: 0, layoutCode: null },
	{ itemId: 673,  value: 673, valueType: 0, layoutCode: null }, { itemId: 674, value: 674, valueType: 0, layoutCode: null },			{ itemId: 675, value: 675, valueType: 0, layoutCode: null }, { itemId: 676,  value: 676, valueType: 0, layoutCode: null },
	{ itemId: 677, value: 677, valueType: 0, layoutCode: null }, { itemId: 678, value: 678, valueType: 0, layoutCode: null }, { itemId: 679, value: 679, valueType: 0, layoutCode: null},			{ itemId: 680,  value: 680, valueType: 0, layoutCode: null },
	{ itemId: 681,  value: 681, valueType: 0, layoutCode: null }, { itemId: 682, value: 682, valueType: 0, layoutCode: null }, { itemId: 683, value: 683, valueType: 0, layoutCode: null},			{ itemId: 684,  value: 684, valueType: 0, layoutCode: null },
	{ itemId: 685, value: 685, valueType: 0, layoutCode: null }, { itemId: 686, value: 686, valueType: 0, layoutCode: null }, { itemId: 687, value: 687, valueType: 0, layoutCode: null }, { itemId: 688, value: 688, valueType: 0, layoutCode: null },
	{ itemId: 689, value: 689, valueType: 0, layoutCode: null }, { itemId: 690, value: 690, valueType: 0, layoutCode: null },			{ itemId: 691, value: 691, valueType: 0, layoutCode: null }, { itemId: 692,  value: 692, valueType: 0, layoutCode: null },
	{ itemId: 693,  value: 693, valueType: 0, layoutCode: null }, { itemId: 694, value: 694, valueType: 0, layoutCode: null }, { itemId: 695, value: 695, valueType: 0, layoutCode: null }, { itemId: 696,  value: 696, valueType: 0, layoutCode: null },
	{ itemId: 697,  value: 697, valueType: 0, layoutCode: null }, { itemId: 698,  value: 698, valueType: 0, layoutCode: null }, { itemId: 699, value: 699, valueType: 0, layoutCode: null},			{ itemId: 700,  value: 700, valueType: 0, layoutCode: null },
	{ itemId: 701, value: 701, valueType: 0, layoutCode: null },			{ itemId: 702, value: 702, valueType: 0, layoutCode: null },			{ itemId: 703, value: 703, valueType: 0, layoutCode: null }, { itemId: 704, value: 704, valueType: 0, layoutCode: null },
	{ itemId: 705, value: 705, valueType: 0, layoutCode: null }, {itemId: 706, value: 706, valueType: 0, layoutCode: null }, { itemId: 707, value: 707, valueType: 0, layoutCode: null }, { itemId: 708, value: 708, valueType: 0, layoutCode: null },
	{ itemId: 709,  value: 709, valueType: 0, layoutCode: null }, { itemId: 710, value: 710, valueType: 0, layoutCode: null }, { itemId: 711, value: 711, valueType: 0, layoutCode: null }, { itemId: 712,  value: 712, valueType: 0, layoutCode: null },
	{ itemId: 713, value: 713, valueType: 0, layoutCode: null }, { itemId: 714, value: 714, valueType: 0, layoutCode: null }, { itemId: 715, value: 715, valueType: 0, layoutCode: null},			{ itemId: 716, value: 716, valueType: 0, layoutCode: null },
	{ itemId: 717, value: 717, valueType: 0, layoutCode: null }, { itemId: 718, value: 718, valueType: 0, layoutCode: null }, { itemId: 719, value: 719, valueType: 0, layoutCode: null },			{ itemId: 720, value: 720, valueType: 0, layoutCode: null },
	{ itemId: 721, value: 721, valueType: 0, layoutCode: null }, { itemId: 722, value: 722, valueType: 0, layoutCode: null },			{ itemId: 723, value: 723, valueType: 0, layoutCode: null},			{ itemId: 724, value: 724, valueType: 0, layoutCode: null },
	{ itemId: 725, value: 725, valueType: 0, layoutCode: null }, {itemId: 726, value: 726, valueType: 0, layoutCode: null }, { itemId: 727, value: 727, valueType: 0, layoutCode: null }, { itemId: 728,  value: 728, valueType: 0, layoutCode: null },
	{ itemId: 729,  value: 729, valueType: 0, layoutCode: null }, { itemId: 730, value: 730, valueType: 0, layoutCode: null }, { itemId: 731, value: 731, valueType: 0, layoutCode: null }, { itemId: 732,  value: 732, valueType: 0, layoutCode: null },
	{ itemId: 733,  value: 733, valueType: 0, layoutCode: null }, { itemId: 734, value: 734, valueType: 0, layoutCode: null }, { itemId: 735, value: 735, valueType: 0, layoutCode: null},			{ itemId: 736, value: 736, valueType: 0, layoutCode: null },
	{ itemId: 737, value: 737, valueType: 0, layoutCode: null }, { itemId: 738, value: 738, valueType: 0, layoutCode: null },			{ itemId: 739, value: 739, valueType: 0, layoutCode: null }, { itemId: 740, value: 740, valueType: 0, layoutCode: null },
	{ itemId: 801, value: 801, valueType: 0, layoutCode: null }, {itemId: 806, value: 806, valueType: 0, layoutCode: null }, { itemId: 811, value: 811, valueType: 0, layoutCode: null }, { itemId: 816, value: 816, valueType: 0, layoutCode: null },
	{ itemId: 821,  value: 821, valueType: 0, layoutCode: null }],
	lockInfos: null
	}

let attendentItems = [
	216, 221, 226, 231, 236, 241, 246, 251,
	256, 261, 266, 271, 276, 281, 286, 291,
	296, 301, 306, 311, 438, 443, 448, 453,
	458, 532, 535, 559, 592, 598, 604, 610, 
	41, 642, 643, 644, 645, 646, 647, 648,
	649, 650, 651, 652, 653, 654, 655, 656,
	657, 658, 659, 660, 661, 662, 663, 664,
	665, 666, 667, 668, 669, 670, 671, 672, 
	3, 674, 675, 676, 677, 678, 679, 680, 
	681, 682, 683, 684, 685, 686, 687, 688, 
	89, 690, 691, 692, 693, 694, 695, 696,
	697, 698, 699, 700, 701, 702, 703, 704,
	705, 706, 707, 708, 709, 710, 711, 712,
	713, 714, 715, 716, 717, 718, 719, 720,
	721, 722, 723, 724, 725, 726, 727, 728,
	729, 730, 731, 732, 733, 734, 735, 736, 
	37, 738, 739, 740, 801, 806, 811, 816, 821];

__viewContext.ready(function() {
	var screenModel = new viewmodel.ScreenModel();
	screenModel.startPage().done(function() {
		__viewContext.bind(screenModel);
	});
});
}
