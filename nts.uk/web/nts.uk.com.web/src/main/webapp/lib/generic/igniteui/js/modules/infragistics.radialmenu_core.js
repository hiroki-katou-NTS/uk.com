/*!@license
* Infragistics.Web.ClientUI infragistics.radialmenu_core.js 16.1.20161.2145
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
"ArgumentOutOfRangeException:bf", 
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
"Dictionary_EnumerableCollection$3:ch", 
"GenericEnumerable$1:cj", 
"GenericEnumerator$1:ck", 
"INotifyCollectionChanged:cm", 
"NotifyCollectionChangedEventHandler:cn", 
"NotifyCollectionChangedEventArgs:co", 
"EventArgs:cp", 
"NotifyCollectionChangedAction:cq", 
"ObservableCollection$1:ct", 
"INotifyPropertyChanged:cu", 
"PropertyChangedEventHandler:cv", 
"PropertyChangedEventArgs:cw", 
"Delegate:cx", 
"Interlocked:cy", 
"Stack$1:c2", 
"ReverseArrayEnumerator$1:c3", 
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
"Environment:d5", 
"Debug:ee", 
"StringBuilder:eu", 
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
"UIElementCollection:gf", 
"FrameworkElement:gg", 
"Visibility:gh", 
"Style:gi", 
"Panel:gv", 
"Image:gx", 
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

$.ig.util.defType('INotifyCollectionChanged', 'Object', {
	$type: new $.ig.Type('INotifyCollectionChanged', null)
}, true);

$.ig.util.defType('ObservableCollection$1', 'List$1', {
	$t: null,
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
		$.ig.List$1.prototype.init.call(this, this.$t, 0);
	},
	init1: function ($t, initNumber, source) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.List$1.prototype.init1.call(this, this.$t, 1, source);
	},
	init2: function ($t, initNumber, capacity) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.List$1.prototype.init2.call(this, this.$t, 2, capacity);
	},
	setItem: function (index, newItem) {
		var oldItem = this.__inner[index];
		$.ig.List$1.prototype.setItem.call(this, index, newItem);
		if (this.propertyChanged != null) {
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Item[]"));
		}
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(2, $.ig.NotifyCollectionChangedAction.prototype.replace, $.ig.util.getBoxIfEnum(this.$t, newItem), $.ig.util.getBoxIfEnum(this.$t, oldItem), index);
			this.onCollectionChanged(args);
		}
	}
	,
	clearItems: function () {
		$.ig.List$1.prototype.clearItems.call(this);
		if (this.propertyChanged != null) {
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Count"));
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Item[]"));
		}
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(0, $.ig.NotifyCollectionChangedAction.prototype.reset);
			this.onCollectionChanged(args);
		}
	}
	,
	insertItem: function (index, newItem) {
		$.ig.List$1.prototype.insertItem.call(this, index, newItem);
		if (this.propertyChanged != null) {
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Count"));
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Item[]"));
		}
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.add, $.ig.util.getBoxIfEnum(this.$t, newItem), index);
			this.onCollectionChanged(args);
		}
	}
	,
	addItem: function (newItem) {
		$.ig.List$1.prototype.addItem.call(this, newItem);
		if (this.propertyChanged != null) {
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Count"));
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Item[]"));
		}
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.add, $.ig.util.getBoxIfEnum(this.$t, newItem), this.count() - 1);
			this.onCollectionChanged(args);
		}
	}
	,
	removeItem: function (index) {
		var oldItem = this.__inner[index];
		$.ig.List$1.prototype.removeItem.call(this, index);
		if (this.propertyChanged != null) {
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Count"));
			this.onPropertyChanged(new $.ig.PropertyChangedEventArgs("Item[]"));
		}
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.remove, $.ig.util.getBoxIfEnum(this.$t, oldItem), index);
			this.onCollectionChanged(args);
		}
	}
	,
	collectionChanged: null,
	propertyChanged: null,
	onPropertyChanged: function (args) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, args);
		}
	}
	,
	onCollectionChanged: function (args) {
		if (this.collectionChanged != null) {
			this.collectionChanged(this, args);
		}
	}
	,
	$type: new $.ig.Type('ObservableCollection$1', $.ig.List$1.prototype.$type.specialize(0), [$.ig.INotifyCollectionChanged.prototype.$type, $.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('Stack$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		this.__inner = new $.ig.Array();
		$.ig.Object.prototype.init.call(this);
	},
	__inner: null,
	push: function (item) {
		this.__inner.add($.ig.util.getBoxIfEnum(this.$t, item));
	}
	,
	peek: function () {
		if (this.__inner.length < 1) {
			return $.ig.util.getDefaultValue(this.$t);
		}
		return $.ig.util.castObjTo$t(this.$t, this.__inner[this.__inner.length - 1]);
	}
	,
	pop: function () {
		var ret = this.__inner[this.__inner.length - 1];
		this.__inner.removeAt(this.__inner.length - 1);
		return $.ig.util.castObjTo$t(this.$t, ret);
	}
	,
	count: function () {
		return this.__inner.length;
	}
	,
	clear: function () {
		this.__inner.clear();
	}
	,
	contains: function (item) {
		return this.__inner.contains($.ig.util.getBoxIfEnum(this.$t, item));
	}
	,
	getEnumerator: function () {
		return new $.ig.ReverseArrayEnumerator$1(this.$t, this.__inner);
	}
	,
	getEnumerator: function () {
		return new $.ig.ReverseArrayEnumerator$1(this.$t, this.__inner);
	}
	,
	$type: new $.ig.Type('Stack$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('ReverseArrayEnumerator$1', 'Object', {
	$t: null,
	__index: 0,
	__array: null,
	init: function ($t, array) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__array = array;
		this.__index = array.length;
	},
	current: function () {
		return $.ig.util.castObjTo$t(this.$t, this.__array[this.__index]);
	}
	,
	current: function () {
		return this.__array[this.__index];
	}
	,
	moveNext: function () {
		this.__index--;
		return this.__index >= 0;
	}
	,
	reset: function () {
		this.__index = this.__array.length;
	}
	,
	dispose: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	$type: new $.ig.Type('ReverseArrayEnumerator$1', $.ig.Object.prototype.$type, [$.ig.IEnumerator$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('ArgumentOutOfRangeException', 'ArgumentException', {
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
				case 4:
					this.init4.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.ArgumentException.prototype.init.call(this, 0);
	},
	init1: function (initNumber, argumentName) {
		$.ig.ArgumentException.prototype.init1.call(this, 1, argumentName + " is out of range.");
	},
	init2: function (initNumber, paramName, errorMessage) {
		$.ig.ArgumentException.prototype.init1.call(this, 1, errorMessage);
	},
	init3: function (initNumber, paramName, value, message) {
		$.ig.ArgumentOutOfRangeException.prototype.init2.call(this, 2, message, paramName);
	},
	init4: function (initNumber, message, innerException) {
		$.ig.ArgumentException.prototype.init3.call(this, 3, message, innerException);
	},
	$type: new $.ig.Type('ArgumentOutOfRangeException', $.ig.ArgumentException.prototype.$type)
}, true);

$.ig.util.defType('Environment', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	newLine: function () {
		return "\n";
	}
	,
	$type: new $.ig.Type('Environment', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Debug', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	assert1: function (condition, text) {
	}
	,
	writeLine: function (line) {
	}
	,
	assert: function (value) {
	}
	,
	fail: function (message) {
	}
	,
	$type: new $.ig.Type('Debug', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('StringBuilder', 'Object', {
	_internal: null,
	internal: function (value) {
		if (arguments.length === 1) {
			this._internal = value;
			return value;
		} else {
			return this._internal;
		}
	}
	,
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
		$.ig.Object.prototype.init.call(this);
		this.internal("");
	},
	init1: function (initNumber, capacity) {
		$.ig.StringBuilder.prototype.init.call(this, 0);
	},
	init2: function (initNumber, value) {
		$.ig.Object.prototype.init.call(this);
		this.internal(value);
	},
	append4: function (obj) {
		if (obj != null) {
			this.append5(obj.toString());
		}
		return this;
	}
	,
	append5: function (str_) {
		if (str_ != null)
        {
            this._internal = this._internal.concat(str_);
        };
		return this;
	}
	,
	append7: function (builder) {
		var str_ = builder.toString();
		this._internal = this._internal.concat(str_);
		return this;
	}
	,
	append1: function (chr_) {
		this._internal = this._internal.concat(chr_);
		return this;
	}
	,
	append2: function (chr_, count_) {
		this._internal = this._internal.concat(chr_.repeat(count_));
		return this;
	}
	,
	append3: function (value_) {
		this._internal = this._internal.concat(value_);
		return this;
	}
	,
	append6: function (value_, startIndex_, count_) {
		this._internal = this._internal.concat(value_.substr(startIndex_, count_));
		return this;
	}
	,
	append: function (value_, startIndex_, charCount_) {
		this._internal = this._internal.concat(value_.slice(startIndex_, startIndex_ + charCount_).join(''));
		return this;
	}
	,
	appendLine: function () {
		return this.appendLine1("");
	}
	,
	appendLine1: function (str_) {
		if (str_ != null)
        {
            this._internal = this._internal.concat(str_);
        }
        this._internal = this._internal.concat($.ig.Environment.prototype.newLine());;
		return this;
	}
	,
	clear: function () {
		this.internal("");
		return this;
	}
	,
	insert: function (index_, chr_) {
		if (index_ == this.length()) {
			this.append1(chr_);
		} else {
			this._internal = this._internal.substring(0, index_).concat(chr_).concat(this._internal.substring(index_, this._internal.length));
		}
		return this;
	}
	,
	insert1: function (index_, str_) {
		if (index_ == this.length()) {
			this.append5(str_);
		} else {
			this._internal = this._internal.substring(0, index_).concat(str_).concat(this._internal.substring(index_, this._internal.length));
		}
		return this;
	}
	,
	remove: function (startIndex_, length_) {
		this._internal = this._internal.substring(0, startIndex_).concat(this._internal.substring(startIndex_ + length_, this._internal.length));
		return this;
	}
	,
	toString: function () {
		return this.internal();
	}
	,
	toString1: function (startIndex, length) {
		return this.internal().substr(startIndex, length);
	}
	,
	length: function (value) {
		if (arguments.length === 1) {
			if (value <= this.length()) {
				this._internal = this._internal.substring(0, value);
			} else {
				throw new $.ig.NotImplementedException(0);
			}
			return value;
		} else {
			return this.internal().length;
		}
	}
	,
	item: function (index_, value) {
		if (arguments.length === 2) {
			this._internal = this._internal.substring(0, index_).concat(value).concat(this._internal.substring(index_ + 1, this._internal.length));
			return value;
		} else {
			return this.internal().charAt(index_);
		}
	}
	,
	appendFormat2: function (format, arg0) {
		return this.append5($.ig.util.stringFormat(format, arg0));
	}
	,
	appendFormat1: function (format, args) {
		return this.append5($.ig.util.stringFormat1(format, args));
	}
	,
	appendFormat: function (provider, format, args) {
		return this.append5($.ig.util.stringFormat2(provider, format, args));
	}
	,
	appendFormat3: function (format, arg0, arg1) {
		return this.append5($.ig.util.stringFormat(format, arg0, arg1));
	}
	,
	appendFormat4: function (format, arg0, arg1, arg2) {
		return this.append5($.ig.util.stringFormat(format, arg0, arg1, arg2));
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
	$type: new $.ig.Type('StringBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('UIElementCollection', 'ObservableCollection$1', {
	__owner: null,
	init: function (owner) {
		$.ig.ObservableCollection$1.prototype.init.call(this, $.ig.UIElement.prototype.$type, 0);
		this.__owner = owner;
	},
	onCollectionChanged: function (args) {
		$.ig.ObservableCollection$1.prototype.onCollectionChanged.call(this, args);
		if (args.oldItems() != null) {
			var en = args.oldItems().getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				(item).parent(null);
			}
		}
		if (args.newItems() != null) {
			var en1 = args.newItems().getEnumerator();
			while (en1.moveNext()) {
				var item1 = en1.current();
				(item1).parent(this.__owner);
			}
		}
	}
	,
	clearItems: function () {
		var en = this.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			(item).parent(null);
		}
		$.ig.ObservableCollection$1.prototype.clearItems.call(this);
	}
	,
	$type: new $.ig.Type('UIElementCollection', $.ig.ObservableCollection$1.prototype.$type.specialize($.ig.UIElement.prototype.$type))
}, true);

$.ig.util.defType('Panel', 'FrameworkElement', {
	init: function () {
		$.ig.FrameworkElement.prototype.init.call(this);
		this.children(new $.ig.UIElementCollection(this));
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
	$type: new $.ig.Type('Panel', $.ig.FrameworkElement.prototype.$type)
}, true);

$.ig.util.defType('Image', 'FrameworkElement', {
	init: function () {
		$.ig.FrameworkElement.prototype.init.call(this);
	},
	_source: null,
	source: function (value) {
		if (arguments.length === 1) {
			this._source = value;
			return value;
		} else {
			return this._source;
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
	$type: new $.ig.Type('Image', $.ig.FrameworkElement.prototype.$type)
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
"EventHandler$1:dy", 
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
"PointCollection:e5", 
"Line:fj", 
"FontInfo:fk", 
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
"RotateTransform:gk", 
"INotifyPropertyChanged:gn", 
"PropertyChangedEventHandler:go", 
"PropertyChangedEventArgs:gp", 
"DivElement:gy", 
"DoubleAnimator:gz", 
"EasingFunctionHandler:g0", 
"ImageElement:g1", 
"MathUtil:g3", 
"RuntimeHelpers:g4", 
"RuntimeFieldHandle:g5", 
"BrushUtil:g6", 
"ColorUtil:g7", 
"Random:g8", 
"InterpolationMode:g9", 
"BrushCollection:ha", 
"ObservableCollection$1:hb", 
"INotifyCollectionChanged:hc", 
"NotifyCollectionChangedEventHandler:hd", 
"CssHelper:he", 
"CssGradientUtil:hf", 
"Stack$1:hj", 
"ReverseArrayEnumerator$1:hk", 
"Image:h6", 
"StackPool$1:ia", 
"Func$1:ib", 
"Debug:ic", 
"StringBuilder:ih", 
"Environment:ii", 
"Panel:jg", 
"UIElementCollection:jh", 
"IVisualData:jt", 
"PrimitiveVisualDataList:ju", 
"PrimitiveVisualData:jv", 
"PrimitiveAppearanceData:jw", 
"BrushAppearanceData:jx", 
"AppearanceHelper:jy", 
"LinearGradientBrushAppearanceData:jz", 
"GradientStopAppearanceData:j0", 
"SolidBrushAppearanceData:j1", 
"GeometryData:j2", 
"GetPointsSettings:j3", 
"EllipseGeometryData:j4", 
"RectangleGeometryData:j5", 
"LineGeometryData:j6", 
"PathGeometryData:j7", 
"PathFigureData:j8", 
"SegmentData:j9", 
"LineSegmentData:ka", 
"PolylineSegmentData:kb", 
"ArcSegmentData:kc", 
"PolyBezierSegmentData:kd", 
"BezierSegmentData:ke", 
"LabelAppearanceData:kf", 
"ShapeTags:kg", 
"PathVisualData:ko", 
"AbstractEnumerable:kp", 
"AbstractEnumerator:kq", 
"GenericEnumerable$1:kr", 
"GenericEnumerator$1:ks"]);


$.ig.util.defType('InterpolationMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "RGB";
			case 1: return "HSV";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('InterpolationMode', $.ig.Enum.prototype.$type)
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

$.ig.util.defType('BrushUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getLightened: function (brush, interpolation) {
		if (brush == null) {
			return brush;
		}
		if (brush._isGradient) {
			var newBrush = (brush).clone();
			for (var i = 0; i < newBrush._gradientStops.length; i++) {
				var currentStop = newBrush._gradientStops[i];
				currentStop.color($.ig.ColorUtil.prototype.getLightened(currentStop.color(), interpolation));
			}
			return newBrush;
		} else {
			var l = $.ig.ColorUtil.prototype.getLightened(brush.color(), interpolation);
			return (function () {
				var $ret = new $.ig.Brush();
				$ret.color(l);
				return $ret;
			}());
		}
	}
	,
	getInterpolation: function (minimum, interpolation, maximum, interpolationMode) {
		var target = new $.ig.Brush();
		if (minimum == null && maximum == null) {
			target.__fill = "transparent";
			return target;
		}
		var minSolid = null, maxSolid = null;
		var minLinear = null, maxLinear = null;
		if (minimum == null) {
			var c = maximum._isGradient ? (function () {
				var $ret = new $.ig.Color();
				$ret.a(0);
				$ret.r(255);
				$ret.g(255);
				$ret.b(255);
				return $ret;
			}()) : (function () {
				var $ret = new $.ig.Color();
				$ret.a(0);
				$ret.r(maximum.color().r());
				$ret.g(maximum.color().g());
				$ret.b(maximum.color().b());
				return $ret;
			}());
			minSolid = (function () {
				var $ret = new $.ig.Brush();
				$ret.color(c);
				return $ret;
			}());
		} else {
			if (minimum._isGradient) {
				minLinear = minimum;
			} else {
				minSolid = minimum;
			}
		}
		if (maximum == null) {
			var c1 = minimum._isGradient ? (function () {
				var $ret = new $.ig.Color();
				$ret.a(0);
				$ret.r(255);
				$ret.g(255);
				$ret.b(255);
				return $ret;
			}()) : (function () {
				var $ret = new $.ig.Color();
				$ret.a(0);
				$ret.r(minimum.color().r());
				$ret.g(minimum.color().g());
				$ret.b(minimum.color().b());
				return $ret;
			}());
			maxSolid = (function () {
				var $ret = new $.ig.Brush();
				$ret.color(c1);
				return $ret;
			}());
		} else {
			if (maximum._isGradient) {
				maxLinear = maximum;
			} else {
				maxSolid = maximum;
			}
		}
		if (minSolid != null && maxSolid != null) {
			return $.ig.BrushUtil.prototype.solidSolid(minSolid, interpolation, maxSolid, interpolationMode);
		}
		if (minSolid != null && maxLinear != null) {
			return $.ig.BrushUtil.prototype.solidLinear(minSolid, interpolation, maxLinear, interpolationMode);
		}
		if (minLinear != null && maxSolid != null) {
			return $.ig.BrushUtil.prototype.solidLinear(maxSolid, 1 - interpolation, minLinear, interpolationMode);
		}
		if (minLinear != null && maxLinear != null) {
			return $.ig.BrushUtil.prototype.linearLinear(minLinear, interpolation, maxLinear, interpolationMode);
		}
		return target;
	}
	,
	solidSolid: function (min, p, max, interpolationMode) {
		var b = new $.ig.Brush();
		b.color($.ig.ColorUtil.prototype.getInterpolation(min.color(), p, max.color(), interpolationMode));
		return b;
	}
	,
	solidLinear: function (min, p, max, interpolationMode) {
		var b = new $.ig.LinearGradientBrush();
		b._gradientStops = $.ig.BrushUtil.prototype.gradientStops1(min.color(), p, max._gradientStops, interpolationMode);
		if (max._useCustomDirection) {
			b._useCustomDirection = true;
			b._startX = max._startX;
			b._startY = max._startY;
			b._endX = max._endX;
			b._endY = max._endY;
		}
		return b;
	}
	,
	linearLinear: function (min, p, max, interpolationMode) {
		var b = new $.ig.LinearGradientBrush();
		b._gradientStops = $.ig.BrushUtil.prototype.gradientStops(min._gradientStops, p, max._gradientStops, interpolationMode);
		if (min._useCustomDirection || max._useCustomDirection) {
			b._useCustomDirection = true;
			b._startX = min._startX + p * (max._startX - min._startX);
			b._startY = min._startY + p * (max._startY - min._startY);
			b._endX = (1 - p) * min._endX + p * max._endX;
			b._endY = (1 - p) * min._endY + p * max._endY;
		}
		return b;
	}
	,
	gradientStops1: function (min, p, max, interpolationMode) {
		var gradientStopCollection = new Array(max.length);
		for (var i = 0; i < max.length; ++i) {
			gradientStopCollection[i] = (function () {
				var $ret = new $.ig.GradientStop();
				$ret._offset = max[i]._offset;
				$ret.color($.ig.ColorUtil.prototype.getInterpolation(min, p, max[i].color(), interpolationMode));
				return $ret;
			}());
		}
		return gradientStopCollection;
	}
	,
	gradientStops: function (min, p, max, interpolationMode) {
		var minimumCount = Math.min(min.length, max.length);
		var maxCount = Math.max(min.length, max.length);
		var gradientStopCollection = new Array(maxCount);
		var i = 0;
		for (; i < minimumCount; ++i) {
			gradientStopCollection[i] = (function () {
				var $ret = new $.ig.GradientStop();
				$ret._offset = (1 - p) * min[i]._offset + p * max[i]._offset;
				$ret.color($.ig.ColorUtil.prototype.getInterpolation(min[i].color(), p, max[i].color(), interpolationMode));
				return $ret;
			}());
		}
		for (; i < min.length; ++i) {
			gradientStopCollection[i] = (function () {
				var $ret = new $.ig.GradientStop();
				$ret._offset = (1 - p) * min[i]._offset + p * max[max.length - 1]._offset;
				$ret.color($.ig.ColorUtil.prototype.getInterpolation(min[i].color(), p, max[max.length - 1].color(), interpolationMode));
				return $ret;
			}());
		}
		for (; i < max.length; ++i) {
			gradientStopCollection[i] = (function () {
				var $ret = new $.ig.GradientStop();
				$ret._offset = (1 - p) * min[min.length - 1]._offset + p * max[i]._offset;
				$ret.color($.ig.ColorUtil.prototype.getInterpolation(min[min.length - 1].color(), p, max[i].color(), interpolationMode));
				return $ret;
			}());
		}
		return gradientStopCollection;
	}
	,
	isFullyTransparent: function (brush) {
		var linearBrush = $.ig.util.cast($.ig.LinearGradientBrush.prototype.$type, brush);
		if (linearBrush != null) {
			return $.ig.BrushUtil.prototype.isFullyTransparent2(linearBrush);
		}
		if (brush.color().a() == 0) {
			return true;
		}
		return false;
	}
	,
	isFullyTransparent2: function (brush) {
		var $t = brush._gradientStops;
		for (var i = 0; i < $t.length; i++) {
			var stop = $t[i];
			if (!$.ig.BrushUtil.prototype.isFullyTransparent1(stop)) {
				return false;
			}
		}
		return true;
	}
	,
	isFullyTransparent1: function (gradientStop) {
		if (gradientStop.color().a() == 0) {
			return true;
		}
		return false;
	}
	,
	getCssBrushColors: function (className, obj) {
		var brushes = new Array(2);
		obj.addClass(className);
		var fill = new $.ig.Brush();
		fill.__fill = obj.css("background-color");
		var outline = new $.ig.Brush();
		outline.__fill = obj.css("border-top-color");
		obj.removeClass(className);
		brushes[0] = fill;
		brushes[1] = outline;
		return brushes;
	}
	,
	getBrushCollection: function (palleteName_, container_, brushes, outlines, defaultColors) {
		brushes = new $.ig.BrushCollection();
		outlines = new $.ig.BrushCollection();
		var tempBrush;
		var names = new $.ig.List$1(String, 0);
		names.add("background-color");
		names.add("border-top-color");
		var discovery = $.ig.CssHelper.prototype.getDisoveryElement();
		var isNotInDom = false;
		if (container_ != null) {
			container_.append(discovery);
			isNotInDom = !jQuery.contains(document.documentElement, container_[0]);
			if (isNotInDom) {
				$("body").append(container_);
			}
		} else {
			$("body").append(discovery);
		}
		var palette = $.ig.CssHelper.prototype.getValuesForClassCollection(discovery, "ui-" + palleteName_ + "-palette-", names);
		var numPaletteColors = palette.count();
		if (numPaletteColors == 0) {
			if (defaultColors == null) {
				defaultColors = [ "#B1BFC9", "#50a8be", "#798995", "#fc6754", "#4F606C", "#fec33c", "#374650", "#3c6399", "#162C3B", "#91af49" ];
			}
			for (var i = 0; i < defaultColors.length - 1; i += 2) {
				tempBrush = new $.ig.Brush();
				tempBrush.__fill = defaultColors[i];
				outlines.add(tempBrush);
				tempBrush = new $.ig.Brush();
				tempBrush.__fill = defaultColors[i + 1];
				brushes.add(tempBrush);
			}
		}
		for (var i1 = 0; i1 < numPaletteColors; i1++) {
			var fillBrush = new $.ig.Brush();
			fillBrush.__fill = palette.__inner[i1].__inner[0];
			var outlineBrush = new $.ig.Brush();
			outlineBrush.__fill = palette.__inner[i1].__inner[1];
			brushes.add(fillBrush);
			outlines.add(outlineBrush);
		}
		discovery.remove();
		if (isNotInDom) {
			container_.remove();
		}
		return {
			p2: brushes,
			p3: outlines
		};
	}
	,
	getGradientBrushCollection: function (fillGradientPaletteName, outlineGradientPaletteName, paletteName, container_, brushes, outlines, defaultColors) {
		brushes = new $.ig.BrushCollection();
		outlines = new $.ig.BrushCollection();
		if (defaultColors == null) {
			defaultColors = [ "#B1BFC9", "#50a8be", "#798995", "#fc6754", "#4F606C", "#fec33c", "#374650", "#3c6399", "#162C3B", "#91af49" ];
		}
		var discovery = $.ig.CssHelper.prototype.getDisoveryElement();
		var isNotInDom = false;
		if (container_ != null) {
			container_.append(discovery);
			isNotInDom = !jQuery.contains(document.documentElement, container_[0]);
			if (isNotInDom) {
				$("body").append(container_);
			}
		} else {
			$("body").append(discovery);
		}
		var names = new $.ig.List$1(String, 0);
		names.add("background-image");
		var fillsPalette = $.ig.CssHelper.prototype.getValuesForClassCollection(discovery, fillGradientPaletteName, names);
		var numFillsPaletteColors = fillsPalette.count();
		var outlinesPalette = $.ig.CssHelper.prototype.getValuesForClassCollection(discovery, outlineGradientPaletteName, names);
		var numOutlinesPaletteColors = outlinesPalette.count();
		for (var i = 0; i < numFillsPaletteColors; i++) {
			brushes.add($.ig.CssGradientUtil.prototype.brushFromGradientString(fillsPalette.__inner[i].__inner[0]));
		}
		for (var i1 = 0; i1 < numOutlinesPaletteColors; i1++) {
			outlines.add($.ig.CssGradientUtil.prototype.brushFromGradientString(outlinesPalette.__inner[i1].__inner[0]));
		}
		names.clear();
		var fillIndex = 0;
		var outlineIndex = 0;
		var numPaletteColors = Math.min(numFillsPaletteColors, numOutlinesPaletteColors);
		var palette = null;
		if (numFillsPaletteColors == 0) {
			names.add("background-color");
		}
		if (numOutlinesPaletteColors == 0) {
			names.add("border-top-color");
			outlineIndex = numFillsPaletteColors == 0 ? 1 : 0;
		}
		if (names.count() > 0) {
			palette = $.ig.CssHelper.prototype.getValuesForClassCollection(discovery, paletteName, names);
			numPaletteColors = palette.count();
		}
		if (numFillsPaletteColors == 0) {
			if (numPaletteColors > 0) {
				for (var i2 = 0; i2 < numPaletteColors; i2++) {
					var fillBrush = new $.ig.Brush();
					fillBrush.__fill = palette.__inner[i2].__inner[fillIndex];
					brushes.add(fillBrush);
				}
			} else {
				for (var i3 = 0; i3 < defaultColors.length - 1; i3 += 2) {
					var fillBrush1 = new $.ig.Brush();
					fillBrush1 = new $.ig.Brush();
					fillBrush1.__fill = defaultColors[i3 + 1];
					brushes.add(fillBrush1);
				}
			}
		}
		if (numOutlinesPaletteColors == 0) {
			if (numPaletteColors > 0) {
				for (var i4 = 0; i4 < numPaletteColors; i4++) {
					var outlineBrush = new $.ig.Brush();
					outlineBrush.__fill = palette.__inner[i4].__inner[outlineIndex];
					outlines.add(outlineBrush);
				}
			} else {
				for (var i5 = 0; i5 < defaultColors.length - 1; i5 += 2) {
					var outlineBrush1 = new $.ig.Brush();
					outlineBrush1.__fill = defaultColors[i5];
					outlines.add(outlineBrush1);
				}
			}
		}
		discovery.remove();
		if (isNotInDom) {
			container_.remove();
		}
		return {
			p4: brushes,
			p5: outlines
		};
	}
	,
	getGradientBrush: function (gradientClassName, className, cssProp, container_, fallbackColor) {
		var b = null;
		var discovery = $.ig.CssHelper.prototype.getDisoveryElement();
		var isNotInDom = false;
		if (container_ != null) {
			container_.append(discovery);
			isNotInDom = !jQuery.contains(document.documentElement, container_[0]);
			if (isNotInDom) {
				$("body").append(container_);
			}
		} else {
			$("body").append(discovery);
		}
		var gradientString = $.ig.CssHelper.prototype.getPropertyValue(discovery, gradientClassName, "background-image");
		if (gradientString != null) {
			b = $.ig.CssGradientUtil.prototype.brushFromGradientString(gradientString);
		}
		if (b == null) {
			b = new $.ig.Brush();
			var fillColor = $.ig.CssHelper.prototype.getPropertyValue(discovery, className, cssProp);
			b.__fill = fillColor != null ? fillColor : fallbackColor;
		}
		discovery.remove();
		if (isNotInDom) {
			container_.remove();
		}
		return b;
	}
	,
	$type: new $.ig.Type('BrushUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ColorUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	randomColor: function (alpha) {
		return $.ig.Color.prototype.fromArgb(alpha, $.ig.ColorUtil.prototype._r.next2(0, 255), $.ig.ColorUtil.prototype._r.next2(0, 255), $.ig.ColorUtil.prototype._r.next2(0, 255));
	}
	,
	randomHue: function (color) {
		var ahsv = $.ig.ColorUtil.prototype.getAHSV(color);
		return $.ig.ColorUtil.prototype.fromAHSV(ahsv[0], $.ig.ColorUtil.prototype._r.next2(0, 359), ahsv[2], ahsv[3]);
	}
	,
	getInterpolation: function (minimum, interpolation_, maximum_, interpolationMode) {
		var min_ = minimum;
		switch (interpolationMode) {
			case $.ig.InterpolationMode.prototype.hSV:
				{
					var b = $.ig.ColorUtil.prototype.getAHSV(minimum);
					var e = $.ig.ColorUtil.prototype.getAHSV(maximum_);
					var b1 = b[1] >= 0 ? b[1] : e[1];
					var e1 = e[1] >= 0 ? e[1] : b[1];
					if (b1 >= 0 && e1 >= 0 && Math.abs(e1 - b1) > 180) {
						if (e1 > b1) {
							b1 += 360;
						} else {
							e1 += 360;
						}
					}
					interpolation_ = Math.max(0, Math.min(1, interpolation_));
					return $.ig.ColorUtil.prototype.fromAHSV(b[0] + interpolation_ * (e[0] - b[0]), b1 + interpolation_ * (e1 - b1), b[2] + interpolation_ * (e[2] - b[2]), b[3] + interpolation_ * (e[3] - b[3]));
				}
			case $.ig.InterpolationMode.prototype.rGB: return $.ig.Color.prototype.fromArgb(min_.__a + interpolation_ * (maximum_.__a - min_.__a), min_.__r + interpolation_ * (maximum_.__r - min_.__r), min_.__g + interpolation_ * (maximum_.__g - min_.__g), min_.__b + interpolation_ * (maximum_.__b - min_.__b));
		}
		return minimum;
	}
	,
	getAHSVInterpolation: function (minimum, interpolation, maximum) {
		var b1 = minimum[1] >= 0 ? minimum[1] : maximum[1];
		var e1 = maximum[1] >= 0 ? maximum[1] : minimum[1];
		if (b1 >= 0 && e1 >= 0 && Math.abs(e1 - b1) > 180) {
			if (e1 > b1) {
				b1 += 360;
			} else {
				e1 += 360;
			}
		}
		interpolation = Math.max(0, Math.min(1, interpolation));
		return $.ig.ColorUtil.prototype.fromAHSV(minimum[0] + interpolation * (maximum[0] - minimum[0]), b1 + interpolation * (e1 - b1), minimum[2] + interpolation * (maximum[2] - minimum[2]), minimum[3] + interpolation * (maximum[3] - minimum[3]));
	}
	,
	getLightened: function (color, interpolation) {
		var ahsl = $.ig.ColorUtil.prototype.getAHSL(color);
		if (interpolation < 0) {
			return $.ig.ColorUtil.prototype.fromAHSL(ahsl[0], ahsl[1], ahsl[2], ahsl[3] * (1 - $.ig.MathUtil.prototype.clamp(-interpolation, 0, 1)));
		} else {
			return $.ig.ColorUtil.prototype.fromAHSL(ahsl[0], ahsl[1], ahsl[2], ahsl[3] + $.ig.MathUtil.prototype.clamp(interpolation, 0, 1) * (1 - ahsl[3]));
		}
	}
	,
	getAHSL: function (color) {
		var ahsl = new Array(4);
		var r = color.r() / 255;
		var g = color.g() / 255;
		var b = color.b() / 255;
		var min = Math.min(Math.min(r, g), b);
		var max = Math.max(Math.max(r, g), b);
		var delta = max - min;
		ahsl[0] = color.a() / 255;
		ahsl[3] = (max + min) / 2;
		if (delta == 0) {
			ahsl[1] = -1;
			ahsl[2] = 0;
		} else {
			ahsl[1] = $.ig.ColorUtil.prototype.h(max, delta, r, g, b);
			ahsl[2] = ahsl[3] < 0.5 ? delta / (max + min) : delta / (2 - max - min);
		}
		return ahsl;
	}
	,
	getAHSV: function (color) {
		var a = color.a() / 255;
		var r = color.r() / 255;
		var g = color.g() / 255;
		var b = color.b() / 255;
		var min = Math.min(r, Math.min(g, b));
		var max = Math.max(r, Math.max(g, b));
		var delta = max - min;
		var ahsv = new Array(4);
		ahsv[0] = a;
		ahsv[3] = max;
		if (delta == 0) {
			ahsv[1] = -1;
			ahsv[2] = 0;
		} else {
			ahsv[1] = $.ig.ColorUtil.prototype.h(max, delta, r, g, b);
			ahsv[2] = delta / max;
		}
		return ahsv;
	}
	,
	fromAHSL: function (alpha, hue, saturation, lightness) {
		var r;
		var g;
		var b;
		if (saturation == 0) {
			r = lightness;
			g = lightness;
			b = lightness;
		} else {
			var q = lightness < 0.5 ? lightness * (1 + saturation) : lightness + saturation - (lightness * saturation);
			var p = 2 * lightness - q;
			var hk = hue / 360;
			r = $.ig.ColorUtil.prototype.c(p, q, hk + 1 / 3);
			g = $.ig.ColorUtil.prototype.c(p, q, hk);
			b = $.ig.ColorUtil.prototype.c(p, q, hk - 1 / 3);
		}
		return $.ig.Color.prototype.fromArgb($.ig.truncate((alpha * 255)), $.ig.truncate((r * 255)), $.ig.truncate((g * 255)), $.ig.truncate((b * 255)));
	}
	,
	fromAHSV: function (alpha, hue, saturation, value) {
		var r;
		var g;
		var b;
		while (hue >= 360) {
			hue -= 360;
		}
		if (saturation == 0) {
			r = value;
			g = value;
			b = value;
		} else {
			hue /= 60;
			var i = Math.floor(hue);
			var f = hue - i;
			var p = value * (1 - saturation);
			var q = value * (1 - saturation * f);
			var t = value * (1 - saturation * (1 - f));
			switch ($.ig.truncate(i)) {
				case 0:
					r = value;
					g = t;
					b = p;
					break;
				case 1:
					r = q;
					g = value;
					b = p;
					break;
				case 2:
					r = p;
					g = value;
					b = t;
					break;
				case 3:
					r = p;
					g = q;
					b = value;
					break;
				case 4:
					r = t;
					g = p;
					b = value;
					break;
				default:
					r = value;
					g = p;
					b = q;
					break;
			}
		}
		return $.ig.Color.prototype.fromArgb($.ig.truncate((alpha * 255)), $.ig.truncate((r * 255)), $.ig.truncate((g * 255)), $.ig.truncate((b * 255)));
	}
	,
	h: function (max, delta, r, g, b) {
		var h = r == max ? (g - b) / delta : g == max ? 2 + (b - r) / delta : 4 + (r - g) / delta;
		h *= 60;
		if (h < 0) {
			h += 360;
		}
		return h;
	}
	,
	c: function (p, q, t) {
		t = t < 0 ? t + 1 : t > 1 ? t - 1 : t;
		if (t < 1 / 6) {
			return p + ((q - p) * 6 * t);
		}
		if (t < 1 / 2) {
			return q;
		}
		if (t < 2 / 3) {
			return p + ((q - p) * 6 * (2 / 3 - t));
		}
		return p;
	}
	,
	randomColors: function () {
		if ($.ig.ColorUtil.prototype.__randomColors == null) {
			$.ig.ColorUtil.prototype.__randomColors = new Array(100);
			$.ig.ColorUtil.prototype.__randomColors[0] = $.ig.Color.prototype.fromArgb(255, 70, 130, 180);
			$.ig.ColorUtil.prototype.__randomColors[1] = $.ig.Color.prototype.fromArgb(255, 65, 105, 225);
			$.ig.ColorUtil.prototype.__randomColors[2] = $.ig.Color.prototype.fromArgb(255, 100, 149, 237);
			$.ig.ColorUtil.prototype.__randomColors[3] = $.ig.Color.prototype.fromArgb(255, 176, 196, 222);
			$.ig.ColorUtil.prototype.__randomColors[4] = $.ig.Color.prototype.fromArgb(255, 123, 104, 238);
			$.ig.ColorUtil.prototype.__randomColors[5] = $.ig.Color.prototype.fromArgb(255, 106, 90, 205);
			$.ig.ColorUtil.prototype.__randomColors[6] = $.ig.Color.prototype.fromArgb(255, 72, 61, 139);
			$.ig.ColorUtil.prototype.__randomColors[7] = $.ig.Color.prototype.fromArgb(255, 25, 25, 112);
			for (var colorIndex = 8; colorIndex < 100; colorIndex++) {
				$.ig.ColorUtil.prototype.__randomColors[colorIndex] = $.ig.Color.prototype.fromArgb(255, $.ig.ColorUtil.prototype._r.next1(255), $.ig.ColorUtil.prototype._r.next1(255), $.ig.ColorUtil.prototype._r.next1(255));
			}
		}
		return $.ig.ColorUtil.prototype.__randomColors;
	}
	,
	getRandomColor: function (index) {
		index %= 100;
		return $.ig.ColorUtil.prototype.randomColors()[index];
	}
	,
	colorToInt: function (color) {
		var aa = color.a() / 255;
		var rr = $.ig.truncate((color.r() * aa));
		var gg = $.ig.truncate((color.g() * aa));
		var bb = $.ig.truncate((color.b() * aa));
		return color.a() << 24 | rr << 16 | gg << 8 | bb;
	}
	,
	getColor: function (brush) {
		return brush.color();
	}
	,
	$type: new $.ig.Type('ColorUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CssHelper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getDisoveryElement: function () {
		var discoveryStyle = $("#fakediscoveryelementstyle");
		if (discoveryStyle.length == 0) {
			var styleText = "fakediscoveryelement\n" + "{\n" + "\tdisplay: block;\n" + "   position: absolute;\n" + "   box-sizing: content-box;\n" + "   -moz-box-sizing: content-box;\n" + "\tmargin: " + $.ig.CssHelper.prototype.defaultMarginValue + ";\n" + "\tcolor: " + $.ig.CssHelper.prototype.defaultColorValue + ";\n" + "   border-style: solid;\n" + "   border-color: " + $.ig.CssHelper.prototype.defaultColorValue + ";\n" + "   background-color: " + $.ig.CssHelper.prototype.defaultColorValue + ";\n" + "   background-image: " + $.ig.CssHelper.prototype.defaultBackgroundImageValue + ";\n" + "   border-width: " + $.ig.CssHelper.prototype.defaultWidthHeightValue + ";\n" + "   border-radius: " + $.ig.CssHelper.prototype.defaultWidthHeightValue + ";\n" + "   vertical-align: " + $.ig.CssHelper.prototype.defaultVerticalAlignValue + ";\n" + "   text-align: " + $.ig.CssHelper.prototype.defaultTextAlignValue + ";\n" + "   opacity: " + $.ig.CssHelper.prototype.defaultOpacityValue + ";\n" + "   visibility: " + $.ig.CssHelper.prototype.defaultVisibilityValue + ";\n" + "   width: " + $.ig.CssHelper.prototype.defaultWidthHeightValue + ";\n" + "   height: " + $.ig.CssHelper.prototype.defaultWidthHeightValue + ";\n" + "}\n";
			discoveryStyle = $("<style id='fakediscoveryelementstyle'></style>");
			discoveryStyle.html(styleText);
			$("head").append(discoveryStyle);
		}
		return $("<fakediscoveryelement></fakediscoveryelement>");
	}
	,
	getDefaultValue: function (propertyName) {
		if (propertyName == "color" || propertyName == "border-color" || propertyName == "border-top-color" || propertyName == "border-left-color" || propertyName == "border-right-color" || propertyName == "border-bottom-color" || propertyName == "background-color") {
			return $.ig.CssHelper.prototype.defaultColorValue;
		} else if (propertyName == "margin-left" || propertyName == "margin-top" || propertyName == "margin-right" || propertyName == "margin-bottom") {
			return $.ig.CssHelper.prototype.defaultMarginValue;
		} else if (propertyName == "vertical-align") {
			return $.ig.CssHelper.prototype.defaultVerticalAlignValue;
		} else if (propertyName == "opacity") {
			return $.ig.CssHelper.prototype.defaultOpacityValue;
		} else if (propertyName == "background-image") {
			return $.ig.CssHelper.prototype.defaultBackgroundImageValue;
		} else if (propertyName == "text-align") {
			return $.ig.CssHelper.prototype.defaultTextAlignValue;
		} else if (propertyName == "visibility") {
			return $.ig.CssHelper.prototype.defaultVisibilityValue;
		} else if ($.ig.CssHelper.prototype.isWidthProperty(propertyName)) {
			return $.ig.CssHelper.prototype.defaultWidthHeightValue;
		}
		return "DEFAULT";
	}
	,
	numberOfClasses: function (discoveryElement, classPrefix, propertyName) {
		var defaultValue = $.ig.CssHelper.prototype.getDefaultValue(propertyName);
		var curr = 1;
		var done = false;
		while (!done && curr < $.ig.CssHelper.prototype.maxClasses) {
			var className = classPrefix + curr.toString();
			discoveryElement.addClass(className);
			var propValue = discoveryElement.css(propertyName);
			if (propValue == defaultValue) {
				break;
			}
			curr++;
		}
		return curr;
	}
	,
	getPropertyValue: function (discoveryElement, className, propertyName) {
		discoveryElement.addClass(className);
		var ret_ = discoveryElement.css(propertyName);
		discoveryElement.removeClass(className);
		if (propertyName == "opacity") {
			ret_ = Math.round(1000.0 * ret_) / 1000.0;
		}
		if ($.ig.CssHelper.prototype.isEqualToDefault(ret_, $.ig.CssHelper.prototype.getDefaultValue(propertyName), propertyName)) {
			return null;
		}
		return ret_;
	}
	,
	isEqualToDefault: function (propValue, defaultValue, propertyName) {
		if (propValue == defaultValue) {
			return true;
		}
		if ($.ig.CssHelper.prototype.isWidthProperty(propertyName) && propValue != null && propValue.contains("px")) {
			var num = $.ig.util.replace(propValue, "px", "");
			var val = parseFloat(num);
			if (Math.abs(Math.round(val) - 4321) < 2) {
				return true;
			}
			return false;
		} else if ($.ig.CssHelper.prototype.isMarginProperty(propertyName) && propValue != null && propValue.contains("px")) {
			var num1 = $.ig.util.replace(propValue, "px", "");
			var val1 = parseFloat(num1);
			if (Math.abs(Math.round(val1) + 4321) < 2) {
				return true;
			}
			return false;
		} else {
			return propValue == defaultValue;
		}
	}
	,
	isWidthProperty: function (propertyName) {
		return propertyName == "width" || propertyName == "height" || propertyName == "border-top-width" || propertyName == "border-left-width" || propertyName == "border-right-width" || propertyName == "border-bottom-width" || propertyName == "border-top-left-radius";
	}
	,
	isMarginProperty: function (propertyName) {
		return propertyName == "margin-top" || propertyName == "margin-left" || propertyName == "margin-right" || propertyName == "margin-bottom";
	}
	,
	getValuesForClassCollection: function (discoveryElement, classPrefix, propertyNames) {
		var ret = new $.ig.List$1($.ig.List$1.prototype.$type.specialize(String), 0);
		var curr = 1;
		var done = false;
		while (!done && curr < $.ig.CssHelper.prototype.maxClasses) {
			var className = classPrefix + curr.toString();
			discoveryElement.addClass(className);
			var row = new $.ig.List$1(String, 0);
			for (var i = 0; i < propertyNames.count(); i++) {
				var propertyName = propertyNames.__inner[i];
				var defaultValue = $.ig.CssHelper.prototype.getDefaultValue(propertyName);
				var propValue = discoveryElement.css(propertyName);
				if ($.ig.CssHelper.prototype.isEqualToDefault(propValue, defaultValue, propertyName)) {
					done = true;
					break;
				}
				row.add(propValue);
			}
			discoveryElement.removeClass(className);
			if (!done) {
				ret.add(row);
			}
			curr++;
		}
		return ret;
	}
	,
	$type: new $.ig.Type('CssHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DoubleAnimator', 'Object', {
	needsFlush: function () {
		return this.transitionProgress() == 0;
	}
	,
	flush: function () {
		this.update(true);
	}
	,
	__transitionProgress: 0,
	transitionProgress: function (value) {
		if (arguments.length === 1) {
			this.__transitionProgress = value;
			if (this.propertyChanged != null) {
				this.propertyChanged(this, new $.ig.PropertyChangedEventArgs("TransitionProgress"));
			}
			return value;
		} else {
			return this.__transitionProgress;
		}
	}
	,
	__intervalMilliseconds: 0,
	intervalMilliseconds: function (value) {
		if (arguments.length === 1) {
			this.__intervalMilliseconds = value;
			return value;
		} else {
			return this.__intervalMilliseconds;
		}
	}
	,
	__easingFunction: null,
	easingFunction: function (value) {
		if (arguments.length === 1) {
			this.__easingFunction = value;
			return value;
		} else {
			return this.__easingFunction;
		}
	}
	,
	__from: 0,
	__to: 0,
	from: function (value) {
		if (arguments.length === 1) {
			this.__from = value;
			return value;
		} else {
			return this.__from;
		}
	}
	,
	to: function (value) {
		if (arguments.length === 1) {
			this.__to = value;
			return value;
		} else {
			return this.__to;
		}
	}
	,
	init: function (from, to, intervalMilliseconds) {
		this.__easingFunction = null;
		this.__from = 0;
		this.__to = 0;
		this.__active = false;
		this.__intervalId = -1;
		this.__lastRender = 0;
		$.ig.Object.prototype.init.call(this);
		this.__from = from;
		this.__to = to;
		this.__intervalMilliseconds = intervalMilliseconds;
		this.requestAnimationFrame(window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            window.oRequestAnimationFrame ||
            window.msRequestAnimationFrame ||
            function(callback) {
                window.setTimeout(callback, 1000 / 60);
            });
	},
	_requestAnimationFrame: null,
	requestAnimationFrame: function (value) {
		if (arguments.length === 1) {
			this._requestAnimationFrame = value;
			return value;
		} else {
			return this._requestAnimationFrame;
		}
	}
	,
	__active: false,
	active: function (value) {
		if (arguments.length === 1) {
			this.__active = value;
			return value;
		} else {
			return this.__active;
		}
	}
	,
	start: function () {
		this.__transitionProgress = 0;
		this.__lastRender = 0;
		this.__startTime = $.ig.Date.prototype.now();
		if (!this.__active) {
			this.__active = true;
			this.requestAnimationFrame()(this.tick.runOn(this));
		}
	}
	,
	stop: function () {
		this.__active = false;
		this.__transitionProgress = 0;
		this.__lastRender = 0;
	}
	,
	__startTime: new Date(),
	__intervalId: 0,
	__lastRender: 0,
	tick: function () {
		this.update(false);
	}
	,
	update: function (flush) {
		if (!this.__active) {
			this.stop();
			return;
		}
		var currentTime = $.ig.Date.prototype.now();
		var ellapsedMilliseconds = currentTime.getTime() - this.__startTime.getTime();
		if (ellapsedMilliseconds > this.__intervalMilliseconds) {
			ellapsedMilliseconds = this.__intervalMilliseconds;
		}
		if ((ellapsedMilliseconds - this.__lastRender < 16 && ellapsedMilliseconds != this.__intervalMilliseconds) && !flush) {
			this.requestAnimationFrame()(this.tick.runOn(this));
			return;
		}
		this.__lastRender = ellapsedMilliseconds;
		var p = (ellapsedMilliseconds / this.__intervalMilliseconds);
		if (this.__easingFunction != null) {
			p = this.__easingFunction(p);
		}
		var interpolatedValue = this.__from + ((this.__to - this.__from) * p);
		if (!flush) {
			if (ellapsedMilliseconds == this.__intervalMilliseconds) {
				this.stop();
			} else {
				this.requestAnimationFrame()(this.tick.runOn(this));
			}
		}
		this.transitionProgress(interpolatedValue);
	}
	,
	getElapsedMilliseconds: function () {
		return this.__active ? $.ig.Date.prototype.now().getTime() - this.__startTime.getTime() : 0;
	}
	,
	animationActive: function () {
		return this.__active;
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('DoubleAnimator', $.ig.Object.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('BrushCollection', 'ObservableCollection$1', {
	init: function () {
		this._interpolationMode = $.ig.InterpolationMode.prototype.rGB;
		$.ig.ObservableCollection$1.prototype.init.call(this, $.ig.Brush.prototype.$type, 0);
	},
	selectRandom: function () {
		return this.item($.ig.BrushCollection.prototype._random.next1(this.count()));
	}
	,
	interpolateRandom: function () {
		return this.getInterpolatedBrush($.ig.BrushCollection.prototype._random.nextDouble() * (this.count() - 1));
	}
	,
	interpolationMode: function (value) {
		if (arguments.length === 1) {
			if (this._interpolationMode != value) {
				this._interpolationMode = value;
				this.onCollectionChanged(new $.ig.NotifyCollectionChangedEventArgs(0, $.ig.NotifyCollectionChangedAction.prototype.reset));
			}
			return value;
		} else {
			return this._interpolationMode;
		}
	}
	,
	_interpolationMode: 0,
	item: function (index, value) {
		if (arguments.length === 2) {
			$.ig.ObservableCollection$1.prototype.item.call(this, index, value);
			return value;
		} else {
			if (index < 0 || index >= this.count()) {
				return null;
			}
			return $.ig.ObservableCollection$1.prototype.item.call(this, index);
		}
	}
	,
	getInterpolatedBrush: function (index) {
		if ($.ig.util.isNaN(index)) {
			return null;
		}
		index = $.ig.MathUtil.prototype.clamp(index, 0, this.count() - 1);
		var i = $.ig.truncate(Math.floor(index));
		if (i == index) {
			return this.item(i);
		}
		return this.interpolateBrushes(index - i, this.item(i), this.item(i + 1), this.interpolationMode());
	}
	,
	interpolateBrushes: function (p, minBrush, maxBrush, InterpolationMode) {
		var minFill = minBrush.color();
		var maxFill = maxBrush.color();
		var interp = $.ig.ColorUtil.prototype.getInterpolation(minFill, p, maxFill, InterpolationMode);
		var b = new $.ig.Brush();
		b.color(interp);
		return b;
	}
	,
	equals: function (obj) {
		if (obj == null) {
			return false;
		}
		var bc = obj;
		if (bc.count() != this.count()) {
			return false;
		}
		for (var i = 0; i < bc.count(); i++) {
			if (!bc.item(i).equals(this.item(i))) {
				return false;
			}
		}
		return true;
	}
	,
	$type: new $.ig.Type('BrushCollection', $.ig.ObservableCollection$1.prototype.$type.specialize($.ig.Brush.prototype.$type))
}, true);

$.ig.util.defType('StackPool$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		this._deferDisactivate = false;
		this._active = new $.ig.Dictionary$2(this.$t, $.ig.Object.prototype.$type, 0);
		this._limbo = new $.ig.Stack$1(this.$t);
		this._inactive = new $.ig.Stack$1(this.$t);
		$.ig.Object.prototype.init.call(this);
	},
	pop: function () {
		var t;
		if (this._limbo.count() != 0) {
			t = this._limbo.pop();
		} else {
			t = this._inactive.count() != 0 ? this._inactive.pop() : this.create()();
			this.activate()(t);
		}
		this._active.add(t, null);
		return t;
	}
	,
	push: function (t) {
		this._active.remove(t);
		if (this.deferDisactivate()) {
			this._limbo.push(t);
		} else {
			this.deactivate()(t);
			var inactiveCount = $.ig.StackPool$1.prototype.roundUp(this.$t, this._active.count());
			if (this._inactive.count() < inactiveCount) {
				this.destroy()(t);
			} else {
				this._inactive.push(t);
			}
		}
	}
	,
	deferDisactivate: function (value) {
		if (arguments.length === 1) {
			if (this._deferDisactivate != value) {
				this._deferDisactivate = value;
				if (!this._deferDisactivate) {
					var inactiveCount = $.ig.StackPool$1.prototype.roundUp(this.$t, this._active.count());
					while (this._limbo.count() > 0 && this._inactive.count() <= inactiveCount) {
						var t = this._limbo.pop();
						this.deactivate()(t);
						this._inactive.push(t);
					}
					while (this._limbo.count() > 0) {
						var t1 = this._limbo.pop();
						this.deactivate()(t1);
						this.destroy()(t1);
					}
					while (this._inactive.count() > inactiveCount) {
						this.destroy()(this._inactive.pop());
					}
				}
			}
			return value;
		} else {
			return this._deferDisactivate;
		}
	}
	,
	_deferDisactivate: false,
	activeCount: function () {
		return this._active.count();
	}
	,
	inactiveCount: function () {
		return this._inactive.count();
	}
	,
	_create: null,
	create: function (value) {
		if (arguments.length === 1) {
			this._create = value;
			return value;
		} else {
			return this._create;
		}
	}
	,
	_deactivate: null,
	deactivate: function (value) {
		if (arguments.length === 1) {
			this._deactivate = value;
			return value;
		} else {
			return this._deactivate;
		}
	}
	,
	_activate: null,
	activate: function (value) {
		if (arguments.length === 1) {
			this._activate = value;
			return value;
		} else {
			return this._activate;
		}
	}
	,
	_destroy: null,
	destroy: function (value) {
		if (arguments.length === 1) {
			this._destroy = value;
			return value;
		} else {
			return this._destroy;
		}
	}
	,
	roundUp: function ($t, a) {
		var p = 2;
		while (a > p) {
			p = p << 1;
		}
		return p;
	}
	,
	_active: null,
	_limbo: null,
	_inactive: null,
	$type: new $.ig.Type('StackPool$1', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IVisualData', 'Object', {
	$type: new $.ig.Type('IVisualData', null)
}, true);

$.ig.util.defType('PrimitiveVisualData', 'Object', {
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
	init1: function (initNumber, name) {
		$.ig.Object.prototype.init.call(this);
		this.name(name);
		this.tags(new $.ig.ShapeTags());
		this.appearance(new $.ig.PrimitiveAppearanceData());
	},
	_appearance: null,
	appearance: function (value) {
		if (arguments.length === 1) {
			this._appearance = value;
			return value;
		} else {
			return this._appearance;
		}
	}
	,
	_tags: null,
	tags: function (value) {
		if (arguments.length === 1) {
			this._tags = value;
			return value;
		} else {
			return this._tags;
		}
	}
	,
	type: function () {
	}
	,
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
	scaleByViewport: function (viewport) {
		this.appearance().scaleByViewport(viewport);
	}
	,
	getPoints: function (settings) {
		var points = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 0);
		this.getPointsOverride(points, settings);
		return points;
	}
	,
	getPointsOverride: function (points, settings) {
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("appearance: " + (this.appearance() != null ? this.appearance().serialize() : "null") + ", ");
		sb.appendLine1("tags: [");
		for (var i = 0; i < this.tags().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5("\"" + this.tags().__inner[i] + "\"");
		}
		sb.appendLine1("],");
		sb.appendLine1("type: \"" + this.type() + "\", ");
		sb.appendLine1("name: \"" + this.name() + "\", ");
		sb.appendLine1(this.serializeOverride());
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	serializeOverride: function () {
		return "";
	}
	,
	$type: new $.ig.Type('PrimitiveVisualData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('PrimitiveVisualDataList', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.PrimitiveVisualData.prototype.$type, 0);
	},
	containingTag: function (tag) {
		var ret = new $.ig.PrimitiveVisualDataList();
		for (var i = 0; i < this.count(); i++) {
			var curr = this.__inner[i];
			for (var j = 0; j < curr.tags().count(); j++) {
				if (curr.tags().__inner[j] == tag) {
					ret.add(curr);
					break;
				}
			}
		}
		return ret;
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.append5("{ items: [");
		for (var i = 0; i < this.count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5(this.__inner[i].serialize());
		}
		return sb.toString();
	}
	,
	$type: new $.ig.Type('PrimitiveVisualDataList', $.ig.List$1.prototype.$type.specialize($.ig.PrimitiveVisualData.prototype.$type), [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('LabelAppearanceData', 'Object', {
	init: function () {
		this._labelBrush = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
	},
	_text: null,
	text: function (value) {
		if (arguments.length === 1) {
			this._text = value;
			return value;
		} else {
			return this._text;
		}
	}
	,
	_horizontalAlignment: null,
	horizontalAlignment: function (value) {
		if (arguments.length === 1) {
			this._horizontalAlignment = value;
			return value;
		} else {
			return this._horizontalAlignment;
		}
	}
	,
	_verticalAlignment: null,
	verticalAlignment: function (value) {
		if (arguments.length === 1) {
			this._verticalAlignment = value;
			return value;
		} else {
			return this._verticalAlignment;
		}
	}
	,
	_textAlignment: null,
	textAlignment: function (value) {
		if (arguments.length === 1) {
			this._textAlignment = value;
			return value;
		} else {
			return this._textAlignment;
		}
	}
	,
	_textWrapping: null,
	textWrapping: function (value) {
		if (arguments.length === 1) {
			this._textWrapping = value;
			return value;
		} else {
			return this._textWrapping;
		}
	}
	,
	_textPosition: null,
	textPosition: function (value) {
		if (arguments.length === 1) {
			this._textPosition = value;
			return value;
		} else {
			return this._textPosition;
		}
	}
	,
	_labelBrush: null,
	labelBrush: function (value) {
		if (arguments.length === 1) {
			this._labelBrush = value;
			return value;
		} else {
			return this._labelBrush;
		}
	}
	,
	_labelBrushExtended: null,
	labelBrushExtended: function (value) {
		if (arguments.length === 1) {
			this._labelBrushExtended = value;
			return value;
		} else {
			return this._labelBrushExtended;
		}
	}
	,
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
	_opacity: 0,
	opacity: function (value) {
		if (arguments.length === 1) {
			this._opacity = value;
			return value;
		} else {
			return this._opacity;
		}
	}
	,
	_visibility: false,
	visibility: function (value) {
		if (arguments.length === 1) {
			this._visibility = value;
			return value;
		} else {
			return this._visibility;
		}
	}
	,
	_font: null,
	font: function (value) {
		if (arguments.length === 1) {
			this._font = value;
			return value;
		} else {
			return this._font;
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
	_marginLeft: 0,
	marginLeft: function (value) {
		if (arguments.length === 1) {
			this._marginLeft = value;
			return value;
		} else {
			return this._marginLeft;
		}
	}
	,
	_marginRight: 0,
	marginRight: function (value) {
		if (arguments.length === 1) {
			this._marginRight = value;
			return value;
		} else {
			return this._marginRight;
		}
	}
	,
	_marginTop: 0,
	marginTop: function (value) {
		if (arguments.length === 1) {
			this._marginTop = value;
			return value;
		} else {
			return this._marginTop;
		}
	}
	,
	_marginBottom: 0,
	marginBottom: function (value) {
		if (arguments.length === 1) {
			this._marginBottom = value;
			return value;
		} else {
			return this._marginBottom;
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("text: \"" + (this.text() != null ? this.text() : "") + "\", ");
		if (this.textAlignment() != null) {
			sb.appendLine1("textAlignment: \"" + this.textAlignment() + "\", ");
		}
		if (this.textWrapping() != null) {
			sb.appendLine1("textWrapping: \"" + this.textWrapping() + "\", ");
		}
		sb.appendLine1("labelBrush: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.labelBrush()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.labelBrush()) : "null") + ", ");
		sb.appendLine1("labelBrushExtended: " + (this.labelBrushExtended() != null ? this.labelBrushExtended().serialize() : "null") + ", ");
		sb.appendLine1("angle: " + this.angle() + ", ");
		sb.appendLine1("marginLeft: " + this.marginLeft() + ", ");
		sb.appendLine1("marginRight: " + this.marginRight() + ", ");
		sb.appendLine1("marginTop: " + this.marginTop() + ", ");
		sb.appendLine1("marginBottom: " + this.marginBottom() + ", ");
		sb.appendLine1("opacity: " + this.opacity() + ", ");
		sb.appendLine1("visibility: " + (this.visibility() ? "true" : "false") + ", ");
		if (this.horizontalAlignment() != null) {
			sb.appendLine1("horizontalAlignment: \"" + this.horizontalAlignment() + "\", ");
		}
		if (this.verticalAlignment() != null) {
			sb.appendLine1("verticalAlignment: \"" + this.verticalAlignment() + "\", ");
		}
		if (this.font() != null) {
			sb.appendLine1("font: \"" + this.font() + "\",");
		}
		if (this.fontFamily() != null) {
			sb.appendLine1("fontFamily: \"" + $.ig.util.replace(this.fontFamily(), "\"", "'") + "\",");
		}
		if (this.fontWeight() != null) {
			sb.appendLine1("fontWeight: \"" + this.fontWeight() + "\",");
		}
		if (this.fontStyle() != null) {
			sb.appendLine1("fontStyle: \"" + this.fontStyle() + "\",");
		}
		if (this.fontStretch() != null) {
			sb.appendLine1("fontStretch: \"" + this.fontStretch() + "\",");
		}
		sb.appendLine1("fontSize: " + this.fontSize());
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('LabelAppearanceData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('BrushAppearanceData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	type: function () {
	}
	,
	serialize: function () {
		return "{ type: \"" + this.type() + "\", " + this.serializeOverride() + " }";
	}
	,
	serializeOverride: function () {
		return "";
	}
	,
	$type: new $.ig.Type('BrushAppearanceData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('SolidBrushAppearanceData', 'BrushAppearanceData', {
	init: function () {
		this._colorValue = new $.ig.Color();
		$.ig.BrushAppearanceData.prototype.init.call(this);
	},
	type: function () {
		return "solid";
	}
	,
	_colorValue: null,
	colorValue: function (value) {
		if (arguments.length === 1) {
			this._colorValue = value;
			return value;
		} else {
			return this._colorValue;
		}
	}
	,
	serializeOverride: function () {
		return "colorValue: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.colorValue()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.colorValue()) : "null");
	}
	,
	$type: new $.ig.Type('SolidBrushAppearanceData', $.ig.BrushAppearanceData.prototype.$type)
}, true);

$.ig.util.defType('LinearGradientBrushAppearanceData', 'BrushAppearanceData', {
	init: function () {
		$.ig.BrushAppearanceData.prototype.init.call(this);
		this.stops(new $.ig.List$1($.ig.GradientStopAppearanceData.prototype.$type, 0));
	},
	type: function () {
		return "linear";
	}
	,
	_startX: 0,
	startX: function (value) {
		if (arguments.length === 1) {
			this._startX = value;
			return value;
		} else {
			return this._startX;
		}
	}
	,
	_startY: 0,
	startY: function (value) {
		if (arguments.length === 1) {
			this._startY = value;
			return value;
		} else {
			return this._startY;
		}
	}
	,
	_endX: 0,
	endX: function (value) {
		if (arguments.length === 1) {
			this._endX = value;
			return value;
		} else {
			return this._endX;
		}
	}
	,
	_endY: 0,
	endY: function (value) {
		if (arguments.length === 1) {
			this._endY = value;
			return value;
		} else {
			return this._endY;
		}
	}
	,
	_stops: null,
	stops: function (value) {
		if (arguments.length === 1) {
			this._stops = value;
			return value;
		} else {
			return this._stops;
		}
	}
	,
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.append5("startX: " + this.startX() + ", endX: " + this.endX() + ", startY: " + this.startY() + ", endY: " + this.endY());
		sb.append5(", stops: [");
		for (var i = 0; i < this.stops().count(); i++) {
			if (i > 0) {
				sb.append5(", ");
			}
			sb.append5(this.stops().__inner[i].serialize());
		}
		sb.append5("]");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('LinearGradientBrushAppearanceData', $.ig.BrushAppearanceData.prototype.$type)
}, true);

$.ig.util.defType('GradientStopAppearanceData', 'Object', {
	init: function () {
		this._colorValue = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
	},
	_colorValue: null,
	colorValue: function (value) {
		if (arguments.length === 1) {
			this._colorValue = value;
			return value;
		} else {
			return this._colorValue;
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
	serialize: function () {
		return "{ " + "colorValue: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.colorValue()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.colorValue()) : "null") + ", offset: " + this.offset() + " }";
	}
	,
	$type: new $.ig.Type('GradientStopAppearanceData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('PrimitiveAppearanceData', 'Object', {
	init: function () {
		this._stroke = new $.ig.Color();
		this._fill = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
	},
	_stroke: null,
	stroke: function (value) {
		if (arguments.length === 1) {
			this._stroke = value;
			return value;
		} else {
			return this._stroke;
		}
	}
	,
	_strokeExtended: null,
	strokeExtended: function (value) {
		if (arguments.length === 1) {
			this._strokeExtended = value;
			return value;
		} else {
			return this._strokeExtended;
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
	_fillExtended: null,
	fillExtended: function (value) {
		if (arguments.length === 1) {
			this._fillExtended = value;
			return value;
		} else {
			return this._fillExtended;
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
	_opacity: 0,
	opacity: function (value) {
		if (arguments.length === 1) {
			this._opacity = value;
			return value;
		} else {
			return this._opacity;
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
	_canvaZIndex: 0,
	canvaZIndex: function (value) {
		if (arguments.length === 1) {
			this._canvaZIndex = value;
			return value;
		} else {
			return this._canvaZIndex;
		}
	}
	,
	_dashArray: null,
	dashArray: function (value) {
		if (arguments.length === 1) {
			this._dashArray = value;
			return value;
		} else {
			return this._dashArray;
		}
	}
	,
	_dashCap: 0,
	dashCap: function (value) {
		if (arguments.length === 1) {
			this._dashCap = value;
			return value;
		} else {
			return this._dashCap;
		}
	}
	,
	scaleByViewport: function (viewport) {
		this.canvasLeft((this.canvasLeft() - viewport.left()) / viewport.width());
		this.canvasTop((this.canvasTop() - viewport.top()) / viewport.height());
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("stroke: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.stroke()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.stroke()) : "null") + ", ");
		sb.appendLine1("fill: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.fill()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.fill()) : "null") + ", ");
		sb.appendLine1("strokeExtended: " + (this.strokeExtended() != null ? this.strokeExtended().serialize() : "null") + ", ");
		sb.appendLine1("fillExtended: " + (this.fillExtended() != null ? this.fillExtended().serialize() : "null") + ", ");
		sb.appendLine1("strokeThickness: " + this.strokeThickness() + ", ");
		sb.appendLine1("visibility: " + (this.visibility() == $.ig.Visibility.prototype.visible ? "true" : "false") + ", ");
		sb.appendLine1("opacity: " + this.opacity() + ", ");
		sb.appendLine1("canvasLeft: " + this.canvasLeft() + ", ");
		sb.appendLine1("canvasTop: " + this.canvasTop() + ", ");
		sb.appendLine1("canvasZIndex: " + this.canvaZIndex() + ", ");
		sb.appendLine1("dashArray: null, ");
		sb.appendLine1("dashCap: " + this.dashCap());
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('PrimitiveAppearanceData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('GetPointsSettings', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_ignoreFigureStartPoint: false,
	ignoreFigureStartPoint: function (value) {
		if (arguments.length === 1) {
			this._ignoreFigureStartPoint = value;
			return value;
		} else {
			return this._ignoreFigureStartPoint;
		}
	}
	,
	$type: new $.ig.Type('GetPointsSettings', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ShapeTags', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, String, 0);
	},
	$type: new $.ig.Type('ShapeTags', $.ig.List$1.prototype.$type.specialize(String))
}, true);

$.ig.util.defType('PathVisualData', 'PrimitiveVisualData', {
	type: function () {
		return "Path";
	}
	,
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
		$.ig.PrimitiveVisualData.prototype.init1.call(this, 1, "path1");
		this.data(new $.ig.List$1($.ig.GeometryData.prototype.$type, 0));
	},
	init1: function (initNumber, name, path) {
		$.ig.PrimitiveVisualData.prototype.init1.call(this, 1, name);
		this.data($.ig.AppearanceHelper.prototype.fromGeometry(path.data()));
		$.ig.AppearanceHelper.prototype.getShapeAppearance(this.appearance(), path);
	},
	init2: function (initNumber, name, line) {
		$.ig.PrimitiveVisualData.prototype.init1.call(this, 1, name);
		this.data($.ig.AppearanceHelper.prototype.fromLineData(line));
		$.ig.AppearanceHelper.prototype.getShapeAppearance(this.appearance(), line);
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
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("data: [");
		for (var i = 0; i < this.data().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5((this.data().__inner[i] != null ? this.data().__inner[i].serialize() : "null"));
		}
		sb.appendLine1("]");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		$.ig.PrimitiveVisualData.prototype.scaleByViewport.call(this, viewport);
		var en = this.data().getEnumerator();
		while (en.moveNext()) {
			var data = en.current();
			data.scaleByViewport(viewport);
		}
	}
	,
	getPointsOverride: function (points, settings) {
		for (var i = 0; i < this.data().count(); i++) {
			var data = this.data().__inner[i];
			data.getPointsOverride(points, settings);
		}
	}
	,
	$type: new $.ig.Type('PathVisualData', $.ig.PrimitiveVisualData.prototype.$type)
}, true);

$.ig.util.defType('GeometryData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	type: function () {
	}
	,
	scaleByViewport: function (viewport) {
	}
	,
	getPointsOverride: function (points, settings) {
	}
	,
	serialize: function () {
		return "{ type: \"" + this.type() + "\", " + this.serializeOverride() + "}";
	}
	,
	serializeOverride: function () {
		return "";
	}
	,
	$type: new $.ig.Type('GeometryData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('PathGeometryData', 'GeometryData', {
	init: function () {
		$.ig.GeometryData.prototype.init.call(this);
		this.figures(new $.ig.List$1($.ig.PathFigureData.prototype.$type, 0));
	},
	type: function () {
		return "Path";
	}
	,
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
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("figures: [");
		for (var i = 0; i < this.figures().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5(this.figures().__inner[i].serialize());
		}
		sb.appendLine1("]");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		var en = this.figures().getEnumerator();
		while (en.moveNext()) {
			var figure = en.current();
			figure.scaleByViewport(viewport);
		}
	}
	,
	getPointsOverride: function (points, settings) {
		for (var i = 0; i < this.figures().count(); i++) {
			var figure = this.figures().__inner[i];
			figure.getPointsOverride(points, settings);
		}
	}
	,
	$type: new $.ig.Type('PathGeometryData', $.ig.GeometryData.prototype.$type)
}, true);

$.ig.util.defType('LineGeometryData', 'GeometryData', {
	init: function () {
		$.ig.GeometryData.prototype.init.call(this);
	},
	type: function () {
		return "Line";
	}
	,
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
	serializeOverride: function () {
		return "x1: " + this.x1() + ", y1: " + this.y1() + ", x2: " + this.x2() + ", y2:" + this.y2();
	}
	,
	scaleByViewport: function (viewport) {
		this.x1((this.x1() - viewport.left()) / viewport.width());
		this.y1((this.y1() - viewport.top()) / viewport.height());
		this.x2((this.x2() - viewport.left()) / viewport.width());
		this.y2((this.y2() - viewport.top()) / viewport.height());
	}
	,
	getPointsOverride: function (points, settings) {
		var current = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		points.add(current);
		current.add({ __x: this.x1(), __y: this.y1(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		current.add({ __x: this.x2(), __y: this.y2(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	$type: new $.ig.Type('LineGeometryData', $.ig.GeometryData.prototype.$type)
}, true);

$.ig.util.defType('RectangleGeometryData', 'GeometryData', {
	init: function () {
		$.ig.GeometryData.prototype.init.call(this);
	},
	type: function () {
		return "Rectangle";
	}
	,
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
	serializeOverride: function () {
		return "x: " + this.x() + ", y: " + this.y() + ", width: " + this.width() + ", height: " + this.height();
	}
	,
	scaleByViewport: function (viewport) {
		this.x((this.x() - viewport.left()) / viewport.width());
		this.y((this.y() - viewport.top()) / viewport.height());
		this.width(this.width() / viewport.width());
		this.height(this.height() / viewport.height());
	}
	,
	getPointsOverride: function (points, settings) {
		var current = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		points.add(current);
		current.add({ __x: this.x(), __y: this.y(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		current.add({ __x: this.x() + this.width(), __y: this.y(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		current.add({ __x: this.x() + this.width(), __y: this.y() + this.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		current.add({ __x: this.x(), __y: this.y() + this.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	$type: new $.ig.Type('RectangleGeometryData', $.ig.GeometryData.prototype.$type)
}, true);

$.ig.util.defType('EllipseGeometryData', 'GeometryData', {
	init: function () {
		$.ig.GeometryData.prototype.init.call(this);
	},
	type: function () {
		return "Ellipse";
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
	serializeOverride: function () {
		return "centerX: " + this.centerX() + ", centerY: " + this.centerY() + ", radiusX: " + this.radiusX() + ", radiusY: " + this.radiusY();
	}
	,
	scaleByViewport: function (viewport) {
		this.centerX((this.centerX() - viewport.left()) / viewport.width());
		this.centerX((this.centerY() - viewport.top()) / viewport.height());
		this.radiusX(this.radiusX() / viewport.width());
		this.radiusY(this.radiusY() / viewport.height());
	}
	,
	getPointsOverride: function (points, settings) {
		var current = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		points.add(current);
		current.add({ __x: this.centerX(), __y: this.centerY(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	$type: new $.ig.Type('EllipseGeometryData', $.ig.GeometryData.prototype.$type)
}, true);

$.ig.util.defType('PathFigureData', 'Object', {
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
		this.segments(new $.ig.List$1($.ig.SegmentData.prototype.$type, 0));
		this.startPoint(new $.ig.Point(0));
	},
	init1: function (initNumber, fig) {
		$.ig.Object.prototype.init.call(this);
		this.segments(new $.ig.List$1($.ig.SegmentData.prototype.$type, 0));
		this.startPoint(fig.__startPoint);
		for (var i = 0; i < fig.__segments.count(); i++) {
			var seg = fig.__segments.__inner[i];
			var newData = null;
			switch (seg.type()) {
				case $.ig.PathSegmentType.prototype.line:
					newData = new $.ig.LineSegmentData(1, seg);
					break;
				case $.ig.PathSegmentType.prototype.polyLine:
					newData = new $.ig.PolylineSegmentData(1, seg);
					break;
				case $.ig.PathSegmentType.prototype.arc:
					newData = new $.ig.ArcSegmentData(1, seg);
					break;
				case $.ig.PathSegmentType.prototype.polyBezier:
					newData = new $.ig.PolyBezierSegmentData(1, seg);
					break;
				case $.ig.PathSegmentType.prototype.bezier:
					newData = new $.ig.BezierSegmentData(1, seg);
					break;
			}
			this.segments().add(newData);
		}
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
	_segments: null,
	segments: function (value) {
		if (arguments.length === 1) {
			this._segments = value;
			return value;
		} else {
			return this._segments;
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		if ($.ig.Point.prototype.l_op_Inequality(this.startPoint(), null)) {
			sb.appendLine1("startPoint: { x: " + this.startPoint().__x + ", y: " + this.startPoint().__y + "}, ");
		}
		sb.appendLine1("segments: [");
		for (var i = 0; i < this.segments().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5(this.segments().__inner[i].serialize());
		}
		sb.appendLine1("]");
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		if ($.ig.Point.prototype.l_op_Inequality(this.startPoint(), null)) {
			this.startPoint({ __x: (this.startPoint().__x - viewport.left()) / viewport.width(), __y: (this.startPoint().__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		for (var i = 0; i < this.segments().count(); i++) {
			this.segments().__inner[i].scaleByViewport(viewport);
		}
	}
	,
	getPointsOverride: function (points, settings) {
		var current = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		points.add(current);
		if (!settings.ignoreFigureStartPoint()) {
			current.add({ __x: this.startPoint().__x, __y: this.startPoint().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		for (var i = 0; i < this.segments().count(); i++) {
			this.segments().__inner[i].getPointsOverride(current, settings);
		}
	}
	,
	$type: new $.ig.Type('PathFigureData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('SegmentData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	type: function () {
	}
	,
	scaleByViewport: function (viewport) {
	}
	,
	getPointsOverride: function (current, settings) {
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("type: \"" + this.type() + "\", ");
		sb.appendLine1(this.serializeOverride());
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	serializeOverride: function () {
		return "";
	}
	,
	$type: new $.ig.Type('SegmentData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('LineSegmentData', 'SegmentData', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.SegmentData.prototype.init.call(this);
		this.point(new $.ig.Point(0));
	},
	init1: function (initNumber, seg) {
		$.ig.SegmentData.prototype.init.call(this);
		this.point(seg.point());
	},
	type: function () {
		return "Line";
	}
	,
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
	serializeOverride: function () {
		return "point: { x: " + this.point().__x + ", y: " + this.point().__y + "}";
	}
	,
	scaleByViewport: function (viewport) {
		this.point({ __x: (this.point().__x - viewport.left()) / viewport.width(), __y: (this.point().__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	getPointsOverride: function (current, settings) {
		current.add({ __x: this.point().__x, __y: this.point().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	$type: new $.ig.Type('LineSegmentData', $.ig.SegmentData.prototype.$type)
}, true);

$.ig.util.defType('PolylineSegmentData', 'SegmentData', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
	},
	init1: function (initNumber, poly) {
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
		for (var i = 0; i < poly.__points.count(); i++) {
			this.points().add(poly.__points.__inner[i]);
		}
	},
	type: function () {
		return "Polyline";
	}
	,
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
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("points: [");
		for (var i = 0; i < this.points().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5("{ x: " + this.points().__inner[i].__x + ", y: " + this.points().__inner[i].__y + "}");
		}
		sb.appendLine1("]");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		for (var i = 0; i < this.points().count(); i++) {
			this.points().__inner[i] = { __x: (this.points().__inner[i].__x - viewport.left()) / viewport.width(), __y: (this.points().__inner[i].__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
	}
	,
	getPointsOverride: function (current, settings) {
		for (var i = 0; i < this.points().count(); i++) {
			current.add({ __x: this.points().__inner[i].__x, __y: this.points().__inner[i].__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
	}
	,
	$type: new $.ig.Type('PolylineSegmentData', $.ig.SegmentData.prototype.$type)
}, true);

$.ig.util.defType('BezierSegmentData', 'SegmentData', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
	},
	init1: function (initNumber, segment) {
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
		this.points().add(segment.point1());
		this.points().add(segment.point2());
		this.points().add(segment.point3());
	},
	type: function () {
		return "Bezier";
	}
	,
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
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("points: [");
		for (var i = 0; i < this.points().count(); i++) {
			if ($.ig.Point.prototype.l_op_Equality(this.points().__inner[i], null)) {
				break;
			}
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5("{ x: " + this.points().__inner[i].__x + ", y: " + this.points().__inner[i].__y + "}");
		}
		sb.appendLine1("]");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		for (var i = 0; i < this.points().count(); i++) {
			if ($.ig.Point.prototype.l_op_Equality(this.points().__inner[i], null)) {
				break;
			}
			this.points().__inner[i] = { __x: (this.points().__inner[i].__x - viewport.left()) / viewport.width(), __y: (this.points().__inner[i].__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
	}
	,
	getPointsOverride: function (current, settings) {
		for (var i = 0; i < this.points().count(); i++) {
			current.add({ __x: this.points().__inner[i].__x, __y: this.points().__inner[i].__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
	}
	,
	$type: new $.ig.Type('BezierSegmentData', $.ig.SegmentData.prototype.$type)
}, true);

$.ig.util.defType('PolyBezierSegmentData', 'SegmentData', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
	},
	init1: function (initNumber, poly) {
		$.ig.SegmentData.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
		for (var i = 0; i < poly.points().count(); i++) {
			this.points().add(poly.points().__inner[i]);
		}
	},
	type: function () {
		return "PolyBezierSpline";
	}
	,
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
	serializeOverride: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("points: [");
		for (var i = 0; i < this.points().count(); i++) {
			if (i != 0) {
				sb.append5(", ");
			}
			sb.append5("{ x: " + this.points().__inner[i].__x + ", y: " + this.points().__inner[i].__y + "}");
		}
		sb.appendLine1("]");
		return sb.toString();
	}
	,
	scaleByViewport: function (viewport) {
		for (var i = 0; i < this.points().count(); i++) {
			this.points().__inner[i] = { __x: (this.points().__inner[i].__x - viewport.left()) / viewport.width(), __y: (this.points().__inner[i].__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
	}
	,
	getPointsOverride: function (current, settings) {
		for (var i = 0; i < this.points().count(); i++) {
			current.add({ __x: this.points().__inner[i].__x, __y: this.points().__inner[i].__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
	}
	,
	$type: new $.ig.Type('PolyBezierSegmentData', $.ig.SegmentData.prototype.$type)
}, true);

$.ig.util.defType('ArcSegmentData', 'SegmentData', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.SegmentData.prototype.init.call(this);
		this.point(new $.ig.Point(0));
		this.isLargeArc(false);
		this.isCounterClockwise(true);
		this.rotationAngle(0);
	},
	init1: function (initNumber, arc) {
		$.ig.SegmentData.prototype.init.call(this);
		this.point(arc.point());
		this.isLargeArc(arc.isLargeArc());
		this.isCounterClockwise(arc.sweepDirection() == $.ig.SweepDirection.prototype.counterclockwise);
		this.sizeX(arc.size().width());
		this.sizeY(arc.size().height());
		this.rotationAngle(arc.rotationAngle());
	},
	type: function () {
		return "Arc";
	}
	,
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
	_isCounterClockwise: false,
	isCounterClockwise: function (value) {
		if (arguments.length === 1) {
			this._isCounterClockwise = value;
			return value;
		} else {
			return this._isCounterClockwise;
		}
	}
	,
	_sizeX: 0,
	sizeX: function (value) {
		if (arguments.length === 1) {
			this._sizeX = value;
			return value;
		} else {
			return this._sizeX;
		}
	}
	,
	_sizeY: 0,
	sizeY: function (value) {
		if (arguments.length === 1) {
			this._sizeY = value;
			return value;
		} else {
			return this._sizeY;
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
	serializeOverride: function () {
		return "point: { x: " + this.point().__x + ", y: " + this.point().__y + " }, isLargeArc: " + (this.isLargeArc() ? "true" : "false") + ", isCounterClockwise: " + (this.isCounterClockwise() ? "true" : "false") + ", sizeX: " + this.sizeX() + ", sizeY: " + this.sizeY() + ", rotationAngle: " + this.rotationAngle();
	}
	,
	scaleByViewport: function (viewport) {
		this.point({ __x: (this.point().__x - viewport.left()) / viewport.width(), __y: (this.point().__y - viewport.top()) / viewport.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		this.sizeX(this.sizeX() / viewport.width());
		this.sizeY(this.sizeY() / viewport.height());
	}
	,
	getPointsOverride: function (current, settings) {
		current.add({ __x: this.point().__x, __y: this.point().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	$type: new $.ig.Type('ArcSegmentData', $.ig.SegmentData.prototype.$type)
}, true);

$.ig.util.defType('AppearanceHelper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	fromBrush: function (b) {
		if (b == null) {
			return $.ig.Color.prototype.fromArgb(0, 0, 0, 0);
		}
		if ($.ig.Color.prototype.l_op_Equality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, b.color()), $.ig.util.toNullable($.ig.Color.prototype.$type, null))) {
			return $.ig.Color.prototype.fromArgb(0, 0, 0, 0);
		}
		return b.color();
	}
	,
	fromBrushExtended: function (b) {
		if (b == null) {
			return null;
		}
		if (b._isGradient) {
			var linear = new $.ig.LinearGradientBrushAppearanceData();
			var inLinear = b;
			linear.startX(inLinear._startX);
			linear.startY(inLinear._startY);
			linear.endX(inLinear._endX);
			linear.endY(inLinear._endY);
			var $t = inLinear._gradientStops;
			for (var i = 0; i < $t.length; i++) {
				var stop = $t[i];
				var newStop = new $.ig.GradientStopAppearanceData();
				newStop.colorValue(stop.color());
				newStop.offset(stop._offset);
				linear.stops().add(newStop);
			}
			return linear;
		} else if (b._isRadialGradient) {
			return null;
		} else {
			var solid = new $.ig.SolidBrushAppearanceData();
			solid.colorValue(b.color());
			return solid;
		}
		return null;
	}
	,
	getCanvasLeft: function (visual) {
		return visual.canvasLeft();
	}
	,
	getCanvasTop: function (visual) {
		return visual.canvasTop();
	}
	,
	getCanvasZIndex: function (line) {
		return line.canvasZIndex();
	}
	,
	fromPathData: function (path) {
		return $.ig.AppearanceHelper.prototype.fromGeometry(path.data());
	}
	,
	fromLineData: function (line) {
		var lineGeometry = new $.ig.LineGeometry();
		lineGeometry.endPoint({ __x: line.x2(), __y: line.y2(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		lineGeometry.startPoint({ __x: line.x1(), __y: line.y1(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		return $.ig.AppearanceHelper.prototype.fromGeometry(lineGeometry);
	}
	,
	fromGeometry: function (data) {
		if (data == null) {
			return new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
		}
		if ($.ig.util.cast($.ig.GeometryGroup.prototype.$type, data) !== null) {
			var ret = new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
			var group = data;
			for (var i = 0; i < group.children().count(); i++) {
				var items = $.ig.AppearanceHelper.prototype.fromGeometry(group.children().__inner[i]);
				for (var j = 0; j < items.count(); j++) {
					ret.add(items.__inner[j]);
				}
			}
			return ret;
		} else if ($.ig.util.cast($.ig.PathGeometry.prototype.$type, data) !== null) {
			return $.ig.AppearanceHelper.prototype.fromPathGeometry(data);
		} else if ($.ig.util.cast($.ig.LineGeometry.prototype.$type, data) !== null) {
			return $.ig.AppearanceHelper.prototype.fromLineGeometry(data);
		} else if ($.ig.util.cast($.ig.RectangleGeometry.prototype.$type, data) !== null) {
			return $.ig.AppearanceHelper.prototype.fromRectangleGeometry(data);
		} else if ($.ig.util.cast($.ig.EllipseGeometry.prototype.$type, data) !== null) {
			return $.ig.AppearanceHelper.prototype.fromEllipseGeometry(data);
		} else {
			throw new $.ig.Error(1, "not supported");
		}
	}
	,
	fromEllipseGeometry: function (ellipseGeometry) {
		var ret = new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
		var newData = new $.ig.EllipseGeometryData();
		ret.add(newData);
		newData.centerX(ellipseGeometry.center().__x);
		newData.centerY(ellipseGeometry.center().__y);
		newData.radiusX(ellipseGeometry.radiusX());
		newData.radiusY(ellipseGeometry.radiusY());
		return ret;
	}
	,
	fromRectangleGeometry: function (rectangleGeometry) {
		var ret = new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
		var newData = new $.ig.RectangleGeometryData();
		ret.add(newData);
		newData.x(rectangleGeometry.rect().x());
		newData.y(rectangleGeometry.rect().y());
		newData.width(rectangleGeometry.rect().width());
		newData.height(rectangleGeometry.rect().height());
		return ret;
	}
	,
	fromLineGeometry: function (lineGeometry) {
		var ret = new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
		var newData = new $.ig.LineGeometryData();
		ret.add(newData);
		newData.x1(lineGeometry.startPoint().__x);
		newData.y1(lineGeometry.startPoint().__y);
		newData.x2(lineGeometry.endPoint().__x);
		newData.y2(lineGeometry.endPoint().__y);
		return ret;
	}
	,
	fromPathGeometry: function (pathGeometry) {
		var ret = new $.ig.List$1($.ig.GeometryData.prototype.$type, 0);
		var newData = new $.ig.PathGeometryData();
		ret.add(newData);
		for (var i = 0; i < pathGeometry.figures().count(); i++) {
			var figure = pathGeometry.figures().__inner[i];
			var f = new $.ig.PathFigureData(1, figure);
			newData.figures().add(f);
		}
		return ret;
	}
	,
	getShapeAppearance: function (appearance, path) {
		appearance.stroke($.ig.AppearanceHelper.prototype.fromBrush(path.__stroke));
		appearance.fill($.ig.AppearanceHelper.prototype.fromBrush(path.__fill));
		appearance.strokeExtended($.ig.AppearanceHelper.prototype.fromBrushExtended(path.__stroke));
		appearance.fillExtended($.ig.AppearanceHelper.prototype.fromBrushExtended(path.__fill));
		appearance.strokeThickness(path.strokeThickness());
		appearance.dashArray(null);
		if (path.strokeDashArray() != null) {
			appearance.dashArray(path.strokeDashArray().asArray());
		}
		appearance.dashCap(path.strokeDashCap());
		appearance.visibility(path.__visibility);
		appearance.opacity(path.__opacity);
		appearance.canvasLeft($.ig.AppearanceHelper.prototype.getCanvasLeft(path));
		appearance.canvasTop($.ig.AppearanceHelper.prototype.getCanvasTop(path));
		appearance.canvaZIndex($.ig.AppearanceHelper.prototype.getCanvasZIndex(path));
	}
	,
	fromTextElement: function (frameworkElement, fontInfo) {
		var lad = new $.ig.LabelAppearanceData();
		var tb = frameworkElement;
		lad.text(tb.text());
		lad.labelBrush($.ig.AppearanceHelper.prototype.fromBrush(tb.fill()));
		lad.labelBrushExtended($.ig.AppearanceHelper.prototype.fromBrushExtended(tb.fill()));
		lad.visibility((tb.__visibility == $.ig.Visibility.prototype.visible) ? true : false);
		lad.opacity(tb.__opacity);
		if (fontInfo != null) {
			if (fontInfo.fontFamily() != null) {
				lad.fontFamily(fontInfo.fontFamily());
			}
			if (!$.ig.util.isNaN(fontInfo.fontSize())) {
				lad.fontSize(fontInfo.fontSize());
			}
			if (fontInfo.fontWeight() != null) {
				lad.fontWeight(fontInfo.fontWeight());
			}
			if (fontInfo.fontStyle() != null) {
				lad.fontStyle(fontInfo.fontStyle());
			}
			if (fontInfo.fontStretch() != null) {
				lad.fontStretch(fontInfo.fontStyle());
			}
		}
		var angle = 0;
		var xForm = tb.renderTransform();
		if ($.ig.util.cast($.ig.RotateTransform.prototype.$type, xForm) !== null) {
			var rt = $.ig.util.cast($.ig.RotateTransform.prototype.$type, xForm);
			angle = rt.angle();
		} else if ($.ig.util.cast($.ig.TransformGroup.prototype.$type, xForm) !== null) {
			var tg = $.ig.util.cast($.ig.TransformGroup.prototype.$type, xForm);
			var en = tg.children().getEnumerator();
			while (en.moveNext()) {
				var child = en.current();
				if ($.ig.util.cast($.ig.RotateTransform.prototype.$type, child) !== null) {
					var rt1 = $.ig.util.cast($.ig.RotateTransform.prototype.$type, child);
					angle = rt1.angle();
					break;
				}
			}
		}
		lad.angle(angle);
		return lad;
	}
	,
	serializeColor: function (color) {
		return "{ r: " + color.r() + ", g: " + color.g() + ", b: " + color.b() + ", a: " + color.a() + "}";
	}
	,
	serializeItems: function (sb, name, items, isFirst) {
		if (items != null) {
			if (!isFirst) {
				sb.append5(", ");
			}
			sb.append5(name);
			sb.append5(": [");
			var addedFirst = false;
			var en = items.getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				if (addedFirst) {
					sb.appendLine1(", ");
				} else {
					addedFirst = true;
				}
				sb.append5(item.serialize());
			}
			sb.appendLine1("]");
			return true;
		}
		return false;
	}
	,
	serializeItem: function (sb, name, item, isFirst) {
		if (item != null) {
			if (!isFirst) {
				sb.append5(", ");
			}
			sb.append5(name);
			sb.append5(": ");
			sb.appendLine1(item.serialize());
			return true;
		}
		return false;
	}
	,
	$type: new $.ig.Type('AppearanceHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.InterpolationMode.prototype.rGB = 0;
$.ig.InterpolationMode.prototype.hSV = 1;

$.ig.BaseDOMEventProxy.prototype.nullTimer = -1;
$.ig.BaseDOMEventProxy.prototype.mSPointerEnabled = false;
$.ig.BaseDOMEventProxy.prototype.pointerEnabled = false;
$.ig.BaseDOMEventProxy.prototype.tridentVersion = 0;
$.ig.BaseDOMEventProxy.prototype.edgeVersion = 0;

$.ig.DOMEventProxy.prototype.__proxyCount = 0;

$.ig.ColorUtil.prototype._r = new $.ig.Random(0);
$.ig.ColorUtil.prototype.__randomColors = null;

$.ig.CssHelper.prototype.defaultMarginValue = "-4321px";
$.ig.CssHelper.prototype.defaultColorValue = "rgb(3, 2, 1)";
$.ig.CssHelper.prototype.defaultBackgroundImageValue = "none";
$.ig.CssHelper.prototype.defaultTextAlignValue = "justify";
$.ig.CssHelper.prototype.defaultVerticalAlignValue = "baseline";
$.ig.CssHelper.prototype.defaultOpacityValue = "0.888";
$.ig.CssHelper.prototype.defaultVisibilityValue = "hidden";
$.ig.CssHelper.prototype.defaultWidthHeightValue = "4321px";
$.ig.CssHelper.prototype.maxClasses = 500;

$.ig.BrushCollection.prototype._random = new $.ig.Random(0);

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["DeferredOperation:a", 
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
"Action:ba", 
"MulticastDelegate:bb", 
"IntPtr:bc", 
"Callback:bd", 
"window:be", 
"TimedOperation:bf", 
"RadialMenuItemBaseCollection:bg", 
"ObservableCollection$1:bh", 
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
"INotifyCollectionChanged:b0", 
"NotifyCollectionChangedEventHandler:b1", 
"NotifyCollectionChangedEventArgs:b2", 
"EventArgs:b3", 
"NotifyCollectionChangedAction:b4", 
"INotifyPropertyChanged:b5", 
"PropertyChangedEventHandler:b6", 
"PropertyChangedEventArgs:b7", 
"Delegate:b8", 
"Interlocked:b9", 
"Action$2:ca", 
"RadialMenuItemBase:cb", 
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
"XamRadialMenu:cz", 
"DataTemplate:c0", 
"DataTemplateRenderHandler:c1", 
"DataTemplateRenderInfo:c2", 
"DataTemplatePassInfo:c3", 
"DataTemplateMeasureHandler:c4", 
"DataTemplateMeasureInfo:c5", 
"DataTemplatePassHandler:c6", 
"EasingFunctionHandler:c7", 
"Panel:c8", 
"UIElementCollection:c9", 
"RadialMenuFrame:da", 
"Ellipse:db", 
"ShapeDefinition:dc", 
"Geometry:dd", 
"GeometryType:de", 
"Point:df", 
"ShapeUtilities:dg", 
"AnnularSector:dh", 
"Annulus:di", 
"PathBuilder:dj", 
"PathGeometry:dk", 
"PathFigureCollection:dl", 
"LiteRect:dm", 
"LiteRectExtensions:dn", 
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
"RadialMenuItemPosition:d0", 
"TriangleShape:d1", 
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
"RadialMenuItemFrame:ei", 
"TextDefinition:ej", 
"RadialMenuItemVisualData:ek", 
"IVisualData:el", 
"PrimitiveVisualData:em", 
"PrimitiveAppearanceData:en", 
"BrushAppearanceData:eo", 
"StringBuilder:ep", 
"Environment:eq", 
"AppearanceHelper:er", 
"LinearGradientBrushAppearanceData:es", 
"GradientStopAppearanceData:et", 
"LinearGradientBrush:eu", 
"GradientStop:ev", 
"SolidBrushAppearanceData:ew", 
"GeometryData:ex", 
"GetPointsSettings:ey", 
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
"EllipseGeometryData:e9", 
"RectangleGeometryData:fa", 
"LineGeometryData:fb", 
"PathGeometryData:fc", 
"PathFigureData:fd", 
"SegmentData:fe", 
"LineSegmentData:ff", 
"PolyLineSegment:fg", 
"PolylineSegmentData:fh", 
"ArcSegmentData:fi", 
"PolyBezierSegmentData:fj", 
"BezierSegment:fk", 
"BezierSegmentData:fl", 
"LabelAppearanceData:fm", 
"FontInfo:fn", 
"TextBlock:fo", 
"RotateTransform:fp", 
"TransformGroup:fq", 
"TransformCollection:fr", 
"ShapeTags:fs", 
"RadialMenuWedgePart:ft", 
"PathVisualData:fu", 
"Debug:fv", 
"Stack$1:fw", 
"ReverseArrayEnumerator$1:fx", 
"RadialMenuItemLevel:fy", 
"RadialMenuItem:fz", 
"OuterRingButtonType:f0", 
"RadialMenuChildItemPlacement:f1", 
"RadialMenuCheckBehavior:f2", 
"XamRadialMenuView:f3", 
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
"EventProxy:ge", 
"ModifierKeys:gf", 
"MouseWheelHandler:gg", 
"GestureHandler:gh", 
"ZoomGestureHandler:gi", 
"FlingGestureHandler:gj", 
"ContactHandler:gk", 
"TouchHandler:gl", 
"MouseOverHandler:gm", 
"MouseHandler:gn", 
"KeyHandler:go", 
"Key:gp", 
"JQuery:gq", 
"JQueryObject:gr", 
"JQueryPosition:gs", 
"JQueryCallback:gt", 
"JQueryEvent:gu", 
"JQueryUICallback:gv", 
"JQueryDeferred:gw", 
"JQueryPromise:gx", 
"CssClassChain$1:gy", 
"Tuple$2:gz", 
"CssProperty:g0", 
"RadialMenuNumericGauge:g1", 
"RadialMenuNumericGaugeFrame:g2", 
"LineSegmentShape:g3", 
"RadialMenuNumericGaugeVisualData:g4", 
"RadialMenuNumericGaugeTickmarkDataList:g5", 
"RadialMenuNumericGaugeNeedleData:g6", 
"PrimitiveVisualDataList:g7", 
"FontUtil:g8", 
"RenderingContext:g9", 
"IRenderer:ha", 
"Rectangle:hb", 
"Polygon:hc", 
"Polyline:hd", 
"ContentControl:he", 
"RadialMenuNumericGaugeTickmarkData:hf", 
"ColorUtil:hg", 
"Random:hh", 
"InterpolationMode:hi", 
"MathUtil:hj", 
"RuntimeHelpers:hk", 
"RuntimeFieldHandle:hl", 
"KeyTipInfo:hm", 
"RadialMenuNumericItem:hn", 
"RadialMenuItemOverlayTemplates:ho", 
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
"EventHandler$1:hz", 
"RadialMenuNumericValueChangedEventArgs:h0", 
"RadialMenuValueChangedEventArgs$1:h1", 
"RadialMenuUpdateVisualResult:h2", 
"RadialMenuUtilities:h3", 
"ColorNameCalculator:h4", 
"DoubleAnimator:h5", 
"RadialMenuItemView:h6", 
"TextContainer:h7", 
"ImageElement:h8", 
"ImageManager:h9", 
"RadialMenuItemToolTipVisualData:ia", 
"CssDpCache:ib", 
"CssPropCacheBase$2:ic", 
"CssHelper:id", 
"CssPropCache$1:ie", 
"RadialMenuPart:ig", 
"DivElement:ih", 
"RadialMenuDOMEventProxy:ii", 
"DOMEventProxy:ij", 
"BaseDOMEventProxy:ik", 
"MouseEventArgs:il", 
"MSGesture:im", 
"Image:io", 
"RadialMenuColorWell:ip", 
"RadialMenuColorItemBase:iq", 
"RadialMenuColorChangedEventArgs:ir", 
"RadialMenuColorItem:is", 
"EventHandler:it", 
"RadialMenuVisualData:iu", 
"RadialMenuItemVisualDataList:iv", 
"ColorWellPreviewDataList:iw", 
"KeyTipManager:ix", 
"RadialMenuPointerArea:iy", 
"RadialMenuInvalidation:iz", 
"StackPool$1:i0", 
"Func$1:i1", 
"RadialMenuPointerAnimationInfo:i2", 
"ColorWellPreviewData:i3", 
"BrushUtil:i4", 
"BrushCollection:i5", 
"CssGradientUtil:i6", 
"Nullable:i7", 
"RadialMenuAnimation:i8", 
"ArgumentOutOfRangeException:i9", 
"BindingExpression:ja", 
"PropertyStep:jb", 
"AbstractEnumerable:je", 
"AbstractEnumerator:jf", 
"GenericEnumerable$1:jg", 
"GenericEnumerator$1:jh"]);


$.ig.util.defType('RadialMenuCheckBehavior', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "CheckBox";
			case 2: return "RadioButton";
			case 3: return "RadioButtonAllowAllUp";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuCheckBehavior', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuChildItemPlacement', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "AsChildren";
			case 1: return "AsSiblingsWhenChecked";
			case 2: return "None";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuChildItemPlacement', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('OuterRingButtonType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "ToolAreaClick";
			case 2: return "NavigateToChildren";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('OuterRingButtonType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuAnimation', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "ExpandCollapse";
			case 2: return "NavigateMenu";
			case 3: return "NavigateToChildColor";
			case 4: return "NavigateToParentColor";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuAnimation', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuPointerArea', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "OuterRingButton";
			case 2: return "ToolArea";
			case 3: return "CenterButton";
			case 4: return "DisabledMenu";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuPointerArea', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuPart', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Menu";
			case 1: return "OuterRing";
			case 2: return "InnerRingFocus";
			case 3: return "InnerRing";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuPart', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuWedgePart', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "OuterRing";
			case 1: return "ToolArea";
			case 2: return "ToolCheckmark";
			case 3: return "ToolHighlight";
			case 4: return "OuterRingArrow";
			case 5: return "ColorWellPreview";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuWedgePart', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuInvalidation', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "InvalidateCustomVisuals";
			case 2: return "RenderFrame";
			case 3: return "PrepareFrame";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuInvalidation', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuUpdateVisualResult', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: (function () {
		function getNameSingle(v) {
			switch (v) {
				case 0: return "NoChange";
				case 1: return "UpdateShapes";
				case 2: return "UpdateTextElements";
				default: return v.toString();
			}
		}
		return function (v) {
			return this.getFlaggedName(v, getNameSingle);
		};
	}()),
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RadialMenuUpdateVisualResult', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('CssProperty', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "BackgroundColor";
			case 1: return "BorderTopColor";
			case 2: return "BorderTopStyle";
			case 3: return "BorderTopWidth";
			case 4: return "BorderTopLeftRadius";
			case 5: return "PaddingTop";
			case 6: return "Color";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('CssProperty', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('DeferredOperation', 'Object', {
	__pendingContext: null,
	__deferredAction: null,
	__isPerformingAction: false,
	init: function (deferredAction) {
		$.ig.Object.prototype.init.call(this);
		this.__deferredAction = deferredAction;
	},
	isOperationPending: function () {
		return this.__pendingContext != null;
	}
	,
	isPerformingAction: function () {
		return this.__isPerformingAction;
	}
	,
	invokePendingOperation: function () {
		if (this.__pendingContext == null) {
			return;
		}
		this.__pendingContext = null;
		var wasPerforming = this.__isPerformingAction;
		this.__isPerformingAction = true;
		try {
			this.__deferredAction();
		}
		finally {
			this.__isPerformingAction = wasPerforming;
		}
	}
	,
	cancelPendingOperation: function () {
		this.__pendingContext = null;
	}
	,
	startAsyncOperation: function () {
		var $self = this;
		if (this.__pendingContext != null) {
			return;
		}
		var token = {};
		this.__pendingContext = token;
		window.setTimeout(function () { $self.onAsyncCallback(token); }, 0);
	}
	,
	onAsyncCallback: function (state) {
		if (state != this.__pendingContext) {
			return;
		}
		this.invokePendingOperation();
	}
	,
	$type: new $.ig.Type('DeferredOperation', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TimedOperation', 'Object', {
	__callback: null,
	init: function (callback, millisecondDelay) {
		$.ig.Object.prototype.init.call(this);
		this.__callback = callback;
		window.setTimeout(this.onTimerTick.runOn(this), millisecondDelay);
	},
	cancel: function () {
		this.__callback = null;
	}
	,
	onTimerTick: function () {
		if (this.__callback != null) {
			var callback = this.__callback;
			this.__callback = null;
			callback();
		}
	}
	,
	$type: new $.ig.Type('TimedOperation', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemBase', 'Control', {
	_dataItem: null,
	staticInit: function () {
	},
	init: function () {
		var $self = this;
		this._dataItem = null;
		this.__isEnabled = true;
		$.ig.Control.prototype.init.call(this);
		this.setBinding($.ig.RadialMenuItemBase.prototype._visibilityProxyProperty, (function () {
			var $ret = new $.ig.Binding(0);
			$ret.path(new $.ig.PropertyPath("Visibility"));
			$ret.source($self);
			return $ret;
		}()));
	},
	autoRotateChildren: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.autoRotateChildrenProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.autoRotateChildrenProperty);
		}
	}
	,
	checkedHighlightBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushProperty);
		}
	}
	,
	checkedHighlightBrushResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushProperty);
	}
	,
	foreground: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.foregroundProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.foregroundProperty);
		}
	}
	,
	highlightBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.highlightBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.highlightBrushProperty);
		}
	}
	,
	highlightBrushResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.highlightBrushProperty);
	}
	,
	innerAreaFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.innerAreaFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.innerAreaFillProperty);
		}
	}
	,
	innerAreaFillResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.innerAreaFillProperty);
	}
	,
	innerAreaHotTrackFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillProperty);
		}
	}
	,
	innerAreaHotTrackStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokeProperty);
		}
	}
	,
	innerAreaStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.innerAreaStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.innerAreaStrokeProperty);
		}
	}
	,
	innerAreaStrokeResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.innerAreaStrokeProperty);
	}
	,
	innerAreaStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessProperty);
		}
	}
	,
	innerAreaStrokeThicknessResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessProperty);
	}
	,
	__isEnabled: false,
	isEnabled: function (value) {
		if (arguments.length === 1) {
			if (value != this.__isEnabled) {
				this.__isEnabled = value;
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.isEnabledPropertyName, !value, value);
			}
			return value;
		} else {
			return this.__isEnabled;
		}
	}
	,
	isToolTipEnabled: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.isToolTipEnabledProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.isToolTipEnabledProperty);
		}
	}
	,
	menu: function () {
		return this.getValue($.ig.RadialMenuItemBase.prototype.menuProperty);
	}
	,
	outerRingButtonHotTrackFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillProperty);
		}
	}
	,
	outerRingButtonHotTrackFillResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillProperty);
	}
	,
	outerRingButtonHotTrackForeground: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundProperty);
		}
	}
	,
	outerRingButtonHotTrackForegroundResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundProperty);
	}
	,
	outerRingButtonHotTrackStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokeProperty);
		}
	}
	,
	outerRingButtonHotTrackStrokeResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokeProperty);
	}
	,
	outerRingButtonFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonFillProperty);
		}
	}
	,
	outerRingButtonFillResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonFillProperty);
	}
	,
	outerRingButtonForeground: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundProperty);
		}
	}
	,
	outerRingButtonForegroundResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundProperty);
	}
	,
	outerRingButtonStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeProperty);
		}
	}
	,
	outerRingButtonStrokeResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeProperty);
	}
	,
	outerRingButtonStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessProperty);
		}
	}
	,
	outerRingButtonStrokeThicknessResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessProperty);
	}
	,
	parentItem: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.parentItemProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.parentItemProperty);
		}
	}
	,
	toolTip: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.toolTipProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItemBase.prototype.toolTipProperty);
		}
	}
	,
	wedgeIndex: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.wedgeIndexProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.RadialMenuItemBase.prototype.wedgeIndexProperty));
		}
	}
	,
	wedgeSpan: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItemBase.prototype.wedgeSpanProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.RadialMenuItemBase.prototype.wedgeSpanProperty));
		}
	}
	,
	customDropDownContent: function () {
		return null;
	}
	,
	dataItemResolved: function () {
		var di = this._dataItem;
		if (di == null) {
			di = this;
		}
		return di;
	}
	,
	displayAsChecked: function () {
		return false;
	}
	,
	displaysCustomDropDown: function () {
		return false;
	}
	,
	innerAreaHotTrackFillResolved: function () {
		var b = this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillProperty);
		if (b == null) {
			b = this.innerAreaFillResolved();
		}
		return b;
	}
	,
	innerAreaHotTrackStrokeResolved: function () {
		var b = this.getResolvedProperty($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokeProperty);
		if (b == null) {
			b = this.innerAreaStrokeResolved();
		}
		return b;
	}
	,
	_isHotTracked: false,
	isHotTracked: function (value) {
		if (arguments.length === 1) {
			this._isHotTracked = value;
			return value;
		} else {
			return this._isHotTracked;
		}
	}
	,
	_isMouseDown: false,
	isMouseDown: function (value) {
		if (arguments.length === 1) {
			this._isMouseDown = value;
			return value;
		} else {
			return this._isMouseDown;
		}
	}
	,
	itemContainers: function () {
		return null;
	}
	,
	outerRingButtonType: function () {
		return $.ig.OuterRingButtonType.prototype.none;
	}
	,
	toolTipResolved: function () {
		var tt = this.toolTip();
		if (tt != null) {
			return tt;
		}
		return null;
	}
	,
	calculateChildAngleAdjustment: function (itemPosition, childPositions) {
		return NaN;
	}
	,
	createCustomVisuals: function (itemPos, frame) {
		return null;
	}
	,
	createVisualData: function () {
		return new $.ig.RadialMenuItemVisualData();
	}
	,
	getKeyTips: function (itemPos) {
		return null;
	}
	,
	getResolvedProperty: function (property) {
		var value = this.readLocalValue(property);
		if (value == $.ig.DependencyProperty.prototype.unsetValue) {
			if (this.menu() != null) {
				value = this.menu().view().getDefaultItemValue(property);
			} else {
				value = this.getValue(property);
			}
		}
		return value;
	}
	,
	getContainerForItem: function (dataItem) {
		if (dataItem != null && dataItem == this.dataItemResolved()) {
			return this;
		}
		return null;
	}
	,
	isAncestorOf: function (descendant) {
		while (descendant != null) {
			descendant = descendant.parentItem();
			if (descendant == this) {
				return true;
			}
		}
		return false;
	}
	,
	navigateToFirstLast: function (first) {
		return false;
	}
	,
	onClick: function () {
		if (this.menu() != null) {
			this.menu().onItemClicked(this);
		}
	}
	,
	onItemClosed: function () {
		var handler = this.closed;
		if (null != handler) {
			handler(this, $.ig.EventArgs.prototype.empty);
		}
	}
	,
	onItemOpened: function () {
		var handler = this.opened;
		if (null != handler) {
			handler(this, $.ig.EventArgs.prototype.empty);
		}
	}
	,
	onItemOpening: function () {
		return true;
	}
	,
	onMouseMove: function (angleForMenuRect, menuRect, itemPos, relativePoint) {
	}
	,
	onPropertyChangedImpl: function (propertyName, oldValue, newValue) {
		if (oldValue == newValue) {
			return;
		}
		this.onPropertyChanged(propertyName, oldValue, newValue);
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		var handler = this.propertyChanged;
		if (null != handler) {
			handler(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	processNextPrevious: function (next, wrap) {
		return false;
	}
	,
	processTabKey: function (forward) {
		return false;
	}
	,
	setMenu: function (menu) {
		this.setValue($.ig.RadialMenuItemBase.prototype.menuProperty, menu);
	}
	,
	updateCustomVisuals: function (itemPos, frame, itemFrame) {
		return $.ig.RadialMenuUpdateVisualResult.prototype.noChange;
	}
	,
	closed: null,
	opened: null,
	propertyChanged: null,
	$type: new $.ig.Type('RadialMenuItemBase', $.ig.Control.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuItemBaseCollection', 'ObservableCollection$1', {
	__itemCallback: null,
	init: function (itemCallback) {
		$.ig.ObservableCollection$1.prototype.init.call(this, $.ig.RadialMenuItemBase.prototype.$type, 0);
		this.__itemCallback = itemCallback;
	},
	addItem: function (newItem) {
		this.__itemCallback(newItem, true);
		$.ig.ObservableCollection$1.prototype.addItem.call(this, newItem);
	}
	,
	clearItems: function () {
		var en = this.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			this.__itemCallback(item, false);
		}
		$.ig.ObservableCollection$1.prototype.clearItems.call(this);
	}
	,
	insertItem: function (index, newItem) {
		this.__itemCallback(newItem, true);
		$.ig.ObservableCollection$1.prototype.insertItem.call(this, index, newItem);
	}
	,
	removeItem: function (index) {
		var item = this.__inner[index];
		this.__itemCallback(item, false);
		$.ig.ObservableCollection$1.prototype.removeItem.call(this, index);
	}
	,
	setItem: function (index, newItem) {
		var oldItem = this.__inner[index];
		this.__itemCallback(oldItem, false);
		this.__itemCallback(newItem, true);
		$.ig.ObservableCollection$1.prototype.setItem.call(this, index, newItem);
	}
	,
	$type: new $.ig.Type('RadialMenuItemBaseCollection', $.ig.ObservableCollection$1.prototype.$type.specialize($.ig.RadialMenuItemBase.prototype.$type))
}, true);

$.ig.util.defType('CssClassChain$1', 'Object', {
	$t: null,
	_classes: null,
	_parent: null,
	_children: null,
	_properties: null,
	init: function ($t, classes) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this._classes = classes;
		this._children = new $.ig.List$1($.ig.CssClassChain$1.prototype.$type.specialize(this.$t), 0);
		this._properties = new $.ig.Dictionary$2(this.$t, $.ig.Tuple$2.prototype.$type.specialize($.ig.CssProperty.prototype.$type, String), 0);
	},
	addChildClass: function (cssClasses) {
		var child = new $.ig.CssClassChain$1(this.$t, cssClasses);
		child._parent = this;
		this._children.add(child);
		return child;
	}
	,
	addProperty: function (propId, cssProp, defaultValue) {
		this._properties.item(propId, new $.ig.Tuple$2($.ig.CssProperty.prototype.$type, String, cssProp, defaultValue));
		return this;
	}
	,
	$type: new $.ig.Type('CssClassChain$1', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamRadialMenu', 'Control', {
	__outerPathCanvas: null,
	__itemPanel: null,
	__itemCustomCanvas: null,
	__itemPathCanvas: null,
	__centerButtonPanel: null,
	__menuPath: null,
	__outerRingPath: null,
	__innerRingPath: null,
	__innerRingFocusPath: null,
	__currentFrame: null,
	__wedgePaths: null,
	__wedgePools: null,
	__pointerAnimations: null,
	__invalidation: 0,
	__levels: null,
	__customWedgePaths: null,
	__customWedgePool: null,
	__textBlocks: null,
	__textBlockPool: null,
	__hotTrackArea: 0,
	__hotTrackItem: null,
	__lastMovePoint: null,
	__isInitialized: false,
	__forceDisableCenterButton: false,
	__navigateToChildrenOnLeaveCenter: false,
	__mouseDownToken: null,
	__mouseDownCenterButtonTimer: null,
	__expandCollapseAnimator: null,
	__navigationAnimator: null,
	__leaveAnimator: null,
	__previousLevelForAnimation: null,
	__keyTipManager: null,
	__hideToolTipTimer: null,
	__items: null,
	staticInit: function () {
	},
	init: function () {
		var $self = this;
		this.__invalidation = $.ig.RadialMenuInvalidation.prototype.prepareFrame;
		this.__viewport = new $.ig.LiteRect(1, 0, 0, 0, 0);
		$.ig.Control.prototype.init.call(this);
		this.__items = new $.ig.RadialMenuItemBaseCollection(function (mi, added) { mi.setMenu(added ? $self : null); });
		this.__outerPathCanvas = new $.ig.Panel();
		this.__itemPathCanvas = new $.ig.Panel();
		this.__itemCustomCanvas = new $.ig.Panel();
		this.__itemPanel = new $.ig.Panel();
		this.__centerButtonPanel = new $.ig.Panel();
		this.__outerPathCanvas.canvasZIndex(100);
		this.__itemPathCanvas.canvasZIndex(200);
		this.__itemCustomCanvas.canvasZIndex(300);
		this.__itemPanel.canvasZIndex(400);
		this.__centerButtonPanel.canvasZIndex(500);
		this.__keyTipManager = new $.ig.KeyTipManager(this);
		this.__wedgePaths = new $.ig.Dictionary$2($.ig.RadialMenuWedgePart.prototype.$type, $.ig.List$1.prototype.$type.specialize($.ig.Path.prototype.$type), 0);
		this.__wedgePools = new $.ig.Dictionary$2($.ig.RadialMenuWedgePart.prototype.$type, $.ig.StackPool$1.prototype.$type.specialize($.ig.Path.prototype.$type), 0);
		this.__pointerAnimations = new $.ig.List$1($.ig.RadialMenuPointerAnimationInfo.prototype.$type, 0);
		this.__currentFrame = new $.ig.RadialMenuFrame();
		this.__levels = new $.ig.Stack$1($.ig.RadialMenuItemLevel.prototype.$type);
		var view = new $.ig.XamRadialMenuView(this);
		this.__customWedgePaths = new $.ig.List$1($.ig.Path.prototype.$type, 0);
		this.__customWedgePool = (function () {
			var $ret = new $.ig.StackPool$1($.ig.Path.prototype.$type);
			$ret.activate(view.pathActivate.runOn(view));
			$ret.deactivate(view.pathDeactivate.runOn(view));
			$ret.create(function () { return view.customPathCreate(); });
			$ret.destroy(function (p) { view.customPathDestroy(p); });
			return $ret;
		}());
		this.__textBlocks = new $.ig.List$1($.ig.TextBlock.prototype.$type, 0);
		this.__textBlockPool = (function () {
			var $ret = new $.ig.StackPool$1($.ig.TextBlock.prototype.$type);
			$ret.activate(view.labelActivate.runOn(view));
			$ret.deactivate(view.labelDeactivate.runOn(view));
			$ret.create(view.labelCreate.runOn(view));
			$ret.destroy(view.labelDestroy.runOn(view));
			return $ret;
		}());
		this.view(view);
		this.__menuPath = view.pathCreate($.ig.RadialMenuPart.prototype.menu);
		this.__outerRingPath = view.pathCreate($.ig.RadialMenuPart.prototype.outerRing);
		this.__innerRingPath = view.pathCreate($.ig.RadialMenuPart.prototype.innerRing);
		this.__innerRingFocusPath = view.pathCreate($.ig.RadialMenuPart.prototype.innerRingFocus);
		view.onInit();
		this.__levels.push(new $.ig.RadialMenuItemLevel(this, null, null, view.rootItems(), null));
		view.scheduleArrange();
	},
	centerButtonContentWidth: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonContentWidthProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonContentWidthProperty);
		}
	}
	,
	centerButtonContentHeight: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonContentHeightProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonContentHeightProperty);
		}
	}
	,
	centerButtonClosedFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonClosedFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonClosedFillProperty);
		}
	}
	,
	centerButtonClosedStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonClosedStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonClosedStrokeProperty);
		}
	}
	,
	centerButtonFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonFillProperty);
		}
	}
	,
	centerButtonHotTrackFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonHotTrackFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonHotTrackFillProperty);
		}
	}
	,
	centerButtonHotTrackStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokeProperty);
		}
	}
	,
	centerButtonContent: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonContentProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonContentProperty);
		}
	}
	,
	centerButtonKeyTip: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonKeyTipProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonKeyTipProperty);
		}
	}
	,
	centerButtonStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonStrokeProperty);
		}
	}
	,
	centerButtonStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessProperty);
		}
	}
	,
	currentOpenMenuItem: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.currentOpenMenuItemProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.currentOpenMenuItemProperty);
		}
	}
	,
	font: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.fontProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.fontProperty);
		}
	}
	,
	isOpen: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.isOpenProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.isOpenProperty);
		}
	}
	,
	items: function () {
		return this.__items;
	}
	,
	keyTipTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.keyTipTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.keyTipTemplateProperty);
		}
	}
	,
	menuBackground: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.menuBackgroundProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.menuBackgroundProperty);
		}
	}
	,
	menuItemOpenCloseAnimationDuration: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationProperty));
		}
	}
	,
	menuItemOpenCloseAnimationEasingFunction: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionProperty);
		}
	}
	,
	menuOpenCloseAnimationDuration: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationProperty));
		}
	}
	,
	menuOpenCloseAnimationEasingFunction: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionProperty);
		}
	}
	,
	minWedgeCount: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.minWedgeCountProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.XamRadialMenu.prototype.minWedgeCountProperty));
		}
	}
	,
	outerRingFill: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.outerRingFillProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.outerRingFillProperty);
		}
	}
	,
	outerRingThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.outerRingThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.outerRingThicknessProperty);
		}
	}
	,
	outerRingStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.outerRingStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.outerRingStrokeProperty);
		}
	}
	,
	outerRingStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.outerRingStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.outerRingStrokeThicknessProperty);
		}
	}
	,
	rotationInDegrees: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.rotationInDegreesProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.rotationInDegreesProperty);
		}
	}
	,
	rotationAsPercentageOfWedge: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgeProperty);
		}
	}
	,
	wedgePaddingInDegrees: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamRadialMenu.prototype.wedgePaddingInDegreesProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamRadialMenu.prototype.wedgePaddingInDegreesProperty);
		}
	}
	,
	allowAnimations: function () {
		return true;
	}
	,
	centerButtonPanel: function () {
		return this.__centerButtonPanel;
	}
	,
	currentFrame: function () {
		return this.__currentFrame;
	}
	,
	customItemPathPanel: function () {
		return this.__itemCustomCanvas;
	}
	,
	isDisplayingCustomDropDown: function () {
		return this.__levels.peek().customDropDownContent() != null;
	}
	,
	itemPanel: function () {
		return this.__itemPanel;
	}
	,
	itemPathPanel: function () {
		return this.__itemPathCanvas;
	}
	,
	levelCount: function () {
		return this.__levels.count();
	}
	,
	outerPathPanel: function () {
		return this.__outerPathCanvas;
	}
	,
	_view: null,
	view: function (value) {
		if (arguments.length === 1) {
			this._view = value;
			return value;
		} else {
			return this._view;
		}
	}
	,
	__viewport: null,
	viewport: function (value) {
		if (arguments.length === 1) {
			var oldViewport = this.__viewport;
			this.__viewport = value;
			if (!$.ig.LiteRectExtensions.prototype.isEqual(oldViewport, this.__viewport)) {
				this.onViewportChanged(oldViewport, this.__viewport);
			}
			return value;
		} else {
			return this.__viewport;
		}
	}
	,
	onViewportChanged: function (oldViewport, newViewport) {
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
	}
	,
	areKeyTipsDisplayed: function () {
		return this.__keyTipManager.hasKeyTips();
	}
	,
	shouldDisplayCenterFocusRing: function () {
		return this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.centerButton && !this.areKeyTipsDisplayed() && this.view().isFocused();
	}
	,
	exportVisualData: function () {
		var $self = this;
		var frame = this.__currentFrame;
		var vd = new $.ig.RadialMenuVisualData();
		vd.menuArea(new $.ig.PathVisualData(1, "MenuArea", this.__menuPath));
		vd.outerRing(new $.ig.PathVisualData(1, "OuterRing", this.__outerRingPath));
		vd.innerRing(new $.ig.PathVisualData(1, "InnerRing", this.__innerRingPath));
		vd.innerRingFocus(new $.ig.PathVisualData(1, "InnerRingFocus", this.__innerRingFocusPath));
		vd.itemOpacity(this.__itemPanel.__opacity);
		vd.outerPathOpacity(this.__outerPathCanvas.__opacity);
		vd.viewport($.ig.LiteRectExtensions.prototype.toRect(this.viewport()));
		var tempItemFrame;
		var itemMap = new $.ig.Dictionary$2($.ig.RadialMenuItemBase.prototype.$type, $.ig.RadialMenuItemVisualData.prototype.$type, 0);
		vd.items(new $.ig.RadialMenuItemVisualDataList());
		var en = frame.visibleItems().getEnumerator();
		while (en.moveNext()) {
			var itemPos = en.current();
			var itemData = itemPos.item().createVisualData();
			itemData.type(itemPos.item().getType().typeName());
			itemData.name(itemPos.item().name());
			itemMap.item(itemPos.item(), itemData);
			if ((function () { var $ret = frame.customItemFrames().tryGetValue(itemPos, tempItemFrame); tempItemFrame = $ret.p1; return $ret.ret; }())) {
				tempItemFrame.initializeData(itemData, function (s) {
					for (var i = 0, count = $self.__customWedgePaths.count(); i < count; i++) {
						if ($self.__customWedgePaths.__inner[i].dataContext() == s) {
							return $self.__customWedgePaths.__inner[i];
						}
					}
					return null;
				}, function (t) {
					for (var i = 0, count = $self.__textBlocks.count(); i < count; i++) {
						if ($self.__textBlocks.__inner[i].dataContext() == t) {
							return $self.__textBlocks.__inner[i];
						}
					}
					return null;
				});
			}
			vd.items().add(itemData);
		}
		this.exportWedgePartVisualData($.ig.RadialMenuWedgePart.prototype.outerRing, frame, itemMap);
		this.exportWedgePartVisualData($.ig.RadialMenuWedgePart.prototype.outerRingArrow, frame, itemMap);
		this.exportWedgePartVisualData($.ig.RadialMenuWedgePart.prototype.toolArea, frame, itemMap);
		this.exportWedgePartVisualData($.ig.RadialMenuWedgePart.prototype.toolCheckmark, frame, itemMap);
		this.exportWedgePartVisualData($.ig.RadialMenuWedgePart.prototype.toolHighlight, frame, itemMap);
		var previewPaths;
		if ((function () { var $ret = $self.__wedgePaths.tryGetValue($.ig.RadialMenuWedgePart.prototype.colorWellPreview, previewPaths); previewPaths = $ret.p1; return $ret.ret; }())) {
			vd.colorWellPreviews(new $.ig.ColorWellPreviewDataList());
			var tempItem;
			var en1 = previewPaths.getEnumerator();
			while (en1.moveNext()) {
				var path = en1.current();
				var shape = $.ig.util.cast($.ig.ShapeDefinition.prototype.$type, path.dataContext());
				$.ig.Debug.prototype.assert1(null != shape, "Expected to have the associated shape");
				if ((function () { var $ret = frame.itemShapes().tryGetValue(shape, tempItem); tempItem = $ret.p1; return $ret.ret; }())) {
					var colorWell = $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, shape._context);
					var previewData = new $.ig.ColorWellPreviewData();
					previewData.type(colorWell.getType().typeName());
					previewData.name(colorWell.name());
					previewData.colorValue(colorWell.color());
					previewData.previewPath(new $.ig.PathVisualData(1, "ColorWellPreview", path));
					vd.colorWellPreviews().add(previewData);
				}
			}
		}
		this.view().exportVisualData(vd);
		return vd;
	}
	,
	exportWedgePartVisualData: function (part, frame, visualDatas) {
		var $self = this;
		var currentPaths;
		if (!(function () { var $ret = $self.__wedgePaths.tryGetValue(part, currentPaths); currentPaths = $ret.p1; return $ret.ret; }())) {
			return;
		}
		var tempItem;
		var tempData;
		var en = currentPaths.getEnumerator();
		while (en.moveNext()) {
			var path = en.current();
			var shape = $.ig.util.cast($.ig.ShapeDefinition.prototype.$type, path.dataContext());
			$.ig.Debug.prototype.assert1(null != shape, "Expected to have the associated shape");
			if (shape != null) {
				if ((function () { var $ret = frame.itemShapes().tryGetValue(shape, tempItem); tempItem = $ret.p1; return $ret.ret; }())) {
					if ((function () { var $ret = visualDatas.tryGetValue(tempItem, tempData); tempData = $ret.p1; return $ret.ret; }())) {
						tempData.initializePath(part, path);
					}
				}
			}
		}
	}
	,
	provideContainer: function (container) {
		this.view().onContainerProvided(container);
	}
	,
	containerResized: function () {
		this.view().onContainerResized();
	}
	,
	destroy: function () {
		this.view().onContainerProvided(null);
	}
	,
	styleUpdated: function () {
		this.view().styleUpdated();
	}
	,
	flush: function () {
		this.view().flush();
	}
	,
	dirtyLayout: function () {
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
	}
	,
	displayKeyTips: function () {
		var $self = this;
		var keyTips = this.__keyTipManager.keyTips();
		keyTips.clear();
		keyTips.add((function () {
			var $ret = new $.ig.KeyTipInfo();
			$ret._keyTip = $self.centerButtonKeyTip();
			$ret.action(function (k) { $self.processCenterButtonClick(); });
			$ret.positionCalculator($self.calculateCenterKeyTipPos.runOn($self));
			return $ret;
		}()));
		if (this.isOpen()) {
			var level = this.__levels.peek();
			var itemPos = level.getNextItem(null, true);
			while (itemPos != null) {
				var item = itemPos.item();
				var itemKeyTips = item.getKeyTips(itemPos);
				var action = null;
				if (itemKeyTips != null) {
					keyTips.addRange(itemKeyTips);
				} else if (this.canNavigateToChildren(item)) {
					action = function (k) { $self.navigateToItem((k.tag()).item()); };
				} else if (item.isEnabled()) {
					action = function (k) {
						$self.processItemClick((k.tag()).item());
					};
				}
				if (action != null) {
					var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, item);
					keyTips.add((function () {
						var $ret = new $.ig.KeyTipInfo();
						$ret._keyTip = mi != null ? mi.keyTip() : null;
						$ret.action(action);
						$ret.tag(itemPos);
						$ret.positionCalculator($self.calculateAngleKeyTipPos.runOn($self));
						return $ret;
					}()));
				}
				itemPos = level.getNextItem(item, true);
			}
		}
		this.__keyTipManager.prepareKeyTips();
		this.verifyFocusRingBorder();
		this.view().hideToolTip();
		this.view().showKeyTips(keyTips);
	}
	,
	hideKeyTips: function () {
		if (this.areKeyTipsDisplayed()) {
			this.__keyTipManager.reset();
			this.view().hideKeyTips();
			this.verifyFocusRingBorder();
			if ($.ig.Point.prototype.l_op_Inequality(this.__lastMovePoint, null)) {
				var pt = this.__lastMovePoint;
				this.__lastMovePoint = null;
				this.onMouseMove(pt);
			}
		}
	}
	,
	isOverMenu: function (relativePoint) {
		var shapeDefinition;
		if (this.isOpen()) {
			shapeDefinition = this.__currentFrame.menuArea();
		} else {
			shapeDefinition = this.__currentFrame.innerRingFocus();
		}
		return shapeDefinition.hitTest(relativePoint);
	}
	,
	onCustomDropDownClosed: function (customDropDownContent) {
		if (this.__levels.peek().customDropDownContent() == customDropDownContent) {
			this.navigateToParent();
		}
	}
	,
	onGotFocus: function () {
		{
			switch (this.__hotTrackArea) {
				case $.ig.RadialMenuPointerArea.prototype.none:
				case $.ig.RadialMenuPointerArea.prototype.disabledMenu:
					if (!this.isDisplayingCustomDropDown()) {
						this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, true, false);
					}
					break;
				case $.ig.RadialMenuPointerArea.prototype.centerButton:
					this.verifyFocusRingBorder();
					this.updateCenterRingBrushes();
					break;
			}
		}
	}
	,
	onItemClicked: function (item) {
		if (item != null) {
			this.hideKeyTips();
			this.view().restoreFocus();
			if (this.isOpen()) {
				this.view().showToolTip(item);
			}
			if (this.__hideToolTipTimer == null) {
				this.__hideToolTipTimer = new $.ig.TimedOperation(this.onHideToolTipDelayExpired.runOn(this), $.ig.XamRadialMenu.prototype._clickToolTipHideDelay);
			}
		}
	}
	,
	onKeyPress: function (text) {
		if (this.__keyTipManager.hasKeyTips()) {
			return this.__keyTipManager.processKeyPress(text);
		}
		return false;
	}
	,
	onKeyUp: function (key, modifiers) {
		if (this.areKeyTipsDisplayed()) {
			switch (key) {
				case $.ig.Key.prototype.down:
				case $.ig.Key.prototype.up:
				case $.ig.Key.prototype.left:
				case $.ig.Key.prototype.right:
				case $.ig.Key.prototype.home:
				case $.ig.Key.prototype.end:
				case $.ig.Key.prototype.tab:
					this.hideKeyTips();
					this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, false, false);
					break;
			}
		}
		switch (key) {
			case $.ig.Key.prototype.escape:
				if (this.__levels.count() > 1) {
					this.navigateToParent();
				} else {
					this.hideKeyTips();
					this.view().restoreFocus();
				}
				break;
			case $.ig.Key.prototype.space:
			case $.ig.Key.prototype.enter:
				if (!this.isDisplayingCustomDropDown()) {
					this.processClick(this.__hotTrackArea, this.__hotTrackItem, false);
				}
				break;
			case $.ig.Key.prototype.tab:
				if (!this.isDisplayingCustomDropDown()) {
					this.processTabKey(modifiers);
				}
				break;
			case $.ig.Key.prototype.up:
			case $.ig.Key.prototype.down:
				if (!this.isDisplayingCustomDropDown()) {
					this.navigateNextPrevious(this.__hotTrackItem, key == $.ig.Key.prototype.up);
				}
				break;
			case $.ig.Key.prototype.left:
				if (this.isOpen()) {
					this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, false, false);
					this.processCenterButtonClick();
				}
				break;
			case $.ig.Key.prototype.right:
				if (this.__hotTrackItem != null && this.canNavigateToChildren(this.__hotTrackItem)) {
					this.navigateToItem(this.__hotTrackItem);
				}
				break;
			default:
				if (this.view().isAltKey(key)) {
					if (this.areKeyTipsDisplayed()) {
						this.hideKeyTips();
						this.view().restoreFocus();
					} else if (!this.isDisplayingCustomDropDown()) {
						this.displayKeyTips();
					}
					break;
				}
				return false;
		}
		return true;
	}
	,
	onLostFocus: function () {
		this.hideKeyTips();
		this.verifyFocusRingBorder();
		this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.none, false, false);
	}
	,
	onMouseEnter: function (relativePoint) {
		if (this.__expandCollapseAnimator != null && this.areKeyTipsDisplayed() && this.view().isFocused()) {
			this.__lastMovePoint = relativePoint;
			return;
		}
		this.onMouseMove(relativePoint);
	}
	,
	onMouseDown: function (relativePoint, token) {
		$.ig.Debug.prototype.assert1(this.__mouseDownToken == null, "We already have a token? How should we behave?");
		$.ig.Debug.prototype.assert1(token != null, "We need something to identify the pointer");
		this.onMouseMove(relativePoint);
		this.__mouseDownToken = token;
		if (this.__hotTrackItem != null && this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.toolArea) {
			this.__hotTrackItem.isMouseDown(true);
		}
		this.__navigateToChildrenOnLeaveCenter = this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.centerButton;
		this.startCenterButtonTimer();
	}
	,
	onCancelMouseCapture: function (relativePoint, token) {
		this.processMouseUp(relativePoint, token, true);
	}
	,
	onMouseUp: function (relativePoint, token) {
		this.processMouseUp(relativePoint, token, false);
	}
	,
	onMouseMove: function (relativePoint) {
		if (this.isDisplayingCustomDropDown()) {
			return;
		}
		if ($.ig.Point.prototype.l_op_Inequality(this.__lastMovePoint, null) && relativePoint.__x == (this.__lastMovePoint).__x && relativePoint.__y == (this.__lastMovePoint).__y && this.view().isFocused()) {
			return;
		}
		this.hideKeyTips();
		this.__lastMovePoint = relativePoint;
		var hitResult = this.hitTest(relativePoint, !this.__navigateToChildrenOnLeaveCenter);
		var item = hitResult.item1();
		this.setHotTrackArea(item, hitResult.item2(), false, false);
		if (item != null && item.isHotTracked()) {
			var center = this.__currentFrame.center();
			var angleMenuRect = $.ig.ShapeUtilities.prototype.radiansFromPoint(center, this.__currentFrame.menuRect().width / 2, this.__currentFrame.menuRect().height / 2, relativePoint);
			var itemPos = this.__currentFrame.getItemPosition(item);
			item.onMouseMove(angleMenuRect, this.__currentFrame.menuRect(), itemPos, relativePoint);
		}
	}
	,
	onMouseLeave: function () {
		this.__lastMovePoint = null;
		this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.none, false, false);
	}
	,
	setIsOpen: function (newValue) {
		this.isOpen(newValue);
	}
	,
	updateView: function () {
		var invalidation = this.__invalidation;
		this.__invalidation = $.ig.RadialMenuInvalidation.prototype.none;
		var renderFrame = invalidation == $.ig.RadialMenuInvalidation.prototype.renderFrame || invalidation == $.ig.RadialMenuInvalidation.prototype.prepareFrame || this.view().shouldUpdateRender();
		if (invalidation == $.ig.RadialMenuInvalidation.prototype.prepareFrame) {
			if (this.__pointerAnimations.count() > 0) {
				this.__pointerAnimations.clear();
				this.stopLeaveAnimations();
				this.updateCenterRingBrushes();
			}
			var level = this.__levels.peek();
			var animation = $.ig.RadialMenuAnimation.prototype.none;
			var animationPercent = 1;
			if (this.__expandCollapseAnimator != null) {
				animation = $.ig.RadialMenuAnimation.prototype.expandCollapse;
				animationPercent = this.__expandCollapseAnimator.transitionProgress();
			} else if (this.__navigationAnimator != null) {
				animationPercent = this.__navigationAnimator.transitionProgress();
				var currentLevel = this.__levels.peek();
				var oldLevel = this.__previousLevelForAnimation;
				if (oldLevel != null && oldLevel.parentLevel() == currentLevel && $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, oldLevel.parent()) !== null) {
					animation = $.ig.RadialMenuAnimation.prototype.navigateToParentColor;
				} else if (oldLevel != null && currentLevel.parentLevel() == oldLevel && $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, currentLevel.parent()) !== null) {
					animation = $.ig.RadialMenuAnimation.prototype.navigateToChildColor;
				} else {
					animation = $.ig.RadialMenuAnimation.prototype.navigateMenu;
				}
			}
			this.prepareFrame(this.__currentFrame, level, animation, animationPercent, this.__previousLevelForAnimation);
		} else {
			var invalidatedVisuals = this.__currentFrame.invalidatedCustomVisuals();
			var customItemFrames = this.__currentFrame.customItemFrames();
			var en = invalidatedVisuals.getEnumerator();
			while (en.moveNext()) {
				var itemPos = en.current();
				var itemFrame;
				if ((function () { var $ret = customItemFrames.tryGetValue(itemPos, itemFrame); itemFrame = $ret.p1; return $ret.ret; }())) {
					var change = itemPos.item().updateCustomVisuals(itemPos, this.__currentFrame, itemFrame);
					if (change != $.ig.RadialMenuUpdateVisualResult.prototype.noChange) {
						if (invalidation == $.ig.RadialMenuInvalidation.prototype.none) {
							invalidation = $.ig.RadialMenuInvalidation.prototype.invalidateCustomVisuals;
						}
						if ((change & $.ig.RadialMenuUpdateVisualResult.prototype.updateTextElements) != 0) {
							this.updateTextBlockPosition(itemFrame.textBlocks());
						}
					}
				}
			}
			invalidatedVisuals.clear();
		}
		if (renderFrame) {
			if (invalidation == $.ig.RadialMenuInvalidation.prototype.invalidateCustomVisuals) {
				this.initializeCustomPaths(this.__currentFrame);
				this.initializeCustomText(this.__currentFrame);
			} else {
				this.renderFrame(this.__currentFrame);
			}
			this.view().arrangeComplete();
		}
	}
	,
	addHighlightArc: function (slot, frame) {
		var item = slot.item();
		var highlightThickness = $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, item) !== null ? 3 : 2;
		var highlightBrush = item.highlightBrushResolved();
		var outerRing = frame.outerRing();
		var toolHighlight = $.ig.ShapeUtilities.prototype.createAnnularSector(frame.menuRect(), outerRing._innerRadiusX - $.ig.XamRadialMenu.prototype._highlightInset, outerRing._innerRadiusY - $.ig.XamRadialMenu.prototype._highlightInset, outerRing._innerRadiusX - ($.ig.XamRadialMenu.prototype._highlightInset + highlightThickness), outerRing._innerRadiusY - ($.ig.XamRadialMenu.prototype._highlightInset + highlightThickness), slot._startAngle + $.ig.XamRadialMenu.prototype._sectorInset, slot._endAngle - $.ig.XamRadialMenu.prototype._sectorInset);
		toolHighlight.fill(highlightBrush);
		frame.toolHighlightArcs().add(toolHighlight);
		frame.itemShapes().item(toolHighlight, item);
	}
	,
	calculateAngleKeyTipPos: function (keyTipSize, keyTip) {
		var itemPos = $.ig.util.cast($.ig.RadialMenuItemPosition.prototype.$type, keyTip.tag());
		var angle = itemPos._startAngle + (itemPos._endAngle - itemPos._startAngle) / 2;
		var pt = $.ig.ShapeUtilities.prototype.getPointOnEllipse(this.__currentFrame.menuRect(), angle);
		return { __x: pt.__x - keyTipSize.width() / 2, __y: pt.__y - keyTipSize.height() / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	calculateCenterKeyTipPos: function (keyTipSize, keytip) {
		var sz = this.view().getCenterButtonSize();
		var pt = this.__currentFrame.center();
		return { __x: pt.__x - keyTipSize.width() / 2, __y: pt.__y - sz.height() / 2 - keyTipSize.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	calculatePreferredMenuSize: function (constraint) {
		if (Number.isInfinity(constraint.width()) || Number.isInfinity(constraint.height())) {
			var DefaultToolAreaSpace = 100;
			var centerButtonSize = this.view().getCenterButtonSize();
			var centerButtonRect = $.ig.ShapeUtilities.prototype.getBoundingEllipse(new $.ig.LiteRect(1, 0, 0, centerButtonSize.width(), centerButtonSize.height()));
			if (Number.isInfinity(constraint.width())) {
				constraint.width((this.outerRingThickness() + $.ig.XamRadialMenu.prototype._centerButtonFocusSpace + DefaultToolAreaSpace + $.ig.XamRadialMenu.prototype._toolAreaInnerSpace) * 2 + centerButtonRect.width);
			}
			if (Number.isInfinity(constraint.height())) {
				constraint.height((this.outerRingThickness() + $.ig.XamRadialMenu.prototype._centerButtonFocusSpace + DefaultToolAreaSpace + $.ig.XamRadialMenu.prototype._toolAreaInnerSpace) * 2 + centerButtonRect.height);
			}
		}
		return constraint;
	}
	,
	cancelHideToolTipTimer: function () {
		if (this.__hideToolTipTimer != null) {
			this.__hideToolTipTimer.cancel();
			this.__hideToolTipTimer = null;
		}
	}
	,
	canNavigateToChildren: function (item) {
		return item.outerRingButtonType() == $.ig.OuterRingButtonType.prototype.navigateToChildren;
	}
	,
	getPointerAnimationIndex: function (item, area) {
		for (var i = 0, count = this.__pointerAnimations.count(); i < count; i++) {
			var animation = this.__pointerAnimations.__inner[i];
			if (animation.item() == item && animation.area() == area) {
				return i;
			}
		}
		return -1;
	}
	,
	hitTest: function (relativePoint, honorCenterDisabledState) {
		var $self = this;
		var item = null;
		var area = $.ig.RadialMenuPointerArea.prototype.none;
		var shouldDisableItems = false;
		if (this.__currentFrame.innerRingFocus().hitTest(relativePoint)) {
			if (!this.__forceDisableCenterButton || !honorCenterDisabledState) {
				area = $.ig.RadialMenuPointerArea.prototype.centerButton;
			}
		} else if (this.__mouseDownToken != null || this.isOpen()) {
			var isOverOuterRing = this.isOpen() && this.__currentFrame.outerRing().hitTest(relativePoint);
			var itemShape = null;
			if (this.__mouseDownToken != null && !isOverOuterRing) {
				var center = this.__currentFrame.center();
				var deltaX = Math.abs(relativePoint.__x - center.__x);
				var deltaY = Math.abs(relativePoint.__y - center.__y);
				var mouseHypotenuse = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
				var angleMenuRect = $.ig.ShapeUtilities.prototype.radiansFromPoint(center, this.__currentFrame.menuRect().width / 2, this.__currentFrame.menuRect().height / 2, relativePoint);
				var ptMenuRect = $.ig.ShapeUtilities.prototype.getPointOnEllipse(this.__currentFrame.menuRect(), angleMenuRect);
				deltaX = Math.abs(ptMenuRect.__x - center.__x);
				deltaY = Math.abs(ptMenuRect.__y - center.__y);
				var menuRectHypotenuse = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
				shouldDisableItems = mouseHypotenuse > menuRectHypotenuse * 3;
				var toolRect = this.__currentFrame.toolAreaMidPtRect();
				var angle = $.ig.ShapeUtilities.prototype.radiansFromPoint(center, toolRect.width / 2, toolRect.height / 2, relativePoint);
				relativePoint = $.ig.ShapeUtilities.prototype.getPointOnEllipse(toolRect, angle);
			}
			if (isOverOuterRing) {
				var buttons = this.__currentFrame.outerRingSectors();
				for (var i = 0, count = buttons.count(); i < count; i++) {
					if (buttons.__inner[i].hitTest(relativePoint)) {
						area = $.ig.RadialMenuPointerArea.prototype.outerRingButton;
						itemShape = buttons.__inner[i];
						break;
					}
				}
			} else if (shouldDisableItems) {
				area = $.ig.RadialMenuPointerArea.prototype.disabledMenu;
			} else {
				var toolSectors = this.__currentFrame.toolAreaSectors();
				for (var i1 = 0, count1 = toolSectors.count(); i1 < count1; i1++) {
					if (toolSectors.__inner[i1].hitTest(relativePoint)) {
						area = $.ig.RadialMenuPointerArea.prototype.toolArea;
						itemShape = toolSectors.__inner[i1];
						break;
					}
				}
			}
			if (itemShape != null) {
				if (!(function () { var $ret = $self.__currentFrame.itemShapes().tryGetValue(itemShape, item); item = $ret.p1; return $ret.ret; }())) {
					area = $.ig.RadialMenuPointerArea.prototype.none;
				} else if (!item.isEnabled()) {
					if (area == $.ig.RadialMenuPointerArea.prototype.toolArea || !this.canNavigateToChildren(item)) {
						item = null;
						area = $.ig.RadialMenuPointerArea.prototype.none;
					}
				} else if (area == $.ig.RadialMenuPointerArea.prototype.outerRingButton && !this.canNavigateToChildren(item)) {
					area = $.ig.RadialMenuPointerArea.prototype.toolArea;
				}
			}
		}
		return new $.ig.Tuple$2($.ig.RadialMenuItemBase.prototype.$type, $.ig.RadialMenuPointerArea.prototype.$type, item, area);
	}
	,
	initializeCustomPaths: function (frame) {
		var currentPaths = this.__customWedgePaths;
		var pool = this.__customWedgePool;
		pool.deferDisactivate(true);
		for (var i = currentPaths.count() - 1; i >= 0; i--) {
			pool.push(currentPaths.__inner[i]);
		}
		currentPaths.clear();
		var en = frame.customItemFrames().getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			var opacity = pair.key().item().isEnabled() ? 1 : $.ig.XamRadialMenu.prototype.disabledMenuOpacity;
			var en1 = pair.value().shapes().getEnumerator();
			while (en1.moveNext()) {
				var shape = en1.current();
				var path = pool.pop();
				path.__opacity = opacity;
				$.ig.XamRadialMenu.prototype.initializePath(shape, path);
				currentPaths.add(path);
			}
		}
		pool.deferDisactivate(false);
	}
	,
	initializeCustomText: function (frame) {
		var currentBlocks = this.__textBlocks;
		var pool = this.__textBlockPool;
		pool.deferDisactivate(true);
		for (var i = currentBlocks.count() - 1; i >= 0; i--) {
			pool.push(currentBlocks.__inner[i]);
		}
		currentBlocks.clear();
		var en = frame.customItemFrames().getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			var opacity = pair.key().item().isEnabled() ? 1 : $.ig.XamRadialMenu.prototype.disabledMenuOpacity;
			var en1 = pair.value().textBlocks().getEnumerator();
			while (en1.moveNext()) {
				var textDef = en1.current();
				var textBlock = pool.pop();
				textBlock.__opacity = opacity;
				textBlock.text(textDef._text);
				textBlock.dataContext(textDef);
				textBlock.fill(textDef._textBrush);
				var rect = textDef._calculatedRect;
				textBlock.canvasLeft(rect.x());
				textBlock.canvasTop(rect.y());
				currentBlocks.add(textBlock);
			}
		}
		pool.deferDisactivate(false);
	}
	,
	initializePath: function (shape, path) {
		path.__fill = shape.fill();
		path.__stroke = shape.strokeThickness() == 0 ? $.ig.RadialMenuUtilities.prototype.transparentBrush : shape.stroke();
		path.strokeThickness(shape.strokeThickness());
		path.renderTransform(shape.transform());
		if (shape.data() == null) {
			shape.data(shape.createGeometry(shape.strokeThickness()));
		}
		path.data(shape.data());
		path.dataContext(shape);
	}
	,
	initializeWedgePaths: function (part, shapes, itemShapes) {
		var $self = this;
		var currentPaths;
		var pool;
		if (!(function () { var $ret = $self.__wedgePaths.tryGetValue(part, currentPaths); currentPaths = $ret.p1; return $ret.ret; }())) {
			this.__wedgePaths.item(part, currentPaths = new $.ig.List$1($.ig.Path.prototype.$type, 0));
		}
		if (!(function () { var $ret = $self.__wedgePools.tryGetValue(part, pool); pool = $ret.p1; return $ret.ret; }())) {
			this.__wedgePools.item(part, pool = (function () {
				var $ret = new $.ig.StackPool$1($.ig.Path.prototype.$type);
				$ret.activate($self.view().pathActivate.runOn($self.view()));
				$ret.deactivate($self.view().pathDeactivate.runOn($self.view()));
				$ret.create(function () { return $self.view().pathCreate1(part); });
				$ret.destroy(function (p) { $self.view().pathDestroy1(part, p); });
				return $ret;
			}()));
		}
		pool.deferDisactivate(true);
		for (var i = currentPaths.count() - 1; i >= 0; i--) {
			pool.push(currentPaths.__inner[i]);
		}
		currentPaths.clear();
		var en = shapes.getEnumerator();
		while (en.moveNext()) {
			var shape = en.current();
			var path = pool.pop();
			var item;
			var opacity = 1;
			if (itemShapes != null && (function () { var $ret = itemShapes.tryGetValue(shape, item); item = $ret.p1; return $ret.ret; }()) && !item.isEnabled()) {
				opacity = $.ig.XamRadialMenu.prototype.disabledMenuOpacity;
			}
			path.__opacity = opacity;
			$.ig.XamRadialMenu.prototype.initializePath(shape, path);
			currentPaths.add(path);
		}
		pool.deferDisactivate(false);
	}
	,
	invalidate: function (invalidation) {
		if (invalidation > this.__invalidation) {
			this.__invalidation = invalidation;
		}
		this.view().scheduleArrange();
	}
	,
	onHideToolTipDelayExpired: function () {
		this.view().hideToolTip();
		this.__hideToolTipTimer = null;
	}
	,
	navigateToItem1: function (dataItem) {
		var newContainer = $.ig.util.cast($.ig.RadialMenuItemBase.prototype.$type, dataItem);
		$.ig.Debug.prototype.assert1(newContainer == null || newContainer.menu() == this || newContainer.menu() == null, "The item belongs to another menu?");
		if (newContainer == null && dataItem != null) {
			var currentParent = this.__levels.peek().parent();
			if (currentParent != null && dataItem == currentParent.dataItemResolved()) {
				newContainer = currentParent;
			} else {
				var en = this.view().rootItems().getEnumerator();
				while (en.moveNext()) {
					var rootItem = en.current();
					newContainer = rootItem.getContainerForItem(dataItem);
					if (newContainer != null) {
						break;
					}
				}
			}
		}
		this.navigateToItem(newContainer);
	}
	,
	navigateToItem: function (newContainer) {
		var oldLevel = this.__levels.peek();
		var oldContainer = oldLevel.parent();
		if (oldContainer == newContainer) {
			return;
		}
		while ($.ig.util.cast($.ig.RadialMenuItem.prototype.$type, newContainer) !== null) {
			var containerItem = newContainer;
			var containerCanShowChildren = containerItem.childItemPlacementResolved() == $.ig.RadialMenuChildItemPlacement.prototype.asChildren;
			if (containerCanShowChildren) {
				break;
			}
			newContainer = containerItem.parentItem();
		}
		if (oldContainer == newContainer) {
			this.verifyCurrentOpenMenuItem();
			return;
		}
		this.stopNavigationAnimation();
		if (newContainer == null || (oldContainer != null && !oldContainer.isAncestorOf(newContainer))) {
			while (this.__levels.count() > 1) {
				var tempLevel = this.__levels.pop();
				tempLevel.onDeactivated();
				tempLevel.parent().onItemClosed();
				var parentLevel = this.__levels.peek();
				if (parentLevel.parent() == newContainer || (parentLevel.parent() != null && parentLevel.parent().isAncestorOf(newContainer))) {
					break;
				}
			}
		}
		if (newContainer != null) {
			var rootParent = this.__levels.peek().parent();
			var ancestors = new $.ig.Stack$1($.ig.RadialMenuItemBase.prototype.$type);
			var tempItem = newContainer;
			while (tempItem != rootParent) {
				ancestors.push(tempItem);
				tempItem = tempItem.parentItem();
			}
			var en = ancestors.getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				if (item.__visibility == $.ig.Visibility.prototype.collapsed) {
					break;
				}
				var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, item);
				if (mi != null && mi.childItemPlacementResolved() == $.ig.RadialMenuChildItemPlacement.prototype.asSiblingsWhenChecked) {
					continue;
				}
				if (!this.canNavigateToChildren(item)) {
					break;
				}
				if (!item.onItemOpening()) {
					break;
				}
				var currentLevel = this.__levels.peek();
				var hasCustomDropDown = item.displaysCustomDropDown();
				var customDropDownContent = hasCustomDropDown ? item.customDropDownContent() : null;
				var items = hasCustomDropDown ? new Array(0) : item.itemContainers();
				var nextLevel = new $.ig.RadialMenuItemLevel(this, item, currentLevel, items, customDropDownContent);
				this.__levels.push(nextLevel);
				if (currentLevel != oldLevel) {
					currentLevel.verifyItemPositionAngles(0);
				}
				currentLevel.expandedItem(item);
				item.onItemOpened();
				if (hasCustomDropDown) {
					break;
				}
			}
		}
		this.__previousLevelForAnimation = oldLevel;
		this.view().onLevelCountChanged();
		this.startNavigationAnimation();
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
		if (oldLevel.customDropDownContent() != null) {
			this.view().hideCustomDropDown(oldLevel.customDropDownContent());
		}
		var newLevel = this.__levels.peek();
		if (newLevel.customDropDownContent() != null) {
			this.hideKeyTips();
			this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.none, false, false);
			this.view().showCustomDropDown(newLevel.customDropDownContent());
		} else if (this.view().isFocused() && !this.areKeyTipsDisplayed() && newLevel.parentLevel() == oldLevel) {
			var oldPos = oldLevel.getItemPosition(newLevel.parent());
			if (oldPos != null) {
				newLevel.verifyItemPositionAngles(0);
				var midPtParent = oldPos._startAngle + (oldPos._endAngle - oldPos._startAngle) / 2;
				var diff = 1.7976931348623157E+308;
				var closestItem = null;
				var en1 = newLevel.itemPositions().getEnumerator();
				while (en1.moveNext()) {
					var newPos = en1.current();
					var midPtChild = newPos._startAngle + (newPos._endAngle - newPos._startAngle) / 2;
					var tempDiff = Math.abs(midPtChild - midPtParent);
					if (tempDiff < diff && (newPos.item().isEnabled() || this.canNavigateToChildren(newPos.item()))) {
						diff = tempDiff;
						closestItem = newPos;
					}
				}
				if (closestItem != null) {
					this.setHotTrackArea(closestItem.item(), closestItem.item().isEnabled() ? $.ig.RadialMenuPointerArea.prototype.toolArea : $.ig.RadialMenuPointerArea.prototype.outerRingButton, false, false);
				} else {
					this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, false, false);
				}
			}
		}
		this.verifyCurrentOpenMenuItem();
		if (!$.ig.RadialMenuUtilities.prototype.isAnimatorActive(this.__navigationAnimator)) {
			if (this.areKeyTipsDisplayed()) {
				this.displayKeyTips();
			}
		}
	}
	,
	navigateToParent: function () {
		if (this.__levels.count() > 1) {
			this.navigateToItem($.ig.Enumerable.prototype.first$1($.ig.RadialMenuItemLevel.prototype.$type, $.ig.Enumerable.prototype.skip$1($.ig.RadialMenuItemLevel.prototype.$type, this.__levels, 1)).parent());
		}
	}
	,
	onExpandAnimatorPropertyChanged: function (sender, e) {
		if (!$.ig.RadialMenuUtilities.prototype.isAnimatorActive(this.__expandCollapseAnimator)) {
			this.stopExpandCollapseAnimation();
		}
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
	}
	,
	onInitialized: function () {
		if (this.__isInitialized) {
			return;
		}
		this.__isInitialized = true;
		if (this.currentOpenMenuItem() != null) {
			this.navigateToItem1(this.currentOpenMenuItem());
		}
	}
	,
	onLeaveAnimatorPropertyChanged: function (sender, e) {
		var percent = this.__leaveAnimator.transitionProgress();
		this.processLeaveAnimatorChanged(percent);
	}
	,
	processLeaveAnimatorChanged: function (percent) {
		var animations = this.__pointerAnimations;
		for (var i = animations.count() - 1; i >= 0; i--) {
			var animation = animations.__inner[i];
			var itemPercent = $.ig.RadialMenuUtilities.prototype.clamp(animation.progressCompleted() + percent, 0, 1);
			var item = animation.item();
			switch (animation.area()) {
				case $.ig.RadialMenuPointerArea.prototype.none:
				case $.ig.RadialMenuPointerArea.prototype.disabledMenu:
					break;
				case $.ig.RadialMenuPointerArea.prototype.centerButton:
					{
						var fill = $.ig.BrushUtil.prototype.getInterpolation(this.centerButtonHotTrackFill(), itemPercent, this.isOpen() ? this.centerButtonFill() : this.centerButtonClosedFill(), $.ig.InterpolationMode.prototype.rGB);
						var stroke = $.ig.BrushUtil.prototype.getInterpolation(this.centerButtonHotTrackStroke(), itemPercent, this.isOpen() ? this.centerButtonStroke() : this.centerButtonClosedStroke(), $.ig.InterpolationMode.prototype.rGB);
						this.updatePathFill(this.__currentFrame.innerRing(), fill);
						this.updatePathStroke(this.__currentFrame.innerRing(), stroke);
						this.updatePathStroke(this.__currentFrame.innerRingFocus(), stroke);
						this.view().onCenterRingBrushesUpdated();
						break;
					}
				case $.ig.RadialMenuPointerArea.prototype.outerRingButton:
					{
						var sector = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.outerRing);
						var arrow = sector != null ? this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.outerRingArrow) : null;
						if (sector != null) {
							sector.fill($.ig.BrushUtil.prototype.getInterpolation(item.outerRingButtonHotTrackFillResolved(), itemPercent, item.outerRingButtonFillResolved(), $.ig.InterpolationMode.prototype.rGB));
							sector.stroke($.ig.BrushUtil.prototype.getInterpolation(item.outerRingButtonHotTrackStrokeResolved(), itemPercent, item.outerRingButtonStrokeResolved(), $.ig.InterpolationMode.prototype.rGB));
						}
						if (arrow != null) {
							arrow.fill($.ig.BrushUtil.prototype.getInterpolation(item.outerRingButtonHotTrackForegroundResolved(), itemPercent, item.outerRingButtonForegroundResolved(), $.ig.InterpolationMode.prototype.rGB));
						}
						break;
					}
				case $.ig.RadialMenuPointerArea.prototype.toolArea:
					{
						var highlight = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.toolHighlight);
						if (null != highlight) {
							highlight.fill($.ig.BrushUtil.prototype.getInterpolation(item.highlightBrushResolved(), itemPercent, null, $.ig.InterpolationMode.prototype.rGB));
						}
						var toolArea = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.toolArea);
						if (null != toolArea) {
							toolArea.fill($.ig.BrushUtil.prototype.getInterpolation(item.innerAreaHotTrackFillResolved(), itemPercent, item.innerAreaFillResolved(), $.ig.InterpolationMode.prototype.rGB));
							toolArea.stroke($.ig.BrushUtil.prototype.getInterpolation(item.innerAreaHotTrackStrokeResolved(), itemPercent, item.innerAreaStrokeResolved(), $.ig.InterpolationMode.prototype.rGB));
						}
						break;
					}
			}
		}
		this.invalidate($.ig.RadialMenuInvalidation.prototype.renderFrame);
	}
	,
	onNavigationAnimatorPropertyChanged: function (sender, e) {
		if (!$.ig.RadialMenuUtilities.prototype.isAnimatorActive(this.__navigationAnimator)) {
			this.stopNavigationAnimation();
		}
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		if (oldValue == newValue) {
			return;
		}
		if (oldValue == null) {
			var dp = $.ig.DependencyProperty.prototype.queryRegisteredProperty(propertyName, $.ig.XamRadialMenu.prototype.$type);
			if (dp != null && $.ig.Nullable.prototype.getUnderlyingType(dp.propertyType()) == null && dp.propertyMetadata() != null && dp.propertyMetadata().defaultValue() == newValue) {
				return;
			}
		}
		this.view().onMenuPropertyChanged(propertyName, oldValue, newValue);
		var shouldDirty = false;
		switch (propertyName) {
			case $.ig.XamRadialMenu.prototype.centerButtonKeyTipPropertyName:
				shouldDirty = false;
				break;
			case $.ig.XamRadialMenu.prototype.currentOpenMenuItemPropertyName:
				if (this.__isInitialized) {
					this.navigateToItem1(newValue);
				}
				break;
			case $.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationPropertyName:
			case $.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionPropertyName:
			case $.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationPropertyName:
			case $.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionPropertyName:
			case $.ig.XamRadialMenu.prototype.keyTipTemplatePropertyName:
				break;
			case $.ig.XamRadialMenu.prototype.menuBackgroundPropertyName:
				this.updatePathFill(this.__currentFrame.menuArea(), newValue);
				shouldDirty = true;
				break;
			case $.ig.XamRadialMenu.prototype.centerButtonHotTrackFillPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokePropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonFillPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonStrokePropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonClosedFillPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonClosedStrokePropertyName:
				this.updateCenterRingBrushes();
				break;
			case $.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessPropertyName:
				this.updatePathStrokeThickness(this.__currentFrame.innerRing(), newValue);
				this.verifyFocusRingBorder();
				break;
			case $.ig.XamRadialMenu.prototype.outerRingFillPropertyName:
				this.updatePathFill(this.__currentFrame.outerRing(), newValue);
				break;
			case $.ig.XamRadialMenu.prototype.outerRingStrokeThicknessPropertyName:
				this.updatePathStrokeThickness(this.__currentFrame.outerRing(), newValue);
				break;
			case $.ig.XamRadialMenu.prototype.outerRingStrokePropertyName:
				this.updatePathStroke(this.__currentFrame.outerRing(), newValue);
				break;
			case $.ig.XamRadialMenu.prototype.centerButtonContentHeightPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonContentWidthPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonContentPropertyName:
			case $.ig.XamRadialMenu.prototype.outerRingThicknessPropertyName:
			case $.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgePropertyName:
			case $.ig.XamRadialMenu.prototype.rotationInDegreesPropertyName:
			case $.ig.XamRadialMenu.prototype.minWedgeCountPropertyName:
			case $.ig.XamRadialMenu.prototype.wedgePaddingInDegreesPropertyName:
				{
					shouldDirty = true;
					break;
				}
			case $.ig.XamRadialMenu.prototype.isOpenPropertyName:
				{
					shouldDirty = true;
					this.stopExpandCollapseAnimation();
					this.stopNavigationAnimation();
					var isOpening = newValue == true;
					var start = this.__outerPathCanvas.__opacity;
					var end = isOpening ? 1 : 0;
					var delta = isOpening ? end - start : start - end;
					if (isOpening) {
						this.view().onBeforeOpen();
					}
					if (delta != 0) {
						var duration = this.menuOpenCloseAnimationDuration();
						if (duration > 0 && this.allowAnimations()) {
							this.__expandCollapseAnimator = new $.ig.DoubleAnimator(start, end, $.ig.truncate(Math.ceil(delta * duration)));
							this.__expandCollapseAnimator.easingFunction(this.menuOpenCloseAnimationEasingFunction());
							var $t = this.__expandCollapseAnimator;
							$t.propertyChanged = $.ig.Delegate.prototype.combine($t.propertyChanged, this.onExpandAnimatorPropertyChanged.runOn(this));
							this.__expandCollapseAnimator.start();
							this.__expandCollapseAnimator.flush();
						} else if (this.areKeyTipsDisplayed()) {
							this.displayKeyTips();
							if (!isOpening) {
								this.view().onAfterClose();
							}
						}
					}
					this.updateCenterRingBrushes();
					var handler = isOpening ? this.opened : this.closed;
					if (null != handler) {
						handler(this, $.ig.EventArgs.prototype.empty);
					}
					break;
				}
			default:
				$.ig.Debug.prototype.assert1(false, "Missing property:" + propertyName);
				break;
		}
		if (shouldDirty) {
			this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
		}
		var propHandler = this.propertyChanged;
		if (null != propHandler) {
			switch (propertyName) {
				case $.ig.XamRadialMenu.prototype.isOpenPropertyName:
				case $.ig.XamRadialMenu.prototype.currentOpenMenuItemPropertyName:
					propHandler(this, new $.ig.PropertyChangedEventArgs(propertyName));
					break;
			}
		}
	}
	,
	onItemLevelStateChanged: function (level, relatedItem, childProperty) {
		if (level != this.__levels.peek()) {
		}
		var newInvalidation = this.__invalidation;
		if (relatedItem != null) {
			switch (childProperty) {
				case $.ig.RadialMenuItemBase.prototype.toolTipResolvedPropertyName:
					if (relatedItem == this.__hotTrackItem) {
						this.cancelHideToolTipTimer();
						this.view().showToolTip(relatedItem);
					}
					break;
				case $.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName:
					if (this.currentFrame().invalidateCustomItemFrame(relatedItem)) {
						newInvalidation = $.ig.RadialMenuInvalidation.prototype.invalidateCustomVisuals;
					} else {
						newInvalidation = $.ig.RadialMenuInvalidation.prototype.prepareFrame;
					}
					break;
				case $.ig.RadialMenuItemBase.prototype.isEnabledPropertyName:
					newInvalidation = $.ig.RadialMenuInvalidation.prototype.renderFrame;
					break;
				default:
					newInvalidation = $.ig.RadialMenuInvalidation.prototype.prepareFrame;
					break;
			}
		} else {
			newInvalidation = $.ig.RadialMenuInvalidation.prototype.prepareFrame;
		}
		if (newInvalidation != this.__invalidation) {
			this.invalidate(newInvalidation);
		}
	}
	,
	onShapeInvalidated: function (shape) {
		this.invalidate($.ig.RadialMenuInvalidation.prototype.renderFrame);
	}
	,
	openMenuViaTimer: function () {
		if (!this.isOpen()) {
			this.__forceDisableCenterButton = true;
			this.setIsOpen(true);
		}
	}
	,
	navigateNextPrevious: function (item, next) {
		if (!this.isOpen()) {
			return;
		}
		if (item != null && item.processNextPrevious(next, false)) {
			return;
		}
		var level = this.__levels.peek();
		var getNextItem = level.getNextItem(item, next);
		if (getNextItem != null) {
			var nextItem = getNextItem.item();
			do {
				if (nextItem.isEnabled()) {
					this.setHotTrackArea(nextItem, $.ig.RadialMenuPointerArea.prototype.toolArea, false, false);
					nextItem.processNextPrevious(next, true);
					break;
				} else if (this.canNavigateToChildren(nextItem)) {
					this.setHotTrackArea(nextItem, $.ig.RadialMenuPointerArea.prototype.outerRingButton, false, false);
					break;
				} else {
					var tempNext = level.getNextItem(nextItem, next);
					if (tempNext != null) {
						nextItem = tempNext.item();
					} else {
						this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, false, false);
						break;
					}
				}
			} while (nextItem != getNextItem.item());
		} else {
			this.setHotTrackArea(null, $.ig.RadialMenuPointerArea.prototype.centerButton, false, false);
		}
	}
	,
	prepareFrame: function (frame, level, animation, animationPercent, animationFromLevel) {
		var menuRect = this.viewport();
		if ($.ig.LiteRectExtensions.prototype.isEmpty(menuRect)) {
			return;
		}
		var originalLevel = level;
		if (animationFromLevel != null && animation != $.ig.RadialMenuAnimation.prototype.none && animation != $.ig.RadialMenuAnimation.prototype.expandCollapse && animationPercent < 0.5) {
			level = animationFromLevel;
		}
		var center = { __x: menuRect.x + menuRect.width / 2, __y: menuRect.y + menuRect.height / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		var outerRingThickness = Math.max(this.outerRingThickness(), 0);
		var isExpandCollapse = animation == $.ig.RadialMenuAnimation.prototype.expandCollapse;
		var extraWedgePercentRotation = isExpandCollapse ? (1 - animationPercent) * $.ig.XamRadialMenu.prototype._expandRotationPercent : 0;
		var outerRingAdjustment = isExpandCollapse ? (1 - animationPercent) * outerRingThickness : 0;
		var pulseAnimationPercent = (animationPercent <= 0.5 ? animationPercent : 1 - animationPercent);
		var outerRingNavAdjustment = !isExpandCollapse ? pulseAnimationPercent * outerRingThickness : 0;
		var menuRadiusX = menuRect.width / 2 - outerRingAdjustment;
		var menuRadiusY = menuRect.height / 2 - outerRingAdjustment;
		outerRingThickness -= outerRingAdjustment;
		var outerRingThicknessX = Math.min(outerRingThickness, menuRadiusX);
		var outerRingThicknessY = Math.min(outerRingThickness, menuRadiusY);
		var centerButtonContentSize = this.view().getCenterButtonSize();
		var centerButtonRect = $.ig.ShapeUtilities.prototype.getBoundingEllipse(new $.ig.LiteRect(1, 0, 0, centerButtonContentSize.width(), centerButtonContentSize.height()));
		var innerRadiusX = Math.min(centerButtonRect.width / 2, menuRadiusX - outerRingThicknessX);
		var innerRadiusY = Math.min(centerButtonRect.height / 2, menuRadiusY - outerRingThicknessY);
		var middleRadiusX = menuRadiusX - outerRingThicknessX - outerRingNavAdjustment;
		var middleRadiusY = menuRadiusY - outerRingThicknessY - outerRingNavAdjustment;
		frame.center(center);
		var contentScale = !isExpandCollapse ? 1 - (pulseAnimationPercent * 2 * 0.6) : 1;
		frame.contentScale(contentScale);
		frame.menuRect(menuRect);
		frame._toolAreaOuterRadiusX = menuRadiusX - outerRingThicknessX;
		frame._toolAreaOuterRadiusY = menuRadiusY - outerRingThicknessY;
		frame.invalidatedCustomVisuals().clear();
		var menuArea = frame.menuArea();
		menuArea._center = center;
		menuArea._radiusX = menuRadiusX;
		menuArea._radiusY = menuRadiusY;
		menuArea.data(null);
		var outerRing = frame.outerRing();
		outerRing._center = center;
		outerRing._radiusX = menuRadiusX;
		outerRing._radiusY = menuRadiusY;
		outerRing._innerRadiusX = middleRadiusX;
		outerRing._innerRadiusY = middleRadiusY;
		outerRing.data(null);
		var innerRing = frame.innerRing();
		innerRing._center = center;
		innerRing._radiusX = innerRadiusX;
		innerRing._radiusY = innerRadiusY;
		innerRing.data(null);
		var innerRingFocus = frame.innerRingFocus();
		innerRingFocus._center = center;
		innerRingFocus._radiusX = innerRing._radiusX + $.ig.XamRadialMenu.prototype._centerButtonFocusSpace;
		innerRingFocus._radiusY = innerRing._radiusY + $.ig.XamRadialMenu.prototype._centerButtonFocusSpace;
		innerRingFocus.data(null);
		var slots = level.itemPositions();
		var itemShapes = frame.itemShapes();
		itemShapes.clear();
		var visibleItems = frame.visibleItems();
		visibleItems.clear();
		visibleItems.addRange(slots);
		level.verifyItemPositionAngles(extraWedgePercentRotation);
		frame.contentRotationAnimationAngle(extraWedgePercentRotation * level.getWedgeSweep());
		var outerRings = frame.outerRingSectors();
		outerRings.clear();
		var outerRingArrows = frame.outerRingArrows();
		outerRingArrows.clear();
		var toolAreas = frame.toolAreaSectors();
		toolAreas.clear();
		var toolHighlights = frame.toolHighlightArcs();
		toolHighlights.clear();
		var toolCheckmarks = frame.toolCheckmarks();
		toolCheckmarks.clear();
		var colorWellPreviews = frame.colorWellPreviewSectors();
		colorWellPreviews.clear();
		var customFrames = frame.customItemFrames();
		customFrames.clear();
		var toolAreaOuterRadiusX = Math.max(middleRadiusX - 1, 0) * contentScale;
		var toolAreaOuterRadiusY = Math.max(middleRadiusY - 1, 0) * contentScale;
		var toolAreaInnerRadiusX = Math.min(innerRingFocus._radiusX + $.ig.XamRadialMenu.prototype._toolAreaInnerSpace, toolAreaOuterRadiusX) * contentScale;
		var toolAreaInnerRadiusY = Math.min(innerRingFocus._radiusY + $.ig.XamRadialMenu.prototype._toolAreaInnerSpace, toolAreaOuterRadiusY) * contentScale;
		var colorWellOuterRadiusX = Math.max(toolAreaOuterRadiusX - $.ig.XamRadialMenu.prototype._highlightInset, 0) * contentScale;
		var colorWellOuterRadiusY = Math.max(toolAreaOuterRadiusY - $.ig.XamRadialMenu.prototype._highlightInset, 0) * contentScale;
		var colorWellInnerRadiusX = (toolAreaOuterRadiusX - ((toolAreaOuterRadiusX - toolAreaInnerRadiusX) * 0.56)) * contentScale;
		var colorWellInnerRadiusY = (toolAreaOuterRadiusY - ((toolAreaOuterRadiusY - toolAreaInnerRadiusY) * 0.56)) * contentScale;
		var colorWellPreviewOuterRadiusX = Math.max(colorWellInnerRadiusX - 8, innerRingFocus._radiusX);
		var colorWellPreviewOuterRadiusY = Math.max(colorWellInnerRadiusY - 8, innerRingFocus._radiusY);
		var colorWellPreviewInnerRadiusX = Math.min(innerRingFocus._radiusX + 8, colorWellPreviewOuterRadiusX);
		var colorWellPreviewInnerRadiusY = Math.min(innerRingFocus._radiusY + 8, colorWellPreviewOuterRadiusY);
		var toolAreaOuterRect = new $.ig.LiteRect(1, center.__x - toolAreaOuterRadiusX, center.__y - toolAreaOuterRadiusY, toolAreaOuterRadiusX * 2, toolAreaOuterRadiusY * 2);
		var toolAreaInnerRect = new $.ig.LiteRect(1, center.__x - toolAreaInnerRadiusX, center.__y - toolAreaInnerRadiusY, toolAreaInnerRadiusX * 2, toolAreaInnerRadiusY * 2);
		var height = toolAreaInnerRect.height + (toolAreaOuterRect.height - toolAreaInnerRect.height) * 0.6;
		var width = toolAreaInnerRect.width + (toolAreaOuterRect.width - toolAreaInnerRect.width) * 0.6;
		var toolAreaContentRect = new $.ig.LiteRect(1, center.__x - width / 2, center.__y - height / 2, width, height);
		var toolOuterSectorOuterRadiusX = outerRing._radiusX - outerRingNavAdjustment * 2;
		var toolOuterSectorOuterRadiusY = outerRing._radiusY - outerRingNavAdjustment * 2;
		var toolOuterSectorInnerRadiusX = outerRing._innerRadiusX;
		var toolOuterSectorInnerRadiusY = outerRing._innerRadiusY;
		var outerRingCenterRadiusX = (toolOuterSectorOuterRadiusX + toolOuterSectorInnerRadiusX) / 2;
		var outerRingCenterRadiusY = (toolOuterSectorOuterRadiusY + toolOuterSectorInnerRadiusY) / 2;
		var outerRingCenterContentRect = new $.ig.LiteRect(1, center.__x - outerRingCenterRadiusX, center.__y - outerRingCenterRadiusY, outerRingCenterRadiusX * 2, outerRingCenterRadiusY * 2);
		frame.toolAreaMidPtRect(toolAreaContentRect);
		var en = slots.getEnumerator();
		while (en.moveNext()) {
			var slot = en.current();
			var item = slot.item();
			var actualStart = slot._startAngle;
			var actualEnd = slot._endAngle;
			var customFrame = item.createCustomVisuals(slot, frame);
			if (customFrame != null) {
				customFrames.add(slot, customFrame);
				this.updateTextBlockPosition(customFrame.textBlocks());
			}
			var colorWellItem = $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, item);
			var actualStartInset = actualStart;
			var actualEndInset = actualEnd;
			if (colorWellItem == null) {
				actualStartInset += $.ig.XamRadialMenu.prototype._sectorInset;
				actualEndInset -= $.ig.XamRadialMenu.prototype._sectorInset;
			} else {
				colorWellItem.verifyBorderColors();
			}
			var centerAngleInRadians = actualStart + (actualEnd - actualStart) / 2;
			var toolAreaCenterAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, centerAngleInRadians, toolAreaContentRect);
			if ($.ig.util.isNaN(toolAreaCenterAngle)) {
				toolAreaCenterAngle = centerAngleInRadians;
			}
			slot._contentLocation = $.ig.ShapeUtilities.prototype.getPointOnEllipse(toolAreaContentRect, toolAreaCenterAngle - frame.contentRotationAnimationAngle());
			slot._contentAngle = toolAreaCenterAngle;
			var outerRingSector = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, toolOuterSectorOuterRadiusX, toolOuterSectorOuterRadiusY, toolOuterSectorInnerRadiusX, toolOuterSectorInnerRadiusY, actualStartInset, actualEndInset);
			outerRings.add(outerRingSector);
			itemShapes.item(outerRingSector, item);
			var isOverOuterRingButton = this.__mouseDownToken == null && item == this.__hotTrackItem && this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.outerRingButton;
			var canNavToChildren = this.canNavigateToChildren(item);
			if (canNavToChildren || colorWellItem != null) {
				var arrow = null;
				if (canNavToChildren) {
					var outerRingContentCenterAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, centerAngleInRadians, outerRingCenterContentRect);
					var drillDownPt = $.ig.ShapeUtilities.prototype.getPointOnEllipse(outerRingCenterContentRect, outerRingContentCenterAngle);
					var ddSize = new $.ig.Size(1, 5.5, 11);
					arrow = (function () {
						var $ret = new $.ig.TriangleShape();
						$ret._point2 = { __x: ddSize.width(), __y: ddSize.height() / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
						$ret._point3 = { __x: 0, __y: ddSize.height(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
						return $ret;
					}());
					var tg = new $.ig.TransformGroup();
					tg.children().add((function () {
						var $ret = new $.ig.RotateTransform();
						$ret.angle(centerAngleInRadians * 180 / Math.PI);
						$ret.centerX(ddSize.width() / 2);
						$ret.centerY(ddSize.height() / 2);
						return $ret;
					}()));
					tg.children().add((function () {
						var $ret = new $.ig.TranslateTransform();
						$ret.x(drillDownPt.__x - ddSize.width() / 2);
						$ret.y(drillDownPt.__y - ddSize.height() / 2);
						return $ret;
					}()));
					arrow.transform(tg);
					outerRingArrows.add(arrow);
					itemShapes.item(arrow, item);
				}
				if (isOverOuterRingButton) {
					if (arrow != null) {
						arrow.fill(item.outerRingButtonHotTrackForegroundResolved());
					}
					outerRingSector.fill(item.outerRingButtonHotTrackFillResolved());
					outerRingSector.stroke(item.outerRingButtonHotTrackStrokeResolved());
				} else {
					if (arrow != null) {
						arrow.fill(item.outerRingButtonForegroundResolved());
					}
					outerRingSector.fill(item.outerRingButtonFillResolved());
					outerRingSector.stroke(item.outerRingButtonStrokeResolved());
				}
				outerRingSector.strokeThickness(item.outerRingButtonStrokeThicknessResolved());
			}
			var toolArea;
			if (colorWellItem != null) {
				toolArea = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, colorWellOuterRadiusX, colorWellOuterRadiusY, colorWellInnerRadiusX, colorWellInnerRadiusY, actualStart, actualEnd);
			} else {
				toolArea = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, toolAreaOuterRadiusX, toolAreaOuterRadiusY, toolAreaInnerRadiusX, toolAreaInnerRadiusY, actualStart, actualEnd);
			}
			var hotTrackToolArea = item == this.__hotTrackItem && this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.toolArea;
			toolArea.fill(hotTrackToolArea ? item.innerAreaHotTrackFillResolved() : item.innerAreaFillResolved());
			toolArea.stroke(hotTrackToolArea ? item.innerAreaHotTrackStrokeResolved() : item.innerAreaStrokeResolved());
			toolArea.strokeThickness(item.innerAreaStrokeThicknessResolved());
			toolAreas.add(toolArea);
			itemShapes.item(toolArea, item);
			if (item.displayAsChecked()) {
				var highlightThickness = colorWellItem != null ? 3 : 1;
				var highlightBrush = item.checkedHighlightBrushResolved();
				var toolHighlight = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, outerRing._innerRadiusX - $.ig.XamRadialMenu.prototype._highlightInset, outerRing._innerRadiusY - $.ig.XamRadialMenu.prototype._highlightInset, outerRing._innerRadiusX - ($.ig.XamRadialMenu.prototype._highlightInset + highlightThickness), outerRing._innerRadiusY - ($.ig.XamRadialMenu.prototype._highlightInset + highlightThickness), actualStartInset, actualEndInset);
				toolHighlight.fill(highlightBrush);
				toolCheckmarks.add(toolHighlight);
				itemShapes.item(toolHighlight, item);
			}
			if (hotTrackToolArea) {
				this.addHighlightArc(slot, frame);
			}
		}
		var parentLevel = level.parentLevel();
		if (parentLevel != null) {
			parentLevel.verifyItemPositionAngles(0);
			var en1 = parentLevel.itemPositions().getEnumerator();
			while (en1.moveNext()) {
				var parentItem = en1.current();
				var item1 = $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, parentItem.item());
				if (item1 != null) {
					item1.verifyBorderColors();
					var colorWellPreview = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, colorWellPreviewOuterRadiusX, colorWellPreviewOuterRadiusY, colorWellPreviewInnerRadiusX, colorWellPreviewInnerRadiusY, parentItem._startAngle, parentItem._endAngle);
					colorWellPreview.fill(item1.innerAreaFillResolved());
					colorWellPreview.stroke(item1.innerAreaStrokeResolved());
					colorWellPreview.strokeThickness(item1.innerAreaStrokeThicknessResolved());
					colorWellPreview._context = item1;
					colorWellPreviews.add(colorWellPreview);
					itemShapes.item(colorWellPreview, item1);
				}
			}
		}
	}
	,
	processCenterButtonClick: function () {
		if (this.__levels.count() > 1) {
			this.navigateToParent();
		} else {
			this.setIsOpen(!this.isOpen());
		}
	}
	,
	processClick: function (area, item, wasCenterDisabled) {
		if (area == $.ig.RadialMenuPointerArea.prototype.centerButton) {
			if (!wasCenterDisabled) {
				this.processCenterButtonClick();
			}
		} else if (item != null) {
			if (area == $.ig.RadialMenuPointerArea.prototype.outerRingButton && this.canNavigateToChildren(item)) {
				this.navigateToItem(item);
			} else if (item.isEnabled()) {
				this.processItemClick(item);
			}
		}
	}
	,
	processItemClick: function (item) {
		if (item != null) {
			item.onClick();
		}
	}
	,
	processMouseUp: function (relativePoint, token, isCancel) {
		this.stopCenterButtonTimer();
		if (this.__mouseDownToken == null) {
			return;
		}
		var wasCenterDisabled = this.__forceDisableCenterButton;
		this.__mouseDownToken = null;
		this.__forceDisableCenterButton = false;
		this.invalidate($.ig.RadialMenuInvalidation.prototype.prepareFrame);
		var oldArea = this.__hotTrackArea;
		var oldItem = this.__hotTrackItem;
		var hitResult = this.hitTest(relativePoint, true);
		if (oldItem != null && oldArea == $.ig.RadialMenuPointerArea.prototype.toolArea) {
			oldItem.isMouseDown(false);
		}
		var canHideToolTip = isCancel || this.isOpen() || hitResult.item1() != null || oldItem == null || oldArea != $.ig.RadialMenuPointerArea.prototype.toolArea;
		this.setHotTrackArea(hitResult.item1(), hitResult.item2(), true, !canHideToolTip);
		if (!isCancel) {
			if (this.isOpen() && oldArea == $.ig.RadialMenuPointerArea.prototype.none && hitResult.item2() == $.ig.RadialMenuPointerArea.prototype.none) {
				var en = this.__currentFrame.colorWellPreviewSectors().getEnumerator();
				while (en.moveNext()) {
					var preview = en.current();
					if (preview.hitTest(relativePoint)) {
						oldArea = $.ig.RadialMenuPointerArea.prototype.centerButton;
						break;
					}
				}
			}
			this.processClick(oldArea, oldItem, wasCenterDisabled);
		}
		if (!canHideToolTip && this.__hideToolTipTimer == null && this.__hotTrackArea == hitResult.item2() && this.__hotTrackItem == hitResult.item1()) {
			this.view().hideToolTip();
		}
	}
	,
	processTabKey: function (modifiers) {
		if (!this.isOpen()) {
			return;
		}
		this.hideKeyTips();
		var nextItem = this.__hotTrackItem;
		var nextArea = this.__hotTrackArea;
		var previous = (modifiers & $.ig.ModifierKeys.prototype.shift) == $.ig.ModifierKeys.prototype.shift;
		var level = this.__levels.peek();
		var nextPos = level.getItemPosition(nextItem);
		if (nextItem != null && nextArea == $.ig.RadialMenuPointerArea.prototype.toolArea) {
			if (nextItem.processTabKey(!previous)) {
				return;
			}
		}
		do {
			switch (nextArea) {
				case $.ig.RadialMenuPointerArea.prototype.toolArea:
					if (previous) {
						nextPos = level.getNextItem(nextItem, false);
					}
					nextArea = $.ig.RadialMenuPointerArea.prototype.outerRingButton;
					break;
				case $.ig.RadialMenuPointerArea.prototype.outerRingButton:
					if (!previous) {
						nextPos = level.getNextItem(nextItem, true);
					}
					nextArea = $.ig.RadialMenuPointerArea.prototype.toolArea;
					break;
				case $.ig.RadialMenuPointerArea.prototype.centerButton:
				case $.ig.RadialMenuPointerArea.prototype.disabledMenu:
				case $.ig.RadialMenuPointerArea.prototype.none:
					nextPos = level.getNextItem(null, !previous);
					nextArea = previous ? $.ig.RadialMenuPointerArea.prototype.outerRingButton : $.ig.RadialMenuPointerArea.prototype.toolArea;
					break;
			}
			if (nextPos == null) {
				nextItem = null;
				nextArea = $.ig.RadialMenuPointerArea.prototype.centerButton;
				break;
			}
			nextItem = nextPos.item();
			if (nextArea == $.ig.RadialMenuPointerArea.prototype.toolArea && nextItem.isEnabled()) {
				break;
			}
			if (nextArea == $.ig.RadialMenuPointerArea.prototype.outerRingButton && this.canNavigateToChildren(nextItem)) {
				break;
			}
		} while (nextItem != null);
		this.setHotTrackArea(nextItem, nextArea, false, false);
		if (this.__hotTrackItem != null && this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.toolArea) {
			this.__hotTrackItem.navigateToFirstLast(!previous);
		}
	}
	,
	renderFrame: function (frame) {
		var targetOuterOpacity = this.isOpen() ? 1 : 0;
		var targetItemOpacity = targetOuterOpacity;
		var itemPanelTransform = null;
		if (this.__expandCollapseAnimator != null) {
			targetItemOpacity = targetOuterOpacity = $.ig.RadialMenuUtilities.prototype.clamp(this.__expandCollapseAnimator.transitionProgress(), 0, 1);
		} else if (this.__navigationAnimator != null) {
			targetItemOpacity = $.ig.RadialMenuUtilities.prototype.clamp(this.__navigationAnimator.transitionProgress(), 0, 1);
			targetItemOpacity = targetItemOpacity <= 0.5 ? 1 - (targetItemOpacity * 2) : 1 - ((1 - targetItemOpacity) * 2);
			var scale = frame.contentScale();
			itemPanelTransform = (function () {
				var $ret = new $.ig.ScaleTransform();
				$ret.scaleX(scale);
				$ret.scaleY(scale);
				$ret.centerX(frame.center().__x);
				$ret.centerY(frame.center().__y);
				return $ret;
			}());
		}
		this.__outerPathCanvas.__opacity = targetOuterOpacity;
		this.__itemCustomCanvas.__opacity = (this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.disabledMenu ? $.ig.XamRadialMenu.prototype.disabledMenuOpacity : 1) * targetItemOpacity;
		this.__itemPanel.__opacity = this.__itemCustomCanvas.__opacity;
		if (!$.ig.ShapeUtilities.prototype.areClose(frame.contentRotationAnimationAngle(), 0)) {
			var rotateTransform = (function () {
				var $ret = new $.ig.RotateTransform();
				$ret.angle(frame.contentRotationAnimationAngle() * 180 / Math.PI);
				$ret.centerX(frame.menuRect().width / 2);
				$ret.centerY(frame.menuRect().height / 2);
				return $ret;
			}());
			if (itemPanelTransform == null) {
				itemPanelTransform = rotateTransform;
			} else {
				var tg = new $.ig.TransformGroup();
				tg.children().add(rotateTransform);
				tg.children().add(itemPanelTransform);
				itemPanelTransform = tg;
			}
		}
		this.__itemPanel.renderTransform(itemPanelTransform);
		this.__itemCustomCanvas.renderTransform(this.__itemPanel.renderTransform());
		this.__itemPathCanvas.__opacity = this.__itemCustomCanvas.__opacity;
		$.ig.XamRadialMenu.prototype.initializePath(frame.menuArea(), this.__menuPath);
		$.ig.XamRadialMenu.prototype.initializePath(frame.outerRing(), this.__outerRingPath);
		$.ig.XamRadialMenu.prototype.initializePath(frame.innerRing(), this.__innerRingPath);
		$.ig.XamRadialMenu.prototype.initializePath(frame.innerRingFocus(), this.__innerRingFocusPath);
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.outerRing, frame.outerRingSectors(), null);
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.outerRingArrow, frame.outerRingArrows(), null);
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.toolArea, frame.toolAreaSectors(), frame.itemShapes());
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.colorWellPreview, frame.colorWellPreviewSectors(), frame.itemShapes());
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.toolCheckmark, frame.toolCheckmarks(), frame.itemShapes());
		this.initializeWedgePaths($.ig.RadialMenuWedgePart.prototype.toolHighlight, frame.toolHighlightArcs(), frame.itemShapes());
		this.initializeCustomPaths(frame);
		this.initializeCustomText(frame);
		var view = this.view();
		view.onVisibleItemPositionsChanged(frame.visibleItems());
		this.__centerButtonPanel.__opacity = this.__forceDisableCenterButton ? $.ig.XamRadialMenu.prototype.disabledMenuOpacity : 1;
		view.positionCenterContent(frame.center());
	}
	,
	setHotTrackArea: function (item, area, preventHideKeyTips, preventHideToolTip) {
		if (area == $.ig.RadialMenuPointerArea.prototype.none || area == $.ig.RadialMenuPointerArea.prototype.disabledMenu) {
			if (this.view().isFocused() && !this.isDisplayingCustomDropDown()) {
				area = $.ig.RadialMenuPointerArea.prototype.centerButton;
			}
		}
		if (area != this.__hotTrackArea || item != this.__hotTrackItem) {
			var oldItem = this.__hotTrackItem;
			var oldArea = this.__hotTrackArea;
			var wasOverCenter = oldArea == $.ig.RadialMenuPointerArea.prototype.centerButton;
			this.__hotTrackItem = item;
			this.__hotTrackArea = area;
			if (area == $.ig.RadialMenuPointerArea.prototype.centerButton && this.__mouseDownToken != null && !this.isOpen()) {
				this.startCenterButtonTimer();
			} else if (wasOverCenter) {
				this.stopCenterButtonTimer();
			}
			switch (oldArea) {
				case $.ig.RadialMenuPointerArea.prototype.centerButton:
				case $.ig.RadialMenuPointerArea.prototype.outerRingButton:
				case $.ig.RadialMenuPointerArea.prototype.toolArea:
					{
						this.stopLeaveAnimations();
						$.ig.Debug.prototype.assert1(-1 == this.getPointerAnimationIndex(oldItem, oldArea), "How can we already have an animation for this area since we only animate the leave?");
						this.__pointerAnimations.add(new $.ig.RadialMenuPointerAnimationInfo(oldItem, oldArea));
						this.startLeaveAnimations();
						break;
					}
			}
			var animationIndex = area != $.ig.RadialMenuPointerArea.prototype.none ? this.getPointerAnimationIndex(item, area) : -1;
			if (animationIndex >= 0) {
				this.__pointerAnimations.removeAt(animationIndex);
			}
			if (wasOverCenter) {
				this.verifyFocusRingBorder();
			}
			if (preventHideKeyTips != true) {
				this.hideKeyTips();
			}
			switch (area) {
				case $.ig.RadialMenuPointerArea.prototype.disabledMenu:
					this.stopLeaveAnimations();
					break;
				case $.ig.RadialMenuPointerArea.prototype.centerButton:
					this.verifyFocusRingBorder();
					this.updateCenterRingBrushes();
					break;
				case $.ig.RadialMenuPointerArea.prototype.outerRingButton:
					{
						var sector = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.outerRing);
						var arrow = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.outerRingArrow);
						if (sector != null) {
							sector.fill(item.outerRingButtonHotTrackFillResolved());
							sector.stroke(item.outerRingButtonHotTrackStrokeResolved());
						}
						if (arrow != null) {
							arrow.fill(item.outerRingButtonHotTrackForegroundResolved());
						}
						break;
					}
				case $.ig.RadialMenuPointerArea.prototype.toolArea:
					{
						var highlight = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.toolHighlight);
						if (highlight == null) {
							var pos = this.__currentFrame.getItemPosition(item);
							if (pos != null) {
								this.addHighlightArc(pos, this.__currentFrame);
							}
						}
						if (null != highlight) {
							highlight.fill(item.highlightBrushResolved());
						}
						var toolArea = this.__currentFrame.getShape(item, $.ig.RadialMenuWedgePart.prototype.toolArea);
						if (null != toolArea) {
							toolArea.fill(item.innerAreaHotTrackFillResolved());
							toolArea.stroke(item.innerAreaHotTrackStrokeResolved());
						}
					}
					break;
			}
			if (this.__mouseDownToken != null && this.isOpen()) {
				this.__forceDisableCenterButton = true;
				if (wasOverCenter && this.__navigateToChildrenOnLeaveCenter && item != null && this.canNavigateToChildren(item)) {
					this.navigateToItem(item);
				}
			}
			if (wasOverCenter && this.isOpen()) {
				this.__navigateToChildrenOnLeaveCenter = false;
			}
			if (oldItem != null && oldArea == $.ig.RadialMenuPointerArea.prototype.toolArea) {
				oldItem.isHotTracked(false);
				oldItem.isMouseDown(false);
			}
			if (item != null && area == $.ig.RadialMenuPointerArea.prototype.toolArea) {
				item.isHotTracked(true);
				if (this.__mouseDownToken != null) {
					item.isMouseDown(true);
				}
			}
			this.cancelHideToolTipTimer();
			if (item != null && !this.areKeyTipsDisplayed()) {
				this.view().showToolTip(item);
			} else if (preventHideToolTip != true) {
				this.view().hideToolTip();
			}
			this.invalidate($.ig.RadialMenuInvalidation.prototype.renderFrame);
		}
	}
	,
	startCenterButtonTimer: function () {
		if (this.isOpen() == false && this.__mouseDownToken != null && this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.centerButton) {
			$.ig.Debug.prototype.assert1(this.__mouseDownCenterButtonTimer == null, "There's already a timer?");
			this.stopCenterButtonTimer();
			this.__mouseDownCenterButtonTimer = new $.ig.TimedOperation(this.openMenuViaTimer.runOn(this), 2000);
		}
	}
	,
	startLeaveAnimations: function () {
		if (!this.allowAnimations()) {
			if (this.__pointerAnimations.count() > 0) {
				this.processLeaveAnimatorChanged(1);
				this.__pointerAnimations.clear();
			}
			return;
		}
		if (this.__leaveAnimator == null) {
			this.__leaveAnimator = new $.ig.DoubleAnimator(0, 1, $.ig.XamRadialMenu.prototype._leaveAnimationDuration);
			var $t = this.__leaveAnimator;
			$t.propertyChanged = $.ig.Delegate.prototype.combine($t.propertyChanged, this.onLeaveAnimatorPropertyChanged.runOn(this));
		}
		this.__leaveAnimator.start();
		this.__leaveAnimator.flush();
	}
	,
	startNavigationAnimation: function () {
		this.stopNavigationAnimation();
		var duration = this.menuItemOpenCloseAnimationDuration();
		if (duration > 0 && this.allowAnimations()) {
			this.__navigationAnimator = new $.ig.DoubleAnimator(0, 1, duration);
			this.__navigationAnimator.easingFunction(this.menuItemOpenCloseAnimationEasingFunction());
			var $t = this.__navigationAnimator;
			$t.propertyChanged = $.ig.Delegate.prototype.combine($t.propertyChanged, this.onNavigationAnimatorPropertyChanged.runOn(this));
			this.__navigationAnimator.start();
			this.__navigationAnimator.flush();
		}
	}
	,
	stopCenterButtonTimer: function () {
		if (null != this.__mouseDownCenterButtonTimer) {
			this.__mouseDownCenterButtonTimer.cancel();
			this.__mouseDownCenterButtonTimer = null;
		}
	}
	,
	stopExpandCollapseAnimation: function () {
		if (this.__expandCollapseAnimator != null) {
			var $t = this.__expandCollapseAnimator;
			$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.onExpandAnimatorPropertyChanged.runOn(this));
			this.__expandCollapseAnimator.stop();
			this.__expandCollapseAnimator = null;
			if (!this.isOpen()) {
				this.view().onAfterClose();
			}
			if (this.areKeyTipsDisplayed()) {
				this.displayKeyTips();
			}
		}
	}
	,
	stopLeaveAnimations: function () {
		if (this.__leaveAnimator != null) {
			var progress = this.__leaveAnimator.transitionProgress();
			for (var i = this.__pointerAnimations.count() - 1; i >= 0; i--) {
				var animation = this.__pointerAnimations.__inner[i];
				animation.progressCompleted(animation.progressCompleted() + progress);
				if (animation.progressCompleted() >= 1) {
					this.__pointerAnimations.removeAt(i);
				}
			}
			this.__leaveAnimator.stop();
		}
	}
	,
	stopNavigationAnimation: function () {
		if (this.__navigationAnimator != null) {
			this.__previousLevelForAnimation = null;
			var $t = this.__navigationAnimator;
			$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.onNavigationAnimatorPropertyChanged.runOn(this));
			this.__navigationAnimator.stop();
			this.__navigationAnimator = null;
			var top = this.__levels.peek();
			top.expandedItem(null);
			if (this.areKeyTipsDisplayed()) {
				this.displayKeyTips();
			}
		}
	}
	,
	updateCenterRingBrushes: function () {
		var isHotTracked = this.__hotTrackArea == $.ig.RadialMenuPointerArea.prototype.centerButton && !this.areKeyTipsDisplayed() && !this.shouldDisplayCenterFocusRing();
		var fill = null;
		var stroke = null;
		if (isHotTracked) {
			fill = this.centerButtonHotTrackFill();
			stroke = this.centerButtonHotTrackStroke();
		} else if (!this.isOpen()) {
			fill = this.centerButtonClosedFill();
			stroke = this.centerButtonClosedStroke();
		} else {
			fill = this.centerButtonFill();
			stroke = this.centerButtonStroke();
		}
		this.updatePathFill(this.__currentFrame.innerRing(), fill);
		this.updatePathStroke(this.__currentFrame.innerRing(), stroke);
		this.updatePathStroke(this.__currentFrame.innerRingFocus(), stroke);
		this.view().onCenterRingBrushesUpdated();
	}
	,
	updatePathFill: function (shape, fill) {
		shape.fill(fill);
		this.onShapeInvalidated(shape);
	}
	,
	updatePathStroke: function (shape, stroke) {
		shape.stroke(stroke);
		this.onShapeInvalidated(shape);
	}
	,
	updatePathStrokeThickness: function (shape, thickness) {
		shape.strokeThickness(thickness);
		shape.data(null);
		this.invalidate($.ig.RadialMenuInvalidation.prototype.renderFrame);
	}
	,
	updateTextBlockPosition: function (textBlocks) {
		var textBlock = this.view().getItemMeasureTextBlock();
		var en = textBlocks.getEnumerator();
		while (en.moveNext()) {
			var textDef = en.current();
			textBlock.text(textDef._text);
			var tbSize = this.view().measureText(textBlock);
			var pt = textDef._location;
			textDef._calculatedRect = $.ig.LiteRectExtensions.prototype.toRect($.ig.RadialMenuUtilities.prototype.calculateRect(textDef._location, tbSize, textDef._horizontalAlignment, textDef._verticalAlignment));
		}
	}
	,
	verifyCurrentOpenMenuItem: function () {
		var openContainer = this.__levels.peek().parent();
		var currentItem = null;
		if (openContainer != null) {
			currentItem = openContainer.dataItemResolved();
		}
		this.currentOpenMenuItem(currentItem);
	}
	,
	verifyFocusRingBorder: function () {
		this.updatePathStrokeThickness(this.__currentFrame.innerRingFocus(), this.shouldDisplayCenterFocusRing() ? this.__currentFrame.innerRing().strokeThickness() : 0);
	}
	,
	closed: null,
	opened: null,
	propertyChanged: null,
	$type: new $.ig.Type('XamRadialMenu', $.ig.Control.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuNumericGauge', 'RadialMenuItemBase', {
	__sortedTickMarks: null,
	__lastPendingValueAngle: 0,
	__hidePendingLine: false,
	__lastRelativePoint: null,
	__areTickMarksVerified: false,
	__endHotTrackDeferredOperation: null,
	staticInit: function () {
	},
	init: function () {
		this.__lastPendingValueAngle = NaN;
		$.ig.RadialMenuItemBase.prototype.init.call(this);
		this.__endHotTrackDeferredOperation = new $.ig.DeferredOperation(this.clearEditInfo.runOn(this));
		this.__sortedTickMarks = new $.ig.List$1(Number, 0);
		this.pendingValue(this.value(NaN));
	},
	createCustomVisuals: function (itemPos, frame) {
		this.verifySortedTickMarks();
		var count = this.__sortedTickMarks.count();
		if (count == 0) {
			return null;
		}
		var hasEmptyWedge = this.hasEmptyFirstWedgeResolved();
		var ArcLineThickness = 2;
		var TickLineLength = 4;
		var LineMargin = 34;
		var itemFrame = new $.ig.RadialMenuNumericGaugeFrame();
		var angle = itemPos._startAngle;
		var totalSweep = itemPos._endAngle - itemPos._startAngle;
		var sweepPerTick = totalSweep / (hasEmptyWedge ? count : count - 1);
		var menuRect = frame.menuRect();
		var innerRing = frame.innerRing();
		var innerRingFocus = frame.innerRingFocus();
		var center = frame.center();
		var innerX = Math.min(frame._toolAreaOuterRadiusX, innerRingFocus._radiusX + 6);
		var innerY = Math.min(frame._toolAreaOuterRadiusY, innerRingFocus._radiusY + 6);
		var outerX = Math.max(frame._toolAreaOuterRadiusX - LineMargin, innerX);
		var outerY = Math.max(frame._toolAreaOuterRadiusY - LineMargin, innerY);
		var lineInnerX = Math.max(outerX - ArcLineThickness, innerX);
		var lineInnerY = Math.max(outerY - ArcLineThickness, innerY);
		var lineOuterRect = new $.ig.LiteRect(1, center.__x - outerX, center.__y - outerY, outerX * 2, outerY * 2);
		var tickOuterRect = $.ig.ShapeUtilities.prototype.inflateSafe(lineOuterRect, TickLineLength, TickLineLength);
		var value = this.value();
		var hasValue = !$.ig.util.isNaN(value);
		var needValueTick = hasValue && value > this.__sortedTickMarks.__inner[0] && value < this.__sortedTickMarks.__inner[count - 1];
		var valueOuterX = Math.max(lineInnerX - 1, innerRing._radiusX);
		var valueOuterY = Math.max(lineInnerY - 1, innerRing._radiusY);
		var valueInnerX = Math.min(Math.max(innerRing._radiusX - innerRing.strokeThickness() / 2, 0), valueOuterX);
		var valueInnerY = Math.min(Math.max(innerRing._radiusY - innerRing.strokeThickness() / 2, 0), valueOuterY);
		var valueOuterRect = new $.ig.LiteRect(1, center.__x - valueOuterX, center.__y - valueOuterY, valueOuterX * 2, valueOuterY * 2);
		var valueInnerRect = new $.ig.LiteRect(1, center.__x - valueInnerX, center.__y - valueInnerY, valueInnerX * 2, valueInnerY * 2);
		var textX = Math.max(frame._toolAreaOuterRadiusX - LineMargin / 2, innerX);
		var textY = Math.max(frame._toolAreaOuterRadiusY - LineMargin / 2, innerY);
		var textRect = new $.ig.LiteRect(1, center.__x - textX, center.__y - textY, textX * 2, textY * 2);
		var tickBrush = this.tickBrushResolved();
		var startColor = this.trackStartColorResolved();
		var endColor = this.trackEndColorResolved();
		var tempStart = startColor;
		var tempEnd = startColor;
		itemFrame._valueInnerRect = valueInnerRect;
		itemFrame._valueOuterRect = valueOuterRect;
		for (var i = 0; i < count; i++) {
			var tickMark = this.__sortedTickMarks.__inner[i];
			if (needValueTick && tickMark == value) {
				needValueTick = false;
			}
			if (hasEmptyWedge || i < count - 1) {
				var arc = $.ig.ShapeUtilities.prototype.createAnnularSector(menuRect, outerX, outerY, lineInnerX, lineInnerY, angle, angle + sweepPerTick);
				var brush = new $.ig.LinearGradientBrush();
				var arcStart = $.ig.ShapeUtilities.prototype.getPointOnEllipse(menuRect, angle);
				var arcEnd = $.ig.ShapeUtilities.prototype.getPointOnEllipse(menuRect, angle + sweepPerTick);
				var startX = arcStart.__x > arcEnd.__x ? 1 : 0;
				var startY = arcStart.__y > arcEnd.__y ? 1 : 0;
				var endX = arcStart.__x < arcEnd.__x ? 1 : 0;
				var endY = arcStart.__y < arcEnd.__y ? 1 : 0;
				tempStart = tempEnd;
				tempEnd = $.ig.ColorUtil.prototype.getInterpolation(startColor, (i + 1) / count, endColor, $.ig.InterpolationMode.prototype.rGB);
				var stop1 = (function () {
					var $ret = new $.ig.GradientStop();
					$ret.color(tempStart);
					$ret._offset = 0;
					return $ret;
				}());
				var stop2 = (function () {
					var $ret = new $.ig.GradientStop();
					$ret.color(tempEnd);
					$ret._offset = 1;
					return $ret;
				}());
				brush._startX = startX;
				brush._startY = startY;
				brush._endX = endX;
				brush._endY = endY;
				brush._gradientStops = [ stop1, stop2 ];
				arc.fill(brush);
				itemFrame.shapes().add(arc);
			}
			var tickAngle = angle;
			if (hasEmptyWedge) {
				tickAngle += sweepPerTick;
			}
			var tickLine = $.ig.RadialMenuNumericGauge.prototype.createTickLine(menuRect, lineOuterRect, tickOuterRect, tickBrush, tickAngle);
			itemFrame.shapes().add(tickLine);
			var tickText = $.ig.RadialMenuNumericGauge.prototype.createTickText(menuRect, textRect, tickMark, tickAngle);
			tickText._context = tickLine;
			itemFrame.textBlocks().add(tickText);
			angle += sweepPerTick;
		}
		var valueAngle = hasValue ? this.angleFromValue(itemPos, value) : 0;
		var valueLine = itemFrame.valueLine();
		if (hasValue) {
			if (needValueTick) {
				var valueTickLine = $.ig.RadialMenuNumericGauge.prototype.createTickLine(menuRect, lineOuterRect, tickOuterRect, tickBrush, valueAngle);
				valueTickLine._context = valueLine;
				itemFrame.shapes().add(valueTickLine);
				var tickText1 = $.ig.RadialMenuNumericGauge.prototype.createTickText(menuRect, textRect, value, valueAngle);
				tickText1._context = valueLine;
				itemFrame.textBlocks().add(tickText1);
			}
			valueLine._start = $.ig.ShapeUtilities.prototype.getPointOnEllipse(valueOuterRect, $.ig.ShapeUtilities.prototype.translateAngle(menuRect, valueAngle, valueOuterRect));
			valueLine._end = $.ig.ShapeUtilities.prototype.getPointOnEllipse(valueInnerRect, $.ig.ShapeUtilities.prototype.translateAngle(menuRect, valueAngle, valueInnerRect));
			valueLine.strokeThickness(1);
			valueLine.stroke(this.valueNeedleBrushResolved());
		}
		var tbBrush = this.foreground();
		var en = itemFrame.textBlocks().getEnumerator();
		while (en.moveNext()) {
			var tb = en.current();
			tb._textBrush = tbBrush;
		}
		this.updateCustomVisuals(itemPos, frame, itemFrame);
		return itemFrame;
	}
	,
	createVisualData: function () {
		return new $.ig.RadialMenuNumericGaugeVisualData();
	}
	,
	getKeyTips: function (itemPos) {
		var $self = this;
		var keyTips = new $.ig.List$1($.ig.KeyTipInfo.prototype.$type, 0);
		this.verifySortedTickMarks();
		var currentValue = this.value();
		var hasAddedCurrentValue = $.ig.util.isNaN(currentValue);
		var action = function (k) {
			var t = k.tag();
			$self.value(t.item1());
			var m = $self.menu();
			if (m != null) {
				m.onItemClicked($self);
			}
		};
		var en = this.__sortedTickMarks.getEnumerator();
		while (en.moveNext()) {
			var t = en.current();
			if (!hasAddedCurrentValue) {
				if (t == currentValue) {
					hasAddedCurrentValue = true;
				} else if (t > currentValue) {
					hasAddedCurrentValue = true;
					keyTips.add((function () {
						var $ret = new $.ig.KeyTipInfo();
						$ret.action(action);
						$ret.positionCalculator($self.calculateValuePosition.runOn($self));
						$ret.tag(new $.ig.Tuple$2(Number, $.ig.RadialMenuItemPosition.prototype.$type, currentValue, itemPos));
						return $ret;
					}()));
				}
			}
			keyTips.add((function () {
				var $ret = new $.ig.KeyTipInfo();
				$ret.action(action);
				$ret.positionCalculator($self.calculateValuePosition.runOn($self));
				$ret.tag(new $.ig.Tuple$2(Number, $.ig.RadialMenuItemPosition.prototype.$type, t, itemPos));
				return $ret;
			}()));
		}
		return keyTips;
	}
	,
	getResolvedProperty: function (property) {
		if (property.propertyType() == $.ig.Brush.prototype.$type && $.ig.DependencyProperty.prototype.queryRegisteredProperty(property.name(), $.ig.RadialMenuItemBase.prototype.$type) != null) {
			return this.getValue(property);
		}
		return $.ig.RadialMenuItemBase.prototype.getResolvedProperty.call(this, property);
	}
	,
	isHotTracked: function (value) {
		if (arguments.length === 1) {
			if (value != this.isHotTracked()) {
				$.ig.RadialMenuItemBase.prototype.isHotTracked.call(this, value);
				if (value) {
					this.__endHotTrackDeferredOperation.cancelPendingOperation();
					this.setPendingValue(this.value(), false);
				} else {
					this.__endHotTrackDeferredOperation.startAsyncOperation();
				}
			}
			return value;
		} else {
			return $.ig.RadialMenuItemBase.prototype.isHotTracked.call(this);
		}
	}
	,
	isMouseDown: function (value) {
		if (arguments.length === 1) {
			if (value != $.ig.RadialMenuItemBase.prototype.isMouseDown.call(this)) {
				$.ig.RadialMenuItemBase.prototype.isMouseDown.call(this, value);
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName, null, null);
			}
			return value;
		} else {
			return $.ig.RadialMenuItemBase.prototype.isMouseDown.call(this);
		}
	}
	,
	navigateToFirstLast: function (first) {
		this.verifySortedTickMarks();
		if (this.__sortedTickMarks.count() == 0) {
			return false;
		}
		this.setPendingValue(this.__sortedTickMarks.__inner[first ? 0 : this.__sortedTickMarks.count() - 1], false);
		return true;
	}
	,
	onClick: function () {
		if (!$.ig.util.isNaN(this.pendingValue())) {
			if (this.isHotTracked()) {
				this.__hidePendingLine = true;
			}
			this.setValueImpl(this.pendingValue());
			var numParent = $.ig.util.cast($.ig.RadialMenuNumericItem.prototype.$type, this.parentItem());
			if (null != numParent && !$.ig.util.isNaN(this.value())) {
				numParent.setValueImpl(this.value());
			}
		}
		$.ig.RadialMenuItemBase.prototype.onClick.call(this);
	}
	,
	onMouseMove: function (angleForMenuRect, menuRect, itemPos, relativePoint) {
		$.ig.RadialMenuItemBase.prototype.onMouseMove.call(this, angleForMenuRect, menuRect, itemPos, relativePoint);
		var newPendingValue = this.valueFromAngle(angleForMenuRect, itemPos);
		var wasHidingPendingLine = this.__hidePendingLine;
		if (this.__hidePendingLine) {
			if ($.ig.Point.prototype.l_op_Equality(this.__lastRelativePoint, null)) {
				this.__hidePendingLine = false;
			} else if (!$.ig.ShapeUtilities.prototype.areClose(newPendingValue, this.pendingValue())) {
				this.__hidePendingLine = false;
			} else if (Math.abs(relativePoint.__x - (this.__lastRelativePoint).__x) > 16 || Math.abs(relativePoint.__y - (this.__lastRelativePoint).__y) > 16) {
				this.__hidePendingLine = false;
			}
		}
		this.__lastPendingValueAngle = angleForMenuRect;
		if (this.__hidePendingLine == false || $.ig.Point.prototype.l_op_Equality(this.__lastRelativePoint, null)) {
			this.__lastRelativePoint = relativePoint;
		}
		this.setPendingValue(newPendingValue, true);
		this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName, null, null);
		if (wasHidingPendingLine && this.__hidePendingLine == false) {
			this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.toolTipResolvedPropertyName, null, null);
		}
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushPropertyName:
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName, null, null);
				break;
			case $.ig.RadialMenuNumericGauge.prototype.pendingValuePropertyName:
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.toolTipResolvedPropertyName, null, null);
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName, null, null);
				break;
			case $.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.tickBrushPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.valuePropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.reserveFirstSlicePropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.trackStartColorPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.trackEndColorPropertyName:
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.dirtyCustomVisualsPropertyName, null, null);
				break;
			case $.ig.RadialMenuNumericGauge.prototype.ticksPropertyName:
				this.__areTickMarksVerified = false;
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.dirtyCustomVisualsPropertyName, null, null);
				break;
		}
		$.ig.RadialMenuItemBase.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
	}
	,
	processNextPrevious: function (next, wrap) {
		var pendingValue = this.pendingValue();
		if ($.ig.util.isNaN(pendingValue)) {
			return this.navigateToFirstLast(next);
		}
		var editValue = pendingValue;
		this.verifySortedTickMarks();
		if (this.__sortedTickMarks.count() > 0) {
			editValue = editValue + this.smallIncrement() * (next ? 1 : -1);
			var lastIndex = this.__sortedTickMarks.count() - 1;
			if (editValue < this.__sortedTickMarks.__inner[0]) {
				if (wrap) {
					editValue = this.__sortedTickMarks.__inner[lastIndex];
				} else {
					return false;
				}
			} else if (editValue > this.__sortedTickMarks.__inner[lastIndex]) {
				if (wrap) {
					editValue = this.__sortedTickMarks.__inner[0];
				} else {
					return false;
				}
			}
			this.setPendingValue(editValue, false);
			return true;
		}
		return false;
	}
	,
	processTabKey: function (forward) {
		var pendingValue = this.pendingValue();
		if ($.ig.util.isNaN(pendingValue)) {
			this.navigateToFirstLast(forward);
			return true;
		}
		var editValue = pendingValue;
		this.verifySortedTickMarks();
		var start = forward ? 0 : this.__sortedTickMarks.count() - 1;
		var end = forward ? this.__sortedTickMarks.count() : -1;
		var increment = forward ? 1 : -1;
		for (var i = start; i != end; i += increment) {
			if (forward && this.__sortedTickMarks.__inner[i] <= editValue) {
				continue;
			} else if (!forward && this.__sortedTickMarks.__inner[i] >= editValue) {
				continue;
			}
			this.setPendingValue(this.__sortedTickMarks.__inner[i], false);
			return true;
		}
		return false;
	}
	,
	toolTipResolved: function () {
		var tt = $.ig.RadialMenuItemBase.prototype.toolTipResolved.call(this);
		if (tt != null) {
			return tt;
		}
		var value = this.pendingValue();
		if ($.ig.util.isNaN(value)) {
			value = this.value();
		}
		if (!$.ig.util.isNaN(value)) {
			return value;
		}
		return null;
	}
	,
	updateCustomVisuals: function (itemPos, frame, itemFrame) {
		var result = $.ig.RadialMenuItemBase.prototype.updateCustomVisuals.call(this, itemPos, frame, itemFrame);
		var gaugeFrame = $.ig.util.cast($.ig.RadialMenuNumericGaugeFrame.prototype.$type, itemFrame);
		if (gaugeFrame == null) {
			return result;
		}
		var menuRect = frame.menuRect();
		var value = this.value();
		var valueOuterRect = gaugeFrame._valueOuterRect;
		var valueInnerRect = gaugeFrame._valueInnerRect;
		var editValue = this.pendingValue();
		var hotTrackLine = gaugeFrame.hotTrackLine();
		if (!$.ig.util.isNaN(editValue)) {
			var valueFromAngle = !$.ig.util.isNaN(this.__lastPendingValueAngle) ? this.valueFromAngle(this.__lastPendingValueAngle, itemPos) : NaN;
			var editValueAngle = !$.ig.util.isNaN(valueFromAngle) && valueFromAngle == editValue ? this.__lastPendingValueAngle : this.angleFromValue(itemPos, editValue);
			hotTrackLine._start = $.ig.ShapeUtilities.prototype.getPointOnEllipse(valueOuterRect, $.ig.ShapeUtilities.prototype.translateAngle(menuRect, editValueAngle, valueOuterRect));
			hotTrackLine._end = $.ig.ShapeUtilities.prototype.getPointOnEllipse(valueInnerRect, $.ig.ShapeUtilities.prototype.translateAngle(menuRect, editValueAngle, valueInnerRect));
			hotTrackLine.strokeThickness(1);
			if (this.isMouseDown()) {
				hotTrackLine.stroke(this.valueNeedleBrushResolved());
			} else if (this.__hidePendingLine && !$.ig.util.isNaN(value)) {
				hotTrackLine.stroke($.ig.RadialMenuUtilities.prototype.transparentBrush);
			} else {
				hotTrackLine.stroke(this.pendingValueNeedleBrushResolved());
			}
		} else {
			hotTrackLine.strokeThickness(0);
		}
		if (this.isMouseDown() && hotTrackLine.strokeThickness() > 0) {
			gaugeFrame.valueLine().stroke($.ig.RadialMenuUtilities.prototype.transparentBrush);
		} else if (gaugeFrame.valueLine().stroke() == $.ig.RadialMenuUtilities.prototype.transparentBrush) {
			gaugeFrame.valueLine().stroke(this.valueNeedleBrushResolved());
		}
		hotTrackLine.data(null);
		return $.ig.RadialMenuUpdateVisualResult.prototype.updateShapes;
	}
	,
	pendingValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.pendingValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.pendingValueProperty);
		}
	}
	,
	pendingValueNeedleBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushProperty);
		}
	}
	,
	pendingValueNeedleBrushResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushProperty);
	}
	,
	reserveFirstSlice: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.reserveFirstSliceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.reserveFirstSliceProperty);
		}
	}
	,
	smallIncrement: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.smallIncrementProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.smallIncrementProperty);
		}
	}
	,
	tickBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.tickBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.tickBrushProperty);
		}
	}
	,
	tickBrushResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuNumericGauge.prototype.tickBrushProperty);
	}
	,
	ticks: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.ticksProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.ticksProperty);
		}
	}
	,
	trackStartColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.trackStartColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.trackStartColorProperty);
		}
	}
	,
	trackStartColorResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuNumericGauge.prototype.trackStartColorProperty);
	}
	,
	trackEndColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.trackEndColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.trackEndColorProperty);
		}
	}
	,
	trackEndColorResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuNumericGauge.prototype.trackEndColorProperty);
	}
	,
	value: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.valueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.valueProperty);
		}
	}
	,
	valueNeedleBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushProperty);
		}
	}
	,
	valueNeedleBrushResolved: function () {
		return this.getResolvedProperty($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushProperty);
	}
	,
	hasEmptyFirstWedgeResolved: function () {
		return this.reserveFirstSlice();
	}
	,
	angleFromValue: function (itemPos, value) {
		this.verifySortedTickMarks();
		var hasEmptyWedge = this.hasEmptyFirstWedgeResolved();
		var sweepPerTick = (itemPos._endAngle - itemPos._startAngle) / (hasEmptyWedge ? this.__sortedTickMarks.count() : this.__sortedTickMarks.count() - 1);
		var angle = itemPos._startAngle;
		if (hasEmptyWedge) {
			angle += sweepPerTick;
		}
		for (var i = 1, count = this.__sortedTickMarks.count(); i < count; i++) {
			var tickMark = this.__sortedTickMarks.__inner[i];
			if (value < tickMark) {
				var prevValue = this.__sortedTickMarks.__inner[i - 1];
				var percent = (value - prevValue) / (tickMark - prevValue);
				angle += sweepPerTick * percent;
				break;
			}
			angle += sweepPerTick;
		}
		return angle;
	}
	,
	calculateValuePosition: function (keyTipSize, keyTip) {
		var t = keyTip.tag();
		var itemPos = t.item2();
		var value = t.item1();
		var angle = this.angleFromValue(itemPos, value);
		var pt = $.ig.ShapeUtilities.prototype.getPointOnEllipse(this.menu().currentFrame().menuRect(), angle);
		return { __x: pt.__x - keyTipSize.width() / 2, __y: pt.__y - keyTipSize.height() / 2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	clearEditInfo: function () {
		if (!this.isHotTracked()) {
			this.setPendingValue(NaN, false);
		}
	}
	,
	createTickLine: function (angleRect, lineOuterRect, tickOuterRect, tickBrush, ptAngle) {
		var line = (function () {
			var $ret = new $.ig.LineSegmentShape();
			$ret._start = $.ig.ShapeUtilities.prototype.getPointOnEllipse(lineOuterRect, $.ig.ShapeUtilities.prototype.translateAngle(angleRect, ptAngle, lineOuterRect));
			$ret._end = $.ig.ShapeUtilities.prototype.getPointOnEllipse(tickOuterRect, $.ig.ShapeUtilities.prototype.translateAngle(angleRect, ptAngle, tickOuterRect));
			return $ret;
		}());
		line.strokeThickness(1);
		line.stroke(tickBrush);
		return line;
	}
	,
	createTickText: function (menuRect, textRect, value, tickAngle) {
		var tb = new $.ig.TextDefinition();
		tb._horizontalAlignment = $.ig.HorizontalAlignment.prototype.center;
		tb._verticalAlignment = $.ig.VerticalAlignment.prototype.center;
		tb._text = value.toString();
		tb._location = $.ig.ShapeUtilities.prototype.getPointOnEllipse(textRect, $.ig.ShapeUtilities.prototype.translateAngle(menuRect, tickAngle, textRect));
		return tb;
	}
	,
	onItemsPropertyChanged: function (sender, e) {
		switch (e.propertyName()) {
			case "Count":
				this.__areTickMarksVerified = false;
				break;
		}
	}
	,
	prepareSortedTickMarks: function () {
		this.__areTickMarksVerified = true;
		this.__sortedTickMarks.clear();
		var ticks = this.ticks();
		if (null != ticks) {
			this.__sortedTickMarks.addRange(ticks);
		}
		this.__sortedTickMarks.sort();
	}
	,
	setPendingValue: function (newValue, retainPendingInfo) {
		if (retainPendingInfo != true) {
			this.__lastPendingValueAngle = NaN;
			this.__lastRelativePoint = null;
			this.__hidePendingLine = false;
		}
		if (newValue == this.pendingValue()) {
			return;
		}
		this.pendingValue(newValue);
	}
	,
	setValueImpl: function (newValue) {
		this.value(newValue);
	}
	,
	verifySortedTickMarks: function () {
		if (!this.__areTickMarksVerified) {
			this.prepareSortedTickMarks();
		}
	}
	,
	valueFromAngle: function (angle, itemPos) {
		this.verifySortedTickMarks();
		var tickCount = this.__sortedTickMarks.count();
		if (itemPos == null || tickCount == 0) {
			return NaN;
		}
		var value;
		if (tickCount == 1) {
			value = this.__sortedTickMarks.__inner[0];
		} else {
			var hasEmptyWedge = this.hasEmptyFirstWedgeResolved();
			var sweepPerTick = (itemPos._endAngle - itemPos._startAngle) / (hasEmptyWedge ? tickCount : tickCount - 1);
			var valueAngle = itemPos._startAngle;
			if (valueAngle < -Math.PI) {
				valueAngle += $.ig.ShapeUtilities.prototype.twoPI;
			}
			if (angle < valueAngle) {
				angle += $.ig.ShapeUtilities.prototype.twoPI;
			}
			if (hasEmptyWedge) {
				valueAngle += sweepPerTick;
			}
			if (angle < valueAngle) {
				value = this.__sortedTickMarks.__inner[0];
			} else {
				value = this.__sortedTickMarks.__inner[tickCount - 1];
				for (var i = 0; i < tickCount - 1; i++) {
					if (angle < valueAngle + sweepPerTick) {
						var percent = 1 - (valueAngle + sweepPerTick - angle) / sweepPerTick;
						var previous = this.__sortedTickMarks.__inner[i];
						var next = this.__sortedTickMarks.__inner[i + 1];
						var newValue = (next - previous) * percent + previous;
						var mod = newValue % this.smallIncrement();
						newValue -= mod;
						if (Math.abs(mod) > this.smallIncrement() / 2) {
							newValue += this.smallIncrement();
						}
						value = newValue;
						break;
					}
					valueAngle += sweepPerTick;
				}
			}
		}
		return value;
	}
	,
	pendingValueChanged: null,
	valueChanged: null,
	$type: new $.ig.Type('RadialMenuNumericGauge', $.ig.RadialMenuItemBase.prototype.$type)
}, true);

$.ig.util.defType('XamRadialMenuView', 'Object', {
	__ignorePropertyChanged: false,
	__canvasWidth: 0,
	__canvasHeight: 0,
	__isScheduled: false,
	_menu: null,
	menu: function (value) {
		if (arguments.length === 1) {
			this._menu = value;
			return value;
		} else {
			return this._menu;
		}
	}
	,
	__canvas: null,
	__context: null,
	__container: null,
	__font: null,
	_menuCache: null,
	__usedProperties: null,
	_itemCache: null,
	_toolTipCache: null,
	_eventProxy: null,
	_windowEventNs: null,
	__centerButtonBackPath: null,
	__centerButtonImage: null,
	__centerImageSize: null,
	__isItemPathPanelSorted: false,
	__viewPortResolved: null,
	__lastCenter: null,
	staticInit: function () {
		var root = new $.ig.CssClassChain$1($.ig.DependencyProperty.prototype.$type, "ui-radialmenu");
		root.addProperty($.ig.XamRadialMenu.prototype.fontProperty, $.ig.CssProperty.prototype.backgroundColor, "12px segoe ui,tahoma,arial,sans-serif").addChildClass("ui-radialmenu-backing").addProperty($.ig.XamRadialMenu.prototype.menuBackgroundProperty, $.ig.CssProperty.prototype.backgroundColor, "#FFFFFF")._parent.addChildClass("ui-radialmenu-outerring").addProperty($.ig.XamRadialMenu.prototype.outerRingFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#F1DAEA").addProperty($.ig.XamRadialMenu.prototype.outerRingStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "transparent").addProperty($.ig.XamRadialMenu.prototype.outerRingStrokeThicknessProperty, $.ig.CssProperty.prototype.borderTopWidth, "0.0")._parent.addChildClass("ui-radialmenu-centerbutton").addProperty($.ig.XamRadialMenu.prototype.centerButtonFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#FFFFFF").addProperty($.ig.XamRadialMenu.prototype.centerButtonStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "#80397B").addProperty($.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessProperty, $.ig.CssProperty.prototype.borderTopWidth, "1.0").addChildClass("ui-radialmenu-centerbutton-hover").addProperty($.ig.XamRadialMenu.prototype.centerButtonHotTrackFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#EFEFEF").addProperty($.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "#80397B")._parent.addChildClass("ui-radialmenu-centerbutton-closed").addProperty($.ig.XamRadialMenu.prototype.centerButtonClosedFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#FFFFFF").addProperty($.ig.XamRadialMenu.prototype.centerButtonClosedStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "#80397B");
		$.ig.XamRadialMenuView.prototype._menuCssClassChain = root;
		root = new $.ig.CssClassChain$1($.ig.DependencyProperty.prototype.$type, null);
		root.addChildClass("ui-radialmenu-item").addProperty($.ig.RadialMenuItemBase.prototype.foregroundProperty, $.ig.CssProperty.prototype.color, "black").addChildClass("ui-radialmenu-item-inner").addProperty($.ig.RadialMenuItemBase.prototype.innerAreaFillProperty, $.ig.CssProperty.prototype.backgroundColor, "transparent").addProperty($.ig.RadialMenuItemBase.prototype.innerAreaStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "transparent").addProperty($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessProperty, $.ig.CssProperty.prototype.borderTopWidth, "1.0").addChildClass("ui-radialmenu-item-inner-hover").addProperty($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillProperty, $.ig.CssProperty.prototype.backgroundColor, "transparent").addProperty($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "transparent")._parent._parent.addChildClass("ui-radialmenu-item-outer").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#80397B").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "transparent").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundProperty, $.ig.CssProperty.prototype.color, "white").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessProperty, $.ig.CssProperty.prototype.borderTopWidth, "1.0").addChildClass("ui-radialmenu-item-outer-hover").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillProperty, $.ig.CssProperty.prototype.backgroundColor, "#444444").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundProperty, $.ig.CssProperty.prototype.color, "white").addProperty($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokeProperty, $.ig.CssProperty.prototype.borderTopColor, "transparent")._parent._parent.addChildClass("ui-radialmenu-item-checkarc").addProperty($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushProperty, $.ig.CssProperty.prototype.backgroundColor, "#A674A2")._parent.addChildClass("ui-radialmenu-item-hoverarc").addProperty($.ig.RadialMenuItemBase.prototype.highlightBrushProperty, $.ig.CssProperty.prototype.backgroundColor, "#80397B")._parent._parent.addChildClass("ui-radialmenu-gauge").addChildClass("ui-radialmenu-gauge-pendingneedle").addProperty($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushProperty, $.ig.CssProperty.prototype.backgroundColor, "#D6A9CE")._parent.addChildClass("ui-radialmenu-gauge-valueneedle").addProperty($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushProperty, $.ig.CssProperty.prototype.backgroundColor, "#A26497")._parent.addChildClass("ui-radialmenu-gauge-tick").addProperty($.ig.RadialMenuNumericGauge.prototype.tickBrushProperty, $.ig.CssProperty.prototype.backgroundColor, "#AE5A9D")._parent.addChildClass("ui-radialmenu-gauge-track-start").addProperty($.ig.RadialMenuNumericGauge.prototype.trackStartColorProperty, $.ig.CssProperty.prototype.color, "rgba(200,142,188,0)")._parent.addChildClass("ui-radialmenu-gauge-track-end").addProperty($.ig.RadialMenuNumericGauge.prototype.trackEndColorProperty, $.ig.CssProperty.prototype.color, "#C88EBC");
		$.ig.XamRadialMenuView.prototype._itemCssClassChain = root;
		var cssRoot = new $.ig.CssClassChain$1($.ig.CssProperty.prototype.$type, "ui-radialmenu-tooltip");
		cssRoot.addProperty($.ig.CssProperty.prototype.borderTopColor, $.ig.CssProperty.prototype.borderTopColor, "").addProperty($.ig.CssProperty.prototype.borderTopWidth, $.ig.CssProperty.prototype.borderTopWidth, "").addProperty($.ig.CssProperty.prototype.borderTopLeftRadius, $.ig.CssProperty.prototype.borderTopLeftRadius, "").addProperty($.ig.CssProperty.prototype.borderTopStyle, $.ig.CssProperty.prototype.borderTopStyle, "").addProperty($.ig.CssProperty.prototype.backgroundColor, $.ig.CssProperty.prototype.backgroundColor, "").addProperty($.ig.CssProperty.prototype.color, $.ig.CssProperty.prototype.color, "").addProperty($.ig.CssProperty.prototype.paddingTop, $.ig.CssProperty.prototype.paddingTop, "");
		$.ig.XamRadialMenuView.prototype._toolTipCssClassChain = cssRoot;
	},
	init: function (menu) {
		this.__ignorePropertyChanged = false;
		this.__canvasWidth = -1;
		this.__canvasHeight = -1;
		this.__isScheduled = false;
		this.__centerImageSize = new $.ig.Size();
		this.__viewPortResolved = new $.ig.LiteRect(1, 0, 0, 0, 0);
		this.__lastCenter = new $.ig.Point(0);
		this.__itemViewTable = new $.ig.Dictionary$2($.ig.RadialMenuItemBase.prototype.$type, $.ig.RadialMenuItemView.prototype.$type, 0);
		this.__visibleItems = new $.ig.List$1($.ig.RadialMenuItemView.prototype.$type, 0);
		this.__toolTipData = new $.ig.RadialMenuItemToolTipVisualData();
		this.__currentFontHeight = NaN;
		$.ig.Object.prototype.init.call(this);
		this.menu(menu);
		this.__usedProperties = new $.ig.Dictionary$2(String, $.ig.Boolean.prototype.$type, 0);
		this._menuCache = new $.ig.CssDpCache($.ig.XamRadialMenuView.prototype._menuCssClassChain);
		this._itemCache = new $.ig.CssDpCache($.ig.XamRadialMenuView.prototype._itemCssClassChain);
		this._toolTipCache = new $.ig.CssPropCache$1($.ig.CssProperty.prototype.$type, $.ig.XamRadialMenuView.prototype._toolTipCssClassChain);
		$.ig.XamRadialMenuView.prototype.__radialMenuCount++;
		this._windowEventNs = "RadialMenuWindow" + $.ig.XamRadialMenuView.prototype.__radialMenuCount;
	},
	arrange: function () {
		if (this.__isScheduled) {
			this.__isScheduled = false;
			this.menu().updateView();
		}
	}
	,
	flush: function () {
		if (this.__isScheduled) {
			this.arrange();
		}
	}
	,
	onInit: function () {
		this.__centerButtonBackPath = this.createCenterButtonBackPath();
		this.menu().centerButtonPanel().children().add(this.__centerButtonBackPath);
		this.initializeCenterButton();
		this.updateBrushes();
	}
	,
	scheduleArrange: function () {
		if (!this.__isScheduled) {
			this.__isScheduled = true;
			window.setTimeout(this.arrange.runOn(this), 0);
		}
	}
	,
	arrangeComplete: function () {
		this.render();
	}
	,
	pathDeactivate: function (p) {
		p.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	pathActivate: function (p) {
		p.__visibility = $.ig.Visibility.prototype.visible;
	}
	,
	customPathCreate: function () {
		var p = new $.ig.Path();
		this.menu().customItemPathPanel().children().add(p);
		return p;
	}
	,
	customPathDestroy: function (p) {
		var pnl = $.ig.util.cast($.ig.Panel.prototype.$type, p.parent());
		if (pnl != null) {
			pnl.children().remove(p);
		}
	}
	,
	pathCreate1: function (part) {
		var p = new $.ig.Path();
		var zIndex = 0;
		switch (part) {
			case $.ig.RadialMenuWedgePart.prototype.outerRing:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeOuterRing;
				break;
			case $.ig.RadialMenuWedgePart.prototype.outerRingArrow:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeOuterRingArrow;
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolArea:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeToolArea;
				break;
			case $.ig.RadialMenuWedgePart.prototype.colorWellPreview:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeColorWell;
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolCheckmark:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeToolCheckmark;
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolHighlight:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexWedgeToolHighlight;
				break;
		}
		this.__isItemPathPanelSorted = false;
		p.canvasZIndex(zIndex);
		this.menu().itemPathPanel().children().add(p);
		return p;
	}
	,
	pathDestroy1: function (part, p) {
		var pnl = $.ig.util.cast($.ig.Panel.prototype.$type, p.parent());
		if (pnl != null) {
			pnl.children().remove(p);
		}
	}
	,
	pathCreate: function (part) {
		var p = new $.ig.Path();
		var panel = this.menu().outerPathPanel();
		var zIndex = 0;
		switch (part) {
			default:
			case $.ig.RadialMenuPart.prototype.menu:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexMenu;
				break;
			case $.ig.RadialMenuPart.prototype.outerRing:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexMenuOuterRing;
				break;
			case $.ig.RadialMenuPart.prototype.innerRing:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexMenuInnerRing;
				panel = this.menu().centerButtonPanel();
				break;
			case $.ig.RadialMenuPart.prototype.innerRingFocus:
				zIndex = $.ig.RadialMenuUtilities.prototype.zIndexMenuInnerRingFocus;
				panel = this.menu().centerButtonPanel();
				break;
		}
		p.canvasZIndex(zIndex);
		panel.children().add(p);
		return p;
	}
	,
	pathDestroy: function (part, p) {
		var pnl = $.ig.util.cast($.ig.Panel.prototype.$type, p.parent());
		if (pnl != null) {
			pnl.children().remove(p);
		}
	}
	,
	labelDeactivate: function (t) {
		t.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	labelActivate: function (t) {
		t.__visibility = $.ig.Visibility.prototype.visible;
	}
	,
	labelCreate: function () {
		var t = new $.ig.TextBlock();
		this.menu().customItemPathPanel().children().add(t);
		return t;
	}
	,
	labelDestroy: function (t) {
		var pnl = $.ig.util.cast($.ig.Panel.prototype.$type, t.parent());
		if (pnl != null) {
			pnl.children().remove(t);
		}
	}
	,
	createCenterButtonBackPath: function () {
		var backPath = new $.ig.Path();
		backPath.strokeThickness(3);
		backPath.actualWidth(20);
		backPath.actualHeight(14);
		var geo = new $.ig.PathGeometry();
		var fig1 = new $.ig.PathFigure();
		fig1.__startPoint = { __x: 8.5, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		fig1.__segments.add(new $.ig.LineSegment(0, { __x: 2, __y: 6.5, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }));
		fig1.__segments.add(new $.ig.LineSegment(0, { __x: 8.5, __y: 13, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }));
		geo.figures().add(fig1);
		var fig2 = new $.ig.PathFigure();
		fig2.__startPoint = { __x: 2, __y: 6.5, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		fig2.__segments.add(new $.ig.LineSegment(0, { __x: 20, __y: 6.5, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }));
		geo.figures().add(fig2);
		backPath.data(geo);
		backPath.renderTransform(new $.ig.TranslateTransform());
		return backPath;
	}
	,
	shouldUpdateRender: function () {
		return true;
	}
	,
	onLevelCountChanged: function () {
		this.initializeCenterButton();
	}
	,
	__itemViewTable: null,
	__visibleItems: null,
	onVisibleItemPositionsChanged: function (visibleItems) {
		var $self = this;
		this.__visibleItems.clear();
		for (var i = 0, count = visibleItems.count(); i < count; i++) {
			var itemPos = visibleItems.__inner[i];
			var item = itemPos.item();
			var view;
			if (!(function () { var $ret = $self.__itemViewTable.tryGetValue(item, view); view = $ret.p1; return $ret.ret; }())) {
				view = new $.ig.RadialMenuItemView(item, this);
			} else {
				this.__itemViewTable.remove(item);
			}
			view._position = itemPos;
			this.__visibleItems.add(view);
		}
		var en = this.__itemViewTable.values().getEnumerator();
		while (en.moveNext()) {
			var view1 = en.current();
			view1.dispose();
		}
		this.__itemViewTable.clear();
		var en1 = this.__visibleItems.getEnumerator();
		while (en1.moveNext()) {
			var view2 = en1.current();
			var item1 = view2.item();
			this.__itemViewTable.item(item1, view2);
		}
	}
	,
	rootItems: function () {
		return this.menu().items();
	}
	,
	positionCenterContent: function (center) {
		var tt = this.__centerButtonBackPath.renderTransform();
		tt.x(center.__x - this.__centerButtonBackPath.actualWidth() / 2);
		tt.y(center.__y - this.__centerButtonBackPath.actualHeight() / 2);
	}
	,
	getCenterButtonSize: function () {
		var size = new $.ig.Size(1, this.menu().centerButtonContentWidth(), this.menu().centerButtonContentHeight());
		if ($.ig.util.isNaN(size.width())) {
			if (!this.__centerImageSize.isEmpty()) {
				size.width(Math.max(28, this.__centerImageSize.width()));
			} else {
				size.width(28);
			}
		}
		if ($.ig.util.isNaN(size.height())) {
			if (!this.__centerImageSize.isEmpty()) {
				size.height(Math.max(28, this.__centerImageSize.height()));
			} else {
				size.height(28);
			}
		}
		return size;
	}
	,
	onMenuPropertyChanged: function (propertyName, oldValue, newValue) {
		var $self = this;
		if (!this.__ignorePropertyChanged) {
			var dp = $.ig.DependencyProperty.prototype.queryRegisteredProperty(propertyName, $.ig.XamRadialMenu.prototype.$type);
			if (null != dp) {
				if (this._menuCache.getValues().containsKey(dp)) {
					var isSet = this.menu().readLocalValue(dp) != $.ig.DependencyProperty.prototype.unsetValue;
					this.__usedProperties.item(propertyName, isSet);
					if (!isSet) {
						this.__ignorePropertyChanged = true;
						var cssValue;
						if ((function () { var $ret = $self._menuCache.getValues().tryGetValue(dp, cssValue); cssValue = $ret.p1; return $ret.ret; }())) {
							this.menu().setValue(dp, cssValue);
						}
						this.__ignorePropertyChanged = false;
					}
				}
			}
		}
		switch (propertyName) {
			case $.ig.XamRadialMenu.prototype.centerButtonContentPropertyName:
				if (this.__centerButtonImage != null) {
					this.menu().centerButtonPanel().children().remove1(this.__centerButtonImage);
					this.__centerButtonImage = null;
					this.__centerImageSize = $.ig.Size.prototype.empty();
				}
				if (!String.isNullOrEmpty(newValue)) {
					this.__centerButtonImage = $.ig.ImageManager.prototype.global.getImage(newValue, this.onCenterButtonImageReady.runOn(this));
				}
				this.initializeCenterButton();
				break;
			case $.ig.XamRadialMenu.prototype.centerButtonContentHeightPropertyName:
			case $.ig.XamRadialMenu.prototype.centerButtonContentWidthPropertyName:
				this.initializeCenterButton();
				break;
		}
	}
	,
	onCenterButtonImageReady: function (img) {
		var img_ = img;
		this.__centerImageSize = new $.ig.Size(1, img_.width, img_.height);
		this.menu().dirtyLayout();
	}
	,
	initializeCenterButton: function () {
		var backPath = this.__centerButtonBackPath;
		if (this.menu().levelCount() < 2) {
			backPath.__visibility = $.ig.Visibility.prototype.collapsed;
		} else {
			backPath.__visibility = $.ig.Visibility.prototype.visible;
		}
	}
	,
	__shouldHookWindow: false,
	__hookedWindow: null,
	shouldHookWindow: function (value) {
		if (arguments.length === 1) {
			if (value != this.__shouldHookWindow) {
				this.__shouldHookWindow = value;
				if (!value && this.__hookedWindow != null) {
					var old = this.__hookedWindow;
					this.__hookedWindow = null;
					this._eventProxy.unbindFromSource($(old), this._windowEventNs);
				} else if (value && this.__hookedWindow == null) {
					this.__hookedWindow = window;
					this._eventProxy.bindToSource($(this.__hookedWindow), this._windowEventNs);
				}
			}
			return value;
		} else {
			return this.__shouldHookWindow;
		}
	}
	,
	onContainerProvided: function (container) {
		var $self = this;
		var oldCont = this.__container;
		var cont = container == null ? null : $(container);
		this._toolTipCache.rootObject(this._itemCache.rootObject(this._menuCache.rootObject(this.__container = cont)));
		var mouseEnterHandler = function (pt) {
			$self.menu().onMouseEnter($self.adjustCollapsedPoint(pt));
		};
		var mouseLeaveHandler = function (pt) {
			if (!$self.__shouldHookWindow) {
				$self.menu().onMouseLeave();
			}
		};
		var mouseOverHandler = function (pt, onMouseMove, isFinger) {
			$self.menu().onMouseMove($self.adjustCollapsedPoint(pt));
		};
		var mouseDownHandler = function (pt) {
			if (!$self.shouldHookWindow()) {
				$self.shouldHookWindow(true);
				$self.menu().onMouseDown($self.adjustCollapsedPoint(pt), $self);
			}
		};
		var mouseUpHandler = function (pt) {
			if ($self.shouldHookWindow()) {
				$self.shouldHookWindow(false);
				$self.menu().onMouseUp($self.adjustCollapsedPoint(pt), $self);
			}
		};
		if (this._eventProxy != null) {
			this.shouldHookWindow(false);
			this._eventProxy.destroy();
			var $t = this._eventProxy;
			$t.onMouseEnter = $.ig.Delegate.prototype.remove($t.onMouseEnter, mouseEnterHandler);
			var $t1 = this._eventProxy;
			$t1.onMouseLeave = $.ig.Delegate.prototype.remove($t1.onMouseLeave, mouseLeaveHandler);
			var $t2 = this._eventProxy;
			$t2.onMouseOver = $.ig.Delegate.prototype.remove($t2.onMouseOver, mouseOverHandler);
			var $t3 = this._eventProxy;
			$t3.onMouseDown = $.ig.Delegate.prototype.remove($t3.onMouseDown, mouseDownHandler);
			var $t4 = this._eventProxy;
			$t4.onMouseUp = $.ig.Delegate.prototype.remove($t4.onMouseUp, mouseUpHandler);
		}
		if (cont == null) {
			return;
		}
		var width = Math.round(cont.width());
		var height = Math.round(cont.height());
		this.__container.css("position", "relative");
		this.__container.css("visibility", "hidden");
		var canv = $("<canvas style='position:absolute'></canvas>");
		this.__container.append(canv);
		this.__canvasWidth = $.ig.truncate(width);
		this.__canvasHeight = $.ig.truncate(height);
		canv.attr("width", width.toString());
		canv.attr("height", height.toString());
		canv.css("visibility", "visible");
		this.menu().viewport(new $.ig.LiteRect(1, 0, 0, width, height));
		this._eventProxy = new $.ig.RadialMenuDOMEventProxy(canv);
		this._eventProxy.shouldInteract(this.shouldInteract.runOn(this));
		var $t5 = this._eventProxy;
		$t5.onMouseEnter = $.ig.Delegate.prototype.combine($t5.onMouseEnter, mouseEnterHandler);
		var $t6 = this._eventProxy;
		$t6.onMouseLeave = $.ig.Delegate.prototype.combine($t6.onMouseLeave, mouseLeaveHandler);
		var $t7 = this._eventProxy;
		$t7.onMouseOver = $.ig.Delegate.prototype.combine($t7.onMouseOver, mouseOverHandler);
		var $t8 = this._eventProxy;
		$t8.onMouseDown = $.ig.Delegate.prototype.combine($t8.onMouseDown, mouseDownHandler);
		var $t9 = this._eventProxy;
		$t9.onMouseUp = $.ig.Delegate.prototype.combine($t9.onMouseUp, mouseUpHandler);
		this.__canvas = canv;
		this._eventProxy.viewport($.ig.LiteRectExtensions.prototype.toRect(this.menu().viewport()));
		var canvasContext = (this.__canvas[0]).getContext("2d");
		this.__context = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), canvasContext);
		this.getDefaultColors();
		this.updateFont();
		if (this.menu().isOpen()) {
			this.onBeforeOpen();
		} else {
			this.onAfterClose();
		}
		this.render();
	}
	,
	onContainerResized: function () {
		var width = Math.round(this.__container.width());
		var height = Math.round(this.__container.height());
		this.menu().viewport(new $.ig.LiteRect(1, 0, 0, width, height));
	}
	,
	onBeforeOpen: function () {
		this.__viewPortResolved = this.menu().viewport();
		this.__lastCenter = this.menu().currentFrame().innerRing()._center;
		this.onViewportChanged();
	}
	,
	onAfterClose: function () {
		var ring = this.menu().currentFrame().innerRing();
		var ringThickness = this.menu().centerButtonStrokeThickness() + 1;
		this.__lastCenter = ring._center;
		this.__viewPortResolved = new $.ig.LiteRect(1, Math.floor(this.__lastCenter.__x - ring._radiusX) - ringThickness, Math.floor(this.__lastCenter.__y - ring._radiusY) - ringThickness, Math.ceil((ring._radiusX + ringThickness) * 2), Math.ceil((ring._radiusY + ringThickness) * 2));
		this.onViewportChanged();
	}
	,
	onViewportChanged: function () {
		var viewport = this.menu().viewport();
		var actual = this.__viewPortResolved;
		if (this.__canvas != null) {
			this.__canvas.attr("width", actual.width.toString());
			this.__canvas.attr("height", actual.height.toString());
			this.__canvas.css("left", actual.x.toString() + "px");
			this.__canvas.css("top", actual.y.toString() + "px");
		}
		this._eventProxy.viewport(new $.ig.Rect(0, 0, 0, actual.width, actual.height));
		this.render();
	}
	,
	render: function () {
		var menu = this.menu();
		var viewport = this.__viewPortResolved;
		var innerRing = menu.currentFrame().innerRing();
		var center = innerRing._center;
		if ((center.__x != this.__lastCenter.__x || center.__y != this.__lastCenter.__y) && !menu.isOpen()) {
			this.onAfterClose();
		}
		var ctx = this.__context;
		ctx.clearRectangle(0, 0, viewport.width, viewport.height);
		if (viewport.x != 0 || viewport.y != 0) {
			ctx.save();
			var transform = new $.ig.TranslateTransform();
			transform.x(-viewport.x);
			transform.y(-viewport.y);
			ctx.applyTransform(transform);
		}
		this.renderChildren(ctx, menu.outerPathPanel(), true, null, null);
		if (!this.__isItemPathPanelSorted) {
			this.__isItemPathPanelSorted = true;
			menu.itemPathPanel().children().sort2(this.sortByZIndex.runOn(this));
		}
		this.renderChildren(ctx, menu.itemPathPanel(), true, null, null);
		this.renderChildren(ctx, menu.customItemPathPanel(), false, $.ig.RadialMenuUtilities.prototype.createBrush($.ig.Color.prototype.fromArgb(255, 0, 0, 0)), null);
		this.renderChildren(ctx, menu.itemPanel(), false, null, this.renderItemsImpl.runOn(this));
		this.renderChildren(ctx, menu.centerButtonPanel(), false, null, this.renderCenterImageImpl.runOn(this));
		if (viewport.x != 0 || viewport.y != 0) {
			ctx.restore();
		}
	}
	,
	renderItemsImpl: function (ctx, pnl) {
		var en = this.__visibleItems.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			item.draw(ctx, this.__currentFontHeight, pnl.__opacity);
		}
	}
	,
	renderCenterImageImpl: function (ctx, pnl) {
		if (this.__centerButtonImage != null && $.ig.Size.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Size.prototype.$type, this.__centerImageSize), $.ig.util.toNullable($.ig.Size.prototype.$type, null)) && this.__centerButtonBackPath.__visibility == $.ig.Visibility.prototype.collapsed) {
			var center = this.menu().currentFrame().center();
			var width = this.__centerImageSize.width();
			var height = this.__centerImageSize.height();
			if (width > 0 && height > 0) {
				ctx.drawImage(this.__centerButtonImage, 1, center.__x - width / 2, center.__y - height / 2, width, height);
			}
		}
	}
	,
	renderChildren: function (ctx, pnl, pathsOnly, defaultTextBrush, afterItemCallback) {
		if (pnl.__opacity > 0) {
			if (pnl.renderTransform() != null) {
				ctx.save();
				ctx.applyTransform(pnl.renderTransform());
			}
			if (pnl.__opacity < 1) {
				ctx.setOpacity(pnl.__opacity);
			}
			var en = pnl.children().getEnumerator();
			while (en.moveNext()) {
				var child = en.current();
				if (pathsOnly || $.ig.util.cast($.ig.Path.prototype.$type, child) !== null) {
					this.renderPath(ctx, child);
				} else if ($.ig.util.cast($.ig.Image.prototype.$type, child) !== null) {
					var img = child;
					ctx.drawImage(img, img.__opacity, img.canvasLeft(), img.canvasTop(), img.actualWidth(), img.height());
				} else if ($.ig.util.cast($.ig.TextBlock.prototype.$type, child) !== null) {
					var tb = child;
					if (tb.fill() == null) {
						tb.fill(defaultTextBrush);
					}
					ctx.renderTextBlock(tb);
				}
			}
			if (null != afterItemCallback) {
				afterItemCallback(ctx, pnl);
			}
			if (pnl.__opacity < 1) {
				ctx.setOpacity(1);
			}
			if (pnl.renderTransform() != null) {
				ctx.restore();
			}
		}
	}
	,
	renderPath: function (c, p) {
		if (p.renderTransform() != null) {
			c.save();
			c.applyTransform(p.renderTransform());
		}
		c.renderPath(p);
		if (p.renderTransform() != null) {
			c.restore();
		}
	}
	,
	updateBrushes: function () {
		this.__ignorePropertyChanged = true;
		var en = this._menuCache.getValues().getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			if (!this.__usedProperties.item(pair.key().name())) {
				this.menu().setValue(pair.key(), pair.value());
			}
		}
		this.__ignorePropertyChanged = false;
	}
	,
	getDefaultColors: function () {
		this._menuCache.refreshCssValues();
		this._itemCache.refreshCssValues();
		this._toolTipCache.refreshCssValues();
		this.updateBrushes();
	}
	,
	styleUpdated: function () {
		this.getDefaultColors();
		this.updateFont();
		this.menu().dirtyLayout();
	}
	,
	updateFont: function () {
		this.__font = $.ig.FontUtil.prototype.toFontInfo(this.menu().font());
		if (this.__font == null && this.__container != null) {
			this.__font = $.ig.FontUtil.prototype.getFont(this.__container);
		}
		this.__currentFontHeight = this.getCurrentFontHeight(this.__font);
		if (this.__context != null && this.__font != null) {
			this.__context.setFontInfo(this.__font);
		}
	}
	,
	__toolTip: null,
	showToolTip: function (item) {
		if (!item.isToolTipEnabled()) {
			this.hideToolTip();
			return;
		}
		var tt = this.__toolTip;
		if (tt == null) {
			this.__toolTip = tt = $("<div class='ui-radialmenu-tooltip ui-corner-all'></div>");
			tt.css("position", "absolute");
			tt.css("visibility", "visible");
			tt.css("white-space", "nowrap");
			tt.css("z-index", "10000");
			this.__container.append(tt);
		}
		tt.children().remove();
		var ttText = item.toolTipResolved();
		if (ttText != null) {
			tt.append($("<span>" + ttText.toString() + "</span>"));
		}
		this.__toolTipData.toolTipText(ttText == null ? String.empty() : ttText.toString());
		this.__toolTipData.itemName(item.name());
		this.__toolTipData.itemType(item.getType().typeName());
		var values = this._toolTipCache.getValues();
		var well = $.ig.util.cast($.ig.RadialMenuColorWell.prototype.$type, item);
		var en = values.getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			if (String.isNullOrEmpty(pair.value())) {
				var cssName = null;
				var defValue = null;
				switch (pair.key()) {
					case $.ig.CssProperty.prototype.backgroundColor:
						cssName = "background-color";
						defValue = well == null ? "white" : well.outerRingButtonFillResolved().__fill;
						break;
					case $.ig.CssProperty.prototype.paddingTop:
						cssName = "padding";
						defValue = "8px 11px";
						break;
					case $.ig.CssProperty.prototype.color:
						cssName = "color";
						defValue = well == null ? "#666666" : well.outerRingButtonForegroundResolved().__fill;
						break;
					case $.ig.CssProperty.prototype.borderTopColor:
						cssName = "border-color";
						defValue = "#808080";
						break;
					case $.ig.CssProperty.prototype.borderTopWidth:
						cssName = "border-width";
						defValue = "2px";
						break;
					case $.ig.CssProperty.prototype.borderTopLeftRadius:
						cssName = "border-radius";
						defValue = "0";
						break;
					case $.ig.CssProperty.prototype.borderTopStyle:
						cssName = "border-style";
						defValue = "solid";
						break;
				}
				if (cssName != null) {
					this.__toolTip.css(cssName, defValue);
				}
			}
		}
		var pt = this.getToolTipPos(tt);
		tt.css("top", pt.__y.toString() + "px");
		tt.css("left", pt.__x.toString() + "px");
	}
	,
	__toolTipData: null,
	getToolTipPos: function (toolTip) {
		var container_ = this.__container[0];
		var clientRect_ = container_.getBoundingClientRect();
		var elemRect = new $.ig.LiteRect(1, clientRect_.left, clientRect_.top, clientRect_.width, clientRect_.height);
		var origRect = new $.ig.LiteRect(1, elemRect.x, elemRect.y, elemRect.width, elemRect.height);
		var docEle_ = document.documentElement;
		var screenRect = new $.ig.LiteRect(1, 0, 0, docEle_.clientWidth, docEle_.clientHeight);
		if (!this.menu().isOpen()) {
			var centerSize = this.getCenterButtonSize();
			elemRect = new $.ig.LiteRect(1, elemRect.x + elemRect.width / 2 - centerSize.width() / 2, elemRect.y + elemRect.height / 2 - centerSize.height() / 2, centerSize.width(), centerSize.height());
			elemRect = $.ig.ShapeUtilities.prototype.inflateSafe(elemRect, this.menu().outerRingThickness(), this.menu().outerRingThickness());
		}
		elemRect = $.ig.ShapeUtilities.prototype.inflateSafe(elemRect, 6, 6);
		var width = toolTip.outerWidth();
		var height = toolTip.outerHeight();
		var x, y;
		if (elemRect.y - screenRect.y >= height) {
			y = elemRect.y - origRect.y - height;
			x = (elemRect.width - width) / 2 + elemRect.x - origRect.x;
		} else if (elemRect.x + elemRect.width + width <= screenRect.x + screenRect.width) {
			x = (elemRect.x + elemRect.width) - origRect.x;
			y = (elemRect.height - height) / 2 + elemRect.y - origRect.y;
		} else if (elemRect.x - width >= screenRect.x) {
			x = elemRect.x - origRect.x - width;
			y = (elemRect.height - height) / 2 + elemRect.y - origRect.y;
		} else {
			y = (elemRect.y + elemRect.height) - origRect.y;
			x = (elemRect.width - width) / 2 + elemRect.x - origRect.x;
		}
		this.__toolTipData.relativeLeft(x - origRect.x);
		this.__toolTipData.relativeTop(y - origRect.y);
		this.__toolTipData.width(origRect.width);
		this.__toolTipData.height(origRect.height);
		return { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	hideToolTip: function () {
		if (this.__toolTip != null) {
			var tt = this.__toolTip;
			this.__toolTip = null;
			tt.remove();
		}
	}
	,
	restoreFocus: function () {
	}
	,
	isFocused: function () {
		return false;
	}
	,
	hideKeyTips: function () {
	}
	,
	showKeyTips: function (keyTips) {
	}
	,
	isAltKey: function (key) {
		return false;
	}
	,
	measureText: function (tb) {
		if ($.ig.util.isNaN(this.__currentFontHeight)) {
			this.__currentFontHeight = this.getCurrentFontHeight(null);
		}
		var width = this.getDesiredWidth(tb);
		var height = this.__currentFontHeight;
		return new $.ig.Size(1, width, height);
	}
	,
	__currentFontHeight: 0,
	getCurrentFontHeight: function (font) {
		var tempSpan_ = $("<span>M</span>");
		var doc = $('body');
		doc.append(tempSpan_);
		if (font != null) {
			tempSpan_.css("font", font.fontString());
		}
		var offset_ = tempSpan_.attr("offsetHeight");
		if (isNaN(offset_)) { offset_ = tempSpan_[0].offsetHeight; };
		tempSpan_.remove();
		return parseInt(offset_);
	}
	,
	getDesiredWidth: function (element) {
		var tb = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
		if (tb != null && tb.text() != null) {
			return this.__context.measureTextWidth(tb.text()) + $.ig.XamRadialMenuView.prototype._tEXT_MARGIN;
		}
		return 0;
	}
	,
	showCustomDropDown: function (customDropDownContent) {
	}
	,
	hideCustomDropDown: function (customDropDownContent) {
	}
	,
	onCenterRingBrushesUpdated: function () {
		this.__centerButtonBackPath.__stroke = this.menu().currentFrame().innerRing().stroke();
	}
	,
	getDefaultItemValue: function (property) {
		var values = this._itemCache.getValues();
		var value;
		var $ret = values.tryGetValue(property, value);
		value = $ret.p1;
		return value;
	}
	,
	getItemMeasureTextBlock: function () {
		return new $.ig.TextBlock();
	}
	,
	sortByZIndex: function (x, y) {
		var xIdx = (x).canvasZIndex();
		var yIdx = (y).canvasZIndex();
		if (xIdx < yIdx) {
			return -1;
		} else if (xIdx > yIdx) {
			return 1;
		} else {
			return 0;
		}
	}
	,
	exportVisualData: function (vd) {
		if (this.__toolTip != null) {
			vd.itemToolTip(this.__toolTipData);
		}
	}
	,
	shouldInteract: function (mousePt) {
		var menu = this.menu();
		if (this.shouldHookWindow()) {
			return true;
		}
		return menu.isOverMenu(this.adjustCollapsedPoint(mousePt));
	}
	,
	adjustCollapsedPoint: function (mousePt) {
		if (this.__viewPortResolved.x != 0 || this.__viewPortResolved.y != 0) {
			mousePt = { __x: mousePt.__x + this.__viewPortResolved.x, __y: mousePt.__y + this.__viewPortResolved.y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
		return mousePt;
	}
	,
	$type: new $.ig.Type('XamRadialMenuView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuDOMEventProxy', 'DOMEventProxy', {
	__eventNS: null,
	init: function (DOMEventSource) {
		this.__eventNS = null;
		$.ig.DOMEventProxy.prototype.init.call(this, DOMEventSource);
	},
	eventNS: function () {
		if (this.__eventNS == null) {
			$.ig.RadialMenuDOMEventProxy.prototype.__proxyCount++;
			this.__eventNS = ".DOMProxyRadialMenu" + $.ig.RadialMenuDOMEventProxy.prototype.__proxyCount.toString();
		}
		return this.__eventNS;
	}
	,
	onContextMenu: function (e, ui) {
		e.preventDefault();
	}
	,
	bindToSource: function (objSource, sourceID) {
		if (objSource == this.eventSource()) {
			this.eventSource().bind("contextmenu", this.onContextMenu.runOn(this));
		}
		$.ig.DOMEventProxy.prototype.bindToSource.call(this, objSource, sourceID);
	}
	,
	unbindFromSource: function (objSource, sourceID) {
		if (this.eventSource() != null && objSource == this.eventSource()) {
			this.eventSource().unbind("contextmenu");
		}
		$.ig.DOMEventProxy.prototype.unbindFromSource.call(this, objSource, sourceID);
	}
	,
	destroy: function () {
		if (this.eventSource() != null) {
			this.eventSource().unbind("contextmenu");
		}
		$.ig.DOMEventProxy.prototype.destroy.call(this);
	}
	,
	$type: new $.ig.Type('RadialMenuDOMEventProxy', $.ig.DOMEventProxy.prototype.$type)
}, true);

$.ig.util.defType('TextContainer', 'Object', {
	__text: null,
	__lines: null,
	__widths: null,
	_textAlignment: 0,
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_foreground: null,
	foreground: function (value) {
		if (arguments.length === 1) {
			this._foreground = value;
			return value;
		} else {
			return this._foreground;
		}
	}
	,
	text: function (value) {
		if (arguments.length === 1) {
			if (this.__text != value) {
				this.__text = value;
				this.__lines = null;
				this.__widths = null;
			}
			return value;
		} else {
			return this.__text;
		}
	}
	,
	draw: function (renderContext, lineHeight, x, y) {
		var context = renderContext.getUnderlyingContext();
		var lines = this.getLines();
		if (lines != null) {
			var width = this.getWidth(context.measureText.runOn(context));
			var originalX = x;
			context.fillStyle = this.foreground().__fill;
			context.textBaseline = "top";
			for (var i = 0, len = lines.length; i < len; i++) {
				switch (this._textAlignment) {
					case $.ig.HorizontalAlignment.prototype.left:
					case $.ig.HorizontalAlignment.prototype.stretch:
						x = originalX;
						break;
					case $.ig.HorizontalAlignment.prototype.right:
						x = originalX - this.__widths[i];
						break;
					case $.ig.HorizontalAlignment.prototype.center:
						x = originalX - this.__widths[i] / 2;
						break;
				}
				context.fillText(lines[i], x, y);
				y += lineHeight;
			}
		}
	}
	,
	getWidth: function (measure) {
		var total = 0;
		var widths = this.__widths;
		if (widths == null) {
			var lines = this.getLines();
			if (lines != null) {
				this.__widths = widths = new Array(lines.length);
				for (var i = 0, len = widths.length; i < len; i++) {
					widths[i] = measure(lines[i]).width;
				}
			}
		}
		if (widths != null) {
			for (var i1 = 0, len1 = widths.length; i1 < len1; i1++) {
				total += widths[i1];
			}
		}
		return total;
	}
	,
	getHeight: function (lineHeight) {
		var total = 0;
		var lines = this.getLines();
		if (lines != null) {
			total += lines.length * lineHeight;
		}
		return total;
	}
	,
	getLines: function () {
		if (this.__lines == null) {
			if (this.__text != null) {
				this.__lines = this.__text.split('\n');
			}
		}
		return this.__lines;
	}
	,
	$type: new $.ig.Type('TextContainer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ImageManager', 'Object', {
	__uriToImage: null,
	__callBacks: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__uriToImage = new $.ig.Dictionary$2(String, $.ig.ImageElement.prototype.$type, 0);
		this.__callBacks = new $.ig.Dictionary$2($.ig.ImageElement.prototype.$type, $.ig.List$1.prototype.$type.specialize($.ig.Action$1.prototype.$type.specialize($.ig.ImageElement.prototype.$type)), 0);
	},
	getImage: function (url, onReady) {
		var $self = this;
		var image;
		if (!(function () { var $ret = $self.__uriToImage.tryGetValue(url, image); image = $ret.p1; return $ret.ret; }())) {
			var jq = $("<img />");
			image = jq[0];
			image.src = url;
			if (!image.complete) {
				var self_ = this;
				var ele_ = jq;
				ele_.bind('load readystatechange', function(e) { if (this.complete || (this.readyState == 'complete' && e.type == 'readystatechange')) { self_.downloadCompleted(this) }});
			}
		}
		if (onReady != null) {
			if (image.complete) {
				window.setTimeout(function () { onReady(image); }, 0);
			} else {
				var callbacks;
				if (!(function () { var $ret = $self.__callBacks.tryGetValue(image, callbacks); callbacks = $ret.p1; return $ret.ret; }())) {
					this.__callBacks.item(image, callbacks = new $.ig.List$1($.ig.Action$1.prototype.$type.specialize($.ig.ImageElement.prototype.$type), 0));
				}
				callbacks.add(onReady);
			}
		}
		return image;
	}
	,
	downloadCompleted: function (elem) {
		var $self = this;
		if (elem.complete) {
			var jq = $(elem);
			jq.unbind("load readystatechange");
			var callbacks;
			if ((function () { var $ret = $self.__callBacks.tryGetValue(elem, callbacks); callbacks = $ret.p1; return $ret.ret; }())) {
				this.__callBacks.remove(elem);
				for (var i = 0, len = callbacks.count(); i < len; i++) {
					callbacks.__inner[i](elem);
				}
			}
		}
	}
	,
	$type: new $.ig.Type('ImageManager', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemOverlayTemplates', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		var dtNumeric = new $.ig.DataTemplate();
		dtNumeric.measure($.ig.RadialMenuItemOverlayTemplates.prototype.measureOverlayTemplate);
		dtNumeric.render($.ig.RadialMenuItemOverlayTemplates.prototype.renderNumericOverlay);
		$.ig.RadialMenuItemOverlayTemplates.prototype.numericOverlayTemplate = dtNumeric;
		var dtColor = new $.ig.DataTemplate();
		dtColor.measure($.ig.RadialMenuItemOverlayTemplates.prototype.measureOverlayTemplate);
		dtColor.render($.ig.RadialMenuItemOverlayTemplates.prototype.renderColorOverlay);
		$.ig.RadialMenuItemOverlayTemplates.prototype.colorOverlayTemplate = dtColor;
	},
	measureOverlayTemplate: function (info) {
		if (info.width < 20) {
			info.width = 20;
		}
		if (info.height < 20) {
			info.height = 20;
		}
	}
	,
	renderColorOverlay: function (info) {
		var item = info.data;
		var color = item.iconOverlayValue();
		if ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, color), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) && color.a() > 0) {
			var renderCont = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), info.context);
			var path = new $.ig.Path();
			var rectGeo = new $.ig.RectangleGeometry();
			rectGeo.rect(new $.ig.Rect(0, info.xPosition, (info.yPosition + info.availableHeight) - 5, info.availableWidth, 5));
			path.data(rectGeo);
			var brush = new $.ig.Brush();
			brush.color(color);
			path.__fill = brush;
			renderCont.renderPath(path);
		}
	}
	,
	renderNumericOverlay: function (info) {
		var item = info.data;
		var value = item.iconOverlayValue();
		if (value != null) {
			var renderCont = new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), info.context);
			var tb = new $.ig.TextBlock();
			tb.text(value.toString());
			tb.fill(item.getResolvedProperty($.ig.RadialMenuItemBase.prototype.foregroundProperty));
			var size = item.menu().view().measureText(tb);
			tb.canvasLeft(info.xPosition + info.availableWidth - size.width() + 8);
			tb.canvasTop(info.yPosition - 8);
			renderCont.renderTextBlock(tb);
		}
	}
	,
	$type: new $.ig.Type('RadialMenuItemOverlayTemplates', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemView', 'Object', {
	__item: null,
	__text: null,
	__image: null,
	__imageRect: null,
	__menuView: null,
	__overlayTemplate: null,
	init: function (item, menuView) {
		$.ig.Object.prototype.init.call(this);
		this.__item = item;
		this.__menuView = menuView;
		if ($.ig.util.cast($.ig.RadialMenuItem.prototype.$type, item) !== null) {
			this.__text = new $.ig.TextContainer();
			this.__text._textAlignment = $.ig.HorizontalAlignment.prototype.center;
			this.__text.foreground(item.getResolvedProperty($.ig.RadialMenuItemBase.prototype.foregroundProperty));
			this.updateHeader();
			this.updateImage();
			this.updateOverlay();
		}
		this._isEnabled = item.isEnabled();
		item.propertyChanged = $.ig.Delegate.prototype.combine(item.propertyChanged, this.onItemPropertyChanged.runOn(this));
	},
	item: function () {
		return this.__item;
	}
	,
	_position: null,
	_isEnabled: false,
	dispose: function () {
		var $t = this.__item;
		$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.onItemPropertyChanged.runOn(this));
	}
	,
	draw: function (context, lineHeight, globalAlpha) {
		var location = this._position._contentLocation;
		var textHeight, imageHeight, overlayHeight;
		textHeight = imageHeight = overlayHeight = 0;
		if (this.__text != null && !String.isNullOrEmpty(this.__text.text())) {
			textHeight = this.__text.getHeight(lineHeight);
		}
		var overlaySize = this.getOverlaySize(context);
		if ($.ig.Size.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Size.prototype.$type, overlaySize), $.ig.util.toNullable($.ig.Size.prototype.$type, null))) {
			overlayHeight = overlaySize.height();
		}
		if ($.ig.Rect.prototype.l_op_Inequality(this.__imageRect, null)) {
			imageHeight = this.__imageRect.height();
		}
		var totalHeight = textHeight + Math.max(overlayHeight, imageHeight);
		var overlayDiff = (overlayHeight - imageHeight) / 2;
		var opacity = (globalAlpha * (this._isEnabled ? 1 : $.ig.XamRadialMenu.prototype.disabledMenuOpacity));
		var actualContext = context.getUnderlyingContext();
		if (opacity < 1) {
			actualContext.globalAlpha = opacity;
		}
		if (imageHeight > 0) {
			var imageWidth = this.__imageRect.width();
			context.drawImage1(this.__image, 1, this.__imageRect.x(), this.__imageRect.y(), imageWidth, imageHeight, location.__x - imageWidth / 2, location.__y - (totalHeight / 2) + Math.max(overlayDiff, 0), imageWidth, imageHeight);
		}
		if (overlayHeight > 0) {
			this.drawOverlay(context, location.__x - overlaySize.width() / 2, location.__y - (totalHeight / 2) - Math.min(overlayDiff, 0), overlaySize.width(), overlaySize.height());
		}
		if (textHeight > 0) {
			this.__text.draw(context, lineHeight, location.__x, location.__y - (totalHeight / 2) + Math.max(overlayHeight, imageHeight));
		}
		if (opacity < 1) {
			actualContext.globalAlpha = 1;
		}
	}
	,
	drawOverlay: function (context, x, y, width, height) {
		var renderInfo = new $.ig.DataTemplateRenderInfo();
		renderInfo.context = context.getUnderlyingContext();
		renderInfo.data = this.__item;
		renderInfo.xPosition = x;
		renderInfo.yPosition = y;
		renderInfo.availableWidth = width;
		renderInfo.availableHeight = height;
		this.__overlayTemplate.render()(renderInfo);
	}
	,
	getOverlaySize: function (context) {
		if (this.__overlayTemplate != null) {
			var measureInfo = new $.ig.DataTemplateMeasureInfo();
			measureInfo.context = context.getUnderlyingContext();
			measureInfo.data = this.__item;
			if ($.ig.Rect.prototype.l_op_Inequality(this.__imageRect, null)) {
				measureInfo.width = this.__imageRect.width();
				measureInfo.height = this.__imageRect.height();
			}
			if (this.__overlayTemplate.measure() != null) {
				this.__overlayTemplate.measure()(measureInfo);
			}
			return new $.ig.Size(1, measureInfo.width, measureInfo.height);
		}
		return $.ig.Size.prototype.empty();
	}
	,
	onImageReady: function (img) {
		if (img == this.__image) {
			var img_ = img;
			this.__imageRect = new $.ig.Rect(0, 0, 0, img_.width, img_.height);
			this.__menuView.menu().dirtyLayout();
		}
	}
	,
	onItemPropertyChanged: function (sender, e) {
		switch (e.propertyName()) {
			case $.ig.RadialMenuItemBase.prototype.foregroundPropertyName:
				if (this.__text != null) {
					this.__text.foreground(this.__item.getResolvedProperty($.ig.RadialMenuItemBase.prototype.foregroundProperty));
					this.__menuView.scheduleArrange();
				}
				break;
			case $.ig.RadialMenuItemBase.prototype.isEnabledPropertyName:
				this._isEnabled = this.__item.isEnabled();
				this.__menuView.scheduleArrange();
				break;
			case $.ig.RadialMenuItem.prototype.headerPropertyName:
				this.updateHeader();
				break;
			case $.ig.RadialMenuItem.prototype.iconUriPropertyName:
				this.updateImage();
				break;
			case $.ig.RadialMenuItem.prototype.iconOverlayPropertyName:
				this.updateOverlay();
				break;
			case $.ig.RadialMenuItem.prototype.iconOverlayValuePropertyName:
				this.__menuView.scheduleArrange();
				break;
		}
	}
	,
	updateHeader: function () {
		var newHeader = null;
		var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, this.item());
		if (mi != null) {
			var header = mi.header();
			if (header != null) {
				newHeader = header.toString();
			}
		}
		this.__text.text(newHeader);
		this.__menuView.scheduleArrange();
	}
	,
	updateImage: function () {
		var uri = (this.__item).iconUri();
		if (String.isNullOrEmpty(uri)) {
			this.__image = null;
		} else {
			this.__image = $.ig.ImageManager.prototype.global.getImage(uri, this.onImageReady.runOn(this));
		}
		this.__menuView.scheduleArrange();
	}
	,
	updateOverlay: function () {
		if ($.ig.util.cast($.ig.RadialMenuItem.prototype.$type, this.__item) !== null) {
			var item = this.__item;
			if (item.iconOverlay() != null) {
				this.__overlayTemplate = item.iconOverlay();
			} else {
				this.__overlayTemplate = null;
			}
			this.__menuView.scheduleArrange();
		}
	}
	,
	$type: new $.ig.Type('RadialMenuItemView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CssPropCacheBase$2', 'Object', {
	$tProp: null,
	$tValue: null,
	__values: null,
	__rootObject: null,
	__span: null,
	_classChain: null,
	__valueParser: null,
	init: function ($tProp, $tValue, chain, valueParser) {
		this.$tProp = $tProp;
		this.$tValue = $tValue;
		this.$type = this.$type.specialize(this.$tProp, this.$tValue);
		$.ig.Object.prototype.init.call(this);
		this._classChain = chain;
		this.__valueParser = valueParser;
	},
	rootObject: function (value) {
		if (arguments.length === 1) {
			this.__rootObject = value;
			this.__values = null;
			if (null != this.__span) {
				this.__span.remove();
			}
			return value;
		} else {
			return this.__rootObject;
		}
	}
	,
	getValues: function () {
		if (this.__values == null) {
			this.refreshCssValues();
		}
		return this.__values;
	}
	,
	refreshCssValues: function () {
		if (this.__span == null && this.__rootObject != null) {
			this.__span = $.ig.CssHelper.prototype.getDisoveryElement();
			var parent = this.__rootObject;
			parent.append(this.__span);
		}
		var oldValues = new $.ig.Dictionary$2(this.$tProp, String, 0);
		this.getOldValues(this._classChain, oldValues);
		var values = new $.ig.Dictionary$2(this.$tProp, this.$tValue, 0);
		this.addValues(this._classChain, values, oldValues);
		this.__values = values;
		if (this.__span != null) {
			this.__span.remove();
			this.__span = null;
		}
	}
	,
	addValues: function (chain, values, oldValues) {
		if (this.__span != null) {
			this.__span.addClass(chain._classes);
		}
		var en = chain._properties.getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			var prop = pair.key();
			var cssValue = this.getCssValue(this.__span, pair.value().item1());
			if (cssValue == null || cssValue.length == 0 || cssValue == oldValues.item(prop)) {
				cssValue = pair.value().item2();
			}
			var value = this.__valueParser(prop, cssValue);
			values.item(prop, value);
		}
		for (var i = 0, count = chain._children.count(); i < count; i++) {
			this.addValues(chain._children.__inner[i], values, oldValues);
		}
		if (this.__span != null) {
			this.__span.removeClass(chain._classes);
		}
	}
	,
	getCssValue: function (obj, property) {
		var attrib = null;
		var cssValue = null;
		if (obj != null) {
			switch (property) {
				case $.ig.CssProperty.prototype.backgroundColor:
					attrib = "background-color";
					break;
				case $.ig.CssProperty.prototype.borderTopColor:
					attrib = "border-top-color";
					break;
				case $.ig.CssProperty.prototype.borderTopStyle:
					attrib = "border-top-style";
					break;
				case $.ig.CssProperty.prototype.borderTopWidth:
					attrib = "border-top-width";
					break;
				case $.ig.CssProperty.prototype.borderTopLeftRadius:
					attrib = "border-top-left-radius";
					break;
				case $.ig.CssProperty.prototype.color:
					attrib = "color";
					break;
				case $.ig.CssProperty.prototype.paddingTop:
					attrib = "padding-top";
					break;
				default:
					$.ig.Debug.prototype.assert1(false, "Unrecognized css property:" + $.ig.CssProperty.prototype.$getName(property));
					return null;
			}
			if (attrib != null) {
				cssValue = obj.css(attrib);
			}
		}
		return cssValue;
	}
	,
	getOldValues: function (chain, oldValues) {
		var en = chain._properties.getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			oldValues.item(pair.key(), this.getCssValue(this.__span, pair.value().item1()));
		}
		for (var i = 0, count = chain._children.count(); i < count; i++) {
			this.getOldValues(chain._children.__inner[i], oldValues);
		}
	}
	,
	$type: new $.ig.Type('CssPropCacheBase$2', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CssPropCache$1', 'CssPropCacheBase$2', {
	$tPropertyId: null,
	init: function ($tPropertyId, chain) {
		var $self = this;
		this.$tPropertyId = $tPropertyId;
		this.$type = this.$type.specialize(this.$tPropertyId);
		$.ig.CssPropCacheBase$2.prototype.init.call(this, this.$tPropertyId, String, chain, function () { return $.ig.CssPropCache$1.prototype.parseValue.apply(null, [ $self.$tPropertyId ].concat(Array.prototype.slice.call(arguments))); });
	},
	parseValue: function ($tPropertyId, prop, cssValue) {
		return cssValue;
	}
	,
	$type: new $.ig.Type('CssPropCache$1', $.ig.CssPropCacheBase$2.prototype.$type.specialize(0, String))
}, true);

$.ig.util.defType('CssDpCache', 'CssPropCacheBase$2', {
	init: function (chain) {
		$.ig.CssPropCacheBase$2.prototype.init.call(this, $.ig.DependencyProperty.prototype.$type, $.ig.Object.prototype.$type, chain, $.ig.CssDpCache.prototype.parseValue);
	},
	parseValue: function (prop, cssValue) {
		var value = null;
		if (cssValue != null) {
			var propType_ = prop.propertyType();
			if (propType_ == $.ig.Brush.prototype.$type) {
				var b = new $.ig.Brush();
				b.__fill = cssValue;
				value = b;
			} else if (propType_ === Number) {
				value = parseInt(cssValue, 10);
			} else if (propType_ == $.ig.Color.prototype.$type) {
				var c = new $.ig.Color();
				c.colorString(cssValue);
				value = c;
			}
		}
		return value;
	}
	,
	$type: new $.ig.Type('CssDpCache', $.ig.CssPropCacheBase$2.prototype.$type.specialize($.ig.DependencyProperty.prototype.$type, $.ig.Object.prototype.$type))
}, true);

$.ig.util.defType('BindingExpression', 'Object', {
	__steps: null,
	__target: null,
	__targetProperty: null,
	init: function (source, sourcePath, target, targetProperty) {
		$.ig.Object.prototype.init.call(this);
		this.__target = target;
		this.__targetProperty = targetProperty;
		var paths = sourcePath.split('.');
		this.__steps = new Array(paths.length);
		for (var i = 0; i < paths.length; i++) {
			this.__steps[i] = new $.ig.PropertyStep(paths[i]);
			if (i > 0) {
				this.__steps[i - 1].childStep(this.__steps[i]);
			}
		}
		var $t = this.__steps[this.__steps.length - 1];
		$t.propertyChanged = $.ig.Delegate.prototype.combine($t.propertyChanged, this.onValueChanged.runOn(this));
		this.__steps[0].source(source);
	},
	onValueChanged: function (sender, e) {
		if (e.propertyName() == "Value") {
			var value = this.__steps[this.__steps.length - 1].value();
			if (value == $.ig.DependencyProperty.prototype.unsetValue) {
				this.__target.clearValue(this.__targetProperty);
			} else {
				this.__target.setValue(this.__targetProperty, value);
			}
		}
	}
	,
	$type: new $.ig.Type('BindingExpression', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PropertyStep', 'Object', {
	__path: null,
	__source: null,
	__value: null,
	__childStep: null,
	init: function (path) {
		$.ig.Object.prototype.init.call(this);
		this.__path = path;
	},
	childStep: function (value) {
		if (arguments.length === 1) {
			if (this.__childStep != value) {
				if (this.__childStep != null) {
					this.__childStep.source(null);
				}
				this.__childStep = value;
				if (this.__childStep != null) {
					this.__childStep.source(this.value());
				}
			}
			return value;
		} else {
			return this.__childStep;
		}
	}
	,
	source: function (value) {
		if (arguments.length === 1) {
			if (this.__source != value) {
				if ($.ig.util.cast($.ig.INotifyPropertyChanged.prototype.$type, this.__source) !== null) {
					var $t = (this.__source);
					$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.onSourcePropertyChanged.runOn(this));
				}
				this.__source = value;
				if ($.ig.util.cast($.ig.INotifyPropertyChanged.prototype.$type, value) !== null) {
					var $t1 = (value);
					$t1.propertyChanged = $.ig.Delegate.prototype.combine($t1.propertyChanged, this.onSourcePropertyChanged.runOn(this));
				}
				this.onPropertyChanged("Source");
				this.updateValue();
			}
			return value;
		} else {
			return this.__source;
		}
	}
	,
	value: function (value) {
		if (arguments.length === 1) {
			if (this.__value != value) {
				this.__value = value;
				if (this.__childStep != null) {
					this.__childStep.source(value);
				}
				this.onPropertyChanged("Value");
			}
			return value;
		} else {
			return this.__value;
		}
	}
	,
	onPropertyChanged: function (propertyName) {
		var handler = this.propertyChanged;
		if (null != handler) {
			handler(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	onSourcePropertyChanged: function (sender, e) {
		if (String.isNullOrEmpty(e.propertyName()) || e.propertyName() == this.__path || (e.propertyName().length == this.__path.length && e.propertyName().toLowerCase() == this.__path.toLowerCase())) {
			this.updateValue();
		}
	}
	,
	updateValue: function () {
		if (this.__source == null) {
			this.value($.ig.DependencyProperty.prototype.unsetValue);
		} else if (String.isNullOrEmpty(this.__path)) {
			this.value(this.__source);
		} else {
			var src_ = this.__source;
			var path_ = this.__path;
			this.value(src_[path_]());
		}
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('PropertyStep', $.ig.Object.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuValueChangedEventArgs$1', 'EventArgs', {
	$t: null,
	__oldValue: null,
	__newValue: null,
	init: function ($t, oldValue, newValue) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.EventArgs.prototype.init.call(this);
		this.__oldValue = oldValue;
		this.__newValue = newValue;
	},
	oldValue: function () {
		return this.__oldValue;
	}
	,
	newValue: function () {
		return this.__newValue;
	}
	,
	$type: new $.ig.Type('RadialMenuValueChangedEventArgs$1', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuNumericValueChangedEventArgs', 'RadialMenuValueChangedEventArgs$1', {
	init: function (oldValue, newValue) {
		$.ig.RadialMenuValueChangedEventArgs$1.prototype.init.call(this, Number, oldValue, newValue);
	},
	$type: new $.ig.Type('RadialMenuNumericValueChangedEventArgs', $.ig.RadialMenuValueChangedEventArgs$1.prototype.$type.specialize(Number))
}, true);

$.ig.util.defType('RadialMenuColorChangedEventArgs', 'RadialMenuValueChangedEventArgs$1', {
	init: function (oldValue, newValue) {
		$.ig.RadialMenuValueChangedEventArgs$1.prototype.init.call(this, $.ig.Color.prototype.$type, oldValue, newValue);
	},
	$type: new $.ig.Type('RadialMenuColorChangedEventArgs', $.ig.RadialMenuValueChangedEventArgs$1.prototype.$type.specialize($.ig.Color.prototype.$type))
}, true);

$.ig.util.defType('RadialMenuItem', 'RadialMenuItemBase', {
	__items: null,
	staticInit: function () {
	},
	init: function () {
		var $self = this;
		$.ig.RadialMenuItemBase.prototype.init.call(this);
		this.__items = new $.ig.RadialMenuItemBaseCollection(function (mi, added) {
			mi.setMenu(added ? $self.menu() : null);
			mi.parentItem(added ? $self : null);
		});
		var $t = (this.__items);
		$t.propertyChanged = $.ig.Delegate.prototype.combine($t.propertyChanged, this.onItemsPropertyChanged.runOn(this));
	},
	calculateChildAngleAdjustment: function (itemPosition, childPositions) {
		var recentItem = this.recentItem();
		if (null != recentItem) {
			var en = childPositions.getEnumerator();
			while (en.moveNext()) {
				var slot = en.current();
				var item = slot.item();
				if (item.parentItem() == this && this.getItemFromContainer(item) == recentItem) {
					var midPtChild = slot._startAngle + (slot._endAngle - slot._startAngle) / 2;
					var midPtParent = itemPosition._startAngle + (itemPosition._endAngle - itemPosition._startAngle) / 2;
					return midPtParent - midPtChild;
				}
			}
		}
		return $.ig.RadialMenuItemBase.prototype.calculateChildAngleAdjustment.call(this, itemPosition, childPositions);
	}
	,
	displayAsChecked: function () {
		return this.isChecked();
	}
	,
	getContainerForItem: function (dataItem) {
		var item = $.ig.RadialMenuItemBase.prototype.getContainerForItem.call(this, dataItem);
		if (null == item) {
			var en = this.items().getEnumerator();
			while (en.moveNext()) {
				var child = en.current();
				item = child.getContainerForItem(dataItem);
				if (item != null) {
					break;
				}
			}
		}
		return item;
	}
	,
	itemContainers: function () {
		return this.__items;
	}
	,
	onClick: function () {
		var checkBehavior = this.checkBehavior();
		if (checkBehavior != $.ig.RadialMenuCheckBehavior.prototype.none) {
			var isChecked = !this.isChecked();
			if (isChecked || checkBehavior != $.ig.RadialMenuCheckBehavior.prototype.radioButton) {
				this.setIsChecked(isChecked);
			}
		}
		var parent = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, this.parentItem());
		if (null != parent && parent.autoUpdateRecentItem()) {
			var newRecent = parent.getItemFromContainer(this);
			parent.recentItem(newRecent);
		}
		this.onClick1($.ig.EventArgs.prototype.empty);
		$.ig.RadialMenuItemBase.prototype.onClick.call(this);
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.RadialMenuItem.prototype.isCheckedPropertyName:
				if (true == newValue) {
					this.uncheckRadioButtons();
					this.onChecked($.ig.EventArgs.prototype.empty);
				} else {
					this.onUnchecked($.ig.EventArgs.prototype.empty);
				}
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.displayAsCheckedPropertyName, oldValue, newValue);
				break;
			case $.ig.RadialMenuItem.prototype.recentItemPropertyName:
				this.recentItemContainer(this.getCreatedContainer(newValue));
				break;
		}
		$.ig.RadialMenuItemBase.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
	}
	,
	outerRingButtonType: function () {
		var result = $.ig.RadialMenuItemBase.prototype.outerRingButtonType.call(this);
		if (result == $.ig.OuterRingButtonType.prototype.none && this.items().count() > 0 && this.childItemPlacementResolved() == $.ig.RadialMenuChildItemPlacement.prototype.asChildren) {
			result = $.ig.OuterRingButtonType.prototype.navigateToChildren;
		}
		return result;
	}
	,
	setMenu: function (menu) {
		$.ig.RadialMenuItemBase.prototype.setMenu.call(this, menu);
		var en = this.itemContainers().getEnumerator();
		while (en.moveNext()) {
			var c = en.current();
			if (c != null) {
				c.setMenu(menu);
			}
		}
	}
	,
	toolTipResolved: function () {
		var tt = $.ig.RadialMenuItemBase.prototype.toolTipResolved.call(this);
		if (tt != null) {
			return tt;
		}
		var header = this.header();
		if (header != null && !($.ig.util.cast($.ig.UIElement.prototype.$type, header) !== null)) {
			return header;
		}
		return null;
	}
	,
	autoUpdateRecentItem: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.autoUpdateRecentItemProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.autoUpdateRecentItemProperty);
		}
	}
	,
	childItemPlacement: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.childItemPlacementProperty, $.ig.RadialMenuChildItemPlacement.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.RadialMenuItem.prototype.childItemPlacementProperty));
		}
	}
	,
	checkBehavior: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.checkBehaviorProperty, $.ig.RadialMenuCheckBehavior.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.RadialMenuItem.prototype.checkBehaviorProperty));
		}
	}
	,
	isChecked: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.isCheckedProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.isCheckedProperty);
		}
	}
	,
	groupName: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.groupNameProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.groupNameProperty);
		}
	}
	,
	header: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.headerProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.headerProperty);
		}
	}
	,
	iconUri: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.iconUriProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.iconUriProperty);
		}
	}
	,
	iconOverlay: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.iconOverlayProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.iconOverlayProperty);
		}
	}
	,
	iconOverlayValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.iconOverlayValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.iconOverlayValueProperty);
		}
	}
	,
	items: function () {
		return this.__items;
	}
	,
	keyTip: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.keyTipProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.keyTipProperty);
		}
	}
	,
	recentItem: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.recentItemProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.recentItemProperty);
		}
	}
	,
	onRecentItemContainerChanged: function (d, e) {
		var instance = d;
		instance.onPropertyChangedImpl($.ig.RadialMenuItem.prototype.recentItemContainerPropertyName, e.oldValue(), e.newValue());
	}
	,
	recentItemContainer: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuItem.prototype.recentItemContainerProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuItem.prototype.recentItemContainerProperty);
		}
	}
	,
	childItemPlacementResolved: function () {
		if (this.displaysCustomDropDown()) {
			return $.ig.RadialMenuChildItemPlacement.prototype.asChildren;
		}
		return this.childItemPlacement();
	}
	,
	createItemContainer: function () {
		return new $.ig.RadialMenuItem();
	}
	,
	getCreatedContainer: function (dataItem) {
		var en = this.items().getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (item._dataItem == dataItem) {
				return item;
			}
		}
		return null;
	}
	,
	getItemFromContainer: function (item) {
		return item._dataItem;
	}
	,
	onItemsPropertyChanged: function (sender, e) {
		switch (e.propertyName()) {
			case "Count":
				this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.outerRingButtonTypePropertyName, null, null);
				break;
		}
	}
	,
	setIconOverlayValue: function (value) {
		this.iconOverlayValue(value);
	}
	,
	setIsChecked: function (isChecked) {
		this.isChecked(isChecked);
	}
	,
	uncheckRadioButtons: function () {
		var parentItems = null;
		if (this.parentItem() != null) {
			parentItems = this.parentItem().itemContainers();
		} else if (this.menu() != null) {
			var rootItems = this.menu().view().rootItems();
			if (rootItems != null && rootItems.contains(this)) {
				parentItems = rootItems;
			}
		}
		if (parentItems != null) {
			switch (this.checkBehavior()) {
				case $.ig.RadialMenuCheckBehavior.prototype.radioButton:
				case $.ig.RadialMenuCheckBehavior.prototype.radioButtonAllowAllUp:
					var name = this.groupName();
					if (name == null) {
						name = "";
					}
					var itemsToUncheck = new $.ig.List$1($.ig.RadialMenuItem.prototype.$type, 0);
					var en = parentItems.getEnumerator();
					while (en.moveNext()) {
						var item = en.current();
						var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, item);
						if (mi != null && mi != this && mi.isChecked()) {
							var groupName = mi.groupName();
							if (groupName == null) {
								groupName = "";
							}
							if (name == groupName) {
								itemsToUncheck.add(mi);
							}
						}
					}
					var en1 = itemsToUncheck.getEnumerator();
					while (en1.moveNext()) {
						var mi1 = en1.current();
						mi1.setIsChecked(false);
					}
					break;
			}
		}
	}
	,
	onChecked: function (e) {
		var handler = this.checked;
		if (null != handler) {
			handler(this, e);
		}
	}
	,
	checked: null,
	onClick1: function (e) {
		var handler = this.click;
		if (null != handler) {
			handler(this, e);
		}
	}
	,
	click: null,
	onUnchecked: function (e) {
		var handler = this.unchecked;
		if (null != handler) {
			handler(this, e);
		}
	}
	,
	unchecked: null,
	$type: new $.ig.Type('RadialMenuItem', $.ig.RadialMenuItemBase.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuColorItemBase', 'RadialMenuItem', {
	init: function () {
		$.ig.RadialMenuItem.prototype.init.call(this);
	},
	calculateChildAngleAdjustment: function (itemPosition, childPositions) {
		var color = this.color();
		var en = childPositions.getEnumerator();
		while (en.moveNext()) {
			var slot = en.current();
			var childColorItem = $.ig.util.cast($.ig.RadialMenuColorItemBase.prototype.$type, slot.item());
			if (childColorItem != null && $.ig.RadialMenuUtilities.prototype.equals(childColorItem.color(), color)) {
				var midPtChild = slot._startAngle + (slot._endAngle - slot._startAngle) / 2;
				var midPtParent = itemPosition._startAngle + (itemPosition._endAngle - itemPosition._startAngle) / 2;
				return midPtParent - midPtChild;
			}
		}
		return $.ig.RadialMenuItem.prototype.calculateChildAngleAdjustment.call(this, itemPosition, childPositions);
	}
	,
	createItemContainer: function () {
		return new $.ig.RadialMenuColorWell();
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		if (propertyName == $.ig.RadialMenuColorItemBase.prototype.colorPropertyName) {
			this.computedColorName($.ig.RadialMenuUtilities.prototype.colorNameCalculator().getColorName(this.color()));
			this.setIconOverlayValue(newValue);
			var raiseChange = oldValue != null && newValue != null && !$.ig.RadialMenuUtilities.prototype.equals(oldValue, newValue);
			if (raiseChange) {
				var handler = this.colorChanged;
				if (null != handler) {
					handler(this, new $.ig.RadialMenuColorChangedEventArgs(oldValue, newValue));
				}
			}
		}
		$.ig.RadialMenuItem.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
	}
	,
	color: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuColorItemBase.prototype.colorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuColorItemBase.prototype.colorProperty);
		}
	}
	,
	computedColorName: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuColorItemBase.prototype.computedColorNameProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuColorItemBase.prototype.computedColorNameProperty);
		}
	}
	,
	setColor: function (color) {
		this.color(color);
	}
	,
	colorChanged: null,
	$type: new $.ig.Type('RadialMenuColorItemBase', $.ig.RadialMenuItem.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuColorItem', 'RadialMenuColorItemBase', {
	staticInit: function () {
	},
	init: function () {
		$.ig.RadialMenuColorItemBase.prototype.init.call(this);
		this.iconOverlay($.ig.RadialMenuItemOverlayTemplates.prototype.colorOverlayTemplate);
	},
	onColorWellClick: function (e) {
		var handler = this.colorWellClick;
		if (null != handler) {
			handler(this, e);
		}
	}
	,
	colorWellClick: null,
	$type: new $.ig.Type('RadialMenuColorItem', $.ig.RadialMenuColorItemBase.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuColorWell', 'RadialMenuColorItemBase', {
	staticInit: function () {
	},
	init: function () {
		$.ig.RadialMenuColorItemBase.prototype.init.call(this);
	},
	displayAsChecked: function () {
		var elem = this.parentItem();
		while ($.ig.util.cast($.ig.RadialMenuColorItemBase.prototype.$type, elem) !== null) {
			var ci = $.ig.util.cast($.ig.RadialMenuColorItem.prototype.$type, elem);
			if (ci != null) {
				return $.ig.RadialMenuUtilities.prototype.equals(ci.color(), this.color());
			}
			elem = elem.parentItem();
		}
		return $.ig.RadialMenuColorItemBase.prototype.displayAsChecked.call(this);
	}
	,
	onClick: function () {
		var item = this.parentItem();
		var wasChecked = this.displayAsChecked();
		while ($.ig.util.cast($.ig.RadialMenuColorItemBase.prototype.$type, item) !== null) {
			var parentColor = $.ig.util.cast($.ig.RadialMenuColorItemBase.prototype.$type, item);
			parentColor.setColor(this.color());
			if ($.ig.util.cast($.ig.RadialMenuColorItem.prototype.$type, parentColor) !== null) {
				var colorItem = parentColor;
				colorItem.onColorWellClick($.ig.EventArgs.prototype.empty);
			}
			item = item.parentItem();
		}
		if (wasChecked != this.displayAsChecked()) {
			this.onPropertyChanged($.ig.RadialMenuItemBase.prototype.displayAsCheckedPropertyName, wasChecked, !wasChecked);
		}
		$.ig.RadialMenuColorItemBase.prototype.onClick.call(this);
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		if (propertyName == $.ig.RadialMenuColorItemBase.prototype.colorPropertyName) {
			var color = newValue;
			this.innerAreaHotTrackFill(this.innerAreaFill(this.outerRingButtonFill($.ig.RadialMenuUtilities.prototype.createBrush(color))));
			this.outerRingButtonForeground($.ig.RadialMenuUtilities.prototype.createBrush($.ig.RadialMenuUtilities.prototype.calculateForeground(color)));
			var ahsl = $.ig.ColorUtil.prototype.getAHSL(color);
			var hotTrackFill = $.ig.RadialMenuUtilities.prototype.adjustRgb(color, ahsl[3] < 0.5 ? +60 : -80);
			this.highlightBrush(this.outerRingButtonHotTrackFill($.ig.RadialMenuUtilities.prototype.createBrush(hotTrackFill)));
			this.outerRingButtonHotTrackForeground($.ig.RadialMenuUtilities.prototype.createBrush($.ig.RadialMenuUtilities.prototype.calculateForeground(hotTrackFill)));
			var checkFill = $.ig.RadialMenuUtilities.prototype.adjustRgb(color, ahsl[3] < 0.5 ? +30 : -40);
			this.checkedHighlightBrush($.ig.RadialMenuUtilities.prototype.createBrush(checkFill));
			this.verifyBorderColors();
		}
		$.ig.RadialMenuColorItemBase.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
	}
	,
	outerRingButtonType: function () {
		var value = $.ig.RadialMenuColorItemBase.prototype.outerRingButtonType.call(this);
		if (value == $.ig.OuterRingButtonType.prototype.none) {
			value = $.ig.OuterRingButtonType.prototype.toolAreaClick;
		}
		return value;
	}
	,
	toolTipResolved: function () {
		var tt = $.ig.RadialMenuColorItemBase.prototype.toolTipResolved.call(this);
		if (tt == null) {
			return this.computedColorName();
		}
		return tt;
	}
	,
	verifyBorderColors: function () {
		var menu = this.menu();
		var color = $.ig.Color.prototype.fromArgb(255, 255, 255, 255);
		var menuBack = menu != null ? menu.menuBackground() : null;
		if (menuBack != null) {
			color = menuBack.color();
		}
		this.verifyBorderColors1(color);
	}
	,
	verifyBorderColors1: function (backgroundColor) {
		var brightness = $.ig.RadialMenuUtilities.prototype.calculateSRgbBrightness(this.color());
		var backBrightness = $.ig.RadialMenuUtilities.prototype.calculateSRgbBrightness(backgroundColor);
		if (Math.abs(backBrightness - brightness) < 0.1) {
			this.outerRingButtonStroke(this.innerAreaStroke(this.checkedHighlightBrush()));
		} else {
			this.outerRingButtonStroke(this.innerAreaStroke(null));
		}
		this.outerRingButtonHotTrackStroke(this.innerAreaHotTrackStroke(this.outerRingButtonStroke()));
	}
	,
	$type: new $.ig.Type('RadialMenuColorWell', $.ig.RadialMenuColorItemBase.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemFrame', 'Object', {
	__shapes: null,
	__textBlocks: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__shapes = new $.ig.List$1($.ig.ShapeDefinition.prototype.$type, 0);
		this.__textBlocks = new $.ig.List$1($.ig.TextDefinition.prototype.$type, 0);
	},
	shapes: function () {
		return this.__shapes;
	}
	,
	textBlocks: function () {
		return this.__textBlocks;
	}
	,
	initializeData: function (itemData, getPath, getText) {
	}
	,
	$type: new $.ig.Type('RadialMenuItemFrame', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuNumericGaugeFrame', 'RadialMenuItemFrame', {
	__hotTrackLine: null,
	__valueLine: null,
	_valueOuterRect: null,
	_valueInnerRect: null,
	init: function () {
		this._valueOuterRect = new $.ig.LiteRect();
		this._valueInnerRect = new $.ig.LiteRect();
		$.ig.RadialMenuItemFrame.prototype.init.call(this);
		this.__valueLine = new $.ig.LineSegmentShape();
		this.shapes().add(this.__valueLine);
		this.__hotTrackLine = new $.ig.LineSegmentShape();
		this.shapes().add(this.__hotTrackLine);
	},
	initializeData: function (itemData, getPath, getText) {
		var tempTb;
		var tempFi;
		var gaugeData = $.ig.util.cast($.ig.RadialMenuNumericGaugeVisualData.prototype.$type, itemData);
		if (this.valueLine() != null) {
			gaugeData.valueNeedle(new $.ig.RadialMenuNumericGaugeNeedleData());
			gaugeData.valueNeedle().needlePath(new $.ig.PathVisualData(1, "ValueNeedle", getPath(this.valueLine())));
			var en = this.textBlocks().getEnumerator();
			while (en.moveNext()) {
				var textDef = en.current();
				if (textDef._context == this.valueLine()) {
					tempTb = getText(textDef);
					tempFi = $.ig.FontUtil.prototype.getFontInfo(tempTb, null);
					gaugeData.valueNeedle().label($.ig.AppearanceHelper.prototype.fromTextElement(tempTb, tempFi));
					break;
				}
			}
		}
		if (this.hotTrackLine() != null && this.hotTrackLine().strokeThickness() > 0) {
			gaugeData.editValueNeedle(new $.ig.RadialMenuNumericGaugeNeedleData());
			gaugeData.editValueNeedle().label(null);
			gaugeData.editValueNeedle().needlePath(new $.ig.PathVisualData(1, "EditValueNeedle", getPath(this.hotTrackLine())));
		}
		gaugeData.tickmarks(new $.ig.RadialMenuNumericGaugeTickmarkDataList());
		var en1 = this.textBlocks().getEnumerator();
		while (en1.moveNext()) {
			var textDef1 = en1.current();
			var shape = $.ig.util.cast($.ig.ShapeDefinition.prototype.$type, textDef1._context);
			if (shape == this.valueLine()) {
				continue;
			}
			var tickmark = new $.ig.RadialMenuNumericGaugeTickmarkData();
			tempTb = getText(textDef1);
			tempFi = $.ig.FontUtil.prototype.getFontInfo(tempTb, null);
			tickmark.label($.ig.AppearanceHelper.prototype.fromTextElement(tempTb, tempFi));
			tickmark.tickPath(new $.ig.PathVisualData(1, "tickmark", getPath(shape)));
			gaugeData.tickmarks().add(tickmark);
		}
		gaugeData.trackArcs(new $.ig.PrimitiveVisualDataList());
		var en2 = this.shapes().getEnumerator();
		while (en2.moveNext()) {
			var shapeDef = en2.current();
			if (shapeDef == this.__valueLine || shapeDef == this.__hotTrackLine) {
				continue;
			}
			if (shapeDef._context == this.__valueLine) {
				gaugeData.valueTickLine(new $.ig.PathVisualData(1, "valueTickLine", getPath(shapeDef)));
			} else if ($.ig.util.cast($.ig.AnnularSector.prototype.$type, shapeDef) !== null) {
				gaugeData.trackArcs().add(new $.ig.PathVisualData(1, "trackArc", getPath(shapeDef)));
			}
		}
	}
	,
	hotTrackLine: function () {
		return this.__hotTrackLine;
	}
	,
	valueLine: function () {
		return this.__valueLine;
	}
	,
	$type: new $.ig.Type('RadialMenuNumericGaugeFrame', $.ig.RadialMenuItemFrame.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuNumericItem', 'RadialMenuItem', {
	staticInit: function () {
	},
	init: function () {
		$.ig.RadialMenuItem.prototype.init.call(this);
		this.iconOverlay($.ig.RadialMenuItemOverlayTemplates.prototype.numericOverlayTemplate);
		this.value(NaN);
	},
	calculateChildAngleAdjustment: function (itemPosition, childPositions) {
		var value = $.ig.RadialMenuItem.prototype.calculateChildAngleAdjustment.call(this, itemPosition, childPositions);
		if ($.ig.util.isNaN(value)) {
			var en = childPositions.getEnumerator();
			while (en.moveNext()) {
				var item = en.current();
				var gauge = $.ig.util.cast($.ig.RadialMenuNumericGauge.prototype.$type, item.item());
				if (gauge != null) {
					var numericValue = gauge.pendingValue();
					if ($.ig.util.isNaN(numericValue)) {
						numericValue = gauge.value();
					}
					if (!$.ig.util.isNaN(numericValue)) {
						var midPtParent = itemPosition._startAngle + (itemPosition._endAngle - itemPosition._startAngle) / 2;
						var angle = gauge.angleFromValue(item, numericValue);
						value = midPtParent - angle;
					}
				}
			}
		}
		return value;
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		if (propertyName == $.ig.RadialMenuNumericItem.prototype.valuePropertyName) {
			this.setIconOverlayValue(newValue);
		}
		$.ig.RadialMenuItem.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
	}
	,
	value: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.RadialMenuNumericItem.prototype.valueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.RadialMenuNumericItem.prototype.valueProperty);
		}
	}
	,
	setValueImpl: function (newValue) {
		this.value(newValue);
	}
	,
	valueChanged: null,
	$type: new $.ig.Type('RadialMenuNumericItem', $.ig.RadialMenuItem.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuFrame', 'Object', {
	__menuArea: null,
	__outerRing: null,
	__innerRingFocus: null,
	__innerRing: null,
	__outerRingSectors: null,
	__colorWellPreviewSectors: null,
	__toolAreaSectors: null,
	__toolCheckmarks: null,
	__toolHighlightArcs: null,
	__outerRingArrows: null,
	__visibleItems: null,
	__itemShapes: null,
	__customItemFrames: null,
	__invalidatedCustomVisuals: null,
	init: function () {
		this._menuRect = new $.ig.LiteRect();
		this._toolAreaMidPtRect = new $.ig.LiteRect();
		$.ig.Object.prototype.init.call(this);
		this.__menuArea = new $.ig.Ellipse();
		this.__outerRing = new $.ig.Annulus();
		this.__outerRingSectors = new $.ig.List$1($.ig.AnnularSector.prototype.$type, 0);
		this.__toolAreaSectors = new $.ig.List$1($.ig.AnnularSector.prototype.$type, 0);
		this.__toolCheckmarks = new $.ig.List$1($.ig.AnnularSector.prototype.$type, 0);
		this.__toolHighlightArcs = new $.ig.List$1($.ig.AnnularSector.prototype.$type, 0);
		this.__colorWellPreviewSectors = new $.ig.List$1($.ig.AnnularSector.prototype.$type, 0);
		this.__innerRing = new $.ig.Ellipse();
		this.__innerRingFocus = new $.ig.Ellipse();
		this.__visibleItems = new $.ig.List$1($.ig.RadialMenuItemPosition.prototype.$type, 0);
		this.__outerRingArrows = new $.ig.List$1($.ig.TriangleShape.prototype.$type, 0);
		this.__customItemFrames = new $.ig.Dictionary$2($.ig.RadialMenuItemPosition.prototype.$type, $.ig.RadialMenuItemFrame.prototype.$type, 0);
		this.__itemShapes = new $.ig.Dictionary$2($.ig.ShapeDefinition.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, 0);
		this.__invalidatedCustomVisuals = new $.ig.List$1($.ig.RadialMenuItemPosition.prototype.$type, 0);
	},
	menuArea: function () {
		return this.__menuArea;
	}
	,
	outerRing: function () {
		return this.__outerRing;
	}
	,
	innerRing: function () {
		return this.__innerRing;
	}
	,
	innerRingFocus: function () {
		return this.__innerRingFocus;
	}
	,
	outerRingSectors: function () {
		return this.__outerRingSectors;
	}
	,
	colorWellPreviewSectors: function () {
		return this.__colorWellPreviewSectors;
	}
	,
	toolAreaSectors: function () {
		return this.__toolAreaSectors;
	}
	,
	toolCheckmarks: function () {
		return this.__toolCheckmarks;
	}
	,
	toolHighlightArcs: function () {
		return this.__toolHighlightArcs;
	}
	,
	visibleItems: function () {
		return this.__visibleItems;
	}
	,
	outerRingArrows: function () {
		return this.__outerRingArrows;
	}
	,
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
	itemShapes: function () {
		return this.__itemShapes;
	}
	,
	customItemFrames: function () {
		return this.__customItemFrames;
	}
	,
	invalidatedCustomVisuals: function () {
		return this.__invalidatedCustomVisuals;
	}
	,
	_menuRect: null,
	menuRect: function (value) {
		if (arguments.length === 1) {
			this._menuRect = value;
			return value;
		} else {
			return this._menuRect;
		}
	}
	,
	_toolAreaMidPtRect: null,
	toolAreaMidPtRect: function (value) {
		if (arguments.length === 1) {
			this._toolAreaMidPtRect = value;
			return value;
		} else {
			return this._toolAreaMidPtRect;
		}
	}
	,
	_toolAreaOuterRadiusX: 0,
	_toolAreaOuterRadiusY: 0,
	_contentRotationAnimationAngle: 0,
	contentRotationAnimationAngle: function (value) {
		if (arguments.length === 1) {
			this._contentRotationAnimationAngle = value;
			return value;
		} else {
			return this._contentRotationAnimationAngle;
		}
	}
	,
	_contentScale: 0,
	contentScale: function (value) {
		if (arguments.length === 1) {
			this._contentScale = value;
			return value;
		} else {
			return this._contentScale;
		}
	}
	,
	getItemPosition: function (item) {
		var en = this.__visibleItems.getEnumerator();
		while (en.moveNext()) {
			var position = en.current();
			if (position.item() == item) {
				return position;
			}
		}
		return null;
	}
	,
	getShape: function (item, part) {
		var $self = this;
		var shapes = this.getShapes(part);
		var temp;
		var en = shapes.getEnumerator();
		while (en.moveNext()) {
			var shape = en.current();
			if ((function () { var $ret = $self.__itemShapes.tryGetValue(shape, temp); temp = $ret.p1; return $ret.ret; }()) && temp == item) {
				return shape;
			}
		}
		return null;
	}
	,
	getShapes: function (part) {
		var shapes;
		switch (part) {
			case $.ig.RadialMenuWedgePart.prototype.toolCheckmark:
				shapes = this.__toolCheckmarks;
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolHighlight:
				shapes = this.__toolHighlightArcs;
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolArea:
				shapes = this.__toolAreaSectors;
				break;
			case $.ig.RadialMenuWedgePart.prototype.colorWellPreview:
				shapes = this.__colorWellPreviewSectors;
				break;
			case $.ig.RadialMenuWedgePart.prototype.outerRingArrow:
				shapes = this.__outerRingArrows;
				break;
			case $.ig.RadialMenuWedgePart.prototype.outerRing:
				shapes = this.__outerRingSectors;
				break;
			default:
				$.ig.Debug.prototype.assert1(false, "Unrecognized part:" + $.ig.RadialMenuWedgePart.prototype.getBox(part));
				return null;
		}
		return shapes;
	}
	,
	invalidateCustomItemFrame: function (relatedItem) {
		var en = this.__customItemFrames.keys().getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (item.item() == relatedItem) {
				if (this.__invalidatedCustomVisuals.contains(item)) {
					return true;
				}
				this.__invalidatedCustomVisuals.add(item);
				return true;
			}
		}
		return false;
	}
	,
	$type: new $.ig.Type('RadialMenuFrame', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TextDefinition', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_text: null,
	_textBrush: null,
	_horizontalAlignment: 0,
	_verticalAlignment: 0,
	_location: null,
	_context: null,
	_calculatedRect: null,
	$type: new $.ig.Type('TextDefinition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemLevel', 'Object', {
	__menu: null,
	__parent: null,
	__itemPositions: null,
	__collapsedItems: null,
	__parentLevel: null,
	__wedgeCount: 0,
	__contentAngleAdjustment: 0,
	__expandedItem: null,
	__rootItems: null,
	__isDeactivated: false,
	__startAngle: 0,
	__customDropDownContent: null,
	init: function (menu, parent, parentLevel, rootItems, customDropDownContent) {
		this.__contentAngleAdjustment = NaN;
		$.ig.Object.prototype.init.call(this);
		this.__menu = menu;
		this.__parent = parent;
		this.__parentLevel = parentLevel;
		this.__rootItems = rootItems;
		this.__collapsedItems = new $.ig.List$1($.ig.RadialMenuItemBase.prototype.$type, 0);
		this.__customDropDownContent = customDropDownContent;
		this.hookCollection(this.__rootItems, true);
	},
	customDropDownContent: function () {
		return this.__customDropDownContent;
	}
	,
	expandedItem: function (value) {
		if (arguments.length === 1) {
			this.__expandedItem = value;
			return value;
		} else {
			return this.__expandedItem;
		}
	}
	,
	itemPositions: function () {
		if (this.__itemPositions == null) {
			this.calculateItemPositions();
		}
		return this.__itemPositions;
	}
	,
	parent: function () {
		return this.__parent;
	}
	,
	parentLevel: function () {
		return this.__parentLevel;
	}
	,
	startAngle: function () {
		return this.__startAngle;
	}
	,
	wedgeCount: function () {
		if (this.__itemPositions == null) {
			this.calculateItemPositions();
		}
		return this.__wedgeCount;
	}
	,
	onDeactivated: function () {
		this.__isDeactivated = true;
		this.hookCollection(this.__rootItems, false);
		this.unhookItems();
	}
	,
	getItemPosition: function (item) {
		var index = this.getItemPositionIndex(item);
		return index < 0 ? null : this.itemPositions().item(index);
	}
	,
	getItemPositionIndex: function (item) {
		if (item != null) {
			var positions = this.itemPositions();
			for (var i = 0, count = positions.count(); i < count; i++) {
				if (positions.item(i).item() == item) {
					return i;
				}
			}
		}
		return -1;
	}
	,
	getItemPositionIndex1: function (angle) {
		var positions = this.itemPositions();
		var diff = 1.7976931348623157E+308;
		var idx = -1;
		angle = $.ig.ShapeUtilities.prototype.normalizeRadians(angle);
		for (var i = 0, count = positions.count(); i < count; i++) {
			var pos = positions.item(i);
			var start = pos._startAngle;
			var end = pos._endAngle;
			while (start < -Math.PI) {
				start += $.ig.ShapeUtilities.prototype.twoPI;
				end += $.ig.ShapeUtilities.prototype.twoPI;
			}
			while (end > Math.PI) {
				start -= $.ig.ShapeUtilities.prototype.twoPI;
				end -= $.ig.ShapeUtilities.prototype.twoPI;
			}
			if (start <= angle && end >= angle) {
				return i;
			}
			var midPt = start + (end - start) / 2;
			midPt = $.ig.ShapeUtilities.prototype.normalizeRadians(midPt);
			var tempDiff = Math.abs(midPt - angle);
			if (tempDiff < diff) {
				diff = tempDiff;
				idx = i;
			}
		}
		return idx;
	}
	,
	getNextItem: function (item, next) {
		var positions = this.itemPositions();
		var index;
		if (item == null) {
			index = this.getItemPositionIndex1(this.startAngle());
		} else {
			index = this.getItemPositionIndex(item);
			if (index >= 0 && next) {
				index = index == positions.count() ? 0 : index + 1;
			}
		}
		if (!next) {
			index = index == 0 ? positions.count() - 1 : index - 1;
		}
		if (index < 0) {
			return null;
		}
		if (index == positions.count()) {
			index = 0;
		}
		var nextItem = positions.item(index);
		if (item != null) {
			if (nextItem == this.getNextItem(null, next)) {
				return null;
			}
		}
		return nextItem;
	}
	,
	getStartAngle: function (extraWedgePercentRotation, wedgeSweep) {
		var DefaultStartAngle = 0;
		var startAngle = (DefaultStartAngle + this.__menu.rotationInDegrees()) * Math.PI / 180;
		startAngle += (this.__menu.rotationAsPercentageOfWedge() + extraWedgePercentRotation) * wedgeSweep;
		startAngle = $.ig.ShapeUtilities.prototype.normalizeRadians(startAngle);
		return startAngle;
	}
	,
	getWedgeSweep: function () {
		var totalSlots = Math.max(this.wedgeCount(), this.__menu.minWedgeCount());
		return $.ig.ShapeUtilities.prototype.twoPI / totalSlots;
	}
	,
	verifyItemPositionAngles: function (extraWedgePercentRotation) {
		var itemPositions = this.itemPositions();
		var wedgePadding = this.__menu.wedgePaddingInDegrees() * Math.PI / 180;
		var halfWedgePadding = wedgePadding / 2;
		var wedgeSweep = this.getWedgeSweep();
		var startAngle = this.getStartAngle(extraWedgePercentRotation, wedgeSweep);
		this.__startAngle = startAngle;
		var en = itemPositions.getEnumerator();
		while (en.moveNext()) {
			var slot = en.current();
			var actualStart = startAngle + slot._offset * wedgeSweep + halfWedgePadding;
			var actualEnd = actualStart + wedgeSweep * slot._span - wedgePadding;
			if (actualEnd < actualStart) {
				var halfOverlap = (actualEnd - actualStart) / 2;
				actualStart += halfOverlap;
				actualEnd = actualStart;
			}
			slot._startAngle = actualStart;
			slot._endAngle = actualEnd;
		}
		if ($.ig.util.isNaN(this.__contentAngleAdjustment)) {
			if (this.__parent != null && this.__parent.autoRotateChildren()) {
				var parentSlot = this.__parentLevel.getItemPosition(this.__parent);
				if (parentSlot != null) {
					this.__contentAngleAdjustment = this.__parent.calculateChildAngleAdjustment(parentSlot, itemPositions);
				}
			}
			if ($.ig.util.isNaN(this.__contentAngleAdjustment)) {
				this.__contentAngleAdjustment = 0;
			}
		}
		if (!$.ig.util.isNaN(this.__contentAngleAdjustment) && this.__contentAngleAdjustment != 0) {
			var en1 = itemPositions.getEnumerator();
			while (en1.moveNext()) {
				var slot1 = en1.current();
				slot1._startAngle = slot1._startAngle + this.__contentAngleAdjustment;
				slot1._endAngle = slot1._endAngle + this.__contentAngleAdjustment;
			}
		}
	}
	,
	addSlots: function (items, slots) {
		var hookEvents = !this.__isDeactivated;
		var en = items.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			if (hookEvents) {
				item.propertyChanged = $.ig.Delegate.prototype.combine(item.propertyChanged, this.onChildPropertyChanged.runOn(this));
			}
			if (item.__visibility == $.ig.Visibility.prototype.collapsed) {
				this.__collapsedItems.add(item);
				continue;
			}
			var span = item.wedgeSpan();
			var start = item.wedgeIndex();
			slots.add(new $.ig.RadialMenuItemPosition(item, start, span));
		}
		var en1 = items.getEnumerator();
		while (en1.moveNext()) {
			var item1 = en1.current();
			var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, item1);
			if (mi != null && mi.displayAsChecked() && mi.childItemPlacementResolved() == $.ig.RadialMenuChildItemPlacement.prototype.asSiblingsWhenChecked) {
				if (hookEvents) {
					this.hookCollection(mi.itemContainers(), true);
				}
				this.addSlots(mi.itemContainers(), slots);
			}
		}
	}
	,
	calculateItemPositions: function () {
		$.ig.Debug.prototype.assert(this.__collapsedItems.count() == 0);
		this.__collapsedItems.clear();
		var slots = new $.ig.List$1($.ig.RadialMenuItemPosition.prototype.$type, 0);
		this.__itemPositions = slots;
		this.addSlots(this.__rootItems, slots);
		slots.sort2($.ig.RadialMenuItemLevel.prototype.compareItemPosition);
		var wedges = new $.ig.List$1($.ig.Boolean.prototype.$type, 0);
		var firstOpenWedgeIndex = 0;
		var adjustedOffsetCount = 0;
		var en = slots.getEnumerator();
		while (en.moveNext()) {
			var slot = en.current();
			if (slot._offset < 0) {
				adjustedOffsetCount++;
				slot._offset = wedges.count();
				for (var i = firstOpenWedgeIndex, count = wedges.count(); i < count; i++) {
					if (!wedges.__inner[i]) {
						if (i + slot._span >= count) {
							break;
						}
						var isValid = true;
						for (var j = 1; j < slot._span; j++) {
							if (wedges.__inner[i + j]) {
								i += j;
								isValid = false;
								break;
							}
						}
						if (!isValid) {
							continue;
						}
						slot._offset = i;
						if (i == firstOpenWedgeIndex) {
							firstOpenWedgeIndex = i + slot._span;
						}
						break;
					} else if (i == firstOpenWedgeIndex) {
						firstOpenWedgeIndex = i + 1;
					}
				}
			}
			for (var i1 = slot._offset, end = i1 + slot._span; i1 < end; i1++) {
				while (i1 >= wedges.count()) {
					wedges.add(false);
				}
				if (!wedges.__inner[i1]) {
					wedges.__inner[i1] = true;
				}
			}
			slot._startAngle = 0;
			slot._endAngle = 0;
		}
		if (adjustedOffsetCount > 0 && adjustedOffsetCount != slots.count()) {
			slots.sort2($.ig.RadialMenuItemLevel.prototype.compareItemPosition);
		}
		this.__wedgeCount = wedges.count();
	}
	,
	compareItemPosition: function (p1, p2) {
		if (p1._offset >= 0) {
			if (p2._offset < 0) {
				return -1;
			}
			if (p1._offset == p2._offset) {
				return 0;
			} else if (p1._offset < p2._offset) {
				return -1;
			} else {
				return 1;
			}
		}
		return p2._offset < 0 ? 0 : 1;
	}
	,
	hookCollection: function (children, hook) {
		var notifyCollection = $.ig.util.cast($.ig.INotifyCollectionChanged.prototype.$type, children);
		if (null != notifyCollection) {
			if (hook) {
				notifyCollection.collectionChanged = $.ig.Delegate.prototype.combine(notifyCollection.collectionChanged, this.onRootItemsChanged.runOn(this));
			} else {
				notifyCollection.collectionChanged = $.ig.Delegate.prototype.remove(notifyCollection.collectionChanged, this.onRootItemsChanged.runOn(this));
			}
		}
	}
	,
	onChildPropertyChanged: function (sender, e) {
		var notifyMenu = false;
		var mi = $.ig.util.cast($.ig.RadialMenuItem.prototype.$type, sender);
		switch (e.propertyName()) {
			case $.ig.RadialMenuItemBase.prototype.checkedHighlightBrushPropertyName:
			case $.ig.RadialMenuItemBase.prototype.outerRingButtonFillPropertyName:
			case $.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundPropertyName:
			case $.ig.RadialMenuItemBase.prototype.outerRingButtonStrokePropertyName:
			case $.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessPropertyName:
			case $.ig.RadialMenuItemBase.prototype.innerAreaFillPropertyName:
			case $.ig.RadialMenuItemBase.prototype.innerAreaStrokePropertyName:
			case $.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessPropertyName:
			case $.ig.RadialMenuItemBase.prototype.isEnabledPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.toolTipResolvedPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.dirtyCustomVisualsPropertyName:
			case $.ig.RadialMenuNumericGauge.prototype.invalidateCustomVisualsPropertyName:
				notifyMenu = true;
				break;
			case $.ig.RadialMenuItemBase.prototype.displayAsCheckedPropertyName:
				notifyMenu = true;
				if (mi != null && mi.childItemPlacementResolved() == $.ig.RadialMenuChildItemPlacement.prototype.asSiblingsWhenChecked) {
					this.removeItemPositions();
				}
				break;
			case $.ig.RadialMenuItem.prototype.childItemPlacementPropertyName:
			case $.ig.RadialMenuItemBase.prototype.outerRingButtonTypePropertyName:
			case $.ig.RadialMenuItemBase.prototype.wedgeIndexPropertyName:
			case $.ig.RadialMenuItemBase.prototype.wedgeSpanPropertyName:
			case $.ig.RadialMenuItemBase.prototype.visibilityProxyPropertyName:
				notifyMenu = true;
				this.removeItemPositions();
				break;
		}
		if (notifyMenu) {
			this.__menu.onItemLevelStateChanged(this, $.ig.util.cast($.ig.RadialMenuItemBase.prototype.$type, sender), e.propertyName());
		}
	}
	,
	onRootItemsChanged: function (sender, e) {
		this.removeItemPositions();
		this.__menu.onItemLevelStateChanged(this, null, null);
	}
	,
	removeItemPositions: function () {
		if (this.__itemPositions != null) {
			this.unhookItems();
			this.__collapsedItems.clear();
			this.__itemPositions = null;
		}
	}
	,
	unhookItems: function () {
		if (this.__itemPositions != null) {
			var en = this.__itemPositions.getEnumerator();
			while (en.moveNext()) {
				var slot = en.current();
				var $t = slot.item();
				$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.onChildPropertyChanged.runOn(this));
				this.hookCollection(slot.item().itemContainers(), false);
			}
			var en1 = this.__collapsedItems.getEnumerator();
			while (en1.moveNext()) {
				var item = en1.current();
				item.propertyChanged = $.ig.Delegate.prototype.remove(item.propertyChanged, this.onChildPropertyChanged.runOn(this));
			}
		}
	}
	,
	$type: new $.ig.Type('RadialMenuItemLevel', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuItemPosition', 'Object', {
	__item: null,
	init: function (item, offset, span) {
		$.ig.Object.prototype.init.call(this);
		this.__item = item;
		this._offset = offset;
		this._span = span;
	},
	item: function () {
		return this.__item;
	}
	,
	_offset: 0,
	_span: 0,
	_contentAngle: 0,
	_contentLocation: null,
	_startAngle: 0,
	_endAngle: 0,
	$type: new $.ig.Type('RadialMenuItemPosition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuPointerAnimationInfo', 'Object', {
	__item: null,
	__area: 0,
	__completed: 0,
	init: function (item, area) {
		$.ig.Object.prototype.init.call(this);
		this.__item = item;
		this.__area = area;
	},
	item: function () {
		return this.__item;
	}
	,
	area: function () {
		return this.__area;
	}
	,
	progressCompleted: function (value) {
		if (arguments.length === 1) {
			this.__completed = value;
			return value;
		} else {
			return this.__completed;
		}
	}
	,
	$type: new $.ig.Type('RadialMenuPointerAnimationInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ColorNameCalculator', 'Object', {
	__colorNames: null,
	__closestColorNames: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__colorNames = new $.ig.Dictionary$2($.ig.Color.prototype.$type, String, 0);
		this.__closestColorNames = new $.ig.Dictionary$2($.ig.Color.prototype.$type, String, 0);
	},
	addNamedColor: function (color, name) {
		this.__colorNames.item(color, name);
	}
	,
	getColorName: function (color) {
		var $self = this;
		var name;
		if (!(function () { var $ret = $self.__closestColorNames.tryGetValue(color, name); name = $ret.p1; return $ret.ret; }())) {
			this.__closestColorNames.item(color, name = this.calculateColorName(color));
		}
		return name;
	}
	,
	calculateColorName: function (color) {
		var diff = 1.7976931348623157E+308;
		var name = null;
		var en = this.__colorNames.getEnumerator();
		while (en.moveNext()) {
			var pair = en.current();
			var temp = $.ig.ColorNameCalculator.prototype.getColorDifference(pair.key(), color);
			if (temp < diff) {
				diff = temp;
				name = pair.value();
			}
		}
		return name;
	}
	,
	getColorDifference: function (baseColor, color) {
		var l;
		var a;
		var b;
		var $ret = $.ig.ColorNameCalculator.prototype.rGBToCIELAB(baseColor, l, a, b);
		l = $ret.p1;
		a = $ret.p2;
		b = $ret.p3;
		var l2;
		var a2;
		var b2;
		var $ret1 = $.ig.ColorNameCalculator.prototype.rGBToCIELAB(color, l2, a2, b2);
		l2 = $ret1.p1;
		a2 = $ret1.p2;
		b2 = $ret1.p3;
		return (Math.pow(l - l2, 2)) + (Math.pow(a - a2, 2)) + (Math.pow(b - b2, 2));
	}
	,
	rGBToCIELAB: function (color, l, a, b) {
		var normalizedR = (color.r() / 255);
		var normalizedG = (color.g() / 255);
		var normalizedB = (color.b() / 255);
		var X = normalizedR * 0.412453 + normalizedG * 0.35758 + normalizedB * 0.180423;
		var Y = normalizedR * 0.212671 + normalizedG * 0.71516 + normalizedB * 0.072169;
		var Z = normalizedR * 0.019334 + normalizedG * 0.119193 + normalizedB * 0.950227;
		var tempX = $.ig.ColorNameCalculator.prototype.rGBToCIELABHelper(X / 0.95047);
		var tempY = $.ig.ColorNameCalculator.prototype.rGBToCIELABHelper(Y / 1);
		var tempZ = $.ig.ColorNameCalculator.prototype.rGBToCIELABHelper(Z / 1.08883);
		l = (116 * tempY) - 16;
		a = 500 * (tempX - tempY);
		b = 200 * (tempY - tempZ);
		return {
			p1: l,
			p2: a,
			p3: b
		};
	}
	,
	rGBToCIELABHelper: function (value) {
		if (value > 0.008856) {
			return Math.pow(value, (1 / 3));
		} else {
			return (7.787 * value) + (16 / 116);
		}
	}
	,
	$type: new $.ig.Type('ColorNameCalculator', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuUtilities', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	clamp: function (value, min, max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}
		return value;
	}
	,
	staticInit: function () {
		var calculator = new $.ig.ColorNameCalculator();
		var add = calculator.addNamedColor.runOn(calculator);
		add($.ig.Color.prototype.fromArgb(255, 240, 248, 255), "Alice Blue");
		add($.ig.Color.prototype.fromArgb(255, 250, 235, 215), "Antique White");
		add($.ig.Color.prototype.fromArgb(255, 0, 255, 255), "Aqua");
		add($.ig.Color.prototype.fromArgb(255, 127, 255, 212), "Aquamarine");
		add($.ig.Color.prototype.fromArgb(255, 240, 255, 255), "Azure");
		add($.ig.Color.prototype.fromArgb(255, 245, 245, 220), "Beige");
		add($.ig.Color.prototype.fromArgb(255, 255, 228, 196), "Bisque");
		add($.ig.Color.prototype.fromArgb(255, 0, 0, 0), "Black");
		add($.ig.Color.prototype.fromArgb(255, 255, 235, 205), "Blanched Almond");
		add($.ig.Color.prototype.fromArgb(255, 0, 0, 255), "Blue");
		add($.ig.Color.prototype.fromArgb(255, 138, 43, 226), "Blue Violet");
		add($.ig.Color.prototype.fromArgb(255, 165, 42, 42), "Brown");
		add($.ig.Color.prototype.fromArgb(255, 222, 184, 135), "Burly Wood");
		add($.ig.Color.prototype.fromArgb(255, 95, 158, 160), "Cadet Blue");
		add($.ig.Color.prototype.fromArgb(255, 127, 255, 0), "Chartreuse");
		add($.ig.Color.prototype.fromArgb(255, 210, 105, 30), "Chocolate");
		add($.ig.Color.prototype.fromArgb(255, 255, 127, 80), "Coral");
		add($.ig.Color.prototype.fromArgb(255, 100, 149, 237), "Cornflower Blue");
		add($.ig.Color.prototype.fromArgb(255, 255, 248, 220), "Cornsilk");
		add($.ig.Color.prototype.fromArgb(255, 220, 20, 60), "Crimson");
		add($.ig.Color.prototype.fromArgb(255, 0, 255, 255), "Cyan");
		add($.ig.Color.prototype.fromArgb(255, 0, 0, 139), "Dark Blue");
		add($.ig.Color.prototype.fromArgb(255, 0, 139, 139), "Dark Cyan");
		add($.ig.Color.prototype.fromArgb(255, 184, 134, 11), "Dark Goldenrod");
		add($.ig.Color.prototype.fromArgb(255, 169, 169, 169), "Dark Gray");
		add($.ig.Color.prototype.fromArgb(255, 0, 100, 0), "Dark Green");
		add($.ig.Color.prototype.fromArgb(255, 189, 183, 107), "Dark Khaki");
		add($.ig.Color.prototype.fromArgb(255, 139, 0, 139), "Dark Magenta");
		add($.ig.Color.prototype.fromArgb(255, 85, 107, 47), "Dark Olive Green");
		add($.ig.Color.prototype.fromArgb(255, 255, 140, 0), "Dark Orange");
		add($.ig.Color.prototype.fromArgb(255, 153, 50, 204), "Dark Orchid");
		add($.ig.Color.prototype.fromArgb(255, 139, 0, 0), "Dark Red");
		add($.ig.Color.prototype.fromArgb(255, 233, 150, 122), "Dark Salmon");
		add($.ig.Color.prototype.fromArgb(255, 143, 188, 143), "Dark Sea Green");
		add($.ig.Color.prototype.fromArgb(255, 72, 61, 139), "Dark Slate Blue");
		add($.ig.Color.prototype.fromArgb(255, 47, 79, 79), "Dark Slate Gray");
		add($.ig.Color.prototype.fromArgb(255, 0, 206, 209), "Dark Turquoise");
		add($.ig.Color.prototype.fromArgb(255, 148, 0, 211), "Dark Violet");
		add($.ig.Color.prototype.fromArgb(255, 255, 20, 147), "Deep Pink");
		add($.ig.Color.prototype.fromArgb(255, 0, 191, 255), "Deep Sky Blue");
		add($.ig.Color.prototype.fromArgb(255, 105, 105, 105), "Dim Gray");
		add($.ig.Color.prototype.fromArgb(255, 30, 144, 255), "Dodger Blue");
		add($.ig.Color.prototype.fromArgb(255, 178, 34, 34), "Firebrick");
		add($.ig.Color.prototype.fromArgb(255, 255, 250, 240), "Floral White");
		add($.ig.Color.prototype.fromArgb(255, 34, 139, 34), "Forest Green");
		add($.ig.Color.prototype.fromArgb(255, 255, 0, 255), "Fuchsia");
		add($.ig.Color.prototype.fromArgb(255, 220, 220, 220), "Gainsboro");
		add($.ig.Color.prototype.fromArgb(255, 248, 248, 255), "Ghost White");
		add($.ig.Color.prototype.fromArgb(255, 255, 215, 0), "Gold");
		add($.ig.Color.prototype.fromArgb(255, 218, 165, 32), "Goldenrod");
		add($.ig.Color.prototype.fromArgb(255, 128, 128, 128), "Gray");
		add($.ig.Color.prototype.fromArgb(255, 0, 128, 0), "Green");
		add($.ig.Color.prototype.fromArgb(255, 173, 255, 47), "Green Yellow");
		add($.ig.Color.prototype.fromArgb(255, 240, 255, 240), "Honeydew");
		add($.ig.Color.prototype.fromArgb(255, 255, 105, 180), "Hot Pink");
		add($.ig.Color.prototype.fromArgb(255, 205, 92, 92), "Indian Red");
		add($.ig.Color.prototype.fromArgb(255, 75, 0, 130), "Indigo");
		add($.ig.Color.prototype.fromArgb(255, 255, 255, 240), "Ivory");
		add($.ig.Color.prototype.fromArgb(255, 240, 230, 140), "Khaki");
		add($.ig.Color.prototype.fromArgb(255, 230, 230, 250), "Lavender");
		add($.ig.Color.prototype.fromArgb(255, 255, 240, 245), "Lavender Blush");
		add($.ig.Color.prototype.fromArgb(255, 124, 252, 0), "Lawn Green");
		add($.ig.Color.prototype.fromArgb(255, 255, 250, 205), "Lemon Chiffon");
		add($.ig.Color.prototype.fromArgb(255, 173, 216, 230), "Light Blue");
		add($.ig.Color.prototype.fromArgb(255, 240, 128, 128), "Light Coral");
		add($.ig.Color.prototype.fromArgb(255, 224, 255, 255), "Light Cyan");
		add($.ig.Color.prototype.fromArgb(255, 250, 250, 210), "Light Goldenrod Yellow");
		add($.ig.Color.prototype.fromArgb(255, 211, 211, 211), "Light Gray");
		add($.ig.Color.prototype.fromArgb(255, 144, 238, 144), "Light Green");
		add($.ig.Color.prototype.fromArgb(255, 255, 182, 193), "Light Pink");
		add($.ig.Color.prototype.fromArgb(255, 255, 160, 122), "Light Salmon");
		add($.ig.Color.prototype.fromArgb(255, 32, 178, 170), "Light Sea Green");
		add($.ig.Color.prototype.fromArgb(255, 135, 206, 250), "Light Sky Blue");
		add($.ig.Color.prototype.fromArgb(255, 119, 136, 153), "Light Slate Gray");
		add($.ig.Color.prototype.fromArgb(255, 176, 196, 222), "Light Steel Blue");
		add($.ig.Color.prototype.fromArgb(255, 255, 255, 224), "Light Yellow");
		add($.ig.Color.prototype.fromArgb(255, 0, 255, 0), "Lime");
		add($.ig.Color.prototype.fromArgb(255, 50, 205, 50), "Lime Green");
		add($.ig.Color.prototype.fromArgb(255, 250, 240, 230), "Linen");
		add($.ig.Color.prototype.fromArgb(255, 255, 0, 255), "Magenta");
		add($.ig.Color.prototype.fromArgb(255, 128, 0, 0), "Maroon");
		add($.ig.Color.prototype.fromArgb(255, 102, 205, 170), "Medium Aquamarine");
		add($.ig.Color.prototype.fromArgb(255, 0, 0, 205), "Medium Blue");
		add($.ig.Color.prototype.fromArgb(255, 186, 85, 211), "Medium Orchid");
		add($.ig.Color.prototype.fromArgb(255, 147, 112, 219), "Medium Purple");
		add($.ig.Color.prototype.fromArgb(255, 60, 179, 113), "Medium Sea Green");
		add($.ig.Color.prototype.fromArgb(255, 123, 104, 238), "Medium Slate Blue");
		add($.ig.Color.prototype.fromArgb(255, 0, 250, 154), "Medium Spring Green");
		add($.ig.Color.prototype.fromArgb(255, 72, 209, 204), "Medium Turquoise");
		add($.ig.Color.prototype.fromArgb(255, 199, 21, 133), "Medium Violet Red");
		add($.ig.Color.prototype.fromArgb(255, 25, 25, 112), "Midnight Blue");
		add($.ig.Color.prototype.fromArgb(255, 245, 255, 250), "Mint Cream");
		add($.ig.Color.prototype.fromArgb(255, 255, 228, 225), "Misty Rose");
		add($.ig.Color.prototype.fromArgb(255, 255, 228, 181), "Moccasin");
		add($.ig.Color.prototype.fromArgb(255, 255, 222, 173), "Navajo White");
		add($.ig.Color.prototype.fromArgb(255, 0, 0, 128), "Navy");
		add($.ig.Color.prototype.fromArgb(255, 253, 245, 230), "Old Lace");
		add($.ig.Color.prototype.fromArgb(255, 128, 128, 0), "Olive");
		add($.ig.Color.prototype.fromArgb(255, 107, 142, 35), "Olive Drab");
		add($.ig.Color.prototype.fromArgb(255, 255, 165, 0), "Orange");
		add($.ig.Color.prototype.fromArgb(255, 255, 69, 0), "Orange Red");
		add($.ig.Color.prototype.fromArgb(255, 218, 112, 214), "Orchid");
		add($.ig.Color.prototype.fromArgb(255, 238, 232, 170), "Pale Goldenrod");
		add($.ig.Color.prototype.fromArgb(255, 152, 251, 152), "Pale Green");
		add($.ig.Color.prototype.fromArgb(255, 175, 238, 238), "Pale Turquoise");
		add($.ig.Color.prototype.fromArgb(255, 219, 112, 147), "Pale Violet Red");
		add($.ig.Color.prototype.fromArgb(255, 255, 239, 213), "Papaya Whip");
		add($.ig.Color.prototype.fromArgb(255, 255, 218, 185), "Peach Puff");
		add($.ig.Color.prototype.fromArgb(255, 205, 133, 63), "Peru");
		add($.ig.Color.prototype.fromArgb(255, 255, 192, 203), "Pink");
		add($.ig.Color.prototype.fromArgb(255, 221, 160, 221), "Plum");
		add($.ig.Color.prototype.fromArgb(255, 176, 224, 230), "Powder Blue");
		add($.ig.Color.prototype.fromArgb(255, 128, 0, 128), "Purple");
		add($.ig.Color.prototype.fromArgb(255, 255, 0, 0), "Red");
		add($.ig.Color.prototype.fromArgb(255, 188, 143, 143), "Rosy Brown");
		add($.ig.Color.prototype.fromArgb(255, 65, 105, 225), "Royal Blue");
		add($.ig.Color.prototype.fromArgb(255, 139, 69, 19), "Saddle Brown");
		add($.ig.Color.prototype.fromArgb(255, 250, 128, 114), "Salmon");
		add($.ig.Color.prototype.fromArgb(255, 244, 164, 96), "Sandy Brown");
		add($.ig.Color.prototype.fromArgb(255, 46, 139, 87), "Sea Green");
		add($.ig.Color.prototype.fromArgb(255, 255, 245, 238), "Sea Shell");
		add($.ig.Color.prototype.fromArgb(255, 160, 82, 45), "Sienna");
		add($.ig.Color.prototype.fromArgb(255, 192, 192, 192), "Silver");
		add($.ig.Color.prototype.fromArgb(255, 135, 206, 235), "Sky Blue");
		add($.ig.Color.prototype.fromArgb(255, 106, 90, 205), "Slate Blue");
		add($.ig.Color.prototype.fromArgb(255, 112, 128, 144), "Slate Gray");
		add($.ig.Color.prototype.fromArgb(255, 255, 250, 250), "Snow");
		add($.ig.Color.prototype.fromArgb(255, 0, 255, 127), "Spring Green");
		add($.ig.Color.prototype.fromArgb(255, 70, 130, 180), "Steel Blue");
		add($.ig.Color.prototype.fromArgb(255, 210, 180, 140), "Tan");
		add($.ig.Color.prototype.fromArgb(255, 0, 128, 128), "Teal");
		add($.ig.Color.prototype.fromArgb(255, 216, 191, 216), "Thistle");
		add($.ig.Color.prototype.fromArgb(255, 255, 99, 71), "Tomato");
		add($.ig.Color.prototype.fromArgb(255, 64, 224, 208), "Turquoise");
		add($.ig.Color.prototype.fromArgb(255, 238, 130, 238), "Violet");
		add($.ig.Color.prototype.fromArgb(255, 245, 222, 179), "Wheat");
		add($.ig.Color.prototype.fromArgb(255, 255, 255, 255), "White");
		add($.ig.Color.prototype.fromArgb(255, 245, 245, 245), "White Smoke");
		add($.ig.Color.prototype.fromArgb(255, 255, 255, 0), "Yellow");
		add($.ig.Color.prototype.fromArgb(255, 154, 205, 50), "Yellow Green");
		$.ig.RadialMenuUtilities.prototype.__colorCalculator = calculator;
	},
	adjustRgb: function (color, rgbAdjustment) {
		return $.ig.Color.prototype.fromArgb(color.a(), Math.min(Math.max(color.r() + rgbAdjustment, 0), 255), Math.min(Math.max(color.g() + rgbAdjustment, 0), 255), Math.min(Math.max(color.b() + rgbAdjustment, 0), 255));
	}
	,
	calculateForeground1: function (background, foregroundColors) {
		var backBrightness = $.ig.RadialMenuUtilities.prototype.calculateSRgbBrightness(background);
		var previousContrast = -1;
		var color = $.ig.RadialMenuUtilities.prototype._transparent;
		for (var i = 0; i < foregroundColors.length; i++) {
			var foreground = foregroundColors[i];
			var brightness = $.ig.RadialMenuUtilities.prototype.calculateSRgbBrightness(foreground);
			var contrast = brightness > backBrightness ? (brightness + 0.05) / (backBrightness + 0.05) : (backBrightness + 0.05) / (brightness + 0.05);
			if (contrast > previousContrast) {
				previousContrast = contrast;
				color = foreground;
			}
		}
		return color;
	}
	,
	calculateForeground: function (background) {
		var backBrightness = $.ig.RadialMenuUtilities.prototype.calculateSRgbBrightness(background);
		var whiteContrast = 1.05 / (backBrightness + 0.05);
		var blackContrast = (backBrightness + 0.05) / 0.05;
		return blackContrast > whiteContrast ? $.ig.RadialMenuUtilities.prototype._black : $.ig.RadialMenuUtilities.prototype._white;
	}
	,
	calculateRect: function (pt, size, hAlign, vAlign) {
		var rect = new $.ig.LiteRect(1, 0, 0, size.width(), size.height());
		switch (hAlign) {
			case $.ig.HorizontalAlignment.prototype.left:
			case $.ig.HorizontalAlignment.prototype.stretch:
				rect.x = pt.__x;
				break;
			case $.ig.HorizontalAlignment.prototype.right:
				rect.x = pt.__x - size.width();
				break;
			case $.ig.HorizontalAlignment.prototype.center:
				rect.x = pt.__x - size.width() / 2;
				break;
		}
		switch (vAlign) {
			case $.ig.VerticalAlignment.prototype.top:
			case $.ig.VerticalAlignment.prototype.stretch:
				rect.y = pt.__y;
				break;
			case $.ig.VerticalAlignment.prototype.bottom:
				rect.y = pt.__y - size.height();
				break;
			case $.ig.VerticalAlignment.prototype.center:
				rect.y = pt.__y - size.height() / 2;
				break;
		}
		return rect;
	}
	,
	calculateSRgbBrightness: function (color) {
		var Gamma = 2.2;
		var brightness = (Math.pow(color.r() / 255, Gamma) * 0.2126) + (Math.pow(color.g() / 255, Gamma) * 0.7152) + (Math.pow(color.b() / 255, Gamma) * 0.0722);
		return brightness;
	}
	,
	colorNameCalculator: function () {
		return $.ig.RadialMenuUtilities.prototype.__colorCalculator;
	}
	,
	createBrush: function (color) {
		return (function () {
			var $ret = new $.ig.Brush();
			$ret.fill(color.colorString());
			return $ret;
		}());
	}
	,
	equals: function (colorX, colorY) {
		return colorX.a() == colorY.a() && colorX.r() == colorY.r() && colorX.g() == colorY.g() && colorX.b() == colorY.b();
	}
	,
	isAnimatorActive: function (animator) {
		return animator != null && animator.animationActive() && animator.getElapsedMilliseconds() < animator.intervalMilliseconds();
	}
	,
	$type: new $.ig.Type('RadialMenuUtilities', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuVisualData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
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
	_menuArea: null,
	menuArea: function (value) {
		if (arguments.length === 1) {
			this._menuArea = value;
			return value;
		} else {
			return this._menuArea;
		}
	}
	,
	_outerRing: null,
	outerRing: function (value) {
		if (arguments.length === 1) {
			this._outerRing = value;
			return value;
		} else {
			return this._outerRing;
		}
	}
	,
	_innerRing: null,
	innerRing: function (value) {
		if (arguments.length === 1) {
			this._innerRing = value;
			return value;
		} else {
			return this._innerRing;
		}
	}
	,
	_innerRingFocus: null,
	innerRingFocus: function (value) {
		if (arguments.length === 1) {
			this._innerRingFocus = value;
			return value;
		} else {
			return this._innerRingFocus;
		}
	}
	,
	_items: null,
	items: function (value) {
		if (arguments.length === 1) {
			this._items = value;
			return value;
		} else {
			return this._items;
		}
	}
	,
	_colorWellPreviews: null,
	colorWellPreviews: function (value) {
		if (arguments.length === 1) {
			this._colorWellPreviews = value;
			return value;
		} else {
			return this._colorWellPreviews;
		}
	}
	,
	_itemOpacity: 0,
	itemOpacity: function (value) {
		if (arguments.length === 1) {
			this._itemOpacity = value;
			return value;
		} else {
			return this._itemOpacity;
		}
	}
	,
	_outerPathOpacity: 0,
	outerPathOpacity: function (value) {
		if (arguments.length === 1) {
			this._outerPathOpacity = value;
			return value;
		} else {
			return this._outerPathOpacity;
		}
	}
	,
	_itemToolTip: null,
	itemToolTip: function (value) {
		if (arguments.length === 1) {
			this._itemToolTip = value;
			return value;
		} else {
			return this._itemToolTip;
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		var hasAdded = false;
		sb.appendLine1("viewPort: { left: " + this.viewport().left() + ", top: " + this.viewport().top() + ", width: " + this.viewport().width() + ", height: " + this.viewport().height() + "}");
		sb.appendLine1(", itemOpacity: " + this.itemOpacity());
		sb.appendLine1(", outerPathOpacity: " + this.outerPathOpacity());
		hasAdded = true;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "menuArea", this.menuArea(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "outerRing", this.outerRing(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "innerRing", this.innerRing(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "innerRingFocus", this.innerRingFocus(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItems(sb, "items", this.items(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItems(sb, "colorWellPreviews", this.colorWellPreviews(), !hasAdded) || hasAdded;
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('RadialMenuVisualData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuItemVisualData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_outerRingPath: null,
	outerRingPath: function (value) {
		if (arguments.length === 1) {
			this._outerRingPath = value;
			return value;
		} else {
			return this._outerRingPath;
		}
	}
	,
	_outerRingArrowPath: null,
	outerRingArrowPath: function (value) {
		if (arguments.length === 1) {
			this._outerRingArrowPath = value;
			return value;
		} else {
			return this._outerRingArrowPath;
		}
	}
	,
	_toolAreaPath: null,
	toolAreaPath: function (value) {
		if (arguments.length === 1) {
			this._toolAreaPath = value;
			return value;
		} else {
			return this._toolAreaPath;
		}
	}
	,
	_checkmarkPath: null,
	checkmarkPath: function (value) {
		if (arguments.length === 1) {
			this._checkmarkPath = value;
			return value;
		} else {
			return this._checkmarkPath;
		}
	}
	,
	_highlightPath: null,
	highlightPath: function (value) {
		if (arguments.length === 1) {
			this._highlightPath = value;
			return value;
		} else {
			return this._highlightPath;
		}
	}
	,
	_type: null,
	type: function (value) {
		if (arguments.length === 1) {
			this._type = value;
			return value;
		} else {
			return this._type;
		}
	}
	,
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
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		this.serializeImpl(sb, false);
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	serializeImpl: function (sb, hasAdded) {
		if (hasAdded) {
			sb.appendLine1(", ");
		}
		sb.appendLine1("name: '" + this.name() + "'");
		sb.appendLine1(", type: '" + this.type() + "'");
		hasAdded = true;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "outerRingPath", this.outerRingPath(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "outerRingArrowPath", this.outerRingArrowPath(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "toolAreaPath", this.toolAreaPath(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "checkmarkPath", this.checkmarkPath(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "highlightPath", this.highlightPath(), !hasAdded) || hasAdded;
		return hasAdded;
	}
	,
	initializePath: function (part, path) {
		var data = new $.ig.PathVisualData(1, $.ig.RadialMenuWedgePart.prototype.$getName(part), path);
		switch (part) {
			case $.ig.RadialMenuWedgePart.prototype.outerRing:
				this.outerRingPath(data);
				break;
			case $.ig.RadialMenuWedgePart.prototype.outerRingArrow:
				this.outerRingArrowPath(data);
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolArea:
				this.toolAreaPath(data);
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolCheckmark:
				this.checkmarkPath(data);
				break;
			case $.ig.RadialMenuWedgePart.prototype.toolHighlight:
				this.highlightPath(data);
				break;
		}
	}
	,
	$type: new $.ig.Type('RadialMenuItemVisualData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuItemVisualDataList', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.RadialMenuItemVisualData.prototype.$type, 0);
	},
	$type: new $.ig.Type('RadialMenuItemVisualDataList', $.ig.List$1.prototype.$type.specialize($.ig.RadialMenuItemVisualData.prototype.$type))
}, true);

$.ig.util.defType('RadialMenuItemToolTipVisualData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_itemType: null,
	itemType: function (value) {
		if (arguments.length === 1) {
			this._itemType = value;
			return value;
		} else {
			return this._itemType;
		}
	}
	,
	_itemName: null,
	itemName: function (value) {
		if (arguments.length === 1) {
			this._itemName = value;
			return value;
		} else {
			return this._itemName;
		}
	}
	,
	_toolTipText: null,
	toolTipText: function (value) {
		if (arguments.length === 1) {
			this._toolTipText = value;
			return value;
		} else {
			return this._toolTipText;
		}
	}
	,
	_relativeTop: 0,
	relativeTop: function (value) {
		if (arguments.length === 1) {
			this._relativeTop = value;
			return value;
		} else {
			return this._relativeTop;
		}
	}
	,
	_relativeLeft: 0,
	relativeLeft: function (value) {
		if (arguments.length === 1) {
			this._relativeLeft = value;
			return value;
		} else {
			return this._relativeLeft;
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
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("itemName: '" + this.itemName() + "'");
		sb.appendLine1(", itemType: '" + this.itemType() + "'");
		sb.appendLine1(", toolTipText: '" + this.toolTipText() + "'");
		sb.appendLine1(", relativeTop: " + this.relativeTop());
		sb.appendLine1(", relativeLeft: " + this.relativeLeft());
		sb.appendLine1(", width: " + this.width());
		sb.appendLine1(", height: " + this.height());
		sb.appendLine1("}");
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('RadialMenuItemToolTipVisualData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuNumericGaugeVisualData', 'RadialMenuItemVisualData', {
	init: function () {
		$.ig.RadialMenuItemVisualData.prototype.init.call(this);
	},
	_tickmarks: null,
	tickmarks: function (value) {
		if (arguments.length === 1) {
			this._tickmarks = value;
			return value;
		} else {
			return this._tickmarks;
		}
	}
	,
	_valueNeedle: null,
	valueNeedle: function (value) {
		if (arguments.length === 1) {
			this._valueNeedle = value;
			return value;
		} else {
			return this._valueNeedle;
		}
	}
	,
	_editValueNeedle: null,
	editValueNeedle: function (value) {
		if (arguments.length === 1) {
			this._editValueNeedle = value;
			return value;
		} else {
			return this._editValueNeedle;
		}
	}
	,
	_valueTickLine: null,
	valueTickLine: function (value) {
		if (arguments.length === 1) {
			this._valueTickLine = value;
			return value;
		} else {
			return this._valueTickLine;
		}
	}
	,
	_trackArcs: null,
	trackArcs: function (value) {
		if (arguments.length === 1) {
			this._trackArcs = value;
			return value;
		} else {
			return this._trackArcs;
		}
	}
	,
	serializeImpl: function (sb, hasAdded) {
		hasAdded = $.ig.RadialMenuItemVisualData.prototype.serializeImpl.call(this, sb, hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "valueNeedle", this.valueNeedle(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "valueTickLine", this.valueTickLine(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItem(sb, "editValueNeedle", this.editValueNeedle(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItems(sb, "tickmarks", this.tickmarks(), !hasAdded) || hasAdded;
		hasAdded = $.ig.AppearanceHelper.prototype.serializeItems(sb, "trackArcs", this.trackArcs(), !hasAdded) || hasAdded;
		return hasAdded;
	}
	,
	$type: new $.ig.Type('RadialMenuNumericGaugeVisualData', $.ig.RadialMenuItemVisualData.prototype.$type)
}, true);

$.ig.util.defType('RadialMenuNumericGaugeTickmarkData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_tickPath: null,
	tickPath: function (value) {
		if (arguments.length === 1) {
			this._tickPath = value;
			return value;
		} else {
			return this._tickPath;
		}
	}
	,
	_label: null,
	label: function (value) {
		if (arguments.length === 1) {
			this._label = value;
			return value;
		} else {
			return this._label;
		}
	}
	,
	_labelLeft: 0,
	labelLeft: function (value) {
		if (arguments.length === 1) {
			this._labelLeft = value;
			return value;
		} else {
			return this._labelLeft;
		}
	}
	,
	_labelTop: 0,
	labelTop: function (value) {
		if (arguments.length === 1) {
			this._labelTop = value;
			return value;
		} else {
			return this._labelTop;
		}
	}
	,
	_labelWidth: 0,
	labelWidth: function (value) {
		if (arguments.length === 1) {
			this._labelWidth = value;
			return value;
		} else {
			return this._labelWidth;
		}
	}
	,
	_labelHeight: 0,
	labelHeight: function (value) {
		if (arguments.length === 1) {
			this._labelHeight = value;
			return value;
		} else {
			return this._labelHeight;
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("labelLeft: " + this.labelLeft());
		sb.appendLine1(", labelTop: " + this.labelTop());
		sb.appendLine1(", labelWidth: " + this.labelWidth());
		sb.appendLine1(", labelHeight: " + this.labelHeight());
		$.ig.AppearanceHelper.prototype.serializeItem(sb, "tickPath", this.tickPath(), false);
		$.ig.AppearanceHelper.prototype.serializeItem(sb, "label", this.label(), false);
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('RadialMenuNumericGaugeTickmarkData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('RadialMenuNumericGaugeTickmarkDataList', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.RadialMenuNumericGaugeTickmarkData.prototype.$type, 0);
	},
	$type: new $.ig.Type('RadialMenuNumericGaugeTickmarkDataList', $.ig.List$1.prototype.$type.specialize($.ig.RadialMenuNumericGaugeTickmarkData.prototype.$type))
}, true);

$.ig.util.defType('RadialMenuNumericGaugeNeedleData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_needlePath: null,
	needlePath: function (value) {
		if (arguments.length === 1) {
			this._needlePath = value;
			return value;
		} else {
			return this._needlePath;
		}
	}
	,
	_label: null,
	label: function (value) {
		if (arguments.length === 1) {
			this._label = value;
			return value;
		} else {
			return this._label;
		}
	}
	,
	serialize: function () {
		if (this.label() == null) {
			if (this.needlePath() == null) {
				return "{}";
			}
			return "{ needlePath: " + this.needlePath().serialize() + "}";
		} else if (this.needlePath() == null) {
			return "{ label: " + this.label().serialize() + "}";
		} else {
			return "{ needlePath: " + this.needlePath().serialize() + ", label: " + this.label().serialize() + "}";
		}
	}
	,
	$type: new $.ig.Type('RadialMenuNumericGaugeNeedleData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('ColorWellPreviewData', 'Object', {
	init: function () {
		this._colorValue = new $.ig.Color();
		$.ig.Object.prototype.init.call(this);
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
	_type: null,
	type: function (value) {
		if (arguments.length === 1) {
			this._type = value;
			return value;
		} else {
			return this._type;
		}
	}
	,
	_colorValue: null,
	colorValue: function (value) {
		if (arguments.length === 1) {
			this._colorValue = value;
			return value;
		} else {
			return this._colorValue;
		}
	}
	,
	_previewPath: null,
	previewPath: function (value) {
		if (arguments.length === 1) {
			this._previewPath = value;
			return value;
		} else {
			return this._previewPath;
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		sb.appendLine1("{");
		sb.appendLine1("name: '" + this.name() + "'");
		sb.appendLine1(", type: '" + this.type() + "'");
		sb.appendLine1(", colorValue: " + ($.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.colorValue()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) ? $.ig.AppearanceHelper.prototype.serializeColor(this.colorValue()) : "null"));
		$.ig.AppearanceHelper.prototype.serializeItem(sb, "previewPath", this.previewPath(), false);
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('ColorWellPreviewData', $.ig.Object.prototype.$type, [$.ig.IVisualData.prototype.$type])
}, true);

$.ig.util.defType('ColorWellPreviewDataList', 'List$1', {
	init: function () {
		$.ig.List$1.prototype.init.call(this, $.ig.ColorWellPreviewData.prototype.$type, 0);
	},
	$type: new $.ig.Type('ColorWellPreviewDataList', $.ig.List$1.prototype.$type.specialize($.ig.ColorWellPreviewData.prototype.$type))
}, true);

$.ig.util.defType('KeyTipInfo', 'Object', {
	init: function () {
		this._autoGeneratePrefix = null;
		this._defaultPrefix = '\0';
		$.ig.Object.prototype.init.call(this);
	},
	_autoGeneratePrefix: null,
	_defaultPrefix: '\0',
	_keyTip: null,
	_calculatedKeyTip: null,
	_caption: null,
	caption: function (value) {
		if (arguments.length === 1) {
			this._caption = value;
			return value;
		} else {
			return this._caption;
		}
	}
	,
	_action: null,
	action: function (value) {
		if (arguments.length === 1) {
			this._action = value;
			return value;
		} else {
			return this._action;
		}
	}
	,
	_tag: null,
	tag: function (value) {
		if (arguments.length === 1) {
			this._tag = value;
			return value;
		} else {
			return this._tag;
		}
	}
	,
	_positionCalculator: null,
	positionCalculator: function (value) {
		if (arguments.length === 1) {
			this._positionCalculator = value;
			return value;
		} else {
			return this._positionCalculator;
		}
	}
	,
	$type: new $.ig.Type('KeyTipInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KeyTipManager', 'Object', {
	__currentKeyTipValue: null,
	__element: null,
	__keyTips: null,
	__culture: null,
	init: function (element) {
		$.ig.Object.prototype.init.call(this);
		this.__element = element;
		this.__keyTips = new $.ig.List$1($.ig.KeyTipInfo.prototype.$type, 0);
	},
	hasKeyTips: function () {
		return this.__keyTips.count() > 0;
	}
	,
	keyTips: function () {
		return this.__keyTips;
	}
	,
	processKeyPress: function (text) {
		var foundAnyMatch = false;
		for (var i = 0; i < text.length; i++) {
			var ch = text.charAt(i);
			if (!$.ig.KeyTipManager.prototype.isValidKeyTipChar(ch)) {
				continue;
			}
			var newKeyTip = this.__currentKeyTipValue + ch;
			var newKeyTipValue = newKeyTip.toLocaleUpperCase(this.__culture);
			var foundMatch = false;
			var en = this.__keyTips.getEnumerator();
			while (en.moveNext()) {
				var keytip = en.current();
				if (newKeyTipValue == keytip._calculatedKeyTip) {
					this.__currentKeyTipValue = null;
					keytip.action()(keytip);
					return true;
				} else if (keytip._calculatedKeyTip.startsWith(newKeyTipValue)) {
					foundAnyMatch = foundMatch = true;
					break;
				}
			}
			if (foundMatch) {
				this.__currentKeyTipValue = newKeyTip;
			}
		}
		return foundAnyMatch;
	}
	,
	prepareKeyTips: function () {
		this.initializeCulture();
		var keyTips = new $.ig.List$1($.ig.KeyTipInfo.prototype.$type, 0);
		var en = this.__keyTips.getEnumerator();
		while (en.moveNext()) {
			var keyTip = en.current();
			if (String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				keyTips.add(keyTip);
			}
		}
		this.initializeKeyTipsUsingKeyTips(keyTips);
		this.initializeKeyTipsUsingCaptions(keyTips);
		this.initializeKeyTipsUsingIndex(keyTips);
		this.initializeRemainingKeyTips(keyTips);
		this.removeInvalidKeyTips();
		this.resolveConflicts();
	}
	,
	reset: function () {
		this.__keyTips.clear();
		this.__currentKeyTipValue = null;
	}
	,
	compareKeyTip: function (k1, k2) {
		return k1._calculatedKeyTip.compareTo(k2._calculatedKeyTip);
	}
	,
	getKeyTipAtIndex: function (index) {
		$.ig.Debug.prototype.assert1(index >= 0, "The index is invalid");
		if (index < 0) {
			return null;
		}
		var indexResolved = index;
		if (indexResolved <= 9) {
			return $.ig.util.numberToString(indexResolved, this.__culture);
		}
		var maxNumeric = 19;
		if (indexResolved < maxNumeric) {
			return "0" + $.ig.util.numberToString((maxNumeric - indexResolved), this.__culture);
		}
		indexResolved -= maxNumeric;
		if (indexResolved > 25) {
			return null;
		}
		var appendLetter = String.fromCharCode('A'.charCodeAt(0) + indexResolved);
		return "0" + appendLetter;
	}
	,
	getValidAutoGeneratePrefix: function (keyTip) {
		var autoGeneratePrefix = keyTip._autoGeneratePrefix;
		var validPrefix = String.empty();
		if (String.isNullOrEmpty(autoGeneratePrefix)) {
			return validPrefix;
		}
		for (var i = 0; i < autoGeneratePrefix.length; i++) {
			var c = autoGeneratePrefix.charAt(i);
			if (validPrefix.length == 2) {
				break;
			}
			if ($.ig.KeyTipManager.prototype.isValidKeyTipChar(c)) {
				validPrefix += c;
			}
		}
		return validPrefix;
	}
	,
	initializeCulture: function () {
		var culture = $.ig.CultureInfo.prototype.currentCulture();
		this.__culture = culture;
	}
	,
	initializeKeyTipsUsingCaptions: function (keytips) {
		for (var i = 0; i < keytips.count(); i++) {
			var keyTip = keytips.__inner[i];
			var caption = keyTip.caption();
			if (String.isNullOrEmpty(caption)) {
				continue;
			}
			var prefix = this.getValidAutoGeneratePrefix(keyTip);
			var firstValidKeyTip = String.empty();
			for (var j = 0, count = caption.length; j < count; j++) {
				var currentChar = caption.charAt(j);
				if ($.ig.KeyTipManager.prototype.isValidKeyTipChar(currentChar) == false) {
					continue;
				}
				var desiredValue = (prefix + currentChar).toLocaleUpperCase(this.__culture);
				if (firstValidKeyTip.length == 0) {
					firstValidKeyTip = desiredValue;
				}
				var alreadyUsed = false;
				var en = this.__keyTips.getEnumerator();
				while (en.moveNext()) {
					var existingKeyTip = en.current();
					if (existingKeyTip._calculatedKeyTip == null) {
						continue;
					}
					if (existingKeyTip._calculatedKeyTip.startsWith(desiredValue)) {
						alreadyUsed = true;
						break;
					}
				}
				if (alreadyUsed == false) {
					keyTip._calculatedKeyTip = desiredValue;
					break;
				}
			}
			if (String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				if (firstValidKeyTip.length == 0) {
					keyTip._calculatedKeyTip = prefix;
				} else {
					keyTip._calculatedKeyTip = firstValidKeyTip;
				}
			}
			if (!String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				keytips.removeAt(i);
				i--;
			}
		}
	}
	,
	initializeKeyTipsUsingKeyTips: function (keytips) {
		for (var i = keytips.count() - 1; i >= 0; i--) {
			var keyTip = keytips.__inner[i];
			if (!String.isNullOrEmpty(keyTip._keyTip)) {
				keyTip._calculatedKeyTip = keyTip._keyTip;
				keytips.removeAt(i);
			}
		}
	}
	,
	initializeKeyTipsUsingIndex: function (keyTips) {
		var nextOpenIndex = 1;
		for (var i = 0; i < keyTips.count(); i++) {
			var temp = null;
			do {
				temp = this.getKeyTipAtIndex(nextOpenIndex++);
				if (temp == null) {
					return;
				}
				var en = this.__keyTips.getEnumerator();
				while (en.moveNext()) {
					var k = en.current();
					if (k._calculatedKeyTip == temp) {
						temp = null;
						break;
					}
				}
				if (temp != null) {
					keyTips.__inner[i]._calculatedKeyTip = temp;
					keyTips.removeAt(i);
					i--;
					break;
				}
			} while (true);
		}
	}
	,
	initializeRemainingKeyTips: function (keyTips) {
		for (var i = 0; i < keyTips.count(); i++) {
			var keyTip = keyTips.__inner[i];
			keyTip._calculatedKeyTip = this.getValidAutoGeneratePrefix(keyTip);
			if (String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				var prefixChar = keyTip._defaultPrefix;
				if ($.ig.KeyTipManager.prototype.isValidKeyTipChar(prefixChar)) {
					keyTip._calculatedKeyTip = prefixChar;
				}
			}
			if (!String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				keyTips.removeAt(i);
				i--;
			}
		}
	}
	,
	isValidKeyTipChar: function (c) {
		return true;
	}
	,
	removeInvalidKeyTips: function () {
		for (var i = this.__keyTips.count() - 1; i >= 0; i--) {
			var keyTip = this.__keyTips.__inner[i];
			if (String.isNullOrEmpty(keyTip._calculatedKeyTip)) {
				this.__keyTips.removeAt(i);
			}
		}
	}
	,
	resolveConflicts: function () {
		var keyTipsToSort = new $.ig.List$1($.ig.KeyTipInfo.prototype.$type, 1, this.__keyTips);
		var beginSearchAt = 0;
		var conflictOccurred;
		do {
			conflictOccurred = false;
			keyTipsToSort.sort2(this.compareKeyTip.runOn(this));
			var conflictKeyTips = null;
			for (var i = beginSearchAt; i < keyTipsToSort.count() - 1; i++) {
				var firstKeyTip = keyTipsToSort.__inner[i];
				var secondKeyTip = keyTipsToSort.__inner[i + 1];
				if (firstKeyTip._calculatedKeyTip == secondKeyTip._calculatedKeyTip) {
					if (conflictKeyTips == null) {
						conflictKeyTips = new $.ig.List$1($.ig.KeyTipInfo.prototype.$type, 0);
					} else {
						conflictKeyTips.clear();
					}
					conflictKeyTips.add(firstKeyTip);
					conflictKeyTips.add(secondKeyTip);
					for (i += 2; i < keyTipsToSort.count(); i++) {
						var keyTip = keyTipsToSort.__inner[i];
						if (keyTip._calculatedKeyTip != firstKeyTip._calculatedKeyTip) {
							break;
						}
						conflictKeyTips.add(keyTip);
					}
					var formatDigits = $.ig.truncate(Math.floor(Math.log10(conflictKeyTips.count() - 1))) + 1;
					for (var conflictIndex = 0; conflictIndex < conflictKeyTips.count(); conflictIndex++) {
						var keyTip1 = conflictKeyTips.__inner[conflictIndex];
						var uniqueSuffix = $.ig.util.numberToString((conflictIndex + 1), this.__culture).padLeft(formatDigits, '0');
						if (uniqueSuffix.length > formatDigits) {
							uniqueSuffix = uniqueSuffix.substr(uniqueSuffix.length - formatDigits);
						}
						keyTip1._calculatedKeyTip = String.concat(keyTip1._calculatedKeyTip, uniqueSuffix);
					}
					conflictOccurred = true;
					break;
				} else if (secondKeyTip._calculatedKeyTip.startsWith(firstKeyTip._calculatedKeyTip)) {
					firstKeyTip._calculatedKeyTip = String.concat(firstKeyTip._calculatedKeyTip, firstKeyTip._calculatedKeyTip == "0" ? "0" : "1");
					conflictOccurred = true;
					break;
				} else {
					beginSearchAt = i + 1;
				}
			}
		} while (conflictOccurred);
	}
	,
	$type: new $.ig.Type('KeyTipManager', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LiteRect', 'ValueType', {
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
	x: 0,
	y: 0,
	width: 0,
	height: 0,
	init1: function (initNumber, x, y, width, height) {
		$.ig.ValueType.prototype.init.call(this);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	},
	$type: new $.ig.Type('LiteRect', $.ig.ValueType.prototype.$type)
}, true);

$.ig.util.defType('LiteRectExtensions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	toRect: function (rect) {
		if ($.ig.LiteRectExtensions.prototype.isEmpty(rect)) {
			return $.ig.Rect.prototype.empty();
		}
		return new $.ig.Rect(0, rect.x, rect.y, rect.width, rect.height);
	}
	,
	isEqual: function (rect, other) {
		return rect.x == other.x && rect.y == other.y && rect.width == other.width && rect.height == other.height;
	}
	,
	isEmpty: function (rect) {
		return rect.width < 0;
	}
	,
	$type: new $.ig.Type('LiteRectExtensions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PathBuilder', 'Object', {
	__geometry: null,
	__figure: null,
	__segments: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__geometry = new $.ig.PathGeometry();
	},
	addArc: function (rect, startRadians, sweepRadians) {
		if ($.ig.LiteRectExtensions.prototype.isEmpty(rect)) {
			return;
		}
		var sweepDir = sweepRadians < 0 ? $.ig.SweepDirection.prototype.counterclockwise : $.ig.SweepDirection.prototype.clockwise;
		var isLargeArc = Math.abs(sweepRadians) > Math.PI;
		var endRadians = startRadians + sweepRadians;
		var cx = rect.width / 2;
		var cy = rect.height / 2;
		var r1 = (cx * cy) / Math.sqrt($.ig.PathBuilder.prototype.square(cy * Math.cos(startRadians)) + $.ig.PathBuilder.prototype.square(cx * Math.sin(startRadians)));
		var x1 = rect.x + r1 * Math.cos(startRadians) + cx;
		var y1 = rect.y + r1 * Math.sin(startRadians) + cy;
		var r = (cx * cy) / Math.sqrt($.ig.PathBuilder.prototype.square(cy * Math.cos(endRadians)) + $.ig.PathBuilder.prototype.square(cx * Math.sin(endRadians)));
		var x2 = rect.x + r * Math.cos(endRadians) + cx;
		var y2 = rect.y + r * Math.sin(endRadians) + cy;
		var pt0 = $.ig.ShapeUtilities.prototype.getPointOnEllipse(rect, startRadians);
		var pt1 = $.ig.ShapeUtilities.prototype.getPointOnEllipse(rect, endRadians);
		x1 = pt0.__x;
		y1 = pt0.__y;
		x2 = pt1.__x;
		y2 = pt1.__y;
		this.initialize({ __x: x1, __y: y1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, false);
		this.__segments.add((function () {
			var $ret = new $.ig.ArcSegment();
			$ret.point({ __x: x2, __y: y2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			$ret.size(new $.ig.Size(1, cx, cy));
			$ret.rotationAngle(0);
			$ret.isLargeArc(isLargeArc);
			$ret.sweepDirection(sweepDir);
			return $ret;
		}()));
	}
	,
	addEllipse: function (centerX, centerY, radiusX, radiusY, reverse) {
		var points = $.ig.ShapeUtilities.prototype.getEllipseBezierPoints(centerX, centerY, radiusX, radiusY);
		var pointCount = points.length;
		if (reverse) {
			for (var i = 0; i < $.ig.intDivide(pointCount, 2); i++) {
				var temp = points[pointCount - i - 1];
				points[pointCount - i - 1] = points[i];
				points[i] = temp;
			}
		}
		this.initialize(points[0], true);
		var pbs = new $.ig.PolyBezierSegment();
		for (var i1 = 1; i1 < pointCount; i1++) {
			pbs.points().add(points[i1]);
		}
		this.__segments.add(pbs);
	}
	,
	addLine: function (pt1, pt2) {
		this.initialize(pt1, false);
		this.__segments.add((function () {
			var $ret = new $.ig.LineSegment(1);
			$ret.point(pt2);
			return $ret;
		}()));
	}
	,
	geometry: function () {
		return this.__geometry;
	}
	,
	initialize: function (startPoint, forceMove) {
		if (this.__figure == null || forceMove) {
			this.__figure = new $.ig.PathFigure();
			this.__segments = this.__figure.__segments;
			this.__figure.__startPoint = startPoint;
			this.__geometry.figures().add(this.__figure);
		}
	}
	,
	square: function (value) {
		return value * value;
	}
	,
	$type: new $.ig.Type('PathBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ShapeDefinition', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_context: null,
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
	_stroke: null,
	stroke: function (value) {
		if (arguments.length === 1) {
			this._stroke = value;
			return value;
		} else {
			return this._stroke;
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
	_transform: null,
	transform: function (value) {
		if (arguments.length === 1) {
			this._transform = value;
			return value;
		} else {
			return this._transform;
		}
	}
	,
	hitTest: function (point) {
	}
	,
	createGeometry: function (strokeThickness) {
	}
	,
	$type: new $.ig.Type('ShapeDefinition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ellipse', 'ShapeDefinition', {
	init: function () {
		this._center = new $.ig.Point(0);
		$.ig.ShapeDefinition.prototype.init.call(this);
	},
	_center: null,
	_radiusX: 0,
	_radiusY: 0,
	hitTest: function (pt) {
		return $.ig.ShapeUtilities.prototype.lessThanOrClose($.ig.ShapeUtilities.prototype.hitTestEllipse(this._center, this._radiusX, this._radiusY, pt), 0);
	}
	,
	createGeometry: function (strokeThickness) {
		var pb = new $.ig.PathBuilder();
		var halfPenWidth = strokeThickness / 2;
		pb.addEllipse(this._center.__x, this._center.__y, this._radiusX - halfPenWidth, this._radiusY - halfPenWidth, false);
		return pb.geometry();
	}
	,
	$type: new $.ig.Type('Ellipse', $.ig.ShapeDefinition.prototype.$type)
}, true);

$.ig.util.defType('Annulus', 'Ellipse', {
	init: function () {
		$.ig.Ellipse.prototype.init.call(this);
	},
	_innerRadiusX: 0,
	_innerRadiusY: 0,
	hitTest: function (pt) {
		if (!$.ig.Ellipse.prototype.hitTest.call(this, pt)) {
			return false;
		}
		return $.ig.ShapeUtilities.prototype.greaterThanOrClose($.ig.ShapeUtilities.prototype.hitTestEllipse(this._center, this._innerRadiusX, this._innerRadiusY, pt), 0);
	}
	,
	createGeometry: function (strokeThickness) {
		var pb = new $.ig.PathBuilder();
		var halfPenWidth = strokeThickness / 2;
		pb.addEllipse(this._center.__x, this._center.__y, this._radiusX - halfPenWidth, this._radiusY - halfPenWidth, false);
		pb.addEllipse(this._center.__x, this._center.__y, this._innerRadiusX + halfPenWidth, this._innerRadiusY + halfPenWidth, true);
		return pb.geometry();
	}
	,
	$type: new $.ig.Type('Annulus', $.ig.Ellipse.prototype.$type)
}, true);

$.ig.util.defType('TriangleShape', 'ShapeDefinition', {
	init: function () {
		this._point1 = new $.ig.Point(0);
		this._point2 = new $.ig.Point(0);
		this._point3 = new $.ig.Point(0);
		$.ig.ShapeDefinition.prototype.init.call(this);
	},
	_point1: null,
	_point2: null,
	_point3: null,
	hitTest: function (point) {
		return $.ig.ShapeUtilities.prototype.hitTestTriangle(this._point1, this._point2, this._point3, point) <= 0;
	}
	,
	createGeometry: function (strokeThickness) {
		var $self = this;
		var geo = new $.ig.PathGeometry();
		var f = new $.ig.PathFigure();
		f.__startPoint = this._point1;
		f.__isClosed = true;
		f.__segments.add((function () {
			var $ret = new $.ig.LineSegment(1);
			$ret.point($self._point2);
			return $ret;
		}()));
		f.__segments.add((function () {
			var $ret = new $.ig.LineSegment(1);
			$ret.point($self._point3);
			return $ret;
		}()));
		geo.figures().add(f);
		return geo;
	}
	,
	$type: new $.ig.Type('TriangleShape', $.ig.ShapeDefinition.prototype.$type)
}, true);

$.ig.util.defType('AnnularSector', 'Annulus', {
	init: function () {
		$.ig.Annulus.prototype.init.call(this);
	},
	_startAngle: 0,
	_sweepAngle: 0,
	_innerStartAngle: 0,
	_innerSweepAngle: 0,
	hitTest: function (pt) {
		if (!$.ig.Annulus.prototype.hitTest.call(this, pt)) {
			return false;
		}
		return $.ig.ShapeUtilities.prototype.greaterThanOrClose($.ig.ShapeUtilities.prototype.hitTestEllipse(this._center, this._innerRadiusX, this._innerRadiusY, pt), 0) && $.ig.ShapeUtilities.prototype.isWithinWedgeAngle(this._innerStartAngle, this._innerSweepAngle, this._center, this._innerRadiusX, this._innerRadiusY, pt);
	}
	,
	createGeometry: function (strokeThickness) {
		if ($.ig.ShapeUtilities.prototype.greaterThanOrClose(this._sweepAngle, $.ig.ShapeUtilities.prototype.twoPI)) {
			return $.ig.Annulus.prototype.createGeometry.call(this, strokeThickness);
		}
		var pb = new $.ig.PathBuilder();
		var halfPenWidth = strokeThickness / 2;
		var outerRect = new $.ig.LiteRect(1, this._center.__x - this._radiusX, this._center.__y - this._radiusY, this._radiusX * 2, this._radiusY * 2);
		var innerRect = new $.ig.LiteRect(1, this._center.__x - this._innerRadiusX, this._center.__y - this._innerRadiusY, this._innerRadiusX * 2, this._innerRadiusY * 2);
		var outerSweep = this._sweepAngle;
		var innerSweep = this._innerSweepAngle;
		pb.addArc(outerRect, this._startAngle, outerSweep);
		pb.addLine($.ig.ShapeUtilities.prototype.getPointOnEllipse(outerRect, this._startAngle + outerSweep), $.ig.ShapeUtilities.prototype.getPointOnEllipse(innerRect, this._innerStartAngle));
		pb.addArc(innerRect, this._innerStartAngle, innerSweep);
		pb.addLine($.ig.ShapeUtilities.prototype.getPointOnEllipse(innerRect, this._innerStartAngle + innerSweep), $.ig.ShapeUtilities.prototype.getPointOnEllipse(outerRect, this._startAngle));
		return pb.geometry();
	}
	,
	$type: new $.ig.Type('AnnularSector', $.ig.Annulus.prototype.$type)
}, true);

$.ig.util.defType('LineSegmentShape', 'ShapeDefinition', {
	init: function () {
		this._start = new $.ig.Point(0);
		this._end = new $.ig.Point(0);
		$.ig.ShapeDefinition.prototype.init.call(this);
	},
	_start: null,
	_end: null,
	createGeometry: function (strokeThickness) {
		var $self = this;
		if (strokeThickness == 0) {
			return null;
		}
		return (function () {
			var $ret = new $.ig.LineGeometry();
			$ret.startPoint($self._start);
			$ret.endPoint($self._end);
			return $ret;
		}());
	}
	,
	hitTest: function (pt) {
		var halfWidth = this.strokeThickness() == 0 ? 0 : this.strokeThickness() / 2;
		var dbl = function (d) { return d * d; };
		var ab = Math.sqrt(dbl(this._start.__x - this._end.__x) + dbl(this._start.__y - this._end.__y));
		var ac = Math.sqrt(dbl(this._start.__x - pt.__x) + dbl(this._start.__y - pt.__y));
		var bc = Math.sqrt(dbl(this._end.__x - pt.__x) + dbl(this._end.__y - pt.__y));
		var delta = Math.abs(ac + bc - ab);
		return delta < halfWidth;
	}
	,
	$type: new $.ig.Type('LineSegmentShape', $.ig.ShapeDefinition.prototype.$type)
}, true);

$.ig.util.defType('ShapeUtilities', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	areClose: function (value1, value2) {
		if (value1 == value2) {
			return true;
		}
		return Math.abs(value1 - value2) < 1E-06;
	}
	,
	createAnnularSector: function (menuRect, outerRadiusX, outerRadiusY, innerRadiusX, innerRadiusY, startRadians, endRadians) {
		if (outerRadiusX < 0) {
			outerRadiusX = 0;
		}
		if (outerRadiusY < 0) {
			outerRadiusY = 0;
		}
		if (innerRadiusX < 0) {
			innerRadiusX = 0;
		}
		if (innerRadiusY < 0) {
			innerRadiusY = 0;
		}
		var toolArea = new $.ig.AnnularSector();
		var x0 = menuRect.x + menuRect.width / 2;
		var y0 = menuRect.y + menuRect.height / 2;
		toolArea._center = { __x: x0, __y: y0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		toolArea._radiusX = outerRadiusX;
		toolArea._radiusY = outerRadiusY;
		toolArea._innerRadiusX = innerRadiusX;
		toolArea._innerRadiusY = innerRadiusY;
		var outerRect = new $.ig.LiteRect(1, x0 - outerRadiusX, y0 - outerRadiusY, outerRadiusX * 2, outerRadiusY * 2);
		toolArea._startAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, startRadians, outerRect);
		toolArea._sweepAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, endRadians, outerRect) - toolArea._startAngle;
		var innerRect = new $.ig.LiteRect(1, x0 - innerRadiusX, y0 - innerRadiusY, innerRadiusX * 2, innerRadiusY * 2);
		toolArea._innerStartAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, endRadians, innerRect);
		toolArea._innerSweepAngle = $.ig.ShapeUtilities.prototype.translateAngle(menuRect, startRadians, innerRect) - toolArea._innerStartAngle;
		if (endRadians > startRadians && $.ig.ShapeUtilities.prototype.lessThanOrClose(toolArea._sweepAngle, 0)) {
			toolArea._sweepAngle = toolArea._sweepAngle + $.ig.ShapeUtilities.prototype.twoPI;
			toolArea._innerSweepAngle = toolArea._innerSweepAngle - $.ig.ShapeUtilities.prototype.twoPI;
		}
		return toolArea;
	}
	,
	getBoundingEllipse: function (rect) {
		var a = rect.width / 2 * $.ig.ShapeUtilities.prototype.squareOf2;
		var b = rect.height / 2 * $.ig.ShapeUtilities.prototype.squareOf2;
		var x0 = rect.x + rect.width / 2;
		var y0 = rect.y + rect.height / 2;
		return new $.ig.LiteRect(1, x0 - a, y0 - b, a * 2, b * 2);
	}
	,
	getEllipseBezierPoints: function (x, y, radiusX, radiusY) {
		var kappa = 0.55228474983079356;
		var hOffset = radiusX * kappa;
		var yOffset = radiusY * kappa;
		return [ { __x: x, __y: y - radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x + hOffset, __y: y - radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x + radiusX, __y: y - yOffset, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x + radiusX, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x + radiusX, __y: y + yOffset, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x + hOffset, __y: y + radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x, __y: y + radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x - hOffset, __y: y + radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x - radiusX, __y: y + yOffset, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x - radiusX, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x - radiusX, __y: y - yOffset, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x - hOffset, __y: y - radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: x, __y: y - radiusY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName } ];
	}
	,
	getPointOnEllipse: function (ellipseRect, angleInRadians) {
		var cx = ellipseRect.width / 2;
		var cy = ellipseRect.height / 2;
		var x = cx * Math.cos(angleInRadians);
		var y = cy * Math.sin(angleInRadians);
		return { __x: x + cx + ellipseRect.x, __y: y + cy + ellipseRect.y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	getRadianDelta: function (radians1, radians2) {
		radians1 = $.ig.ShapeUtilities.prototype.normalizeRadians(radians1);
		radians2 = $.ig.ShapeUtilities.prototype.normalizeRadians(radians2);
		var delta = Math.abs(radians1 - radians2);
		return Math.min($.ig.ShapeUtilities.prototype.twoPI - delta, delta);
	}
	,
	greaterThanOrClose: function (x, y) {
		return x >= y || $.ig.ShapeUtilities.prototype.areClose(x, y);
	}
	,
	hitTestEllipse: function (center, radiusX, radiusY, hitPoint) {
		var x = hitPoint.__x - center.__x;
		var y = hitPoint.__y - center.__y;
		var delta = (x * x) / (radiusX * radiusX) + (y * y) / (radiusY * radiusY);
		return delta - 1;
	}
	,
	hitTestTriangle: function (pt1, pt2, pt3, hitPt) {
		var pt0 = hitPt;
		var b0 = (pt2.__x - pt1.__x) * (pt3.__y - pt1.__y) - (pt3.__x - pt1.__x) * (pt2.__y - pt1.__y);
		var b1 = Math.sign(((pt2.__x - pt0.__x) * (pt3.__y - pt0.__y) - (pt3.__x - pt0.__x) * (pt2.__y - pt0.__y)) / b0);
		var b2 = Math.sign(((pt3.__x - pt0.__x) * (pt1.__y - pt0.__y) - (pt1.__x - pt0.__x) * (pt3.__y - pt0.__y)) / b0);
		var b3 = Math.sign(((pt1.__x - pt0.__x) * (pt2.__y - pt0.__y) - (pt2.__x - pt0.__x) * (pt1.__y - pt0.__y)) / b0);
		if (b1 == b2 && b2 == b3) {
			return -1;
		}
		if (b1 == 0 || b2 == 0 || b3 == 0) {
			return 0;
		}
		return 1;
	}
	,
	inflateSafe: function (rect, x, y) {
		if (!$.ig.LiteRectExtensions.prototype.isEmpty(rect)) {
			rect = new $.ig.LiteRect(1, rect.x, rect.y, rect.width, rect.height);
			rect.x -= x;
			rect.y -= y;
			rect.width = rect.width + x * 2;
			rect.height = rect.height + y * 2;
		}
		return rect;
	}
	,
	isWithinWedgeAngle: function (startRadians, sweepRadians, center, radiusX, radiusY, hitPoint) {
		var hitAngle = $.ig.ShapeUtilities.prototype.radiansFromPoint(center, radiusX, radiusY, hitPoint);
		hitAngle = $.ig.ShapeUtilities.prototype.normalizeRadians(hitAngle);
		var endRadians = startRadians + sweepRadians;
		if (endRadians < startRadians) {
			var temp = startRadians;
			startRadians = endRadians;
			endRadians = temp;
		}
		if (startRadians < -Math.PI) {
			return hitAngle < endRadians || hitAngle > startRadians + $.ig.ShapeUtilities.prototype.twoPI;
		} else if (endRadians > Math.PI) {
			return hitAngle > startRadians || hitAngle < endRadians + $.ig.ShapeUtilities.prototype.twoPI;
		}
		return startRadians <= hitAngle && hitAngle <= endRadians;
	}
	,
	lessThanOrClose: function (x, y) {
		return x <= y || $.ig.ShapeUtilities.prototype.areClose(x, y);
	}
	,
	normalizeRadians: function (radians) {
		radians %= $.ig.ShapeUtilities.prototype.twoPI;
		if (radians > Math.PI) {
			radians -= $.ig.ShapeUtilities.prototype.twoPI;
		} else if (radians < -Math.PI) {
			radians += $.ig.ShapeUtilities.prototype.twoPI;
		}
		return radians;
	}
	,
	radiansFromPoint: function (center, radiusX, radiusY, pt) {
		return Math.atan2((pt.__y - center.__y) * (radiusX / radiusY), pt.__x - center.__x);
	}
	,
	translateAngle: function (sourceEllipseRect, sourceEllipseAngle, targetEllipseRect) {
		var pt = $.ig.ShapeUtilities.prototype.getPointOnEllipse(sourceEllipseRect, sourceEllipseAngle);
		var cxInner = targetEllipseRect.width / 2;
		var cyInner = targetEllipseRect.height / 2;
		return $.ig.ShapeUtilities.prototype.radiansFromPoint({ __x: targetEllipseRect.x + cxInner, __y: targetEllipseRect.y + cyInner, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, cxInner, cyInner, pt);
	}
	,
	$type: new $.ig.Type('ShapeUtilities', $.ig.Object.prototype.$type)
}, true);

$.ig.RadialMenuCheckBehavior.prototype.none = 0;
$.ig.RadialMenuCheckBehavior.prototype.checkBox = 1;
$.ig.RadialMenuCheckBehavior.prototype.radioButton = 2;
$.ig.RadialMenuCheckBehavior.prototype.radioButtonAllowAllUp = 3;

$.ig.RadialMenuChildItemPlacement.prototype.asChildren = 0;
$.ig.RadialMenuChildItemPlacement.prototype.asSiblingsWhenChecked = 1;
$.ig.RadialMenuChildItemPlacement.prototype.none = 2;

$.ig.OuterRingButtonType.prototype.none = 0;
$.ig.OuterRingButtonType.prototype.toolAreaClick = 1;
$.ig.OuterRingButtonType.prototype.navigateToChildren = 2;

$.ig.RadialMenuAnimation.prototype.none = 0;
$.ig.RadialMenuAnimation.prototype.expandCollapse = 1;
$.ig.RadialMenuAnimation.prototype.navigateMenu = 2;
$.ig.RadialMenuAnimation.prototype.navigateToChildColor = 3;
$.ig.RadialMenuAnimation.prototype.navigateToParentColor = 4;

$.ig.RadialMenuPointerArea.prototype.none = 0;
$.ig.RadialMenuPointerArea.prototype.outerRingButton = 1;
$.ig.RadialMenuPointerArea.prototype.toolArea = 2;
$.ig.RadialMenuPointerArea.prototype.centerButton = 3;
$.ig.RadialMenuPointerArea.prototype.disabledMenu = 4;

$.ig.RadialMenuPart.prototype.menu = 0;
$.ig.RadialMenuPart.prototype.outerRing = 1;
$.ig.RadialMenuPart.prototype.innerRingFocus = 2;
$.ig.RadialMenuPart.prototype.innerRing = 3;

$.ig.RadialMenuWedgePart.prototype.outerRing = 0;
$.ig.RadialMenuWedgePart.prototype.toolArea = 1;
$.ig.RadialMenuWedgePart.prototype.toolCheckmark = 2;
$.ig.RadialMenuWedgePart.prototype.toolHighlight = 3;
$.ig.RadialMenuWedgePart.prototype.outerRingArrow = 4;
$.ig.RadialMenuWedgePart.prototype.colorWellPreview = 5;

$.ig.RadialMenuInvalidation.prototype.none = 0;
$.ig.RadialMenuInvalidation.prototype.invalidateCustomVisuals = 1;
$.ig.RadialMenuInvalidation.prototype.renderFrame = 2;
$.ig.RadialMenuInvalidation.prototype.prepareFrame = 3;

$.ig.RadialMenuUpdateVisualResult.prototype.noChange = 0;
$.ig.RadialMenuUpdateVisualResult.prototype.updateShapes = 1;
$.ig.RadialMenuUpdateVisualResult.prototype.updateTextElements = 2;

$.ig.CssProperty.prototype.backgroundColor = 0;
$.ig.CssProperty.prototype.borderTopColor = 1;
$.ig.CssProperty.prototype.borderTopStyle = 2;
$.ig.CssProperty.prototype.borderTopWidth = 3;
$.ig.CssProperty.prototype.borderTopLeftRadius = 4;
$.ig.CssProperty.prototype.paddingTop = 5;
$.ig.CssProperty.prototype.color = 6;

$.ig.RadialMenuItemBase.prototype.dirtyCustomVisualsPropertyName = "DirtyCustomVisuals";
$.ig.RadialMenuItemBase.prototype.invalidateCustomVisualsPropertyName = "InvalidateCustomVisuals";
$.ig.RadialMenuItemBase.prototype.autoRotateChildrenPropertyName = "AutoRotateChildren";
$.ig.RadialMenuItemBase.prototype.checkedHighlightBrushPropertyName = "CheckedHighlightBrush";
$.ig.RadialMenuItemBase.prototype.foregroundPropertyName = "Foreground";
$.ig.RadialMenuItemBase.prototype.highlightBrushPropertyName = "HighlightBrush";
$.ig.RadialMenuItemBase.prototype.innerAreaFillPropertyName = "InnerAreaFill";
$.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillPropertyName = "InnerAreaHotTrackFill";
$.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokePropertyName = "InnerAreaHotTrackStroke";
$.ig.RadialMenuItemBase.prototype.innerAreaStrokePropertyName = "InnerAreaStroke";
$.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessPropertyName = "InnerAreaStrokeThickness";
$.ig.RadialMenuItemBase.prototype.isEnabledPropertyName = "IsEnabled";
$.ig.RadialMenuItemBase.prototype.isToolTipEnabledPropertyName = "IsToolTipEnabled";
$.ig.RadialMenuItemBase.prototype.menuPropertyName = "Menu";
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillPropertyName = "OuterRingButtonHotTrackFill";
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundPropertyName = "OuterRingButtonHotTrackForeground";
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokePropertyName = "OuterRingButtonHotTrackStroke";
$.ig.RadialMenuItemBase.prototype.outerRingButtonFillPropertyName = "OuterRingButtonFill";
$.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundPropertyName = "OuterRingButtonForeground";
$.ig.RadialMenuItemBase.prototype.outerRingButtonStrokePropertyName = "OuterRingButtonStroke";
$.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessPropertyName = "OuterRingButtonStrokeThickness";
$.ig.RadialMenuItemBase.prototype.parentItemPropertyName = "ParentItem";
$.ig.RadialMenuItemBase.prototype.toolTipPropertyName = "ToolTip";
$.ig.RadialMenuItemBase.prototype.wedgeIndexPropertyName = "WedgeIndex";
$.ig.RadialMenuItemBase.prototype.wedgeSpanPropertyName = "WedgeSpan";
$.ig.RadialMenuItemBase.prototype.displayAsCheckedPropertyName = "DisplayAsChecked";
$.ig.RadialMenuItemBase.prototype.outerRingButtonTypePropertyName = "OuterRingButtonType";
$.ig.RadialMenuItemBase.prototype.toolTipResolvedPropertyName = "ToolTipResolved";
$.ig.RadialMenuItemBase.prototype.visibilityProxyPropertyName = "VisibilityProxy";
$.ig.RadialMenuItemBase.prototype.autoRotateChildrenProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.autoRotateChildrenPropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.autoRotateChildrenPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.checkedHighlightBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.checkedHighlightBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.foregroundProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.foregroundPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.RadialMenuItemBase.prototype.foregroundPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.highlightBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.highlightBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.highlightBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.innerAreaFillProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.innerAreaFillPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.innerAreaFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.innerAreaHotTrackStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.innerAreaStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.innerAreaStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.innerAreaStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessPropertyName, Number, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.innerAreaStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.isEnabledProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.isEnabledPropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.isEnabledPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.isToolTipEnabledProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.isToolTipEnabledPropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.isToolTipEnabledPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.menuProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.menuPropertyName, $.ig.XamRadialMenu.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.menuPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackForegroundPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonHotTrackStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonFillProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonFillPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonForegroundPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessPropertyName, Number, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.outerRingButtonStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.parentItemProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.parentItemPropertyName, $.ig.RadialMenuItemBase.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.parentItemPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.toolTipProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.toolTipPropertyName, $.ig.Object.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.toolTipResolvedPropertyName, null, null);
}));
$.ig.RadialMenuItemBase.prototype.wedgeIndexProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.wedgeIndexPropertyName, $.ig.Number.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, -1, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.wedgeIndexPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype.wedgeSpanProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.wedgeSpanPropertyName, $.ig.Number.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (o, e) {
	if ($.ig.util.getValue(e.newValue()) <= 0) {
		throw new $.ig.ArgumentOutOfRangeException(1, "WedgeSpan");
	}
	(o).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.wedgeSpanPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItemBase.prototype._visibilityProxyProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItemBase.prototype.visibilityProxyPropertyName, $.ig.Visibility.prototype.$type, $.ig.RadialMenuItemBase.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.visible), function (sender, e) {
	(sender).onPropertyChangedImpl($.ig.RadialMenuItemBase.prototype.visibilityProxyPropertyName, e.oldValue(), e.newValue());
}));
if ($.ig.RadialMenuItemBase.prototype.staticInit && !$.ig.RadialMenuItemBase.prototype.radialMenuItemBaseStaticInitCalled) { $.ig.RadialMenuItemBase.prototype.staticInit(); $.ig.RadialMenuItemBase.prototype.radialMenuItemBaseStaticInitCalled = true; }

$.ig.XamRadialMenu.prototype._centerButtonFocusSpace = 5;
$.ig.XamRadialMenu.prototype._toolAreaInnerSpace = 5;
$.ig.XamRadialMenu.prototype._highlightInset = 2;
$.ig.XamRadialMenu.prototype._sectorInset = Math.PI / 180 * 0.5;
$.ig.XamRadialMenu.prototype._expandRotationPercent = -1;
$.ig.XamRadialMenu.prototype.disabledMenuOpacity = 0.3;
$.ig.XamRadialMenu.prototype._leaveAnimationDuration = 250;
$.ig.XamRadialMenu.prototype._clickToolTipHideDelay = 1000;
$.ig.XamRadialMenu.prototype.centerButtonContentWidthPropertyName = "CenterButtonContentWidth";
$.ig.XamRadialMenu.prototype.centerButtonContentHeightPropertyName = "CenterButtonContentHeight";
$.ig.XamRadialMenu.prototype.centerButtonClosedFillPropertyName = "CenterButtonClosedFill";
$.ig.XamRadialMenu.prototype.centerButtonClosedStrokePropertyName = "CenterButtonClosedStroke";
$.ig.XamRadialMenu.prototype.centerButtonFillPropertyName = "CenterButtonFill";
$.ig.XamRadialMenu.prototype.centerButtonHotTrackFillPropertyName = "CenterButtonHotTrackFill";
$.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokePropertyName = "CenterButtonHotTrackStroke";
$.ig.XamRadialMenu.prototype.centerButtonContentPropertyName = "CenterButtonContent";
$.ig.XamRadialMenu.prototype.centerButtonKeyTipPropertyName = "CenterButtonKeyTip";
$.ig.XamRadialMenu.prototype.centerButtonStrokePropertyName = "CenterButtonStroke";
$.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessPropertyName = "CenterButtonStrokeThickness";
$.ig.XamRadialMenu.prototype.currentOpenMenuItemPropertyName = "CurrentOpenMenuItem";
$.ig.XamRadialMenu.prototype.fontPropertyName = "Font";
$.ig.XamRadialMenu.prototype.isOpenPropertyName = "IsOpen";
$.ig.XamRadialMenu.prototype.keyTipTemplatePropertyName = "KeyTipTemplate";
$.ig.XamRadialMenu.prototype.menuBackgroundPropertyName = "MenuBackground";
$.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationPropertyName = "MenuItemOpenCloseAnimationDuration";
$.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionPropertyName = "MenuItemOpenCloseAnimationEasingFunction";
$.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationPropertyName = "MenuOpenCloseAnimationDuration";
$.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionPropertyName = "MenuOpenCloseAnimationEasingFunction";
$.ig.XamRadialMenu.prototype.minWedgeCountPropertyName = "MinWedgeCount";
$.ig.XamRadialMenu.prototype.outerRingFillPropertyName = "OuterRingFill";
$.ig.XamRadialMenu.prototype.outerRingThicknessPropertyName = "OuterRingThickness";
$.ig.XamRadialMenu.prototype.outerRingStrokePropertyName = "OuterRingStroke";
$.ig.XamRadialMenu.prototype.outerRingStrokeThicknessPropertyName = "OuterRingStrokeThickness";
$.ig.XamRadialMenu.prototype.rotationInDegreesPropertyName = "RotationInDegrees";
$.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgePropertyName = "RotationAsPercentageOfWedge";
$.ig.XamRadialMenu.prototype.wedgePaddingInDegreesPropertyName = "WedgePaddingInDegrees";
$.ig.XamRadialMenu.prototype.centerButtonContentWidthProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonContentWidthPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 28, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonContentWidthPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonContentHeightProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonContentHeightPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 28, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonContentHeightPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonClosedFillProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonClosedFillPropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonClosedFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonClosedStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonClosedStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonClosedStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonFillProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonFillPropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonHotTrackFillProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonHotTrackFillPropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonHotTrackFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonHotTrackStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonContentProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonContentPropertyName, $.ig.Object.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonContentPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonKeyTipProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonKeyTipPropertyName, String, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, "0", function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonKeyTipPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.centerButtonStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.currentOpenMenuItemProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.currentOpenMenuItemPropertyName, $.ig.Object.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.currentOpenMenuItemPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.fontProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.fontPropertyName, String, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.fontPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.isOpenProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.isOpenPropertyName, $.ig.Boolean.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, false, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.isOpenPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.keyTipTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.keyTipTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.keyTipTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.menuBackgroundProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.menuBackgroundPropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.menuBackgroundPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationPropertyName, $.ig.Number.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 250, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationDurationPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionPropertyName, $.ig.Func$2.prototype.$type.specialize(Number, Number), $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.menuItemOpenCloseAnimationEasingFunctionPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationPropertyName, $.ig.Number.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 250, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationDurationPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionPropertyName, $.ig.Func$2.prototype.$type.specialize(Number, Number), $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.menuOpenCloseAnimationEasingFunctionPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.minWedgeCountProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.minWedgeCountPropertyName, $.ig.Number.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 8, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.minWedgeCountPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.outerRingFillProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.outerRingFillPropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.outerRingFillPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.outerRingThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.outerRingThicknessPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 26, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.outerRingThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.outerRingStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.outerRingStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.outerRingStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.outerRingStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.outerRingStrokeThicknessPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.outerRingStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.rotationInDegreesProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.rotationInDegreesPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, -90, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.rotationInDegreesPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgeProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgePropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, -0.5, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.rotationAsPercentageOfWedgePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamRadialMenu.prototype.wedgePaddingInDegreesProperty = $.ig.DependencyProperty.prototype.register($.ig.XamRadialMenu.prototype.wedgePaddingInDegreesPropertyName, Number, $.ig.XamRadialMenu.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (o, e) {
	(o).onPropertyChanged($.ig.XamRadialMenu.prototype.wedgePaddingInDegreesPropertyName, e.oldValue(), e.newValue());
}));
if ($.ig.XamRadialMenu.prototype.staticInit && !$.ig.XamRadialMenu.prototype.xamRadialMenuStaticInitCalled) { $.ig.XamRadialMenu.prototype.staticInit(); $.ig.XamRadialMenu.prototype.xamRadialMenuStaticInitCalled = true; }

$.ig.RadialMenuNumericGauge.prototype.pendingValuePropertyName = "PendingValue";
$.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushPropertyName = "PendingValueNeedleBrush";
$.ig.RadialMenuNumericGauge.prototype.reserveFirstSlicePropertyName = "ReserveFirstSlice";
$.ig.RadialMenuNumericGauge.prototype.smallIncrementPropertyName = "SmallIncrement";
$.ig.RadialMenuNumericGauge.prototype.tickBrushPropertyName = "TickBrush";
$.ig.RadialMenuNumericGauge.prototype.ticksPropertyName = "Ticks";
$.ig.RadialMenuNumericGauge.prototype.trackStartColorPropertyName = "TrackStartColor";
$.ig.RadialMenuNumericGauge.prototype.trackEndColorPropertyName = "TrackEndColor";
$.ig.RadialMenuNumericGauge.prototype.valuePropertyName = "Value";
$.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushPropertyName = "ValueNeedleBrush";
$.ig.RadialMenuNumericGauge.prototype.pendingValueProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.pendingValuePropertyName, Number, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	var gauge = o;
	gauge.onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.pendingValuePropertyName, e.oldValue(), e.newValue());
	var handler = gauge.pendingValueChanged;
	if (null != handler) {
		handler(gauge, new $.ig.RadialMenuNumericValueChangedEventArgs(e.oldValue(), e.newValue()));
	}
}));
$.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.pendingValueNeedleBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.reserveFirstSliceProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.reserveFirstSlicePropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.reserveFirstSlicePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.smallIncrementProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.smallIncrementPropertyName, Number, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.smallIncrementPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.tickBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.tickBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.tickBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.ticksProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.ticksPropertyName, $.ig.DoubleCollection.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.ticksPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.trackStartColorProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.trackStartColorPropertyName, $.ig.Color.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Color.prototype.fromArgb(0, 0, 0, 0), function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.trackStartColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.trackEndColorProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.trackEndColorPropertyName, $.ig.Color.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Color.prototype.fromArgb(255, 0, 0, 0), function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.trackEndColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuNumericGauge.prototype.valueProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.valuePropertyName, Number, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	var gauge = o;
	gauge.onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.valuePropertyName, e.oldValue(), e.newValue());
	var handler = gauge.valueChanged;
	if (null != handler) {
		handler(gauge, new $.ig.RadialMenuNumericValueChangedEventArgs(e.oldValue(), e.newValue()));
	}
}));
$.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.RadialMenuNumericGauge.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuNumericGauge.prototype.valueNeedleBrushPropertyName, e.oldValue(), e.newValue());
}));
if ($.ig.RadialMenuNumericGauge.prototype.staticInit && !$.ig.RadialMenuNumericGauge.prototype.radialMenuNumericGaugeStaticInitCalled) { $.ig.RadialMenuNumericGauge.prototype.staticInit(); $.ig.RadialMenuNumericGauge.prototype.radialMenuNumericGaugeStaticInitCalled = true; }

$.ig.XamRadialMenuView.prototype._tEXT_MARGIN = 0;
$.ig.XamRadialMenuView.prototype._menuCssClassChain = null;
$.ig.XamRadialMenuView.prototype._itemCssClassChain = null;
$.ig.XamRadialMenuView.prototype._toolTipCssClassChain = null;
$.ig.XamRadialMenuView.prototype.__radialMenuCount = 0;
if ($.ig.XamRadialMenuView.prototype.staticInit && !$.ig.XamRadialMenuView.prototype.xamRadialMenuViewStaticInitCalled) { $.ig.XamRadialMenuView.prototype.staticInit(); $.ig.XamRadialMenuView.prototype.xamRadialMenuViewStaticInitCalled = true; }

$.ig.RadialMenuDOMEventProxy.prototype.__proxyCount = 0;

$.ig.ImageManager.prototype.global = new $.ig.ImageManager();

$.ig.RadialMenuItemOverlayTemplates.prototype.numericOverlayTemplate = null;
$.ig.RadialMenuItemOverlayTemplates.prototype.colorOverlayTemplate = null;
if ($.ig.RadialMenuItemOverlayTemplates.prototype.staticInit && !$.ig.RadialMenuItemOverlayTemplates.prototype.radialMenuItemOverlayTemplatesStaticInitCalled) { $.ig.RadialMenuItemOverlayTemplates.prototype.staticInit(); $.ig.RadialMenuItemOverlayTemplates.prototype.radialMenuItemOverlayTemplatesStaticInitCalled = true; }

$.ig.RadialMenuItem.prototype.autoUpdateRecentItemPropertyName = "AutoUpdateRecentItem";
$.ig.RadialMenuItem.prototype.childItemPlacementPropertyName = "ChildItemPlacement";
$.ig.RadialMenuItem.prototype.checkBehaviorPropertyName = "CheckBehavior";
$.ig.RadialMenuItem.prototype.isCheckedPropertyName = "IsChecked";
$.ig.RadialMenuItem.prototype.groupNamePropertyName = "GroupName";
$.ig.RadialMenuItem.prototype.headerPropertyName = "Header";
$.ig.RadialMenuItem.prototype.iconUriPropertyName = "IconUri";
$.ig.RadialMenuItem.prototype.iconOverlayPropertyName = "IconOverlay";
$.ig.RadialMenuItem.prototype.iconOverlayValuePropertyName = "IconOverlayValue";
$.ig.RadialMenuItem.prototype.keyTipPropertyName = "KeyTip";
$.ig.RadialMenuItem.prototype.recentItemPropertyName = "RecentItem";
$.ig.RadialMenuItem.prototype.recentItemContainerPropertyName = "RecentItemContainer";
$.ig.RadialMenuItem.prototype.autoUpdateRecentItemProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.autoUpdateRecentItemPropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, true, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.autoUpdateRecentItemPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.childItemPlacementProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.childItemPlacementPropertyName, $.ig.RadialMenuChildItemPlacement.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.RadialMenuChildItemPlacement.prototype.getBox($.ig.RadialMenuChildItemPlacement.prototype.asChildren), function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.childItemPlacementPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.checkBehaviorProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.checkBehaviorPropertyName, $.ig.RadialMenuCheckBehavior.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.RadialMenuCheckBehavior.prototype.getBox($.ig.RadialMenuCheckBehavior.prototype.none), function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.checkBehaviorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.isCheckedProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.isCheckedPropertyName, $.ig.Boolean.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, false, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.isCheckedPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.groupNameProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.groupNamePropertyName, String, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.groupNamePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.headerProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.headerPropertyName, $.ig.Object.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.headerPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.iconUriProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.iconUriPropertyName, String, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.RadialMenuItem.prototype.iconUriPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.iconOverlayProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.iconOverlayPropertyName, $.ig.DataTemplate.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.iconOverlayPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.iconOverlayValueProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.iconOverlayValuePropertyName, $.ig.Object.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.iconOverlayValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.keyTipProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.keyTipPropertyName, String, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.keyTipPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.recentItemProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.recentItemPropertyName, $.ig.Object.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuItem.prototype.recentItemPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuItem.prototype.recentItemContainerProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuItem.prototype.recentItemContainerPropertyName, $.ig.Object.prototype.$type, $.ig.RadialMenuItem.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.RadialMenuItem.prototype.onRecentItemContainerChanged));
if ($.ig.RadialMenuItem.prototype.staticInit && !$.ig.RadialMenuItem.prototype.radialMenuItemStaticInitCalled) { $.ig.RadialMenuItem.prototype.staticInit(); $.ig.RadialMenuItem.prototype.radialMenuItemStaticInitCalled = true; }

$.ig.RadialMenuColorItemBase.prototype.colorPropertyName = "Color";
$.ig.RadialMenuColorItemBase.prototype.computedColorNamePropertyName = "ComputedColorName";
$.ig.RadialMenuColorItemBase.prototype.colorProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuColorItemBase.prototype.colorPropertyName, $.ig.Color.prototype.$type, $.ig.RadialMenuColorItemBase.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Color.prototype.fromArgb(0, 0, 0, 0), function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuColorItemBase.prototype.colorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.RadialMenuColorItemBase.prototype.computedColorNameProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuColorItemBase.prototype.computedColorNamePropertyName, String, $.ig.RadialMenuColorItemBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChangedImpl($.ig.RadialMenuColorItemBase.prototype.computedColorNamePropertyName, e.oldValue(), e.newValue());
}));

if ($.ig.RadialMenuColorItem.prototype.staticInit && !$.ig.RadialMenuColorItem.prototype.radialMenuColorItemStaticInitCalled) { $.ig.RadialMenuColorItem.prototype.staticInit(); $.ig.RadialMenuColorItem.prototype.radialMenuColorItemStaticInitCalled = true; }

if ($.ig.RadialMenuColorWell.prototype.staticInit && !$.ig.RadialMenuColorWell.prototype.radialMenuColorWellStaticInitCalled) { $.ig.RadialMenuColorWell.prototype.staticInit(); $.ig.RadialMenuColorWell.prototype.radialMenuColorWellStaticInitCalled = true; }

$.ig.RadialMenuNumericItem.prototype.valuePropertyName = "Value";
$.ig.RadialMenuNumericItem.prototype.valueProperty = $.ig.DependencyProperty.prototype.register($.ig.RadialMenuNumericItem.prototype.valuePropertyName, Number, $.ig.RadialMenuNumericItem.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	var item = o;
	item.onPropertyChangedImpl($.ig.RadialMenuNumericItem.prototype.valuePropertyName, e.oldValue(), e.newValue());
	var handler = item.valueChanged;
	if (null != handler) {
		handler(item, new $.ig.RadialMenuNumericValueChangedEventArgs(e.oldValue(), e.newValue()));
	}
}));
if ($.ig.RadialMenuNumericItem.prototype.staticInit && !$.ig.RadialMenuNumericItem.prototype.radialMenuNumericItemStaticInitCalled) { $.ig.RadialMenuNumericItem.prototype.staticInit(); $.ig.RadialMenuNumericItem.prototype.radialMenuNumericItemStaticInitCalled = true; }

$.ig.RadialMenuUtilities.prototype.zIndexMenu = 0;
$.ig.RadialMenuUtilities.prototype.zIndexMenuOuterRing = 100;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeOuterRing = 200;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeOuterRingArrow = 300;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeToolArea = 500;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeColorWell = 600;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeToolCheckmark = 700;
$.ig.RadialMenuUtilities.prototype.zIndexWedgeToolHighlight = 800;
$.ig.RadialMenuUtilities.prototype.zIndexMenuInnerRing = 900;
$.ig.RadialMenuUtilities.prototype.zIndexMenuInnerRingFocus = 1000;
$.ig.RadialMenuUtilities.prototype.zIndexCenterButton = 1100;
$.ig.RadialMenuUtilities.prototype._transparent = (function () {
	var $ret = new $.ig.Color();
	$ret.colorString("transparent");
	return $ret;
}());
$.ig.RadialMenuUtilities.prototype._black = (function () {
	var $ret = new $.ig.Color();
	$ret.colorString("black");
	return $ret;
}());
$.ig.RadialMenuUtilities.prototype._white = (function () {
	var $ret = new $.ig.Color();
	$ret.colorString("white");
	return $ret;
}());
$.ig.RadialMenuUtilities.prototype.transparentBrush = (function () {
	var $ret = new $.ig.Brush();
	$ret.color($.ig.RadialMenuUtilities.prototype._transparent);
	return $ret;
}());
$.ig.RadialMenuUtilities.prototype.__colorCalculator = null;
if ($.ig.RadialMenuUtilities.prototype.staticInit && !$.ig.RadialMenuUtilities.prototype.radialMenuUtilitiesStaticInitCalled) { $.ig.RadialMenuUtilities.prototype.staticInit(); $.ig.RadialMenuUtilities.prototype.radialMenuUtilitiesStaticInitCalled = true; }

$.ig.ShapeUtilities.prototype.twoPI = Math.PI * 2;
$.ig.ShapeUtilities.prototype.squareOf2 = Math.sqrt(2);

} (jQuery));


