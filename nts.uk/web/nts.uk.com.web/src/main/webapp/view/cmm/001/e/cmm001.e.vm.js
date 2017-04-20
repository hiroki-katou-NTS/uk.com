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
                            class ScreenModel {
                                constructor() {
                                    var self = this;
                                    self.postCodeList = ko.observableArray([]);
                                    self.addressList = ko.observableArray([]);
                                    self.zipCode = ko.observable('');
                                    var strZipCode = nts.uk.ui.windows.getShared('zipCode');
                                    self.columns = ko.observableArray([
                                        { headerText: '郵便番号', prop: 'postcode', width: 100, hidden: true },
                                        { headerText: '住所', prop: 'address', width: 245 },
                                        { headerText: 'カナ', prop: 'kana', width: 220 }
                                    ]);
                                    postcode_1.service.findPostCodeZipCode(strZipCode).done(data => {
                                        var postcodeList;
                                        postcodeList = [];
                                        for (var itemData of data) {
                                            postcodeList.push(new PostCodeModel(itemData));
                                        }
                                        self.postCodeList(postcodeList);
                                        self.addressList(data);
                                    }).fail(function (error) {
                                        console.log(error);
                                    });
                                }
                                chooseZipCode() {
                                    var self = this;
                                    for (var itemZipCode of self.addressList()) {
                                        if (itemZipCode.postcode == self.zipCode()) {
                                            nts.uk.ui.windows.setShared('zipCodeRes', itemZipCode);
                                            break;
                                        }
                                    }
                                    nts.uk.ui.windows.close();
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            class PostCodeModel {
                                constructor(postcode) {
                                    this.address = postcode_1.service.toAddress(postcode);
                                    this.postcode = postcode.postcode;
                                    this.id = postcode.id;
                                    this.kana = postcode_1.service.toKana(postcode);
                                }
                            }
                            viewmodel.PostCodeModel = PostCodeModel;
                        })(viewmodel = postcode_1.viewmodel || (postcode_1.viewmodel = {}));
                    })(postcode = base.postcode || (base.postcode = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
