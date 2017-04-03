module qmm020.j.viewmodel {
    import option = nts.uk.ui.option;
    //Type of tab when call J Dialog
    //var tabIdType="";
    export class ScreenModel {
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

        constructor() {
            var self = this;
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
            //Default radio selected radio 1
            self.selectedValue = ko.observable(1);
            self.isSelected = ko.observable(true);
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
            //Tab Selected
            self.valueShareJDialog = ko.observable(nts.uk.ui.windows.getShared('valJDialog'));
            if (self.valueShareJDialog().split("~")[0] === "1") {
                nts.uk.ui.windows.getSelf().setHeight(350);
                $('#J_INP_003').hide();
                $('#J_LBL_006').parent().hide();
                $('#J_INP_002').hide();
                $('#J_caution').hide();
            } else {
                //Set height to Sub Windows
                nts.uk.ui.windows.getSelf().setHeight(450);
            }
            //
            self.selectStartYm = ko.observable('');
            self.txtCopyHistory = "";
            self.start();
        }
        

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
                var maxDate :number = Number(self.valueShareJDialog().split("~")[1]);
                self.selectStartYm(nts.uk.time.formatYearMonth(maxDate));
                //Hien thi lable RadioBoxs
                self.txtCopyHistory="最新の履歴（" + nts.uk.time.formatYearMonth(maxDate) + "）から引き継ぐ";
            dfd.resolve();
            // Return.
            return dfd.promise();
        }

        //Event when click to Setting Button
        createHistoryDocument(): any {
            
            var self = this;
            if (self.valueShareJDialog().split('~')[0] === "1") {
                var radioCheckVal = self.selectedValue();
                var inputYm = $('#J_INP_001').val();
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
            //check YM
            //           var selectYm = self.selectStartYm();
            //           inputYm = inputYm.replace('/','');
            //           if(+inputYm < +selectYm || + inputYm == +selectYm){
            //              alert('履歴の期間が正しくありません。');
            //               return false;
            //           }
            //           else{
            //               nts.uk.ui.windows.setShared('returnJDialog',self.selectStartYm());
            //               nts.uk.ui.windows.close();  
            //           }
        }
        //Close Dialog
        closeDialog(): any {
            //nts.uk.ui.windows.setShared('returnJDialog','');
            nts.uk.ui.windows.close();
        }
    }
}