__viewContext.ready(function() {

    class Control {
        controlName: KnockoutObservable<string>;
        properties: KnockoutObservableArray<Property>;
        constructor() {
            var self = this;
            self.controlName = ko.observable("");
            self.properties = ko.observableArray([]);
        }
    class Property {
        propertyName: KnockoutObservable<string>;
        link: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.propertyName = ko.observable("");
            self.link = ko.observable("");
        }
    }
    class ViewModel {
        controls: KnockoutObservableArray<Control>;
        constructor() {
            var self = this;
            self.controls = ko.observableArray([]);
        }
    }

    var viewModel = {
        controls: [
            {
                controlName: "テキストボックス", properties: [
                    { propertyName: "PrimitiveValue", link: "/nts.uk.com.web/view/sample/editor/text-editor.xhtml#text-1" },
                    { propertyName: "項目名", link: "link" },
                    { propertyName: "透かし文字", link: "/nts.uk.com.web/view/sample/editor/text-editor.xhtml#text-1" },
                    { propertyName: "カンマ編集", link: "/nts.uk.com.web/view/sample/editor/number-editor.xhtml#currency-1" },
                    { propertyName: "単位", link: "/nts.uk.com.web/view/sample/editor/number-editor.xhtml#currency-1" },
                    { propertyName: "Enterマーク", link: "link" },
                    { propertyName: "精度", link: "/nts.uk.com.web/view/sample/editor/time-editor.xhtml#yyyymm" },
                    { propertyName: "曜日表示", link: "/nts.uk.com.web/view/sample/datepicker/datepicker.xhtml#dayofweek" },
                    { propertyName: "日付送り", link: "link" }
                ]
            },
            {
                controlName: "期間入力フォーム", properties: [
                    { propertyName: "精度", link: "link" },
                    { propertyName: "項目名", link: "link" },
                    { propertyName: "最長期間", link: "link" },
                    { propertyName: "期間送り", link: "link" }
                ]
            },
            {
                controlName: "期間入力フォーム開始", properties: [
                    { propertyName: "項目名", link: "link" }
                ]
            },
            {
                controlName: "期間入力フォーム終了", properties: [
                    { propertyName: "項目名", link: "link" }
                ]
            },
            {
                controlName: "ファイル参照フォーム", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/fileupload/index.xhtml" }
                ]
            },
            {
                controlName: "月日入力フォーム", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/datepicker/datepicker.xhtml" }
                ]
            },
            {
                controlName: "ドロップダウンリスト", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/combobox/combobox.xhtml" },
                    { propertyName: "ソート", link: "link" }
                ]
            },
            {
                controlName: "グリッドリスト", properties: [
                    { propertyName: "選択モード", link: "http://localhost:8080/nts.uk.com.web/view/sample/gridlist/gridlist.xhtml" },
                    { propertyName: "項目名", link: "http://localhost:8080/nts.uk.com.web/view/sample/gridlist/gridlist.xhtml" },
                    { propertyName: "ソート", link: "link" },
                    { propertyName: "任意列ソート", link: "link" },
                    { propertyName: "列幅可変", link: "http://localhost:8080/nts.uk.com.web/view/sample/gridlist/gridlist.xhtml" },
                    { propertyName: "ページング", link: "http://localhost:8080/nts.uk.com.web/view/sample/gridlist/gridlist.xhtml" },
                ]
            },
            {
                controlName: "シンプルリスト", properties: [
                    { propertyName: "選択モード", link: "/nts.uk.com.web/view/sample/listbox/listbox.xhtml" },
                    { propertyName: "項目名", link: "link" },
                    { propertyName: "ソート", link: "link" }
                ]
            },
            {
                controlName: "ツリーリスト", properties: [
                    { propertyName: "選択モード", link: "/nts.uk.com.web/view/sample/treegrid/treegrid.xhtml" },
                    { propertyName: "項目名", link: "link" },
                    { propertyName: "ソート", link: "link" }
                ]
            },
            {
                controlName: "親子リスト", properties: [
                    { propertyName: "選択モード", link: "link" },
                    { propertyName: "項目名", link: "link" }
                ]
            },
            {
                controlName: "親子リスト親ノード", properties: [
                    { propertyName: "ソート", link: "link" }
                ]
            },
            {
                controlName: "親子リスト子ノード", properties: [
                    { propertyName: "ソート", link: "link" },
                    { propertyName: "選択可能", link: "link" }
                ]
            },
            {
                controlName: "リスト列項目", properties: [
                    { propertyName: "Enum", link: "link" }
                ]
            },
            {
                controlName: "カラーピッカー", properties: [
                    { propertyName: "項目名", link: "link" }
                ]
            },
            {
                controlName: "ラジオボタングループ", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/radioboxgroup/radioboxgroup.xhtml" },
                    { propertyName: "Enum", link: "link" }
                ]
            },
            {
                controlName: "ラジオボタン", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/radiobox/radiobox.xhtml" }
                ]
            },
            {
                controlName: "チェックボックス", properties: [
                    { propertyName: "テキスト", link: "http://localhost:8080/nts.uk.com.web/view/sample/checkbox/checkbox.xhtml" },
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "スイッチボタングループ", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/switch/switch.xhtml" },
                    { propertyName: "Enum", link: "link" }
                ]
            },
            {
                controlName: "スイッチボタン", properties: [
                    { propertyName: "テキスト", link: "link" }
                ]
            },
            {
                controlName: "スワップリスト", properties: [
                    { propertyName: "項目名", link: "/nts.uk.com.web/view/sample/swaplist/swaplist.xhtml" }
                ]
            },
            {
                controlName: "ボタン", properties: [
                    { propertyName: "テキスト", link: "link" },
                    { propertyName: "色", link: "link" },
                    { propertyName: "サイズ", link: "/nts.uk.com.web/view/sample/commoncss/button.xhtml" },
                    { propertyName: "アイコン", link: "link" },
                    { propertyName: "キャレット", link: "link" }
                ]
            },
            {
                controlName: "ファンクションボタン", properties: [
                    { propertyName: "テキスト", link: "link" },
                    { propertyName: "色", link: "link" },
                    { propertyName: "アイコン", link: "link" }
                ]
            },
            {
                controlName: "画像ボタン", properties: [
                    { propertyName: "画像ファイル", link: "link" },
                    { propertyName: "影", link: "link" }
                ]
            },
            {
                controlName: "タブグループ", properties: [
                    { propertyName: "配置方向", link: "link" }
                ]
            },
            {
                controlName: "タブ", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/tabpanel/tabpanel.xhtml" }
                ]
            },
            {
                controlName: "サイドバー", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/sidebar/sidebar.xhtml" }
                ]
            },
            {
                controlName: "サイドバーリンクラベル", properties: [
                    { propertyName: "テキスト", link: "link" }
                ]
            },
            {
                controlName: "リンクラベル", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/linkbutton/linkbutton.xhtml" },
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "アコーディオン", properties: [
                    { propertyName: "ヘッダテキスト", link: "/nts.uk.com.web/view/sample/commoncss/accordion.xhtml" },
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "はてなアイコン", properties: [
                    { propertyName: "画像ファイル", link: "link" },
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "凡例ボタン", properties: [
                    { propertyName: "コンテンツパネル", link: "link" },
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "ウィザード", properties: [
                    { propertyName: "-", link: "/nts.uk.com.web/view/sample/wizard/wizard.xhtml" }
                ]
            },
            {
                controlName: "ウィザードタイトル", properties: [
                    { propertyName: "アイコン", link: "link" },
                    { propertyName: "テキスト", link: "link" }
                ]
            },
            {
                controlName: "ウィザードステップ", properties: [
                    { propertyName: "テキスト", link: "link" }
                ]
            },
            {
                controlName: "ウィザード終端", properties: [
                    { propertyName: "テキスト", link: "link" }
                ]
            },
            {
                controlName: "ラベル", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/commoncss/label.xhtml" }
                ]
            },
            {
                controlName: "警告ラベル", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/label/form-label.xhtml" }
                ]
            },
            {
                controlName: "フォームラベル", properties: [
                    { propertyName: "テキスト", link: "/nts.uk.com.web/view/sample/label/form-label.xhtml" },
                    { propertyName: "ラインの色", link: "link" },
                    { propertyName: "スタイル", link: "link" },
                    { propertyName: "制約表示", link: "link" }
                ]
            },
            {
                controlName: "グリッドヘッダラベル", properties: [
                    { propertyName: "テキスト", link: "link" },
                    { propertyName: "制約表示", link: "link" }
                ]
            },
            {
                controlName: "表示専用テキストボックス", properties: [
                    { propertyName: "-", link: "link" }
                ]
            },
            {
                controlName: "ガイドメッセージ", properties: [
                    { propertyName: "テキスト", link: "link" },
                    { propertyName: "対象項目", link: "link" }
                ]
            },
            {
                controlName: "キャレット", properties: [
                    { propertyName: "スタイル", link: "/nts.uk.com.web/view/sample/commoncss/caret.xhtml" }
                ]
            },
            {
                controlName: "画像", properties: [
                    { propertyName: "画像ファイル", link: "link" }
                ]
            },
            {
                controlName: "罫線", properties: [
                    { propertyName: "-", link: "link" }
                ]
            },
            {
                controlName: "パネル", properties: [
                    { propertyName: "スタイル", link: "link" },
                    { propertyName: "スクロールバー", link: "link" }
                ]
            },
            {
                controlName: "ポップアップパネル", properties: [
                    { propertyName: "-", link: "link" }
                ]
            },
            {
                controlName: "マスタリスト選択パネル", properties: [
                    { propertyName: "スタイル", link: "link" }
                ]
            },
            {
                controlName: "ファンクションパネル", properties: [
                    { propertyName: "ボタンテキスト", link: "link" }
                ]
            },
            {
                controlName: "リスト検索フォーム", properties: [
                    { propertyName: "対象リスト", link: "link" },
                    { propertyName: "対象列", link: "link" },
                    { propertyName: "検索モード", link: "link" }
                ]
            },
        ]
    };
    this.bind(ko.mapping.fromJS(viewModel));
    console.log();

});

