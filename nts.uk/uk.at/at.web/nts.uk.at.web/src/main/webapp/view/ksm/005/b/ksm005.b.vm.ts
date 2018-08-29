module nts.uk.at.view.ksm005.b {

    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import WorkMonthlySettingDto = service.model.WorkMonthlySettingDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;
    import blockUI = nts.uk.ui.block;
    export module viewmodel {

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;
            modeMonthlyPattern: KnockoutObservable<number>;
            monthlyPatternModel: KnockoutObservable<MonthlyPatternModel>;
            lstWorkMonthlySetting: KnockoutObservableArray<WorkMonthlySettingDto>;
            enableDelete: KnockoutObservable<boolean>;

            calendarData: KnockoutObservable<any>;
            yearMonthPicked: KnockoutObservable<number>;
            cssRangerYM: any;
            optionDates: KnockoutObservableArray<any>;
            firstDay: number;
            yearMonth: KnockoutObservable<number>;
            startDate: number;
            endDate: number;
            isBuild: boolean;
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
                    { headerText: nts.uk.resource.getText("KSM005_14"), key: 'name', width: 150 ,formatter: _.escape }
                ]);
                self.isBuild = false;
                self.lstWorkMonthlySetting = ko.observableArray([]);
                self.lstMonthlyPattern = ko.observableArray([]);
                self.selectMonthlyPattern = ko.observable('');
                self.monthlyPatternModel = ko.observable(new MonthlyPatternModel());
                self.modeMonthlyPattern = ko.observable(ModeMonthlyPattern.ADD);
                self.enableDelete = ko.observable(true);
                
                // now month setting kcp006
                self.yearMonthPicked = ko.observable(self.getMonth());
                self.yearMonthPicked.extend({ notify: 'always' });
                
                self.selectMonthlyPattern.subscribe(function(monthlyPatternCode: string) {
                    if (self.isBuild) {
                        self.clearValiate();
                    }
                    if (monthlyPatternCode) {
                        self.yearMonthPicked(self.getMonth());
                        self.detailMonthlyPattern(monthlyPatternCode, self.yearMonthPicked());
                        self.enableDelete(true);
                    }
                    else {
                        self.resetData();
                        self.enableDelete(false);
                    }
                });
                
                self.yearMonthPicked.subscribe(function(month: number){
                    if($('#yMPicker').ntsError('hasError')){
                        return; 
                    }
                    if (self.modeMonthlyPattern() == ModeMonthlyPattern.UPDATE) {
                        self.detailMonthlyPattern(self.selectMonthlyPattern(), month);
                    }
                    
                    if (self.modeMonthlyPattern() == ModeMonthlyPattern.ADD){
                        service.getItemOfMonth(self.selectMonthlyPattern(), month).done(function(data) {
                            var dataUpdate: WorkMonthlySettingDto[] = [];
                            for (var item of data) {
                                item.workTypeCode='';
                                item.workTypeName = '';
                                item.workingCode='';
                                item.workingName = '';
                                item.typeColor = TypeColor.HOLIDAY;
                                dataUpdate.push(item);
                            }
                            self.updateWorkMothlySetting(dataUpdate);
                            self.lstWorkMonthlySetting(dataUpdate);
                        });
                    }
                });
                self.cssRangerYM = {
                };
                $("#calendar").ntsCalendar("init", {
                    buttonClick: function(date: string) {
                        self.openDialogByFindDate(moment.utc(date,"YYYY-MM-DD").format("YYYY/MM/DD"));
                    }
                });
                self.optionDates = ko.observableArray([]);
                self.firstDay = 0;
                self.startDate = 1;
                self.endDate = 31;
                self.workplaceId = ko.observable("0");
                self.workplaceName = ko.observable("");
                self.eventDisplay = ko.observable(true);
                self.eventUpdatable = ko.observable(true);
                self.holidayDisplay = ko.observable(true);
                self.cellButtonDisplay = ko.observable(true);
                
                var self = this;
                service.getItemOfMonth(self.selectMonthlyPattern(), self.getMonth()).done(function(data) {
                    self.updateWorkMothlySetting(data);
                    self.lstWorkMonthlySetting(data);
                });
            }
            /**
             * get month now
             */
            private getMonth(): number {
                return parseInt(moment().format('YYYY') + '01');
            }

            /**
             * open dialog batch setting (init view model e)
             */
            public openBatchSettingDialog(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                
                // mode ADD
                if(self.modeMonthlyPattern() == ModeMonthlyPattern.ADD){
                    service.findByIdMonthlyPattern(nts.uk.text.padLeft(self.monthlyPatternModel().code(), '0', 3)).done(function(data){
                       if(data && data.code){
                           $('#inp_monthlyPatternCode').ntsError('set', {messageId: 'Msg_3'});
                           return;
                       }else {
                           self.openDialogBacthSetting();
                       }
                    });
                } else {
                    self.openDialogBacthSetting();
                }
                
            }
            
            /**
             * open batch setting E
             */
            private openDialogBacthSetting(): void{
                var self = this; 
                nts.uk.ui.windows.setShared("monthlyPatternCode", nts.uk.text.padLeft(self.monthlyPatternModel().code(), '0', 3));
                nts.uk.ui.windows.setShared("monthlyPatternName", self.monthlyPatternModel().name());
                nts.uk.ui.windows.setShared("yearmonth", self.yearMonthPicked());
                nts.uk.ui.windows.sub.modal("/view/ksm/005/e/index.xhtml").onClosed(function() {
                    var isCancelSave: boolean = nts.uk.ui.windows.getShared("isCancelSave");
                    if (isCancelSave != null && isCancelSave != undefined && !isCancelSave) {
                        self.reloadPage(nts.uk.text.padLeft(self.monthlyPatternModel().code(), '0', 3), false);
                    }
                });    
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                nts.uk.ui.block.invisible();
                service.findAllMonthlyPattern().done(function(data) {
                    self.lstMonthlyPattern(data);
                    if(data.length <= 0){
                        self.enableDelete(false);
                        self.resetData();
                    }else {
                        self.selectMonthlyPattern(data[0].code);
                    }
                    dfd.resolve(self);
                });
                nts.uk.ui.block.clear();
                return dfd.promise();
            }
            
            /**
             * setting view by call service return
             */
            public updateWorkMothlySetting(data: WorkMonthlySettingDto[]): void{
                var self = this;
//                nts.uk.ui.block.invisible();
                var optionDates: any[] = [];
                for(var settings of data){
                    optionDates.push(self.toOptionDate(settings));      
                }
                self.optionDates(optionDates);
            }
            
            /**
             * convert option date
             */
            public toOptionDate(dto: WorkMonthlySettingDto): any {
                // view option date 
                var self = this;
                var start: string = self.convertYMD(dto.ymdk);
                var textColor: string = '';
                var listText: string[] = [
                    dto.workTypeName,
                    dto.workingName
                ];
                if (dto.typeColor == TypeColor.ATTENDANCE) {
                    textColor = TypeColor.ATTENDANCE_COLOR;
                }
                else {
                    textColor = TypeColor.HOLIDAY_COLOR;
                }
                return {
                    start: start,
                    textColor: textColor,
                    backgroundColor: 'white',
                    listText: listText
                };
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
                            self.resetData();
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
                for(var item of self.lstMonthlyPattern()){
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
                for (var item of dataRes) {
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
                nts.uk.ui.block.invisible();
                service.findByMonthWorkMonthlySetting(monthlyPatternCode, month).done(function(data) {
                    if (monthlyPatternCode) {
                        service.findByIdMonthlyPattern(monthlyPatternCode).done(function(res) {
                            self.monthlyPatternModel().updateData(res);
                            self.modeMonthlyPattern(ModeMonthlyPattern.UPDATE);
                            self.monthlyPatternModel().updateEnable(false);
                            self.enableDelete(true);
                            self.updateWorkMothlySetting(data);
                            self.lstWorkMonthlySetting(data);
                        });
                    }
                    else {
                        self.updateWorkMothlySetting([]);
                        self.lstWorkMonthlySetting([]);
                    }
                    nts.uk.ui.block.clear();
//                    self.updateWorkMothlySetting(data);
//                    self.lstWorkMonthlySetting(data);
                });
            }
            
            /**
             * reset data (mode new)
             */
            public resetData(): void {
                var self = this;
                if (self.isBuild) {
                    self.clearValiate();
                }
                self.modeMonthlyPattern(ModeMonthlyPattern.ADD);
                self.yearMonthPicked(self.getMonth());
                self.monthlyPatternModel().resetData();   
                var dataUpdate: WorkMonthlySettingDto[] = [];
                for (var item of self.lstWorkMonthlySetting()) {
                    item.workTypeCode='';
                    item.workTypeName = '';
                    item.workingCode='';
                    item.workingName = '';
                    item.typeColor = TypeColor.HOLIDAY;
                    dataUpdate.push(item);
                }
                self.lstWorkMonthlySetting(dataUpdate);
                self.selectMonthlyPattern('');
                self.updateWorkMothlySetting(dataUpdate);
                self.enableDelete(false);
                // focus on code
                $('#inp_monthlyPatternCode').focus();
            }
            /**
             * convert date month day => YYYYMMDD
             */
            public convertYMD(ymdk: string): string {
                return moment(ymdk, "YYYY/MM/DD").format("YYYY-MM-DD");
            }
            
            /**
             * find data work monthly setting by date
             */
            public findByDate(date: string): WorkMonthlySettingDto{
                var self = this;
               var workMonthlySetting : WorkMonthlySettingDto= _.find(self.lstWorkMonthlySetting(), function(item) {
                    return item.ymdk == date;
                });
                if (!workMonthlySetting) {
                    return  null;
                }
                return workMonthlySetting;
            }
            
            /**
             * default dto
             */
            private toDefaultWorkMonthlySetting(date: string) {
                var self = this;
                var dto: WorkMonthlySettingDto = {
                    workTypeCode: '',
                    workingCode: '',
                    ymdk: date,
                    monthlyPatternCode: '',
                    workTypeName: '',
                    typeColor: TypeColor.HOLIDAY,
                    workingName: ''
                };
                return dto;
            }
            /**
             * open dialog KDL003 by find date
             */
            public openDialogByFindDate(date: string): void {
                var self = this;
                var dto: WorkMonthlySettingDto = self.findByDate(date);
                if (dto != null) {
                    nts.uk.ui.windows.setShared('parentCodes', {
                        selectedWorkTypeCode: dto.workTypeCode,
                        selectedWorkTimeCode: dto.workingCode
                    }, true);
                } else {
                    dto = self.toDefaultWorkMonthlySetting(date);
                    nts.uk.ui.windows.setShared('parentCodes', {
                        selectedWorkTypeCode: '',
                        selectedWorkTimeCode: ''
                    }, true);
                }

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function() {
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        dto.workTypeCode = childData.selectedWorkTypeCode;
                        dto.workTypeName = childData.selectedWorkTypeName;
                        dto.workingCode = childData.selectedWorkTimeCode;
                        dto.workingName = childData.selectedWorkTimeName;

                        if (dto.workTypeCode && dto.workingCode) {
                            dto.typeColor = TypeColor.ATTENDANCE;
                        } else {
                            dto.typeColor = TypeColor.HOLIDAY;
                        }

                        self.updateWorkMonthlySettingClose(dto);
                    }
                });
            }

            
            /**
             * update work monthly setting by close dialog KDL003
             */
            public updateWorkMonthlySettingClose(setting: WorkMonthlySettingDto): void{
                var self = this;
                var dataUpdate: WorkMonthlySettingDto[] = [];
                var isAdd: boolean = true;
                for(var item of self.lstWorkMonthlySetting()){
                    if(item.ymdk == moment(setting.ymdk, "YYYY-MM-DD").format("YYYY/MM/DD")){
                        setting.ymdk = moment(setting.ymdk, "YYYY-MM-DD").format("YYYY/MM/DD");
                        dataUpdate.push(setting);
//                        isAdd = false;
                    } else {
                        dataUpdate.push(item);
                    }
                } 
//                if (isAdd) {
//                    dataUpdate.push(setting);
//                }   
                self.lstWorkMonthlySetting(dataUpdate);
                self.updateWorkMothlySetting(dataUpdate);
            } 
             /**
             * clear validate client
             */
           public clearValiate() {
                $('#inp_monthlyPatternCode').ntsError('clear');
                $('#inp_monthlyPatternName').ntsError('clear');
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
            public saveDataMonthlyPattern(): void {
                var self = this;
                if(self.validateClient()){
                    return;    
                }
                
                if (_.isNull(self.yearMonthPicked())){
                    return;
                }
                blockUI.grayout();
                service.saveMonthWorkMonthlySetting(self.lstWorkMonthlySetting(), self.monthlyPatternModel().toDto(), self.modeMonthlyPattern()).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.reloadPage(nts.uk.text.padLeft(self.monthlyPatternModel().code(), '0', 3), false);
                    });
                }).fail(function(error) {
                    // show message
                    if (error.messageId === 'Msg_3') {
                        $('#inp_monthlyPatternCode').ntsError('set', error);
                    } else {
                        nts.uk.ui.dialog.alertError(error);
                    }
                }).always(()=> blockUI.clear());
                                
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
                    filldirection: "left",
                    fillcharacter: "0",
                    autofill: true,
                    width: "40px",
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
        
        export class TypeColor {
            static HOLIDAY = 0;
            static HOLIDAY_COLOR = "#ff0000";
            static ATTENDANCE = 1;
            static ATTENDANCE_COLOR = "#0000ff";
        }

    }

}