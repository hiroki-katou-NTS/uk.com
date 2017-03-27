module qmm034.a.viewmodel {
    export class ScreenModel {
        //chua du lieu cua gridlist
        items: KnockoutObservableArray<any>;
        // cau truc cot cua gridList era
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        // là era code hien thời đang được select trên grid list era
        currentCode: KnockoutObservable<any>;
        // dùng để lưu trữ đối tượng era hiện thời mà đang làm việc trên màn hình(dùng trong insert, update, delete)
        currentEra: KnockoutObservable<EraModel>;
        // là cờ để phân biệt xem đang insert hay đang update
        isUpdate: KnockoutObservable<boolean>;
        // là giá trị để binding với nts datePicker
        date: KnockoutObservable<any>;
        startDate: KnockoutObservable<Date>;
        dateTime: KnockoutObservable<string>;
        // selected row
        isDeleteEnable: KnockoutObservable<boolean>;
        // binding with textEditor as a observable object
        isEnableCode: KnockoutObservable<boolean>;
        dirty: nts.uk.ui.DirtyChecker;
        countStartDateChange: number = 1;

        constructor() {
            let self = this;
            self.init();
            self.date = ko.observable("");
            self.startDate = ko.observable(new Date());

            self.startDate.subscribe(function(dateChange) {
                if (self.countStartDateChange === 1) {
                    // datePicker onchange
                    if ($('#A_INP_003').ntsError("hasError")) {
                        $("#A_INP_003").ntsError('clear');
                    }
                } else {
                    self.countStartDateChange = 1;
                }
            })
            self.currentCode.subscribe(function(codeChanged) {
                if (nts.uk.text.isNullOrEmpty(codeChanged)) {
                    self.refreshLayout();
                } else {
                    self.countStartDateChange += 1;
                    self.currentEra(self.getEra(codeChanged));
                    self.date(self.currentEra().startDate().toString());
                    self.startDate(self.currentEra().startDate());
                }

                self.isDeleteEnable(true);
                self.isEnableCode(false);
                self.isUpdate(true);
                //                self.dirty = new nts.uk.ui.DirtyChecker(self.currentEra);
                //                if (self.dirty.isDirty()) {
                //                    if (self.dirty.isDirty()) {
                //                        alert("Data is changed.");
                //                    } else {
                //                        alert("Data isn't changed.");
                //                    }
                //
                //                }
                qmm034.a.service.getFixAttribute(self.currentEra().eraHist()).done(function(data) {
                    if (data === 0) {
                        self.isEnableCode(false);
                    }
                });

            });
            //convert to Japan Emprise year
            self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.currentEra().startDate()).toString());

        }

        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: '元号', key: 'eraName', width: 50 },
                { headerText: '記号', key: 'eraMark', width: 50 },
                { headerText: '開始年月日', key: 'startDate', width: 80 },
            ]);
            self.currentEra = ko.observable((new EraModel('', '', new Date(), 1, '', new Date())));
            self.currentCode = ko.observable(null);
            self.date = ko.observable('');
            self.dateTime = ko.observable('');
            self.isDeleteEnable = ko.observable(false);
            self.isEnableCode = ko.observable(false);
            self.isUpdate = ko.observable(false);
        }

        insertData(): any {
            let self = this;
            let eraName: string;
            eraName = $('#A_INP_001').val();
            let eraMark: string;
            eraMark = $('#A_INP_002').val();
            let startDate = self.startDate();
            let endDate: Date;
            let eraHist = self.currentEra().eraHist();
            let fixAttribute: number;
            let dfd = $.Deferred<any>();
            let node: qmm034.a.service.model.EraDto;
            node = new qmm034.a.service.model.EraDto(
                eraName, eraMark, startDate, endDate, fixAttribute, eraHist
            );

            qmm034.a.service.addData(self.isUpdate(), node).done(function(result) {
                self.reload().done(function() {
                    self.currentCode(eraName);
                    dfd.resolve();
                    self.isDeleteEnable = ko.observable(false);
                    self.isEnableCode = ko.observable(false);
                    self.isUpdate = ko.observable(true);
                    let lastStartDate = _.maxBy(self.items(), function(o) {
                        return o.startDate;
                        //console.log(startDate);
                    });
                });
                //                if (nts.uk.ui._viewModel.errors.isEmpty()) {
                //                    $("#A_INP_003").ntsError('clear');
                //                }
            }).fail(function(res) {
                //alert(res.message);
                $("#A_INP_003").ntsError("set", res.message);
            });
            return dfd.promise();

        }
        alertDelete() {
            let self = this;
            if (confirm("do you wanna delete") === true) {
                self.deleteData();
            } else {
                alert("you didnt delete!");
            }
        }

        reload(): JQueryPromise<any> {
            var dfd = $.Deferred();
            var self = this;

            $.when(qmm034.a.service.getAllEras()).done(function(data) {
                self.items([]);
                if (data.length > 0) {
                    self.items(data);
                    self.date(self.currentEra().startDate().toString());
                    self.currentCode(self.currentEra().eraName());
                    self.isDeleteEnable(true);
                }
                dfd.resolve(data);
            }).fail(function(res) {

            });
            return dfd.promise();
        }

        deleteData() {
            let self = this;
            let eraName: string;
            eraName = $('#A_INP_001').val();
            let eraMark: string;
            eraMark = $('#A_INP_002').val();
            let eraHist: string = self.currentEra().eraHist();
            let startDate = self.startDate();
            let dfd = $.Deferred<any>();
            let node: qmm034.a.service.model.EraDtoDelete;
            node = new qmm034.a.service.model.EraDtoDelete(eraHist);
            let rowIndex = _.findIndex(self.items(), function(item) {
                return item.eraName == self.currentEra().eraName();
            })
            qmm034.a.service.deleteData(node).done(function(result) {
                self.reload().done(function(data) {
                    if (self.items().length === 0) {
                        self.refreshLayout();
                    } else if (self.items().length === rowIndex) {
                        self.currentCode(self.items()[rowIndex - 1].eraName);
                    } else if (self.items().length < rowIndex) {
                        self.currentCode(self.items()[0].eraName);
                    } else {
                        self.currentCode(self.items()[rowIndex].eraName);
                    }
                });
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        getEra(codeNew): EraModel {
            let self = this;
            let era = _.find(self.items(), function(item) {
                return item.eraName === codeNew;
            });
            // let startDate = new Date(era.startDate.substring(0, 10));
            //let endDate = new Date(era.endDate.substring(0, 10));
            if (era) {
                return new EraModel(era.eraName, era.eraMark, new Date(era.startDate), era.fixAttribute, era.eraHist, new Date(era.endDate));
            } else {
                return new EraModel("", "", new Date(), 0, "", new Date());
            }
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            // Page load dfd.
            var dfd = $.Deferred();
            // Resolve start page dfd after load all data.
            $.when(qmm034.a.service.getAllEras()).done(function(data: Array<EraModel>) {
                if (data.length > 0) {
                    self.items(data);
                    self.currentEra(self.items()[0]);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentEra);
                    self.date(new Date(self.currentEra().startDate.toString()));
                    self.currentCode(self.currentEra().eraName);
                    self.isUpdate(false);
                    self.isDeleteEnable(true);
                    self.isEnableCode(false);
                } else {
                    self.refreshLayout();
                    //                    self.isUpdate(false);
                }

                dfd.resolve();
            }).fail(function(res) {
                $("#A_INP_001").ntsError("set", res.message);
                //alert(res.message);
            });

            return dfd.promise();
        }

        refreshLayout(): void {
            let self = this;
            if ($('.nts-editor').ntsError("hasError")) {
                $("#A_INP_003").ntsError('clear');
                $("#A_INP_002").ntsError('clear');
                $("#A_INP_001").ntsError('clear');
            }

            //            if (self.dirty.isDirty()) {
            //                alert("Data is changed.");
            //            } else {
            //                alert("Data isn't changed.");
            //            }
            self.currentEra(new EraModel('', '', new Date(self.currentEra().startDate().toString()), 1, '', new Date("")));
            self.isDeleteEnable(false);
            self.isEnableCode(true);
            self.isUpdate(false);
            self.currentCode(null);

        }

    }


    export class EraModel {
        eraName: KnockoutObservable<string>;
        eraMark: KnockoutObservable<string>;
        startDate: KnockoutObservable<Date>;
        fixAttribute: KnockoutObservable<number>;
        eraHist: KnockoutObservable<string>;
        endDate: KnockoutObservable<Date>;

        constructor(eraName: string, eraMark: string, startDate: Date, fixAttribute: number, eraHist: string, endDate: Date) {
            this.eraName = ko.observable(eraName);
            this.eraMark = ko.observable(eraMark);
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
            this.fixAttribute = ko.observable(fixAttribute);
            this.eraHist = ko.observable(eraHist);
        }
    }



}