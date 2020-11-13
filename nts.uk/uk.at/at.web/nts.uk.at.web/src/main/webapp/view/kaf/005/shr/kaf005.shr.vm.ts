module nts.uk.at.view.kaf005.shr.viewmodel {
	const template = `
	<div class="container cf">
	<div data-bind="if: true">

		<div class="table" data-bind="visible : true">
			<div class="cell valign-center" style="width: 105px;">
				<!--A4_1 勤務種類ラベル-->
				<div class="valign-center control-group"
					data-bind="ntsFormLabel:{required: true, text: $i18n('KAF005_34')}"></div>
				<BR />
				<!--A4_4 就業時間ラベル-->
				<div class="valign-center control-group"
					data-bind="ntsFormLabel:{required: true, text: $i18n('KAF005_35')}"></div>
			</div>
			<div class="cell valign-top" data-bind="if: true"
				style="width: 25px;">
				<!--A4_7 選択ボタン-->
				<button tabindex="7" style="margin-left: 18px" class="workSelect"
					data-bind="enable: true, click : openDialogKdl003, text: $i18n('KAF005_36')"></button>
			</div>
			<div class="cell valign-center">
				<div class="valign-center control-group">
					<!--A4_2 勤務種類コード-->
					<label class="lblWorkTypeCd" data-bind="text: 'text'"></label>
					<!--A4_3 勤務種類名称-->
					<LABEL data-bind="text: 'text'"></LABEL>
				</div>
				<div class="valign-center control-group">
					<!--A4_5 就業時間コード-->
					<label class="lblSiftCd" data-bind="text: 'text'"></label>
					<!--A4_6 就業時間名称-->
					<LABEL data-bind="text: 'text'"></LABEL>
				</div>
			</div>
		</div>


		<!--勤務時間 1-->
		<div class="valign-center control-group">
			<div class="table">
				<div style="width: 113px" class="cell valign-top cm-column2">
					<!--A4_8 勤務時間ラベル-->
					<div class="lblTitle"
						data-bind="ntsFormLabel: {required: true}, text: $i18n('KAF005_37')"
						style="margin-right: 6px"></div>
				</div>
				<div class="cell valign-center">
					<div>
						<!--A4_9 勤務時間From1-->
						<input tabindex="8"
							class="row-cell-margin inputTime-kaf005 right-content"
							id="inpStartTime1"
							data-bind="ntsTimeWithDayEditor: { name: '#[KAF005_333]', constraint:'TimeWithDayAttr',value: 10, enable: true, readonly: false, required: true }" />
						<!--A4_10 ~1-->
						<label class="valign-center link-label-kaf005"
							data-bind=", text: $i18n('KAF005_38')"></label>
					</div>
				</div>
				<div class="cell valign-center">
					<div class="row-cell-margin">
						<!--A4_11 勤務時間To1-->
						<input tabindex="9" id="inpEndTime1"
							class="right-content inputTime-kaf005"
							data-bind="ntsTimeWithDayEditor: {name: '#[KAF005_334]', constraint:'TimeWithDayAttr', value: 10, enable: true, readonly: false, required: true}" />
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="cf valign-top control-group" data-bind="if: true">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="ntsFormLabel: {}, text: $i18n('KAF005_40')"></div>
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
						<td class="header" data-bind="text: frameName"></td>
						<!--A5_6 開始時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_337]', 
									value: startTime, 
									constraint:'TimeWithDayAttr', 
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_338]', 
									value: endTime, 
									constraint:'TimeWithDayAttr', 
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- calculate button A5_8-->
	
	
	<!-- over time hours -->

	<div class="cf valign-top control-group cell"
		style="margin-top: 0px !important">
		<!--A6_1 残業時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="text: $i18n('KAF005_50'), ntsFormLabel: {required: true}"></div>
		</div>
		<div class="table-time" id="overtime-container">
			<table id="fixed-overtime-hour-table">
				<colgroup>
					<col width="110px" />
					<col width="110px" />
					<col width="110px" />
					<col width="110px" />
				</colgroup>
				<thead>
					<tr>
						<th class="ui-widget-header" rowspan="2"></th>
						<!--A6_3 申請時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_51')"></th>
						<!--A6_4 事前申請ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_52')"></th>
						<!--A6_6 実績時間ラベル-->
						<th class="ui-widget-header" rowspan="2" data-bind="text: $i18n('KAF005_54')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: overtimeHours">
					<tr>
						<!--A6_7 残業時間名称-->
						<td class="header"
							data-bind="text: frameName"></td>
						<!--A6_8 残業申請時間入力-->
						<td>
							<input tabindex="15" class="right-content overtimeHoursCheck"
							data-bind=" 
								ntsTimeEditor: {
									name: nameID, 
									value: applicationTime, 
									option: {width: '80px'}, 
									constraint:'OvertimeAppPrimitiveTime',
									enable: true }" />
						</td>
						<!--A6_9 残業事前申請時間-->
						<td class="right-content" data-bind="text: 'preAppTime'"></td>
						<!--A6_11 実績時間-->
						<td class="right-content" data-bind="text: 'caculationTime'"></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- holiday time -->
	<div class="cf valign-top control-group" data-bind="if: true">
		<!--A5_1 休憩時間ラベル-->
		<div class="cm-column" style="display: inline-block; width: 100px">
			<div class="lblTitle pull-left"
				data-bind="ntsFormLabel: {required: true}, text: $i18n('KAF005_70')"></div>
		</div>
		<div class="table-time">
			<table id="fixed-table-holiday">
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
							data-bind="text: $i18n('KAF005_71')"></th>
						<!--A5_4 終了ラベル-->
						<th class="ui-widget-header" rowspan="2"
							data-bind="text: $i18n('KAF005_72')"></th>
					</tr>
				</thead>
				<tbody data-bind="foreach: restTime">
					<tr>
						<!--A5_5 休憩時間順序-->
						<td class="header" data-bind="text: frameName"></td>
						<!--A5_6 開始時刻-->
						<td><input tabindex="12" class="right-content"
							data-bind="
								ntsTimeWithDayEditor: {
									name: '#[KAF005_337]', 
									value: startTime, 
									constraint:'TimeWithDayAttr', 
									enable: false,
									option: {width: '85px', timeWithDay: true}}" /></td>
						<!--A5_7 終了時刻-->
						<td class="right-content" data-bind="text: 'preAppTime'"></td>
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
		
		restTime: KnockoutObservableArray<OverTimeInput> = ko.observableArray([]);
		overtimeHours: KnockoutObservableArray<OverTimeInput> = ko.observableArray([]);
		created() {
			const self = this;
			let restTimes = [];
			let item1 : OverTimeInput = new OverTimeInput('', '', 0, '', 1, 0, '1', 10, 15, null, '');
			let item2 : OverTimeInput = new OverTimeInput('', '', 0, '', 2, 0, '2', 20, 25, null, '');
			let item3 : OverTimeInput = new OverTimeInput('', '', 0, '', 3, 0, '3', null, null, null, '');
			let item4 : OverTimeInput = new OverTimeInput('', '', 0, '', 4, 0, '4', null, null, null, '');
			let item5 : OverTimeInput = new OverTimeInput('', '', 0, '', 5, 0, '5', null, null, null, '');
			let item6 : OverTimeInput = new OverTimeInput('', '', 0, '', 6, 0, '6', null, null, null, '');
			let item7 : OverTimeInput = new OverTimeInput('', '', 0, '', 7, 0, '7', null, null, null, '');
			let item8 : OverTimeInput = new OverTimeInput('', '', 0, '', 8, 0, '8', null, null, null, '');
			let item9 : OverTimeInput = new OverTimeInput('', '', 0, '', 9, 0, '9', null, null, null, '');
			let item10 : OverTimeInput = new OverTimeInput('', '', 0, '', 10, 0, '10', null, null, null, '');
			restTimes.push(item1);
			restTimes.push(item2);
			restTimes.push(item3);
			restTimes.push(item4);
			restTimes.push(item5);
			restTimes.push(item6);
			restTimes.push(item7);
			restTimes.push(item8);
			restTimes.push(item9);
			restTimes.push(item10);
			self.restTime(restTimes);
			self.overtimeHours(restTimes);
		}
		
		mounted() {
			$("#fixed-table").ntsFixedTable({ height: 120 });
			$("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
			$("#fixed-table-holiday").ntsFixedTable({ height: 120 });
			
		}
		
		public openDialogKdl003() {
			
		}
	}
	
	export class OverTimeInput {
            companyID: KnockoutObservable<string>;
            appID: KnockoutObservable<string>;
            attendanceID: KnockoutObservable<number>;
            attendanceName: KnockoutObservable<string>;
            frameNo: KnockoutObservable<number>;
            timeItemTypeAtr: KnockoutObservable<number>;
            frameName: KnockoutObservable<string>;
            startTime: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;
            applicationTime: KnockoutObservable<number>;
            nameID: KnockoutObservable<string>;
            
            constructor(
                companyID: string,
                appID: string,
                attendanceID: number,
                attendanceName: string,
                frameNo: number,
                timeItemTypeAtr: number,
                frameName: string,
                startTime: number,
                endTime: number,
                applicationTime: number,
                nameID: string) {
                this.companyID = ko.observable(companyID);
                this.appID = ko.observable(appID);
                this.attendanceID = ko.observable(attendanceID);
                this.attendanceName = ko.observable(attendanceName);
                this.frameNo = ko.observable(frameNo);
                this.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
                this.frameName = ko.observable(frameName);
                this.startTime = ko.observable(startTime);
                this.endTime = ko.observable(endTime);
                this.applicationTime = ko.observable(applicationTime);
                this.nameID = ko.observable(nameID);
                
            }
        }
	
	
}