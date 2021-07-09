/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module cmm015.a.viewmodel {

    import getText = nts.uk.resource.getText;

    const API = {
        getEmpInWkp: 'com/screen/cmm015/getEmployeesInWorkplace', //職場の所属社員一覧を取得する
        getTransferOfDay: 'com/screen/cmm015/getTransferOfDay', //当日の異動を取得する
        regisJTC: 'com/screen/cmm015/regisJTC', //職位異動の登録をする
        createTransferList: 'com/screen/cmm015/createTransferList',//異動一覧を作成する
        addAWH: 'com/screen/cmm015/addAWH',
        transOnApproved: 'com/screen/cmm015/transOnApproved'
    };

    const MAX_DATE: string = '9999/12/31';
    const ERRORSCODE: string = '#FF0000';
    const COMMA: string = '、';

    @bean()
    export class ScreenModel extends ko.ViewModel {

        transferDate: KnockoutObservable<string> = ko.observable('');
        
        treeGridLeft: any;
        treeGridRight: any;

        selectedIdLeft: KnockoutObservable<string> = ko.observable('');
        baseDateLeft: KnockoutObservable<Date> = ko.observable((new Date(moment.utc().subtract(1, 'd').toString())));
        listDataLeft: KnockoutObservableArray<any> = ko.observableArray([]);

        selectedIdRight: KnockoutObservable<string> = ko.observable('');
        baseDateRight: KnockoutObservable<Date> = ko.observable(new Date(moment.utc().toString()));
        listDataRight: KnockoutObservableArray<any> = ko.observableArray([]);

        incumbentLeft: KnockoutObservable<boolean> = ko.observable(true);
        closedLeft: KnockoutObservable<boolean> = ko.observable(false);
        leaveLeft: KnockoutObservable<boolean> = ko.observable(false);
        retireeLeft: KnockoutObservable<boolean> = ko.observable(false);

        incumbentRight: KnockoutObservable<boolean> = ko.observable(true);
        closedRight: KnockoutObservable<boolean> = ko.observable(false);
        leaveRight: KnockoutObservable<boolean> = ko.observable(false);
        retireeRight: KnockoutObservable<boolean> = ko.observable(false);

        columnsSource: KnockoutObservableArray<any>;
        tableDatasSource: KnockoutObservableArray<DataTable> = ko.observableArray([]);
        currentCodeListSource: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedIndexSource: number = 0;

        columnsDest: KnockoutObservableArray<any>;
        tableDatasDest: KnockoutObservableArray<DataTable> = ko.observableArray([]);
        currentCodeListDest: KnockoutObservable<string> = ko.observable('');
        selectedIndexDest: number = 1;

        columnsHistory: KnockoutObservableArray<any>;
        tableDatasHistory: KnockoutObservableArray<DataTableHistory> = ko.observableArray([]);
        currentCodeListHistory: KnockoutObservableArray<any> = ko.observableArray([]);

        dateValue: KnockoutObservable<any> = ko.observable({
            startDate: moment.utc().format('YYYY/MM/DD'),
            endDate: MAX_DATE
        });
        startDateString: KnockoutObservable<string> = ko.observable('');
        endDateString: KnockoutObservable<string> = ko.observable('');

        isEnableA3: KnockoutObservable<boolean> = ko.observable(true);
        isEnableA6: KnockoutObservable<boolean> = ko.observable(true);
        isEnableA11: KnockoutObservable<boolean> = ko.observable(true);

        enableTransferDate: KnockoutObservable<boolean> = ko.observable(false);

        baseDateLeftText: KnockoutComputed<string>;
        baseDateRightText: KnockoutComputed<string>;

        transfereeOnApproved: KnockoutObservable<TransfereeOnApproved> = ko.observable({} as any);

        created() {
            const vm = this;
            vm.$i18n('CMM015_52');
            vm.treeGridLeft = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: false,
                startMode: 0,
                selectedId: vm.selectedIdLeft,
                baseDate: vm.baseDateLeft,
                selectType: 3,
                isShowSelectButton: false,
                isDialog: false,
                maxRows: 11,
                tabindex: 3,
                systemType : 2,
                listDataDisplay: vm.listDataLeft,
                hasPadding: false
            };

            vm.treeGridRight = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: false,
                startMode: 0,
                selectedId: vm.selectedIdRight,
                baseDate: vm.baseDateRight,
                selectType: 3,
                isShowSelectButton: false,
                isDialog: false,
                maxRows: 11,
                tabindex: 3,
                systemType : 2,
                listDataDisplay: vm.listDataRight,
                hasPadding: false
            }

            vm.columnsSource = ko.observableArray([
                { headerText: '', key: 'id', hidden: true },
                { headerText: vm.$i18n('CMM015_21'), key: 'code', width: 115, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_22'), key: 'name', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_23'), key: 'jt', width: 80, headerCssClass: 'text-left' }
            ]);

            vm.columnsDest = ko.observableArray([
                { headerText: '', key: 'id', hidden: true },
                { headerText: '', key: 'css', hidden: true },
                { headerText: '', key: 'jtID', hidden: true },
                {
                    headerText: vm.$i18n('CMM015_21'), key: 'code', width: 115, headerCssClass: 'text-left',
                    template: '<div class="${cssWKP}">${code}</div>'
                },
                {
                    headerText: vm.$i18n('CMM015_22'), key: 'name', width: 140, headerCssClass: 'text-left',
                    template: '<div class="${cssWKP}">${name}</div>'
                },
                {
                    headerText: vm.$i18n('CMM015_23'), key: 'jt', width: 120, headerCssClass: 'text-left',
                    template: '<div class="${cssJT}">${jt}</div>'
                },
                {
                    headerText: vm.$i18n('CMM015_23'), key: 'jtc', width: 100, headerCssClass: 'text-left',
                    template: '<a class="custom-link ${cssJT}" jtid="${jtID}" emp="${id}" style="color: #0D86D1">${jtc}</a>'
                }
            ]);

            vm.columnsHistory = ko.observableArray([
                { headerText: '', key: 'key', hidden: true },
                { headerText: vm.$i18n('CMM015_45'), key: 'startDate', width: 120, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_46'), key: 'sCD', width: 80, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_47'), key: 'sName', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_48'), key: 'prevWkpName', width: 220, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_49'), key: 'prevPositionName', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_50'), key: 'postWkpName', width: 240, headerCssClass: 'text-left dest-wkp'},
                { headerText: vm.$i18n('CMM015_51'), key: 'postPositionName', width: 140, headerCssClass: 'text-left dest-wkp' }
            ]);

            vm.baseDateLeftText = ko.computed(() => {
                const date = moment.utc(vm.baseDateLeft()).format('YYYY/MM/DD');
                return (nts.uk.time as any).applyFormat('Long_YMD', [date]);
            });
            vm.baseDateRightText = ko.computed(() => {
                const date = moment.utc(vm.baseDateRight()).format('YYYY/MM/DD');
                return (nts.uk.time as any).applyFormat('Long_YMD', [date]);
            });
            
        }

        mounted() {
            const vm = this;
            vm.startPage();
            vm.startDateString.subscribe(value => {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();        
            });
            
            vm.endDateString.subscribe(value => {
                vm.dateValue().endDate = value;   
                vm.dateValue.valueHasMutated();      
            });

            vm.selectedIdLeft.subscribe(() => {
                vm.loadDataWkp(Table.LEFT);
            });

            vm.selectedIdRight.subscribe(() => {
                vm.loadDataWkp(Table.RIGHT);
            });

            vm.transferDate.subscribe(() => {
                vm
                    .$validate('#A1_2')
                    .then(valid => {
                        if (!valid) {
                            vm.enableTransferDate(false);
                        }
                    })
            });

            $('#A13').click((v) => { //「職位の選択」ダイアログで職位変更
                if (!v.target.classList.contains('custom-link')) {
                    return;
                }

                const employeeID = v.target.getAttribute('emp');

                nts.uk.ui.windows.setShared('inputCDL004', {
                    isMultiple: false,
                    isShowNoSelectRow: true,
                    selectedCodes: v.target.getAttribute('jtID'),
                    isShowBaseDate: false,
                    baseDate: moment(vm.baseDateRight()).utc().toISOString()
                });

                nts.uk.ui.windows.sub
                    .modal('com', '/view/cdl/004/a/index.xhtml')
                    .onClosed(() => {
                        const output = nts.uk.ui.windows.getShared('outputCDL004');
                        if (output) {
                            const index = _.findIndex(vm.tableDatasDest(), (item: DataTable) => item.id === employeeID);
                            const data: DataTable = vm.tableDatasDest()[index];
                            if (vm.uiProcess20(data.jtID, output)) return;

                            // 職位異動の登録をする
                            vm
                                .$ajax('com', API.regisJTC, {
                                    sid: data.id,
                                    jobTitleId: output,
                                    startDate: moment.utc(vm.transferDate()).toISOString(),
                                    endDate: moment.utc(MAX_DATE).toISOString()
                                })
                                .then(() => {
                                    vm.loadDataWkp(Table.RIGHT);
                                    // UI処理[6]
                                    vm.currentCodeListDest('');
                                    vm.getTransferOfDay(new Date(vm.transferDate()));
                                    vm.createTransferList();
                                    vm.$dialog.info({ messageId: 'Msg_15' });
                                })
                                .fail(({messageId}) => vm.$dialog.error({ messageId: messageId }));
                            
                        }
                    });
            });
            
        }

        startPage() {
            const vm = this;
            $.when(
                $('#tree-grid-left').ntsTreeComponent(vm.treeGridLeft),
                $('#tree-grid-right').ntsTreeComponent(vm.treeGridRight)
            )
            .then(() => vm.getTransferOfDay(new Date()))
            .then(() => vm.createTransferList());
        }

        clickMeans() {
            const vm = this;
            if (_.isNil(vm.transferDate())) {
                vm.$dialog.error({ messageId: 'Msg_2115' });
                return;
            }
            vm.$validate('#A1_2').then(valid => {
                if (!valid) {
                    vm.enableTransferDate(false);
                    return;
                }
                const date = ko.unwrap<string>(vm.transferDate);
                vm.baseDateRight(new Date(vm.transferDate()));
                vm.baseDateLeft(new Date(moment.utc(date).subtract(1, 'd').toString()));
                vm.enableTransferDate(true);
                vm
                    .reloadKcp()
                    .then(() => vm.getTransferOfDay(new Date(vm.transferDate())));

            })
        }

        reloadKcp(): JQueryPromise<any> {
            const vm = this, dfd = $.Deferred();
            $('#tree-grid-left').ntsTreeComponent(vm.treeGridLeft);
            $('#tree-grid-right').ntsTreeComponent(vm.treeGridRight);

            vm
                .loadDataWkp(Table.LEFT)
                .then(() => vm.loadDataWkp(Table.RIGHT))
                .then(() => dfd.resolve())
                .fail(err => {
                    vm.$dialog.error(err);
                    dfd.reject();
                })
            return dfd.promise();
        }

        onClickTransfer() {
            const vm = this;
            vm.createTransferList();
        }

        /**
         * 「異動登録ボタン」を押下する
         */
        onClickRegister() {
            const vm = this;
            if (vm.uiProcess14()) {
                return;
            }
            // UI処理[18]
            $('#A9 tr').children().css({ color: ''})

            const selectedSidsource: string[] = [];
            const param = _.map(vm.currentCodeListSource(), sid => {
                selectedSidsource.push(sid);
                return {
                    employeeId: sid,
                    workplaceId: vm.selectedIdRight(),
                    startDate: moment.utc(new Date(vm.transferDate())).toISOString(),
                    endDate: moment.utc(new Date(MAX_DATE)).toISOString()
                };
            });
            vm
                .$ajax('com', API.addAWH, param)
                .then((res: any[]) => {
                    // If has erros
                    const ifErrors = () => {
                        const errorList = _.map(res, i => i.errorLst[0]);
                        vm.$dialog
                            .error({ messageId: 'Msg_102' })
                            .then(() => ifNotErrors());
                        vm.uiProcess17(errorList);
                    }

                    // If not has errors
                    const ifNotErrors = () => {
                        if (res.length >= vm.currentCodeListSource().length) return;
                        vm
                            .loadDataWkp(Table.RIGHT)
                            .then(() => {
                                // UI処理[6]
                                vm.currentCodeListDest('');
                                // 当日の異動を取得する
                                return vm.getTransferOfDay(new Date(vm.transferDate()));
                            })
                            .then(() => {
                                // 異動一覧を作成する
                                return vm.createTransferList();
                            })
                            .then(() => {
                                //UI処理[16]
                                vm.currentCodeListSource.removeAll();
                                // UI処理[15]
                                vm.$dialog
                                    .info({ messageId: 'Msg_15' })
                                    .then(() => vm.transOnApproved(selectedSidsource));
                            });
                    };
                    
                    if (_.isEmpty(res)) {
                        ifNotErrors();
                    } else {
                        ifErrors();
                    }
                })
                .fail(err => vm.$dialog.error({ messageId: err.messageId }));
        }

        /**
         * 職場の所属社員一覧を取得する
         * @param table LEFT OR RIGHT
         */
        loadDataWkp(table: Table): JQueryPromise<any> {
            const vm = this, dfd = $.Deferred();
            if (_.isNil(vm.selectedIdLeft()) && _.isNil(vm.selectedIdRight())) {
                return dfd.promise();
            }
            const paramLeft = {
                wkpIds: [vm.selectedIdLeft()],
                referDate: moment.utc(vm.baseDateLeft()),
                incumbent: vm.incumbentLeft(),
                closed: vm.closedLeft(),
                leave: vm.leaveLeft(),
                retiree: vm.retireeLeft()
            };

            const paramRight = {
                wkpIds: [vm.selectedIdRight()],
                referDate: moment.utc(vm.baseDateRight()),
                incumbent: vm.incumbentRight(),
                closed: vm.closedRight(),
                leave: vm.leaveRight(),
                retiree: vm.retireeRight()
            };

            const isLeft = table === Table.LEFT;

            const param = isLeft ? paramLeft : paramRight;
            
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('com', API.getEmpInWkp, param))
                .then((res: EmployeesInWorkplace[]) => {
                    const tableDatas = _.map(res, item => new DataTable(item, isLeft ? '' : vm.$i18n('CMM015_35')));

                    if (isLeft) {
                        vm.tableDatasSource.removeAll();
                        vm.currentCodeListSource.removeAll();
                        vm.tableDatasSource(tableDatas);

                    } else {
                        vm.tableDatasDest.removeAll();
                        vm.currentCodeListDest('');
                        vm.tableDatasDest(tableDatas);
                    }
                    dfd.resolve();
                })
                .fail(err => {
                    vm.$dialog.error(err);
                    dfd.reject();
                })
                .always(() => vm.$blockui('clear'));
            return dfd.promise();
        }

        uiProcess02() {
            const vm = this;
            //List<承認ルート状況>
            const approvalRoutes = vm.transfereeOnApproved().approvalRoutes;
            //List<社員情報>
            const empInfors = vm.transfereeOnApproved().empInfors;
            //List<職場情報一覧>
            const wkpListInfo = vm.transfereeOnApproved().wkpListInfo;

            let msg2110 = '';
            let msg2111 = '';
            let msg2112 = '';

            _.map(approvalRoutes, i => {
                const employeeName = _.find(empInfors, ii => ii.employeeId === i.sid).businessName;
                //承認職場リスト
                if (!_.isEmpty(i.workplaceList)) {
                    const aprWkpName = _
                        .map(i.workplaceList, ii => _.find(wkpListInfo, iii => ii === iii.workplaceId).workplaceName)
                        .join(COMMA);
                    
                    msg2110 += `${vm.$i18n.message('Msg_2110', [employeeName, aprWkpName])}<br/>`;
                }
                
                //承認対象社員リスト
                if (!_.isEmpty(i.approveEmpList)) {
                    const aprEmpName = _.
                        map(i.approveEmpList, ii => _.find(empInfors, iii => ii === iii.employeeId).businessName)
                        .join(COMMA);

                    msg2111 += `${vm.$i18n.message('Msg_2111', [employeeName, aprEmpName])}<br/>`;
                }
                
                //指定者の承認者リスト
                if (!_.isEmpty(i.approverList)) {
                    const aprListName = _
                        .map(i.approverList, ii => _.find(empInfors, iii => ii === iii.employeeId).businessName)
                        .join(COMMA);

                    msg2112 += `${vm.$i18n.message('Msg_2112', [employeeName, aprListName])}<br/>`;
                }
                
            });

            const msg = `${msg2110}${msg2111}${msg2112}`;
            if (!_.isEmpty(msg)) {
                vm.$dialog.info(msg);
            }
        }
        
        uiProcess08() {

        }

        uiProcess14(): boolean {
            const vm = this;
            if (_.isEmpty(vm.currentCodeListSource())) {
                vm.$dialog.error({ messageId: 'Msg_2107' });
                return true;
            }
            return false;
        }

        uiProcess17(errorList: string[]) {
            _.forEach(errorList, i => {
                $(`#A9 [data-id=${i}]`).children().css({color: ERRORSCODE})
            });
        }

        uiProcess20(jtID: string, newID: string): boolean {
            const vm = this;
            if(jtID === newID) {
                vm.$dialog.error({ messageId: 'Msg_2120' });
                return true;
            }
            return false;
        }

        //当日の異動を取得する
        getTransferOfDay(date: Date): JQueryPromise<any> {
            const vm = this, dfd = $.Deferred();
            const dataTableDest = ko.unwrap<DataTable[]>(vm.tableDatasDest);
            const sids: any[] = _.map(dataTableDest, e => e.id);
            const baseDate = moment.utc(date).toISOString();
            vm
                .$ajax('com', API.getTransferOfDay, {sids, baseDate})
                .then(({wkpEmployees, jtEmployees}: TransferOfDay) => {
                    _.forEach(dataTableDest, (e, index) => {
                        if (_.includes(wkpEmployees, e.id)) {
                            // e.cssWKP = 'red';
                            $($($('#A13 tr')[index]).children()[0]).css({ color: ERRORSCODE });
                            $($($('#A13 tr')[index]).children()[1]).css({ color: ERRORSCODE });
                        }
                        if (_.includes(jtEmployees, e.id)) {
                            // e.cssJT = 'red'
                            $($($('#A13 tr')[index]).children()[2]).css({ color: ERRORSCODE });
                            $($($('#A13 tr')[index]).children()[3]).css({ color: ERRORSCODE });
                        }
                    });
                    dfd.resolve();
                })
                .fail(err => {
                    vm.$dialog.error(err);
                    dfd.reject();
                });
            
            return dfd.promise();
        }


        // Data SQ 異動一覧を作成する
        awhItems: any[];
        ajthItems: any[];
        empInfors: any[];
        wkpListInfo: any[];
        jtInfor: any[];

        //異動一覧を作成する
        createTransferList(): JQueryPromise<any> {
            const vm = this, dfd = $.Deferred();
            const startDate = moment.utc(new Date(vm.dateValue().startDate)).toISOString();
            const endDate = moment.utc(new Date(vm.dateValue().endDate)).toISOString();
            vm
                .$ajax('com', API.createTransferList, { startDate, endDate })
                .then(({awhItems, ajthItems, empInfors, wkpListInfo, jtInfor}: TransferList) => {
                    // 職場履歴リスト
                    vm.awhItems = awhItems;
                    // 職位履歴リスト
                    vm.ajthItems = ajthItems;
                    // List<社員一覧情報>
                    vm.empInfors = empInfors;
                    // List<職場一覧情報>
                    vm.wkpListInfo = wkpListInfo;
                    // List<職位一覧情報>
                    vm.jtInfor = jtInfor;

                    vm.uiProcess25();
                    dfd.resolve();
                })
                .fail(err => {
                    vm.$dialog.error(err);
                    dfd.reject();
                });

            return dfd.promise();
        }

        //異動者が承認ルートにいるか確認する
        transOnApproved(sids: string[]): JQueryPromise<any> {
            const vm = this, dfd = $.Deferred();
            const baseDate = moment.utc(new Date(vm.transferDate())).toISOString();
            vm
                .$ajax('com', API.transOnApproved, { sids, baseDate })
                .then((res:TransfereeOnApproved) => {
                    vm.transfereeOnApproved(res);
                    vm.uiProcess02();
                    dfd.resolve();
                })
                .fail(err => {
                    vm.$dialog.error({ messageId: err.messageId });
                    dfd.reject();
                });

            return dfd.promise();
        }

        uiProcess25() {
            const vm = this;

            const condition = (date: string): boolean => {
                return moment.utc(new Date(date))
                    .isBetween(
                        moment.utc(new Date(vm.dateValue().startDate)),
                        moment.utc(new Date(vm.dateValue().endDate))
                    );
            };

            const compare = (firstDate: string, secondDate: string): number => {
                return moment
                    .utc(new Date(firstDate))
                    .isBefore(moment.utc(new Date(secondDate))) ? 1 : 0;
            };

            vm.awhItems.sort((first, second) => compare(first.startDate, second.startDate));
            vm.ajthItems.sort((first, second) => compare(first.startDate, second.startDate));
            
            // Remove start date outside display period
            const awhItems = _.filter(vm.awhItems, i => condition(i.startDate))
                .map(i => {
                    i.wkpHID = i.historyId;
                    i.historyId = '';
                    return i;
                });

            const ajthItems = _.filter(vm.ajthItems, i => condition(i.startDate))
                .map(i => {
                    i.jtHID = i.historyId;
                    i.historyId = '';
                    return i;
                });
            
            // summarize in 1 table
            const arr: HistoryItem[] = [...awhItems, ...ajthItems];

            // Sort by date
            arr.sort((first, second) => compare(first.startDate, second.startDate));

            // Summarize the same employees and the same start date
            const summarizeArr: HistoryItem[] = [];
            _.forEach(arr, i => {
                let item = i;

                const summarize =_.find(arr, j =>
                    i.employeeId === j.employeeId &&
                    moment.utc(new Date(i.startDate)).isSame(moment.utc(new Date(j.startDate)))
                );
            
                if (summarize) {
                    item = $.extend(item, summarize);
                }
                summarizeArr.push(item);
            });
            // Remove duplicate item
            summarizeArr.filter((c, index) => summarizeArr.indexOf(c) === index);

            const displayData: DataTableHistory[] = _.map(summarizeArr, i => {
                const post = i;
                const startDate = i.startDate;
                const theDayBefore = moment.utc(new Date(startDate)).subtract(1, 'd');
                const prev = _.find(summarizeArr, j => moment.utc(new Date(j.endDate)).isSame(theDayBefore));
                return new DataTableHistory(prev, post, vm.empInfors, vm.wkpListInfo, vm.jtInfor);
            });

            vm.tableDatasHistory(displayData);
        }


        setSelectedRowKcp() {
            const vm = this;

            const $selectedRight = $(`[data-id=${vm.selectedIdRight()}]`);
            let indexRight = $selectedRight.parent().children().index($selectedRight);

            const dataLength = $("#tree-grid-right [data-id]").length;
            if (indexRight >= dataLength) {
                indexRight = dataLength - 1;
            }

            if (indexRight >= 0) {
                const selectedId = $($("#tree-grid-right [data-id]")[indexRight]).attr('data-id');
                vm.selectedIdRight(selectedId);
            }
        }
    }

    enum Table { LEFT, RIGHT }

    interface EmployeesInWorkplace {
        employeeCD: string;
        employeeID: string;
        employeeName: string;
        jobtitle: string;
        jobtitleID: string;
        order: number;
    }

    interface TransferOfDay {
        wkpEmployees: string[];
        jtEmployees: string[];
    }

    interface TransferList {
        awhItems: any[];
        ajthItems: any[];
        empInfors: any[];
        wkpListInfo: any[];
        jtInfor: any[];
    }

    class DataTable {
        code: string;
        id: string;
        name: string;
        jt: string;
        jtID: string;
        od: number;
        jtc: string;
        cssWKP: string;
        cssJT: string;

        constructor(data: EmployeesInWorkplace, jtc: string) {
            const vm = this;
            vm.id = data.employeeID;
            vm.code = data.employeeCD;
            vm.name = data.employeeName;
            vm.jt = data.jobtitle;
            vm.jtID = data.jobtitleID;
            vm.od = data.order;
            vm.jtc = jtc;
            vm.cssWKP = '';
            vm.cssJT = '';
        }
    }

    class DataTableHistory {
        key: string; 
        dayBefore: string; //前日
        startDate: string; //開始日
        endDate: string; //終了日
        sID: string; //社員ID
        sCD: string; //社員CD
        sName: string; //社員名称
        prevWkpID: string; //前職場ID
        prevWkpName: string; //前職場名称
        prevWkpHID: string; //前職場履歴ID
        postWkpID: string; //後職場ID
        postWkpName: string; //後職場名称
        wkpHID: string; //後職場履歴ID
        prevPositionID: string; //前職位ID
        prevPositionName: string; //前職位名称
        prevPositionOrder: string; //前職位並び順
        prevPositionHID: string; //前職位履歴ID
        positionId: string; //後職位ID
        postPositionName: string; //後職位名称
        postPositionOrder: string; //後職位並び順
        postPositionHID: string; //後職位履歴ID

        constructor(prev: HistoryItem, post: HistoryItem, empInfors: any[], wkpListInfo: any[], jtInfor: any[]) {
            this.endDate = MAX_DATE;
            
            if (prev) {
                this.prevWkpID = prev.workplaceId;
                this.prevWkpName = _.isNil(prev.workplaceId)
                    ? getText('CMM015_52')
                    : _.find(wkpListInfo, i => i.workplaceId = prev.workplaceId).workplaceName;
                this.prevWkpHID = prev.wkpHID;

                this.prevPositionID = prev.jobTitleId;
                const prevPosition = _.find(jtInfor, i => i.jobTitleId = prev.jobTitleId);
                this.prevPositionName = prevPosition.workplaceName;
                this.prevPositionOrder = prevPosition.order;
                this.prevPositionHID = prev.jtHID;
            }

            if (post) {
                this.dayBefore = moment.utc(new Date(post.startDate)).subtract(1, 'd').toString();
                this.startDate = post.startDate;
                this.sID = post.employeeId;
                const { employeeCode, businessName } = _.find(empInfors, i => i.employeeId === post.employeeId);
                this.sCD = employeeCode;
                this.sName = businessName;
                this.postWkpID = post.workplaceId;
                this.postWkpName = _.isNil(post.workplaceId)
                    ? getText('CMM015_52')
                    : _.find(wkpListInfo, i => i.workplaceId = post.workplaceId).workplaceName;
                this.wkpHID = post.wkpHID;

                this.positionId = post.jobTitleId;
                const postPosition = _.find(jtInfor, i => i.jobTitleId = post.jobTitleId);
                if (postPosition) {
                    this.postPositionName = postPosition.workplaceName;
                    this.postPositionOrder = postPosition.order;
                }
                this.postPositionHID = post.jtHID;
            }

            this.key = `${this.sID} ${this.startDate}`;
        }
    }

    class HistoryItem {
        startDate: string;
        endDate: string;
        employeeId: string;
        workplaceId: string;
        normalWorkplaceId: string;
        jobTitleId: string;
        wkpHID: string;
        jtHID: string;

        constructor(init: HistoryItem) {
            $.extend(this, init);
        }
    }

    interface TransfereeOnApproved {
        approvalRoutes: ApprovalRouteSpStatus[];
        
        empInfors: EmployeeInformation[];
        
        wkpListInfo: WorkplaceInfor[];
    }

    interface ApprovalRouteSpStatus {
        sid: string;
        workplaceList: string[];
        approveEmpList: string[];
        approverList: string[];
        workplace: WorkplaceInfor;
    }

    interface EmployeeInformation {
        employeeId: string;
        employeeCode: string;
        businessName: string;
    }

    interface WorkplaceInfor {
        workplaceId: string;
        workplaceCode: string;
        workplaceName: string;
    }
}
