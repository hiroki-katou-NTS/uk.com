module cmm001.a {
    import PostCode = nts.uk.pr.view.base.postcode.service.model.PostCode;
    export class ViewModel {

        gridColumns: KnockoutObservableArray<any>;
        currentCompany: KnockoutObservable<CompanyModel>;
        company: service.model.CompanyDto = null;
        currentCompanyCode: KnockoutObservable<string>;
        sel001Data: KnockoutObservableArray<ICompany>;
        listCom: KnockoutObservableArray<ICompany>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        displayAttribute: KnockoutObservable<boolean>;
        previousDisplayAttribute: boolean = true; //lưu giá trị của displayAttribute trước khi nó bị thay đổi
        isUpdate: KnockoutObservable<boolean> = ko.observable(null);
        dirtyObject: nts.uk.ui.DirtyChecker;
        previousCurrentCode: string = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
        itemList: KnockoutObservable<any>;
        hasFocus: KnockoutObservable<boolean> = ko.observable(true);
        roundingRules:  KnockoutObservableArray<RoundingRule>;
        roundingRules3: KnockoutObservableArray<RoundingRule>;
        display: KnockoutObservable<boolean>;
        
        
        constructor() {
            let self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '会社基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '会社所在地・連絡先', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'システム設定', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            let itemArray = [
                { code: 1, name: '1月' },
                { code: 2, name: '2月' },
                { code: 3, name: '3月' },
                { code: 4, name: '4月' },
                { code: 5, name: '5月' },
                { code: 6, name: '6月' },
                { code: 7, name: '7月' },
                { code: 8, name: '8月' },
                { code: 9, name: '9月' },
                { code: 10, name: '10月' },
                { code: 11, name: '11月' },
                { code: 12, name: '12月' }
            ];
            self.selectedTab = ko.observable('tab-1');
            self.gridColumns = ko.observableArray([
                { headerText: '会社コード', prop: 'companyCode', width: 80 },
                { headerText: '名称', prop: 'companyName', width: 220 },
                { headerText: '廃止', prop: 'isAbolition', width: 75,
                    template:  '{{if ${isAbolition} == 1}} <img src="../images/78.png" style="margin-left: 20px; width: 20px; height: 20px;" />{{else }} <span></span> {{/if}}'}
            ]);
            self.sel001Data = ko.observableArray([]);
            self.listCom = ko.observableArray([]);
            self.itemList = ko.observableArray(itemArray);
            self.displayAttribute = ko.observable(true);
            self.currentCompany = ko.observable(null);
            self.currentCompanyCode = ko.observable('');
            self.sel001Data = ko.observableArray([]);
            self.display = ko.observable(false);
            
            self.roundingRules = ko.observableArray([
                new RoundingRule(1, nts.uk.resource.getText("CMM001_31")),
                new RoundingRule(0, nts.uk.resource.getText("CMM001_32"))
            ]);
            self.roundingRules3= ko.observableArray([
                new RoundingRule(1, nts.uk.resource.getText("CMM001_36")),
                new RoundingRule(0, nts.uk.resource.getText("CMM001_37"))
            ]);
            
            self.currentCompanyCode.subscribe((value) => {
                if (value) {
                    let foundItem: ICompany = _.find(self.sel001Data(), (item: ICompany) => {
                        return item.companyCode == value;
                    });
                    self.currentCompany(new CompanyModel(foundItem));
                    console.log(self.currentCompany());
                    let param = {
                        companyId: self.currentCompany().companyId(),
                        companyCode: self.currentCompany().companyCode()    
                    }
                    var divEmpty = {
                        regWorkDiv: 1,
                    }
                    service.getDiv(param).done((div) => {
                        console.log(div);
                        div == null? div = divEmpty: div;
                        self.currentCompany().regWorkDiv(div.regWorkDiv); 
                    })
                    var sysEmpty = {
                        jinji: 1,
                        kyuyo: 1,
                        shugyo: 1,
                    }
                    service.getSys(param).done((sys) => {
                        console.log(sys); 
                        sys == null? sys=sysEmpty : sys;
                        self.currentCompany().jinji(sys.jinji);
                        self.currentCompany().kyuyo(sys.kyuyo);
                        self.currentCompany().shugyo(sys.shugyo);   
                    })
                    self.currentCompany().isAbolition() == 1 ? true : false;
                }
            });
            self.display.subscribe((item) => {
                if(item){
                    self.sel001Data(_.filter(self.listCom(), function(obj) {
                        return obj.isAbolition == 1;
                    }));
                }else{
                    self.sel001Data(self.listCom());    
                }
            });
        }

        start() {
            let self = this;
            let dfd = $.Deferred<any>();
            service.getAll().done((lst) => {
                if(lst.length == 0){
                    self.sel001Data([]); 
                    self.listCom([]);
                    self.innit();
                }else{
                    self.sel001Data(lst); 
                    self.listCom(lst); 
                    self.currentCompanyCode(self.sel001Data()[0].companyCode);
                }
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
        
        innit(){
            let self = this;
            var addEmpty = {
                            addKana_1: "",
                            addKana_2: "",
                            add_1: "",
                            add_2: "",
                            companyCode: "",
                            companyId: "",
                            contractCd: "",
                            faxNum: "",
                            phoneNum: "",
                            postCd: ""    
                        };            
            let param = {
                companyCode: "",
                comNameKana: "",
                companyId: "",
                companyName: "",
                contractCd: "",
                isAbolition: 0,
                repJob: "",
                repname: "",
                shortComName: "",
                startMonth: 1,
                taxNum: "",
                addinfor:  addEmpty,
                regWorkDiv: 1,
                jinji: 1,
                kyuyo: 1,
                shugyo: 1,    
            } 
//            self.currentCompanyCode("");
//            self.currentCompany(null);
//            self.currentCompany().comNameKana("");
//            self.currentCompany().companyId("");
//            self.currentCompany().companyName("")
//            self.currentCompany().contractCd("");
//            self.currentCompany().isAbolition(0);
//            self.currentCompany().repJob("");
//            self.currentCompany().repname("");
//            self.currentCompany().shortComName("");
//            self.currentCompany().startMonth(1);
//            self.currentCompany().taxNum(null);
//            self.currentCompany().addinfor(addEmpty);
//            self.currentCompany().regWorkDiv(1);
//            self.currentCompany().jinji(1);
//            self.currentCompany().kyuyo(1);
//            self.currentCompany().shugyo(1);
            self.currentCompany(new CompanyModel(param));   
            self.currentCompanyCode("");
        }
        
        
       register(){
           let self = this;
           let dataAdd: IAddInfor ={
               companyCode: self.currentCompany().companyCode(),
               faxNum: self.currentCompany().addinfor().faxNum(),
               add_1: self.currentCompany().addinfor().add_1(),
               add_2: self.currentCompany().addinfor().add_2(),
               addKana_1: self.currentCompany().addinfor().addKana_1(),
               addKana_2: self.currentCompany().addinfor().addKana_2(),
               postCd: self.currentCompany().addinfor().postCd(),
               phoneNum: self.currentCompany().addinfor().phoneNum(),
               
           }
           let dataCom = {
               ccd: string = self.currentCompany().companyCode(),
               name: string = self.currentCompany().companyName(),
               month: number = self.currentCompany().startMonth(), 
               abolition: number = self.currentCompany().isAbolition() == true ? 1:0,
               repname: string = self.currentCompany().repname(),
               repJob: string = self.currentCompany().repJob(),
               comNameKana: string = self.currentCompany().comNameKana(),
               shortComName: string = self.currentCompany().shortComName(),
               contractCd: string = self.currentCompany().contractCd(),
               taxNo: number = self.currentCompany().taxNum(),
               addinfor: IAddInfor =  dataAdd,
           }
           let dataSys = {
                companyCode: string = self.currentCompany().companyCode(),
                contractCd: string = self.currentCompany().contractCd(),
                jinji: number = self.currentCompany().jinji(),
                shugyo: number = self.currentCompany().shugyo(),
                kyuyo: number = self.currentCompany().kyuyo(),    
           }
           let dataDiv = {
                companyCode: string = self.currentCompany().companyCode(),
                contractCd: string = self.currentCompany().contractCd(),
                regWorkDiv: number = self.currentCompany().regWorkDiv(),
           }
           let dataTransfer = {
                comCm: any = dataCom,
                sysCm: any = dataSys,
                divCm: any = dataDiv,
           } 
           let code = self.currentCompany().companyCode();
           service.update(dataTransfer).done(function(){
               nts.uk.ui.dialog.info({ messageId: "Msg_15" });
               self.start().then(function(){
                   self.currentCompanyCode(code);
               });
           });
//           service.add(dataTransfer).done(function(){
//               nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//               self.start().then(function(){
//                   self.currentCompanyCode(code);
//               });
//               
//           })
       }

       search(){
            let self = this;
            service.findPostCd(self.currentCompany().addinfor().postCd()).done((item) => {
                console.log(item);
                item[0].stateProvince = item[0].stateProvince == null ? "":item[0].stateProvince;
                item[0].city = item[0].city == null ? "":item[0].city;
                item[0].townArea = item[0].townArea == null ? "":item[0].townArea;
                
                item[0].townAreaKana = item[0].townAreaKana == null ? "":item[0].townAreaKana;
                item[0].cityKanaName = item[0].cityKanaName == null ? "":item[0].cityKanaName;
                item[0].townAreaKana = item[0].townAreaKana == null ? "":item[0].townAreaKana;
                self.currentCompany().addinfor().add_1(item[0].stateProvince + item[0].city + item[0].townArea);
                self.currentCompany().addinfor().addKana_1(item[0].townAreaKana + item[0].cityKanaName + item[0].townAreaKana);
            })
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
        comNameKana: KnockoutObservable<string>;
        companyId: KnockoutObservable<string>;
        companyName: KnockoutObservable<string>;
        contractCd: KnockoutObservable<string>;
        isAbolition: KnockoutObservable<number> ;
        repJob: KnockoutObservable<string>;
        repname: KnockoutObservable<string>;
        shortComName: KnockoutObservable<string>;
        startMonth: KnockoutObservable<number>;
        taxNum: KnockoutObservable<number>;
        addinfor: KnockoutObservable<AddInfor>;
        regWorkDiv: KnockoutObservable<number>;
        jinji: KnockoutObservable<number>;
        kyuyo: KnockoutObservable<number>;
        shugyo: KnockoutObservable<number>;
        constructor(param: ICompany) {
            let self = this;
            this.companyCode = ko.observable(param.companyCode);
            this.comNameKana = ko.observable(param.comNameKana);
            this.companyId = ko.observable(param.companyId);
            this.companyName = ko.observable(param.companyName);
            this.contractCd = ko.observable(param.contractCd);
            this.isAbolition = ko.observable(param.isAbolition);
            this.repJob = ko.observable(param.repJob);
            this.repname = ko.observable(param.repname);
            this.shortComName = ko.observable(param.shortComName);
            this.startMonth = ko.observable(param.startMonth);
            this.taxNum = ko.observable(param.taxNum);
            this.addinfor = ko.observable(new AddInfor(param.addinfor) || new AddInfor({
                addKana_1: "",
                addKana_2: "",
                add_1: "",
                add_2: "",
                companyCode: "",
                companyId: "",
                contractCd: "",
                faxNum: "",
                phoneNum: "",
                postCd: ""    
            }));
            this.regWorkDiv = ko.observable(param.regWorkDiv);
            this.jinji = ko.observable(param.jinji);
            this.kyuyo = ko.observable(param.kyuyo);
            this.shugyo = ko.observable(param.shugyo);
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
        isAbolition: number;
        repJob?: string;
        repname?: string;
        shortComName?: string;
        startMonth: number;
        taxNum?: number;
        addinfor?:  IAddInfor;
        regWorkDiv?: number;
        jinji?: number;
        kyuyo?: number;
        shugyo?: number;
        roundingRules?: RoundingRule;
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
        addKana_1: KnockoutObservable<string>;
        addKana_2: KnockoutObservable<string>;
        add_1: KnockoutObservable<string>;
        add_2: KnockoutObservable<string>;
        companyCode: KnockoutObservable<string>;
        companyId: KnockoutObservable<string>;
        contractCd: KnockoutObservable<string>;
        faxNum: KnockoutObservable<string>;  
        phoneNum: KnockoutObservable<string>;
        postCd: KnockoutObservable<string>;
        constructor(param: IAddInfor){
            this.addKana_1 = ko.observable(param.addKana_1 || "");
            this.addKana_2 = ko.observable(param.addKana_2 || "");
            this.add_1 = ko.observable(param.add_1 || "");
            this.add_2 = ko.observable(param.add_2 || "");
            this.companyCode = ko.observable(param.companyCode || "");
            this.companyId = ko.observable(param.companyId || "");
            this.contractCd = ko.observable(param.contractCd || "");
            this.faxNum = ko.observable(param.faxNum || "");
            this.phoneNum = ko.observable(param.phoneNum || "");
            this.postCd = ko.observable(param.postCd || "");    
        }
    }
      
//    export class CompanyUseSet {
//       
//        constructor(useKtSet: number, useQySet: number, useJjSet: number) {
//        }
//    }
    
    class RoundingRule {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    

}