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
}