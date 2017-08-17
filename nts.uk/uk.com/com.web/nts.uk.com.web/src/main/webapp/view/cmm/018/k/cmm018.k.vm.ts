module cmm018.k.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    export class ScreenModel{
        appType: KnockoutObservable<String> = ko.observable('');;
        standardDate: KnockoutObservable<any> = ko.observable(new Date());
        typeSetting: KnockoutObservableArray<typeSetting> = ko.observableArray([]);
        selectTypeSet: KnockoutObservable<number> = ko.observable(0);
        currentCalendarWorkPlace: KnockoutObservableArray<SimpleObject> = ko.observableArray([]);
        //職場リスト
        treeGrid: ITreeGrid = {
                treeType: 1,
                selectType: 4,
                isDialog: false,
                isMultiSelect: true,
                isShowAlreadySet: false,
                isShowSelectButton: true,
                baseDate: ko.observable(this.standardDate()),
                selectedWorkplaceId: ko.observableArray(_.map(this.currentCalendarWorkPlace(), o => o.key)),
                alreadySettingList: ko.observableArray([])
        };
        constructor(){
            var self = this;
            //設定対象
            let data: any = getShared('CMM008K_PARAM');
            if(data !== undefined){
                this.appType(data.appType);
                this.standardDate(data.standardDate);
            }
            //承認者指定種類
            self.typeSetting.push(new typeSetting(0, resource.getText('CMM018_56')));
            self.typeSetting.push(new typeSetting(1, resource.getText('CMM018_57')));
            //職場リスト
            $('#tree-grid').ntsTreeComponent(self.treeGrid);
            
        }// end constructor
        
        // start function
        start(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred<any>();
            
            
            return dfd.promise();
        }//end start
        
        
    }//end ScreenModel
    interface ITreeGrid {
            treeType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowSelectButton: boolean;
            baseDate: KnockoutObservable<any>;
            selectedWorkplaceId: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
        }
    class SimpleObject {
            key: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            constructor(key: string, name: string){
                this.key = ko.observable(key);
                this.name = ko.observable(name);
            }      
        }
    export class typeSetting{
        typeCode: number;
        typeName: String;
        constructor(typeCode: number, typeName: String){
            this.typeCode = typeCode;
            this.typeName = typeName;    
        }
    }
}