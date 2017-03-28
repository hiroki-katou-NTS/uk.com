module cmm001.a {
    export class ViewModel {

        gridColumns: KnockoutObservableArray<any>;
        currentCompany: KnockoutObservable<CompanyModel>;
        sel001Data: KnockoutObservableArray<CompanyModel>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;
        mode: KnockoutObservable<boolean> = ko.observable(null);
        editmode: KnockoutObservable<boolean> = ko.observable(null);

        constructor() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);

            self.checked = ko.observable(true);
            self.selectedTab = ko.observable('tab-1');
            self.gridColumns = ko.observableArray([
                { headerText: '会社コード', prop: 'companyCode', width: 80 },
                { headerText: '名称', prop: 'companyName', width: 200 },
                { headerText: '廃止', prop: 'displayAttribute', width: 50, hidden: false }
            ]);

            self.currentCompany = ko.observable(new CompanyModel({
                companyCode: '',
                address1: '',
                companyName: '',
                companyNameGlobal: '',
                corporateMyNumber: '',
                depWorkPlaceSet: 0,
                displayAttribute: '',
                termBeginMon: 0,
                companyUseSet: null
            }));

            self.sel001Data = ko.observableArray([]);
            self.checked.subscribe(function(newValue) {
                let $grid = $("#A_LST_001");
                var currentColumns = $grid.igGrid("option", "columns");
                var width = $grid.igGrid("option", "width");

                if (newValue) {
                    //nts.uk.ui.dialog.confirm("Do you want to change data?").ifYes(function() {
                    $('#A_SEL_001').ntsError('clear');
                    currentColumns[2].hidden = false;
                    $grid.igGrid("option", "width", "400px");
                    self.sel001Data([]);
                    self.start(undefined);
                    //      });  
                } else {
                    //  nts.uk.ui.dialog.confirm("Do you want to change data?").ifYes(function() {
                    self.sel001Data([]);
                    currentColumns[2].hidden = true;
                    $grid.igGrid("option", "width", "400px");
                    service.getAllCompanys().done(function(data: Array<service.model.CompanyDto>) {
                        if (data.length > 0) {
                            _.each(data, function(obj: service.model.CompanyDto) {
                                let companyModel: CompanyModel;
                                companyModel = ko.mapping.fromJS(obj);
                                if (obj.displayAttribute === 0) {
                                    companyModel.displayAttribute('');
                                    self.sel001Data.push(ko.toJS(companyModel));

                                }
                            });
                            var companyCheckExist = _.find(self.sel001Data(), function(obj: CompanyModel) {
                                let x: string = ko.toJS(obj.companyCode);
                                let y: string = (ko.toJS(self.currentCompany().companyCode));
                                return x === y;

                            });
                            if (companyCheckExist != undefined) {
                                self.currentCompany().companyCode(ko.toJS(self.sel001Data()[0].companyCode));

                            } else {
                                self.currentCompany().companyCode("");
                            }
                        }
                    });
                    //     });
                }
                $grid.igGrid("option", "columns", currentColumns);
            });
        }
        start(currentCode: string) {
            let self = this;
            let items = [];
            service.getAllCompanys().done(function(data: Array<service.model.CompanyDto>) {
                if (data.length > 0) {
                    self.mode(true);
                    _.each(data, function(obj: service.model.CompanyDto) {
                        let companyModel: CompanyModel;
                        companyModel = ko.mapping.fromJS(obj);
                        if (obj.displayAttribute === 1) {
                            companyModel.displayAttribute('<i style="margin-left: 15px" class="icon icon-close"></i>');
                        } else {
                            companyModel.displayAttribute('');
                        }
                        self.sel001Data.push(ko.toJS(companyModel));
                    });
                    if (currentCode === undefined) {
                        self.currentCompany().setList(data, ko.toJS(self.sel001Data()[0].companyCode));
                    } else {
                        self.currentCompany().setList(data, currentCode);
                    }
                } else {
                    self.mode(false);
                }
            });
        }

        resetData() {
            let self = this;
            self.currentCompany().hasFocus(true);

            self.currentCompany().companyCode(null);
            self.currentCompany().address1("");
            self.currentCompany().addressKana1("");
            self.currentCompany().address2("");
            self.currentCompany().addressKana2("");
            self.currentCompany().companyName("");
            self.currentCompany().companyNameGlobal("");
            self.currentCompany().companyNameAbb('');
            self.currentCompany().companyNameKana('');
            self.currentCompany().corporateMyNumber('');
            self.currentCompany().companyUseSet(new CompanyUseSet(0, 0, 0));
            self.currentCompany().depWorkPlaceSet(0);
            self.currentCompany().displayAttribute('');
            self.currentCompany().faxNo('');
            self.currentCompany().telephoneNo('');
            self.currentCompany().postal('');
            self.currentCompany().presidentName('');
            self.currentCompany().presidentJobTitle('');
            self.currentCompany().termBeginMon(0);
            self.currentCompany().selectedRuleCode('0');
            self.currentCompany().selectedRuleCode1('0');
            self.currentCompany().selectedRuleCode2('0');
            self.currentCompany().selectedRuleCode3('0');
            self.currentCompany().isDelete(true);
            self.currentCompany().editMode = true;
            self.mode(false);

        }

        clickRegister() {

            let self = this;
            let dfd = $.Deferred<any>();
            let currentCompany: CompanyModel;
            currentCompany = ko.toJS(self.currentCompany);
            if (nts.uk.text.isNullOrEmpty(ko.toJS(currentCompany.companyCode))) {
                return;
            }
            if (currentCompany.isDelete) {
                currentCompany.displayAttribute = ko.observable("1");
            } else {
                currentCompany.displayAttribute = ko.observable("0");
            }

            let errorCheck1: boolean;
            if (self.checked()) {
                $('#A_SEL_001').ntsError('clear');
                errorCheck1 = true;
            } else {
                $('#A_SEL_001').ntsError('set', 'this check must be true');
                errorCheck1 = false;
            }
            let company: service.model.CompanyDto =
                new service.model.CompanyDto("", "", "", "", "", "", "", "", "", 0, 0, "", "", "", "", "", 0, 0, 0, 0, 0);
            company.companyCode = ko.toJS(currentCompany.companyCode);
            company.companyName = ko.toJS(currentCompany.companyName);
            company.companyNameGlobal = ko.toJS(currentCompany.companyNameGlobal);
            company.companyNameAbb = ko.toJS(currentCompany.companyNameAbb);
            company.companyNameKana = ko.toJS(currentCompany.companyNameKana);
            company.corporateMyNumber = ko.toJS(currentCompany.corporateMyNumber);
            company.use_Jj_Set = Number(ko.toJS(currentCompany.selectedRuleCode));
            company.use_Kt_Set = Number(ko.toJS(currentCompany.selectedRuleCode1));
            company.use_Qy_Set = Number(ko.toJS(currentCompany.selectedRuleCode2));
            company.depWorkPlaceSet = Number(ko.toJS(currentCompany.selectedRuleCode3));
            company.displayAttribute = Number(ko.toJS(currentCompany.displayAttribute));
            company.termBeginMon = Number(ko.toJS(currentCompany.termBeginMon));
            company.address1 = ko.toJS(currentCompany.address1);
            company.address2 = ko.toJS(currentCompany.address2);
            company.addressKana1 = ko.toJS(currentCompany.addressKana1);
            console.log(company.addressKana1);
            company.addressKana2 = ko.toJS(currentCompany.addressKana2);
            company.telephoneNo = ko.toJS(currentCompany.telephoneNo);
            company.faxNo = ko.toJS(currentCompany.faxNo);
            company.postal = ko.toJS(currentCompany.postal);
            company.presidentJobTitle = ko.toJS(currentCompany.presidentJobTitle);
            company.presidentName = ko.toJS(currentCompany.presidentName);
            if (self.mode()) {
                cmm001.a.service.updateData(company).done(function(data) {
                    console.log(company);
                    self.sel001Data([]);
                    self.start(company.companyCode);
                });
            } else {
                cmm001.a.service.addData(company).done(function() {
                    console.log(company);
                    self.sel001Data([]);
                    self.start(company.companyCode);
                })
            }
        }
    }
    class CompanyModel {
        sources: Array<any>;
        companyCode: KnockoutObservable<string>;
        address1: KnockoutObservable<string>;
        address2: KnockoutObservable<string>;
        addressKana1: KnockoutObservable<string>;
        addressKana2: KnockoutObservable<string>;
        companyName: KnockoutObservable<string>;
        companyNameGlobal: KnockoutObservable<string>;
        companyNameAbb: KnockoutObservable<string>;
        companyNameKana: KnockoutObservable<string>;
        corporateMyNumber: KnockoutObservable<string>;
        depWorkPlaceSet: KnockoutObservable<number>;
        displayAttribute: KnockoutObservable<string>;
        faxNo: KnockoutObservable<string>;
        postal: KnockoutObservable<string>;
        presidentName: KnockoutObservable<string>;
        presidentJobTitle: KnockoutObservable<string>;
        telephoneNo: KnockoutObservable<string>;
        termBeginMon: KnockoutObservable<number>;
        companyUseSet: KnockoutObservable<CompanyUseSet>;
        isDelete: KnockoutObservable<boolean>;
        itemList: KnockoutObservable<any>;
        //switch
        roundingRules: KnockoutObservableArray<RoundingRule>;
        selectedRuleCode: KnockoutObservable<string>;
        selectedRuleCode1: KnockoutObservable<string>;
        selectedRuleCode2: KnockoutObservable<string>;
        roundingRules3: KnockoutObservableArray<RoundingRule>;
        selectedRuleCode3: KnockoutObservable<string>;
        hasFocus: KnockoutObservable<boolean> = ko.observable(false);
        editMode: boolean = true;// mode reset or not reset

        constructor(param: ICompany) {
            let self = this;
            self.sources = param.sources || [];
            self.companyCode = ko.observable(param.companyCode);
            self.address1 = ko.observable(param.address1);
            self.address2 = ko.observable(param.address2);
            self.addressKana1 = ko.observable(param.addressKana1);
            self.addressKana2 = ko.observable(param.addressKana2);
            self.companyName = ko.observable(param.companyName);
            self.companyNameGlobal = ko.observable(param.companyNameGlobal);
            self.companyNameAbb = ko.observable(param.companyNameAbb);
            self.companyNameKana = ko.observable(param.companyNameKana);
            self.corporateMyNumber = ko.observable(param.corporateMyNumber);
            self.depWorkPlaceSet = ko.observable(param.depWorkPlaceSet);
            self.displayAttribute = ko.observable(param.displayAttribute);

            self.faxNo = ko.observable(param.faxNo);
            self.postal = ko.observable(param.postal);
            self.presidentName = ko.observable(param.presidentName);
            self.presidentJobTitle = ko.observable(param.presidentJobTitle);
            self.telephoneNo = ko.observable(param.telephoneNo);
            self.termBeginMon = ko.observable(param.termBeginMon);
            self.companyUseSet = ko.observable(param.companyUseSet);
            self.isDelete = ko.observable(param.isDelete || false);
            //SWITCH
            self.roundingRules = ko.observableArray([
                new RoundingRule("1", '利用する'),
                new RoundingRule('0', '利用しない')
            ]);
            self.selectedRuleCode = ko.observable("");
            self.selectedRuleCode1 = ko.observable("");
            self.selectedRuleCode2 = ko.observable('');
            self.roundingRules3 = ko.observableArray([
                new RoundingRule("1", '区別する'),
                new RoundingRule('0', '区別しない')
            ]);
            self.selectedRuleCode3 = ko.observable("");
            self.itemList = ko.observableArray([
                { code: '1', name: '1月' },
                { code: '2', name: '2月' },
                { code: '3', name: '3月' },
                { code: '4', name: '4月' },
                { code: '5', name: '5月' },
                { code: '6', name: '6月' },
                { code: '7', name: '7月' },
                { code: '8', name: '8月' },
                { code: '9', name: '9月' },
                { code: '10', name: '10月' },
                { code: '11', name: '11月' },
                { code: '12', name: '12月' }
            ]);

            self.companyCode.subscribe(function(newValue) {
                if (self.editMode) {
                    if (nts.uk.text.isNullOrEmpty(newValue)) {
                        return;
                    }

                    service.getCompanyDetail(newValue).done(function(company: service.model.CompanyDto) {
                        if (company) {
                            if ($('.nts-editor').ntsError("hasError")) {
                                $('.save-error').ntsError('clear');
                            }
                            $("#A_INP_002").attr('disabled', 'true');
                            $("#A_INP_002").attr('readonly', 'true');
                            self.address1(company.address1);
                            self.addressKana1(company.addressKana1);
                            self.address2(company.address2);
                            self.addressKana2(company.addressKana2);
                            self.companyName(company.companyName);
                            self.companyNameGlobal(company.companyNameGlobal);
                            self.companyNameAbb(company.companyNameAbb);
                            self.companyNameKana(company.companyNameKana);
                            self.corporateMyNumber(company.corporateMyNumber);
                            self.depWorkPlaceSet(company.depWorkPlaceSet);
                            self.displayAttribute(company.displayAttribute.toString());
                            if (company.displayAttribute === 1) {
                                self.isDelete(true);
                            } else {
                                self.isDelete(false);
                            }
                            self.faxNo(company.faxNo);
                            self.postal(company.postal);
                            self.presidentName(company.presidentName);
                            self.presidentJobTitle(company.presidentJobTitle);
                            self.telephoneNo(company.telephoneNo);
                            self.termBeginMon(company.termBeginMon);
                            self.companyUseSet(new CompanyUseSet(company.use_Kt_Set, company.use_Qy_Set, company.use_Jj_Set));
                            self.selectedRuleCode(company.use_Jj_Set.toString());
                            self.selectedRuleCode1(company.use_Kt_Set.toString());
                            self.selectedRuleCode2(company.use_Qy_Set.toString());
                            self.selectedRuleCode3(company.depWorkPlaceSet.toString());
                        } else {
                            self.editMode = false;
                            self.address1('');
                            self.address2('');
                            self.addressKana1('');
                            self.addressKana2('');
                            self.companyName('');
                            self.companyNameGlobal('');
                            self.companyNameAbb('');
                            self.companyNameKana('');
                            self.corporateMyNumber('');
                            self.depWorkPlaceSet(0);
                            self.displayAttribute('');
                            self.faxNo('');
                            self.postal('');
                            self.presidentName('');
                            self.presidentJobTitle('');
                            self.telephoneNo('');
                            self.termBeginMon(0);
                            self.companyUseSet(new CompanyUseSet(0, 0, 0));
                            self.isDelete(false);
                        }
                    });

                } else {
                    self.editMode = true;
                }
            });


        }


        setList(list: Array<any>, companyCode: string) {
            this.sources = list || [];
            this.companyCode(companyCode);
        }
        getCompanyDetail(currentCode: string) {
            let self = this;
            service.getCompanyDetail(currentCode).done(function(data: service.model.CompanyDto) {



            });


        }

        clickSel001() { }
    }

    interface ICompany {
        companyCode: string;
        address1: string;
        address2?: string;
        addressKana1?: string;
        addressKana2?: string;
        companyName: string;
        companyNameGlobal: string;
        companyNameAbb?: string;
        companyNameKana?: string;
        corporateMyNumber: string;
        depWorkPlaceSet: number;
        displayAttribute: string;
        faxNo?: string;
        postal?: string;
        presidentName?: string;
        presidentJobTitle?: string;
        telephoneNo?: string;
        termBeginMon: number;
        companyUseSet: CompanyUseSet;
        sources?: Array<any>;
        isDelete?: boolean;
    }

    export class Address {
        address1: KnockoutObservable<string>;
        address2: KnockoutObservable<string>;
        addressKana1: KnockoutObservable<string>;
        addressKana2: KnockoutObservable<string>;
        constructor(address1: string, address2: string, addressKana1: string, addressKana2: string) {
            this.address1 = ko.observable(address1);
            this.address2 = ko.observable(address2);
            this.addressKana1 = ko.observable(addressKana1);
            this.addressKana2 = ko.observable(addressKana2);
        }
    }

    export class CompanyUseSet {
        useGrSet: number;
        useKtSet: number;
        useQySet: number;
        useJjSet: number;
        useAcSet: number;
        useGwSet: number;
        useHcSet: number;
        useLcSet: number;
        useBiSet: number;
        useRs01Set: number;
        useRs02Set: number;
        useRs03Set: number;
        useRs04Set: number;
        useRs05Set: number;
        useRs06Set: number;
        useRs07Set: number;
        useRs08Set: number;
        useRs09Set: number;
        useRs10Set: number;
        constructor(useKtSet: number, useQySet: number, useJjSet: number) {
            this.useGrSet = 0;
            this.useKtSet = useKtSet;
            this.useQySet = useQySet;
            this.useJjSet = useJjSet;
            this.useAcSet = 0;
            this.useGwSet = 0;
            this.useHcSet = 0;
            this.useLcSet = 0;
            this.useBiSet = 0;
            this.useRs01Set = 0;
            this.useRs02Set = 0;
            this.useRs03Set = 0;
            this.useRs04Set = 0;
            this.useRs05Set = 0;
            this.useRs06Set = 0;
            this.useRs07Set = 0;
            this.useRs08Set = 0;
            this.useRs09Set = 0;
            this.useRs10Set = 0;

        }
    }
    class RoundingRule {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }


    }

}