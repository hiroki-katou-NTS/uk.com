module kcp013.component {
	import text = nts.uk.resource.getText;

	ko.components.register('test-component', {
		viewModel: {
			createViewModel: function(params, componentInfo) {
				var cvm = new Model(params.input);

				return cvm;
			}
		},
		template: `<div style="margin-top: 30px">
			<div style="display: flex; margin-left: 40px;">
				<div data-bind="text: showNone"></div>
				<div data-bind="text: showDeferred"></div>
			</div>
			<div style="display: flex;">
				<div style="margin-left: 40px" id="combo-box" data-bind="ntsComboBox: {
					options: listWorkHours,
					optionsValue: 'code',
					value: selectedCode,
					optionsText: 'name',
					editable: false,
					width: 450,
					columns: [
					{ prop: 'code', length: 4 },
					{ prop: 'name', length: 5 },
					{ prop: 'tzStartToEnd1', length: 5 },
					{ prop: 'tzStartToEnd2', length: 5 },
					{ prop: 'workStyleClassfication', length: 5 },
					{ prop: 'remark', length: 5 },]}">
				</div>
				<div data-bind="ntsCheckBox: { checked: checked, text: nts.uk.resource.getText('KCP013_3')}"></div>
			</div>
			</div>`
	});
	const GET_WORK_HOURS_URL = 'at/record/stamp/workhours/workhours';
	const GET_ALL_WORK_HOURS_URL = 'at/record/stamp/workhours/getallworkhours';
	class Model {
		listWorkHours: KnockoutObservableArray<Input>;
		selectedCode: KnockoutObservable<string>;
		fillter: boolean;
		workPlaceId: string;
		checked: KnockoutObservable<boolean>
		inputModel: Input;
		selectItem: any;
		showNone: KnockoutObservable<string>;
		showDeferred: KnockoutObservable<string>;

		constructor(param) {
			var self = this;
			self.listWorkHours = ko.observableArray([]);
			self.selectedCode = ko.observable('');
			self.inputModel = new Input('', '', '', '', '', '', '', '', '');
			self.fillter = param.fillter;
			self.workPlaceId = param.workPlaceId;
			self.selectItem = param.initiallySelected;
			self.showNone = ko.observable('');
			self.showDeferred = ko.observable('');
			if (param.showNone) {
				self.showNone(nts.uk.resource.getText('KCP013_5'));
			}
			if (param.showDeferred) {
				self.showDeferred(nts.uk.resource.getText('KCP013_6'));
			}

			self.checked = ko.observable(!self.fillter);
			if (self.checked()) {
				self.loadAllWorkHours();
			} else {
				self.loadWorkHours(param);
			}

			self.checked.subscribe(function(value) {
				self.listWorkHours.removeAll();
				if (value) {
					self.loadAllWorkHours();
				} else {
					self.loadWorkHours(param);
				}
			});
			self.selectedCode.subscribe(function(code) {
				// self.selectedCode(code);
			})

		}
		public loadWorkHours(param): JQueryPromise<void> {
			let self = this;
			var dfd = $.Deferred();
			nts.uk.request.ajax('at', GET_WORK_HOURS_URL, param).done((data) => {
				data.forEach((x) => {
					self.listWorkHours.push(new Input(x.code, x.name, x.tzStart1, x.tzEnd1, x.tzStart2, x.tzEnd2, x.workStyleClassfication, x.remark, x.useDistintion));
				})
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem();
				dfd.resolve();
			})
			return dfd.promise();
		}

		public loadAllWorkHours(): JQueryPromise<void> {
			let self = this;
			var dfd = $.Deferred();
			nts.uk.request.ajax('at', GET_ALL_WORK_HOURS_URL).done((data) => {
				data.forEach((x) => {
					self.listWorkHours.push(new Input(x.code, x.name, x.tzStart1, x.tzEnd1, x.tzStart2, x.tzEnd2, x.workStyleClassfication, x.remark, x.useDistintion));
				})
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem();
				dfd.resolve();
			})

			return dfd.promise();
		}
		public checkSelectedItem() {
			let self = this;
			if (self.selectItem instanceof Array) {
				if (self.selectItem.length == 1) {
					var ifthree = self.listWorkHours().filter((x) => {
						return x.code == self.selectItem[0];
					})[0].code;
					if (ifthree == self.selectItem[0]) {
						self.selectedCode(self.selectItem[0]);
					}

				}

			}
		}
	}
	export class Input {
		code: string;
		name: string;
		tzStartToEnd1: string;
		tzStartToEnd2: string;
		workStyleClassfication: string;
		remark: string;
		useDistintion: string;
		constructor(code: string, name: string, tzStart1: string, tzEnd1: string, tzStart2: string, tzEnd2: string, workStyleClassfication: string, remark: string, useDistintion: string) {
			this.code = code;
			this.name = name;
			this.tzStartToEnd1 = tzStart1 + nts.uk.resource.getText('KCP013_4') + tzEnd1;
			if (useDistintion == '1') {
				this.tzStartToEnd2 = tzStart2 + nts.uk.resource.getText('KCP013_4') + tzEnd2;
			} else {
				this.tzStartToEnd2 = '';
			}
			this.workStyleClassfication = workStyleClassfication;
			this.remark = remark;
		}
	}

}