module nts.uk.pr.view.qmm005.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import getSetDaySupports = nts.uk.pr.view.qmm005.a.service.getSetDaySupports;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        processCateNo: any;
        processInfomationDto: any;
        setDaySupportDtoList: any;
        setPaymentDate: KnockoutObservable<model.SetPaymentDateTransfer>;
        // B3_4	処理年
        processingYear: KnockoutObservable<number>;
        processingDivisionName: KnockoutObservable<string>;
        listSettingPayment: KnockoutObservableArray<model.PaymentDateItem>;
        valPayDateSet = {};
        itemList: KnockoutObservableArray<any>;
        // B2_3	処理年
        processingYearList: KnockoutObservableArray<model.ItemModel>;
        btnText: any;
        screenModeCreate: KnockoutObservable<boolean>;
        processingYearListSelectedCode: KnockoutObservable<number>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        switchOptions: KnockoutObservableArray<any>;
        show: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        setDaySupportList: KnockoutObservableArray<model.SetDaySupport>;
        processInfomation: KnockoutObservableArray<model.ProcessInfomation>;

        constructor() {
            var self = this;
            let objItem = {
                paymentDate: "09/08/2018",
                employeeExtractionReferenceDate: "09/08/2018",
                socialInsuranceCollectionMonth: "09/08/2018",
                specificationPrintDate: "09/08/2018",
                numberOfWorkingDays: 24,
                targetMonth: "09/08/2018",
                incomeTaxReferenceDate: "09/08/2018",
                accountingClosureDate: "09/08/2018",
                socialInsuranceStandardDate: "09/08/2018",
                employmentInsuranceStandardDate: "09/08/2018",
                timeClosingDate: "09/08/2018",
            }
            self.listSettingPayment = ko.observableArray([
                new model.PaymentDateItem(objItem),
            ]);
            self.setPaymentDate = ko.observable(new model.SetPaymentDateTransfer(self.processingYear, self.listSettingPayment));
            self.show = ko.observable(true);
            self.btnText = ko.computed(function () {
                if (self.show()) return "-";
                return "+";
            });
            self.screenModeCreate = ko.observable(false);
            self.itemList = ko.observableArray([]);
            //B2_3 処理年
            this.columns = ko.observableArray([
                { headerText: '', key: 'code', width: 0, hidden: true },
                { headerText: nts.uk.resource.getText('#QMM005_25'), key: 'name', width: 150 },
            ]);
            this.enable = ko.observable(true);
            self.processingYearListSelectedCode = ko.observable(-1);
            self.setDaySupportList = ko.observableArray([]);
            self.processInfomation = ko.observableArray([]);
            self.switchOptions = ko.observableArray([
                {code: "1", name: '四捨五入'},
                {code: "2", name: '切り上げ'},
                {code: "3", name: '切り捨て'}
            ]);
            self.processingYear.subscribe(function (newValue) {
                self.selectProcessingYear(newValue);
            })

        }


        toggle(): void {
            this.show(!this.show());

        }

        reflectSystemReferenceDateInformation(): void {
        }

        reflect(): void {
        }

        newCharacterSetting(): void {
            this.screenModeCreate(true);

        }

        saveCharacterSetting(): void {
        }

        cancelCharacterSetting(): void {
        }


        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen(){
            // gọi vào domain 処理区分基本情報 (ProcessInfomation), 給与支払日設定 (SetDaySupport)
            var self = this;
            service.getProcessInfomation(self.processCateNo).done(function (data) {
                self.processInfomationDto = data;
                // B3_2
                self.processingDivisionName = ko.observable(nts.uk.text.format(nts.uk.resource.getText("#QMM005_97"), self.processCateNo, data.processDivisionName));
            });
            service.getSetDaySupport(self.processCateNo).done(function (data) {
                // B2_2
                // 詳細設定対象_処理年 processingYearList
                var array = [];
                _.forEach(data, function (setDaySupport) {
                    _.forEach(setDaySupport, function (key, value) {
                        if(key == "processDate"){
                            array = _.union(array, [ new model.ItemModel(value.toString().substr(0,4), value.toString().substr(0,4))]);
                        }
                    });
                });
                self.processingYearList = ko.observableArray(array);
                if (array.length > 0){
                    self.selectProcessingYear(array[0].code);
                }
            });
        }

        selectProcessingYear(year){
            /* phần lớn dữ liệu lấy từ setDaySupport*/
            // B3_4				処理年
            var self = this;
            self.processingYear = ko.observable(year);
            service.getSelectProcessingYear(self.processCateNo, year).done(function (data) {
                _.forEach(self.setDaySupportDtoList, function (setDaySupport) {
                    var array = {};
                    // B4_10 支払年月日
                    array["paymentDate"] = ko.observable(setDaySupport.processDate);
                    // B4_11 支払曜日
                    // B4_12				社員抽出基準日
                    array["employeeExtractionReferenceDate"] = ko.observable(setDaySupport.empExtraRefeDate);
                    // B4_13 社会保険徴収月
                    array["socialInsuranceCollectionMonth"] = ko.observable(setDaySupport.socialInsurdCollecMonth);
                    // B4_16 要勤務日数
                    array["numberOfWorkingDays"] = ko.observable(setDaySupport.numberWorkDay);
                    // B6_7	社会保険基準日
                    array["socialInsuranceStandardDate"] = ko.observable(setDaySupport.socialInsurdStanDate);
                    // B6_8	雇用保険基準日
                    array["employmentInsuranceStandardDate"] = ko.observable(setDaySupport.empInsurdStanDate);
                    // B6_9	勤怠締め日
                    array["timeClosingDate"] = ko.observable(setDaySupport.closeDateTime);
                    // B6_10 所得税基準日
                    array["incomeTaxReferenceDate"] = ko.observable(setDaySupport.incomeTaxDate);
                    // B6_11
                    array["accountingClosureDate"] = ko.observable(setDaySupport.closureDateAccounting)
                    self.listSettingPayment.apply(array);
                });
            });
        }
        //to E screen
        reflectionPressingProcess(){

        }
        //to E screen
        ReflectSystemReference(){
            var self = this;
            setShared("QMM005bParams", { processCateNo: self.processCateNo, processingYear: self.processingYear});
            modal("/view/qmm/005/e/index.xhtml").onClosed(() => {
                var reflect = getShared("QMM005eReflect");
                if(reflect) {
                    var params = getShared("QMM005eParams");
                    var index = 0;
                    var startMonth = getShared("QMM005estartMonth");
                    _.forEach(self.listSettingPayment, function (settingPayment) {
                        if(++index >= startMonth) {
                            /* B4_10	支払年月日
                            ※1　支払日チェックが入っている場合のみ更新する
                            ※10 勤怠締め日チェックが入っている場合のみ更新する
                            */
                            if(params.dailyPaymentDateCheck && params.timeClosingDateCheck){
                                settingPayment.paymentDate = year/month/date;
                            }
                            // B4_11    支払曜日
                            // ※1　支払日チェックが入っている場合のみ更新する
                            if (params.dailyPaymentDateCheck){

                            }
                            // B4_12	社員抽出基準日
                            // ※2　対象社員抽出基準日チェックが入っている場合のみ更新する
                            if (params.empExtractionRefDateCheck){

                            }

                            // B4_13	社会保険徴収月
                            // ※3　社会保険徴収月チェックが入っている場合のみ更新する
                            if (params.socialInsuranceMonthCheck){

                            }
                            // B4_15	明細書印字年月
                            // ※4　要勤務日数チェックが入っている場合のみ更新する
                            if (params.numWorkingDaysCheck){

                            }
                            // B4_16	要勤務日数
                            // ※5　明細書印字年月チェックが入っている場合のみ更新する
                            if (params.specPrintDateCheck){

                            }
                            // B6_7		社会保険基準日
                            // ※6　社会保険基準日チェックが入っている場合のみ更新する
                            if (params.socialInsuranceDateCheck){

                            }
                            // B6_8		雇用保険基準日
                            // ※7　雇用保険基準日チェックが入っている場合のみ更新する
                            if (params.empInsuranceStandardDateCheck){

                            }
                            // B6_9		勤怠締め日
                            // ※10 勤怠締め日チェックが入っている場合のみ更新する
                            if (params.timeClosingDateCheck){

                            }

                            /*B6_10	所得税基準日
                            ※8　所得税基準日チェックが入っている場合のみ更新する*/
                            if (params.incomeTaxReferenceCheck){

                            }
                            /*B6_11	経理締め日
                            ※9　経理締め日チェックが入っている場合のみ更新する*/
                            if (params.accountingClosureDateCheck){

                            }
                        }
                    });
                }
            });
        }

        registrationPress(){

        }
    }
}
