module nts.uk.com.view.qmm011.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import service = nts.uk.com.view.qmm011.c.service;
    export class ScreenModel {

        listPerFracClass:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listOccAccIsHis:              KnockoutObservableArray<IOccAccIsHis> = ko.observableArray([]);
        listOccAccIsPrRate:        KnockoutObservableArray<OccAccIsPrRate> = ko.observableArray([]);
        listOccAccInsurBus: KnockoutObservableArray<IOccAccInsurBus> = ko.observableArray([]);
        listAccInsurPreRate:KnockoutObservableArray<AccInsurPreRate> = ko.observableArray([]);
        selectedEmpInsHis:          KnockoutObservable<IOccAccIsHis> = ko.observable();
        hisId:                      KnockoutObservable<string> = ko.observable('');
        index:                      KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:        KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:            KnockoutObservable<string> = ko.observable('2010/1');
        startYearMonth:                  KnockoutObservable<string> = ko.observable('2010/1');
        endYearMonth:                    KnockoutObservable<string> = ko.observable('2010/1');
        laststartYearMonth:              KnockoutObservable<string> = ko.observable('2010/1');
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
                self.selectedEmpInsHis(self.listOccAccIsHis()[self.index()]);
                self.setOccAccIsHis(self.selectedEmpInsHis());
                self.getAccInsurPreRate();
            });
        }


        // 初期データ取得処理
        initScreen(hisId: string){
            let self = this;
            block.invisible();
            service.getListOccAccIsHis().done((listOccAccIsHis: Array<IOccAccIsHis>) =>{
                if (listOccAccIsHis && listOccAccIsHis.length > 0) {
                    self.listOccAccIsHis(listOccAccIsHis);
                    self.index(self.getIndex(null));
                    if (hisId != null) {
                        self.index(self.getIndex(hisId));
                    }
                    self.selectedEmpInsHisId(self.listOccAccIsHis()[self.index()].hisId);
                } else {
                    self.isNewMode(false);
                }
            }).always(() => {
                block.clear();
            });
        }
        getOccAccIsPrRate(){
            let self = this;
            service.getOccAccIsPrRate(self.selectedEmpInsHisId()).done((listOccAccIsPrRate: Array<IOccAccIsPrRate>) =>{
                if(listOccAccIsPrRate && listOccAccIsPrRate.length > 0) {
                    self.listOccAccIsPrRate(OccAccIsPrRate.fromApp(listOccAccIsPrRate));
                    self.isNewMode(false);
                }
            });
        }
        getOccAccInsurBus(){
            let self = this;
            service.getOccAccInsurBus(self.selectedEmpInsHisId()).done((listOccAccInsurBus: Array<IOccAccInsurBus>) =>{
                if(listOccAccInsurBus && listOccAccInsurBus.length > 0) {
                    self.listOccAccInsurBus(OccAccInsurBus.fromApp(listOccAccInsurBus));
                    self.isNewMode(false);
                }
            });
        }

        getAccInsurPreRate() {
            let self = this;
            service.getAccInsurPreRate(self.selectedEmpInsHisId()).done((listAccInsurPreRate: Array<AccInsurPreRate>) => {
                if (listAccInsurPreRate && listAccInsurPreRate.length > 0) {
                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(listAccInsurPreRate));
                    self.isNewMode(false);
                }
            });
        }

        setIOccAccIsHis(param: IOccAccIsHis){
            let self = this;
            self.hisId(param.hisId);
            self.startYearMonth(param.startYearMonth);
            self.endYearMonth(param.startYearMonth);
        }

        getIndex(hisId: string){
            let self = this;
            let temp = _.findLast(self.listOccAccIsHis(), function(x) {
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
                startYearMonth:  self.startYearMonth()
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
                listOccAccIsPrRate : self.listOccAccIsPrRate();
        }
            service.register(data).done(() =>{
            });
        }

        setOccAccIsHis(emplInsurHis: IOccAccIsHis){
            let self = this
            self.hisId(emplInsurHis.hisId);
            self.startYearMonth(self.convertMonthYearToString(emplInsurHis.startYearMonth));
            self.endYearMonth(self.convertMonthYearToString(emplInsurHis.endYearMonth));
        }

        openFscreen(){
            let self = this;
            setShared('QMM011_F_PARAMS', {
                startYearMonth:  self.listOccAccIsHis(),
                endYearMonth: self.startYearMonth(),
                laststartYearMonth: self.laststartYearMonth()
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
        startYearMonth: string;
        endYearMonth: string;
        between: string;
    }

    class OccAccIsHis{
        hisId: string
        startYearMonth: string;
        endYearMonth: string;
        between: string;
        constructor(param: IOccAccIsHis) {
            this.hisId =param.hisId ;
            this.startYearMonth =param.startYearMonth ;
            this.endYearMonth =param.endYearMonth;
            this.between ='~';

        }

    }
    /*労災保険事業*/
    class IOccAccInsurBus{
        /**
         * 会社ID
         */
        cid:string;

        /**
         * 労災保険事業No
         */
        occAccInsurBusNo:number;

        /**
         * 利用する
         */
        toUse:number;

        /**
         * 名称
         */
        name:string;
    }

    class OccAccInsurBus{
        cid:string;
        occAccInsurBusNo:number;
        toUse:number;
        name:string;
        constructor() {


        }
        static fromApp(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: OccAccInsurBus = new OccAccInsurBus();
                dto.cid = item.cid;
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.toUse = item.toUse;
                dto.name = item.name;

                listEmp.push(dto);
            })
            return listEmp;
        }

    }

    class AccInsurPreRate {

        occAccInsurBusNo: number;
        name: string;
        fracClass: number;
        empConRatio: string;
        constructor() {

        }

        static fromApp(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: AccInsurPreRate = new AccInsurPreRate();
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.name = item.name;
                dto.fracClass = ko.observable(item.fracClass);
                dto.empConRatio = item.empConRatio;

                listEmp.push(dto);
            })
            return listEmp;
        }


    }

    class IOccAccIsPrRate{
        hisId: string;
        occAccInsurBusNo: number;
        fracClass: number;
        empConRatio: string;
    }

    class OccAccIsPrRate {
        /*履歴ID*/
        hisId: string;
        /*労災保険事業No*/
        occAccInsurBusNo: number;
        /*端数区分*/
        fracClass: number;
        /*事業主負担率*/
        empConRatio: string;
        constructor() {

        }

        static fromApp(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: OccAccIsPrRate = new OccAccIsPrRate();
                dto.hisId = item.hisId;
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.fracClass = ko.observable(item.fracClass);
                dto.empConRatio = ko.observable(item.empConRatio);

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