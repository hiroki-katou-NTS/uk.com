module cps002.a.vm {
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    import subModal = nts.uk.ui.windows.sub.modal;
    import jump = nts.uk.request.jump;
    import liveView = nts.uk.request.liveView;

    export class ViewModel {

        date: KnockoutObservable<Date> = ko.observable(new Date());

        createTypeList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, text('CPS002_26')),
            new BoxModel(2, text('CPS002_27')),
            new BoxModel(3, text('CPS002_28'))
        ]);

        categoryList: KnockoutObservableArray<CategoryItem> = ko.observableArray([]);

        initValueList: KnockoutObservableArray<InitSetting> = ko.observableArray([]);

        itemSettingList: KnockoutObservableArray<SettingItem> = ko.observableArray([]);

        createTypeId: KnockoutObservable<number> = ko.observable(1);

        enable: KnockoutObservable<boolean> = ko.observable(true);

        currentEmployee: KnockoutObservable<Employee> = ko.observable(new Employee());

        categorySelectedCode: KnockoutObservable<string> = ko.observable('');

        empRegHistory: KnockoutObservable<EmpRegHistory> = ko.observable(new EmpRegHistory(null));

        currentStep: KnockoutObservable<number> = ko.observable(0);

        initSettingSelectedCode: KnockoutObservable<string> = ko.observable('');

        currentInitSetting: KnockoutObservable<InitSetting> = ko.observable(new InitSetting(null));

        copyEmployee: KnockoutObservable<EmployeeCopy> = ko.observable(new EmployeeCopy(null));

        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: '', name: '' }));

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
                self.copyEmployee(new EmployeeCopy(dataEmployee[0]));
            }
        };

        constructor() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

            self.createTypeId.subscribe((newValue) => {
                self.initValueList([]);
                self.categoryList([]);
                self.itemSettingList([]);
                self.categorySelectedCode('');
                self.initSettingSelectedCode('');
                self.currentInitSetting(new InitSetting(null));
              //  self.layout(new Layout({ id: '', code: '', name: '' }));

            });

            self.initSettingSelectedCode.subscribe((initCode) => {
                if (initCode === '') {
                    return;
                }

                let InitSetting = _.find(self.initValueList(), item => {
                    return item.itemCode == initCode;
                });

                service.getAllInitValueCtgSetting(InitSetting.itemId).done((result: Array<IInitValueCtgSetting>) => {
                    if (result.length) {
                        self.categoryList(_.map(result, item => {
                            return new CategoryItem(item);
                        }));

                        self.categorySelectedCode(result[0].categoryCd);
                    } else {
                        self.categoryList.removeAll();
                    }
                });

                self.currentInitSetting(InitSetting);

            });

            self.copyEmployee.subscribe((CopyEmployee) => {

                self.loadCopySettingItemData();

            });



            self.categorySelectedCode.subscribe((categoryCode) => {

                if (categoryCode == '') {
                    return;
                }
                self.itemSettingList.removeAll();
                if (self.isUseInitValue()) {
                    service.getAllInitValueItemSetting(self.currentInitSetting().itemId, categoryCode, nts.uk.time.formatDate(self.currentEmployee().hireDate(), 'yyyyMMdd')).done((result: Array<SettingItem>) => {
                        if (result.length) {
                            self.itemSettingList(_.map(result, item => {
                                return new SettingItem(item);
                            }));
                        }
                    });
                } else {

                    self.loadCopySettingItemData();


                }
            });

            self.currentEmployee().avatarId.subscribe((avartarId) => {

                var self = this;
                var avartarContent = $("#employeeAvatar");
                avartarContent.html("");
                avartarContent.append($("<img/>").attr("src", liveView(avartarId)));
                avartarContent.append($("<iframe/>").attr("src", liveView(avartarId)));


            });

            self.start();
        }

        loadCopySettingItemData() {

            let self = this,
                currentCopyEmployeeId = self.copyEmployee().employeeId;

            if (currentCopyEmployeeId != "") {
                service.getAllCopySettingItem(currentCopyEmployeeId, self.categorySelectedCode(), nts.uk.time.formatDate(self.currentEmployee().hireDate(), 'yyyyMMdd')).done((result: Array<SettingItem>) => {
                    if (result.length) {
                        self.itemSettingList(_.map(result, item => {
                            return new SettingItem(item);
                        }));
                    }
                });
            }
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

        validateStep1() {
            $(".form_step1").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            return true;
        }

        completeStep1() {
            let self = this;
            if (self.validateStep1()) {
                self.validEmployeeInfo().done((result) => {
                    if (result.isError) {
                        dialog({ messageId: result.messageId });

                    } else {

                        if (self.createTypeId() === 3) {

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
            let self = this,
                command = {
                    createType: self.createTypeId(),

                    initSettingId: self.initSettingSelectedCode(),

                    baseDate: self.currentEmployee().hireDate(),

                    employeeId: self.copyEmployee().employeeId

                },
                layout = self.layout();

            self.currentStep(2);


            service.getLayoutByCreateType(command).done((x: ILayout) => {
                layout.id(x.id);
                layout.code(x.code);
                layout.name(x.name);

                // remove all sibling sperators
                let maps = _(x.itemsClassification)
                    .map((x, i) => (x.layoutItemType == 2) ? i : -1)
                    .filter(x => x != -1).value();

                _.each(maps, (t, i) => {
                    if (maps[i + 1] == t + 1) {
                        _.remove(x.itemsClassification, (m: IItemClassification) => {
                            let item: IItemClassification = ko.unwrap(x.itemsClassification)[maps[i + 1]];
                            return item && item.layoutItemType == 2 && item.layoutID == m.layoutID;
                        });
                    }
                });

                layout.itemsClassification(x.itemsClassification);
            });



            service.getSelfRoleAuth().done((result: IRoleAuth) => {

                if (result.allowAvatarUpload) {
                    //if allowAvatarUpload
                }

            });


        }


        completeStep2() {
            let self = this;
            if (self.copyEmployee().employeeId === '' && !self.isUseInitValue()) {

                dialog({ messageId: "Msg_349" });

            } else {

                self.gotoStep3();

            }
        }

        isUseInitValue() {
            let self = this;

            return self.createTypeId() === 2;
        }

        gotoStep2() {
            let self = this;

            self.currentStep(1);


            if (self.isUseInitValue()) {

                //start Screen C

                $('#search_panel').show();

                self.loadInitSettingData();

            } else {

                //start Screen B

                $('#search_panel').hide();

                self.loadCopySettingCtgData();

            }


        }

        loadCopySettingCtgData() {

            let self = this;
            self.categoryList.removeAll();

            service.getCopySetting().done((result: Array<ICopySetting>) => {
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

        loadInitSettingData() {

            let self = this;
            self.initValueList.removeAll();
            service.getAllInitValueSetting().done((result: Array<IInitValueSetting>) => {
                if (result.length) {
                    self.initValueList(_.map(result, item => {
                        return new InitSetting(item);
                    }));

                    let lastValueItem = _.find(result, (item) => {

                        return item.settingCode == self.currentEmployee().initvalueCode;
                    });


                    self.initSettingSelectedCode(lastValueItem ? lastValueItem.settingCode : result[0].settingCode);


                }
            }).fail((error) => {
                dialog({ messageId: error.message }).then(() => {
                    //   self.gotoStep1();
                });

            });

        }

        prev() {
            let self = this;
            if (self.createTypeId() === 3) {
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

        openEModal(param, data) {

            let self = __viewContext['viewModel'];
            setShared("cardNoMode", param === 'true' ? true : false);
            subModal('/view/cps/002/e/index.xhtml', { title: '' }).onClosed(() => {

                let result = getShared("CPS002_PARAM"),
                    currentEmp = self.currentEmployee();

                param === 'true' ? currentEmp.cardNo(result) : currentEmp.employeeCode(result);
            });
        }

        openFModal() {

            let self = this;

            subModal('/view/cps/002/f/index.xhtml', { title: '' }).onClosed(() => {

            });
        }

        openGModal() {

            let self = this;

            subModal('/view/cps/002/g/index.xhtml', { title: '' }).onClosed(() => {

                if (getShared("userSettingStatus")) {
                    service.getUserSetting().done((result: IUserSetting) => {

                        self.getLastRegHistory(result);

                    });

                }

            });
        }

        openIModal() {
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
        initvalueCode: string;
        avatarId: KnockoutObservable<string> = ko.observable("");

        constructor(param?) {
        }
    }


    class EmployeeCopy {
        employeeId: string;
        employeeName: string;
        employeeCode: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;

        constructor(param?: any) {
            this.employeeId = param ? param.employeeId : '';
            this.employeeName = param ? param.employeeName : '';
            this.employeeCode = param ? param.employeeCode : '';
            this.workplaceCode = param ? param.workplaceCode : '';
            this.workplaceId = param ? param.workplaceId : '';
            this.workplaceName = param ? param.workplaceName : '';
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

    class InitSetting {

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

    class SettingItem {
        itemCode: string
        itemName: string;
        isRequired: number;
        saveData: any;
        constructor(param?: any) {
            this.itemCode = param ? param.itemCode : '';
            this.itemName = param ? param.itemName : '';
            this.isRequired = param ? param.isRequired : 0;
            this.saveData = param ? param.saveData : null;
        }
    }

    interface IItemClassification {
        layoutID?: string;
        dispOrder?: number;
        className?: string;
        personInfoCategoryID?: string;
        layoutItemType: number;
        listItemDf: Array<IItemDefinition>;
    }

    interface IItemDefinition {
        id: string;
        perInfoCtgId?: string;
        itemCode?: string;
        itemName: string;
    }

    interface ILayout {
        id: string;
        code: string;
        name: string;
        editable?: boolean;
        itemsClassification?: Array<IItemClassification>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        editable: KnockoutObservable<boolean> = ko.observable(true);
        itemsClassification: KnockoutObservableArray<IItemClassification> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;

            self.id(param.id);
            self.code(param.code);
            self.name(param.name);

            if (param.editable != undefined) {
                self.editable(param.editable);
            }

            // replace x by class that implement this interface
            self.itemsClassification(param.itemsClassification || []);
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