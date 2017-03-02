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
        index_of_itemDelete: any;

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




            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findObj(codeChanged));
                if (self.currentItem() != null) {
                    self.INP_002_code(self.currentItem().classificationCode);
                    self.INP_003_name(self.currentItem().classificationName);
                    self.INP_004_notes(self.currentItem().memo);
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
        }

        checkInput(): boolean {
            var self = this;
            if (self.INP_002_code() == '' || self.INP_003_name() == '') {
                console.log("input is null");
                return false;
            } else {
                return true;
            }
        }

        RegisterClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            if (self.checkInput()) {
                console.log("Insert(đăng ký mới) or Update Classification");
                for (let i = 0; i < self.dataSource().length; i++) {
                    if (self.INP_002_code() == self.dataSource()[i].classificationCode) {
                        var classification_old = self.dataSource()[i];
                        var classification_update = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.updateClassification(classification_update).done(function() {
                            self.getClassificationList();
                        }).fail(function(res) {
                            dfd.reject(res);
                        })
                        break;
                    } else if (self.INP_002_code() != self.dataSource()[i].classificationCode && i == self.dataSource().length - 1) {
                        var classification_new = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name(), self.INP_004_notes());
                        service.addClassification(classification_new).done(function() {
                            self.getClassificationList();
                        }).fail(function(res) {
                            dfd.reject(res);
                        })
                        break;
                    }

                }
            }
        }

        DeleteClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            var item = new model.RemoveClassificationCommand(self.currentItem().classificationCode);
            self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
            console.log(self.index_of_itemDelete);
            service.removeClassification(item).done(function(res) {
                self.getClassificationList_aftefDelete();
            }).fail(function(res) {
                dfd.reject(res);
            })

        }
        //   self.dataSource.remove(self.currentItem());

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                self.dataSource(classification_arr);
                self.INP_002_code(self.dataSource()[0].classificationCode);
                self.INP_003_name(self.dataSource()[0].classificationName);
                self.INP_004_notes(self.dataSource()[0].memo);
                self.currentCode(self.dataSource()[0].classificationCode)
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
                if (self.dataSource().length > 1) {
                    self.currentCode(self.dataSource()[0].classificationCode)
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

        }

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