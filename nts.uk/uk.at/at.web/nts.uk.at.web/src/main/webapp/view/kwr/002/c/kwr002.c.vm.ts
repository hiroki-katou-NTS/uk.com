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

        confirmMarkC18_1: KnockoutObservableArray<any>;
        confirmMarkValue: KnockoutObservable<any>;
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

        attendanceRecordExportSettings: KnockoutObservable<model.AttendanceRecordExportSetting>;

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
            self.confirmMarkValue = ko.observable(true);

            self.confirmMarkC18_1 = ko.observableArray([
                { code: true, name: nts.uk.resource.getText("KWR002_195") },
                { code: false, name: nts.uk.resource.getText("KWR002_196") }
            ]);
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

                var attendanceItemTemp: model.AttendanceRecItem = new model.AttendanceRecItem("", 0, "", columnIndex, position, exportAtr, null, 0);

                var index: number = self.findAttendanceRecItem(attendanceItemTemp);
                setShared('attendanceItem', shareParam, true);
                var link: string;
                if (exportAtr == 1 && columnIndex <= 6) link = '/view/kdl/047/a/index.xhtml';
                else link = '/view/kdl/048/index.xhtml';
                blockUI.grayout();

                nts.uk.ui.windows.sub.modal(link).onClosed(function(): any {
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
                        item = new model.AttendanceRecItem(attendanceItem.attendanceItemName, attendanceItem.layoutCode, attendanceItem.layoutName,
                            attendanceItem.columnIndex, attendanceItem.position, attendanceItem.exportAtr,
                            attendanceItem.attendanceId, attendanceItem.attribute)
                        self.updateAttendanceRecItemList(item);

                    }
                })

            });
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
            const vm  = this;
            const dfd = $.Deferred();
            let attendanceRecExpSetCode: any = getShared('attendanceRecExpSetCode');
            let attendanceRecExpSetName: any = getShared('attendanceRecExpSetName');
            let attendanceRecordExportSettings = getShared('attendanceRecordExportSettings');
            vm.attendanceRecordExportSettings = ko.observable(attendanceRecordExportSettings);
            // Setting C1_2 and C1_3
            if (!attendanceRecExpSetCode) {
                vm.attendanceCode('1');
                vm.attendanceName('デフォルト名');
            }
            else {
                vm.attendanceCode(attendanceRecExpSetCode);
                vm.attendanceName(attendanceRecExpSetName);
            }
            // C2_2
            vm.useSealValue(vm.attendanceRecordExportSettings.sealUseAtr);
            // C12_1 ~ 13
            _.each(attendanceRecordExportSettings.dailyExportItem, (item: model.AttendanceRecordExport) => {
                const columnIndex: number = item.columnIndex;
                vm.attendanceRecExpDaily()[columnIndex] = new model.AttendanceRecExp(item.exportAtr, item.columnIndex, item.useAtr, item.upperPosition, item.lowerPosition);
            })
            for (let i = 1; i <= 13; i++) {
                if (!vm.attendanceRecExpDaily()[i]) {
                    vm.attendanceRecExpDaily()[i] = new model.AttendanceRecExp(1, i, false, '', '');
                }
            }
            // C16_1 ~ 16
            _.each(attendanceRecordExportSettings.monthlyExportItem, item => {
                const columnIndex: number = item.columnIndex;
                vm.attendanceRecExpMonthly()[columnIndex] = new model.AttendanceRecExp(item.exportAtr, item.columnIndex, item.useAtr, item.upperPosition, item.lowerPosition);
            })
            for (let i = 1; i <= 13; i++) {
                if (!vm.attendanceRecExpMonthly()[i]) {
                    vm.attendanceRecExpMonthly()[i] = new model.AttendanceRecExp(1, i, false, '', '');
                }
            }
            // C18_2
            vm.confirmMarkValue(vm.attendanceRecordExportSettings.monthlyConfirmedDisplay)
            // C3_1 ~ 6
            vm.sealName1(vm.attendanceRecordExportSettings.sealStamp[0]);
            vm.sealName2(vm.attendanceRecordExportSettings.sealStamp[1]);
            vm.sealName3(vm.attendanceRecordExportSettings.sealStamp[2]);
            vm.sealName4(vm.attendanceRecordExportSettings.sealStamp[3]);
            vm.sealName5(vm.attendanceRecordExportSettings.sealStamp[4]);
            vm.sealName6(vm.attendanceRecordExportSettings.sealStamp[5]);

            vm.isSmallOrMedium(vm.attendanceRecordExportSettings().exportFontSize === 0 || vm.attendanceRecordExportSettings().exportFontSize === 1);
            vm.isSmall(vm.attendanceRecordExportSettings().exportFontSize === 0);
            service.getApprovalProcessingUseSetting().done(response => {
                vm.useMonthApproverConfirm(response.useMonthApproverConfirm);
            })
            if (vm.useMonthApproverConfirm()) {
                $('#switch-button').focusComponent();
            }
            blockUI.clear();
            return dfd.promise();
        }

        setSharedParams(columnIndex: string, position: number): any {
            const vm = this;
            let params: any = {};
            params.layoutCode = vm.attendanceCode();
            params.layoutName = vm.attendanceName();
            const positionText = position == 1 ? "上" : "下";
            params.directText = getText('KWR002_131')
                + columnIndex
                + getText('KWR002_132')
                + positionText
                + getText('KWR002_133');
            params.itemNameLineDisplayFlag = true;
            params.itemNameIndicatesInputDivision = 2;
            // c12_1~6

            params.attributeSelectionCategory = 2;
            params.attributesList = {
                1: getText('KWR002_141'),
                2: getText('KWR002_141'),
                3: getText('KWR002_141')
            };
            params.selectedAttribute = vm.attendanceRecItemList()[columnIndex].attribute;
            // 勤怠項目ID<List>
            params.attendanceItemIds;
            // 勤怠項目名称 <List>
            params.attendanceItemNames;
            // 勤怠項目の属性<List>
            params.attendanceItemAttrs;
            // 勤怠項目の表示番号<List>
            params.attendanceItemDisplayNumber;
            // 選択済み勤怠項目ID
            params.attendanceItemSelectedId =vm.attendanceRecItemList()[columnIndex].attendanceId;
            return params;
        }
    }

    export module model {

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
            lstJobTitleNotUse: string[];
            supervisorConfirmErrorAtr: ConfirmationOfManagerOrYouself;
        }

        export class ConfirmationOfManagerOrYouself {
            value: number;
            nameId: string;
        }

        // Display object mock
        export class SharedParams {
            // タイトル行
            titleLine: TitleLineObject = new TitleLineObject();
            // 項目名行
            itemNameLine: ItemNameLineObject = new ItemNameLineObject();
            // 属性
            attribute: AttributeObject = new AttributeObject();
            // List<勤怠項目>
            diligenceProjectList: DiligenceProject[] = [];
            // 選択済み勤怠項目ID
            selectedTime: number = 0;

            constructor(init?: Partial<SharedParams>) {
                (<any>Object).assign(this, init);
            }
        }
        
        export class TitleLineObject {
            // 表示フラグ
            displayFlag: boolean = false;
            // 出力項目コード
            layoutCode: String | null = null;
            // 出力項目名
            layoutName: String | null = null;
            // コメント
            directText: String | null = null;

            constructor(init?: Partial<TitleLineObject>) {
                (<any>Object).assign(this, init);
            }
        }
        export class ItemNameLineObject {
            // 表示フラグ
            displayFlag: boolean = false;
            // 表示入力区分
            displayInputCategory: number = 1;
            // 名称
            name: String | null = null;

            constructor(init?: Partial<ItemNameLineObject>) {
                (<any>Object).assign(this, init);
            }
        }
        export class AttributeObject {
            // 選択区分
            selectionCategory: number = 2;
            // List<属性>
            attributeList: AttendaceType[] = [];
            // 選択済み
            selected: number = 1;

            constructor(init?: Partial<AttributeObject>) {
                (<any>Object).assign(this, init);
            }
        }

        export class AttendaceType {
            attendanceTypeCode: number;
            attendanceTypeName: string;
            constructor(attendanceTypeCode: number, attendanceTypeName: string) {
                this.attendanceTypeCode = attendanceTypeCode;
                this.attendanceTypeName = attendanceTypeName;
            }
        }

        export class DiligenceProject {
            // ID
            id: any;
            // 名称
            name: any;
            // 属性
            attributes: any;
            // 表示番号
            indicatesNumber: any;

            constructor(id: any, name: any, attributes: any, indicatesNumber:any) {
                this.id = id;
                this.name = name;
                this.attributes = attributes;
                this.indicatesNumber = indicatesNumber;
            }
        }
            
        // 出勤簿の出力項目設定 Ver25
        export class AttendanceRecordExportSetting {
            companyId: string;
            dailyExportItem: Array<AttendanceRecordExport>;
            monthlyExportItem: Array<AttendanceRecordExport>;
            sealUseAtr: boolean;
            code: any;
            name: any;
            sealStamp: Array<String>;
            nameUseAtr: any;
            exportFontSize: any;
            monthlyConfirmedDisplay: any;

            constructor(init?: Partial<AttendanceRecordExportSetting>) {
                $.extend(this, init);
            }
        }

        export class AttendanceRecordExport {
            exportAtr: any;
            columnIndex: number;
            useAtr: boolean;
            upperPosition: any;
            lowerPosition: any;

            constructor(init?: Partial<AttendanceRecordExport>) {
                $.extend(this, init);
            }
        }
    }

}