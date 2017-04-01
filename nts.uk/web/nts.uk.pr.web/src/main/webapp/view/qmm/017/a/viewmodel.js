var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.isNewMode = ko.observable(true);
                self.isUpdateMode = ko.observable(false);
                self.isNewMode.subscribe(function (val) {
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
                self.treeGridHistory().singleSelectedCode.subscribe(function (codeChange) {
                    if (codeChange !== null) {
                        var currentNode = null;
                        var currentParentNode = null;
                        self.isNewMode(false);
                        for (var order = 0; order < self.treeGridHistory().items().length; order++) {
                            var foundNode = findNode(self.treeGridHistory().items()[order], self.treeGridHistory().singleSelectedCode());
                            if (foundNode) {
                                self.currentNode(foundNode);
                                self.currentParentNode(self.treeGridHistory().items()[order]);
                            }
                        }
                        var rangeYearMonth = self.currentNode().name.split('~');
                        self.startYearMonth(rangeYearMonth[0].trim());
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
                                else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 0) {
                                    self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail(currentFormulaDetail.easyFormula[0].formulaEasyDetail);
                                    self.viewModel017c().noneConditionalEasyFormula().easyFormulaName(currentFormulaDetail.easyFormula[0].formulaEasyDetail.easyFormulaName);
                                }
                                else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 1 && currentFormula.refMasterNo < 6) {
                                    self.viewModel017c().defaultEasyFormula(new qmm017.EasyFormula(0));
                                    self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(currentFormulaDetail.easyFormula[0].value);
                                    self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings(currentFormulaDetail.easyFormula[0].fixFormulaAtr);
                                    self.viewModel017c().defaultEasyFormula().easyFormulaDetail(currentFormulaDetail.easyFormula[0].formulaEasyDetail);
                                    self.viewModel017c().defaultEasyFormula().easyFormulaName(currentFormulaDetail.easyFormula[0].formulaEasyDetail.easyFormulaName);
                                }
                                else if (currentFormula.difficultyAtr === 0 && currentFormula.conditionAtr === 1 && currentFormula.refMasterNo === 6) {
                                    self.viewModel017c().defaultEasyFormula(new qmm017.EasyFormula(0));
                                    _.forEach(currentFormulaDetail.easyFormula, function (easyFormula) {
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
                                .fail(function (res) {
                                alert(res);
                            });
                            self.viewModel017b().selectedConditionAtr(currentFormula.conditionAtr);
                            self.viewModel017b().comboBoxUseMaster().selectedCode(currentFormula.refMasterNo.toString());
                        })
                            .fail(function (res) {
                            alert(res);
                        });
                    }
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
                        self.treeGridHistory().singleSelectedCode(null);
                        self.isNewMode(true);
                        self.viewModel017b().startYearMonth('');
                        self.viewModel017b().formulaCode('');
                        self.viewModel017b().formulaName('');
                        self.viewModel017b().selectedDifficultyAtr(0);
                        self.viewModel017b().selectedConditionAtr(0);
                        self.viewModel017b().comboBoxUseMaster().selectedCode(1);
                        self.selectedTabASel001('tab-1');
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
                            var noneConditionalEasyFormulaDetail = self.viewModel017c().noneConditionalEasyFormula().easyFormulaDetail();
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
                        }
                        else if (self.viewModel017b().selectedConditionAtr() == 1 && self.viewModel017b().comboBoxUseMaster().selectedCode() < 6) {
                            var defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '000';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push({
                                easyFormulaCode: '000',
                                value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                referenceMasterNo: "000000000" + self.viewModel017c().useMasterCode(),
                                formulaDetail: defaultEasyFormulaDetail,
                                fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                            });
                        }
                        else if (self.viewModel017b().selectedConditionAtr() == 1 && self.viewModel017b().comboBoxUseMaster().selectedCode() === '6') {
                            var defaultEasyFormulaDetail = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                            defaultEasyFormulaDetail.easyFormulaCode = '000';
                            defaultEasyFormulaDetail.maxLimitValue = 0;
                            defaultEasyFormulaDetail.minLimitValue = 0;
                            command.easyFormulaDto.push({
                                easyFormulaCode: '000',
                                value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                referenceMasterNo: "0000000060",
                                formulaDetail: defaultEasyFormulaDetail,
                                fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                            });
                            if (self.viewModel017c().monthlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                                var monthlyEasyFormulaDetail = self.viewModel017c().monthlyEasyFormula().easyFormulaDetail();
                                monthlyEasyFormulaDetail.easyFormulaCode = '001';
                                monthlyEasyFormulaDetail.maxLimitValue = 0;
                                monthlyEasyFormulaDetail.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '001',
                                    value: self.viewModel017c().monthlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000061",
                                    formulaDetail: monthlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().monthlyEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            else {
                                var defaultEasyFormulaDetail_1 = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                                defaultEasyFormulaDetail_1.easyFormulaCode = '001';
                                defaultEasyFormulaDetail_1.maxLimitValue = 0;
                                defaultEasyFormulaDetail_1.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '001',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000061",
                                    formulaDetail: defaultEasyFormulaDetail_1,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            if (self.viewModel017c().dailyMonthlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                                var dailyMonthlyEasyFormulaDetail = self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaDetail();
                                dailyMonthlyEasyFormulaDetail.easyFormulaCode = '002';
                                dailyMonthlyEasyFormulaDetail.maxLimitValue = 0;
                                dailyMonthlyEasyFormulaDetail.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '002',
                                    value: self.viewModel017c().dailyMonthlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000062",
                                    formulaDetail: dailyMonthlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().dailyMonthlyEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            else {
                                var defaultEasyFormulaDetail_2 = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                                defaultEasyFormulaDetail_2.easyFormulaCode = '002';
                                defaultEasyFormulaDetail_2.maxLimitValue = 0;
                                defaultEasyFormulaDetail_2.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '002',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000062",
                                    formulaDetail: defaultEasyFormulaDetail_2,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            if (self.viewModel017c().dailyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                                var dailyEasyFormulaDetail = self.viewModel017c().dailyEasyFormula().easyFormulaDetail();
                                dailyEasyFormulaDetail.easyFormulaCode = '003';
                                dailyEasyFormulaDetail.maxLimitValue = 0;
                                dailyEasyFormulaDetail.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '003',
                                    value: self.viewModel017c().dailyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000063",
                                    formulaDetail: dailyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().dailyEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            else {
                                var defaultEasyFormulaDetail_3 = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                                defaultEasyFormulaDetail_3.easyFormulaCode = '003';
                                defaultEasyFormulaDetail_3.maxLimitValue = 0;
                                defaultEasyFormulaDetail_3.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '003',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000063",
                                    formulaDetail: defaultEasyFormulaDetail_3,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            if (self.viewModel017c().hourlyEasyFormula().selectedRuleCodeEasySettings() !== '2') {
                                var hourlyEasyFormulaDetail = self.viewModel017c().hourlyEasyFormula().easyFormulaDetail();
                                hourlyEasyFormulaDetail.easyFormulaCode = '004';
                                hourlyEasyFormulaDetail.maxLimitValue = 0;
                                hourlyEasyFormulaDetail.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '004',
                                    value: self.viewModel017c().hourlyEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000064",
                                    formulaDetail: hourlyEasyFormulaDetail,
                                    fixFormulaAtr: self.viewModel017c().hourlyEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
                            else {
                                var defaultEasyFormulaDetail_4 = self.viewModel017c().defaultEasyFormula().easyFormulaDetail();
                                defaultEasyFormulaDetail_4.easyFormulaCode = '004';
                                defaultEasyFormulaDetail_4.maxLimitValue = 0;
                                defaultEasyFormulaDetail_4.minLimitValue = 0;
                                command.easyFormulaDto.push({
                                    easyFormulaCode: '004',
                                    value: self.viewModel017c().defaultEasyFormula().easyFormulaFixMoney(),
                                    referenceMasterNo: "0000000064",
                                    formulaDetail: defaultEasyFormulaDetail_4,
                                    fixFormulaAtr: self.viewModel017c().defaultEasyFormula().selectedRuleCodeEasySettings()
                                });
                            }
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
