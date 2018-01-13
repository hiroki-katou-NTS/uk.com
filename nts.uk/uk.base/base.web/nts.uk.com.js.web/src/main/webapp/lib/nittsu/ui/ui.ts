/// <reference path="../reference.ts"/>

module nts.uk.ui {
    
    export module toBeResource {
        export let yes = "はい";
        export let no = "いいえ";
        export let cancel = "キャンセル";
        export let close = "閉じる";
        export let info = "情報";
        export let warn = "警告";
        export let error = "エラー";
        export let unset = "未設定";
        export let errorContent = "エラー内容";
        export let errorCode = "エラーコード";
        export let errorList = "エラー一覧";
        export let plzWait = "お待ちください";
    }

    export module windows {

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

                options.close = () => {
                    this.dispose();
                };

                this.build$dialog(options);
                
                this.$iframe.bind('load', () => {
                    this.globalContext.nts.uk.ui.windows.selfId = this.id;

                    let dialogName = this.globalContext.__viewContext["program"]["programName"];
                    let title = nts.uk.util.isNullOrEmpty(dialogName)　? "" : dialogName;
                
                    this.$dialog.dialog('option', {
                        width: options.width || this.globalContext.dialogSize.width,
                        height: options.height || this.globalContext.dialogSize.height,
                        title: title,
                        resizable: options.resizable,
                        open: function() {
                            let $dialog = $(this);
                            $dialog.closest(".ui-dialog").addClass("no-close-btn");
                            
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

                this.$iframe = $('<iframe/>').css({
                    width: '100%',
                    height: '100%'
                }).appendTo(this.$dialog);

                this.setGlobal((<any>this.$iframe[0]).contentWindow);
            }

            onClosed(callback: () => void) {
                this.onClosedHandler = function() {
                    callback();
                    container.localShared = {};
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

                // delay 2 seconds to avoid IE error when any JS is running in destroyed iframe
                setTimeout(() => {
                    this.$iframe.remove();
                    this.$dialog.remove();
                    this.$dialog = null;
                    this.$iframe = null;
                    this.globalContext = null;
                    this.parent = null;
                    this.onClosedHandler = null
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
                    this.shared[key] = data;
                } else {
                    this.localShared[key] = data;
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
                if(webAppId==nts.uk.request.location.currentAppId){
                    path = nts.uk.request.resolvePath(path);
                }else{
                    path = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(path).serialize();
                }
                
                options = options || {};
                options.modal = true;
                return open(path, options);
            }
            export function modeless(path: string, options?: any)
            export function modeless(webAppId: nts.uk.request.WebAppId, path: string, options?: any) {
                 if (typeof arguments[1] !== 'string') {
                    return modeless.apply(null, _.concat(nts.uk.request.location.currentAppId, arguments));
                }
                if(webAppId==nts.uk.request.location.currentAppId){
                    path = nts.uk.request.resolvePath(path);
                }else{
                    path = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME[webAppId] + '/')
                    .mergeRelativePath(path).serialize();
                }
                options = options || {};
                options.modal = false;
                return open(path, options);
            }

            export function open(path: string, options?: any) {
                nts.uk.ui.block.invisible();
                return windows.container.createDialog(path, options, selfId);
            }
        }
    }

    export function localize(textId: string): string {
        return textId;
    }

    /**
     * Dialog Module
     * Using for display info or confirm dialog
     */
    export module dialog {
        interface DialogHeader {
            icon?: string;
            text?: string;
        }
        interface Message {
            d: string;
            messageParams?: any[];
        }
        export function getMaxZIndex() {
            let overlayElements = parent.$(".ui-widget-overlay");
            var max = 12000;
            if (overlayElements.length > 0) {
                let zIndexs = _.map(overlayElements, function(element) { return parseInt($(element).css("z-index")); });
                var temp = _.max(zIndexs);
                max = temp > max ? temp : max;
            }
            return max;
        }
        function createNoticeDialog(message, buttons, header?: DialogHeader) {
            var $control = $('<div/>').addClass('control').addClass("pre");
            let text;
            if (typeof message === "object") {
                //business exception
                if (message.message) {
                    text = message.message;
                    if (message.messageId) {
                        $control.append(message.messageId);
                    }
                } else {
                    text = nts.uk.resource.getMessage(message.messageId, message.messageParams);
                    $control.append(message.messageId);
                }

            } else {
                text = message;
            }
            text = text.replace(/\n/g, '<br />');

            var $this = window.parent.$('<div/>').addClass('notice-dialog')
                .append($('<div/>').addClass('text').append(text))
                .append($control)
                .appendTo('body')
                .dialog({
                    dialogClass: "no-close-btn",
                    width: 'auto',
                    modal: true,
                    minWidth: 300,
                    maxWidth: 800,
                    maxHeight: 400,
                    closeOnEscape: false,
                    buttons: buttons,
                    open: function() {
                        $(this).closest('.ui-dialog').css('z-index', getMaxZIndex() + 2);
                        $('.ui-widget-overlay').last().css('z-index', getMaxZIndex() + 1);
                        $(this).parent().find('.ui-dialog-buttonset > button:first-child').focus();
                        $(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');

                        //add header icon if it has
                        if (header && header.icon) {
                            let $headerContainer = $("<div'></div>").addClass("ui-dialog-titlebar-container");
                            $headerContainer.append($("<img>").attr("src", header.icon).addClass("ui-dialog-titlebar-icon"));
                            $headerContainer.append($(this).parent().find(".ui-dialog-title"));
                            $(this).parent().children(".ui-dialog-titlebar").prepend($headerContainer);
                        }
                    },
                    close: function(event) {
                        window.parent.$(this).dialog('destroy');
                        window.parent.$(event.target).remove();
                    }
                });
            $this.dialogPositionControl();
            //add header text if it has
            if (header && header.text) {
                $this.dialog("option", "title", header.text);
            }
            return $this;
        }

		/**
		 * Show information dialog.
		 * 
		 * @param {String}
		 *			text information text
		 * @returns handler
		 */
        export function info(text) {
            var then = $.noop;
            var $dialog = window.parent.$('<div/>').hide();
            $(function() {
                $dialog.appendTo('body').dialog({
                    autoOpen: false
                });
            })

            setTimeout(function() {
                var $this = createNoticeDialog(
                    text,
                    [{
                        text: toBeResource.yes,
                        "class": "large",
                        click: function() {
                            $this.dialog('close');
                            then();
                        }
                    }], { icon: "/nts.uk.com.js.web/lib/nittsu/ui/style/images/infor.png", text: toBeResource.info });
            }, 0);

            return {
                then: function(callback) {
                    then = callback;
                }
            };
        };
        export function alertError(message) {
            var then = $.noop;
            var $dialog = window.parent.$('<div/>').hide();
            $(function() {
                $dialog.appendTo('body').dialog({
                    autoOpen: false
                });
            })

            setTimeout(function() {
                var $this = createNoticeDialog(
                    message,
                    [{
                        text: toBeResource.yes,
                        "class": "large",
                        click: function() {
                            $this.dialog('close');
                            then();
                        }
                    }], { icon: "/nts.uk.com.js.web/lib/nittsu/ui/style/images/error.png", text: toBeResource.error });
            }, 0);

            return {
                then: function(callback) {
                    then = callback;
                }
            };
        }
		/**
		 * Show alert dialog.
		 * 
		 * @param {String}
		 *			text information text
		 * @returns handler
		 */
        export function alert(text) {  
            var then = $.noop;
            var $dialog = parent.$('<div/>').hide();
            $(function() {
                $dialog.appendTo('body').dialog({
                    autoOpen: false
                });
            })

            setTimeout(function() {
                var $this = createNoticeDialog(
                    text,
                    [{
                        text: toBeResource.yes,
                        "class": "large",
                        click: function() {
                            $this.dialog('close');
                            then();
                        }
                    }], { text: nts.uk.resource.getText(toBeResource.warn) });
            }, 0);

            return {
                then: function(callback) {
                    then = callback;
                }
            };
        };

		/**
		 * Show confirm dialog.
		 * 
		 * @param {String}
		 *			text information text
		 * @returns handler
		 */
        export function confirm(text) {
            var handleYes = $.noop;
            var handleNo = $.noop;
            var handleCancel = $.noop;
            var handleThen = $.noop;
            var hasNoButton = true;
            var hasCancelButton = false;

            var handlers = {
                ifYes: function(handler) {
                    handleYes = handler;
                    return handlers;
                },
                ifCancel: function(handler) {
                    hasNoButton = false;
                    hasCancelButton = true;
                    handleCancel = handler;
                    return handlers;
                },
                ifNo: function(handler) {
                    hasNoButton = true;
                    handleNo = handler;
                    return handlers;
                },
                then: function(handler) {
                    handleThen = handler;
                    return handlers;
                }
            };

            setTimeout(function() {

                var buttons = [];
                // yes button
                buttons.push({
                    text: toBeResource.yes,
                    "class": "yes large danger",
                    click: function() {
                        $this.dialog('close');
                        handleYes();
                        handleThen();
                    }
                });
                // no button
                if (hasNoButton) {
                    buttons.push({
                        text: toBeResource.no,
                        "class": "no large",
                        click: function() {
                            $this.dialog('close');
                            handleNo();
                            handleThen();
                        }
                    });
                }
                // cancel button
                if (hasCancelButton) {
                    buttons.push({
                        text: toBeResource.cancel,
                        "class": "cancel large",
                        click: function() {
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
        
        function addError(errorBody: JQuery, error: any, idx: number){
            let row = $("<tr/>");
            row.append("<td style='display: none;'>" + idx + "/td><td>" + error["message"] + "</td><td>" + error["messageId"] + "</td>");
            let nameId = error["supplements"]["NameID"];   
            if (!util.isNullOrUndefined(nameId)) {
                row.click(function(evt, ui){
                    let element = $("body").find('[NameID="' + nameId + '"]');
                    let tab = element.closest("[role='tabpanel']");
                    while(!util.isNullOrEmpty(tab)){
                        let tabId = tab.attr("id");
                        tab.siblings(":first").children("li[aria-controls='" + tabId + "']").children("a").click();
                        tab = tab.parent().closest("[role='tabpanel']");
                    } 
                    element.focus();
                    let $dialogContainer = errorBody.closest(".bundled-errors-alert").closest("[role='dialog']");
                    let $self = nts.uk.ui.windows.getSelf();
                    let additonalTop = 0;
                    let additonalLeft = 0;
                    if(!$self.isRoot) {
                        let $currentDialog = $self.$dialog.closest("[role='dialog']");
                        let $currentHeadBar = $currentDialog.find(".ui-dialog-titlebar");
                        let currentDialogOffset = $currentDialog.offset();
                        additonalTop = currentDialogOffset.top+ $currentHeadBar.height();
                        additonalLeft = currentDialogOffset.left;
                    }
                    
                    let currentControlOffset = element.offset();
                    let top = additonalTop + currentControlOffset.top  + element.outerHeight() - window.scrollY;
                    let left = additonalLeft + currentControlOffset.left - window.scrollX;
                    let $errorDialogOffset = $dialogContainer.offset();
                    let maxLeft = $errorDialogOffset.left + $dialogContainer.width();
                    let maxTop = $errorDialogOffset.top + $dialogContainer.height();
                    if($errorDialogOffset.top < top && top < maxTop){
                        $dialogContainer.css("top", top + 15);
                    }
                    if (($errorDialogOffset.left < left && left < maxLeft) ){
                        $dialogContainer.css("left", left);
                    }
                });    
            }
            row.appendTo(errorBody);  
        }
        
        function getRoot(): JQuery {
            let self = nts.uk.ui.windows.getSelf();
            while(!self.isRoot){
                self = self.parent; 
            }
            return $(self.globalContext.document).find("body");
        }
        
        export function bundledErrors(errors) {
            var then = $.noop;
            let id = util.randomId();
            let container = $("<div id='" + id + "' class='bundled-errors-alert'/>"), 
                functionArea = $("<div id='functions-area-bottom'/>"),
                errorBoard = $(`<div id='error-board'>    <table> <thead> <tr>    <th style='width: auto;'>`
                     + toBeResource.errorContent + `</th><th style='display: none;'/>    <th style='width: 150px;'>`
                     + toBeResource.errorCode + `</th>   </tr>   </thead>    <tbody/>    </table> </div>`),
                closeButton = $("<button class='ntsButton ntsClose large'/>");
            
            let errorBody = errorBoard.find("tbody");
            if($.isArray(errors["errors"])) {
                 _.forEach(errors["errors"], function(error, idx: number){ 
                    addError(errorBody, error, idx + 1);  
                 });   
            } else {
                return alertError(errors);
            }
                       
            closeButton.appendTo(functionArea);
            functionArea.appendTo(container);
            errorBoard.appendTo(container);
            container.appendTo(getRoot()); 
            
            setTimeout(function() {
                container.dialog({ 
                    title: toBeResource.errorList,   
                    dialogClass: "no-close-btn",
                    modal: false,
                    resizable: false,
                    width: 450,
                    maxHeight: 500,
                    closeOnEscape: false,
                    open: function() {
                        errorBoard.css({"overflow": "auto", "max-height" : "300px", "margin-bottom": "65px"});
                        functionArea.css({"left": "0px"});
                        closeButton.text(toBeResource.close).click(function(evt){
                            container.dialog("destroy");  
                            container.remove();
                            then();
                        });
                        
                        container.closest("div[role='dialog']").position({ my: "center", at: "center", of: window.parent });
                    },
                    close: function(event) {
                    }
                }).dialogPositionControl();
            }, 0);
            
            return {
                then: function(callback) {
                    then = callback;
                }
            };
        };
    }

    export var confirmSave: (dirtyChecker: DirtyChecker) => any;
    confirmSave = function(dirtyChecker: DirtyChecker) {
        var frame = windows.getSelf();
        if (frame.$dialog === undefined || frame.$dialog === null) {
            confirmSaveWindow(dirtyChecker);
        } else {
            confirmSaveDialog(dirtyChecker, frame.$dialog);
        }
    }
    function confirmSaveWindow(dirtyChecker: DirtyChecker) {

        var beforeunloadHandler = function(e) {
            if (dirtyChecker.isDirty()) {
                return "ban co muon save hok?";
                //return nts.ui.message('Com_0000105');
            }
        };

        confirmSaveEnable(beforeunloadHandler);
    }

    function confirmSaveDialog(dirtyChecker: DirtyChecker, dialog: JQuery) {
        //dialog* any;
        var beforeunloadHandler = function(e) {
            if (dirtyChecker.isDirty()) {
                e.preventDefault();
                nts.uk.ui.dialog.confirm("Are you sure you want to leave the page?")
                    .ifYes(function() {
                        dirtyChecker.reset();
                        dialog.dialog("close");
                    }).ifNo(function() {
                    })
                //return nts.ui.message('Com_0000105');
            }
        };

        confirmSaveEnableDialog(beforeunloadHandler, dialog);
    }

    export function confirmSaveEnableDialog(beforeunloadHandler, dialog) {
        dialog.on("dialogbeforeclose", beforeunloadHandler);
    };

    export function confirmSaveDisableDialog(dialog) {
        dialog.on("dialogbeforeclose", function() { });
    };

    export function confirmSaveEnable(beforeunloadHandler) {
        $(window).bind('beforeunload', beforeunloadHandler);
    };

    export function confirmSaveDisable() {
        $(window).unbind('beforeunload');
    };

    /**
     * Block UI Module
     * Using for blocking UI when action in progress
     */
    export module block {

        export function invisible() {
            let rect = calcRect();

            (<any>$).blockUI({
                message: null,
                overlayCSS: { opacity: 0 },
                css: {
                    width: rect.width,
                    left: rect.left
                }
            });
        }

        export function grayout() {
            let rect = calcRect();

            (<any>$).blockUI({
                message: '<div class="block-ui-message">' + toBeResource.plzWait + '</div>',
                fadeIn: 200,
                css: {
                    width: rect.width,
                    left: rect.left
                }
            });
        }

        export function clear() {
            (<any>$).unblockUI({
                fadeOut: 200
            });
        }

        function calcRect() {
            let width = 220;
            let left = ($(window).width() - width) / 2;
            return {
                width: width,
                left: left
            };
        }
    }

    export class DirtyChecker {

        targetViewModel: KnockoutObservable<any>;
        initialState: string;

        constructor(targetViewModelObservable: KnockoutObservable<any>) {
            this.targetViewModel = targetViewModelObservable;
            this.initialState = this.getCurrentState();
        }

        getCurrentState() {
            return ko.toJSON(this.targetViewModel());
        }

        reset() {
            this.initialState = this.getCurrentState();
        }

        isDirty() {
            return this.initialState !== this.getCurrentState();
        }
    }

    /**
     * Utilities for IgniteUI
     */
    export module ig {

        export module grid {
            
            export function getScrollContainer($grid: JQuery): JQuery {
                let $scroll: any = $grid.igGrid("scrollContainer");
                if ($scroll.length === 1) return $scroll;
                return $("#" + $grid.attr("id") + "_scrollContainer");
            }

            export function getRowIdFrom($anyElementInRow: JQuery): any {
                return $anyElementInRow.closest('tr').attr('data-id');
            }

            export function getRowIndexFrom($anyElementInRow: JQuery): number {
                return parseInt($anyElementInRow.closest('tr').attr('data-row-idx'), 10);
            }
            
            export function expose(targetRow: any, $grid: JQuery) {
                let $scroll: any = getScrollContainer($grid);
                $scroll.exposeVertically(targetRow.element);
            }

            export module virtual {

                export function getDisplayContainer(gridId: String) {
                    return $('#' + gridId + '_displayContainer');
                }

                export function getVisibleRows(gridId: String) {
                    return $('#' + gridId + ' > tbody > tr:visible');
                }

                export function getFirstVisibleRow(gridId: String) {
                    let top = getDisplayContainer(gridId).scrollTop();
                    let visibleRows = getVisibleRows(gridId);
                    for (var i = 0; i < visibleRows.length; i++) {
                        let $row = $(visibleRows[i]);
                        if (visibleRows[i].offsetTop + $row.height() > top) {
                            return $row;
                        }
                    }
                }

                export function getLastVisibleRow(gridId: String) {
                    let $displayContainer = getDisplayContainer(gridId);
                    let bottom = $displayContainer.scrollTop() + $displayContainer.height();
                    return getVisibleRows(gridId).filter(function() {
                        return this.offsetTop < bottom;
                    }).last();
                }
                
                export function expose(targetRow: any, $grid: JQuery) {
                    
                    if (targetRow.index === undefined) {
                        $grid.igGrid("virtualScrollTo", dataSource.getIndexOfKey(targetRow.id, $grid));
                        return;
                    }
                    
                    let rowHeight = targetRow.element.outerHeight();
                    let targetTop = targetRow.index * rowHeight;
                    let targetBottom = targetTop + rowHeight;
                    
                    let $scroll = getScrollContainer($grid);
                    let viewHeight = $scroll.height();
                    let viewTop = $scroll.scrollTop();
                    let viewBottom = viewTop + viewHeight;
                    
                    if (viewTop <= targetTop && targetBottom <= viewBottom) {
                        return;
                    }
                    
                    $grid.igGrid("virtualScrollTo", targetRow.index);
                }
            }
            
            export module dataSource {
                
                export function getIndexOfKey(targetKey: any, $grid: JQuery) {
                    let option = $grid.igGrid("option");
                    return _.findIndex(
                        option.dataSource,
                        s => s[option.primaryKey].toString() === targetKey.toString());
                }
            }

            export module header {

                export function getCell(gridId: String, columnKey: String) {
                    let $headers: JQuery = <any>$('#' + gridId).igGrid("headersTable");
                    return $headers.find('#' + gridId + '_' + columnKey);
                }

                export function getLabel(gridId: String, columnKey: String) {
                    return getCell(gridId, columnKey).find('span');
                }
            }
        }
        
        export module tree {
            export module grid {
                export function expandTo(targetKey: any, $treeGrid: JQuery) {
                    let option = $treeGrid.igTreeGrid("option");
                    let ancestorKeys = dataSource.collectAncestorKeys(targetKey, option.dataSource, option.primaryKey, option.childDataKey);
                    if (ancestorKeys === null) {
                        return;
                    }
                    
                    let expand = (currentIndex) => {
                        if (currentIndex >= ancestorKeys.length) return;
                        $treeGrid.igTreeGrid("expandRow", ancestorKeys[currentIndex]);
                        setTimeout(() => { expand(currentIndex + 1); }, 0);
                    };
                    
                    expand(0);
                    
                    setTimeout(() => {
                        scrollTo(targetKey, $treeGrid);
                    }, 1);
                }
                
                export function scrollTo(targetKey: any, $treeGrid: JQuery) {
                    let $scroll: any = $treeGrid.igTreeGrid("scrollContainer");
                    let $targetNode = $treeGrid.find("tr[data-id='" + targetKey + "']").first();
                    if ($targetNode.length === 0) return;
                    
                    $scroll.exposeVertically($targetNode);
                }
            }
            
            export module dataSource {
                export function collectAncestorKeys(targetKey: any, dataSource: any[], primaryKey: string, childDataKey: string): any[] {
                    if (typeof dataSource === "undefined") {
                        return null;
                    }
                    
                    for (var i = 0, len = dataSource.length; i < len; i++) {
                        let currentData: any = dataSource[i];
                        if (currentData[primaryKey] === targetKey) {
                            return [targetKey];
                        }
                        
                        let children: any[] = currentData[childDataKey];
                        let results = collectAncestorKeys(targetKey, children, primaryKey, childDataKey);
                        if (results !== null) {
                            results.unshift(currentData[primaryKey]);
                            return results;
                        }
                    }
                    
                    return null;
                }
            }
        }
    }

    module smallExtensions {

        $(() => {
            $(document).on('mouseenter', '.limited-label', e => {
                let $label = $(e.target);

                // Check if contents is overflow
                if ($label.outerWidth() < $label[0].scrollWidth) {
                    let $view = $('<div />').addClass('limited-label-view')
                        .text($label.text())
                        .appendTo('body')
                        .position({
                            my: 'left top',
                            at: 'left bottom',
                            of: $label,
                            collision: 'flip'
                        });

                    $label.bind('mouseleave.limitedlabel', () => {
                        $label.unbind('mouseleave.limitedlabel');
                        $view.remove();
                    });
                }
            });
        });
    }
}