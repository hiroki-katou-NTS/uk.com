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
                    var g;
                    (function (g) {
                        var RefundPaddingThreeDto = g.service.model.RefundPaddingThreeDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.refundPaddingThreeModel = new RefundPaddingThreeModel();
                                    self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.spliteLineOutput = ko.observableArray([
                                        { code: 0, name: 'する' },
                                        { code: 1, name: 'しない' },
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    g.service.findRefundPadding().done(function (data) {
                                        self.refundPaddingThreeModel.updateDataDto(data);
                                        dfd.resolve(self);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.saveRefundPaddingThree = function () {
                                    var self = this;
                                    if ($('.nts-input').ntsError('hasError')) {
                                        return;
                                    }
                                    g.service.saveRefundPadding(self.refundPaddingThreeModel.toDto());
                                };
                                ScreenModel.prototype.closeRefundPaddingThree = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var RefundPaddingThreeModel = (function () {
                                function RefundPaddingThreeModel() {
                                    this.upperAreaPaddingTop = ko.observable(0);
                                    this.middleAreaPaddingTop = ko.observable(0);
                                    this.underAreaPaddingTop = ko.observable(0);
                                    this.paddingLeft = ko.observable(0);
                                    this.breakLineMarginTop = ko.observable(0);
                                    this.breakLineMarginButtom = ko.observable(0);
                                    this.isShowBreakLine = ko.observable(0);
                                }
                                RefundPaddingThreeModel.prototype.updateDataDto = function (dto) {
                                    this.upperAreaPaddingTop(dto.underAreaPaddingTop);
                                    this.middleAreaPaddingTop(dto.middleAreaPaddingTop);
                                    this.underAreaPaddingTop(dto.underAreaPaddingTop);
                                    this.paddingLeft(dto.paddingLeft);
                                    this.breakLineMarginTop(dto.breakLineMarginTop);
                                    this.breakLineMarginButtom(dto.breakLineMarginButtom);
                                    this.isShowBreakLine(dto.isShowBreakLine);
                                };
                                RefundPaddingThreeModel.prototype.toDto = function () {
                                    var dto;
                                    dto = new RefundPaddingThreeDto();
                                    dto.upperAreaPaddingTop = this.upperAreaPaddingTop();
                                    dto.middleAreaPaddingTop = this.middleAreaPaddingTop();
                                    dto.underAreaPaddingTop = this.underAreaPaddingTop();
                                    dto.paddingLeft = this.paddingLeft();
                                    dto.breakLineMarginTop = this.breakLineMarginTop();
                                    dto.breakLineMarginButtom = this.breakLineMarginButtom();
                                    dto.isShowBreakLine = this.isShowBreakLine();
                                    return dto;
                                };
                                return RefundPaddingThreeModel;
                            }());
                            viewmodel.RefundPaddingThreeModel = RefundPaddingThreeModel;
                        })(viewmodel = g.viewmodel || (g.viewmodel = {}));
                    })(g = qpp021.g || (qpp021.g = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.g.vm.js.map