module nts.uk.ui {
     
    export module windows {

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
        export class ScreenWindow {
            id: string;
            isRoot: boolean;
            parent: ScreenWindow;
            global: Window;

            constructor(id: string, isRoot: boolean, parent: ScreenWindow) {
                this.id = id;
                this.isRoot = isRoot;
                this.parent = parent;
                this.global = null;
            }

            static createMainWindow() {
                return new ScreenWindow(MAIN_WINDOW_ID, true, null);
            }

            static createSubWindow(parent: ScreenWindow) {
                return new ScreenWindow(util.randomId(), false, parent);
            }
        }

        /**
         * All ScreenWindows are managed by this container.
         * this instance is singleton in one browser-tab.
         */
        export class ScreenWindowContainer {
            windows: { [key: string]: ScreenWindow };

            constructor() {
                this.windows = {};
                this.windows[selfId] = ScreenWindow.createMainWindow();
            }

            /**
             * All dialog object is in MainWindow.
             */
            createDialog(path: string, options: any, parentId: string) {

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

                var dialogGlobal = (<any>$iframe[0]).contentWindow;
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
            }
        }

        export var selfId: string;
        export var container: ScreenWindowContainer;

        if (util.isInFrame()) {
            var parent: any = window.parent;
            container = <ScreenWindowContainer> (parent.nts.uk.ui.windows.container);
        } else {
            selfId = MAIN_WINDOW_ID;
            container = new ScreenWindowContainer();
        }


        export module sub {

            export function modal(path: string, options: any) {
                options = options || {};
                options.modal = true;
                return open(path, options);
            }

            export function modeless(path: string, options: any) {
                options = options || {};
                options.modal = false;
                return open(path, options);
            }

            export function open(path: string, options: any) {
                return windows.container.createDialog(path, options, selfId);
            }
        }
    }
    
    export function localize(textId: string): string {
        return textId;
    }
         
    export module dialog {
                
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
        export function info(text){
            var $dialog = $('<div/>').hide();
            
            $(function () {
                $dialog.appendTo('body').dialog({
                    autoOpen: false
                });
            })
            
            return function (text) {
        
                var then = $.noop;
                
                setTimeout(function () {
                    var $this = createNoticeDialog(
                        text,
                        [
                            $('<button/>').addClass('large')
                                .text("OK")
                                .click(function () {
                                    $this.dialog('close');
                                    then();
                                })
                        ]);
                }, 0);
                
                return {
                    then: function (callback) {
                        then = callback;
                    }
                };
            };
        };
        
        /**
         * Show alert dialog.
         * 
         * @param {String}
         *            text information text
         * @returns handler
         */
        export function alert(text) {
            return info(text);
        };
        
        /**
         * Show confirm dialog.
         * 
         * @param {String}
         *            text information text
         * @returns handler
         */
        export function confirm(text) {
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
                buttons.push({text: "はい",
                           "class": "yes large danger",
                           click: function(){
                                $this.dialog('close');
                                handleYes();
                                handleThen();
                           }
                         });
                // no button
                buttons.push({text: "いいえ",
                               "class": "no large",
                               click: function(){
                                    $this.dialog('close');
                                    handleNo();
                                    handleThen();
                               }
                             });
                // cancel button
                if (hasCancelButton) {
                    buttons.push({text: "Cancel",
                               "class": "cancel large",
                               click: function(){
                                    $this.dialog('close');
                                    handleCancel();
                                    handleThen();
                               }
                             });
                }
                
                var $this = createNoticeDialog(text, buttons);
            });
        
            return handlers;
        };
    }
}