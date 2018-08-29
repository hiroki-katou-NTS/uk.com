module nts.uk.com.view.qmm011.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        listPerFracClass:       KnockoutObservableArray<ItemModel> = ko.observableArray(getListPerFracClass());
        listEmpInsHis:          KnockoutObservableArray<IEmplInsurHis> = ko.observableArray([]);
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

        constructor() {
            let self = this;
            self.initScreen(null);
            self.selectedEmpInsHisId.subscribe((data) =>{
                let self = this;
                // self.selectedEmpInsHis(self.);
            });
        }


        // 「初期データ取得処理
        initScreen(hisId: string){
            let self = this;
            // block.invisible();
            // service.getEmpInsHis().done((listEmpInsHis: Array<EmploymentInsRate>) =>{
            //     if (listEmpInsHis && listEmpInsHis.length > 0) {
            //         self.listEmpInsHis(listEmpInsHis);
            //         self.index(self.getIndex());
            //         self.selectedEmpInsHisId(self.listEmpInsHis()[self.index()]);
            //     }
            // }).always(() => {
            //     block.clear();
            // });
        }
        setEmpInsRate(param: IEmplInsurHis){
            let self = this;
            self.hisId(param.hisId);
            self.startDate(param.startDate);
            self.endDate(param.endDate);
        }

        getIndex(){
            let self= this;
            let temp = _.findLast(self.listEmpInsHis(), function(x) {
                return x.hisId == self.hisId;
            });
            if (temp) {
                return temp;
            }
            return 0;
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
            this.hisId = param.hisId;
            this.startDate = param.startDate;
            this.endDate = param.endDate;
            this.between = '~';
        }
    }
    export function getListPerFracClass(): Array<ItemModel> {
        return [
            new ItemModel(0, getText('CMF002_358')),
            new ItemModel(1, getText('CMF002_359')),
            new ItemModel(2, getText('CMF002_360')),
            new ItemModel(3, getText('CMF002_361')),
            new ItemModel(4, getText('CMF002_362'))
        ];
    }
    export class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}