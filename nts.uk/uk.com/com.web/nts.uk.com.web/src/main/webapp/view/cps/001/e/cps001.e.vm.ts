module cps001.e.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {

        empFileMn: KnockoutObservable<IEmpFileMn>= ko.observable(<IEmpFileMn>{});
        oldEmpFileMn = {};
        isChange: KnockoutObservable<boolean> = ko.observable(false);
        isInit = true;

        constructor() {
            let self = this;
        }
        start() {
            let self = this, 
             params = getShared("CPS001E_PARAMS");
            self.empFileMn().employeeId = params.employeeId;
            //get employee file management domain by employeeId
            service.getAvatar(self.empFileMn().employeeId).done(function(data) {
                if (data) {
                    self.empFileMn().fileId = data.fileId ? data.fileId : "";
                    self.empFileMn().fileType = 1;
                    if (self.empFileMn().fileId != "" && self.empFileMn().fileId != undefined)
                        self.getImage();
                    self.oldEmpFileMn = { employeeId: self.empFileMn().employeeId, fileId: self.empFileMn().fileId, fileType: self.empFileMn().fileType };
                }
                $("#test").bind("imgloaded", function(evt, query?: SrcChangeQuery) {
                    if (!self.isInit) {
                        self.isChange(true);
                        return;
                    }
                    self.isInit = false;
                });
                
            });

        }

        upload() {
            let self = this;
            nts.uk.ui.block.grayout();
            let isImageLoaded = $("#test").ntsImageEditor("getImgStatus");
            
            if (isImageLoaded.imgOnView) {
                if (self.isChange()) {
                    $("#test").ntsImageEditor("upload", { stereoType: "image" }).done(function(data) {
                        self.empFileMn().fileId = data.id;
                        self.updateImage(self.oldEmpFileMn, ko.toJS(self.empFileMn()));
                    });
                } else self.close();
            } else {
                alertError({ messageId: "Msg_617" });
                nts.uk.ui.block.clear();
            }
        }

        updateImage(oldEmpFileMn, currentEmpFileMn) {
            let self = this;
            service.checkEmpFileMnExist(currentEmpFileMn.employeeId).done(function(isExist) {
                if (isExist) {
                    confirm({ messageId: "Msg_386", messageParams: "CPS001_69" }).ifYes(() => {
                        //insert employee file management
                        service.removeAvaOrMap(oldEmpFileMn).done(function() {
                            service.insertAvaOrMap(currentEmpFileMn).done(function() {
                                setShared("CPS001E_VALUES", ko.unwrap(self.empFileMn));
                                self.close();
                            }).always(function() { nts.uk.ui.block.clear(); });
                        });
                    }).ifNo(() => { nts.uk.ui.block.clear(); });
                } else {
                    //insert employee file management
                    service.insertAvaOrMap(currentEmpFileMn).done(function() {
                        setShared("CPS001E_VALUES", ko.unwrap(self.empFileMn));
                        self.close();
                    }).always(function() { nts.uk.ui.block.clear(); });
                }
            });
        }

        getImage() {
            let self = this;
            let id = self.empFileMn().fileId;
            $("#test").ntsImageEditor("selectByFileId", id);
        }
        close() {
            close();
        }
    }

    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
    }   
}