module nts.uk.at.kha003.d {

    const API = {
        aggregation: 'at/screen/kha003/d/aggregation-result',
        csv: 'at/screen/kha003/d/export-csv',
        excel: 'at/function/kha/003/d/report/excel'
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        dateHeaders: KnockoutObservableArray<DateHeader>;
        contents: KnockoutObservableArray<any>;
        c21Params: KnockoutObservable<any>;
        c31Params: KnockoutObservable<any>;
        c41Params: KnockoutObservable<any>;
        c51Params: KnockoutObservable<any>;
        c21Text: KnockoutObservable<any>;
        c31Text: KnockoutObservable<any>;
        c41Text: KnockoutObservable<any>;
        c51Text: KnockoutObservable<any>;
        dateRange: KnockoutObservable<any>;
        bScreenData: KnockoutObservable<any>;
        cScreenData: KnockoutObservable<any>;
        agCommand: KnockoutObservable<any>;
        preriod: KnockoutObservable<any>;
        maxDateRange: any = 0;

        constructor() {
            super();
            const vm = this;
            vm.c21Params = ko.observable();
            vm.c31Params = ko.observable();
            vm.c41Params = ko.observable();
            vm.c51Params = ko.observable();
            vm.c21Text = ko.observable();
            vm.c31Text = ko.observable();
            vm.c41Text = ko.observable();
            vm.c51Text = ko.observable();
            vm.dateRange = ko.observable();
            vm.dateHeaders = ko.observableArray([]);
            vm.bScreenData = ko.observable();
            vm.cScreenData = ko.observable();
            vm.agCommand = ko.observable();
            vm.preriod = ko.observable();
            vm.contents = ko.observableArray([]);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.$window.storage('kha003AShareData').done((aData: any) => {
                console.log('in side kha003 D:' + aData)
                vm.c21Params(aData.c21);
                vm.c31Params(aData.c31);
                vm.c41Params(aData.c41);
                vm.c51Params(aData.c51);
                vm.c21Text(aData.c21.name);
                vm.c31Text(aData.c31.name);
                vm.c41Text(aData.c41.name);
                vm.c51Text(aData.c51.name);
                vm.arrangeTaskHeader(vm.c21Text());
                vm.arrangeTaskHeader(vm.c31Text());
                vm.arrangeTaskHeader(vm.c41Text());
                vm.arrangeTaskHeader(vm.c51Text());
                vm.$window.storage('kha003BShareData').done((bData) => {
                    vm.bScreenData(bData);
                    vm.$window.storage('kha003CShareData').done((cData: any) => {
                        vm.cScreenData(cData);
                        vm.dateRange(cData.dateRange);
                        vm.getDateRange(vm.dateRange().startDate, vm.dateRange().endDate);
                        let command = vm.getItemData(aData, bData, cData);
                        vm.initData(command);
                    })
                })
            })
        }

        /**
         * 集計結果を作成する
         * @param command
         */
        initData(command: any) {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API.aggregation, command).done((data) => {
                vm.agCommand(data);
                vm.printContents(data);
            }).fail(function (error) {
                vm.$dialog.error({messageId: 'Msg_2171'}).then(() => {
                    vm.displayKha003CScreen();
                });
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        /**
         *  print data
         *
         * @param data
         */
        printContents(data: any) {
            let vm = this;
            var detailFormatSetting = data.summaryTableFormat;
            var dispFormat = detailFormatSetting.displayFormat;
            var totalUnit = detailFormatSetting.totalUnit;
            var isDisplayTotal = detailFormatSetting.dispHierarchy == 1;
            var outputContent = data.outputContent;
            var totalLevel = data.countTotalLevel;
            if (totalLevel == 0) return;
            switch (totalLevel) {
                case 1:
                    vm.printData1Level(outputContent, isDisplayTotal, vm.maxDateRange, dispFormat, totalUnit);
                    break;
                case 2:
                    vm.printData2Level(outputContent, isDisplayTotal, vm.maxDateRange, dispFormat, totalUnit);
                    break;
                case 3:
                    vm.printData3Level(outputContent, isDisplayTotal, vm.maxDateRange, dispFormat, totalUnit);
                    break;
                case 4:
                    vm.printData4Level(outputContent, isDisplayTotal, vm.maxDateRange, dispFormat, totalUnit);
                    break;
            }
        }

        /**
         *  print data for level 1
         *
         * @param outputContent
         * @param isDispTotal
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printData1Level(outputContent: any, isDispTotal: boolean, maxDateRange: any, dispFormat: any, unit: any) {
            let vm = this;
            var itemDetails = outputContent.itemDetails;
            var countRow = 3;
            for (var i = 1; i <= itemDetails.length; i++) {
                var level1 = itemDetails[i - 1];
                let subArray = [];
                subArray.push(new Content(level1.displayInfo.name))
                for (let verticalItem of level1.verticalTotalList) {
                    subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
                }
                if (isDispTotal) {
                    subArray.push(new Content(vm.formatValue(level1.totalPeriod, dispFormat)))
                }
                vm.contents.push(subArray);
            }
            if (isDispTotal) {
                vm.contents.push(vm.printAllTotalByVertical(outputContent, maxDateRange, dispFormat, unit));
            }
        }

        /**
         * print data for level 2
         *
         * @param outputContent
         * @param isDispTotal
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printData2Level(outputContent: any, isDispTotal: any, maxDateRange: any, dispFormat: any, unit: any) {
            let vm = this;
            var itemDetails = outputContent.itemDetails;
            var countRow = 3;
            for (let level1 of itemDetails) {
                let isPrintNameLv1 = false;
                let mergeIndexLv1 = countRow;
                let childHierarchyList = level1.childHierarchyList;
                for (let i = 1; i <= childHierarchyList.length; i++) {
                    let subArray = [];
                    let level2 = childHierarchyList[i - 1];
                    subArray.push(new Content(!isPrintNameLv1 ? level1.displayInfo.name : ""))
                    subArray.push(new Content(level2.displayInfo.name))
                    isPrintNameLv1 = true;
                    for (let verticalItem of level2.verticalTotalList) {
                        subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
                    }
                    if (isDispTotal) {
                        subArray.push(new Content(vm.formatValue(level2.totalPeriod, dispFormat)))
                    }
                    vm.contents.push(subArray);
                }
                if (isDispTotal) {
                    vm.contents.push(vm.printTotalByVerticalOfEachLevel(level1, maxDateRange, dispFormat, unit, 2));
                }
            }
            if (isDispTotal) {
                vm.contents.push(vm.printAllTotalByVertical(outputContent, maxDateRange, dispFormat, unit));
            }
        }

        /**
         * print data for level 3
         *
         * @param outputContent
         * @param isDispTotal
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printData3Level(outputContent: any, isDispTotal: any, maxDateRange: any, dispFormat: any, unit: any) {
            let vm = this;
            var itemDetails = outputContent.itemDetails;
            var countRow = 3;
            for (let level1 of itemDetails) {
                var isPrintNameLv1 = false;
                var mergeIndexLv1 = countRow;
                for (let level2 of level1.childHierarchyList) {
                    var isPrintNameLv2 = false;
                    var mergeIndexLv2 = countRow;
                    var childHierarchyList = level2.childHierarchyList;
                    for (let i = 1; i <= childHierarchyList.length; i++) {
                        let subArray = [];
                        let level3 = childHierarchyList[i - 1];
                        subArray.push(new Content(!isPrintNameLv1 ? level1.displayInfo.name : ""))
                        isPrintNameLv1 = true;
                        subArray.push(new Content(!isPrintNameLv2 ? level2.displayInfo.name : ""))
                        isPrintNameLv2 = true;
                        subArray.push(new Content(level3.displayInfo.name))
                        for (let verticalItem of level3.verticalTotalList) {
                            subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
                        }
                        if (isDispTotal) {
                            subArray.push(new Content(vm.formatValue(level3.totalPeriod, dispFormat)))
                        }
                        vm.contents.push(subArray);

                    }
                    if (isDispTotal) {
                        vm.contents.push(vm.printTotalByVerticalOfEachLevel(level2, maxDateRange, dispFormat, unit, 3));
                    }
                }
                if (isDispTotal) {
                    vm.contents.push(vm.printTotalByVerticalOfEachLevel(level1, maxDateRange, dispFormat, unit, 3));
                }
            }
            if (isDispTotal) {
                vm.contents.push(vm.printAllTotalByVertical(outputContent, maxDateRange, dispFormat, unit));
            }
        }

        isSpan(value: any): boolean {
            if (value === "col_span") {
                return true;
            }
            return false;
        }

        /**
         * Total of each column of each level by vertical
         *
         * @param summaryItemDetail
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printTotalByVerticalOfEachLevel(summaryItemDetail: any, maxDateRange: any, dispFormat: any, unit: any, level: any): any {
            let vm = this;
            let subArray = [];
            switch (level) {
                case 1:
                    break;
                case 2:
                    subArray.push(new Content(summaryItemDetail.displayInfo.name + vm.$i18n("KHA003_100")));
                    subArray.push(new Content(""));
                    break;
                case 3:
                    subArray.push(new Content(""));
                    subArray.push(new Content(summaryItemDetail.displayInfo.name + vm.$i18n("KHA003_100")));
                    subArray.push(new Content(""));
                    break;
                case 4:
                    subArray.push(new Content(""));
                    subArray.push(new Content(""));
                    subArray.push(new Content(summaryItemDetail.displayInfo.name + vm.$i18n("KHA003_100")));
                    subArray.push(new Content(""));
                    break;
            }
            for (let verticalItem of summaryItemDetail.verticalTotalList) {
                subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
            }
            subArray.push(new Content(vm.formatValue(summaryItemDetail.totalPeriod, dispFormat)));
            return subArray;
        }

        /**
         * print data for level 4
         *
         * @param outputContent
         * @param isDispTotal
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printData4Level(outputContent: any, isDispTotal: any, maxDateRange: any, dispFormat: any, unit: any) {
            let vm = this;
            var itemDetails = outputContent.itemDetails;
            var countRow = 3;
            for (let level1 of itemDetails) {
                var isPrintNameLv1 = false;
                var mergeIndexLv1 = countRow;
                for (let level2 of level1.childHierarchyList) {
                    var isPrintNameLv2 = false;
                    var mergeIndexLv2 = countRow;
                    for (let level3 of level2.childHierarchyList) {
                        var isPrintNameLv3 = false;
                        var mergeIndexLv3 = countRow;
                        var childHierarchyList = level3.childHierarchyList;
                        for (let i = 1; i <= childHierarchyList.length; i++) {
                            let subArray = [];

                            var level4 = childHierarchyList[i - 1];
                            subArray.push(new Content(!isPrintNameLv1 ? level1.displayInfo.name : ""))
                            isPrintNameLv1 = true;
                            subArray.push(new Content(!isPrintNameLv2 ? level2.displayInfo.name : ""))
                            isPrintNameLv2 = true;
                            subArray.push(new Content(!isPrintNameLv3 ? level3.displayInfo.name : ""))
                            isPrintNameLv3 = true;
                            subArray.push(new Content(level4.displayInfo.name))
                            for (let verticalItem of level4.verticalTotalList) {
                                subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
                            }
                            if (isDispTotal) {
                                subArray.push(new Content(vm.formatValue(level4.totalPeriod, dispFormat)))
                            }
                            vm.contents.push(subArray);
                        }
                        if (isDispTotal) {
                            vm.contents.push(vm.printTotalByVerticalOfEachLevel(level3, maxDateRange, dispFormat, unit, 4));
                        }
                    }
                    if (isDispTotal) {
                        vm.contents.push(vm.printTotalByVerticalOfEachLevel(level2, maxDateRange, dispFormat, unit, 4));
                    }
                }
                if (isDispTotal) {
                    vm.contents.push(vm.printTotalByVerticalOfEachLevel(level1, maxDateRange, dispFormat, unit, 4));
                }
            }
            if (isDispTotal) {
                vm.contents.push(vm.printAllTotalByVertical(outputContent, maxDateRange, dispFormat, unit));
            }
        }

        /**
         * All total by vertical
         *
         * @param outputContent
         * @param maxDateRange
         * @param dispFormat
         * @param unit
         */
        printAllTotalByVertical(outputContent: any, maxDateRange: any, dispFormat: any, unit: any): any {
            let vm = this;
            let subArray = [];
            subArray.push(new Content(vm.$i18n("KHA003_99")));
            subArray.push(new Content(""));
            subArray.push(new Content(""));
            for (let verticalItem of outputContent.verticalTotalValues) {
                subArray.push(new Content(vm.formatValue(verticalItem.workingHours, dispFormat)));
            }
            subArray.push(new Content(vm.formatValue(outputContent.totalPeriod, dispFormat)));
        }

        /**
         * Format value by display format
         *
         * @param value
         * @param displayFormat
         * @return String
         */
        formatValue(value: any, displayFormat: any): string {
            let vm = this;
            let targetValue = null;
            switch (displayFormat) {
                case 0:
                    targetValue = (value / 60).toLocaleString('en-US', {maximumFractionDigits: 2})
                    break;
                case 1:
                    let spilt: any = (value / 60).toFixed(2).toString().split('.');
                    let integerPart = spilt[0];
                    let decimalPart: any = spilt[1] * 60;
                    let remainValue = integerPart - decimalPart;
                    remainValue = vm.findReminder(remainValue)
                    targetValue = integerPart.toLocaleString('en-US') + ":" + (isNaN(remainValue) ? "" : remainValue);
                    break;
                case 2:
                    targetValue = value.toLocaleString('en-US')
                    break;
            }
            return targetValue;
        }

        /**
         * find reminder
         * @param remainValue
         */
        findReminder(remainValue: any): any {
            return Math.abs(parseInt(remainValue.toString()))
                .toString()
                .substring(0, 2)
        }


        /**
         * function for get item data to map with UI
         * @param aScreenData
         * @param bScreenData
         * @param cScreenData
         * @author rafiqul.islam
         */
        getItemData(aScreenData: any, bScreenData: any, cScreenData: any) {
            let vm = this;
            let data = cScreenData;
            let affWorkplaceInfoList: any = vm.mapCode(vm.mapTaskToCOde(0, aScreenData, cScreenData), 0, bScreenData);
            let workPlaceInfoList: any = vm.mapCode(vm.mapTaskToCOde(1, aScreenData, cScreenData), 1, bScreenData);
            let employeeInfoList: any = vm.mapCode(vm.mapTaskToCOde(2, aScreenData, cScreenData), 2, bScreenData);
            let task1List: any = vm.mapCode(vm.mapTaskToCOde(3, aScreenData, cScreenData), 3, bScreenData);
            let task2List: any = vm.mapCode(vm.mapTaskToCOde(4, aScreenData, cScreenData), 4, bScreenData);
            let task3List: any = vm.mapCode(vm.mapTaskToCOde(5, aScreenData, cScreenData), 5, bScreenData);
            let task4List: any = vm.mapCode(vm.mapTaskToCOde(6, aScreenData, cScreenData), 6, bScreenData);
            let task5List: any = vm.mapCode(vm.mapTaskToCOde(7, aScreenData, cScreenData), 7, bScreenData);
            let masterNameInfo = {
                affWorkplaceInfoList: affWorkplaceInfoList,
                workPlaceInfoList: workPlaceInfoList,
                employeeInfoList: employeeInfoList,
                task1List: task1List,
                task2List: task2List,
                task3List: task3List,
                task4List: task4List,
                task5List: task5List,
            };
            let period = {
                totalUnit: aScreenData.totalUnit,
                startDate: aScreenData.totalUnit == 0 ? bScreenData.dateRange.startDate : null,
                endDate: aScreenData.totalUnit == 0 ? bScreenData.dateRange.endDate : null,
                yearMonthStart: aScreenData.totalUnit == 1 ? bScreenData.dateRange.startDate : null,
                yearMonthEnd: aScreenData.totalUnit == 1 ? bScreenData.dateRange.endDate : null
            };
            vm.preriod(period);
            return {
                code: aScreenData.code,
                masterNameInfo: masterNameInfo,
                workDetailList: bScreenData.workDetailDataList,
                period: period
            }
        }


        /**
         * function map selected codes from Kha003 c screen
         * @param selectedCodes
         * @param type
         */
        mapTaskToCOde(type: any, aScreenData: any, cScreenData: any) {
            let vm = this;
            let array: any = [];
            switch (type) {
                case 0:
                    array = vm.match(aScreenData, cScreenData, 0);
                    break;
                case 1:
                    array = vm.match(aScreenData, cScreenData, 1);
                    break;
                case 2:
                    array = vm.match(aScreenData, cScreenData, 2);
                    break;
                case 3:
                    array = vm.match(aScreenData, cScreenData, 3);
                    break;
                case 4:
                    array = vm.match(aScreenData, cScreenData, 4);
                    break;
                case 5:
                    array = vm.match(aScreenData, cScreenData, 5);
                    break;
                case 6:
                    array = vm.match(aScreenData, cScreenData, 6);
                    break;
                case 7:
                    array = vm.match(aScreenData, cScreenData, 7);
                    break;
            }
            return array;
        }

        /**
         * function match task selected list and type
         * @param aScreenData
         * @param cScreenData
         * @param type
         */
        match(aScreenData: any, cScreenData: any, type: any) {
            let params = '';
            jQuery.each(aScreenData, function (i, val) {
                console.log(i + ":" + val)
                if (val.type == type) {
                    params = i;
                    return;
                }
            });
            if (params == 'c21') {
                return cScreenData.c24CurrentCodeList;
            }
            if (params == 'c31') {
                return cScreenData.c34CurrentCodeList;
            }
            if (params == 'c41') {
                return cScreenData.c44CurrentCodeList;
            }
            if (params == 'c51') {
                return cScreenData.c54CurrentCodeList;
            }
            return [];
        }

        /**
         * function map selected codes from Kha003 c screen
         * @param selectedCodes
         * @param type
         */
        mapCode(selectedCodes: any, type: any, bScreenData: any) {
            let vm = this;
            let masterInfo = bScreenData.masterNameInfo;
            let array: any = [];
            switch (type) {
                case 0:
                    selectedCodes.forEach((data: any) => {
                        array.push(masterInfo.affWorkplaceInfoList.find(x => x.workplaceCode === data))
                    });
                    break;
                case 1:
                    selectedCodes.forEach((data: any) => {
                        array.push(masterInfo.workPlaceInfoList.find(x => x.workplaceCode === data))
                    });
                    break;
                case 2:
                    selectedCodes.forEach((data: any) => {
                        array.push(masterInfo.employeeInfoList.find(x => x.employeeCode === data))
                    });
                    break;
                case 3:
                    array = this.mapTask1To5(selectedCodes, masterInfo.task1List);
                    break;
                case 4:
                    array = this.mapTask1To5(selectedCodes, masterInfo.task2List);
                    break;
                case 5:
                    array = this.mapTask1To5(selectedCodes, masterInfo.task3List);
                    break;
                case 6:
                    array = this.mapTask1To5(selectedCodes, masterInfo.task4List);
                    break;
                case 7:
                    array = this.mapTask1To5(selectedCodes, masterInfo.task5List);
                    break;
            }
            return array;
        }

        /**
         * function map task from 1 ~5 for arrange data as request params
         * @param selectedCodes
         * @param taskList
         */
        mapTask1To5(selectedCodes: any, taskList: any) {
            let array: any = [];
            selectedCodes.forEach((data: any) => {
                array.push(taskList.find(x => x.code === data))
            });
            return array;
        }

        /**
         * function for D1_4 back to kha003 A screen
         */
        backToAScreen() {
            let vm = this;
            vm.$jump('/view/kha/003/a/index.xhtml');
        }

        /**
         * 帳票設計書：取得データよりExcel編集
         */
        exportExcell() {
            let vm = this;
            vm.exportFile(API.excel);
        }

        /**
         * 帳票設計書：取得データよりCSVl編集
         */
        exportCsv() {
            let vm = this;
            vm.exportFile(API.csv);
        }

        /**
         * export file
         * @param api
         */
        exportFile(api: string) {
            let vm = this;
            let command = vm.agCommand();
            command.period = vm.preriod();
            vm.$blockui("invisible");
            nts.uk.request.exportFile("at", api, command)
                .done((successData: any) => {
                }).fail((error: any) => {
                vm.$dialog.error({messageId: error.messageId});
            }).always(() => vm.$blockui("clear"));
        }

        /**
         * function for display kha003 C screen[C画面を表示する]
         */
        displayKha003CScreen() {
            let vm = this;
            vm.$window.modal("/view/kha/003/c/index.xhtml").then(() => {

            });
        }

        /**
         * function for arrange task headers.
         * @param task
         */
        arrangeTaskHeader(task: any) {
            let vm = this;
            if (task) {
                vm.dateHeaders.push(
                    new DateHeader(null, null, task)
                )
            }
        }

        /**
         * function for arrange date range headers.
         * @param startDate
         * @param endDate
         */
        getDateRange(startDate: any, endDate: any, steps = 1) {
            let vm = this;
            let currentDate = new Date(startDate);
            while (currentDate <= new Date(endDate)) {
                let date = new Date(currentDate.toISOString());
                let headerText = (date.getMonth() + 1) + "月" + date.getDate() + "日";
                vm.dateHeaders.push(
                    new DateHeader(date.getMonth(), date.getDate(), headerText)
                )
                // Use UTC date to prevent problems with time zones and DST
                currentDate.setUTCDate(currentDate.getUTCDate() + steps);
                vm.maxDateRange++;
            }
            vm.dateHeaders.push(
                new DateHeader('', '', vm.$i18n('KHA003_98'))
            )
        }

        mounted() {
            const vm = this;
        }
    }

    class DateHeader {
        month: any;
        day: any;
        text: any

        constructor(month: any, day: any, text: any) {
            this.month = month;
            this.day = day;
            this.text = text
        }
    }

    class Content {
        value: any

        constructor(value: any) {
            this.value = value;
        }
    }
}


