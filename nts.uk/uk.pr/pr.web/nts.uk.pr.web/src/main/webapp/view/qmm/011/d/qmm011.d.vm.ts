module nts.uk.com.view.qmm011.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        texteditor: any;

        listNameOfEachBusiness: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        listEachBusiness : KnockoutObservableArray<EachBusiness> = ko.observableArray([]);
        data: KnockoutObservable<string> = ko.observable('');
        constructor() {
            var self = this;

            self.checked = ko.observable(true);
            self.enable = ko.observable(true);

            self.texteditor = {
                //value: ko.observable('123'),
                //enable: ko.observable(true),
            };

            self.init();
        }
        init(){
            var self = this;
            block.invisible();
            service.getOccAccInsurBus().done( (listNameOfEachBusiness: Array<INameOfEachBusiness>) => {
                if (listNameOfEachBusiness && listNameOfEachBusiness.length > 0) {
                    self.listNameOfEachBusiness(NameOfEachBusiness.fromData(listNameOfEachBusiness));
                }
            }).always(() => {
                block.clear();
            });

        }
        update(){
            let self = this;
            let data: any = {
                listEachBusiness: self.convertToCommand(self.listNameOfEachBusiness()),
            }
            service.removeOccAccInsurBus(data).done(()=>{
                close();
            });
        }

        cancel(){
            close();
        }

        convertToCommand(dto :Array<NameOfEachBusiness>){
            let listEachBusiness :Array <EachBusiness> = [];
            _.each(dto, function(item: NameOfEachBusiness) {
                let temp = new EachBusiness();
                temp.occAccInsurBusNo = item.occAccInsurBusNo;
                temp.toUse = item.toUse() === true ? 1 : 0;
                temp.name = item.name();
                listEachBusiness.push(temp);
            })
            return listEachBusiness;
        }
    }

    class EachBusiness{
        occAccInsurBusNo: number;
        toUse: number;
        name: string;
        constructor() {
        }
    }
    class INameOfEachBusiness{
        occAccInsurBusNo: number;
        toUse: boolean;
        name: string;
        index: number;

    }
    class NameOfEachBusiness{
        occAccInsurBusNo: number;
        toUse: KnockoutObservable<boolean>;
        name: KnockoutObservable<string>;
        index: number;
        constructor() {
        }

        static fromData(data){
            let listBus = [];
            let i = 1;
            _.each(data,(item) =>{
                let dto  = new NameOfEachBusiness();
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.toUse = item.toUse > 0 ? ko.observable(true) : ko.observable(false);
                dto.name = ko.observable(item.name);
                dto.index = i;
                i++;
                listBus.push(dto);
            });

            return listBus;
        }
    }

}