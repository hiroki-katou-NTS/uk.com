var cmm009;
(function (cmm009) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            class ScreenModel {
                /**
                 * Init screen model.
                 */
                constructor() {
                    var self = this;
                    self.C_INP_002 = ko.observable(null);
                    self.valueSel001 = ko.observable("");
                    self.startYmHis = ko.observable(null);
                    self.object = ko.observable(null);
                    self.selectStartYm = ko.observable("1900");
                    self.yearmonthdayeditor = {
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TimeEditorOption({
                            inputFormat: 'date'
                        })),
                    };
                    self.data = nts.uk.ui.windows.getShared('datanull');
                    var startDateofHisFromScreena = nts.uk.ui.windows.getShared('startDateOfHis');
                    startDateofHisFromScreena = new Date(startDateofHisFromScreena);
                    var year = startDateofHisFromScreena.getFullYear();
                    var month = startDateofHisFromScreena.getMonth() + 1;
                    if (month < 10)
                        month = "0" + month;
                    var day = startDateofHisFromScreena.getDate();
                    if (day < 10)
                        day = "0" + day;
                    startDateofHisFromScreenatoString = year + month + day;
                    //---radio
                    if (self.data == "datanull") {
                        self.isRadioCheck = ko.observable(2);
                        self.enable = ko.observable(false);
                    }
                    else {
                        self.enable = ko.observable(true);
                        let _st = nts.uk.ui.windows.getShared('startDateOfHis');
                        self.selectStartYm(_st);
                        self.isRadioCheck = ko.observable(1);
                    }
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: ko.observable('最新の履歴（' + self.selectStartYm() + '）から引き継ぐ') },
                        { value: 2, text: ko.observable('初めから作成する') }
                    ]);
                }
                createHistory() {
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
                    if (+inputYm2 < +startDateofHisFromScreenatoString
                        || +inputYm2 == +startDateofHisFromScreenatoString) {
                        alert('履歴の期間が正しくありません。');
                        return false;
                    }
                    else {
                        self.createData();
                        nts.uk.ui.windows.close();
                    }
                }
                createData() {
                    var self = this;
                    var startYearMonthDay = $('#INP_001').val();
                    var checked = null;
                    if (self.isRadioCheck() === 1 && self.enable() === true) {
                        checked = true;
                    }
                    else {
                        checked = false;
                    }
                    var memo = self.C_INP_002();
                    var obj = new Object(startYearMonthDay, checked, memo);
                    self.object(obj);
                    nts.uk.ui.windows.setShared('itemHistory', self.object());
                }
                start() {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                }
                closeDialog() {
                    nts.uk.ui.windows.close();
                }
            }
            viewmodel.ScreenModel = ScreenModel;
            class Object {
                constructor(startYearMonth, checked, memo) {
                    this.startYearMonth = startYearMonth;
                    this.memo = memo;
                    this.checked = checked;
                }
            }
            viewmodel.Object = Object;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm009.c || (cmm009.c = {}));
})(cmm009 || (cmm009 = {}));
/*

*/ 
