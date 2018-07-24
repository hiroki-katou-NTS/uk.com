module nts.uk.com.view.ccg.share.ccg {

    import ListType = kcp.share.list.ListType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import EmployeeSearchDto = service.model.EmployeeSearchDto;
    import Ccg001ReturnedData = service.model.Ccg001ReturnedData;
    import GroupOption = service.model.GroupOption;
    import EmployeeRangeSelection = service.model.EmployeeRangeSelection;
    import EmployeeQueryParam = service.model.EmployeeQueryParam;
    import EmployeeDto = service.model.EmployeeDto;
    import DatePeriodDto = service.model.DatePeriodDto;
    import BusinessType = service.model.BusinessType;

    export module viewmodel {
        
        /**
        * Screen Model.
        */
        
        export class ListGroupScreenModel {
            /** Domain characteristic */
            employeeRangeSelection: EmployeeRangeSelection;
            comEmployment: any;
            comClassification: any;
            comJobTitle: any;

            /** Common properties */
            showEmployeeSelection: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab: boolean; // クイック検索
            showAdvancedSearchTab: boolean; // 詳細検索
            showBaseDate: boolean; // 基準日利用
            showClosure: boolean; // 就業締め日利用
            showAllClosure: boolean; // 全締め表示
            showPeriod: boolean; // 対象期間利用
            showPeriodYM: boolean; // 対象期間精度

            /** Required parameter */
            inputBaseDate: KnockoutObservable<string>;
            inputPeriod: KnockoutObservable<DateRangePickerModel>;
            baseDate: KnockoutComputed<moment.Moment>;
            periodStart: KnockoutComputed<moment.Moment>;
            periodEnd: KnockoutComputed<moment.Moment>;

            /** Quick search tab options */
            showAllReferableEmployee: boolean; // 参照可能な社員すべて
            showOnlyMe: boolean; // 自分だけ
            showSameWorkplace: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: boolean; // 雇用条件
            showWorkplace: boolean; // 職場条件
            showClassification: boolean; // 分類条件
            showJobTitle: boolean; // 職位条件
            showWorktype: boolean; // 勤種条件
            isMultiple: boolean; // 選択モード

            // flags
            isShow: KnockoutObservable<boolean>;
            isOpenStatusOfEmployeeList: KnockoutObservable<boolean>;
            isOpenEmploymentList: KnockoutObservable<boolean>;
            isOpenClassificationList: KnockoutObservable<boolean>;
            isOpenJoptitleList: KnockoutObservable<boolean>;
            isOpenWorkplaceList: KnockoutObservable<boolean>;
            isOpenWorkTypeList: KnockoutObservable<boolean>;
            isInDialog: boolean;
            isApplySearchDone: boolean = true;
            hasShownErrorDialog: boolean = false;

            // tabs
            tabs: KnockoutObservableArray<any>;
            selectedTab: KnockoutObservable<string>;

            // selected code
            selectedCodeEmployment: KnockoutObservableArray<string>;
            selectedCodeClassification: KnockoutObservableArray<string>;
            selectedCodeJobtitle: KnockoutObservableArray<string>;
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            selectedCodeEmployee: KnockoutObservableArray<string>;

            // params
            employments: ComponentOption;
            classifications: ComponentOption;
            jobtitles: ComponentOption;
            workplaces: TreeComponentOption;
            employeeinfo: ComponentOption;
            closureList: KnockoutObservableArray<any>;
            selectedClosure: KnockoutObservable<number>;
            
            //QueryParam
            queryParam: EmployeeQueryParam;
            referenceRange: number;
            
            //params Status Of Employee
            inputStatusPeriodStart: KnockoutObservable<string>;
            inputStatusPeriodEnd: KnockoutObservable<string>;
            statusPeriodStart: KnockoutComputed<moment.Moment>;
            statusPeriodEnd: KnockoutComputed<moment.Moment>;

            incumbentDatasource: KnockoutObservableArray<any>;
            selectedIncumbent: KnockoutObservable<boolean>; // 在職区分
            
            closedDatasource: KnockoutObservableArray<any>;
            selectedClosed: KnockoutObservable<boolean>; // 休業区分
            
            leaveOfAbsenceDatasource: KnockoutObservableArray<any>;
            selectedLeave: KnockoutObservable<boolean>; // 休職区分
            
            retirementDatasource: KnockoutObservableArray<any>;
            selectedRetirement: KnockoutObservable<boolean>; // 退職区分

            // return function
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;

            // List WorkType
            listWorkType: KnockoutObservableArray<BusinessType>;
            selectedWorkTypeCode: KnockoutObservableArray<string>;
            workTypeColumns: KnockoutObservableArray<any>;

            // flags
            isFirstTime = true;
            isHeightFixed = false;
            showApplyBtn: KnockoutComputed<boolean>;
            tab2HasLoaded = false;
            isTab2Lazy = false;

            // reserved list employee for KCP005
            reservedEmployees: KnockoutObservableArray<EmployeeSearchDto>;
            reservedEmployeesTab3: KnockoutObservableArray<EmployeeSearchDto>;

            // Acquired baseDate
            acquiredBaseDate: KnockoutObservable<string>;

            employeeListTab3: KnockoutObservableArray<UnitModel>;
            selectedEmployeesTab3: KnockoutObservableArray<string>;
            inputCodeTab3: KnockoutObservable<string>;
            inputNameTab3: KnockoutObservable<string>;
            entryDateTab3: KnockoutObservable<any>;
            retirementDateTab3: KnockoutObservable<any>;
            tab3kcp005option: any;

            employmentSubscriptions: Array<KnockoutSubscription> = [];
            employeeSubscriptions: Array<KnockoutSubscription> = [];

            /**
             * Init screen model
             */
            constructor() {
                var self = this;
                self.initQueryParam();

                // init datasource
                self.initDatasource();

                // init selected values
                self.selectedTab = ko.observable('tab-1');
                self.selectedCodeEmployment = ko.observableArray([]);
                self.selectedCodeClassification = ko.observableArray([]);
                self.selectedCodeJobtitle = ko.observableArray([]);
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.selectedCodeEmployee = ko.observableArray([]);
                self.closureList = ko.observableArray([]);
                self.selectedClosure = ko.observable(null);

                // init reserved employee list.
                self.reservedEmployees = ko.observableArray([]);
                self.reservedEmployeesTab3 = ko.observableArray([]);

                // date picker
                self.inputBaseDate = ko.observable('');
                self.inputPeriod = ko.observable(new DateRangePickerModel(moment().toISOString(), moment().toISOString()));
                self.inputStatusPeriodStart = ko.observable(moment.utc("1900/01/01", "YYYY/MM/DD").toISOString());
                self.inputStatusPeriodEnd = ko.observable(moment().toISOString());

                // flags
                self.isShow = ko.observable(false);
                self.isOpenStatusOfEmployeeList = ko.observable(false);
                self.isOpenEmploymentList = ko.observable(false);
                self.isOpenClassificationList = ko.observable(false);
                self.isOpenJoptitleList = ko.observable(false);
                self.isOpenWorkplaceList = ko.observable(false);
                self.isOpenWorkTypeList = ko.observable(false);

                // search reference date & period
                self.acquiredBaseDate = ko.observable('');

                // status of employee
                self.selectedIncumbent = ko.observable(false);
                self.selectedClosed = ko.observable(false);
                self.selectedLeave = ko.observable(false);
                self.selectedRetirement = ko.observable(false);
                
                //WorkType
                self.listWorkType = ko.observableArray([]);
                self.selectedWorkTypeCode = ko.observableArray([]);
                
                // init computed values
                self.initComputedValues();

            }

            /**
             * Initialize computed values
             */
            public initComputedValues(): void {
                let self = this;
                self.baseDate = ko.computed(() => {
                    return moment.utc(self.inputBaseDate());
                });
                self.periodStart = ko.computed(() => {
                    return moment.utc(self.inputPeriod().startDate, CcgDateFormat.YMD);
                });
                self.periodEnd = ko.computed(() => {
                    return moment.utc(self.inputPeriod().endDate, CcgDateFormat.YMD);
                });
                self.statusPeriodStart = ko.computed(() => {
                    return moment.utc(self.inputStatusPeriodStart());
                });
                self.statusPeriodEnd = ko.computed(() => {
                    return moment.utc(self.inputStatusPeriodEnd());
                });
                self.showApplyBtn = ko.computed(() => {
                    return self.baseDate() && self.periodStart() && self.periodEnd() ? true : false;
                });
            }

            /**
             * Initialize subscribers
             */
            public initSubscribers(): void {
                let self = this;
                self.baseDate.subscribe(vl => {
                    self.applyDataSearch();
                });

                self.selectedTab.subscribe(vl => {
                    if (vl == 'tab-2' && !self.tab2HasLoaded) {
                        self.reloadAdvanceSearchTab();
                    }
                });
                self.selectedTab.valueHasMutated();

                self.inputPeriod.subscribe(value => {
                    if (!$('.ntsDatepicker').ntsError('hasError')) {
                        _.defer(() => self.applyDataSearch());
                    }
                });

                self.statusPeriodStart.subscribe(startDate => {
                    if (startDate.isAfter(self.statusPeriodEnd())) {
                        let CCG001_94 = nts.uk.resource.getText("CCG001_94");
                        $("#ccg001-partg-start").ntsError('set', nts.uk.resource.getMessage("FND_E_SPAN_REVERSED", [CCG001_94]), "FND_E_SPAN_REVERSED");
                    }
                });

                self.statusPeriodEnd.subscribe(endDate => {
                    $("#ccg001-partg-start").ntsError("clear");
                    if (endDate.isBefore(self.statusPeriodStart())) {
                        let CCG001_94 = nts.uk.resource.getText("CCG001_94");
                        $("#ccg001-partg-start").ntsError('set', nts.uk.resource.getMessage("FND_E_SPAN_REVERSED", [CCG001_94]), "FND_E_SPAN_REVERSED");
                    }
                });
            }

            /**
             * Init datasource
             */
            private initDatasource(): void {
                let self = this;
                self.tabs = ko.observableArray([]);
                self.incumbentDatasource = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("CCG001_40") },
                    { code: false, name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.closedDatasource = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("CCG001_40") },
                    { code: false, name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.leaveOfAbsenceDatasource = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("CCG001_40") },
                    { code: false, name: nts.uk.resource.getText("CCG001_41") }
                ]);
                self.retirementDatasource = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("CCG001_40") },
                    { code: false, name: nts.uk.resource.getText("CCG001_41") }
                ]);
                // Define gridlist's columns
                self.workTypeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CCG001_60'), prop: 'businessTypeCode', width: 100 },
                    { headerText: nts.uk.resource.getText('CCG001_61'), prop: 'businessTypeName', width: 200 }
                ]);

                self.employeeListTab3 = ko.observableArray([]);
                self.selectedEmployeesTab3 = ko.observableArray([]);
                self.inputNameTab3 = ko.observable("");
                self.inputCodeTab3 = ko.observable("");
                self.entryDateTab3 = ko.observable({});
                self.retirementDateTab3 = ko.observable({});
            }
            
            /**
             * Set QuickSearchParam
             */
            private setQuickSearchParam(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                let param = self.queryParam;
                param.filterByEmployment = false;
                param.employmentCodes = [];
                param.filterByDepartment = false;
                param.departmentCodes = [];
                param.filterByWorkplace = false;
                param.workplaceCodes = [];
                param.filterByClassification = false;
                param.classificationCodes = [];
                param.filterByJobTitle = false;
                param.jobTitleCodes = [];
                param.filterByWorktype = false;
                param.worktypeCodes = [];
                param.filterByClosure = false;
                param.closureIds = [];
                param.includeIncumbents = true;
                param.includeWorkersOnLeave = true;
                param.includeOccupancy = true;
                param.includeRetirees = false;
                param.systemType = self.systemType;
                param.sortOrderNo = 1; // 並び順NO＝1
                param.nameType = 1; // ビジネスネーム（日本語）

                // set employments code condition
                if (self.showClosure && self.selectedClosure() != ConfigEnumClosure.CLOSURE_ALL) {
                    service.getEmploymentCodeByClosureId(self.selectedClosure()).done(data => {
                        param.filterByEmployment = true;
                        param.employmentCodes = data;
                        dfd.resolve();
                    });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            private initQueryParam(): void {
                let self = this;
                self.queryParam = <EmployeeQueryParam>{};
                self.queryParam.sortOrderNo = 1; // 並び順NO＝1
                self.queryParam.nameType = 1; // ビジネスネーム（日本語）
                self.queryParam.baseDate = moment().format(CcgDateFormat.DEFAULT_FORMAT);
            }

            /**
             * update select tabs
             */
             
            public updateTabs(): Array<any> {
                let self = this;
                let arrTabs = [];
                // is quick search tab
                if (self.showQuickSearchTab) {
                    // push tab 1
                    arrTabs.push({
                        id: 'tab-1',
                        title: nts.uk.resource.getText("CCG001_22"),
                        content: '.tab-content-1',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                // is advanced search tab
                if (self.showAdvancedSearchTab) {
                    // push tab 2
                    arrTabs.push({
                        id: 'tab-2',
                        title: nts.uk.resource.getText("CCG001_23"),
                        content: '.tab-content-2',
                        enable: ko.observable(true),
                        visible: ko.observable(true)
                    });
                }
                arrTabs.push({
                    id: 'tab-3',
                    title: nts.uk.resource.getText("CCG001_103"),
                    content: '#ccg001-tab-content-3',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                });
                // => data res
                return arrTabs;
            }

            /**
             * get tab by update selected 
             */

            public updateSelectedTab(): string {
                var self = this;
                // res tab 1
                if (self.showQuickSearchTab) {
                    return 'tab-1';
                }
                // res tab 2
                if (self.showAdvancedSearchTab) {
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
                const TAB_KEY_CODE = 9;

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

                // when tab to last item of tab 1
                $("[tabindex='10']").on('keydown', function(e) {
                    if (e.which == TAB_KEY_CODE && self.showAdvancedSearchTab) {
                        // switch to tab 2
                        self.selectedTab('tab-2');

                        // auto open accordion
                        if (!self.isOpenStatusOfEmployeeList()) {
                            $('#tab-2').find('#StatusOfEmployeeList').find('.ui-accordion-header').click();
                        }
                        $("[tabindex='11']").on('keydown', function(e) {
                            if (e.which == TAB_KEY_CODE) {
                                if (!self.isOpenEmploymentList()) {
                                    $('#tab-2').find('#EmploymentList').find('.ui-accordion-header').click();
                                }
                            }
                            $("[tabindex='12']").on('keydown', function(e) {
                                if (e.which == TAB_KEY_CODE) {
                                    if (!self.isOpenClassificationList()) {
                                        $('#tab-2').find('#ClassificationList').find('.ui-accordion-header').click();
                                    }
                                }
                                $("[tabindex='13']").on('keydown', function(e) {
                                    if (e.which == TAB_KEY_CODE) {
                                        if (!self.isOpenJoptitleList()) {
                                            $('#tab-2').find('#JoptitleList').find('.ui-accordion-header').click();
                                        }
                                    }
                                    $("[tabindex='14']").on('keydown', function(e) {
                                        if (e.which == TAB_KEY_CODE) {
                                            if (!self.isOpenWorkplaceList()) {
                                                $('#tab-2').find('#WorkplaceList').find('.ui-accordion-header').click();
                                            }
                                        }
                                        $("[tabindex='15']").on('keydown', function(e) {
                                            if (e.which == TAB_KEY_CODE) {
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
                    if (e.which == TAB_KEY_CODE && !self.showAdvancedSearchTab) {
                        // switch to tab 3
                        self.selectedTab('tab-3');
                    }
                });
                // when tab to last item of tab 2
                if (self.showEmployeeSelection) {
                    $("[tabindex='34']").on('keydown', function(e) {
                        if (e.which == TAB_KEY_CODE) {
                            // switch to tab 3
                            self.selectedTab('tab-3');
                        }
                    });
                } else {
                    $("[tabindex='32']").on('keydown', function(e) {
                        if (e.which == TAB_KEY_CODE) {
                            // switch to tab 3
                            self.selectedTab('tab-3');
                        }
                    });
                }
                // when tab to last item of tab 3
                $("[tabindex='43']").on('keydown', function(e) {
                    if (e.which == TAB_KEY_CODE && self.showQuickSearchTab) {
                        // switch to tab 1
                        self.selectedTab('tab-1');
                    }
                    if (e.which == TAB_KEY_CODE && !self.showQuickSearchTab && self.showAdvancedSearchTab) {
                        // switch to tab 2
                        self.selectedTab('tab-2');
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

                // start component
                nts.uk.ui.block.invisible(); // block ui
                self.startComponent().done(() => {
                    self.setShowHideByReferenceRange();

                    // Initial tab panel
                    self.tabs(self.updateTabs());
                    self.selectedTab(self.updateSelectedTab());

                    // init view
                    let webserviceLocator = nts.uk.request.location.siteRoot
                        .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                        .mergeRelativePath('/view/ccg/share/index.xhtml').serialize();
                    $input.load(webserviceLocator, function() {
                        ko.cleanNode($input[0]);
                        ko.applyBindings(self, $input[0]);
                        // Set tabindex
                        self.initNextTabFeature();
                        let tabindex = $input.attr('tabindex');
                        $input.attr('tabindex', -1);
                        $input.find('.btn_showhide').attr('tabindex', tabindex);

                        // init ccg show/hide event
                        self.initCcgEvent();
                        // set component height
                        self.setComponentHeight();

                        if (data.showOnStart) {
                            self.showComponent().done(() => dfd.resolve());
                        } else {
                            dfd.resolve();
                        }

                    });
                });

                return dfd.promise();
            }

            private setShowHideByReferenceRange(): void {
                let self = this;
                // set advanced search tab flag
                self.showAdvancedSearchTab = self.showAdvancedSearchTab &&
                    (self.referenceRange != EmployeeReferenceRange.ONLY_MYSELF);
                // always show quick search if advanced search is hidden
                self.showQuickSearchTab = self.showAdvancedSearchTab ? self.showQuickSearchTab : true;

                self.showAllReferableEmployee = self.referenceRange != EmployeeReferenceRange.ONLY_MYSELF
                    && self.showAllReferableEmployee;
                self.showSameWorkplace = self.referenceRange != EmployeeReferenceRange.ONLY_MYSELF
                    && self.showSameWorkplace;
                self.showSameWorkplaceAndChild = (self.referenceRange == EmployeeReferenceRange.ALL_EMPLOYEE
                    || self.referenceRange == EmployeeReferenceRange.DEPARTMENT_AND_CHILD)
                    && self.showSameWorkplaceAndChild;
            }

            /**
             * Start component
             */
            private startComponent(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                $.when(service.getRefRangeBySysType(self.systemType),
                    self.loadClosure()
                ).done((refRange, noValue) => {
                    self.referenceRange = refRange;
                    dfd.resolve();
                }).fail(err => nts.uk.ui.dialog.alertError(err));

                return dfd.promise();
            }

            /**
             * Set advanced search param
             */
            private setAdvancedSearchParam(): void {
                let self = this;
                let param = this.queryParam;
                param.referenceRange = SearchReferenceRange.ALL_EMPLOYEE;

                // filter param
                param.filterByEmployment = self.showEmployment;
                // not covered param.filterByDepartment = options.showDepartment;
                param.filterByWorkplace = self.showWorkplace;
                param.filterByClassification = self.showClassification;
                param.filterByJobTitle = self.showJobTitle;
                param.filterByWorktype = self.showWorktype;
                param.filterByClosure = self.showClosure && self.selectedClosure() != ConfigEnumClosure.CLOSURE_ALL;

                // filter status of employment
                param.includeIncumbents = self.selectedIncumbent();
                param.includeWorkersOnLeave = self.selectedLeave();
                param.includeOccupancy = self.selectedClosed();
                param.includeRetirees = self.selectedRetirement();
                param.retireStart = self.statusPeriodStart().format(CcgDateFormat.DEFAULT_FORMAT);
                param.retireEnd = self.statusPeriodEnd().format(CcgDateFormat.DEFAULT_FORMAT);
                param.systemType = self.systemType;

                self.queryParam.employmentCodes = self.showEmployment ? self.selectedCodeEmployment() : [];
                self.queryParam.classificationCodes = self.showClassification ? self.selectedCodeClassification() : [];
                self.queryParam.jobTitleCodes = self.showJobTitle ? self.selectedCodeJobtitle() : [];
                self.queryParam.workplaceCodes = self.showWorkplace ? self.selectedCodeWorkplace() : [];
                self.queryParam.worktypeCodes = self.showWorktype ? self.selectedWorkTypeCode() : [];
                self.queryParam.closureIds = self.showClosure ? [self.selectedClosure()] : [];
            }

            /**
             * Set component properties
             */
            private setProperties(options: GroupOption): void {
                let self = this;

                /** Common properties */
                self.showEmployeeSelection = _.isNil(options.showEmployeeSelection) ? false : options.showEmployeeSelection;
                self.systemType = options.systemType;
                self.showQuickSearchTab = _.isNil(options.showQuickSearchTab) ? true : options.showQuickSearchTab;
                self.showAdvancedSearchTab = _.isNil(options.showAdvancedSearchTab) ? false : options.showAdvancedSearchTab;
                // showBaseDate and showPeriod can not hide at the same time
                const isBaseDateAndPeriodHidden = !options.showBaseDate && !options.showPeriod;
                self.showBaseDate = _.isNil(options.showBaseDate) ? true : (isBaseDateAndPeriodHidden ? true : options.showBaseDate);
                self.showAllClosure = _.isNil(options.showAllClosure) ? false : options.showAllClosure;
                self.showPeriod = _.isNil(options.showPeriod) ? false : options.showPeriod;
                self.showClosure = _.isNil(options.showClosure) ? false : options.showClosure;
                // if ShowPeriod = false then period accuracy must be false too. 
                self.showPeriodYM = _.isNil(self.showPeriod) ? false : (self.showPeriod ? options.periodFormatYM : false);
                self.isTab2Lazy = _.isNil(options.isTab2Lazy) ? true : options.isTab2Lazy;

                /** Required parameter */
                self.inputBaseDate(_.isNil(options.baseDate) ? moment().toISOString() : options.baseDate);
                const periodStart = _.isNil(options.periodStartDate) ? moment().toISOString() : options.periodStartDate;
                const periodEnd = _.isNil(options.periodEndDate) ? moment().toISOString() : options.periodEndDate;
                self.inputPeriod(new DateRangePickerModel(periodStart, periodEnd));
                self.selectedIncumbent(options.inService);
                self.selectedLeave(options.leaveOfAbsence);
                self.selectedClosed(options.closed);
                self.selectedRetirement(options.retirement);

                /** Quick search tab options */
                self.showAllReferableEmployee = _.isNil(options.showAllReferableEmployee) ? true : options.showAllReferableEmployee;
                self.showOnlyMe = true;
                self.showSameWorkplace = _.isNil(options.showSameWorkplace) ? true : options.showSameWorkplace;
                self.showSameWorkplaceAndChild = _.isNil(options.showSameWorkplaceAndChild) ? true : options.showSameWorkplaceAndChild;

                /** Advanced search properties */
                self.showEmployment = _.isNil(options.showEmployment) ? true : options.showEmployment;
                self.showWorkplace = _.isNil(options.showWorkplace) ? true : options.showWorkplace;
                self.showClassification = _.isNil(options.showClassification) ? true : options.showClassification;
                self.showJobTitle = _.isNil(options.showJobTitle) ? true : options.showJobTitle;
                self.showWorktype = self.systemType == ConfigEnumSystemType.EMPLOYMENT ? options.showWorktype : false;
                self.isMultiple = _.isNil(options.isMutipleCheck) ? true : options.isMutipleCheck;

                /** Optional properties */
                self.isInDialog = _.isNil(options.isInDialog) ? false : options.isInDialog;

                // return data function
                self.returnDataFromCcg001 = options.returnDataFromCcg001;
            }

            /**
             * Set component height
             */
            private setComponentHeight(): void {
                let self = this;
                const headerHeight = $('#header').outerHeight();
                const sidebarHeaderHeight = $('.sidebar-content-header').outerHeight(); // for screen with sidebar
                const functionAreaHeight = $('#functions-area').length > 0 ? $('#functions-area').outerHeight() : 0;
                const buffer = 15;
                let componentHeight = 0;

                // calculate component height
                if (self.isInDialog) {
                    componentHeight = window.innerHeight - functionAreaHeight - buffer;
                } else {
                    const notIncluded = headerHeight + functionAreaHeight + (sidebarHeaderHeight ? sidebarHeaderHeight : 0) + buffer;
                    componentHeight = window.innerHeight - notIncluded;
                }

                const minHeight = 450;
                if (componentHeight < minHeight) {
                    componentHeight = minHeight;
                }

                // set component height
                $('#component-ccg001').outerHeight(componentHeight);
                $('#hor-scroll-button-hide').outerHeight(componentHeight);
                $('#ccg001-btn-search-drawer').outerHeight(componentHeight / 2);

                // set tab panel height.
                const tabpanelHeight = componentHeight - $('#ccg001-header').outerHeight(true) - 10;
                const tabpanelNavHeight = 85;
                const tabpanelContentHeight = tabpanelHeight - tabpanelNavHeight;
                $('.ccg-tabpanel.pull-left').outerHeight(tabpanelHeight);
                $('.ccg-tabpanel>#tab-1').css('height', tabpanelContentHeight);
                $('.ccg-tabpanel>#tab-2').css('height', tabpanelContentHeight);
                $('.ccg-tabpanel>#tab-3').css('height', tabpanelContentHeight);
            }

            /**
             * Load ListClosure 
             */
            private loadClosure(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showClosure) {
                    service.getClosuresByBaseDate(self.baseDate().format(CcgDateFormat.DEFAULT_FORMAT)).done(data => {
                        if (self.showAllClosure) {
                            data.unshift({
                                closureId: ConfigEnumClosure.CLOSURE_ALL,
                                closureName: ConfigEnumClosure.CLOSURE_ALL_NAME
                            });
                        }

                        // set closure list
                        self.closureList(data);

                        self.getSelectedClosure().done(selected => {
                            // set selected closure
                            self.selectedClosure(selected);

                            // initialize selected cosure subscriber
                            self.selectedClosure.subscribe(vl => {
                                // calculate period by current month
                                self.calculatePeriod(parseInt(moment().format(CcgDateFormat.YEAR_MONTH))).done(period => {
                                    self.isApplySearchDone = false;
                                    self.inputPeriod(new DateRangePickerModel(period.startDate, period.endDate));
                                    self.isApplySearchDone = true;

                                    // apply data search
                                    self.applyDataSearch();
                                });
                            });

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
                service.getEmployeeRangeSelection().done((data: EmployeeRangeSelection) => {
                    if (data) {
                        // set employeeRangeSelection
                        self.employeeRangeSelection = data;

                        // get selected closure id
                        switch (self.systemType) {
                            case ConfigEnumSystemType.PERSONAL_INFORMATION:
                                if (!nts.uk.util.isNullOrEmpty(data.personalInfo.selectedClosureId)) {
                                    dfd.resolve(data.personalInfo.selectedClosureId);
                                } else {
                                    self.getSelectedClosureByEmployment().done(id => dfd.resolve(id));
                                }
                                break;
                            case ConfigEnumSystemType.EMPLOYMENT:
                                if (!nts.uk.util.isNullOrEmpty(data.employmentInfo.selectedClosureId)) {
                                    dfd.resolve(data.employmentInfo.selectedClosureId);
                                } else {
                                    self.getSelectedClosureByEmployment().done(id => dfd.resolve(id));
                                }
                                break;
                            case ConfigEnumSystemType.SALARY:
                                if (!nts.uk.util.isNullOrEmpty(data.salaryInfo.selectedClosureId)) {
                                    dfd.resolve(data.salaryInfo.selectedClosureId);
                                } else {
                                    self.getSelectedClosureByEmployment().done(id => dfd.resolve(id));
                                }
                                break;
                            case ConfigEnumSystemType.HUMAN_RESOURCES:
                                if (!nts.uk.util.isNullOrEmpty(data.humanResourceInfo.selectedClosureId)) {
                                    dfd.resolve(data.humanResourceInfo.selectedClosureId);
                                } else {
                                    self.getSelectedClosureByEmployment().done(id => dfd.resolve(id));
                                }
                                break;
                            default: break; // systemType not found
                        }
                    } else {
                        self.getSelectedClosureByEmployment().done(id => dfd.resolve(id));
                    }
                });
                return dfd.promise();
            }

            /**
             * Get selected closure by employment
             */
            private getSelectedClosureByEmployment(): JQueryPromise<number> {
                let dfd = $.Deferred<number>();
                service.getCurrentHistoryItem().done(item => {
                    if (item) {
                        service.getClosureTiedByEmployment(item.employmentCode).done(id => dfd.resolve(id));
                    } else {
                        const DEFAULT_VALUE = 1;
                        // Q&A: #88282 (update specs)
                        dfd.resolve(DEFAULT_VALUE);
                    }
                });
                return dfd.promise();
            }

            /**
             * Initial ccg event
             */
            private initCcgEvent(): void {
                let self = this;
                $(window).on('click', function(e) {
                    // Check is click to inside component.
                    if (e.target.id == "component-ccg001" || $(e.target).parents("#component-ccg001")[0]) {
                        return;
                    }
                    // click when block ui
                    if (!_.isEmpty($('div.ui-widget-overlay.ui-front'))) {
                        return;
                    }
                    if (!_.isEmpty($('div.blockUI.blockOverlay'))) {
                        return;
                    }
                    // check is click to errors notifier
                    if (e.target.id == 'func-notifier-errors') {
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
            public showComponent(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.isFirstTime) {
                    // Apply data search & load Kcp components
                    self.toggleSlide().done(() => $.when(self.applyDataSearch(), self.loadKcp005()).always(() => {
                        // Set acquired base date to status period end date
                        self.inputStatusPeriodEnd(moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toISOString());

                        // init subscribers
                        self.initSubscribers();

                        // update flag isFirstTime
                        self.isFirstTime = false;
                        dfd.resolve();
                    }));
                } else {
                    // toggle slide ccg001
                    self.toggleSlide();
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Toggle slide CCG001
             */
            private toggleSlide(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.isShow()) {
                    return;
                }
                let componentElement = document.getElementById('component-ccg001');
                if (componentElement.style.visibility == 'hidden') {
                    componentElement.style.removeProperty('visibility');
                    componentElement.style.display = 'none';
                }
                $('#component-ccg001').toggle("slide", () => dfd.resolve());
                self.isShow(true);
                return dfd.promise();
            }

            /**
             * Load component KCP005
             */
            private loadKcp005(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;

                $.when(self.loadKcp005OnTab2(), self.loadKcp005OnTab3()).done(() => {
                    self.fixComponentWidth();
                    dfd.resolve();
                });

                return dfd.promise();
            }

            /**
             * Calculate KCP005 rows
             */
            private calculateKcp005Rows(marginHeight: number): number {
                const tabContentHeight = parseInt(document.querySelector('.ccg-tabpanel>#tab-3').style.height);
                const heightPerRow = 24;
                return (tabContentHeight - marginHeight) / heightPerRow;
            }

            private loadKcp005OnTab2(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showAdvancedSearchTab && self.showEmployeeSelection) {
                    const Kcp005MarginHeight = 70;

                    // set KCP005 options
                    self.employeeinfo = {
                        isShowAlreadySet: false,
                        isMultiSelect: self.isMultiple,
                        isMultipleUse: true,
                        listType: ListType.EMPLOYEE,
                        employeeInputList: ko.observableArray([]),
                        selectType: SelectType.SELECT_BY_SELECTED_CODE,
                        selectedCode: self.selectedCodeEmployee,
                        isDialog: true,
                        hasPadding: false,
                        isShowNoSelectRow: false,
                        isShowWorkPlaceName: false,
                        maxRows: self.calculateKcp005Rows(Kcp005MarginHeight),
                        subscriptions: self.employeeSubscriptions
                    }

                    // Show KCP005
                    $('#employeeinfo').ntsListComponent(self.employeeinfo).done(() => dfd.resolve());
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            private loadKcp005OnTab3(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                // Load KCP05 on tab 3
                const Kcp005MarginHeight = 255;
                const calculatedRows = self.calculateKcp005Rows(Kcp005MarginHeight);
                const maxRows = calculatedRows < 10 ? 10 : calculatedRows;

                self.tab3kcp005option = {
                    isShowAlreadySet: false,
                    maxWidth: 420,
                    isMultiSelect: self.isMultiple,
                    isMultipleUse: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeListTab3,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedEmployeesTab3,
                    isDialog: true,
                    hasPadding: false,
                    isShowNoSelectRow: false,
                    isShowWorkPlaceName: true,
                    maxRows: maxRows,
                }

                // Show KCP005
                $('#tab3kcp005').ntsListComponent(self.tab3kcp005option).done(() => dfd.resolve());
                return dfd.promise();
            }

            /**
             * Fix component width according to screen width
             */
            private fixComponentWidth(): void {
                let self = this;
                _.defer(() => {
                    // update tab 2 width
                    let totalWidth = 5;
                    $('#ccg001-tab-content-2').children('div.pull-left.height-maximum').each((i, e) => totalWidth += $(e).outerWidth(true));
                    $('#ccg001-tab-content-2').outerWidth(totalWidth);

                    // Fix component width if screen width is smaller than component
                    const componentWidth = window.innerWidth - $('#hor-scroll-button-hide').offset().left;
                    if (componentWidth <= $('#ccg001-tab-content-2').outerWidth()) {
                        const margin = 20;
                        // fix width and show scrollbar
                        $('.tab-content-2.height-maximum').outerWidth(componentWidth - margin);
                        $('.tab-content-2.height-maximum').css('overflow-x', 'auto');

                        // fix height
                        if (!self.isHeightFixed) {
                            const fixedTabHeight = parseInt(document.querySelector('.ccg-tabpanel>#tab-2').style.height) + 15;
                            $('.ccg-tabpanel>#tab-2').css('height', fixedTabHeight);
                            self.isHeightFixed = true;
                        }
                    }
                });
            }

            /**
             * Check future date
             */
            private isFutureDate(date: moment.Moment): boolean {
                return date.isAfter(moment());
            }

            /**
             * function click by apply data search employee (init tab 2)
             * get base date
             */
            applyDataSearch(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                if (!self.isApplySearchDone) {
                    dfd.resolve();
                    return dfd.promise();
                }

                // validate input base date
                if (self.isInvalidBaseDate()) {
                    dfd.reject();
                    return dfd.promise();
                }

                self.isApplySearchDone = false;
                nts.uk.ui.block.invisible(); // block ui
                self.setBaseDateAndPeriod().done(() => {
                    // Comparing accquired base date to current system date.
                    if (self.isFutureDate(moment.utc(self.acquiredBaseDate(), CcgDateFormat.DEFAULT_FORMAT))) {
                        // If base date is future date, check future reference permission
                        self.getFuturePermit().done(hasPermission => {
                            if (hasPermission) {
                                self.queryParam.baseDate = self.acquiredBaseDate();
                            } else {
                                self.inputBaseDate(moment.utc().toISOString());
                                self.queryParam.baseDate = moment().format(CcgDateFormat.DEFAULT_FORMAT); // set basedate = current system date
                            }
                            self.loadAdvancedSearchTab().done(() => {
                                self.isApplySearchDone = true;
                                nts.uk.ui.block.clear();
                                dfd.resolve();
                            });
                        }).fail(err => {
                            nts.uk.ui.dialog.alertError(err);
                            self.isApplySearchDone = true;
                            nts.uk.ui.block.clear();
                            dfd.reject();
                        });
                    } else {
                        self.queryParam.baseDate = self.acquiredBaseDate();
                        self.loadAdvancedSearchTab().done(() => {
                            self.isApplySearchDone = true;
                            nts.uk.ui.block.clear();
                            dfd.resolve();
                        });
                    }
                }).fail(err => {
                    nts.uk.ui.dialog.alertError(err);
                    self.isApplySearchDone = true;
                    nts.uk.ui.block.clear();
                    dfd.reject();
                });

                return dfd.promise();
            }

            /**
             * Load advanced search tab
             */
            private loadAdvancedSearchTab(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                if ((!self.isTab2Lazy || !self.isFirstTime) && self.showAdvancedSearchTab) {
                    self.reloadAdvanceSearchTab().done(() => dfd.resolve());
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Reload advanced search tab
             */
            private reloadAdvanceSearchTab(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;
                if (!self.tab2HasLoaded) {
                    self.tab2HasLoaded = true;
                }
                // set advanced search param
                self.queryParam.retireStart = self.statusPeriodStart().format(CcgDateFormat.DEFAULT_FORMAT);
                self.queryParam.retireEnd = self.statusPeriodEnd().format(CcgDateFormat.DEFAULT_FORMAT);

                // reload advanced search tab.
                nts.uk.ui.block.invisible();
                self.setComponentOptions();
                self.loadEmploymentPart()
                    .done(() => self.loadClassificationPart()
                        .done(() => self.loadJobTitlePart()
                            .done(() => self.loadWorkplacePart()
                                .done(() => self.loadWorktypePart()
                                    .done(() => {
                                        nts.uk.ui.block.clear();// clear block UI
                                        self.fixComponentWidth();
                                        dfd.resolve();
                                    })))));
                return dfd.promise();
            }

            /**
             * Load employment part
             */
            private loadEmploymentPart(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showEmployment) {
                    if (_.isNil(self.comEmployment)) {
                        $('#employmentList').ntsListComponent(self.employments).done(emp => {
                            self.comEmployment = emp;
                            dfd.resolve();
                        });
                    } else {
                        self.comEmployment.reload();
                        dfd.resolve();
                    }
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Load Classification part
             */
            private loadClassificationPart(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showClassification) {
                    if (_.isNil(self.comClassification)) {
                        $('#classificationList').ntsListComponent(self.classifications).done(emp => {
                            self.comClassification = emp;
                            dfd.resolve();
                        });
                    } else {
                        self.comClassification.reload();
                        dfd.resolve();
                    }
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Load jobtitle part
             */
            private loadJobTitlePart(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showJobTitle) {
                    if (_.isNil(self.comJobTitle)) {
                        $('#jobtitleList').ntsListComponent(self.jobtitles).done(emp => {
                            self.comJobTitle = emp;
                            dfd.resolve();
                        });
                    } else {
                        self.comJobTitle.reload();
                        dfd.resolve();
                    }
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Load workplace part
             */
            private loadWorkplacePart(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showWorkplace) {
                    service.searchWorkplaceOfEmployee(moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toDate())
                        .done(selectedCodes => {
                            self.selectedCodeWorkplace(selectedCodes);
                            $('#workplaceList').ntsTreeComponent(self.workplaces).done(() => {
                                dfd.resolve();
                            });
                        })
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * Load worktype part
             */
            private loadWorktypePart(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showWorktype) {
                    service.searchAllWorkType().done((workTypeList: Array<BusinessType>) => {
                        self.listWorkType(workTypeList);
                        self.selectedWorkTypeCode(_.map(workTypeList, vl => vl.businessTypeCode));
                        dfd.resolve();
                    });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();
            }

            /**
             * function click by button detail work place (open dialog)
             */
            detailWorkplace(): void {
                let self = this;
                let inputCDL008 = {
                    baseDate: moment.utc(self.queryParam.baseDate, 'YYYY-MM-DD').toDate(),
                    isMultiple: true,
                    selectedCodes: self.selectedCodeWorkplace()
                };
                nts.uk.ui.windows.setShared('inputCDL008', inputCDL008);
                nts.uk.ui.windows.sub.modal('com',"/view/cdl/008/a/index.xhtml").onClosed(() => {
                    if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
                        return;
                    }
                    // reload KCP004
                    self.selectedCodeWorkplace(nts.uk.ui.windows.getShared('outputCDL008'));
                    $('#workplaceList').ntsTreeComponent(self.workplaces);
                });
            }

            /**
             * function click by button search employee
             */
            extractSelectedEmployees(): void {
                let self = this;
                if (nts.uk.util.isNullOrEmpty(self.getSelectedCodeEmployee())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_758" });
                    return;
                }

                // Filter selected employee
                let selectedEmployees = self.getSelectedCodeEmployee();
                let filteredList = _.filter(self.reservedEmployees(), e => {
                    return _.includes(selectedEmployees, e.employeeCode);
                });

                // block ui
                nts.uk.ui.block.invisible();

                // return data
                self.returnDataFromCcg001(self.combineData(filteredList));

                // Hide component.
                self.hideComponent();

                // clear block UI
                _.defer(() => nts.uk.ui.block.clear());
            }

            public extractSelectedEmployeesInTab3(): void {
                let self = this;
                if (nts.uk.util.isNullOrEmpty(self.getSelectedCodeEmployeeTab3())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_758" });
                    return;
                }

                // Filter selected employee
                let selectedEmployees = self.getSelectedCodeEmployeeTab3();
                let filteredList = _.filter(self.reservedEmployeesTab3(), e => {
                    return _.includes(selectedEmployees, e.employeeCode);
                });

                // block ui
                nts.uk.ui.block.invisible();

                // return data
                self.returnDataFromCcg001(self.combineData(filteredList));

                // Hide component.
                self.hideComponent();

                // clear block UI
                _.defer(() => nts.uk.ui.block.clear());
            }

            /**
             * clear validate client
             */
            clearValiate() {
                $('#inp_baseDate').ntsError('clear');
            }

            /**
             * Validate base date
             */
            isInvalidBaseDate(): boolean {
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

            // validate input
            private isStatusEmployeePeriodInvalid(): boolean {
                let self = this;
                $("#ccg001-partg-start").ntsEditor("validate");
                $("#ccg001-partg-end").ntsEditor("validate");
                return $("#ccg001-partg-start").ntsError('hasError') || $("#ccg001-partg-end").ntsError('hasError');
            }

            /**
             * function click by button employee login
             */
            getEmployeeLogin(): void {
                let self = this;
                if (self.isInvalidBaseDate()) {
                    return;
                }
                nts.uk.ui.block.invisible(); // block ui
                service.searchEmployeeByLogin(moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toDate())
                    .done(data => {
                        self.returnDataFromCcg001(self.combineData(data));
                        self.hideComponent();
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => nts.uk.ui.block.clear());  // clear block UI
            }

            /**
             * Combine return data
             */
            private combineData(listEmployee: Array<EmployeeSearchDto>): Ccg001ReturnedData {
                let self = this;
                let dto = <Ccg001ReturnedData>{};
                dto.baseDate = moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toISOString();
                dto.closureId = self.showClosure ? self.selectedClosure() : undefined;
                dto.periodStart = moment.utc(self.queryParam.periodStart).toISOString();
                dto.periodEnd = moment.utc(self.queryParam.periodEnd).toISOString();
                dto.listEmployee = listEmployee;
                return dto;
            }

            /**
             * Set base date and period
             */
            public setBaseDateAndPeriod(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let self = this;

                // set base date = user input
                if (self.showBaseDate) {
                    self.acquiredBaseDate(self.baseDate().format(CcgDateFormat.DEFAULT_FORMAT));
                }

                // set period
                if (self.showPeriod) {
                    self.queryParam.periodStart = self.periodStart().format(CcgDateFormat.DEFAULT_FORMAT);
                    self.queryParam.periodEnd = self.periodEnd().format(CcgDateFormat.DEFAULT_FORMAT);
                    if (!self.showBaseDate) {
                        self.acquiredBaseDate(self.queryParam.periodEnd);
                    }
                } else {
                    // set period = base date (Period accuracy is YMD)
                    self.queryParam.periodStart = self.baseDate().format(CcgDateFormat.DEFAULT_FORMAT);
                    self.queryParam.periodEnd = self.baseDate().format(CcgDateFormat.DEFAULT_FORMAT);
                }

                // Period accuracy is YM 
                if (self.showPeriodYM) {
                    self.calculatePeriod(parseInt(self.periodEnd().format(CcgDateFormat.YEAR_MONTH))).done(period => {
                        if (!self.showBaseDate) {
                            // set base date = period end
                            self.acquiredBaseDate(period.endDate);
                        }

                        dfd.resolve();
                    });
                } else {
                    dfd.resolve();
                }

                return dfd.promise();
            }

            /**
             * Calculate date period from selected closure id and yearMonth
             */
            public calculatePeriod(yearMonth: number): JQueryPromise<DatePeriodDto> {
                let self = this;
                let dfd = $.Deferred<DatePeriodDto>();
                const closureId = self.selectedClosure() == ConfigEnumClosure.CLOSURE_ALL ? 1 : self.selectedClosure();
                // アルゴリズム「当月の期間を算出する」を実行する
                service.calculatePeriod(closureId, yearMonth)
                    .done(period => dfd.resolve(period));
                return dfd.promise();
            }

            /**
             * Get future reference permission
             */
            public getFuturePermit(): JQueryPromise<boolean> {
                let self = this;
                switch (self.systemType) {
                    case ConfigEnumSystemType.PERSONAL_INFORMATION:
                        return service.getPersonalRoleFuturePermit();
                    case ConfigEnumSystemType.EMPLOYMENT:
                        return service.getEmploymentRoleFuturePermit();
                    default: 
                        let dfd = $.Deferred<boolean>();
                        dfd.reject();
                        return dfd.promise();// systemType not found
                }
            }

            /**
             * Validate basedate & target period
             */
            public isBaseDateInTargetPeriod(): boolean {
                let self = this;
                let baseDate = self.baseDate();
                
                if (self.showPeriodYM){
                    baseDate = moment.utc((self.baseDate()).format("YYYY/MM"), "YYYY/MM"); 
                } 
                
                if (baseDate.isBefore(self.periodStart()) || baseDate.isAfter(self.periodEnd())) {
                    if (!self.hasShownErrorDialog) {
                        self.hasShownErrorDialog = true;
                        nts.uk.ui.dialog.alertError({ messageId: 'Msg_765' }).then(() => self.hasShownErrorDialog = false);
                    }
                    return false;
                }
                return true;
            }

            /**
             * function click apply search employee
             */
            advancedSearchEmployee(): void {
                let self = this;
                // validate all inputs & conditions
                if (self.isInvalidBaseDate() || self.isStatusEmployeePeriodInvalid()) {
                    return;
                }
                if (!self.isValidAdvancedSearchCondition()) {
                    return;
                }
                
                // set param
                self.setAdvancedSearchParam();

                nts.uk.ui.block.invisible(); // block ui
                if (self.showClosure) { // save EmployeeRangeSelection if show closure
                    // check data exist
                    let empRangeSelection = self.employeeRangeSelection ?
                        self.employeeRangeSelection : new EmployeeRangeSelection();

                    switch (self.systemType) {
                        case ConfigEnumSystemType.PERSONAL_INFORMATION:
                            empRangeSelection.personalInfo.selectedClosureId = self.selectedClosure();
                            break;
                        case ConfigEnumSystemType.EMPLOYMENT:
                            empRangeSelection.employmentInfo.selectedClosureId = self.selectedClosure();
                            break;
                        case ConfigEnumSystemType.SALARY:
                            empRangeSelection.salaryInfo.selectedClosureId = self.selectedClosure();
                            break;
                        case ConfigEnumSystemType.HUMAN_RESOURCES:
                            empRangeSelection.humanResourceInfo.selectedClosureId = self.selectedClosure();
                            break;
                        default: break; // systemType not found
                    }
                    service.saveEmployeeRangeSelection(empRangeSelection).done(() => {
                        self.findAndReturnListEmployee(true);
                    });
                } else {
                    self.findAndReturnListEmployee(true);
                }
            }

            /**
             * Check advanced search conditions
             */
            private isValidAdvancedSearchCondition(): boolean {
                let self = this;
                if (self.showEmployment && nts.uk.util.isNullOrEmpty(self.selectedCodeEmployment())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1195' });
                    return false;
                }
                if (self.showWorkplace && nts.uk.util.isNullOrEmpty(self.selectedCodeWorkplace())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1197' });
                    return false;
                }
                if (self.showClassification && nts.uk.util.isNullOrEmpty(self.selectedCodeClassification())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1198' });
                    return false;
                }
                if (self.showJobTitle && nts.uk.util.isNullOrEmpty(self.selectedCodeJobtitle())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1199' });
                    return false;
                }
                if (self.showWorktype && nts.uk.util.isNullOrEmpty(self.selectedWorkTypeCode())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1200' });
                    return false;
                }
                return true;
            }

            /**
             * function get selected employee to
             */
            public getSelectedCodeEmployee(): string[]{
                var self = this;
                if (self.isMultiple) {
                    return self.selectedCodeEmployee();
                } else {
                    let employeeCodes = [];
                    if (!nts.uk.util.isNullOrEmpty(self.selectedCodeEmployee())) {
                        employeeCodes.push(self.selectedCodeEmployee());
                    }
                    return employeeCodes;
                }
            }        

            /**
             * Get selected code employee in tab3
             */
            public getSelectedCodeEmployeeTab3(): string[]{
                let self = this;
                if (self.isMultiple) {
                    return self.selectedEmployeesTab3();
                } else {
                    let employeeCodes = [];
                    if (!nts.uk.util.isNullOrEmpty(self.selectedEmployeesTab3())) {
                        employeeCodes.push(self.selectedEmployeesTab3());
                    }
                    return employeeCodes;
                }
            }        

            /**
             * Show data to kcp005 on tab 3
             */
            private showDataOnKcp005Tab3(data: Array<EmployeeSearchDto>): void {
                let self = this;
                // Data not found
                if (nts.uk.util.isNullOrEmpty(data)) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_317" });
                    return;
                }

                // sort by code
                const sortedList = _.sortBy(data, item => item.employeeCode);

                // reserve list data
                self.reservedEmployeesTab3(sortedList);

                // clear selected codes
                self.selectedEmployeesTab3([]);

                // set data to kcp005
                self.employeeListTab3(self.toUnitModelList(sortedList));
            }
            
            /**
             * function convert dto to model init data 
             */
            public toUnitModelList(dataList: EmployeeSearchDto[]): Array<UnitModel> {
                return _.map(dataList, item => {
                    return {
                        code: item.employeeCode,
                        name: item.employeeName,
                        workplaceName: item.workplaceName
                    };
                });
            }
            
            /**
             * search all Employee
             */
            public searchAllListEmployee(): void {
                var self = this;
                self.queryParam.referenceRange = SearchReferenceRange.ALL_EMPLOYEE;
                self.quickSearchEmployee();
            }
            
            /**
             * search Employee of Departmant_only
             */
            public searchEmployeeOfDepOnly(): void {
                var self = this;
                self.queryParam.referenceRange = SearchReferenceRange.DEPARTMENT_ONLY;
                self.quickSearchEmployee();
            }
            
            /**
             * search Employee of Departmant and child
             */
            public searchEmployeeOfDepAndChild(): void {
                var self = this;
                self.queryParam.referenceRange = SearchReferenceRange.DEPARTMENT_AND_CHILD;
                self.quickSearchEmployee();
            }

            /**
             * Search employee by code
             */
            public searchByCode(): void {
                let self = this;
                if (nts.uk.util.isNullOrEmpty(self.inputCodeTab3())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1201' });
                    return;
                }
                const query = {
                    code: self.inputCodeTab3(),
                    useClosure: self.showClosure,
                    closureId: self.selectedClosure(),
                    systemType: self.systemType,
                    referenceDate: moment.utc(self.acquiredBaseDate(), CcgDateFormat.DEFAULT_FORMAT).toDate()
                };
                nts.uk.ui.block.invisible(); // block ui
                service.searchByCode(query).done(data => {
                    self.showDataOnKcp005Tab3(data);
                    nts.uk.ui.block.clear(); // clear block UI
                });
            }

            /**
             * Search employee by name
             */
            public searchByName(): void {
                let self = this;
                if (nts.uk.util.isNullOrEmpty(self.inputNameTab3())) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1201' });
                    return;
                }
                const query = {
                    name: self.inputNameTab3(),
                    useClosure: self.showClosure,
                    closureId: self.selectedClosure(),
                    systemType: self.systemType,
                    referenceDate: moment.utc(self.acquiredBaseDate(), CcgDateFormat.DEFAULT_FORMAT).toDate()
                };
                nts.uk.ui.block.invisible(); // block ui
                service.searchByName(query).done(data => {
                    self.showDataOnKcp005Tab3(data);
                    nts.uk.ui.block.clear(); // clear block UI
                });
            }

            /**
             * Search employee by entry date
             */
            public searchByEntryDate(): void {
                let self = this;
                if ($('#ccg001-date-entry *').ntsError('hasError')) {
                    return;
                }
                if (self.isInValidEntryDate()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1201' });
                    return;
                }
                const query = {
                    useClosure: self.showClosure,
                    closureId: self.selectedClosure(),
                    systemType: self.systemType,
                    referenceDate: moment.utc(self.acquiredBaseDate(), CcgDateFormat.DEFAULT_FORMAT).toDate(),
                    period: self.toPeriodDto(self.entryDateTab3())
                };
                nts.uk.ui.block.invisible(); // block ui
                service.searchByEntryDate(query).done(data => {
                    self.showDataOnKcp005Tab3(data);
                    nts.uk.ui.block.clear(); // clear block UI
                });
            }

            /**
             * Search employee by retirement date
             */
            public searchByRetirementDate(): void {
                let self = this;
                if ($('#ccg001-date-retirement *').ntsError('hasError')) {
                    return;
                }
                if (self.isInValidRetirementDate()) {
                    nts.uk.ui.dialog.alertError({ messageId: 'Msg_1201' });
                    return;
                }
                const query = {
                    useClosure: self.showClosure,
                    closureId: self.selectedClosure(),
                    systemType: self.systemType,
                    referenceDate: moment.utc(self.acquiredBaseDate(), CcgDateFormat.DEFAULT_FORMAT).toDate(),
                    period: self.toPeriodDto(self.retirementDateTab3())
                };
                nts.uk.ui.block.invisible(); // block ui
                service.searchByRetirementDate(query).done(data => {
                    self.showDataOnKcp005Tab3(data);
                    nts.uk.ui.block.clear(); // clear block UI
                });
            }

            /**
             * Check input entry date
             */
            private isInValidEntryDate(): boolean {
                let self = this;
                return nts.uk.util.isNullOrEmpty(self.entryDateTab3().startDate)
                    || nts.uk.util.isNullOrEmpty(self.entryDateTab3().endDate);
            }

            /**
             * Check input retirement date
             */
            private isInValidRetirementDate(): boolean {
                let self = this;
                return nts.uk.util.isNullOrEmpty(self.retirementDateTab3().startDate)
                    || nts.uk.util.isNullOrEmpty(self.retirementDateTab3().endDate);
            }

            /**
             * Convert period in dateRangePicker to period dto
             */
            private toPeriodDto(period: any): any {
                return {
                    startDate: new Date(period.startDate),
                    endDate: new Date(period.endDate)
                };
            }

            /**
             * Quick search employee
             */
            private quickSearchEmployee(): void {
                let self = this;
                if (self.isInvalidBaseDate()) {
                    return;
                }
                nts.uk.ui.block.invisible(); // block ui
                self.setQuickSearchParam().done(() => {
                    self.findAndReturnListEmployee(false);
                });
            }
            /**
             * Find and return list employee for caller screen.
             */
            public findAndReturnListEmployee(isAdvancedSearch: boolean): void {
                let self = this;
                service.findRegulationInfoEmployee(self.queryParam).done(data => {
                    // Data not found
                    if (nts.uk.util.isNullOrEmpty(data)) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_317" });
                        nts.uk.ui.block.clear(); // clear block UI
                        return;
                    }

                    // Data found
                    if (isAdvancedSearch && self.showEmployeeSelection) {
                        // Set reserved list employees
                        self.reservedEmployees(data);

                        // Load list employee to KCP005
                        self.employeeinfo.employeeInputList(self.toUnitModelList(data));

                        // Reset selected employees on KCP005
                        self.selectedCodeEmployee([]);
                    } else {
                        self.returnDataFromCcg001(self.combineData(data));
                        // Hide component.
                        self.hideComponent();
                    }
                    nts.uk.ui.block.clear(); // clear block UI
                });
            }
            

            /**
             * Set component options (for advanced search tab)
             */
            public setComponentOptions(): void {
                var self = this;
                self.employments = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    isMultipleUse: true,
                    selectType: SelectType.SELECT_ALL,
                    listType: ListType.EMPLOYMENT,
                    selectedCode: self.selectedCodeEmployment,
                    isDialog: true,
                    isShowNoSelectRow: false,
                    maxRows: ConfigCCGKCP.MAX_ROWS_EMPLOYMENT,
                    selectedClosureId: self.showClosure ? self.selectedClosure : undefined,
                    hasPadding: false,
                    subscriptions: self.employmentSubscriptions
                };

                self.classifications = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    isMultipleUse: true,
                    listType: ListType.Classification,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedCodeClassification,
                    isDialog: true,
                    isShowNoSelectRow: false,
                    hasPadding: false,
                    maxRows: ConfigCCGKCP.MAX_ROWS_CLASSIFICATION
                }

                self.jobtitles = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    isMultipleUse: true,
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedCodeJobtitle,
                    isDialog: true,
                    baseDate: ko.observable(moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toDate()),
                    isShowNoSelectRow: false,
                    hasPadding: false,
                    maxRows: ConfigCCGKCP.MAX_ROWS_JOBTITLE
                }

                self.workplaces = {
                    isShowAlreadySet: false,
                    systemType: self.systemType,
                    isMultipleUse: true,
                    isMultiSelect: true,
                    treeType: TreeType.WORK_PLACE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    selectedWorkplaceId: self.selectedCodeWorkplace,
                    baseDate: ko.observable(moment.utc(self.queryParam.baseDate, CcgDateFormat.DEFAULT_FORMAT).toDate()),
                    maxRows: ConfigCCGKCP.MAX_ROWS_WORKPLACE,
                    isFullView: true,
                    hasPadding: false,
                    isDialog: true
                }
            }
        }
        
        export class ConfigCCGKCP{
            static MAX_ROWS_EMPLOYMENT = 10;
            static MAX_ROWS_CLASSIFICATION = 10;
            static MAX_ROWS_JOBTITLE = 10;
            static MAX_ROWS_WORKPLACE = 10;
        }
        
        export class ConfigEnumSystemType{
            static PERSONAL_INFORMATION = 1;
            static EMPLOYMENT = 2;
            static SALARY = 3;
            static HUMAN_RESOURCES = 4;
            static ADMINISTRATOR = 5;
        }

        export class CcgDateFormat {
            static DEFAULT_FORMAT = 'YYYY-MM-DD';
            static YMD = 'YYYY/MM/DD';
            static YEAR_MONTH = 'YYYYMM';
        }
        
         export class ConfigEnumClosure{
            static CLOSURE_ALL = 0;
            static CLOSURE_ALL_NAME = nts.uk.resource.getText("CCG001_64");
        }
        
        export class ReferenceRange {
            static ALL_EMPLOYEE = 0;
            static DEPARTMENT_AND_CHILD = 1;
            static DEPARTMENT_ONLY = 2;
        }

        export class EmployeeReferenceRange extends ReferenceRange {
            static ONLY_MYSELF = 3;
        }

        export class SearchReferenceRange extends ReferenceRange {
            static DO_NOT_CONSIDER_REFERENCE_RANGE = 3;
        }

        export class DateRangePickerModel {
            startDate: string;
            endDate: string;
            constructor(startDate: string, endDate: string) {
                let self = this;
                self.startDate = startDate;
                self.endDate = endDate;
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
        return new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().init(this, option).done(() => nts.uk.ui.block.clear());
    }
} (jQuery));