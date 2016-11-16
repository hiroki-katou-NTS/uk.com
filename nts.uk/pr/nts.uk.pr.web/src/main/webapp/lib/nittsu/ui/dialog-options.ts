    interface IDialogOption {
        title?: string, 
        message?: string,
    }
    
    abstract class DialogOption {
        title: string;
        message: string;
        protected modal: boolean = true;
        protected show: boolean = false;
        protected buttons: DialogButton[];
    }
    
    class ConfirmDialogOption extends DialogOption {
        constructor(option?: IDialogOption)
        {
            super();
            // Default value
            this.title = (option && option.title) ? option.title : "";
            this.message = (option && option.message) ? option.message : "";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: "OK",
                               "class": "yes",
                               size: "large",
                               color: "proceed",
                               click: function(viewmodel, ui): void {
                                   viewmodel.okButtonClicked();
                                   $(ui).dialog("close");
                               }
                             });
        }
    }
    
    class DelDialogOption extends DialogOption {
        constructor(option?: IDialogOption)
        {
            super();
            // Default value
            this.title = (option && option.title) ? option.title : "";
            this.message = (option && option.message) ? option.message : "";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: "はい",
                               "class": "yes ",
                               size: "large",
                               color: "danger",
                               click: function(viewmodel, ui){
                                   viewmodel.okButtonClicked();
                                   ui.dialog("close");
                               }
                             });
            // Add Cancel Button
            this.buttons.push({text: "いいえ",
                               "class": "no ",
                               size: "large",
                               color: "",
                               click: function(viewmodel, ui){
                                   viewmodel.cancelButtonClicked();
                                   ui.dialog("close");
                               }
                             });
        }
    }
    
    class OKDialogOption extends DialogOption {
        constructor(option?: IDialogOption)
        {
            super();
            // Default value
            this.title = (option && option.title) ? option.title : "";
            this.message = (option && option.message) ? option.message : "";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: "はい",
                               "class": "yes ",
                               size: "large",
                               color: "proceed",
                               click: function(viewmodel, ui){
                                   viewmodel.okButtonClicked();
                                   ui.dialog("close");
                               }
                             });
            // Add Cancel Button
            this.buttons.push({text: "いいえ",
                               "class": "no ",
                               size: "large",
                               color: "",
                               click: function(viewmodel, ui){
                                   viewmodel.cancelButtonClicked();
                                   ui.dialog("close");
                               }
                             });
        }
    }
    
    type ButtonSize = "x-large" | "large" | "medium" | "small";
    type ButtonColor = "" | "danger" | "proceed";
    class DialogButton{
        text: string;
        "class": string;
        size: ButtonSize;
        color: ButtonColor;
        click(viewmodel, ui): void {};
    }