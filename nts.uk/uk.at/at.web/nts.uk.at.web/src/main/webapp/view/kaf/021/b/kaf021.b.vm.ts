/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kaf021.b {

    @bean()
    class ViewModel extends ko.ViewModel {

        items: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable("");


        API = {
            getListWorkCycleDto: 'screen/at/ksm003/a/get',
        };

        constructor() {
            super();
        }

        created(params: any) {
            let vm = this;
        }

        mounted() {
            let vm = this;

            
        }

    }

}
