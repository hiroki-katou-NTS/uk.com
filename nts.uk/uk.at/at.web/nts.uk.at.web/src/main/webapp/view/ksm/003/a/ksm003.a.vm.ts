module nts.uk.at.view.ksm003.a {
    import PatternCalendarDto = service.model.PatternCalendarDto;
    import DailyPatternVal = service.model.DailyPatternVal;


    export module viewmodel {
        export class ScreenModel {
            //A_label_x
            columns: KnockoutObservableArray<any>;
            columnsWork: KnockoutObservableArray<any>;
            dailyPatternVal: KnockoutObservableArray<DailyPatternVal>;
            lstPattern: KnockoutObservableArray<model.Item>;
            currentCode: KnockoutObservable<string>;
            enableCode: KnockoutObservable<boolean>;
            itemPatternCal: KnockoutObservable<model.Item>;
            objectOld: any;
            enableDel: KnockoutObservable<boolean>;
            checkModel: KnockoutObservable<boolean>;
            patternCalendar: KnockoutObservable<model.Item>;
            patternCode: KnockoutObservable<string>;
            patternName: KnockoutObservable<string>;
            itemDailyPatternVal: KnockoutObservableArray<DailyPatternVal>;
            workTypeSetCd: KnockoutObservable<string>;
            workingHoursCd: KnockoutObservable<string>;
            patternCalendarNumberDay: KnockoutObservable<number>;
            constructor() {

                var self = this;
                self.currentCode = ko.observable('');
                self.dailyPatternVal = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);
                self.columnsWork = ko.observableArray([
                    { headerText: '', key: 'btOpenWorkSelection', template: "<button class=\"button separate-button fix-button\">" + nts.uk.resource.getText('KSM003_34') + " </button>", formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_30'), key: 'workTypeSetCd', formatter: _.escape, width: 180 },
                    { headerText: nts.uk.resource.getText('KSM003_31'), key: 'workingHoursCd', formatter: _.escape, width: 150 },
                    { headerText: nts.uk.resource.getText('KSM003_32'), template: "<input type=\"text\" value=\"${days}\" data-id=\"${days}\" />", key: 'days', formatter: _.escape, width: 30 }
                    //                   <input class=\"nts-input\" data-bind=\"ntsTextEditor: { name: '#[KSM003_32]', value: \"${days}\" , constraint: 'DiverdenceReasonCode', enable:enableCode, required: true}\" />
                ]);
                //               template: "<input type=\"text\" value=\"${days}\" data-id=\"${days}\" />"
                self.lstPattern = ko.observableArray([]);
                self.enableCode = ko.observable(false);
                self.itemPatternCal = ko.observable(null);

                self.enableDel = ko.observable(true);
                self.checkModel = ko.observable(true);
                self.patternCode = ko.observable(null);
                self.patternName = ko.observable('');
                self.itemDailyPatternVal = ko.observableArray([]);
                self.workTypeSetCd = ko.observable('');
                self.workingHoursCd = ko.observable('');
                self.patternCalendarNumberDay = ko.observable(null);

                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged: string) {

                    console.log(codeChanged);
                    self.clearError();
                    self.itemPatternCal(self.findItemPatternCal(codeChanged));
                    if (self.itemPatternCal() === undefined || self.itemPatternCal() == null) {
                        return;
                    }
                    self.objectOld = self.itemPatternCal().patternCode + self.itemPatternCal().patternName + self.itemPatternCal().itemDailyPatternVal;
                    self.enableCode(false);
                    self.patternCode(self.itemPatternCal().patternCode);
                    self.patternName(self.itemPatternCal().patternName);
                    self.enableDel(true);
                    self.getPatternValByPatternCd(codeChanged);
                    if (self.dailyPatternVal() === undefined || self.dailyPatternVal() == null) {
                        return;
                    }
                    $("#inpPattern").focus();
                });

                self.dailyPatternVal.subscribe(function(dataRes: DailyPatternVal[]) {
                    console.log('Change');
                    console.log(dataRes);
                });
            }




            /**
             * start page
             * get all pattern 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
                    console.log(dataRes);
                    self.lstPattern(dataRes);
                    nts.uk.ui.block.clear();
                    self.currentCode(null);
                    if (dataRes === undefined || dataRes.length == 0) {
                        self.dailyPatternVal([]);
                        self.enableCode(true);
                        self.checkModel(false);
                        self.enableDel(false);
                    } else {
                        self.lstPattern(dataRes);
                        let patternFirst = _.first(dataRes);
                        self.currentCode(patternFirst.patternCode);
                        self.checkModel(true);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }



            /**
            * find item Pattern Cal is selected
            */
            findItemPatternCal(value: string): any {
                let self = this;
                var itemModel = null;
                return _.find(self.lstPattern(), function(obj: model.Item) {
                    return obj.patternCode == value;
                })
            }

            refreshData() {
                var self = this;
                self.patternCode(null);
                self.patternName("");
                self.enableCode(true);
                self.clearError();
                self.enableDel(false);
                self.currentCode(null);
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
                            self.addDivReason();
                        } else
                            if (self.enableCode() == true) {
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



            //get all divergence reason new
            getAllDivReasonNew() {
                var self = this;
                var dfd = $.Deferred<any>();
                self.lstPattern();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
                    self.currentCode('');
                    self.lstPattern(dataRes);
                    self.enableCode(false);
                    self.currentCode(self.patternCode());
                    dfd.resolve();
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                })

                dfd.resolve();
                return dfd.promise();
            }

            //delete divergence reason
            deleteDivReason() {
                nts.uk.ui.block.invisible();
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let patternCal = self.itemPatternCal();
                    service.deleteDailyPattern(patternCal.patternCode).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            nts.uk.ui.block.clear();
                            self.getDivReasonList_afterDelete();
                            $("#inpCode").focus();
                        });
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }

            // get list pattern after Delete 1 pattern
            getDivReasonList_afterDelete(): any {
                var self = this;
                var dfd = $.Deferred<any>();
                self.dailyPatternVal();
                self.getAllDivReasonNew();
                $("#inpPattern").focus();
                nts.uk.ui.block.clear();
                dfd.resolve();
                return dfd.promise();
            }



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
                    listDailyPatternVal: self.dailyPatternVal()
                };
                console.log(dto);
                return dto;
            }

            /**
             * call service get by pattern code
             */

            public getPatternValByPatternCd(patternCode: string) {
                var self = this;
                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {
                    console.log(dataRes);
                    self.dailyPatternVal([]);
                    if (dataRes === undefined || dataRes == null) {
                        return;
                    } else {

                        self.dailyPatternVal(dataRes);
                        self.patternCode(patternCode);
                    }
                });
            }



        }
        export module model {
            export class Item {
                patternCode: string;

                patternName: string;

                itemDailyPatternVal: ItemDailyPatternVal[];

                constructor(patternCode: string, patternName: string, itemDailyPatternVal: ItemDailyPatternVal[]) {
                    this.patternCode = patternCode;
                    this.patternName = patternName;
                    this.itemDailyPatternVal = itemDailyPatternVal;
                }
            }

        }
    }
}