var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dataSource = ko.observableArray([
                        new viewmodel.model.ClassificationDto('A000001', '1���{��'),
                        new viewmodel.model.ClassificationDto('A000002', '2���{��'),
                        new viewmodel.model.ClassificationDto('A000003', '3��12�{'),
                        new viewmodel.model.ClassificationDto('A000004', '4��12�{'),
                        new viewmodel.model.ClassificationDto('A000005', '5��12�{'),
                        new viewmodel.model.ClassificationDto('A000006', '6��12�{'),
                        new viewmodel.model.ClassificationDto('A000007', '7��12�{'),
                        new viewmodel.model.ClassificationDto('A000008', '8��12�{'),
                        new viewmodel.model.ClassificationDto('A000009', '9��12�{'),
                        new viewmodel.model.ClassificationDto('A000010', '10��12�{')
                    ]);
                    self.columns = ko.observableArray([
                        { headerText: '�R�[�h', prop: 'classificationCode', width: 100 },
                        { headerText: '����', prop: 'classificationName', width: 80 }
                    ]);
                    self.currentCode = ko.observable(self.dataSource()[0].classificationCode);
                    self.currentCodeList = ko.observableArray([]);
                    self.currentItem = ko.observable("");
                    self.INP_002_code = ko.observable(self.dataSource()[0].classificationCode);
                    self.INP_002_enable = ko.observable(false);
                    self.INP_003_name = ko.observable(self.dataSource()[0].classificationName);
                    self.INP_004_notes = ko.observable('');
                    self.currentCode.subscribe((function (codeChanged) {
                        self.currentItem(self.findObj(codeChanged));
                        if (self.currentItem() != null) {
                            self.INP_002_code(self.currentItem().classificationCode);
                            self.INP_003_name(self.currentItem().classificationName);
                        }
                    }));
                }
                ScreenModel.prototype.findObj = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.dataSource(), function (obj) {
                        if (obj.classificationCode == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.initRegisterClassification = function () {
                    var self = this;
                    self.INP_002_enable(true);
                    self.INP_002_code("");
                    self.INP_003_name("");
                    self.currentCode(null);
                };
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (self.INP_002_code() == '' || self.INP_003_name() == '') {
                        console.log("input is null");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.RegisterClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkInput()) {
                        console.log("Insert(??ng ky m?i) or Update Classification");
                        for (var i = 0; i < self.dataSource().length; i++) {
                            if (self.INP_002_code() == self.dataSource()[i].classificationCode) {
                                var classification_old = self.dataSource()[i];
                                var classification_update = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name());
                                classification_update.classificationMemo = self.INP_004_notes();
                                a.service.updateClassification(classification_update).done(function () {
                                    a.service.getAllClassification().done(function (classification_arr) {
                                        self.dataSource(classification_arr);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                //                        self.dataSource.replace(classification_old, classification_update);
                                //                        console.log("update");
                                //                        console.log(classification_update);
                                break;
                            }
                            else if (self.INP_002_code() != self.dataSource()[i].classificationCode && i == self.dataSource().length - 1) {
                                var classification_new = new viewmodel.model.ClassificationDto(self.INP_002_code(), self.INP_003_name());
                                classification_new.classificationMemo = self.INP_004_notes();
                                a.service.addClassification(classification_new).done(function () {
                                    a.service.getAllClassification().done(function (classification_arr) {
                                        self.dataSource(classification_arr);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        dfd.reject(res);
                                    });
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                //                        self.dataSource.push(classification_new);
                                //                        console.log("dang ky moi");
                                //                        console.log(classification_new);
                                break;
                            }
                        }
                    }
                };
                ScreenModel.prototype.DeleteClassification = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var item = new model.RemoveClassificationCommand(self.currentItem().classificationCode);
                    a.service.removeClassification(item).done(function () {
                        a.service.getAllClassification().done(function (classification_arr) {
                            self.dataSource(classification_arr);
                            dfd.resolve();
                        }).fail(function (res) {
                            dfd.reject(res);
                        });
                        //   self.dataSource(classification_arr);
                        //  dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    //   self.dataSource.remove(self.currentItem());
                    self.classificationCode = ko.observable("");
                    self.classificationName = ko.observable("");
                    self.classificationMemo = ko.observable("");
                    self.classificationList = ko.observableArray([]);
                    self.selectedClassificationCode = ko.observable(null);
                    self.texteditorcode = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true)
                    };
                    self.texteditorname = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true)
                    };
                    self.multilineeditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllClassification().done(function (classification_arr) {
                        self.dataSource(classification_arr);
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var ClassificationDto = (function () {
                    function ClassificationDto(classificationCode, classificationName) {
                        this.classificationCode = classificationCode;
                        this.classificationName = classificationName;
                    }
                    return ClassificationDto;
                }());
                model.ClassificationDto = ClassificationDto;
                var RemoveClassificationCommand = (function () {
                    function RemoveClassificationCommand(classificationCode) {
                        this.classificationCode = classificationCode;
                    }
                    return RemoveClassificationCommand;
                }());
                model.RemoveClassificationCommand = RemoveClassificationCommand;
            })(model = viewmodel.model || (viewmodel.model = {}));
            var Classification = (function () {
                function Classification(classificationCode, classificationName) {
                    this.classificationCode = classificationCode;
                    this.classificationName = classificationName;
                }
                return Classification;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
