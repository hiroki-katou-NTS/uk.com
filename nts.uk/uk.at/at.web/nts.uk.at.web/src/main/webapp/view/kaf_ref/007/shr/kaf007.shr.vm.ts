module nts.uk.at.view.kaf007_ref.shr.viewmodel {

    @component({
        name: 'kaf007-share',
        template: '/nts.uk.at.web/view/kaf_ref/007/shr/index.html'
    })
    class Kaf007ShareViewModel extends ko.ViewModel {
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;   
        created(params: any) {
            const vm = this;
            vm.startTime1 = params.startTime1;
            vm.endTime1 = params.endTime1;
        }
    
        mounted() {
            
        }
    }
    
    export class Kaf007Process {
        public static register() {  
        
        }    
        
        public static update() { 
              
        }
    }
    
    export class AppWorkChange {
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;
        constructor(startTime1: number, endTime1: number) {
            this.startTime1 = ko.observable(startTime1);
            this.endTime1 = ko.observable(endTime1);
        } 
    }
}