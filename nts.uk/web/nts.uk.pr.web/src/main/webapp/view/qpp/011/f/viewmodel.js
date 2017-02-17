// TreeGrid Node
var qpp011;
(function (qpp011) {
    var f;
    (function (f) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //gridlist data
                self.gridListItems_F_LST_001 = ko.observableArray([
                    new GridItemModel_F_LST_001('001', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬ鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('150', '鬮ｯ貅ｷ遘�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｹ鬯ｮ�ｽ｢�ｿｽ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｷ鬮ｫ�ｽｰ�ｿｽ�ｽｿ�ｽｽ髯ｷ�ｽｿ�ｿｽ�ｽｽ�ｽ･�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｿ�ｿｽ�ｽｽ�ｽｽ'),
                    new GridItemModel_F_LST_001('ABC', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC1', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC2', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC3', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC4', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC5', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC6', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC7', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC8', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC9', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC10', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC11', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ'),
                    new GridItemModel_F_LST_001('ABC12', '鬮ｯ諞ｺ螻ｮ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｺ12鬮ｫ�ｽｴ陝ｷ�ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｬghj鬯ｩ謳ｾ�ｽｽ�ｽｨ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｦ')
                ]);
                self.columns_F_LST_001 = ko.observableArray([
                    { headerText: '鬩幢ｽ｢�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｳ鬩幢ｽ｢隴趣ｽ｢�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｼ鬩幢ｽ｢隴趣ｽ｢�ｿｽ�ｽｽ�ｽｿ�ｿｽ�ｽｽ�ｽｽ', prop: 'code', width: 100 },
                    { headerText: '鬮ｯ�ｽｷ�ｿｽ�ｽｽ�ｽｷ髯ｷ�ｽ･�ｿｽ�ｽｽ�ｽｲ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｧ�ｿｽ�ｽｿ�ｽｽ�ｿｽ�ｽｽ�ｽｽ�ｿｽ�ｽｽ�ｽｰ', prop: 'name', width: 150 }
                ]);
                self.gridListCurrentCode_F_LST_001 = ko.observable();
                self.currentCodeList_F_LST_001 = ko.observableArray([]);
                //currencyeditor
                self.currencyeditor = {
                    value: ko.observable(),
                    constraint: '',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                        grouplength: 3,
                        decimallength: 2,
                        currencyformat: "USD",
                        currencyposition: 'right'
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
                //Switch
                self.enable = ko.observable(true);
                self.roundingRules = ko.observableArray([
                    { code: '1', name: '驍丞�ｺ�ｽｻ莨懶ｿｽ莠･謖ｨ' },
                    { code: '2', name: '陋溷�ｶ�ｽｺ�ｽｺ隴丞ｮ茨ｽｴ�ｽｰ陋ｻ�ｽ･' }
                ]);
                self.selectedRuleCode = ko.observable(1);
            }
            ScreenModel.prototype.submitDialog = function () {
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        f.ScreenModel = ScreenModel;
        var GridItemModel_F_LST_001 = (function () {
            function GridItemModel_F_LST_001(code, name) {
                this.code = code;
                this.name = name;
            }
            return GridItemModel_F_LST_001;
        }());
        f.GridItemModel_F_LST_001 = GridItemModel_F_LST_001;
    })(f = qpp011.f || (qpp011.f = {}));
})(qpp011 || (qpp011 = {}));
;
