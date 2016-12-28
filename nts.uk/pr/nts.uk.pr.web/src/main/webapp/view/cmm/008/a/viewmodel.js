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
                    self.textSearch = {
                        valueSearch: ko.observable(""),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "コード・名称で検索・・・",
                            width: "75%",
                            textalign: "left"
                        }))
                    };
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm008.a || (cmm008.a = {}));
})(cmm008 || (cmm008 = {}));
