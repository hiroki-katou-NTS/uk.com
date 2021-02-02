module nts.uk.pr.view.kmf001.b {
    export module viewmodel {

        import Enum = service.model.Enum;
        
        export class ScreenModel {
            categoryEnums: KnockoutObservableArray<Enum>;
            selectedPriority: KnockoutObservable<number>;
            enableInputPriority: KnockoutObservable<boolean>;
            priorityPause: KnockoutObservable<boolean>;
            prioritySubstitute: KnockoutObservable<boolean>;
            sixtyHoursOverrideHoliday: KnockoutObservable<boolean>;

            constructor() {

                let self = this;

                self.categoryEnums = ko.observableArray([]);

                self.selectedPriority = ko.observable(1);
                self.enableInputPriority = ko.computed(function() {
                    return self.selectedPriority() == 1;
                }, self);
                
                self.priorityPause = ko.observable(false);
                self.prioritySubstitute = ko.observable(false);
                self.sixtyHoursOverrideHoliday = ko.observable(false);
            }

            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<void>();
                $.when(self.loadCategoryEnums()).done(function(res) {
                    self.loadAcquisitionRule();
                    $('#priority').focus();
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private loadCategoryEnums(): JQueryPromise<Array<Enum>> {
                let self = this;

                let dfd = $.Deferred();
                service.categoryEnum().done(function(res: Array<Enum>) {
                    self.categoryEnums(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });

                return dfd.promise();
            }        
            
            //CLOSE DIALOG
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            //LOAD ACQUISITION RULE WHEN START DIALOG
            private loadAcquisitionRule(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                //  find data on db
                service.findAcquisitionRule().done(function(res: any) {
                    self.initUI(res);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res.message);
                });
                return dfd.promise();
            }

            //get data to dialog
            private initUI(res: any): void {
                let self = this;
                //if find data exist
                if (res) {
                    //if use Priority
                    self.selectedPriority(res.category); 
                    
                    self.priorityPause(res.annualHolidayShow.priorityPause);
                    self.prioritySubstitute(res.annualHolidayShow.prioritySubstitute);
                    self.sixtyHoursOverrideHoliday(res.annualHolidayShow.sixtyHoursOverrideHoliday);
                } else {
                    //if find data null
                    //Selected default button is "No Setting"
                    self.selectedPriority(1);
                    
                    self.priorityPause(false);
                    self.prioritySubstitute(false);
                    self.sixtyHoursOverrideHoliday(false);     
                }

                //when change button Select
                self.selectedPriority.subscribe(function(value) {
                    //if click button "No Setting"
                    if (value == 0) {
                        nts.uk.ui.errors.clearAll();
                    }
                });
            }

            //CLICK SAVE TO DB
            public saveToDb(): void {
                let self = this;
                let dfd = $.Deferred();
                
                let command = self.setList();
                
                nts.uk.ui.block.grayout();
                
                service.updateAcquisitionRule(command).done(function() {
                    self.loadAcquisitionRule().done(function(res) {
                        // Msg_15
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            self.closeDialog();
                        });
                        dfd.resolve();
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }
            
            //GET DATA
            private setList(): void {
                let self = this;
                let command: any = {};
                if (self.selectedPriority() == 0) {
                    command.category = 0;
                } else {
                    command.category = 1;
                }
                
                let annualHoliday: any = {};
                annualHoliday.priorityPause = self.priorityPause();
                annualHoliday.prioritySubstitute = self.prioritySubstitute();
                annualHoliday.sixtyHoursOverrideHoliday = self.sixtyHoursOverrideHoliday();
                
                let hoursHoliday: any = {};  
                command.annualHoliday = annualHoliday;
                command.hoursHoliday = hoursHoliday;
                
                return command;
            }
        }
    }
}
