module nts.uk.com.view.cmm051.b {
    @bean()
    class ViewModel extends ko.ViewModel {
        periodDate: KnockoutObservable<any> =ko.observable(null);
        startDate: KnockoutObservable<any> = ko.observable(moment());
        endDate: KnockoutObservable<any> = ko.observable(moment());
        required: KnockoutObservable<boolean>;
        isUpdate: boolean = false;
        isCreate : boolean = false;
        constructor(){
            super();
            let vm = this;
            vm.required = ko.observable(true);
            vm.periodDate(
                { startDate: vm.startDate(),
                    endDate: vm.endDate()
                });
        }
        created(){

        }
        mounted(){

        }
        execution(){

        }
        close():void{
            nts.uk.ui.windows.close();
        }
    }

}