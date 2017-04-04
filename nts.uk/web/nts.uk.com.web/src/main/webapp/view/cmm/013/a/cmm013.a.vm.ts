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
        startDateUpdate: KnockoutObservable<string>;
        currentCode: KnockoutObservable<any>;
        dataSource: KnockoutObservableArray<model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<model.ListPositionDto>;
        index_of_itemDelete: any;
        updatedata: KnockoutObservable<any>;
        adddata: KnockoutObservable<any>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateLast: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        checkRegister: KnockoutObservable<string>;

        oldStartDate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        histIdUpdate: KnockoutObservable<string>;
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
        srtDateLast: KnockoutObservable<string>;
        jTitleRef: KnockoutObservableArray<model.JobRef>;
        dataRef: KnockoutObservableArray<model.GetAuth>;
        dataRefNew: KnockoutObservableArray<model.GetAuth>;
        createdMode: KnockoutObservable<boolean>;
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
            self.createdMode = ko.observable(true);
            self.updatedata = ko.observable(null);
            self.adddata = ko.observable(null);
            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable('');
            self.isEnable = ko.observable(true);
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');
            self.srtDateLast = ko.observable(null);
            self.startDateLast = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable("");
            self.checkRegister = ko.observable('0');
            self.startDateUpdate = ko.observable(null);
            self.oldStartDate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.histIdUpdate = ko.observable(null);
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
            self.historyIdUpdate = ko.observable(null);
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

            //change history
            self.selectedCode.subscribe(function(codeChanged) {


                //tim kiem position tuong ung voi history
                service.findAllPosition(codeChanged).done(function(position_arr: Array<model.ListPositionDto>) {
                    self.dataSource(position_arr);
                    if (self.dataSource().length > 0) {
                        self.currentCode(self.dataSource()[0].jobCode);
                        self.inp_002_code(self.dataSource()[0].jobCode);
                        self.inp_003_name(self.dataSource()[0].jobName);
                        self.inp_005_memo(self.dataSource()[0].memo);
                        self.index_selected(codeChanged);

                    }
                }).fail(function(err: any) {
                    nts.uk.ui.dialog.alert(err.message);
                })
                //set lai disable cho input code
                self.inp_002_enable(false);
            });

            //change position
            self.currentCode.subscribe(function(codeChanged) {
                self.currentItem(self.findPosition(codeChanged));
                if (self.currentItem() != null) {
                    self.inp_002_code(self.currentItem().jobCode);
                    self.inp_003_name(self.currentItem().jobName);
                    self.inp_005_memo(self.currentItem().memo);
                    self.selectedId(self.currentItem().presenceCheckScopeSet);
                    self.inp_002_enable(false);
                    self.createdMode(false);
                    //tim kiem quyen theo historyId va position code
                    service.getAllJobTitleAuth(self.currentItem().historyId, self.currentItem().jobCode).done(function(jTref) {
                        //neu nhu ko co du lieu thi an list di
                        if (jTref.length === 0) {
                            $('.trLst003').css('visibility', 'hidden');
                        } else {
                            $('.trLst003').css('visibility', 'visible');
                            self.dataRef([]);
                            _.map(jTref, function(item) {
                                var tmp = new model.GetAuth(item.jobCode, item.authCode, item.authName, item.referenceSettings);
                                self.dataRef.push(tmp);
                                return tmp;
                            });
                            self.dataRefNew(self.dataRef());
                            self.createdMode(true);
                        }
                    });
                }
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
                    self.selectedCode(histStart.historyId);
                    self.srtDateLast(histStart.startDate);
                    self.endDateUpdate(histStart.endDate);
                    self.histIdUpdate(histStart.historyId);
                    self.startDateLast(histStart.startDate);
                    self.historyIdUpdate(histStart.historyId);
                    self.startDateLastEnd(hisEnd.startDate);
                    self.oldStartDate();
                    dfd.resolve(history_arr);

                } else {
                    self.openCDialog();
                    dfd.resolve();
                }
            })
            return dfd.promise();

        }

        //thuc hien nut 新規
        registerPosition(): any {
            var self = this;

            //check xem position da duoc nhap chua
            //ko ton tai viec co history nhung ko co position
            if (!self.checkPositionValue()) {
                return;
            } else {
                var chkInsert = nts.uk.ui.windows.getShared('cmm013Insert');
                var chkCopy = nts.uk.ui.windows.getShared('cmm013C_copy');
                var jobInfor: model.jobtitle;
                var dfd = $.Deferred();
                var startDate = "";
                if (chkInsert === true) {
                    startDate = nts.uk.ui.windows.getShared('cmm013C_startDateNew');

                }
                if (!chkCopy || !chkInsert) {
                    //thong tin cua jobcode
                    jobInfor = new model.jobtitle(self.inp_003_name(),
                        self.inp_005_memo(),
                        '99',
                        self.selectedId(),
                        '');
                }
                var positionInfor: model.registryCommand;
                //lay thong tin cua quyen 
                if (self.selectedId() === 2) {
                    var refInfor = [];
                    var dataRef = ko.toJS(self.dataRef());
                    _.each(dataRef, function(obj: model.JobRef) {
                        positionInfor.refJobInfo.push(new model.refJob(obj.authCode, obj.referenceSettings));
                    })
                }
                positionInfor.historyId = self.selectedCode();
                positionInfor.startDate = startDate;
                positionInfor.chkCopy = chkCopy;
                positionInfor.jobCode = self.inp_002_code();
                positionInfor.chkInsert = self.inp_002_enable();
                positionInfor.jobTitleInfor.push(jobInfor);
                service.registry(positionInfor).done(function() {
                    
                    //clear het bien toan cuc

                })
                return dfd.promise();
            }
        }
        
         //check dieu kien cua position
        checkPositionValue(): boolean {
            var self = this;
            //check data chua duoc nhap
            if (self.inp_002_code() === "" || self.inp_002_code() === null) {
                nts.uk.ui.dialog.alert("コードが入力されていません");
                $('#inp_002').focus();
                return false;
            }
            if (self.inp_003_name() === "" || self.inp_003_name() === null) {
                nts.uk.ui.dialog.alert("名称が入力されていません");
                $('inp_003').focus();
                return false;
            }
            //check code bi trung (can bo sung them)


            return true;
        }





        addHist() {
            var self = this;
            var dfd = $.Deferred<any>();
            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
            var i = 0;
            //check xem position da duoc nhap chua
            //ko ton tai viec co history nhung ko co position
            if (!self.checkPositionValue()) {
                return;
            } else {
                //lay du lieu dau tien cua list history de insert
                var currentHist = new model.ListHistoryDto(self.listbox()[0].companyCode, self.listbox()[0].startDate, '9999/12/31', self.listbox()[0].historyId);
                //dudt
                if (!currentHist) {
                    return false;
                }
                if (!currentHist.historyId) {
                    service.registry(currentHist).done(function() {
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
                    self.srtDateLast(histStart.startDate);
                    self.endDateUpdate(histStart.endDate);
                    self.histIdUpdate(histStart.historyId);
                    self.startDateLast(histStart.startDate);
                    self.historyIdUpdate(histStart.historyId);
                    self.startDateLastEnd(hisEnd.startDate);
                    dfd.resolve(history_arr);

                } else {
                    self.openCDialog();
                    dfd.resolve();
                }
            })
            return dfd.promise();
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
            var result = _.find(self.dataSource(), function(obj: model.ListPositionDto) {
                return obj.jobCode === value;
            });
            return (result) ? result : null;
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
            if (self.checkRegister() == '1') {
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
                nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected(), true);
                nts.uk.ui.windows.setShared('CMM013_startDateLast', self.startDateLast(), true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '履歴の追加', })
                    .onClosed(function(): any {
                        self.startDateUpdateNew('');
                        self.selectedCode('');
                        self.startDateAddNew(nts.uk.ui.windows.getShared('cmm013C_startDateNew'));
                        self.checkCoppyJtitle(nts.uk.ui.windows.getShared('cmm013C_copy'));
                        if (self.startDateAddNew() != '' && self.startDateAddNew() !== undefined) {
                            let add = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '1');
                            self.listbox.unshift(add);
                            self.selectedCode(self.startDateAddNew());
                            self.currentCode("");
                            self.dataSource([]);
                            if (self.currentCode("") && self.dataSource([])) {
                                alert("");
                            }
                            let startDate = new Date(self.startDateAddNew());
                            startDate.setDate(startDate.getDate() - 1);
                            let strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                            let update = new model.ListHistoryDto('', self.startDateLast(), strStartDate, self.historyIdUpdate());
                            if (self.listbox().length > 1) {
                                self.listbox.splice(1, 1, update);
                                self.listbox.valueHasMutated();
                            }
                            console.log(self.listbox());
                            self.checkRegister('1');
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


                nts.uk.ui.windows.setShared('cmm013HistoryId', self.historyIdUpdate(), true);
                nts.uk.ui.windows.setShared('cmm013StartDate', self.oldStartDate(), true);
                nts.uk.ui.windows.setShared('cmm013EndDate', self.endDateUpdate(), true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', })
                    .onClosed(function() {

                    });
            }
        }



        deletePosition() {
            var self = this;
            if (self.checkRegister() == '1') {
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

        addPosition() {
            var self = this;
            var dfd = $.Deferred();
            if (self.checkInput()) {
                if (self.dataSource().length === 0) {
                    let position = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), 1, self.inp_005_memo());
                    position.historyId = self.index_selected();
                    service.addPosition(position).done(function() {
                        self.getPosition_first();
                    }).fail(function(res) {
                        dfd.reject(res);
                    })
                }
                if (self.inp_002_enable(false)) {
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
                    let position_new = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), 1, self.inp_005_memo());
                    position_new.historyId = self.index_selected();
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
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(startDate: string, endDate: string, historyId: string) {
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
            referenceSettings: number;
            constructor(historyId: string, jobCode: string, authCode: string, referenceSettings: number) {
                this.historyId = historyId;
                this.jobCode = jobCode;
                this.authCode = authCode;
                this.referenceSettings = referenceSettings;
            }
        }


        export class GetAuth {
            jobCode: KnockoutObservable<string>;
            authCode: KnockoutObservable<string>;
            authName: KnockoutObservable<string>;
            referenceSettings: KnockoutObservable<number>;
            constructor(jobCode: string, authCode: string,
                authName: string, referenceSettings: number) {
                this.jobCode = ko.observable(jobCode);
                this.authCode = ko.observable(authCode);
                this.authName = ko.observable(authName);
                this.referenceSettings = ko.observable(referenceSettings);
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
            checkAddHist: string;
            checkAdd: string;
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

        export class registryCommand {
            historyId: string;
            startDate: string;
            chkCopy: boolean;
            jobCode: string;
            chkInsert: boolean;
            jobTitleInfor: Array<jobtitle>;
            refJobInfo: Array<refJob>;
            constructor(historyId: string, startDate: string, chkCopy: boolean, jobCode: string, chkInsert: boolean, jobTitleInfor: Array<jobtitle>, refJobInfo: Array<refJob>) {
                this.historyId = historyId;
                this.startDate = startDate;
                this.chkCopy = chkCopy;
                this.jobCode = jobCode;
                this.chkInsert = chkInsert;
                this.jobTitleInfor = jobTitleInfor;
                this.refJobInfo = refJobInfo;
            }

        }

        export class jobtitle {
            jobName: string;
            memo: string;
            hiterarchyOrderCode: string;
            presenceCheckScopeSet: number;
            jobOutCode: string;
            constructor(jobName: string, memo: string, hiterarchyOrderCode: string, presenceCheckScopeSet: number, jobOutCode: string) {
                this.jobName = jobName;
                this.memo = memo;
                this.hiterarchyOrderCode = hiterarchyOrderCode;
                this.presenceCheckScopeSet = presenceCheckScopeSet;
                this.jobOutCode = jobOutCode;
            }
        }

        export class refJob {
            authorizationCode: string;
            referenceSettings: number;
            constructor(authorizationCode: string, referenceSettings: number) {
                this.authorizationCode = authorizationCode;
                this.referenceSettings = referenceSettings;
            }
        }

    }
}