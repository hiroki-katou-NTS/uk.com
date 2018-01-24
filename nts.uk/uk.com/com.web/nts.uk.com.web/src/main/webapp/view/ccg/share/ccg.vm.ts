module nts.uk.com.view.ccg.share.ccg {

    import ListType = kcp.share.list.ListType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import EmployeeSearchDto = service.model.EmployeeSearchDto;
    import GroupOption = service.model.GroupOption;
    import EmployeeSearchInDto = service.model.EmployeeSearchInDto;


    export module viewmodel {
        
        /**
        * Screen Model.
        */
        
        export class ListGroupScreenModel {
            isMultiple: boolean;
            isQuickSearchTab: boolean;
            isAdvancedSearchTab: boolean;
            isAllReferableEmployee: boolean;
            isOnlyMe: boolean;
            isEmployeeOfWorkplace: boolean;
            isEmployeeWorkplaceFollow: boolean;
            isSelectAllEmployee: boolean;
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selectedCodeEmployment: KnockoutObservableArray<string>;
            selectedCodeClassification: KnockoutObservableArray<string>;
            selectedCodeJobtitle: KnockoutObservableArray<string>;
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            selectedCodeEmployee: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            employments: any;
            classifications: any;
            jobtitles: any;
            workplaces: TreeComponentOption;
            employeeinfo: any;
            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;
            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
            isShow: KnockoutObservable<boolean>;
            isFistTimeShow: boolean;
            isOpenEmploymentList: KnockoutObservable<boolean>;
            isOpenClassificationList: KnockoutObservable<boolean>;
            isOpenJoptitleList: KnockoutObservable<boolean>;
            isOpenWorkplaceList: KnockoutObservable<boolean>;
            /**
             * Init screen model
             */
            constructor() {
                var self = this;
                self.selectedCodeEmployment = ko.observableArray([]);
                self.selectedCodeClassification = ko.observableArray([]);
                self.selectedCodeJobtitle = ko.observableArray([]);
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.selectedCodeEmployee = ko.observableArray([]);
                self.baseDate = ko.observable(new Date());
                self.tabs = ko.observableArray([
                    {
                        id: 'tab-1',
                        title: nts.uk.resource.getText("CCG001_3"),
                        content: '.tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    },
                    {
                        id: 'tab-2',
                        title: nts.uk.resource.getText("CCG001_4"),
                        content: '.tab-content-2',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    }
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.reloadDataSearch();
                self.isShow = ko.observable(false);
                self.isFistTimeShow = true;
                self.isOpenEmploymentList = ko.observable(false);
                self.isOpenClassificationList = ko.observable(false);
                self.isOpenJoptitleList = ko.observable(false);
                self.isOpenWorkplaceList = ko.observable(false);
            }

            /**
             * update select tabs
             */
             
            public updateTabs(): NtsTabPanelModel[] {
                var self = this;
                var arrTabs: NtsTabPanelModel[] = [];
                // is quick search tab
                if (self.isQuickSearchTab) {
                    // push tab 1
                    arrTabs.push({
                        id: 'tab-1',
                        title: nts.uk.resource.getText("CCG001_3"),
                        content: '.tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                // is advanced search tab
                if (self.isAdvancedSearchTab) {
                    // push tab 2
                    arrTabs.push({
                        id: 'tab-2',
                        title: nts.uk.resource.getText("CCG001_4"),
                        content: '.tab-content-2',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                // => data res
                return arrTabs;
            }

            /**
             * get tab by update selected 
             */

            public updateSelectedTab(): string {
                var self = this;
                // res tab 1
                if (self.isQuickSearchTab) {
                    return 'tab-1';
                }
                // res tab 2
                if (self.isAdvancedSearchTab) {
                    return 'tab-2';
                }
                // res none tab
                return '';
            }

            /**
             * init next tab
             */
            public initNextTabFeature() {
                var self = this;
                // Auto next tab when press tab key.
                $('#tab-2').find('#EmploymentList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenEmploymentList(!self.isOpenEmploymentList());
                });
                $('#tab-2').find('#ClassificationList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenClassificationList(!self.isOpenClassificationList());
                });
                $('#tab-2').find('#JoptitleList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenJoptitleList(!self.isOpenJoptitleList());
                });
                $('#tab-2').find('#WorkplaceList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenWorkplaceList(!self.isOpenWorkplaceList());
                });
                $("[tabindex='6']").on('keydown', function(e) {
                    if (e.which == 9 && self.isAdvancedSearchTab) {
                        self.selectedTab('tab-2');
                        if (!self.isOpenEmploymentList()) {
                            $('#tab-2').find('#EmploymentList').find('.ui-accordion-header').click();
                        }
                        $("[tabindex='7']").on('keydown', function(e) {
                            if (e.which == 9) {
                                if (!self.isOpenClassificationList()) {
                                    $('#tab-2').find('#ClassificationList').find('.ui-accordion-header').click();
                                }
                            }
                            $("[tabindex='8']").on('keydown', function(e) {
                                if (e.which == 9) {
                                    if (!self.isOpenJoptitleList()) {
                                        $('#tab-2').find('#JoptitleList').find('.ui-accordion-header').click();
                                    }
                                }
                                $("[tabindex='9']").on('keydown', function(e) {
                                    if (e.which == 9) {
                                        if (!self.isOpenWorkplaceList()) {
                                            $('#tab-2').find('#WorkplaceList').find('.ui-accordion-header').click();
                                        }
                                    }
                                });
                            });
                        });
                    }
                });
                $("[tabindex='2']").on('keydown', function(e) {
                    if (e.which == 9 && self.selectedTab() == 'tab-2' && !$(e.target).parents("[tabindex='2']")[0]) {
                        self.selectedTab('tab-1');
                    }
                });
                
            }

            
            /**
             * Init component.
             */
            
            public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
                // init data sample
                self.isMultiple = data.isMutipleCheck;
                self.isQuickSearchTab = data.isQuickSearchTab;
                self.isAdvancedSearchTab = data.isAdvancedSearchTab;
                self.isAllReferableEmployee = data.isAllReferableEmployee;
                self.isOnlyMe = data.isOnlyMe;
                self.isEmployeeOfWorkplace = data.isEmployeeOfWorkplace;
                self.isEmployeeWorkplaceFollow = data.isEmployeeWorkplaceFollow;
                self.isSelectAllEmployee = data.isSelectAllEmployee;
                self.onSearchAllClicked = data.onSearchAllClicked;
                self.onSearchOnlyClicked = data.onSearchOnlyClicked;
                self.onSearchOfWorkplaceClicked = data.onSearchOfWorkplaceClicked;
                self.onSearchWorkplaceChildClicked = data.onSearchWorkplaceChildClicked;
                self.onApplyEmployee = data.onApplyEmployee;
                self.baseDate = data.baseDate;
                self.tabs(self.updateTabs());
                self.selectedTab(self.updateSelectedTab());
                
                // init view
                var webserviceLocator = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/ccg/share/index.xhtml').serialize();
                $input.load(webserviceLocator, function() {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                    self.applyDataSearch();
                    self.initNextTabFeature();
                    
                    // Set tabindex ro button show component.
                    var tabindex = $input.attr('tabindex');
                    $input.attr('tabindex', -1);
                    $input.find('.btn_showhide').attr('tabindex', tabindex);
                    dfd.resolve();
                });
                $('#ccg-component').outerHeight($('#contents-area').outerHeight());
                
                // init function click button ccg common
                $(window).on('click', function(e) {
                    // Check is click to outter component.
                    if (e.target.id == "ccg-component" || $(e.target).parents("#ccg-component")[0]) {
                        return;
                    }
                    // Check is click to dialog.
                    if ($(e.target).parents("[role='dialog']")[0]) {
                        return;
                    }
                    // Check is click to ignite combo-box.
                    if ($(e.target).parents().hasClass('ui-igcombo-dropdown')) {
                        return;
                    }
                    if (e.target.id == "hor-scroll-button-hide" || $(e.target).parents("#hor-scroll-button-hide")[0]) {
                        return;
                    }
                    if (self.isShow()) {
                        // Hide component.
                        self.isShow(false);
                        $('#ccg-component').toggle("slide");
                    }
                });
                return dfd.promise();
            }
            
            /**
             * show hide div ccg common
             */
            showHide() {
                // Show component.
                var self = this;
                if (self.isShow()) {
                    return;
                }
                self.isShow(true);
                $('#ccg-component').toggle("slide", function() {
                    if (self.isFistTimeShow) {
                        self.applyDataSearch();
                        self.isFistTimeShow = false;
                    }
                });
            }

            /**
             * function click by search all employee
             */
            searchAllEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                service.searchAllEmployee(self.baseDate()).done(data => {
                    self.onSearchAllClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            
            /**
             * convert model to dto => call service 
             */
            toEmployeeDto(): EmployeeSearchInDto {
                var self = this;
                var dto: EmployeeSearchInDto = new EmployeeSearchInDto();
                dto.baseDate = self.baseDate();
                dto.classificationCodes = self.selectedCodeClassification();
                dto.employmentCodes = self.selectedCodeEmployment();
                dto.jobTitleCodes = self.selectedCodeJobtitle();
                dto.workplaceCodes = self.selectedCodeWorkplace();
                return dto;
            }

            /**
             * function click by apply data search employee (init tab 2)
             */
            applyDataSearch(): void {
                var self = this;
                
                // call service search by base date
                if (!self.baseDate()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_374' });
                    return;
                }
                
                if (self.validateClient()) {
                    return;
                }
                
                service.searchWorkplaceOfEmployee(self.baseDate()).done(function(data) {
                    self.selectedCodeWorkplace(data);
                    self.reloadDataSearch();
                    if (self.isAdvancedSearchTab) {
                        $('#employmentList').ntsListComponent(self.employments);
                        $('#classificationList').ntsListComponent(self.classifications);
                        $('#jobtitleList').ntsListComponent(self.jobtitles);
                        $('#workplaceList').ntsTreeComponent(self.workplaces);
                        if(!self.isSelectAllEmployee) {
                            $('#employeeinfo').ntsListComponent(self.employeeinfo);
                        }
                    }
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error);
                });

            }

            /**
             * function click by button detail work place (open dialog)
             */
            
            detailWorkplace(): void {
                var self = this;
                nts.uk.ui.windows.setShared('baseDate', self.baseDate());
                nts.uk.ui.windows.setShared('selectedCodeWorkplace', self.selectedCodeWorkplace());
                
                nts.uk.ui.windows.sub.modal('com','/view/ccg/share/dialog/index.xhtml').onClosed(function() {
                    self.selectedCodeWorkplace(nts.uk.ui.windows.getShared('selectedCodeWorkplace'));
                    self.reloadDataSearch();
                    $('#workplaceList').ntsTreeComponent(self.workplaces);
                });
            }

            /**
             * function click by button search employee
             */
            searchDataEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                service.searchModeEmployee(self.toEmployeeDto()).done(data => {
                    self.employeeinfo.employeeInputList(self.toUnitModelList(data));
                }).fail(function(error){
                   nts.uk.ui.dialog.alertError(error); 
                });

            }

            /**
             * clear validate client
             */
            clearValiate() {
                $('#inp_baseDate').ntsError('clear');

            }

            /**
             * validate client
             */
            validateClient(): boolean {
                $("#inp_baseDate").ntsEditor("validate");

                if ($('#inp_baseDate').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            /**
             * function click by button employee login
             */
            getEmployeeLogin(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                service.searchEmployeeByLogin(self.baseDate()).done(data => {
                    if (data.length > 0) {
                        self.onSearchOnlyClicked(data[0]);
                    }
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * function click by search employee of work place
             */
            searchOfWorkplace(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                service.searchOfWorkplace(self.baseDate()).done(data => {
                    self.onSearchOfWorkplaceClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * function click by search employee of work place child
             */
            searchWorkplaceChild(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                service.searchWorkplaceChild(self.baseDate()).done(data => {
                    self.onSearchOfWorkplaceClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * function click apply search employee
             */
            applyEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                if (self.isSelectAllEmployee) {
                    service.searchModeEmployee(self.toEmployeeDto()).done(data => {
                        self.onApplyEmployee(data);
                    }).fail(function(error){
                        nts.uk.ui.dialog.alertError(error);
                    });
                } else {
                    
                    service.getOfSelectedEmployee(self.baseDate(), self.getSelectedCodeEmployee()).done(data => {
                        self.onApplyEmployee(data);
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    });

                }
            }

            /**
             * function get selected employee to
             */
            
            public getSelectedCodeEmployee(): string[]{
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                if(self.isMultiple){
                    return self.selectedCodeEmployee();    
                }
                var employeeIds: string[] = [];
                employeeIds.push(self.selectedCodeEmployee() + "");
                return employeeIds;
            }        
            
            /**
             * function convert dto to model init data 
             */
            
            public toUnitModelList(dataList: EmployeeSearchDto[]): Array<UnitModel> {
                var dataRes: UnitModel[] = [];

                for (var item: EmployeeSearchDto of dataList) {
                    dataRes.push({
                        code: item.employeeId,
                        name: item.employeeName
                    });
                }
                return dataRes;
            }

            /**
             * function reload page (init tab 2)
             */

            public reloadDataSearch(): void {
                var self = this;
                if (self.isAdvancedSearchTab) {
                    self.employments = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        selectType: SelectType.SELECT_ALL,
                        listType: ListType.EMPLOYMENT,
                        selectedCode: self.selectedCodeEmployment,
                        isDialog: true,
                        maxRows: ConfigCCGKCP.MAX_ROWS_EMPLOYMENT
                    };

                    self.classifications = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        listType: ListType.Classification,
                        selectType: SelectType.SELECT_ALL,
                        selectedCode: self.selectedCodeClassification,
                        isDialog: true,
                        maxRows: ConfigCCGKCP.MAX_ROWS_CLASSIFICATION
                    }

                    self.jobtitles = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        listType: ListType.JOB_TITLE,
                        selectType: SelectType.SELECT_ALL,
                        selectedCode: self.selectedCodeJobtitle,
                        isDialog: true,
                        baseDate: self.baseDate,
                        maxRows: ConfigCCGKCP.MAX_ROWS_JOBTITLE
                    }

                    self.workplaces = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        treeType: TreeType.WORK_PLACE,
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        isShowSelectButton: true,
                        selectedWorkplaceId: self.selectedCodeWorkplace,
                        baseDate: self.baseDate,
                        maxRows: ConfigCCGKCP.MAX_ROWS_WORKPLACE,
                        isDialog: true
                    }

                    self.employeeinfo = {
                        isShowAlreadySet: false,
                        isMultiSelect: self.isMultiple,
                        listType: ListType.EMPLOYEE,
                        employeeInputList: ko.observableArray([]),
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        selectedCode: self.selectedCodeEmployee,
                        isDialog: true,
                        isShowNoSelectRow: false,
                        maxRows: ConfigCCGKCP.MAX_ROWS_EMPLOYEE
                    }
                }
            }
        }
        
        export class ConfigCCGKCP{
            static MAX_ROWS_EMPLOYMENT = 10;
            static MAX_ROWS_CLASSIFICATION = 10;
            static MAX_ROWS_JOBTITLE = 10;
            static MAX_ROWS_WORKPLACE = 10;
            static MAX_ROWS_EMPLOYEE = 15;    
        }
    }
}
/**
 * Defined Jquery interface.
 */
interface JQuery {

   ntsGroupComponent(option: nts.uk.com.view.ccg.share.ccg.service.model.GroupOption): JQueryPromise<void>;
}

(function($: any) {
    $.fn.ntsGroupComponent = function(option: nts.uk.com.view.ccg.share.ccg.service.model.GroupOption): JQueryPromise<void> {

        // Return.
        return new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().init(this, option);
    }
} (jQuery));