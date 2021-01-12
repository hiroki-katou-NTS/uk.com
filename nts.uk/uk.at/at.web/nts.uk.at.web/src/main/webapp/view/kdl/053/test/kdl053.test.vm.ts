module nts.uk.at.view.kdl053.test {
	import setShare = nts.uk.ui.windows.setShared;
	// Import
	export module viewmodel {
		export class ScreenModel {	
			currentScreen: any = null;	
			columns: KnockoutObservableArray<NtsGridListColumn>;

			items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);;		
			currentCode: KnockoutObservable<any> = ko.observable();
			currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);;
			count: number = 100;
			// switchOptions: KnockoutObservableArray<any>;

			itemList: KnockoutObservableArray<any>;
			selectedId: KnockoutObservable<number>;
			enable: KnockoutObservable<boolean>;

			errorRegistrations = ko.observable( [
						{id: 0, employeeCdName:'000001 天海　春香',  date:'2020/12/08', errId: 163, errMsg: '開始時刻と終了時刻が逆転してします。'}, 
                        {id: 1,employeeCdName:'000001 天海　春香', date:'2020/12/09', errId: 195, errMsg: '開始時刻と終了時刻が逆転してします。'}, 
                        {id: 2,employeeCdName:'000001 天海　春香', date:'2020/12/10', errId: 201, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 3,employeeCdName:'000001 天海　春香', date:'2020/12/11', errId: 205, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 4,employeeCdName:'000001 天海　春香', date:'2020/12/12', errId: 537, errMsg: '開始時刻と終了時刻が逆転してします。' },                  
                        {id: 5,employeeCdName:'000001 天海　春香', date:'2020/12/13', errId: 165, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 6,employeeCdName:'000002 新規　ログ', date:'2020/12/11', errId: 165, errMsg: '開始時刻と終了時刻が逆転してします。'},                        
                        {id: 7,employeeCdName:'000002 新規　ログ', date:'2020/12/12', errId: 195, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 8,employeeCdName:'000002 新規　ログ', date:'2020/12/13', errId: 201, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 9,employeeCdName:'000012 承認者　課長', date:'2020/12/08', errId: 205, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 10,employeeCdName:'000012 承認者　課長', date:'2020/12/09', errId: 537, errMsg: '開始時刻と終了時刻が逆転してします。' },
                        {id: 11,employeeCdName:'000012 承認者　課長', date:'2020/12/13', errId: 537, errMsg: '開始時刻と終了時刻が逆転してします。'}
					]);
			employeeIds: KnockoutObservableArray<string> = ko.observableArray([
				'000001', '000002','000012'
			]);			
			
			constructor() {
				var self = this;
				self.columns = ko.observableArray([
					{ key: 'id',width:20, hidden: true },
					{ headerText: '社員コード／名称', key: 'code', width: 180 },
					{ headerText: '日付', key: 'date', width: 150 }, 
					{ headerText: '項目ID', key: 'errId', width: 80 }, 
					{ headerText: 'メッセージ', key: 'msg', width: 280}					
				]);
				_.each(self.errorRegistrations(), err =>{
					self.items.push(new ItemModel(err.id, err.employeeCdName, err.date, err.errId, err.errMsg));
				})	

				self.itemList = ko.observableArray([
					new BoxModel(1, 'Yes'),
					new BoxModel(0, 'No')
				
				]);
				self.selectedId = ko.observable(1);
				self.enable = ko.observable(true);
			}
		

			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();

				dfd.resolve();
				return dfd.promise();
			}

			openDialog(): void {
				let self = this;
				let request: any = {};				
				let errorRegistrationList: any = [];
				
				request.isRegistered = self.selectedId();
				request.employeeIds = self.employeeIds();
				
				_.each(self.currentCodeList(), id =>{
					errorRegistrationList.push(self.errorRegistrations()[id]);
				})
				request.errorRegistrationList = errorRegistrationList;
				setShare('dataShareDialogKDL053', request);
				self.currentScreen = nts.uk.ui.windows.sub.modal('/view/kdl/053/index.xhtml');
			}

			// selectSomeItems() {
			// 	this.currentCode('0010');
			// 	this.currentCodeList.removeAll();
			// 	this.currentCodeList.push('001');
			// 	this.currentCodeList.push('002');
			// }
			
			// deselectAll() {
			// 	this.currentCode(null);
			// 	this.currentCodeList.removeAll();
			// }
			
			// addItem() {
			// 	this.items.push(new ItemModel(this.count.toString(), '基本給',  this.count, ""+this.count));
			// 	this.count;
			// }
			
			// removeItem() {
			// 	this.items.shift();
			// }
		}	
		class ItemModel {
			id: number;
			code: string;
			date: string;
			errId: number;
			msg: string;			
			constructor(id: number, code: string, date: string, errId: number, msg: string) {
				this.id = id;
				this.code = code;
				this.date = date;
				this.errId = errId;
				this.msg = msg;				
			}
		}

		class BoxModel {
			id: number;
			name: string;
			constructor(id: number, name: string){
				var self = this;
				self.id = id;
				self.name = name;
			}
		}
	}	
}