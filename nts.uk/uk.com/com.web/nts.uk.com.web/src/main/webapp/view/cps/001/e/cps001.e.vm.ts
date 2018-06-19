module cps001.e.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import permision = service.getCurrentEmpPermision;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {

        empFileMn: KnockoutObservable<IEmpFileMn> = ko.observable(<IEmpFileMn>{});
        oldEmpFileMn = {};
        isChange: KnockoutObservable<boolean> = ko.observable(false);
        enaBtnSave: KnockoutObservable<boolean> = ko.observable(true);
        isInit = true;
        overSize = false;

        constructor() {
            let self = this;
        }
        start() {
            let self = this,
                params = getShared("CPS001E_PARAMS");
            self.empFileMn().employeeId = params.employeeId;
            self.empFileMn().isAvatar = false;
            //get employee file management domain by employeeId
            block();
            service.getMap(self.empFileMn().employeeId).done(function(data) {
                if (data.fileId != null) {
                    self.empFileMn().fileId = data.fileId ? data.fileId : "";
                    self.empFileMn().fileType = 1;
                    if (self.empFileMn().fileId != "" && self.empFileMn().fileId != undefined)
                        self.getImage();
                    else self.isChange(true);
                    self.oldEmpFileMn = { employeeId: self.empFileMn().employeeId, fileId: self.empFileMn().fileId, fileType: self.empFileMn().fileType };
                } else {
                    unblock();
                    self.isChange(true);
                }

                $("#test").bind("imgloaded", function(evt, query?: SrcChangeQuery) {
                    if (!self.isInit) {
                        self.isChange(true);
                        self.overSize = false;
                        unblock();
                        if (query.size > 10485760) { // 10485760 = 10MB
                            self.overSize = true;
                            alertError({ messageId: "Msg_77" });
                        }
                        return;
                    }
                    self.isInit = false;
                    $('.upload-btn').focus();
                    unblock();
                });

            }).fail((mes) => {
                unblock();
            });

            permision().done((data: IPersonAuth) => {
                if (data) {
                    if (data.allowMapUpload != 1) {
                        self.enaBtnSave(false);
                        $(".upload-btn").attr('disabled', 'disabled');
                        $('.upload-btn').focus();
                    }
                }
            });

            $('.upload-btn').focus();

        }

        upload() {
            let self = this;
            nts.uk.ui.block.grayout();

            if (nts.uk.ui.errors.hasError() || self.overSize) {
                if (self.overSize) {
                    alertError({ messageId: "Msg_77" });
                }
                unblock();
                return;
            }

            let isImageLoaded = $("#test").ntsImageEditor("getImgStatus");

            if (isImageLoaded.imgOnView) {
                if (self.isChange()) {
                    $("#test").ntsImageEditor("upload", { stereoType: "avatarfile" }).done(function(data) {
                        self.empFileMn().fileId = data.id;
                        self.oldEmpFileMn = { employeeId: self.empFileMn().employeeId, fileId: self.empFileMn().fileId, fileType: self.empFileMn().fileType, isAvatar: false };
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
                    confirm({ messageId: "Msg_386", messageParams: [nts.uk.resource.getText("CPS001_69")] }).ifYes(() => {
                        //insert employee file management
                        block();
                        service.removeAvaOrMap(oldEmpFileMn).done(function() {
                            service.insertAvaOrMap(currentEmpFileMn).done(function() {
                                setShared("CPS001E_VALUES", ko.unwrap(self.empFileMn));
                                unblock();
                                self.close();
                            }).always(function() { nts.uk.ui.block.clear(); });
                        }).fail((mes) => {
                            unblock();
                        });
                    }).ifNo(() => { nts.uk.ui.block.clear(); });
                } else {
                    //insert employee file management
                    block();
                    service.insertAvaOrMap(currentEmpFileMn).done(function() {
                        setShared("CPS001E_VALUES", ko.unwrap(self.empFileMn));
                        unblock();
                        self.close();
                    }).fail((mes) => {
                        unblock();
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            });
        }

        getImage() {
            let self = this;
            let id = self.empFileMn().fileId;
            try {
                $("#test").ntsImageEditor("selectByFileId", {fileId: id, actionOnClose: function(){
                     unblock();
+                    self.isChange(true);
+                    $(".checkbox-holder").hide();
+                    $('.upload-btn').focus(); 
                }});
            } catch (Error) {
                self.isChange(true);
            }

        }
        close() {
            close();
        }
    }

    interface IPersonAuth {
        roleId: string;
        allowMapUpload: number;
        allowMapBrowse: number;
        allowDocRef: number;
        allowDocUpload: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;
    }


    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
        isAvatar: boolean;
    }
}