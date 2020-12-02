module nts.uk.at.view.kaf010.shr.footer.viewmodel {
    const template = `
	<div id="kaf000-a-component5">
	<div class="table" style="margin-bottom: 5px;" data-bind="visible: $parent.selectReflectDivergenceCheck()">
		<div class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: { text: $i18n('KAF010_90') }"></div>
		</div>
		<div class="cell valign-center">
			<!-- <div id="kaf000-a-component5-comboReason" style="width: 390px"
				data-bind="ntsComboBox: {
								name: $i18n('KAF000_51'),
								options: reasonTypeItemLst,
								optionsValue: 'appStandardReasonCD',
								optionsText: 'reasonForFixedForm',
								value: opAppStandardReasonCD,
								columns: [{ prop: 'reasonForFixedForm', length: 20 }],
								required: appReasonCDRequired }">
			</div> -->
		</div>
	</div>
	<div class="table" style="margin-top: 5px;" data-bind="visible: $parent.inputReflectDivergenceCheck()">
		<div class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: { text: $i18n('KAF010_92') }"></div>
		</div>
		<div class="cell valign-center">
			<textarea style="height: 80px;" id="kaf000-a-component5-textReason"
				data-bind="ntsMultilineEditor: {
							name: $i18n('KAF000_52'),
							option: {
								resizeable: false,
		                    	width: '450',
		                    	textalign: 'left'
		                   	}}"></textarea>
		</div>
	</div>
</div>
    `

    @component({
        name: 'kaf010-share-footer',
        template: template
    })
    class KAF010ShrFooterModel extends ko.ViewModel {
        
        created(params: any) {
            const self = this;
        }
    }
}