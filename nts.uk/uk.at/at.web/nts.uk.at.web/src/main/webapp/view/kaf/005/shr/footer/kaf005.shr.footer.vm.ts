module nts.uk.at.view.kaf005.shr.footer.viewmodel {
	const template = `
<div id="kaf005-footer" data-bind="with: $parent">
	<div class="table" style="margin-bottom: 12px;" data-bind="if: visibleModel.c11_1()">
		
		<div style="width: 120px" class="cell valign-top cm-column2">
				<div class="lblTitle"
					data-bind="text: $i18n('KAF005_90', [messageInfos()[0].titleDrop()]), ntsFormLabel: {}"
					></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" data-bind="ntsComboBox: {
					enable: outputMode,
					options: messageInfos()[0].listDrop,
					optionsValue: 'code',
					value: messageInfos()[0].selectedCode,
					optionsText: 'name',
					required: false,
					name: '#[KAF005_91]'
					}">
			</div>
		</div>
	</div>
	
	<div class="table" style="margin-bottom: 12px;" data-bind="if: visibleModel.c11_2()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center labelCustom" data-bind="ntsFormLabel: {
				constraint: 'DivergenceReason',
				text: $i18n('KAF005_92', [messageInfos()[0].titleInput()])
			}"></div>
		</div>
		<div class="cell valign-center">
			<input data-bind="ntsTextEditor: {
											enable: outputMode,
											name: '#[KAF005_347]',
											value: messageInfos()[0].valueInput,
											constraint: 'DivergenceReason',
											option: {
												textalign: 'left',
												width: '445px'
												}
											}" />
		</div>
	</div>
	
	
	<div class="table" style="margin-bottom: 12px;" data-bind="if: visibleModel.c12_1()">
		<div style="width: 120px" class="cell valign-top cm-column2">
				<div class="lblTitle"
					data-bind="text: $i18n('KAF005_90', [messageInfos()[1].titleDrop()]), ntsFormLabel: {}"
					></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" data-bind="ntsComboBox: {
					enable: outputMode,
					options: messageInfos()[1].listDrop,
					optionsValue: 'code',
					value: messageInfos()[1].selectedCode,
					optionsText: 'name',
					required: false,
					name: '#[KAF005_91]'
					}">
			</div>
		</div>
	</div>
	
	<div class="table" style="margin-bottom: 12px;" data-bind="if: visibleModel.c12_2()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center labelCustom" data-bind="ntsFormLabel: {
				constraint: 'DivergenceReason',
				text: $i18n('KAF005_92', [messageInfos()[1].titleInput()])
			}"></div>
		</div>
		<div class="cell valign-center">
			<input data-bind="ntsTextEditor: {
														enable: outputMode,
														name: '#[KAF005_347]',
														value: messageInfos()[1].valueInput,
														constraint: 'DivergenceReason',
														option: {
															textalign: 'left',
															width: '445px'
															}
														}" />
		</div>
	</div>
	
	
</div>


	`
	@component({
		name: 'kaf005-share-footer',
		template: template
	})
	class KAF005ShrFooterModel extends ko.ViewModel {
		itemList: KnockoutObservableArray<ItemModel>;
		selectedCode: KnockoutObservable<String>;
		created() {
			const self = this;
			
		}
		
		mounted() {
			const self = this;
		}
	}
	export class ItemModel {
	    code: string;
	    name: string;
	
	    constructor(code: string, name: string) {
	        this.code = code;
	        this.name = name;
	    }
	}
	
	export interface MessageInfo {
		selectedCode: KnockoutObservable<String>;
		titleDrop: KnockoutObservable<String>;
		listDrop: KnockoutObservableArray<ItemModel>;
		titleInput: KnockoutObservable<String>;
		valueInput: KnockoutObservable<String>;
	}
	
	
	
	
	
	
	
	
	
	
}