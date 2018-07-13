module nts.uk.com.view.cas004.b {
    import getText = nts.uk.resource.getText;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;

    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<model.ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            items: KnockoutObservableArray<model.ItemModel2>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                let self = this;

                self.itemList = ko.observableArray([
                    new model.ItemModel('1', '基本給'),
                    new model.ItemModel('2', '役職手当'),
                    new model.ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
                ]);

                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);

                this.items = ko.observableArray([]);
                let str = ['a0', 'b0', 'c0', 'd0'];
                for (let j = 0; j < 4; j++) {
                    for (let i = 1; i < 11; i++) {
                        let code = i < 10 ? str[j] + '0' + i : str[j] + i;
                        this.items.push(new model.ItemModel2(code, code));
                    }
                }
                this.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CAS004_24'), prop: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText('CAS004_14'), prop: 'name', width: 230 }
                ]);
                this.currentCode = ko.observable();
                this.currentCodeList = ko.observableArray([]);
            }
            /**
             * functiton start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //                bloc                  

                //                self.getData().done(function() {
                //                    self.getListWorkplace().done(() => {
                //                        block.clear();
                //                        if (self.component.listRole().length != 0)
                //                            self.selectRoleCodeByIndex(0);
                //                        else
                //                            se);
                //                        dfd.resolve();
                //                    });
                //                });
                //                block.clear();
                dfd.resolve();
                return dfd.promise();
            }//end start page


            /**
            * functiton closeDialog
            */
            closeDialog() {

            }// end closeDialog

            /**
            * functiton decision
            */
            decision() {

            }// end decision

        }//end screenModel
    }//end viewmodel

    //module model
    export module model {
        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemModel2 {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }//end module model
}//end module