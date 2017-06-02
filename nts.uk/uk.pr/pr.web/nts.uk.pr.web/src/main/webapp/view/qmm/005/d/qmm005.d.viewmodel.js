var qmm005;
(function (qmm005) {
    var d;
    (function (d) {
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
                this.sel016 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: 'この処理区分を廃止する' });
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
                    sel016: new nts.uk.ui.DirtyChecker(this.sel016.checked),
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
                            || this.sel015.isDirty()
                            || this.sel016.isDirty();
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
                        this.sel016.reset();
                    }
                };
                var self = this;
                // processingNo
                self.index(nts.uk.ui.windows.getShared('dataRow').index());
                self.sel016.checked(nts.uk.ui.windows.getShared('dataRow').dispSet);
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
                // observable all props
                d.services.getData(self.index()).done(function (resp) {
                    if (resp && resp.length == 2) {
                        self.inp001(nts.uk.ui.windows.getShared('dataRow').label());
                        if (resp[0]) {
                            self.sel001(resp[0].payStdDay || self.sel001());
                            self.sel002(resp[0].pickupStdMonAtr || self.sel002());
                            self.sel003(resp[0].pickupStdDay || self.sel003());
                            self.sel004(resp[0].accountDueMonAtr || self.sel004());
                            self.sel005(resp[0].accountDueDay || self.sel005());
                            self.sel006(resp[0].payslipPrintMonth || self.sel006());
                            self.sel007(resp[0].socialInsLevyMonAtr || self.sel007());
                        }
                        if (resp[1]) {
                            self.sel008(resp[1].socialInsStdYearAtr || self.sel008());
                            self.sel009(resp[1].socialInsStdMon || self.sel009());
                            self.sel010(resp[1].socialInsStdDay || self.sel010());
                            self.sel011(resp[1].empInsStdMon || self.sel011());
                            self.sel012(resp[1].empInsStdDay || self.sel012());
                            self.sel013(resp[1].incometaxStdYearAtr || self.sel013());
                            self.sel014(resp[1].incometaxStdMon || self.sel014());
                            self.sel015(resp[1].incometaxStdDay || self.sel015());
                        }
                        // reset all dirty data
                        self.dirty.reset();
                    }
                });
                // observable all datas
                self.sel001Data(D_SEL_001_DATA);
                self.sel002Data(D_SEL_002_DATA);
                self.sel003Data(D_SEL_003_DATA);
                self.sel004Data(D_SEL_004_DATA);
                self.sel005Data(D_SEL_005_DATA);
                self.sel006Data(D_SEL_006_DATA);
                self.sel007Data(D_SEL_007_DATA);
                self.sel008Data(D_SEL_008_DATA);
                self.sel009Data(D_SEL_009_DATA);
                self.sel010Data(D_SEL_010_DATA);
                self.sel011Data(D_SEL_011_DATA);
                self.sel012Data(D_SEL_012_DATA);
                self.sel013Data(D_SEL_013_DATA);
                self.sel014Data(D_SEL_014_DATA);
                self.sel015Data(D_SEL_015_DATA);
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
                    dispSet: self.sel016.checked() == true ? 1 : 0,
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
                    incometaxStdDay: self.sel015()
                };
                d.services.updateData(data).done(function () {
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
        d.ViewModel = ViewModel;
    })(d = qmm005.d || (qmm005.d = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.d.viewmodel.js.map