module nts.uk.com.view.qmm011.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        listPerFracClass:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listEmpInsHis:              KnockoutObservableArray<IOccAccIsHis> = ko.observableArray([]);
        listEmpInsurPreRate:        KnockoutObservableArray<EmpInsurPreRate> = ko.observableArray([]);
        selectedEmpInsHis:          KnockoutObservable<IOccAccIsHis> = ko.observable();
        hisId:                      KnockoutObservable<string> = ko.observable('');
        index:                      KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:        KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:            KnockoutObservable<string> = ko.observable('2010/1');
        startDate:                  KnockoutObservable<string> = ko.observable('2010/1');
        endDate:                    KnockoutObservable<string> = ko.observable('2010/1');
        lastStartDate:              KnockoutObservable<string> = ko.observable('2010/1');
        indBdRatio:                 KnockoutObservable<string> = ko.observable('');
        perFracClass:               KnockoutObservable<number> = ko.observable();
        empContrRatio:              KnockoutObservable<string> = ko.observable('');
        busiOwFracClass:            KnockoutObservable<string> = ko.observable('');
        isNewMode:                  KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) =>{
                let self = this;
                self.selectedEmpInsHis(self.listEmpInsHis()[self.index()]);
                self.setEmplInsurHis(self.selectedEmpInsHis());
                self.getEmpInsurPreRate();
            });
        }


        // 「初期データ取得処理
        initScreen(hisId: string){
            let self = this;
            block.invisible();
            service.getListOccAccIsHis().done((listEmpInsHis: Array<IOccAccIsHis>) =>{
                if (listEmpInsHis && listEmpInsHis.length > 0) {
                    self.listEmpInsHis(listEmpInsHis);
                    self.index(self.getIndex(null));
                    if (hisId != null) {
                        self.index(self.getIndex(hisId));
                    }
                    self.selectedEmpInsHisId(self.listEmpInsHis()[self.index()].hisId);
                } else {
                    self.isNewMode(false);
                }
            }).always(() => {
                block.clear();
            });
        }
        getEmpInsurPreRate(){
            let self = this;
            service.getOccAccIsPrRate(self.selectedEmpInsHisId()).done((listEmpInsurPreRate: Array<IEmpInsurPreRate>) =>{
                if(listEmpInsurPreRate && listEmpInsurPreRate.length > 0) {
                    self.listEmpInsurPreRate(EmpInsurPreRate.fromApp(listEmpInsurPreRate));
                    self.isNewMode(false);
                }
            });
        }

        setIOccAccIsHis(param: IOccAccIsHis){
            let self = this;
            self.hisId(param.hisId);
            self.startDate(param.startDate);
            self.endDate(param.endDate);
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
                startDate:  self.startDate()
            });

            modal("/view/qmm/011/e/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_B_Param');
                if (params && params.result == true) {
                    self.isNewMode(true);
                    if(params.transferHistory) {
                        self.initScreen(null);
                    }
                }
            });
        }

        register(){
            let self = this;
            let data: any = {
                listEmpInsurPreRate : self.listEmpInsurPreRate();
        }
            service.register(data).done(() =>{
            });
        }

        setEmplInsurHis(emplInsurHis: IOccAccIsHis){
            let self = this
            self.hisId(emplInsurHis.hisId);
            self.startDate(self.convertMonthYearToString(emplInsurHis.startDate));
            self.endDate(self.convertMonthYearToString(emplInsurHis.endDate));
        }

        openFscreen(){
            let self = this;
            setShared('QMM011_F_PARAMS', {
                startDate:  self.listEmpInsHis(),
                endDate: self.startDate(),
                lastStartDate: self.lastStartDate()
            });
            modal("/view/qmm/011/f/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_B_Param');
                if (params && params.result == true) {
                    if (!params.isDelete) {
                        self.initScreen(self.hisId);
                    } else {
                        self.initScreen(null);
                    }
                }
            });
        }
        convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month:string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
    }

    class IOccAccIsHis{
        hisId: string
        startDate: string;
        endDate: string;
        between: string;
    }

    class OccAccIsHis{
        hisId: string
        startDate: string;
        endDate: string;
        between: string;
        constructor(param: IOccAccIsHis) {
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

    class EmpInsurPreRate {
        hisId: string;
        empPreRateId: string;
        indBdRatio: number;
        empContrRatio: string;
        perFracClass: KnockoutObservable<number>;
        busiOwFracClass: number;
        constructor() {
        }

        static fromApp(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: EmpInsurPreRate = new EmpInsurPreRate();
                dto.hisId = item.hisId;
                dto.empPreRateId = item.empPreRateId;
                dto.indBdRatio = ko.observable(item.indBdRatio);
                dto.empContrRatio = ko.observable(item.empContrRatio);
                dto.perFracClass = ko.observable(item.perFracClass);
                dto.busiOwFracClass = ko.observable(item.busiOwFracClass);
                listEmp.push(dto);
            })
            return listEmp;
        }
    }

    export function getListPerFracClass(): Array<model.ItemModel> {
        return [
            new model.ItemModel('0', getText('CMF002_358')),
            new model.ItemModel('1', getText('CMF002_359')),
            new model.ItemModel('2', getText('CMF002_360')),
            new model.ItemModel('3', getText('CMF002_361')),
            new model.ItemModel('4', getText('CMF002_362'))
        ];
    }
}