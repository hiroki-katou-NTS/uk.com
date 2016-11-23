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
                 *            text information text
                 * @returns handler
                 */
                function info(text) {
                    var $dialog = $('<div/>').hide();
                    $(function () {
                        $dialog.appendTo('body').dialog({
                            autoOpen: false
                        });
                    });
                    return function (text) {
                        var then = $.noop;
                        setTimeout(function () {
                            var $this = createNoticeDialog(text, [
                                { text: "はい",
                                    "class": "large",
                                    click: function () {
                                        $this.dialog('close');
                                        then();
                                    }
                                }
                            ]);
                        }, 0);
                        return {
                            then: function (callback) {
                                then = callback;
                            }
                        };
                    };
                }
                dialog.info = info;
                ;
                /**
                 * Show alert dialog.
                 *
                 * @param {String}
                 *            text information text
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
                 *            text information text
                 * @returns handler
                 */
                function confirm(text) {
                    var handleYes = $.noop;
                    var handleNo = $.noop;
                    var handleCancel = $.noop;
                    var handleThen = $.noop;
                    var hasCancelButton = false;
                    var handlers = {
                        ifYes: function (handler) {
                            handleYes = handler;
                            return handlers;
                        },
                        ifNo: function (handler) {
                            handleNo = handler;
                            return handlers;
                        },
                        ifCancel: function (handler) {
                            hasCancelButton = true;
                            handleCancel = handler;
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
                        buttons.push({ text: "はい",
                            "class": "yes large danger",
                            click: function () {
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                            }
                        });
                        // no button
                        buttons.push({ text: "いいえ",
                            "class": "no large",
                            click: function () {
                                $this.dialog('close');
                                handleNo();
                                handleThen();
                            }
                        });
                        // cancel button
                        if (hasCancelButton) {
                            buttons.push({ text: "Cancel",
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
            })(dialog = ui.dialog || (ui.dialog = {}));
            var DirtyChecker = (function () {
                function DirtyChecker(targetViewModelObservable) {
                    this.targetViewModel = targetViewModelObservable;
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
            ui.DirtyChecker = DirtyChecker;
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
