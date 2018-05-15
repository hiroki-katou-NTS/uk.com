var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsFixedTable;
                (function (ntsFixedTable_1) {
                    $.fn.ntsFixedTable = ntsFixedTable;
                    function ntsFixedTable(action, options) {
                        var $controls = $(this);
                        if (typeof arguments[0] !== 'string') {
                            return ntsFixedTable.apply($controls, _.concat("init", action));
                        }
                        if (action === "init") {
                            return init($controls, options);
                        }
                        else {
                            return $controls;
                        }
                        ;
                    }
                    function init(controls, options) {
                        controls.each(function () {
                            var $originTable = $(this);
                            $originTable.addClass("fixed-table");
                            var $colgroup = $originTable.find("colgroup");
                            var $thead = $originTable.find("thead");
                            var setting = $.extend({ height: "auto" }, options);
                            var viewWidth = setting.width;
                            var width = 0;
                            $colgroup.find("col").each(function () {
                                width += Number($(this).attr("width").replace(/px/gi, ''));
                            });
                            width++;
                            if (nts.uk.util.isNullOrUndefined(viewWidth)) {
                                viewWidth = width;
                            }
                            var $container = $("<div class='nts-fixed-table cf'/>");
                            $originTable.after($container);
                            var $headerContainer = $("<div class='nts-fixed-header-container ui-iggrid nts-fixed-header'/>").css({ "max-width": viewWidth });
                            var $headerWrapper = $("<div class='nts-fixed-header-wrapper'/>").width(width);
                            var $headerTable = $("<table class='fixed-table'></table>");
                            $headerTable.append($colgroup.clone()).append($thead);
                            $headerTable.appendTo($headerWrapper);
                            $headerContainer.append($headerWrapper);
                            var $header = $("<div>");
                            $headerContainer.appendTo($header);
                            $header.appendTo($container);
                            $header.height($headerContainer.height());
                            var $headerScroll = $("<div>", { "class": "scroll-header nts-fixed-header", width: 16, height: $headerWrapper.outerHeight() });
                            $headerScroll.appendTo($header);
                            $originTable.addClass("nts-fixed-body-table");
                            var $bodyContainer = $("<div class='nts-fixed-body-container ui-iggrid'/>");
                            var $bodyWrapper = $("<div class='nts-fixed-body-wrapper'/>");
                            var bodyHeight = "auto";
                            if (setting.height !== "auto") {
                                $bodyContainer.css("max-width", viewWidth);
                                bodyHeight = Number(setting.height.toString().replace(/px/mi)) - $headerTable.find("thead").outerHeight();
                            }
                            var resizeEvent = function () {
                                $header.height($headerContainer.height());
                                if (bodyHeight < $originTable.height()) {
                                    if (/Edge/.test(navigator.userAgent)) {
                                        $headerScroll.width(11);
                                        $bodyContainer.css("padding-right", "12px");
                                    }
                                    else {
                                        $headerScroll.width(16);
                                        $bodyContainer.css("padding-right", "17px");
                                    }
                                    $headerScroll.css({ "border-right": "1px #CCC solid", "border-top": "1px #CCC solid", "border-bottom": "1px #CCC solid" });
                                }
                                else {
                                    $headerScroll.width(0);
                                    $headerScroll.css({ "border-right": "0px", "border-top": "0px", "border-bottom": "0px" });
                                    $bodyContainer.css("padding-right", "0px");
                                }
                                setTimeout(resizeEvent, 20);
                            };
                            $bodyContainer.scroll(function (evt, ui) {
                                $headerContainer.scrollLeft($bodyContainer.scrollLeft());
                            });
                            $bodyWrapper.width(width).height(bodyHeight);
                            $bodyWrapper.append($originTable);
                            $bodyContainer.append($bodyWrapper);
                            $container.append($bodyContainer);
                            if (setting.height !== "auto" && bodyHeight < $originTable.height()) {
                                if (/Edge/.test(navigator.userAgent)) {
                                    $bodyContainer.css("padding-right", "12px");
                                }
                                else {
                                    $bodyContainer.css("padding-right", "17px");
                                }
                            }
                            resizeEvent();
                        });
                        return controls;
                    }
                })(ntsFixedTable || (ntsFixedTable = {}));
            })(jqueryExtentions = ui_1.jqueryExtentions || (ui_1.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=fixed-table-jquery-ext.js.map