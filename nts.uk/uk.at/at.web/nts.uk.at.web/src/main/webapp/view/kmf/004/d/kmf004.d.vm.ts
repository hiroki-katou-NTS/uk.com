module nts.uk.at.view.kmf004.d.viewmodel {
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    export class ScreenModel {
        // smt
        grantDates: KnockoutObservableArray<GrantDateTblDto> = ko.observableArray([]);
        // D2_3
        lstGrantDate: KnockoutObservableArray<GrantDateItem> = ko.observableArray([]);
        // D2_3
        columns: KnockoutObservableArray<any>;
        // D2_3
        selectedCode: KnockoutObservable<string> = ko.observable("");
        // D3_5
        grantDateCode: KnockoutObservable<string> = ko.observable("");
        // D3_7
        grantDateName: KnockoutObservable<string> = ko.observable("");
        // D3_8
        isSpecified: KnockoutObservable<boolean> = ko.observable(false);
        // D4_3
        items: KnockoutObservableArray<Item> = ko.observableArray([]);
        // D4_7, D4_8
        enableElapsedYears: KnockoutObservable<boolean> = ko.observable(false);
        // D1_3
        editMode: KnockoutObservable<boolean> = ko.observable(true);
        
        fixedAssign: KnockoutObservable<boolean> = ko.observable(false);
        // D5_3
        gDateGrantedDays: KnockoutObservable<number> = ko.observable(null);
        sphdCode: any;
        // D3_6
        codeEnable: KnockoutObservable<boolean> = ko.observable(false);
        // D5_5, D5_3, 
        gDateGrantedDaysEnable: KnockoutObservable<boolean> = ko.observable(false);
        // D5_5, D5_3
        daysReq: KnockoutObservable<boolean> = ko.observable(false);
        // D1_1
        newModeEnable: KnockoutObservable<boolean> = ko.observable(true);
        isDelete: KnockoutObservable<boolean> = ko.observable(false);
        // D5_1
        grantCls: KnockoutObservable<boolean> = ko.observable(false);
        
        isSpecifiedEnable: KnockoutObservable<boolean> = ko.observable(true);
        // D5_5
        cycleYear: KnockoutObservable<number> = ko.observable(null);
        // D5_6
        cycleMonth: KnockoutObservable<number> = ko.observable(null)

        constructor() {
            let self = this;
            
            self.sphdCode = nts.uk.ui.windows.getShared("KMF004_A_DATA");
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_5"), key: 'grantDateCode', width: 60 },
                { headerText: nts.uk.resource.getText("KMF004_6"), key: 'grantDateName', width: 160, formatter: _.escape}
            ]);
            
            self.selectedCode.subscribe(function(grantDateCode) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(grantDateCode.length > 0){
                    var selectedItem = _.find(self.grantDates(), (o) => { return o.grantDateCode == grantDateCode; });
                    
                    self.grantDateCode(selectedItem.grantDateCode);
                    self.grantDateName(selectedItem.grantDateName);
                    self.isSpecified(selectedItem.isSpecified);
                    self.fixedAssign(selectedItem.elapseYearDto.fixedAssign);
                    self.cycleYear(selectedItem.grantCycleAfterTblDto.year);
                    self.cycleMonth(selectedItem.grantCycleAfterTblDto.month);
                    self.gDateGrantedDays(selectedItem.grantedDays);
                    let elapseYearList: Array<any> = [];
                    let elapseYearMonthTblList: Array<any> = [];

                    service.findByGrantDateCd(self.sphdCode, selectedItem.grantDateCode)
                    .done(function(data) {
                        elapseYearList = data;
                    }).fail(function(res) {                        
                    });

                    service.findElapseYearByCd(self.sphdCode)
                    .done(function(data) {
                        elapseYearMonthTblList = data;
                    }).fail(function(res) {                        
                    });
                    self.bindElapseYearDto(elapseYearList, elapseYearMonthTblList);
                    if (self.isSpecified() == true) {
                        self.isSpecifiedEnable(false);
                    } else {
                        self.isSpecifiedEnable(true);
                    }
                    self.codeEnable(false);
                    self.editMode(true);
                    self.newModeEnable(true);
                    
                    if(!self.isDelete()) {
                        $("#inpPattern").focus();
                    }
                }
            });  
            
            self.fixedAssign.subscribe(function(value) {
                if(value){
                    self.gDateGrantedDaysEnable(true);
                    self.daysReq(true);
                    // clear all error
                    nts.uk.ui.errors.clearAll();
                } else {
                    self.gDateGrantedDaysEnable(false);
                    self.daysReq(false);
                    // clear all error
                    nts.uk.ui.errors.clearAll();
                }
            }); 
            
            self.isSpecified.subscribe((val) => {
                if(val == true){
                    self.enableElapsedYears(true);
                } else {
                    self.enableElapsedYears(false);
                }

            });
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            $.when(self.getData()).done(function() {
                                    
                if (self.lstGrantDate().length > 0) {
                    self.selectedCode(self.lstGrantDate()[0].grantDateCode);
                    self.selectedCode.valueHasMutated();
                } else {
                    self.newModeEnable(false);
                    self.newMode();
                }
                
                nts.uk.ui.errors.clearAll();
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            nts.uk.ui.errors.clearAll();
            
            return dfd.promise();
        }
        
        /** get data from db **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            self.lstGrantDate([]);
            
            service.findBySphdCd(self.sphdCode).done(function(data) {
                self.grantDates = data;
                
                _.forEach(data, function(item) {
                    self.lstGrantDate.push(new GrantDateItem(item.grantDateCode, item.grantDateName));
                });
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /** bind elapse year data **/
        bindElapseYearDto(elapseYearList: Array<GrantElapseYearMonthDto>, elapseYearMonthTblList: Array<ElapseYearMonthTblDto>) {
            let self = this;     
            self.items([]);
            let keyMap: any = {};
       
            if(elapseYearList) {
                _.forEach(elapseYearList, e => {
                    keyMap[e.elapseNo] = e;
                }); 
                let itemTotals = 0;
                for(let item of elapseYearMonthTblList){
                    let elapse = new Item(ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null));
                    let currentItem = keyMap[item.grantCnt];
                    if(currentItem){
                        elapse.elapseNo(item.grantCnt);
                        elapse.months(item.month);
                        elapse.years(item.year);
                        elapse.grantedDays(currentItem.grantedDays);
                        itemTotals ++;
                    }
                    self.items.push(elapse);  
                }
               
                for(let i = itemTotals + 1; i <= 20; i++) {
                    let elapseNull = new Item(ko.observable(i), ko.observable(null), ko.observable(null), ko.observable(null));
                    self.items.push(elapseNull);    
                }
            } else {
                for(let i = 1; i <= 20; i++) {
                    let elapseNull = new Item(ko.observable(i), ko.observable(null), ko.observable(null), ko.observable(null));
                    self.items.push(elapseNull);    
                }
            }
        }
        
        /** update or insert data when click button register **/
        register() {  
            let self = this; 
            let checkErr = false;
                        
            $("#inpCode").trigger("validate");
            $("#inpPattern").trigger("validate");
                
            if (nts.uk.ui.errors.hasError()) {
                return;       
            }

            let elapseYearMonthTblList: Array<service.ElapseYearMonthTblCommand> = [];
            let elapseYear: Array<service.GrantElapseYearMonthCommand> = [];
            _.forEach(self.items(), function(item, index) {
                let elapseYearMonth: service.ElapseYearMonthCommand = null;
                if(item.years() && item.months())
                    elapseYearMonthTblList.push({
                        grantCnt: index + 1,
                        elapseYearMonth: new service.ElapseYearMonthCommand(item.years(), item.months())
                    });
                if(item.grantedDays())
                    elapseYear.push({
                        elapseNo: index + 1,
                        grantedDays: item.grantedDays(),
                    });
            });
             
            
            // if(elapseData.length > 0) {
            //     var evens = _.remove(elapseData, function(item) {
            //         return isNullOrEmpty(item.months) && isNullOrEmpty(item.years) && isNullOrEmpty(item.grantedDays);
            //     });
            // }
            
            // _.forEach(elapseData, function(item) {
            //     if(isNullOrEmpty(item.grantedDays)  && (!isNullOrEmpty(item.months)|| !isNullOrEmpty(item.months))) {
            //         nts.uk.ui.dialog.alertError({ messageId: "Msg_101" });
            //         checkErr = true;
            //         return;
            //     }
            // });
            
            // 「経過年数に対する付与日数」は1件以上登録すること
            // if(elapseData.length <= 0) {
            //     nts.uk.ui.dialog.alertError({ messageId: "Msg_144" });
            //     return;
            // }
            
            let grantDateTblCommand : service.GrantDateTblCommand = {
                specialHolidayCode: self.sphdCode,
                grantDateCode: self.grantDateCode(),
                grantDateName: self.grantDateName(),
                elapseYear: elapseYear,
                isSpecified: self.isSpecified(),
                grantedDays: self.gDateGrantedDays(),
                elapseYearMonthTblList: elapseYearMonthTblList,
                fixedAssign: self.fixedAssign(),
                year: self.cycleYear(),
                month: self.cycleMonth()
            };
            
            // if(self.daysReq() && dataItem.gDateGrantedDays === "") {
            //     $("#granted-days-number").ntsError("set", "固定付与日数を入力してください", "MsgB_1");
            //     return;
            // }
            
            if(!checkErr) {
                if(!self.editMode()) {
                    nts.uk.ui.block.invisible();
                    service.addGrantDate(grantDateTblCommand).done(function(errors){
                        if (errors && errors.length > 0) {
                            self.addListError(errors);    
                        } else {
                            $.when(self.getData()).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                                    self.selectedCode(grantDateTblCommand.grantDateCode);
                                    self.selectedCode.valueHasMutated();
                                });
                            });
                        }
                    }).fail(function(error){
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    nts.uk.ui.block.invisible();
                    service.updateGrantDate(grantDateTblCommand).done(function(errors){
                        if (errors && errors.length > 0) {
                            self.addListError(errors);    
                        } else {
                            $.when(self.getData()).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                                    self.selectedCode(grantDateTblCommand.grantDateCode);
                                    self.selectedCode.valueHasMutated();
                                });
                            });
                        }
                    }).fail(function(error){
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            }
        }
        
        prescribedEnable() {
            let self = this;
            return !(self.editMode() == true && self.isSpecified() == true);
        }
        
        //  new mode 
        newMode() {
            let self = this;
            
            self.codeEnable(true);
            self.newModeEnable(false);
            self.editMode(false);
            self.selectedCode("");
            self.grantDateCode("");
            self.grantDateName("");
            if (self.lstGrantDate().length) {
                self.isSpecified(false);
            } else {
                self.isSpecified(true);
            }
            self.isSpecifiedEnable(true);
            self.fixedAssign(false);
            self.gDateGrantedDays(null);
            self.bindElapseYearDto([], []);
            
            $("#inpCode").focus();
            
            nts.uk.ui.errors.clearAll();  
        }
        
        /** remove item from list **/
        remove() {
            let self = this;
            
            
            let count = 0;
            for (let i = 0; i <= self.lstGrantDate().length; i++){
                if(self.lstGrantDate()[i].grantDateCode == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            
            if(self.isSpecified()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1219" });
                nts.uk.ui.block.clear();
            } else {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.deleteGrantDate(self.sphdCode, self.grantDateCode()).done(function() {
                        self.getData().done(function(){
                            self.isDelete(true);
                            // if number of item from list after delete == 0 
                            if(self.lstGrantDate().length==0){
                                self.newMode();
                                return;
                            }
                            // delete the last item
                            if(count == ((self.lstGrantDate().length))){
                                self.selectedCode(self.lstGrantDate()[count-1].grantDateCode);
                                return;
                            }
                            // delete the first item
                            if(count == 0 ){
                                self.selectedCode(self.lstGrantDate()[0].grantDateCode);
                                return;
                            }
                            // delete item at mediate list 
                            else if(count > 0 && count < self.lstGrantDate().length){
                                self.selectedCode(self.lstGrantDate()[count].grantDateCode);    
                                return;
                            }
                        });
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                            if(self.editMode()) {
                                $("#inpPattern").focus();
                            } else {
                                $("#inpCode").focus();
                            }
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();      
                    });
                })
            }
        } 
        
        /** close dialog **/
        closeDialog(){
            nts.uk.ui.windows.close();
        }
        
        /** set errors **/
        addListError(errorsRequest: Array<string>) {
            var errors = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });
            
            nts.uk.ui.dialog.bundledErrors({ errors: errors});
        }
    }
    
    class GrantDateItem {
        grantDateCode: string;
        grantDateName: string;
        
        constructor(grantDateCode: string, grantDateName: string) {
            this.grantDateCode = grantDateCode;
            this.grantDateName = grantDateName;       
        }
    }

    export class GrantDateTblDto { 
        grantDateCode: string;
        grantDateName: string;
        elapseYear: Array<GrantElapseYearMonthDto>;
        isSpecified: boolean;
        grantedDays: number;
        elapseYearDto: ElapseYearDto;
        grantCycleAfterTblDto: GrantCycleAfterTblDto;
 
        constructor(grantDateCode: string, grantDateName: string, elapseYear: Array<any>, isSpecified: boolean, grantedDays: number, elapseYearDto: ElapseYearDto, grantCycleAfterTblDto: GrantCycleAfterTblDto) {
            this.grantDateCode = grantDateCode;
            this.grantDateName = grantDateName;
            this.elapseYear = elapseYear;
            this.isSpecified = isSpecified;
            this.grantedDays = grantedDays;
            this.elapseYearDto = elapseYearDto;
            this.grantCycleAfterTblDto = grantCycleAfterTblDto;
        }
    }
    export class ElapseYearDto {
        elapseYearMonthTblList: Array<ElapseYearMonthTblDto>;
        fixedAssign: boolean;
        grantCycleAfterTbl: GrantCycleAfterTblDto;

        constructor(elapseYearMonthTblList : Array<any>, fixedAssign: boolean, grantCycleAfterTbl:  GrantCycleAfterTblDto){
            this.elapseYearMonthTblList = elapseYearMonthTblList;
            this.fixedAssign = fixedAssign;
            this.grantCycleAfterTbl = grantCycleAfterTbl;
        }
    }

    export class GrantElapseYearMonthDto {
        elapseNo: number;
        grantedDays: number;
        constructor(elapseNo: number, grantedDays: number){
            this.elapseNo = elapseNo;
            this.grantedDays = grantedDays;
        }
    }
    export class ElapseYearMonthTblDto{
        grantCnt: number;
        year: number;
        month: number;    
        constructor(grantCnt: number, year: number, month: number){
            this.grantCnt = grantCnt;
            this.year = year;
            this.month = month;
        }
    }
    export interface GrantCycleAfterTblDto{
        year: number;
        month: number;
    }

    export class Item {
        elapseNo: KnockoutObservable<number>;
        months: KnockoutObservable<number>;
        years: KnockoutObservable<number>;
        grantedDays: KnockoutObservable<number>;

        constructor(elapseNo: KnockoutObservable<number>, 
                    years: KnockoutObservable<number>, 
                    months: KnockoutObservable<number>,
                    grantedDays: KnockoutObservable<number>) {
            this.elapseNo = elapseNo;
            this.years = years;
            this.months = months;
            this.grantedDays = grantedDays;
        }
    }
}