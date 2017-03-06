module nts.qmm017 {

    export class CScreen {
        c_sel_001: KnockoutObservable<ComboBox>;
        roundingRulesEasySettings: KnockoutObservableArray<any>;
        selectedRuleCodeEasySettings: KnockoutObservable<any>;
        selectedRuleCode: KnockoutObservable<any>;
        selectedRuleCode2: KnockoutObservable<any>;

        formulaManualContent: KnockoutObservable<TextEditor>;
        c_sel_006: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        c_sel_007: KnockoutObservable<ComboBox>;
        c_sel_008: KnockoutObservable<ComboBox>;
        selectedTabCSel006: KnockoutObservable<string>;
        easyFormulaName: KnockoutObservable<string>;

        constructor(data) {
            var self = this;
            self.roundingRulesEasySettings = ko.observableArray([
                { code: '0', name: '固定値' },
                { code: '1', name: '計算式' }
            ]);
            self.selectedRuleCodeEasySettings = ko.observable(0);
            self.selectedRuleCode = ko.observable(data().selectedRuleCode());
            self.selectedRuleCode2 = ko.observable(data().selectedRuleCode2());
            data().selectedRuleCode.subscribe(function(val) {
                self.selectedRuleCode(val);
            });
            data().selectedRuleCode2.subscribe(function(val) {
                self.selectedRuleCode2(val);
            });
            var c_sel_001 = [
                { code: 1, name: '雇用マスタ' },
                { code: 2, name: '部門マスタ' },
                { code: 3, name: '分類マスタ' },
                { code: 4, name: '給与分類マスタ' },
                { code: 5, name: '職位マスタ' },
                { code: 6, name: '給与形態' }
            ];
            var c_sel_007 = [
                { code: 1, name: '当月' },
                { code: 2, name: '1ヶ月前' },
                { code: 3, name: '2ヶ月前' },
                { code: 4, name: '3ヶ月前' },
                { code: 5, name: '4ヶ月前' },
                { code: 6, name: '5ヶ月前' },
                { code: 7, name: '6ヶ月前' },
                { code: 8, name: '7ヶ月前' },
                { code: 9, name: '8ヶ月前' },
                { code: 10, name: '9ヶ月前' },
                { code: 11, name: '10ヶ月前' },
                { code: 12, name: '11ヶ月前' },
                { code: 13, name: '12ヶ月前' },
            ];
            var c_sel_008 = [
                { code: 1, name: '切上げ' },
                { code: 2, name: '切捨て' },
                { code: 3, name: '一捨二入' },
                { code: 4, name: '二捨三入' },
                { code: 5, name: '三捨四入' },
                { code: 6, name: '四捨五入' },
                { code: 7, name: '五捨六入' },
                { code: 8, name: '六捨七入' },
                { code: 9, name: '七捨八入' },
                { code: 10, name: '八捨九入' },
            ];

            self.formulaManualContent = ko.observable(new TextEditor());
            self.c_sel_001 = ko.observable(new ComboBox(c_sel_001, true, false));
            self.c_sel_006 = ko.observableArray([
                { id: 'tab-1', title: '明細・勤怠', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '関数', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'システム変数', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: '個人情報', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: '計算式', content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-6', title: '賃金テーブル', content: '.tab-content-6', style: 'margin-left: 50px;', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.c_sel_007 = ko.observable(new ComboBox(c_sel_007, true, false));
            self.c_sel_008 = ko.observable(new ComboBox(c_sel_008, true, false));
            self.selectedTabCSel006 = ko.observable('tab-1');
            self.easyFormulaName = ko.observable('');
        }

        undo() {
            document.execCommand("undo", false, null);
        }

        redo() {
            document.execCommand("redo", false, null);
        }

        openDialogQ() {
            let param = {}
            nts.uk.ui.windows.setShared('paramFromScreenC', param);
            nts.uk.ui.windows.sub.modal('/view/qmm/017/l/index.xhtml', { title: 'かんたん計算式の登録', width: 650, height: 750 }).onClosed(() => {

            });
        }

        validateTextArea() {
            var self = this;
            self.formulaManualContent().testError();
        }
    }

    export class ComboBox {
        itemList: KnockoutObservableArray<any>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<any>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        constructor(data, isEnable, isEditable) {
            var self = this;
            self.itemList = ko.observableArray(data);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(0);
            self.isEnable = ko.observable(isEnable);
            self.isEditable = ko.observable(isEditable);
        }
    }

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

    export class ItemModelTextEditor {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        description: KnockoutObservable<string>;
        text: KnockoutObservable<string>;
        constructor(code: string, name: string, description: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.description = ko.observable(description);
            this.text = ko.computed(function() {
                return this.code() + "  " + this.name();
            }, this).extend({ deferred: true });
        }
    }

    export class TextEditor {

        const ERROR_BRACKET = "カッコ()の数に誤りがあります。";
        const ERROR_CONSECUTIVELY = "構文に誤りがあります。＋と＋が連続して入力されています。";
        const ERROR_MUSTCONTAINATSIGN = "「基本給」は利用できない文字列です。";

        autoComplete: KnockoutObservableArray<any>;
        textArea: KnockoutObservable<string>;
        contentValue: KnockoutObservable<any>;
        autoSelected: KnockoutObservable<any>;
        showAutoComplete: KnockoutObservable<boolean>;
        row: KnockoutObservable<number>;
        col: KnockoutObservable<number>;
        index: KnockoutObservable<number>;
        error: KnockoutObservable<string>;
        lstError: KnockoutObservableArray<string>;

        constructor() {
            var self = this;
            //----------------------------------------------------------------------------
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
            self.autoSelected.subscribe(function(value) {
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
            $(document).on("keyup", "#input-text", (event) => {
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
                } else {
                    $("#auto-complete-containner").hide();
                }
            });
            $(document).on("mouseleave", "#error-containner", (event) => {
                $("#error-containner").hide();
            });

            $(document).on("click", "#input-area", (event) => {
                $("#error-containner").hide();
                var y = _.findLast($(".special-char"), function(d) {
                    var x = $(d).offset();
                    return x.top <= event.pageY && x.left <= event.pageX
                        && (x.left + $(d).outerWidth()) >= event.pageX
                        && (x.top + $(d).outerHeight()) >= event.pageY;
                });
                if (y) {
                    $(y).click({ pageX: event.pageX, pageY: event.pageY }, showError);
                    $(y).click();
                }
            });

        }

        markError(tag, message: string) {
            tag.addClass("error-char").attr("message", message);
        }

        insertString(original, sub, position) {
            if (original.length === position) {
                return original + sub;
            }
            return original.substr(0, position) + sub + original.substr(position);
        }

        testError() {
            var self = this;
            var value = $("#input-text").val();
            var count = 1;
            var toChar = value.split('');
            var html = "<span class='editor-line'>";
            for (var i = 0; i < toChar.length; i++) {
                if (toChar[i] === "\n") {
                    html += "</span>";
                    html += "<span class='editor-line'>";
                } else {
                    if (toChar[i] === "@") {
                        html += "<span id='span-" + count + "' class='autocomplete-char'>" + toChar[i] + "</span>";
                        count++;
                    } else if (self.checkJapanese(toChar[i])) {
                        if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                            html += "<span id='span-" + count + "' class='japanese-character'>" + toChar[i] + "</span>";
                            count++;
                        } else if (self.checkJapanese(toChar[i - 1])) {
                            html = self.insertString(html, toChar[i], html.length - 7);
                        } else {
                            html += "<span id='span-" + count + "' class='japanese-character'>" + toChar[i] + "</span>";
                            count++;
                        }
                    } else if (self.checkAlphaOrEmpty(toChar[i])) {
                        if (toChar[i - 1] === undefined || toChar[i - 1] === "\n") {
                            html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                            count++;
                        } else if (self.checkAlphaOrEmpty(toChar[i - 1]) && toChar[i - 1] !== "@") {
                            html = self.insertString(html, toChar[i], html.length - 7);
                        } else {
                            html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                            count++;
                        }
                    } else {
                        html += "<span id='span-" + count + "' class='special-char'>" + toChar[i] + "</span>";
                        count++;
                    }
                }
            }
            html += "</span>";
            self.contentValue(html);

            var specialChar = $(".special-char");
            self.validateConsecutively(specialChar);
            self.validateBracket(specialChar);

            self.contentValue($("#input-content-area").html());
        }

        getCurrentPosition(position) {
            var uiPosition = {};
            var $lines = $("#input-content-area").find(".editor-line");
            var index = 0;
            $lines.each(function(index, line) {
                var $line = $(line);
                var char = _.find($line.children(), function(text) {
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
        }

        checkAlphaOrEmpty(char) {
            var speChar = new RegExp(/[~`!#$%\^&*+=\-\[\]\\;\',/{}|\\\":<>\?\(\)]/g);
            return !speChar.test(char) || char === " " || char === undefined;
        }

        checkJapanese(char) {
            return !nts.uk.text.allHalf(char);
        }

        validateBracket(specialChar) {
            var self = this;

            let openBracket = _.remove(specialChar, function(n) {
                return $(n).html() === "\(";
            });

            let closeBracket = _.remove(specialChar, function(n) {
                return $(n).html() === "\)";
            });

            if (closeBracket.length === 0) {
                self.markError($(openBracket), self.ERROR_BRACKET);
            } else if (openBracket.length === 0) {
                self.markError($(closeBracket), self.ERROR_BRACKET);
            } else {
                var openError = [];
                for (var i = openBracket.length - 1; i >= 0; i--) {
                    var currentOpen = openBracket[i];
                    var id = parseInt($(currentOpen).attr("id").split("-")[1]);
                    var currentClose = _.find(closeBracket, function(a) {
                        return parseInt($(a).attr("id").split("-")[1]) > id;
                    });
                    if (currentClose === undefined) {
                        openError.unshift(currentOpen);
                    } else {
                        closeBracket.splice(closeBracket.indexOf(currentClose), 1);
                    }
                }
                self.markError($(openError), self.ERROR_BRACKET);
                self.markError($(closeBracket), self.ERROR_BRACKET);
            }

        }

        validateConsecutively(specialChar) {
            var self = this;

            var singleSpecial = {
                "+": "+",
                "-": "-",
                "^": "^",
                "*": "*",
                "=": "=",
                "#": "#",
                "%": "%",
                "<": "<",
                ">": ">",
                "≦": "≦",
                "≧": "≧"
            };

            for (var i = 0; i < specialChar.length; i++) {
                var self = this;
                var $data = $(specialChar[i]);
                var charCount = parseInt($data.attr("id").split("-")[1]);
                var char = $data.text();
                var single = singleSpecial[char];
                if (single !== undefined) {
                    var neighborCount = self.countNeighbor(charCount, specialChar, true, true);
                    if (neighborCount > 0) {
                        self.markError($data, self.ERROR_CONSECUTIVELY);
                    }
                }
            }
        }

        countNeighbor(index, array, countNext, countPrev) {
            var self = this;
            var current = _.find(array, function(a) { return $(a).attr("id") === "span-" + (index); });
            var previous = _.find(array, function(a) { return $(a).attr("id") === "span-" + (index - 1); });
            var next = _.find(array, function(a) { return $(a).attr("id") === "span-" + (index + 1); });
            if (previous === undefined && next === undefined) {
                return 0;
            }
            var previousCount = 0;
            var nextCount = 0;
            if (countNext && next) {
                if (next.innerText !== '(') {
                    nextCount++;
                    nextCount += self.countNeighbor(index + 1, array, countNext, false);
                }
            }
            if (countPrev && previous) {
                if (previous.innerText !== ')') {
                    previousCount++;
                    previousCount += self.countNeighbor(index - 1, array, false, countPrev);
                }
            }
            return previousCount + nextCount;

        }

        countPreviousElement(element: Array<string>, x, index) {
            var x2 = element.slice(0, index);
            return _.filter(x2, function(d) {
                return d === x;
            }).length;
        }

        toArrayChar(element) {
            return _.map(element, function(data) {
                return $(data).html();
            });
        }
    }

}