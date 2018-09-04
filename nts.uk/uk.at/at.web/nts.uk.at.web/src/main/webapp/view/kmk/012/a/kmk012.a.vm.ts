module nts.uk.at.view.kmk012.a {

    import ClosureHistoryFindDto = service.model.ClosureHistoryFindDto;
    import DayofMonth = service.model.DayofMonth;
    import ClosureDto = service.model.ClosureDto;
    import ClosureHistoryMDto = service.model.ClosureHistoryMasterDto;
    import ClosureHistoryDDto = service.model.ClosureHistoryHeaderDto;
    import ClosureSaveDto = service.model.ClosureSaveDto;
    import ClosureHistoryDto = service.model.ClosureHistoryDto;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {

        export class ScreenModel {
            lstClosureHistory: KnockoutObservableArray<ClosureHistoryFindDto>;
            closureModel: ClosureModel;
            closureHistoryModel: ClosureHistoryDetailModel;
            useClassification: KnockoutObservableArray<any>;
            lstDayOfMonth: KnockoutObservableArray<DayofMonth>;
            columnsLstClosureHistory: KnockoutObservableArray<any>;
            selectCodeLstClosure: KnockoutObservable<ClosureHistoryFindDto>;
            selectCodeLstClosureHistory: KnockoutObservable<ClosureHistoryMDto>;
            textEditorOption: KnockoutObservable<any>;
            visibleUseClassification: KnockoutObservable<boolean>;
            enableChangeClosureDate: KnockoutObservable<boolean>;
            enableUseClassification: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.lstClosureHistory = ko.observableArray<ClosureHistoryFindDto>([]);
                self.closureModel = new ClosureModel();
                self.closureHistoryModel = new ClosureHistoryDetailModel();
                self.columnsLstClosureHistory = ko.observableArray([
                    { headerText: 'コード', prop: 'id', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);

                self.useClassification = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("KMK012_3") },
                    { code: '0', name: nts.uk.resource.getText("KMK012_4") }
                ]);
                self.selectCodeLstClosure = ko.observable(new ClosureHistoryFindDto());
                self.selectCodeLstClosureHistory = ko.observable(new ClosureHistoryMDto());
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());

                self.textEditorOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    width: "50px",
                    textmode: "text",
                    textalign: "left"
                }));

                self.selectCodeLstClosure.subscribe(function(val: ClosureHistoryFindDto) {
                    self.visibleUseClassification(val.id != self.lstClosureHistory()[0].id);
                    self.detailClosure(val.id);
                });

                self.selectCodeLstClosureHistory.subscribe(function(val: ClosureHistoryMDto) {
                    self.enableChangeClosureDate(val.startDate == self.closureModel.closureHistories()[0].startDate);
                    self.detailClosureHistory(val);
                });

                self.visibleUseClassification = ko.observable(true);
                self.enableChangeClosureDate = ko.observable(true);
                self.enableUseClassification = ko.observable(true);


                self.closureModel.useClassification.subscribe(function(val: number) {
                    self.updateDataInit(val);
                });
            }

            openDialogE() {
                let self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/012/e/index.xhtml");
            }
            
            /**
             * open dialog screen F 
             */
            openDialogCalDailyPfm(): void {
                nts.uk.ui.windows.sub.modal("/view/kmk/012/f/index.xhtml").onClosed(function() {
                    
                }); 
            }

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findAllClosureHistory().done(function(data) {
                    var dataRes: ClosureHistoryFindDto[] = [];
                    for (var item: ClosureHistoryFindDto of data) {
                        var dataI: ClosureHistoryFindDto = new ClosureHistoryFindDto();
                        dataI.id = item.id;
                        dataI.name = item.name;
                        dataI.updateData();
                        dataRes.push(dataI);
                    }
                    self.lstClosureHistory(dataRes);
                    self.selectCodeLstClosure(data[0]);
                });
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * function update UI by selected use class
             */
            updateDataInit(useClass: number) {
                var self = this;
                if (useClass == 0) {
                    self.enableUseClassification(false);
                    self.enableChangeClosureDate(self.checkIsFirstClosure() && self.enableUseClassification());
                    self.clearValiate();
                }
                else {
                    self.enableUseClassification(true);
                    self.enableChangeClosureDate(self.checkIsFirstClosure());
                    self.clearValiate();
                }
            }

            /**
             * function check first closure
             */
            checkIsFirstClosure(): boolean {
                var self = this;
                if (self.closureModel.closureHistories().length > 0) {
                    return self.closureHistoryModel.startDate() == self.closureModel.closureHistories()[0].startDate;
                }
                return false;
            }

            /**
             * detail closure by call service find by id => update view model
             */
            detailClosure(closureId: number): void {
                var self = this;
                service.findByIdClosure(closureId).done(function(data: ClosureDto) {
                    self.closureModel.updateData(data);
                    self.selectCodeLstClosureHistory(data.closureSelected);
                    $("#selUseClassification").focus();
                });
            }

            /**
             * detail closure history by call service find by id => update view model
             */
            detailClosureHistory(master: ClosureHistoryMDto) {
                var self = this;
                service.findByIdClosureHistory(master).done(function(data) {
                    self.closureHistoryModel.updateData(data);
                    self.updateDataInit(self.closureModel.useClassification());
                    self.clearValiate();
                });
            }



            /**
             * ini data closure day
             */
            intDataMonth(): DayofMonth[] {
                var data: DayofMonth[] = [];
                var i: number = 1;
                for (i = 1; i <= 30; i++) {
                    var dayI: DayofMonth;
                    dayI = new DayofMonth();
                    dayI.day = i;
                    dayI.name = i + "日";
                    data.push(dayI);
                }
                var dayLast: DayofMonth;
                dayLast = new DayofMonth();
                dayLast.day = 0;
                dayLast.name = "末日";
                data.push(dayLast);
                return data;
            }



            /**
             * collect data  
             */
            collectData(): ClosureSaveDto {
                var self = this;
                var dto: ClosureSaveDto;
                dto = new ClosureSaveDto();
                dto.closureId = self.closureModel.closureId();
                dto.useClassification = self.closureModel.useClassification();
                if (dto.useClassification == 1) {
                    dto.month = self.closureModel.month();
                } else {
                    dto.month = 0;
                }
                return dto;
            }

            /**
             * clear validate client
             */
            clearValiate() {
                $('#inpMonth').ntsError('clear')
                $('#inpname').ntsError('clear')
            }

            /**
             * validate client by action click
             */
            validateClient(): boolean {
                var self = this;
                self.clearValiate();
                if (self.closureModel.useClassification() == 1) {
                    $("#inpMonth").ntsEditor("validate");
                    $("#inpname").ntsEditor("validate");
                    if ($('.nts-input').ntsError('hasError')) {
                        return true;
                    }
                }
                return false;
            }

            /**
             * save closure history by call service
             */
            saveClosureHistory(): void {
                var self = this;
                blockUI.grayout();
                if (self.closureModel.useClassification() == 1 && self.validateClient()) {
                    return;
                }
                self.clearValiate();
                service.saveClosure(self.collectData()).done(function() {
                    if (self.closureModel.useClassification() == 1) {
                        service.saveClosureHistory(self.collectDataHistory()).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                self.reloadPage(self.selectCodeLstClosure().id,
                                    self.selectCodeLstClosureHistory().startDate);
                            });
                        }).fail(function(error) {
                            nts.uk.ui.dialog.alertError(error).then(function() {
                                self.reloadPage(self.selectCodeLstClosure().id,
                                    self.selectCodeLstClosureHistory().startDate);
                            });
                        }).always(() => {
                            blockUI.clear();
                        });
                    } else {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            self.reloadPage(self.selectCodeLstClosure().id,
                                self.selectCodeLstClosureHistory().startDate);
                        });
                    }
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error).then(function() {
                        self.reloadPage(self.selectCodeLstClosure().id,
                            self.selectCodeLstClosureHistory().startDate);
                    });
                }).always(() => {
                    blockUI.clear();
                });
            }

            /**
             * reload page 
             */
            reloadPage(closureId: number, startDate: number): void {
                var self = this;
                service.findAllClosureHistory().done(function(data) {
                    var dataRes: ClosureHistoryFindDto[] = [];
                    for (var item: ClosureHistoryFindDto of data) {
                        var dataI: ClosureHistoryFindDto = new ClosureHistoryFindDto();
                        dataI.id = item.id;
                        dataI.name = item.name;
                        dataI.updateData();
                        dataRes.push(dataI);
                    }
                    self.lstClosureHistory(dataRes);
                    for (var closure: ClosureHistoryFindDto of data) {
                        if (closure.id == closureId) {
                            self.selectCodeLstClosure(closure);
                            self.detailClosure(closureId);
                            return;
                        }
                    }

                });
            }

            /**
             * collect data closure history 
             */
            collectDataHistory(): ClosureHistoryDto {
                var self = this;
                var dto: ClosureHistoryDto;
                dto = new ClosureHistoryDto();
                dto.closeName = self.closureHistoryModel.closureName();
                dto.closureId = self.closureHistoryModel.closureId();
                dto.closureDate = self.closureHistoryModel.closureDate();
                dto.startDate = self.closureHistoryModel.startDate();
                return dto;
            }

            // 締め期間確認 
            /**
             * open dialog D
             */
            public openConfirmClosingPeriodDialog(): void {
                var self = this;
                nts.uk.ui.windows.setShared('closureId', self.closureModel.closureId());
                nts.uk.ui.windows.setShared('startDate', self.closureHistoryModel.startDate());
                nts.uk.ui.windows.sub.modeless('/view/kmk/012/d/index.xhtml',
                    { title: '締め期間確認 ', dialogClass: 'no-close' }).onClosed(function() {
                        self.reloadPage(self.closureModel.closureId(), self.closureHistoryModel.startDate());
                    });
            }


        }


        export class ClosureHistoryModel {

            id: KnockoutObservable<number>;
            name: KnockoutObservable<string>;

            constructor() {
                this.id = ko.observable(0);
                this.name = ko.observable('');
            }

            updateDate(dto: ClosureHistoryFindDto) {
                this.id(dto.id);
                this.name(dto.name);
            }
        }

        export class ClosureModel {
            /** The closure id. */
            closureId: KnockoutObservable<number>;

            /** The use classification. */
            useClassification: KnockoutObservable<number>;

            /** The day. */
            month: KnockoutObservable<number>;


            closureHistories: KnockoutObservableArray<ClosureHistoryMDto>;

            constructor() {
                this.closureId = ko.observable(0);
                this.useClassification = ko.observable(0)
                this.month = ko.observable(0);
                this.closureHistories = ko.observableArray<ClosureHistoryMDto>([]);
            }

            /**
             * update data 
             */
            updateData(dto: ClosureDto): void {
                this.closureId(dto.closureId);
                this.useClassification(dto.useClassification);
                this.month(dto.month);
                this.closureHistories([]);
                var dataRes: ClosureHistoryMDto[] = [];
                for (var history: ClosureHistoryMDto of dto.closureHistories) {
                    var dataI: ClosureHistoryMDto = new ClosureHistoryMDto();
                    dataI.closureId = history.closureId;
                    dataI.endDate = history.endDate;
                    dataI.startDate = history.startDate;
                    dataI.updateData();
                    dataRes.push(dataI);
                }
                this.closureHistories(dataRes);
            }


        }

        export class ClosureHistoryDetailModel {

            /** The closure id. */
            closureId: KnockoutObservable<number>;

            /** The end date. */
            // 終了年月: 年月
            closureName: KnockoutObservable<string>;

            /** The start date. */
            // 開始年月: 年月
            closureDate: KnockoutObservable<number>;


            startDate: KnockoutObservable<number>;


            constructor() {
                this.closureId = ko.observable(0);
                this.closureName = ko.observable('');
                this.closureDate = ko.observable(0);
                this.startDate = ko.observable(0);
            }


            /**
             * update data
             */
            updateData(dto: ClosureHistoryDDto): void {
                this.closureId(dto.closureId);
                this.closureName(dto.closureName);
                this.closureDate(dto.closureDate);
                this.startDate(dto.startDate);
            }

        }

    }
}