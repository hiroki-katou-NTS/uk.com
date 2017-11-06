module nts.uk.at.view.kmk006.a {

    import Enum = service.model.Enum;
    //importDto
    import ComAutoCalSettingDto = a.service.model.ComAutoCalSettingDto;
    import AutoCalOvertimeSettingDto = service.model.AutoCalOvertimeSettingDto;
    import AutoCalRestTimeSettingDto = service.model.AutoCalRestTimeSettingDto;
    import AutoCalFlexOvertimeSettingDto = service.model.AutoCalFlexOvertimeSettingDto;
    import AutoCalSettingDto = service.model.AutoCalSettingDto;
    import JobAutoCalSettingDto = service.model.JobAutoCalSettingDto;
    import WkpAutoCalSettingDto = service.model.WkpAutoCalSettingDto;
    import WkpJobAutoCalSettingDto = service.model.WkpJobAutoCalSettingDto;
    import UnitAutoCalSettingDto = nts.uk.at.view.kmk006.e.service.model.UnitAutoCalSettingDto;

    export module viewmodel {

        export class ScreenModel {
            totalSelectedWorkplaceId: KnockoutObservable<string>;
            multiSelectedWorkplaceId: KnockoutObservable<string>;
            wkpAlreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeOptionsWkp: TreeComponentOption;
            treeOptionsWkpTotal: TreeComponentOption;
            autoCalAtrOvertimeEnum: Array<Enum>;
            selectedTab: KnockoutObservable<string>;
            timeLimitUpperLimitEnum: Array<Enum>;
            itemComAutoCalModel: ComAutoCalSettingModel;
            itemWkpAutoCalModel: WkpAutoCalSettingModel;
            itemJobAutoCalModel: JobAutoCalSettingModel;
            itemWkpJobAutoCalModel: WkpJobAutoCalSettingModel;

            // define value Enum
            valueEnumNorEarLi: KnockoutObservable<number>;
            valueEnumNorEarAtr: KnockoutObservable<number>;
            valueEnumNorEarMidLi: KnockoutObservable<number>;
            valueEnumNorEarMidAtr: KnockoutObservable<number>;
            valueEnumNorNorLi: KnockoutObservable<number>;
            valueEnumNorNorAtr: KnockoutObservable<number>;
            valueEnumNorNorMidLi: KnockoutObservable<number>;
            valueEnumNorNorMidAtr: KnockoutObservable<number>;
            valueEnumNorLegLi: KnockoutObservable<number>;
            valueEnumNorLegAtr: KnockoutObservable<number>;
            valueEnumNorLegMidLi: KnockoutObservable<number>;
            valueEnumNorLegMidAtr: KnockoutObservable<number>;
            valueEnumFleTimeLi: KnockoutObservable<number>;
            valueEnumFleTimeAtr: KnockoutObservable<number>;
            valueEnumResResLi: KnockoutObservable<number>;
            valueEnumResResAtr: KnockoutObservable<number>;
            valueEnumResLatLi: KnockoutObservable<number>;
            valueEnumResLatAtr: KnockoutObservable<number>;

            jobListOptions: any;
            jobTotalListOptions: any;
            selectedCode: KnockoutObservable<string>;
            totalSelectedCode: KnockoutObservableArray<string>;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isMultiSelectKcp: KnockoutObservable<boolean>;
            jobTitleList: KnockoutObservableArray<UnitModel>;
            selectedCurrentJob: KnockoutObservable<string>;
            selectedCurrentWkp: KnockoutObservable<string>;
            useUnitAutoCalSettingModel: KnockoutObservable<UnitAutoCalSettingView>;
            treeItemName: KnockoutObservable<string>;
            componentItemName: KnockoutObservable<string>;
            treeItemCode: KnockoutObservable<string>;
            componentItemCode: KnockoutObservable<string>;
            enableEnumNorLegLi: KnockoutObservable<boolean>;
            enableEnumNorLegMidLi: KnockoutObservable<boolean>;
            enableEnumNorNorLi: KnockoutObservable<boolean>;
            enableEnumNorNorMidLi: KnockoutObservable<boolean>;
            enableEnumNorEarLi: KnockoutObservable<boolean>;
            enableEnumNorEarMidLi: KnockoutObservable<boolean>;
            enableEnumFleTimeLi: KnockoutObservable<boolean>;
            enableEnumResResLi: KnockoutObservable<boolean>;
            enableEnumResLatLi: KnockoutObservable<boolean>;
            baseDate: KnockoutObservable<Date>;
            isLoading: KnockoutObservable<boolean>;
            date: KnockoutObservable<string>;
            yearMonth: KnockoutObservable<number>;

            // Common
            tabs: KnockoutObservableArray<NtsTabPanelModel>;

            constructor() {
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.isMultiSelectKcp = ko.observable(false);
                self.date = ko.observable('20000101');
                self.yearMonth = ko.observable(200001);
                // Initial common data.
                self.useUnitAutoCalSettingModel = ko.observable(new UnitAutoCalSettingView(true, true, true));
                this.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK006_14"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK006_15"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK006_16"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                ]);

                self.multiSelectedWorkplaceId = ko.observable('');
                self.totalSelectedWorkplaceId = ko.observable('');
                self.wkpAlreadySettingList = ko.observableArray([]);
                self.treeOptionsWkp = {
                    isShowAlreadySet: true,
                    isMultiSelect: false,
                    treeType: TreeType.WORK_PLACE,
                    selectedWorkplaceId: self.multiSelectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: SelectionType.SELECT_FIRST_ITEM,
                    isShowSelectButton: false,
                    isDialog: false,
                    alreadySettingList: self.wkpAlreadySettingList,
                    maxRows: 10
                };
                self.treeOptionsWkpTotal = {
                    isShowAlreadySet: false,
                    isMultiSelect: false,
                    treeType: TreeType.WORK_PLACE,
                    selectedWorkplaceId: self.totalSelectedWorkplaceId,
                    baseDate: self.baseDate,
                    selectType: SelectionType.SELECT_FIRST_ITEM,
                    isShowSelectButton: false,
                    isDialog: false,
                    maxRows: 10
                };
                self.itemComAutoCalModel = new ComAutoCalSettingModel();
                self.itemJobAutoCalModel = new JobAutoCalSettingModel();
                self.itemWkpAutoCalModel = new WkpAutoCalSettingModel();
                self.itemWkpJobAutoCalModel = new WkpJobAutoCalSettingModel();
                self.selectedTab = ko.observable('tab-1');
                self.isLoading = ko.observable(false);

                self.autoCalAtrOvertimeEnum = [];

                self.timeLimitUpperLimitEnum = [];
                self.valueEnumNorEarLi = ko.observable(2);
                self.valueEnumNorEarAtr = ko.observable(2);
                self.valueEnumNorEarMidLi = ko.observable(2);
                self.valueEnumNorEarMidAtr = ko.observable(2);
                self.valueEnumNorNorLi = ko.observable(2);
                self.valueEnumNorNorAtr = ko.observable(2);
                self.valueEnumNorNorMidLi = ko.observable(2);
                self.valueEnumNorNorMidAtr = ko.observable(2);
                self.valueEnumNorLegLi = ko.observable(2);
                self.valueEnumNorLegAtr = ko.observable(2);
                self.valueEnumNorLegMidLi = ko.observable(2);
                self.valueEnumNorLegMidAtr = ko.observable(2);
                self.valueEnumFleTimeLi = ko.observable(2);
                self.valueEnumFleTimeAtr = ko.observable(2);
                self.valueEnumResResLi = ko.observable(2);
                self.valueEnumResResAtr = ko.observable(2);
                self.valueEnumResLatLi = ko.observable(2);
                self.valueEnumResLatAtr = ko.observable(2);

                self.selectedCode = ko.observable('');
                self.totalSelectedCode = ko.observable('');
                self.selectedCurrentJob = ko.observable('');
                self.selectedCurrentWkp = ko.observable('');
                self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
                self.isShowAlreadySet = ko.observable(false);
                self.jobAlreadySettingList = ko.observableArray([]);
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.treeItemName = ko.observable('');
                self.componentItemName = ko.observable('');
                self.treeItemCode = ko.observable('');
                self.componentItemCode = ko.observable('');
                self.jobListOptions = {
                    isShowAlreadySet: true,
                    baseDate: self.baseDate,
                    isMultiSelect: false,
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.jobAlreadySettingList
                };
                self.jobTotalListOptions = {
                    isShowAlreadySet: false,
                    baseDate: self.baseDate,
                    isMultiSelect: false,
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.totalSelectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow()
                };
                self.jobTitleList = ko.observableArray<UnitModel>([]);


                //subscribe 
                self.multiSelectedWorkplaceId.subscribe(function(codeChanged) {
                    self.selectedCurrentWkp(codeChanged);
                    self.loadWkpAutoCal(codeChanged);
                    let data = $('#tree-grid-srcc').getDataList();
                    for (let ent of data) {
                        if (ent.workplaceId == codeChanged) {
                            self.treeItemName(ent.name);
                            self.treeItemCode(ent.code);
                        }
                    }
                });

                //subscribe 
                self.totalSelectedWorkplaceId.subscribe(function(codeChanged) {
                    self.selectedCurrentWkp(codeChanged);
                    self.loadWkpJobAutoCal(codeChanged, self.totalSelectedCode);
                    let data = $('#tree-grid').getDataList();
                    for (let ent of data) {
                        if (ent.workplaceId == codeChanged) {
                            self.treeItemName(ent.name);
                            self.treeItemCode(ent.code);
                        }
                    }
                });

                //subscribe 
                self.totalSelectedCode.subscribe(function(codeChanged) {
                    self.selectedCurrentJob(codeChanged);
                    self.loadWkpJobAutoCal(self.totalSelectedWorkplaceId(), codeChanged);
                    let data = $('#jobtitles').getDataList();
                    for (let ent of data) {
                        if (ent.id == codeChanged) {
                            self.componentItemName(ent.name);
                            self.componentItemCode(ent.code);
                        }
                    }
                });

                //subscribe 
                self.selectedCode.subscribe(function(codeChanged) {
                    self.selectedCurrentJob(codeChanged);
                    self.loadJobAutoCal(codeChanged);
                    let data = $('#component-items-list').getDataList();
                    for (let ent of data) {
                        if (ent.id == codeChanged) {
                            self.componentItemName(ent.name);
                            self.componentItemCode(ent.code);
                        }
                    }
                });


                self.enableEnumNorLegLi = ko.computed(function() {
                    return self.valueEnumNorLegAtr() != 2;
                });
                self.enableEnumNorLegMidLi = ko.computed(function() {
                    return self.valueEnumNorLegMidAtr() != 2;
                });
                self.enableEnumNorNorLi = ko.computed(function() {
                    return self.valueEnumNorNorAtr() != 2;
                });
                self.enableEnumNorNorMidLi = ko.computed(function() {
                    return self.valueEnumNorNorMidAtr() != 2;
                });
                self.enableEnumNorEarLi = ko.computed(function() {
                    return self.valueEnumNorEarAtr() != 2;
                });
                self.enableEnumNorEarMidLi = ko.computed(function() {
                    return self.valueEnumNorEarMidAtr() != 2;
                });
                self.enableEnumFleTimeLi = ko.computed(function() {
                    return self.valueEnumFleTimeAtr() != 2;
                });
                self.enableEnumResResLi = ko.computed(function() {
                    return self.valueEnumResResAtr() != 2;
                });
                self.enableEnumResLatLi = ko.computed(function() {
                    return self.valueEnumResLatAtr() != 2;
                });

            }


            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var _self = this;
                var dfd = $.Deferred<any>();

                // Initial settings.
                $.when(_self.loadTimeLimitUpperLimitSettingEnum(), _self.loadAutoCalAtrOvertimeEnum(),
                    _self.loadUseUnitAutoCalSettingModel()).done(function() {
                        _self.onSelectCompany();
                        dfd.resolve(_self);
                    });

                return dfd.promise();
            }

            //load workPlace-job already setting
            public loadWkpJobAlreadySettingList(): JQueryPromise<void> {
                let _self = this;
                var jobSettingList: any = [];
                var wkpSettingList: any = [];
                var dfd = $.Deferred<any>();

                service.getAllWplJobAutoCal().done(function(data) {
                    data.forEach(c => {
                        let job_item: JobAlreadySettingModel = { id: c.jobId, isAlreadySetting: true };
                        let wkp_item: UnitAlreadySettingModel = { workplaceId: c.wkpId, isAlreadySetting: true };
                        wkpSettingList.push(wkp_item);
                        jobSettingList.push(job_item);
                    });
                    _self.wkpAlreadySettingList(wkpSettingList);
                    _self.jobAlreadySettingList(jobSettingList);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            //load job already setting
            public loadJobAlreadySettingList(): JQueryPromise<void> {
                let _self = this;
                var settingList: any = [];
                var dfd = $.Deferred<any>();
                service.getAllJobAutoCal().done(function(data) {
                    data.forEach(c => {
                        let item: JobAlreadySettingModel = { id: c.jobId, isAlreadySetting: true };
                        settingList.push(item);
                    });
                    _self.jobAlreadySettingList(settingList);
                    dfd.resolve();
                });
                return dfd.promise();
            }


            //load workPlace already setting
            public loadWkpAlreadySettingList(): JQueryPromise<void> {
                let _self = this;
                var settingList: any = [];
                var dfd = $.Deferred<any>();
                service.getAllWkpAutoCal().done(function(data) {
                    data.forEach(c => {
                        let item: UnitAlreadySettingModel = { workplaceId: c.wkpId, isAlreadySetting: true };
                        settingList.push(item);
                    });
                    _self.wkpAlreadySettingList(settingList);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
          * call service load UseUnitAutoCalSettingModel
          */
            private loadUseUnitAutoCalSettingModel(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                nts.uk.at.view.kmk006.e.service.getUseUnitAutoCal().done(function(data) {
                    self.useUnitAutoCalSettingModel(new UnitAutoCalSettingView(data.useWkpSet, data.useJobSet, data.useJobwkpSet));
                    dfd.resolve();
                }).fail(res => {
                    nts.uk.ui.dialog.alertError(res);
                });
                return dfd.promise();
            }

            // All function
            // load AutoCalAtrOvertimeEnum
            private loadAutoCalAtrOvertimeEnum(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.invisible();

                // get setting
                service.findEnumAutoCalAtrOvertime().done(function(dataRes: Array<Enum>) {

                    self.autoCalAtrOvertimeEnum = dataRes;

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }
            // All function
            // load AutoCalAtrOvertimeEnum
            private loadTimeLimitUpperLimitSettingEnum(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.invisible();

                // get setting
                service.findEnumTimeLimitUpperLimitSetting().done(function(dataRes: Array<Enum>) {

                    self.timeLimitUpperLimitEnum = dataRes;

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }


            // load ComAutoCal
            private loadComAutoCal(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<any>();

                //            nts.uk.ui.block.invisible();

                service.getComAutoCal().done(function(data) {
                    //                nts.uk.ui.block.clear();
                    if (data) {
                        self.itemComAutoCalModel.updateData(data);
                    }
                    if (self.itemComAutoCalModel) {
                        // load get all value enum
                        self.reLoadListEnum(self.itemComAutoCalModel);
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }


            // load  JobAutoCal
            private loadJobAutoCal(jobId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //            nts.uk.ui.block.invisible();

                service.getJobAutoCal(jobId).done(function(data) {
                    //                nts.uk.ui.block.clear();
                    if (data) {
                        self.itemJobAutoCalModel.updateData(data);
                    }
                    if (self.itemJobAutoCalModel) {
                        // load get all value enum
                        self.reLoadListEnum(self.itemJobAutoCalModel);

                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }

            // load  JobAutoCal
            private loadWkpAutoCal(wkpId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //            nts.uk.ui.block.invisible();

                service.getWkpAutoCal(wkpId).done(function(data) {
                    //                nts.uk.ui.block.clear();
                    if (data) {
                        self.itemWkpAutoCalModel.updateData(data);
                    }
                    if (self.itemWkpAutoCalModel) {
                        // load get all value enum
                        self.reLoadListEnum(self.itemWkpAutoCalModel);

                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }

            // load  JobAutoCal
            private loadWkpJobAutoCal(wkpId: string, jobId: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                //            nts.uk.ui.block.invisible();

                service.getWkpJobAutoCal(wkpId, jobId).done(function(data) {
                    //                nts.uk.ui.block.clear();
                    if (data) {
                        self.itemWkpJobAutoCalModel.updateData(data);
                    }
                    if (self.itemWkpJobAutoCalModel) {
                        // load get all value enum
                        self.reLoadListEnum(self.itemWkpJobAutoCalModel);

                    }
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError(res);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });

                return dfd.promise();
            }

            private reLoadListEnum(list: any): void {
                var self = this;
                self.valueEnumNorEarLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.earlyOtTime.upLimitOtSet()].value);
                self.valueEnumNorEarAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.earlyOtTime.calAtr()].value);
                self.valueEnumNorEarMidLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.earlyMidOtTime.upLimitOtSet()].value);
                self.valueEnumNorEarMidAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.earlyMidOtTime.calAtr()].value);
                self.valueEnumNorNorLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.normalOtTime.upLimitOtSet()].value);
                self.valueEnumNorNorAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.normalOtTime.calAtr()].value);
                self.valueEnumNorNorMidLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.normalMidOtTime.upLimitOtSet()].value);
                self.valueEnumNorNorMidAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.normalMidOtTime.calAtr()].value);
                self.valueEnumNorLegLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.legalOtTime.upLimitOtSet()].value);
                self.valueEnumNorLegAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.legalOtTime.calAtr()].value);
                self.valueEnumNorLegMidLi(self.autoCalAtrOvertimeEnum[list.normalOTTime.legalMidOtTime.upLimitOtSet()].value);
                self.valueEnumNorLegMidAtr(self.autoCalAtrOvertimeEnum[list.normalOTTime.legalMidOtTime.calAtr()].value);
                self.valueEnumFleTimeLi(self.autoCalAtrOvertimeEnum[list.flexOTTime.flexOtTime.upLimitOtSet()].value);
                self.valueEnumFleTimeAtr(self.autoCalAtrOvertimeEnum[list.flexOTTime.flexOtTime.calAtr()].value);
                self.valueEnumResResLi(self.autoCalAtrOvertimeEnum[list.restTime.restTime.upLimitOtSet()].value);
                self.valueEnumResResAtr(self.autoCalAtrOvertimeEnum[list.restTime.restTime.calAtr()].value);
                self.valueEnumResLatLi(self.autoCalAtrOvertimeEnum[list.restTime.lateNightTime.upLimitOtSet()].value);
                self.valueEnumResLatAtr(self.autoCalAtrOvertimeEnum[list.restTime.lateNightTime.calAtr()].value);
            }

            private saveListEnum(list: any): void {
                var self = this;
                list.normalOTTime.earlyOtTime.upLimitOtSet(self.valueEnumNorEarLi());
                list.normalOTTime.earlyOtTime.calAtr(self.valueEnumNorEarAtr());
                list.normalOTTime.earlyMidOtTime.upLimitOtSet(self.valueEnumNorEarMidLi());
                list.normalOTTime.earlyMidOtTime.calAtr(self.valueEnumNorEarMidAtr());
                list.normalOTTime.normalOtTime.upLimitOtSet(self.valueEnumNorNorLi());
                list.normalOTTime.normalOtTime.calAtr(self.valueEnumNorNorAtr());
                list.normalOTTime.normalMidOtTime.upLimitOtSet(self.valueEnumNorNorMidLi());
                list.normalOTTime.normalMidOtTime.calAtr(self.valueEnumNorNorMidAtr());
                list.normalOTTime.legalOtTime.upLimitOtSet(self.valueEnumNorLegLi());
                list.normalOTTime.legalOtTime.calAtr(self.valueEnumNorLegAtr());
                list.normalOTTime.legalMidOtTime.upLimitOtSet(self.valueEnumNorLegMidLi());
                list.normalOTTime.legalMidOtTime.calAtr(self.valueEnumNorLegMidAtr());
                list.flexOTTime.flexOtTime.upLimitOtSet(self.valueEnumFleTimeLi());
                list.flexOTTime.flexOtTime.calAtr(self.valueEnumFleTimeAtr());
                list.restTime.restTime.upLimitOtSet(self.valueEnumResResLi());
                list.restTime.restTime.calAtr(self.valueEnumResResAtr());
                list.restTime.lateNightTime.upLimitOtSet(self.valueEnumResLatLi());
                list.restTime.lateNightTime.calAtr(self.valueEnumResLatAtr());
            }

            /**
             * function on click saveCompanyAutoCal action
             */
            public saveCompanyAutoCal(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;

                // save enum
                self.saveListEnum(self.itemComAutoCalModel);

                var dto: ComAutoCalSettingDto = {
                    normalOTTime: self.itemComAutoCalModel.normalOTTime.toDto(),
                    flexOTTime: self.itemComAutoCalModel.flexOTTime.toDto(),
                    restTime: self.itemComAutoCalModel.restTime.toDto()
                };

                self.itemComAutoCalModel.updateData(self.itemComAutoCalModel.toDto());

                service.saveComAutoCal(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.loadComAutoCal();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
            * function on click saveCompanyAutoCal action
            */
            public saveJobAutoCal(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;

                // save enum
                self.saveListEnum(self.itemJobAutoCalModel);
                var jobId = self.selectedCurrentJob();

                var dto: JobAutoCalSettingDto = {
                    jobId: jobId,
                    normalOTTime: self.itemJobAutoCalModel.normalOTTime.toDto(),
                    flexOTTime: self.itemJobAutoCalModel.flexOTTime.toDto(),
                    restTime: self.itemJobAutoCalModel.restTime.toDto()
                };

                self.itemJobAutoCalModel.updateData(self.itemJobAutoCalModel.toDto());

                service.saveJobAutoCal(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.loadJobAutoCal(jobId);
                        self.loadJobAlreadySettingList();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            /**
            * function on click saveCompanyAutoCal action
            */
            public saveWkpAutoCal(): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;

                // save enum
                self.saveListEnum(self.itemWkpAutoCalModel);
                var wkpId = self.selectedCurrentWkp();

                var dto: WkpAutoCalSettingDto = {
                    wkpId: wkpId,
                    normalOTTime: self.itemWkpAutoCalModel.normalOTTime.toDto(),
                    flexOTTime: self.itemWkpAutoCalModel.flexOTTime.toDto(),
                    restTime: self.itemWkpAutoCalModel.restTime.toDto()
                };

                self.itemWkpAutoCalModel.updateData(self.itemWkpAutoCalModel.toDto());

                service.saveWkpAutoCal(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.loadWkpAutoCal(wkpId);
                        self.loadWkpAlreadySettingList();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }


            public saveWkpJobAutoCal(wkpId: string, jobId: string): void {
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                };
                nts.uk.ui.block.invisible();
                var self = this;

                // save enum

                self.saveListEnum(self.itemWkpJobAutoCalModel);
                var jobId = self.selectedCurrentJob();
                var wkpId = self.selectedCurrentWkp();


                var dto: WkpJobAutoCalSettingDto = {
                    wkpId: wkpId,
                    jobId: jobId,
                    normalOTTime: self.itemWkpJobAutoCalModel.normalOTTime.toDto(),
                    flexOTTime: self.itemWkpJobAutoCalModel.flexOTTime.toDto(),
                    restTime: self.itemWkpJobAutoCalModel.restTime.toDto()
                };

                self.itemWkpJobAutoCalModel.updateData(self.itemWkpJobAutoCalModel.toDto());

                service.saveWkpJobAutoCal(dto).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // reload pa    
                        self.loadWkpJobAutoCal(wkpId, jobId);
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            }

            // delete Pattern
            public deleteJobAutoCal() {
                let self = this;

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    nts.uk.ui.block.grayout();

                    service.deleteJobAutoCal(self.selectedCurrentJob()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadJobAutoCal(self.selectedCurrentJob());
                            self.loadJobAlreadySettingList();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });

            }

            // delete Pattern
            public deleteWkpAutoCal() {
                let self = this;

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    nts.uk.ui.block.grayout();

                    service.deleteWkpAutoCal(self.selectedCurrentWkp()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadJobAutoCal(self.selectedCurrentWkp());
                            self.loadWkpAlreadySettingList();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });

            }

            // delete Pattern
            public deleteWkpJobAutoCal() {
                let self = this;

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    nts.uk.ui.block.grayout();

                    service.deleteWkpJobAutoCal(self.selectedCurrentWkp(), self.selectedCurrentJob()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadWkpJobAutoCal(self.selectedCurrentJob(), self.selectedCurrentWkp());
                            self.loadWkpJobAlreadySettingList();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });

            }

            /**
         * on click tab panel company action event
         */
            public onSelectCompany(): JQueryPromise<void> {
                $('.nts-input').ntsError('clear');
                var self = this;
                var dfd = $.Deferred<void>();

                self.loadComAutoCal();

                self.isLoading(true);

                dfd.resolve();

                return dfd.promise();
            }

            public onSelectJobTitle(): void {
                $('.nts-input').ntsError('clear');
                var self = this;

                self.isLoading(true);

                $('#component-items-list').ntsListComponent(self.jobListOptions).done(function() {
                    let code = $('#component-items-list').getDataList()[0].id;
                    self.selectedCode(code);
                    self.loadJobAutoCal(code);
                    self.loadJobAlreadySettingList();
                });

            }


            /**
          * on click tab panel employment action event
          */
            public onSelectWorkplace(): void {
                $('.nts-input').ntsError('clear');
                var self = this;

                // Update flags.
                self.isLoading(true);

                $('#tree-grid-srcc').ntsTreeComponent(self.treeOptionsWkp).done(function() {
                    self.loadWkpAutoCal(self.multiSelectedWorkplaceId);
                    self.loadWkpAlreadySettingList().done(function() {

                    });
                });
            }

            public onSelectWkpJob(): void {
                $('.nts-input').ntsError('clear');
                var self = this;

                // Update flags.
                self.isLoading(true);

                //load grid          
                $('#tree-grid').ntsTreeComponent(self.treeOptionsWkpTotal).done(function() {

                });

                $('#jobtitles').ntsListComponent(self.jobTotalListOptions).done(function() {
                    let code = $('#jobtitles').getDataList()[0].id;
                    self.totalSelectedCode(code);
                    self.loadWkpJobAutoCal(self.multiSelectedWorkplaceId, code);
                    // load ready setting
                    self.loadWkpJobAlreadySettingList().done(function() {

                    });
                });

            }

            /**
             * call service find all by employee id
             */
            public findAllByEmployeeIds(employeeIds: string[]): JQueryPromise<UnitAlreadySettingModel[]> {
                var dfd = $.Deferred();
                var dataRes: UnitAlreadySettingModel[] = [];
                dfd.resolve(dataRes);
                return dfd.promise();
            }

            /**
             * open dialog openDialogUsageSettingModel (view model E)
             */
            private openDialogUsageSettingModel(): void {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/006/e/index.xhtml").onClosed(function() {
                    self.loadUseUnitAutoCalSettingModel().done(function(){});
                });
            }

        }
        // exportclass
        export class TreeType {
            static WORK_PLACE = 1;
        }
        //        SELECTIONTYPE
        export class SelectionType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;

        }

        export class WkpJobAutoCalSettingModel {
            wkpId: KnockoutObservable<string>;
            jobId: KnockoutObservable<string>;
            normalOTTime: AutoCalOvertimeSettingModel;
            flexOTTime: AutoCalFlexOvertimeSettingModel;
            restTime: AutoCalRestTimeSettingModel;

            constructor() {
                this.wkpId = ko.observable('');
                this.jobId = ko.observable('');
                this.normalOTTime = new AutoCalOvertimeSettingModel();
                this.flexOTTime = new AutoCalFlexOvertimeSettingModel();
                this.restTime = new AutoCalRestTimeSettingModel();

            }

            updateData(dto: WkpJobAutoCalSettingDto) {
                this.wkpId(dto.wkpId);
                this.jobId(dto.jobId);
                this.normalOTTime.updateData(dto.normalOTTime);
                this.flexOTTime.updateData(dto.flexOTTime);
                this.restTime.updateData(dto.restTime);

            }

            toDto(): WkpJobAutoCalSettingDto {
                var dto: WkpJobAutoCalSettingDto = {
                    wkpId: this.wkpId(),
                    jobId: this.jobId(),
                    normalOTTime: this.normalOTTime.toDto(),
                    flexOTTime: this.flexOTTime.toDto(),
                    restTime: this.restTime.toDto()
                };
                return dto;
            }
            resetData() {
                this.wkpId('');
                this.jobId('');
                this.normalOTTime.resetData();
                this.flexOTTime.resetData();
                this.restTime.resetData();
            }
        }

        export class WkpAutoCalSettingModel {
            wkpId: KnockoutObservable<string>;
            normalOTTime: AutoCalOvertimeSettingModel;
            flexOTTime: AutoCalFlexOvertimeSettingModel;
            restTime: AutoCalRestTimeSettingModel;

            constructor() {
                this.wkpId = ko.observable('');
                this.normalOTTime = new AutoCalOvertimeSettingModel();
                this.flexOTTime = new AutoCalFlexOvertimeSettingModel();
                this.restTime = new AutoCalRestTimeSettingModel();

            }

            updateData(dto: WkpAutoCalSettingDto) {
                this.wkpId(dto.wkpId);
                this.normalOTTime.updateData(dto.normalOTTime);
                this.flexOTTime.updateData(dto.flexOTTime);
                this.restTime.updateData(dto.restTime);

            }

            toDto(): WkpAutoCalSettingDto {
                var dto: WkpAutoCalSettingDto = {
                    wkpId: this.wkpId(),
                    normalOTTime: this.normalOTTime.toDto(),
                    flexOTTime: this.flexOTTime.toDto(),
                    restTime: this.restTime.toDto()
                };
                return dto;
            }
            resetData() {
                this.wkpId('');
                this.normalOTTime.resetData();
                this.flexOTTime.resetData();
                this.restTime.resetData();
            }
        }


        export class JobAutoCalSettingModel {
            jobId: KnockoutObservable<string>;
            normalOTTime: AutoCalOvertimeSettingModel;
            flexOTTime: AutoCalFlexOvertimeSettingModel;
            restTime: AutoCalRestTimeSettingModel;

            constructor() {
                this.jobId = ko.observable('');
                this.normalOTTime = new AutoCalOvertimeSettingModel();
                this.flexOTTime = new AutoCalFlexOvertimeSettingModel();
                this.restTime = new AutoCalRestTimeSettingModel();

            }

            updateData(dto: JobAutoCalSettingDto) {
                this.jobId(dto.jobId);
                this.normalOTTime.updateData(dto.normalOTTime);
                this.flexOTTime.updateData(dto.flexOTTime);
                this.restTime.updateData(dto.restTime);

            }

            toDto(): JobAutoCalSettingDto {
                var dto: JobAutoCalSettingDto = {
                    jobId: this.jobId(),
                    normalOTTime: this.normalOTTime.toDto(),
                    flexOTTime: this.flexOTTime.toDto(),
                    restTime: this.restTime.toDto()
                };
                return dto;
            }
            resetData() {
                this.jobId('');
                this.normalOTTime.resetData();
                this.flexOTTime.resetData();
                this.restTime.resetData();
            }
        }

        //        ComAutoCalSettingModel
        export class ComAutoCalSettingModel {
            normalOTTime: AutoCalOvertimeSettingModel;
            flexOTTime: AutoCalFlexOvertimeSettingModel;
            restTime: AutoCalRestTimeSettingModel;

            constructor() {
                this.normalOTTime = new AutoCalOvertimeSettingModel();
                this.flexOTTime = new AutoCalFlexOvertimeSettingModel();
                this.restTime = new AutoCalRestTimeSettingModel();

            }

            updateData(dto: ComAutoCalSettingDto) {
                this.normalOTTime.updateData(dto.normalOTTime);
                this.flexOTTime.updateData(dto.flexOTTime);
                this.restTime.updateData(dto.restTime);

            }

            toDto(): ComAutoCalSettingDto {
                var dto: ComAutoCalSettingDto = {
                    normalOTTime: this.normalOTTime.toDto(),
                    flexOTTime: this.flexOTTime.toDto(),
                    restTime: this.restTime.toDto()

                };
                return dto;
            }
            resetData() {
                this.normalOTTime.resetData();
                this.flexOTTime.resetData();
                this.restTime.resetData();
            }
        }
        //        AutoCalOvertimeSettingDto
        export class AutoCalFlexOvertimeSettingModel {
            flexOtTime: AutoCalSettingModel;

            constructor() {
                this.flexOtTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalFlexOvertimeSettingDto) {
                this.flexOtTime.updateData(dto.flexOtTime);

            }

            toDto(): AutoCalFlexOvertimeSettingDto {
                var dto: AutoCalFlexOvertimeSettingDto = {
                    flexOtTime: this.flexOtTime.toDto(),

                };
                return dto;
            }
            resetData() {
                this.flexOtTime.resetData();
            }
        }

        //        AutoCalRestTimeSettingDto
        export class AutoCalRestTimeSettingModel {
            restTime: AutoCalSettingModel;
            lateNightTime: AutoCalSettingModel;

            constructor() {
                this.restTime = new AutoCalSettingModel();
                this.lateNightTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalRestTimeSettingDto) {
                this.restTime.updateData(dto.restTime);
                this.lateNightTime.updateData(dto.lateNightTime);

            }

            toDto(): AutoCalRestTimeSettingDto {
                var dto: AutoCalRestTimeSettingDto = {
                    restTime: this.restTime.toDto(),
                    lateNightTime: this.lateNightTime.toDto(),

                };
                return dto;
            }
            resetData() {
                this.restTime.resetData();
                this.lateNightTime.resetData();
            }
        }
        //        AutoCalFlexOvertimeSettingDto
        export class AutoCalOvertimeSettingModel {
            earlyOtTime: AutoCalSettingModel;
            earlyMidOtTime: AutoCalSettingModel;
            normalOtTime: AutoCalSettingModel;
            normalMidOtTime: AutoCalSettingModel;
            legalOtTime: AutoCalSettingModel;
            legalMidOtTime: AutoCalSettingModel;

            constructor() {
                this.earlyOtTime = new AutoCalSettingModel();
                this.earlyMidOtTime = new AutoCalSettingModel();
                this.normalOtTime = new AutoCalSettingModel();
                this.normalMidOtTime = new AutoCalSettingModel();
                this.legalOtTime = new AutoCalSettingModel();
                this.legalMidOtTime = new AutoCalSettingModel();

            }

            updateData(dto: AutoCalOvertimeSettingDto) {
                this.earlyOtTime.updateData(dto.earlyOtTime);
                this.earlyMidOtTime.updateData(dto.earlyMidOtTime);
                this.normalOtTime.updateData(dto.normalOtTime);
                this.normalMidOtTime.updateData(dto.normalMidOtTime);
                this.legalOtTime.updateData(dto.legalOtTime);
                this.legalMidOtTime.updateData(dto.legalMidOtTime);

            }

            toDto(): AutoCalOvertimeSettingDto {
                var dto: AutoCalOvertimeSettingDto = {
                    earlyOtTime: this.earlyOtTime.toDto(),
                    earlyMidOtTime: this.earlyMidOtTime.toDto(),
                    normalOtTime: this.normalOtTime.toDto(),
                    normalMidOtTime: this.normalMidOtTime.toDto(),
                    legalOtTime: this.legalOtTime.toDto(),
                    legalMidOtTime: this.legalMidOtTime.toDto()

                };
                return dto;
            }
            resetData() {
                this.earlyOtTime.resetData();
                this.earlyMidOtTime.resetData();
                this.normalOtTime.resetData();
                this.normalMidOtTime.resetData();
                this.legalOtTime.resetData();
                this.legalMidOtTime.resetData();
            }
        }
        //        AutoCalSettingDto
        export class AutoCalSettingModel {
            upLimitOtSet: KnockoutObservable<number>;
            calAtr: KnockoutObservable<number>;
            constructor() {
                this.upLimitOtSet = ko.observable(0);
                this.calAtr = ko.observable(0);
            }
            updateData(dto: AutoCalSettingDto) {
                this.upLimitOtSet(dto.upLimitOtSet);
                this.calAtr(dto.calAtr);
            }

            toDto(): AutoCalSettingDto {
                var dto: AutoCalSettingDto = {
                    upLimitOtSet: this.upLimitOtSet(),
                    calAtr: this.calAtr(),
                };
                return dto;
            }
            resetData() {
                this.upLimitOtSet(0);
                this.calAtr(0);
            }
        }

        export class UnitAutoCalSettingView {
            useWkpSet: any;
            useJobSet: any;
            useJobwkpSet: any;

            constructor(useWkpSet: boolean, useJobSet: boolean, useJobwkpSet: boolean) {
                this.useWkpSet = useWkpSet;
                this.useJobSet = useJobSet;
                this.useJobwkpSet = useJobwkpSet;
            }
        }


        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            workplaceId: string;
            isAlreadySetting: boolean;
        }

        export interface JobAlreadySettingModel {
            id: string;
            isAlreadySetting: boolean;
        }


    }
}