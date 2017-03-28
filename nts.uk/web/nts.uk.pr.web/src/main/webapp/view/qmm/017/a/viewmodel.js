var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.isNewMode = ko.observable(true);
                self.treeGridHistory = ko.observable(new TreeGrid());
                self.a_sel_001 = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(false), visible: ko.observable(true) }
                ]);
                self.selectedTabASel001 = ko.observable('tab-1');
                self.isNewMode = ko.observable(true);
                self.startYearMonth = ko.observable('');
                self.currentNode = ko.observable(null);
                self.currentParentNode = ko.observable(null);
                self.treeGridHistory().singleSelectedCode.subscribe(function (codeChange) {
                    var currentNode = null;
                    var currentParentNode = null;
                    for (var order = 0; order < self.treeGridHistory().items().length; order++) {
                        var foundNode = findNode(self.treeGridHistory().items()[order], self.treeGridHistory().singleSelectedCode());
                        if (foundNode) {
                            self.currentNode(foundNode);
                            self.currentParentNode(self.treeGridHistory().items()[order]);
                        }
                    }
                    var rangeYearMonth = self.currentNode().name.split('~');
                    self.startYearMonth(rangeYearMonth[0].trim());
                    if (codeChange) {
                        self.isNewMode(false);
                    }
                    self.a_sel_001()[1].enable(true);
                    self.viewModel017b().startYearMonth(rangeYearMonth[0].trim());
                    self.viewModel017b().formulaCode(self.currentParentNode().code);
                    self.viewModel017b().formulaName(self.currentParentNode().name);
                    qmm017.service.findFormula(self.currentParentNode().code, self.currentNode().code)
                        .done(function (currentFormula) {
                        self.viewModel017b().selectedDifficultyAtr(currentFormula.difficultyAtr);
                        qmm017.service.getFormulaDetail(self.currentParentNode().code, self.currentNode().code, currentFormula.difficultyAtr)
                            .done(function (currentFormulaDetail) {
                            if (currentFormula && currentFormula.difficultyAtr === 1) {
                                self.viewModel017c().formulaManualContent().textArea(currentFormulaDetail.formulaContent);
                                self.viewModel017c().comboBoxReferenceMonthAtr().selectedCode(currentFormulaDetail.referenceMonthAtr);
                                self.viewModel017c().comboBoxRoudingMethod().selectedCode(currentFormulaDetail.roundAtr);
                                self.viewModel017c().comboBoxRoudingPosition().selectedCode(currentFormulaDetail.roundDigit);
                            }
                            else if (currentFormula && currentFormula.difficultyAtr === 0) {
                                self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail(currentFormulaDetail.easyFormula[0].formulaEasyDetail);
                                self.viewModel017c().noneConditionalEasyFormula().easyFormulaName(currentFormulaDetail.easyFormula[0].formulaEasyDetail.easyFormulaName);
                            }
                        })
                            .fail(function (res) {
                            alert(res);
                        });
                        self.viewModel017b().selectedConditionAtr(currentFormula.conditionAtr);
                        self.viewModel017b().comboBoxUseMaster().selectedCode(currentFormula.refMasterNo.toString());
                    })
                        .fail(function (res) {
                        alert(res);
                    });
                });
                self.viewModel017b = ko.observable(new qmm017.BScreen(self));
                self.viewModel017c = ko.observable(new qmm017.CScreen(self.viewModel017b));
                self.viewModel017d = ko.observable(new qmm017.DScreen());
                self.viewModel017e = ko.observable(new qmm017.EScreen());
                self.viewModel017f = ko.observable(new qmm017.FScreen());
                self.viewModel017g = ko.observable(new qmm017.GScreen());
                self.viewModel017h = ko.observable(new qmm017.HScreen());
                self.viewModel017i = ko.observable(new qmm017.IScreen());
            }
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                var itemsTreeGridHistory = [];
                var itemsTreeGridFormula = [];
                var nodesTreeGrid = [];
                qmm017.service.getAllFormula().done(function (lstFormulaDto) {
                    if (lstFormulaDto) {
                        var groupsFormulaByCode_1 = _.groupBy(lstFormulaDto, 'formulaCode');
                        var lstFormulaCode = Object.keys(groupsFormulaByCode_1);
                        _.forEach(lstFormulaCode, function (formulaCode) {
                            var nodeHistory = [];
                            var lstHistoryEachCode = groupsFormulaByCode_1[formulaCode];
                            for (var orderHistory = 0; orderHistory < lstHistoryEachCode.length; orderHistory++) {
                                nodeHistory.push(new Node(lstHistoryEachCode[orderHistory].historyId, nts.uk.time.formatYearMonth(lstHistoryEachCode[orderHistory].startDate)
                                    + ' ~ '
                                    + nts.uk.time.formatYearMonth(lstHistoryEachCode[orderHistory].endDate), []));
                            }
                            var nodeFormula = new Node(lstHistoryEachCode[0].formulaCode, lstHistoryEachCode[0].formulaName, self.sortFormulaHistory(nodeHistory));
                            nodesTreeGrid.push(nodeFormula);
                        });
                        self.treeGridHistory().items(nodesTreeGrid);
                        self.isNewMode(true);
                        self.treeGridHistory().singleSelectedCode(null);
                        self.viewModel017b().startYearMonth('');
                        self.viewModel017b().formulaCode('');
                        self.viewModel017b().formulaName('');
                        self.viewModel017b().selectedDifficultyAtr(0);
                        self.viewModel017b().selectedConditionAtr(0);
                        self.viewModel017b().comboBoxUseMaster().selectedCode(1);
                        self.treeGridHistory().singleSelectedCode(null);
                    }
                    else {
                        self.treeGridHistory().items(nodesTreeGrid);
                    }
                    dfd.resolve();
                }).fail(function (res) {
                    alert(res);
                });
                return dfd.promise();
            };
            ScreenModel.prototype.sortFormulaHistory = function (lstFormulaHistory) {
                return lstFormulaHistory.sort(function (formulaHistoryA, formulaHistoryB) {
                    return formulaHistoryB.name.split(' ~ ')[0].replace('/', '') - formulaHistoryA.name.split(' ~ ')[0].replace('/', '');
                });
            };
            ScreenModel.prototype.registerFormulaMaster = function () {
                var self = this;
                if (self.isNewMode()) {
                    var referenceMasterNo = null;
                    if (self.viewModel017b().selectedDifficultyAtr() === 0 && self.viewModel017b().selectedConditionAtr() === 0) {
                        referenceMasterNo = 0;
                    }
                    else if (self.viewModel017b().selectedDifficultyAtr() === 0 && self.viewModel017b().selectedConditionAtr() === '1') {
                        referenceMasterNo = self.viewModel017b().comboBoxUseMaster().selectedCode();
                    }
                    var command = {
                        formulaCode: self.viewModel017b().formulaCode(),
                        formulaName: self.viewModel017b().formulaName(),
                        difficultyAtr: self.viewModel017b().selectedDifficultyAtr(),
                        startDate: self.viewModel017b().startYearMonth(),
                        endDate: 999912,
                        conditionAtr: self.viewModel017b().selectedConditionAtr(),
                        refMasterNo: referenceMasterNo
                    };
                    qmm017.service.registerFormulaMaster(command)
                        .done(function () {
                        self.start();
                    })
                        .fail(function (res) {
                        alert(res);
                    });
                }
                else {
                    var command = {
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
                    }
                    else {
                        if (self.viewModel017b().selectedConditionAtr() == 0) {
                            command.easyFormulaDto.push({
                                easyFormulaCode: '000',
                                value: self.viewModel017c().noneConditionalEasyFormula().easyFormulaFixMoney(),
                                referenceMasterNo: "0000000000",
                                formulaDetail: {
                                    easyFormulaCode: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().easyFormulaCode,
                                    easyFormulaName: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().easyFormulaName,
                                    easyFormulaTypeAtr: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().easyFormulaTypeAtr,
                                    baseFixedAmount: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().baseFixedAmount,
                                    baseAmountDevision: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().baseAmountDevision,
                                    baseFixedValue: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().baseFixedValue,
                                    baseValueDevision: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().baseValueDevision,
                                    premiumRate: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().premiumRate,
                                    roundProcessingDevision: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().roundProcessingDevision,
                                    coefficientDivision: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().coefficientDivision,
                                    coefficientFixedValue: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().coefficientFixedValue,
                                    adjustmentDevision: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().adjustmentDevision,
                                    totalRounding: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().totalRounding,
                                    maxLimitValue: 0,
                                    minLimitValue: 0,
                                    referenceItemCodes: self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail().referenceItemCodes
                                },
                                fixFormulaAtr: self.viewModel017c().noneConditionalEasyFormula().selectedRuleCodeEasySettings()
                            });
                            debugger;
                        }
                        else if (self.viewModel017b().selectedConditionAtr() == 0 && self.viewModel017b().comboBoxUseMaster().selectedCode() < 6) {
                        }
                        else if (self.viewModel017b().selectedConditionAtr() == 0 && self.viewModel017b().comboBoxUseMaster().selectedCode())
                             = 6;
                        {
                        }
                    }
                    qmm017.service.updateFormulaMaster(command)
                        .done(function () {
                        self.start();
                    })
                        .fail(function (res) {
                        alert(res);
                    });
                }
            };
            ScreenModel.prototype.openDialogJ = function () {
                var self = this;
                var lastestHistoryNode = self.currentParentNode().childs[0];
                var lastestHistory = lastestHistoryNode.name;
                var lastestYearMonth = lastestHistory.split("~");
                var lastestStartYm = lastestYearMonth[0].trim();
                var param = {
                    formulaCode: self.viewModel017b().formulaCode(),
                    formulaName: self.viewModel017b().formulaName(),
                    startYm: lastestStartYm,
                    difficultyAtr: self.viewModel017b().selectedDifficultyAtr(),
                    conditionAtr: self.viewModel017b().selectedConditionAtr(),
                    referenceMasterNo: self.viewModel017b().comboBoxUseMaster().selectedCode()
                };
                nts.uk.ui.windows.setShared('paramFromScreenA', param);
                nts.uk.ui.windows.sub.modal('/view/qmm/017/j/index.xhtml', { title: '履歴の追加', width: 540, height: 545 }).onClosed(function () {
                    self.start();
                });
            };
            ScreenModel.prototype.openDialogK = function () {
                var self = this;
                var currentHistory = self.currentNode().name;
                var currentYearMonth = currentHistory.split(" ~ ");
                var currentStartYm = currentYearMonth[0];
                var currentEndYm = currentYearMonth[1];
                var param = {
                    formulaCode: self.viewModel017b().formulaCode(),
                    formulaName: self.viewModel017b().formulaName(),
                    historyId: self.currentNode().code,
                    startYm: currentStartYm,
                    endYm: currentEndYm,
                    difficultyAtr: self.viewModel017b().selectedDifficultyAtr()
                };
                nts.uk.ui.windows.setShared('paramFromScreenA', param);
                nts.uk.ui.windows.sub.modal('/view/qmm/017/k/index.xhtml', { title: '履歴の編集', width: 540, height: 380 }).onClosed(function () {
                    self.start();
                });
            };
            return ScreenModel;
        }());
        qmm017.ScreenModel = ScreenModel;
        function findNode(targetNode, searchString) {
            if (targetNode.code === searchString) {
                if (targetNode.childs.length > 0) {
                    return targetNode.childs[0];
                }
                else if (targetNode.childs.length === 0) {
                    return targetNode;
                }
            }
            for (var count = 0; count < targetNode.childs.length; count++) {
                var foundNode = findNode(targetNode.childs[count], searchString);
                if (foundNode) {
                    return foundNode;
                }
            }
        }
        var TreeGrid = (function () {
            function TreeGrid() {
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
            return TreeGrid;
        }());
        qmm017.TreeGrid = TreeGrid;
        var Node = (function () {
            function Node(code, name, childs) {
                var self = this;
                self.code = code;
                self.name = name;
                if (childs.length > 0) {
                    self.nodeText = self.code + ' ' + self.name;
                }
                else if (childs.length === 0) {
                    self.nodeText = self.name;
                }
                self.childs = childs;
            }
            return Node;
        }());
        qmm017.Node = Node;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
