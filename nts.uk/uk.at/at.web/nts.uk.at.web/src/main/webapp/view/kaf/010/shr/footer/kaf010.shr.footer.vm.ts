module nts.uk.at.view.kaf010.shr.footer.viewmodel {
    const template = `
	<div id="kaf000-a-component5">
	<div class="table" style="margin-bottom: 5px;" data-bind="visible: $parent.selectReflectDivergenceCheck()">
		<div class="cell" style="width: 120px;">
			<div class="cell valign-center" data-bind="ntsFormLabel: { text: $i18n('KAF010_90') }"></div>
		</div>
		<div class="cell valign-center">
			<div id="kaf000-a-component5-comboReason" style="width: 472px"
				data-bind="ntsComboBox: {
								name: $i18n('KAF010_91'),
								options: $parent.comboDivergenceReason,
								optionsValue: 'divergenceReasonCode',
								optionsText: 'reason',
								value: $parent.selectedDivergenceReasonCode,
								columns: [{ prop: 'comboBoxText', length: 20 }],
								required: $parent.comboDivergenceReason()[0] && $parent.comboDivergenceReason()[0].reasonRequired == 0 }">
			</div>
		</div>
	</div>
	<div class="table" style="margin-top: 5px;" data-bind="visible: $parent.inputReflectDivergenceCheck()">
		<div class="cell" style="width: 120px;">
			<div class="cell valign-center" data-bind="ntsFormLabel: { 
													text: $i18n('KAF010_92'),
													constraint: 'DivergenceReasonContent',
													required: $parent.comboDivergenceReason()[0] && $parent.comboDivergenceReason()[0].reasonRequired == 0
												}"></div>
		</div>
		<div class="cell valign-center">
			<textarea style="height: 80px;" id="kaf000-a-component5-textReason"
				data-bind="ntsMultilineEditor: {
							name: $i18n('KAF010_93'),
							value: $parent.divergenceReasonText,
							constraint: 'DivergenceReasonContent',
							option: {
								resizeable: false,
		                    	width: '449',
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