module nts.uk.at.view.kdp002.b {
    export module viewmodel {
        export class ScreenModel {
            
            // B2_2
            workTypeNames: KnockoutObservable<string> = ko.observable("基本給");
            // B3_2
            dayName: KnockoutObservable<string> = ko.observable("基本給");
            // B3_3
            timeName: KnockoutObservable<string> = ko.observable("基本給");
            // G4_2
            checkHandName: KnockoutObservable<string> = ko.observable("基本給");
            // G5_2
            numberName: KnockoutObservable<string> = ko.observable("基本給");
            // G6_2
            laceName: KnockoutObservable<string> = ko.observable("基本給");

            items: KnockoutObservableArray<model.ItemModels> = ko.observableArray([]);
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>  = ko.observable();
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                for (let i = 1; i < 6; i++) {
                    self.items.push(new model.ItemModels('00' + i, '基本給', "description " + i));
                }

                self.columns2 = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KDP002_45") , key: 'code', width: 100},
                    { headerText: nts.uk.resource.getText("KDP002_46"), key: 'name', width: 100 },
                    { headerText: nts.uk.resource.getText("KDP002_47"), key: 'description', width: 150 }
                ]);
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            getData(newValue: number): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                return dfd.promise();
            }

            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
    export module model {

        export class ItemModels {
            code: string;
            name: string;
            description: string;
            constructor(code: string, name: string, description: string) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
        }
    }
}