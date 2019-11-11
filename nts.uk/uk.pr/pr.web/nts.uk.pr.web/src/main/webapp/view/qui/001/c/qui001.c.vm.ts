module nts.uk.pr.view.qui001.c.viewmodel {
    var dialog = nts.uk.ui.dialog;
    import model = nts.uk.pr.view.qui001.share.model;
    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        // paymentDateProcessingList: KnockoutObservableArray<any>;
        // selectedPaymentDate: KnockoutObservable<any>;
        submittedNameA: KnockoutObservable<any> = ko.observable(0);
        submittedNameB: KnockoutObservable<any> = ko.observable(0);
        /**
         *ComboBox
         */
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        /**
         *
         * @type {KnockoutObservable<string>}
         */
        selectedItem: KnockoutObservable<string>= ko.observable('');
        /**
         * Button Switch
         */
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        roundingRules1: KnockoutObservableArray<any>;
        selectedRuleCode1: any;

        submittedNamesA: KnockoutObservableArray<model.ItemModelA> = ko.observableArray(model.getSubNameClassA());
        submittedNamesB: KnockoutObservableArray<model.ItemModelB> = ko.observableArray(model.getSubNameClassB());
        socInsurNotiCreSet : KnockoutObservable<SocInsurNotiCreSet> = ko.observable(new SocInsurNotiCreSet({
            submittedNameA: 0,
            submittedNameB: 0
        }));
        /**
         * Text Editor
         */
        simpleValue: KnockoutObservable<string>;

        /**
         * KCP009-社員送り
         */
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;

        constructor() {
            var self = this;

            // self.paymentDateProcessingList = ko.observableArray([]);
            // self.selectedPaymentDate = ko.observable(null);

            /**
             * loadComboBox
             */
            self.loadComboBox()

            /**
             * Text Editor
             */
            self.simpleValue = ko.observable("");
            /**
             * loadKCP009
             */
            self.loadKCP009()
            /**
             * startPage
             */
            self.startPage();
        }

        loadComboBox(){
            let self = this;
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            self.selectedCode = ko.observable('1');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

        }

        loadKCP009(){
            let self = this;
            self.employeeInputList = ko.observableArray([
                {id: '01', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '04', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '05', code: 'A000000000005', businessName: '日通　純一郎5', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '06', code: 'A000000000006', businessName: '日通　純一郎6', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '07', code: 'A000000000007', businessName: '日通　純一郎7', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '08', code: 'A000000000008', businessName: '日通　純一郎8', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '09', code: 'A000000000009', businessName: '日通　純一郎9', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '10', code: 'A000000000010', businessName: '日通　純一郎10', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '11', code: 'A000000000011', businessName: '日通　純一郎11', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '02', code: 'A000000000002', businessName: '日通　純一郎2', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '03', code: 'A000000000003', businessName: '日通　純一郎3', workplaceName: '名古屋支店', depName: 'Dep Name'}]);

            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 1;
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getSocialInsurNotiCreateSet().done(function (data : ISocInsurNotiCreSet) {
                if (data != null){
                    self.socInsurNotiCreSet().submittedNameA(data.submittedNameA);
                    self.socInsurNotiCreSet().submittedNameB(data.submittedNameB);
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                }else {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
            })
            return dfd.promise();
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }

    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

    export interface ISocInsurNotiCreSet {
        submittedNameA: number;
        submittedNameB: number;
    }

    export class SocInsurNotiCreSet {
        submittedNameA: KnockoutObservable<number>;
        submittedNameB: KnockoutObservable<number>;
        constructor(params: ISocInsurNotiCreSet) {
            this.submittedNameA = ko.observable(params.submittedNameA);
            this.submittedNameB = ko.observable(params.submittedNameB);
        }
    }

}