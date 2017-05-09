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
                        var option = nts.uk.ui.option;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.refundPaddingThreeModel = new RefundPaddingThreeModel();
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.spliteLineOutput = ko.observableArray([
                                        { code: 0, name: 'する' },
                                        { code: 1, name: 'しない' },
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve(self);
                                    return dfd.promise();
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
                                    this.upperAreaPaddingTop(dto.upperAreaPaddingTop);
                                    this.middleAreaPaddingTop(dto.middleAreaPaddingTop);
                                    this.paddingLeft(dto.paddingLeft);
                                    this.breakLineMarginTop(dto.breakLineMarginTop);
                                    this.breakLineMarginButtom(dto.breakLineMarginButtom);
                                    this.isShowBreakLine(dto.isShowBreakLine);
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