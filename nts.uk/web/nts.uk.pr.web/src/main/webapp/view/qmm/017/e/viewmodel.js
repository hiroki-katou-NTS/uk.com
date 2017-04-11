var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var EScreen = (function () {
            function EScreen() {
                var self = this;
                self.description = ko.observableArray([]);
                var eList001 = [
                    { code: '1', name: '全て' }
                ];
                var eList002 = [
                    { code: '1', name: '条件式' },
                    { code: '2', name: 'かつ' },
                    { code: '3', name: 'または' },
                    { code: '4', name: '四捨五入' },
                    { code: '5', name: '切り捨て' },
                    { code: '6', name: '切り上げ' },
                    { code: '7', name: '最大値' },
                    { code: '8', name: '最小値' },
                    { code: '9', name: '家族人数' },
                    { code: '10', name: '年月加算' },
                    { code: '11', name: '年抽出' },
                    { code: '12', name: '月抽出' }
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(eList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        self.listBoxItems().itemList(eList002);
                    }
                });
                self.listBoxItems().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '2') {
                        self.description.push('関数＠かつ（＜式1＞,＜式2>, ・・・　）');
                        self.description.push('※＜式＞は３つ以上記載してもよい。');
                        self.description.push('複数の式をまとめて評価する場合に利用します。');
                        self.description.push('論理和（AND条件）です。');
                        self.description.push('括弧内の式が全て正しい場合、正しいものとして評価する');
                        self.description.push('括弧内の式に1つでも誤りがあれば、誤りとして評価する');
                    }
                    else if (codeChange === '3') {
                        self.description.push('関数＠または（＜式1＞,　＜式2＞ , ・・・　）');
                        self.description.push('※＜式＞は３つ以上記載してもよい。');
                        self.description.push('複数の式をまとめて評価する場合に利用します。');
                        self.description.push('論理積（OR条件）です。');
                        self.description.push('括弧内の式に1つでも正しいものがあれば、正しいものとして評価する');
                        self.description.push('カッコ内の式が全て誤っている場合、誤りとして評価する');
                    }
                    else if (codeChange === '4') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '5') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '6') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '7') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '8') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '9') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '10') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '11') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                    else if (codeChange === '12') {
                        self.description.push('関数＠条件式（＜式＞ , ＜結果①＞ , ＜結果②＞ ）');
                        self.description.push('＜式＞の内容が正しい場合、＜結果①＞とする');
                        self.description.push('＜式＞の内容が間違っている場合、＜結果②＞とする');
                        self.description.push('※＜結果①＞、＜結果②＞には値のほか、式を指定することも可能です。');
                        self.description.push('例）皆勤手当て計算式');
                        self.description.push('条件式（　勤怠＠欠勤日数　≦　0 , 5000 , 0 )');
                        self.description.push('　欠勤日数が０日以下の場合、5000円');
                        self.description.push('　欠勤日数が０日を超える場合、0円');
                    }
                });
            }
            return EScreen;
        }());
        qmm017.EScreen = EScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
