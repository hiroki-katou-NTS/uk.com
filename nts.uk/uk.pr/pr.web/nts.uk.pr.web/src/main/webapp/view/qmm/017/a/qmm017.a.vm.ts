module nts.uk.pr.view.qmm017.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        // tabs variables
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        
        // tree grid variables
        formulaList: any;
        selectedFormulaCode: KnockoutObservable<string>;
        headers: any;
        
        // switch setting method
        settingMethods: KnockoutObservableArray<any>;
        
        selectedFormula:  KnockoutObservable<Formula>;
        viewModelD: any = new qmm017.d.viewmodel.ScreenModelD();
        
        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('QMM017_6'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('QMM017_7'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.headers = ko.observableArray([getText('QMM017_8')]);
            self.formulaList = ko.observableArray([
                new Node('0001', 'サービス部', [
                    new Node('0001-1', 'サービス部1', []),
                    new Node('0001-2', 'サービス部2', []),
                    new Node('0001-3', 'サービス部3', [])
                ]), 
                new Node('0002', '開発部', [
                    new Node('0002-1', '開発部1', []),
                    new Node('0002-2', '開発部2', [])
                ])
            ]);
            self.settingMethods  = ko.observableArray([
                { code: 0, name: 'かんたん設定' },
                { code: 1, name: '詳細設定' }
            ]);
            self.selectedFormulaCode = ko.observable(null);
            self.selectedFormula = ko.observable(new Formula("", "", 0));
            self.selectedFormulaCode.subscribe(val => {
                console.log(val);
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
    
    class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
        }
    }
    
    class Formula {
        formulaCode: KnockoutObservable<string> = ko.observable(null);
        formulaName: KnockoutObservable<string> = ko.observable(null);
        settingMethod: KnockoutObservable<number> = ko.observable(null);
        nestedAtr: KnockoutObservable<number> = ko.observable(null);
        constructor(code: string, name: string, settingMethod: number, nestedAtr?: number) {
            this.formulaCode(code);
            this.formulaName(name);
            this.settingMethod(settingMethod);
            this.nestedAtr(nestedAtr);
        }
    }
    
    enum SETTING_METHOD {
        SIMPLE_SETTING = 0,
        DETAIL_SETTING = 1
    }

}