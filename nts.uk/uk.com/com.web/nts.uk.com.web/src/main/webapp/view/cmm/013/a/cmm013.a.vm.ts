module nts.uk.com.view.cmm013.a {

    export module viewmodel {
    
        import JobTitleHistoryAbstract = base.JobTitleHistoryAbstract;
        
        export class ScreenModel {
            
            jobTitleHistoryModel: KnockoutObservable<JobTitleHistoryModel>;
            
            createMode: KnockoutObservable<boolean>;
            
            listJobTitleOption: ComponentOption;
            
            baseDate: KnockoutObservable<Date>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            jobTitleSelectedId: KnockoutObservable<string>;
            multiJobTitleSelectedId: KnockoutObservableArray<string>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;           
            
            jobTitleList: KnockoutObservableArray<UnitModel>;
            
            jobTitleCode: KnockoutObservable<string>;
            jobTitleName: KnockoutObservable<string>;
            sequenceCode: KnockoutObservable<string>;
            sequenceDisplayText: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;               
                
                _self.jobTitleHistoryModel = ko.observable(new JobTitleHistoryModel(_self));
    
                _self.createMode = ko.observable(false);
                _self.createMode.subscribe((newValue) => {
                    _self.startCreate(newValue);
                });
                
                // Init list JobTitle setting
                _self.baseDate = ko.observable(new Date()); 
                _self.jobTitleSelectedId = ko.observable("");
                _self.jobTitleSelectedId.subscribe((newValue) => {
                    _self.loadJobTitleById(newValue);
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
                _self.multiJobTitleSelectedId = ko.observableArray([]);                       
                _self.alreadySettingList = ko.observableArray([]);           
                
                _self.listJobTitleOption = {
                    baseDate: _self.baseDate,
                    isShowAlreadySet: _self.isShowAlreadySet(),
                    isMultiSelect: _self.isMultiSelect(),
                    listType: 3,
                    selectType: 1,
                    selectedCode: _self.jobTitleSelectedId,
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
                _self.sequenceDisplayText = ko.observable("01 役員");    
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
                
                _self.listJobTitleOption.selectedCode = _self.isMultiSelect() ? _self.multiJobTitleSelectedId : _self.jobTitleSelectedId;
                
                $('#job-title-items-list').ntsListComponent(_self.listJobTitleOption);
            }
            
            startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                //TODO: Switch mode
                _self.isMultiSelect(false);
                _self.reloadComponent();
                
                //TODO: Get JobTitle data by date
                
                
                //TODO: Get SequenceCode data
                
                
                //TODO: Apply data to list JobTitle
                
                
                dfd.resolve();
                return dfd.promise();
            }
            
            startCreate(newValue: boolean) {
                let _self = this;
                
                if(newValue === true) {
                    
                } else {
                    _self.jobTitleHistoryModel().listJobTitleHistory.removeAll();
                    //_self.jobTitleHistoryModel().listJobTitleHistory.push(new History());
                    _self.jobTitleHistoryModel().selectFirst();
                    _self.jobTitleCode = ko.observable("");
                    _self.jobTitleName = ko.observable("");          
                    _self.sequenceCode = ko.observable("");  
                }               
            }
            
            loadJobTitleById(jobTitleId: string) {
                let _self = this;               
                //TODO: load new JobTitle info
                
                console.log(_self.jobTitleList());
            }          
            
            
            
            // Load Dialog
            openDeleteDialog() {
                let _self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/013/b/index.xhtml').onClosed(() => {});
            }
            
            openSelectSequenceDialog() {
                let _self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml').onClosed(() => {});   
            }
            
            openSequenceManageDialog() {
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
                _self.init([]);
            }
            
            init(data: any) {
                let _self = this;
                _self.listJobTitleHistory(data);
                _self.selectFirst();
            }
        }
    }
}