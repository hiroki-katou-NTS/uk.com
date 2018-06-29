var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsIconBindingHandler = (function () {
                    function NtsIconBindingHandler() {
                    }
                    NtsIconBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var iconNo = ko.unwrap(data.no);
                        var width = ko.unwrap(data.width) || "100%";
                        var height = ko.unwrap(data.height) || "100%";
                        var iconFileName = iconNo + ".png";
                        var iconPath = nts.uk.request.location.siteRoot
                            .mergeRelativePath(nts.uk.request.WEB_APP_NAME["comjs"] + "/")
                            .mergeRelativePath("lib/nittsu/ui/style/stylesheets/images/icons/numbered/")
                            .mergeRelativePath(iconFileName)
                            .serialize();
                        var $icon = $(element);
                        $icon.addClass("img-icon");
                        $icon.css({
                            "background-image": "url(" + iconPath + ")",
                            "background-size": "contain",
                            width: width,
                            height: height
                        });
                    };
                    NtsIconBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                    };
                    return NtsIconBindingHandler;
                }());
                ko.bindingHandlers['ntsIcon'] = new NtsIconBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=icon-ko-ext.js.map