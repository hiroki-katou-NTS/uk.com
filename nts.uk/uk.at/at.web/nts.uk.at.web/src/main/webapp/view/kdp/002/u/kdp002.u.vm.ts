/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.kdp002.u {

	const API = {
	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		model: KnockoutObservableArray<IModel> = ko.observableArray([]);

		created() {
			const vm = this;
			const data1: IModel = {
				message: '休暇を取得する場合は、休暇申請をおこなってください。詳細が不明な場合は、内線：101　日通までお知らせください。',
				URL: 'http://jhvnsnvjskv.co.jp',
				author: '日通　A子',
				time: '7/5～7/10'
			};

			vm.model.push(data1);
			vm.model.push(data1);
			vm.model.push(data1);
			vm.model.push(data1);
			vm.model.push(data1);
			vm.model.push(data1);
		}

		closeDialog() {
			const vm = this;

			vm.$window.close();
		}
	}



	interface IModel {
		message: string;
		URL?: string;
		author: string;
		time: string;
	}
}
