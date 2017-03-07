module nts.uk.pr.view.qmm016.a {
    export module viewmodel {
        export class ScreenModel {
            
            // UI
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            wageTableList: any;
            monthRange: KnockoutComputed<string>;

            // Process
            selectedCode: KnockoutObservable<string>;
            selectedTab: KnockoutObservable<string>;

            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
            selectedDimensionType: KnockoutObservable<number>;
            selectedDimensionName: KnockoutComputed<string>;

            generalTableTypes: KnockoutObservableArray<any>;
            specialTableTypes: KnockoutObservableArray<any>;

            constructor() {
                var self = this;

                // UI init
                self.wageTableList = ko.observableArray([new WageTableItem('0001', 'あああああ', []),
                    new WageTableItem('0002', '明細書 B', []),
                    new WageTableItem('0003', '資格手当 テーブル', [new WageTableHistoryItem('1', '2015/04', '2016/03')])]);

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '基本情報', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '賃金テーブルの情報', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);

                self.generalTableTypes = ko.observableArray([
                    { code: '0', name: '一次元' },
                    { code: '1', name: '二次元' },
                    { code: '2', name: '三次元' }
                ]);

                self.specialTableTypes = ko.observableArray([
                    { code: '3', name: '資格' },
                    { code: '4', name: '精皆勤手当' }
                ]);

                // Process
                self.selectedCode = ko.observable('0001');
                self.selectedTab = ko.observable('tab-1');

                self.code = ko.observable('');
                self.name = ko.observable('');
                self.startMonth = ko.observable('');
                self.endMonth = ko.observable('9999/12');
                self.monthRange = ko.computed(function() { return self.startMonth() + " ~ " + self.endMonth(); });
                self.selectedDimensionName = ko.computed(function() { return '一次元' });
                self.memo = ko.observable('');
                self.selectedDimensionType = ko.observable(0);
            }

            goToB() {
                nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { dialogClass: 'no-close', height: 380, width: 400 }).setTitle('å±¥æ­´ã�®è¿½åŠ ');
            }
            
            //open dialog edit HistoryAccidentInsurance => show view model xhtml (action event edit)
            private openSetupEntitlementGroup() {
                // Set parent value
                //  selectionHistoryUnemployeeInsuranceRate
                var self = this;
                //var historyId = self.selectionHistoryAccidentInsuranceRate();
                //  nts.uk.ui.windows.setShared("historyId", historyId);
                // nts.uk.ui.windows.setShared("type", TypeHistory.HistoryAccident);
                // nts.uk.ui.windows.setShared("historyStart", self.historyAccidentInsuranceRateStart());
                // nts.uk.ui.windows.setShared("historyEnd", self.historyAccidentInsuranceRateEnd());
                //self.typeActionAccidentInsurance(TypeActionInsuranceRate.update);
                nts.uk.ui.windows.sub.modal("/view/qmm/016/l/index.xhtml", { height: 800, width: 1319, title: "資格グループの設定" }).onClosed(() => {
                    //OnClose => call
                    //var updateHistoryAccidentInsuranceDto = nts.uk.ui.windows.getShared("updateHistoryAccidentInsuranceDto");
                    /*if (updateHistoryAccidentInsuranceDto != null && updateHistoryAccidentInsuranceDto != undefined) {
                        self.historyAccidentInsuranceRateStart(updateHistoryAccidentInsuranceDto.startMonthRage);
                        self.historyAccidentInsuranceRateEnd(updateHistoryAccidentInsuranceDto.endMonthRage);
                    }*/
                });
            }
        }


        export class WageTableItem {
            
            code: string;
            name: string;
            nodeText: string;
            histories: Array<WageTableHistoryItem>;

            constructor(code: string, name: string, histories: Array<WageTableHistoryItem>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.histories = histories;
            }
        }

        export class WageTableHistoryItem {
            
            code: string;
            startMonth: string;
            endMonth: string;
            nodeText: string;

            constructor(code: string, startMonth: string, endMonth: string) {
                var self = this;
                self.code = code;
                self.startMonth = startMonth;
                self.endMonth = endMonth;
                self.nodeText = self.startMonth + ' ~ ' + self.endMonth;
            }
        }


    }
}