module nts.uk.at.view.kaf007_ref.shr.viewmodel {

    @component({
        name: 'kaf007-share',
        template: '/nts.uk.at.web/view/kaf_ref/007/shr/index.html'
    })
    class Kaf007ShareViewModel extends ko.ViewModel {
        workTypeCD: KnockoutObservable<string>;
        workTypeName: KnockoutObservable<string>;
        workTimeCD: KnockoutObservable<string>;
        workTimeName: KnockoutObservable<string>;
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;   
        created(params: any) {
            const vm = this;
            vm.workTypeCD = params.workTypeCD;
            vm.workTypeName = ko.observable("workTypeName");
            vm.workTimeCD = params.workTimeCD;
            vm.workTimeName = ko.observable("workTimeName");
            vm.startTime1 = params.startTime1;
            vm.endTime1 = params.endTime1;
        }
    
        mounted() {
            
        }
        
        openKDL003Click() {
            const vm = this;
            
            vm.$window.storage('parentCodes', {
                workTypeCodes: ['001'],
                selectedWorkTypeCode: '001',
                workTimeCodes: ['001'],
                selectedWorkTimeCode: '001'
            });

            vm.$window.modal('/view/kdl/003/a/index.xhtml').then((result: any) => {
                let dataStored = vm.$window.storage('childData');
                console.log(dataStored);
            });
        }
    }
    
    export class Kaf007Process {
        public static register() {  
        
        }    
        
        public static update() { 
              
        }
    }
    
    export class AppWorkChange {
        workTypeCD: KnockoutObservable<string>;
        workTimeCD: KnockoutObservable<string>;
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;
        constructor(workTypeCD: string, workTimeCD: string, startTime1: number, endTime1: number) {
            this.workTypeCD = ko.observable(workTypeCD);
            this.workTimeCD = ko.observable(workTimeCD);
            this.startTime1 = ko.observable(startTime1);
            this.endTime1 = ko.observable(endTime1);
        } 
    }
}