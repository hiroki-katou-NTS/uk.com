/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.j {

    const API = {
        ADD_OR_UPDATE: 'at/record/kdw006/view-j/register-or-upate',
        GET_ALL_DATA: 'at/record/kdw006/view-j/get-screen-usage-details'
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        public tabs: KnockoutObservableArray<any> = ko.observableArray([
            { id: 'tab-1', title: this.$i18n('KDW006_308'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-2', title: this.$i18n('KDW006_309'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-3', title: this.$i18n('KDW006_310'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
        ]);
        public selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

        public columns2: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: this.$i18n('KDW006_44'), key: 'id', width: 55 },
            { headerText: this.$i18n('KDW006_45'), key: 'name', width: 175, formatter: _.escape }
        ]);
        public data: IModel;

        // Value Tab1
        public textInput1: KnockoutObservable<string> = ko.observable('');
        public value1: KnockoutObservable<number> = ko.observable();
        public nameAttendent1: KnockoutObservable<string> = ko.observable('');
        public textInput2: KnockoutObservable<string> = ko.observable('');
        public value2: KnockoutObservable<number> = ko.observable();
        public nameAttendent2: KnockoutObservable<string> = ko.observable('');
        public textInput3: KnockoutObservable<string> = ko.observable('');
        public value3: KnockoutObservable<number> = ko.observable();
        public nameAttendent3: KnockoutObservable<string> = ko.observable('');

        // Value Tab2
        public currentCodeListLeft: KnockoutObservableArray<IItemsViewScreen> = ko.observableArray([]);
        public itemsLeft: KnockoutObservableArray<IItemsViewScreen> = ko.observableArray([]);

        // Value Tab3
        public currentCodeListRight: KnockoutObservableArray<IItemsViewScreen> = ko.observableArray([]);
        public itemsRight: KnockoutObservableArray<IItemsViewScreen> = ko.observableArray([]);

        created() {
            const vm = this;

            vm.reloadData();
        }

        mounted() {
            const vm = this;
            $('.ip-nameItem1').focus();
        }

        openDialogKDLDL1() {
            const vm = this;
            vm.openDialogKDL(1);
        }

        openDialogKDLDL2() {
            const vm = this;
            vm.openDialogKDL(2);
        }

        openDialogKDLDL3() {
            const vm = this;
            vm.openDialogKDL(3);
        }

        reloadData() {
            const vm = this;
            vm.$blockui('invisible')
                .then(() => {
                    vm.$ajax('at', API.GET_ALL_DATA)
                        .done((data: IModel) => {
                            vm.data = data;
                            vm.setColumnDisplayItems(data);
                            vm.setDailyAttendanceItem(data);
                            vm.setValueDisplayManHrRecordItem(data);
                        });
                })
                .always(() => vm.$blockui('clear'));
        }

        openDialogKDL(param: number) {
            const vm = this;
            nts.uk.ui.windows.setShared('Multiple', false);
            nts.uk.ui.windows.setShared('AllAttendanceObj', _.map(vm.data.dailyAttendanceItem, (item: IDailyAttendanceItem) => { return item.attendanceItemId }));

            switch (param) {
                case 1:
                    nts.uk.ui.windows.setShared('SelectedAttendanceId', [ko.unwrap(vm.value1)]);
                    break;
                case 2:
                    nts.uk.ui.windows.setShared('SelectedAttendanceId', [ko.unwrap(vm.value2)]);
                    break;
                case 3:
                    nts.uk.ui.windows.setShared('SelectedAttendanceId', [ko.unwrap(vm.value3)]);
                    break;
            }
            nts.uk.ui.windows.sub.modal("at", "/view/kdl/021/a/index.xhtml").onClosed(() => {
                let output = nts.uk.ui.windows.getShared("selectedChildAttendace");
                const exist = _.find(vm.data.dailyAttendanceItem, ((item: IDailyAttendanceItem) => { return item.attendanceItemId == output }));

                switch (param) {
                    case 1:
                        vm.value1(output)
                        if (exist) {
                            vm.nameAttendent1(exist.attendanceName);
                        }
                        break;
                    case 2:
                        vm.value2(output)
                        if (exist) {
                            vm.nameAttendent2(exist.attendanceName);
                        }
                        break;
                    case 3:
                        vm.value3(output)
                        if (exist) {
                            vm.nameAttendent3(exist.attendanceName);
                        }
                        break;
                }
            });
        }

        setColumnDisplayItems(param: IModel) {
            const vm = this;
            const data = _.orderBy(param.manHourInputDisplayFormat.recordColumnDisplayItems, ['order'], ['asc']);
            for (var i = 0; i < data.length; i++) {
                const exist = _.find(param.dailyAttendanceItem, ((item: IDailyAttendanceItem) => {
                    return item.attendanceItemId === data[i].attendanceItemId
                }));
                switch (i) {
                    case 0: {
                        vm.value1(data[i].attendanceItemId);
                        vm.textInput1(data[i].displayName);
                        if (exist) {
                            vm.nameAttendent1(exist.attendanceName);
                        }
                        break;
                    }
                    case 1: {
                        vm.value2(data[i].attendanceItemId);
                        vm.textInput2(data[i].displayName);
                        if (exist) {
                            vm.nameAttendent2(exist.attendanceName);
                        }
                        break;
                    }
                    case 2: {
                        vm.value3(data[i].attendanceItemId);
                        vm.textInput3(data[i].displayName);
                        if (exist) {
                            vm.nameAttendent3(exist.attendanceName);
                        }
                        break;
                    }
                }
            }
        }

        setValueDisplayManHrRecordItem(param: IModel) {
            const vm = this;
            var itemNotUse: IItemsViewScreen[] = [];
            var itemUse: IItemsViewScreen[] = [];
            const data = _.orderBy(param.manHourInputDisplayFormat.displayManHrRecordItems, ['order'], ['asc']);

            _.forEach(data, ((item: IDisplayManHrRecordItem) => {
                const exist: IAcquireManHourRecordItems = _.find(param.manHourRecordItem, ((value: IAcquireManHourRecordItems) => {
                    return value.itemId === item.attendanceItemId;
                }));
                if (exist) {
                    itemUse.push({ id: item.attendanceItemId, name: exist.name });
                }
            }));

            _.forEach(param.manHourRecordItem, ((item: IAcquireManHourRecordItems) => {
                itemNotUse.push({ id: item.itemId, name: item.name });
            }));

            vm.itemsRight(itemNotUse);
            vm.currentCodeListRight(itemUse);
        }

        setDailyAttendanceItem(param: IModel) {
            const vm = this;
            var itemNotUse: IItemsViewScreen[] = [];
            var itemUse: IItemsViewScreen[] = [];

            const data = _.orderBy(param.manHourInputDisplayFormat.displayAttItems, ['order'], ['asc']);

            _.forEach(data, ((item: IDisplayAttItem) => {
                const exist: IDailyAttendanceItem = _.find(param.dailyAttendanceItem, ((value: IDailyAttendanceItem) => {
                    return value.attendanceItemId === item.attendanceItemId;
                }))
                if (exist) {
                    itemUse.push({ id: exist.displayNumber, name: exist.attendanceName });
                }
            }))

            _.forEach(param.dailyAttendanceItem, ((item: IDailyAttendanceItem) => {
                itemNotUse.push({ id: item.displayNumber, name: item.attendanceName });
            }));

            vm.itemsLeft(itemNotUse);
            vm.currentCodeListLeft(itemUse);
        }

        addOrUpdate() {
            const vm = this;
            var order = 1;

            const recordColumnDisplayItems = [];

            if (ko.unwrap(vm.value1)) {
                recordColumnDisplayItems.push({ order: order, attendanceItemId: ko.unwrap(vm.value1), displayName: ko.unwrap(vm.textInput1) });
                order++;
            }
            if (ko.unwrap(vm.value2)) {
                recordColumnDisplayItems.push({ order: order, attendanceItemId: ko.unwrap(vm.value2), displayName: ko.unwrap(vm.textInput2) });
                order++;
            }
            if (ko.unwrap(vm.value3)) {
                recordColumnDisplayItems.push({ order: order, attendanceItemId: ko.unwrap(vm.value3), displayName: ko.unwrap(vm.textInput3) });
                order++;
            }

            order = 1;
            const displayAttItems: any[] = [];
            _.forEach(ko.unwrap(vm.currentCodeListLeft), ((item: IItemsViewScreen) => {
                const exist = _.find(vm.data.dailyAttendanceItem, ((value: IDailyAttendanceItem) => { return item.id === value.displayNumber }));
                if (exist) {
                    displayAttItems.push({ attendanceItemId: exist.attendanceItemId, order: order });
                    order++;
                }
            }));

            order = 1;

            const displayManHrRecordItems: any[] = [];

            _.forEach(ko.unwrap(vm.currentCodeListRight), ((item: IItemsViewScreen) => {
                displayManHrRecordItems.push({ attendanceItemId: item.id, order: order });
                order++;
            }))

            const param = {
                recordColumnDisplayItems: recordColumnDisplayItems,
                displayAttItems: displayAttItems,
                displayManHrRecordItems: displayManHrRecordItems
            }


            vm.validate()
                .then((valid: boolean) => {
                    if (valid) {
                        vm.$blockui('invisible')
                            .then(() => {
                                vm.$ajax('at', API.ADD_OR_UPDATE, param)
                                    .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
                                    .then(() => vm.reloadData())
                            })
                            .always(() => vm.$blockui('clear'));
                    }
                });
        }

        validate(action: 'clear' | undefined = undefined) {
            if (action === 'clear') {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').ntsError('clear'));
            } else {
                return $.Deferred().resolve()
                    .then(() => $('.nts-input').trigger("validate"))
                    .then(() => !$('.nts-input').ntsError('hasError'));
            }
        }
    }

    interface IItemsViewScreen {
        id: number;
        name: string;
    }

    interface IModel {
        /** 工数入力表示フォーマット */
        manHourInputDisplayFormat: IGetDisplayFormat;

        /** 利用する勤怠項目 */
        dailyAttendanceItem: IDailyAttendanceItem[];

        /** 工数実績項目 */
        manHourRecordItem: IAcquireManHourRecordItems[];
    }

    interface IGetDisplayFormat {
        /** 実績欄表示項目一覧 */
        recordColumnDisplayItems: IRecordColumnDisplayItem[];

        /** 実績入力ダイアログ表示項目一覧 */
        displayAttItems: IDisplayAttItem[];

        /** 作業内容入力ダイアログ表示項目一覧 */
        displayManHrRecordItems: IDisplayManHrRecordItem[];
    }

    // 実績欄表示項目一覧
    interface IRecordColumnDisplayItem {
        // 表示順
        order: number;

        // 対象項目: 勤怠項目ID
        attendanceItemId: number;

        // 名称: 実績欄表示名称
        displayName: string
    }

    // 実績入力ダイアログ表示項目一覧
    interface IDisplayAttItem {
        // 項目ID: 勤怠項目ID
        attendanceItemId: number;

        // 表示順
        order: number;
    }

    // 作業内容入力ダイアログ表示項目一覧
    interface IDisplayManHrRecordItem {
        // 項目ID: 勤怠項目ID
        attendanceItemId: number;

        // 表示順
        order: number;
    }

    interface IDailyAttendanceItem {
        /* 会社ID */
        companyId: string;

        /* 勤怠項目ID */
        attendanceItemId: number;

        /* 勤怠項目名称 */
        attendanceName: string;

        /* 表示番号 */
        displayNumber: number;

        /* ユーザーが値を変更できる */
        userCanUpdateAtr: number;

        /* 勤怠項目属性 */
        dailyAttendanceAtr: number;

        /* 名称の改行位置 */
        nameLineFeedPosition: number;

        /* マスタの種類 */
        masterType: number;

        /* 怠項目のPrimitiveValue */
        primitiveValue: number;

        /* 表示名称 */
        displayName: string;
    }

    interface IAcquireManHourRecordItems {
        /** 項目ID*/
        itemId: number;

        /** 名称*/
        name: string;

        /** フォーマット設定に表示する*/
        useAtr: number;
    }
}
