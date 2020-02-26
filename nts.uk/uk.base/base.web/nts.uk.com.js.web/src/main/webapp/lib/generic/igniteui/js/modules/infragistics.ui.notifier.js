/*!@license
* Infragistics.Web.ClientUI jQuery Notifier 19.1.20
*
* Copyright (c) 2013-2019 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*  jquery-1.9.1.js
*  jquery.ui.core.js
*  jquery.ui.widget.js
*  infragistics.util.js
*  infragistics.util.jquery.js
*  infragistics.ui.widget.js
*  infragistics.ui.popover.js
*/
(function(factory){if(typeof define==="function"&&define.amd){define(["./infragistics.ui.popover"],factory)}else{return factory(jQuery)}})(function($){/*!@license
* Infragistics.Web.ClientUI Notifier localization resources 19.1.20
*
* Copyright (c) 2011-2019 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/
(function($){$.ig=$.ig||{};$.ig.locale=$.ig.locale||{};$.ig.locale.ja=$.ig.locale.ja||{};$.ig.Notifier=$.ig.Notifier||{};$.ig.locale.ja.Notifier={successMsg:"\u6210\u529f",errorMsg:"\u30a8\u30e9\u30fc",warningMsg:"\u8b66\u544a",infoMsg:"\u60c5\u5831",notSupportedState:"\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u901a\u77e5\u72b6\u614b\u3067\u3059\u3002success\u3001info\u3001warning\u3001error \u306e\u3044\u305a\u308c\u304b\u306e\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u308b\u72b6\u614b\u3092\u4f7f\u7528\u3057\u3066\u304f\u3060\u3055\u3044\u3002",notSupportedMode:"\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u3066\u3044\u306a\u3044\u901a\u77e5\u30e2\u30fc\u30c9\u3067\u3059\u3002auto\u3001popover\u3001inline \u306e\u3044\u305a\u308c\u304b\u306e\u30b5\u30dd\u30fc\u30c8\u3055\u308c\u308b\u30e2\u30fc\u30c9\u3092\u4f7f\u7528\u3057\u3066\u304f\u3060\u3055\u3044\u3002"};$.ig.Notifier.locale=$.ig.Notifier.locale||$.ig.locale.ja.Notifier;return $.ig.locale.ja.Notifier})($);$.widget("ui.igNotifier",$.ui.igPopover,{css:{baseClasses:"ui-widget ui-igpopover ui-ignotify",contentInner:"ui-ignotify-content",inline:"ui-ignotify-inline",iconContainer:"ui-ignotify-icon-container",infoState:"ui-ignotify-info",infoIcon:"ui-icon ui-icon-info",successState:"ui-ignotify-success",successIcon:"ui-icon ui-icon-circle-check",warningState:"ui-ignotify-warn",warningIcon:"ui-icon ui-icon-alert",errorState:"ui-ignotify-error",errorIcon:"ui-icon ui-icon-circle-close"},options:{state:"info",notifyLevel:"success",appendTo:"body",mode:"auto",allowCSSOnTarget:true,showIcon:false,contentTemplate:"<span class='{0}'><span class='{1}'></span></span>{2}",headerTemplate:{closeButton:true,title:null},showOn:"manual",closeOnBlur:false,animationDuration:250,animationSlideDistance:5,selectors:null},inlineStates:["success","error"],_create:function(){this._super();this._setOption("directionPriority",["top","left","right","bottom"]);this._states=["success","info","warning","error"];this._modes=["auto","popover","inline"];this._currentText=this._getDefaultMessageByState(this.options.state)},_getDefaultMessageByState:function(state){return this._getLocaleValue(state+"Msg")},changeLocale:function(){if(this.contentInner.attr("data-default-locale")){this._currentText=this._getDefaultMessageByState(this.options.state);this._setNewContent(this._getTemplate())}},_setState:function(value,message){if($.inArray(value,this._states)===-1){throw new Error(this._getLocaleValue("notSupportedState"))}if(message!==undefined){this._currentText=message;this.contentInner.removeAttr("data-default-locale")}else{this.contentInner.attr("data-default-locale",true)}if(this.options.state!==value){this._currentText=message!==undefined?this._currentText:this._getDefaultMessageByState(value);this._previousState=this.options.state;this.options.state=value;if(this._visible){this._setTargetState()}if(this._isInline(value)!==this._isInline(this._previousState)){this._setMode(this.options.mode,true);return}}this.popover.removeClass(this.css[this._previousState+"State"]).addClass(this.css[this.options.state+"State"]);this._setNewContent(this._getTemplate())},_setTargetState:function(clean){this._target.removeClass(this.css[this._previousState+"State"]).removeClass(this.css[this.options.state+"State"]);if(this.options.allowCSSOnTarget&&!clean){this._target.addClass(this.css[this.options.state+"State"])}},_setOption:function(key,value){switch(key){case"state":this._setState(value);if(this._visible&&!this._isInline()){this._positionPopover(this._target);this._slide()}break;case"mode":if(typeof value==="string"){this._setMode(value)}break;case"contentTemplate":if(typeof value==="string"){this.options.contentTemplate=value;this._setNewContent(this._getTemplate())}break;case"allowCSSOnTarget":if(typeof value==="boolean"){this.options.allowCSSOnTarget=value;if(this._visible){this._setTargetState(!value)}}break;case"showIcon":this.options.showIcon=value;if(this._visible){this._setNewContent(this._getTemplate())}break;default:this._superApply(arguments)}},_setMode:function(value,force){if($.inArray(value,this._modes)===-1){throw new Error(this._getLocaleValue("notSupportedMode"))}if(this.options.mode!==value||force){this.popover.remove();delete this.arrow;this.options.mode=value;this._renderPopover();if(this._visible){if(!this._isInline()){this._positionPopover(this._target)}this.popover.show();this._slide()}}},_isInline:function(state){var target=state||this.options.state;if(this.options.mode==="inline"){return true}else{return this.options.mode==="auto"&&$.inArray(target,this.inlineStates)>-1}},notify:function(state,message){if($.inArray(state,this._states)>=$.inArray(this.options.notifyLevel,this._states)){if(!this._visible||this.options.state!==state||this._currentText!==message){this._setState(state,message);this.show()}}else{this.hide();this._setState(state,message)}},isVisible:function(){return this._visible},_renderPopover:function(){if(this._isInline()){this.popover=$("<div></div>").addClass(this.css.baseClasses).addClass(this.css.inline);this.contentInner=$("<div></div>").appendTo(this.popover);this.popover.insertAfter(this._target);this._attachEventsToTarget()}else{$.ui.igPopover.prototype._renderPopover.apply(this,arguments)}this._setState(this.options.state);this.contentInner.addClass(this.css.contentInner)},_openPopover:function(){var initialState=this._visible;if(this.popover.is(":animated")){this.popover.stop(true)}this._visible=false;$.ui.igPopover.prototype._openPopover.apply(this,arguments);if(this._visible){var change=this._visible!==initialState;this._slide(!change);if(change){this._setTargetState()}}else{this._visible=initialState}},_slide:function(quick){if(!this.options.animationSlideDistance||!this.oDir||this._isInline()){return}var slideAnimation;switch(this.oDir){case"top":slideAnimation={top:"-="+this.options.animationSlideDistance+"px"};break;case"bottom":slideAnimation={top:"+="+this.options.animationSlideDistance+"px"};break;case"left":slideAnimation={left:"-="+this.options.animationSlideDistance+"px"};break;case"right":slideAnimation={left:"+="+this.options.animationSlideDistance+"px"};break}this.popover.animate(slideAnimation,{queue:false,duration:quick?0:this.options.animationDuration})},_resizeHandler:function(event){if(this._visible&&this._currentTarget){this._positionPopover(this._currentTarget);this._slide(event)}},_attachEventsToTarget:function(){if(this.options.showOn!=="manual"){$.ui.igPopover.prototype._attachEventsToTarget.apply(this,arguments)}},_closePopover:function(){var initialState=this._visible;$.ui.igPopover.prototype._closePopover.apply(this,arguments);if(!this._visible&&this._visible!==initialState){this._setTargetState(true)}},_positionPopover:function(){if(!this._isInline()){$.ui.igPopover.prototype._positionPopover.apply(this,arguments)}},_getTemplate:function(){var currContent=this.options.contentTemplate;if(typeof currContent==="function"&&this._target){currContent=this._getContentTemplate(this._target[0])}return currContent},_getContentTemplate:function(target){var template="";if(target){template=this.options.contentTemplate.call(target,this.options.state)}return template},_setNewContent:function(nc){var newContent=nc,iconContainer=this.css.iconContainer,icon="";if(nc instanceof $){newContent=nc.html()}else if(typeof nc==="object"){newContent=nc.innerHTML}if(this.options.showIcon){icon=this.css[this.options.state+"Icon"]}else{iconContainer+=" hidden"}newContent=newContent.replace(/\{0\}/g,iconContainer).replace(/\{1\}/g,icon).replace(/\{2\}/g,this._currentText);this.contentInner.html(newContent)},destroy:function(){this._setTargetState(true);this._superApply(arguments);return this}});$.extend($.ui.igNotifier,{version:"19.1.20"});return $});