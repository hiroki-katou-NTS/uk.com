var qmm005;
(function (qmm005) {
    var c;
    (function (c) {
        var ViewModel = (function () {
            function ViewModel() {
                var self = this;
                self.index = ko.observable(nts.uk.ui.windows.getShared('dataRow').index());
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
                self.inp001 = ko.observable('');
                self.sel001 = ko.observable(1);
                self.sel002 = ko.observable(0);
                self.sel003 = ko.observable(1);
                self.sel004 = ko.observable(0);
                self.sel005 = ko.observable(1);
                self.sel006 = ko.observable(0);
                self.sel007 = ko.observable(0);
                self.sel008 = ko.observable(0);
                self.sel009 = ko.observable(0);
                self.sel010 = ko.observable(1);
                self.sel011 = ko.observable(1);
                self.sel012 = ko.observable(1);
                self.sel013 = ko.observable(1);
                self.sel014 = ko.observable(1);
                self.sel015 = ko.observable(1);
                self.sel001Data = ko.observableArray(C_SEL_001_DATA);
                self.sel002Data = ko.observableArray(C_SEL_002_DATA);
                self.sel003Data = ko.observableArray(C_SEL_003_DATA);
                self.sel004Data = ko.observableArray(C_SEL_004_DATA);
                self.sel005Data = ko.observableArray(C_SEL_005_DATA);
                self.sel006Data = ko.observableArray(C_SEL_006_DATA);
                self.sel007Data = ko.observableArray(C_SEL_007_DATA);
                self.sel008Data = ko.observableArray(C_SEL_008_DATA);
                self.sel009Data = ko.observableArray(C_SEL_009_DATA);
                self.sel010Data = ko.observableArray(C_SEL_010_DATA);
                self.sel011Data = ko.observableArray(C_SEL_011_DATA);
                self.sel012Data = ko.observableArray(C_SEL_012_DATA);
                self.sel013Data = ko.observableArray(C_SEL_013_DATA);
                self.sel014Data = ko.observableArray(C_SEL_014_DATA);
                self.sel015Data = ko.observableArray(C_SEL_015_DATA);
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
                var data = {
                    processingNo: self.index(),
                    processingName: self.inp001(),
                    dispSet: 0,
                    currentProcessingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, stdDate <= self.sel001() ? stdMonth + 1 : stdMonth, 1), 'yyyyMM')),
                    bonusAtr: 0,
                    bcurrentProcessingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, stdDate <= self.sel001() ? stdMonth + 1 : stdMonth, 1), 'yyyyMM')),
                    payStdDay: self.sel001(),
                    resitaxBeginMon: 6,
                    resitaxStdMon: 1,
                    resitaxStdDay: 1,
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
                for (var month = 0; month < 12; month++) {
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
                c.services.insertData(data).done(self.closeDialog);
            };
            ViewModel.prototype.closeDialog = function () { nts.uk.ui.windows.close(); };
            return ViewModel;
        }());
        c.ViewModel = ViewModel;
    })(c = qmm005.c || (qmm005.c = {}));
})(qmm005 || (qmm005 = {}));
