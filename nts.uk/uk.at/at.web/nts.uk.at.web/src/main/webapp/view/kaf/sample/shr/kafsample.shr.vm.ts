module nts.uk.at.view.kafsample.shr.viewmodel {

    @component({
        name: 'kafsample-share',
        template: `
       		<div>
				<div class="table">
					<div class="cell col-1">
						<!-- A7 -->
						<div class="cell valign-center" data-bind="ntsFormLabel: {required:true}, text: $i18n('KAF007_13')"></div>
					</div>
					<div class="cell valign-center col-3">
						<!-- A7_1 -->
						<input class="inputTime" data-bind="ntsTimeWithDayEditor: { 
														constraint:'TimeWithDayAttr',
														value: $parent.time, 
														enable: true, 
														readonly: false,	
														required: true,
														name: $i18n('KAF007_62') }" />
					</div>
				</div>
			</div>
		`
    })
    export class KafSampleShareViewModel extends ko.ViewModel {
	}
}