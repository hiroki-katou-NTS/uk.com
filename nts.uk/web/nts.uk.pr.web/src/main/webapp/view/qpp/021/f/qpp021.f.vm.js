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
                    var f;
                    (function (f) {
                        var RefundPaddingTwoDto = f.service.model.RefundPaddingTwoDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.refundPaddingTwoModel = new RefundPaddingTwoModel();
                                    self.splitLineOutput = ko.observableArray([
                                        new SelectionModel('0', 'する'),
                                        new SelectionModel('1', 'しない')
                                    ]);
                                    self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    f.service.findRefundPadding().done(function (data) {
                                        self.refundPaddingTwoModel.updateDataDto(data);
                                        dfd.resolve(self);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.onSaveBtnClicked = function () {
                                    var self = this;
                                    if ($('.nts-input').ntsError('hasError')) {
                                        return;
                                    }
                                    f.service.saveRefundPadding(self.refundPaddingTwoModel.toDto());
                                };
                                ScreenModel.prototype.onCloseBtnClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var RefundPaddingTwoModel = (function () {
                                function RefundPaddingTwoModel() {
                                    this.upperAreaPaddingTop = ko.observable(0);
                                    this.underAreaPaddingTop = ko.observable(0);
                                    this.paddingLeft = ko.observable(0);
                                    this.breakLineMargin = ko.observable(0);
                                    this.isShowBreakLine = ko.observable(0);
                                }
                                RefundPaddingTwoModel.prototype.updateDataDto = function (dto) {
                                    this.upperAreaPaddingTop(dto.upperAreaPaddingTop);
                                    this.underAreaPaddingTop(dto.underAreaPaddingTop);
                                    this.paddingLeft(dto.paddingLeft);
                                    this.breakLineMargin(dto.breakLineMargin);
                                    this.isShowBreakLine(dto.isShowBreakLine);
                                };
                                RefundPaddingTwoModel.prototype.toDto = function () {
                                    var dto;
                                    dto = new RefundPaddingTwoDto();
                                    dto.upperAreaPaddingTop = this.underAreaPaddingTop();
                                    dto.underAreaPaddingTop = this.underAreaPaddingTop();
                                    dto.paddingLeft = this.paddingLeft();
                                    dto.breakLineMargin = this.breakLineMargin();
                                    dto.isShowBreakLine = this.isShowBreakLine();
                                    return dto;
                                };
                                return RefundPaddingTwoModel;
                            }());
                            viewmodel.RefundPaddingTwoModel = RefundPaddingTwoModel;
                            var SelectionModel = (function () {
                                function SelectionModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return SelectionModel;
                            }());
                            viewmodel.SelectionModel = SelectionModel;
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qpp021.f || (qpp021.f = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.f.vm.js.map