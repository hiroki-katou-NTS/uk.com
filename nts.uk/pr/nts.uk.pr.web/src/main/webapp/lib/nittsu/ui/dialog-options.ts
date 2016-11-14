    
    enum DialogType {
        Alert,
        Confirm
    }
    
    class DialogOption {
        dialogType: DialogType;
        title: string;
        message: string;
        modal: boolean;
        buttons: DialogButton[];
        
        // Default Constructor
        public constructor() {
        }
    }

    class AlertDialogOption extends DialogOption {
        //Default Contructor
        public constructor(){
            super();
            this.dialogType = DialogType.Alert;
            this.buttons = [];
            this.buttons.push({text: "はい", size: "large", color:"success", fontSize:""});
        }
    }
    
    class ConfirmDialogOption extends DialogOption {
        //Default Contructor
        public constructor(){
            super();
            this.dialogType = DialogType.Confirm;
            this.buttons = [];
            this.buttons.push({text: "はい", size: "large", color:"danger", fontSize:""});
            this.buttons.push({text: "いいえ", size: "large", color:"", fontSize:""});
        }
    }
    
    class DialogButton{
        text: string;
        size: string;
        color: string;
        fontSize: string;
        
        //Default Contructor
        constructor(){
        }
    }
