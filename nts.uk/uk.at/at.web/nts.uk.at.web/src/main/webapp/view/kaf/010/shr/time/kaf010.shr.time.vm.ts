module nts.uk.at.view.kaf010.shr.time.viewmodel {
	const template = `
	<div class="container cf" data-bind="with: $parent">
	<div class="cf valign-top control-group" data-bind="if: true">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF010_40'), ntsFormLabel: {}"></div>
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
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF010_338]', 
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
	<div style="margin-bottom: 20px">
		<button style="width: 100px; margin-left: 200px" data-bind="text: $i18n('KAF010_43')" class="caret-bottom caret-inline" ></button>
	</div>

	<!-- holiday time -->
	<div class="cf valign-top control-group" data-bind="if: true">
		<!--A6_1 -->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF010_50'), ntsFormLabel: {required: true}"></div>
		</div>
		
		<div class="table-time">
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
						<!--A6_3-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_51')"></th>
						<!--A6_4-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_52')"></th>
						<!--A6_6-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF010_54')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: holidayTime">
					<tr>
						<!--A6_7-->
						<td class="header" data-bind="text: String(frameNo)"></td>
						<!--A6_8 -->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_337]', 
									constraint:'TimeWithDayAttr',
									inputFormat: 'time',
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A6_9 -->
						<td class="right-content"></td>
						<!--A6_11 -->
						<td class="right-content" data-bind="text: $parent.getFormatTime(ko.toJS(actualTime))"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<!-- over time hours -->
	<div class="cf valign-top control-group" data-bind="if: true">
		<!--A7_1 残業時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_50'), ntsFormLabel: {}"></div>
		</div>
		<div class="table-time">
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
						<!--A7_3 申請時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_51')"></th>
						<!--A7_4 事前申請ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_52')"></th>
						<!--A7_6 実績時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_54')"></th>
					</tr>
				</thead>
				<tbody">
					<tr>
						<!--A7_7-->
						<td class="header"
							></td>
						<!--A7_8 残業申請時間入力-->
						<td>
							<input tabindex="12" class="right-content overtimeHoursCheck"
							data-bind=" 
								ntsTimeEditor: { 
									
									option: {width: '85px', timeWithDay: true},
									inputFormat: 'time',
									constraint:'OvertimeAppPrimitiveTime',
									enable: true }" />
						</td>
						<!--A7_9 残業事前申請時間-->
						<td class="right-content"></td>
						<!--A7_11 実績時間-->
						<td class="right-content"></td>
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
        
        created(params: any) {
            const self = this;
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({ height: 120 });
			$("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
			$("#fixed-table-holiday").ntsFixedTable({ height: 120 });
			$("#fixed-table-holiday-test").ntsFixedTable({ height: 216 });
        }
    }

    export interface OverTime {
		frameNo: string;
		applicationTime?: KnockoutObservable<number>;
		preTime?: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
	}
	export interface RestTime {
		frameNo: string;
		start?: KnockoutObservable<number>;
		end?: KnockoutObservable<number>;
	}
	
	export interface HolidayTime {
		frameNo: string;
		start?: KnockoutObservable<number>;
		preApp?: KnockoutObservable<number>;
		actualTime?: KnockoutObservable<number>;
	}
	
	// export class OverTimeInput {
    //         companyID: KnockoutObservable<string>;
    //         appID: KnockoutObservable<string>;
    //         attendanceID: KnockoutObservable<number>;
    //         attendanceName: KnockoutObservable<string>;
    //         frameNo: KnockoutObservable<number>;
    //         timeItemTypeAtr: KnockoutObservable<number>;
    //         frameName: KnockoutObservable<string>;
    //         startTime: KnockoutObservable<number>;
    //         endTime: KnockoutObservable<number>;
    //         applicationTime: KnockoutObservable<number>;
    //         nameID: KnockoutObservable<string>;
            
    //         constructor(
    //             companyID: string,
    //             appID: string,
    //             attendanceID: number,
    //             attendanceName: string,
    //             frameNo: number,
    //             timeItemTypeAtr: number,
    //             frameName: string,
    //             startTime: number,
    //             endTime: number,
    //             applicationTime: number,
    //             nameID: string) {
    //             this.companyID = ko.observable(companyID);
    //             this.appID = ko.observable(appID);
    //             this.attendanceID = ko.observable(attendanceID);
    //             this.attendanceName = ko.observable(attendanceName);
    //             this.frameNo = ko.observable(frameNo);
    //             this.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
    //             this.frameName = ko.observable(frameName);
    //             this.startTime = ko.observable(startTime);
    //             this.endTime = ko.observable(endTime);
    //             this.applicationTime = ko.observable(applicationTime);
    //             this.nameID = ko.observable(nameID);
    //         }
    //     }
}