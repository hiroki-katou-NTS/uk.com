module cmm011.a.viewmodel {


    export class ScreenModel {

        //  list box
        itemHistId: KnockoutObservableArray<any>;
        itemName_histId: KnockoutObservable<string>;
        historyId: KnockoutObservable<any>;
        selectedCodes_His: KnockoutObservable<any>;
        isEnable_histId: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<any>;
        arr: any;

        // treegrid
        dataSource: KnockoutObservableArray<any>;
        dataSource2: KnockoutObservableArray<any>;
        currentItem_treegrid: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<string>;
        selectedCodes_treegrid: any;
        headers: any;
        lengthTreeBegin: KnockoutObservable<number>;
        lengthTreeCurrent: KnockoutObservable<number>;

        A_INP_002: KnockoutObservable<string>;
        A_INP_002_enable: KnockoutObservable<boolean>;
        A_INP_003: KnockoutObservable<string>;
        A_INP_004: KnockoutObservable<string>;
        A_INP_006_enable: KnockoutObservable<boolean>;
        A_INP_007: KnockoutObservable<string>;
        A_INP_008: KnockoutObservable<string>;
        allowClick: KnockoutObservable<boolean> = ko.observable(true);
        checknull: KnockoutObservable<string>;
        filteredData2: any;
        itemaddHist: any;
        numberItemNew: KnockoutObservable<number>;
        listDtothaydoi: KnockoutObservable<any>;
        dtoAdd: KnockoutObservable<any>;
        checkAddHist1: KnockoutObservable<string>;
        newEndDate: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.itemHistId = ko.observableArray([]);
            self.itemName_histId = ko.observable('');
            self.historyId = ko.observable('');
            self.selectedCodes_His = ko.observable('');
            //self.selectedCodes_His = ko.observableArray([]);
            self.isEnable_histId = ko.observable(true);
            self.itemHist = ko.observable(null);
            self.arr = ko.observableArray([]);

            self.dataSource = ko.observableArray([]);
            self.dataSource2 = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.selectedCodes_treegrid = ko.observableArray([]);
            self.headers = ko.observableArray(["", ""]);
            self.lengthTreeCurrent = ko.observable(null);
            self.lengthTreeBegin = ko.observable(null);
            self.numberItemNew = ko.observable(0);

            self.A_INP_002 = ko.observable(null);
            self.A_INP_002_enable = ko.observable(false);
            self.A_INP_003 = ko.observable(null);
            self.A_INP_004 = ko.observable(null);
            self.A_INP_007 = ko.observable(null);
            self.A_INP_008 = ko.observable(null);
            self.currentItem_treegrid = ko.observable(null);
            self.checknull = ko.observable(null);
            self.listDtothaydoi = ko.observable(null);
            self.dtoAdd = ko.observable(null);
            self.checkAddHist1 = ko.observable('');
            self.newEndDate = ko.observable(null);


            self.singleSelectedCode.subscribe(function(codeChangeds) {
                var _dt = self.dataSource();
                var _code = self.singleSelectedCode();
                var current = self.findHira(_code, _dt);
                if (current.historyId == "") {
                    self.A_INP_002_enable(true);
                    self.A_INP_002("");
                    self.A_INP_003("");
                    self.A_INP_004("");
                    self.A_INP_007(""); $("#A_INP_002").focus();
                }
                else {
                    self.A_INP_002(current.departmentCode);
                    self.A_INP_003(current.name);
                    self.A_INP_004(current.fullName);
                    self.A_INP_007(current.externalCode);
                    self.A_INP_002_enable(false);
                }
            });

            self.selectedCodes_His.subscribe((function(codeChanged) {
                self.findHist_Dep(self.itemHistId(), codeChanged);
                if (self.itemHist() != null) {
                    if (self.itemHist().historyId != "") {
                        for (var i = 0; i < self.itemHistId().length; i++) {
                            if (self.itemHistId()[i].historyId == "") {
                                let item = self.itemHistId()[i];
                                self.itemHistId.remove(item);
                            }
                        }
                        self.historyId(self.itemHist().historyId);
                        //get position by historyId
                        var dfd = $.Deferred();
                        service.getAllWorkPLaceByHistId(self.historyId())
                            .done(function(department_arr: Array<viewmodel.model.DtoWKP>) {
                                self.dataSource(department_arr);
                                if (self.dataSource().length > 0) {
                                    self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                    self.singleSelectedCode(self.dataSource()[0].departmentCode);
                                    self.A_INP_003(self.dataSource()[0].name);
                                    self.A_INP_004(self.dataSource()[0].fullName);
                                    if (self.dataSource()[0].externalCode != null)
                                        self.A_INP_007(self.dataSource()[0].externalCode);
                                }
                            }).fail(function(error) {
                                alert(error.message);
                            })
                        service.getMemoWorkPLaceByHistId(self.historyId())
                            .done(function(memo: viewmodel.model.MemoDto) {
                                if (memo != null) {
                                    self.A_INP_008(memo.memo);
                                }
                            }).fail(function(error) {
                                alert(error.message);
                            })
                        dfd.resolve();
                        return dfd.promise();
                    } else {
                        console.log("=== historyId null");
                    }
                }
            }));
        }

        register() {
            var self = this;
            /*case add item lần đầu khi history == null*/
            if (self.checknull() === "landau" && self.itemHistId().length == 1 && self.checkInput()) {
                let dto = new model.AddWorkplaceDto(self.A_INP_002(), null, "9999/12/31", self.A_INP_007(), self.A_INP_004(), "001", self.A_INP_003(), self.itemaddHist.startDate, self.A_INP_008(), self.A_INP_003(), "1", "1", null, null, null);
                var dfd = $.Deferred();
                let arr = new Array;
                arr.push(dto);
                service.addWorkPlace(arr)
                    .done(function(mess: any) {
                        self.start();
                        location.reload();
                    }).fail(function(error) {
                        if (error.message == "ER026") {
                            alert("trung companyCode");
                        }
                    })
                dfd.resolve();
                return dfd.promise();
            }
            /*case update item*/
            if (self.A_INP_002_enable() == false && self.checkInput() && self.checkAddHist1() == '') {
                var dfd = $.Deferred();
                let hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                var _dt = self.dataSource();
                var _code = self.singleSelectedCode();
                var current = self.findHira(_code, _dt);
                let dto = new model.AddWorkplaceDto(self.A_INP_002(), hisdto.historyId, hisdto.endDate, self.A_INP_007(), self.A_INP_004(), current.hierarchyCode, self.A_INP_003(), hisdto.startDate, self.A_INP_008(), current.shortName, current.parentChildAttribute1, current.parentChildAttribute2, null, null, null);
                let arr = new Array;
                arr.push(dto);
                debugger;
                service.upDateListWorkplace(arr)
                    .done(function(mess: any) {
                        location.reload();
                    }).fail(function(error) {
                        if (error.message == "ER005") {
                            alert("ko ton tai");
                        }
                    })

                dfd.resolve();
                return dfd.promise();
            }
            /*case add item trong trường hợp histtory không thay đổi*/
            if (self.numberItemNew() == 1 && self.checkInput()) {
                var self = this;
                var dfd = $.Deferred();
                let hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                let _dto = new model.AddWorkplaceDto(self.A_INP_002(), hisdto.historyId, hisdto.endDate, self.A_INP_007(), self.A_INP_004(), self.dtoAdd().hierarchyCode, self.A_INP_003(), hisdto.startDate, self.A_INP_008(), self.A_INP_004(), "1", "1", null, null, null);
                let data = self.listDtothaydoi();
                let arr = new Array;
                arr.push(_dto);
                debugger;
                if (data != null) {
                    service.upDateListWorkplace(data)
                        .done(function(mess) {
                            var dfd2 = $.Deferred();
                            service.addWorkPlace(arr)
                                .done(function(mess: any) {
                                    self.start();
                                    location.reload();
                                })
                                .fail(function(error) {
                                    if (error.message == "ER026") {
                                        alert("入力した " + _dto.workplaceCode + "は既に存在しています。\r\n " + _dto.departmentCode + "  を確認してください。 ");
                                    }
                                })
                            dfd2.resolve();
                            return dfd2.promise();
                        }).fail(function(error) {
                            if (error.message == "ER005") {
                                alert("ko ton tai");
                            }
                        })
                    dfd.resolve();
                    return dfd.promise();
                } else {
                    var dfd2 = $.Deferred();
                    service.addWorkPlace(arr)
                        .done(function(mess: any) {
                            self.start();
                            location.reload();
                        })
                        .fail(function(error) {
                            if (error.message == "ER026") {
                                alert("入力した " + _dto.workplaceCode + "は既に存在しています。\r\n " + _dto.departmentCode + "  を確認してください。 ");
                            }
                        })
                    dfd2.resolve();
                    return dfd2.promise();
                }
            }
            if (self.checkAddHist1() == "AddhistoryFromLatest") {
                console.log(self.dataSource2());
                let _dt = self.dataSource2();
                if (_dt.length > 0) {
                    _dt[0].memo = self.A_INP_008();
                }
                self.dataSource2(_dt);
                var dfd2 = $.Deferred();
                service.addListWorkPlace(self.dataSource2())
                    .done(function(mess: any) {
                        var dfd2 = $.Deferred();
                        let _dto = new model.AddWorkplaceDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, null, null, null, null, null, "addhistoryfromlatest", null, null, null, null, null, null);
                        let arr = new Array;
                        arr.push(_dto);
                        service.upDateEndDateWkp(arr)
                            .done(function() {
                                location.reload();
                            })
                            .fail(function() { })
                        self.start();
                    })
                    .fail(function(error) { })
                dfd2.resolve();
                return dfd2.promise();
            }
            if (self.checkAddHist1() == "AddhistoryFromBeggin") {
                if (self.checkInput()) {
                    let _dto = new model.AddWorkplaceDto(self.A_INP_002(), null, self.itemHistId()[0].endDate, self.A_INP_007(), self.A_INP_004(), "001", self.A_INP_003(), self.itemHistId()[0].startDate, self.A_INP_008(), null, "1", "1", null, null, null);
                    let arr1 = new Array;
                    arr1.push(_dto);
                    var dfd2 = $.Deferred();
                    service.addListWorkPlace(arr1)
                        .done(function(mess: any) {
                            var dfd2 = $.Deferred();
                            let _dto = new model.AddWorkplaceDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, null, null, null, null, null, "addhistoryfromlatest", null, null, null, null, null, null);
                            let arr = new Array;
                            arr.push(_dto);
                            service.upDateEndDateWkp(arr)
                                .done(function() {
                                    location.reload();
                                })
                                .fail(function() { })
                            self.start();
                        })
                        .fail(function(error) { })
                    dfd2.resolve();
                    return dfd2.promise();
                }
            }
        }

        deletebtn() {
            var self = this;
            var _dt = self.dataSource();
            var _dtflat = nts.uk.util.flatArray(_dt, 'children');
            debugger;
            var _code = self.singleSelectedCode();
            var current = self.findHira(_code, _dt);
            let deleteobj = new model.DepartmentDeleteDto(current.departmentCode, current.historyId, current.hierarchyCode);
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                var dfd2 = $.Deferred();
                service.deleteDepartment(deleteobj)
                    .done(function() {
                        var _dt = self.dataSource();
                        var _code = self.singleSelectedCode();
                        var current = self.findHira(_code, _dt);
                        var parrent = self.findParent(_code, _dt);
                        if (parrent) {
                            var index = parrent.children.indexOf(current);
                            //Parent hirachy code
                            var phc = parrent.hierarchyCode;
                            var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));

                            // Thay đổi hirachiCode của các object bên dưới
                            var changeIndexChild = _.filter(parrent['children'], function(item) {
                                return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                            });
                            debugger;
                            for (var i in changeIndexChild) {
                                var item1 = changeIndexChild[i];
                                var itemAddH = (parseInt(item1.hierarchyCode.substr(item1.hierarchyCode.length - 3, 3)) - 1) + "";
                                while ((itemAddH + "").length < 3)
                                    itemAddH = "0" + itemAddH;
                                item1.hierarchyCode = phc + itemAddH;
                                item1.editIndex = true;
                                if (item1.children.length > 0) {
                                    self.updateHierachy2(item1, phc + itemAddH);
                                }
                            }
                            var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                            if (editObjs.length > 0) {
                                let currentHis = self.itemHist();
                                for (var k = 0; k < editObjs.length; k++) {
                                    editObjs[k].startDate = currentHis.startDate;
                                    editObjs[k].endDate = currentHis.endDate;
                                    editObjs[k].memo = self.A_INP_008();
                                }
                            }
                            self.listDtothaydoi(editObjs);
                        } else {
                            var index = _dt.indexOf(current);
                            //Parent hirachy code
                            var phc = current.hierarchyCode;
                            var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));

                            // Thay đổi hirachiCode của các object bên dưới
                            var changeIndexChild2 = _.filter(_dt, function(item) {
                                return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                            });

                            for (var i in changeIndexChild2) {
                                var item2 = changeIndexChild2[i];
                                var itemAddH = (parseInt(item2.hierarchyCode.substr(item2.hierarchyCode.length - 3, 3)) - 1) + "";
                                while ((itemAddH + "").length < 3)
                                    itemAddH = "0" + itemAddH;
                                item2.hierarchyCode = itemAddH;
                                item2.editIndex = true;
                                if (item2.children.length > 0) {
                                    self.updateHierachy2(item2, itemAddH);
                                }
                            }
                            var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                            if (editObjs.length > 0) {
                                let currentHis = self.itemHist();
                                for (var k = 0; k < editObjs.length; k++) {
                                    editObjs[k].startDate = currentHis.startDate;
                                    editObjs[k].endDate = currentHis.endDate;
                                    editObjs[k].memo = self.A_INP_008();
                                }
                            }
                            self.listDtothaydoi(editObjs);
                        }
                        let data = self.listDtothaydoi();
                        if (data != null) {
                            service.upDateListDepartment(data)
                                .done(function(mess) {
                                    location.reload();
                                }).fail(function(error) {
                                    if (error.message == "ER005") {
                                        alert("ko ton tai");
                                    }
                                })
                            dfd.resolve();
                            return dfd.promise();
                        }
                    })
                    .fail(function() { })
                dfd2.resolve();
                return dfd2.promise();
            }).ifNo(function() { });
        }

        findHira(value: string, sources) {
            let self = this;

            if (!sources || !sources.length) {
                return undefined;
            }

            sources = nts.uk.util.flatArray(sources, 'children');
            self.lengthTreeCurrent(sources.length + 1);
            return _.find(sources, function(item: model.Dto) { return item.departmentCode == value; });
        }

        findParent(value: string, sources) {
            let self = this, node;

            if (!sources || !sources.length) {
                return undefined;
            }

            sources = nts.uk.util.flatArray(sources, 'children');
            self.lengthTreeCurrent(sources.length + 1);
            console.log(self.lengthTreeCurrent());
            return _.find(sources, function(item: model.Dto) { return _.find(item.children, function(child) { return child.departmentCode == value; }); });
        }

        findHist_Dep(items: Array<viewmodel.model.HistoryDto>, newValue: string): viewmodel.model.HistoryDto {
            let self = this;
            let node: viewmodel.model.HistoryDto;
            _.find(items, function(obj: viewmodel.model.HistoryDto) {
                if (!node) {
                    if (obj.startDate == newValue) {
                        node = obj;
                        self.itemHist(node);
                        console.log("===" + self.currentItem_treegrid());
                    }
                }
            });
            return node;
        };

        //find history need to show position
        findHist(value: string): viewmodel.model.HistoryDto {
            let self = this;
            var itemModel = null;
            _.find(self.itemHistId, function(obj: viewmodel.model.HistoryDto) {
                if (obj.startDate == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }

        checkInput(): boolean {
            var self = this;
            if (self.A_INP_002() == "") {
                alert("コードが入力されていません。");
                $("#A_INP_002").focus();
                return false;
            } else if (self.A_INP_003() == "") {
                alert("名称 が入力されていません。");
                $("#A_INP_003").focus();
                return false;
            }
            return true
        }

        openCDialog() {
            var self = this;
            if (self.checknull() == "landau") {
                nts.uk.ui.windows.setShared('datanull', "datanull");
                nts.uk.ui.windows.sub.modal('/view/cmm/011/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function(): any {
                    let itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                    if (itemAddHistory !== undefined) {
                        let itemadd = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                        self.itemaddHist = itemadd;
                        self.itemHistId().push(self.itemaddHist);
                        self.selectedCodes_His(self.itemaddHist.startDate);
                        self.A_INP_002_enable(true);
                        self.A_INP_002("");
                        self.A_INP_003("");
                        self.A_INP_004("");
                        self.A_INP_007("");
                        $("#A_INP_002").focus();
                        if (itemAddHistory.memo !== null) {
                            self.A_INP_008(itemAddHistory.memo);
                        }
                    }
                });
            } else {
                if (self.selectedCodes_His() == null)
                    return false;
                console.log(self.selectedCodes_His() + "=== test== " + self.historyId());
                nts.uk.ui.windows.setShared('datanull', "notnull");
                nts.uk.ui.windows.setShared('startDateOfHis', self.itemHistId()[0].startDate);
                nts.uk.ui.windows.sub.modal('/view/cmm/009/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function(): any {
                    let itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                    if (itemAddHistory.checked == true) {
                        let add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                        let arr = self.itemHistId();
                        arr.unshift(add);
                        //self.itemHistId.unshift(add);
                        let startDate = new Date(itemAddHistory.startYearMonth);
                        startDate.setDate(startDate.getDate() - 1);
                        let strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                        arr[1].endDate = strStartDate;
                        self.itemHistId(arr);
                        self.selectedCodes_His(itemAddHistory.startYearMonth);
                        self.A_INP_008(itemAddHistory.memo);
                        console.log(self.selectedCodes_His());
                        var _dt = self.dataSource();
                        let hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var _dt2 = _.forEach(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) {
                            item.historyId = null;
                            item.startDate = hisdto.startDate;
                            item.endDate = hisdto.endDate;
                            item.workPlaceCode = item.departmentCode;
                        });

                        self.checkAddHist1("AddhistoryFromLatest");
                        self.dataSource2(_dt2);
                        debugger;
                    } else {
                        let add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                        console.log(add);
                        let arr = self.itemHistId();
                        arr.unshift(add);
                        //self.itemHistId.unshift(add);
                        let startDate = new Date(itemAddHistory.startYearMonth);
                        startDate.setDate(startDate.getDate() - 1);
                        let strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                        arr[1].endDate = strStartDate;
                        self.itemHistId(arr);
                        self.A_INP_008(itemAddHistory.memo);
                        self.selectedCodes_His(self.itemHistId()[0].startDate);
                        self.dataSource(null);
                        self.A_INP_002("");
                        self.A_INP_002_enable(true);
                        self.A_INP_003("");
                        self.A_INP_004("");
                        self.A_INP_007("");
                        $("#A_INP_002").focus();
                        self.checkAddHist1("AddhistoryFromBeggin");
                    }
                });
            }
        }

        openDDialog() {
            var self = this;
            if (self.selectedCodes_His() == null)
                return false;
            let hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
            let index = _.findIndex(self.itemHistId(), function(obj) { return obj == hisdto; });
            hisdto.index = index;
            console.log(hisdto);
            console.log(index);
            nts.uk.ui.windows.setShared('itemHist', hisdto);
            nts.uk.ui.windows.sub.modal('/view/cmm/011/d/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function(): any {
                let newstartDate = nts.uk.ui.windows.getShared('newstartDate');
                let isRadiocheck = nts.uk.ui.windows.getShared('isradio');
                debugger;
                if (isRadiocheck == "1") {
                    // delete thang his dau tien + delete memo
                    var dfd = $.Deferred();
                    service.deleteHistory(self.itemHistId()[0].historyId)
                        .done(function() {
                            console.log("done");
                            // cap nhat endate thang sau --> 9999/12/31
                            var dfd = $.Deferred();
                            service.updateEndDateByHistoryId(self.itemHistId()[1].historyId)
                                .done(function() {
                                    location.reload();
                                })
                                .fail(function() {

                                })
                            dfd.resolve();
                            return dfd.promise();
                        })
                        .fail(function() {

                        })
                    dfd.resolve();
                    return dfd.promise();

                } else if (isRadiocheck == "2") {
                    let hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    let indexItemHist = _.findIndex(self.itemHistId(), function(obj) { return obj == hisdto; });
                    let his2 = "";
                    let endDate = "";
                    let newEndDateRep = "";
                    if (indexItemHist == self.itemHistId().length - 1) {
                        his2 = null;
                    } else {
                        his2 = self.itemHistId()[indexItemHist + 1].historyId;
                        let newEndDate = new Date(newstartDate);
                        newEndDate.setDate(newEndDate.getDate() - 1);
                        newEndDateRep = newEndDate.getFullYear() + '/' + (newEndDate.getMonth() + 1) + '/' + newEndDate.getDate();
                    }
                    let obj = new model.updateDateMY(hisdto.historyId, his2, newstartDate, newEndDateRep);
                    var dfd = $.Deferred();
                    service.upDateStartDateandEndDate(obj)
                        .done(function() {
                            location.reload();
                        })
                        .fail(function() { })
                    dfd.resolve();
                    return dfd.promise();
                }
            });
        }
        insertItemUp() {
            var self = this;
            if (self.lengthTreeCurrent() < 889) {
                if (self.numberItemNew() == 0) {
                    var _dt = self.dataSource();
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var i = current.hierarchyCode.substr(current.hierarchyCode.length - 3, current.hierarchyCode.length);
                    var hierachyItemadd = (parseInt(i)) + "";
                    while ((hierachyItemadd + "").length < 3)
                        hierachyItemadd = "0" + hierachyItemadd;
                    var parrent = self.findParent(_code, _dt);
                    var newObj = new model.Dto('', "999", "", "",
                        "", "", hierachyItemadd,
                        "情報を登録してください",
                        current.startDate,
                        []);
                    if (parrent) {
                        var index = parrent.children.indexOf(current);
                        //Parent hirachy code
                        var phc = parrent.hierarchyCode;
                        var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));

                        // Thay đổi hirachiCode của các object bên dưới
                        var changeIndexChild = _.filter(parrent['children'], function(item) {
                            return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) >= chc;
                        });
                        for (var i in changeIndexChild) {
                            var item1 = changeIndexChild[i];
                            var itemAddH = (parseInt(item1.hierarchyCode.substr(item1.hierarchyCode.length - 3, 3)) + 1) + "";
                            while ((itemAddH + "").length < 3)
                                itemAddH = "0" + itemAddH;
                            item1.hierarchyCode = phc + itemAddH;
                            item1.editIndex = true;
                            if (item1.children.length > 0) {
                                self.updateHierachy2(item1, phc + itemAddH);
                            }
                        }
                        newObj.hierarchyCode = phc + hierachyItemadd;
                        parrent.children.splice(index, 0, newObj);
                        var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                        if (editObjs.length > 0) {
                            let currentHis = self.itemHist();
                            for (var k = 0; k < editObjs.length; k++) {
                                editObjs[k].startDate = currentHis.startDate;
                                editObjs[k].endDate = currentHis.endDate;
                                editObjs[k].memo = self.A_INP_008();
                                editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                            }
                        }

                        self.dtoAdd(newObj);
                        self.listDtothaydoi(editObjs);
                    } else {
                        var index = _dt.indexOf(current);
                        //Parent hirachy code
                        var phc = current.hierarchyCode;
                        var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                        // Thay đổi hirachiCode của các object bên dưới
                        var changeIndexChild2 = _.filter(_dt, function(item) {
                            return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) >= chc;
                        });

                        for (var i in changeIndexChild2) {
                            var item2 = changeIndexChild2[i];
                            var itemAddH = (parseInt(item2.hierarchyCode.substr(item2.hierarchyCode.length - 3, 3)) + 1) + "";
                            while ((itemAddH + "").length < 3)
                                itemAddH = "0" + itemAddH;
                            item2.hierarchyCode = itemAddH;
                            item2.editIndex = true;
                            if (item2.children.length > 0) {
                                self.updateHierachy2(item2, itemAddH);
                            }
                        }
                        newObj.hierarchyCode = hierachyItemadd;
                        var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                        if (editObjs.length > 0) {
                            let currentHis = self.itemHist();
                            for (var k = 0; k < editObjs.length; k++) {
                                editObjs[k].startDate = currentHis.startDate;
                                editObjs[k].endDate = currentHis.endDate;
                                editObjs[k].memo = self.A_INP_008();
                                editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                            }
                        }
                        self.dtoAdd(newObj);
                        self.listDtothaydoi(editObjs);
                        _dt.splice(index, 0, newObj);
                    }
                    self.dataSource(_dt);
                    self.numberItemNew(1);
                    self.singleSelectedCode(newObj.departmentCode);
                    self.resetInput();
                }
            } else {
                alert("more than 889 item");
            }

        }

        resetInput() {
            var self = this;
            self.A_INP_002("");
            self.A_INP_002_enable(true);
            self.A_INP_003("");
            self.A_INP_004("");
            self.A_INP_007("");
            $("#A_INP_002").focus();
        }

        updateHierachy2(item: any, hierarchyCode: any) {
            var self = this;
            for (var i in item.children) {
                var con = item.children[i];
                var hierachy = con.hierarchyCode.substr(0, hierarchyCode.length);
                var ii = con.hierarchyCode.replace(hierachy, hierarchyCode);
                con.hierarchyCode = ii;
                con.editIndex = true;
                if (con.children.length > 0) {
                    self.updateHierachy2(con, hierarchyCode);
                }
            }
        }

        insertItemDown() {
            var self = this;

            if (self.lengthTreeCurrent() < 889) {
                if (self.numberItemNew() == 0) {
                    var _dt = self.dataSource();
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var i = current.hierarchyCode.substr(current.hierarchyCode.length - 3, current.hierarchyCode.length);
                    var hierachyItemadd = (parseInt(i) + 1) + "";
                    while ((hierachyItemadd + "").length < 3)
                        hierachyItemadd = "0" + hierachyItemadd;
                    var parrent = self.findParent(_code, _dt);
                    var newObj = new model.Dto('', "999", "", "",
                        "", "", hierachyItemadd,
                        "情報を登録してください",
                        current.startDate,
                        []);
                    if (parrent) {
                        var index = parrent.children.indexOf(current);
                        //Parent hirachy code
                        var phc = parrent.hierarchyCode;
                        var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                        // Thay đổi hirachiCode của các object bên dưới
                        var changeIndexChild = _.filter(parrent['children'], function(item) {
                            return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                        });
                        for (var i in changeIndexChild) {
                            var item = changeIndexChild[i];
                            var itemAddH = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) + 1) + "";
                            while ((itemAddH + "").length < 3)
                                itemAddH = "0" + itemAddH;
                            item.hierarchyCode = phc + itemAddH;
                            item.editIndex = true;
                            if (item.children.length > 0) {
                                self.updateHierachy2(item, phc + itemAddH);
                            }
                        }
                        newObj.hierarchyCode = phc + hierachyItemadd;
                        parrent.children.splice(index + 1, 0, newObj);
                        var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                        if (editObjs.length > 0) {
                            let currentHis = self.itemHist();
                            for (var k = 0; k < editObjs.length; k++) {
                                editObjs[k].startDate = currentHis.startDate;
                                editObjs[k].endDate = currentHis.endDate;
                                editObjs[k].memo = self.A_INP_008();
                                editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                            }
                        }
                        self.dtoAdd(newObj);
                        if (editObjs.length > 0) {
                            self.listDtothaydoi(editObjs);
                        } else {
                            self.listDtothaydoi();
                        }
                    }
                    else {
                        var index = _dt.indexOf(current);
                        //Parent hirachy code
                        var phc = current.hierarchyCode;
                        var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                        // Thay đổi hirachiCode của các object bên dưới
                        var changeIndexChild2 = _.filter(_dt, function(item) {
                            return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                        });
                        for (var i in changeIndexChild2) {
                            var item = changeIndexChild2[i];
                            var itemAddH = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) + 1) + "";
                            while ((itemAddH + "").length < 3)
                                itemAddH = "0" + itemAddH;
                            item.hierarchyCode = itemAddH;
                            item.editIndex = true;
                            if (item.children.length > 0) {
                                self.updateHierachy2(item, itemAddH);
                            }
                        }
                        newObj.hierarchyCode = hierachyItemadd;
                        var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function(item) { return item.editIndex; });
                        if (editObjs.length > 0) {
                            let currentHis = self.itemHist();
                            for (var k = 0; k < editObjs.length; k++) {
                                editObjs[k].startDate = currentHis.startDate;
                                editObjs[k].endDate = currentHis.endDate;
                                editObjs[k].memo = self.A_INP_008();
                                editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                            }
                        }
                        self.dtoAdd(newObj);
                        if (editObjs.length > 0) {
                            self.listDtothaydoi(editObjs);
                        } else {
                            self.listDtothaydoi();
                        }
                        _dt.splice(index + 1, 0, newObj);
                    }
                    self.dataSource(_dt);
                    self.numberItemNew(1);
                    self.singleSelectedCode(newObj.departmentCode);
                    self.resetInput();
                }
            } else {
                alert("more than 889 item");
            }

        }

        insertItemEnd() {
            var self = this;
            if (self.lengthTreeCurrent() < 889) {
                if (self.numberItemNew() == 0) {
                    var _dt = self.dataSource();
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    if (current.hierarchyCode.length < 30) {
                        var hierachy_current = current.hierarchyCode;
                        var length = current.children.length + 1;
                        var hierachyItemadd = length + "";
                        while ((hierachyItemadd + "").length < 3)
                            hierachyItemadd = "0" + hierachyItemadd;
                        var newObj = new model.Dto('', new Date().getTime() + "", "", "",
                            "", "", hierachy_current + hierachyItemadd,
                            "情報を登録してください",
                            current.startDate,
                            []);
                        current.children.push(newObj);
                        let currentHis = self.itemHist();
                        newObj.startDate = currentHis.startDate;
                        newObj.endDate = currentHis.endDate;
                        newObj.memo = self.A_INP_008();
                        //newObj.workPlaceCode = editObjs[k].departmentCode;
                        self.dtoAdd(newObj);
                        self.listDtothaydoi();
                        self.dataSource(_dt);
                        self.numberItemNew(1);
                        self.singleSelectedCode(newObj.departmentCode);
                        self.A_INP_002("");
                        self.A_INP_003("");
                        $("#A_INP_002").focus();
                    } else {
                        alert("hierarchy item current = 10 ,not push item child to tree");
                    }
                }
                console.log(self.dataSource());
            } else {
                alert("more than 889 item");
            }
        }


        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            // get init data workplace
            service.getAllWorkplace().done(function(workplaceQueryResult: viewmodel.model.WorkPlaceQueryResult) {
                var workplaceQueryResult = workplaceQueryResult;
                console.log(workplaceQueryResult);
                if (workplaceQueryResult.histories == null) {
                    nts.uk.ui.windows.setShared('datanull', "datanull");
                    self.checknull("landau");
                    self.openCDialog();
                } else {
                    if (workplaceQueryResult.workPlaces.length > 0) {
                        self.dataSource(workplaceQueryResult.workPlaces);
                    }
                    if (workplaceQueryResult.memo) {
                        self.A_INP_008(workplaceQueryResult.memo.memo);
                    }
                    if (workplaceQueryResult.histories.length > 0) {
                        self.itemHistId(workplaceQueryResult.histories);
                        if (self.dataSource().length > 0) {
                            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                            self.singleSelectedCode(workplaceQueryResult.workPlaces[0].departmentCode);
                            self.selectedCodes_His(self.itemHistId()[0].startDate);
                            self.numberItemNew(0);
                        }
                    }
                }
                dfd.resolve();
            }).fail(function(error) {
                console.log(error);
            })
            dfd.resolve();
            return dfd.promise();
        }
    }
    /**
          * Model namespace.
       */
    export module model {

        export class DepartmentQueryResult {
            histories: Array<HistoryDto>;
            departments: Array<Dto>;
            memo: MemoDto;
        }

        export class WorkPlaceQueryResult {
            histories: Array<HistoryDto>;
            workPlaces: Array<DtoWKP>;
            memo: MemoDto;
        }

        export class MemoDto {
            historyId: string;
            memo: string;
        }

        export class HistoryDto {
            endDate: string;
            historyId: string;
            startDate: string;
            displayDate: string;
            constructor(startDate: string, endDate: string, historyId: string) {
                var self = this;
                self.endDate = endDate;
                self.startDate = startDate;
                self.historyId = historyId;
                self.displayDate = startDate + " ~ " + endDate;
            }
        }
        //    Department     
        export class DtoWKP {
            companyCode: string;
            departmentCode: string;
            historyId: string;
            endDate: string;
            externalCode: string;
            fullName: string;
            hierarchyCode: string;
            name: string;
            parentChildAttribute1: string;
            parentChildAttribute2: string;
            parentWorkCode1: string;
            parentWorkCode2: string;
            shortName: string;
            startDate: string;
            children: Array<Dto>;
            constructor(companyCode: string,
                departmentCode: string,
                historyId: string,
                endDate: string,
                externalCode: string,
                fullName: string,
                hierarchyCode: string,
                name: string,
                parentChildAttribute1: string,
                parentChildAttribute2: string,
                parentWorkCode1: string,
                parentWorkCode2: string,
                shortName: string,
                startDate: string,
                children: Array<Dto>
            ) {
                var self = this;
                self.companyCode = companyCode;
                self.departmentCode = departmentCode;
                self.historyId = historyId;
                self.endDate = endDate;
                self.externalCode = externalCode;
                self.fullName = fullName;
                self.hierarchyCode = hierarchyCode;
                self.name = name;
                self.parentChildAttribute1 = parentChildAttribute1;
                self.parentChildAttribute2 = parentChildAttribute2;
                self.parentWorkCode1 = parentWorkCode1;
                self.parentWorkCode2 = parentWorkCode2;
                self.shortName = shortName;
                self.startDate = startDate;
                self.children = children;
            }
        }

        // Department
        export class Dto {
            companyCode: string;
            departmentCode: string;
            historyId: string;
            endDate: string;
            externalCode: string;
            fullName: string;
            hierarchyCode: string;
            name: string;
            startDate: string;
            children: Array<Dto>;
            constructor(companyCode: string,
                departmentCode: string,
                historyId: string,
                endDate: string,
                externalCode: string,
                fullName: string,
                hierarchyCode: string,
                name: string,
                startDate: string,
                children: Array<Dto>
            ) {
                var self = this;
                self.companyCode = companyCode;
                self.departmentCode = departmentCode;
                self.historyId = historyId;
                self.endDate = endDate;
                self.externalCode = externalCode;
                self.fullName = fullName;
                self.hierarchyCode = hierarchyCode;
                self.name = name;
                self.startDate = startDate;
                self.children = children;
            }
        }
        export class AddWorkplaceDto {
            workPlaceCode: string;
            historyId: string;
            endDate: string;
            externalCode: string;
            fullName: string;
            hierarchyCode: string;
            name: string;
            startDate: string;
            memo: string;
            shortName: string;
            parentChildAttribute1: string;
            parentChildAttribute2: string;
            parentWorkCode1: string;
            parentWorkCode2: string;
            children: Array<AddWorkplaceDto>;
            constructor(
                workPlaceCode: string,
                historyId: string,
                endDate: string,
                externalCode: string,
                fullName: string,
                hierarchyCode: string,
                name: string,
                startDate: string,
                memo: string,
                shortName: string,
                parentChildAttribute1: string,
                parentChildAttribute2: string,
                parentWorkCode1: string,
                parentWorkCode2: string,
                children: Array<AddWorkplaceDto>) {
                var self = this;
                self.memo = memo;
                self.workPlaceCode = workPlaceCode;
                self.historyId = historyId;
                self.endDate = endDate;
                self.externalCode = externalCode;
                self.fullName = fullName;
                self.hierarchyCode = hierarchyCode;
                self.name = name;
                self.shortName = shortName;
                self.startDate = startDate;
                self.parentChildAttribute1 = parentChildAttribute1;
                self.parentChildAttribute2 = parentChildAttribute2;
                self.parentWorkCode1 = parentWorkCode1;
                self.parentWorkCode2 = parentWorkCode2;
                self.children = children;
            }
        }

        export class AddDepartmentDto {
            departmentCode: string;
            historyId: string;
            endDate: string;
            externalCode: string;
            fullName: string;
            hierarchyCode: string;
            name: string;
            startDate: string;
            memo: string;
            children: Array<Dto>;
            constructor(
                departmentCode: string,
                historyId: string,
                endDate: string,
                externalCode: string,
                fullName: string,
                hierarchyCode: string,
                name: string,
                startDate: string,
                memo: string,
                children: Array<Dto>) {
                var self = this;
                self.memo = memo;
                self.departmentCode = departmentCode;
                self.historyId = historyId;
                self.endDate = endDate;
                self.externalCode = externalCode;
                self.fullName = fullName;
                self.hierarchyCode = hierarchyCode;
                self.name = name;
                self.startDate = startDate;
                self.children = children;
            }
        }

        export class DepartmentDeleteDto {
            departmentCode: string;
            hierarchyCode: string;
            historyId: string;
            constructor(departmentCode: string, historyId: string, hierarchyCode: string) {
                var self = this;
                self.departmentCode = departmentCode;
                self.hierarchyCode = hierarchyCode;
                self.historyId = historyId;
            }
        }

        export class updateDateMY {
            historyId1: string;
            historyId2: string;
            newStartDate: string;
            newEndDate: string;
            constructor(historyId1: string, historyId2: string, newStartDate: string, newEndDate: string) {
                var self = this;
                self.historyId1 = historyId1;
                self.historyId2 = historyId2;
                self.newStartDate = newStartDate;
                self.newEndDate = newEndDate;
            }
        }

    }

}
