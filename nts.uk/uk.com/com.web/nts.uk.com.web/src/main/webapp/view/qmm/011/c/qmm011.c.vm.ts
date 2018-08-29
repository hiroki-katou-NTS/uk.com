module nts.uk.com.view.qmm011.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import model = nts.uk.at.view.qmm011.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        //new
        listPerFracClass:       KnockoutObservableArray<model.ItemModel> = ko.observableArray(getListPerFracClass());
        listEmpInsHis:          KnockoutObservableArray<EmploymentInsRate> = ko.observableArray([]);
        selectedEmpInsHis:      KnockoutObservable<EmploymentInsRate> = ko.observable();
        hisId:                  KnockoutObservable<string> = ko.observable('');
        index:                  KnockoutObservable<number> = ko.observable(0);
        selectedEmpInsHisId:    KnockoutObservable<string> = ko.observable('');
        monthlyCalendar:        KnockoutObservable<string> = ko.observable('2010/1/1');
        startDate:              KnockoutObservable<string> = ko.observable('2010/1/1');
        endDate:                KnockoutObservable<string> = ko.observable('2010/1/1');
        indBdRation:            KnockoutObservable<string> = ko.observable('');
        perFracClass:           KnockoutObservable<string> = ko.observable('');
        empContrRatio:          KnockoutObservable<string> = ko.observable('');
        busiOwFracClass:        KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            this.listEmpInsHis = ko.observableArray([]);

            for(let i = 1; i < 100; i++) {
                this.listEmpInsHis.push(new EmploymentInsRate('00' + i, "2010/1/1", "2010/1/1"));
            }

            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                { headerText: '名称', key: 'name', width: 150, hidden: true },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '説明1', key: 'other1', width: 150},
                { headerText: '説明2', key: 'other2', width: 150, isDateColumn: true, format: 'YYYY/MM/DD' }
            ]);


            self.selectedEmpInsHisId.subscribe((data) =>){
                let self = this;
                self.selectedEmpInsHis(self.index());
            });
    }


    // 「初期データ取得処理
    initScreen(hisId: string){
        let self = this;
//           block.invisible();
//           service.getEmpInsHis().done((listEmpInsHis: Array<EmploymentInsRate>) =>{
//                if (listEmpInsHis && listEmpInsHis.length > 0) {
//                    self.listEmpInsHis(listEmpInsHis);
//                    self.index(self.getIndex());
//                    self.selectedEmpInsHisId(self.listEmpInsHis()[self.index()]);
//                }
//            }).always(() => {
//                block.clear();
//            });
    }
    setEmpInsRate(param: EmploymentInsRate){
        let self = this;
        self.hisId = param.hisId;
        self.startDate = param.startDate;
        self.endDate = param.endDate;
    }

    getIndex(){
        let temp = _.findLast(self.listEmpInsHis(), function(x) {
            return x.hisId == hisId;
        });
        if (temp) {
            return temp;
        }
        return 0;
    }


}

class EmploymentInsRate{
    hisId: string
    startDate: string;
    endDate: string;
    between: string;
    constructor(hisId: string, startDate: string, endDate: string) {
        this.hisId = hisId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.between = '~';
    }
    export function getListPerFracClass(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('qmm011_358')),
            new model.ItemModel(1, getText('qmm011_359')),
            new model.ItemModel(2, getText('qmm011_360')),
            new model.ItemModel(3, getText('qmm011_361')),
            new model.ItemModel(4, getText('qmm011_362'))
        ];
    }
}