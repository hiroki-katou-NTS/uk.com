module nts.uk.at.view.kaf000_ref.b.component5.viewmodel {

    @component({
        name: 'kaf000-b-component5',
        template: '/nts.uk.at.web/view/kaf_ref/000/b/component5/index.html'
    })
    class Kaf000BComponent5ViewModel extends ko.ViewModel {
        appDispInfoStartupOutput: any;
        prePostAtrName: KnockoutObservable<string>;
        prePostAtrDisp: KnockoutObservable<boolean>;
        created(params: any) {
            const vm = this;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.prePostAtrName = ko.observable("prePostAtrName");
            vm.prePostAtrDisp = ko.observable(false);
            
            vm.prePostAtrName(vm.getPrePostAtrName(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr));
            vm.prePostAtrDisp(vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.applicationSetting.appDisplaySetting.prePostDisplayAtr == 1);
            params.application().prePostAtr(vm.appDispInfoStartupOutput().appDetailScreenInfo.application.prePostAtr); 
        }
    
        mounted() {
            const vm = this;
        }
        
        getPrePostAtrName(prePostAtr: number) {
            const vm = this;    
            if(prePostAtr==0) {
                return vm.$i18n('KAF000_47');    
            } else {
                return vm.$i18n('KAF000_48');    
            }
        }
    }
}