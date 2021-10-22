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
			start: "at/record/stamp/timestampinputsetting/saveStampPage"
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

			params: any;

			constructor() {
				let self = this;
				self.params = getShared('KDW013H');
				self.fakeData();
			}
			
			public startPage(): JQueryPromise<any> {
				let self = this;
				let dfd = $.Deferred();
				/*block.invisible();
				ajax(paths.getSettingCommonStamp).done(function(data: any) {
					self.settingsStampUse = data; 
					if(!data.supportUse){
						self.optionPopup([{ code: 1, name: getText("KDP010_336")}]);
					}
					
				}).fail(function(res:any) {
					error({ messageId: res.messageId });
				}).always(() => {
					block.clear();
				});*/
				self.breakTimeBase.push(this.itemId157_159, this.itemId163_165);
				self.breakTimeOptions.push(this.itemId169_171, this.itemId181_183,
											this.itemId187_189, this.itemId193_195,
											this.itemId199_201, this.itemId205_207,
											this.itemId205_207, this.itemId211_213);
				dfd.resolve();
				return dfd.promise();
			}
			
			fakeData(): void {
				let self = this;
				
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
				
				let attendentItems = [
					216, 221, 226, 231, 236, 241, 246, 251, 
					256, 261, 266, 271, 276, 281, 286, 291, 
					296, 301, 306, 311, 439, 444, 449, 454, 
					459, 532, 535, 559, 592, 598, 604, 610, 
					641, 642, 643, 644, 645, 646, 647, 648, 
					649, 650, 651, 652, 653, 654, 655, 656, 
					657, 658, 659, 660, 661, 662, 663, 664, 
					665, 666, 667, 668, 669, 670, 671, 672, 
					673, 674, 675, 676, 677, 678, 679, 680, 
					681, 682, 683, 684, 685, 686, 687, 688, 
					689, 690, 691, 692, 693, 694, 695, 696, 
					697, 698, 699, 700, 701, 702, 703, 704, 
					705, 706, 707, 708, 709, 710, 711, 712, 
					713, 714, 715, 716, 717, 718, 719, 720, 
					721, 722, 723, 724, 725, 726, 727, 728, 
					729, 730, 731, 732, 733, 734, 735, 736, 
					737, 738, 739, 740, 802, 807, 812, 817, 822];
				_.forEach(attendentItems, (id)=>{
					self.itemOptions.push(new ItemValueOption({itemId: id, value: '', valueType: 0, layoutCode: ''}, true, 'Item ' + id, 7));	
				});
			}

			registration() {
				let self = this;
				let data: IItemValue[] = [];
				
				if(self.itemId28.isChange()){
					data.push(self.itemId28.toDataSave());	
				}
				
				if(self.itemId29.isChange()){
					data.push(self.itemId29.toDataSave());	
				}
				
				self.itemId31_34.toDataSave().forEach((e) => data.push(e));
				
				self.breakTimeBase.forEach((startEnd)=>{
					startEnd.toDataSave().forEach((e) => data.push(e));
				});
				
				self.breakTimeOptions.forEach((startEnd)=>{
					startEnd.toDataSave().forEach((e) => data.push(e));
				});
				
				self.itemOptions.forEach((item)=>{
					if(item.use() && item.isChange()){
						data.push(item.toDataSave());	
					}
				});
				
				console.log(data);
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
			this.start = new ItemValue(start, breakTimeNo ? getText('KDW013_88', [breakTimeNo]): undefined);
			this.end = new ItemValue(end, breakTimeNo ? getText('KDW013_89', [breakTimeNo]): undefined);
			this.end.value.subscribe(() => {
			});
		}
		toDataSave(): IItemValue[]{
			let result: IItemValue[] = [];
			if(this.start.isChange()){
				result.push(this.start.toDataSave());
			}
			if(this.end.isChange()){
				result.push(this.end.toDataSave());
			}
			return result;
		}
	}
	class ItemValue {
		itemId: number;
		value: KnockoutObservable<string>;
		valueBeforeChange: any;
		valueType: number;
		layoutCode: string;
		name: string; //only use for break times
		constructor(itemValue: IItemValue, name? : string) {
			this.itemId = itemValue.itemId;
			this.value = ko.observable(itemValue.value);
			this.valueBeforeChange = itemValue.value;
			this.valueType = itemValue.valueType;
			this.layoutCode = itemValue.layoutCode;
			this.name = name;
		}
		toDataSave(): IItemValue{
			return {
				itemId: this.itemId,
				value: this.value(),
				valueType: this.valueType,
				layoutCode: this.layoutCode
			};
		}
		isChange(): boolean{
			return this.value() == this.valueBeforeChange;
		}
	}
	
	class ItemValueOption extends ItemValue{
		lable: KnockoutObservable<string> = ko.observable('');
		use: KnockoutObservable<boolean> = ko.observable(false);
		type: number;
		options: KnockoutObservableArray<any> = ko.observableArray([]);
		constructor(itemValue: IItemValue, use: boolean, name?: string, type?: number) {
			super(itemValue);
			if(use){
				this.use(use);
				this.layoutCode = itemValue.layoutCode;
				this.lable(name);
				this.type = type;	
			}
		}
	}
	type IItemValue = {
		itemId: number;
		value: any;
		valueType: number;
		layoutCode: string;
	}
	__viewContext.ready(function() {
		var screenModel = new viewmodel.ScreenModel();
		screenModel.startPage().done(function() {
			__viewContext.bind(screenModel);
		});
	});
}
