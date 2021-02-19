module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;
	const editorTemplate = `
<div>
	<table class="layout-grid">
		<tbody>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $i18n('KMP001_16')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.workplaceName"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_9')"></div>
				</td>
				<td class="label-column-a-right">
					<div data-bind="text: model.code"></div>
					<div id="right_name" data-bind="text: model.businessName"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_20')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.entryDate"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_21')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.retiredDate"></div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div>
	<div>
		<div class="list-card" 
			data-bind="component: { name: 'card-list-component', params: { model: model, 
				stampCardEdit: stampCardEdit, 
				textInput: textInput, 
				methodEdit: methodEdit}}"></div>
	</div>
</div>
`

	@component({
		name: 'editor-area',
		template: editorTemplate
	})
	class RightPanelComponent extends ko.ViewModel {
		model!: Model;
		stampCardEdit!: StampCardEdit;
		textInput: KnockoutObservable<string>;
		methodEdit: KnockoutObservable<boolean>;
		created(params: any) {
			const vm = this;

			vm.model = params.model;
			vm.stampCardEdit = params.stampCardEdit;
			vm.textInput = params.textInput;
			vm.methodEdit = params.methodEdit;
		}

		mounted() {
			const vm = this;
		
			_.extend(window, { vm });
		}
	}
}