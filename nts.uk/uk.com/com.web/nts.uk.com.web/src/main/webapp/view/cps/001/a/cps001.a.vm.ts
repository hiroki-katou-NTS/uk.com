module cps001.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import hasError = nts.uk.ui.errors.hasError;
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
            onSearchAllClicked: (dataList: Array<IEmployee>) => {
                let self = this;

                self.employees.removeAll();
                self.employees(dataList);
            },
            onSearchOnlyClicked: (data: IEmployee) => {
                let self = this;

                self.employees.removeAll();
                self.employees([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployee>) => {
                let self = this;

                self.employees.removeAll();
                self.employees(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployee>) => {
                let self = this;

                self.employees.removeAll();
                self.employees(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployee>) => {
                let self = this;

                self.employees.removeAll();
                self.employees(dataList);
            }
        };

        // current tab active id (layout/category)
        tab: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);

        // permision of current employee (current login).
        auth: KnockoutObservable<PersonAuth> = ko.observable(new PersonAuth());

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        employee: KnockoutObservable<Employee> = ko.observable(new Employee());

        person: KnockoutComputed<PersonInfo> = ko.computed(() => {
            let self = this,
                employee = self.employee();

            return employee.personInfo();
        });

        // output data on category changed
        multipleData: KnockoutObservableArray<MultiData> = ko.observableArray([new MultiData()]);

        categories: KnockoutComputed<Array<IListData>> = ko.computed(() => {
            let self = this,
                categories = self.multipleData().map(x => x.categories());

            return categories[0] || [];
        });

        combobox: KnockoutObservableArray<any> = ko.observableArray([]);

        // resource id for title in category mode
        titleResource: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                category: any = { categoryType: () => { return 1; } };

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

        constructor() {
            let self = this,
                auth = self.auth(),
                employee = self.employee(),
                person = employee.personInfo(),
                list = self.multipleData;

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

            self.tab.subscribe(tab => {
                self.multipleData.removeAll();

                let personId: string = person.personId(),
                    employeeId: string = employee.employeeId();
                if (!!employeeId) {
                    switch (tab) {
                        default:
                        case TABS.LAYOUT: // layout mode
                            {
                                let layoutData = new MultiData({
                                    personId: personId,
                                    employeeId: employeeId
                                }),
                                    layout = layoutData.layout();

                                layoutData.mode(TABS.LAYOUT);
                                self.multipleData.push(layoutData);

                                service.getAllLayout(employeeId).done((data: Array<any>) => {
                                    if (data && data.length) {
                                        let sources = data.map(x => {
                                            return {
                                                item: x,
                                                optionText: x.layoutName,
                                                optionValue: x.maintenanceLayoutID
                                            };
                                        });

                                        layoutData.gridlist(sources);
                                        layoutData.id(sources[0].optionValue);
                                    }
                                });
                            }
                            break;
                        case TABS.CATEGORY: // category mode
                            {
                                let layoutData = new MultiData({
                                    personId: personId,
                                    employeeId: employeeId
                                }),
                                    layout = layoutData.layout();

                                layoutData.mode(TABS.CATEGORY);
                                self.multipleData.push(layoutData);

                                service.getCats(employeeId).done((data: Array<ICategory>) => {
                                    if (data && data.length) {
                                        let sources = data.map(x => {
                                            return {
                                                item: _.cloneDeep(x),
                                                optionValue: x.id,
                                                optionText: x.categoryName
                                            };
                                        });
                                        layoutData.combobox(sources);
                                        layoutData.id(sources[0].optionValue);
                                    }
                                });
                            }
                            break;
                    }
                }
            });

            employee.employeeId.subscribe(id => {
                if (id) {
                    self.tab.valueHasMutated();
                }
            });

            employee.employeeId.valueHasMutated();

            self.start();
        }

        start() {
            let self = this,
                employee = self.employee(),
                params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined };

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                if (params && params.employeeId) {
                    $('.btn-quick-search[tabindex=3]').click();
                    setInterval(() => {
                        if (!employee.employeeId()) {
                            let employees = _.filter(self.employees(), m => m.employeeId == params.employeeId);
                            self.employees(employees);
                            employee.employeeId(employees[0] ? employees[0].employeeId : undefined);
                        }
                    }, 0);
                } else {
                    $('.btn-quick-search[tabindex=4]').click();
                    setInterval(() => {
                        if (!employee.employeeId()) {
                            let employees = self.employees();
                            employee.employeeId(employees[0] ? employees[0].employeeId : undefined);
                        }
                    }, 0);
                }
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
                employee: Employee = self.employee(),
                iemp: IEmployee = ko.toJS(employee);

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
                employee: Employee = self.employee(),
                iemp: IEmployee = ko.toJS(employee);

            modal('../c/index.xhtml').onClosed(() => {
                self.start();
            });
        }

        pickLocation() {
            let self = this,
                employee: Employee = self.employee(),
                iemp: IEmployee = ko.toJS(employee);

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
                layouts = self.multipleData().map(x => x.layout()),
                inputs = _.flatten(layouts.map(x => x.outData())),
                command: IPeregCommand = {
                    personId: person.personId(),
                    employeeId: emp.employeeId(),
                    inputs: inputs
                };

            // trigger change of all control in layout
            _.each(__viewContext.primitiveValueConstraints, x => {
                if (_.has(x, "itemCode")) {
                    $('#' + x.itemCode).trigger('change');
                }
            })

            if (hasError()) {
                $('#func-notifier-errors').trigger('click');
                return;
            }

            // push data layout to webservice
            block();
            service.saveCurrentLayout(command).done(() => {
                info({ messageId: "Msg_15" }).then(function() {
                    self.start();
                    _.each(self.multipleData(), m => {
                        if (m.mode() == TABS.LAYOUT) {
                            m.id.valueHasMutated();
                        } else {
                            m.infoId(undefined);
                            m.category().categoryType.valueHasMutated();
                        }
                    });
                    unblock();
                });
            }).fail((mes) => {
                unblock();
                alert(mes.message);
            });
        }
    }

    interface IListData {
        optionText: string;
        optionValue: string;
        item?: any;
    }

    interface Events {
        add: (callback?: void) => void;
        remove: (callback?: void) => void;
        replace: (callback?: void) => void;
    }

    class Layout {
        outData: KnockoutObservableArray<any> = ko.observableArray([]);

        listItemCls: KnockoutObservableArray<any> = ko.observableArray([]);

        // standardDate of layout
        standardDate: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD"));

        constructor() {
            let self = this;
        }

        clearData() {
            let self = this;
            _.each(self.listItemCls(), x => {
                _.each((x.items()), (def, i) => {
                    if (_.isArray(def)) {
                        _.each(def, m => {
                            m.value(undefined);
                        });
                    } else {
                        def.value(undefined);
                    }
                });
            });
        }
    }

    interface IMultiData {
        employeeId?: string;
        personId?: string;
        categoryId?: string;
    }

    class MultiData {
        mode: KnockoutObservable<TABS> = ko.observable(undefined);

        // selected value on list data
        id: KnockoutObservable<string> = ko.observable(undefined);

        // selected value on list data multiple/history
        infoId: KnockoutObservable<string> = ko.observable(undefined);

        categoryId: KnockoutObservable<string> = ko.observable(undefined);

        personId: KnockoutObservable<string> = ko.observable(undefined);
        employeeId: KnockoutObservable<string> = ko.observable(undefined);

        category: KnockoutObservable<Category> = ko.observable(new Category());

        // event action
        events: Events = {
            add: (callback?: void) => {
                let self = this;
                if (self.infoId()) {
                    self.infoId(undefined);
                } else {
                    self.infoId.valueHasMutated();
                }
                if (callback) {
                    callback;
                }
            },
            remove: (callback?: void) => {
                let self = this,
                    category = self.category();

                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    let query = {
                        recordId: self.infoId(),
                        personId: self.personId(),
                        employeeId: self.employeeId(),
                        categoryId: category.categoryCode()
                    };

                    service.removeCurrentCategoryData(query).done(x => {
                        info({ messageId: "Msg_16" }).then(() => {
                            self.infoId(undefined);
                            category.categoryType.valueHasMutated();
                        });
                    });
                });
            },
            replace: (callback?: void) => {
                if (callback) {
                    callback;
                }
            }
        }

        combobox: KnockoutObservableArray<IListData> = ko.observableArray([]);
        gridlist: KnockoutObservableArray<IListData> = ko.observableArray([]);
        categories: KnockoutObservableArray<IListData> = ko.observableArray([]);

        // object layout
        layout: KnockoutObservable<Layout> = ko.observable(new Layout());

        constructor(data?: IMultiData) {
            let self = this,
                layout = self.layout,
                category = self.category();

            if (data) {
                self.personId(data.personId);
                self.employeeId(data.employeeId);

                self.categoryId(data.categoryId);
            }

            self.id.subscribe(id => {
                self.infoId(undefined);

                if (id && self.mode() == TABS.CATEGORY) {
                    let option = _.find(self.combobox(), x => x.optionValue == id);

                    if (option) {
                        let icat: ICategory = option.item;

                        category.categoryCode(icat.categoryCode);
                        category.categoryType(icat.categoryType);
                    } else {
                        category.categoryCode(undefined);
                        category.categoryType(undefined);
                    }

                    service.getCatChilds(id).done(data => {
                        category.hasChildrens(data.length > 1);
                    });
                }

                self.categoryId.valueHasMutated();
            });

            self.categoryId.subscribe(id => {
                self.infoId.valueHasMutated();
                category.categoryType.valueHasMutated();
            });

            self.infoId.subscribe(infoId => {
                if (self.id()) {
                    if (self.mode() == TABS.LAYOUT) {
                        let id = self.id(),
                            sdate = layout().standardDate(),
                            ddate = sdate && moment.utc(sdate).toDate() || moment.utc().toDate(),
                            query: ILayoutQuery = {
                                layoutId: id,
                                browsingEmpId: self.employeeId(),
                                standardDate: ddate
                            };
                        service.getCurrentLayout(query).done((data: any) => {
                            if (data) {
                                layout().standardDate(data.standardDate || undefined);
                                layout().listItemCls(data.classificationItems || []);
                            }
                        });
                    } else if (self.category().categoryType() != IT_CAT_TYPE.SINGLE) {
                        let id = self.id(),
                            catid = self.categoryId(),
                            query = {
                                infoId: self.infoId(),
                                categoryId: catid || id,
                                personId: self.personId(),
                                employeeId: self.employeeId(),
                                standardDate: undefined,
                                categoryCode: category.categoryCode()
                            };
                        service.getCatData(query).done(data => {
                            layout().listItemCls(data.classificationItems);
                        });
                    }
                }
            });

            category.categoryType.subscribe(t => {
                if (self.id() && self.mode() == TABS.CATEGORY) {
                    let id = self.id(),
                        catid = self.categoryId(),
                        query = {
                            infoId: self.infoId(),
                            categoryId: catid || id,
                            personId: self.personId(),
                            employeeId: self.employeeId(),
                            standardDate: undefined,
                            categoryCode: category.categoryCode()
                        };
                    switch (t) {
                        case IT_CAT_TYPE.SINGLE:
                            service.getCatData(query).done(data => {
                                layout().listItemCls(data.classificationItems);
                            });
                            break;
                        case IT_CAT_TYPE.MULTI:
                            {
                            }
                            break;
                        case IT_CAT_TYPE.CONTINU:
                        case IT_CAT_TYPE.CONTINUWED:
                        case IT_CAT_TYPE.DUPLICATE:
                        case IT_CAT_TYPE.NODUPLICATE:
                            service.getHistData(query).done((data: Array<any>) => {
                                if (data && data.length) {
                                    self.gridlist(data);
                                    self.infoId(data[0].optionValue);
                                } else {
                                    self.events.add();
                                    self.gridlist.removeAll();
                                }
                            });
                            break;
                    }
                }
            });

            category.hasChildrens.subscribe(h => {
                if (!h) {
                    self.categoryId(undefined);
                }
            });
        }
    }

    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: IT_CAT_TYPE;
    }

    class Category {
        categoryCode: KnockoutObservable<string> = ko.observable('');
        categoryType: KnockoutObservable<IT_CAT_TYPE> = ko.observable(0);

        hasChildrens: KnockoutObservable<boolean> = ko.observable(false);
    }

    interface IEmployee {
        pid?: string;
        employeeId: string;
        gender?: string;
        birthday?: string;

        employeeCode?: string;
        employeeName?: string;

        numberOfWork?: number;
        numberOfTempHist?: number;

        departmentCode?: string;
        departmentName?: string;

        avatar?: string;

        position?: string;
        contractCodeType?: string;
    }

    class Employee {
        employeeId: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        departmentCode: KnockoutObservable<string> = ko.observable('');
        departmentName: KnockoutObservable<string> = ko.observable('');

        position: KnockoutObservable<string> = ko.observable('');
        contractType: KnockoutObservable<string> = ko.observable('');

        numberOfWork: KnockoutObservable<number> = ko.observable(0);

        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        personInfo: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo());

        // calc days of work process
        entire: KnockoutComputed<string> = ko.computed(() => {
            let self = this,
                days = self.numberOfWork(),
                duration = moment.duration(days, "days");

            return format("{0}{1}{2}{3}", duration.years(), text('CPS001_67'), duration.months(), text('CPS001_88'));
        });

        constructor(param?: IEmployee) {
            let self = this,
                person = self.personInfo();

            if (param) {
                self.employeeId(param.employeeId);
                self.employeeCode(param.employeeCode);
                self.employeeName(param.employeeName);

                self.avatar(param.avatar || DEF_AVATAR);
            }

            self.employeeId.subscribe(id => {
                if (id) {
                    // get employee && employment info
                    service.getEmpInfo(id).done((data: IEmployee) => {
                        if (data) {
                            person.personId(data.pid);
                            self.employeeCode(data.employeeCode);
                            self.employeeName(data.employeeName);

                            // set entire days with data receive
                            self.numberOfWork(data.numberOfWork - data.numberOfTempHist);

                            self.position(data.position);
                            self.contractType(data.contractCodeType);

                            person.gender(data.gender);
                            person.birthDate(moment.utc(data.birthday).toDate());

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
                    person.gender(undefined);
                    person.personId(undefined);
                    person.birthDate(undefined);
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
        pid: string;
        birthDate?: Date;
        gender?: string;
        countryId?: number;
        mailAddress?: string;
        personMobile?: string;
        code?: string;
        bloodType?: number;
        personNameGroup?: IPersonNameGroup;
    }

    interface IPersonNameGroup {
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
        gender: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        fullName: KnockoutObservable<string> = ko.observable('');
        birthDate: KnockoutObservable<Date> = ko.observable(undefined);
        constructor(param?: IPersonInfo) {
            let self = this;

            if (param) {
                self.personId(param.pid || '');
                self.code(param.code || '');
                self.fullName(param.personNameGroup ? param.personNameGroup.businessName : '');
                self.gender(param.gender || '');
            }
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
        DUPLICATE = 5, // Duplicate history,
        CONTINUWED = 6 // Continuos history with end date
    }

    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
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

    interface IParam {
        employeeId: string;
    }
}