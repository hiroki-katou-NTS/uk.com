module nts.uk.at.view.kaf000_ref.a.component8.viewmodel {
	import ActualContentDisplayDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.ActualContentDisplayDto;
	
    @component({
        name: 'kaf000-a-component8',
        template: '/nts.uk.at.web/view/kaf_ref/000/a/component8/index.html'
    })
    class Kaf000AComponent8ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
		actualContentDisplayDtoLst: KnockoutObservableArray<ActualContentDisplayDto> = ko.observableArray([]);
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            
            vm.appDispInfoStartupOutput.subscribe(value => {
				vm.actualContentDisplayDtoLst(value.appDispInfoWithDateOutput.opActualContentDisplayLst);
				vm.actualContentDisplayDtoLst.valueHasMutated();
            });
        }
    
        mounted() {
            const vm = this;
        }
    }
}