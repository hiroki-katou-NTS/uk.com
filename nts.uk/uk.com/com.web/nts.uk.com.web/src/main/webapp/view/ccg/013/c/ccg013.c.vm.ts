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
        currentTitleMenu: KnockoutObservableArray<any>;
        selectCodeTitleMenu: KnockoutObservable<string>;

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
                { value: 1, titleAtrName: resource.getText('CCG013_34') },
                { value: 2, titleAtrName: resource.getText('CCG013_35') }]);
            self.selectedTitleAtr = ko.observable(1);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            self.listTitleMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'titleCode', width: 100 },
                { headerText: '名称', key: 'titleName', width: 230 }
            ]);
            self.selectCodeTitleMenu = ko.observable('');
            self.currentTitleMenu = ko.observableArray([]);
            //delete button 
            self.isDelete = ko.observable(false);
            //image upload
            self.filename = ko.observable(""); //file name
            self.imageName = ko.observable("");
            self.imageSize = ko.observable("");
            self.accept = ko.observableArray([".png", '.img']); //supported extension
            self.textId = ko.observable(""); // file browser button text id
            self.fileID = ko.observable('');
            self.fileID.subscribe(function(id) {
                if (id) {
                    var liveviewcontainer = $("#liveview");
                    liveviewcontainer.html("");
                    liveviewcontainer.append($("<img/>").attr("src", nts.uk.request.resolvePath("/webapi/shr/infra/file/storage/liveview/" + id)));
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
                onFail: function() { alert('upload Fails')}
            }
            $("#file_upload").ntsFileUpload({ stereoType: "titleBar" }).done(function(res) {
                self.fileID(res[0].id);
                self.filename('');
                self.imageName(res[0].originalName);
                self.imageSize(res[0].originalSize+'KB');
                self.isDelete(true);
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError({ messageId: err.messageId });
            });
        }

        private deleteFile(): void {
            var self = this;
            self.imageName('');
            self.imageSize('');
            $("#liveview").html('');
            self.isDelete(false);
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        submit() {
            var self = this;
            var titleBar = new TitleBar(self.nameTitleBar(), self.letterColor(), self.backgroundColor(), self.selectedTitleAtr(),self.fileID());
            windows.setShared("CCG013C_TitleBar", titleBar);
            console.log(titleBar);
            self.cancel_Dialog();
        }

    class TitleBar {
        nameTitleBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedTitleAtr: number;
        imageId:string;

        constructor(nameTitleBar: string, letterColor: string, backgroundColor: string, selectedTitleAtr: number,imageId:string) {
            this.nameTitleBar = nameTitleBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.selectedTitleAtr = selectedTitleAtr;
            this.imageId = imageId;
        }
    }
    class TitleMenu {
        titleCode: string;
        titleName: string;
        constructor(titleCode: string, titleName: string) {
            var self = this;
            self.titleCode = titleCode;
            self.titleName = titleName;
        }
    }
}