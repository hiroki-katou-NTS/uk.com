module nts.uk.at.view.ksm005.a {
	import baseService = nts.uk.at.view.kdl023.base.service;
	import ReflectionSetting = baseService.model.ReflectionSetting;
	import DayOffSetting = baseService.model.DayOffSetting;
    import MonthlyPatternDto = service.model.MonthlyPatternDto;
    import WorkMonthlySettingDto = service.model.WorkMonthlySettingDto;
    import blockUI = nts.uk.ui.block;
    import text = nts.uk.resource;
    import empty = nts.uk.util;

    export module viewmodel {

        import getText = nts.uk.resource.getText;

        export class ScreenModel {
            columnMonthlyPatterns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            lstMonthlyPattern: KnockoutObservableArray<MonthlyPatternDto>;
            selectMonthlyPattern: KnockoutObservable<string>;
            modeMonthlyPattern: KnockoutObservable<number>;
            monthlyPatternModel: KnockoutObservable<MonthlyPatternModel>;
            lstWorkMonthlySetting: KnockoutObservableArray<WorkMonthlySettingDto>;
            enableDelete: KnockoutObservable<boolean>;

            yearMonthPicked: KnockoutObservable<number>;
            cssRangerYM: any;
            optionDates: KnockoutObservableArray<CalendarItem> = ko.observableArray([]);
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

            typeOfWorkCode: KnockoutObservable<string>;
            typeOfWorkName: KnockoutObservable<string>;
            typeOfWorkInfo: KnockoutObservable<string>;
            workingHoursCode: KnockoutObservable<string>;
            workingHoursName: KnockoutObservable<string>;
            workingHoursInfo: KnockoutObservable<string>;

            enableUpdate: KnockoutObservable<boolean>;
            typeOfWorkLabel: KnockoutObservable<string>;
            workingHoursLabel: KnockoutObservable<string>;
            reflectionSetting: ReflectionSetting;
            calendarOptions: KnockoutObservableArray<any>;
            cssRangerYM = ko.observable({});

            workStyle: string;

            constructor() {
                const self = this;
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
                self.calendarOptions = ko.observableArray([]);

                // now month setting kcp006
                self.yearMonthPicked = ko.observable(0); //monthlyPatternCode

                self.monthlyPatternModel().name.subscribe(() => {
                    const mvm = new ko.ViewModel();

                    mvm.$validate('#inp_monthlyPatternName');
                });

                self.selectMonthlyPattern.subscribe(function(monthlyPatternCode: string) {
                    if (self.isBuild) {
                        self.clearValiate();
                    }

                    if (monthlyPatternCode) {
                        self.modeMonthlyPattern(ModeMonthlyPattern.UPDATE);
                        if (self.yearMonthPicked() == self.getMonth()){
                            self.yearMonthPicked.valueHasMutated();
                        } else{
                            self.yearMonthPicked(self.getMonth());
                        }
                        self.enableDelete(true);
	                    self.enableUpdate(true);
                        $('#inp_monthlyPatternName').focus();
                    } else {
                        self.resetData().then(() => {
                            blockUI.clear();
                            self.clearValiate();
                        });
                        self.enableDelete(false);
	                    self.enableUpdate(false);
                        $('#inp_monthlyPatternCode').focus();
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
                            let dataUpdate: WorkMonthlySettingDto[] = [];
                            for (let item of data) {
                                item.workTypeCode='';
                                item.workTypeName = '';
                                item.workingCode='';
                                item.workingName = '';
                                item.typeColor = TypeColor.ATTENDANCE;
                                dataUpdate.push(item);
                            }
                            self.updateWorkMothlySetting(dataUpdate);
                            self.lstWorkMonthlySetting(dataUpdate);
                        });
                    }
                });

                $("#calendar").ntsCalendar("init", {
	                cellClick: function(date) {
		                nts.uk.ui._viewModel.content.setWorkingDayAtr(date);
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
                self.cellButtonDisplay = ko.observable(false);

                self.typeOfWorkCode = ko.observable(null);
	            self.typeOfWorkName = ko.observable(null);
	            self.typeOfWorkInfo = ko.observable(null);
	            self.workingHoursCode = ko.observable(null);
	            self.workingHoursName = ko.observable(null);
	            self.workingHoursInfo = ko.observable(null);

	            self.enableUpdate  = ko.observable(false);

	            self.typeOfWorkLabel = ko.computed( ()=> {
	                return self.typeOfWorkInfo() != null ? text.getText('KSM005_86') : '' ;
	            });
	            self.workingHoursLabel = ko.computed( ()=> {
		            return self.workingHoursInfo() != null ? text.getText('KSM005_87') : '' ;
	            });
            }

            /**
             * get month now
             */
            private getMonth(): number {
                return parseInt(moment().format('YYYYMM'));// + '01');
            }

            /**
             * open dialog batch setting (init view model e)
             */
            public openBatchSettingDialog(): void {
                const self = this;
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
                const self = this;
                nts.uk.ui.windows.setShared("monthlyPatternCode", nts.uk.text.padLeft(self.monthlyPatternModel().code(), '0', 3));
                nts.uk.ui.windows.setShared("monthlyPatternName", self.monthlyPatternModel().name());
                nts.uk.ui.windows.setShared("yearmonth", self.yearMonthPicked());
                nts.uk.ui.windows.sub.modal("/view/ksm/005/b/index.xhtml").onClosed(function() {
                    let isCancelSave: boolean = nts.uk.ui.windows.getShared("isCancelSave");
                    if (isCancelSave != null && isCancelSave != undefined && !isCancelSave) {
                        let endYearMonth: number = nts.uk.ui.windows.getShared("endYearMonth");
                        self.setYearMonthPicked(Number(endYearMonth))
                    }
                    if(self.selectMonthlyPattern()) {
                        $('#inp_monthlyPatternName').focus();

                    } else {
                        $('#inp_monthlyPatternCode').focus();
                    }
                });    
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                const self = this;
                let dfd = $.Deferred();

	            service.getMonthlyAll().done( function( data ) {
	            	let listMonthlyPattern = data.listMonthlyPattern;
                    listMonthlyPattern = listMonthlyPattern && listMonthlyPattern.map(item =>  {
                        return {
                            code: item.monthlyPatternCode,
                            name: item.monthlyPatternName
                        }
                    });
		            self.lstMonthlyPattern(listMonthlyPattern);
		            if(listMonthlyPattern.length <= 0){
			            self.enableDelete(false);
			            self.resetData();
		            }else {
			            self.selectMonthlyPattern(listMonthlyPattern[0].code);
		            }
                    dfd.resolve(self);
	            });
                return dfd.promise();
            }
            
            /**
             * setting view by call service return
             */
            public updateWorkMothlySetting(data: WorkMonthlySettingDto[]): void{
                const self = this;
                let optionDates: any[] = [];
                for(let settings of data){
                    optionDates.push(self.toOptionDate(settings));      
                }
                self.optionDates(optionDates);
            }
            
            /**
             * convert option date
             */
            public toOptionDate(dto: WorkMonthlySettingDto): any {
                // view option date 
                const self = this;
                let start: string = self.convertYMD(dto.ymdk);
                let textColor: string = '';
	            let row1: string = '';
	            let row2: string = '';

                //row1
                if( nts.uk.util.isNullOrEmpty(dto.workTypeCode) ){
                    row1 = '';
                } else if( nts.uk.util.isNullOrEmpty(dto.workTypeName) ){
	                row1 = dto.workTypeCode + text.getText('KSM005_84');
                } else row1 = dto.workTypeName;

	            //row2
                if(nts.uk.util.isNullOrEmpty(dto.workingCode)){
                    row2 = '';
                }else if(dto.workingCode && nts.uk.util.isNullOrEmpty(dto.workingName) ){
                    row2 = dto.workingCode + text.getText('KSM005_84');
                } else row2 = dto.workingName;

	            if (nts.uk.util.isNullOrEmpty(dto.workTypeName) && !nts.uk.util.isNullOrEmpty(dto.workTypeCode)) {
                    textColor = 'black';
                }
                else if (dto.typeColor == TypeColor.ATTENDANCE) {
                    textColor = TypeColor.ATTENDANCE_COLOR;
                } else if (dto.typeColor == TypeColor.HALF_DAY_WORK) {
                    textColor = TypeColor.HALF_DAY_WORK_COLOR;
                }
                else {
                    textColor = TypeColor.HOLIDAY_COLOR;
                }
                return new CalendarItem(start, textColor, row1, row2, false);
            }
            
            /**
             * reload data page by call service
             */
            public reloadPage(selectedCode: string, isDelete: boolean ): void{
                const self = this;
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
                            self.setSelectMonthlyPattern(data[data.length-1].code);
                            return;
                        }
                        let i = _.findIndex(self.lstMonthlyPattern(), item => item.code == selectedCode);
                        self.lstMonthlyPattern(data);
                        self.setSelectMonthlyPattern(data[i].code);
                        return;
                    }
                    self.lstMonthlyPattern(data);
                    self.setSelectMonthlyPattern(selectedCode);
                    self.monthlyPatternModel().updateEnable(false);
                }); 
            }

            setSelectMonthlyPattern(value: string){
                const self = this;
                if (self.selectMonthlyPattern() == value){
                    self.selectMonthlyPattern.valueHasMutated();
                } else {
                    self.selectMonthlyPattern(value);
                }
            }

            setYearMonthPicked(value: number){
                const self = this;
                if (self.yearMonthPicked() == value){
                    self.yearMonthPicked.valueHasMutated();
                } else {
                    self.yearMonthPicked(value);
                }
            }
            
            /**
             * check select by selected code
             */
            public isLastMonthlyPattern(selectedCode: string): boolean {
                const self = this;
                let index: number = 0;
                for(let item of self.lstMonthlyPattern()){
                    index++;
                    if(index == self.lstMonthlyPattern().length && selectedCode === item.code){
                        return true;
                    }
                }
                return false;
            }

            /**
             * detail monthly pattern by selected monthly pattern code
             */
            public detailMonthlyPattern(monthlyPatternCode: string, month: number): void {
	            let self = this,
		            currentMonth = self.getCurrentMonthPicked(),
		            params = {
			            monthlyPatternCode: monthlyPatternCode || self.selectMonthlyPattern(),
			            startDate: currentMonth.startDate,
			            endDate: currentMonth.endDate
		            };
                blockUI.invisible();
	            service.getMonthlyPattern( params ).done( (data) => {
                    let a = {};
                    a[Math.floor(self.yearMonthPicked() / 100)] = data.listMonthYear;
                    self.cssRangerYM(a);
                    if (monthlyPatternCode) {
                        service.findByIdMonthlyPattern(monthlyPatternCode)
	                    .done(function(response) {
	                        self.monthlyPatternModel().updateData( response );
	                        self.monthlyPatternModel().updateEnable( false );
	                        self.enableDelete( true );
	                        if ( data.monthlyPatternDtos.length > 0 ) {
	                            //save to clear calendar
		                        self.calendarOptions( data.monthlyPatternDtos );
		                        self.updateWorkMothlySetting ( data.monthlyPatternDtos );
		                        self.lstWorkMonthlySetting ( data.monthlyPatternDtos );
	                        } else {
		                        self.clearCalendar();
	                        }
                        });
                        $('#inp_monthlyPatternName').focus();
                    } else {
                        $('#inp_monthlyPatternCode').focus();
	                    self.clearCalendar();
                    }
                }).fail((response) => {
		            nts.uk.ui.dialog.alertError(response.message);
	            }).always(() => blockUI.clear());
            }
            
            /**
             * reset data (mode new)
             */
            public resetData(): JQueryPromise<any> {
                const self = this;
                if(self.selectMonthlyPattern() == '') {
                    $('#inp_monthlyPatternCode').focus();
                }
                if (self.isBuild) {
                    self.clearValiate();
                }
                let dfd = $.Deferred();
                self.cssRangerYM({});
                self.modeMonthlyPattern(ModeMonthlyPattern.ADD);
                self.yearMonthPicked(self.getMonth());
                self.monthlyPatternModel().resetData();
                let dataUpdate: WorkMonthlySettingDto[] = [];
                for (let item of self.lstWorkMonthlySetting()) {
                    item.workTypeCode='';
                    item.workTypeName = '';
                    item.workingCode='';
                    item.workingName = '';
                    item.typeColor = TypeColor.ATTENDANCE;
                    dataUpdate.push(item);
                }
                self.lstWorkMonthlySetting(dataUpdate);
                self.selectMonthlyPattern('');
                self.updateWorkMothlySetting(dataUpdate);
                self.enableDelete(false);
	            self.enableUpdate(false);
	            dfd.resolve();
                return dfd.promise();
            }
            /**
             * convert date month day => YYYYMMDD
             */
            public convertYMD(ymdk: string): string {
                return moment(ymdk, "YYYY/MM/DD").format("YYYY-MM-DD");
            }

            /**
             * convert date month day => YYYYMMDD
             */
            public convertYMDToDate(ymdk: string): string {
                return moment(ymdk, "YYYY-MM-DD").format("YYYY/MM/DD");
            }
            
            /**
             * find data work monthly setting by date
             */
            public findByDate(date: string): WorkMonthlySettingDto{
                const self = this;
               let workMonthlySetting : WorkMonthlySettingDto= _.find(self.lstWorkMonthlySetting(), function(item) {
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
                let dto: WorkMonthlySettingDto = {
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
                const self = this;
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
                const self = this;
                if(self.validateClient()){
                    return;    
                }
                
                if (_.isNull(self.yearMonthPicked())){
                    return;
                }
                //登録の時には月間パターンカレンダーのデータがない。
	            //monthlyPatternData
                blockUI.grayout();
                service.saveMonthWorkMonthlySetting(self.lstWorkMonthlySetting(), self.monthlyPatternModel().toDto(),
                                                    self.modeMonthlyPattern()).done(function() {
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
                const self = this;

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.deleteMonthlyPattern(self.monthlyPatternModel().code()).done(function() {
                         nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function(){
                            // reload page
                            self.reloadPage(self.monthlyPatternModel().code(), true);
                            nts.uk.ui.errors.clearAll()
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error).then(function() {
                            self.reloadPage(self.selectMonthlyPattern(), false);
                            nts.uk.ui.errors.clearAll()
                        });
                    });    
                }).ifNo(function() {
                   self.reloadPage(self.selectMonthlyPattern(),false);
                })    
            }


	        /**
	         * open dialog KDL003 by Work Days
	         */
	        public openDialogKDL03(): void {
		        const self = this;

		        nts.uk.ui.windows.setShared('parentCodes', {
			        selectedWorkTypeCode: self.typeOfWorkCode(),
			        selectedWorkTimeCode: self.workingHoursCode()
		        }, true);

		        nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
			        let childData = nts.uk.ui.windows.getShared('childData');

			        service.getWorkStyle(childData.selectedWorkTypeCode, childData.selectedWorkTimeCode).done(data => {
                        if(data.typeColor == TypeColor.HOLIDAY  ) {
                            self.workStyle = TypeColor.HOLIDAY_COLOR
                        } else if(data.typeColor == TypeColor.ATTENDANCE) {
                            self.workStyle = TypeColor.ATTENDANCE_COLOR
                        } else {
                            self.workStyle = TypeColor.HALF_DAY_WORK_COLOR
                        }
                    });

			        if (childData) {
				        self.typeOfWorkCode(childData.selectedWorkTypeCode);
				        self.typeOfWorkName(childData.selectedWorkTypeName);
				        if (childData.selectedWorkTypeCode) {
					        self.typeOfWorkInfo(childData.selectedWorkTypeCode + '   ' + childData.selectedWorkTypeName);
				        } else
					        self.typeOfWorkInfo('');

				        self.workingHoursCode(childData.selectedWorkTimeCode);
				        self.workingHoursName(childData.selectedWorkTimeName);
				        if (childData.selectedWorkTimeCode) {
					        self.workingHoursInfo(childData.selectedWorkTimeCode + '   ' + childData.selectedWorkTimeName);
				        } else {
					        self.workingHoursInfo('');
				        }
			        }
		        });
	        }

	        private showDialogKDL023(): void {
	            const self = this,
                    yearMonth = self.yearMonthPicked().toString(),
                    year: number = parseInt(yearMonth.substring(0, 4)),
		            month: number = parseInt(yearMonth.substring(4, yearMonth.length)),
			        startDate = moment([year, month - 1]).format("YYYY-MM-DD"),
			        endDate = moment(startDate).endOf('month').format("YYYY-MM-DD"),
			        dataMonthly: ReflectionSetting = {
				        calendarStartDate: startDate,
				        calendarEndDate: endDate,
                        monthlyPatternCode: self.selectMonthlyPattern(),
				        selectedPatternCd: '',
				        patternStartDate: startDate,
				        reflectionMethod: 1,
				        statutorySetting: self.convertWorktypeSetting(true, ''),
				        holidaySetting: self.convertWorktypeSetting(true, ''),
				        nonStatutorySetting: self.convertWorktypeSetting(true, '')
			        };
		        nts.uk.ui.windows.setShared('reflectionSetting', ko.toJS(dataMonthly));
		        nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml').onClosed(() => {
			        let dto = nts.uk.ui.windows.getShared('returnedData');

			        if(dto) {
                        let endYearMonth: number = nts.uk.ui.windows.getShared("endYearMonth");
                        self.setYearMonthPicked(Number(endYearMonth))
                    }

			        if(self.selectMonthlyPattern()) {
                        $('#inp_monthlyPatternName').focus();

                    } else {
                        $('#inp_monthlyPatternCode').focus();
                    }
			       /* if (dto) {
				        self.returnedSetting.fromDto(dto);
			        }*/
		        });
	        }

            private clearWorkMothly() {
                let self = this;
                if (self.isBuild)  self.clearValiate();

                let dataUpdate: WorkMonthlySettingDto[] = [];
                for (let item of self.lstWorkMonthlySetting()) {
                    item.workTypeCode ='';
                    item.workTypeName = '';
                    item.workingCode ='';
                    item.workingName = '';
                    item.typeColor = TypeColor.HOLIDAY;
                    dataUpdate.push(item);
                }
                self.lstWorkMonthlySetting(dataUpdate);
                self.updateWorkMothlySetting(dataUpdate);
                //self.enableDelete(false);
                //self.enableUpdate(false);
            }

	        /*
                setting date Wokring Day Atr event
            */
	        private setWorkingDayAtr(date){
		        let vm = this;
		        if(vm.typeOfWorkCode()) {
                    let dataUpdate: Array<WorkMonthlySettingDto> = vm.lstWorkMonthlySetting();
                    let i = _.findIndex(dataUpdate, (item) => vm.convertYMD(item.ymdk) == date);
                    let optionDates = vm.optionDates;
                    let existItem = _.find(optionDates(), item => item.start == date);

                    if(existItem != null) {
                        existItem.changeListText(
                            vm.typeOfWorkName() ? vm.typeOfWorkName() : vm.typeOfWorkCode()+getText('KSM005_84'),
                            vm.workingHoursCode() ?  vm.workingHoursName() ? vm.workingHoursName() : vm.workingHoursCode()+getText('KSM005_84') : '',
                            !vm.typeOfWorkName() ? TypeColor.NOT_EXIST_COLOR : vm.workStyle ? vm.workStyle : null
                        );
                    } else {
                        optionDates.push(new CalendarItem(date, vm.workStyle, vm.typeOfWorkName(),  vm.workingHoursName(), true))
                    }

                    if( dataUpdate && i > -1 && !empty.isNullOrEmpty(vm.typeOfWorkCode()) ) {
                        dataUpdate[i].workTypeCode = vm.typeOfWorkCode();
                        dataUpdate[i].workTypeName = vm.typeOfWorkName();
                        dataUpdate[i].workingCode  = vm.workingHoursCode();
                        dataUpdate[i].workingName  = vm.workingHoursName();
                    } else {
                        let typeColor: number;
                        if(vm.workStyle == TypeColor.ATTENDANCE_COLOR) {
                            typeColor = TypeColor.ATTENDANCE
                        } else if(vm.workStyle == TypeColor.HOLIDAY_COLOR) {
                            typeColor = TypeColor.HOLIDAY
                        } else {
                            typeColor = TypeColor.HALF_DAY_WORK
                        }
                        vm.lstWorkMonthlySetting.push({
                            workTypeCode: vm.typeOfWorkCode(),
                            workingCode: vm.workingHoursCode(),
                            ymdk: vm.convertYMDToDate(date),
                            monthlyPatternCode: vm.selectMonthlyPattern(),
                            workTypeName: vm.typeOfWorkName(),
                            typeColor: typeColor,
                            workingName: vm.workingHoursName()
                        })
                    }

                    vm.lstWorkMonthlySetting(dataUpdate);
                    optionDates.valueHasMutated();
                }
	        }

            private  clearCalendar() {
                let self = this,
	                currentMonth = moment(self.yearMonthPicked(), "YYYYMM"),
	                endDate = currentMonth.endOf('month').format("DD"),
                    dataUpdate: WorkMonthlySettingDto[] = [];

                for (let i = 1; i <= parseInt(endDate); i++) {
                    let ymdk = self.yearMonthPicked() + ( i < 10 ? '0' + i : i).toString();

	                let item = {
		                workTypeCode : '',
		                workTypeName : '',
		                workingCode : '',
		                workingName : '',
		                typeColor : TypeColor.ATTENDANCE,
		                monthlyPatternCode : '',
		                ymdk: moment(ymdk, "YYYYMMDD").format("YYYY/MM/DD")
	                };
	                dataUpdate.push(item);
                }

                self.updateWorkMothlySetting(dataUpdate);
                self.lstWorkMonthlySetting(dataUpdate);
            }
            /**
	         * convert work type setting
	         */
	        private convertWorktypeSetting(use: boolean, worktypeCode: string): DayOffSetting {
		        let data: DayOffSetting = {
			        useClassification: !!use,
			        workTypeCode: worktypeCode
		        };
		        return data;
	        }

	        private  getCurrentMonthPicked() {
		        let self = this,
		            currentMonth = moment(self.yearMonthPicked(), "YYYYMM"),
			        startDate = currentMonth.format("YYYY-MM-DD"),
			        endDate = currentMonth.endOf('month').format("YYYY-MM-DD");
		        return { startDate: moment.utc(startDate , "YYYY/MM/DD"), endDate: moment.utc(endDate , "YYYY/MM/DD") };
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
                let dto: MonthlyPatternDto;
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
            static HOLIDAY = 1;
            static HOLIDAY_COLOR = "#ff0000";
            static ATTENDANCE = 0;
            static ATTENDANCE_COLOR = "#0000ff";
            static HALF_DAY_WORK = 2;
            static HALF_DAY_WORK_COLOR = '#FF7F27';
            static NOT_EXIST_COLOR = 'black';
        }
        export class CalendarItem {
            start: string;
            textColor: string;
            backgroundColor: string;
            listText: Array<any>;
            insertText: boolean;
            constructor(start: string, textColor: string, row1: string, row2: string, insertText: boolean) {
                this.start = moment(start.toString()).format('YYYY-MM-DD');
                this.backgroundColor = 'white';
                this.textColor = textColor;
                this.listText= [row1, row2];
                this.insertText = insertText;
            }
            changeListText(row1: string, row2: string, textColor: string){
                if (textColor) {
                    this.textColor = textColor;
                }
                this.listText= [row1, row2];
                this.insertText = true;
            }
        }

    }

}