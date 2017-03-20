var screenQmm019;
var fadeVisibleCustome = (function () {
    /**
     * Constructor.
     */
    function fadeVisibleCustome() {
    }
    fadeVisibleCustome.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        // Start visible/invisible according to initial value
        var shouldDisplay = valueAccessor();
        $(element).toggle(shouldDisplay);
    };
    fadeVisibleCustome.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
        // On update, fade in/out
        var shouldDisplay = valueAccessor();
        shouldDisplay ? $(element).fadeIn() : $(element).fadeOut();
    };
    return fadeVisibleCustome;
}());
ko.bindingHandlers["fadeVisible"] = new fadeVisibleCustome();
var qmm019;
(function (qmm019) {
    var z;
    (function (z) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.survey = new SurveyViewModel("Binh chon Qua bong vang 2017", 10, ["Messi", "Ronaldo", "Neymar"]);
                //            var products = [
                //                { "ProductID": 1, "Name": "Adjustable Race", "ProductNumber": "AR-5381", "Category": { "ID": 0, "Name": "Food", "Active": true, "Date": "\/Date(1059660800000)\/" } },
                //                { "ProductID": 2, "Name": "Bearing Ball", "ProductNumber": "BA-8327", "Category": { "ID": 1, "Name": "Beverages", "Active": true, "Date": "\/Date(1159660800000)\/" } },
                //                { "ProductID": 3, "Name": "BB Ball Bearing", "ProductNumber": "BE-2349", "Category": { "ID": 1, "Name": "Beverages", "Active": true, "Date": "\/Date(1259660800000)\/" } },
                //                { "ProductID": 4, "Name": "Headset Ball Bearings", "ProductNumber": "BE-2908", "Category": { "ID": 1, "Name": "Beverages", "Active": true, "Date": "\/Date(1359660800000)\/" } },
                //                { "ProductID": 316, "Name": "Blade", "ProductNumber": "BL-2036", "Category": { "ID": 1, "Name": "Beverages", "Active": false, "Date": "\/Date(1459660800000)\/" } },
                //                { "ProductID": 317, "Name": "LL Crankarm", "ProductNumber": "CA-5965", "Category": { "ID": 1, "Name": "Beverages", "Active": false, "Date": "\/Date(1559660800000)\/" } },
                //                { "ProductID": 318, "Name": "ML Crankarm", "ProductNumber": "CA-6738", "Category": { "ID": 1, "Name": "Beverages", "Active": false, "Date": "\/Date(1659660800000)\/" } },
                //                { "ProductID": 319, "Name": "HL Crankarm", "ProductNumber": "CA-7457", "Category": { "ID": 2, "Name": "Electronics", "Active": false, "Date": "\/Date(1759660800000)\/" } },
                //                { "ProductID": 320, "Name": "Chainring Bolts", "ProductNumber": "CB-2903", "Category": { "ID": 2, "Name": "Electronics", "Active": false, "Date": "\/Date(1859660800000)\/" } }
                //           ];
                //           $("#grid").igGrid({
                //               columns: [
                //                   { headerText: "Product ID", key: "ProductID", dataType: "number" },
                //                   { headerText: "Product Name", key: "Name", dataType: "string" },
                //                   { headerText: "Product Number", key: "ProductNumber", dataType: "string" }
                //               ],
                //               width: "500px",
                //               dataSource: products
                //           });
            }
            return ScreenModel;
        }());
        z.ScreenModel = ScreenModel;
        var Answer = (function () {
            function Answer(text) {
                this.text = text;
                this.points = ko.observable(1);
            }
            return Answer;
        }());
        var SurveyViewModel = (function () {
            function SurveyViewModel(question, pointsBudget, answers) {
                var self = this;
                self.question = question;
                self.pointsBudget = pointsBudget;
                self.answers = $.map(answers, function (text) {
                    return new Answer(text);
                });
                self.pointsUsed = ko.computed(function () {
                    var total = 0;
                    for (var _i = 0, _a = self.answers; _i < _a.length; _i++) {
                        var answer = _a[_i];
                        total += answer.points();
                    }
                    return total;
                }, self);
                self.enableSave = ko.computed(function () {
                    return self.pointsUsed() - self.pointsBudget <= 0;
                }, this);
            }
            SurveyViewModel.prototype.save = function () {
                alert("to do");
            };
            return SurveyViewModel;
        }());
    })(z = qmm019.z || (qmm019.z = {}));
})(qmm019 || (qmm019 = {}));
;
