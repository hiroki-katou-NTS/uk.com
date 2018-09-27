module nts.uk.sys.view.ccg013.c.viewmodel {
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        //Text edittor
        nameTitleBar: KnockoutObservable<string>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //Radio button
        itemTitleAtr: KnockoutObservableArray<any>;
        selectedTitleAtr: KnockoutObservable<number>;
        //GridList
        listTitleMenu: KnockoutObservableArray<TitleMenu>;
        columns: KnockoutObservableArray<any>;
        currentTitleMenu: KnockoutObservable<string>;
        enableGrid: KnockoutObservable<boolean>;

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
            //Radio button
            self.itemTitleAtr = ko.observableArray([
                { value: 0, titleAtrName: resource.getText('CCG013_34') },
                { value: 1, titleAtrName: resource.getText('CCG013_35') }]);
            self.selectedTitleAtr = ko.observable(0);  
            self.enableGrid = ko.observable(false);
            $("#titleSeach").prop("disabled", true);
            _.defer(function() {
                $(".ntsSearchBox").prop('disabled', true);
            });

            //color picker
            self.letterColor = ko.observable('#FFFFFF');
            self.backgroundColor = ko.observable('#FF9900');
            //GridList
            self.listTitleMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_39"), key: 'titleCode', width: 100 },
                { headerText: nts.uk.resource.getText("CCG013_40"), key: 'titleName', width: 230 }
            ]);
            //self.selectCodeTitleMenu = ko.observable('');
            self.currentTitleMenu = ko.observable('');
            //delete button 
            self.isDelete = ko.observable(false);
            //image upload
            self.filename = ko.observable(''); //file name
            self.imageName = ko.observable(nts.uk.resource.getText("CCG013_127"));
            self.imageSize = ko.observable(nts.uk.text.format(resource.getText('CCG013_44'), 0));
            self.accept = ko.observableArray([".png"]); //supported extension
            self.textId = ko.observable("CCG013_42"); // file browser button text id
            self.fileID = ko.observable('');
            self.fileID.subscribe(function(id) {
                if (id) {
                    var liveviewcontainer = $("#liveview");
                    liveviewcontainer.html("");
                    liveviewcontainer.append($("<img/>").attr("src", nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/" + id)));
                }
            });
            self.selectedTitleAtr.subscribe(function(atrValue) {
                if (atrValue == 0) {
                    //self.selectCodeTitleMenu('');
                    self.currentTitleMenu('');
                    self.enableGrid(false);
                } else {
                    self.enableGrid(true);
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get TitleBar*/
            service.getTitleMenu().done(function(titleMenu: any) {
                let lstSource: Array<TitleMenu> = [];
                if (titleMenu.length > 0) {
                    _.forEach(titleMenu, function(item) {
                        lstSource.push(new TitleMenu(item.titleMenuCD, item.name));
                    })
                }
                self.listTitleMenu(lstSource);
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
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
            nts.uk.ui.block.invisible();
            $("#file_upload").ntsFileUpload({ stereoType: "titleBar" }).done(function(res) {
                self.fileID(res[0].id);
                self.filename('');
                self.imageName(res[0].originalName);
                self.imageSize(nts.uk.text.format(resource.getText('CCG013_44'), res[0].originalSize));
                self.isDelete(true);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError(err.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            });
        }

        private deleteFile(): void {
            var self = this;
            self.imageName(nts.uk.resource.getText("CCG013_127"));
            self.imageSize(nts.uk.text.format(resource.getText('CCG013_44'), 0));
            $("#liveview").html('');
            self.isDelete(false);
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;
            $(".ntsColorPicker_Container").trigger("validate");
            validateNameInput($("#nameTitleBar"), '#[CCG013_31]', self.nameTitleBar().trim(), 'TitleBarName');

            var hasError = nts.uk.ui.errors.hasError();
            if (hasError) {
                return;
            }
            if (self.selectedTitleAtr() == 1) {
                if (self.currentTitleMenu() !== '') {
                    var titleBar1 = new TitleBar(self.nameTitleBar(), self.letterColor(), self.backgroundColor(), self.selectedTitleAtr(), self.fileID(), self.currentTitleMenu(), self.imageName(), self.imageSize());
                    windows.setShared("CCG013C_TitleBar", titleBar1);
                    self.cancel_Dialog();
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_75" });
                    return;
                }
            } else {
                var titleBar0 = new TitleBar(self.nameTitleBar(), self.letterColor(), self.backgroundColor(), self.selectedTitleAtr(), self.fileID(), '', self.imageName(), self.imageSize());
                windows.setShared("CCG013C_TitleBar", titleBar0);
                self.cancel_Dialog();
            }

        }
    }

    class TitleBar {
        nameTitleBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedTitleAtr: number;
        imageId: string;
        titleMenuCode: string;
        imageName: string;
        imageSize: string;


        constructor(nameTitleBar: string, letterColor: string, backgroundColor: string, selectedTitleAtr: number, imageId: string, titleMenuCode: string, imageName: string, imageSize: string) {
            this.nameTitleBar = nameTitleBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.selectedTitleAtr = selectedTitleAtr;
            this.imageId = imageId;
            this.titleMenuCode = titleMenuCode;
            this.imageName = imageName;
            this.imageSize = imageSize;
        }
    }

    class TitleMenu {
        titleCode: string;
        titleName: string;
        constructor(titleCode: string, titleName: string) {
            this.titleCode = titleCode;
            this.titleName = titleName;
        }
    }
}