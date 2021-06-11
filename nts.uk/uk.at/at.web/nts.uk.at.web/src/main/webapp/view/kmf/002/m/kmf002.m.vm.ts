module nts.uk.at.view.kmf002.m {
    export module viewmodel {

        import invisible = nts.uk.ui.block.invisible;
        import alertError = nts.uk.ui.dialog.alertError;
        import clear = nts.uk.ui.block.clear;
        import getText = nts.uk.resource.getText;
        import getMessage = nts.uk.resource.getMessage;

        export class ScreenModel {

            holidaySetting: KnockoutObservable<HolidaySetting>;
            description:  KnockoutObservable<string> = ko.observable("");
            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                invisible();
                $.when(self.getM8_3(), self.startUp()).done(function() {
                    clear();
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            public startUp() : JQueryPromise<any> {
                 let self = this;
                let dfd = $.Deferred<any>();
                service.startUp().done(function(data) {
                    let fakeData = { startDay: '1', description: '2' }
                    self.holidaySetting = ko.observable(new HolidaySetting(data));
                    dfd.resolve();
                }).fail(function(res) {
                    alertError(res.message);
                });
                return dfd.promise();
            }
            //
            public getM8_3(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                let command = {
                    programID: 'KMK013',
                    screenID: 'A',
                    queryString: null
                }
                service.getM8_3(command).done((data) => {
                    console.log(data);
                    if (data != null) {
                        self.description(nts.uk.text.format(getText(('KMF002_112')), data));
                    }
                    else {
                         //self.startingDay(getText(('KMF001_292'),[data.hdDay]));
                        self.description(getText('KMF001_337'));
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            private save(): void {
                let self = this;

                nts.uk.ui.errors.clearAll();
                let dfd = $.Deferred();
                if (self.holidaySetting().selectedCategory() == 1 && self.holidaySetting().selectedClassification() == 0) {
                    $("#numberEditor-YMD").trigger("validate");
                    $("#datePicker-YMD").trigger("validate");
                    $("#radio-YMD").trigger("validate");
                }
                else if (self.holidaySetting().selectedCategory() == 1 && self.holidaySetting().selectedClassification() == 1) {
                    $("#numberEditor1-MD").trigger("validate");
                    $("#numberEditor2-MD").trigger("validate");
                    $("#MonthDays-MD").trigger("validate");
                    $("#radio-MD").trigger("validate");
                }
                else if (self.holidaySetting().selectedCategory() == 0) {
                    $("#numberEditor-1week").trigger("validate");
                }
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                invisible();
                //IF　M2_2に「１週」を選択している                                 
                // AND　休日の設定情報.起算曜日がEmpty                              
                //→　RETURN　エラーメッセージ（）を表示する Msg_2048
                if (self.holidaySetting().selectedCategory() == 0
                    && self.holidaySetting().startingDay() == getText('KMF002_115')) {
                    alertError({ messageId: "Msg_2048" });
                    nts.uk.ui.block.clear();
                    return;
                }

                if (self.holidaySetting().is1week() == true) {
                    let command = {
                        nonStatutory: self.holidaySetting().nonStatutory() == true ? 1 : 0,
                        monthDay: null,
                        holidayValue: self.holidaySetting().holidayValue(),
                        addHolidayValue: null,
                        standardDate: null,
                        selectedClassification: null,
                        holidayCheckUnit: 0
                    }
                } else {
                    if (self.holidaySetting().is4weeksAndMonthDayYear() == true) {
                        let command = {
                            //M6_4 
                            nonStatutory: self.holidaySetting().nonStatutory() == true ? 1 : 0,
                            //M4_2
                            monthDay: null,
                            //M6_2 || M_5 || M_8    
                            holidayValue: self.holidaySetting().holidayValue(),
                            //M7_2        
                            addHolidayValue: null,
                            //M5_2
                            standardDate: self.holidaySetting().standardDate(),
                            selectedClassification: 0,
                            holidayCheckUnit: 1
                        }

                    } else {
                        let command = {
                            nonStatutory: self.holidaySetting().nonStatutory() == true ? 1 : 0,
                            monthDay: self.holidaySetting().monthDay(),
                            holidayValue: self.holidaySetting().holidayValue(),
                            addHolidayValue: self.holidaySetting().addHolidayValue(),
                            standardDate: null,
                            selectedClassification: 1,
                            holidayCheckUnit: 1
                        };
                        
                    }
                }

                //                    //M6_4 
                //                    nonStatutory: self.holidaySetting().nonStatutory() == true ? 1 : 0,
                //                    //M4_2
                //                    monthDay: self.holidaySetting().monthDay(),
                //                    //M6_2 || M_5 || M_8
                //                    holidayValue: self.holidaySetting().holidayValue(),
                //                    //M7_2
                //                    addHolidayValue: self.holidaySetting().addHolidayValue(),
                //                    //M5_2
                //                    standardDate: self.holidaySetting().standardDate(),
                //                    
                //                    holidayCheckUnit : self.holidaySetting().selectedCategory()
                // 
                service.save(command).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    dfd.resolve();
                            }).fail( function( res ) {
                                alertError(res.message );
                    }).always(() => {
                  clear();
               });                              
            }

        }

        export class HolidaySetting {

            //M2_2          
            selectedCategory: KnockoutObservable<number> = ko.observable(1);
            //M3_2            
            selectedClassification: KnockoutObservable<number> = ko.observable(0);
            //M4_2          
            monthDay: KnockoutObservable<number> = ko.observable(101);
            //M5_2 
            standardDate: KnockoutObservable<any> = ko.observable('');
            //M6_2           
            holidayValue: KnockoutObservable<number> = ko.observable(0);
            holidayOption = new nts.uk.ui.option.NumberEditorOption({
                unitID: "DAYS",
                textalign: "right",
                grouplength: 2,
                decimallength: 1,
                placeholder: '',
                width: ''
            });
            //M6_4
            nonStatutory: KnockoutObservable<boolean> = ko.observable(true);
            //M7_2
            addHolidayValue: KnockoutObservable<number> = ko.observable(0);
            addHolidayOption = new nts.uk.ui.option.NumberEditorOption({
                unitID: "DAYS",
                textalign: "right",
                grouplength: 2,
                decimallength: 1,
                placeholder: '',
                width: ''
            });
            //M8_2
            startingDay: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText('KMF002_115'));
            //M8_3
//            description: KnockoutObservable<string> = ko.observable('');
            is4weeksAndMonthDay: KnockoutObservable<boolean> = ko.observable(false);
            is4weeksAndMonthDayYear: KnockoutObservable<boolean> = ko.observable(true);
            is1week: KnockoutObservable<boolean> = ko.observable(false);
            constructor(data: any) {
                let self = this;

                // holidayCheckUnit
                self.selectedCategory.subscribe((value) => {
                    nts.uk.ui.errors.clearAll();
                    if (value == 1 && self.selectedClassification() == 0) {
                        self.is4weeksAndMonthDayYear(true);
                        self.is4weeksAndMonthDay(false);
                        self.is1week(false);
                    } else if (value == 1 && self.selectedClassification() == 1) {
                        self.is4weeksAndMonthDayYear(false);
                        self.is4weeksAndMonthDay(true);
                        self.is1week(false);
                    }
                    else {
                        self.is4weeksAndMonthDayYear(false);
                        self.is4weeksAndMonthDay(false);
                        self.is1week(true);
                    }
                });
                if(data != null) {
                    self.selectedCategory(data.holidayCheckUnit);
                    
                    if (data.selectedClassification !== null)
                        self.selectedClassification(data.selectedClassification)
                    if (data.standardDate !== null)
                        self.standardDate(data.standardDate)
                    if (data.addHolidayValue !== null)
                        self.addHolidayValue(data.addHolidayValue)
                    if (data.monthDay !== null)
                        self.monthDay(data.monthDay)
                    if (data.hdDay !== null)
                        self.startingDay(getText(('KMF002_111'),[data.hdDay]));
                    // KMF001_292 {０}から７日間 : {０} = 起算曜日
                    // 休日の設定情報.起算曜日がEmpty場合：KMF002_115を表示する       
                    // self.startingDay( data.startDay == null ? getText( 'KMF002_115' ) : getText( 'KMF001_292', [data.startDay] ) );
                    //＃KMF001_293　｛0｝：　休日の設定情報.計算設定画面名
                    
                  //  self.description(getText('KMF001_293', [data.description]));
    
                    //// Tab 2
                    if (data.holidayValue !== null)
                        self.holidayValue(data.holidayValue);
                    if (data.nonStatutory !== null)
                        self.nonStatutory(data.nonStatutory == 1 ? true : false);
                    self.selectedCategory.valueHasMutated();
                 }
                

                //// Tab 1
                self.selectedClassification.subscribe((value) => {
                    nts.uk.ui.errors.clearAll();
                    if (value == 0) {
                        self.is4weeksAndMonthDayYear(true);
                        self.is4weeksAndMonthDay(false);
                    } else {
                        self.is4weeksAndMonthDayYear(false);
                        self.is4weeksAndMonthDay(true);
                    }
                });
            }
        }
    }
}