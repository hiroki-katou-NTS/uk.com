module nts.uk.at.view.kmf001.m {
    export module viewmodel {

        import invisible = nts.uk.ui.block.invisible;
        import alertError = nts.uk.ui.dialog.alertError;
        import clear = nts.uk.ui.block.clear;

        export class ScreenModel {

            holidaySetting: KnockoutObservable<HolidaySetting> = ko.observable( new HolidaySetting() );
            constructor() {
                let self = this;
            }

            public startPage(): JQueryPromise<any> {
                let self = this;

                let dfd = $.Deferred<any>();
                invisible();

                service.startUp().done( function() {
                    dfd.resolve();
                } ).fail( function( res ) {
                    alertError( res.message );
                } ).always(() => {
                    clear();
                } );
                return dfd.promise();
            }

            private save(): void {
                let self = this;
                
                nts.uk.ui.errors.clearAll();
                let dfd = $.Deferred();
                if(self.holidaySetting().selectedCategory() == 0 && self.holidaySetting().selectedClassification() == 0){
                    $("#numberEditor-YMD").trigger("validate");
                    $("#datePicker-YMD").trigger("validate");
                    $("#radio-YMD").trigger("validate");
                }
                else if(self.holidaySetting().selectedCategory() == 0 && self.holidaySetting().selectedClassification() == 1){
                    $("#numberEditor1-MD").trigger("validate");
                    $("#numberEditor2-MD").trigger("validate");
                    $("#MonthDays-MD").trigger("validate");
                    $("#radio-MD").trigger("validate");
                }
                else if(self.holidaySetting().selectedCategory() == 1){
                    $("#numberEditor-1week").trigger("validate");
                }
                if ( nts.uk.ui.errors.hasError() ) {
                    return;
                }
                invisible();
                let command = {
                    //M6_4
                    nonStatutory: self.holidaySetting().nonStatutory(),
                    //M4_2
                    monthDay: self.holidaySetting().monthDay(),
                    //M6_2 || M_5 || M_8
                    holidayValue: self.holidaySetting().holidayValue(),
                    //M7_2
                    addHolidayValue: self.holidaySetting().addHolidayValue(),
                    //M5_2
                    standardDate: self.holidaySetting().standardDate()

                };
                service.save( command ).done( function() {

                } ).fail( function( res ) {
                    alertError( res.message );
                } ).always(() => {
                    clear();
                } );
            }

        }

        export class HolidaySetting {

            //M2_2          
            selectedCategory: KnockoutObservable<number> = ko.observable( 0 );
            //M3_2            
            selectedClassification: KnockoutObservable<number> = ko.observable( 0 );
            //M4_2          
            monthDay: KnockoutObservable<number> = ko.observable( 101 );
            //M5_2 
            standardDate: KnockoutObservable<any> = ko.observable( '' );
            //M6_2           
            holidayValue: KnockoutObservable<number> = ko.observable( 0 );
            holidayOption = new nts.uk.ui.option.NumberEditorOption( {
                unitID: "DAYS",
                textalign: "right"
            } );
            //M6_4
            nonStatutory: KnockoutObservable<boolean> = ko.observable( true );
            //M7_2
            addHolidayValue: KnockoutObservable<number> = ko.observable( 0 );
            addHolidayOption = new nts.uk.ui.option.NumberEditorOption( {
                unitID: "DAYS",
                textalign: "right"
            } );
            is4weeksAndMonthDay: KnockoutObservable<boolean> = ko.observable( false );
            is4weeksAndMonthDayYear: KnockoutObservable<boolean> = ko.observable( true );
            is1week: KnockoutObservable<boolean> = ko.observable( false );
            constructor() {
                let self = this;

                self.selectedCategory.subscribe(( value ) => {
                    nts.uk.ui.errors.clearAll();
                    if ( value == 0 && self.selectedClassification() == 0 ) {
                        self.is4weeksAndMonthDayYear( true );
                        self.is4weeksAndMonthDay( false );
                        self.is1week( false );
                    } else if ( value == 0 && self.selectedClassification() == 1 ) {
                        self.is4weeksAndMonthDayYear( false );
                        self.is4weeksAndMonthDay( true );
                        self.is1week( false );
                    }
                    else {
                        self.is4weeksAndMonthDayYear( false );
                        self.is4weeksAndMonthDay( false );
                        self.is1week( true );
                    }
                } );

                self.selectedClassification.subscribe(( value ) => {
                    nts.uk.ui.errors.clearAll();
                    if ( value == 0 ) {
                        self.is4weeksAndMonthDayYear( true );
                        self.is4weeksAndMonthDay( false );
                    } else {
                        self.is4weeksAndMonthDayYear( false );
                        self.is4weeksAndMonthDay( true );
                    }
                } );
            }
        }
    }
}