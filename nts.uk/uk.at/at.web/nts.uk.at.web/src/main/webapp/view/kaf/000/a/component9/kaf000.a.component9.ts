module nts.uk.at.view.kaf000.a.component9.viewmodel {

    @component({
        name: 'kaf000-a-component9',
        template: `
			<div id="kaf000-a-component9" style="display: inline-block; margin-left: 5px;" data-bind="if: params">
				<a class="goback link-button" data-bind="click: backtoPre, text: $i18n('KAF000_53')"></a>
			</div>
        `
    })
    class Kaf000AComponent9ViewModel extends ko.ViewModel {
		params: any = null;
        created(params: any) {
            const vm = this;
			vm.params = params;
        }

		backtoPre() {
			window.history.back();	
		}
    }
}