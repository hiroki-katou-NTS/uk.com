var qmm020;
(function (qmm020) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.textSearch = "";
                    this.allowClick = ko.observable(true);
                    var self = this;
                    self.itemList = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(null);
                    self.columns = ko.observableArray([{ headerText: "Item Code", width: "150px", key: 'code', dataType: "string", hidden: false },
                        { headerText: "Item Text", key: 'nodeText', width: "200px", dataType: "string" }]);
                    self.itemName = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.selectedName = ko.observable(null);
                    $('#A_LST_001').on('selectionChanging', function (event) {
                        console.log('Selecting value:' + event.originalEvent.detail);
                    });
                    $('#A_LST_001').on('selectionChanged', function (event) {
                        console.log('Selected value:' + event.originalEvent.detail);
                    });
                    self.start();
                }
                ;
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllotCompanyList().done(function (companyAllots) {
                        if (companyAllots.length > 0) {
                        }
                        else {
                            self.allowClick(false);
                            dfd.resolve();
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openMainLDialog = function () {
                    var self = this;
                    debugger;
                    nts.uk.ui.windows.sub.modal('/view/qmm/020/l/index.xhtml', { title: '利用単位の設定' }).onClosed(function () {
                        self.returnLDialog = ko.observable(nts.uk.ui.windows.getShared('arrSettingVal'));
                        if (self.returnLDialog().split('~')[0] === '2') {
                            $("#sidebar").ntsSideBar("hide", 1);
                            $("#sidebar").ntsSideBar("hide", 2);
                            $("#sidebar").ntsSideBar("hide", 3);
                            $("#sidebar").ntsSideBar("hide", 4);
                            $("#sidebar").ntsSideBar("hide", 5);
                        }
                        else {
                            if (self.returnLDialog().split('~')[1] === '1') {
                                $("#sidebar").ntsSideBar("show", 1);
                                $("#sidebar").ntsSideBar("hide", 2);
                                $("#sidebar").ntsSideBar("hide", 3);
                                $("#sidebar").ntsSideBar("hide", 4);
                                $("#sidebar").ntsSideBar("hide", 5);
                            }
                            ;
                            if (self.returnLDialog().split('~')[1] === '2') {
                                $("#sidebar").ntsSideBar("hide", 1);
                                $("#sidebar").ntsSideBar("show", 2);
                                $("#sidebar").ntsSideBar("hide", 3);
                                $("#sidebar").ntsSideBar("hide", 4);
                                $("#sidebar").ntsSideBar("hide", 5);
                            }
                            ;
                            if (self.returnLDialog().split('~')[1] === '3') {
                                $("#sidebar").ntsSideBar("hide", 1);
                                $("#sidebar").ntsSideBar("hide", 2);
                                $("#sidebar").ntsSideBar("show", 3);
                                $("#sidebar").ntsSideBar("hide", 4);
                                $("#sidebar").ntsSideBar("hide", 5);
                            }
                            ;
                            if (self.returnLDialog().split('~')[1] === '4') {
                                $("#sidebar").ntsSideBar("hide", 1);
                                $("#sidebar").ntsSideBar("hide", 2);
                                $("#sidebar").ntsSideBar("hide", 3);
                                $("#sidebar").ntsSideBar("show", 4);
                                $("#sidebar").ntsSideBar("hide", 5);
                            }
                            ;
                            if (self.returnLDialog().split('~')[1] === '5') {
                                $("#sidebar").ntsSideBar("hide", 1);
                                $("#sidebar").ntsSideBar("hide", 2);
                                $("#sidebar").ntsSideBar("hide", 3);
                                $("#sidebar").ntsSideBar("hide", 4);
                                $("#sidebar").ntsSideBar("show", 5);
                            }
                            ;
                        }
                        ;
                        if (self.returnLDialog().split('~')[2] === '1') {
                            $("#sidebar").ntsSideBar("show", 6);
                        }
                        if (self.returnLDialog().split('~')[2] === '2') {
                            $("#sidebar").ntsSideBar("hide", 6);
                        }
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            var Node = (function () {
                function Node(code, name, childs) {
                    var self = this;
                    self.code = code;
                    self.name = name;
                    self.nodeText = self.code + ' ' + self.name;
                    self.childs = childs;
                    self.custom = 'Random' + new Date().getTime();
                }
                return Node;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm020.a || (qmm020.a = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.a.viewmodel.js.map