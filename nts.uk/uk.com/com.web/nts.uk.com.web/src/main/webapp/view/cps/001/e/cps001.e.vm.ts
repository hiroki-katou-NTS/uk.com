module cps001.e.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        imageId: KnockoutObservable<string> = ko.observable("");
        
        constructor(){  
            let self = this;         
        }
        start(){
            let self = this;
            self.imageId(getShared("imageId"));
            if(self.imageId() != "" && self.imageId() != undefined){
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

    interface IModelDto {
        
    }
}