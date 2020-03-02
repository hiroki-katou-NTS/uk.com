module nts.uk.pr.view.qui001.c.viewmodel {
    import model = nts.uk.pr.view.qui001.share.model;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        /**
         *ComboBox
         */
        insCauseAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.insCauseAtr());
        jobAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.jobAtr());
        jobPaths: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.jobPath());
        wagePaymentModes: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.wagePaymentMode());
        employmentStatuses: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.employmentStatus());
        /**
         * Button Switch
         */
        acquisitionAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.acquisitionAtr());
        contrPeriPrintAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.contrPeriPrintAtr());
        /**
         * Text Editor
         */
        workingTime: KnockoutObservable<number> = ko.observable(null);
        payWage: KnockoutObservable<number> = ko.observable(null);
        /**
         * KCP009-社員送り
         */
        employeeInputList: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
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
        acquiAtr: KnockoutObservable<number> = ko.observable(0);
        insCauseAtr: KnockoutObservable<number> = ko.observable(0);
        jobAtr: KnockoutObservable<number> = ko.observable(0);
        jobPath: KnockoutObservable<number> = ko.observable(0);
        wagePaymentMode: KnockoutObservable<number> = ko.observable(0);
        employmentStatus: KnockoutObservable<number> = ko.observable(0);
        contrPeriPrintAtr: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            var self = this;
            /**
             * startPage
             */
            $('#emp-component').focus();
            self.selectedItem.subscribe((data) => {
                self.startPage(data);
            });
            /**
             *KCP009
             */
            let params = getShared("QUI001_PARAMS_A");
            if (nts.uk.util.isNullOrEmpty(params) || nts.uk.util.isNullOrEmpty(params.employeeList)) {
                close();
            }
            self.employeeInputList(self.createEmployeeModel(params.employeeList));
            this.loadKCP009();
            self.selectedItem(self.employeeInputList()[0].id);
        }

        createEmployeeModel(data) {
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.id,
                    code: data.code,
                    businessName: data.name,
                    workplaceName: data.workplaceName
                });
            });

            return listEmployee;
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        loadKCP009() {
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

        updateEmpInsGetInfo() {
            let self = this;
            let empInsGetInfo: any = {
                sId: self.selectedItem(),
                acquiAtr: self.acquiAtr(),
                jobPath: self.jobPath(),
                workingTime: self.workingTime() > 0 ? self.workingTime() : null,
                jobAtr: self.jobAtr(),
                payWage: self.payWage() > 0 ? self.payWage() : null,
                insCauseAtr: self.insCauseAtr(),
                wagePaymentMode: self.wagePaymentMode(),
                employmentStatus: self.employmentStatus(),
                contrPeriPrintAtr: self.contrPeriPrintAtr(),
                screenMode: self.screenMode()
            };
            service.register(empInsGetInfo).done(function () {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                });
            }).fail(error => {
                dialog.alertError(error);
            });
            $('#emp-component').focus();
        }

        startPage(sId: string): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.start(sId).done(function (data: any) {
                if (data) {
                    self.sId(data.sId);
                    self.acquiAtr(data.acquiAtr);
                    self.jobPath(data.jobPath);
                    self.workingTime(data.workingTime);
                    self.jobAtr(data.jobAtr);
                    self.payWage(data.payWage);
                    self.insCauseAtr(data.insCauseAtr);
                    self.wagePaymentMode(data.wagePaymentMode);
                    self.employmentStatus(data.employmentStatus);
                    self.contrPeriPrintAtr(data.contrPeriPrintAtr);
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                } else {
                    self.getDefault();
                }
            }).fail(error => {
                dialog.alertError(error);
                dfd.reject();
            });
            return dfd.promise();
        }

        getDefault() {
            let self = this;
            self.createNew();
            self.screenMode(model.SCREEN_MODE.NEW);
        }

        createNew() {
            let self = this;
            self.acquiAtr(0);
            self.jobPath(0);
            self.workingTime(null);
            self.jobAtr(0);
            self.payWage(null);
            self.insCauseAtr(0);
            self.wagePaymentMode(0);
            self.employmentStatus(0);
            self.contrPeriPrintAtr(0);
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