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
    import permision = service.getCurrentEmpPermision;

    const DEF_AVATAR = 'images/avatar.png',
        __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        ccgcomponent: any = {
            /** Common properties */
            systemType: 1, // シスッ�区�
            showEmployeeSelection: true, // 検索タイ�
            showQuickSearchTab: true, // クイヂ�検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業�め日利用
            showAllClosure: true, // 全�め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parame*/
            baseDate: moment.utc().toISOString(), // 基準日
            periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // 対象期間開始日
            periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // 対象期間終亗�
            inService: true, // 在職区�
            leaveOfAbsence: true, // 休�区�
            closed: true, // 休業区�
            retirement: false, // 退職区�

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参�可能な社員すべて
            showOnlyMe: true, // 自刁��
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とそ�配下�社員

            /** Advanced search properties */
            showEmployment: true, // 雔�条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 刡�条件
            showJobTitle: true, // 職位条件
            showWorktype: false, // 勤種条件
            isMutipleCheck: true, // 選択モー�

            /** Return data */
            returnDataFromCcg001: (data: any) => {
                let self = this,
                    id = ko.toJS(self.employee.employeeId),
                    emps = data.listEmployee,
                    exits = !!_.find(emps, m => m.employeeId == id);

                self.employee.employees(emps);
                if (emps.length > 0) {
                    if (!exits) {
                        self.employee.employeeId(emps[0].employeeId);
                    }
                } else {
                    self.employee.employeeId(undefined);
                }
            }
        };

        employee: any = {
            personId: ko.observable(''),
            employeeId: ko.observable(''),
            employeeIds: ko.observableArray([]),
            employees: ko.observableArray([])
        };

        saveAble: KnockoutObservable<boolean> = ko.observable(false);

        // resource id for title in category mode
        titleResource: KnockoutObservable<string> = ko.observable(text("CPS001_39"));

        layout: Layout = new Layout();
        
        // check quyen có thể delete employee ở đăng ký thông tin cá nhân 
        enaBtnManagerEmp: KnockoutObservable<boolean> = ko.observable(true);
        enaBtnDelEmp: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this,
                employee = self.employee,
                params: IParam = getShared("CPS001A_PARAMS") || { employeeId: undefined };

            employee.employeeId.subscribe(id => {
                self.block();
            });

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
                if (params && params.employeeId) {
                    employee.employeeIds([params.employeeId]);
                } else {
                    employee.employeeIds([__viewContext.user.employeeId]);
                }
            });

            setInterval(() => {
                let aut = _(self.layout.listItemCls())
                    .map((m: any) => m.items || undefined)
                    .filter(x => !!x)
                    .flatten() // flat set item
                    .flatten() // flat list item
                    .map((m: any) => !ko.toJS(m.readonly))
                    .filter(x => !!x)
                    .value();

                self.saveAble(!!aut.length && !hasError());
            }, 0);
            
            // check quyen có thể delete employee ở đăng ký thông tin cá nhân
            permision().done((data: Array<IPersonAuth>) => {
                if (data) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].functionNo == FunctionNo.No1_Allow_DelEmp) {
                            if (data[i].available == false) {
                                self.enaBtnManagerEmp(false);
                                self.enaBtnDelEmp(false);
                            }
                        }
                    }
                }
            });
        }

        reload() {
            let self = this,
                employee = self.employee,
                employees = ko.toJS(employee.employees),
                oids = ko.toJS(employee.employeeIds),
                nids = _.map(employees, m => m.employeeId),
                vids = _.clone(nids);

            if (!_.isEqual(oids.sort(), nids.sort())) {
                employee.employeeIds(vids);
            } else {
                employee.employeeIds.valueHasMutated();
            }
        }

        block() {
            let self = this;
            if (!$('.blockUI').length) {
                block();
                setTimeout(() => {
                    unblock();
                }, 30000);
            }
        }

        unblock() {
            setTimeout(() => {
                unblock();
            }, 50);
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
                if (getShared('CPS001B_VALUES')) {
                    self.reload();
                }
            });
        }

        unManagerEmployee() {
            let self = this;

            modal('../c/index.xhtml').onClosed(() => {
                self.reload();
            });
        }

        saveData() {
            let self = this,
                emp = self.employee,
                controls = self.layout.listItemCls(),
                inputs = self.layout.outData(),
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
                    info({ messageId: "Msg_15" }).then(function() {
                        self.reload();
                    });
                }).fail((mes : any) => {
                    self.unblock();
                    if (mes.messageId == "Msg_346") {
                        let lstCardNumber = _.map($('[data-code = IS00779]'), e => e.value);
                        let index = _.findLastIndex(lstCardNumber, function(o) { return o == mes.parameterIds[0]; });
                        $($('[data-code = IS00779]')[index]).ntsError('set', { messageId: "Msg_346" });
                    } else {
                        alert(mes.message);
                    }

                });
            }, 50);
        }

        change = (evt: IEventData) => {
            let self = this;

            self.layout.mode(evt.tab);

            if (evt.tab == TABS.LAYOUT) {
                if (evt.id != self.layout.id()) {
                    self.layout.id(evt.id);
                } else {
                    self.layout.id.valueHasMutated();
                }
            } else {
                self.layout.id(undefined);
                let query = {
                    infoId: evt.iid,
                    categoryId: evt.id,
                    categoryCode: evt.ccode,
                    standardDate: undefined,
                    personId: ko.toJS(__viewContext.viewModel.employee.personId),
                    employeeId: ko.toJS(__viewContext.viewModel.employee.employeeId)
                };

                if (evt.ctype) {
                    switch (evt.ctype) {
                        case IT_CAT_TYPE.SINGLE:
                            self.titleResource(text('CPS001_38'));
                            break;
                        case IT_CAT_TYPE.MULTI:
                            if (evt.act == 'add') {
                                self.titleResource(text('CPS001_39'));
                            } else {
                                self.titleResource(text('CPS001_40'));
                            }
                            break;
                        case IT_CAT_TYPE.CONTINU:
                        case IT_CAT_TYPE.NODUPLICATE:
                        case IT_CAT_TYPE.DUPLICATE:
                        case IT_CAT_TYPE.CONTINUWED:
                            if (['add', 'copy'].indexOf(evt.act) > -1) {
                                self.titleResource(text('CPS001_41'));
                            } else {
                                self.titleResource(text('CPS001_42'));
                            }
                            break;
                    }
                } else {
                    self.titleResource('');
                }


                service.getCatData(query).done(data => {
                    if (data) {
                        if (evt.act == 'copy') {
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
                        }

                        lv.removeDoubleLine(data.classificationItems);
                        self.layout.listItemCls(data.classificationItems || []);
                        _.defer(() => {
                            new vc(self.layout.listItemCls());
                            _.defer(() => {
                                self.unblock();
                            });
                        });
                    } else {
                        self.layout.listItemCls.removeAll();
                    }
                }).fail(mgs => {
                    self.layout.listItemCls.removeAll();
                    self.unblock();
                });
            }
        }
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        mode: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);
        showColor: KnockoutObservable<boolean> = ko.observable(false);

        outData: KnockoutObservableArray<any> = ko.observableArray([]);

        listItemCls: KnockoutObservableArray<any> = ko.observableArray([]);

        // standardDate of layout
        standardDate: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD"));

        constructor() {
            let self = this;

            self.id.subscribe(id => {
                if (id) {
                    self.showColor(true);

                    let sdate = self.standardDate(),
                        ddate = sdate && moment.utc(sdate, "YYYY/MM/DD").toDate() || moment.utc().toDate(),
                        query: ILayoutQuery = {
                            layoutId: id,
                            browsingEmpId: ko.toJS(__viewContext.viewModel.employee.employeeId),
                            standardDate: ddate
                        };

                    service.getCurrentLayout(query).done((data: any) => {
                        if (data) {
                            self.showColor(true);
                            self.standardDate(data.standardDate || undefined);

                            lv.removeDoubleLine(data.classificationItems);
                            self.listItemCls(data.classificationItems || []);

                            _.defer(() => {
                                new vc(self.listItemCls());
                                _.defer(() => {
                                    __viewContext.viewModel.unblock();
                                });
                            });
                        } else {
                            self.listItemCls.removeAll();
                        }
                    }).fail(mgs => {
                        self.showColor(true);
                        self.listItemCls.removeAll();
                        __viewContext.viewModel.unblock();
                    });
                } else {
                    self.showColor(false);
                    self.listItemCls.removeAll();
                    self.standardDate(moment.utc().format("YYYY/MM/DD"));
                }
            });
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

    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: IT_CAT_TYPE;
    }

    export enum TABS {
        LAYOUT = <any>"layout",
        CATEGORY = <any>"category"
    }

    export interface IPeregQuery {
        ctgId: string;
        ctgCd?: string;
        empId: string;
        standardDate: Date;
        infoId?: string;
    }

    export interface ILayoutQuery {
        layoutId: string;
        browsingEmpId: string;
        standardDate: Date;
    }

    export interface IPeregCommand {
        personId: string;
        employeeId: string;
        inputs: Array<IPeregItemCommand>;
    }

    export interface IPeregItemCommand {
        /** category code */
        categoryCd: string;
        /** Record Id, but this is null when new record */
        recordId: string;
        /** input items */
        items: Array<IPeregItemValueCommand>;
    }

    export interface IPeregItemValueCommand {
        definitionId: string;
        itemCode: string;
        value: string;
        'type': number;
    }

    export interface IParam {
        showAll?: boolean;
        employeeId: string;
    }

    export interface IEventData {
        id: string;
        iid?: string;
        tab: TABS;
        act?: string;
        ccode?: string;
        ctype?: IT_CAT_TYPE;
    }

    // define ITEM_CATEGORY_TYPE
    export enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5, // Duplicate history,
        CONTINUWED = 6 // Continuos history with end date
    }

    export enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
    }
    
    interface IPersonAuth {
        functionNo: number;
        functionName: string;
        available: boolean;
        description: string;
        orderNumber: number;
    }
    
    enum FunctionNo {
        No1_Allow_DelEmp = 1, // có thể delete employee ở đăng ký thông tin cá nhân
        No2_Allow_UploadAva = 2, // có thể upload ảnh chân dung employee ở đăng ký thông tin cá nhân
        No3_Allow_RefAva = 3,// có thể xem ảnh chân dung employee ở đăng ký thông tin cá nhân
        No4_Allow_UploadMap = 4, // có thể upload file bản đồ ở đăng ký thông tin cá nhân
        No5_Allow_RefMap = 5, // có thể xem file bản đồ ở đăng ký thông tin cá nhân
        No6_Allow_UploadDoc = 6,// có thể upload file điện tử employee ở đăng ký thông tin cá nhân
        No7_Allow_RefDoc = 7,// có thể xem file điện tử employee ở đăng ký thông tin cá nhân
        No8_Allow_Print = 8,  // có thể in biểu mẫu của employee ở đăng ký thông tin cá nhân
        No9_Allow_SetCoppy = 9,// có thể setting copy target item khi tạo nhân viên mới ở đăng ký mới thông tin cá nhân
        No10_Allow_SetInit = 10, // có thể setting giá trị ban đầu nhập vào khi tạo nhân viên mới ở đăng ký mới thông tin cá nhân
        No11_Allow_SwitchWpl = 11  // Lọc chọn lựa phòng ban trực thuộc/workplace trực tiếp theo bộ phận liên kết cấp dưới tại đăng ký thông tin cá nhân
    }
}
