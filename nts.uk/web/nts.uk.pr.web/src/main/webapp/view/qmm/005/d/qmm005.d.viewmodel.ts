module qmm005.d {
    export class ViewModel {
        index: KnockoutObservable<number>;
        inp001: KnockoutObservable<string>;

        sel001: KnockoutObservable<number>;
        sel002: KnockoutObservable<number>;
        sel003: KnockoutObservable<number>;
        sel004: KnockoutObservable<number>;
        sel005: KnockoutObservable<number>;
        sel006: KnockoutObservable<number>;
        sel007: KnockoutObservable<number>;
        sel008: KnockoutObservable<number>;
        sel009: KnockoutObservable<number>;
        sel010: KnockoutObservable<number>;
        sel011: KnockoutObservable<number>;
        sel012: KnockoutObservable<number>;
        sel013: KnockoutObservable<number>;
        sel014: KnockoutObservable<number>;
        sel015: KnockoutObservable<number>;

        sel001Data: KnockoutObservableArray<common.SelectItem>;
        sel002Data: KnockoutObservableArray<common.SelectItem>;
        sel003Data: KnockoutObservableArray<common.SelectItem>;
        sel004Data: KnockoutObservableArray<common.SelectItem>;
        sel005Data: KnockoutObservableArray<common.SelectItem>;
        sel006Data: KnockoutObservableArray<common.SelectItem>;
        sel007Data: KnockoutObservableArray<common.SelectItem>;
        sel008Data: KnockoutObservableArray<common.SelectItem>;
        sel009Data: KnockoutObservableArray<common.SelectItem>;
        sel010Data: KnockoutObservableArray<common.SelectItem>;
        sel011Data: KnockoutObservableArray<common.SelectItem>;
        sel012Data: KnockoutObservableArray<common.SelectItem>;
        sel013Data: KnockoutObservableArray<common.SelectItem>;
        sel014Data: KnockoutObservableArray<common.SelectItem>;
        sel015Data: KnockoutObservableArray<common.SelectItem>;

        constructor() {
            let self = this;
            // processingNo
            self.index = ko.observable(nts.uk.ui.windows.getShared('dataRow').index());

            // define all data
            let D_SEL_001_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_001_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_001_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            let D_SEL_002_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: 0, label: '当月' }),
                new common.SelectItem({ index: -1, label: '前月' })
            ];

            let D_SEL_003_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_003_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_003_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            let D_SEL_004_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: 0, label: '当月' }),
                new common.SelectItem({ index: -1, label: '前月' })
            ];

            let D_SEL_005_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_005_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_005_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            let D_SEL_006_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: 0, label: '当月' }),
                new common.SelectItem({ index: -1, label: '前月' }),
            ];

            let D_SEL_007_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: -2, label: '前々月' }),
                new common.SelectItem({ index: -1, label: '前月' }),
                new common.SelectItem({ index: 0, label: '当月' }),
                new common.SelectItem({ index: 1, label: '翌月' }),
                new common.SelectItem({ index: 2, label: '翌々月' })
            ];

            let D_SEL_008_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: -1, label: '前年' }),
                new common.SelectItem({ index: 0, label: '当年' }),
                new common.SelectItem({ index: 1, label: '翌年' }),
                new common.SelectItem({ index: 2, label: '翌々年' })
            ];

            let D_SEL_009_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: -1, label: '前月' }),
                new common.SelectItem({ index: 0, label: '当月' })
            ];

            for (let i = 1; i <= 12; i++) {
                D_SEL_009_DATA.push(new common.SelectItem({ index: i, label: i + '月' }));
            }

            let D_SEL_010_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_010_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_010_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            let D_SEL_011_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i <= 12; i++) {
                D_SEL_011_DATA.push(new common.SelectItem({ index: i, label: i + '月' }));
            }

            let D_SEL_012_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_012_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_012_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            let D_SEL_013_DATA: Array<common.SelectItem> = [
                new common.SelectItem({ index: -1, label: '前年' }),
                new common.SelectItem({ index: 0, label: '当年' }),
                new common.SelectItem({ index: 1, label: '翌年' }),
                new common.SelectItem({ index: 2, label: '翌々年' }),
            ];

            let D_SEL_014_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i <= 12; i++) {
                D_SEL_014_DATA.push(new common.SelectItem({ index: i, label: i + '月' }));
            }

            let D_SEL_015_DATA: Array<common.SelectItem> = [];

            for (let i = 1; i < 31; i++) {
                D_SEL_015_DATA.push(new common.SelectItem({ index: i, label: i + '日' }));
            }

            D_SEL_015_DATA.push(new common.SelectItem({ index: 31, label: '末日' }));

            // observables all values
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

        toggleView() {
            $('.form-extent').toggleClass('hidden');
            if (!$('.form-extent').hasClass('hidden')) {
                nts.uk.ui.windows.getSelf().setHeight(600);
                $('#contents-area').css('padding-bottom', '0');
            } else {
                nts.uk.ui.windows.getSelf().setHeight(480);
                $('#contents-area').css('padding-bottom', '');
            }
        }

        validate($root) {
            let self = this;
            return $root.errors.isEmpty() && self.inp001().trim() != '';
        }

        saveData() {
            let self = this,
                date = new Date(),
                stdYear = date.getFullYear(),
                stdMonth = date.getMonth(),
                stdDate = date.getDate();

            // Chưa thấy đề cập tới các trường hợp ngày > số ngày trong tháng.
            var data = {
                payBonusAtr: 0,
                sparePayAtr: 0,
                processingNo: self.index(),
                processingName: self.inp001(),
                dispSet: 0,
                currentProcessingYm: parseInt(nts.uk.time.formatDate(new Date(stdYear, stdDate <= self.sel001() ? stdMonth + 1 : stdMonth, 1), 'yyyyMM')),
                bonusAtr: 0,
                bCurrentProcessingYm: 0,
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
                    neededWorkDay: 0, //Interim
                    empInsStdDate: new Date(stdYear, data.empInsStdMon - 1, data.empInsStdDay),
                    stmtOutputMon: parseInt(nts.uk.time.formatDate(new Date(stdYear, month + data.payslipPrintMonth, 1), 'yyyyMM'))
                });
            }
            debugger;
            //services.insertData(data).done(function(reps) {
            //    self.closeDialog();
            //});
        }

        closeDialog() { nts.uk.ui.windows.close(); }
    }
}



