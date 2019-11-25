module nts.uk.pr.view.qui004.c.viewmodel {
    import model = nts.uk.pr.view.qui004.share.model;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        /**
         *ComboBox
         */
        causeOfLossAtrs: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getCauseOfLossAtr());
        causeOfLossEmpInsurances: KnockoutObservableArray<RetirementReasonDto> = ko.observableArray([]);
        listRetirementReason: Array<RetirementReasonDto>;
        /**
         * Button Switch
         */
        requestForInsurances: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRequestForInsurance());
        scheduleForReplenishments: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getScheduleForReplenishment());
        /**
         * Text Editor
         */
        scheduleWorkingHourPerWeek: KnockoutObservable<number> = ko.observable(null);
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
        causeOfLossAtr: KnockoutObservable<number> = ko.observable(0);
        requestForInsurance: KnockoutObservable<number> = ko.observable(0);
        scheduleForReplenishment: KnockoutObservable<number> = ko.observable(0);
        causeOfLossEmpInsurance: KnockoutObservable<string> = ko.observable(null);

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
            let params = getShared("QUI004_PARAMS_A");
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

        updateEmpInsLossInfo() {
            let self = this;
            let empInsLossInfo: any = {
                sId: self.selectedItem(),
                causeOfLossAtr: self.causeOfLossAtr(),
                requestForInsurance: self.requestForInsurance(),
                scheduleWorkingHourPerWeek: self.scheduleWorkingHourPerWeek() > 0 ? self.scheduleWorkingHourPerWeek() : null,
                scheduleForReplenishment: self.scheduleForReplenishment(),
                causeOfLossEmpInsurance: self.causeOfLossEmpInsurance().length > 0 ? self.causeOfLossEmpInsurance() : null,
                screenMode: self.screenMode()
            };
            service.register(empInsLossInfo).done(function () {
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
            $.when(service.start(sId), service.getRetirement()).done(function (data: any, getRetirement: any) {
                if (data && getRetirement) {
                    self.sId(data.sId);
                    self.causeOfLossAtr(data.causeOfLossAtr);
                    self.requestForInsurance(data.requestForInsurance);
                    self.scheduleWorkingHourPerWeek(data.scheduleWorkingHourPerWeek);
                    self.scheduleForReplenishment(data.scheduleForReplenishment);
                    self.causeOfLossEmpInsurances (getRetirement.listRetirementReason);
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
            self.causeOfLossAtr(0);
            self.requestForInsurance(0);
            self.scheduleWorkingHourPerWeek(null);
            self.scheduleForReplenishment(0);
            self.causeOfLossEmpInsurance(null);
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

    export interface RetirementReasonDto {
        cid: string;
        retirementReasonClsCode: string;
        retirementReasonClsName: string;
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

}