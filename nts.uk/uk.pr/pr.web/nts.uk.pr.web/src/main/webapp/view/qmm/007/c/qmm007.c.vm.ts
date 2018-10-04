module nts.uk.pr.view.qmm007.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm007.share.model;
    import server = nts.uk.pr.view.qmm007.c;
    export class ScreenModel {
        startYearMonth:         KnockoutObservable<number> = ko.observable();
        endYearMonth:           KnockoutObservable<number> = ko.observable();
        startLastYearMonth:     KnockoutObservable<number> = ko.observable();
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        selectedId:             KnockoutObservable<string> = ko.observable('');
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        insurrance:             KnockoutObservable<number> = ko.observable();
        hisId:                  KnockoutObservable<string> = ko.observable('');
        isHisFirst:              KnockoutObservable<boolean> = ko.observable(true);
        insuranceName:          KnockoutObservable<string> = ko.observable('');
        mPayrollUnitPriceHis : KnockoutObservableArray<PayrollUnitPriceHistoryDto> = ko.observableArray(null);
        constructor() {
            let self = this;
            self.innitView();



        }
        submit(){
            // if(this.methodEditing() == EDIT_METHOD.UPDATE){
            //     service.updatePayrollUnitPriceHis(hisId).done((data) => {
            //         console.log(data);
            //     }).fail(function(res: any) {
            //         if (res)
            //             dialog.alertError(res);
            //     }).always(() => {
            //         block.clear();
            //     });
            // }
            // else{
            //     service.deletePayrollUnitPriceHis(hisId).done((data) => {
            //         console.log(data);
            //     }).fail(function(res: any) {
            //         if (res)
            //             dialog.alertError(res);
            //     }).always(() => {
            //         block.clear();
            //     });
            // }
        }

        innitView(){
            let self = this;
            let to = getText('QMM011_9');
            self.endYearMonth(' '+ to + ' ' + self.convertMonthYearToString(999912));

            // // start
            // let params: any = getShared('QMM007_PARAMS_TO_SCREEN_C');
            // if(params){
            //     self.isHisFirst(params.isHisFirst);
            //     self.getPayrollUnitPriceHis(params.hisId);
            // }
            self.getPayrollUnitPriceHis("0000000101");
        }

        getPayrollUnitPriceHis(hisId :string ) {
            let self = this;
            service.getPayUnitPriceHist(hisId).done((data:PayrollUnitPriceHistoryDto) => {
               self.mPayrollUnitPriceHis(ko.observableArray(data));
            });

        }

        hasRequired(){
            if(this.methodEditing() != EDIT_METHOD.UPDATE) {

                return false;
            }
            return true;
        }

        validateYearMonth(){

        }

        cancel(){
            close();
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
            yearMonth = yearMonth.substring(3);
            yearMonth = yearMonth.slice(0, 4) + yearMonth.slice(5, 7);
            return yearMonth;
        }
        // 「初期データ取得処理
    }

    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM007_40')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM007_41'))
        ];
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }
    class PayrollUnitPriceHistoryDto{
        /**
         * 会社ID
         */
         cId:string;

        /**
         * 履歴ID
         */
        hisId:string;

        /**
         * コード
         */
          code:string;

        /**
         * 開始年月
         */
          startYearMonth: number;

        /**
         * 終了年月
         */
          endYearMonth :number;
          constructor(cId:string,hisId:string,code:string,startYearMonth: number,endYearMonth :number){
                this.cId=cId;
                this.code=code;
                this.hisId=hisId;
                this.startYearMonth=startYearMonth;
                this.endYearMonth = endYearMonth;
          }
    }
    class AcquiCondiPayrollHis{
        cId :string;
        code :string;
        startYearMonth:number

    }




}