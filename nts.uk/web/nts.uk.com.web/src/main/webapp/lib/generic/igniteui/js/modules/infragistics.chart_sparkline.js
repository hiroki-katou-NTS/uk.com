/*!@license
* Infragistics.Web.ClientUI infragistics.chart_sparkline.js 16.1.20161.2145
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
"Script:bm", 
"Array:bo", 
"NotImplementedException:bz", 
"Math:cb", 
"Dictionary_EnumerableCollection$3:ch", 
"InvalidOperationException:ci", 
"GenericEnumerable$1:cj", 
"GenericEnumerator$1:ck", 
"EventArgs:cp", 
"Queue$1:cz", 
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
"Point:dt", 
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
"UIElement:gd", 
"Transform:ge", 
"Key:g1", 
"ModifierKeys:g2", 
"MouseEventArgs:g3"]);


$.ig.util.defType('ModifierKeys', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Alt";
			case 2: return "Control";
			case 4: return "Shift";
			case 8: return "Windows";
			case 8: return "Apple";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ModifierKeys', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('Key', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Back";
			case 2: return "Tab";
			case 3: return "Enter";
			case 4: return "Shift";
			case 5: return "Ctrl";
			case 6: return "Alt";
			case 7: return "CapsLock";
			case 8: return "Escape";
			case 9: return "Space";
			case 10: return "PageUp";
			case 11: return "PageDown";
			case 12: return "End";
			case 13: return "Home";
			case 14: return "Left";
			case 15: return "Up";
			case 16: return "Right";
			case 17: return "Down";
			case 18: return "Insert";
			case 19: return "Delete";
			case 20: return "D0";
			case 21: return "D1";
			case 22: return "D2";
			case 23: return "D3";
			case 24: return "D4";
			case 25: return "D5";
			case 26: return "D6";
			case 27: return "D7";
			case 28: return "D8";
			case 29: return "D9";
			case 30: return "A";
			case 31: return "B";
			case 32: return "C";
			case 33: return "D";
			case 34: return "E";
			case 35: return "F";
			case 36: return "G";
			case 37: return "H";
			case 38: return "I";
			case 39: return "J";
			case 40: return "K";
			case 41: return "L";
			case 42: return "M";
			case 43: return "N";
			case 44: return "O";
			case 45: return "P";
			case 46: return "Q";
			case 47: return "R";
			case 48: return "S";
			case 49: return "T";
			case 50: return "U";
			case 51: return "V";
			case 52: return "W";
			case 53: return "X";
			case 54: return "Y";
			case 55: return "Z";
			case 56: return "F1";
			case 57: return "F2";
			case 58: return "F3";
			case 59: return "F4";
			case 60: return "F5";
			case 61: return "F6";
			case 62: return "F7";
			case 63: return "F8";
			case 64: return "F9";
			case 65: return "F10";
			case 66: return "F11";
			case 67: return "F12";
			case 68: return "NumPad0";
			case 69: return "NumPad1";
			case 70: return "NumPad2";
			case 71: return "NumPad3";
			case 72: return "NumPad4";
			case 73: return "NumPad5";
			case 74: return "NumPad6";
			case 75: return "NumPad7";
			case 76: return "NumPad8";
			case 77: return "NumPad9";
			case 78: return "Multiply";
			case 79: return "Add";
			case 80: return "Subtract";
			case 81: return "Decimal";
			case 82: return "Divide";
			case 255: return "Unknown";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('Key', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('Queue$1', 'Object', {
	$t: null,
	__count: 0,
	__head: 0,
	__tail: 0,
	__items: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__items = new Array(4);
	},
	count: function () {
		return this.__count;
	}
	,
	copyTo: function (array, index) {
		if (this.__head < this.__tail) {
			for (var i = this.__head; i < this.__tail; i++) {
				array[index++] = $.ig.util.getBoxIfEnum(this.$t, this.__items[i]);
			}
		} else {
			for (var i1 = this.__head; i1 < this.__items.length; i1++) {
				array[index++] = $.ig.util.getBoxIfEnum(this.$t, this.__items[i1]);
			}
			for (var i2 = 0; i2 < this.__tail; i2++) {
				array[index++] = $.ig.util.getBoxIfEnum(this.$t, this.__items[i2]);
			}
		}
	}
	,
	isSynchronized: function () {
		return false;
	}
	,
	syncRoot: function () {
		return null;
	}
	,
	getEnumerator: function () {
		var d__ = new $.ig.Queue___GetEnumerator__IteratorClass$1(this.$t, 0);
		d__.__4__this = this;
		return d__;
	}
	,
	enqueue: function (item) {
		if (this.__count == this.__items.length) {
			var newItems = new Array(Math.max(2, this.__items.length * 2));
			this.copyTo(newItems, 0);
			this.__head = 0;
			this.__tail = this.__items.length;
			this.__items = newItems;
		}
		this.__items[this.__tail] = item;
		this.__tail = (this.__tail + 1) % this.__items.length;
		this.__count++;
	}
	,
	dequeue: function () {
		if (this.__count == 0) {
			throw new $.ig.InvalidOperationException(1, "The Queue is empty.");
		}
		var result = this.__items[this.__head];
		this.__items[this.__head] = null;
		this.__head = (this.__head + 1) % this.__items.length;
		this.__count--;
		return result;
	}
	,
	peek: function () {
		if (this.__count == 0) {
			throw new $.ig.InvalidOperationException(1, "The Queue is empty.");
		}
		return this.__items[this.__head];
	}
	,
	$type: new $.ig.Type('Queue$1', $.ig.Object.prototype.$type, [$.ig.ICollection.prototype.$type, $.ig.IEnumerable$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('MouseEventArgs', 'EventArgs', {
	init: function () {
		$.ig.EventArgs.prototype.init.call(this);
	},
	_position: null,
	position: function (value) {
		if (arguments.length === 1) {
			this._position = value;
			return value;
		} else {
			return this._position;
		}
	}
	,
	_originalSource: null,
	originalSource: function (value) {
		if (arguments.length === 1) {
			this._originalSource = value;
			return value;
		} else {
			return this._originalSource;
		}
	}
	,
	getPosition: function (relativeTo) {
		return this.position();
	}
	,
	$type: new $.ig.Type('MouseEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('Queue___GetEnumerator__IteratorClass$1', 'Object', {
	$t: null,
	__1__state: 0,
	__2__current: null,
	__i_5_0: 0,
	__i_5_1: 0,
	__i_5_2: 0,
	__4__this: null,
	init: function ($t, _1__state) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					if (this.__4__this.__head < this.__4__this.__tail) {
						this.__i_5_0 = this.__4__this.__head;
						this.__1__state = 1;
						break;
					}
					this.__i_5_1 = this.__4__this.__head;
					this.__1__state = 3;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_0 < this.__4__this.__tail) {
						this.__2__current = this.__4__this.__items[this.__i_5_0];
						this.__1__state = 2;
						return true;
					}
					break;
				case 2:
					this.__1__state = -1;
					this.__i_5_0++;
					this.__1__state = 1;
					break;
				case 3:
					this.__1__state = -1;
					if (this.__i_5_1 < this.__4__this.__items.length) {
						this.__2__current = this.__4__this.__items[this.__i_5_1];
						this.__1__state = 4;
						return true;
					}
					this.__i_5_2 = 0;
					this.__1__state = 5;
					break;
				case 4:
					this.__1__state = -1;
					this.__i_5_1++;
					this.__1__state = 3;
					break;
				case 5:
					this.__1__state = -1;
					if (this.__i_5_2 < this.__4__this.__tail) {
						this.__2__current = this.__4__this.__items[this.__i_5_2];
						this.__1__state = 6;
						return true;
					}
					break;
				case 6:
					this.__1__state = -1;
					this.__i_5_2++;
					this.__1__state = 5;
					break;
			}
		}
		return false;
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
		return $.ig.util.getBoxIfEnum(this.$t, this.__2__current);
	}
	,
	$type: new $.ig.Type('Queue___GetEnumerator__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.ModifierKeys.prototype.none = 0;
$.ig.ModifierKeys.prototype.alt = 1;
$.ig.ModifierKeys.prototype.control = 2;
$.ig.ModifierKeys.prototype.shift = 4;
$.ig.ModifierKeys.prototype.windows = 8;
$.ig.ModifierKeys.prototype.apple = 8;

$.ig.Key.prototype.none = 0;
$.ig.Key.prototype.back = 1;
$.ig.Key.prototype.tab = 2;
$.ig.Key.prototype.enter = 3;
$.ig.Key.prototype.shift = 4;
$.ig.Key.prototype.ctrl = 5;
$.ig.Key.prototype.alt = 6;
$.ig.Key.prototype.capsLock = 7;
$.ig.Key.prototype.escape = 8;
$.ig.Key.prototype.space = 9;
$.ig.Key.prototype.pageUp = 10;
$.ig.Key.prototype.pageDown = 11;
$.ig.Key.prototype.end = 12;
$.ig.Key.prototype.home = 13;
$.ig.Key.prototype.left = 14;
$.ig.Key.prototype.up = 15;
$.ig.Key.prototype.right = 16;
$.ig.Key.prototype.down = 17;
$.ig.Key.prototype.insert = 18;
$.ig.Key.prototype.del = 19;
$.ig.Key.prototype.d0 = 20;
$.ig.Key.prototype.d1 = 21;
$.ig.Key.prototype.d2 = 22;
$.ig.Key.prototype.d3 = 23;
$.ig.Key.prototype.d4 = 24;
$.ig.Key.prototype.d5 = 25;
$.ig.Key.prototype.d6 = 26;
$.ig.Key.prototype.d7 = 27;
$.ig.Key.prototype.d8 = 28;
$.ig.Key.prototype.d9 = 29;
$.ig.Key.prototype.a = 30;
$.ig.Key.prototype.b = 31;
$.ig.Key.prototype.c = 32;
$.ig.Key.prototype.d = 33;
$.ig.Key.prototype.e = 34;
$.ig.Key.prototype.f = 35;
$.ig.Key.prototype.g = 36;
$.ig.Key.prototype.h = 37;
$.ig.Key.prototype.i = 38;
$.ig.Key.prototype.j = 39;
$.ig.Key.prototype.k = 40;
$.ig.Key.prototype.l = 41;
$.ig.Key.prototype.m = 42;
$.ig.Key.prototype.n = 43;
$.ig.Key.prototype.o = 44;
$.ig.Key.prototype.p = 45;
$.ig.Key.prototype.q = 46;
$.ig.Key.prototype.r = 47;
$.ig.Key.prototype.s = 48;
$.ig.Key.prototype.t = 49;
$.ig.Key.prototype.u = 50;
$.ig.Key.prototype.v = 51;
$.ig.Key.prototype.w = 52;
$.ig.Key.prototype.x = 53;
$.ig.Key.prototype.y = 54;
$.ig.Key.prototype.z = 55;
$.ig.Key.prototype.f1 = 56;
$.ig.Key.prototype.f2 = 57;
$.ig.Key.prototype.f3 = 58;
$.ig.Key.prototype.f4 = 59;
$.ig.Key.prototype.f5 = 60;
$.ig.Key.prototype.f6 = 61;
$.ig.Key.prototype.f7 = 62;
$.ig.Key.prototype.f8 = 63;
$.ig.Key.prototype.f9 = 64;
$.ig.Key.prototype.f10 = 65;
$.ig.Key.prototype.f11 = 66;
$.ig.Key.prototype.f12 = 67;
$.ig.Key.prototype.numPad0 = 68;
$.ig.Key.prototype.numPad1 = 69;
$.ig.Key.prototype.numPad2 = 70;
$.ig.Key.prototype.numPad3 = 71;
$.ig.Key.prototype.numPad4 = 72;
$.ig.Key.prototype.numPad5 = 73;
$.ig.Key.prototype.numPad6 = 74;
$.ig.Key.prototype.numPad7 = 75;
$.ig.Key.prototype.numPad8 = 76;
$.ig.Key.prototype.numPad9 = 77;
$.ig.Key.prototype.multiply = 78;
$.ig.Key.prototype.add = 79;
$.ig.Key.prototype.subtract = 80;
$.ig.Key.prototype.decimal = 81;
$.ig.Key.prototype.divide = 82;
$.ig.Key.prototype.unknown = 255;

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["EventProxy:a", 
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
"Rect:ba", 
"Size:bb", 
"Point:bc", 
"Math:bd", 
"ModifierKeys:be", 
"Func$2:bf", 
"MulticastDelegate:bg", 
"IntPtr:bh", 
"MouseWheelHandler:bi", 
"Delegate:bj", 
"Interlocked:bk", 
"GestureHandler:bl", 
"ZoomGestureHandler:bm", 
"FlingGestureHandler:bn", 
"ContactHandler:bo", 
"TouchHandler:bp", 
"MouseOverHandler:bq", 
"MouseHandler:br", 
"KeyHandler:bs", 
"Key:bt", 
"BaseDOMEventProxy:bu", 
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
"DOMEventProxy:ca", 
"MSGesture:cb", 
"Script:cc", 
"JQuery:cd", 
"JQueryDeferred:ce", 
"JQueryPromise:cf", 
"Action:cg", 
"Action$1:ch", 
"Callback:ci", 
"window:cj", 
"MouseEventArgs:ck", 
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
"IFastItemColumnInternal:dt", 
"IFastItemColumnPropertyName:du", 
"IFastItemColumn$1:dv", 
"FastItemColumn:dw", 
"IFastItemsSource:dx", 
"EventHandler$1:dy", 
"FastItemsSourceEventArgs:dz", 
"FastItemsSourceEventAction:d0", 
"NotifyCollectionChangedEventArgs:d1", 
"NotifyCollectionChangedAction:d2", 
"FastReflectionHelper:d3", 
"FastItemDateTimeColumn:d4", 
"FastItemObjectColumn:d5", 
"FastItemIntColumn:d6", 
"FastItemsSource:d7", 
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
"ColumnReference:eo", 
"FastItemsSourceReference:ep", 
"IFastItemsSourceProvider:eq", 
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
"CanvasRenderScheduler:fl", 
"ISchedulableRender:fm", 
"RenderingContext:fn", 
"PathFigureCollection:f1", 
"PathSegment:f6", 
"PathSegmentType:f7", 
"PathFigure:f9", 
"PathSegmentCollection:ga", 
"LineSegment:gb", 
"PolyBezierSegment:ge", 
"MathUtil:g3", 
"RuntimeHelpers:g4", 
"RuntimeFieldHandle:g5", 
"Random:g8", 
"Func$1:ib", 
"TrendCalculators:io", 
"TrendLineType:ip", 
"UnknownValuePlotting:iq", 
"Numeric:jl", 
"LeastSquaresFit:jm", 
"AbstractEnumerable:kp", 
"AbstractEnumerator:kq", 
"GenericEnumerable$1:kr", 
"GenericEnumerator$1:ks"]);


$.ig.util.defType('FastItemsSourceEventAction', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Remove";
			case 1: return "Insert";
			case 2: return "Replace";
			case 3: return "Change";
			case 4: return "Reset";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('FastItemsSourceEventAction', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('UnknownValuePlotting', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "LinearInterpolate";
			case 1: return "DontPlot";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('UnknownValuePlotting', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('TrendLineType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "LinearFit";
			case 2: return "QuadraticFit";
			case 3: return "CubicFit";
			case 4: return "QuarticFit";
			case 5: return "QuinticFit";
			case 6: return "LogarithmicFit";
			case 7: return "ExponentialFit";
			case 8: return "PowerLawFit";
			case 9: return "SimpleAverage";
			case 10: return "ExponentialAverage";
			case 11: return "ModifiedAverage";
			case 12: return "CumulativeAverage";
			case 13: return "WeightedAverage";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('TrendLineType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('EventProxy', 'Object', {
	init: function () {
		this.__isInteractionDisabled = false;
		$.ig.Object.prototype.init.call(this);
	},
	onMouseWheel: null,
	onPinchStarted: null,
	onPinchDelta: null,
	onGestureCompleted: null,
	onZoomStarted: null,
	onZoomDelta: null,
	onZoomCompleted: null,
	onFlingStarted: null,
	onContactStarted: null,
	onDragStarted: null,
	onContactMoved: null,
	onDragDelta: null,
	onContactCompleted: null,
	onDragCompleted: null,
	onMouseLeave: null,
	onMouseOver: null,
	onMouseEnter: null,
	onMouseDown: null,
	onMouseUp: null,
	onDoubleTap: null,
	onHold: null,
	onKeyDown: null,
	onKeyUp: null,
	_viewport: null,
	viewport: function (value) {
		if (arguments.length === 1) {
			this._viewport = value;
			return value;
		} else {
			return this._viewport;
		}
	}
	,
	_currentModifiers: 0,
	currentModifiers: function (value) {
		if (arguments.length === 1) {
			this._currentModifiers = value;
			return value;
		} else {
			return this._currentModifiers;
		}
	}
	,
	_rightButton: false,
	rightButton: function (value) {
		if (arguments.length === 1) {
			this._rightButton = value;
			return value;
		} else {
			return this._rightButton;
		}
	}
	,
	_shouldInteract: null,
	shouldInteract: function (value) {
		if (arguments.length === 1) {
			this._shouldInteract = value;
			return value;
		} else {
			return this._shouldInteract;
		}
	}
	,
	clone: function () {
	}
	,
	destroy: function () {
	}
	,
	supportsNonIsometricZoom: function () {
		return false;
	}
	,
	_originalEvent: null,
	originalEvent: function (value) {
		if (arguments.length === 1) {
			this._originalEvent = value;
			return value;
		} else {
			return this._originalEvent;
		}
	}
	,
	raiseOnMouseWheel: function (point, delta) {
		if (this.onMouseWheel != null && !this.isInteractionDisabled()) {
			return this.onMouseWheel(point, delta);
		}
		return false;
	}
	,
	raiseOnPinchStarted: function (point, scale) {
		if (this.onPinchStarted != null && !this.isInteractionDisabled()) {
			this.onPinchStarted(point, scale);
		}
	}
	,
	raiseOnZoomStarted: function (point, scaleX, scaleY, isoScaleDelta) {
		if (this.onZoomStarted != null && !this.isInteractionDisabled()) {
			this.onZoomStarted(point, scaleX, scaleY, isoScaleDelta);
		}
	}
	,
	raiseOnFlingStarted: function (point, velocityX, velocityY) {
		if (this.onFlingStarted != null && !this.isInteractionDisabled()) {
			return this.onFlingStarted(point, velocityX, velocityY);
		}
		return true;
	}
	,
	raiseOnPinchDelta: function (point, scale) {
		if (this.onPinchDelta != null && !this.isInteractionDisabled()) {
			this.onPinchDelta(point, scale);
		}
	}
	,
	raiseOnZoomDelta: function (point, scaleX, scaleY, isoScaleDelta) {
		if (this.onZoomDelta != null && !this.isInteractionDisabled()) {
			this.onZoomDelta(point, scaleX, scaleY, isoScaleDelta);
		}
	}
	,
	raiseOnZoomCompleted: function (point, scaleX, scaleY, isoScaleDelta) {
		if (this.onZoomCompleted != null && !this.isInteractionDisabled()) {
			this.onZoomCompleted(point, scaleX, scaleY, isoScaleDelta);
		}
	}
	,
	raiseOnGestureCompleted: function (point, scale) {
		if (this.onGestureCompleted != null && !this.isInteractionDisabled()) {
			this.onGestureCompleted(point, scale);
		}
	}
	,
	raiseOnContactStarted: function (point, isFinger) {
		if (this.onContactStarted != null && !this.isInteractionDisabled()) {
			this.onContactStarted(point, isFinger);
		}
	}
	,
	raiseOnDragStarted: function (point) {
		if (this.onDragStarted != null && !this.isInteractionDisabled()) {
			this.onDragStarted(point);
		}
	}
	,
	raiseOnContactMoved: function (point, isFinger) {
		if (this.onContactMoved != null && !this.isInteractionDisabled()) {
			this.onContactMoved(point, isFinger);
		}
	}
	,
	raiseOnDragDelta: function (point) {
		if (this.onDragDelta != null && !this.isInteractionDisabled()) {
			this.onDragDelta(point);
		}
	}
	,
	raiseOnContactCompleted: function (point, isFinger) {
		if (this.onContactCompleted != null && !this.isInteractionDisabled()) {
			this.onContactCompleted(point, isFinger);
		}
	}
	,
	raiseOnDragCompleted: function (point) {
		if (this.onDragCompleted != null && !this.isInteractionDisabled()) {
			this.onDragCompleted(point);
		}
	}
	,
	raiseOnMouseLeave: function (point) {
		if (this.onMouseLeave != null && !this.isInteractionDisabled()) {
			this.onMouseLeave(point);
		}
	}
	,
	raiseOnMouseOver: function (point, onMouseMove, isFinger) {
		if (this.onMouseOver != null && !this.isInteractionDisabled()) {
			this.onMouseOver(point, onMouseMove, isFinger);
		}
	}
	,
	raiseOnMouseEnter: function (point) {
		if (this.onMouseEnter != null && !this.isInteractionDisabled()) {
			this.onMouseEnter(point);
		}
	}
	,
	raiseOnMouseDown: function (point) {
		if (this.onMouseDown != null && !this.isInteractionDisabled()) {
			this.onMouseDown(point);
		}
	}
	,
	raiseOnMouseUp: function (point) {
		if (this.onMouseUp != null && !this.isInteractionDisabled()) {
			this.onMouseUp(point);
		}
	}
	,
	raiseOnKeyDown: function (key) {
		if (this.onKeyDown != null && !this.isInteractionDisabled()) {
			return this.onKeyDown(key);
		}
		return false;
	}
	,
	raiseOnKeyUp: function (key) {
		if (this.onKeyUp != null && !this.isInteractionDisabled()) {
			return this.onKeyUp(key);
		}
		return false;
	}
	,
	raiseOnDoubleTap: function (point) {
		if (this.onDoubleTap != null && !this.isInteractionDisabled()) {
			this.onDoubleTap(point);
		}
	}
	,
	raiseOnHold: function (point) {
		if (this.onHold != null && !this.isInteractionDisabled()) {
			this.onHold(point);
		}
	}
	,
	getSourceOffsets: function () {
	}
	,
	__isInteractionDisabled: false,
	isInteractionDisabled: function (value) {
		if (arguments.length === 1) {
			this.__isInteractionDisabled = value;
			this.onIsInteractionDisabledChanged();
			return value;
		} else {
			return this.__isInteractionDisabled;
		}
	}
	,
	_deferredTouchStartMode: false,
	deferredTouchStartMode: function (value) {
		if (arguments.length === 1) {
			this._deferredTouchStartMode = value;
			return value;
		} else {
			return this._deferredTouchStartMode;
		}
	}
	,
	_shouldInteractForDirection: null,
	shouldInteractForDirection: function (value) {
		if (arguments.length === 1) {
			this._shouldInteractForDirection = value;
			return value;
		} else {
			return this._shouldInteractForDirection;
		}
	}
	,
	onIsInteractionDisabledChanged: function () {
	}
	,
	bindToSource: function (source, sourceId) {
	}
	,
	unbindFromSource: function (source, sourceId) {
	}
	,
	$type: new $.ig.Type('EventProxy', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BaseDOMEventProxy', 'EventProxy', {
	init: function () {
		this.__touchCaptureEnabled = false;
		this.__mouseX = 0;
		this.__mouseY = 0;
		this.__containerMouseX = 0;
		this.__containerMouseY = 0;
		this.__numTouches = 0;
		this.__suppressMouseEvents = false;
		this.__suppressId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
		this.__touchStartDeferred = false;
		this.__mouseCaptured = false;
		$.ig.EventProxy.prototype.init.call(this);
	},
	_eventSource: null,
	eventSource: function (value) {
		if (arguments.length === 1) {
			this._eventSource = value;
			return value;
		} else {
			return this._eventSource;
		}
	}
	,
	clone: function () {
		var ret = new $.ig.DOMEventProxy(this.eventSource());
		ret.deferredTouchStartMode(this.deferredTouchStartMode());
		ret.shouldInteractForDirection(this.shouldInteractForDirection());
		return ret;
	}
	,
	preventDefault: function (e) {
		if (!this.isInteractionDisabled()) {
			if (this.isCancelable(e)) {
				e.preventDefault();
			}
		}
	}
	,
	isCancelable: function (e_) {
		return e_.originalEvent.cancelable || e_.originalEvent.cancelable == undefined;
	}
	,
	__touchCaptureEnabled: false,
	enableTouchCapture: function () {
		var source_ = this.eventSource()[0];
		$.ig.BaseDOMEventProxy.prototype.grabTouches(source_);
	}
	,
	grabTouches: function (source_) {
		if ($.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			source_.style.touchAction = 'none';
		} else {
			source_.style.msTouchAction = 'none';
		}
		source_.style.msUserSelect = 'none';
		source_.style.webkitTouchCallout = 'none';
		source_.style.webkitUserSelect = 'none';
		source_.style.khtmlUserSelect = 'none';
		source_.style.mozUserSelect = 'none';
		source_.style.userSelect = 'none';
	}
	,
	deferTouches: function (source_) {
		if ($.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			source_.style.touchAction = 'auto';
		} else {
			source_.style.msTouchAction = 'auto';
		}
		source_.style.msUserSelect = 'auto';
		source_.style.webkitTouchCallout = 'none';
		source_.style.webkitUserSelect = 'auto';
		source_.style.khtmlUserSelect = 'auto';
		source_.style.mozUserSelect = 'auto';
		source_.style.userSelect = 'auto';
	}
	,
	disableTouchCapture: function () {
		var source_ = this.eventSource()[0];
		$.ig.BaseDOMEventProxy.prototype.deferTouches(source_);
	}
	,
	onIsInteractionDisabledChanged: function () {
		$.ig.EventProxy.prototype.onIsInteractionDisabledChanged.call(this);
		if (!this.isInteractionDisabled()) {
			this.enableTouchCapture();
		} else {
			this.disableTouchCapture();
		}
	}
	,
	getTridentVersion: function () {
		var ver_ = -1;
		var matchIE_ = /Trident\/([\d.]+)/;
		if (matchIE_.exec(navigator.userAgent) != null) {
			ver_ = parseFloat(RegExp.$1);
		}
		return ver_;
	}
	,
	getEdgeVersion: function () {
		var ver_ = -1;
		var matchIE_ = /Edge\/([\d.]+)/;
		if (matchIE_.exec(navigator.userAgent) != null) {
			ver_ = parseFloat(RegExp.$1);
		}
		return ver_;
	}
	,
	fixEvent: function (e_) {
		var ieHack = $.ig.BaseDOMEventProxy.prototype.tridentVersion >= 6;
		var oe_ = e_.originalEvent;
		if (((typeof e_.pageX == 'undefined') || ieHack) && oe_.clientX != null) {
			var od_ = e_.target.ownerDocument;
			var ed_ = od_ ? od_ : document;
			var doc_ = ed_.documentElement;
			var body_ = ed_.body;
			var clientX_ = oe_.clientX;
			var clientY_ = oe_.clientY;
			var scrollLeft_ = doc_ && doc_.scrollLeft || body_ && body_.scrollLeft || 0;
			var scrollTop_ = doc_ && doc_.scrollTop || body_ && body_.scrollTop || 0;
			var clientLeft_ = doc_ && doc_.clientLeft || body_ && body_.clientLeft || 0;
			var clientTop_ = doc_ && doc_.clientTop || body_ && body_.clientTop || 0;
			e_.pageX = clientX_ + (scrollLeft_ - clientLeft_);
			e_.pageY = clientY_ + (scrollTop_ - clientTop_);
		}
		if (!e_.pageX) {
			e_.pageX = e_.originalEvent.pageX;
		}
		if (!e_.pageY) {
			e_.pageY = e_.originalEvent.pageY;
		}
	}
	,
	getOffset: function (source) {
		var ieHack = $.ig.BaseDOMEventProxy.prototype.tridentVersion >= 6;
		var source_ = source[0];
		var d_ = source_.ownerDocument;
		var doc_ = d_ ? d_.documentElement : null;
		var z_ = doc_ ? doc_.msContentZoomFactor : null;
		var body_ = doc_.body;
		if (z_ && z_ > 1 || ieHack) {
			var rect_ = source_.getBoundingClientRect();
			var x_ = rect_.left;
			var y_ = rect_.top;
			var scrollLeft_ = doc_ && doc_.scrollLeft || body_ && body_.scrollLeft || 0;
			var scrollTop_ = doc_ && doc_.scrollTop || body_ && body_.scrollTop || 0;
			var clientLeft_ = doc_ && doc_.clientLeft || body_ && body_.clientLeft || 0;
			var clientTop_ = doc_ && doc_.clientTop || body_ && body_.clientTop || 0;
			var left = x_ + scrollLeft_ - clientLeft_;
			var top = y_ + scrollTop_ - clientTop_;
			return new $.ig.Rect(0, left, top, 0, 0);
		} else {
			var offset = source.offset();
			return new $.ig.Rect(0, offset.left, offset.top, 0, 0);
		}
	}
	,
	__mouseX: 0,
	__mouseY: 0,
	__containerMouseX: 0,
	__containerMouseY: 0,
	__numTouches: 0,
	_mousePoint: null,
	mousePoint: function (value) {
		if (arguments.length === 1) {
			this._mousePoint = value;
			return value;
		} else {
			return this._mousePoint;
		}
	}
	,
	_containerMousePoint: null,
	containerMousePoint: function (value) {
		if (arguments.length === 1) {
			this._containerMousePoint = value;
			return value;
		} else {
			return this._containerMousePoint;
		}
	}
	,
	numTouches: function (value) {
		if (arguments.length === 1) {
			this.__numTouches = value;
			return value;
		} else {
			return this.__numTouches;
		}
	}
	,
	updateMousePosition: function (e) {
		this.originalEvent(e);
		$.ig.BaseDOMEventProxy.prototype.fixEvent(e);
		var offset = $.ig.BaseDOMEventProxy.prototype.getOffset(this.eventSource());
		this.__mouseX = e.pageX - offset.left();
		this.__mouseY = e.pageY - offset.top();
		this.__containerMouseX = this.__mouseX - this.viewport().left();
		this.__containerMouseY = this.__mouseY - this.viewport().top();
		this.mousePoint({ __x: this.__mouseX, __y: this.__mouseY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		this.containerMousePoint({ __x: this.__containerMouseX, __y: this.__containerMouseY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	updateTouchPosition: function (e_) {
		this.originalEvent(e_);
		$.ig.BaseDOMEventProxy.prototype.fixEvent(e_);
		if ($.ig.BaseDOMEventProxy.prototype.mSPointerEnabled || $.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			var pageX = e_.pageX;
			var pageY = e_.pageY;
			var offset = $.ig.BaseDOMEventProxy.prototype.getOffset(this.eventSource());
			this.__mouseX = pageX - offset.left();
			this.__mouseY = pageY - offset.top();
		} else {
			this.__numTouches = 0;
			if (!e_.originalEvent.targetTouches || e_.originalEvent.targetTouches.length < 1) { return; };
			this.__numTouches = e_.originalEvent.targetTouches.length;
			var pageX1 = e_.originalEvent.targetTouches[0].pageX;
			var pageY1 = e_.originalEvent.targetTouches[0].pageY;
			var offset1 = $.ig.BaseDOMEventProxy.prototype.getOffset(this.eventSource());
			this.__mouseX = pageX1 - offset1.left();
			this.__mouseY = pageY1 - offset1.top();
		}
		this.__containerMouseX = this.__mouseX - this.viewport().left();
		this.__containerMouseY = this.__mouseY - this.viewport().top();
		this.mousePoint({ __x: this.__mouseX, __y: this.__mouseY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		this.containerMousePoint({ __x: this.__containerMouseX, __y: this.__containerMouseY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	canvasMouseScroll: function (e_) {
		this.updateModifiers(e_);
		if (this.shouldInteract()(this.mousePoint())) {
			var delta_ = 0;
			if (e_.wheelDelta) { delta_ = e_.wheelDelta/120; };
			if (e_.originalEvent && e_.originalEvent.wheelDelta) { delta_ = e_.originalEvent.wheelDelta/120; };
			if (e_.detail) { delta_ = -e_.detail/3; };
			if (e_.originalEvent && e_.originalEvent.detail) { delta_ = -e_.originalEvent.detail/3; };
			delta_ = delta_ / 10;
			var handled = this.raiseOnMouseWheel(this.containerMousePoint(), delta_);
			if (handled) {
				this.preventDefault(e_);
			}
		}
	}
	,
	__suppressMouseEvents: false,
	__suppressId: 0,
	endMouseSuppress: function () {
		if (this.__suppressMouseEvents) {
			if (this.__suppressId == $.ig.BaseDOMEventProxy.prototype.nullTimer) {
				this.__suppressId = window.setTimeout(this.doEndSuppress.runOn(this), 500);
			} else {
				window.clearTimeout(this.__suppressId);
				this.__suppressId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
				this.__suppressId = window.setTimeout(this.doEndSuppress.runOn(this), 500);
			}
		}
	}
	,
	beginMouseSuppress: function () {
		this.__suppressMouseEvents = true;
		if (this.__suppressId != $.ig.BaseDOMEventProxy.prototype.nullTimer) {
			window.clearTimeout(this.__suppressId);
			this.__suppressId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
		}
	}
	,
	doEndSuppress: function () {
		this.__suppressMouseEvents = false;
	}
	,
	_mouseIsOver: false,
	mouseIsOver: function (value) {
		if (arguments.length === 1) {
			this._mouseIsOver = value;
			return value;
		} else {
			return this._mouseIsOver;
		}
	}
	,
	__touchStartDeferred: false,
	touchStartDeferred: function (value) {
		if (arguments.length === 1) {
			this.__touchStartDeferred = value;
			return value;
		} else {
			return this.__touchStartDeferred;
		}
	}
	,
	canvasMouseLeave: function (e) {
		if (this.__touchStartDeferred || this.__suppressMouseEvents) {
			return;
		}
		this.updateMousePosition(e);
		if (this.mouseIsOver()) {
			this.mouseIsOver(false);
			this.raiseOnMouseLeave(this.containerMousePoint());
		}
	}
	,
	canvasMouseMove: function (e) {
		if (this.__touchStartDeferred || this.__suppressMouseEvents) {
			return;
		}
		this.updateMousePosition(e);
		this.updateModifiers(e);
		var me = new $.ig.MouseEventArgs();
		me.position(this.mousePoint());
		if (this.shouldInteract()(this.mousePoint())) {
			if (!this.mouseIsOver()) {
				this.mouseIsOver(true);
				this.raiseOnMouseEnter(this.mousePoint());
			}
			this.raiseOnMouseOver(this.mousePoint(), true, false);
			this.raiseOnContactMoved(this.containerMousePoint(), false);
		} else if (this.mouseIsOver()) {
			this.canvasMouseLeave(e);
		}
	}
	,
	__mouseCaptured: false,
	canvasMouseDown: function (e) {
		if (this.__touchStartDeferred || this.__suppressMouseEvents) {
			return;
		}
		this.eventSource().focus();
		this.updateMousePosition(e);
		this.updateModifiers(e);
		if (this.shouldInteract()(this.mousePoint())) {
			this.__mouseCaptured = true;
			this.raiseOnMouseDown(this.mousePoint());
			this.raiseOnContactStarted(this.containerMousePoint(), false);
			this.preventDefault(e);
		}
	}
	,
	windowMouseUp: function (e) {
		if (this.__touchStartDeferred || this.__suppressMouseEvents) {
			return;
		}
		if (this.__mouseCaptured) {
			this.canvasMouseUp(e);
		}
	}
	,
	canvasMouseUp: function (e) {
		if (this.__touchStartDeferred || this.__suppressMouseEvents) {
			return;
		}
		this.updateModifiers(e);
		this.__mouseCaptured = false;
		this.raiseOnMouseUp(this.mousePoint());
		this.raiseOnContactCompleted(this.containerMousePoint(), false);
		this.preventDefault(e);
	}
	,
	canvasKeyDown: function (e) {
		this.canvasKeyEvent(e, true);
	}
	,
	canvasKeyUp: function (e) {
		this.canvasKeyEvent(e, false);
	}
	,
	getKey: function (e) {
		var downKey = $.ig.Key.prototype.none;
		switch (e.which) {
			case 33:
				downKey = $.ig.Key.prototype.pageUp;
				break;
			case 34:
				downKey = $.ig.Key.prototype.pageDown;
				break;
			case 36:
				downKey = $.ig.Key.prototype.home;
				break;
			case 37:
				downKey = $.ig.Key.prototype.left;
				break;
			case 38:
				downKey = $.ig.Key.prototype.up;
				break;
			case 39:
				downKey = $.ig.Key.prototype.right;
				break;
			case 40:
				downKey = $.ig.Key.prototype.down;
				break;
			case 9:
				downKey = $.ig.Key.prototype.tab;
				break;
			case 32:
				downKey = $.ig.Key.prototype.space;
				break;
			case 13:
				downKey = $.ig.Key.prototype.enter;
				break;
			case 27:
				downKey = $.ig.Key.prototype.escape;
				break;
			case 16:
				downKey = $.ig.Key.prototype.shift;
				break;
			case 17:
				downKey = $.ig.Key.prototype.ctrl;
				break;
			case 18:
				downKey = $.ig.Key.prototype.alt;
				break;
			default:
				if (112 <= e.which && e.which <= 123) {
					downKey = ($.ig.Key.prototype.f1 + e.which - 112);
				}
				break;
		}
		return downKey;
	}
	,
	canvasKeyEvent: function (e, isDown) {
		var downKey = $.ig.BaseDOMEventProxy.prototype.getKey(e);
		var send = downKey != $.ig.Key.prototype.none;
		this.updateModifiers(e);
		if (send && this.shouldInteract()(this.mousePoint())) {
			var handled = false;
			if (isDown) {
				handled = this.raiseOnKeyDown(downKey);
			} else {
				handled = this.raiseOnKeyUp(downKey);
			}
			if (handled) {
				this.preventDefault(e);
			}
		}
	}
	,
	updateModifiers: function (e) {
		this.originalEvent(e);
		this.currentModifiers($.ig.ModifierKeys.prototype.none);
		if (e.shiftKey) {
			this.currentModifiers(this.currentModifiers() | $.ig.ModifierKeys.prototype.shift);
		}
		if (e.altKey) {
			this.currentModifiers(this.currentModifiers() | $.ig.ModifierKeys.prototype.alt);
		}
		if (e.ctrlKey) {
			this.currentModifiers(this.currentModifiers() | $.ig.ModifierKeys.prototype.control);
		}
		this.rightButton(e.button == 2);
	}
	,
	getSourceOffsets: function () {
		var offset = $.ig.BaseDOMEventProxy.prototype.getOffset(this.eventSource());
		var x = offset.left();
		var y = offset.top();
		return { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	$type: new $.ig.Type('BaseDOMEventProxy', $.ig.EventProxy.prototype.$type)
}, true);

$.ig.util.defType('DOMEventProxy', 'BaseDOMEventProxy', {
	__proxyID: 0,
	__eventNS: null,
	__pinching: false,
	_mGesture: null,
	mGesture: function (value) {
		if (arguments.length === 1) {
			this._mGesture = value;
			return value;
		} else {
			return this._mGesture;
		}
	}
	,
	init: function (DOMEventSource) {
		this.__proxyID = 0;
		this.__eventNS = "";
		this.__pinching = false;
		this.__lastScale = 1;
		this.__holdId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
		this.__tapArea = null;
		this.__holdStartX = 0;
		this.__holdStartY = 0;
		this.__holdCancelDistance = 5;
		this.__dragging = false;
		this.__dragStart = null;
		$.ig.BaseDOMEventProxy.prototype.init.call(this);
		this.shouldInteractForDirection(function (p) { return true; });
		this.deferredTouchStartMode(false);
		$.ig.DOMEventProxy.prototype.__proxyCount++;
		this.__proxyID = $.ig.DOMEventProxy.prototype.__proxyCount;
		this.__eventNS = ".DOMProxy" + this.__proxyID.toString();
		this.currentModifiers($.ig.ModifierKeys.prototype.none);
		this.eventSource(DOMEventSource);
		$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled = false;
		try {
			$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled = window.navigator.msPointerEnabled && MSGesture !== undefined;
		}
		catch (e) {
		}
		$.ig.BaseDOMEventProxy.prototype.pointerEnabled = false;
		try {
			$.ig.BaseDOMEventProxy.prototype.pointerEnabled = window.navigator.pointerEnabled;
		}
		catch (e1) {
		}
		$.ig.BaseDOMEventProxy.prototype.tridentVersion = this.getTridentVersion();
		$.ig.BaseDOMEventProxy.prototype.edgeVersion = this.getEdgeVersion();
		this.bindToSource(this.eventSource(), "");
		this.shouldInteract(function (p) { return true; });
	},
	bindToSource: function (objSource, sourceID) {
		var source = objSource;
		var ns = this.__eventNS + sourceID;
		if ((!$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled && !$.ig.BaseDOMEventProxy.prototype.pointerEnabled)) {
			source.bind("mousemove" + ns, this.canvasMouseMove.runOn(this));
			source.bind("mouseleave" + ns, this.canvasMouseLeave.runOn(this));
			source.bind("mousedown" + ns, this.canvasMouseDown.runOn(this));
			source.bind("mouseup" + ns, this.canvasMouseUp.runOn(this));
			$(window).bind("mouseup" + ns, this.windowMouseUp.runOn(this));
		}
		source.bind("keydown" + ns, this.canvasKeyDown.runOn(this));
		source.bind("keyup" + ns, this.canvasKeyUp.runOn(this));
		var source_ = this.eventSource()[0];
		if (!this.isInteractionDisabled()) {
			$.ig.BaseDOMEventProxy.prototype.grabTouches(source_);
		}
		if ($.ig.BaseDOMEventProxy.prototype.mSPointerEnabled || $.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			var container = this.eventSource()[0];
			if (window.MSGesture !== undefined) {
				var gesture = new MSGesture();
				gesture.target = container;
				this.mGesture(gesture);
				source.bind("MSGestureStart" + ns, this.canvasGestureStart.runOn(this));
				source.bind("MSGestureChange" + ns, this.canvasGestureChange.runOn(this));
				source.bind("MSGestureEnd" + ns, this.canvasGestureEnd.runOn(this));
			}
			if ($.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
				source.bind("pointerdown" + ns, this.canvasMSPointerDown.runOn(this));
				source.bind("pointerup" + ns, this.canvasMSPointerUp.runOn(this));
				source.bind("pointercancel" + ns, this.canvasMSPointerCancel.runOn(this));
				source.bind("pointermove" + ns, this.canvasMSPointerMove.runOn(this));
				source.bind("pointerout" + ns, this.canvasMSPointerOut.runOn(this));
				source.bind("lostpointercapture" + ns, this.canvasMSLostPointerCapture.runOn(this));
			} else {
				source.bind("MSPointerDown" + ns, this.canvasMSPointerDown.runOn(this));
				source.bind("MSPointerUp" + ns, this.canvasMSPointerUp.runOn(this));
				source.bind("MSPointerCancel" + ns, this.canvasMSPointerCancel.runOn(this));
				source.bind("MSPointerMove" + ns, this.canvasMSPointerMove.runOn(this));
				source.bind("MSPointerOut" + ns, this.canvasMSPointerOut.runOn(this));
				source.bind("MSLostPointerCapture" + ns, this.canvasMSLostPointerCapture.runOn(this));
			}
		}
		source.bind("DOMMouseScroll" + ns, this.canvasMouseScroll.runOn(this));
		source.bind("mousewheel" + ns, this.canvasMouseScroll.runOn(this));
		source.bind("gesturestart" + ns, this.canvasGestureStart.runOn(this));
		source.bind("gesturechange" + ns, this.canvasGestureChange.runOn(this));
		source.bind("gestureend" + ns, this.canvasGestureEnd.runOn(this));
		source.bind("touchstart" + ns, this.canvasTouchStart.runOn(this));
		source.bind("touchmove" + ns, this.canvasTouchMove.runOn(this));
		source.bind("touchend" + ns, this.canvasTouchEnd.runOn(this));
	}
	,
	__lastScale: 0,
	canvasMSPointerUp: function (e_) {
		var isFinger = this.isFinger(e_);
		if (isFinger) {
			this.numTouches(this.numTouches() - 1);
			if (this.numTouches() < 0) {
				this.numTouches(0);
			}
		}
		if (this.numTouches() < 2 && this.__pinching && isFinger) {
			this.__pinching = false;
			var scale = e_.originalEvent.scale;
			this.raiseOnGestureCompleted(this.containerMousePoint(), scale);
		} else {
			if (isFinger) {
				this.canvasTouchEnd(e_);
			} else {
				this.canvasMouseUp(e_);
			}
		}
	}
	,
	canvasMSLostPointerCapture: function (e_) {
		var isFinger = this.isFinger(e_);
		if (isFinger) {
			(function ($obj) { var $value = $obj.numTouches(); $obj.numTouches($value - 1); return $value; }(this));
			if (this.numTouches() < 0) {
				this.numTouches(0);
			}
		}
		if (this.numTouches() < 2 && this.__pinching && isFinger) {
			this.__pinching = false;
			var scale = e_.originalEvent.scale;
			this.raiseOnGestureCompleted(this.containerMousePoint(), scale);
		}
	}
	,
	canvasMSPointerCancel: function (e_) {
		var isFinger = this.isFinger(e_);
		if (isFinger) {
			(function ($obj) { var $value = $obj.numTouches(); $obj.numTouches($value - 1); return $value; }(this));
			if (this.numTouches() < 0) {
				this.numTouches(0);
			}
		}
		if (this.numTouches() < 2 && this.__pinching && isFinger) {
			this.__pinching = false;
			var scale = e_.originalEvent.scale;
			this.raiseOnGestureCompleted(this.containerMousePoint(), scale);
		}
	}
	,
	canvasMSPointerDown: function (e_) {
		var isFinger = this.isFinger(e_);
		if (this.mGesture() != null && isFinger) {
			(function ($obj) { var $value = $obj.numTouches(); $obj.numTouches($value + 1); return $value; }(this));
			this.mGesture().addPointer(e_.originalEvent.pointerId);
		}
		var eventSource_ = this.eventSource()[0];
		if ($.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			eventSource_.setPointerCapture(e_.originalEvent.pointerId);
		} else {
			eventSource_.msSetPointerCapture(e_.originalEvent.pointerId);
		}
		if (this.numTouches() > 1 && !this.__pinching && isFinger) {
			this.__pinching = true;
			this.updateTouchPosition(e_);
			var scale = e_.originalEvent.scale;
			this.raiseOnPinchStarted(this.containerMousePoint(), scale);
		} else {
			if (isFinger) {
				this.canvasTouchStart(e_);
			} else {
				this.canvasMouseDown(e_);
			}
		}
	}
	,
	isFinger: function (e_) {
		var pointerEvent_ = e_.originalEvent;
		var isFinger = false;
		if ($.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
			isFinger = pointerEvent_.pointerType == 'touch';
		} else {
			isFinger = pointerEvent_.pointerType == pointerEvent_.MSPOINTER_TYPE_TOUCH;
		}
		return isFinger;
	}
	,
	canvasMSPointerMove: function (e_) {
		var isFinger = this.isFinger(e_);
		if (this.__pinching) {
			return;
		}
		if (isFinger) {
			this.canvasTouchMove(e_);
		} else {
			this.canvasMouseMove(e_);
		}
	}
	,
	canvasMSPointerOut: function (e_) {
		var isFinger = this.isFinger(e_);
		if (this.__pinching) {
			return;
		}
		if (isFinger) {
		} else {
			this.canvasMouseLeave(e_);
		}
	}
	,
	canvasGestureStart: function (e_) {
		this.mouseIsOver(true);
		this.updateModifiers(e_);
		this.updateTouchPosition(e_);
		this.dragStopHoldTimer();
		if (this.shouldInteract()(this.mousePoint())) {
			this.preventDefault(e_);
			if ($.ig.BaseDOMEventProxy.prototype.mSPointerEnabled || $.ig.BaseDOMEventProxy.prototype.pointerEnabled) {
				this.__lastScale = 1;
			}
			if (($.ig.BaseDOMEventProxy.prototype.mSPointerEnabled || $.ig.BaseDOMEventProxy.prototype.pointerEnabled) && this.numTouches() < 2) {
				return;
			}
			var scale = e_.originalEvent.scale;
			this.raiseOnPinchStarted(this.containerMousePoint(), scale);
		}
	}
	,
	canvasGestureChange: function (e_) {
		this.mouseIsOver(true);
		this.updateModifiers(e_);
		this.updateTouchPosition(e_);
		if (this.shouldInteract()(this.mousePoint())) {
			this.preventDefault(e_);
			if (($.ig.BaseDOMEventProxy.prototype.mSPointerEnabled || $.ig.BaseDOMEventProxy.prototype.pointerEnabled) && this.numTouches() < 2) {
				return;
			}
			var scale = e_.originalEvent.scale;
			this.raiseOnPinchDelta(this.containerMousePoint(), scale);
		}
	}
	,
	canvasGestureEnd: function (e_) {
		this.preventDefault(e_);
		var scale = e_.originalEvent.scale;
		this.mouseIsOver(false);
		this.updateModifiers(e_);
		this.updateTouchPosition(e_);
		if ((!$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled && !$.ig.BaseDOMEventProxy.prototype.pointerEnabled) || this.__pinching) {
			this.raiseOnGestureCompleted(this.containerMousePoint(), scale);
		}
	}
	,
	__touchContainerStart: null,
	canvasTouchStart: function (e_) {
		this.mouseIsOver(true);
		this.updateModifiers(e_);
		this.updateTouchPosition(e_);
		this.__touchContainerStart = this.containerMousePoint();
		if (this.shouldInteract()(this.mousePoint())) {
			if (!this.deferredTouchStartMode()) {
				this.preventDefault(e_);
			} else {
				this.touchStartDeferred(true);
			}
			this.raiseOnMouseOver(this.mousePoint(), false, true);
			this.raiseOnMouseDown(this.mousePoint());
			this.raiseOnContactStarted(this.containerMousePoint(), true);
			this.startHoldTimer();
		}
	}
	,
	__holdId: 0,
	__tapArea: null,
	__holdStartX: 0,
	__holdStartY: 0,
	__holdCancelDistance: 0,
	startHoldTimer: function () {
		if (this.__holdId == $.ig.BaseDOMEventProxy.prototype.nullTimer) {
			this.__holdStartX = this.mousePoint().__x;
			this.__holdStartY = this.mousePoint().__y;
			this.__holdId = window.setTimeout(this.onHoldTimer.runOn(this), 1500);
		}
	}
	,
	dragStopHoldTimer: function () {
		if (Math.abs(this.__holdStartX - this.mousePoint().__x) > this.__holdCancelDistance || Math.abs(this.__holdStartY - this.mousePoint().__y) > this.__holdCancelDistance) {
			this.stopHoldTimer();
		}
	}
	,
	stopHoldTimer: function () {
		if (this.__holdId != $.ig.BaseDOMEventProxy.prototype.nullTimer) {
			window.clearTimeout(this.__holdId);
			this.__holdId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
		}
	}
	,
	onHoldTimer: function () {
		this.__holdId = $.ig.BaseDOMEventProxy.prototype.nullTimer;
		if (this.touchStartDeferred()) {
			this.touchStartDeferred(false);
			this.beginMouseSuppress();
		}
		this.raiseOnHold(this.containerMousePoint());
	}
	,
	__dragging: false,
	__dragStart: null,
	canvasTouchMove: function (e) {
		this.mouseIsOver(true);
		this.updateModifiers(e);
		this.updateTouchPosition(e);
		if (this.touchStartDeferred()) {
			if (this.shouldInteract()(this.mousePoint()) && this.shouldAllowTouchDrag()) {
				this.touchStartDeferred(false);
				this.beginMouseSuppress();
			}
		}
		this.dragStopHoldTimer();
		this.stopTapTimer();
		if (this.shouldInteract()(this.mousePoint()) && this.numTouches() == 1 && !this.touchStartDeferred()) {
			this.preventDefault(e);
			if (!this.__dragging) {
				this.__dragging = true;
				this.__dragStart = this.containerMousePoint();
				this.raiseOnDragStarted(this.__dragStart);
			} else {
				this.raiseOnMouseOver(this.mousePoint(), true, true);
				this.raiseOnContactMoved(this.containerMousePoint(), true);
				this.raiseOnDragDelta(this.containerMousePoint());
			}
		}
	}
	,
	shouldAllowTouchDrag: function () {
		var direction = { __x: this.__touchContainerStart.__x - this.containerMousePoint().__x, __y: this.__touchContainerStart.__y - this.containerMousePoint().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		if (Math.abs(direction.__x) < 2 && Math.abs(direction.__y) < 2) {
			return false;
		}
		if (Math.abs(direction.__x) > Math.abs(direction.__y)) {
			direction.__y = 0;
		}
		if (Math.abs(direction.__y) > Math.abs(direction.__x)) {
			direction.__x = 0;
		}
		return this.shouldInteractForDirection()(direction);
	}
	,
	canvasTouchEnd: function (e) {
		this.mouseIsOver(false);
		this.updateModifiers(e);
		this.updateTouchPosition(e);
		this.stopHoldTimer();
		if (this.touchStartDeferred()) {
			this.touchStartDeferred(false);
			this.beginMouseSuppress();
		}
		this.preventDefault(e);
		this.raiseOnMouseOver(this.mousePoint(), false, true);
		this.raiseOnMouseUp(this.mousePoint());
		if (this.numTouches() == 0) {
			this.raiseDoubleTap(this.mousePoint());
			this.endMouseSuppress();
		}
		if (this.__dragging && this.numTouches() == 0) {
			this.__dragging = false;
			this.__dragStart = null;
			this.raiseOnDragCompleted(this.containerMousePoint());
			this.endMouseSuppress();
		}
		this.raiseOnContactCompleted(this.containerMousePoint(), true);
	}
	,
	raiseDoubleTap: function (pt) {
		if ($.ig.Rect.prototype.l_op_Equality(this.__tapArea, null)) {
			this.__tapArea = new $.ig.Rect(0, pt.__x - 50, pt.__y - 50, 100, 100);
			window.setTimeout(this.stopTapTimer.runOn(this), 500);
		} else {
			if (pt.__x >= this.__tapArea.x() && pt.__x <= this.__tapArea.right() && pt.__y >= this.__tapArea.y() && pt.__y <= this.__tapArea.bottom()) {
				this.stopTapTimer();
				this.raiseOnDoubleTap(pt);
			}
		}
	}
	,
	stopTapTimer: function () {
		this.__tapArea = null;
	}
	,
	unbindFromSource: function (objSource, sourceID) {
		var source = objSource;
		var ns = this.__eventNS + sourceID;
		source.unbind(ns);
		if ((!$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled && !$.ig.BaseDOMEventProxy.prototype.pointerEnabled)) {
			$(window).unbind(ns);
		}
	}
	,
	destroy: function () {
		if (this.eventSource() == null) {
			return;
		}
		this.eventSource().unbind(this.__eventNS);
		if ((!$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled && !$.ig.BaseDOMEventProxy.prototype.pointerEnabled)) {
			$(window).unbind(this.__eventNS);
		}
		this.eventSource(null);
	}
	,
	$type: new $.ig.Type('DOMEventProxy', $.ig.BaseDOMEventProxy.prototype.$type)
}, true);

$.ig.util.defType('IFastItemColumnInternal', 'Object', {
	$type: new $.ig.Type('IFastItemColumnInternal', null)
}, true);

$.ig.util.defType('IFastItemColumnPropertyName', 'Object', {
	$type: new $.ig.Type('IFastItemColumnPropertyName', null)
}, true);

$.ig.util.defType('IFastItemColumn$1', 'Object', {
	$type: new $.ig.Type('IFastItemColumn$1', null, [$.ig.IList$1.prototype.$type.specialize(0), $.ig.IFastItemColumnPropertyName.prototype.$type])
}, true);

$.ig.util.defType('FastItemColumn', 'Object', {
	__coerceValue: null,
	__expectFunctions: false,
	init: function (fastItemsSource, propertyName, coerceValue, expectFunctions) {
		this.__coerceValue = null;
		this.__expectFunctions = false;
		this.__propertyName = null;
		$.ig.Object.prototype.init.call(this);
		this.__coerceValue = coerceValue;
		this.__expectFunctions = expectFunctions;
		this.propertyName(propertyName);
		this.fastItemsSource(fastItemsSource);
	},
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			this.reset();
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	_fastItemsSource: null,
	__propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this.__propertyName = value;
			return value;
		} else {
			return this.__propertyName;
		}
	}
	,
	minimum: function (value) {
		if (arguments.length === 1) {
			this._minimum = value;
			return value;
		} else {
			if ($.ig.util.isNaN(this._minimum) && this.values() != null) {
				this._minimum = Number.POSITIVE_INFINITY;
				var en = this.values().getEnumerator();
				while (en.moveNext()) {
					var value = en.current();
					if (!$.ig.util.isNaN(value)) {
						this._minimum = Math.min(this._minimum, value);
					}
				}
			}
			return this._minimum;
		}
	}
	,
	_minimum: 0,
	maximum: function (value) {
		if (arguments.length === 1) {
			this._maximum = value;
			return value;
		} else {
			if ($.ig.util.isNaN(this._maximum) && this.values() != null) {
				this._maximum = Number.NEGATIVE_INFINITY;
				var en = this.values().getEnumerator();
				while (en.moveNext()) {
					var value = en.current();
					if (!$.ig.util.isNaN(value)) {
						this._maximum = Math.max(this._maximum, value);
					}
				}
			}
			return this._maximum;
		}
	}
	,
	_maximum: 0,
	item: function (index, value) {
		if (arguments.length === 2) {
			this.values().__inner[index] = value;
			return value;
		} else {
			return this.values().__inner[index];
		}
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	contains: function (item) {
		return this.values().contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.values().copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.values().count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	indexOf: function (item) {
		return this.values().indexOf(item);
	}
	,
	add: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	clear: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	remove: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	insert: function (index, item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	removeAt: function (index) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	reset: function () {
		this.values(null);
		this.minimum(NaN);
		this.maximum(NaN);
		return this.fastItemsSource() != null ? this.insertRange(0, this.fastItemsSource().count()) : true;
	}
	,
	insertRange: function (position, count) {
		var newValues = new Array(count);
		var source_ = this._fastItemsSource.asArray();
		var item_;
		var minimum = this.minimum();
		var maximum = this.maximum();
		var minimumIsNaN = $.ig.util.isNaN(this.minimum());
		var maximumIsNaN = $.ig.util.isNaN(this.maximum());
		var newCount = 0;
		var newValue;
		var coerce = this.__coerceValue;
		var sourceItem_ = null;
		if (this.__coerceValue != null || this.__expectFunctions) {
			for (var i_ = position; i_ < position + count; ++i_) {
				sourceItem_ = source_[i_];
				if (sourceItem_ == null) {
					item_ = NaN;
				} else {
					item_ = sourceItem_[this.__propertyName];
				}
				if (this.__expectFunctions) {
					if (typeof(item_) == 'function') {
						item_ = item_();
					}
				}
				if (coerce != null) {
					item_ = coerce(item_);
				}
				newValue = item_ == null ? NaN : item_;
				var valIsNaN = (newValue != newValue);
				if (minimumIsNaN || newValue < minimum) {
					minimum = newValue;
					minimumIsNaN = valIsNaN;
				}
				if (maximumIsNaN || newValue > maximum) {
					maximum = newValue;
					maximumIsNaN = valIsNaN;
				}
				newValues[newCount] = newValue;
				newCount++;
			}
		} else {
			for (var i_ = position; i_ < position + count; ++i_) {
				sourceItem_ = source_[i_];
				if (sourceItem_ == null) {
					item_ = NaN;
				} else {
					item_ = sourceItem_[this.__propertyName];
				}
				newValue = item_ == null ? NaN : item_;
				var valIsNaN1 = (newValue != newValue);
				if (minimumIsNaN || newValue < minimum) {
					minimum = newValue;
					minimumIsNaN = valIsNaN1;
				}
				if (maximumIsNaN || newValue > maximum) {
					maximum = newValue;
					maximumIsNaN = valIsNaN1;
				}
				newValues[newCount] = newValue;
				newCount++;
			}
		}
		this.minimum(minimum);
		this.maximum(maximum);
		if (this.values() == null) {
			this.values(new $.ig.List$1(Number, 1, newValues));
		} else {
			this.values().insertRange(position, newValues);
		}
		return true;
	}
	,
	removeRange: function (position, count) {
		for (var i = position; i < position + count && !$.ig.util.isNaN(this.minimum()) && !$.ig.util.isNaN(this.maximum()); ++i) {
			if (this.item(i) == this.minimum()) {
				this.minimum(NaN);
			}
			if (this.item(i) == this.maximum()) {
				this.maximum(NaN);
			}
		}
		this.values().removeRange(position, count);
		return true;
	}
	,
	replaceMinMax: function (oldValue, newValue) {
		if ($.ig.util.isNaN(oldValue)) {
			if (!$.ig.util.isNaN(newValue)) {
				if (!$.ig.util.isNaN(this.minimum())) {
					this.minimum(Math.min(newValue, this.minimum()));
				}
				if (!$.ig.util.isNaN(this.maximum())) {
					this.maximum(Math.max(newValue, this.maximum()));
				}
			}
			return;
		}
		if ($.ig.util.isNaN(newValue)) {
			this.minimum(!$.ig.util.isNaN(this.minimum()) && oldValue == this.minimum() ? NaN : this.minimum());
			this.maximum(!$.ig.util.isNaN(this.maximum()) && oldValue == this.maximum() ? NaN : this.maximum());
			return;
		}
		if (!$.ig.util.isNaN(this.minimum())) {
			if (oldValue == this.minimum() && newValue > this.minimum()) {
				this.minimum(NaN);
			} else {
				this.minimum(Math.min(newValue, this.minimum()));
			}
		}
		if (!$.ig.util.isNaN(this.maximum())) {
			if (oldValue == this.maximum() && newValue < this.maximum()) {
				this.maximum(NaN);
			} else {
				this.maximum(Math.max(newValue, this.maximum()));
			}
		}
	}
	,
	replaceRange: function (position, count) {
		var ret = false;
		for (var i = 0; i < count; ++i) {
			var oldValue = this.values().__inner[position + i];
			var newValue = this.toDouble(this.fastItemsSource().item(position + i));
			if (oldValue != newValue) {
				this.values().__inner[position + i] = newValue;
				ret = true;
				this.replaceMinMax(oldValue, newValue);
			}
		}
		return ret;
	}
	,
	_fastReflectionHelper: null,
	toDouble: function (item) {
		if (item == null) {
			return NaN;
		}
		var from_ = item;
		item = from_[this.__propertyName];
		if (this.__expectFunctions) {
			from_ = item;
			if (typeof(from_) == 'function') {
				item = from_();
			}
		}
		if (this.__coerceValue != null) {
			item = this.__coerceValue(item);
		}
		if (item == null) {
			return NaN;
		}
		return item;
		if (typeof item === 'number') {
			return item;
		}
	}
	,
	_values: null,
	values: function (value) {
		if (arguments.length === 1) {
			this._values = value;
			return value;
		} else {
			return this._values;
		}
	}
	,
	quickSort: function (list, comparison) {
		$.ig.FastItemColumn.prototype.quickSort1(list, 0, list.count() - 1, comparison);
	}
	,
	quickSort1: function (list, low, high, comparison) {
		if (low < high) {
			var pp = $.ig.FastItemColumn.prototype.partition(list, low, high, comparison);
			$.ig.FastItemColumn.prototype.quickSort1(list, low, pp - 1, comparison);
			$.ig.FastItemColumn.prototype.quickSort1(list, pp + 1, high, comparison);
		}
	}
	,
	partition: function (list, low, high, comparison) {
		var pivot = list.item(high);
		var ii = low;
		for (var jj = low; jj < high; jj++) {
			if (comparison(list.item(jj), pivot) <= 0) {
				var swapA = list.item(ii);
				list.item(ii, list.item(jj));
				list.item(jj, swapA);
				ii++;
			}
		}
		var swapB = list.item(ii);
		list.item(ii, list.item(high));
		list.item(high, swapB);
		return ii;
	}
	,
	getSortedIndices1: function (values, comparison) {
		var result = new $.ig.List$1($.ig.Number.prototype.$type, 2, values.count());
		for (var i = 0; i < values.count(); i++) {
			result.add(i);
		}
		if (values.count() < 22) {
			$.ig.FastItemColumn.prototype.quickSort(result, function (i1, i2) {
				var v1 = values.item($.ig.util.getValue(i1));
				var v2 = values.item($.ig.util.getValue(i2));
				return comparison(v1, v2);
			});
		} else {
			result.sort2(function (i1, i2) {
				var v1 = values.item(i1);
				var v2 = values.item(i2);
				return comparison(v1, v2);
			});
		}
		return result;
	}
	,
	getSortedIndices: function () {
		return $.ig.FastItemColumn.prototype.getSortedIndices1(this.values(), function (o1, o2) {
			var d1 = o1;
			var d2 = o2;
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return 1;
			}
			return 0;
		});
	}
	,
	asArray: function () {
		return this.values().asArray();
	}
	,
	$type: new $.ig.Type('FastItemColumn', $.ig.Object.prototype.$type, [$.ig.IFastItemColumnInternal.prototype.$type, $.ig.IFastItemColumn$1.prototype.$type.specialize(Number)])
}, true);

$.ig.util.defType('FastItemDateTimeColumn', 'Object', {
	__coerceValue: null,
	__expectFunctions: false,
	init: function (fastItemsSource, propertyName, coerceValue, expectFunctions) {
		this.__propertyName = null;
		this._hasMinimum = false;
		this._hasMaximum = false;
		$.ig.Object.prototype.init.call(this);
		this.__coerceValue = coerceValue;
		this.__expectFunctions = expectFunctions;
		this.propertyName(propertyName);
		this.fastItemsSource(fastItemsSource);
	},
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			this.reset();
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	_fastItemsSource: null,
	__propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this.__propertyName = value;
			return value;
		} else {
			return this.__propertyName;
		}
	}
	,
	_hasMinimum: false,
	_hasMaximum: false,
	minimum: function (value) {
		if (arguments.length === 1) {
			this._minimum = value;
			return value;
		} else {
			if (!this._hasMinimum && this.values() != null) {
				var en = this.values().getEnumerator();
				while (en.moveNext()) {
					var value = en.current();
					if (value < this._minimum) {
						this._minimum = value;
					}
				}
				if (this.values().count() > 0) {
					this._hasMinimum = true;
				}
			}
			return this._minimum;
		}
	}
	,
	_minimum: new Date(),
	maximum: function (value) {
		if (arguments.length === 1) {
			this._maximum = value;
			return value;
		} else {
			if (!this._hasMaximum && this.values() != null) {
				var en = this.values().getEnumerator();
				while (en.moveNext()) {
					var value = en.current();
					if (value > this._maximum) {
						this._maximum = value;
					}
				}
				if (this.values().count() > 0) {
					this._hasMaximum = true;
				}
			}
			return this._maximum;
		}
	}
	,
	_maximum: new Date(),
	item: function (index, value) {
		if (arguments.length === 2) {
			throw new $.ig.NotImplementedException(0);
			return value;
		} else {
			return this.values().__inner[index];
		}
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	contains: function (item) {
		return this.values().contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.values().copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.values().count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	indexOf: function (item) {
		return this.values().indexOf(item);
	}
	,
	add: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	clear: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	remove: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	insert: function (index, item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	removeAt: function (index) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	reset: function () {
		this.values(null);
		this._hasMinimum = false;
		this._hasMaximum = false;
		return this.fastItemsSource() != null ? this.insertRange(0, this.fastItemsSource().count()) : true;
	}
	,
	insertRange: function (position, count) {
		var newValues = new Array(count);
		var source_ = this._fastItemsSource.asArray();
		var item_;
		var minimum = this.minimum();
		var maximum = this.maximum();
		var newValue;
		var newCount = 0;
		var coerce = this.__coerceValue;
		if (this.__coerceValue != null || this.__expectFunctions) {
			for (var i_ = position; i_ < position + count; ++i_) {
				item_ = source_[i_][this.__propertyName];
				if (this.__expectFunctions) {
					if (typeof(item_) == 'function') {
						item_ = item_();
					}
				}
				if (coerce != null) {
					item_ = coerce(item_);
				}
				newValue = item_ == null ? new Date() : item_;
				if (!this._hasMinimum) {
					minimum = newValue;
					this._hasMinimum = true;
				} else if (newValue < minimum) {
					minimum = newValue;
				}
				if (!this._hasMaximum) {
					maximum = newValue;
					this._hasMaximum = true;
				} else if (newValue > maximum) {
					maximum = newValue;
				}
				newValues[newCount] = newValue;
				newCount++;
			}
		} else {
			for (var i_ = position; i_ < position + count; ++i_) {
				item_ = source_[i_][this.__propertyName];
				newValue = item_ == null ? new Date() : item_;
				if (!this._hasMinimum) {
					minimum = newValue;
					this._hasMinimum = true;
				} else if (newValue < minimum) {
					minimum = newValue;
				}
				if (!this._hasMaximum) {
					maximum = newValue;
					this._hasMaximum = true;
				} else if (newValue > maximum) {
					maximum = newValue;
				}
				newValues[newCount] = newValue;
				newCount++;
			}
		}
		this.minimum(minimum);
		this.maximum(maximum);
		if (this.values() == null) {
			this.values(new $.ig.List$1($.ig.Date.prototype.$type, 1, newValues));
		} else {
			this.values().insertRange(position, newValues);
		}
		return true;
	}
	,
	removeRange: function (position, count) {
		for (var i = position; i < position + count; ++i) {
			if (+(this.item(i)) == +(this.minimum())) {
				this._hasMinimum = false;
			}
			if (+(this.item(i)) == +(this.maximum())) {
				this._hasMaximum = false;
			}
		}
		this.values().removeRange(position, count);
		return true;
	}
	,
	replaceMinMax: function (oldValue, newValue) {
		if (+oldValue != +($.ig.Date.prototype.minValue())) {
			if (+newValue != +($.ig.Date.prototype.minValue())) {
				this.minimum(newValue < this.minimum() ? newValue : this.minimum());
				this.maximum(newValue > this.maximum() ? newValue : this.maximum());
			}
			return;
		}
		this.minimum(newValue < this.minimum() ? newValue : this.minimum());
		this.maximum(newValue > this.maximum() ? newValue : this.maximum());
	}
	,
	replaceRange: function (position, count) {
		var ret = false;
		for (var i = 0; i < count; ++i) {
			var oldValue = this.values().__inner[position + i];
			var newValue = this.toDateTime(this.fastItemsSource().item(position + i));
			if (+oldValue != +newValue) {
				this.values().__inner[position + i] = newValue;
				ret = true;
				this.replaceMinMax(oldValue, newValue);
			}
		}
		return ret;
	}
	,
	_fastReflectionHelper: null,
	toDateTime: function (item) {
		if (item == null) {
			return $.ig.Date.prototype.minValue();
		}
		var from_ = item;
		item = from_[this.__propertyName];
		if (this.__expectFunctions) {
			from_ = item;
			if (typeof(from_) == 'function') {
				item = from_();
			}
		}
		if (this.__coerceValue != null) {
			item = this.__coerceValue(item);
		}
		if (item == null) {
			return $.ig.Date.prototype.minValue();
		}
		return item;
	}
	,
	_values: null,
	values: function (value) {
		if (arguments.length === 1) {
			this._values = value;
			return value;
		} else {
			return this._values;
		}
	}
	,
	getSortedIndices: function () {
		return $.ig.FastItemColumn.prototype.getSortedIndices1(this.values(), function (o1, o2) {
			var d1 = o1;
			var d2 = o2;
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return 1;
			}
			return 0;
		});
	}
	,
	asArray: function () {
		return this.values().asArray();
	}
	,
	$type: new $.ig.Type('FastItemDateTimeColumn', $.ig.Object.prototype.$type, [$.ig.IFastItemColumnInternal.prototype.$type, $.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Date.prototype.$type)])
}, true);

$.ig.util.defType('FastItemObjectColumn', 'Object', {
	__coerceValue: null,
	__expectFunctions: false,
	init: function (fastItemsSource, propertyName, coerceValue, expectFunctions) {
		$.ig.Object.prototype.init.call(this);
		this.__coerceValue = coerceValue;
		this.__expectFunctions = expectFunctions;
		this.propertyName(propertyName);
		this.fastItemsSource(fastItemsSource);
	},
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			this.reset();
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	_fastItemsSource: null,
	__propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this.__propertyName = value;
			return value;
		} else {
			return this.__propertyName;
		}
	}
	,
	minimum: function (value) {
		if (arguments.length === 1) {
			this.__minimum = value;
			return value;
		} else {
			return this.__minimum;
		}
	}
	,
	__minimum: null,
	maximum: function (value) {
		if (arguments.length === 1) {
			this.__maximum = value;
			return value;
		} else {
			return this.__maximum;
		}
	}
	,
	__maximum: null,
	item: function (index, value) {
		if (arguments.length === 2) {
			throw new $.ig.NotImplementedException(0);
			return value;
		} else {
			return this.values().__inner[index];
		}
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	contains: function (item) {
		return this.values().contains1(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.values().copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.values().count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	indexOf: function (item) {
		return this.values().indexOf1(item);
	}
	,
	add: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	clear: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	remove: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	insert: function (index, item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	removeAt: function (index) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	reset: function () {
		this.values(null);
		return this.fastItemsSource() != null ? this.insertRange(0, this.fastItemsSource().count()) : true;
	}
	,
	insertRange: function (position, count) {
		var newValues = (function () {
			var $ret = new $.ig.List$1($.ig.Object.prototype.$type, 0);
			$ret.capacity(count);
			return $ret;
		}());
		for (var i = position; i < position + count; ++i) {
			var newValue = this.toObject(this.fastItemsSource().item(i));
			newValues.add1(newValue);
		}
		if (this.values() == null) {
			this.values(newValues);
		} else {
			this.values().insertRange(position, newValues);
		}
		return true;
	}
	,
	replaceRange: function (position, count) {
		var ret = false;
		for (var i = 0; i < count; ++i) {
			var oldValue = this.values().__inner[position + i];
			var newValue = this.toObject(this.fastItemsSource().item(position + i));
			if (oldValue != newValue) {
				this.values().__inner[position + i] = newValue;
				ret = true;
			}
		}
		return ret;
	}
	,
	removeRange: function (position, count) {
		this.values().removeRange(position, count);
		return true;
	}
	,
	_fastReflectionHelper: null,
	toObject: function (item) {
		if (item == null) {
			return null;
		}
		var from_ = item;
		item = from_[this.__propertyName];
		if (this.__expectFunctions) {
			from_ = item;
			if (typeof(from_) == 'function') {
				item = from_();
			}
		}
		if (this.__coerceValue != null) {
			item = this.__coerceValue(item);
		}
		return item;
	}
	,
	_values: null,
	values: function (value) {
		if (arguments.length === 1) {
			this._values = value;
			return value;
		} else {
			return this._values;
		}
	}
	,
	getSortedIndices: function () {
		return $.ig.FastItemColumn.prototype.getSortedIndices1(this.values(), function (o1, o2) {
			var d1 = parseFloat(o1);
			var d2 = parseFloat(o2);
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return 1;
			}
			return 0;
		});
	}
	,
	asArray: function () {
		return this.values().asArray();
	}
	,
	$type: new $.ig.Type('FastItemObjectColumn', $.ig.Object.prototype.$type, [$.ig.IFastItemColumnInternal.prototype.$type, $.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Object.prototype.$type)])
}, true);

$.ig.util.defType('FastItemIntColumn', 'Object', {
	__coerceValue: null,
	__expectFunctions: false,
	init: function (fastItemsSource, propertyName, coerceValue, expectFunctions) {
		this.__propertyName = null;
		$.ig.Object.prototype.init.call(this);
		this.__coerceValue = coerceValue;
		this.__expectFunctions = expectFunctions;
		this.propertyName(propertyName);
		this.fastItemsSource(fastItemsSource);
	},
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			this.reset();
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	_fastItemsSource: null,
	__propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this.__propertyName = value;
			return value;
		} else {
			return this.__propertyName;
		}
	}
	,
	minimum: function (value) {
		if (arguments.length === 1) {
			this.__minimum = value;
			return value;
		} else {
			return this.__minimum;
		}
	}
	,
	__minimum: 0,
	maximum: function (value) {
		if (arguments.length === 1) {
			this.__maximum = value;
			return value;
		} else {
			return this.__maximum;
		}
	}
	,
	__maximum: 0,
	item: function (index, value) {
		if (arguments.length === 2) {
			throw new $.ig.NotImplementedException(0);
			return value;
		} else {
			return this.values().__inner[index];
		}
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.values().getEnumerator();
	}
	,
	contains: function (item) {
		return this.values().contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.values().copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.values().count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	indexOf: function (item) {
		return this.values().indexOf(item);
	}
	,
	add: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	clear: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	remove: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	insert: function (index, item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	removeAt: function (index) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	reset: function () {
		this.values(null);
		return this.fastItemsSource() != null ? this.insertRange(0, this.fastItemsSource().count()) : true;
	}
	,
	insertRange: function (position, count) {
		var newValues = new Array(count);
		var source_ = this._fastItemsSource.asArray();
		var item_;
		var minimum = this.minimum();
		var maximum = this.maximum();
		var newCount = 0;
		var coerce = this.__coerceValue;
		if (this.__coerceValue != null || this.__expectFunctions) {
			for (var i_ = position; i_ < position + count; ++i_) {
				item_ = source_[i_][this.__propertyName];
				if (this.__expectFunctions) {
					if (typeof(item_) == 'function') {
						item_ = item_();
					}
				}
				if (coerce != null) {
					item_ = coerce(item_);
				}
				var newValue = item_ == null ? 0 : $.ig.util.getValue(item_);
				newValues[newCount] = newValue;
				newCount++;
			}
		} else {
			for (var i_ = position; i_ < position + count; ++i_) {
				item_ = source_[i_][this.__propertyName];
				var newValue1 = item_ == null ? 0 : $.ig.util.getValue(item_);
				newValues[newCount] = newValue1;
				newCount++;
			}
		}
		if (this.values() == null) {
			this.values(new $.ig.List$1($.ig.Number.prototype.$type, 1, newValues));
		} else {
			this.values().insertRange(position, newValues);
		}
		return true;
	}
	,
	replaceRange: function (position, count) {
		var ret = false;
		for (var i = 0; i < count; ++i) {
			var oldValue = this.values().__inner[position + i];
			var newValue = this.toInt(this.fastItemsSource().item(position + i));
			if (oldValue != newValue) {
				this.values().__inner[position + i] = newValue;
				ret = true;
			}
		}
		return ret;
	}
	,
	removeRange: function (position, count) {
		this.values().removeRange(position, count);
		return true;
	}
	,
	_fastReflectionHelper: null,
	toInt: function (item) {
		var from_ = item;
		item = from_[this.__propertyName];
		if (this.__expectFunctions) {
			from_ = item;
			if (typeof(from_) == 'function') {
				item = from_();
			}
		}
		if (this.__coerceValue != null) {
			item = this.__coerceValue(item);
		}
		if (item == null) {
			return 0;
		}
		return $.ig.util.getValue(item);
	}
	,
	_values: null,
	values: function (value) {
		if (arguments.length === 1) {
			this._values = value;
			return value;
		} else {
			return this._values;
		}
	}
	,
	getSortedIndices: function () {
		return $.ig.FastItemColumn.prototype.getSortedIndices1(this.values(), function (o1, o2) {
			var d1 = $.ig.util.getValue(o1);
			var d2 = $.ig.util.getValue(o2);
			if (d1 < d2) {
				return -1;
			}
			if (d1 > d2) {
				return 1;
			}
			return 0;
		});
	}
	,
	asArray: function () {
		return this.values().asArray();
	}
	,
	$type: new $.ig.Type('FastItemIntColumn', $.ig.Object.prototype.$type, [$.ig.IFastItemColumnInternal.prototype.$type, $.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Number.prototype.$type)])
}, true);

$.ig.util.defType('IFastItemsSource', 'Object', {
	$type: new $.ig.Type('IFastItemsSource', null)
}, true);

$.ig.util.defType('FastItemsSource', 'Object', {
	init: function () {
		this._columns = new $.ig.Dictionary$2(String, $.ig.ColumnReference.prototype.$type, 0);
		this._contents = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		this._index = null;
		$.ig.Object.prototype.init.call(this);
	},
	event: null,
	raiseDataSourceEvent: function (action, position, count) {
		if (this.event != null) {
			this.event(this, new $.ig.FastItemsSourceEventArgs(0, action, position, count));
		}
	}
	,
	raiseDataSourceEvent1: function (position, propertyName) {
		if (this.event != null) {
			this.event(this, new $.ig.FastItemsSourceEventArgs(1, position, propertyName));
		}
	}
	,
	itemsSource: function (value) {
		if (arguments.length === 1) {
			if (this._itemsSource == value) {
				return;
			}
			this.detach();
			this._itemsSource = value;
			this._contents.clear();
			this._index = null;
			this.attach();
			var en = this._columns.values().getEnumerator();
			while (en.moveNext()) {
				var referencedColumn = en.current();
				referencedColumn._fastItemColumn.reset();
			}
			this.raiseDataSourceEvent($.ig.FastItemsSourceEventAction.prototype.insert, 0, this._contents.count());
			return value;
		} else {
			return this._itemsSource;
		}
	}
	,
	detach: function () {
	}
	,
	onItemsSourceEventProxyWeakCollectionChanged: function (fastItemsSource, sender, e) {
		var fis = fastItemsSource;
		switch (e.action()) {
			case $.ig.NotifyCollectionChangedAction.prototype.add:
				fis.dataSourceAdd(e.newStartingIndex(), e.newItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.remove:
				fis.dataSourceRemove(e.oldStartingIndex(), e.oldItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.replace:
				fis.dataSourceReplace(e.newStartingIndex(), e.oldItems(), e.newItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.reset:
				fis.dataSourceReset();
				break;
		}
	}
	,
	attach: function () {
		this._contents.insertRange1(this._contents.count(), this._itemsSource);
	}
	,
	dataSourceAdd: function (position, newItems) {
		if (this._index != null) {
			for (var i = 0; i < newItems.count(); ++i) {
				this._index.add(newItems.item(i), position + i);
			}
			for (var i1 = position; i1 < this._contents.count(); ++i1) {
				this._index.item(this._contents.__inner[i1], i1 + newItems.count());
			}
		}
		this._contents.insertRange1(position, newItems);
		var en = this._columns.values().getEnumerator();
		while (en.moveNext()) {
			var referencedColumn = en.current();
			referencedColumn._fastItemColumn.insertRange(position, newItems.count());
		}
		this.raiseDataSourceEvent($.ig.FastItemsSourceEventAction.prototype.insert, position, newItems.count());
	}
	,
	dataSourceRemove: function (position, oldItems) {
		this._contents.removeRange(position, oldItems.count());
		if (this._index != null) {
			var en = oldItems.getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				this._index.remove(item);
			}
			for (var i = position; i < this._contents.count(); ++i) {
				this._index.item(this._contents.__inner[i], i);
			}
		}
		var en1 = this._columns.values().getEnumerator();
		while (en1.moveNext()) {
			var referencedColumn = en1.current();
			referencedColumn._fastItemColumn.removeRange(position, oldItems.count());
		}
		this.raiseDataSourceEvent($.ig.FastItemsSourceEventAction.prototype.remove, position, oldItems.count());
	}
	,
	dataSourceReplace: function (position, oldItems, newItems) {
		for (var i = 0; i < newItems.count(); ++i) {
			this._contents.__inner[position + i] = newItems.item(i);
		}
		if (this._index != null) {
			var en = oldItems.getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				this._index.remove(item);
			}
			for (var i1 = 0; i1 < newItems.count(); ++i1) {
				this._index.add(newItems.item(i1), position + i1);
			}
		}
		var en1 = this._columns.values().getEnumerator();
		while (en1.moveNext()) {
			var referencedColumn = en1.current();
			referencedColumn._fastItemColumn.replaceRange(position, newItems.count());
		}
		this.raiseDataSourceEvent($.ig.FastItemsSourceEventAction.prototype.replace, position, oldItems.count());
	}
	,
	dataSourceReset: function () {
		this._contents.clear();
		this._index = null;
		this._contents.insertRange1(0, this._itemsSource);
		var en = this._columns.values().getEnumerator();
		while (en.moveNext()) {
			var referencedColumn = en.current();
			referencedColumn._fastItemColumn.reset();
		}
		this.raiseDataSourceEvent($.ig.FastItemsSourceEventAction.prototype.reset, 0, this._contents.count());
	}
	,
	dataSourceChange: function (item, propertyName) {
		var $self = this;
		var columnReference = null;
		var position = this.indexOf(item);
		if (position == -1) {
			throw new $.ig.ArgumentException(1, "item");
		}
		if (String.isNullOrEmpty(propertyName)) {
			var en = this._columns.getEnumerator();
			while (en.moveNext()) {
				var col = en.current();
				col.value()._fastItemColumn.replaceRange(position, 1);
				this.raiseDataSourceEvent1(position, col.value()._fastItemColumn.propertyName());
			}
		} else {
			if ((function () { var $ret = $self._columns.tryGetValue(propertyName, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
				columnReference._fastItemColumn.replaceRange(position, 1);
			}
			if ((function () { var $ret = $self._columns.tryGetValue(propertyName + "_object", columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
				columnReference._fastItemColumn.replaceRange(position, 1);
			}
			this.raiseDataSourceEvent1(position, propertyName);
		}
	}
	,
	count: function () {
		return this._contents.count();
	}
	,
	item: function (i) {
		return this._contents.__inner[i];
	}
	,
	getEnumerator: function () {
		return this._contents.getEnumerator();
	}
	,
	indexOf: function (item) {
		var $self = this;
		var ret;
		if (this._index == null && this._contents.count() > 0) {
			this._index = new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.Number.prototype.$type, 0);
			var contents_ = this._contents;
			var index_ = this._index;
			for (var j_ = 0; j_ < this._contents.count(); j_++) {
				var o = contents_.__inner[j_];
				if (!this._index.containsKey(o)) {
					this._index.add(o, j_);
				}
			}
		}
		if ((function () { var $ret = $self._index.tryGetValue(item, ret); ret = $ret.p1; return $ret.ret; }())) {
			return ret;
		} else {
			return -1;
		}
	}
	,
	registerColumnDateTime: function (propertyName, coerceValue, expectFunctions) {
		var $self = this;
		var fastItemColumn = null;
		if (propertyName != null) {
			var columnReference = null;
			if (!(function () { var $ret = $self._columns.tryGetValue(propertyName, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
				columnReference = new $.ig.ColumnReference(new $.ig.FastItemDateTimeColumn(this, propertyName, coerceValue, expectFunctions));
				this._columns.add(propertyName, columnReference);
			}
			columnReference.references(columnReference.references() + 1);
			fastItemColumn = $.ig.util.cast($.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Date.prototype.$type), columnReference._fastItemColumn);
		}
		return fastItemColumn;
	}
	,
	registerColumnObject: function (propertyName, coerceValue, expectFunctions) {
		var $self = this;
		var fastItemColumn = null;
		var key = propertyName + "_object";
		if (propertyName != null) {
			var columnReference = null;
			if (!(function () { var $ret = $self._columns.tryGetValue(key, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
				columnReference = new $.ig.ColumnReference(new $.ig.FastItemObjectColumn(this, propertyName, coerceValue, expectFunctions));
				this._columns.add(key, columnReference);
			}
			columnReference.references(columnReference.references() + 1);
			fastItemColumn = $.ig.util.cast($.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Object.prototype.$type), columnReference._fastItemColumn);
		}
		return fastItemColumn;
	}
	,
	registerColumnInt: function (propertyName, coerceValue, expectFunctions) {
		var $self = this;
		var fastItemColumn = null;
		if (propertyName == null) {
			propertyName = "";
		}
		var columnReference = null;
		if (!(function () { var $ret = $self._columns.tryGetValue(propertyName, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
			columnReference = new $.ig.ColumnReference(new $.ig.FastItemIntColumn(this, propertyName, coerceValue, expectFunctions));
			this._columns.add(propertyName, columnReference);
		}
		columnReference.references(columnReference.references() + 1);
		fastItemColumn = $.ig.util.cast($.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Number.prototype.$type), columnReference._fastItemColumn);
		return fastItemColumn;
	}
	,
	registerColumn: function (propertyName, coerceValue, expectFunctions) {
		var $self = this;
		var fastItemColumn = null;
		if (propertyName == null) {
			propertyName = "";
		}
		var columnReference = null;
		if (!(function () { var $ret = $self._columns.tryGetValue(propertyName, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
			columnReference = new $.ig.ColumnReference(new $.ig.FastItemColumn(this, propertyName, coerceValue, expectFunctions));
			this._columns.add(propertyName, columnReference);
		}
		columnReference.references(columnReference.references() + 1);
		fastItemColumn = $.ig.util.cast($.ig.IFastItemColumn$1.prototype.$type.specialize(Number), columnReference._fastItemColumn);
		return fastItemColumn;
	}
	,
	deregisterColumn: function (fastItemColumn) {
		var $self = this;
		var propertyName = fastItemColumn != null ? fastItemColumn.propertyName() : null;
		var key = propertyName;
		if ($.ig.util.cast($.ig.IFastItemColumn$1.prototype.$type.specialize($.ig.Object.prototype.$type), fastItemColumn) !== null) {
			key += "_object";
		}
		if (propertyName != null) {
			var columnReference = null;
			if ((function () { var $ret = $self._columns.tryGetValue(propertyName, columnReference); columnReference = $ret.p1; return $ret.ret; }())) {
				columnReference.references(columnReference.references() - 1);
				if (columnReference.references() == 0) {
					this._columns.remove(key);
				}
			}
		}
	}
	,
	_columns: null,
	_itemsSource: null,
	_contents: null,
	_index: null,
	asArray: function () {
		return this._contents.asArray();
	}
	,
	handleCollectionChanged: function (e) {
		switch (e.action()) {
			case $.ig.NotifyCollectionChangedAction.prototype.add:
				this.dataSourceAdd(e.newStartingIndex(), e.newItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.remove:
				this.dataSourceRemove(e.oldStartingIndex(), e.oldItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.replace:
				this.dataSourceReplace(e.newStartingIndex(), e.oldItems(), e.newItems());
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.reset:
				this.dataSourceReset();
				break;
		}
	}
	,
	$type: new $.ig.Type('FastItemsSource', $.ig.Object.prototype.$type, [$.ig.IEnumerable.prototype.$type, $.ig.IFastItemsSource.prototype.$type])
}, true);

$.ig.util.defType('ColumnReference', 'Object', {
	init: function (fastItemColumn) {
		$.ig.Object.prototype.init.call(this);
		this._fastItemColumn = fastItemColumn;
		this.references(0);
	},
	_fastItemColumn: null,
	_references: 0,
	references: function (value) {
		if (arguments.length === 1) {
			this._references = value;
			return value;
		} else {
			return this._references;
		}
	}
	,
	$type: new $.ig.Type('ColumnReference', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FastItemsSourceReference', 'Object', {
	init: function (source) {
		$.ig.Object.prototype.init.call(this);
		this._fastItemsSource = source;
		this._references = 0;
	},
	_fastItemsSource: null,
	_references: 0,
	$type: new $.ig.Type('FastItemsSourceReference', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IFastItemsSourceProvider', 'Object', {
	$type: new $.ig.Type('IFastItemsSourceProvider', null)
}, true);

$.ig.util.defType('FastReflectionHelper', 'Object', {
	init: function (useTraditionalReflection, propertyName) {
		$.ig.Object.prototype.init.call(this);
		this.useTraditionalReflection(useTraditionalReflection);
		this.updatePropertyName(propertyName);
	},
	__propertyName: null,
	updatePropertyName: function (propertyName) {
		this.__propertyName = propertyName;
	}
	,
	_useTraditionalReflection: false,
	useTraditionalReflection: function (value) {
		if (arguments.length === 1) {
			this._useTraditionalReflection = value;
			return value;
		} else {
			return this._useTraditionalReflection;
		}
	}
	,
	getPropertyValue: function (item) {
		var from_ = item;
		return from_[this.__propertyName];
	}
	,
	invalid: function () {
		return false;
	}
	,
	$type: new $.ig.Type('FastReflectionHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CanvasRenderScheduler', 'Object', {
	init: function () {
		this.__sortDirty = true;
		this.__scheduled = false;
		this.__scheduledId = -1;
		$.ig.Object.prototype.init.call(this);
		this.targets(new $.ig.List$1($.ig.ISchedulableRender.prototype.$type, 0));
		this.dependsOn(new $.ig.List$1($.ig.CanvasRenderScheduler.prototype.$type, 0));
	},
	__sortDirty: false,
	_targets: null,
	targets: function (value) {
		if (arguments.length === 1) {
			this._targets = value;
			return value;
		} else {
			return this._targets;
		}
	}
	,
	_dependsOn: null,
	dependsOn: function (value) {
		if (arguments.length === 1) {
			this._dependsOn = value;
			return value;
		} else {
			return this._dependsOn;
		}
	}
	,
	register: function (target) {
		this.__sortDirty = true;
		this.targets().add(target);
	}
	,
	unRegister: function (target) {
		this.__sortDirty = true;
		this.targets().remove(target);
	}
	,
	sortDirty: function () {
		this.__sortDirty = true;
		this.schedule();
	}
	,
	isScheduled: function () {
		return this.__scheduled;
	}
	,
	__scheduled: false,
	__scheduledId: 0,
	schedule: function () {
		if (!this.__scheduled) {
			this.__scheduled = true;
			this.__scheduledId = window.setTimeout(this.refresh.runOn(this), 0);
		}
	}
	,
	schedule1: function (context1, context2) {
		this.schedule();
	}
	,
	flush: function () {
		if (this.__scheduledId != -1) {
			window.clearTimeout(this.__scheduledId);
			this.__scheduledId = -1;
		}
		if (this.__scheduled) {
			this.refresh();
		}
	}
	,
	refresh: function () {
		this.__scheduledId = -1;
		if (this.__scheduled) {
			this.__scheduled = false;
			if (this.dependsOn().count() > 0) {
				for (var i = 0; i < this.dependsOn().count(); i++) {
					this.dependsOn().__inner[i].flush();
				}
			}
			if (this.__sortDirty) {
				this.sortTargets();
			}
			this.renderTargets();
		}
	}
	,
	renderTargets: function () {
		if (this.isDisabled()) {
			return;
		}
		var first = true;
		for (var i = 0; i < this.targets().count(); i++) {
			var target = this.targets().__inner[i];
			target.preRender();
		}
		for (var i1 = 0; i1 < this.targets().count(); i1++) {
			var target1 = this.targets().__inner[i1];
			target1.undirty(first);
			first = false;
		}
		for (var i2 = 0; i2 < this.targets().count(); i2++) {
			var target2 = this.targets().__inner[i2];
			target2.postRender();
		}
	}
	,
	sortTargets: function () {
		this.targets().sort2(function (o1, o2) {
			var t1 = o1;
			var t2 = o2;
			if (t1.index() < t2.index()) {
				return -1;
			}
			if (t1.index() > t2.index()) {
				return 1;
			}
			return 0;
		});
		this.__sortDirty = false;
	}
	,
	_isDisabled: false,
	isDisabled: function (value) {
		if (arguments.length === 1) {
			this._isDisabled = value;
			return value;
		} else {
			return this._isDisabled;
		}
	}
	,
	$type: new $.ig.Type('CanvasRenderScheduler', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ISchedulableRender', 'Object', {
	$type: new $.ig.Type('ISchedulableRender', null)
}, true);

$.ig.util.defType('TrendCalculators', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	wMA: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___WMA__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	eMA: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___EMA__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	mMA: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___MMA__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	cMA: function (sequence) {
		var d__ = new $.ig.TrendCalculators___CMA__IteratorClass(-2);
		d__.__3__sequence = sequence;
		return d__;
	}
	,
	sMA: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___SMA__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	movingSum: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___MovingSum__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	sTDEV: function (sequence, period) {
		var d__ = new $.ig.TrendCalculators___STDEV__IteratorClass(-2);
		d__.__3__sequence = sequence;
		d__.__3__period = period;
		return d__;
	}
	,
	$type: new $.ig.Type('TrendCalculators', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FastItemsSourceEventArgs', 'EventArgs', {
	init: function (initNumber, action, position, count) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.EventArgs.prototype.init.call(this);
		this.action(action);
		this.position(position);
		this.count(count);
		this.propertyName(null);
	},
	init1: function (initNumber, position, propertyName) {
		$.ig.EventArgs.prototype.init.call(this);
		this.action($.ig.FastItemsSourceEventAction.prototype.change);
		this.position(position);
		this.count(1);
		this.propertyName(propertyName);
	},
	_action: 0,
	action: function (value) {
		if (arguments.length === 1) {
			this._action = value;
			return value;
		} else {
			return this._action;
		}
	}
	,
	_position: 0,
	position: function (value) {
		if (arguments.length === 1) {
			this._position = value;
			return value;
		} else {
			return this._position;
		}
	}
	,
	_count: 0,
	count: function (value) {
		if (arguments.length === 1) {
			this._count = value;
			return value;
		} else {
			return this._count;
		}
	}
	,
	_propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this._propertyName = value;
			return value;
		} else {
			return this._propertyName;
		}
	}
	,
	$type: new $.ig.Type('FastItemsSourceEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('Numeric', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	solve1: function (a, b, c, r, u) {
		var j;
		var n = a.count();
		var gam = new Array(n);
		if (b.__inner[0] == 0) {
			return false;
		}
		var bet = b.__inner[0];
		u.__inner[0] = r.__inner[0] / (bet);
		for (j = 1; j < n; j++) {
			gam[j] = c.__inner[j - 1] / bet;
			bet = b.__inner[j] - a.__inner[j] * gam[j];
			if (bet == 0) {
				return false;
			}
			u.__inner[j] = (r.__inner[j] - a.__inner[j] * u.__inner[j - 1]) / bet;
		}
		for (j = (n - 2); j >= 0; j--) {
			u.__inner[j] -= gam[j + 1] * u.__inner[j + 1];
		}
		return true;
	}
	,
	solve: function (a, b) {
		var n = a.getLength(0);
		var indxc = new Array(n);
		var indxr = new Array(n);
		var ipiv = new Array(n);
		for (var i = 0; i < n; i++) {
			ipiv[i] = 0;
		}
		for (var i1 = 0; i1 < n; i1++) {
			var big = 0;
			var irow = 0;
			var icol = 0;
			for (var j = 0; j < n; j++) {
				if (ipiv[j] != 1) {
					for (var k = 0; k < n; k++) {
						if (ipiv[k] == 0) {
							if (Math.abs(a[j][k]) >= big) {
								big = Math.abs(a[j][k]);
								irow = j;
								icol = k;
							}
						}
					}
				}
			}
			++(ipiv[icol]);
			if (irow != icol) {
				for (var j1 = 0; j1 < n; j1++) {
					var t = a[irow][j1];
					a[irow][j1] = a[icol][j1];
					a[icol][j1] = t;
				}
				{
					var t1 = b[irow];
					b[irow] = b[icol];
					b[icol] = t1;
				}
			}
			indxr[i1] = irow;
			indxc[i1] = icol;
			if (a[icol][icol] == 0) {
				return false;
			}
			var pivinv = 1 / a[icol][icol];
			a[icol][icol] = 1;
			for (var j2 = 0; j2 < n; j2++) {
				a[icol][j2] *= pivinv;
			}
			b[icol] *= pivinv;
			for (var j3 = 0; j3 < n; j3++) {
				if (j3 != icol) {
					var dum = a[j3][icol];
					a[j3][icol] = 0;
					for (var l = 0; l < n; l++) {
						a[j3][l] -= a[icol][l] * dum;
					}
					b[j3] -= b[icol] * dum;
				}
			}
		}
		for (var i2 = n - 1; i2 >= 0; i2--) {
			if (indxr[i2] != indxc[i2]) {
				for (var j4 = 0; j4 < n; j4++) {
					var t2 = a[j4][indxr[i2]];
					a[j4][indxr[i2]] = a[j4][indxc[i2]];
					a[j4][indxc[i2]] = t2;
				}
			}
		}
		return true;
	}
	,
	safeCubicSplineFit: function (count, x, y, yp1, ypn) {
		var ret = new $.ig.List$1(Number, 0);
		for (var i = 0; i < count; ++i) {
			while (i < count && ($.ig.util.isNaN(x(i)) || $.ig.util.isNaN(y(i)))) {
				ret.add(NaN);
				++i;
			}
			var j = i;
			while (i < count && !$.ig.util.isNaN(x(i)) && !$.ig.util.isNaN(y(i))) {
				++i;
			}
			--i;
			if (i - j > 0) {
				ret.addRange($.ig.Numeric.prototype.cubicSplineFit1(j, i - j + 1, x, y, yp1, ypn));
			} else {
				for (; j <= i; ++j) {
					ret.add(NaN);
				}
			}
		}
		return ret.toArray();
	}
	,
	cubicSplineFit1: function (start, count, x, y, yp1, ypn) {
		return $.ig.Numeric.prototype.cubicSplineFit(count, function (i) { return x(i + start); }, function (i) { return y(i + start); }, yp1, ypn);
	}
	,
	cubicSplineFit: function (count, x, y, yp1, ypn) {
		var u = new Array(count - 1);
		var y2 = new Array(count);
		y2[0] = $.ig.util.isNaN(yp1) ? 0 : -0.5;
		u[0] = $.ig.util.isNaN(yp1) ? 0 : (3 / (x(1) - x(0))) * ((y(1) - y(0)) / (x(1) - x(0)) - yp1);
		for (var i = 1; i < count - 1; i++) {
			var sig = (x(i) - x(i - 1)) / (x(i + 1) - x(i - 1));
			var p = sig * y2[i - 1] + 2;
			y2[i] = (sig - 1) / p;
			u[i] = (y(i + 1) - y(i)) / (x(i + 1) - x(i)) - (y(i) - y(i - 1)) / (x(i) - x(i - 1));
			u[i] = (6 * u[i] / (x(i + 1) - x(i - 1)) - sig * u[i - 1]) / p;
		}
		var qn = $.ig.util.isNaN(ypn) ? 0 : 0.5;
		var un = $.ig.util.isNaN(ypn) ? 0 : (3 / (x(count - 1) - x(count - 2))) * (ypn - (y(count - 1) - y(count - 2)) / (x(count - 1) - x(count - 2)));
		y2[count - 1] = (un - qn * u[count - 2]) / (qn * y2[count - 2] + 1);
		for (var i1 = count - 2; i1 >= 0; i1--) {
			y2[i1] = y2[i1] * y2[i1 + 1] + u[i1];
		}
		return y2;
	}
	,
	cubicSplineEvaluate: function (x, x1, y1, x2, y2, u1, u2) {
		var h = x2 - x1;
		var a = (x2 - x) / h;
		var b = (x - x1) / h;
		return a * y1 + b * y2 + ((a * a * a - a) * u1 + (b * b * b - b) * u2) * (h * h) / 6;
	}
	,
	spline2D1: function (count, x, y, stiffness) {
		var result = new $.ig.PathFigureCollection();
		var currentSegmentStart = 0;
		var currentSegmentEnd = -1;
		var valueX = NaN;
		var valueY = NaN;
		for (var i = 0; i < count; i++) {
			valueX = x(i);
			valueY = y(i);
			if ($.ig.util.isNaN(valueX) || $.ig.util.isNaN(valueY)) {
				currentSegmentEnd = i - 1;
				if (currentSegmentEnd - currentSegmentStart > 0) {
					result.add($.ig.Numeric.prototype.spline2D(currentSegmentStart, currentSegmentEnd, x, y, stiffness));
				}
				currentSegmentStart = i + 1;
			}
		}
		if (!$.ig.util.isNaN(valueX) && !$.ig.util.isNaN(valueY)) {
			currentSegmentEnd = count - 1;
		}
		if (currentSegmentEnd - currentSegmentStart > 0) {
			result.add($.ig.Numeric.prototype.spline2D(currentSegmentStart, currentSegmentEnd, x, y, stiffness));
		}
		return result;
	}
	,
	spline2D: function (startIndex, endIndex, x, y, stiffness) {
		stiffness = 0.5 * $.ig.MathUtil.prototype.clamp($.ig.util.isNaN(stiffness) ? 0.5 : stiffness, 0, 1);
		var pathFigure = new $.ig.PathFigure();
		var count = endIndex - startIndex + 1;
		if (count < 2) {
			return pathFigure;
		}
		if (count == 2) {
			pathFigure.__startPoint = { __x: x(startIndex), __y: y(startIndex), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var newSeg = (function () {
				var $ret = new $.ig.LineSegment(1);
				$ret.point({ __x: x(startIndex + 1), __y: y(startIndex + 1), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				return $ret;
			}());
			pathFigure.__segments.add(newSeg);
			return pathFigure;
		}
		var Segment = new $.ig.PolyBezierSegment();
		var pix = x(startIndex);
		var piy = y(startIndex);
		var pixnext = x(startIndex + 1);
		var piynext = y(startIndex + 1);
		while (pixnext == pix && piynext == piy && startIndex + 1 <= endIndex) {
			startIndex++;
			pixnext = x(startIndex + 1);
			piynext = y(startIndex + 1);
		}
		var tix = pixnext - pix;
		var tiy = piynext - piy;
		var li = Math.sqrt(tix * tix + tiy * tiy);
		for (var j = startIndex + 1; j < endIndex; ++j) {
			var pjx = x(j);
			var pjy = y(j);
			if (pjx == pix && pjy == piy) {
				continue;
			}
			var tjx = x(j + 1) - x(j - 1);
			var tjy = y(j + 1) - y(j - 1);
			var lj = tjx * tjx + tjy * tjy;
			if (lj < 0.01) {
				tjx = -(y(j + 1) - y(j));
				tjy = x(j + 1) - x(j);
				lj = tjx * tjx + tjy * tjy;
			}
			lj = Math.sqrt(lj);
			var d = stiffness * Math.sqrt((pjx - pix) * (pjx - pix) + (pjy - piy) * (pjy - piy));
			if (lj > 0.01) {
				Segment.points().add({ __x: pix + tix * d / li, __y: piy + tiy * d / li, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				Segment.points().add({ __x: pjx - tjx * d / lj, __y: pjy - tjy * d / lj, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				Segment.points().add({ __x: pjx, __y: pjy, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				pix = pjx;
				piy = pjy;
				tix = tjx;
				tiy = tjy;
				li = lj;
			}
		}
		{
			var j1 = endIndex;
			var pjx1 = x(j1);
			var pjy1 = y(j1);
			var tjx1 = x(j1) - x(j1 - 1);
			var tjy1 = y(j1) - y(j1 - 1);
			var lj1 = tjx1 * tjx1 + tjy1 * tjy1;
			var d1 = stiffness * Math.sqrt((pjx1 - pix) * (pjx1 - pix) + (pjy1 - piy) * (pjy1 - piy));
			Segment.points().add({ __x: pix + tix * d1 / li, __y: piy + tiy * d1 / li, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			Segment.points().add({ __x: pjx1 - tjx1 * d1 / lj1, __y: pjy1 - tjy1 * d1 / lj1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			Segment.points().add({ __x: pjx1, __y: pjy1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		pathFigure.__startPoint = { __x: x(startIndex), __y: y(startIndex), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		pathFigure.__segments.add(Segment);
		return pathFigure;
	}
	,
	$type: new $.ig.Type('Numeric', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LeastSquaresFit', 'Numeric', {
	test: function () {
		return $.ig.LeastSquaresFit.prototype.linearTest() && $.ig.LeastSquaresFit.prototype.logarithmicTest() && $.ig.LeastSquaresFit.prototype.exponentialTest() && $.ig.LeastSquaresFit.prototype.powerLawTest() && $.ig.LeastSquaresFit.prototype.quadraticTest() && $.ig.LeastSquaresFit.prototype.cubicTest() && $.ig.LeastSquaresFit.prototype.quarticTest() && $.ig.LeastSquaresFit.prototype.quinticTest();
	}
	,
	init: function () {
		$.ig.Numeric.prototype.init.call(this);
	},
	linearFit: function (n, x, y) {
		var s0 = 0;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var N = 0;
		for (var i = 0; i < n; ++i) {
			var xi = x(i);
			var yi = y(i);
			if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(yi)) {
				s0 += yi;
				s1 += xi * xi;
				s2 += xi;
				s3 += xi * yi;
				++N;
			}
		}
		if (N < 2) {
			return null;
		}
		var A = (s0 * s1 - s2 * s3) / (N * s1 - s2 * s2);
		var B = (N * s3 - s2 * s0) / (N * s1 - s2 * s2);
		return [ A, B ];
	}
	,
	linearEvaluate: function (a, x) {
		if (a.length != 2) {
			return NaN;
		}
		return a[0] + a[1] * x;
	}
	,
	linearTest: function () {
		var random = new $.ig.Random(0);
		var coeffs = new Array(2);
		for (var i = 0; i < coeffs.length; ++i) {
			coeffs[i] = 10 * random.nextDouble();
		}
		var x = new $.ig.List$1(Number, 0);
		var y = new $.ig.List$1(Number, 0);
		for (var i1 = -100; i1 < 100; ++i1) {
			var X = i1;
			var Y = $.ig.LeastSquaresFit.prototype.linearEvaluate(coeffs, X);
			if (!$.ig.util.isNaN(Y)) {
				x.add(X);
				y.add(Y);
			}
		}
		var fit = $.ig.LeastSquaresFit.prototype.linearFit(x.count(), function (i) {
			return x.__inner[i];
		}, function (i) {
			return y.__inner[i];
		});
		for (var i2 = 0; i2 < coeffs.length; ++i2) {
			if (Math.abs(coeffs[i2] - fit[i2]) > 0.0001) {
			}
		}
		return true;
	}
	,
	logarithmicFit: function (n, x, y) {
		var s0 = 0;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var N = 0;
		for (var i = 0; i < n; ++i) {
			var xi = x(i);
			var yi = y(i);
			if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(yi) && xi > 0) {
				var lnxi = Math.log(xi);
				s0 += yi * lnxi;
				s1 += yi;
				s2 += lnxi;
				s3 += lnxi * lnxi;
				++N;
			}
		}
		if (N < 2) {
			return null;
		}
		var B = (N * s0 - s1 * s2) / (N * s3 - s2 * s2);
		var A = (s1 - B * s2) / N;
		return [ A, B ];
	}
	,
	logarithmicEvaluate: function (a, x) {
		if (a.length != 2 || x < 0 || Number.isInfinity(x) || $.ig.util.isNaN(x)) {
			return NaN;
		}
		return a[0] + a[1] * Math.log(x);
	}
	,
	logarithmicTest: function () {
		var random = new $.ig.Random(0);
		var coeffs = new Array(2);
		for (var i = 0; i < coeffs.length; ++i) {
			coeffs[i] = 10 * random.nextDouble();
		}
		var x = new $.ig.List$1(Number, 0);
		var y = new $.ig.List$1(Number, 0);
		for (var i1 = 1; i1 < 100; ++i1) {
			var X = i1;
			var Y = $.ig.LeastSquaresFit.prototype.logarithmicEvaluate(coeffs, X);
			if (!$.ig.util.isNaN(Y)) {
				x.add(X);
				y.add(Y);
			}
		}
		var fit = $.ig.LeastSquaresFit.prototype.logarithmicFit(x.count(), function (i) {
			return x.__inner[i];
		}, function (i) {
			return y.__inner[i];
		});
		for (var i2 = 0; i2 < coeffs.length; ++i2) {
			if (Math.abs(coeffs[i2] - fit[i2]) > 0.0001) {
			}
		}
		return true;
	}
	,
	exponentialFit: function (n, x, y) {
		var s0 = 0;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var s4 = 0;
		var N = 0;
		for (var i = 0; i < n; ++i) {
			var xi = x(i);
			var yi = y(i);
			if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(yi) && yi > 0) {
				var lnyi = Math.log(yi);
				s0 += xi * xi * yi;
				s1 += yi * lnyi;
				s2 += xi * yi;
				s3 += xi * yi * lnyi;
				s4 += yi;
				++N;
			}
		}
		if (N < 2) {
			return null;
		}
		var a = (s0 * s1 - s2 * s3) / (s4 * s0 - s2 * s2);
		var B = (s4 * s3 - s2 * s1) / (s4 * s0 - s2 * s2);
		return [ Math.exp(a), B ];
	}
	,
	exponentialEvaluate: function (a, x) {
		if (a.length != 2 || x < 0 || Number.isInfinity(x) || $.ig.util.isNaN(x)) {
			return NaN;
		}
		return a[0] * Math.exp(a[1] * x);
	}
	,
	exponentialTest: function () {
		var random = new $.ig.Random(0);
		var coeffs = new Array(2);
		for (var i = 0; i < coeffs.length; ++i) {
			coeffs[i] = 2 * random.nextDouble();
		}
		var x = new $.ig.List$1(Number, 0);
		var y = new $.ig.List$1(Number, 0);
		for (var i1 = 1; i1 < 100; ++i1) {
			var X = i1;
			var Y = $.ig.LeastSquaresFit.prototype.exponentialEvaluate(coeffs, X);
			if (!$.ig.util.isNaN(Y)) {
				x.add(X);
				y.add(Y);
			}
		}
		var fit = $.ig.LeastSquaresFit.prototype.exponentialFit(x.count(), function (i) {
			return x.__inner[i];
		}, function (i) {
			return y.__inner[i];
		});
		for (var i2 = 0; i2 < coeffs.length; ++i2) {
			if (Math.abs(coeffs[i2] - fit[i2]) > 0.0001) {
				return false;
			}
		}
		return true;
	}
	,
	powerLawFit: function (n, x, y) {
		var s0 = 0;
		var s1 = 0;
		var s2 = 0;
		var s3 = 0;
		var N = 0;
		for (var i = 0; i < n; ++i) {
			var xi = x(i);
			var yi = y(i);
			if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(yi) && xi > 0 && yi > 0) {
				var lnxi = Math.log(x(i));
				var lnyi = Math.log(y(i));
				s0 += lnxi * lnyi;
				s1 += lnxi;
				s2 += lnyi;
				s3 += lnxi * lnxi;
				++N;
			}
		}
		if (N < 2) {
			return null;
		}
		var B = (N * s0 - s1 * s2) / (N * s3 - s1 * s1);
		var A = Math.exp((s2 - B * s1) / N);
		return [ A, B ];
	}
	,
	powerLawEvaluate: function (a, x) {
		if (a.length != 2 || x < 0 || Number.isInfinity(x) || $.ig.util.isNaN(x)) {
			return NaN;
		}
		return a[0] * Math.pow(x, a[1]);
	}
	,
	powerLawTest: function () {
		var random = new $.ig.Random(0);
		var coeffs = new Array(2);
		for (var i = 0; i < coeffs.length; ++i) {
			coeffs[i] = 10 * random.nextDouble();
		}
		var x = new $.ig.List$1(Number, 0);
		var y = new $.ig.List$1(Number, 0);
		for (var i1 = -100; i1 < 100; ++i1) {
			x.add(i1);
			y.add($.ig.LeastSquaresFit.prototype.powerLawEvaluate(coeffs, i1));
		}
		var fit = $.ig.LeastSquaresFit.prototype.powerLawFit(x.count(), function (i) {
			return x.__inner[i];
		}, function (i) {
			return y.__inner[i];
		});
		for (var i2 = 0; i2 < coeffs.length; ++i2) {
			if (Math.abs(coeffs[i2] - fit[i2]) > 0.0001) {
				return false;
			}
		}
		return true;
	}
	,
	quadraticFit: function (n, x, y) {
		return $.ig.LeastSquaresFit.prototype.polynomialFit(n, 2, x, y);
	}
	,
	quadraticEvaluate: function (a, x) {
		return $.ig.LeastSquaresFit.prototype.polynomialEvaluate(a, x);
	}
	,
	quadraticTest: function () {
		return $.ig.LeastSquaresFit.prototype.polynomialTest(2);
	}
	,
	cubicFit: function (n, x, y) {
		return $.ig.LeastSquaresFit.prototype.polynomialFit(n, 3, x, y);
	}
	,
	cubicEvaluate: function (a, x) {
		return $.ig.LeastSquaresFit.prototype.polynomialEvaluate(a, x);
	}
	,
	cubicTest: function () {
		return $.ig.LeastSquaresFit.prototype.polynomialTest(3);
	}
	,
	quarticFit: function (n, x, y) {
		return $.ig.LeastSquaresFit.prototype.polynomialFit(n, 4, x, y);
	}
	,
	quarticEvaluate: function (a, x) {
		return $.ig.LeastSquaresFit.prototype.polynomialEvaluate(a, x);
	}
	,
	quarticTest: function () {
		return $.ig.LeastSquaresFit.prototype.polynomialTest(4);
	}
	,
	quinticFit: function (n, x, y) {
		return $.ig.LeastSquaresFit.prototype.polynomialFit(n, 5, x, y);
	}
	,
	quinticEvaluate: function (a, x) {
		return $.ig.LeastSquaresFit.prototype.polynomialEvaluate(a, x);
	}
	,
	quinticTest: function () {
		return $.ig.LeastSquaresFit.prototype.polynomialTest(5);
	}
	,
	polynomialFit: function (n, k, x, y) {
		var ps = new Array(1 + 2 * k);
		for (var ind1 = 0; ind1 < ps.length; ind1++) {
			ps[ind1] = 0;
		}
		var A = (function () {
			var $ret = new Array($firstRank = k + 1);
			var $currRet = $ret;
			for (var $rankInit = 0; $rankInit < $firstRank; $rankInit++) {
				$currRet[$rankInit] = new Array(k + 1);
			}
			return $ret;
		}());
		var B = new Array(k + 1);
		for (var ind2 = 0; ind2 < B.length; ind2++) {
			B[ind2] = 0;
		}
		var N = 0;
		for (var i = 0; i < n; ++i) {
			var s = 1;
			var xi = x(i);
			if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(y(i))) {
				for (var p = 0; p < ps.length; ++p) {
					ps[p] += s;
					s *= xi;
					++N;
				}
			}
		}
		if (N < k) {
			return null;
		}
		for (var i1 = 0; i1 <= k; ++i1) {
			for (var j = 0; j <= k; ++j) {
				A[i1][j] = ps[i1 + j];
			}
		}
		for (var i2 = 0; i2 < n; ++i2) {
			var xi1 = x(i2);
			var yi = y(i2);
			if (!$.ig.util.isNaN(xi1) && !$.ig.util.isNaN(yi)) {
				for (var j1 = 0; j1 <= k; ++j1) {
					B[j1] += (Math.pow(xi1, j1) * yi);
				}
			}
		}
		return $.ig.Numeric.prototype.solve(A, B) ? B : null;
	}
	,
	polynomialEvaluate: function (a, x) {
		if (a.length < 1 || Number.isInfinity(x) || $.ig.util.isNaN(x)) {
			return NaN;
		}
		var y = 0;
		for (var i = 0; i < a.length; ++i) {
			y += a[i] * Math.pow(x, i);
		}
		return y;
	}
	,
	polynomialTest: function (k) {
		var random = new $.ig.Random(0);
		var coeffs = new Array(k + 1);
		for (var i = 0; i < coeffs.length; ++i) {
			coeffs[i] = 2 * random.nextDouble();
		}
		var x = new $.ig.List$1(Number, 0);
		var y = new $.ig.List$1(Number, 0);
		for (var i1 = -100; i1 < 100; ++i1) {
			var X = i1;
			var Y = $.ig.LeastSquaresFit.prototype.polynomialEvaluate(coeffs, X);
			if (!$.ig.util.isNaN(Y)) {
				x.add(X);
				y.add(Y);
			}
		}
		var fit = $.ig.LeastSquaresFit.prototype.polynomialFit(x.count(), k, function (i) {
			return x.__inner[i];
		}, function (i) {
			return y.__inner[i];
		});
		for (var i2 = 0; i2 < k; ++i2) {
			if (Math.abs(coeffs[i2] - fit[i2]) > 0.0001) {
				return false;
			}
		}
		return true;
	}
	,
	$type: new $.ig.Type('LeastSquaresFit', $.ig.Numeric.prototype.$type)
}, true);

$.ig.util.defType('TrendCalculators___STDEV__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__sma_5_0: null,
	__price_5_1: null,
	__buffer_5_2: null,
	__i_5_3: 0,
	__s_5_4: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__sma_5_0 = $.ig.TrendCalculators.prototype.sMA(this._sequence, this._period).getEnumerator();
					this.__price_5_1 = this._sequence.getEnumerator();
					this.__buffer_5_2 = new Array(this._period);
					this.__i_5_3 = 0;
					for (this.__i_5_3 = 0; this.__i_5_3 < this._period; this.__i_5_3++) {
						this.__buffer_5_2[this.__i_5_3] = 0;
					}
					this.__i_5_3 = 0;
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__price_5_1.moveNext() && this.__sma_5_0.moveNext()) {
						this.__buffer_5_2[(this.__i_5_3++) % this._period] = this.__price_5_1.current();
						this.__s_5_4 = 0;
						if (this.__i_5_3 < this._period) {
							var effectivePeriod = 0;
							for (var j = 0; j < this.__i_5_3; j++) {
								var t = (this.__sma_5_0.current() - this.__buffer_5_2[j]);
								this.__s_5_4 += t * t;
								effectivePeriod++;
							}
							this.__2__current = Math.sqrt(this.__s_5_4 / effectivePeriod);
							this.__1__state = 1;
							return true;
						}
						for (var j1 = 0; j1 < this._period; ++j1) {
							var t1 = (this.__sma_5_0.current() - this.__buffer_5_2[j1]);
							this.__s_5_4 += t1 * t1;
						}
						this.__2__current = Math.sqrt(this.__s_5_4 / this._period);
						this.__1__state = 1;
						return true;
					}
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
			d__ = new $.ig.TrendCalculators___STDEV__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
	$type: new $.ig.Type('TrendCalculators___STDEV__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___MovingSum__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__buffer_5_0: null,
	__i_5_1: 0,
	__ms_5_2: 0,
	_valueEnumerator: null,
	__value_5_3: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__buffer_5_0 = new Array(this._period);
						this.__i_5_1 = 0;
						for (this.__i_5_1 = 0; this.__i_5_1 < this._period; this.__i_5_1++) {
							this.__buffer_5_0[this.__i_5_1] = 0;
						}
						this.__i_5_1 = 0;
						this.__ms_5_2 = NaN;
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_3 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_3)) {
								var next = this.__value_5_3;
								var cursor = this.__i_5_1 % this._period;
								if (this.__i_5_1 < this._period) {
									this.__ms_5_2 = $.ig.util.isNaN(this.__ms_5_2) ? next : this.__ms_5_2 + next;
								} else {
									this.__ms_5_2 = this.__ms_5_2 + next - this.__buffer_5_0[cursor];
								}
								this.__buffer_5_0[cursor] = next;
								++this.__i_5_1;
							}
							this.__2__current = this.__ms_5_2;
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
			d__ = new $.ig.TrendCalculators___MovingSum__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___MovingSum__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___SMA__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__buffer_5_0: null,
	__i_5_1: 0,
	__sma_5_2: 0,
	_valueEnumerator: null,
	__value_5_3: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__buffer_5_0 = new Array(this._period);
						this.__i_5_1 = 0;
						for (this.__i_5_1 = 0; this.__i_5_1 < this._period; this.__i_5_1++) {
							this.__buffer_5_0[this.__i_5_1] = 0;
						}
						this.__i_5_1 = 0;
						this.__sma_5_2 = NaN;
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_3 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_3)) {
								var next = this.__value_5_3 / this._period;
								var cursor = this.__i_5_1 % this._period;
								if (this.__i_5_1 < this._period) {
									this.__sma_5_2 = $.ig.util.isNaN(this.__sma_5_2) ? this.__value_5_3 : (this.__sma_5_2 * this.__i_5_1 + this.__value_5_3) / (this.__i_5_1 + 1);
								} else {
									this.__sma_5_2 = this.__sma_5_2 + next - this.__buffer_5_0[cursor];
								}
								this.__buffer_5_0[cursor] = next;
								++this.__i_5_1;
							}
							this.__2__current = this.__sma_5_2;
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
			d__ = new $.ig.TrendCalculators___SMA__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___SMA__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___CMA__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__cma_5_0: 0,
	__i_5_1: 0,
	_valueEnumerator: null,
	__value_5_2: 0,
	_sequence: null,
	__3__sequence: null,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__cma_5_0 = NaN;
						this.__i_5_1 = 0;
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_2 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_2)) {
								this.__cma_5_0 = $.ig.util.isNaN(this.__cma_5_0) ? this.__value_5_2 : (this.__cma_5_0 * this.__i_5_1 + this.__value_5_2) / (this.__i_5_1 + 1);
								++this.__i_5_1;
							}
							this.__2__current = this.__cma_5_0;
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
			d__ = new $.ig.TrendCalculators___CMA__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___CMA__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___MMA__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__i_5_0: 0,
	__mma_5_1: 0,
	__alpha_5_2: 0,
	_valueEnumerator: null,
	__value_5_3: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__i_5_0 = 0;
						this.__mma_5_1 = NaN;
						this.__alpha_5_2 = 1 / this._period;
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_3 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_3)) {
								if (this.__i_5_0 < this._period) {
									this.__mma_5_1 = $.ig.util.isNaN(this.__mma_5_1) ? this.__value_5_3 : (this.__mma_5_1 * this.__i_5_0 + this.__value_5_3) / (this.__i_5_0 + 1);
								} else {
									this.__mma_5_1 = (this.__value_5_3 - this.__mma_5_1) * this.__alpha_5_2 + this.__mma_5_1;
								}
								++this.__i_5_0;
							}
							this.__2__current = this.__mma_5_1;
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
			d__ = new $.ig.TrendCalculators___MMA__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___MMA__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___EMA__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__i_5_0: 0,
	__ema_5_1: 0,
	__alpha_5_2: 0,
	_valueEnumerator: null,
	__value_5_3: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__i_5_0 = 0;
						this.__ema_5_1 = NaN;
						this.__alpha_5_2 = 2 / (1 + this._period);
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_3 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_3)) {
								if (this.__i_5_0 < this._period) {
									this.__ema_5_1 = $.ig.util.isNaN(this.__ema_5_1) ? this.__value_5_3 : (this.__ema_5_1 * this.__i_5_0 + this.__value_5_3) / (this.__i_5_0 + 1);
								} else {
									this.__ema_5_1 = (this.__value_5_3 - this.__ema_5_1) * this.__alpha_5_2 + this.__ema_5_1;
								}
								++this.__i_5_0;
							}
							this.__2__current = this.__ema_5_1;
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
			d__ = new $.ig.TrendCalculators___EMA__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___EMA__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('TrendCalculators___WMA__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__buffer_5_0: null,
	__i_5_1: 0,
	__total_5_2: 0,
	__numerator_5_3: 0,
	__weightsum_5_4: 0,
	__wma_5_5: 0,
	_valueEnumerator: null,
	__value_5_6: 0,
	_sequence: null,
	__3__sequence: null,
	_period: 0,
	__3__period: 0,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._valueEnumerator != null) {
			this._valueEnumerator.dispose();
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
						this.__buffer_5_0 = new Array(this._period);
						this.__i_5_1 = 0;
						for (this.__i_5_1 = 0; this.__i_5_1 < this._period; this.__i_5_1++) {
							this.__buffer_5_0[this.__i_5_1] = 0;
						}
						this.__total_5_2 = NaN;
						this.__numerator_5_3 = NaN;
						this.__weightsum_5_4 = NaN;
						this.__wma_5_5 = NaN;
						this.__i_5_1 = 0;
						this.__1__state = 1;
						this._valueEnumerator = this._sequence.getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._valueEnumerator.moveNext()) {
							this.__value_5_6 = this._valueEnumerator.current();
							if (!$.ig.util.isNaN(this.__value_5_6)) {
								var cursor = this.__i_5_1 % this._period;
								if (this.__i_5_1 == 0) {
									this.__weightsum_5_4 = 1;
									this.__wma_5_5 = this.__numerator_5_3 = this.__total_5_2 = this.__value_5_6;
								} else if (this.__i_5_1 < this._period) {
									this.__weightsum_5_4 += (this.__i_5_1 + 1);
									this.__total_5_2 += this.__value_5_6;
									this.__numerator_5_3 += (this.__i_5_1 + 1) * this.__value_5_6;
									this.__wma_5_5 = this.__numerator_5_3 / this.__weightsum_5_4;
								} else {
									this.__numerator_5_3 = this.__numerator_5_3 + (this._period * this.__value_5_6) - this.__total_5_2;
									this.__wma_5_5 = this.__numerator_5_3 / this.__weightsum_5_4;
									this.__total_5_2 = this.__total_5_2 + this.__value_5_6 - this.__buffer_5_0[cursor];
								}
								this.__buffer_5_0[cursor] = this.__value_5_6;
								++this.__i_5_1;
							}
							this.__2__current = this.__wma_5_5;
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
			d__ = new $.ig.TrendCalculators___WMA__IteratorClass(0);
		}
		d__._sequence = this.__3__sequence;
		d__._period = this.__3__period;
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
		return this.__2__current;
	}
	,
	$type: new $.ig.Type('TrendCalculators___WMA__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.FastItemsSourceEventAction.prototype.remove = 0;
$.ig.FastItemsSourceEventAction.prototype.insert = 1;
$.ig.FastItemsSourceEventAction.prototype.replace = 2;
$.ig.FastItemsSourceEventAction.prototype.change = 3;
$.ig.FastItemsSourceEventAction.prototype.reset = 4;

$.ig.UnknownValuePlotting.prototype.linearInterpolate = 0;
$.ig.UnknownValuePlotting.prototype.dontPlot = 1;

$.ig.TrendLineType.prototype.none = 0;
$.ig.TrendLineType.prototype.linearFit = 1;
$.ig.TrendLineType.prototype.quadraticFit = 2;
$.ig.TrendLineType.prototype.cubicFit = 3;
$.ig.TrendLineType.prototype.quarticFit = 4;
$.ig.TrendLineType.prototype.quinticFit = 5;
$.ig.TrendLineType.prototype.logarithmicFit = 6;
$.ig.TrendLineType.prototype.exponentialFit = 7;
$.ig.TrendLineType.prototype.powerLawFit = 8;
$.ig.TrendLineType.prototype.simpleAverage = 9;
$.ig.TrendLineType.prototype.exponentialAverage = 10;
$.ig.TrendLineType.prototype.modifiedAverage = 11;
$.ig.TrendLineType.prototype.cumulativeAverage = 12;
$.ig.TrendLineType.prototype.weightedAverage = 13;

$.ig.BaseDOMEventProxy.prototype.nullTimer = -1;
$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled = false;
$.ig.BaseDOMEventProxy.prototype.pointerEnabled = false;
$.ig.BaseDOMEventProxy.prototype.tridentVersion = 0;
$.ig.BaseDOMEventProxy.prototype.edgeVersion = 0;

$.ig.DOMEventProxy.prototype.__proxyCount = 0;

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["Message_Spark:a", 
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
"ContainerMessage_Spark:ba", 
"VisibilityMessage_Spark:bb", 
"Visibility:bc", 
"BrushChangedMessage_Spark:bd", 
"Brush:be", 
"Color:bf", 
"Math:bg", 
"ArgumentException:bh", 
"Script:bi", 
"NumberChangedMessage_Spark:bj", 
"ContainerResizedMessage_Spark:bk", 
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
"DataChangedMessage_Spark:dr", 
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
"HorizontalAxisView:e9", 
"ServiceProvider_Spark:fa", 
"XamSparklineView:fb", 
"ISchedulableRender:fc", 
"SparklineController:fd", 
"IFastItemsSourceProvider:fe", 
"IFastItemsSource:ff", 
"IFastItemColumn$1:fg", 
"IFastItemColumnPropertyName:fh", 
"Func$2:fi", 
"EventHandler$1:fj", 
"FastItemsSourceEventArgs:fk", 
"EventArgs:fl", 
"FastItemsSourceEventAction:fm", 
"NotifyCollectionChangedEventArgs:fn", 
"XamSparkline:fo", 
"TrendLineType:fp", 
"SparklineDisplayType:fq", 
"UnknownValuePlotting:fr", 
"MessageChannel_Spark:fs", 
"MessageEventHandler:ft", 
"Array:fu", 
"Delegate:fv", 
"Interlocked:fw", 
"VerticalAxisView:fx", 
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
"AxisRenderMessage_Spark:gd", 
"RenderingMessage_Spark:ge", 
"SparkLayerType:gf", 
"ViewportChangedMessage_Spark:gg", 
"InteractionMessage_Spark:gh", 
"FontUtil:gi", 
"JQuery:gj", 
"JQueryDeferred:gk", 
"JQueryPromise:gl", 
"Action:gm", 
"ClearMessage_Spark:gn", 
"SparklineConnector:go", 
"PropertyChangedMessage_Spark:gp", 
"ConfigurationMessage_Spark:gq", 
"ContainerSizeChangedMessage_Spark:gr", 
"SparkFramePreparer:gs", 
"SparkFrame:gt", 
"TrendCalculators:gu", 
"LeastSquaresFit:gv", 
"Numeric:gw", 
"MathUtil:gx", 
"RuntimeHelpers:gy", 
"RuntimeFieldHandle:gz", 
"Random:g0", 
"FastItemsSource:g1", 
"ColumnReference:g2", 
"IFastItemColumnInternal:g3", 
"FastItemDateTimeColumn:g4", 
"FastItemColumn:g5", 
"FastReflectionHelper:g6", 
"FastItemObjectColumn:g7", 
"FastItemIntColumn:g8", 
"ToooltipTemplateMessage_Spark:g9", 
"MouseLeaveMessage_Spark:ha", 
"MouseMoveMessage_Spark:hb", 
"TooltipMessage_Spark:hc", 
"SparklineToolTipContext:hd", 
"NormalRangeMessage_Spark:he", 
"TrendLineMessage_Spark:hf", 
"ColumnMessage_Spark:hg", 
"WinLossColumnMessage_Spark:hh", 
"PolygonMessage_Spark:hi", 
"MarkerMessage_Spark:hj", 
"FastItemsSourceReference:hk", 
"CanvasRenderScheduler:hl", 
"Callback:hm", 
"window:hn", 
"EventProxy:ho", 
"ModifierKeys:hp", 
"MouseWheelHandler:hq", 
"GestureHandler:hr", 
"ZoomGestureHandler:hs", 
"FlingGestureHandler:ht", 
"ContactHandler:hu", 
"TouchHandler:hv", 
"MouseOverHandler:hw", 
"MouseHandler:hx", 
"KeyHandler:hy", 
"Key:hz", 
"DOMEventProxy:h0", 
"BaseDOMEventProxy:h1", 
"MouseEventArgs:h2", 
"MSGesture:h3", 
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


$.ig.util.defType('SparklineDisplayType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Line";
			case 1: return "Area";
			case 2: return "Column";
			case 3: return "WinLoss";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('SparklineDisplayType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('SparkLayerType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 1: return "SparkLayer";
			case 2: return "MarkerLayer";
			case 4: return "ToolTipLayer";
			case 8: return "RangeLayer";
			case 16: return "TrendLayer";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('SparkLayerType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('Message_Spark', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	toString: function () {
		return this.getType().toString();
	}
	,
	$type: new $.ig.Type('Message_Spark', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ContainerMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_container: null,
	container: function (value) {
		if (arguments.length === 1) {
			this._container = value;
			return value;
		} else {
			return this._container;
		}
	}
	,
	$type: new $.ig.Type('ContainerMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('VisibilityMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_visibility: 0,
	visibility: function (value) {
		if (arguments.length === 1) {
			this._visibility = value;
			return value;
		} else {
			return this._visibility;
		}
	}
	,
	_pathPropertyName: null,
	pathPropertyName: function (value) {
		if (arguments.length === 1) {
			this._pathPropertyName = value;
			return value;
		} else {
			return this._pathPropertyName;
		}
	}
	,
	$type: new $.ig.Type('VisibilityMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('BrushChangedMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_brush: null,
	brush: function (value) {
		if (arguments.length === 1) {
			this._brush = value;
			return value;
		} else {
			return this._brush;
		}
	}
	,
	_brushPropertyName: null,
	brushPropertyName: function (value) {
		if (arguments.length === 1) {
			this._brushPropertyName = value;
			return value;
		} else {
			return this._brushPropertyName;
		}
	}
	,
	$type: new $.ig.Type('BrushChangedMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('NumberChangedMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_value: 0,
	value: function (value) {
		if (arguments.length === 1) {
			this._value = value;
			return value;
		} else {
			return this._value;
		}
	}
	,
	_propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this._propertyName = value;
			return value;
		} else {
			return this._propertyName;
		}
	}
	,
	$type: new $.ig.Type('NumberChangedMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('ContainerResizedMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
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
	_verticalAxisWidth: 0,
	verticalAxisWidth: function (value) {
		if (arguments.length === 1) {
			this._verticalAxisWidth = value;
			return value;
		} else {
			return this._verticalAxisWidth;
		}
	}
	,
	_horizontalAxisHeight: 0,
	horizontalAxisHeight: function (value) {
		if (arguments.length === 1) {
			this._horizontalAxisHeight = value;
			return value;
		} else {
			return this._horizontalAxisHeight;
		}
	}
	,
	_container: null,
	container: function (value) {
		if (arguments.length === 1) {
			this._container = value;
			return value;
		} else {
			return this._container;
		}
	}
	,
	_backgroundCanvas: null,
	backgroundCanvas: function (value) {
		if (arguments.length === 1) {
			this._backgroundCanvas = value;
			return value;
		} else {
			return this._backgroundCanvas;
		}
	}
	,
	_horizontalAxisCanvas: null,
	horizontalAxisCanvas: function (value) {
		if (arguments.length === 1) {
			this._horizontalAxisCanvas = value;
			return value;
		} else {
			return this._horizontalAxisCanvas;
		}
	}
	,
	_verticalAxisCanvas: null,
	verticalAxisCanvas: function (value) {
		if (arguments.length === 1) {
			this._verticalAxisCanvas = value;
			return value;
		} else {
			return this._verticalAxisCanvas;
		}
	}
	,
	_horizontalAxisContext: null,
	horizontalAxisContext: function (value) {
		if (arguments.length === 1) {
			this._horizontalAxisContext = value;
			return value;
		} else {
			return this._horizontalAxisContext;
		}
	}
	,
	_verticalAxisContext: null,
	verticalAxisContext: function (value) {
		if (arguments.length === 1) {
			this._verticalAxisContext = value;
			return value;
		} else {
			return this._verticalAxisContext;
		}
	}
	,
	$type: new $.ig.Type('ContainerResizedMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('DataChangedMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_index: 0,
	index: function (value) {
		if (arguments.length === 1) {
			this._index = value;
			return value;
		} else {
			return this._index;
		}
	}
	,
	_oldItem: null,
	oldItem: function (value) {
		if (arguments.length === 1) {
			this._oldItem = value;
			return value;
		} else {
			return this._oldItem;
		}
	}
	,
	_newItem: null,
	newItem: function (value) {
		if (arguments.length === 1) {
			this._newItem = value;
			return value;
		} else {
			return this._newItem;
		}
	}
	,
	_change: 0,
	change: function (value) {
		if (arguments.length === 1) {
			this._change = value;
			return value;
		} else {
			return this._change;
		}
	}
	,
	$type: new $.ig.Type('DataChangedMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('HorizontalAxisView', 'Object', {
	_sparkline: null,
	_canvas: null,
	_renderingContext: null,
	_font: null,
	_fontHeight: 0,
	_viewportWidth: 0,
	_viewportHeight: 0,
	_view: null,
	__thickness: 0,
	_minLabel: null,
	_maxLabel: null,
	__axisLine: null,
	_css: null,
	_cssSet: false,
	init: function (sparkline) {
		var $self = this;
		this.__thickness = -1;
		this._css = [ "border-top-width", "border-top-color", "color" ];
		this._cssSet = false;
		this.__messageHandlers = new $.ig.Dictionary$2($.ig.Type.prototype.$type, $.ig.MessageEventHandler.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
		this.messageHandlers().item($.ig.AxisRenderMessage_Spark.prototype.$type, function (m) { $self.renderAxisMessageReceived($.ig.util.cast($.ig.AxisRenderMessage_Spark.prototype.$type, m)); });
		this.messageHandlers().item($.ig.ContainerResizedMessage_Spark.prototype.$type, function (m) { $self.containerResizedMessageReceived($.ig.util.cast($.ig.ContainerResizedMessage_Spark.prototype.$type, m)); });
		this.messageHandlers().item($.ig.ViewportChangedMessage_Spark.prototype.$type, function (m) { $self.viewportChangedMessageReceived($.ig.util.cast($.ig.ViewportChangedMessage_Spark.prototype.$type, m)); });
		this._sparkline = sparkline;
	},
	getCss: function () {
		if (!this._cssSet) {
			this._cssSet = true;
			this._view.cssValue("ui-sparkline-axis-x", this._css);
		}
		return this._css;
	}
	,
	thickness: function () {
		var v = this.__thickness;
		if (v >= 0) {
			return v;
		}
		var width = this.getCss()[0];
		if (width != null) {
			v = $.ig.Number.prototype.parseInt(width);
		}
		this.__thickness = v = (v < 1) ? 1 : v;
		return v;
	}
	,
	__serviceProvider: null,
	serviceProvider: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__serviceProvider;
			this.__serviceProvider = value;
			this._view = value.getService("View");
			this.onServiceProviderChanged(oldValue, this.__serviceProvider);
			return value;
		} else {
			return this.__serviceProvider;
		}
	}
	,
	__messageHandlers: null,
	messageHandlers: function (value) {
		if (arguments.length === 1) {
			this.__messageHandlers = value;
			return value;
		} else {
			return this.__messageHandlers;
		}
	}
	,
	__renderingMessages: null,
	renderingMessages: function (value) {
		if (arguments.length === 1) {
			this.__renderingMessages = value;
			return value;
		} else {
			return this.__renderingMessages;
		}
	}
	,
	__interactionMessages: null,
	interactionMessages: function (value) {
		if (arguments.length === 1) {
			this.__interactionMessages = value;
			return value;
		} else {
			return this.__interactionMessages;
		}
	}
	,
	messageReceived: function (m) {
		var $self = this;
		var h;
		if ((function () { var $ret = $self.messageHandlers().tryGetValue(m.getType(), h); h = $ret.p1; return $ret.ret; }())) {
			h(m);
		}
	}
	,
	onServiceProviderChanged: function (oldValue, newValue) {
		if (oldValue != null) {
			this.renderingMessages().detachTarget(this.messageReceived.runOn(this));
		}
		if (newValue != null) {
			this.renderingMessages(newValue.getService("RenderingMessages"));
			this.interactionMessages(newValue.getService("InteractionMessages"));
			this.renderingMessages().attachTarget(this.messageReceived.runOn(this));
		}
	}
	,
	containerResizedMessageReceived: function (message) {
		this._canvas = message.horizontalAxisCanvas();
		this._renderingContext = message.horizontalAxisContext();
		this._font = this._view.getFont("ui-sparkline-axis-x");
		if (this._font == null) {
			this._font = $.ig.FontUtil.prototype.getFont(message.horizontalAxisCanvas());
		}
		this._renderingContext.setFontInfo(this._font);
		this._fontHeight = $.ig.FontUtil.prototype.getCurrentFontHeight(this._font);
		if (this._sparkline != null && this._sparkline.horizontalAxisVisibility() != $.ig.Visibility.prototype.collapsed) {
			message.height(message.height() - this._fontHeight);
			message.horizontalAxisHeight(this.thickness());
		}
	}
	,
	viewportChangedMessageReceived: function (message) {
		this._viewportWidth = this._canvas.width();
		this._viewportHeight = this._fontHeight + this.thickness();
	}
	,
	renderAxisMessageReceived: function (message) {
		this.renderLabels(message.renderInCanvas());
	}
	,
	renderLabels: function (render) {
		var rc = (!render || this._sparkline == null || this._sparkline.labelColumn() == null || this._sparkline.labelColumn().count() == 0) ? null : this._renderingContext;
		if (rc == null || !rc.shouldRender()) {
			return;
		}
		var thickness = this.thickness(), shift = thickness / 2;
		var text = this.getLabelValue(this._sparkline.labelColumn().item(0)).toString();
		this._minLabel = new $.ig.TextBlock();
		this._minLabel.text(text);
		this._minLabel.canvasLeft(2);
		this._maxLabel = new $.ig.TextBlock();
		this._maxLabel.text(text = this.getLabelValue(this._sparkline.labelColumn().item(this._sparkline.labelColumn().count() - 1)).toString());
		this._maxLabel.canvasTop(this._minLabel.canvasTop(thickness));
		rc.clearRectangle(0, 0, this._viewportWidth, this._viewportHeight);
		if (this._font != null) {
			rc.setFontInfo(this._font);
		}
		this._maxLabel.canvasLeft(this._viewportWidth - rc.measureTextWidth(text) - 4);
		var brush = this._sparkline.horizontalAxisBrush();
		var fill = brush == null ? null : brush.__fill;
		var color = new $.ig.Color();
		var wasNull = true;
		if (brush != null) {
			wasNull = false;
			color = brush.color();
		}
		brush = new $.ig.Brush();
		if (wasNull) {
			brush.__fill = (this._css[2] == null) ? "#000000" : this._css[2];
		} else {
			brush.color(color);
		}
		this._minLabel.fill(this._maxLabel.fill(brush));
		rc.renderTextBlock(this._minLabel);
		rc.renderTextBlock(this._maxLabel);
		if (thickness < 0.5) {
			return;
		}
		brush = new $.ig.Brush();
		if (fill == null) {
			fill = this._css[1];
		}
		brush.__fill = (fill == null) ? "#000000" : fill;
		this.__axisLine = new $.ig.Line();
		this.__axisLine.x1(0);
		this.__axisLine.x2(this._canvas.width());
		this.__axisLine.y1(shift);
		this.__axisLine.y2(shift);
		this.__axisLine.__stroke = brush;
		this.__axisLine.strokeThickness(thickness);
		rc.renderLine(this.__axisLine);
	}
	,
	getLabelValue: function (dataItem_) {
		if (this._sparkline.formatLabel() != null) {
			return this._sparkline.formatLabel()(dataItem_);
		}
		var label_ = this._sparkline.horizontalAxisLabel();
		return (typeof dataItem_ != 'undefined') ? dataItem_ : '';;
	}
	,
	$type: new $.ig.Type('HorizontalAxisView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('VerticalAxisView', 'Object', {
	__minLabelString: null,
	__maxLabelString: null,
	_minLabel: null,
	_maxLabel: null,
	__axisLine: null,
	_minLabelWidth: 0,
	_maxLabelWidth: 0,
	_sparkline: null,
	_canvas: null,
	_renderingContext: null,
	_font: null,
	_fontHeight: 0,
	_maxWidth: 0,
	_viewportWidth: 0,
	_viewportHeight: 0,
	_view: null,
	__thickness: 0,
	_css: null,
	_cssSet: false,
	init: function (sparkline) {
		var $self = this;
		this._minLabelWidth = -1;
		this._maxLabelWidth = -1;
		this.__thickness = -1;
		this._css = [ "border-top-width", "border-top-color", "color", "text-align" ];
		this._cssSet = false;
		this.__messageHandlers = new $.ig.Dictionary$2($.ig.Type.prototype.$type, $.ig.MessageEventHandler.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
		this.messageHandlers().item($.ig.AxisRenderMessage_Spark.prototype.$type, function (m) { $self.renderAxisMessageReceived($.ig.util.cast($.ig.AxisRenderMessage_Spark.prototype.$type, m)); });
		this.messageHandlers().item($.ig.ContainerResizedMessage_Spark.prototype.$type, function (m) { $self.containerResizedMessageReceived($.ig.util.cast($.ig.ContainerResizedMessage_Spark.prototype.$type, m)); });
		this.messageHandlers().item($.ig.ViewportChangedMessage_Spark.prototype.$type, function (m) { $self.viewportChangedMessageReceived($.ig.util.cast($.ig.ViewportChangedMessage_Spark.prototype.$type, m)); });
		this._sparkline = sparkline;
	},
	getCss: function () {
		if (!this._cssSet) {
			this._cssSet = true;
			this._view.cssValue("ui-sparkline-axis-y", this._css);
		}
		return this._css;
	}
	,
	thickness: function () {
		var v = this.__thickness;
		if (v >= 0) {
			return v;
		}
		var width = this.getCss()[0];
		if (width != null) {
			v = $.ig.Number.prototype.parseInt(width);
		}
		this.__thickness = v = (v < 1) ? 1 : v;
		return v;
	}
	,
	__serviceProvider: null,
	serviceProvider: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__serviceProvider;
			this.__serviceProvider = value;
			this._view = value.getService("View");
			this.onServiceProviderChanged(oldValue, this.__serviceProvider);
			return value;
		} else {
			return this.__serviceProvider;
		}
	}
	,
	__messageHandlers: null,
	messageHandlers: function (value) {
		if (arguments.length === 1) {
			this.__messageHandlers = value;
			return value;
		} else {
			return this.__messageHandlers;
		}
	}
	,
	__renderingMessages: null,
	renderingMessages: function (value) {
		if (arguments.length === 1) {
			this.__renderingMessages = value;
			return value;
		} else {
			return this.__renderingMessages;
		}
	}
	,
	__interactionMessages: null,
	interactionMessages: function (value) {
		if (arguments.length === 1) {
			this.__interactionMessages = value;
			return value;
		} else {
			return this.__interactionMessages;
		}
	}
	,
	messageReceived: function (m) {
		var $self = this;
		var h;
		if ((function () { var $ret = $self.messageHandlers().tryGetValue(m.getType(), h); h = $ret.p1; return $ret.ret; }())) {
			h(m);
		}
	}
	,
	onServiceProviderChanged: function (oldValue, newValue) {
		if (oldValue != null) {
			this.renderingMessages().detachTarget(this.messageReceived.runOn(this));
		}
		if (newValue != null) {
			this.renderingMessages(newValue.getService("RenderingMessages"));
			this.interactionMessages(newValue.getService("InteractionMessages"));
			this.renderingMessages().attachTarget(this.messageReceived.runOn(this));
		}
	}
	,
	containerResizedMessageReceived: function (message) {
		this._canvas = message.verticalAxisCanvas();
		this._renderingContext = message.verticalAxisContext();
		this._font = this._view.getFont("ui-sparkline-axis-y");
		if (this._font == null) {
			this._font = $.ig.FontUtil.prototype.getFont(message.verticalAxisCanvas());
		}
		this._renderingContext.setFontInfo(this._font);
		this._fontHeight = $.ig.FontUtil.prototype.getCurrentFontHeight(this._font);
		if (this._sparkline != null && this._sparkline.verticalAxisVisibility() != $.ig.Visibility.prototype.collapsed) {
			this.renderLabels(false);
			message.width(message.width() - this._maxWidth);
			message.verticalAxisWidth(this.thickness());
		}
	}
	,
	viewportChangedMessageReceived: function (message) {
		this._viewportWidth = this._maxWidth + this.thickness();
		this._viewportHeight = this._canvas.height();
	}
	,
	renderAxisMessageReceived: function (message) {
		if (this.renderLabels(message.renderInCanvas())) {
			var m = new $.ig.ClearMessage_Spark();
			m.axisVisibilityChanged(true);
			this.renderingMessages().sendMessage(m);
		}
	}
	,
	renderLabels: function (render) {
		if (this._sparkline == null || this._sparkline.itemsSource() == null || this._renderingContext == null || !this._renderingContext.shouldRender() || (render && this._maxLabel == null)) {
			return false;
		}
		var changed = false;
		var rc = this._renderingContext;
		if (rc != null && this._font != null) {
			rc.setFontInfo(this._font);
		}
		if (!render) {
			this._minLabel = new $.ig.TextBlock();
			this._maxLabel = new $.ig.TextBlock();
			this._minLabel.text(this.__minLabelString = this.getLabelValue(this._sparkline.actualMinimum()).toString());
			this._maxLabel.text(this.__maxLabelString = this.getLabelValue(this._sparkline.actualMaximum()).toString());
			if (rc != null) {
				this._minLabelWidth = rc.measureTextWidth(this.__minLabelString) + 3;
				if (this._minLabelWidth > this._maxWidth) {
					changed = true;
					this._maxWidth = this._minLabelWidth;
				}
				this._maxLabelWidth = rc.measureTextWidth(this.__maxLabelString) + 3;
				if (this._maxLabelWidth > this._maxWidth) {
					changed = true;
					this._maxWidth = this._maxLabelWidth;
				}
			}
			if (!changed) {
				var width = Math.max(this._minLabelWidth, this._maxLabelWidth);
				if (width < this._maxWidth) {
					this._maxWidth = width;
					changed = true;
				}
			}
			if (changed) {
				this._viewportWidth = this._maxWidth;
			}
		} else if (rc != null) {
			var brush = this._sparkline.verticalAxisBrush();
			var align = 1;
			var fill = brush == null ? null : brush.__fill;
			var p = this.getCss()[3];
			if (p == "left") {
				align = 0;
			} else if (p == "center") {
				align = 0.5;
			}
			this._maxLabel.canvasTop(2);
			this._maxLabel.canvasLeft((this._maxWidth - this._maxLabelWidth) * align + 1);
			this._minLabel.canvasLeft((this._maxWidth - this._minLabelWidth) * align + 1);
			this._minLabel.canvasTop(this._viewportHeight - this._fontHeight - 2);
			rc.clearRectangle(0, 0, this._viewportWidth, this._viewportHeight);
			var color = new $.ig.Color();
			var wasNull = true;
			if (brush != null) {
				color = brush.color();
				wasNull = false;
			}
			brush = new $.ig.Brush();
			if (wasNull) {
				brush.__fill = (this._css[2] == null) ? "#000000" : this._css[2];
			} else {
				brush.color(color);
			}
			this._minLabel.fill(this._maxLabel.fill(brush));
			rc.renderTextBlock(this._minLabel);
			rc.renderTextBlock(this._maxLabel);
			var thickness = this.thickness(), shift = thickness / 2;
			if (thickness < 0.5) {
				return changed;
			}
			brush = new $.ig.Brush();
			if (fill == null) {
				fill = this._css[1];
			}
			brush.__fill = (fill == null) ? "#000000" : fill;
			this.__axisLine = new $.ig.Line();
			this.__axisLine.x1(this._viewportWidth - shift);
			this.__axisLine.x2(this._viewportWidth - shift);
			this.__axisLine.y1(0);
			this.__axisLine.y2(this._viewportHeight);
			this.__axisLine.__stroke = brush;
			this.__axisLine.strokeThickness(thickness);
			rc.renderLine(this.__axisLine);
		}
		return changed;
	}
	,
	getLabelValue: function (dataItem_) {
		if (this._sparkline.formatLabel() != null) {
			return this._sparkline.formatLabel()(dataItem_);
		}
		var label_ = this._sparkline.verticalAxisLabel();
		return (typeof dataItem_ != 'undefined') ? dataItem_ : '';;
	}
	,
	$type: new $.ig.Type('VerticalAxisView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamSparklineView', 'Object', {
	_viewport: null,
	_marginViewport: null,
	_container: null,
	_backgroundCanvas: null,
	_horizontalAxisCanvas: null,
	_verticalAxisCanvas: null,
	_cssSpan: null,
	_backgroundContext: null,
	_horizontalAxisContext: null,
	_verticalAxisContext: null,
	_eventProxy: null,
	_toolTipVisible: false,
	_markerSizes: null,
	__margin: 0,
	init: function () {
		var $self = this;
		this._markerSizes = new Array(6);
		this.__margin = -1;
		this._sparkPath = new $.ig.Path();
		this._negativeSparkPath = new $.ig.Path();
		this._trendLinePath = new $.ig.Path();
		this._rangePath = new $.ig.Path();
		this._markersPath = new $.ig.Path();
		this._negativeMarkersPath = new $.ig.Path();
		this._lowMarkersPath = new $.ig.Path();
		this._highMarkersPath = new $.ig.Path();
		this._firstMarkerPath = new $.ig.Path();
		this._lastMarkerPath = new $.ig.Path();
		this.__messageHandlers = new $.ig.Dictionary$2($.ig.Type.prototype.$type, $.ig.MessageEventHandler.prototype.$type, 0);
		this.__inContainerResized = false;
		this.__tooltip = null;
		this.__isDirty = false;
		$.ig.Object.prototype.init.call(this);
		var mh = this.messageHandlers();
		mh.item($.ig.ClearMessage_Spark.prototype.$type, function (m) { $self.clearLayer($.ig.util.cast($.ig.ClearMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.PolygonMessage_Spark.prototype.$type, function (m) { $self.updatePolygon($.ig.util.cast($.ig.PolygonMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.ColumnMessage_Spark.prototype.$type, function (m) { $self.updateColumns($.ig.util.cast($.ig.ColumnMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.WinLossColumnMessage_Spark.prototype.$type, function (m) { $self.updateWinLossColumns($.ig.util.cast($.ig.WinLossColumnMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.MarkerMessage_Spark.prototype.$type, function (m) { $self.updateMarkers($.ig.util.cast($.ig.MarkerMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.NormalRangeMessage_Spark.prototype.$type, function (m) { $self.updateRange($.ig.util.cast($.ig.NormalRangeMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.TrendLineMessage_Spark.prototype.$type, function (m) { $self.updateTrendLine($.ig.util.cast($.ig.TrendLineMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.TooltipMessage_Spark.prototype.$type, function (m) { $self.updateToolTip($.ig.util.cast($.ig.TooltipMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.ToooltipTemplateMessage_Spark.prototype.$type, function (m) { $self.updateToolTipTemplate($.ig.util.cast($.ig.ToooltipTemplateMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.ContainerMessage_Spark.prototype.$type, function (m) { $self.containerProvided($.ig.util.cast($.ig.ContainerMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.BrushChangedMessage_Spark.prototype.$type, function (m) { $self.updateBrush($.ig.util.cast($.ig.BrushChangedMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.ContainerResizedMessage_Spark.prototype.$type, function (m) { $self.onContainerResized(); });
		mh.item($.ig.VisibilityMessage_Spark.prototype.$type, function (m) { $self.updateVisibility($.ig.util.cast($.ig.VisibilityMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.NumberChangedMessage_Spark.prototype.$type, function (m) { $self.updateNumericProperty($.ig.util.cast($.ig.NumberChangedMessage_Spark.prototype.$type, m)); });
		mh.item($.ig.ContainerSizeChangedMessage_Spark.prototype.$type, function (m) { $self.onContainerResized(); });
		this.renderScheduler(new $.ig.CanvasRenderScheduler());
		this.renderScheduler().register(this);
	},
	_sparkPath: null,
	_negativeSparkPath: null,
	_trendLinePath: null,
	_rangePath: null,
	_markersPath: null,
	_negativeMarkersPath: null,
	_lowMarkersPath: null,
	_highMarkersPath: null,
	_firstMarkerPath: null,
	_lastMarkerPath: null,
	__serviceProvider: null,
	serviceProvider: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__serviceProvider;
			this.__serviceProvider = value;
			this.onServiceProviderChanged(oldValue, this.__serviceProvider);
			this.__controller = null;
			return value;
		} else {
			return this.__serviceProvider;
		}
	}
	,
	__interactionMessages: null,
	interactionMessages: function (value) {
		if (arguments.length === 1) {
			this.__interactionMessages = value;
			return value;
		} else {
			return this.__interactionMessages;
		}
	}
	,
	__renderingMessages: null,
	renderingMessages: function (value) {
		if (arguments.length === 1) {
			this.__renderingMessages = value;
			return value;
		} else {
			return this.__renderingMessages;
		}
	}
	,
	__messageHandlers: null,
	messageHandlers: function (value) {
		if (arguments.length === 1) {
			this.__messageHandlers = value;
			return value;
		} else {
			return this.__messageHandlers;
		}
	}
	,
	onServiceProviderChanged: function (oldValue, newValue) {
		if (oldValue != null) {
			this.renderingMessages().detachTarget(this.messageReceived.runOn(this));
			this.interactionMessages(null);
		}
		if (newValue != null) {
			this.renderingMessages(newValue.getService("RenderingMessages"));
			this.interactionMessages(newValue.getService("InteractionMessages"));
			this.renderingMessages().attachTarget(this.messageReceived.runOn(this));
			this.startInteractionChannel(this.interactionMessages());
		}
	}
	,
	startInteractionChannel: function (messageChannel) {
		this.interactionMessages(messageChannel);
	}
	,
	messageReceived: function (m) {
		var $self = this;
		var h;
		if ((function () { var $ret = $self.messageHandlers().tryGetValue(m.getType(), h); h = $ret.p1; return $ret.ret; }())) {
			h(m);
		}
	}
	,
	__controller: null,
	controller: function () {
		if (this.__controller == null) {
			this.__controller = this.serviceProvider().getService("Controller");
		}
		return this.__controller;
	}
	,
	sparklineView_MouseMove: function (p, onMouseMove, isFinger) {
		var m = new $.ig.MouseMoveMessage_Spark();
		m.position(p);
		this.interactionMessages().sendMessage(m);
	}
	,
	sparklineView_MouseLeave: function (p) {
		var m = new $.ig.MouseLeaveMessage_Spark();
		this.interactionMessages().sendMessage(m);
	}
	,
	updateBrush: function (b) {
		this.updateBrush1(b.brushPropertyName(), b.brush(), null, -1, -1);
	}
	,
	updateBrush1: function (name, brush, clr, opacity, thickness) {
		var path = null;
		var stroke = true, fill = true;
		switch (name) {
			case "sparkpath":
			case $.ig.XamSparkline.prototype.brushPropertyName:
				path = this._sparkPath;
				break;
			case "negativesparkpath":
			case $.ig.XamSparkline.prototype.negativeBrushPropertyName:
				path = this._negativeSparkPath;
				break;
			case "markers":
			case $.ig.XamSparkline.prototype.markerBrushPropertyName:
				path = this._markersPath;
				break;
			case "firstmarker":
			case $.ig.XamSparkline.prototype.firstMarkerBrushPropertyName:
				path = this._firstMarkerPath;
				break;
			case "lastmarker":
			case $.ig.XamSparkline.prototype.lastMarkerBrushPropertyName:
				path = this._lastMarkerPath;
				break;
			case "highmarker":
			case $.ig.XamSparkline.prototype.highMarkerBrushPropertyName:
				path = this._highMarkersPath;
				break;
			case "lowmarker":
			case $.ig.XamSparkline.prototype.lowMarkerBrushPropertyName:
				path = this._lowMarkersPath;
				break;
			case "negativemarkers":
			case $.ig.XamSparkline.prototype.negativeMarkerBrushPropertyName:
				path = this._negativeMarkersPath;
				break;
			case "trendline":
			case $.ig.XamSparkline.prototype.trendLineBrushPropertyName:
				path = this._trendLinePath;
				fill = false;
				break;
			case "range":
			case $.ig.XamSparkline.prototype.normalRangeFillPropertyName:
				path = this._rangePath;
				stroke = false;
				break;
		}
		if (path == null) {
			return;
		}
		if (brush == null && clr != null) {
			brush = new $.ig.Brush();
			brush.__fill = clr;
		}
		if (brush != null) {
			if (fill && (clr == null || path.__fill == null)) {
				path.__fill = brush;
			}
			if (stroke && (clr == null || path.__stroke == null)) {
				path.__stroke = brush;
			}
		}
		if (opacity > 0 && opacity < 1) {
			path.__opacity = opacity;
		}
		if (thickness > 0 && (clr == null || path.strokeThickness() <= 0)) {
			path.strokeThickness(thickness);
		}
		this.makeDirty();
	}
	,
	updateVisibility: function (b) {
		switch (b.pathPropertyName()) {
			case $.ig.XamSparkline.prototype.markerVisibilityPropertyName:
				this._markersPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.firstMarkerVisibilityPropertyName:
				this._firstMarkerPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.lastMarkerVisibilityPropertyName:
				this._lastMarkerPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.highMarkerVisibilityPropertyName:
				this._highMarkersPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.lowMarkerVisibilityPropertyName:
				this._lowMarkersPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.negativeMarkerVisibilityPropertyName:
				this._negativeMarkersPath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.normalRangeVisibilityPropertyName:
				this._rangePath.__visibility = b.visibility();
				break;
			case $.ig.XamSparkline.prototype.toolTipVisibilityPropertyName:
				this._toolTipVisible = b.visibility() == $.ig.Visibility.prototype.visible;
				break;
		}
		this.makeDirty();
	}
	,
	updateNumericProperty: function (b) {
		var v = b.value();
		if (v <= 0) {
			return;
		}
		switch (b.propertyName()) {
			case $.ig.XamSparkline.prototype.lineThicknessPropertyName:
				this._sparkPath.strokeThickness(this._negativeSparkPath.strokeThickness(v));
				break;
			case $.ig.XamSparkline.prototype.trendLineThicknessPropertyName:
				this._trendLinePath.strokeThickness(v);
				break;
		}
		this.makeDirty();
	}
	,
	containerProvided: function (m) {
		if (m.container() == null) {
			if (this._eventProxy != null) {
				this._eventProxy.destroy();
				var $t = (this._eventProxy);
				$t.onMouseLeave = $.ig.Delegate.prototype.remove($t.onMouseLeave, this.sparklineView_MouseLeave.runOn(this));
				var $t1 = (this._eventProxy);
				$t1.onMouseOver = $.ig.Delegate.prototype.remove($t1.onMouseOver, this.sparklineView_MouseMove.runOn(this));
				this._eventProxy = null;
			}
			this._container = this._backgroundCanvas = this._horizontalAxisCanvas = this._verticalAxisCanvas = this._cssSpan = null;
			this._backgroundContext = this._horizontalAxisContext = this._verticalAxisContext = null;
			return;
		}
		var container = m.container();
		this._container = $(container);
		this._container.css("position", "relative");
		this._backgroundCanvas = $("<canvas style='position:absolute' />");
		this._horizontalAxisCanvas = $("<canvas style='position:absolute' />");
		this._verticalAxisCanvas = $("<canvas style='position:absolute' />");
		if (this._cssSpan == null) {
			this._cssSpan = $("<span style='position:absolute;display:none' />");
		}
		this._container.append(this._cssSpan);
		this._container.append(this._backgroundCanvas);
		this._container.append(this._horizontalAxisCanvas);
		this._container.append(this._verticalAxisCanvas);
		this._backgroundContext = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), (this._backgroundCanvas[0]).getContext("2d"));
		this._horizontalAxisContext = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), (this._horizontalAxisCanvas[0]).getContext("2d"));
		this._verticalAxisContext = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), (this._verticalAxisCanvas[0]).getContext("2d"));
		this._eventProxy = new $.ig.DOMEventProxy(this._backgroundCanvas);
		var $t2 = (this._eventProxy);
		$t2.onMouseLeave = $.ig.Delegate.prototype.combine($t2.onMouseLeave, this.sparklineView_MouseLeave.runOn(this));
		var $t3 = (this._eventProxy);
		$t3.onMouseOver = $.ig.Delegate.prototype.combine($t3.onMouseOver, this.sparklineView_MouseMove.runOn(this));
		this.getDefaultColors();
		this.onContainerResized();
	}
	,
	getDefaultColors: function () {
		var names = [ "sparkpath", "negativesparkpath", "trendline", "markers", "firstmarker", "lastmarker", "highmarker", "lowmarker", "negativemarkers", "range" ];
		var clrs = [ "#B1BFC9", "#798995", "#2070a0", "#4F606C", "#374650", "#162C3B", "#162C3B", "#162C3B", "#862C3B", "#a0a0a0" ];
		for (var i = 0; i < 10; i++) {
			var name = names[i];
			var css = [ "background-color", "opacity", "border-top-width" ];
			this.cssValue("ui-sparkline-" + name, css);
			var width = $.ig.XamSparklineView.prototype.toDouble(css[2], (i < 3) ? (i < 2 ? 2 : 1.5) : -1);
			if (i > 2 && i < 9) {
				this._markerSizes[i - 3] = width;
			}
			this.updateBrush1(name, null, (css[0] == null) ? clrs[i] : css[0], $.ig.XamSparklineView.prototype.toDouble(css[1], -1), i < 3 ? width : -1);
		}
	}
	,
	toDouble: function (v_, def) {
		var d_ = (v_ == null || v_.length < 1) ? null : parseFloat(v_);
		return (d_ == null || d_.toString() == "NaN") ? def : d_;
	}
	,
	cssValue: function (css, prop) {
		var span = this._cssSpan;
		if (span == null) {
			span = this._cssSpan = $("<span style='position:absolute;display:none' />");
			$("body").append(this._cssSpan);
		}
		var i = prop.length, i0 = i;
		var old = new Array(i0);
		while (i0-- > 0) {
			old[i0] = span.css(prop[i0]);
		}
		span.addClass(css);
		while (i-- > 0) {
			var p = prop[i];
			p = span.css(p);
			prop[i] = (p == null || p.length == 0 || p == "null" || p == "transparent" || p == old[i]) ? null : p;
		}
		span.removeClass(css);
		return prop;
	}
	,
	__inContainerResized: false,
	onContainerResized: function () {
		if (this._container == null || this.__inContainerResized) {
			return;
		}
		this.__inContainerResized = true;
		var width = this._container.width(), height = this._container.height();
		var back = this._backgroundCanvas, ha = this._horizontalAxisCanvas, va = this._verticalAxisCanvas;
		var msg = new $.ig.ContainerResizedMessage_Spark();
		msg.width(width);
		msg.height(height);
		msg.horizontalAxisContext(this._horizontalAxisContext);
		msg.horizontalAxisCanvas(ha);
		msg.container(this._container);
		msg.backgroundCanvas(back);
		msg.verticalAxisContext(this._verticalAxisContext);
		msg.verticalAxisCanvas(va);
		msg.verticalAxisWidth(0);
		msg.horizontalAxisHeight(0);
		this.renderingMessages().sendMessage(msg);
		var xHeight = msg.horizontalAxisHeight(), yWidth = msg.verticalAxisWidth(), w = msg.width() - yWidth, h = msg.height() - xHeight;
		var width0 = w.toString(), widthA = (width - w).toString(), height0 = h.toString(), heightA = (height - h).toString();
		back.css("width", width0);
		back.css("height", height0);
		back.attr("width", width0);
		back.attr("height", height0);
		back.css("marginLeft", widthA + "px");
		width0 = (w + yWidth).toString();
		this.setSize(ha, width0, heightA);
		ha.css("marginTop", height0 + "px");
		ha.css("marginLeft", (width - w - yWidth).toString() + "px");
		this.setSize(va, widthA, height0);
		this._viewport = new $.ig.Rect(0, 0, 0, w, h);
		this._eventProxy.viewport(this._viewport);
		this.updateViewportMargins();
		var m = new $.ig.ViewportChangedMessage_Spark();
		m.newLeft(this._viewport.left());
		m.newTop(this._viewport.top());
		m.newHeight(this._viewport.height());
		m.newWidth(this._viewport.width());
		this.renderingMessages().sendMessage(m);
		this.__inContainerResized = false;
	}
	,
	doMargin: function (reset) {
		if (reset) {
			this.__margin = -1;
		} else if (this.__margin < 0) {
			this.updateViewportMargins();
		}
	}
	,
	updateViewportMargins: function () {
		var w = this._viewport.width();
		var h = this._viewport.height();
		var margin = this.margin(true);
		var marginViewport = new $.ig.Rect(0, margin, margin, w - margin * 2, h - margin * 2);
		var m = new $.ig.ViewportChangedMessage_Spark();
		m.newLeft(marginViewport.left());
		m.newTop(marginViewport.top());
		m.newHeight(marginViewport.height());
		m.newWidth(marginViewport.width());
		this._marginViewport = marginViewport;
		this.interactionMessages().sendMessage(m);
	}
	,
	clearLayer: function (message) {
		if ($.ig.Rect.prototype.l_op_Equality(this._viewport, null)) {
			return;
		}
		switch (message.layer()) {
			case $.ig.SparkLayerType.prototype.markerLayer:
				this._markersPath.data(null);
				this._firstMarkerPath.data(null);
				this._lastMarkerPath.data(null);
				this._highMarkersPath.data(null);
				this._lowMarkersPath.data(null);
				this._negativeMarkersPath.data(null);
				break;
			case $.ig.SparkLayerType.prototype.rangeLayer:
				this._rangePath.data(null);
				break;
			case $.ig.SparkLayerType.prototype.sparkLayer:
				this._sparkPath.data(null);
				this._negativeSparkPath.data(null);
				break;
			case $.ig.SparkLayerType.prototype.toolTipLayer:
				this.doTooltip(null);
				break;
			case $.ig.SparkLayerType.prototype.trendLayer:
				this._trendLinePath.data(null);
				break;
		}
		if (message.axisVisibilityChanged()) {
			this.onContainerResized();
		}
		if (this._backgroundContext != null && message.layer() != $.ig.SparkLayerType.prototype.toolTipLayer) {
			this._backgroundContext.clearRectangle(this._viewport.left(), this._viewport.top(), this._viewport.width(), this._viewport.height());
		}
	}
	,
	margin: function (reset) {
		if (reset) {
			this.__margin = -1;
		}
		var margin = this.__margin;
		if (margin >= 0) {
			return margin;
		}
		margin = 0;
		var xam = this.controller().model();
		if (xam.markerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.markerSize(), 0);
		}
		if (xam.firstMarkerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.firstMarkerSize(), 1);
		}
		if (xam.lastMarkerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.lastMarkerSize(), 2);
		}
		if (xam.highMarkerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.highMarkerSize(), 3);
		}
		if (xam.lowMarkerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.lowMarkerSize(), 4);
		}
		if (xam.negativeMarkerVisibility() == $.ig.Visibility.prototype.visible) {
			margin = this.max(margin, xam.negativeMarkerSize(), 5);
		}
		this.__margin = margin;
		return margin;
	}
	,
	max: function (old, val, def) {
		if (val >= 0) {
			return val > old ? val : old;
		}
		return this._markerSizes[def] > old ? this._markerSizes[def] : old;
	}
	,
	updateMarkers: function (markerMessage) {
		this.updateMarkers1(this._markersPath, markerMessage.markerPoints(), markerMessage.markerSize(), 0);
		this.updateMarkers1(this._negativeMarkersPath, markerMessage.negativeMarkerPoints(), markerMessage.negativeMarkerSize(), 5);
		this.updateMarkers1(this._lowMarkersPath, markerMessage.lowPoints(), markerMessage.lowMarkerSize(), 4);
		this.updateMarkers1(this._highMarkersPath, markerMessage.highPoints(), markerMessage.highMarkerSize(), 3);
		this.updateMarkers2(this._firstMarkerPath, markerMessage.firstPoint(), markerMessage.firstMarkerSize(), 1);
		this.updateMarkers2(this._lastMarkerPath, markerMessage.lastPoint(), markerMessage.lastMarkerSize(), 2);
		this.makeDirty();
	}
	,
	updateMarkers2: function (path, point, size, def) {
		var points = (function () {
			var $ret = new $.ig.List$1($.ig.Point.prototype.$type, 0);
			$ret.add(point);
			return $ret;
		}());
		this.updateMarkers1(path, points, size, def);
	}
	,
	updateMarkers1: function (path, points, size, def) {
		if (size < 0) {
			size = this._markerSizes[def];
		}
		path.data(this.createMarkers(points, size < 0.1 ? 3 : size));
		if (this.controller().model().displayType() != $.ig.SparklineDisplayType.prototype.winLoss) {
			var transform = new $.ig.TranslateTransform();
			transform.x(this.controller()._framePreparer._offset);
			path.renderTransform(transform);
		} else {
			path.renderTransform(null);
		}
	}
	,
	createMarkers: function (markers, markerSize) {
		var geometry = new $.ig.GeometryGroup();
		geometry.fillRule($.ig.FillRule.prototype.nonzero);
		var viewportWidth = this._viewport.width();
		var viewportHeight = this._viewport.height();
		var en = markers.getEnumerator();
		while (en.moveNext()) {
			var marker = en.current();
			if (marker.__x < 0 || marker.__y < 0 || marker.__x > viewportWidth || marker.__y > viewportHeight) {
				continue;
			}
			var e = new $.ig.EllipseGeometry();
			e.radiusX(markerSize);
			e.radiusY(markerSize);
			e.center(marker);
			geometry.children().add(e);
		}
		return geometry;
	}
	,
	updatePolygon: function (pm) {
		var geometry = new $.ig.PathGeometry();
		var closed = this.controller().model().displayType() == $.ig.SparklineDisplayType.prototype.area;
		var en = pm.points().getEnumerator();
		while (en.moveNext()) {
			var pointList = en.current();
			var numPoints = pointList.length;
			if (numPoints == 0) {
				continue;
			}
			var figure = new $.ig.PathFigure();
			figure.__isClosed = figure.__isFilled = closed;
			figure.__startPoint = pointList[0];
			for (var i = 1; i < numPoints; i++) {
				var line = new $.ig.LineSegment(1);
				line.point(pointList[i]);
				figure.__segments.add(line);
			}
			geometry.figures().add(figure);
		}
		this._sparkPath.data(geometry);
		this.makeDirty();
	}
	,
	createColumns: function (columns, message) {
		var winlossMessage = $.ig.util.cast($.ig.WinLossColumnMessage_Spark.prototype.$type, message);
		var columnMessage = $.ig.util.cast($.ig.ColumnMessage_Spark.prototype.$type, message);
		var geometry = new $.ig.GeometryGroup();
		var offset = winlossMessage != null ? winlossMessage.offset() : columnMessage.offset();
		var crossing = winlossMessage != null ? winlossMessage.crossing() : columnMessage.crossing();
		var a = offset * 0.1;
		var en = columns.getEnumerator();
		while (en.moveNext()) {
			var column = en.current();
			var width = offset * 2 - a * 2;
			var height = Math.abs(crossing - column.__y);
			var x = column.__x + a;
			var y = (column.__y > crossing ? crossing : column.__y);
			var r = new $.ig.RectangleGeometry();
			r.rect(new $.ig.Rect(0, x, y, width, height));
			geometry.children().add(r);
		}
		return geometry;
	}
	,
	updateColumns: function (cm) {
		this.updateColumns1(this._sparkPath, cm.columns(), cm);
		this.updateColumns1(this._negativeSparkPath, cm.negativeColumns(), cm);
		this.makeDirty();
	}
	,
	updateColumns2: function (path, point, message) {
		var points = (function () {
			var $ret = new $.ig.List$1($.ig.Point.prototype.$type, 0);
			$ret.add(point);
			return $ret;
		}());
		this.updateColumns1(path, points, message);
	}
	,
	updateColumns1: function (path, points, message) {
		path.data(this.createColumns(points, message));
	}
	,
	updateWinLossColumns: function (message) {
		this.updateColumns1(this._sparkPath, message.columns(), message);
		this.updateColumns1(this._negativeSparkPath, message.negativeColumns(), message);
		this.makeDirty();
	}
	,
	updateRange: function (message) {
		var rg = new $.ig.RectangleGeometry();
		rg.rect(new $.ig.Rect(0, message.x(), message.y(), message.width(), message.height()));
		this._rangePath.data(rg);
		this.makeDirty();
	}
	,
	updateTrendLine: function (message) {
		if (message.points().length == 0) {
			return;
		}
		var geometry = new $.ig.PathGeometry();
		var figure = new $.ig.PathFigure();
		figure.__isClosed = figure.__isFilled = false;
		figure.__startPoint = message.points()[0];
		var numPoints = message.points().length;
		for (var i = 1; i < numPoints; i++) {
			var line = new $.ig.LineSegment(1);
			line.point(message.points()[i]);
			figure.__segments.add(line);
		}
		geometry.figures().add(figure);
		this._trendLinePath.data(geometry);
		this.makeDirty();
	}
	,
	__tooltip: null,
	updateToolTipTemplate: function (message) {
		this.__tooltip = message.template().toString();
	}
	,
	updateToolTip: function (tooltipMessage) {
		if (!this._toolTipVisible) {
			this.doTooltip(null);
			return;
		}
		var mouseX = $.ig.truncate(Math.round(tooltipMessage.xOffset() - 10));
		var mouseY = $.ig.truncate(Math.round(tooltipMessage.yOffset() - 10));
		var imageData = this._backgroundContext.getPixelAt(mouseX, mouseY);
		if (imageData[3] > 0) {
			this.doTooltip(tooltipMessage);
		} else {
			this.doTooltip(null);
		}
	}
	,
	doTooltip: function (m) {
		var x_ = 0, y_ = 0;
		var v_ = null, t_ = null, i_ = this._container;
		i_ = i_?i_.data('igSparkline'):null;
		if (i_ != null) {
			if (m != null) {
				t_ = this.__tooltip;
				x_ = m.xOffset() + 6;
				y_ = m.yOffset() + 6;
				var c = m.context();
				var f_ = c.first(), l_ = c.last(), o_ = c.low(), h_ = c.high();
				v_ = {First:f_,Last:l_,Low:o_,High:h_};
			}
			i_._fireTooltip(t_,v_,x_,y_);
		}
	}
	,
	_renderScheduler: null,
	renderScheduler: function (value) {
		if (arguments.length === 1) {
			this._renderScheduler = value;
			return value;
		} else {
			return this._renderScheduler;
		}
	}
	,
	__isDirty: false,
	isDirty: function (value) {
		if (arguments.length === 1) {
			this.__isDirty = value;
			return value;
		} else {
			return this.__isDirty;
		}
	}
	,
	makeDirty: function () {
		if (!this.isDirty()) {
			this.isDirty(true);
			this.renderScheduler().schedule1(this._backgroundContext, null);
		}
	}
	,
	undirty: function (clearRect) {
		this.__isDirty = false;
		this.render();
	}
	,
	index: function () {
		return 10;
	}
	,
	renderPathWithTransform: function (path, c) {
		c.renderPath(path);
	}
	,
	render: function () {
		var c = this._backgroundContext;
		if (c != null && c.shouldRender()) {
			c.renderPath(this._sparkPath);
			c.renderPath(this._negativeSparkPath);
			this.renderPathWithTransform(this._markersPath, c);
			this.renderPathWithTransform(this._firstMarkerPath, c);
			this.renderPathWithTransform(this._lastMarkerPath, c);
			this.renderPathWithTransform(this._highMarkersPath, c);
			this.renderPathWithTransform(this._lowMarkersPath, c);
			this.renderPathWithTransform(this._negativeMarkersPath, c);
			c.renderPath(this._rangePath);
			c.renderPath(this._trendLinePath);
		}
		var m = new $.ig.AxisRenderMessage_Spark();
		m.renderInCanvas(true);
		this.renderingMessages().sendMessage(m);
		this.postRender();
	}
	,
	postRender: function () {
	}
	,
	preRender: function () {
	}
	,
	isValid: function () {
		return true;
	}
	,
	getFont: function (classString) {
		var span = this._cssSpan;
		if (span == null) {
			return null;
		}
		span.addClass(classString);
		var font = $.ig.FontUtil.prototype.getFont(span);
		span.removeClass(classString);
		return font;
	}
	,
	setSize: function (target, width, height) {
		target.css("width", width + "px");
		target.css("height", height + "px");
		target.attr("width", width);
		target.attr("height", height);
	}
	,
	$type: new $.ig.Type('XamSparklineView', $.ig.Object.prototype.$type, [$.ig.ISchedulableRender.prototype.$type])
}, true);

$.ig.util.defType('SparkFrame', 'Object', {
	init: function () {
		this.__buckets = new $.ig.List$1(Array, 0);
		this.__trendPoints = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		this.__markers = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		this.__negativeMarkers = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		this.__lowPoints = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		this.__highPoints = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
	},
	__buckets: null,
	buckets: function (value) {
		if (arguments.length === 1) {
			this.__buckets = value;
			return value;
		} else {
			return this.__buckets;
		}
	}
	,
	__trendPoints: null,
	trendPoints: function (value) {
		if (arguments.length === 1) {
			this.__trendPoints = value;
			return value;
		} else {
			return this.__trendPoints;
		}
	}
	,
	__markers: null,
	markers: function (value) {
		if (arguments.length === 1) {
			this.__markers = value;
			return value;
		} else {
			return this.__markers;
		}
	}
	,
	__negativeMarkers: null,
	negativeMarkers: function (value) {
		if (arguments.length === 1) {
			this.__negativeMarkers = value;
			return value;
		} else {
			return this.__negativeMarkers;
		}
	}
	,
	__lowPoints: null,
	lowPoints: function (value) {
		if (arguments.length === 1) {
			this.__lowPoints = value;
			return value;
		} else {
			return this.__lowPoints;
		}
	}
	,
	__highPoints: null,
	highPoints: function (value) {
		if (arguments.length === 1) {
			this.__highPoints = value;
			return value;
		} else {
			return this.__highPoints;
		}
	}
	,
	_firstPoint: null,
	firstPoint: function (value) {
		if (arguments.length === 1) {
			this._firstPoint = value;
			return value;
		} else {
			return this._firstPoint;
		}
	}
	,
	_lastPoint: null,
	lastPoint: function (value) {
		if (arguments.length === 1) {
			this._lastPoint = value;
			return value;
		} else {
			return this._lastPoint;
		}
	}
	,
	$type: new $.ig.Type('SparkFrame', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SparkFramePreparer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_controller: null,
	_offset: 0,
	_crossing: 0,
	prepareFrame: function (frame) {
		var $self = this;
		this._controller._view.doMargin(false);
		frame.buckets().clear();
		frame.trendPoints().clear();
		frame.markers().clear();
		frame.negativeMarkers().clear();
		frame.lowPoints().clear();
		frame.highPoints().clear();
		var valueColumn = this._controller._valueColumn;
		var itemCount = valueColumn.count();
		var width = this._controller._viewport.width();
		var height = this._controller._viewport.height();
		var rowsPerBucket = Math.floor((itemCount + 1) / width);
		var bucketSize = $.ig.truncate(Math.max(1, rowsPerBucket));
		var firstBucket = 0;
		var lastBucket = $.ig.truncate(Math.ceil(1 * itemCount / bucketSize));
		var zero = 0;
		var trendValues = new $.ig.List$1(Number, 0);
		var trendLineType = this._controller.model().trendLineType();
		var displayType = this._controller.model().displayType();
		var trendLinePeriod = this._controller.model().trendLinePeriod();
		var showTrendline = trendLineType != $.ig.TrendLineType.prototype.none && trendLinePeriod >= 1;
		var average = null;
		var trendCoefficients = null;
		if (displayType == $.ig.SparklineDisplayType.prototype.area || displayType == $.ig.SparklineDisplayType.prototype.line) {
			this._offset = 0;
		} else {
			if (lastBucket == 0) {
				this._offset = 0;
			} else {
				this._offset = width / lastBucket / 2;
			}
		}
		if (showTrendline) {
			var GetUnscaledX = function (i) { return i + 1; };
			var GetUnscaledY = function (i) { return valueColumn.item(i); };
			var GetScaledXValue = function (x) { return $self._controller.getScaledXValue(x); };
			var GetScaledYValue = function (y) { return $self._controller.getScaledYValue(y); };
			switch (this._controller.model().trendLineType()) {
				case $.ig.TrendLineType.prototype.simpleAverage:
					average = $.ig.TrendCalculators.prototype.sMA(valueColumn, trendLinePeriod);
					break;
				case $.ig.TrendLineType.prototype.exponentialAverage:
					average = $.ig.TrendCalculators.prototype.eMA(valueColumn, trendLinePeriod);
					break;
				case $.ig.TrendLineType.prototype.modifiedAverage:
					average = $.ig.TrendCalculators.prototype.mMA(valueColumn, trendLinePeriod);
					break;
				case $.ig.TrendLineType.prototype.cumulativeAverage:
					average = $.ig.TrendCalculators.prototype.cMA(valueColumn);
					break;
				case $.ig.TrendLineType.prototype.weightedAverage:
					average = $.ig.TrendCalculators.prototype.wMA(valueColumn, trendLinePeriod);
					break;
				case $.ig.TrendLineType.prototype.linearFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.linearFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.quadraticFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.quadraticFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.cubicFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.cubicFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.quarticFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.quarticFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.quinticFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.quinticFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.exponentialFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.exponentialFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.logarithmicFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.logarithmicFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
				case $.ig.TrendLineType.prototype.powerLawFit:
					trendCoefficients = $.ig.LeastSquaresFit.prototype.powerLawFit(itemCount, GetUnscaledX, GetUnscaledY);
					break;
			}
			if (average != null) {
				var en = average.getEnumerator();
				while (en.moveNext()) {
					var value = en.current();
					trendValues.add(value);
				}
			}
			if (trendCoefficients != null) {
				var xmin = firstBucket * bucketSize;
				var xmax = lastBucket * bucketSize;
				if (displayType == $.ig.SparklineDisplayType.prototype.area || displayType == $.ig.SparklineDisplayType.prototype.line) {
					xmax = xmax - 1;
				}
				var xStart = 0 + this._offset;
				var xEnd = width - this._offset;
				var d = 0;
				if (this._offset != 0) {
					d = 0.5;
				}
				for (var i = xStart; i <= xEnd; i += 2) {
					var p = i / (width - 1);
					var xi = xmin + p * (xmax - xmin);
					var yi = NaN;
					switch (trendLineType) {
						case $.ig.TrendLineType.prototype.linearFit:
							yi = $.ig.LeastSquaresFit.prototype.linearEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.quadraticFit:
							yi = $.ig.LeastSquaresFit.prototype.quadraticEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.cubicFit:
							yi = $.ig.LeastSquaresFit.prototype.cubicEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.quarticFit:
							yi = $.ig.LeastSquaresFit.prototype.quarticEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.quinticFit:
							yi = $.ig.LeastSquaresFit.prototype.quinticEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.exponentialFit:
							yi = $.ig.LeastSquaresFit.prototype.exponentialEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.logarithmicFit:
							yi = $.ig.LeastSquaresFit.prototype.logarithmicEvaluate(trendCoefficients, xi - d);
							break;
						case $.ig.TrendLineType.prototype.powerLawFit:
							yi = $.ig.LeastSquaresFit.prototype.powerLawEvaluate(trendCoefficients, xi - d);
							break;
					}
					xi = GetScaledXValue(xi);
					yi = GetScaledYValue(yi);
					if (!$.ig.util.isNaN(yi) && !Number.isInfinity(yi)) {
						frame.trendPoints().add({ __x: xi, __y: yi, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
					}
				}
			}
		}
		var minValue = this._controller._valueColumn.minimum();
		var maxValue = this._controller._valueColumn.maximum();
		var axisMinimum = this._controller.model().actualMinimum();
		var axisMaximum = this._controller.model().actualMaximum();
		this._crossing = height - (zero - axisMinimum) / (axisMaximum - axisMinimum) * height;
		if (this._crossing < 0) {
			this._crossing = 0;
		} else if (this._crossing > height) {
			this._crossing = height;
		}
		var crossingValue = this._crossing;
		var yValue;
		for (var i1 = firstBucket; i1 < lastBucket; i1++) {
			var bucket = this.getBucket(i1, bucketSize, itemCount);
			var bucketMin = bucket[1];
			var bucketMax = bucket[2];
			this.scaleBucket(bucket, itemCount, this._controller._viewport, axisMinimum, axisMaximum);
			yValue = bucket[1];
			if (bucket[2] < crossingValue) {
				yValue = bucket[2];
			}
			frame.buckets().add(bucket);
			if (!$.ig.util.isNaN(bucket[0]) && !$.ig.util.isNaN(bucket[1])) {
				frame.markers().add({ __x: bucket[0], __y: yValue, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (showTrendline && average != null) {
				var index = i1 * bucketSize;
				var scaledTrendValue = height - (((trendValues.__inner[index] - minValue) / (maxValue - minValue)) * height);
				frame.trendPoints().add({ __x: bucket[0] + this._offset, __y: scaledTrendValue, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (bucketMin < zero) {
				frame.negativeMarkers().add({ __x: bucket[0], __y: bucket[1], $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (minValue == bucketMin) {
				frame.lowPoints().add({ __x: bucket[0], __y: bucket[1], $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			if (maxValue == bucketMax) {
				frame.highPoints().add({ __x: bucket[0], __y: bucket[2], $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
		}
		if (frame.markers().count() == 0) {
			frame.firstPoint(frame.lastPoint({ __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }));
		} else {
			frame.firstPoint(frame.markers().__inner[0]);
			frame.lastPoint(frame.markers().__inner[frame.markers().count() - 1]);
		}
	}
	,
	getBucket: function (bucket, bucketSize, itemCount) {
		var i0 = Math.min(bucket * bucketSize, itemCount - 1);
		var i1 = Math.min(i0 + bucketSize - 1, itemCount - 1);
		var first = true;
		var min = 0;
		var max = 0;
		for (var i = i0; i <= i1; ++i) {
			var y = this._controller._valueColumn.item(i);
			if (first) {
				first = false;
				min = y;
				max = y;
			} else {
				min = Math.min(min, y);
				max = Math.max(max, y);
			}
		}
		var b = [ 0, 0, 0 ];
		b[0] = (0.5 * (i0 + i1));
		b[1] = min;
		b[2] = max;
		return b;
	}
	,
	scaleBucket: function (bucket, itemCount, viewport, minValue, maxValue) {
		var left = viewport.left();
		var top = viewport.top();
		var width = viewport.width();
		var height = viewport.height();
		if (this._controller.model().displayType() == $.ig.SparklineDisplayType.prototype.area || this._controller.model().displayType() == $.ig.SparklineDisplayType.prototype.line) {
			itemCount--;
		}
		if (itemCount < 0) {
			itemCount = 0;
		}
		var scaledValue = itemCount > 0 ? bucket[0] / itemCount : itemCount == 0 ? 0.5 : NaN;
		bucket[0] = left + scaledValue * width;
		if (this._controller.model().displayType() == $.ig.SparklineDisplayType.prototype.winLoss) {
			if (bucket[1] > 0) {
				bucket[1] = top;
				bucket[2] = top;
			} else if (bucket[1] < 0) {
				bucket[1] = top + height;
				bucket[2] = top + height;
			} else {
				bucket[1] = top + (1 - ((bucket[1] - minValue) / (maxValue - minValue))) * height;
				bucket[2] = top + (1 - ((bucket[2] - minValue) / (maxValue - minValue))) * height;
			}
		} else {
			bucket[1] = top + (1 - ((bucket[1] - minValue) / (maxValue - minValue))) * height;
			bucket[2] = top + (1 - ((bucket[2] - minValue) / (maxValue - minValue))) * height;
		}
	}
	,
	$type: new $.ig.Type('SparkFramePreparer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SparklineConnector', 'Object', {
	_model: null,
	_controller: null,
	_view: null,
	_horizontalAxis: null,
	_verticalAxis: null,
	init: function (parent, view) {
		$.ig.Object.prototype.init.call(this);
		this._view = view;
		if (parent == null) {
			return;
		}
		this._model = $.ig.util.cast($.ig.XamSparkline.prototype.$type, parent);
		this._horizontalAxis = this._model.horizontalAxis();
		this._verticalAxis = this._model.verticalAxis();
		var sp = new $.ig.ServiceProvider_Spark();
		sp.addService("ConfigurationMessages", new $.ig.MessageChannel_Spark());
		sp.addService("RenderingMessages", new $.ig.MessageChannel_Spark());
		sp.addService("InteractionMessages", new $.ig.MessageChannel_Spark());
		sp.addService("Model", this._model);
		sp.addService("View", this._view);
		sp.addService("HorizontalAxis", this._horizontalAxis);
		sp.addService("VerticalAxis", this._verticalAxis);
		this._model.serviceProvider(sp);
		this._view.serviceProvider(sp);
		this._horizontalAxis.serviceProvider(sp);
		this._verticalAxis.serviceProvider(sp);
		this._controller = new $.ig.SparklineController(sp);
		sp.addService("Controller", this._controller);
	},
	$type: new $.ig.Type('SparklineConnector', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SparklineController', 'DependencyObject', {
	init: function (provider) {
		var $self = this;
		this._framePreparer = new $.ig.SparkFramePreparer();
		this._currentFrame = new $.ig.SparkFrame();
		this._viewport = $.ig.Rect.prototype.empty();
		$.ig.DependencyObject.prototype.init.call(this);
		this._configurationMessages = provider.getService("ConfigurationMessages");
		this._renderingMessages = provider.getService("RenderingMessages");
		this._interactionMessages = provider.getService("InteractionMessages");
		this._fastItemsSource_Event = function (o, e) {
			$self.dataUpdatedOverride(e.action(), e.position(), e.count(), e.propertyName());
		};
		this.model(provider.getService("Model"));
		this._view = provider.getService("View");
		this._configurationMessages.attachTarget(this.configurationMessageReceived.runOn(this));
		this._interactionMessages.attachTarget(this.interactionMessageReceived.runOn(this));
	},
	_configurationMessages: null,
	_renderingMessages: null,
	_interactionMessages: null,
	__model: null,
	model: function (value) {
		if (arguments.length === 1) {
			var changed = this.__model != value;
			if (changed) {
				this.__model = value;
				this.updateInternalData();
			}
			return value;
		} else {
			return this.__model;
		}
	}
	,
	_view: null,
	itemsSource: function () {
		return this.model() != null ? this.model().itemsSource() : null;
	}
	,
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.SparklineController.prototype.fastItemsSourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.SparklineController.prototype.fastItemsSourceProperty);
		}
	}
	,
	_valueColumn: null,
	_labelColumn: null,
	_framePreparer: null,
	_currentFrame: null,
	valueMemberPath: function () {
		return this.model() != null ? this.model().valueMemberPath() : null;
	}
	,
	labelMemberPath: function () {
		return this.model() != null ? this.model().labelMemberPath() : null;
	}
	,
	_viewport: null,
	toolTip: function () {
		return this.model() != null ? this.model().toolTip() : null;
	}
	,
	_fastItemsSource_Event: null,
	getScaledXValue: function (unscaledValue) {
		var itemCount = this._valueColumn.count();
		if (this.model().displayType() == $.ig.SparklineDisplayType.prototype.area || this.model().displayType() == $.ig.SparklineDisplayType.prototype.line) {
			itemCount--;
		}
		if (itemCount < 0) {
			itemCount = 0;
		}
		var scaledValue = itemCount > 0 ? unscaledValue / itemCount : itemCount == 0 ? 0.5 : NaN;
		scaledValue = this._viewport.left() + (scaledValue) * this._viewport.width();
		return scaledValue;
	}
	,
	getScaledYValue: function (unscaledValue) {
		var scaledValue = ((unscaledValue - this.model().actualMinimum()) / (this.model().actualMaximum() - this.model().actualMinimum()));
		scaledValue = (1 - scaledValue);
		scaledValue = this._viewport.top() + (scaledValue) * this._viewport.height();
		return scaledValue;
	}
	,
	valid: function () {
		return this.itemsSource() != null && this.valueMemberPath() != null && !this._viewport.isEmpty() && this._viewport.width() > 0 && this._viewport.height() > 0 && this.model() != null && this.model().actualMinimum() != this.model().actualMaximum();
	}
	,
	requiresRefresh: function (propertyChangedMessage) {
		if (propertyChangedMessage.oldValue() != propertyChangedMessage.newValue()) {
			return true;
		}
		return false;
	}
	,
	updateFastItemsSource: function (oldValue, newValue) {
		var $self = this;
		if (this._fastItemsSource_Event == null) {
			this._fastItemsSource_Event = function (o, args) {
				$self.dataUpdatedOverride(args.action(), args.position(), args.count(), args.propertyName());
			};
		}
		var oldFastItemsSource = $.ig.util.cast($.ig.FastItemsSource.prototype.$type, oldValue);
		if (oldFastItemsSource != null) {
			oldFastItemsSource.event = $.ig.Delegate.prototype.remove(oldFastItemsSource.event, this._fastItemsSource_Event);
		}
		var newFastItemsSource = $.ig.util.cast($.ig.FastItemsSource.prototype.$type, newValue);
		if (newFastItemsSource != null) {
			newFastItemsSource.event = $.ig.Delegate.prototype.combine(newFastItemsSource.event, this._fastItemsSource_Event);
		}
	}
	,
	updateMinMax: function () {
		if (this.model() != null) {
			if (this._valueColumn == null) {
				this.model().actualMinimum(this.model().actualMaximum(NaN));
			} else {
				if ($.ig.util.isNaN(this.model().minimum())) {
					this.model().actualMinimum(this._valueColumn.minimum());
				} else {
					this.model().actualMinimum(this.model().minimum());
				}
				if ($.ig.util.isNaN(this.model().maximum())) {
					this.model().actualMaximum(this._valueColumn.maximum());
				} else {
					this.model().actualMaximum(this.model().maximum());
				}
				if (this.model().actualMinimum() == this.model().actualMaximum()) {
					if (!$.ig.util.isNaN(this.model().maximum()) && $.ig.util.isNaN(this.model().minimum())) {
						this.model().actualMinimum(this.model().actualMaximum() - 1);
					} else if ($.ig.util.isNaN(this.model().maximum()) && !$.ig.util.isNaN(this.model().minimum())) {
						this.model().actualMaximum(this.model().actualMinimum() + 1);
					} else {
						if (Math.floor(this.model().actualMinimum()) < this.model().actualMinimum()) {
							this.model().actualMinimum(Math.floor(this.model().actualMinimum()));
						} else {
							this.model().actualMinimum(Math.floor(this.model().actualMinimum()) - 1);
						}
						if (Math.ceil(this.model().actualMaximum()) > this.model().actualMaximum()) {
							this.model().actualMaximum(Math.ceil(this.model().actualMaximum()));
						} else {
							this.model().actualMaximum(Math.ceil(this.model().actualMaximum()) + 1);
						}
					}
				}
			}
		}
	}
	,
	updateValueColumn: function () {
		if (this.fastItemsSource() != null) {
			this.fastItemsSource().deregisterColumn(this._valueColumn);
			this._valueColumn = this.fastItemsSource().registerColumn(this.valueMemberPath(), null, false);
		} else {
			this._valueColumn = null;
		}
	}
	,
	updateLabelColumn: function () {
		if (this.fastItemsSource() != null) {
			this.fastItemsSource().deregisterColumn(this._labelColumn);
			this._labelColumn = this.fastItemsSource().registerColumnObject(this.labelMemberPath(), null, false);
			this.model().labelColumn(this._labelColumn);
		} else {
			this.model().labelColumn(this._labelColumn = null);
		}
	}
	,
	updateAxes: function () {
		var message = new $.ig.AxisRenderMessage_Spark();
		this._renderingMessages.sendMessage(message);
	}
	,
	updateInternalData: function () {
		this.fastItemsSource(this.getFastItemsSource(this.itemsSource()));
		this.updateValueColumn();
		this.updateLabelColumn();
		this.updateMinMax();
		this.updateAxes();
	}
	,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		this.updateValueColumn();
		this.updateLabelColumn();
		this.updateMinMax();
		this.updateAxes();
		this.refresh();
	}
	,
	updateToolTip: function () {
		var m = new $.ig.ToooltipTemplateMessage_Spark();
		m.template(this.toolTip());
		this._renderingMessages.sendMessage(m);
	}
	,
	configurationMessageReceived: function (m) {
		var cm = m;
		if ($.ig.util.cast($.ig.PropertyChangedMessage_Spark.prototype.$type, cm) !== null) {
			this.propertyChangedMessageReceived(cm);
		} else if ($.ig.util.cast($.ig.ContainerSizeChangedMessage_Spark.prototype.$type, m) !== null) {
			this._renderingMessages.sendMessage(m);
		} else if ($.ig.util.cast($.ig.DataChangedMessage_Spark.prototype.$type, m) !== null) {
			this.handleDataChanged(m);
		}
	}
	,
	handleDataChanged: function (m) {
		var itemsSource = this.fastItemsSource();
		if (itemsSource == null) {
			return;
		}
		switch (m.change()) {
			case $.ig.NotifyCollectionChangedAction.prototype.replace:
				itemsSource.handleCollectionChanged(new $.ig.NotifyCollectionChangedEventArgs(2, $.ig.NotifyCollectionChangedAction.prototype.replace, m.newItem(), m.oldItem(), m.index()));
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.add:
				itemsSource.handleCollectionChanged(new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.add, m.newItem(), m.index()));
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.remove:
				itemsSource.handleCollectionChanged(new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.remove, m.oldItem(), m.index()));
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.reset:
				itemsSource.handleCollectionChanged(new $.ig.NotifyCollectionChangedEventArgs(0, $.ig.NotifyCollectionChangedAction.prototype.reset));
				break;
		}
		this.updateInternalData();
	}
	,
	interactionMessageReceived: function (m) {
		var im = m;
		if ($.ig.util.cast($.ig.ViewportChangedMessage_Spark.prototype.$type, im) !== null) {
			this.viewportChangedMessageReceived(im);
		} else if ($.ig.util.cast($.ig.MouseLeaveMessage_Spark.prototype.$type, im) !== null) {
			this.mouseLeaveMessageReceived(im);
		} else if ($.ig.util.cast($.ig.MouseMoveMessage_Spark.prototype.$type, im) !== null) {
			this.mouseMoveMessageReceived(im);
		} else if ($.ig.util.cast($.ig.ContainerSizeChangedMessage_Spark.prototype.$type, im) !== null) {
			this._renderingMessages.sendMessage(m);
		}
	}
	,
	propertyChangedMessageReceived: function (propertyChangedMessage) {
		switch (propertyChangedMessage.propertyName()) {
			case $.ig.XamSparkline.prototype.itemsSourcePropertyName:
				this.updateInternalData();
				break;
			case $.ig.XamSparkline.prototype.valueMemberPathPropertyName:
				this.updateInternalData();
				break;
			case $.ig.XamSparkline.prototype.labelMemberPathPropertyName:
				this.updateInternalData();
				break;
			case $.ig.XamSparkline.prototype.toolTipPropertyName:
				this.updateToolTip();
				break;
			case $.ig.XamSparkline.prototype.minimumPropertyName:
			case $.ig.XamSparkline.prototype.maximumPropertyName:
				this.updateMinMax();
				this.updateAxes();
				break;
			case $.ig.XamSparkline.prototype.horizontalAxisLabelPropertyName:
			case $.ig.XamSparkline.prototype.verticalAxisLabelPropertyName:
				this.updateAxes();
				break;
			case $.ig.XamSparkline.prototype.brushPropertyName:
			case $.ig.XamSparkline.prototype.negativeBrushPropertyName:
			case $.ig.XamSparkline.prototype.markerBrushPropertyName:
			case $.ig.XamSparkline.prototype.firstMarkerBrushPropertyName:
			case $.ig.XamSparkline.prototype.lastMarkerBrushPropertyName:
			case $.ig.XamSparkline.prototype.highMarkerBrushPropertyName:
			case $.ig.XamSparkline.prototype.lowMarkerBrushPropertyName:
			case $.ig.XamSparkline.prototype.negativeMarkerBrushPropertyName:
			case $.ig.XamSparkline.prototype.trendLineBrushPropertyName:
			case $.ig.XamSparkline.prototype.normalRangeFillPropertyName:
				var b = new $.ig.BrushChangedMessage_Spark();
				b.brush($.ig.util.cast($.ig.Brush.prototype.$type, propertyChangedMessage.newValue()));
				b.brushPropertyName(propertyChangedMessage.propertyName());
				this._renderingMessages.sendMessage(b);
				break;
			case $.ig.XamSparkline.prototype.markerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.firstMarkerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.lastMarkerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.highMarkerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.lowMarkerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.negativeMarkerVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.normalRangeVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.toolTipVisibilityPropertyName:
				var v = new $.ig.VisibilityMessage_Spark();
				v.visibility($.ig.util.getEnumValue(propertyChangedMessage.newValue()));
				v.pathPropertyName(propertyChangedMessage.propertyName());
				this._renderingMessages.sendMessage(v);
				this._view.doMargin(true);
				break;
			case $.ig.XamSparkline.prototype.markerSizePropertyName:
			case $.ig.XamSparkline.prototype.firstMarkerSizePropertyName:
			case $.ig.XamSparkline.prototype.lastMarkerSizePropertyName:
			case $.ig.XamSparkline.prototype.highMarkerSizePropertyName:
			case $.ig.XamSparkline.prototype.lowMarkerSizePropertyName:
			case $.ig.XamSparkline.prototype.negativeMarkerSizePropertyName:
				this._view.doMargin(true);
				break;
			case $.ig.XamSparkline.prototype.lineThicknessPropertyName:
			case $.ig.XamSparkline.prototype.trendLineThicknessPropertyName:
				var n = new $.ig.NumberChangedMessage_Spark();
				n.value(propertyChangedMessage.newValue());
				n.propertyName(propertyChangedMessage.propertyName());
				this._renderingMessages.sendMessage(n);
				break;
			case $.ig.XamSparkline.prototype.horizontalAxisBrushPropertyName:
			case $.ig.XamSparkline.prototype.verticalAxisBrushPropertyName:
			case $.ig.XamSparkline.prototype.horizontalAxisVisibilityPropertyName:
			case $.ig.XamSparkline.prototype.verticalAxisVisibilityPropertyName:
				var cm = new $.ig.ClearMessage_Spark();
				cm.axisVisibilityChanged(true);
				this._renderingMessages.sendMessage(cm);
				break;
			case "Container":
				var m = new $.ig.ContainerMessage_Spark();
				m.container(propertyChangedMessage.newValue());
				this._renderingMessages.sendMessage(m);
				break;
		}
		if (this.requiresRefresh(propertyChangedMessage)) {
			this.refresh();
		}
	}
	,
	viewportChangedMessageReceived: function (viewportChangedMessage) {
		this._viewport = new $.ig.Rect(0, viewportChangedMessage.newLeft(), viewportChangedMessage.newTop(), viewportChangedMessage.newWidth(), viewportChangedMessage.newHeight());
		this.refresh();
	}
	,
	mouseMoveMessageReceived: function (mouseMoveMessage) {
		var cm = new $.ig.ClearMessage_Spark();
		cm.layer($.ig.SparkLayerType.prototype.toolTipLayer);
		this._renderingMessages.sendMessage(cm);
		var tm = new $.ig.TooltipMessage_Spark();
		tm.layer($.ig.SparkLayerType.prototype.toolTipLayer);
		tm.xOffset(mouseMoveMessage.position().__x + 10);
		tm.yOffset(mouseMoveMessage.position().__y + 10);
		if (this._valueColumn != null && this._valueColumn.count() > 0) {
			var c = new $.ig.SparklineToolTipContext();
			c.high(this._valueColumn.maximum());
			c.low(this._valueColumn.minimum());
			c.first(this._valueColumn.item(0));
			c.last(this._valueColumn.item(this._valueColumn.count() - 1));
			tm.context(c);
		}
		this._renderingMessages.sendMessage(tm);
	}
	,
	mouseLeaveMessageReceived: function (mouseLeaveMessage) {
		var cm = new $.ig.ClearMessage_Spark();
		cm.layer($.ig.SparkLayerType.prototype.toolTipLayer);
		this._renderingMessages.sendMessage(cm);
	}
	,
	clearLayer: function (layerType) {
		var cm = new $.ig.ClearMessage_Spark();
		cm.layer(layerType);
		this._renderingMessages.sendMessage(cm);
	}
	,
	clearData: function () {
		this.clearLayer($.ig.SparkLayerType.prototype.sparkLayer);
		this.clearLayer($.ig.SparkLayerType.prototype.markerLayer);
		this.clearLayer($.ig.SparkLayerType.prototype.trendLayer);
		this.clearLayer($.ig.SparkLayerType.prototype.rangeLayer);
	}
	,
	refresh: function () {
		this.clearData();
		if (!this.valid()) {
			return;
		}
		this._framePreparer._controller = this;
		this._framePreparer.prepareFrame(this._currentFrame);
		switch (this.model().displayType()) {
			case $.ig.SparklineDisplayType.prototype.column:
				this.refreshColumns();
				break;
			case $.ig.SparklineDisplayType.prototype.winLoss:
				this.refreshWinLoss();
				break;
			case $.ig.SparklineDisplayType.prototype.line:
			case $.ig.SparklineDisplayType.prototype.area:
				this.refreshPolygon();
				break;
		}
		var rangeMessage = new $.ig.NormalRangeMessage_Spark();
		rangeMessage.x(0);
		rangeMessage.y(this.getScaledYValue(this.model().normalRangeMaximum()));
		rangeMessage.width(this._viewport.width());
		rangeMessage.height(Math.abs(this.getScaledYValue(this.model().normalRangeMaximum()) - this.getScaledYValue(this.model().normalRangeMinimum())));
		rangeMessage.displayInFront(this.model().displayNormalRangeInFront());
		this._renderingMessages.sendMessage(rangeMessage);
		var trendMessage = new $.ig.TrendLineMessage_Spark();
		trendMessage.points(this._currentFrame.trendPoints().toArray());
		this._renderingMessages.sendMessage(trendMessage);
	}
	,
	refreshColumns: function () {
		var columnMessage = new $.ig.ColumnMessage_Spark();
		columnMessage.columns(this._currentFrame.markers());
		columnMessage.negativeColumns(this._currentFrame.negativeMarkers());
		columnMessage.lowColumns(this._currentFrame.lowPoints());
		columnMessage.highColumns(this._currentFrame.highPoints());
		columnMessage.firstColumn(this._currentFrame.firstPoint());
		columnMessage.lastColumn(this._currentFrame.lastPoint());
		columnMessage.bucketCount(this._currentFrame.buckets().count());
		columnMessage.crossing(this._framePreparer._crossing);
		columnMessage.offset(this._framePreparer._offset);
		columnMessage.displayType(this.model().displayType());
		this._renderingMessages.sendMessage(columnMessage);
		this.refreshMarkers();
	}
	,
	refreshWinLoss: function () {
		var message = new $.ig.WinLossColumnMessage_Spark();
		message.columns(this._currentFrame.markers());
		message.negativeColumns(this._currentFrame.negativeMarkers());
		message.lowColumns(this._currentFrame.lowPoints());
		message.highColumns(this._currentFrame.highPoints());
		message.firstColumn(this._currentFrame.firstPoint());
		message.lastColumn(this._currentFrame.lastPoint());
		message.bucketCount(this._currentFrame.buckets().count());
		message.crossing(this._framePreparer._crossing);
		message.offset(this._framePreparer._offset);
		message.displayType(this.model().displayType());
		this._renderingMessages.sendMessage(message);
	}
	,
	refreshPolygon: function () {
		if (this._currentFrame.buckets().count() == 0) {
			return;
		}
		var segments = new $.ig.List$1($.ig.List$1.prototype.$type.specialize(Array), 0);
		var segment = new $.ig.List$1(Array, 0);
		var lastBucketIsNull = true;
		var en = this._currentFrame.buckets().getEnumerator();
		while (en.moveNext()) {
			var bucket = en.current();
			if ($.ig.util.isNaN(bucket[1]) || Number.isInfinity(bucket[1])) {
				if (!lastBucketIsNull) {
					segments.add(segment);
				}
				lastBucketIsNull = true;
				continue;
			}
			if (lastBucketIsNull) {
				segment = new $.ig.List$1(Array, 0);
				lastBucketIsNull = false;
			}
			segment.add(bucket);
		}
		if (!lastBucketIsNull) {
			segments.add(segment);
		}
		var polygonMessage = new $.ig.PolygonMessage_Spark();
		polygonMessage.points(new $.ig.List$1(Array, 0));
		if (this.model().unknownValuePlotting() == $.ig.UnknownValuePlotting.prototype.linearInterpolate) {
			var InterpolatedBuckets = new $.ig.List$1(Array, 0);
			var en1 = segments.getEnumerator();
			while (en1.moveNext()) {
				var validSegment = en1.current();
				InterpolatedBuckets.addRange(validSegment);
			}
			var pts = this.createPolygonSegment(InterpolatedBuckets);
			polygonMessage.points().add(pts);
		} else {
			var en2 = segments.getEnumerator();
			while (en2.moveNext()) {
				var validSegment1 = en2.current();
				var pts1 = this.createPolygonSegment(validSegment1);
				polygonMessage.points().add(pts1);
			}
		}
		this._renderingMessages.sendMessage(polygonMessage);
		this.refreshMarkers();
	}
	,
	createPolygonSegment: function (buckets) {
		var crossing = this._framePreparer._crossing;
		var points = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		var yValue;
		var isArea = this.model().displayType() == $.ig.SparklineDisplayType.prototype.area;
		var en = buckets.getEnumerator();
		while (en.moveNext()) {
			var bucket = en.current();
			yValue = bucket[1];
			if (isArea) {
				if (bucket[2] <= crossing) {
					yValue = bucket[2];
				}
			}
			points.add({ __x: bucket[0], __y: yValue, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		if (this.model().displayType() == $.ig.SparklineDisplayType.prototype.line) {
			var points2 = new $.ig.List$1($.ig.Point.prototype.$type, 0);
			var en1 = buckets.getEnumerator();
			while (en1.moveNext()) {
				var bucket1 = en1.current();
				points2.add({ __x: bucket1[0], __y: bucket1[2], $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			points2.reverse();
			points.addRange(points2);
		}
		if (this.model().displayType() == $.ig.SparklineDisplayType.prototype.area) {
			var firstPoint = points.__inner[0];
			var lastPoint = points.__inner[points.count() - 1];
			points.add({ __x: lastPoint.__x, __y: this._framePreparer._crossing, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			points.add({ __x: firstPoint.__x, __y: this._framePreparer._crossing, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		return points.toArray();
	}
	,
	refreshMarkers: function () {
		var markerMessage = new $.ig.MarkerMessage_Spark();
		markerMessage.markerPoints(this._currentFrame.markers());
		markerMessage.negativeMarkerPoints(this._currentFrame.negativeMarkers());
		markerMessage.lowPoints(this._currentFrame.lowPoints());
		markerMessage.highPoints(this._currentFrame.highPoints());
		markerMessage.firstPoint(this._currentFrame.firstPoint());
		markerMessage.lastPoint(this._currentFrame.lastPoint());
		markerMessage.markerSize(this.model().markerSize());
		markerMessage.firstMarkerSize(this.model().firstMarkerSize());
		markerMessage.lastMarkerSize(this.model().lastMarkerSize());
		markerMessage.highMarkerSize(this.model().highMarkerSize());
		markerMessage.lowMarkerSize(this.model().lowMarkerSize());
		markerMessage.negativeMarkerSize(this.model().negativeMarkerSize());
		this._renderingMessages.sendMessage(markerMessage);
	}
	,
	getFastItemsSource: function (target) {
		var fastItemsSource = null;
		if (this.itemsSource() != null) {
			var itemsSourceReference = null;
			fastItemsSource = new $.ig.FastItemsSource();
			fastItemsSource.itemsSource(this.itemsSource());
			itemsSourceReference = new $.ig.FastItemsSourceReference(fastItemsSource);
			itemsSourceReference._references++;
			fastItemsSource = itemsSourceReference._fastItemsSource;
		}
		return fastItemsSource;
	}
	,
	releaseFastItemsSource: function (itemsSource) {
		if (itemsSource != null) {
			var itemsSourceReference = null;
			--itemsSourceReference._references;
		}
		return null;
	}
	,
	$type: new $.ig.Type('SparklineController', $.ig.DependencyObject.prototype.$type, [$.ig.IFastItemsSourceProvider.prototype.$type])
}, true);

$.ig.util.defType('SparklineToolTipContext', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_high: 0,
	high: function (value) {
		if (arguments.length === 1) {
			this._high = value;
			return value;
		} else {
			return this._high;
		}
	}
	,
	_low: 0,
	low: function (value) {
		if (arguments.length === 1) {
			this._low = value;
			return value;
		} else {
			return this._low;
		}
	}
	,
	_first: 0,
	first: function (value) {
		if (arguments.length === 1) {
			this._first = value;
			return value;
		} else {
			return this._first;
		}
	}
	,
	_last: 0,
	last: function (value) {
		if (arguments.length === 1) {
			this._last = value;
			return value;
		} else {
			return this._last;
		}
	}
	,
	$type: new $.ig.Type('SparklineToolTipContext', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RenderingMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	_layer: 0,
	layer: function (value) {
		if (arguments.length === 1) {
			this._layer = value;
			return value;
		} else {
			return this._layer;
		}
	}
	,
	$type: new $.ig.Type('RenderingMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('AxisRenderMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	_renderInCanvas: false,
	renderInCanvas: function (value) {
		if (arguments.length === 1) {
			this._renderInCanvas = value;
			return value;
		} else {
			return this._renderInCanvas;
		}
	}
	,
	$type: new $.ig.Type('AxisRenderMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ClearMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	_axisVisibilityChanged: false,
	axisVisibilityChanged: function (value) {
		if (arguments.length === 1) {
			this._axisVisibilityChanged = value;
			return value;
		} else {
			return this._axisVisibilityChanged;
		}
	}
	,
	$type: new $.ig.Type('ClearMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ColumnMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	_columns: null,
	columns: function (value) {
		if (arguments.length === 1) {
			this._columns = value;
			return value;
		} else {
			return this._columns;
		}
	}
	,
	_negativeColumns: null,
	negativeColumns: function (value) {
		if (arguments.length === 1) {
			this._negativeColumns = value;
			return value;
		} else {
			return this._negativeColumns;
		}
	}
	,
	_firstColumn: null,
	firstColumn: function (value) {
		if (arguments.length === 1) {
			this._firstColumn = value;
			return value;
		} else {
			return this._firstColumn;
		}
	}
	,
	_lastColumn: null,
	lastColumn: function (value) {
		if (arguments.length === 1) {
			this._lastColumn = value;
			return value;
		} else {
			return this._lastColumn;
		}
	}
	,
	_lowColumns: null,
	lowColumns: function (value) {
		if (arguments.length === 1) {
			this._lowColumns = value;
			return value;
		} else {
			return this._lowColumns;
		}
	}
	,
	_highColumns: null,
	highColumns: function (value) {
		if (arguments.length === 1) {
			this._highColumns = value;
			return value;
		} else {
			return this._highColumns;
		}
	}
	,
	_offset: 0,
	offset: function (value) {
		if (arguments.length === 1) {
			this._offset = value;
			return value;
		} else {
			return this._offset;
		}
	}
	,
	_crossing: 0,
	crossing: function (value) {
		if (arguments.length === 1) {
			this._crossing = value;
			return value;
		} else {
			return this._crossing;
		}
	}
	,
	_bucketCount: 0,
	bucketCount: function (value) {
		if (arguments.length === 1) {
			this._bucketCount = value;
			return value;
		} else {
			return this._bucketCount;
		}
	}
	,
	_displayType: 0,
	displayType: function (value) {
		if (arguments.length === 1) {
			this._displayType = value;
			return value;
		} else {
			return this._displayType;
		}
	}
	,
	$type: new $.ig.Type('ColumnMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ConfigurationMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	$type: new $.ig.Type('ConfigurationMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('InteractionMessage_Spark', 'Message_Spark', {
	init: function () {
		$.ig.Message_Spark.prototype.init.call(this);
	},
	$type: new $.ig.Type('InteractionMessage_Spark', $.ig.Message_Spark.prototype.$type)
}, true);

$.ig.util.defType('MarkerMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	_markerPoints: null,
	markerPoints: function (value) {
		if (arguments.length === 1) {
			this._markerPoints = value;
			return value;
		} else {
			return this._markerPoints;
		}
	}
	,
	_negativeMarkerPoints: null,
	negativeMarkerPoints: function (value) {
		if (arguments.length === 1) {
			this._negativeMarkerPoints = value;
			return value;
		} else {
			return this._negativeMarkerPoints;
		}
	}
	,
	_firstPoint: null,
	firstPoint: function (value) {
		if (arguments.length === 1) {
			this._firstPoint = value;
			return value;
		} else {
			return this._firstPoint;
		}
	}
	,
	_lastPoint: null,
	lastPoint: function (value) {
		if (arguments.length === 1) {
			this._lastPoint = value;
			return value;
		} else {
			return this._lastPoint;
		}
	}
	,
	_lowPoints: null,
	lowPoints: function (value) {
		if (arguments.length === 1) {
			this._lowPoints = value;
			return value;
		} else {
			return this._lowPoints;
		}
	}
	,
	_highPoints: null,
	highPoints: function (value) {
		if (arguments.length === 1) {
			this._highPoints = value;
			return value;
		} else {
			return this._highPoints;
		}
	}
	,
	_markerSize: 0,
	markerSize: function (value) {
		if (arguments.length === 1) {
			this._markerSize = value;
			return value;
		} else {
			return this._markerSize;
		}
	}
	,
	_firstMarkerSize: 0,
	firstMarkerSize: function (value) {
		if (arguments.length === 1) {
			this._firstMarkerSize = value;
			return value;
		} else {
			return this._firstMarkerSize;
		}
	}
	,
	_lastMarkerSize: 0,
	lastMarkerSize: function (value) {
		if (arguments.length === 1) {
			this._lastMarkerSize = value;
			return value;
		} else {
			return this._lastMarkerSize;
		}
	}
	,
	_highMarkerSize: 0,
	highMarkerSize: function (value) {
		if (arguments.length === 1) {
			this._highMarkerSize = value;
			return value;
		} else {
			return this._highMarkerSize;
		}
	}
	,
	_lowMarkerSize: 0,
	lowMarkerSize: function (value) {
		if (arguments.length === 1) {
			this._lowMarkerSize = value;
			return value;
		} else {
			return this._lowMarkerSize;
		}
	}
	,
	_negativeMarkerSize: 0,
	negativeMarkerSize: function (value) {
		if (arguments.length === 1) {
			this._negativeMarkerSize = value;
			return value;
		} else {
			return this._negativeMarkerSize;
		}
	}
	,
	$type: new $.ig.Type('MarkerMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('MessageChannel_Spark', 'Object', {
	init: function () {
		this.__sendQueue = new $.ig.Array();
		$.ig.Object.prototype.init.call(this);
	},
	__sendQueue: null,
	sendMessage: function (message) {
		if (this.messageSent != null) {
			this.messageSent(message);
		} else {
			this.__sendQueue.push(message);
		}
	}
	,
	attachTarget: function (m) {
		this.messageSent = $.ig.Delegate.prototype.combine(this.messageSent, m);
		while (this.__sendQueue.length > 0) {
			var mess = this.__sendQueue.shift();
			this.messageSent(mess);
		}
	}
	,
	messageSent: null,
	detachTarget: function (m) {
		this.messageSent = $.ig.Delegate.prototype.remove(this.messageSent, m);
	}
	,
	__connectedTo: null,
	connectTo: function (m) {
		this.__connectedTo = m;
		this.attachTarget(this.sendToNext.runOn(this));
	}
	,
	detachFromNext: function () {
		if (this.__connectedTo == null) {
			return;
		}
		this.detachTarget(this.sendToNext.runOn(this));
		this.__connectedTo = null;
	}
	,
	sendToNext: function (m) {
		if (this.__connectedTo != null) {
			this.__connectedTo.sendMessage(m);
		}
	}
	,
	toString: function () {
		return "MessageQueue";
	}
	,
	$type: new $.ig.Type('MessageChannel_Spark', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MouseLeaveMessage_Spark', 'InteractionMessage_Spark', {
	init: function () {
		$.ig.InteractionMessage_Spark.prototype.init.call(this);
	},
	$type: new $.ig.Type('MouseLeaveMessage_Spark', $.ig.InteractionMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('MouseMoveMessage_Spark', 'InteractionMessage_Spark', {
	init: function () {
		$.ig.InteractionMessage_Spark.prototype.init.call(this);
	},
	__position: null,
	position: function (value) {
		if (arguments.length === 1) {
			this.__position = value;
			return value;
		} else {
			return this.__position;
		}
	}
	,
	$type: new $.ig.Type('MouseMoveMessage_Spark', $.ig.InteractionMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('NormalRangeMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	__x: 0,
	x: function (value) {
		if (arguments.length === 1) {
			this.__x = value;
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
			return value;
		} else {
			return this.__height;
		}
	}
	,
	__displayInFront: false,
	displayInFront: function (value) {
		if (arguments.length === 1) {
			this.__displayInFront = value;
			return value;
		} else {
			return this.__displayInFront;
		}
	}
	,
	$type: new $.ig.Type('NormalRangeMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('PolygonMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
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
	$type: new $.ig.Type('PolygonMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('PropertyChangedMessage_Spark', 'ConfigurationMessage_Spark', {
	init: function () {
		$.ig.ConfigurationMessage_Spark.prototype.init.call(this);
	},
	__propertyName: null,
	propertyName: function (value) {
		if (arguments.length === 1) {
			this.__propertyName = value;
			return value;
		} else {
			return this.__propertyName;
		}
	}
	,
	__oldValue: null,
	oldValue: function (value) {
		if (arguments.length === 1) {
			this.__oldValue = value;
			return value;
		} else {
			return this.__oldValue;
		}
	}
	,
	__newValue: null,
	newValue: function (value) {
		if (arguments.length === 1) {
			this.__newValue = value;
			return value;
		} else {
			return this.__newValue;
		}
	}
	,
	toString: function () {
		var oldValue = "null";
		var newVAlue = "null";
		if (this.oldValue() != null) {
			oldValue = this.oldValue().toString();
		}
		if (this.newValue() != null) {
			newVAlue = this.newValue().toString();
		}
		return "PropertyChangedMessage[" + this.propertyName() + ", " + oldValue + ", " + newVAlue + "]";
	}
	,
	$type: new $.ig.Type('PropertyChangedMessage_Spark', $.ig.ConfigurationMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ServiceProvider_Spark', 'Object', {
	init: function () {
		this.__dict = new $.ig.Dictionary(0);
		$.ig.Object.prototype.init.call(this);
	},
	__dict: null,
	addService: function (key, service) {
		this.__dict.item(key, service);
	}
	,
	getService: function (key) {
		return this.__dict.item(key);
	}
	,
	$type: new $.ig.Type('ServiceProvider_Spark', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TooltipMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	__xOffset: 0,
	xOffset: function (value) {
		if (arguments.length === 1) {
			this.__xOffset = value;
			return value;
		} else {
			return this.__xOffset;
		}
	}
	,
	__yOffset: 0,
	yOffset: function (value) {
		if (arguments.length === 1) {
			this.__yOffset = value;
			return value;
		} else {
			return this.__yOffset;
		}
	}
	,
	__context: null,
	context: function (value) {
		if (arguments.length === 1) {
			this.__context = value;
			return value;
		} else {
			return this.__context;
		}
	}
	,
	$type: new $.ig.Type('TooltipMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ToooltipTemplateMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	__template: null,
	template: function (value) {
		if (arguments.length === 1) {
			this.__template = value;
			return value;
		} else {
			return this.__template;
		}
	}
	,
	$type: new $.ig.Type('ToooltipTemplateMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('TrendLineMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
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
	$type: new $.ig.Type('TrendLineMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ViewportChangedMessage_Spark', 'InteractionMessage_Spark', {
	init: function () {
		$.ig.InteractionMessage_Spark.prototype.init.call(this);
	},
	__newTop: 0,
	newTop: function (value) {
		if (arguments.length === 1) {
			this.__newTop = value;
			return value;
		} else {
			return this.__newTop;
		}
	}
	,
	__newLeft: 0,
	newLeft: function (value) {
		if (arguments.length === 1) {
			this.__newLeft = value;
			return value;
		} else {
			return this.__newLeft;
		}
	}
	,
	__newWidth: 0,
	newWidth: function (value) {
		if (arguments.length === 1) {
			this.__newWidth = value;
			return value;
		} else {
			return this.__newWidth;
		}
	}
	,
	__newHeight: 0,
	newHeight: function (value) {
		if (arguments.length === 1) {
			this.__newHeight = value;
			return value;
		} else {
			return this.__newHeight;
		}
	}
	,
	toString: function () {
		return "ViewportChangedMessage[" + this.newTop().toString() + ", " + this.newLeft().toString() + ", " + this.newWidth().toString() + ", " + this.newHeight().toString() + "]";
	}
	,
	$type: new $.ig.Type('ViewportChangedMessage_Spark', $.ig.InteractionMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('ContainerSizeChangedMessage_Spark', 'InteractionMessage_Spark', {
	init: function () {
		$.ig.InteractionMessage_Spark.prototype.init.call(this);
	},
	$type: new $.ig.Type('ContainerSizeChangedMessage_Spark', $.ig.InteractionMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('WinLossColumnMessage_Spark', 'RenderingMessage_Spark', {
	init: function () {
		$.ig.RenderingMessage_Spark.prototype.init.call(this);
	},
	_columns: null,
	columns: function (value) {
		if (arguments.length === 1) {
			this._columns = value;
			return value;
		} else {
			return this._columns;
		}
	}
	,
	_negativeColumns: null,
	negativeColumns: function (value) {
		if (arguments.length === 1) {
			this._negativeColumns = value;
			return value;
		} else {
			return this._negativeColumns;
		}
	}
	,
	_firstColumn: null,
	firstColumn: function (value) {
		if (arguments.length === 1) {
			this._firstColumn = value;
			return value;
		} else {
			return this._firstColumn;
		}
	}
	,
	_lastColumn: null,
	lastColumn: function (value) {
		if (arguments.length === 1) {
			this._lastColumn = value;
			return value;
		} else {
			return this._lastColumn;
		}
	}
	,
	_lowColumns: null,
	lowColumns: function (value) {
		if (arguments.length === 1) {
			this._lowColumns = value;
			return value;
		} else {
			return this._lowColumns;
		}
	}
	,
	_highColumns: null,
	highColumns: function (value) {
		if (arguments.length === 1) {
			this._highColumns = value;
			return value;
		} else {
			return this._highColumns;
		}
	}
	,
	_offset: 0,
	offset: function (value) {
		if (arguments.length === 1) {
			this._offset = value;
			return value;
		} else {
			return this._offset;
		}
	}
	,
	_crossing: 0,
	crossing: function (value) {
		if (arguments.length === 1) {
			this._crossing = value;
			return value;
		} else {
			return this._crossing;
		}
	}
	,
	_bucketCount: 0,
	bucketCount: function (value) {
		if (arguments.length === 1) {
			this._bucketCount = value;
			return value;
		} else {
			return this._bucketCount;
		}
	}
	,
	_displayType: 0,
	displayType: function (value) {
		if (arguments.length === 1) {
			this._displayType = value;
			return value;
		} else {
			return this._displayType;
		}
	}
	,
	$type: new $.ig.Type('WinLossColumnMessage_Spark', $.ig.RenderingMessage_Spark.prototype.$type)
}, true);

$.ig.util.defType('XamSparkline', 'Control', {
	init: function () {
		this.__configurationMessages = new $.ig.MessageChannel_Spark();
		this.__actualMinimum = NaN;
		this.__actualMaximum = NaN;
		$.ig.Control.prototype.init.call(this);
		this.defaultStyleKey($.ig.XamSparkline.prototype.$type);
		this.verticalAxis(new $.ig.VerticalAxisView(this));
		this.horizontalAxis(new $.ig.HorizontalAxisView(this));
		this.__connector = new $.ig.SparklineConnector(this, new $.ig.XamSparklineView());
	},
	__connector: null,
	brush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.brushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.brushProperty);
		}
	}
	,
	negativeBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.negativeBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.negativeBrushProperty);
		}
	}
	,
	markerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.markerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.markerBrushProperty);
		}
	}
	,
	negativeMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.negativeMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.negativeMarkerBrushProperty);
		}
	}
	,
	firstMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.firstMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.firstMarkerBrushProperty);
		}
	}
	,
	lastMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lastMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.lastMarkerBrushProperty);
		}
	}
	,
	highMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.highMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.highMarkerBrushProperty);
		}
	}
	,
	lowMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lowMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.lowMarkerBrushProperty);
		}
	}
	,
	trendLineBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.trendLineBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.trendLineBrushProperty);
		}
	}
	,
	horizontalAxisBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.horizontalAxisBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.horizontalAxisBrushProperty);
		}
	}
	,
	verticalAxisBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.verticalAxisBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.verticalAxisBrushProperty);
		}
	}
	,
	normalRangeFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.normalRangeFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.normalRangeFillProperty);
		}
	}
	,
	horizontalAxisVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.horizontalAxisVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.horizontalAxisVisibilityProperty));
		}
	}
	,
	verticalAxisVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.verticalAxisVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.verticalAxisVisibilityProperty));
		}
	}
	,
	markerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.markerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.markerVisibilityProperty));
		}
	}
	,
	negativeMarkerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.negativeMarkerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.negativeMarkerVisibilityProperty));
		}
	}
	,
	firstMarkerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.firstMarkerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.firstMarkerVisibilityProperty));
		}
	}
	,
	lastMarkerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lastMarkerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.lastMarkerVisibilityProperty));
		}
	}
	,
	lowMarkerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lowMarkerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.lowMarkerVisibilityProperty));
		}
	}
	,
	highMarkerVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.highMarkerVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.highMarkerVisibilityProperty));
		}
	}
	,
	normalRangeVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.normalRangeVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.normalRangeVisibilityProperty));
		}
	}
	,
	displayNormalRangeInFront: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.displayNormalRangeInFrontProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.displayNormalRangeInFrontProperty);
		}
	}
	,
	markerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.markerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.markerSizeProperty);
		}
	}
	,
	firstMarkerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.firstMarkerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.firstMarkerSizeProperty);
		}
	}
	,
	lastMarkerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lastMarkerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.lastMarkerSizeProperty);
		}
	}
	,
	highMarkerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.highMarkerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.highMarkerSizeProperty);
		}
	}
	,
	lowMarkerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lowMarkerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.lowMarkerSizeProperty);
		}
	}
	,
	negativeMarkerSize: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.negativeMarkerSizeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.negativeMarkerSizeProperty);
		}
	}
	,
	lineThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.lineThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.lineThicknessProperty);
		}
	}
	,
	minimum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.minimumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.minimumProperty);
		}
	}
	,
	maximum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.maximumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.maximumProperty);
		}
	}
	,
	itemsSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.itemsSourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.itemsSourceProperty);
		}
	}
	,
	valueMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.valueMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.valueMemberPathProperty);
		}
	}
	,
	labelMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.labelMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.labelMemberPathProperty);
		}
	}
	,
	toolTip: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.toolTipProperty, value);
			this.toolTipVisibility(value == null || value.toString().length == 0 ? $.ig.Visibility.prototype.collapsed : $.ig.Visibility.prototype.visible);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.toolTipProperty);
		}
	}
	,
	toolTipVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.toolTipVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.toolTipVisibilityProperty));
		}
	}
	,
	trendLineType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.trendLineTypeProperty, $.ig.TrendLineType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.trendLineTypeProperty));
		}
	}
	,
	trendLinePeriod: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.trendLinePeriodProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.XamSparkline.prototype.trendLinePeriodProperty));
		}
	}
	,
	trendLineThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.trendLineThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.trendLineThicknessProperty);
		}
	}
	,
	normalRangeMinimum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.normalRangeMinimumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.normalRangeMinimumProperty);
		}
	}
	,
	normalRangeMaximum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.normalRangeMaximumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.normalRangeMaximumProperty);
		}
	}
	,
	displayType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.displayTypeProperty, $.ig.SparklineDisplayType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.displayTypeProperty));
		}
	}
	,
	unknownValuePlotting: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.unknownValuePlottingProperty, $.ig.UnknownValuePlotting.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamSparkline.prototype.unknownValuePlottingProperty));
		}
	}
	,
	verticalAxisLabel: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.verticalAxisLabelProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.verticalAxisLabelProperty);
		}
	}
	,
	horizontalAxisLabel: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.horizontalAxisLabelProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.horizontalAxisLabelProperty);
		}
	}
	,
	formatLabel: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamSparkline.prototype.formatLabelProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamSparkline.prototype.formatLabelProperty);
		}
	}
	,
	__serviceProvider: null,
	serviceProvider: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__serviceProvider;
			this.__serviceProvider = value;
			this.onServiceProviderChanged(oldValue, this.__serviceProvider);
			return value;
		} else {
			return this.__serviceProvider;
		}
	}
	,
	__configurationMessages: null,
	configurationMessages: function (value) {
		if (arguments.length === 1) {
			this.__configurationMessages = value;
			return value;
		} else {
			return this.__configurationMessages;
		}
	}
	,
	__actualMinimum: 0,
	actualMinimum: function (value) {
		if (arguments.length === 1) {
			this.__actualMinimum = value;
			return value;
		} else {
			return this.__actualMinimum;
		}
	}
	,
	__actualMaximum: 0,
	actualMaximum: function (value) {
		if (arguments.length === 1) {
			this.__actualMaximum = value;
			return value;
		} else {
			return this.__actualMaximum;
		}
	}
	,
	_labelColumn: null,
	labelColumn: function (value) {
		if (arguments.length === 1) {
			this._labelColumn = value;
			return value;
		} else {
			return this._labelColumn;
		}
	}
	,
	_horizontalAxis: null,
	horizontalAxis: function (value) {
		if (arguments.length === 1) {
			this._horizontalAxis = value;
			return value;
		} else {
			return this._horizontalAxis;
		}
	}
	,
	_verticalAxis: null,
	verticalAxis: function (value) {
		if (arguments.length === 1) {
			this._verticalAxis = value;
			return value;
		} else {
			return this._verticalAxis;
		}
	}
	,
	onServiceProviderChanged: function (oldValue, newValue) {
		if (oldValue != null) {
			this.configurationMessages().detachFromNext();
		}
		if (newValue != null) {
			this.configurationMessages().connectTo(newValue.getService("ConfigurationMessages"));
		}
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.PropertyChangedMessage_Spark();
			$ret.propertyName(propertyName);
			$ret.oldValue(oldValue);
			$ret.newValue(newValue);
			return $ret;
		}()));
	}
	,
	destroy: function () {
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.PropertyChangedMessage_Spark();
			$ret.propertyName("Container");
			$ret.newValue(null);
			return $ret;
		}()));
	}
	,
	provideContainer: function (container) {
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.PropertyChangedMessage_Spark();
			$ret.propertyName("Container");
			$ret.newValue(container);
			return $ret;
		}()));
	}
	,
	notifyResized: function () {
		this.configurationMessages().sendMessage(new $.ig.ContainerSizeChangedMessage_Spark());
	}
	,
	notifySetItem: function (source_, index, oldItem, newItem) {
		if (source_.dataView && source_.dataSource) { source_ = source_.dataView() };
		if (source_ != this.itemsSource()) {
			return;
		}
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.DataChangedMessage_Spark();
			$ret.change($.ig.NotifyCollectionChangedAction.prototype.replace);
			$ret.oldItem(oldItem);
			$ret.newItem(newItem);
			$ret.index(index);
			return $ret;
		}()));
	}
	,
	notifyClearItems: function (source_) {
		if (source_.dataView && source_.dataSource) { source_ = source_.dataView() };
		if (source_ != this.itemsSource()) {
			return;
		}
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.DataChangedMessage_Spark();
			$ret.change($.ig.NotifyCollectionChangedAction.prototype.reset);
			return $ret;
		}()));
	}
	,
	notifyInsertItem: function (source_, index, newItem) {
		if (source_.dataView && source_.dataSource) { source_ = source_.dataView() };
		if (source_ != this.itemsSource()) {
			return;
		}
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.DataChangedMessage_Spark();
			$ret.change($.ig.NotifyCollectionChangedAction.prototype.add);
			$ret.newItem(newItem);
			$ret.index(index);
			return $ret;
		}()));
	}
	,
	notifyRemoveItem: function (source_, index, oldItem) {
		if (source_.dataView && source_.dataSource) { source_ = source_.dataView() };
		if (source_ != this.itemsSource()) {
			return;
		}
		this.configurationMessages().sendMessage((function () {
			var $ret = new $.ig.DataChangedMessage_Spark();
			$ret.change($.ig.NotifyCollectionChangedAction.prototype.remove);
			$ret.oldItem(oldItem);
			$ret.index(index);
			return $ret;
		}()));
	}
	,
	$type: new $.ig.Type('XamSparkline', $.ig.Control.prototype.$type)
}, true);

$.ig.SparklineDisplayType.prototype.line = 0;
$.ig.SparklineDisplayType.prototype.area = 1;
$.ig.SparklineDisplayType.prototype.column = 2;
$.ig.SparklineDisplayType.prototype.winLoss = 3;

$.ig.SparkLayerType.prototype.sparkLayer = 1;
$.ig.SparkLayerType.prototype.markerLayer = 2;
$.ig.SparkLayerType.prototype.toolTipLayer = 4;
$.ig.SparkLayerType.prototype.rangeLayer = 8;
$.ig.SparkLayerType.prototype.trendLayer = 16;

$.ig.XamSparklineView.prototype._iDEAL_WIDTH = 100;
$.ig.XamSparklineView.prototype._iDEAL_HEIGHT = 50;

$.ig.SparklineController.prototype.fastItemsSourceProperty = $.ig.DependencyProperty.prototype.register("FastItemsSource", $.ig.IFastItemsSource.prototype.$type, $.ig.SparklineController.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	($.ig.util.cast($.ig.SparklineController.prototype.$type, sender)).updateFastItemsSource(e.oldValue(), e.newValue());
}));

$.ig.XamSparkline.prototype.brushPropertyName = "Brush";
$.ig.XamSparkline.prototype.negativeBrushPropertyName = "NegativeBrush";
$.ig.XamSparkline.prototype.markerBrushPropertyName = "MarkerBrush";
$.ig.XamSparkline.prototype.negativeMarkerBrushPropertyName = "NegativeMarkerBrush";
$.ig.XamSparkline.prototype.firstMarkerBrushPropertyName = "FirstMarkerBrush";
$.ig.XamSparkline.prototype.lastMarkerBrushPropertyName = "LastMarkerBrush";
$.ig.XamSparkline.prototype.highMarkerBrushPropertyName = "HighMarkerBrush";
$.ig.XamSparkline.prototype.lowMarkerBrushPropertyName = "LowMarkerBrush";
$.ig.XamSparkline.prototype.trendLineBrushPropertyName = "TrendLineBrush";
$.ig.XamSparkline.prototype.horizontalAxisBrushPropertyName = "HorizontalAxisBrush";
$.ig.XamSparkline.prototype.verticalAxisBrushPropertyName = "VerticalAxisBrush";
$.ig.XamSparkline.prototype.normalRangeFillPropertyName = "NormalRangeFill";
$.ig.XamSparkline.prototype.horizontalAxisVisibilityPropertyName = "HorizontalAxisVisibility";
$.ig.XamSparkline.prototype.verticalAxisVisibilityPropertyName = "VerticalAxisVisibility";
$.ig.XamSparkline.prototype.markerVisibilityPropertyName = "MarkerVisibility";
$.ig.XamSparkline.prototype.negativeMarkerVisibilityPropertyName = "NegativeMarkerVisibility";
$.ig.XamSparkline.prototype.firstMarkerVisibilityPropertyName = "FirstMarkerVisibility";
$.ig.XamSparkline.prototype.lastMarkerVisibilityPropertyName = "LastMarkerVisibility";
$.ig.XamSparkline.prototype.lowMarkerVisibilityPropertyName = "LowMarkerVisibility";
$.ig.XamSparkline.prototype.highMarkerVisibilityPropertyName = "HighMarkerVisibility";
$.ig.XamSparkline.prototype.normalRangeVisibilityPropertyName = "NormalRangeVisibility";
$.ig.XamSparkline.prototype.displayNormalRangeInFrontPropertyName = "DisplayNormalRangeInFront";
$.ig.XamSparkline.prototype.markerSizePropertyName = "MarkerSize";
$.ig.XamSparkline.prototype.markerSizeDef = -1;
$.ig.XamSparkline.prototype.firstMarkerSizePropertyName = "FirstMarkerSize";
$.ig.XamSparkline.prototype.lastMarkerSizePropertyName = "LastMarkerSize";
$.ig.XamSparkline.prototype.highMarkerSizePropertyName = "HighMarkerSize";
$.ig.XamSparkline.prototype.lowMarkerSizePropertyName = "LowMarkerSize";
$.ig.XamSparkline.prototype.negativeMarkerSizePropertyName = "NegativeMarkerSize";
$.ig.XamSparkline.prototype.lineThicknessPropertyName = "LineThickness";
$.ig.XamSparkline.prototype._lineThicknessDef = -1;
$.ig.XamSparkline.prototype.minimumPropertyName = "Minimum";
$.ig.XamSparkline.prototype.maximumPropertyName = "Maximum";
$.ig.XamSparkline.prototype.itemsSourcePropertyName = "ItemsSource";
$.ig.XamSparkline.prototype.valueMemberPathPropertyName = "ValueMemberPath";
$.ig.XamSparkline.prototype.labelMemberPathPropertyName = "LabelMemberPath";
$.ig.XamSparkline.prototype.toolTipPropertyName = "ToolTip";
$.ig.XamSparkline.prototype.toolTipVisibilityPropertyName = "ToolTipVisibility";
$.ig.XamSparkline.prototype.trendLineTypePropertyName = "TrendLineType";
$.ig.XamSparkline.prototype.trendLinePeriodPropertyName = "TrendLinePeriod";
$.ig.XamSparkline.prototype.trendLineThicknessPropertyName = "TrendLineThickness";
$.ig.XamSparkline.prototype._trendLineThicknessDef = -1;
$.ig.XamSparkline.prototype.normalRangeMinimumPropertyName = "NormalRangeMinimum";
$.ig.XamSparkline.prototype.normalRangeMaximumPropertyName = "NormalRangeMaximum";
$.ig.XamSparkline.prototype.displayTypePropertyName = "DisplayType";
$.ig.XamSparkline.prototype.unknownValuePlottingPropertyName = "UnknownValuePlotting";
$.ig.XamSparkline.prototype.verticalAxisLabelPropertyName = "VerticalAxisLabel";
$.ig.XamSparkline.prototype.horizontalAxisLabelPropertyName = "HorizontalAxisLabel";
$.ig.XamSparkline.prototype.formatLabelPropertyName = "FormatLabel";
$.ig.XamSparkline.prototype.brushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.brushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.brushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.negativeBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.negativeBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.negativeBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.markerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.markerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.markerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.negativeMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.negativeMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.negativeMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.firstMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.firstMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.firstMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lastMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lastMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lastMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.highMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.highMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.highMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lowMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lowMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lowMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.trendLineBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.trendLineBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.trendLineBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.horizontalAxisBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.horizontalAxisBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.horizontalAxisBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.verticalAxisBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.verticalAxisBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.verticalAxisBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.normalRangeFillProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.normalRangeFillPropertyName, $.ig.Brush.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.normalRangeFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.horizontalAxisVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.horizontalAxisVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.horizontalAxisVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.verticalAxisVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.verticalAxisVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.verticalAxisVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.markerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.markerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.markerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.negativeMarkerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.negativeMarkerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.negativeMarkerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.firstMarkerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.firstMarkerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.firstMarkerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lastMarkerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lastMarkerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lastMarkerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lowMarkerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lowMarkerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lowMarkerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.highMarkerVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.highMarkerVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.highMarkerVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.normalRangeVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.normalRangeVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.normalRangeVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.displayNormalRangeInFrontProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.displayNormalRangeInFrontPropertyName, $.ig.Boolean.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.displayNormalRangeInFrontPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.markerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.markerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.markerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.firstMarkerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.firstMarkerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.firstMarkerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lastMarkerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lastMarkerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lastMarkerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.highMarkerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.highMarkerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.highMarkerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lowMarkerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lowMarkerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lowMarkerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.negativeMarkerSizeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.negativeMarkerSizePropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype.markerSizeDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.negativeMarkerSizePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.lineThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.lineThicknessPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype._lineThicknessDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.lineThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.minimumProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.minimumPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.minimumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.maximumProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.maximumPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.maximumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.itemsSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.itemsSourcePropertyName, $.ig.IEnumerable.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.itemsSourcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.valueMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.valueMemberPathPropertyName, String, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.valueMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.labelMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.labelMemberPathPropertyName, String, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.labelMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.toolTipProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.toolTipPropertyName, $.ig.Object.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) { ($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.toolTipPropertyName, e.oldValue(), e.newValue()); }));
$.ig.XamSparkline.prototype.toolTipVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.toolTipVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.collapsed), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.toolTipVisibilityPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.trendLineTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.trendLineTypePropertyName, $.ig.TrendLineType.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.TrendLineType.prototype.getBox($.ig.TrendLineType.prototype.none), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.trendLineTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.trendLinePeriodProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.trendLinePeriodPropertyName, $.ig.Number.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, 7, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.trendLinePeriodPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.trendLineThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.trendLineThicknessPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamSparkline.prototype._trendLineThicknessDef, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.trendLineThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.normalRangeMinimumProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.normalRangeMinimumPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(1, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.normalRangeMinimumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.normalRangeMaximumProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.normalRangeMaximumPropertyName, Number, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(1, function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.normalRangeMaximumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.displayTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.displayTypePropertyName, $.ig.SparklineDisplayType.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.SparklineDisplayType.prototype.getBox($.ig.SparklineDisplayType.prototype.line), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.displayTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.unknownValuePlottingProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.unknownValuePlottingPropertyName, $.ig.UnknownValuePlotting.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.UnknownValuePlotting.prototype.getBox($.ig.UnknownValuePlotting.prototype.dontPlot), function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.unknownValuePlottingPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.verticalAxisLabelProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.verticalAxisLabelPropertyName, $.ig.Object.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, "{0:n}", function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.verticalAxisLabelPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.horizontalAxisLabelProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.horizontalAxisLabelPropertyName, $.ig.Object.prototype.$type, $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(2, "{0}", function (o, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, o)).onPropertyChanged($.ig.XamSparkline.prototype.horizontalAxisLabelPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamSparkline.prototype.formatLabelProperty = $.ig.DependencyProperty.prototype.register($.ig.XamSparkline.prototype.formatLabelPropertyName, $.ig.Func$2.prototype.$type.specialize($.ig.Object.prototype.$type, String), $.ig.XamSparkline.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	($.ig.util.cast($.ig.XamSparkline.prototype.$type, sender)).onPropertyChanged($.ig.XamSparkline.prototype.formatLabelPropertyName, e.oldValue(), e.newValue());
}));

} (jQuery));


