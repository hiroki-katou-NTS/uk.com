module nts.uk.at.view.ksu001.jb.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkType());
        listWorkTime: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkTime());
        selectedWorkTypeCode: KnockoutObservable<string> = ko.observable('');
        selectedWorkTimeCode: KnockoutObservable<string> = ko.observable('');
        time1: KnockoutObservable<string> = ko.observable('');
        time2: KnockoutObservable<string> = ko.observable('');
        nameWorkTimeType: KnockoutComputed<any>;
        textName: KnockoutObservable<string> = ko.observable(getShared('dataForJB').text || null);
        arrTooltip: any[] = getShared('dataForJB').tooltip ? getShared('dataForJB').tooltip.match(/[^[\]]+(?=])/g) : [];
        source: KnockoutObservableArray<any> = ko.observableArray(getShared('dataForJB').data || []);
        dataSource: KnockoutObservableArray<any> = ko.observableArray([]);
        textDecision: KnockoutObservable<string> = ko.observable(getShared('dataForJB').textDecision);

        constructor() {
            let self = this;

            //get workTypeCode, workTimeCode, workTypeName, workTimeName, startTime, endTime, symbolName
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
                        startTime = c.timeNumberCnt == 1 ? nts.uk.time.parseTime(c.start, true).format() : '';
                        endTime = c.timeNumberCnt == 1 ? nts.uk.time.parseTime(c.end, true).format() : '';
                    } else {
                        workTimeName = null;
                        workTimeCode = null;
                        startTime = '';
                        endTime = '';
                    }
                }
                return {
                    workTypeCode: workTypeCode,
                    workTypeName: workTypeName,
                    workTimeCode: workTimeCode,
                    workTimeName: workTimeName,
                    startTime: startTime,
                    endTime: endTime,
                    symbolName: null
                };
            });

            for (let i = 0; i < self.arrTooltip.length; i++) {
                $($("#table-date td")[i]).html(self.arrTooltip[i]);
                self.dataSource().push({ index: i + 1, value: self.arrTooltip[i], data: self.source()[i].data });
            }

            /**
             * handle when click/ctr+click cell table
             * get workTypeName/workTimeName paste to cell
             * push data to dataSource
             */
            $("#table-date td").on('click', function(event) {
                let nameWTypeWTime: string = self.nameWorkTimeType().workTimeName ?
                    self.nameWorkTimeType().workTypeName + '/' + self.nameWorkTimeType().workTimeName : self.nameWorkTimeType().workTypeName;

                if (event.ctrlKey) {
                    $(this.parentElement.children).html(nameWTypeWTime);
                    let arrDate = _.map($(this.parentElement).prev().children(), (x) => { return +x.innerHTML });
                    _.each(arrDate, (date) => {
                        _.remove(self.dataSource(), { index: date });
                        self.dataSource().push({ index: date, value: nameWTypeWTime, data: self.nameWorkTimeType() });
                    });
                } else {
                    $(this).html(nameWTypeWTime);
                    let index = +$(this).parent().prev().children()[$(this).index()].innerHTML;
                    _.remove(self.dataSource(), { index: index });
                    self.dataSource().push({ index: index, value: nameWTypeWTime, data: self.nameWorkTimeType() });
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
            let arrTooltipClone = _.clone(arrTooltip);

            for (let i = 7; i < arrTooltipClone.length; i += 7) {
                arrTooltip.splice(i, 0, 'lb');
                i++;
            }

            let tooltip: string = arrTooltip.join('→');
            tooltip = tooltip.replace(/→lb/g, '\n');
            // sap xep cho mang lien mach
            let index = 0;
            let arrData = _.map(dataSourceOrder, (dataS) => {
                index++;
                return {
                    index: index,
                    data: dataS.data
                };
            });

            setShared("dataFromJB", {
                text: self.textName(),
                tooltip: tooltip,
                data: arrData
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