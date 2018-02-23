module nts.uk.sys.view.ccg013.j.viewmodel {
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        //Text edittor
        nameTitleBar: KnockoutObservable<string>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //file Upload
        filename: KnockoutObservable<string>;
        imageName: KnockoutObservable<string>;
        imageSize: KnockoutObservable<string>;
        textId: KnockoutObservable<string>;
        accept: KnockoutObservableArray<string>;
        fileID: KnockoutObservable<string>;
        //delete Button 
        isDelete: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.nameTitleBar = ko.observable("");
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //image upload
            self.filename = ko.observable("");
            //file name
            self.imageName = ko.observable("未設定。");
            self.imageSize = ko.observable(nts.uk.text.format(resource.getText('CCG013_99'), 0));
            self.accept = ko.observableArray([".png"]);
            //supported extension
            self.textId = ko.observable("CCG013_97");
            // file browser button text id
            self.fileID = ko.observable('');
            var liveviewcontainer = $("#liveview");

            self.fileID.subscribe(function(id) {
                if (id) {
                    liveviewcontainer.html("");
                    liveviewcontainer.append($("<img/>").attr("src", nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/" + id)));
                }
            });

            self.isDelete = ko.pureComputed(function() {
                return !!self.fileID();
            });

            var setShareTitleMenu = nts.uk.ui.windows.getShared("CCG013A_ToChild_TitleBar");
            if (setShareTitleMenu !== undefined) {
                self.fileID(setShareTitleMenu.imageFile);
                self.nameTitleBar(setShareTitleMenu.titleMenuName);
                self.letterColor(setShareTitleMenu.textColor);
                self.backgroundColor(setShareTitleMenu.backgroundColor);
                nts.uk.request.ajax("/shr/infra/file/storage/infor/" + setShareTitleMenu.imageFile).done(function(res) {
                    self.imageName(res.originalName);
                    self.imageSize(nts.uk.text.format(resource.getText('CCG013_44'), res.originalSize));
                });
                if (!!self.fileID()) {
                    liveviewcontainer.html("");
                    liveviewcontainer.append($("<img/>").attr("src", nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/" + self.fileID())));
                }
            }
        }

        /** Upload File */
        uploadFile(): void {
            var self = this;
            self.uploadFileProcess();
        }

        private uploadFileProcess(): void {
            var self = this;
            var option = {
                stereoType: "titleBar",
                onSuccess: function() { alert('upload Success'); },
                onFail: function() { alert('upload Fails') }
            }
            $("#file_upload").ntsFileUpload({ stereoType: "titleBar" }).done(function(res) {
                self.fileID(res[0].id);
                self.filename('');
                self.imageName(res[0].originalName);
                self.imageSize(nts.uk.text.format(resource.getText('CCG013_99'), res[0].originalSize));
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err.message);
            });
        }

        private deleteFile(): void {
            var self = this;
            self.imageName('未設定。');
            self.imageSize(nts.uk.text.format(resource.getText('CCG013_99'), 0));
            self.fileID('');
            $("#liveview").html('');
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;

            $(".ntsColorPicker_Container").trigger("validate");
            validateNameInput($("#title-bar-name"), '#[CCG013_93]', self.nameTitleBar().trim(), 'TitleBarName');

            if (nts.uk.ui.errors.hasError() !== true) {
                var titleBar = new TitleBar(self.nameTitleBar(), self.letterColor(), self.backgroundColor(), self.fileID(), self.imageName(), self.imageSize());
                windows.setShared("CCG013J_ToMain_TitleBar", titleBar);
                self.cancel_Dialog();
            }
        }
    }

    class TitleBar {
        nameTitleBar: string;
        letterColor: string;
        backgroundColor: string;
        imageId: string;
        imageName: string;
        imageSize: string;

        constructor(nameTitleBar: string, letterColor: string, backgroundColor: string, imageId: string, imageName: string, imageSize: string) {
            this.nameTitleBar = nameTitleBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.imageId = imageId;
            this.imageName = imageName;
            this.imageSize = imageSize;
        }
    }

}