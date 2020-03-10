module nts.uk.at.view.ksu001.jb.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        selectedTab: KnockoutObservable<string> = ko.observable(getShared('dataForJB').selectedTab);
        workplaceName: KnockoutObservable<string> = ko.observable(getShared('dataForJB').workplaceName);
        workplaceCode: KnockoutObservable<string> = ko.observable(getShared('dataForJB').workplaceCode); 
        selectedLinkButton: KnockoutObservable<number> = ko.observable(getShared('dataForJB').selectedLinkButton);
        workplaceId: string = getShared('dataForJB').workplaceId;
        listWorkType: any[] = getShared('dataForJB').listWorkType;
        listWorkTime: any[] = getShared('dataForJB').listWorkTime;
        listPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        isVisibleWkpName: KnockoutObservable<boolean> = ko.observable(false);
        groupName: KnockoutObservable<string> = ko.observable('');
        note: KnockoutObservable<string> = ko.observable('');
        selectedGroupUsageAtr: KnockoutObservable<number> = ko.observable(0);
        contextMenu: Array<any>;
        textName: KnockoutObservable<string> = ko.observable(null);
        tooltip: KnockoutObservable<string> = ko.observable(null);
        dataWorkPairSet: KnockoutObservableArray<any> = ko.observableArray([]);
        source: KnockoutObservableArray<any> = ko.observableArray([]);
        isDeleteEnable: KnockoutObservable<boolean> = ko.observable(true);
        currentObject: KnockoutObservable<any> = ko.observable(null);
        isAllowCheckChanged: boolean = false;
        sourceEmpty: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
        dataSource: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        groupUsageAtr: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: 'する' },
            { code: 0, name: 'しない' }
        ]);
        textButtonArr: KnockoutObservableArray<any> = ko.observableArray([
            { name: ko.observable(nts.uk.resource.getText("ページ1", ['１'])), id: 0 },
            { name: ko.observable(nts.uk.resource.getText("ページ2", ['２'])), id: 1 },
            { name: ko.observable(nts.uk.resource.getText("ページ3", ['３'])), id: 2 },
            { name: ko.observable(nts.uk.resource.getText("ページ4", ['４'])), id: 3 },
            { name: ko.observable(nts.uk.resource.getText("ページ5", ['５'])), id: 4 },
            { name: ko.observable(nts.uk.resource.getText("ページ6", ['６'])), id: 5 },
            { name: ko.observable(nts.uk.resource.getText("ページ7", ['７'])), id: 6 },
            { name: ko.observable(nts.uk.resource.getText("ページ8", ['８'])), id: 7 },
            { name: ko.observable(nts.uk.resource.getText("ページ9", ['９'])), id: 8 },
            { name: ko.observable(nts.uk.resource.getText("ページ10", ['１０'])), id: 9 },
        ]);

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup.bind(self) },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1708"), action: self.remove }
            ];

            $("#test2").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
                self.dataWorkPairSet(data.data);
            });
        }

        init(): void {
            let self = this;

            if (self.selectedTab() === 'company') {
                self.isVisibleWkpName(false);
                $.when(self.getDataComPattern()).done(() => {
                    self.clickLinkButton(null, self.selectedLinkButton);
                 var test = _.map(data, "groupName")

                    
                });
            } else {
                self.isVisibleWkpName(true);
                $.when(self.getDataWkpPattern()).done(() => {
                    self.clickLinkButton(null, self.selectedLinkButton);
                    self.workplaceName();
                   // nts.uk.ui.windows.getSelf().setSize(400, 845);
                });
            }
        }

        /**
         * set selectedLinkButton
         * check changed of data to save or not
         * handle click button
         */
        clickLinkButton(element?: any, param?: any): void {
            $('input#textName').focus();
            let self = this, index: number = param();

            self.selectedLinkButton(index);
           /* if (self.isAllowCheckChanged && self.isChanged()) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_447" }).ifYes(() => {
                    $.when(self.saveData()).done(() => {
                        self.handleClickButton(index);
                    });
                }).ifNo(() => {
                    self.handleClickButton(index);
                });
            } else {
                self.handleClickButton(index);
           } */
             self.handleClickButton(index);
            self.isAllowCheckChanged = true;
        }

        /**
         * link button has color gray when clicked
         * set data for screen
         */
        handleClickButton(index): void {
            let self = this;
            //set enable/disable delete button
            self.isDeleteEnable(_.find(self.listPattern(), ['groupNo', index + 1]));
            nts.uk.ui.errors.clearAll();
            _.find($('#group-link-button-ja a.hyperlink.color-gray'), (a) => {
                $(a).removeClass('color-gray');
            });
            $($('a.hyperlink')[index]).addClass('color-gray');
            let pattern = _.find(self.listPattern(), ['groupNo', index + 1]);
            self.groupName(pattern ? pattern.groupName : null);
            self.selectedGroupUsageAtr(pattern ? pattern.groupUsageAtr : 0);
            self.note(pattern ? pattern.note : null);
            self.source(self.dataSource()[index] || self.sourceEmpty);
            self.currentObject({
                groupNo: index + 1,
                groupName: self.groupName(),
                selectedGroupUsageAtr: self.selectedGroupUsageAtr(),
                note: self.note(),
                source: self.source()
            });
        }

        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let positionButton = $(button).data().idx;
            let dt = self.source()[positionButton];
            $("#test2").trigger("getdatabutton", { text: dt.text, tooltip: dt.tooltip, data: dt.data });
            $("#popup-area").css('visibility', 'visible');
            let buttonWidth = button.outerWidth(true) - 30;
            $("#popup-area").position({ "of": button, my: "left+" + buttonWidth + " top", at: "left+" + buttonWidth + " top" });
            $("#test2").bind("namechanged", function(evt, data) {
                $("#test2").unbind("namechanged");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    dfd.resolve(data);
                } else {
                    dfd.resolve(button.parent().data("cell-data"));
                }
            });
            return dfd.promise();
        }

        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip(), data: self.dataWorkPairSet() });
        }

        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", undefined);
        }

        /** Clear all data of button table */
        clear() {
            let self = this;
            $("#test2").ntsButtonTable("dataSource", []);
            self.source([]);
        }

        closeDialog(): void {
            let self = this;
            setShared('dataFromJA', {
                selectedLinkButton: self.selectedLinkButton()
            });
            nts.uk.ui.windows.close();
        }

        openDialogJC(evt, data): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.textName(data ? data.text : null);
            self.tooltip(data ? data.tooltip : null);
            
            setShared("dataForJC", {
                text: self.textName(),
                data: data ? data.data : null,
                textDecision: nts.uk.resource.getText("KSU001_923"),
                listCheckNeededOfWorkTime: getShared("dataForJB").listCheckNeededOfWorkTime,
                selectedTab: getShared("dataForJB").selectedTab,
                workplaceId: getShared('dataForJB').workplaceId
            });
            
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jc/index.xhtml").onClosed(() => {
                let dataFromJB = getShared("dataFromJB");
                if (dataFromJB) {
                    self.textName(dataFromJB ? dataFromJB.text : self.textName());
                    self.tooltip(dataFromJB ? dataFromJB.tooltip : self.tooltip());
                    
                    dfd.resolve({ text: self.textName(), tooltip: self.tooltip(), data: dataFromJB.data });
                }
            });

            return dfd.promise();
        }

        saveData(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            
            if (nts.uk.ui.errors.hasError()) {
                 return;
              }
            //check soucre null or empty
             if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let isArrEmpty: boolean = true;
            _.map(self.source(), (item) => {
                if (!_.isEmpty(item)) {
                    isArrEmpty = false;
                    return;
                };
            });

            if (self.selectedGroupUsageAtr() == 0 && isArrEmpty) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1592" });
                dfd.resolve();
                return;
            }
            //if data is not changed, not save
            if (!self.isChanged()) {
                return;
            }

            nts.uk.ui.block.grayout();
            let listInsertPatternItemCommand = [];
            for (let i = 0; i < self.source().length; i++) {
                if (!_.isEmpty(self.source()[i])) {
                    let listInsertWorkPairSetCommand = [];
                    let j = 1;
                    _.each(self.source()[i].data, (dt) => {
                        listInsertWorkPairSetCommand.push({
                            pairNo: j, 
                            shiftCode: dt.value
                        });
                        j++;
                    });

                    listInsertPatternItemCommand.push({
                        patternNo: i + 1,
                        patternName: self.source()[i].text,
                        listInsertWorkPairSetCommand: listInsertWorkPairSetCommand
                    });
                }
            }

            let obj = {
                workplaceId: self.workplaceId,
                groupNo: self.currentObject().groupNo,
                groupName: self.groupName(),
                groupUsageAtr: self.selectedGroupUsageAtr(),
                note: self.note(),
                listInsertPatternItemCommand: listInsertPatternItemCommand
            }

            service.registerWorkPairPattern(obj).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.isAllowCheckChanged = false;
                self.handleAfterChangeData();
                self.isDeleteEnable(true);
                dfd.resolve();
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                dfd.reject();
            }).always(() => {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        deletePatternItem(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let obj = {
                    groupNo: self.selectedLinkButton() + 1,
                    workplaceId: self.workplaceId
                }

                service.deleteWorkPairPattern(obj).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    self.handleAfterChangeData();
                    self.isDeleteEnable(false);
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
            }).ifNo(() => {
                dfd.resolve();
                return;
            });


            return dfd.promise();
        }

        handleAfterChangeData(): void {
            let self = this;

            if (self.selectedTab() == 'company') {
                $.when(self.getDataComPattern()).done(() => {
                    self.clickLinkButton(null, self.selectedLinkButton);
                });
            } else {
                $.when(self.getDataWkpPattern()).done(() => {
                    self.clickLinkButton(null, self.selectedLinkButton);
                });
            }
        }

        /** remove data of button table */
        remove(): JQueryPromise<any> {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }

        /**
         * check data is changed or not
         * return true if data is changed
         * return false if data is not changed
         */
        isChanged(): boolean {
            let self = this;
            if (self.currentObject().groupName == self.groupName()
                && self.currentObject().selectedGroupUsageAtr == self.selectedGroupUsageAtr()
                && self.currentObject().note == self.note()
                && _.isEqual(self.currentObject().source, self.source())) {
                return false;
            }
            return true;
        }

        /** get data form COM_PATTERN */
        getDataComPattern(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getDataComPattern().done((data) => {
                self.listPattern(data);
                self.handleAfterGetData(self.listPattern());
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }

        /** get data form WKP_PATTERN */
        getDataWkpPattern(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getDataWkpPattern(self.workplaceId).done((data) => {
                self.listPattern(data);
                self.handleAfterGetData(self.listPattern());
                dfd.resolve();
            }).fail(function() {
                dfd.reject();
            });

            return dfd.promise();
        }

        handleAfterGetData(listPattern: any[]): any {
            let self = this;
            // set default for dataSource and textButtonArr 
            self.dataSource([null, null, null, null, null, null, null, null, null, null]);
            self.textButtonArr([
                { name: ko.observable(nts.uk.resource.getText("ページ1", ['１'])), id: 0 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ2", ['２'])), id: 1 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ3", ['３'])), id: 2 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ4", ['４'])), id: 3 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ5", ['５'])), id: 4 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ6", ['６'])), id: 5 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ7", ['７'])), id: 6 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ8", ['８'])), id: 7 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ9", ['９'])), id: 8 , formatter: _.escape},
                { name: ko.observable(nts.uk.resource.getText("ページ10", ['１０'])), id: 9 , formatter: _.escape},
            ]);

            for (let i = 0; i < listPattern.length; i++) {
                let source: any[] = _.clone(self.sourceEmpty);
                // Change text of linkbutton
                self.textButtonArr()[listPattern[i].groupNo - 1].name(nts.uk.text.padRight(listPattern[i].groupName, ' ', 6));
                // Get data for dataSource
                _.each(listPattern[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject = [];                    
                    _.forEach(pattItem.workPairSet, (wPSet) => {
                        self.selectedTab() === 'company'? arrPairShortName.push('[' + wPSet.shiftCode + ']')
                                                        : arrPairShortName.push('[' + wPSet.workTypeCode + ']');
                        arrPairObject.push({
                            index: self.selectedTab() === 'company'? wPSet.order : wPSet.pairNo,
                            value: self.selectedTab() === 'company'? wPSet.shiftCode : wPSet.workTypeCode
                        });
                    });
                    
                    // screen JA must not set symbol for arrPairObject
                    // set tooltip
                    let arrTooltipClone = _.clone(arrPairShortName);
                    for (let i = 7; i < arrTooltipClone.length; i += 7) {
                        arrPairShortName.splice(i, 0, 'lb');
                        i++;
                    }
                    let tooltip: string = arrPairShortName.join('→');
                    tooltip = tooltip.replace(/→lb/g, '\n');
                    // Insert data to source
                    source.splice(pattItem.patternNo - 1, 1, { text: text, tooltip: tooltip, data: arrPairObject });
                });
                self.dataSource().splice(listPattern[i].groupNo - 1, 1, source);
            }
        }
    }
}