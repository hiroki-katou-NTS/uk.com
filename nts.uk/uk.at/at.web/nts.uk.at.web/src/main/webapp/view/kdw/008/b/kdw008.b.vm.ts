module nts.uk.at.view.kdw008.b {
    export module viewmodel {
        export class ScreenModel {

            //combobox select sheetNo
            itemListCbb2: KnockoutObservableArray<ItemModelCbb2>;
            itemNameCbb2: KnockoutObservable<string>;
            currentCodeCbb2: KnockoutObservable<number>
            selectedCodeCbb2: KnockoutObservable<string>;

            //swap list tab 1
            itemsSwap1: KnockoutObservableArray<ItemModel2>;
            currentCodeListSwap1: KnockoutObservableArray<any>;

            //swap list tab 2
            itemsSwap2: KnockoutObservableArray<ItemModel2>;
            columns2: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwap2: KnockoutObservableArray<any>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            items: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            constructor() {
                var self = this;

                this.items = ko.observableArray([]);

                for (let i = 1; i < 3; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給'));
                }

                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 70 },
                    { headerText: '勤務種別名称', key: 'name', width: 120 }
                ]);

                this.currentCode = ko.observable();

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                //combobox select sheetNo tab2
                self.itemListCbb2 = ko.observableArray([
                    new ItemModelCbb2('基本給'),
                    new ItemModelCbb2('役職手当'),
                    new ItemModelCbb2('基本給2')
                ]);
                self.selectedCodeCbb2 = ko.observable('基本給');

                //swaplist 2
                this.itemsSwap2 = ko.observableArray([]);

                //sample swaplist 2
                let array = [];
                for (var i = 0; i < 10000; i++) {
                    array.push(new ItemModel(i, '基本給', "description"));
                }
                self.itemsSwap2(array);

                //swaplist 1
                this.itemsSwap1 = ko.observableArray([]);
                var x1 = [];
                this.currentCodeListSwap1 = ko.observableArray(x1);
                this.currentCodeListSwap1.subscribe(function(value) {
                    console.log(value);
                });

                //sample swaplist 2
                let array = [];
                for (var i = 0; i < 10000; i++) {
                    array.push(new ItemModel(i, '基本給', "description"));
                }
                self.itemsSwap1(array);

                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 }
                ]);
                var x = [];
                this.currentCodeListSwap2 = ko.observableArray(x);
                this.currentCodeListSwap2.subscribe(function(value) {
                    console.log(value);
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }

        export class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemModelCbb2 {
            nameCbb2: string;
            labelCbb2: string;
            constructor(nameCbb2: string) {
                this.nameCbb2 = nameCbb2;
                this.labelCbb2 = nameCbb2;
            }
        }

        export class ItemModel2 {
            code: number;
            name: string;
            description: string;
            deletable: boolean;
            constructor(code: number, name: string, description: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.deletable = code % 3 === 0;
            }
        }

        export class AttendanceItemModel {
            attendanceItemId: KnockoutObservable<number>;
            attendanceItemName: KnockoutObservable<string>;
            attendanceItemDisplayNumber: KnockoutObservable<number>;
            constructor(data: any) {
                let self = this;
                self.attendanceItemId = data.attendanceItemId;
                self.attendanceItemName = data.attendanceItemName;
                self.attendanceItemDisplayNumber = data.attendanceItemDisplayNumber;
            }
        }

        export class BusinessTypeModel {
            businessTypeCode: KnockoutObservable<string>;
            businessTypeName: KnockoutObservable<string>;
            constructor(data: any) {
                let self = this;
                self.businessTypeCode = data.businessTypeCode;
                self.businessTypeName = data.businessTypeName;
            }
        }

        export class BusinessTypeFormatMonthlyModel {
            businessTypeFormatMonthlyDtos: KnockoutObservableArray<BusinessTypeFormatDetailModel> = ko.observableArray([]);
            constructor(data: IBusinessTypeFormatDaily) {
                let self = this;
                self.businessTypeFormatMonthlyDtos(_.map(data.businessTypeFormatDetailDtos, item => { return new BusinessTypeFormatDetailModel(item) }))
            }
        }

        export class BusinessTypeFormatDailyModel {
            sheetNo: KnockoutObservable<number> = ko.observable(0);
            sheetName: KnockoutObservable<string> = ko.observable("");
            businessTypeFormatDetailDtos: KnockoutObservableArray<BusinessTypeFormatDetailModel> = ko.observableArray([]);
            constructor(data: IBusinessTypeFormatDaily) {
                let self = this;
                self.sheetNo(data.sheetNo);
                self.sheetName(data.sheetName);
                self.businessTypeFormatDetailDtos(_.map(data.businessTypeFormatDetailDtos, item => { return new BusinessTypeFormatDetailModel(item) }));
            }
        }

        export class BusinessTypeFormatDetailModel {
            attendanceItemId: number;
            order: number;
            columnWidth: number;
            constructor(data: IBusinessTypeFormatDetail) {
                this.attendanceItemId = data.attendanceItemId;
                this.order = data.order;
                this.columnWidth = data.columnWidth;
            }
        }

        export interface IBusinessTypeFormatDaily {
            sheetNo?: number;
            sheetName?: string;
            businessTypeFormatDetailDtos: Array<IBusinessTypeFormatDetail>;
        }

        interface IBusinessTypeFormatDetail {
            attendanceItemId: number;
            order: number;
            columnWidth: number;
        }
    }
}
