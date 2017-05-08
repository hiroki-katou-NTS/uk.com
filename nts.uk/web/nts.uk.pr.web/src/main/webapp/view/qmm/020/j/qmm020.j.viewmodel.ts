module qmm020.j.viewmodel {
    import option = nts.uk.ui.option;

    export class ScreenModel {
        displayMode: KnockoutObservable<number> = ko.observable(1);

        timeEditorOption: KnockoutObservable<any>;
        //Shared value from J Dialog
        valueShareJDialog: KnockoutObservable<string>;
        startYm: string;
        txtCopyHistory: string;
        selectStartYm: KnockoutObservable<string>;
        //value check of radio 
        selectedValue: KnockoutObservable<any>;
        //focus input
        isSelected: KnockoutObservable<boolean>;
        maxYm: KnockoutObservable<any>;
        input001: KnockoutObservable<string>;

        constructor() {
            var self = this;
            // display mode
            self.displayMode(nts.uk.ui.windows.getShared('J_MODE') || 1);

            // resize window
            self.displayMode.subscribe((v) => {
                nts.uk.ui.windows.getSelf().setWidth(490);
                if (v == 2) {
                    nts.uk.ui.windows.getSelf().setHeight(420);
                } else {
                    nts.uk.ui.windows.getSelf().setHeight(300);
                }
            });

            // trigger resize window
            self.displayMode.valueHasMutated();

            self.selectedValue = ko.observable(2);
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
            //Default radio selected radio 1
            self.selectedValue = ko.observable(1);
            self.isSelected = ko.observable(true);
            self.maxYm = ko.observable('abc');
            self.input001 = ko.observable(undefined);
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
            //Tab Selected
            self.valueShareJDialog = ko.observable(nts.uk.ui.windows.getShared('valJDialog'));

            self.selectStartYm = ko.observable('');
            self.txtCopyHistory = "";
            self.start();
        }


        start() {
            var self = this,
                maxDate: number = Number(self.valueShareJDialog().split("~")[1]);

            self.selectStartYm(nts.uk.time.formatYearMonth(maxDate));

            self.txtCopyHistory = "最新の履歴（" + nts.uk.time.formatYearMonth(maxDate) + "）から引き継ぐ";
            self.maxYm = nts.uk.time.formatYearMonth(maxDate);
        }

        //Event when click to Setting Button
        createHistoryDocument(): any {

            var self = this;
            if (self.selectStartYm() > nts.uk.time.formatYearMonth($('#J_INP_001').val())) {
                $('#J_INP_001').ntsError('set', Error.ER023);
            }

            if (ko.mapping.toJS(self.valueShareJDialog()).split('~')[0] === "1") {
                var radioCheckVal = self.selectedValue();
                var inputYm = $('#J_INP_001').val();
                self.input001(inputYm);
                console.log('aaa');
                if (!nts.uk.time.parseYearMonth(inputYm).success) {
                    alert(nts.uk.time.parseYearMonth(inputYm).msg);
                    return false;
                }
                //Set value share to parent Dialog
                if (radioCheckVal === 1) {
                    nts.uk.ui.windows.setShared('returnJDialog', "1~" + inputYm);
                    nts.uk.ui.windows.close();
                } else {
                    nts.uk.ui.windows.setShared('returnJDialog', "2~" + inputYm);
                    nts.uk.ui.windows.close();
                }
            }
            //Type of History Screen is 2 
            if (self.valueShareJDialog().split('~')[0] === "2") {

            }
            //Type of History Screen is 3 
            if (self.valueShareJDialog().split('~')[0] === "3") {

            }
        }

        closeDialog() { nts.uk.ui.windows.close(); }
    }

    enum Error {
        ER023 = <any>"履歴の期間が重複しています。"
    }
}