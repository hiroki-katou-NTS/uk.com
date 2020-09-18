/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr003.b {
    @bean()
    export class KMR003BViewModel extends ko.ViewModel {
        errors: KnockoutObservableArray<any> = ko.observableArray([]);
        selected: KnockoutObservable<any>= ko.observable(null);
        constructor(params: any) {
            super();
            let self = this
            self.errors(params);
        }

        created() {
            let self = this
            _.extend(window, { self });
        }

        mounted(){
            $("#B3_1").focus();
        }

        close(){
            let self = this;
            self.$window.close();
        }
    }
}
