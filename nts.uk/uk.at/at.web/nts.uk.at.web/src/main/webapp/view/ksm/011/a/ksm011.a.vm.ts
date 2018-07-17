module nts.uk.at.view.ksm011 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            removeAble: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'A', name: getText('KSM011_2'), active: true }),
                new TabModel({ id: 'B', name: getText('KSM011_3') }),
                new TabModel({ id: 'C', name: getText('KSM011_4') }),
                new TabModel({ id: 'D', name: getText('KSM011_5') })
            ]);
            currentTab: KnockoutObservable<string> = ko.observable('A');
            oldtab: KnockoutObservable<any> = ko.observable(new TabModel({ id: 0, name: "" }));

            constructor() {
                let self = this;
                //get use setting
                self.tabs().map((t) => {
                    // set title for tab

                    if (t.active() == true) {
                        self.title(t.name);
                    }
                });
            }

            changeTab(tab: TabModel) {
                let self = this,
                    view: any = __viewContext.viewModel;

                // cancel action if tab self click
                if (self.oldtab().id == tab.id) {
                    return;
                }
                //set not display remove button first when change tab
                //__viewContext.viewModel.tabView.removeAble(false);
                tab.active(true);
                self.title(tab.name);
                self.oldtab(tab);

                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
                    case 'A':
                        self.currentTab('A');
                        if (!!view.viewmodelA && typeof view.viewmodelA.start == 'function') {
                            view.viewmodelA.start();
                        }
                        break;
                    case 'B':
                        self.currentTab('B');
                        if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                            view.viewmodelB.start();
                        }
                        break;
                    case 'C':
                        self.currentTab('C');
                        if (!!view.viewmodelC && typeof view.viewmodelC.start == 'function') {
                            view.viewmodelC.start();
                        }
                        break;
                    case 'D':
                        self.currentTab('D');
                        if (!!view.viewmodelD && typeof view.viewmodelD.start == 'function') {
                            view.viewmodelD.start();
                        }
                        break;
                }
            }
        }

        interface ITabModel {
            id: any;
            name: string;
            active?: boolean;
            display?: boolean;
        }

        class TabModel {
            id: any;
            name: string;
            active: KnockoutObservable<boolean> = ko.observable(false);
            display: KnockoutObservable<boolean> = ko.observable(true);

            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
                this.display(param.display || true);
            }

            changeTab() {
                // call parent view action
                __viewContext.viewModel.tabView.changeTab(this);
            }
        }
    }

    export module a.viewmodel {
        export class ScreenModel {
            dataModel: KnockoutObservable<ScheFuncControlDto>;
            alarmCheckAtr: KnockoutObservableArray<any>;
            selectedAlarm: KnockoutObservable<number>;
            confirmedAtr: KnockoutObservableArray<any>;
            selectedConfirmed: KnockoutObservable<number>;
            publicAtr: KnockoutObservableArray<any>;
            selectedPublic: KnockoutObservable<number>;
            outputAtr: KnockoutObservableArray<any>;
            selectedOutput: KnockoutObservable<number>;
            workDevision: KnockoutObservableArray<any>;
            selectedWorkDivision: KnockoutObservable<number>;
            teamDivision: KnockoutObservableArray<any>;
            selectedTeamDivision: KnockoutObservable<number>;
            rankAtr: KnockoutObservableArray<any>;
            selectedRank: KnockoutObservable<number>;
            dayOfWeek: KnockoutObservableArray<any>;
            selectedDay: KnockoutObservable<number>;
            shortNameDisp: KnockoutObservableArray<any>;
            selectedShortName: KnockoutObservable<number>;
            timeDisp: KnockoutObservableArray<any>;
            selectedTime: KnockoutObservable<number>;
            symbols: KnockoutObservableArray<any>;
            selectedSymbol: KnockoutObservable<number>;
            dispOn28th: KnockoutObservableArray<any>;
            selectedDispOn28th: KnockoutObservable<number>;
            endDateIndication: KnockoutObservableArray<any>;
            selectedIndication: KnockoutObservable<number>;
            individualDisp: KnockoutObservableArray<any>;
            selectedIndividual: KnockoutObservable<number>;
            dispByDate: KnockoutObservableArray<any>;
            selectedDate: KnockoutObservable<number>;
            indicationByShift: KnockoutObservableArray<any>;
            selectedIndicationByShift: KnockoutObservable<number>;
            regularWork: KnockoutObservableArray<any>;
            selectedRegular: KnockoutObservable<number>;
            fluidWork: KnockoutObservableArray<any>;
            selectedFluid: KnockoutObservable<number>;
            workForFlex: KnockoutObservableArray<any>;
            selectedFlex: KnockoutObservable<number>;
            overtime: KnockoutObservableArray<any>;
            selectedOvertime: KnockoutObservable<number>;
            generalCreated: KnockoutObservableArray<any>;
            selectedGeneral: KnockoutObservable<number>;
            simulation: KnockoutObservableArray<any>;
            selectedSimulation: KnockoutObservable<number>;
            capture: KnockoutObservableArray<any>;
            selectedCapture: KnockoutObservable<number>;
            completeFunc: KnockoutObservableArray<any>;
            selectedCompFunc: KnockoutObservable<number>;
            optionComplete: KnockoutObservableArray<any>;
            selectedOptionComp: KnockoutObservable<number>;
            alarmCheck: KnockoutObservableArray<any>;
            selectedAlarmCheck: KnockoutObservable<number>;
            alarmMethod: KnockoutObservableArray<any>;
            selectedAlarmMethod: KnockoutObservable<number>;
            conditionList: KnockoutObservable<string>;
            unhooking: KnockoutObservableArray<any>;
            selectedUnhooking: KnockoutObservable<number>;
            confirm: KnockoutObservableArray<any>;
            selectedConfirm: KnockoutObservable<number>;
            searchMethod: KnockoutObservableArray<any>;
            selectedSearchMethod: KnockoutObservable<number>;
            retrievalMethod: KnockoutObservableArray<any>;
            selectedRetrieval: KnockoutObservable<number>;
            alarmCheckEnable: KnockoutObservable<boolean>;
            alarmMethodEnable: KnockoutObservable<boolean>;
            openEDialogEnable: KnockoutObservable<boolean>;
            conditionListEnable: KnockoutObservable<boolean>;
            unhookingEnable: KnockoutObservable<boolean>;
            confirmEnable: KnockoutObservable<boolean>;
            retrievalMethodEnable: KnockoutObservable<boolean>;
            optionCompleteEnable: KnockoutObservable<boolean>;
            dataA: any;
            dataE: any;
            conditionData: KnockoutObservableArray<ConditionModel>;
            scheFuncCondList: KnockoutObservableArray<any>;
            oldScheFuncCondList: any;

            constructor() {
                var self = this;

                self.dataA = null;
                self.dataE = null;
                self.conditionData = ko.observableArray([]);
                self.scheFuncCondList = ko.observableArray([]);

                self.alarmCheckEnable = ko.observable(false);
                self.alarmMethodEnable = ko.observable(true);
                self.openEDialogEnable = ko.observable(false);
                self.conditionListEnable = ko.observable(false);
                self.unhookingEnable = ko.observable(false);
                self.confirmEnable = ko.observable(false);
                self.retrievalMethodEnable = ko.observable(false);
                self.optionCompleteEnable = ko.observable(false);

                //Block 1
                self.alarmCheckAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedAlarm = ko.observable(0);

                self.confirmedAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedConfirmed = ko.observable(0);

                self.publicAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedPublic = ko.observable(1);

                self.outputAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedOutput = ko.observable(0);

                self.workDevision = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedWorkDivision = ko.observable(1);

                self.teamDivision = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedTeamDivision = ko.observable(1);

                self.rankAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedRank = ko.observable(1);

                self.dayOfWeek = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("月曜日") },
                    { code: 1, name: nts.uk.resource.getText("火曜日") },
                    { code: 2, name: nts.uk.resource.getText("水曜日") },
                    { code: 3, name: nts.uk.resource.getText("木曜日") },
                    { code: 4, name: nts.uk.resource.getText("金曜日") },
                    { code: 5, name: nts.uk.resource.getText("土曜日") },
                    { code: 6, name: nts.uk.resource.getText("日曜日") }
                ]);

                self.selectedDay = ko.observable(0);

                //Block 2
                self.shortNameDisp = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedShortName = ko.observable(0);

                self.timeDisp = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedTime = ko.observable(0);

                self.symbols = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedSymbol = ko.observable(1);

                //Block 3
                self.dispOn28th = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedDispOn28th = ko.observable(1);

                self.endDateIndication = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedIndication = ko.observable(1);

                //Block 4
                self.individualDisp = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedIndividual = ko.observable(1);

                self.dispByDate = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedDate = ko.observable(0);

                self.indicationByShift = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedIndicationByShift = ko.observable(1);

                //Block 5
                self.regularWork = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);

                self.selectedRegular = ko.observable(0);

                self.fluidWork = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);

                self.selectedFluid = ko.observable(0);

                self.workForFlex = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);

                self.selectedFlex = ko.observable(0);

                self.overtime = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);

                self.selectedOvertime = ko.observable(0);

                //Block 6
                self.generalCreated = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedGeneral = ko.observable(1);

                self.simulation = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedSimulation = ko.observable(1);

                self.capture = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedCapture = ko.observable(1);

                //Block 7                
                self.completeFunc = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedCompFunc = ko.observable(1);

                self.optionComplete = ko.observableArray([
                    { id: 0, name: nts.uk.resource.getText("KSM011_41") },
                    { id: 1, name: nts.uk.resource.getText("KSM011_42") }
                ]);

                self.selectedOptionComp = ko.observable(0);

                self.alarmCheck = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedAlarmCheck = ko.observable(0);

                self.alarmMethod = ko.observableArray([
                    { id: 0, name: nts.uk.resource.getText("KSM011_41") },
                    { id: 1, name: nts.uk.resource.getText("KSM011_42") }
                ]);

                self.selectedAlarmMethod = ko.observable(0);

                self.conditionList = ko.observable(nts.uk.resource.getText("KSM011_75"));

                self.unhooking = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedUnhooking = ko.observable(0);

                self.confirm = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedConfirm = ko.observable(0);


                //Block 8
                self.searchMethod = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);

                self.selectedSearchMethod = ko.observable(1);

                self.retrievalMethod = ko.observableArray([
                    { id: 0, name: nts.uk.resource.getText("KSM011_51") },
                    { id: 1, name: nts.uk.resource.getText("KSM011_52") }
                ]);

                self.selectedRetrieval = ko.observable(1);

                //Subscribes
                self.selectedCompFunc.subscribe(function(value) {
                    if (value == 0) {
                        self.optionCompleteEnable(true);
                        if (self.selectedOptionComp() == 0) {
                            self.alarmCheckEnable(false);
                            self.alarmMethodEnable(false);
                            self.selectedAlarmMethod.valueHasMutated();
                            self.openEDialogEnable(false);
                            self.conditionListEnable(false);
                            self.unhookingEnable(false);
                            self.confirmEnable(false);
                        } else {
                            self.alarmCheckEnable(true);
                            if (self.selectedAlarmCheck() == 0) {
                                self.alarmMethodEnable(true);

                                if (self.selectedAlarmMethod() == 0) {
                                    self.openEDialogEnable(false);
                                    self.conditionListEnable(false);
                                } else {
                                    self.openEDialogEnable(true);
                                    self.conditionListEnable(true);
                                }
                            } else {
                                self.alarmMethodEnable(false);
                                self.openEDialogEnable(false);
                                self.conditionListEnable(false);
                            }

                            self.unhookingEnable(true);
                            self.confirmEnable(true);
                        }
                    } else {
                        self.optionCompleteEnable(false);
                        self.alarmCheckEnable(false);
                        self.alarmMethodEnable(false);
                        self.openEDialogEnable(false);
                        self.conditionListEnable(false);
                        self.unhookingEnable(false);
                        self.confirmEnable(false);
                    }
                });

                self.selectedOptionComp.subscribe(function(value) {
                    if (value == 0) {
                        self.alarmCheckEnable(false);
                        self.unhookingEnable(false);
                        self.confirmEnable(false);
                        self.alarmMethodEnable(false);
                        self.openEDialogEnable(false);
                        self.conditionListEnable(false);
                    } else {
                        if (self.selectedCompFunc() == 1) {
                            self.alarmCheckEnable(false);
                            self.alarmMethodEnable(false);
                            self.openEDialogEnable(false);
                            self.conditionListEnable(false);
                        } else {
                            self.alarmCheckEnable(true);
                        }

                        if (self.selectedAlarmCheck() == 0) {
                            if (self.selectedCompFunc() == 1) {
                                self.alarmCheckEnable(false);
                                self.alarmMethodEnable(false);
                                self.openEDialogEnable(false);
                                self.conditionListEnable(false);
                            } else {
                                self.alarmMethodEnable(true);
                            }

                            if (self.selectedAlarmMethod() == 0) {
                                self.openEDialogEnable(false);
                                self.conditionListEnable(false);
                            } else {
                                if (self.selectedCompFunc() == 1) {
                                    self.alarmCheckEnable(false);
                                    self.alarmMethodEnable(false);
                                    self.openEDialogEnable(false);
                                    self.conditionListEnable(false);
                                } else {
                                    self.openEDialogEnable(true);
                                    self.conditionListEnable(true);
                                }
                            }
                        } else {
                            self.alarmMethodEnable(false);
                            self.openEDialogEnable(false);
                            self.conditionListEnable(false);
                        }

                        self.unhookingEnable(true);
                        self.confirmEnable(true);
                    }
                });

                self.selectedAlarmCheck.subscribe(function(value) {
                    if (value == 0) {
                        self.alarmMethodEnable(true);
                        self.selectedAlarmMethod.valueHasMutated();
                    } else {
                        self.alarmMethodEnable(false);
                        self.openEDialogEnable(false);
                        self.conditionListEnable(false);
                    }
                });

                self.selectedAlarmMethod.subscribe(function(value) {
                    if (value == 0) {
                        self.openEDialogEnable(false);
                        self.conditionListEnable(false);
                    } else {
                        if (self.selectedCompFunc() == 1) {
                            self.alarmCheckEnable(false);
                            self.alarmMethodEnable(false);
                            self.openEDialogEnable(false);
                            self.conditionListEnable(false);
                        } else {
                            self.openEDialogEnable(true);
                            self.conditionListEnable(true);
                        }
                    }
                });

                self.selectedSearchMethod.subscribe(function(value) {
                    if (value == 0) {
                        self.retrievalMethodEnable(false);
                    } else {
                        self.retrievalMethodEnable(true);
                    }
                });
            }

            /**
             * Start page.
             */
            start() {
                var self = this;
                var dfd = $.Deferred();

                self.conditionData([]);

                $.when(self.getData(), self.getShiftConditionCat(), self.getShiftCondition()).done(function() {
                    if (self.conditionData().length > 0 && self.dataA != null) {
                        var conds = "";
                        self.scheFuncCondList([]);

                        self.selectedAlarm(self.dataA.alarmCheckUseCls);
                        self.selectedConfirmed(self.dataA.confirmedCls);
                        self.selectedPublic(self.dataA.publicCls);
                        self.selectedOutput(self.dataA.outputCls);
                        self.selectedWorkDivision(self.dataA.workDormitionCls);
                        self.selectedTeamDivision(self.dataA.teamCls);
                        self.selectedRank(self.dataA.rankCls);
                        self.selectedDay(self.dataA.startDateInWeek);
                        self.selectedShortName(self.dataA.shortNameDisp);
                        self.selectedTime(self.dataA.timeDisp);
                        self.selectedSymbol(self.dataA.symbolDisp);
                        self.selectedDispOn28th(self.dataA.twentyEightDaysCycle);
                        self.selectedIndication(self.dataA.lastDayDisp);
                        self.selectedIndividual(self.dataA.individualDisp);
                        self.selectedDate(self.dataA.dispByDate);
                        self.selectedIndicationByShift(self.dataA.indicationByShift);
                        self.selectedRegular(self.dataA.regularWork);
                        self.selectedFluid(self.dataA.fluidWork);
                        self.selectedFlex(self.dataA.workingForFlex);
                        self.selectedOvertime(self.dataA.overtimeWork);
                        self.selectedGeneral(self.dataA.normalCreation);
                        self.selectedSimulation(self.dataA.simulationCls);
                        self.selectedCapture(self.dataA.captureUsageCls);
                        self.selectedCompFunc(self.dataA.completedFuncCls);
                        self.selectedOptionComp(self.dataA.howToComplete);
                        self.selectedAlarmCheck(self.dataA.alarmCheckCls);
                        self.selectedAlarmMethod(self.dataA.executionMethod);
                        self.selectedUnhooking(self.dataA.handleRepairAtr);
                        self.selectedConfirm(self.dataA.confirm);
                        self.selectedSearchMethod(self.dataA.searchMethod);
                        self.selectedRetrieval(self.dataA.searchMethodDispCls);

                        var sortedScheFuncCond = _.sortBy(self.dataA.scheFuncCond, [function(o) { return o.conditionNo; }]);
                        _.forEach(sortedScheFuncCond, function(item) {
                            var result = _.find(self.conditionData(), function(o) { return o.conditionNo == Number(item.conditionNo) && o.isParent == false; });
                            if (result !== undefined) {
                                self.scheFuncCondList.push(result);
                            }
                            else {
                                self.scheFuncCondList.push(new ConditionModel({
                                    conditionNo: item.conditionNo,
                                    conditionName: nts.uk.resource.getText("KSM011_75"),
                                    isParent: false,
                                }));
                            }
                            conds += result !== undefined ? result.conditionName + ", " : nts.uk.resource.getText("KSM011_75") + ", ";
                        });
                        if (conds == "") {
                            self.conditionList(nts.uk.resource.getText("KSM011_75"));
                        } else {
                            self.conditionList(conds.trim().slice(0, -1));
                        }
                        self.oldScheFuncCondList = self.scheFuncCondList();
                    } else {
                        self.selectedAlarm(0);
                        self.selectedConfirmed(0);
                        self.selectedPublic(1);
                        self.selectedOutput(0);
                        self.selectedWorkDivision(1);
                        self.selectedTeamDivision(1);
                        self.selectedRank(1);
                        self.selectedDay(0);
                        self.selectedShortName(0);
                        self.selectedTime(0);
                        self.selectedSymbol(1);
                        self.selectedDispOn28th(1);
                        self.selectedIndication(1);
                        self.selectedIndividual(1);
                        self.selectedDate(0);
                        self.selectedIndicationByShift(1);
                        self.selectedRegular(0);
                        self.selectedFluid(0);
                        self.selectedFlex(0);
                        self.selectedOvertime(0);
                        self.selectedGeneral(1);
                        self.selectedSimulation(1);
                        self.selectedCapture(1);
                        self.selectedCompFunc(1);
                        self.selectedOptionComp(0);
                        self.selectedAlarmCheck(0);
                        self.selectedAlarmMethod(0);
                        self.conditionList(nts.uk.resource.getText("KSM011_75"));
                        self.selectedUnhooking(0);
                        self.selectedConfirm(0);
                        self.selectedSearchMethod(1);
                        self.selectedRetrieval(1);
                    }

                    if (self.selectedCompFunc() == 0) {
                        self.optionCompleteEnable(true);
                        if (self.selectedOptionComp() == 0) {
                            self.alarmCheckEnable(false);
                            self.alarmMethodEnable(false);
                        } else {
                            self.alarmCheckEnable(true);
                            if (self.selectedAlarmCheck() == 1) {
                                self.alarmMethodEnable(false);
                                self.openEDialogEnable(false);
                                self.conditionListEnable(false);
                            }
                        }
                    } else {
                        self.unhookingEnable(false);
                        self.confirmEnable(false);
                        self.alarmMethodEnable(false);
                    }
                    nts.uk.ui.windows.setShared("KSM011_A_TEAMDIVISION", self.selectedTeamDivision());
                    nts.uk.ui.windows.setShared("KSM011_A_RANK", self.selectedRank());

                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });

                return dfd.promise();
            }

            /**
             * Get data from db.
             */
            getData(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.findScheFuncControl().done(function(data) {
                    if (data != null) {
                        var dataItems = new ScheFuncControlDto({
                            alarmCheckUseCls: data.alarmCheckUseCls,
                            confirmedCls: data.confirmedCls,
                            publicCls: data.publicCls,
                            outputCls: data.outputCls,
                            workDormitionCls: data.workDormitionCls,
                            teamCls: data.teamCls,
                            rankCls: data.rankCls,
                            startDateInWeek: data.startDateInWeek,
                            shortNameDisp: data.shortNameDisp,
                            timeDisp: data.timeDisp,
                            symbolDisp: data.symbolDisp,
                            twentyEightDaysCycle: data.twentyEightDaysCycle,
                            lastDayDisp: data.lastDayDisp,
                            individualDisp: data.individualDisp,
                            dispByDate: data.dispByDate,
                            indicationByShift: data.indicationByShift,
                            regularWork: data.regularWork,
                            fluidWork: data.fluidWork,
                            workingForFlex: data.workingForFlex,
                            overtimeWork: data.overtimeWork,
                            normalCreation: data.normalCreation,
                            simulationCls: data.simulationCls,
                            captureUsageCls: data.captureUsageCls,
                            completedFuncCls: data.completedFuncCls,
                            howToComplete: data.howToComplete,
                            alarmCheckCls: data.alarmCheckCls,
                            executionMethod: data.executionMethod,
                            handleRepairAtr: data.handleRepairAtr,
                            confirm: data.confirm,
                            searchMethod: data.searchMethod,
                            searchMethodDispCls: data.searchMethodDispCls,
                            scheFuncCond: data.scheFuncCond
                        });

                        self.dataA = dataItems;
                    }

                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);
                });

                return dfd.promise();
            }

            /**
             * Get ShiftConditionCat.
             */
            getShiftConditionCat(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.getShiftConditionCat().done(function(data) {
                    _.forEach(data, function(item) {
                        var model = new ConditionModel({
                            conditionNo: item.categoryNo,
                            conditionName: item.categoryName,
                            isParent: true,
                        });

                        self.conditionData.push(model);
                    });

                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);
                });

                return dfd.promise();
            }

            /**
             * Get ShiftCondition.
             */
            getShiftCondition(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.getShiftCondition().done(function(data) {
                    _.forEach(data, function(item) {
                        var model = new ConditionModel({
                            conditionNo: item.conditionNo,
                            conditionName: item.conditionName,
                            isParent: false,
                        });

                        self.conditionData.push(model);
                    });

                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);
                });

                return dfd.promise();
            }

            /**
             * Registration function.
             */
            registration() {
                var self = this;

                // clear all error
                nts.uk.ui.errors.clearAll();

                if (self.selectedCompFunc() == 0 && self.selectedOptionComp() == 1) {
                    if (self.selectedAlarmCheck() == 0 && self.selectedAlarmMethod() == 1) {
                        if (self.dataE == null && self.scheFuncCondList().length == 0) {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_718" });
                            return;
                        }
                    }

                    if (self.selectedAlarmCheck() != 0 && self.selectedUnhooking() != 0 && self.selectedConfirm() != 0) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_717" });
                        return;
                    }
                }

                if (nts.uk.ui.errors.hasError()) {
                    return;
                }

                var conditionData = [];
                if (self.selectedCompFunc() == 0 && self.selectedOptionComp() == 1 && self.selectedAlarmCheck() == 0 && self.selectedAlarmMethod() == 1) {
                    if (self.dataE != null && self.dataE.length > 0) {
                        _.forEach(self.dataE, function(code) {
                            conditionData.push({
                                conditionNo: Number(code.slice(0, -1))
                            });
                        });
                    } else {
                        let result = _.find(self.scheFuncCondList(), function(o) { return o.conditionName == nts.uk.resource.getText("KSM011_75"); });
                        if (result !== undefined) {
                            nts.uk.ui.dialog.alertError("khong dang ki duoc");
                            return;
                        }
                        _.forEach(self.scheFuncCondList(), function(item) {
                            conditionData.push({
                                conditionNo: Number(item.conditionNo)
                            });
                        });
                    }
                } else {
                    let result = _.find(self.oldScheFuncCondList, function(o) { return o.conditionName == nts.uk.resource.getText("KSM011_75"); });
                    if (result !== undefined) {
                        nts.uk.ui.dialog.alertError("khong dang ki duoc");
                        return;
                    }
                    if (self.selectedOptionComp() == 0 || self.selectedAlarmCheck() == 1 || self.selectedAlarmMethod() == 0) {
                        _.forEach(self.oldScheFuncCondList, function(item) {
                            conditionData.push({
                                conditionNo: Number(item.conditionNo)
                            });
                        });
                    } else {
                        _.forEach(self.oldScheFuncCondList, function(item) {
                            conditionData.push({
                                conditionNo: Number(item.conditionNo)
                            });
                        });
                    }
                }

                var data = new ScheFuncControlDto({
                    alarmCheckUseCls: self.selectedAlarm(),
                    confirmedCls: self.selectedConfirmed(),
                    publicCls: self.selectedPublic(),
                    outputCls: self.selectedOutput(),
                    workDormitionCls: self.selectedWorkDivision(),
                    teamCls: self.selectedTeamDivision(),
                    rankCls: self.selectedRank(),
                    startDateInWeek: self.selectedDay(),
                    shortNameDisp: self.selectedShortName(),
                    timeDisp: self.selectedTime(),
                    symbolDisp: self.selectedSymbol(),
                    twentyEightDaysCycle: self.selectedDispOn28th(),
                    lastDayDisp: self.selectedIndication(),
                    individualDisp: self.selectedIndividual(),
                    dispByDate: self.selectedDate(),
                    indicationByShift: self.selectedIndicationByShift(),
                    regularWork: self.selectedRegular(),
                    fluidWork: self.selectedFluid(),
                    workingForFlex: self.selectedFlex(),
                    overtimeWork: self.selectedOvertime(),
                    normalCreation: self.selectedGeneral(),
                    simulationCls: self.selectedSimulation(),
                    captureUsageCls: self.selectedCapture(),
                    completedFuncCls: self.selectedCompFunc(),
                    howToComplete: self.selectedOptionComp(),
                    alarmCheckCls: self.selectedAlarmCheck(),
                    executionMethod: self.selectedAlarmMethod(),
                    handleRepairAtr: self.selectedUnhooking(),
                    confirm: self.selectedConfirm(),
                    searchMethod: self.selectedSearchMethod(),
                    searchMethodDispCls: self.selectedRetrieval(),
                    scheFuncCond: conditionData
                });
                nts.uk.ui.windows.setShared("KSM011_A_TEAMDIVISION", self.selectedTeamDivision());
                nts.uk.ui.windows.setShared("KSM011_A_RANK", self.selectedRank());
                service.saveScheFuncControl(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId, messageParams: error.parameterIds });
                });
            }

            /**
             * Open E dialog to set condition list.
             */
            openEDialog() {
                var self = this;

                var oldData = [];
                _.forEach(self.scheFuncCondList(), function(item) {
                    oldData.push(item.conditionNo.toString() + "c");
                });
                let items = self.dataE != null ? self.dataE : oldData;
                //check exist items
                service.checkExistedItems(items).done(function(data) {
                        if (self.dataE != null) {
                            nts.uk.ui.windows.setShared("KSM011_A_DATA_SELECTED", self.dataE);
                        } else {
                            nts.uk.ui.windows.setShared("KSM011_A_DATA_SELECTED", oldData);
                        }

                        nts.uk.ui.windows.sub.modal("/view/ksm/011/e/index.xhtml").onClosed(() => {
                            self.dataE = nts.uk.ui.windows.getShared("KSM011_E_DATA");

                            if (self.conditionData().length > 0 && self.dataE != null) {
                                var conds = "";
                                self.scheFuncCondList([]);

                                _.forEach(self.dataE, function(code) {
                                    let coder = Number(code.slice(0, -1)) + "c";

                                    var result = _.find(self.conditionData(), function(o) { return o.conditionNo == Number(code.slice(0, -1)) && o.isParent == false; });
                                    if (result !== undefined) {
                                        if (code == coder) {
                                            self.scheFuncCondList.push(result);
                                        } else {
                                            self.scheFuncCondList.push(new ConditionModel({
                                            conditionNo: Number(code.slice(0, -1)),
                                            conditionName: "",
                                            isParent: true,
                                        }));
                                        }

                                    }
                                    else {
                                        self.scheFuncCondList.push(new ConditionModel({
                                            conditionNo: Number(code.slice(0, -1)),
                                            conditionName: nts.uk.resource.getText("KSM011_75"),
                                            isParent: false,
                                        }));
                                    }
                                    conds += result !== undefined ? result.conditionName + ", " : nts.uk.resource.getText("KSM011_75") + ", ";
                                });

                                if (conds == "") {
                                    self.conditionList(nts.uk.resource.getText("KSM011_75"));
                                } else {
                                    self.conditionList(conds.trim().slice(0, -1));
                                }
                            }
                        });

                    
                });
            }
        }

        class ScheFuncControlDto {
            alarmCheckUseCls: number;
            confirmedCls: number;
            publicCls: number;
            outputCls: number;
            workDormitionCls: number;
            teamCls: number;
            rankCls: number;
            startDateInWeek: number;
            shortNameDisp: number;
            timeDisp: number;
            symbolDisp: number;
            twentyEightDaysCycle: number;
            lastDayDisp: number;
            individualDisp: number;
            dispByDate: number;
            indicationByShift: number;
            regularWork: number;
            fluidWork: number;
            workingForFlex: number;
            overtimeWork: number;
            normalCreation: number;
            simulationCls: number;
            captureUsageCls: number;
            completedFuncCls: number;
            howToComplete: number;
            alarmCheckCls: number;
            executionMethod: number;
            handleRepairAtr: number;
            confirm: number;
            searchMethod: number;
            searchMethodDispCls: number;
            scheFuncCond: Array<ScheFuncCondDto>;

            constructor(param: IScheFuncControlDto) {
                this.alarmCheckUseCls = param.alarmCheckUseCls;
                this.confirmedCls = param.confirmedCls;
                this.publicCls = param.publicCls;
                this.outputCls = param.outputCls;
                this.workDormitionCls = param.workDormitionCls;
                this.teamCls = param.teamCls;
                this.rankCls = param.rankCls;
                this.startDateInWeek = param.startDateInWeek;
                this.shortNameDisp = param.shortNameDisp;
                this.timeDisp = param.timeDisp;
                this.symbolDisp = param.symbolDisp;
                this.twentyEightDaysCycle = param.twentyEightDaysCycle;
                this.lastDayDisp = param.lastDayDisp;
                this.individualDisp = param.individualDisp;
                this.dispByDate = param.dispByDate;
                this.indicationByShift = param.indicationByShift;
                this.regularWork = param.regularWork;
                this.fluidWork = param.fluidWork;
                this.workingForFlex = param.workingForFlex;
                this.overtimeWork = param.overtimeWork;
                this.normalCreation = param.normalCreation;
                this.simulationCls = param.simulationCls;
                this.captureUsageCls = param.captureUsageCls;
                this.completedFuncCls = param.completedFuncCls;
                this.howToComplete = param.howToComplete;
                this.alarmCheckCls = param.alarmCheckCls;
                this.executionMethod = param.executionMethod;
                this.handleRepairAtr = param.handleRepairAtr;
                this.confirm = param.confirm;
                this.searchMethod = param.searchMethod;
                this.searchMethodDispCls = param.searchMethodDispCls;
                this.scheFuncCond = param.scheFuncCond;
            }
        }

        interface IScheFuncControlDto {
            alarmCheckUseCls: number;
            confirmedCls: number;
            publicCls: number;
            outputCls: number;
            workDormitionCls: number;
            teamCls: number;
            rankCls: number;
            startDateInWeek: number;
            shortNameDisp: number;
            timeDisp: number;
            symbolDisp: number;
            twentyEightDaysCycle: number;
            lastDayDisp: number;
            individualDisp: number;
            dispByDate: number;
            indicationByShift: number;
            regularWork: number;
            fluidWork: number;
            workingForFlex: number;
            overtimeWork: number;
            normalCreation: number;
            simulationCls: number;
            captureUsageCls: number;
            completedFuncCls: number;
            howToComplete: number;
            alarmCheckCls: number;
            executionMethod: number;
            handleRepairAtr: number;
            confirm: number;
            searchMethod: number;
            searchMethodDispCls: number;
            scheFuncCond: Array<ScheFuncCondDto>;
        }

        class ScheFuncCondDto {
            conditionNo: number;

            constructor(param: IScheFuncCondDto) {
                this.conditionNo = param.conditionNo;
            }
        }

        interface IScheFuncCondDto {
            conditionNo: number;
        }

        class ConditionModel {
            conditionNo: number;
            conditionName: string;
            isParent: boolean;

            constructor(param: IConditionModel) {
                this.conditionNo = param.conditionNo;
                this.conditionName = param.conditionName;
                this.isParent = param.isParent;
            }
        }

        interface IConditionModel {
            conditionNo: number;
            conditionName: string;
            isParent: boolean;
        }
    }
}