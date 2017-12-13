module cps001.d.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        empFileMn: KnockoutObservable<IEmpFileMn> = ko.observable(<IEmpFileMn>{});
        oldEmpFileMn = {};
        isChange: KnockoutObservable<boolean> = ko.observable(false);
        isInit = true;
        
        constructor(){  
            let self = this;          
        }
        start(){
            let self = this,
            params: IEmpFileMn = getShared("CPS001D_PARAMS");
            
            self.empFileMn().employeeId = params.employeeId;
            //get employee file management domain by employeeId
            service.getAvatar(self.empFileMn().employeeId).done(function(data){
                if(data.filedId){
                    self.empFileMn().fileId = data.fileId;
                    self.empFileMn().fileType =0;
                    if(self.empFileMn().fileId != "" && self.empFileMn().fileId != undefined)
                        self.getImage();
                    else self.isChange(true);
                    self.oldEmpFileMn = {employeeId: self.empFileMn().employeeId, fileId: self.empFileMn().fileId, fileType: self.empFileMn().fileType};
                }else self.isChange(true);
                 $("#test").bind("imgloaded", function(evt, query?: SrcChangeQuery) {
                    if (!self.isInit) {
                        self.isChange(true);
                        return;
                    }
                    self.isInit = false;
                });
                
            });
            
        }
        
        upload(){
            let self = this;
            nts.uk.ui.block.grayout();
            let isImageLoaded = $("#test").ntsImageEditor("getImgStatus");
            if($("#test").data("cropper") == undefined) {
                self.close();
                return;
            }
            if($("#test").data("cropper").cropped)
                self.isChange(true);
            if(isImageLoaded.imgOnView){
                if (self.isChange()) {
                    $("#test").ntsImageEditor("upload", { stereoType: "image" }).done(function(data) {
                        self.empFileMn().fileId = data.id;
                        self.updateImage(self.oldEmpFileMn, ko.toJS(self.empFileMn()));
                    });
                } else self.close();
            }else{
                self.close();
            }          
        }
        
        updateImage(oldEmpFileMn, currentEmpFileMn){
            let self = this;
            service.checkEmpFileMnExist(currentEmpFileMn.employeeId).done(function(isExist){
                        if(isExist){
                             confirm({messageId: "Msg_386", messageParams:"CPS001_68"}).ifYes(()=>{
                                //insert employee file management
                                service.removeAvaOrMap(oldEmpFileMn).done(function(){
                                     service.insertAvaOrMap(currentEmpFileMn).done(function(){
                                        setShared("CPS001D_VALUES", ko.unwrap(self.empFileMn));
                                        self.close();
                                     }).always(function(){ nts.uk.ui.block.clear();});
                                });
                            }).ifNo(()=>{
                                nts.uk.ui.block.clear();
                            });
                           
                        }else{
                            //insert employee file management
                            service.insertAvaOrMap(currentEmpFileMn).done(function(){
                                setShared("CPS001D_VALUES", ko.unwrap(self.empFileMn));
                                self.close();
                            }).always(function(){ nts.uk.ui.block.clear();});
                        }
                    }); 
        }
        
        getImage(){
            let self = this;
            let id = self.empFileMn().fileId;
            try{
                 $("#test").ntsImageEditor("selectByFileId", id);
            }catch(Error){
                self.isChange(true);
            }
        }
        close(){
            nts.uk.ui.block.clear();
           close();
        }
    }

    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
    }
}