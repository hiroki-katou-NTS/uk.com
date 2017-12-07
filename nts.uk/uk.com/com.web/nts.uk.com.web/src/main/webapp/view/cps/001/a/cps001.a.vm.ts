module cps001.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import clearError = nts.uk.ui.errors.clearAll;
    import liveView = nts.uk.request.liveView;
    import permision = service.getCurrentEmpPermision;
    import format = nts.uk.text.format;

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

                self.listEmployee.removeAll();
                self.listEmployee(dataList);
            },
            onSearchOnlyClicked: (data: IEmployeeInfo) => {
                let self = this;

                self.listEmployee.removeAll();
                self.listEmployee([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployee.removeAll();
                self.listEmployee(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployee.removeAll();
                self.listEmployee(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployee.removeAll();
                self.listEmployee(dataList);
            }
        };

        // permision of current employee (current login).
        auth: KnockoutObservable<PersonAuth> = ko.observable(new PersonAuth());

        // current tab active id (layout/category)
        tabActive: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);

        // for employee info.
        listEmployee: KnockoutObservableArray<IEmployeeInfo> = ko.observableArray([]);
        employee: KnockoutObservable<EmployeeInfo> = ko.observable(new EmployeeInfo({ employeeId: '', workplaceId: '' }));

        person: KnockoutComputed<PersonInfo> = ko.computed(() => {
            let self = this,
                employee = self.employee();
            return employee.personInfo();
        });

        // for case: layout
        listLayout: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        currentLayout: KnockoutObservable<Layout> = ko.observable(new Layout());
        // for case: category
        categoryLayout: KnockoutObservableArray<Layout> = ko.observableArray([new Layout()]);

        // for case: combobox
        listCategory: KnockoutObservableArray<ICategory> = ko.observableArray([]);
        currentCategory: KnockoutObservable<Category> = ko.observable(new Category({ id: '' }));

        // for case: category with childs
        listTabCategory: KnockoutObservableArray<ICategory> = ko.observableArray([]);
        currentTabCategory: KnockoutObservable<string> = ko.observable('cat1');

        // resource id for title in category mode
        titleResource: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this,
                auth = self.auth(),
                employee = self.employee(),
                person = employee.personInfo(),
                layout = self.currentLayout(),
                category = self.currentCategory();

            permision().done((data: IPersonAuth) => {
                if (data) {
                    auth.allowDocRef(!!data.allowDocRef);
                    auth.allowAvatarRef(!!data.allowAvatarRef);
                    auth.allowMapBrowse(!!data.allowMapBrowse);
                } else {
                    auth.allowAvatarRef(false);
                    auth.allowAvatarRef(false);
                    auth.allowMapBrowse(false);
                }
            });

            self.tabActive.subscribe(tab => {
                let employeeId: string = employee.employeeId();
                if (!!employeeId) {
                    // clear all error message
                    clearError();
                    switch (tab) {
                        default:
                        case TABS.LAYOUT: // layout mode
                            self.listLayout.removeAll();
                            layout.maintenanceLayoutID(undefined);
                            service.getAllLayout(employeeId).done((data: Array<ILayout>) => {
                                if (data && data.length) {
                                    self.listLayout(data);
                                    layout.maintenanceLayoutID(data[0].maintenanceLayoutID);
                                }
                            });
                            break;
                        case TABS.CATEGORY: // category mode
                            self.listCategory.removeAll();
                            service.getCats(employeeId).done((data: Array<ICategory>) => {
                                self.listCategory(data);
                            });
                            layout.maintenanceLayoutID(undefined);
                            break;
                    }
                }
            });

            employee.employeeId.subscribe(id => {
                if (id) {
                    self.tabActive.valueHasMutated();
                }
            });

            employee.employeeId.subscribe(id => {
                //self.tabActive.valueHasMutated();
            }, self, "beforeChange");

            employee.employeeId.valueHasMutated();

            layout.maintenanceLayoutID.subscribe(id => {
                if (id) {
                    // clear all error message
                    clearError();
                    let query: ILayoutQuery = {
                        layoutId: id,
                        browsingEmpId: employee.employeeId(),
                        standardDate: moment.utc(layout.standardDate()).toDate()
                    };

                    service.getCurrentLayout(query).done((data: ILayout) => {
                        layout.layoutCode(data.layoutCode || '');
                        layout.layoutName(data.layoutName || '');

                        if (data.standardDate) {
                            layout.standardDate(data.standardDate);
                        }

                        layout.listItemClsDto(data.classificationItems || []);
                    });

                } else {
                    layout.layoutCode(undefined);
                    layout.layoutName(undefined);
                    layout.standardDate(undefined);
                    layout.listItemClsDto([]);
                }
            });

            category.id.subscribe(id => {
                if (id) {
                    let query = {
                        categoryId: id,
                        employeeId: employee.employeeId(),
                        standardDate: moment.utc(),
                        categoryCode: undefined,
                        personId: undefined,
                        infoId: undefined
                    };
                    let cat = _.find(self.listCategory(), x => x.id == id);
                    if (cat) {
                        category.categoryCode(cat.categoryCode);
                        category.categoryName(cat.categoryName);
                        category.categoryType(cat.categoryType);
                        category.isFixed(cat.isFixed);
                        service.getCatData(query).done(data => {
                            //layout.listItemClsDto.removeAll();
                            layout.listItemClsDto(data.classificationItems);
                        });
                    }
                }
            });

            self.start();
        }

        start() {
            let self = this,
                employee = self.employee();

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                $('.btn-quick-search[tabindex=4]').click();
                setInterval(() => {
                    if (!employee.employeeId()) {
                        let employees = self.listEmployee()
                        employee.employeeId(employees[0] ? employees[0].employeeId : undefined);
                    }
                }, 0);
            });
        }

        deleteEmployee() {
            let self = this,
                emp = self.employee(),
                person = self.person();

            setShared('CPS001B_PARAM', { sid: emp.employeeId(), pid: person.personId() });
            modal('../b/index.xhtml').onClosed(() => { });
        }

        chooseAvatar() {
            let self = this,
                employee: EmployeeInfo = self.employee(),
                iemp: IEmployeeInfo = ko.toJS(employee);

            // cancel click if hasn't emp
            if (!iemp || !iemp.employeeId) {
                return;
            }

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowAvatarUpload) {
                    setShared("CPS001D_PARAMS", iemp);
                    modal('../d/index.xhtml').onClosed(() => {
                        let data = getShared("CPS001D_VALUES");
                        employee.avatar(data.fileId ? liveView(data.fileId) : undefined);
                    });
                }
            });
        }

        unManagerEmployee() {
            let self = this;

            modal('../c/index.xhtml').onClosed(() => { });
        }

        pickLocation() {
            let self = this;

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowMapBrowse) {
                    modal('../e/index.xhtml').onClosed(() => { });
                }
            });
        }

        uploadebook() {
            let self = this;

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowDocRef) {
                    modal('../f/index.xhtml').onClosed(() => { });
                }
            });
        }

        saveData() {
            let self = this,
                emp = self.employee(),
                person = emp.personInfo(),
                layout = self.currentLayout(),
                inputs: Array<IPeregItemCommand> = _(layout.listItemClsDto())
                    .map(x => x.items())
                    .flatten()
                    .groupBy("categoryCode")
                    .map(items => {
                        return {
                            recordId: <string>undefined,
                            categoryCd: <string>items[0].categoryCode,
                            items: <Array<IPeregItemValueCommand>>ko.toJS(items).map(m => {
                                return {
                                    definitionId: m.itemDefId,
                                    itemCode: m.itemCode,
                                    value: m.value,
                                    'type': typeof m.value == 'string' ? 1 : (typeof m.value == 'number' ? 2 : 3)// m.item.dataTypeValue
                                };
                            })
                        };
                    })
                    .value(),
                command: IPeregCommand = {
                    personId: person.personId(),
                    employeeId: emp.employeeId(),
                    inputs: inputs
                };
            debugger;
            // push data layout to webservice
            block();
            service.saveCurrentLayout(command).done(() => {
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
        classificationItems?: Array<any>;
        standardDate?: string;
    }

    class Layout {
        layoutCode: KnockoutObservable<string> = ko.observable('');
        layoutName: KnockoutObservable<string> = ko.observable('');
        maintenanceLayoutID: KnockoutObservable<string> = ko.observable('');
        listItemClsDto: KnockoutObservableArray<any> = ko.observableArray([]);
        standardDate: KnockoutObservable<string> = ko.observable(undefined);

        constructor(param?: ILayout) {
            let self = this;
            if (param) {
                self.layoutCode(param.layoutCode || '');
                self.layoutName(param.layoutName || '');
                self.maintenanceLayoutID(param.maintenanceLayoutID || '');
                self.standardDate(param.standardDate)

                self.listItemClsDto(param.listItemClsDto || []);
            }
        }

        // recall selected layout event
        filterData() {
            let self = this;
            self.maintenanceLayoutID.valueHasMutated();
        }
    }

    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: IT_CAT_TYPE;
        isFixed?: number;
    }

    class Category {
        id: KnockoutObservable<string> = ko.observable('');
        categoryCode: KnockoutObservable<string> = ko.observable('');
        categoryName: KnockoutObservable<string> = ko.observable('');
        categoryType: KnockoutObservable<IT_CAT_TYPE> = ko.observable(0);
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
        avatar?: string;
        workplaceId: string;
        workplaceCode?: string;
        workplaceName?: string;
        daysOfEntire?: number;
        daysOfTemporaryAbsence?: number;
    }

    class EmployeeInfo {
        employeeId: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        workplaceId: KnockoutObservable<string> = ko.observable('');
        workplaceCode: KnockoutObservable<string> = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        daysOfEntire: KnockoutObservable<number> = ko.observable(0);
        daysOfTemporaryAbsence: KnockoutObservable<number> = ko.observable(0);
        personInfo: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo({ personId: '' }));

        // calc days of work process
        entire: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                days = self.daysOfEntire();

            days -= self.daysOfTemporaryAbsence();

            let current = moment.utc(),
                entire = moment.utc().add(-days, 'days'),
                duration = moment.duration(current.diff(entire));

            return format("{0}{1}{2}{3}", duration.years(), text('CPS001_67'), duration.months(), text('CPS001_88'));
        });

        constructor(param: IEmployeeInfo) {
            let self = this,
                person = self.personInfo(),
                perInfo = (data?: IPersonInfo) => {
                    if (data) {
                        person.personId(data.personId);
                        person.birthDate(data.birthDate);
                        person.fullName(data.personNameGroup ? data.personNameGroup.businessName : '');
                    } else {
                        person.personId('');
                        person.birthDate(undefined);
                        person.fullName(self.employeeName());
                    }
                };

            if (param) {
                self.employeeId(param.employeeId);
                self.employeeCode(param.employeeCode);
                self.employeeName(param.employeeName);

                self.workplaceId(param.workplaceId);
                self.workplaceCode(param.workplaceCode);
                self.workplaceName(param.workplaceName);

                self.daysOfEntire(param.daysOfEntire);
                self.daysOfTemporaryAbsence(param.daysOfTemporaryAbsence);


                self.avatar(param.avatar || DEF_AVATAR);
            }

            self.employeeId.subscribe(id => {
                if (id) {
                    service.getPerson(id).done((data: IPersonInfo) => {
                        debugger;
                        perInfo(data);
                    }).fail(() => {
                        perInfo();
                    });

                    // get employee && employment info
                    service.getEmpInfo(id).done((data: IEmployeeInfo) => {
                        if (data) {
                            self.employeeCode(data.employeeCode);

                            // set entire days with data receive
                            self.daysOfEntire(data.daysOfEntire);
                            self.daysOfTemporaryAbsence(data.daysOfTemporaryAbsence);
                        } else {
                            self.employeeCode(undefined);

                            // set entire days is zero
                            self.daysOfEntire(0);
                            self.daysOfTemporaryAbsence(0);
                        }
                    }).fail(() => {
                        self.employeeCode(undefined);

                        // set entire days is zero
                        self.daysOfEntire(0);
                        self.daysOfTemporaryAbsence(0);
                    });

                    permision().done((perm: IPersonAuth) => {
                        // Current Employee has permision view other employee avatar
                        if (!!perm.allowAvatarRef) {
                            service.getAvatar(id).done((data: any) => {
                                self.avatar(data.fileId ? liveView(data.fileId) : undefined);
                            });
                        }
                    });
                } else {
                    perInfo();
                }
            });

            self.avatar.subscribe(x => {
                if (!x) {
                    self.avatar(DEF_AVATAR);
                }
            });
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
        bloodType?: number;
        personNameGroup?: PersonNameGroup;
    }

    interface PersonNameGroup {
        businessEnglishName?: string;
        businessName?: string;
        businessOtherName?: string;
        oldName?: string;
        personName?: string;
        personNameKana?: string;
        personRomanji?: string;
        todokedeFullName?: string;
        todokedeOldFullName?: string;
    }

    class PersonInfo {
        personId: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        fullName: KnockoutObservable<string> = ko.observable('');
        birthDate: KnockoutObservable<Date> = ko.observable(undefined);
        constructor(param: IPersonInfo) {
            let self = this;

            self.personId(param.personId || '');
            self.code(param.code || '');
            self.fullName(param.personNameGroup ? param.personNameGroup.businessName : '');
        }

        age: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                birthDay = self.birthDate(),
                duration = moment.duration(moment.utc().diff(moment.utc(birthDay))),
                years = duration.years(),
                months = duration.months(),
                days = duration.days();

            return (years + Number(!!(months || days))) + text('CPS001_66');
        });
    }

    interface IPersonAuth {
        roleId: string;
        allowMapUpload: number;
        allowMapBrowse: number;
        allowDocRef: number;
        allowDocUpload: number;
        allowAvatarUpload: number;
        allowAvatarRef: number;
    }

    class PersonAuth {
        allowAvatarRef: KnockoutObservable<boolean> = ko.observable(false);
        allowDocRef: KnockoutObservable<boolean> = ko.observable(false);
        allowMapBrowse: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param?: IPersonAuth) {
            let self = this;
            if (param) {
                self.allowAvatarRef(!!param.allowAvatarRef);
                self.allowDocRef(!!param.allowDocRef);
                self.allowMapBrowse(!!param.allowMapBrowse);
            }
        }
    }

    enum TABS {
        LAYOUT = <any>"layout",
        CATEGORY = <any>"category"
    }

    // define ITEM_CATEGORY_TYPE
    enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5 // Duplicate history
    }

    interface IPeregQuery {
        ctgId: string;
        ctgCd?: string;
        empId: string;
        standardDate: Date;
        infoId?: string;
    }

    interface ILayoutQuery {
        layoutId: string;
        browsingEmpId: string;
        standardDate: Date;
    }

    interface IPeregCommand {
        personId: string;
        employeeId: string;
        inputs: Array<IPeregItemCommand>;
    }

    interface IPeregItemCommand {
        /** category code */
        categoryCd: string;
        /** Record Id, but this is null when new record */
        recordId: string;
        /** input items */
        items: Array<IPeregItemValueCommand>;
    }

    interface IPeregItemValueCommand {
        definitionId: string;
        itemCode: string;
        value: string;
        'type': number;
    }
}