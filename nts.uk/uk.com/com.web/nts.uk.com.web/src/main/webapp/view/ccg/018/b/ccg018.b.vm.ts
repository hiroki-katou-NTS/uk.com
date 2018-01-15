module ccg018.b.viewmodel {
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import blockUI = nts.uk.ui.block;

    export class ScreenModel extends base.viewModel.ScreenModelBase {
        items: KnockoutObservableArray<TopPagePersonSet>;
        selectedItem: KnockoutObservable<TopPagePersonSet>;
        currentCode: KnockoutObservable<any>;
        selectedItemAfterLogin: KnockoutObservable<string>;
        selectedItemAsTopPage: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        employeeName: KnockoutObservable<string>;
        isVisible: KnockoutObservable<boolean>;
        isEnable: KnockoutObservable<boolean>;
        categorySet: KnockoutObservable<any>;

        listSid: Array<any>;
        isSelectedFirst: KnockoutObservable<boolean>;
        
        isEmpty: KnockoutObservable<boolean>;

        //component
        ccgcomponent: GroupOption;
        showinfoSelectedEmployee: KnockoutObservable<boolean>;

        // Options
        baseDate: KnockoutObservable<Date>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

        constructor(baseModel: base.result.BaseResultModel) {
            super(baseModel);
            let self = this;
            self.screenTemplateUrl("../b/index.xhtml");
            self.comboItemsAfterLogin(baseModel.comboItemsAfterLogin);
            self.comboItemsAsTopPage(baseModel.comboItemsAsTopPage);
            self.categorySet(baseModel.categorySet);
            self.items = ko.observableArray([]);
            self.selectedItem = ko.observable(null);
            self.listSid = [];
            self.currentCode = ko.observable();
            self.employeeCode = ko.observable('');
            self.employeeName = ko.observable('');
            self.selectedItemAfterLogin = ko.observable('');
            self.selectedItemAsTopPage = ko.observable('');
            self.isVisible = ko.computed(function() {
                return !!self.categorySet();
            });

            self.isEnable = ko.observable(false);
            self.isSelectedFirst = ko.observable(true);

            self.currentCode.subscribe(function(codeChange: any) {
                if (!!self.currentCode()) {
                    self.employeeCode(codeChange);
                    self.selectedItem(_.find(self.items(), ['code', codeChange]));
                    self.employeeName(self.selectedItem().name);
                    self.selectedItemAfterLogin(self.selectedItem().uniqueCode());
                    self.selectedItemAsTopPage(self.selectedItem().topPageCode());
                    self.isEnable(_.find(self.items(), ['code', self.currentCode()]).isAlreadySetting);
                } else {
                    self.employeeCode('');
                    self.employeeName('');
                    self.selectedItemAfterLogin('');
                    self.selectedItemAsTopPage('');
                    self.isEnable(false);
                }
            });

            //component
            self.selectedEmployee = ko.observableArray([]);
            self.showinfoSelectedEmployee = ko.observable(false);
            self.baseDate = ko.observable(new Date());

            self.selectedEmployee.subscribe(function() {
                self.listSid = [];
                _.each(self.selectedEmployee(), function(x) {
                    self.listSid.push(x.employeeId);
                });
                self.findTopPagePersonSet();
            });
            
            self.isEmpty = ko.computed(function(){
                return !nts.uk.ui.errors.hasError();    
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            $.when(self.findTopPagePersonSet()).done(function() {
                self.bindGrid();
                self.bindCCG001();
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject(error);
            });
            return dfd.promise();
        }

        
        bindCCG001(): void {
            let self = this;   
            self.ccgcomponent = {
                baseDate: self.baseDate,
                //Show/hide options
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: true,

                //Event options 
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
                onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(dataList);
                },
                onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                    self.showinfoSelectedEmployee(true);
                    var dataEmployee: EmployeeSearchDto[] = [];
                    dataEmployee.push(data);
                    self.selectedEmployee(dataEmployee);
                },
                onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(dataList);
                },
                onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(dataList);
                },
                onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                    self.showinfoSelectedEmployee(true);
                    self.selectedEmployee(dataEmployee);
                }
            }
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        }
        
        bindGrid(): any {
            let self = this;
            let listComponentOption: any = {
                isShowAlreadySet: true,
                alreadySettingList: self.items,
                isMultiSelect: false,
                listType: 4,
                isShowWorkPlaceName: true,
                selectedCode: self.currentCode,
                isShowNoSelectRow: false,
                isDialog: false,
                selectType: 4,
                isShowSelectAllButton: false,
                employeeInputList: self.items
            };
            $('#sample-component').ntsListComponent(listComponentOption);
        }

        findTopPagePersonSet(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.findTopPagePersonSet(self.listSid)
                .done(function(data) {
                    self.items([]);
                    let arr = [];
                    _.each(self.selectedEmployee(), function(x) {
                        let topPagePersonSet: any = _.find(data, ['sid', x.employeeId]);
                        if (!!topPagePersonSet) {
                            arr.push(new TopPagePersonSet({
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceName: x.workplaceName,
                                employeeId: x.employeeId,
                                topPageCode: topPagePersonSet.topMenuCode,
                                loginMenuCode: topPagePersonSet.loginMenuCode,
                                system: topPagePersonSet.loginSystem,
                                menuClassification: topPagePersonSet.menuClassification,
                                isAlreadySetting: true
                            }));
                        } else {
                            arr.push(new TopPagePersonSet({
                                code: x.employeeCode,
                                name: x.employeeName,
                                workplaceName: x.workplaceName,
                                employeeId: x.employeeId,
                                topPageCode: '',
                                loginMenuCode: '',
                                system: 0,
                                menuClassification: 0,
                                isAlreadySetting: false
                            }));
                        }
                    });
                    self.items(arr);
                    if (self.isSelectedFirst() && self.items().length > 0) {
                        self.currentCode(self.items()[0].code);
                    }
                    self.isSelectedFirst(true);
                    dfd.resolve();
                }).fail(function(){
                        dfd.reject();    
                    });
            return dfd.promise();
        }

        /**
         * Update/Insert data in to table TOPPAGE_PERSON_SET
         */
        save(): void {
            let self = this;
            if (!self.currentCode()) {
                return;
            }
            blockUI.invisible();
            let oldCode = self.selectedItem().code;
            let obj = {
                ctgSet: self.categorySet(),
                sId: self.selectedItem().employeeId,
                topMenuCode: self.selectedItemAsTopPage(),
                loginMenuCode: !!self.categorySet() ? (self.selectedItemAfterLogin().length == 6 ? self.selectedItemAfterLogin().slice(0, 4) : '') : self.selectedItemAsTopPage(),
                loginSystem: !!self.categorySet() ? self.selectedItemAfterLogin().slice(-2, -1) : 0,
                loginMenuCls: !!self.categorySet() ? self.selectedItemAfterLogin().slice(-1) : 8,
            };
            ccg018.b.service.update(obj).done(function() {
                self.isSelectedFirst(false);
                $.when(self.findTopPagePersonSet()).done(function() {
                    self.currentCode(oldCode);
                    self.selectedItemAfterLogin(obj.loginMenuCode + obj.loginSystem + obj.loginMenuCls);
                    self.isEnable(true);
                    nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                });
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            }).always(function() {
                blockUI.clear();
            });
        }

        /**
         * remove data in to table TOPPAGE_PERSON_SET
         */
        removeData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            if (!!!self.currentCode()) {
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_85'));
            } else {
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage('Msg_18')).ifYes(() => {
                    let obj = { sId: self.selectedItem().employeeId };
                    ccg018.b.service.remove(obj).done(function() {
                        self.isSelectedFirst(false);
                        $.when(self.findTopPagePersonSet()).done(function() {
                            self.isEnable(false);
                            self.selectedItemAfterLogin('');
                            self.selectedItemAsTopPage('');
                            nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_16'));
                        });
                    }).fail(function(){
                        dfd.reject();    
                    });
                }).ifNo(() => { });
            }
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            let self = this;
            blockUI.invisible();
            nts.uk.ui.windows.setShared('categorySet', self.categorySet());
            nts.uk.ui.windows.sub.modal('/view/ccg/018/c/index.xhtml', { dialogClass: 'no-close' }).onClosed(() => {
                if (nts.uk.ui.windows.getShared('categorySetC') != undefined) {
                    if (self.categorySet() != nts.uk.ui.windows.getShared('categorySetC')) {
                        self.categorySet(nts.uk.ui.windows.getShared('categorySetC'));
                    }
                }
            });
            blockUI.clear();
        }

    }

    interface ITopPagePersonSet {
        code: string,
        name: string,
        workplaceName: string,
        employeeId: string,
        topPageCode: string,
        loginMenuCode: string,
        system: number,
        menuClassification: number,
        isAlreadySetting: boolean
    }

    class TopPagePersonSet {
        code: string;
        name: string;
        workplaceName: string;
        employeeId: string;
        topPageCode: KnockoutObservable<string>;
        loginMenuCode: KnockoutObservable<string>;
        isAlreadySetting: boolean;
        system: KnockoutObservable<number>;
        menuClassification: KnockoutObservable<number>;
        //beacause there can exist same code, so create uniqueCode = loginMenuCd+ system+ menuClassification
        uniqueCode: KnockoutObservable<string> = ko.observable('');

        constructor(param: ITopPagePersonSet) {
            let self = this;

            self.code = param.code;
            self.name = param.name;
            self.workplaceName = param.workplaceName;
            self.employeeId = param.employeeId;
            self.topPageCode = ko.observable(param.topPageCode);
            self.loginMenuCode = ko.observable(param.loginMenuCode);
            self.isAlreadySetting = param.isAlreadySetting;
            self.system = ko.observable(param.system);
            self.menuClassification = ko.observable(param.menuClassification);
            self.uniqueCode(nts.uk.text.format("{0}{1}{2}", param.loginMenuCode, param.system, param.menuClassification));
        }
    }

}