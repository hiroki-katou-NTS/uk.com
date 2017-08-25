module nts.uk.at.view.kmf003.b.viewmodel {
    export class ScreenModel {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        conditionValue: KnockoutObservable<string>;
        dateSelected: KnockoutObservable<string>;
        referenceDate: KnockoutObservable<string>;
        items: KnockoutObservableArray<Item>;
        grantReferenceDateOptions: KnockoutObservableArray<GrantReferenceDateOption>;
        payDayCalculate: KnockoutObservable<string>;
        displayDateSelected: KnockoutObservable<boolean>;
        conditionData: any;
        
        constructor() {
            var self = this;
            
            self.conditionData = nts.uk.ui.windows.getShared("KMF003_CONDITION_NO");
            self.code = ko.observable(self.conditionData.code);
            self.name = ko.observable(self.conditionData.name);            
            self.conditionValue = ko.observable(
                nts.uk.resource.getText("KMF003_37", [self.conditionData.conditionValue.option, self.conditionData.conditionValue.value, self.conditionData.conditionValue.afterValue])
            );
            self.dateSelected = ko.observable(self.convertToMonthDay(self.conditionData.dateSelected));
            
            if(self.conditionData.dateSelected === "") {
                self.displayDateSelected = ko.observable(false);
            } else {
                self.displayDateSelected = ko.observable(true);
            }
            
            if(self.conditionData.useCondition == true){
                var style = $('<style>table td.allow-pay { display: table-cell; }</style>');
                $('html > head').append(style);
            } else {
                var style = $('<style>table td.allow-pay { display: none; } table.contents-data td.pay-day-cal { width: 260px }</style>');
                $('html > head').append(style);
            }
            
            self.referenceDate = ko.observable("");
            self.items = ko.observableArray([]);
            
            self.grantReferenceDateOptions = ko.observableArray([
                new GrantReferenceDateOption(0, '入社日'),
                new GrantReferenceDateOption(1, '年休付与基準日')
            ]);
            
            self.payDayCalculate = ko.observable("");
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            service.findByCode(self.conditionData.conditionNo, self.conditionData.code).done(function(data){
                self.bindData(data);                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         * Binding data to screen.
         */
        bindData(data: any){
            var self = this;
            
            self.items.removeAll();
            
            //Update case
            for(var i = 0; i < data.length; i++){
                var item : IItem = {
                    grantYearHolidayNo: data[i].grantYearHolidayNo,
                    conditionNo: data[i].conditionNo,
                    yearHolidayCode: data[i].yearHolidayCode,
                    lengthOfServiceYears: data[i].lengthOfServiceYears,
                    lengthOfServiceMonths: data[i].lengthOfServiceMonths,
                    grantDays: data[i].grantDays,
                    limitedTimeHdDays: data[i].limitedTimeHdDays,
                    limitedHalfHdCnt: data[i].limitedHalfHdCnt,
                    grantReferenceDate: data[i].grantReferenceDate,
                    grantSimultaneity: data[i].grantSimultaneity,
                    grantDate: data[i].grantDate
                };
                self.items.push(new Item(item));
            }
            
            for(var i = data.length; i < 20; i++) {
                var item : IItem = {
                    grantYearHolidayNo: i + 1,
                    conditionNo: self.conditionData.conditionNo,
                    yearHolidayCode: self.conditionData.code,
                    lengthOfServiceYears: null,
                    lengthOfServiceMonths: null,
                    grantDays: null,
                    limitedTimeHdDays: null,
                    limitedHalfHdCnt: null,
                    grantReferenceDate: 0,
                    grantSimultaneity: false,
                    grantDate: ""
                };
                self.items.push(new Item(item));    
            }
        }
        
        /**
         * Calculate data function.
         */
        calculate() {
            var self = this;
            
            if(self.referenceDate() !== ""){
                var monthDay = String(self.conditionData.dateSelected); 
                monthDay = nts.uk.text.padLeft(monthDay, '0', 4); 
                var dateSelected = moment.utc(monthDay, "MMDDYY"); 
                var grantHolidayTblList = [];
                _.forEach(self.items(), function(item) {
                     if (item.lengthOfServiceYears() != null && item.lengthOfServiceMonths() != null) {
                         grantHolidayTblList.push({
                            grantYearHolidayNo: item.grantYearHolidayNo(),
                            conditionNo: item.conditionNo(),
                            yearHolidayCode: item.yearHolidayCode(),
                            lengthOfServiceYears: item.lengthOfServiceYears(),
                            lengthOfServiceMonths: item.lengthOfServiceMonths(),
                            grantDays: item.grantDays(),
                            limitedTimeHdDays: item.limitedTimeHdDays(),
                            limitedHalfHdCnt: item.limitedHalfHdCnt(),
                            grantReferenceDate: item.grantReferenceDate(),
                            grantSimultaneity: item.grantSimultaneity() ? 1 : 0
                        });
                    }
                });
                
                //勤続年数、0年0ヶ月は登録不可
                for(var i = 0; i < grantHolidayTblList.length; i++) {
                    if(Number(grantHolidayTblList[i].lengthOfServiceYears) == 0 && Number(grantHolidayTblList[i].lengthOfServiceMonths) == 0){
                        nts.uk.ui.dialog.alert({ messageId: "Msg_268" });
                        return;
                    }
                }   
                
                //重複した勤続年数の登録不可
                var valueArr = grantHolidayTblList.map(function(item){ return item.lengthOfServiceYears });
                var isDuplicate = valueArr.some(function(item, idx){ 
                    return valueArr.indexOf(item) != idx 
                });
                
                if(isDuplicate){
                    nts.uk.ui.dialog.alert({ messageId: "Msg_266" });
                }
                
                //勤続年数は上から昇順になっていること
                for(var i = 0; i < grantHolidayTblList.length; i++) {
                    if(grantHolidayTblList[i + 1] != undefined){
                        if(grantHolidayTblList[i].lengthOfServiceYears > grantHolidayTblList[i + 1].lengthOfServiceYears){
                            nts.uk.ui.dialog.alert({ messageId: "Msg_269" });
                            return;
                        }
                    }
                }   
                
                //勤続年数が入力されている場合、付与日数を入力すること
                //付与日数が入力されている場合、勤続年数を入力すること
                for(var i = 0; i < grantHolidayTblList.length; i++) {
                    var item = grantHolidayTblList[i];
                    // 勤続年数が入力されている場合、付与日数を入力すること
                    if ((item.lengthOfServiceMonths != "" || item.lengthOfServiceYears != "") 
                            && (item.grantDays == "")) {
                            nts.uk.ui.dialog.alert({ messageId: "Msg_270" });
                            return;
                    }
                    // 付与日数が入力されている場合、勤続年数を入力すること
                    if ((item.lengthOfServiceMonths == "" && item.lengthOfServiceYears == "") && (item.grantDays != "")) {
                            nts.uk.ui.dialog.alert({ messageId: "Msg_270" });
                            return;
                    }
                }   
                    
                var dataTranfer: service.DataTranfer = {
                    grantHolidayTblList: grantHolidayTblList,
                    useSimultaneousGrant: self.conditionData.useCondition ? 1 : 0, 
                    referDate: new Date(self.referenceDate()), 
                    simultaneousGrantDate: dateSelected
                }
                
                service.calculateGrantDate(dataTranfer).done(function(res) {
                    self.bindData(res);
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error.message);    
                });
            } else {
                nts.uk.ui.dialog.alert({ messageId: "Msg_272" });
            }
        }
        
        /**
         * Add or Update data to db.
         */
        submit() {
            var self = this;
            
            var grantHolidayTblList = [];
            _.forEach(self.items(), function(item) {
                if (item.lengthOfServiceYears() != null && item.lengthOfServiceMonths() != null) {
                    grantHolidayTblList.push({
                        grantYearHolidayNo: item.grantYearHolidayNo(),
                        conditionNo: item.conditionNo(),
                        yearHolidayCode: item.yearHolidayCode(),
                        lengthOfServiceYears: item.lengthOfServiceYears(),
                        lengthOfServiceMonths: item.lengthOfServiceMonths(),
                        grantDays: item.grantDays(),
                        limitedTimeHdDays: item.limitedTimeHdDays(),
                        limitedHalfHdCnt: item.limitedHalfHdCnt(),
                        grantReferenceDate: item.grantReferenceDate(),
                        grantSimultaneity: item.grantSimultaneity() ? 1 : 0
                    });
                }
            });
            
            var dataTranfer = {
                conditionNo: self.conditionData.conditionNo,
                yearHolidayCode: self.code(), 
                grantHolidayList: grantHolidayTblList, 
            }
        
            service.addYearHolidayGrant(dataTranfer).done(function(){
                nts.uk.ui.windows.close();
            }).fail(function(error){
                nts.uk.ui.dialog.alertError(error.message);    
            });
        }
        
        /**
         * Close dialog.
         */
        cancel() {
            nts.uk.ui.windows.close();
        }
        
        //Convert MD number to Date
        convertToMonthDay(monthDay: String){
            if(monthDay != ""){
                var md = String(monthDay); 
                md = nts.uk.text.padLeft(md, '0', 4); 
                return moment.utc(md, "MMDD").format("MMMDo"); 
            }
            
            return monthDay; 
        }
        
        //Set check or uncheck checkbox list
        checkAllowPayBelow(index: number, value: boolean): void {
            var self = this;
            if (value) {
                for (let i = index; i < self.items().length; i++) {
                    self.items()[i].grantSimultaneity(value);
                }
            } else {
                for (let i = 0; i < index; i++) {
                    self.items()[i].grantSimultaneity(value);
                }    
            }
            self.items.valueHasMutated();
        }
        
    }
    
    export class Item {
        grantYearHolidayNo: KnockoutObservable<number>;
        conditionNo: KnockoutObservable<number>;
        yearHolidayCode: KnockoutObservable<string>;        
        lengthOfServiceYears: KnockoutObservable<number>;
        lengthOfServiceMonths: KnockoutObservable<number>;
        grantDays: KnockoutObservable<number>;
        limitedTimeHdDays: KnockoutObservable<number>;
        limitedHalfHdCnt: KnockoutObservable<number>;
        grantReferenceDate: KnockoutObservable<number>;
        grantSimultaneity: KnockoutObservable<boolean>;
        grantDate: KnockoutObservable<string>;
        
        constructor(param: IItem) {
            var self = this;
            self.grantYearHolidayNo = ko.observable(param.grantYearHolidayNo);
            self.conditionNo = ko.observable(param.conditionNo);
            self.yearHolidayCode = ko.observable(param.yearHolidayCode);            
            self.lengthOfServiceYears = ko.observable(param.lengthOfServiceYears);
            self.lengthOfServiceMonths = ko.observable(param.lengthOfServiceMonths);
            self.grantDays = ko.observable(param.grantDays);
            self.limitedTimeHdDays = ko.observable(param.limitedTimeHdDays);
            self.limitedHalfHdCnt = ko.observable(param.limitedHalfHdCnt);
            self.grantReferenceDate = ko.observable(param.grantReferenceDate);
            self.grantSimultaneity = ko.observable(param.grantSimultaneity);    
            self.grantDate = ko.observable(param.grantDate);   
            self.grantSimultaneity.subscribe(function(value){
                checkAllowPayBelow(self);
            });
        }
    }
    
    export interface IItem {
        grantYearHolidayNo: number;
        conditionNo: number;
        yearHolidayCode: string;
        lengthOfServiceYears: number;
        lengthOfServiceMonths: number;
        grantDays: number;
        limitedTimeHdDays: number;
        limitedHalfHdCnt: number;
        grantReferenceDate: number;
        grantSimultaneity: boolean;   
        grantDate: string;     
    }
     
    export class GrantReferenceDateOption {
        code: number;
        name: string;
        
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    function checkAllowPayBelow(item: Item): void {
        var self = nts.uk.ui._viewModel.content;
        var index = _.indexOf(self.items(), item);
        self.checkAllowPayBelow(index, item.grantSimultaneity());
    }

}