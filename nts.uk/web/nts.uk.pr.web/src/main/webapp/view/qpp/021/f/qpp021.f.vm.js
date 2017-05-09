var qpp021;
(function (qpp021) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.myModel = ko.observable(new MyModel());
                    this.splitLineOutput = ko.observableArray([
                        new SelectionModel('1', 'する'),
                        new SelectionModel('2', 'しない')
                    ]);
                }
                /**
                 * Start page.
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                /**
                 * Event when click save button.
                 */
                ScreenModel.prototype.onSaveBtnClicked = function () {
                    var self = this;
                    // Validate.
                    if ($('.nts-input').ntsError('hasError')) {
                        return;
                    }
                    // Save.
                    f.service.save(ko.toJS(self.myModel));
                };
                /**
                 * Event when click close button.
                 */
                ScreenModel.prototype.onCloseBtnClicked = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var MyModel = (function () {
                function MyModel() {
                    this.inp1 = ko.observable('1');
                    this.inp2 = ko.observable('2');
                    this.inp3 = ko.observable('3');
                    this.inp4 = ko.observable('4');
                    this.selectedSplitLineOutput = ko.observable('1');
                }
                return MyModel;
            }());
            viewmodel.MyModel = MyModel;
            /**
             *  Class SelectionModel.
             */
            var SelectionModel = (function () {
                function SelectionModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return SelectionModel;
            }());
            viewmodel.SelectionModel = SelectionModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qpp021.f || (qpp021.f = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.f.vm.js.map