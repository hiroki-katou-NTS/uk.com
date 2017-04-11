var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var postcode;
                    (function (postcode_1) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.postCodeList = ko.observableArray([]);
                                    self.addressList = ko.observableArray([]);
                                    self.zipCode = ko.observable('');
                                    var strZipCode = nts.uk.ui.windows.getShared('zipCode');
                                    self.columns = ko.observableArray([
                                        { headerText: '郵便番号', prop: 'postcode', width: 230 },
                                        { headerText: 'カナ', prop: 'kana', width: 240 }
                                    ]);
                                    postcode_1.service.findPostCodeZipCode(strZipCode).done(function (data) {
                                        var postcodeList;
                                        postcodeList = [];
                                        for (var _i = 0, data_1 = data; _i < data_1.length; _i++) {
                                            var itemData = data_1[_i];
                                            postcodeList.push(new PostCodeModel(itemData));
                                        }
                                        self.postCodeList(postcodeList);
                                        self.addressList(data);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                }
                                ScreenModel.prototype.chooseZipCode = function () {
                                    var self = this;
                                    for (var _i = 0, _a = self.addressList(); _i < _a.length; _i++) {
                                        var itemZipCode = _a[_i];
                                        if (itemZipCode.postcode == self.zipCode()) {
                                            nts.uk.ui.windows.setShared('zipCodeRes', itemZipCode);
                                            break;
                                        }
                                    }
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var PostCodeModel = (function () {
                                function PostCodeModel(postcode) {
                                    this.postcode = postcode.postcode;
                                    this.id = postcode.id;
                                    this.kana = postcode_1.service.toKana(postcode);
                                }
                                return PostCodeModel;
                            }());
                            viewmodel.PostCodeModel = PostCodeModel;
                        })(viewmodel = postcode_1.viewmodel || (postcode_1.viewmodel = {}));
                    })(postcode = base.postcode || (base.postcode = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
