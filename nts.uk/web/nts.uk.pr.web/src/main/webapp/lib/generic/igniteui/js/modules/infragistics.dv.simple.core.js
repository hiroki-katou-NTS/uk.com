/*!@license
* Infragistics.Web.ClientUI infragistics.dv.simple.core.js 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends:
*     jquery-1.4.4.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*/

(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["AbstractEnumerable:a", 
"Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"Func$1:ba", 
"MulticastDelegate:bb", 
"IntPtr:bc", 
"AbstractEnumerator:bd", 
"ArgumentException:bg", 
"IArray:bi", 
"List$1:bj", 
"IList$1:bk", 
"ICollection$1:bl", 
"Script:bm", 
"IArrayList:bn", 
"Array:bo", 
"CompareCallback:bp", 
"Func$3:bq", 
"Action$1:br", 
"Comparer$1:bs", 
"IComparer:bt", 
"IComparer$1:bu", 
"DefaultComparer$1:bv", 
"Comparison$1:bw", 
"ReadOnlyCollection$1:bx", 
"Predicate$1:by", 
"NotImplementedException:bz", 
"IDictionary$2:b1", 
"Dictionary$2:b2", 
"IDictionary:b3", 
"Func$2:b4", 
"KeyValuePair$2:b5", 
"Enumerable:b6", 
"Thread:b7", 
"ThreadStart:b8", 
"IOrderedEnumerable$1:b9", 
"SortedList$1:ca", 
"Math:cb", 
"ArgumentNullException:cc", 
"IEqualityComparer$1:cd", 
"EqualityComparer$1:ce", 
"IEqualityComparer:cf", 
"DefaultEqualityComparer$1:cg", 
"Dictionary_EnumerableCollection$3:ch", 
"InvalidOperationException:ci", 
"GenericEnumerable$1:cj", 
"GenericEnumerator$1:ck", 
"NotifyCollectionChangedEventArgs:co", 
"EventArgs:cp", 
"NotifyCollectionChangedAction:cq", 
"Delegate:cx", 
"Interlocked:cy", 
"DependencyObject:de", 
"Dictionary:df", 
"DependencyProperty:dg", 
"PropertyMetadata:dh", 
"PropertyChangedCallback:di", 
"DependencyPropertyChangedEventArgs:dj", 
"DependencyPropertiesCollection:dk", 
"UnsetValue:dl", 
"Binding:dm", 
"PropertyPath:dn", 
"APIFactory:ds", 
"Point:dt", 
"Rect:du", 
"Size:dv", 
"Color:dw", 
"JQueryPromise:e7", 
"Action:e8", 
"JQueryDeferred:fb", 
"JQuery:fc", 
"JQueryObject:fd", 
"Element:fe", 
"ElementAttributeCollection:ff", 
"ElementCollection:fg", 
"WebStyle:fh", 
"ElementNodeType:fi", 
"Document:fj", 
"EventListener:fk", 
"IElementEventHandler:fl", 
"ElementEventHandler:fm", 
"ElementAttribute:fn", 
"JQueryPosition:fo", 
"JQueryCallback:fp", 
"JQueryEvent:fq", 
"JQueryUICallback:fr", 
"Random:f6", 
"Tuple$2:f8", 
"UIElement:gd", 
"Transform:ge", 
"FrameworkElement:gg", 
"Visibility:gh", 
"Style:gi", 
"Control:gj", 
"Thickness:gk", 
"HorizontalAlignment:gl", 
"VerticalAlignment:gm", 
"ContentControl:gn", 
"DataTemplate:go", 
"DataTemplateRenderHandler:gp", 
"DataTemplateRenderInfo:gq", 
"DataTemplatePassInfo:gr", 
"DataTemplateMeasureHandler:gs", 
"DataTemplateMeasureInfo:gt", 
"DataTemplatePassHandler:gu", 
"TextBlock:gy", 
"Brush:gz", 
"LinearGradientBrush:g5", 
"GradientStop:g6", 
"DoubleCollection:g7", 
"FillRule:g8", 
"GeometryType:g9", 
"Geometry:ha", 
"GeometryCollection:hb", 
"GeometryGroup:hc", 
"LineGeometry:hd", 
"RectangleGeometry:he", 
"EllipseGeometry:hf", 
"PathGeometry:hg", 
"PathFigureCollection:hh", 
"PathFigure:hi", 
"PathSegmentCollection:hj", 
"PathSegmentType:hk", 
"PathSegment:hl", 
"LineSegment:hm", 
"BezierSegment:hn", 
"PolyBezierSegment:ho", 
"PointCollection:hp", 
"PolyLineSegment:hq", 
"ArcSegment:hr", 
"SweepDirection:hs", 
"RotateTransform:hv", 
"TranslateTransform:hw", 
"ScaleTransform:hx", 
"TransformGroup:hy", 
"TransformCollection:hz", 
"Shape:h1", 
"Line:h2", 
"Path:h3", 
"Polygon:h4", 
"Polyline:h5", 
"Rectangle:h6"]);


$.ig.util.defType('Visibility', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Visible";
			case 1: return "Collapsed";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('Visibility', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('VerticalAlignment', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Top";
			case 1: return "Center";
			case 2: return "Bottom";
			case 3: return "Stretch";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('VerticalAlignment', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HorizontalAlignment', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Left";
			case 1: return "Center";
			case 2: return "Right";
			case 3: return "Stretch";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('HorizontalAlignment', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('SweepDirection', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Counterclockwise";
			case 1: return "Clockwise";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('SweepDirection', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('PathSegmentType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Line";
			case 1: return "Bezier";
			case 2: return "PolyBezier";
			case 3: return "PolyLine";
			case 4: return "Arc";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('PathSegmentType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('GeometryType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Group";
			case 1: return "Line";
			case 2: return "Rectangle";
			case 3: return "Ellipse";
			case 4: return "Path";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('GeometryType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('FillRule', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "EvenOdd";
			case 1: return "Nonzero";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('FillRule', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('NotifyCollectionChangedAction', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Add";
			case 1: return "Remove";
			case 2: return "Replace";
			case 4: return "Reset";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('NotifyCollectionChangedAction', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('AbstractEnumerable', 'Object', {
	__inner: null,
	init: function (inner) {
		$.ig.Object.prototype.init.call(this);
		this.__inner = inner;
	},
	getEnumerator: function () {
		return new $.ig.AbstractEnumerator(this.__inner().getEnumerator());
	}
	,
	$type: new $.ig.Type('AbstractEnumerable', $.ig.Object.prototype.$type, [$.ig.IEnumerable.prototype.$type])
}, true);

$.ig.util.defType('AbstractEnumerator', 'Object', {
	__inner: null,
	init: function (inner) {
		$.ig.Object.prototype.init.call(this);
		this.__inner = inner;
	},
	current: function () {
		return this.__inner.current();
	}
	,
	moveNext: function () {
		return this.__inner.moveNext();
	}
	,
	reset: function () {
		this.__inner.reset();
	}
	,
	$type: new $.ig.Type('AbstractEnumerator', $.ig.Object.prototype.$type, [$.ig.IEnumerator.prototype.$type])
}, true);

$.ig.util.defType('IArray', 'Object', {
	$type: new $.ig.Type('IArray', null)
}, true);

$.ig.util.defType('List$1', 'Object', {
	$t: null,
	__inner: null,
	__useFastCompare: false,
	init: function ($t, initNumber) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
			}
			return;
		}
		this.__syncRoot = {};
		$.ig.Object.prototype.init.call(this);
		this.__inner = [];
		this.__useFastCompare = this.$t.InstanceConstructor && this.$t.InstanceConstructor.prototype.equals === $.ig.Object.prototype.equals;
	},
	init1: function ($t, initNumber, source) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.List$1.prototype.init.call(this, this.$t, 0);
		if (this.tryArray(0, source)) {
			return;
		}
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			this.add(item);
		}
	},
	init2: function ($t, initNumber, capacity) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.List$1.prototype.init.call(this, this.$t, 0);
	},
	setItem: function (index, newItem) {
		this.__inner[index] = newItem;
	}
	,
	insertItem: function (index, newItem) {
		this.__inner.splice(index, 0, newItem);
	}
	,
	addItem: function (newItem) {
		this.__inner.push(newItem);
	}
	,
	removeItem: function (index) {
		this.__inner.splice(index, 1);
	}
	,
	clearItems: function () {
		this.__inner = [];
	}
	,
	item: function (index, value) {
		if (arguments.length === 2) {
			this.setItem(index, value);
			return value;
		} else {
			return this.__inner[index];
		}
	}
	,
	indexOf: function (item) {
		if (this.__useFastCompare) {
			return this.__inner.indexOf(item);
		}
		for (var i = 0; i < this.__inner.length; i++) {
			if ($.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$t, item), $.ig.util.getBoxIfEnum(this.$t, this.__inner[i]))) {
				return i;
			}
		}
		return -1;
	}
	,
	indexOf2: function (item, index) {
		if (this.__useFastCompare) {
			return this.__inner.indexOf(item, index);
		}
		for (; index < this.__inner.length; index++) {
			if ($.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$t, item), $.ig.util.getBoxIfEnum(this.$t, this.__inner[index]))) {
				return index;
			}
		}
		return -1;
	}
	,
	lastIndexOf: function (item) {
		if (this.__useFastCompare) {
			return this.__inner.lastIndexOf(item);
		}
		for (var i = this.__inner.length - 1; i >= 0; i--) {
			if ($.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$t, item), $.ig.util.getBoxIfEnum(this.$t, this.__inner[i]))) {
				return i;
			}
		}
		return -1;
	}
	,
	insert: function (index, item) {
		this.insertItem(index, item);
	}
	,
	removeAt: function (index) {
		this.removeItem(index);
	}
	,
	count: function () {
		return this.__inner.length;
	}
	,
	isReadOnly: function () {
		return false;
	}
	,
	add: function (item) {
		this.addItem(item);
	}
	,
	clear: function () {
		this.clearItems();
	}
	,
	contains: function (item) {
		return this.indexOf(item) >= 0;
	}
	,
	copyTo: function (array, arrayIndex) {
		for (var i = 0; i < this.__inner.length; i++) {
			array[arrayIndex + i] = this.__inner[i];
		}
	}
	,
	remove: function (item) {
		var indexOf = this.indexOf(item);
		if (indexOf < 0) {
			return false;
		}
		this.removeItem(indexOf);
		return true;
	}
	,
	getEnumerator: function () {
		return this.__inner.getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.__inner.getEnumerator();
	}
	,
	asArray: function () {
		return this.__inner;
	}
	,
	tryArray: function (index_, collection_) {
		var asArrayList = $.ig.util.cast($.ig.IArrayList.prototype.$type, collection_);
		if (asArrayList != null) {
			var a_ = asArrayList.asArrayList();
			Array.prototype.splice.apply(this.__inner, Array.prototype.concat.apply([index_, 0], a_));
			return true;
		}
		var asArray = $.ig.util.cast($.ig.IArray.prototype.$type, collection_);
		if (asArray != null) {
			var a_ = asArray.asArray();
			Array.prototype.splice.apply(this.__inner, Array.prototype.concat.apply([index_, 0], a_));
			return true;
		}
		var asList_ = $.ig.util.cast($.ig.IList$1.prototype.$type.specialize(this.$t), collection_);
		if (asList_ != null) {
			for (var i_ = 0; i_ < asList_.count(); i_++) {
				var item_ = asList_.item(i_);
				this.__inner.splice(index_ + i_, 0, item_);
			}
			return true;
		}
		var arr_ = $.isArray(collection_) ? collection_ : null;;
		if (arr_ != null) {
			var inn_ = this.__inner;
			if (this.__inner.length == 0) {
				for (var i_ = 0; i_ < arr_.length; i_++) {
					inn_[index_++] = $.ig.util.castObjTo$t(this.$t, arr_[i_]);
				}
			} else {
				for (var i_ = 0; i_ < arr_.length; i_++) {
					inn_.splice(index_++, 0, arr_[i_]);
				}
			}
			return true;
		}
		return false;
	}
	,
	insertRange1: function (index, collection) {
		if (this.tryArray(index, collection)) {
			return;
		}
		var j_ = index;
		var en = collection.getEnumerator();
		while (en.moveNext()) {
			var item_ = en.current();
			this.__inner.splice(j_, 0, item_);
			j_++;
		}
	}
	,
	insertRange: function (index, collection) {
		if (this.tryArray(index, collection)) {
			return;
		}
		var j_ = index;
		var en = collection.getEnumerator();
		while (en.moveNext()) {
			var item_ = en.current();
			this.__inner.splice(j_, 0, item_);
			j_++;
		}
	}
	,
	removeRange: function (index_, numToRemove_) {
		this.__inner.splice(index_, numToRemove_);
	}
	,
	copyTo1: function (array, index) {
		$.ig.util.arrayCopyTo(this.__inner, array, index);
	}
	,
	isFixedSize: function () {
		return false;
	}
	,
	add1: function (value) {
		this.addItem($.ig.util.castObjTo$t(this.$t, value));
		return this.__inner.length - 1;
	}
	,
	contains1: function (item) {
		return this.indexOf1(item) >= 0;
	}
	,
	indexOf1: function (item) {
		return this.indexOf($.ig.util.castObjTo$t(this.$t, item));
	}
	,
	insert1: function (index, value) {
		this.insertItem(index, $.ig.util.castObjTo$t(this.$t, value));
	}
	,
	remove1: function (value) {
		var indexOf = this.indexOf1(value);
		if (indexOf < 0) {
			return;
		}
		this.removeItem(indexOf);
	}
	,
	sort: function () {
		var c = null;
		if (this.$t == Number) {
			c = function (n1, n2) {
				var d1 = n1;
				var d2 = n2;
				if (d1 < d2) {
					return -1;
				}
				if (d1 == d2) {
					return 0;
				}
				return 1;
			};
		} else if (this.$t == $.ig.Number.prototype.$type) {
			c = function (n1, n2) {
				var f1 = n1;
				var f2 = n2;
				if (f1 < f2) {
					return -1;
				}
				if (f1 == f2) {
					return 0;
				}
				return 1;
			};
		} else if (this.$t == $.ig.Number.prototype.$type) {
			c = function (n1, n2) {
				var i1 = $.ig.util.getValue(n1);
				var i2 = $.ig.util.getValue(n2);
				if (i1 < i2) {
					return -1;
				}
				if (i1 == i2) {
					return 0;
				}
				return 1;
			};
		} else if (this.$t == $.ig.Date.prototype.$type) {
			c = function (n1, n2) {
				var d1 = n1;
				var d2 = n2;
				if (d1.getTime() < d2.getTime()) {
					return -1;
				}
				if (d1.getTime() == d2.getTime()) {
					return 0;
				}
				return 1;
			};
		} else {
			c = function (n1, n2) {
				return (n1).compareTo(n2);
			};
		}
		this.sortHelper(c);
	}
	,
	sortHelper: function (compare_) {
		this.__inner.sort(compare_);
	}
	,
	sort2: function (compare_) {
		this.__inner.sort(compare_);
	}
	,
	_capacity: 0,
	capacity: function (value) {
		if (arguments.length === 1) {
			this._capacity = value;
			return value;
		} else {
			return this._capacity;
		}
	}
	,
	addRange: function (values) {
		var en = values.getEnumerator();
		while (en.moveNext()) {
			var item_ = en.current();
			this.__inner.push(item_);
		}
	}
	,
	toArray: function () {
		return this.__inner;
	}
	,
	forEach: function (action) {
		for (var i = 0; i < this.__inner.length; i++) {
			action(this.__inner[i]);
		}
	}
	,
	isSynchronized: function () {
		return true;
	}
	,
	__syncRoot: null,
	syncRoot: function () {
		return this.__syncRoot;
	}
	,
	binarySearch: function (item) {
		return this.binarySearch1(item, $.ig.Comparer$1.prototype.defaultComparerValue(this.$t));
	}
	,
	binarySearch1: function (item, comparer) {
		var start = 0;
		var end = this.count() - 1;
		while (start <= end) {
			var mid = start + ($.ig.intDivide((end - start), 2));
			var testValue = this.__inner[mid];
			var compareResult = comparer.compare(testValue, item);
			if (compareResult == 0) {
				return mid;
			}
			if (compareResult < 0) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return ~start;
	}
	,
	asReadOnly: function () {
		return new $.ig.ReadOnlyCollection$1(this.$t, 1, this);
	}
	,
	reverse: function () {
		for (var i = 0; i < $.ig.intDivide(this.count(), 2); i++) {
			var other = this.count() - i - 1;
			var temp = this.__inner[i];
			this.__inner[i] = this.__inner[other];
			this.__inner[other] = temp;
		}
	}
	,
	sort1: function (comparer) {
		this.sort2(comparer.compare.runOn(comparer));
	}
	,
	findIndex: function (match) {
		for (var i = 0; i < this.__inner.length; i++) {
			if (match(this.__inner[i])) {
				return i;
			}
		}
		return -1;
	}
	,
	removeAll: function (match) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	$type: new $.ig.Type('List$1', $.ig.Object.prototype.$type, [$.ig.IList$1.prototype.$type.specialize(0), $.ig.IArray.prototype.$type, $.ig.IList.prototype.$type])
}, true);

$.ig.util.defType('IComparer', 'Object', {
	$type: new $.ig.Type('IComparer', null)
}, true);

$.ig.util.defType('IComparer$1', 'Object', {
	$type: new $.ig.Type('IComparer$1', null)
}, true);

$.ig.util.defType('Comparer$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
	},
	defaultComparerValue: function ($t) {
		return new $.ig.DefaultComparer$1($t);
	}
	,
	compare: function (x, y) {
	}
	,
	create: function ($t, comparison) {
		return null;
	}
	,
	$type: new $.ig.Type('Comparer$1', $.ig.Object.prototype.$type, [$.ig.IComparer.prototype.$type, $.ig.IComparer$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('DefaultComparer$1', 'Comparer$1', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Comparer$1.prototype.init.call(this, this.$t);
	},
	compare: function (x, y) {
		var xComparable = $.ig.util.cast($.ig.IComparable$1.prototype.$type.specialize(this.$t), x);
		if (xComparable != null) {
			return xComparable.compareTo(y);
		}
		var yComparable = $.ig.util.cast($.ig.IComparable$1.prototype.$type.specialize(this.$t), y);
		if (yComparable != null) {
			return -yComparable.compareTo(x);
		}
		return $.ig.util.compare(x, y);
	}
	,
	$type: new $.ig.Type('DefaultComparer$1', $.ig.Comparer$1.prototype.$type.specialize(0))
}, true);

$.ig.util.defType('KeyValuePair$2', 'ValueType', {
	$tKey: null,
	$tValue: null,
	init: function ($tKey, $tValue, initNumber) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.ValueType.prototype.init.call(this);
	},
	__key: null,
	__value: null,
	init1: function ($tKey, $tValue, initNumber, key, value) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		$.ig.ValueType.prototype.init.call(this);
		this.__key = key;
		this.__value = value;
	},
	key: function () {
		return this.__key;
	}
	,
	value: function () {
		return this.__value;
	}
	,
	$type: new $.ig.Type('KeyValuePair$2', $.ig.ValueType.prototype.$type)
}, true);

$.ig.util.defType('IDictionary$2', 'Object', {
	$type: new $.ig.Type('IDictionary$2', null, [$.ig.ICollection$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(0, 1)), $.ig.IEnumerable$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(0, 1)), $.ig.IEnumerable.prototype.$type])
}, true);

$.ig.util.defType('Dictionary$2', 'Object', {
	$tKey: null,
	$tValue: null,
	__comparer: null,
	__count: 0,
	__useStrings: false,
	__needsEnsure: false,
	__assumeUniqueKeys: false,
	__keysUnique: null,
	__values: null,
	init: function ($tKey, $tValue, initNumber) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
				case 3:
					this.init3.apply(this, arguments);
					break;
				case 4:
					this.init4.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Dictionary$2.prototype.init4.call(this, this.$tKey, this.$tValue, 4, 0, null);
	},
	init1: function ($tKey, $tValue, initNumber, capacity) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		$.ig.Dictionary$2.prototype.init4.call(this, this.$tKey, this.$tValue, 4, capacity, null);
	},
	init2: function ($tKey, $tValue, initNumber, comparer) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		$.ig.Dictionary$2.prototype.init4.call(this, this.$tKey, this.$tValue, 4, 0, comparer);
	},
	init3: function ($tKey, $tValue, initNumber, dictionary) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		$.ig.Dictionary$2.prototype.init1.call(this, this.$tKey, this.$tValue, 1, dictionary.count());
		var en = dictionary.getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			this.item(pair.key(), pair.value());
		}
	},
	init4: function ($tKey, $tValue, initNumber, capacity, comparer) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		$.ig.Object.prototype.init.call(this);
		this.__keysUnique = {};
		this.__values = {};
		this.__comparer = comparer || $.ig.EqualityComparer$1.prototype.defaultEqualityComparerValue(this.$tKey);
		this.__useStrings = comparer == null && ($tKey === $.ig.String.prototype.$type || $tKey === String);
		this.__needsEnsure = $tKey === $.ig.Object.prototype.$type || ($tKey.InstanceConstructor && !$tKey.InstanceConstructor.prototype.getHashCode);
		this.__assumeUniqueKeys = comparer == null && (this.__useStrings || this.__needsEnsure || $tKey.InstanceConstructor && $tKey.InstanceConstructor.prototype.getHashCode == $.ig.Object.prototype.getHashCode);
	},
	count: function () {
		return this.__count;
	}
	,
	item: function (key_, value) {
		if (arguments.length === 2) {
			this.addHelper(key_, value, false);
			return value;
		} else {
			var $self = this;
			var result;
			if ((function () { var $ret = $self.tryGetValue(key_, result); result = $ret.p1; return $ret.ret; }())) {
				return result;
			}
			return $.ig.util.getDefaultValue(this.$tValue);
		}
	}
	,
	length: function () {
		return this.__count;
	}
	,
	containsKey: function (key) {
		if (this.__assumeUniqueKeys) {
			var hash = this.hashUnique(key);
			return this.__keysUnique.hasOwnProperty(hash);
		} else {
			var hashCode = this.hashCode(key);
			var current = this.__values[hashCode];
			if (current) {
				if (current.$isHashSetBucket) {
					var $t = current;
					for (var i = 0; i < $t.length; i++) {
						var currentItem = $t[i];
						return this.__comparer.equalsC(currentItem.key, key);
					}
				} else {
					return this.__comparer.equalsC(current.key, key);
				}
			}
		}
		return false;
	}
	,
	remove: function (key) {
		if (this.__assumeUniqueKeys) {
			var hash = this.hashUnique(key);
			if (!this.__keysUnique.hasOwnProperty(hash)) {
				return false;
			}
			delete this.__keysUnique[hash];
			delete this.__values[hash];
			this.__count--;
			return true;
		}
		var hashCode = this.hashCode(key);
		var current = this.__values[hashCode];
		if (current) {
			if (current.$isHashSetBucket) {
				var $t = current;
				for (var i = 0; i < $t.length; i++) {
					var currentItem = $t[i];
					if (this.__comparer.equalsC(currentItem.key, key)) {
						current.removeItem(currentItem);
						if (current.length == 1) {
							this.__values[hashCode] = current[0];
						}
						this.__count--;
						return true;
					}
				}
			} else {
				if (this.__comparer.equalsC(current.key, key)) {
					delete this.__values[hashCode];
					this.__count--;
					return true;
				}
			}
		}
		return false;
	}
	,
	clear: function () {
		this.__count = 0;
		this.__keysUnique = {};
		this.__values = {};
	}
	,
	hashUnique: function (key) {
		if (this.__needsEnsure) {
			$.ig.util.ensureUniqueId(key);
		}
		if (this.__useStrings) {
			return $.ig.util.toString$1(this.$tKey, key);
		} else {
			return key.getHashCode().toString();
		}
	}
	,
	hashCode: function (key) {
		if (this.__needsEnsure) {
			$.ig.util.ensureUniqueId(key);
		} else {
			if (!key.getHashCode) {
				this.__needsEnsure = true;
				$.ig.util.ensureUniqueId(key);
			}
		}
		return this.__comparer.getHashCodeC(key);
	}
	,
	add: function (key, value) {
		this.addHelper(key, value, true);
	}
	,
	addHelper: function (key, value, add) {
		if (this.__assumeUniqueKeys) {
			var hash = this.hashUnique(key);
			if (!this.__keysUnique.hasOwnProperty(hash)) {
				this.__count++;
			} else if (add) {
				throw new $.ig.ArgumentException(1, "Duplicate key added to the dictionary");
			}
			this.__keysUnique[hash] = key;
			this.__values[hash] = value;
		} else {
			var hashCode = this.hashCode(key);
			var current = this.__values[hashCode];
			if (current) {
				if (current.$isHashSetBucket) {
					var $t = current;
					for (var i = 0; i < $t.length; i++) {
						var currentItem = $t[i];
						if (this.__comparer.equalsC(currentItem.key, key)) {
							if (add) {
								throw new $.ig.ArgumentException(1, "Duplicate key added to the dictionary");
							}
							currentItem.value = value;
							return;
						}
					}
					current.push({key: key, value: value});
					this.__count++;
				} else {
					if (this.__comparer.equalsC(current.key, key)) {
						if (add) {
							throw new $.ig.ArgumentException(1, "Duplicate key added to the dictionary");
						}
						current.value = value;
					} else {
						var bucket = [current, {key: key, value: value}];
						bucket.$isHashSetBucket = true;;
						this.__values[hashCode] = bucket;
						this.__count++;
					}
				}
			} else {
				this.__values[hashCode] = {key: key, value: value};
				this.__count++;
			}
		}
	}
	,
	tryGetValue: function (key, value) {
		if (this.__assumeUniqueKeys) {
			var hash = this.hashUnique(key);
			if (this.__keysUnique.hasOwnProperty(hash)) {
				value = this.__values[hash];
				return {
					ret: true,
					p1: value
				};
			}
		} else {
			var hashCode = this.hashCode(key);
			var current = this.__values[hashCode];
			if (current) {
				if (current.$isHashSetBucket) {
					var $t = current;
					for (var i = 0; i < $t.length; i++) {
						var currentItem = $t[i];
						if (this.__comparer.equalsC(currentItem.key, key)) {
							value = currentItem.value;
							return {
								ret: true,
								p1: value
							};
						}
					}
				} else {
					if (this.__comparer.equalsC(current.key, key)) {
						value = current.value;
						return {
							ret: true,
							p1: value
						};
					}
				}
			}
		}
		value = $.ig.util.getDefaultValue(this.$tValue);
		return {
			ret: false,
			p1: value
		};
	}
	,
	isReadOnly: function () {
		return false;
	}
	,
	add1: function (item) {
		this.add(item.key(), item.value());
	}
	,
	contains: function (item) {
		var $self = this;
		var test;
		return (function () { var $ret = $self.tryGetValue(item.key(), test); test = $ret.p1; return $ret.ret; }()) && $.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$tValue, test), $.ig.util.getBoxIfEnum(this.$tValue, item.value()));
	}
	,
	copyTo: function (array, arrayIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	remove1: function (item) {
		this.remove(item.key());
		return true;
	}
	,
	getEnumerator: function () {
		return this.toEnumerable().getEnumerator();
	}
	,
	toEnumerable: function () {
		var d__ = new $.ig.Dictionary___ToEnumerable__IteratorClass$2(this.$tKey, this.$tValue, -2);
		d__.__4__this = this;
		return d__;
	}
	,
	getEnumerator: function () {
		return this.toEnumerable().getEnumerator();
	}
	,
	keys: function () {
		return new $.ig.Dictionary_EnumerableCollection$3(this.$tKey, this.$tValue, this.$tKey, this, $.ig.Enumerable.prototype.select$2($.ig.KeyValuePair$2.prototype.$type.specialize(this.$tKey, this.$tValue), this.$tKey, this.toEnumerable(), function (p) { return p.key(); }), this.__comparer || $.ig.EqualityComparer$1.prototype.defaultEqualityComparerValue(this.$tKey));
	}
	,
	values: function () {
		return new $.ig.Dictionary_EnumerableCollection$3(this.$tKey, this.$tValue, this.$tValue, this, $.ig.Enumerable.prototype.select$2($.ig.KeyValuePair$2.prototype.$type.specialize(this.$tKey, this.$tValue), this.$tValue, this.toEnumerable(), function (p) { return p.value(); }), $.ig.EqualityComparer$1.prototype.defaultEqualityComparerValue(this.$tValue));
	}
	,
	$type: new $.ig.Type('Dictionary$2', $.ig.Object.prototype.$type, [$.ig.IDictionary$2.prototype.$type.specialize(0, 1), $.ig.IDictionary.prototype.$type])
}, true);

