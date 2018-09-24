/// <reference path="../../reference.ts"/>

module nts.uk.ui {

    export module windows {
        
        export var iframeNameCounter = 0;
        
        // this is used for "name" attr of iframe for sub window
        function createIframeName(): string {
            iframeNameCounter++;
            return "window_" + iframeNameCounter;
        }

        var MAIN_WINDOW_ID = 'MAIN_WINDOW';

        var DEFAULT_DIALOG_OPTIONS = {
            autoOpen: false,
            draggable: true,
            resizable: false,
            dialogClass: "no-close"
        };

        /**
         * Main or Sub Window(dialog)
         */
        export class ScreenWindow {
            id: string;
            isRoot: boolean;
            parent: ScreenWindow;
            globalContext: any = null;
            $dialog: JQuery = null;
            $iframe: JQuery = null;
            onClosedHandler: () => void = $.noop;
            onOpenedHandler: () => void = $.noop;

            constructor(id: string, isRoot: boolean, parent: ScreenWindow) {
                this.id = id;
                this.isRoot = isRoot;
                this.parent = parent;
            }

            static createMainWindow() {
                return new ScreenWindow(MAIN_WINDOW_ID, true, null);
            }

            static createSubWindow(parent: ScreenWindow) {
                return new ScreenWindow(util.randomId(), false, parent);
            }

            setGlobal(globalContext: any) {
                this.globalContext = globalContext;
            }

            setTitle(newTitle: string) {
                if (this.isRoot) {
                    this.globalContext.title = newTitle;
                } else {
                    this.$dialog.dialog('option', { title: newTitle });
                }
            }
            setHeight(height: any) {
                if (!isNaN(height)) {
                    this.$dialog.dialog('option', {
                        height: height
                    });
                    this.$dialog.resize();
                }
            }
            setWidth(width: any) {
                if (!isNaN(width)) {
                    this.$dialog.dialog('option', {
                        width: width
                    });
                    this.$dialog.resize();
                }
            }
            setSize(height, width) {
                if (!isNaN(width) && !isNaN(height)) {
                    this.$dialog.dialog('option', {
                        width: width,
                        height: height
                    });
                    this.$dialog.resize();
                }
            }
            setupAsDialog(path: string, options: any) {

                var self = this;
                options.close = () => {
                    this.dispose();
                };

                this.build$dialog(options);
                
                this.$iframe.bind('load', () => {
                    this.globalContext.nts.uk.ui.windows.selfId = this.id;

                    let dialogName = this.globalContext.__viewContext["program"]["programName"];
                    let title = nts.uk.util.isNullOrEmpty(dialogName)　? "" : dialogName;
                    let showCloseButton = this.globalContext.dialogCloseButton === true;
                
                    this.$dialog.dialog('option', {
                        width: options.width || this.globalContext.dialogSize.width,
                        height: options.height || this.globalContext.dialogSize.height,
                        title: title,
                        resizable: options.resizable,
                        open: function() {
                            let $dialog = $(this);
                            if (!showCloseButton) {
                                $dialog.closest(".ui-dialog").addClass("no-close-btn");
                            }
                            
                            $dialog.dialogPositionControl();
                            
//                            if ($(this).parent().height() >= $("#contents-area").height()) {
//                                $(this).dialog("option", "position", {
//                                    my: "center top",
//                                    at: "center top",
//                                    of: $("#contents-area"),
//                                    collision: "none"
//                                })
//                                $(this).parent().css("position", "absolute");
//                            }

                            var $dialogDocument = $(this).parent();
                            let $dialogContentDoc = $(this.lastElementChild.contentDocument);

                            // catch press tab key in close button of dialog.
                            $dialogDocument.on("keydown", ":tabbable", function(evt) {
                                var code = evt.which || evt.keyCode;
                                if (code.toString() === "9") {
                                    var focusableElements = $dialogContentDoc.find(":tabbable");
                                    if ($(evt.target).hasClass("ui-dialog-titlebar-close") && evt.shiftKey === false) {
                                        focusableElements.first().focus();
                                        evt.preventDefault();
                                    } else if ($(evt.target).hasClass("ui-dialog-titlebar-close") && evt.shiftKey === true) {
                                        focusableElements.last().focus();
                                        evt.preventDefault();
                                    }
                                }
                            });
                            // catch press tab key for component in dialog.
                            $dialogContentDoc.on("keydown", ":tabbable", function(evt) {
                                var code = evt.which || evt.keyCode;
                                if (code.toString() === "9") {
                                    var focusableElements = $dialogContentDoc.find(":tabbable");
                                    if ($(evt.target).is(focusableElements.last()) && evt.shiftKey === false) {
                                        focusableElements.first().focus();
                                        evt.preventDefault();
                                    } else if ($(evt.target).is(focusableElements.first()) && evt.shiftKey === true) {
                                        focusableElements.last().focus();
                                        evt.preventDefault();
                                    }
                                }
                            });
                            
                            setTimeout(function(){
                                self.onOpenedHandler();
                            }, 100);
                        },
                        beforeClose: function() {
                            //return dialogWindow.__viewContext.dialog.beforeClose();
                        }
                    }).dialog('open');
                    //remove focus on tab key press on the close button on jquery dialog
                    $('.ui-dialog-titlebar-close').attr('tabindex', '-1');
                    if (this.parent !== null)
                        this.parent.globalContext.nts.uk.ui.block.clear();
                    //                    var widget= this.$dialog.dialog("widget");
                    //                    widget.draggable("option","containment",false);
                });

                this.globalContext.location.href = path;
            }

