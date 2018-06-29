module nts.uk.at.view.ksu001.o1.viewmodel {
    import getShare = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<any>;
        listWorkTime: KnockoutObservableArray<any>;
        selectedWorkTypeCode: KnockoutObservable<string>;
        selectedWorkTimeCode: KnockoutObservable<string>;
        time1: KnockoutObservable<string> = ko.observable('');
        time2: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);
        nameWorkTimeType: KnockoutComputed<ksu001.common.viewmodel.ExCell>;
        columnsWorkTime: KnockoutObservableArray<NtsGridListColumn>;
        listWorkTimeComboBox: KnockoutObservableArray<ksu001.common.viewmodel.WorkTime>;
        isEnableClearSearchButton: KnockoutObservable<boolean> = ko.observable(false);


        constructor() {
            let self = this;
            self.listWorkType = ko.observableArray(getShare("listWorkType"));
            self.listWorkTime = ko.observableArray(getShare("listWorkTime"));
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KSU001_71") },
                { code: '2', name: nts.uk.resource.getText("KSU001_72") }
            ]);
            self.listWorkTimeComboBox = ko.observableArray(self.listWorkTime());
            self.selectedWorkTypeCode = ko.observable(self.listWorkType()[0].workTypeCode);
            self.selectedWorkTimeCode = ko.observable(self.listWorkTimeComboBox()[0].codeName);

            self.columnsWorkTime = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSU001_1402"), key: 'workTimeCode', width: 70 },
                { headerText: nts.uk.resource.getText("KSU001_1403"), key: 'symbolName', width: 70 },
                { headerText: nts.uk.resource.getText("KSU001_1404"), key: 'name', width: 110 },
                { headerText: nts.uk.resource.getText("KSU001_1406"), key: 'timeZone1', width: 160 },
                { headerText: nts.uk.resource.getText("KSU001_1407"), key: 'timeZone2', width: 160 },
                { headerText: nts.uk.resource.getText("KSU001_1408"), key: 'note', width: 160 },
                { headerText: 'data-id', key: 'codeName', width: 160, hidden: true }
            ]);

            //get name of workType and workTime
            self.nameWorkTimeType = ko.pureComputed(() => {
                let workTypeName, workTypeCode, workTimeName, workTimeCode: string;
                let startTime, endTime: any;
                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
                    if (d) {
                        workTypeName = d.abbreviationName;
                        workTypeCode = d.workTypeCode;
                    } else {
                        workTypeName = null;
                        workTypeCode = null;
                    }

                    let workTimeCd: string = null;
                    if (self.selectedWorkTimeCode()) {
                        workTimeCd = self.selectedWorkTimeCode().slice(0, 3);
                    } else {
                        workTimeCd = self.selectedWorkTimeCode()
                    }

                    let c = _.find(self.listWorkTime(), ['workTimeCode', workTimeCd]);
                    if (c) {
                        workTimeName = c.abName;
                        workTimeCode = _.isEmpty(c.workTimeCode) ? null : c.workTimeCode;
                        startTime = c.startTime ? formatById("Clock_Short_HM", c.startTime) : '';
                        endTime = c.endTime ? formatById("Clock_Short_HM", c.endTime) : '';
                    } else {
                        workTimeName = null;
                        workTimeCode = null;
                        startTime = '';
                        endTime = '';
                    }
                }
                return new ksu001.common.viewmodel.ExCell({
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    startTime: startTime,
                    endTime: endTime
                });
            });

            self.nameWorkTimeType.subscribe(function(value) {
                //Paste data into cell (set-sticker-single)
                nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.$("#extable").exTable("stickData", value);
            });
        }

        /**
         * paste data on cell
         */
        pasteData(): void {
            if (nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.__viewContext.viewModel.viewA.selectedModeDisplay() == 1) {
                nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.$("#extable").exTable("updateMode", "stick");
                nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.$("#extable").exTable("stickMode", "single");
            }
        }

        /**
         * copy data on cell
         */
        copyData(): void {
            nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.$("#extable").exTable("updateMode", "copyPaste");
        }

        /**
         * undo data on cell
         */
        undoData(): void {
            nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.$("#extable").exTable("stickUndo");
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * search time
         */
        search(): void {
            let self = this;
            self.isEnableClearSearchButton(true);
            if (!self.time1() && !self.time2()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_53" });
                self.isEnableClearSearchButton(false);
                self.clear();
                return;
            }
            if (self.time1() && self.time2() && moment(self.time1(), 'HH:mm').isSameOrAfter(moment(self.time2(), 'HH:mm'))) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_54" });
                self.clear();
                return;
            }
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            self.listWorkTimeComboBox([]);
            _.forEach(self.listWorkTime(), (obj) => {
                let timezone1 = obj.timeZone1.split("～");
                let timezone2 = obj.timeZone2.split("～");
                if (self.time1() && self.time2()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone1[0]).asMinutes()
                            && moment.duration(self.time2()).asMinutes() == moment.duration(timezone1[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone2[0]).asMinutes()
                            && moment.duration(self.time2()).asMinutes() == moment.duration(timezone2[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }

                } else if (!self.time2()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone1[0]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time1()).asMinutes() == moment.duration(timezone2[0]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }

                } else if (!self.time1()) {
                    if (timezone1.length == 2) {
                        if (moment.duration(self.time2()).asMinutes() == moment.duration(timezone1[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                    if (timezone2.length == 2) {
                        if (moment.duration(self.time2()).asMinutes() == moment.duration(timezone2[1]).asMinutes()) {
                            self.listWorkTimeComboBox.push(obj);
                        }
                    }
                }
            });
            $("#single-list").focus();
        }

        /**
         * clear search time
         */
        clear(): void {
            let self = this;
            self.isEnableClearSearchButton(false);
            self.listWorkTimeComboBox([]);
            self.listWorkTimeComboBox(self.listWorkTime());
        }
    }
}