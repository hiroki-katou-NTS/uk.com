var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var contextmenu;
            (function (contextmenu) {
                var ContextMenu = (function () {
                    function ContextMenu(selector, items, enable) {
                        this.selector = selector;
                        this.items = items;
                        this.enable = (enable !== undefined) ? enable : true;
                        this.init();
                    }
                    ContextMenu.prototype.init = function () {
                        var self = this;
                        $('body .ntsContextMenu').each(function () {
                            if ($(this).data("selector") === self.selector) {
                                $("body").off("contextmenu", self.selector);
                                $(this).remove();
                            }
                        });
                        self.guid = nts.uk.util.randomId();
                        var $contextMenu = $("<ul id='" + self.guid + "' class='ntsContextMenu'></ul>").data("selector", self.selector).hide();
                        self.createMenuItems($contextMenu);
                        $('body').append($contextMenu);
                        $("html").on("contextmenu", self.selector, function (event) {
                            if (self.enable === true) {
                                event.preventDefault();
                                self.target = event.target;
                                $contextMenu.show().position({
                                    my: "left+2 top+2",
                                    of: event,
                                    collision: "fit"
                                });
                            }
                        });
                        $("html").on("mousedown", function (event) {
                            if (!$contextMenu.is(event.target) && $contextMenu.has(event.target).length === 0) {
                                $contextMenu.hide();
                            }
                        });
                    };
                    ContextMenu.prototype.destroy = function () {
                        $("html").off("contextmenu", this.selector);
                        $("#" + this.guid).remove();
                    };
                    ContextMenu.prototype.refresh = function () {
                        this.destroy();
                        this.init();
                    };
                    ContextMenu.prototype.getItem = function (target) {
                        if (typeof target === "number") {
                            return this.items[target];
                        }
                        else if (typeof target === "string") {
                            return _.find(this.items, ["key", target]);
                        }
                        else {
                            return undefined;
                        }
                    };
                    ContextMenu.prototype.addItem = function (item) {
                        this.items.push(item);
                        this.refresh();
                    };
                    ContextMenu.prototype.removeItem = function (target) {
                        var item = this.getItem(target);
                        if (item !== undefined) {
                            _.remove(this.items, item);
                            this.refresh();
                        }
                    };
                    ContextMenu.prototype.setEnable = function (enable) {
                        this.enable = enable;
                    };
                    ContextMenu.prototype.setEnableItem = function (enable, target) {
                        var item = this.getItem(target);
                        item.enable = enable;
                        this.refresh();
                    };
                    ContextMenu.prototype.setVisibleItem = function (visible, target) {
                        var item = this.getItem(target);
                        item.visible = visible;
                        this.refresh();
                    };
                    ContextMenu.prototype.createMenuItems = function (container) {
                        var self = this;
                        _.forEach(self.items, function (item) {
                            if (item.key !== "divider") {
                                var menuClasses = "menu-item ";
                                menuClasses += (item.enable === true) ? "" : "disabled ";
                                menuClasses += (item.visible === true) ? "" : "hidden ";
                                var menuItem = $("<li class='" + menuClasses + "'><span class='menu-icon " + item.icon + "'></span>" + item.text + "</li>")
                                    .data("key", item.key)
                                    .on("click", function () {
                                    if (!$(this).hasClass("disabled")) {
                                        item.handler(self.target);
                                        container.hide();
                                    }
                                }).appendTo(container);
                            }
                            else {
                                var menuItem = $("<li class='menu-item divider'></li>").appendTo(container);
                            }
                        });
                    };
                    return ContextMenu;
                }());
                contextmenu.ContextMenu = ContextMenu;
                var ContextMenuItem = (function () {
                    function ContextMenuItem(key, text, handler, icon, visible, enable) {
                        this.key = key;
                        this.text = text;
                        this.handler = (handler !== undefined) ? handler : $.noop;
                        this.icon = (icon) ? icon : "";
                        this.visible = (visible !== undefined) ? visible : true;
                        this.enable = (enable !== undefined) ? enable : true;
                    }
                    return ContextMenuItem;
                }());
                contextmenu.ContextMenuItem = ContextMenuItem;
            })(contextmenu = ui_1.contextmenu || (ui_1.contextmenu = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=contextmenu.js.map