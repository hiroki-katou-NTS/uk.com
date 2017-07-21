module nts.uk.at.view.ksm003.a {

    import PatternCalendarDto = service.model.PatternCalendarDto;

    export module viewmodel {
        export class ScreenModel {
            //A_label_x
            columns: KnockoutObservableArray<any>;
            columnsWork: KnockoutObservableArray<any>;
            dataSource: KnockoutObservableArray<model.Item>;
            lstPattern: KnockoutObservableArray<model.Item>;
            currentCode: KnockoutObservable<string>;
            switchUSe3: KnockoutObservableArray<any>;
            requiredAtr: KnockoutObservable<any>;
            divReasonCode: KnockoutObservable<string>;
            divReasonContent: KnockoutObservable<string>;
            enableCode: KnockoutObservable<boolean>;

            //f
            itemDivReason: KnockoutObservable<model.Item>;
            itemPatternCal: KnockoutObservable<model.Item>;


            divTimeId: KnockoutObservable<number>;
            index_of_itemDelete: any;
            objectOld: any;
            enableDel: KnockoutObservable<boolean>;
            checkModel: KnockoutObservable<boolean>;

            patternCalendar: KnockoutObservable<model.Item>;
            patternCode: KnockoutObservable<number>;
            patternName: KnockoutObservable<string>;
            workTypeCodes: KnockoutObservableArray<string>;
            workHouseCodes: KnockoutObservableArray<string>;
            patternCalendarNumberDay: KnockoutObservable<number>;
            constructor() {

                var self = this;
                self.currentCode = ko.observable('');
                self.dataSource = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);
                self.columnsWork = ko.observableArray([
                    { headerText: '', key: document.createElement('button'), formatter: _.escape, width: 50 },
                    { headerText: nts.uk.resource.getText('KSM003_30'), key: 'patternCode', formatter: _.escape, width: 200 },
                    { headerText: nts.uk.resource.getText('KSM003_31'), key: 'patternCode', formatter: _.escape, width: 200 },
                    { headerText: nts.uk.resource.getText('KSM003_32'), key: 'patternName', formatter: _.escape, width: 50 }
                ]);
                self.lstPattern = ko.observableArray([]);
                self.switchUSe3 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("Enum_DivergenceReasonInputRequiredAtr_Required") },
                    { code: '0', name: nts.uk.resource.getText("Enum_DivergenceReasonInputRequiredAtr_Optional") },
                ]);
                self.requiredAtr = ko.observable(0);
                self.divReasonCode = ko.observable('');
                self.divReasonContent = ko.observable('');
                self.enableCode = ko.observable(false);
                //f
                self.itemDivReason = ko.observable(null);
                self.itemPatternCal = ko.observable(null);

                self.divTimeId = ko.observable(null);
                self.enableDel = ko.observable(true);
                self.checkModel = ko.observable(true);


                self.patternCode = ko.observable(null);
                self.patternName = ko.observable('');
                self.workTypeCodes = ko.observableArray([]);
                self.workHouseCodes = ko.observableArray([]);
                self.patternCalendarNumberDay = ko.observable(null);
                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged) {
                    //                var t0 = performance.now();   
                    self.clearError();
                    self.itemPatternCal(self.findItemPatternCal(codeChanged));
                    if (self.itemPatternCal() === undefined || self.itemPatternCal() == null) {
                        return;
                    }
                    self.objectOld = self.itemPatternCal().patternCode + self.itemPatternCal().patternName + self.itemPatternCal().workTypeCodes + self.itemPatternCal().workHouseCodes + self.itemPatternCal().patternCalendarNumberDay;
                    self.enableCode(false);
                    self.patternCode(self.itemPatternCal().patternCode);
                    self.patternName(self.itemPatternCal().patternName);
                    self.workTypeCodes(self.itemPatternCal().workTypeCodes);
                    self.workHouseCodes(self.itemPatternCal().workHouseCodes);
                    self.patternCalendarNumberDay(self.itemPatternCal().patternCalendarNumberDay);
                    self.enableDel(true);
                    $("#inpPattern").focus();
                });
            }




            /**
             * start page
             * get all divergence reason
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
                    self.lstPattern(dataRes);
                    nts.uk.ui.block.clear();
                    self.currentCode(null);
                    if (dataRes === undefined || dataRes.length == 0) {
                        self.dataSource([]);
                        self.enableCode(true);
                        self.checkModel(false);
                        self.enableDel(false);
                    } else {
                        self.dataSource(dataRes);
                        let reasonFirst = _.first(dataRes);
                        self.currentCode(reasonFirst.dataRes);
                        self.checkModel(true);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }



            //        /**
            //         * find item Pattern Cal is selected
            //         */
            findItemPatternCal(value: string): any {
                let self = this;
                var itemModel = null;
                return _.find(self.dataSource(), function(obj: model.Item) {
                    return obj.patternCode == value;
                })
            }

            refreshData() {
                var self = this;
                //                self.divReasonCode(null);
                //                self.divReasonContent("");
                //                self.requiredAtr(0);
                self.enableCode(true);
                //                self.clearError();
                //                self.enableDel(false);
                //                self.currentCode(null);
                $("#inpCode").focus();

            }


            clearError(): void {
                if ($('.nts-editor').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }
            RegistrationDivReason() {
                nts.uk.ui.block.invisible();
                var self = this;
                $('.nts-input').trigger("validate");
                _.defer(() => {
                    if (!$('.nts-editor').ntsError("hasError")) {
                        if (self.enableCode() == false) {
                            self.convertCode(self.divReasonCode());
                            //                        self.updateDivReason();
                            self.addDivReason();
                        } else
                            if (self.enableCode() == true) {//add divergence
                                self.addDivReason();
                            }
                    }
                });
            }


            //        addDivReason
            addDivReason() {
                var self = this;
                var dfd = $.Deferred();
                let command: any = {};
                service.addPattCalender(self.collectData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getAllDivReasonNew();
                    $("#inpPattern").focus();
                    nts.uk.ui.block.clear();
                }).fail(function(error) {
                    nts.uk.ui.block.clear();
                    $('#inpCode').ntsError('set', error);
                });
            }



            convertCode(value: string) {
                var self = this;
                if (value.length == 1) {
                    let code = '0' + value;
                    self.divReasonCode(code);
                }
                else self.divReasonCode(value);
            }

            updateDivReason() {
                var self = this;
                var dfd = $.Deferred();
                var divReason = new model.Item(self.patternCode(), self.patternName(), self.workTypeCodes(), self.workHouseCodes(), self.patternCalendarNumberDay());
                service.addPattCalender(divReason).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.getAllDivReasonNew();
                        nts.uk.ui.block.clear();
                    });;
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.reject(res);
                    nts.uk.ui.block.clear();
                });
            }


            //get all divergence reason new
            getAllDivReasonNew() {
                var self = this;
                var dfd = $.Deferred<any>();
                self.lstPattern();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
                    self.currentCode('');
                    self.lstPattern(dataRes);
                    self.enableCode(false);
                    self.currentCode(self.divReasonCode());
                    dfd.resolve();
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                })

                dfd.resolve();
                return dfd.promise();
            }




            //delete divergence reason
            //       deleteDivReason() {
            //            nts.uk.ui.block.invisible();
            //            var self = this;
            //            nts.uk.ui.dialog.confirm({messageId:'Msg_18'}).ifYes(function() {
            //                let divReason = self.itemDivReason();
            //                self.index_of_itemDelete = self.dataSource().indexOf(self.itemDivReason());
            //                service.deleteDivReason(divReason).done(function() {
            //                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function(){
            //                        nts.uk.ui.block.clear();
            //                        self.getDivReasonList_afterDelete();
            //                         $("#inpCode").focus();
            //                    });
            //                });
            //            }).ifNo(function() {
            //                nts.uk.ui.block.clear();
            //                return;
            //            })
            //        }

            //        //get list divergence reason after Delete 1 divergence reason
            //        getDivReasonList_afterDelete(): any {
            //            var self = this;
            //            var dfd = $.Deferred<any>();
            //            self.dataSource();
            //            service.getAllDivReason(self.divTimeId().toString()).done(function(lstDivReason: Array<model.Item>) {
            //                self.dataSource(lstDivReason);
            //
            //                if (self.dataSource().length > 0) {
            //                    if (self.index_of_itemDelete === self.dataSource().length) {
            //                        self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].divReasonCode)
            //                    } else {
            //                        self.currentCode(self.dataSource()[self.index_of_itemDelete].divReasonCode)
            //                    }
            //                } else {
            //            se;
            //                }
            //                dfd.resolve();
            //            }).fail(function(error) {
            //                nts.uk.ui.dialog.alert(error.message);
            //            })
            //            dfd.resolve();
            //            return dfd.promise();
            //        }



            //        closeDialog
            closeDialog() {
                nts.uk.ui.windows.close();
            }

            public collectData(): PatternCalendarDto {
                var self = this;
                var dto: PatternCalendarDto;
                dto = {
                    patternCode: self.patternCode(),
                    patternName: self.patternName(),
                    workTypeCodes: self.workTypeCodes(),
                    workHouseCodes: self.workHouseCodes(),
                    patternCalendarNumberDay: self.patternCalendarNumberDay()
                };
                return dto;
            }



        }
        export module model {
            export class Item {
                patternCode: number;

                patternName: string;

                workTypeCodes: string[];

                workHouseCodes: string[];

                patternCalendarNumberDay: number;


                constructor(patternCode: string, patternName: string, workTypeCodes: string[], workHouseCodes: string[], patternCalendarNumberDay: number) {
                    this.patternCode = patternCode;
                    this.patternName = patternName;
                    this.workTypeCodes = workTypeCodes;
                    this.workHouseCodes = workHouseCodes;
                    this.patternCalendarNumberDay = patternCalendarNumberDay;
                }
            }
        }
    }
}