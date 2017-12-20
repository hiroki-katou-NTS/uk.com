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
        isChange: KnockoutObservable<boolean> = ko.observable(false);
        isInit = true;
        constructor() {
            let self = this;
        }
        start() {
            let self = this;
            self.imageId(getShared("imageId"));
            if (self.imageId() != "" && self.imageId() != undefined) {
                self.getImage();
                $("#test").bind("imgloaded", function(evt, query?: SrcChangeQuery) {
                    if (!self.isInit) {
                        self.isChange(true);
                        return;
                    }
                    self.isInit = false;
                });
            } else self.isChange(true);
            $(".upload-btn").focus();
        }
        upload() {
            let self = this;
            nts.uk.ui.block.grayout();
            let isImageLoaded = $("#test").ntsImageEditor("getImgStatus");
            if ($("#test").data("cropper") == undefined) {
                self.close();
                return;
            }
            if ($("#test").data("cropper").cropped)
                self.isChange(true);
            if (isImageLoaded.imgOnView) {
                if (self.isChange()) {
                    $("#test").ntsImageEditor("upload", { stereoType: "image" }).done(function(data) {
                        self.imageId(data.id);
                        nts.uk.ui.block.clear();
                        setShared("imageId", self.imageId());
                        self.close();
                    });
                } else self.close();
            } else self.close();
        }
        getImage() {
            let self = this;
            let id = self.imageId();
            $("#test").ntsImageEditor("selectByFileId", id);
        }
        close() {
            nts.uk.ui.block.clear();
            close();
        }

    }
}