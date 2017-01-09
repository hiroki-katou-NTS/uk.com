var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var a;
                    (function (a_1) {
                        var viewmodel;
                        (function (viewmodel) {
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.childs = childs;
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                            var TreeGrid = (function () {
                                function TreeGrid() {
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
                                return TreeGrid;
                            }());
                            viewmodel.TreeGrid = TreeGrid;
                            var SwitchButton = (function () {
                                function SwitchButton(data) {
                                    var self = this;
                                    self.roundingRules = ko.observableArray(data);
                                    self.selectedRuleCode = ko.observable(1);
                                }
                                return SwitchButton;
                            }());
                            viewmodel.SwitchButton = SwitchButton;
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
                            viewmodel.ComboBox = ComboBox;
                            var ItemModelComboBox = (function () {
                                function ItemModelComboBox(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModelComboBox;
                            }());
                            viewmodel.ItemModelComboBox = ItemModelComboBox;
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
                            viewmodel.ItemModelTextEditor = ItemModelTextEditor;
                            function showError(event) {
                                var currentString = $("#input-text").val();
                                var selectValue = $(this).attr("message");
                                $(this).tooltip({ content: selectValue });
                                $("#error-message").text(selectValue);
                                var position = $("#input-containner").position();
                                $("#error-containner").css({
                                    "top": (event.data.pageY - position.top + 2) + "px",
                                    "left": (event.data.pageX - position.left + 2) + "px"
                                });
                                $("#error-containner").show();
                            }
                            var TextEditor = (function () {
                                function TextEditor() {
                                    //----------------------------------------------------------------------------
                                    var self = this;
                                    this.autoComplete = ko.observableArray([
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
                                    this.showAutoComplete = ko.observable(false);
                                    this.autoSelected = ko.observable("");
                                    this.row = ko.observable(1);
                                    this.col = ko.observable(1);
                                    this.error = ko.observable("");
                                    this.autoSelected.subscribe(function (value) {
                                        //                $("#auto-complete-containner").show()
                                        if (value !== undefined) {
                                            var currentString = $("#input-text").val();
                                            var index = this.getIndex(currentString, this.row(), this.col()) + 1;
                                            var selectValue = value.name();
                                            var inserted = this.insertString(currentString, selectValue, index);
                                            this.textArea(inserted);
                                            $("#input-text").focus();
                                            $("#input-text")[0].selectionStart = index + selectValue.length;
                                            $("#input-text")[0].selectionEnd = index + selectValue.length;
                                            this.testError();
                                            $("#auto-complete-containner").hide();
                                            this.autoSelected(undefined);
                                        }
                                    }, this);
                                    this.textArea = ko.observable("");
                                    this.divValue = ko.observable("");
                                    $("#error-containner").hide();
                                    //            $("#error-content").mouseout(function(event){
                                    //                $("#error-containner").hide();
                                    //            }
                                    $(document).on("keyup", "#input-text", function (event) {
                                        if (!event.shiftKey && event.keyCode === 16) {
                                            return;
                                        }
                                        $("#error-containner").hide();
                                        var start = $("#input-text")[0].selectionStart;
                                        var end = $("#input-text")[0].selectionEnd;
                                        var maxWidthCharacter = 15;
                                        //                if(event.keyCode === 8){
                                        //                      
                                        //                }else 
                                        if ((event.shiftKey && event.keyCode === 50) || event.keyCode === 192) {
                                            //                    $("#auto-complete-containner").css("top": start);
                                            var currentRow = self.getCurrentRows(end);
                                            var currentCol = self.getCurrentColumn(currentRow, end);
                                            self.row(currentRow);
                                            self.col(currentCol);
                                            $("#auto-complete-containner").show();
                                            $("#auto-complete-containner").css({ "top": (currentRow * 17) + "px", "left": (currentCol * maxWidthCharacter) + "px" });
                                        }
                                        else {
                                            $("#auto-complete-containner").hide();
                                            self.testError();
                                        }
                                    });
                                    $(document).on("mouseleave", "#error-containner", function (event) {
                                        $("#error-containner").hide();
                                    });
                                    $(document).on("click", "#input-area", function (event) {
                                        $("#error-containner").hide();
                                        var y = _.findLast($(".special-char"), function (d) {
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
                                TextEditor.prototype.insertString = function (original, sub, position) {
                                    if (original.length === position) {
                                        return original + sub;
                                    }
                                    return original.substr(0, position) + sub + original.substr(position);
                                };
                                TextEditor.prototype.testError = function () {
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
                                            if (this.checkAlphaOrEmpty(toChar[i])) {
                                                if (toChar[i - 1] === undefined) {
                                                    html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                                                    count++;
                                                }
                                                else if (this.checkAlphaOrEmpty(toChar[i - 1])) {
                                                    html = this.insertString(html, toChar[i], html.length - 7);
                                                }
                                                else {
                                                    html += "<span id='span-" + count + "'>" + toChar[i] + "</span>";
                                                    count++;
                                                }
                                            }
                                            else {
                                                html += "<span id='span-" + count + "' class='special-char'>" + toChar[i] + "</span>";
                                                count++;
                                            }
                                        }
                                    }
                                    html += "</span>";
                                    this.divValue(html);
                                    this.testGachChan($(".special-char"));
                                    this.divValue($("#input-content-area").html());
                                };
                                TextEditor.prototype.getIndex = function (str, row, col) {
                                    if (row === 1) {
                                        return col - 1;
                                    }
                                    var rowValues = str.split("\n");
                                    var index = 0;
                                    for (var i = 0; i < row - 1; i++) {
                                        index = rowValues[i].length + 1;
                                    }
                                    return index + col - 1;
                                };
                                TextEditor.prototype.getCurrentColumn = function (currentRow, position) {
                                    if (currentRow === 1) {
                                        return position;
                                    }
                                    var rowValues = $("#input-text").val().split("\n");
                                    var count = 1;
                                    var x = position;
                                    for (var i = 0; i < currentRow - 1; i++) {
                                        count = x - rowValues[i].length - 1;
                                        x = count;
                                    }
                                    return count + 1;
                                };
                                TextEditor.prototype.getCurrentRows = function (position) {
                                    var currentText = $("#input-text").val();
                                    var count = 1;
                                    var start = currentText.indexOf("\n", 0);
                                    while (start !== -1 && start < position) {
                                        count++;
                                        start = currentText.indexOf("\n", start + 1);
                                    }
                                    ;
                                    return count;
                                };
                                TextEditor.prototype.checkAlphaOrEmpty = function (char) {
                                    var speChar = new RegExp(/[~`!#$%\^&*+=\-\[\]\\;\',/{}|\\\":<>\?\(\)]/g);
                                    return !speChar.test(char) || char === " " || char === undefined;
                                };
                                TextEditor.prototype.testGachChan = function (specialChar) {
                                    //            var openSpecial = {
                                    //                "&gt;": "&lt;",
                                    //                "&lt;": "&gt;"
                                    //            };
                                    //            
                                    //            var o= {
                                    //                "\)": "\(",
                                    //                "\(": "\)"
                                    //            };
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
                                    var closeTriangle = _.remove(specialChar, function (n) {
                                        return $(n).html() === "&gt;";
                                    });
                                    var openTriangle = _.remove(specialChar, function (n) {
                                        return $(n).html() === "&lt;";
                                    });
                                    var openRound = _.remove(specialChar, function (n) {
                                        return $(n).html() === "\(";
                                    });
                                    var closeRound = _.remove(specialChar, function (n) {
                                        return $(n).html() === "\)";
                                    });
                                    if (closeTriangle.length === 0) {
                                        $(openTriangle).addClass("error-char").attr("message", "test 1");
                                    }
                                    else if (openTriangle.length === 0) {
                                        $(closeTriangle).addClass("error-char").attr("message", "test 1");
                                    }
                                    else {
                                        var openError = [];
                                        for (var i = openTriangle.length - 1; i >= 0; i--) {
                                            var currentOpen = openTriangle[i];
                                            var id = parseInt($(currentOpen).attr("id").split("-")[1]);
                                            var currentClose = _.find(closeTriangle, function (a) {
                                                return parseInt($(a).attr("id").split("-")[1]) > id;
                                            });
                                            if (currentClose === undefined) {
                                                openError.unshift(currentOpen);
                                            }
                                            else {
                                                closeTriangle.splice(closeTriangle.indexOf(currentClose), 1);
                                            }
                                        }
                                        $(openError).addClass("error-char").attr("message", "test 1");
                                        $(closeTriangle).addClass("error-char").attr("message", "test 1");
                                    }
                                    if (closeRound.length === 0) {
                                        $(openRound).addClass("error-char").attr("message", "test 1");
                                    }
                                    else if (openRound.length === 0) {
                                        $(closeRound).addClass("error-char").attr("message", "test 1");
                                    }
                                    else {
                                        var openError = [];
                                        for (var i = openRound.length - 1; i >= 0; i--) {
                                            var currentOpen = openRound[i];
                                            var id = parseInt($(currentOpen).attr("id").split("-")[1]);
                                            var currentClose = _.find(closeRound, function (a) {
                                                return parseInt($(a).attr("id").split("-")[1]) > id;
                                            });
                                            if (currentClose === undefined) {
                                                openError.unshift(currentOpen);
                                            }
                                            else {
                                                closeRound.splice(closeRound.indexOf(currentClose), 1);
                                            }
                                        }
                                        $(openError).addClass("error-char").attr("message", "test 3");
                                        $(closeRound).addClass("error-char").attr("message", "test 3");
                                    }
                                    var element = this.toArrayChar(specialChar);
                                    for (var i = 0; i < specialChar.length; i++) {
                                        var $data = $(specialChar[i]);
                                        var charCount = parseInt($data.attr("id").split("-")[1]);
                                        var char = $data.text();
                                        //                var openComa = openSpecial[nts.uk.text.htmlEncode(char)];
                                        var single = singleSpecial[char];
                                        //                if (openComa !== undefined) {
                                        //                    var x2 = this.countPreviousElement(element, nts.uk.text.htmlEncode(char), i) + 1;
                                        //                    var x = this.countPreviousElement(element, openComa, i);
                                        //                    if (x2 > x) {
                                        //                        $data.addClass("error-char").attr("message", "test 1");
                                        //                    }
                                        //                } else 
                                        if (single !== undefined) {
                                            var neighborCount = this.countNeighbor(charCount, specialChar, true, true);
                                            if (neighborCount > 0) {
                                                $data.addClass("error-char").attr("message", "test 2");
                                            }
                                        }
                                        else if (char !== "@") {
                                            $data.addClass("error-char").attr("message", "test 4");
                                        }
                                    }
                                };
                                TextEditor.prototype.countNeighbor = function (index, array, countNext, countPrev) {
                                    var previous = _.find(array, function (a) { return $(a).attr("id") === "span-" + (index - 1); });
                                    var next = _.find(array, function (a) { return $(a).attr("id") === "span-" + (index + 1); });
                                    if (previous === undefined && next === undefined) {
                                        return 0;
                                    }
                                    var previousCount = 0;
                                    var nextCount = 0;
                                    if (countNext && next !== undefined) {
                                        nextCount++;
                                        nextCount += this.countNeighbor(index + 1, array, countNext, false);
                                    }
                                    if (countPrev && previous !== undefined) {
                                        previousCount++;
                                        previousCount += this.countNeighbor(index - 1, array, false, countPrev);
                                    }
                                    return previousCount + nextCount;
                                };
                                TextEditor.prototype.countPreviousElement = function (element, x, index) {
                                    //        var count = 0;
                                    var x2 = element.slice(0, index);
                                    return _.filter(x2, function (d) {
                                        return d === x;
                                    }).length;
                                    //        for(var i = index; i >= 0; i--){
                                    //            if(element[i] === x){
                                    //                count++;             
                                    //            }                       
                                    //        }     
                                };
                                TextEditor.prototype.toArrayChar = function (element) {
                                    return _.map(element, function (data) {
                                        return $(data).html();
                                    });
                                };
                                return TextEditor;
                            }());
                            viewmodel.TextEditor = TextEditor;
                            var ListBox = (function () {
                                function ListBox(data) {
                                    var self = this;
                                    self.itemList = ko.observableArray(data);
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(3);
                                    self.selectedCode = ko.observable(null);
                                    self.isEnable = ko.observable(true);
                                    self.selectedCodes = ko.observableArray([]);
                                    $('#list-box').on('selectionChanging', function (event) {
                                        console.log('Selecting value:' + event.originalEvent.detail);
                                    });
                                    $('#list-box').on('selectionChanged', function (event) {
                                        console.log('Selected value:' + event.originalEvent.detail);
                                    });
                                }
                                ListBox.prototype.deselectAll = function () {
                                    $('#list-box').ntsListBox('deselectAll');
                                };
                                ListBox.prototype.selectAll = function () {
                                    $('#list-box').ntsListBox('selectAll');
                                };
                                return ListBox;
                            }());
                            viewmodel.ListBox = ListBox;
                            var ScreenModel = (function () {
                                function ScreenModel() {
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
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = a_1.viewmodel || (a_1.viewmodel = {}));
                    })(a = qmm017.a || (qmm017.a = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
