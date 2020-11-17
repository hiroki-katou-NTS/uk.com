module nts.uk.at.view.kaf005.shr.work_info.viewmodel {
	const template = `
	<div data-bind="with: $parent">
<div data-bind="if: workInfo">

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
		<div class="cell valign-top" data-bind="if: true" style="width: 25px;">
			<!--A4_7 選択ボタン-->
			<button tabindex="7" style="margin-left: 18px" class="workSelect"
				data-bind="enable: true, click : openDialogKdl003, text: $i18n('KAF005_36')"></button>
		</div>
		<div class="cell valign-center">
			<div class="valign-center control-group">
				<!--A4_2 勤務種類コード-->
				<label class="lblWorkTypeCd" data-bind="text: ko.toJS(workInfo).workType.code"></label>
				<!--A4_3 勤務種類名称-->
				<LABEL data-bind="text: ko.toJS(workInfo).workType.name"></LABEL>
			</div>
			<div class="valign-center control-group">
				<!--A4_5 就業時間コード-->
				<label class="lblSiftCd" data-bind="text: ko.toJS(workInfo).workTime.code"></label>
				<!--A4_6 就業時間名称-->
				<LABEL data-bind="text: ko.toJS(workInfo).workTime.name"></LABEL>
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
						data-bind="ntsTimeWithDayEditor: { 
							name: '#[KAF005_333]',
							constraint:'TimeWithDayAttr',
							value: workInfo().workHours1.start,
							enable: true,
							readonly: false,
							required: true }" />
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
						data-bind="ntsTimeWithDayEditor: {name: '#[KAF005_334]',
						 constraint:'TimeWithDayAttr',
						 value: workInfo().workHours1.end,
						 enable: true,
						 readonly: false,
						 required: true}" />
				</div>
			</div>
		</div>
		
		
		<div class="table">
			<div style="width: 113px" class="cell valign-top cm-column2">
				<div class="lblTitle"
					
					style="margin-right: 6px"></div>
			</div>
			<div class="cell valign-center">
				<div>
					<!--A4_12 勤務時間From1-->
					<input tabindex="8"
						class="row-cell-margin inputTime-kaf005 right-content"
						id="inpStartTime1"
						data-bind="ntsTimeWithDayEditor: { 
						name: '#[KAF005_333]',
						constraint:'TimeWithDayAttr',
						value: workInfo().workHours2.start,
						enable: true,
						readonly: false,
						required: true }" />
					<!--A4_13 ~1-->
					<label class="valign-center link-label-kaf005"
						data-bind=", text: $i18n('KAF005_38')"></label>
				</div>
			</div>
			<div class="cell valign-center">
				<div class="row-cell-margin">
					<!--A4_14 勤務時間To1-->
					<input tabindex="9" id="inpEndTime1"
						class="right-content inputTime-kaf005"
						data-bind="ntsTimeWithDayEditor: {
						name: '#[KAF005_334]',
						constraint:'TimeWithDayAttr',
						value: workInfo().workHours2.end,
						enable: true,
						readonly: false,
						required: true}" />
				</div>
			</div>
		</div>
	</div>
</div>
</div>
	`
	@component({
		name: 'kaf005-share-work-info',
		template: template
	})
	class VModel extends ko.ViewModel {
		created(params: any) {
			const self = this;
		}
		mounted() {
			
		}
		
	}
	
	export interface Work {
		code?: string;
		name?: string;
	}
	export interface WorkHours {
		start?: KnockoutObservable<number>;
		end?: KnockoutObservable<number>;
	}
	export interface WorkInfo {
		
		workType: KnockoutObservable<Work>;
		
		workTime: KnockoutObservable<Work>;
		
		workHours1 : WorkHours;
		
		workHours2 : WorkHours;
	}
}