module nts.uk.pr.view.qui001.c.viewmodel {
    import model = nts.uk.pr.view.qui001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        selectedItem: KnockoutObservable<string>= ko.observable('');
        /**
         *ComboBox
         */
        insuranceCauAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.insuranceCauAtr());
        jobAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.jobAtr());
        jobPaths: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.jobPath());
        wagePaymentModes: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.wagePaymentMode());
        employmentStatuses: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.employmentStatus());
        /**
         * Button Switch
         */
        acquisitionArts: KnockoutObservableArray<model.ItemModel> =  ko.observableArray(model.acquisitionArt());
        contractPeriodPrintArts: KnockoutObservableArray<model.ItemModel> =  ko.observableArray(model.contractPeriodPrintArt());
        /**
         * Text Editor
         */
        workingTime: KnockoutObservable<string> = ko.observable('');
        payWage: KnockoutObservable<string> = ko.observable('');
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
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        /**
         *
         */
        sId: KnockoutObservable<string> = ko.observable('');
        acquiArt: KnockoutObservable<number> = ko.observable(0);
        insuranceCauAtr: KnockoutObservable<number> = ko.observable(0);
        jobAtr: KnockoutObservable<number> = ko.observable(0);
        jobPath: KnockoutObservable<number> = ko.observable(0);
        wagePaymentMode: KnockoutObservable<number> = ko.observable(0);
        employmentStatus: KnockoutObservable<number> = ko.observable(0);
        conPerPrintArt: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            var self = this;
            /**
             * startPage
             */
            $('#emp-component').focus();
            self.selectedItem.subscribe(e => {
                self.startPage(e);
            });
            /**
             *KCP009
             */
            // let params = getShared("QUI001_PARAMS_A");
            let params = ko.observableArray([
                    {id: '01', code: 'A000000000001',  name: '大塚太郎A', workplaceName: '名古屋支店', isAlreadySetting: false},
                    {id: '02', code: 'A000000000002',  name: '大塚太郎B', workplaceName: '名古屋支店', isAlreadySetting: false},
                    {id: '03', code: 'A000000000003',  name: '大塚太郎C', workplaceName: '名古屋支店', isAlreadySetting: false},
                    {id: '04', code: 'A000000000004',  name: '大塚太郎D', workplaceName: '名古屋支店', isAlreadySetting: false},
                ]);
            if(nts.uk.util.isNullOrEmpty(params) || nts.uk.util.isNullOrEmpty(params)) {
                close();
            }
            // data fake
            self.employeeInputList = ko.observableArray([
                {id: '01', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '04', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '05', code: 'A000000000005', businessName: '日通　純一郎5', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '06', code: 'A000000000006', businessName: '日通　純一郎6', workplaceName: '名古屋支店', depName: 'Dep Name'},
            ]);
            this.loadKCP009();
            self.selectedItem(self.employeeInputList()[0].id);
        }

        cancel(){
            nts.uk.ui.windows.close();
        }

        loadKCP009(){
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
            self.systemReference = ko.observable(SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.tabindex = 3;

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

        register(){
            var self = this;
            let empInsGetInfo: any ={
                sId: self.selectedItem(),
                acquiArt: self.acquiArt(),
                jobPath: self.jobPath,
                workingTime: self.workingTime().length > 0 ? self.workingTime: null,
                jobArt: self.jobAtr(),
                payWage: self.payWage().length > 0 ? self.payWage: null,
                insuranceCauArt: self.insuranceCauAtr(),
                wagePaymentMode: self.wagePaymentMode(),
                employmentStatus: self.employmentStatus(),
                conPerPrintArt: self.conPerPrintArt(),
                screenMode: model.SCREEN_MODE.UPDATE
            };
            service.register(empInsGetInfo).done(function () {
                self.screenMode(model.SCREEN_MODE.UPDATE);
                self.startPage(self.selectedItem());
            });
        }

        startPage(sId: string): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.start(sId).done(function (data: any) {
                if (data){
                    self.acquiArt(data.acquiArt);
                    self.jobPath(data.jobPath);
                    self.workingTime(data.workingTime);
                    self.jobAtr(data.jobAtr);
                    self.payWage(data.payWage);
                    self.insuranceCauAtr(data.insuranceCauAtr);
                    self.wagePaymentMode(data.wagePaymentMode);
                    self.employmentStatus(data.employmentStatus);
                    self.conPerPrintArt(data.conPerPrintArt);
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                }else {
                    self.getDefault();
                }
            });
            return dfd.promise();
        }

        getDefault(){
            let self = this;
            self.createNew();
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        createNew(){
            let self = this;
            self.acquiArt(0);
            self.jobPath(0);
            self.workingTime('');
            self.jobAtr(0);
            self.payWage('');
            self.insuranceCauAtr(0);
            self.wagePaymentMode(0);
            self.employmentStatus(0);
            self.conPerPrintArt(0);
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

}