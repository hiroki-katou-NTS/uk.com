 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.c.viewmodel {
	
	@bean()
	class Kaf018CViewModel extends ko.ViewModel {
		closureId: number = 0;
		closureName: string = '対象締め';
		items: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
			new model.ItemModel('1', '1', '基本給'),
			new model.ItemModel('2', '2', '基本給'),
			new model.ItemModel('3', '3', '基本給'),
		]);
		currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
		textEditor: KnockoutObservable<string> = ko.observable('');
		textArea: KnockoutObservable<string> = ko.observable('');
		selectedIds: KnockoutObservableArray<number> = ko.observableArray([1,2]);
		
		created(params: any) {
			const vm = this;
			vm.closureName = 'closureName';
		}
		
		getTargetDate(): string {
			const vm = this;
//			let startDate = nts.uk.time.formatDate(new Date(self.startDate), 'yyyy/MM/dd');
//			let endDate = nts.uk.time.formatDate(new Date(self.endDate), 'yyyy/MM/dd');
//			return self.processingYm + " (" + startDate + " ～ " + endDate + ")";
			return "Date Range";
		}
		
		sendMail() {
			
		}
		
		close() {
			const vm = this;
			vm.$window.close();
		}
	}

	export module model {
		export class TableItem {
			wkpName: string;	
			numberPeople: number;
			appInfo: number;
			constructor(wkpName: string, numberPeople: number, appInfo: number) {
				this.wkpName = wkpName;
				this.numberPeople = numberPeople;
				this.appInfo = appInfo;
			}
		}
		
		export class ItemModel {
			code: string;
			name: string;
			description: string;
			other1: string;
			other2: string;
			constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
				this.code = code;
				this.name = name;
				this.description = description;
				this.other1 = other1;
				this.other2 = other2 || other1;
			}
		}
	}

	const API = {
		getStatusExecution: "at/request/application/approvalstatus/getStatusExecution"
	}
}