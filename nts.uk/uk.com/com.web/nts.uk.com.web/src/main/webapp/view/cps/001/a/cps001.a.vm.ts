module cps001.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import clearError = nts.uk.ui.errors.clearAll;

    let DEF_AVATAR = 'images/avatar.png',
        __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            //Show/hide options
            isQuickSearchTab: ko.observable(true),
            isAdvancedSearchTab: ko.observable(true),
            isAllReferableEmployee: ko.observable(true),
            isOnlyMe: ko.observable(true),
            isEmployeeOfWorkplace: ko.observable(true),
            isEmployeeWorkplaceFollow: ko.observable(true),
            isMutipleCheck: ko.observable(true),
            isSelectAllEmployee: ko.observable(true),
            onSearchAllClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onSearchOnlyClicked: (data: IEmployeeInfo) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            }
        };

        tabActive: KnockoutObservable<string> = ko.observable('layout');

        listEmployees: KnockoutObservableArray<IEmployeeInfo> = ko.observableArray([]);

        person: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo({ personId: '' }));
        employee: KnockoutObservable<EmployeeInfo> = ko.observable(new EmployeeInfo({ employeeId: '', workplaceId: '' }));

        listLayout: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        currentLayout: KnockoutObservable<Layout> = ko.observable(new Layout());

        listCategory: KnockoutObservableArray<ICategory> = ko.observableArray([]);
        currentCategory: KnockoutObservable<Category> = ko.observable(new Category({ id: '' }));

        constructor() {
            let self = this,
                person = self.person(),
                employee = self.employee(),
                layout = self.currentLayout(),
                category = self.currentCategory();

            self.tabActive.subscribe(x => {
                let employeeId = employee.employeeId()
                if (!!employeeId) {
                    if (x) {
                        // clear all error message
                        clearError();
                        if (x == 'layout') { // layout mode
                            self.listLayout.removeAll();
                            service.getAllLayout().done((data: Array<ILayout>) => {
                                if (data && data.length) {
                                    self.listLayout(data);
                                    layout.maintenanceLayoutID(data[0].maintenanceLayoutID);
                                }
                            });
                        } else { // category mode
                            self.listCategory.removeAll();
                            service.getCats(employeeId).done((data: Array<ICategory>) => {
                                self.listCategory(data);
                            });
                        }
                    }
                }
            });

            employee.employeeId.subscribe(x => {
                self.tabActive.valueHasMutated();
            });

            employee.employeeId.subscribe(x => {
                //self.tabActive.valueHasMutated();
            }, self, "beforeChange");

            employee.employeeId.valueHasMutated();

            layout.maintenanceLayoutID.subscribe(x => {
                if (x) {
                    // clear all error message
                    clearError();

                    service.getCurrentLayout(x).done((data: ILayout) => {
                        layout.layoutCode(data.layoutCode || '');
                        layout.layoutName(data.layoutName || '');

                        _.each(data.listItemClsDto, x => {
                            x.items = ko.observableArray([]);
                            if (x.layoutItemType == 0) {
                                if (x.listItemDf && x.listItemDf[0]) {
                                    _.each(x.listItemDf, m => {
                                        x.items.push({
                                            itemCode: m.itemCode,
                                            value: ko.observable('xxxx')
                                        });
                                    });
                                }
                            } else {
                                _.each(Array(1), (_x, i) => {
                                    let rows = [];
                                    _.each(x.listItemDf, (m, j) => {
                                        rows.push({
                                            itemCode: m.itemCode,
                                            value: ko.observable('xxxx')
                                        });
                                    });
                                    x.items.push(rows);
                                });
                            }
                        });

                        layout.listItemClsDto(data.listItemClsDto || []);
                    });

                }
            });

            self.start();
        }

        start() {
            let self = this;

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                $('.btn-quick-search[tabindex=4]').click();
            });
        }

        deleteEmployee() {
            let self = this;

            modal('../b/index.xhtml').onClosed(() => { });
        }

        chooseAvatar() {
            let self = this,
                employee: IEmployeeInfo = ko.toJS(self.employee);
            
            // cancel click if hasn't emp
            if (!employee || !employee.employeeId) {
                return;
            }
            
            setShared("employeeId", employee.employeeId);
            modal('../d/index.xhtml').onClosed(() => {
                let data = getShared("imageId");
                debugger;
            });
        }

        unManagerEmployee() {
            let self = this;

            modal('../c/index.xhtml').onClosed(() => { });
        }

        pickLocation() {
            let self = this;

            modal('../e/index.xhtml').onClosed(() => { });
        }

        uploadebook() {
            let self = this;

            modal('../f/index.xhtml').onClosed(() => { });
        }

        saveData() {
            let self = this;

            // push data layout to webservice
            block();
            service.saveData({}).done(() => {
                self.start();
                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                });
            }).fail((mes) => {
                unblock();
                alert(mes.message);
            });
        }
    }

    interface ILayout {
        layoutCode?: string;
        layoutName?: string;
        maintenanceLayoutID: string;
        listItemClsDto?: Array<any>;
    }

    class Layout {
        layoutCode: KnockoutObservable<string> = ko.observable('');
        layoutName: KnockoutObservable<string> = ko.observable('');
        maintenanceLayoutID: KnockoutObservable<string> = ko.observable('');
        listItemClsDto: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param?: ILayout) {
            let self = this;
            if (param) {
                self.layoutCode(param.layoutCode || '');
                self.layoutName(param.layoutName || '');
                self.maintenanceLayoutID(param.maintenanceLayoutID || '');

                self.listItemClsDto(param.listItemClsDto || []);
            }
        }
    }

    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: number;
        isFixed?: number;
    }

    class Category {
        id: KnockoutObservable<string> = ko.observable('');
        categoryCode: KnockoutObservable<string> = ko.observable('');
        categoryName: KnockoutObservable<string> = ko.observable('');
        categoryType: KnockoutObservable<number> = ko.observable(0);
        isFixed: KnockoutObservable<number> = ko.observable(0);

        constructor(param: ICategory) {
            let self = this;

            if (param) {
                self.id(param.id);
                self.categoryCode(param.categoryCode);
                self.categoryName(param.categoryName);
                self.categoryType(param.categoryType);
                self.isFixed(param.isFixed);
            }
        }
    }

    interface IEmployeeInfo {
        employeeId: string;
        text?: string;
        employeeCode?: string;
        employeeName?: string;
        workplaceId: string;
        workplaceCode?: string;
        workplaceName?: string;
    }

    class EmployeeInfo {
        employeeId: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        workplaceId: KnockoutObservable<string> = ko.observable('');
        workplaceCode: KnockoutObservable<string> = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IEmployeeInfo) {
        }
    }

    interface IPersonInfo {
        personId: string;
        birthDate?: Date;
        gender?: number;
        countryId?: number;
        mailAddress?: string;
        personMobile?: string;
        code?: string;
        avatar?: string;
        fullName?: string;
    }

    class PersonInfo {
        personId: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        fullName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IPersonInfo) {
            let self = this;

            self.personId(param.personId || '');
            self.code(param.code || '');
            self.avatar(param.avatar || DEF_AVATAR);
            self.fullName(param.fullName || '');
        }
    }
}