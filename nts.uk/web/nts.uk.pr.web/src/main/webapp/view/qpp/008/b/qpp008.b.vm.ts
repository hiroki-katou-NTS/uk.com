module qpp008.b.viewmodel {
    export class ScreenModel {
        printSetting: KnockoutObservable<PrintSettingModel>;
        hrchyIndexArray: KnockoutComputed<Array<number>>;

        /* SwictchButton*/
        roundingRules: KnockoutObservableArray<any>;
        roundingRules1: KnockoutObservableArray<any>;
        departmentDate: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.printSetting = ko.observable(new PrintSettingModel(null));
            self.departmentDate = ko.observable(Date.now().toString());
            
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
                let itemsDetail = new Array<number>();
                let hrchyIndexValue = new Array<boolean>();
                hrchyIndexValue.push(self.printSetting().hrchyIndex1());
                hrchyIndexValue.push(self.printSetting().hrchyIndex2());
                hrchyIndexValue.push(self.printSetting().hrchyIndex3());
                hrchyIndexValue.push(self.printSetting().hrchyIndex4());
                hrchyIndexValue.push(self.printSetting().hrchyIndex5());
                hrchyIndexValue.push(self.printSetting().hrchyIndex6());
                hrchyIndexValue.push(self.printSetting().hrchyIndex7());
                hrchyIndexValue.push(self.printSetting().hrchyIndex8());
                hrchyIndexValue.push(self.printSetting().hrchyIndex9());
                _.forEach(hrchyIndexValue, function(item, i){
                    
                    
                });
                
                return itemsDetail;
            }, self).extend({ deferred: true });

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadData()
            dfd.resolve();
            return dfd.promise();
        }

        loadData() {
            let self = this;
            let dfd = $.Deferred();
            service.getComparingPrintSet().done(function(data: PrintSettingMapping) {
                self.printSetting(new PrintSettingModel(data));
                dfd.resolve(data);
            })
            return dfd.promise();
        }

        configurationPrintSetting(): any {
            let self = this;
            service.insertUpdateData(new ConfigPrintSettingModel(self.printSetting()));
        }


        closeDialog(): any {
            nts.uk.ui.windows.close();
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
        hrchyIndex1: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex2: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex3: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex4: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex5: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex6: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex7: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex8: KnockoutObservable<boolean> = ko.observable(false);
        hrchyIndex9: KnockoutObservable<boolean> = ko.observable(false);
        constructor(settingMapping: PrintSettingMapping) {
            if (settingMapping) {
                this.plushBackColor(settingMapping.plushBackColor);
                this.minusBackColor(settingMapping.minusBackColor);
                this.showItemIfCfWithNull(settingMapping.showItemIfCfWithNull);
                this.showItemIfSameValue(settingMapping.showItemIfSameValue);
                this.showPayment(settingMapping.showPayment === 0 ? false : true);
                this.totalSet(settingMapping.totalSet === 0 ? false : true);
                this.sumEachDeprtSet(settingMapping.sumEachDeprtSet === 0 ? false : true);
                this.sumDepHrchyIndexSet(settingMapping.sumDepHrchyIndexSet === 0 ? false : true);
                this.mappingHrchyIndex(settingMapping.hrchyIndexList);
            }
        }

        mappingHrchyIndex(hrchyIndexList: Array<number>): void {
            _.forEach(hrchyIndexList, function(value: number, i: number) {
                switch (value) {
                    case 1:
                        if (i == 0) { this.hrchyIndex1(true); }
                        break;
                    case 2:
                        if (i == 0 || i == 1) { this.hrchyIndex2(true); }
                        break;
                    case 3:
                        if (i == 0 || i == 1 || i == 2) { this.hrchyIndex3(true); }
                        break;
                    case 4:
                        if (i != 4) { this.hrchyIndex4(false); }
                        break;
                    case 5:
                        this.hrchyIndex5(true);
                        break;
                    case 6:
                        this.hrchyIndex6(true);
                        break;
                    case 7:
                        this.hrchyIndex7(true);
                        break;
                    case 8:
                        this.hrchyIndex8(true);
                        break;
                    case 9:
                        this.hrchyIndex9(true);
                        break;
                }
            });
        }
    }
    
     export class HrchyIndexModel {
        plushBackColor: string = "#cfe2f3";
        minusBackColor: string = "#f4cccc";
        constructor() {
           
        }
       
    }

    export class PrintSettingMapping {
        plushBackColor: string;
        minusBackColor: string;
        showItemIfCfWithNull: number;
        showItemIfSameValue: number;
        showPayment: number;
        totalSet: number;
        sumEachDeprtSet: number;
        sumDepHrchyIndexSet: number;
        hrchyIndex1: number;
        hrchyIndex2: number;
        hrchyIndex3: number;
        hrchyIndex4: number;
        hrchyIndex5: number;
        hrchyIndexList: Array<number>;
        constructor(plushBackColor: string, minusBackColor: string, showItemIfCfWithNull: number, showItemIfSameValue: number, showPayment: number,
            totalSet: number, sumEachDeprtSet: number, sumDepHrchyIndexSet: number, hrchyIndex1: number, hrchyIndex2: number,
            hrchyIndex3: number, hrchyIndex4: number, hrchyIndex5: number) {
            this.plushBackColor = plushBackColor;
            this.minusBackColor = minusBackColor;
            this.showItemIfCfWithNull = showItemIfCfWithNull;
            this.showItemIfSameValue = showItemIfSameValue;
            this.showPayment = showPayment;
            this.totalSet = totalSet;
            this.sumEachDeprtSet = sumEachDeprtSet;
            this.sumDepHrchyIndexSet = sumDepHrchyIndexSet;
            this.hrchyIndex1 = hrchyIndex1;
            this.hrchyIndex2 = hrchyIndex2;
            this.hrchyIndex3 = hrchyIndex3;
            this.hrchyIndex4 = hrchyIndex4;
            this.hrchyIndex5 = hrchyIndex5;
            this.hrchyIndexList.push(hrchyIndex1);
            this.hrchyIndexList.push(hrchyIndex2);
            this.hrchyIndexList.push(hrchyIndex3);
            this.hrchyIndexList.push(hrchyIndex4);
            this.hrchyIndexList.push(hrchyIndex5);
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
        constructor(printSettingModel: PrintSettingModel) {
            if (printSettingModel) {
                this.plushBackColor = printSettingModel.plushBackColor();
                this.minusBackColor = printSettingModel.minusBackColor();
                this.showItemIfCfWithNull = printSettingModel.showItemIfCfWithNull();
                this.showItemIfSameValue = printSettingModel.showItemIfSameValue();
                this.showPayment = (printSettingModel.showPayment() === true ? 1 : 0);
                this.totalSet = (printSettingModel.totalSet() === true ? 1 : 0);
                this.sumEachDeprtSet = (printSettingModel.sumEachDeprtSet() === true ? 1 : 0);
                this.sumDepHrchyIndexSet = (printSettingModel.sumDepHrchyIndexSet() === true ? 1 : 0);
                //                this.hrchyIndex1 = printSettingModel.hrchyIndex1();
                //                this.hrchyIndex2 = printSettingModel.hrchyIndex2();
                //                this.hrchyIndex3 = printSettingModel.hrchyIndex3;
                //                this.hrchyIndex4 = printSettingModel.hrchyIndex4;
                //                this.hrchyIndex5 = printSettingModel.hrchyIndex5;
            }
        }
        mappingHrchyIndex(printSetting: PrintSettingModel): void {
            let listHrchyIndex = [0, 0, 0, 0, 0];
            let x = 0;
            if (printSetting.hrchyIndex1() === true) {
                x++;
                this.hrchyIndex1 = 1;
                listHrchyIndex[0] = 1;
            }
            if (printSetting.hrchyIndex2() === true) {

            }
        }
    }
}