$.ig.util.defType('Dictionary_EnumerableCollection$3', 'Object', {
	$tKey: null,
	$tValue: null,
	$t: null,
	__collection: null,
	__comparer: null,
	__owner: null,
	init: function ($tKey, $tValue, $t, owner, collection, comparer) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$t = $t;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue, this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__collection = collection;
		this.__comparer = comparer;
		this.__owner = owner;
	},
	count: function () {
		return this.__owner.count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	add: function (item) {
		throw new $.ig.InvalidOperationException(0);
	}
	,
	clear: function () {
		throw new $.ig.InvalidOperationException(0);
	}
	,
	contains: function (item) {
		var en = this.__collection.getEnumerator();
		while (en.moveNext()) {
			var i = en.current();
			if ($.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$t, i), $.ig.util.getBoxIfEnum(this.$t, item))) {
				return true;
			}
		}
		return false;
	}
	,
	copyTo: function (array, arrayIndex) {
		var en = this.__collection.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			array[arrayIndex++] = item;
		}
	}
	,
	remove: function (item) {
		throw new $.ig.InvalidOperationException(0);
	}
	,
	getEnumerator: function () {
		return this.__collection.getEnumerator();
	}
	,
	getEnumerator1: function () {
		return this.__collection.getEnumerator();
	}
	,
	$type: new $.ig.Type('Dictionary_EnumerableCollection$3', $.ig.Object.prototype.$type, [$.ig.ICollection$1.prototype.$type.specialize(2)])
}, true);

$.ig.util.defType('EqualityComparer$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
	},
	defaultEqualityComparerValue: function ($t) {
		return new $.ig.DefaultEqualityComparer$1($t);
	}
	,
	equalsC: function (x, y) {
	}
	,
	getHashCodeC: function (obj) {
	}
	,
	equalsC: function (x, y) {
		return this.equalsC($.ig.util.castObjTo$t(this.$t, x), $.ig.util.castObjTo$t(this.$t, y));
	}
	,
	getHashCodeC: function (obj) {
		return this.getHashCodeC($.ig.util.castObjTo$t(this.$t, obj));
	}
	,
	$type: new $.ig.Type('EqualityComparer$1', $.ig.Object.prototype.$type, [$.ig.IEqualityComparer.prototype.$type, $.ig.IEqualityComparer$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('DefaultEqualityComparer$1', 'EqualityComparer$1', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.EqualityComparer$1.prototype.init.call(this, this.$t);
	},
	equalsC: function (x, y) {
		return $.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum(this.$t, x), $.ig.util.getBoxIfEnum(this.$t, y));
	}
	,
	getHashCodeC: function (obj) {
		return obj.getHashCode();
	}
	,
	$type: new $.ig.Type('DefaultEqualityComparer$1', $.ig.EqualityComparer$1.prototype.$type.specialize(0))
}, true);

