module cmm014.a.viewmodel {

    export class ScreenModel {

        dataSource: KnockoutObservableArray<viewmodel.model.ClassificationDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<string>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<viewmodel.model.ClassificationDto>;
        INP_002_code: KnockoutObservable<string>;
        INP_002_enable: KnockoutObservable<boolean>;
        INP_003_name: KnockoutObservable<string>;
        INP_004_notes: KnockoutObservable<string>;
        multilineeditor: KnockoutObservable<any>;
        hasCellphone: KnockoutObservable<boolean>;
        index_of_itemDelete: any;
        itemdata_add: any;
        itemdata_update: any;

        constructor() {
            var self = this;
            self.dataSource = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'classificationCode', width: 100 },
                { headerText: '名称', key: 'classificationName', width: 80 }

            ]);
            self.currentCode = ko.observable(null);
            self.currentCodeList = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.multilineeditor = ko.observable(null);
            self.INP_002_code = ko.observable(null);
            self.INP_002_enable = ko.observable(false);
            self.INP_003_name = ko.observable(null);
            self.INP_004_notes = ko.observable(null);
            self.itemdata_add = ko.observable(null);
            self.itemdata_update = ko.observable(null);
            self.hasCellphone = ko.observable(true);



            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findObj(codeChanged));
                if (self.currentItem() != null) {
                    self.INP_002_code(self.currentItem().classificationCode);
                    self.INP_002_enable(false);
                    self.INP_003_name(self.currentItem().classificationName);
                    self.INP_004_notes(self.currentItem().memo);
                    self.hasCellphone(true);
                }
            }));




        }

        findObj(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.dataSource(), function(obj: viewmodel.model.ClassificationDto) {
                if (obj.classificationCode == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }

        initRegisterClassification() {
            var self = this;
            self.INP_002_enable(true);
            self.INP_002_code("");
            self.INP_003_name("");
            self.INP_004_notes("");
            self.currentCode(null);
            $("#test input").val("");
            $("#A_INP_002").focus();
            self.hasCellphone(false);
        }

        checkInput(): boolean {
            var self = this;
            if (!self.INP_002_code()) {
                alert("コードが入力されていません。");

                return false;
            } else if (!self.INP_003_name()) {
                alert("名称が入力されていません。");
                return false;
            }
            return true;
        }

        RegisterClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            if (self.checkInput()) {
                if (self.dataSource().length === 0) {
                    let classification = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                    service.addClassification(classification).done(function() {
                        self.getClassificationList_first();
                    }).fail(function(res) {
                        if (res.message == "ER05") {
                            alert("入力したコードは既に存在しています。\r\n コードを確認してください。 ");
                        }
                        dfd.reject(res);
                    })
                }
                for (let i = 0; i < self.dataSource().length; i++) {
                    if (self.INP_002_code() == self.dataSource()[i].classificationCode && self.INP_002_enable() == false) {
                        var classification_old = self.dataSource()[i];
                        var classification_update = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.updateClassification(classification_update).done(function() {
                            self.itemdata_update(classification_update);
                            self.getClassificationList_afterUpdateClassification();
                        }).fail(function(res) {
                            if (res.message == "ER026") {
                                alert("更新対象のデータが存在しません。");
                            }
                            dfd.reject(res);
                        })
                        break;
                    } else if (self.INP_002_code() != self.dataSource()[i].classificationCode
                        && i == self.dataSource().length - 1
                        && self.INP_002_enable() == true) {
                        var classification_new = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.addClassification(classification_new).done(function() {
                            self.itemdata_add(classification_new);
                            self.getClassificationList_afterAddClassification();
                        }).fail(function(res) {
                            if (res.message == "ER05") {
                                alert("入力したコードは既に存在しています。\r\n コードを確認してください。");
                            }
                            dfd.reject(res);
                        })
                        break;
                    } else if (self.INP_002_code() == self.dataSource()[i].classificationCode && self.INP_002_enable() == true) {
                        alert("入力したコードは既に存在しています。\r\n コードを確認してください。  ");
                        break;
                    }
                }
                self.hasCellphone(true);
            }
        }

        DeleteClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            if (self.dataSource().length > 0) {
                var item = new model.RemoveClassificationCommand(self.currentItem().classificationCode);
                self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    service.removeClassification(item).done(function(res) {
                        self.getClassificationList_aftefDelete();
                    }).fail(function(res) {
                        if (res.message == "ER06") {
                            alert("対象データがありません。");
                        }
                        dfd.reject(res);
                    })
                }).ifNo(function() {

                });

            } else {

            }


        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                if (classification_arr.length > 0) {
                    self.dataSource(classification_arr);
                    self.INP_002_code(self.dataSource()[0].classificationCode);
                    self.INP_003_name(self.dataSource()[0].classificationName);
                    self.INP_004_notes(self.dataSource()[0].memo);
                    self.currentCode(self.dataSource()[0].classificationCode)
                } else if (classification_arr.length === 0) {
                    self.INP_002_enable(true);
                    $("#A_INP_002").focus();
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();
        }

        getClassificationList(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);
                self.INP_002_code(self.dataSource()[0].classificationCode);
                self.INP_003_name(self.dataSource()[0].classificationName);
                self.INP_004_notes(self.dataSource()[0].memo);
                if (self.dataSource().length > 0) {
                    self.currentCode(self.dataSource()[0].classificationCode);
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }
        // get list Classification after insert
        getClassificationList_afterUpdateClassification(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);

                if (self.dataSource().length > 1) {
                    self.currentCode(self.itemdata_update().classificationCode);
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }
        // get list Classification after insert
        getClassificationList_first(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);
                self.currentCode(self.dataSource()[0].classificationCode);
                let i = self.dataSource().length;
                if (i > 0) {
                    self.INP_002_enable(false);
                    self.INP_002_code(self.dataSource()[0].classificationCode);
                    self.INP_003_name(self.dataSource()[0].classificationName);
                    self.INP_004_notes(self.dataSource()[0].memo);
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }


        // get list Classification after insert
        getClassificationList_afterAddClassification(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);
                self.INP_002_code(self.dataSource()[0].classificationCode);
                self.INP_002_enable(false);
                self.INP_003_name(self.dataSource()[0].classificationName);
                self.INP_004_notes(self.dataSource()[0].memo);
                self.currentCode(self.itemdata_add().classificationCode);
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

        // get list Classification after remove
        getClassificationList_aftefDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);

                if (self.dataSource().length > 0) {
                    if (self.index_of_itemDelete === self.dataSource().length) {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].classificationCode)
                        self.INP_002_code(self.dataSource()[self.index_of_itemDelete - 1].classificationCode);
                        self.INP_003_name(self.dataSource()[self.index_of_itemDelete - 1].classificationName);
                        self.INP_004_notes(self.dataSource()[self.index_of_itemDelete - 1].memo);
                    } else {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete].classificationCode)
                        self.INP_002_code(self.dataSource()[self.index_of_itemDelete].classificationCode);
                        self.INP_003_name(self.dataSource()[self.index_of_itemDelete].classificationName);
                        self.INP_004_notes(self.dataSource()[self.index_of_itemDelete].memo);
                    }

                } else {
                    self.initRegisterClassification();
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }


    }

    /**
    *  model
    */
    export module model {
        export class ClassificationDto {
            classificationCode: string;
            classificationName: string;
            memo: string;
            constructor(classificationCode: string, classificationName: string, memo: string) {
                this.classificationCode = classificationCode;
                this.classificationName = classificationName;
                this.memo = memo;
            }
        }

        export class RemoveClassificationCommand {
            classificationCode: string;
            constructor(classificationCode: string) {
                this.classificationCode = classificationCode;
            }

        }
    }


}