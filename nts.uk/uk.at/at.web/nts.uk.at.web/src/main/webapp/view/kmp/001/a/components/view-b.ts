/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `
<div class="sidebar-content-header">
	<span class="title" data-bind= "text: $i18n('KMP001_2')"></span>
	<button class="proceed" data-bind= "text: $i18n('KMP001_5')"></button>
	<button class="danger" data-bind= "text: $i18n('KMP001_6')"></button>
</div>
<div class="search_label" style="padding-bottom: 0px">
	<span class="sub_title" data-bind= "text: $i18n('KMP001_22')"></span>
	<input data-bind="ntsTextEditor: {value: inputStampCard}" style="width: 225px"/>
	<button id="top_bottom" data-bind= "text: $i18n('KMP001_23'),
										click: getStampCard">
	</button>
	<button id="top_bottom" data-bind= "text: $i18n('KMP001_24'),
										click: getAllStampCard"></button>
</div>
<div class="view-kmp">
	<div class="float-left list-component">
		<div class="caret-right caret-background bg-green" style="padding: 10px;">
			<table id="card-list" 
				data-bind="ntsGridList: {
					height: 300,
					options: items,
					optionsValue: 'stampNumber',
					columns: [
			            { headerText: $i18n('KMP001_22'), prop: 'stampNumber', width: 180 },
			            { headerText: $i18n('KMP001_8'), prop: 'employeeCode', width: 112 },
			            { headerText: $i18n('KMP001_9'), prop: 'businessName', width: 110 }
			        ],
					multiple: false,
					enable: true,
					value: model.stampNumber
				}">
			</table>
		</div>
	</div>
	<div class="float-left model-component">
		<table>
			<tbody>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_22') }"></div>
						</div>
					</td>
					<td class="data">
						<div id="td-bottom" data-bind="text: model.stampNumber"></div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div id=" td-bottom" data-bind="ntsFormLabel: { text: $i18n('KMP001_9'), required: true }"></div>
							<button id="td-bottom" data-bind="text: $i18n('KMP001_26')"></button>
						</div>
					</td>
					<td class="data">
						<div id="td-bottom">
							<div id="td-bottom" data-bind="text: model.employeeCode"></div>
							<div id="td-bottom" style="margin-left: 10px" data-bind="text: employee.businessName"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_20') }"></div>
						</div>
					</td>
					<td class="data">
						<div id="td-bottom" data-bind="text: employee.entryDate"></div>
					</td>
				</tr>
				<tr>
					<td class="label-column-left">
						<div id="td-bottom">
							<div style="border: 0" data-bind="ntsFormLabel: { text: $i18n('KMP001_21') }"></div>
						</div>
					</td>
					<td class="data">
					<div id="td-bottom" data-bind="text: employee.retiredDate"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
`;

interface Params {

}

const KMP001B_API = {
	GET_STAMPCARD: 'screen/pointCardNumber/getEmployeeFromCardNo/',
	GET_ALL_STAMPCARD: 'screen/pointCardNumber/getAllEmployeeFromCardNo/',
	GET_INFO_EMPLOYEE: 'screen/pointCardNumber/getEmployeeInformationViewB/'
};

@component({
	name: 'view-b',
	template
})
class ViewBComponent extends ko.ViewModel {
	public params!: Params;
	public inputStampCard: KnockoutObservable<string> = ko.observable('');
	public items: KnockoutObservableArray<IStampCard> = ko.observableArray([]);
	public currentCode: KnockoutObservable<string> = ko.observable('');
	public model: StampCard = new StampCard();
	public employee: EmployeeVIewB = new EmployeeVIewB();

	created(params: Params) {
		const vm = this;
		this.params = params;
		
		vm.model.stampNumber
			.subscribe((c: string) => {
				const stampCards: IStampCard[] = ko.toJS(vm.items);
				const current = _.find(stampCards, e => e.stampNumber === c);
				
				if (current) {
					vm.$ajax(KMP001B_API.GET_INFO_EMPLOYEE + ko.toJS(current.employeeId))
					.then((data: IEmployeeVIewB[]) => {
						vm.employee.update(ko.toJS(data));
					});
				} else {
					// reset data ve mode them moi
				}
			});
	}

	public getStampCard() {
		const vm = this;
		vm.$blockui("invisible");
		const stampInput: string = ko.toJS(vm.inputStampCard);

		//hiển thị dialog ở đây
		vm.$ajax(KMP001B_API.GET_STAMPCARD + stampInput)
			.then((data: IStampCard[]) => {
				vm.items(data);
				const record = data[0];
				
				if (record) {
					vm.model.update(record);
				}
			}).then(() => {
				vm.$blockui("clear");
			});
	}

	public getAllStampCard() {
		const vm = this;
		vm.$blockui("invisible");

		vm.$ajax(KMP001B_API.GET_ALL_STAMPCARD)
			.then((data: IStampCard[]) => {
				vm.items(data);
				const record = data[0];
				
				if (record) {
					vm.model.update(record);
				}
			}).then(() => {
				vm.$blockui("clear");
			});
	}
}

interface IStampCard {
	stampNumber: string;
	employeeCode: string;
	businessName: string;
	employeeId: string;
}

interface IEmployeeVIewB {
	birthDay: Date;
	businessName:string;
	employeeCode: string;
	employeeId: string;
	entryDate: Date;
	gender: number;
	pid: string;
	retiredDate: Date;
}

class StampCard {
	stampNumber: KnockoutObservable<String> = ko.observable('');
	employeeCode: KnockoutObservable<String> = ko.observable('');
	businessName: KnockoutObservable<String> = ko.observable('');
	employeeId: KnockoutObservable<String> = ko.observable('');

	constructor(params?: IStampCard) {
		const seft = this;

		if (params) {
			seft.stampNumber(params.stampNumber);
			seft.update(params);
		}
	}

	update(params: IStampCard) {
		const seft = this;

		seft.employeeCode(params.employeeCode);
		seft.businessName(params.businessName);
		seft.employeeId(params.employeeId);
	}
}

class EmployeeVIewB {
	birthDay: KnockoutObservable<Date | null> = ko.observable(null);
	businessName:KnockoutObservable<string> = ko.observable('');
	employeeCode: KnockoutObservable<string> = ko.observable('');
	employeeId: KnockoutObservable<string> = ko.observable('');
	entryDate: KnockoutObservable<Date | null> = ko.observable(null);
	gender: KnockoutObservable<number> = ko.observable(0);
	pid: KnockoutObservable<string> = ko.observable('');
	retiredDate: KnockoutObservable<Date | null> = ko.observable(null);
	
	constructor(params?: IEmployeeVIewB) {
		const seft = this;

		if (params) {
			seft.employeeId(params.employeeId);
			seft.update(params);
		}
	}

	update(params: IEmployeeVIewB) {
		const seft = this;

		seft.birthDay(params.birthDay);
		seft.employeeCode(params.employeeCode);
		seft.businessName(params.businessName);
		seft.entryDate(params.entryDate);
		seft.gender(params.gender);
		seft.pid(params.pid);
		seft.retiredDate(params.retiredDate);
	}
}