module nts.uk.at.view.kdw013.h {
	import getShared = nts.uk.ui.windows.getShared;
	import setShared = nts.uk.ui.windows.setShared;
	import block = nts.uk.ui.block;
	import info = nts.uk.ui.dialog.info;
	import error = nts.uk.ui.dialog.error;
	import errors = nts.uk.ui.errors;
	import ajax = nts.uk.request.ajax;
	import getText = nts.uk.resource.getText;

	export module viewmodel {
		const paths: any = {
			start: "screen/at/kdw013/h/start",
			save: "screen/at/kdw013/h/save",
			getWorkPlaceId: "screen/at/kdw013/h/getWorkPlaceId",
			getWorkType: "screen/at/kdw013/h/getWorkType"
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
			primitiveValueDaily: any[] = __viewContext.enums.PrimitiveValueDaily;
			
			errorLable: KnockoutObservable<string> = ko.observable('');

			constructor() {
				let self = this;
				self.params = getShared('KDW013H');
				self.setError();
				self.setBaseItems();// data co dinh
				self.orderOptionItems(); // data item tuy y
				
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
				let param = _.map(self.params.displayAttItems, i => i.attendanceItemId);
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
			
			setError(){
				let self = this;
				let infor: any = _.find(self.params.lockInfos, (i:DailyLock)=> moment(self.params.date).isSame(moment(i.date)));
				if(infor){
					let err: string = null;
					if(infor.lockPast = 1){
						err = getText('KDW013_53');
					}
					if(infor.lockWpl = 1){
						err = err ? (err + ',' + getText('KDW013_54')) : getText('KDW013_54');
					}
					if(infor.lockApprovalMontｈ = 1){
						err = err ? (err + ',' + getText('KDW013_55')) : getText('KDW013_55');
					}
					if(infor.lockConfirmMonth = 1){
						err = err ? (err + ',' + getText('KDW013_56')) : getText('KDW013_56');
					}
					if(infor.lockApprovalDay = 1){
						err = err ? (err + ',' + getText('KDW013_57')) : getText('KDW013_57');
					}
					if(infor.lockConfirmDay = 1){
						err = err ? (err + ',' + getText('KDW013_58')) : getText('KDW013_58');
					}
					if(infor.lockDailyResult = 1){
						err = err ? (err + ',' + getText('KDW013_59')) : getText('KDW013_59');
					}
					if(err){
						self.errorLable(getText('KDW013_52', [err]));
					}
				}
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
					item.setUseNameType(use, itemName ? itemName.displayName : null, itemType ? itemType.dailyAttendanceAtr : null, self.getPrimitiveValue(itemType ? itemType.primitiveValue: null));
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
			
			getPrimitiveValue(primitiveValue: number): string {
				if(primitiveValue){
					return _.find(this.primitiveValueDaily, (p: any) => p.value == primitiveValue).name.replace('Enum_PrimitiveValueDaily_','');	
				}
				return '';
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

			setBaseItems(): void {
				let self = this;

				//fake data các item co dinh
				self.itemId28 = new ItemValue(_.find(self.params.itemValues, i => i.itemId == 28));
				self.itemId29 = new ItemValue(_.find(self.params.itemValues, i => i.itemId == 29));

				self.itemId31_34 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 31), _.find(self.params.itemValues, i => i.itemId == 34));

				self.itemId157_159 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 157), _.find(self.params.itemValues, i => i.itemId == 159), 1);
				self.itemId163_165 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 163), _.find(self.params.itemValues, i => i.itemId == 165), 2);

