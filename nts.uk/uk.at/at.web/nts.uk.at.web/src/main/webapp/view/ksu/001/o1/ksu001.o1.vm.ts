module ksu001.o1.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<any>;
        listWorkTime: KnockoutObservableArray<any>;
        selectedWorkTypeCode: KnockoutObservable<string>;
        selectedWorkTimeCode: KnockoutObservable<string>;
        time1: KnockoutObservable<string>;
        time2: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        nameWorkTimeType: KnockoutObservable<ExCell>;
        columnsWorkTime: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            let self = this;
            self.listWorkType = ko.observableArray(getShare("listWorkType"));
            self.listWorkTime = ko.observableArray(getShare("listWorkTime"));
            self.roundingRules = ko.observableArray([
                { code: '1', name: nts.uk.resource.getText("KSU001_71") },
                { code: '2', name: nts.uk.resource.getText("KSU001_72") }
            ]);

            self.columnsWorkTime = ko.observableArray([
                { headerText: nts.uk.resource.getText("KSU001_1402"), key: 'siftCd', width: 70 },
                { headerText: nts.uk.resource.getText("KSU001_1403"), key: 'symbol', width: 70 },
                { headerText: nts.uk.resource.getText("KSU001_1404"), key: 'name', width: 110 },
                { headerText: nts.uk.resource.getText("KSU001_1406"), key: 'timeZone1', width: 160 },
                { headerText: nts.uk.resource.getText("KSU001_1407"), key: 'timeZone2', width: 160 },
                { headerText: nts.uk.resource.getText("KSU001_1408"), key: 'note', width: 160 },
                { headerText: 'data-id', key: 'codeName', width: 160, hidden: true }
            ]);

            self.selectedRuleCode = ko.observable(1);
            self.selectedWorkTypeCode = ko.observable('');
            self.selectedWorkTimeCode = ko.observable('');
            self.time1 = ko.observable('12:00');
            self.time2 = ko.observable('15:00');

            //get name of workType and workTime
            self.nameWorkTimeType = ko.pureComputed(() => {
                let workTypeName, workTypeCode, workTimeName, workTimeCode: string;
                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
                    if (d) {
                        workTypeName = d.abbreviationName;
                        workTypeCode = d.workTypeCode;
                    } else {
                        workTypeName = '';
                        workTypeCode = '';
                    }

                    let siftCode: string = null;
                    if (self.selectedWorkTimeCode()) {
                        siftCode = self.selectedWorkTimeCode().slice(0, 3);
                    } else {
                        siftCode = self.selectedWorkTimeCode()
                    }

                    let c = _.find(self.listWorkTime(), ['siftCd', siftCode]);
                    if (c) {
                        workTimeName = c.abName;
                        workTimeCode = c.siftCd;
                    } else {
                        workTimeName = '';
                        workTimeCode = '';
                    }
                }
                return new ExCell({
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    symbol: null,
                    startTime: null,
                    endTime: null
                });
            });

            self.nameWorkTimeType.subscribe(function(value) {
                //Paste data into cell (set-sticker-single)
                $("#extable").exTable("stickData", value);
            });

            $("#stick-undo").click(function() {
                $("#extable").exTable("stickUndo");
            });
        }
                
        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }

    interface IExCell {
        workTypeCode: string,
        workTypeName: string,
        workTimeCode: string,
        workTimeName: string,
        symbol: string,
        startTime: any,
        endTime: any
    }

    class ExCell {
        workTypeCode: string;
        workTypeName: string;
        workTimeCode: string;
        workTimeName: string;
        symbol: string;
        startTime: any;
        endTime: any;
        constructor(params: IExCell) {
            this.workTypeCode = params.workTypeCode;
            this.workTypeName = params.workTypeName;
            this.workTimeCode = params.workTimeCode;
            this.workTimeName = params.workTimeName;
            this.symbol = params.symbol;
            this.startTime = params.startTime;
            this.endTime = params.endTime;
        }
    }
}