module nts.uk.at.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import aService = nts.uk.at.view.cmm018.a.service;
    export module viewmodel {
        export class ScreenModel {
            cssRangerYM = ko.observable({});
            cssRangerYM1 = ko.observable({});
            cssRangerYM2 = ko.observable({});
            yearMonthPicked: KnockoutObservable<number> = ko.observable(Number(moment(new Date()).format('YYYY01')));
            yearMonthPicked1: KnockoutObservable<number> = ko.observable(Number(moment(new Date()).format('YYYY01')));
            yearMonthPicked2: KnockoutObservable<number> = ko.observable(Number(moment(new Date()).format('YYYY01')));
            currentWorkingDayAtr: number = null;
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            isShowDatepicker: boolean = false;
            constructor() {
                var self = this;
                self.yearMonthPicked.subscribe(value => {
                    if(!nts.uk.util.isNullOrEmpty(value)){
//                        nts.uk.ui.block.invisible();
                    }
                });
                }
                            
            /*
                setting date Wokring Day Atr event
            */
            setWorkingDayAtr(date){
                var self = this;
                if(self.currentWorkingDayAtr!=null) {
                    let i = $("#sidebar").ntsSideBar("getCurrent");
                    let dateData;
                    switch(i) {
                        case 1:
                            // select tab Work Place
                            dateData = self.calendarPanel1.optionDates;
                            break;
                        case 2:
                            // select tab Class
                            dateData = self.calendarPanel2.optionDates;
                            break;
                        default:
                            // select tab Company
                            dateData = self.calendarPanel.optionDates;
                            break;
                    }   
                    
                    let existItem = _.find(dateData(), item => item.start == date);   
                    dateData.valueHasMutated();
                }
            }
            
            /*
                register button handler
            */
            submitCalendar(value){
                var self = this;
                if(value==1){
                    if(nts.uk.util.isNullOrUndefined(self.currentCalendarWorkPlace().key())){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_339" }).then(()=>{nts.uk.ui.block.clear();});      
                    } else {
                        $(".yearMonthPicker").trigger("validate");
                        if (!nts.uk.ui.errors.hasError()) {
                            let dayOfMonth: number = moment(self.yearMonthPicked1(), "YYYYMM").daysInMonth(); 
                            let daySetnumber = self.calendarPanel1.optionDates().length;
                            if(daySetnumber<dayOfMonth){
                                // when at least 1 day is not select
                                // confirm auto fill data 
                                nts.uk.ui.dialog.confirm({ messageId: 'Msg_140' }).ifYes(function(){ 
                                    self.processData(value, true);    
                                }).ifNo(function(){
                                    // do nothing    
                                });
                            } else {
                                self.processData(value, false);
                            }
                        }        
                    }    
                } else if(value==2){
                    if(nts.uk.util.isNullOrUndefined(self.currentCalendarClass().key())){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_339" }).then(()=>{nts.uk.ui.block.clear();});   
                    } else {
                        $(".yearMonthPicker").trigger("validate");
                        if (!nts.uk.ui.errors.hasError()) {
                            let dayOfMonth: number = moment(self.yearMonthPicked2(), "YYYYMM").daysInMonth(); 
                            let daySetnumber = self.calendarPanel2.optionDates().length;
                            if(daySetnumber<dayOfMonth){
                                // when at least 1 day is not select
                                // confirm auto fill data 
                                nts.uk.ui.dialog.confirm({ messageId: 'Msg_140' }).ifYes(function(){ 
                                    self.processData(value, true);    
                                }).ifNo(function(){
                                    // do nothing    
                                });
                            } else {
                                self.processData(value, false);
                            }
                        }     
                    } 
                } else {
                    $(".yearMonthPicker").trigger("validate");
                    if (!nts.uk.ui.errors.hasError()) {
                        let dayOfMonth: number = moment(self.yearMonthPicked(), "YYYYMM").daysInMonth(); 
                        let daySetnumber = self.calendarPanel1.optionDates().length;
                        if(daySetnumber<dayOfMonth){
                            // when at least 1 day is not select
                            // confirm auto fill data 
                            nts.uk.ui.dialog.confirm({ messageId: 'Msg_140' }).ifYes(function(){ 
                                self.processData(value, true);    
                            }).ifNo(function(){
                                // do nothing    
                            });
                        } else {
                            self.processData(value, false);
                        }
                    }
                }
            }
            
            /*
                process Data when insert/update
            */
            processData(value, autoFill){
//                nts.uk.ui.block.invisible();
                var self = this;
                if(self.isUpdate()){
                    // update case
                    switch(value) {
                        case 1:
                            // select tab Work Place
                            break;
                        case 2:
                            // select tab Class
                            break;
                        default:
                            // select tab Company
                            break;
                    }    
                } else {
                    // insert case
                }        
            }
            
            /*
                remove button handler
            */
            removeCalendar(value){
                var self = this;
                $(".yearMonthPicker").trigger("validate");
                if (!nts.uk.ui.errors.hasError()) {
//                    nts.uk.ui.block.invisible();
                    // confirm delete
                    nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function(){ 
                        switch(value) {
                            case 1:
                                // select tab Work Place
                                self.deleteCalendarWorkPlace({
                                    yearMonth: self.yearMonthPicked1().toString(),
                                    workPlaceId: self.currentCalendarWorkPlace().key()
                                }).done(()=>{ nts.uk.ui.block.clear(); })
                                .fail((res) => {
                                    nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                                });;
                                break;
                            case 2:
                                // select tab Class
                                self.deleteCalendarClass({
                                    yearMonth: self.yearMonthPicked2().toString(),
                                    classId: self.currentCalendarClass().key()
                                }).done(()=>{ nts.uk.ui.block.clear(); })
                                .fail((res) => {
                                    nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                                });;
                                break;
                            default:
                                // select tab Company
                                self.deleteCalendarCompany({yearMonth: self.yearMonthPicked().toString()})
                                .done(()=>{ nts.uk.ui.block.clear(); })
                                .fail((res) => {
                                    nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                                });;
                                break;
                        }  
                    }).ifNo(function(){ 
                        nts.uk.ui.block.clear();           
                    });
                }
            }
            
            getCalendarCompanySet(): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                aService.getCalendarCompanySet(self.yearMonthPicked().toString())
                .done(data => {
                    if(!nts.uk.util.isNullOrEmpty(data)) {
                        a = {};
                        a[Math.floor(self.yearMonthPicked()/100)] = data;
                        self.cssRangerYM(a);
                        $("#yearMonthPicker1").datepicker("hide");
                        $("#yearMonthPicker1").datepicker("show");
                    }
                    dfd.resolve(); 
                }).fail(res => {
                    dfd.reject(res);       
                });        
                return dfd.promise(); 
            }
            
            getCalendarWorkplaceSet(value): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                aService.getCalendarWorkplaceSet(value,self.yearMonthPicked1().toString())
                .done(data => {
                    if(!nts.uk.util.isNullOrEmpty(data)) {
                        a = {};
                        a[Math.floor(self.yearMonthPicked()/100)] = data;
                        self.cssRangerYM1(a);
                        $("#yearMonthPicker2").datepicker("hide");
                        $("#yearMonthPicker2").datepicker("show");
                    }
                    dfd.resolve(); 
                }).fail(res => {
                    dfd.reject(res);        
                });       
                return dfd.promise();  
            }
            
            getCalendarClassSet(value): JQueryPromise<any>{
                var self = this;
                var dfd = $.Deferred();
                aService.getCalendarClassSet(value,self.yearMonthPicked2().toString())
                .done(data => {
                    if(!nts.uk.util.isNullOrEmpty(data)) {
                        a = {};
                        a[Math.floor(self.yearMonthPicked()/100)] = data;
                        self.cssRangerYM2(a);
                        $("#yearMonthPicker3").datepicker("hide");
                        $("#yearMonthPicker3").datepicker("show");
                    }
                    dfd.resolve(); 
                }).fail(res => {
                    dfd.reject(res);              
                });    
                return dfd.promise();     
            }
            
            /*
                insert Calendar Company
            */
            insertCalendarCompany(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.insertCalendarCompany(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(self.getCalendarCompanySet(), self.getAllCalendarCompany())
                        .done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });   
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                insert Calendar Work Place
            */
            insertCalendarWorkPlace(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.insertCalendarWorkPlace(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(
                            self.getCalendarWorkplaceSet(self.currentCalendarWorkPlace().key()),
                            self.getCalenderWorkPlaceByCode(self.currentCalendarWorkPlace().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });  
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                insert Calendar Class
            */
            insertCalendarClass(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.insertCalendarClass(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(
                            self.getCalendarClassSet(self.currentCalendarClass().key()),
                            self.getCalendarClassById(self.currentCalendarClass().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });   
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                update Calendar Company
            */
            updateCalendarCompany(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.updateCalendarCompany(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(self.getCalendarCompanySet(), self.getAllCalendarCompany())
                        .done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });    
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                update Calendar Work Place
            */
            updateCalendarWorkPlace(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.updateCalendarWorkPlace(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(
                            self.getCalendarWorkplaceSet(self.currentCalendarWorkPlace().key()),
                            self.getCalenderWorkPlaceByCode(self.currentCalendarWorkPlace().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });  
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                update Calendar Class
            */
            updateCalendarClass(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.updateCalendarClass(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        $.when(
                            self.getCalendarClassSet(self.currentCalendarClass().key()),
                            self.getCalendarClassById(self.currentCalendarClass().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });     
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                delete Calendar Company
            */
            deleteCalendarCompany(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.deleteCalendarCompany(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        $.when(self.getCalendarCompanySet(), self.getAllCalendarCompany())
                        .done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });  
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                delete Calendar Work Place
            */
            deleteCalendarWorkPlace(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.deleteCalendarWorkPlace(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        $.when(
                            self.getCalendarWorkplaceSet(self.currentCalendarWorkPlace().key()),
                            self.getCalenderWorkPlaceByCode(self.currentCalendarWorkPlace().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });     
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /*
                delete Calendar Class
            */
            deleteCalendarClass(value): JQueryPromise<any>{
                var self = this; 
                var dfd = $.Deferred();
                aService.deleteCalendarClass(value)
                    .done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                        $.when(
                            self.getCalendarClassSet(self.currentCalendarClass().key()),
                            self.getCalendarClassById(self.currentCalendarClass().key())
                        ).done(()=>{ dfd.resolve(); })
                        .fail((res) => { dfd.reject(res); });   
                    }).fail((res) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            
            /*
                change Style when change selected Working Day
            */
            changeWorkingDayAtr(value){
                var self = this;
                $('.labelSqr').css("border","2px solid #B1B1B1");
                if(value!=null) {
                    self.currentWorkingDayAtr = value-1;
                    $('.labelSqr'+value).css("border","2px dashed #008000");
                } else {
                    self.currentWorkingDayAtr = value;   
                }
            }
            
            /*
                open Dialog D, set param = {yearMonth} 
            */
            openDialogC() {
                nts.uk.ui.block.invisible()
                var self = this;
                nts.uk.ui.windows.setShared('KSM004_C_PARAM', 
                {
                    yearMonth: self.yearMonthPicked().toString().substring(0,4)
                });
                nts.uk.ui.windows.sub.modal("/view/ksm/004/c/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    self.isShowDatepicker = false;
                    $.when(self.getCalendarCompanySet(), self.getAllCalendarCompany())
                    .done(()=>{ 
                        self.isShowDatepicker = true;
                        nts.uk.ui.block.clear(); 
                    })
                    .fail((res) => {
                        nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                    });      
                });  
            }
            
            /*
                open Dialog D, set param = {classification,yearMonth,workPlaceId,classCD}
            */
            openDialogD(value) {
                nts.uk.ui.block.invisible()
                var self = this;
                if(value==1){
                    if(nts.uk.util.isNullOrUndefined(self.currentCalendarWorkPlace().key())){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_339" }).then(()=>{nts.uk.ui.block.clear();});      
                    } else {
                        nts.uk.ui.windows.setShared('KSM004_D_PARAM', 
                        {
                            classification: value,
                            yearMonth: self.yearMonthPicked1(),
                            workPlaceId: self.currentCalendarWorkPlace().key()
                        }); 
                        nts.uk.ui.windows.sub.modal("/view/ksm/004/d/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                            self.isShowDatepicker = false;
                            $.when(
                                self.getCalendarWorkplaceSet(self.currentCalendarWorkPlace().key()),
                                self.getCalenderWorkPlaceByCode(self.currentCalendarWorkPlace().key())
                            ).done(()=>{ 
                                self.isShowDatepicker = true; 
                                nts.uk.ui.block.clear(); 
                            })
                            .fail((res) => {
                                nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                            });        
                        });     
                    }    
                } else if(value==2){
                    if(nts.uk.util.isNullOrUndefined(self.currentCalendarClass().key())){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_339" }).then(()=>{nts.uk.ui.block.clear();});  
                    } else {
                        nts.uk.ui.windows.setShared('KSM004_D_PARAM', 
                        {
                            classification: value,
                            yearMonth: self.yearMonthPicked2(),
                            classCD: self.currentCalendarClass().key()
                        }); 
                        nts.uk.ui.windows.sub.modal("/view/ksm/004/d/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                            self.isShowDatepicker = false;
                            $.when(
                                self.getCalendarClassSet(self.currentCalendarClass().key()),
                                self.getCalendarClassById(self.currentCalendarClass().key())
                            ).done(()=>{ 
                                self.isShowDatepicker = true; 
                                nts.uk.ui.block.clear(); 
                            })
                            .fail((res) => {
                                nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                            });        
                        });       
                    } 
                } else {
                    nts.uk.ui.windows.setShared('KSM004_D_PARAM', 
                    {
                        classification: value,
                        yearMonth: self.yearMonthPicked()
                    }); 
                    nts.uk.ui.windows.sub.modal("/view/ksm/004/d/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                        self.isShowDatepicker = false;
                        $.when(self.getCalendarCompanySet(), self.getAllCalendarCompany())
                        .done(()=>{ 
                            self.isShowDatepicker = true;
                            nts.uk.ui.block.clear(); 
                        })
                        .fail((res) => {
                            nts.uk.ui.dialog.alertError(res.message).then(()=>{nts.uk.ui.block.clear();});
                        });        
                    }); 
                }
            }
            
            openI(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/i/index.xhtml");
            }
            openJ(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/j/index.xhtml");
                
            }
        }
        interface IDataJ{
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**履歴ID*/
            workplaceId: string;
            /**社員ID*/
            employeeId: string;
            /**check 申請承認の種類区分*/
            check: number;
            /**「履歴を削除する」を選択する か、「履歴を修正する」を選択する か。*/
            editOrDelete: number;
            /**開始日 previous*/
            sDatePrevious: string;
            /** list history and approvalId */
            lstUpdate: Array<UpdateHistoryDto>;
        }
        class UpdateHistoryDto{
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
        }
    }
}