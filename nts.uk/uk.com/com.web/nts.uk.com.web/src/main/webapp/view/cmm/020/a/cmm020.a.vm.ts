module nts.uk.com.view.cmm020.a {

    //    import MasterCopyDataCommand = nts.uk.com.view.cmm001.f.model.MasterCopyDataCommand;

    export module viewmodel {

        export class ScreenModel {

            listEra: KnockoutObservableArray<any>;
            eraName: KnockoutObservable<string>;
            eraSymbol: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            inputData: KnockoutObservable<any>;

            constructor() {
                let self = this;
                self.listEra = ko.obvervableArray([]);
                self.eraName = ko.observable('');
                self.eraSymbol = ko.observable('');
                self.startDate = ko.observable('');
                self.inputData = ko.observable();
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();

                service.getEraList().then(function(listEra: Array<model.EraItem>) {
                    self.listEra(listEra);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            public newItem() {
            
            }
            
            /**
             * createEra
             */
            public createEra(): void {
                var self = this;
                service.createEraName(self.inputData).done(function(res: any) {

                }).fail(function(res: any) {
                    console.log(res);
                });
            }

            /**
             * deleteEra
             */
            public deleteEra(): void {
                var self = this;
                service.deleteEraName(self.inputData).done(function(res: any) {

                }).fail(function(res: any) {
                    console.log(res);
                });
            }
        }

        export module model {

            export class EraItem {
                eraId: string;
                eraName: string;
                eraSymbol: string;
                startDate: string;
                endDate: string;
                systemValue: boolean;

                constructor() {

                }
            }
        }
    }
}