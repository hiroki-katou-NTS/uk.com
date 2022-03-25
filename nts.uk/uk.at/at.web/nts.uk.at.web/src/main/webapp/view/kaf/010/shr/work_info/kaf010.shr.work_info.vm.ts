module nts.uk.at.view.kaf010.shr.work_info.viewmodel {
    const template = `
     <div data-bind="with: $parent">
	<div style="margin-top: -3px" data-bind="if: workInfo">
		<div class="table" data-bind="visible : true">
			<div class="cell valign-center" style="width: 120px;">
				<!--A4_1 勤務種類ラベル-->
				<div class="valign-center"
					data-bind="text: $i18n('KAF010_34'), ntsFormLabel:{required: true}"></div>
			</div>
			<div class="cell valign-top" data-bind="if: true" style="width: 45px; vertical-align: middle;">
				<!--A4_7 選択ボタン-->
				<button tabindex="7" class="workSelectKAF010"
				data-bind="enable: mode() != 3, click : openDialogKdl003, text: $i18n('KAF010_36')"></button>
			</div>
			<div class="cell valign-center">
				<div class="code-name">
					<!--A4_2 勤務種類コード-->
					<label class="lblWorkTypeCd" data-bind="text: ko.toJS(workInfo).workType.code"></label>
					<!--A4_3 勤務種類名称-->
					<LABEL data-bind="text: ko.toJS(workInfo).workType.name"></LABEL>
				</div>
				<div class="code-name">
					<!--A4_5 就業時間コード-->
					<label class="lblSiftCd" data-bind="text: ko.toJS(workInfo).workTime.code"></label>
					<!--A4_6 就業時間名称-->
					<LABEL data-bind="text: ko.toJS(workInfo).workTime.name"></LABEL>
				</div>
			</div>
		</div>

		<!--勤務時間 1-->
		<div style="margin-top: 12px">
			<div class="table">
				<div style="width: 120px; margin-right: 0px !important" class="cell valign-top cm-column2">
					<!--A4_8 勤務時間ラベル-->
					<div class="lblTitle" data-bind="text: $i18n('KAF010_37'), ntsFormLabel: {required: true}"
						></div>
				</div>
				<div class="cell valign-center">
					<div>
						<!--A4_9 勤務時間From1-->
						<input tabindex="8" class="inputTime-kaf005 right-content" id="inpStartTime1"
							data-bind="ntsTimeWithDayEditor: { 
								name: '#[KAF010_333]',
								constraint:'TimeWithDayAttr',
								value: workInfo().workHours1.start,
								enable: mode() != 3,
								readonly: false,
								required: true }" />
						<!--A4_10 ~1-->
						<label class="valign-center link-label-kaf005" data-bind=", text: $i18n('KAF010_38')"></label>
					</div>
				</div>
				<div class="cell valign-center">
					<div class="row-cell-center">
						<!--A4_11 勤務時間To1-->
						<input tabindex="9" id="inpEndTime1" class="right-content inputTime-kaf005" data-bind="ntsTimeWithDayEditor: {name: '#[KAF010_334]',						
						constraint:'TimeWithDayAttr',
						value: workInfo().workHours1.end,
						enable: mode() != 3,
							 readonly: false,
							 required: true}" />
					</div>
				</div>
			</div>
			<div class="table" style="margin-top: 12px">
				<div style="width: 120px; margin-right: 0px !important" class="cell valign-top cm-column2">
					<!--A4_8 勤務時間ラベル-->
					<div class="lblTitle" data-bind="text: $i18n('KAF010_339'), ntsFormLabel: {required: false}, visible: managementMultipleWorkCyclescheck()"
						></div>
				</div>
				<div class="cell valign-center">
					<div class="cell valign-center" data-bind="visible: managementMultipleWorkCyclescheck()">
						<!-- A4_12 -->
						<input tabindex="10" class="inputTime-kaf005 right-content" id="inpStartTime2"
							data-bind="ntsTimeWithDayEditor: { 
								name: '#[KAF010_335]',
								constraint:'TimeWithDayAttr',
								value: workInfo().workHours2.start,
								enable: mode() != 3,
								readonly: false,
								required: false }" />
						<label class="valign-center link-label-kaf005" data-bind=", text: $i18n('KAF010_39')"></label>
					</div>
					
				</div>
				<div class="cell valign-center">
					<div class="cell valign-center" data-bind="visible: managementMultipleWorkCyclescheck()">
						<!-- A4_14 -->
						<input tabindex="11" id="inpEndTime2" class="right-content inputTime-kaf005" data-bind="ntsTimeWithDayEditor: {name: '#[KAF010_336]',
							constraint:'TimeWithDayAttr',
							value: workInfo().workHours2.end,
							enable: mode() != 3,
							readonly: false,
							required: false}" />
					</div>
					
				</div>
			</div>
			<div style="margin-top: 12px; margin-left: 113px">
				<div style="width: 133px; margin-top: 11px; margin-left: 5px; margin-bottom: 11px" data-bind="ntsCheckBox: { checked: isGoWorkAtr, text: $i18n('KAF010_3')}, visible: workTimeCheckBoxVisible()"></div>
				<div style="margin-top: 11px; margin-left: 5px; margin-bottom: 11px" data-bind="ntsCheckBox: { checked: isBackHomeAtr, text: $i18n('KAF010_4')}, visible: workTimeCheckBoxVisible()"></div>
			</div>
		</div>
	</div>
</div>
    `
    @component({
        name: 'kaf010-share-work-info',
        template: template
    })
    class KAF010ShrWorkInfoModel extends ko.ViewModel {

        created(params: any) {
			const vm = this;
        }
    }

    export interface Work {
		code?: string;
		name?: string;
	}
	export interface WorkHours {
		start: KnockoutObservable<number>;
		end: KnockoutObservable<number>;
	}
	export class WorkInfo {
		
		workType: KnockoutObservable<Work> = ko.observable();
		
		workTime: KnockoutObservable<Work> = ko.observable();
		
		workHours1: WorkHours;

		workHours2: WorkHours;

		
	}


}