				self.itemId169_171 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 169), _.find(self.params.itemValues, i => i.itemId == 171), 3);
				self.itemId175_177 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 175), _.find(self.params.itemValues, i => i.itemId == 177), 4);
				self.itemId181_183 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 181), _.find(self.params.itemValues, i => i.itemId == 183), 5);
				self.itemId187_189 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 187), _.find(self.params.itemValues, i => i.itemId == 189), 6);
				self.itemId193_195 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 193), _.find(self.params.itemValues, i => i.itemId == 195), 7);
				self.itemId199_201 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 199), _.find(self.params.itemValues, i => i.itemId == 201), 8);
				self.itemId205_207 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 205), _.find(self.params.itemValues, i => i.itemId == 207), 9);
				self.itemId211_213 = new StartEndTime(_.find(self.params.itemValues, i => i.itemId == 211), _.find(self.params.itemValues, i => i.itemId == 213), 10);

			}
			
			openKdl001() {
                var self = this;
                setShared('kml001multiSelectMode', false);
                setShared('kml001selectAbleCodeList', []);
                setShared('kml001selectedCodeList', self.itemId29.value() ? [self.itemId29.value()]: []);
                setShared('kml001isSelection', true);
                setShared('kml001BaseDate', self.params.date);
				block.grayout();
				ajax(paths.getWorkPlaceId, { employeeId: self.params.employeeId, date: moment(self.params.date) }).done(function(data: any) {
					setShared('kml001WorkPlaceId', data ? data.workPlaceId: null);
					nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml").onClosed(function () {
	                    const kml001selectedCodeList = getShared("kml001selectedCodeList");
							if(kml001selectedCodeList[0] && kml001selectedCodeList[0] != ''){
								self.itemId29.value(kml001selectedCodeList[0]);
								let workTime = _.find(self.dataMaster.workTimeSettings, w => w.worktimeCode == self.itemId29.value());
								if (workTime) {
									self.itemId29.itemSelectedDisplay(self.itemId29.value() + ' ' + workTime.workTimeDisplayName.workTimeName);
								} else {
									self.itemId29.itemSelectedDisplay(self.itemId29.value() + ' ' + getText('KDW013_40'));
								}	
							}
	                    console.log(kml001selectedCodeList);
	                });
				}).fail(function(res: any) {
					error({ messageId: res.messageId });
				}).always(() => {
					block.clear();
				});
            }

			openKdl002() {
                var self = this;
	            setShared('KDL002_Multiple',false);
	            setShared('kdl002isSelection',true);
	            setShared('KDL002_SelectedItemId',self.itemId28.value() ? [self.itemId28.value()]: []);
	            setShared('KDL002_isShowNoSelectRow', false);
				ajax(paths.getWorkType, { employeeId: self.params.employeeId, date: moment(self.params.date), code: self.itemId28.value() }).done(function(data: any) {
					setShared('KDL002_AllItemObj', data);
		            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml').onClosed(function(): any {
		                var lst = getShared('KDL002_SelectedNewItem');
							if(lst[0] && lst[0] != ''){
								self.itemId28.value(lst[0].code);
								let workType = _.find(self.dataMaster.workTypes, w => w.workTypeCode == self.itemId28.value());
								if (workType){
									self.itemId28.itemSelectedDisplay(self.itemId28.value() + ' ' + workType.name);
								} else {
									self.itemId28.itemSelectedDisplay(self.itemId28.value() + ' ' + getText('KDW013_40'));
								}
							}
		                console.log(lst);
		            });
				});
            }

			registration() {
				let self = this;
				let data: any[] = [];

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
					targetDate: moment(self.params.date), //対象日
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
			let result: any[] = [];
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
		fixed: boolean;
		itemSelectedDisplay: KnockoutObservable<string> = ko.observable('');
		name: string; //only use for break times
		
		constructor(itemValue?: IItemValue, name?: string) {
			this.itemId = itemValue.itemId;
			this.value(itemValue.value);
			this.valueBeforeChange = itemValue.value;
			this.valueType = itemValue.valueType;
			this.layoutCode = itemValue.layoutCode;
			this.name = name;
			this.fixed = itemValue.fixed;
			
		}
		toDataSave() {
			return {
				itemId: this.itemId,
				value: this.value(),
				valueType: this.valueType,
				layoutCode: this.layoutCode,
				isFixed: this.fixed
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
		primitiveValue: string;
		constructor(itemValue: IItemValue) {
			super(itemValue);
		}
		setUseNameType(use: boolean, name?: string, type?: number, primitiveValue?: string) {
			if (use) {
				this.use(use);
				this.lable(name);
				this.type = type;
				this.primitiveValue = primitiveValue;
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
		fixed: boolean;
	}

	type Param = {
		employeeId: string; //対象社員
		date: string; //対象日
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
		date: string;
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


__viewContext.ready(function() {
	var screenModel = new viewmodel.ScreenModel();
	screenModel.startPage().done(function() {
		__viewContext.bind(screenModel);
	});
});
}
