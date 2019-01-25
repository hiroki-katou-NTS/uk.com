module cps003.b.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import format = nts.uk.text.format;
    let __viewContext: any = window['__viewContext'] || {};

    export class ViewModel {
        currentMode: KnockoutObservable<ModelData> = ko.observable(null);
        currentFile: KnockoutObservable<FileData> = ko.observable(new FileData());
        constructor() {
            let self = this;
            self.pushData();
        }

        pushData() {
            let self = this,
                params = getShared('CPS003B_VALUE');
            self.currentMode(new ModelData(params));
        }
        
        decide(){
            let self = this,
                params = {
                    fileId: self.currentFile().fileId(),
                    fileName: self.currentFile().filename(),
                    categoryId: self.currentMode().categoryId,
                    modeUpdate: self.currentMode().mode,
                    columnChange: self.currentMode().columnChange    
                };
            
            if(_.isEmpty(self.currentFile().filename())){
                alertError({ messageId: "Msg_722" });   
            }
            service.checkColums(params).done(data =>{
//                if(data == undefined){
//                     alertError({ messageId: "Msg_723",  messageParams: [self.currentMode().categoryName] });   
//                     return;
//                }
                setShared('CPS003C_VALUE', data);
                close();
            }).fail((res) =>{
                alertError({ messageId: res.messageId });
            })
        }

        close() {
            close();
        }
    }

    
    interface IModelDto {
        categoryId: string;
        categoryName: string;
        systemDate: string;
        mode: number;
        updateModeEnable: boolean;
        columnChange: Array<string>;
        sids: Array<string>;
    }
    
    class ModelData{
        categoryId: string;
        categoryName: string;
        systemDate: string;
        mode: KnockoutObservable<number> = ko.observable(1);
        updateModeEnable: KnockoutObservable<boolean> = ko.observable(true);
        columnChange: Array<any> = [];
        sids: Array<string>;
        mode : KnockoutObservable<number> = ko.observable(1);
        roundingRules : Array<any> = [
            { id: 1, name: text("CPS003_19") },
            { id: 2, name: text("CPS003_20") },
        ];
        constructor(data: IModelDto) {
            let self = this;
            self.categoryId = data.categoryId;
            self.categoryName = data.categoryName;
            self.systemDate = data.systemDate;
            self.mode(data.mode);
            self.updateModeEnable(data.updateModeEnable);
            self.columnChange = data.columnChange;
            self.sids = data.systemDate;
        }
    }
    
    interface ICheckFileParams{
        fileId: string;
        fileName: string;
        categoryId: string;
        columnChange:  Array<IDataHead>;    
    }
    
        /* Dữ liệu  */
    export interface IDataHead {
        itemId: string;
        itemCode: string;
        itemName: string;
        itemOrder: number;
        itemParentCode: string;
        itemTypeState: ISingleItem;
        required: boolean;
        resourceId: string;
    }
    
    class FileData{
        stereoType: KnockoutObservable<string>;
        fileId: KnockoutObservable<string>= ko.observable("");
        filename: KnockoutObservable<string> = ko.observable("");
        fileInfo: KnockoutObservable<any> = ko.observable(null);
        textId: KnockoutObservable<string> = ko.observable("CPS003_41");
        accept: KnockoutObservableArray<string> = ko.observableArray(['.xlsx']);
        asLink: KnockoutObservable<boolean> = ko.observable(true);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        immediate: KnockoutObservable<boolean>;
        stereoType: KnockoutObservable<string> = ko.observable("CPS003_41");
        imageSize: KnockoutObservable<string> = ko.observable("");
        onchange: (filename) => void;
        onfilenameclick: (filename) => void;
        contructor(){
            let self = this;
            self.onchange = (filename) => {
                console.log(filename);
            };
            self.onfilenameclick = (filename) => {
                alert(filename);
            };
        }
        
        upload() {
            let self = this,
                option = {
                    stereoType: "excelFile",
                    onSuccess: function() { alert('upload Success'); },
                    onFail: function() { alert('upload Fails') }

                };
            
            $("#file-upload").ntsFileUpload({ stereoType: "excelFile" }).done(function(res) {
                self.fileId(res[0].id);
                self.imageSize("(" +format(text('CCG013_99'), res[0].originalSize)+")");
            }).fail(function(err) {
                alertError({ messageId: "Msg_1466" });
            });
        }
    
    }
}