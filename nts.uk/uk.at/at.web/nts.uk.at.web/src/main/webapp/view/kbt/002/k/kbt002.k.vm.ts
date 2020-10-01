module nts.uk.at.view.kbt002.k {
    const getTextResoure = nts.uk.resource.getText


    @bean()
    export class KBT002KViewModel extends ko.ViewModel {
        
        empNames: KnockoutObservableArray<any> = ko.observableArray([
            {code : 1, name: getTextResoure('KBT002_275')},
            {code : 2,name: getTextResoure('KBT002_276')}
        ]);
        selectEmpName :KnockoutObservable<number> = ko.observable(2);
        startDate: KnockoutObservable<number> = ko.observable(moment.now());
        endDate: KnockoutObservable<number> = ko.observable(moment.now());

        mounted(){
            
        }
        
        getDataExportCsv(){
            const vm = this
            const command: GetDataExportCSV = new GetDataExportCSV({    
                isExportEmployeeName : vm.selectEmpName() === 1 ? true : false,
                startDate: moment.utc(vm.startDate(),'YYYY/MM/DD').toISOString(),
                endDate: moment.utc(vm.endDate(),'YYYY/MM/DD').toISOString()
            });
            service.exportCSV(command).done((res) =>{

            }).fail((err) => {});
        }

        closeDialog(){
            const vm = this;
            vm.$window.close();
        }
    }

    export class GetDataExportCSV{
        isExportEmployeeName : boolean;
        startDate : any;
        endDate : any;
        constructor (init?: Partial<GetDataExportCSV>) {
            $.extend(this, init)
        }
    }
}