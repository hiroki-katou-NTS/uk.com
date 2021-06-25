/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module cmm015.a.viewmodel {

    const API = {
        getEmpInWkp: 'com/screen/cmm015/getEmployeesInWorkplace', //職場の所属社員一覧を取得する
        getTransferOfDay: 'com/screen/cmm015/getTransferOfDay', //当日の異動を取得する
        regisJTC: 'com/screen/cmm015/regisJTC', //職位異動の登録をする
        createTransferList: 'com/screen/cmm015/createTransferList',//異動一覧を作成する
    };

    const MAX_DATE: string = '9999/12/31';

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
        tableDatasSource: KnockoutObservableArray<any>;
        currentCodeListSource: KnockoutObservableArray<any> = ko.observableArray([]);

        columnsDest: KnockoutObservableArray<any>;
        tableDatasDest: KnockoutObservableArray<any>;
        currentCodeListDest: KnockoutObservableArray<any> = ko.observableArray([]);

        columns3: KnockoutObservableArray<any>;
        tableDatas3: KnockoutObservableArray<any>;
        currentCodeList3: KnockoutObservableArray<any> = ko.observableArray([]);

        dateValue: KnockoutObservable<any> = ko.observable({});
        startDateString: KnockoutObservable<string> = ko.observable("");
        endDateString: KnockoutObservable<string> = ko.observable("");

        isEnableA3: KnockoutObservable<boolean> = ko.observable(true);
        isEnableA6: KnockoutObservable<boolean> = ko.observable(true);
        isEnableA11: KnockoutObservable<boolean> = ko.observable(true);

        isNullTransferDate: KnockoutObservable<boolean> = ko.observable(true);

        baseDateLeftText: KnockoutComputed<string>;
        baseDateRightText: KnockoutComputed<string>;

        created() {
            const vm = this;
            vm.treeGridLeft = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: false,
                startMode: 0, // StartMode.WORKPLACE
                selectedId: vm.selectedIdLeft,
                baseDate: vm.baseDateLeft,
                selectType: 4, //SelectionType.NO_SELECT,
                isShowSelectButton: false,
                isDialog: false,
                maxRows: 11,
                tabindex: 3,
                systemType : 2, // SystemType.EMPLOYMENT
                listDataDisplay: vm.listDataLeft,
                hasPadding: false
            };

            vm.treeGridRight = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: false,
                startMode: 0, // StartMode.WORKPLACE
                selectedId: vm.selectedIdRight,
                baseDate: vm.baseDateRight,
                selectType: 4, //SelectionType.NO_SELECT,
                isShowSelectButton: false,
                isDialog: false,
                maxRows: 11,
                tabindex: 3,
                systemType : 2, // SystemType.EMPLOYMENT
                listDataDisplay: vm.listDataRight,
                hasPadding: false
            }

            vm.columnsSource = ko.observableArray([
                { headerText: '', key: 'id', hidden: true },
                { headerText: vm.$i18n('CMM015_21'), key: 'code', width: 80, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_22'), key: 'name', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_23'), key: 'jt', width: 120, headerCssClass: 'text-left' }
            ]);

            vm.tableDatasSource = ko.observableArray([]);

            vm.columnsDest = ko.observableArray([
                { headerText: '', key: 'id', hidden: true },
                { headerText: '', key: 'css', hidden: true },
                { headerText: '', key: 'jtID', hidden: true },
                { headerText: vm.$i18n('CMM015_21'), key: 'code', width: 80, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_22'), key: 'name', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_23'), key: 'jt', width: 140, headerCssClass: 'text-left' },
                {
                    headerText: vm.$i18n('CMM015_23'), key: 'jtc', width: 140, headerCssClass: 'text-left',
                    template: '<a class="custom-link ${css}" jtid="${jtID}" emp="${id}">${jtc}</a>'
                }
            ]);

            vm.tableDatasDest = ko.observableArray([]);

            vm.columns3 = ko.observableArray([
                { headerText: vm.$i18n('CMM015_45'), key: 'date', width: 120, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_46'), key: 'code', width: 80, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_47'), key: 'name', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_48'), key: 'source', width: 220, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_49'), key: 'currentJT', width: 140, headerCssClass: 'text-left' },
                { headerText: vm.$i18n('CMM015_50'), key: 'dest', width: 240, headerCssClass: 'text-left dest-wkp'},
                { headerText: vm.$i18n('CMM015_51'), key: 'newJT', width: 140, headerCssClass: 'text-left dest-wkp' }
            ]);

            vm.tableDatas3 = ko.observableArray([
                { date: '2021年10月3日', code: '00745', name: '１２３４５', source: '１２３４５６７８９', currentJT: '１２', dest: '１２３４５６７８９１', newJT: '１２' }
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
            $('#tree-grid-left').ntsTreeComponent(vm.treeGridLeft);
            $('#tree-grid-right').ntsTreeComponent(vm.treeGridRight);

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
                            if (vm.ui20(data.jtID, output)) return;

                            // 職位異動の登録をする
                            vm
                                .$ajax('com', API.regisJTC, {
                                    sid: data.id,
                                    jobTitleId: output,
                                    startDate: moment(vm.transferDate()).utc().toISOString(),
                                    endDate: moment(MAX_DATE).utc().toISOString()
                                })
                                .then(() => {
                                    vm.loadDataWkp(Table.RIGHT);
                                    vm.ui6();
                                    vm.$dialog.info({ messageId: 'Msg_15' });
                                })
                                .fail(({messageId}) => vm.$dialog.error({ messageId: messageId }));
                            
                        }
                    });
            });
            
        }

        clickMeans() {
            const vm = this;

            vm.$validate('#A1_3').then(valid => {
                if (!valid) {
                    vm.isNullTransferDate(true);
                    return;
                }
                const date = ko.unwrap<string>(vm.transferDate);
                vm.baseDateRight(new Date(vm.transferDate()));
                vm.baseDateLeft(new Date(moment.utc(date).subtract(1, 'd').toString()));
                vm.isNullTransferDate(false);
                vm.reloadKcp();

            })
        }

        reloadKcp() {
            const vm = this;
            $('#tree-grid-left').ntsTreeComponent(vm.treeGridLeft);
            $('#tree-grid-right').ntsTreeComponent(vm.treeGridRight);

            vm.loadDataWkp(Table.LEFT);
            vm.loadDataWkp(Table.RIGHT);
        }

        /**
         * 職場の所属社員一覧を取得する
         * @param table LEFT OR RIGHT
         */
        loadDataWkp(table: Table) {
            const vm = this;
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
                        vm.currentCodeListDest.removeAll();
                        vm.tableDatasDest(tableDatas);
                    }

                })
                .always(() => vm.$blockui('clear'));

        }

        ui6() {
            const vm = this;
            vm.currentCodeListDest([]);
        }

        ui20(jtID: string, newID: string): boolean {
            const vm = this;
            if(jtID === newID) {
                vm.$dialog.error({ messageId: 'Msg_2120' });
                return true;
            }
            return false;
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

    class DataTable {
        code: string;
        id: string;
        name: string;
        jt: string;
        jtID: string;
        od: number;
        jtc: string;
        css: string;

        constructor(data: EmployeesInWorkplace, jtc: string) {
            const vm = this;
            vm.id = data.employeeID;
            vm.code = data.employeeCD;
            vm.name = data.employeeName;
            vm.jt = data.jobtitle;
            vm.jtID = data.jobtitleID;
            vm.od = data.order;
            vm.jtc = jtc;
            vm.css = 'red';
        }
    }
}
