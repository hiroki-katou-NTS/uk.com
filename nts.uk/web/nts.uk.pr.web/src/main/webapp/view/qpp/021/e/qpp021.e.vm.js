var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var e;
                    (function (e) {
                        var RefundPaddingOnceDto = e.service.model.RefundPaddingOnceDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.refundPaddingOnceModel = new RefundPaddingOnceModel();
                                    self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    e.service.findRefundPadding().done(function (data) {
                                        self.refundPaddingOnceModel.updateDataDto(data);
                                        dfd.resolve(self);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.preview = function () {
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if ($('.nts-input').ntsError('hasError')) {
                                        return;
                                    }
                                    e.service.saveRefundPadding(self.refundPaddingOnceModel.toDto()).done(function () {
                                        self.close();
                                    }).fail(function (error) {
                                        self.close();
                                    });
                                };
                                ScreenModel.prototype.close = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var RefundPaddingOnceModel = (function () {
                                function RefundPaddingOnceModel() {
                                    this.paddingTop = ko.observable(0);
                                    this.paddingLeft = ko.observable(0);
                                }
                                RefundPaddingOnceModel.prototype.updateDataDto = function (dto) {
                                    this.paddingTop(dto.paddingTop);
                                    this.paddingLeft(dto.paddingLeft);
                                };
                                RefundPaddingOnceModel.prototype.toDto = function () {
                                    var dto;
                                    dto = new RefundPaddingOnceDto();
                                    dto.paddingTop = this.paddingTop();
                                    dto.paddingLeft = this.paddingLeft();
                                    return dto;
                                };
                                return RefundPaddingOnceModel;
                            }());
                            viewmodel.RefundPaddingOnceModel = RefundPaddingOnceModel;
                        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
                    })(e = qpp021.e || (qpp021.e = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.e.vm.js.map