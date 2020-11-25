module nts.uk.com.view.kwr002.c.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        attendanceCode: KnockoutObservable<String>;
        attendanceName: KnockoutObservable<String>;
        useSeal: KnockoutObservableArray<any>;
        sealStamp: KnockoutObservableArray<String>;
        useSealValue: KnockoutObservable<any>;
        attendanceItemListDaily: KnockoutObservableArray<viewmodel.model.AttendanceItem>;
        attendanceItemListMonthly: KnockoutObservableArray<viewmodel.model.AttendanceItem>;
        attendanceItemList: KnockoutObservableArray<viewmodel.model.AttendanceItem>;
        attendanceRecExpDaily: KnockoutObservableArray<viewmodel.model.AttendanceRecExp>;
        attendanceRecExpMonthly: KnockoutObservableArray<viewmodel.model.AttendanceRecExp>;
        attendanceRecItemList: KnockoutObservableArray<viewmodel.model.AttendanceRecItem>;

        confirmedDisplay: KnockoutObservableArray<any>;
        monthlyConfirmedDisplay: KnockoutObservable<any>;
        columns: KnockoutObservableArray<any>;

        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;

        sealName1: KnockoutObservable<String>;
        sealName2: KnockoutObservable<String>;
        sealName3: KnockoutObservable<String>;
        sealName4: KnockoutObservable<String>;
        sealName5: KnockoutObservable<String>;
        sealName6: KnockoutObservable<String>;
        currentCode: KnockoutObservable<number>;

        // Update Ver25
        isSmallOrMedium: KnockoutObservable<boolean>;
        isSmall: KnockoutObservable<boolean>;
        useMonthApproverConfirm: KnockoutObservable<boolean>;
        layoutId: KnockoutObservable<string> = ko.observable('');

        action = {
            ADDITION: getText('KWR002_178'),
            SUBTRACTION: getText('KWR002_179')
        }

        // End update v25

        constructor() {
            var self = this;

            self.attendanceCode = ko.observable('');
            self.attendanceName = ko.observable('');
            self.sealStamp = ko.observableArray([]);
            self.attendanceItemListDaily = ko.observableArray([]);
            self.attendanceItemListMonthly = ko.observableArray([]);
            self.attendanceItemList = ko.observableArray([]);
            self.attendanceRecExpDaily = ko.observableArray([]);
            self.attendanceRecExpMonthly = ko.observableArray([]);
            self.attendanceRecItemList = ko.observableArray([]);
            self.sealName1 = ko.observable('');
            self.sealName2 = ko.observable('');
            self.sealName3 = ko.observable('');
            self.sealName4 = ko.observable('');
            self.sealName5 = ko.observable('');
            self.sealName6 = ko.observable('');
            self.currentCode = ko.observable(0);
            self.isSmallOrMedium = ko.observable(false);
            self.isSmall = ko.observable(false);
            self.useMonthApproverConfirm = ko.observable(false);


            //            console.log(self.attendanceCode());

            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KWR002_86'), key: 'attendanceItemId', width: 100 },
                { headerText: nts.uk.resource.getText('KWR002_87'), key: 'attendanceItemName', width: 200 }
            ]);
            self.useSealValue = ko.observable(true);
            self.useSeal = ko.observableArray([
                { code: true, name: nts.uk.resource.getText("KWR002_63") },
                { code: false, name: nts.uk.resource.getText("KWR002_64") }
            ]);

            self.confirmedDisplay = ko.observableArray([
                { value: 1, name: nts.uk.resource.getText("KWR002_195") },
                { value: 0, name: nts.uk.resource.getText("KWR002_196") }
            ]);
            self.monthlyConfirmedDisplay = ko.observable(0);

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("KWR002_88"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("KWR002_89"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.selectedTab.subscribe((codeChange) => {
                if (codeChange === 'tab-1') {
                    //                    self.attendanceItemList([]);
                    self.attendanceItemList(self.attendanceItemListDaily());
                }
                else {
                    //                    self.attendanceItemList([]);
                    self.attendanceItemList(self.attendanceItemListMonthly());
                }
            })

            $('.sub').click((element) => {

                //                console.log($(element).attr('id'));

                var bind = (element.currentTarget as any).dataset.bind;

                var openSign: number = bind.indexOf('[');
                var closeSign: number = bind.indexOf(']');
                var columnIndex: number;
                if (closeSign - openSign > 2)
                    columnIndex = bind.substr(bind.indexOf('.') - 3, 2);
                else
                    columnIndex = bind.substr(bind.indexOf('.') - 2, 1);

                var position = bind.substr(bind.indexOf('.') + 1);

                var attendanceItemName: String;

                var positionTop: any = $(element).hasClass('top');
                var positionBot: any = $(element).hasClass('bot');

                var position: number;

                var exportAtr: number;

                var attItem: model.AttendanceRecExp;

                if (position.search("upper") != -1) {
                    position = 1;
                }
                else position = 2;

                const positionText = position == 1 ? "上" : "下";
                if (self.selectedTab() == "tab-1") {
                    exportAtr = 1;
                    if (position == 1)
                        if (!self.attendanceRecExpDaily()[columnIndex].upperPosition()) attendanceItemName = "";
                        else attendanceItemName = self.attendanceRecExpDaily()[columnIndex].upperPosition();
                    else
                        if (!self.attendanceRecExpDaily()[columnIndex].lowwerPosition()) attendanceItemName = "";
                        else attendanceItemName = self.attendanceRecExpDaily()[columnIndex].lowwerPosition();

                    attItem = self.attendanceRecExpDaily()[columnIndex];

                } else {
                    exportAtr = 2;
                    if (position == 1)
                        if (!self.attendanceRecExpMonthly()[columnIndex].upperPosition()) attendanceItemName = "";
                        else attendanceItemName = self.attendanceRecExpMonthly()[columnIndex].upperPosition();
                    else
                        if (!self.attendanceRecExpMonthly()[columnIndex].lowwerPosition()) attendanceItemName = "";
                        else attendanceItemName = self.attendanceRecExpMonthly()[columnIndex].lowwerPosition();
                    attItem = self.attendanceRecExpMonthly()[columnIndex];
                }

                // アルゴリズム「出力項目画面設定」を実行する (Thực thi thuật toán Setting màn hình hạng mục output)
                self.settingOutputScreen(attendanceItemName, columnIndex, position, exportAtr, positionText);

                var link: string;
                if (exportAtr == 1 && columnIndex <= 6) link = '/view/kdl/047/a/index.xhtml';
                else link = '/view/kdl/048/index.xhtml';
                blockUI.grayout();

                nts.uk.ui.windows.sub.modal(link).onClosed(() => {
                    if (attendanceItemName == '') {

                        if (exportAtr == 1) {

                            self.attendanceRecExpDaily()[columnIndex].exportAtr = exportAtr;
                            self.attendanceRecExpDaily()[columnIndex].columnIndex = columnIndex;
                            self.attendanceRecExpDaily()[columnIndex].userAtr = false;

                        } else {

                            self.attendanceRecExpMonthly()[columnIndex].exportAtr = exportAtr;
                            self.attendanceRecExpMonthly()[columnIndex].columnIndex = columnIndex;
                            self.attendanceRecExpMonthly()[columnIndex].userAtr = false;

                        }

                    }
                    var attendanceItem = getShared('attendanceRecordExport');
                    if (attendanceItem) {

                        if (exportAtr == 1) {
                            if (position == 1) {
                                self.attendanceRecExpDaily()[columnIndex].upperPosition(attendanceItem.attendanceItemName);
                                self.attendanceRecExpDaily()[columnIndex].upperShow(true);
                            }
                            else {
                                self.attendanceRecExpDaily()[columnIndex].lowwerPosition(attendanceItem.attendanceItemName);
                                self.attendanceRecExpDaily()[columnIndex].lowerShow(true);
                            }
                        } else {
                            if (position == 1) {
                                self.attendanceRecExpMonthly()[columnIndex].upperPosition(attendanceItem.attendanceItemName);
                                self.attendanceRecExpMonthly()[columnIndex].upperShow(true);
                            }
                            else {
                                self.attendanceRecExpMonthly()[columnIndex].lowwerPosition(attendanceItem.attendanceItemName);
                                self.attendanceRecExpMonthly()[columnIndex].lowerShow(true);
                            }
                        }

                        var item: viewmodel.model.AttendanceRecItem;

                        if (exportAtr == 1 && columnIndex <= 6) {

                            item = new model.AttendanceRecItem(attendanceItem.attendanceItemName, attendanceItem.layoutCode, attendanceItem.layoutName,
                                attendanceItem.columnIndex, attendanceItem.position, attendanceItem.exportAtr,
                                attendanceItem.attendanceId, attendanceItem.attribute);

                        } else {
                            item = new model.AttendanceRecItem(attendanceItem.attendanceItemName,
                                attendanceItem.layoutCode,
                                attendanceItem.layoutName,
                                attendanceItem.columnIndex,
                                attendanceItem.position,
                                attendanceItem.exportAtr,
                                _.map(attendanceItem.attendanceId, (item: any) => new model.SelectedItem({action: item.operator, code: item.itemId})),
                                attendanceItem.attribute);
                        }

                        self.updateAttendanceRecItemList(item);

                    }


                })

            });
        }

        /**
         * 出力項目画面設定
         * @param attendanceCode the attendance code
         * @param columnIndex 列目
         * @param position 上段/下段
         * @param exportAtr 日次/月次
         */
        settingOutputScreen(attendanceItemName: String, columnIndex: number, position: any, exportAtr: number, positionText: string) {
            const vm = this;
            let attendanceItem: model.AttendanceItemShare = new model.AttendanceItemShare();
            attendanceItem.titleLine.displayFlag = true;
            attendanceItem.titleLine.layoutCode = vm.attendanceCode();
            attendanceItem.titleLine.layoutName = vm.attendanceName();
            attendanceItem.itemNameLine.displayFlag = true;
            attendanceItem.itemNameLine.displayInputCategory = 2;
            attendanceItem.itemNameLine.name = attendanceItemName;
            attendanceItem.attribute.selectionCategory = 2;
            attendanceItem.columnIndex = columnIndex;
            attendanceItem.position = position;
            attendanceItem.exportAtr = exportAtr;

            const attendanceRecordKey: model.AttendanceRecordKey = new model.AttendanceRecordKey({
                layoutId: vm.layoutId(),
                code: Number(vm.attendanceCode()),
                columnIndex: columnIndex,
                position: position,
                exportAtr: exportAtr
            });

            if (exportAtr === 1 && columnIndex <= 6) {
                attendanceItem.titleLine.directText = getText('KWR002_131') + columnIndex + getText('KWR002_132') + positionText + getText('KWR002_133');

                attendanceItem.attribute.attributeList = [
                    new model.AttendaceType(1, getText('KWR002_141')),
                    new model.AttendaceType(2, getText('KWR002_142')),
                    new model.AttendaceType(3, getText('KWR002_143'))
                ];

                $.when(service.getDailyAttendanceItems(), service.getSingleAttendanceRecord(attendanceRecordKey))
                    .done((dailyAttendanceItems: Array<model.AttributeOfAttendanceItem>, singleAttendanceRecord: any) => {

                        if (!!dailyAttendanceItems) {
                            attendanceItem.attendanceItems = dailyAttendanceItems;
                        }

                        if (!!singleAttendanceRecord) {
                            attendanceItem.selectedTime = singleAttendanceRecord.itemId;
                            attendanceItem.attribute.selected = singleAttendanceRecord.attribute;
                        }

                        const index = vm.findAttendanceRecItem(new model.AttendanceRecItem('', 0, '', columnIndex, position, exportAtr, '', 0));
                        if (index >= 0) {
                            attendanceItem.selectedTime = vm.attendanceRecItemList()[index].attendanceId;
                            attendanceItem.attribute.selected = vm.attendanceRecItemList()[index].attribute;
                        }

                        setShared('attendanceItem', attendanceItem, true);

                    })
            }

            if (exportAtr === 1 && columnIndex > 6) {
                attendanceItem.titleLine.directText = getText('KWR002_131') + columnIndex + getText('KWR002_132') + positionText + getText('KWR002_133');

                attendanceItem.attribute.attributeList = [
                    new model.AttendaceType(4, getText('KWR002_171')),
                    new model.AttendaceType(5, getText('KWR002_172')),
                    new model.AttendaceType(7, getText('KWR002_173'))
                ];

                $.when(service.getDailyAttendanceItems(), service.getCalculateAttendanceRecordDto(attendanceRecordKey))
                    .then((dailyAttendanceItems: Array<model.AttributeOfAttendanceItem>, calculateAttendanceRecord: any) => {

                        if (!!dailyAttendanceItems) {
                            attendanceItem.diligenceProjectList = _.map(dailyAttendanceItems,
                                item => new model.DiligenceProject({
                                    id: item.attendanceItemId,
                                    name: item.attendanceItemName,
                                    attributes: item.attributes,
                                    indicatesNumber: item.displayNumbers
                                })
                            );
                        }

                        if (!!calculateAttendanceRecord) {
                            let calculateAttendanceRecordList: Array<model.SelectedTimeItem> = [];
                            calculateAttendanceRecord.addedItem.forEach(function(item) {
                                calculateAttendanceRecordList.push(new model.SelectedTimeItem({itemId: item.attendanceItemId, operator: vm.action.ADDITION}));
                            });

                            calculateAttendanceRecord.subtractedItem.forEach(function(item) {
                                calculateAttendanceRecordList.push(new model.SelectedTimeItem({itemId: item.attendanceItemId, operator: vm.action.SUBTRACTION}));
                            });

                            attendanceItem.selectedTimeList = calculateAttendanceRecordList;
                        }

                        const index = vm.findAttendanceRecItem(new model.AttendanceRecItem('', 0, '', columnIndex, position, exportAtr, '', 0));
                        if (index >= 0) {
                            attendanceItem.selectedTimeList = _.map(vm.attendanceRecItemList()[index].attendanceId,
                                (item: any) => new model.SelectedTimeItem({itemId: item.code, operator: item.action}));
                            attendanceItem.attribute.selected = vm.attendanceRecItemList()[index].attribute;
                        }

                        setShared('attendanceItem', attendanceItem, true);

                    })
            }

            if (exportAtr === 2) {
                attendanceItem.titleLine.directText = getText('KWR002_161') + columnIndex + getText('KWR002_162') + positionText + getText('KWR002_163');

                attendanceItem.attribute.attributeList = [
                    new model.AttendaceType(4, getText('KWR002_180')),
                    new model.AttendaceType(5, getText('KWR002_181')),
                    new model.AttendaceType(6, getText('KWR002_182')),
                    new model.AttendaceType(7, getText('KWR002_183'))
                ];

                $.when(service.getMonthlyAttendanceItems(), service.getCalculateAttendanceRecordDto(attendanceRecordKey))
                    .then((monthlyAttendanceItems: Array<model.AttributeOfAttendanceItem>, calculateAttendanceRecord: any) => {

                        if (!!monthlyAttendanceItems) {

                            attendanceItem.diligenceProjectList = _.map(monthlyAttendanceItems,
                                item => new model.DiligenceProject({
                                    id: item.attendanceItemId,
                                    name: item.attendanceItemName,
                                    attributes: item.attributes,
                                    indicatesNumber: item.displayNumbers
                                })
                            );
                        }

                        if (!!calculateAttendanceRecord) {
                            let calculateAttendanceRecordList: Array<model.SelectedTimeItem> = [];
                            calculateAttendanceRecord.addedItem.forEach(function(item) {
                                calculateAttendanceRecordList.push(new model.SelectedTimeItem({itemId: item.attendanceItemId, operator: vm.action.ADDITION}));
                            });
                            calculateAttendanceRecord.subtractedItem.forEach(function(item) {
                                calculateAttendanceRecordList.push(new model.SelectedTimeItem({itemId: item.attendanceItemId, operator: vm.action.SUBTRACTION}));
                            });
                            calculateAttendanceRecordList.sort((a, b) => { return Number(a.itemId) - Number(b.itemId); });
                            attendanceItem.selectedTimeList = calculateAttendanceRecordList;
                        }

                        const index = vm.findAttendanceRecItem(new model.AttendanceRecItem('', 0, '', columnIndex, position, exportAtr, '', 0));
                        if (index >= 0) {
                            attendanceItem.selectedTimeList = _.map(vm.attendanceRecItemList()[index].attendanceId,
                                (item: any) => new model.SelectedTimeItem({itemId: item.attendanceItemId, operator: item.action}));
                            attendanceItem.attribute.selected = vm.attendanceRecItemList()[index].attribute;
                        }

                        setShared('attendanceItem', attendanceItem, true);

                    })
            }
        }

        decision(): void {
            var self = this;
            var attendanceRecExpDailyRes: viewmodel.model.AttendanceRecExpRespond[] = [];
            if (!self.attendanceRecExpDaily()[0]) {
                attendanceRecExpDailyRes.push(new viewmodel.model.AttendanceRecExpRespond(0, 0, false, null, null));
            }
            var attendanceRecExpMonthlyRes: viewmodel.model.AttendanceRecExpRespond[] = [];
            if (!self.attendanceRecExpMonthly()[0]) {
                attendanceRecExpMonthlyRes.push(new viewmodel.model.AttendanceRecExpRespond(0, 0, false, null, null));
            }

            self.attendanceRecExpDaily().forEach((item) => {
                attendanceRecExpDailyRes.push(new viewmodel.model.AttendanceRecExpRespond(item.exportAtr, item.columnIndex, item.userAtr, item.upperPosition(), item.lowwerPosition()));
            });
            self.attendanceRecExpMonthly().forEach((item) => {
                attendanceRecExpMonthlyRes.push(new viewmodel.model.AttendanceRecExpRespond(item.exportAtr, item.columnIndex, item.userAtr, item.upperPosition(), item.lowwerPosition()));
            });
            setShared('attendanceRecExpDaily', attendanceRecExpDailyRes, true);
            setShared('attendanceRecExpMonthly', attendanceRecExpMonthlyRes, true);
            setShared('attendanceRecItemList', self.attendanceRecItemList(), true);

            self.sealStamp().push(self.sealName1());
            self.sealStamp().push(self.sealName2());
            self.sealStamp().push(self.sealName3());
            self.sealStamp().push(self.sealName4());
            self.sealStamp().push(self.sealName5());
            self.sealStamp().push(self.sealName6());
            setShared('sealStamp', _.reject(self.sealStamp(), (it) => _.isEmpty(it)), true);
            setShared('useSeal', self.useSealValue());
            setShared('monthlyConfirmedDisplay', self.monthlyConfirmedDisplay());
            nts.uk.ui.windows.close();
        }

        cancel(): void {
            nts.uk.ui.windows.close();
        }

        findAttendanceRecItem(attendanceRecItem: viewmodel.model.AttendanceRecItem): number {
            var self = this;
            if (self.attendanceRecItemList().length == 0) return -1;
            var i: any;
            for (i = 0; i < self.attendanceRecItemList().length; i++) {
                var item = self.attendanceRecItemList()[i];
                if (item.exportAtr == attendanceRecItem.exportAtr && item.columnIndex == attendanceRecItem.columnIndex && item.position == attendanceRecItem.position)
                    return i;
            }
            return -1;
        }

        updateAttendanceRecItemList(attendanceRecItem: viewmodel.model.AttendanceRecItem): void {
            var self = this;
            var index = self.findAttendanceRecItem(attendanceRecItem);
            if (index == -1) {
                self.attendanceRecItemList().push(attendanceRecItem);
            }
            else self.attendanceRecItemList()[index] = attendanceRecItem;

        }

        start_page(): JQueryPromise<any> {

            blockUI.invisible();
            var self = this;
            var dfd = $.Deferred();

            let attendanceRecExpDaily = getShared('attendanceRecExpDaily');
            let attendanceRecExpMonthly = getShared('attendanceRecExpMonthly');
            let attendanceRecItemList = getShared('attendanceRecItemList');
            let attendanceRecExpSetCode: any = getShared('attendanceRecExpSetCode');
            let attendanceRecExpSetName: any = getShared('attendanceRecExpSetName');
            let sealStamp: any = getShared('sealStamp');
            let useSeal: any = getShared('useSeal');
            const fontSzie = getShared('exportFontSize');
            const selectionType = getShared('selectionType');
            const layoutId = getShared('layoutId');
            self.layoutId(layoutId);

            // Update Ver25
            $.when(service.getApprovalProcessingUseSetting(), service.getAttendanceRecordExportSetting(attendanceRecExpSetCode, selectionType))
                .then((aPUS: any, aRES: any) => {
                    if (aPUS !== null) {
                        self.useMonthApproverConfirm(aPUS.useMonthApproverConfirm);
                    }
                    if (aRES !== null) {
                        self.monthlyConfirmedDisplay(aRES.monthlyConfirmedDisplay);
                    } else {
                        self.monthlyConfirmedDisplay(0);
                    }
                })
                .fail(() => {
                    blockUI.clear();
                    dfd.reject();
                })

            self.isSmallOrMedium(fontSzie === 0 || fontSzie === 1);
            self.isSmall(fontSzie === 0);
            // End Update Ver25

            if (!attendanceRecExpSetCode) {
                self.attendanceCode('1');
                self.attendanceName('デフォルト名');
            } else {
                self.attendanceCode(attendanceRecExpSetCode);
                self.attendanceName(attendanceRecExpSetName);
            }

            if (attendanceRecExpDaily != null || attendanceRecExpMonthly != null || attendanceRecItemList != null) {
                self.useSealValue(useSeal);
                attendanceRecExpDaily.forEach((item: any) => {
                    var columnIndex: number = item.columnIndex;
                    self.attendanceRecExpDaily()[columnIndex] = new viewmodel.model.AttendanceRecExp(item.exportAtr
                        , item.columnIndex
                        , item.userAtr
                        , item.upperPosition + ""
                        , item.lowwerPosition + "");
                });

                for (var i: number = 1; i <= 13; i++) {
                    if (!self.attendanceRecExpDaily()[i]) {
                        self.attendanceRecExpDaily()[i] = new viewmodel.model.AttendanceRecExp(1, i, false, "", "");
                    }
                }

                attendanceRecExpMonthly.forEach((item: any) => {
                    var columnIndex: number = item.columnIndex;
                    self.attendanceRecExpMonthly()[columnIndex] = new viewmodel.model.AttendanceRecExp(item.exportAtr
                        , item.columnIndex
                        , item.userAtr
                        , item.upperPosition + ""
                        , item.lowwerPosition + "");

                });

                 for (var i: number = 1; i <= 16; i++) {
                    if (!self.attendanceRecExpMonthly()[i]) {
                        self.attendanceRecExpMonthly()[i] = new viewmodel.model.AttendanceRecExp(2, i, false, "", "");
                    }
                }

                self.attendanceRecItemList(attendanceRecItemList);
                self.sealName1(sealStamp[0]);
                self.sealName2(sealStamp[1]);
                self.sealName3(sealStamp[2]);
                self.sealName4(sealStamp[3]);
                self.sealName5(sealStamp[4]);
                self.sealName6(sealStamp[5]);

                dfd.resolve();
            } else {
                self.useSealValue(useSeal)
                $.when(service.findAllAttendanceRecExportDaily(layoutId)
                     , service.findAllAttendanceRecExportMonthly(layoutId)
                     , service.getSealStamp(layoutId))
                    .done((listattendanceRecExpDailyList: Array<model.AttendanceRecExp>
                        , listattendanceRecExpMonthlyList: Array<model.AttendanceRecExp>
                        ,sealStampList: Array<string>) => {
                    if (listattendanceRecExpDailyList.length > 0) {
                        listattendanceRecExpDailyList.forEach(item => {
                            var columnIndex: number = item.columnIndex;
                            self.attendanceRecExpDaily()[columnIndex] = new viewmodel.model.AttendanceRecExp(item.exportAtr
                                , item.columnIndex
                                , item.userAtr
                                , item.upperPosition + ""
                                , item.lowwerPosition + "");
                        })
                    }
                    for (var i: number = 1; i <= 13; i++) {
                        if (!self.attendanceRecExpDaily()[i]) {
                            self.attendanceRecExpDaily()[i] = new viewmodel.model.AttendanceRecExp(1, i, false, "", "");
                        }
                    }

                    if (listattendanceRecExpMonthlyList.length > 0) {
                        listattendanceRecExpMonthlyList.forEach(item => {
                            var columnIndex: number = item.columnIndex;
                            self.attendanceRecExpMonthly()[columnIndex] = new viewmodel.model.AttendanceRecExp(item.exportAtr
                                , item.columnIndex
                                , item.userAtr
                                , item.upperPosition + ""
                                , item.lowwerPosition + "");
                        })
                    }
                    for (var i: number = 1; i <= 16; i++) {
                        if (!self.attendanceRecExpMonthly()[i]) {
                            self.attendanceRecExpMonthly()[i] = new viewmodel.model.AttendanceRecExp(2, i, false, "", "");
                        }
                    }

                    if (sealStampList.length > 0) {
                        self.sealName1(sealStampList[0]);
                        self.sealName2(sealStampList[1]);
                        self.sealName3(sealStampList[2]);
                        self.sealName4(sealStampList[3]);
                        self.sealName5(sealStampList[4]);
                        self.sealName6(sealStampList[5]);

                    }
                    dfd.resolve();
                })
            }
            blockUI.clear();
            return dfd.promise();
        }
    }

    export module model {

        export class SelectedItem {
            code: string;
            action: string;

            constructor(init?: Partial<SelectedItem>) {
                $.extend(this, init);
            }
        }

        export class SelectedTimeItem {
            itemId: string;
            operator: string;

            constructor(init?: Partial<SelectedTimeItem>) {
                $.extend(this, init);
            }
        }

        export class AttendanceRecordKey {
            layoutId: string;
            code: number;
            columnIndex: number;
            position: number;
            exportAtr: number;
            constructor(init?: Partial<AttendanceRecordKey>) {
                $.extend(this, init);
            }
        }

        export class AttendanceRecExp {

            exportAtr: number;
            columnIndex: number;
            userAtr: Boolean;
            upperPosition: KnockoutObservable<string>;
            lowwerPosition: KnockoutObservable<string>;
            upperShow: KnockoutObservable<boolean>;
            lowerShow: KnockoutObservable<boolean>;

            constructor(exportAtr: number, columnIndex: number, userAtr: Boolean, upperPosition: string, lowwerPosition: string) {
                var self = this;
                self.exportAtr = exportAtr;
                self.columnIndex = columnIndex;
                self.userAtr = userAtr;
                self.upperPosition = ko.observable(upperPosition);
                self.lowwerPosition = ko.observable(lowwerPosition);

                self.upperShow = self.upperPosition() == "" ? ko.observable(false) : ko.observable(true);
                self.lowerShow = self.lowwerPosition() == "" ? ko.observable(false) : ko.observable(true);
            }
        }

        export class AttendanceRecExpRespond {

            exportAtr: number;
            columnIndex: number;
            userAtr: Boolean;
            upperPosition: string;
            lowwerPosition: string;

            constructor(exportAtr: number, columnIndex: number, userAtr: Boolean, upperPosition: string, lowwerPosition: string) {

                this.exportAtr = exportAtr;
                this.columnIndex = columnIndex;
                this.userAtr = userAtr;
                this.upperPosition = upperPosition;
                this.lowwerPosition = lowwerPosition;
            }
        }

        export class AttendanceRecItem {

            attendanceItemName: string;
            layoutCode: number;
            layoutName: string;
            columnIndex: number;
            position: number;
            exportAtr: number;
            attendanceId: any;
            attribute: number;
            constructor(attendanceItemName: string, layoutCode: number, layoutName: string, indexColumn: number, position: number, exportAtr: number, attendanceId: any, attribute: number) {
                this.attendanceItemName = attendanceItemName;
                this.layoutCode = layoutCode;
                this.layoutName = layoutName;
                this.columnIndex = indexColumn;
                this.position = position;
                this.exportAtr = exportAtr;
                this.attendanceId = attendanceId;
                this.attribute = attribute;
            }

        }

        export class AttendanceItem {
            attendanceItemId: number;
            attendanceItemName: String;
            screenUseItem: number;

            constructor(code: number, name: String, screenType: number) {
                this.attendanceItemId = code;
                this.attendanceItemName = name;
                this.screenUseItem = screenType;
            }
        }

        export class ApprovalProcessingUseSetting {
            companyId: string;
            useDayApproverConfirm: boolean;
            useMonthApproverConfirm: boolean;
            lstJobTitleNotUse: Array<string>;
            supervisorConfirmErrorAtr: number;
        }

        export class AttendaceType {
            attendanceTypeCode: number;
            attendanceTypeName: string;
            constructor(attendanceTypeCode: number, attendanceTypeName: string) {
                this.attendanceTypeCode = attendanceTypeCode;
                this.attendanceTypeName = attendanceTypeName;
            }
        }

        // 出勤簿の出力項目設定 Ver25
        export class AttendanceRecordExportSetting {
            // コード
            code: any;
            // 名称
            name: any;
            // 印鑑欄
            sealStamp: Array<String>;
            // 日次の出力項目
            dailyExportItem: Array<any>;
            // 月次の出力項目
            monthlyExportItem: Array<any>;
            // 名称使用区分
            nameUseAtr: any;
            // 印鑑欄使用区分
            sealUseAtr: boolean;
            // 文字の大きさ
            exportFontSize: any;
            // 月次確認済表示区分
            monthlyConfirmedDisplay: any;

            constructor(init?: Partial<AttendanceRecordExportSetting>) {
                $.extend(this, init);
            }
        }

        export class AttendanceItemShare {
            // タイトル行
            titleLine: TitleLine = new TitleLine();
            // 項目名行
            itemNameLine: ItemNameLine = new ItemNameLine();
            // 属性
            attribute: Attribute = new Attribute();
            // List<勤怠項目>
            attendanceItems: Array<AttributeOfAttendanceItem> = [];
            // 選択済み勤怠項目ID
            selectedTime: number;
            // 加減算する項目
            selectedTimeList: Array<SelectedTimeItem>;
            // columnIndex
            columnIndex: number;
            // position
            position: number;
            // exportAtr
            exportAtr: number;
            //List<勤怠項目> for KDL048
            diligenceProjectList: any;

            constructor(init?: Partial<AttendanceItemShare>) {
                $.extend(this, init);
            }
        }

        export class TitleLine {
            // 表示フラグ
            displayFlag: boolean;
            // 出力項目コード
            layoutCode: String;
            // 出力項目名
            layoutName: String;
            // コメント
            directText: String;
        }

        export class ItemNameLine {
            // 表示フラグ
            displayFlag: boolean;
            // 表示入力区分
            displayInputCategory: number;
            // 名称
            name: String;
        }

        export class Attribute {
            // 選択区分
            selectionCategory: number;
            // List<属性>
            attributeList: Array<AttendaceType>;
            // 選択済み
            selected: number;
        }

        export class AttributeOfAttendanceItem {
            /** 勤怠項目ID */
            attendanceItemId: number;
            /** 勤怠項目名称 */
            attendanceItemName: string;
            /** 勤怠項目の属性 */
            attributes: number;
            /** マスタの種類 */
            masterTypes: number | null;
            /** 表示番号 */
            displayNumbers: number;
        }

        export class DiligenceProject {
            id: any;
            name: any;
            attributes: any;
            indicatesNumber: any;

            constructor(init?: Partial<DiligenceProject>) {
              $.extend(this, init);
            }
        }
    }

}
