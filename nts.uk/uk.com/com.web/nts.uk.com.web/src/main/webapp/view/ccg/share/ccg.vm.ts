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
            employments: ComponentOption;
            classifications: ComponentOption;
            jobtitles: ComponentOption;
            workplaces: TreeComponentOption;
            employeeinfo: ComponentOption;
            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;
            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
            isShow:  KnockoutObservable<boolean>;


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
                this.isShow = ko.observable(false);
            }

            public updateTabs(): NtsTabPanelModel[] {
                var self = this;
                var arrTabs: NtsTabPanelModel[] = [];
                if (self.isQuickSearchTab) {
                    arrTabs.push({
                        id: 'tab-1',
                        title: nts.uk.resource.getText("CCG001_3"),
                        content: '.tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                if (self.isAdvancedSearchTab) {
                    arrTabs.push({
                        id: 'tab-2',
                        title: nts.uk.resource.getText("CCG001_4"),
                        content: '.tab-content-2',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                return arrTabs;
            }

            public updateSelectedTab(): string {
                var selectedTab: string = '';
                var self = this;
                if (self.isQuickSearchTab) {
                    selectedTab = 'tab-1';
                }
                else if (self.isAdvancedSearchTab) {
                    selectedTab = 'tab-2';
                }
                return selectedTab;
            }
            /**
             * Init component.
             */
            public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var self = this;
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
                var webserviceLocator = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/ccg/share/index.xhtml').serialize();
                $input.load(webserviceLocator, function() {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                    self.applyDataSearch();
                    $(".accordion").accordion({
                        active: false,
                        collapsible: true
                    });
                    dfd.resolve();
                });
                
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
                    if (self.isShow()) {
                        $('#ccg-component').toggle("slide", function() {
                            self.isShow(false);
                        });
                    }
                });
                return dfd.promise();
            }
            
            showHide() {
                var self = this;
                if (self.isShow()) {
                    return;
                }
                $('#hor-scroll-button-hide').hide();
                $('#ccg-component').toggle("slide", function() {
                    self.isShow(true);
                });
            }

            searchAllEmployee(): void {
                var self = this;
                service.searchAllEmployee(self.baseDate()).done(data => {
                    self.onSearchAllClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

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

            applyDataSearch(): void {
                var self = this;
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

            detailWorkplace(): void {
                var self = this;
                nts.uk.ui.windows.setShared('baseDate', self.baseDate());
                nts.uk.ui.windows.setShared('selectedCodeWorkplace', self.selectedCodeWorkplace());
                nts.uk.ui.windows.sub.modal('/view/ccg/share/dialog/index.xhtml', { title: '職場リストダイアログ', dialogClass: 'no-close' }).onClosed(function() {
                    self.selectedCodeWorkplace(nts.uk.ui.windows.getShared('selectedCodeWorkplace'));
                    self.reloadDataSearch();
                    $('#workplaceList').ntsTreeComponent(self.workplaces);
                });
            }

            searchDataEmployee(): void {
                var self = this;
                service.searchModeEmployee(self.toEmployeeDto()).done(data => {
                    self.employeeinfo.employeeInputList(self.toUnitModelList(data));
                }).fail(function(error){
                   nts.uk.ui.dialog.alertError(error); 
                });

            }



            getEmployeeLogin(): void {
                var self = this;
                service.searchEmployeeByLogin(self.baseDate()).done(data => {
                    if (data.length > 0) {
                        self.onSearchOnlyClicked(data[0]);
                    }
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            searchOfWorkplace(): void {
                var self = this;
                service.searchOfWorkplace(self.baseDate()).done(data => {
                    self.onSearchOfWorkplaceClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            searchWorkplaceChild(): void {
                var self = this;
                service.searchWorkplaceChild(self.baseDate()).done(data => {
                    self.onSearchOfWorkplaceClicked(data);
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            applyEmployee(): void {
                var self = this;
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

            public getSelectedCodeEmployee(): string[]{
                var self = this;
                if(self.isMultiple){
                    return self.selectedCodeEmployee();    
                }
                var employeeIds: string[] = [];
                employeeIds.push(self.selectedCodeEmployee() + "");
                return employeeIds;
            }            
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


            reloadDataSearch() {
                var self = this;
                if (self.isAdvancedSearchTab) {
                    self.employments = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        selectType: SelectType.SELECT_ALL,
                        listType: ListType.EMPLOYMENT,
                        selectedCode: self.selectedCodeEmployment,
                        isDialog: true
                    };

                    self.classifications = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        listType: ListType.Classification,
                        selectType: SelectType.SELECT_ALL,
                        selectedCode: self.selectedCodeClassification,
                        isDialog: true
                    }

                    self.jobtitles = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        listType: ListType.JOB_TITLE,
                        selectType: SelectType.SELECT_ALL,
                        selectedCode: self.selectedCodeJobtitle,
                        isDialog: true,
                        baseDate: self.baseDate,
                    }

                    self.workplaces = {
                        isShowAlreadySet: false,
                        isMultiSelect: true,
                        treeType: TreeType.WORK_PLACE,
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        isShowSelectButton: true,
                        selectedWorkplaceId: self.selectedCodeWorkplace,
                        baseDate: self.baseDate,
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
                    }
                }
            }
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