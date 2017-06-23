module nts.uk.sys.view.ccg013.c.viewmodel {
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
        //Text edittor
        nameMenuBar: KnockoutObservable<string>;
        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        //Radio button
        itemRadioAtcClass: KnockoutObservableArray<any>;
        selectedRadioAtcClass: KnockoutObservable<number>;
        //GridList
        listTitleMenu: KnockoutObservableArray<TitleMenu>;
        columns: KnockoutObservableArray<any>;
        currentListTitleMenu: KnockoutObservableArray<any>;
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
            self.nameMenuBar = ko.observable("");
            //Radio button
            self.itemRadioAtcClass = ko.observableArray([]);
            self.selectedRadioAtcClass = ko.observable(1);
            //color picker
            self.letterColor = ko.observable('');
            self.backgroundColor = ko.observable('');
            //GridList
            self.listTitleMenu = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', key: 'code', width: 100 },
                { headerText: '名称', prop: 'displayName', key: 'displayName', width: 230 }
            ]);
            self.selectCodeTitleMenu = ko.observable('');
            self.currentListTitleMenu = ko.observableArray([]);
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
//            var data = windows.getShared("CCG013A_StandardMeNu");
//            if (data) {
//                self.nameMenuBar(data.nameMenuBar);
//                self.letterColor(data.pickerLetter);
//                self.backgroundColor(data.pickerBackground);
//                self.selectedRadioAtcClass(data.radioActlass);
//            }

            /** Get TitleBar*/
            service.getTitleMenu().done(function(titleMenu: any) {
                let lstSource : Array<TitleMenu> = [];
                if(titleMenu.length>0){
                    _.forEach(titleMenu, function(item){
                            lstSource.push(new TitleMenu(item.titleMenuCD,item.name));
                    })   
                }
                self.listTitleMenu(lstSource);
//                let listTitleMenu: Array<TitleMenu> = _.orderBy((titleMenu.listStandardMenu, ["code"], ["asc"]));
//                self.listStandardMenu(editMenuBar.listStandardMenu);
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
                stereoType: "any",
                onSuccess: function() {alert('upload Fail'); },
                onFail: function() { }
            }
            $("#file_upload").ntsFileUpload({ stereoType: "any" }).done(function(res) {
                self.fileID(res[0].id);
                self.filename('');
                self.imageName(res[0].originalName);
                self.imageSize(res[0].originalSize);
                self.isDelete(true);
                //console.log(res);
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
            var menuBar = new MenuBar(self.nameMenuBar(), self.letterColor(), self.backgroundColor(), self.selectedRadioAtcClass());
            windows.setShared("CCG013C_MenuBar", menuBar);
            self.cancel_Dialog();
        }

        deleteButtonClick() {

        }

    class MenuBar {
        nameMenuBar: string;
        letterColor: string;
        backgroundColor: string;
        selectedRadioAtcClass: number;

        constructor(nameMenuBar: string, letterColor: string, backgroundColor: string, selectedRadioAtcClass: number) {
            this.nameMenuBar = nameMenuBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.selectedRadioAtcClass = selectedRadioAtcClass;
        }
    }
    class TitleMenu{
        titleCode:string;
        titleName:string;
        constructor(titleCode:string,titleName:string){
            var self = this;
            self.titleCode = titleCode;
            self.titleName = titleName;    
        }    
    }
}