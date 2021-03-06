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
        fileIdOld : string;
        fileNameOld: string;

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
                    self.fileIdOld = data.fileId;
                    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + data.fileId).done(function(res) {
                        self.fileNameOld = res.originalName;
                    });
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
                    unblock();
                    $('.upload-btn').focus();
                });

            }).fail((mes) => {
                unblock();
            });

            permision().done((data: Array<IPersonAuth>) => {
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].functionNo == FunctionNo.No4_Allow_UploadMap) {
                            if (data[i].available == false) {
                                self.enaBtnSave(false);
                                $(".upload-btn").attr('disabled', 'disabled');
                            }
                        }
                    }
                }
                $('.upload-btn').focus();
            });            

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
                        self.oldEmpFileMn = { 
                        employeeId: self.empFileMn().employeeId, 
                        fileId: self.empFileMn().fileId, 
                        fileType: self.empFileMn().fileType, 
                        isAvatar: false ,
                        fileName : data.originalName,
                        categoryName : nts.uk.resource.getText("CPS001_152"), 
                        itemName : nts.uk.resource.getText("CPS001_155"), 
                        fileIdOld:self.fileIdOld , 
                        fileNameOld : self.fileNameOld};
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
                            service.updateAvaOrMap(oldEmpFileMn).done(function() {
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
                    service.insertAvaOrMap(oldEmpFileMn).done(function() {
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
                $("#test").ntsImageEditor("selectByFileId", {
                    fileId: id, actionOnClose: function() {
                        unblock();
                        self.isChange(true);
                        $(".checkbox-holder").hide();
                    }
                });
            } catch (Error) {
                self.isChange(true);
            }

        }
        close() {
            close();
        }
    }

    interface IPersonAuth {
        functionNo: number;
        functionName: string;
        available: boolean;
        description: string;
        orderNumber: number;
    }


    interface IEmpFileMn {
        employeeId: string;
        fileId?: string;
        fileType?: number;
        isAvatar: boolean;
    }
    
    enum FunctionNo {
        No1_Allow_DelEmp = 1, // c?? th??? delete employee ??? ????ng k?? th??ng tin c?? nh??n
        No2_Allow_UploadAva = 2, // c?? th??? upload ???nh ch??n dung employee ??? ????ng k?? th??ng tin c?? nh??n
        No3_Allow_RefAva = 3,// c?? th??? xem ???nh ch??n dung employee ??? ????ng k?? th??ng tin c?? nh??n
        No4_Allow_UploadMap = 4, // c?? th??? upload file b???n ????? ??? ????ng k?? th??ng tin c?? nh??n
        No5_Allow_RefMap = 5, // c?? th??? xem file b???n ????? ??? ????ng k?? th??ng tin c?? nh??n
        No6_Allow_UploadDoc = 6,// c?? th??? upload file ??i???n t??? employee ??? ????ng k?? th??ng tin c?? nh??n
        No7_Allow_RefDoc = 7,// c?? th??? xem file ??i???n t??? employee ??? ????ng k?? th??ng tin c?? nh??n
        No8_Allow_Print = 8,  // c?? th??? in bi???u m???u c???a employee ??? ????ng k?? th??ng tin c?? nh??n
        No9_Allow_SetCoppy = 9,// c?? th??? setting copy target item khi t???o nh??n vi??n m???i ??? ????ng k?? m???i th??ng tin c?? nh??n
        No10_Allow_SetInit = 10, // c?? th??? setting gi?? tr??? ban ?????u nh???p v??o khi t???o nh??n vi??n m???i ??? ????ng k?? m???i th??ng tin c?? nh??n
        No11_Allow_SwitchWpl = 11  // L???c ch???n l???a ph??ng ban tr???c thu???c/workplace tr???c ti???p theo b??? ph???n li??n k???t c???p d?????i t???i ????ng k?? th??ng tin c?? nh??n
    }
}