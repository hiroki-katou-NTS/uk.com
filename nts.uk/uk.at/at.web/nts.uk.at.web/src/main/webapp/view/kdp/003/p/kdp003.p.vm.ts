/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kdp003.p {

	const API = {
		GET_NOTICE: '/at/record/stamp/notice/getNoticeByStamping'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		dateValue: KnockoutObservable<DatePeriod> = ko.observable(new DatePeriod({
			startDate: moment.utc().format('YYYY/MM/DD'),
			endDate: moment.utc().format('YYYY/MM/DD')
		  }));
	  
		  itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		  msgNotice: MessageNotice[] = [];
		  role: Role = new Role();

		created(role: Role) {
			const vm = this;
			vm.role = role;
			vm.searchMessage(null);
		  }

		mounted() {
			$('#P20_1').focus();
		}

		searchMessage(param: DatePeriod){
			const vm = this;
			vm.$blockui('show');
			vm.$ajax(API.GET_NOTICE, param).then((response: MessageNotice[]) => {
				if (response) {
				  vm.msgNotice = response;
				  const itemList = _.map(response, msg => new ItemModel({
					creatorID: msg.creatorID,
					inputDate: msg.inputDate,
					ymDisplay: moment.utc(msg.startDate, 'YYYYMMDD').format('M/D').toString()
					  + ' ' + vm.$i18n('KDP003_67').toString() + ' '
					  + moment.utc(msg.endDate, 'YYYYMMDD').format('M/D').toString(),
					content: msg.notificationMessage
				  }));
				  vm.itemList(itemList);
				}
			  })
				.fail(error => this.$dialog.error(error))
				.always(() => vm.$blockui('hide'));
		}

	onClickSearch() {
		const vm = this;
		vm.$errors().then((valid: boolean) => {
		  if (!valid) {
			return;
		  }
		});

	}

	/**
     * P20_1:新規をクリックする
     */
    openScreenCInNewMode(){
		const vm = this;
		vm.$window.modal('at', '/view/kdp/003/q/index.xhtml', {
		  isNewMode: true,
		  role: vm.role,
		  messageNotice: null
		})
		  .then(result => {
			if (result && !result.isClose) {
			  vm.onClickSearch();
			}
		  });
	}

	/**
     * P4_2:対象のリンクラベルをクリックする
     */
    onClickTargetLink(data: ItemModel){
		const vm = this;
		vm.$window.modal('at', '/view/kdp/003/q/index.xhtml', {
		  isNewMode: false,
		  role: vm.role,
		  messageNotice: vm.findMessage(data)
		})
		.then(() => {
			vm.onClickSearch();
		  });
	}

	findMessage(data: ItemModel): MessageNotice {
		return _.find(this.msgNotice, m => m.creatorID === data.creatorID && m.inputDate === data.inputDate);
	  }

	closeDialog() {
			const vm = this;
			vm.$window.close();
		}

	}

	export class Role {
		companyId: string;
		roleId: string;
		roleCode: string;
		roleName: string;
		assignAtr: number;
		employeeReferenceRange: number;
	  }
	
	export class DatePeriod {
		startDate: string;
		endDate: string;
	
		constructor(init?: Partial<DatePeriod>) {
			$.extend(this, init);
		}
	  }
	
	export class ItemModel {
		creatorID: string;
		inputDate: string;
		ymDisplay: string;
		content: string;
	
		constructor(init?: Partial<ItemModel>) {
			$.extend(this, init);
		}
	  }
	
	export class MessageNotice {
		creatorID: string; //作成者ID
		inputDate: string; //入力日
		modifiedDate: string; //更新日
		targetInformation: TargetInformation; //対象情報
		startDate: any; //開始日
		endDate: any; //終了日
		employeeIdSeen: string[]; //対象情報
		notificationMessage: string; //メッセージの内容

		constructor(init?: Partial<MessageNotice>) {
			$.extend(this, init);
		  }
	  }

	export class TargetInformation {
		targetSIDs: string[];
		targetWpids: string[];
		destination: number;
		constructor(init?: Partial<TargetInformation>) {
			$.extend(this, init);
		}
	  }

}