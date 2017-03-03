module nts.uk.pr.view.qmm008.c {

    export module viewmodel {
        import InsuranceOfficeItemDto = qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        import aservice = nts.uk.pr.view.qmm008.a.service;
        import OfficeItemDto = qmm008.c.service.model.finder.OfficeItemDto;

        export class ScreenModel {
            // for left list
            officeItems: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;

            //for tab panel
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<optionsModel>;
            selectedValue: KnockoutObservable<optionsModel>;
            //model
            officeModel: KnockoutObservable<SocialInsuranceOfficeModel>;
            //text area
            textArea: any;
            textInputOption: KnockoutObservable<any>;
            selectedOfficeCode: KnockoutObservable<string>;
            enabled: KnockoutObservable<boolean>;
            deleteButtonControll: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.enabled = ko.observable(true);
                self.deleteButtonControll = ko.observable(true);

                self.officeItems = ko.observableArray([]);
                self.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);

                self.listOptions = ko.observableArray([new optionsModel(1, "基本情報"), new optionsModel(2, "保険料マスタの情報")]);
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));


                //panel
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '保険料マスタの情報', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.officeModel = ko.observable(new SocialInsuranceOfficeModel('', '', '', '', '','', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''));
                //text area
                self.textArea = ko.observable("");
                //text input options
                self.textInputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    width: "100",
                    textalign: "center"
                }));
                self.selectedOfficeCode = ko.observable('');
                self.selectedOfficeCode.subscribe(function(selectedOfficeCode: string) {
                    if (selectedOfficeCode != null && selectedOfficeCode != undefined && selectedOfficeCode != "") {
                        self.enabled(false);
                        self.deleteButtonControll(true);
                        //                        alert(officeSelectedCode);
                        $.when(self.load(selectedOfficeCode)).done(function() {
                            //TODO load data success
                        }).fail(function(res) {
                            //TODO when load data error
                        });
                    }
                });
            }
            // start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                //first load
                self.loadAllInsuranceOfficeData().done(function() {
                    // Load first result.
                    if (self.officeItems().length > 0) {
                        //load first office
                        self.selectedOfficeCode(self.officeItems()[0].code);
                    } else {
                        //register new office mode
                        self.addNew();
                    }
                    // Resolve
                    dfd.resolve(null);
                });
                // Return.
                return dfd.promise();
            }
            //
            public loadAllInsuranceOfficeData(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                // find all insurance office 
                aservice.findInsuranceOffice('').done(function(data: Array<InsuranceOfficeItemDto>) {
                    //check list office is empty
                    if (data != null) {
                        self.officeItems([]);
                        // Set data get from service to list.
                        data.forEach((item, index) => {
                            self.officeItems.push(new ItemModel(item.code, item.name));
                        });
                        dfd.resolve(data);
                    }
                    //list is empty
                    else {
                        dfd.resolve(null);
                    }
                });
                // Return.
                return dfd.promise();
            }
            public load(officeCode: string): JQueryPromise<any> {
                if (officeCode != null && officeCode != '') {
                    var self = this;
                    service.getOfficeItemDetail(officeCode).done(function(data: OfficeItemDto) {
                        //Convert data get from service to screen
                        self.officeModel().officeCode(data.code);
                        self.officeModel().officeName(data.name);
                        self.officeModel().shortName(data.shortName);
                        self.officeModel().PicName(data.picName);
                        self.officeModel().PicPosition(data.picPosition);
                        self.officeModel().potalCode(data.potalCode);
                        self.officeModel().prefecture(data.prefecture);
                        self.officeModel().address1st(data.address1st);
                        self.officeModel().kanaAddress1st(data.kanaAddress1st);
                        self.officeModel().address2nd(data.address2nd);
                        self.officeModel().kanaAddress2nd(data.kanaAddress2nd);
                        self.officeModel().phoneNumber(data.phoneNumber);
                        self.officeModel().healthInsuOfficeRefCode1st(data.healthInsuOfficeRefCode1st);
                        self.officeModel().healthInsuOfficeRefCode2nd(data.healthInsuOfficeRefCode2nd);
                        self.officeModel().pensionOfficeRefCode1st(data.pensionOfficeRefCode1st);
                        self.officeModel().pensionOfficeRefCode2nd(data.pensionOfficeRefCode2nd);
                        self.officeModel().welfarePensionFundCode(data.welfarePensionFundCode);
                        self.officeModel().officePensionFundCode(data.officePensionFundCode);
                        self.officeModel().healthInsuCityCode(data.healthInsuCityCode);
                        self.officeModel().healthInsuOfficeSign(data.healthInsuOfficeSign);
                        self.officeModel().pensionCityCode(data.pensionCityCode);
                        self.officeModel().pensionOfficeSign(data.pensionOfficeSign);
                        self.officeModel().healthInsuOfficeCode(data.healthInsuOfficeCode);
                        self.officeModel().healthInsuAssoCode(data.healthInsuAssoCode);
                        self.officeModel().memo(data.memo);
                    });
                }
                return;
            }
            public convertDatatoList(data: Array<InsuranceOfficeItemDto>): Array<ItemModel> {
                var OfficeItemList: Array<ItemModel> = [];
                // 
                data.forEach((item, index) => {
                    OfficeItemList.push(new ItemModel(item.code, item.name));
                });
                return OfficeItemList;
            }

            //save (mode: update or create new)
            public save() {
                var self = this;
                //if update office
                if (!self.enabled())
                    self.updateOffice();
                //if register new office
                else {
                    self.registerOffice();
                    //reload list office
                    self.loadAllInsuranceOfficeData().done(function(){
                    //focus add new item
                           self.selectedOfficeCode(self.officeModel().officeCode()); 
                    });
//                    //set add item into list view
//                    if ((self.officeModel().officeCode() != '') && (self.officeModel().officeName() != ''))
//                        self.officeItems.push(new ItemModel(self.officeModel().officeCode(), self.officeModel().officeName()));
                }
            }

            //update office
            private updateOffice() {
                var self = this;
                service.update(self.collectData()).done(function() {
                    //TODO when update done   
                }).fail(function() {
                    //TODO if update fail    
                });
            }

            //create new Office
            private registerOffice() {
                var self = this;
                service.register(self.collectData()).done(function() {
                    //TODO when register done   
                }).fail(function() {
                    //TODO if register fail    
                });
            }

            //remove office  by office Code
            private remove() {
                var self = this;
                if (self.selectedOfficeCode() != '') {
                    service.remove(self.selectedOfficeCode()).done(function() {
                        //if remove success
                            
                    }).fail(function() {
                        //TODO if remove fail    
                    });
                    
                    //reload list
                    self.loadAllInsuranceOfficeData().done(function() {
                        // if empty list -> add new mode
                        if (self.officeItems().length == 0) {
                            self.addNew();
                        } else {
                            self.selectedOfficeCode(self.officeItems()[0].code);
                        }
                    });
                }
            }

            //collect all data
            public collectData() {
                var self = this;
                var a = new service.model.finder.OfficeItemDto(
                    "company code",
                    self.officeModel().officeCode(),
                    self.officeModel().officeName(),
                    self.officeModel().shortName(),
                    self.officeModel().PicName(),
                    self.officeModel().PicPosition(),
                    self.officeModel().potalCode(),
                    self.officeModel().prefecture(),
                    self.officeModel().address1st(),
                    self.officeModel().address2nd(),
                    self.officeModel().kanaAddress1st(),
                    self.officeModel().kanaAddress2nd(),
                    self.officeModel().phoneNumber(),
                    self.officeModel().healthInsuOfficeRefCode1st(),
                    self.officeModel().healthInsuOfficeRefCode2nd(),
                    self.officeModel().pensionOfficeRefCode1st(),
                    self.officeModel().pensionOfficeRefCode2nd(),
                    self.officeModel().welfarePensionFundCode(),
                    self.officeModel().officePensionFundCode(),
                    self.officeModel().healthInsuCityCode(),
                    self.officeModel().healthInsuOfficeSign(),
                    self.officeModel().pensionCityCode(),
                    self.officeModel().pensionOfficeSign(),
                    self.officeModel().healthInsuOfficeCode(),
                    self.officeModel().healthInsuAssoCode(),
                    self.officeModel().memo()
                );
                return a;
            }

            //reset all field when click add new office button
            public addNew() {
                var self = this;
                //reset all input fields to blank
                self.officeModel().officeCode('');
                self.officeModel().officeName('');
                self.officeModel().shortName('');
                self.officeModel().PicName('');
                self.officeModel().PicPosition('');
                self.officeModel().potalCode('');
                self.officeModel().prefecture('');
                self.officeModel().address1st('');
                self.officeModel().kanaAddress1st('');
                self.officeModel().address2nd('');
                self.officeModel().kanaAddress2nd('');
                self.officeModel().phoneNumber('');
                self.officeModel().healthInsuOfficeRefCode1st('');
                self.officeModel().healthInsuOfficeRefCode2nd('');
                self.officeModel().pensionOfficeRefCode1st('');
                self.officeModel().pensionOfficeRefCode2nd('');
                self.officeModel().welfarePensionFundCode('');
                self.officeModel().officePensionFundCode('');
                self.officeModel().healthInsuCityCode('');
                self.officeModel().healthInsuOfficeSign('');
                self.officeModel().pensionCityCode('');
                self.officeModel().pensionOfficeSign('');
                self.officeModel().healthInsuOfficeCode('');
                self.officeModel().healthInsuAssoCode('');
                self.officeModel().memo('');
                //set enabled code input
                self.enabled(true);
                //disable remove
                self.deleteButtonControll(false);
                //reset selected officeCode
                self.selectedOfficeCode('');
            }

            closeDialog() {
                // Set child value
                nts.uk.ui.windows.setShared("insuranceOfficeChildValue", "return value", this.isTransistReturnData());
                nts.uk.ui.windows.close();
            }
        }

        //Models
        export class SocialInsuranceOfficeModel {
            officeCode: KnockoutObservable<string>;
            officeName: KnockoutObservable<string>;
            shortName: KnockoutObservable<string>;
            PicName: KnockoutObservable<string>;
            PicPosition: KnockoutObservable<string>;

            prefecture: KnockoutObservable<string>;
            potalCode: KnockoutObservable<string>;
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
                potalCode: string, prefecture: string, address1st: string, kanaAddress1st: string, address2nd: string, kanaAddress2nd: string, phoneNumber: string,
                //insurance info input 
                healthInsuOfficeRefCode1st: string, healthInsuOfficeRefCode2nd: string, pensionOfficeRefCode1st: string, pensionOfficeRefCode2nd: string, welfarePensionFundCode: string, officePensionFundCode: string,
                healthInsuCityCode: string, healthInsuOfficeSign: string, pensionCityCode: string, pensionOfficeSign: string, healthInsuOfficeCode: string, healthInsuAssoCode: string,
                memo: string
            ) {
                //basic info input
                this.officeCode = ko.observable(officeCode);
                this.officeName = ko.observable(officeName);
                this.shortName = ko.observable(shortName);
                this.PicName = ko.observable(PicName);
                this.PicPosition = ko.observable(PicPosition);

                this.potalCode = ko.observable(potalCode);
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