var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var windows;
            (function (windows) {
                var MAIN_WINDOW_ID = 'MAIN_WINDOW';
                var DEFAULT_DIALOG_OPTIONS = {
                    autoOpen: false,
                    draggable: true,
                    resizable: false,
                    dialogClass: "no-close"
                };
                var ScreenWindow = (function () {
                    function ScreenWindow(id, isRoot, parent) {
                        this.globalContext = null;
                        this.$dialog = null;
                        this.$iframe = null;
                        this.onClosedHandler = $.noop;
                        this.onOpenedHandler = $.noop;
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
                        var self = this;
                        options.close = function () {
                            _this.dispose();
                        };
                        this.build$dialog(options);
                        this.$iframe.bind('load', function () {
                            _this.globalContext.nts.uk.ui.windows.selfId = _this.id;
                            var dialogName = _this.globalContext.__viewContext["program"]["programName"];
                            var title = nts.uk.util.isNullOrEmpty(dialogName) ? "" : dialogName;
                            var showCloseButton = _this.globalContext.dialogCloseButton === true;
                            _this.$dialog.dialog('option', {
                                width: options.width || _this.globalContext.dialogSize.width,
                                height: options.height || _this.globalContext.dialogSize.height,
                                title: title,
                                resizable: options.resizable,
                                open: function () {
                                    var $dialog = $(this);
                                    if (!showCloseButton) {
                                        $dialog.closest(".ui-dialog").addClass("no-close-btn");
                                    }
                                    $dialog.dialogPositionControl();
                                    var $dialogDocument = $(this).parent();
                                    var $dialogContentDoc = $(this.lastElementChild.contentDocument);
                                    $dialogDocument.on("keydown", ":tabbable", function (evt) {
                                        var code = evt.which || evt.keyCode;
                                        if (code.toString() === "9") {
                                            var focusableElements = $dialogContentDoc.find(":tabbable");
                                            if ($(evt.target).hasClass("ui-dialog-titlebar-close") && evt.shiftKey === false) {
                                                focusableElements.first().focus();
                                                evt.preventDefault();
                                            }
                                            else if ($(evt.target).hasClass("ui-dialog-titlebar-close") && evt.shiftKey === true) {
                                                focusableElements.last().focus();
                                                evt.preventDefault();
                                            }
                                        }
                                    });
                                    $dialogContentDoc.on("keydown", ":tabbable", function (evt) {
                                        var code = evt.which || evt.keyCode;
                                        if (code.toString() === "9") {
                                            var focusableElements = $dialogContentDoc.find(":tabbable");
                                            if ($(evt.target).is(focusableElements.last()) && evt.shiftKey === false) {
                                                focusableElements.first().focus();
                                                evt.preventDefault();
                                            }
                                            else if ($(evt.target).is(focusableElements.first()) && evt.shiftKey === true) {
                                                focusableElements.last().focus();
                                                evt.preventDefault();
                                            }
                                        }
                                    });
                                    setTimeout(function () {
                                        self.onOpenedHandler();
                                    }, 100);
                                },
                                beforeClose: function () {
                                }
                            }).dialog('open');
                            $('.ui-dialog-titlebar-close').attr('tabindex', '-1');
                            if (_this.parent !== null)
                                _this.parent.globalContext.nts.uk.ui.block.clear();
                        });
                        this.globalContext.location.href = path;
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
                        }).appendTo(this.$dialog);
                        this.setGlobal(this.$iframe[0].contentWindow);
                    };
                    ScreenWindow.prototype.onClosed = function (callback) {
                        this.onClosedHandler = function () {
                            callback();
                            windows.container.localShared = {};
                        };
                    };
                    ScreenWindow.prototype.onOpened = function (callback) {
                        this.onOpenedHandler = function () {
                            callback();
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
                        window.parent.$("body").trigger("dialogclosed", { dialogId: this.id });
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
                var ScreenWindowContainer = (function () {
                    function ScreenWindowContainer() {
                        this.windows = {};
                        this.windows[windows.selfId] = ScreenWindow.createMainWindow();
                        this.windows[windows.selfId].setGlobal(window);
                        this.shared = {};
                        this.localShared = {};
                    }
                    ScreenWindowContainer.prototype.createDialog = function (path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        this.windows[subWindow.id] = subWindow;
                        options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                        subWindow.setupAsDialog(path, options);
                        return subWindow;
                    };
                    ScreenWindowContainer.prototype.createDialogNotOpen = function (path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        this.windows[subWindow.id] = subWindow;
                        return subWindow;
                    };
                    ScreenWindowContainer.prototype.mergeOption = function (options) {
                        return $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                    };
                    ScreenWindowContainer.prototype.getShared = function (key) {
                        return this.localShared[key] !== undefined ? this.localShared[key] : this.shared[key];
                    };
                    ScreenWindowContainer.prototype.setShared = function (key, data, isRoot, persist) {
                        var transferData;
                        if (uk.util.isNullOrUndefined(data)) {
                            transferData = data;
                        }
                        else if (!_.isFunction(data) || ko.isObservable(data)) {
                            transferData = JSON.parse(JSON.stringify(ko.unwrap(data)));
                        }
                        else {
                            transferData = data;
                        }
                        if (persist || isRoot) {
                            this.shared[key] = transferData;
                        }
                        else {
                            this.localShared[key] = transferData;
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
                function rgc() {
                    return windows.container.windows[MAIN_WINDOW_ID].globalContext;
                }
                windows.rgc = rgc;
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
                    function modal(webAppId, path, options) {
                        if (typeof arguments[1] !== 'string') {
                            return modal.apply(null, _.concat(nts.uk.request.location.currentAppId, arguments));
                        }
                        return dialog(webAppId, path, true, options);
                    }
                    sub.modal = modal;
                    function modeless(webAppId, path, options) {
                        if (typeof arguments[1] !== 'string') {
                            return modeless.apply(null, _.concat(nts.uk.request.location.currentAppId, arguments));
                        }
                        return dialog(webAppId, path, false, options);
                    }
                    sub.modeless = modeless;
                    function dialog(webAppId, path, modal, options) {
                        options = options || {};
                        options.modal = modal;
                        if (webAppId == nts.uk.request.location.currentAppId) {
                            path = nts.uk.request.resolvePath(path);
                            return open(path, options);
                        }
                        else {
                            path = nts.uk.request.location.siteRoot
                                .mergeRelativePath(nts.uk.request.WEB_APP_NAME[webAppId] + '/')
                                .mergeRelativePath(path).serialize();
                            var dialog_1 = createDialog(path, options);
                            uk.request.login.keepSerializedSession()
                                .then(function () {
                                return uk.request.login.restoreSessionTo(webAppId);
                            })
                                .then(function () {
                                dialog_1.setupAsDialog(path, windows.container.mergeOption(options));
                            });
                            return dialog_1;
                        }
                    }
                    function open(path, options) {
                        nts.uk.ui.block.invisible();
                        return windows.container.createDialog(path, options, windows.selfId);
                    }
                    sub.open = open;
                    function createDialog(path, options) {
                        nts.uk.ui.block.invisible();
                        return windows.container.createDialogNotOpen(path, options, windows.selfId);
                    }
                    sub.createDialog = createDialog;
                })(sub = windows.sub || (windows.sub = {}));
            })(windows = ui.windows || (ui.windows = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=windows.js.map