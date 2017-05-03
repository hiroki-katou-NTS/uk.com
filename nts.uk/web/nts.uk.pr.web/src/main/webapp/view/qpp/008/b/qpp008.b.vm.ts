module qpp008.b.viewmodel {
    export class ScreenModel {
        printSetting: KnockoutObservable<PrintSettingModel>;
        hrchyIndexArray: KnockoutComputed<Array<number>>;
        hrchyIndexList: KnockoutObservableArray<HrchyIndexModel>;
        /* SwictchButton*/
        roundingRules: KnockoutObservableArray<any>;
        roundingRules1: KnockoutObservableArray<any>;

        /* check dirty*/
        printSettingDirty: nts.uk.ui.DirtyChecker;

        constructor() {
            let self = this;
            self.printSetting = ko.observable(new PrintSettingModel(null));
            self.printSettingDirty = new nts.uk.ui.DirtyChecker(self.printSetting);
            self.hrchyIndexList = ko.observableArray([]);
            for (let i = 1; i < 10; i++) {
                self.hrchyIndexList.push(new HrchyIndexModel(i, i + " 階層", true));
            }
            /*Switch*/
            //B_SEL_010
            self.roundingRules = ko.observableArray([
                { code: '0', name: '表示する' },
                { code: '1', name: '表示しない' },
            ]);
            //B_SEL_011
            self.roundingRules1 = ko.observableArray([
                { code1: '0', name1: '表示する' },
                { code1: '1', name1: '表示しない' },
            ]);

            self.hrchyIndexArray = ko.computed(function() {
                let itemsDetail = [0, 0, 0, 0, 0];
                let hrchyIndexSelect = self.printSetting().hrchyIndexSelectId().sort();
                let newHrchyIndexList = new Array();
                let enable = true;
                if (hrchyIndexSelect.length >= 5) {
                    enable = false
                }
                for (let i = 1; i < 10; i++) {
                    newHrchyIndexList.push(new HrchyIndexModel(i, i + " 階層", enable));
                }
                _.forEach(hrchyIndexSelect, function(item, i) {
                    for (let x = 0; x < 5; x++) {
                        if (itemsDetail[x] === 0 && i < 5) {
                            itemsDetail[x] = item;
                            _.find(newHrchyIndexList, function(o) {
                                if(o.id === item){
                                    o.enable(true);
                                    return true;
                                }
                                return false; 
                            });
                            break;
                        }
                    }
                });
                self.hrchyIndexList(newHrchyIndexList);
                return itemsDetail;
            }, self).extend({ deferred: true });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadData().done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        loadData() {
            let self = this;
            let dfd = $.Deferred();
            service.getComparingPrintSet().done(function(data: any) {
                self.printSetting(new PrintSettingModel(data));
                self.printSettingDirty.reset();
                dfd.resolve();
            })
            return dfd.promise();
        }

        configurationPrintSetting(): any {
            let self = this;
            service.insertUpdateData(new ConfigPrintSettingModel(self.printSetting(), self.hrchyIndexArray())).done(function() {
                self.loadData();
            });
        }

        closeDialog(): any {
            let self = this;
            if (!self.printSettingDirty.isDirty()) {
                nts.uk.ui.windows.close();
                return;
            }
            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                self.printSettingDirty.reset();
                nts.uk.ui.windows.close();
            }).ifCancel(function() {
                return;
            })

        }
    }

    export class PrintSettingModel {
        plushBackColor: KnockoutObservable<string> = ko.observable("#cfe2f3");
        minusBackColor: KnockoutObservable<string> = ko.observable("#f4cccc");
        showItemIfCfWithNull: KnockoutObservable<number> = ko.observable(0);
        showItemIfSameValue: KnockoutObservable<number> = ko.observable(0);
        showPayment: KnockoutObservable<boolean> = ko.observable(true);
        totalSet: KnockoutObservable<boolean> = ko.observable(true);
        sumEachDeprtSet: KnockoutObservable<boolean> = ko.observable(false);
        sumDepHrchyIndexSet: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndexSelectId: KnockoutObservableArray<number> = ko.observableArray([]);
        constructor(settingMapping: any) {
            let self = this;
            if (settingMapping) {
                self.plushBackColor(settingMapping.plushBackColor);
                self.minusBackColor(settingMapping.minusBackColor);
                self.showItemIfCfWithNull(settingMapping.showItemIfCfWithNull);
                self.showItemIfSameValue(settingMapping.showItemIfSameValue);
                self.showPayment(settingMapping.showPayment === 0 ? false : true);
                self.totalSet(settingMapping.totalSet === 0 ? false : true);
                self.sumEachDeprtSet(settingMapping.sumEachDeprtSet === 0 ? false : true);
                self.sumDepHrchyIndexSet(settingMapping.sumDepHrchyIndexSet === 0 ? false : true);
                if (settingMapping.hrchyIndex1 > 0) { self.hrchyIndexSelectId.push(settingMapping.hrchyIndex1); }
                if (settingMapping.hrchyIndex2 > 1) { self.hrchyIndexSelectId.push(settingMapping.hrchyIndex2); }
                if (settingMapping.hrchyIndex3 > 2) { self.hrchyIndexSelectId.push(settingMapping.hrchyIndex3); }
                if (settingMapping.hrchyIndex4 > 3) { self.hrchyIndexSelectId.push(settingMapping.hrchyIndex4); }
                if (settingMapping.hrchyIndex5 > 4) { self.hrchyIndexSelectId.push(settingMapping.hrchyIndex5); }
            }
        }
    }

    export class HrchyIndexModel {
        id: number;
        name: string;
        enable: KnockoutObservable<boolean>
        constructor(id: number, name: string, enable: boolean) {
            let self = this;
            self.id = id;
            self.name = name;
            self.enable = ko.observable(enable);
        }
    }

    export class ConfigPrintSettingModel {
        plushBackColor: string = "#cfe2f3";
        minusBackColor: string = "#f4cccc";
        showItemIfCfWithNull: number = 0;
        showItemIfSameValue: number = 0;
        showPayment: number = 1;
        totalSet: number = 1;
        sumEachDeprtSet: number = 0;
        sumDepHrchyIndexSet: number = 0;
        hrchyIndex1: number = 0;
        hrchyIndex2: number = 0;
        hrchyIndex3: number = 0;
        hrchyIndex4: number = 0;
        hrchyIndex5: number = 0;
        constructor(printSettingModel: PrintSettingModel, hrchyIndexArray: Array<number>) {
            if (printSettingModel) {
                this.plushBackColor = printSettingModel.plushBackColor();
                this.minusBackColor = printSettingModel.minusBackColor();
                this.showItemIfCfWithNull = printSettingModel.showItemIfCfWithNull();
                this.showItemIfSameValue = printSettingModel.showItemIfSameValue();
                this.showPayment = (printSettingModel.showPayment() === true ? 1 : 0);
                this.totalSet = (printSettingModel.totalSet() === true ? 1 : 0);
                this.sumEachDeprtSet = (printSettingModel.sumEachDeprtSet() === true ? 1 : 0);
                this.sumDepHrchyIndexSet = (printSettingModel.sumDepHrchyIndexSet() === true ? 1 : 0);
                this.hrchyIndex1 = hrchyIndexArray[0];
                this.hrchyIndex2 = hrchyIndexArray[1];
                this.hrchyIndex3 = hrchyIndexArray[2];
                this.hrchyIndex4 = hrchyIndexArray[3];
                this.hrchyIndex5 = hrchyIndexArray[4];
            }
        }

    }
}