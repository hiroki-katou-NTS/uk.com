module nts.uk.at.view.kmk007.b.viewmodel {
    export class ScreenModel {
        frameName: KnockoutObservable<string>;
        frameElimination: KnockoutObservableArray<any>;
        frameEliminationSelectedCode: any;
        frameId: any;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<any>;
        enableB41: KnockoutObservable<boolean> = ko.observable(false);
        timeMngAtr : any;
        itemListB41 : KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            
            self.frameName = ko.observable("");
            
            self.frameElimination = ko.observableArray([
                { code: '0', frameValue: nts.uk.resource.getText("KMK007_55") },
                { code: '1', frameValue: nts.uk.resource.getText("KMK007_56") }
            ]);
            
            self.frameEliminationSelectedCode = ko.observable(0);
            
            self.timeMngAtr =  ko.observable(0);
             self.itemListB41 = ko.observableArray([
                { code: '1', frameValue: nts.uk.resource.getText("KMF004_167") },
                { code: '0', frameValue: nts.uk.resource.getText("KMF004_168") }
            ]);
            
            self.frameId = ko.observable(nts.uk.ui.windows.getShared("KMK007_ITEM_ID"));
            
            self.columns = ko.observableArray([
                { headerText: "", key: 'frameNo', hidden: true },
                { headerText: nts.uk.resource.getText("KMK007_49"), key: 'name', width: 150, formatter: _.escape },
                { headerText: nts.uk.resource.getText("KMK007_50"), key: 'icon', width: 60 }
            ]);
            
            self.items = ko.observableArray([]);
            self.selectedCode = ko.observableArray();
            
            self.selectedCode.subscribe(function(value) {
                // clear all error
                nts.uk.ui.errors.clearAll();
                
                if(self.frameId() == Cls_Of_Duty.SpecialHolidayFrame){
                    service.findHolidayFrameByCode(Number(value)).done(function(data) {
                        self.frameName(data.specialHdFrameName);
                        self.frameEliminationSelectedCode(data.deprecateSpecialHd);
                        self.timeMngAtr(data.timeMngAtr);
                    }).fail(function(res) {
                              
                    });
                } else if(self.frameId() == Cls_Of_Duty.AbsenceFrame){
                    service.findAbsenceFrameByCode(Number(value)).done(function(data) {
                        self.frameName(data.absenceFrameName);
                        self.frameEliminationSelectedCode(data.deprecateAbsence);
                    }).fail(function(res) {
                              
                    });
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            
            var dfd = $.Deferred();
            
            $.when(self.getData()).done(function() {
                
                if (self.items().length > 0) {
                    self.selectedCode(self.items()[0].frameNo);
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         *  Get and display data
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            self.items([]);
            
            if(self.frameId() == Cls_Of_Duty.SpecialHolidayFrame){
                self.enableB41(true);
                service.getAllSpecialHolidayFrame().done(function(data) {
                    let sortData1 = _.sortBy(data, [function(o) { return o.specialHdFrameNo; }]);
                    _.forEach(sortData1, function(item) {
                        self.items.push(new ItemModel(item.specialHdFrameNo, item.specialHdFrameName, item.deprecateSpecialHd , item.timeMngAtr));
                    });
                    
                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);    
                });
            } else if(self.frameId() == Cls_Of_Duty.AbsenceFrame){
                service.getAllAbsenceFrame().done(function(data) {
                    let sortData2 = _.sortBy(data, [function(o) { return o.absenceFrameNo; }]);
                    _.forEach(sortData2, function(item) {
                        self.items.push(new ItemModel(item.absenceFrameNo, item.absenceFrameName, item.deprecateAbsence , item.timeMngAtr));
                    });
                    
                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);    
                });
            }
            
            return dfd.promise();
        }
        
        /**
         *  Update db
         */
        register() {
            var self = this;
            nts.uk.ui.block.invisible();
            if (nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.clear();
                return;
            }
            if(self.frameId() == Cls_Of_Duty.SpecialHolidayFrame){
                var holidayFrame = new HolidayFrameDto(self.selectedCode(), self.frameName(), self.frameEliminationSelectedCode(), self.timeMngAtr());
                
                service.updateSpecialHolidayFrame(holidayFrame).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getData();
                }).fail(function(res) {
                          
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            } else if(self.frameId() == Cls_Of_Duty.AbsenceFrame){
                var absenceFrame = new AbsenceFrameDto(self.selectedCode(), self.frameName(), self.frameEliminationSelectedCode());
                
                service.updateAbsenceFrame(absenceFrame).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getData();
                }).fail(function(res) {
                          
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }
          
        /**
         *  Close dialog
         */
        close() {
            nts.uk.ui.windows.close();
        }
    }
    
    class ItemModel {
        frameNo: number;
        name: string;
        abolition: number;
        icon: string;
        timeMngAtr: number;
        constructor(frameNo: number, name: string, abolition: number ,timeMngAtr: number) {
            this.frameNo = frameNo;
            this.name = name;
            this.abolition = abolition;
             if (abolition == 1) {
                this.icon = "";
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
            this.timeMngAtr = timeMngAtr;
        }
    }
    
    enum Cls_Of_Duty {
        SpecialHolidayFrame = 4,
        AbsenceFrame = 5
    }
    
    class HolidayFrameDto {
        specialHdFrameNo: number;
        specialHdFrameName: string;
        deprecateSpecialHd: number;
        timeMngAtr :number;
        constructor(specialHdFrameNo: number, specialHdFrameName: string, deprecateSpecialHd: number , timeMngAtr: number) {
            this.specialHdFrameNo = specialHdFrameNo;
            this.specialHdFrameName = specialHdFrameName;
            this.deprecateSpecialHd = deprecateSpecialHd;
            this.timeMngAtr = timeMngAtr;
        }
    }
    
    class AbsenceFrameDto {
        absenceFrameNo: number;
        absenceFrameName: string;
        deprecateAbsence: number;
        constructor(absenceFrameNo: number, absenceFrameName: string, deprecateAbsence: number) {
            this.absenceFrameNo = absenceFrameNo;
            this.absenceFrameName = absenceFrameName;
            this.deprecateAbsence = deprecateAbsence;
        }
    }
}