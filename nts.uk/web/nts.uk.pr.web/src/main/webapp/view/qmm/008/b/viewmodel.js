var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
                            var ScreenModel = (function () {
                                function ScreenModel(recivedVal) {
                                    var self = this;
                                    self.getInsuranceOfficeItemDto = ko.observable(recivedVal);
                                    self.returnInsuranceOfficeItemDto = ko.observable(null);
                                    self.listOptions = ko.observableArray([new optionsModel(1, "最新の履歴(2016/04)から引き継ぐ"), new optionsModel(2, "初めから作成する")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.officeCodeName = ko.observable(recivedVal.code + " " + recivedVal.name);
                                    self.addDate = ko.observable('2016/04');
                                }
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    this.getInsuranceOfficeItemDto().childs.push(new InsuranceOfficeItemDto('id', '2016/04 ~ 2017/03', 'chilcode2', []));
                                    this.returnInsuranceOfficeItemDto(this.getInsuranceOfficeItemDto());
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", this.returnInsuranceOfficeItemDto(), true);
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm008.b || (qmm008.b = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
