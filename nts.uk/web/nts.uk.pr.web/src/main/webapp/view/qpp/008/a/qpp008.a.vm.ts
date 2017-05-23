module qpp008.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        singleSelectedCode: KnockoutObservable<string>;
        textLbl006: KnockoutObservable<string>;
        /* Edit Text */
        processingYMEarlierValue: KnockoutObservable<string>;
        processingYMLaterValue: KnockoutObservable<string>;
        /*Multiple selecting GridList*/
        statusRegisterList: KnockoutObservableArray<ItemModel>;
        statusRegisterColumns: KnockoutObservableArray<any>;
        statusRegisterSelectCode: KnockoutObservableArray<string>;
        /*Multiple selecting GridList*/
        employyerList: KnockoutObservableArray<EmployeerInfo>;
        employyerColumns: KnockoutObservableArray<any>;
        employyerCurrentCodeList: KnockoutObservableArray<any>;
        //combobox1
        formHeaderList: KnockoutObservableArray<FormHeaderModel>;
        formHeaderSelectCode: KnockoutObservable<string>;
        //combobox2
        gradeList: KnockoutObservableArray<ItemModel>;
        gradeSelectedCode: KnockoutObservable<number>;
        //combobox3
        pagingList: KnockoutObservableArray<ItemModel>;
        pagingSelectedCode: KnockoutObservable<number>;
        pagingEnable: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            self._init();

            self.textLbl006 = ko.observable(nts.uk.time.formatDate(new Date(), "yyyy/MM/dd"));
            self.processingYMEarlierValue = ko.observable('');
            self.processingYMLaterValue = ko.observable('');

            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);

            self.gradeSelectedCode.subscribe(function(newValue) {
                if (nts.uk.text.isNullOrEmpty(newValue)) {
                    return;
                }
                if (newValue === 3) {
                    self.pagingEnable(true);
                    return;
                }
                self.pagingEnable(false);
            });

        }

        _init() {
            let self = this;
            /*  GridList */
            self.statusRegisterList = ko.observableArray([
                new ItemModel(1, '自動計算による変更'),
                new ItemModel(2, '手入力による変更'),
                new ItemModel(3, '就業システム連動'),
                new ItemModel(4, '外部データ（CSV）取り込みの値'),
                new ItemModel(5, '未計算')
            ]);
            self.statusRegisterColumns = ko.observableArray([
                { headerText: '印刷内容', prop: 'code', width: 150, hidden: 'hidden' },
                { headerText: '印刷内容', prop: 'name', width: 150 }

            ]);
            self.statusRegisterSelectCode = ko.observableArray([]);

            self.employyerList = ko.observableArray([]);
            self.employyerColumns = ko.observableArray([
                { headerText: '社員CD', prop: 'personID', hidden: 'hidden' },
                { headerText: '社員CD', prop: 'employeeCode', width: 200 },
                { headerText: '氏名', prop: 'employeeName', width: 150 },
                { headerText: '所属', prop: 'departmentName', width: 150 }

            ]);
            self.employyerCurrentCodeList = ko.observableArray([]);

            //combobox1
            self.formHeaderList = ko.observableArray([]);
            self.formHeaderSelectCode = ko.observable('');
            //combobox2
            self.gradeList = ko.observableArray([
                new ItemModel(0, 'なし'),
                new ItemModel(1, '社員毎'),
                new ItemModel(2, '部門毎'),
                new ItemModel(3, '部門階層')
            ]);
            self.gradeSelectedCode = ko.observable(0);
            //combobox3
            self.pagingList = ko.observableArray([
                new ItemModel(0, '（空白）'),
                new ItemModel(1, '1'),
                new ItemModel(2, '2'),
                new ItemModel(3, '3'),
                new ItemModel(4, '4'),
                new ItemModel(5, '5'),
                new ItemModel(6, '6'),
                new ItemModel(7, '7'),
                new ItemModel(8, '8'),
                new ItemModel(9, '9'),
            ]);
            self.pagingSelectedCode = ko.observable(0);
            self.pagingEnable = ko.observable(false);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadPersonInfo();
            self.loadComparingFormHeader();
            dfd.resolve();
            return dfd.promise();
        }

        loadComparingFormHeader() {
            let self = this;
            let dfd = $.Deferred();
            self.formHeaderList([]);
            service.getListComparingFormHeader().done(function(data: any) {
                _.forEach(data, function(item) {
                    self.formHeaderList.push(new FormHeaderModel(item));
                });
                dfd.resolve();
            }).fail(function(error: any) {

            });
            return dfd.promise();
        }

        loadPersonInfo() {
            let self = this;
            let dfd = $.Deferred();
            self.employyerList([]);
            service.getPersonInfo().done(function(data) {
                self.employyerList(_.map(data, function(emp: any) {
                    return new EmployeerInfo(emp.personID, emp.employeeCode, emp.employeeName, "", "Hard code");
                }));
                dfd.resolve();
            });
            return dfd.promise();
        }
        /**
         *  to JSon Object
         */
        private toJSObjet(): any {
            let self = this;
            let command: any = {};
            //command.formCode = self.selectedCodeCbb1();
            command.employeeCodeList = self.employyerCurrentCodeList();
            command.month1 = self.processingYMEarlierValue().trim().replace("/", "");
            command.monthJapan1 = nts.uk.time.yearInJapanEmpire(self.processingYMEarlierValue().trim().replace("/", "").charAt[0]).toString();
            command.monthJapan1 = command.monthJapan1 + " " + self.processingYMEarlierValue().split("/")[1] + "月";
            command.month2 = self.processingYMLaterValue().trim().replace("/", "");
            command.monthJapan2 = nts.uk.time.yearInJapanEmpire(self.processingYMLaterValue().trim().replace("/", "").charAt[0]).toString();
            command.monthJapan2 = command.monthJapan2 + " " + self.processingYMLaterValue().split("/")[1] + "月";
            command.formCode = self.formHeaderSelectCode();
            command.gradeSelectedCode = self.gradeSelectedCode();
            command.pagingSelectedCode = self.pagingSelectedCode();
            command.payBonusAttr = 0;
            return command;
        }
        /**
         *  Export Data
         */
        exportData(): void {
            let self = this;
            let dfd = $.Deferred<any>();
            let command: any;
            command = self.toJSObjet();
            service.saveAsPdf(command).done(function() {
            }).fail(function(res: any) {
              nts.uk.ui.dialog.alert(res.message);
            });
        }

        openDialogB() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/qpp/008/b/index.xhtml', { title: '印刷設定' }).onClosed(function(): any {

            });
        }

        openDialogC() {
            let self = this;
            nts.uk.ui.windows.setShared('qpp008_form_header_code_set', self.formHeaderSelectCode(), true);
            nts.uk.ui.windows.sub.modal('/view/qpp/008/c/index.xhtml', { title: '出力項目の設定（共通）', dialogClass: 'no-close' }).onClosed(function(): any {
                self.loadComparingFormHeader().done(function() {
                    self.formHeaderSelectCode(nts.uk.ui.windows.getShared('qpp008_form_header_code'));
                });
            });
        }

        openDialogG() {
            let self = this;
            if (!self.isValidInputComparingDate(self.processingYMEarlierValue(), self.processingYMLaterValue())) {
                return;
            }
            if (self.employyerCurrentCodeList().length === 0) {
                nts.uk.ui.dialog.alert("社員一覧（汎用コントロール）が選択されていません。");
                return;
            }
            let processingYMEarlier = Number(self.processingYMEarlierValue().trim().replace("/", ""));
            let processingYMLater = Number(self.processingYMLaterValue().trim().replace("/", ""));
            service.getDetailDifferentials(processingYMEarlier, processingYMLater, self.employyerCurrentCodeList()).done(function(data: any) {
                if (!data || data.length === 0) {
                    nts.uk.ui.dialog.alert("対象データがありません。");
                    return;
                }
                nts.uk.ui.windows.setShared('qpp008_data', data, true);
                nts.uk.ui.windows.setShared('qpp008_employyerCurrentCodeList', self.employyerCurrentCodeList(), true);
                nts.uk.ui.windows.setShared('qpp008_processingYMEarlierValue', self.processingYMEarlierValue(), true);
                nts.uk.ui.windows.setShared('qpp008_processingYMLaterValue', self.processingYMLaterValue(), true);
                nts.uk.ui.windows.sub.modal('/view/qpp/008/g/index.xhtml', { title: '差異を確認' }).onClosed(function(): any {
                });
            }).fail(function(error: any) {
                nts.uk.ui.dialog.alert(error.message);
            });

        }

        isValidInputComparingDate(date1: string, date2: string): boolean {
            if (nts.uk.text.isNullOrEmpty(date1)) {
                nts.uk.ui.dialog.alert("比較年月1が入力されていません。");
                return false;
            }
            if (nts.uk.text.isNullOrEmpty(date2)) {
                nts.uk.ui.dialog.alert("比較年月2が入力されていません。");
                return false;
            }
            if (date1 === date2) {
                nts.uk.ui.dialog.alert("設定が正しくありません。");
                return false;
            }
            return true;
        }
    }

    class ItemModel {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            let self = this;
            self.code = code;
            self.name = name;
        }
    }

    class FormHeaderModel {
        formCode: string;
        formName: string;
        constructor(data: any) {
            let self = this;
            self.formCode = data.formCode;
            self.formName = data.formName;
        }
    }

    class EmployeerInfo {
        personID: string;
        employeeCode: string;
        employeeName: string;
        departmentCode: string;
        departmentName: string;
        constructor(personID: string, employeeCode: string, employeeName: string, departmentCode: string, departmentName: string) {
            this.personID = personID;
            this.employeeCode = employeeCode;
            this.employeeName = employeeName;
            this.departmentCode = departmentCode;
            this.departmentName = departmentName;
        }
    }

    class ComparingSalaryBonus {
        month1: string;
        month2: string;
        employeeCodeList: Array<string>;
        constructor(month1: string, month2: string, employeeCodeList: Array<string>) {
            this.month1 = month1;
            this.month2 = month2;
            this.employeeCodeList = employeeCodeList;
        }
    }

}
