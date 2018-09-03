module nts.uk.at.view.kdp003.b {
    import getText = nts.uk.resource.getText;
    import StampingOutputItemSetDto = nts.uk.at.view.kdp003.b.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {

            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentId: KnockoutObservable<any>;
            stampMode: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;
            stampCode: KnockoutObservable<string>;
            stampName: KnockoutObservable<string>;
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
            selectMode: boolean;
            selectedOutputEmbossMethod: KnockoutObservable<number>;
            selectedOutputWorkHours: KnockoutObservable<number>;
            selectedOutputSetLocation: KnockoutObservable<number>;
            selectedOutputPosInfor: KnockoutObservable<number>;
            selectedOutputOT: KnockoutObservable<number>;
            selectedOutputNightTime: KnockoutObservable<number>;
            selectedOutputSupportCard: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.items = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: getText('KDP003_17'), key: 'stampOutputSetCode', width: 90 },
                    { headerText: getText('KDP003_18'), key: 'stampOutputSetName', width: 190, formatter: _.escape },
                ]);
                self.currentId = ko.observable('');
                self.stampCode = ko.observable('');
                self.stampName = ko.observable('');
                self.stampMode = ko.observable(false);
                self.enableDelete = ko.observable(true);
                self.selectMode = false;
                self.roundingRules = ko.observableArray([])

                self.selectedOutputEmbossMethod = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputWorkHours = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputSetLocation = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputPosInfor = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputOT = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputNightTime = ko.observable(NotUseAtr.NOT_USE);
                self.selectedOutputSupportCard = ko.observable(NotUseAtr.NOT_USE);
                // selected item list then update mode 
                self.currentId.subscribe(newValue => {
                    self.clearError();
                    if (!newValue) {
                         $("#stampName").blur(); 
                        self.enterNewMode().done(() => {
                            return;
                        });
                    }
                   
                    service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                        self.clearError();
                        var itemstamOutput = _.find(data, item => item.stampOutputSetCode == newValue);
                        self.stampCode(itemstamOutput.stampOutputSetCode);
                        self.stampName(itemstamOutput.stampOutputSetName);
                        self.selectedOutputEmbossMethod(itemstamOutput.outputEmbossMethod ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputWorkHours(itemstamOutput.outputWorkHours ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputSetLocation(itemstamOutput.outputSetLocation ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputPosInfor(itemstamOutput.outputPosInfor ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputOT(itemstamOutput.outputOT ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputNightTime(itemstamOutput.outputNightTime ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        self.selectedOutputSupportCard(itemstamOutput.outputSupportCard ? NotUseAtr.USE : NotUseAtr.NOT_USE);
                        $("#stampName").focus();
                        self.selectMode = false;
                        self.enableDelete(true);
                        self.stampMode(false);
                    });
                });
            }



            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                //get all enum
                service.getAllEnum().done(data => {
                    data.settingSegment.forEach((item: any, index: number) => {
                        _self.roundingRules().push({ code: item.value, name: item.localizedName });
                    });

                });
                //get data set list
                service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                    if (!_.isEmpty(data)) {
                        _self.items(data);
                        _self.stampMode(false);
                        var paramCode = nts.uk.ui.windows.getShared("datakdp003.b");
                        var itemstamOutput = _.find(data, item => item.stampOutputSetCode == paramCode);
                        if (_.isEmpty(itemstamOutput)) {
                            itemstamOutput = data[0];
                            _self.currentId(itemstamOutput.stampOutputSetCode);
                        } else {
                            _self.currentId(paramCode);
                        }
                    } else {
                        _self.enterNewMode();
                    }

                    dfd.resolve();
                }).fail((res: any) => {
                    dfd.reject();
                });

                return dfd.promise();
            }
            
            clearError(): void {
                    $('.nts-validate').ntsError('clear');
                    $('.nts-input').ntsError('clear');
                    
            }

            /**
             * mode new
             */
            public enterNewMode(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                self.enableDelete(false);
                self.selectedOutputEmbossMethod(NotUseAtr.NOT_USE);
                self.selectedOutputWorkHours(NotUseAtr.NOT_USE);
                self.selectedOutputSetLocation(NotUseAtr.NOT_USE);
                self.selectedOutputPosInfor(NotUseAtr.NOT_USE);
                self.selectedOutputOT(NotUseAtr.NOT_USE);
                self.selectedOutputNightTime(NotUseAtr.NOT_USE);
                self.selectedOutputSupportCard(NotUseAtr.NOT_USE);
                self.stampCode('');
                self.stampName('');
                self.stampMode(true);
                self.selectMode = true;
                $("#stampCode").focus();
                //clear error
                self.clearError();
                self.currentId('');
                dfd.resolve();
                return dfd.promise();
            }

            /**
            * Set focus
            */
            public setInitialFocus(): void {
                let self = this;

                if (_.isEmpty(self.items())) {
                    $('#stampCode').focus();
                } else {
                    $('#stampName').focus();
                }
            }

            /**
            *  click 登録
            */
            public register() {
                let self = this;
                $('.nts-input').ntsError('check');
                if (!self.stampCode()) {
                    $('#stampName').ntsError('clear');
                }
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
                nts.uk.ui.block.invisible();
                let data: StampingOutputItemSetDto = {
                    stampOutputSetCode: self.stampCode(),
                    stampOutputSetName: self.stampName(),
                    outputEmbossMethod: self.selectedOutputEmbossMethod() == NotUseAtr.USE,
                    outputWorkHours: self.selectedOutputWorkHours() == NotUseAtr.USE,
                    outputSetLocation: self.selectedOutputSetLocation() == NotUseAtr.USE,
                    outputPosInfor: self.selectedOutputPosInfor() == NotUseAtr.USE,
                    outputOT: self.selectedOutputOT() == NotUseAtr.USE,
                    outputNightTime: self.selectedOutputNightTime() == NotUseAtr.USE,
                    outputSupportCard: self.selectedOutputSupportCard() == NotUseAtr.USE
                }
                if (_.isEmpty(self.stampCode()) || _.isEmpty(self.stampName())) {
                    return;
                }
                if (self.selectMode) {
                    service.addStampingOutputItemSet(data).done(() => {
                        var objItem = new ItemModel(self.stampCode(), self.stampName());
                        self.items().push(objItem);
                        self.currentId(self.stampCode());
                        self.items(_.sortBy(self.items(), item => item.stampOutputSetCode));
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).fail(err => nts.uk.ui.dialog.alertError(err)).always(()=> {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    service.updateStampingOutputItemSet(data).done(() => {
                        var oldItem = _.find(self.items(), item => item.stampOutputSetCode == self.stampCode());
                        var newItem = new ItemModel(self.stampCode(), self.stampName());
                        self.items.replace(oldItem, newItem)
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).always(()=> {
                        nts.uk.ui.block.clear();
                    });

                }

            }

            /**
            *  click 削除
            */
            public btnDelete() {
                let self = this;
                var currentIndex: number = null;
                var nextCode: string = null;
                var obj = _.last(self.items());
                currentIndex = self.items.indexOf(obj);
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {

                    service.deleteStampingOutputItemSet({ code: self.stampCode() }).done((data: any) => {
                        if (self.items().length == 0) {
                            self.enterNewMode();
                        } else {

                            if (obj.stampOutputSetCode == self.stampCode()) {
                                if (currentIndex != 0) {
                                    nextCode = self.items()[currentIndex - 1].stampOutputSetCode;
                                    self.currentId(nextCode);
                                }
                            } else {
                                var nextIndex = self.items.indexOf(_.find(self.items(), item => item.stampOutputSetCode == self.stampCode()));
                                if (currentIndex != 0) {
                                    nextCode = self.items()[nextIndex + 1].stampOutputSetCode;
                                    self.currentId(nextCode);
                                }
                            }
                        }
                        service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                            if (!_.isEmpty(data)) {
                                self.items(data);
                            }
                        });
                        if (currentIndex == 0) {
                            self.items([]);
                            self.enterNewMode();
                        }

                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                }).ifNo(() => {
                    return;
                });

            }

            /**
           *  click 閉じる
           */
            public close() {
                let self = this;
                nts.uk.ui.windows.setShared("datakdp003.a", self.currentId());
                nts.uk.ui.windows.close();

            }

        }
    }

    class ItemModel {
        stampOutputSetCode: string;
        stampOutputSetName: string;
        constructor(stampOutputSetCode: string, stampOutputSetName: string) {
            this.stampOutputSetCode = stampOutputSetCode;
            this.stampOutputSetName = stampOutputSetName;
        }
    }
    /**
        * Define constant Use Server
        */
    enum NotUseAtr {
        NOT_USE = 0,// しない
        USE = 1 //する 
    }
}