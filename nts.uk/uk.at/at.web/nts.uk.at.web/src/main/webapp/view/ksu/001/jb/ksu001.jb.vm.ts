module nts.uk.at.view.ksu001.jb.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkType());
        listWorkTime: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkTime());
        selectedWorkTypeCode: KnockoutObservable<string> = ko.observable('');
        selectedWorkTimeCode: KnockoutObservable<string> = ko.observable('');
        time1: KnockoutObservable<string> = ko.observable('12:00');
        time2: KnockoutObservable<string> = ko.observable('15:00');
        nameWorkTimeType: KnockoutComputed<any>;
        textName: KnockoutObservable<string> = ko.observable(getShared('dataForJB').text || null);
        arrTooltip: any[] = getShared('dataForJB').tooltip ? getShared('dataForJB').tooltip.match(/[^[\]]+(?=])/g) : [];
        dataSource: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this;

            //            self.nameWorkTimeType = ko.computed(() => {
            //                let workTypeName, workTimeName, nameWTT: string;
            //                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
            //                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
            //                    if (d) {
            //                        workTypeName = d.abbreviationName;
            //                    } else {
            //                        workTypeName = '';
            //                    }
            //
            //                    let siftCode: string = null;
            //                    if (self.selectedWorkTimeCode()) {
            //                        siftCode = self.selectedWorkTimeCode().slice(0, 3);
            //                    } else {
            //                        siftCode = self.selectedWorkTimeCode()
            //                    }
            //
            //                    let c = _.find(self.listWorkTime(), ['siftCd', siftCode]);
            //                    if (c) {
            //                        workTimeName = c.abName;
            //                    } else {
            //                        workTimeName = '';
            //                    }
            //                }
            //
            //                if (!!workTypeName && !!workTimeName) {
            //                    nameWTT = workTypeName + '/' + workTimeName;
            //                } else if (!workTypeName) {
            //                    nameWTT = workTimeName;
            //                } else {
            //                    nameWTT = workTypeName;
            //                }
            //                return nameWTT;
            //                 
            //get workTypeCode, workTimeCode, workTypeName, workTimeName, startTime and endTime
            self.nameWorkTimeType = ko.pureComputed(() => {
                let workTypeName, workTypeCode, workTimeName, workTimeCode: string;
                let startTime, endTime: any;
                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
                    if (d) {
                        workTypeName = d.abbreviationName;
                        workTypeCode = d.workTypeCode;
                    } else {
                        workTypeName = null;
                        workTypeCode = null;
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
                        workTimeCode = (c.siftCd == '000' ? '' : c.siftCd);
                        startTime = c.start;
                        endTime = c.end;
                    } else {
                        workTimeName = null;
                        workTimeCode = null;
                        startTime = null;
                        endTime = null;
                    }
                }
                return {
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    startTime: startTime,
                    endTime: endTime,
                    symbolName: 'symbol'
                };
            });

            for (let i = 0; i < self.arrTooltip.length; i++) {
                $($("#table-date td")[i]).html(self.arrTooltip[i]);
                self.dataSource().push({ index: i + 1, value: self.arrTooltip[i] });
                //TO-DO: set them truong obj cho dataSource
            }

            /**
             * handle when click/ctr+click cell table
             * get workTypeName/workTimeName paste to cell
             * push obj to dataSource
             */
            $("#table-date td").on('click', function(event) {
                let nameWTypeWTime: string = self.nameWorkTimeType().workTimeName ?
                    self.nameWorkTimeType().workTypeName + '/' + self.nameWorkTimeType().workTimeName : self.nameWorkTimeType().workTypeName;

                if (event.ctrlKey) {
                    $(this.parentElement.children).html(nameWTypeWTime);
                    let arrDate = _.map($(this.parentElement).prev().children(), (x) => { return +x.innerHTML });
                    _.each(arrDate, (date) => {
                        _.remove(self.dataSource(), { index: date });
                        self.dataSource().push({ index: date, value: nameWTypeWTime, obj: self.nameWorkTimeType() });
                    });
                } else {
                    $(this).html(nameWTypeWTime);
                    let index = +$(this).parent().prev().children()[$(this).index()].innerHTML;
                    _.remove(self.dataSource(), { index: index });
                    self.dataSource().push({ index: index, value: nameWTypeWTime, obj: self.nameWorkTimeType() });
                }
            });
        }

        /**
         * Clear data in table
         */
        clearData(): void {
            let self = this;
            $("#table-date td").html('');
            self.dataSource([]);
        }

        /**
         * decision
         */
        decision(): void {
            let self = this;
            if (self.dataSource().length == 0) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_510" });
                return;
            }
            // sort dataSoucre (tooltip) ASC
            let dataSourceOrder = _.orderBy(self.dataSource(), ['index'], ['asc']);
            let arrTooltip = _.map(dataSourceOrder, (data) => {
                return '[' + data.value + ']';
            });

            let tooltip: string = arrTooltip.join('→');
            setShared("dataFromJB", {
                text: self.textName(),
                tooltip: tooltip
            });
            nts.uk.ui.windows.close();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}