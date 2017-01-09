var cmm008;
(function (cmm008) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.employmentCode = ko.observable("");
                    self.employmentName = ko.observable("");
                    self.isCheckbox = ko.observable(true);
                    self.closeDateList = ko.observableArray([]);
                    self.selectedCloseCode = ko.observable(0);
                    self.managementHolidays = ko.observableArray([]);
                    self.holidayCode = ko.observable(1);
                    self.processingDateList = ko.observableArray([]);
                    self.selectedProcessCode = ko.observable(0);
                    self.employmentOutCode = ko.observable("");
                    self.memoValue = ko.observable("");
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dataSource = ko.observableArray([]);
                    self.multilineeditor = {
                        memoValue: ko.observable(''),
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                    };
                }
                // start function
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var heightScreen = $(window).height();
                    var widthScreen = $(window).width();
                    var heightHeader = $('#header').height() + $('#functions-area').height();
                    var height = heightScreen - heightHeader - 75;
                    $('#contents-left').css({ height: height, width: widthScreen * 30 / 100 });
                    $('#contents-right').css({ height: height, width: widthScreen * 70 / 100 });
                    self.closeDateListItem();
                    self.managementHolidaylist();
                    self.processingDateItem();
                    self.dataSourceItem();
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDateListItem = function () {
                    var self = this;
                    self.closeDateList.removeAll();
                    self.closeDateList.push(new ItemCloseDate(0, '0'));
                    self.closeDateList.push(new ItemCloseDate(1, '1'));
                    self.closeDateList.push(new ItemCloseDate(2, '2'));
                };
                ScreenModel.prototype.managementHolidaylist = function () {
                    var self = this;
                    self.managementHolidays = ko.observableArray([
                        { code: 1, name: 'する' },
                        { code: 2, name: 'しない' }
                    ]);
                    self.holidayCode = ko.observable(1);
                };
                ScreenModel.prototype.processingDateItem = function () {
                    var self = this;
                    self.processingDateList.push(new ItemProcessingDate(0, '0'));
                    self.processingDateList.push(new ItemProcessingDate(1, '1'));
                    self.processingDateList.push(new ItemProcessingDate(2, '2'));
                };
                ScreenModel.prototype.dataSourceItem = function () {
                    var self = this;
                    self.dataSource = ko.observableArray([]);
                    for (var i = 1; i < 100; i++) {
                        self.dataSource.push(new ItemModel('00' + i, '基本給', "description " + i, "other" + i));
                    }
                    this.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100 },
                        { headerText: '名称', prop: 'name', width: 150 },
                        { headerText: '説明', prop: 'description', width: 150 },
                        { headerText: '説明1', prop: 'other1', width: 150 },
                        { headerText: '説明2', prop: 'other2', width: 150 }
                    ]);
                    this.currentCode = ko.observable();
                    self.singleSelectedCode = ko.observable(null);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemCloseDate = (function () {
                function ItemCloseDate(closeDateCode, closeDatename) {
                    this.closeDateCode = closeDateCode;
                    this.closeDatename = closeDatename;
                }
                return ItemCloseDate;
            }());
            viewmodel.ItemCloseDate = ItemCloseDate;
            var ItemProcessingDate = (function () {
                function ItemProcessingDate(processingDateCode, processingDatename) {
                    this.processingDateCode = processingDateCode;
                    this.processingDatename = processingDatename;
                }
                return ItemProcessingDate;
            }());
            viewmodel.ItemProcessingDate = ItemProcessingDate;
            var ItemModel = (function () {
                function ItemModel(code, name, description, other1, other2, childs) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                    this.other1 = other1;
                    this.other2 = other2 || other1;
                    this.childs = childs;
                }
                return ItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
