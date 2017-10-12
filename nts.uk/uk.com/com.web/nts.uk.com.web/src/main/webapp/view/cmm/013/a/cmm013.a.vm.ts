module nts.uk.com.view.cmm013.a {

    export module viewmodel {
        
        import Constants = base.Constants;
    
        import JobTitleHistoryAbstract = base.JobTitleHistoryAbstract;
        import History = base.History;
        
        import JobTitle = base.JobTitle;
        import SequenceMaster = base.SequenceMaster;
        
        export class ScreenModel {
                                     
            listJobTitleOption: ComponentOption;
                     
            baseDate: KnockoutObservable<Date>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            selectedJobTitleId: KnockoutObservable<string>;
            multiselectedJobTitleId: KnockoutObservableArray<string>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;           
                        
            jobTitleList: KnockoutObservableArray<UnitModel>;
            
            
            
            createMode: KnockoutObservable<boolean>;
            
            jobTitleHistoryModel: KnockoutObservable<JobTitleHistoryModel>;
            
            jobTitleCode: KnockoutObservable<string>;
            jobTitleName: KnockoutObservable<string>;
            sequenceCode: KnockoutObservable<string>;
            sequenceName: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;               
                
                _self.jobTitleHistoryModel = ko.observable(new JobTitleHistoryModel(_self));
    
                _self.createMode = ko.observable(false);
                _self.createMode.subscribe((newValue) => {
                    _self.startCreate(newValue);
                });
                
                // Init list JobTitle setting
                _self.baseDate = ko.observable(new Date()); 

                _self.selectedJobTitleId = ko.observable("");
                _self.selectedJobTitleId.subscribe((newValue) => {
                    _self.findJobHistoryById(newValue);
                });        
                _self.isShowAlreadySet = ko.observable(false);
                _self.isShowAlreadySet.subscribe(() => {
                    _self.reloadComponent();
                });           
                _self.isMultiSelect = ko.observable(false);
                _self.isMultiSelect.subscribe(() => {
                    _self.reloadComponent();
                });
                _self.isShowNoSelectRow = ko.observable(false);
                _self.isShowNoSelectRow.subscribe(() => {
                    _self.reloadComponent();
                });
                _self.multiselectedJobTitleId = ko.observableArray([]);                       
                _self.alreadySettingList = ko.observableArray([]);           
                
                _self.listJobTitleOption = {
                    baseDate: _self.baseDate,
                    isShowAlreadySet: _self.isShowAlreadySet(),
                    isMultiSelect: _self.isMultiSelect(),
                    listType: 3,
                    selectType: 1,
                    selectedCode: _self.selectedJobTitleId,
                    isDialog: false,
                    isShowNoSelectRow: _self.isShowNoSelectRow(),
                    alreadySettingList: _self.alreadySettingList,
                    maxRows: 12
                };
                
                _self.jobTitleList = ko.observableArray<UnitModel>([]);
                
                // Init JobTitle form
                _self.jobTitleCode = ko.observable("");
                _self.jobTitleName = ko.observable("");          
                _self.sequenceCode = ko.observable("");  
                _self.sequenceName = ko.observable("");    
            }
    
            /**
             * Reload component
             */
            private reloadComponent() {
                let _self = this;
                
                _self.listJobTitleOption.isShowAlreadySet = _self.isShowAlreadySet();
                _self.listJobTitleOption.isMultiSelect = _self.isMultiSelect();
                _self.listJobTitleOption.isShowNoSelectRow = _self.isShowNoSelectRow();
                _self.listJobTitleOption.alreadySettingList = _self.alreadySettingList;
                
                _self.listJobTitleOption.selectedCode = _self.isMultiSelect() ? _self.multiselectedJobTitleId : _self.selectedJobTitleId;
                
                $('#job-title-items-list').ntsListComponent(_self.listJobTitleOption);
            }
            
            startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                //TODO: Switch mode
                _self.isMultiSelect(false);
                _self.reloadComponent();
                
                //TODO: Get JobTitle data by date                                
                //TODO: mocked JobTitle
                _self.selectedJobTitleId("000000000000000000000000000000000001");
                
                //TODO: Apply data to list JobTitle                
                
                dfd.resolve();
                return dfd.promise();
            }
            
            startCreate(newValue: boolean) {
                let _self = this;
                
                if (newValue === true) {
                    _self.jobTitleHistoryModel().listJobTitleHistory.removeAll();
                    //_self.jobTitleHistoryModel().listJobTitleHistory.push(new History());
                    _self.jobTitleHistoryModel().selectFirst();
                    _self.jobTitleCode = ko.observable("");
                    _self.jobTitleName = ko.observable("");          
                    _self.sequenceCode = ko.observable("");  
                    _self.sequenceName = ko.observable("");
                } else {
                        
                }               
            }
            
            /**
             * Find JobTitle history by id
             */
            private findJobHistoryById(jobTitleId: string) {
                let _self = this;    
                           
                // Load JobTitle history info 
                service.findJobHistoryList(jobTitleId)
                    .done((data: any) => {
                        if (data) {
                            // Load JobTitle History
                            let listHistory: History[] = _.map(data.jobTitleHistory, (item: any) => {
                                return new History(data.jobTitleId, item.historyId, item.period);
                            });
                            _self.jobTitleHistoryModel().init(listHistory);   
                        }                                           
                    })
                    .fail((res: any) => {
                        
                    }); 
            }          
            
            /**
             * Find JobTitle info by job title id and job history id
             */
            public findJobInfo(jobTitleId: string, jobHistoryId: string) {
                let _self = this;    
                
                service.findJobInfoByJobIdAndHistoryId(jobTitleId, jobHistoryId)
                    .done((data: any) => {
                        if (data) {
                            _self.jobTitleCode(data.jobTitleCode);
                            _self.jobTitleName(data.jobTitleName);
                            _self.sequenceCode(data.sequenceCode);
                            _self.sequenceName(data.sequenceName);
                        }
                    })
                    .fail((res: any) => {
                        
                    });                
            }
            
            
            
            
            
            // Load Dialog
            /**
             * Screen B - openDeleteDialog
             */
            public openDeleteDialog() {
                let _self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/013/b/index.xhtml').onClosed(() => {});
            }
            
            /**
             * Screen C - openSelectSequenceDialog
             */
            public openSelectSequenceDialog() {
                let _self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml').onClosed(() => {
                    let dialogData: SequenceMaster = nts.uk.ui.windows.getShared(Constants.SHARE_OUT_DIALOG_SELECT_SEQUENCE);
                    if (!dialogData) {
                        _self.sequenceCode("");
                        _self.sequenceName("");
                        return;
                    }
                    _self.sequenceCode(dialogData.sequenceCode);
                    _self.sequenceName(dialogData.sequenceName);
                });   
            }
            
            /**
             * Screen D - openAddHistoryDialog
             */
            public openAddHistoryDialog() {
                let _self = this;
                nts.uk.ui.windows.setShared(Constants.SHARE_IN_DIALOG_ADD_HISTORY, _self.selectedJobTitleId());
                nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml').onClosed(() => {
                    let isSuccess: boolean = nts.uk.ui.windows.getShared(Constants.SHARE_OUT_DIALOG_ADD_HISTORY);                 
                    if (isSuccess) {
                        // Reload history
                        //TODO
                        _self.findJobHistoryById(_self.selectedJobTitleId());
                    }
                });   
            }
            
            /**
             * Screen E - openUpdateHistoryDialog
             */
            public openUpdateHistoryDialog() {
                let _self = this;
                let transferObj: any = {};
                transferObj.jobTitleId = _self.selectedJobTitleId();
                transferObj.historyId = _self.jobTitleHistoryModel().selectedHistoryId();
                transferObj.startDate = _self.jobTitleHistoryModel().getSelectedHistoryByHistoryId().period.startDate;
                nts.uk.ui.windows.setShared(Constants.SHARE_IN_DIALOG_EDIT_HISTORY, transferObj);
                nts.uk.ui.windows.sub.modal('/view/cmm/013/e/index.xhtml').onClosed(() => {
                    let isSuccess: boolean = nts.uk.ui.windows.getShared(Constants.SHARE_OUT_DIALOG_EDIT_HISTORY);                 
                    if (isSuccess) {
                        // Reload history
                        //TODO
                        _self.findJobHistoryById(_self.selectedJobTitleId());
                    }    
                });   
            }
            
            /**
             * Screen F - openSequenceManageDialog
             */
            public openSequenceManageDialog() {
                let _self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/013/f/index.xhtml').onClosed(() => {});   
            }
        }
        
        /**
         * JobTitleHistoryModel
         */
        class JobTitleHistoryModel extends JobTitleHistoryAbstract {
            
            parentModel : ScreenModel;
            
            constructor(parentModel : ScreenModel) {
                super();
                let _self = this;
                _self.parentModel = parentModel;
                _self.selectedHistoryId.subscribe((jobHistoryId: string) => {
                    _self.parentModel.findJobInfo(_self.parentModel.selectedJobTitleId(), jobHistoryId);
                });
                _self.init([]);
            }
            
            public init(data: History[]) {
                let _self = this;
                _self.listJobTitleHistory(data);
                _self.selectFirst();
            }
        }
    }
}