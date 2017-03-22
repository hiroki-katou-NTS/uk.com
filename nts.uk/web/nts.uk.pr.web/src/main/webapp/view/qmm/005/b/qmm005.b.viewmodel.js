var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        var ViewModel = (function () {
            function ViewModel() {
                this.date = ko.observable(new Date());
                this.selectedCodes = ko.observableArray([2]);
                this.listboxItem = ko.observableArray([
                    new SelectItem(1, "Option 1"),
                    new SelectItem(2, "Option 2"),
                    new SelectItem(3, "Option 3")
                ]);
                this.tableItems = ko.observableArray([
                    new TableRowItem(1),
                    new TableRowItem(2),
                    new TableRowItem(3),
                    new TableRowItem(4),
                    new TableRowItem(5),
                    new TableRowItem(6),
                    new TableRowItem(7),
                    new TableRowItem(8),
                    new TableRowItem(9),
                    new TableRowItem(10),
                    new TableRowItem(11),
                    new TableRowItem(12)
                ]);
            }
            ViewModel.prototype.toggleColumns = function (item, event) {
                $('.toggle').toggleClass('hidden');
                $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
                ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
                if (!$('.toggle').hasClass('hidden')) {
                    nts.uk.ui.windows.getSelf().setWidth(1190);
                }
                else {
                    nts.uk.ui.windows.getSelf().setWidth(730);
                }
            };
            ViewModel.prototype.showModalDialogC = function (item, event) {
                nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 682, height: 370, title: '処理区分の追加' }).onClosed(function () { });
            };
            ViewModel.prototype.showModalDialogD = function (item, event) {
                nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 682, height: 410, title: '処理区分の追加' })
                    .onClosed(function () {
                    alert('ok');
                });
            };
            ViewModel.prototype.showModalDialogE = function (item, event) {
                nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加' })
                    .onClosed(function () {
                    alert('ok');
                });
            };
            return ViewModel;
        }());
        b.ViewModel = ViewModel;
        var SelectItem = (function () {
            function SelectItem(index, label) {
                this.index = index;
                this.label = label;
            }
            return SelectItem;
        }());
        var TableRowItem = (function () {
            function TableRowItem(index) {
                this.index = index;
            }
            TableRowItem.prototype.toggleCalendar = function (item, event) {
                $(event.currentTarget).parent('div').find('input[type=text]').trigger('click');
            };
            return TableRowItem;
        }());
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
