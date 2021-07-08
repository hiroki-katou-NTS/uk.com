module nts.uk.at.view.kaf010.shr.time.viewmodel {

	import Application = nts.uk.at.view.kaf000.shr.viewmodel.Application;

	const template = `
	<div class="container cf" data-bind="with: $parent">
	<div class="cf valign-top control-group" data-bind="visible: restTimeTableVisible() && restTimeTableVisible2()">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF010_40'), ntsFormLabel: {}"></div>
		</div>
		<div class="table-time1" style="margin-left: 16px">
			<table id="fixed-table">
				<colgroup>
					<col width="25px" />
					<col width="115px" />
					<col width="115px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A5_3 開始ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_41')"></th>
						<!--A5_4 終了ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_42')"></th>
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
									name: '#[KAF010_337]', 
									value: start, 
									constraint:'TimeWithDayAttr', 
									enable: $parent.mode() != 3,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF010_338]', 
									value: end, 
									constraint:'TimeWithDayAttr', 
									enable: $parent.mode() != 3,
									option: {width: '85px', timeWithDay: true}}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- calculate button A5_8-->
	<div style="margin-bottom: 20px; margin-top: 13px;" data-bind="visible: restTimeTableVisible2()">
		<button style="width: 100px; margin-left: 200px" data-bind="text: $i18n('KAF010_43'), click: calculate, enable: mode() != 3" class="caret-bottom caret-inline" ></button>
	</div>

	<!-- holiday time -->
	<div class="cf valign-top control-group" data-bind="if: true" style="margin-bottom: 2px; margin-top: 23px;">
		<!--A6_1 -->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF010_50'), ntsFormLabel: {required: true}"></div>
		</div>
		
		<div class="table-time2" style="margin-left: 16px">
			<table id="fixed-table-holiday">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
					<col width="115px" data-bind="visible: application().prePostAtr() == 1 && mode() != 2" />
					<col width="115px" data-bind="visible: application().prePostAtr() == 1 && mode() != 2" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A6_3-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_51')"></th>
						<!--A6_4-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_52'), visible: application().prePostAtr() == 1 && mode() != 2"></th>
						<!--A6_6-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_54'), visible: application().prePostAtr() == 1 && mode() != 2"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: holidayTime">
					<tr>
						<!--A6_7-->
						<td class="header" data-bind="text: frameName()"></td>
						<!--A6_8 -->
						<td data-bind="style: {'background-color': backgroundColor()}"><input tabindex="12" class="right-content"
							data-bind="
								style: {'background-color': backgroundColor()},
								ntsTimeWithDayEditor: {
									name: frameName, 
									value: start,
									constraint:'OvertimeAppPrimitiveTime',
									inputFormat: 'time',
									mode: 'time',
									enable: $parent.inputEnable() && ($parent.mode() != 3 || type() != 1),
									option: {width: '85px', timeWithDay: false}}" /></td>
						<!--A6_9 -->
						<td class="right-content" data-bind="text: $parent.getFormatTime(ko.toJS(preApp)), visible: $parent.application().prePostAtr() == 1 && $parent.mode() != 2"></td>
						<!--A6_11 -->
						<td class="right-content" data-bind="text: $parent.getFormatTime(ko.toJS(actualTime)), visible: $parent.application().prePostAtr() == 1 && $parent.mode() != 2"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- over time hours -->
	<div style="margin-top: 13px;
    margin-bottom: 1px;" class="cf valign-top control-group" data-bind="visible: overTimeTableVisible()">
		<!--A7_1 残業時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_50'), ntsFormLabel: {}"></div>
		</div>
		<div class="table-time3" style="margin-left: 16px">
			<table id="fixed-overtime-hour-table">
				<colgroup>
					<col width="109px" />
					<col width="115px" />
					<col width="115px" data-bind="visible: application().prePostAtr() == 1 && mode() != 2"/>
					<col width="115px" data-bind="visible: application().prePostAtr() == 1 && mode() != 2"/>
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A7_3 申請時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_51')"></th>
						<!--A7_4 事前申請ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_52'), visible: application().prePostAtr() == 1 && mode() != 2"></th>
						<!--A7_6 実績時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_54'), visible: application().prePostAtr() == 1 && mode() != 2"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: overTime">
					<tr>
						<!--A7_7-->
						<td class="header" data-bind="text: frameName()"
							></td>
						<!--A7_8 残業申請時間入力-->
						<td data-bind="style: {'background-color': backgroundColor()}">
							<input tabindex="12" class="right-content overtimeHoursCheck"
							data-bind=" 
								style: {'background-color': backgroundColor()},
								ntsTimeEditor: { 
									name: frameName, 
									value: applicationTime, 
									option: {width: '85px', timeWithDay: true},
									inputFormat: 'time',
									mode: 'time',
									constraint:'OvertimeAppPrimitiveTime',
									enable: $parent.mode() != 3 || type() != 0 }" />
						</td>
						<!--A7_9 残業事前申請時間-->
						<td class="right-content" data-bind="text: $parent.getFormatTime(ko.toJS(preTime)), visible: $parent.application().prePostAtr() == 1 && $parent.mode() != 2"></td>
						<!--A7_11 実績時間-->
						<td class="right-content" data-bind="text: $parent.getFormatTime(ko.toJS(actualTime)), visible: $parent.application().prePostAtr() == 1 && $parent.mode() != 2"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
    `

    @component({
        name: 'kaf010-share-time',
        template: template
    })
    class KAF010ShrTimeModel extends ko.ViewModel {
		
		application: KnockoutObservable<Application>;
		holidayTime: KnockoutObservableArray<HolidayTime>;
		overTime: KnockoutObservableArray<OverTime>;

		backgroundColor: KnockoutObservable<Boolean> = ko.observable(false);
		
        created(params: any) {
			const self = this;
			self.application = params.application;
			self.holidayTime = params.holidayTime;
			self.overTime = params.overTime;
        }

        mounted() {
			const self = this;
            $("#fixed-table").ntsFixedTable({ height: 100 });
			$("#fixed-overtime-hour-table").ntsFixedTable({ height: 120 });
			$("#fixed-table-holiday").ntsFixedTable({ height: 120 });
			$("#fixed-table-holiday-test").ntsFixedTable({ height: 216 });
        }
    }

    export interface OverTime {
		frameNo: KnockoutObservable<number>;
		frameName: KnockoutObservable<string>;
		applicationTime?: KnockoutObservable<number>;
		preTime?: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
		type?: KnockoutObservable<number>;
		displayNo?: KnockoutObservable<string>;
		visible?: KnockoutObservable<Boolean>;
		backgroundColor: KnockoutObservable<string>;
	}
	export interface RestTime {
		frameNo: string;
		start?: KnockoutObservable<number>;
		end?: KnockoutObservable<number>;
	}
	
	export interface HolidayTime {
		frameNo: KnockoutObservable<number>;
		frameName: KnockoutObservable<string>;
		start?: KnockoutObservable<number>;
		preApp: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
		type?: KnockoutObservable<number>;
		legalClf?: KnockoutObservable<number>;
		displayNo?: KnockoutObservable<string>;
		visible?: KnockoutObservable<Boolean>;
		backgroundColor: KnockoutObservable<string>;
	}
}