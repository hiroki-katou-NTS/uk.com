var qmm005;
(function (qmm005) {
    var c;
    (function (c) {
        var ViewModel = (function () {
            function ViewModel() {
                this.index = ko.observable(0);
                this.inp001 = ko.observable('');
                this.sel001 = ko.observable(1);
                this.sel002 = ko.observable(0);
                this.sel003 = ko.observable(1);
                this.sel004 = ko.observable(0);
                this.sel005 = ko.observable(1);
                this.sel006 = ko.observable(0);
                this.sel007 = ko.observable(0);
                this.sel008 = ko.observable(0);
                this.sel009 = ko.observable(0);
                this.sel010 = ko.observable(1);
                this.sel011 = ko.observable(1);
                this.sel012 = ko.observable(1);
                this.sel013 = ko.observable(1);
                this.sel014 = ko.observable(1);
                this.sel015 = ko.observable(1);
                this.sel001Data = ko.observableArray([]);
                this.sel002Data = ko.observableArray([]);
                this.sel003Data = ko.observableArray([]);
                this.sel004Data = ko.observableArray([]);
                this.sel005Data = ko.observableArray([]);
                this.sel006Data = ko.observableArray([]);
                this.sel007Data = ko.observableArray([]);
                this.sel008Data = ko.observableArray([]);
                this.sel009Data = ko.observableArray([]);
                this.sel010Data = ko.observableArray([]);
                this.sel011Data = ko.observableArray([]);
                this.sel012Data = ko.observableArray([]);
                this.sel013Data = ko.observableArray([]);
                this.sel014Data = ko.observableArray([]);
                this.sel015Data = ko.observableArray([]);
                this.dirty = {
                    inp001: new nts.uk.ui.DirtyChecker(this.inp001),
                    sel001: new nts.uk.ui.DirtyChecker(this.sel001),
                    sel002: new nts.uk.ui.DirtyChecker(this.sel002),
                    sel003: new nts.uk.ui.DirtyChecker(this.sel003),
                    sel004: new nts.uk.ui.DirtyChecker(this.sel004),
                    sel005: new nts.uk.ui.DirtyChecker(this.sel005),
                    sel006: new nts.uk.ui.DirtyChecker(this.sel006),
                    sel007: new nts.uk.ui.DirtyChecker(this.sel007),
                    sel008: new nts.uk.ui.DirtyChecker(this.sel008),
                    sel009: new nts.uk.ui.DirtyChecker(this.sel009),
                    sel010: new nts.uk.ui.DirtyChecker(this.sel010),
                    sel011: new nts.uk.ui.DirtyChecker(this.sel011),
                    sel012: new nts.uk.ui.DirtyChecker(this.sel012),
                    sel013: new nts.uk.ui.DirtyChecker(this.sel013),
                    sel014: new nts.uk.ui.DirtyChecker(this.sel014),
                    sel015: new nts.uk.ui.DirtyChecker(this.sel015),
                    isDirty: function () {
                        return this.inp001.isDirty()
                            || this.sel001.isDirty()
                            || this.sel002.isDirty()
                            || this.sel003.isDirty()
                            || this.sel004.isDirty()
                            || this.sel005.isDirty()
                            || this.sel006.isDirty()
                            || this.sel007.isDirty()
                            || this.sel008.isDirty()
                            || this.sel009.isDirty()
                            || this.sel010.isDirty()
                            || this.sel011.isDirty()
                            || this.sel012.isDirty()
                            || this.sel013.isDirty()
                            || this.sel014.isDirty()
                            || this.sel015.isDirty();
                    },
                    reset: function () {
                        this.inp001.reset();
                        this.sel001.reset();
                        this.sel002.reset();
                        this.sel003.reset();
                        this.sel004.reset();
                        this.sel005.reset();
                        this.sel006.reset();
                        this.sel007.reset();
                        this.sel008.reset();
                        this.sel009.reset();
                        this.sel010.reset();
                        this.sel011.reset();
                        this.sel012.reset();
                        this.sel013.reset();
                        this.sel014.reset();
                        this.sel015.reset();
                    }
                };
                var self = this;
                // processingNo
                self.index(nts.uk.ui.windows.getShared('dataRow').index());
                // define all data
                var C_SEL_001_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_001_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_001_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var C_SEL_002_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' })
                ];
                var C_SEL_003_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_003_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_003_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var C_SEL_004_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' })
                ];
                var C_SEL_005_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_005_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_005_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var C_SEL_006_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                ];
                var C_SEL_007_DATA = [
                    new qmm005.common.SelectItem({ index: -2, label: '前々月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌月' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々月' })
                ];
                var C_SEL_008_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前年' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当年' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌年' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々年' })
                ];
                var C_SEL_009_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当月' })
                ];
                for (var i = 1; i <= 12; i++) {
                    C_SEL_009_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var C_SEL_010_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_010_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_010_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var C_SEL_011_DATA = [];
                for (var i = 1; i <= 12; i++) {
                    C_SEL_011_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var C_SEL_012_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_012_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_012_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var C_SEL_013_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前年' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当年' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌年' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々年' }),
                ];
                var C_SEL_014_DATA = [];
                for (var i = 1; i <= 12; i++) {
                    C_SEL_014_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var C_SEL_015_DATA = [];
                for (var i = 1; i < 31; i++) {
                    C_SEL_015_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                C_SEL_015_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                // observable all datas
                self.sel001Data(C_SEL_001_DATA);
                self.sel002Data(C_SEL_002_DATA);
                self.sel003Data(C_SEL_003_DATA);
                self.sel004Data(C_SEL_004_DATA);
                self.sel005Data(C_SEL_005_DATA);
                self.sel006Data(C_SEL_006_DATA);
                self.sel007Data(C_SEL_007_DATA);
                self.sel008Data(C_SEL_008_DATA);
                self.sel009Data(C_SEL_009_DATA);
                self.sel010Data(C_SEL_010_DATA);
                self.sel011Data(C_SEL_011_DATA);
                self.sel012Data(C_SEL_012_DATA);
                self.sel013Data(C_SEL_013_DATA);
                self.sel014Data(C_SEL_014_DATA);
                self.sel015Data(C_SEL_015_DATA);
                // reset all dirty data
                self.dirty.reset();
            }
            ViewModel.prototype.toggleView = function () {
                $('.form-extent').toggleClass('hidden');
                if (!$('.form-extent').hasClass('hidden')) {
                    nts.uk.ui.windows.getSelf().setHeight(window.top.innerHeight < 801 ? 610 : 650);
                    $('#contents-area').css('padding-bottom', '0');
                }
                else {
                    nts.uk.ui.windows.getSelf().setHeight(350);
                    $('#contents-area').css('padding-bottom', '');
                }
            };
            ViewModel.prototype.validate = function ($root) {
                var self = this;
                return $root.errors.isEmpty() && self.inp001().trim() != '';
            };
            ViewModel.prototype.saveData = function () {
                var self = this, date = new Date(), stdYear = date.getFullYear(), stdMonth = date.getMonth(), stdDate = date.getDate();
                // Chưa thấy đề cập tới các trường hợp ngày > số ngày trong tháng.
                var data = {
                    processingNo: self.index(),
                    processingName: self.inp001(),
                    dispSet: 0,
                    currentProcessingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, stdDate <= self.sel001() ? stdMonth + 1 : stdMonth, 1), 'yyyyMM')),
                    bonusAtr: 0,
                    bcurrentProcessingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, stdDate <= self.sel001() ? stdMonth + 1 : stdMonth, 1), 'yyyyMM')),
                    payStdDay: self.sel001(),
                    pickupStdMonAtr: self.sel002(),
                    pickupStdDay: self.sel003(),
                    accountDueMonAtr: self.sel004(),
                    accountDueDay: self.sel005(),
                    payslipPrintMonth: self.sel006(),
                    socialInsuLevyMonAtr: self.sel007(),
                    socialInsStdYearAtr: self.sel008(),
                    socialInsStdMon: self.sel009(),
                    socialInsStdDay: self.sel010(),
                    empInsStdMon: self.sel011(),
                    empInsStdDay: self.sel012(),
                    incometaxStdYearAtr: self.sel013(),
                    incometaxStdMon: self.sel014(),
                    incometaxStdDay: self.sel015(),
                    payDays: []
                };
                /// Initial data for salary and bonus for 12 months in year
                for (var month = 0; month < 12; month++) {
                    // Salary
                    data.payDays.push({
                        processingNo: data.processingNo,
                        payBonusAtr: 0,
                        processingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, month, 1), 'yyyyMM')),
                        sparePayAtr: 0,
                        payDate: new Date(stdYear, month, data.payStdDay),
                        stdDate: new Date(stdYear, month + data.pickupStdMonAtr, data.pickupStdDay),
                        accountingClosing: new Date(stdYear, month + data.accountDueMonAtr, data.accountDueDay),
                        socialInsLevyMon: parseInt(nts.uk.time.formatDate(new Date(stdYear, month + data.socialInsuLevyMonAtr, 1), 'yyyyMM')),
                        socialInsStdDate: new Date(stdYear + data.socialInsStdYearAtr, data.socialInsStdMon < 1 ? month + data.socialInsStdMon : data.socialInsStdMon - 1, data.socialInsStdDay),
                        incomeTaxStdDate: new Date(stdYear + data.incometaxStdYearAtr, data.incometaxStdMon - 1, data.incometaxStdDay),
                        neededWorkDay: 0,
                        empInsStdDate: new Date(stdYear, data.empInsStdMon - 1, data.empInsStdDay),
                        stmtOutputMon: parseInt(nts.uk.time.formatDate(new Date(stdYear, month + data.payslipPrintMonth, 1), 'yyyyMM'))
                    });
                }
                c.services.insertData(data).done(function () {
                    self.dirty.reset();
                    self.closeDialog();
                });
            };
            ViewModel.prototype.closeDialog = function () {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。よろしいですか。?")
                        .ifYes(function () {
                        nts.uk.ui.windows.close();
                    });
                }
                else {
                    nts.uk.ui.windows.close();
                }
            };
            return ViewModel;
        }());
        c.ViewModel = ViewModel;
    })(c = qmm005.c || (qmm005.c = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.c.viewmodel.js.map