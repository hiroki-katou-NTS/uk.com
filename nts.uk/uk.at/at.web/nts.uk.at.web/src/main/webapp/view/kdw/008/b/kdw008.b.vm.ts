module nts.uk.at.view.kdw008.b {
    export module viewmodel {
        export class ScreenModel {

            newMode: KnockoutObservable<boolean>;
            isUpdate: boolean;

            currentBusinessTypeCode: KnockoutObservable<string>;
            currentBusinessTypeName: KnockoutObservable<string>;

            currentBusinessType: KnockoutObservable<BusinessTypeDetailModel>;

            // list businessType
            businessTypeList: KnockoutObservableArray<BusinessTypeModel>;
            columns1: KnockoutObservableArray<NtsGridListColumn>;
            selectedCode: KnockoutObservable<any>;

            //list attendance Item
            // attendanceItemList: KnockoutObservableArray<AttendanceItemModel>;
            //combobox select sheetNo
            itemListCbb2: KnockoutObservableArray<ItemModelCbb2>;
            itemNameCbb2: KnockoutObservable<string>;
            currentCodeCbb2: KnockoutObservable<number>;
            selectedCodeCbb2: KnockoutObservable<string>;

            //swap list tab 1
            monthlyDetailList: KnockoutObservableArray<BusinessTypeFormatDetailModel>;
            columns3: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeListSwapMonthly: KnockoutObservableArray<any>;
            businessTypeFormatMonthlyValue: KnockoutObservableArray<AttendanceItemModel>;

            //swap list tab 2
            currentCodeListSwap2: KnockoutObservableArray<any>;
            businessTypeFormatDailyValue: KnockoutObservableArray<AttendanceItemModel>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            selectedSheetNo: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.newMode = ko.observable(false);
                self.isUpdate = true;

                self.currentBusinessTypeCode = ko.observable('');
                self.currentBusinessTypeName = ko.observable('');

                self.selectedSheetNo = ko.observable(1);

                self.businessTypeList = ko.observableArray([]);
                self.currentBusinessType = ko.observable(new BusinessTypeDetailModel(null));

                self.columns1 = ko.observableArray([
                    { headerText: 'コード', key: 'businessTypeCode', width: 100 },
                    { headerText: '勤務種別名称', key: 'businessTypeName', width: 150 }
                ]);
                this.selectedCode = ko.observable();

                //list attendance Item
                // self.attendanceItemList = ko.observableArray([]);
                self.columns3 = ko.observableArray([
                    { headerText: 'コード', key: 'attendanceItemDisplayNumber', width: 70 },
                    { headerText: 'ID', key: 'attendanceItemId', hidden: true, width: 100 },
                    { headerText: '名称', key: 'attendanceItemName', width: 150 }
                ]);

                //swap list 1
                self.monthlyDetailList = ko.observableArray([]);
                self.businessTypeFormatMonthlyValue = ko.observableArray([]);
                var monthlySwapList = [];
                self.currentCodeListSwapMonthly = ko.observableArray(monthlySwapList);
                this.currentCodeListSwapMonthly.subscribe(function(value) {
                    console.log(value);
                });
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
                var x = [];
                this.currentCodeListSwap2 = ko.observableArray(x);
                this.currentCodeListSwap2.subscribe(function(value) {
                    console.log(value);
                });
                self.businessTypeFormatDailyValue = ko.observableArray([]);

                self.selectedCode.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    self.getDetail(newValue);
                    let empSelect = _.find(self.businessTypeList(), bus => {
                        return bus.businessTypeCode == newValue;
                    });
                    if (empSelect) {
                        self.currentBusinessTypeCode(empSelect.businessTypeCode);
                        self.currentBusinessTypeName(empSelect.businessTypeName);
                    }
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.businessTypeList([]);
                new service.Service().getBusinessType().done(function(data: Array<IBusinessType>) {
                    if (data && data.length > 0) {
                        self.businessTypeList(_.map(data, item => { return new BusinessTypeModel(item) }));
                        self.currentBusinessTypeCode(self.businessTypeList()[0].businessTypeCode);
                        self.currentBusinessTypeName(self.businessTypeList()[0].businessTypeName);

                    } else {
                        self.setNewMode();
                    }
                });

                dfd.resolve();
                return dfd.promise();
            }

            setNewMode() {
                let self = this;
                self.newMode(true);
                self.isUpdate = false;
                self.currentBusinessTypeCode(null);
                self.currentBusinessTypeName('');
                self.selectedCode(null);
            }

            getDetail(businessTypeCode: string) {
                let self = this,
                    dfd = $.Deferred();
                new service.Service().getDailyPerformance(self.currentBusinessTypeCode(), self.selectedSheetNo()).done(function(data: IBusinessTypeDetail) {
                    if (data) {
                        self.currentBusinessType(new BusinessTypeDetailModel(data));
                    }
                    dfd.resolve();
                }).fail(error => {

                });
                return dfd.promise();

            }

            addOrUpdateClick() {
                let self = this;
                if (!self.isUpdate) {
                    self.updateData();
                    return;
                }
                self.register();
            }

            register() {
                let self = this;
                var businessTypeFormatDetailDtos = _.map(self.businessTypeFormatMonthlyValue(), item => {
                    var indexOfItem = _.findIndex(self.businessTypeFormatMonthlyValue(), {attendanceItemId: item.attendanceItemId});
                    var obj = {
                        attendanceItemId: item.attendanceItemId,
                        order: indexOfItem,
                        columnWidth: 0
                    };
                    return new BusinessTypeFormatDetailModel(obj); 
                })
                var addBusinessFormatMonthly = new AddBusinessFormatMonthly(self.currentBusinessTypeCode(), businessTypeFormatDetailDtos);
                //                if(self.selectedTab() == "tab-1"){
                new service.Service().addMonthlyDetail(addBusinessFormatMonthly);    
                //                }

            }

            updateData() {
                let self = this;
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

        export class AddBusinessFormatMonthly{
            businesstypeCode: string;
            businessTypeFormatDetailDtos : Array<BusinessTypeFormatDetailModel>;
            constructor(businessTypeCode: string, businessTypeFormatDetailDtos : Array<BusinessTypeFormatDetailModel>){
                let self = this;
                self.businesstypeCode = businessTypeCode || "";
                self.businessTypeFormatDetailDtos = businessTypeFormatDetailDtos || [];
            }    
        } 

        export class BusinessTypeModel {
            businessTypeCode: string;
            businessTypeName: string;
            constructor(data: IBusinessType) {
                let self = this;
                if (!data) return;
                self.businessTypeCode = data.businessTypeCode || "";
                self.businessTypeName = data.businessTypeName || "";
            }
        }

        export class AttendanceItemModel {
            attendanceItemId: number = 0;
            attendanceItemName: string = "";
            attendanceItemDisplayNumber: number = 0;
            constructor(data: IAttendanceItem) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId || 0;
                this.attendanceItemName = data.attendanceItemName || "";
                this.attendanceItemDisplayNumber = data.attendanceItemDisplayNumber || 0;
            }
        }

        export class BusinessTypeFormatDetailModel {
            attendanceItemId: number = 0;
            order: number = 0;
            columnWidth: number = 0;
            constructor(data: IBusinessTypeFormatDetail) {
                if (!data) return;
                this.attendanceItemId = data.attendanceItemId || 0;
                this.order = data.order || 0;
                this.columnWidth = data.columnWidth || 0;
            }
        }

        export class BusinessTypeFormatDailyModel {
            sheetNo: KnockoutObservable<number> = ko.observable(0);
            sheetName: KnockoutObservable<string> = ko.observable("");
            businessTypeFormatDetailDtos: KnockoutObservableArray<BusinessTypeFormatDetailModel> = ko.observableArray([]);
            constructor(data: IBusinessTypeFormatDaily) {
                if (!data) return;
                this.sheetNo(data.sheetNo || 0);
                this.sheetName(data.sheetName || "");
                this.businessTypeFormatDetailDtos(data.businessTypeFormatDetailDtos ? _.map(data.businessTypeFormatDetailDtos, item => { return new BusinessTypeFormatDetailModel(item) }) : []);
            }
        }

        export class BusinessTypeDetailModel {
            attendanceItemDtos: KnockoutObservableArray<AttendanceItemModel> = ko.observableArray([]);
            businessTypeFormatDailyDto: KnockoutObservable<BusinessTypeFormatDailyModel> = ko.observable(new BusinessTypeFormatDailyModel(null));
            businessTypeFormatMonthlyDtos: KnockoutObservableArray<BusinessTypeFormatDetailModel> = ko.observableArray([]);
            constructor(data: IBusinessTypeDetail) {
                if (!data) return;
                this.attendanceItemDtos(data.attendanceItemDtos ? _.map(data.attendanceItemDtos, item => { return new AttendanceItemModel(item) }) : []);
                this.businessTypeFormatDailyDto(data.businessTypeFormatDailyDto ? new BusinessTypeFormatDailyModel(data.businessTypeFormatDailyDto) : null);
                this.businessTypeFormatMonthlyDtos(data.businessTypeFormatMonthlyDtos ? _.map(data.businessTypeFormatMonthlyDtos, item => { return new BusinessTypeFormatDetailModel(item) }) : []);
            }
        }

        export interface IBusinessTypeDetail {
            attendanceItemDtos: Array<IAttendanceItem>;
            businessTypeFormatDailyDto: IBusinessTypeFormatDaily;
            businessTypeFormatMonthlyDtos: Array<IBusinessTypeFormatDetail>;
        }

        export interface IBusinessType {
            businessTypeCode: string;
            businessTypeName: string;
        }
        export interface IAttendanceItem {
            attendanceItemId: number;
            attendanceItemName: string;
            attendanceItemDisplayNumber: number;
        }
        export interface IBusinessTypeFormatDetail {
            attendanceItemId: number;
            order: number;
            columnWidth: number;
        }
        export interface IBusinessTypeFormatDaily {
            sheetNo: number;
            sheetName: string;
            businessTypeFormatDetailDtos: Array<IBusinessTypeFormatDetail>;
        }

    }
}
