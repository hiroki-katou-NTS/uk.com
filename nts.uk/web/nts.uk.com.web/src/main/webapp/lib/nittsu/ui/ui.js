/// <reference path="../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var windows;
            (function (windows) {
                var MAIN_WINDOW_ID = 'MAIN_WINDOW';
                var DEFAULT_DIALOG_OPTIONS = {
                    autoOpen: false,
                    draggable: true,
                    resizable: false,
                    create: function (event) {
                        $(event.target).dialog('widget').css({ position: 'fixed' });
                    }
                };
                /**
                 * Main or Sub Window(dialog)
                 */
                var ScreenWindow = (function () {
                    function ScreenWindow(id, isRoot, parent) {
                        this.globalContext = null;
                        this.$dialog = null;
                        this.$iframe = null;
                        this.onClosedHandler = $.noop;
                        this.id = id;
                        this.isRoot = isRoot;
                        this.parent = parent;
                    }
                    ScreenWindow.createMainWindow = function () {
                        return new ScreenWindow(MAIN_WINDOW_ID, true, null);
                    };
                    ScreenWindow.createSubWindow = function (parent) {
                        return new ScreenWindow(uk.util.randomId(), false, parent);
                    };
                    ScreenWindow.prototype.setGlobal = function (globalContext) {
                        this.globalContext = globalContext;
                    };
                    ScreenWindow.prototype.setTitle = function (newTitle) {
                        if (this.isRoot) {
                            this.globalContext.title = newTitle;
                        }
                        else {
                            this.$dialog.dialog('option', { title: newTitle });
                        }
                    };
                    ScreenWindow.prototype.setHeight = function (height) {
                        if (!isNaN(height)) {
                            this.$dialog.dialog('option', {
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setWidth = function (width) {
                        if (!isNaN(width)) {
                            this.$dialog.dialog('option', {
                                width: width
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setSize = function (height, width) {
                        if (!isNaN(width) && !isNaN(height)) {
                            this.$dialog.dialog('option', {
                                width: width,
                                height: height
                            });
                            this.$dialog.resize();
                        }
                    };
                    ScreenWindow.prototype.setupAsDialog = function (path, options) {
                        var _this = this;
                        options.close = function () {
                            _this.dispose();
                        };
                        this.build$dialog(options);
                        this.$iframe.bind('load', function () {
                            _this.globalContext.nts.uk.ui.windows.selfId = _this.id;
                            _this.$dialog.dialog('option', {
                                width: options.width || _this.globalContext.dialogSize.width,
                                height: options.height || _this.globalContext.dialogSize.height,
                                title: options.title || "dialog",
                                resizable: true,
                                beforeClose: function () {
                                    //return dialogWindow.__viewContext.dialog.beforeClose();
                                }
                            }).dialog('open');
                        });
                        this.globalContext.location.href = uk.request.resolvePath(path);
                    };
                    ScreenWindow.prototype.build$dialog = function (options) {
                        this.$dialog = $('<div/>')
                            .css({
                            padding: '0px',
                            overflow: 'hidden'
                        })
                            .appendTo($('body'))
                            .dialog(options);
                        this.$iframe = $('<iframe/>').css({
                            width: '100%',
                            height: '100%'
                        })
                            .appendTo(this.$dialog);
                        this.setGlobal(this.$iframe[0].contentWindow);
                    };
                    ScreenWindow.prototype.onClosed = function (callback) {
                        this.onClosedHandler = function () {
                            callback();
                            windows.container.localShared = {};
                        };
                    };
                    ScreenWindow.prototype.close = function () {
                        if (this.isRoot) {
                            window.close();
                        }
                        else {
                            this.$dialog.dialog('close');
                        }
                    };
                    ScreenWindow.prototype.dispose = function () {
                        var _this = this;
                        _.defer(function () { return _this.onClosedHandler(); });
                        // delay 2 seconds to avoid IE error when any JS is running in destroyed iframe
                        setTimeout(function () {
                            _this.$iframe.remove();
                            _this.$dialog.remove();
                            _this.$dialog = null;
                            _this.$iframe = null;
                            _this.globalContext = null;
                            _this.parent = null;
                            _this.onClosedHandler = null;
                        }, 2000);
                    };
                    return ScreenWindow;
                }());
                windows.ScreenWindow = ScreenWindow;
                /**
                 * All ScreenWindows are managed by this container.
                 * this instance is singleton in one browser-tab.
                 */
                var ScreenWindowContainer = (function () {
                    function ScreenWindowContainer() {
                        this.windows = {};
                        this.windows[windows.selfId] = ScreenWindow.createMainWindow();
                        this.windows[windows.selfId].setGlobal(window);
                        this.shared = {};
                        this.localShared = {};
                    }
                    /**
                     * All dialog object is in MainWindow.
                     */
                    ScreenWindowContainer.prototype.createDialog = function (path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        this.windows[subWindow.id] = subWindow;
                        options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                        subWindow.setupAsDialog(path, options);
                        return subWindow;
                    };
                    ScreenWindowContainer.prototype.getShared = function (key) {
                        return this.localShared[key] !== undefined ? this.localShared[key] : this.shared[key];
                    };
                    ScreenWindowContainer.prototype.setShared = function (key, data, isRoot, persist) {
                        if (persist || isRoot) {
                            this.shared[key] = data;
                        }
                        else {
                            this.localShared[key] = data;
                        }
                    };
                    ScreenWindowContainer.prototype.close = function (id) {
                        var target = this.windows[id];
                        delete this.windows[id];
                        target.close();
                    };
                    return ScreenWindowContainer;
                }());
                windows.ScreenWindowContainer = ScreenWindowContainer;
                if (uk.util.isInFrame()) {
                    var parent = window.parent;
                    windows.container = (parent.nts.uk.ui.windows.container);
                }
                else {
                    windows.selfId = MAIN_WINDOW_ID;
                    windows.container = new ScreenWindowContainer();
                }
                function getShared(key) {
                    return windows.container.getShared(key);
                }
                windows.getShared = getShared;
                function setShared(key, data, persist) {
                    windows.container.setShared(key, data, windows.getSelf().isRoot, persist);
                }
                windows.setShared = setShared;
                function getSelf() {
                    return windows.container.windows[windows.selfId];
                }
                windows.getSelf = getSelf;
                function close(windowId) {
                    windowId = uk.util.orDefault(windowId, windows.selfId);
                    windows.container.close(windowId);
                }
                windows.close = close;
                var sub;
                (function (sub) {
                    function modal(path, options) {
                        options = options || {};
                        options.modal = true;
                        return open(path, options);
                    }
                    sub.modal = modal;
                    function modeless(path, options) {
                        options = options || {};
                        options.modal = false;
                        return open(path, options);
                    }
                    sub.modeless = modeless;
                    function open(path, options) {
                        return windows.container.createDialog(path, options, windows.selfId);
                    }
                    sub.open = open;
                })(sub = windows.sub || (windows.sub = {}));
            })(windows = ui_1.windows || (ui_1.windows = {}));
            function localize(textId) {
                return textId;
            }
            ui_1.localize = localize;
            var dialog;
            (function (dialog) {
                function createNoticeDialog(text, buttons) {
                    var $control = $('<div/>').addClass('control');
                    text = text.replace(/\n/g, '<br />');
                    var $this = $('<div/>').addClass('notice-dialog')
                        .append($('<div/>').addClass('text').append(text))
                        .append($control)
                        .appendTo('body')
                        .dialog({
                        dialogClass: "no-close",
                        width: 'auto',
                        modal: true,
                        closeOnEscape: false,
                        buttons: buttons,
                        open: function () {
                            $(this).closest('.ui-dialog').css('z-index', 120001);
                            $('.ui-widget-overlay').last().css('z-index', 120000);
                            $(this).parent().find('.ui-dialog-buttonset > button:first-child').focus();
                            $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
                        },
                        close: function (event) {
                            $(this).dialog('destroy');
                            $(event.target).remove();
                        }
                    });
                    return $this;
                }
                /**
                 * Show information dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
                function info(text) {
                    var then = $.noop;
                    var $dialog = $('<div/>').hide();
                    $(function () {
                        $dialog.appendTo('body').dialog({
                            autoOpen: false
                        });
                    });
                    setTimeout(function () {
                        var $this = createNoticeDialog(text, [{
                                text: "はい",
                                "class": "large",
                                click: function () {
                                    $this.dialog('close');
                                    then();
                                }
                            }]);
                    }, 0);
                    return {
                        then: function (callback) {
                            then = callback;
                        }
                    };
                }
                dialog.info = info;
                ;
                /**
                 * Show alert dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
                function alert(text) {
                    return info(text);
                }
                dialog.alert = alert;
                ;
                /**
                 * Show confirm dialog.
                 *
                 * @param {String}
                 *			text information text
                 * @returns handler
                 */
                function confirm(text) {
                    var handleYes = $.noop;
                    var handleNo = $.noop;
                    var handleCancel = $.noop;
                    var handleThen = $.noop;
                    var hasNoButton = true;
                    var hasCancelButton = false;
                    var handlers = {
                        ifYes: function (handler) {
                            handleYes = handler;
                            return handlers;
                        },
                        ifCancel: function (handler) {
                            hasNoButton = false;
                            hasCancelButton = true;
                            handleCancel = handler;
                            return handlers;
                        },
                        ifNo: function (handler) {
                            hasNoButton = true;
                            handleNo = handler;
                            return handlers;
                        },
                        then: function (handler) {
                            handleThen = handler;
                            return handlers;
                        }
                    };
                    setTimeout(function () {
                        var buttons = [];
                        // yes button
                        buttons.push({
                            text: "はい",
                            "class": "yes large danger",
                            click: function () {
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                            }
                        });
                        // no button
                        if (hasNoButton) {
                            buttons.push({
                                text: "いいえ",
                                "class": "no large",
                                click: function () {
                                    $this.dialog('close');
                                    handleNo();
                                    handleThen();
                                }
                            });
                        }
                        // cancel button
                        if (hasCancelButton) {
                            buttons.push({
                                text: "キャンセル",
                                "class": "cancel large",
                                click: function () {
                                    $this.dialog('close');
                                    handleCancel();
                                    handleThen();
                                }
                            });
                        }
                        var $this = createNoticeDialog(text, buttons);
                    });
                    return handlers;
                }
                dialog.confirm = confirm;
                ;
            })(dialog = ui_1.dialog || (ui_1.dialog = {}));
            var contextmenu;
            (function (contextmenu) {
                var ContextMenu = (function () {
                    /**
                     * Create an instance of ContextMenu. Auto call init() method
                     *
                     * @constructor
                     * @param {selector} Jquery selector for elements need to show ContextMenu
                     * @param {items} List ContextMenuItem for ContextMenu
                     * @param {enable} (Optinal) Set enable/disable for ContextMenu
                     */
                    function ContextMenu(selector, items, enable) {
                        this.selector = selector;
                        this.items = items;
                        this.enable = (enable !== undefined) ? enable : true;
                        this.init();
                    }
                    /**
                     * Create ContextMenu and bind event in DOM
                     */
                    ContextMenu.prototype.init = function () {
                        var self = this;
                        // Remove ContextMenu with same 'selector' (In case Ajax call will re-create DOM elements)
                        $('body .ntsContextMenu').each(function () {
                            if ($(this).data("selector") === self.selector) {
                                $("body").off("contextmenu", self.selector);
                                $(this).remove();
                            }
                        });
                        // Initial
                        self.guid = nts.uk.util.randomId();
                        var $contextMenu = $("<ul id='" + self.guid + "' class='ntsContextMenu'></ul>").data("selector", self.selector).hide();
                        self.createMenuItems($contextMenu);
                        $('body').append($contextMenu);
                        // Binding contextmenu event
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
                        // Hiding when click outside
                        $("html").on("mousedown", function (event) {
                            if (!$contextMenu.is(event.target) && $contextMenu.has(event.target).length === 0) {
                                $contextMenu.hide();
                            }
                        });
                    };
                    /**
                     * Remove and unbind ContextMenu event
                     */
                    ContextMenu.prototype.destroy = function () {
                        // Unbind contextmenu event
                        $("html").off("contextmenu", this.selector);
                        $("#" + this.guid).remove();
                    };
                    /**
                     * Re-create ContextMenu. Useful when you change various things in ContextMenu.items
                     */
                    ContextMenu.prototype.refresh = function () {
                        this.destroy();
                        this.init();
                    };
                    /**
                     * Get a ContextMenuItem instance
                     *
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     * @return {any} Return ContextMenuItem if found or undefiend
                     */
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
                    /**
                     * Add an ContextMenuItem instance to ContextMenu
                     *
                     * @param {item} An ContextMenuItem instance
                     */
                    ContextMenu.prototype.addItem = function (item) {
                        this.items.push(item);
                        this.refresh();
                    };
                    /**
                     * Remove item with given "key" or index
                     *
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
                    ContextMenu.prototype.removeItem = function (target) {
                        var item = this.getItem(target);
                        if (item !== undefined) {
                            _.remove(this.items, item);
                            this.refresh();
                        }
                    };
                    /**
                     * Enable/Disable ContextMenu. If disable right-click will have default behavior
                     *
                     * @param {enable} A boolean value set enable/disable
                     */
                    ContextMenu.prototype.setEnable = function (enable) {
                        this.enable = enable;
                    };
                    /**
                     * Enable/Disable item with given "key" or index
                     *
                     * @param {enable} A boolean value set enable/disable
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
                    ContextMenu.prototype.setEnableItem = function (enable, target) {
                        var item = this.getItem(target);
                        item.enable = enable;
                        this.refresh();
                    };
                    /**
                     * Show/Hide item with given "key" or index
                     *
                     * @param {enable} A boolean value set visible/hidden
                     * @param {target} Can be string or number. String type will select item by "key", Number type will select item by index
                     */
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
            ui_1.confirmSave = function (dirtyChecker) {
                var frame = windows.getSelf();
                if (frame.$dialog === undefined || frame.$dialog === null) {
                    confirmSaveWindow(dirtyChecker);
                }
                else {
                    confirmSaveDialog(dirtyChecker, frame.$dialog);
                }
            };
            function confirmSaveWindow(dirtyChecker) {
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        return "ban co muon save hok?";
                    }
                };
                confirmSaveEnable(beforeunloadHandler);
            }
            function confirmSaveDialog(dirtyChecker, dialog) {
                //dialog* any;
                var beforeunloadHandler = function (e) {
                    if (dirtyChecker.isDirty()) {
                        e.preventDefault();
                        nts.uk.ui.dialog.confirm("Are you sure you want to leave the page?")
                            .ifYes(function () {
                            dirtyChecker.reset();
                            dialog.dialog("close");
                        }).ifNo(function () {
                        });
                    }
                };
                confirmSaveEnableDialog(beforeunloadHandler, dialog);
            }
            function confirmSaveEnableDialog(beforeunloadHandler, dialog) {
                dialog.on("dialogbeforeclose", beforeunloadHandler);
            }
            ui_1.confirmSaveEnableDialog = confirmSaveEnableDialog;
            ;
            function confirmSaveDisableDialog(dialog) {
                dialog.on("dialogbeforeclose", function () { });
            }
            ui_1.confirmSaveDisableDialog = confirmSaveDisableDialog;
            ;
            function confirmSaveEnable(beforeunloadHandler) {
                $(window).bind('beforeunload', beforeunloadHandler);
            }
            ui_1.confirmSaveEnable = confirmSaveEnable;
            ;
            function confirmSaveDisable() {
                $(window).unbind('beforeunload');
            }
            ui_1.confirmSaveDisable = confirmSaveDisable;
            ;
            var DirtyChecker = (function () {
                function DirtyChecker(targetViewModelObservable) {
                    this.targetViewModel = targetViewModelObservable;
                    this.initialState = this.getCurrentState();
                }
                DirtyChecker.prototype.getCurrentState = function () {
                    return ko.mapping.toJSON(this.targetViewModel());
                };
                DirtyChecker.prototype.reset = function () {
                    this.initialState = this.getCurrentState();
                };
                DirtyChecker.prototype.isDirty = function () {
                    return this.initialState !== this.getCurrentState();
                };
                return DirtyChecker;
            }());
            ui_1.DirtyChecker = DirtyChecker;
            /**
             * Utilities for IgniteUI
             */
            var ig;
            (function (ig) {
                var grid;
                (function (grid) {
                    function getRowIdFrom($anyElementInRow) {
                        return $anyElementInRow.closest('tr').attr('data-id');
                    }
                    grid.getRowIdFrom = getRowIdFrom;
                    function getRowIndexFrom($anyElementInRow) {
                        return parseInt($anyElementInRow.closest('tr').attr('data-row-idx'), 10);
                    }
                    grid.getRowIndexFrom = getRowIndexFrom;
                    var virtual;
                    (function (virtual) {
                        function getDisplayContainer(gridId) {
                            return $('#' + gridId + '_displayContainer');
                        }
                        virtual.getDisplayContainer = getDisplayContainer;
                        function getVisibleRows(gridId) {
                            return $('#' + gridId + ' > tbody > tr:visible');
                        }
                        virtual.getVisibleRows = getVisibleRows;
                        function getFirstVisibleRow(gridId) {
                            var top = getDisplayContainer(gridId).scrollTop();
                            var visibleRows = getVisibleRows(gridId);
                            for (var i = 0; i < visibleRows.length; i++) {
                                var $row = $(visibleRows[i]);
                                if (visibleRows[i].offsetTop + $row.height() > top) {
                                    return $row;
                                }
                            }
                        }
                        virtual.getFirstVisibleRow = getFirstVisibleRow;
                        function getLastVisibleRow(gridId) {
                            var $displayContainer = getDisplayContainer(gridId);
                            var bottom = $displayContainer.scrollTop() + $displayContainer.height();
                            return getVisibleRows(gridId).filter(function () {
                                return this.offsetTop < bottom;
                            }).last();
                        }
                        virtual.getLastVisibleRow = getLastVisibleRow;
                    })(virtual = grid.virtual || (grid.virtual = {}));
                    var header;
                    (function (header) {
                        function getCell(gridId, columnKey) {
                            var $headers = $('#' + gridId).igGrid("headersTable");
                            return $headers.find('#' + gridId + '_' + columnKey);
                        }
                        header.getCell = getCell;
                        function getLabel(gridId, columnKey) {
                            return getCell(gridId, columnKey).find('span');
                        }
                        header.getLabel = getLabel;
                    })(header = grid.header || (grid.header = {}));
                })(grid = ig.grid || (ig.grid = {}));
            })(ig = ui_1.ig || (ui_1.ig = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
