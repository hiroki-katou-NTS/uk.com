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
            mainModel: KnockoutObservable<DailyPatternDetailModel>;
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
                //init model
                var dailyPatternVals: Array<DailyPatternValModel> = [];
                for (let i = 0; i <= 9; i++) {
                    dailyPatternVals.push(new DailyPatternValModel(null, "", "", null));
                }
                self.mainModel = ko.observable(new DailyPatternDetailModel("", "", dailyPatternVals));
                self.itemLst = ko.observableArray([]);
                //subscribe currentCode
                self.selectedCode.subscribe(function(codeChanged: string) {
                    self.mainModel().resetModel();
                    if (codeChanged) {
                        self.getPatternValByPatternCd(codeChanged);
                    } else {
                        self.isEditting(false);
                        self.clearError();
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
                        self.itemLst([]);
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
                let lstWorkType: Array<WorkTypeDto> = [];
                let lstWorkTime: Array<WorkTimeDto> = [];
                self.clearError();
                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {
                    if (dataRes !== undefined) {
                        self.isEditting(true);
                        //get list item 
                        let dailyPatternVals = dataRes.dailyPatternVals.map(function(item) {
                            return new DailyPatternValModel(item.dispOrder, item.workTypeSetCd, item.workingHoursCd, item.days);
                        })
                        //init list work type code 
                        let lstWorkTypeCode: Array<string> = [];
                        _.forEach(dailyPatternVals, (item, index) => {
                            if (item.workTypeSetCd() && item.workTypeSetCd() != '') {
                                lstWorkTypeCode.push(item.workTypeSetCd());
                            }
                        });
                        //find work type
                        service.findWorkTypes(lstWorkTypeCode).done((data: Array<WorkTypeDto>) => {
                            lstWorkType = data;
                            //init list work time code
                            let lstWorkTimeCode: Array<string> = [];
                            _.forEach(dailyPatternVals, (item, index) => {
                                if (item.workingHoursCd() && item.workingHoursCd() != '') {
                                    lstWorkTimeCode.push(item.workingHoursCd());
                                }
                            });
                            service.findWorkTimes(lstWorkTimeCode).done((data: Array<WorkTimeDto>) => {
                                lstWorkTime = data;
                                self.updateDataModel(dataRes, lstWorkType, lstWorkTime);
                            });
                        });
                        $("#inpPattern").focus();
                    }
                });
            }

            private updateDataModel(dataRes: DailyPatternDetailDto, lstWorkType: Array<WorkTypeDto>, lstWorkTime: Array<WorkTimeDto>) {
                let self = this;
                //sort list by order
                let lstVal: Array<DailyPatternValDto> = dataRes.dailyPatternVals;
                lstVal = _.sortBy(lstVal, item => item.dispOrder);
                dataRes.dailyPatternVals = lstVal;
                //bind item code name
                self.mainModel().patternCode(dataRes.patternCode);
                self.mainModel().patternName(dataRes.patternName);
                for (let k = 0; k < lstVal.length; k++) {
                    for (let i = 0; i <= 9; i++) {
                        if (lstVal[k].dispOrder == i) {
                            //set display order
                            //self.mainModel().dailyPatternVals()[k].dispOrder = i;
                            //set day
                            self.mainModel().dailyPatternVals()[i].days(lstVal[k].days);
                            let workTimeCode = lstVal[k].workingHoursCd;
                            let workTypeCode = lstVal[k].workTypeSetCd;
                            // add workTypeName to List
                            if (workTypeCode && workTypeCode != '') {
                                self.mainModel().dailyPatternVals()[i].workTypeSetCd(workTypeCode);
                                let workTypeName = _.find(lstWorkType, (i) => { return i.workTypeCode == workTypeCode }).name;
                                self.mainModel().dailyPatternVals()[i].setWorkTypeName(workTypeName);
                            }
                            // add workHoursName to List
                            if (workTimeCode && workTimeCode != '') {
                                self.mainModel().dailyPatternVals()[i].workingHoursCd(workTimeCode);
                                let workTimeName = _.find(lstWorkTime, (i) => { return i.code == workTimeCode }).name;
                                self.mainModel().dailyPatternVals()[i].setWorkTimeName(workTimeName);
                            }
                            break;
                        }
                    }

                }
            }
            // save Daily Pattern in database
            public save() {
                let self = this;

                nts.uk.ui.block.grayout();
                self.mainModel().patternName($.trim(self.mainModel().patternName()));
                if (self.validate()) {
                    nts.uk.ui.block.clear();
                    return;
                }

                var detailDto = self.mainModel().toDto();
                detailDto.isEditting = self.isEditting();

                service.saveDailyPattern(detailDto).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.loadAllDailyPatternItems();
                    self.selectedCode(nts.uk.text.padLeft(self.mainModel().patternCode(), '0', 2));
                    self.isEditting(true);
                }).fail(function(res) {
                    if (res.messageId == "Msg_3") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_3" });
                    } else if (res.messageId == "Msg_23") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_23" });
                    } else if (res.messageId == "Msg_24") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_24" });
                    } else if (res.messageId == "Msg_25") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_25" });
                    } else if (res.messageId == "Msg_389") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_389" });
                    } else if (res.messageId == "Msg_390") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_390" });
                    } else if (res.messageId == "Msg_416") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_416" });
                    } else if (res.messageId == "Msg_417") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_417" });
                    } else if (res.messageId == "Msg_434") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_434" });
                    } else if (res.messageId == "Msg_435") {
                        $('#inpCode').ntsError('set', { messageId: "Msg_435" });
                    } else {
                        nts.uk.ui.dialog.alertError(res.message);
                    }
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            // delete Pattern
            public deletePattern() {
                let self = this;

                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    var dataHistory: DailyPatternItemDto[] = self.itemLst();

                    nts.uk.ui.block.grayout();

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
                            if (nts.uk.util.isNullOrEmpty(self.itemLst())) {
                                self.itemLst([]);
                                self.switchNewMode()
                            }
                            // check next visible                            
                            else if (dataHistory[dataHistory.length - 1].patternCode == self.selectedCode()) {
                                if (self.itemLst()[self.itemLst().length - 2]) {
                                    self.selectedCode(self.itemLst()[self.itemLst().length - 2].patternCode);
                                }
                            }
                            // check previous visible
                            else if (dataHistory[dataHistory.length - 1].patternCode != self.selectedCode()) {
                                self.selectedCode(self.itemLst()[indexSelected + 1].patternCode);
                            }
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(() => { nts.uk.ui.block.clear(); });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                });

            }
            /**
             * export excel
             */
            exportExcel(){
                nts.uk.at.view.ksm003.a.service.exportExcel().done(function(data) {
                }).fail(function(res: any) {
                    nts.uk.ui.dialog.alertError(res).then(function() { nts.uk.ui.block.clear(); });
                }).always(()=>{
                    nts.uk.ui.block.clear;
                });
            }

            //select switch New Mode
            public switchNewMode(): void {
                let self = this;
                self.isEditting(false);
                self.selectedCode("");
                self.mainModel().patternName("");
                self.mainModel().resetModel();
                $("#inpCode").focus();
                self.clearError();
            }

            // clear Error
            private clearError(): void {
                if ($('.nts-editor').ntsError("hasError")) {
                    $('.nts-editor').ntsError('clear');
                }
                if ($('.buttonEvent').ntsError("hasError")) {
                    $('.buttonEvent').ntsError('clear');
                }
            }

            //validate form
            private validate(): boolean {
                let self = this;
                $('#inpCode').ntsEditor('validate');
                $('#inpPattern').ntsEditor('validate');

                if (nts.uk.util.isNullOrEmpty(self.mainModel().dailyPatternVals())) {
                    $('#days1').ntsError('set', { messageId: "Msg_31" });
                }

                self.mainModel().dailyPatternVals().filter(i => i.isSetting()).forEach((item) => {
                    $('#days' + item.dispOrder).ntsEditor('validate');

                    if (!nts.uk.text.isNullOrEmpty(item.days()) && nts.uk.text.isNullOrEmpty(item.workTypeSetCd())) {
                        $('#btnVal' + item.dispOrder).ntsError('set', { messageId: "Msg_22" });
                    }

                    if (nts.uk.text.isNullOrEmpty(item.days()) && !nts.uk.text.isNullOrEmpty(item.workTypeSetCd())) {
                        $('#days' + item.dispOrder).ntsError('set', { messageId: "Msg_25" });
                    }
                });

                return $('.nts-input').ntsError('hasError') || $('.buttonEvent').ntsError('hasError');
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
        }

        export class DailyPatternDetailModel {
            patternCode: KnockoutObservable<string>;
            patternName: KnockoutObservable<string>;
            dailyPatternVals: KnockoutObservableArray<DailyPatternValModel>;
            constructor(patternCode: string, patternName: string, dailyPatternVals: Array<DailyPatternValModel>) {
                let self = this;
                self.patternCode = ko.observable(patternCode);
                self.patternName = ko.observable(patternName);
                self.dailyPatternVals = ko.observableArray(dailyPatternVals);
            }

            public toDto(): DailyPatternDetailDto {
                let lstVal: Array<DailyPatternValDto> = this.dailyPatternVals().map((item, index) => {
                    if (item.workTypeSetCd() || item.workingHoursCd() || item.days()) {
                        return item.toDto();
                    }
                }).filter(function(el) {
                    return el != null;
                });
                return new DailyPatternDetailDto(nts.uk.text.padLeft(this.patternCode(), '0', 2), this.patternName(), lstVal);
            }

            public resetModel() {
                let self = this;
                self.patternCode("");
                _.forEach(self.dailyPatternVals(), (item, index) => {
                    item.resetModel(index);
                });
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
            isSetting: KnockoutComputed<boolean>;

            constructor(dispOrder: number, workTypeSetCd: string, workingHoursCd: string, days: number) {
                this.dispOrder = dispOrder;
                this.workTypeSetCd = ko.observable(workTypeSetCd);
                this.workingHoursCd = ko.observable(workingHoursCd);
                this.days = ko.observable(days);
                this.workTypeInfo = ko.observable(workTypeSetCd);
                this.workingInfo = ko.observable(workingHoursCd);
                this.isSetting = ko.computed(() => {
                    if (this.workTypeSetCd() || this.workingHoursCd() || this.days()) {
                        return true;
                    }
                    return false;
                });
            }

            public resetModel(displayOrder: number) {
                this.dispOrder = displayOrder;
                this.workTypeSetCd("");
                this.workingHoursCd("");
                this.days(null);
                this.workTypeInfo("");
                this.workingInfo("");
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
                    console.log(childData);
                    if (childData) {
                        self.workTypeSetCd(childData.selectedWorkTypeCode);
                        self.workingHoursCd(childData.selectedWorkTimeCode);
                        self.setWorkTypeName(childData.selectedWorkTypeName);
                        self.setWorkTimeName(childData.selectedWorkTimeName);
                        if ($('.nts-editor').ntsError("hasError")) {
                            $('.nts-editor').ntsError('clear');
                        }
                        if ($('.buttonEvent').ntsError("hasError")) {
                            $('.buttonEvent').ntsError('clear');
                        }
                    }
                });

            }

        }
    }
}