module nts.uk.at.view.kmf004.a.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModelSpecialHoliday>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        selectedValue: KnockoutObservable<any>;
        enable: KnockoutObservable<boolean>;
        option1: KnockoutObservable<any>;
        option2: KnockoutObservable<any>;
        date: KnockoutObservable<string>;
        roundingRules1: KnockoutObservableArray<any>;
        roundingRules2: KnockoutObservableArray<any>;
        roundingRules3: KnockoutObservableArray<any>;
        roundingRules4: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        selectedRuleCode: any;

        constructor() {
            var self = this;

            self.items = ko.observableArray([]);
            self.enable = ko.observable(true);
            self.selectedValue = ko.observable(false);
            self.option1 = ko.observable({ value: 0, text: nts.uk.resource.getText('KMF004_23') });
            self.option2 = ko.observable({ value: 1, text: nts.uk.resource.getText('KMF004_24') });
            self.date = ko.observable('');
            self.selectedCode = ko.observable('0002')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);



            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);


            self.roundingRules1 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_38') },
                { code: '2', name: nts.uk.resource.getText('KMF004_39') }
            ]);

            self.roundingRules2 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_51') },
                { code: '2', name: nts.uk.resource.getText('KMF004_52') }
            ]);

            self.roundingRules3 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_125') },
                { code: '2', name: nts.uk.resource.getText('KMF004_126') }
            ]);

            self.roundingRules4 = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText('KMF004_61') },
                { code: '2', name: nts.uk.resource.getText('KMF004_62') }
            ]);

            self.selectedRuleCode = ko.observable(1);

            self.currentCode = ko.observable();

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText('KMF004_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText('KMF004_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText('KMF004_19'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: nts.uk.resource.getText('KMF004_20'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: nts.uk.resource.getText('KMF004_21'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
//            self.getAllSpecialHoliday();
            
            dfd.resolve();
            return dfd.promise();
        }

//        getAllSpecialHoliday(): JQueryPromise<any> {
//            var self = this;
//            var dfd = $.Deferred();
//            service.findAllSpecialHoliday().done(function(specialHoliday_arr: Array<model.SpecialHolidayDto>) {
//                if (specialHoliday_arr && specialHoliday_arr.length) {
//                    for (var i = 0; i < specialHoliday_arr.length; i++) {
//                        self.items.push(new model.SpecialHolidayDto(
//                            specialHoliday_arr[i].companyId,
//                            specialHoliday_arr[i].specialHolidayCode,
//                            specialHoliday_arr[i].specialHolidayName,
//                            specialHoliday_arr[i].grantPeriodicCls,
//                            specialHoliday_arr[i].memo));
//                    }
//                }
//                dfd.resolve();
//            }).fail(function(error) {
//                alert(error.message);
//                dfd.reject(error);
//            });
//
//            return dfd.promise();
//        }

        openBDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1000, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
        openDDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/b/index.xhtml', { title: '代行リスト', height: 600, width: 1100, dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        openGDialog() {   
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/g/index.xhtml', { title: '続柄に対する付与日数', dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }

        openHDialog() {
            let self = this;
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.sub.modal('/view/kmf/004/h/index.xhtml', { title: '続柄の登録',dialogClass: 'no-close' }).onClosed(function(): any {
                nts.uk.ui.block.clear();
            });

        }
    }
    
    class ItemModelSpecialHoliday {
        specialHolidayCode: string;
        specialHolidayName: string;
        constructor(specialHolidayCode: string, specialHolidayName: string) {
            this.specialHolidayCode = specialHolidayCode;
            this.specialHolidayName = specialHolidayName;
        }
    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export module model {
        export class SpecialHolidayDto {
            companyId: string;
            specialHolidayCode: string;
            specialHolidayName: string;
            grantPeriodicCls: number;
            memo: string;
            constructor(companyId: string, specialHolidayCode: string, specialHolidayName: string, grantPeriodicCls: number, memo: string) {
                this.companyId = companyId;
                this.specialHolidayCode = specialHolidayCode;
                this.specialHolidayName = specialHolidayName;
                this.grantPeriodicCls = grantPeriodicCls;
                this.memo = memo;
            }
        }
    }
}