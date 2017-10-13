module cps002.i.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;
    

    export class ViewModel {
        imageId: KnockoutObservable<string> = ko.observable("");
        
        constructor(){  
            let self = this;         
        }
        start(){
            let self = this;
            self.imageId(getShared("imageId"));
            if(self.imageId() != ""){
                self.getImage();
            }
        }
        upload(){
            let self = this;
            nts.uk.ui.block.grayout();
            $("#test").ntsImageEditor("upload", {stereoType: "image"}).done(function(data){
                self.imageId(data.id);
                nts.uk.ui.block.clear();
                setShared("imageId", self.imageId());
                self.close();
            });
        }
        getImage(){
            let self = this;
            let id = self.imageId();
            $("#test").ntsImageEditor("selectByFileId", id); 
        }
        close(){
           close();
        }
        
    }
}