module nts.uk.at.view.kdw001.j {
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        export class ScreenModel {
            //Declare import cScreenmodel, dScreenmodel
            cScreenmodel: any;
            dScreenmodel: any;

            //Declare for grid list
            items: KnockoutObservableArray<ItemModel>;
            //columns: KnockoutObservableArray<NtsGridListColumn>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            //Declare wizard properties
            stepList: Array<NtsWizardStep>;
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;

            constructor() {
                var self = this;

                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kdw001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kdw001.d.viewmodel.ScreenModel();

                //Init for grid list
                this.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給'));
                }

                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 80, hidden: true },
                    { headerText: getText('KDW001_85'), key: 'name', width: 200 }
                ]);

                this.currentCode = ko.observable();

                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);

                //Get activeStep value from a screen or c screen
                __viewContext.transferred.ifPresent(data => {
                    self.activeStep(data.activeStep);
                });
                //self.stepSelected = ko.observable({ id: 'step-2', content: '.step-2' });
            }

            opendScreenD() {
                nts.uk.request.jump("/view/kdw/001/b/index.xhtml", { "activeStep": 2, "screenName": "J" });
            }

            opendScreenC() {
                nts.uk.request.jump("/view/kdw/001/b/index.xhtml", { "activeStep": 0 });
            }

            navigateView() {
                nts.uk.request.jump("/view/kdw/001/a/index.xhtml");
            }
        }

        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }

}
