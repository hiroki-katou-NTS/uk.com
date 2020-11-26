/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

import FlexScreenData = nts.uk.at.kmk004.components.flex.FlexScreenData;
import YearItem = nts.uk.at.kmk004.components.flex.YearItem;
const template = `
	
	
	<div class="div_line">
		<div data-bind="ntsFormLabel: {} , i18n: 'KMK004_232'"></div>
	</div>
	
	<div style="padding-left:10px;"> 
		<div class="div_line">
			<button data-bind="click: openQDialog , i18n: 'KMK004_233'" ></button>
		</div>
	
		<div class="div_line" >
		
			<div id="year-list" data-bind="ntsListBox: {
				options: screenData().yearList,
				optionsValue: 'year',
				optionsText: 'yearName',
				multiple: false,
				value: screenData().selectedYear,
				rows:5,
				columns:[
				{ key: 'isNotSave', length: 1 },
				{ key: 'yearName', length: 4 }
				]
				}"></div>
			
			<div id="monthly-list">
			
				<table id="expand-list">
				
								<thead>
										<tr style="background-color:#92D050">
											<th data-bind="visible: screenMode == 'Com_Person'"></th>
											<th style="text-align:center;" data-bind="i18n: 'KMK004_263'"></th>
											<th data-bind="visible: screenData().setting().useRegularWorkingHours() == 1,i18n: 'KMK004_264'" style="text-align:center;"></th>
											<th style="text-align:center;" data-bind="i18n: 'KMK004_265'"></th>
											<th style="text-align:center;" data-bind="i18n: 'KMK004_266'"></th>
											
										</tr>
								</thead>
								
								<tbody data-bind="foreach:screenData().monthlyWorkTimeSetComs">
											<tr>
												<td  data-bind="visible: $parent.screenMode == 'Com_Person' "><div data-bind="ntsCheckBox: { checked:$data.laborTime().checkbox }"></div></td>
												<td class="bg-green" style="text-align:center;" ><span data-bind="text: $data.month + '月度'"></span></td>
												<td data-bind="visible: $parent.screenData().setting().useRegularWorkingHours() == 1"><input  data-bind="
												ntsTimeEditor: {
														name:'#[KMK004_264]',
														value: $data.laborTime().withinLaborTime,
														enable: $data.laborTime().checkbox,
														inputFormat: 'time', 
														mode: 'time',
														option: {textalign: 'center',width: '60px'}
														}"
												/></td>
												<td ><input  data-bind="
												ntsTimeEditor: {name:'#[KMK004_265]',
														value: $data.laborTime().legalLaborTime,
														enable: $data.laborTime().checkbox,
														inputFormat: 'time', 
														mode: 'time',
														option: {textalign: 'center',width: '60px'}
														}"
												/></td>
												<td ><input  data-bind="
												ntsTimeEditor: {name:'#[KMK004_266]',
														value: $data.laborTime().weekAvgTime,
														enable: $data.laborTime().checkbox,
														inputFormat: 'time', 
														mode: 'time',
														option: {textalign: 'center',width: '60px'}
														}"
												/></td>
											</tr>
								</tbody>
								
								<tr data-bind="visible: screenMode != 'Com_Person' " >
									<td class="bg-green" style="text-align:center;" data-bind="i18n: 'KMK004_267'" ></td>
									<td data-bind="visible: screenData().setting().useRegularWorkingHours() == 1,text:calTotalTime('withinLaborTime')" style="text-align:center;"></td>
									<td style="text-align:center;" data-bind="text:calTotalTime('legalLaborTime')" ></td>
									<td style="text-align:center;" data-bind="text:calTotalTime('weekAvgTime')" ></td>
								</tr>
									
				</table>		
			</div>
			<div style="margin-left: 10px;    display: inline-block;" ><a id="show-explan" class="hyperlink" data-bind="i18n: 'KMK004_269'"></a> </div>
		</div>
	</div>
	<div id="explan-popup">
		<h2>Popup 1!</h2>
		<p>Any content go here</p>
	</div>
	`;
const COMPONENT_NAME = 'monthly-working-hours';

@component({
	name: COMPONENT_NAME,
	template
})
class MonthlyWorkingHours extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData>;
	
	screenMode: string = 'Com_Workplace';

	popUpShow = false;

	created(param: IParam) {
		let vm = this;
		vm.screenData = param.screenData;
		vm.screenMode =  param.screenMode;
		console.log(vm.screenData().setting().useRegularWorkingHours());
	}

	mounted() {
		$("#explan-popup").ntsPopup({
			trigger: "#show-explan",
			position: {
				my: "left top",
				at: "left bottom",
				of: "#show-explan"
			},
			showOnStart: false,
			dismissible: false
		});

		$('#show-explan').click(function() {
			$(this).siblings('#explan-popup').ntsPopup('toggle');
		});
	}

	openQDialog() {
		const vm = this;
		vm.$window.modal('/view/kmk/004/q/index.xhtml', { years: _.map(vm.screenData().yearList(), (yearItem: YearItem) => { return yearItem.year; }) }).then((result) => {
			if (result) {
				let yearList = vm.screenData().yearList();
				yearList.push(new YearItem(Number(result.year), true));
				vm.screenData().yearList(_.orderBy(yearList, ['year'], ['desc']));
				vm.screenData().updateMode(false);
				vm.screenData().selectedYear(vm.screenData().yearList()[0].year);
				
			}
		});
	}

	calTotalTime(attributeName: string) {
		let vm = this,
			data = ko.mapping.toJS(vm.screenData().monthlyWorkTimeSetComs()),
			total = _.sumBy(data, 'laborTime.' + attributeName);

		if (!vm.screenData().monthlyWorkTimeSetComs().length) {
			return '';
		}
		return nts.uk.time.format.byId("Clock_Short_HM", total);
	}

}

interface IParam {
	screenData: KnockoutObservable<FlexScreenData>;
	isShowCheckbox: boolean;
}

