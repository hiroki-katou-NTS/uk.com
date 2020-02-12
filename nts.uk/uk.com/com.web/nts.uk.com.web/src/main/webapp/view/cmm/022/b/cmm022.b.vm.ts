module nts.uk.com.view.cmm022.b.viewmodel {
    import blockUI = nts.uk.ui.block;

    const LAST_INDEX_ERA_NAME_SYTEM: number = 3;
        export class ScreenModel {
            
            settings: KnockoutObservableArray<any> = ko.observableArray([]);
            columns: KnockoutObservableArray<any>
            columnsItem: KnockoutObservableArray<any>
            itemSelected: KnockoutObservable<CommonMasterItem> = ko.observable();
            items: KnockoutObservableArray<any> = ko.observableArray([]);
            selected: KnockoutObservable<CommonMasterItem> = ko.observable();
            listItems: KnockoutObservableArray<any> = ko.observableArray([]);
            constructor() {
                let self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'code', width: 80 },
                    { headerText: nts.uk.resource.getText("CMM022_B1_7"), key: 'name', width: 160 },
                    { headerText: nts.uk.resource.getText("CMM022_B1_8"), key: 'message', width: 160 }
                ]);
                self.columnsItem = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CMM022_B1_6"), key: 'code', width: 80, hidden: true },
                    { headerText: nts.uk.resource.getText("CMM022_B1_7"), key: 'name', width: 160 },
                    { headerText: nts.uk.resource.getText("CMM022_B1_8"), key: 'message', width: 160 }
                ]);
            }

            /**
             * start page
             */
            public start_page(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                let item = {
                    code: 1,
                    name: "b",
                    displayNum: 1
                }
                let array = [];
                array.push(item);
                let param = {
                            title: "a",
                            listItem: array
                }
                self.itemSelected(param);
                dfd.resolve();

                return dfd.promise();
            }
            
            newItem(){
                nts.uk.ui.windows.sub.modal('/view/cmm/022/b/index.xhtml').onClosed(function(): any {
                });
            }
            
            // close dialog
            closeDialog() {
                nts.uk.ui.windows.close();
            }

        }

    export interface IItem {
        code: number;
        name: string;
        displayNum: number;
    }
        
    class Item{
        code: string;
        name: string;
        displayNum: number;
        constructor(param: IItem){
            let self = this;
            self.code = ko.observable(param.code);
            self.name = ko.observable(param.name);
            self.displayNum = ko.observable(param.displayNum);
        }
    }
        
    export interface ICommonMasterItem {
        title: string;
        listItem: Array<Item>;
    }
    
    class CommonMasterItem {
        title: KnockoutObservable<string>;
        
        listItem: KnockoutObservableArray<Item>;

        constructor(param: ICommonMasterItem) {
            let self = this;
            self.title = ko.observable(param.title);
            self.listItem = ko.observable(param.listItem);
        }
    }
    
}