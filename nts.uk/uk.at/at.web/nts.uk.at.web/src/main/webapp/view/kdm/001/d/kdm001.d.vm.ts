module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string>      = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string>  = ko.observable('');
        employeeName: KnockoutObservable<string>  = ko.observable('');
        remainDays: KnockoutObservable<string>  = ko.observable('');
        lawAtr: KnockoutObservable<string>        = ko.observable('');
        pickup: KnockoutObservable<boolean>    = ko.observable(false);;
        pause: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean>      = ko.observable(false);
        dayOff: KnockoutObservable<string>        = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        subDayoffDate: KnockoutObservable<string>     = ko.observable('');
        holidayDate: KnockoutObservable<string>    = ko.observable('');
        requiredDays: KnockoutObservable<string> = ko.observable('');
        typeHoliday: KnockoutObservableArray<model.ItemModel>     = ko.observableArray(model.getTypeHoliday());
        itemListHoliday: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        occurredDays: KnockoutObservable<string>              = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeSubHoliday: KnockoutObservable<string>           = ko.observable('');
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);

        constructor() {
            let self = this;
            self.initScreen();
            
            self.expiredDate.subscribe(x =>{
//                if () {
//                    $('#M6_2').ntsError('set', { messageId: messageId});
//                    }
            });
        }

        initScreen(): void {
            let self = this;
            self.workCode("100");
            self.workplaceName("営業部");
            self.employeeCode("A0000001");
            self.employeeName("日通　太郎");
            self.remainDays("0.5日");
            self.checkedSplit.subscribe((v) => {
                if (v == true) {
                    self.isOptionSubHolidayEnable(true);
                } else {
                    self.isOptionSubHolidayEnable(false);
                }
            });
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }
        
        public submitForm() {
            let self = this;
            let data = {
                sID: self.employeeCode(),
                pickUp: self.pickup(),
                dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString(),
                occurredDays: self.occurredDays(),
                expiredDate: moment.utc(self.expiredDate(), 'YYYY/MM/DD').toISOString(),
                pause: self.pause(),
                subDayoffDate: self.subDayoffDate(),
                lawAtr: self.lawAtr(),
                checkedSplit: self.checkedSplit(),
                requiredDays: self.requiredDays(),
                remainDays: self.remainDays(),
                holidayDate: moment.utc(self.holidayDate(), 'YYYY/MM/DD').toISOString()
            };
            
            console.log(data);
            service.save(data).done(() => {

            }).fail(function(res: any) {

            });
        }
//        check(){
//            
//            return true;
//        }
 }