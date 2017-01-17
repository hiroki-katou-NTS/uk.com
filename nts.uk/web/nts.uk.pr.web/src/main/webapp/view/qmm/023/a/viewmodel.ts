module qmm023.a.viewmodel {
    export class ScreenModel {
        constraint: string = 'LayoutCode';
        texteditor1: any;
        texteditor2: any;
        texteditor3: any;
        codeValue: KnockoutObservable<any>;
        items: KnockoutObservableArray<TaxModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        currentTax: KnockoutObservable<TaxModel>;
        nameValue: KnockoutObservable<string>;
        taxLimitValue: KnockoutObservable<string>;
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        allowEditCode: KnockoutObservable<boolean> = ko.observable(false);
        //isUpdate: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            var self = this;
            //constructor of gridList
            this.items = ko.observableArray([
                new TaxModel('01', '基本給', "description 1"),
                new TaxModel('02', '役職手当', "description 2"),
                new TaxModel('03', '基12本ghj給', "description 3"),
                new TaxModel('04', '基12s本ghj給', "description 4"),
                new TaxModel('05', '基12d本ghj給', "description 5"),
                new TaxModel('06', '基12fg本ghj給', "description 6"),
                new TaxModel('07', '基12h本ghj給', "description 7"),
                new TaxModel('08', '基12本fghj給', "description 8"),
                new TaxModel('09', '基12jk本ghj給', "description 9")
            ]);

            //self.items(ko.toJS(self.items()));
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 50 },
                { headerText: '名称', prop: 'name', width: 120 },
                { headerText: '説明', prop: 'taxLimit', width: 170 }
            ]);
            self.currentCode = ko.observable(null);
            self.currentCodeList = ko.observableArray([]);
            //finding the first object
            self.currentTax = ko.observable(ko.mapping.fromJS(_.first(self.items())));
            console.log(self.currentTax());
            self.codeValue = ko.observable(self.currentTax().code);
            self.nameValue = ko.observable(self.currentTax().name);
            self.taxLimitValue = ko.observable(self.currentTax().taxLimit);

            //get event when hover on table by subcribe
            self.currentCode.subscribe(function(codeChanged) {
                self.currentTax(ko.mapping.fromJS(self.getTax(codeChanged)));
                self.codeValue(self.currentTax().code);
                self.nameValue(self.currentTax().name);

            });

            //constructor of textEditor
            //            self.codeValue = ko.computed(function() {
            //                return ko.observabl      //            })

            self.texteditor1 = {
                //                value: self.codeValue(),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100px",
                })),
                required: ko.observable(true),
                //                enable: ko.observable(false),
                //                readonly: ko.observable(true)
            };
            self.texteditor2 = {
                //                value: self.codeValue(),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100px",
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(true)
            };
            self.texteditor3 = {
                //                value: self.codeValue(),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100px",
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(true)
            };
        }

        getTax(codeNew): TaxModel {
            let self = this;
            let tax: TaxModel = _.find(self.items(), function(item) {
                return item.code === codeNew;
            });

            return tax;
        }

        refreshLayout(): void {
            let self = this;
            //            let current = self.currentTax();
            //            current.code = "";
            //            current.name = "";
            //            current.taxLimit = "";
            self.allowEditCode(true);
            self.currentTax(ko.mapping.fromJS(new TaxModel('', '', '')));
            self.isUpdate(false);
        }
        insertData() {
            let self = this;
            let newData = ko.toJS(self.currentTax());
            if (self.isUpdate() === false) {
                self.items.push(newData);
                self.isUpdate = ko.observable(true);
                self.allowEditCode(false);
            }
        }
        deleteData() {
            let self = this;
            let newDel = self.getTax(self.currentTax().code());
            self.items.splice(self.items().indexOf(newDel), 1);
        }
        alertDelete(){
            let self= this;
            if(confirm("do you wanna delete")=== true){
                    self.deleteData();
            }else{
                    alert("you didnt delete!");
            }
        }


        selectSomeItems() {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        }

        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }
        // startpage
        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function(data) {

                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }

    }
    //    class ItemModel {
    //        code: string;
    //        name: string;
    //        description: string;
    //
    //        constructor(code: string, name: string, description: string) {
    //            this.code = code;
    //            this.name = name;
    //            this.description = ription;
    //        }
    //    }

    class TaxModel {
        code: string;
        name: string;
        taxLimit: string;

        constructor(code: string, name: string, taxLimit: string) {
            this.code = code;
            this.name = name;
            this.taxLimit = taxLimit;
        }
    }
}