            build$dialog(options: any) {
                this.$dialog = $('<div/>')
                    .css({
                        padding: '0px',
                        overflow: 'hidden'
                    })
                    .appendTo($('body'))
                    .dialog(options);

                this.$iframe = $('<iframe/>')
                    .attr('name', createIframeName())
                    .css({
                        width: '100%',
                        height: '100%'
                    }).appendTo(this.$dialog);

                this.setGlobal((<any>this.$iframe[0]).contentWindow);
            }

            onClosed(callback: () => void) {
                this.onClosedHandler = () => {
                    //var dataModel = ko.dataFor(this.$dialog[0]);
                    //dataModel.kiban.errorDialogViewModel.errors([]);
                    //dataModel.kiban.errorDialogViewModel.errors.valueHasMutated();
                    callback();
                    container.localShared = {};
                };
            }
            
            onOpened(callback: () => void) {
                this.onOpenedHandler = function() {
                    callback();
                };
            }

            close() {
                if (this.isRoot) {
                    window.close();
                } else {
                    this.$dialog.dialog('close');
                }
            }

            dispose() {
                _.defer(() => this.onClosedHandler());
                window.parent.$("body").trigger("dialogclosed", {dialogId: this.id});
                // delay 2 seconds to avoid IE error when any JS is running in destroyed iframe
                setTimeout(() => {
                    this.$iframe.remove();
                    this.$dialog.remove();
                    this.$dialog = null;
                    this.$iframe = null;
                    this.globalContext = null;
                    this.parent = null;
                    this.onClosedHandler = null;
//                    this.id = null;
                }, 2000);
            }
        }

        /**
         * All ScreenWindows are managed by this container.
         * this instance is singleton in one browser-tab.
         */
        export class ScreenWindowContainer {
            windows: { [key: string]: ScreenWindow };
            shared: { [key: string]: any };
            localShared: { [key: string]: any };

            constructor() {
                this.windows = {};
                this.windows[selfId] = ScreenWindow.createMainWindow();
                this.windows[selfId].setGlobal(window);
                this.shared = {};
                this.localShared = {};
            }

            /**
             * All dialog object is in MainWindow.
             */
            createDialog(path: string, options: any, parentId: string) {

                var parentwindow = this.windows[parentId];
                var subWindow = ScreenWindow.createSubWindow(parentwindow);
                this.windows[subWindow.id] = subWindow;

                options = $.extend({}, DEFAULT_DIALOG_OPTIONS, options);

                subWindow.setupAsDialog(path, options);

                return subWindow;
            }
            
