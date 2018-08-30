module nts.uk.com.view.qmm011.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        
        listPerFracClass:       KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listEmpInsHis:          KnockoutObservableArray<IEmplInsurHis> = ko.observableArray([]);
        listEmpInsurPreRate:    KnockoutObservableArray<IEmpInsurPreRate> = ko.observableArray([]);
        selectedEmpInsHis:      KnockoutObservable<IEmplInsurHis> = ko.observable();
        hisId:                  KnockoutObservable<string> = ko.observable('');
        index:                  KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:    KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:        KnockoutObservable<string> = ko.observable('2010/1');
        startDate:              KnockoutObservable<string> = ko.observable('2010/1');
        endDate:                KnockoutObservable<string> = ko.observable('2010/1');
        indBdRation:            KnockoutObservable<string> = ko.observable('');
        perFracClass:           KnockoutObservable<string> = ko.observable('');
        empContrRatio:          KnockoutObservable<string> = ko.observable('');
        busiOwFracClass:        KnockoutObservable<string> = ko.observable('');
        isNewMode:              KnockoutObservable<boolean> = ko.observable(true);
        
        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) =>{
                let self = this;
                self.selectedEmpInsHis(self.index());
            });
        }
        
        
       // 「初期データ取得処理
       initScreen(hisId: string){
           let self = this;
           block.invisible();
           service.getEmpInsHis().done((listEmpInsHis: Array<IEmplInsurHis>) =>{
                if (listEmpInsHis && listEmpInsHis.length > 0) {
                    self.listEmpInsHis(listEmpInsHis);
                    self.index(self.getIndex(null));
                    self.selectedEmpInsHisId(self.listEmpInsHis()[self.index()].hisId);
                    service.getEmpInsurPreRate(self.selectedEmpInsHisId()).done((listEmpInsurPreRate: Array<IEmpInsurPreRate>) =>{
                        if(listEmpInsurPreRate && listEmpInsurPreRate.length > 0) {
                            self.listEmpInsurPreRate(listEmpInsurPreRate);
                            self.isNewMode(false);
                        }
                    });
                } else {
                    self.isNewMode(false);
                }
            }).always(() => {
                block.clear();
            });
       }
       setEmpInsRate(param: IEmplInsurHis){
           let self = this;
           self.hisId = param.hisId;
           self.startDate = param.startDate;
           self.endDate = param.endDate;
       }
        
       getIndex(hisId: string){
           let self = this;
           let temp = _.findLast(self.listEmpInsHis(), function(x) { 
                return x.hisId == hisId;
           });
           if (temp) {
               return temp;         
           }
           return 0;
       }
       
       openEscreen(){
           let self = this;
           setShared('QMM011_E_PARAMS', {
                    startDate:  self.listEmpInsHis()
           });
                    
           modal("/view/qmm/011/e/index.xhtml").onClosed(function() {
               let params = getShared('QMM011_B_Param');
               if (params) {
                   let override = params.overWrite;
                   let destinationCode = params.copyDestinationCode;
                   let destinationName = params.destinationName;
                   let result = params.result;
                   service.register().done(()=> {
                    
                   });
                }
            });
        }
        
        openFscreen(){
            setShared('QMM011_F_PARAMS', {
                    
           });
            modal("/view/qmm/011/f/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_B_Param');
                if (params) {
                    let override = params.overWrite;
                    let destinationCode = params.copyDestinationCode;
                    let destinationName = params.destinationName;
                    let result = params.result;
                    service.register().done(()=> {
                    
                    });
                }
            });
        }
    }
    
    class IEmplInsurHis{
        hisId: string
        startDate: string;
        endDate: string;
        between: string;
    }
    
    class EmplInsurHis{
        hisId: string
        startDate: string;
        endDate: string;
        between: string;
        constructor(param: IEmplInsurHis) {
            this.hisId(param.hisId || '');
            this.startDate(param.startDate || '');
            this.endDate(param.endDate || '');
            this.between('~');
        }
    }
    
    class IEmpInsurPreRate{
         hisId: string;
         empPreRateId: string;
         indBdRatio: number;
         empContrRatio: string;
         perFracClass: number;
         busiOwFracClass: number;
      }
    
    class EmpInsurPreRate{
         hisId: string;
         empPreRateId: string;
         indBdRatio: number;
         empContrRatio: string;
         perFracClass: number;
         busiOwFracClass: number;
         constructor(param: IEmpInsurPreRate) {
            this.hisId(param.hisId);
            this.empPreRateId(param.empPreRateId);
            this.indBdRatio(param.indBdRatio);
            this.empContrRatio(param.empContrRatio);
            this.perFracClass(param.perFracClass);
            this.busiOwFracClass(param.busiOwFracClass);
        }
    }
    
    export function getListPerFracClass(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('CMF002_358')),
            new model.ItemModel(1, getText('CMF002_359')),
            new model.ItemModel(2, getText('CMF002_360')),
            new model.ItemModel(3, getText('CMF002_361')),
            new model.ItemModel(4, getText('CMF002_362'))
        ];
    }
}