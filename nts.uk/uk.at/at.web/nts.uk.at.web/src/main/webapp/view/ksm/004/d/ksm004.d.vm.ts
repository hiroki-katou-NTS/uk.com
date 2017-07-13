module ksm004.d.viewmodel{
    export class ScreenModel{
        // start and end month
        startMonth : KnockoutObservable<number>;
        startMonth : KnockoutObservable<number>;
        
        // select day
        workdayGroup : KnockoutObservableArray<any>;
        selectedMon : KnockoutObservable<number>;
        selectedTue : KnockoutObservable<number>;
        selectedWed : KnockoutObservable<number>;
        selectedThu : KnockoutObservable<number>;
        selectedFri : KnockoutObservable<number>;
        selectedSat : KnockoutObservable<number>;
        selectedSun : KnockoutObservable<number>;
        
        // check box Overwrite
        checkSelect :  KnockoutObservable<boolean>;
        
        constructor(){
            var self = this;
            //start and end month
            self.startMonth = ko.observable(201601);
            self.endMonth = ko.observable(201612);
            //
            self.workdayGroup = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KSM004_46') },
                { code: '1', name: nts.uk.resource.getText('KSM004_47') },
                { code: '2', name: nts.uk.resource.getText('KSM004_48') }
            ]);
            // day
            self.selectedMon = ko.observable(0);
            self.selectedTue = ko.observable(0);
            self.selectedWed = ko.observable(0);
            self.selectedThu = ko.observable(0);
            self.selectedFri = ko.observable(0);
            self.selectedSat = ko.observable(0);
            self.selectedSun = ko.observable(0);
            //checked
            self.checkSelect = ko.observable(false);

        }//end constructor
        
        startPage(){
            
        }//end startPage
    }//end screen model    
}