var cmm013;
(function (cmm013) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                }
                /**
                 * Init screen model.
                 */
                //        constructor() {
                //            var self = this;
                //            self.selectLayoutAtr = ko.observable("3");
                //            self.itemList = ko.observableArray([]);
                //            self.isEnable = ko.observable(true);
                //            self.selectStmtCode = ko.observable(null);
                //            self.selectStmtName = ko.observable(null);
                //            self.selectStartYm =  ko.observable(null);
                //            self.layoutSelect = ko.observable(nts.uk.ui.windows.getShared('stmtCode'));
                //            self.valueSel001 = ko.observable("");
                //            self.startYmHis = ko.observable(null);
                //            console.log(option);
                //            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({inputFormat: "yearmonth"}));
                //            //---radio
                //            self.itemsRadio = ko.observableArray([
                //                {value: 1, text: ko.observable('譛譁ｰ縺ｮ螻･豁ｴ・・+self.selectStartYm()+'・峨°繧牙ｼ輔″邯吶＄')},
                //                {value: 2, text: ko.observable('蛻昴ａ縺九ｉ菴懈・縺吶ｋ')}
                //            ]);
                //            self.isRadioCheck = ko.observable(1);
                //        } 
                //      
                //            
                //            
                //        
                //        buildItemList(): any{
                //            var self = this;
                //            self.itemList.removeAll();
                //            self.itemList.push(new ItemModel('0','繝ｬ繝ｼ繧ｶ繝ｼ繝励Μ繝ｳ繧ｿ', '・｡・・, '邵ｦ蜷代″','1莠ｺ','譛螟ｧ縲30陦鯉ｽ・蛻･縺ｾ縺ｧ險ｭ螳壼庄閭ｽ',''));
                //            self.itemList.push(new ItemModel('1','繝ｬ繝ｼ繧ｶ繝ｼ繝励Μ繝ｳ繧ｿ', '・｡・・, '邵ｦ蜷代″','譛螟ｧ2莠ｺ','譛螟ｧ縲17陦鯉ｽ・蛻･縺ｾ縺ｧ險ｭ螳壼庄閭ｽ',''));
                //            self.itemList.push(new ItemModel('2','繝ｬ繝ｼ繧ｶ繝ｼ繝励Μ繝ｳ繧ｿ', '・｡・・, '邵ｦ蜷代″','譛螟ｧ3莠ｺ','譛螟ｧ縲10陦鯉ｽ・蛻･縺ｾ縺ｧ險ｭ螳壼庄閭ｽ',''));
                //            self.itemList.push(new ItemModel('3','繝ｬ繝ｼ繧ｶ繝ｼ繝励Μ繝ｳ繧ｿ', '・｡・・, '讓ｪ蜷代″','譛螟ｧ2莠ｺ','譛螟ｧ縲10陦鯉ｽ・蛻･縺ｾ縺ｧ險ｭ螳壼庄閭ｽ',''));
                //            self.itemList.push(new ItemModel('4','繝ｬ繝ｼ繧ｶ繝ｼ・亥悸逹蠑擾ｼ・, '・｡・・, '邵ｦ蜷代″','1莠ｺ','譛螟ｧ縲17陦鯉ｽ・蛻･縺ｾ縺ｧ險ｭ螳壼庄閭ｽ','蝨ｧ逹蠑擾ｼ壹・ｺ謚倥ｊ'));
                //            self.itemList.push(new ItemModel('5','繝ｬ繝ｼ繧ｶ繝ｼ・亥悸逹蠑擾ｼ・, '・｡・・, '讓ｪ蜷代″','1莠ｺ','謾ｯ邨ｦ縲∵而髯､縲∝共諤蜷・2鬆・岼','蝨ｧ逹蠑擾ｼ壹縺ｯ縺後″'));
                //            self.itemList.push(new ItemModel('6','繝峨ャ繝医・繝ｪ繝ｳ繧ｿ', '騾｣邯夂畑邏・, '窶・,'1莠ｺ','謾ｯ邨ｦ縲∵而髯､縲∝共諤蜷・7鬆・岼',''));
                //        }
                //        
                //       
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
         * Class Item model.
         */
            var ItemModel = (function () {
                function ItemModel(stt, printType, paperType, direction, numberPeople, numberDisplayItems, reference) {
                    this.stt = stt;
                    this.printType = printType;
                    this.paperType = paperType;
                    this.direction = direction;
                    this.numberPeople = numberPeople;
                    this.numberDisplayItems = numberDisplayItems;
                    this.reference = reference;
                }
                return ItemModel;
            }());
            viewmodel.ItemModel = ItemModel;
            var ItemCombobox = (function () {
                function ItemCombobox(layoutCode, layoutName) {
                    this.layoutCode = layoutCode;
                    this.layoutName = layoutName;
                }
                return ItemCombobox;
            }());
            viewmodel.ItemCombobox = ItemCombobox;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = cmm013.b || (cmm013.b = {}));
})(cmm013 || (cmm013 = {}));
