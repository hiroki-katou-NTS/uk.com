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

        constructor(data: any) {
            let self = this;
            self.dateOfPayment = ko.observable(null);
            self.sparePayAtr = ko.observable(1);
            self.d_SEL_002_selectedCode = ko.observable(1);
            self.d_LST_001_items = ko.observableArray([]);
            //            for (let i = 1; i < 31; i++) {
            //                self.d_LST_001_items.push(new ItemModel_D_LST_001('00' + i, '基本給' + i, 'description' + i, 'description' + i, 'description' + i, 'description' + i, 'description' + i));
            //            }
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
            self.findDataScreenD();
        }

        findDataScreenD(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qpp014.d.service.findDataScreenD(+self.d_lbl_015())
                .done(function(data) {
                    var items = [];
                    _.forEach(data, function(x) {
                        items.push(new ItemModel_D_LST_001(x.scd, x.nameB, x.paymentMethod1, x.paymentMethod2, x.paymentMethod3, x.paymentMethod4, x.paymentMethod5));
                    });
                    self.d_LST_001_items(items);
                    self.countItems(self.d_LST_001_items().length);
                    self.dateOfPayment(data.payDate);
                    bindGrind(items);
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                });
            return dfd.promise();
        }

        buttonFilter(): void {
            var self = this;
            //            switch (self.sparePayAtr()) {
            //                case 1: {
            //                    for (let i = 1; i < 11; i++) {
            //                        self.d_LST_001_items.push(({ code: '10' + i, name: '基本給' + i, description: ('description' + i) }));
            //                    }
            //                    break;
            //                }
            //                case 2: {
            //                    for (let i = 1; i < 21; i++) {
            //                        self.d_LST_001_items.push(({ code: '20' + i, name: '基本給' + i, description: ('description' + i) }));
            //                    }
            //                    //                    $('#D_LST_001').igGrid('option', 'dataSource', self.d_LST_001_items());
            //                    //                    $('#D_LST_001').igGrid("dataBind");
            //                    break;
            //                }
            //                case 3: {
            //                    for (let i = 1; i < 31; i++) {
            //                        self.d_LST_001_items.push(({ code: '30' + i, name: '基本給' + i, description: ('description' + i) }));
            //                    }
            //                    break;
            //                }
            //            }
        }

        openEDialog(): void {
            var self = this;
            if (self.dateOfPayment() == '') {
                nts.uk.ui.dialog.alert("振込日が入力されていません。");
            } else {
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

        constructor(scd: string, nameB: string, paymentMethod1: string, paymentMethod2: string, paymentMethod3: string, paymentMethod4: string, paymentMethod5: string) {
            this.scd = scd;
            this.nameB = nameB;
            this.paymentMethod1 = paymentMethod1;
            this.paymentMethod2 = paymentMethod2;
            this.paymentMethod3 = paymentMethod3;
            this.paymentMethod4 = paymentMethod4;
            this.paymentMethod5 = paymentMethod5;
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
