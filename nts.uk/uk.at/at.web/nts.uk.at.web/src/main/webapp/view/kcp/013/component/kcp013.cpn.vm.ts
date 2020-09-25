module kcp013.component {
	import text = nts.uk.resource.getText;
	import formatById = nts.uk.time.format.byId;

	ko.components.register('working-hours', {
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
					enable: true,
					visibleItemsCount: 10,
					columns: [
					{ prop: 'code', length: 1 },
					{ prop: 'name', length: 1 },
					{ prop: 'tzStartToEnd1', length: 1 },
					{ prop: 'tzStartToEnd2', length: 1 },
					{ prop: 'workStyleClassfication', length: 1 },
					{ prop: 'remark', length: 1 },]}">
				</div>
				<div data-bind="visible: fillter, ntsCheckBox: { checked: checked, text: nts.uk.resource.getText('KCP013_3')}"></div>
			</div>
			</div>`
	});
	const GET_WORK_HOURS_URL = 'at/record/stamp/workhours/workhours';
	const GET_ALL_WORK_HOURS_URL = 'at/record/stamp/workhours/getallworkhours';
	class Model {
		listWorkHours: KnockoutObservableArray<Input>;
		selectedCode: KnockoutObservable<string>;
		fillter: boolean;
		workPlaceId: KnockoutObservable<string>;
		checked: KnockoutObservable<boolean> = ko.observable(false);
		selectItem: any;
		showNone: KnockoutObservable<string>;
		showDeferred: KnockoutObservable<string>;
		data: any;
		isEnable: KnockoutObservable<boolean>;
		constructor(param, callback) {
			var self = this;
			self.listWorkHours = ko.observableArray([]);
			self.selectedCode = ko.observable(param.initiallySelected);
			self.fillter = param.fillter;
			self.workPlaceId = ko.observable(param.workPlaceId);
			self.selectItem = param.initiallySelected;
			self.showNone = param.showNone;
			self.showDeferred = param.showDeferred;
			self.isEnable = ko.observable(param.disable);
            
			//self.checked = ko.observable(param.fillter);
			if (!param.fillter) {
				self.loadAllWorkHours(param);
			} else {
				self.loadWorkHours(param);
			}
			self.checked.subscribe(function(value) {
				self.listWorkHours.removeAll();
				if (!value) {
					self.loadWorkHours(param);
				} else {
					self.loadAllWorkHours(param);
				}
			});
			self.selectedCode.subscribe(function(codeMap) {
				if (codeMap == null) {
					return;
				} else {
					let obj = _.filter(self.listWorkHours(), function(o) { return o.code === codeMap; });
					let listWorkTime = self.listWorkHours();
					callback(obj, listWorkTime);
				}
			})
		}
		// get Working Hours by workplaceId
		public loadWorkHours(param): JQueryPromise<void> {
			let self = this;
			var dfd = $.Deferred();
            let wkpId = typeof param.workPlaceId == 'function' ?  param.workPlaceId() : param.workPlaceId;
            param.workPlaceId = wkpId;
			nts.uk.request.ajax('at', GET_WORK_HOURS_URL, param).done((data) => {
				self.getListWorkHours(data, param)
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem(param);
				dfd.resolve();
			})
			return dfd.promise();
		}

		// get All Working Hours by workplaceId
		public loadAllWorkHours(param): JQueryPromise<void> {
			let self = this;
            var dfd = $.Deferred();
            let wkpId = typeof param.workPlaceId == 'function' ?  param.workPlaceId() : param.workPlaceId;
            param.workPlaceId = wkpId;
			nts.uk.request.ajax('at', GET_ALL_WORK_HOURS_URL).done((data) => {
				self.getListWorkHours(data, param)
				self.listWorkHours.valueHasMutated();
				self.checkSelectedItem(param);
				dfd.resolve();
			})

			return dfd.promise();
		}
		// push data to listWorkHours
		private getListWorkHours(data, param) {
			var self = this;
			let datas = [];
			if (param.showNone == true && param.showDeferred == false) {
				datas.push(new Input('', nts.uk.resource.getText('KCP013_5'), '', '', '', '', '', '', '', '1'));
			}
			if (param.showNone == false && param.showDeferred == true) {
				datas.push(new Input('', nts.uk.resource.getText('KCP013_6'), '', '', '', '', '', '', '', '1'));
			}
			if (param.showNone == true && param.showDeferred == true) {
				datas.push(new Input('', nts.uk.resource.getText('KCP013_5'), '', '', '', '', '', '', '', '1'));
				datas.push(new Input(' ', nts.uk.resource.getText('KCP013_6'), '', '', '', '', '', '', '', '1'));
			}

			data.forEach((x) => {
				datas.push(new Input(x.code, x.name, formatById("Clock_Short_HM", x.tzStart1),
					formatById("Clock_Short_HM", x.tzEnd1), formatById("Clock_Short_HM", x.tzStart2),
					x.tzEnd2 > 0 ? formatById("Clock_Short_HM", x.tzEnd2) : null, x.workStyleClassfication, x.remark, x.useDistintion, ''));
			})
			self.listWorkHours(datas);

		}

		public checkSelectedItem(param) {
			let self = this;
			if (self.selectItem instanceof Array) {
				if (self.selectItem.length == 1) {
					var ifthree = self.listWorkHours().filter((x) => {
						return x.code == self.selectItem[0];
					})[0];
					if (ifthree) {
						self.selectedCode(self.selectItem[0]);
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
				if (useDistintion == '1' && tzEnd2 != null) {
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