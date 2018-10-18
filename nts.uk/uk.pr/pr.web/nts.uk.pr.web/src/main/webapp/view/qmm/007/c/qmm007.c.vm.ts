module nts.uk.pr.view.qmm007.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import model = qmm007.share.model;
    export class ScreenModel {
        startYearMonth:         KnockoutObservable<number> = ko.observable();
        endYearMonth:           KnockoutObservable<number> = ko.observable();
        startLastYearMonth:     KnockoutObservable<number> = ko.observable();
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        insurrance:             KnockoutObservable<number> = ko.observable();

        // validate disable item
        isFirst:              KnockoutObservable<boolean> = ko.observable(true);
        insuranceName:          KnockoutObservable<string> = ko.observable('');
        mPayrollUnitPriceHis : KnockoutObservableArray<PayrollUnitPriceHistoryDto> = ko.observableArray(null);
        // data
        name :KnockoutObservable<string> = ko.observable('項目移送');
        code :KnockoutObservable<string> = ko.observable('項目移送');
        textResourceRadioFirt :KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
            self.initView();
        }
        submit(){
            let self = this;
            let data: any = {
                cId: '',
                hisId: self.mPayrollUnitPriceHis().hisId,
                code: self.mPayrollUnitPriceHis().code,
                startYearMonth: Number(self.startYearMonth()),
                endYearMonth: self.mPayrollUnitPriceHis().endYearMonth,
                isMode:this.methodEditing()
            }

            if(this.methodEditing() == EDIT_METHOD.DELETE){
                dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                    service.submitPayrollUnitPriceHis(data).done((data) => {
                        dialog.info({ messageId: "Msg_16" }).then(function () {
                            setShared('QMM007_C_PARAMS_OUTPUT', {
                                methodEditing: self.methodEditing(),
                                startYearMonth: self.startYearMonth()
                            });
                            self.cancel();
                        });
                    }).fail(function (res: any) {
                        if (res)
                            dialog.alertError(res);
                    })
                }).ifCancel(() => {
                    nts.uk.ui.block.clear();
                    return ;
                });

            }
            else {
                service.submitPayrollUnitPriceHis(data).done(() => {
                    dialog.info({ messageId: "Msg_15" }).then(()=>{
                        setShared('QMM007_C_PARAMS_OUTPUT', {
                            methodEditing: self.methodEditing(),
                            startYearMonth: self.startYearMonth()
                        });
                        self.cancel();
                    });
                }).fail(function (res: any) {
                    if (res)
                        dialog.alertError(res);
                }).always((res:any) => {
                    if (res)
                        dialog.alertError(res);
                });
            }
        }

        initView() {
            let self = this;
            // start
            let params: any = getShared('QMM007_PARAMS_TO_SCREEN_C');
            if (!params) {
               return;
            }
            self.name(params.name);
            self.code(params.code);
            self.startYearMonth(params.startYearMonth);
            self.endYearMonth(getText('QMM007_43',[self.convertYearMonthToDisplayYearMonth(params.endYearMonth)]));
            self.textResourceRadioFirt(getText('QMM007_42',[self.convertMonthYearToString(self.startYearMonth())]));
            self.isFirst(params.isFirst);
            self.mPayrollUnitPriceHis(new PayrollUnitPriceHistoryDto('',params.hisId,params.code,params.startYearMonth,params.endYearMonth));
        }
       hasRequired() {
           return !(this.methodEditing() != EDIT_METHOD.UPDATE)
       }

        cancel(){
            close();
        }
        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }
        convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }

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

    class PayrollUnitPriceHisKey {
        cId: string;
        code: string;
        hisId: string;
        constructor(cId:string,hisId:string,code:string){
            this.cId=cId;
            this.code=code;
            this.hisId=hisId;
        }
    }

    class PayrollUnitPriceHistoryCommand {
        cId: string;
        hisId: string;
        code: string;
        startYearMonth: number;
        endYearMonth: number;
        isMode: number;

        constructor(cId: string, hisId: string, code: string, startYearMonth: number, endYearMonth: number, isMode: number) {
            this.cId = cId;
            this.hisId = hisId;
            this.code = code;
            this.startYearMonth = startYearMonth;
            this.endYearMonth = endYearMonth;
            this.isMode = isMode;
        }

    }




}