module nts.uk.at.view.ksm006.a {
 import CompanyBasicWorkDto = service.model.CompanyBasicWorkDto;
    export module viewmodel {
        export class ScreenModel {
            isShowEmployment : KnockoutObservable<boolean>;
            selectedWorkplaceId: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            workplaceAlreadySetList: KnockoutObservableArray<UnitAlreadySettingModel>;
            workplaceList: KnockoutObservableArray<UnitModel>;
            workplaceGrid: any;
            
            classificationGrid: any;
            selectedClassifi: KnockoutObservable<string>;
            classificationList: KnockoutObservableArray<UnitModel>;
            classifiAlreadySetList: KnockoutObservableArray<UnitAlreadySettingModel>;
            
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
            
            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.isShowEmployment = ko.observable(true);
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
                
//                $('#workplace-list').ntsTreeComponent(self.workplaceGrid);
//                $('#classification-list').ntsListComponent(self.classificationGrid);

                return dfd.promise();
            }
            
            
            // Find CompanyBasicWork
            private findCompanyBasicWork(): JQueryPromise<any>  {
                let self = this;
                let dfd = $.Deferred();
                service.findCompanyBasicWork().done(function(data: CompanyBasicWorkDto) {
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
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
                return dfd.promise();
            }
            

            registerByCompany() { }
            switchToCompanyTab() { }
            switchToWorkplaceTab() { }
            switchToClassTab() { }
            
            private gotoDialog(): void {
                var self = this;
//                nts.uk.ui.windows.setShared("workTypeId",self.selectedWorktype());
                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml");
            }
        }
        
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
            code: string;
            isAlreadySetting: boolean;
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