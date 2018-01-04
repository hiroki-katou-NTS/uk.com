module nts.uk.at.view.ksu001.q.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        selectedTab: KnockoutObservable<string> = ko.observable('company');
        contextMenu: Array<any>;
        dataSourceCompany: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        dataSourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([null, null, null, null, null, null, null, null, null, null]);
        sourceCompany: KnockoutObservableArray<any> = ko.observableArray([]);
        sourceWorkplace: KnockoutObservableArray<any> = ko.observableArray([]);
        checked: KnockoutObservable<boolean> = ko.observable(false);
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
        selectedButtonTableCompany: KnockoutObservable<any> = ko.observable({});
        selectedButtonTableWorkplace: KnockoutObservable<any> = ko.observable({});

        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel> = ko.observableArray([
            { id: 'company', title: nts.uk.resource.getText("Com_Company"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'workplace', title: nts.uk.resource.getText("Com_Workplace"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
        ]);

        textButtonArrComPattern: KnockoutObservableArray<any> = ko.observableArray([
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１'])), id: 0 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['２'])), id: 1 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['３'])), id: 2 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['４'])), id: 3 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['５'])), id: 4 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['６'])), id: 5 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['７'])), id: 6 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['８'])), id: 7 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['９'])), id: 8 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１０'])), id: 9 },
        ]);

        textButtonArrWkpPattern: KnockoutObservableArray<any> = ko.observableArray([
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１'])), id: 0 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['２'])), id: 1 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['３'])), id: 2 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['４'])), id: 3 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['５'])), id: 4 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['６'])), id: 5 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['７'])), id: 6 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['８'])), id: 7 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['９'])), id: 8 },
            { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１０'])), id: 9 },
        ]);

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "openDialog", text: nts.uk.resource.getText("KSU001_1705"), action: self.openDialogJB.bind(self) },
                { id: "openPopup", text: nts.uk.resource.getText("KSU001_1706"), action: self.openPopup.bind(self) },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1707"), action: self.remove.bind(self) }
            ];

            self.selectedTab.subscribe((newValue) => {
                if (newValue === 'workplace' && self.flag) {
                    self.init();
                    self.flag = false;
                }
            });

            self.selectedButtonTableCompany.subscribe(function() {
                self.dataToStick = $("#test1").ntsButtonTable("getSelectedCells")[0] ? $("#test1").ntsButtonTable("getSelectedCells")[0].data.data : null;
                let arrDataToStick: any[] = [];
                _.map(self.dataToStick, (data: any) => {
                    arrDataToStick.push(data.data);
                });
                $("#extable").exTable("stickData", arrDataToStick);
            });

            self.selectedButtonTableWorkplace.subscribe(function() {
                self.dataToStick = $("#test2").ntsButtonTable("getSelectedCells")[0] ? $("#test2").ntsButtonTable("getSelectedCells")[0].data.data : null;
                let arrDataToStick: any[] = [];
                _.map(self.dataToStick, (data: any) => {
                    arrDataToStick.push(data.data);
                });
                $("#extable").exTable("stickData", arrDataToStick);
            });

            $("#test1").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });

            $("#test2").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
            });
        }

        /**
         * get content of link button
         */
        init(): void {
            let self = this;
            if (self.selectedTab() === 'company') {
                self.handleInit(self.listComPattern(), self.textButtonArrComPattern, self.dataSourceCompany);
            } else {
                self.handleInit(self.listWkpPattern(), self.textButtonArrWkpPattern, self.dataSourceWorkplace);
            }
            //select first link button
            self.clickLinkButton(null, ko.observable(0));
        }

        /**
         * handle init
         * change text of linkbutton
         * set data for datasource
         */
        handleInit(listPattern: any, listTextButton: any, dataSource: any): any {
            //set default for listTextButton and dataSource
            listTextButton([
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１'])), id: 0 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['２'])), id: 1 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['３'])), id: 2 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['４'])), id: 3 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['５'])), id: 4 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['６'])), id: 5 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['７'])), id: 6 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['８'])), id: 7 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['９'])), id: 8 },
                { name: ko.observable(nts.uk.resource.getText("KSU001_1603", ['１０'])), id: 9 },
            ]);
            dataSource([null, null, null, null, null, null, null, null, null, null]);

            for (let i = 0; i < listPattern.length; i++) {
                let source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
                //change text of linkbutton
                listTextButton()[listPattern[i].groupNo - 1].name(nts.uk.text.padRight(listPattern[i].groupName, ' ', 6));
                //set data for dataSource
                _.each(listPattern[i].patternItem, (pattItem) => {
                    let text = pattItem.patternName;
                    let arrPairShortName = [], arrPairObject: any = [];
                    _.forEach(pattItem.workPairSet, (wPSet) => {
                        let workType = null, workTime = null, pairShortName = null;
                        workType = _.find(__viewContext.viewModel.viewO.listWorkType(), { 'workTypeCode': wPSet.workTypeCode });
                        let workTypeShortName = workType.abbreviationName;
                        workTime = _.find(__viewContext.viewModel.viewO.listWorkTime(), { 'siftCd': wPSet.workTimeCode });
                        let workTimeShortName = workTime ? workTime.abName : null;
                        pairShortName = workTimeShortName ? '[' + workTypeShortName + '/' + workTimeShortName + ']' : '[' + workTypeShortName + ']';
                        arrPairShortName.push(pairShortName);
                        arrPairObject.push({
                            index: wPSet.pairNo,
                            data: {
                                workTypeCode: workType.workTypeCode,
                                workTypeName: workType.name,
                                workTimeCode: workTime ? workTime.siftCd : null,
                                workTimeName: workTime ? workTime.name : null,
                                startTime: (workTime && workTime.timeNumberCnt == 1) ? workTime.start : '',
                                endTime: (workTime && workTime.timeNumberCnt == 1) ? workTime.end : '',
                                symbolName: null
                            }
                        });
                    });
                    let arrDataOfArrPairObject: any = [];
                    _.each(arrPairObject, (data) => {
                        arrDataOfArrPairObject.push(data.data);
                    });
                    //set symbol for arrPairObject
                    $.when(__viewContext.viewModel.viewA.getDataToDisplaySymbol(arrDataOfArrPairObject)).done(() => {
                        // set tooltip
                        let arrTooltipClone = _.clone(arrPairShortName);
                        for (let i = 7; i < arrTooltipClone.length; i += 7) {
                            arrPairShortName.splice(i, 0, 'lb');
                            i++;
                        }
                        let tooltip: string = arrPairShortName.join('→');
                        tooltip = tooltip.replace(/→lb/g, '\n');

                        //insert data to source
                        source.splice(pattItem.patternNo - 1, 1, { text: text, tooltip: tooltip, data: arrPairObject });
                    });
                });
                dataSource().splice(listPattern[i].groupNo - 1, 1, source);
            }
        }

        /**
         * Click to link button
         */
        clickLinkButton(element: any, index?: any): void {
            let self = this,
                source: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}];

            if (self.selectedTab() === 'company') {
                self.indexLinkButtonCom = index();
                // link button has color gray when clicked
                _.each($('#part-1-1 a.hyperlink'), (a) => {
                    $(a).removeClass('color-gray');
                });
                $($('#part-1-1 a.hyperlink')[self.indexLinkButtonCom]).addClass('color-gray');
                self.selectedLinkButtonCom(self.indexLinkButtonCom);
                //set sourceCompany
                self.sourceCompany(self.dataSourceCompany()[self.indexLinkButtonCom] || source);
            } else {
                self.indexLinkButtonWkp = index();
                // link button has color gray when clicked
                _.each($('#part-1-2 a.hyperlink'), (a) => {
                    $(a).removeClass('color-gray');
                });
                $($('#part-1-2 a.hyperlink')[self.indexLinkButtonWkp]).addClass('color-gray');
                self.selectedLinkButtonWkp(self.indexLinkButtonWkp);
                //set sourceWorkplace
                self.sourceWorkplace(self.dataSourceWorkplace()[self.indexLinkButtonWkp] || source);
            }
        }

        /**
         * Open popup to change name button
         */
        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            $("#test2").trigger("getdatabutton", { text: button[0].innerText });
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
            $("#test2").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip() });
        }

        /**
         * Close popup
         */
        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#test2").trigger("namechanged", undefined);
        }

        /**
         * Open dialog JA
         */
        openDialogJA(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            setShared('dataForJA', {
                selectedTab: self.selectedTab(),
                workplaceName: __viewContext.viewModel.viewA.workPlaceNameDisplay(),
                workplaceId: self.selectedTab() === 'company' ? null : __viewContext.viewModel.viewA.workplaceId,
                listWorkType: __viewContext.viewModel.viewO.listWorkType(),
                listWorkTime: __viewContext.viewModel.viewO.listWorkTime(),
                selectedLinkButton: self.selectedTab() === 'company' ? self.selectedLinkButtonCom() : self.selectedLinkButtonWkp()
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/ja/index.xhtml").onClosed(() => {
                let selectedLB = getShared("dataFromJA").selectedLinkButton;
                if (self.selectedTab() == 'company') {
                    $.when(__viewContext.viewModel.viewA.getDataComPattern()).done(() => {
                        self.handleInit(self.listComPattern(), self.textButtonArrComPattern, self.dataSourceCompany);
                        self.clickLinkButton(null, selectedLB);
                    });
                } else {
                    $.when(__viewContext.viewModel.viewA.getDataWkpPattern()).done(() => {
                        self.handleInit(self.listWkpPattern(), self.textButtonArrWkpPattern, self.dataSourceWorkplace);
                        self.clickLinkButton(null, selectedLB);
                    });
                }

                dfd.resolve(undefined);
            });
            return dfd.promise();
        }

        /**
         * Open dialog JB
         */
        openDialogJB(data): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.textName(data[0].innerHTML === '+' ? null : data[0].innerHTML);
            self.tooltip(data[0].title === '' ? null : data[0].title);
            setShared("dataForJB", {
                text: self.textName(),
                tooltip: self.tooltip(),
                textDecision: nts.uk.resource.getText("KSU001_924"),
            });
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                let data = getShared("dataFromJB");
                self.textName(data ? data.text : self.textName());
                self.tooltip(data ? data.tooltip : self.tooltip());
                //set symbol for object
                $.when(__viewContext.viewModel.viewA.getDataToDisplaySymbol(data.data)).done(() => {
                    dfd.resolve({ text: self.textName(), tooltip: self.tooltip(), data: data.data });
                    self.refreshDataSource();
                });
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
            if (self.selectedTab() === 'company') {
                self.dataSourceCompany()[self.indexLinkButtonCom] = self.sourceCompany();
            } else {
                self.dataSourceWorkplace()[self.indexLinkButtonWkp] = self.sourceWorkplace();
            }
        }
    }
}