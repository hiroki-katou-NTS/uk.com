module nts.uk.at.view.kal004.g.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = nts.uk.at.view.kal004.share.model;
    import service = nts.uk.at.view.kal004.a.service;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        textlabel: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;

        //start date tab1
        strSelected: KnockoutObservable<number>;
        strDay: KnockoutObservable<number>;
        strMonth: KnockoutObservable<number>;
        strComboMonth: KnockoutObservableArray<any>;

        //End date tab1
        endSelected: KnockoutObservable<number>;
        endDay: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        endComboMonth: KnockoutObservableArray<any>;

        daily36: share.ExtractionPeriodDailyCommand;
        listMonthly36: Array<share.ExtractionPeriodMonthlyCommand>;
        yearly36: share.ExtractionRangeYearCommand;
        monthly2: share.ExtractionPeriodMonthlyCommand;
        monthly3: share.ExtractionPeriodMonthlyCommand;
        monthly4: share.ExtractionPeriodMonthlyCommand;

        categoryId: KnockoutObservable<number>;
        categoryName: KnockoutObservable<string>;

        dateSpecify: KnockoutObservableArray<any>;



        // tab2 
        strMonth2: KnockoutObservable<number>;
        strComboMonth2: KnockoutObservableArray<any>;
        endMonth2: KnockoutObservable<number>;
        endComboMonth2: KnockoutObservableArray<any>;

        // tab3
        yearSpecify: KnockoutObservableArray<any>;
        strSelected3: KnockoutObservable<number>;
        strMonthy3: KnockoutObservable<number>;
        strMonth3: KnockoutObservable<number>;
        strComboMonth3: KnockoutObservableArray<any>;
        endMonth3: KnockoutObservable<number>;
        endComboMonth3: KnockoutObservableArray<any>;

        // tab4        
        strSelected4: KnockoutObservable<number>;
        strMonthy4: KnockoutObservable<number>;
        strMonth4: KnockoutObservable<number>;
        strComboMonth4: KnockoutObservableArray<any>;
        endMonth4: KnockoutObservable<number>;
        endComboMonth4: KnockoutObservableArray<any>;

        //tab5
        strSelected5: KnockoutObservable<number>;
        strYear5: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.textlabel = ko.observable(nts.uk.ui.windows.getShared("categoryName"));
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: getText('KAL004_69'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: getText('KAL004_70'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: getText('KAL004_71'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: getText('KAL004_72'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: getText('KAL004_73'), content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true) },

            ]);
            self.selectedTab = ko.observable(nts.uk.ui.windows.getShared("selectedTab"));


            self.yearly36 = nts.uk.ui.windows.getShared("yearly36");
            self.listMonthly36 = nts.uk.ui.windows.getShared("listMonthly36");
            self.daily36 = nts.uk.ui.windows.getShared("daily36");
            self.categoryName = nts.uk.ui.windows.getShared("categoryName");
            self.categoryId = ko.observable(nts.uk.ui.windows.getShared("categoryId"));
            self.monthly2 = _.find(self.listMonthly36, ['unit', 0]);
            self.monthly3 = _.find(self.listMonthly36, ['unit', 1]);
            self.monthly4 = _.find(self.listMonthly36, ['unit', 2]);

            //start date
            self.strSelected = ko.observable(Number(self.daily36.strSpecify));
            self.strDay = ko.observable(self.strSelected() == 0 ? Number(self.daily36.strDay) : 0);
            self.strMonth = ko.observable(self.strSelected() == 1 ? self.daily36.strMonth : 0);
            self.strComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.strSelected.subscribe((newSelect) => {
                if (newSelect) $('.input-str1').ntsError('clear');
            });


            //End Date
            self.endSelected = ko.observable(Number(self.daily36.endSpecify));
            self.endDay = ko.observable(self.endSelected() == 0 ? Number(self.daily36.endDay) : null);
            self.endMonth = ko.observable(self.endSelected() == 1 ? self.daily36.endMonth : 0);
            self.endComboMonth = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endSelected.subscribe((newSelect) => {
                if (newSelect) $('.input-end1').ntsError('clear');
            });


            self.dateSpecify = ko.observableArray([
                { value: 0, name: getText('KAL004_77') },
                { value: 1, name: '' }
            ]);

            //tab2
            self.strMonth2 = ko.observable(self.monthly2.strMonth);
            self.strComboMonth2 = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endMonth2 = ko.observable(self.monthly2.endMonth);
            self.endComboMonth2 = ko.observableArray(__viewContext.enums.StandardMonth);


            //tab3:
            self.yearSpecify = ko.observableArray([
                { value: 0, name: '' },
                { value: 1, name: getText('KAL004_96') }
            ]);
            self.strSelected3 = ko.observable(self.monthly3.strSpecify - 1);
            self.strMonthy3 = ko.observable(self.strSelected3() == 1 ? self.monthly3.specifyMonth : null);
            self.strMonth3 = ko.observable(self.strSelected3() == 0 ? self.monthly3.strMonth : 0);
            self.strComboMonth3 = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endMonth3 = ko.observable(self.monthly3.endMonth);
            self.endComboMonth3 = ko.observableArray(__viewContext.enums.StandardMonth);
            self.strSelected3.subscribe((newSelect) => {
                if (!newSelect) $('.input-str3').ntsError('clear');
            });

            //tab4:
            self.strSelected4 = ko.observable(self.monthly4.strSpecify - 1);
            self.strMonthy4 = ko.observable(self.strSelected4() == 1 ? self.monthly4.specifyMonth : null);
            self.strMonth4 = ko.observable(self.strSelected4() == 0 ? self.monthly4.strMonth : 0);
            self.strComboMonth4 = ko.observableArray(__viewContext.enums.StandardMonth);
            self.endMonth4 = ko.observable(self.monthly4.endMonth);
            self.endComboMonth4 = ko.observableArray(__viewContext.enums.StandardMonth);
            self.strSelected4.subscribe((newSelect) => {
                if (!newSelect) $('.input-str4').ntsError('clear');
            });

            //tab5
            self.strSelected5 = ko.observable(Number(self.yearly36.thisYear));
            self.strYear5 = ko.observable(self.strSelected5() == 0 ? Number(self.yearly36.year) : null);
            self.strSelected5.subscribe((newSelect) => {
                if (newSelect) $('.input-str5').ntsError('clear');
            });

            self.selectedTab.subscribe((value) => {
                self.checkForcus(value);
                nts.uk.ui.errors.clearAll();
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        checkValidate(): boolean {
            let self = this;
            if (self.strSelected() == 0) $(".input-str1").trigger("validate");
            if (self.endSelected() == 0) $(".input-end1").trigger("validate");
            if (self.strSelected3() == 1) $(".input-str3").trigger("validate");
            if (self.strSelected4() == 1) $(".input-str4").trigger("validate");
            if (self.strSelected5() == 0) $(".input-str5").trigger("validate");

            if ($(".nts-input").ntsError("hasError")) {
                return true;
            }

            return false;
        }

        submit() {
            let self = this;

            if (self.checkValidate()) return;

            // tab1
            if (self.strSelected() == 0) {
                if (self.endSelected() == 0 && self.strDay() < self.endDay()) {
                    alertError({ messageId: "Msg_812" });
                    return;
                } else if (self.endSelected() == 1) {
                    alertError({ messageId: "Msg_815" });
                    return;
                }
            } else {
                if (self.endSelected() == 1 && self.strMonth() < self.endMonth()) {
                    alertError({ messageId: "Msg_812" });
                    return;
                }
            }
            // tab2
            if (self.strMonth2() < self.endMonth2()) {
                alertError({ messageId: "Msg_812" });
                return;
            }

            // tab3
            if (self.strSelected3() == 0 && self.strMonth3() < self.endMonth3()) {
                alertError({ messageId: "Msg_812" });
                return;
            }
            // tab4
            if (self.strSelected4() == 0 && self.strMonth4() < self.endMonth4()) {
                alertError({ messageId: "Msg_812" });
                return;
            }



            let daily36Share = {
                extractionId: "",
                extractionRange: 0,
                strSpecify: self.strSelected(),
                strPreviousDay: 0,
                strMakeToDay: 0,
                strDay: self.strSelected() == 0 ? Number(self.strDay()) : 0,
                strPreviousMonth: 0,
                strCurrentMonth: 1,
                strMonth: self.strSelected() == 1 ? self.strMonth() : 0,
                endSpecify: self.endSelected(),
                endPreviousDay: 0,
                endMakeToDay: 0,
                endDay: self.endSelected() == 0 ? Number(self.endDay()) : 0,
                endPreviousMonth: 0,
                endCurrentMonth: 1,
                endMonth: self.endSelected() == 1 ? self.endMonth() : 0
            };
            let monthly2Share = {
                extractionId: "",
                extractionRange: 0,
                unit: 0,
                strSpecify: 1,
                yearType: 2,
                specifyMonth: 0,
                strMonth: self.strMonth2(),
                strCurrentMonth: 1,
                strPreviousAtr: 0,
                endSpecify: 2,
                extractPeriod: 12,
                endMonth: self.endMonth2(),
                endCurrentMonth: 1,
                endPreviousAtr: 0
            }
            let monthly3Share = {
                extractionId: "",
                extractionRange: 0,
                unit: 1,
                strSpecify: self.strSelected3() + 1,
                yearType: 2,
                specifyMonth: self.strSelected3() == 1 ? self.strMonthy3() : 0,
                strMonth: self.strSelected3() == 0 ? self.strMonth3() : 0,
                strCurrentMonth: 1,
                strPreviousAtr: 0,
                endSpecify: 2,
                extractPeriod: 12,
                endMonth: self.endMonth3(),
                endCurrentMonth: 1,
                endPreviousAtr: 0
            }
            let monthly4Share = {
                extractionId: "",
                extractionRange: 0,
                unit: 2,
                strSpecify: self.strSelected4() + 1,
                yearType: 2,
                specifyMonth: self.strSelected4() == 1 ? self.strMonthy4() : 0,
                strMonth: self.strSelected4() == 0 ? self.strMonth4() : 0,
                strCurrentMonth: 1,
                strPreviousAtr: 0,
                endSpecify: 2,
                extractPeriod: 12,
                endMonth: self.endMonth4(),
                endCurrentMonth: 1,
                endPreviousAtr: 0
            }
            let listMonth36Share = [monthly2Share, monthly3Share, monthly4Share];

            let yearly36Share = {
                extractionId: "",
                extractionRange: 1,
                year: self.strSelected5() == 0 ? Number(self.strYear5()) : 0,
                thisYear: self.strSelected5()
            }

            nts.uk.ui.windows.setShared("selectedTab", self.selectedTab());
            nts.uk.ui.windows.setShared("daily36Share", daily36Share);
            nts.uk.ui.windows.setShared("listMonth36Share", listMonth36Share);
            nts.uk.ui.windows.setShared("yearly36Share", yearly36Share);
            nts.uk.ui.windows.close();
        }
        checkForcus(value): any {
            let self = this;
            if (value == "tab-1") {
                setTimeout(function() {
                    if (self.strSelected() == 0) {
                        $("#start-tab1").focus();
                    } else {
                        $("#combo-1 input").focus();
                    }

                }, 50);

            } else if (value == "tab-2") {
                setTimeout(function() {
                    $("#start-tab-2 input").focus();
                }, 50);

            } else if (value == "tab-3") {
                setTimeout(function() {
                    if (self.strSelected3() == 0) {
                        $("#start-tab-3 input").focus();
                    } else {
                        $("#edit-3").focus();
                    }

                }, 50);

            } else if (value == "tab-4") {
                setTimeout(function() {
                    if (self.strSelected4() == 0) {
                        $("#start-tab-4 input").focus();
                    } else {
                        $("#edit-4").focus();
                    }
                }, 50);

            } else if (value == "tab-5") {
                setTimeout(function() {
                    if (self.strSelected4() == 0) {
                        $("#start-tab-5").focus();
                    }
                }, 50);

            }
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }

}