/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

import FlexScreenData = nts.uk.at.kmk004.components.flex.FlexScreenData;
import YearItem = nts.uk.at.kmk004.components.flex.YearItem;
const template = `
	
	
	<div data-bind="ntsFormLabel: {inline:true , text: $i18n('KMK004_232') }"></div>
	
	<div style="padding-left:10px;"> 
		<div class="div_line">
			<button data-bind="enable:enableQButton(),click: openQDialog , i18n: 'KMK004_233'" ></button>
		</div>
	
		<div class="div_line" >
		<div style="float:left">
			<div id="year-list" data-bind="ntsListBox: {
				options: screenData().yearList,
				optionsValue: 'year',
				optionsText: 'yearName',
				multiple: false,
				value: screenData().selectedYear,
				rows:5,
				columns:[
				{ key: 'isNewText', length: 1 },
				{ key: 'yearName', length: 4 }
				]
				}"></div>
			<div> <label class="color-attendance" data-bind="i18n:'KMK004_212' "> </label></div>
		</div>
			<div id="monthly-list">
			
				<table id="expand-list" data-bind="html:bind_table()">
				
								
									
				</table>		
			</div>
			<div style="margin-left: 10px;    display: inline-block;" >
			
			<a class="show-popup1 hyperlink" data-bind="i18n: 'KMK004_269'"></a> </div>
		</div>
	</div>
	<div class="popup-area1">
		<img id="G8_2" src="../img/IMG_KMK004_1.png" alt=""/>
	</div>
	`;