$.ig.util.defType('GenericEnumerable$1', 'Object', {
	$t: null,
	__inner: null,
	init: function ($t, inner) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__inner = inner;
	},
	getEnumerator: function () {
		return new $.ig.GenericEnumerator$1(this.$t, this.__inner().getEnumerator());
	}
	,
	getEnumerator: function () {
		return new $.ig.GenericEnumerator$1(this.$t, this.__inner().getEnumerator());
	}
	,
	$type: new $.ig.Type('GenericEnumerable$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('GenericEnumerator$1', 'Object', {
	$t: null,
	__inner: null,
	init: function ($t, inner) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__inner = inner;
	},
	current: function () {
		return this.__inner.current();
	}
	,
	moveNext: function () {
		return this.__inner.moveNext();
	}
	,
	reset: function () {
		this.__inner.reset();
	}
	,
	dispose: function () {
	}
	,
	$type: new $.ig.Type('GenericEnumerator$1', $.ig.Object.prototype.$type, [$.ig.IEnumerator$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('NotifyCollectionChangedEventArgs', 'EventArgs', {
	init: function (initNumber, action) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.EventArgs.prototype.init.call(this);
		this.__action = action;
		this.__oldItems = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		this.__newItems = new $.ig.List$1($.ig.Object.prototype.$type, 0);
	},
	init1: function (initNumber, action, changedItem, index) {
		$.ig.EventArgs.prototype.init.call(this);
		this.__action = action;
		this.__oldItems = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		if (this.__action == $.ig.NotifyCollectionChangedAction.prototype.remove || this.__action == $.ig.NotifyCollectionChangedAction.prototype.replace) {
			this.__oldItems.add(changedItem);
			this.__oldStartingIndex = index;
		}
		if (this.__action != $.ig.NotifyCollectionChangedAction.prototype.remove) {
			this.__newItems = (function () {
				var $ret = new $.ig.List$1($.ig.Object.prototype.$type, 0);
				$ret.add1(changedItem);
				return $ret;
			}());
		} else {
			this.__newItems = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		}
		this.__newStartingIndex = index;
	},
	init2: function (initNumber, action, newItem, oldItem, index) {
		$.ig.EventArgs.prototype.init.call(this);
		this.__action = action;
		this.__newStartingIndex = index;
		this.__oldStartingIndex = index;
		this.__newItems = (function () {
			var $ret = new $.ig.List$1($.ig.Object.prototype.$type, 0);
			$ret.add1(newItem);
			return $ret;
		}());
		this.__oldItems = (function () {
			var $ret = new $.ig.List$1($.ig.Object.prototype.$type, 0);
			$ret.add1(oldItem);
			return $ret;
		}());
	},
	__action: 0,
	action: function () {
		return this.__action;
	}
	,
	__newItems: null,
	newItems: function () {
		return this.__newItems;
	}
	,
	__newStartingIndex: 0,
	newStartingIndex: function () {
		return this.__newStartingIndex;
	}
	,
	__oldItems: null,
	oldItems: function () {
		return this.__oldItems;
	}
	,
	__oldStartingIndex: 0,
	oldStartingIndex: function () {
		return this.__oldStartingIndex;
	}
	,
	$type: new $.ig.Type('NotifyCollectionChangedEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('ReadOnlyCollection$1', 'Object', {
	$t: null,
	init: function ($t, initNumber) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		this.__syncRoot = {};
		$.ig.Object.prototype.init.call(this);
	},
	init1: function ($t, initNumber, source) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		this.__syncRoot = {};
		$.ig.Object.prototype.init.call(this);
		this.__inner = source;
	},
	__inner: null,
	item: function (index, value) {
		if (arguments.length === 2) {
			this.__inner.item(index, value);
			return value;
		} else {
			return this.__inner.item(index);
		}
	}
	,
	indexOf: function (item) {
		return this.__inner.indexOf(item);
	}
	,
	insert: function (index, item) {
	}
	,
	removeAt: function (index) {
	}
	,
	count: function () {
		return this.__inner.count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	add: function (item) {
	}
	,
	clear: function () {
	}
	,
	contains: function (item) {
		return this.__inner.contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.__inner.copyTo(array, arrayIndex);
	}
	,
	remove: function (item) {
		return false;
	}
	,
	getEnumerator: function () {
		return this.__inner.getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.__inner.getEnumerator();
	}
	,
	isFixedSize: function () {
		return true;
	}
	,
	add1: function (value) {
		return -1;
	}
	,
	contains1: function (value) {
		return this.__inner.contains($.ig.util.castObjTo$t(this.$t, value));
	}
	,
	indexOf1: function (value) {
		return this.__inner.indexOf($.ig.util.castObjTo$t(this.$t, value));
	}
	,
	insert1: function (index, value) {
	}
	,
	remove1: function (value) {
	}
	,
	copyTo1: function (array, index) {
		this.__inner.copyTo(array, index);
	}
	,
	items: function () {
		return this.__inner;
	}
	,
	isSynchronized: function () {
		return true;
	}
	,
	__syncRoot: null,
	syncRoot: function () {
		return this.__syncRoot;
	}
	,
	$type: new $.ig.Type('ReadOnlyCollection$1', $.ig.Object.prototype.$type, [$.ig.IList$1.prototype.$type.specialize(0), $.ig.IList.prototype.$type])
}, true);

$.ig.util.defType('IOrderedEnumerable$1', 'Object', {
	$type: new $.ig.Type('IOrderedEnumerable$1', null, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	where$1: function ($tSource, source, predicate) {
		var d__ = new $.ig.Enumerable___Where__IteratorClass$1($tSource, -2);
		d__.__3__source = source;
		d__.__3__predicate = predicate;
		return d__;
	}
	,
	where$11: function ($tSource, source, predicate) {
		var d__ = new $.ig.Enumerable___Where__IteratorClass1$1($tSource, -2);
		d__.__3__source = source;
		d__.__3__predicate = predicate;
		return d__;
	}
	,
	select$2: function ($tSource, $tResult, source, selector) {
		var d__ = new $.ig.Enumerable___Select__IteratorClass$2($tSource, $tResult, -2);
		d__.__3__source = source;
		d__.__3__selector = selector;
		return d__;
	}
	,
	selectMany$2: function ($tSource, $tResult, source, selector) {
		var d__ = new $.ig.Enumerable___SelectMany__IteratorClass$2($tSource, $tResult, -2);
		d__.__3__source = source;
		d__.__3__selector = selector;
		return d__;
	}
	,
	ofType$1: function ($tResult, source) {
		var d__ = new $.ig.Enumerable___OfType__IteratorClass$1($tResult, -2);
		d__.__3__source = source;
		return d__;
	}
	,
	last$1: function ($tSource, source) {
		var ilist = $.ig.util.cast($.ig.IList$1.prototype.$type.specialize($tSource), source);
		if (ilist != null) {
			return ilist.item(ilist.count() - 1);
		}
		var current = $.ig.util.getDefaultValue($tSource);
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			current = item;
		}
		return current;
	}
	,
	first$1: function ($tSource, source) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			return item;
		}
		return $.ig.util.getDefaultValue($tSource);
	}
	,
	first$11: function ($tSource, source, predicate) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (predicate(item)) {
				return item;
			}
		}
		return $.ig.util.getDefaultValue($tSource);
	}
	,
	firstOrDefault$1: function ($tSource, source) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			return item;
		}
		return $.ig.util.getDefaultValue($tSource);
	}
	,
	orderBy$2: function ($tSource, $tKey, source, keySelector) {
		var buffer = new $.ig.SortedList$1($tSource, source);
		buffer.sort2(function (o1, o2) {
			var t1 = o1;
			var t2 = o2;
			var k1 = keySelector(t1);
			var k2 = keySelector(t2);
			if ($.ig.util.cast($.ig.IComparable.prototype.$type, k1) !== null) {
				return ($.ig.util.cast($.ig.IComparable.prototype.$type, k1)).compareTo($.ig.util.getBoxIfEnum($tKey, k2));
			} else {
				return $.ig.util.toString$1($tKey, k1).compareTo($.ig.util.toString$1($tKey, k2));
			}
		});
		return buffer;
	}
	,
	orderByDescending$2: function ($tSource, $tKey, source, keySelector) {
		var buffer = new $.ig.SortedList$1($tSource, source);
		buffer.sort2(function (o2, o1) {
			var t1 = o1;
			var t2 = o2;
			var k1 = keySelector(t1);
			var k2 = keySelector(t2);
			if ($.ig.util.cast($.ig.IComparable.prototype.$type, k1) !== null) {
				return ($.ig.util.cast($.ig.IComparable.prototype.$type, k1)).compareTo($.ig.util.getBoxIfEnum($tKey, k2));
			} else {
				return $.ig.util.toString$1($tKey, k1).compareTo($.ig.util.toString$1($tKey, k2));
			}
		});
		return buffer;
	}
	,
	toList$1: function ($tSource, source) {
		var list = new $.ig.List$1($tSource, 1, source);
		return list;
	}
	,
	range: function (startValue, count) {
		var d__ = new $.ig.Enumerable___Range__IteratorClass(-2);
		d__.__3__startValue = startValue;
		d__.__3__count = count;
		return d__;
	}
	,
	concat$1: function ($tSource, source1, source2) {
		var d__ = new $.ig.Enumerable___Concat__IteratorClass$1($tSource, -2);
		d__.__3__source1 = source1;
		d__.__3__source2 = source2;
		return d__;
	}
	,
	max: function (source) {
		var first = true;
		var max = 0;
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (first) {
				first = false;
				max = item;
			} else {
				max = Math.max(max, item);
			}
		}
		return max;
	}
	,
	max$1: function ($tSource, source, selector) {
		return $.ig.Enumerable.prototype.max($.ig.Enumerable.prototype.select$2($tSource, $.ig.Number.prototype.$type, source, selector));
	}
	,
	min: function (source) {
		var first = true;
		var min = 0;
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (first) {
				first = false;
				min = item;
			} else {
				min = Math.min(min, item);
			}
		}
		return min;
	}
	,
	min$1: function ($tSource, source, selector) {
		return $.ig.Enumerable.prototype.max($.ig.Enumerable.prototype.select$2($tSource, $.ig.Number.prototype.$type, source, selector));
	}
	,
	count$1: function ($tSource, source) {
		var count = 0;
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			count++;
		}
		return count;
	}
	,
	reverse$1: function ($tSource, source) {
		var d__ = new $.ig.Enumerable___Reverse__IteratorClass$1($tSource, -2);
		d__.__3__source = source;
		return d__;
	}
	,
	cast$1: function ($tResult, source) {
		if (source == null) {
			throw new $.ig.ArgumentNullException(0, "source");
		}
		var enumerable = $.ig.util.cast($.ig.IEnumerable$1.prototype.$type.specialize($tResult), source);
		if (enumerable != null) {
			return enumerable;
		}
		var list = new $.ig.List$1($tResult, 0);
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if ($.ig.util.cast($tResult, item) !== null) {
				list.add($.ig.util.castObjTo$t($tResult, item));
			} else {
				list.add($.ig.util.getDefaultValue($tResult));
			}
		}
		return list;
	}
	,
	take$1: function ($tSource, source, toTake) {
		var d__ = new $.ig.Enumerable___Take__IteratorClass$1($tSource, -2);
		d__.__3__source = source;
		d__.__3__toTake = toTake;
		return d__;
	}
	,
	skip$1: function ($tSource, source, toSkip) {
		var d__ = new $.ig.Enumerable___Skip__IteratorClass$1($tSource, -2);
		d__.__3__source = source;
		d__.__3__toSkip = toSkip;
		return d__;
	}
	,
	any$1: function ($tSource, source) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			return true;
		}
		return false;
	}
	,
	contains$1: function ($tSource, source, value_) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item_ = en.current();
			if (item_ === value_)
                {
                    return true;
                };
		}
		return false;
	}
	,
	union$1: function ($tSource, first, second) {
		return null;
	}
	,
	toArray$1: function ($tSource, source) {
		var arr = $.ig.util.cast($.ig.List$1.prototype.$type.specialize($tSource), source);
		if (arr != null) {
			return arr.toArray();
		}
		arr = new $.ig.List$1($tSource, 1, source);
		return arr.asArray();
	}
	,
	elementAt$1: function ($tSource, source, index) {
		var iList = $.ig.util.cast($.ig.IList$1.prototype.$type.specialize($tSource), source);
		if (iList != null) {
			return iList.item(index);
		}
		return $.ig.Enumerable.prototype.first$1($tSource, $.ig.Enumerable.prototype.skip$1($tSource, source, index));
	}
	,
	sum: function (source) {
		var sum = 0;
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			sum += item;
		}
		return sum;
	}
	,
	sum$1: function ($tSource, source, selector) {
		return $.ig.Enumerable.prototype.sum($.ig.Enumerable.prototype.select$2($tSource, Number, source, selector));
	}
	,
	sequenceEqual$1: function ($tSource, first, second) {
		if (first == null) {
			throw new $.ig.ArgumentNullException(0, "first");
		}
		if (second == null) {
			throw new $.ig.ArgumentNullException(0, "second");
		}
		{
			var enumerator = first.getEnumerator();
			try {
				var enumerator2 = second.getEnumerator();
				try {
					while (enumerator.moveNext()) {
						if (!enumerator2.moveNext() || !$.ig.Object.prototype.equalsStatic($.ig.util.getBoxIfEnum($tSource, enumerator.current()), $.ig.util.getBoxIfEnum($tSource, enumerator2.current()))) {
							return false;
						}
					}
					return (enumerator2.moveNext() == false);
				}
				finally {
					if (enumerator2 != null) {
						enumerator2.dispose();
					}
				}
			}
			finally {
				if (enumerator != null) {
					enumerator.dispose();
				}
			}
		}
	}
	,
	empty$1: function ($tSource) {
		return new $.ig.Enumerable___Empty__IteratorClass$1($tSource, -2);
	}
	,
	selectMany$3: function ($tSource, $tCollection, $tResult, source, collectionSelector, resultSelector) {
		var d__ = new $.ig.Enumerable___SelectMany__IteratorClass1$3($tSource, $tCollection, $tResult, -2);
		d__.__3__source = source;
		d__.__3__collectionSelector = collectionSelector;
		d__.__3__resultSelector = resultSelector;
		return d__;
	}
	,
	any$11: function ($tSource, source, predicate) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (predicate(item)) {
				return true;
			}
		}
		return false;
	}
	,
	firstOrDefault$11: function ($tSource, source, predicate) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (predicate(item)) {
				return item;
			}
		}
		return $.ig.util.getDefaultValue($tSource);
	}
	,
	all$1: function ($tSource, source, predicate) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (predicate(item) == false) {
				return false;
			}
		}
		return true;
	}
	,
	toDictionary$2: function ($tSource, $tKey, source, keySelector) {
		var d = new $.ig.Dictionary$2($tKey, $tSource, 0);
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			d.item(keySelector(item), item);
		}
		return d;
	}
	,
	lastOrDefault$1: function ($tSource, source) {
		throw new $.ig.NotImplementedException(0);
		var ilist = $.ig.util.cast($.ig.IList$1.prototype.$type.specialize($tSource), source);
		if (ilist != null) {
			return ilist.count() == 0 ? $.ig.util.getDefaultValue($tSource) : ilist.item(ilist.count() - 1);
		}
		var current = $.ig.util.getDefaultValue($tSource);
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			current = item;
		}
		return current;
	}
	,
	zip$3: function ($tFirst, $tSecond, $tResult, first, second, resultSelector) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	distinct$1: function ($tSource, source) {
		throw new $.ig.NotImplementedException(0);
		return null;
	}
	,
	$type: new $.ig.Type('Enumerable', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SortedList$1', 'List$1', {
	$tElement: null,
	init: function ($tElement, source) {
		this.$tElement = $tElement;
		this.$type = this.$type.specialize(this.$tElement);
		$.ig.List$1.prototype.init1.call(this, this.$tElement, 1, source);
	},
	getEnumerator: function () {
		return $.ig.List$1.prototype.getEnumerator.call(this);
	}
	,
	$type: new $.ig.Type('SortedList$1', $.ig.List$1.prototype.$type.specialize(0), [$.ig.IOrderedEnumerable$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('IArrayList', 'Object', {
	$type: new $.ig.Type('IArrayList', null)
}, true);

$.ig.util.defType('DependencyObject', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this._localValues = new $.ig.Dictionary(0);
		this._bindings = new $.ig.Dictionary(0);
	},
	_localValues: null,
	_bindings: null,
	getValue: function (dp) {
		if (this._localValues.containsKey(dp.name())) {
			return this._localValues.item(dp.name());
		}
		return dp.propertyMetadata().defaultValue();
	}
	,
	setValue: function (dp_, value) {
		if (dp_.hasCallback()) {
			var oldValue_ = null;
			var old = this._localValues.proxy[dp_.__name]; if (typeof old != 'undefined') { oldValue_ = old };
			this._localValues.item(dp_.__name, value);
			dp_.propertyMetadata().propertyChangedCallback()(this, new $.ig.DependencyPropertyChangedEventArgs(dp_, value, oldValue_));
		} else {
			this._localValues.item(dp_.__name, value);
		}
	}
	,
	clearValue: function (dp) {
		this._localValues.remove(dp.__name);
	}
	,
	readLocalValue: function (dp) {
		if (this._localValues.containsKey(dp.__name)) {
			return this._localValues.item(dp.name());
		}
		return $.ig.DependencyProperty.prototype.unsetValue;
	}
	,
	setBinding: function (dp, binding) {
		if (dp == null) {
			return;
		}
		this._bindings.item(dp.name(), binding);
	}
	,
	$type: new $.ig.Type('DependencyObject', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PropertyMetadata', 'Object', {
	__defaultValue: null,
	defaultValue: function (value) {
		if (arguments.length === 1) {
			this.__defaultValue = value;
			return value;
		} else {
			return this.__defaultValue;
		}
	}
	,
	__propertyChangedCallback: null,
	propertyChangedCallback: function (value) {
		if (arguments.length === 1) {
			this.__propertyChangedCallback = value;
			return value;
		} else {
			return this.__propertyChangedCallback;
		}
	}
	,
	init: function (initNumber, defaultValue) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
		this.defaultValue(defaultValue);
		this.propertyChangedCallback(null);
	},
	init1: function (initNumber, propertyChangedCallback) {
		$.ig.Object.prototype.init.call(this);
		this.defaultValue(null);
		this.propertyChangedCallback(propertyChangedCallback);
	},
	init2: function (initNumber, defaultValue, propertyChangedCallback) {
		$.ig.Object.prototype.init.call(this);
		this.defaultValue(defaultValue);
		this.propertyChangedCallback(propertyChangedCallback);
	},
	$type: new $.ig.Type('PropertyMetadata', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('UnsetValue', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('UnsetValue', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DependencyProperty', 'Object', {
	__name: null,
	__propertyType: null,
	__propertyMetadata: null,
	__hasCallback: false,
	hasCallback: function () {
		return this.__hasCallback;
	}
	,
	init: function (name, propertyType, propertyMetadata) {
		this.__hasCallback = false;
		$.ig.Object.prototype.init.call(this);
		this.__name = name;
		this.__propertyType = propertyType;
		this.__propertyMetadata = propertyMetadata;
		if (this.__propertyMetadata != null && this.__propertyMetadata.propertyChangedCallback() != null) {
			this.__hasCallback = true;
		} else {
			this.__hasCallback = false;
		}
	},
	propertyMetadata: function () {
		return this.__propertyMetadata;
	}
	,
	propertyType: function () {
		return this.__propertyType;
	}
	,
	name: function () {
		return this.__name;
	}
	,
	register: function (name, propertyType, ownerType, propertyMetadata) {
		return $.ig.DependencyPropertiesCollection.prototype.instance().register(name, propertyType, ownerType, propertyMetadata);
	}
	,
	queryRegisteredProperty: function (name, ownerType) {
		if (ownerType == null) {
			return null;
		}
		var dp = $.ig.DependencyPropertiesCollection.prototype.instance().getProperty(ownerType.typeName() + name);
		if (dp != null) {
			return dp;
		}
		return $.ig.DependencyProperty.prototype.queryRegisteredProperty(name, ownerType.baseType);
	}
	,
	$type: new $.ig.Type('DependencyProperty', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('APIFactory', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	createPoint: function (x, y) {
		return { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	createRect: function (left, top, width, height) {
		return new $.ig.Rect(0, left, top, width, height);
	}
	,
	createSize: function (width, height) {
		return new $.ig.Size(1, width, height);
	}
	,
	createColor: function (value) {
		var ret = new $.ig.Color();
		ret.colorString(value);
		return ret;
	}
	,
	$type: new $.ig.Type('APIFactory', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ArgumentException', 'Error', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
				case 3:
					this.init3.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Error.prototype.init.call(this, 0);
	},
	init1: function (initNumber, message) {
		$.ig.Error.prototype.init1.call(this, 1, message);
	},
	init2: function (initNumber, message, paramName) {
		$.ig.Error.prototype.init1.call(this, 1, message);
	},
	init3: function (initNumber, message, innerException) {
		$.ig.Error.prototype.init2.call(this, 2, message, innerException);
	},
	$type: new $.ig.Type('ArgumentException', $.ig.Error.prototype.$type)
}, true);

$.ig.util.defType('ArgumentNullException', 'Error', {
	init: function (initNumber, argumentName) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Error.prototype.init1.call(this, 1, argumentName + " cannot be null.");
	},
	init1: function (initNumber) {
		$.ig.Error.prototype.init.call(this, 0);
		throw new $.ig.NotImplementedException(0);
	},
	init2: function (initNumber, paramName, message) {
		$.ig.Error.prototype.init1.call(this, 1, message);
		throw new $.ig.NotImplementedException(0);
	},
	$type: new $.ig.Type('ArgumentNullException', $.ig.Error.prototype.$type)
}, true);

$.ig.util.defType('InvalidOperationException', 'Error', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Error.prototype.init1.call(this, 1, "Invalid operation");
	},
	init1: function (initNumber, errorMessage) {
		$.ig.Error.prototype.init1.call(this, 1, errorMessage);
	},
	init2: function (initNumber, errorMessage, innerException) {
		$.ig.Error.prototype.init2.call(this, 2, errorMessage, innerException);
		throw new $.ig.NotImplementedException(0);
	},
	$type: new $.ig.Type('InvalidOperationException', $.ig.Error.prototype.$type)
}, true);

$.ig.util.defType('NotImplementedException', 'Error', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Error.prototype.init1.call(this, 1, "not implemented");
	},
	init1: function (initNumber, message) {
		$.ig.Error.prototype.init1.call(this, 1, message);
		throw new $.ig.NotImplementedException(0);
	},
	$type: new $.ig.Type('NotImplementedException', $.ig.Error.prototype.$type)
}, true);

$.ig.util.defType('Random', 'Object', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
	},
	init1: function (initNumber, Seed) {
		$.ig.Object.prototype.init.call(this);
	},
	nextDouble: function () {
		return Math.random();
	}
	,
	next: function () {
		return this.next1(0x7FFFFFFF);
	}
	,
	next1: function (value) {
		return $.ig.truncate(Math.round(this.nextDouble() * (value - 1)));
	}
	,
	next2: function (low, high) {
		return low + $.ig.truncate(Math.round(this.nextDouble() * ((high - low) - 1)));
	}
	,
	$type: new $.ig.Type('Random', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Tuple$2', 'Object', {
	$t1: null,
	$t2: null,
	_item1: null,
	item1: function (value) {
		if (arguments.length === 1) {
			this._item1 = value;
			return value;
		} else {
			return this._item1;
		}
	}
	,
	_item2: null,
	item2: function (value) {
		if (arguments.length === 1) {
			this._item2 = value;
			return value;
		} else {
			return this._item2;
		}
	}
	,
	init: function ($t1, $t2, item1, item2) {
		this.$t1 = $t1;
		this.$t2 = $t2;
		this.$type = this.$type.specialize(this.$t1, this.$t2);
		$.ig.Object.prototype.init.call(this);
		this.item1(item1);
		this.item2(item2);
	},
	$type: new $.ig.Type('Tuple$2', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('UIElement', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	_renderTransform: null,
	renderTransform: function (value) {
		if (arguments.length === 1) {
			this._renderTransform = value;
			return value;
		} else {
			return this._renderTransform;
		}
	}
	,
	$type: new $.ig.Type('UIElement', $.ig.DependencyObject.prototype.$type)
}, true);

$.ig.util.defType('FrameworkElement', 'UIElement', {
	init: function () {
		this.__opacity = 1;
		$.ig.UIElement.prototype.init.call(this);
		this.__opacity = 1;
		this.canvasZIndex(0);
		this.__visibility = $.ig.Visibility.prototype.visible;
		this.width(NaN);
		this.height(NaN);
	},
	_name: null,
	name: function (value) {
		if (arguments.length === 1) {
			this._name = value;
			return value;
		} else {
			return this._name;
		}
	}
	,
	_actualWidth: 0,
	actualWidth: function (value) {
		if (arguments.length === 1) {
			this._actualWidth = value;
			return value;
		} else {
			return this._actualWidth;
		}
	}
	,
	_actualHeight: 0,
	actualHeight: function (value) {
		if (arguments.length === 1) {
			this._actualHeight = value;
			return value;
		} else {
			return this._actualHeight;
		}
	}
	,
	__visibility: 0,
	visibility: function (value) {
		if (arguments.length === 1) {
			this.__visibility = value;
			return value;
		} else {
			return this.__visibility;
		}
	}
	,
	_width: 0,
	width: function (value) {
		if (arguments.length === 1) {
			this._width = value;
			return value;
		} else {
			return this._width;
		}
	}
	,
	_height: 0,
	height: function (value) {
		if (arguments.length === 1) {
			this._height = value;
			return value;
		} else {
			return this._height;
		}
	}
	,
	_canvasTop: 0,
	canvasTop: function (value) {
		if (arguments.length === 1) {
			this._canvasTop = value;
			return value;
		} else {
			return this._canvasTop;
		}
	}
	,
	_canvasLeft: 0,
	canvasLeft: function (value) {
		if (arguments.length === 1) {
			this._canvasLeft = value;
			return value;
		} else {
			return this._canvasLeft;
		}
	}
	,
	_canvasZIndex: 0,
	canvasZIndex: function (value) {
		if (arguments.length === 1) {
			this._canvasZIndex = value;
			return value;
		} else {
			return this._canvasZIndex;
		}
	}
	,
	_parent: null,
	parent: function (value) {
		if (arguments.length === 1) {
			this._parent = value;
			return value;
		} else {
			return this._parent;
		}
	}
	,
	_dataContext: null,
	dataContext: function (value) {
		if (arguments.length === 1) {
			this._dataContext = value;
			return value;
		} else {
			return this._dataContext;
		}
	}
	,
	__opacity: 0,
	opacity: function (value) {
		if (arguments.length === 1) {
			if (this.__opacity != value) {
				this.__opacity = value;
				this.onOpacityChanged();
			}
			return value;
		} else {
			return this.__opacity;
		}
	}
	,
	onOpacityChanged: function () {
	}
	,
	_style: null,
	style: function (value) {
		if (arguments.length === 1) {
			this._style = value;
			return value;
		} else {
			return this._style;
		}
	}
	,
	$type: new $.ig.Type('FrameworkElement', $.ig.UIElement.prototype.$type)
}, true);

$.ig.util.defType('Control', 'FrameworkElement', {
	init: function () {
		$.ig.FrameworkElement.prototype.init.call(this);
	},
	_defaultStyleKey: null,
	defaultStyleKey: function (value) {
		if (arguments.length === 1) {
			this._defaultStyleKey = value;
			return value;
		} else {
			return this._defaultStyleKey;
		}
	}
	,
	_padding: null,
	padding: function (value) {
		if (arguments.length === 1) {
			this._padding = value;
			return value;
		} else {
			return this._padding;
		}
	}
	,
	onApplyTemplate: function () {
	}
	,
	_horizontalContentAlignment: 0,
	horizontalContentAlignment: function (value) {
		if (arguments.length === 1) {
			this._horizontalContentAlignment = value;
			return value;
		} else {
			return this._horizontalContentAlignment;
		}
	}
	,
	_verticalContentAlignment: 0,
	verticalContentAlignment: function (value) {
		if (arguments.length === 1) {
			this._verticalContentAlignment = value;
			return value;
		} else {
			return this._verticalContentAlignment;
		}
	}
	,
	$type: new $.ig.Type('Control', $.ig.FrameworkElement.prototype.$type)
}, true);

$.ig.util.defType('ContentControl', 'Control', {
	init: function () {
		$.ig.Control.prototype.init.call(this);
	},
	_content: null,
	content: function (value) {
		if (arguments.length === 1) {
			this._content = value;
			return value;
		} else {
			return this._content;
		}
	}
	,
	_contentTemplate: null,
	contentTemplate: function (value) {
		if (arguments.length === 1) {
			this._contentTemplate = value;
			return value;
		} else {
			return this._contentTemplate;
		}
	}
	,
	$type: new $.ig.Type('ContentControl', $.ig.Control.prototype.$type)
}, true);

$.ig.util.defType('TextBlock', 'FrameworkElement', {
	init: function () {
		$.ig.FrameworkElement.prototype.init.call(this);
	},
	__text: null,
	text: function (value) {
		if (arguments.length === 1) {
			if (this.__text != value) {
				this.__text = value;
				this.textWidthCache(-1);
			}
			return value;
		} else {
			return this.__text;
		}
	}
	,
	_fill: null,
	fill: function (value) {
		if (arguments.length === 1) {
			this._fill = value;
			return value;
		} else {
			return this._fill;
		}
	}
	,
	_textWidthCache: 0,
	textWidthCache: function (value) {
		if (arguments.length === 1) {
			this._textWidthCache = value;
			return value;
		} else {
			return this._textWidthCache;
		}
	}
	,
	$type: new $.ig.Type('TextBlock', $.ig.FrameworkElement.prototype.$type)
}, true);

$.ig.util.defType('DataTemplate', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_render: null,
	render: function (value) {
		if (arguments.length === 1) {
			this._render = value;
			return value;
		} else {
			return this._render;
		}
	}
	,
	_measure: null,
	measure: function (value) {
		if (arguments.length === 1) {
			this._measure = value;
			return value;
		} else {
			return this._measure;
		}
	}
	,
	_passStarting: null,
	passStarting: function (value) {
		if (arguments.length === 1) {
			this._passStarting = value;
			return value;
		} else {
			return this._passStarting;
		}
	}
	,
	_passCompleted: null,
	passCompleted: function (value) {
		if (arguments.length === 1) {
			this._passCompleted = value;
			return value;
		} else {
			return this._passCompleted;
		}
	}
	,
	$type: new $.ig.Type('DataTemplate', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataTemplatePassInfo', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	renderContext: null,
	context: null,
	viewportTop: 0,
	viewportLeft: 0,
	viewportWidth: 0,
	viewportHeight: 0,
	isHitTestRender: false,
	passID: null,
	$type: new $.ig.Type('DataTemplatePassInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataTemplateMeasureInfo', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	renderContext: null,
	context: null,
	width: 0,
	height: 0,
	isConstant: false,
	data: null,
	passInfo: null,
	renderOffsetX: 0,
	renderOffsetY: 0,
	$type: new $.ig.Type('DataTemplateMeasureInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataTemplateRenderInfo', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	renderContext: null,
	context: null,
	xPosition: 0,
	yPosition: 0,
	availableWidth: 0,
	availableHeight: 0,
	data: null,
	isHitTestRender: false,
	passInfo: null,
	renderOffsetX: 0,
	renderOffsetY: 0,
	$type: new $.ig.Type('DataTemplateRenderInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Binding', 'Object', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		this.__satisfied = false;
		$.ig.Object.prototype.init.call(this);
	},
	init1: function (initNumber, path) {
		this.__satisfied = false;
		$.ig.Object.prototype.init.call(this);
		this.__path = new $.ig.PropertyPath(path);
	},
	__source: null,
	source: function (value) {
		if (arguments.length === 1) {
			this.__source = value;
			return value;
		} else {
			return this.__source;
		}
	}
	,
	__path: null,
	path: function (value) {
		if (arguments.length === 1) {
			this.__path = value;
			return value;
		} else {
			return this.__path;
		}
	}
	,
	__satisfied: false,
	satisfied: function (value) {
		if (arguments.length === 1) {
			this.__satisfied = value;
			return value;
		} else {
			return this.__satisfied;
		}
	}
	,
	$type: new $.ig.Type('Binding', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DependencyPropertiesCollection', 'Object', {
	__dependencyProperties: null,
	instance: function () {
		if ($.ig.DependencyPropertiesCollection.prototype.__instance == null) {
			$.ig.DependencyPropertiesCollection.prototype.__instance = new $.ig.DependencyPropertiesCollection();
		}
		return $.ig.DependencyPropertiesCollection.prototype.__instance;
	}
	,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if (this.__dependencyProperties == null) {
			this.__dependencyProperties = new $.ig.Dictionary(0);
		}
	},
	getProperty: function (key) {
		return this.__dependencyProperties.item(key);
	}
	,
	register: function (name, propertyType, ownerType, propertyMetadata) {
		var dependencyProperty = new $.ig.DependencyProperty(name, propertyType, propertyMetadata);
		this.__dependencyProperties.item(ownerType.typeName() + name, dependencyProperty);
		return dependencyProperty;
	}
	,
	$type: new $.ig.Type('DependencyPropertiesCollection', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DependencyPropertyChangedEventArgs', 'Object', {
	__newValue: null,
	__oldValue: null,
	init: function (dp, newValue, oldValue) {
		$.ig.Object.prototype.init.call(this);
		this.__newValue = newValue;
		this.__oldValue = oldValue;
		this.__property = dp;
	},
	__property: null,
	property: function (value) {
		if (arguments.length === 1) {
			this.__property = value;
			return value;
		} else {
			return this.__property;
		}
	}
	,
	newValue: function () {
		return this.__newValue;
	}
	,
	oldValue: function () {
		return this.__oldValue;
	}
	,
	$type: new $.ig.Type('DependencyPropertyChangedEventArgs', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Brush', 'Object', {
	init: function () {
		this.__fill = null;
		this.__cachedFill = null;
		this.__cachedColor = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
	},
	_isGradient: false,
	_isRadialGradient: false,
	_isImageFill: false,
	__fill: null,
	fill: function (value) {
		if (arguments.length === 1) {
			this.__fill = value;
			return value;
		} else {
			return this.__fill;
		}
	}
	,
	__cachedFill: null,
	__cachedColor: null,
	color: function (value) {
		if (arguments.length === 1) {
			this.__cachedColor = value;
			this.__cachedFill = this.__cachedColor.colorString();
			this.__fill = this.__cachedFill;
			return value;
		} else {
			if (this.__fill == null && (this._isGradient || this._isRadialGradient)) {
				this.__fill = this.getPrimaryColor();
			}
			if (this.__fill == this.__cachedFill) {
				return this.__cachedColor;
			}
			var color = new $.ig.Color();
			if (this.__fill != null) {
				color.colorString(this.__fill);
				this.__cachedColor = color;
				this.__cachedFill = this.__fill;
			}
			return color;
		}
	}
	,
	getPrimaryColor: function () {
		return null;
	}
	,
	equals: function (obj) {
		if (obj == null) {
			return false;
		}
		var other = obj;
		return this.__fill.equals(other.__fill) && this.color().equals(other.color()) && this._isGradient == other._isGradient && this._isImageFill == other._isImageFill && this._isRadialGradient == other._isRadialGradient;
	}
	,
	create: function (val_) {
		var b_ = new $.ig.Brush();
		if (!val_) {
			return null;
			}
			
			if (typeof val_ == 'string') {
				if ($.ig.CssGradientUtil.prototype.isGradient(val_)) {
                    b_ = $.ig.CssGradientUtil.prototype.brushFromGradientString(val_);
                } else {
                    b_ = new $.ig.Brush();
                    b_.fill(val_);
                }
			} else if (val_.type == 'linearGradient') {
				b_ = new $.ig.LinearGradientBrush();
				if (val_.startPoint && val_.endPoint) {
					b_._useCustomDirection = true;
					b_._startX = val_.startPoint.x;
					b_._startY = val_.startPoint.y;
					b_._endX = val_.endPoint.x;
					b_._endY = val_.endPoint.y;
				}
				
				if (val_.colorStops) {
					stops = [];
					for (var i = 0; i < val_.colorStops.length; i++) {
						colorStop = new $.ig.GradientStop();
						colorStop._offset = val_.colorStops[i].offset;
						colorStop.__fill = val_.colorStops[i].color;
						stops.push(colorStop);
					}
					b_._gradientStops = stops;
				}};
		return b_;
	}
	,
	$type: new $.ig.Type('Brush', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LinearGradientBrush', 'Brush', {
	init: function () {
		$.ig.Brush.prototype.init.call(this);
		this._useCustomDirection = false;
		this._startX = 0;
		this._startY = 0;
		this._endX = 0;
		this._endY = 1;
		this._isAbsolute = false;
		this._gradientStops = new Array(0);
		this._isGradient = true;
	},
	_useCustomDirection: false,
	_startX: 0,
	_startY: 0,
	_endX: 0,
	_endY: 0,
	_isAbsolute: false,
	_gradientStops: null,
	clone: function () {
		var newBrush = new $.ig.LinearGradientBrush();
		newBrush._startX = this._startX;
		newBrush._startY = this._startY;
		newBrush._endX = this._endX;
		newBrush._endY = this._endY;
		newBrush._useCustomDirection = this._useCustomDirection;
		newBrush._isAbsolute = this._isAbsolute;
		if (this._gradientStops != null) {
			newBrush._gradientStops = new Array(this._gradientStops.length);
			for (var i = 0; i < this._gradientStops.length; i++) {
				newBrush._gradientStops[i] = this._gradientStops[i].clone();
			}
		}
		return newBrush;
	}
	,
	equals: function (obj) {
		if (obj == null) {
			return false;
		}
		var other = obj;
		var retval = $.ig.Brush.prototype.equals.call(this, obj) && this._startX == other._startX && this._startY == other._startY && this._endX == other._endX && this._endY == other._endY && this._isAbsolute == other._isAbsolute && this._useCustomDirection == other._useCustomDirection;
		if (retval == false) {
			return false;
		}
		if (this._gradientStops.length != other._gradientStops.length) {
			return false;
		}
		for (var i = 0, length = this._gradientStops.length; i < length; i++) {
			if (!this._gradientStops[i].equals(other._gradientStops[i])) {
				return false;
			}
		}
		return true;
	}
	,
	getPrimaryColor: function () {
		if (this._gradientStops != null && this._gradientStops.length > 0) {
			return this._gradientStops[0].color().colorString();
		}
		return $.ig.Brush.prototype.getPrimaryColor.call(this);
	}
	,
	$type: new $.ig.Type('LinearGradientBrush', $.ig.Brush.prototype.$type)
}, true);

$.ig.util.defType('GradientStop', 'Object', {
	init: function () {
		this.__fill = null;
		this.__cachedFill = null;
		this.__cachedColor = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
		this._offset = 0;
	},
	_offset: 0,
	clone: function () {
		var newStop = new $.ig.GradientStop();
		newStop._offset = this._offset;
		newStop.__fill = this.__fill;
		return newStop;
	}
	,
	__fill: null,
	fill: function (value) {
		if (arguments.length === 1) {
			this.__fill = value;
			return value;
		} else {
			return this.__fill;
		}
	}
	,
	__cachedFill: null,
	__cachedColor: null,
	color: function (value) {
		if (arguments.length === 1) {
			this.__cachedColor = value;
			this.__cachedFill = this.__cachedColor.colorString();
			this.__fill = this.__cachedFill;
			return value;
		} else {
			if (this.__fill == this.__cachedFill) {
				return this.__cachedColor;
			}
			var color = new $.ig.Color();
			if (this.__fill != null) {
				color.colorString(this.__fill);
				this.__cachedColor = color;
				this.__cachedFill = this.__fill;
			}
			return color;
		}
	}
	,
	equals: function (obj) {
		if (obj == null) {
			return false;
		}
		var other = obj;
		return this._offset == other._offset && this.color().equals(other.color()) && this.__fill.equals(other.__fill);
	}
	,
	$type: new $.ig.Type('GradientStop', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Color', 'ValueType', {
	init: function () {
		$.ig.ValueType.prototype.init.call(this);
	},
	__a: 0,
	a: function (value) {
		if (arguments.length === 1) {
			this.__a = $.ig.truncate(Math.round(value));
			this.__stringDirty = true;
			return value;
		} else {
			return this.__a;
		}
	}
	,
	__r: 0,
	r: function (value) {
		if (arguments.length === 1) {
			this.__r = $.ig.truncate(Math.round(value));
			this.__stringDirty = true;
			return value;
		} else {
			return this.__r;
		}
	}
	,
	__g: 0,
	g: function (value) {
		if (arguments.length === 1) {
			this.__g = $.ig.truncate(Math.round(value));
			this.__stringDirty = true;
			return value;
		} else {
			return this.__g;
		}
	}
	,
	__b: 0,
	b: function (value) {
		if (arguments.length === 1) {
			this.__b = $.ig.truncate(Math.round(value));
			this.__stringDirty = true;
			return value;
		} else {
			return this.__b;
		}
	}
	,
	__colorString: null,
	colorString: function (value) {
		if (arguments.length === 1) {
			this.__colorString = value;
			this.updateColors();
			return value;
		} else {
			if (this.__stringDirty || this.__colorString == null) {
				this.__stringDirty = false;
				this.updateColorString();
			}
			return this.__colorString;
		}
	}
	,
	__stringDirty: false,
	create: function (value) {
		if ($.ig.util.cast($.ig.Color.prototype.$type, value) !== null) {
			return value;
		}
		var ret = new $.ig.Color();
		if (typeof value === 'string') {
			ret.colorString(value);
		} else if (value != null) {
			throw new $.ig.ArgumentException(1, "Unknown color type");
		}
		return ret;
	}
	,
	updateColorString: function () {
		this.__colorString = "rgba(" + this.__r + "," + this.__g + "," + this.__b + "," + this.__a / 255 + ")";
	}
	,
	updateColors: function () {
		var obj_ = $.ig.util.stringToColor(this.__colorString);
		this.__a = typeof obj_.a != 'undefined' ? Math.round(obj_.a) : 0;
		this.__r = typeof obj_.r != 'undefined' ? Math.round(obj_.r) : 0;
		this.__g = typeof obj_.g != 'undefined' ? Math.round(obj_.g) : 0;
		this.__b = typeof obj_.b != 'undefined' ? Math.round(obj_.b) : 0;
	}
	,
	fromArgb: function (a_, r_, g_, b_) {
		var c = new $.ig.Color();
		c.__a = a_ | 0;
		c.__r = r_ | 0;
		c.__g = g_ | 0;
		c.__b = b_ | 0;
		c.__stringDirty = true;
		return c;
	}
	,
	equals: function (obj) {
		if (($.ig.util.cast($.ig.Color.prototype.$type, obj) !== null) == false) {
			return false;
		}
		var other = obj;
		return this.__a == other.__a && this.__r == other.__r && this.__g == other.__g && this.__b == other.__b;
	}
	,
	getHashCode: function () {
		return (this.__a << 24) | (this.__r << 16) | (this.__g << 8) | this.__b;
	}
	,
	l_op_Inequality: function (left, right) {
		return !($.ig.Color.prototype.l_op_Equality(left, right));
	}
	,
	l_op_Inequality_Lifted: function (left, right) {
		if (!left.hasValue()) {
			return right.hasValue();
		} else if (!right.hasValue()) {
			return true;
		}
		return $.ig.Color.prototype.l_op_Inequality(left.value(), right.value());
	}
	,
	l_op_Equality: function (left, right) {
		return left.__a == right.__a && left.__r == right.__r && left.__g == right.__g && left.__b == right.__b;
	}
	,
	l_op_Equality_Lifted: function (left, right) {
		if (!left.hasValue()) {
			return !right.hasValue();
		} else if (!right.hasValue()) {
			return false;
		}
		return $.ig.Color.prototype.l_op_Equality(left.value(), right.value());
	}
	,
	$type: new $.ig.Type('Color', $.ig.ValueType.prototype.$type)
}, true);

$.ig.util.defType('DoubleCollection', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, Number, 0);
	},
	$type: new $.ig.Type('DoubleCollection', $.ig.List$1.prototype.$type.specialize(Number))
}, true);

$.ig.util.defType('Geometry', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	type: function () {
	}
	,
	$type: new $.ig.Type('Geometry', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GeometryCollection', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.Geometry.prototype.$type, 0);
	},
	$type: new $.ig.Type('GeometryCollection', $.ig.List$1.prototype.$type.specialize($.ig.Geometry.prototype.$type))
}, true);

$.ig.util.defType('GeometryGroup', 'Geometry', {
	init: function () {
		$.ig.Geometry.prototype.init.call(this);
		this.children(new $.ig.GeometryCollection());
	},
	_children: null,
	children: function (value) {
		if (arguments.length === 1) {
			this._children = value;
			return value;
		} else {
			return this._children;
		}
	}
	,
	type: function () {
		return $.ig.GeometryType.prototype.group;
	}
	,
	_fillRule: 0,
	fillRule: function (value) {
		if (arguments.length === 1) {
			this._fillRule = value;
			return value;
		} else {
			return this._fillRule;
		}
	}
	,
	$type: new $.ig.Type('GeometryGroup', $.ig.Geometry.prototype.$type)
}, true);

$.ig.util.defType('LineGeometry', 'Geometry', {
	init: function () {
		$.ig.Geometry.prototype.init.call(this);
	},
	_startPoint: null,
	startPoint: function (value) {
		if (arguments.length === 1) {
			this._startPoint = value;
			return value;
		} else {
			return this._startPoint;
		}
	}
	,
	_endPoint: null,
	endPoint: function (value) {
		if (arguments.length === 1) {
			this._endPoint = value;
			return value;
		} else {
			return this._endPoint;
		}
	}
	,
	type: function () {
		return $.ig.GeometryType.prototype.line;
	}
	,
	$type: new $.ig.Type('LineGeometry', $.ig.Geometry.prototype.$type)
}, true);

$.ig.util.defType('RectangleGeometry', 'Geometry', {
	init: function () {
		$.ig.Geometry.prototype.init.call(this);
	},
	_rect: null,
	rect: function (value) {
		if (arguments.length === 1) {
			this._rect = value;
			return value;
		} else {
			return this._rect;
		}
	}
	,
	_radiusX: 0,
	radiusX: function (value) {
		if (arguments.length === 1) {
			this._radiusX = value;
			return value;
		} else {
			return this._radiusX;
		}
	}
	,
	_radiusY: 0,
	radiusY: function (value) {
		if (arguments.length === 1) {
			this._radiusY = value;
			return value;
		} else {
			return this._radiusY;
		}
	}
	,
	type: function () {
		return $.ig.GeometryType.prototype.rectangle;
	}
	,
	$type: new $.ig.Type('RectangleGeometry', $.ig.Geometry.prototype.$type)
}, true);

$.ig.util.defType('EllipseGeometry', 'Geometry', {
	init: function () {
		$.ig.Geometry.prototype.init.call(this);
	},
	_center: null,
	center: function (value) {
		if (arguments.length === 1) {
			this._center = value;
			return value;
		} else {
			return this._center;
		}
	}
	,
	_radiusX: 0,
	radiusX: function (value) {
		if (arguments.length === 1) {
			this._radiusX = value;
			return value;
		} else {
			return this._radiusX;
		}
	}
	,
	_radiusY: 0,
	radiusY: function (value) {
		if (arguments.length === 1) {
			this._radiusY = value;
			return value;
		} else {
			return this._radiusY;
		}
	}
	,
	type: function () {
		return $.ig.GeometryType.prototype.ellipse;
	}
	,
	$type: new $.ig.Type('EllipseGeometry', $.ig.Geometry.prototype.$type)
}, true);

$.ig.util.defType('PathGeometry', 'Geometry', {
	init: function () {
		$.ig.Geometry.prototype.init.call(this);
		this.figures(new $.ig.PathFigureCollection());
	},
	_figures: null,
	figures: function (value) {
		if (arguments.length === 1) {
			this._figures = value;
			return value;
		} else {
			return this._figures;
		}
	}
	,
	type: function () {
		return $.ig.GeometryType.prototype.path;
	}
	,
	$type: new $.ig.Type('PathGeometry', $.ig.Geometry.prototype.$type)
}, true);

$.ig.util.defType('PathFigure', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__segments = new $.ig.PathSegmentCollection();
		this.__isClosed = false;
		this.__isFilled = true;
	},
	__segments: null,
	segments: function (value) {
		if (arguments.length === 1) {
			this.__segments = value;
			return value;
		} else {
			return this.__segments;
		}
	}
	,
	__startPoint: null,
	startPoint: function (value) {
		if (arguments.length === 1) {
			this.__startPoint = value;
			return value;
		} else {
			return this.__startPoint;
		}
	}
	,
	__isFilled: false,
	isFilled: function (value) {
		if (arguments.length === 1) {
			this.__isFilled = value;
			return value;
		} else {
			return this.__isFilled;
		}
	}
	,
	__isClosed: false,
	isClosed: function (value) {
		if (arguments.length === 1) {
			this.__isClosed = value;
			return value;
		} else {
			return this.__isClosed;
		}
	}
	,
	$type: new $.ig.Type('PathFigure', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PathFigureCollection', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.PathFigure.prototype.$type, 0);
	},
	$type: new $.ig.Type('PathFigureCollection', $.ig.List$1.prototype.$type.specialize($.ig.PathFigure.prototype.$type))
}, true);

$.ig.util.defType('PathSegment', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	type: function () {
	}
	,
	$type: new $.ig.Type('PathSegment', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PathSegmentCollection', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.PathSegment.prototype.$type, 0);
	},
	$type: new $.ig.Type('PathSegmentCollection', $.ig.List$1.prototype.$type.specialize($.ig.PathSegment.prototype.$type))
}, true);

$.ig.util.defType('LineSegment', 'PathSegment', {
	__point: null,
	point: function (value) {
		if (arguments.length === 1) {
			this.__point = value;
			return value;
		} else {
			return this.__point;
		}
	}
	,
	init: function (initNumber, point) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.PathSegment.prototype.init.call(this);
		this.point(point);
	},
	init1: function (initNumber) {
		$.ig.PathSegment.prototype.init.call(this);
	},
	type: function () {
		return $.ig.PathSegmentType.prototype.line;
	}
	,
	$type: new $.ig.Type('LineSegment', $.ig.PathSegment.prototype.$type)
}, true);

$.ig.util.defType('BezierSegment', 'PathSegment', {
	__point1: null,
	point1: function (value) {
		if (arguments.length === 1) {
			this.__point1 = value;
			return value;
		} else {
			return this.__point1;
		}
	}
	,
	__point2: null,
	point2: function (value) {
		if (arguments.length === 1) {
			this.__point2 = value;
			return value;
		} else {
			return this.__point2;
		}
	}
	,
	__point3: null,
	point3: function (value) {
		if (arguments.length === 1) {
			this.__point3 = value;
			return value;
		} else {
			return this.__point3;
		}
	}
	,
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.PathSegment.prototype.init.call(this);
		this.point1(this.point2(this.point3({ __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName })));
	},
	init1: function (initNumber, cp1, cp2, p) {
		$.ig.PathSegment.prototype.init.call(this);
		this.point1(cp1);
		this.point2(cp2);
		this.point3(p);
	},
	type: function () {
		return $.ig.PathSegmentType.prototype.bezier;
	}
	,
	$type: new $.ig.Type('BezierSegment', $.ig.PathSegment.prototype.$type)
}, true);

$.ig.util.defType('PolyBezierSegment', 'PathSegment', {
	init: function () {
		$.ig.PathSegment.prototype.init.call(this);
		this.points(new $.ig.PointCollection(0));
	},
	_points: null,
	points: function (value) {
		if (arguments.length === 1) {
			this._points = value;
			return value;
		} else {
			return this._points;
		}
	}
	,
	type: function () {
		return $.ig.PathSegmentType.prototype.polyBezier;
	}
	,
	$type: new $.ig.Type('PolyBezierSegment', $.ig.PathSegment.prototype.$type)
}, true);

$.ig.util.defType('PolyLineSegment', 'PathSegment', {
	init: function () {
		$.ig.PathSegment.prototype.init.call(this);
		this.__points = new $.ig.PointCollection(0);
	},
	__points: null,
	points: function (value) {
		if (arguments.length === 1) {
			this.__points = value;
			return value;
		} else {
			return this.__points;
		}
	}
	,
	type: function () {
		return $.ig.PathSegmentType.prototype.polyLine;
	}
	,
	$type: new $.ig.Type('PolyLineSegment', $.ig.PathSegment.prototype.$type)
}, true);

$.ig.util.defType('ArcSegment', 'PathSegment', {
	init: function () {
		this._size = new $.ig.Size();
		$.ig.PathSegment.prototype.init.call(this);
		this.isLargeArc(false);
		this.sweepDirection($.ig.SweepDirection.prototype.counterclockwise);
	},
	_point: null,
	point: function (value) {
		if (arguments.length === 1) {
			this._point = value;
			return value;
		} else {
			return this._point;
		}
	}
	,
	_isLargeArc: false,
	isLargeArc: function (value) {
		if (arguments.length === 1) {
			this._isLargeArc = value;
			return value;
		} else {
			return this._isLargeArc;
		}
	}
	,
	_sweepDirection: 0,
	sweepDirection: function (value) {
		if (arguments.length === 1) {
			this._sweepDirection = value;
			return value;
		} else {
			return this._sweepDirection;
		}
	}
	,
	_size: null,
	size: function (value) {
		if (arguments.length === 1) {
			this._size = value;
			return value;
		} else {
			return this._size;
		}
	}
	,
	_rotationAngle: 0,
	rotationAngle: function (value) {
		if (arguments.length === 1) {
			this._rotationAngle = value;
			return value;
		} else {
			return this._rotationAngle;
		}
	}
	,
	type: function () {
		return $.ig.PathSegmentType.prototype.arc;
	}
	,
	$type: new $.ig.Type('ArcSegment', $.ig.PathSegment.prototype.$type)
}, true);

$.ig.util.defType('Transform', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	$type: new $.ig.Type('Transform', $.ig.DependencyObject.prototype.$type)
}, true);

$.ig.util.defType('RotateTransform', 'Transform', {
	init: function () {
		$.ig.Transform.prototype.init.call(this);
	},
	_angle: 0,
	angle: function (value) {
		if (arguments.length === 1) {
			this._angle = value;
			return value;
		} else {
			return this._angle;
		}
	}
	,
	_centerX: 0,
	centerX: function (value) {
		if (arguments.length === 1) {
			this._centerX = value;
			return value;
		} else {
			return this._centerX;
		}
	}
	,
	_centerY: 0,
	centerY: function (value) {
		if (arguments.length === 1) {
			this._centerY = value;
			return value;
		} else {
			return this._centerY;
		}
	}
	,
	$type: new $.ig.Type('RotateTransform', $.ig.Transform.prototype.$type)
}, true);

$.ig.util.defType('TranslateTransform', 'Transform', {
	init: function () {
		$.ig.Transform.prototype.init.call(this);
	},
	_x: 0,
	x: function (value) {
		if (arguments.length === 1) {
			this._x = value;
			return value;
		} else {
			return this._x;
		}
	}
	,
	_y: 0,
	y: function (value) {
		if (arguments.length === 1) {
			this._y = value;
			return value;
		} else {
			return this._y;
		}
	}
	,
	$type: new $.ig.Type('TranslateTransform', $.ig.Transform.prototype.$type)
}, true);

