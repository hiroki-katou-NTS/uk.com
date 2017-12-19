module nts.uk.at.view.ksu001.jb.viewmodel {

    export class ScreenModel {
        listWorkType: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkType());
        listWorkTime: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkTime());
        selectedWorkTypeCode: KnockoutObservable<string> = ko.observable('');
        selectedWorkTimeCode: KnockoutObservable<string> = ko.observable('');
        time1: KnockoutObservable<string> = ko.observable('12:00');
        time2: KnockoutObservable<string> = ko.observable('15:00');
        nameWorkTimeType: KnockoutComputed<string>;

        constructor() {
            let self = this;

            self.nameWorkTimeType = ko.computed(() => {
                let workTypeName, workTimeName, nameWTT: string;
                if (self.listWorkType().length > 0 || self.listWorkTime().length > 0) {
                    let d = _.find(self.listWorkType(), ['workTypeCode', self.selectedWorkTypeCode()]);
                    if (d) {
                        workTypeName = d.abbreviationName;
                    } else {
                        workTypeName = '';
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
                    } else {
                        workTimeName = '';
                    }
                }

                if (!!workTypeName && !!workTimeName) {
                    nameWTT = workTypeName + '/' + workTimeName;
                } else if (!workTypeName) {
                    nameWTT = workTimeName;
                } else {
                    nameWTT = workTypeName;
                }
                return nameWTT;
            });

            $("#table-date td").on('click', function(event) {
                if (event.ctrlKey) {
                    $(this.parentElement.children).html(self.nameWorkTimeType());
                } else {
                    $(this).html(self.nameWorkTimeType());
                }
            });
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Clear data in table
         */
        clearData(): void {
            $("#table-date td").html('');
        }
    }
}