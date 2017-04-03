module cmm009.d.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        
        startDate: KnockoutObservable<string>;
        startDateFromA: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        valueSel001: KnockoutObservable<string>;
        startYmHis: KnockoutObservable<number>;
        timeEditorOption: KnockoutObservable<any>;
        //---radio        
        isRadioCheck: KnockoutObservable<number>;
        itemsRadio: KnockoutObservableArray<any>;
        isEnable: KnockoutObservable<boolean>;
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.isEnable = ko.observable(true);
            self.startDate = ko.observable(null);
            self.startDateFromA = ko.observable(null);
            self.valueSel001 = ko.observable("");
            self.startYmHis = ko.observable(null);
            self.endDate = ko.observable(null);
            var data = nts.uk.ui.windows.getShared('itemHist');
            self.startDate(data.startDate);
            self.startDateFromA(data.startDate);
            self.endDate(data.endDate);
            console.log(data);
            debugger;
            //---radio
            self.itemsRadio = ko.observableArray([
                { value: 1, text: ko.observable('履歴を削除する') },
                { value: 2, text: ko.observable('履歴を修正する') }
            ]);
            self.isRadioCheck = ko.observable(2);
            let index = data.index;
            if (index != "0") {
                self.isEnable(false);
            }
        }

        createHistory(): any {
            var self = this;
            var inputYm = $('#INP_001').val();
            //check YM
            if (!nts.uk.time.parseYearMonthDate(inputYm).success) {
                alert(nts.uk.time.parseYearMonthDate(inputYm).msg);
                return false;
            }
            var selectYm = self.startDateFromA();
            var inputYm2 = inputYm.replace('/', '');
            inputYm2 = inputYm2.replace('/', '');
            selectYm = selectYm.replace('/', '');
            selectYm = selectYm.replace('/', '');
            console.log(inputYm2);
            if (+inputYm2 < +selectYm
                || +inputYm2 == +selectYm) {
                alert('履歴の期間が正しくありません。');
                return false;
            }
            else {
                var self = this;
                var isRadio = self.isRadioCheck()+"";
                nts.uk.ui.windows.setShared('newstartDate', self.startDate());
                nts.uk.ui.windows.setShared('isradio', isRadio);
                nts.uk.ui.windows.close();
            }
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            dfd.resolve();
            // Return.
            return dfd.promise();
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

    }
}