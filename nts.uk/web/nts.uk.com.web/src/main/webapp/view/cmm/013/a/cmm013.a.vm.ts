module cmm013.a.viewmodel {

    export class ScreenModel {




        radiobox: KnockoutObservable<RadioBox>;

        inp_002_code: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003_name: KnockoutObservable<string>;
        inp_004: KnockoutObservable<TextEditor_3>;
        inp_005_memo: KnockoutObservable<string>;

        swb_001: KnockoutObservable<SwitchButton>;
        swb_002: KnockoutObservable<SwitchButton>;
        swb_003: KnockoutObservable<SwitchButton>;
        swb_004: KnockoutObservable<SwitchButton>;

        label_003: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;


        start_Date: KnockoutObservable<Date>;
        end_Date: KnockoutObservable<Date>;
        listbox: KnockoutObservableArray<model.ListHistoryDto>;
        checkUp: KnockoutObservable<string>;
        checkDel: KnockoutObservable<string>;
        checkUpOrDel: KnockoutObservable<string>;
        check: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<any>;
        itemHist: KnockoutObservable<model.ListHistoryDto>;
        index_selected: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;
        updatedata: KnockoutObservable<any>;
        adddata: any;

        currentCode: KnockoutObservable<string>;
        dataSource: KnockoutObservableArray<model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        srtDateLast: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<model.ListPositionDto>;
        currentItem2: KnockoutObservable<model.ListPositionDto>;
        index_of_itemDelete: any;

        startDateLast: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;

        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;

        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        createdMode: KnockoutObservable<boolean>;



        constructor() {
            var self = this;



            self.label_003 = ko.observable(new Labels());
            self.label_005 = ko.observable(new Labels());

            self.radiobox = ko.observable(new RadioBox());
            self.adddata = ko.observable(null);
            self.updatedata = ko.observable(null);
            self.inp_002_code = ko.observable(null);
            self.inp_002_enable = ko.observable(false);
            self.inp_003_name = ko.observable(null);
            self.inp_004 = ko.observable(new TextEditor_3());
            self.inp_005_memo = ko.observable(null);

            self.swb_001 = ko.observable(new SwitchButton);
            self.swb_002 = ko.observable(new SwitchButton);
            self.swb_003 = ko.observable(new SwitchButton);
            self.swb_004 = ko.observable(new SwitchButton);

            self.length = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.startDateLast = ko.observable(null);
            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable(null);
            self.checkDelete = ko.observable(null);
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');
            self.checkUpOrDel = ko.observable(null);
            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.checkUp = ko.observable(null);
            self.checkDel = ko.observable(null);
            self.startDateLast = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable(null);
            self.check = ko.observable('0');
            self.startDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.jobHistory = ko.observable(null);
            self.srtDateLast = ko.observable(null);
            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.currentItem2 = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(null);
            self.start_Date = ko.observable(null);
            self.end_Date = ko.observable(null);
            //self.multilineeditor = ko.observable(null);           
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'jobCode', width: 80 },
                { headerText: '名称', key: 'jobName', width: 100 }
            ]);
            self.itemList = ko.observableArray([
                new BoxModel(0, '全員参照可能'),
                new BoxModel(1, '全員参照不可'),
                new BoxModel(2, 'ロール毎に設定')
            ]);

            self.selectedId = ko.observable(0);
            self.enable = ko.observable(true);
            self.createdMode = ko.observable(true);
            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findPosition(codeChanged));
                if (self.currentItem() != null) {
                    self.createdMode(false);
                    self.inp_002_code(self.currentItem().jobCode);
                    self.inp_003_name(self.currentItem().jobName);
                    self.inp_005_memo(self.currentItem().memo);
                } else {
                    self.createdMode(true);
                }
            }));
        }

        checkPage(): boolean {
            var self = this;
            if (self.inp_002_code() == '' || self.inp_003_name() == '') {
                alert("が入力されていません。");
                return false;
            } else {
                return true;
            }
        }

        checkData(): boolean {
            var self = this;
            if (self.check() == '1') {
                var retVal = confirm("Changed.Register OK?");
                if (retVal == true) {
                    self.addHist();
                    return true;
                } else {
                    self.startDateAddNew(null);
                    self.check('0');
                    return false;
                }
            }
        }

        initRegisterPosition() {
            var self = this;
            self.inp_002_enable(true);
            self.createdMode(true);
            self.inp_002_code("");
            self.inp_003_name("");
            self.inp_005_memo("");
            self.currentCode(null);
            $("#inp_002").focus();
        }

        findHist(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.listbox(), function(obj: viewmodel.model.historyDto) {
                if (obj.historyId == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }


        findPosition(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.dataSource(), function(obj: viewmodel.model.ListPositionDto) {
                if (obj.jobCode == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }

        //add
        addPosition() {
            var self = this;
            var dfd = $.Deferred();
            if (self.checkPage()) {
                // var selectHistory = self.selectedCode();

                if (self.dataSource().length === 0) {
                    let position = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                    position.historyId = self.selectedCode();
                    service.addPosition(position).done(function() {

                        self.getPosition_first();

                    }).fail(function(res) {
                        dfd.reject(res);
                    })
                }
                if (!self.createdMode()) {
                    let currentItem = self.currentItem();
                    currentItem.jobCode = self.inp_002_code();
                    currentItem.jobName = self.inp_003_name();
                    currentItem.memo = self.inp_005_memo();
                    service.updatePosition(currentItem).done(function() {
                        self.updatedata(currentItem);
                        self.getPositionList_afterUpdate();
                    }).fail(function(res) {
                        alert("更新対象のデータが存在しません。");
                        dfd.reject(res);
                    });
                }
                else {
                    let position_new = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                    position_new.historyId = self.selectedCode();
                    service.addPosition(position_new).done(function() {
                        self.adddata(position_new);
                        self.currentCode(self.adddata().jobCode);
                        self.getPositionList_afterAdd();
                    }).fail(function(res) {
                        alert("入力した は既に存在しています。");
                        dfd.reject(res);
                    })
                }

            }
        }
        //delete

        deletePosition() {

            var self = this;
            var dfd = $.Deferred<any>();
            var item = new model.DeletePositionCommand(self.currentItem().jobCode, self.currentItem().historyId);
            self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
            service.deletePosition(item).done(function(res) {
                self.getPositionList_aftefDelete();
            }).fail(function(res) {
                dfd.reject(res);
            })
        }

        //        updateHist() {
        //            var self = this;
        //            if (self.check() != '0') {
        //                var dfd = $.Deferred<any>();
        //                self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));
        //                self.checkUpOrDel(nts.uk.ui.windows.getShared('check_d'));
        //                if (self.checkUpOrDel() == '1') {
        //                    var listHist = new model.ListHistoryDto('', self.startDateUpdate(), self.endDateUpdate(), self.historyIdUpdate());
        //                    self.checkDelete('1');
        //                } else
        //                    if (self.checkUpOrDel() == '2') {
        //                        var jobHist = new model.ListHistoryDto('', self.startDateUpdateNew(), self.endDateUpdate(), self.historyIdUpdate());
        //                        if (self.srtDateLast() == self.startDateUpdate()) {
        //                            self.checkUp('2');
        //                        } else {
        //                            self.checkUp('1');
        //                        }
        //                    }
        //                var update = new model.Update(jobHist, self.checkUp(), self.checkDel());
        //                service.updateHist(update).done(function() {
        //                    alert('update OK');
        //                    nts.uk.ui.windows.setShared('startUpdateNew', '', true)
        //                    self.getAllHist();
        //                }).fail(function(res) {
        //                    dfd.reject(res);
        //                })
        //            }
        //        }
        getPositionList_aftefDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);

                if (self.dataSource().length > 0) {
                    if (self.index_of_itemDelete === self.dataSource().length) {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].jobCode)
                        self.inp_002_code(self.dataSource()[self.index_of_itemDelete - 1].jobCode);
                        self.inp_003_name(self.dataSource()[self.index_of_itemDelete - 1].jobName);
                        self.inp_005_memo(self.dataSource()[self.index_of_itemDelete - 1].memo);
                    } else {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete].jobCode)
                        self.inp_002_code(self.dataSource()[self.index_of_itemDelete].jobCode);
                        self.inp_003_name(self.dataSource()[self.index_of_itemDelete].jobName);
                        self.inp_005_memo(self.dataSource()[self.index_of_itemDelete].memo);
                    }
                } else {
                    self.initRegisterPosition();
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

        getPositionList(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.inp_002_code(self.dataSource()[0].jobCode);
                self.inp_003_name(self.dataSource()[0].jobName);
                self.inp_005_memo(self.dataSource()[0].memo);
                if (self.dataSource().length > 1) {
                    self.currentCode(self.dataSource()[0].jobCode);
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            self.selectedCode.subscribe((function(codeChanged) {
                self.itemHist(self.findHist(codeChanged));

                if (self.itemHist() != null) {
                    self.index_selected(self.itemHist().historyId);
                    self.startDateUpdate(self.itemHist().startDateString);
                    self.endDateUpdate(self.itemHist().endDateString);
                    self.historyIdUpdate(self.itemHist().historyId);

                    var dfd = $.Deferred();
                    service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                        self.dataSource(position_arr);
                        if (self.dataSource().length > 0) {
                            self.currentCode(self.dataSource()[0].jobCode);
                            self.inp_002_code(self.dataSource()[0].jobCode);
                            self.inp_003_name(self.dataSource()[0].jobName);
                            self.inp_005_memo(self.dataSource()[0].memo);
                        }
                        dfd.resolve();
                    }).fail(function(error) {
                        alert(error.message);
                    })
                    dfd.resolve();
                    return dfd.promise();
                }
            }));

            service.getAllHistory().done(function(history_arr: Array<any>) {

                if (history_arr === undefined || history_arr.length === 0) {
                    self.openCDialog();
                } else {
//                    self.listbox(_.map(history_arr, function(history) {
//                        return new model.ListHistoryDto(history.companyCode, history.startDate, history.endDate, history.historyId);
//                    }));
                    self.listbox(_.map(history_arr, function(history) {
                        return new model.ListHistoryDto(history.companyCode, history.startDate, history.endDate, history.historyId);
                    }));
                    if (history_arr.length > 0) {
                        self.selectedCode(history_arr[0].historyId);
                    }
                    if (self.dataSource().length > 0) {
    
                        self.selectedCode = ko.observable(self.listbox()[0].startDateString);
                    }
                    var historyFirst = _.first(history_arr);
                    var historyLast = _.last(history_arr);
                    self.checkDelete(historyLast.startDate);
                    self.selectedCode(historyFirst.startDate);
                    self.startDateUpdate(historyFirst.startDate);
                    self.endDateUpdate(historyFirst.endDate);
                    self.historyIdUpdate(historyFirst.historyId);
                    self.startDateLast(historyFirst.startDate);
                    self.historyIdLast(historyFirst.historyId);
                    self.srtDateLast(historyLast.startDate);
                    dfd.resolve(history_arr);
                }
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

//        getAllHist(): any {
//            var self = this;
//            var dfd = $.Deferred<any>();
//            self.selectedCode('');
//            self.listbox([]);
//            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
//                self.listbox(_.map(history_arr, function(history) {
//                    return new model.ListHistoryDto(history.companyCode, history.startDateS, history.endDateS, history.historyId);
//                }));
//                if (history_arr === undefined || history_arr.length === 0)
//                    return;
//                self.listbox(history_arr);
//                _.forEach(history_arr, function(strHistory) {
//                    self.listbox.push(strHistory);
//                })
//                var historyFirst = _.first(history_arr);
//                var historyLast = _.last(history_arr);
//                self.checkDelete(historyLast.startDate);
//                self.selectedCode(historyFirst.startDate);
//                self.startDateUpdate(historyFirst.startDate);
//                self.endDateUpdate(historyFirst.endDate);
//                self.historyIdUpdate(historyFirst.historyId);
//                self.startDateLast(historyFirst.startDate);
//                self.historyIdLast(historyFirst.historyId);
//                dfd.resolve(history_arr);
//            })
//            return dfd.promise();
//        }
        
        
        
        getPosition_first(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.currentCode(self.dataSource()[0].historyId);
                let i = self.dataSource().length;
                if (i > 0) {
                    self.inp_002_enable(false);
                    self.inp_002_code(self.dataSource()[0].jobCode);
                    self.inp_003_name(self.dataSource()[0].jobName);
                    self.inp_005_memo(self.dataSource()[0].memo);
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

        getPositionList_afterUpdate() {
            let self = this;
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource([]);
                self.dataSource(position_arr);

                if (position_arr.length) {
                    self.currentCode(self.updatedata().jobCode);
                }

            }).fail(function(error) {
                alert(error.message);
            });
        }

        getPositionList_afterAdd() {
            let self = this;

            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.inp_002_code(self.adddata().jobCode);
                self.inp_002_enable(false);
                self.inp_003_name(self.adddata().jobName);
                self.inp_005_memo(self.adddata().memo);
                if (self.dataSource().length) {
                    self.currentCode(self.adddata().jobCode);
                }

            }).fail(function(error) {
                alert(error.message);
            })
        }
        //add

        addHist() {
            var self = this;
            var dfd = $.Deferred<any>();
            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
            var i = 0;
            var currentHist = null;
            //            for (i = 0; i < self.listbox().length; i++) {
            for (i = 0; i < 1; i++) {
                currentHist = new model.ListHistoryDto(self.listbox()[i].companyCode, self.listbox()[i].startDateString, '9999/12/31', self.listbox()[i].historyId);

                break;
                //                }
            }
            if (!currentHist) {
                return;
            }
            if (!currentHist.historyId) {
                service.addHist(currentHist).done(function() {
                    nts.uk.ui.windows.setShared('startNew', '', true);
                    self.startPage();
                }).fail(function(res) {
                    alert('fail');
                    dfd.reject(res);
                })
            }
            else {
                service.updateHist(currentHist).done(function() {
                    nts.uk.ui.windows.setShared('startNew', '', true);
                    self.startPage();
                }).fail(function(res) {
                    alert('fail');
                    dfd.reject(res);
                })
            }

        }
        openCDialog() {
            let self = this;
            let histLs = [];
            let dfd = $.Deferred();
            //let isCopy = nts.uk.ui.windows.getShared('copy_c');
            nts.uk.ui.windows.setShared('Id_13', self.index_selected());
            nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate());
            nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '画面ID：C', }).onClosed(function() {
                let isCopy = nts.uk.ui.windows.getShared('copy_c');
                self.selectedCode('');
                if (isCopy == 2) {
                    self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                    let add = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '');
                    self.listbox.unshift(add);
                    //                    self.listbox().sort();
                    //                    histLs = _.cloneDeep(self.listbox());
                    //                   
                    //                    let startDate = self.startDateAddNew();
                    //                    startDate.setDate(startDate.getDate() - 1);
                    //                    let strStartDate = new Date(startDate.getFullYear(),startDate.getMonth() + 1, startDate.getDate      //                    self.listbox()[1].endDate = strStartDate;
                    //
                    //                    self.listbox(histLs);

                } else if (isCopy == 1) {
                    self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                    let update = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '');
                    self.listbox.unshift(update);

                }

            });
        }

        getHistList_aftefDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllHistory().done(function(hist_arr: Array<model.ListHistoryDto>) {
                self.listbox(hist_arr);

                if (self.listbox().length > 0) {
                    if (self.index_of_itemDelete === self.listbox().length) {
                        self.start_Date(self.listbox()[self.index_of_itemDelete - 1].startDate);
                        self.end_Date(self.listbox()[self.index_of_itemDelete - 1].endDate);
                    } else {
                        self.start_Date(self.listbox()[self.index_of_itemDelete].startDate);
                        self.end_Date(self.listbox()[self.index_of_itemDelete].endDate);
                    }
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

        deleteHist() {
            var self = this;
            var dfd = $.Deferred<any>();
            var item2 = new model.DeleteHistoryCommand(self.currentItem().historyId);
            self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
            service.deleteHist(item2).done(function(res) {
                self.getHistList_aftefDelete();
            }).fail(function(res) {
                dfd.reject(res);
            })
        }

        openDDialog() {
            var self = this;

            var histLs = [];
            self.startDateUpdateNew(null);
            var checkDelete = 0;
            if (self.startDateUpdate() == self.listbox()[0].startDateString) {
                checkDelete = 1;
            } else {
                checkDelete = 2;
            }
            nts.uk.ui.windows.setShared('delete', checkDelete, true);
            nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate(), true);
            nts.uk.ui.windows.setShared('endUpdate', self.endDateUpdate(), true);
            nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', }).onClosed(function() {
                var checkUpdate = nts.uk.ui.windows.getShared('check_d');
                self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));
                var i = 0;
                for (i = 0; i < self.listbox().length; i++) {
                    if (self.listbox()[i].startDateString == self.startDateUpdate()) {
                        break;
                    }
                }
                if (checkUpdate == '1') {
                    //delete
                    self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));

                    var j = 0;
                    var k = 0;
                    for (j = 0; j < self.listbox().length; j++) {
                        if (j != i) {
                            histLs[k] = self.listbox()[j];
                            k++;
                        }
                    }
                    histLs[i].endDate = self.endDateUpdate();
                    self.listbox = ko.observableArray([]);
                    self.listbox(histLs);
                    self.deleteHist();
                    //self.selectedCode(self.listbox()[0].startDate);
                    console.log(self.listbox());
                } else
                    if (checkUpdate == '2') {
                        //update
                        self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));

                        histLs = self.listbox();
                        var tmp = self.selectedCode();
                        self.selectedCode("");
                        self.listbox([]);
                        var i = 0;
                        for (i = 0; i < histLs.length; i++) {
                            if (histLs[i].startDate == self.startDateUpdate() &&
                                histLs[i].endDate == self.endDateUpdate()) {
                                histLs[i].startDate = self.startDateUpdateNew();
                                break;
                            }
                        }
                        self.listbox(histLs);
                        self.selectedCode(tmp);
                    }
                self.check('1');
            });

        }

    }

    export class Labels {
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }
    class RadioBox {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, '全員参照可能'),
                new BoxModel(2, '全員参照不可'),
                new BoxModel(3, 'ロール毎に設定')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }


    export class TextEditor_3 {
        texteditor: any;
        constructor() {
            var self = this;
            self.texteditor = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "10px",
                    textalign: "center"
                })),
                required: ko.observable(true),
                enable: ko.observable(false),
                readonly: ko.observable(false)
            };
        }
    }

    export class SwitchButton {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '可能' },
                { code: '2', name: '不可' },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }

    export module model {
        export class historyDto {
            startDate: Date;
            endDate: Date;
            historyId: string;
            constructor(startDate: Date, endDate: Date, historyId: string) {
                this.startDate = startDate;
                this.endDate = endDate;
                this.historyId = historyId;
            }
        }

        export class ListHistoryDto {
            companyCode: string;
            startDate: Date;
            startDateString: string;
            endDate: Date;
            endDateString: string;
            historyId: string;

            constructor(companyCode: string, startDate: string, endDate: string, historyId: string) {
                var self = this;
                self.companyCode = companyCode;
                self.startDate = new Date(startDate);
                self.startDateString = startDate;
                self.endDate = new Date(endDate);
                self.endDateString = endDate;
                self.historyId = historyId;
            }
        }
        export class ListPositionDto {
            jobCode: string;
            jobName: string;
            historyId: string;
            memo: string;
            constructor(jobCode: string, jobName: string, memo: string) {
                let self = this;
                self.jobCode = jobCode;
                self.jobName = jobName;

                self.memo = memo;

            }
        }

        export class DeletePositionCommand {
            jobCode: string;
            historyId: string;

            constructor(jobCode: string, historyId: string) {
                this.jobCode = jobCode;
                this.historyId = historyId;
            }

        }

        export class DeleteHistoryCommand {

            historyId: string;

            constructor(historyId: string) {

                this.historyId = historyId;
            }

        }
    }
}
