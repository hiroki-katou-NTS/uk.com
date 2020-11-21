module nts.uk.at.view.kaf005.shr.footer.viewmodel {
	const template = `
<div id="kaf005-footer" data-bind="with: $parent">
	<div class="table" style="margin-bottom: 5px;" data-bind="if: visibleModel.c11_1()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: {}, text: $i18n('KAF005_90', [messageInfos()[0].titleDrop()])"></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" data-bind="ntsComboBox: {
					options: messageInfos()[0].listDrop,
					optionsValue: 'code',
					value: messageInfos()[0].selectedCode,
					optionsText: 'name',
					required: true
					}">
			</div>
		</div>
	</div>
	
	<div class="table" style="margin-bottom: 5px;" data-bind="if: visibleModel.c11_2()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: {}, text: $i18n('KAF005_92', [messageInfos()[0].titleInput()])"></div>
		</div>
		<div class="cell valign-center">
			<input style="width: 390px" data-bind="ntsTextEditor: {
														value: ko.observable(''),
														option: {
															textalign: 'left'
															}
														}" />
		</div>
	</div>
	
	
	<div class="table" style="margin-bottom: 5px;" data-bind="if: visibleModel.c12_1()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: {}, text: $i18n('KAF005_90', [messageInfos()[0].titleDrop()])"></div>
		</div>
		<div class="cell valign-center">
			<div id="combo-box" data-bind="ntsComboBox: {
					options: messageInfos()[1].listDrop,
					optionsValue: 'code',
					value: messageInfos()[1].selectedCode,
					optionsText: 'name',
					required: true
					}">
			</div>
		</div>
	</div>
	
	<div class="table" style="margin-bottom: 5px;" data-bind="if: visibleModel.c12_1()">
		<div style="width: 120px" class="cell col-1">
			<div class="cell valign-center" data-bind="ntsFormLabel: {}, text: $i18n('KAF005_92', [messageInfos()[0].titleInput()])"></div>
		</div>
		<div class="cell valign-center">
			<input style="width: 390px" data-bind="ntsTextEditor: {
														value: ko.observable(''),
														option: {
															textalign: 'left'
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