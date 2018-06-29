var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsLegentButtonBindingHandler = (function () {
                    function NtsLegentButtonBindingHandler() {
                    }
                    NtsLegentButtonBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var $container = $(element);
                        $container.text('■ 凡例');
                        $container.click(function () {
                            showLegendPanel($container, data);
                        });
                    };
                    NtsLegentButtonBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                    };
                    return NtsLegentButtonBindingHandler;
                }());
                function getColorCodeFromItem(legendItem) {
                    return uk.util.optional.of(legendItem.cssClass)
                        .map(function (cc) { return getColorCodeFromCssClass(cc); })
                        .orElse(legendItem.colorCode);
                }
                function getColorCodeFromCssClass(legendCssClass) {
                    var $temp = $('<span/>').addClass(legendCssClass.className)
                        .hide()
                        .appendTo('body');
                    var colorCode = $temp.css(legendCssClass.colorPropertyName);
                    $temp.remove();
                    return colorCode;
                }
                function showLegendPanel($legendButton, options) {
                    var legendSize = 18;
                    var hasTemplate = !nts.uk.util.isNullOrEmpty(options.template);
                    var $panel = $('<div/>').addClass('nts-legendbutton-panel');
                    options.items.forEach(function (item) {
                        if (hasTemplate) {
                            $('<div/>').addClass('legend-item')
                                .append(extractTemplate(options.template, item))
                                .appendTo($panel);
                        }
                        else {
                            $('<div/>').addClass('legend-item')
                                .append($('<div/>')
                                .addClass('legend-item-symbol')
                                .css({
                                'background-color': getColorCodeFromItem(item),
                                width: legendSize + 'px',
                                height: legendSize + 'px'
                            })
                                .text('　'))
                                .append($('<div/>')
                                .addClass('legend-item-label')
                                .text(item.labelText))
                                .appendTo($panel);
                        }
                    });
                    $panel.appendTo('body').position({
                        my: 'left top',
                        at: 'left bottom',
                        of: $legendButton
                    });
                    _.defer(function () {
                        $(window).bind('mousedown.legendpanel', function () {
                            $panel.remove();
                            $(window).unbind('mousedown.legendpanel');
                        });
                    });
                }
                function extractTemplate(template, item) {
                    var extracted = _.clone(template);
                    var changeTextIndex = extracted.indexOf("#{");
                    while (changeTextIndex > -1) {
                        var closeComa = extracted.indexOf("}", changeTextIndex);
                        var textToChange = extracted.substring(changeTextIndex, closeComa + 1);
                        extracted = extracted.replace(new RegExp(textToChange, 'g'), item[textToChange.substring(2, textToChange.length - 1)]);
                        changeTextIndex = extracted.indexOf("#{");
                    }
                    return extracted;
                }
                ko.bindingHandlers['ntsLegendButton'] = new NtsLegentButtonBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=legendbutton-ko-ext.js.map