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
                    create: function (event) {
                        $(event.target).dialog('widget').css({ position: 'fixed' });
                    }
                };
                /**
                 * Main or Sub Window(dialog)
                 */
                var ScreenWindow = (function () {
                    function ScreenWindow(id, isRoot, parent) {
                        this.id = id;
                        this.isRoot = isRoot;
                        this.parent = parent;
                        this.global = null;
                    }
                    ScreenWindow.createMainWindow = function () {
                        return new ScreenWindow(MAIN_WINDOW_ID, true, null);
                    };
                    ScreenWindow.createSubWindow = function (parent) {
                        return new ScreenWindow(uk.util.randomId(), false, parent);
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
                    }
                    /**
                     * All dialog object is in MainWindow.
                     */
                    ScreenWindowContainer.prototype.createDialog = function (path, options, parentId) {
                        var parentwindow = this.windows[parentId];
                        var subWindow = ScreenWindow.createSubWindow(parentwindow);
                        options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
                        var $dialog = $('<div/>')
                            .css({
                            padding: 'initial',
                            overflow: 'hidden'
                        })
                            .appendTo($('body'))
                            .dialog(options);
                        var $iframe = $('<iframe/>')
                            .css({
                            width: '100%',
                            height: '100%'
                        }).appendTo($dialog);
                        var dialogGlobal = $iframe[0].contentWindow;
                        $iframe.bind('load', function () {
                            dialogGlobal.nts.uk.ui.windows.selfId = subWindow.id;
                            $dialog.dialog('option', {
                                width: dialogGlobal.dialogSize.width,
                                height: dialogGlobal.dialogSize.height,
                                title: 'dialog',
                                beforeClose: function () {
                                    //return dialogWindow.__viewContext.dialog.beforeClose();
                                }
                            }).dialog('open');
                        });
                        dialogGlobal.location.href = path;
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
            })(windows = ui.windows || (ui.windows = {}));
            function localize(textId) {
                return textId;
            }
            ui.localize = localize;
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
