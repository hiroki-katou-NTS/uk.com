    interface IDialogOption {
        title?: string, 
        message?: string,
        modal?: boolean,
        show?: boolean,
        buttonSize?: ButtonSize,
        okButtonColor?: ButtonColor,
        okButtonText?: string,
        cancelButtonText?: string
    }

    abstract class DialogOption {
        dialogType: DialogType;
        title: string;
        message: string;
        modal: boolean;
        show: boolean;
        buttonSize: ButtonSize;
        okButtonColor: ButtonColor;
        okButtonText: string;
        cancelButtonText: string;
        protected buttons: DialogButton[];
    }

    class ConfirmDialogOption extends DialogOption {
        constructor(option?: IDialogOption)
        {
            super();
            this.dialogType = "alert";
            this.title = (option && option.title) || "";
            this.message = (option && option.message) || "";
            this.modal = (option && option.modal) || false;
            this.show = (option && option.show) || false;
            this.buttonSize = (option && option.buttonSize) || "large";
            this.okButtonColor = (option && option.okButtonColor) || "proceed";
            this.okButtonText = (option && option.okButtonText) || "OK";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: this.okButtonText,
                               "class": "yes",
                               size: this.buttonSize,
                               color: this.okButtonColor,
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
            this.dialogType = "confirm";
            this.title = (option && option.title) || "";
            this.message = (option && option.message) || "";
            this.modal = (option && option.modal) || true;
            this.show = (option && option.show) || false;
            this.buttonSize = (option && option.buttonSize) || "large";
            this.okButtonColor = (option && option.okButtonColor) || "danger";
            this.okButtonText = (option && option.okButtonText) || "はい";
            this.cancelButtonText = (option && option.cancelButtonText) || "いいえ";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: this.okButtonText,
                               "class": "yes ",
                               size: this.buttonSize,
                               color: this.okButtonColor,
                               click: function(viewmodel, ui){
                                   viewmodel.okButtonClicked();
                                   ui.dialog("close");
                               }
                             });
            // Add Cancel Button
            this.buttons.push({text: this.cancelButtonText,
                               "class": "no ",
                               size: this.buttonSize,
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
            this.dialogType = "confirm";
            this.title = (option && option.title) || "";
            this.message = (option && option.message) || "";
            this.modal = (option && option.modal) || true;
            this.show = (option && option.show) || false;
            this.buttonSize = (option && option.buttonSize) || "large";
            this.okButtonColor = (option && option.okButtonColor) || "proceed";
            this.okButtonText = (option && option.okButtonText) || "はい";
            this.cancelButtonText = (option && option.cancelButtonText) || "いいえ";
            this.buttons = [];
            // Add OK Button
            this.buttons.push({text: this.okButtonText,
                               "class": "yes ",
                               size: this.buttonSize,
                               color: this.okButtonColor,
                               click: function(viewmodel, ui){
                                   viewmodel.okButtonClicked();
                                   ui.dialog("close");
                               }
                             });
            // Add Cancel Button
            this.buttons.push({text: this.cancelButtonText,
                               "class": "no ",
                               size: this.buttonSize,
                               color: "",
                               click: function(viewmodel, ui){
                                   viewmodel.cancelButtonClicked();
                                   ui.dialog("close");
                               }
                             });
        }
    }


    type DialogType = "alert" | "confirm";
    type ButtonSize = "x-large" | "large" | "medium" | "small";
    type ButtonColor = "" | "danger" | "proceed";
    class DialogButton{
        text: string;
        "class": string;
        size: ButtonSize;
        color: ButtonColor;
        click(viewmodel, ui): void {};
    }
