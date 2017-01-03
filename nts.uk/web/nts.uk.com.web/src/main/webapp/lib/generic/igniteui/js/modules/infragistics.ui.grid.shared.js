/*
 * Infragistics.Web.ClientUI Grid Modal Dialog 16.1.20161.2145
 *
 * Copyright (c) 2011 Infragistics Inc.
 * <Licensing info>
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.shared.js
 *	infragistics.util.js
 */

/*global jQuery, window, Class */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	/*
		igGridModalDialog summary
		Modal dialog will show content for advanced group by, column chooser and multiple sorting
	*/
	$.widget("ui.igGridModalDialog", {
		options: {
			buttonApplyText: "Apply",
			buttonCancelText: "Cancel",
			buttonApplyTitle: null,
			buttonCancelTitle: null,
			modalDialogCaptionText: "Header",
			/* type="number" The default modal dialog width in pixels. */
			modalDialogWidth: 250,
			/* type="number" The default modal dialog height in pixels. */
			modalDialogHeight: "",
			renderFooterButtons: true,
			animationDuration: 200,
			buttonApplyDisabled: false,
			/* type="bool" If true and Enter is pressed - close modal dialog(NOTE: buttonApplyDisabled should be set to false - otherwise this options is ignored) */
			closeModalDialogOnEnter: false,
			/* type="number" tab index to assign to containers and buttons inside the dialog */
			tabIndex: null
		},
		css: {
			/*jscs:disable*/
			/* Classes applied to the modal dialog element */
			"modalDialog": "ui-dialog ui-draggable ui-resizable ui-iggrid-dialog ui-widget ui-widget-content ui-corner-all",
			/* Classes applied to the modal dialog dialog header caption area */
			"modalDialogHeaderCaption": "ui-dialog-titlebar ui-widget-header ui-corner-top ui-helper-reset ui-corner-all ui-helper-clearfix",
			/* Classes applied to the modal dialog dialog header caption title */
			"modalDialogHeaderCaptionTitle": "ui-dialog-title",
			/* Classes applied to the modal dialog dialog content */
			"modalDialogContent": "ui-dialog-content ui-widget-content",
			/* Classes applied to the modal dialog handlebar at the bottom */
			"modalDialogHandleBar": "",
			/* Classes applied to button container at the caption */
			"captionButtonContainer": "ui-iggrid-modaldialog-caption-buttoncontainer",
			/* Classes applied to the footer container at the bottom */
			"modalDialogFooter": "ui-dialog-buttonpane ui-widget-content ui-helper-clearfix",
			/* Classes applied to button set in the footer */
			"buttonset": "ui-dialog-buttonset",
			/* Classes applied to block area over which it is shown modal dialog */
			"blockArea": "ui-widget-overlay ui-iggrid-blockarea"
			/*jscs:enable*/
		},
		events: {
			/* cancel="true" event fired before the modal dialog is opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.owner.grid to get the reference to the igGrid widget.
			*/
			modalDialogOpening: "modalDialogOpening",
			/* event fired after the modal dialog is already opened.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			modalDialogOpened: "modalDialogOpened",
			/* event fired every time the modal dialog changes its position.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			Use ui.originalPosition to get the original position of the modal dialog div as { top, left } object, relative to the page.
			Use ui.position to get the current position of the modal dialog div as { top, left } object, relative to the page.
			*/
			modalDialogMoving: "modalDialogMoving",
			/* cancel="true" event fired before the modal dialog is closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			modalDialogClosing: "modalDialogClosing",
			/* event fired after the modal dialog has been closed.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			modalDialogClosed: "modalDialogClosed",
			/* cancel="true" event fired before the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			modalDialogContentsRendering: "modalDialogContentsRendering",
			/* event fired after the contents of the modal dialog are rendered.
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			modalDialogContentsRendered: "modalDialogContentsRendered",
			/* cancel="true" event fired when the button OK/Apply is clicked
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			buttonOKClick: "buttonOKClick",
			/* cancel="true" event fired when the button Cancel is clicked
			The handler function takes arguments evt and ui.
			Use ui.owner to get the reference to the igGridModalDialog widget.
			Use ui.modalDialog to get the reference to the igGridModalDialog element
			*/
			buttonCancelClick: "buttonCancelClick"
		},
		_createWidget: function (options) {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			/* this.options.columnSettings = []; */
			/* M.H. 8 May 2012 Fix for bug #110344 */
			this.gridContainer = options.gridContainer;
			this.containment = options.containment || options.gridContainer;
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		_create: function () {
			this._setGridWidthHeight();
			this._renderModalDialog();
		},

		_setGridWidthHeight: function () {
			this.element
				.css("width", this.options.modalDialogWidth)
				.css("height", this.options.modalDialogHeight);
		},
		_checkModalDialogFocus: function () {
			// if modal dialog is opened and the user starts tabbing through grid elements(inside grid container) - like grid cells/ grid header cells/etc. focus first element inside dialog
			// M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used and one of the dialogs is displayed you can move focus back to the grid (using TAB)
			var self = this;
			self.gridContainer.unbind("keydown.focusChecker");
			self.gridContainer.bind("keydown.focusChecker", function (e) {
				var target, gridContainer, tabElems;
				if (e.keyCode === $.ui.keyCode.TAB) {
					target = document.activeElement;
					gridContainer = self.gridContainer[ 0 ];
					if (!target || !gridContainer) {
						return;
					}
					if (target === gridContainer ||
							($.contains(gridContainer, target) &&
								!$.contains(self.element[ 0 ], target))) {
						tabElems = $(":tabbable", self.element);
						tabElems.first().focus();
						return;
					}
				}
			});
		},
		openModalDialog: function () {
			var maxZ = 10000, rOffset, self = this,
				scrollLeft, scrollTop, pageLeft, pageTop, pageRight,
				pageBottom, pos, h, w, bh, bw, mdW, mdH, caption,
				content, footer, modalDialogTop, modalDialogLeft,
				noCancel = true,
				modalDialog = this.element,
				block,
				animationDuration = this._getAnimationDuration(),
				blockId = this._id("_modaldialog_block"),
				ti = this.options.tabIndex;
			if (this._modalDialogOpened) {
				return;
			}
			noCancel = this._trigger(
				this.events.modalDialogOpening,
				null,
				{ modalDialog: modalDialog, owner: this }
			);
			if (noCancel) {
				/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used
				and one of the dialogs is displayed you can move focus back to the grid (using TAB) */
				this._checkModalDialogFocus();
				/* M.H. 21 March 2012 Fix for bug #105213 */
				$("#" + blockId).remove();
				this._setGridWidthHeight();
				/* M.H. 9 Nov 2012 Fix for bug #115850 */
				pos = $.ig.util.offset(this.gridContainer);
				w = this.gridContainer.outerWidth();
				h = this.gridContainer.outerHeight();
				scrollLeft = $(window).scrollLeft();
				scrollTop = $(window).scrollTop();
				pageLeft = pos.left - scrollLeft;
				pageTop = pos.top - scrollTop;
				pageRight = pageLeft + w;
				pageBottom = pageTop + h;
				/* M.H. 21 March 2012 Fix for bug #105447 */
				/* V.K Fix for bug #212144 */
				if (typeof (this.options.modalDialogWidth) === "number") {
					mdW = parseInt(this.options.modalDialogWidth, 10);
				} else {
					if (this.options.modalDialogWidth.indexOf("%") > 0) {
						var percentW = parseInt(this.options.modalDialogWidth, 10) / 100;
						if (this.options.containment instanceof jQuery) {
							mdW = this.options.containment.width() * percentW;
						}
						if (typeof (this.options.containment) === "string") {
							mdW = $(window).width() * percentW;
						}
					}
					if (this.options.modalDialogWidth.indexOf("px") > 0) {
						mdW = parseInt(this.options.modalDialogWidth, 10);
					}
				}
				if (typeof (this.options.modalDialogHeight) === "number") {
					mdH = parseInt(this.options.modalDialogHeight, 10);
				} else {
					if (this.options.modalDialogHeight.indexOf("%") > 0) {
						var percentH = parseInt(this.options.modalDialogHeight, 10) / 100;
						if (this.options.containment instanceof jQuery) {
							mdH = this.options.containment.height() * percentH;
						}
						if (typeof (this.options.containment) === "string") {
							mdH = $(window).height() * percentH;
						}
					}
					if (this.options.modalDialogHeight.indexOf("px") > 0) {
						mdH = parseInt(this.options.modalDialogHeight, 10);
					}
				}

				/* M.H. 21 March 2012 Fix for bug #105213 */
				block = $("<div></div>")
					.appendTo(this.gridContainer)
					.attr("id", blockId)
					.css("position", "absolute")
					.addClass(this.css.blockArea).hide();

				if (block.outerWidth() !== this.gridContainer.outerWidth()) {
					block.css("width", this.gridContainer.outerWidth());
				}
				if (block.outerHeight() !== this.gridContainer.outerHeight()) {
					block.css("height", this.gridContainer.outerHeight());
				}
				rOffset = $.ig.util.getRelativeOffset(block);
				block.css({
					left: pos.left - rOffset.left,
					top: pos.top - rOffset.top
				}).fadeToggle(animationDuration);

				if (!mdW) {
					mdW = this.element.width();
				}
				if (!mdH) {
					mdH = this.element.height();
				}
				if (pageLeft < 0) {
					pageLeft = 0;
				}
				if (pageTop < 0) {
					pageTop = 0;
				}

				bw = $(window).width();
				bh = $(window).height();
				if (pageRight > bw) {
					pageRight = bw;
				}
				if (pageBottom > bh) {
					pageBottom = bh;
				}

				modalDialogTop = pageTop + scrollTop + (pageBottom - pageTop) / 2 - mdH / 2;
				modalDialogLeft = pageLeft + scrollLeft + (pageRight - pageLeft) / 2 - mdW / 2;
				/* M.H. 13 March 2012 Fix for bug #104443 */
				if (modalDialogTop < 0) {
					modalDialogTop = pageTop;
				}

				if (modalDialogLeft < 0) {
					modalDialogLeft = pageLeft;
				}
				/* M.H. 10 July 2012 Fix for bug #106407 */
				maxZ = $.ig.getMaxZIndex(this.element[ 0 ].id);
				rOffset = $.ig.util.getRelativeOffset(modalDialog);
				modalDialog
					.css({
						left: modalDialogLeft - rOffset.left,
						top: modalDialogTop - rOffset.top,
						zIndex: maxZ + 2
					})
					.fadeToggle(animationDuration,
					function () {
						var args = {
							modalDialogElement: modalDialog,
							owner: self,
							shouldFocus: true
						};
						self._modalDialogOpened = true;
						self._trigger(
							self.events.modalDialogOpened,
							null,
							args
						);
						if (args.shouldFocus) {
							/* focus modal dialog so it can be closed with the ESCAPE key */
							modalDialog.focus();
						}
					});
				block.css({ zIndex: maxZ + 1 });
				/* add a fixed height to the content div so that it overflows and
				shows a scrollbar, this height should be calculated as the space left
				after the caption and resizing handles are taken into account */
				caption = modalDialog.children("div.ui-dialog-titlebar");
				content = modalDialog.children("div.ui-dialog-content");
				footer = modalDialog.children("div.ui-dialog-buttonpane");
				content.css("height", modalDialog.height() - caption.outerHeight() -
					(content.outerHeight() - content.height()) - footer.outerHeight());
				/* M.H. 9 March 2012 Fix for bug #104366 */
				this.element.find("#" + this._id("content"))
					/* M.H. 19 Nov 2015 Fix for bug 209768: Wrong element on focus using keyboard navigation on Feature Chooser */
					.attr("tabIndex", $.type(ti) === "number" ? ti : 0)
					.css({ width: "" });
			}
		},
		_getAnimationDuration: function () {
			var animationDuration = this.options.animationDuration;

			if (animationDuration === null || animationDuration === undefined) {
				animationDuration = 200;
			}
			return animationDuration;
		},
		_setOption: function (key, value) {
			// M.H. 14 March 2012 Fix for bug #104700
			// handle new settings and update options hash
			$.Widget.prototype._setOption.apply(this, arguments);
			switch (key) {
				case "buttonApplyText":
					this.element.find("#" + this._id("footer_buttonok")).igButton("option", "labelText", value);
					break;
				case "buttonApplyDisabled":
					this.element.find("#" + this._id("footer_buttonok")).igButton("option", "disabled", value);
					break;
				case "buttonCancelText":
					this.element.find("#" + this._id("footer_buttoncancel"))
						.igButton("option", "labelText", value);
					break;
				case "modalDialogCaptionText":
					this.element.find("span.ui-dialog-title:eq(0)").html(value);
					break;
				case "modalDialogWidth":
					this.element
						.css("width", value);
					break;
				case "modalDialogHeight":
					this.element
						.css("height", value);
					break;
				case "renderFooterButtons":
					/* dialog should be re-rendered */
					if (this.element.is(":visible")) {
						this.closeModalDialog();
					}
					this.element.empty();
					this._renderModalDialog();
					break;
				default:
					break;
			}
		},
		closeModalDialog: function (accepted, fromUI) {
			var noCancel = true,
				self = this,
				modalDialog = this.element,
				animationDuration = this._getAnimationDuration();
			if (!this._modalDialogOpened) {
				return;
			}
			noCancel = this._trigger(
				this.events.modalDialogClosing,
				null,
				{
					modalDialog: modalDialog,
					owner: this,
					accepted: !!accepted,
					raiseEvents: fromUI
				});
			if (noCancel) {
				/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used
				and one of the dialogs is displayed you can move focus back to the grid (using TAB) */
				this.gridContainer.unbind("keydown.focusChecker");
				/* M.H. 21 March 2012 Fix for bug #105213 */
				$("#" + this._id("_modaldialog_block")).fadeToggle(animationDuration);
				modalDialog.fadeToggle(animationDuration, null, function () {
					self._trigger(self.events.modalDialogClosed, null, {
						modalDialog: modalDialog,
						owner: self,
						accepted: !!accepted,
						raiseEvents: fromUI
					});
				});
				self._modalDialogOpened = false;
			}
		},
		_modalDialogMove: function (e, ui) {
			var oPos = ui.originalPosition, pos = ui.position;
			this._trigger(this.events.modalDialogMoving, null,
				{
					modalDialog: e.target,
					owner: this,
					originalPosition: oPos,
					position: pos
				});
		},

		/********* Getter methods ********/
		getCaptionButtonContainer: function () {
			return this.element.find("#" + this._id("caption_button_container"));
		},
		getFooter: function () {
			return this.element.find("#" + this._id("footer"));
		},
		getContent: function () {
			return this.element.find("#" + this._id("content"));
		},
		/********* //Getter methods ********/

		_renderModalDialog: function () {
			var self = this,
				css = this.css,
				modalDialog = this.element,
				caption,
				containment,
				modalDialogContent,
				footer,
				o = this.options,
				$buttonSet,
				$buttonOK,
				$buttonCancel,
				noCancel = true,
				ti = this.options.tabIndex;

			modalDialog.css("position", "absolute")
				.addClass(this.css.modalDialog)
				.hide();
			noCancel = this._trigger(
				this.events.modalDialogContentsRendering,
				null,
				{ modalDialog: modalDialog, owner: this }
			);
			if (noCancel) {
				caption = $("<div></div>")
					.addClass(this.css.modalDialogHeaderCaption)
					.appendTo(modalDialog);
				$("<span></span>")
					.text(this.options.modalDialogCaptionText)
					.addClass(this.css.modalDialogHeaderCaptionTitle)
					.appendTo(caption);
				$("<div></div>")
					.attr("id", this._id("caption_button_container"))
					.addClass(css.captionButtonContainer)
					.appendTo(caption);
				modalDialogContent =
					$("<div></div>")
					.css("overflow", "auto")
					.addClass(this.css.modalDialogContent)
					.attr("id", this._id("content"))
					.appendTo(modalDialog);
				if (o.renderFooterButtons === true) {
					footer = $("<div></div>")
						.addClass(this.css.modalDialogFooter)
						.attr("id", this._id("footer"))
						.appendTo(modalDialog);
					$buttonSet = $("<div></div>")
						.addClass(this.css.buttonset)
						.appendTo(footer);
					$buttonOK = $("<button></button>")
						.attr("id", this._id("footer_buttonok"))
						.appendTo($buttonSet);
					if ($.type(ti)) {
						$buttonOK.attr("tabIndex", ti);
					}
					$buttonOK.igButton({
						labelText: o.buttonApplyText,
						title: o.buttonApplyTitle,
						disabled: o.buttonApplyDisabled
					});
					$buttonCancel = $("<button></button>")
						.attr("id", this._id("footer_buttoncancel"))
						.appendTo($buttonSet);
					if ($.type(ti)) {
						$buttonCancel.attr("tabIndex", ti);
					}
					$buttonCancel.igButton({
						labelText: o.buttonCancelText,
						title: o.buttonCancelTitle
					});
					$buttonCancel.bind({
						click: function (e) {
							noCancel = self._trigger(
								self.events.buttonCancelClick,
								e,
								{ modalDialog: modalDialog, owner: self }
							);
							if (noCancel) {
								self.closeModalDialog(false, true);
								e.preventDefault();
								e.stopPropagation();
							}
						}
					});
					$buttonOK.bind({
						click: function (e) {
							var arg = {
								modalDialog: modalDialog,
								owner: self,
								toClose: false
							};
							self._trigger(self.events.buttonOKClick, e, arg);
							if (arg.toClose) {
								self.closeModalDialog(true, true);
								e.preventDefault();
								e.stopPropagation();
							}
						}
					});
				}
				/* M.H. 22 May 2014 Fix for bug #162225: Allow Users to Position the
				igGrid Advanced Filter Dialog Anywhere in Browser Window */
				containment = this.containment;
				if (containment === "window") {
					containment = "document";
				}
				/* $("<div></div>").addClass(this.css.modalDialogHandleBar).appendTo(modalDialog); */
				/* M.H. 23 March 2012 Fix for bug #106009 */
				modalDialog
					.bind({
						keydown: function (e) {
							var tabElems, first, last;
							if (e.keyCode === $.ui.keyCode.ESCAPE) {
								self.closeModalDialog(false, true);
								return;
							}
							if (e.keyCode === $.ui.keyCode.ENTER &&
								/* M.H. 19 Nov 2015 Fix for bug 209857: Modal dialog of Feature Chooser
								for Hiding column closes when pressing Enter */
								self.options.closeModalDialogOnEnter &&
								!self.options.buttonApplyDisabled) {
								self.closeModalDialog(true, true);
								return;
							}
							/* M.H. 15 Apr 2015 Fix for bug 192627: It is not possible to TAB between
							elements inside modal dialog of GroupBy/Multiple Sorting/ColumnChooser when
							modal dialog is shown - it should be possible to tab ONLY between elements
							of the dialog(and do not focus any other element outside the dialog) */
							if (e.keyCode !== $.ui.keyCode.TAB) {
								return;
							}
							tabElems = $(":tabbable", this);
							first = tabElems.first();
							last = tabElems.last();
							if (e.target === last[ 0 ] && !e.shiftKey) {
								first.focus(1);
								return false;
							}
							if (e.target === first[ 0 ] && e.shiftKey) {
								last.focus(1);
								return false;
							}
						}
					})
					.draggable({
						containment: containment,
						handle: caption,
						drag: $.proxy(this._modalDialogMove, this)
					})
					.attr("role", "dialog")
					.attr("tabIndex", -1);
				/* M.H. 20 April 2012 Fix for bug #104800 */
				/* M.H. 16 June 2014 Fix for bug #173464: When modernizr is not referenced modal dialogs are not resizable */
				if (!$.ig.util.isTouch) {
					/* M.H. 22 May 2014 Fix for bug #162225: Allow Users to Position the
					igGrid Advanced Filter Dialog Anywhere in Browser Window */
					modalDialog.resizable({
						alsoResize: modalDialogContent,
						minHeight: modalDialog.outerHeight() / 4,
						minWidth: modalDialog.outerWidth() / 2
					});
					/* M.H. 9 June 2014 Fix for bug #173021: Resizing the edit template
					causes it to shrink when grid height is less. if we use containment
					window, body or document there are issues with resizing */
					if (this.containment !== "window") {
						modalDialog.resizable("option", "containment", "parent");
					}
				}
				this._trigger(
					this.events.modalDialogContentsRendered,
					null,
					{ modalDialog: modalDialog, owner: this }
				);
			}
		},
		/* helper methods */
		_id: function () {
			var i, ar = arguments, res = this.element[ 0 ].id;

			for (i = 0; i < ar.length; i++) {
				res += "_" + ar[ i ];
			}
			return res;
		},
		destroy: function () {
			// M.H. 10 May 2012 Fix for bug #111002
			$("#" + this._id("_modaldialog_block")).remove();
			/* M.H. 29 June 2015 Fix for bug 186625: When a Feature Chooser is used and
			one of the dialogs is displayed you can move focus back to the grid (using TAB) */
			if (this.gridContainer) {
				this.gridContainer.unbind("keydown.focusChecker");
			}
			$.Widget.prototype.destroy.apply(this, arguments);
			return this;
		}
	});
	$.extend($.ui.igGridModalDialog, { version: "16.1.20161.2145" });
}(jQuery));

