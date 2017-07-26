module nts.uk.at.view.ksm005.b {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import WorkMonthlySettingDto = service.model.WorkMonthlySettingDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;
            modeMonthlyPattern: KnockoutObservable<number>;
            monthlyPatternModel: KnockoutObservable<MonthlyPatternModel>;

            calendarData: KnockoutObservable<any>;
            yearMonthPicked: KnockoutObservable<number>;
            cssRangerYM: any;
            optionDates: KnockoutObservableArray<any>;
            firstDay: number;
            yearMonth: KnockoutObservable<number>;
            startDate: number;
            endDate: number;
            workplaceId: KnockoutObservable<string>;
            eventDisplay: KnockoutObservable<boolean>;
            eventUpdatable: KnockoutObservable<boolean>;
            holidayDisplay: KnockoutObservable<boolean>;
            cellButtonDisplay: KnockoutObservable<boolean>;
            workplaceName: KnockoutObservable<string>;


            constructor() {
                var self = this;
                self.columnMonthlyPatterns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSM005_13"), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'name', width: 150 }
                ]);

                self.lstMonthlyPattern = ko.observableArray([]);
                self.selectMonthlyPattern = ko.observable('');
                self.monthlyPatternModel = ko.observable(new MonthlyPatternModel());
                self.modeMonthlyPattern = ko.observable(ModeMonthlyPattern.ADD);
                self.yearMonthPicked = ko.observable(201707);
                self.selectMonthlyPattern.subscribe(function(monthlyPatternCode: string){
                   self.detailMonthlyPattern(monthlyPatternCode, self.yearMonthPicked()); 
                });
                
                self.yearMonthPicked.subscribe(function(month: number){
                    self.detailMonthlyPattern(self.selectMonthlyPattern(), month);
                });
                self.cssRangerYM = {
                };
                self.optionDates = ko.observableArray([
                    {
                        start: '2017-07-01',
                        textColor: 'red',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study"
                        ]
                    },
                    {
                        start: '2017-07-05',
                        textColor: '#31859C',
                        backgroundColor: 'white',
                        listText: [
                            "Sleepaaaa",
                            "Study",
                            "Eating",
                            "Woman"
                        ]
                    },
                    {
                        start: '2017-07-10',
                        textColor: '#31859C',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study"
                        ]
                    },
                    {
                        start: '2017-07-20',
                        textColor: 'blue',
                        backgroundColor: 'white',
                        listText: [
                            "Sleep",
                            "Study",
                            "Play"
                        ]
                    },
                    {
                        start: '2017-07-22',
                        textColor: 'blue',
                        backgroundColor: 'red',
                        listText: [
                            "Sleep",
                            "Study",
                            "Play"
                        ]
                    }
                ]);
                self.firstDay = 0;
                self.startDate = 1;
                self.endDate = 31;
                self.workplaceId = ko.observable("0");
                self.workplaceName = ko.observable("");
                self.eventDisplay = ko.observable(true);
                self.eventUpdatable = ko.observable(true);
                self.holidayDisplay = ko.observable(true);
                self.cellButtonDisplay = ko.observable(true);
                $("#calendar").ntsCalendar("init", {
                    buttonClick: function(date: Date) {
                        alert(date);
                    }
                });
            }

            /**
             * open dialog batch setting (init view model e)
             */
            public openBatchSettingDialog(): void {
                var self = this;
                nts.uk.ui.windows.setShared("monthlyPatternCode",self.selectMonthlyPattern());
                nts.uk.ui.windows.sub.modal("/view/ksm/005/e/index.xhtml").onClosed(function(){
                    self.reloadPage(self.selectMonthlyPattern(), false);    
                });
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllMonthlyPattern().done(function(data) {
                    self.lstMonthlyPattern(data);
                    if(data.length <= 0){
                        self.modeMonthlyPattern(ModeMonthlyPattern.ADD);
                    }else {
                        self.selectMonthlyPattern(data[0].code);
                    }
                    dfd.resolve(self);
                });
                return dfd.promise();
            }
            
            /**
             * setting view by call service return
             */
            public updateWorkMothlySetting(data: WorkMonthlySettingDto[], dataWorkType: WorkTypeDto[], dataWorkTime: WorkTimeDto[]): void{
                var self = this;
                var optionDates: any[] = [];
                for(var settings: WorkMonthlySettingDto of data){
                    optionDates.push({
                        start: self.convertYMD(settings.ymdk),
                        textColor: 'red',
                        backgroundColor: 'white',
                        listText: [
                            self.findNameWorkTypeCode(settings.workTypeCode, dataWorkType),
                            self.findNameWorkTimeCode(settings.workingCode, dataWorkTime)
                        ]
                    });      
                }
                self.optionDates(optionDates);
            }
            
             /**
             * find by work time code of data
             */
            public findNameWorkTypeCode(workTypeCode: string, data: WorkTypeDto[]) {
                var worktype = _.find(data, function(item) {
                    return item.workTypeCode == workTypeCode;
                });
                if (!worktype) {
                    return  nts.uk.resource.getText("KSM005_43");
                }
                return worktype.name;
            }
             /**
             * find by work time code of data
             */
            public findNameWorkTimeCode(workTimeCode: string, data: WorkTimeDto[]) {
                var worktime = _.find(data, function(item) {
                    return item.code == workTimeCode;
                });
                if (!worktime) {
                    return  nts.uk.resource.getText("KSM005_43");
                }
                return worktime.name;
            }
            /**
             * reload data page by call service
             */
            public reloadPage(selectedCode: string, isDelete: boolean ): void{
                var self = this;
                service.findAllMonthlyPattern().done(function(data) {
                    // event delete
                    if(isDelete){
                        if(data.length <= 0){
                            self.modeMonthlyPattern(ModeMonthlyPattern.ADD);
                            self.monthlyPatternModel().updateEnable(true);
                            self.lstMonthlyPattern(data);
                            return; 
                        }
                        
                        if(self.isLastMonthlyPattern(selectedCode)){
                            self.lstMonthlyPattern(data);
                             self.monthlyPatternModel().updateEnable(false);
                            self.selectMonthlyPattern(data[data.length-1].code);   
                            return;         
                        }
                        var dataHistory: MonthlyPatternDto[] = self.lstMonthlyPattern();
                        // check end visible
                        var indexSelected: number = 0;
                        for (var index: number = 0; index < dataHistory.length; index++) {
                            if (dataHistory[index].code == selectedCode) {
                                indexSelected = index;
                                break;
                            }
                        }

                        // check next visible
                        for (var index: number = indexSelected; index < dataHistory.length; index++) {
                            if (self.isVisibleMonthlyPattern(dataHistory[index].code, data)) {
                                self.lstMonthlyPattern(data);
                                self.selectMonthlyPattern(dataHistory[index].code);
                                return;
                            }
                        }
                        // check previous visible
                        for (var index: number = indexSelected; index >= 0; index--) {
                            if (self.isVisibleMonthlyPattern(dataHistory[index].code, data)) {
                                self.lstMonthlyPattern(data);
                                self.selectMonthlyPattern(dataHistory[index].code);
                                return;
                            }
                        }
                       return;  
                    }
                    self.lstMonthlyPattern(data);
                    self.selectMonthlyPattern(selectedCode);
                    self.monthlyPatternModel().updateEnable(false);
                    self.detailMonthlyPattern(self.selectMonthlyPattern(), self.yearMonthPicked());
                }); 
            }
            
            /**
             * check select by selected code
             */
            public isLastMonthlyPattern(selectedCode: string): boolean {
                var self = this;
                var index: number = 0;
                for(var item: MonthlyPatternDto of self.lstMonthlyPattern()){
                    index++;
                    if(index == self.lstMonthlyPattern().length && selectedCode === item.code){
                        return true;
                    }   
                }
                return false;
            }
            
            /**
             * check exist data by selected
             */
            public isVisibleMonthlyPattern(seletedCode: string, dataRes: MonthlyPatternDto[]){
                for (var item: MonthlyPatternDto of dataRes) {
                    if(seletedCode === item.code){
                        return true;    
                    }
                }
                return false;
            }
            /**
             * detail monthly pattern by selected monthly pattern code
             */
            public detailMonthlyPattern(monthlyPatternCode: string, month: number): void {
                var self = this;
                service.findByMonthWorkMonthlySetting(monthlyPatternCode, month).done(function(data) {
                    service.findByIdMonthlyPattern(monthlyPatternCode).done(function(res) {
                        service.findAllWorkType().done(function(dataWorkType) {
                            service.findAllWorkTime().done(function(dataWorkTime) {
                                self.monthlyPatternModel().updateData(res);
                                self.modeMonthlyPattern(ModeMonthlyPattern.UPDATE);
                                self.monthlyPatternModel().updateEnable(false);
                                self.updateWorkMothlySetting(data, dataWorkType, dataWorkTime);
                            });
                        });
                    });
                });
            }
            
            /**
             * reset data (mode new)
             */
            public resetData(): void{
                var self = this;
                self.monthlyPatternModel().resetData();   
                self.modeMonthlyPattern(ModeMonthlyPattern.ADD); 
            }
            /**
             * convert date month day => YYYYMMDD
             */
            public convertYMD(ymdk: number): string {
                var y: number = (ymdk/10000).toFixed(0);
                var m: number = ((ymdk%10000)/100).toFixed(0);
                var mm: string = m+'';
                var d: number = (ymdk % 100).toFixed(0);
                var dd: string = d + '';
                if (m < 10) {
                    mm = '0' + m;
                }
                if (d < 10) {
                    dd = '0' + d;
                }
                return y+'-'+mm+'-'+dd;
            }
             /**
             * clear validate client
             */
           public clearValiate() {
                $('#inp_monthlyPatternCode').ntsError('clear')
                $('#inp_monthlyPatternName').ntsError('clear')
            }
            
            
            /**
             * validate client by action click
             */
            public validateClient(): boolean {
                var self = this;
                self.clearValiate();
                $("#inp_monthlyPatternCode").ntsEditor("validate");
                $("#inp_monthlyPatternName").ntsEditor("validate");
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }
            
            /**
             * save data (add or update monthly pattern)
             */
            public saveDataMonthlyPattern(): void{
                var self = this;
                if(self.validateClient()){
                    return;    
                }
                // check mode ADD
                if(self.modeMonthlyPattern() == ModeMonthlyPattern.ADD){
                    service.addMonthlyPattern(self.monthlyPatternModel().toDto()).done(function(){
                        // show message 15
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                            // reload page
                            self.reloadPage(self.monthlyPatternModel().code(), false); 
                        });
                    }).fail(function(error){
                        nts.uk.ui.dialog.alertError(error).then(function(){
                              self.reloadPage(self.selectMonthlyPattern(), false); 
                        });
                    });    
                }else {
                    // mode UPDATE
                    service.updateMonthlyPattern(self.monthlyPatternModel().toDto()).done(function(){
                          // show message 15
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                            // reload page
                            self.reloadPage(self.monthlyPatternModel().code(), false); 
                        });
                    }).fail(function(error){
                        nts.uk.ui.dialog.alertError(error).then(function(){
                              self.reloadPage(self.selectMonthlyPattern(), false); 
                        });
                    });    
                }                    
            }
            
            /**
             * delete monthly pattern 
             */
            public deleteMonthlyPattern(): void {
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.deleteMonthlyPattern(self.monthlyPatternModel().code()).done(function() {
                         nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function(){
                            // reload page
                            self.reloadPage(self.monthlyPatternModel().code(), true); 
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error).then(function() {
                            self.reloadPage(self.selectMonthlyPattern(), false);
                        });
                    });    
                }).ifNo(function() {
                   self.reloadPage(self.selectMonthlyPattern(),false);
                })    
            }

        }
        
        export class MonthlyPatternModel{
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            textEditorOptionName: KnockoutObservable<any>;
            enableMonthlyPatternCode: KnockoutObservable<boolean>;
            
            constructor(){
                this.code = ko.observable('');
                this.name = ko.observable('');
                this.enableMonthlyPatternCode = ko.observable(true);
                this.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "left"
                }));
                this.textEditorOptionName = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "150px",
                    textmode: "text",
                    textalign: "left"
                })); 
            }    
            
            updateData(dto: MonthlyPatternDto) {
                this.code(dto.code);
                this.name(dto.name);
            }
            
            toDto(): MonthlyPatternDto {
                var dto: MonthlyPatternDto;
                dto = { code: this.code(), name: this.name() };
                return dto;
            }
            
            resetData(){
                this.code('');
                this.name('');    
                this.enableMonthlyPatternCode(true);
            }
            updateEnable(enable: boolean){
                this.enableMonthlyPatternCode(enable);
            }
        }
        
        export class ModeMonthlyPattern {
            static ADD = 1;
            static UPDATE = 2;
        }

    }

}