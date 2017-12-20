module nts.uk.at.view.kdw003.c.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        gridListColumns: KnockoutObservableArray<any>;
        dailyPerfFmtList: KnockoutObservableArray<any>;
        selectedPerfFmtCodeList: KnockoutObservableArray<string>;
        selectedPerfFmtCode: KnockoutObservable<string>;
        selectedPerfFmt: KnockoutObservable<any>;

        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW003_2"), key: 'dailyPerformanceFormatCode', width: 45 },
                { headerText: nts.uk.resource.getText("KDW003_3"), key: 'dailyPerformanceFormatName', width: 280 }
            ]);
            self.dailyPerfFmtList = ko.observableArray([]);
            self.selectedPerfFmtCodeList = ko.observableArray(<Array<string>>nts.uk.ui.windows.getShared('selectedPerfFmtCodeList'));
            self.selectedPerfFmt = ko.observable(ko.mapping.fromJS(new DailyPerformanceFormat()));
            self.selectedPerfFmtCode = ko.observable(null);
            self.selectedPerfFmtCode.subscribe((code) => {
                if (code) {
                    let foundItem = _.find(self.dailyPerfFmtList(), (item: DailyPerformanceFormat) => {
                        return item.dailyPerformanceFormatCode == code;
                    });
                    if (foundItem) {
                        self.changeSelectedPerfFmtCode(foundItem);
                    }
                }
            });
            
            // TODO - remove when commit
           // self.selectedPerfFmtCodeList = new Array('001', '002', '009');
        }
        
        changeSelectedPerfFmtCode(foundItem) {
            let self = this;
            self.selectedPerfFmt().companyId(foundItem.companyId);
            self.selectedPerfFmt().dailyPerformanceFormatCode(foundItem.dailyPerformanceFormatCode);
            self.selectedPerfFmt().dailyPerformanceFormatName(foundItem.dailyPerformanceFormatName);
        }
        
        /**
         * 起動する
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getFormatList().done((lstData) => {
                let sortedData = _.orderBy(lstData, ['dailyPerformanceFormatCode'], ['asc']);
                self.dailyPerfFmtList(sortedData);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * 選択する
         */
        select() {
            let self = this;

            // set return value
            setShared('dailyPerfFmtList', self.selectedPerfFmt());

            // close dialog.
            windows.close();
        }
        
        /**
         * キャンセル
         */
        closeDialog(): void {
            windows.close();
        }
    }

    export class DailyPerformanceFormat {
        /* 会社ID */
        companyId: string;
        /* コード */
        dailyPerformanceFormatCode: string;
        /* 名称 */
        dailyPerformanceFormatName: string;

        constructor() {
            this.companyId = '';
            this.dailyPerformanceFormatCode = '';
            this.dailyPerformanceFormatName = '';
        }
    }
}