module nts.uk.at.view.kaf005.shr.footer.viewmodel {
	const template = `
<div id="kaf005-footer" data-bind="with: $parent, visible: $parent.visibleModel.c11_1() || $parent.visibleModel.c11_2() || $parent.visibleModel.c12_1() || $parent.visibleModel.c12_2()">
	<div class="table" style="margin-bottom: 7px;" data-bind="if: visibleModel.c11_1()">
		
		<div style="width: 120px" class="cell valign-top cm-column2">
				<div
					data-bind="ntsFormLabel: {text: titleLabel1}"
					></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" style="width: 472px" data-bind="ntsComboBox: {
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
			<div class="cell valign-center" data-bind="ntsFormLabel: {
				constraint: 'DivergenceReason',
				text: titleLabelInput1
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
												width: '449px'
												}
											}" />
		</div>
	</div>
	
	
	<div class="table" style="margin-bottom: 7px;" data-bind="if: visibleModel.c12_1()">
		<div style="width: 120px" class="cell valign-top cm-column2">
				<div
					data-bind="ntsFormLabel: {text: titleLabel2}"
					></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" style="width: 472px" data-bind="ntsComboBox: {
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
			<div class="cell valign-center" data-bind="ntsFormLabel: {
				constraint: 'DivergenceReason',
				text: titleLabelInput2
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
															width: '449px'
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