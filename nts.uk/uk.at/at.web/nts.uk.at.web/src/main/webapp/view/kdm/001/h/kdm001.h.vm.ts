module nts.uk.at.view.kdm001.h.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string>     = ko.observable('');
        workName: KnockoutObservable<string>     = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        substituteHolidayDate: KnockoutObservable<string>         = ko.observable('');
        holidayTimeList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        holidayTime: KnockoutObservable<string>                   = ko.observable('');
        remainDaysList: KnockoutObservableArray<model.ItemModel>  = ko.observableArray(model.getNumberOfDays());
        remainDays: KnockoutObservable<string>                    = ko.observable('');
        
        subOfHDID: KnockoutObservable<string>                     = ko.observable('');
        cid:KnockoutObservable<string>                            = ko.observable('');
        sID:KnockoutObservable<string>                            = ko.observable('');
        holidayDate:KnockoutObservable<string>                    = ko.observable('');
        requiredDays:KnockoutObservable<string>                   = ko.observable('');

        
        

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            let self = this;
            self.workCode('100');
            self.workName('営業部');
            self.employeeCode('A0000001');
            self.employeeName('日通　太郎');
            self.substituteHolidayDate('20160424');
        }

        closeKDM001H(): void {
            nts.uk.ui.windows.close();
        }
        
        openKDM001H(): void {
            modal("/view/kdm/001/h/index.xhtml").onClosed(function() { });
        }
    }
    
    
    
    
    
}