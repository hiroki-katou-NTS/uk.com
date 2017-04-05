module nts.uk.pr.view.qpp007.a {

    import SalaryOutputSettingHeaderDto = service.model.SalaryOutputSettingHeaderDto;
    
    export module viewmodel {
        
        export class ScreenModel {
            startYearMonth: KnockoutObservable<string>;
            endYearMonth: KnockoutObservable<string>;
            isUsuallyAMonth: KnockoutObservable<boolean>;
            isPreliminaryMonth: KnockoutObservable<boolean>;
            outputFormatType: KnockoutObservableArray<SelectionModel>;
            enable: KnockoutObservable<boolean>;
            selectedOutputFormat: KnockoutObservable<string>;
            outputItemSetting: KnockoutObservableArray<SelectionModel>;
            selectedOutputSetting: KnockoutObservable<string>;
            isVerticalLine: KnockoutObservable<boolean>;
            isHorizontalRuledLine: KnockoutObservable<boolean>;
            pageBreakSetting: KnockoutObservableArray<SelectionModel>;
            selectedpageBreakSetting: KnockoutObservable<string>;
            departmentHierarchyList: KnockoutObservableArray<SelectionModel>;
            selectedHierachy: KnockoutObservable<string>;
            outputLanguage: KnockoutObservableArray<SelectionModel>;
            selectedOutputLanguage: KnockoutObservable<string>;
            isDepartmentHierarchy: KnockoutObservable<boolean>;

            constructor() {
                this.isUsuallyAMonth = ko.observable(true);
                this.isPreliminaryMonth = ko.observable(true);
                this.startYearMonth = ko.observable('2016/12');
                this.endYearMonth = ko.observable('2017/03');
                this.enable = ko.observable(true);
                this.selectedOutputFormat = ko.observable('1');
                this.outputFormatType = ko.observableArray<SelectionModel>([
                    new SelectionModel('1', '明細一覧表'),
                    new SelectionModel('2', '明細累計表')
                ]);
                this.outputItemSetting = ko.observableArray<SelectionModel>([]);
                this.selectedOutputSetting = ko.observable('1');
                this.isVerticalLine = ko.observable(true);
                this.isHorizontalRuledLine = ko.observable(true);

                this.pageBreakSetting = ko.observableArray<SelectionModel>([
                    new SelectionModel('1', 'なし'),
                    new SelectionModel('2', '社員毎'),
                    new SelectionModel('3', '部門毎'),
                    new SelectionModel('4', '部門階層')
                ]);
                this.selectedpageBreakSetting = ko.observable('1');

                this.departmentHierarchyList = ko.observableArray<SelectionModel>([
                    new SelectionModel('1', '1'), new SelectionModel('2', '2'), new SelectionModel('3', '3'),
                    new SelectionModel('4', '4'), new SelectionModel('5', '5'), new SelectionModel('6', '6'),
                    new SelectionModel('7', '7'), new SelectionModel('8', '8'), new SelectionModel('9', '9')
                ]);
                this.selectedHierachy = ko.observable('1');

                this.outputLanguage = ko.observableArray<SelectionModel>([
                    new SelectionModel('1', '日本語'),
                    new SelectionModel('2', '英語')
                ]);
                this.selectedOutputLanguage = ko.observable('1');
                var self = this;
                this.isDepartmentHierarchy = ko.computed(function() {
                    return self.selectedpageBreakSetting() == '4';
                });
            }

            /**
             * Start srceen.
             */
            public start(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                $.when(self.loadAllOutputSetting()).done(function() {
                    dfd.resolve();
                })
                return dfd.promise();
            }

            public loadAllOutputSetting(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                var self = this;
                service.findAllSalaryOutputSetting().done(function(data) {
                    self.outputItemSetting(data);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.reject();
                })
                return dfd.promise();
            }
            
            public openPrintSettingDialog() {
                // Set parent value
                nts.uk.ui.windows.setShared("data", "nothing");

                nts.uk.ui.windows.sub.modal("/view/qpp/007/b/index.xhtml", { title: "印刷設定", dialogClass: 'no-close' }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("childData");
                })
            }
            
            public openSalaryOuputSettingDialog() {
                nts.uk.ui.windows.sub.modal("/view/qpp/007/c/index.xhtml", { title: "出力項目の設定", dialogClass: 'no-close' }).onClosed(() => {
                    // Get child value
                })
            }

            saveAsPdf(): void {
                let self = this;
                let dfd = $.Deferred<void>();
                let command: any = {};
                service.saveAsPdf(command).done(function() {
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });
            }

        }

        /**
         *  Class Page Break setting
         */
        export class SelectionModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}