$.ig.util.defType('ScaleTransform', 'Transform', {
	init: function () {
		$.ig.Transform.prototype.init.call(this);
	},
	_scaleX: 0,
	scaleX: function (value) {
		if (arguments.length === 1) {
			this._scaleX = value;
			return value;
		} else {
			return this._scaleX;
		}
	}
	,
	_scaleY: 0,
	scaleY: function (value) {
		if (arguments.length === 1) {
			this._scaleY = value;
			return value;
		} else {
			return this._scaleY;
		}
	}
	,
	_centerX: 0,
	centerX: function (value) {
		if (arguments.length === 1) {
			this._centerX = value;
			return value;
		} else {
			return this._centerX;
		}
	}
	,
	_centerY: 0,
	centerY: function (value) {
		if (arguments.length === 1) {
			this._centerY = value;
			return value;
		} else {
			return this._centerY;
		}
	}
	,
	$type: new $.ig.Type('ScaleTransform', $.ig.Transform.prototype.$type)
}, true);

$.ig.util.defType('TransformGroup', 'Transform', {
	_children: null,
	children: function (value) {
		if (arguments.length === 1) {
			this._children = value;
			return value;
		} else {
			return this._children;
		}
	}
	,
	init: function () {
		$.ig.Transform.prototype.init.call(this);
		this.children(new $.ig.TransformCollection());
	},
	$type: new $.ig.Type('TransformGroup', $.ig.Transform.prototype.$type)
}, true);

$.ig.util.defType('TransformCollection', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.Transform.prototype.$type, 0);
	},
	$type: new $.ig.Type('TransformCollection', $.ig.List$1.prototype.$type.specialize($.ig.Transform.prototype.$type))
}, true);

$.ig.util.defType('Thickness', 'Object', {
	init: function (initNumber, uniformLength) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
		this.bottom(this.left(this.right(this.top(uniformLength))));
	},
	init1: function (initNumber, left, top, right, bottom) {
		$.ig.Object.prototype.init.call(this);
		this.left(left);
		this.top(top);
		this.right(right);
		this.bottom(bottom);
	},
	_bottom: 0,
	bottom: function (value) {
		if (arguments.length === 1) {
			this._bottom = value;
			return value;
		} else {
			return this._bottom;
		}
	}
	,
	_left: 0,
	left: function (value) {
		if (arguments.length === 1) {
			this._left = value;
			return value;
		} else {
			return this._left;
		}
	}
	,
	_right: 0,
	right: function (value) {
		if (arguments.length === 1) {
			this._right = value;
			return value;
		} else {
			return this._right;
		}
	}
	,
	_top: 0,
	top: function (value) {
		if (arguments.length === 1) {
			this._top = value;
			return value;
		} else {
			return this._top;
		}
	}
	,
	equals: function (thickness) {
		return this.bottom() == thickness.bottom() && this.top() == thickness.top() && this.left() == thickness.left() && this.right() == thickness.right();
	}
	,
	toString: function () {
		var marginsInfo = this.left().toString() + "," + this.top().toString() + "," + this.right().toString() + "," + this.bottom().toString();
		return marginsInfo;
	}
	,
	$type: new $.ig.Type('Thickness', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Point', 'Object', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
		this.__x = 0;
		this.__y = 0;
	},
	x: function (value) {
		if (arguments.length === 1) {
			this.__x = value;
			return value;
		} else {
			return this.__x;
		}
	}
	,
	y: function (value) {
		if (arguments.length === 1) {
			this.__y = value;
			return value;
		} else {
			return this.__y;
		}
	}
	,
	__x: 0,
	__y: 0,
	init1: function (initNumber, x, y) {
		$.ig.Object.prototype.init.call(this);
		this.__x = x;
		this.__y = y;
	},
	equals: function (obj) {
		if (obj == null) {
			return $.ig.Object.prototype.equals.call(this, obj);
		}
		var other = obj;
		return other.__x == this.__x && other.__y == this.__y;
	}
	,
	l_op_Equality: function (a, b) {
		if (a == null) {
			return b == null;
		} else if (b == null) {
			return false;
		}
		return a.__x == b.__x && a.__y == b.__y;
	}
	,
	l_op_Inequality: function (a, b) {
		return !($.ig.Point.prototype.l_op_Equality(a, b));
	}
	,
	$type: new $.ig.Type('Point', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PointCollection', 'List$1', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.List$1.prototype.init.call(this, $.ig.Point.prototype.$type, 0);
	},
	init1: function (initNumber, source_) {
		$.ig.List$1.prototype.init.call(this, $.ig.Point.prototype.$type, 0);
		this.__inner = source_.__inner;
	},
	$type: new $.ig.Type('PointCollection', $.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type))
}, true);

$.ig.util.defType('PropertyPath', 'Object', {
	__path: null,
	path: function (value) {
		if (arguments.length === 1) {
			this.__path = value;
			return value;
		} else {
			return this.__path;
		}
	}
	,
	init: function (path) {
		$.ig.Object.prototype.init.call(this);
		this.path(path);
	},
	$type: new $.ig.Type('PropertyPath', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Rect', 'Object', {
	init: function (initNumber, left, top, width, height) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
				case 2:
					this.init2.apply(this, arguments);
					break;
				case 3:
					this.init3.apply(this, arguments);
					break;
				case 4:
					this.init4.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
		this.top(top);
		this.left(left);
		this.width(width);
		this.height(height);
	},
	init1: function (initNumber, left, top, size) {
		$.ig.Object.prototype.init.call(this);
		this.top(top);
		this.left(left);
		this.width(size.width());
		this.height(size.height());
	},
	init2: function (initNumber, point1, point2) {
		$.ig.Object.prototype.init.call(this);
		this.top(Math.min(point1.__y, point2.__y));
		this.left(Math.min(point1.__x, point2.__x));
		this.width(Math.max(Math.max(point1.__x, point2.__x) - this.left(), 0));
		this.height(Math.max(Math.max(point1.__y, point2.__y) - this.top(), 0));
	},
	init3: function (initNumber, point1, size) {
		$.ig.Object.prototype.init.call(this);
		this.top(point1.__y);
		this.left(point1.__x);
		this.width(size.width());
		this.height(size.height());
	},
	init4: function (initNumber) {
		$.ig.Object.prototype.init.call(this);
		this.top(0);
		this.left(0);
		this.width(0);
		this.height(0);
	},
	__x: 0,
	x: function (value) {
		if (arguments.length === 1) {
			this.__x = value;
			this.__left = this.__x;
			this.__right = this.__left + this.__width;
			return value;
		} else {
			return this.__x;
		}
	}
	,
	__y: 0,
	y: function (value) {
		if (arguments.length === 1) {
			this.__y = value;
			this.__top = this.__y;
			this.__bottom = this.__top + this.__height;
			return value;
		} else {
			return this.__y;
		}
	}
	,
	__width: 0,
	width: function (value) {
		if (arguments.length === 1) {
			this.__width = value;
			this.__right = this.__left + this.__width;
			return value;
		} else {
			return this.__width;
		}
	}
	,
	__height: 0,
	height: function (value) {
		if (arguments.length === 1) {
			this.__height = value;
			this.__bottom = this.__top + this.__height;
			return value;
		} else {
			return this.__height;
		}
	}
	,
	__top: 0,
	top: function (value) {
		if (arguments.length === 1) {
			this.__top = value;
			this.y(this.__top);
			return value;
		} else {
			return this.__top;
		}
	}
	,
	__left: 0,
	left: function (value) {
		if (arguments.length === 1) {
			this.__left = value;
			this.x(this.__left);
			return value;
		} else {
			return this.__left;
		}
	}
	,
	__right: 0,
	right: function (value) {
		if (arguments.length === 1) {
			this.__right = value;
			this.__width = this.__right - this.__left;
			return value;
		} else {
			return this.__right;
		}
	}
	,
	__bottom: 0,
	bottom: function (value) {
		if (arguments.length === 1) {
			this.__bottom = value;
			this.__height = this.__bottom - this.__top;
			return value;
		} else {
			return this.__bottom;
		}
	}
	,
	isEmpty: function () {
		return this.__width < 0;
	}
	,
	empty: function () {
		return new $.ig.Rect(0, Number.POSITIVE_INFINITY, Number.POSITIVE_INFINITY, Number.NEGATIVE_INFINITY, Number.NEGATIVE_INFINITY);
	}
	,
	equals1: function (value) {
		if ($.ig.Rect.prototype.l_op_Equality(value, null)) {
			return false;
		}
		if (value.x() == this.x() && value.y() == this.y() && value.width() == this.width() && value.height() == this.height()) {
			return true;
		}
		return false;
	}
	,
	containsInternal: function (x, y) {
		return x >= this.__x && x - this.__width <= this.__x && y >= this.__y && y - this.__height <= this.__y;
	}
	,
	containsLocation: function (x, y) {
		return !this.isEmpty() && this.containsInternal(x, y);
	}
	,
	containsPoint: function (point) {
		return this.containsLocation(point.__x, point.__y);
	}
	,
	containsRect: function (rect) {
		return !this.isEmpty() && !rect.isEmpty() && (this.__x <= rect.__x && this.__y <= rect.__y && this.__x + this.__width >= rect.__x + rect.__width) && this.__y + this.__height >= rect.__y + rect.__height;
	}
	,
	inflate: function (width, height) {
		this.x(this.x() - width);
		this.y(this.y() - height);
		this.width(this.width() + (width * 2));
		this.height(this.height() + (height * 2));
		if (this.__width < 0 || this.__height < 0) {
			this.makeEmpty();
		}
	}
	,
	makeEmpty: function () {
		this.top(Number.POSITIVE_INFINITY);
		this.left(Number.POSITIVE_INFINITY);
		this.width(Number.NEGATIVE_INFINITY);
		this.height(Number.NEGATIVE_INFINITY);
	}
	,
	intersectsWith: function (rect) {
		return !(rect.left() > this.right() || rect.right() < this.left() || rect.top() > this.bottom() || rect.bottom() < this.top());
	}
	,
	intersect: function (other) {
		if (!this.intersectsWith(other)) {
			this.makeEmpty();
		} else {
			var maxX = Math.max(this.x(), other.x());
			var maxY = Math.max(this.y(), other.y());
			var newWidth = Math.min(this.x() + this.width(), other.x() + other.width()) - maxX;
			var newHeight = Math.min(this.y() + this.height(), other.y() + other.height()) - maxY;
			if (newWidth < 0) {
				newWidth = 0;
			}
			if (newHeight < 0) {
				newHeight = 0;
			}
			this.__width = newWidth;
			this.__height = newHeight;
			this.__x = maxX;
			this.__y = maxY;
			this.__left = this.__x;
			this.__top = this.__y;
			this.__right = this.__left + this.__width;
			this.__bottom = this.__top + this.__height;
		}
	}
	,
	union: function (other) {
		if (this.isEmpty()) {
			this.__x = other.x();
			this.__y = other.y();
			this.__width = other.width();
			this.__height = other.height();
			this.__left = this.__x;
			this.__top = this.__y;
			this.__right = this.__left + this.__width;
			this.__bottom = this.__top + this.__height;
			return;
		}
		if (!other.isEmpty()) {
			var minX = Math.min(this.x(), other.x());
			var minY = Math.min(this.y(), other.y());
			var newWidth = this.width();
			var newHeight = this.height();
			if (other.width() == Number.POSITIVE_INFINITY || this.width() == Number.POSITIVE_INFINITY) {
				newWidth = Number.POSITIVE_INFINITY;
			} else {
				var maxRight = Math.max(this.right(), other.right());
				newWidth = maxRight - minX;
			}
			if (other.height() == Number.POSITIVE_INFINITY || this.height() == Number.POSITIVE_INFINITY) {
				newHeight = Number.POSITIVE_INFINITY;
			} else {
				var maxBottom = Math.max(this.bottom(), other.bottom());
				newHeight = maxBottom - minY;
			}
			this.__x = minX;
			this.__y = minY;
			this.__width = newWidth;
			this.__height = newHeight;
			this.__left = this.__x;
			this.__top = this.__y;
			this.__right = this.__left + this.__width;
			this.__bottom = this.__top + this.__height;
		}
	}
	,
	equals: function (obj) {
		if (obj == null) {
			return $.ig.Object.prototype.equals.call(this, obj);
		}
		var other = obj;
		return other.left() == this.left() && other.top() == this.top() && other.width() == this.width() && other.height() == this.height();
	}
	,
	getHashCode: function () {
		return (this.__x) ^ (this.__y) ^ (this.__width) ^ (this.__height);
	}
	,
	l_op_Equality: function (a, b) {
		if (a == null) {
			return b == null;
		} else if (b == null) {
			return false;
		}
		return a.__x == b.__x && a.__y == b.__y && a.__width == b.__width && a.__height == b.__height;
	}
	,
	l_op_Inequality: function (a, b) {
		if (a == null) {
			return b != null;
		} else if (b == null) {
			return true;
		}
		return a.__x != b.__x || a.__y != b.__y || a.__width != b.__width || a.__height != b.__height;
	}
	,
	$type: new $.ig.Type('Rect', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Shape', 'FrameworkElement', {
	init: function () {
		this.__fill = null;
		this.__stroke = null;
		$.ig.FrameworkElement.prototype.init.call(this);
	},
	__fill: null,
	fill: function (value) {
		if (arguments.length === 1) {
			this.__fill = value;
			return value;
		} else {
			return this.__fill;
		}
	}
	,
	__stroke: null,
	stroke: function (value) {
		if (arguments.length === 1) {
			this.__stroke = value;
			return value;
		} else {
			return this.__stroke;
		}
	}
	,
	_isHitTestVisible: false,
	isHitTestVisible: function (value) {
		if (arguments.length === 1) {
			this._isHitTestVisible = value;
			return value;
		} else {
			return this._isHitTestVisible;
		}
	}
	,
	_strokeThickness: 0,
	strokeThickness: function (value) {
		if (arguments.length === 1) {
			this._strokeThickness = value;
			return value;
		} else {
			return this._strokeThickness;
		}
	}
	,
	_strokeDashArray: null,
	strokeDashArray: function (value) {
		if (arguments.length === 1) {
			this._strokeDashArray = value;
			return value;
		} else {
			return this._strokeDashArray;
		}
	}
	,
	_strokeDashCap: 0,
	strokeDashCap: function (value) {
		if (arguments.length === 1) {
			this._strokeDashCap = value;
			return value;
		} else {
			return this._strokeDashCap;
		}
	}
	,
	$type: new $.ig.Type('Shape', $.ig.FrameworkElement.prototype.$type)
}, true);

$.ig.util.defType('Line', 'Shape', {
	init: function () {
		$.ig.Shape.prototype.init.call(this);
		this.x1(0);
		this.x2(0);
		this.y1(0);
		this.y2(0);
	},
	_x1: 0,
	x1: function (value) {
		if (arguments.length === 1) {
			this._x1 = value;
			return value;
		} else {
			return this._x1;
		}
	}
	,
	_x2: 0,
	x2: function (value) {
		if (arguments.length === 1) {
			this._x2 = value;
			return value;
		} else {
			return this._x2;
		}
	}
	,
	_y1: 0,
	y1: function (value) {
		if (arguments.length === 1) {
			this._y1 = value;
			return value;
		} else {
			return this._y1;
		}
	}
	,
	_y2: 0,
	y2: function (value) {
		if (arguments.length === 1) {
			this._y2 = value;
			return value;
		} else {
			return this._y2;
		}
	}
	,
	$type: new $.ig.Type('Line', $.ig.Shape.prototype.$type)
}, true);

$.ig.util.defType('Path', 'Shape', {
	init: function () {
		$.ig.Shape.prototype.init.call(this);
	},
	_data: null,
	data: function (value) {
		if (arguments.length === 1) {
			this._data = value;
			return value;
		} else {
			return this._data;
		}
	}
	,
	_height: 0,
	height: function (value) {
		if (arguments.length === 1) {
			this._height = value;
			return value;
		} else {
			return this._height;
		}
	}
	,
	_width: 0,
	width: function (value) {
		if (arguments.length === 1) {
			this._width = value;
			return value;
		} else {
			return this._width;
		}
	}
	,
	$type: new $.ig.Type('Path', $.ig.Shape.prototype.$type)
}, true);

$.ig.util.defType('Polygon', 'Shape', {
	init: function () {
		$.ig.Shape.prototype.init.call(this);
		this.points(new $.ig.PointCollection(0));
	},
	_points: null,
	points: function (value) {
		if (arguments.length === 1) {
			this._points = value;
			return value;
		} else {
			return this._points;
		}
	}
	,
	$type: new $.ig.Type('Polygon', $.ig.Shape.prototype.$type)
}, true);

$.ig.util.defType('Polyline', 'Shape', {
	init: function () {
		$.ig.Shape.prototype.init.call(this);
		this.points(new $.ig.PointCollection(0));
	},
	_points: null,
	points: function (value) {
		if (arguments.length === 1) {
			this._points = value;
			return value;
		} else {
			return this._points;
		}
	}
	,
	$type: new $.ig.Type('Polyline', $.ig.Shape.prototype.$type)
}, true);

$.ig.util.defType('Rectangle', 'Shape', {
	__rect: null,
	__radiusX: 0,
	radiusX: function (value) {
		if (arguments.length === 1) {
			this.__radiusX = value;
			return value;
		} else {
			return this.__radiusX;
		}
	}
	,
	__radiusY: 0,
	radiusY: function (value) {
		if (arguments.length === 1) {
			this.__radiusY = value;
			return value;
		} else {
			return this.__radiusY;
		}
	}
	,
	init: function () {
		$.ig.Shape.prototype.init.call(this);
		this.__rect = new $.ig.Rect(0, 0, 0, 0, 0);
	},
	arrange: function (rect) {
	}
	,
	$type: new $.ig.Type('Rectangle', $.ig.Shape.prototype.$type)
}, true);

$.ig.util.defType('Size', 'ValueType', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.ValueType.prototype.init.call(this);
	},
	init1: function (initNumber, width, height) {
		$.ig.ValueType.prototype.init.call(this);
		this.__width = width;
		this.__height = height;
	},
	__width: 0,
	__height: 0,
	width: function (value) {
		if (arguments.length === 1) {
			this.__width = value;
			return value;
		} else {
			return this.__width;
		}
	}
	,
	height: function (value) {
		if (arguments.length === 1) {
			this.__height = value;
			return value;
		} else {
			return this.__height;
		}
	}
	,
	isEmpty: function () {
		return this.__width < 0;
	}
	,
	empty: function () {
		var s = new $.ig.Size(0);
		s.__width = Number.NEGATIVE_INFINITY;
		s.__height = Number.NEGATIVE_INFINITY;
		return s;
	}
	,
	l_op_Inequality: function (left, right) {
		return !($.ig.Size.prototype.l_op_Equality(left, right));
	}
	,
	l_op_Inequality_Lifted: function (left, right) {
		if (!left.hasValue()) {
			return right.hasValue();
		} else if (!right.hasValue()) {
			return true;
		}
		return $.ig.Size.prototype.l_op_Inequality(left.value(), right.value());
	}
	,
	l_op_Equality: function (left, right) {
		return left.__width == right.__width && left.__height == right.__height;
	}
	,
	l_op_Equality_Lifted: function (left, right) {
		if (!left.hasValue()) {
			return !right.hasValue();
		} else if (!right.hasValue()) {
			return false;
		}
		return $.ig.Size.prototype.l_op_Equality(left.value(), right.value());
	}
	,
	$type: new $.ig.Type('Size', $.ig.ValueType.prototype.$type)
}, true);

