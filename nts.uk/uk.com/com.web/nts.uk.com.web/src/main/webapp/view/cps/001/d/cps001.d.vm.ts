module cps001.d.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        imageId: KnockoutObservable<string> = ko.observable("");
        empFileMn: KnockoutObservable<EmpFileMn> = ko.observable(new EmpFileMn({employeeId: "", fileId: "", fileType: -1}));
        
        constructor(){  
            let self = this;         
        }
        start(){
            let self = this;
            self.empFileMn().employeeId(getShared("employeeId"));
            //get employee file management domain by employeeId
            
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

    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
    }
    class EmpFileMn{
        employeeId: KnockoutObservable<string>;
        fileId: KnockoutObservable<string>;
        fileType: KnockoutObservable<number>;
        constructor(data: IEmpFileMn){
            let self = this;
            self.employeeId(data.employeeId);
            self.fileId(data.fileId);
            self.fileType(data.fileType);
        }
    }
}