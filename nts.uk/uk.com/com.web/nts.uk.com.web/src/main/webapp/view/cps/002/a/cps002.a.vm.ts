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

        createTypeList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, text('CPS002_26')),
            new BoxModel(2, text('CPS002_27')),
            new BoxModel(3, text('CPS002_28'))
        ]);

        categoryList: KnockoutObservableArray<CategoryItem> = ko.observableArray([]);

        initValueList: KnockoutObservableArray<SelectedItem> = ko.observableArray([]);

        itemList: KnockoutObservableArray<Item> = ko.observableArray([]);

        selectedId: KnockoutObservable<number> = ko.observable(1);

        enable: KnockoutObservable<boolean> = ko.observable(true);

        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());

        categorySelectedCode: KnockoutObservable<string> = ko.observable('');

        empRegHistory: KnockoutObservable<EmpRegHistory> = ko.observable(new EmpRegHistory(null));

        currentStep: KnockoutObservable<number> = ko.observable(0);

        initValueSelectedCode: KnockoutObservable<string> = ko.observable('');

        currentItem: KnockoutObservable<SelectedItem> = ko.observable(new SelectedItem(null));;

        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            isQuickSearchTab: true,
            isAdvancedSearchTab: true,
            isAllReferableEmployee: true,
            isOnlyMe: true,
            isEmployeeOfWorkplace: true,
            isEmployeeWorkplaceFollow: true,
            isMutipleCheck: false,
            isSelectAllEmployee: false,
            onApplyEmployee: (dataEmployee: Array<any>) => {
                let self = this;
                self.currentItem(new SelectedItem(dataEmployee[0]));
            }
        };

        constructor() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

            self.selectedId.subscribe((newValue) => {

            });

            self.initValueSelectedCode.subscribe((newValue) => {

                let selectedItem = _.find(self.initValueList(), item => {
                    return item.itemCode == newValue;
                });

                service.getAllInitValueCtgSetting(selectedItem.itemId).done((result: Array<IInitValueCtgSetting>) => {
                    if (result.length) {
                        self.categoryList(_.map(result, item => {
                            return new CategoryItem(item);
                        }));

                        self.categorySelectedCode('');
                        self.categorySelectedCode(result[0].categoryCd);
                    } else {
                        self.categoryList.removeAll();
                    }
                });

                self.currentItem(selectedItem);

            });



            self.categorySelectedCode.subscribe((newValue) => {

                if (newValue == '') {
                    return;
                }

                if (self.isUseInitValue()) {
                    service.getAllInitValueItemSetting(self.currentItem().itemId, newValue, self.currentEmployee().hireDate()).done((result: Array<Item>) => {
                        if (result.length) {
                            self.itemList(_.map(result, item => {
                                return new Item(item);
                            }));
                        } else {
                            self.itemList.removeAll();
                        }
                    });
                } else {


                }
            });

            self.currentEmployee().avatarId.subscribe((newValue) => {

                //set avatar
                //  $("#employeeAvatar").ntsImageEditor("selectByFileId", newValue);

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

            let self = this;

            self.currentStep(0);
        }

        gotoStep3() {

            let self = this;

            self.currentStep(2);
            service.getSelfRoleAuth().done((result: IRoleAuth) => {

                if (result.allowAvatarUpload) {
                    //if allowAvatarUpload
                }

            });


        }


        completeStep2() {
            let self = this;
            if (!self.currentEmployee().employeeId && !self.isUseInitValue()) {

                dialog({ messageId: "Msg_349" });

            } else {

                self.gotoStep3();

            }
        }

        isUseInitValue() {
            let self = this;

            return self.selectedId() === 2;
        }

        gotoStep2() {
            let self = this;

            self.currentStep(1);


            if (self.isUseInitValue()) {

                //start Screen C

                $('#search_panel').show();

                self.loadInitValueData();

            } else {

                //start Screen B

                $('#search_panel').hide();

                self.loadCopySettingData();

            }


        }

        loadCopySettingData() {

            let self = this;

            service.getCopySetting().done((result: Array<ICopySetting>) => {
                self.categoryList.removeAll();
                if (result.length) {
                    self.categoryList(_.map(result, item => {
                        return new CategoryItem(item);
                    }));

                    self.categorySelectedCode(result[0].code);
                }

            }).fail((error) => {

                dialog({ messageId: error.message }).then(() => {

                    // self.gotoStep1();

                });

            });

        }

        loadInitValueData() {

            let self = this;
            service.getAllInitValueSetting().done((result: Array<IInitValueSetting>) => {
                if (result.length) {
                    self.initValueList(_.map(result, item => {
                        return new SelectedItem(item);
                    }));

                    let lastValueItem = _.find(result, (item) => {

                        return item.settingCode == self.currentEmployee().initvalueCode;
                    });


                    self.initValueSelectedCode(lastValueItem ? lastValueItem.settingCode : result[0].settingCode);


                }
            }).fail((error) => {
                dialog({ messageId: error.message }).then(() => {
                    //   self.gotoStep1();
                });

            });

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

            nts.uk.ui.windows.sub.modal('/view/cps/002/h/index.xhtml', { title: '' }).onClosed(() => {
                if (getShared('isContinue')) {

                    self.gotoStep1();

                } else {
                    jump('/view/cps/001/a/index.xhtml');
                }
            });
        }

        OpenEModal(param, data) {

            let self = __viewContext['viewModel'];
            setShared("cardNoMode", param === 'true' ? true : false);
            subModal('/view/cps/002/e/index.xhtml', { title: '' }).onClosed(() => {

                let result = getShared("CPS002_PARAM"),
                    currentEmp = self.currentEmployee();

                param === 'true' ? currentEmp.cardNo(result) : currentEmp.employeeCode(result);
            });
        }

        OpenFModal() {

            let self = this;

            subModal('/view/cps/002/f/index.xhtml', { title: '' }).onClosed(() => {

            });
        }

        OpenGModal() {

            let self = this;

            subModal('/view/cps/002/g/index.xhtml', { title: '' }).onClosed(() => {

                if (getShared("userSettingStatus")) {
                    service.getUserSetting().done((result: IUserSetting) => {

                        self.getLastRegHistory(result);

                    });

                }

            });
        }

        OpenIModal() {
            let self = this;
            setShared("imageId", self.currentEmployee().avatarId());

            subModal('/view/cps/002/i/index.xhtml', { title: '' }).onClosed(() => {

                let imageResult = getShared("imageId");

                if (imageResult) {
                    self.currentEmployee().avatarId(imageResult);
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
        initvalueCode: string;
        avatarId: KnockoutObservable<string> = ko.observable("");

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

    class SelectedItem {

        itemId: string = '';
        itemCode: string = '';
        itemName: string = '';

        constructor(param?: any) {

            this.itemId = param ? param.settingId ? param.settingId : param.employeeId : '';
            this.itemCode = param ? param.settingCode ? param.settingCode : param.employeeCode : '';
            this.itemName = param ? param.settingName ? param.settingName : param.employeeName : '';
        }

    }




    interface IInitValueCtgSetting {

        categoryCd: string;
        categoryName: string;

    }


    interface ICopySetting {
        code: string;
        name: string;

    }

    class CategoryItem {
        code: string;
        name: string;
        constructor(param?: any) {
            this.code = param ? param.categoryCd ? param.categoryCd : param.code : '';
            this.name = param ? param.categoryName ? param.categoryName : param.name : '';

        }
    }

    class Item {
        itemName: string;
        isRequired: number;
        saveData: any;
        constructor(param?: any) {
            this.itemName = param ? param.itemName ? param.itemName : param.id : '';
            this.isRequired = param ? param.isRequired ? param.isRequired : param.name : 0;
            this.saveData = param ? param.saveData ? param.saveData : param.id : 0;
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

    interface IRoleAuth {
        allowMapUpload: number;
        allowMapBrowse: number;
        allowDocRef: number;
        allowDocUpload: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;

    }



}