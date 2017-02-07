module nts.uk.pr.view.qmm017.a {
    export module viewmodel {
        export class Node {
            code: string;
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
        }
        export class TreeGrid {
            index: number;
            items: any;
            selectedCode: any;
            singleSelectedCode: any;
            constructor() {
                var self = this;
                self.items = ko.observableArray([new Node('0001', 'サービス部', [
                    new Node('0001-1', 'サービス部1', []),
                    new Node('0001-2', 'サービス部2', []),
                    new Node('0001-3', 'サービス部3', [])
                ]), new Node('0002', '開発部', [])]);
                self.selectedCode = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.index = 0;
            }
        }
        export class SwitchButton {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;

            constructor(data) {
                var self = this;
                self.roundingRules = ko.observableArray(data);
                self.selectedRuleCode = ko.observable(1);
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
        export class ItemModelComboBox {
            code: any;
            name: string;
            label: string;

            constructor(code: any, name: string) {
                this.code = code;
                this.name = name;
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

        function showError(event) {
            var self = this;
            var currentString = $("#input-text").val();
            var selectValue = $(self).attr("messege");
            if (selectValue !== undefined) {
                $("#error-message").text(selectValue);
                var position = $("#input-containner").position();
                $("#error-containner").css({
                    "top": (event.data.pageY - position.top + 2 - 95) + "px",
                    "left": (event.data.pageX - position.left + 2 - 400) + "px"
                });
                $("#error-containner").show();
            }
        }

        export class TextEditor {
            autoComplete: KnockoutObservableArray<any>;
            textArea: KnockoutObservable<any>;
            divValue: KnockoutObservable<any>;
            autoSelected: KnockoutObservable<any>;
            showAutoComplete: KnockoutObservable<boolean>;
            row: KnockoutObservable<number>;
            col: KnockoutObservable<number>;
            index: KnockoutObservable<number>;
            error: KnockoutObservable<string>;

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
                self.autoSelected.subscribe(function(value) {
                    if (value !== undefined) {
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
                self.textArea = ko.observable("");
                self.divValue = ko.observable("");
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
                        self.testError();
                        var currentRow = self.getCurrentPosition(end);
                        self.index(end);
                        $("#auto-complete-containner").show();
                        $("#auto-complete-containner").css({ "top": (currentRow.top + 17) + "px", "left": (currentRow.left + 15) + "px" });
                    } else {
                        $("#auto-complete-containner").hide();
                        self.testError();
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
                    if (y !== undefined) {
                        $(y).click({ pageX: event.pageX, pageY: event.pageY }, showError);
                        $(y).click();
                    }
                });

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
                self.divValue(html);
                self.testGachChan($(".special-char"));
                self.divValue($("#input-content-area").html());
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

            testGachChan(specialChar) {
                var self = this;
                var singleSpecial = {
                    "+": "+",
                    "-": "-",
                    "^": "^",
                    "*": "*",
                    "/": "/",
                    "=": "=",
                    "!": "!",
                    "#": "#",
                    "$": "$",
                    "%": "%"
                };

                var closeTriangle = _.remove(specialChar, function(n) {
                    return $(n).html() === "&gt;";
                });

                var openTriangle = _.remove(specialChar, function(n) {
                    return $(n).html() === "&lt;";
                });

                var openRound = _.remove(specialChar, function(n) {
                    return $(n).html() === "\(";
                });

                var closeRound = _.remove(specialChar, function(n) {
                    return $(n).html() === "\)";
                });

                self.checkOpenClose(openTriangle, closeTriangle);

                self.checkOpenClose(openRound, closeRound);

                var element = self.toArrayChar(specialChar);
                for (var i = 0; i < specialChar.length; i++) {
                    var $data = $(specialChar[i]);
                    var charCount = parseInt($data.attr("id").split("-")[1]);
                    var char = $data.text();
                    var single = singleSpecial[char];
                    if (single !== undefined) {
                        var neighborCount = self.countNeighbor(charCount, specialChar, true, true);
                        if (neighborCount > 0) {
                            $data.addClass("error-char").attr("messege", "test 2");
                        }
                    } else if (char !== "@") {
                        $data.addClass("error-char").attr("messege", "test 4");
                    }
                }
            }

            checkOpenClose(openTriangle, closeTriangle) {
                if (closeTriangle.length === 0) {
                    $(openTriangle).addClass("error-char").attr("messege", "test 1");
                } else if (openTriangle.length === 0) {
                    $(closeTriangle).addClass("error-char").attr("messege", "test 1");
                } else {
                    var openError = [];
                    for (var i = openTriangle.length - 1; i >= 0; i--) {
                        var currentOpen = openTriangle[i];
                        var id = parseInt($(currentOpen).attr("id").split("-")[1]);
                        var currentClose = _.find(closeTriangle, function(a) {
                            return parseInt($(a).attr("id").split("-")[1]) > id;
                        });
                        if (currentClose === undefined) {
                            openError.unshift(currentOpen);
                        } else {
                            closeTriangle.splice(closeTriangle.indexOf(currentClose), 1);
                        }
                    }
                    $(openError).addClass("error-char").attr("messege", "test 1");
                    $(closeTriangle).addClass("error-char").attr("messege", "test 1");
                }
            }

            countNeighbor(index, array, countNext, countPrev) {
                var self = this;
                var previous = _.find(array, function(a) { return $(a).attr("id") === "span-" + (index - 1); });
                var next = _.find(array, function(a) { return $(a).attr("id") === "span-" + (index + 1); });
                if (previous === undefined && next === undefined) {
                    return 0;
                }
                var previousCount = 0;
                var nextCount = 0;
                if (countNext && next !== undefined) {
                    nextCount++;
                    nextCount += self.countNeighbor(index + 1, array, countNext, false);
                }
                if (countPrev && previous !== undefined) {
                    previousCount++;
                    previousCount += self.countNeighbor(index - 1, array, false, countPrev);
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
        export class ListBox {
            itemList: KnockoutObservableArray<any>;
            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            selectedCode: KnockoutObservable<number>;
            selectedCodes: KnockoutObservableArray<number>;
            isEnable: KnockoutObservable<boolean>;

            constructor(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable(null)
                self.isEnable = ko.observable(true);
                self.selectedCodes = ko.observableArray([]);

                $('#list-box').on('selectionChanging', function(event) {
                    console.log('Selecting value:' + (<any>event.originalEvent).detail);
                })
                $('#list-box').on('selectionChanged', function(event: any) {
                    console.log('Selected value:' + (<any>event.originalEvent).detail)
                })
            }
        }
        export class ScreenModel {
            a_lst_001: KnockoutObservable<TreeGrid>;
            a_sel_001: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTabASel001: KnockoutObservable<string>;
            b_sel_001: KnockoutObservable<SwitchButton>;
            b_sel_002: KnockoutObservable<SwitchButton>;
            c_sel_001: KnockoutObservable<ComboBox>;
            c_inp_003: KnockoutObservable<TextEditor>;
            c_sel_006: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTabCSel006: KnockoutObservable<string>;
            c_sel_007: KnockoutObservable<ComboBox>;
            c_sel_008: KnockoutObservable<ComboBox>;
            d_lst_001: KnockoutObservable<ListBox>;
            d_lst_002: KnockoutObservable<ListBox>;
            constructor() {
                var self = this;
                var b_sel_001 = [
                    { code: 1, name: 'かんたん設定' },
                    { code: 2, name: '詳細設定' }
                ];
                var b_sel_002 = [
                    { code: 1, name: '利用しない' },
                    { code: 2, name: '利用する' }
                ];
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
                var d_lst_001 = [
                    { code: 1, name: '支給項目（支給＠）' },
                    { code: 2, name: '控除項目（控除＠）' },
                    { code: 3, name: '勤怠項目（勤怠＠）' },
                    { code: 4, name: '明細割増単価項目（割増し単価＠）' }
                ];
                var d_lst_002 = [
                    { code: '1', name: 'child1' },
                    { code: '2', name: 'child2' },
                    { code: '3', name: 'child3' },
                    { code: '4', name: 'child4' }
                ];
                self.a_lst_001 = ko.observable(new TreeGrid());
                self.b_sel_001 = ko.observable(new SwitchButton(b_sel_001));
                self.b_sel_002 = ko.observable(new SwitchButton(b_sel_002));
                self.c_sel_001 = ko.observable(new ComboBox(c_sel_001, true, false));
                self.a_sel_001 = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.c_sel_006 = ko.observableArray([
                    { id: 'tab-1', title: '明細・勤怠', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '関数', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: 'システム変数', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-4', title: '個人情報', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-5', title: '計算式', content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-6', title: '賃金テーブル', content: '.tab-content-6', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTabASel001 = ko.observable('tab-1');
                self.selectedTabCSel006 = ko.observable('tab-1');
                self.c_inp_003 = ko.observable(new TextEditor());
                self.c_sel_007 = ko.observable(new ComboBox(c_sel_007, true, false));
                self.c_sel_008 = ko.observable(new ComboBox(c_sel_008, true, false));
                self.d_lst_001 = ko.observable(new ListBox(d_lst_001));
                self.d_lst_002 = ko.observable(new ListBox(d_lst_002));
            }

            undo() {
                document.execCommand("undo", false, null);
            }
            
            redo() {
                document.execCommand("redo", false, null);
            }
        }
    }
}