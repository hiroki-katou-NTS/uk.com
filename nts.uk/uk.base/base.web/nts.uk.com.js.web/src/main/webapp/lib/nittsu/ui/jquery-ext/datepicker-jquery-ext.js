var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsDatepicker;
                (function (ntsDatepicker) {
                    var CONTAINER_CLASSES = ["arrow-bottom", "arrow-top", "arrow-right", "arrow-left"];
                    var PICKER_CLASSES = ["datepicker-top-left", "datepicker-top-right", "datepicker-bottom-left", "datepicker-bottom-right"];
                    $.fn.ntsDatepicker = function (action, index) {
                        var $container = $(this);
                        if (action === "bindFlip") {
                            return bindFlip($container);
                        }
                        return $container;
                    };
                    function bindFlip($input) {
                        $input.on('show.datepicker', function (evt) {
                            var picker = $(this);
                            picker.data("showed", true);
                            setTimeout(function () {
                                picker.trigger("flippickercontainer");
                            }, 10);
                        });
                        $input.on('hide.datepicker', function (evt) {
                            var picker = $(this);
                            picker.data("showed", false);
                            CONTAINER_CLASSES.forEach(function (cls) { return picker.parent().removeClass(cls); });
                        });
                        $(window).resize(function () {
                            var picker = $(this);
                            if (picker.data("showed")) {
                                picker.datepicker('hide');
                                setTimeout(function () {
                                    picker.datepicker('show');
                                }, 10);
                            }
                        });
                        $input.bind("flippickercontainer", function (evt, data) {
                            var picker = $(this);
                            var container = picker.parent();
                            var currentShowContainer = $(".datepicker-container:not(.datepicker-hide)");
                            var datepickerID = picker.attr("id");
                            var ePos = container.offset();
                            if (ePos.top < 0 && ePos.left < 0) {
                                return;
                            }
                            CONTAINER_CLASSES.forEach(function (cls) { return container.removeClass(cls); });
                            PICKER_CLASSES.forEach(function (cls) { return currentShowContainer.removeClass(cls); });
                            var containerHeight = container.outerHeight(true);
                            var containerWidth = container.outerWidth(true);
                            var showContainerHeight = currentShowContainer.outerHeight(true);
                            var showContainerWidth = currentShowContainer.outerWidth(true);
                            var documentHeight = document.body.clientHeight;
                            var documentWidth = document.body.clientWidth;
                            var headerHeight = $("#functions-area").outerHeight(true) + $("#header").outerHeight(true);
                            var bottomHeight = $("#functions-area-bottom").outerHeight(true);
                            var spaceBottom = documentHeight - ePos.top - containerHeight;
                            var spaceTop = ePos.top;
                            var spaceRight = documentWidth - ePos.left - containerWidth;
                            var spaceLeft = ePos.left;
                            if (showContainerHeight + 10 <= spaceBottom) {
                                container.addClass("arrow-bottom");
                                currentShowContainer.position({
                                    my: "left bottom+" + (showContainerHeight + 10),
                                    at: "left bottom",
                                    'of': "#" + datepickerID
                                });
                                return;
                            }
                            if (showContainerHeight + 10 <= spaceTop) {
                                container.addClass("arrow-top");
                                currentShowContainer.position({
                                    my: "left top-" + (showContainerHeight + 10),
                                    at: "left top",
                                    'of': "#" + datepickerID
                                });
                                return;
                            }
                            var diaTop = ePos.top <= 0 ? 0 : ePos.top - showContainerHeight + containerHeight + headerHeight;
                            if (ePos.top <= diaTop) {
                                diaTop = ePos.top;
                            }
                            if (showContainerWidth + 10 <= spaceRight) {
                                var diaRight = ePos.left + containerWidth + 10;
                                container.addClass("arrow-right");
                                currentShowContainer.css({ top: diaTop, left: diaRight });
                                return;
                            }
                            if (showContainerWidth + 10 <= spaceLeft) {
                                var diaLeft = ePos.left - 10 - showContainerWidth;
                                container.addClass("arrow-left");
                                currentShowContainer.css({ top: diaTop, left: diaLeft });
                                return;
                            }
                            container.addClass("arrow-bottom");
                            currentShowContainer.position({
                                my: "left bottom+" + (showContainerHeight + 10),
                                at: "left bottom",
                                'of': "#" + datepickerID
                            });
                        });
                        return $input;
                    }
                })(ntsDatepicker || (ntsDatepicker = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=datepicker-jquery-ext.js.map