var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var CScreen = (function () {
            function CScreen(data) {
                var self = this;
                self.selectedDifficultyAtr = ko.observable(data.viewModel017b().selectedDifficultyAtr());
                self.selectedConditionAtr = ko.observable(data.viewModel017b().selectedConditionAtr());
                self.formulaCode = ko.observable('');
                self.formulaName = ko.observable('');
                data.viewModel017b().selectedDifficultyAtr.subscribe(function (val) {
                    self.selectedDifficultyAtr(val);
                });
                data.viewModel017b().selectedConditionAtr.subscribe(function (val) {
                    self.selectedConditionAtr(val);
                });
                data.viewModel017b().formulaCode.subscribe(function (val) {
                    self.formulaCode(val);
                });
                data.viewModel017b().formulaName.subscribe(function (val) {
                    self.formulaName(val);
                });
                self.useMasterCode = ko.observable(data.viewModel017b().comboBoxUseMaster().selectedCode());
                self.useMasterName = ko.observable('');
                data.viewModel017b().comboBoxUseMaster().selectedCode.subscribe(function (codeChange) {
                    var useMasterFound = _.find(data.viewModel017b().comboBoxUseMaster().itemList(), function (item) {
                        return item.code == codeChange;
                    });
                    self.useMasterCode(data.viewModel017b().comboBoxUseMaster().selectedCode());
                    if (useMasterFound) {
                        self.useMasterName(useMasterFound.name);
                    }
                });
                var lstReferenceMonthAtr = [
                    { code: 0, name: '当月' },
                    { code: 1, name: '1ヶ月前' },
                    { code: 2, name: '2ヶ月前' },
                    { code: 3, name: '3ヶ月前' },
                    { code: 4, name: '4ヶ月前' },
                    { code: 5, name: '5ヶ月前' },
                    { code: 6, name: '6ヶ月前' },
                    { code: 7, name: '7ヶ月前' },
                    { code: 8, name: '8ヶ月前' },
                    { code: 9, name: '9ヶ月前' },
                    { code: 10, name: '10ヶ月前' },
                    { code: 11, name: '11ヶ月前' },
                    { code: 12, name: '12ヶ月前' },
                ];
                var lstRoudingMethod = [
                    { code: 0, name: '切上げ' },
                    { code: 1, name: '切捨て' },
                    { code: 2, name: '一捨二入' },
                    { code: 3, name: '二捨三入' },
                    { code: 4, name: '三捨四入' },
                    { code: 5, name: '四捨五入' },
                    { code: 6, name: '五捨六入' },
                    { code: 7, name: '六捨七入' },
                    { code: 8, name: '七捨八入' },
                    { code: 9, name: '八捨九入' },
                ];
                var lstRoudingPostion = [
                    { code: 0, name: '1円丸め' },
                    { code: 1, name: '10円丸め' },
                    { code: 2, name: '100円丸め' },
                    { code: 3, name: '1000円丸め' }
                ];
                self.formulaManualContent = ko.observable(new TextEditor());
                self.itemsTab = ko.observableArray([
                    { id: 'tab-1', title: '明細・勤怠', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '単価', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: '関数', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: 'システム変数', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-5', title: '個人情報', content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-6', title: '計算式', content: '.tab-content-6', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-7', title: '賃金テーブル', content: '.tab-content-7', style: 'margin-left: 50px;', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.comboBoxReferenceMonthAtr = ko.observable(new ComboBox(lstReferenceMonthAtr, true, false));
                self.comboBoxRoudingMethod = ko.observable(new ComboBox(lstRoudingMethod, true, false));
                self.comboBoxRoudingPosition = ko.observable(new ComboBox(lstRoudingPostion, true, false));
                self.selectedTabCSel006 = ko.observable('tab-1');
                self.noneConditionalEasyFormula = ko.observable(new EasyFormula(0, data.viewModel017b));
                self.defaultEasyFormula = ko.observable(new EasyFormula(0, data.viewModel017b));
                self.monthlyEasyFormula = ko.observable(new EasyFormula(1, data.viewModel017b));
                self.dailyMonthlyEasyFormula = ko.observable(new EasyFormula(1, data.viewModel017b));
                self.dailyEasyFormula = ko.observable(new EasyFormula(1, data.viewModel017b));
                self.hourlyEasyFormula = ko.observable(new EasyFormula(1, data.viewModel017b));
            }
            CScreen.prototype.undo = function () {
                document.execCommand("undo", false, null);
            };
            CScreen.prototype.redo = function () {
                document.execCommand("redo", false, null);
            };
            CScreen.prototype.validateTextArea = function () {
                var self = this;
                self.formulaManualContent().testError();
            };
            CScreen.prototype.setAllFixValued = function () {
                var self = this;
                self.monthlyEasyFormula().selectedRuleCodeEasySettings('0');
                self.dailyMonthlyEasyFormula().selectedRuleCodeEasySettings('0');
                self.dailyEasyFormula().selectedRuleCodeEasySettings('0');
                self.hourlyEasyFormula().selectedRuleCodeEasySettings('0');
            };
            CScreen.prototype.setAllDetail = function () {
                var self = this;
                self.monthlyEasyFormula().selectedRuleCodeEasySettings('1');
                self.dailyMonthlyEasyFormula().selectedRuleCodeEasySettings('1');
                self.dailyEasyFormula().selectedRuleCodeEasySettings('1');
                self.hourlyEasyFormula().selectedRuleCodeEasySettings('1');
            };
            CScreen.prototype.pasteValue = function (targetString) {
                var self = this;
                var currentTextArea = self.formulaManualContent().textArea();
                self.formulaManualContent().textArea(self.formulaManualContent().insertString(currentTextArea, targetString, $("#input-text")[0].selectionStart));
            };
            return CScreen;
        }());
        qmm017.CScreen = CScreen;
        var EasyFormula = (function () {
            function EasyFormula(mode, root) {
                var self = this;
                self.startYm = ko.observable(root().startYearMonth());
                root().startYearMonth.subscribe(function (yearMonth) {
                    self.startYm(yearMonth);
                });
                if (mode == 0) {
                    self.roundingRulesEasySettings = ko.observableArray([
                        { code: '0', name: '固定値' },
                        { code: '1', name: '計算式' }
                    ]);
                    self.selectedRuleCodeEasySettings = ko.observable('0');
                }
                else if (mode == 1) {
                    self.roundingRulesEasySettings = ko.observableArray([
                        { code: '0', name: '固定値' },
                        { code: '1', name: '計算式' },
                        { code: '2', name: '既定計算式' }
                    ]);
                    self.selectedRuleCodeEasySettings = ko.observable('2');
                }
                self.easyFormulaFixMoney = ko.observable(0);
                self.easyFormulaDetail = ko.observable(new nts.qmm017.model.FormulaEasyDetailDto());
                self.easyFormulaName = ko.observable('');
            }
            EasyFormula.prototype.openDialogL = function () {
                var self = this;
                var param = {
                    isUpdate: (self.easyFormulaName() !== '' && self.easyFormulaName() !== null),
                    dirtyData: self.easyFormulaDetail(),
                    startYm: self.startYm()
                };
                nts.uk.ui.windows.setShared('paramFromScreenC', param);
                nts.uk.ui.windows.sub.modal('/view/qmm/017/l/index.xhtml', { title: 'かんたん計算式の登録', width: 650, height: 750 }).onClosed(function () {
                    if (nts.uk.ui.windows.getShared('easyFormulaDetail')) {
                        self.easyFormulaDetail(nts.uk.ui.windows.getShared('easyFormulaDetail'));
                        self.easyFormulaName(self.easyFormulaDetail().easyFormulaName);
                    }
                });
            };
            return EasyFormula;
        }());
        qmm017.EasyFormula = EasyFormula;
        var ComboBox = (function () {
            function ComboBox(data, isEnable, isEditable) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(0);
                self.isEnable = ko.observable(isEnable);
                self.isEditable = ko.observable(isEditable);
            }
            return ComboBox;
        }());
        qmm017.ComboBox = ComboBox;
        function showError(event) {
            var self = this;
            var currentString = $("#input-text").val();
            var selectValue = $(self).attr("message");
            if (selectValue !== undefined) {
                $("#error-message").text(selectValue);
                var position = $("#input-containner").position();
                $("#error-containner").css({
                    "top": (event.data.pageY - position.top - 93) + "px",
                    "left": (event.data.pageX - position.left - 398) + "px"
                });
                $("#error-containner").show();
            }
        }
        var ItemModelTextEditor = (function () {
            function ItemModelTextEditor(code, name, description) {
                this.code = ko.observable(code);
                this.name = ko.observable(name);
                this.description = ko.observable(description);
                this.text = ko.computed(function () {
                    return this.code() + "  " + this.name();
                }, this).extend({ deferred: true });
            }
            return ItemModelTextEditor;
        }());
        qmm017.ItemModelTextEditor = ItemModelTextEditor;
        var TextEditor = (function () {
            function TextEditor() {
                this.ERROR_BRACKET = "カッコ()の数に誤りがあります。";
                this.ERROR_CONSECUTIVELY = "構文に誤りがあります。{0}と{1}が連続して入力されています。";
                this.ERROR_MUST_CONTAIN_ATSIGN = "「{0}」は利用できない文字列です。";
                this.ERROR_BEFORE_ATSIGN = "「{0}＠」は利用できない文字列です。";
                this.ERROR_DIVIDE_ZERO = "計算式中に「÷0」となる部分が含まれています。";
                this.ERROR_EMPTY_INPUT = "計算式が入力されていません。";
                this.ERROR_NESTED_MORE_THAN_10 = "関数を10回を超えて入れ子にすることはできません。";
                this.ERROR_DIGITS_AFTER_DECIMAL = "「{0}」は小数点以下の桁数が多すぎます。 計算式で利用できる小数は小数点以下5桁までです。";
                this.ERROR_TOO_MUCH_PARAM = "「{0}」の引数が多く指定されています。";
                this.ERROR_NOT_ENOUGH_PARAM = "「{0}」の引数が不足しています。";
                this.ERROR_AFTER_ATSIGN = "「{0}」は利用できない文字列です。";
                this.ERROR_PARAM_TYPE = "「{0}」の第{1}引数の型が不正です。";
                this.listSpecialChar = ["+", "-", "×", "÷", "＾", "（", "）", "＜", "＞", "≦", "≧", "＝", "≠"];
                this.listOperatorChar = ["+", "-", "×", "÷"];
                var self = this;
                self.autoComplete = ko.observableArray([
                    new ItemModelTextEditor('001', '基本給', "description 1"),
                    new ItemModelTextEditor('150', '役職手当', "description 2"),
                    new ItemModelTextEditor('ABC', '基12本ghj給', "description 3"),
                    new ItemModelTextEditor('002', '基本給', "description 4"),
                    new ItemModelTextEditor('153', '役職手当', "description 5"),
                    new ItemModelTextEditor('AB4', '基12本ghj給', "description 6"),
                    new ItemModelTextEditor('003', '基本給', "description 7"),
                    new ItemModelTextEditor('155', '役職手当', "description 8"),
                    new ItemModelTextEditor('AB5', '基12本ghj給', "description 9")
                ]);
                self.showAutoComplete = ko.observable(false);
                self.autoSelected = ko.observable("");
                self.row = ko.observable(1);
                self.col = ko.observable(1);
                self.index = ko.observable(1);
                self.error = ko.observable("");
                self.textArea = ko.observable("");
                self.autoSelected.subscribe(function (value) {
                    if (value) {
                        var currentString = $("#input-text").val();
                        var index = self.index() + 1;
                        var selectValue = value.name();
                        var inserted = self.insertString(currentString, selectValue, index);
                        self.textArea(inserted);
                        $("#input-text").focus();
                        $("#input-text")[0].selectionStart = index + selectValue.length;
                        $("#input-text")[0].selectionEnd = index + selectValue.length;
                        self.testError();
                        $("#auto-complete-containner").hide();
                        self.autoSelected(undefined);
                    }
                }, self);
                self.contentValue = ko.observable("");
                $("#error-containner").hide();
                $(document).on("keyup", "#input-text", function (event) {
                    if (!event.shiftKey && event.keyCode === 16 && event.key === "@") {
                        return;
                    }
                    $("#error-containner").hide();
                    var start = $("#input-text")[0].selectionStart;
                    var end = $("#input-text")[0].selectionEnd;
                    var maxWidthCharacter = 15;
                    if (((event.shiftKey && event.keyCode === 50) || event.keyCode === 192) && event.key === "@") {
                        var currentRow = self.getCurrentPosition(end);
                        self.index(end);
                        $("#auto-complete-containner").show();
                        $("#auto-complete-containner").css({ "top": (currentRow.top + 17) + "px", "left": (currentRow.left + 15) + "px" });
                    }
                    else {
                        $("#auto-complete-containner").hide();
                    }
                });
                $(document).on("mouseleave", "#error-containner", function (event) {
                    $("#error-containner").hide();
                });
                $(document).on("click", "#input-area", function (event) {
                    $("#error-containner").hide();
                    self.observeError($(".error-char"));
                });
            }
            TextEditor.prototype.observeError = function (subjectTags) {
                var currentClickedTag = _.findLast(subjectTags, function (tag) {
                    var x = $(tag).offset();
                    return x.top <= event.pageY && x.left <= event.pageX
                        && (x.left + $(tag).outerWidth()) >= event.pageX
                        && (x.top + $(tag).outerHeight()) >= event.pageY;
                });
                if (currentClickedTag) {
                    $(currentClickedTag).click({ pageX: event.pageX, pageY: event.pageY }, showError);
                    $(currentClickedTag).click();
                }
            };
            TextEditor.prototype.validateBracket = function (bracketTags) {
                var self = this;
                var openBracket = _.remove(bracketTags, function (n) {
                    return $(n).html() === "\（";
                });
                var closeBracket = _.remove(bracketTags, function (n) {
                    return $(n).html() === "\）";
                });
                if (closeBracket.length === 0) {
                    self.markError($(openBracket), self.ERROR_BRACKET, []);
                }
                else if (openBracket.length === 0) {
                    self.markError($(closeBracket), self.ERROR_BRACKET, []);
                }
                else {
                    var openError = [];
                    for (var i = openBracket.length - 1; i >= 0; i--) {
                        var currentOpen = openBracket[i];
                        var id = parseInt($(currentOpen).attr("id").split("-")[1]);
                        var currentClose = _.find(closeBracket, function (a) {
                            return parseInt($(a).attr("id").split("-")[1]) > id;
                        });
                        if (currentClose === undefined) {
                            openError.unshift(currentOpen);
                        }
                        else {
                            closeBracket.splice(closeBracket.indexOf(currentClose), 1);
                        }
                    }
                    self.markError($(openError), self.ERROR_BRACKET, []);
                    self.markError($(closeBracket), self.ERROR_BRACKET, []);
                }
            };
            TextEditor.prototype.validateConsecutively = function (specialChar) {
                var self = this;
                var singleSpecial = {
                    "+": "+",
                    "-": "-",
                    "＾": "＾",
                    "×": "×",
                    "＝": "＝",
                    "≠": "≠",
                    "÷": "÷",
                    "＜": "＜",
                    "＞": "＞",
                    "≦": "≦",
                    "≧": "≧",
                    "＠": "＠"
                };
                for (var i = 0; i < specialChar.length; i++) {
                    var $data = $(specialChar[i]);
                    var charCount = parseInt($data.attr("id").split("-")[1]);
                    var char = $data.text();
                    var single = singleSpecial[char];
                    if (single !== undefined) {
                        var neighborCount = self.countNeighbor(charCount, specialChar, true, true);
                        if (neighborCount > 0) {
                            self.markError($data, self.ERROR_CONSECUTIVELY, [specialChar[i].innerText, specialChar[i].innerText]);
                        }
                    }
                }
            };
            TextEditor.prototype.validateContainAtSign = function (tagsJapaneseChar) {
                var self = this;
                for (var tagOrder = 0; tagOrder < tagsJapaneseChar.length; tagOrder++) {
                    if (tagsJapaneseChar[tagOrder].innerText.indexOf('＠') === -1) {
                        var contentToChars = tagsJapaneseChar[tagOrder].innerText.split('');
                        if (contentToChars[0] !== '”' || contentToChars[contentToChars.length - 1] !== '”') {
                            self.markError($(tagsJapaneseChar[tagOrder]), self.ERROR_MUST_CONTAIN_ATSIGN, [tagsJapaneseChar[tagOrder].innerText]);
                        }
                    }
                }
            };
            TextEditor.prototype.validateBeforeAtSign = function (tagsJapaneseChar) {
                var self = this;
                var lstSyntaxBeforeAtSign = ["支給", "控除", "勤怠", "会社単価", "個人単価", "関数", "変数", "個人", "計算式", "賃金TBL"];
                for (var tagOrder = 0; tagOrder < tagsJapaneseChar.length; tagOrder++) {
                    if (tagsJapaneseChar[tagOrder].innerText.indexOf('＠') !== -1) {
                        var splitByAtSign = tagsJapaneseChar[tagOrder].innerText.split('＠');
                        if (!self.checkEqualInArray(splitByAtSign[0], lstSyntaxBeforeAtSign)) {
                            self.markError($(tagsJapaneseChar[tagOrder]), self.ERROR_BEFORE_ATSIGN, [splitByAtSign[0]]);
                        }
                    }
                }
            };
            TextEditor.prototype.validateDivideZero = function (tagsSpecialChar) {
                var self = this;
                for (var tagOrder = 0; tagOrder < tagsSpecialChar.length; tagOrder++) {
                    if (tagsSpecialChar[tagOrder].innerText === '÷') {
                        var nextTag = $(tagsSpecialChar[tagOrder]).next();
                        if (nextTag) {
                            var contentNextTag = nextTag[0].innerText;
                            if (contentNextTag.trim() === '0') {
                                self.markError($(tagsSpecialChar[tagOrder]), self.ERROR_DIVIDE_ZERO, []);
                            }
                        }
                    }
                }
            };
            TextEditor.prototype.validateEmptyInput = function () {
                var self = this;
                var contentInput = $("#input-text").val();
                if (!contentInput || contentInput.trim() === '') {
                    var html = "<span class='editor-line'><span id='span-1' class='error-char' message='" + self.ERROR_EMPTY_INPUT + "'>  </span></span>";
                    self.contentValue(html);
                }
            };
            TextEditor.prototype.validateNestedMoreThan10 = function (tagsSpecialChar) {
                var self = this;
                var countChar = 0;
                for (var tagOrder = 0; tagOrder < tagsSpecialChar.length; tagOrder++) {
                    if (tagsSpecialChar[tagOrder].innerText === '（') {
                        countChar++;
                        if (countChar > 10) {
                            self.markError($(tagsSpecialChar[tagOrder]), self.ERROR_NESTED_MORE_THAN_10, []);
                        }
                    }
                    else {
                        countChar = 0;
                    }
                }
            };
            TextEditor.prototype.validateDigitsAfterDecimal = function (tagsUnknownChar) {
                var self = this;
                for (var tagOrder = 0; tagOrder < tagsUnknownChar.length; tagOrder++) {
                    if (tagsUnknownChar[tagOrder].innerText >= '0' && tagsUnknownChar[tagOrder].innerText <= '9' && tagsUnknownChar[tagOrder].innerText.indexOf('.') !== -1) {
                        var splitContentTag = tagsUnknownChar[tagOrder].innerText.split('.');
                        if (splitContentTag[1].length > 5) {
                            self.markError($(tagsUnknownChar[tagOrder]), self.ERROR_DIGITS_AFTER_DECIMAL, [tagsUnknownChar[tagOrder].innerText]);
                        }
                    }
                }
            };
            TextEditor.prototype.validateNumberOfParam = function (treeObject) {
                var functionName = treeObject.value.trim();
                var numberOfParam = treeObject.children.length;
                if (functionName === "関数＠条件式") {
                    if (numberOfParam === 3)
                        return 1;
                    else if (numberOfParam > 3)
                        return 2;
                    else if (numberOfParam < 3)
                        return 0;
                }
                else if (functionName === "関数＠かつ") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 1;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠または") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 1;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠四捨五入") {
                    if (numberOfParam === 1)
                        return 1;
                    else if (numberOfParam > 1)
                        return 2;
                    else if (numberOfParam < 1)
                        return 0;
                }
                else if (functionName === "関数＠切捨て") {
                    if (numberOfParam === 1)
                        return 1;
                    else if (numberOfParam > 1)
                        return 2;
                    else if (numberOfParam < 1)
                        return 0;
                }
                else if (functionName === "関数＠切上げ") {
                    if (numberOfParam === 1)
                        return 1;
                    else if (numberOfParam > 1)
                        return 2;
                    else if (numberOfParam < 1)
                        return 0;
                }
                else if (functionName === "関数＠最大値") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 1;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠最小値") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 1;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠家族人数") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 2;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠月加算") {
                    if (numberOfParam === 2)
                        return 1;
                    else if (numberOfParam > 2)
                        return 2;
                    else if (numberOfParam < 2)
                        return 0;
                }
                else if (functionName === "関数＠年抽出") {
                    if (numberOfParam === 1)
                        return 1;
                    else if (numberOfParam > 1)
                        return 2;
                    else if (numberOfParam < 1)
                        return 0;
                }
                else if (functionName === "関数＠月抽出") {
                    if (numberOfParam === 1)
                        return 1;
                    else if (numberOfParam > 1)
                        return 2;
                    else if (numberOfParam < 1)
                        return 0;
                }
                return 1;
            };
            TextEditor.prototype.validateTypeOfParams = function (treeObject) {
                var functionName = treeObject.value.trim();
                var param = treeObject.children;
                if (functionName === "関数＠条件式" && param.length == 3) {
                }
                else if (functionName === "関数＠かつ" && param.length >= 2) {
                }
                else if (functionName === "関数＠または" && param.length >= 2) {
                }
                else if (functionName === "関数＠四捨五入" && param.length == 1) {
                }
                else if (functionName === "関数＠切捨て" && param.length == 1) {
                }
                else if (functionName === "関数＠切上げ" && param.length == 1) {
                }
                else if (functionName === "関数＠最大値" && param.length >= 2) {
                }
                else if (functionName === "関数＠最小値" && param.length >= 2) {
                }
                else if (functionName === "関数＠家族人数" && param.length == 2) {
                }
                else if (functionName === "関数＠月加算" && param.length == 2) {
                }
                else if (functionName === "関数＠年抽出" && param.length == 1) {
                }
                else if (functionName === "関数＠月抽出" && param.length == 1) {
                }
            };
            TextEditor.prototype.validateFunction = function (allElementTag) {
                var self = this;
                var inputContent = [];
                var inputTags = [];
                var splitContent = "";
                var splitTags = [];
                for (var tagOrder = 0; tagOrder < allElementTag.length; tagOrder++) {
                    if (!self.checkEqualInArray(allElementTag[tagOrder].innerText, self.listOperatorChar)) {
                        splitContent += allElementTag[tagOrder].innerText;
                        splitTags.push(allElementTag[tagOrder]);
                    }
                    else {
                        inputContent.push(splitContent);
                        inputTags.push(splitTags);
                        splitContent = "";
                        splitTags = [];
                    }
                }
                self.validateContentFunction(inputContent[0]);
            };
            TextEditor.prototype.validateContentFunction = function (contentFunction) {
                var self = this;
                var treeFunction = nts.uk.util.createTreeFromString(contentFunction, "（", "）", ",");
                self.validateTreeFunction(treeFunction[0]);
            };
            TextEditor.prototype.validateTreeFunction = function (treeObject) {
                var self = this;
                var params = treeObject.children;
                if (params.length > 0) {
                    if (self.validateNumberOfParam(treeObject) === 1) {
                        for (var i = 0; i < params.length; i++) {
                            self.validateTreeFunction(params[i]);
                        }
                    }
                    else if (self.validateNumberOfParam(treeObject) === 2) {
                        self.markErrorTreeObject(treeObject, self.ERROR_TOO_MUCH_PARAM);
                    }
                    else if (self.validateNumberOfParam(treeObject) === 0) {
                        self.markErrorTreeObject(treeObject, self.ERROR_NOT_ENOUGH_PARAM);
                    }
                }
            };
            TextEditor.prototype.markErrorTreeObject = function (treeObject, message) {
                var self = this;
                var indexTree = treeObject.index;
                var specialCharTags = $(".special-char");
                var countOpenBrackets = 0;
                for (var orderTag = 0; orderTag < specialCharTags.length; orderTag++) {
                    if (specialCharTags[orderTag].innerText === '（') {
                        countOpenBrackets += 1;
                    }
                    if (countOpenBrackets === indexTree) {
                        var functionNameTag = specialCharTags[orderTag].previousSibling;
                        self.markError($(functionNameTag), message, [functionNameTag.innerText]);
                        return true;
                    }
                }
                return true;
            };
            TextEditor.prototype.checkEqualInArray = function (target, array) {
                for (var count = 0; count < array.length; count++) {
                    if (target === array[count]) {
                        return true;
                    }
                }
                return false;
            };
            TextEditor.prototype.checkContainsInArray = function (target, array) {
                for (var count = 0; count < array.length; count++) {
                    if (target.indexOf(array[count]) !== -1) {
                        return true;
                    }
                }
                return false;
            };
            TextEditor.prototype.markError = function (tag, message, param) {
                var errorContent = message;
                if (tag) {
                    if (param && param.length > 0) {
                        for (var paramOrder = 0; paramOrder < param.length; paramOrder++) {
                            errorContent = errorContent.replace("{" + paramOrder + "}", param[paramOrder]);
                        }
                    }
                    tag.addClass("error-char").attr("message", errorContent);
                }
            };
            TextEditor.prototype.insertString = function (original, sub, position) {
                if (original.length === position) {
                    return original + sub;
                }
                return original.substr(0, position) + sub + original.substr(position);
            };
            TextEditor.prototype.testError = function () {
                var self = this;
                var value = $("#input-text").val();
                var count = 1;
                var toChar = value.split('');
                var html = "<span class='editor-line'>";
                for (var i = 0; i < toChar.length; i++) {
                    if (toChar[i] === "\n") {
                        html += "</span>";
                        html += "<span class='editor-line'>";
                    }
                    else {
                        if (toChar[i] === "@") {
                            html += "<span id='span-" + count + "' class='element-content autocomplete-char'>" + toChar[i] + "</span>";
                            count++;
                        }
                        else if (self.checkEqualInArray(toChar[i], self.listSpecialChar)) {
                            html += "<span id='span-" + count + "' class='element-content special-char'>" + toChar[i] + "</span>";
                            count++;
                        }
                        else if (self.checkJapanese(toChar[i])) {
                            if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                                html += "<span id='span-" + count + "' class='element-content japanese-character'>" + toChar[i] + "</span>";
                                count++;
                            }
                            else if (self.checkJapanese(toChar[i - 1]) && !self.checkEqualInArray(toChar[i - 1], self.listSpecialChar)) {
                                html = self.insertString(html, toChar[i], html.length - 7);
                            }
                            else {
                                html += "<span id='span-" + count + "' class='element-content japanese-character'>" + toChar[i] + "</span>";
                                count++;
                            }
                        }
                        else if (self.checkAlphaOrEmpty(toChar[i])) {
                            if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                                html += "<span id='span-" + count + "' class='element-content unknown-char'>" + toChar[i] + "</span>";
                                count++;
                            }
                            else if (self.checkAlphaOrEmpty(toChar[i - 1]) && toChar[i - 1] !== "@") {
                                html = self.insertString(html, toChar[i], html.length - 7);
                            }
                            else {
                                html += "<span id='span-" + count + "' class='element-content unknown-char'>" + toChar[i] + "</span>";
                                count++;
                            }
                        }
                        else {
                            html += "<span id='span-" + count + "' class='element-content unknown-char'>" + toChar[i] + "</span>";
                            count++;
                        }
                    }
                }
                html += "</span>";
                self.contentValue(html);
                self.validateConsecutively($(".special-char"));
                self.validateBracket($(".special-char"));
                self.validateContainAtSign($(".japanese-character"));
                self.validateBeforeAtSign($(".japanese-character"));
                self.validateDivideZero($(".special-char"));
                self.validateEmptyInput();
                self.validateNestedMoreThan10($(".special-char"));
                self.validateDigitsAfterDecimal($(".unknown-char"));
                self.validateFunction($(".element-content"));
                self.contentValue($("#input-content-area").html());
            };
            TextEditor.prototype.getCurrentPosition = function (position) {
                var uiPosition = {};
                var $lines = $("#input-content-area").find(".editor-line");
                var index = 0;
                $lines.each(function (index, line) {
                    var $line = $(line);
                    var char = _.find($line.children(), function (text) {
                        var current = index + $(text).text().length;
                        index += $(text).text().length;
                        return current === position;
                    });
                    if (char !== undefined) {
                        uiPosition = $(char).position();
                        return;
                    }
                });
                return uiPosition;
            };
            TextEditor.prototype.checkAlphaOrEmpty = function (char) {
                var speChar = new RegExp(/[~`!#$×%\（）＜＞≦≧＝≠^＾÷&*+=\-\[\]\\;\',/{}|\\\":<>\?\(\)]/g);
                return !speChar.test(char) || char === " " || char === undefined;
            };
            TextEditor.prototype.checkJapanese = function (char) {
                return !nts.uk.text.allHalf(char);
            };
            TextEditor.prototype.countNeighbor = function (index, array, countNext, countPrev) {
                var self = this;
                var current = _.find(array, function (a) { return $(a).attr("id") === "span-" + (index); });
                var previous = _.find(array, function (a) { return $(a).attr("id") === "span-" + (index - 1); });
                var next = _.find(array, function (a) { return $(a).attr("id") === "span-" + (index + 1); });
                if (previous === undefined && next === undefined) {
                    return 0;
                }
                var previousCount = 0;
                var nextCount = 0;
                if (countNext && next) {
                    if (next.innerText !== '（' && next.innerText !== '(') {
                        nextCount++;
                        nextCount += self.countNeighbor(index + 1, array, countNext, false);
                    }
                }
                if (countPrev && previous) {
                    if (previous.innerText !== '）' && previous.innerText !== ')') {
                        previousCount++;
                        previousCount += self.countNeighbor(index - 1, array, false, countPrev);
                    }
                }
                return previousCount + nextCount;
            };
            TextEditor.prototype.countPreviousElement = function (element, x, index) {
                var x2 = element.slice(0, index);
                return _.filter(x2, function (d) {
                    return d === x;
                }).length;
            };
            TextEditor.prototype.toArrayChar = function (element) {
                return _.map(element, function (data) {
                    return $(data).html();
                });
            };
            return TextEditor;
        }());
        qmm017.TextEditor = TextEditor;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
