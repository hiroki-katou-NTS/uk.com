module nts.uk.pr.view.kmf001.f {
    export module viewmodel {

        import EnumertionModel = service.model.EnumerationModel;

        export class ScreenModel {

            manageDistinctOptions: KnockoutObservableArray<EnumertionModel>;
            applyOptions: KnockoutObservableArray<EnumertionModel>;
            compenManage: KnockoutObservable<number>;
            compenPreApply: KnockoutObservable<number>;
            compenTimeManage: KnockoutObservable<number>;

            expirationDateList: KnockoutObservableArray<ItemModel>;
            timeUnitList: KnockoutObservableArray<ItemModel>;

            checkWorkTime: KnockoutObservable<boolean>;
            checkOverTime: KnockoutObservable<boolean>;

            copenWorkTimeOptions: KnockoutObservableArray<BoxModel>;
            selectedOfWorkTime: KnockoutObservable<number>;
            selectedOfOverTime: KnockoutObservable<number>;

            itemName: KnockoutObservable<string>;
            currentCode: KnockoutObservable<number>
            selectedCode: KnockoutObservable<string>;

            workOneDay: KnockoutObservable<string>;
            workHalfDay: KnockoutObservable<string>;
            workAll: KnockoutObservable<string>;

            overOneDay: KnockoutObservable<string>;
            overHalfDay: KnockoutObservable<string>;
            overAll: KnockoutObservable<string>;

            inputOption: KnockoutObservable<any>;
            isManageCompen: KnockoutObservable<boolean>;
            enableWorkArea: KnockoutObservable<boolean>;
            enableOverArea: KnockoutObservable<boolean>;

            enableWorkAll: KnockoutObservable<boolean>;
            enableOverAll: KnockoutObservable<boolean>;
            enableDesignWork: KnockoutObservable<boolean>;
            enableDesignOver: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                // 代休の管理
                self.manageDistinctOptions = ko.observableArray([
                    { code: '1', name: "管理する" },
                    { code: '0', name: "管理しない" }
                ]);
                self.applyOptions = ko.observableArray([
                    { code: '0', name: "許可しない" },
                    { code: '1', name: "許可する" },
                ]);
                self.compenManage = ko.observable(0);
                self.compenPreApply = ko.observable(0);
                self.compenTimeManage = ko.observable(0);

                self.expirationDateList = ko.observableArray([
                    new ItemModel('1', '1月'),
                    new ItemModel('2', '2ヶ月'),
                    new ItemModel('3', '3ヶ月')
                ]);
                self.timeUnitList = ko.observableArray([
                    new ItemModel('1', '1分'),
                    new ItemModel('2', '2分')
                ]);

                self.checkWorkTime = ko.observable(true);
                self.checkOverTime = ko.observable(false);

                self.copenWorkTimeOptions = ko.observableArray([
                    new BoxModel(1, '指定した時間を代休とする'),
                    new BoxModel(2, '一定時間を超えたら代休とする')]);
                self.selectedOfWorkTime = ko.observable(1);
                self.selectedOfOverTime = ko.observable(1);

                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable('1');

                self.workOneDay = ko.observable('');
                self.workHalfDay = ko.observable('');
                self.workAll = ko.observable('');

                self.overOneDay = ko.observable('');
                self.overHalfDay = ko.observable('');
                self.overAll = ko.observable('');

                self.inputOption = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    filldirection: "right",
                    fillcharacter: "0",
                    width: "50"
                }));

                self.isManageCompen = ko.computed(function() {
                    return self.compenManage() == 1;
                }, self);

                self.enableWorkArea = ko.computed(function() {
                    return self.checkWorkTime() && self.isManageCompen();
                });

                self.enableOverArea = ko.computed(function() {
                    return self.checkOverTime() && self.isManageCompen();
                });

                self.enableWorkAll = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == 2;
                });

                self.enableOverAll = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == 2;
                });

                self.enableDesignWork = ko.computed(function() {
                    return self.enableWorkArea() && self.selectedOfWorkTime() == 1;
                });

                self.enableDesignOver = ko.computed(function() {
                    return self.enableOverArea() && self.selectedOfOverTime() == 1;
                });

                self.checkWorkTime.subscribe(function(data: boolean) {
                    if (data == true) {
                        self.checkOverTime(false);
                    }
                });
                self.checkOverTime.subscribe(function(data: boolean) {
                    if (data == true) {
                        self.checkWorkTime(false);
                    }
                });
            }

            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
        class ItemModel {
            code: string;
            name: string;
            label: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id: number, name: string) {
                this.id = id;
                this.name = name;
            }
        }
    }
}