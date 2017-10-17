module cps002.a.vm {
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    import subModal = nts.uk.ui.windows.sub.modal;
    import jump = nts.uk.request.jump;

    export class ViewModel {

        date: KnockoutObservable<Date> = ko.observable(new Date());

        itemList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, text('CPS002_26')),
            new BoxModel(2, text('CPS002_27')),
            new BoxModel(3, text('CPS002_28'))
        ]);

        categoryList: KnockoutObservableArray<CategoryItem> = ko.observableArray([]);

        initValueList: KnockoutObservableArray<InitValueSetting> = ko.observableArray([]);

        selectedId: KnockoutObservable<number> = ko.observable(1);

        enable: KnockoutObservable<boolean> = ko.observable(true);

        selectedCode: KnockoutObservable<number> = ko.observable(1);

        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());

        currentCode = ko.observable(1);

        categorySelectedId: KnockoutObservable<string> = ko.observable('');

        empRegHistory: KnockoutObservable<EmpRegHistory> = ko.observable(new EmpRegHistory(null));

        currentStep: KnockoutObservable<number> = ko.observable(0);

        initValueSelectedId: KnockoutObservable<string> = ko.observable('');

        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            isQuickSearchTab: ko.observable(true),
            isAdvancedSearchTab: ko.observable(true),
            isAllReferableEmployee: ko.observable(true),
            isOnlyMe: ko.observable(true),
            isEmployeeOfWorkplace: ko.observable(true),
            isEmployeeWorkplaceFollow: ko.observable(true),
            isMutipleCheck: ko.observable(true),
            isSelectAllEmployee: ko.observable(true),
            onSearchAllClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchOnlyClicked: (data: any) => {
                let self = this;
            },
            onSearchOfWorkplaceClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchWorkplaceChildClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onApplyEmployee: (dataEmployee: Array<any>) => {
                let self = this;
            }
        };

        constructor() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

            self.selectedId.subscribe((newValue) => {

            });

            self.initValueSelectedId.subscribe((newValue) => {

                if (self.isUseInitValue()) {

                    service.getAllInitValueCtgSetting(self.initValueSelectedId()).done((result: Array<IInitValueCtgSetting>) => {
                        self.categoryList.removeAll();
                        if (result.length) {
                            self.categoryList(_.map(result, item => {
                                return new CategoryItem(item);
                            }));
                            self.categorySelectedId(result[0].perInfoCtgId);

                        } else {



                        }

                    });
                }

            });

            self.start();
        }

        start() {

            let self = this;

            service.getLayout().done((layout) => {
                if (layout) {
                    service.getUserSetting().done((result: IUserSetting) => {
                        if (result) {

                            self.getEmployeeCode(result).done(() => {

                                self.getCardNumber(result);

                            });
                        }

                        self.getLastRegHistory(result);

                    });
                } else {
                    dialog({ messageId: "Msg_344" }).then(() => {
                        //move to toppage
                    });
                }
            });
        }

        getLastRegHistory(userSetting: IUserSetting) {
            let self = this,
                showHistory = !userSetting ? true : userSetting.employeeCodeType === 1 ? true : false;

            if (showHistory)
                service.getLastRegHistory().done((result: IEmpRegHistory) => {
                    self.empRegHistory(new EmpRegHistory(result));
                });

        }

        getEmployeeCode(userSetting: IUserSetting): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                genType = userSetting.employeeCodeType;

            if (genType === 3 || genType === 1) {
                service.getEmployeeCode(genType === 1 ? userSetting.employeeCodeLetter : '').done((result) => {
                    self.currentEmployee().employeeCode(result);
                    dfd.resolve();
                });
            }

            return dfd.promise();
        }

        getCardNumber(userSetting: IUserSetting) {
            let self = this,
                genType = userSetting.cardNumberType,
                eployee = self.currentEmployee();

            if (genType === 1 || genType === 4) {

                service.getCardNumber(genType === 1 ? userSetting.cardNumberLetter : '').done((result) => {

                    eployee.cardNo(result);

                });
            } else {

                if (genType === 3) {

                    eployee.cardNo(eployee.employeeCode());
                }

                if (genType === 5) {

                    service.getEmployeeCodeAndComId(userSetting.employeeCodeLetter).done((result) => {

                        eployee.cardNo(result);
                    });
                }
            }

        }

        validEmployeeInfo(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                employee = self.currentEmployee();
            service.validateEmpInfo(employee.employeeCode(), employee.cardNo()).done((result) => {
                dfd.resolve(result);
            })
            return dfd.promise();
        }

        next() {
            let self = this;
            $('#emp_reg_info_wizard').ntsWizard("next");



        }

        validateForm() {
            $(".nts-editor").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        }

        completeStep1() {
            let self = this;
            if (self.validateForm()) {
                self.validEmployeeInfo().done((result) => {
                    if (result.isError) {
                        dialog({ messageId: result.messageId });

                    } else {

                        if (self.selectedId() === 3) {
                            self.gotoStep3();
                            return;
                        }

                        self.gotoStep2();
                    }
                });
            }



        }


        gotoStep1() {

            $('#emp_reg_info_wizard').ntsWizard("goto", 0);
        }

        gotoStep3() {
            $('#emp_reg_info_wizard').ntsWizard("goto", 2);


        }


        completeStep2() {
            let self = this;
            if (self.currentEmployee().employeeId) {

                dialog({ messageId: "Msg_344" });

            } else {

                $('#emp_reg_info_wizard').ntsWizard("goto", 2);

            }
        }

        isUseInitValue() {
            let self = this;

            return self.selectedId() === 2;
        }

        gotoStep2() {
            let self = this;
            $('#emp_reg_info_wizard').ntsWizard("goto", 1);

            //start Screen B
            if (!self.isUseInitValue()) {

                $('#search_panel').hide();


                service.getCopySetting().done((result: Array<ICopySetting>) => {
                    self.categoryList.removeAll();
                    if (result.length) {
                        self.categoryList(_.map(result, item => {
                            return new CategoryItem(item);
                        }));

                        self.categorySelectedId(result[0].id);
                    }
                    $('#emp_reg_info_wizard').ntsWizard("goto", 1);
                }).fail((error) => {

                    dialog({ messageId: error.message });

                });

            } else {
                //start Screen C

                $('#search_panel').show();

                service.getAllInitValueSetting().done((result: Array<IInitValueSetting>) => {
                    if (result.length) {
                        self.initValueList(_.map(result, item => {
                            return new InitValueSetting(item);
                        }));

                        self.initValueSelectedId(result[0].settingId);
                    }
                }).fail((error) => {
                    dialog({ messageId: error.message }).then(() => {
                        self.gotoStep1()
                    });

                });



            }


        }

        prev() {
            let self = this;
            if (self.selectedId() === 3) {
                $('#emp_reg_info_wizard').ntsWizard("goto", 0);
                return;
            }
            $('#emp_reg_info_wizard').ntsWizard("prev");
        }



        finish() {

            let self = this;

            nts.uk.ui.windows.sub.modal('/view/cps/002/h/index.xhtml', { title: '' }).onClosed(function(): any {
                $('#emp_reg_info_wizard').ntsWizard("goto", 0);
            });
        }

        OpenEModal(param, data) {

            let self = __viewContext['viewModel'];
            setShared("cardNoMode", param === 'true' ? true : false);
            subModal('/view/cps/002/e/index.xhtml', { title: '' }).onClosed(function(): any {

                let result = getShared("CPS002_PARAM"),
                    currentEmp = self.currentEmployee();

                param === 'true' ? currentEmp.cardNo(result) : currentEmp.employeeCode(result);
            });
        }

        OpenFModal() {

            let self = this;

            subModal('/view/cps/002/f/index.xhtml', { title: '' }).onClosed(function(): any {




            });
        }

        OpenGModal() {

            let self = this;

            subModal('/view/cps/002/g/index.xhtml', { title: '' }).onClosed(function(): any {

                if (true) {
                    service.getUserSetting().done((result: IUserSetting) => {

                        self.getLastRegHistory(result);

                    });

                }

            });
        }


        JumpToInitValueSettingPage() {

            jump('/view/cps/009/a/index.xhtml');
        }


    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class Employee {

        employeeName: KnockoutObservable<string> = ko.observable("");
        employeeCode: KnockoutObservable<string> = ko.observable("");
        hireDate: KnockoutObservable<Date> = ko.observable(new Date());
        cardNo: KnockoutObservable<string> = ko.observable("");
        employeeId: string;

        constructor(param?) {
        }
    }

    interface IUserSetting {
        employeeCodeType: number;
        recentRegistrationType: number;
        cardNumberType: number;
        employeeCodeLetter: string;
        cardNumberLetter: string;
    }

    interface IEmpRegHistory {

        registeredEmployeeID: string;

        lastRegEmployeeID: string;

    }


    interface IInitValueSetting {

        settingId: string;
        settingCode: string;
        settingName: string;

    }

    class InitValueSetting {

        settingId: string;
        settingCode: string;
        settingName: string;

        constructor(param?: IInitValueSetting) {

            this.settingId = param ? param.settingId : '';
            this.settingCode = param ? param.settingCode : '';
            this.settingName = param ? param.settingName : '';
        }

    }

    interface IInitValueCtgSetting {

        perInfoCtgId: string;
        categoryName: string;

    }


    interface ICopySetting {
        id: string;
        name: string;

    }

    class CategoryItem {
        id: string;
        name: string;
        constructor(param?: any) {
            this.id = param ? param.perInfoCtgId ? param.perInfoCtgId : param.id : '';
            this.name = param ? param.categoryName ? param.categoryName : param.name : '';

        }
    }

    class EmpRegHistory {

        registeredEmployeeID: string;

        lastRegEmployeeID: string;


        constructor(param: IEmpRegHistory) {
            this.registeredEmployeeID = param ? param.registeredEmployeeID : '';

            this.lastRegEmployeeID = param ? param.lastRegEmployeeID : '';

        }
    }



}