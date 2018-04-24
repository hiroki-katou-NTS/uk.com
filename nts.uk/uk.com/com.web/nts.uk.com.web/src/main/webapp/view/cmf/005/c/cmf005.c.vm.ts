module nts.uk.com.view.cmf005.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import gridColumn = nts.uk.ui.NtsGridListColumn;

    export class ScreenModel {
        //Dropdownlist contain System data
        systemList: KnockoutObservableArray<SystemModel>;
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<number>;
        
        //C5
        listCategorys : KnockoutObservableArray<CategoryModel>;
        swapColumns: KnockoutObservableArray<gridColumn>;
        currentListCategorys : KnockoutObservableArray<any>;
               
        /* screen */
        constructor() {
            //Set System data to dropdownlist
            self.systemList = ko.observableArray([
                new SystemModel(0, nts.uk.resource.getText("Enum_SystemType_PERSON_SYSTEM")),
                new SystemModel(1, nts.uk.resource.getText("Enum_SystemType_ATTENDANCE_SYSTEM")),
                new SystemModel(2, nts.uk.resource.getText("Enum_SystemType_PAYROLL_SYSTEM")),
                new SystemModel(3, nts.uk.resource.getText("Enum_SystemType_OFFICE_HELPER"))
            ]);
            
            self.systemName = ko.observable('');
            self.currentSystemCode = ko.observable(0);
            self.selectedSystemCode = ko.observable(0);
            
            //C5
            this.listCategorys = ko.observableArray([]);
           
            // set tam sau lay tu tang apdapter
           var array = [];
           for (var i = 1; i < 10; i++) {
               array.push(new CategoryModel('0000'+i, 'cccccc'+i));
           }
           this.listCategorys(array);

           this.swapColumns = ko.observableArray([
               { headerText: getText('CMF005_19'), key: 'categoryId', width: 100 },
               { headerText: getText('CMF005_20'), key: 'categoryName', width: 150 }
           ]);

           this.currentListCategorys = ko.observableArray([]);
        }
        
         start(): JQueryPromise<any> {
            block.invisible();
            var self = this;
//            var dfd = $.Deferred();
//            
//             self.loadCategoryList().done(function() {

//                if (self.loadCategoryList().length > 0) {
//                    let selectedId = self.currentRoleId() !== '' ? self.currentRoleId() : self.personRoleList()[0].roleId;
//
//                    self.currentRoleId(selectedId);
//
//
//                } else {
//
//                    dialog({ messageId: "Msg_364" }).then(function() {
//                        nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
//                    });
//
//                }

//                dfd.resolve();

//            });

            return dfd.promise();
        }
        
        //Load category
        loadCategoryList(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            
            return dfd.resolve();
        }

        
        // Return code / name of selected line
        selectConvertCode() {
//            var self = this;
//            let codeConvert = new model.AcceptanceCodeConvert("", "", 0);
//            if (!_.isEqual(self.selectedConvertCode(), "")){
//                codeConvert = _.find(ko.toJS(self.listConvertCode), (x: model.AcceptanceCodeConvert) => x.dispConvertCode == self.selectedConvertCode());
//            }
//            // 選択された行のコード/名称を返す
//            setShared("cmf005kOutput", { selectedConvertCodeShared: codeConvert});
            nts.uk.ui.windows.close();
        }
        //Cancel and exit
        cancelSelectConvertCode() {
            nts.uk.ui.windows.close();
        }
    }

    class SystemModel {
        systemCode: number;
        systemName: string;
        
        constructor(systemCode: number, systemName: string) {
            this.systemCode = systemCode;
            this.systemName = systemName;
        }
    }
    
     class CategoryModel {
       categoryId: string;
       categoryName: string;
       deletable: boolean;
       constructor(categoryId: number, categoryName: string) {
           this.categoryId = categoryId;
           this.categoryName = categoryName;
           this.deletable = categoryId % 2 === 0;
       }
   }
}