            createDialogNotOpen(path: string, options: any, parentId: string) {

                var parentwindow = this.windows[parentId];
                var subWindow = ScreenWindow.createSubWindow(parentwindow);
                this.windows[subWindow.id] = subWindow;

                return subWindow;
            }
            
            mergeOption(options: any){
                return $.extend({}, DEFAULT_DIALOG_OPTIONS, options);
            }

            getShared(key: string): any {               
                return this.localShared[key] !== undefined ? this.localShared[key] : this.shared[key];
            }

            setShared(key: string, data: any, isRoot: boolean, persist?: boolean) {
                var transferData;
                
                // Null or Undefined
                if (util.isNullOrUndefined(data)) {
                    transferData = data;
                }
                // Data or KO data
                else if (!_.isFunction(data) || ko.isObservable(data)) {
                    transferData = JSON.parse(JSON.stringify(ko.unwrap(data))); // Complete remove reference by object
                }
                // Callback function
                else {
                    transferData = data;
                }
                
                if (persist || isRoot) {
                    this.shared[key] = transferData;
                } else {
                    this.localShared[key] = transferData;
                }
            }

            close(id: string) {
                var target = this.windows[id];
                delete this.windows[id];
                target.close();
            }
        }

        export var selfId: string;
        export var container: ScreenWindowContainer;
        export function rgc() {
            return container.windows[MAIN_WINDOW_ID].globalContext;
        }

        if (util.isInFrame()) {
            var parent: any = window.parent;
            container = <ScreenWindowContainer>(parent.nts.uk.ui.windows.container);
        } else {
            selfId = MAIN_WINDOW_ID;
            container = new ScreenWindowContainer();
        }

        export function getShared(key: string): any {
            return container.getShared(key);
        }

        export function setShared(key: string, data: any, persist?: boolean) {
            container.setShared(key, data, windows.getSelf().isRoot, persist);
        } 

        export function getSelf() {
            return container.windows[selfId];
        }

        export function close(windowId?: string) {
            windowId = util.orDefault(windowId, selfId);
            container.close(windowId);
        }

        export module sub {
            export function modal(path: string, options?: any);
            export function modal(webAppId: nts.uk.request.WebAppId, path: string, options?: any) {
                if (typeof arguments[1] !== 'string') {
                    return modal.apply(null, _.concat(nts.uk.request.location.currentAppId, arguments));
                }
                
                return dialog(webAppId, path, true, options);
            }
            export function modeless(path: string, options?: any)
            export function modeless(webAppId: nts.uk.request.WebAppId, path: string, options?: any) {
                 if (typeof arguments[1] !== 'string') {
                    return modeless.apply(null, _.concat(nts.uk.request.location.currentAppId, arguments));
                }
                return dialog(webAppId, path, false, options);
            }
            
            function dialog(webAppId: nts.uk.request.WebAppId, path: string, modal: boolean, options?: any){
                options = options || {};
                options.modal = modal;
                if(webAppId==nts.uk.request.location.currentAppId){
                    path = nts.uk.request.resolvePath(path);
                    return open(path, options);
                }else{
                    path = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(path).serialize();
                    
                    let dialog = createDialog(path, options);
                    request.login.keepSerializedSession()
                        .then(() => {
                            return request.login.restoreSessionTo(webAppId);
                        })
                        .then(() => {
                            dialog.setupAsDialog(path, windows.container.mergeOption(options)); 
                        });
                    return dialog;
                }
            }

            export function open(path: string, options?: any) {
                nts.uk.ui.block.invisible();
                return windows.container.createDialog(path, options, selfId);
            }
            
            export function createDialog(path: string, options?: any) {
                nts.uk.ui.block.invisible();
                return windows.container.createDialogNotOpen(path, options, selfId);
            }
        }
    }
    
}
