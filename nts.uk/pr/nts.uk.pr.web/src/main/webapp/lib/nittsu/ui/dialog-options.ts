   
    abstract class DialogOption {
        modal: boolean;
        show: boolean = false;
        protected buttons: DialogButton[];
    }
    
    // Normal Dialog
    interface IDialogOption {
        modal?: boolean
    }
    
    class ConfirmDialogOption extends DialogOption {
        constructor(option?: IDialogOption)
        {
            super();
            // Default value
            this.modal = (option && option.modal !== undefined) ? option.modal : true;
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
            this.modal = (option && option.modal !== undefined) ? option.modal : true;
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
            this.modal = (option && option.modal !== undefined) ? option.modal : true;
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
    
    // Error Dialog
    interface IErrorDialogOption {
        modal?: boolean,
        displayrows?: number,
        maxrows?: number,
        autoclose?: boolean
    }
    
    class ErrorDialogOption extends DialogOption {
        displayrows: number;
        maxrows: number;
        autoclose: boolean;
        
        constructor(option?: IErrorDialogOption)
        {
            super();
            // Default value
            this.modal = (option && option.modal !== undefined) ? option.modal : false;
            this.displayrows = (option && option.displayrows) ? option.displayrows : 10;
            this.maxrows = (option && option.maxrows) ? option.maxrows : 1000;
            this.autoclose = (option && option.autoclose !== undefined) ? option.autoclose : true;
            
            this.buttons = [];
            // Add Close Button
            this.buttons.push({text: "閉じる",
                               "class": "yes ",
                               size: "large",
                               color: "",
                               click: function(viewmodel, ui){
                                   viewmodel.closeButtonClicked();
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