module nts.uk.at.view.kbt002.k {
    const getTextResoure = nts.uk.resource.getText


    @bean()
    export class KBT002KViewModel extends ko.ViewModel {
        
        empNames: KnockoutObservableArray<any> = ko.observableArray([
            {code : true, name: getTextResoure('KBT002_275')},
            {code : false,name: getTextResoure('KBT002_276')}
        ]);
        selectEmpName :KnockoutObservable<number> = ko.observable(1);
        startDate: KnockoutObservable<number> = ko.observable(null);
        endDate: KnockoutObservable<number> = ko.observable(null);

        mounted(){
            const vm = this
            // const params = nts.uk.ui.windows.getShared("KBT002_K_PARAMS");
        }
        
        getDataExportCsv(){
            const vm = this
            const command: GetDataExportCSV = new GetDataExportCSV({    
                isExportEmployeeName : vm.selectEmpName() === 1 ? true : false,
                startDate: vm.startDate() ? moment.utc(vm.startDate(),'YYYY/MM/DD').toString() : null,
                endDate: vm.endDate() ? moment.utc(vm.endDate(),'YYYY/MM/DD').toString() : null
            });
            service.exportCSV(command).done((res) =>{

            }).fail((err) => {});
        }

        closeDialog(){
            const vm = this;
            vm.$window.close;
        }
    }

    export class GetDataExportCSV{
        isExportEmployeeName : boolean;
        startDate : String;
        endDate : String;
        constructor (init?: Partial<GetDataExportCSV>) {
            $.extend(this, init)
        }
    }
}