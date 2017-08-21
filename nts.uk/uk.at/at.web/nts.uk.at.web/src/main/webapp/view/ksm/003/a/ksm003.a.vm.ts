module nts.uk.at.view.ksm003.a {
    // Import
    import DailyPatternItemDto = service.model.DailyPatternItemDto;
    import DailyPatternDetailDto = service.model.DailyPatternDetailDto;
    import DailyPatternValDto = service.model.DailyPatternValDto;
    import WorkTypeDto = service.model.WorkTypeDto;
    import WorkTimeDto = service.model.WorkTimeDto;

    export module viewmodel {

        export class ScreenModel {
            //Init
            columns: KnockoutObservableArray<any>;

            selectedCode: KnockoutObservable<string>;
            isEditting: KnockoutObservable<boolean>;

            itemLst: KnockoutObservableArray<DailyPatternItemDto>;
            detail: KnockoutObservable<model.DailyPatternDetailModel>;

            worktypeInfoWorkDays: KnockoutObservable<string>;
            worktimeInfoWorkDays: KnockoutObservable<string>;


            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {

                var self = this;

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);

                self.selectedCode = ko.observable('');
                self.isEditting = ko.observable(false);

                var dailyPatternVals: Array<model.DailyPatternValModel> = [];
                self.detail = ko.observable(new model.DailyPatternDetailModel("", "", dailyPatternVals));

                self.itemLst = ko.observableArray([]);
                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');

                //subscribe currentCode
                self.selectedCode.subscribe(function(codeChanged: string) {
                    if (codeChanged) {
                        self.getPatternValByPatternCd(codeChanged);
                    }

                });

            }

            /**
             * start page
             * get all pattern 
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                self.isEditting(false);
                // Init
                self.loadAllDailyPatternItems().done(() => {
                    if (self.itemLst().length > 0) {
                        self.selectedCode(self.itemLst()[0].patternCode);
                        $("#inpPattern").focus();
                    } else {
                        self.switchNewMode();
                    }
                    self.clearError();
                    dfd.resolve();
                });

                return dfd.promise();
            }

            // load all data Daily Pattern Items
            private loadAllDailyPatternItems(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.grayout();

                // get all data Pattern from service
                service.getAllPatterns().done(function(dataRes: Array<DailyPatternItemDto>) {

                    if (dataRes === undefined || dataRes.length == 0) {
                        self.itemLst([]);
                        self.switchNewMode();
                    } else {
                        self.itemLst(dataRes);
                    }

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }

            // get Pattern Val By PatternCd form database
            public getPatternValByPatternCd(patternCode: string): void {
                var self = this;

                var dfd = $.Deferred();

                self.clearError();

                // nts.uk.ui.block.grayout();

                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {
                    //getPatternValByPatternCd by patternCode  
                    if (dataRes !== undefined) {
                        self.detail(new model.DailyPatternDetailModel(dataRes.patternCode, dataRes.patternName, dataRes.dailyPatternVals.map(function(item) {
                            return new model.DailyPatternValModel(item.dispOrder, item.workTypeSetCd, item.workingHoursCd, item.days);
                        })));
                    }

                    //check start in Mode
                    if (self.itemLst().length <= 0 || self.selectedCode() == null) {
                        self.isEditting(false);
                        $("#inpCode").focus();
                    } else {
                        self.isEditting(true);
                        $("#inpPattern").focus();
                    }

                    //                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });
                return dfd.promise();
            }

            // save Daily Pattern in database
            public save() {
                let self = this;

                nts.uk.ui.block.grayout();
                self.detail().patternName($.trim(self.detail().patternName()));
                if (self.validate()) {
                    nts.uk.ui.block.clear();
                    return;
                }

                var detailDto = self.detail().toDto();
                detailDto.isEditting = self.isEditting();

                service.saveDailyPattern(detailDto).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.loadAllDailyPatternItems();
                    self.selectedCode(nts.uk.text.padLeft(self.detail().patternCode(), '0', 2));
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    if (error.messageId == 'Msg_31' || error.messageId == 'Msg_22' || error.messageId == 'Msg_23' || error.messageId == 'Msg_25' || error.messageId == 'Msg_435' || error.messageId == 'Msg_434') {
                        $('#days0').ntsError('set', error);
                    } else {
                        $('#inpCode').ntsError('set', error);
                    }
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            // delete Pattern
            public deletePattern() {
                let self = this;

                //                nts.uk.ui.block.grayout();

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    var dataHistory: DailyPatternItemDto[] = self.itemLst();
                    service.deleteDailyPattern(self.selectedCode()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadAllDailyPatternItems();

                            // check end visible
                            var indexSelected: number = 0;
                            if (self.itemLst()) {
                                for (var index: number = 0; index < dataHistory.length; index++) {
                                    if (dataHistory[index].patternCode == self.selectedCode()) {
                                        indexSelected = index;
                                        break;
                                    }
                                }
                            }

                            // check list control is 0   
                            if (self.itemLst() === undefined || self.itemLst().length == 0) {
                                self.itemLst([]);
                                self.switchNewMode()
                            }
                            // check next visible                            
                            else if (dataHistory[dataHistory.length - 1].patternCode == self.selectedCode()) {
                                self.selectedCode(self.itemLst()[self.itemLst().length - 2].patternCode);
                            }
                            // check previous visible
                            else if (dataHistory[dataHistory.length - 1].patternCode != self.selectedCode()) {
                                self.selectedCode(self.itemLst()[indexSelected + 1].patternCode);
                            }
                        });
                    }).always(function() {
                        //                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    //                    nts.uk.ui.block.clear();
                    return;
                });

            }

            //select switch New Mode
            public switchNewMode(): void {
                let self = this;
                var dataNew: model.DailyPatternValModel[];
                dataNew = new Array();
                for (let i = 0; i <= 9; i++) {
                    dataNew.push(new model.DailyPatternValModel(i, "", "", null));
                }

                self.detail(new model.DailyPatternDetailModel("", "", dataNew));
                self.isEditting(false);
                self.selectedCode(null);
                self.resetInput();
                $("#inpCode").focus();
                self.clearError();
            }

            //reset Input
            private resetInput(): void {
                $('.nts-input').val("");
            }

            // clear Error
            private clearError(): void {
                if ($('.nts-input').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }

            //validate form
            private validate(): boolean {
                let self = this;
                $('#inpCode').ntsEditor('validate');
                $('#inpPattern').ntsEditor('validate');
                self.detail().dailyPatternVals().forEach((item) => {
                    if (item.isSetting()) {
                        $('#days' + item.dispOrder).ntsEditor('validate');
                    }
                });
                return $('.nts-input').ntsError('hasError');
            }

            //click button open Dialog Working
            public openDialogWorking(): void {
                let self = this;
                nts.uk.ui.windows.setShared('patternCode', self.selectedCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml', { title: nts.uk.resource.getText('KDL023_1') });
            }

            public openDialogKDL003(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml', { title: nts.uk.resource.getText('KDL003_1') });
            }

            //        closeDialog
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

        }

        // define model     
        export module model {
            var VAL_ROW_COUNT = 10;
            // defile DailyPatternDetailModel
            export class DailyPatternDetailModel {
                patternCode: KnockoutObservable<string>;

                patternName: KnockoutObservable<string>;

                dailyPatternVals: KnockoutObservable<Array<DailyPatternValModel>>;

                constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValModel>) {
                    this.patternCode = ko.observable(patternCode);
                    this.patternName = ko.observable(patternName);

                    // Always display 10 row.
                    for (let i = 0; i <= 9; i++) {
                        if (dailyPatternVals.length != 0 && dailyPatternVals[i] != undefined && dailyPatternVals[i].dispOrder != i) {
                            dailyPatternVals.splice(i, 0, new DailyPatternValModel(i, "", "", null));
                        } else if (dailyPatternVals[i] == undefined) {
                            dailyPatternVals.splice(i, 0, new DailyPatternValModel(i, "", "", null));
                        }

                        // add workTypeName to List
                        if (dailyPatternVals[i].workTypeSetCd() && dailyPatternVals[i].workTypeSetCd() != '') {
                            service.findByIdWorkType(dailyPatternVals[i].workTypeSetCd()).done(function(workType) {
                                dailyPatternVals[i].setWorkTypeName(workType.name);
                            });
                        }

                        // add workHoursName to List
                        if (dailyPatternVals[i].workingHoursCd() && dailyPatternVals[i].workingHoursCd() != '') {
                            service.findByIdWorkType(dailyPatternVals[i].workingHoursCd()).done(function(workTime) {
                                if (workTime) {
                                    dailyPatternVals[i].setWorkTimeName(workTime.name);
                                }
                            });
                        }
                    }

                    this.dailyPatternVals = ko.observable(dailyPatternVals);
                }

                public toDto(): DailyPatternDetailDto {
                    return new DailyPatternDetailDto(this.patternCode(), this.patternName(), this.dailyPatternVals().map((item) => {
                        if (item.workTypeSetCd() || item.workingHoursCd() || item.days()) {
                            return item.toDto();
                        }
                    }));
                }

            }

            //define DailyPatternValModel
            export class DailyPatternValModel {
                dispOrder: number;
                workTypeSetCd: KnockoutObservable<string>;
                workTypeInfo: KnockoutObservable<string>;
                workingInfo: KnockoutObservable<string>;
                workingHoursCd: KnockoutObservable<string>;
                days: KnockoutObservable<number>;

                constructor(dispOrder: number, workTypeSetCd: string, workingHoursCd: string, days: number) {
                    this.dispOrder = dispOrder;
                    this.workTypeSetCd = ko.observable(workTypeSetCd);
                    this.workingHoursCd = ko.observable(workingHoursCd);
                    this.days = ko.observable(days);
                    this.workTypeInfo = ko.observable(workTypeSetCd);
                    this.workingInfo = ko.observable(workingHoursCd);
                }

                public setWorkTypeName(workTypeName: string): void {
                    this.workTypeInfo(this.workTypeSetCd() + ' ' + workTypeName);
                }

                public setWorkTimeName(workTimeName: string) {
                    this.workingInfo(this.workingHoursCd() + ' ' + workTimeName);
                }


                public toDto(): DailyPatternValDto {
                    return new DailyPatternValDto(this.dispOrder, this.workTypeSetCd(), this.workingHoursCd(), this.days());
                }

                public isSetting(): boolean {
                    if (this.workTypeSetCd() || this.workingHoursCd() || this.days()) {
                        return true;
                    }
                    return false;
                }

                /**
                * open dialog KDL003 by Work Days
                */
                public openDialogWorkDays(): void {
                    var self = this;

                    nts.uk.ui.windows.setShared('parentCodes', {
                        selectWorkTypeCode: self.workTypeSetCd,
                        selectSiftCode: self.workingHoursCd
                    });

                    nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml", { title: nts.uk.resource.getText('KDL003_1') }).onClosed(function() {
                        var childData = nts.uk.ui.windows.getShared('childData');
                        self.workTypeSetCd(childData.selectedWorkTypeCode);
                        self.workingHoursCd(childData.selectedWorkTimeCode);
                        self.setWorkTypeName(childData.selectedWorkTypeName);
                        self.setWorkTimeName(childData.selectedWorkTimeName);

                    });

                }

            }

        }
    }
}