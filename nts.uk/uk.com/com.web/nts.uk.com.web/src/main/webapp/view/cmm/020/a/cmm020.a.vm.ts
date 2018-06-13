module nts.uk.com.view.cmm020.a {

    //    import MasterCopyDataCommand = nts.uk.com.view.cmm001.f.model.MasterCopyDataCommand;

    export module viewmodel {

        export class ScreenModel {

            texteditor: any;
            simpleValue: KnockoutObservable<string>;

            listEra: KnockoutObservableArray<any>;
            eraName: KnockoutObservable<string>;
            eraSymbol: KnockoutObservable<string>;
            startDate: KnockoutObservable<string>;
            inputData: KnockoutObservable<any>;
            currentName: KnockoutObservable<string>;
            currentSymbol: KnockoutObservable<string>;
            currentStartDate: KnockoutObservable<string>;
            currentCode: KnockoutObservable<any;>;
            
            constructor() {
                let self = this;

                self.simpleValue = ko.observable("123");
                self.texteditor = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "Placeholder for text editor",
                        width: "100px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.listEra = ko.observableArray([]);
                self.eraName = ko.observable('');
                self.eraSymbol = ko.observable('');
                self.startDate = ko.observable('');
                self.inputData = ko.observable();
                self.currentName = ko.observable('');
                self.currentSymbol = ko.observable('');
                self.currentStartDate = ko.observable('');
                self.currentCode = ko.observable();
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();

                for (let i = 0; i <= 3; i++) {
                    this.listEra.push(new model.EraItem("test", "00", "fsf", "fdsfas"));
                }

                //                service.getEraList().then(function(listEra: Array<model.EraItem>) {
                //                    self.listEra(listEra);
                //                    dfd.resolve();
                //                });
                dfd.resolve();
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
                systemType: boolean;

                constructor(eraId: string, eraName: string, eraSysbol: string, startDate: string) {
                    this.eraId = eraId;
                    this.eraName = eraName;
                    this.eraSymbol = eraSysbol;
                    this.startDate = startDate;
                }
            }
        }
    }
}