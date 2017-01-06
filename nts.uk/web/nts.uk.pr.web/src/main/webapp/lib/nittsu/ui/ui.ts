module nts.uk.ui {

	export module windows {

		var MAIN_WINDOW_ID = 'MAIN_WINDOW';

		var DEFAULT_DIALOG_OPTIONS = {
			autoOpen: false,
			draggable: true,
			resizable: false,
			create: function(event) {
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
			   if(!isNaN(height)) {
				   this.$dialog.dialog('option', {
					   height: height			  
				   });
				   this.$dialog.resize();
			   }
			}
			setWidth(width: any) {
			   if(!isNaN(width)) { 
				   this.$dialog.dialog('option', {
					   width: width								
				   });
				   this.$dialog.resize();
			   }
			}
			setSize(height, width) {
			   if(!isNaN(width) && !isNaN(height)) { 
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

					this.$dialog.dialog('option', {
						width: options.width || this.globalContext.dialogSize.width,
						height: options.height || this.globalContext.dialogSize.height,
						title: options.title || "dialog",
						resizable: true,
						beforeClose: function() {
							//return dialogWindow.__viewContext.dialog.beforeClose();
						}
					}).dialog('open');
				});

				this.globalContext.location.href = request.resolvePath(path);
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
				})
					.appendTo(this.$dialog);

				this.setGlobal((<any>this.$iframe[0]).contentWindow);
			}

			onClosed(callback: () => void) {
				this.onClosedHandler = function(){
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
				if(persist || isRoot){
					this.shared[key] = data;	
				}else {
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

			export function modal(path: string, options?: any) {
				options = options || {};
				options.modal = true;
				return open(path, options);
			}

			export function modeless(path: string, options?: any) {
				options = options || {};
				options.modal = false;
				return open(path, options);
			}

			export function open(path: string, options?: any) {
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
					open: function() {
						$(this).closest('.ui-dialog').css('z-index', 120001);
						$('.ui-widget-overlay').last().css('z-index', 120000);
						$(this).parent().find('.ui-dialog-buttonset > button:first-child').focus();
						$(this).parent().find('.ui-dialog-buttonset > button').removeClass('ui-button ui-corner-all ui-widget');
					},
					close: function(event) {
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
		export function info(text) {
			var then = $.noop;
			var $dialog = $('<div/>').hide();
			$(function() {
				$dialog.appendTo('body').dialog({
					autoOpen: false
				});
			})

			setTimeout(function() {
				var $this = createNoticeDialog(
					text,
					[{
						text: "はい",
						"class": "large",
						click: function() {
							$this.dialog('close');
							then();
						}
					}]);
			}, 0);
			
			return {
				then: function(callback) {
					then = callback;
				}
			};
		};

		/**
		 * Show alert dialog.
		 * 
		 * @param {String}
		 *			text information text
		 * @returns handler
		 */
		export function alert(text) {
			return info(text);
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
			var hasCancelButton = false;

			var handlers = {
				ifYes: function(handler) {
					handleYes = handler;
					return handlers;
				},
				ifNo: function(handler) {
					handleNo = handler;
					return handlers;
				},
				ifCancel: function(handler) {
					hasCancelButton = true;
					handleCancel = handler;
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
					text: "はい",
					"class": "yes large danger",
					click: function() {
						$this.dialog('close');
						handleYes();
						handleThen();
					}
				});
				// no button
				buttons.push({
					text: "いいえ",
					"class": "no large",
					click: function() {
						$this.dialog('close');
						handleNo();
						handleThen();
					}
				});
				// cancel button
				if (hasCancelButton) {
					buttons.push({
						text: "Cancel",
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
	}
	
	export module contextmenu {
        
        export class ContextMenu {
            guid: string;
            selector: string;
            items: Array<ContextMenuItem>;
            enable: boolean;
            private target: string;
            
            constructor(selector: string, items: Array<ContextMenuItem>, enable?: boolean) {
                this.selector = selector;
                this.items = items;
                this.enable = (enable !== undefined) ? enable : true;
                this.init();
            }
            
            init() {
                // Initial
                var self = this;
                self.guid = nts.uk.util.randomId();
                var $contextMenu = $("<ul id='" + self.guid + "' class='ntsContextMenu'></ul>").hide();
                this.createMenuItems($contextMenu);
                $('body').append($contextMenu);
                
                // Binding contextmenu event
                $("body").on("contextmenu", self.selector, function(event) {
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
                $("body").on("mousedown", function() {
                    if (!$contextMenu.is(event.target) && $contextMenu.has(event.target).length === 0) {
                        $contextMenu.hide();
                    }
                });
            }
            
            destroy() {
                // Unbind contextmenu event
                $("body").off("contextmenu", this.selector);
                $("#" + this.guid).remove();
            }
            
            refresh() {
                this.destroy();
                this.init();
            }
            
            getItem(target: any) {
                if (typeof target === "number") {
                    return this.items[target];
                }
                else if (typeof target === "string") {
                    return _.find(this.items, ["key", target]);
                }
                else {
                    return undefined;
                }
            }
            
            addItem(item: ContextMenuItem) {
                this.items.push(item);
                this.refresh();
            }
            
            removeItem(target: any) {
                var item = this.getItem(target);
                if (item !== undefined) {
                    _.remove(this.items, item);
                    this.refresh();
                }
            }
            
            setEnable(enable: boolean) {
                this.enable = enable;
            }
            
            setEnableItem(enable: boolean, target: any) {
                var item = this.getItem(target);
                item.enable = enable;
                this.refresh();
            }
            
            setVisibleItem(visible: boolean, target: any) {
                var item = this.getItem(target);
                item.visible = visible;
                this.refresh();
            }

            
            private createMenuItems (container: Jquery) {
                var self = this;
                _.forEach(self.items, function(item) {
                    if (item.key !== "divider") {
                        let menuClasses = "menu-item ";
                        menuClasses += (item.enable === true) ? "" : "disabled ";
                        menuClasses += (item.visible === true) ? "" : "hidden ";
                        let menuItem = $("<li class='" + menuClasses + "'><span class='menu-icon " + item.icon + "'></span>" + item.text + "</li>")
                                        .data("key", item.key)
                                        .on("click", function() {
                                            if (!$(this).hasClass("disabled")) {
                                                item.handler(self.target);
                                                container.hide();
                                            }
                                        }).appendTo(container);
                    }
                    else {
                        let menuItem = $("<li class='menu-item divider'></li>").appendTo(container);
                    }
                });
            }
        }
        
        export class ContextMenuItem {
            key: string;
            text: string;
            handler: (ui: any) => void;
            icon: string;
            visible: boolean;
            enable: boolean;
            
            constructor(key: string, text?: string, handler?: (ui: any) => void, icon?: string, visible?: boolean, enable?: boolean) {
                this.key = key;
                this.text = text;
                this.handler = (handler !== undefined) ? handler : $.noop;
                this.icon = (icon) ? icon : "";
                this.visible = (visible !== undefined) ? visible : true;
                this.enable = (enable !== undefined) ? enable : true;
            }
        }
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

	export class DirtyChecker {

		targetViewModel: KnockoutObservable<any>;
		initialState: string;

		constructor(targetViewModelObservable: KnockoutObservable<any>) {
			this.targetViewModel = targetViewModelObservable;
			this.initialState = this.getCurrentState();
		}

		getCurrentState() {
			return ko.mapping.toJSON(this.targetViewModel());
		}

		reset() {
			this.initialState = this.getCurrentState();
		}

		isDirty() {
			return this.initialState !== this.getCurrentState();
		}
	}
}