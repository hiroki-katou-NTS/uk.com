var qmm018;
(function (qmm018) {
    var shr;
    (function (shr) {
        var viewmodelbase;
        (function (viewmodelbase) {
            var ItemModel = (function () {
                function ItemModel(itemCode, itemAbName) {
                    this.itemCode = itemCode;
                    this.itemAbName = itemAbName;
                }
                return ItemModel;
            }());
            viewmodelbase.ItemModel = ItemModel;
            (function (CategoryAtr) {
                CategoryAtr[CategoryAtr["PAYMENT"] = 0] = "PAYMENT";
                CategoryAtr[CategoryAtr["DEDUCTION"] = 1] = "DEDUCTION";
                CategoryAtr[CategoryAtr["PERSONAL_TIME"] = 2] = "PERSONAL_TIME";
                CategoryAtr[CategoryAtr["ARTICLES"] = 3] = "ARTICLES";
                CategoryAtr[CategoryAtr["OTHER"] = 9] = "OTHER";
            })(viewmodelbase.CategoryAtr || (viewmodelbase.CategoryAtr = {}));
            var CategoryAtr = viewmodelbase.CategoryAtr;
            (function (Error) {
                Error[Error["ER001"] = "が入力されていません。"] = "ER001";
                Error[Error["ER007"] = "が選択されていません。"] = "ER007";
                Error[Error["ER010"] = "対象データがありません。"] = "ER010";
            })(viewmodelbase.Error || (viewmodelbase.Error = {}));
            var Error = viewmodelbase.Error;
        })(viewmodelbase = shr.viewmodelbase || (shr.viewmodelbase = {}));
    })(shr = qmm018.shr || (qmm018.shr = {}));
})(qmm018 || (qmm018 = {}));
