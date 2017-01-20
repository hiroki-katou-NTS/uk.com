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
            //model
            officeModel : KnockoutObservable<SocialInsuranceOfficeModel>;          
            //text area
            textArea: any;
            textInputOption: KnockoutObservable<any>;
            constructor() {
                var self = this;

                this.currentCode = ko.observable();
                this.items = ko.observableArray([]);
                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, 'name' + i));
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
                self.officeModel = ko.observable(new SocialInsuranceOfficeModel('','','','','',1,'','','','','','','','','','','','','','','','','','',''));
                //text area
                self.textArea = ko.observable("");
                //text input options
                this.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100",
                    textalign: "center"
                }));
            }

            start() {
                
            }

            CloseModalSubWindow() {
                // Set child value
                nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                nts.uk.ui.windows.close();
            }
        }
        export class SocialInsuranceOfficeModel{
            officeCode: KnockoutObservable<string>;
            officeName: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            PicName: KnockoutObservable<string>;
            PicPosition: KnockoutObservable<string>;

            portCode: KnockoutObservable<number>;
            prefecture: KnockoutObservable<string>;
            address1st: KnockoutObservable<string>;
            kanaAddress1st: KnockoutObservable<string>;
            address2nd: KnockoutObservable<string>;
            kanaAddress2nd: KnockoutObservable<string>;
            phoneNumber: KnockoutObservable<string>;

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
            
            constructor(officeCode: string, officeName: string, shortName: string, PicName: string, PicPosition: string,
                portCode: number,prefecture: string,address1st: string,kanaAddress1st: string,address2nd: string,kanaAddress2nd: string,phoneNumber: string,
                //insurance info input 
                healthInsuOfficeRefCode1st: string,healthInsuOfficeRefCode2nd: string,pensionOfficeRefCode1st: string,pensionOfficeRefCode2nd: string,welfarePensionFundCode: string,officePensionFundCode: string,
                healthInsuCityCode: string,healthInsuOfficeSign: string,pensionCityCode: string,pensionOfficeSign: string,healthInsuOfficeCode: string,healthInsuAssoCode: string,
                memo: string
            )
            {
                  //basic info input
                this.officeCode = ko.observable(officeCode);
                this.officeName = ko.observable(officeName);
                this.shortName = ko.observable(shortName);
                this.PicName = ko.observable(PicName);
                this.PicPosition = ko.observable(PicPosition);

                this.portCode = ko.observable(portCode);
                this.prefecture = ko.observable(prefecture);
                this.address1st = ko.observable(address1st);
                this.kanaAddress1st = ko.observable(kanaAddress1st);
                this.address2nd = ko.observable(address2nd);
                this.kanaAddress2nd = ko.observable(kanaAddress2nd);
                this.phoneNumber = ko.observable(phoneNumber);

                //insurance info input 
                this.healthInsuOfficeRefCode1st = ko.observable(healthInsuOfficeRefCode1st);
                this.healthInsuOfficeRefCode2nd = ko.observable(healthInsuOfficeRefCode2nd);
                this.pensionOfficeRefCode1st = ko.observable(pensionOfficeRefCode1st);
                this.pensionOfficeRefCode2nd = ko.observable(pensionOfficeRefCode2nd);
                this.welfarePensionFundCode = ko.observable(welfarePensionFundCode);
                this.officePensionFundCode = ko.observable(officePensionFundCode);

                this.healthInsuCityCode = ko.observable(healthInsuCityCode);
                this.healthInsuOfficeSign = ko.observable(healthInsuOfficeSign);
                this.pensionCityCode = ko.observable(pensionCityCode);
                this.pensionOfficeSign = ko.observable(pensionOfficeSign);
                this.healthInsuOfficeCode = ko.observable(healthInsuOfficeCode);
                this.healthInsuAssoCode = ko.observable(healthInsuAssoCode);
                this.memo = ko.observable(memo);  
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
    }
}