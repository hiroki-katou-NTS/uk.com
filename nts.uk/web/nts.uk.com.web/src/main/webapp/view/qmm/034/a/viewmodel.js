var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.constraint = 'LayoutCode';
                    this.isUpdate = ko.observable(true);
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: '元号', prop: 'code', width: 50 },
                        { headerText: '記号', prop: 'name', width: 50 },
                        { headerText: '開始年月日', prop: 'startDateText', width: 80 },
                    ]);
                    self.currentCodeList = ko.observableArray([]);
                    self.currentEra = ko.observable(null);
                    self.currentCode = ko.observable();
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentEra(self.getEra(codeChanged));
                        self.date(self.currentEra().startDate);
                    });
                    self.date = ko.observable(new Date());
                    self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.date()).toString());
                    self.eras = ko.observableArray([]);
                    console.log(self.items());
                    self.findIndex = ko.observable(0);
                    self.countItems = ko.observable(0);
                    self.isSelectdFirstRow = ko.observable(true);
                    self.isDeleteEnable = ko.observable(true);
                }
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.currentEra(new EraModel('', '', ''));
                    self.isUpdate = ko.observable(false);
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var eraName;
                    eraName = $('#A_INP_001').val();
                    var eraMark;
                    eraMark = $('#A_INP_002').val();
                    var startDate = self.date();
                    var endDate;
                    var fixAttribute;
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDto(eraName, eraMark, startDate.toDateString(), endDate, fixAttribute);
                    qmm034.a.service.addData(self.isUpdate(false), node).done(function (result) {
                        self.reload().done(function () {
                            dfd.resolve();
                        });
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    if (confirm("do you wanna delete") === true) {
                        self.deleteData();
                    }
                    else {
                        alert("you didnt delete!");
                    }
                };
                ScreenModel.prototype.selectedItem = function (item) {
                    var self = this;
                    self.currentCode(item.code);
                    return new EraModel(item.code, item.name, item.startDate);
                };
                ScreenModel.prototype.reload = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        self.buildGridDataSource(data);
                        self.countItems(data.length);
                        if (data.length > 0) {
                            if (self.isSelectdFirstRow()) {
                                self.currentEra(self.selectedItem(data[0]));
                                self.isSelectdFirstRow(false);
                            }
                            self.items(data);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var eraName;
                    eraName = $('#A_INP_001').val();
                    var eraMark;
                    eraMark = $('#A_INP_002').val();
                    var startDate = self.date();
                    var endDate;
                    var fixAttribute;
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDtoDelete(startDate);
                    qmm034.a.service.deleteData(node).done(function (result) {
                        self.reload().done(function () {
                            dfd.resolve();
                        });
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getEra = function (codeNew) {
                    var self = this;
                    var era = _.find(self.items(), function (item) {
                        return item.code === codeNew;
                    });
                    return _.cloneDeep(era);
                };
                ScreenModel.prototype.update = function () {
                    var self = this;
                };
                ScreenModel.prototype.selectSomeItems = function () {
                    this.currentCode('150');
                    this.currentCodeList.removeAll();
                    this.currentCodeList.push('001');
                    this.currentCodeList.push('ABC');
                };
                ScreenModel.prototype.deselectAll = function () {
                    this.currentCode(null);
                    this.currentCodeList.removeAll();
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        self.buildGridDataSource(data);
                        self.currentEra = ko.observable(_.cloneDeep(_.first(self.items())));
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.buildGridDataSource = function (items) {
                    var self = this;
                    self.items([]);
                    _.forEach(items, function (obj) {
                        self.items.push(new EraModel(obj.eraName, obj.eraMark, obj.startDate));
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var EraModel = (function () {
                function EraModel(code, name, startDate) {
                    this.code = code;
                    this.name = name;
                    if (startDate !== "") {
                        this.startDate = new Date(startDate);
                        this.startDateText = startDate;
                    }
                    else {
                        this.startDate = new Date();
                        this.startDateText = this.startDate.toDateString();
                    }
                }
                return EraModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
//# sourceMappingURL=viewmodel.js.map