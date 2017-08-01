module nts.uk.at.view.ksm006.a {
    import CompanyBasicWorkFindDto = service.model.CompanyBasicWorkFindDto; 
    import CompanyBasicWorkDto = service.model.CompanyBasicWorkDto;
    import BasicWorkSettingDto = service.model.BasicWorkSettingDto;
    import WorkplaceBasicWorkFindDto = service.model.WorkplaceBasicWorkFindDto;
    import WorkplaceBasicWorkDto = service.model.WorkplaceBasicWorkDto;
    import ClassifiBasicWorkFindDto = service.model.ClassifiBasicWorkFindDto;
    import ClassificationBasicWorkDto = service.model.ClassificationBasicWorkDto;
    export module viewmodel {
        export class ScreenModel {
            selectedWorkplaceId: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            workplaceAlreadySetList: KnockoutObservableArray<UnitAlreadySettingModel>;
            workplaceList: KnockoutObservableArray<UnitModel>;
            workplaceGrid: any;
            
            classificationGrid: any;
            selectedClassifi: KnockoutObservable<string>;
            classificationList: KnockoutObservableArray<UnitModel>;
            classifiAlreadySetList: KnockoutObservableArray<any>;
            
            // on Company Tab
            companyBWWorkingDay: KnockoutObservable<BasicWorkModel>;
            companyBWNonInLaw: KnockoutObservable<BasicWorkModel>;
            companyBWNonExtra: KnockoutObservable<BasicWorkModel>;
            
            // on Workplace Tab
            workplaceBWWorkingDay: KnockoutObservable<BasicWorkModel>;
            workplaceBWNonInLaw: KnockoutObservable<BasicWorkModel>;
            workplaceBWNonExtra: KnockoutObservable<BasicWorkModel>;
            
            // on Classification Tab
            classifyBWWorkingDay: KnockoutObservable<BasicWorkModel>;
            classifyBWNonInLaw: KnockoutObservable<BasicWorkModel>;
            classifyBWNonExtra: KnockoutObservable<BasicWorkModel>;
            
            isShowCompanyTab: KnockoutObservable<boolean>;
            isShowWorkplaceTab: KnockoutObservable<boolean>;
            isShowClassifyTab: KnockoutObservable<boolean>;
            
            companyId: string;
            isUpdateModeWorkplace: KnockoutObservable<boolean>;
            isUpdateModeClassify: KnockoutObservable<boolean>;
            
            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.selectedWorkplaceId = ko.observable("");
                self.baseDate = ko.observable(new Date());
                self.workplaceAlreadySetList = ko.observableArray([]);
                self.workplaceGrid = {
                        isShowAlreadySet: true,
                        isMultiSelect: false,
                        treeType: ListType.WORK_PLACE,
                        selectedWorkplaceId: self.selectedWorkplaceId,
                        baseDate: self.baseDate,
                        selectType: SelectionType.SELECT_FIRST_ITEM,
                        isShowSelectButton: false,
                        isDialog: false,
                        alreadySettingList: self.workplaceAlreadySetList,
                        maxRows: 10
                };
                
                self.selectedClassifi = ko.observable("");
                self.classifiAlreadySetList = ko.observableArray([]);
                self.classificationGrid = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    listType: ListType.CLASSIFICATION,
                    selectType: SelectionType.SELECT_FIRST_ITEM,
                    selectedCode: self.selectedClassifi,
                    isDialog: false,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.classifiAlreadySetList,
                    maxRows: 12
                };
                
                // Initialize on Company Tab
                self.companyBWWorkingDay = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.WORKING_DAY, worktypeCode: 'ComWorkTypeCd1', worktypeDisplayName: 'ComWorkTypeName1', workingCode: 'ComWorkingCd1', workingDisplayName: 'WorkingDisplayName1' }
                );
                self.companyBWNonInLaw = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_INLAW, worktypeCode: 'ComWorkTypeCd2', worktypeDisplayName: 'ComWorkTypeName2', workingCode: 'ComWorkingCd2', workingDisplayName: 'WorkingDisplayName2' }
                );
                self.companyBWNonExtra = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_EXTR, worktypeCode: 'ComWorkTypeCd3', worktypeDisplayName: 'ComWorkTypeName3', workingCode: 'ComWorkingCd3', workingDisplayName: 'WorkingDisplayName3' }
                );
                
                // Initialize on Workplace Tab
                self.workplaceBWWorkingDay = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.WORKING_DAY, worktypeCode: 'WorkTypeCode1', worktypeDisplayName: 'WorkTypeName1', workingCode: 'workingCode1', workingDisplayName: 'WorkingDisplayName1' }
                );
                self.workplaceBWNonInLaw = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_INLAW, worktypeCode: 'WorkTypeCode2', worktypeDisplayName: 'WorkTypeName2', workingCode: 'workingCode2', workingDisplayName: 'WorkingDisplayName2' }
                );
                self.workplaceBWNonExtra = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_EXTR, worktypeCode: 'WorkTypeCode3', worktypeDisplayName: 'WorkTypeName3', workingCode: 'workingCode3', workingDisplayName: 'WorkingDisplayName3' }
                );
                
                // Initialize on Classification Tab
                self.classifyBWWorkingDay = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.WORKING_DAY, worktypeCode: 'WorkTypeCode1', worktypeDisplayName: 'WorkTypeName1', workingCode: 'workingCode1', workingDisplayName: 'WorkingDisplayName1' }
                );
                self.classifyBWNonInLaw = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_INLAW, worktypeCode: 'WorkTypeCode2', worktypeDisplayName: 'WorkTypeName2', workingCode: 'workingCode2', workingDisplayName: 'WorkingDisplayName2' }
                );
                self.classifyBWNonExtra = ko.observable<BasicWorkModel>(
                    { division: WorkingDayDivision.NON_WORK_EXTR, worktypeCode: 'WorkTypeCode3', worktypeDisplayName: 'WorkTypeName3', workingCode: 'workingCode3', workingDisplayName: 'WorkingDisplayName3' }
                );
                
                self.isShowCompanyTab = ko.observable(true);
                self.isShowWorkplaceTab = ko.observable(false);
                self.isShowClassifyTab = ko.observable(false);
                
                self.isUpdateModeWorkplace = ko.computed(function() {
                    var existItem = self.workplaceAlreadySetList().filter((item) => {
                                return item.workplaceId == self.selectedWorkplaceId();
                            })[0];
                    if (existItem) {
                        return true;    
                    } else {
                        return false;    
                    }
                });
                
                self.isUpdateModeClassify = ko.computed(function() {
                    var existItem = self.classifiAlreadySetList().filter((item) => {
                        return item.code == self.selectedClassifi();
                    })[0];
                    if (existItem) {
                        return true;    
                    } else {
                        return false;    
                    }
                });
                

            }
            
            // Start Page
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                
                self.findCompanyBasicWork().done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                
                return dfd.promise();
            }
            
            
            // Find CompanyBasicWork
            private findCompanyBasicWork(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findCompanyBasicWork().done(function(data: CompanyBasicWorkFindDto) {
                    if (data == null) {
                        self.companyBWWorkingDay(new BasicWorkModel(null, null, null, null));
                        self.companyBWNonInLaw(new BasicWorkModel(null, null, null, null));
                        self.companyBWNonExtra(new BasicWorkModel(null, null, null, null));
                    } else {
                        // ForEach BasicWorkSetting
                        data.basicWorkSetting.forEach(function(item, index) {                            
                            switch (item.workDayDivision) {
                                case WorkingDayDivision.WORKING_DAY:
                                    self.companyBWWorkingDay(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_INLAW:
                                    self.companyBWNonInLaw(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_EXTR: 
                                    self.companyBWNonExtra(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                            }
                        });
                        self.companyId = data.companyId;
                    }
                    $('#focus-btn').focus();
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            // Find WorkplaceBasicWork by WorkplaceId
            private findWorkplaceBasicWork(workplaceId: string): JQueryPromise<any>  {
                let self = this;
                let dfd = $.Deferred();
                service.findWorkplaceBasicWork(workplaceId).done(function(data: WorkplaceBasicWorkFindDto) {
                    if (data == null) {
                        self.companyBWWorkingDay(new BasicWorkModel(null, null, null, null));
                        self.companyBWNonInLaw(new BasicWorkModel(null, null, null, null));
                        self.companyBWNonExtra(new BasicWorkModel(null, null, null, null));
                    } else {
                        data.basicWorkSetting.forEach(function(item, index) {                            
                            switch (item.workDayDivision) {
                                case WorkingDayDivision.WORKING_DAY:
                                    self.companyBWWorkingDay(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_INLAW:
                                    self.companyBWNonInLaw(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_EXTR: 
                                    self.companyBWNonExtra(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                            }
                        });
                        self.companyId = data.companyId;
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            
            
            
            // Register Basic Work By Company
            registerByCompany(): void { 
                var self = this;
                service.saveCompanyBasicWork(self.collectCompanyData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            //Register Basic Work By Workplace
            registerByWorkplace(): void {
                var self = this;
                if (self.selectedWorkplaceId == undefined) {
                     nts.uk.ui.dialog.info({ messageId: "Msg_339" });
                } else {
                    // Check Worktype, Pair WorkType-WorkingHours
                }
                service.saveWorkplaceBasicWork(self.collectWorkplaceData()).done(function() {
                    // Set AlreadySetting
                    self.workplaceAlreadySetList.push(new UnitAlreadySettingModel(self.selectedWorkplaceId(), true));
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    //reload
                    $('#workplace-list').ntsTreeComponent(self.workplaceGrid);
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            // Register Basic Work By Classification
            registerByClassification(): void {
                var self = this;
                service.saveClassifyBasicWork(self.collectClassifyData()).done(function() { 
                    // Set AlreadySetting
                    self.classifiAlreadySetList.push({ "code": self.selectedClassifi(), "isAlreadySetting": true });  
                                    
                    // Reload 
                    $('#classification-list').ntsListComponent(self.classificationGrid);
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res.message);
                });
            }
            
            // Remove Classification Basic Work
            removeByClassification(): void {
                 var self = this;
                
                 nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_35" });
                     service.removeClassifyBasicWork(self.collectClassifyData()).done(function() {
                         nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                         // Remove AlreadySetting from Classification BW Grid List 
                         self.classifiAlreadySetList.remove(self.classifiAlreadySetList().filter((item) => {
                             return item.code == self.selectedClassifi();
                         })[0]);
                         
                         // Reload new mode
                          $('#classification-list').ntsListComponent(self.classificationGrid);
                     }).fail((res) => {
                         nts.uk.ui.dialog.alertError(res.message);
                     });
                 }).ifNo(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_36" });
                 });
                
               
            }
            
            // Remove Workplace Basic Work
            removeByWorkplace(): void {
                 var self = this;
                 nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_35" });
                     service.removeWorkplaceBasicWork(self.collectWorkplaceData()).done(function() {
                         nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                         // Remove AlreadySetting from Workplace BW Grid List
                         // Remove AlreadySetting from Classification BW Grid List 
                         self.workplaceAlreadySetList.remove(self.workplaceAlreadySetList().filter((item) => {
                             return item.workplaceId == self.selectedWorkplaceId();
                         })[0]);
                         
                         //reload new mode
                         $('#workplace-list').ntsTreeComponent(self.workplaceGrid);
                     }).fail((res) => {
                         nts.uk.ui.dialog.alertError(res.message);
                     });
                 }).ifNo(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_36" });
                 });
            }
            
            // Switch To Company Tab
            switchToCompanyTab(): void { 
                var self = this;
                self.isShowCompanyTab(true);
                self.isShowClassifyTab(false);
                self.isShowWorkplaceTab(false);
                self.findCompanyBasicWork();
                $('#focus-btn').focus();
            }
            
            // SwitchTo Workplace Tab
            switchToWorkplaceTab(): void {
                var self = this;
                self.isShowWorkplaceTab(true);
                self.isShowCompanyTab(false);
                self.isShowClassifyTab(false);
                // Find ardeadySetting
                service.findWorkplaceSetting().done(function(data: any) {
                    for (var i = 0; i < data.length; i++) {
                        self.workplaceAlreadySetList.push(new UnitAlreadySettingModel(data[i], true));
                    }
                });
                 
                // Selected Item subscribe
                self.selectedWorkplaceId.subscribe(function(data: string) {
                    if (data) {
                        // Find EmploymentSetting By employment
                        service.findWorkplaceBasicWork(data).done(function(data1: WorkplaceBasicWorkFindDto) {
                            if (data != undefined) {
                                self.bindWorkplaceBasicWork(data1);
                            }
                        });
                    }

                });
                // Reload Workplace List
                $('#workplace-list').ntsTreeComponent(self.workplaceGrid).done(() => $('#workplace-list').focus());
            }
            
            // Switch To Classification Tab
            switchToClassTab(): void { 
                var self = this;
                self.isShowClassifyTab(true);
                self.isShowCompanyTab(false);
                self.isShowWorkplaceTab(false);
                // Find ardeadySetting
                service.findClassifySetting().done(function(data: any) {
                    for (var i = 0; i < data.length; i++) {
                        self.classifiAlreadySetList.push({ "code": data[i], "isAlreadySetting": true });
//                        self.classifiAlreadySetList.push(new UnitAlreadySettingModel(data[i], true)); 
                    }
//                    self.classifiAlreadySetList(data);
                })
                // Reload Classification List
                $('#classification-list').ntsListComponent(self.classificationGrid).done(() => $('#classification-list').focus());
                
                // SelectedClassification Subscribe
                self.selectedClassifi.subscribe(function(data: string) {
                    if (data) {
                        // Find EmploymentSetting By employment
                        service.findClassifyBasicWork(data).done(function(data1: ClassifiBasicWorkFindDto) {
                            if (data != undefined) {
                                self.bindClassifyBasicWork(data1);
                            }
                        });
                    }
                    
                });
                
            }
            
            // Bind Classification Basic Work
            private bindClassifyBasicWork(data: ClassifiBasicWorkFindDto): void {
                var self = this;
                if (data == undefined) {
                    self.classifyBWWorkingDay(new BasicWorkModel('no', 'no', 'no', 'no'));
                    self.classifyBWNonInLaw(new BasicWorkModel('no', 'no', 'no', 'no'));
                    self.classifyBWNonExtra(new BasicWorkModel('no', 'no', 'no', 'no'));
                } else {
                    data.basicWorkSetting.forEach(function(item, index) {
                        switch (item.workDayDivision) {
                            case WorkingDayDivision.WORKING_DAY:
                                self.classifyBWWorkingDay(new BasicWorkModel(item.workTypeCode,
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                break;

                            case WorkingDayDivision.NON_WORK_INLAW:
                                self.classifyBWNonInLaw(new BasicWorkModel(item.workTypeCode,
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                break;

                            case WorkingDayDivision.NON_WORK_EXTR:
                                self.classifyBWNonExtra(new BasicWorkModel(item.workTypeCode,
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                break;
                        }
                    });
                    self.selectedClassifi(data.classificationCode);
                }
            }
            
            // Bind Workplace Basic Work
            private bindWorkplaceBasicWork(data: WorkplaceBasicWorkFindDto): void {
                var self = this;
                if (data == undefined) {
                        self.workplaceBWWorkingDay(new BasicWorkModel(null, null, null, null));
                        self.workplaceBWNonInLaw(new BasicWorkModel(null, null, null, null));
                        self.workplaceBWNonExtra(new BasicWorkModel(null, null, null, null));
                    } else {
                        data.basicWorkSetting.forEach(function(item, index) {                            
                            switch (item.workDayDivision) {
                                case WorkingDayDivision.WORKING_DAY:
                                    self.workplaceBWWorkingDay(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_INLAW:
                                    self.workplaceBWNonInLaw(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                                    
                                case WorkingDayDivision.NON_WORK_EXTR: 
                                    self.workplaceBWNonExtra(new BasicWorkModel(item.workTypeCode, 
                                    item.workTypeDisplayName, item.workingCode, item.workingDisplayName));
                                    break;
                            }
                        });
                        self.selectedWorkplaceId(data.workplaceId);
                    }
            }
            
            // Go to KDL003
            private gotoDialog(): void {
                var self = this;
//                nts.uk.ui.windows.setShared("workTypeId",self.selectedWorktype());
                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml");
            }
            
            // collect company Basic Work data to save
            private collectCompanyData(): CompanyBasicWorkDto {
                var self = this;
                var dto: CompanyBasicWorkDto = new CompanyBasicWorkDto();
//                dto.companyId = self.companyId;                
                var basicWorkSettingArray: Array<BasicWorkSettingDto> =[];
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, self.companyBWWorkingDay().worktypeCode, self.companyBWWorkingDay().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_INLAW, self.companyBWNonInLaw().worktypeCode, self.companyBWNonInLaw().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_EXTR, self.companyBWNonExtra().worktypeCode, self.companyBWNonExtra().workingCode));
                
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, '004', '004'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, '002', '002'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, '003', '003'));
                dto.basicWorkSetting = basicWorkSettingArray;
                return dto;
            }
            
            // collect Workplace Basic Work data to save
            private collectWorkplaceData(): WorkplaceBasicWorkDto {
                var self = this;
                var dto: WorkplaceBasicWorkDto = new WorkplaceBasicWorkDto();
                dto.workplaceId = self.selectedWorkplaceId();
                var basicWorkSettingArray: Array<BasicWorkSettingDto> =[];
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, self.workplaceBWWorkingDay().worktypeCode, self.workplaceBWWorkingDay().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_INLAW, self.workplaceBWNonInLaw().worktypeCode, self.workplaceBWNonInLaw().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_EXTR, self.workplaceBWNonExtra().worktypeCode, self.workplaceBWNonExtra().workingCode));
                
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, '001', '001'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_INLAW, '002', '002'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_EXTR, '003', '003'));
               
                dto.basicWorkSetting = basicWorkSettingArray;
                return dto;
            }
            
            // collect Classification Basic Work data to save
            private collectClassifyData(): ClassificationBasicWorkDto {
                var self = this;
                var dto: ClassificationBasicWorkDto = new ClassificationBasicWorkDto();
                dto.classificationCode = self.selectedClassifi();  
                var basicWorkSettingArray: Array<BasicWorkSettingDto> =[];
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, self.classifyBWWorkingDay().worktypeCode, self.classifyBWWorkingDay().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_INLAW, self.classifyBWNonInLaw().worktypeCode, self.classifyBWNonInLaw().workingCode));
//                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_EXTR, self.classifyBWNonExtra().worktypeCode, self.classifyBWNonExtra().workingCode));
                
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.WORKING_DAY, '004', '004'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_INLAW, '005', '005'));
                basicWorkSettingArray.push(new BasicWorkSettingDto(WorkingDayDivision.NON_WORK_EXTR, '006', '006'));
                dto.basicWorkSetting = basicWorkSettingArray;
                return dto;
            }
        }
        
        // BasicWork Model
        export class BasicWorkModel {
            division: number;
            worktypeCode: string;
            worktypeDisplayName: string;
            workingCode: string;
            workingDisplayName: string;
            
            constructor (worktypeCode: string, worktypeDisplayName: string, workingCode: string, workingDisplayName: string) {
                this.worktypeCode = worktypeCode;
                this.worktypeDisplayName = worktypeDisplayName;
                this.workingCode = workingCode;
                this.workingDisplayName = workingDisplayName;
            }
        }
        
        export class WorkingDayDivision {
            static WORKING_DAY = 0;
            static NON_WORK_INLAW = 1;
            static NON_WORK_EXTR = 2;
        }
        
        export class UnitAlreadySettingModel {
            workplaceId: string;
            isAlreadySetting: boolean;
            
            constructor(code: string, isAlreadySetting: boolean) {
                this.workplaceId = code;
                this.isAlreadySetting = isAlreadySetting;
            }
        }
        
        export class ListType {
            static WORK_PLACE = 1;
            static CLASSIFICATION = 2;
        }
        
        export class SelectionType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
    }
}