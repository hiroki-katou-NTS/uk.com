module nts.uk.at.view.kaf005.shr.viewmodel {
	import AttendanceType = nts.uk.at.view.kaf005.a.viewmodel.AttendanceType;
	const template = `
<div class="container cf" data-bind="with: $parent">
	<div class="cf valign-top control-group"
		data-bind="visible: visibleModel.c18()">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_40'), ntsFormLabel: {}"></div>
		</div>
		<div class="table-time">
			<table id="fixed-table">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A5_3 開始ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_41')"></th>
						<!--A5_4 終了ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_42')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: restTime">
					<tr>
						<!--A5_5 休憩時間順序-->
						<td class="header" data-bind="text: String(frameNo)"></td>
						<!--A5_6 開始時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_337]', 
									value: start, 
									constraint:'TimeWithDayAttr', 
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_338]', 
									value: end, 
									constraint:'TimeWithDayAttr', 
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>



	<!-- calculate button A5_8-->
	<div data-bind="if: visibleModel.c7()" style="margin-bottom: 20px">
		<button style="width: 100px; margin-left: 200px"
			data-bind="text: $i18n('KAF005_43'), click: calculate"
			class="caret-bottom caret-inline"></button>
	</div>



	<!-- over time hours -->
	<div class="cf valign-top control-group" data-bind="visible: true">
		<!--A6_1 残業時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_50'), ntsFormLabel: {required: true}"></div>
		</div>
		<div class="table-time overTime1">
			<table id="fixed-overtime-hour-table">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
					<col width="115px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A6_3 申請時間ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_51')"></th>
						<!--A6_4 事前申請ラベル-->
						<th class="ui-widget-header hoangnd" rowspan="2"
							data-bind="text: $i18n('KAF005_52')"></th>
						<!--A6_6 実績時間ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_54')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: overTime">
					<tr data-bind="if: visible()">
						<!--A6_7 残業時間名称-->
						<td class="header" data-bind="text: displayNo"></td>
						<!--A6_8 残業申請時間入力-->
						<td data-bind="style: {'background-color': backgroundColor()}"><input
							tabindex="12" class="right-content overtimeHoursCheck"
							data-bind=" 
								style: {'background-color': backgroundColor()},
								ntsTimeEditor: { 
									value: applicationTime,
									option: {width: '85px', timeWithDay: true},
									inputFormat: 'time',
									mode: 'time',
									constraint:'OvertimeAppPrimitiveTime',
									enable: $parent.visibleModel.c28() }" />
						</td>
						<!--A6_9 残業事前申請時間-->
						<td class="right-content hoangnd"
							data-bind="text: $parent.getFormatTime(ko.toJS(preTime))"></td>
						<!--A6_11 実績時間-->
						<td class="right-content"
							data-bind="text: $parent.getFormatTime(ko.toJS(actualTime))"></td>
					</tr>
				</tbody>
			</table>
		</div>



		<div class="table-time overTime2">
			<table id="fixed-overtime-hour-table-1">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A6_3 申請時間ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_51')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: overTime">
					<tr data-bind="if: visible()">
						<!--A6_7 残業時間名称-->
						<td class="header" data-bind="text: displayNo"></td>
						<!--A6_8 残業申請時間入力-->
						<td data-bind="style: {'background-color': backgroundColor()}"><input
							tabindex="12" class="right-content overtimeHoursCheck"
							data-bind=" 
								style: {'background-color': backgroundColor()},
								ntsTimeEditor: { 
									value: applicationTime,
									option: {width: '85px', timeWithDay: true},
									inputFormat: 'time',
									mode: 'time',
									constraint:'OvertimeAppPrimitiveTime',
									enable: $parent.visibleModel.c28() }" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>





	</div>


	<!-- holiday time -->
	<div class="cf valign-top control-group"
		data-bind="visible: visibleModel.c30()">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_70'), ntsFormLabel: {required: true}"></div>
		</div>

		<div class="table-time holidayTime1">
			<table id="fixed-table-holiday">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
					<col width="115px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A5_3 開始ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_71')"></th>
						<!--A5_4 終了ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_72')"></th>
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_54')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: holidayTime">
					<tr data-bind="if: visible()">
						<!--A5_5 休憩時間順序-->
						<td class="header" data-bind="text: displayNo"></td>
						<!--A5_6 開始時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeEditor: {
									name: '#[KAF005_337]', 
									value: start, 
									constraint:'OvertimeAppPrimitiveTime',
									inputFormat: 'time',
									enable: $parent.visibleModel.c28(),
									mode: 'time',
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td class="right-content"
							data-bind="text: $parent.getFormatTime(ko.toJS(preApp))"></td>
						<td class="right-content"
							data-bind="text: $parent.getFormatTime(ko.toJS(actualTime))"></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="table-time holidayTime2">
			<table id="fixed-table-holiday-1">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A5_3 開始ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_71')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: holidayTime">
					<tr data-bind="if: visible()">
						<!--A5_5 休憩時間順序-->
						<td class="header" data-bind="text: displayNo"></td>
						<!--A5_6 開始時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeEditor: {
									name: '#[KAF005_337]', 
									value: start, 
									constraint:'OvertimeAppPrimitiveTime',
									inputFormat: 'time',
									enable: $parent.visibleModel.c28(),
									mode: 'time',
									option: {width: '85px', timeWithDay: true}}" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>




	</div>





</div>






	`
	@component({
        name: 'kaf005-share',
		template: template
    })
	class KAF005ShrModel extends ko.ViewModel {
		
		// restTime: KnockoutObservableArray<RestTime> = ko.observableArray([]);
		
		// holidayTime: KnockoutObservableArray<HolidayTime> = ko.observableArray([]);
		
		// overTime: KnockoutObservableArray<OverTime> = ko.observableArray([]);
		visibleModel: any;
		overtTimeMountTable1: boolean = false;
		overtTimeMountTable2: boolean = false;
		
		holidayTimeMountTable1: boolean = false;
		holidayTimeMountTable2: boolean = false;
		
		backgroundColor: KnockoutObservable<Boolean> = ko.observable(false);
		
		created(params: any) {
			const self = this;
			self.visibleModel = params.visibleModel;
			// self.restTime = params.restTime;
			// self.holidayTime = params.holidayTime;
			// self.overTime = params.overTime;
			
		}
		
		mounted() {
			const self = this;
			$(".overTime2").hide();
			$(".overTime1").hide();
			$("#fixed-table").ntsFixedTable({ height: 120 });
			self.visibleModel.c15_3.subscribe((value: any) => {
				if (!_.isNil(value)) {
					if (value) {
						
						$(".overTime2").hide();
						$(".overTime1").show();
						$(".holidayTime2").hide();
						$(".holidayTime1").show();
						
						if (!self.overtTimeMountTable1) {
							$("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
							self.overtTimeMountTable1 = true;
						}
						if (!self.holidayTimeMountTable1) {
							$("#fixed-table-holiday").ntsFixedTable({ height: 120 });
							self.holidayTimeMountTable1 = true;
						}
						
					} else {
						
						$(".overTime1").hide();
						$(".overTime2").show();
						$(".holidayTime1").hide();
						$(".holidayTime2").show();
						
						if (!self.overtTimeMountTable2) {
							$("#fixed-overtime-hour-table-1").ntsFixedTable({ height: 216 });
							self.overtTimeMountTable2 = true;
						}
						
						if (!self.holidayTimeMountTable2) {
							$("#fixed-table-holiday-1").ntsFixedTable({ height: 120 });
							self.holidayTimeMountTable2 = true;
						}
						
					}
				}
			})
			
		}
	}
	export interface OverTime {
		frameNo: string;
		displayNo: KnockoutObservable<string>;
		applicationTime?: KnockoutObservable<number>;
		preTime?: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
		type: AttendanceType;
		visible: KnockoutObservable<Boolean>;
		backgroundColor: KnockoutObservable<string>;
	}
	export interface RestTime {
		frameNo: string;
		displayNo: KnockoutObservable<string>;
		start?: KnockoutObservable<number>;
		end?: KnockoutObservable<number>;
	}
	
	export interface HolidayTime {
		frameNo: string;
		displayNo: KnockoutObservable<string>;
		start?: KnockoutObservable<number>;
		preApp?: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
		type: AttendanceType;
		visible: KnockoutObservable<Boolean>;
		backgroundColor: KnockoutObservable<string>;
	}
	
	
}