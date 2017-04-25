var qmm020;
(function (qmm020) {
    var i;
    (function (i_1) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.count = 10;
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.items = ko.observableArray([]);
                    for (var i_2 = 1; i_2 < 10; i_2++) {
                        this.items.push(new ItemModel('00' + i_2, '基本給', "description " + i_2, i_2 % 3 === 0, "other 1 " + i_2, "other2 " + i_2));
                    }
                    this.columns2 = ko.observableArray([
                        { headerText: 'コード', key: 'code', width: 100 },
                        { headerText: '名称', key: 'name', width: 150 },
                        { headerText: '給与明細書', key: 'description', width: 150 },
                        { headerText: '賞与明細書', key: 'other1', width: 150 },
                        { headerText: '適用されている設定', key: 'other2', width: 150 },
                    ]);
                    //Delegate 
                    //            $(document).delegate("#single-list", "iggriddatarendered", function(evt, ui) {
                    //
                    //                $('.error').parent().css({ 'background-color': 'rgba(253, 159, 159, 0.44)' });
                    //
                    //            })
                    //close dialog
                    this.currentCode = ko.observable();
                    this.currentCodeList = ko.observableArray([]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    i_1.service.getPaymentDateProcessingList().done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = i_1.viewmodel || (i_1.viewmodel = {}));
    })(i = qmm020.i || (qmm020.i = {}));
})(qmm020 || (qmm020 = {}));
var ItemModel = (function () {
    //  template: string;  
    function ItemModel(code, name, description, deletable, other1, other2) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.other1 = other1;
        this.other2 = other2 || other1;
        this.deletable = deletable;
        if (deletable) {
            this.other1 = "<div class ='error'>" + other1 + "</div>";
            this.other2 = "<div class ='error'>" + other2 + "</div>";
            this.description = "<div class ='error'>" + description + "</div>";
        }
        else {
            this.other1 = "<div class ='no-error'>" + other1 + "</div>";
            this.other2 = "<div class ='no-error'>" + other2 + "</div>";
            this.description = "<div class ='no-error'>" + description + "</div>";
        }
    }
    return ItemModel;
}());
