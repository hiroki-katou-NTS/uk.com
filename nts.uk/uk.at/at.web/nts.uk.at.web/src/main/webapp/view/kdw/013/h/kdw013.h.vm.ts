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
			itemId28: KnockoutObservable<string> = ko.observable('H2_3');
			itemId29: KnockoutObservable<string> = ko.observable('H2_6');

			itemId31_34: StartEndTime = new StartEndTime();

			itemId157_159: StartEndTime = new StartEndTime();
			itemId163_165: StartEndTime = new StartEndTime();
			itemId169_171: StartEndTime = new StartEndTime();
			itemId175_177: StartEndTime = new StartEndTime();
			itemId181_183: StartEndTime = new StartEndTime();
			itemId187_189: StartEndTime = new StartEndTime();
			itemId193_195: StartEndTime = new StartEndTime();
			itemId199_201: StartEndTime = new StartEndTime();
			itemId205_207: StartEndTime = new StartEndTime();
			itemId211_213: StartEndTime = new StartEndTime();

			breakTimeBase: StartEndTime[] = [this.itemId157_159, this.itemId163_165]
			breakTimeOptions: StartEndTime[] = [this.itemId169_171, this.itemId181_183,
												this.itemId187_189, this.itemId193_195,
												this.itemId199_201, this.itemId205_207,
												this.itemId205_207, this.itemId211_213];

			isShowBreakTimeOptions: KnockoutObservable<boolean> = ko.observable(false);

			itemOptions: KnockoutObservableArray<ItemValue> = ko.observableArray([]);

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
				dfd.resolve();
				return dfd.promise();
			}
			fakeData(): void {
				let self = this;
				let tg: ItemValue[] = [];
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
					tg.push(new ItemValue({itemId: id, value: '', valueType: 0, layoutCode: ''}, 'Item ' + id, 7));	
				});
				self.itemOptions(tg);
			}

			registration() {
				let self = this;
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
		start: KnockoutObservable<number> = ko.observable(null);
		end: KnockoutObservable<number> = ko.observable(null);
		constructor() {
			this.end.subscribe((value) => {
			});
		}
	}
	class ItemValue {
		itemId: number;
		lable: KnockoutObservable<string> = ko.observable('');
		use: KnockoutObservable<boolean> = ko.observable(true);
		type: number;
		value: KnockoutObservable<string> = ko.observable(null);
		options: KnockoutObservableArray<any> = ko.observableArray([]);
		valueBeforeChange: any;
		valueType: number;
		layoutCode: string;
		constructor(itemValue: IItemValue, name?: string, type?: number) {
			this.itemId = itemValue.itemId;
			this.value(itemValue.value);
			this.valueBeforeChange = itemValue.value;
			this.valueType = itemValue.valueType;
			this.layoutCode = itemValue.layoutCode;
			this.lable(name);
			this.type = type;
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
