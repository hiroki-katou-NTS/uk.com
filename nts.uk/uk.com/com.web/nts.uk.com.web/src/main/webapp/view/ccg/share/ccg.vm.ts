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

            /** Common properties */
            isSelectAllEmployee: boolean; // 検索タイプ
            systemType: number; // システム区分
            isQuickSearchTab: boolean; // クイック検索
            isAdvancedSearchTab: boolean; // 詳細検索
            showBaseDate: boolean; // 基準日利用
            showClosure: boolean; // 就業締め日利用
            showAllClosure: boolean; // 全締め表示
            showPeriod: boolean; // 対象期間利用
            showPeriodYM: boolean; // 対象期間精度

            /** Required parameter */
            baseDate: KnockoutObservable<string>;
            periodStartDate: KnockoutObservable<string>;
            periodEndDate: KnockoutObservable<string>;
            periodStartYm: KnockoutObservable<string>;
            periodEndYm: KnockoutObservable<string>;
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
            isFistTimeShow: boolean;
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
            quickSearchParam: QuickSearchParam;
            advancedSearchParam: AdvancedSearchParam;
            
            //params Status Of Employee
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
                self.isOpenEmploymentList = ko.observable(false);
                self.isOpenClassificationList = ko.observable(false);
                self.isOpenJoptitleList = ko.observable(false);
                self.isOpenWorkplaceList = ko.observable(false);
                self.isOpenWorkTypeList = ko.observable(false);
                self.closureList = ko.observableArray([]);
                self.selectedClosure = ko.observable(null);
                self.periodStartDate = ko.observable('');
                self.periodEndDate = ko.observable('');
                self.periodStartYm = ko.observable('');
                self.periodEndYm = ko.observable('');
                
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
                    { headerText: nts.uk.resource.getText('CCG001_60'), prop: 'workTypeCode', width: 50 },
                    { headerText: nts.uk.resource.getText('CCG001_61'), prop: 'name', width: 100 }
                ]);
            }

            private initQuickSearchParam(): void {
                let self = this;
                self.quickSearchParam = <QuickSearchParam>{};
                self.quickSearchParam.filterByDepartment = false;
                self.quickSearchParam.listDepartmentId = [];
                self.quickSearchParam.filterByWorkplace = false;
                self.quickSearchParam.listWorkplaceId = [];
                self.quickSearchParam.filterByClassification = false;
                self.quickSearchParam.listClassificationCode = [];
                self.quickSearchParam.filterByJobTitle = false;
                self.quickSearchParam.listPositionId = [];
                self.quickSearchParam.includeIncumbents = true;
                self.quickSearchParam.includeOccupancy = false;
                self.quickSearchParam.includeRetirees = false;
                self.quickSearchParam.sortOrderNo = 1; // 並び順NO＝1
                self.quickSearchParam.nameType = 1; // ビジネスネーム（日本語）
            }

            private initAdvancedSearchParam(): void {
                let self = this;
                self.advancedSearchParam = <QuickSearchParam>{};
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
                service.getRefRangeBySysType(self.systemType)// get ref range
                // TODO: AppContexts.user().roles().forPersonalInfo() null?
                self.acquireBaseDate().done(date => {
                    console.log(date);
                    //TODO: set basedate to query param. 
                });
                self.loadClosure().done(() => {
                    dfd.resolve();
                });

                return dfd.promise();
            }

            /**
             * Set component properties
             */
            private setProperties(options: GroupOption): void {
                let self = this;

                /** Common properties */
                self.isSelectAllEmployee = options.isSelectAllEmployee;
                self.systemType = 5; //TODO: mock data
                self.isQuickSearchTab = options.isQuickSearchTab;
                self.isAdvancedSearchTab = options.isAdvancedSearchTab;
                self.showBaseDate = options.showBaseDate;
                self.showClosure = true; //TODO: mock data
                self.showAllClosure = options.showAllClosure;
                self.showPeriod = options.showPeriod;
                self.showPeriodYM = options.periodAccuracy == 1 ? true : false; // 1 == YM, other = YMD

                /** Required parameter */
                self.baseDate = ko.observable('2018-06-02'); //TODO: mock data
                self.periodStartDate = ko.observable('2018-06-02');
                self.periodEndDate = ko.observable('2018-06-02');
                self.periodStartYm = ko.observable('2018-06-02');
                self.periodEndYm = ko.observable('2018-06-02');
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
                let headerHeight = $('#header').outerHeight();
                let functionAreaHeight = $('#functions-area').length > 0 ? $('#functions-area').outerHeight() : 0;
                $('#component-ccg001').outerHeight(window.innerHeight - headerHeight - functionAreaHeight - 30);
            }

            /**
             * Load ListClosure 
             */
            private loadClosure(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                if (self.showClosure) {
                    service.getClosuresByBaseDate('2018-06-02').done(data => { // mock base date
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
                        service.getClosureTiedByEmployment().done(id => dfd.resolve(id));
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
                    if ($(e.target).hasClass('ui-widget-overlay ui-front')) {
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
                        $('#component-ccg001').toggle("slide");
                    }
                });
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
                $('#component-ccg001').toggle("slide", function() {
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
            }

            /**
             * function click by apply data search employee (init tab 2)
             * get base date
             */
            applyDataSearch(): void {
                var self = this;
                
                // validate input base date
                self.validateBaseDateAndTargetPeriod();
                if (self.validateClient()) {
                    return;
                }

                return;
                nts.uk.ui.block.invisible(); // block ui
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
                        service.searchAllWorkType()
                            .done(function(workTypeList: Array<WorkType>) {
                                self.listWorkType(workTypeList);
                        });
                    }
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => nts.uk.ui.block.clear());

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
            }

            public acquireBaseDate(): JQueryPromise<String> {
                let dfd = $.Deferred<String>();
                let self = this;
                if (self.showBaseDate) {
                    dfd.resolve(self.baseDate().toString());
                } else {
                    if (self.showPeriodYM) { //TODO: is accuracy year month date 
                        dfd.resolve(self.periodEndDate().toString());
                    } else {
                        service.calculatePeriod(1, 201802).done(date => { //TODO mock data
                            return dfd.resolve(date);
                        });
                    }
                }
                return dfd.promise();
            }

            public validateBaseDate(): void {
                let self = this;
                if (self.baseDate()) { // is future date
                    //TODO: throw msg_853
                }
                if (1==1) { // period end is future date
                    //TODO: throw msg_860
                }
            }

            public getFuturePermit(): boolean {
                let self = this;
                if (self.systemType == 1) { //TODO: check system type
                    //TODO: call ws check future permit
                    return true;
                }
            }

            public validateBaseDateAndTargetPeriod() {
                let self = this;
                if (self.baseDate()) { //TODO: base date is not in target period range.
                    // throw Msg_765
                }
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
            applyEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
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

                _.forEach(dataList, item => {
                    dataRes.push({
                        code: item.employeeId,
                        name: item.employeeName
                    });
                });
                return dataRes;
            }
            
            quickSearchEmployee(): void {
                var self = this;
                if (self.validateClient()) {
                    return;
                }
                
                
                
//                service.searchAllEmployee(self.baseDate()).done(data => {
//                    self.onSearchAllClicked(data);
//                }).fail(function(error) {
//                    nts.uk.ui.dialog.alertError(error);
//                });
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
                        baseDate: ko.observable(new Date()),
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
                        baseDate: ko.observable(new Date()),
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

        interface BaseQueryParam {
            baseDate: any;
            referenceRange: number;
            filterByEmployment: boolean;
            listEmploymentCode: Array<any>;
            filterByDepartment: boolean;
            listDepartmentId: Array<any>;
            filterByWorkplace: boolean;
            listWorkplaceId: Array<any>;
            filterByClassification: boolean;
            listClassificationCode: Array<any>;
            filterByJobTitle: boolean;
            listPositionId: Array<any>;

            periodStart: any;
            periodEnd: any;

            includeIncumbents: boolean;
            //TODO: Including workers on leave 
            includeOccupancy: boolean;
            includeRetirees: boolean;
            retireStart: any;
            retireEnd: any;

            sortOrderNo: number;
            nameType: number;
        }

        interface QuickSearchParam extends BaseQueryParam {
        }

        interface AdvancedSearchParam extends BaseQueryParam {
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