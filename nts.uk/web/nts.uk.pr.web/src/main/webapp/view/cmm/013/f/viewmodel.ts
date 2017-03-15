module cmm013.f.viewmodel {

    export class ScreenModel {

        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        test: KnockoutObservable<string>;

        historyId: KnockoutObservable<string>;

        radiobox: KnockoutObservable<RadioBox>;

        inp_002_code: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003_name: KnockoutObservable<string>;
        inp_004: KnockoutObservable<TextEditor_3>;
        inp_005_memo: KnockoutObservable<string>;

        sel_0021: KnockoutObservable<SwitchButton>;
        sel_0022: KnockoutObservable<SwitchButton>;
        sel_0023: KnockoutObservable<SwitchButton>;
        sel_0024: KnockoutObservable<SwitchButton>;

        listbox: KnockoutObservableArray<model.ListHistoryDto>;



        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<any>;
        selectedHis: KnockoutObservable<any>;
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<model.ListHistoryDto>;
        index_selected: KnockoutObservable<any>;
        updatedata: KnockoutObservable<any>;
        adddata: any;
        itemdata: any;

        currentCode: KnockoutObservable<string>;
        currentYm: KnockoutObservable<string>;
        dataSource: KnockoutObservableArray<model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;


        length: KnockoutObservable<number>;
        startDateLast: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;

        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<model.ListPositionDto>;
        currentItem2: KnockoutObservable<model.ListPositionDto>;
        index_of_itemDelete: any;

        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        createdMode: KnockoutObservable<boolean>;
        constructor() {
            var self = this;

            self.label_002 = ko.observable(new Labels());
            self.label_003 = ko.observable(new Labels());
            self.label_004 = ko.observable(new Labels());
            self.label_005 = ko.observable(new Labels());
            self.label_006 = ko.observable(new Labels());
            self.radiobox = ko.observable(new RadioBox());
            self.itemdata = ko.observable(null);
            self.adddata = ko.observable(null);
            self.updatedata = ko.observable(null);
            self.inp_002_code = ko.observable(null);
            self.inp_002_enable = ko.observable(false);
            self.inp_003_name = ko.observable(null);
            self.inp_004 = ko.observable(new TextEditor_3());
            self.inp_005_memo = ko.observable(null);
            self.sel_0021 = ko.observable(new SwitchButton);
            self.sel_0022 = ko.observable(new SwitchButton);
            self.sel_0023 = ko.observable(new SwitchButton);
            self.sel_0024 = ko.observable(new SwitchButton);
            self.test = ko.observable('2');
            self.length = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.startDateLast = ko.observable(null);
            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable(null);
            self.selectedHis = ko.observable(null);

            self.isEnable = ko.observable(true);
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');


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
            self.createdMode = ko.observable(true);




            self.selectedCode.subscribe((function(codeChanged) {
                self.itemHist(self.findHist(codeChanged));
                if (self.itemHist() != null) {
                    self.index_selected(self.itemHist().historyId);
                    //                    console.log(self.

                    var dfd = $.Deferred();
                    service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                        self.dataSource(position_arr);
                        if (self.dataSource().length > 0) {
                            self.currentCode(self.dataSource()[0].jobCode);
                            self.inp_002_code(self.dataSource()[0].jobCode);
                            self.inp_003_name(self.dataSource()[0].jobName);
                            self.inp_005_memo(self.dataSource()[0].memo);
                            //    self.selectedId(self.dataSource()[0].presenceCheckScopeSet);
                        } dfd.resolve();
                    }).fail(function(error) {
                        alert(error.message);

                    })
                    dfd.resolve();
                    return dfd.promise();
                }
            }));

            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findPosition(codeChanged));
                if (self.currentItem() != null) {
                    self.createdMode(false);
                    self.inp_002_code(self.currentItem().jobCode);
                    self.inp_003_name(self.currentItem().jobName);
                    self.inp_005_memo(self.currentItem().memo);
                    //     self.selectedId(self.currentItem().presenceCheckScopeSet);
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
            _.find(self.listbox(), function(obj: viewmodel.model.ListHistoryDto) {
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

            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
                self.listbox(history_arr);
                if (history_arr.length > 0) {
                    self.selectedCode(history_arr[0].historyId);
                }
                if (self.dataSource().length > 0) {

                    self.selectedCode = ko.observable(self.listbox()[0].startDate);
                }
            }).fail(function(error) {
                alert(error.message);

            })
            dfd.resolve();
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
                /*
                                //                for (let i = 0; i < self.dataSource().                
                                if (!self.createdMode()) {
                
                                    var position = self.dataSource()[i];
                                    var position_update = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                                    position_update.historyId = selectHistory.historyId;
                                    service.updatePosition(position_update).done(function() {
                                        self.updatedata(position_update);
                                        self.getPositionList_afterUpdate();
                
                                    }).fail(function(res) {
                                        alert("更新対象のデータが存在しません。");
                                        dfd.reject(res);
                                    })
                                    //                        break;
                                } else {
                
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
                                    //                        break;
                                    //                    }
                
                                }
                                */
            }
        }





        //phai lam getshare

        openBDialog() {
            nts.uk.ui.windows.sub.modal('/view/cmm/013/b/index.xhtml', { title: '画面ID：B', });
        }
           openCDialog() {
            var self = this;
            if(self.selectedCode() == null)
                return false;
            var selectedCode = self.selectedCode().split(';');
            nts.uk.ui.windows.setShared('stmtCode', selectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', {title: '明細レイアウトの作成＞履歴追加'}).onClosed(function(): any {
               
            });
        }
        openDDialog() {
              var self = this;
            if (self.selectedCode() === null)
                return false;
            var selectedCode = self.selectedCode().split(';');
            nts.uk.ui.windows.setShared('stmtCode', selectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function(): any {
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

        export class ListHistoryDto {
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(startDate: string, endDate: string, historyId: string) {
                var self = this;
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
