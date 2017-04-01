module nts.qmm017 {

    export class ScreenModel {
        viewModel017b: KnockoutObservable<any>;
        viewModel017c: KnockoutObservable<any>;
        viewModel017d: KnockoutObservable<any>;
        viewModel017e: KnockoutObservable<any>;
        viewModel017f: KnockoutObservable<any>;
        viewModel017g: KnockoutObservable<any>;
        viewModel017h: KnockoutObservable<any>;
        viewModel017i: KnockoutObservable<any>;

        treeGridHistory: KnockoutObservable<TreeGrid>;
        a_sel_001: KnockoutObservableArray<any>;
        selectedTabASel001: KnockoutObservable<string>;
        isNewMode: KnockoutObservable<boolean>;
        isUpdateMode: KnockoutObservable<boolean>;
        startYearMonth: KnockoutObservable<string>;
        selectedFormula: KnockoutObservable<model.FormulaDto>;

        currentNode: any;
        currentParentNode: any;

        constructor() {
            var self = this;
            self.isNewMode = ko.observable(true);
            self.isUpdateMode = ko.observable(false);
            self.isNewMode.subscribe(function(val) {
                self.isUpdateMode(!val);
            });
            self.treeGridHistory = ko.observable(new TreeGrid());
            self.a_sel_001 = ko.observableArray([
                { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: self.isUpdateMode, visible: ko.observable(true) }
            ]);
            self.selectedTabASel001 = ko.observable('tab-1');
            self.startYearMonth = ko.observable('');
            self.currentNode = ko.observable(null);
            self.currentParentNode = ko.observable(null);
            self.treeGridHistory().singleSelectedCode.subscribe(function(codeChange) {
                if (codeChange !== null) {
                    let currentNode = null;
                    let currentParentNode = null;
                    self.isNewMode(false);
                    for (let order = 0; order < self.treeGridHistory().items().length; order++) {
                        let foundNode = findNode(self.treeGridHistory().items()[order], self.treeGridHistory().singleSelectedCode());
                        if (foundNode) {
                            self.currentNode(foundNode);
                            self.currentParentNode(self.treeGridHistory().items()[order]);
                        }
                    }
                    let rangeYearMonth = self.currentNode().name.split('~');
                    self.startYearMonth(rangeYearMonth[0].trim());
                    self.a_sel_001()[1].enable(true);
                    self.viewModel017b().startYearMonth(rangeYearMonth[0].trim());
                    self.viewModel017b().formulaCode(self.currentParentNode().code);
                    self.viewModel017b().formulaName(self.currentParentNode().name);
                    //get formula detail
                    service.findFormula(self.currentParentNode().code, self.currentNode().code)
                        .done(function(currentFormula) {
                            self.viewModel017b().selectedDifficultyAtr(currentFormula.difficultyAtr);
                            service.getFormulaDetail(self.currentParentNode().code, self.currentNode().code, currentFormula.difficultyAtr)
                                .done(function(currentFormulaDetail: model.FormulaDetailDto) {
                                    if (currentFormula && currentFormula.difficultyAtr === 1) {
                                        self.viewModel017c().formulaManualContent().textArea(currentFormulaDetail.formulaContent);
                                        self.viewModel017c().comboBoxReferenceMonthAtr().selectedCode(currentFormulaDetail.referenceMonthAtr);
                                        self.viewModel017c().comboBoxRoudingMethod().selectedCode(currentFormulaDetail.roundAtr);
                                        self.viewModel017c().comboBoxRoudingPosition().selectedCode(currentFormulaDetail.roundDigit);
                                    } else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 0) {
                                        self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail(currentFormulaDetail.easyFormula[0].formulaEasyDetail);
                                        self.viewModel017c().noneConditionalEasyFormula().easyFormulaName(currentFormulaDetail.easyFormula[0].formulaEasyDetail.easyFormulaName);
                                    } else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 1 && currentFormula.refMasterNo < 6) {
                                        self.viewModel017c().defaultEasyFormula(new EasyFormula(0, self.viewModel017b));
                                        self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(currentFormulaDetail.easyFormula[0].value);
                                        self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings(currentFormulaDetail.easyFormula[0].fixFormulaAtr);
                                        self.viewModel017c().defaultEasyFormula().easyFormulaDetail(currentFormulaDetail.easyFormula[0].formulaEasyDetail);
                                        self.viewModel017c().defaultEasyFormula().easyFormulaName(currentFormulaDetail.easyFormula[0].formulaEasyDetail.easyFormulaName);
                                    } else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 1 && currentFormula.refMasterNo === 6) {
                                        self.viewModel017c().defaultEasyFormula(new EasyFormula(0, self.viewModel017b));
                                        _.forEach(currentFormulaDetail.easyFormula, easyFormula => {
                                            if (easyFormula.easyFormulaCode === '000') {
                                                self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(easyFormula.value);
                                                self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings(easyFormula.fixFormulaAtr);
                                                if (easyFormula.fixFormulaAtr === 1) {
                                                    self.viewModel017c().defaultEasyFormula().easyFormulaDetail(easyFormula.formulaEasyDetail);
                                                    self.viewModel017c().defaultEasyFormula().easyFormulaName(easyFormula.formulaEasyDetail.easyFormulaName);
                                                }
                                            }
                                            if (easyFormula.easyFormulaCode === '001') {
                                                self.viewModel017c().monthlyEasyFormula().easyFormulaFixMoney(easyFormula.value);
                                                self.viewModel017c().monthlyEasyFormula().selectedRuleCodeEasySettings(easyFormula.fixFormulaAtr);
                                                if (easyFormula.fixFormulaAtr === 1) {
                                                    self.viewModel017c().monthlyEasyFormula().easyFormulaDetail(easyFormula.formulaEasyDetail);
                                                    self.viewModel017c().monthlyEasyFormula().easyFormulaName(easyFormula.formulaEasyDetail.easyFormulaName);
                                                }
                                            }
                                            if (easyFormula.easyFormulaCode === '002') {
                                                self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaFixMoney(easyFormula.value);
                                                self.viewModel017c().dailyMonthlyEasyFormula().selectedRuleCodeEasySettings(easyFormula.fixFormulaAtr);
                                                if (easyFormula.fixFormulaAtr === 1) {
                                                    self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaDetail(easyFormula.formulaEasyDetail);
                                                    self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaName(easyFormula.formulaEasyDetail.easyFormulaName);
                                                }
                                            }
                                            if (easyFormula.easyFormulaCode === '003') {
                                                self.viewModel017c().dailyEasyFormula().easyFormulaFixMoney(easyFormula.value);
                                                self.viewModel017c().dailyEasyFormula().selectedRuleCodeEasySettings(easyFormula.fixFormulaAtr);
                                                if (easyFormula.fixFormulaAtr === 1) {
                                                    self.viewModel017c().dailyEasyFormula().easyFormulaDetail(easyFormula.formulaEasyDetail);
                                                    self.viewModel017c().dailyEasyFormula().easyFormulaName(easyFormula.formulaEasyDetail.easyFormulaName);
                                                }
                                            }
                                            if (easyFormula.easyFormulaCode === '004') {
                                                self.viewModel017c().hourlyEasyFormula().easyFormulaFixMoney(easyFormula.value);
                                                self.viewModel017c().hourlyEasyFormula().selectedRuleCodeEasySettings(easyFormula.fixFormulaAtr);
                                                if (easyFormula.fixFormulaAtr === 1) {
                                                    self.viewModel017c().hourlyEasyFormula().easyFormulaDetail(easyFormula.formulaEasyDetail);
                                                    self.viewModel017c().hourlyEasyFormula().easyFormulaName(easyFormula.formulaEasyDetail.easyFormulaName);
                                                }
                                            }
                                        });
                                    }
                                })
                                .fail(function(res) {
                                    alert(res);
                                });

                            self.viewModel017b().selectedConditionAtr(currentFormula.conditionAtr);
                            self.viewModel017b().comboBoxUseMaster().selectedCode(currentFormula.refMasterNo.toString());
                        })
                        .fail(function(res) {
                            alert(res);
                        });
                }
            });
            self.viewModel017b = ko.observable(new BScreen(self));
            self.viewModel017c = ko.observable(new CScreen(self.viewModel017b));
            self.viewModel017d = ko.observable(new DScreen());
            self.viewModel017e = ko.observable(new EScreen());
            self.viewModel017f = ko.observable(new FScreen());
            self.viewModel017g = ko.observable(new GScreen());
            self.viewModel017h = ko.observable(new HScreen());
            self.viewModel017i = ko.observable(new IScreen());
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            var itemsTreeGridHistory = [];
            var itemsTreeGridFormula = [];
            var nodesTreeGrid = [];
            // bind a_lst_001
            // convert FormulaDto to Node objects to fill in the tree grid
            service.getAllFormula().done(function(lstFormulaDto: Array<model.FormulaDto>) {
                if (lstFormulaDto) {
                    let groupsFormulaByCode = _.groupBy(lstFormulaDto, 'formulaCode');
                    let lstFormulaCode = Object.keys(groupsFormulaByCode);

                    _.forEach(lstFormulaCode, (formulaCode) => {
                        let nodeHistory = [];
                        let lstHistoryEachCode = groupsFormulaByCode[formulaCode];
                        for (let orderHistory = 0; orderHistory < lstHistoryEachCode.length; orderHistory++) {
                            nodeHistory.push(
                                new Node(
                                    lstHistoryEachCode[orderHistory].historyId,
                                    nts.uk.time.formatYearMonth(lstHistoryEachCode[orderHistory].startDate)
                                    + ' ~ '
                                    + nts.uk.time.formatYearMonth(lstHistoryEachCode[orderHistory].endDate),
                                    []
                                )
                            );
                        }
                        let nodeFormula = new Node(lstHistoryEachCode[0].formulaCode, lstHistoryEachCode[0].formulaName, self.sortFormulaHistory(nodeHistory));
                        nodesTreeGrid.push(nodeFormula);
                    });
                    self.treeGridHistory().items(nodesTreeGrid);
                    self.treeGridHistory().singleSelectedCode(null);
                    self.isNewMode(true);
                    self.viewModel017b().startYearMonth('');
                    self.viewModel017b().formulaCode('');
                    self.viewModel017b().formulaName('');
                    self.viewModel017b().selectedDifficultyAtr(0);
                    self.viewModel017b().selectedConditionAtr(0);
                    self.viewModel017b().comboBoxUseMaster().selectedCode(1);
                    self.selectedTabASel001('tab-1');
                } else {
                    self.treeGridHistory().items(nodesTreeGrid);
                }
                dfd.resolve();
            }).fail(function(res) {
                // Alert message
                alert(res);
            });

            // Return.
            return dfd.promise();
        }

        sortFormulaHistory(lstFormulaHistory) {
            return lstFormulaHistory.sort(function(formulaHistoryA, formulaHistoryB) {
                return formulaHistoryB.name.split(' ~ ')[0].replace('/', '') - formulaHistoryA.name.split(' ~ ')[0].replace('/', '');
            });
        }

        registerFormulaMaster() {
            var self = this;
            if (self.isNewMode()) {
                let referenceMasterNo = null;
                if (self.viewModel017b().selectedDifficultyAtr() === 0 && self.viewModel017b().selectedConditionAtr() === 0) {
                    referenceMasterNo = 0;
                } else if (self.viewModel017b().selectedDifficultyAtr() === 0 && self.viewModel017b().selectedConditionAtr() === '1') {
                    referenceMasterNo = self.viewModel017b().comboBoxUseMaster().selectedCode();
                }
                let command = {
                    formulaCode: self.viewModel017b().formulaCode(),
                    formulaName: self.viewModel017b().formulaName(),
                    difficultyAtr: self.viewModel017b().selectedDifficultyAtr(),
                    startDate: self.viewModel017b().startYearMonth(),
                    endDate: 999912,
                    conditionAtr: self.viewModel017b().selectedConditionAtr(),
                    refMasterNo: referenceMasterNo
                };

                service.registerFormulaMaster(command)
                    .done(function() {
                        self.start();
                    })
                    .fail(function(res) {
                        alert(res)
                    });
            } else {
                let command = {
                    formulaCode: self.viewModel017b().formulaCode(),
                    formulaName: self.viewModel017b().formulaName(),
                    difficultyAtr: self.viewModel017b().selectedDifficultyAtr(),
                    historyId: self.currentNode().code,
                    easyFormulaDto: [],
                    formulaContent: '',
                    referenceMonthAtr: '',
                    roundAtr: '',
                    roundDigit: ''
                };
                if (command.difficultyAtr === 1) {
                    command.formulaContent = self.viewModel017c().formulaManualContent().textArea();
                    command.referenceMonthAtr = self.viewModel017c().comboBoxReferenceMonthAtr().selectedCode();
                    command.roundAtr = self.viewModel017c().comboBoxRoudingMethod().selectedCode();
                    command.roundDigit = self.viewModel017c().comboBoxRoudingPosition().selectedCode();
                } else {
                    if (self.viewModel017b().selectedConditionAtr() == 0) {
                        let noneConditionalEasyFormulaDetail = self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail();
                        noneConditionalEasyFormulaDetail.easyFormulaCode = '000';
                        noneConditionalEasyFormulaDetail.maxLimitValue = 0;
                        noneConditionalEasyFormulaDetail.minLimitValue = 0;
                        command.easyFormulaDto.push({
                            easyFormulaCode: '000',
                            value: self.viewModel017c().noneConditionalEasyFormula().easyFormulaFixMoney(),
                            referenceMasterNo: "0000000000",
                            formulaDetail: noneConditionalEasyFormulaDetail,
                            fixFormulaAtr: self.viewModel017c().noneConditionalEasyFormula().selectedRuleCodeEasySettings()
                        });
                    } else if (self.viewModel017b().selectedConditionAtr() == 1 && self.viewModel017b().comboBoxUseMaster().selectedCode() < 6) {
                        let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                        defaultEasyFormulaDetail.easyFormulaCode = '000';
                        defaultEasyFormulaDetail.maxLimitValue = 0;
                        defaultEasyFormulaDetail.minLimitValue = 0;
                        command.easyFormulaDto.push(
                            {
                                easyFormulaCode: '000',
                                value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                referenceMasterNo: "000000000" + self.viewModel017c().useMasterCode(),
                                formulaDetail: defaultEasyFormulaDetail,
                                fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                            }
                        );
                        if (command.easyFormulaDto[0].fixFormulaAtr !== 0) {
                            command.easyFormulaDto[0].value = 0;
                        }
                    } else if (self.viewModel017b().selectedConditionAtr() == 1 && self.viewModel017b().comboBoxUseMaster().selectedCode() === '6') {
                        let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                        defaultEasyFormulaDetail.easyFormulaCode = '000';
                        defaultEasyFormulaDetail.maxLimitValue = 0;
                        defaultEasyFormulaDetail.minLimitValue = 0;
                        command.easyFormulaDto.push(
                            {
                                easyFormulaCode: '000',
                                value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                referenceMasterNo: "0000000060",
                                formulaDetail: defaultEasyFormulaDetail,
                                fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                            }
                        );
                        if (command.easyFormulaDto[0].fixFormulaAtr !== '0') {
                            command.easyFormulaDto[0].value = 0;
                        }
                        if (self.viewModel017c().monthlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                            let monthlyEasyFormulaDetail = self.viewModel017c().monthlyEasyFormula().easyFormulaDetail();
                            monthlyEasyFormulaDetail.easyFormulaCode = '001';
                            monthlyEasyFormulaDetail.maxLimitValue = 0;
                            monthlyEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '001',
                                    value: self.viewModel017c().monthlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000061",
                                    formulaDetail: monthlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().monthlyEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        } else {
                            let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '001';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '001',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000061",
                                    formulaDetail: defaultEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        }
                        if (self.viewModel017c().dailyMonthlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                            let dailyMonthlyEasyFormulaDetail = self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaDetail();
                            dailyMonthlyEasyFormulaDetail.easyFormulaCode = '002';
                            dailyMonthlyEasyFormulaDetail.maxLimitValue = 0;
                            dailyMonthlyEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '002',
                                    value: self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000062",
                                    formulaDetail: dailyMonthlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().dailyMonthlyEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        } else {
                            let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '002';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '002',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000062",
                                    formulaDetail: defaultEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        }
                        if (self.viewModel017c().dailyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                            let dailyEasyFormulaDetail = self.viewModel017c().dailyEasyFormula().easyFormulaDetail();
                            dailyEasyFormulaDetail.easyFormulaCode = '003';
                            dailyEasyFormulaDetail.maxLimitValue = 0;
                            dailyEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '003',
                                    value: self.viewModel017c().dailyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000063",
                                    formulaDetail: dailyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().dailyEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        } else {
                            let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '003';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '003',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000063",
                                    formulaDetail: defaultEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        }
                        if (self.viewModel017c().hourlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                            let hourlyEasyFormulaDetail = self.viewModel017c().hourlyEasyFormula().easyFormulaDetail();
                            hourlyEasyFormulaDetail.easyFormulaCode = '004';
                            hourlyEasyFormulaDetail.maxLimitValue = 0;
                            hourlyEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '004',
                                    value: self.viewModel017c().hourlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000064",
                                    formulaDetail: hourlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().hourlyEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        } else {
                            let defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '004';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push(
                                {
                                    easyFormulaCode: '004',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000064",
                                    formulaDetail: defaultEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                }
                            );
                        }
                        if (command.easyFormulaDto[1].fixFormulaAtr !== 0) {
                            command.easyFormulaDto[1].value = 0;
                        }
                        if (command.easyFormulaDto[2].fixFormulaAtr !== 0) {
                            command.easyFormulaDto[2].value = 0;
                        }
                        if (command.easyFormulaDto[3].fixFormulaAtr !== 0) {
                            command.easyFormulaDto[3].value = 0;
                        }
                        if (command.easyFormulaDto[4].fixFormulaAtr !== 0) {
                            command.easyFormulaDto[4].value = 0;
                        }
                    }
                }
                service.updateFormulaMaster(command)
                    .done(function() {
                        self.start();
                    })
                    .fail(function(res) {
                        alert(res)
                    });
            }
        }

        openDialogJ() {
            var self = this;
            let lastestHistoryNode = self.currentParentNode().childs[0];
            let lastestHistory = lastestHistoryNode.name;
            let lastestYearMonth = lastestHistory.split("~");
            let lastestStartYm = lastestYearMonth[0].trim();
            let param = {
                formulaCode: self.viewModel017b().formulaCode(),
                formulaName: self.viewModel017b().formulaName(),
                startYm: lastestStartYm,
                difficultyAtr: self.viewModel017b().selectedDifficultyAtr(),
                conditionAtr: self.viewModel017b().selectedConditionAtr(),
                referenceMasterNo: self.viewModel017b().comboBoxUseMaster().selectedCode()
            };
            nts.uk.ui.windows.setShared('paramFromScreenA', param);
            nts.uk.ui.windows.sub.modal('/view/qmm/017/j/index.xhtml', { title: '履歴の追加', width: 540, height: 545 }).onClosed(() => {
                self.start();
            });
        }

        openDialogK() {
            var self = this;
            let currentHistory = self.currentNode().name;
            let currentYearMonth = currentHistory.split(" ~ ");
            let currentStartYm = currentYearMonth[0];
            let currentEndYm = currentYearMonth[1];
            let param = {
                formulaCode: self.viewModel017b().formulaCode(),
                formulaName: self.viewModel017b().formulaName(),
                historyId: self.currentNode().code,
                startYm: currentStartYm,
                endYm: currentEndYm,
                difficultyAtr: self.viewModel017b().selectedDifficultyAtr()
            };
            nts.uk.ui.windows.setShared('paramFromScreenA', param);
            nts.uk.ui.windows.sub.modal('/view/qmm/017/k/index.xhtml', { title: '履歴の編集', width: 540, height: 380 }).onClosed(() => {
                self.start();
            });
        }
    }

    function findNode(targetNode: Node, searchString: string) {
        if (targetNode.code === searchString) {
            if (targetNode.childs.length > 0) {
                return targetNode.childs[0];
            } else if (targetNode.childs.length === 0) {
                return targetNode;
            }
        }
        for (let count = 0; count < targetNode.childs.length; count++) {
            let foundNode = findNode(targetNode.childs[count], searchString);
            if (foundNode) {
                return foundNode;
            }
        }
    }

    export class TreeGrid {
        index: number;
        items: any;
        selectedCode: any;
        singleSelectedCode: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.items = ko.observableArray([new Node('001', 'Formula 1', [
                new Node('0001-2', '2017/03 ~ 9999/12', []),
                new Node('0001-1', '2016/06 ~ 2017/03', [])
            ]), new Node('002', 'Formula 2', [
                new Node('0002-2', '2017/03 ~ 9999/12', []),
                new Node('0002-1', '2016/06 ~ 2017/03', [])
            ])
            ]);
            self.selectedCode = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.index = 0;
        }
    }

    export class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            if (childs.length > 0) {
                self.nodeText = self.code + ' ' + self.name;
            } else if (childs.length === 0) {
                self.nodeText = self.name;
            }
            self.childs = childs;
        }
    }


}

