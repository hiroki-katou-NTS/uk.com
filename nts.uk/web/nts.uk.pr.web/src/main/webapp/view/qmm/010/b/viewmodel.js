var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.employmentName = ko.observable("");
                                    self.textSearch = {
                                        valueSearch: ko.observable(""),
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "コード・名称で検索・・・",
                                            width: "340",
                                            textalign: "left"
                                        }))
                                    };
                                    self.selectLstSocialInsuranceOffice = ko.observableArray([]);
                                    self.columnsLstSocialInsuranceOffice = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 120 },
                                        { headerText: '名称', prop: 'name', width: 120 }
                                    ]);
                                    self.multilineeditor = {
                                        value: ko.observable(''),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                                            resizeable: true,
                                            placeholder: "Placeholder for text editor",
                                            width: "",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(true),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.findAllInsuranceOffice().done(function (data) {
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.findAllInsuranceOffice = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    b.service.findAllSocialInsuranceOffice().done(function (data) {
                                        self.lstSocialInsuranceOffice = ko.observableArray(data);
                                        dfd.resolve(self);
                                    });
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm010.b || (qmm010.b = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