$.ig.util.defType('Style', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('Style', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Enumerable___Skip__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_source: null,
	__3__source: null,
	_toSkip: 0,
	__3__toSkip: 0,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							if (this._toSkip <= 0) {
								this.__2__current = this.__item_5_0;
								this.__1__state = 2;
								return true;
							}
							this._toSkip--;
							this.__1__state = 2;
							break;
						}
						this._m_Finally0();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Skip__IteratorClass$1(this.$tSource, 0);
		}
		d__._source = this.__3__source;
		d__._toSkip = this.__3__toSkip;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Skip__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Take__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_source: null,
	__3__source: null,
	_toTake: 0,
	__3__toTake: 0,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							if (this._toTake > 0) {
								this._toTake--;
								this.__2__current = this.__item_5_0;
								this.__1__state = 2;
								return true;
							}
							return false;
						}
						this._m_Finally0();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Take__IteratorClass$1(this.$tSource, 0);
		}
		d__._source = this.__3__source;
		d__._toTake = this.__3__toTake;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Take__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___SelectMany__IteratorClass1$3', 'Object', {
	$tSource: null,
	$tCollection: null,
	$tResult: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_resultEnumerator: null,
	__result_5_1: null,
	_source: null,
	__3__source: null,
	_collectionSelector: null,
	__3__collectionSelector: null,
	_resultSelector: null,
	__3__resultSelector: null,
	init: function ($tSource, $tCollection, $tResult, _1__state) {
		this.$tSource = $tSource;
		this.$tCollection = $tCollection;
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tSource, this.$tCollection, this.$tResult);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	_m_Finally1: function () {
		this.__1__state = 1;
		if (this._resultEnumerator != null) {
			this._resultEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							this.__1__state = 3;
							this._resultEnumerator = this._collectionSelector(this.__item_5_0).getEnumerator();
							this.__1__state = 4;
							break;
						}
						this._m_Finally0();
						break;
					case 4:
						this.__1__state = 3;
						if (this._resultEnumerator.moveNext()) {
							this.__result_5_1 = this._resultEnumerator.current();
							this.__2__current = this._resultSelector(this.__item_5_0, this.__result_5_1);
							this.__1__state = 4;
							return true;
						}
						this._m_Finally1();
						this.__1__state = 2;
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___SelectMany__IteratorClass1$3(this.$tSource, this.$tCollection, this.$tResult, 0);
		}
		d__._source = this.__3__source;
		d__._collectionSelector = this.__3__collectionSelector;
		d__._resultSelector = this.__3__resultSelector;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
			case 3:
			case 4:
				try {
					this._m_Finally1();
				}
				finally {
					this._m_Finally0();
				}
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tResult, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___SelectMany__IteratorClass1$3', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(2), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(2), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Reverse__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	__list_5_0: null,
	__i_5_1: 0,
	_source: null,
	__3__source: null,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__list_5_0 = new $.ig.List$1(this.$tSource, 1, this._source);
					this.__i_5_1 = this.__list_5_0.count() - 1;
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_1 >= 0) {
						this.__2__current = this.__list_5_0.__inner[this.__i_5_1];
						this.__1__state = 2;
						return true;
					}
					break;
				case 2:
					this.__1__state = -1;
					this.__i_5_1--;
					this.__1__state = 1;
					break;
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Reverse__IteratorClass$1(this.$tSource, 0);
		}
		d__._source = this.__3__source;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Reverse__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Range__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__i_5_0: 0,
	_startValue: 0,
	__3__startValue: 0,
	_count: 0,
	__3__count: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__i_5_0 = this._startValue;
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_0 < this._count) {
						this.__2__current = this.__i_5_0;
						this.__1__state = 2;
						return true;
					}
					break;
				case 2:
					this.__1__state = -1;
					this.__i_5_0++;
					this.__1__state = 1;
					break;
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Range__IteratorClass(0);
		}
		d__._startValue = this.__3__startValue;
		d__._count = this.__3__count;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('Enumerable___Range__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.Number.prototype.$type), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize($.ig.Number.prototype.$type), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___OfType__IteratorClass$1', 'Object', {
	$tResult: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_source: null,
	__3__source: null,
	init: function ($tResult, _1__state) {
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tResult);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		var d__ = $.ig.util.cast($.ig.IDisposable.prototype.$type, this._itemEnumerator);
		if (d__ != null) {
			d__.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							if ($.ig.util.cast(this.$tResult, this.__item_5_0) !== null) {
								this.__2__current = $.ig.util.castObjTo$t(this.$tResult, this.__item_5_0);
								this.__1__state = 2;
								return true;
							}
							this.__1__state = 2;
							break;
						}
						this._m_Finally0();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___OfType__IteratorClass$1(this.$tResult, 0);
		}
		d__._source = this.__3__source;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tResult, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___OfType__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___SelectMany__IteratorClass$2', 'Object', {
	$tSource: null,
	$tResult: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_item2Enumerator: null,
	__item2_5_1: null,
	_source: null,
	__3__source: null,
	_selector: null,
	__3__selector: null,
	init: function ($tSource, $tResult, _1__state) {
		this.$tSource = $tSource;
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tSource, this.$tResult);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	_m_Finally1: function () {
		this.__1__state = 1;
		if (this._item2Enumerator != null) {
			this._item2Enumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							this.__1__state = 3;
							this._item2Enumerator = this._selector(this.__item_5_0).getEnumerator();
							this.__1__state = 4;
							break;
						}
						this._m_Finally0();
						break;
					case 4:
						this.__1__state = 3;
						if (this._item2Enumerator.moveNext()) {
							this.__item2_5_1 = this._item2Enumerator.current();
							this.__2__current = this.__item2_5_1;
							this.__1__state = 4;
							return true;
						}
						this._m_Finally1();
						this.__1__state = 2;
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___SelectMany__IteratorClass$2(this.$tSource, this.$tResult, 0);
		}
		d__._source = this.__3__source;
		d__._selector = this.__3__selector;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
			case 3:
			case 4:
				try {
					this._m_Finally1();
				}
				finally {
					this._m_Finally0();
				}
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tResult, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___SelectMany__IteratorClass$2', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(1), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(1), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Select__IteratorClass$2', 'Object', {
	$tSource: null,
	$tResult: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_source: null,
	__3__source: null,
	_selector: null,
	__3__selector: null,
	init: function ($tSource, $tResult, _1__state) {
		this.$tSource = $tSource;
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tSource, this.$tResult);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							this.__2__current = this._selector(this.__item_5_0);
							this.__1__state = 2;
							return true;
						}
						this._m_Finally0();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Select__IteratorClass$2(this.$tSource, this.$tResult, 0);
		}
		d__._source = this.__3__source;
		d__._selector = this.__3__selector;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tResult, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Select__IteratorClass$2', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(1), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(1), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Where__IteratorClass1$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	__index_5_0: 0,
	_itemEnumerator: null,
	__item_5_1: null,
	_source: null,
	__3__source: null,
	_predicate: null,
	__3__predicate: null,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = -1;
						this.__index_5_0 = 0;
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_1 = this._itemEnumerator.current();
							if (this._predicate(this.__item_5_1, this.__index_5_0)) {
								this.__2__current = this.__item_5_1;
								this.__1__state = 3;
								return true;
							}
							this.__1__state = 3;
							break;
						}
						this._m_Finally0();
						break;
					case 3:
						this.__1__state = 1;
						this.__index_5_0++;
						this.__1__state = 2;
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Where__IteratorClass1$1(this.$tSource, 0);
		}
		d__._source = this.__3__source;
		d__._predicate = this.__3__predicate;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
			case 3:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Where__IteratorClass1$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Where__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_source: null,
	__3__source: null,
	_predicate: null,
	__3__predicate: null,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							if (this._predicate(this.__item_5_0)) {
								this.__2__current = this.__item_5_0;
								this.__1__state = 2;
								return true;
							}
							this.__1__state = 2;
							break;
						}
						this._m_Finally0();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Where__IteratorClass$1(this.$tSource, 0);
		}
		d__._source = this.__3__source;
		d__._predicate = this.__3__predicate;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Where__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Dictionary___ToEnumerable__IteratorClass$2', 'Object', {
	$tKey: null,
	$tValue: null,
	__1__state: 0,
	__2__current: null,
	__array_5_0: null,
	__i_5_1: 0,
	__array_5_2: null,
	__i_5_3: 0,
	__pair_5_4: null,
	__pArray_5_5: null,
	__j_5_6: 0,
	__4__this: null,
	init: function ($tKey, $tValue, _1__state) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue);
		this.__2__current = new $.ig.KeyValuePair$2(this.$tKey, this.$tValue);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					if (this.__4__this.__assumeUniqueKeys) {
						this.__array_5_0 = $.ig.util.getArrayOfProperties(this.__4__this.__keysUnique);
						this.__i_5_1 = 0;
						this.__1__state = 1;
						break;
					}
					this.__array_5_2 = $.ig.util.getArrayOfProperties(this.__4__this.__values);
					this.__i_5_3 = 0;
					this.__1__state = 3;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_1 < this.__array_5_0.length) {
						this.__2__current = new $.ig.KeyValuePair$2(this.$tKey, this.$tValue, 1, this.__4__this.__keysUnique[this.__array_5_0[this.__i_5_1]], this.__4__this.__values[this.__array_5_0[this.__i_5_1]]);
						this.__1__state = 2;
						return true;
					}
					break;
				case 2:
					this.__1__state = -1;
					this.__i_5_1++;
					this.__1__state = 1;
					break;
				case 3:
					this.__1__state = -1;
					if (this.__i_5_3 < this.__array_5_2.length) {
						this.__pair_5_4 = this.__4__this.__values[this.__array_5_2[this.__i_5_3]];
						if (this.__pair_5_4.$isHashSetBucket) {
							this.__pArray_5_5 = this.__pair_5_4;
							this.__j_5_6 = 0;
							this.__1__state = 4;
							break;
						}
						this.__2__current = new $.ig.KeyValuePair$2(this.$tKey, this.$tValue, 1, this.__pair_5_4.key, this.__pair_5_4.value);
						this.__1__state = 6;
						return true;
					}
					break;
				case 4:
					this.__1__state = -1;
					if (this.__j_5_6 < this.__pArray_5_5.length) {
						var subItem_ = this.__pArray_5_5[this.__j_5_6];
						this.__2__current = new $.ig.KeyValuePair$2(this.$tKey, this.$tValue, 1, subItem_.key, subItem_.value);
						this.__1__state = 5;
						return true;
					}
					this.__1__state = 6;
					break;
				case 5:
					this.__1__state = -1;
					this.__j_5_6++;
					this.__1__state = 4;
					break;
				case 6:
					this.__1__state = -1;
					this.__i_5_3++;
					this.__1__state = 3;
					break;
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Dictionary___ToEnumerable__IteratorClass$2(this.$tKey, this.$tValue, 0);
			d__.__4__this = this.__4__this;
		}
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('Dictionary___ToEnumerable__IteratorClass$2', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(0, 1)), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(0, 1)), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Concat__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	_itemEnumerator: null,
	__item_5_0: null,
	_itemEnumerator0: null,
	__item_5_1: null,
	_source1: null,
	__3__source1: null,
	_source2: null,
	__3__source2: null,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._itemEnumerator != null) {
			this._itemEnumerator.dispose();
		}
	}
	,
	_m_Finally1: function () {
		this.__1__state = -1;
		if (this._itemEnumerator0 != null) {
			this._itemEnumerator0.dispose();
		}
	}
	,
	moveNext: function () {
		var __hasError__ = false;
		try {
			while (this.__1__state >= 0) {
				switch (this.__1__state) {
					case 0:
						this.__1__state = 1;
						this._itemEnumerator = this._source1.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._itemEnumerator.moveNext()) {
							this.__item_5_0 = this._itemEnumerator.current();
							this.__2__current = this.__item_5_0;
							this.__1__state = 2;
							return true;
						}
						this._m_Finally0();
						this.__1__state = 3;
						this._itemEnumerator0 = this._source2.getEnumerator();
						this.__1__state = 4;
						break;
					case 4:
						this.__1__state = 3;
						if (this._itemEnumerator0.moveNext()) {
							this.__item_5_1 = this._itemEnumerator0.current();
							this.__2__current = this.__item_5_1;
							this.__1__state = 4;
							return true;
						}
						this._m_Finally1();
						break;
				}
			}
		}
		catch (e) {
			__hasError__ = true;
			throw e;
		}
		finally {
			if (__hasError__) {
				(this).dispose();
			}
		}
		return false;
	}
	,
	getEnumerator: function () {
		var d__;
		if (this.__1__state == -2) {
			this.__1__state = 0;
			d__ = this;
		} else {
			d__ = new $.ig.Enumerable___Concat__IteratorClass$1(this.$tSource, 0);
		}
		d__._source1 = this.__3__source1;
		d__._source2 = this.__3__source2;
		return d__;
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
		switch (this.__1__state) {
			case 1:
			case 2:
				this._m_Finally0();
				break;
			case 3:
			case 4:
				this._m_Finally1();
				break;
		}
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Concat__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('Enumerable___Empty__IteratorClass$1', 'Object', {
	$tSource: null,
	__1__state: 0,
	__2__current: null,
	init: function ($tSource, _1__state) {
		this.$tSource = $tSource;
		this.$type = this.$type.specialize(this.$tSource);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		switch (this.__1__state) {
			case 0:
				this.__1__state = -1;
				return false;
		}
		return false;
	}
	,
	getEnumerator: function () {
		if (this.__1__state == -2) {
			this.__1__state = 0;
			return this;
		}
		return new $.ig.Enumerable___Empty__IteratorClass$1(this.$tSource, 0);
	}
	,
	reset: function () {
		throw new $.ig.NotSupportedException(1);
	}
	,
	dispose: function () {
	}
	,
	current: function () {
		return this.__2__current;
	}
	,
	current1: function () {
		return $.ig.util.getBoxIfEnum(this.$tSource, this.__2__current);
	}
	,
	$type: new $.ig.Type('Enumerable___Empty__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.Visibility.prototype.visible = 0;
$.ig.Visibility.prototype.collapsed = 1;

$.ig.VerticalAlignment.prototype.top = 0;
$.ig.VerticalAlignment.prototype.center = 1;
$.ig.VerticalAlignment.prototype.bottom = 2;
$.ig.VerticalAlignment.prototype.stretch = 3;

$.ig.HorizontalAlignment.prototype.left = 0;
$.ig.HorizontalAlignment.prototype.center = 1;
$.ig.HorizontalAlignment.prototype.right = 2;
$.ig.HorizontalAlignment.prototype.stretch = 3;

$.ig.SweepDirection.prototype.counterclockwise = 0;
$.ig.SweepDirection.prototype.clockwise = 1;

$.ig.PathSegmentType.prototype.line = 0;
$.ig.PathSegmentType.prototype.bezier = 1;
$.ig.PathSegmentType.prototype.polyBezier = 2;
$.ig.PathSegmentType.prototype.polyLine = 3;
$.ig.PathSegmentType.prototype.arc = 4;

$.ig.GeometryType.prototype.group = 0;
$.ig.GeometryType.prototype.line = 1;
$.ig.GeometryType.prototype.rectangle = 2;
$.ig.GeometryType.prototype.ellipse = 3;
$.ig.GeometryType.prototype.path = 4;

$.ig.FillRule.prototype.evenOdd = 0;
$.ig.FillRule.prototype.nonzero = 1;

$.ig.NotifyCollectionChangedAction.prototype.add = 0;
$.ig.NotifyCollectionChangedAction.prototype.remove = 1;
$.ig.NotifyCollectionChangedAction.prototype.replace = 2;
$.ig.NotifyCollectionChangedAction.prototype.reset = 4;

$.ig.DependencyProperty.prototype.unsetValue = new $.ig.UnsetValue();

$.ig.DependencyPropertiesCollection.prototype.__instance = null;

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"Rect:ba", 
"Size:bb", 
"Point:bc", 
"Math:bd", 
"Func$2:bf", 
"MulticastDelegate:bg", 
"IntPtr:bh", 
"Delegate:bj", 
"Interlocked:bk", 
"JQueryObject:bv", 
"Element:bw", 
"ElementAttributeCollection:bx", 
"ElementCollection:by", 
"WebStyle:bz", 
"ElementNodeType:b0", 
"Document:b1", 
"EventListener:b2", 
"IElementEventHandler:b3", 
"ElementEventHandler:b4", 
"ElementAttribute:b5", 
"JQueryPosition:b6", 
"JQueryCallback:b7", 
"JQueryEvent:b8", 
"JQueryUICallback:b9", 
"Script:cc", 
"JQuery:cd", 
"JQueryDeferred:ce", 
"JQueryPromise:cf", 
"Action:cg", 
"Action$1:ch", 
"Callback:ci", 
"window:cj", 
"EventArgs:cl", 
"UIElement:cm", 
"DependencyObject:cn", 
"Dictionary:co", 
"DependencyProperty:cp", 
"PropertyMetadata:cq", 
"PropertyChangedCallback:cr", 
"DependencyPropertyChangedEventArgs:cs", 
"DependencyPropertiesCollection:ct", 
"UnsetValue:cu", 
"Binding:cv", 
"PropertyPath:cw", 
"Transform:cx", 
"List$1:cz", 
"IList$1:c0", 
"ICollection$1:c1", 
"IArray:c2", 
"IArrayList:c3", 
"Array:c4", 
"CompareCallback:c5", 
"Func$3:c6", 
"Comparer$1:c7", 
"IComparer:c8", 
"IComparer$1:c9", 
"DefaultComparer$1:da", 
"Comparison$1:db", 
"ReadOnlyCollection$1:dc", 
"Predicate$1:dd", 
"NotImplementedException:de", 
"NotifyCollectionChangedEventArgs:d1", 
"NotifyCollectionChangedAction:d2", 
"Dictionary$2:d8", 
"IDictionary$2:d9", 
"IDictionary:ea", 
"KeyValuePair$2:eb", 
"Enumerable:ec", 
"Thread:ed", 
"ThreadStart:ee", 
"IOrderedEnumerable$1:ef", 
"SortedList$1:eg", 
"ArgumentNullException:eh", 
"IEqualityComparer$1:ei", 
"EqualityComparer$1:ej", 
"IEqualityComparer:ek", 
"DefaultEqualityComparer$1:el", 
"InvalidOperationException:em", 
"ArgumentException:en", 
"IRenderer:er", 
"Rectangle:es", 
"Shape:et", 
"FrameworkElement:eu", 
"Visibility:ev", 
"Style:ew", 
"Brush:ex", 
"Color:ey", 
"DoubleCollection:ez", 
"Path:e0", 
"Geometry:e1", 
"GeometryType:e2", 
"TextBlock:e3", 
"Polygon:e4", 
"PointCollection:e5", 
"Polyline:e6", 
"DataTemplateRenderInfo:e7", 
"DataTemplatePassInfo:e8", 
"ContentControl:e9", 
"Control:fa", 
"Thickness:fb", 
"HorizontalAlignment:fc", 
"VerticalAlignment:fd", 
"DataTemplate:fe", 
"DataTemplateRenderHandler:ff", 
"DataTemplateMeasureHandler:fg", 
"DataTemplateMeasureInfo:fh", 
"DataTemplatePassHandler:fi", 
"Line:fj", 
"FontInfo:fk", 
"RenderingContext:fn", 
"CanvasViewRenderer:fo", 
"CanvasContext2D:fp", 
"CanvasContext:fq", 
"TextMetrics:fr", 
"ImageData:fs", 
"CanvasElement:ft", 
"Gradient:fu", 
"LinearGradientBrush:fv", 
"GradientStop:fw", 
"GeometryGroup:fx", 
"GeometryCollection:fy", 
"FillRule:fz", 
"PathGeometry:f0", 
"PathFigureCollection:f1", 
"LineGeometry:f2", 
"RectangleGeometry:f3", 
"EllipseGeometry:f4", 
"ArcSegment:f5", 
"PathSegment:f6", 
"PathSegmentType:f7", 
"SweepDirection:f8", 
"PathFigure:f9", 
"PathSegmentCollection:ga", 
"LineSegment:gb", 
"PolyLineSegment:gc", 
"BezierSegment:gd", 
"PolyBezierSegment:ge", 
"GeometryUtil:gf", 
"Tuple$2:gg", 
"TransformGroup:gh", 
"TransformCollection:gi", 
"TranslateTransform:gj", 
"RotateTransform:gk", 
"ScaleTransform:gl", 
"MathUtil:g3", 
"RuntimeHelpers:g4", 
"RuntimeFieldHandle:g5", 
"Random:g8", 
"CssGradientUtil:hf", 
"FontUtil:hg", 
"Func$1:ib", 
"EasingFunctions:im", 
"AbstractEnumerable:kp", 
"AbstractEnumerator:kq", 
"GenericEnumerable$1:kr", 
"GenericEnumerator$1:ks"]);


$.ig.util.defType('IRenderer', 'Object', {
	$type: new $.ig.Type('IRenderer', null)
}, true);

$.ig.util.defType('CanvasViewRenderer', 'Object', {
	init: function () {
		this.__trackBounds = false;
		this.__validBounds = false;
		this.__globalAlpha = 1;
		$.ig.Object.prototype.init.call(this);
	},
	__context: null,
	getUnderlyingContext: function () {
		return this.__context;
	}
	,
	data: function (value) {
		if (arguments.length === 1) {
			this.__context = value;
			return value;
		} else {
			return this.__context;
		}
	}
	,
	createGradient1: function (brush, minX, minY, maxX, maxY, lineWidth, isStroke) {
		return $.ig.CanvasViewRenderer.prototype.createGradient(this.__context, brush, minX, minY, maxX, maxY, lineWidth, isStroke);
	}
	,
	createGradient: function (context, brush, minX, minY, maxX, maxY, lineWidth, isStroke) {
		var gradient;
		if (brush._useCustomDirection) {
			var _minX, _minY, _maxX, _maxY, ratio;
			ratio = maxX - minX;
			_minX = minX + brush._startX * ratio;
			_maxX = minX + brush._endX * ratio;
			ratio = maxY - minY;
			_minY = minY + brush._startY * ratio;
			_maxY = minY + brush._endY * ratio;
			if (isStroke) {
				var halfWidth = lineWidth / 2;
				_minX -= halfWidth;
				_maxX += halfWidth;
				_minY -= halfWidth;
				_maxY += halfWidth;
			}
			gradient = context.createLinearGradient(_minX, _minY, _maxX, _maxY);
		} else {
			if (isStroke) {
				var halfWidth1 = lineWidth / 2;
				minX -= halfWidth1;
				maxX += halfWidth1;
				minY -= halfWidth1;
				maxY += halfWidth1;
			}
			gradient = context.createLinearGradient(minX, minY, minX, maxY);
		}
		for (var i = 0; i < brush._gradientStops.length; i++) {
			var stop = brush._gradientStops[i];
			gradient.addColorStop(stop._offset, stop.__fill);
		}
		return gradient;
	}
	,
	renderRectangle: function (rectangle) {
		if (rectangle.__visibility != $.ig.Visibility.prototype.visible) {
			return;
		}
		var left = rectangle.canvasLeft();
		var top = rectangle.canvasTop();
		var width = rectangle.width();
		var height = rectangle.height();
		var radiusX = rectangle.radiusX();
		var radiusY = rectangle.radiusY();
		this.__context.beginPath();
		this.__context.globalAlpha = (rectangle.__opacity * this.__globalAlpha);
		if (radiusX > 0 || radiusY > 0) {
			if (radiusX > width / 2) {
				radiusX = (width / 2);
			}
			if (radiusY > height / 2) {
				radiusY = (height / 2);
			}
			var radius = Math.min(radiusX, radiusY);
			this.__context.beginPath();
			this.__context.moveTo(left + radius, top);
			this.__context.lineTo(left + width - radius, top);
			this.__context.arc(left + width - radius, top + radius, radius, (3 / 2 * Math.PI), 0, false);
			this.__context.lineTo(left + width, top + height - radius);
			this.__context.arc(left + width - radius, top + height - radius, radius, 0, (Math.PI / 2), false);
			this.__context.lineTo(left + radius, top + height);
			this.__context.arc(left + radius, top + height - radius, radius, (Math.PI / 2), Math.PI, false);
			this.__context.lineTo(left, top + radius);
			this.__context.arc(left + radius, top + radius, radius, Math.PI, (3 / 2 * Math.PI), false);
			this.__context.closePath();
		} else {
			this.__context.rect(left, top, width, height);
		}
		var fill = rectangle.__fill;
		var stroke = rectangle.__stroke;
		if (fill != null) {
			if (fill._isGradient && top == top && left == left) {
				this.__context.fillStyle = this.createGradient1(fill, left, top, left + width, top + height, rectangle.strokeThickness(), false);
			} else {
				this.__context.fillStyle = fill.__fill;
			}
			this.__context.fill();
		}
		if (stroke != null) {
			if (stroke._isGradient && top == top && left == left) {
				this.__context.strokeStyle = this.createGradient1(stroke, left, top, left + width, top + height, rectangle.strokeThickness(), true);
			} else {
				this.__context.strokeStyle = stroke.__fill;
			}
			this.__context.lineWidth = rectangle.strokeThickness();
			this.__context.stroke();
		}
		this.__context.globalAlpha = 1;
	}
	,
	__minX: 0,
	__maxX: 0,
	__minY: 0,
	__maxY: 0,
	__trackBounds: false,
	__validBounds: false,
	renderPath: function (path) {
		if (path.__visibility != $.ig.Visibility.prototype.visible) {
			return;
		}
		this.__context.beginPath();
		if (path.__opacity < 1 || this.__globalAlpha < 1) {
			this.__context.globalAlpha = (path.__opacity * this.__globalAlpha);
		}
		var fill = path.__fill;
		var stroke = path.__stroke;
		this.__trackBounds = (fill != null && fill._isGradient) || (stroke != null && stroke._isGradient);
		if (this.__trackBounds) {
			this.__maxX = -1.7976931348623157E+308;
			this.__maxY = -1.7976931348623157E+308;
			this.__minX = 1.7976931348623157E+308;
			this.__minY = 1.7976931348623157E+308;
			this.__validBounds = false;
		}
		this.renderGeometry(path.data());
		if (fill != null) {
			if (fill._isGradient && this.__validBounds) {
				this.__context.fillStyle = this.createGradient1(fill, this.__minX, this.__minY, this.__maxX, this.__maxY, path.strokeThickness(), false);
			} else {
				this.__context.fillStyle = path.__fill.__fill;
			}
			this.__context.fill();
		}
		if (stroke != null) {
			if (stroke._isGradient && this.__validBounds) {
				this.__context.strokeStyle = this.createGradient1(stroke, this.__minX, this.__minY, this.__maxX, this.__maxY, path.strokeThickness(), true);
			} else {
				this.__context.strokeStyle = path.__stroke.__fill;
			}
			this.__context.lineWidth = path.strokeThickness();
			this.__context.stroke();
		}
		if (path.__opacity < 1 || this.__globalAlpha < 1) {
			this.__context.globalAlpha = 1;
		}
	}
	,
	renderGeometry: function (geometry) {
		if (geometry == null) {
			return;
		}
		var type = geometry.type();
		switch (type) {
			case $.ig.GeometryType.prototype.group:
				for (var i = 0; i < (geometry).children().count(); i++) {
					this.renderGeometry((geometry).children().__inner[i]);
				}
				break;
			case $.ig.GeometryType.prototype.path:
				this.renderPathGeometry(geometry);
				break;
			case $.ig.GeometryType.prototype.line:
				this.renderLineGeometry(geometry);
				break;
			case $.ig.GeometryType.prototype.rectangle:
				this.renderRectangleGeometry(geometry);
				break;
			case $.ig.GeometryType.prototype.ellipse:
				this.renderEllipseGeometry(geometry);
				break;
		}
	}
	,
	renderEllipseGeometry: function (ellipseGeometry) {
		this.__context.moveTo(ellipseGeometry.center().__x, (ellipseGeometry.center().__y - ellipseGeometry.radiusY()));
		this.__lastSegmentPoint = { __x: ellipseGeometry.center().__x, __y: ellipseGeometry.center().__y - ellipseGeometry.radiusY(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		var a1 = (function () {
			var $ret = new $.ig.ArcSegment();
			$ret.point({ __x: ellipseGeometry.center().__x, __y: ellipseGeometry.center().__y + ellipseGeometry.radiusY(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			$ret.isLargeArc(false);
			$ret.size(new $.ig.Size(1, ellipseGeometry.radiusX(), ellipseGeometry.radiusY()));
			return $ret;
		}());
		var a2 = (function () {
			var $ret = new $.ig.ArcSegment();
			$ret.point({ __x: ellipseGeometry.center().__x, __y: ellipseGeometry.center().__y - ellipseGeometry.radiusY(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			$ret.isLargeArc(false);
			$ret.size(new $.ig.Size(1, ellipseGeometry.radiusX(), ellipseGeometry.radiusY()));
			return $ret;
		}());
		var oldTrack = this.__trackBounds;
		this.__trackBounds = false;
		this.renderArcSegment(a1);
		this.renderArcSegment(a2);
		this.__trackBounds = oldTrack;
		if (!this.__trackBounds) {
			return;
		}
		var minX = ellipseGeometry.center().__x - ellipseGeometry.radiusX();
		var maxX = ellipseGeometry.center().__y + ellipseGeometry.radiusX();
		var minY = ellipseGeometry.center().__x - ellipseGeometry.radiusY();
		var maxY = ellipseGeometry.center().__y + ellipseGeometry.radiusY();
		var currMaxX = this.__maxX;
		var currMaxY = this.__maxY;
		var currMinX = this.__minX;
		var currMinY = this.__minY;
		this.__minX = minX < currMinX ? minX : currMinX;
		this.__minY = minY < currMinY ? minY : currMinY;
		this.__maxX = maxX > currMaxX ? maxX : currMaxX;
		this.__maxY = maxY > currMaxY ? maxY : currMaxY;
		this.__validBounds = true;
	}
	,
	renderPathGeometry: function (pathGeometry) {
		var figures = pathGeometry.figures();
		var count = figures.count();
		for (var i = 0; i < count; i++) {
			this.renderFigure(figures.__inner[i]);
		}
	}
	,
	renderFigure: function (figure) {
		var p = figure == null ? null : figure.__startPoint;
		if ($.ig.Point.prototype.l_op_Equality(p, null)) {
			return;
		}
		var x = p.__x;
		var y = p.__y;
		this.__context.moveTo(x, y);
		if (this.__trackBounds) {
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			this.__minX = x < currMinX ? x : currMinX;
			this.__minY = y < currMinY ? y : currMinY;
			this.__maxX = x > currMaxX ? x : currMaxX;
			this.__maxY = y > currMaxY ? y : currMaxY;
			this.__validBounds = true;
		}
		this.__lastSegmentPoint = p;
		var segments = figure.__segments;
		var count = segments.count();
		for (var i = 0; i < count; i++) {
			this.renderSegment(segments.__inner[i]);
		}
		if (figure.__isClosed) {
			this.__context.closePath();
		}
	}
	,
	renderSegment: function (segment) {
		var type = segment.type();
		switch (type) {
			case $.ig.PathSegmentType.prototype.line:
				this.renderLineSegment(segment);
				break;
			case $.ig.PathSegmentType.prototype.polyLine:
				this.renderPolyLineSegment(segment);
				break;
			case $.ig.PathSegmentType.prototype.arc:
				this.renderArcSegment(segment);
				break;
			case $.ig.PathSegmentType.prototype.bezier:
				this.renderBezierSegment(segment);
				break;
			case $.ig.PathSegmentType.prototype.polyBezier:
				this.renderPolyBezierSegment(segment);
				break;
		}
	}
	,
	__lastSegmentPoint: null,
	updateBoundsFromBezier: function (x0, y0, x1, y1, x2, y2, x3, y3) {
		var delta = 1 / 50;
		var oneMinusT;
		var oneMinusT2;
		var oneMinusT3;
		var t2;
		var t3;
		var currX;
		var currY;
		var currMinX = this.__minX;
		var currMinY = this.__minY;
		var currMaxX = this.__maxX;
		var currMaxY = this.__maxY;
		for (var t = 0; t <= 1; t += delta) {
			oneMinusT = 1 - t;
			oneMinusT2 = oneMinusT * oneMinusT;
			oneMinusT3 = oneMinusT2 * oneMinusT;
			t2 = t * t;
			t3 = t2 * t;
			currX = oneMinusT3 * x0 + 3 * oneMinusT2 * t * x1 + 3 * oneMinusT * t2 * x2 + t3 * x3;
			currY = oneMinusT3 * y0 + 3 * oneMinusT2 * t * y1 + 3 * oneMinusT * t2 * y2 + t3 * y3;
			currMinX = currX < currMinX ? currX : currMinX;
			currMinY = currY < currMinY ? currY : currMinY;
			currMaxX = currX > currMaxX ? currX : currMaxX;
			currMaxY = currY > currMaxY ? currY : currMaxY;
		}
		this.__minX = currMinX;
		this.__minY = currMinY;
		this.__maxX = currMaxX;
		this.__maxY = currMaxY;
		this.__validBounds = true;
	}
	,
	renderBezierSegment: function (segment) {
		this.__context.bezierCurveTo(segment.point1().__x, segment.point1().__y, segment.point2().__x, segment.point2().__y, segment.point3().__x, segment.point3().__y);
		if (this.__trackBounds) {
			this.updateBoundsFromBezier(this.__lastSegmentPoint.__x, this.__lastSegmentPoint.__y, segment.point1().__x, segment.point1().__y, segment.point2().__x, segment.point2().__y, segment.point3().__x, segment.point3().__y);
		}
	}
	,
	renderPolyBezierSegment: function (arcSegment) {
		var i = 0;
		var pointsCount = arcSegment.points().count();
		var points = arcSegment.points();
		var p1 = this.__lastSegmentPoint;
		var p2 = this.__lastSegmentPoint;
		var p3 = this.__lastSegmentPoint;
		var trackBounds = this.__trackBounds;
		while (i < pointsCount) {
			if (i + 1 < pointsCount && i + 2 < pointsCount) {
				p1 = points.__inner[i];
				p2 = points.__inner[i + 1];
				p3 = points.__inner[i + 2];
				this.__context.bezierCurveTo(p1.__x, p1.__y, p2.__x, p2.__y, p3.__x, p3.__y);
				if (trackBounds) {
					this.updateBoundsFromBezier(this.__lastSegmentPoint.__x, this.__lastSegmentPoint.__y, p1.__x, p1.__y, p2.__x, p2.__y, p3.__x, p3.__y);
					this.__lastSegmentPoint = p3;
				}
			}
			i = i + 3;
		}
		this.__lastSegmentPoint = p3;
	}
	,
	updateBoundsFromArc: function (center, startAngle, endAngle, radius, isCounter) {
		var points = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		points.add(center);
		points.add({ __x: center.__x + Math.cos(startAngle) * radius, __y: center.__y + Math.sin(startAngle) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		points.add({ __x: center.__x + Math.cos(endAngle) * radius, __y: center.__y + Math.sin(endAngle) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		var threeSixty = Math.PI * 2;
		var ninety = Math.PI / 2;
		var oneEighty = Math.PI;
		var twoSeventy = Math.PI * 3 / 2;
		while (startAngle < 0) {
			startAngle += threeSixty;
		}
		while (startAngle > threeSixty) {
			startAngle -= threeSixty;
		}
		while (endAngle < 0) {
			endAngle += threeSixty;
		}
		while (endAngle > threeSixty) {
			endAngle -= threeSixty;
		}
		if (isCounter) {
			if ((0 > endAngle && 0 < startAngle) || (threeSixty > endAngle && threeSixty < startAngle) || (startAngle < endAngle)) {
				points.add({ __x: center.__x + Math.cos(0) * radius, __y: center.__y + Math.sin(0) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (ninety > endAngle && ninety < startAngle) {
				points.add({ __x: center.__x + Math.cos(ninety) * radius, __y: center.__y + Math.sin(ninety) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (oneEighty > endAngle && oneEighty < startAngle) {
				points.add({ __x: center.__x + Math.cos(oneEighty) * radius, __y: center.__y + Math.sin(oneEighty) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (twoSeventy > endAngle && twoSeventy < startAngle) {
				points.add({ __x: center.__x + Math.cos(twoSeventy) * radius, __y: center.__y + Math.sin(twoSeventy) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
		} else {
			if ((0 > startAngle && 0 < endAngle) || (threeSixty > startAngle && threeSixty < endAngle) || (endAngle < startAngle)) {
				points.add({ __x: center.__x + Math.cos(0) * radius, __y: center.__y + Math.sin(0) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (ninety > startAngle && ninety < endAngle) {
				points.add({ __x: center.__x + Math.cos(ninety) * radius, __y: center.__y + Math.sin(ninety) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (oneEighty > startAngle && oneEighty < endAngle) {
				points.add({ __x: center.__x + Math.cos(oneEighty) * radius, __y: center.__y + Math.sin(oneEighty) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (twoSeventy > startAngle && twoSeventy < endAngle) {
				points.add({ __x: center.__x + Math.cos(twoSeventy) * radius, __y: center.__y + Math.sin(twoSeventy) * radius, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
		}
		var minX = 1.7976931348623157E+308;
		var minY = 1.7976931348623157E+308;
		var maxX = -1.7976931348623157E+308;
		var maxY = -1.7976931348623157E+308;
		for (var i = 0; i < points.count(); i++) {
			var point = points.__inner[i];
			minX = Math.min(minX, point.__x);
			minY = Math.min(minY, point.__y);
			maxX = Math.max(maxX, point.__x);
			maxY = Math.max(maxY, point.__y);
		}
		this.__minX = Math.min(this.__minX, minX);
		this.__minY = Math.min(this.__minY, minY);
		this.__maxX = Math.max(this.__maxX, maxX);
		this.__maxY = Math.max(this.__maxY, maxY);
		this.__validBounds = true;
	}
	,
	renderArcSegment: function (arcSegment) {
		var startPoint = this.__lastSegmentPoint;
		var endPoint = arcSegment.point();
		if (arcSegment.size().width() != arcSegment.size().height()) {
			this.__context.save();
			this.__context.scale(arcSegment.size().width() / arcSegment.size().height(), 1);
			startPoint = { __x: startPoint.__x * (arcSegment.size().height() / arcSegment.size().width()), __y: startPoint.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			endPoint = { __x: endPoint.__x * (arcSegment.size().height() / arcSegment.size().width()), __y: endPoint.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
		var isCounter = arcSegment.sweepDirection() == $.ig.SweepDirection.prototype.counterclockwise;
		var center = $.ig.GeometryUtil.prototype.getCenterFromArcSegment(startPoint, endPoint, arcSegment.size().height(), isCounter, arcSegment.isLargeArc());
		var startAngle = Math.atan2(startPoint.__y - center.__y, startPoint.__x - center.__x);
		var endAngle = Math.atan2(endPoint.__y - center.__y, endPoint.__x - center.__x);
		var lessThan180 = (Math.abs(endAngle - startAngle) < Math.PI);
		if (arcSegment.isLargeArc() == lessThan180) {
			if (startAngle < endAngle) {
				startAngle += 2 * Math.PI;
			} else {
				endAngle += 2 * Math.PI;
			}
		}
		if (!$.ig.util.isNaN(center.__x) && !$.ig.util.isNaN(center.__y) && !$.ig.util.isNaN(arcSegment.size().height()) && !$.ig.util.isNaN(startAngle) && !$.ig.util.isNaN(endAngle)) {
			this.__context.arc(center.__x, center.__y, arcSegment.size().height(), startAngle, endAngle, isCounter);
			if (this.__trackBounds) {
				this.updateBoundsFromArc(center, startAngle, endAngle, arcSegment.size().height(), isCounter);
			}
		}
		this.__lastSegmentPoint = arcSegment.point();
		if (arcSegment.size().width() != arcSegment.size().height()) {
			this.__context.restore();
		}
	}
	,
	renderLineSegment: function (lineSegment) {
		var x = lineSegment.point().__x;
		var y = lineSegment.point().__y;
		this.__context.lineTo(x, y);
		this.__lastSegmentPoint = lineSegment.point();
		if (this.__trackBounds) {
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			this.__minX = x < currMinX ? x : currMinX;
			this.__minY = y < currMinY ? y : currMinY;
			this.__maxX = x > currMaxX ? x : currMaxX;
			this.__maxY = y > currMaxY ? y : currMaxY;
			this.__validBounds = true;
		}
	}
	,
	renderPolyLineSegment: function (polyLineSegment) {
		var points = polyLineSegment.__points;
		var count = points.count();
		if (this.__trackBounds) {
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var x;
			var y;
			for (var i = 0; i < count; i++) {
				x = points.__inner[i].__x;
				y = points.__inner[i].__y;
				this.__context.lineTo(x, y);
				currMinX = x < currMinX ? x : currMinX;
				currMinY = y < currMinY ? y : currMinY;
				currMaxX = x > currMaxX ? x : currMaxX;
				currMaxY = y > currMaxY ? y : currMaxY;
			}
			this.__minX = currMinX;
			this.__minY = currMinY;
			this.__maxX = currMaxX;
			this.__maxY = currMaxY;
			this.__validBounds = true;
		} else {
			for (var i1 = 0; i1 < count; i1++) {
				this.__context.lineTo(points.__inner[i1].__x, points.__inner[i1].__y);
			}
		}
		this.__lastSegmentPoint = polyLineSegment.__points.__inner[count - 1];
	}
	,
	renderRectangleGeometry: function (rectangleGeometry) {
		this.__context.rect(rectangleGeometry.rect().left(), rectangleGeometry.rect().top(), rectangleGeometry.rect().width(), rectangleGeometry.rect().height());
		if (this.__trackBounds) {
			var rect = rectangleGeometry.rect();
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var minX = rect.left();
			var maxX = rect.right();
			var minY = rect.top();
			var maxY = rect.bottom();
			this.__minX = minX < currMinX ? minX : currMinX;
			this.__minY = minY < currMinY ? minY : currMinY;
			this.__maxX = maxX > currMaxX ? maxX : currMaxX;
			this.__maxY = maxY > currMaxY ? maxY : currMaxY;
			this.__validBounds = true;
		}
	}
	,
	renderLineGeometry: function (lineGeometry) {
		this.__context.moveTo(lineGeometry.startPoint().__x, lineGeometry.startPoint().__y);
		this.__context.lineTo(lineGeometry.endPoint().__x, lineGeometry.endPoint().__y);
		if (this.__trackBounds) {
			var p1 = lineGeometry.startPoint();
			var p2 = lineGeometry.endPoint();
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var minX = p1.__x < p2.__x ? p1.__x : p2.__x;
			var maxX = p1.__x > p2.__x ? p1.__x : p2.__x;
			var minY = p1.__y < p2.__y ? p1.__y : p2.__y;
			var maxY = p1.__y > p2.__y ? p1.__y : p2.__y;
			this.__minX = minX < currMinX ? minX : currMinX;
			this.__minY = minY < currMinY ? minY : currMinY;
			this.__maxX = maxX > currMaxX ? maxX : currMaxX;
			this.__maxY = maxY > currMaxY ? maxY : currMaxY;
			this.__validBounds = true;
		}
	}
	,
	renderTextBlock: function (tb) {
		if (tb.__visibility == $.ig.Visibility.prototype.visible) {
			if (tb.__opacity < 1 || this.__globalAlpha < 1) {
				this.__context.globalAlpha = (tb.__opacity * this.__globalAlpha);
			}
			this.__context.fillStyle = tb.fill().__fill;
			this.__context.textBaseline = "top";
			this.__context.fillText(tb.text(), tb.canvasLeft(), tb.canvasTop());
			if (tb.__opacity < 1 || this.__globalAlpha < 1) {
				this.__context.globalAlpha = 1;
			}
		}
	}
	,
	renderTextBlockInRect: function (tb, rect, lineHeight) {
		if (tb.__visibility == $.ig.Visibility.prototype.visible) {
			if (tb.__opacity < 1 || this.__globalAlpha < 1) {
				this.__context.globalAlpha = (tb.__opacity * this.__globalAlpha);
			}
			var x = rect.left() + rect.width() / 2;
			var words = tb.text().split(' ');
			this.__context.fillStyle = tb.fill().__fill;
			this.__context.textBaseline = "top";
			this.__context.textAlign = "center";
			var maxWidth = rect.width();
			var maxHeight = rect.height();
			var currentY = rect.top();
			var line = "";
			for (var i = 0; i < words.length; i++) {
				var currentLine = line + words[i];
				var textMetrics = this.__context.measureText(currentLine);
				if (textMetrics.width > maxWidth) {
					this.__context.fillText(line, x, currentY);
					line = "";
					currentY = currentY + lineHeight;
				}
				line = line + words[i] + " ";
			}
			this.__context.fillText(line, x, currentY);
			if (tb.__opacity < 1 || this.__globalAlpha < 1) {
				this.__context.globalAlpha = 1;
			}
		}
	}
	,
	renderPolygon: function (polygon) {
		if (polygon.points() == null || polygon.points().count() < 1) {
			return;
		}
		this.__context.beginPath();
		this.__context.globalAlpha = (polygon.__opacity * this.__globalAlpha);
		var points = polygon.points();
		var startPoint = points.__inner[0];
		var fill = polygon.__fill;
		var stroke = polygon.__stroke;
		this.__trackBounds = (fill != null && fill._isGradient) || (stroke != null && stroke._isGradient);
		if (this.__trackBounds) {
			var minX = 1.7976931348623157E+308;
			var maxX = -1.7976931348623157E+308;
			var minY = 1.7976931348623157E+308;
			var maxY = -1.7976931348623157E+308;
			var x = startPoint.__x;
			var y = startPoint.__y;
			minX = x < minX ? x : minX;
			minY = y < minY ? y : minY;
			maxX = x > maxX ? x : maxX;
			maxY = y > maxY ? y : maxY;
			this.__context.moveTo(x, y);
			for (var i = 1; i < points.count(); i++) {
				x = points.__inner[i].__x;
				y = points.__inner[i].__y;
				this.__context.lineTo(x, y);
				minX = x < minX ? x : minX;
				minY = y < minY ? y : minY;
				maxX = x > maxX ? x : maxX;
				maxY = y > maxY ? y : maxY;
			}
			this.__context.closePath();
			this.__minX = minX;
			this.__minY = minY;
			this.__maxX = maxX;
			this.__maxY = maxY;
		} else {
			this.__context.moveTo(startPoint.__x, startPoint.__y);
			for (var i1 = 1; i1 < points.count(); i1++) {
				this.__context.lineTo(points.__inner[i1].__x, points.__inner[i1].__y);
			}
			this.__context.closePath();
		}
		if (fill != null) {
			if (fill._isGradient) {
				this.__context.fillStyle = this.createGradient1(fill, this.__minX, this.__minY, this.__maxX, this.__maxY, polygon.strokeThickness(), false);
			} else {
				this.__context.fillStyle = fill.__fill;
			}
			this.__context.fill();
		}
		if (stroke != null) {
			if (stroke._isGradient) {
				this.__context.strokeStyle = this.createGradient1(stroke, this.__minX, this.__minY, this.__maxX, this.__maxY, polygon.strokeThickness(), true);
			} else {
				this.__context.strokeStyle = stroke.__fill;
			}
			this.__context.lineWidth = polygon.strokeThickness();
			this.__context.stroke();
		}
		this.__context.globalAlpha = 1;
	}
	,
	renderPolyline: function (polyline) {
		if (polyline.points() == null || polyline.points().count() < 1) {
			return;
		}
		this.__context.beginPath();
		this.__context.globalAlpha = (polyline.__opacity * this.__globalAlpha);
		var points = polyline.points();
		var startPoint = points.__inner[0];
		var fill = polyline.__fill;
		var stroke = polyline.__stroke;
		this.__trackBounds = (fill != null && fill._isGradient) || (stroke != null && stroke._isGradient);
		if (this.__trackBounds) {
			var minX = 1.7976931348623157E+308;
			var maxX = -1.7976931348623157E+308;
			var minY = 1.7976931348623157E+308;
			var maxY = -1.7976931348623157E+308;
			var x = startPoint.__x;
			var y = startPoint.__y;
			minX = x < minX ? x : minX;
			minY = y < minY ? y : minY;
			maxX = x > maxX ? x : maxX;
			maxY = y > maxY ? y : maxY;
			this.__context.moveTo(x, y);
			for (var i = 1; i < points.count(); i++) {
				x = points.__inner[i].__x;
				y = points.__inner[i].__y;
				this.__context.lineTo(x, y);
				minX = x < minX ? x : minX;
				minY = y < minY ? y : minY;
				maxX = x > maxX ? x : maxX;
				maxY = y > maxY ? y : maxY;
			}
			this.__minX = minX;
			this.__minY = minY;
			this.__maxX = maxX;
			this.__maxY = maxY;
		} else {
			this.__context.moveTo(startPoint.__x, startPoint.__y);
			for (var i1 = 1; i1 < points.count(); i1++) {
				this.__context.lineTo(points.__inner[i1].__x, points.__inner[i1].__y);
			}
		}
		if (fill != null) {
			if (fill._isGradient) {
				this.__context.fillStyle = this.createGradient1(fill, this.__minX, this.__minY, this.__maxX, this.__maxY, polyline.strokeThickness(), false);
			} else {
				this.__context.fillStyle = fill.__fill;
			}
			this.__context.fill();
		}
		if (stroke != null) {
			if (stroke._isGradient) {
				this.__context.strokeStyle = this.createGradient1(stroke, this.__minX, this.__minY, this.__maxX, this.__maxY, polyline.strokeThickness(), true);
			} else {
				this.__context.strokeStyle = stroke.__fill;
			}
			this.__context.lineWidth = polyline.strokeThickness();
			this.__context.stroke();
		}
		this.__context.globalAlpha = 1;
	}
	,
	renderContentControl: function (renderInfo, marker) {
		if (marker.__visibility == $.ig.Visibility.prototype.collapsed) {
			return;
		}
		if ((marker.__opacity != 1 || this.__globalAlpha != 1) && !renderInfo.isHitTestRender) {
			this.__context.globalAlpha = (marker.__opacity * this.__globalAlpha);
		}
		var template = marker.contentTemplate();
		if (template != null && template.render() != null) {
			renderInfo.context = this.__context;
			renderInfo.xPosition = marker.canvasLeft();
			renderInfo.yPosition = marker.canvasTop();
			renderInfo.data = marker.content();
			template.render()(renderInfo);
		}
		this.__context.globalAlpha = 1;
	}
	,
	applyTransform: function (transform) {
		if ($.ig.util.cast($.ig.TransformGroup.prototype.$type, transform) !== null) {
			var tg = transform;
			for (var i = tg.children().count() - 1; i >= 0; i--) {
				var tran = tg.children().__inner[i];
				this.applyTransform(tran);
			}
		} else if ($.ig.util.cast($.ig.TranslateTransform.prototype.$type, transform) !== null) {
			var trans = transform;
			this.__context.translate(trans.x(), trans.y());
		} else if ($.ig.util.cast($.ig.RotateTransform.prototype.$type, transform) !== null) {
			var rot = transform;
			var angle = rot.angle() * Math.PI / 180;
			var x = Math.cos(angle);
			var y = Math.sin(angle);
			var offsetX = rot.centerX() * (1 - x) + rot.centerY() * y;
			var offsetY = rot.centerY() * (1 - x) - rot.centerX() * y;
			this.__context.transform(x, y, y * -1, x, offsetX, offsetY);
		} else if ($.ig.util.cast($.ig.ScaleTransform.prototype.$type, transform) !== null) {
			var scale = transform;
			this.__context.transform(scale.scaleX(), 0, 0, scale.scaleY(), scale.centerX() - scale.scaleX() * scale.centerX(), scale.centerY() - scale.scaleY() * scale.centerY());
		}
	}
	,
	renderLine: function (line) {
		if (line.__visibility != $.ig.Visibility.prototype.visible) {
			return;
		}
		this.__context.beginPath();
		this.__context.globalAlpha = (line.__opacity * this.__globalAlpha);
		this.__context.moveTo(line.x1(), line.y1());
		this.__context.lineTo(line.x2(), line.y2());
		if (this.__trackBounds) {
			this.__minX = 1.7976931348623157E+308;
			this.__maxX = -1.7976931348623157E+308;
			this.__minY = 1.7976931348623157E+308;
			this.__maxY = -1.7976931348623157E+308;
			var currMinX = this.__minX;
			var currMinY = this.__minY;
			var currMaxX = this.__maxX;
			var currMaxY = this.__maxY;
			var minX = line.x1() < line.x2() ? line.x1() : line.x2();
			var maxX = line.x1() > line.x2() ? line.x1() : line.x2();
			var minY = line.y1() < line.y2() ? line.y1() : line.y2();
			var maxY = line.y1() > line.y2() ? line.y1() : line.y2();
			this.__minX = minX < currMinX ? minX : currMinX;
			this.__minY = minY < currMinY ? minY : currMinY;
			this.__maxX = maxX > currMaxX ? maxX : currMaxX;
			this.__maxY = maxY > currMaxY ? maxY : currMaxY;
		}
		var fill = line.__fill;
		var stroke = line.__stroke;
		if (fill != null) {
			if (fill._isGradient) {
				this.__context.fillStyle = this.createGradient1(fill, this.__minX, this.__minY, this.__maxX, this.__maxX, line.strokeThickness(), false);
			} else {
				this.__context.fillStyle = fill.__fill;
			}
			this.__context.fill();
		}
		if (stroke != null) {
			if (stroke._isGradient) {
				this.__context.strokeStyle = this.createGradient1(stroke, this.__minX, this.__minY, this.__maxX, this.__maxX, line.strokeThickness(), true);
			} else {
				this.__context.strokeStyle = line.__stroke.__fill;
			}
			this.__context.lineWidth = line.strokeThickness();
			this.__context.stroke();
		}
		this.__context.globalAlpha = 1;
	}
	,
	setRectangleClip: function (rect) {
		this.__context.beginPath();
		this.__context.rect(rect.left(), rect.top(), rect.width(), rect.height());
		this.__context.clip();
	}
	,
	save: function () {
		this.__context.save();
	}
	,
	restore: function () {
		this.__context.restore();
	}
	,
	scale: function (x, y) {
		this.__context.scale(x, y);
	}
	,
	translate: function (x, y) {
		this.__context.translate(x, y);
	}
	,
	clearRectangle: function (left, top, width, height) {
		this.__context.clearRect(left, top, width, height);
	}
	,
	drawImage: function (image, opacity, left, top, width, height) {
		if (opacity != 1) {
			this.__context.globalAlpha = (opacity * this.__globalAlpha);
		}
		this.__context.drawImage(image, left, top, width, height);
		if (opacity != 1) {
			this.__context.globalAlpha = 1;
		}
	}
	,
	drawImage1: function (image, opacity, sourceLeft, sourceTop, sourceWidth, sourceHeight, left, top, width, height) {
		if (opacity != 1) {
			this.__context.globalAlpha = (opacity * this.__globalAlpha);
		}
		this.__context.drawImage(image, sourceLeft, sourceTop, sourceWidth, sourceHeight, left, top, width, height);
		if (opacity != 1) {
			this.__context.globalAlpha = 1;
		}
	}
	,
	getPixelAt: function (x, y) {
		var data = this.__context.getImageData(x, y, 1, 1);
		var ret = new Array(4);
		ret[0] = data.data[0];
		ret[1] = data.data[1];
		ret[2] = data.data[2];
		ret[3] = data.data[3];
		return ret;
	}
	,
	getFont: function () {
		return this.__context.font;
	}
	,
	getFontInfo: function () {
		return this.__fontInfo;
	}
	,
	setFont: function (font) {
		if (this.__context.font != font) {
			this.__context.font = font;
		}
	}
	,
	__fontInfo: null,
	setFontInfo: function (font) {
		this.__fontInfo = font;
		if (this.__context.font != font.fontString()) {
			this.__context.font = font.fontString();
		}
	}
	,
	measureTextWidth: function (text) {
		var metrics = this.__context.measureText(text);
		return metrics.width;
	}
	,
	__globalAlpha: 0,
	setOpacity: function (p) {
		this.__globalAlpha = p;
	}
	,
	applyStyle: function (shape_, style_) {
		if (style_ == null) {
			return;
		}
		var fillColor_ = null;
		var strokeColor_ = null;
		var strokeThickness_ = NaN;
		var opacity_ = NaN;
		if (style_.fill) { fillColor_ = style_.fill };
		if (style_.stroke) { strokeColor_ = style_.stroke };
		if (style_.strokeThickness) { strokeThickness_ = style_.strokeThickness };
		if (style_.opacity) { opacity_ = style_.opacity };
		if (fillColor_ != null) {
			shape_.__fill = (function () {
				var $ret = new $.ig.Brush();
				$ret.fill(fillColor_);
				return $ret;
			}());
		}
		if (strokeColor_ != null) {
			shape_.__stroke = (function () {
				var $ret = new $.ig.Brush();
				$ret.fill(strokeColor_);
				return $ret;
			}());
		}
		if (!$.ig.util.isNaN(strokeThickness_)) {
			shape_.strokeThickness(strokeThickness_);
		}
		if (!$.ig.util.isNaN(opacity_)) {
			shape_.__opacity = opacity_;
		}
	}
	,
	enableDropShadow: function (color, blur, offsetX, offsetY) {
		this.__context.shadowColor = color;
		this.__context.shadowBlur = blur;
		this.__context.shadowOffsetX = offsetX;
		this.__context.shadowOffsetY = offsetY;
	}
	,
	disableDropShadow: function () {
		this.__context.shadowColor = "rgba(0,0,0,0)";
		this.__context.shadowBlur = 0;
		this.__context.shadowOffsetX = 0;
		this.__context.shadowOffsetY = 0;
	}
	,
	$type: new $.ig.Type('CanvasViewRenderer', $.ig.Object.prototype.$type, [$.ig.IRenderer.prototype.$type])
}, true);

$.ig.util.defType('CssGradientUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	isGradient: function (value) {
		return value.contains("linear-gradient") || value.contains("radial-gradient");
	}
	,
	brushFromGradientString: function (value) {
		var regex = /hsl\([\s\S]+?\)[\s\S]*?[,\)]|rgba?\([\s\S]+?\)[\s\S]*?[,\)]|[^\(\)]*?[,\)]/gim, percentRegex = /\s*\d*%\s*$/, trimStartRegex = /^\s\s*/, trimEndRegex = /\s\s*$/, trimEndCharactersRegex = /[,\)]?$/;
		var match;
		var angle, i = 1, j = 0, length, offsetIndex;
		var hasUnsetOffsets = false;
		var stops;
		var matches = value.match(regex);
		if (matches == null || matches.length <= 1) {
			return null;
		}
		var b = new $.ig.LinearGradientBrush();
		length = matches.length;
		match = matches[0];
		if (match.contains("to") || match.contains("deg")) {
			angle = $.ig.CssGradientUtil.prototype.angleFromString(match);
			b._useCustomDirection = true;
			var points = $.ig.CssGradientUtil.prototype.calculatePointsFromAngle(angle);
			b._startX = points[0].__x;
			b._startY = points[0].__y;
			b._endX = points[1].__x;
			b._endY = points[1].__y;
			stops = new Array(length - 1);
		} else {
			stops = new Array(length);
			i = 0;
		}
		for (; i < length; i++) {
			var stop = new $.ig.GradientStop();
			match = matches[i];
			match = match.replace(trimStartRegex, "").replace(trimEndRegex, "").replace(trimEndCharactersRegex, "");
			offsetIndex = match.search(percentRegex);
			if (offsetIndex != -1) {
				stop.__fill = match.substr(0, offsetIndex);
				stop._offset = parseFloat(match.substr(offsetIndex + 1)) / 100;
			} else {
				stop.__fill = match;
				stop._offset = -1;
				hasUnsetOffsets = true;
			}
			stops[j] = stop;
			j++;
		}
		if (hasUnsetOffsets) {
			if (stops[0]._offset == -1) {
				stops[0]._offset = 0;
			}
			if (stops[stops.length - 1]._offset == -1) {
				stops[stops.length - 1]._offset = 1;
			}
			$.ig.CssGradientUtil.prototype.fixUnsetOffsets(stops);
			b._gradientStops = stops;
		}
		return b;
	}
	,
	fixUnsetOffsets: function (stops) {
		var i, j, k, offsetRange, maxOffset = -1, lastSetOffsetIndex = -1, lastSetOffset = 0;
		var hasUnsetOffsets = false;
		for (i = lastSetOffsetIndex + 1; i < stops.length; i++) {
			var stop = stops[i];
			if (stop._offset != -1) {
				maxOffset = Math.max(maxOffset, stop._offset);
				stop._offset = maxOffset;
				if (hasUnsetOffsets) {
					k = 1;
					offsetRange = $.ig.intDivide((maxOffset - lastSetOffset), (i - lastSetOffsetIndex));
					for (j = lastSetOffsetIndex + 1; j < i; j++) {
						stops[j]._offset = lastSetOffset + offsetRange * k;
						k++;
					}
					hasUnsetOffsets = false;
				}
				lastSetOffsetIndex = i;
				lastSetOffset = maxOffset;
			} else {
				hasUnsetOffsets = true;
			}
		}
	}
	,
	angleFromString: function (value) {
		var toTopRegex = /to\s*top\s*/i, toRightTopRegex = /to\s*right\s*top\s*/i, toRightRegex = /to\s*right\s*/i, toRightBottomRegex = /to\s*right\s*bottom\s*/i, toBottomRegex = /to\s*bottom\s*/i, toLeftBottomRegex = /to\s*left\s*bottom\s*/i, toLeftRegex = /to\s*left\s*/i, toLeftTopRegex = /to\s*left\s*top\s*/i;
		if (value.contains("deg")) {
			return parseFloat(value);
		}
		if (toTopRegex.test(value)) {
			return 0;
		}
		if (toRightTopRegex.test(value)) {
			return 45;
		}
		if (toRightRegex.test(value)) {
			return 90;
		}
		if (toRightBottomRegex.test(value)) {
			return 135;
		}
		if (toBottomRegex.test(value)) {
			return 180;
		}
		if (toLeftBottomRegex.test(value)) {
			return 225;
		}
		if (toLeftRegex.test(value)) {
			return 270;
		}
		return toLeftTopRegex.test(value) ? 315 : 180;
	}
	,
	calculatePointsFromAngle: function (inputAngle) {
		var points = new Array(2);
		var p1 = new $.ig.Point(0);
		var p2 = new $.ig.Point(0);
		var angle = $.ig.GeometryUtil.prototype.simplifyAngle(inputAngle);
		if (angle >= 0 && angle <= 45) {
			var tan = Math.tan($.ig.MathUtil.prototype.radians(angle));
			p1.__x = 0.5 - 0.5 * tan;
			p1.__y = 1;
			p2.__x = 0.5 + 0.5 * tan;
			p2.__y = 0;
		} else if (angle > 180 && angle <= 225) {
			var tan1 = Math.tan($.ig.MathUtil.prototype.radians(angle - 180));
			p1.__x = 0.5 + 0.5 * tan1;
			p1.__y = 0;
			p2.__x = 0.5 - 0.5 * tan1;
			p2.__y = 1;
		} else if (angle > 135 && angle <= 180) {
			var tan2 = Math.tan($.ig.MathUtil.prototype.radians(180 - angle));
			p1.__x = 0.5 - 0.5 * tan2;
			p1.__y = 0;
			p2.__x = 0.5 + 0.5 * tan2;
			p2.__y = 1;
		} else if (angle > 315 && angle < 360) {
			var tan3 = Math.tan($.ig.MathUtil.prototype.radians(360 - angle));
			p1.__x = 0.5 + 0.5 * tan3;
			p1.__y = 1;
			p2.__x = 0.5 - 0.5 * tan3;
			p2.__y = 0;
		} else if (angle > 45 && angle <= 90) {
			var tan4 = Math.tan($.ig.MathUtil.prototype.radians(90 - angle));
			p2.__y = 0.5 - 0.5 * tan4;
			p2.__x = 1;
			p1.__y = 0.5 + 0.5 * tan4;
			p1.__x = 0;
		} else if (angle > 90 && angle <= 135) {
			var tan5 = Math.tan($.ig.MathUtil.prototype.radians(angle - 90));
			p2.__y = 0.5 + 0.5 * tan5;
			p2.__x = 1;
			p1.__y = 0.5 - 0.5 * tan5;
			p1.__x = 0;
		} else if (angle > 225 && angle <= 270) {
			var tan6 = Math.tan($.ig.MathUtil.prototype.radians(270 - angle));
			p1.__y = 0.5 - 0.5 * tan6;
			p1.__x = 1;
			p2.__y = 0.5 + 0.5 * tan6;
			p2.__x = 0;
		} else if (angle > 270 && angle <= 315) {
			var tan7 = Math.tan($.ig.MathUtil.prototype.radians(angle - 270));
			p1.__y = 0.5 + 0.5 * tan7;
			p1.__x = 1;
			p2.__y = 0.5 - 0.5 * tan7;
			p2.__x = 0;
		}
		points[0] = p1;
		points[1] = p2;
		return points;
	}
	,
	$type: new $.ig.Type('CssGradientUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FontUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getCurrentFontHeight: function (font) {
		var tempSpan_ = $("<span>M</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		var fontString = null;
		if (font != null) {
			fontString = font.fontString();
		}
		tempSpan_.css("font", fontString);
		var offset_ = tempSpan_.attr("offsetHeight");
		if (isNaN(offset_)) { offset_ = tempSpan_[0].offsetHeight; };
		tempSpan_.remove();
		return parseInt(offset_);
	}
	,
	measureStringHeight: function (text, font) {
		var tempSpan_ = $("<span>" + text + "</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		tempSpan_.css("font", font.fontString());
		var height = tempSpan_.css("height");
		tempSpan_.remove();
		var h = parseFloat(height);
		return h;
	}
	,
	measureStringWidth1: function (text, font, context) {
		context.save();
		context.setFont(font);
		var width = context.measureTextWidth(text);
		context.restore();
		return width;
	}
	,
	measureStringWidth: function (text, font, context) {
		context.save();
		context.setFontInfo(font);
		var width = context.measureTextWidth(text);
		context.restore();
		return width;
	}
	,
	measureMultilineStringHeight: function (text, font, width) {
		var tempSpan_ = $("<div style=\"width:" + width + "\"><span>" + text + "</span></div>");
		var doc = $('body');
		doc.append(tempSpan_);
		tempSpan_.css("font", font.fontString());
		var height = tempSpan_.css("height");
		tempSpan_.remove();
		var h = parseFloat(height);
		return h;
	}
	,
	getFontSize: function (font) {
		var tempSpan_ = $("<span>M</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		tempSpan_.css("font", font.fontString());
		var fontSize = tempSpan_.css("font-size");
		tempSpan_.remove();
		return parseFloat(fontSize);
	}
	,
	getFontWithNewFontSize: function (font, fontSize) {
		var tempFont = font.copyFontInfo();
		tempFont.fontSize(fontSize);
		tempFont.fontString(fontSize + "px " + font.fontFamily());
		return tempFont;
	}
	,
	getFontInfoFromString: function (font) {
		var returnFont = new $.ig.FontInfo();
		var tempSpan_ = $("<span>M</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		tempSpan_.css("font", font);
		returnFont = $.ig.FontUtil.prototype.getFont(tempSpan_);
		tempSpan_.remove();
		return returnFont;
	}
	,
	getDefaultFont: function () {
		var newFont = new $.ig.FontInfo();
		var tempSpan_ = $("<span>M</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		newFont = $.ig.FontUtil.prototype.getFont(tempSpan_);
		tempSpan_.remove();
		return newFont;
	}
	,
	getFont: function (container) {
		var font = String.empty();
		var fontStyle = container.css("font-style");
		var fontVariant = container.css("font-variant");
		var fontWeight = container.css("font-weight");
		var fontSize = container.css("font-size");
		var lineHeight = container.css("line-height");
		var fontFamily = container.css("font-family");
		var first = true;
		if (fontStyle.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontStyle;
		}
		if (fontVariant.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontVariant;
		}
		if (fontWeight.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontWeight;
		}
		if (fontSize.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontSize;
		}
		if (lineHeight.length > 0) {
			if (!first) {
				font += "/";
			} else {
				first = false;
			}
			font += lineHeight;
		}
		if (fontFamily.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontFamily;
		}
		var info = new $.ig.FontInfo();
		info.fontFamily(fontFamily);
		info.fontSize(parseFloat(fontSize));
		info.fontStretch("Normal");
		info.fontStyle(fontStyle);
		info.fontVariant(fontVariant);
		info.fontWeight(fontWeight);
		info.fontString(font);
		return info;
	}
	,
	updateFontString: function (fontInfo) {
		var font = String.empty();
		var fontStyle = fontInfo.fontStyle();
		var fontVariant = fontInfo.fontVariant();
		var fontWeight = fontInfo.fontWeight();
		var fontSize = fontInfo.fontSize().toString();
		var lineHeight = "normal";
		var fontFamily = fontInfo.fontFamily();
		var first = true;
		if (fontStyle.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontStyle;
		}
		if (fontVariant.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontVariant;
		}
		if (fontWeight.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontWeight;
		}
		if (fontSize.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontSize + "px";
		}
		if (lineHeight.length > 0) {
			if (!first) {
				font += "/";
			} else {
				first = false;
			}
			font += lineHeight;
		}
		if (fontFamily.length > 0) {
			if (!first) {
				font += " ";
			} else {
				first = false;
			}
			font += fontFamily;
		}
		fontInfo.fontString(font);
	}
	,
	getFontInfo: function (tb, fontDescriptor) {
		if (fontDescriptor == null) {
			var fi = new $.ig.FontInfo();
			var tempDiv = $("<div></div>");
			var fontStyle = tempDiv.css("font-style");
			var fontVariant = tempDiv.css("font-variant");
			var fontWeight = tempDiv.css("font-weight");
			var fontSize = tempDiv.css("font-size");
			var lineHeight = tempDiv.css("line-height");
			var fontFamily = tempDiv.css("font-family");
			tempDiv = null;
			fi.fontFamily($.ig.util.replace(fontFamily, "'", ""));
			fi.fontStyle($.ig.FontUtil.prototype.toUpperFirst(fontStyle));
			fi.fontWeight($.ig.FontUtil.prototype.toUpperFirst(fontWeight.toString()));
			fi.fontVariant(fontVariant);
			fi.lineHeight(parseFloat(lineHeight));
			fi.fontSize(parseFloat(fontSize));
			return fi;
		}
		return fontDescriptor;
	}
	,
	toUpperFirst: function (label) {
		if (String.isNullOrEmpty(label)) {
			return String.empty();
		}
		var result = label.substr(0, 1).toUpperCase();
		if (label.length > 1) {
			result += label.substr(1);
		}
		return result;
	}
	,
	toFontInfo: function (font) {
		if (font == null) {
			return null;
		}
		return $.ig.FontUtil.prototype.getFontInfoFromString(font);
	}
	,
	splitString: function (text, delimiters) {
		return $.ig.util.stringSplit(text, delimiters, $.ig.StringSplitOptions.prototype.none);
	}
	,
	charsEqual: function (text1, index1, chars, index2) {
		return text1.charAt(index1).equals(chars[index2]);
	}
	,
	$type: new $.ig.Type('FontUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FontInfo', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.fontSize(NaN);
		this.lineHeight(NaN);
	},
	_fontStyle: null,
	fontStyle: function (value) {
		if (arguments.length === 1) {
			this._fontStyle = value;
			return value;
		} else {
			return this._fontStyle;
		}
	}
	,
	_fontVariant: null,
	fontVariant: function (value) {
		if (arguments.length === 1) {
			this._fontVariant = value;
			return value;
		} else {
			return this._fontVariant;
		}
	}
	,
	_fontWeight: null,
	fontWeight: function (value) {
		if (arguments.length === 1) {
			this._fontWeight = value;
			return value;
		} else {
			return this._fontWeight;
		}
	}
	,
	_fontSize: 0,
	fontSize: function (value) {
		if (arguments.length === 1) {
			this._fontSize = value;
			return value;
		} else {
			return this._fontSize;
		}
	}
	,
	_lineHeight: 0,
	lineHeight: function (value) {
		if (arguments.length === 1) {
			this._lineHeight = value;
			return value;
		} else {
			return this._lineHeight;
		}
	}
	,
	_fontFamily: null,
	fontFamily: function (value) {
		if (arguments.length === 1) {
			this._fontFamily = value;
			return value;
		} else {
			return this._fontFamily;
		}
	}
	,
	_fontStretch: null,
	fontStretch: function (value) {
		if (arguments.length === 1) {
			this._fontStretch = value;
			return value;
		} else {
			return this._fontStretch;
		}
	}
	,
	_fontString: null,
	fontString: function (value) {
		if (arguments.length === 1) {
			this._fontString = value;
			return value;
		} else {
			return this._fontString;
		}
	}
	,
	copyFontInfo: function () {
		var $self = this;
		return (function () {
			var $ret = new $.ig.FontInfo();
			$ret.fontStyle($self.fontStyle());
			$ret.fontVariant($self.fontVariant());
			$ret.fontWeight($self.fontWeight());
			$ret.fontSize($self.fontSize());
			$ret.lineHeight($self.lineHeight());
			$ret.fontFamily($self.fontFamily());
			$ret.fontStretch($self.fontStretch());
			$ret.fontString($self.fontString());
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('FontInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RenderingContext', 'Object', {
	__renderer: null,
	getUnderlyingContext: function () {
		if (this.__renderer == null) {
			return null;
		}
		return this.__renderer.getUnderlyingContext();
	}
	,
	init: function (renderer, data) {
		this.__renderer = null;
		$.ig.Object.prototype.init.call(this);
		this.__renderer = renderer;
		if (this.__renderer != null) {
			this.__renderer.data(data);
		}
	},
	shouldRender: function () {
		if (this.__renderer == null) {
			return false;
		}
		return true;
	}
	,
	renderRectangle: function (rectangle) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderRectangle(rectangle);
	}
	,
	renderPath: function (path) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderPath(path);
	}
	,
	renderGeometry: function (geometry) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderGeometry(geometry);
	}
	,
	renderTextBlock: function (tb) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderTextBlock(tb);
	}
	,
	renderTextBlockInRect: function (tb, rect, lineHeight) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderTextBlockInRect(tb, rect, lineHeight);
	}
	,
	renderPolygon: function (polygon) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderPolygon(polygon);
	}
	,
	renderPolyline: function (polyline) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderPolyline(polyline);
	}
	,
	renderContentControl: function (info, marker) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderContentControl(info, marker);
	}
	,
	applyTransform: function (transform) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.applyTransform(transform);
	}
	,
	renderLine: function (line) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.renderLine(line);
	}
	,
	save: function () {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.save();
	}
	,
	restore: function () {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.restore();
	}
	,
	setRectangleClip: function (clipRectangle) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.setRectangleClip(clipRectangle);
	}
	,
	scale: function (x, y) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.scale(x, y);
	}
	,
	translate: function (x, y) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.translate(x, y);
	}
	,
	clearRectangle: function (left, top, width, height) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.clearRectangle(left, top, width, height);
	}
	,
	drawImage1: function (image, opacity, sourceLeft, sourceTop, sourceWidth, sourceHeight, left, top, width, height) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.drawImage1(image, opacity, sourceLeft, sourceTop, sourceWidth, sourceHeight, left, top, width, height);
	}
	,
	drawImage: function (image, opacity, left, top, width, height) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.drawImage(image, opacity, left, top, width, height);
	}
	,
	getPixelAt: function (x, y) {
		if (this.__renderer == null) {
			return null;
		}
		return this.__renderer.getPixelAt(x, y);
	}
	,
	getFont: function () {
		if (this.__renderer == null) {
			return null;
		}
		return this.__renderer.getFont();
	}
	,
	getFontInfo: function () {
		if (this.__renderer == null) {
			return null;
		}
		return this.__renderer.getFontInfo();
	}
	,
	setFont: function (font) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.setFont(font);
	}
	,
	setFontInfo: function (fontInfo) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.setFontInfo(fontInfo);
	}
	,
	measureTextWidth: function (text) {
		if (this.__renderer == null) {
			return NaN;
		}
		return this.__renderer.measureTextWidth(text);
	}
	,
	setOpacity: function (p) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.setOpacity(p);
	}
	,
	applyStyle: function (shape, style) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.applyStyle(shape, style);
	}
	,
	enableDropShadow: function (color, blur, offsetX, offsetY) {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.enableDropShadow(color, blur, offsetX, offsetY);
	}
	,
	disableDropShadow: function () {
		if (this.__renderer == null) {
			return;
		}
		this.__renderer.disableDropShadow();
	}
	,
	$type: new $.ig.Type('RenderingContext', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('EasingFunctions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	cubic: function (p) {
		return p * p * p;
	}
	,
	exponential: function (p) {
		return (Math.exp(2 * p) - 1) / (Math.exp(2) - 1);
	}
	,
	circle: function (p) {
		return 1 - Math.sqrt(1 - p * p);
	}
	,
	doIn: function (t, func) {
		var fastTime = t * 2;
		return 0.5 * func(fastTime);
	}
	,
	doOut: function (t, func) {
		var fastTime = (1 - t) * 2;
		var y = 1 - func(fastTime);
		return 0.5 * y + 0.5;
	}
	,
	cubicEase: function (t) {
		if (t < 0.5) {
			return $.ig.EasingFunctions.prototype.doIn(t, $.ig.EasingFunctions.prototype.cubic);
		}
		return $.ig.EasingFunctions.prototype.doOut(t, $.ig.EasingFunctions.prototype.cubic);
	}
	,
	exponentialEase: function (t) {
		if (t < 0.5) {
			return $.ig.EasingFunctions.prototype.doIn(t, $.ig.EasingFunctions.prototype.exponential);
		}
		return $.ig.EasingFunctions.prototype.doOut(t, $.ig.EasingFunctions.prototype.exponential);
	}
	,
	circleEase: function (t) {
		if (t < 0.5) {
			return $.ig.EasingFunctions.prototype.doIn(t, $.ig.EasingFunctions.prototype.circle);
		}
		return $.ig.EasingFunctions.prototype.doOut(t, $.ig.EasingFunctions.prototype.circle);
	}
	,
	$type: new $.ig.Type('EasingFunctions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GeometryUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	simplifyAngle: function (angle) {
		if ($.ig.util.isNaN(angle) || Number.isInfinity(angle)) {
			return angle;
		}
		while (angle > 360) {
			angle -= 360;
		}
		while (angle < 0) {
			angle += 360;
		}
		return angle;
	}
	,
	angleFromSlope: function (slope) {
		return Math.atan(slope);
	}
	,
	slope: function (point1, point2) {
		return (point2.__y - point1.__y) / (point2.__x - point1.__x);
	}
	,
	eccentricity: function (bounds) {
		return 1 - Math.pow(bounds.height() / 2, 2) / Math.pow(bounds.width() / 2, 2);
	}
	,
	pointOnEllipse: function (theta, eccentricity, center, halfHeight, extent) {
		var cos = Math.cos(theta);
		var sin = Math.sin(theta);
		var r = Math.sqrt(halfHeight * halfHeight / (1 - (eccentricity * Math.pow(cos, 2))));
		r *= extent;
		return { __x: r * cos + center.__x, __y: r * sin + center.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	findCenter: function (width, height, exploded, angle, radius) {
		var center;
		if (exploded) {
			center = $.ig.GeometryUtil.prototype.findRadialPoint({ __x: width / 2, __y: height / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, angle, radius);
		} else {
			center = { __x: width / 2, __y: height / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
		return center;
	}
	,
	findRadialPoint: function (center, angle, radius) {
		angle = angle / 180 * Math.PI;
		var y = center.__y + radius * Math.sin(angle);
		var x = center.__x + radius * Math.cos(angle);
		return { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	pointAtDistance: function (p1, p2, distance) {
		var x3 = p2.__x - p1.__x;
		var y3 = p2.__y - p1.__y;
		var length = Math.sqrt(x3 * x3 + y3 * y3);
		x3 = x3 / length * distance;
		y3 = y3 / length * distance;
		return { __x: p1.__x + x3, __y: p1.__y + y3, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	getSegmentLength: function (p1, p2) {
		var a = Math.abs(p2.__x - p1.__x);
		var b = Math.abs(p2.__y - p1.__y);
		return Math.sqrt(a * a + b * b);
	}
	,
	getCenterFromArcSegment: function (startPoint, endPoint, radius, isCounter, isLargeArc) {
		var midway = { __x: (startPoint.__x + endPoint.__x) / 2, __y: (startPoint.__y + endPoint.__y) / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		var vectorX = endPoint.__x - startPoint.__x;
		var vectorY = endPoint.__y - startPoint.__y;
		var vectorLength = Math.sqrt(vectorX * vectorX + vectorY * vectorY);
		var rotatedVectorX = vectorY;
		var rotatedVectorY = vectorX * -1;
		if (isLargeArc == isCounter) {
			rotatedVectorX = vectorY * -1;
			rotatedVectorY = vectorX;
		}
		var maxAbs = Math.max(Math.abs(rotatedVectorX), Math.abs(rotatedVectorY));
		rotatedVectorX = rotatedVectorX / maxAbs;
		rotatedVectorY = rotatedVectorY / maxAbs;
		var rotatedVectorLength = Math.sqrt(rotatedVectorX * rotatedVectorX + rotatedVectorY * rotatedVectorY);
		var normalizedVectorX = rotatedVectorX / rotatedVectorLength;
		var normalizedVectorY = rotatedVectorY / rotatedVectorLength;
		var halfChordLength = vectorLength / 2;
		var distToCenter = Math.sqrt(radius * radius - halfChordLength * halfChordLength);
		if ($.ig.util.isNaN(distToCenter)) {
			distToCenter = 0;
		}
		var centerVectorX = distToCenter * normalizedVectorX;
		var centerVectorY = distToCenter * normalizedVectorY;
		var center = { __x: midway.__x + centerVectorX, __y: midway.__y + centerVectorY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		return center;
	}
	,
	getAngleTo: function (center, toPoint) {
		var radius = Math.sqrt(Math.pow(toPoint.__x - center.__x, 2) + Math.pow(toPoint.__y - center.__y, 2));
		var angle = Math.acos((toPoint.__x - center.__x) / radius);
		if ((toPoint.__y - center.__y) < 0) {
			angle = (2 * Math.PI) - angle;
		}
		return angle;
	}
	,
	getCircleIntersection: function (startPoint, endPoint, circleCenter, circleRadius) {
		var x1 = startPoint.__x - circleCenter.__x;
		var y1 = startPoint.__y - circleCenter.__y;
		var x2 = endPoint.__x - circleCenter.__x;
		var y2 = endPoint.__y - circleCenter.__y;
		var dx = x2 - x1;
		var dy = y2 - y1;
		var dr = Math.sqrt(dx * dx + dy * dy);
		var det = x1 * y2 - x2 * y1;
		var radSquared = circleRadius * circleRadius;
		var drSquared = dr * dr;
		var detSquared = det * det;
		var disc = radSquared * drSquared - detSquared;
		if (disc < 0) {
			return new $.ig.Tuple$2($.ig.Point.prototype.$type, $.ig.Point.prototype.$type, { __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		var signDy = 1;
		if (dy < 0) {
			signDy = -1;
		}
		var interX1 = (det * dy + signDy * dx * Math.sqrt(disc)) / drSquared;
		var interX2 = (det * dy - signDy * dx * Math.sqrt(disc)) / drSquared;
		var interY1 = (-1 * det * dx + Math.abs(dy) * Math.sqrt(disc)) / drSquared;
		var interY2 = (-1 * det * dx - Math.abs(dy) * Math.sqrt(disc)) / drSquared;
		return new $.ig.Tuple$2($.ig.Point.prototype.$type, $.ig.Point.prototype.$type, { __x: interX1 + circleCenter.__x, __y: interY1 + circleCenter.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: interX2 + circleCenter.__x, __y: interY2 + circleCenter.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	isPointNearLineSegment: function (targetPoint, startPoint, endPoint, closeness) {
		var closeness2 = closeness * closeness;
		var x1 = startPoint.__x;
		var y1 = startPoint.__y;
		var x2 = endPoint.__x;
		var y2 = endPoint.__y;
		var x0 = targetPoint.__x;
		var y0 = targetPoint.__y;
		if (y1 == y2) {
			var yClose = Math.abs(y1 - y0) < closeness;
			var xInRange = x0 >= Math.min(x1, x2) - closeness && x0 <= Math.max(x1, x2) + closeness;
			if (yClose && xInRange) {
				return true;
			} else {
				return false;
			}
		}
		if (x1 == x2) {
			var xClose = Math.abs(x1 - x0) < closeness;
			var yInRange = y0 >= Math.min(y1, y2) - closeness && y0 <= Math.max(y1, y2) + closeness;
			if (xClose && yInRange) {
				return true;
			} else {
				return false;
			}
		}
		var dx = x2 - x1;
		var dy = y2 - y1;
		var segDistance2 = dx * dx + dy * dy;
		if (segDistance2 == 0) {
			return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1) < closeness2;
		}
		var t = ((x0 - x1) * dx + (y0 - y1) * dy) / segDistance2;
		t = t < 0 ? 0 : (t > 1 ? 1 : t);
		var xt = x1 + dx * t;
		var yt = y1 + dy * t;
		var dist = (xt - x0) * (xt - x0) + (yt - y0) * (yt - y0);
		if (dist < closeness2) {
			return true;
		}
		return false;
	}
	,
	isPointNearPolyline: function (targetPoint, firstPoint, linePoints, closeness) {
		var startPoint;
		var endPoint;
		var closeness2 = closeness * closeness;
		var lineCount = linePoints.count();
		if (lineCount == 0) {
			return (targetPoint.__x - firstPoint.__x) * (targetPoint.__x - firstPoint.__x) + (targetPoint.__y - firstPoint.__y) * (targetPoint.__y - firstPoint.__y) < closeness2;
		}
		var x0 = targetPoint.__x;
		var y0 = targetPoint.__y;
		for (var i = 0; i < lineCount; i++) {
			if (i == 0) {
				endPoint = linePoints.item(i);
				startPoint = firstPoint;
			} else {
				endPoint = linePoints.item(i);
				startPoint = linePoints.item(i - 1);
			}
			var x1 = startPoint.__x;
			var y1 = startPoint.__y;
			var x2 = endPoint.__x;
			var y2 = endPoint.__y;
			if (y1 == y2) {
				var yClose = Math.abs(y1 - y0) < closeness;
				var xInRange = x0 >= Math.min(x1, x2) - closeness && x0 <= Math.max(x1, x2) + closeness;
				if (yClose && xInRange) {
					return true;
				} else {
					continue;
				}
			}
			if (x1 == x2) {
				var xClose = Math.abs(x1 - x0) < closeness;
				var yInRange = y0 >= Math.min(y1, y2) - closeness && y0 <= Math.max(y1, y2) + closeness;
				if (xClose && yInRange) {
					return true;
				} else {
					continue;
				}
			}
			var dx = x2 - x1;
			var dy = y2 - y1;
			var segDistance2 = dx * dx + dy * dy;
			if (segDistance2 == 0) {
				return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1) < closeness2;
			}
			var t = ((x0 - x1) * dx + (y0 - y1) * dy) / segDistance2;
			t = t < 0 ? 0 : (t > 1 ? 1 : t);
			var xt = x1 + dx * t;
			var yt = y1 + dy * t;
			var dist = (xt - x0) * (xt - x0) + (yt - y0) * (yt - y0);
			if (dist < closeness2) {
				return true;
			}
		}
		return false;
	}
	,
	$type: new $.ig.Type('GeometryUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MathUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	asinh: function (angle) {
		return Math.log(angle + Math.sqrt(angle * angle + 1));
	}
	,
	hypot: function (x, y) {
		return Math.sqrt(x * x + y * y);
	}
	,
	sqr: function (x) {
		return x * x;
	}
	,
	gammaLn: function (x) {
		if (x <= 0) {
			return NaN;
		}
		var cof = [ 57.156235665862923, -59.597960355475493, 14.136097974741746, -0.49191381609762019, 3.3994649984811891E-05, 4.6523628927048578E-05, -9.8374475304879565E-05, 0.00015808870322491249, -0.00021026444172410488, 0.00021743961811521265, -0.00016431810653676389, 8.441822398385275E-05, -2.6190838401581408E-05, 3.6899182659531625E-06 ];
		var y = x;
		var t = (x + 0.5) * Math.log(x + 5.2421875) - (x + 5.2421875);
		var s = 0.99999999999999711;
		for (var j = 0; j < 14; j++) {
			s += cof[j] / ++y;
		}
		return t + Math.log(2.5066282746310007 * s / x);
	}
	,
	clamp: function (value, minimum, maximum) {
		return Math.min(maximum, Math.max(minimum, value));
	}
	,
	radians: function (degrees) {
		return Math.PI * degrees / 180;
	}
	,
	degrees: function (radians) {
		return 180 * radians / Math.PI;
	}
	,
	noise: function (x, y, z) {
		var X = $.ig.truncate(Math.floor(x)) & 255;
		var Y = $.ig.truncate(Math.floor(y)) & 255;
		var Z = $.ig.truncate(Math.floor(z)) & 255;
		x -= Math.floor(x);
		y -= Math.floor(y);
		z -= Math.floor(z);
		var u = $.ig.MathUtil.prototype.fade(x);
		var v = $.ig.MathUtil.prototype.fade(y);
		var w = $.ig.MathUtil.prototype.fade(z);
		var A = $.ig.MathUtil.prototype._basis[X] + Y;
		var AA = $.ig.MathUtil.prototype._basis[A] + Z;
		var AB = $.ig.MathUtil.prototype._basis[A + 1] + Z;
		var B = $.ig.MathUtil.prototype._basis[X + 1] + Y;
		var BA = $.ig.MathUtil.prototype._basis[B] + Z;
		var BB = $.ig.MathUtil.prototype._basis[B + 1] + Z;
		return $.ig.MathUtil.prototype.lerp(w, $.ig.MathUtil.prototype.lerp(v, $.ig.MathUtil.prototype.lerp(u, $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[AA], x, y, z), $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[BA], x - 1, y, z)), $.ig.MathUtil.prototype.lerp(u, $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[AB], x, y - 1, z), $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[BB], x - 1, y - 1, z))), $.ig.MathUtil.prototype.lerp(v, $.ig.MathUtil.prototype.lerp(u, $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[AA + 1], x, y, z - 1), $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[BA + 1], x - 1, y, z - 1)), $.ig.MathUtil.prototype.lerp(u, $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[AB + 1], x, y - 1, z - 1), $.ig.MathUtil.prototype.grad($.ig.MathUtil.prototype._basis[BB + 1], x - 1, y - 1, z - 1))));
	}
	,
	fade: function (t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}
	,
	lerp: function (t, a, b) {
		return a + t * (b - a);
	}
	,
	grad: function (hash, x, y, z) {
		var h = hash & 15;
		var u = h < 8 ? x : y;
		var v = h < 4 ? y : h == 12 || h == 14 ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}
	,
	niceFloor: function (value) {
		if (value == 0) {
			return 0;
		}
		if (value < 0) {
			return -$.ig.MathUtil.prototype.niceCeiling(-value);
		}
		var expv = $.ig.truncate(Math.floor(Math.log10(value)));
		var f = value / $.ig.MathUtil.prototype.expt(10, expv);
		var nf = f < 2 ? 1 : (f < 5 ? 2 : (f < 10 ? 5 : 10));
		return nf * $.ig.MathUtil.prototype.expt(10, expv);
	}
	,
	niceRound: function (value) {
		if (value == 0) {
			return 0;
		}
		if (value < 0) {
			return -$.ig.MathUtil.prototype.niceRound(-value);
		}
		var expv = $.ig.truncate(Math.floor(Math.log10(value)));
		var f = value / $.ig.MathUtil.prototype.expt(10, expv);
		var nf = f < 1 ? 1 : (f < 3 ? 2 : (f < 7 ? 5 : 10));
		return nf * $.ig.MathUtil.prototype.expt(10, expv);
	}
	,
	niceCeiling: function (value) {
		if (value == 0) {
			return 0;
		}
		if (value < 0) {
			return -$.ig.MathUtil.prototype.niceFloor(-value);
		}
		var expv = $.ig.truncate(Math.floor(Math.log10(value)));
		var f = value / $.ig.MathUtil.prototype.expt(10, expv);
		var nf = f <= 1 ? 1 : (f <= 2 ? 2 : (f <= 5 ? 5 : 10));
		return nf * $.ig.MathUtil.prototype.expt(10, expv);
	}
	,
	expt: function (a, n) {
		var x = 1;
		for (; n > 0; --n) {
			x *= a;
		}
		for (; n < 0; ++n) {
			x /= a;
		}
		return x;
	}
	,
	min3: function (v1, v2, v3) {
		return Math.min(v1, Math.min(v2, v3));
	}
	,
	max3: function (v1, v2, v3) {
		return Math.max(v1, Math.max(v2, v3));
	}
	,
	min: function (a) {
		var min = a[0];
		for (var i = 1; i < a.length; ++i) {
			min = Math.min(min, a[i]);
		}
		return min;
	}
	,
	max: function (a) {
		var max = a[0];
		for (var i = 1; i < a.length; ++i) {
			max = Math.max(max, a[i]);
		}
		return max;
	}
	,
	$type: new $.ig.Type('MathUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.MathUtil.prototype.degreeAsRadian = Math.PI / 180;
$.ig.MathUtil.prototype.pHI = (1 + Math.sqrt(5)) / 2;
$.ig.MathUtil.prototype.sQRT2 = Math.sqrt(2);
$.ig.MathUtil.prototype._basis = [ 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180, 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 ];

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"Visibility:bc", 
"Brush:be", 
"Color:bf", 
"Math:bg", 
"ArgumentException:bh", 
"Script:bi", 
"JQueryObject:bl", 
"Element:bm", 
"ElementAttributeCollection:bn", 
"ElementCollection:bo", 
"WebStyle:bp", 
"ElementNodeType:bq", 
"Document:br", 
"EventListener:bs", 
"IElementEventHandler:bt", 
"ElementEventHandler:bu", 
"ElementAttribute:bv", 
"JQueryPosition:bw", 
"JQueryCallback:bx", 
"MulticastDelegate:by", 
"IntPtr:bz", 
"JQueryEvent:b0", 
"JQueryUICallback:b1", 
"RenderingContext:b2", 
"IRenderer:b3", 
"Rectangle:b4", 
"Shape:b5", 
"FrameworkElement:b6", 
"UIElement:b7", 
"DependencyObject:b8", 
"Dictionary:b9", 
"DependencyProperty:ca", 
"PropertyMetadata:cb", 
"PropertyChangedCallback:cc", 
"DependencyPropertyChangedEventArgs:cd", 
"DependencyPropertiesCollection:ce", 
"UnsetValue:cf", 
"Binding:cg", 
"PropertyPath:ch", 
"Transform:ci", 
"Style:cj", 
"DoubleCollection:ck", 
"List$1:cl", 
"IList$1:cm", 
"ICollection$1:cn", 
"IArray:co", 
"IArrayList:cp", 
"Array:cq", 
"CompareCallback:cr", 
"Func$3:cs", 
"Action$1:ct", 
"Comparer$1:cu", 
"IComparer:cv", 
"IComparer$1:cw", 
"DefaultComparer$1:cx", 
"Comparison$1:cy", 
"ReadOnlyCollection$1:cz", 
"Predicate$1:c0", 
"NotImplementedException:c1", 
"Rect:c2", 
"Size:c3", 
"Point:c4", 
"Path:c5", 
"Geometry:c6", 
"GeometryType:c7", 
"TextBlock:c8", 
"Polygon:c9", 
"PointCollection:da", 
"Polyline:db", 
"DataTemplateRenderInfo:dc", 
"DataTemplatePassInfo:dd", 
"ContentControl:de", 
"Control:df", 
"Thickness:dg", 
"HorizontalAlignment:dh", 
"VerticalAlignment:di", 
"DataTemplate:dj", 
"DataTemplateRenderHandler:dk", 
"DataTemplateMeasureHandler:dl", 
"DataTemplateMeasureInfo:dm", 
"DataTemplatePassHandler:dn", 
"Line:dp", 
"FontInfo:dq", 
"NotifyCollectionChangedAction:ds", 
"LinearGradientBrush:d3", 
"GradientStop:d4", 
"LineGeometry:d8", 
"GeometryGroup:d9", 
"GeometryCollection:ea", 
"FillRule:eb", 
"PathGeometry:ec", 
"PathFigureCollection:ed", 
"RectangleGeometry:ee", 
"EllipseGeometry:ef", 
"PathFigure:em", 
"PathSegmentCollection:en", 
"PathSegment:eo", 
"PathSegmentType:ep", 
"LineSegment:eq", 
"PolyLineSegment:es", 
"ArcSegment:eu", 
"SweepDirection:ev", 
"PolyBezierSegment:ex", 
"BezierSegment:ez", 
"RotateTransform:e2", 
"TransformGroup:e3", 
"TransformCollection:e4", 
"Func$2:fi", 
"EventArgs:fl", 
"NotifyCollectionChangedEventArgs:fn", 
"Array:fu", 
"Delegate:fv", 
"Interlocked:fw", 
"Dictionary$2:fy", 
"IDictionary$2:fz", 
"IDictionary:f0", 
"KeyValuePair$2:f1", 
"Enumerable:f2", 
"Thread:f3", 
"ThreadStart:f4", 
"IOrderedEnumerable$1:f5", 
"SortedList$1:f6", 
"ArgumentNullException:f7", 
"IEqualityComparer$1:f8", 
"EqualityComparer$1:f9", 
"IEqualityComparer:ga", 
"DefaultEqualityComparer$1:gb", 
"InvalidOperationException:gc", 
"FontUtil:gi", 
"JQuery:gj", 
"JQueryDeferred:gk", 
"JQueryPromise:gl", 
"Action:gm", 
"MathUtil:gx", 
"RuntimeHelpers:gy", 
"RuntimeFieldHandle:gz", 
"Random:g0", 
"Callback:hm", 
"window:hn", 
"CanvasViewRenderer:h4", 
"CanvasContext2D:h5", 
"CanvasContext:h6", 
"TextMetrics:h7", 
"ImageData:h8", 
"CanvasElement:h9", 
"Gradient:ia", 
"GeometryUtil:ib", 
"Tuple$2:ic", 
"TranslateTransform:id", 
"ScaleTransform:ie", 
"AbstractEnumerable:ii", 
"Func$1:ij", 
"AbstractEnumerator:ik", 
"GenericEnumerable$1:il", 
"GenericEnumerator$1:im"]);


} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"Action:ba", 
"MulticastDelegate:bb", 
"IntPtr:bc", 
"Callback:bd", 
"window:be", 
"List$1:bi", 
"IList$1:bj", 
"ICollection$1:bk", 
"IArray:bl", 
"Script:bm", 
"IArrayList:bn", 
"Array:bo", 
"CompareCallback:bp", 
"Func$3:bq", 
"Action$1:br", 
"Comparer$1:bs", 
"IComparer:bt", 
"IComparer$1:bu", 
"DefaultComparer$1:bv", 
"Comparison$1:bw", 
"ReadOnlyCollection$1:bx", 
"Predicate$1:by", 
"NotImplementedException:bz", 
"NotifyCollectionChangedEventArgs:b2", 
"EventArgs:b3", 
"NotifyCollectionChangedAction:b4", 
"Delegate:b8", 
"Interlocked:b9", 
"Control:cc", 
"FrameworkElement:cd", 
"UIElement:ce", 
"DependencyObject:cf", 
"Dictionary:cg", 
"DependencyProperty:ch", 
"PropertyMetadata:ci", 
"PropertyChangedCallback:cj", 
"DependencyPropertyChangedEventArgs:ck", 
"DependencyPropertiesCollection:cl", 
"UnsetValue:cm", 
"Binding:cn", 
"PropertyPath:co", 
"Transform:cp", 
"Visibility:cq", 
"Style:cr", 
"Thickness:cs", 
"HorizontalAlignment:ct", 
"VerticalAlignment:cu", 
"Brush:cv", 
"Color:cw", 
"Math:cx", 
"ArgumentException:cy", 
"DataTemplate:c0", 
"DataTemplateRenderHandler:c1", 
"DataTemplateRenderInfo:c2", 
"DataTemplatePassInfo:c3", 
"DataTemplateMeasureHandler:c4", 
"DataTemplateMeasureInfo:c5", 
"DataTemplatePassHandler:c6", 
"Geometry:dd", 
"GeometryType:de", 
"Point:df", 
"PathGeometry:dk", 
"PathFigureCollection:dl", 
"Rect:dp", 
"Size:dq", 
"PathSegmentCollection:dr", 
"ArcSegment:ds", 
"PathSegment:dt", 
"PathSegmentType:du", 
"SweepDirection:dv", 
"PolyBezierSegment:dw", 
"PointCollection:dx", 
"LineSegment:dy", 
"PathFigure:dz", 
"Dictionary$2:d2", 
"IDictionary$2:d3", 
"IDictionary:d4", 
"Func$2:d5", 
"KeyValuePair$2:d6", 
"Enumerable:d7", 
"Thread:d8", 
"ThreadStart:d9", 
"IOrderedEnumerable$1:ea", 
"SortedList$1:eb", 
"ArgumentNullException:ec", 
"IEqualityComparer$1:ed", 
"EqualityComparer$1:ee", 
"IEqualityComparer:ef", 
"DefaultEqualityComparer$1:eg", 
"InvalidOperationException:eh", 
"LinearGradientBrush:eu", 
"GradientStop:ev", 
"Path:ez", 
"Shape:e0", 
"DoubleCollection:e1", 
"Line:e2", 
"LineGeometry:e3", 
"GeometryGroup:e4", 
"GeometryCollection:e5", 
"FillRule:e6", 
"RectangleGeometry:e7", 
"EllipseGeometry:e8", 
"PolyLineSegment:fg", 
"BezierSegment:fk", 
"FontInfo:fn", 
"TextBlock:fo", 
"RotateTransform:fp", 
"TransformGroup:fq", 
"TransformCollection:fr", 
"Element:f4", 
"ElementAttributeCollection:f5", 
"ElementCollection:f6", 
"WebStyle:f7", 
"ElementNodeType:f8", 
"Document:f9", 
"EventListener:ga", 
"IElementEventHandler:gb", 
"ElementEventHandler:gc", 
"ElementAttribute:gd", 
"JQuery:gq", 
"JQueryObject:gr", 
"JQueryPosition:gs", 
"JQueryCallback:gt", 
"JQueryEvent:gu", 
"JQueryUICallback:gv", 
"JQueryDeferred:gw", 
"JQueryPromise:gx", 
"Tuple$2:gz", 
"FontUtil:g8", 
"RenderingContext:g9", 
"IRenderer:ha", 
"Rectangle:hb", 
"Polygon:hc", 
"Polyline:hd", 
"ContentControl:he", 
"Random:hh", 
"MathUtil:hj", 
"RuntimeHelpers:hk", 
"RuntimeFieldHandle:hl", 
"CanvasViewRenderer:hp", 
"CanvasContext2D:hq", 
"CanvasContext:hr", 
"TextMetrics:hs", 
"ImageData:ht", 
"CanvasElement:hu", 
"Gradient:hv", 
"GeometryUtil:hw", 
"TranslateTransform:hx", 
"ScaleTransform:hy", 
"Func$1:i1", 
"CssGradientUtil:i6", 
"AbstractEnumerable:je", 
"AbstractEnumerator:jf", 
"GenericEnumerable$1:jg", 
"GenericEnumerator$1:jh"]);


} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"List$1:ba", 
"IList$1:bb", 
"ICollection$1:bc", 
"IArray:bd", 
"Script:be", 
"IArrayList:bf", 
"Array:bg", 
"CompareCallback:bh", 
"MulticastDelegate:bi", 
"IntPtr:bj", 
"Func$3:bk", 
"Action$1:bl", 
"Comparer$1:bm", 
"IComparer:bn", 
"IComparer$1:bo", 
"DefaultComparer$1:bp", 
"Comparison$1:bq", 
"ReadOnlyCollection$1:br", 
"Predicate$1:bs", 
"NotImplementedException:bt", 
"Point:bu", 
"GeometryUtil:bv", 
"Math:bw", 
"Rect:bx", 
"Size:by", 
"Tuple$2:bz", 
"PathFigure:b0", 
"PathSegmentCollection:b1", 
"ArcSegment:b2", 
"PathSegment:b3", 
"PathSegmentType:b4", 
"SweepDirection:b5", 
"PolyLineSegment:b6", 
"PointCollection:b7", 
"Func$1:b9", 
"Control:cd", 
"FrameworkElement:ce", 
"UIElement:cf", 
"DependencyObject:cg", 
"Dictionary:ch", 
"DependencyProperty:ci", 
"PropertyMetadata:cj", 
"PropertyChangedCallback:ck", 
"DependencyPropertyChangedEventArgs:cl", 
"DependencyPropertiesCollection:cm", 
"UnsetValue:cn", 
"Binding:co", 
"PropertyPath:cp", 
"Transform:cq", 
"Visibility:cr", 
"Style:cs", 
"Thickness:ct", 
"HorizontalAlignment:cu", 
"VerticalAlignment:cv", 
"Callback:c1", 
"Delegate:c2", 
"Interlocked:c3", 
"NotifyCollectionChangedEventArgs:c8", 
"EventArgs:c9", 
"NotifyCollectionChangedAction:da", 
"Brush:dc", 
"Color:dd", 
"ArgumentException:de", 
"Random:df", 
"MathUtil:dg", 
"RuntimeHelpers:dh", 
"RuntimeFieldHandle:di", 
"LinearGradientBrush:dv", 
"GradientStop:dw", 
"JQueryObject:dx", 
"Element:dy", 
"ElementAttributeCollection:dz", 
"ElementCollection:d0", 
"WebStyle:d1", 
"ElementNodeType:d2", 
"Document:d3", 
"EventListener:d4", 
"IElementEventHandler:d5", 
"ElementEventHandler:d6", 
"ElementAttribute:d7", 
"JQueryPosition:d8", 
"JQueryCallback:d9", 
"JQueryEvent:ea", 
"JQueryUICallback:eb", 
"JQuery:ed", 
"JQueryDeferred:ee", 
"JQueryPromise:ef", 
"Action:eg", 
"CssGradientUtil:eh", 
"PathGeometry:ei", 
"Geometry:ej", 
"GeometryType:ek", 
"PathFigureCollection:el", 
"LineSegment:eo", 
"Dictionary$2:er", 
"IDictionary$2:es", 
"IDictionary:et", 
"Func$2:eu", 
"KeyValuePair$2:ev", 
"Enumerable:ew", 
"Thread:ex", 
"ThreadStart:ey", 
"IOrderedEnumerable$1:ez", 
"SortedList$1:e0", 
"ArgumentNullException:e1", 
"IEqualityComparer$1:e2", 
"EqualityComparer$1:e3", 
"IEqualityComparer:e4", 
"DefaultEqualityComparer$1:e5", 
"InvalidOperationException:e6", 
"Path:e9", 
"Shape:fa", 
"DoubleCollection:fb", 
"TextBlock:fc", 
"TransformGroup:fl", 
"TransformCollection:fm", 
"RotateTransform:fn", 
"Line:f3", 
"LineGeometry:f4", 
"GeometryGroup:f5", 
"GeometryCollection:f6", 
"FillRule:f7", 
"RectangleGeometry:f8", 
"EllipseGeometry:f9", 
"PolyBezierSegment:gj", 
"BezierSegment:gl", 
"FontInfo:go", 
"FontUtil:gy", 
"RenderingContext:gz", 
"IRenderer:g0", 
"Rectangle:g1", 
"Polygon:g2", 
"Polyline:g3", 
"DataTemplateRenderInfo:g4", 
"DataTemplatePassInfo:g5", 
"ContentControl:g6", 
"DataTemplate:g7", 
"DataTemplateRenderHandler:g8", 
"DataTemplateMeasureHandler:g9", 
"DataTemplateMeasureInfo:ha", 
"DataTemplatePassHandler:hb", 
"window:ho", 
"CanvasElement:hq", 
"CanvasContext:hr", 
"CanvasViewRenderer:hs", 
"CanvasContext2D:ht", 
"TextMetrics:hu", 
"ImageData:hv", 
"Gradient:hw", 
"TranslateTransform:hx", 
"ScaleTransform:hy", 
"AbstractEnumerable:h5", 
"AbstractEnumerator:h6", 
"GenericEnumerable$1:h7", 
"GenericEnumerator$1:h8"]);


} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"List$1:ba", 
"IList$1:bb", 
"ICollection$1:bc", 
"IArray:bd", 
"Script:be", 
"IArrayList:bf", 
"Array:bg", 
"CompareCallback:bh", 
"MulticastDelegate:bi", 
"IntPtr:bj", 
"Func$3:bk", 
"Action$1:bl", 
"Comparer$1:bm", 
"IComparer:bn", 
"IComparer$1:bo", 
"DefaultComparer$1:bp", 
"Comparison$1:bq", 
"ReadOnlyCollection$1:br", 
"Predicate$1:bs", 
"NotImplementedException:bt", 
"Point:bu", 
"GeometryUtil:bv", 
"Math:bw", 
"Rect:bx", 
"Size:by", 
"Tuple$2:bz", 
"PathFigure:b0", 
"PathSegmentCollection:b1", 
"ArcSegment:b2", 
"PathSegment:b3", 
"PathSegmentType:b4", 
"SweepDirection:b5", 
"PolyLineSegment:b6", 
"PointCollection:b7", 
"Func$1:b9", 
"Control:cd", 
"FrameworkElement:ce", 
"UIElement:cf", 
"DependencyObject:cg", 
"Dictionary:ch", 
"DependencyProperty:ci", 
"PropertyMetadata:cj", 
"PropertyChangedCallback:ck", 
"DependencyPropertyChangedEventArgs:cl", 
"DependencyPropertiesCollection:cm", 
"UnsetValue:cn", 
"Binding:co", 
"PropertyPath:cp", 
"Transform:cq", 
"Visibility:cr", 
"Style:cs", 
"Thickness:ct", 
"HorizontalAlignment:cu", 
"VerticalAlignment:cv", 
"Callback:c1", 
"Delegate:c2", 
"Interlocked:c3", 
"PathGeometry:c8", 
"Geometry:c9", 
"GeometryType:da", 
"PathFigureCollection:db", 
"LineSegment:dd", 
"Brush:de", 
"Color:df", 
"ArgumentException:dg", 
"LinearGradientBrush:di", 
"GradientStop:dj", 
"Random:dl", 
"MathUtil:dn", 
"RuntimeHelpers:dp", 
"RuntimeFieldHandle:dq", 
"JQueryObject:dr", 
"Element:ds", 
"ElementAttributeCollection:dt", 
"ElementCollection:du", 
"WebStyle:dv", 
"ElementNodeType:dw", 
"Document:dx", 
"EventListener:dy", 
"IElementEventHandler:dz", 
"ElementEventHandler:d0", 
"ElementAttribute:d1", 
"JQueryPosition:d2", 
"JQueryCallback:d3", 
"JQueryEvent:d4", 
"JQueryUICallback:d5", 
"NotifyCollectionChangedEventArgs:ea", 
"EventArgs:eb", 
"NotifyCollectionChangedAction:ec", 
"JQuery:ee", 
"JQueryDeferred:ef", 
"JQueryPromise:eg", 
"Action:eh", 
"CssGradientUtil:ei", 
"Dictionary$2:ep", 
"IDictionary$2:eq", 
"IDictionary:er", 
"Func$2:es", 
"KeyValuePair$2:et", 
"Enumerable:eu", 
"Thread:ev", 
"ThreadStart:ew", 
"IOrderedEnumerable$1:ex", 
"SortedList$1:ey", 
"ArgumentNullException:ez", 
"IEqualityComparer$1:e0", 
"EqualityComparer$1:e1", 
"IEqualityComparer:e2", 
"DefaultEqualityComparer$1:e3", 
"InvalidOperationException:e4", 
"Path:e7", 
"Shape:e8", 
"DoubleCollection:e9", 
"TextBlock:fa", 
"Line:ft", 
"LineGeometry:fu", 
"GeometryGroup:fv", 
"GeometryCollection:fw", 
"FillRule:fx", 
"RectangleGeometry:fy", 
"EllipseGeometry:fz", 
"PolyBezierSegment:f9", 
"BezierSegment:gb", 
"FontInfo:ge", 
"RotateTransform:gf", 
"TransformGroup:gg", 
"TransformCollection:gh", 
"FontUtil:gz", 
"RenderingContext:g0", 
"IRenderer:g1", 
"Rectangle:g2", 
"Polygon:g3", 
"Polyline:g4", 
"DataTemplateRenderInfo:g5", 
"DataTemplatePassInfo:g6", 
"ContentControl:g7", 
"DataTemplate:g8", 
"DataTemplateRenderHandler:g9", 
"DataTemplateMeasureHandler:ha", 
"DataTemplateMeasureInfo:hb", 
"DataTemplatePassHandler:hc", 
"window:hs", 
"CanvasElement:hv", 
"CanvasContext:hw", 
"CanvasViewRenderer:hx", 
"CanvasContext2D:hy", 
"TextMetrics:hz", 
"ImageData:h0", 
"Gradient:h1", 
"TranslateTransform:h2", 
"ScaleTransform:h3", 
"AbstractEnumerable:h8", 
"AbstractEnumerator:h9", 
"GenericEnumerable$1:ia", 
"GenericEnumerator$1:ib"]);


} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"List$1:ba", 
"IList$1:bb", 
"ICollection$1:bc", 
"IArray:bd", 
"Script:be", 
"IArrayList:bf", 
"Array:bg", 
"CompareCallback:bh", 
"MulticastDelegate:bi", 
"IntPtr:bj", 
"Func$3:bk", 
"Action$1:bl", 
"Comparer$1:bm", 
"IComparer:bn", 
"IComparer$1:bo", 
"DefaultComparer$1:bp", 
"Comparison$1:bq", 
"ReadOnlyCollection$1:br", 
"Predicate$1:bs", 
"NotImplementedException:bt", 
"Point:bu", 
"GeometryUtil:bv", 
"Math:bw", 
"Rect:bx", 
"Size:by", 
"Tuple$2:bz", 
"PathFigure:b0", 
"PathSegmentCollection:b1", 
"ArcSegment:b2", 
"PathSegment:b3", 
"PathSegmentType:b4", 
"SweepDirection:b5", 
"PolyLineSegment:b6", 
"PointCollection:b7", 
"Func$1:b9", 
"Control:cd", 
"FrameworkElement:ce", 
"UIElement:cf", 
"DependencyObject:cg", 
"Dictionary:ch", 
"DependencyProperty:ci", 
"PropertyMetadata:cj", 
"PropertyChangedCallback:ck", 
"DependencyPropertyChangedEventArgs:cl", 
"DependencyPropertiesCollection:cm", 
"UnsetValue:cn", 
"Binding:co", 
"PropertyPath:cp", 
"Transform:cq", 
"Visibility:cr", 
"Style:cs", 
"Thickness:ct", 
"HorizontalAlignment:cu", 
"VerticalAlignment:cv", 
"Callback:c1", 
"Delegate:c2", 
"Interlocked:c3", 
"PathGeometry:c8", 
"Geometry:c9", 
"GeometryType:da", 
"PathFigureCollection:db", 
"LineSegment:dd", 
"Brush:de", 
"Color:df", 
"ArgumentException:dg", 
"LinearGradientBrush:di", 
"GradientStop:dj", 
"Random:dl", 
"MathUtil:dn", 
"RuntimeHelpers:dp", 
"RuntimeFieldHandle:dq", 
"JQueryObject:dr", 
"Element:ds", 
"ElementAttributeCollection:dt", 
"ElementCollection:du", 
"WebStyle:dv", 
"ElementNodeType:dw", 
"Document:dx", 
"EventListener:dy", 
"IElementEventHandler:dz", 
"ElementEventHandler:d0", 
"ElementAttribute:d1", 
"JQueryPosition:d2", 
"JQueryCallback:d3", 
"JQueryEvent:d4", 
"JQueryUICallback:d5", 
"NotifyCollectionChangedEventArgs:ea", 
"EventArgs:eb", 
"NotifyCollectionChangedAction:ec", 
"JQuery:ee", 
"JQueryDeferred:ef", 
"JQueryPromise:eg", 
"Action:eh", 
"CssGradientUtil:ei", 
"Dictionary$2:eq", 
"IDictionary$2:er", 
"IDictionary:es", 
"Func$2:et", 
"KeyValuePair$2:eu", 
"Enumerable:ev", 
"Thread:ew", 
"ThreadStart:ex", 
"IOrderedEnumerable$1:ey", 
"SortedList$1:ez", 
"ArgumentNullException:e0", 
"IEqualityComparer$1:e1", 
"EqualityComparer$1:e2", 
"IEqualityComparer:e3", 
"DefaultEqualityComparer$1:e4", 
"InvalidOperationException:e5", 
"Path:e8", 
"Shape:e9", 
"DoubleCollection:fa", 
"TextBlock:fb", 
"Line:fx", 
"LineGeometry:fy", 
"GeometryGroup:fz", 
"GeometryCollection:f0", 
"FillRule:f1", 
"RectangleGeometry:f2", 
"EllipseGeometry:f3", 
"PolyBezierSegment:gd", 
"BezierSegment:gf", 
"FontInfo:gi", 
"RotateTransform:gj", 
"TransformGroup:gk", 
"TransformCollection:gl", 
"FontUtil:g3", 
"RenderingContext:g4", 
"IRenderer:g5", 
"Rectangle:g6", 
"Polygon:g7", 
"Polyline:g8", 
"DataTemplateRenderInfo:g9", 
"DataTemplatePassInfo:ha", 
"ContentControl:hb", 
"DataTemplate:hc", 
"DataTemplateRenderHandler:hd", 
"DataTemplateMeasureHandler:he", 
"DataTemplateMeasureInfo:hf", 
"DataTemplatePassHandler:hg", 
"window:hw", 
"CanvasElement:hz", 
"CanvasContext:h0", 
"CanvasViewRenderer:h1", 
"CanvasContext2D:h2", 
"TextMetrics:h3", 
"ImageData:h4", 
"Gradient:h5", 
"TranslateTransform:h6", 
"ScaleTransform:h7", 
"AbstractEnumerable:ia", 
"AbstractEnumerator:ib", 
"GenericEnumerable$1:ic", 
"GenericEnumerator$1:id"]);


} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Object:b", 
"Type:c", 
"Boolean:d", 
"ValueType:e", 
"Void:f", 
"IConvertible:g", 
"IFormatProvider:h", 
"Number:i", 
"String:j", 
"IComparable:k", 
"Number:l", 
"IComparable$1:m", 
"IEquatable$1:n", 
"Number:o", 
"Number:p", 
"Number:q", 
"NumberStyles:r", 
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"NotSupportedException:y", 
"Error:z", 
"Number:aa", 
"String:ab", 
"StringComparison:ac", 
"RegExp:ad", 
"CultureInfo:ae", 
"DateTimeFormatInfo:af", 
"Calendar:ag", 
"Date:ah", 
"Number:ai", 
"DayOfWeek:aj", 
"DateTimeKind:ak", 
"CalendarWeekRule:al", 
"NumberFormatInfo:am", 
"CompareInfo:an", 
"CompareOptions:ao", 
"IEnumerable$1:ap", 
"IEnumerator$1:aq", 
"IDisposable:ar", 
"StringSplitOptions:as", 
"Number:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Assembly:az", 
"Stream:a0", 
"SeekOrigin:a1", 
"RuntimeTypeHandle:a2", 
"MethodInfo:a3", 
"MethodBase:a4", 
"MemberInfo:a5", 
"ParameterInfo:a6", 
"TypeCode:a7", 
"ConstructorInfo:a8", 
"PropertyInfo:a9", 
"InvalidOperationException:be", 
"NotImplementedException:bf", 
"Script:bg", 
"Math:bj", 
"ArgumentNullException:bl", 
"ArgumentException:bn", 
"Dictionary$2:bp", 
"IDictionary$2:bq", 
"ICollection$1:br", 
"IDictionary:bs", 
"Func$2:bt", 
"MulticastDelegate:bu", 
"IntPtr:bv", 
"KeyValuePair$2:bw", 
"Enumerable:bx", 
"Thread:by", 
"ThreadStart:bz", 
"Func$3:b0", 
"IList$1:b1", 
"IOrderedEnumerable$1:b2", 
"SortedList$1:b3", 
"List$1:b4", 
"IArray:b5", 
"IArrayList:b6", 
"Array:b7", 
"CompareCallback:b8", 
"Action$1:b9", 
"Comparer$1:ca", 
"IComparer:cb", 
"IComparer$1:cc", 
"DefaultComparer$1:cd", 
"Comparison$1:ce", 
"ReadOnlyCollection$1:cf", 
"Predicate$1:cg", 
"IEqualityComparer$1:ch", 
"EqualityComparer$1:ci", 
"IEqualityComparer:cj", 
"DefaultEqualityComparer$1:ck", 
"RuntimeHelpers:cn", 
"RuntimeFieldHandle:co", 
"Control:ii", 
"FrameworkElement:ij", 
"UIElement:ik", 
"DependencyObject:il", 
"Dictionary:im", 
"DependencyProperty:io", 
"PropertyMetadata:ip", 
"PropertyChangedCallback:iq", 
"DependencyPropertyChangedEventArgs:ir", 
"DependencyPropertiesCollection:is", 
"UnsetValue:it", 
"Binding:iu", 
"PropertyPath:iv", 
"Transform:iw", 
"Visibility:ix", 
"Style:iy", 
"Thickness:iz", 
"HorizontalAlignment:i0", 
"VerticalAlignment:i1", 
"Rect:i2", 
"Size:i3", 
"Point:i4", 
"Brush:i5", 
"Color:i6", 
"EventArgs:jb", 
"TextBlock:jc", 
"Delegate:jf", 
"Interlocked:jg", 
"FontInfo:jh", 
"FontUtil:ji", 
"JQuery:jj", 
"JQueryObject:jk", 
"Element:jl", 
"ElementAttributeCollection:jm", 
"ElementCollection:jn", 
"WebStyle:jo", 
"ElementNodeType:jp", 
"Document:jq", 
"EventListener:jr", 
"IElementEventHandler:js", 
"ElementEventHandler:jt", 
"ElementAttribute:ju", 
"JQueryPosition:jv", 
"JQueryCallback:jw", 
"JQueryEvent:jx", 
"JQueryUICallback:jy", 
"JQueryDeferred:jz", 
"JQueryPromise:j0", 
"Action:j1", 
"RenderingContext:j2", 
"IRenderer:j3", 
"Rectangle:j4", 
"Shape:j5", 
"DoubleCollection:j6", 
"Path:j7", 
"Geometry:j8", 
"GeometryType:j9", 
"Polygon:ka", 
"PointCollection:kb", 
"Polyline:kc", 
"DataTemplateRenderInfo:kd", 
"DataTemplatePassInfo:ke", 
"ContentControl:kf", 
"DataTemplate:kg", 
"DataTemplateRenderHandler:kh", 
"DataTemplateMeasureHandler:ki", 
"DataTemplateMeasureInfo:kj", 
"DataTemplatePassHandler:kk", 
"Line:kl", 
"LinearGradientBrush:kn", 
"GradientStop:ko", 
"Random:kq", 
"MathUtil:ks", 
"NotifyCollectionChangedEventArgs:kx", 
"NotifyCollectionChangedAction:ky", 
"CssGradientUtil:k3", 
"GeometryUtil:k4", 
"Tuple$2:k5", 
"Callback:k6", 
"window:k7", 
"CanvasElement:k9", 
"CanvasContext:la", 
"CanvasViewRenderer:lb", 
"CanvasContext2D:lc", 
"TextMetrics:ld", 
"ImageData:le", 
"Gradient:lf", 
"GeometryGroup:lg", 
"GeometryCollection:lh", 
"FillRule:li", 
"PathGeometry:lj", 
"PathFigureCollection:lk", 
"LineGeometry:ll", 
"RectangleGeometry:lm", 
"EllipseGeometry:ln", 
"ArcSegment:lo", 
"PathSegment:lp", 
"PathSegmentType:lq", 
"SweepDirection:lr", 
"PathFigure:ls", 
"PathSegmentCollection:lt", 
"LineSegment:lu", 
"PolyLineSegment:lv", 
"BezierSegment:lw", 
"PolyBezierSegment:lx", 
"TransformGroup:ly", 
"TransformCollection:lz", 
"TranslateTransform:l0", 
"RotateTransform:l1", 
"ScaleTransform:l2", 
"AbstractEnumerable:np", 
"Func$1:nq", 
"AbstractEnumerator:nr", 
"GenericEnumerable$1:ns", 
"GenericEnumerator$1:nt"]);


} (jQuery));


