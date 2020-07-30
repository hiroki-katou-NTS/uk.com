module kcp013.component {
	import text = nts.uk.resource.getText;

	ko.components.register('combo-box', {
		viewModel: {
			createViewModel: function(params, componentInfo) {
				var cvm = new Model(params.input, params.callback);

				return cvm;
			}
		},
		template: `<div>
			<div style="display: flex;">
				<div id="combo-box" data-bind="ntsComboBox: {
					options: listWorkHours,
					optionsValue: 'code',
					value: selectedCode,
					optionsText: 'name',
					editable: false,
					width: 450,
					columns: [
					{ prop: 'showNone', length: 0 },
					{ prop: 'showDeferred', length: 0 },
					{ prop: 'code', length: 4 },
					{ prop: 'name', length: 4 },
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
		selectItem: any;
		showNone: KnockoutObservable<string>;
		showDeferred: KnockoutObservable<string>;
		data: any;

		constructor(param, callback) {
			var self = this;
			self.listWorkHours = ko.observableArray([]);
			self.selectedCode = ko.observable('');
			self.fillter = param.fillter;
			self.workPlaceId = param.workPlaceId;
			self.selectItem = param.initiallySelected;
			self.showNone = param.showNone;
			self.showDeferred = param.showDeferred;

			self.checked = ko.observable(!self.fillter);
			if (self.checked()) {
				self.loadAllWorkHours(param);
			} else {
				self.loadWorkHours(param);
			}

			self.checked.subscribe(function(value) {
				self.listWorkHours.removeAll();
				if (value) {
					self.loadAllWorkHours(param);
				} else {
					self.loadWorkHours(param);
				}
			});
			self.selectedCode.subscribe(function(codeMap) {
				if (codeMap) {
					let obj = _.filter(self.listWorkHours(), function(o) { return o.code === codeMap; });
					callback(obj);
				} else {
					callback(null);
				}
			})
		}
		public loadWorkHours(param): JQueryPromise<void> {
			let self = this;
			var dfd = $.Deferred();
			nts.uk.request.ajax('at', GET_WORK_HOURS_URL, param).done((data) => {
				self.getListWorkHours(data, param)
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem(param);
				dfd.resolve();
			})
			return dfd.promise();
		}

		public loadAllWorkHours(param): JQueryPromise<void> {
			let self = this;
			var dfd = $.Deferred();
			nts.uk.request.ajax('at', GET_ALL_WORK_HOURS_URL).done((data) => {
				self.getListWorkHours(data, param)
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem(param);
				dfd.resolve();
			})

			return dfd.promise();
		}

		private getListWorkHours(data, param) {
			var self = this;
			if (param.showNone == true && param.showDeferred == false) {
				self.listWorkHours.push(new Input('', nts.uk.resource.getText('KCP013_5'), '', '', '', '', '', '', '', '1'));
			}
			if (param.showNone == false && param.showDeferred == true) {
				self.listWorkHours.push(new Input('', nts.uk.resource.getText('KCP013_6'), '', '', '', '', '', '', '', '1'));
			}
			if (param.showNone == true && param.showDeferred == true) {
				self.listWorkHours.push(new Input('', nts.uk.resource.getText('KCP013_5'), nts.uk.resource.getText('KCP013_6'), '', '', '', '', '', '', '1'));
			}
			data.forEach((x) => {

				self.listWorkHours.push(new Input(x.code, x.name, x.tzStart1, x.tzEnd1, x.tzStart2, x.tzEnd2, x.workStyleClassfication, x.remark, x.useDistintion, ''));
			})

		}
		public checkSelectedItem(param) {
			let self = this;
			if (self.selectItem instanceof Array) {
				if (self.selectItem.length == 1) {
					var ifthree = self.listWorkHours().filter((x) => {
						return x.code == self.selectItem[0];
					})[0];
					if (ifthree) {
						if (ifthree.code == self.selectItem[0]) {
							self.selectedCode(self.selectItem[0]);
						} else {
							if (param.showNone == true || param.showDeferred == true) {
								self.selectedCode(self.listWorkHours()[1].code);
							}

						}
					} else {
						if (param.showNone == true || param.showDeferred == true) {
							self.selectedCode(self.listWorkHours()[1].code);
						}
					}
				}
			}
		}
	}
	export class Input {
		code: string;
		name: string;
		tzStart1: string;
		tzEnd1: string;
		tzStart2: string;
		tzEnd2: string;
		tzStartToEnd1: string;
		tzStartToEnd2: string;
		workStyleClassfication: string;
		remark: string;
		useDistintion: string;
		constructor(code: string, name: string, tzStart1: string, tzEnd1: string, tzStart2: string, tzEnd2: string, workStyleClassfication: string, remark: string, useDistintion: string, showNaci: string) {

			if (showNaci == '1') {
				this.code = code;
				this.name = name;
				this.tzStartToEnd1 = '';
				this.tzStartToEnd2 = '';
				this.workStyleClassfication = workStyleClassfication;
				this.remark = remark;
			} else {
				this.code = code;
				this.name = name;
				this.tzStart1 = tzStart1;
				this.tzEnd1 = tzEnd1;
				this.tzStart2 = tzStart2;
				this.tzEnd2 = tzEnd2;
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

}