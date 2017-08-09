module nts.uk.at.view.ksm003.a {
    // Import
    import DailyPatternItemDto = service.model.DailyPatternItemDto;
    import DailyPatternDetailDto = service.model.DailyPatternDetailDto;
    import DailyPatternValDto = service.model.DailyPatternValDto;

    export module viewmodel {

        export class ScreenModel {
            //Init
            columns: KnockoutObservableArray<any>;

            selectedCode: KnockoutObservable<string>;
            enableCode: KnockoutObservable<boolean>;
            enableDel: KnockoutObservable<boolean>;
            checkModel: KnockoutObservable<boolean>;

            itemLst: KnockoutObservableArray<DailyPatternItemDto>;
            detail: KnockoutObservable<model.DailyPatternDetailModel>;

            worktypeInfoWorkDays: KnockoutObservable<string>;
            worktimeInfoWorkDays: KnockoutObservable<string>;

            // Chua dung den (Check )
            patternValDays: KnockoutObservable<number>;
            workTypeSetCd: KnockoutObservable<string>;
            workingHoursCd: KnockoutObservable<string>;

            // Dirty checker
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {

                var self = this;

                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);

                self.selectedCode = ko.observable('');
                self.enableCode = ko.observable(false);
                self.enableDel = ko.observable(true);
                self.checkModel = ko.observable(true);

                var dailyPatternVals: Array<model.DailyPatternValModel> = [];

                new model.DailyPatternValModel(1, "", "", null)

                self.detail = ko.observable(new model.DailyPatternDetailModel("", "", dailyPatternVals));

                self.itemLst = ko.observableArray([]);

                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');

                self.patternValDays = ko.observable(null);
                self.workTypeSetCd = ko.observable('');
                self.workingHoursCd = ko.observable('');

                //subscribe currentCode
                self.selectedCode.subscribe(function(codeChanged: string) {
                    self.clearError();

                    self.enableCode(false);
                    self.enableDel(true);

                    self.getPatternValByPatternCd(codeChanged);

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

                // Init
                self.loadAllDailyPatternItems().done(() => {
                    dfd.resolve();
                });

                return dfd.promise();
            }

            private loadAllDailyPatternItems(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();

                nts.uk.ui.block.invisible();

                service.getAllPatterns().done(function(dataRes: Array<DailyPatternItemDto>) {

                    self.itemLst(dataRes);

                    nts.uk.ui.block.clear();

                    if (dataRes === undefined || dataRes.length == 0) {
                        self.enableCode(true);
                        self.checkModel(false);
                        self.enableDel(false);
                    } else {
                        self.selectedCode(dataRes[0].patternCode);
                        self.checkModel(true);
                    }

                    dfd.resolve();
                })

                return dfd.promise();
            }

            public getPatternValByPatternCd(patternCode: string): void {
                var self = this;

                var dfd = $.Deferred();

                self.clearError();

                nts.uk.ui.block.invisible();

                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {

                    self.detail(new model.DailyPatternDetailModel(dataRes.patternCode, dataRes.patternName, dataRes.dailyPatternVals.map(function(item) {
                        return new model.DailyPatternValModel(item.dispOrder, item.workTypeSetCd, item.workingHoursCd, item.days);
                    })));

                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }

            // addDailyPattern
            public save() {
                let self = this;
                nts.uk.ui.block.invisible();

                if (!self.validate()) {
                    return;
                }

                service.saveDailyPattern(self.detail().toDto()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getPatternValByPatternCd(self.selectedCode());
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    $('#inpCode').ntsError('set', error);
                }).then(() => {
                    nts.uk.ui.block.clear();
                });
            }

            //delete divergence reason
            public delete() {
                let self = this;
                nts.uk.ui.block.invisible();

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {

                    service.deleteDailyPattern(self.selectedCode()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadAllDailyPatternItems();
                            nts.uk.ui.block.clear();
                        });
                    });

                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });
            }

            public refreshData(): void {
                var self = this;
                //                self.dailyPatternVal([{"dispOrder": 1}, {"dispOrder": 2}, {"dispOrder": 3}, {"dispOrder": 4}, {"dispOrder": 5}, {"dispOrder": 6}, {"dispOrder": 7}, {"dispOrder": 8}, {"dispOrder": 9}, {"dispOrder": 10}]);
                //                self.dailyPatternVal([new model.DailyPatternValModel(1, "", "", null), new model.DailyPatternValModel(2, "", "", null), new model.DailyPatternValModel(3, "", "", null), new model.DailyPatternValModel(4, "", "", null), new model.DailyPatternValModel(5, "", "", null), new model.DailyPatternValModel(6, "", "", null), new model.DailyPatternValModel(7, "", "", null), new model.DailyPatternValModel(8, "", "", null), new model.DailyPatternValModel(9, "", "", null), new model.DailyPatternValModel(10, "", "", null)]);
                //                self.patternCode(null);
                //                self.patternName("");
                self.enableCode(true);
                self.clearError();
                self.enableDel(false);
                $("#inpCode").focus();

            }

            private clearError(): void {
                if ($('.nts-input').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }

            private validate(): boolean {
                let self = this;
                $('.nts-editor').ntsEditor('validate');
                return $('.nts-input').ntsError('hasError');
            }

            public openDialogWorking(): void {
                let self = this;
                nts.uk.ui.windows.setShared('patternCode', self.selectedCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
            }

            public openDialogKDL003(): void {
                let self = this;
                //                nts.uk.ui.windows.setShared('patternCode', self.patternCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml');
            }

            //        closeDialog
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

        }

        export module model {
            var VAL_ROW_COUNT = 10;

            export class DailyPatternDetailModel {
                patternCode: KnockoutObservable<string>;

                patternName: KnockoutObservable<string>;

                dailyPatternVals: KnockoutObservable<Array<DailyPatternValModel>>;

                constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValModel>) {
                    this.patternCode = ko.observable(patternCode);
                    this.patternName = ko.observable(patternName);

                    // Always display 10 row.
                    for (let i = dailyPatternVals.length + 1; i < VAL_ROW_COUNT; i++) {
                        dailyPatternVals.push(new DailyPatternValModel(i, "", "", null));
                    }
                    this.dailyPatternVals = ko.observable(dailyPatternVals);
                }

                public toDto(): DailyPatternDetailDto {
                    return new DailyPatternDetailDto(this.patternCode(), this.patternName(), this.dailyPatternVals().map((item) => {
                        return item.toDto();
                    }));
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

                public fromDto(dto: DailyPatternValDto): DailyPatternValModel {
                    return new DailyPatternValModel(dto.dispOrder, dto.workTypeSetCd, dto.workingHoursCd, dto.days);
                }

                public toDto(): DailyPatternValDto {
                    return new DailyPatternValDto(this.dispOrder, this.workTypeSetCd(), this.workingHoursCd(), this.days());
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

                    nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function() {
                        var childData = nts.uk.ui.windows.getShared('childData');
                        self.workTypeSetCd(childData.selectedWorkTypeCode);
                        if (childData.selectedWorkTimeCode) {
                            //                        self.worktypeInfoWorkDays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        }
                        else {
                            //                        self.worktypeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                        }

                        self.workingHoursCd(childData.selectedWorkTimeCode);
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