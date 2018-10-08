module nts.uk.com.view.qmm011.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        texteditor: any;
        simpleValue: KnockoutObservable<string>;

        itemList: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        data: KnockoutObservable<string> = ko.observable('');
        constructor() {
            var self = this;

            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            self.data("123");
            self.init();
        }
        init(){
            var self = this;
            service.getOccAccInsurBus().done( (item: Array<NameOfEachBusiness>) => {
                self.itemList(item);
                console.dir(self.itemList);
            }).always(() => {

            });

        }
        remove(){
            var self = this;
            let itemList: Array<NameOfEachBusiness> = [];
            service.getOccAccInsurBus().done( (item: Array<NameOfEachBusiness>) => {
                self.itemList = item;
                console.dir(itemList);
            }).always(() => {

            });

        }

        cancel(){
            close();
        }
    }

    class INameOfEachBusiness{
        occAccInsurBusNo: number;
        toUse: number;
        name: any;
        index:number;
    }
    class NameOfEachBusiness{
        occAccInsurBusNo: number;
        toUse: number;
        name: any;
        index:number;
        constructor(param:INameOfEachBusiness ){
            this.occAccInsurBusNo(param.occAccInsurBusNo);
            this.toUse(param.toUse);
            this.name(param.name);
            this.index(param.index);
        }
    }

}