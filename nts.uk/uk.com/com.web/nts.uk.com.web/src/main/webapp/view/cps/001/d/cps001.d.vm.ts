module cps001.d.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        empFileMn: KnockoutObservable<EmpFileMn> = ko.observable(new EmpFileMn({employeeId: "", fileId: "", fileType: -1}));
        oldEmpFileMn = {};
        
        constructor(){  
            let self = this;         
        }
        start(){
            let self = this;
            self.empFileMn().employeeId(getShared("employeeId"));
            //get employee file management domain by employeeId
            service.getAvatar(self.empFileMn().employeeId()).done(function(data){
                if(data){
                    self.empFileMn().fileId(data.fileId);
                    self.empFileMn().fileType(data.fileType);
                    self.empFileMn().fileType(0);
                    if(self.empFileMn().fileId() != "" && self.empFileMn().fileId() != undefined)
                        self.getImage();
                    self.oldEmpFileMn = {employeeId: self.empFileMn().employeeId(), fileId: self.empFileMn().fileId(), fileType: self.empFileMn().fileType()};
                }
                
            });
            
        }
        upload(){
            let self = this;
            nts.uk.ui.block.grayout();
            $("#test").ntsImageEditor("upload", {stereoType: "image"}).done(function(data){
                self.empFileMn().fileId(data.id);
                
                service.checkEmpFileMnExist(self.empFileMn().employeeId()).done(function(isExist){
                    if(isExist){
                        //insert employee file management
                        service.removeAvaOrMap(self.oldEmpFileMn).done(function(){
                             service.insertAvaOrMap(ko.toJS(self.empFileMn())).done(function(){
                            setShared("imageId", self.empFileMn().fileId());
                            self.close();
                        }).always(function(){ nts.uk.ui.block.clear();});
                            });
                       
                    }else{
                        //insert employee file management
                        service.insertAvaOrMap(ko.toJS(self.empFileMn())).done(function(){
                            setShared("imageId", self.empFileMn().fileId());
                            self.close();
                        }).always(function(){ nts.uk.ui.block.clear();});
                    }
                }); 
            });
        }
        getImage(){
            let self = this;
            let id = self.empFileMn().fileId();
            $("#test").ntsImageEditor("selectByFileId", id); 
        }
        close(){
           close();
        }
    }

    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
    }
    class EmpFileMn{
        employeeId: KnockoutObservable<string> = ko.observable("");
        fileId: KnockoutObservable<string> = ko.observable("");
        fileType: KnockoutObservable<number> = ko.observable(-1);
        constructor(data: IEmpFileMn){
            let self = this;
            self.employeeId(data.employeeId);
            self.fileId(data.fileId);
            self.fileType(data.fileType);
        }
    }
}