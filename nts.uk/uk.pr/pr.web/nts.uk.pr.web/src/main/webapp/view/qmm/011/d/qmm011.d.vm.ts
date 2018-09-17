module nts.uk.com.view.qmm011.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        listNameOfEachBusiness: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        businessType1: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(1,0,''));
        businessType2: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(2,0,''));
        businessType3: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(3,0,''));
        businessType4: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(4,0,''));
        businessType5: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(5,0,''));
        businessType6: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(6,0,''));
        businessType7: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(7,0,''));
        businessType8: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(8,0,''));
        businessType9: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(9,0,''));
        businessType10: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(0,0,''));

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
                    if(self.listNameOfEachBusiness().length > 0){
                        _.each(self.listNameOfEachBusiness(),(value) => {
                            if(value.occAccInsurBusNo == 1){
                                self.businessType1(value);
                            }else if(value.occAccInsurBusNo == 2){
                                self.businessType2(value);
                            }else if(value.occAccInsurBusNo == 3){
                                self.businessType3(value);
                            }else if(value.occAccInsurBusNo == 4){
                                self.businessType4(value);
                            }else if(value.occAccInsurBusNo == 5){
                                self.businessType5(value);
                            }else if(value.occAccInsurBusNo == 6){
                                self.businessType6(value);
                            }else if(value.occAccInsurBusNo == 7){
                                self.businessType7(value);
                            }else if(value.occAccInsurBusNo == 8){
                                self.businessType8(value);
                            }else if(value.occAccInsurBusNo == 9){
                                self.businessType9(value);
                            }else if(value.occAccInsurBusNo == 0){
                                self.businessType10(value);
                            }
                        });
                    }
                }

            }).always(() => {
                $("#D2_3").focus();
                block.clear();
            });

        }

        update(){
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("input").trigger("validate");
            if(self.businessType1().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType1()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType1());
            }
            if(self.businessType2().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType2()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType2());
            }
            if(self.businessType3().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType3()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType3());
            }
            if(self.businessType4().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType4()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType4());
            }
            if(self.businessType5().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType5()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType5());
            }
            if(self.businessType6().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType6()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType6());
            }
            if(self.businessType7().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType7()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType7());
            }
            if(self.businessType8().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType8()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType8());
            }
            if(self.businessType9().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType9()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType9());
            }
            if(self.businessType10().name() != ''
                && !(self.listNameOfEachBusiness.indexOf(self.businessType10()) >= 0)){
                self.listNameOfEachBusiness.push(self.businessType10());
            }
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
            let eachBusiness :Array <EachBusiness> = [];
            _.each(dto, function(item: NameOfEachBusiness) {
                if(item != undefined){
                    let temp = new EachBusiness();
                    temp.occAccInsurBusNo = item.occAccInsurBusNo;
                    temp.toUse = item.toUse() === true ? 1 : 0;
                    temp.name = item.name();
                    eachBusiness.push(temp);
                }

            });
            return eachBusiness;
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
    }
    class NameOfEachBusiness{
        occAccInsurBusNo: number;
        toUse: KnockoutObservable<boolean>;
        name: KnockoutObservable<string>;
        constructor(occAccInsurBusNo: number, toUse: number, name: string) {
            this.occAccInsurBusNo = occAccInsurBusNo;
            this.toUse = toUse > 0 ? ko.observable(true) : ko.observable(false);
            this.name = ko.observable(name);
        }

        static fromData(data){
            let listBus = [];
            _.each(data,(item) =>{
                let dto  = new NameOfEachBusiness();
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.toUse = item.toUse > 0 ? ko.observable(true) : ko.observable(false);
                dto.name = ko.observable(item.name);
                listBus.push(dto);
            });

            return listBus;
        }
    }
}