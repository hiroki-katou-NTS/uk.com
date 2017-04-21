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

            services.getData(self.index()).done(function(resp: Array<any>) {
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

        toggleView() {
            $('.form-extent').toggleClass('hidden');
            if (!$('.form-extent').hasClass('hidden')) {
                nts.uk.ui.windows.getSelf().setHeight(window.top.innerHeight < 801 ? 620 : 660);
                $('#contents-area').css('padding-bottom', '0');
            } else {
                nts.uk.ui.windows.getSelf().setHeight(370);
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
            services.updateData(data).done(self.closeDialog);
        }

        closeDialog() { nts.uk.ui.windows.close(); }
    }
}



