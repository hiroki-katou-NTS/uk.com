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
        dirtyObject: nts.uk.ui.DirtyChecker;
        countStartDateChange: number = 1; //Biến này để tránh việc chạy hàm startDate.subscribe 2 lần
        previousCurrentCode: string = null; //lưu giá trị của currentCode trước khi nó bị thay đổi

        constructor() {
            let self = this;
            self.init();
            self.date = ko.observable("");
            self.startDate = ko.observable(new Date());

            self.startDate.subscribe(function(dateChange) {
                if (self.countStartDateChange === 1) {
                    // event datePicker onchange
                    if ($('#A_INP_003').ntsError("hasError")) {
                        $("#A_INP_003").ntsError('clear');
                    }
                } else {
                    self.countStartDateChange = 1;
                }
            })
            self.currentCode.subscribe(function(codeChanged) {
                if (!nts.uk.text.isNullOrEmpty(codeChanged) && self.currentCode() !== self.previousCurrentCode) {
                    if (self.dirtyObject.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                            self.processWhenCurrentCodeChange(codeChanged);
                        }).ifCancel(function() {
                            self.currentCode(self.previousCurrentCode);
                        })
                    } else {
                        self.processWhenCurrentCodeChange(codeChanged);    
                    }
                }
            });
            //convert to Japan Emprise year
            self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.currentEra().startDate()).toString());

        }

        processWhenCurrentCodeChange(codeChanged) {
            let self = this;
            self.countStartDateChange += 1;
            self.currentEra(self.getEra(codeChanged));
            self.dirtyObject.reset();
            
            self.date(self.currentEra().startDate().toString());
            self.startDate(self.currentEra().startDate());
            self.isDeleteEnable(true);
            self.isEnableCode(false);
            self.isUpdate(true);
            qmm034.a.service.getFixAttribute(self.currentEra().eraHist()).done(function(data) {
                if (data === 0) {
                    self.isEnableCode(true);
                }
            });    
            self.previousCurrentCode = codeChanged;
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
        
        validateData() : boolean {
            $(".nts-editor").ntsEditor("validate");
            $("#A_INP_003").ntsEditor("validate");
            
            if ($(".nts-editor").ntsError('hasError') || $("#A_INP_003").ntsError('hasError')) {
                return false;    
            }
            return true;
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
            if (!self.validateData()) {
                return;    
            }

            qmm034.a.service.addData(self.isUpdate(), node).done(function(result) {
                self.reload().done(function() {
                    self.currentCode(eraName);
                    dfd.resolve();
                    self.isDeleteEnable = ko.observable(false);
                    self.isEnableCode = ko.observable(false);
                    self.isUpdate = ko.observable(true);
                    let lastStartDate = _.maxBy(self.items(), function(o) {
                        return o.startDate;
                    });
                });
            }).fail(function(res) {
                $("#A_INP_003").ntsError("set", res.message);
            });
            return dfd.promise();

        }
        alertDelete() {
            let self = this;
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                self.deleteData();
            })
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
                    self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentEra);
                    //self.date(new Date(self.currentEra().startDate.toString()));
                    self.currentCode(self.currentEra().eraName);
                    self.processWhenCurrentCodeChange(self.currentCode());
                } else {
                    self.refreshLayout();
                }

                dfd.resolve();
            }).fail(function(res) {
                $("#A_INP_001").ntsError("set", res.message);
            });

            return dfd.promise();
        }

        refreshLayout(): void {
            let self = this;
            self.clearError();
            self.currentEra(new EraModel('', '', new Date(self.currentEra().startDate().toString()), 1, '', new Date("")));
            self.currentCode(null);
            self.isDeleteEnable(false);
            self.isEnableCode(true);
            self.isUpdate(false);
            self.dirtyObject.reset();
        }
        
        clearError() {
            $(".nts-editor").ntsError('clear');
            $("#A_INP_003").ntsError('clear');
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