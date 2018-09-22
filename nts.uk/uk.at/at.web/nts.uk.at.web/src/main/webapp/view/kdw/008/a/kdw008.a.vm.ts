module nts.uk.at.view.kdw008.a {
    export module viewmodel {
        import getText = nts.uk.resource.getText;
        import confirm = nts.uk.ui.dialog.confirm;
        export class ScreenModel {

            //isDaily
            newMode: KnockoutObservable<boolean>;
            isUpdate: KnockoutObservable<boolean>;
            isRemove: KnockoutObservable<boolean>;
            showCode: KnockoutObservable<boolean>;

            checked: KnockoutObservable<boolean>;

            currentDailyFormatCode: KnockoutObservable<string>;
            currentDailyFormatName: KnockoutObservable<string>;

            currentBusinessType: KnockoutObservable<AuthorityDetailModel>;



            // list businessType
            businessTypeList: KnockoutObservableArray<BusinessTypeModel>;
            columns1: KnockoutObservableArray<NtsGridListColumn>;
            selectedCode: KnockoutObservable<any>;
            //combobox select sheetNo
            sheetNoList: KnockoutObservableArray<SheetNoModel>;
            itemNameCbb2: KnockoutObservable<string>;
            currentCodeCbb2: KnockoutObservable<number>;
            selectedSheetNo: KnockoutObservable<number>;

            //swap list tab 2
            monthlyDetailList: KnockoutObservableArray<DailyAttendanceAuthorityDetailDto>;
            columns3: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            columns4: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            columns5: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwapMonthly: KnockoutObservableArray<any>;
            authorityFormatMonthlyValue: KnockoutObservableArray<AttendanceItemModel>;
            monthlyDataSource: KnockoutObservableArray<AttendanceItemModel>;

            //swap list tab 1
            currentCodeListSwap2: KnockoutObservableArray<any>;
            authorityFormatDailyValue: KnockoutObservableArray<AttendanceItemModel>;
            columns2: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            selectedSheetName: KnockoutObservable<string>;
            dailyDataSource: KnockoutObservableArray<AttendanceItemModel>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            //is daily
            isDaily: KnockoutObservable<boolean>;

            columnsCommom: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;

            //monthly
            listMonthlyAttdItem: KnockoutObservableArray<any>;
            listMonthlyAttdItemFullData: KnockoutObservableArray<any>;
            valuesMonthly: KnockoutObservableArray<any>;

            listMonPfmCorrectionFormat: KnockoutObservableArray<any>;
            valuesMonthlyTab3: KnockoutObservableArray<any>;
            enableSheetNo: KnockoutObservable<boolean>;

            isSetFormatToDefault: KnockoutObservable<boolean>;

            //data commom
            listDataCommom: KnockoutObservableArray<any>;
            primaryKey: string;

            sideBar: KnockoutObservable<number>;
            constructor(dataShare: any) {

                var self = this;
                //monthly
                self.listMonthlyAttdItem = ko.observableArray([]);
                self.listMonthlyAttdItemFullData = ko.observableArray([]);
                self.valuesMonthly = ko.observableArray([]);

                self.listMonPfmCorrectionFormat = ko.observableArray([]);
                self.valuesMonthlyTab3 = ko.observableArray([]);
                self.enableSheetNo = ko.observable(false);

                self.isSetFormatToDefault = ko.observable(false);
                self.sideBar = ko.observable(1);
                //isdaily
                self.isDaily = ko.observable(dataShare.ShareObject);
                if (!self.isDaily()) {
                    self.sideBar(2);
                }

                self.newMode = ko.observable(false);
                self.isUpdate = ko.observable(true);
                self.isRemove = ko.observable(true);
                self.showCode = ko.observable(false);

                self.checked = ko.observable(true);

                self.currentDailyFormatCode = ko.observable('');
                self.currentDailyFormatName = ko.observable('');


                //data commom
                self.listDataCommom = ko.observableArray([]);
                self.primaryKey = '';

                if (self.isDaily()) {
                    self.columnsCommom = ko.observableArray([
                        { headerText: getText('KDW008_7'), key: 'dailyPerformanceFormatCode', width: 90 },
                        { headerText: getText('KDW008_8'), key: 'dailyPerformanceFormatName', width: 120, formatter: _.escape }
                    ]);
                    self.primaryKey = "dailyPerformanceFormatCode";
                } else {
                    self.columnsCommom = ko.observableArray([
                        { headerText: getText('KDW008_7'), key: 'monthlyPfmFormatCode', width: 90 },
                        { headerText: getText('KDW008_8'), key: 'monPfmCorrectionFormatName', width: 120, formatter: _.escape }
                    ]);
                    self.primaryKey = "monthlyPfmFormatCode";
                }

                self.selectedSheetName = ko.observable('');

                self.businessTypeList = ko.observableArray([]);
                self.currentBusinessType = ko.observable(new AuthorityDetailModel(null));

                this.selectedCode = ko.observable();

                self.columns3 = ko.observableArray([
                    { headerText: getText('KDW008_7'), key: 'attendanceItemDisplayNumber', width: 70 },
                    { headerText: 'number', key: 'attendanceItemId', hidden: true, width: 100 },
                    { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 100 , formatter: _.escape }
                ]);
                self.columns4 = ko.observableArray([
                    { headerText: getText('KDW008_7'), key: 'attendanceItemDisplayNumber', width: 70 },
                    { headerText: 'number', key: 'attendanceItemId', hidden: true, width: 100 },
                    { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 100 , formatter: _.escape }
                ]);
                self.columns2 = ko.observableArray([
                    { headerText: getText('KDW008_7'), key: 'attendanceItemDisplayNumber', width: 70 },
                    { headerText: 'number', key: 'attendanceItemId', hidden: true, width: 100 },
                    { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 100 , formatter: _.escape }
                ]);

                self.columns5 = ko.observableArray([
                    { headerText: getText('KDW008_7'), key: 'attendanceItemDisplayNumber', width: 70 },
                    { headerText: 'number', key: 'attendanceItemId', hidden: true, width: 100 },
                    { headerText: getText('KDW008_8'), key: 'attendanceItemName', width: 140 , formatter: _.escape}
                ]);

                //swap list 2
                self.monthlyDetailList = ko.observableArray([]);
                self.authorityFormatMonthlyValue = ko.observableArray([]);
                self.monthlyDataSource = ko.observableArray([]);
                var monthlySwapList = [];
                self.currentCodeListSwapMonthly = ko.observableArray(monthlySwapList);
                this.currentCodeListSwapMonthly.subscribe(function(value) {
                    console.log(value);
                });
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: getText('KDW008_14'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(self.isDaily()) },
                    { id: 'tab-2', title: getText('KDW008_13'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(self.isDaily()) },
                    { id: 'tab-3', title: getText('KDW008_13'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(!self.isDaily()) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                //combobox select sheetNo tab2
                self.sheetNoList = ko.observableArray([
                    new SheetNoModel('1', '1'),
                    new SheetNoModel('2', '2'),
                    new SheetNoModel('3', '3'),
                    new SheetNoModel('4', '4'),
                    new SheetNoModel('5', '5'),
                    new SheetNoModel('6', '6'),
                    new SheetNoModel('7', '7'),
                    new SheetNoModel('8', '8'),
                    new SheetNoModel('9', '9'),
                    new SheetNoModel('10', '10')
                ]);
                self.selectedSheetNo = ko.observable(1);
                self.selectedSheetNo.subscribe((value) => {
                    if (value == 1) {
                        self.enableSheetNo(false);
                    } else {
                        self.enableSheetNo(true);
                    }
                    if (self.isDaily()) {
                        nts.uk.ui.errors.clearAll();
                        self.getDetail(self.selectedCode());
                    } else {
                        let empSelect = _.find(self.listMonPfmCorrectionFormat(), format => {
                            return format.monthlyPfmFormatCode == self.selectedCode();
                        });

                        if (empSelect) {

                            self.isSetFormatToDefault(empSelect.setFormatToDefault);


                            self.checked(!empSelect.setFormatToDefault);
                            self.currentDailyFormatCode(empSelect.monthlyPfmFormatCode);
                            self.currentDailyFormatName(empSelect.monPfmCorrectionFormatName);
                            let index = _.findIndex(empSelect.displayItem.listSheetCorrectedMonthly, function(o) { return o.sheetNo == value; });
                            self.valuesMonthlyTab3([]);
                            self.listMonthlyAttdItem(_.cloneDeep(self.listMonthlyAttdItemFullData()));
                            if (index >= 0) {
                                self.selectedSheetName(_.filter(empSelect.displayItem.listSheetCorrectedMonthly, function(o) { return o.sheetNo == value; })[0].sheetName);
                                let monthlyAttdAutMonthly = _.sortBy(empSelect.displayItem
                                    .listSheetCorrectedMonthly[index]
                                    .listDisplayTimeItem, ['displayOrder']);
                                var attendanceItemModelMonthly = _.map(monthlyAttdAutMonthly, item => {
                                    var obj = {
                                        attendanceItemId: item.itemDaily,
                                        attendanceItemName: _.filter(self.listMonthlyAttdItemFullData(), function(o) { return item.itemDaily == o.attendanceItemId; })[0].attendanceItemName,
                                        attendanceItemDisplayNumber: _.filter(self.listMonthlyAttdItemFullData(), function(o) { return item.itemDaily == o.attendanceItemId; })[0].attendanceItemDisplayNumber,
                                        columnWidth: item.columnWidthTable
                                    };
                                    return new AttendanceItemModel(obj);
                                });

                                self.valuesMonthlyTab3(attendanceItemModelMonthly);
                            } else {
                                self.selectedSheetName("");
                                self.valuesMonthlyTab3([]);
                            }
                        } else {
                            self.selectedSheetName("");
                            self.valuesMonthlyTab3([]);
                            self.listMonthlyAttdItem(_.cloneDeep(self.listMonthlyAttdItemFullData()));
                        }
                    }
                    nts.uk.ui.errors.clearAll();
                });
                //swaplist 1
                var x = [];
                this.currentCodeListSwap2 = ko.observableArray(x);
                this.currentCodeListSwap2.subscribe(function(value) {
                    console.log(value);
                });
                self.authorityFormatDailyValue = ko.observableArray([]);
                self.dailyDataSource = ko.observableArray([]);

                self.selectedCode.subscribe(newValue => {
                    let CodeMonthly = _.cloneDeep(newValue);
                    self.isUpdate(true);
                    self.showCode(false);
                    if (nts.uk.text.isNullOrEmpty(CodeMonthly)) return;
                    _.defer(() => { $("#currentName").focus(); });
                    if (self.isDaily()) {
                        let empSelect = _.find(self.listDataCommom(), bus => {
                            return bus.dailyPerformanceFormatCode == CodeMonthly;
                        });
                        if (empSelect) {
                            self.currentDailyFormatCode(empSelect.dailyPerformanceFormatCode);
                            self.currentDailyFormatName(empSelect.dailyPerformanceFormatName);
                        }
                        self.isRemove(true);
                        nts.uk.ui.errors.clearAll();
//                        self.initSelectedSheetNoHasMutated();
                        // self.getDetail(self.currentDailyFormatCode(), 1);
                    } else {
//                        self.initSelectedSheetNoHasMutated();
                        self.isRemove(true);
                        nts.uk.ui.errors.clearAll();
                        //                            self.getDetail(self.currentDailyFormatCode(), 1);
                    }


                    //                    self.selectedTab('tab-1');
                });

            }

            jumpTo(sidebar) {
                let self = this;
                nts.uk.request.jump("/view/kdw/006/a/index.xhtml", { ShareObject: sidebar() });
            }

            initSelectedSheetNoHasMutated() {
                let self = this;
                if (self.selectedSheetNo() == 1) {
                    self.selectedSheetNo.valueHasMutated();
                } else {
                    self.selectedSheetNo(1);
                }
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                if (self.isDaily()) {
                    let dfdGetBusinessType = self.getBusinessType();
                    let dfdGetListMonthlyAttdItem = self.getListMonthlyAttdItem();
                    $.when(dfdGetBusinessType, dfdGetListMonthlyAttdItem).done(function(dfdGetBusinessTypeData, dfdGetListMonthlyAttdItemData) {
                        self.listDataCommom(self.businessTypeList());
                        dfd.resolve();
                    });
                } else {
                    let dfdGetListMonthlyAttdItem = self.getListMonthlyAttdItem();
                    let dfdListMonPfmCorrectionFormat = self.getListMonPfmCorrectionFormat();
                    $.when(dfdGetListMonthlyAttdItem, dfdListMonPfmCorrectionFormat)
                        .done(function(dfdGetListMonthlyAttdItemData, dfdListMonPfmCorrectionFormatData) {
                            if (self.listMonPfmCorrectionFormat().length > 0) {
                                self.selectedCode(self.listMonPfmCorrectionFormat()[0].monthlyPfmFormatCode);
                                self.selectedCode.valueHasMutated();
                            } else {
                                self.setNewMode();
                            }
                            dfd.resolve();
                        });
                }
                dfd.resolve();
                return dfd.promise();
            }

            getListMonthlyAttdItem() {

                let self = this;
                let dfd = $.Deferred();
                new service.Service().getListMonthlyAttdItem().done(function(data) {
                    self.listMonthlyAttdItem(_.sortBy(data, ["attendanceItemDisplayNumber"]));
                    self.listMonthlyAttdItemFullData(_.cloneDeep(_.sortBy(data, ["attendanceItemDisplayNumber"])));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getBusinessType() {
                let self = this;
                let dfd = $.Deferred();
                nts.uk.ui.block.grayout();
                self.businessTypeList([]);
                new service.Service().getBusinessType().done(function(data: Array<IDailyPerformanceFormatType>) {
                    if (data && data.length > 0) {
                        self.businessTypeList(_.map(data, item => { return new BusinessTypeModel(item) }));
                        self.currentDailyFormatCode(self.businessTypeList()[0].dailyPerformanceFormatCode);
                        self.currentDailyFormatName(self.businessTypeList()[0].dailyPerformanceFormatName);
                        self.selectedCode(self.businessTypeList()[0].dailyPerformanceFormatCode);
                        // self.getDetail(self.businessTypeList()[0].dailyPerformanceFormatCode);
                    } else {
                        self.setNewMode();
                    }
                    self.listDataCommom(self.businessTypeList());
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });

                return dfd.promise();
            }

            //monthly
            getListMonPfmCorrectionFormat() {
                let self = this;
                let dfd = $.Deferred();
                new service.Service().getListMonPfmCorrectionFormat().done(function(data) {

                    self.listMonPfmCorrectionFormat(_.sortBy(data, ["monthlyPfmFormatCode"]));
                    self.listDataCommom(self.listMonPfmCorrectionFormat());
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getMonPfmCorrectionFormat(monthlyPfmFormatCode) {
                let self = this;
                let dfd = $.Deferred();
                new service.Service().getMonPfmCorrectionFormat(monthlyPfmFormatCode).done(function(data) {
                    dfd.resolve();
                });
                return dfd.promise();
            }

            setNewMode() {
                let self = this;
                self.newMode(true);
                self.currentDailyFormatCode(null);
                self.currentDailyFormatName('');
                self.selectedCode(null);
                self.getDetail(self.selectedCode()).done(() => {
                    _.defer(() => { $("#currentCode").focus(); 
                    self.checked(true);
                    _.defer(() => {nts.uk.ui.errors.clearAll();});
                    
                    });
                });
                //$("#currentCode").focus();
                self.checked(true);
                self.showCode(true);
                self.isUpdate(false);
                self.isRemove(false);

                if (!self.isDaily()) {
                    self.isSetFormatToDefault(true);
                    self.selectedSheetName("");
                    self.valuesMonthlyTab3([]);
                    self.listMonthlyAttdItem(_.cloneDeep(self.listMonthlyAttdItemFullData()));
                    _.defer(() => {nts.uk.ui.errors.clearAll();});
                }
                
                    _.defer(() => {nts.uk.ui.errors.clearAll();});
            }

            btnSheetNo() {
                let self = this;
                if (self.isDaily()) {
                    //add or update Monthly
                    let deleteBySheet = {
                        dailyPerformanceFormatCode : self.selectedCode(),
                        sheetNo : self.selectedSheetNo()                            
                    };
                    nts.uk.ui.block.invisible();
                    confirm({ messageId: "Msg_18" }).ifYes(() => {
                        new service.Service().deleteAuthBySheet(deleteBySheet).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_991" }).then(() => {
                               self.reloadData(self.currentDailyFormatCode());
                               self.initSelectedSheetNoHasMutated();
                            });
                        }).always(function() {
                            nts.uk.ui.block.clear();
                        }).fail(function(error) {
                            $('#currentCode').ntsError('set', error);
                        }); 
                    }).ifNo(() => {
                        nts.uk.ui.block.clear();
                    });
                    
                } else {
                    //monthly
                    let listDisplayTimeItem = [];
                    let listSheetMonthly = [new SheetCorrectedMonthly(
                        self.selectedSheetNo(),
                        "",
                        listDisplayTimeItem
                    )];

                    let temp = new MonPfmCorrectionFormat("", self.currentDailyFormatCode(), self.currentDailyFormatName(),
                        new MonthlyActualResults(listSheetMonthly),
                        self.checked()
                    );
                    nts.uk.ui.block.invisible();
                    confirm({ messageId: "Msg_18" }).ifYes(() => {
                        new service.Service().updateMonPfmCorrectionFormat(temp).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_991" }).then(() => {
                                self.getListMonPfmCorrectionFormat().done(function(data) {
                                    self.initSelectedSheetNoHasMutated();
                                });
                            });
                        }).always(function() {
                            nts.uk.ui.block.clear();
                        }).fail(function(error) {
                            $('#currentCode').ntsError('set', error);
                        });
                    }).ifNo(() => {
                        nts.uk.ui.block.clear();
                    });

                }
            }

            getDetail(dailyPerformanceFormatCode: string) {
                let self = this,
                    dfd = $.Deferred();
                if (self.isDaily()) {
                    new service.Service().getDailyPerformance(dailyPerformanceFormatCode, self.selectedSheetNo()).done(function(data: IDailyPerformanceFormatTypeDetail) {

                        if (data) {
                            data.attendanceItemDtos = _.sortBy(data.attendanceItemDtos, ["attendanceItemDisplayNumber"]);
                            if (data.isDefaultInitial == 1) {
                                self.isSetFormatToDefault(false);
                                self.checked(true);
                            } else {
                                self.isSetFormatToDefault(true);
                                self.checked(false);
                            }
                            self.valuesMonthly([]);
                            self.authorityFormatDailyValue([]);
                            self.currentBusinessType(new AuthorityDetailModel(data));
                            self.currentBusinessType().attendanceItemDtos.valueHasMutated();
                            // show data tab 2
                            var dailyDataSource = _.map(self.currentBusinessType().attendanceItemDtos(), item => {
                                var obj = {
                                    attendanceItemId: item.attendanceItemId,
                                    attendanceItemName: item.attendanceItemName,
                                    attendanceItemDisplayNumber: item.attendanceItemDisplayNumber,
                                    columnWidth: item.columnWidth
                                }
                                return new AttendanceItemModel(obj);
                            })
                            self.dailyDataSource(_.clone(dailyDataSource));
                            self.dailyDataSource.valueHasMutated();
                            data.dailyAttendanceAuthorityMonthlyDto = _.sortBy(data.dailyAttendanceAuthorityMonthlyDto, ["order"]);
                            if (data.dailyAttendanceAuthorityMonthlyDto) {
                                var attendanceItemModelMonthly = _.map(data.dailyAttendanceAuthorityMonthlyDto, item => {
                                    var obj = {
                                        attendanceItemId: item.attendanceItemId,
                                        attendanceItemName: item.attendanceItemName,
                                        attendanceItemDisplayNumber: item.dislayNumber,
                                        columnWidth: item.columnWidth
                                    };
                                    return new AttendanceItemModel(obj);
                                });
                                self.valuesMonthly(attendanceItemModelMonthly);

                            } else self.valuesMonthly([]);
                            //show data tab 1
                            //self.selectedSheetNo(data.businessTypeFormatDailyDto.sheetNo);
                            self.selectedSheetName(data.dailyAttendanceAuthorityDailyDto.sheetName);
                            self.currentBusinessType().attendanceItemDtos.valueHasMutated();
                            self.monthlyDataSource(_.clone(dailyDataSource));
                            self.monthlyDataSource.valueHasMutated();
                            if (data.dailyAttendanceAuthorityDailyDto != null && data.dailyAttendanceAuthorityDailyDto.dailyAttendanceAuthorityDetailDtos) {
                                data.dailyAttendanceAuthorityDailyDto.dailyAttendanceAuthorityDetailDtos = _.sortBy(data.dailyAttendanceAuthorityDailyDto.dailyAttendanceAuthorityDetailDtos, ["order"]);
                                var attendanceItemModelDaily = _.map(data.dailyAttendanceAuthorityDailyDto.dailyAttendanceAuthorityDetailDtos, item => {
                                    var daily = {
                                        attendanceItemId: item.attendanceItemId,
                                        attendanceItemName: item.attendanceItemName,
                                        attendanceItemDisplayNumber: item.dislayNumber,
                                        columnWidth: item.columnWidth
                                    }
                                    return new AttendanceItemModel(daily);
                                });
                                self.authorityFormatDailyValue(attendanceItemModelDaily);
                            } else {
                                self.authorityFormatDailyValue([]);
                            }
                            if (!self.newMode()) {
                                $("#currentName").focus();
                            }
                        } else {
                            self.currentBusinessType([]);
                        }
                        dfd.resolve();
                    }).fail(error => {

                    });
                } else {
                    dfd.resolve();
                }
                return dfd.promise();

            }

            remove() {
                let self = this;
                if (self.isDaily()) {
                    let isDefaultInitial = 0;
                    if (self.checked() == true) {
                        let isDefaultInitial = 1;
                    }
                    var removeAuthorityDto = {
                        dailyPerformanceFormatCode: self.currentDailyFormatCode(),
                        isDefaultInitial: isDefaultInitial
                    }
                    nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                        .ifYes(() => {
                            new service.Service().removeAuthorityDailyFormat(removeAuthorityDto).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                                    self.reloadData(removeAuthorityDto.dailyPerformanceFormatCode, true);    
                                    self.initSelectedSheetNoHasMutated();
                                });
                            }).fail(function(error) {
                            });
                            
                        });
                } else {
                    let deleteMonPfmCmd = {
                        companyID: "",
                        monthlyPfmFormatCode: self.currentDailyFormatCode()
                    };
                    nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                        .ifYes(() => {
                            new service.Service().deleteMonPfmCorrectionFormat(deleteMonPfmCmd).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                                    let indexOfItemOld = _.findIndex(self.listDataCommom(), { monthlyPfmFormatCode: self.currentDailyFormatCode() });
                                    let indexNext = 0;
                                    if ((indexOfItemOld) == self.listDataCommom().length - 1) {
                                        indexNext = self.listDataCommom().length - 2;
                                    } else {
                                        indexNext = (indexOfItemOld + 1);
                                    }
                                    if (self.listDataCommom().length > 1) {
                                        let code = self.listDataCommom()[indexNext].monthlyPfmFormatCode;
                                    }
                                    self.getListMonPfmCorrectionFormat().done(function(data) {
                                        if (self.listDataCommom().length == 0) {
                                            self.listDataCommom([]);
                                            self.setNewMode();
                                        } else {
                                            self.selectedCode(code);
                                            self.selectedCode.valueHasMutated();
                                        }
                                    });
                                    $("#currentName").focus();
                                })
                            }).fail(function(error) {
                            });
                        });
                }

            }

            addOrUpdateClick() {
                let self = this;
                $("#currentName").trigger("validate");
                
                if (!nts.uk.ui.errors.hasError()) {
                    if (self.isDaily()) {
                        $("#checkSheetNameIsDaily").trigger("validate");
                        if (!nts.uk.ui.errors.hasError()) {
                             if (self.authorityFormatDailyValue().length <= 0) {
                                nts.uk.ui.dialog.alert({ messageId: "Msg_920" });
                            } else {
                                self.register();
                            }
                        }
                    } else {
                        $("#currentCode").trigger("validate");
                        $("#currentName").trigger("validate");
                        $("#selectedSheetName").trigger("validate");
                        if (!nts.uk.ui.errors.hasError()) {
                            if (self.valuesMonthlyTab3().length <= 0) {
                                nts.uk.ui.dialog.alert({ messageId: "Msg_920" });
                            } else {
                                self.register();
                            }
                        }

                    }
                }
            }

            register() {
                let self = this;
                $(".need-check").trigger("validate");
                if (self.isDaily()) {
                    if (!nts.uk.ui.errors.hasError()) {
                        //add or update Monthly
                        var authorityFormatDetailDtos = _.map(self.valuesMonthly(), item => {
                            var indexOfItem = _.findIndex(self.valuesMonthly(), { attendanceItemId: item.attendanceItemId });
                            var obj = {
                                attendanceItemId: item.attendanceItemId,
                                dislayNumber: item.attendanceItemDisplayNumber,
                                attendanceItemName: item.attendanceItemName,
                                order: indexOfItem,
                                columnWidth: item.columnWidth ? item.columnWidth : null
                            };
                            return new DailyAttendanceAuthorityDetailDto(obj);
                        });
                        var addOrUpdateBusinessFormatMonthly = new AddAuthorityFormatMonthly(self.currentDailyFormatCode(), authorityFormatDetailDtos);

                        //add or update Daily
                        var businessTypeFormatDetailDailyDto = _.map(self.authorityFormatDailyValue(), item => {
                            var indexOfDaily = _.findIndex(self.authorityFormatDailyValue(), { attendanceItemId: item.attendanceItemId });
                            var monthly = {
                                attendanceItemId: item.attendanceItemId,
                                dislayNumber: item.attendanceItemDisplayNumber,
                                attendanceItemName: item.attendanceItemName,
                                order: indexOfDaily,
                                columnWidth: item.columnWidth ? item.columnWidth : null
                            };
                            return new DailyAttendanceAuthorityDetailDto(monthly);
                        });

                        if (self.checked() == true) {
                            var addOrUpdateBusinessFormatDaily = new AddAuthorityFormatDaily(self.currentDailyFormatCode(), self.currentDailyFormatName(), self.selectedSheetNo(), self.selectedSheetName(), businessTypeFormatDetailDailyDto, 1);
                        } else {
                            var addOrUpdateBusinessFormatDaily = new AddAuthorityFormatDaily(self.currentDailyFormatCode(), self.currentDailyFormatName(), self.selectedSheetNo(), self.selectedSheetName(), businessTypeFormatDetailDailyDto, 0);
                        }

                        var addOrUpdateDailyFormat = new AddOrUpdateDailyFormat(addOrUpdateBusinessFormatMonthly, addOrUpdateBusinessFormatDaily);
                        nts.uk.ui.block.invisible();
                        if (self.isUpdate() == true) {
                            new service.Service().updateDailyDetail(addOrUpdateDailyFormat).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                    self.reloadData(self.currentDailyFormatCode());
                                });
                                $("#currentName").focus();
                            }).always(function() {
                                nts.uk.ui.block.clear();
                            }).fail(function(error) {
                                nts.uk.ui.dialog.alertError(error.message);
                            });
                        } else {
                            new service.Service().addDailyDetail(addOrUpdateDailyFormat).done(function() {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                    self.reloadData(self.currentDailyFormatCode());
                                });

                                $("#currentName").focus();
                            }).always(function() {
                                nts.uk.ui.block.clear();
                            }).fail(function(error) {
                                //                        nts.uk.ui.dialog.alertError(error.message);
                                $('#currentCode').ntsError('set', error);
                            });
                        }
                        //self.getDetail(self.currentDailyFormatCode());
                    }
                } else {
                    //monthly
                    let listDisplayTimeItem = [];
                    for (let i = 0; i < self.valuesMonthlyTab3().length; i++) {
                        var indexOfItem = _.findIndex(self.valuesMonthlyTab3(), { attendanceItemId: self.valuesMonthlyTab3()[i].attendanceItemId });
                        let obj = new DisplayTimeItem(
                            indexOfItem,
                            self.valuesMonthlyTab3()[i].attendanceItemId,
                            null
                        );
                        listDisplayTimeItem.push(obj);
                    }


                    let listSheetMonthly = [new SheetCorrectedMonthly(
                        self.selectedSheetNo(),
                        self.selectedSheetName(),
                        listDisplayTimeItem
                    )];

                    let temp = new MonPfmCorrectionFormat("", self.currentDailyFormatCode(), self.currentDailyFormatName(),
                        new MonthlyActualResults(listSheetMonthly),
                        self.checked()
                    );
                    if (!self.isRemove()) {
                        nts.uk.ui.block.invisible();
                        new service.Service().addMonPfmCorrectionFormat(temp).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.getListMonPfmCorrectionFormat().done(function(data) {
                                    nts.uk.ui.block.clear();
                                    self.selectedCode(self.currentDailyFormatCode());
                                    self.selectedCode.valueHasMutated();
                                });
                            });
                            $("#currentName").focus();
                        }).always(function() {
                        }).fail(function(error) {
                            nts.uk.ui.block.clear();
                            $('#currentCode').ntsError('set', error);
                        });
                    } else {
                        nts.uk.ui.block.invisible();
                        new service.Service().updateMonPfmCorrectionFormat(temp).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.getListMonPfmCorrectionFormat().done(function(data) {
                                    self.selectedCode(self.currentDailyFormatCode());
                                    self.selectedCode.valueHasMutated();
                                });
                            });
                            $("#currentName").focus();
                        }).always(function() {
                            nts.uk.ui.block.clear();
                        }).fail(function(error) {
                            $('#currentCode').ntsError('set', error);
                        });

                    }
                }


            }

            reloadData(dailyPerformanceFormatCode: string, isRemove?: boolean) {
                let self = this,
                    dfd = $.Deferred();
                let oldSelectIndex = _.findIndex(self.listDataCommom(), item => { return item.dailyPerformanceFormatCode == dailyPerformanceFormatCode; });
                self.listDataCommom([]);
                new service.Service().getBusinessType().done(function(data: Array<IDailyPerformanceFormatType>) {
                    if (data && data.length > 0) {
                        data = _.orderBy(data, ["dailyPerformanceFormatCode"], ['asc']);
                        self.listDataCommom(_.map(data, item => { return new BusinessTypeModel(item) }));
                        self.currentDailyFormatCode(dailyPerformanceFormatCode);
                        //                        self.currentDailyFormatName(self.businessTypeList()[0].dailyPerformanceFormatName);
                        //                        self.selectedCode(dailyPerformanceFormatCode);
//                        self.selectedSheetNo(1);
                        self.getDetail(dailyPerformanceFormatCode);
                        if (isRemove && isRemove == true) {
                            self.selectedCode(self.getNewSelectRemove(oldSelectIndex));
                        } else {
                            self.selectedCode(dailyPerformanceFormatCode);
                        }
                    } else {
                        self.setNewMode();
                    }
                    
                    dfd.resolve();
                });

                return dfd.promise();
            }

            getNewSelectRemove(oldSelectIndex: number): String {
                let self = this;
                let dataLength = self.listDataCommom().length;
                if (dataLength == 1 || oldSelectIndex > dataLength) {
                    return self.listDataCommom()[0].dailyPerformanceFormatCode;
                }
                if (oldSelectIndex <= dataLength - 1) {
                    return self.listDataCommom()[oldSelectIndex].dailyPerformanceFormatCode;
                }
                if (oldSelectIndex == dataLength) {
                    return self.listDataCommom()[oldSelectIndex - 1].dailyPerformanceFormatCode;
                }
                return null;
            }

        }

        //monthly class :
        export class MonPfmCorrectionFormat {
            companyID: string;
            monthlyPfmFormatCode: string;
            monPfmCorrectionFormatName: string;
            displayItem: MonthlyActualResults;
            setFormatToDefault: boolean;
            constructor(companyID: string,
                monthlyPfmFormatCode: string,
                monPfmCorrectionFormatName: string,
                displayItem: MonthlyActualResults,
                setFormatToDefault: boolean) {
                this.companyID = companyID;
                this.monthlyPfmFormatCode = monthlyPfmFormatCode;
                this.monPfmCorrectionFormatName = monPfmCorrectionFormatName;
                this.displayItem = displayItem;
                this.setFormatToDefault = setFormatToDefault;

            }
        }

        export class MonthlyActualResults {
            listSheetCorrectedMonthly: Array<SheetCorrectedMonthly>;
            constructor(listSheetCorrectedMonthly: Array<SheetCorrectedMonthly>) {
                this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
            }
        }

        export class SheetCorrectedMonthly {
            sheetNo: number;
            sheetName: string;
            listDisplayTimeItem: Array<DisplayTimeItem>;
            constructor(sheetNo: number,
                sheetName: string,
                listDisplayTimeItem: Array<DisplayTimeItem>) {
                this.sheetNo = sheetNo;
                this.sheetName = sheetName;
                this.listDisplayTimeItem = listDisplayTimeItem;
            }
        }

        export class DisplayTimeItem {
            displayOrder: number;
            itemDaily: number;
            columnWidthTable: Number;
            constructor(
                displayOrder: number,
                itemDaily: number,
                columnWidthTable: Number) {
                this.displayOrder = displayOrder;
                this.itemDaily = itemDaily;
                this.columnWidthTable = (columnWidthTable == null ? null : columnWidthTable);
            }
        }

        export class SheetNoModel {
            sheetNoId: string;
            sheetNoName: string;
            constructor(sheetNoId: string, sheetNoName: string) {
                this.sheetNoId = sheetNoId;
                this.sheetNoName = sheetNoName;
            }
        }

        export class AddOrUpdateDailyFormat {
            authorityMonthlyCommand: KnockoutObservable<AddAuthorityFormatMonthly>;
            authorityDailyCommand: KnockoutObservable<AddAuthorityFormatDaily>;
            constructor(addOrUpdateAuthorityFormatMonthly: KnockoutObservable<AddAuthorityFormatMonthly>, addOrUpdateAuthorityFormatDaily: KnockoutObservable<AddAuthorityFormatDaily>) {
                let self = this;
                self.authorityMonthlyCommand = addOrUpdateAuthorityFormatMonthly;
                self.authorityDailyCommand = addOrUpdateAuthorityFormatDaily;
            }
        }

        export class AddAuthorityFormatMonthly {
            dailyPerformanceFormatCode: string;
            dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>;
            constructor(dailyPerformanceFormatCode: string, dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>) {
                let self = this;
                self.dailyPerformanceFormatCode = dailyPerformanceFormatCode || "";
                self.dailyAttendanceAuthorityDetailDtos = dailyAttendanceAuthorityDetailDtos || [];
            }
        }

        export class AddAuthorityFormatDaily {
            dailyPerformanceFormatCode: string;
            dailyPerformanceFormatName: string;
            sheetNo: number;
            sheetName: string;
            dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>;
            isDefaultInitial: number;
            constructor(dailyPerformanceFormatCode: string, dailyPerformanceFormatName: string, sheetNo: number, sheetName: string, dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>, isDefaultInitial: number) {
                this.dailyPerformanceFormatCode = dailyPerformanceFormatCode || "";
                this.dailyPerformanceFormatName = dailyPerformanceFormatName || "";
                this.sheetNo = sheetNo || 0;
                this.sheetName = sheetName || null;
                this.dailyAttendanceAuthorityDetailDtos = dailyAttendanceAuthorityDetailDtos || [];
                this.isDefaultInitial = isDefaultInitial;
            }
        }

        export class BusinessTypeModel {
            dailyPerformanceFormatCode: string;
            dailyPerformanceFormatName: string;
            constructor(data: IDailyPerformanceFormatType) {
                let self = this;
                if (!data) return;
                self.dailyPerformanceFormatCode = data.dailyPerformanceFormatCode || "";
                self.dailyPerformanceFormatName = data.dailyPerformanceFormatName || "";
            }
        }

        export class AttendanceItemModel {
            attendanceItemId: number = 0;
            attendanceItemName: string = "";
            attendanceItemDisplayNumber: number = 0;
            columnWidth: number;
            constructor(data: IAttendanceItem) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId || 0;
                this.attendanceItemName = data.attendanceItemName || "";
                this.attendanceItemDisplayNumber = data.attendanceItemDisplayNumber || 0;
                this.columnWidth = data.columnWidth;
            }
        }

        export class DailyAttendanceAuthorityDetailDto {
            attendanceItemId: number = 0;
            dislayNumber: number = 0;
            attendanceItemName: string = '';
            order: number = 0;
            columnWidth: number = 0;
            constructor(data: IAuthorityFormatDetail) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId || 0;
                this.dislayNumber = data.dislayNumber || 0;
                this.attendanceItemName = data.attendanceItemName || '';
                this.order = data.order || 0;
                this.columnWidth = data.columnWidth || 0;
            }
        }

        export class DailyAttendanceAuthorityDailyDto {
            sheetNo: number;
            sheetName: string;
            dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>;
            constructor(sheetNo: number, sheetName: string, dailyAttendanceAuthorityDetailDtos: Array<DailyAttendanceAuthorityDetailDto>) {
                this.sheetNo = sheetNo || 0;
                this.sheetName = sheetName || null;
                this.dailyAttendanceAuthorityDetailDtos = dailyAttendanceAuthorityDetailDtos || [];
            }
        }

        export class AuthorityDetailModel {
            attendanceItemDtos: KnockoutObservableArray<AttendanceItemModel> = ko.observableArray([]);
            dailyAttendanceAuthorityDailyDto: KnockoutObservable<DailyAttendanceAuthorityDailyDto> = ko.observable(new DailyAttendanceAuthorityDailyDto(null, null, null));
            dailyAttendanceAuthorityMonthlyDto: KnockoutObservableArray<DailyAttendanceAuthorityDetailDto> = ko.observableArray([]);
            isDefaultInitial: number = 0;
            constructor(data: IDailyPerformanceFormatTypeDetail) {
                if (!data) return;
                this.attendanceItemDtos(data.attendanceItemDtos ? _.map(data.attendanceItemDtos, item => { return new AttendanceItemModel(item) }) : []);
                this.dailyAttendanceAuthorityDailyDto(data.dailyAttendanceAuthorityDailyDto ? new DailyAttendanceAuthorityDailyDto(data.dailyAttendanceAuthorityDailyDto.sheetNo, data.dailyAttendanceAuthorityDailyDto.sheetName, data.dailyAttendanceAuthorityDailyDto.dailyAttendanceAuthorityDetailDtos) : null);
                this.dailyAttendanceAuthorityMonthlyDto(data.dailyAttendanceAuthorityMonthlyDto ? _.map(data.dailyAttendanceAuthorityMonthlyDto, item => { return new DailyAttendanceAuthorityDetailDto(item) }) : []);
                this.isDefaultInitial = data.isDefaultInitial;
            }
        }

        export interface IDailyPerformanceFormatTypeDetail {
            attendanceItemDtos: Array<IAttendanceItem>;
            dailyAttendanceAuthorityDailyDto: IAuthorityFormatDaily;
            dailyAttendanceAuthorityMonthlyDto: Array<IAuthorityFormatDetail>;
            isDefaultInitial: number;
        }

        export interface IDailyPerformanceFormatType {
            dailyPerformanceFormatCode: string;
            dailyPerformanceFormatName: string;
        }
        export interface IAttendanceItem {
            attendanceItemId: number;
            attendanceItemName: string;
            attendanceItemDisplayNumber: number;
            columnWidth: number;
        }
        export interface IAuthorityFormatDetail {
            attendanceItemId: number;
            dislayNumber: number;
            attendanceItemName: string;
            order: number;
            columnWidth: number;
        }
        export interface IAuthorityFormatDaily {
            sheetNo: number;
            sheetName: string;
            dailyAttendanceAuthorityDetailDtos: Array<IAuthorityFormatDetail>;
        }

    }
}

