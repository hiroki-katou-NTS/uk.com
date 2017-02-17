module cmm014.a.viewmodel {

    export class ScreenModel {

        dataSource: KnockoutObservableArray<viewmodel.model.ClassificationDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode: KnockoutObservable<string>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<viewmodel.model.ClassificationDto>;

        INP_002_code: any;
        INP_002_enable: any;
        INP_003_name: any;
        INP_004_notes: any;

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

            self.INP_002_code = ko.observable(null);
            self.INP_002_enable = ko.observable(false);
            self.INP_003_name = ko.observable(null);
            self.INP_004_notes = ko.observable(null);


            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findObj(codeChanged));
                if (self.currentItem() != null) {
                    self.INP_002_code(self.currentItem().classificationCode);
                    self.INP_003_name(self.currentItem().classificationName);
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
            self.currentCode(null);
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
                        var classification_update = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name());
                        classification_update.classificationMemo = self.INP_004_notes();
                        service.updateClassification(classification_update).done(function() {
                            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                                self.dataSource(classification_arr);
                                dfd.resolve();
                            }).fail(function(res) {
                                dfd.reject(res);
                            })
                            dfd.resolve();
                        }).fail(function(res) {
                            dfd.reject(res);
                        })

                        //                        self.dataSource.replace(classification_old, classification_update);
                        //                        console.log("update");
                        //                        console.log(classification_update);
                        break;
                    } else if (self.INP_002_code() != self.dataSource()[i].classificationCode && i == self.dataSource().length - 1) {
                        var classification_new = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name());
                        classification_new.classificationMemo = self.INP_004_notes();

                        service.addClassification(classification_new).done(function() {
                            service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                                self.dataSource(classification_arr);
                                dfd.resolve();
                            }).fail(function(res) {
                                dfd.reject(res);
                            })
                            dfd.resolve();
                        }).fail(function(res) {
                            dfd.reject(res);
                        })
                        //                        self.dataSource.push(classification_new);
                        //                        console.log("dang ky moi");
                        //                        console.log(classification_new);
                        break;
                    }

                }
            }
        }

        DeleteClassification() {
            var self = this;
            var dfd = $.Deferred<any>();
            var item = new model.RemoveClassificationCommand(self.currentItem().classificationCode);
            service.removeClassification(item).done(function() {

                service.getAllClassification().done(function(classification_arr: Array<model.ClassificationDto>) {
                    self.dataSource(classification_arr);
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);
                })

                //   self.dataSource(classification_arr);
                //  dfd.resolve();
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

                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);
            })
            dfd.resolve();
            return dfd.promise();
        }
    }


    export module model {
        export class ClassificationDto {
            classificationCode: string;
            classificationName: string;
            classificationMemo: string;
            constructor(classificationCode: string, classificationName: string) {
                this.classificationCode = classificationCode;
                this.classificationName = classificationName;
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