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
        test: KnockoutObservable<string>;

        listbox: KnockoutObservableArray<model.ListHistoryDto>;


        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<any>;
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<model.ListHistoryDto>;
        index_selected: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;
        updatedata: KnockoutObservable<any>;
        adddata: any;
        itemdata: any;

        currentCode: KnockoutObservable<string>;
        currentYm: KnockoutObservable<string>;
        dataSource: KnockoutObservableArray<model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;


        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<model.ListPositionDto>;
        currentItem2: KnockoutObservable<model.ListPositionDto>;
        index_of_itemDelete: any;

        startDateLast: KnockoutObservable<Date>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<Date>;

        startDateUpdate: KnockoutObservable<Date>;
        endDateUpdate: KnockoutObservable<Date>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<Date>;
        startDatePre: KnockoutObservable<Date>;
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
            self.itemdata = ko.observable(null);
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
            self.isEnable = ko.observable(true);
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');

            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);

            self.startDateLast = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable(null);

            self.startDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);

            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.currentItem2 = ko.observable(null);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(null);
            self.currentYm = ko.observable(null);
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



        initRegisterPosition() {
            var self = this;
            self.inp_002_enable(true);
            self.createdMode(true);
            self.inp_002_code("");
            self.inp_003_name("");
            self.inp_005_memo("");
            self.currentCode(null);
            $("#A_INP_002").focus();


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
                    self.startDateUpdate(self.itemHist().startDate);
                    self.endDateUpdate(self.itemHist().endDate);
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

            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
                self.listbox(history_arr);
                if (history_arr.length > 0) {
                    self.selectedCode(history_arr[0].historyId);
                }
                if (self.dataSource().length > 0) {

                    self.selectedCode = ko.observable(self.listbox()[0].startDate);
                }
                if (history_arr === undefined || history_arr.length === 0) {
                    self.openCDialog();
                } else {
                    self.listbox(history_arr);
                    var historyFirst = _.first(history_arr);
                    var historyLast = _.last(history_arr);
                    self.checkDelete(historyLast.startDate);
                    self.selectedCode(historyFirst.startDate);
                    self.startDateUpdate(historyFirst.startDate);
                    self.endDateUpdate(historyFirst.endDate);
                    self.historyIdUpdate(historyFirst.historyId);
                    self.startDateLast(historyFirst.startDate);
                    self.historyIdLast(historyFirst.historyId);
                    dfd.resolve(history_arr);
                }
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }





        getAllHist(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            self.selectedCode('');
            self.listbox([]);
            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
                if (history_arr === undefined || history_arr.length === 0)
                    return;
                self.listbox(history_arr);
                _.forEach(history_arr, function(strHistory) {
                    self.listbox.push(strHistory);
                })
                var historyFirst = _.first(history_arr);
                var historyLast = _.last(history_arr);
                self.checkDelete(historyLast.startDate);
                self.selectedCode(historyFirst.startDate);
                self.startDateUpdate(historyFirst.startDate);
                self.endDateUpdate(historyFirst.endDate);
                self.historyIdUpdate(historyFirst.historyId);
                self.startDateLast(historyFirst.startDate);
                self.historyIdLast(historyFirst.historyId);
                dfd.resolve(history_arr);
            })
            return dfd.promise();
        }
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
                    let add = new model.ListHistoryDto('', self.startDateAddNew(), new Date('9999-12-31'), '');
                    self.listbox.unshift(add);
                    //                    self.listbox().sort();
                    //                    histLs = _.cloneDeep(self.listbox());
                    //                   
                    //                    let startDate = self.startDateAddNew();
                    //                    startDate.setDate(startDate.getDate() - 1);
                    //                    let strStartDate = new Date(startDate.getFullYear(),startDate.getMonth() + 1, startDate.getDate      //                    self.listbox()[1].endDate = strStartDate;
                    //
                    //                    self.listbox(histLs);
                    
                } else {

                    self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                    let update = new model.ListHistoryDto('', self.startDateUpdate(), new Date('9999-12-31'), '');
                    self.listbox.unshift(update);
                    self.listbox().sort();
                    histLs = _.cloneDeep(self.listbox());
                    self.listbox(histLs);
                }

            });
        }


        openDDialog() {
            var self = this;
            var dfd = $.Deferred();
            let histLs = [];
            self.startDateUpdateNew(null);
            nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate());
            nts.uk.ui.windows.setShared('endUpdate', self.endDateUpdate());
            nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', }).onClosed(function() {


                var checkUpdate = nts.uk.ui.windows.getShared('check_d');
                self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));
                if (checkUpdate == '1') {
                    //delete
                    var i = 0;
                    var currentHist = null;
                    //            for (i = 0; i < self.listbox().length; i++) {
                    for (i = 0; i < 1; i++) {
                        //                if (self.listbox()[i].startDate == self.startDateUpdate() &&
                        //                    self.listbox()[i].endDate == self.endDateUpdate()) {

                        currentHist = new model.ListHistoryDto(self.listbox()[i].companyCode, self.listbox()[i].startDate, new Date('9999-12-31'), self.listbox()[i].historyId);
                        break;
                        //                }
                    }
                    self.listbox([]);
                    self.listbox(histLs);
                    self.selectedCode(self.listbox()[0].startDate);
                } else {
                    //update
                    histLs = self.listbox();
                    var slc = self.selectedCode();
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
                    self.selectedCode(slc);
                }
                dfd.resolve(self.listbox());
            });
            dfd.promise();
        }

        addHist() {
            var self = this;
            var dfd = $.Deferred<any>();
            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
            var i = 0;
            var currentHist = null;
            //            for (i = 0; i < self.listbox().length; i++) {
            for (i = 0; i < 1; i++) {
                //                if (self.listbox()[i].startDate == self.startDateUpdate() &&
                //                    self.listbox()[i].endDate == self.endDateUpdate()) {

                currentHist = new model.ListHistoryDto(self.listbox()[i].companyCode, self.listbox()[i].startDate, new Date('9999-12-31'), self.listbox()[i].historyId);
                break;
                //                }
            }
            if (currentHist == null) {
                return;
            }
            if (currentHist.historyId == null) {
                service.addHist(currentHist).done(function() {
                    nts.uk.ui.windows.setShared('startNew', '', true);
                    self.getAllHist();
                }).fail(function(res) {
                    alert('fail');
                    dfd.reject(res);
                })
            }
            else {
                service.updateHist(currentHist).done(function() {
                    nts.uk.ui.windows.setShared('startNew', '', true);
                    self.getAllHist();
                }).fail(function(res) {
                    alert('fail');
                    dfd.reject(res);
                })
            }

        }

        //           updtateJobHist() {
        //            var self = this;
        //            var dfd = $.Deferred<any>();
        //            var checkUpdate = nts.uk.ui.windows.getShared('check_d');
        //            self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));
        //
        //            var jobHistUpdateSdate = new model.ListHistoryDto(self.startDateUpdate(), self.startDateUpdateNew(), self.endDateUpdate(), self.historyIdUpdate());
        //            service.updateHist(jobHistUpdateSdate).done(function() {
        //                nts.uk.ui.windows.setShared('startUpdateNew', '', true)
        //                self.getAllHist();
        //            }).fail(function(res) {
        //                dfd.reject(res);
        //            })
        //        }

        //          deleteJobHist() {
        //            var self = this;
        //            var dfd = $.Deferred<any>();
        //            var checkUpdate = nts.uk.ui.windows.getShared('check_d');
        //            self.startDateUpdateNew(nts.uk.ui.windows.getShared('startUpdateNew'));
        //            if (self.startDateUpdateNew() == null || self.startDateUpdateNew() == '') {
        //                if (checkUpdate() == '1') {
        //                    if (self.checkDelete() == self.startDateUpdate()) {
        //                        var jobHistDelete = new model.ListHistoryDto(self.startDateUpdate(), '1', self.endDateUpdate(), self.historyIdUpdate());
        //                    } else {
        //                        var jobHistDelete = new model.ListHistoryDto(self.startDateUpdate(), '0', self.endDateUpdate(), self.historyIdUpdate());
        //                    }
        //                    var dfd = $.Deferred<any>();
        //                    service.deleteHist(jobHistDelete).done(function(res) {
        //                        nts.uk.ui.windows.setShared('check_d', '', true);
        //                        self.getAllHist();
        //                    }).fail(function(res) {
        //                        dfd.reject(res);
        //                    })
        //                } else
        //                    return;
        //            }
        //        }

        addPosition() {
            var self = this;
            var dfd = $.Deferred();
            if (self.checkPage()) {
                var selectHistory = _.find(self.listbox(), function(item) {
                    return item.historyId === self.selectedCode();
                });
                if (self.dataSource().length === 0) {
                    let position = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                    position.historyId = selectHistory.historyId;
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
                    position_new.historyId = selectHistory.historyId;
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
            endDate: Date;
            historyId: string;

            constructor(companyCode: string, startDate: Date, endDate: Date, historyId: string) {
                var self = this;
                self.companyCode = companyCode;
                self.startDate = startDate;
                self.endDate = endDate;
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
    }
}
