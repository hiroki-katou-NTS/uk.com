module nts.uk.pr.view.qmm011.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import service = nts.uk.pr.view.qmm011.c.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {

        listPerFracClass:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listOccAccIsHis:            KnockoutObservableArray<IOccAccIsHis> = ko.observableArray([]);
        listOccAccIsPrRate:         KnockoutObservableArray<OccAccIsPrRate> = ko.observableArray([]);
        listOccAccInsurBus:         KnockoutObservableArray<IOccAccInsurBus> = ko.observableArray([]);
        listAccInsurPreRate:        KnockoutObservableArray<AccInsurPreRate> = ko.observableArray([10]);
        selectedEmpInsHis:          KnockoutObservable<IOccAccIsHis> = ko.observable();
        hisId:                      KnockoutObservable<string> = ko.observable('');
        index:                      KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:        KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:            KnockoutObservable<string> = ko.observable('2010/1');
        startYearMonth:             KnockoutObservable<string> = ko.observable('2010/1');
        endYearMonth:               KnockoutObservable<string> = ko.observable('2010/1');
        laststartYearMonth:         KnockoutObservable<string> = ko.observable('2010/1');
        indBdRatio:                 KnockoutObservable<string> = ko.observable('');
        perFracClass:               KnockoutObservable<number> = ko.observable();
        empContrRatio:              KnockoutObservable<string> = ko.observable('');
        busiOwFracClass:            KnockoutObservable<string> = ko.observable('');
        isNewMode:                  KnockoutObservable<boolean> = ko.observable();

        listHisTemp:Array<IOccAccIsHis> =[];
        listAccInsurPreRateTemp :Array<IAccInsurPreRate>=[];
        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) =>{
                let self = this;
                self.selectedEmpInsHis(self.listOccAccIsHis()[self.index()]);
                self.setOccAccIsHis(self.selectedEmpInsHis());
                if(self.selectedEmpInsHisId() == '000'){
                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(self.listAccInsurPreRateTemp));

                }
                else{
                    self.getAccInsurPreRate();
                }
            });
        }


        // 初期データ取得処理
        initScreen(hisId: string){
            let self = this;
            block.invisible();
            service.getListOccAccIsHis().done((listOccAccIsHis: Array<IOccAccIsHis>) =>{
                if (listOccAccIsHis && listOccAccIsHis.length > 0) {
                    self.listHisTemp = listOccAccIsHis;
                    self.listOccAccIsHis(OccAccIsHis.convertToDisplayHis(listOccAccIsHis));
                    self.index(self.getIndex(null));
                    if (hisId != null) {
                        self.index(self.getIndex(hisId));
                    }
                    self.selectedEmpInsHisId(self.listOccAccIsHis()[self.index()].hisId);
                } else {
                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(self.regColumnAccInsurPreRate(new Array<IAccInsurPreRate>())));
                    self.isNewMode(true);
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

                }else {
                    console.log("không có data tới !!") ;
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
            service.getAccInsurPreRate(self.selectedEmpInsHisId()).done((listAccInsurPreRate: Array<IAccInsurPreRate>) => {
                console.log(self.selectedEmpInsHisId().toString());
                if (listAccInsurPreRate && listAccInsurPreRate.length > 0) {
                    self.regColumnAccInsurPreRate(listAccInsurPreRate);


                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(listAccInsurPreRate));
                    self.isNewMode(false);
                }

            });
        }
        regColumnAccInsurPreRate(listAccInsurPreRate : Array<IAccInsurPreRate>){

            listAccInsurPreRate.length=10;
            if(listAccInsurPreRate[0] == null){
                let data:IAccInsurPreRate = {
                    hisId: '',
                    occAccInsurBusNo: 0,
                    name: '',
                    fracClass: 0,
                    empConRatio: 0,
                    useArt: 0
                };
                listAccInsurPreRate[0]=data;
            }
            _.forEach( listAccInsurPreRate, function(value,key) {

                let temp :IAccInsurPreRate = _.find(listAccInsurPreRate, function (o) {
                    if(o == null)
                        return null;
                    return o.occAccInsurBusNo == key;
                });

                if (temp == null) {
                    let data:IAccInsurPreRate = {
                        hisId: listAccInsurPreRate[0].hisId,
                        occAccInsurBusNo: key,
                        name: '',
                        fracClass: 0,
                        empConRatio: 0,
                        useArt: 0
                    };
                    listAccInsurPreRate[key] = data;
                }



            });
            return listAccInsurPreRate;

        }

        setIOccAccIsHis(param: IOccAccIsHis){
            let self = this;
            self.hisId(param.hisId);
            self.startYearMonth(param.startYearMonth);
            self.endYearMonth(param.startYearMonth);
        }
        addOccAccIsHis(start: number, list: Array<OccAccIsHis>){
            let listOccAccIsHis: Array<OccAccIsHis> = [];
            let self = this;
            let occAccIsHis = new OccAccIsHis();
            occAccIsHis.hisId = '000';
            occAccIsHis.startYearMonth = start.toString();
            occAccIsHis.endYearMonth = '999912';
            occAccIsHis.display = self.convertMonthYearToString(occAccIsHis.startYearMonth) + " ~ " + self.convertMonthYearToString(occAccIsHis.endYearMonth);
            if (list && list.length > 0) {
                list[FIRST].display = self.convertMonthYearToString(occAccIsHis.startYearMonth) + " ~ " + self.convertMonthYearToString((start - 1).toString());
            }
            listOccAccIsHis.push(occAccIsHis);
            _.each(list, (item) => {
                listOccAccIsHis.push(item);
            })


            let dataInsurPreRate: Array<IAccInsurPreRate> = [];
            _.forEach(self.listAccInsurPreRate(), function (value) {
                let temp: IAccInsurPreRate = {
                    hisId: occAccIsHis.hisId,
                    occAccInsurBusNo: value.occAccInsurBusNo,
                    name: value.name,
                    fracClass: value.fracClass,
                    empConRatio: 0,
                    useArt: value.useArt
                };
                dataInsurPreRate.push(temp);
            });
            /*refesh list pate */
            self.listAccInsurPreRate(AccInsurPreRate.fromApp(dataInsurPreRate));
            self.listAccInsurPreRateTemp = dataInsurPreRate;
            return listOccAccIsHis;

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
            setShared('QMM011_E_PARAMS_INPUT', {
                startYearMonth: self.startYearMonth()
            });

            modal("/view/qmm/011/e/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_E_PARAMS_OUTPUT');

                    if (params != null) {
                        self.isNewMode(true);

                        //
                        self.startYearMonth(params.startYearMonth);
                        self.listOccAccIsHis(self.addOccAccIsHis(self.startYearMonth(), self.listOccAccIsHis()));
                        self.selectedEmpInsHisId(self.listOccAccIsHis()[FIRST].hisId);


                        // sort list day tim
                        self.listOccAccIsHis(_.sortBy(self.listOccAccIsHis(), 'startYearMonth').reverse());


                    } else {
                        self.initScreen(null);
                    }


            });
        }
        openDscreen(){
            let self = this;
            modal("/view/qmm/011/d/index.xhtml").onClosed(function() {
                self.selectedEmpInsHis(self.listOccAccIsHis()[0]);
                self.setOccAccIsHis(self.selectedEmpInsHis());
                self.getAccInsurPreRate();
                self.getOccAccIsPrRate();
            });

        }

        register(){
            let self = this;
            block.invisible();
            if (nts.uk.ui.errors.hasError()) {
                block.clear();
                return;
            }
            let data: any = {
                listAccInsurPreRate: self.convertToCommand(self.listAccInsurPreRate()),
                isNewMode: self.isNewMode(),
                startYearMonth: self.convertStringToYearMonth(self.startYearMonth()),
                endYearMonth:  self.convertStringToYearMonth(self.endYearMonth()),
                hisId: self.listAccInsurPreRate()[0].hisId

            }
            service.register(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.isNewMode(false);
                    self.initScreen(null);
                });
            });
        }
        convertToCommand(dto :Array<AccInsurPreRate>){
            let listOccAccIsPrRate :Array <AccInsurPreRate> = [];
            _.each(dto, function(item: AccInsurPreRate) {
                let temp = new AccInsurPreRate();
                temp.occAccInsurBusNo = item.occAccInsurBusNo;
                temp.empConRatio = Number(item.empConRatio());
                temp.fracClass = Number(item.fracClass());
                listOccAccIsPrRate.push(temp);
            })
            return listOccAccIsPrRate;
        }
        convertStringToYearMonth(yearMonth: any){
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.slice(0, 4) + yearMonth.slice(5, 7);
            return yearMonth;
        }

        setOccAccIsHis(emplInsurHis: IOccAccIsHis){
            let self = this;
            self.hisId(emplInsurHis.hisId);
            self.startYearMonth(self.convertMonthYearToString(emplInsurHis.startYearMonth));
            self.endYearMonth(self.convertMonthYearToString(emplInsurHis.endYearMonth));
            year = self.startYearMonth().slice(0, 4);
            month = self.startYearMonth().slice(5, 7);
            self.monthlyCalendar("(" + nts.uk.time.yearInJapanEmpire(year).toString() + month + "月)");
        }

        openFscreen(){
            let self = this;
            let laststartYearMonth = self.listOccAccIsHis().length > 1 ? self.listOccAccIsHis()[self.index() + 1].startYearMonth : 0
            setShared('QMM011_F_PARAMS_INPUT', {
                startYearMonth: self.convertStringToYearMonth(self.startYearMonth()),
                endYearMonth: self.convertStringToYearMonth(self.endYearMonth()),
                insurrance: INSURRANCE.ACCIDENT_INSURRANCE_RATE,
                hisId: self.hisId(),
                laststartYearMonth: laststartYearMonth
            });
            modal("/view/qmm/011/f/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_F_PARAMS_OUTPUT');

                    if(params && params.methodEditing == 1) {

                        self.initScreen(null);
                        self.setOccAccIsHis(self.selectedEmpInsHis());

                    }
                    if(params && params.methodEditing == 0) {

                        self.initScreen(null);
                    }
                block.clear();

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
        display: string;
        constructor() {


        }
        static convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
        static convertToDisplayHis(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: OccAccIsHis = new OccAccIsHis();
                dto.hisId = item.hisId;
                dto.startYearMonth = item.startYearMonth;
                dto.endYearMonth = item.endYearMonth;
                dto.display = this.convertMonthYearToString(item.startYearMonth) + " ~ " + this.convertMonthYearToString(item.endYearMonth);
                listEmp.push(dto);
            })
            return listEmp;
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
    class IAccInsurPreRate {
        hisId: string;
        occAccInsurBusNo: number;
        name: string;
        fracClass: number;
        empConRatio: number;
        useArt:number;
    }
    class AccInsurPreRate {
        hisId: string;
        occAccInsurBusNo: number;
        name: string;
        fracClass: number;
        empConRatio: number;
        useArt:number;
        constructor() {

        }

        static fromApp(app) {
            let listEmp = [];
            _.each(app, (item) => {
                let dto: AccInsurPreRate = new AccInsurPreRate();
                dto.hisId = item.hisId;
                dto.occAccInsurBusNo = item.occAccInsurBusNo;
                dto.name = item.name;
                dto.fracClass = ko.observable(item.fracClass);
                dto.empConRatio = ko.observable(item.empConRatio);
                dto.useArt = item.useArt;

                listEmp.push(dto);
            })
            return listEmp;
        }


    }

    class IOccAccIsPrRate{
        hisId: string;
        occAccInsurBusNo: number;
        fracClass: number;
        empConRatio: number;
    }

    class OccAccIsPrRate {
        /*履歴ID*/
        hisId: string;
        /*労災保険事業No*/
        occAccInsurBusNo: number;
        /*端数区分*/
        fracClass: number;
        /*事業主負担率*/
        empConRatio: number;
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
    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }
    export const FIRST = 0;
}