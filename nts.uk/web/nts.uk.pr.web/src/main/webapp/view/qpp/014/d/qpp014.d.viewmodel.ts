module qpp014.d.viewmodel {
    export class ScreenModel {
        countItems: KnockoutObservable<number>;
        processingYMNotConvert: KnockoutObservable<number>;
        processingYM: KnockoutObservable<string>;
        sparePayAtr: KnockoutObservable<number>;
        d_SEL_002_selectedCode: KnockoutObservable<any>;
        d_LST_001_items: KnockoutObservableArray<ItemModel_D_LST_001>;
        d_LST_001_itemSelected: KnockoutObservable<any>;
        d_nextScreen: KnockoutObservable<string>;
        dateOfPayment: KnockoutObservable<string>;
        d_lbl_015: KnockoutObservable<string>;
        d_lbl_016: KnockoutObservable<string>;
        data: KnockoutObservable<any>;
        list: KnockoutObservable<any>;
        list0: KnockoutObservable<any>;
        list1: KnockoutObservable<any>;

        constructor(data: any) {
            let self = this;
            self.dateOfPayment = ko.observable(null);
            self.sparePayAtr = ko.observable(1);
            self.d_SEL_002_selectedCode = ko.observable(1);
            self.d_LST_001_items = ko.observableArray([]);
            self.countItems = ko.observable(0);
            self.d_LST_001_itemSelected = ko.observable(0);
            self.d_nextScreen = ko.computed(function() {
                //check value of D_SEL_002 to jump to screen G or H after click E_BTN_002
                return self.d_SEL_002_selectedCode() == 2 ? 'screen_g' : 'screen_h';
            });
            self.processingYMNotConvert = ko.observable(data.currentProcessingYm);
            self.processingYM = ko.observable(nts.uk.time.formatYearMonth(data.currentProcessingYm));
            self.d_lbl_015 = ko.observable(data.processingNo);
            self.d_lbl_016 = ko.observable(data.processingName);
            nts.uk.ui.windows.setShared("sparePayAtr", self.sparePayAtr(), true);
            nts.uk.ui.windows.setShared("processingNo", self.d_lbl_015(), true);
            nts.uk.ui.windows.setShared("processingYMNotConvert", self.processingYMNotConvert(), true);
            self.list = ko.observableArray([]);
            self.list0 = ko.observableArray([]);
            self.list1 = ko.observableArray([]);
            self.findDataScreenD();
            self.data = ko.observable();

        }

        findDataScreenD(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qpp014.d.service.findDataScreenD(+self.d_lbl_015())
                .done(function(data) {
                    self.data(data);
                    _.forEach(data.listOfScreenDDto0, function(x) {
                        self.list0().push(new ItemModel_D_LST_001(x.scd, x.nameB, x.paymentMethod1, x.paymentMethod2, x.paymentMethod3, x.paymentMethod4, x.paymentMethod5, x.pid));
                    });
                    _.forEach(data.listOfScreenDDto1, function(x) {
                        self.list1().push(new ItemModel_D_LST_001(x.scd, x.nameB, x.paymentMethod1, x.paymentMethod2, x.paymentMethod3, x.paymentMethod4, x.paymentMethod5, x.pid));
                    });
                    _.forEach(data.listOfScreenDDto, function(x) {
                        self.list().push(new ItemModel_D_LST_001(x.scd, x.nameB, x.paymentMethod1, x.paymentMethod2, x.paymentMethod3, x.paymentMethod4, x.paymentMethod5, x.pid));
                    });
                    self.d_LST_001_items(self.list0());
                    self.countItems(self.d_LST_001_items().length);
                    self.dateOfPayment(data.payDate);
                    bindGrind(self.d_LST_001_items());
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        buttonFilter(): void {
            var self = this;
            var obj = [];
            switch (self.sparePayAtr()) {
                case 1: {
                    self.d_LST_001_items(self.list0());
                    self.countItems(self.d_LST_001_items().length);
                    bindGrind(self.d_LST_001_items());
                    break;
                }
                case 2: {

                    self.d_LST_001_items(self.list1());
                    self.countItems(self.d_LST_001_items().length);
                    bindGrind(self.d_LST_001_items());
                    break;
                }
                case 3: {
                    self.d_LST_001_items(self.list());
                    self.countItems(self.d_LST_001_items().length);
                    bindGrind(self.d_LST_001_items());
                    break;
                }
            }
        }

        openEDialog(): void {
            var self = this;
            if (self.dateOfPayment() == '') {
                nts.uk.ui.dialog.alert("振込日が入力されていません。");
            } else {
                nts.uk.ui.windows.setShared("dataFromD", self.d_LST_001_items(), true);
                nts.uk.ui.windows.setShared("dateOfPayment", self.dateOfPayment(), true);
                nts.uk.ui.windows.sub.modal("/view/qpp/014/e/index.xhtml", { title: "振込データの作成結果一覧", dialogClass: "no-close" }).onClosed(function() {
                    //if close button, not next screen
                    if (!nts.uk.ui.windows.getShared("closeDialog")) {
                        $('#wizard').ntsWizard("next");
                        //                    _.delay(() => {
                        //                        $("#H_LST_001").igGridSelection("selectRow", 1);
                        //                        $("#H_LST_001").igGridSelection("clearSelection");
                        //                    }, 201); // hot fix by Lam Than
                    }
                });
            }
        }
    }
    export class ItemModel_D_LST_001 {
        scd: string;
        nameB: string;
        paymentMethod1: string;
        paymentMethod2: string;
        paymentMethod3: string;
        paymentMethod4: string;
        paymentMethod5: string;
        pId: string

        constructor(scd: string, nameB: string, paymentMethod1: string, paymentMethod2: string, paymentMethod3: string, paymentMethod4: string, paymentMethod5: string, pId: string) {
            this.scd = scd;
            this.nameB = nameB;
            this.paymentMethod1 = paymentMethod1;
            this.paymentMethod2 = paymentMethod2;
            this.paymentMethod3 = paymentMethod3;
            this.paymentMethod4 = paymentMethod4;
            this.paymentMethod5 = paymentMethod5;
            this.pId = pId;
        }
    }

};

function bindGrind(dataSource) {
    $("#D_LST_001").igGrid({
        dataSource: dataSource,
        primaryKey: 'scd',
        width: '740px',
        height: '280px',
        autoCommit: false,
        dataSourceType: 'json',
        autoGenerateColumns: false,
        features: [
            {
                name: 'Selection',
                mode: 'row'
            },
            {
                name: 'MultiColumnHeaders'
            }
        ],
        columns: [
            { headerText: 'コード', key: 'scd', dataType: 'string', columnCssClass: 'text_align', width: '15%' },
            { headerText: '名称', key: 'nameB', dataType: 'string', width: '15%' },
            {
                headerText: '振込元設定', width: '70%',
                group: [
                    { headerText: '支払1', key: 'paymentMethod1', dataType: 'string', width: '14%' },
                    { headerText: '支払2', key: 'paymentMethod2', dataType: 'string', width: '14%' },
                    { headerText: '支払3', key: 'paymentMethod3', dataType: 'string', width: '14%' },
                    { headerText: '支払4', key: 'paymentMethod4', dataType: 'string', width: '14%' },
                    { headerText: '支払5', key: 'paymentMethod5', dataType: 'string', width: '14%' }
                ]
            }
        ]
    });
}
