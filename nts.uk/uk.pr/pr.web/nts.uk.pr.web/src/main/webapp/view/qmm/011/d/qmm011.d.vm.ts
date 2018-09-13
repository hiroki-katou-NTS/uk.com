module nts.uk.com.view.qmm011.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        listName: KnockoutObservableArray<string> = ko.observableArray(getListName());
        listNameOfEachBusiness: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        listEachBusiness : KnockoutObservableArray<EachBusiness> = ko.observableArray([]);
        data: KnockoutObservable<string> = ko.observable('');
        constructor() {
            var self = this;
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
            nts.uk.ui.errors.clearAll();
            $("input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let data: any = {
                listEachBusiness: self.convertToCommand(self.listNameOfEachBusiness()),
            }
            service.updateOccAccInsurBus(data).done(()=>{
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
    export function getListName(): Array<string> {
        return [
            new String(getText('QMM011_22')),
            new String(getText('QMM011_24')),
            new String(getText('QMM011_26')),
            new String(getText('QMM011_28')),
            new String(getText('QMM011_30'));
            new String(getText('QMM011_32'));
            new String(getText('QMM011_34'));
            new String(getText('QMM011_36'));
            new String(getText('QMM011_38'));
            new String(getText('QMM011_40'));
        ];
    }

}