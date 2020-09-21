module nts.uk.at.view.kaf000_ref.a.component9.viewmodel {
	import ActualContentDisplayDto = nts.uk.at.view.kaf000_ref.shr.viewmodel.ActualContentDisplayDto;
	
    @component({
        name: 'kaf000-b-component9',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component9/index.html'
    })
    class Kaf000BComponent9ViewModel extends ko.ViewModel {
		appType: KnockoutObservable<number> = null;
        appDispInfoStartupOutput: any;
		actualContentDisplayDtoLst: KnockoutObservableArray<ActualContentDisplayDto> = ko.observableArray([]);
        created(params: any) {
            const vm = this;
			vm.appType = params.appType;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            
			vm.actualContentDisplayDtoLst(params.appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst);
			vm.actualContentDisplayDtoLst.valueHasMutated();
			
            vm.appDispInfoStartupOutput.subscribe(value => {
				vm.actualContentDisplayDtoLst(value.appDispInfoWithDateOutput.opActualContentDisplayLst);
				vm.actualContentDisplayDtoLst.valueHasMutated();
            });
        }
    
        mounted() {
            const vm = this;
        }

		formatTime(value: any) {
			let s = nts.uk.time.format.byId(`ClockDay_Short_HM`, value);	
			return s.replace(/当日/g,'');
		}
    }
}