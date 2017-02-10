module cmm001.a {
    export class ScreenModel {
        // data of items list - tree grid\
        items: KnockoutObservableArray<Company>;
        item1s: KnockoutObservableArray<Company>;
        columns2: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCompanyDto: KnockoutObservable<cmm001.a.service.model.CompanyDto>;
        currentCodeList: KnockoutObservableArray<any>;
        currentNode: KnockoutObservable<Company>;
        editMode: boolean = true;
        //tabpanel
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        //switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        roundingRule1s: KnockoutObservableArray<any>;
        selectedRuleCode1: any;
        //combox
        itemList: KnockoutObservableArray<Company>;
        itemName: KnockoutObservable<string>;
        currentCodeCombox: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //check
        checked1: KnockoutObservable<boolean>;
        checked2: KnockoutObservable<boolean>;
        //search box
        textSearch: string = "";
        //company from database
        companys: KnockoutObservableArray<cmm001.a.service.model.CompanyDto>;
        test: KnockoutObservableArray<cmm001.a.service.model.CompanyDto>;
        constructor() {
            let self = this;
            let node: Company;
            self.init();
            self.currentCode.subscribe(function(newValue) {
                let dfd = $.Deferred<any>();
                cmm001.a.service.getAllCompanys().done(function(companies) {
                    if (companies.length > 0) {
                        self.companys(companies);
                        console.log(self.companys());
                        if (self.editMode) {
                            self.getDetailCompany(self.companys(), newValue);
                        }
                        else {
                            self.editMode = true;
                        }

                    }
                    dfd.resolve();
                });
            });
            self.checked2.subscribe(function(newValue) {
                // chu y khi code chinh phai lay lai datasource khong se bi thay doi source
                self.item1s = ko.observableArray([
                    new Company('01', '日通システム株式会社', ''),
                    new Company('02', '有限会社日通ベトナム', ''),
                    new Company('03', 'UKシステム株式会社', ''),
                    new Company('04', '○○株式会社', '<i class="icon icon-close"></i>')
                ]);
                if (!newValue) {

                    for (let i = 0; i < _.size(self.items()); i++) {
                        if (self.items()[i].description === '<i class="icon icon-close"></i>') {
                            node = self.items()[i];
                            self.items()[i] = new Company('', '', '');
                            self.items()[i] = new Company(node.code, node.name, '');
                            console.log(self.items()[i]);
                            self.currentCode(self.items()[i].code);
                        }
                    }


                } else {
                    console.log(self.item1s());
                    self.items([]);
                    self.items(self.item1s());
                    self.currentCode(self.items()[0].code);
                }

            });
            self.checked1.subscribe(function(newValue) {
                        if (newValue == true) {
                            console.log(newValue);
                            self.columns2([]);
                            console.log(self.columns2());
                            self.columns2 = ko.observableArray([
                                { headerText: '会社コード', prop: 'code', width: 80 },
                                { headerText: '名称', prop: 'name', width: 200 },
                                { headerText: '廃止', prop: 'description', width: 50, hidden: true }
                            ]);
                            
                        }


                //                self.item1s = ko.observableArray([
                //                    new Company('01', '日通システム株式会社', ''),
                //                    new Company('02', '有限会社日通ベトナム', ''),
                //                    new Company('03', 'UKシステム株式会社', ''),
                //                    new Company('04', '○○株式会社', '<i class="icon icon-close"></i>')
                //                ]);
                //                if (newValue) {
                //                    _.remove(self.item1s(), function(value) {
                //                        return value.description === '<i class="icon icon-close"></i>';
                //                    });
                //                    self.items(self.item1s());
                //                    console.log(self.items());
                //                } else {
                //                    console.log(self.item1s());
                //                    self.items(self.item1s());
                //                    console.log(self.items());
                //
                //                }
            });

        }

        getObj(items: Array<Company>, newValue: string): void {
            let self = this;
            let node: Company;
            _.find(items, function(obj: Company) {
                if (!node) {
                    if (obj.code == newValue) {
                        node = obj;
                        // self.currentNode(node);

                    }
                }
            });

        }

        getDetailCompany(items: any, newValue: string): any {
            let self = this;
            let node: any;
            _.find(items, function(obj: cmm001.a.service.model.CompanyDto) {
                if (obj.companyCode.toString() == newValue) {
                    node = obj;
                    self.currentCompanyDto(node);
                }
            });
            return node;
        }
        resetData(): void {
            let self = this;
            self.editMode = false;
            self.currentCode(" ");
            self.currentNode(new Company("", "", ""));
            //self.currentCompanyDto({});

        }
        init(): void {
            let self = this;
            self.checked1 = ko.observable(false);
            self.items = ko.observableArray([]);

            self.columns2 = ko.observableArray([
                { headerText: '会社コード', prop: 'code', width: 80 },
                { headerText: '名称', prop: 'name', width: 200 },
                { headerText: '廃止', prop: 'description', width: 50, hidden: false}
            ]);

            self.currentCode = ko.observable("0001");
            self.currentCodeList = ko.observableArray(null);
            //tabpanel
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            //SWITCH
            self.roundingRules = ko.observableArray([
                { code: '1', name: '区別する' },
                { code: '2', name: '区別しない' }
            ]);
            self.selectedRuleCode = ko.observable(2);

            self.roundingRule1s = ko.observableArray([
                { code: '1', name: '利用する' },
                { code: '2', name: '利用しない' }
            ]);
            self.selectedRuleCode1 = ko.observable(1);
            //COMBOX 
            self.itemList = ko.observableArray([
                new Company('1', '1月', ''),
                new Company('2', '2月', ''),
                new Company('3', '3月', ''),
                new Company('4', '4月', ''),
                new Company('5', '5月', ''),
                new Company('6', '6月', ''),
                new Company('7', '7月', ''),
                new Company('8', '8月', ''),
                new Company('9', '9月', ''),
                new Company('10', '10月', '')
            ]);
            self.companys = ko.observableArray([]);
            self.itemName = ko.observable('');
            self.currentCodeCombox = ko.observable(4);
            self.selectedCode = ko.observable('4');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //check

            self.checked2 = ko.observable(true);
            self.currentCompanyDto = ko.observable(new cmm001.a.service.model.CompanyDto());
        }
        // register company information
        //BTN-002 
        ClickRegister(): any {
            let self = this;
            //            let companyCode = $('#A_INP_002').val();
            //            let companyAbb = $("#A_INP_003").val();
            //            let companyHira = $("#A_INP_004").val();
            //       let companyHira1 = $("#A_INP_005").val();


        }
        //BTN-003 -Setting cac thong so ban dau
        ClickSetting(): void {
            alert("Settting");

        }
        //BTN-004- Master correction log 
        ClickLog(): void {
            alert("Log");

        }
        //SEL-Btn-001
        ClickSel001(): void {
            alert("Sel001");
        }
        //SEL-Btn-001
        ClickSel002(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            let item: any;
            item = ko.observable(new Company('01', '日通システム株式会社', ''));

            return dfd.promise();

        }
        //SEL-Btn-001
        Browse(): void {
            alert("Browse!");
        }
        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            cmm001.a.service.getAllCompanys().done(function(companies) {
                if (companies.length > 0) {
                    self.companys(companies);
                    self.buildGridDataSource(self.companys());
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
        buildGridDataSource(items: any): any {
            let self = this;
            _.forEach(items, function(obj) {
                self.items().push(new Company(obj.companyCode.toString(), obj.companyName, ""));
            });
        }

    }
    export class Company {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
};