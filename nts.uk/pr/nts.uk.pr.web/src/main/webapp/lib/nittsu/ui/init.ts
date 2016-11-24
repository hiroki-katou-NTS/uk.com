module nts.uk.ui {
     
    import option = nts.uk.ui.option;
    export var _viewModel: any;

    module init {
        
        var _start: any;
        
        __viewContext.ready = function (callback: () => void) {
            _start = callback;
        };
        
        __viewContext.bind = function (contentViewModel: any) {
            _viewModel = {
                content: contentViewModel, // developer's view model
                kiban: new KibanViewModel() // Kiban's view model
            };
            ko.applyBindings(_viewModel);
        }
        
        $(function () {
            _start.call(__viewContext);
        });
        
        // Kiban ViewModel
        class KibanViewModel {
            errorDialogViewModel: any;
            
            constructor(){
                var self = this;
                self.errorDialogViewModel = {
                    title: ko.observable("Error Dialog title"),
                    headers: ko.observableArray([
                        new ErrorHeader("tab", "タブ", 90, true),
                        new ErrorHeader("location", "エラー箇所", 115, true),
                        new ErrorHeader("message", "エラー詳細", 250, true)
                    ]),
                    errors: ko.observableArray([
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},
                        {tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"},{tab: "基本情報",　location: "メールアドレス", message: "メールアドレスは必須項目です"}
                    ]),
                    option: ko.mapping.fromJS(new option.ErrorDialogOption()),
                    closeButtonClicked: function(){},
                    open: function(){
                        var self = this;
                        self.option.show(true);
                    },
                    hide: function(){
                        var self = this;
                        self.option.show(false);
                    }
                }
            }
        }
        
        class ErrorHeader{
            name: string;
            text: string;
            width: number;
            visible: boolean;
            
            constructor(name:string, text: string, width: number, visible: boolean) {
                this.name = name;
                this.text = text;
                this.width = width;
                this.visible = visible;
            }
        }
    }
}