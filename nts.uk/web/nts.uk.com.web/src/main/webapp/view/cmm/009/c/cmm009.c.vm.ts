module cmm009.c.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        selectStartYm: KnockoutObservable<string>;
        C_INP_002: KnockoutObservable<string>;
        valueSel001: KnockoutObservable<string>;
        startYmHis: KnockoutObservable<number>;
        //timeEditorOption: KnockoutObservable<any>;

        //---radio        
        isRadioCheck: KnockoutObservable<number>;
        itemsRadio: KnockoutObservableArray<any>;
        enable: KnockoutObservable<boolean>;
        // -- check data ban đầu
        data: any;
        object: KnockoutObservable<viewmodel.Object>;

        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.selectStartYm = ko.observable(null);
            self.C_INP_002 = ko.observable(null);
            self.valueSel001 = ko.observable("");
            self.startYmHis = ko.observable(null);
            self.object = ko.observable(null);
            //self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "date"}));
            self.data = nts.uk.ui.windows.getShared('datanull');
            let startDateofHis = nts.uk.ui.windows.getShared('startDateOfHis');

            //---radio
            if (self.data == "datanull") {
                self.isRadioCheck = ko.observable(2);
                self.enable = ko.observable(false);
            } else {
                self.enable = ko.observable(true);
                let startYmHis = startDateofHis;
                startDateofHis = startDateofHis.replace('-', '/');
                startDateofHis = startDateofHis.replace('-', '/');
                startYmHis = startYmHis.replace('-', '');
                startYmHis = startYmHis.replace('-', '');
                self.selectStartYm(startDateofHis);
                self.startYmHis(startYmHis);
                self.isRadioCheck = ko.observable(1);
                console.log("self.startYmHis" + self.startYmHis())
            }
            
            self.itemsRadio = ko.observableArray([
                { value: 1, text: ko.observable('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ') },
                { value: 2, text: ko.observable('初めから作成する') }
            ]);
        }

        createHistory(): any {
            var self = this;
            var inputYm = $('#INP_001').val();
            //check YM
            if (!nts.uk.time.parseYearMonthDate(inputYm).success) {
                alert(nts.uk.time.parseYearMonthDate(inputYm).msg);
                return false;
            }
            var selectYm = self.startYmHis();
            var inputYm2 = inputYm.replace('/', '');
            inputYm2 = inputYm2.replace('/', '');
            console.log(inputYm2);
            if (+inputYm2 < +selectYm
                || +inputYm2 == +selectYm) {
                alert('履歴の期間が正しくありません。'); 
                return false;
            }
            else {
                self.createData();
                nts.uk.ui.windows.close();
            }
        }

        createData(): any {
            var self = this;
            var startYearMonth = $('#INP_001').val();
            var checked = null;
            if (self.isRadioCheck() === 1 && self.enable() === true) {
                checked = true;
            } else {
                checked = false;
            }
            var memo = self.C_INP_002();
            var obj = new Object(startYearMonth, checked, memo);
            self.object(obj);
            nts.uk.ui.windows.setShared('itemHistory', self.object());
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
    export class Object {
        startYearMonth: string;
        checked: boolean;
        memo: string;

        constructor(startYearMonth: string, checked: boolean, memo: string) {
            this.startYearMonth = startYearMonth;
            this.memo = memo;
            this.checked = checked;

        }
    }
}
/*

*/