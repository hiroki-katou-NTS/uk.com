module nts.uk.at.view.ksu001.ac.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        modeCompany: KnockoutObservable<boolean> = ko.observable(true);

        palletUnit: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText("Com_Company") },
            { code: 2, name: getText("Com_Workplace") }
        ]);
        selectedpalletUnit: KnockoutObservable<number>;
        overwrite: KnockoutObservable<boolean> = ko.observable(true);

        dataSourceCompany: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        dataSourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        sourceCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        sourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedButtonTableCompany: KnockoutObservable<any> = ko.observable({});
        selectedButtonTableWorkplace: KnockoutObservable<any> = ko.observable({});

        textName: KnockoutObservable<string> = ko.observable(null);
        tooltip: KnockoutObservable<string> = ko.observable(null);
        selectedLinkButtonCom: KnockoutObservable<number> = ko.observable(0);
        selectedLinkButtonWkp: KnockoutObservable<number> = ko.observable(0);
        listComPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        listWkpPattern: KnockoutObservableArray<any> = ko.observableArray([]);
        flag: boolean = true;
        indexLinkButtonCom: number = null;
        indexLinkButtonWkp: number = null;
        dataToStick: any = null;

        indexBtnSelected: number = 0;

        textButtonArrComPattern: KnockoutObservableArray<any> = ko.observableArray([
            { name: ko.observable(getText("KSU001_1603", ['１'])), id: 0 },
            { name: ko.observable(getText("KSU001_1603", ['２'])), id: 1 },
            { name: ko.observable(getText("KSU001_1603", ['３'])), id: 2 },
            { name: ko.observable(getText("KSU001_1603", ['４'])), id: 3 },
            { name: ko.observable(getText("KSU001_1603", ['５'])), id: 4 },
            { name: ko.observable(getText("KSU001_1603", ['６'])), id: 5 },
            { name: ko.observable(getText("KSU001_1603", ['７'])), id: 6 },
            { name: ko.observable(getText("KSU001_1603", ['８'])), id: 7 },
            { name: ko.observable(getText("KSU001_1603", ['９'])), id: 8 },
            { name: ko.observable(getText("KSU001_1603", ['１０'])), id: 9 },
        ]);

        textButtonArrWkpPattern: KnockoutObservableArray<any> = ko.observableArray([
            { name: ko.observable(getText("KSU001_1603", ['１'])), id: 0 },
            { name: ko.observable(getText("KSU001_1603", ['２'])), id: 1 },
            { name: ko.observable(getText("KSU001_1603", ['３'])), id: 2 },
            { name: ko.observable(getText("KSU001_1603", ['４'])), id: 3 },
            { name: ko.observable(getText("KSU001_1603", ['５'])), id: 4 },
            { name: ko.observable(getText("KSU001_1603", ['６'])), id: 5 },
            { name: ko.observable(getText("KSU001_1603", ['７'])), id: 6 },
            { name: ko.observable(getText("KSU001_1603", ['８'])), id: 7 },
            { name: ko.observable(getText("KSU001_1603", ['９'])), id: 8 },
            { name: ko.observable(getText("KSU001_1603", ['１０'])), id: 9 },
        ]);
        
        KEY : string = 'USER_INFOR';

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openDialog", text: getText("KSU001_1705"), action: self.openDialogJB.bind(self) },
                { id: "openPopup", text: getText("KSU001_1706"), action: self.openPopup.bind(self) },
                { id: "delete", text: getText("KSU001_1707"), action: self.remove.bind(self) }
            ];
            
            self.selectedpalletUnit = ko.observable(1);
            self.selectedpalletUnit.subscribe((newValue) => {
                if (newValue) {
                    uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                        let userInfor = JSON.parse(data);
                        userInfor.shiftPalletUnit = newValue;
                        uk.localStorage.setItemAsJson(self.KEY, userInfor);
                    });

                    self.initScreenQ();
                }
            });

            self.selectedButtonTableCompany.subscribe( (value) => {
                console.log(value);
                self.dataToStick = $("#tableButton").ntsButtonTable("getSelectedCells")[0] ? $("#tableButton").ntsButtonTable("getSelectedCells")[0].data.data : null;
                let arrDataToStick: any[] = _.map(self.dataToStick, 'data');
                $("#extable").exTable("stickData", arrDataToStick);
                self.indexBtnSelected = self.selectedButtonTableCompany().column + self.selectedButtonTableCompany().row * 10;
                
            });

            self.selectedButtonTableWorkplace.subscribe(function() {
                self.dataToStick = $("#tableButton").ntsButtonTable("getSelectedCells")[0] ? $("#tableButton").ntsButtonTable("getSelectedCells")[0].data.data : null;
                let arrDataToStick: any[] = _.map(self.dataToStick, 'data');
                $("#extable").exTable("stickData", arrDataToStick);
                self.indexBtnSelected = self.selectedButtonTableWorkplace().column + self.selectedButtonTableWorkplace().row * 10;
            });

            $("#tableButton").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });

            $("#tableButton").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });
            
        }

    

        /**
         * get content of link button
         */
        initScreenQ(): void {
            let self = this;
            if (self.selectedpalletUnit() === 1) {
                self.modeCompany(true);
                __viewContext.viewModel.viewA.getDataComPattern().done(() => {
                    console.log("get data com done");
                });
            } else {
                self.modeCompany(false);
                __viewContext.viewModel.viewA.getDataWkpPattern().done(() => {
                     console.log("get data workplace done");
                });
            }
        }

        /**
         * handle init
         * change text of linkbutton
         * set data for datasource
         */
        handleInitCom(listPattern: any, listTextButton: any, dataSource: any, index: any): any {
            let self = this;
            self.selectedLinkButtonCom = index;
            //set default for listTextButton and dataSource
            self.dataSourceCompany([null, null, null, null, null, null, null, null, null, null]);
            self.textButtonArrComPattern([
                { name: ko.observable(nts.uk.resource.getText("ページ1", ['１'])), id: 0, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ2", ['２'])), id: 1, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ3", ['３'])), id: 2, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ4", ['４'])), id: 3, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ5", ['５'])), id: 4, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ6", ['６'])), id: 5, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ7", ['７'])), id: 6, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ8", ['８'])), id: 7, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ9", ['９'])), id: 8, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ10", ['１０'])), id: 9, formatter: _.escape },
            ]);
            
            let textLinkButton = [];
            for (let i = 0; i < listPattern.length; i++) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                //change text of linkbutton
                self.textButtonArrComPattern()[listPattern[i].groupNo - 1].name(nts.uk.text.padRight(listPattern[i].groupName, ' ', 6));
                //set data for dataSource
                _.each(listPattern[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject = [];
                    _.forEach(pattItem.workPairSet, (wPSet) => {

                        let matchShiftWork = _.find(self.listShiftWork, ["shiftMasterCode", wPSet.shiftCode != null ? wPSet.shiftCode : wPSet.workTypeCode]);
                        let value = "";
                        if (self.selectedpalletUnit() === 1) {
                            let shortName = (matchShiftWork != null) ? '[' + matchShiftWork.shiftMasterName + ']' : '[' + wPSet.shiftCode + 'マスタ未登録]';
                            value = shortName;
                            arrPairShortName.push(shortName);
                        } else {
                            let shortName = (matchShiftWork != null) ? '[' + matchShiftWork.shiftMasterName + ']' : '[' + wPSet.workTypeCode + 'マスタ未登録]';
                            value = shortName;
                            arrPairShortName.push(shortName);
                        }
                        arrPairObject.push({
                            index: self.selectedpalletUnit() === 1 ? wPSet.order : wPSet.pairNo,
                            value: value,
                            shiftMasterCode: self.selectedpalletUnit() === 1 ? wPSet.shiftCode : wPSet.workTypeCode
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
                self.dataSourceCompany().splice(listPattern[i].groupNo - 1, 1, source);
            }
            
            self.clickLinkButton(null, self.selectedLinkButtonCom);
        }
        
        handleInitWkp(listPattern: any, listTextButton: any, dataSource: any, index: any): any {
            let self = this;
            self.selectedLinkButtonWkp = index;
            //set default for listTextButton and dataSource
            self.dataSourceWorkplace([null, null, null, null, null, null, null, null, null, null]);
            self.textButtonArrWkpPattern([
                { name: ko.observable(nts.uk.resource.getText("ページ1", ['１'])), id: 0, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ2", ['２'])), id: 1, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ3", ['３'])), id: 2, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ4", ['４'])), id: 3, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ5", ['５'])), id: 4, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ6", ['６'])), id: 5, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ7", ['７'])), id: 6, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ8", ['８'])), id: 7, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ9", ['９'])), id: 8, formatter: _.escape },
                { name: ko.observable(nts.uk.resource.getText("ページ10", ['１０'])), id: 9, formatter: _.escape },
            ]);

            for (let i = 0; i < listPattern.length; i++) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                //change text of linkbutton
                self.textButtonArrWkpPattern()[listPattern[i].groupNo - 1].name(nts.uk.text.padRight(listPattern[i].groupName, ' ', 6));

                //set data for dataSource
                _.each(listPattern[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject = [];
                    _.forEach(pattItem.workPairSet, (wPSet) => {
                        //                        self.selectedTab() === 'company' ? arrPairShortName.push('[' + wPSet.shiftCode + ']')
                        //                            : arrPairSt.workTypeCode + ']');

                        let matchShiftWork = _.find(self.listShiftWork, ["shiftMasterCode", wPSet.shiftCode != null ? wPSet.shiftCode : wPSet.workTypeCode]);
                        let value = "";
                        if (self.selectedpalletUnit() === 1 ) {
                            let shortName = (matchShiftWork != null) ? '[' + matchShiftWork.shiftMasterName + ']' : '[' + wPSet.shiftCode + 'マスタ未登録]';
                            value = shortName;
                            arrPairShortName.push(shortName);
                        } else {
                            let shortName = (matchShiftWork != null) ? '[' + matchShiftWork.shiftMasterName + ']' : '[' + wPSet.workTypeCode + 'マスタ未登録]';
                            value = shortName;
                            arrPairShortName.push(shortName);
                        }
                        arrPairObject.push({
                            index: self.selectedpalletUnit() === 1 ? wPSet.order : wPSet.pairNo,
                            value: value,
                            shiftMasterCode: self.selectedpalletUnit() === 1 ? wPSet.shiftCode : wPSet.workTypeCode
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
                self.dataSourceWorkplace().splice(listPattern[i].groupNo - 1, 1, source);
            }
             self.clickLinkButton(null, self.selectedLinkButtonWkp);
        }

        /**
         * Click to link button
         */
        clickLinkButton(element: any, param?: any): void {
            let self = this,
                source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
                index: number = param();
            
            uk.localStorage.getItem(self.KEY).ifPresent((data) => {
                let userInfor = JSON.parse(data);
                userInfor.shiftPalletPositionNumber = index;
                uk.localStorage.setItemAsJson(self.KEY, userInfor);
            });

            if (self.selectedpalletUnit() === 1) {
                self.indexLinkButtonCom = index;
                // link button has color gray when clicked
                _.each($('#group-link-button-ja a.hyperlink'), (a) => {
                    $(a).removeClass('color-gray');
                    $(a).removeClass('background-linkbtn');
                });
                $($('#group-link-button-ja a.hyperlink')[self.indexLinkButtonCom]).addClass('color-gray');
                $($('#group-link-button-ja a.hyperlink')[self.indexLinkButtonCom]).addClass('background-linkbtn');
                self.selectedLinkButtonCom(self.indexLinkButtonCom);
                //set sourceCompany
                self.sourceCompany(self.dataSourceCompany()[self.indexLinkButtonCom] || source);
                
            } else {
                self.indexLinkButtonWkp = index;
                // link button has color gray when clicked
                _.each($('#group-link-button-ja a.hyperlink'), (a) => {
                    $(a).removeClass('color-gray');
                    $(a).removeClass('background-linkbtn');
                });
                $($('#group-link-button-ja a.hyperlink')[self.indexLinkButtonWkp]).addClass('color-gray');
                $($('#group-link-button-ja a.hyperlink')[self.indexLinkButtonWkp]).addClass('background-linkbtn');
                self.selectedLinkButtonWkp(self.indexLinkButtonWkp);
                //set sourceWorkplace
                self.sourceWorkplace(self.dataSourceWorkplace()[self.indexLinkButtonWkp] || source);
            }
            
            // set css table button
            _.each($('.ntsButtonTableButton'), function(buttonTbl, index) {
                if ($('.ntsButtonTableButton')[index].innerHTML == "+") {
                    $($('.ntsButtonTableButton')[index]).addClass('nowithContent');
                } else {
                    $($('.ntsButtonTableButton')[index]).addClass('withContent');
                }
            });
        }

        /**
         * Open popup to change name button
         */
        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("#tableButton").trigger("getdatabutton", { text: button[0].innerText });
            $("#popup-area").css('visibility', 'visible');
            let buttonWidth = button.outerWidth(true) - 30;
            $("#popup-area").position({ "of": button, my: "left+" + buttonWidth + " top", at: "left+" + buttonWidth + " top" });
            $("#tableButton").bind("namechanged", function(evt, data) {
                $("#tableButton").unbind("namechanged");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    dfd.resolve(data);
                } else {
                    dfd.resolve(button.parent().data("cell-data"));
                }
                self.refreshDataSource();
            });
            return dfd.promise();
        }

        /**
         * decision change name button
         */
        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#tableButton").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip() });
        }

        /**
         * Close popup
         */
        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#tableButton").trigger("namechanged", undefined);
        }

        /**
         * Open dialog JB
         */
        openDialogJB1(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            setShared('dataForJB', {
                selectedTab: self.selectedpalletUnit() == 1 ? 'company' : 'workplace',
                workplaceName: '経理部職',
                workplaceCode: '0000000001',
                workplaceId: self.selectedpalletUnit() === 1 ? null : 'dea95de1-a462-4028-ad3a-d68b8f180412', // __viewContext.viewModel.viewA.workplaceId
                listWorkType: __viewContext.viewModel.viewO.listWorkType(),
                listWorkTime: __viewContext.viewModel.viewO.listWorkTime(),
                selectedLinkButton: self.selectedpalletUnit() === 1 ? self.selectedLinkButtonCom() : self.selectedLinkButtonWkp(),
                // listCheckNeededOfWorkTime for JA to JA send to JB
                listCheckNeededOfWorkTime: __viewContext.viewModel.viewA.listCheckNeededOfWorkTime(),
                overwrite : self.overwrite()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let selectedLinkButton: any = ko.observable(getShared("dataFromJA").selectedLinkButton);
                
                if (self.selectedpalletUnit() === 1) {
                    self.modeCompany(true);
                    __viewContext.viewModel.viewA.getDataComPattern(selectedLinkButton).done(() => {
                        console.log("get data com done");
                    });
                } else {
                    self.modeCompany(false);
                    __viewContext.viewModel.viewA.getDataWkpPattern(selectedLinkButton).done(() => {
                        console.log("get data workplace done");
                    });
                }
                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        /**
         * Open dialog JB
         */
        openDialogJB(evt, data): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            self.textName(data ? data.text : null);
            self.tooltip(data ? data.tooltip : null);
            setShared("dataForJB", {
                text: self.textName(),
                tooltip: self.tooltip(),
                data: data ? data.data : null,
                textDecision: getText("KSU001_924"),
                listCheckNeededOfWorkTime: __viewContext.viewModel.viewA.listCheckNeededOfWorkTime()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let data = getShared("dataFromJB");
                if (data) {
                    self.textName(data.text);
                    self.tooltip(data.tooltip);
                    let dataBasicSchedule = _.map(data.data, 'data');
                    //set symbol for object
                    $.when(__viewContext.viewModel.viewA.setDataToDisplaySymbol(dataBasicSchedule)).done(() => {
                        dfd.resolve({ text: self.textName(), tooltip: self.tooltip(), data: data.data });
                        self.refreshDataSource();
                        // neu buttonTable do dang dc select, set lai data cho dataToStick 
                        if (self.indexBtnSelected == $(evt).attr('data-idx')) {
                            $("#extable").exTable("stickData", dataBasicSchedule);
                        }
                    });
                }
            });
            return dfd.promise();
        }

        /**
         * remove button on table
         */
        remove(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
                self.refreshDataSource();
            }, 10);

            return dfd.promise();
        }

        /**
         * refresh dataSource
         */
        refreshDataSource(): void {
            let self = this;
            if (self.selectedpalletUnit() === 1) {
                self.dataSourceCompany()[self.indexLinkButtonCom] = self.sourceCompany();
            } else {
                self.dataSourceWorkplace()[self.indexLinkButtonWkp] = self.sourceWorkplace();
            }
        }
    }
}