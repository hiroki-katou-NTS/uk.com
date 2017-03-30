module cmm013.a.viewmodel {

    export class ScreenModel {

        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        sel_002: KnockoutObservable<string>;
        selectedRuleCode: KnockoutObservable<number>;
        roundingRules: KnockoutObservableArray<any>;

        inp_002_code: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003_name: KnockoutObservable<string>;
        inp_005_memo: KnockoutObservable<string>;

        listbox: KnockoutObservableArray<model.ListHistoryDto>;
        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<model.ListHistoryDto>;
        index_selected: KnockoutObservable<string>;

        currentCode: KnockoutObservable<any>;
        dataSource: KnockoutObservableArray<model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<model.ListPositionDto>;
        index_of_itemDelete: any;

        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;

        startDateLast: KnockoutObservable<string>;
        historyLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        checkRegister: KnockoutObservable<string>;

        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        histUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;
        startDateLastEnd: KnockoutObservable<string>;
        jobHistNew: KnockoutObservable<model.ListPositionDto>;
        checkCoppyJtitle: KnockoutObservable<string>;
        checkAddJhist: KnockoutObservable<string>;
        checkAddJtitle: KnockoutObservable<string>;
        checkUpdate: KnockoutObservable<string>;
        checkDelete: KnockoutObservable<string>;

        jTitleRef: KnockoutObservableArray<model.JobRef>;
        dataRef: KnockoutObservableArray<model.GetAuth>;
        dataRefNew: KnockoutObservableArray<model.GetAuth>;
        constructor() {
            var self = this;
            self.jTitleRef = ko.observableArray([]);
            self.dataRef = ko.observableArray([]);
            self.dataRefNew = ko.observableArray([]);
            self.label_002 = ko.observable(new Labels());
            self.label_003 = ko.observable(new Labels());
            self.label_004 = ko.observable(new Labels());
            self.label_005 = ko.observable(new Labels());
            self.label_006 = ko.observable(new Labels());
            self.sel_002 = ko.observable('');
            self.inp_002_code = ko.observable(null);
            self.inp_002_enable = ko.observable(null);
            self.inp_003_name = ko.observable(null);
            self.inp_005_memo = ko.observable(null);

            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable('');
            self.isEnable = ko.observable(true);
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');

            self.startDateLast = ko.observable(null);
            self.historyLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable("");
            self.checkRegister = ko.observable('0');

            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.histUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);
            self.startDateLastEnd = ko.observable(null);
            self.jobHistNew = ko.observable(null);
            self.checkCoppyJtitle = ko.observable('0');
            self.checkAddJhist = ko.observable('0');
            self.checkAddJtitle = ko.observable('0');
            self.checkUpdate = ko.observable('0');
            self.checkDelete = ko.observable('0');

            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'jobCode', width: 80 },
                { headerText: '名称', key: 'jobName', width: 100 }

            ]);
            self.roundingRules = ko.observableArray([
                { code: '0', name: '可能' },
                { code: '1', name: '不可' },
            ]);
            self.selectedRuleCode = ko.observable(0);

            self.itemList = ko.observableArray([
                new BoxModel(0, '全員参照可能'),
                new BoxModel(1, '全員参照不可'),
                new BoxModel(2, 'ロール毎に設定')
            ]);
            self.selectedId = ko.observable(0);
            self.enable = ko.observable(true);


            self.selectedCode.subscribe(function(codeChanged) {
                self.itemHist(self.findHist(codeChanged));
                if (self.itemHist().historyId == 'landau') { return; }
                if (self.checkRegister() == 'landau') {
                    self.checkChangeData();
                } else {
                    if (self.itemHist() != null) {
                        self.index_selected(self.itemHist().historyId);
                        self.startDateUpdate(self.itemHist().startDate);
                        self.endDateUpdate(self.itemHist().endDate);
                        self.histUpdate(self.itemHist().historyId);

                        var dfd = $.Deferred();
                        if (self.checkCoppyJtitle() == 'landau' && codeChanged == self.listbox()[0].startDate) {
                            self.itemHist(self.findHist(self.listbox()[1].startDate));
                            self.index_selected(self.itemHist().historyId);
                        }
                        service.findAllPosition(self.index_selected())
                            .done(function(position_arr: Array<model.ListPositionDto>) {
                                self.dataSource(position_arr);
                                if (self.dataSource().length > 0) {
                                    self.currentCode('');
                                    self.currentCode(self.dataSource()[0].jobCode);
                                    self.inp_002_code(self.dataSource()[0].jobCode);
                                    self.inp_003_name(self.dataSource()[0].jobName);
                                    self.inp_005_memo(self.dataSource()[0].memo);
                                    self.selectedId(self.dataSource()[0].presenceCheckScopeSet);
                                } else {
                                    self.initPosition();

                                }
                            }).fail(function(error) {
                                alert(error.message);

                            })
                        self.inp_002_enable(false);

                    }
                }
            });

            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(self.findPosition(codeChanged));
                if (self.currentItem() != null) {
                    self.inp_002_code(self.currentItem().jobCode);
                    self.inp_003_name(self.currentItem().jobName);
                    self.inp_005_memo(self.currentItem().memo);
                    self.selectedId(self.currentItem().presenceCheckScopeSet);
                    self.inp_002_enable(false);
                    service.getAllJobTitleRef(self.itemHist().historyId, self.currentItem().jobCode)
                        .done(function(jTref) {
                            console.log(jTref);
                            _.map(jTref, function(item) {
                                var tmp = new model.GetAuth(item.jobCode, item.authCode, item.authName, item.refSet);
                                self.dataRef.push(tmp);
                                return tmp;
                            });
                            console.log(self.dataRef());
                            self.dataRefNew(self.dataRef());
                        });
                }
                else self.inp_002_enable(true);
            });
            self.dataRef.subscribe(function(codeChanged) {
            });

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
                if (history_arr.length > 0) {

                    self.listbox(history_arr);
                    var histStart = _.first(history_arr);
                    var hisEnd = _.last(history_arr);
                    self.selectedCode(histStart.startDate);
                    self.startDateUpdate(histStart.startDate);
                    self.endDateUpdate(histStart.endDate);
                    self.histUpdate(histStart.historyId);
                    self.startDateLast(histStart.startDate);
                    self.historyLast(histStart.historyId);
                    self.startDateLastEnd(hisEnd.startDate);
                    dfd.resolve(history_arr);

                } else {
                    self.openCDialog();
                    dfd.resolve();
                }
            })
            return dfd.promise();
        }





        addJobHistory() {
            var self = this;
            var dfd = $.Deferred<any>();
            var jTitle = new model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.selectedId(), self.inp_005_memo());
            if (self.listbox()[0].historyId == 'landau') {
                if (self.checkCoppyJtitle() == 'landau') {
                    self.checkAddJtitle('landau');
                } else
                    if (self.checkInput() == true) {
                        self.checkAddJtitle('lanhai');
                    }
                if (self.listbox().length == 1) {
                    var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.listbox()[0].historyId);
                    self.checkAddJhist('landau');
                } else
                    if (self.listbox().length >= 1) {
                        var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.listbox()[1].historyId);
                        self.checkAddJhist('lanhai');
                    }
            } else {
                self.checkAddJhist('0');
                if (self.checkInput() == true) {
                    var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.itemHist().historyId);
                    if (self.dataSource().length != 0) {
                        for (let i = 0; i < self.dataSource().length; i++) {
                            if (self.inp_002_code() == self.dataSource()[i].jobCode) {
                                self.checkAddJtitle('chettoi');
                                break;
                            } else {
                                self.checkAddJtitle('baroi');
                            }
                        }
                    } else {
                        self.checkAddJtitle('baroi');
                    }
                }
            }

            var addHandler = new model.AfterAdd(jHist, jTitle, self.checkAddJhist(), self.checkAddJtitle());
            if (self.checkRegister() != '0' || ((self.checkAddJtitle() == 'baroi' || self.checkAddJtitle() == 'chettoi') && self.checkAddJhist() == '0')) {
                service.addHist(addHandler).done(function() {
                    alert('OK');
                    nts.uk.ui.windows.setShared('startNew', '', true);
                    self.checkRegister('0');
                    self.getAllJobTitleNew();
                }).fail(function(res) {
                    alert(res.message);
                    dfd.reject(res);
                })
            }
        }
        getAllJobTitleNew() {
            var self = this;
            var dfd = $.Deferred<any>();
            self.currentCode(self.inp_002_code());
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.inp_002_enable(false);
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

        findPosition(value: string): any {
            let self = this;
            return _.find(self.dataSource(), function(obj: model.ListPositionDto) {
                return obj.jobCode === value;
            })
        }

        findHist(value: string): any {
            let self = this;
            return _.find(self.listbox(), function(obj: model.ListHistoryDto) {
                return obj.startDate === value;
            })
        }

        findJobRef(jRef: model.GetAuth): any {
            let self = this;
            var itemModel = null;
            return _.find(self.dataRef(), function(obj: model.GetAuth) {
                return obj.authCode == jRef.authCode;
            })
        }

        getAllJobHistAfterHandler(): any {
            var self = this;
            var dfd = $.Deferred<any>();


            service.getAllHistory().done(function(history_arr: Array<model.ListHistoryDto>) {
                if (history_arr.length > 0) {


                    self.listbox = ko.observableArray([]);
                    self.listbox([]);
                    self.selectedCode = ko.observable('');
                    self.selectedCode('');
                    self.listbox(history_arr);
                    var histStart = _.first(history_arr);
                    var hisEnd = _.last(history_arr);
                    self.selectedCode(histStart.startDate);
                    self.startDateUpdate(histStart.startDate);
                    self.endDateUpdate(histStart.endDate);
                    self.histUpdate(histStart.historyId);
                    self.startDateLast(histStart.startDate);
                    self.historyLast(histStart.historyId);
                    self.startDateLastEnd(hisEnd.startDate);
                    dfd.resolve(history_arr);

                } else {
                    self.openCDialog();
                    dfd.resolve();
                }
            })
            return dfd.promise();
        }
        initPosition() {
            var self = this;
            if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                self.inp_002_enable(true);
                self.inp_002_code("");
                self.inp_003_name("");
                self.selectedId(1);
                self.inp_005_memo("");
                self.currentCode(null);
            }
        }

        checkInput(): boolean {
            var self = this;
            if (self.inp_002_code() == '' || self.inp_003_name() == '') {
                alert("nhap day du thong tin");
                return false;
            } else {
                return true;
            }
        }

        checkChangeData(): boolean {
            var self = this;
            if (self.checkRegister() == 'landau') {
                var retVal = confirm("Changed ?");
                if (retVal == true && self.startDateAddNew() !== undefined && self.startDateAddNew() != '') {
                    self.registerPosition();
                    return true;
                } else {
                    self.startDateAddNew('');
                    self.checkRegister('0');
                    self.getAllJobHistAfterHandler();
                    return false;
                }
            }
        }



        openCDialog() {
            var self = this;
            if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                var lstTmp = self.listbox();
                nts.uk.ui.windows.setShared('Id_13', self.index_selected(), true);
                nts.uk.ui.windows.setShared('startLast', self.startDateLast(), true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '履歴の追加', })
                    .onClosed(function(): any {
                        self.startDateUpdateNew('');
                        self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                        self.checkCoppyJtitle(nts.uk.ui.windows.getShared('copy_c'));
                        if (self.startDateAddNew() != '' && self.startDateAddNew() !== undefined) {
                            let add = new model.ListHistoryDto('', self.startDateAddNew(), '9999-12-31', '1');
                            //lstTmp.unshift(add)
                            self.listbox.unshift(add);
                            self.selectedCode(self.startDateAddNew());
                            let startDate = new Date(self.startDateAddNew());
                            startDate.setDate(startDate.getDate() - 1);
                            let strStartDate = startDate.getFullYear() + '-' + (startDate.getMonth() + 1) + '-' + startDate.getDate();
                            let update = new model.ListHistoryDto('', self.startDateUpdate(), strStartDate, self.histUpdate());
                            if (self.listbox().length > 1) {
                                self.listbox.splice(1, 1, update);
                                self.listbox.valueHasMutated();
                            }
                            console.log(self.listbox());
                            self.checkRegister('landau');
                        } else {
                            return;
                        }
                    });
            }
        }



        openDDialog() {
            var self = this;
            if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                var lstTmp = [];
                self.startDateUpdateNew('');
                console.log(self.endDateUpdate());
                var cDelete = 0;
                if (self.startDateUpdate() == self.listbox()[0].startDate) {
                    cDelete = 1;
                } else {
                    cDelete = 2;
                }
                nts.uk.ui.windows.setShared('startDateLast', self.startDateLastEnd(), true);
                nts.uk.ui.windows.setShared('delete', cDelete, true);
                nts.uk.ui.windows.setShared('Id_13Update', self.histUpdate(), true);
                nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate(), true);
                nts.uk.ui.windows.setShared('endUpdate', self.endDateUpdate(), true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '履歴の編集', })
                    .onClosed(function() {
                        var checkUpdate = nts.uk.ui.windows.getShared('Finish');
                        if (checkUpdate == true) {
                            self.getAllJobHistAfterHandler();
                        } else {
                            return;
                        }
                    });
            }
        }

        registerPosition(): any {
            var self = this;
            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
            self.checkCoppyJtitle(nts.uk.ui.windows.getShared('copy_c'));

            if ((self.startDateAddNew() != null && self.startDateAddNew() !== undefined && self.startDateAddNew() != '')
                || (self.checkInput() == true && self.inp_002_enable() == true)) {

                self.addJobHistory();
            } else {
                return;
            }
        }

        deletePosition() {
            var self = this;
            if (self.checkRegister() == 'landau') {
                self.checkChangeData();
            } else {
                var dfd = $.Deferred<any>();
                var item = new model.DeleteJobTitle(self.itemHist().historyId, self.currentItem().jobCode);
                self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                service.deletePosition(item).done(function(res) {
                    self.getPositionList_afterDelete();
                }).fail(function(res) {
                    dfd.reject(res);
                })
            }
        }
        getPositionList_afterDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.findAllPosition(self.index_selected()).done(function(position_arr: Array<model.ListPositionDto>) {
                self.dataSource = ko.observableArray([]);
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
                    self.initPosition();
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

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

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
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
            startDate: string;
            endDate: string;
            historyId: string;

            constructor(companyCode: string, startDate: string, endDate: string, historyId: string) {
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
            presenceCheckScopeSet: number;
            memo: string;
            constructor(jobCode: string, jobName: string, presenceCheckScopeSet: number, memo: string) {
                let self = this;
                self.jobCode = jobCode;
                self.jobName = jobName;
                self.presenceCheckScopeSet = presenceCheckScopeSet;
                self.memo = memo;
            }
        }

        export class JobRef {
            historyId: string;
            jobCode: string;
            authCode: string;
            refSet: number;
            constructor(historyId: string, jobCode: string, authCode: string, refSet: number) {
                this.historyId = historyId;
                this.jobCode = jobCode;
                this.authCode = authCode;
                this.refSet = refSet;
            }
        }


        export class GetAuth {
            jobCode: KnockoutObservable<string>;
            authCode: KnockoutObservable<string>;
            authName: KnockoutObservable<string>;
            refSet: KnockoutObservable<number>;
            constructor(jobCode: string, authCode: string,
                authName: string, refSet: number) {
                this.jobCode = ko.observable(jobCode);
                this.authCode = ko.observable(authCode);
                this.authName = ko.observable(authName);
                this.refSet = ko.observable(refSet);
            }
        }

        export class DeleteJobTitle {
            historyId: string;
            jobCode: string;
            constructor(historyId: string, jobCode: string) {
                this.historyId = historyId;
                this.jobCode = jobCode;
            }
        }

        export class AuthLevel {
            authCode: string;
            authName: string;
            constructor(authCode: string, authName: string) {
                this.authCode = authCode;
                this.authName = authName;
            }
        }
        export class AfterAdd {
            listHist: ListHistoryDto;
            listPosition: ListPositionDto;
            checkAddHist: string;//check add job history: L1(1)/L(n)(2)
            checkAdd: string;//check add job title: not add(0)/add old(copy)(1)/add new(2)/
            //add new and add old(3)/update(5)
            constructor(listHistDto: ListHistoryDto, listPositionDto: ListPositionDto, checkAddPositionHist: string, checkAddPosition: string) {
                this.listHist = listHistDto;
                this.listPosition = listPositionDto;
                this.checkAddHist = checkAddPositionHist;
                this.checkAdd = checkAddPosition;
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