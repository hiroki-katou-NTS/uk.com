module cmm015.a.viewmodel {

    export class ScreenModel {

        dataSource: KnockoutObservableArray<viewmodel.model.PayClassificationDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;


        //trang thái của INP_002
        INP_002_enable: KnockoutObservable<boolean>;
        INP_003_name: KnockoutObservable<string>;
        INP_004_notes: KnockoutObservable<string>;
        index_of_itemDelete: KnockoutObservable<number>;;
        adddata: any;
        isDeleteEnable: KnockoutObservable<boolean>;

        currentCode: KnockoutObservable<string>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<viewmodel.model.PayClassificationDto>;
        INP_002_code: KnockoutObservable<string>;
        updatedata: any;
        findIndex: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.dataSource = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'payClassificationCode', width: 100 },
                { headerText: '名称', key: 'payClassificationName', width: 80 }

            ]);
            self.index_of_itemDelete = ko.observable(-1);
            self.currentCode = ko.observable(null);
            self.currentCodeList = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.INP_002_code = ko.observable(null);
            self.INP_002_enable = ko.observable(false);
            self.isDeleteEnable = ko.observable(true);
            self.INP_003_name = ko.observable(null);
            self.INP_004_notes = ko.observable(null);
            self.findIndex = ko.observable(null);
            self.adddata = ko.observable(null);
            self.updatedata = ko.observable(null);
            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.find(codeChanged));
                if (self.currentItem() != null) {
                    self.INP_002_enable(false);
                    self.INP_002_code(self.currentItem().payClassificationCode);
                    self.INP_003_name(self.currentItem().payClassificationName);
                    self.INP_004_notes(self.currentItem().memo);
                }

            }));


        }


        initRegisterPayClassification() {

            var self = this;
            self.INP_002_enable(true);
            self.INP_002_code("");
            self.INP_003_name("");
            self.INP_004_notes("");
            self.currentCode(null);
            self.isDeleteEnable(false);
            $("#INP_002_code").focus();


        }


        checkPage(): boolean {
            var self = this;
            if (self.INP_002_code() == '') {
                alert("コードが入力されていません。");
                $("#INP_002_code").focus();
                return false;
            } else if (self.INP_003_name() == '') {
                alert("名称が入力されていません。");
                $("#INP_003_name").focus();
                return false;
            } else { return true; }
        }



        find(value: string): any {
            let self = this;
            return _.find(self.dataSource(), function(obj) {
                return obj.payClassificationCode == value;
            });
        }

        addPayClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            if (self.checkPage()) {
                if (self.dataSource().length === 0) {
                    let payClassification = new viewmodel.model.PayClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                    service.addPayClassification(payClassification).done(function() {

                        self.getPayClassificationList_first();
                        self.isDeleteEnable(true);
                    }).fail(function(res) {
                        dfd.reject(res);
                    })
                }
                for (let i = 0; i < self.dataSource().length ; i++) {
                    if (self.INP_002_code() == self.dataSource()[i].payClassificationCode && self.INP_002_enable() == false) {
                        var payClassification_before = self.dataSource()[i];
                        var payClassification_update = new viewmodel.model.PayClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.updatePayClassification(payClassification_update).done(function() {
                            self.updatedata(payClassification_update);
                            self.getPayClassificationList_afterUpdate();
                        }).fail(function(res) {
                            dfd.reject(res);
                        })
                        break;
                    } else if (self.INP_002_code() != self.dataSource()[i].payClassificationCode
                        && self.INP_002_enable() == true) {
                        var payClassification_new = new viewmodel.model.PayClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.addPayClassification(payClassification_new).done(function() {
                            self.adddata(payClassification_new);
                            self.currentCode(self.adddata().payClassificationCode);
                            self.getPayClassificationList_afterAdd();
                        }).fail(function(res) {
                            alert(res.message);
                            dfd.reject(res);
                        })
                        break;
                    }
                }
            }
        }



        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {


                self.dataSource(payClassification_arr);
                if (self.dataSource().length > 0) {

                    self.INP_002_code(self.dataSource()[0].payClassificationCode);
                    self.INP_003_name(self.dataSource()[0].payClassificationName);
                    self.INP_004_notes(self.dataSource()[0].memo);
                    self.currentCode(self.dataSource()[0].payClassificationCode)

                } else {
                    self.initRegisterPayClassification();

                }
                dfd.resolve();

            }).fail(function(error) {
                alert(error.message);
            })

            dfd.resolve();
            return dfd.promise();
        }

        deletePayClassification() {

            var self = this;
            var dfd = $.Deferred<any>();

            if (self.dataSource().length > 0) {
                var item = new model.RemovePayClassificationCommand(self.currentItem().payClassificationCode);
                self.index_of_itemDelete(_.findIndex(self.dataSource(), function(item)
                { return item.payClassificationCode === self.currentItem().payClassificationCode; }));
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    service.removePayClassification(item).done(function(res) {

                        self.getPayClassificationList_aftefDelete();

                    }).fail(function(res) {
                        dfd.reject(res);
                    })

                }).ifNo(function() {
                });
            } else { return null }
        }


        getPayClassificationList_aftefDelete(): any {

            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {
                self.dataSource(payClassification_arr);

                if (self.dataSource().length > 0) {
                    if (self.index_of_itemDelete() === self.dataSource().length) {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete() - 1].payClassificationCode)
                        self.INP_002_code(self.dataSource()[self.index_of_itemDelete() - 1].payClassificationCode);
                        self.INP_003_name(self.dataSource()[self.index_of_itemDelete() - 1].payClassificationName);
                        self.INP_004_notes(self.dataSource()[self.index_of_itemDelete() - 1].memo);
                    } else {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete()].payClassificationCode)
                        self.INP_002_code(self.dataSource()[self.index_of_itemDelete()].payClassificationCode);
                        self.INP_003_name(self.dataSource()[self.index_of_itemDelete()].payClassificationName);
                        self.INP_004_notes(self.dataSource()[self.index_of_itemDelete()].memo);
                    }
                    dfd.resolve();
                } else { self.initRegisterPayClassification(); }
                dfd.resolve();

            }).fail(function(res) {
                alert(res.message);
            })

            dfd.resolve();
            return dfd.promise();

        }


        getPayClassificationList_first(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {
                self.dataSource(payClassification_arr);
                self.currentCode(self.dataSource()[0].payClassificationCode);
                let i = self.dataSource().length;
                if (i > 0) {
                    self.INP_002_enable(false);
                    self.INP_002_code(self.dataSource()[0].payClassificationCode);
                    self.INP_003_name(self.dataSource()[0].payClassificationName);
                    self.INP_004_notes(self.dataSource()[0].memo);
                    self.isDeleteEnable(true);
                }
                dfd.resolve();
            }).fail(function(res) {
                alert(res.message);
            })
            dfd.resolve();
            return dfd.promise();

        }


        getPayClassificationList(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {
                self.dataSource(payClassification_arr);
                self.INP_002_code(self.dataSource()[0].payClassificationCode);
                self.INP_003_name(self.dataSource()[0].payClassificationName);
                self.INP_004_notes(self.dataSource()[0].memo);


                if (self.dataSource().length > 0) {
                    self.currentCode(self.dataSource()[0].payClassificationCode);
                }
                dfd.resolve();
            }).fail(function(res) {
                alert(res.message);
            })

            dfd.resolve();
            return dfd.promise();

        }

        getPayClassificationList_afterUpdate(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {
                self.dataSource(payClassification_arr);
                if (self.dataSource().length > 1) {
                    self.currentCode(self.updatedata().payClassificationCode);
                    self.INP_002_enable(false);
                }


                dfd.resolve();
            }).fail(function(res) {
                alert(res.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

        getPayClassificationList_afterAdd(): any {
            var self = this;
            var dfd = $.Deferred<any>();

            service.getAllPayClassification().done(function(payClassification_arr: Array<model.PayClassificationDto>) {
                self.dataSource(payClassification_arr);
                self.INP_002_code(self.adddata().payClassificationCode);
                //  self.INP_002_enable = ko.observable(false);
                self.INP_003_name(self.adddata().payClassificationName);
                self.INP_004_notes(self.adddata().memo);
                self.currentCode(self.adddata().payClassificationCode);
                self.INP_002_enable(false);
                self.isDeleteEnable(true);
                self.currentItem(self.adddata());

                dfd.resolve();
            }).fail(function(res) {
                alert(res.message);
            })
            dfd.resolve();
            return dfd.promise();

        }



    }


    export module model {
        export class PayClassificationDto {
            payClassificationCode: string;
            payClassificationName: string;
            memo: string;
            constructor(payClassificationCode: string, payClassificationName: string, memo: string) {
                this.payClassificationCode = payClassificationCode;
                this.payClassificationName = payClassificationName;
                this.memo = memo;
            }
        }

        export class RemovePayClassificationCommand {
            payClassificationCode: string;
            constructor(payClassificationCode: string) {
                this.payClassificationCode = payClassificationCode;
            }

        }
    }


}