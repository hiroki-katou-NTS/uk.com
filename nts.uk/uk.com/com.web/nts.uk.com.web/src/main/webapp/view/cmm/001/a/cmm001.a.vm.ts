module cmm001.a {
    import PostCode = nts.uk.pr.view.base.postcode.service.model.PostCode;
    export class ViewModel {

        gridColumns: KnockoutObservableArray<any>;
        currentCompany: KnockoutObservable<CompanyModel>;
        company: service.model.CompanyDto = null;
        currentCompanyCode: KnockoutObservable<string>;
        sel001Data: KnockoutObservableArray<CompanyModel>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        displayAttribute: KnockoutObservable<boolean>;
        previousDisplayAttribute: boolean = true; //lưu giá trị của displayAttribute trước khi nó bị thay đổi
        isUpdate: KnockoutObservable<boolean> = ko.observable(null);
        dirtyObject: nts.uk.ui.DirtyChecker;
        previousCurrentCode: string = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
        itemList: KnockoutObservable<any>;
        hasFocus: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            let itemArray = [
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
            ];
            self.selectedTab = ko.observable('tab-1');
            self.gridColumns = ko.observableArray([
                { headerText: '会社コード', prop: 'companyCode', width: 80 },
                { headerText: '名称', prop: 'companyName', width: 220 },
                { headerText: '廃止', prop: 'isAbolition', width: 75,
                    template: '{{if ${isAbolition} == 1}} <img src="../images/78.png" style="margin-left: 20px; width: 20px; height: 20px;" /> {{/if}}', }
            ]);
            self.sel001Data = ko.observableArray([]);
            self.itemList = ko.observableArray(itemArray);
            self.displayAttribute = ko.observable(true);
            self.currentCompany = ko.observable(null);
            self.currentCompanyCode = ko.observable('');
            self.sel001Data = ko.observableArray([]);
        }

        

       

        

        start(currentCode: string) {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAll().done((lst) => {
                let param = {
                    companyId: '000000000000-0001',
                    companyCode: '0001'    
                }
                service.getDiv(param).done((div) => {
                    console.log(div);    
                })
                
                
                self.sel001Data(lst);  
                    
               console.log(self.sel001Data());
                dfd.resolve(); 
            }).fail(function(error){
                    dfd.reject();
                    alert(error.message);
                }) 
//            service.getAllCompanys().done(function(data: Array<service.model.CompanyDto>) {
//                if (data.length > 0) {
//                    self.isUpdate(true);
//                    _.each(data, function(obj: service.model.CompanyDto) {
//                        let companyModel: CompanyModel;
//                        companyModel = ko.mapping.fromJS(obj);
//                        if (obj.displayAttribute === 1) {
//                            companyModel.displayAttribute('');
//                        } else {
//                            companyModel.displayAttribute('<i style="margin-left: 15px" class="icon icon-close"></i>');
//                        }
//                        self.sel001Data.push(ko.toJS(companyModel));
//                    });
//                    if (currentCode === undefined) {
//                        self.currentCompany = ko.observable(new CompanyModel({
//                            companyCode: ko.toJS(self.sel001Data()[0].companyCode),
//                            address1: '',
//                            companyName: '',
//                            companyNameGlobal: '',
//                            corporateMyNumber: '',
//                            depWorkPlaceSet: 0,
//                            displayAttribute: '',
//                            termBeginMon: 0,
//                            companyUseSet: null
//                        }));
//                        self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentCompany);
//                        self.currentCompanyCode(self.currentCompany().companyCode());
//                    } else {
//                        self.currentCompanyCode(currentCode);
//                    }
//                } else {
//                    self.currentCompany = ko.observable(new CompanyModel({
//                        companyCode: '',
//                        address1: '',
//                        companyName: '',
//                        companyNameGlobal: '',
//                        corporateMyNumber: '',
//                        depWorkPlaceSet: 0,
//                        displayAttribute: '',
//                        termBeginMon: 0,
//                        companyUseSet: null
//                    }));
//                    self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentCompany);
//                    self.currentCompanyCode(self.currentCompany().companyCode());
//                    self.resetData();
//                }
//                dfd.resolve();
//            });
            return dfd.promise();
        }

       

        

        
        
       
    }
    class CompanyModel {
//        companyCode: KnockoutObservable<string>;
//        isEnableCompanyCode: KnockoutObservable<boolean> = ko.observable(true);
//        address1: KnockoutObservable<string>;
//        address2: KnockoutObservable<string>;
//        addressKana1: KnockoutObservable<string>;
//        addressKana2: KnockoutObservable<string>;
//        companyName: KnockoutObservable<string>;
//        companyNameGlobal: KnockoutObservable<string>;
//        companyNameAbb: KnockoutObservable<string>;
//        companyNameKana: KnockoutObservable<string>;
//        corporateMyNumber: KnockoutObservable<string>;
//        depWorkPlaceSet: KnockoutObservable<number>;
//        displayAttribute: KnockoutObservable<string>;
//        faxNo: KnockoutObservable<string>;
//        postal: KnockoutObservable<string>;
//        presidentName: KnockoutObservable<string>;
//        presidentJobTitle: KnockoutObservable<string>;
//        telephoneNo: KnockoutObservable<string>;
//        termBeginMon: KnockoutObservable<number>;
//        companyUseSet: KnockoutObservable<CompanyUseSet>;
//        isDelete: KnockoutObservable<boolean>;
//        //switch
//        roundingRules: KnockoutObservableArray<RoundingRule>;
//        selectedRuleCode: KnockoutObservable<string>;
//        selectedRuleCode1: KnockoutObservable<string>;
//        selectedRuleCode2: KnockoutObservable<string>;
//        roundingRules3: KnockoutObservableArray<RoundingRule>;
//        selectedRuleCode3: KnockoutObservable<string>;
//        editMode: boolean = true;// mode reset or not reset
        // yen
        companyCode: KnockoutObservable<string>;
        comNameKana?: KnockoutObservable<string>;
        companyId: KnockoutObservable<string>;
        companyName?: KnockoutObservable<string>;
        contractCd: KnockoutObservable<string>;
        isAbolition: KnockoutObservable<any> = ko.observable();
        repJob?: KnockoutObservable<string>;
        repname?: KnockoutObservable<string>;
        shortComName?: KnockoutObservable<string>;
        startMonth: KnockoutObservable<number>;
        taxNum?: KnockoutObservable<number>;
        addinfor?: KnockoutObservable<IAddInfor>;
        regWorkDiv?: KnockoutObservable<number>;
        constructor(param: ICompany) {
            let self = this;

        }
    }

    interface ICompany {
//        companyCode: string;
//        address1: string;
//        address2?: string;
//        addressKana1?: string;
//        addressKana2?: string;
//        companyName: string;
//        companyNameGlobal: string;
//        companyNameAbb?: string;
//        companyNameKana?: string;
//        corporateMyNumber: string;
//        depWorkPlaceSet: number;
//        displayAttribute: string;
//        faxNo?: string;
//        postal?: string;
//        presidentName?: string;
//        presidentJobTitle?: string;
//        telephoneNo?: string;
//        termBeginMon: number;
//        companyUseSet: CompanyUseSet;
//        isDelete?: boolean;
        companyCode: string;
        comNameKana?: string;
        companyId: string;
        companyName?: string;
        contractCd: string;
        isAbolition: any;
        repJob?: string;
        repname?: string;
        shortComName?: string;
        startMonth: number;
        taxNum?: number;
        addinfor?:  IAddInfor;
        regWorkDiv?: number;
    }

    interface IAddInfor{
        addKana_1?: string;
        addKana_2?: string;
        add_1?: string;
        add_2?: string;
        companyCode: string;
        companyId: string;
        contractCd: string;
        faxNum?: string;
        phoneNum?: string;
        postCd?: string;
    } 
    
    export class AddInfor{
        addKana_1: string;
        addKana_2: string;
        add_1: string;
        add_2: string;
        companyCode: string;
        companyId: string;
        contractCd: string;
        faxNum: string;  
        phoneNum: string;
        postCd: string;
        constructor(param: IAddInfor){
            this.addKana_1 = param.addKana_1;
            this.addKana_2 = param.addKana_2;
            this.add_1 = param.add_1;
            this.add_2 = param.add_2;
            this.companyCode = param.companyCode;
            this.companyId = param.companyId;
            this.contractCd = param.contractCd;
            this.faxNum = param.faxNum;
            this.phoneNum = param.phoneNum;
            this.postCd = param.postCd;    
        }
    }
      
//    export class CompanyUseSet {
//       
//        constructor(useKtSet: number, useQySet: number, useJjSet: number) {
//        }
//    }
    
    class RoundingRule {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    

}