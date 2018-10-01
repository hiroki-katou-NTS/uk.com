module nts.uk.com.view.qmm011.d.viewmodel {
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        listNameOfEachBusiness: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        templistNameOfEachBusiness: KnockoutObservableArray<NameOfEachBusiness> = ko.observableArray([]);
        businessType1: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(1,0,'',false));
        businessType2: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(2,0,'',false));
        businessType3: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(3,0,'',false));
        businessType4: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(4,0,'',false));
        businessType5: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(5,0,'',false));
        businessType6: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(6,0,'',false));
        businessType7: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(7,0,'',false));
        businessType8: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(8,0,'',false));
        businessType9: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(9,0,'',false));
        businessType10: KnockoutObservable<NameOfEachBusiness> = ko.observable(new NameOfEachBusiness(10,0,'',false));

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
                    self.templistNameOfEachBusiness(NameOfEachBusiness.fromData(listNameOfEachBusiness));
                    if(self.listNameOfEachBusiness().length > 0){
                        _.each(self.listNameOfEachBusiness(),(value) => {
                            value.flagReg = true;
                            if(value.occAccInsurBusNo === 1){
                                self.businessType1(value);
                            }else if(value.occAccInsurBusNo === 2){
                                self.businessType2(value);
                            }else if(value.occAccInsurBusNo === 3){
                                self.businessType3(value);
                            }else if(value.occAccInsurBusNo === 4){
                                self.businessType4(value);
                            }else if(value.occAccInsurBusNo === 5){
                                self.businessType5(value);
                            }else if(value.occAccInsurBusNo === 6){
                                self.businessType6(value);
                            }else if(value.occAccInsurBusNo === 7){
                                self.businessType7(value);
                            }else if(value.occAccInsurBusNo === 8){
                                self.businessType8(value);
                            }else if(value.occAccInsurBusNo === 9){
                                self.businessType9(value);
                            }else if(value.occAccInsurBusNo === 10){
                                self.businessType10(value);
                            }
                        });
                    }
                }

            }).fail(function(res: any) {
                if (res)
                    dialog.alertError(res);
            }).always(() => {
                $('#D2_3').focus();
                block.clear();
            });
        }

        update(){
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("input").trigger("validate");
            _.each($("input"),(o)=>{
                let id = o.id ? '#' + o.id : '';
                if(o.disabled === true){
                    nts.uk.ui.errors.removeByElement($(id));
                }
            });
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
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
            let data: any = {
                listEachBusiness: self.convertToCommand(self.listNameOfEachBusiness()),
            }
            service.updateOccAccInsurBus(data).done(()=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    close();
                });
            }).fail(function(res: any) {
                if (res)
                    dialog.alertError(res);
            });
        }
        hasChecked(toUse,e){
            if(!toUse()){
                $(e).ntsError('clear');
                nts.uk.ui.errors.removeByElement($(e));
            }
            return toUse;
        }

        cancel(){
            setShared('QMM011_D_PARAMS_CLOSE', {
                isClose: true
            });
            close();
        }

        convertToCommand(dto :Array<NameOfEachBusiness>){
            let self = this;
            let eachBusiness :Array <EachBusiness> = [];
            _.each(dto, function(item: NameOfEachBusiness) {
                if(!item.flagReg && item.toUse() && item.name() != ''){
                    let temp = new EachBusiness();
                    temp.occAccInsurBusNo = item.occAccInsurBusNo;
                    temp.toUse = item.toUse() === true ? 1 : 0;
                    temp.name = item.name();
                    eachBusiness.push(temp);
                }else if(item.flagReg && !item.toUse()){
                    let temp = new EachBusiness();
                    let nameOfEachBusiness = _.find(self.templistNameOfEachBusiness(),function(o){
                        return item.occAccInsurBusNo == o.occAccInsurBusNo;
                    });
                    temp.occAccInsurBusNo = nameOfEachBusiness.occAccInsurBusNo;
                    temp.toUse = 0;
                    temp.name = nameOfEachBusiness.name();
                    eachBusiness.push(temp);
                }else if(item.flagReg){
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
        flagReg: boolean;
        constructor(occAccInsurBusNo: number, toUse: number, name: string,flagReg: boolean) {
            this.occAccInsurBusNo = occAccInsurBusNo;
            this.toUse = toUse > 0 ? ko.observable(true) : ko.observable(false);
            this.name = ko.observable(name);
            this.flagReg = flagReg;
        }

        static fromData(data){
            let listBus = [];
            _.each(data,(item) =>{
                let dto  = new NameOfEachBusiness();
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.toUse = item.toUse > 0 ? ko.observable(true) : ko.observable(false);
                dto.name = ko.observable(item.name);
                dto.flagReg = item.flagReg;
                listBus.push(dto);
            });
            return listBus;
        }
    }
}