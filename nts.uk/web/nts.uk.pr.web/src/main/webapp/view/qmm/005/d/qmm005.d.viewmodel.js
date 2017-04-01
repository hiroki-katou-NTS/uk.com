var qmm005;
(function (qmm005) {
    var d;
    (function (d) {
        var ViewModel = (function () {
            function ViewModel() {
                var self = this;
                // processingNo
                self.index = ko.observable(nts.uk.ui.windows.getShared('dataRow').index());
                // define all data
                var D_SEL_001_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_001_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_001_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var D_SEL_002_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' })
                ];
                var D_SEL_003_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_003_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_003_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var D_SEL_004_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' })
                ];
                var D_SEL_005_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_005_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_005_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var D_SEL_006_DATA = [
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                ];
                var D_SEL_007_DATA = [
                    new qmm005.common.SelectItem({ index: -2, label: '前々月' }),
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当月' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌月' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々月' })
                ];
                var D_SEL_008_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前年' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当年' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌年' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々年' })
                ];
                var D_SEL_009_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前月' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当月' })
                ];
                for (var i = 1; i <= 12; i++) {
                    D_SEL_009_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var D_SEL_010_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_010_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_010_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var D_SEL_011_DATA = [];
                for (var i = 1; i <= 12; i++) {
                    D_SEL_011_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var D_SEL_012_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_012_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_012_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                var D_SEL_013_DATA = [
                    new qmm005.common.SelectItem({ index: -1, label: '前年' }),
                    new qmm005.common.SelectItem({ index: 0, label: '当年' }),
                    new qmm005.common.SelectItem({ index: 1, label: '翌年' }),
                    new qmm005.common.SelectItem({ index: 2, label: '翌々年' }),
                ];
                var D_SEL_014_DATA = [];
                for (var i = 1; i <= 12; i++) {
                    D_SEL_014_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '月' }));
                }
                var D_SEL_015_DATA = [];
                for (var i = 1; i < 31; i++) {
                    D_SEL_015_DATA.push(new qmm005.common.SelectItem({ index: i, label: i + '日' }));
                }
                D_SEL_015_DATA.push(new qmm005.common.SelectItem({ index: 31, label: '末日' }));
                // observables all default values
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
                d.services.getData(self.index()).done(function (resp) {
                    if (resp && resp.length == 2) {
                        self.inp001(nts.uk.ui.windows.getShared('dataRow').label());
                        self.sel001(resp[0].payStdDay);
                        self.sel002(resp[0].pickupStdMonAtr);
                        self.sel003(resp[0].pickupStdDay);
                        self.sel004(resp[0].accountDueMonAtr);
                        self.sel005(resp[0].accountDueDay);
                        self.sel006(resp[0].payslipPrintMonth);
                        self.sel007(resp[0].socialInsLevyMonAtr);
                        self.sel008(resp[1].socialInsStdYearAtr);
                        self.sel009(resp[1].socialInsStdMon);
                        self.sel010(resp[1].socialInsStdDay);
                        self.sel011(resp[1].empInsStdMon);
                        self.sel012(resp[1].empInsStdDay);
                        self.sel013(resp[1].incometaxStdYearAtr);
                        self.sel014(resp[1].incometaxStdMon);
                        self.sel015(resp[1].incometaxStdDay);
                    }
                });
                // observable all datas
                self.sel001Data = ko.observableArray(D_SEL_001_DATA);
                self.sel002Data = ko.observableArray(D_SEL_002_DATA);
                self.sel003Data = ko.observableArray(D_SEL_003_DATA);
                self.sel004Data = ko.observableArray(D_SEL_004_DATA);
                self.sel005Data = ko.observableArray(D_SEL_005_DATA);
                self.sel006Data = ko.observableArray(D_SEL_006_DATA);
                self.sel007Data = ko.observableArray(D_SEL_007_DATA);
                self.sel008Data = ko.observableArray(D_SEL_008_DATA);
                self.sel009Data = ko.observableArray(D_SEL_009_DATA);
                self.sel010Data = ko.observableArray(D_SEL_010_DATA);
                self.sel011Data = ko.observableArray(D_SEL_011_DATA);
                self.sel012Data = ko.observableArray(D_SEL_012_DATA);
                self.sel013Data = ko.observableArray(D_SEL_013_DATA);
                self.sel014Data = ko.observableArray(D_SEL_014_DATA);
                self.sel015Data = ko.observableArray(D_SEL_015_DATA);
            }
            ViewModel.prototype.toggleView = function () {
                $('.form-extent').toggleClass('hidden');
                if (!$('.form-extent').hasClass('hidden')) {
                    nts.uk.ui.windows.getSelf().setHeight(window.top.innerHeight < 801 ? 620 : 660);
                    $('#contents-area').css('padding-bottom', '0');
                }
                else {
                    nts.uk.ui.windows.getSelf().setHeight(370);
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
                    payBonusAtr: 0,
                    sparePayAtr: 0,
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
                    incometaxStdDay: self.sel015()
                };
                d.services.updateData(data).done(self.closeDialog);
            };
            ViewModel.prototype.closeDialog = function () { nts.uk.ui.windows.close(); };
            return ViewModel;
        }());
        d.ViewModel = ViewModel;
    })(d = qmm005.d || (qmm005.d = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.d.viewmodel.js.map