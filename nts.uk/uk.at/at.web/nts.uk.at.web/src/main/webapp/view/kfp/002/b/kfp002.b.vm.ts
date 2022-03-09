module nts.uk.at.view.kfp002.b {

    const Paths = {        
        getAllFormats: "at/screen/kfp002/b/formats"
    };

    @bean()
    class Kfp002bViewModel extends ko.ViewModel {
        formats: KnockoutObservableArray<any>;
        selectedFormatCode: KnockoutObservable<string>;
      
        constructor() {
            super();
            const self = this;            
            self.formats = ko.observableArray([]);
            self.selectedFormatCode = ko.observable(null);
        }

        created() {
            const self = this;
            self.$blockui("show");
            self.$ajax(Paths.getAllFormats).done(formats => {
                self.formats(formats);
            }).fail(error => {
                self.$dialog.alert(error).then(() => {
                    if (error.messageId == "Msg_1402") {
                        nts.uk.request.jumpFromDialogOrFrame("com", "view/ccg/008/a/index.xhtml");
                    }
                });
            }).always(() => {
                self.$blockui("hide");
            });
        }

        mounted() {
            $('#B4_1').focus();
        }

        changeFormat(): void {
            const self = this;                                
            if (_.isNil(self.selectedFormatCode())) {
                self.$dialog.alert({messageId: "Msg_3276"});
                return;
            }

            self.$window.close(self.selectedFormatCode());
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }
}