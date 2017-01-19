module nts.uk.pr.view.qmm008.c {
    export module viewmodel {
        export class ScreenModel {
            // for left list
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            index: number;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
            headers: any;
            //for tab panel
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            modalValue: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            //basic info input
            officeCode: KnockoutObservable<string>;
            officeName: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            PicName: KnockoutObservable<string>;
            PicPosition : KnockoutObservable<string>;
            
            portCode: KnockoutObservable<number>;
            prefecture: KnockoutObservable<string>;
            address1st :KnockoutObservable<string>;
            kanaAddress1st:KnockoutObservable<string>;
            address2nd :KnockoutObservable<string>;
            kanaAddress2nd:KnockoutObservable<string>;
            phoneNumber:KnockoutObservable<string>;
            
            //insurance info input 
            healthInsuOfficeRefCode1st: KnockoutObservable<string>;
            healthInsuOfficeRefCode2nd: KnockoutObservable<string>;
            pensionOfficeRefCode1st: KnockoutObservable<string>;
            pensionOfficeRefCode2nd: KnockoutObservable<string>;
            welfarePensionFundCode: KnockoutObservable<string>;
            officePensionFundCode: KnockoutObservable<string>;

            healthInsuCityCode: KnockoutObservable<string>;
            healthInsuOfficeSign: KnockoutObservable<string>;
            pensionCityCode: KnockoutObservable<string>;
            pensionOfficeSign: KnockoutObservable<string>;
            healthInsuOfficeCode: KnockoutObservable<string>;
            healthInsuAssoCode: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            //text area
            textArea: any;
            textInputOption: KnockoutObservable<any>;          
            constructor() {
                var self = this;
                
                this.currentCode = ko.observable();
                this.items = ko.observableArray([]);
                for(let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, 'name'+i));
                }
                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);
                
                self.listOptions = ko.observableArray([new optionsModel(1, "基本情報"), new optionsModel(2, "保険料マスタの情報")]);
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.modalValue = ko.observable("Goodbye world!");
                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                
                //
                
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([]);
                self.index = 0;
                self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                //panel
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                //basic info input
                self.officeCode = ko.observable('');
                self.officeName = ko.observable('');
                self.shortName = ko.observable('');
                self.PicName = ko.observable('');
                self.PicPosition = ko.observable('');

                self.portCode = ko.observable(1);
                self.prefecture = ko.observable('');
                self.address1st = ko.observable('');
                self.kanaAddress1st = ko.observable('');
                self.address2nd = ko.observable('');
                self.kanaAddress2nd = ko.observable('');
                self.phoneNumber = ko.observable('');

                //insurance info input 
                self.healthInsuOfficeRefCode1st = ko.observable('');
                self.healthInsuOfficeRefCode2nd = ko.observable('');
                self.pensionOfficeRefCode1st = ko.observable('');
                self.pensionOfficeRefCode2nd = ko.observable('');
                self.welfarePensionFundCode = ko.observable('');
                self.officePensionFundCode = ko.observable('');

                self.healthInsuCityCode = ko.observable('');
                self.healthInsuOfficeSign = ko.observable('');
                self.pensionCityCode = ko.observable('');
                self.pensionOfficeSign = ko.observable('');
                self.healthInsuOfficeCode = ko.observable('');
                self.healthInsuAssoCode = ko.observable('');
                self.memo = ko.observable('');
                //text area
                self.textArea = ko.observable("");
                //text input options
                this.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100",
                    textalign: "center"
                }));
            }

            CloseModalSubWindow() {
                // Set child value
                nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                nts.uk.ui.windows.close();
            }
        }
        export class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;       
            }
        }
        
        export class optionsModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }

        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
            }
        }
    }
}