module nts.uk.at.view.ksc001.f {


    export module viewmodel {

        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            constructor() {
                var self = this;
                this.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, "2010/1/1"));
                }

                this.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSC001_55"), key: 'code', width: 80},
                    { headerText: nts.uk.resource.getText("KSC001_56"), key: 'name', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_57"), key: 'description', width: 150 },
                    { headerText: nts.uk.resource.getText("KSC001_58"), key: 'other', width: 150 }
                ]);

                this.currentCode = ko.observable();
                this.currentCodeList = ko.observableArray([]);
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * function on click save CommonGuidelineSetting
             */
            public saveCommonGuidelineSetting(): void {
                var self = this;
                nts.uk.ui.windows.close();
            }

            /**
             * Event on click cancel button.
             */
            public cancelSaveCommonGuidelineSetting(): void {
                nts.uk.ui.windows.close();
            }

        }     
        
        
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other: string
            constructor(code: string, name: string, description: string, other: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other = other;
            }
        }
    }
}