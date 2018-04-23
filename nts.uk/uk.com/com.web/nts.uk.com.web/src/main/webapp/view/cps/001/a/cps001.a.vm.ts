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
    import permision4Cat = service.getPermision4Cat;
    import format = nts.uk.text.format;
    import lv = nts.layout.validate;
    import vc = nts.layout.validation;

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
            /** Common properties */
            systemType: 1, // システム区分
            showEmployeeSelection: true, // 検索タイプ
            showQuickSearchTab: true, // クイック検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業締め日利用
            showAllClosure: true, // 全締め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parame*/
            baseDate: moment.utc().toISOString(), // 基準日
            periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // 対象期間開始日
            periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // 対象期間終了日
            inService: true, // 在職区分
            leaveOfAbsence: true, // 休職区分
            closed: true, // 休業区分
            retirement: false, // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参照可能な社員すべて
            showOnlyMe: true, // 自分だけ
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment: true, // 雇用条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 分類条件
            showJobTitle: true, // 職位条件
            showWorktype: false, // 勤種条件
            isMutipleCheck: true, // 選択モード

            /** Return data */
            returnDataFromCcg001: (data: any) => {
                let self = this,
                    id = ko.toJS(self.employee.employeeId),
                    emps = data.listEmployee,
                    exits = !!_.find(emps, m => m.employeeId == id);

                self.employees(emps);
                if (emps.length > 0) {
                    if (!exits) {
                        self.employee.employeeId(emps[0].employeeId);
                    }
                } else {
                    self.employee.employeeId(undefined);
                }
            }
        };

        // current tab active id (layout/category)
        tab: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        employee: any = {
            roleId: ko.observable(''),
            personId: ko.observable(''),
            employeeId: ko.observable('')
        };

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

        // show or hide tabs
        hasLayout: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this,
                employee = self.employee,
                list = self.multipleData;

            self.block();

            self.hasLayout.subscribe(x => {
                if (!x) {
                    self.tab(TABS.CATEGORY);
                }
            });

            self.tab.subscribe(tab => {
                self.block();
                let loadData: IReloadData = getShared(RELOAD_DT_KEY),
                    personId: string = employee.personId(),
                    employeeId: string = employee.employeeId(),
                    selectFirstId = (sources: Array<any>, layoutData: any) => {
                        if (loadData) {
                            if (loadData.id) {
                                let exist = _(sources).map(x => x.optionValue).indexOf(loadData.id) != -1;
                                if (exist) {
                                    if (layoutData.id() == loadData.id) {
                                        layoutData.id.valueHasMutated();
                                    } else {
                                        layoutData.id(loadData.id);
                                    }
                                } else {
                                    layoutData.id(sources[0].optionValue);
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
                    layoutData.roleId(employee.roleId());

                    switch (tab) {
                        default:
                        case TABS.LAYOUT: // layout mode
                            service.getAllLayout(employeeId).done((data: Array<any>) => {
                                // prevent if slow networks
                                if (self.tab() != tab) {
                                    return;
                                }

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
                                // prevent if slow networks
                                if (self.tab() != tab) {
                                    return;
                                }

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

            employee.personId.subscribe(id => {
                _.each(list(), l => {
                    l.personId(employee.personId());
                });
            });

            employee.employeeId.subscribe(id => {
                self.block();
                let reload = getShared(RELOAD_KEY);
                if (id) {
                    _.each(list(), l => {
                        l.employeeId(id);
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
                employee = self.employee,
                logInId: string = __viewContext.user.employeeId,
                params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined };

            if (reload) {
                let emps = self.employees(),
                    single = emps.length == 1,
                    ids = _.map(emps, x => x.employeeId),
                    old_index = _.indexOf(emps.map(x => x.employeeId), employee.employeeId());

                self.employees.removeAll();

                $('#ccg001-btn-search-all>div').trigger('click');

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
                    if (single) {
                        let exist = _.find(employees, m => m.employeeId == employee.employeeId());
                        if (exist) {
                            self.employees(_.filter(employees, m => m.employeeId == employee.employeeId()));
                        } else {
                            self.employees(_.filter(employees, m => m.employeeId == logInId));
                            employee.employeeId(logInId);
                        }
                    } else {
                        self.employees(_.filter(employees, m => ids.indexOf(m.employeeId) > -1));
                    }

                    let first = _.find(self.employees(), x => x.employeeId == employee.employeeId());
                    if (first) {
                        employee.employeeId.valueHasMutated();
                    } else {
                        first = _.find(self.employees(), (x, i) => i == old_index) || _.last(self.employees());
                        if (first) {
                            employee.employeeId(first.employeeId);
                        } else {
                            if (employee.employeeId()) {
                                employee.employeeId(undefined);
                            } else {
                                employee.employeeId.valueHasMutated();
                            }
                        }
                    }

                    service.getAllLayout(employee.employeeId())
                        .done((data: Array<any>) => {
                            self.hasLayout(!!data.length);
                        });
                });
            } else {
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                    if (params && params.employeeId) {
                        $('#ccg001-btn-search-all>div').trigger('click');
                    } else {
                        $('#ccg001-btn-only-me>div').trigger('click');
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
                            self.employees(_.filter(employees, m => m.employeeId == params.employeeId));
                        }
                        employee.employeeId(self.employees()[0] ? self.employees()[0].employeeId : undefined);
                        setShared(RELOAD_KEY, true);

                        service.getAllLayout(employee.employeeId())
                            .done((data: Array<any>) => {
                                self.hasLayout(!!data.length);
                            });
                    });
                });
            }
        }

        block() {
            if (!$('.blockUI').length) {
                block();
            }
        }

        deleteEmployee() {
            let self = this,
                emp = self.employee,
                logInId: string = __viewContext.user.employeeId;

            if (emp.employeeId() == logInId) {
                // show message if delete self
                alert({ messageId: 'Msg_1109' });
                return;
            }

            setShared('CPS001B_PARAMS', {
                sid: emp.employeeId(),
                pid: emp.personId()
            });

            modal('../b/index.xhtml').onClosed(() => {
                self.start();
            });
        }

        unManagerEmployee() {
            let self = this;

            modal('../c/index.xhtml').onClosed(() => {
                self.start();
            });
        }

        saveData() {
            let self = this,
                emp = self.employee,
                layouts = self.multipleData().map(x => x.layout()),
                controls = _.flatten(layouts.map(x => x.listItemCls())),
                inputs = _.flatten(layouts.map(x => x.outData())),
                command: IPeregCommand = {
                    personId: emp.personId(),
                    employeeId: emp.employeeId(),
                    inputs: inputs
                };

            // trigger change of all control in layout
            lv.checkError(controls);

            setTimeout(() => {
                if (hasError()) {
                    $('#func-notifier-errors').trigger('click');
                    return;
                }

                // push data layout to webservice
                self.block();
                service.saveCurrentLayout(command).done((selecteds: Array<string>) => {
                    let firstData: MultiData = _.first(self.multipleData()) || new MultiData(),
                        saveData: IReloadData = {
                            id: firstData.id(),
                            infoId: selecteds[0] || firstData.infoId(),
                            categoryId: firstData.categoryId()
                        };

                    setShared(RELOAD_DT_KEY, saveData);
                    setShared(REPL_KEY, REPL_KEYS.NORMAL);

                    info({ messageId: "Msg_15" }).then(function() {
                        self.start();
                    });
                }).fail((mes) => {
                    unblock();
                    alert(mes.message);
                });
            }, 100);
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
        show: KnockoutObservable<boolean>;
        add: KnockoutObservable<boolean>;
        remove: KnockoutObservable<boolean>;
        replace: KnockoutObservable<boolean>;
    }

    class Layout {
        showColor: KnockoutObservable<boolean> = ko.observable(false);

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
        htitle: KnockoutObservable<string> = ko.observable('');
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
                        self.changeTitle(ATCS.ADD);
                        setShared(REPL_KEY, REPL_KEYS.ADDNEW);

                        self.infoId(undefined);
                        //self.id.valueHasMutated();

                        if (callback && _.isFunction(callback)) {
                            callback();
                        }
                    }
                }).fail(msg => {
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

                            let id = self.infoId(),
                                values = _(ko.toJS(self.gridlist))
                                    .map((x: IListData) => x.optionValue)
                                    .value(),
                                index = _(values).indexOf(id),
                                selected = index + 1 == values.length ? values[index - 1] : values[index + 1],
                                saveData: IReloadData = {
                                    id: self.id(),
                                    infoId: selected,
                                    categoryId: self.categoryId()
                                },
                                query = {
                                    recordId: self.infoId(),
                                    personId: self.personId(),
                                    employeeId: self.employeeId(),
                                    categoryId: category.categoryCode()
                                };

                            setShared(RELOAD_DT_KEY, saveData);
                            setShared(REPL_KEY, REPL_KEYS.NORMAL);

                            service.removeCurrentCategoryData(query).done(x => {
                                info({ messageId: "Msg_16" }).then(() => {
                                    self.infoId(undefined);
                                    self.id.valueHasMutated();

                                    if (callback && _.isFunction(callback)) {
                                        callback();
                                    }
                                });
                            }).fail(msg => {
                                alert(msg);
                            });;
                        });
                    }
                }).fail(msg => {
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
                        self.changeTitle(ATCS.COPY);
                        setShared(REPL_KEY, REPL_KEYS.REPLICATION);
                        self.infoId.valueHasMutated();
                        //self.id.valueHasMutated();

                        if (callback && _.isFunction(callback)) {
                            callback();
                        }
                    }
                }).fail(msg => {
                });
            }
        }

        permisions: Permisions = {
            show: ko.observable(true),
            add: ko.observable(false),
            remove: ko.observable(false),
            replace: ko.observable(false)
        };

        combobox: KnockoutObservableArray<IListData> = ko.observableArray([]);
        gridlist: KnockoutObservableArray<IListData> = ko.observableArray([]);
        categories: KnockoutObservableArray<IListData> = ko.observableArray([]);

        // object layout
        layout: KnockoutObservable<Layout> = ko.observable(new Layout());

        block() {
            if (!$('.blockUI').length) {
                block();
            }
        }

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
                self.block();
                let mode: TABS = self.mode();

                setShared(REPL_KEY, REPL_KEYS.NORMAL);

                if (id) {
                    if (mode == TABS.CATEGORY) {
                        let option = _.find(self.combobox(), x => x.optionValue == id);

                        if (option) {
                            let icat: ICategory = option.item;

                            cat.categoryCode(icat.categoryCode);
                            cat.categoryType(icat.categoryType);

                            service.getCatChilds(id).done(data => {
                                cat.hasChildrens(data.length > 1);
                            });
                        } else {
                            cat.categoryCode(undefined);
                            cat.categoryType(undefined);
                        }
                    }
                    self.categoryId.valueHasMutated();
                } else {
                    unblock();
                }
            });

            cat.hasChildrens.subscribe(h => {
                if (!h) {
                    self.categoryId(undefined);
                }
            });

            self.categoryId.subscribe(cid => {
                self.block();
                let id: string = self.id(),
                    mode: TABS = self.mode(),
                    loadData: IReloadData = getShared(RELOAD_DT_KEY);

                if (id) {
                    if (mode == TABS.LAYOUT) {
                        self.infoId(undefined);

                        let sdate = layout.standardDate(),
                            ddate = sdate && moment.utc(sdate, "YYYY/MM/DD").toDate() || moment.utc().toDate(),
                            query: ILayoutQuery = {
                                layoutId: id,
                                browsingEmpId: self.employeeId(),
                                standardDate: ddate
                            };

                        service.getCurrentLayout(query).done((data: any) => {
                            if (data) {
                                layout.showColor(true);
                                layout.standardDate(data.standardDate || undefined);

                                lv.removeDoubleLine(data.classificationItems);
                                layout.listItemCls(data.classificationItems || []);

                                _.defer(() => {
                                    new vc(layout.listItemCls());
                                    $('.drag-panel input:first').focus();
                                    unblock();
                                });
                            } else {
                                layout.listItemCls.removeAll();
                            }
                            setShared(RELOAD_DT_KEY, undefined);
                        }).fail(mgs => {
                            layout.showColor(true);
                            layout.listItemCls.removeAll();
                            unblock();
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
                                layout.showColor(false);

                                self.changeTitle(ATCS.UPDATE);

                                service.getCatData(query).done(data => {
                                    if (data) {
                                        lv.removeDoubleLine(data.classificationItems);
                                        layout.listItemCls(data.classificationItems || []);
                                        _.defer(() => {
                                            new vc(layout.listItemCls());
                                            $('.drag-panel input:first').focus();
                                            unblock();
                                        });
                                    } else {
                                        layout.listItemCls.removeAll();
                                    }
                                    setShared(RELOAD_DT_KEY, undefined);
                                }).fail(mgs => {
                                    layout.listItemCls.removeAll();
                                    unblock();
                                    setShared(RELOAD_DT_KEY, undefined);
                                });
                                break;
                            case IT_CAT_TYPE.MULTI:
                                // http://192.168.50.4:3000/issues/87571
                                self.infoId(undefined);
                                self.gridlist.removeAll();
                                layout.listItemCls.removeAll();
                                unblock();
                                break;
                            case IT_CAT_TYPE.CONTINU:
                            case IT_CAT_TYPE.CONTINUWED:
                            case IT_CAT_TYPE.DUPLICATE:
                            case IT_CAT_TYPE.NODUPLICATE:
                                let rep: number = getShared(REPL_KEY) || REPL_KEYS.NORMAL;

                                if (rep == REPL_KEYS.NORMAL) {
                                    service.getHistData(query).done((data: Array<any>) => {
                                        let _title = _.find(data, x => !x.optionValue);
                                        if (_title) {
                                            self.htitle(_title.optionText);
                                        }

                                        data = _.filter(data, x => !!x.optionValue);
                                        if (data && data.length) {
                                            self.gridlist(data);
                                            self.changeTitle(ATCS.UPDATE);

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
                                            self.changeTitle(ATCS.ADD);
                                            setShared(REPL_KEY, undefined);

                                            if (self.infoId()) {
                                                self.infoId(undefined);
                                            } else {
                                                self.infoId.valueHasMutated();
                                            }
                                        }
                                        setShared(RELOAD_DT_KEY, undefined);
                                    }).fail(mgs => {
                                        self.gridlist.removeAll();
                                        self.changeTitle(ATCS.ADD);
                                        setShared(REPL_KEY, undefined);
                                        setShared(RELOAD_DT_KEY, undefined);

                                        if (self.infoId()) {
                                            self.infoId(undefined);
                                        } else {
                                            self.infoId.valueHasMutated();
                                        }
                                    });
                                } else {
                                    self.infoId.valueHasMutated();
                                }
                                break;
                        }
                    }
                } else {
                    unblock();
                }
            });

            self.infoId.subscribe(infoId => {
                self.block();
                let id = self.id(),
                    mode: TABS = self.mode(),
                    ctp = cat.categoryType(),
                    layout = self.layout(),
                    index = _.indexOf(_.map(self.gridlist(), x => x.optionValue), infoId);

                if (id && mode == TABS.CATEGORY) {
                    let catid = self.categoryId(),
                        rep: number = getShared(REPL_KEY) || REPL_KEYS.NORMAL;

                    if ([REPL_KEYS.NORMAL, REPL_KEYS.REPLICATION, REPL_KEYS.ADDNEW].indexOf(rep) > -1) {
                        let catid = self.categoryId(),
                            query = {
                                infoId: infoId,
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

                                let removed: Array<any> = [],
                                    clearRecord = (m: any) => {
                                        if (!_.isArray(m)) {
                                            m.recordId = undefined;
                                        } else {
                                            _.each(m, k => {
                                                k.recordId = undefined;
                                            });
                                        }
                                    };
                                _.each(data.classificationItems, (c: any, i: number) => {
                                    if (_.has(c, "items") && _.isArray(c.items)) {
                                        _.each(c.items, m => clearRecord(m));

                                        // clear value of first set item
                                        if (!removed.length) {
                                            removed = _.filter(c.items, (x: any) => x.item && x.item.dataTypeValue == ITEM_SINGLE_TYPE.DATE);
                                            if (removed.length) {
                                                _.each(c.items, m => m.value = undefined);
                                            }
                                        }
                                    }
                                });
                            } else if (self.infoId()) {
                                self.changeTitle(ATCS.UPDATE);
                            }
                            layout.showColor(false);
                            lv.removeDoubleLine(data.classificationItems);
                            layout.listItemCls(data.classificationItems || []);

                            _.defer(() => {
                                new vc(layout.listItemCls());
                                $('.drag-panel input:first').focus();
                                unblock();
                            });

                            let roleId = self.roleId(),
                                catId = self.categoryId() || self.id(),
                                category: ICategory = ko.toJS(self.category);

                            if (category.categoryCode != "CS00003") {
                                self.permisions.show(true);
                            } else {
                                self.permisions.show(false);
                            }

                            permision4Cat(roleId, catId).done((perm: ICatAuth) => {
                                let selEmId: string = self.employeeId(),
                                    logInId: string = __viewContext.user.employeeId;

                                if (perm && !!(selEmId == logInId ? (perm.selfAllowAddHis && perm.selfFutureHisAuth == 3) : (perm.otherAllowAddHis && perm.otherFutureHisAuth == 3))) {
                                    self.permisions.add(true);
                                    self.permisions.replace(true);
                                } else {
                                    self.permisions.add(false);
                                    self.permisions.replace(false);
                                }

                                if (perm && !!(selEmId == logInId ? perm.selfAllowDelHis : perm.otherAllowDelHis)) {
                                    if (index > -1) {
                                        if (index == 0) {
                                            self.permisions.remove(true);
                                        } else {
                                            let cat: ICategory = ko.toJS(self.category);
                                            if (cat.categoryType == IT_CAT_TYPE.NODUPLICATE) {
                                                self.permisions.remove(true);
                                            } else {
                                                self.permisions.remove(false);
                                            }
                                        }
                                    } else {
                                        self.permisions.remove(false);
                                    }
                                } else {
                                    self.permisions.remove(false);
                                }
                            }).fail(msg => {
                                unblock();
                            });
                        });
                    } else {
                        setShared(REPL_KEY, REPL_KEYS.NORMAL);
                    }
                } else {
                    unblock();
                }
            });
        }

        changeTitle = (action: ATCS) => {
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

    enum ATCS {
        ADD = 0,
        COPY = 1,
        UPDATE = 2
    }

    enum TABS {
        LAYOUT = <any>"layout",
        CATEGORY = <any>"category"
    }
    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
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
