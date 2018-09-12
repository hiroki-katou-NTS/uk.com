module nts.uk.com.view.qmm011.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm011.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        listPerFracClass: KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listEmpInsHis: KnockoutObservableArray<IEmplInsurHis> = ko.observableArray([]);
        listEmpInsurPreRate: KnockoutObservableArray<EmpInsurPreRate> = ko.observableArray([]);
        selectedEmpInsHis: KnockoutObservable<IEmplInsurHis> = ko.observable();
        hisId: KnockoutObservable<string> = ko.observable('');
        index: KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId: KnockoutObservable<string> = ko.observable('');
        monthlyCalendar: KnockoutObservable<string> = ko.observable('');
        startYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable();
        laststartYearMonth: KnockoutObservable<string> = ko.observable('');
        isNewMode: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) => {
                let self = this;
                self.selectedEmpInsHis(self.listEmpInsHis()[self.index()]);
                self.setEmplInsurHis(self.selectedEmpInsHis());
                self.getEmpInsurPreRate();
            });
        }


        // 初期データ取得処理
        initScreen(hisId: string) {
            let self = this;
            block.invisible();
            service.getEmpInsHis().done((listEmpInsHis: Array<IEmplInsurHis>) => {
                if (listEmpInsHis && listEmpInsHis.length > 0) {
                    self.listEmpInsHis(listEmpInsHis);
                    self.index(self.getIndex(null));
                    if (hisId != null) {
                        self.index(self.getIndex(hisId));
                    }
                    self.selectedEmpInsHisId(self.listEmpInsHis()[self.index()].hisId);
                }
            }).always(() => {
                block.clear();
            });
        }
        
        getEmpInsurPreRate() {
            let self = this;
            service.getEmpInsurPreRate(self.selectedEmpInsHisId()).done((listEmpInsurPreRate: Array<IEmpInsurPreRate>) => {
                if (listEmpInsurPreRate && listEmpInsurPreRate.length > 0) {
                    self.listEmpInsurPreRate(EmpInsurPreRate.fromApp(listEmpInsurPreRate));
                    self.isNewMode(false);
                } else {
                    self.isNewMode(true);
                    self.addEmpInsurPreRate();
                }
            });
        }

        setIEmplInsurHis(param: IEmplInsurHis) {
            let self = this;
            self.hisId(param.hisId);
            self.startYearMonth(param.startYearMonth);
            self.endYearMonth(param.endYearMonth);
        }
        
        addEmpInsurPreRate(){
            let self = this;
            let empInsurPreRate: EmpInsurPreRate;
            for(let i = 0; i< 3; i++) {
                empInsurPreRate = new EmpInsurPreRate();
                empInsurPreRate.hisId = self.hisId();
                empInsurPreRate.empPreRateId = i;
                empInsurPreRate.indBdRatio = ko.observable();
                empInsurPreRate.empContrRatio = ko.observable();
                empInsurPreRate.perFracClass = ko.observable(1);
                empInsurPreRate.busiOwFracClass = ko.observable(1);
                self.listEmpInsurPreRate.push(empInsurPreRate);
            }
        }
        
        convertToCommand(dto :Array<EmpInsurPreRate>){
            let listEmpInsurPreRate :Array <EmpInsurPreRate> = [];
            _.each(dto, function(item: EmpInsurPreRate) {
                let temp = new EmpInsurPreRate();
                temp.hisId = item.hisId;
                temp.empPreRateId = item.empPreRateId;
                temp.indBdRatio = Number(item.indBdRatio());
                temp.empContrRatio = Number(item.empContrRatio());
                temp.perFracClass = item.perFracClass();
                temp.busiOwFracClass = item.busiOwFracClass();
                listEmpInsurPreRate.push(temp);
            })
            return listEmpInsurPreRate;
        }

        getIndex(hisId: string) {
            let self = this;
            let temp = _.findLast(self.listEmpInsHis(), function(x) {
                return x.hisId == hisId;
            });
            if (temp) {
                return temp;
            }
            return 0;
        }

        openEscreen() {
            let self = this;
            setShared('QMM011_E_PARAMS', {
                startYearMonth: self.startYearMonth()
            });

            modal("/view/qmm/011/e/index.xhtml").onClosed(function() {
                let params = getShared('QMM011_B_Param');
                if (params && params.result == true) {
                    self.isNewMode(true);
                    if (params.transferHistory) {
                        self.initScreen(null);
                    }
                }
            });
        }

        register() {
            let self = this;
            let data: any = {
                listEmpInsurPreRate: self.convertToCommand(self.listEmpInsurPreRate()),
                isNewMode: self.isNewMode(),
                hisId: self.hisId(),
                startYearMonth: self.convertStringToYearMonth(self.startYearMonth()),
                endYearMonth:  self.convertStringToYearMonth(self.endYearMonth())
            }
            service.register(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    self.isNewMode(false);
                    self.initScreen(null);
                });
            });
        }
        
        
        
        setEmplInsurHis(emplInsurHis: IEmplInsurHis) {
            let self = this
            self.hisId(emplInsurHis.hisId);
            self.startYearMonth(self.convertMonthYearToString(emplInsurHis.startYearMonth));
            self.endYearMonth(self.convertMonthYearToString(emplInsurHis.endYearMonth));
        }

        openFscreen() {
            let self = this;
            setShared('QMM011_F_PARAMS', {
                startYearMonth: self.listEmpInsHis(),
                endYearMonth: self.startYearMonth(),
                insurrance: INSURRANCE.EMPLOYMENT_INSURRANCE_RATE,
                hisId: self.hisId()
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
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }
        
        convertStringToYearMonth(yearMonth: any){
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.slice(0, 4) + yearMonth.slice(5, 7);
            return yearMonth;
        }
    }
    

    class IEmplInsurHis {
        hisId: string
        startYearMonth: string;
        endYearMonth: string;
        between: string;
    }

    class EmplInsurHis {
        hisId: string
        startYearMonth: string;
        endYearMonth: string;
        between: string;
        constructor(param: IEmplInsurHis) {
            this.hisId(param.hisId || '');
            this.startYearMonth(param.startYearMonth || '');
            this.endYearMonth(param.endYearMonth || '');
            this.between('~');
        }

    }

    class IEmpInsurPreRate {
        hisId: string;
        empPreRateId: number;
        indBdRatio: number;
        empContrRatio: number;
        perFracClass: number;
        busiOwFracClass: number;
    }

    class EmpInsurPreRate {
        hisId: string;
        empPreRateId: number;
        indBdRatio: number;
        empContrRatio: number;
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
    
    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }
}