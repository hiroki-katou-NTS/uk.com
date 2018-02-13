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
    import EmployeeRangeSelection = service.model.EmployeeRangeSelection;
    import EmployeeQueryParam = service.model.EmployeeQueryParam;
    import EmployeeDto = service.model.EmployeeDto;


    export module viewmodel {
        
        /**
        * Screen Model.
        */
        
        export class ListGroupScreenModel {

            /** Common properties */
            isShowEmployeeList: boolean; // 検索タイプ
            systemType: number; // システム区分
            isQuickSearchTab: boolean; // クイック検索
            isAdvancedSearchTab: boolean; // 詳細検索
            showBaseDate: boolean; // 基準日利用
            showClosure: boolean; // 就業締め日利用
            showAllClosure: boolean; // 全締め表示
            showPeriod: boolean; // 対象期間利用
            showPeriodYM: boolean; // 対象期間精度

            /** Required parameter */
            baseDate: KnockoutObservable<moment.Moment>;
            periodStartDate: KnockoutObservable<moment.Moment>;
            periodEndDate: KnockoutObservable<moment.Moment>;
            periodStartYm: KnockoutObservable<moment.Moment>;
            periodEndYm: KnockoutObservable<moment.Moment>;
            inService: boolean; // 在職区分
            leaveOfAbsence: boolean; // 休職区分
            closed: boolean; // 休業区分
            retirement: boolean; // 退職区分

            /** Quick search tab options */
            isAllReferableEmployee: boolean; // 参照可能な社員すべて
            isOnlyMe: boolean; // 自分だけ
            isEmployeeOfWorkplace: boolean; // 同じ職場の社員
            isEmployeeWorkplaceFollow: boolean; // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: boolean; // 雇用条件
            showWorkplace: boolean; // 職場条件
            showClassification: boolean; // 分類条件
            showJobTitle: boolean; // 職位条件
            showWorktype: boolean; // 勤種条件
            isMultiple: boolean; // 選択モード

            isShow: KnockoutObservable<boolean>;
            isOpenStatusOfEmployeeList: KnockoutObservable<boolean>;
            isOpenEmploymentList: KnockoutObservable<boolean>;
            isOpenClassificationList: KnockoutObservable<boolean>;
            isOpenJoptitleList: KnockoutObservable<boolean>;
            isOpenWorkplaceList: KnockoutObservable<boolean>;
            isOpenWorkTypeList: KnockoutObservable<boolean>;

            // tabs
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;

            // selected code
            selectedCodeEmployment: KnockoutObservableArray<string>;
            selectedCodeClassification: KnockoutObservableArray<string>;
            selectedCodeJobtitle: KnockoutObservableArray<string>;
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            selectedCodeEmployee: KnockoutObservableArray<string>;
            selectedCodeWorkType: KnockoutObservableArray<string>;

            // params
            employments: any;
            classifications: any;
            jobtitles: any;
            workplaces: TreeComponentOption;
            employeeinfo: any;
            closureList: KnockoutObservableArray<any>;
            selectedClosure: KnockoutObservable<number>;
            
            //QueryParam
            quickSearchParam: EmployeeQueryParam;
            advancedSearchParam: EmployeeQueryParam;
            referenceRange: number;
            
            //params Status Of Employee
            statusPeriodStart: KnockoutObservable<moment.Moment>;
            statusPeriodEnd: KnockoutObservable<moment.Moment>;
            incumbentDatasource: KnockoutObservableArray<any>;
            selectedIncumbent: any;
            
            closedDatasource: KnockoutObservableArray<any>;
            selectedClosed: any;
            
            leaveOfAbsenceDatasource: KnockoutObservableArray<any>;
            selectedLeave: any;
            
            retirementDatasource: KnockoutObservableArray<any>;
            selectedRetirement: any;

            // functions
            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;
            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
            onApplyEmployee: (data: EmployeeSearchDto[]) => void;

            // List WorkType
            listWorkType: KnockoutObservableArray<WorkType>;
            selectedWorkTypeCode: KnockoutObservableArray<string>;
            
            workTypeColumns: KnockoutObservableArray<any>;
            
            employeeList: KnockoutObservableArray<EmployeeDto>;

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

                // status of employment period
                self.statusPeriodStart = ko.observable(moment());
                self.statusPeriodEnd = ko.observable(moment());

                // init query param
                self.initQuickSearchParam();
                self.initAdvancedSearchParam();
                
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
                self.isOpenStatusOfEmployeeList = ko.observable(false);
                self.isOpenEmploymentList = ko.observable(false);
                self.isOpenClassificationList = ko.observable(false);
                self.isOpenJoptitleList = ko.observable(false);
                self.isOpenWorkplaceList = ko.observable(false);
                self.isOpenWorkTypeList = ko.observable(false);
                self.closureList = ko.observableArray([]);
                self.selectedClosure = ko.observable(null);

                self.baseDate = ko.observable(moment());
                self.periodStartDate = ko.observable(moment());
                self.periodEndDate = ko.observable(moment());
                self.periodStartYm = ko.observable(moment());
                self.periodEndYm = ko.observable(moment());
                
                self.incumbentDatasource = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CCG001_40") },
                    { code: '2', name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.selectedIncumbent = ko.observable(1);
                
                self.closedDatasource = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CCG001_40") },
                    { code: '2', name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.selectedClosed = ko.observable(1);
                
                self.leaveOfAbsenceDatasource = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CCG001_40") },
                    { code: '2', name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.selectedLeave = ko.observable(1);
                
                self.retirementDatasource = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CCG001_40") },
                    { code: '2', name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.selectedRetirement = ko.observable(1);
                
                //WorkType
                self.listWorkType = ko.observableArray([]);
                self.selectedWorkTypeCode = ko.observableArray([]);
                
                // Define gridlist's columns
                self.workTypeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CCG001_60'), prop: 'workTypeCode', width: 100 },
                    { headerText: nts.uk.resource.getText('CCG001_61'), prop: 'name', width: 200 }
                ]);
                
                self.employeeList = ko.observableArray([]);
            }

            private initQuickSearchParam(): void {
                let self = this;
                self.quickSearchParam = <EmployeeQueryParam>{};
                self.quickSearchParam.filterByDepartment = false;
                self.quickSearchParam.departmentCodes = [];
                self.quickSearchParam.filterByWorkplace = false;
                self.quickSearchParam.workplaceCodes = [];
                self.quickSearchParam.filterByClassification = false;
                self.quickSearchParam.classificationCodes = [];
                self.quickSearchParam.filterByJobTitle = false;
                self.quickSearchParam.jobTitleCodes = [];
                self.quickSearchParam.includeIncumbents = true;
                self.quickSearchParam.includeWorkersOnLeave = false;
                self.quickSearchParam.includeOccupancy = false;
                self.quickSearchParam.includeRetirees = false;
                self.quickSearchParam.sortOrderNo = 1; // 並び順NO＝1
                self.quickSearchParam.nameType = 1; // ビジネスネーム（日本語）
            }

            private initAdvancedSearchParam(): void {
                let self = this;
                self.advancedSearchParam = <EmployeeQueryParam>{};
                self.advancedSearchParam.sortOrderNo = 1; // 並び順NO＝1
                self.advancedSearchParam.nameType = 1; // ビジネスネーム（日本語）
            }

            /**
             * update select tabs
             */
             
            public updateTabs(): Array<any> {
                let self = this;
                let arrTabs = [];
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
                $('#tab-2').find('#StatusOfEmployeeList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenStatusOfEmployeeList(!self.isOpenStatusOfEmployeeList());
                });
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
                $('#tab-2').find('#WorkTypeList').find('.ui-accordion-header').on('click', function() {
                    self.isOpenWorkTypeList(!self.isOpenWorkTypeList());
                });
                $("[tabindex='10']").on('keydown', function(e) {
                    if (e.which == 9 && self.isAdvancedSearchTab) {
                        self.selectedTab('tab-2');
                        if (!self.isOpenStatusOfEmployeeList()) {
                            $('#tab-2').find('#StatusOfEmployeeList').find('.ui-accordion-header').click();
                        }
                        $("[tabindex='11']").on('keydown', function(e) {
                            if (e.which == 9) {
                                if (!self.isOpenEmploymentList()) {
                                    $('#tab-2').find('#EmploymentList').find('.ui-accordion-header').click();
                                }
                            }
                            $("[tabindex='12']").on('keydown', function(e) {
                                if (e.which == 9) {
                                    if (!self.isOpenClassificationList()) {
                                        $('#tab-2').find('#ClassificationList').find('.ui-accordion-header').click();
                                    }
                                }
                                $("[tabindex='13']").on('keydown', function(e) {
                                    if (e.which == 9) {
                                        if (!self.isOpenJoptitleList()) {
                                            $('#tab-2').find('#JoptitleList').find('.ui-accordion-header').click();
                                        }
                                    }
                                    $("[tabindex='14']").on('keydown', function(e) {
                                        if (e.which == 9) {
                                            if (!self.isOpenWorkplaceList()) {
                                                $('#tab-2').find('#WorkplaceList').find('.ui-accordion-header').click();
                                            }
                                        }
                                        $("[tabindex='15']").on('keydown', function(e) {
                                            if (e.which == 9) {
                                                if (!self.isOpenWorkTypeList()) {
                                                    $('#tab-2').find('#WorkTypeList').find('.ui-accordion-header').click();
                                                }
                                            }
                                        });     
                                    });
                                });
                            });
                        });
                    }
                });
                $("[tabindex='6']").on('keydown', function(e) {
                    if (e.which == 9 && self.selectedTab() == 'tab-2' && !$(e.target).parents("[tabindex='6']")[0]) {
                        self.selectedTab('tab-1');
                    }
                });
                
            }

            
            /**
             * Init component.
             */
            
            public init($input: JQuery, data: GroupOption): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;

                // set component properties
                self.setProperties(data);

                self.tabs(self.updateTabs());
                self.selectedTab(self.updateSelectedTab());

                // start component
                nts.uk.ui.block.invisible(); // block ui
                self.startComponent().done(() => {
                    // init view
                    let webserviceLocator = nts.uk.request.location.siteRoot
                        .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                        .mergeRelativePath('/view/ccg/share/index.xhtml').serialize();
                    $input.load(webserviceLocator, function() {
                        ko.cleanNode($input[0]);
                        ko.applyBindings(self, $input[0]);
                        self.applyDataSearch();
                        self.initNextTabFeature();

                        // Set tabindex ro button show component.
                        let tabindex = $input.attr('tabindex');
                        $input.attr('tabindex', -1);
                        $input.find('.btn_showhide').attr('tabindex', tabindex);

                        // init ccg show/hide event
                        self.initCcgEvent();
                        // set component height
                        self.setComponentHeight();

                        nts.uk.ui.block.clear(); // clear block UI
                        dfd.resolve();
                    });
                });

                return dfd.promise();
            }

            /**
             * Start component
             */
            private startComponent(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                $.when(service.getRefRangeBySysType(self.systemType),
                    self.acquireBaseDate(),
                    self.loadClosure()
                ).done((refRange, baseDate, noValue) => {
                    self.referenceRange = refRange;
                    self.setSearchParamBaseDate(baseDate);
                    dfd.resolve();
                });

                return dfd.promise();
            }

            private setSearchParamBaseDate(baseDate: any): void {
                let self = this;
                console.log(baseDate);
                self.quickSearchParam.baseDate = baseDate;
                self.advancedSearchParam.baseDate = baseDate;
            }

            private setQuickSearchParam(options: GroupOption): void {
                let param = this.quickSearchParam;
                param.filterByEmployment =  options.showClosure;
            }

            private setAdvancedSearchParam(options: GroupOption): void {
                let param = this.advancedSearchParam;
                param.filterByEmployment = true;
                // not covered param.filterByDepartment = options.showDepartment;
                param.filterByWorkplace = true;
                param.filterByClassification = true;
                param.filterByJobTitle = true;
                param.includeIncumbents = true;
                param.includeWorkersOnLeave = true;
                param.includeOccupancy = true;
                param.includeRetirees = true;
            }

            /**
             * Set component properties
             */
            private setProperties(options: GroupOption): void {
                let self = this;

                // set search param
                self.setQuickSearchParam(options);
                self.setAdvancedSearchParam(options);

                /** Common properties */
                self.isShowEmployeeList = options.isSelectAllEmployee;
                self.systemType = options.systemType;
                // always show quick search if advanced search is hidden
                self.isQuickSearchTab = options.isAdvancedSearchTab ? options.isQuickSearchTab : true;
                self.isAdvancedSearchTab = options.isAdvancedSearchTab;
                self.showBaseDate = options.showBaseDate;
                self.showClosure = options.showClosure;
                self.showAllClosure = options.showAllClosure;
                self.showPeriod = options.showPeriod;
                self.showPeriodYM = options.periodFormatYM;

                /** Required parameter */
                self.baseDate = ko.observable(moment()); //TODO: mock data
                self.periodStartDate = ko.observable(moment());
                self.periodEndDate = ko.observable(moment());
                self.periodStartYm = ko.observable(moment());
                self.periodEndYm = ko.observable(moment());
                self.inService = options.inService;
                self.leaveOfAbsence = options.leaveOfAbsence;
                self.closed = options.closed;
                self.retirement = options.retirement;

                /** Quick search tab options */
                self.isAllReferableEmployee = options.isAllReferableEmployee;
                self.isOnlyMe = options.isOnlyMe;
                self.isEmployeeOfWorkplace = options.isEmployeeOfWorkplace;
                self.isEmployeeWorkplaceFollow = options.isEmployeeWorkplaceFollow;

                /** Advanced search properties */
                self.showEmployment = options.showEmployment;
                self.showWorkplace = options.showWorkplace;
                self.showClassification = options.showClassification;
                self.showJobTitle = options.showJobTitle;
                self.showWorktype = options.showWorktype;
                self.isMultiple = options.isMutipleCheck;

                // functions
                self.onSearchAllClicked = options.onSearchAllClicked;
                self.onSearchOnlyClicked = options.onSearchOnlyClicked;
                self.onSearchOfWorkplaceClicked = options.onSearchOfWorkplaceClicked;
                self.onSearchWorkplaceChildClicked = options.onSearchWorkplaceChildClicked;
                self.onApplyEmployee = options.onApplyEmployee;
            }

            /**
             * Set component height
             */
            private setComponentHeight(): void {
                const headerHeight = $('#header').outerHeight();
                const functionAreaHeight = $('#functions-area').length > 0 ? $('#functions-area').outerHeight() : 0;
                const componentHeight = window.innerHeight - headerHeight - functionAreaHeight - 15;
                $('#component-ccg001').outerHeight(componentHeight);
                $('#hor-scroll-button-hide').outerHeight(componentHeight);
                $('#ccg001-btn-search-drawer').outerHeight(componentHeight / 2);
            }

            /**
             * Load ListClosure 
             */
            private loadClosure(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showClosure) {
                    service.getClosuresByBaseDate(self.baseDate().format('YYYY-MM-DD')).done(data => {
                        self.closureList(data);
                        self.getSelectedClosure().done(selected => {
                            self.selectedClosure(selected);
                            dfd.resolve();
                        });
                    });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Get selected closure id
             */
            private getSelectedClosure(): JQueryPromise<number> {
                let dfd = $.Deferred<number>();
                let self = this;
                const key = 'test';
                service.getEmployeeRangeSelection(key).done(data => {
                    if (data) {
                        switch (self.systemType) {
                            case 1:
                                dfd.resolve(data.personnelInfo.selectedClosureId);
                                break;
                            default: break; // systemType not found
                        }
                    } else {
                        service.getCurrentHistoryItem().done(item => {
                            service.getClosureTiedByEmployment(item.employmentCode).done(id => dfd.resolve(id));
                        });
                    }
                });
                return dfd.promise();
            }

            /**
             * Initial ccg event
             */
            private initCcgEvent(): void {
                let self = this;
                $('#component-ccg001').parent('#component-ccg001').on('hover', e => {
                    console.log('hovered');
                });
                $(window).on('click', function(e) {
                    // Check is click to inside component.
                    if (e.target.id == "component-ccg001" || $(e.target).parents("#component-ccg001")[0]) {
                        return;
                    }
                    // click when block ui
                    if ($(e.target).hasClass('ui-widget-overlay ui-front')) {
                        return;
                    }
                    if ($(e.target).hasClass('blockUI blockOverlay')) {
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
                    self.hideComponent();
                });
            }
            
            /**
             * Hide component
             */
            public hideComponent(): void {
                let self = this;
                if (self.isShow()) {
                    $('#component-ccg001').toggle("slide");
                    self.isShow(false);
                }
            }

            /**
             * Show component
             */
            public showComponent(): void {
                let self = this;
                if (self.isShow()) {
                    return;
                }
                $('#component-ccg001').toggle("slide");
                self.isShow(true);
            }

            /**
             * function click by search all employee
             */
            searchAllEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
            }

            private isValidDate(acquiredBaseDate: string): boolean {
                let self = this;
                if (self.isFutureDate(moment.utc(acquiredBaseDate))) { //TODO: check input basedate hay basedate tinh duoc?
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_853" });
                    return false;
                }
                if (self.isFutureDate(self.getPeriodEnd())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_860" });
                    return false;
                }
                return true;
            }

            private getPeriodStart(): moment.Moment {
                let self = this;
                if (self.showPeriodYM) {
                    return self.periodStartYm();
                } else {
                    return self.periodStartDate();
                }
            }

            private isFutureDate(date: moment.Moment): boolean {
                return date.isAfter(moment());
            }

            private getPeriodEnd(): moment.Moment {
                let self = this;
                if (self.showPeriodYM) {
                    return self.periodEndYm();
                } else {
                    return self.periodEndDate();
                }
            }

            /**
             * function click by apply data search employee (init tab 2)
             * get base date
             */
            applyDataSearch(): void {
                var self = this;

                // validate input base date
                if (self.validateClient()) {
                    return;
                }

                self.acquireBaseDate().done(acquiredDate => {
                    self.getFuturePermit().done(permit => {
                        if (permit) {
                            self.setSearchParamBaseDate(acquiredDate);
                        } else if (self.isValidDate(acquiredDate)) {
                            self.setSearchParamBaseDate(acquiredDate);
                        }
                    });
                });

                self.advancedSearchParam.periodStart = self.getPeriodStart().format('YYYY-MM-DD');
                self.advancedSearchParam.periodEnd = self.getPeriodEnd().format('YYYY-MM-DD');
                self.advancedSearchParam.retireStart = self.statusPeriodStart().format('YYYY-MM-DD');
                self.advancedSearchParam.retireEnd = self.statusPeriodEnd().format('YYYY-MM-DD');

                nts.uk.ui.block.invisible(); // block ui
                // reload advanced search tab.
                if (self.isAdvancedSearchTab) {
                    $.when(service.searchWorkplaceOfEmployee(new Date()), // mock base date
                        service.searchAllWorkType())
                        .done((selectedCodes, workTypeList: Array<WorkType>) => {
                            self.selectedCodeWorkplace(selectedCodes);
                            self.listWorkType(workTypeList);
                            self.reloadDataSearch();

                            if (self.showEmployment) {
                                $('#employmentList').ntsListComponent(self.employments);
                            }
                            if (self.showClassification) {
                                $('#classificationList').ntsListComponent(self.classifications);
                            }
                            if (self.showJobTitle) {
                                $('#jobtitleList').ntsListComponent(self.jobtitles);
                            }
                            if (self.showWorkplace) {
                                $('#workplaceList').ntsTreeComponent(self.workplaces);
                            }
                            if (self.isShowEmployeeList) {
                                $('#employeeinfo').ntsListComponent(self.employeeinfo);
                            }
                            self.setAdvancedFilterCondition();
                            nts.uk.ui.block.clear();

                        });
                }
            }

            private setAdvancedFilterCondition(): void {
                let self = this;
                if (self.showEmployment) {
                    self.advancedSearchParam.employmentCodes = self.selectedCodeEmployment();
                }
                if (self.showClassification) {
                    self.advancedSearchParam.classificationCodes = self.selectedCodeClassification();
                }
                if (self.showJobTitle) {
                    self.advancedSearchParam.jobTitleCodes = self.selectedCodeJobtitle();
                }
                if (self.showWorkplace) {
                    self.advancedSearchParam.workplaceCodes = self.selectedCodeWorkplace();
                }
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
            extractSelectedEmployees(): void {
                var self = this;
                if (nts.uk.util.isNullOrEmpty(self.getSelectedCodeEmployee())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_758" });
                    return;
                }
                nts.uk.ui.block.invisible(); // block ui
                service.getOfSelectedEmployee(new Date(), self.getSelectedCodeEmployee())
                    .done(selectedEmps => {
                        nts.uk.ui.block.clear(); // clear block UI
                        self.onSearchAllClicked(selectedEmps);
                        // Hide component.
                        self.hideComponent();
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
                let self = this;
                $("#inp_baseDate").ntsEditor("validate");

                if ($('#inp_baseDate').ntsError('hasError')) {
                    return true;
                }
                if (self.showPeriod && self.showBaseDate && !self.isBaseDateInTargetPeriod()) {
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
                service.searchEmployeeByLogin(self.baseDate().toDate()).done(data => {
                    if (data.length > 0) {
                        self.onSearchOnlyClicked(data[0]);
                    }
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * Acquire base date
             */
            public acquireBaseDate(): JQueryPromise<string> { // format: YYYY-MM-DD
                let dfd = $.Deferred<String>();
                let self = this;
                if (self.showBaseDate) {
                    dfd.resolve(self.baseDate().format('YYYY-MM-DD'));
                } else {
                    if (self.showPeriodYM) { // Period accuracy is YM 
                        service.calculatePeriod(1, 201802).done(date => { //TODO mock data
                            return dfd.resolve(date);
                        });
                    } else { // Period accuracy is YMD
                        dfd.resolve(self.periodEndDate().format('YYYY-MM-DD'));
                    }
                }
                return dfd.promise();
            }

            public getFuturePermit(): JQueryPromise<boolean> {
                let self = this;
                switch (self.systemType) {
                    case 1:
                        return service.getPersonalRoleFuturePermit();
                    case 2:
                        return service.getEmploymentRoleFuturePermit();
                    default: 
                        let dfd = $.Deferred<boolean>();
                        dfd.reject;
                        return dfd.promise();// systemType not found
                }
            }

            public isBaseDateInTargetPeriod(): boolean {
                let self = this;
                const periodStart = self.getPeriodStart();
                const periodEnd = self.getPeriodEnd();
                if (self.baseDate().isBefore(periodStart) || self.baseDate().isAfter(periodEnd)) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_765" });
                    return false;
                }
                return true;
            }
            /**
             * function click by search employee of work place
             */
            searchOfWorkplace(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
            }

            /**
             * function click by search employee of work place child
             */
            searchWorkplaceChild(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
            }

            /**
             * function click apply search employee
             */
            advancedSearchEmployee(): void {
                let self = this;
                const mock = <EmployeeRangeSelection>{};
                mock.userId = __viewContext.user.employeeId;
                mock.companyId = __viewContext.user.companyId;

                // test
                service.findRegulationInfoEmployee(self.advancedSearchParam);

                nts.uk.ui.block.invisible(); // block ui
                service.saveEmployeeRangeSelection(mock).done(() => {
                    service.searchAllEmployee(new Date()).done(data => {
                        nts.uk.ui.block.clear(); // clear block UI
                        if (self.isShowEmployeeList) {
                            self.employeeinfo.employeeInputList(self.toUnitModelList(data));
                        } else {
                            self.onSearchAllClicked(data);
                            // Hide component.
                            self.hideComponent();
                        }
                    })
                });
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

                _.forEach(dataList, item => {
                    dataRes.push({
                        code: item.employeeId,
                        name: item.employeeName
                    });
                });
                return dataRes;
            }
            
            /**
             * search all Employee
             */
            public searchAllListEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                self.quickSearchParam.referenceRange = ConfigEnumReferenceRange.ALL_EMPLOYEE;
                self.queryMethod();
            }
            
            /**
             * search Employee of Departmant_only
             */
            public searchEmployeeOfDepOnly(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                self.quickSearchParam.referenceRange = ConfigEnumReferenceRange.DEPARTMENT_ONLY;
                self.queryMethod();
            }
            
            /**
             * search Employee of Departmant_Not_Only
             */
            public searchEmployeeOfDepNotOn(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                self.quickSearchParam.referenceRange = ConfigEnumReferenceRange.DEPARTMENT_AND_CHILD;
                self.queryMethod();
            }
            
            /**
             * Method Query
             */
            public queryMethod(): void {
                var self = this;
                
                //check closure is displayed 
                if (self.showClosure){
                    if (self.selectedClosure() != ConfigEnumClosure.CLOSURE_ALL){
                        service.getEmploymentCodeByClosureId(self.selectedClosure()).done(data => {
                            self.quickSearchParam.filterByEmployment = true;
                            self.quickSearchParam.employmentCodes = data;
                        });
                    } 
                }

                self.changeListWorkplaceId().done(() => {
                    service.findRegulationInfoEmployee(self.quickSearchParam).done(data => {
                        // return data
                        self.onSearchAllClicked(data);
                        // Hide component.
                        self.hideComponent();
                    });
                });
            }
            
            /**
             * Change workplace list
             */
            public changeListWorkplaceId(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                var self = this;
                var status = true;
               
                //change the workplace list
                if (nts.uk.util.isNullOrEmpty(self.quickSearchParam.referenceRange)){
                    dfd.resolve();
                } else {
                    //Get ReferenceRange by Role
                    service.getRefRangeBySysType(self.systemType).done(data => {
                        if (data == ConfigEnumReferenceRange.ONLY_MYSELF){
                            status = false;
                            dfd.resolve();
                        }
                        else {
                            //check param ReferenceRange
                            if (self.quickSearchParam.referenceRange == ConfigEnumReferenceRange.ALL_EMPLOYEE){
                                //check ReferenceRange from Role
                                if (data == ConfigEnumReferenceRange.ALL_EMPLOYEE){
                                    dfd.resolve();
                                } 
                            } else {
                                ////check ReferenceRange from Role
                                if (data != ConfigEnumReferenceRange.DEPARTMENT_AND_CHILD){
                                    self.quickSearchParam.referenceRange = ConfigEnumReferenceRange.DEPARTMENT_ONLY;
                                }
                            }
                            //get Workplace list from domain
                            service.getListWorkplaceId(self.quickSearchParam.baseDate, data).done(wkplist => {
                                 //check param filterByWorkplace
                                if(self.quickSearchParam.filterByWorkplace){
                                    if(!nts.uk.util.isNullOrEmpty(self.quickSearchParam.workplaceCodes) &&
                                        !nts.uk.util.isNullOrEmpty(wkplist)){
                                        //Set list workplaceId
                                        self.setListWorkplace(wkplist);
                                    }      
                                } else {
                                    self.quickSearchParam.filterByWorkplace = true;
                                    self.quickSearchParam.workplaceCodes = wkplist;
                                }
                                dfd.resolve();
                            });
                        }
                       
                    });
                }
                return dfd.promise();
            }
            
            /**
             * Set list workplaceId
             */
            public setListWorkplace(listWorkplaceId: Array<string>): void {
                var self = this;
                
                var lstWkp = self.quickSearchParam.workplaceCodes;
                self.quickSearchParam.workplaceCodes = [];
                
                _.forEach(lstWkp, item => {
                    _.forEach(listWorkplaceId, item1 => {
                        if(_.eq(item, item1)){
                            self.quickSearchParam.workplaceCodes.push(item);
                        }
                    });
                });  
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
                        baseDate: ko.observable(self.baseDate().toDate()),
                        maxRows: ConfigCCGKCP.MAX_ROWS_JOBTITLE
                    }

                    self.workplaces = {
                        isShowAlreadySet: false,
                        systemType: self.systemType,
                        isMultipleUse: false,
                        isMultiSelect: true,
                        treeType: TreeType.WORK_PLACE,
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        isShowSelectButton: true,
                        selectedWorkplaceId: self.selectedCodeWorkplace,
                        baseDate: ko.observable(self.baseDate().toDate()),
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
            static MAX_ROWS_EMPLOYEE = 20;    
        }
        
        export class ConfigEnumSystemType{
            static SYSTYPE_ADMIN = 5;
            static SYSTYPE_EMPLOYMENT = 2;
            static SYSTYPE_PERSONALINFOR = 1;
        }
        
         export class ConfigEnumClosure{
            static CLOSURE_ALL = 0;
        }
        
        export class ConfigEnumReferenceRange{
            static ALL_EMPLOYEE = 0;
            static DEPARTMENT_AND_CHILD = 1;
            static DEPARTMENT_ONLY = 2;
            static ONLY_MYSELF = 3;
        }

        interface WorkType {
            abbreviationName: string;
            companyId: string;
            displayAtr: number;
            memo: string;
            name: string;
            sortOrder: number;
            symbolicName: string;
            workTypeCode: string;
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