@component({
	name: 'monthly-working-hours',
	template
})
class MonthlyWorkingHours extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData>;

	screenMode: string = 'Com_Workplace';

	popUpShow = false;

	startYM: KnockoutObservable<number> = ko.observable(0);

	created(param: IParam) {
		let vm = this;
		vm.screenData = param.screenData;
		vm.screenMode = param.screenMode;
		vm.startYM = param.startYM;

		$(".popup-area1").ntsPopup({
			position: {
				my: "left top",
				at: "left bottom",
				of: ".show-popup1"
			},
			showOnStart: false,
			dismissible: true
		});

		$('.show-popup1').click(function() {
			$(".popup-area1").ntsPopup("show");
		});
	}

	enableQButton() {
		const vm = this;
		if (vm.screenMode == 'Com_Company') {
			return true;
		}
		return vm.screenData().selected() != null;
	}

	mounted() {

	}

	showScheduled() {
		const vm = this;

		if (vm.screenData().comFlexMonthActCalSet() == null) {
			return false;
		}
		
		return vm.screenData().comFlexMonthActCalSet().withinTimeUsageAttr;
	}

	openQDialog() {
		const vm = this;
		vm.$window.modal('/view/kmk/004/q/index.xhtml', { startDate: vm.startYM(), years: _.map(vm.screenData().yearList(), (yearItem: YearItem) => { return yearItem.year; }) }).then((result) => {
			if (result) {
				let yearList = vm.screenData().yearList(),
					year = Number(result.year);
				yearList.push(new YearItem(Number(year), true));
				vm.screenData().yearList(_.orderBy(yearList, ['year'], ['desc']));
				vm.screenData().setNewYear(vm.startYM(), year);
				vm.screenData().selectedYear(year);
			}
		});
	}



	calTotalTime(attributeName: string) {
		let vm = this,
			data = ko.toJS(vm.screenData().monthlyWorkTimeSetComs()),
			total = _.sumBy(data, 'laborTime.' + attributeName);

		if (
			!vm.screenData().monthlyWorkTimeSetComs().length) {
			return '';
		}

		if (attributeName == 'withinLaborTime' && !_.filter(data, (item: IMonthlyWorkTimeSetCom) => { return item.laborTime.withinLaborTime != null }).length) {
			return '';
		}

		if (attributeName == 'legalLaborTime' && !_.filter(data, (item: IMonthlyWorkTimeSetCom) => { return item.laborTime.legalLaborTime != null }).length) {
			return '';
		}

		if (attributeName == 'weekAvgTime' && !_.filter(data, (item: IMonthlyWorkTimeSetCom) => { return item.laborTime.weekAvgTime != null }).length) {
			return '';
		}

		return nts.uk.time.format.byId("Clock_Short_HM", total > 0 ? total : 0);
	}
    
    bind_table(){
        const vm =this;
        
        return `<thead>
                                        <tr style="background-color:#92D050">`+
                                        vm.screenMode=='Com_Person' ? `<th></th>`:''+
                                        
                                            `<th style="text-align:center;" data-bind="i18n: 'KMK004_263'"></th>`+
            vm.showScheduled()?`<th data-bind="i18n: 'KMK004_264'" style="text-align:center;"></th>`:``
                                            
                                            +`
                                            <th style="text-align:center;" data-bind="i18n: 'KMK004_265'"></th>
                                            <th style="text-align:center;" data-bind="i18n: 'KMK004_266'"></th>
                                            
                                        </tr>
                                </thead> <tbody>`+vm.bind_row()+
                                
                              
                                
                                `</tbody> <tr data-bind="visible: screenMode != 'Com_Person' " >
                                    <td style="padding: 5px;text-align:center" class="bg-green" style="text-align:center;" data-bind="i18n: 'KMK004_267'" ></td>`+
                vm.showScheduled()?`<td data-bind="text:calTotalTime('withinLaborTime')" style="text-align:center;"></td>`:``
                                    
                        +`<td style="text-align:center;" data-bind="text:calTotalTime('legalLaborTime')" ></td>
                                    <td style="text-align:center;" data-bind="text:calTotalTime('weekAvgTime')" ></td>
                                </tr>`;
    }
    
    bind_row(){
        const vm =this;
        
        let result = '';
        
        _.forEach(vm.screenData().monthlyWorkTimeSetComs(), () => {

            result = result + `
                    <tr>`+
                        vm.screenMode=='Com_Person' ? `<td><div data-bind="ntsCheckBox: { checked:$data.laborTime().checkbox }"></div></td>`:''+
                        +
                    ` <td class="bg-green" style="text-align:center;" ><span data-bind="text: $data.yearMonthText + '月度'"></span></td>
                                                <td data-bind="visible:$parent.showScheduled()"><input  data-bind="
                                                ntsTimeEditor: {
                                                        name:'#[KMK004_264]',
                                                        value: $data.laborTime().withinLaborTime,
                                                        constraint:'MonthlyEstimateTime',
                                                        enable: $data.laborTime().checkbox,
                                                        inputFormat: 'time', 
                                                        mode: 'time',
                                                        option: {textalign: 'center',width: '80px'}
                                                        }"
                                                /></td>
                                                <td ><input  data-bind="
                                                ntsTimeEditor: {name:'#[KMK004_265]',
                                                        value: $data.laborTime().legalLaborTime,
                                                        constraint:'MonthlyEstimateTime',
                                                        enable: $data.laborTime().checkbox,
                                                        inputFormat: 'time', 
                                                        mode: 'time',
                                                        option: {textalign: 'center',width: '80px'}
                                                        }"
                                                /></td>
                                                <td ><input  data-bind="
                                                ntsTimeEditor: {name:'#[KMK004_266]',
                                                        value: $data.laborTime().weekAvgTime,
                                                        constraint:'MonthlyEstimateTime',
                                                        enable: $data.laborTime().checkbox,
                                                        inputFormat: 'time', 
                                                        mode: 'time',
                                                        option: {textalign: 'center',width: '80px'}
                                                        }"
                                                /></td></tr> `
                    ;
        });
        
        
        return result ;
    }

}

interface IParam {
	screenData: KnockoutObservable<FlexScreenData>;
	isShowCheckbox: boolean;
	startYM: KnockoutObservable<number>;
}

