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
            dataModel: KnockoutObservable<ScheFuncControl>;
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
            
            constructor() {
                var self = this;

                self.alarmCheckEnable = ko.observable(false);
                self.alarmMethodEnable = ko.observable(true);
                self.openEDialogEnable = ko.observable(false);
                self.conditionListEnable = ko.observable(false);
                self.unhookingEnable = ko.observable(false);
                self.confirmEnable = ko.observable(false);
                self.retrievalMethodEnable = ko.observable(false);
                self.optionCompleteEnable = ko.observable(true);
                
                //Block 1
                self.alarmCheckAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedAlarm = ko.observable(1);
                
                self.confirmedAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedConfirmed = ko.observable(1);
                
                self.publicAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedPublic = ko.observable(0);
                
                self.outputAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedOutput = ko.observable(1);
                
                self.workDevision = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedWorkDivision = ko.observable(0);
                
                self.teamDivision = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedTeamDivision = ko.observable(0);
                
                self.rankAtr = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedRank = ko.observable(0);
                
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
    
                self.selectedShortName = ko.observable(1);
                
                self.timeDisp = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedTime = ko.observable(1);
                
                self.symbols = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedSymbol = ko.observable(0);
                
                //Block 3
                self.dispOn28th = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedDispOn28th = ko.observable(0);
                
                self.endDateIndication = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedIndication = ko.observable(0);
                
                //Block 4
                self.individualDisp = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedIndividual = ko.observable(0);
                
                self.dispByDate = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedDate = ko.observable(0);
                
                self.indicationByShift = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedIndicationByShift = ko.observable(0);
                
                //Block 5
                self.regularWork = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);
    
                self.selectedRegular = ko.observable(1);
                
                self.fluidWork = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);
    
                self.selectedFluid = ko.observable(1);
                
                self.workForFlex = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);
    
                self.selectedFlex = ko.observable(1);
                
                self.overtime = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_29") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_30") }
                ]);
    
                self.selectedOvertime = ko.observable(1);
                
                //Block 6
                self.generalCreated = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedGeneral = ko.observable(0);
                
                self.simulation = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedSimulation = ko.observable(0);
                
                self.capture = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedCapture = ko.observable(0);
                
                //Block 7                
                self.completeFunc = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KSM011_8") },
                    { code: 1, name: nts.uk.resource.getText("KSM011_9") }
                ]);
    
                self.selectedCompFunc = ko.observable(0);
                
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

                self.conditionList = ko.observable("");
                
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
    
                self.selectedSearchMethod = ko.observable(0);
                
                self.retrievalMethod = ko.observableArray([
                    { id: 0, name: nts.uk.resource.getText("KSM011_51") },
                    { id: 1, name: nts.uk.resource.getText("KSM011_52") }
                ]);
    
                self.selectedRetrieval = ko.observable(1);
                
                //Subscribes
                self.selectedCompFunc.subscribe(function(value) {
                    if(value == 0) {
                        self.optionCompleteEnable(true);
                    } else {
                        self.optionCompleteEnable(false);
                    }
                });
                
                self.selectedOptionComp.subscribe(function(value) {
                    if(value == 0) {
                        self.alarmCheckEnable(false);
                        self.unhookingEnable(false);
                        self.confirmEnable(false);
                    } else {
                        self.alarmCheckEnable(true);
                        self.unhookingEnable(true);
                        self.confirmEnable(true);
                    }
                });
                
                self.selectedAlarmCheck.subscribe(function(value) {
                    if(value == 0) {
                        self.alarmMethodEnable(true);
                    } else {
                        self.alarmMethodEnable(false);
                    }
                });
                
                self.selectedAlarmMethod.subscribe(function(value) {
                    if(value == 0) {
                        self.openEDialogEnable(false);
                        self.conditionListEnable(false);
                    } else {
                        self.openEDialogEnable(true);
                        self.conditionListEnable(true);
                    }
                });
                
                self.selectedSearchMethod.subscribe(function(value) {
                    if(value == 0) {
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
                
                $.when(self.getData()).done(function() {
                    
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
                
            }
            
            /**
             * Open E dialog to set condition list.
             */
            openEDialog() {
                nts.uk.ui.windows.sub.modal("/view/ksm/011/e/index.xhtml").onClosed(() => {
                    
                });
            }
        }
        
        class ScheFuncControl {
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
            scheFuncCond: Array<ScheFuncCond>;
            
            constructor(param: IScheFuncControl) {
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
        
        interface IScheFuncControl {
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
            scheFuncCond: Array<ScheFuncCond>;    
        }
        
        class ScheFuncCond {
            conditionNo: number;
        }
    }
}