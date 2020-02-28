/*!@license
* Infragistics.Web.ClientUI infragistics.legend.js 19.1.20191.172
*
* Copyright (c) 2011-2019 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends:
*     jquery-1.4.4.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*     infragistics.ext_core.js
*     infragistics.ext_collections.js
*     infragistics.dv_core.js
*     infragistics.dv_visualdata.js
*     infragistics.ext_ui.js
*/
(function(factory){if(typeof define==="function"&&define.amd){define(["./infragistics.util","./infragistics.ext_core","./infragistics.ext_collections","./infragistics.dv_core","./infragistics.dv_visualdata","./infragistics.ext_ui"],factory)}else{factory(igRoot)}})(function($){$.ig=$.ig||{};var $$t={};$.ig.globalDefs=$.ig.globalDefs||{};$.ig.globalDefs.$$p=$$t;$$0=$.ig.globalDefs.$$0;$$4=$.ig.globalDefs.$$4;$$1=$.ig.globalDefs.$$1;$$w=$.ig.globalDefs.$$w;$$ap=$.ig.globalDefs.$$ap;$$6=$.ig.globalDefs.$$6;$$a=$.ig.globalDefs.$$a;$.ig.$currDefinitions=$$t;$.ig.util.bulkDefine(["LegendBaseViewManager:b","LegendMouseEventHandler:d","LegendMouseButtonEventHandler:f","ItemLegend:g","Legend:h","LegendBase:k","FinancialLegend:l","ScaleLegend:m","ItemLegendView:n","LegendBaseView:o","LegendView:p","FinancialLegendView:q","ScaleLegendView:r"]);var $a=$.ig.intDivide,$b=$.ig.util.cast,$c=$.ig.util.defType,$d=$.ig.util.defEnum,$e=$.ig.util.getBoxIfEnum,$f=$.ig.util.getDefaultValue,$g=$.ig.util.getEnumValue,$h=$.ig.util.getValue,$i=$.ig.util.intSToU,$j=$.ig.util.nullableEquals,$k=$.ig.util.nullableIsNull,$l=$.ig.util.nullableNotEquals,$m=$.ig.util.toNullable,$n=$.ig.util.toString$1,$o=$.ig.util.u32BitwiseAnd,$p=$.ig.util.u32BitwiseOr,$q=$.ig.util.u32BitwiseXor,$r=$.ig.util.u32LS,$s=$.ig.util.unwrapNullable,$t=$.ig.util.wrapNullable,$u=String.fromCharCode,$v=$.ig.util.castObjTo$t,$w=$.ig.util.compare,$x=$.ig.util.replace,$y=$.ig.util.stringFormat,$z=$.ig.util.stringFormat1,$0=$.ig.util.stringFormat2,$1=$.ig.util.stringCompare1,$2=$.ig.util.stringCompare2,$3=$.ig.util.stringCompare3,$4=$.ig.util.compareSimple,$5=$.ig.util.tryParseNumber,$6=$.ig.util.tryParseNumber1,$7=$.ig.util.numberToString,$8=$.ig.util.numberToString1,$9=$.ig.util.parseNumber;$d("LegendOrientation:i",false,false,{Horizontal:0,Vertical:1});$c("LegendBaseViewManager:b","Object",{a:null,init:function(a){this.g=null;this.d=false;$.ig.$op.init.call(this);this.a=a;this._f=new $$4.h($.ig.$op.$type,$$1.$h.$type,0);this.q()},g:null,q:function(){var $self=this;if(this.g==null){this.g=function(a){var b=$.ig.LegendItemHelper.prototype.b();if(window.devicePixelRatio!=window.undefined&&($.ig.util.isNaN(b)||window.devicePixelRatio!=b)){$.ig.LegendItemHelper.prototype.b(window.devicePixelRatio);$self.a.aj()}};window.addEventListener("resize",this.g,false)}this.g(null)},t:function(a){if(a==null){if(this.g!=null){window.removeEventListener("resize",this.g,false);this.g=null}this._m=null;this._h=null;return}this._h=a;this._m=this.j();this._m.addClass(this.a._s);this._h.append(this._m)},j:function(){return this._h.createElement("table")},_m:null,_h:null,d:false,i:function(){var a=this._h.createElement("tr");a.addClass(this.a._t);var b=this.a.k();if(b){a.setStyleProperty("display","block");a.setStyleProperty("float","left");a.setStyleProperty("margin","0px");a.setStyleProperty("margin-right","8px")}return a},k:function(a){return this.l(a,this._m)},l:function(a,b){var c=$b($$a.$g.$type,a);var d=this.i();if(c!=null){var e=$b($.ig.ILegendContext.prototype.$type,c.content());if(e!=null&&c._ah!=null){var f=new $$a.l;f.context=d;f.passID="LegendItem";var g=new $$a.m;g.passInfo=f;g.width=NaN;g.height=NaN;g.data=e;g.context=d;var h=new $$a.n;h.passInfo=f;if(c._ah.measure()!=null){c._ah.measure()(g)}h.context=this._h.getSubRenderer(d);h.availableWidth=g.width;h.availableHeight=g.height;h.data=e;h.xPosition=0;h.yPosition=0;c._ah.render()(h);var i=d.findByClass(".ui-legend-item-badge");for(var j=0;j<i.length;j++){i[j].removeClass("ui-legend-item-badge").addClass(this.a._r)}i=d.findByClass(".ui-legend-item-text");for(var k=0;k<i.length;k++){if(this.a.k()){i[k].setStyleProperty("white-space","nowrap")}i[k].removeClass("ui-legend-item-text").addClass(this.a._u)}}}return d},_f:null,p:function(a,b){var c=this.l(a,this._m);c.listen("mouseup",this.a.ae.runOn(this.a));c.listen("mousedown",this.a.ab.runOn(this.a));c.listen("mouseleave",this.a.ac.runOn(this.a));c.listen("mousemove",this.a.ad.runOn(this.a));this._f.add(a,c);if(this._m.getChildCount()==0||b>=this._m.getChildCount()){this._m.append(c)}else{var d=this._m.getChildAt(b);d.before(c)}},v:function(a){},w:function(a){var $self=this;var b;if(function(){var c=$self._f.tryGetValue(a,b);b=c.p1;return c.ret}()){b.unlistenAll();b.remove();this._f.remove(a)}},o:function(visual_){var a=this._f.getEnumerator();while(a.moveNext()){var pair_=a.current();if(pair_.value().getNativeElement()===visual_){return pair_.key()}}return null},e:function(a){var c=this._f.keys().getEnumerator();while(c.moveNext()){var b=c.current();var d=$b($$a.$g.$type,b);if(d!=null&&d.content()!=null){var e=$b($.ig.ILegendContext.prototype.$type,d.content());if(e!=null&&e.itemLabel()==a.itemLabel()&&e.legendLabel()==a.legendLabel()&&e.series()==a.series()&&e.itemBrush()==a.itemBrush()){return true}}}return false},_n:null,_c:null,b:function(a){var b=this._h.rootWrapper().width();var c=this._h.rootWrapper().height();this._m.remove();var d=this._h.createElement("div");d.setStyleProperty("position","relative");this._n=this._h.createElement('<canvas style="position : absolute; top : 0; left : 0" />');this._n.setStyleProperty("position","absolute");this._n.setStyleProperty("top","0px");this._n.setStyleProperty("left","0px");this._h.append(d);d.append(this._n);this._c=new $.ig.RenderingContext(new $.ig.CanvasViewRenderer,this._h.get2DCanvasContext(this._n));this._n.setAttribute("width",b.toString());this._n.setAttribute("height",c.toString());return this._c},aa:function(){return new $$a.af(1,this._h.rootWrapper().width(),Math.max(10,this._h.rootWrapper().height()-15))},x:function(a,b,c,d){var e=a.g();e.beginPath();e.moveTo(b._aj.__inner[0].__x,b._aj.__inner[0].__y);for(var f=1;f<b._aj.count();f++){e.lineTo(b._aj.__inner[f].__x,b._aj.__inner[f].__y)}e.lineTo(b._aj.__inner[0].__x,b._aj.__inner[0].__y);var g=e.createLinearGradient(d.left(),d.top(),d.left(),d.top()+d.height());var i=c._a.getEnumerator();while(i.moveNext()){var h=i.current();g.addColorStop(h._a,h._b.__fill)}e.fillStyle=g;e.fill()},r:function(){},s:function(){},u:function(a){var b=this._m==null?0:this._m.getChildCount();for(var c=0;c<b;c++){var d=this._m.getChildAt(c);d.setStyleProperty("display",a?"block":"");d.setStyleProperty("float",a?"left":"");d.setStyleProperty("margin",a?"0px":"");d.setStyleProperty("margin-right",a?"8px":"");d.getChildAt(1).setStyleProperty("white-space",a?"nowrap":"")}},y:function(a){this._m.setStyleProperty("color",a)},z:function(a){var b=$.ig.FontUtil.prototype.getFontInfoFromString(this._h,a);this._m.setStyleProperty("font-family",b.n());this._m.setStyleProperty("font-size",b.d()+"px");this._m.setStyleProperty("font-style",b.q())},$type:new $.ig.Type("LegendBaseViewManager",$.ig.$ot)},true);$c("FinancialLegendViewManager:a","LegendBaseViewManager",{init:function(a){$$t.$b.init.call(this,a)},j:function(){if(this._h==null){return null}var a=this._h.createElement("div");return a},i:function(){if(this._h==null){return null}var a=this._h.createElement("span");a.setStyleProperty("display","inline-block");a.setStyleProperty("background-color","#eee");a.setStyleProperty("padding","5px 2px 5px 2px");a.setStyleProperty("margin","2px");return a},$type:new $.ig.Type("FinancialLegendViewManager",$$t.$b.$type)},true);$c("LegendMouseEventArgs:c","EventArgs",{init:function(a,b,c,d,e){$$0.$w.init.call(this);this.chart(a);this.series(b);this.item(c);this.originalEvent(d);this.legendItem(e)},toString:function(){return this.chart().name()+", "+this.series().name()+", "+(this.item()!=null?this.item().toString():"")+", "+this.getPosition(null).toString()},_originalEvent:null,originalEvent:function(a){if(arguments.length===1){this._originalEvent=a;return a}else{return this._originalEvent}},getPosition:function(a){return this.originalEvent().getPosition(a)},originalSource:function(){return this.originalEvent().originalSource()},_item:null,item:function(a){if(arguments.length===1){this._item=a;return a}else{return this._item}},_series:null,series:function(a){if(arguments.length===1){this._series=a;return a}else{return this._series}},_chart:null,chart:function(a){if(arguments.length===1){this._chart=a;return a}else{return this._chart}},_legendItem:null,legendItem:function(a){if(arguments.length===1){this._legendItem=a;return a}else{return this._legendItem}},$type:new $.ig.Type("LegendMouseEventArgs",$$0.$w.$type)},true);$c("LegendMouseButtonEventArgs:e","EventArgs",{init:function(a,b,c,d,e){$$0.$w.init.call(this);this.chart(a);this.series(b);this.item(c);this.originalEvent(d);this.legendItem(e)},toString:function(){return this.chart().name()+", "+this.series().name()+", "+(this.item()!=null?this.item().toString():"")+", "+this.getPosition(null).toString()},_originalEvent:null,originalEvent:function(a){if(arguments.length===1){this._originalEvent=a;return a}else{return this._originalEvent}},handled:function(a){if(arguments.length===1){this.originalEvent().handled(a);return a}else{return this.originalEvent().handled()}},getPosition:function(a){return this.originalEvent().getPosition(a)},originalSource:function(){return this.originalEvent().originalSource()},_item:null,item:function(a){if(arguments.length===1){this._item=a;return a}else{return this._item}},_series:null,series:function(a){if(arguments.length===1){this._series=a;return a}else{return this._series}},_chart:null,chart:function(a){if(arguments.length===1){this._chart=a;return a}else{return this._chart}},_legendItem:null,legendItem:function(a){if(arguments.length===1){this._legendItem=a;return a}else{return this._legendItem}},$type:new $.ig.Type("LegendMouseButtonEventArgs",$$0.$w.$type)},true);$c("LegendBase:k","ContentControl",{aj:function(){return new $$t.o(this)},a6:function(a){this._ak=a},_ak:null,init:function(){$$a.$g.init.call(this);var a=this.aj();this.a6(a);a.ai();this.children(new $$4.f($$a.$c.$type,0))},addChildInOrder:function(a,b){},a0:function(a,b){},_children:null,children:function(a){if(arguments.length===1){this._children=a;return a}else{return this._children}},_seriesOwner:null,seriesOwner:function(a){if(arguments.length===1){this._seriesOwner=a;return a}else{return this._seriesOwner}},_chartOwner:null,chartOwner:function(a){if(arguments.length===1){this._chartOwner=a;return a}else{return this._chartOwner}},isItemwise:function(){return false},isScale:function(){return false},clearLegendItemsForSeries:function(a){if(a==null||this.children()==null||this.children().count()==0){return}var b=new $$4.f($$a.$c.$type,0);var d=this.children().getEnumerator();while(d.moveNext()){var c=d.current();var e=$b($$a.$g.$type,c);if(e!=null&&e.content()!=null){var f=$b($.ig.ILegendContext.prototype.$type,e.content());if(f!=null&&f.series()==a){b.add(c)}}}var h=b.getEnumerator();while(h.moveNext()){var g=h.current();this.children().remove(g)}},containsChild:function(a){return this.children().contains(a)},removeChild:function(a){this.children().remove(a)},ai:function(){if(this.seriesOwner()!=null){return this.seriesOwner()}else{return this.chartOwner()}},propertyChanged:null,propertyUpdated:null,a7:function(a,b,c){if(this.propertyChanged!=null){this.propertyChanged(this,new $$0.b7(a))}if(this.propertyUpdated!=null){this.propertyUpdated(this,new $.ig.PropertyUpdatedEventArgs(a,b,c))}},legendItemMouseLeftButtonDown:null,a3:function(a){if(this.legendItemMouseLeftButtonDown!=null){this.legendItemMouseLeftButtonDown(this,a)}},legendItemMouseLeftButtonUp:null,a4:function(a){if(this.legendItemMouseLeftButtonUp!=null){this.legendItemMouseLeftButtonUp(this,a)}},legendItemMouseEnter:null,a1:function(a){if(this.legendItemMouseEnter!=null){this.legendItemMouseEnter(this,a)}},legendItemMouseLeave:null,a2:function(a){if(this.legendItemMouseLeave!=null){this.legendItemMouseLeave(this,a)}},legendItemMouseMove:null,a5:function(a){if(this.legendItemMouseMove!=null){this.legendItemMouseMove(this,a)}},provideContainer:function(a){this._ak.ah(a)},legendItemsListStyle:function(a){if(arguments.length===1){this._ak._s=a;return a}else{return this._ak._s}},legendItemStyle:function(a){if(arguments.length===1){this._ak._t=a;return a}else{return this._ak._t}},legendItemBadgeStyle:function(a){if(arguments.length===1){this._ak._r=a;return a}else{return this._ak._r}},legendItemTextStyle:function(a){if(arguments.length===1){this._ak._u=a;return a}else{return this._ak._u}},exportVisualData:function(){return this._ak.f()},exportSerializedVisualData:function(){return this.exportVisualData().serialize()},isFinancial:function(){return false},$type:new $.ig.Type("LegendBase",$$a.$g.$type,[$$0.$b6.$type,$.ig.IChartLegend.prototype.$type])},true);$c("ItemLegend:g","LegendBase",{aj:function(){return new $$t.n(this)},a6:function(a){$$t.$k.a6.call(this,a);this._a9=a},_a9:null,init:function(){var $self=this;$$t.$k.init.call(this);this._ab=$$t.$g.$type;var a=this.children();a.collectionChanged=$.ig.Delegate.prototype.combine(a.collectionChanged,function(a,b){if(b.oldItems()!=null){var d=b.oldItems().getEnumerator();while(d.moveNext()){var c=d.current();$self._a9.al(c)}}if(b.newItems()!=null){var f=b.newItems().getEnumerator();while(f.moveNext()){var e=f.current();$self._a9.x(e)}}})},addChildInOrder:function(a,b){if(!this._ak.m()){return}this.be(b)},isItemwise:function(){return true},createItemwiseLegendItems:function(a,b){this.a0(a,b)},a0:function(a,b){this._ak.af();this.clearLegendItemsForSeries(b);if(b==null||a==null||a.count()==0){return}var d=a.getEnumerator();while(d.moveNext()){var c=d.current();var e=$b($$a.$g.$type,c);if(e!=null&&e.content()!=null){var f=$b($.ig.ILegendContext.prototype.$type,e.content());if(f!=null&&!this.bb(f)){this.children().add(c);var g=new $$t.j;g._b=f;g._c=c;g._a=b;if(f.legendLabel()!=null){g._d=f.legendLabel()}else{g._d=f.itemLabel()}}}}this._ak.ag()},createLegendItemsInsert:function(a,b){this.bd(a,b)},bd:function(a,b){this._ak.af();try{var c=this.bc(b);if(b==null||a==null||a.count()==0){return}var e=a.getEnumerator();while(e.moveNext()){var d=e.current();var f=$b($$a.$g.$type,d);if(f!=null&&f.content()!=null){var g=$b($.ig.ILegendContext.prototype.$type,f.content());if(g!=null&&!this.bb(g)){this.children().insert(c,d);c++;var h=new $$t.j;h._b=g;h._c=d;h._a=b;h._d=g.legendLabel()!=null?g.legendLabel().toString():g.itemLabel()}}}}finally{this._ak.ag()}},renderItemwiseContent:function(a){this.be(a)},be:function(a){var $self=this;this.clearLegendItemsForSeries(a);if(a.hasSubItems()){a.forSubItems(function(b){var c=$b($$a.$g.$type,b);if(c!=null&&c.content()!=null){var d=$b($.ig.ILegendContext.prototype.$type,c.content());if(d!=null&&!$self.bb(d)){$self.children().add(b);var e=new $$t.j;e._b=d;e._c=b;e._a=a;e._d=d.itemLabel()}}})}},bc:function(a){if(a==null||this.children()==null||this.children().count()==0){return 0}var b=new $$4.f($$a.$c.$type,0);var c=-1;var d=0;var f=this.children().getEnumerator();while(f.moveNext()){var e=f.current();var g=$b($$a.$g.$type,e);if(g!=null&&g.content()!=null){var h=$b($.ig.ILegendContext.prototype.$type,g.content());if(h!=null&&h.series()==a){if(c==-1){c=d}b.add(e)}}d++}var j=b.getEnumerator();while(j.moveNext()){var i=j.current();this.children().remove(i)}if(c==-1){return this.children().count()}return c},bb:function(a){return this._a9.an(a)},_ba:null,$type:new $.ig.Type("ItemLegend",$$t.$k.$type,[$.ig.IChartItemLegend.prototype.$type])},true);$c("Legend:h","LegendBase",{aj:function(){return new $$t.p(this)},a6:function(a){$$t.$k.a6.call(this,a);this._ba=a},_ba:null,init:function(){var $self=this;$$t.$k.init.call(this);this._ab=$$t.$h.$type;var a=this.children();a.collectionChanged=$.ig.Delegate.prototype.combine(a.collectionChanged,function(a,b){if(b.oldItems()!=null){var d=b.oldItems().getEnumerator();while(d.moveNext()){var c=d.current();$self._ba.al(c)}}if(b.newItems()!=null){var f=b.newItems().getEnumerator();while(f.moveNext()){var e=f.current();$self._ba.x(e)}}})},bb:function(a){return a.resolveLegendIndex()},bd:function(a){return this.bc(a)},addChildInOrder:function(a,b){if(b.isStacked()){return}if(!b.isUsableInLegend()){return}var c=0;var e=this.children().getEnumerator();while(e.moveNext()){var d=e.current();var f;var g;var h;var i=this._ak.aa(d,f,g,h);f=i.p1;g=i.p2;h=i.p3;if(b.container()!=null&&f!=null&&(this.bd(b.container())<this.bd(f)||this.bd(b.container())==-1&&this.bd(f)==-1&&b.container().getHashCode()<f.getHashCode())){break}if(b.container()!=null&&f!=null&&b.container()==f&&g!=null){var j=this.bb(b);var k=this.bb(g);var l=this.bd(b);var m=this.bd(g);if(g.isVertical()&&!g.isStacked()){if(m==-1&&l==-1){c=0;break}if(l<m||m==-1){break}}if(j<=k){break}}c++}this.children().insert(c,a);var n=new $$t.j;n._c=a;n._a=b;var o=$b($$a.$g.$type,a);if(o!=null&&o.content()!=null){var p=$b($.ig.ILegendContext.prototype.$type,o.content());if(p!=null){n._b=p;n._d=p.itemLabel()}}},orientation:function(a){if(arguments.length===1){this._ak.k(a==0);return a}else{return this._ak.k()?0:0}},bc:function(a){return-1},textColor:function(a){if(arguments.length===1){this._ak.v(a);return a}else{return this._ak.v()}},textStyle:function(a){if(arguments.length===1){this._ak.w(a);return a}else{return this._ak.w()}},$type:new $.ig.Type("Legend",$$t.$k.$type)},true);$c("LegendItemInfo:j","Object",{init:function(){$.ig.$op.init.call(this)},_d:null,_c:null,_a:null,_b:null,$type:new $.ig.Type("LegendItemInfo",$.ig.$ot)},true);$c("FinancialLegend:l","LegendBase",{aj:function(){return new $$t.q(this)},a9:function(){return this._ak},init:function(){var $self=this;$$t.$k.init.call(this);this._ab=$$t.$l.$type;var a=this.children();a.collectionChanged=$.ig.Delegate.prototype.combine(a.collectionChanged,function(a,b){if(b.oldItems()!=null){var d=b.oldItems().getEnumerator();while(d.moveNext()){var c=d.current();$self.a9().al(c)}}if(b.newItems()!=null){var f=b.newItems().getEnumerator();while(f.moveNext()){var e=f.current();$self.a9().x(e)}}})},addChildInOrder:function(a,b){this.children().add(a)},isFinancial:function(){return true},$type:new $.ig.Type("FinancialLegend",$$t.$k.$type)},true);$c("ScaleLegend:m","LegendBase",{aj:function(){return new $$t.r(this)},a6:function(a){$$t.$k.a6.call(this,a);this._bb=a},_bb:null,legendScaleElement:function(){return this._bb._a8},minText:function(){return this._bb._a4},maxText:function(){return this._bb._a3},init:function(){$$t.$k.init.call(this);this._ab=$$t.$m.$type},_bd:0,_bc:0,a9:null,ba:function(a){if(arguments.length===1){this.a9=a;return a}else{return this.a9}},bg:function(){this._bb.a0()},restoreOriginalState:function(){this.bg()},bj:function(a){if(a==null){return this._bb.a7()}return $.ig.ColorUtil.prototype.m(a)},be:function(a){this.ba(a);this.bf()},initializeLegend:function(a){this.be(a)},isScale:function(){return true},bf:function(){var $self=this;if(this.legendScaleElement()==null||this.ba()==null||!this.ba().legendReady()){return}if(this.ba()==null||!this.ba().isAttachedTo(this)){return}var a=false;var b=$b($$a.$bw.$type,this.legendScaleElement());if(b!=null){var c=this._bb.au();if(!this.ba().forScaleColors(function(d,e){$self._bb.aw(c,$self.bj(d),e)})){a=true}this._bb.a1(b,a,c)}if(this.minText()!=null){this.minText().ak(this.ba().minScaleText())}if(this.maxText()!=null){this.maxText().ak(this.ba().maxScaleText())}},$type:new $.ig.Type("ScaleLegend",$$t.$k.$type,[$.ig.IChartScaleLegend.prototype.$type])},true);$c("LegendBaseView:o","Object",{init:function(a){this.l=false;$.ig.$op.init.call(this);this._b=a;this._c=new $$t.b(this)},_c:null,_b:null,ai:function(){},j:function(){return false},d:function(a){var b;var c;var d;var e=this.aa(a,b,c,d);b=e.p1;c=e.p2;d=e.p3;var f=new $$a.as;var g=new $$t.e(b,c,d,f,a);return g},e:function(a){var b;var c;var d;var e=this.aa(a,b,c,d);b=e.p1;c=e.p2;d=e.p3;var f=new $$a.ar;var g=new $$t.c(b,c,d,f,a);return g},aa:function(a,b,c,d){b=this._b.ai();c=null;d=null;if(a!=null){var e=$b($$a.$g.$type,a);if(e!=null&&e.content()!=null&&$b($.ig.ILegendContext.prototype.$type,e.content())!==null){var f=$b($.ig.ILegendContext.prototype.$type,e.content());c=$b($.ig.ILegendSeries.prototype.$type,f.series());if(c!=null){b=c.container()}d=f.item()}}return{p1:b,p2:c,p3:d}},z:function(){},m:function(){return true},y:function(a){},ak:function(a){},ah:function(a){this._c.t(a)},al:function(a){this._c.w(a)},x:function(a){var b=this._b.children().indexOf(a);this._c.p(a,b)},af:function(){this._c.r()},ag:function(){this._c.s()},_s:null,_t:null,_r:null,_u:null,l:false,i:false,k:function(a){if(arguments.length===1){this.i=a;this._c.u(this.i);return a}else{return this.i}},p:null,v:function(a){if(arguments.length===1){this.p=a;this._c.y(this.p);return a}else{return this.p}},q:null,w:function(a){if(arguments.length===1){this.q=a;this._c.z(this.q);return a}else{return this.q}},aj:function(){},n:function(e_){var ev_=e_.originalEvent;return ev_.currentTarget},o:function(e_){return this._c.o(this.n(e_))},ad:function(a){if(!this.l){this._b.a1(this.e(this.o(a)))}this.l=true},ac:function(a){if(this.l){this._b.a2(this.e(this.o(a)))}this.l=false},ab:function(a){this._b.a3(this.d(this.o(a)))},ae:function(a){this._b.a4(this.d(this.o(a)))},h:function(a){var b=a.querySelectorAll("span *");var c=$$a.$ae.empty();for(var e=0;e<b.length;e++){var d=b[e];if(String.isNullOrEmpty(d.getText())){continue}var f=new $$a.ae(0,d.getOffset().left,d.getOffset().top,d.width(),d.height());f.union(c);c=f}return $.ig.RectData.prototype.b(c)},g:function(a){var b=a.querySelectorAll("canvas");var c=b.length>0?b[0]:null;return c!=null?new $.ig.RectData(c.getOffset().left,c.getOffset().top,c.width(),c.height()):null},a:function(a){if(this._b==null||this._b.children()==null||this._b.children().count()<=a){return null}var b=this._b.children().__inner[a];if(b==null){return null}var c=b.content();if(c==null){return null}var d=c.actualItemBrush()!=null?c.actualItemBrush():c.itemBrush();return $.ig.AppearanceHelper.prototype.b(d)},f:function(){var a=new $$t.w;var b=this._c._m;var c=b.getChildCount();for(var d=0;d<c;d++){var e=new $$t.u;var f=b.getChildAt(d);e.label(f.getText());e.label(e.label()!=null?e.label().trim():null);e.bounds(new $.ig.RectData(f.getOffset().left,f.getOffset().top,f.width(),f.height()));e.labelBounds(this.h(f));e.badgeBounds(this.g(f));a.items().add(e)}for(var g=0;g<this._b.children().count();g++){a.items().__inner[g].appearance().fill(this.a(g))}return a},$type:new $.ig.Type("LegendBaseView",$.ig.$ot)},true);$c("ItemLegendView:n","LegendBaseView",{init:function(a){$$t.$o.init.call(this,a);this._am=a},_am:null,ai:function(){$$t.$o.ai.call(this)},j:function(){return true},an:function(a){return this._c.e(a)},f:function(){var a=new $$t.w;var b=this._c._h.rootWrapper();b=b.getChildAt(0);var c=b.getChildCount();for(var d=0;d<c;d++){var e=new $$t.u;var f=b.getChildAt(d);e.label(f.getText());e.label(e.label()!=null?e.label().trim():null);e.bounds(new $.ig.RectData(f.getOffset().left,f.getOffset().top,f.width(),f.height()));e.labelBounds(this.h(f));e.badgeBounds(this.g(f));a.items().add(e)}for(var g=0;g<c;g++){a.items().__inner[g].appearance().fill(this.a(g))}return a},$type:new $.ig.Type("ItemLegendView",$$t.$o.$type)},true);$c("LegendView:p","LegendBaseView",{init:function(a){$$t.$o.init.call(this,a);this._am=a},_am:null,ai:function(){$$t.$o.ai.call(this)},$type:new $.ig.Type("LegendView",$$t.$o.$type)},true);$c("FinancialLegendView:q","LegendBaseView",{init:function(a){$$t.$o.init.call(this,a);this._c=new $$t.a(this)},$type:new $.ig.Type("FinancialLegendView",$$t.$o.$type)},true);$c("ScaleLegendView:r","LegendBaseView",{init:function(a){this._a9=new $$a.af;this.ap=false;$$t.$o.init.call(this,a);this._an=a;this._a4=new $$a.an;this._a3=new $$a.an;this._a8=new $$a.bt},_an:null,_a8:null,_a4:null,_a3:null,a0:function(){},z:function(){},a7:function(){return $$a.$ax.u(0,0,0,0)},au:function(){return new $$t.s},aw:function(a,b,c){var d=a;d._a.add(function(){var $ret=new $$t.t;$ret._b=function(){var $ret=new $$a.at;$ret.color(b);return $ret}();$ret._a=c;return $ret}())},a1:function(a,b,c){if(b){this._a5=this._an.ba().actualMarkerBrush();this._am=null}else{this._a5=null;this._am=c;this._am.b()}this.ax()},_am:null,_ao:null,_a9:null,ah:function(a){$$t.$o.ah.call(this,a);this._ao=this._c.b(a);this._a9=this._c.aa();this.ax()},ap:false,ax:function(){if(!this.ap){this.ap=true;if(this._c._h!=null){this._c._h.setTimeout(this.a2.runOn(this),0)}else{window.setTimeout(this.a2.runOn(this),0)}}},a2:function(){if(this.ap){this.ap=false;this.az()}},az:function(){if(this._ao==null){return}this.ay()},as:function(a){var b=$b($$a.$an.$type,a);if(b!=null&&b.ak()!=null){return this._ao.e(b)+0}return 0},ar:function(a){return this._aq+0},_aq:0,_a6:null,ay:function(){if(this._an.ba()==null||this._an.ba().container()==null||this._am==null&&this._a5==null){return}if(this._ao.d()){var a=$b($.ig.IInternalLegendOwner.prototype.$type,this._an.ba().container());this._ao.ac(a.getFontInfo());this._aq=a.getFontHeight();this._a6=a.getFontBrush();var b=this.as(this._a4);var c=this.as(this._a3);var d=Math.max(b,c)+4;if(d>=this._a9.width()){d=0}var e=this._a9.width()-d;var f=2;var g=2;e=e-4;var h=this._a9.height()-4;var i=f+e+4;var j=g;var k=h;var l={__x:f+3/5*e,__y:g,$type:$$a.$y.$type,getType:$.ig.$op.getType,getGetHashCode:$.ig.$op.getGetHashCode,typeName:$.ig.$op.typeName};var m={__x:f+5/5*e,__y:g,$type:$$a.$y.$type,getType:$.ig.$op.getType,getGetHashCode:$.ig.$op.getGetHashCode,typeName:$.ig.$op.typeName};var n={__x:f+5/5*e,__y:h,$type:$$a.$y.$type,getType:$.ig.$op.getType,getGetHashCode:$.ig.$op.getGetHashCode,typeName:$.ig.$op.typeName};var o={__x:f,__y:h,$type:$$a.$y.$type,getType:$.ig.$op.getType,getGetHashCode:$.ig.$op.getGetHashCode,typeName:$.ig.$op.typeName};var p=this._a8;p._aj.clear();p._aj.add(l);p._aj.add(m);p._aj.add(n);p._aj.add(o);this._a4._n=i;this._a4._o=j;this._a4._am=this._a6;this._a3._n=i;this._a3._o=j+k-this.ar(this._a4);this._a3._am=this._a6;this._ao.k(0,0,this._a9.width(),this._a9.height());if(this._am==null&&this._a5!=null){p.__fill=this._a5;this._ao.t(p)}else{this._c.x(this._ao,p,this._am,new $$a.ae(0,g,f,e,h))}if(d>0){this._ao.w(this._a4);this._ao.w(this._a3)}}},_a5:null,aj:function(){$$t.$o.aj.call(this)},$type:new $.ig.Type("ScaleLegendView",$$t.$o.$type)},true);$c("GradientData:s","Object",{init:function(){$.ig.$op.init.call(this);this._a=new $$4.x($$t.$t.$type,0)},_a:null,b:function(){this._a.sort2(function(a,b){return $4(a._a,b._a)})},$type:new $.ig.Type("GradientData",$.ig.$ot)},true);$c("GradientStopData:t","Object",{init:function(){$.ig.$op.init.call(this)},_a:0,_b:null,$type:new $.ig.Type("GradientStopData",$.ig.$ot)},true);$c("LegendItemVisualData:u","Object",{init:function(){$.ig.$op.init.call(this);this.labelBounds($.ig.RectData.prototype.empty());this.appearance(new $.ig.PrimitiveAppearanceData)},_label:null,label:function(a){if(arguments.length===1){this._label=a;return a}else{return this._label}},_labelBounds:null,labelBounds:function(a){if(arguments.length===1){this._labelBounds=a;return a}else{return this._labelBounds}},_appearance:null,appearance:function(a){if(arguments.length===1){this._appearance=a;return a}else{return this._appearance}},_labelAppearance:null,labelAppearance:function(a){if(arguments.length===1){this._labelAppearance=a;return a}else{return this._labelAppearance}},_bounds:null,bounds:function(a){if(arguments.length===1){this._bounds=a;return a}else{return this._bounds}},_badgeBounds:null,badgeBounds:function(a){if(arguments.length===1){this._badgeBounds=a;return a}else{return this._badgeBounds}},serialize:function(){var a=new $$6.aj(0);a.u("{");if(this.label()!=null){a.u('label: "'+this.label()+'", ')}if(this.appearance()!=null){a.u("appearance: "+this.appearance().serialize()+", ")}if(this.labelAppearance()!=null){a.u("labelAppearance: "+this.labelAppearance().serialize()+", ")}var b=function(c){return"{ left: "+c.left()+", top: "+c.top()+", width: "+c.width()+", height: "+c.height()+"}"};a.q("bounds: {0},\r\n",b(this.bounds()));a.q("badgeBounds: {0},\r\n",b(this.badgeBounds()));a.q("labelBounds: {0}\r\n",b(this.labelBounds()));a.u("}");return a.toString()},$type:new $.ig.Type("LegendItemVisualData",$.ig.$ot)},true);$c("LegendVisualDataList:v","List$1",{init:function(){$$4.$x.init.call(this,$$t.$u.$type,0)},$type:new $.ig.Type("LegendVisualDataList",$$4.$x.$type.specialize($$t.$u.$type))},true);$c("LegendVisualData:w","Object",{init:function(){$.ig.$op.init.call(this);this.items(new $$t.v)},_items:null,items:function(a){if(arguments.length===1){this._items=a;return a}else{return this._items}},_viewport:null,viewport:function(a){if(arguments.length===1){this._viewport=a;return a}else{return this._viewport}},_width:0,width:function(a){if(arguments.length===1){this._width=a;return a}else{return this._width}},_height:0,height:function(a){if(arguments.length===1){this._height=a;return a}else{return this._height}},serialize:function(){var a=new $$6.aj(0);a.u("{");a.u("width: "+this.width()+",");a.u("height: "+this.height()+",");a.u("items: [");for(var b=0;b<this.items().count();b++){if(b!=0){a.l(", ")}a.l(this.items().__inner[b].serialize())}a.u("],");a.u("}");return a.toString()},$type:new $.ig.Type("LegendVisualData",$.ig.$ot)},true)});