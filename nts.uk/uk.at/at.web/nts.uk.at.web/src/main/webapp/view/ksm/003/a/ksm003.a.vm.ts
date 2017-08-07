module nts.uk.at.view.ksm003.a {
    // Import
    import DailyPatternItemDto = service.model.DailyPatternItemDto;
    import DailyPatternDetailDto = service.model.DailyPatternDetailDto;
    import DailyPatternValDto = service.model.DailyPatternValDto;

    export module viewmodel {
        export class ScreenModel {
            //Init
            columns: KnockoutObservableArray<any>;

            currentCode: KnockoutObservable<string>;
            enableCode: KnockoutObservable<boolean>;
            enableDel: KnockoutObservable<boolean>;
            checkModel: KnockoutObservable<boolean>;

            lstPattern: KnockoutObservableArray<DailyPatternItemDto>;
            detail: KnockoutObservable<model.DailyPatternDetailModel>;

            worktypeInfoWorkDays: KnockoutObservable<string>;
            worktimeInfoWorkDays: KnockoutObservable<string>;

            // Chua dung den (Check )
            patternValDays: KnockoutObservable<number>;
            workTypeSetCd: KnockoutObservable<string>;
            workingHoursCd: KnockoutObservable<string>;

            constructor() {

                var self = this;

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);

                self.currentCode = ko.observable('');
                self.enableCode = ko.observable(false);
                self.enableDel = ko.observable(true);
                self.checkModel = ko.observable(true);

                var dailyPatternVals: Array<model.DailyPatternValModel> = [];
                new model.DailyPatternValModel(1, "", "", null)

                self.detail = ko.observable(new model.DailyPatternDetailModel("", "", dailyPatternVals));

                self.lstPattern = ko.observableArray([]);

                self.workTypeSetCd = ko.observable('');
                self.workingHoursCd = ko.observable('');
                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');
                self.patternValDays = ko.observable(null);

                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged: string) {

                    //                    console.log(codeChanged);
                    self.clearError();
                    self.itemPatternCal(self.findItemPatternCal(codeChanged));
                    if (self.itemPatternCal() === undefined || self.itemPatternCal() == null) {
                        return;
                    }
                    self.enableCode(false);
                    self.patternCode(self.itemPatternCal().patternCode);
                    self.patternName(self.itemPatternCal().patternName);
                    self.itemPatternCal(self.findItemPatternCal(codeChanged));


                    self.enableDel(true);
                    //                    self.isBinding = false;
                    self.getPatternValByPatternCd(codeChanged);
                    if (self.dailyPatternVal() === undefined || self.dailyPatternVal() == null) {
                        return;
                    }
                    $("#inpPattern").focus();
                });

            }


            /**
             * start page
             * get all pattern 
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllPattCalender().done(function(dataRes: Array<DailyPatternItemDto>) {
                    //                    console.log(dataRes);
                    self.lstPattern(dataRes);
                    nts.uk.ui.block.clear();
                    self.currentCode(null);
                    if (dataRes === undefined || dataRes.length == 0) {
                        self.enableCode(true);
                        self.checkModel(false);
                        self.enableDel(false);
                    } else {
                        self.lstPattern(dataRes);
                        self.refreshData();
                        self.checkModel(true);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }




            /**
            * find item Pattern Cal is selected
            */
            private findItemPatternCal(value: string): any {
                let self = this;
                return _.find(self.lstPattern(), function(obj: model.DailyPatternItemDto) {
                    return obj.patternCode == value;
                })
            }


            public refreshData(): void {
                var self = this;
                //                self.dailyPatternVal([{"dispOrder": 1}, {"dispOrder": 2}, {"dispOrder": 3}, {"dispOrder": 4}, {"dispOrder": 5}, {"dispOrder": 6}, {"dispOrder": 7}, {"dispOrder": 8}, {"dispOrder": 9}, {"dispOrder": 10}]);
                self.dailyPatternVal([new model.DailyPatternValModel(1, "", "", null), new model.DailyPatternValModel(2, "", "", null), new model.DailyPatternValModel(3, "", "", null), new model.DailyPatternValModel(4, "", "", null), new model.DailyPatternValModel(5, "", "", null), new model.DailyPatternValModel(6, "", "", null), new model.DailyPatternValModel(7, "", "", null), new model.DailyPatternValModel(8, "", "", null), new model.DailyPatternValModel(9, "", "", null), new model.DailyPatternValModel(10, "", "", null)]);
                self.patternCode(null);
                self.patternName("");
                self.patternValDays(null);
                self.enableCode(true);
                self.clearError();
                self.enableDel(false);
                self.currentCode(null);
                $("#inpCode").focus();

            }


            private clearError(): void {
                if ($('.nts-editor').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }

            public registrationDivReason(): void {
                var self = this;
                if (self.patternCode == null || self.patternCode === undefined || self.patternCode() == "") {
                    nts.uk.ui.block.invisible();
                    $('.nts-input').trigger("validate");
                    let patternFirst = _.first(self.lstPattern());
                    self.currentCode(patternFirst.patternCode);
                    _.defer(() => {
                        if (!$('.nts-editor').ntsError("hasError")) {
                            nts.uk.ui.block.clear();
                            return;
                        }
                    });
                } else {
                    nts.uk.ui.block.invisible();
                    $('.nts-input').trigger("validate");
                    _.defer(() => {
                        if (!$('.nts-editor').ntsError("hasError")) {
                            if (self.enableCode() == false) {
                                self.addDailyPattern();
                            } else
                                if (self.enableCode() == true) {
                                    self.addDailyPattern();
                                }
                        }
                    });
                }
            }


            //        addDailyPattern
            private addDailyPattern() {
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

            public openDialogWorking(): void {
                let self = this;
                nts.uk.ui.windows.setShared('patternCode', self.patternCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
            }

            public openDialogKDL003(): void {
                let self = this;
                //                nts.uk.ui.windows.setShared('patternCode', self.patternCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml');
            }




            //get all divergence reason new
            private getAllDivReasonNew(): void {
                var self = this;
                var dfd = $.Deferred<any>();
                self.lstPattern();
                service.getAllPattCalender().done(function(dataRes: Array<model.DailyPatternItemDto>) {
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
            public deleteDivReason(): void {
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
            private getDivReasonList_afterDelete(): any {
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
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            public collectData(): DailyPatternDetailDto {
                var self = this;
                //                self.dailyPatternVal([
                //                    {
                //                        "cid": null,
                //                        "patternCode": "L9",
                //                        "dispOrder": 1,
                //                        "workTypeSetCd": "020",
                //                        "workingHoursCd": "020",
                //                        "days": 20
                //                    }
                //                ]);

                var dto: DailyPatternDetailDto;
                dto = {
                    patternCode: self.patternCode(),
                    patternName: self.patternName(),
                    listDailyPatternVal: self.dailyPatternVal()
                };
                //                console.log(dto);
                return dto;
            }

            /**
             * call service get by pattern code
             */

            public getPatternValByPatternCd(patternCode: string): void {
                var self = this;
                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {
                    self.dailyPatternVal([]);
                    //                    if (dataRes === undefined || dataRes == null) {
                    //                        return;
                    //                    } else {
                    //                    self.dailyPatternVal([{ "dispOrder": 1 }, { "dispOrder": 2 }, { "dispOrder": 3 }, { "dispOrder": 4 }, { "dispOrder": 5 }, { "dispOrder": 6 }, { "dispOrder": 7 }, { "dispOrder": 8 }, { "dispOrder": 9 }, { "dispOrder": 10 }]);
                    self.dailyPatternVal([new model.DailyPatternValModel(1, "", "", null), new model.DailyPatternValModel(2, "", "", null), new model.DailyPatternValModel(3, "", "", null), new model.DailyPatternValModel(4, "", "", null), new model.DailyPatternValModel(5, "", "", null), new model.DailyPatternValModel(6, "", "", null), new model.DailyPatternValModel(7, "", "", null), new model.DailyPatternValModel(8, "", "", null), new model.DailyPatternValModel(9, "", "", null), new model.DailyPatternValModel(10, "", "", null)]);

                    self.dailyPatternVal(self.merge(dataRes, self.dailyPatternVal()));
                    self.patternCode(patternCode);

                    //                    }
                });
            }

        }

        export module model {
            export class DailyPatternDetailModel {
                patternCode: KnockoutObservable<string>;

                patternName: KnockoutObservable<string>;

                dailyPatternVals: KnockoutObservableArray<DailyPatternValModel>;

                constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValModel>) {
                    this.patternCode = ko.observable(patternCode);
                    this.patternName = ko.observable(patternName);
                    this.dailyPatternVals = ko.observable(dailyPatternVals);
                }

                public toDto(): DailyPatternDetailDto {
                    var dailyPatternValDtos = [];
                    //                    this.dailyPatternVals. -> toDto
                    return new DailyPatternDetailDto(this.patternCode(), this.patternName, dailyPatternValDtos);
                }

            }

            export class DailyPatternValModel {
                dispOrder: number;
                workTypeSetCd: KnockoutObservable<string>;
                workingHoursCd: KnockoutObservable<string>;
                days: KnockoutObservable<number>;

                constructor(dispOrder: number, workTypeSetCd: string, workingHoursCd: string, days: number) {
                    this.dispOrder = dispOrder;
                    this.workTypeSetCd = ko.observable(workTypeSetCd);
                    this.workingHoursCd = ko.observable(workingHoursCd);
                    this.days = ko.observable(days);
                }

                public toDto(): DailyPatternValDto {
                    return new DailyPatternValDto((this.dispOrder, this.workTypeSetCd(), this.workingHoursCd(), this.days());
                }

                /**
                * open dialog KDL003 by Work Days
                */
                public openDialogWorkDays(): void {
                    var self = this;
                    //                if (!self.isBinding) {
                    //                    return;
                    //                }
                    nts.uk.ui.windows.setShared('parentCodes', {
                        selectWorkTypeCode: self.workTypeSetCd,
                        selectSiftCode: self.workingHoursCd
                    });

                    nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function() {
                        var childData = nts.uk.ui.windows.getShared('childData');
                        self.workTypeSetCd = childData.selectedWorkTimeCode;
                        if (childData.selectedWorkTimeCode) {
                            //                        self.worktypeInfoWorkDays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        }
                        else {
                            //                        self.worktypeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                        }
                        self.workingHoursCd = childData.selectedSiftCode;
                        if (childData.selectedSiftCode) {
                            //                        self.worktimeInfoWorkDays(childData.selectedSiftCode + ' ' + childData.selectedSiftName);
                        } else {
                            //                        self.worktimeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                        }

                    });

                }

            }

        }
    }
}