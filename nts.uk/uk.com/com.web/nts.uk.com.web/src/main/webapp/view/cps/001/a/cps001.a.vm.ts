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
        employee: KnockoutObservable<EmployeeInfo> = ko.observable(new EmployeeInfo({ employeeId: '' }));

        person: KnockoutComputed<PersonInfo> = ko.computed(() => {
            let self = this,
                employee = self.employee();
            return employee.personInfo();
        });

        listLayout: KnockoutObservableArray<ILayout> = ko.observableArray([]);

        layouts: KnockoutObservableArray<Layout> = ko.observableArray(_.fill(Array(10), 0).map(x => new Layout()));

        currentLayout: KnockoutComputed<Layout> = ko.computed(() => {
            let self = this;

            return _.first(self.layouts());
        });

        // for case: combobox
        listCategory: KnockoutObservableArray<ICategory> = ko.observableArray([]);
        currentCategory: KnockoutObservable<Category> = ko.observable(new Category({ id: '' }));

        // for case: category with childs
        listTabCategory: KnockoutObservableArray<ICategory> = ko.observableArray([]);
        currentTabCategory: KnockoutObservable<string> = ko.observable('cat1');

        // resource id for title in category mode
        titleResource: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                category = self.currentCategory();

            switch (category.categoryType()) {
                case IT_CAT_TYPE.SINGLE:
                case IT_CAT_TYPE.DUPLICATE:
                case IT_CAT_TYPE.NODUPLICATE:
                    return text("CPS001_38");
                case IT_CAT_TYPE.MULTI:
                    return text("CPS001_39");
                case IT_CAT_TYPE.CONTINU:
                case IT_CAT_TYPE.CONTINUWED:
                    return text("CPS001_41");
            }
        });

        // output data on category changed
        multipleData: KnockoutObservableArray<MultiData> = ko.observableArray(_.fill(Array(10), 0).map(x => new MultiData()));

        constructor() {
            let self = this,
                auth = self.auth(),
                employee = self.employee(),
                person = employee.personInfo(),
                category = self.currentCategory(),
                list = self.multipleData,
                layouts = self.layouts,
                layout = self.currentLayout();

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
                            category.id(undefined);
                            self.listLayout.removeAll();
                            layout.id(undefined);
                            service.getAllLayout(employeeId).done((data: Array<any>) => {
                                if (data && data.length) {
                                    let sources = data.map(x => {
                                        return {
                                            id: x.maintenanceLayoutID,
                                            name: x.layoutName
                                        };
                                    });
                                    self.listLayout(sources);
                                    layout.id(sources[0].id);
                                }
                            });
                            break;
                        case TABS.CATEGORY: // category mode
                            layout.id(undefined);
                            self.listCategory.removeAll();
                            service.getCats(employeeId).done((data: Array<ICategory>) => {
                                if (data && data.length) {
                                    self.listCategory(data);
                                }
                            });
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

            layout.id.subscribe(id => {
                if (id) {
                    // clear all error message
                    clearError();
                    let query: ILayoutQuery = {
                        layoutId: id,
                        browsingEmpId: employee.employeeId(),
                        standardDate: moment.utc(layout.standardDate()).toDate()
                    };

                    service.getCurrentLayout(query).done((data: ILayout) => {
                        if (data) {
                            if (data.standardDate) {
                                layout.standardDate(data.standardDate);
                            }
                            layout.listItemCls(data.classificationItems || []);
                        }
                    });
                }
                _.each(layouts(), (item, index) => {
                    if (index > 0) {
                        item.listItemCls([]);
                    }
                });
            });

            category.id.subscribe(id => {
                if (id) {
                    let cat = _.find(self.listCategory(), x => x.id == id);
                    if (cat) {
                        category.categoryCode(cat.categoryCode);
                        category.categoryName(cat.categoryName);
                        category.isFixed(cat.isFixed);

                        if (category.categoryType() == cat.categoryType) {
                            category.categoryType.valueHasMutated();
                        } else {
                            category.categoryType(cat.categoryType);
                        }
                    }

                    service.getCatChilds(id).done(data => {
                        category.hasChilds(data.length > 1);
                    });
                }
            });

            category.categoryType.subscribe(t => {
                if (t) {
                    let query = {
                        categoryId: category.id(),
                        employeeId: employee.employeeId(),
                        standardDate: moment.utc(),
                        categoryCode: category.categoryCode(),
                        personId: person.personId(),
                        infoId: undefined
                    };
                    switch (t) {
                        case IT_CAT_TYPE.SINGLE:
                        case IT_CAT_TYPE.NODUPLICATE:
                            service.getCatData(query).done(data => {
                                layout.listItemCls(data.classificationItems);
                            });
                            break;
                        case IT_CAT_TYPE.MULTI:
                            service.getCatData(query).done(data => {
                                layout.listItemCls(data.classificationItems);
                            });
                            break;
                        case IT_CAT_TYPE.CONTINU:
                            service.getHistData(query).done(data => {
                                let source: MultiData = _.first(list());
                                source.data(data);
                                if (source.id() == data[0].optionValue) {
                                    source.id.valueHasMutated();
                                } else {
                                    source.id(data[0].optionValue);
                                }
                            });
                            break;
                        case IT_CAT_TYPE.DUPLICATE:
                            debugger;
                            break;
                        case IT_CAT_TYPE.CONTINUWED:
                            debugger;
                            break;
                    }
                }
            });

            _.each(list(), (item, index) => {
                let lt = _.find(layouts(), (l, i) => i == index);

                item.id.subscribe(v => {
                    let query = {
                        categoryId: category.id(),
                        employeeId: employee.employeeId(),
                        standardDate: moment.utc(),
                        categoryCode: category.categoryCode(),
                        personId: person.personId(),
                        infoId: v
                    };

                    service.getCatData(query).done(data => {
                        lt.listItemCls(data.classificationItems);
                    });
                });
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

            setShared('CPS001B_PARAMS', {
                sid: emp.employeeId(),
                pid: person.personId()
            });
            modal('../b/index.xhtml').onClosed(() => { });
        }

        chooseAvatar() {
            let self = this,
                employee: EmployeeInfo = self.employee(),
                iemp: IEmployeeInfo = ko.toJS(employee);

            if (!iemp || !iemp.employeeId) {
                return;
            }

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowAvatarUpload) {
                    setShared("CPS001D_PARAMS", {
                        employeeId: iemp.employeeId
                    });
                    modal('../d/index.xhtml').onClosed(() => {
                        let data = getShared("CPS001D_VALUES");

                        if (data)
                            employee.avatar(data.fileId ? liveView(data.fileId) : undefined);
                    });
                }
            });
        }

        unManagerEmployee() {
            let self = this,
                employee: EmployeeInfo = self.employee(),
                iemp: IEmployeeInfo = ko.toJS(employee);

            modal('../c/index.xhtml').onClosed(() => {
                self.start();
            });
        }

        pickLocation() {
            let self = this,
                employee: EmployeeInfo = self.employee(),
                iemp: IEmployeeInfo = ko.toJS(employee);

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowMapBrowse) {
                    setShared("CPS001E_PARAMS", {
                        employeeId: iemp.employeeId
                    });
                    modal('../e/index.xhtml').onClosed(() => { });
                }
            });
        }

        uploadebook() {
            let self = this,
                person = self.person();

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowDocRef) {
                    setShared("CPS001F_PARAMS", {
                        pid: person.personId()
                    });
                    modal('../f/index.xhtml').onClosed(() => { });
                }
            });
        }

        saveData() {
            let self = this,
                emp = self.employee(),
                person = emp.personInfo(),
                layouts = self.layouts(),
                inputs = _.flatten(layouts.map(x => x.outData())),
                command: IPeregCommand = {
                    personId: person.personId(),
                    employeeId: emp.employeeId(),
                    inputs: inputs
                };

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
        standardDate?: string;
        listItemCls?: Array<any>;
        classificationItems?: Array<any>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable(undefined);

        listItemCls: KnockoutObservableArray<any> = ko.observableArray([]);
        standardDate: KnockoutObservable<string> = ko.observable(undefined);
        outData: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param?: ILayout) {
            let self = this;
            if (param) {
                self.standardDate(param.standardDate)

                self.listItemCls(param.listItemCls || []);
            }
        }

        // recall selected layout event
        filterData() {
            let self = this;
            self.id.valueHasMutated();
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
        hasChilds: KnockoutObservable<boolean> = ko.observable(false);

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

        employeeCode?: string;
        employeeName?: string;

        numberOfWork?: number;
        departmentCode?: string;
        departmentName?: string;

        avatar?: string;

        position?: string;
        contractCodeType?: string;
    }

    class EmployeeInfo {
        employeeId: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        departmentCode: KnockoutObservable<string> = ko.observable('');
        departmentName: KnockoutObservable<string> = ko.observable('');

        position: KnockoutObservable<string> = ko.observable('');
        contractType: KnockoutObservable<string> = ko.observable('');

        numberOfWork: KnockoutObservable<number> = ko.observable(0);

        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        personInfo: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo({ personId: '' }));

        // calc days of work process
        entire: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                days = self.numberOfWork();

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

                self.avatar(param.avatar || DEF_AVATAR);
            }

            self.employeeId.subscribe(id => {
                if (id) {
                    service.getPerson(id).done((data: IPersonInfo) => {
                        perInfo(data);
                    }).fail(() => {
                        perInfo();
                    });

                    // get employee && employment info
                    service.getEmpInfo(id).done((data: IEmployeeInfo) => {
                        if (data) {
                            self.employeeCode(data.employeeCode);
                            self.employeeName(data.employeeName);

                            // set entire days with data receive
                            self.numberOfWork(data.numberOfWork);

                            self.position(data.position);
                            self.contractType(data.contractCodeType);

                            self.departmentCode(data.departmentCode);
                            self.departmentName(data.departmentName);
                        } else {
                            self.employeeCode(undefined);
                            self.employeeName(undefined);

                            // set entire days is zero
                            self.numberOfWork(0);

                            self.position(undefined);
                            self.contractType(undefined);

                            self.departmentCode(undefined);
                            self.departmentName(undefined);
                        }
                    }).fail(() => {
                        self.employeeCode(undefined);
                        self.employeeName(undefined);

                        // set entire days is zero
                        self.numberOfWork(0);

                        self.position(undefined);
                        self.contractType(undefined);

                        self.departmentCode(undefined);
                        self.departmentName(undefined);
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

    interface IMultiData {
        optionText: string;
        optionValue: string;
    }

    class MultiData {
        id: KnockoutObservable<string> = ko.observable(undefined);
        data: KnockoutObservableArray<IMultiData> = ko.observableArray([]);
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
        DUPLICATE = 5, // Duplicate history,
        CONTINUWED = 6 // Continuos history with end date
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

    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
    }

    interface IPeregItemValueCommand {
        definitionId: string;
        itemCode: string;
        value: string;
        'type': number;
    }
}