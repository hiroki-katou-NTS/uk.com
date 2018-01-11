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
    import permision4Cat = service.getPermision4Cat;
    import format = nts.uk.text.format;

    const REPL_KEY = '__REPLACE',
        RELOAD_KEY = "__RELOAD",
        RELOAD_DT_KEY = "__RELOAD_DATA",
        DEF_AVATAR = 'images/avatar.png',
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
                self.employees(dataList);
            },
            onSearchOnlyClicked: (data: IEmployee) => {
                let self = this;
                self.employees([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployee>) => {
                let self = this;
                self.employees(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployee>) => {
                let self = this;
                self.employees(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployee>) => {
                let self = this;
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

        saveAble: KnockoutObservable<boolean> = ko.observable(false);

        categories: KnockoutComputed<Array<IListData>> = ko.computed(() => {
            let self = this,
                categories = self.multipleData().map(x => x.categories());

            return categories[0] || [];
        });

        // resource id for title in category mode
        titleResource: KnockoutObservable<string> = ko.observable(text("CPS001_39"));

        constructor() {
            let self = this,
                auth = self.auth(),
                employee = self.employee(),
                person = employee.personInfo(),
                list = self.multipleData;

            permision().done((data: IPersonAuth) => {
                if (data) {
                    auth.roleId(data.roleId);
                    auth.allowDocRef(!!data.allowDocRef);
                    auth.allowAvatarRef(!!data.allowAvatarRef);
                    auth.allowMapBrowse(!!data.allowMapBrowse);
                } else {
                    auth.roleId(undefined);
                    auth.allowDocRef(false);
                    auth.allowAvatarRef(false);
                    auth.allowMapBrowse(false);
                }
            });

            self.tab.subscribe(tab => {
                let loadData: IReloadData = getShared(RELOAD_DT_KEY),
                    personId: string = person.personId(),
                    employeeId: string = employee.employeeId(),
                    selectFirstId = (sources: Array<any>, layoutData: any) => {
                        if (loadData) {
                            if (loadData.id) {
                                if (layoutData.id() == loadData.id) {
                                    layoutData.id.valueHasMutated();
                                } else {
                                    layoutData.id(loadData.id);
                                }
                            } else {
                                if (layoutData.id() == sources[0].optionValue) {
                                    layoutData.id.valueHasMutated();
                                } else {
                                    layoutData.id(sources[0].optionValue);
                                }
                            }
                        } else {
                            if (layoutData.id() == sources[0].optionValue) {
                                layoutData.id.valueHasMutated();
                            } else {
                                layoutData.id(sources[0].optionValue);
                            }
                        }
                    };

                if (!loadData) {
                    self.multipleData.removeAll();
                }

                if (!!employeeId) {
                    let layoutData = _.first(self.multipleData()) || new MultiData({
                        personId: personId,
                        employeeId: employeeId
                    }),
                        layout = layoutData.layout();

                    if (!loadData) {
                        self.multipleData.push(layoutData);
                        $.extend(layoutData, { title: self.titleResource });
                    }

                    layoutData.mode(tab);
                    layoutData.roleId(self.auth().roleId());

                    switch (tab) {
                        default:
                        case TABS.LAYOUT: // layout mode
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
                                    selectFirstId(sources, layoutData);
                                }
                            }).fail(msg => {
                                layoutData.gridlist.removeAll();
                                layoutData.id(undefined);
                                setShared(RELOAD_DT_KEY, undefined);
                            });
                            break;
                        case TABS.CATEGORY: // category mode
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
                                    selectFirstId(sources, layoutData);
                                }
                            }).fail(msg => {
                                layoutData.id(undefined);
                                layoutData.combobox([]);
                                setShared(RELOAD_DT_KEY, undefined);
                            });
                            break;
                    }
                }
            });

            employee.employeeId.subscribe(id => {
                let reload = getShared(RELOAD_KEY);

                if (id) {
                    _.each(list(), l => {
                        l.employeeId(id);
                        l.personId(person.personId());
                    });

                    if (reload) {
                        let firstData: MultiData = _.first(self.multipleData()) || new MultiData(),
                            saveData: IReloadData = {
                                id: firstData.id(),
                                infoId: undefined,
                                categoryId: firstData.categoryId()
                            };

                        if (!getShared(RELOAD_DT_KEY)) {
                            setShared(RELOAD_DT_KEY, saveData);
                        }
                    }

                    self.tab.valueHasMutated();
                }
            });

            self.start();

            setInterval(() => {
                let aut = _(self.multipleData())
                    .map(m => m.layout())
                    .map(m => m.listItemCls())
                    .flatten() // get item of all layout
                    .map((m: any) => _.has(m, 'items') && ko.isObservable(m.items) ? ko.toJS(m.items) : undefined)
                    .filter(x => !!x)
                    .flatten() // flat set item
                    .flatten() // flat list item
                    .map((m: any) => !ko.toJS(m.readonly))
                    .filter(x => !!x)
                    .value();

                self.saveAble(!!aut.length);
            }, 0);
        }

        start() {
            let self = this,
                reload = getShared(RELOAD_KEY),
                reloadData = getShared(RELOAD_DT_KEY),
                employee = self.employee(),
                employees = ko.toJS(self.employees),
                params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined };

            if (reload) {
                if (employees.length == 1) {
                    $('.btn-quick-search[tabindex=4]').click();
                } else {
                    $('.btn-quick-search[tabindex=3]').click();
                }

                $.when((() => {
                    let def = $.Deferred(),
                        int = setInterval(() => {
                            if (self.employees().length) {
                                clearInterval(int);
                                def.resolve(self.employees());
                            }
                        }, 0);
                    return def.promise();
                })()).done(x => {
                    employee.employeeId.valueHasMutated();
                });
            } else {
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                    if (params && params.employeeId) {
                        $('.btn-quick-search[tabindex=3]').click();
                    } else {
                        $('.btn-quick-search[tabindex=4]').click();
                    }

                    $.when((() => {
                        let def = $.Deferred(),
                            int = setInterval(() => {
                                if (self.employees().length) {
                                    clearInterval(int);
                                    def.resolve(self.employees());
                                }
                            }, 0);
                        return def.promise();
                    })()).done((employees: Array<IEmployee>) => {
                        if (params && params.employeeId) {
                            employees = _.filter(employees, m => m.employeeId == params.employeeId);
                            self.employees(employees);
                        }
                        employee.employeeId(employees[0] ? employees[0].employeeId : undefined);
                        setShared(RELOAD_KEY, true);
                    });
                });
            }
        }

        deleteEmployee() {
            let self = this,
                emp = self.employee(),
                person = self.person();

            setShared('CPS001B_PARAMS', {
                sid: emp.employeeId(),
                pid: person.personId()
            });
            modal('../b/index.xhtml').onClosed(() => {
                self.start();
                unblock();
            });
        }

        chooseAvatar() {
            let self = this,
                employee: Employee = self.employee(),
                iemp: IEmployee = ko.toJS(employee);

            if (!iemp || !iemp.employeeId) {
                return;
            }

            permision().done((perm: IPersonAuth) => {
                if (!!perm.allowAvatarRef) {
                    setShared("CPS001D_PARAMS", {
                        employeeId: iemp.employeeId
                    });
                    modal('../d/index.xhtml').onClosed(() => {
                        let data = getShared("CPS001D_VALUES");

                        if (data) {
                            employee.avatar(data.fileId ? liveView(data.fileId) : undefined);
                        }
                    });
                }
            });
        }

        unManagerEmployee() {
            let self = this,
                employee: Employee = self.employee(),
                iemp: IEmployee = ko.toJS(employee);

            modal('../c/index.xhtml').onClosed(() => {
                let params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined, showAll: false };

                $('.btn-quick-search[tabindex=3]').click();
                $.when((() => {
                    let def = $.Deferred(),
                        int = setInterval(() => {
                            if (self.employees().length) {
                                clearInterval(int);
                                def.resolve(self.employees());
                            }
                        }, 0);
                    return def.promise();
                })()).done((employees: Array<IEmployee>) => {
                    if (!employee.employeeId()) {
                        let employees = _.filter(self.employees(), m => m.employeeId == params.employeeId);
                        if (!params.showAll) {
                            self.employees(employees);
                        }
                        if (employees[0]) {
                            if (employees[0].employeeId == employee.employeeId()) {
                                employee.employeeId();
                            } else {
                                employee.employeeId(employees[0].employeeId);
                            }
                        } else {
                            employee.employeeId(undefined);
                        }
                    }
                });
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
                let firstData: MultiData = _.first(self.multipleData()) || new MultiData(),
                    saveData: IReloadData = {
                        id: firstData.id(),
                        infoId: firstData.infoId(),
                        categoryId: firstData.categoryId()
                    };

                setShared(RELOAD_DT_KEY, saveData);

                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                    self.start();
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

    interface Permisions {
        add: KnockoutObservable<boolean>;
        remove: KnockoutObservable<boolean>;
        replace: KnockoutObservable<boolean>;
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
        title: KnockoutObservable<string> = undefined;
        mode: KnockoutObservable<TABS> = ko.observable(undefined);
        roleId: KnockoutObservable<string> = ko.observable(undefined);

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
            add: (callback?: any) => {
                let self = this,
                    catId: string = ko.toJS(self.id),
                    roleId: string = ko.toJS(self.roleId),
                    selEmId: string = self.employeeId(),
                    logInId: string = __viewContext.user.employeeId;

                permision4Cat(roleId, catId).done((perm: ICatAuth) => {
                    if (perm && !!(selEmId == logInId ? perm.selfAllowAddHis : perm.otherAllowAddHis)) {
                        self.changTitle(ATCS.ADD);
                        setShared(REPL_KEY, REPL_KEYS.ADDNEW);

                        self.infoId(undefined);
                        //self.id.valueHasMutated();

                        if (callback && _.isFunction(callback)) {
                            callback();
                        }
                    }
                });
            },
            remove: (callback?: any) => {
                let self = this,
                    category = self.category(),
                    catId: string = ko.toJS(self.id),
                    roleId: string = ko.toJS(self.roleId),
                    selEmId: string = self.employeeId(),
                    logInId: string = __viewContext.user.employeeId;

                permision4Cat(roleId, catId).done((perm: ICatAuth) => {
                    if (perm && !!(selEmId == logInId ? perm.selfAllowDelHis : perm.otherAllowDelHis)) {
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
                                    self.id.valueHasMutated();

                                    if (callback && _.isFunction(callback)) {
                                        callback();
                                    }
                                });
                            });
                        });
                    }
                });
            },
            replace: (callback?: any) => {
                let self = this,
                    catId: string = ko.toJS(self.id),
                    roleId: string = ko.toJS(self.roleId),
                    selEmId: string = self.employeeId(),
                    logInId: string = __viewContext.user.employeeId;

                permision4Cat(roleId, catId).done((perm: ICatAuth) => {
                    if (perm && !!(selEmId == logInId ? perm.selfAllowAddHis : perm.otherAllowAddHis)) {
                        self.changTitle(ATCS.COPY);
                        setShared(REPL_KEY, REPL_KEYS.REPLICATION);
                        self.infoId.valueHasMutated();
                        //self.id.valueHasMutated();

                        if (callback && _.isFunction(callback)) {
                            callback();
                        }
                    }
                });
            }
        }

        permisions: Permisions = {
            add: ko.observable(false),
            remove: ko.observable(false),
            replace: ko.observable(false)
        };

        combobox: KnockoutObservableArray<IListData> = ko.observableArray([]);
        gridlist: KnockoutObservableArray<IListData> = ko.observableArray([]);
        categories: KnockoutObservableArray<IListData> = ko.observableArray([]);

        // object layout
        layout: KnockoutObservable<Layout> = ko.observable(new Layout());

        constructor(data?: IMultiData) {
            let self = this,
                layout = self.layout(),
                cat = self.category();

            if (data) {
                self.personId(data.personId);
                self.employeeId(data.employeeId);

                self.categoryId(data.categoryId);
            }

            self.id.subscribe(id => {
                let mode: TABS = self.mode();
                if (id) {
                    if (mode == TABS.CATEGORY) {
                        let option = _.find(self.combobox(), x => x.optionValue == id);

                        if (option) {
                            let icat: ICategory = option.item;

                            cat.categoryCode(icat.categoryCode);
                            cat.categoryType(icat.categoryType);
                        } else {
                            cat.categoryCode(undefined);
                            cat.categoryType(undefined);
                        }

                        if (id) {
                            service.getCatChilds(id).done(data => {
                                cat.hasChildrens(data.length > 1);
                            });
                        }
                    }
                    self.categoryId.valueHasMutated();
                }
            });

            cat.hasChildrens.subscribe(h => {
                if (!h) {
                    self.categoryId(undefined);
                }
            });

            self.categoryId.subscribe(cid => {
                let id: string = self.id(),
                    mode: TABS = self.mode(),
                    loadData: IReloadData = getShared(RELOAD_DT_KEY);

                if (id) {
                    if (mode == TABS.LAYOUT) {
                        self.infoId(undefined);

                        let sdate = layout.standardDate(),
                            ddate = sdate && moment.utc(sdate).toDate() || moment.utc().toDate(),
                            query: ILayoutQuery = {
                                layoutId: id,
                                browsingEmpId: self.employeeId(),
                                standardDate: ddate
                            };

                        service.getCurrentLayout(query).done((data: any) => {
                            if (data) {
                                layout.standardDate(data.standardDate || undefined);
                                layout.listItemCls(data.classificationItems || []);
                            } else {
                                layout.listItemCls.removeAll();
                            }
                            setShared(RELOAD_DT_KEY, undefined);
                        }).fail(mgs => {
                            layout.listItemCls.removeAll();
                            setShared(RELOAD_DT_KEY, undefined);
                        });
                    } else {
                        let query = {
                            infoId: undefined,
                            categoryId: cid || id,
                            personId: self.personId(),
                            employeeId: self.employeeId(),
                            standardDate: undefined,
                            categoryCode: cat.categoryCode()
                        };

                        switch (cat.categoryType()) {
                            case IT_CAT_TYPE.SINGLE:
                                self.infoId(undefined);

                                self.changTitle(ATCS.UPDATE);

                                service.getCatData(query).done(data => {
                                    if (data) {
                                        layout.listItemCls(data.classificationItems || []);
                                    } else {
                                        layout.listItemCls.removeAll();
                                    }
                                    setShared(RELOAD_DT_KEY, undefined);
                                }).fail(mgs => {
                                    layout.listItemCls.removeAll();
                                    setShared(RELOAD_DT_KEY, undefined);
                                });
                                break;
                            case IT_CAT_TYPE.MULTI:

                                break;
                            case IT_CAT_TYPE.CONTINU:
                            case IT_CAT_TYPE.CONTINUWED:
                            case IT_CAT_TYPE.DUPLICATE:
                            case IT_CAT_TYPE.NODUPLICATE:
                                let rep: number = getShared(REPL_KEY) || REPL_KEYS.NORMAL;

                                if (rep == REPL_KEYS.NORMAL) {
                                    service.getHistData(query).done((data: Array<any>) => {
                                        if (data && data.length) {
                                            self.gridlist(data);
                                            if (!loadData || !loadData.infoId) {
                                                if (self.infoId() != data[0].optionValue) {
                                                    self.infoId(data[0].optionValue);
                                                } else {
                                                    self.infoId.valueHasMutated();
                                                }
                                            } else {
                                                if (loadData.infoId != self.infoId()) {
                                                    self.infoId(loadData.infoId);
                                                } else {
                                                    self.infoId.valueHasMutated();
                                                }
                                            }
                                        } else {
                                            self.events.add();
                                            self.gridlist.removeAll();
                                            setShared(REPL_KEY, undefined);
                                            self.infoId.valueHasMutated();
                                        }
                                        setShared(RELOAD_DT_KEY, undefined);
                                    }).fail(mgs => {
                                        self.gridlist.removeAll();
                                        setShared(REPL_KEY, undefined);
                                        setShared(RELOAD_DT_KEY, undefined);
                                        self.infoId.valueHasMutated();
                                    });
                                } else {
                                    self.infoId.valueHasMutated();
                                }
                                break;
                        }
                    }
                }
            });

            self.infoId.subscribe(infoId => {
                let id = self.id(),
                    mode: TABS = self.mode(),
                    ctp = cat.categoryType(),
                    layout = self.layout();

                if (id && mode == TABS.CATEGORY) {
                    let catid = self.categoryId(),
                        query = {
                            infoId: infoId,
                            categoryId: catid || id,
                            personId: self.personId(),
                            employeeId: self.employeeId(),
                            standardDate: undefined,
                            categoryCode: cat.categoryCode()
                        },
                        rep: number = getShared(REPL_KEY) || REPL_KEYS.NORMAL;

                    if ([REPL_KEYS.NORMAL, REPL_KEYS.REPLICATION, REPL_KEYS.ADDNEW].indexOf(rep) > -1) {
                        let catid = self.categoryId(),
                            query = {
                                infoId: self.infoId(),
                                categoryId: catid || id,
                                personId: self.personId(),
                                employeeId: self.employeeId(),
                                standardDate: undefined,
                                categoryCode: cat.categoryCode()
                            };

                        service.getCatData(query).done(data => {
                            if (rep == REPL_KEYS.ADDNEW) {
                                setShared(REPL_KEY, REPL_KEYS.NORMAL);
                                self.infoId(undefined);
                            } else if (rep == REPL_KEYS.REPLICATION) {
                                setShared(REPL_KEY, REPL_KEYS.OTHER);

                                self.infoId(undefined);

                                _.each(data.classificationItems, (c: any, i: number) => {
                                    if (_.has(c, "items") && _.isArray(c.items)) {
                                        _.each(c.items, m => {
                                            if (!_.isArray(m)) {
                                                if (i == 0) {
                                                    m.value = undefined;
                                                }
                                                m.recordId = undefined;
                                            } else {
                                                _.each(m, k => {
                                                    k.recordId = undefined;
                                                });
                                            }
                                        });
                                    }
                                });
                            } else if (self.infoId()) {
                                self.changTitle(ATCS.UPDATE);
                            }
                            layout.listItemCls(data.classificationItems);

                            let roleId = self.roleId(),
                                catId = self.categoryId() || self.id();

                            permision4Cat(roleId, catId).done((perm: ICatAuth) => {
                                let selEmId: string = self.employeeId(),
                                    logInId: string = __viewContext.user.employeeId;

                                if (perm && !!(selEmId == logInId ? perm.selfAllowAddHis : perm.otherAllowAddHis)) {
                                    self.permisions.add(true);
                                    self.permisions.replace(true);
                                } else {
                                    self.permisions.add(false);
                                    self.permisions.replace(false);
                                }

                                if (perm && !!(selEmId == logInId ? perm.selfAllowDelHis : perm.otherAllowDelHis)) {
                                    self.permisions.remove(true);
                                } else {
                                    self.permisions.remove(false);
                                }
                            });
                        });
                    } else {
                        setShared(REPL_KEY, REPL_KEYS.NORMAL);
                    }
                }
            });
        }

        changTitle = (action: ATCS) => {
            let self = this,
                title = self.title,
                category = self.category(),
                categoryType = category.categoryType();

            switch (categoryType) {
                default:
                case IT_CAT_TYPE.SINGLE:
                    title(text('CPS001_38'));
                    break;
                case IT_CAT_TYPE.MULTI:
                    switch (action) {
                        case ATCS.ADD:
                            title(text('CPS001_39'));
                            break;
                        case ATCS.UPDATE:
                            title(text('CPS001_40'));
                            break;
                    }
                    break;
                case IT_CAT_TYPE.CONTINU:
                case IT_CAT_TYPE.NODUPLICATE:
                case IT_CAT_TYPE.DUPLICATE:
                case IT_CAT_TYPE.CONTINUWED:
                    switch (action) {
                        case ATCS.ADD:
                        case ATCS.COPY:
                            title(text('CPS001_41'));
                            break;
                        case ATCS.UPDATE:
                            title(text('CPS001_42'));
                            break;
                    }
                    break;
            }
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
                        service.getAvatar(id).done((data: any) => {
                            self.avatar(data.fileId ? liveView(data.fileId) : undefined);
                        });
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
        roleId: KnockoutObservable<string> = ko.observable('');
        allowAvatarRef: KnockoutObservable<boolean> = ko.observable(false);
        allowDocRef: KnockoutObservable<boolean> = ko.observable(false);
        allowMapBrowse: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param?: IPersonAuth) {
            let self = this;
            if (param) {
                self.roleId(param.roleId);
                self.allowAvatarRef(!!param.allowAvatarRef);
                self.allowDocRef(!!param.allowDocRef);
                self.allowMapBrowse(!!param.allowMapBrowse);
            }
        }
    }

    interface ICatAuth {
        roleId: string;
        personInfoCategoryAuthId: string;
        allowPersonRef: number;
        allowOtherRef: number;
        allowOtherCompanyRef: number;
        selfPastHisAuth: number;
        selfFutureHisAuth: number;
        selfAllowAddHis: number;
        selfAllowDelHis: number;
        otherPastHisAuth: number;
        otherFutureHisAuth: number;
        otherAllowAddHis: number;
        otherAllowDelHis: number;
        selfAllowAddMulti: number;
        selfAllowDelMulti: number;
        otherAllowAddMulti: number;
        otherAllowDelMulti: number;
    }

    class CatAuth {

    }

    enum ATCS {
        ADD = 0,
        COPY = 1,
        UPDATE = 2
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

    enum REPL_KEYS {
        NORMAL = 0,
        REPLICATION = 1,
        ADDNEW = 2,
        OTHER = 3
    }

    interface IReloadData {
        id: string;
        infoId: string;
        categoryId: string;
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
        showAll?: boolean;
        employeeId: string;
    }
}