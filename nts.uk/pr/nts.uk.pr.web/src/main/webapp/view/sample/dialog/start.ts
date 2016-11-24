import option = nts.uk.ui.option;

__viewContext.ready(function () {
        
    $('#dialog-open').click(function(){
        vm.dialogconfirm.open();
    })
    
    function ErrorHeader(name, text, width, visible){
        this.name = name;
        this.text = text;
        this.width = width;
        this.visible = visible;
    };
    
    var vm = {
        // Dialog Confirm
        dialogconfirm: {
            title: ko.observable(""),
            message: ko.observable(""),
            option: ko.mapping.fromJS(new option.ConfirmDialogOption()),
            okButtonClicked: function(){},
            cancelButtonClicked: function(){},
            open: function(){
                var self = this;
                self.option.show(true);
            },
            hide: function(){
                var self = this;
                self.option.show(false);
            }
        },
        
        // Dialog Delete
        dialogdel: {
            title: ko.observable(""),
            message: ko.observable("You want a message, you get a message."),
            option: ko.mapping.fromJS(new option.DelDialogOption()),
            okButtonClicked: function(){
                alert("Ok");
            },
            cancelButtonClicked: function(){
                alert("Cancel");
            },
            open: function(){
                var self = this;
                self.option.show(true);
            },
            hide: function(){
                var self = this;
                self.option.show(false);
            }
        },

        // Dialog OK
        dialogok: {
            title: ko.observable("A test Title"),
            message: ko.observable("This is a test message created for test purpose."),
            option: ko.mapping.fromJS(new option.OKDialogOption()),
            okButtonClicked: function(){},
            cancelButtonClicked: function(){},
            open: function(){
                var self = this;
                self.option.show(true);
            },
            hide: function(){
                var self = this;
                self.option.show(false);
            }
        }
    };
     // developer's view model
    this.bind(vm);
    
});