/*
 * Infragistics.Web.ClientUI Editor Filter 16.1.20161.2145
 *
 * Copyright (c) 2011 Infragistics Inc.
 * <Licensing info>
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 *	jquery-1.9.1.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.shared.js
 *	infragistics.util.js
 */

/*global jQuery, window */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	$.widget("ui.igEditorFilter", {
		setFocus: function (delay, toggle) {
			var provider = this.options.provider;
			if (delay && $.type(delay) === "number" && delay > 0) {
				setTimeout(function () { provider.setFocus(toggle); }, delay);
			} else {
				provider.setFocus(toggle);
			}
		},
		remove: function () {
			if (!this.options.provider.removeFromParent()) {
				var p, e = this.element;
				p = e[ 0 ].parentNode;
				if (p && p.tagName) {
					p.removeChild(e[ 0 ]);
				}
				e = this.validator();
				if (e) {
					e.hide();
				}
			}
		},
		exitEditMode: function () {
			var editor = this.options.provider.editor;
			if (editor && editor._exitEditMode && $.type(editor._exitEditMode) === "function") {
				editor._exitEditMode();
			}
		},
		validator: function () {
			return this.options.provider.validator();
		},
		hasInvalidMessage: function () {
			var validator = this.validator();
			return validator ? validator.getErrorMessages().length > 0 : false;
		},
		destroy: function () {
			this.options.provider.destroy();
			$.Widget.prototype.destroy.call(this);
		}
	});
	$.extend($.ui.igEditorFilter, { version: "16.1.20161.2145" });
	/* editor providers */
	/* M.P.: 183193 - The igniteui.d.ts file is not compilign */
	/* jshint unused:false */
	$.ig.EditorProvider = $.ig.EditorProvider || Class.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			/* create handlers cache */
			this.handlers = {};
			this.userHandlers = {};
			this.options = editorOptions || {};
			/* store owner */
			this.callbacks = callbacks;
			/* store col key correspondence */
			this.columnKey = key;
			/* keydown handler is required for every editor */
			this.handlers.keyDownHandler = $.proxy(this.keyDown, this);
			if (this.options.keydown) {
				/* store user-defined keydown */
				this.userHandlers.keyDown = this.options.keydown;
			}
			this.options.keydown = this.handlers.keyDownHandler;
		},
		keyDown: function (evt, ui) {
			// notify Updating that a key has been pressed
			if (this.callbacks && typeof this.callbacks.keyDown === "function") {
				this.callbacks.keyDown(evt, ui, this.columnKey);
			}
			if (this.userHandlers.keyDown) {
				this.userHandlers.keyDown.apply(this.editor, [ evt, ui ]);
			}
		},
		attachErrorEvents: function (errorShowing, errorShown, errorHidden) {
			this.editor.element.bind({
				"igvalidatorerrorhidden.updating": errorHidden,
				"igvalidatorerrorshowing.updating": errorShowing,
				"igvalidatorerrorshown.updating": errorShown
			});
		},
		getEditor: function () {
			return this.editor;
		},
		refreshValue: function () {
			return true;
		},
		getValue: function () {
			return this.editor.value();
		},
		setValue: function (val) {
			this.editor.value(val);
		},
		setFocus: function (toggle) {
			return null;
		},
		setSize: function (width, height) {
			return null;
		},
		removeFromParent: function () {
			return false;
		},
		destroy: function () {
			this.editor.destroy();
		},
		validator: function () {
			return null;
		},
		validate: function () {
			var validator = this.validator();
			return validator ? validator.isValid() : true;
		},
		requestValidate: function (evt) {
			var validator = this.validator(), valid = true;
			if (validator) {
				validator._forceValidation = true;
				valid = validator._validate(null, evt);
				validator._forceValidation = false;
			}
			return valid;
		},
		isValid: function () {
			return true;
		}
	});
	$.ig.EditorProviderBase = $.ig.EditorProviderBase || $.ig.EditorProvider.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			/* call parent createEditor */
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			/* all editors inheriting from the base one have a text changed event we need to handle */
			this.handlers.textChangeHandler = $.proxy(this.textChanged, this);
			if (this.options.textChanged) {
				/* store user-specified keypress */
				this.userHandlers.textChanged = this.options.textChanged;
			}
			this.options.textChanged = this.handlers.textChangeHandler;
			this.options.tabIndex = tabIndex;
		},
		textChanged: function (evt, ui) {
			// notify Updating that the text changed
			if (this.callbacks && typeof this.callbacks.textChanged === "function") {
				this.callbacks.textChanged(evt, ui, this.columnKey);
			}
			if (this.userHandlers.textChanged) {
				this.userHandlers.textChanged.apply(this.editor, [ evt, ui ]);
			}
		},
		setSize: function (width, height) {
			this.editor._setOption("width", width);
			this.editor._setOption("height", height);
		},
		setFocus: function () {
			this.editor.setFocus();
		},
		removeFromParent: function () {
			//return this.editor.remove();
			var v = this.validator();
			if (v) {
				v.hide();
			}
			this.editor._focused = false;
			this.editor._exitEditMode();
			this.editor.editorContainer().removeClass("ui-state-focus");
			return this.editor.editorContainer().detach();
		},
		destroy: function () {
			this.editor.element.unbind(".updating");
			this.editor.destroy();
		},
		refreshValue: function () {
			if (this.editor._editorInput.is(":focus")) {
				this.editor._processValueChanging(this.editor._editorInput.val());
			}
		},
		validator: function () {
			if ($.type(this.editor.validator) === "function") {
				return this.editor.validator();
			}
			return null;
		},
		isValid: function () {
			return this.editor.isValid();
		}
	});
	$.ig.EditorProviderText = $.ig.EditorProviderText || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igTextEditor) {
				throw new Error($.ig.GridUpdating.locale.igTextEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			element.igTextEditor(this.options);
			this.editor = element.data("igTextEditor");
			return this.editor.editorContainer();
		},
		keyDown: function (evt, ui) {
			// notify Updating that a key has been pressed
			if (this.callbacks && typeof this.callbacks.keyDown === "function") {
				// don't notify if the editor is textarea and alt+enter is pressed
				if (!(ui.key === $.ui.keyCode.ENTER && evt.originalEvent.altKey) ||
					!ui.editorInput.is("textarea")) {
					this.callbacks.keyDown(evt, ui, this.columnKey);
				} else {
					evt.originalEvent.stopPropagation();
				}
			}
			if (this.userHandlers.keyDown) {
				this.userHandlers.keyDown.apply(this.editor, [ evt, ui ]);
			}
		}
	});
	$.ig.EditorProviderNumeric = $.ig.EditorProviderNumeric || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igNumericEditor) {
				throw new Error($.ig.GridUpdating.locale.igNumericEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			if (format === "int") {
				this.options.maxDecimals = 0;
			} else if (format === "double" && !this.options.maxDecimals) {
				this.options.maxDecimals = 100;
			}
			this.options.allowNullValue = true;
			element.igNumericEditor(this.options);
			this.editor = element.data("igNumericEditor");
			return this.editor.editorContainer();
		},
		getValue: function () {
			var val = this.editor.value();
			return isNaN(val) ? null : val;
		}
	});
	/* Currency */
	$.ig.EditorProviderCurrency = $.ig.EditorProviderCurrency || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igCurrencyEditor) {
				throw new Error($.ig.GridUpdating.locale.igCurrencyEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			this.options.displayFactor = this.options.displayFactor || 1;
			this.options.allowNullValue = true;
			element.igCurrencyEditor(this.options);
			this.editor = element.data("igCurrencyEditor");
			return this.editor.editorContainer();
		}
	});
	/* Percent */
	$.ig.EditorProviderPercent = $.ig.EditorProviderPercent || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igPercentEditor) {
				throw new Error($.ig.GridUpdating.locale.igPercentEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			this.options.allowNullValue = true;
			element.igPercentEditor(this.options);
			this.editor = element.data("igPercentEditor");
			return this.editor.editorContainer();
		}
	});
	/* Mask */
	$.ig.EditorProviderMask = $.ig.EditorProviderMask || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igMaskEditor) {
				throw new Error($.ig.GridUpdating.locale.igMaskEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			element.igMaskEditor(this.options);
			this.editor = element.data("igMaskEditor");
			return this.editor.editorContainer();
		}
	});
	/* Date */
	$.ig.EditorProviderDate = $.ig.EditorProviderDate || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igDateEditor) {
				throw new Error($.ig.GridUpdating.locale.igDateEditorException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			if (format && !this.options.dateInputFormat) {
				this.options.dateInputFormat = format;
			}
			this.options.allowNullValue = true;
			element.igDateEditor(this.options);
			this.editor = element.data("igDateEditor");
			return this.editor.editorContainer();
		}
	});
	$.ig.EditorProviderDatePicker = $.ig.EditorProviderDatePicker || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igDatePicker) {
				throw new Error($.ig.GridUpdating.locale.igDatePickerException);
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			if (format && !this.options.dateInputFormat) {
				this.options.dateInputFormat = format;
			}
			this.options.allowNullValue = true;
			element.igDatePicker(this.options);
			this.editor = element.data("igDatePicker");
			return this.editor.editorContainer();
		},
		removeFromParent: function () {
			if (this.editor.dropDownVisible()) {
				// avoid the closing animation to instantly remove the dropdown
				$("#ui-datepicker-div").hide();
			}
			this._super();
		}
	});
	$.ig.EditorProviderBoolean = $.ig.EditorProviderBoolean || $.ig.EditorProviderBase.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			var requiresWrapper;
			if (!this.renderFormat) {
				this.renderFormat = "checkbox";
			}
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			this.handlers.valueChanged = $.proxy(this.valueChanged, this);
			if (this.options.valueChanged) {
				this.userHandlers.valueChanged = this.options.valueChanged;
			}
			this.options.valueChanged = this.handlers.valueChanged;
			switch (this.renderFormat) {
				case "checkbox":
					requiresWrapper = !element;
					element = element || $("<input />");
					if (!element.igCheckboxEditor) {
						throw new Error($.ig.GridUpdating.locale.igCheckboxEditorException);
					}
					element.igCheckboxEditor(this.options);
					this.editor = element.data("igCheckboxEditor");
					if (requiresWrapper) {
						this.outerContainer = $("<div />")
							.css({
								"background": "rgb(255, 255, 255)",
								"outline": "0px",
								"text-align": "center",
								"overflow": "hidden"
							})
							.addClass("ui-igedit ui-igedit-container ui-state-default ui-iggrid-editor");
						this.outerContainer.append(this.editor.editorContainer());
						return this.outerContainer;
					}
					return this.editor.editorContainer();
				case "dropdown":
					this.options.listItems = [ "true", "false" ];
					this.options.dropDownAttachToBody = true;
					this.options.button = "dropdown";
					this.options.isLimitedToListValues = true;
					this.options.dropDownAttachedToBody = true;
					/* this.options.readOnly = true; */
					element = element || $("<span />");
					if (!element.igTextEditor) {
						throw new Error($.ig.GridUpdating.locale.igTextEditorException);
					}
					element.igTextEditor(this.options);
					this.editor = element.data("igTextEditor");
					return this.editor.editorContainer();
			}
		},
		valueChanged: function (evt, ui) {
			// notify Updating that the value changed
			if (this.callbacks && typeof this.callbacks.textChanged === "function") {
				this.callbacks.textChanged(evt, ui, this.columnKey);
			}
			if (this.userHandlers.valueChanged) {
				this.userHandlers.valueChanged.apply(this.editor, [ evt, ui ]);
			}
		},
		refreshValue: function () {
			return false;
		},
		getValue: function () {
			if (this.renderFormat === "checkbox") {
				return this.editor.value();
			}
			return this.editor.value() === "true";
		},
		setValue: function (val) {
			if (this.renderFormat === "checkbox") {
				this.editor.value(val !== null ? val : false);
			} else {
				this.editor.value(val !== null ? String(val) : "false");
			}
		},
		setSize: function (width, height) {
			var cont, chb, defChb;
			if (this.renderFormat === "checkbox" && this.outerContainer) {
				cont = this.outerContainer;
				chb = cont.children().first();
				cont.css("width", width);
				cont.css("height", height);
				chb.css({
					"margin-top": cont.height() / 2 - chb.height() / 2
				});
				defChb = cont.siblings().first().children().first();
				width = defChb.width();
				height = defChb.height();
			}
			this._super(width, height);
		},
		removeFromParent: function () {
			if (this.renderFormat === "checkbox" && this.outerContainer) {
				return this.outerContainer.detach();
			}
			return this._super();
		},
		destroy: function () {
			this.editor.element.unbind(".updating");
			this.editor.destroy();
			if (this.outerContainer && this.outerContainer instanceof jQuery) {
				this.outerContainer.remove();
			}
		}
	});
	/* jshint unused:true */
	$.ig.EditorProviderCombo = $.ig.EditorProviderCombo || $.ig.EditorProvider.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<span />");
			if (!element.igCombo) {
				throw new Error($.ig.GridUpdating.locale.igComboException);
			}
			/* call parent createEditor */
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			this.handlers.selectionChangedHandler = $.proxy(this.selectionChanged, this);
			if (this.options.selectionChanged) {
				this.userHandlers.selectionChanged = this.options.selectionChanged;
			}
			this.options.selectionChanged = this.handlers.selectionChangedHandler;
			this.options.tabIndex = tabIndex;
			if (this.options.id) {
				element.attr("id", this.options.id);
			}
			element.igCombo(this.options);
			this.editor = element.data("igCombo");
			this.editor.textInput().bind("keydown.updating", this.handlers.keyDownHandler);
			/* handler for setting the combo value through the combo api (no events are raised then) */
			if (this.editor._options.internalSelChangeSubs &&
				$.type(this.editor._options.internalSelChangeSubs) === "array") {
				this.handlers.internalSelectionChangedHandler = $.proxy(this.internalSelectionChanged, this);
				this.editor._options.internalSelChangeSubs.push(this.handlers.internalSelectionChangedHandler);
			}
			return element;
		},
		keyDown: function (evt, ui) {
			if (evt.keyCode === $.ui.keyCode.TAB) {
				this.editor.closeDropDown();
			}
			/* notify Updating that a key has been pressed */
			if (this.callbacks && typeof this.callbacks.keyDown === "function") {
				/* don't notify updating if the enter key is used to accept a value from the dropdown */
				if (evt.keyCode !== $.ui.keyCode.ENTER || !this.editor.dropDownOpened()) {
					this.callbacks.keyDown(evt, ui || { owner: this.editor }, this.columnKey);
				}
			}
			if (this.editor.options.allowCustomValue) {
				this.internalSelectionChanged(evt, ui);
			}
			if (this.userHandlers.keyDown) {
				this.userHandlers.keyDown.apply(this.editor, [ evt, ui ]);
			}
		},
		internalSelectionChanged: function (evt, ui) {
			if (this.callbacks && typeof this.callbacks.textChanged === "function") {
				this.callbacks.textChanged(evt, ui, this.columnKey);
			}
		},
		selectionChanged: function (evt, ui) {
			this.internalSelectionChanged(evt, ui);
			if (this.userHandlers.selectionChanged) {
				this.userHandlers.selectionChanged.apply(this.editor, [ evt, ui ]);
			}
		},
		refreshValue: function () {
			this.editor.refreshValue();
		},
		getValue: function () {
			var val = this.editor.value();
			if ($.type(val) === "array") {
				return val.length ? val[ 0 ] : null;
			}
			return val;
		},
		setValue: function (val, fire) {
			// this.editor.deselectAll();
			this.editor.value(val, null, fire);
		},
		setSize: function (width, height) {
			this.editor.element.igCombo({
				width: width,
				height: height
			});
		},
		setFocus: function () {
			this.editor.textInput().focus();
		},
		removeFromParent: function () {
			this.editor.closeDropDown();
			if (this.validator()) {
				this.validator().hide();
			}
			return this.editor.element.closest(".ui-igcombo-wrapper").detach();
		},
		validator: function () {
			return this.editor.validator();
		},
		destroy: function () {
			this.editor.textInput().unbind("keydown.updating");
			this.editor.element.unbind(".updating");
			this.editor.destroy();
		},
		isValid: function () {
			return true;
		}
	});
	$.ig.EditorProviderObjectCombo =
		$.ig.EditorProviderObjectCombo || $.ig.EditorProviderCombo.extend({
		getValue: function () {
			var val = null, arr = [];
			if (this.editor.selectedItems() !== null) {
				if (this.editor.options.multiSelection && this.editor.options.multiSelection.enabled) {
					$(this.editor.selectedItems()).each(function () {
						arr.push(this.data);
					});
					val = arr;
				} else {
					val = this.editor.selectedItems()[ 0 ].data;
				}
			}
			return val;
		},
		setValue: function (val, fire) {
			var arr = [], editor = this.editor;
			editor.deselectAll();
			if (val) {
				if (editor.options.multiSelection && editor.options.multiSelection.enabled) {
					$(val).each(function () {
						arr.push(this[ editor.options.valueKey ]);
					});
					editor.value(arr, null, fire);
				} else {
					editor.value(val[ this.editor.options.valueKey ], null, fire);
				}
			}
		}
	});
	$.ig.EditorProviderRating = $.ig.EditorProviderRating || $.ig.EditorProvider.extend({
		createEditor: function (callbacks, key, editorOptions, tabIndex, format, element) {
			element = element || $("<div />");
			if (!element.igRating) {
				throw new Error($.ig.GridUpdating.locale.igRatingException);
			}
			/* call parent createEditor */
			this._super(callbacks, key, editorOptions, tabIndex, format, element);
			this.handlers.valueChange = $.proxy(this.valueChange, this);
			if (this.options.valueChange) {
				this.userHandlers.valueChange = this.options.valueChange;
			}
			this.options.valueChange = this.handlers.valueChange;
			if (this.options.id) {
				element.attr("id", this.options.id);
			}
			element.igRating(this.options);
			this.editor = element.data("igRating");
			this.handlers.internalValueChange = $.proxy(this.internalValueChange, this);
			this.editor._internalChanged = this.handlers.internalValueChange;
			if (this.editor._foc) {
				this.editor._foc.attr("tabIndex", tabIndex);
				this.editor._foc.keydown(this.handlers.keyDown);
			}
			return element;
		},
		internalValueChange: function (evt, ui) {
			if (this.callbacks && typeof this.callbacks.textChanged === "function") {
				this.callbacks.textChanged(evt, ui, this.columnKey);
			}
		},
		valueChange: function (evt, ui) {
			this.internalValueChange(evt, ui);
			if (this.userHandlers.valueChange) {
				this.userHandlers.valueChange.apply(this.editor, [ evt, ui ]);
			}
		},
		setValue: function (val) {
			return this.editor.value(val || 0);
		},
		setSize: function (width, height) {
			if (!this._once) {
				this.editor._doVotes(this.editor.options);
			}
			this._once = 1;
			var back = this.editor.element.parent().css("backgroundColor");
			/* adjust 2px for borders */
			this.editor.element.css({ width: width, height: height, backgroundColor: back });
		},
		setFocus: function () {
			this.editor.focus();
		},
		validator: function () {
			return this.editor.validator();
		},
		destroy: function () {
			this.editor.element.unbind(".updating");
			this.editor.destroy();
		},
		isValid: function () {
			return true;
		}
	});
}(jQuery));
