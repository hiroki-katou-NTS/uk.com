module nts.uk.at.view.kdm001.g.viewmodel {
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        date: KnockoutObservable<string> = ko.observable('');
        deadline: KnockoutObservable<string> = ko.observable('');
        daysOff: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeDayOff: KnockoutObservable<string> = ko.observable('');
        days: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeDay: KnockoutObservable<string> = ko.observable('');
        checkedExpired: KnockoutObservable<boolean> = ko.observable(false);


        payoutId: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        sID: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<string> = ko.observable('');
        dayOff: KnockoutObservable<string> = ko.observable('');
        expiredDate: KnockoutObservable<string> = ko.observable('');
        lawAtr: KnockoutObservable<number> = ko.observable('');
        occurredDays: KnockoutObservable<number> = ko.observable('');
        unUsedDays: KnockoutObservable<number> = ko.observable('');
        stateAtr: KnockoutObservable<number> = ko.observable('');
        disapearDate: KnockoutObservable<string> = ko.observable('');
        checkBox: KnockoutObservable<boolean> = ko.observable(false);



        //        listSelection: KnockoutObservableArray<ISelection> = ko.observableArray([]);
        //        selection: KnockoutObservable<Selection> = ko.observable(new Selection({ payoutID: '', sID: '' }));

        constructor() {
            var self = this;
            self.initScreen();
        }

        public initScreen(): void {
            var self = this;
            self.workCode('100');
            self.workPlaceName('営業部');
            self.employeeCode('A000001');
            self.employeeName('日通　太郎');
            self.date('20160424');
            self.deadline('20160724');
        }


        public updateData() {
            let self = this;
            let data = {
                employeeId: "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
                cID: "123",
                sID: "321",
                unknownDate: moment.utc(self.unknownDate(), 'YYYY/MM/DD').toISOString(),
                dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString(),
                expiredDate: moment.utc(self.expiredDate(), 'YYYY/MM/DD').toISOString(),
                lawAtr: self.lawAtr(),
                occurredDays: self.occurredDays(),
                unUsedDays: self.unUsedDays(),
                stateAtr: self.stateAtr(),
                disapearDate: moment.utc(self.disapearDate(), 'YYYY/MM/DD').toISOString(),
                checkBox: self.checkBox()
            };

            console.log(data);
            service.updatePayout(data).done(() => {

            }).fail(function(res: any) {

            });
        }

        public removeData() {
            let self = this;
            let data = {
               employeeId: "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
               sID: "456",
               dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString()
            };
            console.log(data);
            service.removePayout(data).done(() => {

            }).fail(function(res: any) {

            });
        }


        /**
        * closeDialog
        */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }


        //        public update() {
        //            let self = this,
        //                currentItem: Selection = self.selection();
        //            let command = ko.toJS(currentItem);
        //            service.updatePayout(command).done(function() {
        //            });
              console.log('update');
        //        }
        
        //       public remove() {
        //            let self = this,
        //                currentItem: Selection = self.selection();
        //            let command = ko.toJS(currentItem);
        //            service.removePayout(command).done(function() {
        //
        //            });
        //            console.log('remove');
        //        }

    }

    //    interface ISelection {
    //        payoutId: string;
    //        cID: string;
    //        sID: string;
    //        unknownDate: string;
    //        dayOff: string;
    //        expiredDate: string;
    //        lawAtr: number;
    //        occurredDays: number;
    //        unUsedDays: number;
    //        stateAtr: number;
    //        disapearDate: string;
    //        checkBox : boolean;
    //    }

    //    export class Selection {
    //        payoutId: KnockoutObservable<string> = ko.observable('');
    //        cID: KnockoutObservable<string> = ko.observable('');
    //        sID: KnockoutObservable<string> = ko.observable('');
    //        unknownDate: KnockoutObservable<string> = ko.observable('');
    //        dayOff: KnockoutObservable<string> = ko.observable('');
    //        expiredDate: KnockoutObservable<string> = ko.observable('');
    //        lawAtr: KnockoutObservable<number> = ko.observable('');
    //        occurredDays: KnockoutObservable<number> = ko.observable('');
    //        unUsedDays: KnockoutObservable<number> = ko.observable('');
    //        stateAtr: KnockoutObservable<number> = ko.observable('');
    //        disapearDate: KnockoutObservable<string> = ko.observable('');
    //        checkBox: KnockoutObservable<boolean> = ko.observable(false);
    //        
    //        constructor(payoutId: string, cID: string, sID: string,
    //            unknownDate: string, dayOff: string, expiredDate: string, lawAtr: number, occurredDays: number, unUsedDays: number, stateAtr: number, disapearDate: number, checkBox: boolean) {
    //            this.payoutId = payoutId;
    //            this.cID = cID;
    //            this.sID = sID;
    //            this.unknownDate = unknownDate;
    //            this.dayOff = dayOff;
    //            this.expiredDate = expiredDate;
    //            this.lawAtr = lawAtr;
    //            this.occurredDays = occurredDays;
    //            this.unUsedDays = unUsedDays;
    //            this.stateAtr = stateAtr;
    //            this.disapearDate = disapearDate;
    //            this.checkBox = checkBox;
    //        }
    //    }
}