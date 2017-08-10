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
            isEditting: KnockoutObservable<boolean>;

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
                self.isEditting = ko.observable(false);

                var dailyPatternVals: Array<model.DailyPatternValModel> = [];
                self.detail = ko.observable(new model.DailyPatternDetailModel("", "", dailyPatternVals));

                self.itemLst = ko.observableArray([]);

                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');

                self.patternValDays = ko.observable(null);
                self.workTypeSetCd = ko.observable('');
                self.workingHoursCd = ko.observable('');

                //subscribe currentCode
                self.selectedCode.subscribe(function(codeChanged: string) {
                    self.getPatternValByPatternCd(codeChanged);
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
                    if (self.itemLst().length > 0) {
                        self.selectedCode(self.itemLst()[0].patternCode);
                    }
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
                })

                return dfd.promise();
            }

            // get Pattern Val By PatternCd form database
            public getPatternValByPatternCd(patternCode: string): void {
                var self = this;

                var dfd = $.Deferred();

                self.clearError();

                // nts.uk.ui.block.grayout();

                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {

                    self.detail(new model.DailyPatternDetailModel(dataRes.patternCode, dataRes.patternName, dataRes.dailyPatternVals.map(function(item) {
                        return new model.DailyPatternValModel(item.dispOrder, item.workTypeSetCd, item.workingHoursCd, item.days);
                    })));

                    self.isEditting(true);

//                    nts.uk.ui.block.clear();

                    dfd.resolve();
                });

                return dfd.promise();
            }

            // save Daily Pattern in database
            public save() {
                let self = this;

                nts.uk.ui.block.grayout();

                if (self.validate()) {
                    nts.uk.ui.block.clear();
                    return;
                }

                var detailDto = self.detail().toDto();
                detailDto.isEditting = self.isEditting();

                service.saveDailyPattern(detailDto).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.loadAllDailyPatternItems();
                    self.selectedCode(self.detail().patternCode());
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    $('#inpCode').ntsError('set', error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            
            // delete Pattern
            public deletePattern() {
                let self = this;

                nts.uk.ui.block.grayout();

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    var dataHistory: DailyPatternItemDto[] = self.itemLst();
                    service.deleteDailyPattern(self.selectedCode()).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            self.loadAllDailyPatternItems();
                            
                            // check end visible
                            var indexSelected: number = 0;
                            if(self.itemLst()){
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
                            else if( dataHistory[dataHistory.length -1].patternCode ==  self.selectedCode()){
                                self.selectedCode(self.itemLst()[self.itemLst().length - 2].patternCode);    
                            }
                            // check previous visible
                            else if(dataHistory[dataHistory.length -1].patternCode !=  self.selectedCode()){
                                self.selectedCode(self.itemLst()[indexSelected +1].patternCode);    
                            }
                        });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });

            }
            
            //select switch New Mode
            public switchNewMode(): void {
                var self = this;
                self.detail(new model.DailyPatternDetailModel("", "", []));
                self.isEditting(false);
                self.selectedCode(null);
                self.clearError();
                $("#inpCode").focus();
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
                $('#inpPar').ntsEditor('validate');
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
                nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml', {title: nts.uk.resource.getText('KDL023_1') }); 
            }

            public openDialogKDL003(): void {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml', {title: nts.uk.resource.getText('KDL003_1') });
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
                    for (let i = dailyPatternVals.length + 1; i < VAL_ROW_COUNT; i++) {
                        dailyPatternVals.push(new DailyPatternValModel(i, "", "", null));
                    }
                    this.dailyPatternVals = ko.observable(dailyPatternVals);
                }

                public toDto(): DailyPatternDetailDto {
                    return new DailyPatternDetailDto(this.patternCode(), this.patternName(), this.dailyPatternVals().map((item) => {
                        if (item.workTypeSetCd() && item.workingHoursCd() && item.days()) {
                            return item.toDto();
                        }
                    }));
                }

            }
            
            //define DailyPatternValModel
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

                    nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml", {title: nts.uk.resource.getText('KDL003_1') }).onClosed(function() {
                        var childData = nts.uk.ui.windows.getShared('childData');
                        self.workTypeSetCd(childData.selectedWorkTypeCode);
                        self.workingHoursCd(childData.selectedWorkTimeCode);

                        // TODO: ???????????
                        if (childData.selectedWorkTimeCode) {
                            //                        self.worktypeInfoWorkDays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                        }
                        else {
                            //                        self.worktypeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                        }

                        // TODO: ???????????
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