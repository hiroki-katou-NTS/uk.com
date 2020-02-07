(function(factory){if(typeof define==="function"&&define.amd){define(["./infragistics.ui.shared","./infragistics.ui.grid.framework"],factory)}else{return factory(jQuery)}})(function($){$.widget("ui.igGridModalDialog",$.ui.igWidget,{options:{modalDialogWidth:250,modalDialogHeight:"",renderFooterButtons:true,animationDuration:200,buttonApplyDisabled:false,closeModalDialogOnEnter:false,tabIndex:null},css:{modalDialog:"ui-dialog ui-draggable ui-resizable ui-iggrid-dialog ui-widget ui-widget-content ui-corner-all",modalDialogHeaderCaption:"ui-dialog-titlebar ui-widget-header ui-corner-top ui-helper-reset ui-corner-all ui-helper-clearfix",modalDialogHeaderCaptionTitle:"ui-dialog-title",modalDialogContent:"ui-dialog-content ui-widget-content",modalDialogHandleBar:"",captionButtonContainer:"ui-iggrid-modaldialog-caption-buttoncontainer",modalDialogFooter:"ui-dialog-buttonpane ui-widget-content ui-helper-clearfix",buttonset:"ui-dialog-buttonset",blockArea:"ui-widget-overlay ui-iggrid-blockarea"},events:{modalDialogOpening:"modalDialogOpening",modalDialogOpened:"modalDialogOpened",modalDialogMoving:"modalDialogMoving",modalDialogClosing:"modalDialogClosing",modalDialogClosed:"modalDialogClosed",modalDialogContentsRendering:"modalDialogContentsRendering",modalDialogContentsRendered:"modalDialogContentsRendered",buttonOKClick:"buttonOKClick",buttonCancelClick:"buttonCancelClick"},_createWidget:function(options){this.gridContainer=options.gridContainer;this.containment=options.containment||options.gridContainer;this._superApply(arguments)},_create:function(){this._setGridWidthHeight();this._renderModalDialog()},_setGridWidthHeight:function(){this.element.css("width",this.options.modalDialogWidth).css("height",this.options.modalDialogHeight)},_checkModalDialogFocus:function(){var self=this;self.gridContainer.unbind("keydown.focusChecker");self.gridContainer.bind("keydown.focusChecker",function(e){var target,gridContainer,tabElems;if(e.keyCode===$.ui.keyCode.TAB){target=document.activeElement;gridContainer=self.gridContainer[0];if(!target||!gridContainer){return}if(target===gridContainer||$.contains(gridContainer,target)&&!$.contains(self.element[0],target)){tabElems=$(":tabbable",self.element);tabElems.first().focus();return}}})},openModalDialog:function(){var maxZ=1e4,rOffset,self=this,scrollLeft,scrollTop,pageLeft,pageTop,pageRight,pageBottom,pos,h,w,bh,bw,mdW,mdH,caption,content,footer,buttonSet,modalDialogTop,modalDialogLeft,noCancel=true,modalDialog=this.element,block,animationDuration=this._getAnimationDuration(),blockId=this._id("_modaldialog_block"),ti=this.options.tabIndex;if(this._modalDialogOpened){return}noCancel=this._trigger(this.events.modalDialogOpening,null,{modalDialog:modalDialog,owner:this});if(noCancel){this._checkModalDialogFocus();$("#"+blockId).remove();this._setGridWidthHeight();pos=$.ig.util.offset(this.gridContainer);w=this.gridContainer.outerWidth();h=this.gridContainer.outerHeight();scrollLeft=$(window).scrollLeft();scrollTop=$(window).scrollTop();pageLeft=pos.left-scrollLeft;pageTop=pos.top-scrollTop;pageRight=pageLeft+w;pageBottom=pageTop+h;if(typeof this.options.modalDialogWidth==="number"){mdW=parseInt(this.options.modalDialogWidth,10)}else{if(this.options.modalDialogWidth.indexOf("%")>0){var percentW=parseInt(this.options.modalDialogWidth,10)/100;if(this.options.containment instanceof $){mdW=this.options.containment.width()*percentW}if(typeof this.options.containment==="string"){mdW=$(window).width()*percentW}}if(this.options.modalDialogWidth.indexOf("px")>0){mdW=parseInt(this.options.modalDialogWidth,10)}}if(typeof this.options.modalDialogHeight==="number"){mdH=parseInt(this.options.modalDialogHeight,10)}else{if(this.options.modalDialogHeight.indexOf("%")>0){var percentH=parseInt(this.options.modalDialogHeight,10)/100;if(this.options.containment instanceof $){mdH=this.options.containment.height()*percentH}if(typeof this.options.containment==="string"){mdH=$(window).height()*percentH}}if(this.options.modalDialogHeight.indexOf("px")>0){mdH=parseInt(this.options.modalDialogHeight,10)}}block=$("<div></div>").appendTo(this.gridContainer).attr("id",blockId).css("position","absolute").addClass(this.css.blockArea).hide();if(block.outerWidth()!==this.gridContainer.outerWidth()){block.css("width",this.gridContainer.outerWidth())}if(block.outerHeight()!==this.gridContainer.outerHeight()){block.css("height",this.gridContainer.outerHeight())}rOffset=$.ig.util.getRelativeOffset(block);block.css({left:pos.left-rOffset.left,top:pos.top-rOffset.top}).fadeToggle(animationDuration);if(!mdW){mdW=this.element.width()}if(!mdH){mdH=this.element.height()}if(pageLeft<0){pageLeft=0}if(pageTop<0){pageTop=0}bw=$(window).width();bh=$(window).height();if(pageRight>bw){pageRight=bw}if(pageBottom>bh){pageBottom=bh}modalDialogTop=pageTop+scrollTop+(pageBottom-pageTop)/2-mdH/2;modalDialogLeft=pageLeft+scrollLeft+(pageRight-pageLeft)/2-mdW/2;if(modalDialogTop<0){modalDialogTop=pageTop}if(modalDialogLeft<0){modalDialogLeft=pageLeft}maxZ=$.ig.getMaxZIndex(this.element[0].id);rOffset=$.ig.util.getRelativeOffset(modalDialog);modalDialog.css({left:modalDialogLeft-rOffset.left,top:modalDialogTop-rOffset.top,zIndex:maxZ+2}).fadeToggle(animationDuration,function(){var args={modalDialogElement:modalDialog,owner:self,shouldFocus:true};self._modalDialogOpened=true;self._trigger(self.events.modalDialogOpened,null,args);if(args.shouldFocus){modalDialog.focus()}});block.css({zIndex:maxZ+1});caption=modalDialog.children("div.ui-dialog-titlebar");content=modalDialog.children("div.ui-dialog-content");footer=modalDialog.children("div.ui-dialog-buttonpane");buttonSet=footer.children(".ui-dialog-buttonset");content.css("height",modalDialog.height()-caption.outerHeight()-(content.outerHeight()-content.height())-footer.outerHeight());this.element.find("#"+this._id("content")).attr("tabIndex",$.type(ti)==="number"?ti:0).css({width:""});if(!$.ig.util.isTouch){modalDialog.resizable({minHeight:modalDialog.outerHeight()/2,minWidth:buttonSet.width()+(content.outerWidth()-content.width())})}}},_getAnimationDuration:function(){var animationDuration=this.options.animationDuration;if(animationDuration===null||animationDuration===undefined){animationDuration=200}return animationDuration},changeLocale:function(){this.element.find("#"+this._id("footer_buttonok")).igButton("option",{labelText:this._getLocaleValue("buttonApplyText"),title:this._getLocaleValue("buttonApplyTitle")});this.element.find("#"+this._id("footer_buttoncancel")).igButton("option",{labelText:this._getLocaleValue("buttonCancelText"),title:this._getLocaleValue("buttonCancelTitle")});this.element.find("span.ui-dialog-title:eq(0)").html(this._getLocaleValue("modalDialogCaptionText"))},_registerWidget:$.noop,_unregisterWidget:$.noop,_setOption:function(key,value){this._superApply(arguments);switch(key){case"buttonApplyDisabled":this.element.find("#"+this._id("footer_buttonok")).igButton("option","disabled",value);break;case"modalDialogWidth":this.element.css("width",value);break;case"modalDialogHeight":this.element.css("height",value);break;case"renderFooterButtons":if(this.element.is(":visible")){this.closeModalDialog()}this.element.empty();this._renderModalDialog();break;default:break}},closeModalDialog:function(accepted,e){var noCancel=true,self=this,modalDialog=this.element,animationDuration=this._getAnimationDuration();if(!this._modalDialogOpened){return}noCancel=this._trigger(this.events.modalDialogClosing,e||null,{modalDialog:modalDialog,owner:this,accepted:!!accepted,raiseEvents:!!e});if(noCancel){this.gridContainer.unbind("keydown.focusChecker");$("#"+this._id("_modaldialog_block")).fadeToggle(animationDuration);modalDialog.fadeToggle(animationDuration,null,function(){self._trigger(self.events.modalDialogClosed,e||null,{modalDialog:modalDialog,owner:self,accepted:!!accepted,raiseEvents:!!e})});self._modalDialogOpened=false}},_modalDialogMove:function(e,ui){var oPos=ui.originalPosition,pos=ui.position;this._trigger(this.events.modalDialogMoving,null,{modalDialog:e.target,owner:this,originalPosition:oPos,position:pos})},getCaptionButtonContainer:function(){return this.element.find("#"+this._id("caption_button_container"))},getFooter:function(){return this.element.find("#"+this._id("footer"))},getContent:function(){return this.element.find("#"+this._id("content"))},_renderModalDialog:function(){var self=this,css=this.css,modalDialog=this.element,caption,containment,modalDialogContent,footer,o=this.options,$buttonSet,$buttonOK,$buttonCancel,noCancel=true,ti=this.options.tabIndex;modalDialog.css("position","absolute").addClass(this.css.modalDialog).hide();noCancel=this._trigger(this.events.modalDialogContentsRendering,null,{modalDialog:modalDialog,owner:this});if(noCancel){caption=$("<div></div>").addClass(this.css.modalDialogHeaderCaption).appendTo(modalDialog);$("<span></span>").text(this._getLocaleValue("modalDialogCaptionText")).addClass(this.css.modalDialogHeaderCaptionTitle).appendTo(caption);$("<div></div>").attr("id",this._id("caption_button_container")).addClass(css.captionButtonContainer).appendTo(caption);modalDialogContent=$("<div></div>").css("overflow","auto").addClass(this.css.modalDialogContent).attr("id",this._id("content")).appendTo(modalDialog);if(o.renderFooterButtons===true){footer=$("<div></div>").addClass(this.css.modalDialogFooter).attr("id",this._id("footer")).appendTo(modalDialog);$buttonSet=$("<div></div>").addClass(this.css.buttonset).appendTo(footer);$buttonOK=$("<button type='button'></button>").attr("id",this._id("footer_buttonok")).appendTo($buttonSet);if($.type(ti)){$buttonOK.attr("tabIndex",ti)}$buttonOK.igButton({labelText:this._getLocaleValue("buttonApplyText"),title:this._getLocaleValue("buttonApplyTitle"),disabled:o.buttonApplyDisabled});$buttonCancel=$("<button type='button'></button>").attr("id",this._id("footer_buttoncancel")).appendTo($buttonSet);if($.type(ti)){$buttonCancel.attr("tabIndex",ti)}$buttonCancel.igButton({labelText:this._getLocaleValue("buttonCancelText"),title:this._getLocaleValue("buttonCancelTitle")});$buttonCancel.bind({click:function(e){noCancel=self._trigger(self.events.buttonCancelClick,e,{modalDialog:modalDialog,owner:self});if(noCancel){self.closeModalDialog(false,e);e.preventDefault();e.stopPropagation()}}});$buttonOK.bind({click:function(e){var arg={modalDialog:modalDialog,owner:self,toClose:false};self._trigger(self.events.buttonOKClick,e,arg);if(arg.toClose){self.closeModalDialog(true,e);e.preventDefault();e.stopPropagation()}}})}containment=this.containment;if(containment==="window"){containment="document"}modalDialog.bind({keydown:function(e){var tabElems,first,last;if(e.keyCode===$.ui.keyCode.ESCAPE){self.closeModalDialog(false,e);return}if(e.keyCode===$.ui.keyCode.ENTER&&self.options.closeModalDialogOnEnter&&!self.options.buttonApplyDisabled){self.closeModalDialog(true,e);return}if(e.keyCode!==$.ui.keyCode.TAB){return}tabElems=$(":tabbable",this);first=tabElems.first();last=tabElems.last();if(e.target===last[0]&&!e.shiftKey){first.focus();return false}if(e.target===first[0]&&e.shiftKey){last.focus();return false}}}).draggable({containment:containment,handle:caption,drag:$.proxy(this._modalDialogMove,this)}).attr("role","dialog").attr("tabIndex",-1);if(!$.ig.util.isTouch){modalDialog.resizable({alsoResize:modalDialogContent});if(this.containment!=="window"){modalDialog.resizable("option","containment","parent")}}this._trigger(this.events.modalDialogContentsRendered,null,{modalDialog:modalDialog,owner:this})}},_id:function(){var i,ar=arguments,res=this.element[0].id;for(i=0;i<ar.length;i++){res+="_"+ar[i]}return res},destroy:function(){$("#"+this._id("_modaldialog_block")).remove();if(this.gridContainer){this.gridContainer.unbind("keydown.focusChecker")}this._superApply(arguments);return this}});$.extend($.ui.igGridModalDialog,{version:"19.1.20"});$.widget("ui.igEditorFilter",{setFocus:function(delay,toggle){var provider=this.options.provider;if(delay&&$.type(delay)==="number"&&delay>0){setTimeout(function(){provider.setFocus(toggle)},delay)}else{provider.setFocus(toggle)}},remove:function(){if(!this.options.provider.removeFromParent()){var p,e=this.element;p=e[0].parentNode;if(p&&p.tagName){p.removeChild(e[0])}e=this.validator();if(e){e.hide()}}},exitEditMode:function(){var editor=this.options.provider.editor;if(editor&&editor._exitEditMode&&$.type(editor._exitEditMode)==="function"){editor._exitEditMode()}},validator:function(){return this.options.provider.validator()},hasInvalidMessage:function(){var validator=this.validator();return validator?validator.getErrorMessages().length>0:false},destroy:function(){this.options.provider.destroy();this._superApply(arguments)},_setLanguage:function(lang){var editor=this.options.provider.editor;editor.option("language",lang)},_setRegional:function(regional){var editor=this.options.provider.editor;editor.option("regional",regional)}});$.extend($.ui.igEditorFilter,{version:"19.1.20"});$.ig.EditorProvider=$.ig.EditorProvider||Class.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){this.handlers={};this.userHandlers={};this.options=editorOptions||{};this.callbacks=callbacks;this.columnKey=key;this.handlers.keyDownHandler=$.proxy(this.keyDown,this);if(this.options.keydown){this.userHandlers.keyDown=this.options.keydown}this.options.keydown=this.handlers.keyDownHandler},keyDown:function(evt,ui){if(this.callbacks&&typeof this.callbacks.keyDown==="function"){this.callbacks.keyDown(evt,ui,this.columnKey)}if(this.userHandlers.keyDown){this.userHandlers.keyDown.apply(this.editor,[evt,ui])}},attachErrorEvents:function(errorShowing,errorShown,errorHidden){this.editor.element.bind({"igvalidatorerrorhidden.updating":errorHidden,"igvalidatorerrorshowing.updating":errorShowing,"igvalidatorerrorshown.updating":errorShown})},getEditor:function(){return this.editor},refreshValue:function(){return true},getValue:function(){return this.editor.value()},setValue:function(val){this.editor.value(val)},setFocus:function(toggle){return null},setSize:function(width,height){return null},removeFromParent:function(){return false},destroy:function(){this.editor.destroy()},validator:function(){return null},validate:function(){var validator=this.validator();return validator?validator.isValid():true},requestValidate:function(evt){var validator=this.validator(),valid=true;if(validator){validator._forceValidation=true;valid=validator._validate(null,evt);validator._forceValidation=false}return valid},isValid:function(){return true}});$.ig.EditorProviderBase=$.ig.EditorProviderBase||$.ig.EditorProvider.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){this._super(callbacks,key,editorOptions,tabIndex,format,element);this.handlers.textChangeHandler=$.proxy(this.textChanged,this);if(this.options.textChanged){this.userHandlers.textChanged=this.options.textChanged}this.options.textChanged=this.handlers.textChangeHandler;this.options.tabIndex=tabIndex},textChanged:function(evt,ui){if(this.callbacks&&typeof this.callbacks.textChanged==="function"){this.callbacks.textChanged(evt,ui,this.columnKey)}if(this.userHandlers.textChanged){this.userHandlers.textChanged.apply(this.editor,[evt,ui])}},setSize:function(width,height){if(width!==undefined){this.editor._setOption("width",width)}if(height!==undefined){this.editor._setOption("height",height)}},setFocus:function(){this.editor._focused=false;this.editor.setFocus()},removeFromParent:function(){var v=this.validator();if(v){v.hide()}this.editor._focused=false;this.editor._exitEditMode();this.editor._clearEditorNotifier();this.editor.editorContainer().removeClass("ui-state-focus");return this.editor.editorContainer().detach()},destroy:function(){this.editor.element.unbind(".updating");this.editor.destroy()},refreshValue:function(){if(this.editor._editorInput.is(":focus")){this.editor._processValueChanging(this.editor._editorInput.val())}},validator:function(){if($.type(this.editor.validator)==="function"){return this.editor.validator()}return null},isValid:function(){return this.editor.isValid()}});$.ig.EditorProviderText=$.ig.EditorProviderText||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igTextEditor){throw new Error($.ig.GridUpdating.locale.igTextEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);element.igTextEditor(this.options);this.editor=element.data("igTextEditor");return this.editor.editorContainer()},keyDown:function(evt,ui){if(ui.key===$.ui.keyCode.ENTER&&this.editor.dropDownContainer()&&this.editor.dropDownVisible()){return}if(this.callbacks&&typeof this.callbacks.keyDown==="function"){if(!(ui.key===$.ui.keyCode.ENTER&&evt.originalEvent.altKey)||!ui.editorInput.is("textarea")){this.callbacks.keyDown(evt,ui,this.columnKey)}else{evt.originalEvent.stopPropagation();evt.originalEvent.preventDefault()}}if(this.userHandlers.keyDown){this.userHandlers.keyDown.apply(this.editor,[evt,ui])}}});$.ig.EditorProviderNumeric=$.ig.EditorProviderNumeric||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igNumericEditor){throw new Error($.ig.GridUpdating.locale.igNumericEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);if(format==="int"){this.options.maxDecimals=0}else if(format==="double"&&!this.options.maxDecimals){this.options.maxDecimals=100}this.options.allowNullValue=true;element.igNumericEditor(this.options);this.editor=element.data("igNumericEditor");return this.editor.editorContainer()},getValue:function(){var val=this.editor.value();return isNaN(val)?null:val}});$.ig.EditorProviderCurrency=$.ig.EditorProviderCurrency||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igCurrencyEditor){throw new Error($.ig.GridUpdating.locale.igCurrencyEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);this.options.displayFactor=this.options.displayFactor||1;this.options.allowNullValue=true;element.igCurrencyEditor(this.options);this.editor=element.data("igCurrencyEditor");return this.editor.editorContainer()}});$.ig.EditorProviderPercent=$.ig.EditorProviderPercent||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igPercentEditor){throw new Error($.ig.GridUpdating.locale.igPercentEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);this.options.allowNullValue=true;element.igPercentEditor(this.options);this.editor=element.data("igPercentEditor");return this.editor.editorContainer()}});$.ig.EditorProviderMask=$.ig.EditorProviderMask||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igMaskEditor){throw new Error($.ig.GridUpdating.locale.igMaskEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);element.igMaskEditor(this.options);this.editor=element.data("igMaskEditor");return this.editor.editorContainer()}});$.ig.EditorProviderDate=$.ig.EditorProviderDate||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element,offset){element=element||$("<span />");if(!element.igDateEditor){throw new Error($.ig.GridUpdating.locale.igDateEditorException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);if(format&&!this.options.dateInputFormat){this.options.dateInputFormat=format}this.options.allowNullValue=true;this.options.displayTimeOffset=offset;element.igDateEditor(this.options);this.editor=element.data("igDateEditor");return this.editor.editorContainer()},setValue:function(value,fe,newOffset){if(newOffset!==undefined){this.editor._setOption("displayTimeOffset",newOffset)}this._super(value)}});$.ig.EditorProviderDatePicker=$.ig.EditorProviderDatePicker||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element,offset){element=element||$("<span />");if(!element.igDatePicker){throw new Error($.ig.GridUpdating.locale.igDatePickerException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);if(format&&!this.options.dateInputFormat){this.options.dateInputFormat=format}this.options.allowNullValue=true;this.options.displayTimeOffset=offset;element.igDatePicker(this.options);this.editor=element.data("igDatePicker");return this.editor.editorContainer()},removeFromParent:function(){if(this.editor.dropDownVisible()){$("#ui-datepicker-div").hide()}this._super()},setValue:function(value,fe,newOffset){if(newOffset!==undefined){this.editor._setOption("displayTimeOffset",newOffset)}this._super(value)}});$.ig.EditorProviderTimePicker=$.ig.EditorProviderTimePicker||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element,offset){element=element||$("<span />");if(!element.igTimePicker){throw new Error($.ig.GridUpdating.locale.igTimePickerException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);if(!this.options.timeInputFormat){this.options.timeInputFormat=format?format:"time"}this.options.allowNullValue=true;this.options.displayTimeOffset=offset;this.options.buttonType=editorOptions.buttonType||"spin";this.options.spinDelta=editorOptions.spinDelta||{hours:1,minutes:1};element.igTimePicker(this.options);this.editor=element.data("igTimePicker");return this.editor.editorContainer()},setValue:function(value,fe,newOffset){if(newOffset!==undefined){this.editor._setOption("displayTimeOffset",newOffset)}this._super(value)}});$.ig.EditorProviderBoolean=$.ig.EditorProviderBoolean||$.ig.EditorProviderBase.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){var requiresWrapper;if(!this.renderFormat){this.renderFormat="checkbox"}this._super(callbacks,key,editorOptions,tabIndex,format,element);this.handlers.valueChanged=$.proxy(this.valueChanged,this);if(this.options.valueChanged){this.userHandlers.valueChanged=this.options.valueChanged}this.options.valueChanged=this.handlers.valueChanged;switch(this.renderFormat){case"checkbox":requiresWrapper=!element;element=element||$("<input />");if(!element.igCheckboxEditor){throw new Error($.ig.GridUpdating.locale.igCheckboxEditorException)}element.igCheckboxEditor(this.options);this.editor=element.data("igCheckboxEditor");if(requiresWrapper){this.outerContainer=$("<div />").css({background:"rgb(255, 255, 255)",outline:"0px","text-align":"center",overflow:"hidden"}).addClass("ui-igedit ui-igedit-container ui-state-default ui-iggrid-editor");this.outerContainer.append(this.editor.editorContainer());return this.outerContainer}return this.editor.editorContainer();case"dropdown":this.options.listItems=["true","false"];this.options.dropDownAttachToBody=true;this.options.button="dropdown";this.options.isLimitedToListValues=this.options.isLimitedToListValues!==undefined&&this.options.isLimitedToListValues!==null?this.options.isLimitedToListValues:true;this.options.dropDownAttachedToBody=true;element=element||$("<span />");if(!element.igTextEditor){throw new Error($.ig.GridUpdating.locale.igTextEditorException)}element.igTextEditor(this.options);this.editor=element.data("igTextEditor");return this.editor.editorContainer()}},keyDown:function(evt,ui){if(ui.key===$.ui.keyCode.ENTER&&this.editor.dropDownContainer()&&this.editor.dropDownVisible()){return}if(this.callbacks&&typeof this.callbacks.keyDown==="function"){this.callbacks.keyDown(evt,ui,this.columnKey)}if(this.userHandlers.keyDown){this.userHandlers.keyDown.apply(this.editor,[evt,ui])}},valueChanged:function(evt,ui){if(this.callbacks&&typeof this.callbacks.textChanged==="function"){this.callbacks.textChanged(evt,ui,this.columnKey)}if(this.userHandlers.valueChanged){this.userHandlers.valueChanged.apply(this.editor,[evt,ui])}},refreshValue:function(){return false},getValue:function(){var editorValue=this.editor.value();if(this.renderFormat==="checkbox"){return editorValue}if(this.options.allowNullValue&&!this.options.isLimitedToListValues&&editorValue===null){return null}return editorValue&&editorValue.toLowerCase()==="true"},setValue:function(val){if(this.renderFormat==="checkbox"){this.editor.value(val!==null?val:false)}else if(this.options.allowNullValue&&!this.options.isLimitedToListValues){this.editor.value(val!==null?String(val):null)}else{this.editor.value(val!==null?String(val):"false")}},setSize:function(width,height){var cont,chb,defChb;if(this.renderFormat==="checkbox"&&this.outerContainer){cont=this.outerContainer;chb=cont.children().first();cont.css("width",width);cont.css("height",height);chb.css({"margin-top":cont.height()/2-chb.height()/2});defChb=cont.siblings().first().children().first();width=defChb.width();height=defChb.height()}this._super(width,height)},removeFromParent:function(){if(this.renderFormat==="checkbox"&&this.outerContainer){return this.outerContainer.detach()}return this._super()},destroy:function(){this.editor.element.unbind(".updating");this.editor.destroy();if(this.outerContainer&&this.outerContainer instanceof $){this.outerContainer.remove()}}});$.ig.EditorProviderCombo=$.ig.EditorProviderCombo||$.ig.EditorProvider.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<span />");if(!element.igCombo){throw new Error($.ig.GridUpdating.locale.igComboException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);this.handlers.selectionChangedHandler=$.proxy(this.selectionChanged,this);if(this.options.selectionChanged){this.userHandlers.selectionChanged=this.options.selectionChanged}this.options.selectionChanged=this.handlers.selectionChangedHandler;this.options.tabIndex=tabIndex;if(this.options.id){element.attr("id",this.options.id)}element.igCombo(this.options);this.editor=element.data("igCombo");this.editor.textInput().bind("keydown.updating",this.handlers.keyDownHandler);if(this.editor._options.internalSelChangeSubs&&$.type(this.editor._options.internalSelChangeSubs)==="array"){this.handlers.internalSelectionChangedHandler=$.proxy(this.internalSelectionChanged,this);this.editor._options.internalSelChangeSubs.push(this.handlers.internalSelectionChangedHandler)}return element},keyDown:function(evt,ui){if(evt.keyCode===$.ui.keyCode.TAB){this.editor.closeDropDown()}if(this.callbacks&&typeof this.callbacks.keyDown==="function"){if(evt.keyCode!==$.ui.keyCode.ENTER||!this.editor.dropDownOpened()){this.callbacks.keyDown(evt,ui||{owner:this.editor},this.columnKey)}}if(this.editor.options.allowCustomValue){this.internalSelectionChanged(evt,ui)}if(this.userHandlers.keyDown){this.userHandlers.keyDown.apply(this.editor,[evt,ui])}},internalSelectionChanged:function(evt,ui){if(this.callbacks&&typeof this.callbacks.textChanged==="function"){this.callbacks.textChanged(evt,ui,this.columnKey)}},selectionChanged:function(evt,ui){this.internalSelectionChanged(evt,ui);if(this.userHandlers.selectionChanged){this.userHandlers.selectionChanged.apply(this.editor,[evt,ui])}},refreshValue:function(){this.editor.refreshValue()},getValue:function(){var val=this.editor.value();if($.type(val)==="array"){return val.length?val[0]:null}return val},setValue:function(val,fire){this.editor.value(val,null,fire)},setSize:function(width,height){this.editor.element.igCombo({width:width,height:height})},setFocus:function(){this.editor.textInput().focus()},removeFromParent:function(){this.editor.closeDropDown();if(this.validator()){this.validator().hide()}return this.editor.element.closest(".ui-igcombo-wrapper").detach()},validator:function(){return this.editor.validator()},destroy:function(){this.editor.textInput().unbind("keydown.updating");this.editor.element.unbind(".updating");this.editor.destroy()},isValid:function(){return true}});$.ig.EditorProviderObjectCombo=$.ig.EditorProviderObjectCombo||$.ig.EditorProviderCombo.extend({getValue:function(){var val=null,arr=[];if(this.editor.selectedItems()!==null){if(this.editor.options.multiSelection&&this.editor.options.multiSelection.enabled){$(this.editor.selectedItems()).each(function(){arr.push(this.data)});val=arr}else{val=this.editor.selectedItems()[0].data}}return val},setValue:function(val,fire){var arr=[],editor=this.editor;editor.deselectAll();if(val){if(editor.options.multiSelection&&editor.options.multiSelection.enabled){$(val).each(function(){arr.push(this[editor.options.valueKey])});editor.value(arr,null,fire)}else{editor.value(val[this.editor.options.valueKey],null,fire)}}}});$.ig.EditorProviderRating=$.ig.EditorProviderRating||$.ig.EditorProvider.extend({createEditor:function(callbacks,key,editorOptions,tabIndex,format,element){element=element||$("<div />");if(!element.igRating){throw new Error($.ig.GridUpdating.locale.igRatingException)}this._super(callbacks,key,editorOptions,tabIndex,format,element);this.handlers.valueChange=$.proxy(this.valueChange,this);if(this.options.valueChange){this.userHandlers.valueChange=this.options.valueChange}this.options.valueChange=this.handlers.valueChange;if(this.options.id){element.attr("id",this.options.id)}element.igRating(this.options);this.editor=element.data("igRating");this.handlers.internalValueChange=$.proxy(this.internalValueChange,this);this.editor._internalChanged=this.handlers.internalValueChange;if(this.editor._foc){this.editor._foc.attr("tabIndex",tabIndex);this.editor._foc.keydown(this.handlers.keyDown)}return element},internalValueChange:function(evt,ui){if(this.callbacks&&typeof this.callbacks.textChanged==="function"){this.callbacks.textChanged(evt,ui,this.columnKey)}},valueChange:function(evt,ui){this.internalValueChange(evt,ui);if(this.userHandlers.valueChange){this.userHandlers.valueChange.apply(this.editor,[evt,ui])}},setValue:function(val){return this.editor.value(val||0)},setSize:function(width,height){if(!this._once){this.editor._doVotes(this.editor.options)}this._once=1;var back=this.editor.element.parent().css("backgroundColor");this.editor.element.css({width:width,height:height,backgroundColor:back})},setFocus:function(){this.editor.focus()},validator:function(){return this.editor.validator()},destroy:function(){this.editor.element.unbind(".updating");this.editor.destroy()},isValid:function(){return true}});$.ig.SortingExpressionsManager=$.ig.SortingExpressionsManager||Class.extend({init:function(options){if(options){this.grid=options.grid}},setGridInstance:function(grid){this.grid=grid},addSortingExpression:function(se,expr,feature){var i,seLength=se.length,found,cs,isGB=!!expr.isGroupBy,layout=expr.layout,key=expr.fieldName;for(i=0;i<seLength;i++){if(se[i].fieldName===key&&(!layout&&!se[i].layout||layout&&layout===se[i].layout)){if(!!se[i].isGroupBy===isGB){se[i]=expr;return se}else if(!isGB){return se}se.splice(i,1);break}}if(feature&&feature._getColumnSettingByKey){cs=feature._getColumnSettingByKey(key);if(cs&&cs.compareFunc){if($.type(cs.compareFunc)==="function"){expr.compareFunc=cs.compareFunc}else if(typeof cs.compareFunc==="string"&&typeof window[cs.compareFunc]==="function"){expr.compareFunc=window[cs.compareFunc]}}}if(isGB){seLength=se.length;found=-1;expr.isGroupBy=true;for(i=0;i<seLength;i++){if(se[i].isGroupBy===true){found=i}else{break}}if(found===-1){se.unshift(expr)}else{se.splice(found+1,0,expr)}return se}expr.isSorting=true;if(feature.options.mode==="single"&&se.length&&!se[se.length-1].isGroupBy){se[se.length-1]=expr;return se}se.push(expr);return se},setFormattersForSortingExprs:function(exprs,grid){exprs=exprs||[];grid=grid||this.grid;var i,len=exprs.length,expr,col,format,formatterFunc;formatterFunc=$.proxy(function(val,colKey){var col=this.columnByKey(colKey),o=this.options,rowTemplate=!o.rowTemplate||o.rowTemplate.length<=0;return new Date("January 01, 2000 "+$.ig.formatter(val,"date",col.format,rowTemplate,o.enableUTCDates));
},grid);for(i=0;i<len;i++){expr=exprs[i];if(expr.formatter){continue}col=grid.columnByKey(expr.fieldName);if(!col){continue}format=col.format;if(format&&(format==="time"||format==="timeLong"||format==="h:mm:ss tt")){expr.formatter=formatterFunc}}return exprs}});$.ig.GetEditorProvider=function(grid,column,editorType){var provider,dataType=column.dataType,format=column.format,value,ds;if(editorType==="checkbox"||!editorType&&(dataType==="bool"||dataType==="boolean")){provider=new $.ig.EditorProviderBoolean;if(format==="checkbox"||!format&&grid.options.renderCheckboxes||editorType==="checkbox"){provider.renderFormat="checkbox"}else{provider.renderFormat="dropdown"}}else if(editorType==="combo"&&dataType==="object"){provider=new $.ig.EditorProviderObjectCombo}else if(editorType==="combo"&&dataType!=="object"){provider=new $.ig.EditorProviderCombo}else if(editorType==="rating"){provider=new $.ig.EditorProviderRating}else if(editorType==="mask"){provider=new $.ig.EditorProviderMask}else if((editorType||format)==="currency"){provider=new $.ig.EditorProviderCurrency}else if((editorType||format)==="percent"){provider=new $.ig.EditorProviderPercent}else if(editorType==="numeric"||dataType==="number"){provider=new $.ig.EditorProviderNumeric}else if(editorType==="text"||dataType==="string"){provider=new $.ig.EditorProviderText}else if(editorType==="datepicker"){provider=new $.ig.EditorProviderDatePicker}else if((editorType||dataType)==="date"){provider=new $.ig.EditorProviderDate}else if(editorType==="timepicker"||dataType==="time"){provider=new $.ig.EditorProviderTimePicker}else{ds=grid.dataSource;if(ds&&ds.data()&&ds.data().length){value=ds.getCellValue(column.key,ds.data()[0]);switch($.type(value)){case"number":return new $.ig.EditorProviderNumeric;case"string":return new $.ig.EditorProviderText;case"date":return new $.ig.EditorProviderDate;case"boolean":provider=new $.ig.EditorProviderBoolean;if(format==="checkbox"||!format&&grid.options.renderCheckboxes){provider.renderFormat="checkbox"}else{provider.renderFormat="dropdown"}return provider}}throw new Error(grid._getLocaleValue("editorTypeCannotBeDetermined")+column.key)}return provider};return $});