module nts.uk.pr.view.qmm011.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import service = nts.uk.pr.view.qmm011.c.service;
    import dialog = nts.uk.ui.dialog;
    import error = nts.uk.ui.errors;
    export class ScreenModel {

        listPerFracClass:           KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listOccAccIsHis:            KnockoutObservableArray<IOccAccIsHis> = ko.observableArray([]);
        listOccAccIsPrRate:         KnockoutObservableArray<OccAccIsPrRate> = ko.observableArray([]);
        listOccAccInsurBus:         KnockoutObservableArray<IOccAccInsurBus> = ko.observableArray([]);
        listAccInsurPreRate:        KnockoutObservableArray<AccInsurPreRate> = ko.observableArray([]);
        selectedEmpInsHis:          KnockoutObservable<IOccAccIsHis> = ko.observable();
        hisId:                      KnockoutObservable<string> = ko.observable('');
        index:                      KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:        KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:            KnockoutObservable<string> = ko.observable('');
        startYearMonth:             KnockoutObservable<string> = ko.observable('');
        endYearMonth:               KnockoutObservable<string> = ko.observable('');
        laststartYearMonth:         KnockoutObservable<string> = ko.observable('');
        indBdRatio:                 KnockoutObservable<string> = ko.observable('');
        perFracClass:               KnockoutObservable<number> = ko.observable();
        empContrRatio:              KnockoutObservable<string> = ko.observable('');
        busiOwFracClass:            KnockoutObservable<string> = ko.observable('');
        isNewMode:                  KnockoutObservable<number> = ko.observable(2);

        listHisTemp:Array<IOccAccIsHis> =[];
        listAccInsurPreRateTemp :Array<IAccInsurPreRate>=[];
        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) =>{
                nts.uk.ui.errors.clearAll();
                if (self.listOccAccIsHis()[0].hisId == HIS_ID_TEMP) {
                    self.isNewMode(MODE.NEW);
                }
                self.selectedEmpInsHis(self.getIndex(data));
                self.setOccAccIsHis(self.selectedEmpInsHis());
                if(self.selectedEmpInsHisId() == HIS_ID_TEMP){
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
                    self.index(0);
                    if (hisId != null) {
                        self.index(self.getIndex(hisId));
                    }
                    self.selectedEmpInsHisId(self.listOccAccIsHis()[self.index()].hisId);
                } else {
                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(self.regColumnAccInsurPreRate(new Array<IAccInsurPreRate>())));

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
                    self.isNewMode(MODE.UPDATE);
                    if (self.selectedEmpInsHisId() == HIS_ID_TEMP) {
                        self.isNewMode(MODE.NEW);
                    }

                }else {
                    console.log("không có data tới !!") ;
                }

            });
        }

        getAccInsurPreRate() {
            let self = this;
            service.getAccInsurPreRate(self.selectedEmpInsHisId()).done((listAccInsurPreRate: Array<IAccInsurPreRate>) => {
                console.log(self.selectedEmpInsHisId().toString());
                if (listAccInsurPreRate && listAccInsurPreRate.length > 0) {
                    self.listAccInsurPreRate(AccInsurPreRate.fromApp(self.regColumnAccInsurPreRate(listAccInsurPreRate)));
                    if(self.listOccAccIsHis()[0].hisId != HIS_ID_TEMP)
                    self.isNewMode(MODE.UPDATE);
                }

            });
        }
        regColumnAccInsurPreRate(listAccInsurPreRate : Array<IAccInsurPreRate>){
            let arrResulf :Array<IAccInsurPreRate> =[];
            arrResulf.length=10;
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
            _.forEach(arrResulf, function(value,key) {
                key++;
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
                    arrResulf[key-1] = data;
                }
                else{
                    arrResulf[key-1] = temp;
                }

            });
            return arrResulf;

        }

        addOccAccIsHis(start: number, list: Array<OccAccIsHis>, typeCreate:number){
            let listOccAccIsHis: Array<OccAccIsHis> = [];
            let self = this;
            let occAccIsHis = new OccAccIsHis();
            occAccIsHis.hisId = '000';
            occAccIsHis.startYearMonth = start.toString();
            occAccIsHis.endYearMonth = '999912';
            occAccIsHis.display = self.convertMonthYearToString(occAccIsHis.startYearMonth) + " ~ " + self.convertMonthYearToString(occAccIsHis.endYearMonth);
            if (list && list.length > 0) {
                list[FIRST].display = self.convertMonthYearToString(list[FIRST].startYearMonth) + " ~ " + self.convertMonthYearToString((start - 1).toString());
            }
            listOccAccIsHis.push(occAccIsHis);
            _.each(list, (item) => {
                listOccAccIsHis.push(item);
            })
            let dataInsurPreRate: Array<IAccInsurPreRate> = [];
            if(typeCreate ==0 ){

                _.forEach(self.listAccInsurPreRate(), function (value) {
                    let temp: IAccInsurPreRate = {
                        hisId: occAccIsHis.hisId,
                        occAccInsurBusNo: value.occAccInsurBusNo,
                        name: value.name,
                        fracClass: value.fracClass,
                        empConRatio: '',
                        useArt: value.useArt
                    };
                    dataInsurPreRate.push(temp);
                });
            }
            else{
                _.forEach(self.listAccInsurPreRate(), function (value) {
                    let temp: IAccInsurPreRate = {
                        hisId: occAccIsHis.hisId,
                        occAccInsurBusNo: value.occAccInsurBusNo,
                        name: value.name,
                        fracClass: value.fracClass,
                        empConRatio: value.empConRatio().toString(),
                        useArt: value.useArt
                    };
                    console.log(value.toString() + ' fuishuifhsduihfsudif');
                    dataInsurPreRate.push(temp);
                });
            }

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
            let dataToPassScreenE;
            if(self.listOccAccIsHis().length>0){
                dataToPassScreenE = self.listOccAccIsHis()[0].startYearMonth;
            }
            setShared('QMM011_E_PARAMS_INPUT', {
                startYearMonth:  dataToPassScreenE
            });

            modal("/view/qmm/011/e/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_E_PARAMS_OUTPUT');

                    if (params != null) {

                            self.isNewMode(MODE.NEW);
                            // Create new one OccAccIsPrRate
                            self.startYearMonth(params.startYearMonth);
                            self.listOccAccIsHis(self.addOccAccIsHis(self.startYearMonth(), self.listOccAccIsHis(),params.transferMethod));
                            self.selectedEmpInsHisId(self.listOccAccIsHis()[FIRST].hisId);
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
                nts.uk.ui.errors.clearAll();

            });
        }

        register(){
            let self = this;
            block.invisible();
            if(self.validate() || nts.uk.ui.errors.hasError()){
                block.clear();
                return;
            }
            let isNewMode = self.isNewMode() == MODE.NEW;
            _.each(self.listAccInsurPreRate(), function(item: AccInsurPreRate) {
                item.occAccInsurBusNo--;
            });
            let data: any = {
                listAccInsurPreRate: self.convertToCommand(self.listAccInsurPreRate()),
                isNewMode: isNewMode,
                startYearMonth: self.convertStringToYearMonth(self.startYearMonth()),
                endYearMonth:  self.convertStringToYearMonth(self.endYearMonth()),
                hisId: self.listAccInsurPreRate()[0].hisId

            }
            service.register(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.isNewMode(MODE.UPDATE);
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
            });
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
        validate(){
            $(".C3_6").trigger("validate");
            return error.hasError();
        }
        enableRegis(){
            return this.isNewMode() == MODE.NO;
        }

        enableNew(){
            return this.isNewMode() == MODE.NEW;
        }

        enableUpdate(){
            return this.isNewMode() == MODE.UPDATE;
        }


        openFscreen(){
            let self = this;
            let laststartYearMonth = self.listOccAccIsHis().length > 1 ? self.listOccAccIsHis()[self.index() + 1].startYearMonth : 0
            let canDelete: boolean = false;
            if (self.listOccAccIsHis().length > 2 && self.hisId() == self.listOccAccIsHis()[FIRST].hisId) {
                canDelete = true;
            }
            setShared('QMM011_F_PARAMS_INPUT', {
                startYearMonth: self.convertStringToYearMonth(self.startYearMonth()),
                endYearMonth: self.convertStringToYearMonth(self.endYearMonth()),
                insurrance: INSURRANCE.ACCIDENT_INSURRANCE_RATE,
                hisId: self.hisId(),
                laststartYearMonth: laststartYearMonth,
                canDelete :canDelete
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
                dto.occAccInsurBusNo = item.occAccInsurBusNo+1;
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
    export enum MODE {
        NEW = 0,
        UPDATE = 1,
        NO = 2
    }
    export const HIS_ID_TEMP = "000"
}