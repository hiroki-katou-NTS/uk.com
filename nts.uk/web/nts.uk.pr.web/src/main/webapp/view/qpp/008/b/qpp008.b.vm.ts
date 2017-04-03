module qpp008.b.viewmodel {
    export class ScreenModel {
        printSetting: KnockoutObservable<PrintSettingModel>;
        selectedPaymentDate: KnockoutObservable<any>;

        /* SwictchButton*/
        roundingRules: KnockoutObservableArray<any>;
        roundingRules1: KnockoutObservableArray<any>;
        departmentDate: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.printSetting = ko.observable(new PrintSettingModel(null));
            self.selectedPaymentDate = ko.observable(null);

            self.departmentDate = ko.observable('2017/01/13' + 'の部門構成で集計します。')
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
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.reload()
            dfd.resolve();
            return dfd.promise();
        }

        reload() {
            let self = this;
            let dfd = $.Deferred();
            service.getComparingPrintSet().done(function(data: PrintSettingMapping) {
                self.printSetting(new PrintSettingModel(data));
                dfd.resolve(data);
            })
            return dfd.promise();
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

    }

    export class PrintSettingModel {
        plushBackColor: KnockoutObservable<string>;
        minusBackColor: KnockoutObservable<string>;
        showItemIfCfWithNull: KnockoutObservable<number>;
        showItemIfSameValue: KnockoutObservable<number>;
        showPayment: KnockoutObservable<boolean>;
        totalSet: KnockoutObservable<boolean>;
        sumEachDeprtSet: KnockoutObservable<boolean>;
        sumDepHrchyIndexSet: KnockoutObservable<boolean>;
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
                this.plushBackColor = ko.observable(settingMapping.plushBackColor);
                this.minusBackColor = ko.observable(settingMapping.minusBackColor);
                this.showItemIfCfWithNull = ko.observable(settingMapping.showItemIfCfWithNull);
                this.showItemIfSameValue = ko.observable(settingMapping.showItemIfSameValue);
                this.showPayment = ko.observable(settingMapping.showPayment === 0 ? false : true);
                this.totalSet = ko.observable(settingMapping.totalSet === 0 ? false : true);
                this.sumEachDeprtSet = ko.observable(settingMapping.sumEachDeprtSet === 0 ? false : true);
                this.sumDepHrchyIndexSet = ko.observable(settingMapping.sumDepHrchyIndexSet === 0 ? false : true);
                this.mappingHrchyIndex(settingMapping.hrchyIndexList);
            } else {
                this.plushBackColor = ko.observable("#cfe2f3");
                this.minusBackColor = ko.observable("#f4cccc");
                this.showItemIfCfWithNull = ko.observable(0);
                this.showItemIfSameValue = ko.observable(0);
                this.showPayment = ko.observable(true);
                this.totalSet = ko.observable(true);
                this.sumEachDeprtSet = ko.observable(false);
                this.sumDepHrchyIndexSet = ko.observable(false);
            }

        }

        mappingHrchyIndex(hrchyIndexList: Array<number>): void {
            _.forEach(hrchyIndexList, function(value: number, i: number) {
                switch (value) {
                    case 1:
                        if (i == 0) {
                            this.hrchyIndex1(true);
                        }
                        break;
                    case 2:
                        if (i == 0 || i == 1) {
                            this.hrchyIndex2(true);
                        }
                        break;
                    case 3:
                        if (i == 0 || i == 1 || i == 2) {
                            this.hrchyIndex3(true);
                        }
                        break;
                    case 4:
                        if (i != 4) {
                            this.hrchyIndex4(false);
                        }
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

    export class PrintSettingMapping {
        plushBackColor: string;
        minusBackColor: string;
        showItemIfCfWithNull: number;
        showItemIfSameValue: number;
        showPayment: number;
        totalSet: number;
        sumEachDeprtSet: number;
        sumDepHrchyIndexSet: number;
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
            this.hrchyIndexList.push(hrchyIndex1);
            this.hrchyIndexList.push(hrchyIndex2);
            this.hrchyIndexList.push(hrchyIndex3);
            this.hrchyIndexList.push(hrchyIndex4);
            this.hrchyIndexList.push(hrchyIndex5);
        }
    }
}