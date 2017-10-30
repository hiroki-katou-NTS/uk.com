__viewContext.ready(function () {
    class ScreenModel {
        url: KnockoutObservable<string>;
        uploaded: any
        
        constructor(){
            this.url = ko.observable("");    
        }
        
        upload(){
            var self = this;
            nts.uk.ui.block.grayout();
            $("#test").ntsImageEditor("upload", {stereoType: "image"}).done(function(data){
                self.uploaded = data;
                nts.uk.ui.block.clear();
            }).fail(function(error){
                alert(error.message);
                nts.uk.ui.block.clear();
            });
        }
        getImage(){
            if(!nts.uk.util.isNullOrUndefined(this.uploaded)){ 
               $("#test").ntsImageEditor("selectByFileId", this.uploaded.id); 
            }
        }
        show(){
            if(!nts.uk.util.isNullOrEmpty(this.url().trim())){
               $("#test").ntsImageEditor("showByUrl", this.url()); 
            }
        }
        clear(){
            $("#test").ntsImageEditor("clear");
        }
        
        dialog() {
            nts.uk.ui.windows.sub.modal("/view/sample/component/imageeditor/imageeditor.xhtml");    
        }
    }

    
    this.bind(new ScreenModel());
    
});