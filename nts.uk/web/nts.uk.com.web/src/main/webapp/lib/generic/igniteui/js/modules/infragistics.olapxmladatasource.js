/*!@license
* Infragistics.Web.ClientUI infragistics.olapxmladatasource.js 16.1.20161.2145
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
"InvalidOperationException:ci", 
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
"NameValueCollection:c1", 
"Stack$1:c2", 
"ReverseArrayEnumerator$1:c3", 
"AggregateException:dr", 
"APIFactory:ds", 
"Point:dt", 
"Rect:du", 
"Size:dv", 
"Color:dw", 
"AsyncCompletedEventArgs:d0", 
"ListSortDirection:d2", 
"Environment:d5", 
"Debug:ee", 
"UTF8Encoding:eh", 
"Encoding:ei", 
"UnicodeEncoding:ej", 
"AsciiEncoding:ek", 
"Decoder:el", 
"DefaultDecoder:em", 
"UTF8Encoding_UTF8Decoder:en", 
"StringBuilder:eu", 
"ICredentials:ev", 
"NetworkCredential:ew", 
"Uri:ex", 
"UriKind:ey", 
"UploadDataCompletedEventHandler:ez", 
"UploadDataCompletedEventArgs:e0", 
"UploadStringCompletedEventHandler:e1", 
"UploadStringCompletedEventArgs:e2", 
"WebClient:e3", 
"WebHeaderCollection:e4", 
"Task$1:e5", 
"Task:e6", 
"JQueryPromise:e7", 
"Action:e8", 
"TaskStatus:e9", 
"TaskCompletionSource$1:fa", 
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
"FaultCode:fu", 
"FaultException:fv", 
"FaultReason:fw", 
"FaultException$1:fx", 
"TaskFactory:f7", 
"Tuple$2:f8", 
"JavaScriptSerializer:gc", 
"XObject:ia", 
"XmlNode:ib", 
"XmlNodeList:ic", 
"XmlNamedNodeMap:id", 
"XmlNodeType:ie", 
"XmlDocument:ig", 
"XmlElement:ih", 
"XmlLinkedNode:ii", 
"XmlAttribute:ij", 
"XmlUtils:ik", 
"XAttribute:il", 
"XNode:im", 
"XContainer:io", 
"XElement:ip", 
"XName:iq", 
"XNamespace:ir", 
"XDocument:is"]);


$.ig.util.defType('UriKind', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "RelativeOrAbsolute";
			case 1: return "Absolute";
			case 2: return "Relative";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('UriKind', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('TaskStatus', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Created";
			case 5: return "RanToCompletion";
			case 6: return "Canceled";
			case 7: return "Faulted";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('TaskStatus', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('ListSortDirection', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Ascending";
			case 1: return "Descending";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ListSortDirection', $.ig.Enum.prototype.$type)
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

$.ig.util.defType('INotifyCollectionChanged', 'Object', {
	$type: new $.ig.Type('INotifyCollectionChanged', null)
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

$.ig.util.defType('NameValueCollection', 'Object', {
	__dict: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__dict = new $.ig.Dictionary$2(String, String, 0);
	},
	item: function (name, value) {
		if (arguments.length === 2) {
			if (this.__dict.containsKey(name)) {
				this.__dict.item(name, value);
			} else {
				this.__dict.add(name, value);
			}
			return value;
		} else {
			if (this.__dict.containsKey(name)) {
				return this.__dict.item(name);
			} else {
				return null;
			}
		}
	}
	,
	$type: new $.ig.Type('NameValueCollection', $.ig.Object.prototype.$type)
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

$.ig.util.defType('AggregateException', 'Error', {
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
		$.ig.Error.prototype.init.call(this, 0);
	},
	init1: function (initNumber, message) {
		$.ig.Error.prototype.init1.call(this, 1, message);
	},
	init2: function (initNumber, message, innerException) {
		$.ig.Error.prototype.init2.call(this, 2, message, innerException);
	},
	$type: new $.ig.Type('AggregateException', $.ig.Error.prototype.$type)
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

$.ig.util.defType('AsyncCompletedEventArgs', 'EventArgs', {
	__error: null,
	__cancelled: false,
	__userState: null,
	init: function (error, cancelled, userState) {
		$.ig.EventArgs.prototype.init.call(this);
		this.__cancelled = cancelled;
		this.__error = error;
		this.__userState = userState;
	},
	error: function () {
		return this.__error;
	}
	,
	cancelled: function () {
		return this.__cancelled;
	}
	,
	userState: function () {
		return this.__userState;
	}
	,
	raiseExceptionIfNecessary: function () {
		if (this.error() != null) {
			throw this.error();
		}
	}
	,
	$type: new $.ig.Type('AsyncCompletedEventArgs', $.ig.EventArgs.prototype.$type)
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

$.ig.util.defType('ICredentials', 'Object', {
	$type: new $.ig.Type('ICredentials', null)
}, true);

$.ig.util.defType('NetworkCredential', 'Object', {
	init: function (initNumber, userName, password) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.NetworkCredential.prototype.init1.call(this, 1, userName, password, String.empty());
	},
	init1: function (initNumber, userName, password, domain) {
		$.ig.Object.prototype.init.call(this);
		this.userName(userName);
		this.password(password);
		this.domain(domain);
	},
	getCredential: function (uri, authType) {
		return this;
	}
	,
	_userName: null,
	userName: function (value) {
		if (arguments.length === 1) {
			this._userName = value;
			return value;
		} else {
			return this._userName;
		}
	}
	,
	_password: null,
	password: function (value) {
		if (arguments.length === 1) {
			this._password = value;
			return value;
		} else {
			return this._password;
		}
	}
	,
	_domain: null,
	domain: function (value) {
		if (arguments.length === 1) {
			this._domain = value;
			return value;
		} else {
			return this._domain;
		}
	}
	,
	$type: new $.ig.Type('NetworkCredential', $.ig.Object.prototype.$type, [$.ig.ICredentials.prototype.$type])
}, true);

$.ig.util.defType('UploadDataCompletedEventArgs', 'AsyncCompletedEventArgs', {
	_m_Result: null,
	init: function (result, exception, cancelled, userToken) {
		$.ig.AsyncCompletedEventArgs.prototype.init.call(this, exception, cancelled, userToken);
		this._m_Result = result;
	},
	result: function () {
		this.raiseExceptionIfNecessary();
		return this._m_Result;
	}
	,
	$type: new $.ig.Type('UploadDataCompletedEventArgs', $.ig.AsyncCompletedEventArgs.prototype.$type)
}, true);

$.ig.util.defType('UploadStringCompletedEventArgs', 'AsyncCompletedEventArgs', {
	__result: null,
	init: function (result, exception, cancelled, userToken) {
		$.ig.AsyncCompletedEventArgs.prototype.init.call(this, exception, cancelled, userToken);
		this.__result = result;
	},
	result: function () {
		this.raiseExceptionIfNecessary();
		return this.__result;
	}
	,
	$type: new $.ig.Type('UploadStringCompletedEventArgs', $.ig.AsyncCompletedEventArgs.prototype.$type)
}, true);

$.ig.util.defType('WebClient', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.headers(new $.ig.WebHeaderCollection());
	},
	_encoding: null,
	encoding: function (value) {
		if (arguments.length === 1) {
			this._encoding = value;
			return value;
		} else {
			return this._encoding;
		}
	}
	,
	_headers: null,
	headers: function (value) {
		if (arguments.length === 1) {
			this._headers = value;
			return value;
		} else {
			return this._headers;
		}
	}
	,
	_credentials: null,
	credentials: function (value) {
		if (arguments.length === 1) {
			this._credentials = value;
			return value;
		} else {
			return this._credentials;
		}
	}
	,
	uploadStringCompleted: null,
	uploadStringAsync: function (address, method, data, userData) {
		var $self = this;
		this.uploadStringTaskAsync(address, method, data).continueWith1(function (t) {
			if ($self.uploadStringCompleted != null) {
				var result = null;
				var exception = null;
				try {
					result = t.result();
				}
				catch (e_) {
					exception = e_;
				}
				$self.uploadStringCompleted($self, new $.ig.UploadStringCompletedEventArgs(result, exception, t.status() == $.ig.TaskStatus.prototype.canceled, userData));
			}
		});
	}
	,
	uploadStringTaskAsync: function (address, method, data) {
		var url_ = address.value();
		var method_ = method;
		var data_ = data;
		var contentType_ = this.headers().item("Content-Type");
		var credentials_ = this.credentials();
		var promise = $.ig.util.ajax(url_, contentType_, data_, method_, credentials_);
		return new $.ig.Task$1(String, promise, null);
	}
	,
	uploadDataCompleted: null,
	uploadDataAsync: function (address, method, data, userData) {
		var $self = this;
		this.uploadDataTaskAsync(address, method, data).continueWith1(function (t) {
			if ($self.uploadDataCompleted != null) {
				var result = null;
				var exception = null;
				try {
					result = t.result();
				}
				catch (e_) {
					exception = e_;
				}
				$self.uploadDataCompleted($self, new $.ig.UploadDataCompletedEventArgs(result, exception, t.status() == $.ig.TaskStatus.prototype.canceled, userData));
			}
		});
	}
	,
	uploadDataTaskAsync: function (address, method, data) {
		var url_ = address.value();
		var method_ = method;
		var data_ = data;
		var contentType_ = this.headers().item("Content-Type");
		var credentials_ = this.credentials();
		var promise = $.ig.util.ajax(url_, contentType_, data_, method_, credentials_);
		return new $.ig.Task$1(Array, promise, null);
	}
	,
	onSuccess: function (tcs, data, textStatus, jqXHR) {
		tcs.setResult(data);
	}
	,
	onError: function (tcs, jqXHR, textStatus, errorThrown) {
		tcs.setException(new $.ig.Error(1, errorThrown));
	}
	,
	$type: new $.ig.Type('WebClient', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('WebHeaderCollection', 'NameValueCollection', {
	init: function () {
		$.ig.NameValueCollection.prototype.init.call(this);
	},
	$type: new $.ig.Type('WebHeaderCollection', $.ig.NameValueCollection.prototype.$type)
}, true);

$.ig.util.defType('FaultCode', 'Object', {
	init: function (name) {
		$.ig.Object.prototype.init.call(this);
		this.name(name);
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
	$type: new $.ig.Type('FaultCode', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FaultException', 'Error', {
	init: function (reason, code, action) {
		$.ig.Error.prototype.init.call(this, 0);
		this.reason(reason);
		this.code(code);
		this.action(action);
	},
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
	_code: null,
	code: function (value) {
		if (arguments.length === 1) {
			this._code = value;
			return value;
		} else {
			return this._code;
		}
	}
	,
	_reason: null,
	reason: function (value) {
		if (arguments.length === 1) {
			this._reason = value;
			return value;
		} else {
			return this._reason;
		}
	}
	,
	$type: new $.ig.Type('FaultException', $.ig.Error.prototype.$type)
}, true);

$.ig.util.defType('FaultException$1', 'FaultException', {
	$tDetail: null,
	init: function ($tDetail, detail, reason, code, action) {
		this.$tDetail = $tDetail;
		this.$type = this.$type.specialize(this.$tDetail);
		$.ig.FaultException.prototype.init.call(this, reason, code, action);
		this.detail(detail);
	},
	_detail: null,
	detail: function (value) {
		if (arguments.length === 1) {
			this._detail = value;
			return value;
		} else {
			return this._detail;
		}
	}
	,
	$type: new $.ig.Type('FaultException$1', $.ig.FaultException.prototype.$type)
}, true);

$.ig.util.defType('FaultReason', 'Object', {
	__text: null,
	init: function (text) {
		$.ig.Object.prototype.init.call(this);
		this.__text = text;
	},
	toString: function () {
		return this.__text;
	}
	,
	$type: new $.ig.Type('FaultReason', $.ig.Object.prototype.$type)
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

$.ig.util.defType('Encoding', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	uTF8: function () {
		if ($.ig.Encoding.prototype.__utfEncoding == null) {
			$.ig.Encoding.prototype.__utfEncoding = new $.ig.UTF8Encoding(1);
		}
		return $.ig.Encoding.prototype.__utfEncoding;
	}
	,
	uTF8Unmarked: function () {
		if ($.ig.Encoding.prototype.__utf8Unmarked == null) {
			$.ig.Encoding.prototype.__utf8Unmarked = new $.ig.UTF8Encoding(1);
		}
		return $.ig.Encoding.prototype.__utf8Unmarked;
	}
	,
	unicode: function () {
		if ($.ig.Encoding.prototype.__unicodeEncoding == null) {
			$.ig.Encoding.prototype.__unicodeEncoding = new $.ig.UnicodeEncoding(0);
		}
		return $.ig.Encoding.prototype.__unicodeEncoding;
	}
	,
	getString1: function (bytes, startIndex, length) {
		return "";
	}
	,
	getBytes2: function (chars, charIndex, charCount, bytes, byteIndex) {
	}
	,
	getBytes: function (chars, index, count) {
		var array = new Array(this.getByteCount(chars, index, count));
		this.getBytes2(chars, index, count, array, 0);
		return array;
	}
	,
	getBytes1: function (input) {
		if (input == null) {
			throw new $.ig.ArgumentNullException(0, "input");
		}
		var array = new Array(input.length);
		for (var i = 0; i < input.length; i++) {
			array[i] = input.charAt(i);
		}
		return this.getBytes(array, 0, array.length);
	}
	,
	getByteCount: function (chars, index, count) {
	}
	,
	getString: function (bytes) {
		return this.getString1(bytes, 0, bytes.length);
	}
	,
	getCharCount: function (bytes) {
		if (bytes == null) {
			throw new $.ig.ArgumentNullException(0, "bytes");
		}
		return this.getCharCount1(bytes, 0, bytes.length);
	}
	,
	getCharCount1: function (bytes, index, count) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	aSCII: function () {
		if ($.ig.Encoding.prototype.__asciiEncoding == null) {
			$.ig.Encoding.prototype.__asciiEncoding = new $.ig.AsciiEncoding(1);
		}
		return $.ig.Encoding.prototype.__asciiEncoding;
	}
	,
	getDecoder: function () {
		return new $.ig.DefaultDecoder(this);
	}
	,
	getBytes3: function (s, charIndex, charCount, bytes, byteIndex) {
		return this.getBytes2($.ig.util.toCharArray(s), charIndex, charCount, bytes, byteIndex);
	}
	,
	getMaxCharCount: function (size) {
		return size;
	}
	,
	getMaxByteCount: function (size) {
		return size + 1;
	}
	,
	getPreamble: function () {
		return new Array(0);
	}
	,
	bigEndianUnicode: function () {
		if ($.ig.Encoding.prototype.__bigEndianUnicodeEncoding == null) {
			$.ig.Encoding.prototype.__bigEndianUnicodeEncoding = new $.ig.UnicodeEncoding(1, true, false);
		}
		return $.ig.Encoding.prototype.__bigEndianUnicodeEncoding;
	}
	,
	defaultValue: function () {
		return $.ig.Encoding.prototype.aSCII();
	}
	,
	getEncoding: function (name) {
		switch (name.toUpperCase()) {
			case "ASCII": return $.ig.Encoding.prototype.aSCII();
			case "UNICODE": return $.ig.Encoding.prototype.unicode();
			case "UTF-8": return $.ig.Encoding.prototype.uTF8();
			default: throw new $.ig.ArgumentException(1, "'" + name + "' is not a valid encoding name.");
		}
	}
	,
	webName: function () {
		throw new $.ig.NotImplementedException(0);
		return null;
	}
	,
	$type: new $.ig.Type('Encoding', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('UnicodeEncoding', 'Encoding', {
	__bigEndian: false,
	getString1: function (bytes_, startIndex, length) {
		var ret = "";
		var end = startIndex + length;
		for (var i_ = startIndex; i_ < end; i_ = i_ + 2) {
			if (i_ + 1 >= end) {
				ret = ret + '�';
			} else {
				var bits0;
				var bits1;
				if (this.__bigEndian) {
					bits0 = (bytes_[i_ + 1]).toString(16);
					bits1 = (bytes_[i_]).toString(16);
				} else {
					bits0 = (bytes_[i_]).toString(16);
					bits1 = (bytes_[i_ + 1]).toString(16);
				}
				if (bits0.length == 1) {
					bits0 = "0" + bits0;
				}
				if (bits1.length == 1) {
					bits1 = "0" + bits1;
				}
				var code = $.ig.Number.prototype.parseInt(bits1 + bits0, 16);
				ret = ret + String.fromCharCode(code);
			}
		}
		return ret;
	}
	,
	getCharCount1: function (bytes, index, count) {
		return $.ig.intDivide(count, 2);
	}
	,
	getBytes: function (chars, index, count) {
		return $.ig.Encoding.prototype.getBytes.call(this, chars, index, count);
	}
	,
	getBytes1: function (input) {
		var bytes = new Array(input.length * 2);
		this.getBytes3(input, 0, input.length, bytes, 0);
		return bytes;
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
		$.ig.Encoding.prototype.init.call(this);
	},
	init1: function (initNumber, bigEndian, byteOrderMark) {
		$.ig.Encoding.prototype.init.call(this);
		this.__bigEndian = bigEndian;
	},
	getMaxByteCount: function (size) {
		return (size + 1) * 2;
	}
	,
	getMaxCharCount: function (size) {
		return $.ig.truncate(Math.ceil(size / 2)) + 1;
	}
	,
	getBytes3: function (s, charIndex, charCount, bytes, byteIndex) {
		for (var i = charIndex; i < charIndex + charCount; i++) {
			var value = s.charCodeAt(i);
			var lo = (value & 255);
			var hi = ((value >> 8) & 255);
			if (this.__bigEndian) {
				bytes[byteIndex++] = hi;
				bytes[byteIndex++] = lo;
			} else {
				bytes[byteIndex++] = lo;
				bytes[byteIndex++] = hi;
			}
		}
		return charCount * 2;
	}
	,
	getByteCount: function (chars, index, count) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	getBytes2: function (chars, charIndex, charCount, bytes, byteIndex) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	$type: new $.ig.Type('UnicodeEncoding', $.ig.Encoding.prototype.$type)
}, true);

$.ig.util.defType('UTF8Encoding', 'Encoding', {
	init: function (initNumber, encoderShouldEmitUTF8Identifier, throwOnInvalidBytes) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Encoding.prototype.init.call(this);
	},
	init1: function (initNumber) {
		$.ig.Encoding.prototype.init.call(this);
	},
	getString1: function (bytes, startIndex, length) {
		var ret = "";
		var i = startIndex;
		var c1 = 0;
		var c2 = 0;
		var c3 = 0;
		while (i < startIndex + length) {
			c1 = bytes[i++];
			if (c1 < 128) {
				ret += String.fromCharCode(c1);
			} else if (c1 > 191 && c1 < 224) {
				if (i >= startIndex + length) {
					throw new $.ig.InvalidOperationException(1, "UTF-8 decoding error.");
				}
				c2 = bytes[i++];
				ret += String.fromCharCode((((c1 & 31) << 6) | (c2 & 63)));
			} else {
				if (i + 1 >= startIndex + length) {
					throw new $.ig.InvalidOperationException(1, "UTF-8 decoding error.");
				}
				c2 = bytes[i++];
				c3 = bytes[i++];
				ret += String.fromCharCode((((c1 & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
			}
		}
		return ret;
	}
	,
	getCharCount1: function (bytes, index, count) {
		return count;
	}
	,
	getByteCount: function (chars, index, count) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	getBytes2: function (chars_, charIndex_, charCount_, bytes, byteIndex) {
		var inputUTF8_ = unescape(encodeURIComponent(chars_.slice(charIndex_, charIndex_ + charCount_).join("")));
		for (var i_ = 0; i_ < inputUTF8_.length; i_++) {
			bytes[byteIndex + i_] = inputUTF8_.charCodeAt(i_);
		}
		return inputUTF8_.length;
	}
	,
	getBytes: function (chars, index, count) {
		return $.ig.Encoding.prototype.getBytes.call(this, chars, index, count);
	}
	,
	getBytes1: function (input_) {
		var bytes = new Array(input_.length);
		var inputUTF8_ = unescape(encodeURIComponent(input_));
		for (var i_ = 0; i_ < inputUTF8_.length; i_++) {
			bytes[i_] = inputUTF8_.charCodeAt(i_);
		}
		return bytes;
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
		var originalCharIndex = charIndex;
		var i = byteIndex;
		var c1 = 0;
		var c2 = 0;
		var c3 = 0;
		while (i < byteIndex + byteCount) {
			c1 = bytes[i++];
			if (c1 < 128) {
				chars[charIndex++] = String.fromCharCode(c1);
			} else if (c1 > 191 && c1 < 224) {
				if (i >= byteIndex + byteCount) {
					throw new $.ig.InvalidOperationException(1, "UTF-8 decoding error.");
				}
				c2 = bytes[i++];
				chars[charIndex++] = String.fromCharCode((((c1 & 31) << 6) | (c2 & 63)));
			} else {
				if (i + 1 >= byteIndex + byteCount) {
					throw new $.ig.InvalidOperationException(1, "UTF-8 decoding error.");
				}
				c2 = bytes[i++];
				c3 = bytes[i++];
				chars[charIndex++] = String.fromCharCode((((c1 & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
			}
		}
		return charIndex - originalCharIndex;
	}
	,
	getDecoder: function () {
		return new $.ig.UTF8Encoding_UTF8Decoder(this);
	}
	,
	getMaxByteCount: function (size) {
		return (size + 1) * 3;
	}
	,
	getMaxCharCount: function (size) {
		return size + 1;
	}
	,
	$type: new $.ig.Type('UTF8Encoding', $.ig.Encoding.prototype.$type)
}, true);

$.ig.util.defType('Decoder', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	convert: function (bytes, byteIndex, byteCount, chars, charIndex, charCount, flush, bytesUsed, charsUsed, completed) {
		throw new $.ig.NotImplementedException(0);
		bytesUsed = 0;
		charsUsed = 0;
		completed = false;
		return {
			p7: bytesUsed,
			p8: charsUsed,
			p9: completed
		};
	}
	,
	getCharCount: function (bytes, index, count) {
	}
	,
	getCharCount1: function (bytes, index, count, flush) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
	}
	,
	getChars1: function (bytes, byteIndex, byteCount, chars, charIndex, flush) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	reset: function () {
		throw new $.ig.NotImplementedException(0);
	}
	,
	$type: new $.ig.Type('Decoder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('UTF8Encoding_UTF8Decoder', 'Decoder', {
	__c1: 0,
	__c2: 0,
	__c3: 0,
	__encoding: null,
	init: function (encoding) {
		$.ig.Decoder.prototype.init.call(this);
		this.__encoding = encoding;
	},
	getCharCount: function (bytes, index, count) {
		return this.getCharCount1(bytes, index, count, false);
	}
	,
	getCharCount1: function (bytes, index, count, flush) {
		var charCount = 0;
		var i = index;
		while (i < index + count) {
			if (this.__c1 == 0) {
				this.__c1 = bytes[i++];
			}
			if (this.__c1 < 128) {
				charCount++;
			} else {
				if (i >= index + count) {
					break;
				}
				if (this.__c2 == 0) {
					this.__c2 = bytes[i++];
				}
				if (this.__c1 > 191 && this.__c1 < 224) {
					charCount++;
				} else {
					if (i >= index + count) {
						break;
					}
					if (this.__c3 == 0) {
						this.__c3 = bytes[i++];
					}
					charCount++;
					this.__c3 = 0;
				}
				this.__c2 = 0;
			}
			this.__c1 = 0;
		}
		if (flush) {
			this.__c1 = 0;
			this.__c2 = 0;
			this.__c3 = 0;
		}
		return charCount;
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
		return this.getChars1(bytes, byteIndex, byteCount, chars, charIndex, false);
	}
	,
	getChars1: function (bytes, byteIndex, byteCount, chars, charIndex, flush) {
		var originalCharIndex = charIndex;
		var i = byteIndex;
		while (i < byteIndex + byteCount) {
			if (this.__c1 == 0) {
				this.__c1 = bytes[i++];
			}
			if (this.__c1 < 128) {
				chars[charIndex++] = String.fromCharCode(this.__c1);
			} else {
				if (i >= byteIndex + byteCount) {
					break;
				}
				if (this.__c2 == 0) {
					this.__c2 = bytes[i++];
				}
				if (this.__c1 > 191 && this.__c1 < 224) {
					chars[charIndex++] = String.fromCharCode((((this.__c1 & 31) << 6) | (this.__c2 & 63)));
				} else {
					if (i >= byteIndex + byteCount) {
						break;
					}
					if (this.__c3 == 0) {
						this.__c3 = bytes[i++];
					}
					chars[charIndex++] = String.fromCharCode((((this.__c1 & 15) << 12) | ((this.__c2 & 63) << 6) | (this.__c3 & 63)));
					this.__c3 = 0;
				}
				this.__c2 = 0;
			}
			this.__c1 = 0;
		}
		if (flush) {
			this.__c1 = 0;
			this.__c2 = 0;
			this.__c3 = 0;
		}
		return charIndex - originalCharIndex;
	}
	,
	$type: new $.ig.Type('UTF8Encoding_UTF8Decoder', $.ig.Decoder.prototype.$type)
}, true);

$.ig.util.defType('AsciiEncoding', 'Encoding', {
	init: function (initNumber, encoderShouldEmitUTF8Identifier, throwOnInvalidBytes) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Encoding.prototype.init.call(this);
	},
	init1: function (initNumber) {
		$.ig.Encoding.prototype.init.call(this);
	},
	getString1: function (bytes_, startIndex, length) {
		var ret_ = "";
		for (var i_ = startIndex; i_ < startIndex + length; i_++) {
			if (bytes_[i_] == 0) {
				break;
			}
			ret_ = ret_ + String.fromCharCode(bytes_[i_]);
		}
		return ret_;
	}
	,
	getCharCount1: function (bytes, index, count) {
		return count;
	}
	,
	getByteCount: function (chars, index, count) {
		return count;
	}
	,
	getBytes2: function (chars, charIndex, charCount, bytes, byteIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	getBytes: function (chars, index, count) {
		return $.ig.Encoding.prototype.getBytes.call(this, chars, index, count);
	}
	,
	getBytes1: function (input_) {
		var bytes = new Array(input_.length);
		for (var i_ = 0; i_ < input_.length; i_++) {
			bytes[i_] = input_.charCodeAt(i_);
		}
		return bytes;
	}
	,
	getChars: function (bytes_, byteIndex_, byteCount, chars, charIndex) {
		var originalCharIndex = charIndex;
		var ret_ = "";
		for (var i_ = 0; i_ < byteCount; i_++) {
			if (bytes_[i_] == 0) {
				break;
			}
			chars[charIndex++] = String.fromCharCode(bytes_[byteIndex_++]);
		}
		return charIndex - originalCharIndex;
	}
	,
	$type: new $.ig.Type('AsciiEncoding', $.ig.Encoding.prototype.$type)
}, true);

$.ig.util.defType('DefaultDecoder', 'Decoder', {
	__encoding: null,
	init: function (encoding) {
		$.ig.Decoder.prototype.init.call(this);
		this.__encoding = encoding;
	},
	getCharCount: function (bytes, index, count) {
		return this.getCharCount1(bytes, index, count, false);
	}
	,
	getCharCount1: function (bytes, index, count, flush) {
		return this.__encoding.getCharCount1(bytes, index, count);
	}
	,
	getChars: function (bytes, byteIndex, byteCount, chars, charIndex) {
		return this.getChars1(bytes, byteIndex, byteCount, chars, charIndex, false);
	}
	,
	getChars1: function (bytes, byteIndex, byteCount, chars, charIndex, flush) {
		return this.__encoding.getChars(bytes, byteIndex, byteCount, chars, charIndex);
	}
	,
	$type: new $.ig.Type('DefaultDecoder', $.ig.Decoder.prototype.$type)
}, true);

$.ig.util.defType('Task', 'Object', {
	_promise: null,
	promise: function (value) {
		if (arguments.length === 1) {
			this._promise = value;
			return value;
		} else {
			return this._promise;
		}
	}
	,
	_isCanceled: false,
	isCanceled: function (value) {
		if (arguments.length === 1) {
			this._isCanceled = value;
			return value;
		} else {
			return this._isCanceled;
		}
	}
	,
	_exception: null,
	exception: function (value) {
		if (arguments.length === 1) {
			this._exception = value;
			return value;
		} else {
			return this._exception;
		}
	}
	,
	init: function (initNumber, promise) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Task.prototype.init1.call(this, 1, promise, null);
	},
	init1: function (initNumber, promise, state) {
		var $self = this;
		$.ig.Object.prototype.init.call(this);
		var doneContinuation = function () {
		};
		var failContinuation = function (exception) {
			if (exception == null) {
				$self.isCanceled(true);
			} else {
				$self.exception(new $.ig.AggregateException(2, "Exception occurred during task execution", exception));
			}
		};
		this.promise(promise.done(doneContinuation).fail(failContinuation));
		this.asyncState(state);
	},
	status: function () {
		if (this.isCanceled()) {
			return $.ig.TaskStatus.prototype.canceled;
		}
		switch (this.promise().state()) {
			case "pending": return $.ig.TaskStatus.prototype.created;
			case "resolved": return $.ig.TaskStatus.prototype.ranToCompletion;
			case "rejected": return $.ig.TaskStatus.prototype.faulted;
			default: return $.ig.TaskStatus.prototype.created;
		}
	}
	,
	_asyncState: null,
	asyncState: function (value) {
		if (arguments.length === 1) {
			this._asyncState = value;
			return value;
		} else {
			return this._asyncState;
		}
	}
	,
	continueWith: function (continuationAction) {
		var $self = this;
		var doneContinuation = function () { continuationAction($self); };
		var failContinuation = function (exception) { continuationAction($self); };
		var continuationPromise = this.promise().done(doneContinuation).fail(failContinuation);
		return new $.ig.Task(0, continuationPromise);
	}
	,
	continueWith$1: function ($tNewResult, continuationFunction) {
		var $self = this;
		var tcs = new $.ig.TaskCompletionSource$1($tNewResult, 0);
		var continuation = function () {
			try {
				var newResult = continuationFunction($self);
				tcs.setResult(newResult);
			}
			catch (e_) {
				tcs.setException(e_);
			}
		};
		var doneContinuation = function (res) { continuation(); };
		var failContinuation = function (exc) { continuation(); };
		this.promise().done(doneContinuation).fail(failContinuation);
		return tcs.task();
	}
	,
	$type: new $.ig.Type('Task', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Task$1', 'Task', {
	$tResult: null,
	init: function ($tResult, promise, state) {
		var $self = this;
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tResult);
		this.__resultReady = false;
		$.ig.Task.prototype.init1.call(this, 1, promise, state);
		var doneContinuation = function (res) {
			var result = $.ig.util.castObjTo$t($self.$tResult, res);
			$self.__result = result;
			$self.__resultReady = true;
		};
		var failContinuation = function (exc) {
			$self.__resultReady = true;
		};
		this.promise().done(doneContinuation).fail(failContinuation);
	},
	__resultReady: false,
	__result: null,
	result: function () {
		if (!this.__resultReady) {
			throw 'Result is not ready yet';
		}
		if (this.exception() != null) {
			throw this.exception();
		}
		return this.__result;
	}
	,
	continueWith1: function (continuationAction) {
		return $.ig.Task.prototype.continueWith.call(this, function (t) { continuationAction(t); });
	}
	,
	continueWith$11: function ($tNewResult, continuationFunction) {
		var wrapperFunction = function (t) { return $.ig.util.getBoxIfEnum($tNewResult, continuationFunction(t)); };
		return this.continueWithBase$1($.ig.Object.prototype.$type, wrapperFunction).continueWithBase$1($tNewResult, function (t) { return $.ig.util.castObjTo$t($tNewResult, (t).result()); });
	}
	,
	continueWithBase$1: function ($tNewResult, continuationFunction) {
		return $.ig.Task.prototype.continueWith$1.call(this, $tNewResult, continuationFunction);
	}
	,
	$type: new $.ig.Type('Task$1', $.ig.Task.prototype.$type)
}, true);

$.ig.util.defType('TaskCompletionSource$1', 'Object', {
	$tResult: null,
	_deferred: null,
	deferred: function (value) {
		if (arguments.length === 1) {
			this._deferred = value;
			return value;
		} else {
			return this._deferred;
		}
	}
	,
	init: function ($tResult, initNumber) {
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tResult);
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.TaskCompletionSource$1.prototype.init1.call(this, this.$tResult, 1, null);
	},
	init1: function ($tResult, initNumber, state) {
		this.$tResult = $tResult;
		this.$type = this.$type.specialize(this.$tResult);
		$.ig.Object.prototype.init.call(this);
		this.deferred($.ig.util.deferred());
		this.task(new $.ig.Task$1(this.$tResult, this.deferred().promise(), state));
	},
	setCanceled: function () {
		this.deferred().reject(null);
	}
	,
	setException: function (exception) {
		this.deferred().reject(exception);
	}
	,
	setResult: function (result) {
		this.deferred().resolve($.ig.util.getBoxIfEnum(this.$tResult, result));
	}
	,
	_task: null,
	task: function (value) {
		if (arguments.length === 1) {
			this._task = value;
			return value;
		} else {
			return this._task;
		}
	}
	,
	$type: new $.ig.Type('TaskCompletionSource$1', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TaskFactory', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	continueWhenAll: function (tasks, continuationAction) {
		return new $.ig.Task(0, this.whenAll(this.getPromises(tasks)).always(function () { continuationAction(tasks); }));
	}
	,
	continueWhenAll$1: function ($tResult, tasks, continuationFunction) {
		var tcs = new $.ig.TaskCompletionSource$1($tResult, 0);
		this.whenAll(this.getPromises(tasks)).always(function () {
			try {
				var result = continuationFunction(tasks);
				tcs.setResult(result);
			}
			catch (e_) {
				tcs.setException(e_);
			}
		});
		return tcs.task();
	}
	,
	continueWhenAll$2: function ($tAntecedentResult, $tResult, tasks, continuationFunction) {
		var wrapperFunction = function (t) { return $.ig.util.getBoxIfEnum($tResult, continuationFunction(t)); };
		return this.continueWhenAll$1($.ig.Object.prototype.$type, tasks, wrapperFunction).continueWith$11($tResult, function (t) { return $.ig.util.castObjTo$t($tResult, t.result()); });
	}
	,
	getPromises: function (tasks) {
		var promises = new Array(tasks.length);
		for (var i = 0; i < tasks.length; i++) {
			promises[i] = tasks[i].promise();
		}
		return promises;
	}
	,
	whenAll: function (promises) {
		var promises_ = promises;
		var whenPromise = (function (p) {
    //can't use $.when as it rejects the master Deferred as soon as one of the Deferreds is rejected
    //and we need to wait for all to complete
    function whenAll() {
	    var whenDeffered = $.ig.util.deferred();

	    if (arguments.length === 0) {
		    whenDeffered.resolve();
		    return whenDeffered;
	    }
   
	    var count = arguments.length;
	    var resolve = true;
	
	    for (var i = 0; i < arguments.length; i++) {
		    arguments[i].done(
			    function () {
				    resolve = resolve && true;
			    }).fail(
			    function () {
				    resolve = resolve && false;
			    }
		    ).always(function () {
			    count--;
			    if (count <= 0) {
				    if (resolve) {
					    whenDeffered.resolve();
				    } else {
					    whenDeffered.reject();
				    }
			    }
		    });
	    }
	
	    return whenDeffered;
    }
	return whenAll.apply($, p);
})(promises_);;
		return whenPromise;
	}
	,
	$type: new $.ig.Type('TaskFactory', $.ig.Object.prototype.$type)
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

$.ig.util.defType('Uri', 'Object', {
	init: function (initNumber, uri) {
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
		$.ig.Uri.prototype.init2.call(this, 2, uri, $.ig.UriKind.prototype.absolute, true);
	},
	init1: function (initNumber, uriString, uriKind) {
		$.ig.Uri.prototype.init2.call(this, 2, uriString, uriKind, true);
	},
	init2: function (initNumber, uriString, uriKind, validate) {
		$.ig.Object.prototype.init.call(this);
		this.value(uriString);
	},
	_value: null,
	value: function (value) {
		if (arguments.length === 1) {
			this._value = value;
			return value;
		} else {
			return this._value;
		}
	}
	,
	isAbsoluteUri: function () {
		var value = this.value();
		var length = value.length;
		if (length != 0 && $.ig.util.isLetter(value.charAt(0))) {
			for (var i = 1; i < length; i++) {
				var current = value.charAt(i);
				if (current == ':') {
					return true;
				}
				if (current != '+' && current != '-' && current != '.' && !$.ig.util.isLetterOrDigit(current)) {
					break;
				}
			}
		}
		return false;
	}
	,
	scheme: function () {
		var value = this.value();
		var length = value.length;
		if (length != 0 && $.ig.util.isLetter(value.charAt(0))) {
			for (var i = 1; i < length; i++) {
				var current = value.charAt(i);
				if (current == ':') {
					return value.substr(0, i);
				}
				if (current != '+' && current != '-' && current != '.' && !$.ig.util.isLetterOrDigit(current)) {
					break;
				}
			}
		}
		throw new $.ig.InvalidOperationException(1, "The scheme cannot be obtained from a relative Uri.");
	}
	,
	encodeURIComponent: function (stringToEscape) {
		return null;
	}
	,
	escapeUriString: function (stringToEscape) {
		if (/^([A-Z]:)|(\\\\)/i.test(stringToEscape)) {
			return stringToEscape;
		}
		return encodeURI(stringToEscape);
	}
	,
	tryCreate: function (uriString, uriKind, result) {
		if ($.ig.Uri.prototype.isWellFormedUriString(uriString, uriKind)) {
			result = new $.ig.Uri(2, uriString, uriKind, false);
			return {
				ret: true,
				p2: result
			};
		}
		result = null;
		return {
			ret: false,
			p2: result
		};
	}
	,
	isWellFormedUriString: function (uriString, uriKind) {
		switch (uriKind) {
			case $.ig.UriKind.prototype.absolute:
				if (!/^(((http|ftp|https):\/\/[\w-]+(\.[\w-]*)+)|(file:\/\/\/?))([\w\\$()!'.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?$/i.test(uriString)) {
					return false;
				}
				break;
			case $.ig.UriKind.prototype.relative:
				if (!/^([\w\\$()!'.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?$/i.test(uriString)) {
					return false;
				}
				break;
			default:
			case $.ig.UriKind.prototype.relativeOrAbsolute:
				if (!/^((((http|ftp|https):\/\/[\w-]+(\.[\w-]*)+)|(file:\/\/\/?)))?([\w\\$()!'.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?$/i.test(uriString)) {
					return false;
				}
				break;
		}
		return true;
	}
	,
	toString: function () {
		if (/^([A-Z]:)|(\\\\)/i.test(this.value())) {
			return this.value();
		}
		var result = decodeURI(this.value());
		if (/^(http|ftp|https):\/\/[\w-]+(\.[\w-]*)+?$/i.test(result)) {
			result += "/";
		}
		return result;
	}
	,
	init3: function (initNumber, baseUri, relativeUri) {
		$.ig.Object.prototype.init.call(this);
		throw new $.ig.NotImplementedException(0);
	},
	absolutePath: function () {
		throw new $.ig.NotImplementedException(0);
		return null;
	}
	,
	absoluteUri: function () {
		throw new $.ig.NotImplementedException(0);
		return null;
	}
	,
	localPath: function () {
		var result = /^(((http|ftp|https):\/\/[\w-]+(\.[\w-]*)+)|(file:\/\/\/?))([\w\\$()!'.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?$/i.exec(this.value());
		if (result == null) {
			throw new $.ig.InvalidOperationException(0);
		}
		if (result[6] != null) {
			return decodeURI(result[6]);
		}
		return "/";
	}
	,
	originalString: function () {
		return this.value();
	}
	,
	isWellFormedOriginalString: function () {
		throw new $.ig.NotImplementedException(0);
		return false;
	}
	,
	$type: new $.ig.Type('Uri', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('JavaScriptSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserializeObject: function (input) {
		var text_ = input;
		return JSON.parse(text_);
	}
	,
	serialize: function (obj) {
		var value_ = obj;
		return JSON.stringify(value_);
	}
	,
	$type: new $.ig.Type('JavaScriptSerializer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XObject', 'Object', {
	init: function (node) {
		$.ig.Object.prototype.init.call(this);
		this.backingNode(node);
	},
	_backingNode: null,
	backingNode: function (value) {
		if (arguments.length === 1) {
			this._backingNode = value;
			return value;
		} else {
			return this._backingNode;
		}
	}
	,
	nodeType: function () {
	}
	,
	toString: function () {
		if (this.backingNode() != null) {
			return $.ig.XmlUtils.prototype.xmlNodeToString(this.backingNode());
		} else {
			return "";
		}
	}
	,
	$type: new $.ig.Type('XObject', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XAttribute', 'XObject', {
	init: function (node) {
		$.ig.XObject.prototype.init.call(this, node);
	},
	attachToElement: function (element) {
		this.backingNode(this.backingNode().cloneNode(true));
		element.setAttributeNode(this.backingNode());
	}
	,
	nodeType: function () {
		return $.ig.XmlNodeType.prototype.attribute;
	}
	,
	value: function () {
		return $.ig.XmlUtils.prototype.getAttributeValue(this.backingNode());
	}
	,
	$type: new $.ig.Type('XAttribute', $.ig.XObject.prototype.$type)
}, true);

$.ig.util.defType('XNode', 'XObject', {
	init: function (node) {
		$.ig.XObject.prototype.init.call(this, node);
	},
	$type: new $.ig.Type('XNode', $.ig.XObject.prototype.$type)
}, true);

$.ig.util.defType('XContainer', 'XNode', {
	init: function (node) {
		$.ig.XNode.prototype.init.call(this, node);
	},
	element: function (name) {
		var element = null;
		var backingNode = this.backingNode();
		var children = backingNode.childNodes;
		var childrenCount = children.length;
		var localName = name.localName();
		var namespaceName = name.namespaceName();
		for (var i = 0; i < childrenCount; i++) {
			var currNode = children.item(i);
			if (currNode.namespaceURI == namespaceName && currNode.nodeType == $.ig.XmlNodeType.prototype.element && $.ig.XmlUtils.prototype.getLocalName(currNode) == localName) {
				element = currNode;
			}
		}
		if (element == null) {
			return null;
		} else {
			return new $.ig.XElement(0, element);
		}
	}
	,
	elements: function () {
		return this.elementsImpl(null);
	}
	,
	elements1: function (name) {
		return this.elementsImpl(name);
	}
	,
	elementsImpl: function (name) {
		var elements = new $.ig.List$1($.ig.XElement.prototype.$type, 0);
		for (var i = 0; i < this.backingNode().childNodes.length; i++) {
			var currNode = this.backingNode().childNodes.item(i);
			if (currNode.nodeType == $.ig.XmlNodeType.prototype.element) {
				var match = false;
				if (name != null) {
					if ($.ig.XmlUtils.prototype.getLocalName(currNode) == name.localName() && currNode.namespaceURI == name.namespaceName()) {
						match = true;
					}
				} else {
					match = true;
				}
				if (match) {
					elements.add(new $.ig.XElement(0, currNode));
				}
			}
		}
		return elements;
	}
	,
	add: function (content) {
		var node = this.backingNode();
		var doc;
		if ($.ig.util.cast($.ig.XDocument.prototype.$type, this) !== null) {
			doc = this.backingNode();
		} else {
			doc = this.backingNode().ownerDocument;
		}
		if ($.ig.util.cast($.ig.XAttribute.prototype.$type, content) !== null) {
			(content).attachToElement(node);
		} else if ($.ig.util.cast($.ig.XElement.prototype.$type, content) !== null) {
			(content).attachToNode(node, doc);
		}
	}
	,
	$type: new $.ig.Type('XContainer', $.ig.XNode.prototype.$type)
}, true);

$.ig.util.defType('XDocument', 'XContainer', {
	init: function (initNumber, doc) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.XContainer.prototype.init.call(this, doc);
	},
	init1: function (initNumber) {
		$.ig.XContainer.prototype.init.call(this, $.ig.XmlUtils.prototype.createDocument());
	},
	nodeType: function () {
		return $.ig.XmlNodeType.prototype.document;
	}
	,
	parse: function (text) {
		return new $.ig.XDocument(0, $.ig.XmlUtils.prototype.parseXml(text));
	}
	,
	$type: new $.ig.Type('XDocument', $.ig.XContainer.prototype.$type)
}, true);

$.ig.util.defType('XElement', 'XContainer', {
	init: function (initNumber, element) {
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
		$.ig.XContainer.prototype.init.call(this, element);
	},
	init1: function (initNumber, name) {
		$.ig.XElement.prototype.init2.call(this, 2, name, null);
	},
	init2: function (initNumber, name, content) {
		$.ig.XContainer.prototype.init.call(this, $.ig.XmlUtils.prototype.createDetachedElement(name.localName(), name.namespaceName()));
		var value = content == null ? "" : content.toString();
		this.value(value);
	},
	value: function (value) {
		if (arguments.length === 1) {
			$.ig.XmlUtils.prototype.setInnerText(this.backingNode(), value);
			return value;
		} else {
			return $.ig.XmlUtils.prototype.getInnerText(this.backingNode());
		}
	}
	,
	nodeType: function () {
		return $.ig.XmlNodeType.prototype.element;
	}
	,
	name: function () {
		return $.ig.XName.prototype.get($.ig.XmlUtils.prototype.getLocalName(this.backingNode()), this.backingNode().namespaceURI);
	}
	,
	attribute: function (name) {
		return new $.ig.XAttribute($.ig.XmlUtils.prototype.getAttributeNode(this.backingNode(), name.localName(), name.namespaceName()));
	}
	,
	attachToNode: function (node, doc) {
		if (this.backingNode().ownerDocument != doc) {
			this.backingNode($.ig.XmlUtils.prototype.importNode(doc, this.backingNode()));
		}
		node.appendChild(this.backingNode());
	}
	,
	$type: new $.ig.Type('XElement', $.ig.XContainer.prototype.$type)
}, true);

$.ig.util.defType('XmlUtils', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	isW3CDom: function () {
		return !!window.DOMParser;
	}
	,
	parseW3CDom: function (text) {
		var text_ = text;
		return (new DOMParser()).parseFromString(text_, 'text/xml');
	}
	,
	parseMSXmlDom: function (text) {
		var text_ = text;
		return (function (xml) {
    var xmlDoc = new ActiveXObject('Microsoft.XMLDOM');
    xmlDoc.async = false;
    xmlDoc.loadXML(xml);
    return xmlDoc;
})(text_);;
	}
	,
	w3CDomNodeToString: function (node) {
		var node_ = node;
		return (new XMLSerializer()).serializeToString(node_);
	}
	,
	mSXmlDomNodeToString: function (node) {
		var node_ = node;
		return node_.xml;
	}
	,
	createMSXmlDom: function () {
		return new ActiveXObject('Microsoft.XMLDOM');
	}
	,
	parseXml: function (text) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return $.ig.XmlUtils.prototype.parseW3CDom(text);
		} else {
			return $.ig.XmlUtils.prototype.parseMSXmlDom(text);
		}
	}
	,
	xmlNodeToString: function (node) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return $.ig.XmlUtils.prototype.w3CDomNodeToString(node);
		} else {
			return $.ig.XmlUtils.prototype.mSXmlDomNodeToString(node);
		}
	}
	,
	createDocument: function () {
		var doc;
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			doc = $.ig.XmlUtils.prototype.parseW3CDom("<dummy/>");
			doc.removeChild(doc.documentElement);
		} else {
			doc = $.ig.XmlUtils.prototype.createMSXmlDom();
		}
		return doc;
	}
	,
	createDetachedElement: function (localName, namespaceName) {
		var doc_ = $.ig.XmlUtils.prototype.createDocument();
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return doc_.createElementNS(namespaceName, localName);
		} else {
			var name_ = localName;
			var namespaceURI_ = namespaceName;
			return doc_.createNode(1/*NODE_ELEMENT*/, name_, namespaceURI_);
		}
	}
	,
	getInnerText: function (node) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return node.textContent;
		} else {
			var node_ = node;
			return node_.text;
		}
	}
	,
	setInnerText: function (node, text) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			node.textContent = text;
		} else {
			var node_ = node;
			var text_ = text;
			node_.text = text_;
		}
	}
	,
	importNode: function (doc, node) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return doc.importNode(node, true);
		} else {
			return node;
		}
	}
	,
	getLocalName: function (node) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return node.localName;
		} else {
			var node_ = node;
			return node_.baseName;
		}
	}
	,
	getAttributeNode: function (element, localName, namespaceName) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return element.getAttributeNodeNS(namespaceName, localName);
		} else {
			var elem_ = element;
			var ln_ = localName;
			var nn_ = namespaceName;
			return elem_.attributes.getQualifiedItem(ln_, nn_);
		}
	}
	,
	getAttributeValue: function (attr) {
		if ($.ig.XmlUtils.prototype.isW3CDom()) {
			return attr.nodeValue;
		} else {
			var attr_ = attr;
			return attr_.value;
		}
	}
	,
	$type: new $.ig.Type('XmlUtils', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XName', 'Object', {
	__localName: null,
	__namespaceName: null,
	init: function (localName, namespaceName) {
		$.ig.Object.prototype.init.call(this);
		this.__localName = localName;
		this.__namespaceName = namespaceName;
	},
	localName: function () {
		return this.__localName;
	}
	,
	namespaceName: function () {
		return this.__namespaceName;
	}
	,
	namespace: function () {
		return $.ig.XNamespace.prototype.get(this.__namespaceName);
	}
	,
	get: function (localName, namespaceName) {
		return new $.ig.XName(localName, namespaceName);
	}
	,
	$type: new $.ig.Type('XName', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XNamespace', 'Object', {
	__namespaceName: null,
	init: function (namespaceName) {
		$.ig.Object.prototype.init.call(this);
		this.__namespaceName = namespaceName;
	},
	get: function (namespaceName) {
		return new $.ig.XNamespace(namespaceName);
	}
	,
	xmlns: function () {
		return $.ig.XNamespace.prototype.get("http://www.w3.org/2000/xmlns/");
	}
	,
	namespaceName: function () {
		return this.__namespaceName;
	}
	,
	$type: new $.ig.Type('XNamespace', $.ig.Object.prototype.$type)
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

$.ig.UriKind.prototype.relativeOrAbsolute = 0;
$.ig.UriKind.prototype.absolute = 1;
$.ig.UriKind.prototype.relative = 2;

$.ig.TaskStatus.prototype.created = 0;
$.ig.TaskStatus.prototype.ranToCompletion = 5;
$.ig.TaskStatus.prototype.canceled = 6;
$.ig.TaskStatus.prototype.faulted = 7;

$.ig.ListSortDirection.prototype.ascending = 0;
$.ig.ListSortDirection.prototype.descending = 1;

$.ig.NotifyCollectionChangedAction.prototype.add = 0;
$.ig.NotifyCollectionChangedAction.prototype.remove = 1;
$.ig.NotifyCollectionChangedAction.prototype.replace = 2;
$.ig.NotifyCollectionChangedAction.prototype.reset = 4;

$.ig.Encoding.prototype.__utfEncoding = null;
$.ig.Encoding.prototype.__utf8Unmarked = null;
$.ig.Encoding.prototype.__unicodeEncoding = null;
$.ig.Encoding.prototype.__asciiEncoding = null;
$.ig.Encoding.prototype.__bigEndianUnicodeEncoding = null;

$.ig.Uri.prototype.schemeDelimiter = "://";

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["HeaderCellsLayoutOrientation:a", 
"Enum:b", 
"ValueType:c", 
"Object:d", 
"Type:e", 
"Boolean:f", 
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
"NotSupportedException:s", 
"Error:t", 
"Void:u", 
"Number:v", 
"String:w", 
"StringComparison:x", 
"Array:y", 
"IList:z", 
"ICollection:aa", 
"IEnumerable:ab", 
"IEnumerator:ac", 
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
"LevelSpanManager:ba", 
"IDictionary$2:bb", 
"ICollection$1:bc", 
"SpanSource:bd", 
"Dictionary$2:be", 
"IDictionary:bf", 
"Func$2:bg", 
"MulticastDelegate:bh", 
"IntPtr:bi", 
"KeyValuePair$2:bj", 
"Enumerable:bk", 
"Thread:bl", 
"ThreadStart:bm", 
"Func$3:bn", 
"IList$1:bo", 
"IOrderedEnumerable$1:bp", 
"SortedList$1:bq", 
"List$1:br", 
"IArray:bs", 
"Script:bt", 
"IArrayList:bu", 
"Array:bv", 
"CompareCallback:bw", 
"Action$1:bx", 
"Comparer$1:by", 
"IComparer:bz", 
"IComparer$1:b0", 
"DefaultComparer$1:b1", 
"Comparison$1:b2", 
"ReadOnlyCollection$1:b3", 
"Predicate$1:b4", 
"NotImplementedException:b5", 
"Math:b6", 
"ArgumentNullException:b7", 
"IEqualityComparer$1:b8", 
"EqualityComparer$1:b9", 
"IEqualityComparer:ca", 
"DefaultEqualityComparer$1:cb", 
"InvalidOperationException:cc", 
"ArgumentException:cd", 
"ITableViewHeadersFactory:ce", 
"OlapTableViewHeaderCell:cf", 
"TableViewHeadersFactory:cg", 
"OlapResultAxis:ch", 
"PositionResolver$2:ci", 
"IPosition$2:cj", 
"IPositionItem$2:ck", 
"PositionItemInfo$2:cl", 
"IHierarchicalPositionItem$2:cm", 
"EventHandler$1:cn", 
"AsyncCompletedEventArgs:co", 
"EventArgs:cp", 
"Delegate:cq", 
"Interlocked:cr", 
"ListSortDirection:cs", 
"Nullable$1:ct", 
"OlapResultTuple:cu", 
"OlapResultAxisMember:cv", 
"ArrayListCollection$1:cw", 
"INotifyCollectionChanged:cx", 
"NotifyCollectionChangedEventHandler:cy", 
"NotifyCollectionChangedEventArgs:cz", 
"NotifyCollectionChangedAction:c0", 
"TableViewHeaderCellSource:c1", 
"TableSuperCompactViewHeadersFactory:c2", 
"TreeStackAttributesBehavior:c3", 
"OlapTableViewTreeHeaderCell:c4", 
"TreeStackNextDimensionFirstBehavior:c6", 
"TableViewTreeHeadersFactory:c7", 
"Stack$1:c8", 
"ReverseArrayEnumerator$1:c9", 
"TableViewImpl:da", 
"TableViewSettings:db", 
"RowHeaderLayout:dc", 
"OlapResult:dd", 
"OlapResultCell:de", 
"OlapTableViewResultCell:df", 
"TupleSortDirection:dg", 
"LevelSortDirection:dh", 
"LevelSortBehavior:di", 
"ResultHeaderSorter:dj", 
"ResultSorter$1:dk", 
"AxisTupleVisitor:dl", 
"AxisHeaderSortingVisitor:dm", 
"ResultColumnValueSorter:dn", 
"AxisValueSortingVisitor:dp", 
"TreeLayoutTableViewSettings:dq", 
"OlapResultView:dr", 
"ResultViewHelper:ds", 
"IOlapDataSource:dt", 
"OlapMetadataTreeItem:du", 
"ICoreOlapElement:dv", 
"OlapMetadataTreeItemType:dw", 
"Hierarchy:dx", 
"HierarchyOrigin:dy", 
"Measure:dz", 
"AggregatorType:d0", 
"MeasureListLocation:d1", 
"Cube:d2", 
"CubeType:d3", 
"Task$1:d4", 
"Task:d5", 
"JQueryPromise:d6", 
"Action:d7", 
"AggregateException:d8", 
"TaskStatus:d9", 
"TaskCompletionSource$1:ea", 
"JQueryDeferred:eb", 
"JQuery:ec", 
"JQueryObject:ed", 
"Element:ee", 
"ElementAttributeCollection:ef", 
"ElementCollection:eg", 
"WebStyle:eh", 
"ElementNodeType:ei", 
"Document:ej", 
"EventListener:ek", 
"IElementEventHandler:el", 
"ElementEventHandler:em", 
"ElementAttribute:en", 
"JQueryPosition:eo", 
"JQueryCallback:ep", 
"JQueryEvent:eq", 
"JQueryUICallback:er", 
"Member:es", 
"MemberType:et", 
"OlapDataSource:eu", 
"Catalog:ev", 
"MeasureGroup:ew", 
"DataSourceBaseOptions:ex", 
"IOlapDataProviderFactory:ey", 
"IOlapDiscoverDataProvider:ez", 
"KeyValueItem:e0", 
"Dimension:e1", 
"DimensionType:e2", 
"Level:e3", 
"MeasureGroupDimension:e4", 
"CardinalityType:e5", 
"Kpi:e6", 
"CubeMetaItemsCache:e7", 
"KpiMeasure:e8", 
"MeasureGroupMetaItemsCache:e9", 
"Tuple$2:fa", 
"AxisElement:fb", 
"PositionInfo:fc", 
"MeasureList:fd", 
"TaskFactory:fe", 
"TaskExtensions:ff", 
"AxisDefinitionParser:fg", 
"Debug:fh", 
"MetaTreeHelper:fi", 
"HierarchyItemPosition:fj", 
"KpiDimension:fk", 
"CoreOlapElementParser:fo", 
"HierarchyFilterView:fx", 
"FilterMember:fy", 
"INotifyPropertyChanged:fz", 
"PropertyChangedEventHandler:f0", 
"PropertyChangedEventArgs:f1", 
"FilterMemberStateChangedEventArgs:f2", 
"DataSourceBase:f3", 
"jQueryUtils:f4", 
"OlapUtilities:f5", 
"OlapTableView:f6", 
"IConnection:f9", 
"IOlapExecuteCommandProvider:ga", 
"AbstractEnumerable:gc", 
"Func$1:gd", 
"AbstractEnumerator:ge", 
"GenericEnumerable$1:gf", 
"GenericEnumerator$1:gg"]);


$.ig.util.defType('OlapMetadataTreeItemType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Cube";
			case 1: return "Dimension";
			case 2: return "Group";
			case 3: return "UserDefinedHierarchy";
			case 4: return "SystemEnabledHierarchy";
			case 5: return "ParentChildHierarchy";
			case 6: return "Measure";
			case 7: return "Level1";
			case 8: return "Level2";
			case 9: return "Level3";
			case 10: return "Level4";
			case 11: return "Level5";
			case 12: return "KpiRoot";
			case 13: return "Kpi";
			case 14: return "KpiValue";
			case 15: return "KpiGoal";
			case 16: return "KpiStatus";
			case 17: return "KpiTrend";
			case 18: return "KpiWeight";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('OlapMetadataTreeItemType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('MeasureListLocation', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Rows";
			case 1: return "Columns";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('MeasureListLocation', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('MemberType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Unknown";
			case 1: return "Regular";
			case 2: return "All";
			case 3: return "Measure";
			case 4: return "Formula";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('MemberType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('CardinalityType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "One";
			case 1: return "Many";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('CardinalityType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('AggregatorType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Unknown";
			case 1: return "Sum";
			case 2: return "Count";
			case 3: return "Min";
			case 4: return "Max";
			case 5: return "Average";
			case 6: return "Variance";
			case 7: return "Std";
			case 8: return "DistinctCount";
			case 9: return "None";
			case 10: return "AverageOfChildren";
			case 13: return "FirstNonEmpty";
			case 14: return "LastNonEmpty";
			case 15: return "ByAccount";
			case 127: return "Calculated";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('AggregatorType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HierarchyOrigin', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: (function () {
		function getNameSingle(v) {
			switch (v) {
				case 1: return "UserDefined";
				case 2: return "SystemEnabled";
				case 4: return "SystemInternal";
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
	$type: new $.ig.Type('HierarchyOrigin', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('DimensionType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Unknown";
			case 1: return "Time";
			case 2: return "Measure";
			case 3: return "Other";
			case 5: return "Quantitative";
			case 6: return "Accounts";
			case 7: return "Customers";
			case 8: return "Products";
			case 9: return "Scenario";
			case 10: return "Utility";
			case 11: return "Currency";
			case 12: return "Rates";
			case 13: return "Channel";
			case 14: return "Promotion";
			case 15: return "Organization";
			case 16: return "BillOfMaterials";
			case 17: return "Geography";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('DimensionType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('CubeType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Cube";
			case 1: return "Dimension";
			case 2: return "Unknown";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('CubeType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('LevelSortBehavior', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Alphabetical";
			case 1: return "System";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('LevelSortBehavior', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('RowHeaderLayout', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Standard";
			case 1: return "SuperCompact";
			case 2: return "Tree";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RowHeaderLayout', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HeaderCellsLayoutOrientation', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Horizontal";
			case 1: return "Vertical";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('HeaderCellsLayoutOrientation', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('LevelSpanManager', 'Object', {
	__spanSourceTable: null,
	init: function (positionSize) {
		$.ig.Object.prototype.init.call(this);
		this.positionSize(positionSize);
		this.__spanSourceTable = new Array(positionSize);
	},
	_positionSize: 0,
	positionSize: function (value) {
		if (arguments.length === 1) {
			this._positionSize = value;
			return value;
		} else {
			return this._positionSize;
		}
	}
	,
	getSpanSource: function (positionItemIndex, positionItemDepth) {
		var positionIndexSpanSources = this.__spanSourceTable[positionItemIndex];
		if (positionIndexSpanSources == null) {
			positionIndexSpanSources = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.SpanSource.prototype.$type, 0);
			this.__spanSourceTable[positionItemIndex] = positionIndexSpanSources;
		}
		var positionSpanSource;
		if (!(function () { var $ret = positionIndexSpanSources.tryGetValue(positionItemDepth, positionSpanSource); positionSpanSource = $ret.p1; return $ret.ret; }())) {
			positionSpanSource = new $.ig.SpanSource();
			if (positionItemDepth > 0) {
				var parentSpanSource;
				if ((function () { var $ret = positionIndexSpanSources.tryGetValue(positionItemDepth - 1, parentSpanSource); parentSpanSource = $ret.p1; return $ret.ret; }())) {
					positionSpanSource.parent(parentSpanSource);
					parentSpanSource.increaseSpanForChildren();
				}
			}
			positionIndexSpanSources.add(positionItemDepth, positionSpanSource);
		}
		return positionSpanSource;
	}
	,
	$type: new $.ig.Type('LevelSpanManager', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SpanSource', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__spanValue = 1;
	},
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
	__spanValue: 0,
	spanValue: function (value) {
		if (arguments.length === 1) {
			if (this.__spanValue != value) {
				var diff = value - this.__spanValue;
				if (this.parent() != null) {
					this.parent().spanValue(this.parent().spanValue() + diff);
				}
				this.__spanValue = value;
			}
			return value;
		} else {
			return this.__spanValue;
		}
	}
	,
	__hasChildren: false,
	increaseSpanForChildren: function () {
		if (!this.__hasChildren) {
			this.__hasChildren = true;
			this.spanValue(this.spanValue() + 1);
		}
	}
	,
	$type: new $.ig.Type('SpanSource', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ITableViewHeadersFactory', 'Object', {
	$type: new $.ig.Type('ITableViewHeadersFactory', null)
}, true);

$.ig.util.defType('TableViewHeadersFactory', 'Object', {
	_sourceAxis: null,
	sourceAxis: function (value) {
		if (arguments.length === 1) {
			this._sourceAxis = value;
			return value;
		} else {
			return this._sourceAxis;
		}
	}
	,
	_originalAxis: null,
	originalAxis: function (value) {
		if (arguments.length === 1) {
			this._originalAxis = value;
			return value;
		} else {
			return this._originalAxis;
		}
	}
	,
	_layoutOrientation: 0,
	layoutOrientation: function (value) {
		if (arguments.length === 1) {
			this._layoutOrientation = value;
			return value;
		} else {
			return this._layoutOrientation;
		}
	}
	,
	_isParentInFront: false,
	isParentInFront: function (value) {
		if (arguments.length === 1) {
			this._isParentInFront = value;
			return value;
		} else {
			return this._isParentInFront;
		}
	}
	,
	_orderedTuples: null,
	orderedTuples: function (value) {
		if (arguments.length === 1) {
			this._orderedTuples = value;
			return value;
		} else {
			return this._orderedTuples;
		}
	}
	,
	init: function (sourceAxis, originalAxis, layoutOrientation, isParentInFront, orderedTuples) {
		$.ig.Object.prototype.init.call(this);
		this.sourceAxis(sourceAxis);
		this.originalAxis(originalAxis);
		this.layoutOrientation(layoutOrientation);
		this.isParentInFront(isParentInFront);
		this.orderedTuples(orderedTuples);
	},
	createHeaderCells: function () {
		var $self = this;
		if (this.sourceAxis().positionResolver().hasUnregisterdPositions()) {
			this.sourceAxis().positionResolver().completeRegisterPositions();
		}
		var rootPositionInfo = this.sourceAxis().positionResolver().rootPositionInfo();
		var headerCellCollections = new $.ig.List$1($.ig.IList$1.prototype.$type.specialize($.ig.TableViewHeaderCellSource.prototype.$type), 0);
		for (var i = 0; i < rootPositionInfo.positionSize(); i++) {
			headerCellCollections.add(new $.ig.List$1($.ig.TableViewHeaderCellSource.prototype.$type, 0));
		}
		$.ig.TableViewHeadersFactory.prototype.createHeaderCells2(rootPositionInfo, rootPositionInfo.positionItemDepthMin(), headerCellCollections, this.isParentInFront(), this.orderedTuples());
		var result = new $.ig.ArrayListCollection$1($.ig.OlapTableViewHeaderCell.prototype.$type);
		var levelDepthOffset = 0;
		for (var i1 = 0; i1 < rootPositionInfo.positionSize(); i1++) {
			var headerCellsCollection = headerCellCollections.item(i1);
			var directionIndex = 0;
			var maxLevelDepth = 0;
			var en = headerCellsCollection.getEnumerator();
			while (en.moveNext()) {
				var cellSource = en.current();
				var columnIndex;
				var columnSpan;
				var rowIndex;
				var rowSpan;
				if (this.layoutOrientation() == $.ig.HeaderCellsLayoutOrientation.prototype.horizontal) {
					columnIndex = directionIndex;
					columnSpan = cellSource.headerSpanSource().spanValue();
					rowIndex = cellSource.positionItemInfo().positionItemDepth() + levelDepthOffset;
					rowSpan = cellSource.levelSpanSource().spanValue();
				} else {
					rowIndex = directionIndex;
					rowSpan = cellSource.headerSpanSource().spanValue();
					columnIndex = cellSource.positionItemInfo().positionItemDepth() + levelDepthOffset;
					columnSpan = cellSource.levelSpanSource().spanValue();
				}
				var position = this.sourceAxis().tuples().__inner[cellSource.positionItemInfo().positionIndex()];
				var positionItem = position.item(cellSource.positionItemInfo().positionItemIndex());
				var tuple = this.sourceAxis().tuples().__inner[cellSource.tupleIndex()];
				if (this.originalAxis().positionResolver().hasUnregisterdPositions()) {
					this.originalAxis().positionResolver().completeRegisterPositions();
				}
				var originalIndex = this.originalAxis().positionResolver().getPositionIndex(tuple);
				var headerCell = (function () {
					var $ret = new $.ig.OlapTableViewHeaderCell();
					$ret.axisName($self.sourceAxis().name());
					$ret.hierarchyNumber(cellSource.positionItemInfo().positionItemIndex());
					$ret.levelNumber(cellSource.positionItemInfo().positionItemDepth());
					$ret.caption(positionItem.caption());
					$ret.columnIndex(columnIndex);
					$ret.columnSpan(columnSpan);
					$ret.isExpanded(cellSource.isExpanded());
					$ret.isExpandable(cellSource.isExpandable());
					$ret.tupleIndex(originalIndex);
					$ret.memberIndex(cellSource.positionItemInfo().positionItemIndex());
					$ret.rowIndex(rowIndex);
					$ret.rowSpan(rowSpan);
					return $ret;
				}());
				if (!cellSource.isHeaderCellGroup()) {
					directionIndex = directionIndex + cellSource.headerSpanSource().spanValue();
				}
				maxLevelDepth = Math.max(maxLevelDepth, cellSource.positionItemInfo().positionItemDepth());
				result.add(headerCell);
			}
			levelDepthOffset += maxLevelDepth + 1;
		}
		return result;
	}
	,
	createHeaderCells2: function (hostPositionItemInfo, topMostPositionItemDetph, headerCellsCollections, isParentInFront, orderedTuples) {
		var spanManager = new $.ig.LevelSpanManager(hostPositionItemInfo.positionSize());
		var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == topMostPositionItemDetph; }).getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			var tableViewHeaderCell;
			var headerCellSpan;
			var $ret = $.ig.TableViewHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, positionItemInfo, headerCellsCollections, spanManager, isParentInFront, orderedTuples, tableViewHeaderCell, headerCellSpan);
			tableViewHeaderCell = $ret.p6;
			headerCellSpan = $ret.p7;
		}
	}
	,
	createHostedHeaderCells: function (positionItemInfo, headerCellCollections, spanManager, isParentInFront, headerCellSource, orderedTuples) {
		var hostedPosionsInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values(), function (pi) { return pi.positionItemDepth() == positionItemInfo.positionItemDepthMin(); }));
		if (hostedPosionsInfo.count() > 0) {
			var en = hostedPosionsInfo.getEnumerator();
			while (en.moveNext()) {
				var hostedItemInfo = en.current();
				var hostedHeaderCell;
				var hostedCellSpan;
				var $ret = $.ig.TableViewHeadersFactory.prototype.createHeaderCells1(positionItemInfo, hostedItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, hostedHeaderCell, hostedCellSpan);
				hostedHeaderCell = $ret.p6;
				hostedCellSpan = $ret.p7;
				headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + hostedCellSpan);
				if (hostedHeaderCell.isHeaderCellGroup()) {
					headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() - 1);
		} else {
			orderedTuples.add(positionItemInfo.positionIndex());
		}
		headerCellSource.tupleIndex(positionItemInfo.positionIndex());
	}
	,
	createHeaderCells1: function (hostPositionItemInfo, positionItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, headerCellSource, totalSpan) {
		var headerCellsTargetCollection = headerCellCollections.item(positionItemInfo.positionItemIndex());
		var levelSpanSource = spanManager.getSpanSource(positionItemInfo.positionItemIndex(), positionItemInfo.positionItemDepth());
		var grandTotalHeaderCellSource = (function () {
			var $ret = new $.ig.TableViewHeaderCellSource();
			$ret.levelSpanSource(levelSpanSource);
			$ret.positionItemInfo(positionItemInfo);
			$ret.parentPositionItemInfo(hostPositionItemInfo);
			$ret.isExpandable(positionItemInfo.isExpandable());
			return $ret;
		}());
		headerCellSource = grandTotalHeaderCellSource;
		if (isParentInFront) {
			headerCellsTargetCollection.add(grandTotalHeaderCellSource);
			$.ig.TableViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, true, grandTotalHeaderCellSource, orderedTuples);
		}
		var childrenPosionInfo = null;
		if (positionItemInfo.isExpanded()) {
			childrenPosionInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); }));
		}
		if (childrenPosionInfo != null && childrenPosionInfo.count() > 0) {
			var headerGroupCellSource = (function () {
				var $ret = new $.ig.TableViewHeaderCellSource();
				$ret.isHeaderCellGroup(true);
				$ret.positionItemInfo(positionItemInfo);
				$ret.isExpandable(positionItemInfo.isExpandable());
				return $ret;
			}());
			headerCellsTargetCollection.add(headerGroupCellSource);
			var en = childrenPosionInfo.getEnumerator();
			while (en.moveNext()) {
				var childItemInfo = en.current();
				var childHeaderCell;
				var childItemSpan;
				var $ret = $.ig.TableViewHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, childItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, childHeaderCell, childItemSpan);
				childHeaderCell = $ret.p6;
				childItemSpan = $ret.p7;
				headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + childItemSpan);
				if (childHeaderCell.isHeaderCellGroup()) {
					headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() - 1);
			grandTotalHeaderCellSource.isExpanded(true);
			grandTotalHeaderCellSource.isExpandable(false);
			headerGroupCellSource.isExpanded(true);
			grandTotalHeaderCellSource.headerCellGroup(headerGroupCellSource);
			if (!isParentInFront) {
				headerCellsTargetCollection.add(grandTotalHeaderCellSource);
				$.ig.TableViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, false, grandTotalHeaderCellSource, orderedTuples);
			}
			headerGroupCellSource.tupleIndex(grandTotalHeaderCellSource.tupleIndex());
			headerCellSource = headerGroupCellSource;
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue() + headerGroupCellSource.headerSpanSource().spanValue() - 1;
		} else {
			if (positionItemInfo.isExpanded()) {
				grandTotalHeaderCellSource.isExpandable(false);
			}
			if (!isParentInFront) {
				headerCellsTargetCollection.add(grandTotalHeaderCellSource);
				$.ig.TableViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, false, grandTotalHeaderCellSource, orderedTuples);
			}
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue();
		}
		return {
			p6: headerCellSource,
			p7: totalSpan
		};
	}
	,
	$type: new $.ig.Type('TableViewHeadersFactory', $.ig.Object.prototype.$type, [$.ig.ITableViewHeadersFactory.prototype.$type])
}, true);

$.ig.util.defType('TableSuperCompactViewHeadersFactory', 'Object', {
	_sourceAxis: null,
	sourceAxis: function (value) {
		if (arguments.length === 1) {
			this._sourceAxis = value;
			return value;
		} else {
			return this._sourceAxis;
		}
	}
	,
	_originalAxis: null,
	originalAxis: function (value) {
		if (arguments.length === 1) {
			this._originalAxis = value;
			return value;
		} else {
			return this._originalAxis;
		}
	}
	,
	_layoutOrientation: 0,
	layoutOrientation: function (value) {
		if (arguments.length === 1) {
			this._layoutOrientation = value;
			return value;
		} else {
			return this._layoutOrientation;
		}
	}
	,
	_isParentInFront: false,
	isParentInFront: function (value) {
		if (arguments.length === 1) {
			this._isParentInFront = value;
			return value;
		} else {
			return this._isParentInFront;
		}
	}
	,
	_orderedTuples: null,
	orderedTuples: function (value) {
		if (arguments.length === 1) {
			this._orderedTuples = value;
			return value;
		} else {
			return this._orderedTuples;
		}
	}
	,
	init: function (sourceAxis, originalAxis, layoutOrientation, isParentInFront, orderedTuples) {
		$.ig.Object.prototype.init.call(this);
		this.sourceAxis(sourceAxis);
		this.originalAxis(originalAxis);
		this.layoutOrientation(layoutOrientation);
		this.isParentInFront(isParentInFront);
		this.orderedTuples(orderedTuples);
	},
	createHeaderCells: function () {
		var $self = this;
		if (this.sourceAxis().positionResolver().hasUnregisterdPositions()) {
			this.sourceAxis().positionResolver().completeRegisterPositions();
		}
		var rootPositionInfo = this.sourceAxis().positionResolver().rootPositionInfo();
		var headerCellCollections = new $.ig.List$1($.ig.IList$1.prototype.$type.specialize($.ig.TableViewHeaderCellSource.prototype.$type), 0);
		for (var i = 0; i < rootPositionInfo.positionSize(); i++) {
			headerCellCollections.add(new $.ig.List$1($.ig.TableViewHeaderCellSource.prototype.$type, 0));
		}
		$.ig.TableSuperCompactViewHeadersFactory.prototype.createHeaderCells2(rootPositionInfo, rootPositionInfo.positionItemDepthMin(), headerCellCollections, this.isParentInFront(), this.orderedTuples());
		var result = new $.ig.ArrayListCollection$1($.ig.OlapTableViewHeaderCell.prototype.$type);
		var levelDepthOffset = 0;
		for (var i1 = 0; i1 < rootPositionInfo.positionSize(); i1++) {
			var headerCellsCollection = headerCellCollections.item(i1);
			var directionIndex = 0;
			var maxLevelDepth = 0;
			var en = headerCellsCollection.getEnumerator();
			while (en.moveNext()) {
				var cellSource = en.current();
				var columnIndex;
				var columnSpan;
				var rowIndex;
				var rowSpan;
				if (this.layoutOrientation() == $.ig.HeaderCellsLayoutOrientation.prototype.horizontal) {
					columnIndex = directionIndex;
					columnSpan = cellSource.headerSpanSource().spanValue();
					rowIndex = levelDepthOffset;
					rowSpan = 1;
				} else {
					rowIndex = directionIndex;
					rowSpan = cellSource.headerSpanSource().spanValue();
					columnIndex = levelDepthOffset;
					columnSpan = 1;
				}
				var position = this.sourceAxis().tuples().__inner[cellSource.positionItemInfo().positionIndex()];
				var positionItem = position.item(cellSource.positionItemInfo().positionItemIndex());
				var tuple = this.sourceAxis().tuples().__inner[cellSource.tupleIndex()];
				if (this.originalAxis().positionResolver().hasUnregisterdPositions()) {
					this.originalAxis().positionResolver().completeRegisterPositions();
				}
				var originalIndex = this.originalAxis().positionResolver().getPositionIndex(tuple);
				var headerCell = (function () {
					var $ret = new $.ig.OlapTableViewHeaderCell();
					$ret.axisName($self.sourceAxis().name());
					$ret.hierarchyNumber(cellSource.positionItemInfo().positionItemIndex());
					$ret.levelNumber(cellSource.positionItemInfo().positionItemDepth());
					$ret.caption(positionItem.caption());
					$ret.columnIndex(columnIndex);
					$ret.columnSpan(columnSpan);
					$ret.isExpanded(cellSource.isExpanded());
					$ret.isExpandable(cellSource.isExpandable());
					$ret.tupleIndex(originalIndex);
					$ret.memberIndex(cellSource.positionItemInfo().positionItemIndex());
					$ret.rowIndex(rowIndex);
					$ret.rowSpan(rowSpan);
					return $ret;
				}());
				if (!cellSource.isHeaderCellGroup()) {
					directionIndex = directionIndex + cellSource.headerSpanSource().spanValue();
				}
				result.add(headerCell);
			}
			levelDepthOffset += maxLevelDepth + 1;
		}
		return result;
	}
	,
	createHeaderCells2: function (hostPositionItemInfo, topMostPositionItemDetph, headerCellsCollections, isParentInFront, orderedTuples) {
		var spanManager = new $.ig.LevelSpanManager(hostPositionItemInfo.positionSize());
		var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == topMostPositionItemDetph; }).getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			var tableViewHeaderCell;
			var headerCellSpan;
			var $ret = $.ig.TableSuperCompactViewHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, positionItemInfo, headerCellsCollections, spanManager, isParentInFront, orderedTuples, tableViewHeaderCell, headerCellSpan);
			tableViewHeaderCell = $ret.p6;
			headerCellSpan = $ret.p7;
		}
	}
	,
	createHostedHeaderCells: function (positionItemInfo, headerCellCollections, spanManager, isParentInFront, headerCellSource, orderedTuples) {
		var hostedPosionsInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values(), function (pi) { return pi.positionItemDepth() == positionItemInfo.positionItemDepthMin(); }));
		if (hostedPosionsInfo.count() > 0) {
			var en = hostedPosionsInfo.getEnumerator();
			while (en.moveNext()) {
				var hostedItemInfo = en.current();
				var hostedHeaderCell;
				var hostedCellSpan;
				var $ret = $.ig.TableSuperCompactViewHeadersFactory.prototype.createHeaderCells1(positionItemInfo, hostedItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, hostedHeaderCell, hostedCellSpan);
				hostedHeaderCell = $ret.p6;
				hostedCellSpan = $ret.p7;
				headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + hostedCellSpan);
				if (hostedHeaderCell.headerCellGroup() != null) {
					headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() - 1);
		} else {
			orderedTuples.add(positionItemInfo.positionIndex());
		}
		headerCellSource.tupleIndex(positionItemInfo.positionIndex());
	}
	,
	createHeaderCells1: function (hostPositionItemInfo, positionItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, headerCellSource, totalSpan) {
		var headerCellsTargetCollection = headerCellCollections.item(positionItemInfo.positionItemIndex());
		var levelSpanSource = spanManager.getSpanSource(positionItemInfo.positionItemIndex(), positionItemInfo.positionItemDepth());
		var grandTotalHeaderCellSource = (function () {
			var $ret = new $.ig.TableViewHeaderCellSource();
			$ret.levelSpanSource(levelSpanSource);
			$ret.positionItemInfo(positionItemInfo);
			$ret.isExpandable(positionItemInfo.isExpandable());
			return $ret;
		}());
		headerCellSource = grandTotalHeaderCellSource;
		if (isParentInFront) {
			headerCellsTargetCollection.add(grandTotalHeaderCellSource);
			$.ig.TableSuperCompactViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, true, grandTotalHeaderCellSource, orderedTuples);
		}
		var childrenPosionInfo = null;
		if (positionItemInfo.isExpanded()) {
			childrenPosionInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); }));
		}
		if (childrenPosionInfo != null && childrenPosionInfo.count() > 0) {
			var headerGroupCellSource = (function () {
				var $ret = new $.ig.TableViewHeaderCellSource();
				$ret.isHeaderCellGroup(true);
				$ret.positionItemInfo(positionItemInfo);
				$ret.isExpandable(positionItemInfo.isExpandable());
				return $ret;
			}());
			var en = childrenPosionInfo.getEnumerator();
			while (en.moveNext()) {
				var childItemInfo = en.current();
				var childHeaderCell;
				var childItemSpan;
				var $ret = $.ig.TableSuperCompactViewHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, childItemInfo, headerCellCollections, spanManager, isParentInFront, orderedTuples, childHeaderCell, childItemSpan);
				childHeaderCell = $ret.p6;
				childItemSpan = $ret.p7;
				headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + childItemSpan);
				if (childHeaderCell.headerCellGroup() != null) {
					headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() - 1);
			grandTotalHeaderCellSource.isExpanded(true);
			grandTotalHeaderCellSource.isExpandable(true);
			grandTotalHeaderCellSource.headerCellGroup(headerGroupCellSource);
			if (!isParentInFront) {
				headerCellsTargetCollection.add(grandTotalHeaderCellSource);
				$.ig.TableSuperCompactViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, false, grandTotalHeaderCellSource, orderedTuples);
			}
			headerGroupCellSource.tupleIndex(grandTotalHeaderCellSource.tupleIndex());
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue() + headerGroupCellSource.headerSpanSource().spanValue() - 1;
		} else {
			if (positionItemInfo.isExpanded()) {
				grandTotalHeaderCellSource.isExpandable(false);
			}
			if (!isParentInFront) {
				headerCellsTargetCollection.add(grandTotalHeaderCellSource);
				$.ig.TableSuperCompactViewHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellCollections, spanManager, false, grandTotalHeaderCellSource, orderedTuples);
			}
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue();
		}
		return {
			p6: headerCellSource,
			p7: totalSpan
		};
	}
	,
	$type: new $.ig.Type('TableSuperCompactViewHeadersFactory', $.ig.Object.prototype.$type, [$.ig.ITableViewHeadersFactory.prototype.$type])
}, true);

$.ig.util.defType('TableViewHeaderCellSource', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.headerSpanSource(new $.ig.SpanSource());
		this.levelSpanSource(new $.ig.SpanSource());
	},
	_isHeaderCellGroup: false,
	isHeaderCellGroup: function (value) {
		if (arguments.length === 1) {
			this._isHeaderCellGroup = value;
			return value;
		} else {
			return this._isHeaderCellGroup;
		}
	}
	,
	_rowIndex: 0,
	rowIndex: function (value) {
		if (arguments.length === 1) {
			this._rowIndex = value;
			return value;
		} else {
			return this._rowIndex;
		}
	}
	,
	_columnIndex: 0,
	columnIndex: function (value) {
		if (arguments.length === 1) {
			this._columnIndex = value;
			return value;
		} else {
			return this._columnIndex;
		}
	}
	,
	_levelSpanSource: null,
	levelSpanSource: function (value) {
		if (arguments.length === 1) {
			this._levelSpanSource = value;
			return value;
		} else {
			return this._levelSpanSource;
		}
	}
	,
	_headerSpanSource: null,
	headerSpanSource: function (value) {
		if (arguments.length === 1) {
			this._headerSpanSource = value;
			return value;
		} else {
			return this._headerSpanSource;
		}
	}
	,
	_headerCellGroup: null,
	headerCellGroup: function (value) {
		if (arguments.length === 1) {
			this._headerCellGroup = value;
			return value;
		} else {
			return this._headerCellGroup;
		}
	}
	,
	_positionItemInfo: null,
	positionItemInfo: function (value) {
		if (arguments.length === 1) {
			this._positionItemInfo = value;
			return value;
		} else {
			return this._positionItemInfo;
		}
	}
	,
	_parentPositionItemInfo: null,
	parentPositionItemInfo: function (value) {
		if (arguments.length === 1) {
			this._parentPositionItemInfo = value;
			return value;
		} else {
			return this._parentPositionItemInfo;
		}
	}
	,
	_tupleIndex: 0,
	tupleIndex: function (value) {
		if (arguments.length === 1) {
			this._tupleIndex = value;
			return value;
		} else {
			return this._tupleIndex;
		}
	}
	,
	_memberIndex: 0,
	memberIndex: function (value) {
		if (arguments.length === 1) {
			this._memberIndex = value;
			return value;
		} else {
			return this._memberIndex;
		}
	}
	,
	_isExpanded: false,
	isExpanded: function (value) {
		if (arguments.length === 1) {
			this._isExpanded = value;
			return value;
		} else {
			return this._isExpanded;
		}
	}
	,
	_isExpandable: false,
	isExpandable: function (value) {
		if (arguments.length === 1) {
			this._isExpandable = value;
			return value;
		} else {
			return this._isExpandable;
		}
	}
	,
	toString: function () {
		return $.ig.util.stringFormat("{0}-{1}", this.positionItemInfo().positionIndex(), this.positionItemInfo().positionItemIndex());
	}
	,
	$type: new $.ig.Type('TableViewHeaderCellSource', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TreeStackAttributesBehavior', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	showldCollapseTreeStack: function (newCell, prevCell) {
	}
	,
	$type: new $.ig.Type('TreeStackAttributesBehavior', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TreeStackNextDimensionFirstBehavior', 'TreeStackAttributesBehavior', {
	init: function (childrenAttributesIndentation, dimensionAttributesIndentation) {
		$.ig.TreeStackAttributesBehavior.prototype.init.call(this);
		this.childrenAttributesIndentation(childrenAttributesIndentation);
		this.dimensionAttributesIndentation(dimensionAttributesIndentation);
	},
	_childrenAttributesIndentation: 0,
	childrenAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._childrenAttributesIndentation = value;
			return value;
		} else {
			return this._childrenAttributesIndentation;
		}
	}
	,
	_dimensionAttributesIndentation: 0,
	dimensionAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._dimensionAttributesIndentation = value;
			return value;
		} else {
			return this._dimensionAttributesIndentation;
		}
	}
	,
	showldCollapseTreeStack: function (newCell, prevCell) {
		if (newCell.memberIndex() < prevCell.memberIndex()) {
			return true;
		}
		if (newCell.memberIndex() == prevCell.memberIndex()) {
			if (newCell.levelNumber() <= prevCell.levelNumber()) {
				return true;
			}
		}
		if (newCell.memberIndex() > prevCell.memberIndex() && newCell.parentMemberLevelNumber() < prevCell.levelNumber()) {
			return true;
		}
		return false;
	}
	,
	$type: new $.ig.Type('TreeStackNextDimensionFirstBehavior', $.ig.TreeStackAttributesBehavior.prototype.$type)
}, true);

$.ig.util.defType('TableViewTreeHeadersFactory', 'Object', {
	_sourceAxis: null,
	sourceAxis: function (value) {
		if (arguments.length === 1) {
			this._sourceAxis = value;
			return value;
		} else {
			return this._sourceAxis;
		}
	}
	,
	_originalAxis: null,
	originalAxis: function (value) {
		if (arguments.length === 1) {
			this._originalAxis = value;
			return value;
		} else {
			return this._originalAxis;
		}
	}
	,
	_layoutOrientation: 0,
	layoutOrientation: function (value) {
		if (arguments.length === 1) {
			this._layoutOrientation = value;
			return value;
		} else {
			return this._layoutOrientation;
		}
	}
	,
	_isParentInFront: false,
	isParentInFront: function (value) {
		if (arguments.length === 1) {
			this._isParentInFront = value;
			return value;
		} else {
			return this._isParentInFront;
		}
	}
	,
	_orderedTuples: null,
	orderedTuples: function (value) {
		if (arguments.length === 1) {
			this._orderedTuples = value;
			return value;
		} else {
			return this._orderedTuples;
		}
	}
	,
	_childrenAttributesIndentation: 0,
	childrenAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._childrenAttributesIndentation = value;
			return value;
		} else {
			return this._childrenAttributesIndentation;
		}
	}
	,
	_dimensionAttributesIndentation: 0,
	dimensionAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._dimensionAttributesIndentation = value;
			return value;
		} else {
			return this._dimensionAttributesIndentation;
		}
	}
	,
	init: function (sourceAxis, originalAxis, layoutOrientation, orderedTuples, childrenAttributesIndentation, dimensionAttributesIndentation) {
		$.ig.Object.prototype.init.call(this);
		this.sourceAxis(sourceAxis);
		this.originalAxis(originalAxis);
		this.layoutOrientation(layoutOrientation);
		this.isParentInFront(true);
		this.orderedTuples(orderedTuples);
		this.childrenAttributesIndentation(childrenAttributesIndentation);
		this.dimensionAttributesIndentation(dimensionAttributesIndentation);
	},
	createHeaderCells: function () {
		var $self = this;
		if (this.sourceAxis().positionResolver().hasUnregisterdPositions()) {
			this.sourceAxis().positionResolver().completeRegisterPositions();
		}
		var rootPositionInfo = this.sourceAxis().positionResolver().rootPositionInfo();
		var headerCellsCollection = new $.ig.List$1($.ig.TableViewHeaderCellSource.prototype.$type, 0);
		$.ig.TableViewTreeHeadersFactory.prototype.createHeaderCells2(rootPositionInfo, rootPositionInfo.positionItemDepthMin(), headerCellsCollection, this.orderedTuples());
		var headerCells = new $.ig.Dictionary$2(String, $.ig.OlapTableViewHeaderCell.prototype.$type, 0);
		var treeStack = new $.ig.Stack$1($.ig.OlapTableViewTreeHeaderCell.prototype.$type);
		var result = new $.ig.ArrayListCollection$1($.ig.OlapTableViewHeaderCell.prototype.$type);
		var rowIndex = 0;
		var en = headerCellsCollection.getEnumerator();
		while (en.moveNext()) {
			var cellSource = en.current();
			if (cellSource.isHeaderCellGroup()) {
				continue;
			}
			var position = this.sourceAxis().tuples().__inner[cellSource.positionItemInfo().positionIndex()];
			var positionItem = position.item(cellSource.positionItemInfo().positionItemIndex());
			var tuple = this.sourceAxis().tuples().__inner[cellSource.tupleIndex()];
			if (this.originalAxis().positionResolver().hasUnregisterdPositions()) {
				this.originalAxis().positionResolver().completeRegisterPositions();
			}
			var originalIndex = this.originalAxis().positionResolver().getPositionIndex(tuple);
			var headerCell = (function () {
				var $ret = new $.ig.OlapTableViewTreeHeaderCell();
				$ret.axisName($self.sourceAxis().name());
				$ret.hierarchyNumber(cellSource.positionItemInfo().positionItemIndex());
				$ret.levelNumber(cellSource.positionItemInfo().positionItemDepth());
				$ret.parentMemberLevelNumber(cellSource.parentPositionItemInfo().positionItemDepth());
				$ret.caption(positionItem.caption());
				$ret.columnIndex(0);
				$ret.columnSpan(1);
				$ret.isExpanded(cellSource.isExpanded());
				$ret.isExpandable(cellSource.isExpandable());
				$ret.tupleIndex(originalIndex);
				$ret.memberIndex(cellSource.positionItemInfo().positionItemIndex());
				$ret.rowIndex(rowIndex);
				$ret.rowSpan(1);
				return $ret;
			}());
			headerCells.add($.ig.util.stringFormat("{0}-{1}", headerCell.tupleIndex(), headerCell.memberIndex()), headerCell);
			var isHidden = cellSource.positionItemInfo().positionItemIndex() > 0 && cellSource.positionItemInfo().positionItemDepth() == 0;
			if (!cellSource.isHeaderCellGroup() && !isHidden) {
				rowIndex = rowIndex + 1;
			}
			if (!isHidden) {
				result.add(headerCell);
				this.manageTreeStack(treeStack, headerCell, new $.ig.TreeStackNextDimensionFirstBehavior(this.childrenAttributesIndentation(), this.dimensionAttributesIndentation()));
			}
		}
		var en1 = result.getEnumerator();
		while (en1.moveNext()) {
			var headerCell1 = en1.current();
			if (headerCell1.memberIndex() < this.sourceAxis().tupleSize()) {
				var itemKey = $.ig.util.stringFormat("{0}-{1}", headerCell1.tupleIndex(), headerCell1.memberIndex() + 1);
				var itemCell = null;
				if ((function () { var $ret = headerCells.tryGetValue(itemKey, itemCell); itemCell = $ret.p1; return $ret.ret; }())) {
					headerCell1.isItemExpanded(itemCell.isExpanded());
					headerCell1.isItemExpandable(itemCell.isExpandable());
				}
			}
		}
		return result;
	}
	,
	manageTreeStack: function (treeStack, newCell, stackBehavior) {
		var prevCell = null;
		if (treeStack.count() == 0) {
			this.expandTreeStack(treeStack, newCell, null);
			return;
		}
		prevCell = treeStack.peek();
		if (stackBehavior.showldCollapseTreeStack(newCell, prevCell)) {
			this.collapseTreeStack(treeStack, newCell, stackBehavior);
			prevCell = null;
			if (treeStack.count() > 0) {
				prevCell = treeStack.peek();
			}
		}
		this.expandTreeStack(treeStack, newCell, prevCell);
	}
	,
	expandTreeStack: function (treeStack, newCell, prevCell) {
		if (prevCell != null) {
			if (newCell.memberIndex() > prevCell.memberIndex()) {
				newCell.indent(prevCell.indent() + this.dimensionAttributesIndentation());
			} else {
				newCell.indent(prevCell.indent() + this.childrenAttributesIndentation());
			}
		}
		treeStack.push(newCell);
	}
	,
	collapseTreeStack: function (treeStack, newCell, stackBehavior) {
		treeStack.pop();
		if (treeStack.count() > 0) {
			var prevCell = treeStack.peek();
			if (stackBehavior.showldCollapseTreeStack(newCell, prevCell)) {
				this.collapseTreeStack(treeStack, newCell, stackBehavior);
			}
		}
	}
	,
	createHeaderCells2: function (hostPositionItemInfo, topMostPositionItemDetph, headerCellsCollection, orderedTuples) {
		var spanManager = new $.ig.LevelSpanManager(hostPositionItemInfo.positionSize());
		var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == topMostPositionItemDetph; }).getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			var tableViewHeaderCell;
			var headerCellSpan;
			var $ret = $.ig.TableViewTreeHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, positionItemInfo, headerCellsCollection, spanManager, orderedTuples, true, tableViewHeaderCell, headerCellSpan);
			tableViewHeaderCell = $ret.p6;
			headerCellSpan = $ret.p7;
		}
	}
	,
	createHostedHeaderCells: function (positionItemInfo, headerCellsCollection, spanManager, headerCellSource, orderedTuples, addRoot) {
		var hostedPosionsInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values(), function (pi) { return pi.positionItemDepth() == positionItemInfo.positionItemDepthMin(); }));
		if (hostedPosionsInfo.count() > 0) {
			var en = hostedPosionsInfo.getEnumerator();
			while (en.moveNext()) {
				var hostedItemInfo = en.current();
				var hostedHeaderCell;
				var hostedCellSpan;
				var $ret = $.ig.TableViewTreeHeadersFactory.prototype.createHeaderCells1(positionItemInfo, hostedItemInfo, headerCellsCollection, spanManager, orderedTuples, addRoot, hostedHeaderCell, hostedCellSpan);
				hostedHeaderCell = $ret.p6;
				hostedCellSpan = $ret.p7;
				headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + hostedCellSpan);
				if (hostedHeaderCell.isHeaderCellGroup()) {
					headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerCellSource.headerSpanSource().spanValue(headerCellSource.headerSpanSource().spanValue() - 1);
		}
		headerCellSource.tupleIndex(positionItemInfo.positionIndex());
	}
	,
	createHeaderCells1: function (hostPositionItemInfo, positionItemInfo, headerCellsCollection, spanManager, orderedTuples, addRoot, headerCellSource, totalSpan) {
		var headerCellsTargetCollection = headerCellsCollection;
		var levelSpanSource = spanManager.getSpanSource(positionItemInfo.positionItemIndex(), positionItemInfo.positionItemDepth());
		var grandTotalHeaderCellSource = (function () {
			var $ret = new $.ig.TableViewHeaderCellSource();
			$ret.levelSpanSource(levelSpanSource);
			$ret.positionItemInfo(positionItemInfo);
			$ret.parentPositionItemInfo(hostPositionItemInfo);
			$ret.isExpandable(positionItemInfo.isExpandable());
			return $ret;
		}());
		headerCellSource = grandTotalHeaderCellSource;
		headerCellsTargetCollection.add(grandTotalHeaderCellSource);
		if (addRoot) {
			addRoot = false;
			orderedTuples.add(positionItemInfo.positionIndex());
		}
		var childrenPosionInfo = null;
		if (positionItemInfo.isExpanded()) {
			childrenPosionInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); }));
		}
		if (childrenPosionInfo != null && childrenPosionInfo.count() > 0) {
			var headerGroupCellSource = (function () {
				var $ret = new $.ig.TableViewHeaderCellSource();
				$ret.isHeaderCellGroup(true);
				$ret.positionItemInfo(positionItemInfo);
				$ret.parentPositionItemInfo(hostPositionItemInfo);
				$ret.isExpandable(positionItemInfo.isExpandable());
				return $ret;
			}());
			headerCellsTargetCollection.add(headerGroupCellSource);
			var en = childrenPosionInfo.getEnumerator();
			while (en.moveNext()) {
				var childItemInfo = en.current();
				var childHeaderCell;
				var childItemSpan;
				var $ret = $.ig.TableViewTreeHeadersFactory.prototype.createHeaderCells1(hostPositionItemInfo, childItemInfo, headerCellsCollection, spanManager, orderedTuples, true, childHeaderCell, childItemSpan);
				childHeaderCell = $ret.p6;
				childItemSpan = $ret.p7;
				headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + childItemSpan);
				if (childHeaderCell.isHeaderCellGroup()) {
					headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() + 1);
				}
			}
			headerGroupCellSource.headerSpanSource().spanValue(headerGroupCellSource.headerSpanSource().spanValue() - 1);
			grandTotalHeaderCellSource.isExpanded(true);
			headerGroupCellSource.isExpanded(true);
			grandTotalHeaderCellSource.headerCellGroup(headerGroupCellSource);
			headerGroupCellSource.tupleIndex(grandTotalHeaderCellSource.tupleIndex());
			headerCellSource = headerGroupCellSource;
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue() + headerGroupCellSource.headerSpanSource().spanValue() - 1;
		} else {
			totalSpan = grandTotalHeaderCellSource.headerSpanSource().spanValue();
		}
		$.ig.TableViewTreeHeadersFactory.prototype.createHostedHeaderCells(positionItemInfo, headerCellsCollection, spanManager, grandTotalHeaderCellSource, orderedTuples, false);
		return {
			p6: headerCellSource,
			p7: totalSpan
		};
	}
	,
	$type: new $.ig.Type('TableViewTreeHeadersFactory', $.ig.Object.prototype.$type, [$.ig.ITableViewHeadersFactory.prototype.$type])
}, true);

$.ig.util.defType('TableViewImpl', 'Object', {
	init: function (result, hasColumns, hasRows, viewSettings) {
		var $self = this;
		this._childrenAttributesIndentation = 40;
		this._dimensionAttributesIndentation = 20;
		$.ig.Object.prototype.init.call(this);
		if (result == null) {
			throw new $.ig.ArgumentNullException(0, "result");
		}
		this.result(result);
		this.originalResult(result);
		if (viewSettings == null) {
			this.viewSettings(new $.ig.TableViewSettings());
		} else {
			this.viewSettings(viewSettings);
		}
		this.hasColumns(hasColumns);
		this.hasRows(hasRows);
		this.columnSortDirections(new $.ig.ArrayListCollection$1($.ig.TupleSortDirection.prototype.$type));
		var $t = (this.columnSortDirections());
		$t.collectionChanged = $.ig.Delegate.prototype.combine($t.collectionChanged, function (sender, e) {
			$self.isModified(true);
		});
		this.levelSortDirections(new $.ig.ArrayListCollection$1($.ig.LevelSortDirection.prototype.$type));
		var $t1 = (this.levelSortDirections());
		$t1.collectionChanged = $.ig.Delegate.prototype.combine($t1.collectionChanged, function (sender, e) {
			$self.isModified(true);
		});
		this.appliedColumnSortDirections(new $.ig.List$1($.ig.TupleSortDirection.prototype.$type, 0));
		this.appliedLevelSortDirections(new $.ig.List$1($.ig.LevelSortDirection.prototype.$type, 0));
		this.appliedSortDirectionsMap(new $.ig.Dictionary$2(String, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type)), 0));
	},
	_viewSettings: null,
	viewSettings: function (value) {
		if (arguments.length === 1) {
			this._viewSettings = value;
			return value;
		} else {
			return this._viewSettings;
		}
	}
	,
	_result: null,
	result: function (value) {
		if (arguments.length === 1) {
			this._result = value;
			return value;
		} else {
			return this._result;
		}
	}
	,
	_originalResult: null,
	originalResult: function (value) {
		if (arguments.length === 1) {
			this._originalResult = value;
			return value;
		} else {
			return this._originalResult;
		}
	}
	,
	_hasRows: false,
	hasRows: function (value) {
		if (arguments.length === 1) {
			this._hasRows = value;
			return value;
		} else {
			return this._hasRows;
		}
	}
	,
	_hasColumns: false,
	hasColumns: function (value) {
		if (arguments.length === 1) {
			this._hasColumns = value;
			return value;
		} else {
			return this._hasColumns;
		}
	}
	,
	_rowHeadersFactory: null,
	rowHeadersFactory: function (value) {
		if (arguments.length === 1) {
			this._rowHeadersFactory = value;
			return value;
		} else {
			return this._rowHeadersFactory;
		}
	}
	,
	_columnHeadersFactory: null,
	columnHeadersFactory: function (value) {
		if (arguments.length === 1) {
			this._columnHeadersFactory = value;
			return value;
		} else {
			return this._columnHeadersFactory;
		}
	}
	,
	_isInitialized: false,
	isInitialized: function (value) {
		if (arguments.length === 1) {
			this._isInitialized = value;
			return value;
		} else {
			return this._isInitialized;
		}
	}
	,
	_isModified: false,
	isModified: function (value) {
		if (arguments.length === 1) {
			this._isModified = value;
			return value;
		} else {
			return this._isModified;
		}
	}
	,
	__rowHeaders: null,
	__columnHeaders: null,
	__resultCells: null,
	rowHeaders: function (value) {
		if (arguments.length === 1) {
			this.__rowHeaders = value;
			return value;
		} else {
			if (!this.isInitialized() || this.isModified()) {
				this.initialize();
			}
			return this.__rowHeaders;
		}
	}
	,
	columnHeaders: function (value) {
		if (arguments.length === 1) {
			this.__columnHeaders = value;
			return value;
		} else {
			if (!this.isInitialized() || this.isModified()) {
				this.initialize();
			}
			return this.__columnHeaders;
		}
	}
	,
	resultCells: function (value) {
		if (arguments.length === 1) {
			this.__resultCells = value;
			return value;
		} else {
			if (!this.isInitialized() || this.isModified()) {
				this.initialize();
			}
			return this.__resultCells;
		}
	}
	,
	_columnSortDirections: null,
	columnSortDirections: function (value) {
		if (arguments.length === 1) {
			this._columnSortDirections = value;
			return value;
		} else {
			return this._columnSortDirections;
		}
	}
	,
	_levelSortDirections: null,
	levelSortDirections: function (value) {
		if (arguments.length === 1) {
			this._levelSortDirections = value;
			return value;
		} else {
			return this._levelSortDirections;
		}
	}
	,
	_appliedColumnSortDirections: null,
	appliedColumnSortDirections: function (value) {
		if (arguments.length === 1) {
			this._appliedColumnSortDirections = value;
			return value;
		} else {
			return this._appliedColumnSortDirections;
		}
	}
	,
	_appliedLevelSortDirections: null,
	appliedLevelSortDirections: function (value) {
		if (arguments.length === 1) {
			this._appliedLevelSortDirections = value;
			return value;
		} else {
			return this._appliedLevelSortDirections;
		}
	}
	,
	_appliedSortDirectionsMap: null,
	appliedSortDirectionsMap: function (value) {
		if (arguments.length === 1) {
			this._appliedSortDirectionsMap = value;
			return value;
		} else {
			return this._appliedSortDirectionsMap;
		}
	}
	,
	initialize: function () {
		this.rowHeadersFactory(null);
		this.columnHeadersFactory(null);
		this.rowHeaders(null);
		this.columnHeaders(null);
		this.resultCells(null);
		this.result(this.originalResult());
		if (this.result().isEmpty()) {
			this.isModified(false);
			this.isInitialized(true);
			return;
		}
		if (this.levelSortDirections().count() > 0) {
			var resultHeaderSorter = new $.ig.ResultHeaderSorter(this.result(), this.hasRows(), this.hasColumns(), this.levelSortDirections());
			this.result(resultHeaderSorter.sort());
			this.appliedLevelSortDirections(resultHeaderSorter.appliedSortDirections());
			this.appliedSortDirectionsMap(resultHeaderSorter.appliedSortDirectionsMap());
		} else {
			this.appliedLevelSortDirections(new $.ig.List$1($.ig.LevelSortDirection.prototype.$type, 0));
			this.appliedSortDirectionsMap(new $.ig.Dictionary$2(String, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type)), 0));
		}
		if (this.hasRows() && this.columnSortDirections().count() > 0) {
			if (this.appliedLevelSortDirections().count() > 0) {
				if (this.originalResult().axes().item(0).positionResolver().hasUnregisterdPositions()) {
					this.originalResult().axes().item(0).positionResolver().completeRegisterPositions();
				}
				if (this.result().axes().item(0).positionResolver().hasUnregisterdPositions()) {
					this.result().axes().item(0).positionResolver().completeRegisterPositions();
				}
				var sortDirections = new $.ig.List$1($.ig.TupleSortDirection.prototype.$type, 2, this.columnSortDirections().count());
				var tupleIndicesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 1, this.appliedColumnSortDirections().count());
				var en = this.columnSortDirections().getEnumerator();
				while (en.moveNext()) {
					var sd = en.current();
					var tuple = this.originalResult().axes().item(0).tuples().__inner[sd.tupleIndex()];
					var newTupleIndex = this.result().axes().item(0).positionResolver().getPositionIndex(tuple);
					var newTupleSortDirection = new $.ig.TupleSortDirection();
					newTupleSortDirection.sortDirection(sd.sortDirection());
					newTupleSortDirection.tupleIndex(newTupleIndex);
					newTupleSortDirection.comparer(sd.comparer());
					sortDirections.add(newTupleSortDirection);
					tupleIndicesMap.item(newTupleIndex, sd.tupleIndex());
				}
				var resultColumnValueSorter = new $.ig.ResultColumnValueSorter(this.result(), this.hasRows(), this.hasColumns(), sortDirections);
				this.result(resultColumnValueSorter.sort());
				var en1 = resultColumnValueSorter.appliedSortDirections().getEnumerator();
				while (en1.moveNext()) {
					var sd1 = en1.current();
					sd1.tupleIndex(tupleIndicesMap.item(sd1.tupleIndex()));
				}
				this.appliedColumnSortDirections(resultColumnValueSorter.appliedSortDirections());
			} else {
				var resultColumnValueSorter1 = new $.ig.ResultColumnValueSorter(this.result(), this.hasRows(), this.hasColumns(), this.columnSortDirections());
				this.result(resultColumnValueSorter1.sort());
				this.appliedColumnSortDirections(resultColumnValueSorter1.appliedSortDirections());
			}
		} else {
			this.appliedColumnSortDirections(new $.ig.List$1($.ig.TupleSortDirection.prototype.$type, 0));
		}
		var axisIndex = 0;
		var columnIndexesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		var columnsCount = 1;
		if (this.hasColumns()) {
			var columnAxis = this.result().axes().item(axisIndex);
			var originalColumnAxis = this.originalResult().axes().item(axisIndex);
			var orderedColumnTuples = this.initializeColumnHeaders(columnAxis, originalColumnAxis);
			for (var i = 0; i < orderedColumnTuples.count(); i++) {
				var index = orderedColumnTuples.item(i);
				columnIndexesMap.add(index, i);
			}
			axisIndex++;
			columnsCount = columnAxis.tuples().count();
		}
		var rowIndexesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		if (this.hasRows()) {
			var rowAxis = this.result().axes().item(axisIndex);
			var originalRowAxis = this.originalResult().axes().item(axisIndex);
			var orderedRowTuples = this.initializeRowHeaders(rowAxis, originalRowAxis);
			for (var i1 = 0; i1 < orderedRowTuples.count(); i1++) {
				var index1 = orderedRowTuples.item(i1);
				rowIndexesMap.add(index1, i1);
			}
		}
		var resultCells = new $.ig.ArrayListCollection$1($.ig.OlapTableViewResultCell.prototype.$type);
		for (var i2 = 0; i2 < this.result().cells().count(); i2++) {
			var cell = this.result().cells().item(i2);
			var cellValue;
			var $ret = cell.properties().tryGetValue("Value", cellValue);
			cellValue = $ret.p1;
			var formatedValue;
			var $ret1 = cell.properties().tryGetValue("FmtValue", formatedValue);
			formatedValue = $ret1.p1;
			var cellOrdinal = cell.cellOrdinal();
			if (this.hasColumns()) {
				var columnIndex = cellOrdinal % columnsCount;
				var newIndex = columnIndexesMap.item(columnIndex);
				if (columnIndex != newIndex) {
					var rowIndex = $.ig.intDivide(cellOrdinal, columnsCount);
					cellOrdinal = rowIndex * columnsCount + newIndex;
				}
			}
			if (this.hasRows()) {
				var rowIndex1 = $.ig.intDivide(cellOrdinal, columnsCount);
				var newIndex1 = rowIndexesMap.item(rowIndex1);
				if (rowIndex1 != newIndex1) {
					var columnIndex1 = cellOrdinal % columnsCount;
					cellOrdinal = newIndex1 * columnsCount + columnIndex1;
				}
			}
			resultCells.add((function () {
				var $ret = new $.ig.OlapTableViewResultCell();
				$ret.value(cellValue);
				$ret.formattedValue(formatedValue);
				$ret.cellOrdinal(cellOrdinal);
				$ret.resultCellIndex(i2);
				return $ret;
			}()));
			this.resultCells(resultCells);
		}
		this.isModified(false);
		this.isInitialized(true);
	}
	,
	initializeColumnHeaders: function (columnAxis, originalColumnAxis) {
		var orderedTuples = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		if (this.viewSettings().compactColumnHeaders()) {
			this.columnHeadersFactory(new $.ig.TableSuperCompactViewHeadersFactory(columnAxis, originalColumnAxis, $.ig.HeaderCellsLayoutOrientation.prototype.horizontal, this.viewSettings().isParentInFrontForColumns(), orderedTuples));
		} else {
			this.columnHeadersFactory(new $.ig.TableViewHeadersFactory(columnAxis, originalColumnAxis, $.ig.HeaderCellsLayoutOrientation.prototype.horizontal, this.viewSettings().isParentInFrontForColumns(), orderedTuples));
		}
		this.columnHeaders(this.columnHeadersFactory().createHeaderCells());
		return orderedTuples;
	}
	,
	_childrenAttributesIndentation: 0,
	_dimensionAttributesIndentation: 0,
	initializeRowHeaders: function (rowAxis, originalRowAxis) {
		var orderedTuples = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var layout = this.viewSettings().rowHeaderLayout();
		var parentOnTop = this.viewSettings().isParentInFrontForRows();
		switch (layout) {
			case $.ig.RowHeaderLayout.prototype.standard:
				this.rowHeadersFactory(new $.ig.TableViewHeadersFactory(rowAxis, originalRowAxis, $.ig.HeaderCellsLayoutOrientation.prototype.vertical, parentOnTop, orderedTuples));
				break;
			case $.ig.RowHeaderLayout.prototype.superCompact:
				this.rowHeadersFactory(new $.ig.TableSuperCompactViewHeadersFactory(rowAxis, originalRowAxis, $.ig.HeaderCellsLayoutOrientation.prototype.vertical, parentOnTop, orderedTuples));
				break;
			case $.ig.RowHeaderLayout.prototype.tree:
				var childrenAttributesIndentation = this._childrenAttributesIndentation;
				var dimensionAttributesIndentation = this._dimensionAttributesIndentation;
				var settings = $.ig.util.cast($.ig.TreeLayoutTableViewSettings.prototype.$type, this.viewSettings());
				if (settings != null) {
					childrenAttributesIndentation = settings.childrenAttributesIndentation();
					dimensionAttributesIndentation = settings.dimensionAttributesIndentation();
				}
				this.rowHeadersFactory(new $.ig.TableViewTreeHeadersFactory(rowAxis, originalRowAxis, $.ig.HeaderCellsLayoutOrientation.prototype.vertical, orderedTuples, childrenAttributesIndentation, dimensionAttributesIndentation));
				break;
		}
		this.rowHeaders(this.rowHeadersFactory().createHeaderCells());
		return orderedTuples;
	}
	,
	$type: new $.ig.Type('TableViewImpl', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapResultView', 'Object', {
	init: function (result, visibleResult, hasColumns, hasRows) {
		$.ig.Object.prototype.init.call(this);
		if (result == null) {
			throw new $.ig.ArgumentNullException(0, "result");
		}
		this.result(result);
		this.visibleResult(visibleResult);
		this.hasColumns(hasColumns);
		this.hasRows(hasRows);
	},
	_result: null,
	result: function (value) {
		if (arguments.length === 1) {
			this._result = value;
			return value;
		} else {
			return this._result;
		}
	}
	,
	_visibleResult: null,
	visibleResult: function (value) {
		if (arguments.length === 1) {
			this._visibleResult = value;
			return value;
		} else {
			return this._visibleResult;
		}
	}
	,
	_hasRows: false,
	hasRows: function (value) {
		if (arguments.length === 1) {
			this._hasRows = value;
			return value;
		} else {
			return this._hasRows;
		}
	}
	,
	_hasColumns: false,
	hasColumns: function (value) {
		if (arguments.length === 1) {
			this._hasColumns = value;
			return value;
		} else {
			return this._hasColumns;
		}
	}
	,
	getSubResult: function (result, rowAxis, columnAxis, hasRows, hasColumns) {
		var axisIndex = 0;
		var fullColumnAxis = null;
		var fullColumnsCount = 1;
		if (hasColumns) {
			fullColumnAxis = result.axes().item(axisIndex);
			fullColumnsCount = fullColumnAxis.tuples().count();
			axisIndex++;
		}
		var fullRowAxis = null;
		var fullRowsCount = 1;
		if (hasRows) {
			fullRowAxis = result.axes().item(axisIndex);
			fullRowsCount = fullRowAxis.tuples().count();
		}
		var rowsCount = 1;
		if (rowAxis != null) {
			rowsCount = rowAxis.tuples().count();
		}
		var columnsCount = 1;
		if (columnAxis != null) {
			columnsCount = columnAxis.tuples().count();
		}
		var cells = new Array(rowsCount * columnsCount);
		if (result.cells().count() > 0) {
			var cellIndex = 0;
			var cell = result.cells().item(cellIndex);
			for (var i = 0; i < fullRowsCount; i++) {
				var cellRowIndex = $.ig.intDivide(cell.cellOrdinal(), fullColumnsCount);
				if (cellRowIndex != i) {
					continue;
				}
				var rowTupleIndex = 0;
				if (rowAxis != null && fullRowAxis != null) {
					var rowTuple = fullRowAxis.tuples().__inner[i];
					rowTupleIndex = rowAxis.positionResolver().getPositionIndex(rowTuple);
				}
				if (rowTupleIndex == -1) {
					while (cellRowIndex == i) {
						cellIndex++;
						if (cellIndex < result.cells().count()) {
							cell = result.cells().item(cellIndex);
							cellRowIndex = $.ig.intDivide(cell.cellOrdinal(), fullColumnsCount);
						} else {
							break;
						}
					}
					continue;
				}
				var cellColumnIndex = cell.cellOrdinal() % fullColumnsCount;
				for (var j = 0; j < fullColumnsCount; j++) {
					if (cellColumnIndex != j) {
						continue;
					}
					var columnTupleIndex = -1;
					if (columnAxis == null || fullColumnAxis == null) {
						columnTupleIndex = 0;
					} else {
						var columnTuple = fullColumnAxis.tuples().__inner[j];
						columnTupleIndex = columnAxis.positionResolver().getPositionIndex(columnTuple);
					}
					if (columnTupleIndex == -1) {
						cellIndex++;
						if (cellIndex < result.cells().count()) {
							cell = result.cells().item(cellIndex);
							cellRowIndex = $.ig.intDivide(cell.cellOrdinal(), fullColumnsCount);
							if (cellRowIndex > i) {
								break;
							}
							cellColumnIndex = cell.cellOrdinal() % fullColumnsCount;
						}
						continue;
					}
					var newCell = cell.clone();
					newCell.cellOrdinal(rowTupleIndex * columnsCount + columnTupleIndex);
					cells[newCell.cellOrdinal()] = newCell;
					cellIndex++;
					if (cellIndex < result.cells().count()) {
						cell = result.cells().item(cellIndex);
						cellRowIndex = $.ig.intDivide(cell.cellOrdinal(), fullColumnsCount);
						if (cellRowIndex > i) {
							break;
						}
						cellColumnIndex = cell.cellOrdinal() % fullColumnsCount;
					}
				}
			}
		}
		var resultCells = new $.ig.List$1($.ig.OlapResultCell.prototype.$type, 0);
		for (var i1 = 0; i1 < cells.length; i1++) {
			var cell1 = cells[i1];
			if (cell1 != null) {
				resultCells.add(cell1);
			}
		}
		var resultAxes = new $.ig.List$1($.ig.OlapResultAxis.prototype.$type, 0);
		if (hasColumns) {
			resultAxes.add(columnAxis);
		}
		if (hasRows) {
			resultAxes.add(rowAxis);
		}
		return (function () {
			var $ret = new $.ig.OlapResult();
			$ret.axes(resultAxes);
			$ret.cells(resultCells);
			$ret.isEmpty(!hasRows && !hasColumns && resultCells.count() == 0);
			return $ret;
		}());
	}
	,
	createSubAxis: function (sourceAxis, indexes, inner) {
		var tuples = new $.ig.List$1($.ig.OlapResultTuple.prototype.$type, 0);
		if (inner) {
			for (var i = 0; i < indexes.count(); i++) {
				var index = indexes.item(i);
				tuples.add(sourceAxis.tuples().__inner[index].clone());
			}
		} else {
			var index1 = 0;
			var skipIndex = indexes.item(index1);
			for (var i1 = 0; i1 < sourceAxis.tuples().count(); i1++) {
				if (i1 == skipIndex) {
					index1++;
					if (index1 < indexes.count()) {
						skipIndex = indexes.item(index1);
					}
				} else {
					tuples.add(sourceAxis.tuples().__inner[i1].clone());
				}
			}
		}
		var subAxis = (function () {
			var $ret = new $.ig.OlapResultAxis(tuples, sourceAxis.tupleSize());
			$ret.name(sourceAxis.name());
			return $ret;
		}());
		return subAxis;
	}
	,
	expandTupleMember: function (axisName, tupleIndex, memberIndex) {
		var positionInfo = this.getPositionItem(axisName, tupleIndex, memberIndex, this.visibleResult());
		if (positionInfo == null) {
			return null;
		}
		if (!positionInfo.isExpandable()) {
			throw new $.ig.InvalidOperationException(1, "The position is not expandable.");
		}
		if (positionInfo.isExpanded()) {
			return this;
		}
		var visibleAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.visibleResult().axes(), function (a) { return a.name() == axisName; }));
		var cellsAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.result().axes(), function (a) { return a.name() == axisName; }));
		var isRowAxis = (axisName == "Axis0" && !this.hasColumns()) || (axisName == "Axis1" && this.hasRows());
		var visibleTuple = visibleAxis.tuples().__inner[tupleIndex];
		var cachedTupleIndex = cellsAxis.positionResolver().getPositionIndex(visibleTuple);
		var childrenIndexes = this.getChildrenPositionIndexes(axisName, cachedTupleIndex, memberIndex, this.result());
		if (childrenIndexes.count() == 0) {
			return null;
		}
		var rowAxis = null;
		var columnAxis = null;
		if (isRowAxis) {
			rowAxis = this.createSubAxis(cellsAxis, childrenIndexes, true);
			if (this.hasColumns()) {
				columnAxis = this.visibleResult().axes().item(0);
			}
		} else {
			columnAxis = this.createSubAxis(cellsAxis, childrenIndexes, true);
			if (this.hasRows()) {
				rowAxis = this.visibleResult().axes().item(1);
			}
		}
		var subResult = this.getSubResult(this.result(), rowAxis, columnAxis, this.hasRows(), this.hasColumns());
		var extendedResult = this.extendResult(this.visibleResult(), subResult, cellsAxis.name());
		return new $.ig.OlapResultView(this.result(), extendedResult, this.hasColumns(), this.hasRows());
	}
	,
	collapseTupleMember: function (axisName, tupleIndex, memberIndex) {
		var positionInfo = this.getPositionItem(axisName, tupleIndex, memberIndex, this.visibleResult());
		if (positionInfo == null) {
			return null;
		}
		if (!positionInfo.isExpandable()) {
			throw new $.ig.InvalidOperationException(1, "The position is not expandable.");
		}
		if (!positionInfo.isExpanded()) {
			return this;
		}
		var childrenIndexes = this.getChildrenPositionIndexes(axisName, tupleIndex, memberIndex, this.visibleResult());
		if (childrenIndexes.count() == 0) {
			return null;
		}
		var cellsAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.visibleResult().axes(), function (a) { return a.name() == axisName; }));
		var isRowAxis = (axisName == "Axis0" && !this.hasColumns()) || (axisName == "Axis1" && this.hasRows());
		var rowAxis = null;
		var columnAxis = null;
		if (isRowAxis) {
			rowAxis = this.createSubAxis(cellsAxis, childrenIndexes, false);
			if (this.hasColumns()) {
				columnAxis = this.visibleResult().axes().item(0);
			}
		} else {
			columnAxis = this.createSubAxis(cellsAxis, childrenIndexes, false);
			if (this.hasRows()) {
				rowAxis = this.visibleResult().axes().item(1);
			}
		}
		var resultAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.result().axes(), function (a) { return a.name() == axisName; }));
		var position = resultAxis.tuples().__inner[tupleIndex];
		if (resultAxis.positionResolver().hasUnregisterdPositions()) {
			resultAxis.positionResolver().completeRegisterPositions();
		}
		var hostPosition = resultAxis.positionResolver().getHostPositionItemInfo(position, memberIndex);
		var itemPosition = hostPosition.item(position.item(memberIndex).key());
		itemPosition.isExpanded(false);
		var subResult = this.getSubResult(this.visibleResult(), rowAxis, columnAxis, this.hasRows(), this.hasColumns());
		return new $.ig.OlapResultView(this.result(), subResult, this.hasColumns(), this.hasRows());
	}
	,
	extend: function (partialResult, axisName) {
		var cellsAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.visibleResult().axes(), function (a) { return a.name() == axisName; }));
		var isRowAxis = (axisName == "Axis0" && !this.hasColumns()) || (axisName == "Axis1" && this.hasRows());
		var rowsVisibleAxis = null;
		var columnsVisibleAxis = null;
		var fullOpositeAxis = null;
		var resetCache = false;
		if (isRowAxis) {
			rowsVisibleAxis = cellsAxis;
			if (this.hasColumns()) {
				columnsVisibleAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.visibleResult().axes(), function (a) { return a.name() != axisName; }));
				fullOpositeAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.result().axes(), function (a) { return a.name() != axisName; }));
				resetCache = columnsVisibleAxis.tuples().count() != fullOpositeAxis.tuples().count();
			}
		} else {
			columnsVisibleAxis = cellsAxis;
			if (this.hasRows()) {
				rowsVisibleAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.visibleResult().axes(), function (a) { return a.name() != axisName; }));
				fullOpositeAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.result().axes(), function (a) { return a.name() != axisName; }));
				resetCache = rowsVisibleAxis.tuples().count() != fullOpositeAxis.tuples().count();
			}
		}
		var result = null;
		var visibleResult = null;
		if (fullOpositeAxis != null && resetCache) {
			var subResult = this.getSubResult(this.result(), rowsVisibleAxis, columnsVisibleAxis, this.hasRows(), this.hasColumns());
			result = this.extendResult(subResult, partialResult, axisName);
			visibleResult = this.extendResult(subResult, partialResult, axisName);
		} else {
			result = this.extendResult(this.result(), partialResult, axisName);
			visibleResult = this.extendResult(this.visibleResult(), partialResult, axisName);
		}
		return new $.ig.OlapResultView(result, visibleResult, this.hasColumns(), this.hasRows());
	}
	,
	extendResult: function (result, partialResult, axisName) {
		var axisToExtend = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() == axisName; }));
		if (axisToExtend == null) {
			return null;
		}
		var sourceAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, partialResult.axes(), function (a) { return a.name() == axisName; }));
		var isRowAxis = (axisName == "Axis0" && !this.hasColumns()) || (axisName == "Axis1" && this.hasRows());
		var opositeAxisToExtend = null;
		var opositeSourceAxis = null;
		if (isRowAxis) {
			if (this.hasColumns()) {
				opositeAxisToExtend = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() != axisName; }));
				opositeSourceAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, partialResult.axes(), function (a) { return a.name() != axisName; }));
			}
		} else {
			if (this.hasRows()) {
				opositeAxisToExtend = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() != axisName; }));
				opositeSourceAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, partialResult.axes(), function (a) { return a.name() != axisName; }));
			}
		}
		var tuples = new $.ig.List$1($.ig.OlapResultTuple.prototype.$type, 0);
		var en = axisToExtend.tuples().getEnumerator();
		while (en.moveNext()) {
			var tuple = en.current();
			tuples.add(tuple.clone());
		}
		var en1 = sourceAxis.tuples().getEnumerator();
		while (en1.moveNext()) {
			var tuple1 = en1.current();
			tuples.add(tuple1);
		}
		var sourceAxisTuplesCount = sourceAxis.tuples().count();
		var opositeSourceAxisTuplesCount = opositeSourceAxis != null && opositeSourceAxis.tuples().count() > 0 ? opositeSourceAxis.tuples().count() : 1;
		var axisToExtendTuplesCount = axisToExtend.tuples().count() > 0 ? axisToExtend.tuples().count() : 1;
		var notChangedAxisTuplesCount = opositeAxisToExtend != null && opositeAxisToExtend.tuples().count() > 0 ? opositeAxisToExtend.tuples().count() : 1;
		var cellsArrayMaxSize = axisToExtendTuplesCount * notChangedAxisTuplesCount;
		var cells = new $.ig.List$1($.ig.OlapResultCell.prototype.$type, 0);
		if (isRowAxis) {
			var en2 = result.cells().getEnumerator();
			while (en2.moveNext()) {
				var cell = en2.current();
				cells.add(cell.clone());
			}
			var newCells = new Array(sourceAxisTuplesCount * notChangedAxisTuplesCount);
			var offset = axisToExtendTuplesCount * notChangedAxisTuplesCount;
			var en3 = partialResult.cells().getEnumerator();
			while (en3.moveNext()) {
				var cell1 = en3.current();
				var newCell = cell1.clone();
				var columnIndex = this.getMappedCellColumnIndex(newCell.cellOrdinal(), opositeAxisToExtend, opositeSourceAxis);
				var currentRowIndex = $.ig.intDivide(newCell.cellOrdinal(), opositeSourceAxisTuplesCount);
				newCell.cellOrdinal(currentRowIndex * notChangedAxisTuplesCount + columnIndex);
				newCell.cellOrdinal(newCell.cellOrdinal() + cellsArrayMaxSize);
				newCells[newCell.cellOrdinal() - offset] = newCell;
			}
			for (var i = 0; i < newCells.length; i++) {
				var newCell1 = newCells[i];
				if (newCell1 != null) {
					cells.add(newCell1);
				}
			}
		} else {
			var cellsArray = new Array(notChangedAxisTuplesCount * (axisToExtendTuplesCount + sourceAxisTuplesCount));
			var en4 = result.cells().getEnumerator();
			while (en4.moveNext()) {
				var cell2 = en4.current();
				var newCell2 = cell2.clone();
				var rowIndex = $.ig.intDivide(newCell2.cellOrdinal(), axisToExtendTuplesCount);
				newCell2.cellOrdinal(newCell2.cellOrdinal() + rowIndex * sourceAxisTuplesCount);
				cellsArray[newCell2.cellOrdinal()] = newCell2;
			}
			var en5 = partialResult.cells().getEnumerator();
			while (en5.moveNext()) {
				var cell3 = en5.current();
				var newCell3 = cell3.clone();
				var rowIndex1 = this.getMappedCellRowIndex(newCell3.cellOrdinal(), opositeAxisToExtend, opositeSourceAxis, sourceAxisTuplesCount);
				var currentRowIndex1 = $.ig.intDivide(cell3.cellOrdinal(), sourceAxisTuplesCount);
				if (rowIndex1 != currentRowIndex1) {
				}
				var currentColumnIndex = newCell3.cellOrdinal() % sourceAxisTuplesCount;
				newCell3.cellOrdinal(rowIndex1 * sourceAxisTuplesCount + currentColumnIndex);
				newCell3.cellOrdinal(newCell3.cellOrdinal() + (rowIndex1 + 1) * axisToExtendTuplesCount);
				cellsArray[newCell3.cellOrdinal()] = newCell3;
			}
			for (var i1 = 0; i1 < cellsArray.length; i1++) {
				var cell4 = cellsArray[i1];
				if (cell4 != null) {
					cells.add(cell4);
				}
			}
		}
		var combinedResult = new $.ig.OlapResult();
		combinedResult.axes(new $.ig.List$1($.ig.OlapResultAxis.prototype.$type, 0));
		var combinedAxis = (function () {
			var $ret = new $.ig.OlapResultAxis(tuples, axisToExtend.tupleSize());
			$ret.name(axisToExtend.name());
			return $ret;
		}());
		if (isRowAxis) {
			if (opositeAxisToExtend != null) {
				combinedResult.axes().add(opositeAxisToExtend);
			}
			combinedResult.axes().add(combinedAxis);
		} else {
			combinedResult.axes().add(combinedAxis);
			if (opositeAxisToExtend != null) {
				combinedResult.axes().add(opositeAxisToExtend);
			}
		}
		combinedResult.cells(cells);
		combinedResult.isEmpty(!this.hasColumns() && !this.hasRows() && cells.count() == 0);
		return combinedResult;
	}
	,
	getMappedCellColumnIndex: function (cellOrdinal, targetAxis, sourceAxis) {
		if (sourceAxis == null) {
			return 0;
		}
		var columnIndex = cellOrdinal % sourceAxis.tuples().count();
		var tuple = sourceAxis.tuples().__inner[columnIndex];
		var tupleIndex = targetAxis.positionResolver().getPositionIndex(tuple);
		return tupleIndex;
	}
	,
	getMappedCellRowIndex: function (cellOrdinal, targetAxis, sourceAxis, columnsCount) {
		if (sourceAxis == null) {
			return 0;
		}
		var rowIndex = $.ig.intDivide(cellOrdinal, columnsCount);
		var tuple = sourceAxis.tuples().__inner[rowIndex];
		var tupleIndex = targetAxis.positionResolver().getPositionIndex(tuple);
		return tupleIndex;
	}
	,
	getChildrenPositionIndexes: function (axisName, tupleIndex, memberIndex, result) {
		var cellAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() == axisName; }));
		var position = cellAxis.tuples().__inner[tupleIndex];
		var positionItem = position.item(memberIndex);
		if (cellAxis.positionResolver().hasUnregisterdPositions()) {
			cellAxis.positionResolver().completeRegisterPositions();
		}
		var indexList = cellAxis.positionResolver().getChildPositionsIndexes(positionItem, $.ig.ListSortDirection.prototype.ascending);
		if (indexList != null) {
			return $.ig.Enumerable.prototype.toList$1($.ig.Number.prototype.$type, indexList);
		}
		return null;
	}
	,
	getPositionItem: function (axisName, tupleIndex, memberIndex, result) {
		var cellAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() == axisName; }));
		var position = cellAxis.tuples().__inner[tupleIndex];
		var positionItem = position.item(memberIndex);
		if (cellAxis.positionResolver().hasUnregisterdPositions()) {
			cellAxis.positionResolver().completeRegisterPositions();
		}
		var hostPositionInfo = cellAxis.positionResolver().getHostPositionItemInfo(position, memberIndex);
		var positionInfo;
		var $ret = hostPositionInfo.tryGetValue(positionItem.key(), positionInfo);
		positionInfo = $ret.p1;
		return positionInfo;
	}
	,
	$type: new $.ig.Type('OlapResultView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ResultViewHelper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	setPositionIsExpaned2: function (axisName, tupleIndex, memberIndex, isExpanded, resultView) {
		$.ig.ResultViewHelper.prototype.setPositionIsExpaned1(axisName, tupleIndex, memberIndex, true, resultView.visibleResult());
		$.ig.ResultViewHelper.prototype.setPositionIsExpaned1(axisName, tupleIndex, memberIndex, true, resultView.result());
	}
	,
	syncPositionExpansionState: function (axisName, sourceResultView, targetResultView) {
		var sourceVisibleAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, sourceResultView.visibleResult().axes(), function (ax) { return ax.name() == axisName; }));
		var targetVisibleAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, targetResultView.visibleResult().axes(), function (ax) { return ax.name() == axisName; }));
		$.ig.ResultViewHelper.prototype.setExpansionStates1(sourceVisibleAxis.positionResolver(), targetVisibleAxis.positionResolver());
		var sourceAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, sourceResultView.result().axes(), function (ax) { return ax.name() == axisName; }));
		var targetAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, targetResultView.result().axes(), function (ax) { return ax.name() == axisName; }));
		$.ig.ResultViewHelper.prototype.setExpansionStates1(sourceAxis.positionResolver(), targetAxis.positionResolver());
	}
	,
	setPositionIsExpaned1: function (axisName, tupleIndex, memberIndex, isExpanded, result) {
		var resultAxis = $.ig.Enumerable.prototype.first$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (a) { return a.name() == axisName; }));
		if (resultAxis.positionResolver().hasUnregisterdPositions()) {
			resultAxis.positionResolver().completeRegisterPositions();
		}
		$.ig.ResultViewHelper.prototype.setPositionIsExpaned(tupleIndex, memberIndex, isExpanded, resultAxis.positionResolver());
	}
	,
	setPositionIsExpaned: function (tupleIndex, memberIndex, isExpanded, targetResolver) {
		if (targetResolver.hasUnregisterdPositions()) {
			targetResolver.completeRegisterPositions();
		}
		var position = targetResolver.positions().__inner[tupleIndex];
		var hostPosition = targetResolver.getHostPositionItemInfo(position, memberIndex);
		var itemPosition = hostPosition.item(position.item(memberIndex).key());
		itemPosition.isExpanded(isExpanded);
	}
	,
	setExpansionStates1: function (source, target) {
		if (source.hasUnregisterdPositions()) {
			source.completeRegisterPositions();
		}
		if (target.hasUnregisterdPositions()) {
			target.completeRegisterPositions();
		}
		$.ig.ResultViewHelper.prototype.setExpansionStates(source.rootPositionInfo(), target.rootPositionInfo());
	}
	,
	setExpansionStates: function (source, target) {
		var en = source.getEnumerator();
		while (en.moveNext()) {
			var sourcePositionInfo = en.current();
			var sourceItem = sourcePositionInfo.value();
			var targetItem;
			if ((function () { var $ret = target.tryGetValue(sourcePositionInfo.key(), targetItem); targetItem = $ret.p1; return $ret.ret; }())) {
				if (sourceItem.isExpanded() != true) {
					targetItem.isExpanded(false);
				}
				if (targetItem.count() > 0) {
					$.ig.ResultViewHelper.prototype.setExpansionStates(sourceItem, targetItem);
				}
			}
		}
	}
	,
	$type: new $.ig.Type('ResultViewHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapTableViewHeaderCell', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.columnSpan(1);
		this.rowSpan(1);
	},
	_id: null,
	id: function (value) {
		if (arguments.length === 1) {
			this._id = value;
			return value;
		} else {
			return this._id;
		}
	}
	,
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
	_isExpanded: false,
	isExpanded: function (value) {
		if (arguments.length === 1) {
			this._isExpanded = value;
			return value;
		} else {
			return this._isExpanded;
		}
	}
	,
	_isExpandable: false,
	isExpandable: function (value) {
		if (arguments.length === 1) {
			this._isExpandable = value;
			return value;
		} else {
			return this._isExpandable;
		}
	}
	,
	_rowIndex: 0,
	rowIndex: function (value) {
		if (arguments.length === 1) {
			this._rowIndex = value;
			return value;
		} else {
			return this._rowIndex;
		}
	}
	,
	_rowSpan: 0,
	rowSpan: function (value) {
		if (arguments.length === 1) {
			this._rowSpan = value;
			return value;
		} else {
			return this._rowSpan;
		}
	}
	,
	_columnIndex: 0,
	columnIndex: function (value) {
		if (arguments.length === 1) {
			this._columnIndex = value;
			return value;
		} else {
			return this._columnIndex;
		}
	}
	,
	_columnSpan: 0,
	columnSpan: function (value) {
		if (arguments.length === 1) {
			this._columnSpan = value;
			return value;
		} else {
			return this._columnSpan;
		}
	}
	,
	_axisName: null,
	axisName: function (value) {
		if (arguments.length === 1) {
			this._axisName = value;
			return value;
		} else {
			return this._axisName;
		}
	}
	,
	_tupleIndex: 0,
	tupleIndex: function (value) {
		if (arguments.length === 1) {
			this._tupleIndex = value;
			return value;
		} else {
			return this._tupleIndex;
		}
	}
	,
	_memberIndex: 0,
	memberIndex: function (value) {
		if (arguments.length === 1) {
			this._memberIndex = value;
			return value;
		} else {
			return this._memberIndex;
		}
	}
	,
	_levelNumber: 0,
	levelNumber: function (value) {
		if (arguments.length === 1) {
			this._levelNumber = value;
			return value;
		} else {
			return this._levelNumber;
		}
	}
	,
	_hierarchyNumber: 0,
	hierarchyNumber: function (value) {
		if (arguments.length === 1) {
			this._hierarchyNumber = value;
			return value;
		} else {
			return this._hierarchyNumber;
		}
	}
	,
	_headerSpan: 0,
	headerSpan: function (value) {
		if (arguments.length === 1) {
			this._headerSpan = value;
			return value;
		} else {
			return this._headerSpan;
		}
	}
	,
	_rowOrigin: 0,
	rowOrigin: function (value) {
		if (arguments.length === 1) {
			this._rowOrigin = value;
			return value;
		} else {
			return this._rowOrigin;
		}
	}
	,
	toString: function () {
		return this.caption();
	}
	,
	$type: new $.ig.Type('OlapTableViewHeaderCell', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapTableViewResultCell', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_value: null,
	value: function (value) {
		if (arguments.length === 1) {
			this._value = value;
			return value;
		} else {
			return this._value;
		}
	}
	,
	_formattedValue: null,
	formattedValue: function (value) {
		if (arguments.length === 1) {
			this._formattedValue = value;
			return value;
		} else {
			return this._formattedValue;
		}
	}
	,
	_cellOrdinal: 0,
	cellOrdinal: function (value) {
		if (arguments.length === 1) {
			this._cellOrdinal = value;
			return value;
		} else {
			return this._cellOrdinal;
		}
	}
	,
	_resultCellIndex: 0,
	resultCellIndex: function (value) {
		if (arguments.length === 1) {
			this._resultCellIndex = value;
			return value;
		} else {
			return this._resultCellIndex;
		}
	}
	,
	$type: new $.ig.Type('OlapTableViewResultCell', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TableViewSettings', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_isParentInFrontForColumns: false,
	isParentInFrontForColumns: function (value) {
		if (arguments.length === 1) {
			this._isParentInFrontForColumns = value;
			return value;
		} else {
			return this._isParentInFrontForColumns;
		}
	}
	,
	_isParentInFrontForRows: false,
	isParentInFrontForRows: function (value) {
		if (arguments.length === 1) {
			this._isParentInFrontForRows = value;
			return value;
		} else {
			return this._isParentInFrontForRows;
		}
	}
	,
	_rowHeaderLayout: 0,
	rowHeaderLayout: function (value) {
		if (arguments.length === 1) {
			this._rowHeaderLayout = value;
			return value;
		} else {
			return this._rowHeaderLayout;
		}
	}
	,
	_compactColumnHeaders: false,
	compactColumnHeaders: function (value) {
		if (arguments.length === 1) {
			this._compactColumnHeaders = value;
			return value;
		} else {
			return this._compactColumnHeaders;
		}
	}
	,
	$type: new $.ig.Type('TableViewSettings', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapTableViewTreeHeaderCell', 'OlapTableViewHeaderCell', {
	init: function () {
		$.ig.OlapTableViewHeaderCell.prototype.init.call(this);
		this.parentMemberLevelNumber(-1);
	},
	_indent: 0,
	indent: function (value) {
		if (arguments.length === 1) {
			this._indent = value;
			return value;
		} else {
			return this._indent;
		}
	}
	,
	_parentMemberLevelNumber: 0,
	parentMemberLevelNumber: function (value) {
		if (arguments.length === 1) {
			this._parentMemberLevelNumber = value;
			return value;
		} else {
			return this._parentMemberLevelNumber;
		}
	}
	,
	_isItemExpanded: false,
	isItemExpanded: function (value) {
		if (arguments.length === 1) {
			this._isItemExpanded = value;
			return value;
		} else {
			return this._isItemExpanded;
		}
	}
	,
	_isItemExpandable: false,
	isItemExpandable: function (value) {
		if (arguments.length === 1) {
			this._isItemExpandable = value;
			return value;
		} else {
			return this._isItemExpandable;
		}
	}
	,
	$type: new $.ig.Type('OlapTableViewTreeHeaderCell', $.ig.OlapTableViewHeaderCell.prototype.$type)
}, true);

$.ig.util.defType('TreeLayoutTableViewSettings', 'TableViewSettings', {
	init: function (childrenAttributesIndentation, dimensionAttributesIndentation) {
		$.ig.TableViewSettings.prototype.init.call(this);
		this.rowHeaderLayout($.ig.RowHeaderLayout.prototype.tree);
		this.childrenAttributesIndentation(childrenAttributesIndentation);
		this.dimensionAttributesIndentation(dimensionAttributesIndentation);
	},
	_childrenAttributesIndentation: 0,
	childrenAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._childrenAttributesIndentation = value;
			return value;
		} else {
			return this._childrenAttributesIndentation;
		}
	}
	,
	_dimensionAttributesIndentation: 0,
	dimensionAttributesIndentation: function (value) {
		if (arguments.length === 1) {
			this._dimensionAttributesIndentation = value;
			return value;
		} else {
			return this._dimensionAttributesIndentation;
		}
	}
	,
	$type: new $.ig.Type('TreeLayoutTableViewSettings', $.ig.TableViewSettings.prototype.$type)
}, true);

$.ig.util.defType('IOlapDataSource', 'Object', {
	$type: new $.ig.Type('IOlapDataSource', null)
}, true);

$.ig.util.defType('OlapDataSource', 'Object', {
	__positionsToExpand: null,
	__positionsToCollapse: null,
	__hierarchiesToLoad: null,
	__measureListIndex: 0,
	__measureListLocation: 0,
	__dataProviderFactory: null,
	init: function (options) {
		this.__positionsToExpand = new $.ig.List$1($.ig.PositionInfo.prototype.$type, 0);
		this.__positionsToCollapse = new $.ig.List$1($.ig.PositionInfo.prototype.$type, 0);
		this.__hierarchiesToLoad = new $.ig.List$1(String, 0);
		this._membersSelectionChangedList = new $.ig.List$1(String, 0);
		$.ig.Object.prototype.init.call(this);
		this.columnFilters(new $.ig.List$1($.ig.AxisElement.prototype.$type, 0));
		this.rowFilters(new $.ig.List$1($.ig.AxisElement.prototype.$type, 0));
		this.filterFilters(new $.ig.List$1($.ig.AxisElement.prototype.$type, 0));
		this.isInitializing(false);
		this.initializeTask(null);
		this.isUpdating(false);
		this.updateTask(null);
		this.isInitialized(false);
		this.isColumnsAxisChanged(false);
		this.isRowsAxisChanged(false);
		this.isFiltersAxisChanged(false);
		this.isMeasuresAxisChanged(false);
		this.isMeasureListLocationChanged(false);
		this.isMeasureListIndexChanging(false);
		this.catalogs(new $.ig.ArrayListCollection$1($.ig.Catalog.prototype.$type));
		this.cubes(new $.ig.ArrayListCollection$1($.ig.Cube.prototype.$type));
		this.measureGroups(new $.ig.ArrayListCollection$1($.ig.MeasureGroup.prototype.$type));
		this.rowAxis(new $.ig.ArrayListCollection$1($.ig.ICoreOlapElement.prototype.$type));
		this.columnAxis(new $.ig.ArrayListCollection$1($.ig.ICoreOlapElement.prototype.$type));
		this.filters(new $.ig.ArrayListCollection$1($.ig.Hierarchy.prototype.$type));
		this.measures(new $.ig.ArrayListCollection$1($.ig.Measure.prototype.$type));
		this.measureListLocation($.ig.MeasureListLocation.prototype.columns);
		this.measureListIndex(-1);
		this.sourceOptions(options);
		this.result((function () {
			var $ret = new $.ig.OlapResult();
			$ret.isEmpty(true);
			return $ret;
		}()));
	},
	_isInitialized: false,
	isInitialized: function (value) {
		if (arguments.length === 1) {
			this._isInitialized = value;
			return value;
		} else {
			return this._isInitialized;
		}
	}
	,
	_catalogs: null,
	catalogs: function (value) {
		if (arguments.length === 1) {
			this._catalogs = value;
			return value;
		} else {
			return this._catalogs;
		}
	}
	,
	_cubes: null,
	cubes: function (value) {
		if (arguments.length === 1) {
			this._cubes = value;
			return value;
		} else {
			return this._cubes;
		}
	}
	,
	_cube: null,
	cube: function (value) {
		if (arguments.length === 1) {
			this._cube = value;
			return value;
		} else {
			return this._cube;
		}
	}
	,
	_measureGroup: null,
	measureGroup: function (value) {
		if (arguments.length === 1) {
			this._measureGroup = value;
			return value;
		} else {
			return this._measureGroup;
		}
	}
	,
	_measureGroups: null,
	measureGroups: function (value) {
		if (arguments.length === 1) {
			this._measureGroups = value;
			return value;
		} else {
			return this._measureGroups;
		}
	}
	,
	_sourceOptions: null,
	sourceOptions: function (value) {
		if (arguments.length === 1) {
			this._sourceOptions = value;
			return value;
		} else {
			return this._sourceOptions;
		}
	}
	,
	dataProviderFactory: function (value) {
		if (arguments.length === 1) {
			this.__dataProviderFactory = value;
			return value;
		} else {
			if (this.__dataProviderFactory == null) {
				this.__dataProviderFactory = this.createOlapDataProviderFactory();
			}
			return this.__dataProviderFactory;
		}
	}
	,
	_metadataTree: null,
	metadataTree: function (value) {
		if (arguments.length === 1) {
			this._metadataTree = value;
			return value;
		} else {
			return this._metadataTree;
		}
	}
	,
	_rowAxis: null,
	rowAxis: function (value) {
		if (arguments.length === 1) {
			this._rowAxis = value;
			return value;
		} else {
			return this._rowAxis;
		}
	}
	,
	_columnAxis: null,
	columnAxis: function (value) {
		if (arguments.length === 1) {
			this._columnAxis = value;
			return value;
		} else {
			return this._columnAxis;
		}
	}
	,
	_filters: null,
	filters: function (value) {
		if (arguments.length === 1) {
			this._filters = value;
			return value;
		} else {
			return this._filters;
		}
	}
	,
	_measures: null,
	measures: function (value) {
		if (arguments.length === 1) {
			this._measures = value;
			return value;
		} else {
			return this._measures;
		}
	}
	,
	measureListIndex: function (value) {
		if (arguments.length === 1) {
			if (this.__measureListIndex != value) {
				this.isMeasureListIndexChanging(true);
				this.__measureListIndex = value;
				this.updateMeasureList();
				this.isMeasureListIndexChanging(false);
			}
			return value;
		} else {
			return this.__measureListIndex;
		}
	}
	,
	measureListLocation: function (value) {
		if (arguments.length === 1) {
			if (this.__measureListLocation != value) {
				this.removeMeasureList();
				this.__measureListLocation = value;
				this.updateMeasureList();
			}
			return value;
		} else {
			return this.__measureListLocation;
		}
	}
	,
	_result: null,
	result: function (value) {
		if (arguments.length === 1) {
			this._result = value;
			return value;
		} else {
			return this._result;
		}
	}
	,
	_resultView: null,
	resultView: function (value) {
		if (arguments.length === 1) {
			this._resultView = value;
			return value;
		} else {
			return this._resultView;
		}
	}
	,
	_cubeCache: null,
	cubeCache: function (value) {
		if (arguments.length === 1) {
			this._cubeCache = value;
			return value;
		} else {
			return this._cubeCache;
		}
	}
	,
	_columnFilters: null,
	columnFilters: function (value) {
		if (arguments.length === 1) {
			this._columnFilters = value;
			return value;
		} else {
			return this._columnFilters;
		}
	}
	,
	_rowFilters: null,
	rowFilters: function (value) {
		if (arguments.length === 1) {
			this._rowFilters = value;
			return value;
		} else {
			return this._rowFilters;
		}
	}
	,
	_filterFilters: null,
	filterFilters: function (value) {
		if (arguments.length === 1) {
			this._filterFilters = value;
			return value;
		} else {
			return this._filterFilters;
		}
	}
	,
	_membersSelectionChangedList: null,
	_isInitializing: false,
	isInitializing: function (value) {
		if (arguments.length === 1) {
			this._isInitializing = value;
			return value;
		} else {
			return this._isInitializing;
		}
	}
	,
	_initializeTask: null,
	initializeTask: function (value) {
		if (arguments.length === 1) {
			this._initializeTask = value;
			return value;
		} else {
			return this._initializeTask;
		}
	}
	,
	_isUpdating: false,
	isUpdating: function (value) {
		if (arguments.length === 1) {
			this._isUpdating = value;
			return value;
		} else {
			return this._isUpdating;
		}
	}
	,
	_updateTask: null,
	updateTask: function (value) {
		if (arguments.length === 1) {
			this._updateTask = value;
			return value;
		} else {
			return this._updateTask;
		}
	}
	,
	_isColumnsAxisChanged: false,
	isColumnsAxisChanged: function (value) {
		if (arguments.length === 1) {
			this._isColumnsAxisChanged = value;
			return value;
		} else {
			return this._isColumnsAxisChanged;
		}
	}
	,
	_isRowsAxisChanged: false,
	isRowsAxisChanged: function (value) {
		if (arguments.length === 1) {
			this._isRowsAxisChanged = value;
			return value;
		} else {
			return this._isRowsAxisChanged;
		}
	}
	,
	_isFiltersAxisChanged: false,
	isFiltersAxisChanged: function (value) {
		if (arguments.length === 1) {
			this._isFiltersAxisChanged = value;
			return value;
		} else {
			return this._isFiltersAxisChanged;
		}
	}
	,
	_isMeasuresAxisChanged: false,
	isMeasuresAxisChanged: function (value) {
		if (arguments.length === 1) {
			this._isMeasuresAxisChanged = value;
			return value;
		} else {
			return this._isMeasuresAxisChanged;
		}
	}
	,
	_isMeasureListLocationChanged: false,
	isMeasureListLocationChanged: function (value) {
		if (arguments.length === 1) {
			this._isMeasureListLocationChanged = value;
			return value;
		} else {
			return this._isMeasureListLocationChanged;
		}
	}
	,
	_isMeasureListIndexChanging: false,
	isMeasureListIndexChanging: function (value) {
		if (arguments.length === 1) {
			this._isMeasureListIndexChanging = value;
			return value;
		} else {
			return this._isMeasureListIndexChanging;
		}
	}
	,
	getMembersOfLevel: function (levelUniqueName) {
	}
	,
	getMembersOfHierarchy: function (hierarchyUniqueName) {
	}
	,
	getMembersOfMember: function (memberUniqueName) {
	}
	,
	setCube: function (cubeName) {
		if (!this.isInitialized()) {
			throw new $.ig.InvalidOperationException(1, "Data source is not initialized.");
		}
		return this.onSetCube(cubeName);
	}
	,
	setMeasureGroup: function (measureGroupName) {
		if (!this.isInitialized()) {
			throw new $.ig.InvalidOperationException(1, "Data source is not initialized.");
		}
		return this.onSetMeasureGroup(measureGroupName);
	}
	,
	getCoreElement: function (predicate, elementType) {
		if (elementType == $.ig.MeasureList.prototype.$type) {
			var measureListAxis;
			if (this.measureListLocation() == $.ig.MeasureListLocation.prototype.columns) {
				measureListAxis = this.columnAxis();
			} else {
				measureListAxis = this.rowAxis();
			}
			var measureList = null;
			var en = measureListAxis.getEnumerator();
			while (en.moveNext()) {
				var coreOlapElement = en.current();
				measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, coreOlapElement);
				if (measureList != null) {
					break;
				}
			}
			return measureList;
		} else {
			return $.ig.Enumerable.prototype.firstOrDefault$1($.ig.ICoreOlapElement.prototype.$type, this.getCoreElements(predicate, elementType));
		}
	}
	,
	getCoreElements: function (predicate, elementType) {
		if (elementType == $.ig.Dimension.prototype.$type) {
			return this.toArrayListCollection$1($.ig.Dimension.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Dimension.prototype.$type, this.cubeCache().dimensionItems(), function (d) { return predicate(d); }));
		}
		if (elementType == $.ig.Hierarchy.prototype.$type) {
			return this.toArrayListCollection$1($.ig.Hierarchy.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Hierarchy.prototype.$type, this.cubeCache().hierarchyItems(), function (d) { return predicate(d); }));
		}
		if (elementType == $.ig.Level.prototype.$type) {
			return this.toArrayListCollection$1($.ig.Level.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Level.prototype.$type, this.cubeCache().levelItems(), function (d) { return predicate(d); }));
		}
		if (elementType == $.ig.Measure.prototype.$type) {
			return this.toArrayListCollection$1($.ig.Measure.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Measure.prototype.$type, this.cubeCache().measures(), function (d) { return predicate(d); }));
		}
		if (elementType == $.ig.KpiMeasure.prototype.$type) {
			return this.toArrayListCollection$1($.ig.KpiMeasure.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.KpiMeasure.prototype.$type, this.cubeCache().kpiMeasures(), function (d) { return predicate(d); }));
		}
		if (elementType == $.ig.Kpi.prototype.$type) {
			return this.toArrayListCollection$1($.ig.Kpi.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Kpi.prototype.$type, this.cubeCache().kpis(), function (d) { return predicate(d); }));
		}
		throw new $.ig.NotSupportedException(0, "Supported types are Dimension, Hierarchy, Level and Measure");
	}
	,
	expandTupleMember: function (axisName, tupleIndex, memberIndex) {
		var resultAxis = this.getResultAxis(axisName, this.result());
		if (resultAxis == null) {
			throw new $.ig.InvalidOperationException(1, "No axis found for the specified index.");
		}
		var positionInfo = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.PositionInfo.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.PositionInfo.prototype.$type, this.__positionsToExpand, function (pi) { return pi.axisName() == axisName && pi.tupleIndex() == tupleIndex && pi.memberIndex() == memberIndex; }));
		if (positionInfo == null) {
			positionInfo = (function () {
				var $ret = new $.ig.PositionInfo();
				$ret.axisName(axisName);
				$ret.memberIndex(memberIndex);
				$ret.tupleIndex(tupleIndex);
				return $ret;
			}());
			this.__positionsToExpand.add(positionInfo);
		} else {
			this.__positionsToExpand.remove(positionInfo);
		}
	}
	,
	collapseTupleMember: function (axisName, tupleIndex, memberIndex) {
		var resultAxis = this.getResultAxis(axisName, this.result());
		if (resultAxis == null) {
			throw new $.ig.InvalidOperationException(1, "No axis found for the specified index.");
		}
		var positionInfo = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.PositionInfo.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.PositionInfo.prototype.$type, this.__positionsToExpand, function (pi) { return pi.axisName() == axisName && pi.tupleIndex() == tupleIndex && pi.memberIndex() == memberIndex; }));
		if (positionInfo == null) {
			positionInfo = (function () {
				var $ret = new $.ig.PositionInfo();
				$ret.axisName(axisName);
				$ret.memberIndex(memberIndex);
				$ret.tupleIndex(tupleIndex);
				return $ret;
			}());
			this.__positionsToCollapse.add(positionInfo);
		} else {
			this.__positionsToExpand.remove(positionInfo);
		}
	}
	,
	clearPendingChanges: function () {
		this.__positionsToCollapse.clear();
		this.__positionsToExpand.clear();
	}
	,
	update: function () {
		var $self = this;
		if (this.isUpdating()) {
			return this.updateTask();
		}
		this.isUpdating(true);
		var task = null;
		if (this.__positionsToExpand.count() > 0) {
			task = this.loadExpandedMemberSiblings(this.__positionsToExpand);
		}
		if (this.__hierarchiesToLoad.count() > 0) {
			var tcs = new $.ig.TaskCompletionSource$1(Array, 0);
			var tasks = new Array(this.__hierarchiesToLoad.count());
			for (var i = 0; i < tasks.length; i++) {
				tasks[i] = this.getMembersOfHierarchy(this.__hierarchiesToLoad.__inner[i]);
			}
			this.__hierarchiesToLoad.clear();
			var tf = new $.ig.TaskFactory();
			tf.continueWhenAll(tasks, tcs.setResult.runOn(tcs));
			if (task != null) {
				task = $.ig.TaskExtensions.prototype.continueWithTask$1(Array, task, function (t) { return tcs.task(); });
			} else {
				task = tcs.task();
			}
		}
		if (task != null) {
			task = $.ig.TaskExtensions.prototype.continueWithTask$1($.ig.OlapResult.prototype.$type, task, function (t) { return $self.onUpdate(); });
		} else {
			task = this.onUpdate();
		}
		this.updateTask(task);
		this.updateTask().continueWith1(function (t) {
			$self.isUpdating(false);
			$self.updateTask(null);
		});
		return task;
	}
	,
	initialize: function () {
		var $self = this;
		if (this.isInitializing()) {
			return this.initializeTask();
		}
		this.isInitializing(true);
		this.initializeTask(this.onInitialize());
		var task = this.initializeTask();
		this.initializeTask().continueWith1(function (t) {
			$self.isInitializing(false);
			$self.initializeTask(null);
		});
		return task;
	}
	,
	addFilterMember: function (hierarchyUniqueName, memberUniqueName) {
		var $self = this;
		var axisElementsCollection;
		var axisElement = (function () { var $ret = $self.findAxisElement(hierarchyUniqueName, axisElementsCollection); axisElementsCollection = $ret.p1; return $ret.ret; }());
		if (axisElement == null) {
			axisElement = new $.ig.AxisElement(hierarchyUniqueName, (function () {
				var $ret = new $.ig.ArrayListCollection$1(String);
				$ret.add(memberUniqueName);
				return $ret;
			}()));
			axisElementsCollection.add(axisElement);
		}
		if (!axisElement.memberNames().contains(memberUniqueName)) {
			axisElement.memberNames().add(memberUniqueName);
			if (this._membersSelectionChangedList.contains(memberUniqueName)) {
				this._membersSelectionChangedList.remove(memberUniqueName);
			} else {
				this._membersSelectionChangedList.add(memberUniqueName);
			}
		}
		var member;
		if (!(function () { var $ret = $self.cubeCache().tryGetMember1(memberUniqueName, member); member = $ret.p1; return $ret.ret; }()) && !this.__hierarchiesToLoad.contains(hierarchyUniqueName)) {
			this.__hierarchiesToLoad.add(hierarchyUniqueName);
		}
	}
	,
	getFilterMemberNames: function (hierarchyUniqueName) {
		var $self = this;
		var axisElementsCollection;
		var axisElement = (function () { var $ret = $self.findAxisElement(hierarchyUniqueName, axisElementsCollection); axisElementsCollection = $ret.p1; return $ret.ret; }());
		if (axisElement != null) {
			return this.toArrayListCollection$1(String, axisElement.memberNames());
		}
		return new $.ig.ArrayListCollection$1(String);
	}
	,
	removeFilterMember: function (hierarchyUniqueName, memberUniqueName) {
		var $self = this;
		var axisElementsCollection;
		var axisElement = (function () { var $ret = $self.findAxisElement(hierarchyUniqueName, axisElementsCollection); axisElementsCollection = $ret.p1; return $ret.ret; }());
		if (axisElement == null) {
			return;
		}
		if (axisElement.memberNames().remove(memberUniqueName)) {
			if (this._membersSelectionChangedList.contains(memberUniqueName)) {
				this._membersSelectionChangedList.remove(memberUniqueName);
			} else {
				this._membersSelectionChangedList.add(memberUniqueName);
			}
		}
	}
	,
	removeAllFilterMembers: function (hierarchyUniqueName) {
		var $self = this;
		var axisElementsCollection;
		var axisElement = (function () { var $ret = $self.findAxisElement(hierarchyUniqueName, axisElementsCollection); axisElementsCollection = $ret.p1; return $ret.ret; }());
		if (axisElement == null) {
			return;
		}
		for (var i = axisElement.memberNames().count() - 1; i >= 0; i--) {
			var memberName = axisElement.memberNames().item(i);
			axisElement.memberNames().removeAt(i);
			if (this._membersSelectionChangedList.contains(memberName)) {
				this._membersSelectionChangedList.remove(memberName);
			} else {
				this._membersSelectionChangedList.add(memberName);
			}
		}
	}
	,
	getProviderDefaultProperties: function () {
		var properties = new $.ig.List$1($.ig.KeyValueItem.prototype.$type, 0);
		return properties;
	}
	,
	getProviderDefaultRestrictions: function () {
		var restrictions = new $.ig.List$1($.ig.KeyValueItem.prototype.$type, 0);
		return restrictions;
	}
	,
	tryGetMember: function (memberUniqueName, member) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.cubeCache().tryGetMember1(memberUniqueName, member); member = $ret.p1; return $ret.ret; }()),
			p1: member
		};
	}
	,
	tryGetMembersForLevel: function (levelUniqueName, members) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.cubeCache().tryGetMembersForLevel(levelUniqueName, members); members = $ret.p1; return $ret.ret; }()),
			p1: members
		};
	}
	,
	onInitialize: function () {
	}
	,
	onUpdate: function () {
	}
	,
	createOlapDataProviderFactory: function () {
	}
	,
	cacheCubeMetaItems: function () {
	}
	,
	onSetCube: function (cubeName) {
		var $self = this;
		if (String.isNullOrEmpty(cubeName)) {
			if (cubeName == null) {
				throw new $.ig.ArgumentNullException(0, "cubeName");
			}
			throw new $.ig.InvalidOperationException(1, "cubeName cannot be empty.");
		}
		var setCubeCompleted = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		if (this.cube() != null) {
			if (this.cube().name() == cubeName) {
				setCubeCompleted.setResult(this.metadataTree());
				return setCubeCompleted.task();
			}
			this.clearCubeCache();
		}
		var en = this.cubes().getEnumerator();
		while (en.moveNext()) {
			var cube = en.current();
			if (cube.name() == cubeName) {
				this.cube(cube);
				break;
			}
		}
		var cacheCubeItemsTask = this.cacheCubeMetaItems();
		if (!this.isInitialized()) {
			var measureGroupName = this.sourceOptions().measureGroup();
			if (!String.isNullOrEmpty(measureGroupName)) {
				return $.ig.TaskExtensions.prototype.continueWithTask$1($.ig.OlapMetadataTreeItem.prototype.$type, cacheCubeItemsTask, function (t) { return $self.onSetMeasureGroup(measureGroupName); });
			}
		}
		return $.ig.TaskExtensions.prototype.continueWithTask$1($.ig.OlapMetadataTreeItem.prototype.$type, cacheCubeItemsTask, function (t1) { return $self.onSetMeasureGroup(null); });
	}
	,
	initializeAxes: function () {
		var $self = this;
		var columnElements = null;
		var rowElements = null;
		var filterElements = null;
		var hierarchiesWithFilters = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		if (!String.isNullOrEmpty(this.sourceOptions().columns())) {
			var columnsParser = new $.ig.AxisDefinitionParser(this.sourceOptions().columns());
			columnElements = columnsParser.parse();
		}
		if (!String.isNullOrEmpty(this.sourceOptions().rows())) {
			var rowsParser = new $.ig.AxisDefinitionParser(this.sourceOptions().rows());
			rowElements = rowsParser.parse();
		}
		if (!String.isNullOrEmpty(this.sourceOptions().filters())) {
			var filtersParser = new $.ig.AxisDefinitionParser(this.sourceOptions().filters());
			filterElements = filtersParser.parse();
		}
		if (columnElements != null && columnElements.count() > 0) {
			var en = columnElements.getEnumerator();
			while (en.moveNext()) {
				var axisElement = en.current();
				this.addCoreElementToAxis$1($.ig.ICoreOlapElement.prototype.$type, axisElement.name(), this.columnAxis(), $.ig.Hierarchy.prototype.$type);
				this.columnFilters().add(axisElement);
				if (axisElement.memberNames().count() > 0) {
					hierarchiesWithFilters.add(axisElement);
					var en1 = axisElement.memberNames().getEnumerator();
					while (en1.moveNext()) {
						var memberName = en1.current();
						this._membersSelectionChangedList.add(memberName);
					}
				}
			}
		}
		if (rowElements != null && rowElements.count() > 0) {
			var en2 = rowElements.getEnumerator();
			while (en2.moveNext()) {
				var axisElement1 = en2.current();
				this.addCoreElementToAxis$1($.ig.ICoreOlapElement.prototype.$type, axisElement1.name(), this.rowAxis(), $.ig.Hierarchy.prototype.$type);
				this.rowFilters().add(axisElement1);
				if (axisElement1.memberNames().count() > 0) {
					hierarchiesWithFilters.add(axisElement1);
					var en3 = axisElement1.memberNames().getEnumerator();
					while (en3.moveNext()) {
						var memberName1 = en3.current();
						this._membersSelectionChangedList.add(memberName1);
					}
				}
			}
		}
		if (filterElements != null && filterElements.count() > 0) {
			var en4 = filterElements.getEnumerator();
			while (en4.moveNext()) {
				var axisElement2 = en4.current();
				this.addCoreElementToAxis$1($.ig.Hierarchy.prototype.$type, axisElement2.name(), this.filters(), $.ig.Hierarchy.prototype.$type);
				this.filterFilters().add(axisElement2);
				if (axisElement2.memberNames().count() > 0) {
					hierarchiesWithFilters.add(axisElement2);
					var en5 = axisElement2.memberNames().getEnumerator();
					while (en5.moveNext()) {
						var memberName2 = en5.current();
						this._membersSelectionChangedList.add(memberName2);
					}
				}
			}
		}
		var measureElements = null;
		if (!String.isNullOrEmpty(this.sourceOptions().measures())) {
			var measuresParser = new $.ig.AxisDefinitionParser(this.sourceOptions().measures());
			measureElements = measuresParser.parse();
		}
		if (measureElements != null && measureElements.count() > 0) {
			var en6 = measureElements.getEnumerator();
			while (en6.moveNext()) {
				var measureElement = en6.current();
				this.addCoreElementToAxis$1($.ig.Measure.prototype.$type, measureElement.name(), this.measures(), $.ig.Measure.prototype.$type);
			}
		}
		if (this.isColumnsAxisChanged() || this.isRowsAxisChanged() || this.isFiltersAxisChanged() || this.isMeasuresAxisChanged() || this.isMeasureListIndexChanging()) {
			if (hierarchiesWithFilters.count() > 0) {
				var tasksToComplete = new Array(hierarchiesWithFilters.count());
				for (var i = 0; i < hierarchiesWithFilters.count(); i++) {
					var hierarchyName = hierarchiesWithFilters.item(i).name();
					tasksToComplete[i] = this.getMembersOfHierarchy(hierarchyName);
				}
				var tcs1 = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
				var tf = new $.ig.TaskFactory();
				tf.continueWhenAll(tasksToComplete, function (t) {
					$self.onUpdate().continueWith1(function (t1) {
						$self.initializeCompleted();
						tcs1.setResult($self.metadataTree());
					});
				});
				return tcs1.task();
			}
			return $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.OlapResult.prototype.$type, $.ig.OlapMetadataTreeItem.prototype.$type, this.onUpdate(), function (t) {
				$self.initializeCompleted();
				var tcs1 = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
				tcs1.setResult($self.metadataTree());
				return tcs1.task();
			});
		}
		this.initializeCompleted();
		var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		tcs.setResult(this.metadataTree());
		return tcs.task();
	}
	,
	loadCubes: function (catalogName) {
		var $self = this;
		var provider = this.dataProviderFactory().createDataProvider();
		var restrictions = this.getProviderDefaultRestrictions();
		var properties = this.getProviderDefaultProperties();
		var task = provider.discoverCubesAsync(properties, restrictions).continueWith$11($.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), function (t) {
			$self.cubes($self.toArrayListCollection$1($.ig.Cube.prototype.$type, t.result()));
			return $self.cubes();
		});
		return task;
	}
	,
	loadMeasureGroups: function () {
		var $self = this;
		var provider = this.dataProviderFactory().createDataProvider();
		var restrictions = this.getProviderDefaultRestrictions();
		var properties = this.getProviderDefaultProperties();
		var task = provider.discoverMeasureGroupsAsync(properties, restrictions).continueWith$11($.ig.IList$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type), function (t) {
			$self.measureGroups($self.toArrayListCollection$1($.ig.MeasureGroup.prototype.$type, t.result()));
			return $self.measureGroups();
		});
		return task;
	}
	,
	initializeCompleted: function () {
		this.isInitialized(true);
	}
	,
	clearCubeCache: function () {
		this.cube(null);
		this.measureGroups(new $.ig.ArrayListCollection$1($.ig.MeasureGroup.prototype.$type));
		this.filters().clear();
		this.rowAxis().clear();
		this.columnAxis().clear();
		this.measures().clear();
		this.cubeCache(null);
		this.clearMeasureGroupCache();
	}
	,
	clearMeasureGroupCache: function () {
		this.measureGroup(null);
		this.metadataTree(null);
	}
	,
	onSetMeasureGroup: function (measureGroupName) {
		var $self = this;
		if (this.cube() == null) {
			throw new $.ig.InvalidOperationException(1, "Data source has no Cube set.");
		}
		var setMeasueGroupCompleted = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		if (this.measureGroup() != null) {
			if (this.measureGroup().name() == measureGroupName) {
				setMeasueGroupCompleted.setResult(this.metadataTree());
				return setMeasueGroupCompleted.task();
			}
			this.clearMeasureGroupCache();
		}
		if (!String.isNullOrEmpty(measureGroupName) && this.measureGroups().count() == 0) {
			var setMeasureGroupTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.IList$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type), $.ig.OlapMetadataTreeItem.prototype.$type, this.loadMeasureGroups(), function (t) {
				if ($self.measureGroups().count() == 0) {
					return $self.onSetMeasureGroup(null);
				}
				return $self.onSetMeasureGroup(measureGroupName);
			});
			return setMeasureGroupTask;
		}
		var en = this.measureGroups().getEnumerator();
		while (en.moveNext()) {
			var measureGroup = en.current();
			if (measureGroup.name() == measureGroupName) {
				this.measureGroup(measureGroup);
				break;
			}
		}
		this.metadataTree(this.createMetadataTree(measureGroupName));
		setMeasueGroupCompleted.setResult(this.metadataTree());
		return setMeasueGroupCompleted.task();
	}
	,
	createMetadataTree: function (measureGroupName) {
		var $self = this;
		var cubeRoot = (function () {
			var $ret = new $.ig.OlapMetadataTreeItem();
			$ret.type($.ig.OlapMetadataTreeItemType.prototype.cube);
			$ret.caption($self.cube().caption());
			$ret.item($self.cube());
			return $ret;
		}());
		var dimensions;
		var measures;
		var kpis;
		var createMeasureGroupFolders = true;
		if (String.isNullOrEmpty(measureGroupName) || this.measureGroups() == null) {
			dimensions = this.cubeCache().dimensionItems();
			measures = this.cubeCache().measures();
			kpis = this.cubeCache().kpis();
		} else {
			createMeasureGroupFolders = false;
			dimensions = this.cubeCache().measureGroupDimensionsCache().getMeasureGroupDimensions(measureGroupName);
			measures = this.cubeCache().measureGroupDimensionsCache().getMeasureGroupMeasures(measureGroupName);
			kpis = this.cubeCache().measureGroupDimensionsCache().getMeasureGroupKpis(measureGroupName);
		}
		var dimensionMetaItems = $.ig.MetaTreeHelper.prototype.getDimensionMetaItems(dimensions, measures, kpis, this.cubeCache().hierarchyItems(), this.cubeCache().levelItems(), createMeasureGroupFolders, this.cubeCache());
		$.ig.MetaTreeHelper.prototype.setMetaItemChildren(cubeRoot, dimensionMetaItems);
		return cubeRoot;
	}
	,
	getResultAxis: function (axisName, result) {
		if (result != null && result.axes().count() > 0) {
			return $.ig.Enumerable.prototype.firstOrDefault$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, result.axes(), function (ax) { return ax.name() == axisName; }));
		}
		return null;
	}
	,
	updateAxisState: function (changedEventArgs, filters) {
		switch (changedEventArgs.action()) {
			case $.ig.NotifyCollectionChangedAction.prototype.add:
				var en = changedEventArgs.newItems().getEnumerator();
				while (en.moveNext()) {
					var newItem = en.current();
					var hierarchy = $.ig.util.cast($.ig.Hierarchy.prototype.$type, newItem);
					if (hierarchy != null) {
						if (this.isInitialized()) {
							filters.add(new $.ig.AxisElement(hierarchy.uniqueName(), new $.ig.ArrayListCollection$1(String)));
						}
					}
				}
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.remove:
				var en1 = changedEventArgs.oldItems().getEnumerator();
				while (en1.moveNext()) {
					var oldItem = en1.current();
					var hierarchy1 = $.ig.util.cast($.ig.Hierarchy.prototype.$type, oldItem);
					if (hierarchy1 != null) {
						var axisElement = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.AxisElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.AxisElement.prototype.$type, filters, function (ae) { return ae.name() == hierarchy1.uniqueName(); }));
						if (axisElement != null) {
							var memberNames = new $.ig.List$1(String, 0);
							for (var i = 0; i < axisElement.memberNames().count(); i++) {
								memberNames.add(axisElement.memberNames().item(i));
							}
							for (var i1 = 0; i1 < memberNames.count(); i1++) {
								var memberName = memberNames.item(i1);
								this.removeFilterMember(axisElement.name(), memberName);
							}
							filters.remove(axisElement);
						}
					} else {
						var measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, oldItem);
						if (measureList != null) {
							if (!this.isMeasuresAxisChanged() && !this.isMeasureListLocationChanged() && !this.isMeasureListIndexChanging()) {
								this.measures().clear();
							}
						}
					}
				}
				break;
		}
	}
	,
	updateMeasureListIndex: function (changedEventArgs, filters) {
		switch (changedEventArgs.action()) {
			case $.ig.NotifyCollectionChangedAction.prototype.add:
				var en = changedEventArgs.newItems().getEnumerator();
				while (en.moveNext()) {
					var newItem = en.current();
					var hierarchy = $.ig.util.cast($.ig.Hierarchy.prototype.$type, newItem);
					if (hierarchy != null) {
						var measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, $.ig.Enumerable.prototype.firstOrDefault$1($.ig.ICoreOlapElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.ICoreOlapElement.prototype.$type, filters, function (x) { return $.ig.util.cast($.ig.MeasureList.prototype.$type, x) !== null; })));
						if (measureList != null && this.measureListIndex() >= changedEventArgs.newStartingIndex()) {
							this.measureListIndex(this.measureListIndex() + 1);
						}
					}
				}
				break;
			case $.ig.NotifyCollectionChangedAction.prototype.remove:
				var en1 = changedEventArgs.oldItems().getEnumerator();
				while (en1.moveNext()) {
					var oldItem = en1.current();
					var hierarchy1 = $.ig.util.cast($.ig.Hierarchy.prototype.$type, oldItem);
					if (hierarchy1 != null) {
						var measureList1 = $.ig.util.cast($.ig.MeasureList.prototype.$type, $.ig.Enumerable.prototype.firstOrDefault$1($.ig.ICoreOlapElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.ICoreOlapElement.prototype.$type, filters, function (x) { return $.ig.util.cast($.ig.MeasureList.prototype.$type, x) !== null; })));
						if (measureList1 != null && this.measureListIndex() > changedEventArgs.oldStartingIndex()) {
							this.measureListIndex(this.measureListIndex() - 1);
						}
					}
				}
				break;
		}
	}
	,
	updateMeasureList: function () {
		var $self = this;
		var measureListAxis;
		if (this.measureListLocation() == $.ig.MeasureListLocation.prototype.columns) {
			measureListAxis = this.columnAxis();
		} else {
			measureListAxis = this.rowAxis();
		}
		var measureList = null;
		var measureListIndex = 0;
		var en = measureListAxis.getEnumerator();
		while (en.moveNext()) {
			var coreOlapElement = en.current();
			measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, coreOlapElement);
			if (measureList != null) {
				break;
			}
			measureListIndex++;
		}
		if (this.measures().count() > 1) {
			if (measureList == null) {
				measureList = (function () {
					var $ret = new $.ig.MeasureList();
					$ret.measures($self.measures());
					return $ret;
				}());
				if (this.measureListIndex() < 0 || this.measureListIndex() > measureListAxis.count()) {
					this.__measureListIndex = measureListAxis.count();
					measureListAxis.add(measureList);
				} else {
					measureListAxis.insert(this.measureListIndex(), measureList);
				}
			} else {
				if (measureListIndex != this.measureListIndex()) {
					measureListAxis.removeAt(measureListIndex);
					if (this.measureListIndex() > measureListAxis.count()) {
						this.__measureListIndex = measureListAxis.count();
						measureListAxis.add(measureList);
					} else {
						measureListAxis.insert(this.measureListIndex(), measureList);
					}
				}
			}
		} else {
			if (measureList != null) {
				measureListAxis.removeAt(measureListIndex);
			}
		}
	}
	,
	getPositionsToExpand: function () {
		return this.__positionsToExpand;
	}
	,
	getPositionsToCollapse: function () {
		return this.__positionsToCollapse;
	}
	,
	addCoreElementToAxis$1: function ($tItem, elementUniqueName, axisItems, elementType) {
		var predicate = function (ce) { return ($.ig.util.castObjTo$t($tItem, ce)).uniqueName() == elementUniqueName; };
		var coreElement = this.getCoreElement(predicate, elementType);
		if (coreElement != null && !$.ig.Enumerable.prototype.contains$1($.ig.Object.prototype.$type, axisItems, coreElement)) {
			axisItems.add($.ig.util.castObjTo$t($tItem, coreElement));
		}
	}
	,
	loadExpandedMemberSiblings: function (positionsToExpand) {
		var levelNames = new $.ig.List$1(String, 0);
		var en = positionsToExpand.getEnumerator();
		while (en.moveNext()) {
			var positionInfo = en.current();
			var axisMember = this.getResultAxis(positionInfo.axisName(), this.result()).tuples().__inner[positionInfo.tupleIndex()].members().item(positionInfo.memberIndex());
			var nextLevel = this.getCoreElement(function (l) { return (l).hierarchyUniqueName() == axisMember.hierarchyUniqueName() && (l).depth() == axisMember.levelNumber() + 1; }, $.ig.Level.prototype.$type);
			if (nextLevel != null && !this.cubeCache().isLevelLoaded(nextLevel.uniqueName()) && !levelNames.contains(nextLevel.uniqueName())) {
				levelNames.add(nextLevel.uniqueName());
			}
		}
		if (levelNames.count() > 0) {
			var loadLevelTasks = new Array(levelNames.count());
			for (var i = 0; i < levelNames.count(); i++) {
				loadLevelTasks[i] = this.getMembersOfLevel(levelNames.item(i));
			}
			return new $.ig.TaskFactory().continueWhenAll$1(Array, loadLevelTasks, function (t) { return t; });
		}
		var tcs = new $.ig.TaskCompletionSource$1(Array, 0);
		tcs.setResult(new Array(0));
		return tcs.task();
	}
	,
	removeMeasureList: function () {
		var measureListAxis;
		if (this.measureListLocation() == $.ig.MeasureListLocation.prototype.columns) {
			measureListAxis = this.columnAxis();
		} else {
			measureListAxis = this.rowAxis();
		}
		if (measureListAxis == null) {
			return;
		}
		var measureList = null;
		var measureListIndex = 0;
		var en = measureListAxis.getEnumerator();
		while (en.moveNext()) {
			var coreOlapElement = en.current();
			measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, coreOlapElement);
			if (measureList != null) {
				break;
			}
			measureListIndex++;
		}
		if (measureList != null) {
			this.isMeasureListLocationChanged(true);
			measureListAxis.removeAt(measureListIndex);
		}
	}
	,
	toArrayListCollection$1: function ($t, enumerable) {
		var res = new $.ig.ArrayListCollection$1($t);
		var en = enumerable.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			res.add(item);
		}
		return res;
	}
	,
	findAxisElement: function (hierarchyUniqueName, filtersCollection) {
		var axisElement = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.AxisElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.AxisElement.prototype.$type, this.columnFilters(), function (ae) { return ae.name() == hierarchyUniqueName; }));
		if (axisElement != null) {
			filtersCollection = this.columnFilters();
			return {
				ret: axisElement,
				p1: filtersCollection
			};
		}
		axisElement = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.AxisElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.AxisElement.prototype.$type, this.rowFilters(), function (ae) { return ae.name() == hierarchyUniqueName; }));
		if (axisElement != null) {
			filtersCollection = this.rowFilters();
			return {
				ret: axisElement,
				p1: filtersCollection
			};
		}
		axisElement = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.AxisElement.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.AxisElement.prototype.$type, this.filterFilters(), function (ae) { return ae.name() == hierarchyUniqueName; }));
		if (axisElement != null) {
			filtersCollection = this.filterFilters();
			return {
				ret: axisElement,
				p1: filtersCollection
			};
		}
		filtersCollection = null;
		return {
			ret: null,
			p1: filtersCollection
		};
	}
	,
	$type: new $.ig.Type('OlapDataSource', $.ig.Object.prototype.$type, [$.ig.IOlapDataSource.prototype.$type])
}, true);

$.ig.util.defType('PositionInfo', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_axisName: null,
	axisName: function (value) {
		if (arguments.length === 1) {
			this._axisName = value;
			return value;
		} else {
			return this._axisName;
		}
	}
	,
	_tupleIndex: 0,
	tupleIndex: function (value) {
		if (arguments.length === 1) {
			this._tupleIndex = value;
			return value;
		} else {
			return this._tupleIndex;
		}
	}
	,
	_memberIndex: 0,
	memberIndex: function (value) {
		if (arguments.length === 1) {
			this._memberIndex = value;
			return value;
		} else {
			return this._memberIndex;
		}
	}
	,
	$type: new $.ig.Type('PositionInfo', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CubeMetaItemsCache', 'Object', {
	init: function () {
		this.__membersCache = new $.ig.Dictionary$2(String, $.ig.Tuple$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.Member.prototype.$type), 0);
		this.__membersIdCache = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Member.prototype.$type, 0);
		this.__membersForLevels = new $.ig.Dictionary$2(String, $.ig.IList$1.prototype.$type.specialize($.ig.Member.prototype.$type), 0);
		this.__membersForMember = new $.ig.Dictionary$2(String, $.ig.IList$1.prototype.$type.specialize($.ig.Member.prototype.$type), 0);
		this.__measuresCache = new $.ig.Dictionary$2(String, $.ig.Measure.prototype.$type, 0);
		this.__kpiMeasuresCache = new $.ig.Dictionary$2(String, $.ig.KpiMeasure.prototype.$type, 0);
		this.__loadedLevels = new $.ig.Dictionary$2(String, $.ig.Boolean.prototype.$type, 0);
		this.__loadedHierarchies = new $.ig.Dictionary$2(String, $.ig.Boolean.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
	},
	__membersCache: null,
	__membersIdCache: null,
	__membersForLevels: null,
	__membersForMember: null,
	__measuresCache: null,
	__kpiMeasuresCache: null,
	__loadedLevels: null,
	__loadedHierarchies: null,
	_measures: null,
	measures: function (value) {
		if (arguments.length === 1) {
			this._measures = value;
			return value;
		} else {
			return this._measures;
		}
	}
	,
	_kpiMeasures: null,
	kpiMeasures: function (value) {
		if (arguments.length === 1) {
			this._kpiMeasures = value;
			return value;
		} else {
			return this._kpiMeasures;
		}
	}
	,
	_dimensionItems: null,
	dimensionItems: function (value) {
		if (arguments.length === 1) {
			this._dimensionItems = value;
			return value;
		} else {
			return this._dimensionItems;
		}
	}
	,
	_hierarchyItems: null,
	hierarchyItems: function (value) {
		if (arguments.length === 1) {
			this._hierarchyItems = value;
			return value;
		} else {
			return this._hierarchyItems;
		}
	}
	,
	_levelItems: null,
	levelItems: function (value) {
		if (arguments.length === 1) {
			this._levelItems = value;
			return value;
		} else {
			return this._levelItems;
		}
	}
	,
	_kpis: null,
	kpis: function (value) {
		if (arguments.length === 1) {
			this._kpis = value;
			return value;
		} else {
			return this._kpis;
		}
	}
	,
	_measureGroupDimensions: null,
	measureGroupDimensions: function (value) {
		if (arguments.length === 1) {
			this._measureGroupDimensions = value;
			return value;
		} else {
			return this._measureGroupDimensions;
		}
	}
	,
	_measureGroupDimensionsCache: null,
	measureGroupDimensionsCache: function (value) {
		if (arguments.length === 1) {
			this._measureGroupDimensionsCache = value;
			return value;
		} else {
			return this._measureGroupDimensionsCache;
		}
	}
	,
	isLevelLoaded: function (levelUniqueName) {
		var $self = this;
		var loaded;
		if (!(function () { var $ret = $self.__loadedLevels.tryGetValue(levelUniqueName, loaded); loaded = $ret.p1; return $ret.ret; }())) {
			return false;
		}
		return loaded;
	}
	,
	setIsLevelLoaded: function (levelUniqueName) {
		this.__loadedLevels.item(levelUniqueName, true);
	}
	,
	isHierarchyLoaded: function (hierarchyUniqueName) {
		var $self = this;
		var loaded;
		if (!(function () { var $ret = $self.__loadedHierarchies.tryGetValue(hierarchyUniqueName, loaded); loaded = $ret.p1; return $ret.ret; }())) {
			return false;
		}
		return loaded;
	}
	,
	setIsHierarchyLoaded: function (hierarchyUniqueName) {
		this.__loadedHierarchies.item(hierarchyUniqueName, true);
		var en = $.ig.Enumerable.prototype.where$1($.ig.Level.prototype.$type, this.levelItems(), function (l) { return l.hierarchyUniqueName() == hierarchyUniqueName; }).getEnumerator();
		while (en.moveNext()) {
			var level = en.current();
			this.setIsLevelLoaded(level.uniqueName());
		}
	}
	,
	isMemberLoaded: function (levelUniqueName) {
		return this.__membersForMember.containsKey(levelUniqueName);
	}
	,
	addMember: function (member) {
		var $self = this;
		var memberEntity;
		if (!(function () { var $ret = $self.__membersCache.tryGetValue(member.uniqueName(), memberEntity); memberEntity = $ret.p1; return $ret.ret; }())) {
			var memberId = this.__membersCache.count();
			memberEntity = new $.ig.Tuple$2($.ig.Number.prototype.$type, $.ig.Member.prototype.$type, memberId, member);
			this.__membersCache.item(member.uniqueName(), memberEntity);
			this.__membersIdCache.item(memberId, member);
			var membersForLevel;
			if (!(function () { var $ret = $self.__membersForLevels.tryGetValue(member.levelUniqueName(), membersForLevel); membersForLevel = $ret.p1; return $ret.ret; }())) {
				membersForLevel = new $.ig.ArrayListCollection$1($.ig.Member.prototype.$type);
				this.__membersForLevels.item(member.levelUniqueName(), membersForLevel);
			}
			if (!String.isNullOrEmpty(member.parentUniqueName())) {
				var membersForMember;
				if (!(function () { var $ret = $self.__membersForMember.tryGetValue(member.parentUniqueName(), membersForMember); membersForMember = $ret.p1; return $ret.ret; }())) {
					membersForMember = new $.ig.List$1($.ig.Member.prototype.$type, 0);
					this.__membersForMember.item(member.parentUniqueName(), membersForMember);
				}
				membersForMember.add(member);
			}
			membersForLevel.add(member);
		}
		return memberEntity.item1();
	}
	,
	tryGetMemberOrdinal: function (memberUniqueName, memberOrdinal) {
		var $self = this;
		var memberEntity;
		if ((function () { var $ret = $self.__membersCache.tryGetValue(memberUniqueName, memberEntity); memberEntity = $ret.p1; return $ret.ret; }())) {
			memberOrdinal = memberEntity.item1();
			return {
				ret: true,
				p1: memberOrdinal
			};
		}
		memberOrdinal = -1;
		return {
			ret: false,
			p1: memberOrdinal
		};
	}
	,
	tryGetMember1: function (memberUniqueName, member) {
		var $self = this;
		var memberEntity;
		if ((function () { var $ret = $self.__membersCache.tryGetValue(memberUniqueName, memberEntity); memberEntity = $ret.p1; return $ret.ret; }())) {
			member = memberEntity.item2();
			return {
				ret: true,
				p1: member
			};
		}
		member = null;
		return {
			ret: false,
			p1: member
		};
	}
	,
	tryGetMember: function (memberId, member) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.__membersIdCache.tryGetValue(memberId, member); member = $ret.p1; return $ret.ret; }()),
			p1: member
		};
	}
	,
	tryGetMembersForMember: function (levelUniqueName, members) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.__membersForMember.tryGetValue(levelUniqueName, members); members = $ret.p1; return $ret.ret; }()),
			p1: members
		};
	}
	,
	tryGetMembersForLevel: function (levelUniqueName, members) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.__membersForLevels.tryGetValue(levelUniqueName, members); members = $ret.p1; return $ret.ret; }()),
			p1: members
		};
	}
	,
	tryGetMeasure: function (uniqueName, measure) {
		var $self = this;
		if (!(function () { var $ret = $self.__measuresCache.tryGetValue(uniqueName, measure); measure = $ret.p1; return $ret.ret; }())) {
			measure = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.Measure.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.Measure.prototype.$type, this.measures(), function (m) { return m.uniqueName() == uniqueName; }));
			if (measure == null) {
				return {
					ret: false,
					p1: measure
				};
			}
			this.__measuresCache.add(uniqueName, measure);
		}
		return {
			ret: true,
			p1: measure
		};
	}
	,
	tryGetKpiMeasure: function (uniqueName, measure) {
		var $self = this;
		if (!(function () { var $ret = $self.__kpiMeasuresCache.tryGetValue(uniqueName, measure); measure = $ret.p1; return $ret.ret; }())) {
			measure = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.KpiMeasure.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.KpiMeasure.prototype.$type, this.kpiMeasures(), function (m) { return m.uniqueName() == uniqueName; }));
			if (measure == null) {
				return {
					ret: false,
					p1: measure
				};
			}
			this.__kpiMeasuresCache.add(uniqueName, measure);
		}
		return {
			ret: true,
			p1: measure
		};
	}
	,
	$type: new $.ig.Type('CubeMetaItemsCache', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MetaTreeHelper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getDimensionMetaItems: function (dimensions, measures, kpis, hierarchies, levels, createMeasureGroupFolders, itemsCache) {
		var metaItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		var itemPosition = new $.ig.HierarchyItemPosition();
		itemPosition.hierarchyIndex(0);
		itemPosition.levelIndex(0);
		var hierarchiesList = new $.ig.List$1($.ig.Hierarchy.prototype.$type, 0);
		var en = hierarchies.getEnumerator();
		while (en.moveNext()) {
			var hierarchy = en.current();
			hierarchiesList.add(hierarchy);
		}
		var levelsList = new $.ig.List$1($.ig.Level.prototype.$type, 0);
		var en1 = levels.getEnumerator();
		while (en1.moveNext()) {
			var level = en1.current();
			levelsList.add(level);
		}
		var en2 = dimensions.getEnumerator();
		while (en2.moveNext()) {
			var dimension = en2.current();
			var hierarchiesMetaItems = $.ig.MetaTreeHelper.prototype.getHierarchyMetaItems(hierarchiesList, levelsList, dimension, itemPosition);
			if (dimension.dimensionType() != $.ig.DimensionType.prototype.measure) {
				var dimensionMetaItem = (function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.type($.ig.OlapMetadataTreeItemType.prototype.dimension);
					$ret.caption(dimension.caption());
					$ret.item(dimension);
					return $ret;
				}());
				$.ig.MetaTreeHelper.prototype.setMetaItemChildren(dimensionMetaItem, hierarchiesMetaItems);
				metaItems.add(dimensionMetaItem);
			} else {
				var measuresMetaItem = $.ig.MetaTreeHelper.prototype.getMeasureMetadataItem(measures, dimension);
				metaItems.insert(0, measuresMetaItem);
				if ($.ig.Enumerable.prototype.count$1($.ig.Kpi.prototype.$type, kpis) > 0) {
					var kpiDimension = (function () {
						var $ret = new $.ig.KpiDimension();
						$ret.caption("Kpis");
						return $ret;
					}());
					var kpisMetaItem = $.ig.MetaTreeHelper.prototype.getKpiMetadataItem(kpis, kpiDimension, itemsCache);
					kpisMetaItem.type($.ig.OlapMetadataTreeItemType.prototype.kpiRoot);
					metaItems.insert(1, kpisMetaItem);
				}
			}
		}
		return metaItems;
	}
	,
	setMetaItemChildren: function (metaItem, children) {
		if (children.count() > 0) {
			var folders = $.ig.Enumerable.prototype.toArray$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, children, function (i) { return i.type() == $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }));
			var items = $.ig.Enumerable.prototype.toArray$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, children, function (i) { return i.type() != $.ig.OlapMetadataTreeItemType.prototype.group; }));
			var childrenArray = new Array(folders.length + items.length);
			for (var i = 0; i < folders.length; i++) {
				childrenArray[i] = folders[i];
			}
			for (var i1 = 0; i1 < items.length; i1++) {
				childrenArray[folders.length + i1] = items[i1];
			}
			metaItem.children(childrenArray);
		}
	}
	,
	getHierarchyMetaItems: function (hierarchiesList, levelsList, dimension, itemPosition) {
		var hierarchyFolders = new $.ig.Dictionary$2(String, $.ig.Tuple$2.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type)), 0);
		var hierarchiesMetaItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		var hierarchy = hierarchiesList.item(itemPosition.hierarchyIndex());
		while (hierarchy.dimensionUniqueName() != dimension.uniqueName()) {
			itemPosition.hierarchyIndex(itemPosition.hierarchyIndex() + 1);
			hierarchy = hierarchiesList.item(itemPosition.hierarchyIndex());
		}
		while (hierarchy.dimensionUniqueName() == dimension.uniqueName()) {
			var upperTuple = null;
			if (!String.isNullOrEmpty(hierarchy.hierarchyDisplayFolder())) {
				var folderNamePart = String.empty();
				var folderNames = $.ig.MetaTreeHelper.prototype.parseFolderPath(hierarchy.hierarchyDisplayFolder());
				for (var i = 0; i < folderNames.length; i++) {
					var folderName = folderNames[i];
					folderNamePart += folderName + "\\";
					var folderTuple;
					if (!(function () { var $ret = hierarchyFolders.tryGetValue(folderNamePart, folderTuple); folderTuple = $ret.p1; return $ret.ret; }())) {
						var folderMetaItem = (function () {
							var $ret = new $.ig.OlapMetadataTreeItem();
							$ret.type($.ig.OlapMetadataTreeItemType.prototype.group);
							$ret.caption(folderName);
							return $ret;
						}());
						folderTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), folderMetaItem, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
						hierarchyFolders.add(folderNamePart, folderTuple);
						if (upperTuple != null) {
							upperTuple.item2().add(folderMetaItem);
						} else {
							hierarchiesMetaItems.add(folderMetaItem);
						}
					}
					upperTuple = folderTuple;
				}
			}
			var metadataItemType;
			switch (hierarchy.hierarchyOrigin()) {
				case $.ig.HierarchyOrigin.prototype.systemEnabled:
					metadataItemType = $.ig.OlapMetadataTreeItemType.prototype.systemEnabledHierarchy;
					break;
				case $.ig.HierarchyOrigin.prototype.userDefined:
					metadataItemType = $.ig.OlapMetadataTreeItemType.prototype.userDefinedHierarchy;
					break;
				default:
					metadataItemType = $.ig.OlapMetadataTreeItemType.prototype.parentChildHierarchy;
					break;
			}
			var levelMetaItems = $.ig.MetaTreeHelper.prototype.getLevelMetaItems(levelsList, hierarchy, itemPosition);
			var hierarchyMetaItem = (function () {
				var $ret = new $.ig.OlapMetadataTreeItem();
				$ret.type(metadataItemType);
				$ret.caption(hierarchy.caption());
				$ret.item(hierarchy);
				return $ret;
			}());
			$.ig.MetaTreeHelper.prototype.setMetaItemChildren(hierarchyMetaItem, levelMetaItems);
			if (upperTuple != null) {
				upperTuple.item2().add(hierarchyMetaItem);
			} else {
				hierarchiesMetaItems.add(hierarchyMetaItem);
			}
			if (itemPosition.hierarchyIndex() + 1 >= hierarchiesList.count()) {
				break;
			}
			itemPosition.hierarchyIndex(itemPosition.hierarchyIndex() + 1);
			hierarchy = hierarchiesList.item(itemPosition.hierarchyIndex());
		}
		var en = hierarchyFolders.values().getEnumerator();
		while (en.moveNext()) {
			var metaItemTuple = en.current();
			var metaItem = metaItemTuple.item1();
			$.ig.MetaTreeHelper.prototype.setMetaItemChildren(metaItem, metaItemTuple.item2());
		}
		return hierarchiesMetaItems;
	}
	,
	getLevelMetaItems: function (levelsList, hierarchy, itemPosition) {
		var levelMetaItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		if (levelsList == null || levelsList.count() == 0) {
			return levelMetaItems;
		}
		var level = levelsList.item(itemPosition.levelIndex());
		while (level.hierarchyUniqueName() != hierarchy.uniqueName()) {
			itemPosition.levelIndex(itemPosition.levelIndex() + 1);
			level = levelsList.item(itemPosition.levelIndex());
		}
		while (level.hierarchyUniqueName() == hierarchy.uniqueName()) {
			var itemType;
			switch (level.depth()) {
				case 0:
				case 1:
					itemType = $.ig.OlapMetadataTreeItemType.prototype.level1;
					break;
				case 2:
					itemType = $.ig.OlapMetadataTreeItemType.prototype.level2;
					break;
				case 3:
					itemType = $.ig.OlapMetadataTreeItemType.prototype.level3;
					break;
				case 4:
					itemType = $.ig.OlapMetadataTreeItemType.prototype.level4;
					break;
				default:
					itemType = $.ig.OlapMetadataTreeItemType.prototype.level5;
					break;
			}
			var levelMetaItem = (function () {
				var $ret = new $.ig.OlapMetadataTreeItem();
				$ret.type(itemType);
				$ret.caption(level.caption());
				$ret.item(level);
				return $ret;
			}());
			levelMetaItems.add(levelMetaItem);
			if (itemPosition.levelIndex() + 1 >= levelsList.count()) {
				break;
			}
			itemPosition.levelIndex(itemPosition.levelIndex() + 1);
			level = levelsList.item(itemPosition.levelIndex());
		}
		return levelMetaItems;
	}
	,
	getMeasureMetadataItem: function (measures, measureDimension) {
		var subFolders = new $.ig.Dictionary$2(String, $.ig.Tuple$2.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type)), 0);
		var measuresRoot = (function () {
			var $ret = new $.ig.OlapMetadataTreeItem();
			$ret.type($.ig.OlapMetadataTreeItemType.prototype.measure);
			$ret.caption(measureDimension.caption());
			$ret.item(measureDimension);
			return $ret;
		}());
		var rootTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), measuresRoot, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
		var en = measures.getEnumerator();
		while (en.moveNext()) {
			var measure = en.current();
			if ($.ig.util.cast($.ig.KpiMeasure.prototype.$type, measure) !== null) {
				continue;
			}
			var measureGroupTuple = rootTuple;
			if (!String.isNullOrEmpty(measure.measureGroupName())) {
				if (!(function () { var $ret = subFolders.tryGetValue(measure.measureGroupName(), measureGroupTuple); measureGroupTuple = $ret.p1; return $ret.ret; }())) {
					var groupMetaItem = (function () {
						var $ret = new $.ig.OlapMetadataTreeItem();
						$ret.type($.ig.OlapMetadataTreeItemType.prototype.group);
						$ret.caption(measure.measureGroupName());
						return $ret;
					}());
					measureGroupTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), groupMetaItem, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
					subFolders.add(measure.measureGroupName(), measureGroupTuple);
					rootTuple.item2().add(measureGroupTuple.item1());
				}
			}
			var upperTuple = measureGroupTuple;
			if (!String.isNullOrEmpty(measure.measureDisplayFolder())) {
				var folderNamePart = measure.measureGroupName();
				var folderNames = $.ig.MetaTreeHelper.prototype.parseFolderPath(measure.measureDisplayFolder());
				for (var i = 0; i < folderNames.length; i++) {
					var folderName = folderNames[i];
					folderNamePart += "\\" + folderName;
					var folderTuple;
					if (!(function () { var $ret = subFolders.tryGetValue(folderNamePart, folderTuple); folderTuple = $ret.p1; return $ret.ret; }())) {
						var folderMetaItem = (function () {
							var $ret = new $.ig.OlapMetadataTreeItem();
							$ret.type($.ig.OlapMetadataTreeItemType.prototype.group);
							$ret.caption(folderName);
							return $ret;
						}());
						folderTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), folderMetaItem, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
						subFolders.add(folderNamePart, folderTuple);
						upperTuple.item2().add(folderMetaItem);
					}
					upperTuple = folderTuple;
				}
			}
			var measureMetaItem = (function () {
				var $ret = new $.ig.OlapMetadataTreeItem();
				$ret.type($.ig.OlapMetadataTreeItemType.prototype.measure);
				$ret.caption(measure.caption());
				$ret.item(measure);
				return $ret;
			}());
			upperTuple.item2().add(measureMetaItem);
		}
		var measureItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		var en1 = $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, rootTuple.item2(), function (i) { return i.type() == $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }).getEnumerator();
		while (en1.moveNext()) {
			var item = en1.current();
			measureItems.add(item);
		}
		var en2 = $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, rootTuple.item2(), function (i) { return i.type() != $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }).getEnumerator();
		while (en2.moveNext()) {
			var item1 = en2.current();
			measureItems.add(item1);
		}
		$.ig.MetaTreeHelper.prototype.setMetaItemChildren(measuresRoot, measureItems);
		var en3 = subFolders.values().getEnumerator();
		while (en3.moveNext()) {
			var metaItemTuple = en3.current();
			var itemsList = metaItemTuple.item2();
			var groups = $.ig.Enumerable.prototype.toList$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, itemsList, function (i) { return i.type() == $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }));
			var singleItems = $.ig.Enumerable.prototype.toList$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, itemsList, function (i) { return i.type() != $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }));
			var childItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
			for (var i1 = 0; i1 < groups.count(); i1++) {
				childItems.add(groups.item(i1));
			}
			for (var j = 0; j < singleItems.count(); j++) {
				childItems.add(singleItems.item(j));
			}
			var metaItem = metaItemTuple.item1();
			$.ig.MetaTreeHelper.prototype.setMetaItemChildren(metaItem, childItems);
		}
		return measuresRoot;
	}
	,
	getKpiMetadataItem: function (kpis, kpiDimension, itemsCache) {
		var subFolders = new $.ig.Dictionary$2(String, $.ig.Tuple$2.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type)), 0);
		var measures = $.ig.Enumerable.prototype.toList$1($.ig.Measure.prototype.$type, itemsCache.measures());
		itemsCache.measures(measures);
		var kpiMeasures = new $.ig.List$1($.ig.KpiMeasure.prototype.$type, 0);
		itemsCache.kpiMeasures(kpiMeasures);
		var kpisRoot = (function () {
			var $ret = new $.ig.OlapMetadataTreeItem();
			$ret.type($.ig.OlapMetadataTreeItemType.prototype.kpiRoot);
			$ret.caption(kpiDimension.caption());
			$ret.item(kpiDimension);
			return $ret;
		}());
		var rootTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), kpisRoot, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
		var en = kpis.getEnumerator();
		while (en.moveNext()) {
			var kpi = en.current();
			var upperTuple = rootTuple;
			if (!String.isNullOrEmpty(kpi.kpiDisplayFolder())) {
				var folderNamePart = String.empty();
				var folderNames = $.ig.MetaTreeHelper.prototype.parseFolderPath(kpi.kpiDisplayFolder());
				for (var i = 0; i < folderNames.length; i++) {
					var folderName = folderNames[i];
					folderNamePart += "\\" + folderName;
					var folderTuple;
					if (!(function () { var $ret = subFolders.tryGetValue(folderNamePart, folderTuple); folderTuple = $ret.p1; return $ret.ret; }())) {
						var folderMetaItem = (function () {
							var $ret = new $.ig.OlapMetadataTreeItem();
							$ret.type($.ig.OlapMetadataTreeItemType.prototype.group);
							$ret.caption(folderName);
							return $ret;
						}());
						folderTuple = new $.ig.Tuple$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.OlapMetadataTreeItem.prototype.$type), folderMetaItem, new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0));
						subFolders.add(folderNamePart, folderTuple);
						upperTuple.item2().add(folderMetaItem);
					}
					upperTuple = folderTuple;
				}
			}
			var kpiMetaItem = (function () {
				var $ret = new $.ig.OlapMetadataTreeItem();
				$ret.type($.ig.OlapMetadataTreeItemType.prototype.kpi);
				$ret.caption(kpi.caption());
				$ret.item(kpi);
				return $ret;
			}());
			if (!String.isNullOrEmpty(kpi.kpiValue())) {
				var measure = null;
				var itemType = $.ig.OlapMetadataTreeItemType.prototype.measure;
				var caption = "Value";
				if (!(function () { var $ret = itemsCache.tryGetMeasure(kpi.kpiValue(), measure); measure = $ret.p1; return $ret.ret; }())) {
					itemType = $.ig.OlapMetadataTreeItemType.prototype.kpiValue;
					measure = $.ig.KpiMeasure.prototype.createKpiValueMeasure(kpi);
					measures.add(measure);
					kpiMeasures.add(measure);
				} else {
					caption += " (" + measure.caption() + ")";
				}
				kpiMetaItem.addChild((function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.caption(caption);
					$ret.item(measure);
					$ret.type(itemType);
					return $ret;
				}()));
			}
			if (!String.isNullOrEmpty(kpi.kpiGoal())) {
				var measure1 = null;
				var itemType1 = $.ig.OlapMetadataTreeItemType.prototype.measure;
				var caption1 = "Goal";
				if (!(function () { var $ret = itemsCache.tryGetMeasure(kpi.kpiGoal(), measure1); measure1 = $ret.p1; return $ret.ret; }())) {
					itemType1 = $.ig.OlapMetadataTreeItemType.prototype.kpiGoal;
					measure1 = $.ig.KpiMeasure.prototype.createKpiGoalMeasure(kpi);
					measures.add(measure1);
					kpiMeasures.add(measure1);
				} else {
					caption1 += " (" + measure1.caption() + ")";
				}
				kpiMetaItem.addChild((function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.caption(caption1);
					$ret.item(measure1);
					$ret.type(itemType1);
					return $ret;
				}()));
			}
			if (!String.isNullOrEmpty(kpi.kpiStatus())) {
				var measure2 = $.ig.KpiMeasure.prototype.createKpiStatusMeasure(kpi);
				measures.add(measure2);
				kpiMeasures.add(measure2);
				kpiMetaItem.addChild((function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.caption("Status");
					$ret.item(measure2);
					$ret.type($.ig.OlapMetadataTreeItemType.prototype.kpiStatus);
					return $ret;
				}()));
			}
			if (!String.isNullOrEmpty(kpi.kpiTrend())) {
				var measure3 = $.ig.KpiMeasure.prototype.createKpiTrendMeasure(kpi);
				measures.add(measure3);
				kpiMeasures.add(measure3);
				kpiMetaItem.addChild((function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.caption("Trend");
					$ret.item(measure3);
					$ret.type($.ig.OlapMetadataTreeItemType.prototype.kpiTrend);
					return $ret;
				}()));
			}
			if (!String.isNullOrEmpty(kpi.kpiWeight())) {
				var measure4 = $.ig.KpiMeasure.prototype.createKpiWeightMeasure(kpi);
				measures.add(measure4);
				kpiMeasures.add(measure4);
				kpiMetaItem.addChild((function () {
					var $ret = new $.ig.OlapMetadataTreeItem();
					$ret.caption("Weight");
					$ret.item(measure4);
					$ret.type($.ig.OlapMetadataTreeItemType.prototype.kpiWeight);
					return $ret;
				}()));
			}
			upperTuple.item2().add(kpiMetaItem);
		}
		var kpiItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		var en1 = $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, rootTuple.item2(), function (i) { return i.type() == $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }).getEnumerator();
		while (en1.moveNext()) {
			var item = en1.current();
			kpiItems.add(item);
		}
		var en2 = $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, rootTuple.item2(), function (i) { return i.type() != $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }).getEnumerator();
		while (en2.moveNext()) {
			var item1 = en2.current();
			kpiItems.add(item1);
		}
		$.ig.MetaTreeHelper.prototype.setMetaItemChildren(kpisRoot, kpiItems);
		var en3 = subFolders.values().getEnumerator();
		while (en3.moveNext()) {
			var metaItemTuple = en3.current();
			var itemsList = metaItemTuple.item2();
			var groups = $.ig.Enumerable.prototype.toList$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, itemsList, function (i) { return i.type() == $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }));
			var singleItems = $.ig.Enumerable.prototype.toList$1($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.Enumerable.prototype.orderBy$2($.ig.OlapMetadataTreeItem.prototype.$type, String, $.ig.Enumerable.prototype.where$1($.ig.OlapMetadataTreeItem.prototype.$type, itemsList, function (i) { return i.type() != $.ig.OlapMetadataTreeItemType.prototype.group; }), function (i) { return i.caption(); }));
			var childItems = new $.ig.List$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
			for (var i1 = 0; i1 < groups.count(); i1++) {
				childItems.add(groups.item(i1));
			}
			for (var j = 0; j < singleItems.count(); j++) {
				childItems.add(singleItems.item(j));
			}
			var metaItem = metaItemTuple.item1();
			$.ig.MetaTreeHelper.prototype.setMetaItemChildren(metaItem, childItems);
		}
		return kpisRoot;
	}
	,
	parseFolderPath: function (folderPath) {
		var folders = folderPath.split('\\');
		return folders;
	}
	,
	$type: new $.ig.Type('MetaTreeHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('HierarchyItemPosition', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_hierarchyIndex: 0,
	hierarchyIndex: function (value) {
		if (arguments.length === 1) {
			this._hierarchyIndex = value;
			return value;
		} else {
			return this._hierarchyIndex;
		}
	}
	,
	_levelIndex: 0,
	levelIndex: function (value) {
		if (arguments.length === 1) {
			this._levelIndex = value;
			return value;
		} else {
			return this._levelIndex;
		}
	}
	,
	$type: new $.ig.Type('HierarchyItemPosition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataSourceBaseOptions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_cube: null,
	cube: function (value) {
		if (arguments.length === 1) {
			this._cube = value;
			return value;
		} else {
			return this._cube;
		}
	}
	,
	_measureGroup: null,
	measureGroup: function (value) {
		if (arguments.length === 1) {
			this._measureGroup = value;
			return value;
		} else {
			return this._measureGroup;
		}
	}
	,
	_measures: null,
	measures: function (value) {
		if (arguments.length === 1) {
			this._measures = value;
			return value;
		} else {
			return this._measures;
		}
	}
	,
	_filters: null,
	filters: function (value) {
		if (arguments.length === 1) {
			this._filters = value;
			return value;
		} else {
			return this._filters;
		}
	}
	,
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
	_rows: null,
	rows: function (value) {
		if (arguments.length === 1) {
			this._rows = value;
			return value;
		} else {
			return this._rows;
		}
	}
	,
	$type: new $.ig.Type('DataSourceBaseOptions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('AxisDefinitionParser', 'Object', {
	init: function (initString) {
		$.ig.Object.prototype.init.call(this);
		this.position(-1);
		this.initString(initString);
	},
	_isFilter: false,
	isFilter: function (value) {
		if (arguments.length === 1) {
			this._isFilter = value;
			return value;
		} else {
			return this._isFilter;
		}
	}
	,
	_elementName: null,
	elementName: function (value) {
		if (arguments.length === 1) {
			this._elementName = value;
			return value;
		} else {
			return this._elementName;
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
	_initString: null,
	initString: function (value) {
		if (arguments.length === 1) {
			this._initString = value;
			return value;
		} else {
			return this._initString;
		}
	}
	,
	readNextElement: function () {
		if (this.position() == this.initString().length - 1) {
			return false;
		}
		var elementBeginIndex = -1;
		var elementEndIndex = -1;
		var terminationElementFound = false;
		while (this.position() + 1 < this.initString().length) {
			this.position(this.position() + 1);
			var value = this.initString().substr(this.position(), 1);
			switch (value) {
				case $.ig.AxisDefinitionParser.prototype._elementBegin:
					if (elementBeginIndex == -1) {
						elementBeginIndex = this.position();
					}
					break;
				case $.ig.AxisDefinitionParser.prototype._elementEnd:
					elementEndIndex = this.position();
					while (this.position() + 1 < this.initString().length) {
						value = this.initString().substr(this.position() + 1, 1);
						switch (value) {
							case $.ig.AxisDefinitionParser.prototype._elementEnd:
								elementEndIndex = this.position() + 1;
								break;
							case $.ig.AxisDefinitionParser.prototype._elementSeparator:
								if ($.ig.AxisDefinitionParser.prototype._elementEnd == this.initString().substr(this.position(), 1)) {
									terminationElementFound = true;
								}
								break;
							case $.ig.AxisDefinitionParser.prototype._filterGroupBegin:
							case $.ig.AxisDefinitionParser.prototype._filterGroupEnd:
								terminationElementFound = true;
								break;
						}
						if (terminationElementFound) {
							break;
						}
						this.position(this.position() + 1);
					}
					terminationElementFound = true;
					break;
				case $.ig.AxisDefinitionParser.prototype._elementSeparator:
					terminationElementFound = true;
					break;
				case $.ig.AxisDefinitionParser.prototype._filterGroupBegin:
					this.isFilter(true);
					break;
				case $.ig.AxisDefinitionParser.prototype._filterGroupEnd:
					$.ig.Debug.prototype.assert(this.isFilter());
					terminationElementFound = true;
					this.isFilter(false);
					break;
			}
			if (terminationElementFound) {
				terminationElementFound = false;
				if (elementBeginIndex > -1 && elementEndIndex > -1) {
					break;
				}
			}
		}
		if (this.position() == this.initString().length - 1) {
			if (elementEndIndex == -1 || elementBeginIndex == -1) {
				return false;
			}
		}
		var elementValue = this.initString().substr(elementBeginIndex, elementEndIndex - elementBeginIndex + 1).trim([]);
		this.elementName(elementValue);
		return true;
	}
	,
	parse: function () {
		var elementValues = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		var filterValues = null;
		while (this.readNextElement()) {
			if (!this.isFilter()) {
				filterValues = new $.ig.ArrayListCollection$1(String);
				var axisElement = new $.ig.AxisElement(this.elementName(), filterValues);
				elementValues.add(axisElement);
			} else if (filterValues != null) {
				filterValues.add(this.elementName());
			}
		}
		return elementValues;
	}
	,
	$type: new $.ig.Type('AxisDefinitionParser', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CoreOlapElementParser', 'Object', {
	init: function (elementName) {
		$.ig.Object.prototype.init.call(this);
		this.position(-1);
		this.initString(elementName);
	},
	_elementName: null,
	elementName: function (value) {
		if (arguments.length === 1) {
			this._elementName = value;
			return value;
		} else {
			return this._elementName;
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
	_initString: null,
	initString: function (value) {
		if (arguments.length === 1) {
			this._initString = value;
			return value;
		} else {
			return this._initString;
		}
	}
	,
	readNextElement: function () {
		if (this.position() == this.initString().length - 1) {
			return false;
		}
		var elementBeginIndex = -1;
		var elementEndIndex = -1;
		var terminationElementFound = false;
		while (this.position() + 1 < this.initString().length) {
			this.position(this.position() + 1);
			var value = this.initString().substr(this.position(), 1);
			switch (value) {
				case $.ig.CoreOlapElementParser.prototype._elementBegin:
					if (elementBeginIndex == -1) {
						elementBeginIndex = this.position();
					}
					break;
				case $.ig.CoreOlapElementParser.prototype._elementEnd:
					elementEndIndex = this.position();
					while (this.position() + 1 < this.initString().length) {
						value = this.initString().substr(this.position() + 1, 1);
						switch (value) {
							case $.ig.CoreOlapElementParser.prototype._elementEnd:
								elementEndIndex = this.position() + 1;
								break;
							case $.ig.CoreOlapElementParser.prototype._elementSeparator:
								terminationElementFound = true;
								break;
						}
						if (terminationElementFound) {
							break;
						}
						this.position(this.position() + 1);
					}
					break;
				case $.ig.CoreOlapElementParser.prototype._elementSeparator:
					terminationElementFound = true;
					break;
			}
			if (terminationElementFound) {
				terminationElementFound = false;
				if (elementBeginIndex > -1 && elementEndIndex > -1) {
					break;
				}
			}
		}
		if (this.position() == this.initString().length - 1) {
			if (elementEndIndex == -1 || elementBeginIndex == -1) {
				return false;
			}
		}
		var elementValue = this.initString().substr(elementBeginIndex, elementEndIndex - elementBeginIndex + 1).trim([]);
		this.elementName(elementValue);
		return true;
	}
	,
	getPartNames: function () {
		var elementValues = new $.ig.List$1(String, 0);
		while (this.readNextElement()) {
			elementValues.add(this.elementName());
		}
		return elementValues;
	}
	,
	$type: new $.ig.Type('CoreOlapElementParser', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroupMetaItemsCache', 'Object', {
	__measureGroupDimensions: null,
	__measureGroupMeasures: null,
	__measureGroupKpis: null,
	__measureGroupDimensionsCollection: null,
	__dimensions: null,
	__measures: null,
	__kpis: null,
	init: function (dimensions, measures, kpis, measureGroupDimensions) {
		$.ig.Object.prototype.init.call(this);
		this.__measureGroupDimensions = new $.ig.Dictionary$2(String, $.ig.IEnumerable$1.prototype.$type.specialize($.ig.Dimension.prototype.$type), 0);
		this.__measureGroupMeasures = new $.ig.Dictionary$2(String, $.ig.IEnumerable$1.prototype.$type.specialize($.ig.Measure.prototype.$type), 0);
		this.__measureGroupKpis = new $.ig.Dictionary$2(String, $.ig.IEnumerable$1.prototype.$type.specialize($.ig.Kpi.prototype.$type), 0);
		this.__measureGroupDimensionsCollection = measureGroupDimensions;
		this.__dimensions = dimensions;
		this.__measures = measures;
		this.__kpis = kpis;
	},
	getMeasureGroupDimensions: function (measureGroupName) {
		var $self = this;
		var measureGroupDimensions;
		if (!(function () { var $ret = $self.__measureGroupDimensions.tryGetValue(measureGroupName, measureGroupDimensions); measureGroupDimensions = $ret.p1; return $ret.ret; }())) {
			var dimensionNames = new $.ig.List$1(String, 0);
			var en = this.__measureGroupDimensionsCollection.getEnumerator();
			while (en.moveNext()) {
				var measureGroupDimension = en.current();
				if (measureGroupDimension.measureGroupName() == measureGroupName) {
					dimensionNames.add(measureGroupDimension.dimensionUniqueName());
				}
			}
			var dimensions = new $.ig.List$1($.ig.Dimension.prototype.$type, 0);
			var en1 = this.__dimensions.getEnumerator();
			while (en1.moveNext()) {
				var dimension = en1.current();
				if (dimensionNames.contains(dimension.uniqueName()) || dimension.dimensionType() == $.ig.DimensionType.prototype.measure) {
					dimensions.add(dimension);
				}
			}
			this.__measureGroupDimensions.add(measureGroupName, dimensions);
			measureGroupDimensions = dimensions;
		}
		return measureGroupDimensions;
	}
	,
	getMeasureGroupMeasures: function (measureGroupName) {
		var $self = this;
		var measureGroupMeasures;
		if (!(function () { var $ret = $self.__measureGroupMeasures.tryGetValue(measureGroupName, measureGroupMeasures); measureGroupMeasures = $ret.p1; return $ret.ret; }())) {
			var measures = new $.ig.List$1($.ig.Measure.prototype.$type, 0);
			var en = this.__measures.getEnumerator();
			while (en.moveNext()) {
				var measure = en.current();
				if (measure.measureGroupName() == measureGroupName) {
					measures.add(measure);
				}
			}
			this.__measureGroupMeasures.add(measureGroupName, measures);
			measureGroupMeasures = measures;
		}
		return measureGroupMeasures;
	}
	,
	getMeasureGroupKpis: function (measureGroupName) {
		var $self = this;
		var measureGroupKpis;
		if (!(function () { var $ret = $self.__measureGroupKpis.tryGetValue(measureGroupName, measureGroupKpis); measureGroupKpis = $ret.p1; return $ret.ret; }())) {
			var kpis = new $.ig.List$1($.ig.Kpi.prototype.$type, 0);
			var en = this.__kpis.getEnumerator();
			while (en.moveNext()) {
				var kpi = en.current();
				if (kpi.measureGroupName() == measureGroupName) {
					kpis.add(kpi);
				}
			}
			this.__measureGroupKpis.add(measureGroupName, kpis);
			measureGroupKpis = kpis;
		}
		return measureGroupKpis;
	}
	,
	$type: new $.ig.Type('MeasureGroupMetaItemsCache', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LevelSortDirection', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.sortBehavior($.ig.LevelSortBehavior.prototype.system);
	},
	_levelUniqueName: null,
	levelUniqueName: function (value) {
		if (arguments.length === 1) {
			this._levelUniqueName = value;
			return value;
		} else {
			return this._levelUniqueName;
		}
	}
	,
	_sortDirection: 0,
	sortDirection: function (value) {
		if (arguments.length === 1) {
			this._sortDirection = value;
			return value;
		} else {
			return this._sortDirection;
		}
	}
	,
	_sortBehavior: 0,
	sortBehavior: function (value) {
		if (arguments.length === 1) {
			this._sortBehavior = value;
			return value;
		} else {
			return this._sortBehavior;
		}
	}
	,
	$type: new $.ig.Type('LevelSortDirection', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('HierarchyFilterView', 'Object', {
	__filterItems: null,
	__filterLevels: null,
	__selectedFilterItems: null,
	init: function (hierarchy) {
		this.__selectedFilterItems = new $.ig.Dictionary$2(String, $.ig.FilterMember.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
		this.hierarchy(hierarchy);
		this.__filterLevels = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.IList$1.prototype.$type.specialize($.ig.FilterMember.prototype.$type), 0);
		this.__filterItems = new $.ig.Dictionary$2(String, $.ig.FilterMember.prototype.$type, 0);
	},
	filterMemberExpansionChanged: null,
	filterMemberSelectionChanged: null,
	_hierarchy: null,
	hierarchy: function (value) {
		if (arguments.length === 1) {
			this._hierarchy = value;
			return value;
		} else {
			return this._hierarchy;
		}
	}
	,
	addFiltersForMembers: function (members) {
		var en = members.getEnumerator();
		while (en.moveNext()) {
			var member = en.current();
			this.addFilterForMember(member);
		}
	}
	,
	addFilterForMember: function (member) {
		var $self = this;
		if (!this.__filterItems.containsKey(member.uniqueName())) {
			var filterItem = new $.ig.FilterMember(member);
			filterItem.propertyChanged = $.ig.Delegate.prototype.combine(filterItem.propertyChanged, this.filterItem_PropertyChanged.runOn(this));
			filterItem.selectionChanged = $.ig.Delegate.prototype.combine(filterItem.selectionChanged, this.filterItem_SelectionChanged.runOn(this));
			var levelItems;
			if (!(function () { var $ret = $self.__filterLevels.tryGetValue(member.levelDepth(), levelItems); levelItems = $ret.p1; return $ret.ret; }())) {
				levelItems = new $.ig.List$1($.ig.FilterMember.prototype.$type, 0);
				this.__filterLevels.add(member.levelDepth(), levelItems);
			}
			this.__filterItems.add(member.uniqueName(), filterItem);
			this.__selectedFilterItems.add(member.uniqueName(), filterItem);
			levelItems.add(filterItem);
			this.addChildren(filterItem);
			this.attachToParent(filterItem);
		}
	}
	,
	tryGetFilterMember: function (memberUniqueName) {
		if (this.__filterItems.containsKey(memberUniqueName)) {
			return this.__filterItems.item(memberUniqueName);
		}
		return null;
	}
	,
	getRootFilterMembers: function () {
		var minIndex = -1;
		var en = this.__filterLevels.keys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (minIndex == -1) {
				minIndex = key;
			} else {
				minIndex = Math.min(key, minIndex);
			}
		}
		if (minIndex == -1) {
			return null;
		}
		return this.__filterLevels.item(minIndex);
	}
	,
	getSelectedFilterItems: function () {
		var rootLevelItems = this.getRootFilterMembers();
		var filterItems = new $.ig.List$1($.ig.FilterMember.prototype.$type, 0);
		var selectedRootLevelItemsCount = 0;
		var en = rootLevelItems.getEnumerator();
		while (en.moveNext()) {
			var rootLevelItem = en.current();
			if (rootLevelItem.isSelected().hasValue() == true && rootLevelItem.isSelected().value() == true) {
				selectedRootLevelItemsCount++;
			}
		}
		if (selectedRootLevelItemsCount == rootLevelItems.count()) {
			return filterItems;
		}
		var en1 = rootLevelItems.getEnumerator();
		while (en1.moveNext()) {
			var rootLevelItem1 = en1.current();
			if (rootLevelItem1.isSelected().hasValue() == false) {
				this.fillSelectedItems(rootLevelItem1, filterItems);
			} else if (rootLevelItem1.isSelected().hasValue() == true && rootLevelItem1.isSelected().value() == true) {
				filterItems.add(rootLevelItem1);
			}
		}
		return filterItems;
	}
	,
	filterItem_SelectionChanged: function (sender, e) {
		if (this.filterMemberSelectionChanged != null) {
			this.filterMemberSelectionChanged(this, new $.ig.FilterMemberStateChangedEventArgs(sender));
		}
	}
	,
	filterItem_PropertyChanged: function (sender, e) {
		if (e.propertyName() == "IsExpanded") {
			if (this.filterMemberExpansionChanged != null) {
				this.filterMemberExpansionChanged(this, new $.ig.FilterMemberStateChangedEventArgs(sender));
			}
		}
	}
	,
	fillSelectedItems: function (filterItem, filterItems) {
		if (filterItem.isSelected().hasValue() == true && filterItem.isSelected().value() == true) {
			filterItems.add(filterItem);
		} else {
			if (filterItem.children() == null) {
				return;
			}
			var en = filterItem.children().getEnumerator();
			while (en.moveNext()) {
				var child = en.current();
				this.fillSelectedItems(child, filterItems);
			}
		}
	}
	,
	attachToParent: function (filterItem) {
		var $self = this;
		var parentName = filterItem.member().parentUniqueName();
		if (String.isNullOrEmpty(parentName)) {
			return;
		}
		var parentItem;
		if ((function () { var $ret = $self.__filterItems.tryGetValue(parentName, parentItem); parentItem = $ret.p1; return $ret.ret; }())) {
			parentItem.addChildItem(filterItem);
			if (parentItem.isSelected().hasValue() == true && parentItem.isSelected().value() == false) {
				filterItem.isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, false));
			}
		}
	}
	,
	addChildren: function (filterItem) {
		var $self = this;
		var levelIndex = filterItem.member().levelDepth();
		var nextLevelMembers;
		if ((function () { var $ret = $self.__filterLevels.tryGetValue(levelIndex + 1, nextLevelMembers); nextLevelMembers = $ret.p1; return $ret.ret; }())) {
			var en = nextLevelMembers.getEnumerator();
			while (en.moveNext()) {
				var nextLevelMember = en.current();
				if (nextLevelMember.member().parentUniqueName() == filterItem.member().uniqueName()) {
					filterItem.addChildItem(nextLevelMember);
				}
			}
		}
	}
	,
	$type: new $.ig.Type('HierarchyFilterView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('FilterMember', 'Object', {
	__isSelected: $.ig.util.toNullable($.ig.Boolean.prototype.$type, null),
	__isExpanded: false,
	__skipParentUpdate: false,
	__skipChildrenUpdate: false,
	init: function (member) {
		this.__skipParentUpdate = false;
		this.__skipChildrenUpdate = false;
		$.ig.Object.prototype.init.call(this);
		var trueValue = $.ig.util.toNullable($.ig.Boolean.prototype.$type, true);
		this.__isSelected = trueValue;
		this.member(member);
		this.unselectedChildrenCount(0);
		this.nullChildrenCount(0);
	},
	selectionChanged: null,
	propertyChanged: null,
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
	_member: null,
	member: function (value) {
		if (arguments.length === 1) {
			this._member = value;
			return value;
		} else {
			return this._member;
		}
	}
	,
	isExpandable: function () {
		return this.member().childrenCardinality() > 0;
	}
	,
	isExpanded: function (value) {
		if (arguments.length === 1) {
			if (this.__isExpanded != value) {
				this.__isExpanded = value;
				this.onPropertyChanged("IsExpanded");
			}
			return value;
		} else {
			return this.__isExpanded;
		}
	}
	,
	isSelected: function (value) {
		if (arguments.length === 1) {
			var newValue = value;
			var nullValue = $.ig.util.toNullable($.ig.Boolean.prototype.$type, null);
			if (($.ig.util.nullableEquals(this.__isSelected, newValue)) == false) {
				var oldValue = this.__isSelected;
				this.__isSelected = value;
				if (!this.__skipChildrenUpdate) {
					if (this.__isSelected.hasValue() == true) {
						if (this.children() != null) {
							var en = this.children().getEnumerator();
							while (en.moveNext()) {
								var filterItem = en.current();
								filterItem.__skipParentUpdate = true;
								filterItem.isSelected(this.__isSelected);
								filterItem.__skipParentUpdate = false;
							}
						}
					}
				}
				if (this.parent() != null) {
					if (oldValue.hasValue() == true && oldValue.value() == false) {
						if (this.__isSelected.hasValue() == false) {
							this.updateNullChildrenCount(true);
						}
						this.updateUnselectedChildrenCount(false);
					} else if (oldValue.hasValue() == false) {
						if (this.__isSelected.hasValue() == true && this.__isSelected.value() == false) {
							this.updateUnselectedChildrenCount(true);
						}
						this.updateNullChildrenCount(false);
					} else {
						if (this.__isSelected.hasValue() == true && this.__isSelected.value() == false) {
							this.updateUnselectedChildrenCount(true);
						} else if (this.__isSelected.hasValue() == false) {
							this.updateNullChildrenCount(true);
						}
					}
					if (!this.__skipParentUpdate) {
						this.parent().__skipChildrenUpdate = true;
						var unselected = this.parent().unselectedChildrenCount();
						var nullSelected = this.parent().nullChildrenCount();
						var total = this.parent().children().count();
						if (nullSelected == 0) {
							if (unselected == 0) {
								this.parent().isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, true));
							} else if (unselected == total) {
								this.parent().isSelected($.ig.util.toNullable($.ig.Boolean.prototype.$type, false));
							} else {
								this.parent().isSelected(nullValue);
							}
						} else {
							this.parent().isSelected(nullValue);
						}
						this.parent().__skipChildrenUpdate = false;
					}
				}
				this.onPropertyChanged("IsSelected");
				if (!this.__skipParentUpdate && !this.__skipChildrenUpdate) {
					if (this.selectionChanged != null) {
						this.selectionChanged(this, $.ig.EventArgs.prototype.empty);
					}
				}
			}
			return value;
		} else {
			return this.__isSelected;
		}
	}
	,
	updateUnselectedChildrenCount: function (increaseUnselected) {
		if (increaseUnselected) {
			this.parent().unselectedChildrenCount(this.parent().unselectedChildrenCount() + 1);
		} else {
			this.parent().unselectedChildrenCount(this.parent().unselectedChildrenCount() - 1);
		}
	}
	,
	updateNullChildrenCount: function (increaseUnselected) {
		if (increaseUnselected) {
			this.parent().nullChildrenCount(this.parent().nullChildrenCount() + 1);
		} else {
			this.parent().nullChildrenCount(this.parent().nullChildrenCount() - 1);
		}
	}
	,
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
	addChildItem: function (filterItem) {
		if (this.children() == null) {
			this.children(new $.ig.List$1($.ig.FilterMember.prototype.$type, 0));
		}
		filterItem.parent(this);
		this.children().add(filterItem);
		this.onPropertyChanged("Children");
	}
	,
	_unselectedChildrenCount: 0,
	unselectedChildrenCount: function (value) {
		if (arguments.length === 1) {
			this._unselectedChildrenCount = value;
			return value;
		} else {
			return this._unselectedChildrenCount;
		}
	}
	,
	_nullChildrenCount: 0,
	nullChildrenCount: function (value) {
		if (arguments.length === 1) {
			this._nullChildrenCount = value;
			return value;
		} else {
			return this._nullChildrenCount;
		}
	}
	,
	onPropertyChanged: function (propertyName) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	$type: new $.ig.Type('FilterMember', $.ig.Object.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('FilterMemberStateChangedEventArgs', 'EventArgs', {
	init: function (filterMember) {
		$.ig.EventArgs.prototype.init.call(this);
		this.filterMember(filterMember);
	},
	_filterMember: null,
	filterMember: function (value) {
		if (arguments.length === 1) {
			this._filterMember = value;
			return value;
		} else {
			return this._filterMember;
		}
	}
	,
	$type: new $.ig.Type('FilterMemberStateChangedEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('ResultSorter$1', 'Object', {
	$t: null,
	_result: null,
	result: function (value) {
		if (arguments.length === 1) {
			this._result = value;
			return value;
		} else {
			return this._result;
		}
	}
	,
	_hasRows: false,
	hasRows: function (value) {
		if (arguments.length === 1) {
			this._hasRows = value;
			return value;
		} else {
			return this._hasRows;
		}
	}
	,
	_hasColumns: false,
	hasColumns: function (value) {
		if (arguments.length === 1) {
			this._hasColumns = value;
			return value;
		} else {
			return this._hasColumns;
		}
	}
	,
	init: function ($t, result, hasRows, hasColumns) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.result(result);
		this.hasRows(hasRows);
		this.hasColumns(hasColumns);
	},
	sort: function () {
	}
	,
	sortAxis: function (visitor, axis, indicesMap) {
		visitor.visitTuples();
		var tupleIndices = visitor.visitedTupleIndices();
		var tuples = new Array(tupleIndices.count());
		for (var i = 0; i < tupleIndices.count(); i++) {
			var index = tupleIndices.item(i);
			indicesMap.add(index, i);
		}
		for (var i1 = 0; i1 < tupleIndices.count(); i1++) {
			var index1 = tupleIndices.item(i1);
			var tuple = axis.tuples().__inner[index1];
			tuples[i1] = tuple;
		}
		return new $.ig.List$1($.ig.OlapResultTuple.prototype.$type, 1, tuples);
	}
	,
	$type: new $.ig.Type('ResultSorter$1', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('AxisTupleVisitor', 'Object', {
	_axis: null,
	axis: function (value) {
		if (arguments.length === 1) {
			this._axis = value;
			return value;
		} else {
			return this._axis;
		}
	}
	,
	_visitedTupleIndices: null,
	visitedTupleIndices: function (value) {
		if (arguments.length === 1) {
			this._visitedTupleIndices = value;
			return value;
		} else {
			return this._visitedTupleIndices;
		}
	}
	,
	init: function (axis) {
		$.ig.Object.prototype.init.call(this);
		this.axis(axis);
	},
	visitTuples: function () {
		if (this.axis().positionResolver().hasUnregisterdPositions()) {
			this.axis().positionResolver().completeRegisterPositions();
		}
		this.visitedTupleIndices(new $.ig.List$1($.ig.Number.prototype.$type, 2, this.axis().tuples().count()));
		var rootItemPositionInfo = this.axis().positionResolver().rootPositionInfo();
		var positionItemInfos = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), rootItemPositionInfo.values(), function (pi) { return pi.positionItemDepth() == rootItemPositionInfo.positionItemDepthMin(); });
		var en = this.sortPositions(rootItemPositionInfo, positionItemInfos).getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			this.visitTuples1(rootItemPositionInfo, positionItemInfo);
		}
	}
	,
	getChildPositions: function (hostPositionItemInfo, positionItemInfo) {
	}
	,
	sortPositions: function (hostPositionItemInfo, positionItemInfos) {
	}
	,
	visitTuples1: function (hostPositionItemInfo, positionItemInfo) {
		var hostedPosionsInfo = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values(), function (pi) { return pi.positionItemDepth() == positionItemInfo.positionItemDepthMin(); });
		if ($.ig.Enumerable.prototype.count$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostedPosionsInfo) > 0) {
			var en = hostedPosionsInfo.getEnumerator();
			while (en.moveNext()) {
				var hostedItemInfo = en.current();
				this.visitTuples1(positionItemInfo, hostedItemInfo);
			}
		} else {
			this.visitedTupleIndices().add(positionItemInfo.positionIndex());
		}
		var childrenPosionInfo = this.getChildPositions(hostPositionItemInfo, positionItemInfo);
		var en1 = childrenPosionInfo.getEnumerator();
		while (en1.moveNext()) {
			var childItemInfo = en1.current();
			this.visitTuples1(hostPositionItemInfo, childItemInfo);
		}
	}
	,
	$type: new $.ig.Type('AxisTupleVisitor', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('AxisHeaderSortingVisitor', 'AxisTupleVisitor', {
	__sortDirections: null,
	__appliedSortDirections: null,
	__appliedSortDirectionsMap: null,
	init: function (axis, sortDirections, appliedSortDirections, appliedSortDirectionsMap) {
		$.ig.AxisTupleVisitor.prototype.init.call(this, axis);
		this.__sortDirections = sortDirections;
		this.__appliedSortDirections = appliedSortDirections;
		this.__appliedSortDirectionsMap = appliedSortDirectionsMap;
	},
	getChildPositions: function (hostPositionItemInfo, positionItemInfo) {
		var $self = this;
		var childPositions = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); }));
		if (childPositions.count() > 0) {
			var childPositionInfo = childPositions.item(0);
			var sortDirection;
			if ((function () { var $ret = $self.__sortDirections.tryGetValue(childPositionInfo.hierarchyLevelKey(), sortDirection); sortDirection = $ret.p1; return $ret.ret; }())) {
				if (sortDirection.sortBehavior() == $.ig.LevelSortBehavior.prototype.system) {
					childPositions = sortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderBy$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Object.prototype.$type, childPositions, function (pi) { return pi.sortKey(); })) : $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderByDescending$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Object.prototype.$type, childPositions, function (pi) { return pi.sortKey(); }));
				} else {
					childPositions = sortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderBy$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), String, childPositions, function (pi) { return pi.caption(); })) : $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderByDescending$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), String, childPositions, function (pi) { return pi.caption(); }));
				}
				if (!$.ig.Enumerable.prototype.contains$1(String, $.ig.Enumerable.prototype.select$2($.ig.LevelSortDirection.prototype.$type, String, this.__appliedSortDirections, function (x) { return x.levelUniqueName(); }), sortDirection.levelUniqueName())) {
					this.__appliedSortDirections.add(sortDirection);
				}
				var positions;
				if (!(function () { var $ret = $self.__appliedSortDirectionsMap.tryGetValue(childPositionInfo.positionItemIndex(), positions); positions = $ret.p1; return $ret.ret; }())) {
					positions = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type, 0);
					this.__appliedSortDirectionsMap.add(positionItemInfo.positionItemIndex(), positions);
				}
				positions.item(positionItemInfo.positionItemDepth(), sortDirection.sortDirection());
			}
		}
		return childPositions;
	}
	,
	sortPositions: function (hostPositionItemInfo, positionItemInfos) {
		var $self = this;
		var positionItemInfosList = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfos);
		if (positionItemInfosList.count() > 0) {
			var childPositionInfo = positionItemInfosList.item(0);
			var sortDirection;
			if (childPositionInfo.hierarchyLevelKey() != null && (function () { var $ret = $self.__sortDirections.tryGetValue(childPositionInfo.hierarchyLevelKey(), sortDirection); sortDirection = $ret.p1; return $ret.ret; }())) {
				if (sortDirection.sortBehavior() == $.ig.LevelSortBehavior.prototype.system) {
					positionItemInfosList = sortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderBy$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Object.prototype.$type, positionItemInfosList, function (pi) { return pi.sortKey(); })) : $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderByDescending$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Object.prototype.$type, positionItemInfosList, function (pi) { return pi.sortKey(); }));
				} else {
					positionItemInfosList = sortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderBy$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), String, positionItemInfosList, function (pi) { return pi.caption(); })) : $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.orderByDescending$2($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), String, positionItemInfosList, function (pi) { return pi.caption(); }));
				}
				this.__appliedSortDirections.add(sortDirection);
				var positions;
				if (!(function () { var $ret = $self.__appliedSortDirectionsMap.tryGetValue(childPositionInfo.positionItemIndex(), positions); positions = $ret.p1; return $ret.ret; }())) {
					positions = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type, 0);
					this.__appliedSortDirectionsMap.add(hostPositionItemInfo.positionItemIndex(), positions);
				}
				positions.item(hostPositionItemInfo.positionItemDepth(), sortDirection.sortDirection());
			}
		}
		return positionItemInfosList;
	}
	,
	$type: new $.ig.Type('AxisHeaderSortingVisitor', $.ig.AxisTupleVisitor.prototype.$type)
}, true);

$.ig.util.defType('ResultHeaderSorter', 'ResultSorter$1', {
	__sortDirections: null,
	_appliedSortDirections: null,
	appliedSortDirections: function (value) {
		if (arguments.length === 1) {
			this._appliedSortDirections = value;
			return value;
		} else {
			return this._appliedSortDirections;
		}
	}
	,
	_appliedSortDirectionsMap: null,
	appliedSortDirectionsMap: function (value) {
		if (arguments.length === 1) {
			this._appliedSortDirectionsMap = value;
			return value;
		} else {
			return this._appliedSortDirectionsMap;
		}
	}
	,
	init: function (result, hasRows, hasColumns, sortDirections) {
		$.ig.ResultSorter$1.prototype.init.call(this, $.ig.AxisHeaderSortingVisitor.prototype.$type, result, hasRows, hasColumns);
		this.__sortDirections = new $.ig.Dictionary$2(String, $.ig.LevelSortDirection.prototype.$type, 1, sortDirections.count());
		var en = sortDirections.getEnumerator();
		while (en.moveNext()) {
			var levelSortDirection = en.current();
			this.__sortDirections.item(levelSortDirection.levelUniqueName(), levelSortDirection);
		}
	},
	sort: function () {
		var $self = this;
		this.appliedSortDirections(new $.ig.List$1($.ig.LevelSortDirection.prototype.$type, 0));
		this.appliedSortDirectionsMap(new $.ig.Dictionary$2(String, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type)), 0));
		if (this.result().isEmpty()) {
			return this.result();
		}
		var sortedAxes = new $.ig.List$1($.ig.OlapResultAxis.prototype.$type, 0);
		var axisIndex = 0;
		var columnsCount = 1;
		var columnIndicesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		var rowIndecesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		if (this.hasColumns()) {
			var columnAxis = this.result().axes().item(axisIndex);
			var columnSortDirectionsMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type), 0);
			var visitor = new $.ig.AxisHeaderSortingVisitor(columnAxis, this.__sortDirections, this.appliedSortDirections(), columnSortDirectionsMap);
			var sortedTuples = this.sortAxis(visitor, columnAxis, columnIndicesMap);
			var sortedColumnAxis = (function () {
				var $ret = new $.ig.OlapResultAxis(sortedTuples, columnAxis.tupleSize());
				$ret.name(columnAxis.name());
				return $ret;
			}());
			sortedAxes.add(sortedColumnAxis);
			this.appliedSortDirectionsMap().item(columnAxis.name(), columnSortDirectionsMap);
			columnsCount = columnAxis.tuples().count();
			axisIndex++;
		}
		if (this.hasRows()) {
			var rowAxis = this.result().axes().item(axisIndex);
			var rowSortDirectionsMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.IDictionary$2.prototype.$type.specialize($.ig.Number.prototype.$type, $.ig.ListSortDirection.prototype.$type), 0);
			var visitor1 = new $.ig.AxisHeaderSortingVisitor(rowAxis, this.__sortDirections, this.appliedSortDirections(), rowSortDirectionsMap);
			var sortedTuples1 = this.sortAxis(visitor1, rowAxis, rowIndecesMap);
			var sortedRowAxis = (function () {
				var $ret = new $.ig.OlapResultAxis(sortedTuples1, rowAxis.tupleSize());
				$ret.name(rowAxis.name());
				return $ret;
			}());
			sortedAxes.add(sortedRowAxis);
			this.appliedSortDirectionsMap().item(rowAxis.name(), rowSortDirectionsMap);
		}
		var sortedCells = new $.ig.List$1($.ig.OlapResultCell.prototype.$type, 2, this.result().cells().count());
		var en = this.result().cells().getEnumerator();
		while (en.moveNext()) {
			var c = en.current();
			var cell = c.clone();
			var cellOrdinal = cell.cellOrdinal();
			if (this.hasColumns()) {
				var columnIndex = cellOrdinal % columnsCount;
				var newIndex = columnIndicesMap.item(columnIndex);
				if (columnIndex != newIndex) {
					var rowIndex = $.ig.intDivide(cellOrdinal, columnsCount);
					cellOrdinal = rowIndex * columnsCount + newIndex;
				}
			}
			if (this.hasRows()) {
				var rowIndex1 = $.ig.intDivide(cellOrdinal, columnsCount);
				var newIndex1 = rowIndecesMap.item(rowIndex1);
				if (rowIndex1 != newIndex1) {
					var columnIndex1 = cellOrdinal % columnsCount;
					cellOrdinal = newIndex1 * columnsCount + columnIndex1;
				}
			}
			cell.cellOrdinal(cellOrdinal);
			sortedCells.add(cell);
		}
		var sortedResult = (function () {
			var $ret = new $.ig.OlapResult();
			$ret.isEmpty($self.result().isEmpty());
			$ret.axes(sortedAxes);
			$ret.cells(sortedCells);
			return $ret;
		}());
		return sortedResult;
	}
	,
	$type: new $.ig.Type('ResultHeaderSorter', $.ig.ResultSorter$1.prototype.$type.specialize($.ig.AxisHeaderSortingVisitor.prototype.$type))
}, true);

$.ig.util.defType('AxisValueSortingVisitor', 'AxisTupleVisitor', {
	__tupleSortDirections: null,
	init: function (axis, tupleSortDirections) {
		$.ig.AxisTupleVisitor.prototype.init.call(this, axis);
		this.__tupleSortDirections = tupleSortDirections;
	},
	getChildPositions: function (hostPositionItemInfo, positionItemInfo) {
		var childPositions = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); });
		return this.sortPositions(hostPositionItemInfo, childPositions);
	}
	,
	sortPositions: function (hostPositionItemInfo, positionItemInfos) {
		var positionItemInfos_ = $.ig.Enumerable.prototype.toArray$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfos);
		if (positionItemInfos_.length > 0) {
			var tupleSortDirections_ = $.ig.Enumerable.prototype.toArray$1($.ig.KeyValuePair$2.prototype.$type.specialize($.ig.TupleSortDirection.prototype.$type, Array), this.__tupleSortDirections);
			
var sortFunction = function (tupleSortDirections) {
    var compareCells = function (comparer, cell1, cell2) {
        var result = comparer(cell1, cell2);
        if (result < 0) {
            return -1;
        }
        if (result > 0) {
            return 1;
        }
        return (!isNaN(parseFloat(result)) && isFinite(result)) ? result : 0;
    };
    return function (o1, o2) {
        var i, length, tupleSortDirection, cells, direction,
            cell1, cell2, arr1 = [], arr2 = [];

        for (i=0;i<tupleSortDirections.length;i++) {
            tupleSortDirection = tupleSortDirections[i].key();
            cells = tupleSortDirections[i].value();

            if (tupleSortDirection.sortDirection() === $.ig.ListSortDirection.prototype.ascending) {
                direction = 1;
            } else {
                direction = -1;
            }

            comparer = tupleSortDirection.comparer();

            cell1 = cells[o1.positionIndex()];
            cell2 = cells[o2.positionIndex()];
            if (tupleSortDirections.length === 1) {
                arr1 = direction * compareCells(comparer, cell1, cell2);
                arr2 = direction * compareCells(comparer, cell2, cell1);
            } else {
                arr1.push(direction * compareCells(comparer, cell1, cell2));
                arr2.push(direction * compareCells(comparer, cell2, cell1));
            }
        }

        if (arr1 < arr2) {
            return -1;
        }
        if (arr1 > arr2) {
            return 1;
        }
        return 0;
    };
};

positionItemInfos_.sort(sortFunction(tupleSortDirections_));
;
		}
		return positionItemInfos_;
	}
	,
	$type: new $.ig.Type('AxisValueSortingVisitor', $.ig.AxisTupleVisitor.prototype.$type)
}, true);

$.ig.util.defType('ResultColumnValueSorter', 'ResultSorter$1', {
	__tupleSortDirections: null,
	_appliedSortDirections: null,
	appliedSortDirections: function (value) {
		if (arguments.length === 1) {
			this._appliedSortDirections = value;
			return value;
		} else {
			return this._appliedSortDirections;
		}
	}
	,
	init: function (result, hasRows, hasColumns, tupleSortDirections) {
		$.ig.ResultSorter$1.prototype.init.call(this, $.ig.AxisValueSortingVisitor.prototype.$type, result, hasRows, hasColumns);
		this.__tupleSortDirections = tupleSortDirections;
	},
	sort: function () {
		var $self = this;
		this.appliedSortDirections(new $.ig.List$1($.ig.TupleSortDirection.prototype.$type, 0));
		if (!this.hasRows() || this.result().isEmpty()) {
			return this.result();
		}
		var sortedAxes = new $.ig.List$1($.ig.OlapResultAxis.prototype.$type, 0);
		var axisIndex = 0;
		var columnAxis = null;
		var columnsCount = 1;
		if (this.hasColumns()) {
			columnAxis = this.result().axes().item(axisIndex);
			columnsCount = columnAxis.tuples().count();
			sortedAxes.add(columnAxis.clone());
			axisIndex++;
		}
		var rowAxis = this.result().axes().item(axisIndex);
		var rowsCount = this.result().axes().item(axisIndex).tuples().count();
		var cellsMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Object.prototype.$type, 1, this.result().cells().count());
		var en = this.result().cells().getEnumerator();
		while (en.moveNext()) {
			var cell = en.current();
			var value = null;
			if ((function () { var $ret = cell.properties().tryGetValue("Value", value); value = $ret.p1; return $ret.ret; }())) {
				cellsMap.item(cell.cellOrdinal(), value);
			} else {
				var $ret = cell.properties().tryGetValue("FmtValue", value);
				value = $ret.p1;
				cellsMap.item(cell.cellOrdinal(), value);
			}
		}
		var columnValuesToSort = new $.ig.List$1($.ig.KeyValuePair$2.prototype.$type.specialize($.ig.TupleSortDirection.prototype.$type, Array), 2, this.__tupleSortDirections.count());
		var en1 = this.__tupleSortDirections.getEnumerator();
		while (en1.moveNext()) {
			var tupleSortDirection = en1.current();
			var columnIndex;
			if (columnAxis != null && tupleSortDirection.memberNames() != null) {
				if (columnAxis.positionResolver().hasUnregisterdPositions()) {
					columnAxis.positionResolver().completeRegisterPositions();
				}
				columnIndex = columnAxis.positionResolver().rootPositionInfo().getIndex(tupleSortDirection.memberNames());
				tupleSortDirection.tupleIndex(columnIndex);
			} else {
				columnIndex = tupleSortDirection.tupleIndex();
			}
			if (columnIndex > -1 && columnIndex < columnsCount) {
				var column = new Array(rowsCount);
				for (var rowIndex = 0; rowIndex < rowsCount; rowIndex++) {
					var cell1 = null;
					var $ret1 = cellsMap.tryGetValue(rowIndex * columnsCount + columnIndex, cell1);
					cell1 = $ret1.p1;
					column[rowIndex] = cell1;
				}
				columnValuesToSort.add(new $.ig.KeyValuePair$2($.ig.TupleSortDirection.prototype.$type, Array, 1, tupleSortDirection, column));
				this.appliedSortDirections().add(tupleSortDirection);
			}
		}
		var rowIndecesMap = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		var visitor = new $.ig.AxisValueSortingVisitor(rowAxis, columnValuesToSort);
		var sortedTuples = this.sortAxis(visitor, rowAxis, rowIndecesMap);
		var sortedRowAxis = (function () {
			var $ret = new $.ig.OlapResultAxis(sortedTuples, rowAxis.tupleSize());
			$ret.name(rowAxis.name());
			return $ret;
		}());
		sortedAxes.add(sortedRowAxis);
		var sortedCells = new $.ig.List$1($.ig.OlapResultCell.prototype.$type, 2, this.result().cells().count());
		var en2 = this.result().cells().getEnumerator();
		while (en2.moveNext()) {
			var c = en2.current();
			var cell2 = c.clone();
			var cellOrdinal = cell2.cellOrdinal();
			var rowIndex1 = $.ig.intDivide(cellOrdinal, columnsCount);
			var newIndex = rowIndecesMap.item(rowIndex1);
			if (rowIndex1 != newIndex) {
				var columnIndex1 = cellOrdinal % columnsCount;
				cellOrdinal = newIndex * columnsCount + columnIndex1;
			}
			cell2.cellOrdinal(cellOrdinal);
			sortedCells.add(cell2);
		}
		var sortedResult = (function () {
			var $ret = new $.ig.OlapResult();
			$ret.isEmpty($self.result().isEmpty());
			$ret.axes(sortedAxes);
			$ret.cells(sortedCells);
			return $ret;
		}());
		return sortedResult;
	}
	,
	$type: new $.ig.Type('ResultColumnValueSorter', $.ig.ResultSorter$1.prototype.$type.specialize($.ig.AxisValueSortingVisitor.prototype.$type))
}, true);

$.ig.util.defType('TupleSortDirection', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_tupleIndex: 0,
	tupleIndex: function (value) {
		if (arguments.length === 1) {
			this._tupleIndex = value;
			return value;
		} else {
			return this._tupleIndex;
		}
	}
	,
	_memberNames: null,
	memberNames: function (value) {
		if (arguments.length === 1) {
			this._memberNames = value;
			return value;
		} else {
			return this._memberNames;
		}
	}
	,
	_sortDirection: 0,
	sortDirection: function (value) {
		if (arguments.length === 1) {
			this._sortDirection = value;
			return value;
		} else {
			return this._sortDirection;
		}
	}
	,
	_comparer: null,
	comparer: function (value) {
		if (arguments.length === 1) {
			this._comparer = value;
			return value;
		} else {
			return this._comparer;
		}
	}
	,
	$type: new $.ig.Type('TupleSortDirection', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataSourceBase', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_dataSource: null,
	dataSource: function (value) {
		if (arguments.length === 1) {
			this._dataSource = value;
			return value;
		} else {
			return this._dataSource;
		}
	}
	,
	isInitialized: function () {
		if (this.dataSource() == null) {
			return false;
		}
		return this.dataSource().isInitialized();
	}
	,
	_isModified: false,
	isModified: function (value) {
		if (arguments.length === 1) {
			this._isModified = value;
			return value;
		} else {
			return this._isModified;
		}
	}
	,
	_isUpdating: false,
	isUpdating: function (value) {
		if (arguments.length === 1) {
			this._isUpdating = value;
			return value;
		} else {
			return this._isUpdating;
		}
	}
	,
	initialize: function () {
		var promise_ = this.dataSource().initialize().promise();
		
var $this = this;
if (!this._isInitializedAttached) {
    this._isInitializedAttached = true;
    promise_.always(function (result) {
        var args = {
            error: null,
            metadataTreeRoot: null
        };

        if (this.state() === 'rejected') {
            args.error = result;
        } else {
            args.metadataTreeRoot = result;
        }

        $($this).trigger('initialized', args);
        $this._isInitializedAttached = false;
    });
}
;
		return promise_;
	}
	,
	cubes: function () {
		return (this.dataSource().cubes()).inner();
	}
	,
	cube: function () {
		return this.dataSource().cube();
	}
	,
	setCube: function (cubeName) {
		return this.dataSource().setCube(cubeName).continueWith$11($.ig.OlapMetadataTreeItem.prototype.$type, function (t) {
			return t.result();
		}).promise();
	}
	,
	metadataTree: function () {
		return this.dataSource().metadataTree();
	}
	,
	addRowItem: function (rowItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, rowItem) !== null) {
			this.dataSource().rowAxis().add(rowItem);
			this.isModified(true);
		}
	}
	,
	insertRowItem: function (index, rowItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, rowItem) !== null) {
			this.dataSource().rowAxis().insert(index, rowItem);
			this.isModified(true);
		}
	}
	,
	removeRowItem: function (rowItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, rowItem) !== null || $.ig.util.cast($.ig.MeasureList.prototype.$type, rowItem) !== null) {
			this.dataSource().rowAxis().remove(rowItem);
			this.isModified(true);
		}
	}
	,
	addColumnItem: function (columnItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, columnItem) !== null) {
			this.dataSource().columnAxis().add(columnItem);
			this.isModified(true);
		}
	}
	,
	insertColumnItem: function (index, columnItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, columnItem) !== null) {
			this.dataSource().columnAxis().insert(index, columnItem);
			this.isModified(true);
		}
	}
	,
	removeColumnItem: function (columnItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, columnItem) !== null || $.ig.util.cast($.ig.MeasureList.prototype.$type, columnItem) !== null) {
			this.dataSource().columnAxis().remove(columnItem);
			this.isModified(true);
		}
	}
	,
	addFilterItem: function (filterItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, filterItem) !== null) {
			this.dataSource().filters().add(filterItem);
			this.isModified(true);
		}
	}
	,
	insertFilterItem: function (index, filterItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, filterItem) !== null) {
			this.dataSource().filters().insert(index, filterItem);
			this.isModified(true);
		}
	}
	,
	removeFilterItem: function (filterItem) {
		if ($.ig.util.cast($.ig.Hierarchy.prototype.$type, filterItem) !== null) {
			this.dataSource().filters().remove(filterItem);
			this.isModified(true);
		}
	}
	,
	addMeasureItem: function (measureItem) {
		if ($.ig.util.cast($.ig.Measure.prototype.$type, measureItem) !== null) {
			this.dataSource().measures().add(measureItem);
			this.isModified(true);
		}
	}
	,
	insertMeasureItem: function (index, measureItem) {
		if ($.ig.util.cast($.ig.Measure.prototype.$type, measureItem) !== null) {
			this.dataSource().measures().insert(index, measureItem);
			this.isModified(true);
		}
	}
	,
	removeMeasureItem: function (measureItem) {
		if ($.ig.util.cast($.ig.Measure.prototype.$type, measureItem) !== null) {
			this.dataSource().measures().remove(measureItem);
			this.isModified(true);
		}
	}
	,
	setMeasureListIndex: function (index) {
		this.dataSource().measureListIndex(index);
		this.isModified(true);
	}
	,
	setMeasureListLocation: function (location) {
		switch (location) {
			case "rows":
				this.dataSource().measureListLocation($.ig.MeasureListLocation.prototype.rows);
				break;
			case "columns":
				this.dataSource().measureListLocation($.ig.MeasureListLocation.prototype.columns);
				break;
			default: break;
		}
		this.isModified(true);
	}
	,
	getMeasureListLocation: function () {
		switch (this.dataSource().measureListLocation()) {
			case $.ig.MeasureListLocation.prototype.rows:
				return "rows";
				break;
			case $.ig.MeasureListLocation.prototype.columns:
				return "columns";
				break;
			default:
				return null;
				break;
		}
	}
	,
	expandTupleMember: function (axisName, tupleIndex, memberIndex) {
		this.dataSource().expandTupleMember(axisName, tupleIndex, memberIndex);
	}
	,
	collapseTupleMember: function (axisName, tupleIndex, memberIndex) {
		this.dataSource().collapseTupleMember(axisName, tupleIndex, memberIndex);
	}
	,
	rowAxis: function () {
		return (this.dataSource().rowAxis()).inner();
	}
	,
	columnAxis: function () {
		return (this.dataSource().columnAxis()).inner();
	}
	,
	filters: function () {
		return (this.dataSource().filters()).inner();
	}
	,
	measures: function () {
		return (this.dataSource().measures()).inner();
	}
	,
	result: function () {
		return this.dataSource().result();
	}
	,
	bindCollectionChanged: function (options) {
		var options_ = options;
		var ds_ = this.dataSource();
		
var attachCollectionChanged = function(collection, handler) {
    collection.collectionChanged = $.ig.Delegate.prototype.combine(
        collection.collectionChanged,
        handler
    );
};

if ($.isFunction(options_['filters'])) {
    attachCollectionChanged(ds_._filters, options_['filters']);
}
if ($.isFunction(options_['rowAxis'])) {
    attachCollectionChanged(ds_._rowAxis, options_['rowAxis']);
}
if ($.isFunction(options_['columnAxis'])) {
    attachCollectionChanged(ds_._columnAxis, options_['columnAxis']);
}
if ($.isFunction(options_['measures'])) {
    attachCollectionChanged(ds_._measures, options_['measures']);
}
;
	}
	,
	unbindCollectionChanged: function (options) {
		var options_ = options;
		var ds_ = this.dataSource();
		
unbindCollectionChanged = function(collection, handler) {
    collection.collectionChanged = $.ig.Delegate.prototype.remove(
        collection.collectionChanged,
        handler
    );
};

if ($.isFunction(options_['filters'])) {
    unbindCollectionChanged(ds_._filters, options_['filters']);
}
if ($.isFunction(options_['rowAxis'])) {
    unbindCollectionChanged(ds_._rowAxis, options_['rowAxis']);
}
if ($.isFunction(options_['columnAxis'])) {
    unbindCollectionChanged(ds_._columnAxis, options_['columnAxis']);
}
if ($.isFunction(options_['measures'])) {
    unbindCollectionChanged(ds_._measures, options_['measures']);
}
;
	}
	,
	clearPendingChanges: function () {
		this.dataSource().clearPendingChanges();
	}
	,
	update: function () {
		var promise_ = this.dataSource().update().promise();
		
var $this = this;
if (!this.isUpdating()) {
    this.isUpdating(true);
    promise_.always(function (result) {
        var args = {
            error: null,
            result: null
        };

        if (this.state() === 'rejected') {
            args.error = result;
        } else {
            args.result = result;
        }

        $this.isUpdating(false);
        $this.isModified(false);
        $($this).trigger('updated', args);
    });
}
;
		return promise_;
	}
	,
	getCoreElement: function (predicate, elementType) {
		return this.dataSource().getCoreElement(predicate, elementType);
	}
	,
	getCoreElements: function (predicate, elementType) {
		return (this.dataSource().getCoreElements(predicate, elementType)).inner();
	}
	,
	getMembersOfHierarchy: function (hierarchyUniqueName) {
		return this.dataSource().getMembersOfHierarchy(hierarchyUniqueName).promise();
	}
	,
	getMembersOfLevel: function (levelUniqueName) {
		return this.dataSource().getMembersOfLevel(levelUniqueName).promise();
	}
	,
	tryGetMember: function (memberUniqueName) {
		var $self = this;
		var member;
		if ((function () { var $ret = $self.dataSource().tryGetMember(memberUniqueName, member); member = $ret.p1; return $ret.ret; }())) {
			return member;
		}
		return null;
	}
	,
	tryGetMembersForLevel: function (levelUniqueName) {
		var $self = this;
		var members = new $.ig.ArrayListCollection$1($.ig.Member.prototype.$type);
		var success = (function () { var $ret = $self.dataSource().tryGetMembersForLevel(levelUniqueName, members); members = $ret.p1; return $ret.ret; }());
		return success ? (members).inner() : (new $.ig.ArrayListCollection$1($.ig.Member.prototype.$type)).inner();
	}
	,
	getMembersOfMember: function (memberUniqueName) {
		return this.dataSource().getMembersOfMember(memberUniqueName).promise();
	}
	,
	addFilterMember: function (hierarchyUniqueName, memberUniqueName) {
		this.dataSource().addFilterMember(hierarchyUniqueName, memberUniqueName);
		this.isModified(true);
	}
	,
	removeAllFilterMembers: function (hierarchyUniqueName) {
		this.dataSource().removeAllFilterMembers(hierarchyUniqueName);
		this.isModified(true);
	}
	,
	getFilterMemberNames: function (hierarchyUniqueName) {
		return (this.dataSource().getFilterMemberNames(hierarchyUniqueName)).inner();
	}
	,
	getDimension: function (dimensionUniqueName) {
		return this.dataSource().getCoreElement(function (d) { return d.uniqueName() == dimensionUniqueName; }, $.ig.Dimension.prototype.$type);
	}
	,
	getHierarchy: function (hierarchyUniqueName) {
		return this.dataSource().getCoreElement(function (h) { return h.uniqueName() == hierarchyUniqueName; }, $.ig.Hierarchy.prototype.$type);
	}
	,
	getLevel: function (levelUniqueName) {
		return this.dataSource().getCoreElement(function (l) { return l.uniqueName() == levelUniqueName; }, $.ig.Level.prototype.$type);
	}
	,
	getMeasure: function (measurelUniqueName) {
		return this.dataSource().getCoreElement(function (m) { return m.uniqueName() == measurelUniqueName; }, $.ig.Measure.prototype.$type);
	}
	,
	getMeasureList: function () {
		return this.dataSource().getCoreElement(null, $.ig.MeasureList.prototype.$type);
	}
	,
	$type: new $.ig.Type('DataSourceBase', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('jQueryUtils', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	javascriptObjectToDictionary$1: function ($t, jsObject) {
		if (jsObject == null) {
			return null;
		}
		var jsObject_ = jsObject;
		var dict_ = new $.ig.Dictionary$2(String, $t, 0);
		(function (obj, dict) {
                var tempDict = $.ig.Dictionary.prototype.getDictionary(obj);
                var keys = tempDict.keys();
                while (keys.moveNext()){
                    var key = keys.current();
                    var value = tempDict.item(key);
                    dict.add(key, value);
                }
            })(jsObject_, dict_);
		return dict_;
	}
	,
	javascriptObjectFromDictionary$1: function ($t, dict) {
		if (dict == null) {
			return null;
		}
		var jsObject_ = $.ig.jQueryUtils.prototype.createJavascriptObject();
		var en = dict.getEnumerator();
		while (en.moveNext()) {
			var item_ = en.current();
			jsObject_[item_.key()] = item_.value();
		}
		return jsObject_;
	}
	,
	convertToObjectDictionary: function (jsObject) {
		return $.ig.jQueryUtils.prototype.javascriptObjectToDictionary$1($.ig.Object.prototype.$type, jsObject);
	}
	,
	convertToStringDictionary: function (jsObject) {
		return $.ig.jQueryUtils.prototype.javascriptObjectToDictionary$1(String, jsObject);
	}
	,
	convertFromObjectDictionary: function (dictionary) {
		return $.ig.jQueryUtils.prototype.javascriptObjectFromDictionary$1($.ig.Object.prototype.$type, dictionary);
	}
	,
	convertFromStringDictionary: function (dictionary) {
		return $.ig.jQueryUtils.prototype.javascriptObjectFromDictionary$1(String, dictionary);
	}
	,
	createJavascriptObject: function () {
		return {};
	}
	,
	getJavascriptObjectProperty: function (jsObject, property) {
		var jsObject_ = jsObject;
		var property_ = property;
		return jsObject_[property_];
	}
	,
	setJavascriptObjectProperty: function (jsObject, property, value) {
		var jsObject_ = jsObject;
		var property_ = property;
		var value_ = value;
		jsObject_[property_] = value_;
	}
	,
	getJavascriptObjectMethodResult: function (jsObject, method, args) {
		var jsObject_ = jsObject;
		var method_ = method;
		var args_ = args;
		return jsObject_[method_].call(jsObject_, args_);
	}
	,
	$type: new $.ig.Type('jQueryUtils', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapUtilities', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	dateMemberProvider: function (datePart, datePropName, format, datePartFormat) {
		if (String.isNullOrEmpty(datePart) || String.isNullOrEmpty(datePropName)) {
			return null;
		}
		var propName_ = datePropName;
		var datePartFormat_ = datePartFormat;
		var format_ = format;
		var yearProvider_;
		var semesterProvider_;
		var quarterProvider_;
		var monthProvider_;
		datePart = datePart.toLowerCase();
		switch (datePart) {
			case "year":
				if (datePartFormat_ == null) {
					datePartFormat_ = "yyyy";
				}
				if (format_ == null) {
					return function (item) { return $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_); };
				}
				return function (item) { return format_.replace('{0}', $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_)); };
			case "semester":
				if (format_ == null) {
					format_ = "H{0}";
				}
				yearProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("year", datePropName, null, null);
				return function (item) {
                    var year = yearProvider_(item);
                    return format_.replace('{0}', Math.floor(new Date(item[propName_]).getMonth() / 6) + 1).replace('{1}', year); 
                };
			case "quarter":
				if (format_ == null) {
					format_ = "Q{0}";
				}
				yearProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("year", datePropName, null, null);
				semesterProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("semester", datePropName, null, null);
				return function (item) {
                    var year = yearProvider_(item);
                    var semester = semesterProvider_(item);
                    return format_.replace('{0}', Math.floor(new Date(item[propName_]).getMonth() / 3) + 1).replace('{1}', semester).replace('{2}', year); 
                };
			case "month":
				if (datePartFormat_ == null) {
					datePartFormat_ = "MMMM";
				}
				if (format_ == null) {
					return function (item) { return $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_); };
				}
				yearProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("year", datePropName, null, null);
				semesterProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("semester", datePropName, null, null);
				quarterProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("quarter", datePropName, null, null);
				return function (item) {
                    var year = yearProvider_(item);
                    var semester = semesterProvider_(item);
                    var quarter = quarterProvider_(item);
                    return format_.replace('{0}', $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_)).replace('{1}', quarter).replace('{2}', semester).replace('{3}', year); 
                };
			case "date":
				if (datePartFormat_ == null) {
					datePartFormat_ = $.ig.regional.defaults.datePattern.toString();
				}
				if (format_ == null) {
					return function (item) { return $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_); };
				}
				yearProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("year", datePropName, null, null);
				semesterProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("semester", datePropName, null, null);
				quarterProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("quarter", datePropName, null, null);
				monthProvider_ = $.ig.OlapUtilities.prototype.dateMemberProvider("month", datePropName, null, null);
				return function (item) {
                    var year = yearProvider_(item);
                    var semester = semesterProvider_(item);
                    var quarter = quarterProvider_(item);
                    var month = monthProvider_(item);
                    return format_.replace('{0}', $.ig.formatter(new Date(item[propName_]), 'date', datePartFormat_)).replace('{1}', month).replace('{2}', quarter).replace('{3}', semester).replace('{4}', year); 
                };
		}
		return null;
	}
	,
	getDateHierarchy: function (datePropName, dateParts, name, caption, levelCaptions, rootCaption) {
		if (String.isNullOrEmpty(datePropName)) {
			return null;
		}
		if (dateParts == null || dateParts.length == 0) {
			dateParts = [ "year", "quarter", "month", "date" ];
		} else {
			for (var i = 0; i < dateParts.length; i++) {
				dateParts[i] = dateParts[i].toLowerCase();
			}
		}
		if (name == null) {
			name = "Dates";
		}
		if (caption == null) {
			caption = name;
		}
		var res_ = null;
		var name_ = name;
		var caption_ = caption;
		var rootCaption_ = rootCaption;
		var dateParts_ = dateParts;
		if (levelCaptions == null) {
			levelCaptions = new Array(dateParts.length);
			for (var i1 = 0; i1 < levelCaptions.length; i1++) {
				var datePart = dateParts[i1];
				levelCaptions[i1] = datePart.substr(0, 1).toUpperCase() + datePart.substr(1, datePart.length) + "s";
			}
		}
		var captions_ = levelCaptions;
		var memberProviders_ = new Array(dateParts.length);
		for (var i2 = 0; i2 < dateParts.length; i2++) {
			var datePart1 = dateParts[i2];
			memberProviders_[i2] = $.ig.OlapUtilities.prototype.dateMemberProvider(datePart1, datePropName, null, null);
		}
		
            var levelsMetadata = [];
            var levelOffset = 0;

            if (rootCaption_ != null && rootCaption_ != '') {
                levelOffset = 1;
                levelsMetadata[0] = { name: rootCaption_, caption: rootCaption_, memberProvider: function(item) { return rootCaption_;}};
            }

            for (var i = 0; i < memberProviders_.length; i++){
                levelsMetadata[i + levelOffset] = { name: dateParts_[i], caption: captions_[i], memberProvider: memberProviders_[i]};
            }

            res_ = { name: name_, caption: caption_, levels: levelsMetadata, isDateTimeHier: true };
;
		return res_;
	}
	,
	sumAggregator: function (propName, precision) {
		var propName_ = propName;
		var precision_ = precision;
		return function(items, cellMetadata) {
            var sum = 0, areAllNullOrUndefined = true, value;

            for (var i = 0; i < items.length; i++) {
                value = items[i][propName_];
                if (value !== null && value !== undefined) {
                    areAllNullOrUndefined = false;
                    sum += value;
                }
            }
            
            if (areAllNullOrUndefined)
                return null;
            if (precision_ != null) {
                return sum.toFixed(precision_);
            }
            return sum;            
        };
	}
	,
	avgAggregator: function (propName, precision) {
		var propName_ = propName;
		var precision_ = precision;
		return function(items, cellMetadata) {
            if (precision_ == null) {
                precision_ = 2;
            }
            var sum = 0, count = 0, areAllNullOrUndefined = true, value;
            for (var i = 0; i < items.length; i++) {
                value = items[i][propName_];
                if (value !== null && value !== undefined) {
                    areAllNullOrUndefined = false;
                    sum += value;
                    count++;
                }
            }
                
            if (areAllNullOrUndefined)
                return null;

            return (sum/count).toFixed(precision_);
        };
	}
	,
	minAggregator: function (propName) {
		var propName_ = propName;
		return function(items, cellMetadata) {
            var result = items[0][propName_];;                
            for (var i = 1; i < items.length; i++) {
                result = Math.min(result, items[i][propName_]);
            }
            return result;
        };
	}
	,
	maxAggregator: function (propName) {
		var propName_ = propName;
		return function(items, cellMetadata) {
            var result = items[0][propName_];;                
            for (var i = 1; i < items.length; i++) {
                result = Math.max(result, items[i][propName_]);
            }
            return result;
        };
	}
	,
	countAggregator: function () {
		return function(items, cellMetadata) {
            return items.length;
        };
	}
	,
	$type: new $.ig.Type('OlapUtilities', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapTableView', 'Object', {
	__tableViewImpl: null,
	init: function (result, hasColumns, hasRows, viewSettings) {
		$.ig.Object.prototype.init.call(this);
		var settings = this.settingsFromJavascriptObject(viewSettings);
		this.__tableViewImpl = new $.ig.TableViewImpl(result, hasColumns, hasRows, settings);
	},
	rowHeaders: function () {
		if (this.__tableViewImpl.rowHeaders() == null) {
			return null;
		}
		return (this.__tableViewImpl.rowHeaders()).inner();
	}
	,
	columnHeaders: function () {
		if (this.__tableViewImpl.columnHeaders() == null) {
			return null;
		}
		return (this.__tableViewImpl.columnHeaders()).inner();
	}
	,
	resultCells: function () {
		if (this.__tableViewImpl.resultCells() == null) {
			return null;
		}
		return (this.__tableViewImpl.resultCells()).inner();
	}
	,
	columnSortDirections: function (value) {
		if (arguments.length === 1) {
			this.__tableViewImpl.columnSortDirections().clear();
			var en = value.getEnumerator();
			while (en.moveNext()) {
				var obj = en.current();
				var tupleSortDirection = this.tupleSortDirectionFromJavascriptObject(obj);
				this.__tableViewImpl.columnSortDirections().add(tupleSortDirection);
			}
			return value;
		} else {
			var columnSortDirections = new $.ig.Array();
			var en = this.__tableViewImpl.columnSortDirections().getEnumerator();
			while (en.moveNext()) {
				var columnSortDirection = en.current();
				columnSortDirections.add(this.tupleSortDirectionToJavascriptObject(columnSortDirection));
			}
			return columnSortDirections;
		}
	}
	,
	appliedColumnSortDirections: function () {
		var columnSortDirections = new $.ig.Array();
		var en = this.__tableViewImpl.appliedColumnSortDirections().getEnumerator();
		while (en.moveNext()) {
			var columnSortDirection = en.current();
			columnSortDirections.add(this.tupleSortDirectionToJavascriptObject(columnSortDirection));
		}
		return columnSortDirections;
	}
	,
	levelSortDirections: function (value) {
		if (arguments.length === 1) {
			this.__tableViewImpl.levelSortDirections().clear();
			var en = value.getEnumerator();
			while (en.moveNext()) {
				var obj = en.current();
				var levelSortDirection = this.levelSortDirectionFromJavascriptObject(obj);
				this.__tableViewImpl.levelSortDirections().add(levelSortDirection);
			}
			return value;
		} else {
			var levelSortDirections = new $.ig.Array();
			var en = this.__tableViewImpl.levelSortDirections().getEnumerator();
			while (en.moveNext()) {
				var levelSortDirection = en.current();
				levelSortDirections.add(this.levelSortDirectionToJavascriptObject(levelSortDirection));
			}
			return levelSortDirections;
		}
	}
	,
	appliedLevelSortDirections: function () {
		var levelSortDirections = new $.ig.Array();
		var en = this.__tableViewImpl.appliedLevelSortDirections().getEnumerator();
		while (en.moveNext()) {
			var levelSortDirection = en.current();
			levelSortDirections.add(this.levelSortDirectionToJavascriptObject(levelSortDirection));
		}
		return levelSortDirections;
	}
	,
	appliedSortDirectionsMap: function () {
		var resultMap_ = $.ig.jQueryUtils.prototype.createJavascriptObject();
		var i_En = this.__tableViewImpl.appliedSortDirectionsMap().getEnumerator();
		while (i_En.moveNext()) {
			var i_ = i_En.current();
			var axisMap_ = $.ig.jQueryUtils.prototype.createJavascriptObject();
			var j_En = i_.value().getEnumerator();
			while (j_En.moveNext()) {
				var j_ = j_En.current();
				var hierarchyMap_ = $.ig.jQueryUtils.prototype.createJavascriptObject();
				var k_En = j_.value().getEnumerator();
				while (k_En.moveNext()) {
					var k_ = k_En.current();
					var sortDirection_ = k_.value() == $.ig.ListSortDirection.prototype.ascending ? "ascending" : "descending";
					hierarchyMap_[k_.key()] = sortDirection_;
				}
				axisMap_[j_.key()] = hierarchyMap_;
			}
			resultMap_[i_.key()] = axisMap_;
		}
		return resultMap_;
	}
	,
	initialize: function () {
		this.__tableViewImpl.initialize();
	}
	,
	settingsFromJavascriptObject: function (o) {
		var viewSettings = new $.ig.TableViewSettings();
		var compactRowHeadersValue = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "rowHeadersLayout");
		var rowHeaderLayout = $.ig.RowHeaderLayout.prototype.superCompact;
		if (!String.isNullOrEmpty(compactRowHeadersValue)) {
			switch (compactRowHeadersValue) {
				case "tree":
					rowHeaderLayout = $.ig.RowHeaderLayout.prototype.tree;
					var compactRowHeaderIndentation = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "compactRowHeaderIndentation");
					var treeRowHeaderIndentation = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "treeRowHeaderIndentation");
					viewSettings = new $.ig.TreeLayoutTableViewSettings(compactRowHeaderIndentation, treeRowHeaderIndentation);
					break;
				case "standard":
					rowHeaderLayout = $.ig.RowHeaderLayout.prototype.standard;
					break;
			}
		}
		var isParentInFrontForColumns = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "isParentInFrontForColumns");
		var isParentInFrontForRows = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "isParentInFrontForRows");
		var compactColumnHeaders = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "compactColumnHeaders");
		viewSettings.isParentInFrontForColumns(isParentInFrontForColumns);
		viewSettings.isParentInFrontForRows(isParentInFrontForRows);
		viewSettings.compactColumnHeaders(compactColumnHeaders);
		viewSettings.rowHeaderLayout(rowHeaderLayout);
		return viewSettings;
	}
	,
	tupleSortDirectionFromJavascriptObject: function (o_) {
		var memberNames = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o_, "memberNames");
		var index = $.ig.util.getValue($.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o_, "tupleIndex"));
		var sortDirection = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o_, "sortDirection");
		var parsedSortDirection = $.ig.ListSortDirection.prototype.ascending;
		if (!String.isNullOrEmpty(sortDirection)) {
			sortDirection = sortDirection.toLowerCase();
			if (sortDirection == "descending") {
				parsedSortDirection = $.ig.ListSortDirection.prototype.descending;
			}
		}
		var comparer_ = null;
		
if ($.isFunction(o_.comparer)) {
    comparer_ = o_.comparer;
} else {
    comparer_ = function (o1, o2) {
        return parseFloat(o1 || -Infinity) - parseFloat(o2 || -Infinity);
    };
}
;
		return (function () {
			var $ret = new $.ig.TupleSortDirection();
			$ret.memberNames(memberNames);
			$ret.tupleIndex(index);
			$ret.sortDirection(parsedSortDirection);
			$ret.comparer(comparer_);
			return $ret;
		}());
	}
	,
	tupleSortDirectionToJavascriptObject: function (tupleSortDirection) {
		var jsTupleSortDirection = $.ig.jQueryUtils.prototype.createJavascriptObject();
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsTupleSortDirection, "memberNames", tupleSortDirection.memberNames());
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsTupleSortDirection, "tupleIndex", tupleSortDirection.tupleIndex());
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsTupleSortDirection, "sortDirection", tupleSortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? "ascending" : "descending");
		if (tupleSortDirection.comparer() != null) {
			$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsTupleSortDirection, "comparer", tupleSortDirection.comparer());
		}
		return jsTupleSortDirection;
	}
	,
	levelSortDirectionFromJavascriptObject: function (o) {
		var levelUniqueName = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "levelUniqueName");
		var sortDirection = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "sortDirection");
		var sortBehavior = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, "sortBehavior");
		if (!String.isNullOrEmpty(levelUniqueName)) {
			var levelSortDirection = new $.ig.LevelSortDirection();
			levelSortDirection.sortDirection($.ig.ListSortDirection.prototype.ascending);
			levelSortDirection.sortBehavior($.ig.LevelSortBehavior.prototype.system);
			levelSortDirection.levelUniqueName(levelUniqueName);
			if (!String.isNullOrEmpty(sortDirection)) {
				sortDirection = sortDirection.toLowerCase();
				if (sortDirection == "descending") {
					levelSortDirection.sortDirection($.ig.ListSortDirection.prototype.descending);
				}
			}
			if (!String.isNullOrEmpty(sortBehavior)) {
				sortBehavior = sortBehavior.toLowerCase();
				if (sortBehavior == "alphabetical") {
					levelSortDirection.sortBehavior($.ig.LevelSortBehavior.prototype.alphabetical);
				}
			}
			return levelSortDirection;
		} else {
			return null;
		}
	}
	,
	levelSortDirectionToJavascriptObject: function (levelSortDirection) {
		var jsLevelSortDirection = $.ig.jQueryUtils.prototype.createJavascriptObject();
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsLevelSortDirection, "levelUniqueName", levelSortDirection.levelUniqueName());
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsLevelSortDirection, "sortBehavior", levelSortDirection.sortBehavior() == $.ig.LevelSortBehavior.prototype.system ? "system" : "alphabetical");
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(jsLevelSortDirection, "sortDirection", levelSortDirection.sortDirection() == $.ig.ListSortDirection.prototype.ascending ? "ascending" : "descending");
		return jsLevelSortDirection;
	}
	,
	$type: new $.ig.Type('OlapTableView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ICoreOlapElement', 'Object', {
	$type: new $.ig.Type('ICoreOlapElement', null)
}, true);

$.ig.util.defType('Cube', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_catalogName: null,
	catalogName: function (value) {
		if (arguments.length === 1) {
			this._catalogName = value;
			return value;
		} else {
			return this._catalogName;
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
	_cubeType: 0,
	cubeType: function (value) {
		if (arguments.length === 1) {
			this._cubeType = value;
			return value;
		} else {
			return this._cubeType;
		}
	}
	,
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
	_lastProcessed: new Date(),
	lastProcessed: function (value) {
		if (arguments.length === 1) {
			this._lastProcessed = value;
			return value;
		} else {
			return this._lastProcessed;
		}
	}
	,
	_lastUpdated: new Date(),
	lastUpdated: function (value) {
		if (arguments.length === 1) {
			this._lastUpdated = value;
			return value;
		} else {
			return this._lastUpdated;
		}
	}
	,
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this.name(value);
			return value;
		} else {
			return this.name();
		}
	}
	,
	$type: new $.ig.Type('Cube', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Catalog', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	$type: new $.ig.Type('Catalog', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Dimension', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	_dimensionType: 0,
	dimensionType: function (value) {
		if (arguments.length === 1) {
			this._dimensionType = value;
			return value;
		} else {
			return this._dimensionType;
		}
	}
	,
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_defaultHierarchy: null,
	defaultHierarchy: function (value) {
		if (arguments.length === 1) {
			this._defaultHierarchy = value;
			return value;
		} else {
			return this._defaultHierarchy;
		}
	}
	,
	$type: new $.ig.Type('Dimension', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('KpiDimension', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	$type: new $.ig.Type('KpiDimension', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Hierarchy', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
	_defaultHierarchy: null,
	defaultHierarchy: function (value) {
		if (arguments.length === 1) {
			this._defaultHierarchy = value;
			return value;
		} else {
			return this._defaultHierarchy;
		}
	}
	,
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
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_defaultMember: null,
	defaultMember: function (value) {
		if (arguments.length === 1) {
			this._defaultMember = value;
			return value;
		} else {
			return this._defaultMember;
		}
	}
	,
	_allMember: null,
	allMember: function (value) {
		if (arguments.length === 1) {
			this._allMember = value;
			return value;
		} else {
			return this._allMember;
		}
	}
	,
	_dimensionUniqueName: null,
	dimensionUniqueName: function (value) {
		if (arguments.length === 1) {
			this._dimensionUniqueName = value;
			return value;
		} else {
			return this._dimensionUniqueName;
		}
	}
	,
	_hierarchyOrigin: 0,
	hierarchyOrigin: function (value) {
		if (arguments.length === 1) {
			this._hierarchyOrigin = value;
			return value;
		} else {
			return this._hierarchyOrigin;
		}
	}
	,
	_hierarchyDisplayFolder: null,
	hierarchyDisplayFolder: function (value) {
		if (arguments.length === 1) {
			this._hierarchyDisplayFolder = value;
			return value;
		} else {
			return this._hierarchyDisplayFolder;
		}
	}
	,
	$type: new $.ig.Type('Hierarchy', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Kpi', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_measureGroupName: null,
	measureGroupName: function (value) {
		if (arguments.length === 1) {
			this._measureGroupName = value;
			return value;
		} else {
			return this._measureGroupName;
		}
	}
	,
	_kpiDisplayFolder: null,
	kpiDisplayFolder: function (value) {
		if (arguments.length === 1) {
			this._kpiDisplayFolder = value;
			return value;
		} else {
			return this._kpiDisplayFolder;
		}
	}
	,
	_kpiValue: null,
	kpiValue: function (value) {
		if (arguments.length === 1) {
			this._kpiValue = value;
			return value;
		} else {
			return this._kpiValue;
		}
	}
	,
	_kpiGoal: null,
	kpiGoal: function (value) {
		if (arguments.length === 1) {
			this._kpiGoal = value;
			return value;
		} else {
			return this._kpiGoal;
		}
	}
	,
	_kpiStatus: null,
	kpiStatus: function (value) {
		if (arguments.length === 1) {
			this._kpiStatus = value;
			return value;
		} else {
			return this._kpiStatus;
		}
	}
	,
	_kpiTrend: null,
	kpiTrend: function (value) {
		if (arguments.length === 1) {
			this._kpiTrend = value;
			return value;
		} else {
			return this._kpiTrend;
		}
	}
	,
	_kpiStatusGraphic: null,
	kpiStatusGraphic: function (value) {
		if (arguments.length === 1) {
			this._kpiStatusGraphic = value;
			return value;
		} else {
			return this._kpiStatusGraphic;
		}
	}
	,
	_kpiTrendGraphic: null,
	kpiTrendGraphic: function (value) {
		if (arguments.length === 1) {
			this._kpiTrendGraphic = value;
			return value;
		} else {
			return this._kpiTrendGraphic;
		}
	}
	,
	_kpiWeight: null,
	kpiWeight: function (value) {
		if (arguments.length === 1) {
			this._kpiWeight = value;
			return value;
		} else {
			return this._kpiWeight;
		}
	}
	,
	_parentKpiName: null,
	parentKpiName: function (value) {
		if (arguments.length === 1) {
			this._parentKpiName = value;
			return value;
		} else {
			return this._parentKpiName;
		}
	}
	,
	_currentTimeMember: null,
	currentTimeMember: function (value) {
		if (arguments.length === 1) {
			this._currentTimeMember = value;
			return value;
		} else {
			return this._currentTimeMember;
		}
	}
	,
	$type: new $.ig.Type('Kpi', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Level', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_depth: 0,
	depth: function (value) {
		if (arguments.length === 1) {
			this._depth = value;
			return value;
		} else {
			return this._depth;
		}
	}
	,
	_hierarchyUniqueName: null,
	hierarchyUniqueName: function (value) {
		if (arguments.length === 1) {
			this._hierarchyUniqueName = value;
			return value;
		} else {
			return this._hierarchyUniqueName;
		}
	}
	,
	_dimensionUniqueName: null,
	dimensionUniqueName: function (value) {
		if (arguments.length === 1) {
			this._dimensionUniqueName = value;
			return value;
		} else {
			return this._dimensionUniqueName;
		}
	}
	,
	_membersCount: 0,
	membersCount: function (value) {
		if (arguments.length === 1) {
			this._membersCount = value;
			return value;
		} else {
			return this._membersCount;
		}
	}
	,
	_levelOrigin: 0,
	levelOrigin: function (value) {
		if (arguments.length === 1) {
			this._levelOrigin = value;
			return value;
		} else {
			return this._levelOrigin;
		}
	}
	,
	_levelOrderingProperty: null,
	levelOrderingProperty: function (value) {
		if (arguments.length === 1) {
			this._levelOrderingProperty = value;
			return value;
		} else {
			return this._levelOrderingProperty;
		}
	}
	,
	$type: new $.ig.Type('Level', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('Measure', 'Object', {
	init: function () {
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_measureGroupName: null,
	measureGroupName: function (value) {
		if (arguments.length === 1) {
			this._measureGroupName = value;
			return value;
		} else {
			return this._measureGroupName;
		}
	}
	,
	_aggregatorType: 0,
	aggregatorType: function (value) {
		if (arguments.length === 1) {
			this._aggregatorType = value;
			return value;
		} else {
			return this._aggregatorType;
		}
	}
	,
	_defaultFormatString: null,
	defaultFormatString: function (value) {
		if (arguments.length === 1) {
			this._defaultFormatString = value;
			return value;
		} else {
			return this._defaultFormatString;
		}
	}
	,
	_measureDisplayFolder: null,
	measureDisplayFolder: function (value) {
		if (arguments.length === 1) {
			this._measureDisplayFolder = value;
			return value;
		} else {
			return this._measureDisplayFolder;
		}
	}
	,
	$type: new $.ig.Type('Measure', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('KpiMeasure', 'Measure', {
	init: function (uniqueName, caption) {
		$.ig.Measure.prototype.init.call(this);
		this.uniqueName(uniqueName);
		this.caption(caption);
	},
	_graphic: null,
	graphic: function (value) {
		if (arguments.length === 1) {
			this._graphic = value;
			return value;
		} else {
			return this._graphic;
		}
	}
	,
	createKpiValueMeasure: function (kpi) {
		return new $.ig.KpiMeasure(kpi.kpiValue(), kpi.caption() + " Value");
	}
	,
	createKpiGoalMeasure: function (kpi) {
		return new $.ig.KpiMeasure(kpi.kpiGoal(), kpi.caption() + " Goal");
	}
	,
	createKpiWeightMeasure: function (kpi) {
		return new $.ig.KpiMeasure(kpi.kpiWeight(), kpi.caption() + " Weight");
	}
	,
	createKpiStatusMeasure: function (kpi) {
		var measure = new $.ig.KpiMeasure(kpi.kpiStatus(), kpi.caption() + " Status");
		measure.graphic(kpi.kpiStatusGraphic());
		return measure;
	}
	,
	createKpiTrendMeasure: function (kpi) {
		var measure = new $.ig.KpiMeasure(kpi.kpiTrend(), kpi.caption() + " Trend");
		measure.graphic(kpi.kpiTrendGraphic());
		return measure;
	}
	,
	$type: new $.ig.Type('KpiMeasure', $.ig.Measure.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroup', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
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
	_cubeName: null,
	cubeName: function (value) {
		if (arguments.length === 1) {
			this._cubeName = value;
			return value;
		} else {
			return this._cubeName;
		}
	}
	,
	_catalogName: null,
	catalogName: function (value) {
		if (arguments.length === 1) {
			this._catalogName = value;
			return value;
		} else {
			return this._catalogName;
		}
	}
	,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this.name(value);
			return value;
		} else {
			return this.name();
		}
	}
	,
	$type: new $.ig.Type('MeasureGroup', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('MeasureGroupDimension', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_cubeName: null,
	cubeName: function (value) {
		if (arguments.length === 1) {
			this._cubeName = value;
			return value;
		} else {
			return this._cubeName;
		}
	}
	,
	_catalogName: null,
	catalogName: function (value) {
		if (arguments.length === 1) {
			this._catalogName = value;
			return value;
		} else {
			return this._catalogName;
		}
	}
	,
	_measureGroupName: null,
	measureGroupName: function (value) {
		if (arguments.length === 1) {
			this._measureGroupName = value;
			return value;
		} else {
			return this._measureGroupName;
		}
	}
	,
	_dimensionUniqueName: null,
	dimensionUniqueName: function (value) {
		if (arguments.length === 1) {
			this._dimensionUniqueName = value;
			return value;
		} else {
			return this._dimensionUniqueName;
		}
	}
	,
	_measureGroupCardinality: 0,
	measureGroupCardinality: function (value) {
		if (arguments.length === 1) {
			this._measureGroupCardinality = value;
			return value;
		} else {
			return this._measureGroupCardinality;
		}
	}
	,
	_dimensionCardinality: 0,
	dimensionCardinality: function (value) {
		if (arguments.length === 1) {
			this._dimensionCardinality = value;
			return value;
		} else {
			return this._dimensionCardinality;
		}
	}
	,
	_isDimensionVisible: false,
	isDimensionVisible: function (value) {
		if (arguments.length === 1) {
			this._isDimensionVisible = value;
			return value;
		} else {
			return this._isDimensionVisible;
		}
	}
	,
	_isFactDimension: false,
	isFactDimension: function (value) {
		if (arguments.length === 1) {
			this._isFactDimension = value;
			return value;
		} else {
			return this._isFactDimension;
		}
	}
	,
	_dimensionPath: null,
	dimensionPath: function (value) {
		if (arguments.length === 1) {
			this._dimensionPath = value;
			return value;
		} else {
			return this._dimensionPath;
		}
	}
	,
	_dimensionGranularity: null,
	dimensionGranularity: function (value) {
		if (arguments.length === 1) {
			this._dimensionGranularity = value;
			return value;
		} else {
			return this._dimensionGranularity;
		}
	}
	,
	$type: new $.ig.Type('MeasureGroupDimension', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Member', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		var nullValue = $.ig.util.toNullable($.ig.Number.prototype.$type, null);
		this.scope(nullValue);
	},
	_cubeName: null,
	cubeName: function (value) {
		if (arguments.length === 1) {
			this._cubeName = value;
			return value;
		} else {
			return this._cubeName;
		}
	}
	,
	_catalogName: null,
	catalogName: function (value) {
		if (arguments.length === 1) {
			this._catalogName = value;
			return value;
		} else {
			return this._catalogName;
		}
	}
	,
	_dimensionUniqueName: null,
	dimensionUniqueName: function (value) {
		if (arguments.length === 1) {
			this._dimensionUniqueName = value;
			return value;
		} else {
			return this._dimensionUniqueName;
		}
	}
	,
	_hierarchyUniqueName: null,
	hierarchyUniqueName: function (value) {
		if (arguments.length === 1) {
			this._hierarchyUniqueName = value;
			return value;
		} else {
			return this._hierarchyUniqueName;
		}
	}
	,
	_levelUniqueName: null,
	levelUniqueName: function (value) {
		if (arguments.length === 1) {
			this._levelUniqueName = value;
			return value;
		} else {
			return this._levelUniqueName;
		}
	}
	,
	_levelDepth: 0,
	levelDepth: function (value) {
		if (arguments.length === 1) {
			this._levelDepth = value;
			return value;
		} else {
			return this._levelDepth;
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
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
	_memberType: 0,
	memberType: function (value) {
		if (arguments.length === 1) {
			this._memberType = value;
			return value;
		} else {
			return this._memberType;
		}
	}
	,
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
	_childrenCardinality: 0,
	childrenCardinality: function (value) {
		if (arguments.length === 1) {
			this._childrenCardinality = value;
			return value;
		} else {
			return this._childrenCardinality;
		}
	}
	,
	_parentLevel: 0,
	parentLevel: function (value) {
		if (arguments.length === 1) {
			this._parentLevel = value;
			return value;
		} else {
			return this._parentLevel;
		}
	}
	,
	_parentUniqueName: null,
	parentUniqueName: function (value) {
		if (arguments.length === 1) {
			this._parentUniqueName = value;
			return value;
		} else {
			return this._parentUniqueName;
		}
	}
	,
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
	_scope: $.ig.util.toNullable($.ig.Number.prototype.$type, null),
	scope: function (value) {
		if (arguments.length === 1) {
			this._scope = value;
			return value;
		} else {
			return this._scope;
		}
	}
	,
	$type: new $.ig.Type('Member', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('OlapResult', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_isEmpty: false,
	isEmpty: function (value) {
		if (arguments.length === 1) {
			this._isEmpty = value;
			return value;
		} else {
			return this._isEmpty;
		}
	}
	,
	_axes: null,
	axes: function (value) {
		if (arguments.length === 1) {
			this._axes = value;
			return value;
		} else {
			return this._axes;
		}
	}
	,
	_cells: null,
	cells: function (value) {
		if (arguments.length === 1) {
			this._cells = value;
			return value;
		} else {
			return this._cells;
		}
	}
	,
	$type: new $.ig.Type('OlapResult', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapResultAxis', 'Object', {
	init: function (tuples, tupleSize) {
		$.ig.Object.prototype.init.call(this);
		this.tuples($.ig.Enumerable.prototype.toList$1($.ig.OlapResultTuple.prototype.$type, tuples));
		this.tupleSize(tupleSize);
		this.positionResolver(new $.ig.PositionResolver$2(String, $.ig.Object.prototype.$type, tuples, tupleSize));
	},
	_positionResolver: null,
	positionResolver: function (value) {
		if (arguments.length === 1) {
			this._positionResolver = value;
			return value;
		} else {
			return this._positionResolver;
		}
	}
	,
	clone: function () {
		var $self = this;
		var tuples = new $.ig.List$1($.ig.OlapResultTuple.prototype.$type, 0);
		var en = this.tuples().getEnumerator();
		while (en.moveNext()) {
			var tuple = en.current();
			tuples.add(tuple.clone());
		}
		return (function () {
			var $ret = new $.ig.OlapResultAxis(tuples, $self.tupleSize());
			$ret.name($self.name());
			return $ret;
		}());
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
	_tupleSize: 0,
	tupleSize: function (value) {
		if (arguments.length === 1) {
			this._tupleSize = value;
			return value;
		} else {
			return this._tupleSize;
		}
	}
	,
	_tuples: null,
	tuples: function (value) {
		if (arguments.length === 1) {
			this._tuples = value;
			return value;
		} else {
			return this._tuples;
		}
	}
	,
	$type: new $.ig.Type('OlapResultAxis', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IPosition$2', 'Object', {
	$type: new $.ig.Type('IPosition$2', null)
}, true);

$.ig.util.defType('OlapResultTuple', 'Object', {
	init: function (members) {
		$.ig.Object.prototype.init.call(this);
		this.members(new $.ig.ReadOnlyCollection$1($.ig.OlapResultAxisMember.prototype.$type, 1, members));
	},
	_members: null,
	members: function (value) {
		if (arguments.length === 1) {
			this._members = value;
			return value;
		} else {
			return this._members;
		}
	}
	,
	getItems: function () {
		return this.members();
	}
	,
	itemsCount: function () {
		return this.members().count();
	}
	,
	indexOf: function (positionItem) {
		return this.members().indexOf(positionItem);
	}
	,
	item: function (itemIndex) {
		return this.members().item(itemIndex);
	}
	,
	isEmpty: function () {
		return false;
	}
	,
	clone: function () {
		var axisMembers = new $.ig.List$1($.ig.OlapResultAxisMember.prototype.$type, 0);
		var en = this.members().getEnumerator();
		while (en.moveNext()) {
			var axisMember = en.current();
			axisMembers.add(axisMember.clone());
		}
		var tuple = new $.ig.OlapResultTuple(axisMembers);
		var en1 = axisMembers.getEnumerator();
		while (en1.moveNext()) {
			var axisMember1 = en1.current();
			axisMember1.position(tuple);
		}
		return tuple;
	}
	,
	$type: new $.ig.Type('OlapResultTuple', $.ig.Object.prototype.$type, [$.ig.IPosition$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type)])
}, true);

$.ig.util.defType('IPositionItem$2', 'Object', {
	$type: new $.ig.Type('IPositionItem$2', null)
}, true);

$.ig.util.defType('IHierarchicalPositionItem$2', 'Object', {
	$type: new $.ig.Type('IHierarchicalPositionItem$2', null, [$.ig.IPositionItem$2.prototype.$type.specialize(0, 1)])
}, true);

$.ig.util.defType('OlapResultAxisMember', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.properties(new $.ig.Dictionary$2(String, String, 0));
		this.ordinal(-1);
	},
	_uniqueName: null,
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this._uniqueName = value;
			return value;
		} else {
			return this._uniqueName;
		}
	}
	,
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
	_levelUniqueName: null,
	levelUniqueName: function (value) {
		if (arguments.length === 1) {
			this._levelUniqueName = value;
			return value;
		} else {
			return this._levelUniqueName;
		}
	}
	,
	_hierarchyUniqueName: null,
	hierarchyUniqueName: function (value) {
		if (arguments.length === 1) {
			this._hierarchyUniqueName = value;
			return value;
		} else {
			return this._hierarchyUniqueName;
		}
	}
	,
	_levelNumber: 0,
	levelNumber: function (value) {
		if (arguments.length === 1) {
			this._levelNumber = value;
			return value;
		} else {
			return this._levelNumber;
		}
	}
	,
	_displayInfo: 0,
	displayInfo: function (value) {
		if (arguments.length === 1) {
			this._displayInfo = value;
			return value;
		} else {
			return this._displayInfo;
		}
	}
	,
	childCount: function () {
		return (($.ig.util.u32BitwiseAnd(this.displayInfo(), ~65536)) & ~131072);
	}
	,
	drilledDown: function () {
		return (this.displayInfo() & 65536) == 65536;
	}
	,
	parentSameAsPrev: function () {
		return (this.displayInfo() & 131072) == 131072;
	}
	,
	_properties: null,
	properties: function (value) {
		if (arguments.length === 1) {
			this._properties = value;
			return value;
		} else {
			return this._properties;
		}
	}
	,
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
	key: function () {
		return this.uniqueName();
	}
	,
	sortKey: function () {
		if (this.ordinal() > -1) {
			return this.ordinal();
		}
		if (this.caption().contains("/")) {
			return this.caption();
		}
		var decimalValue = parseFloat(this.caption());
		if (!isNaN(decimalValue)) {
			return decimalValue;
		}
		return this.caption();
	}
	,
	_ordinal: 0,
	ordinal: function (value) {
		if (arguments.length === 1) {
			this._ordinal = value;
			return value;
		} else {
			return this._ordinal;
		}
	}
	,
	hierarchyKey: function () {
		return this.hierarchyUniqueName();
	}
	,
	hierarchyLevelKey: function () {
		return this.levelUniqueName();
	}
	,
	parentKey: function () {
		var parentKey;
		var $ret = this.properties().tryGetValue("PARENT_UNIQUE_NAME", parentKey);
		parentKey = $ret.p1;
		return parentKey;
	}
	,
	depth: function () {
		return this.levelNumber();
	}
	,
	isExpandable: function () {
		return this.childCount() > 0;
	}
	,
	clone: function () {
		var axisMember = new $.ig.OlapResultAxisMember();
		axisMember.caption(this.caption());
		axisMember.displayInfo(this.displayInfo());
		axisMember.hierarchyUniqueName(this.hierarchyUniqueName());
		axisMember.levelNumber(this.levelNumber());
		axisMember.levelUniqueName(this.levelUniqueName());
		axisMember.ordinal(this.ordinal());
		var en = this.properties().getEnumerator();
		while (en.moveNext()) {
			var keyValuePair = en.current();
			axisMember.properties().add(keyValuePair.key(), keyValuePair.value());
		}
		axisMember.uniqueName(this.uniqueName());
		axisMember.position(this.position());
		return axisMember;
	}
	,
	$type: new $.ig.Type('OlapResultAxisMember', $.ig.Object.prototype.$type, [$.ig.IHierarchicalPositionItem$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type)])
}, true);

$.ig.util.defType('OlapResultCell', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.properties(new $.ig.Dictionary$2(String, $.ig.Object.prototype.$type, 0));
	},
	_cellOrdinal: 0,
	cellOrdinal: function (value) {
		if (arguments.length === 1) {
			this._cellOrdinal = value;
			return value;
		} else {
			return this._cellOrdinal;
		}
	}
	,
	_properties: null,
	properties: function (value) {
		if (arguments.length === 1) {
			this._properties = value;
			return value;
		} else {
			return this._properties;
		}
	}
	,
	clone: function () {
		var $self = this;
		var cell = (function () {
			var $ret = new $.ig.OlapResultCell();
			$ret.cellOrdinal($self.cellOrdinal());
			return $ret;
		}());
		var en = this.properties().getEnumerator();
		while (en.moveNext()) {
			var keyValuePair = en.current();
			cell.properties().add(keyValuePair.key(), keyValuePair.value());
		}
		return cell;
	}
	,
	$type: new $.ig.Type('OlapResultCell', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IConnection', 'Object', {
	$type: new $.ig.Type('IConnection', null)
}, true);

$.ig.util.defType('IOlapDataProviderFactory', 'Object', {
	$type: new $.ig.Type('IOlapDataProviderFactory', null)
}, true);

$.ig.util.defType('IOlapDiscoverDataProvider', 'Object', {
	$type: new $.ig.Type('IOlapDiscoverDataProvider', null)
}, true);

$.ig.util.defType('IOlapExecuteCommandProvider', 'Object', {
	$type: new $.ig.Type('IOlapExecuteCommandProvider', null)
}, true);

$.ig.util.defType('ArrayListCollection$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.inner(new $.ig.Array());
	},
	_inner: null,
	inner: function (value) {
		if (arguments.length === 1) {
			this._inner = value;
			return value;
		} else {
			return this._inner;
		}
	}
	,
	collectionChanged: null,
	indexOf: function (item) {
		return this.inner().indexOf($.ig.util.getBoxIfEnum(this.$t, item));
	}
	,
	insert: function (index, item) {
		this.inner().insert(index, $.ig.util.getBoxIfEnum(this.$t, item));
		this.triggerItemAdded(index, item);
	}
	,
	removeAt: function (index) {
		var item = $.ig.util.castObjTo$t(this.$t, this.inner()[index]);
		this.inner().removeAt(index);
		this.triggerItemRemoved(index, item);
	}
	,
	item: function (index, value) {
		if (arguments.length === 2) {
			this.inner()[index] = $.ig.util.getBoxIfEnum(this.$t, value);
			return value;
		} else {
			return $.ig.util.castObjTo$t(this.$t, this.inner()[index]);
		}
	}
	,
	add: function (item) {
		this.inner().add($.ig.util.getBoxIfEnum(this.$t, item));
		this.triggerItemAdded(this.inner().length - 1, item);
	}
	,
	clear: function () {
		this.inner().clear();
		this.triggerClear();
	}
	,
	contains: function (item) {
		return this.inner().contains($.ig.util.getBoxIfEnum(this.$t, item));
	}
	,
	copyTo: function (array, arrayIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	count: function () {
		return this.inner().length;
	}
	,
	isReadOnly: function () {
		return false;
	}
	,
	remove: function (item) {
		var index = this.inner().indexOf($.ig.util.getBoxIfEnum(this.$t, item));
		if (index >= 0) {
			this.inner().removeAt(index);
			this.triggerItemRemoved(index, item);
			return true;
		}
		return false;
	}
	,
	enumerate: function () {
		var d__ = new $.ig.ArrayListCollection___Enumerate__IteratorClass$1(this.$t, -2);
		d__.__4__this = this;
		return d__;
	}
	,
	getEnumerator: function () {
		return this.enumerate().getEnumerator();
	}
	,
	triggerItemAdded: function (index, item) {
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.add, $.ig.util.getBoxIfEnum(this.$t, item), index);
			this.collectionChanged(this, args);
		}
	}
	,
	triggerItemRemoved: function (index, item) {
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.remove, $.ig.util.getBoxIfEnum(this.$t, item), index);
			this.collectionChanged(this, args);
		}
	}
	,
	triggerClear: function () {
		if (this.collectionChanged != null) {
			var args = new $.ig.NotifyCollectionChangedEventArgs(1, $.ig.NotifyCollectionChangedAction.prototype.reset, null, -1);
			this.collectionChanged(this, args);
		}
	}
	,
	$type: new $.ig.Type('ArrayListCollection$1', $.ig.Object.prototype.$type, [$.ig.IList$1.prototype.$type.specialize(0), $.ig.INotifyCollectionChanged.prototype.$type])
}, true);

$.ig.util.defType('MeasureList', 'Object', {
	init: function () {
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
	uniqueName: function (value) {
		if (arguments.length === 1) {
			this.name(value);
			return value;
		} else {
			return this.name();
		}
	}
	,
	_measures: null,
	measures: function (value) {
		if (arguments.length === 1) {
			this._measures = value;
			return value;
		} else {
			return this._measures;
		}
	}
	,
	$type: new $.ig.Type('MeasureList', $.ig.Object.prototype.$type, [$.ig.ICoreOlapElement.prototype.$type])
}, true);

$.ig.util.defType('OlapMetadataTreeItem', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_item: null,
	item: function (value) {
		if (arguments.length === 1) {
			this._item = value;
			return value;
		} else {
			return this._item;
		}
	}
	,
	_type: 0,
	type: function (value) {
		if (arguments.length === 1) {
			this._type = value;
			return value;
		} else {
			return this._type;
		}
	}
	,
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
	addChild: function (metaItem) {
		if (this.children() == null) {
			this.children(new Array(1));
			this.children()[0] = metaItem;
			return;
		}
		var children = new Array(this.children().length + 1);
		$.ig.util.arrayCopy1(this.children(), 0, children, 0, this.children().length);
		children[this.children().length] = metaItem;
		this.children(children);
	}
	,
	$type: new $.ig.Type('OlapMetadataTreeItem', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TaskExtensions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	continueWithTask$1: function ($tResult, task, continuationFunction) {
		var tcs = new $.ig.TaskCompletionSource$1($tResult, 0);
		task.continueWith(function (t) {
			if (t.exception() == null) {
				try {
					continuationFunction(t).continueWith1(function (t1) {
						if (t1.exception() == null) {
							tcs.setResult(t1.result());
							return;
						}
						tcs.setException(t1.exception());
					});
				}
				catch (e_) {
					var exception;
					exception = e_;
					console.log(e_);
					tcs.setException(exception);
				}
			} else {
				tcs.setException(t.exception());
			}
		});
		return tcs.task();
	}
	,
	continueWithTask$2: function ($tResult1, $tResult2, task, continuationFunction) {
		return $.ig.TaskExtensions.prototype.continueWithTask$1($tResult2, task, function (t) { return continuationFunction(t); });
	}
	,
	$type: new $.ig.Type('TaskExtensions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KeyValueItem', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_key: null,
	key: function (value) {
		if (arguments.length === 1) {
			this._key = value;
			return value;
		} else {
			return this._key;
		}
	}
	,
	_value: null,
	value: function (value) {
		if (arguments.length === 1) {
			this._value = value;
			return value;
		} else {
			return this._value;
		}
	}
	,
	$type: new $.ig.Type('KeyValueItem', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('AxisElement', 'Object', {
	init: function (name, memberNames) {
		$.ig.Object.prototype.init.call(this);
		this.name(name);
		this.memberNames(memberNames);
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
	_memberNames: null,
	memberNames: function (value) {
		if (arguments.length === 1) {
			this._memberNames = value;
			return value;
		} else {
			return this._memberNames;
		}
	}
	,
	$type: new $.ig.Type('AxisElement', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PositionItemInfo$2', 'Dictionary$2', {
	$tKey: null,
	$tSortKey: null,
	init: function ($tKey, $tSortKey, positionItem, positionIndex, positionItemIndex, positionSize) {
		this.$tKey = $tKey;
		this.$tSortKey = $tSortKey;
		this.$type = this.$type.specialize(this.$tKey, this.$tSortKey);
		$.ig.Dictionary$2.prototype.init.call(this, this.$tKey, $.ig.PositionItemInfo$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), 0);
		this.__parentsToExpand = new $.ig.Dictionary$2(this.$tKey, $.ig.Object.prototype.$type, 0);
		this.positionItem(positionItem);
		this.positionIndex(positionIndex);
		this.positionItemIndex(positionItemIndex);
		this.positionItemDepth(0);
		this.positionItemDepthMin(-1);
		this.positionItemDepthMax(0x7FFFFFFF);
		this.positionSize(positionSize);
		if (positionItem != null) {
			this.positionItemDepth(this.positionItem().depth());
			this.key(this.positionItem().key());
			this.parentKey(this.positionItem().parentKey());
			this.hierarchyKey(this.positionItem().hierarchyKey());
			this.hierarchyLevelKey(this.positionItem().hierarchyLevelKey());
			this.sortKey(this.positionItem().sortKey());
			this.caption(this.positionItem().caption());
		}
	},
	createInstance: function (positionItem, index, memberIndex, positionSize) {
		return new $.ig.PositionItemInfo$2(this.$tKey, this.$tSortKey, positionItem, index, memberIndex, positionSize);
	}
	,
	_positionItemDepthMin: 0,
	positionItemDepthMin: function (value) {
		if (arguments.length === 1) {
			this._positionItemDepthMin = value;
			return value;
		} else {
			return this._positionItemDepthMin;
		}
	}
	,
	_positionItemDepthMax: 0,
	positionItemDepthMax: function (value) {
		if (arguments.length === 1) {
			this._positionItemDepthMax = value;
			return value;
		} else {
			return this._positionItemDepthMax;
		}
	}
	,
	_positionItem: null,
	positionItem: function (value) {
		if (arguments.length === 1) {
			this._positionItem = value;
			return value;
		} else {
			return this._positionItem;
		}
	}
	,
	_positionIndex: 0,
	positionIndex: function (value) {
		if (arguments.length === 1) {
			this._positionIndex = value;
			return value;
		} else {
			return this._positionIndex;
		}
	}
	,
	_positionItemDepth: 0,
	positionItemDepth: function (value) {
		if (arguments.length === 1) {
			this._positionItemDepth = value;
			return value;
		} else {
			return this._positionItemDepth;
		}
	}
	,
	_positionItemIndex: 0,
	positionItemIndex: function (value) {
		if (arguments.length === 1) {
			this._positionItemIndex = value;
			return value;
		} else {
			return this._positionItemIndex;
		}
	}
	,
	_positionSize: 0,
	positionSize: function (value) {
		if (arguments.length === 1) {
			this._positionSize = value;
			return value;
		} else {
			return this._positionSize;
		}
	}
	,
	_key: null,
	key: function (value) {
		if (arguments.length === 1) {
			this._key = value;
			return value;
		} else {
			return this._key;
		}
	}
	,
	_parentKey: null,
	parentKey: function (value) {
		if (arguments.length === 1) {
			this._parentKey = value;
			return value;
		} else {
			return this._parentKey;
		}
	}
	,
	_hierarchyKey: null,
	hierarchyKey: function (value) {
		if (arguments.length === 1) {
			this._hierarchyKey = value;
			return value;
		} else {
			return this._hierarchyKey;
		}
	}
	,
	_hierarchyLevelKey: null,
	hierarchyLevelKey: function (value) {
		if (arguments.length === 1) {
			this._hierarchyLevelKey = value;
			return value;
		} else {
			return this._hierarchyLevelKey;
		}
	}
	,
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
	__sortKey: null,
	sortKey: function (value) {
		if (arguments.length === 1) {
			this.__sortKey = value;
			return value;
		} else {
			return this.__sortKey;
		}
	}
	,
	_isExpandable: false,
	isExpandable: function (value) {
		if (arguments.length === 1) {
			this._isExpandable = value;
			return value;
		} else {
			return this._isExpandable;
		}
	}
	,
	_isExpanded: false,
	isExpanded: function (value) {
		if (arguments.length === 1) {
			this._isExpanded = value;
			return value;
		} else {
			return this._isExpanded;
		}
	}
	,
	addChildObject: function (position, index, lookupPosition) {
		var membersMatch = false;
		if (this.positionItemIndex() == -1) {
			membersMatch = true;
		} else {
			if (lookupPosition != null) {
				if ($.ig.util.getBoxIfEnum(this.$tKey, position.item(this.positionItemIndex()).key()) != null) {
					var lookupPositionItem = lookupPosition.item(this.positionItemIndex());
					membersMatch = position.item(this.positionItemIndex()).key().equals($.ig.util.getBoxIfEnum(this.$tKey, lookupPositionItem.key()));
				}
			}
		}
		if (this.positionItemIndex() + 1 == this.positionSize()) {
			return membersMatch;
		}
		var childMemberMatch = this.addChildObject1(position, index, this.positionItemIndex() + 1, lookupPosition);
		return membersMatch && childMemberMatch;
	}
	,
	onChildAdded: function (position, childItemInfo) {
	}
	,
	__parentsToExpand: null,
	addChildObject1: function (position, index, positionItemIndex, lookupPosition) {
		var $self = this;
		var membersMatch;
		var positionItem = position.item(positionItemIndex);
		if (!this.containsKey(positionItem.key())) {
			var linkedNode = this.createInstance(positionItem, index, positionItemIndex, this.positionSize());
			linkedNode.isExpandable(positionItem.isExpandable());
			if (this.__parentsToExpand.containsKey(linkedNode.key())) {
				this.__parentsToExpand.remove(linkedNode.key());
				linkedNode.isExpanded(true);
			}
			if ($.ig.util.getBoxIfEnum(this.$tKey, positionItem.parentKey()) != null) {
				var parentPositionInfo = null;
				if ((function () { var $ret = $self.tryGetValue(positionItem.parentKey(), parentPositionInfo); parentPositionInfo = $ret.p1; return $ret.ret; }())) {
					parentPositionInfo.isExpanded(true);
				} else {
					if (!this.__parentsToExpand.containsKey(positionItem.parentKey())) {
						this.__parentsToExpand.add(positionItem.parentKey(), null);
					}
				}
			}
			membersMatch = linkedNode.addChildObject(position, index, lookupPosition);
			this.add(positionItem.key(), linkedNode);
			if (this.positionItemDepthMin() == -1) {
				this.positionItemDepthMin(positionItem.depth());
			} else {
				this.positionItemDepthMin(Math.min(this.positionItemDepthMin(), positionItem.depth()));
			}
			if (this.positionItemDepthMax() == 0x7FFFFFFF) {
				this.positionItemDepthMax(positionItem.depth());
			} else {
				this.positionItemDepthMax(Math.max(this.positionItemDepthMax(), positionItem.depth()));
			}
			this.onChildAdded(position, linkedNode);
		} else {
			var positionItemInfo = this.item(positionItem.key());
			membersMatch = positionItemInfo.addChildObject(position, index, lookupPosition);
		}
		return membersMatch;
	}
	,
	getIndex2: function (positionItems) {
		var keys = new Array(positionItems.count());
		for (var i = 0; i < keys.length; i++) {
			keys[i] = positionItems.item(i).key();
		}
		return this.getIndex(keys);
	}
	,
	getIndex: function (keys) {
		return this.getIndex1(keys, keys.length);
	}
	,
	getIndex1: function (keys, itemsCount) {
		var $self = this;
		if (this.positionItemIndex() == itemsCount - 1) {
			return this.positionIndex();
		}
		var positionItemInfo;
		if (!(function () { var $ret = $self.tryGetValue(keys[$self.positionItemIndex() + 1], positionItemInfo); positionItemInfo = $ret.p1; return $ret.ret; }())) {
			return -1;
		}
		if (positionItemInfo.count() == 0) {
			return positionItemInfo.positionIndex();
		}
		return positionItemInfo.getIndex1(keys, itemsCount);
	}
	,
	$type: new $.ig.Type('PositionItemInfo$2', $.ig.Dictionary$2.prototype.$type.specialize(0, -1))
}, true);

$.ig.PositionItemInfo$2.prototype.$type.initSelfReferences();

$.ig.util.defType('PositionResolver$2', 'Object', {
	$tKey: null,
	$tSortKey: null,
	__registeredPositionsCount: 0,
	__rootPositionInfo: null,
	__rootTupleInfoKey: null,
	__rootTupleInfoParentKey: null,
	init: function ($tKey, $tSortKey, positions, positionSize) {
		this.$tKey = $tKey;
		this.$tSortKey = $tSortKey;
		this.$type = this.$type.specialize(this.$tKey, this.$tSortKey);
		this.__registeredPositionsCount = 0;
		$.ig.Object.prototype.init.call(this);
		this.positions($.ig.Enumerable.prototype.toList$1($.ig.IPosition$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), positions));
		this.positionSize(positionSize);
	},
	getPositionIndexCompleted: null,
	_positions: null,
	positions: function (value) {
		if (arguments.length === 1) {
			this._positions = value;
			return value;
		} else {
			return this._positions;
		}
	}
	,
	_positionSize: 0,
	positionSize: function (value) {
		if (arguments.length === 1) {
			this._positionSize = value;
			return value;
		} else {
			return this._positionSize;
		}
	}
	,
	rootPositionInfo: function () {
		if (this.__rootPositionInfo == null) {
			this.__rootPositionInfo = this.resolverRootPositionInfo(this.positionSize());
		}
		return this.__rootPositionInfo;
	}
	,
	resolverRootPositionInfo: function (positionSize) {
		return new $.ig.PositionItemInfo$2(this.$tKey, this.$tSortKey, null, -1, -1, positionSize);
	}
	,
	hasUnregisterdPositions: function () {
		return this.__registeredPositionsCount < this.positions().count();
	}
	,
	completeRegisterPositions: function () {
		if (this.hasUnregisterdPositions()) {
			this.registerAndGetPositionIndex(null);
		}
	}
	,
	resolveParentPositionItem: function (positionItem, parentPositionIndex) {
		parentPositionIndex = -1;
		var hierarchicalPosition = $.ig.util.cast($.ig.IHierarchicalPositionItem$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), positionItem);
		if (hierarchicalPosition == null || $.ig.util.getBoxIfEnum(this.$tKey, hierarchicalPosition.parentKey()) == null) {
			return {
				ret: null,
				p1: parentPositionIndex
			};
		}
		var memberIndex = hierarchicalPosition.position().indexOf(hierarchicalPosition);
		var keys = new Array(hierarchicalPosition.position().itemsCount());
		for (var i = 0; i < hierarchicalPosition.position().itemsCount(); i++) {
			keys[i] = hierarchicalPosition.position().item(i).key();
		}
		keys[memberIndex] = hierarchicalPosition.parentKey();
		parentPositionIndex = this.rootPositionInfo().getIndex(keys);
		if (parentPositionIndex == -1) {
			return {
				ret: null,
				p1: parentPositionIndex
			};
		}
		var parentPosition = this.positions().__inner[parentPositionIndex];
		var parentMember = parentPosition.item(memberIndex);
		return {
			ret: parentMember,
			p1: parentPositionIndex
		};
	}
	,
	getHostPositionItemInfo: function (position, positionItemIndex) {
		var indexInfo = this.rootPositionInfo();
		var positionItem = position.item(positionItemIndex);
		for (var i = 0; i < positionItemIndex; i++) {
			var nextPositionItemInfo;
			if ((function () { var $ret = indexInfo.tryGetValue(position.item(i).key(), nextPositionItemInfo); nextPositionItemInfo = $ret.p1; return $ret.ret; }())) {
				indexInfo = nextPositionItemInfo;
			} else {
				indexInfo = null;
				break;
			}
		}
		if (indexInfo == null) {
			return null;
		}
		if (!indexInfo.containsKey(positionItem.key())) {
			return null;
		}
		return indexInfo;
	}
	,
	getChildPositionsIndexes: function (positionItem, sortDirection) {
		if (this.rootPositionInfo().count() == 0) {
			return null;
		}
		var indexInfo = this.rootPositionInfo();
		var index = positionItem.position().indexOf(positionItem);
		for (var i = 0; i < index; i++) {
			var nextPositionItemInfo;
			if ((function () { var $ret = indexInfo.tryGetValue(positionItem.position().item(i).key(), nextPositionItemInfo); nextPositionItemInfo = $ret.p1; return $ret.ret; }())) {
				indexInfo = nextPositionItemInfo;
			} else {
				indexInfo = null;
				break;
			}
		}
		if (indexInfo == null) {
			return null;
		}
		if (!indexInfo.containsKey(positionItem.key())) {
			return null;
		}
		var resultList = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		this.getChildPositionsIndexes1(positionItem.key(), indexInfo, resultList);
		if (sortDirection == $.ig.ListSortDirection.prototype.descending) {
			resultList.sort2(function (x, y) {
				if (x == y) {
					return 0;
				}
				if (x > y) {
					return -1;
				}
				return 1;
			});
			return resultList;
		}
		resultList.sort2(function (x, y) {
			if (x == y) {
				return 0;
			}
			if (x > y) {
				return 1;
			}
			return -1;
		});
		return resultList;
	}
	,
	getChildPositionsIndexes1: function (parentUniqueName, indexInfo, resultList) {
		var $self = this;
		var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), indexInfo.values(), function (tii) { return parentUniqueName.equals($.ig.util.getBoxIfEnum($self.$tKey, tii.parentKey())); }).getEnumerator();
		while (en.moveNext()) {
			var positionInfo = en.current();
			if (positionInfo.count() == 0) {
				resultList.add(positionInfo.positionIndex());
			} else {
				this.fillMemberTuplesIndexes(positionInfo, resultList);
			}
			if (!positionInfo.isExpanded()) {
				continue;
			}
			if (positionInfo.positionItemDepth() < indexInfo.positionItemDepthMax()) {
				this.getChildPositionsIndexes1(positionInfo.key(), indexInfo, resultList);
			}
		}
	}
	,
	fillMemberTuplesIndexes: function (indexInfo, resultList) {
		var $self = this;
		var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), indexInfo.values(), function (tii) { return $.ig.util.getBoxIfEnum($self.$tKey, tii.parentKey()) == null; }).getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			if (positionItemInfo.count() == 0) {
				resultList.add(positionItemInfo.positionIndex());
			} else {
				this.fillMemberTuplesIndexes(positionItemInfo, resultList);
			}
			if (!positionItemInfo.isExpanded()) {
				continue;
			}
			if (positionItemInfo.positionItemDepth() < indexInfo.positionItemDepthMax()) {
				this.getChildPositionsIndexes1(positionItemInfo.key(), indexInfo, resultList);
			}
		}
	}
	,
	getChildren: function (position, positionItemIndex) {
		var $self = this;
		if (this.hasUnregisterdPositions()) {
			this.completeRegisterPositions();
		}
		var indexInfo = this.getHostPositionItemInfo(position, positionItemIndex);
		var positionItem = position.item(positionItemIndex);
		return $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), indexInfo.values(), function (pi) { return positionItem.key().equals($.ig.util.getBoxIfEnum($self.$tKey, pi.parentKey())); });
	}
	,
	isMemberExpanded: function (positionItem) {
		if (this.hasUnregisterdPositions()) {
			this.completeRegisterPositions();
		}
		var positionIndex = this.getPositionIndex(positionItem.position());
		if (positionIndex == -1 || this.rootPositionInfo().count() == 0) {
			return $.ig.util.toNullable($.ig.Boolean.prototype.$type, null);
		}
		var index = positionItem.position().indexOf(positionItem);
		var indexInfo = this.rootPositionInfo();
		for (var i = 0; i < index; i++) {
			var nextPositionItemInfo;
			if ((function () { var $ret = indexInfo.tryGetValue(positionItem.position().item(i).key(), nextPositionItemInfo); nextPositionItemInfo = $ret.p1; return $ret.ret; }())) {
				indexInfo = nextPositionItemInfo;
			} else {
				indexInfo = null;
				break;
			}
		}
		if (indexInfo == null) {
			return $.ig.util.toNullable($.ig.Boolean.prototype.$type, null);
		}
		if (!indexInfo.containsKey(positionItem.key())) {
			return $.ig.util.toNullable($.ig.Boolean.prototype.$type, null);
		}
		var childMember = null;
		var e = indexInfo.values().getEnumerator();
		while (e.moveNext()) {
			if (positionItem.key().equals($.ig.util.getBoxIfEnum(this.$tKey, e.current().parentKey()))) {
				childMember = e.current();
				break;
			}
		}
		return $.ig.util.toNullable($.ig.Boolean.prototype.$type, childMember != null);
	}
	,
	getPositionIndex: function (position) {
		if (position == null) {
			return -1;
		}
		if (position.itemsCount() != this.positionSize()) {
			return -1;
		}
		if (position.isEmpty() && this.positions().count() == 1 && this.positions().__inner[0].isEmpty()) {
			return 0;
		}
		if (this.__registeredPositionsCount > 0) {
			var positionIndex = this.getPositionIndexImpl(position);
			if (positionIndex > -1) {
				return positionIndex;
			}
			if (this.__registeredPositionsCount == this.positions().count()) {
				return -1;
			}
		}
		return this.registerAndGetPositionIndex(position);
	}
	,
	getPositionIndexImpl: function (position) {
		if (position.isEmpty() && this.positions().count() == 1 && this.positions().__inner[0].isEmpty()) {
			return 0;
		}
		return this.rootPositionInfo().getIndex2($.ig.Enumerable.prototype.toList$1($.ig.IPositionItem$2.prototype.$type.specialize(this.$tKey, this.$tSortKey), position.getItems()));
	}
	,
	registerAndGetPositionIndex: function (lookupPosition) {
		var registerAll = lookupPosition == null;
		var positionIndex = -1;
		for (var i = this.__registeredPositionsCount; i < this.positions().count(); i++) {
			var position = this.positions().__inner[i];
			var positionFound = this.rootPositionInfo().addChildObject(position, i, lookupPosition);
			this.__registeredPositionsCount++;
			if (positionIndex == -1 && positionFound) {
				positionIndex = i;
				if (!registerAll) {
					break;
				}
			}
		}
		return positionIndex;
	}
	,
	$type: new $.ig.Type('PositionResolver$2', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ArrayListCollection___Enumerate__IteratorClass$1', 'Object', {
	$t: null,
	__1__state: 0,
	__2__current: null,
	__i_5_0: 0,
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
					this.__i_5_0 = 0;
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_0 < this.__4__this.inner().length) {
						this.__2__current = $.ig.util.castObjTo$t(this.$t, this.__4__this.inner()[this.__i_5_0]);
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
			d__ = new $.ig.ArrayListCollection___Enumerate__IteratorClass$1(this.$t, 0);
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
		return $.ig.util.getBoxIfEnum(this.$t, this.__2__current);
	}
	,
	$type: new $.ig.Type('ArrayListCollection___Enumerate__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(0), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.OlapMetadataTreeItemType.prototype.cube = 0;
$.ig.OlapMetadataTreeItemType.prototype.dimension = 1;
$.ig.OlapMetadataTreeItemType.prototype.group = 2;
$.ig.OlapMetadataTreeItemType.prototype.userDefinedHierarchy = 3;
$.ig.OlapMetadataTreeItemType.prototype.systemEnabledHierarchy = 4;
$.ig.OlapMetadataTreeItemType.prototype.parentChildHierarchy = 5;
$.ig.OlapMetadataTreeItemType.prototype.measure = 6;
$.ig.OlapMetadataTreeItemType.prototype.level1 = 7;
$.ig.OlapMetadataTreeItemType.prototype.level2 = 8;
$.ig.OlapMetadataTreeItemType.prototype.level3 = 9;
$.ig.OlapMetadataTreeItemType.prototype.level4 = 10;
$.ig.OlapMetadataTreeItemType.prototype.level5 = 11;
$.ig.OlapMetadataTreeItemType.prototype.kpiRoot = 12;
$.ig.OlapMetadataTreeItemType.prototype.kpi = 13;
$.ig.OlapMetadataTreeItemType.prototype.kpiValue = 14;
$.ig.OlapMetadataTreeItemType.prototype.kpiGoal = 15;
$.ig.OlapMetadataTreeItemType.prototype.kpiStatus = 16;
$.ig.OlapMetadataTreeItemType.prototype.kpiTrend = 17;
$.ig.OlapMetadataTreeItemType.prototype.kpiWeight = 18;

$.ig.MeasureListLocation.prototype.rows = 0;
$.ig.MeasureListLocation.prototype.columns = 1;

$.ig.MemberType.prototype.unknown = 0;
$.ig.MemberType.prototype.regular = 1;
$.ig.MemberType.prototype.all = 2;
$.ig.MemberType.prototype.measure = 3;
$.ig.MemberType.prototype.formula = 4;

$.ig.CardinalityType.prototype.one = 0;
$.ig.CardinalityType.prototype.many = 1;

$.ig.AggregatorType.prototype.unknown = 0;
$.ig.AggregatorType.prototype.sum = 1;
$.ig.AggregatorType.prototype.count = 2;
$.ig.AggregatorType.prototype.min = 3;
$.ig.AggregatorType.prototype.max = 4;
$.ig.AggregatorType.prototype.average = 5;
$.ig.AggregatorType.prototype.variance = 6;
$.ig.AggregatorType.prototype.std = 7;
$.ig.AggregatorType.prototype.distinctCount = 8;
$.ig.AggregatorType.prototype.none = 9;
$.ig.AggregatorType.prototype.averageOfChildren = 10;
$.ig.AggregatorType.prototype.firstNonEmpty = 13;
$.ig.AggregatorType.prototype.lastNonEmpty = 14;
$.ig.AggregatorType.prototype.byAccount = 15;
$.ig.AggregatorType.prototype.calculated = 127;

$.ig.HierarchyOrigin.prototype.userDefined = 1;
$.ig.HierarchyOrigin.prototype.systemEnabled = 2;
$.ig.HierarchyOrigin.prototype.systemInternal = 4;

$.ig.DimensionType.prototype.unknown = 0;
$.ig.DimensionType.prototype.time = 1;
$.ig.DimensionType.prototype.measure = 2;
$.ig.DimensionType.prototype.other = 3;
$.ig.DimensionType.prototype.quantitative = 5;
$.ig.DimensionType.prototype.accounts = 6;
$.ig.DimensionType.prototype.customers = 7;
$.ig.DimensionType.prototype.products = 8;
$.ig.DimensionType.prototype.scenario = 9;
$.ig.DimensionType.prototype.utility = 10;
$.ig.DimensionType.prototype.currency = 11;
$.ig.DimensionType.prototype.rates = 12;
$.ig.DimensionType.prototype.channel = 13;
$.ig.DimensionType.prototype.promotion = 14;
$.ig.DimensionType.prototype.organization = 15;
$.ig.DimensionType.prototype.billOfMaterials = 16;
$.ig.DimensionType.prototype.geography = 17;

$.ig.CubeType.prototype.cube = 0;
$.ig.CubeType.prototype.dimension = 1;
$.ig.CubeType.prototype.unknown = 2;

$.ig.LevelSortBehavior.prototype.alphabetical = 0;
$.ig.LevelSortBehavior.prototype.system = 1;

$.ig.RowHeaderLayout.prototype.standard = 0;
$.ig.RowHeaderLayout.prototype.superCompact = 1;
$.ig.RowHeaderLayout.prototype.tree = 2;

$.ig.HeaderCellsLayoutOrientation.prototype.horizontal = 0;
$.ig.HeaderCellsLayoutOrientation.prototype.vertical = 1;

$.ig.AxisDefinitionParser.prototype._elementBegin = "[";
$.ig.AxisDefinitionParser.prototype._elementEnd = "]";
$.ig.AxisDefinitionParser.prototype._filterGroupBegin = "{";
$.ig.AxisDefinitionParser.prototype._filterGroupEnd = "}";
$.ig.AxisDefinitionParser.prototype._elementSeparator = ",";

$.ig.CoreOlapElementParser.prototype._elementBegin = "[";
$.ig.CoreOlapElementParser.prototype._elementEnd = "]";
$.ig.CoreOlapElementParser.prototype._elementSeparator = ".";
$.ig.CoreOlapElementParser.prototype._memberNameStart = "&";

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["IXmlaDataProvider:a", 
"IOlapDiscoverDataProvider:b", 
"Task$1:c", 
"Task:d", 
"Object:e", 
"Type:f", 
"Boolean:g", 
"ValueType:h", 
"Void:i", 
"IConvertible:j", 
"IFormatProvider:k", 
"Number:l", 
"String:m", 
"IComparable:n", 
"Number:o", 
"IComparable$1:p", 
"IEquatable$1:q", 
"Number:r", 
"Number:s", 
"Number:t", 
"NumberStyles:u", 
"Enum:v", 
"Array:w", 
"IList:x", 
"ICollection:y", 
"IEnumerable:z", 
"IEnumerator:aa", 
"NotSupportedException:ab", 
"Error:ac", 
"Number:ad", 
"String:ae", 
"StringComparison:af", 
"RegExp:ag", 
"CultureInfo:ah", 
"DateTimeFormatInfo:ai", 
"Calendar:aj", 
"Date:ak", 
"Number:al", 
"DayOfWeek:am", 
"DateTimeKind:an", 
"CalendarWeekRule:ao", 
"NumberFormatInfo:ap", 
"CompareInfo:aq", 
"CompareOptions:ar", 
"IEnumerable$1:as", 
"IEnumerator$1:at", 
"IDisposable:au", 
"StringSplitOptions:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Number:az", 
"Number:a0", 
"Number:a1", 
"Assembly:a2", 
"Stream:a3", 
"SeekOrigin:a4", 
"RuntimeTypeHandle:a5", 
"MethodInfo:a6", 
"MethodBase:a7", 
"MemberInfo:a8", 
"ParameterInfo:a9", 
"TypeCode:ba", 
"ConstructorInfo:bb", 
"PropertyInfo:bc", 
"JQueryPromise:bd", 
"Action:be", 
"MulticastDelegate:bf", 
"IntPtr:bg", 
"Action$1:bh", 
"AggregateException:bi", 
"TaskStatus:bj", 
"Func$2:bk", 
"TaskCompletionSource$1:bl", 
"JQueryDeferred:bm", 
"JQuery:bn", 
"JQueryObject:bo", 
"Element:bp", 
"ElementAttributeCollection:bq", 
"ElementCollection:br", 
"WebStyle:bs", 
"ElementNodeType:bt", 
"Document:bu", 
"EventListener:bv", 
"IElementEventHandler:bw", 
"ElementEventHandler:bx", 
"ElementAttribute:by", 
"JQueryPosition:bz", 
"JQueryCallback:b0", 
"JQueryEvent:b1", 
"JQueryUICallback:b2", 
"Script:b3", 
"Catalog:b4", 
"ICoreOlapElement:b5", 
"KeyValueItem:b6", 
"Cube:b7", 
"CubeType:b8", 
"Dimension:b9", 
"DimensionType:ca", 
"Hierarchy:cb", 
"HierarchyOrigin:cc", 
"Level:cd", 
"Measure:ce", 
"AggregatorType:cf", 
"MeasureGroup:cg", 
"MeasureGroupDimension:ch", 
"CardinalityType:ci", 
"Member:cj", 
"MemberType:ck", 
"Nullable$1:cl", 
"Kpi:cm", 
"IOlapExecuteCommandProvider:cn", 
"OlapResult:co", 
"IList$1:cp", 
"ICollection$1:cq", 
"OlapResultAxis:cr", 
"PositionResolver$2:cs", 
"List$1:ct", 
"IArray:cu", 
"IArrayList:cv", 
"Array:cw", 
"CompareCallback:cx", 
"Func$3:cy", 
"Comparer$1:cz", 
"IComparer:c0", 
"IComparer$1:c1", 
"DefaultComparer$1:c2", 
"Comparison$1:c3", 
"ReadOnlyCollection$1:c4", 
"Predicate$1:c5", 
"NotImplementedException:c6", 
"IPosition$2:c7", 
"IPositionItem$2:c8", 
"PositionItemInfo$2:c9", 
"Dictionary$2:da", 
"IDictionary$2:db", 
"IDictionary:dc", 
"KeyValuePair$2:dd", 
"Enumerable:de", 
"Thread:df", 
"ThreadStart:dg", 
"IOrderedEnumerable$1:dh", 
"SortedList$1:di", 
"Math:dj", 
"ArgumentNullException:dk", 
"IEqualityComparer$1:dl", 
"EqualityComparer$1:dm", 
"IEqualityComparer:dn", 
"DefaultEqualityComparer$1:dp", 
"InvalidOperationException:dq", 
"ArgumentException:dr", 
"IHierarchicalPositionItem$2:ds", 
"EventHandler$1:dt", 
"AsyncCompletedEventArgs:du", 
"EventArgs:dv", 
"Delegate:dw", 
"Interlocked:dx", 
"ListSortDirection:dy", 
"OlapResultTuple:dz", 
"OlapResultAxisMember:d0", 
"OlapResultCell:d1", 
"MdxDataSourceImpl:d2", 
"OlapDataSource:d3", 
"IOlapDataSource:d4", 
"OlapMetadataTreeItem:d5", 
"OlapMetadataTreeItemType:d6", 
"MeasureListLocation:d7", 
"ArrayListCollection$1:d8", 
"INotifyCollectionChanged:d9", 
"NotifyCollectionChangedEventHandler:ea", 
"NotifyCollectionChangedEventArgs:eb", 
"NotifyCollectionChangedAction:ec", 
"DataSourceBaseOptions:ed", 
"IOlapDataProviderFactory:ee", 
"OlapResultView:ef", 
"CubeMetaItemsCache:eg", 
"KpiMeasure:eh", 
"MeasureGroupMetaItemsCache:ei", 
"Tuple$2:ej", 
"AxisElement:ek", 
"PositionInfo:el", 
"MeasureList:em", 
"TaskFactory:en", 
"TaskExtensions:eo", 
"AxisDefinitionParser:ep", 
"Debug:eq", 
"MetaTreeHelper:er", 
"HierarchyItemPosition:es", 
"KpiDimension:et", 
"MdxDataSourceOptions:eu", 
"MdxSettings:ev", 
"MdxDimensionAxisSettings:ew", 
"MdxAxisSettings:ex", 
"MdxSetSettings:ey", 
"MdxSlicerAxisSettings:ez", 
"ObservableCollection$1:e0", 
"INotifyPropertyChanged:e1", 
"PropertyChangedEventHandler:e2", 
"PropertyChangedEventArgs:e3", 
"MdxDimensionAxisProvider:e4", 
"IMdxDimensionAxisProvider:e5", 
"IMdxAxisProvider:e6", 
"IMdxAxis:e7", 
"IMdxExpression:e8", 
"IMdxSet:e9", 
"IMdxElement:fa", 
"MdxElementType:fb", 
"MdxElementCollectionElement:fc", 
"IMdxCollectionElement:fd", 
"StringBuilder:fe", 
"Environment:ff", 
"MdxAxis:fg", 
"MdxSet:fh", 
"MdxSingleElement:fi", 
"MdxElement:fj", 
"IMdxItemElement:fk", 
"MdxLevelMembersElement:fl", 
"MdxChildrenCollectionElement:fm", 
"MdxDimensionMember:fn", 
"MdxSlicerAxisProvider:fo", 
"MdxSlicerAxis:fp", 
"IMdxSlicerAxis:fq", 
"IMdxExtendedFilterInfo:fr", 
"IMdxFilterInfo:fs", 
"IAxisFilterElement:ft", 
"MdxSlicerAxisBuilder:fu", 
"MdxAxisFilterElement:fv", 
"MdxSlicerSet:fw", 
"ResultViewHelper:fx", 
"MdxQueryBuilder:fy", 
"MdxCalculatedMembersCache:fz", 
"XmlaDataProvider:f0", 
"ICoreXmlaConnection:f1", 
"IConnection:f2", 
"XmlaSoapWebClient$1:f3", 
"XmlaSoapWebClient:f4", 
"ICredentials:f5", 
"NetworkCredential:f6", 
"Uri:f7", 
"UriKind:f8", 
"Encoding:f9", 
"UTF8Encoding:ga", 
"Decoder:gb", 
"UnicodeEncoding:gc", 
"AsciiEncoding:gd", 
"DefaultDecoder:ge", 
"WebClient:gf", 
"WebHeaderCollection:gg", 
"NameValueCollection:gh", 
"UploadStringCompletedEventHandler:gi", 
"UploadStringCompletedEventArgs:gj", 
"UploadDataCompletedEventHandler:gk", 
"UploadDataCompletedEventArgs:gl", 
"RequestCompletedEventArgs$1:gm", 
"IXmlaSoapMethod:gn", 
"XElement:go", 
"XContainer:gp", 
"XNode:gq", 
"XObject:gr", 
"XmlNode:gs", 
"XmlNodeList:gt", 
"XmlNamedNodeMap:gu", 
"XmlNodeType:gv", 
"XmlDocument:gw", 
"XmlElement:gx", 
"XmlLinkedNode:gy", 
"XmlAttribute:gz", 
"XmlUtils:g0", 
"XName:g1", 
"XNamespace:g2", 
"XDocument:g3", 
"XAttribute:g4", 
"XmlaPropertiesCollection:g5", 
"XmlaQueryProperty:g6", 
"XmlaSoapMessageBuilder:g7", 
"XmlaSoapFaultXmlTypeSerializer:g8", 
"IXmlTypeSerializer$1:g9", 
"XmlaSoapFault:ha", 
"FaultDetail:hb", 
"FaultError:hc", 
"SerializerUtils:hd", 
"ErrorXmlTypeSerializer:he", 
"FaultReason:hf", 
"FaultCode:hg", 
"FaultException$1:hh", 
"FaultException:hi", 
"DiscoverResponseResult:hj", 
"XmlaSoapMethodResult:hk", 
"IXmlaMethodResult:hl", 
"ExecuteResponseResult:hm", 
"DatabaseXmlTypeSerializer:hn", 
"CubeXmlTypeSerializer:ho", 
"DimensionXmlTypeSerializer:hp", 
"HierarchyXmlTypeSerializer:hq", 
"LevelXmlTypeSerializer:hr", 
"MeasureXmlTypeSerializer:hs", 
"MeasureGroupXmlTypeSerializer:ht", 
"MeasureGroupDimensionXmlTypeSerializer:hu", 
"MemberXmlTypeSerializer:hv", 
"KpiXmlTypeSerializer:hw", 
"ResultXmlTypeSerializer:hx", 
"ArraySerializer:hy", 
"AxisXmlTypeSerializer:hz", 
"TupleXmlTypeSerializer:h0", 
"AxisMemberXmlTypeSerializer:h1", 
"CellXmlTypeSerializer:h2", 
"XmlaSoapMethodExecute:h3", 
"XmlaSoapMethod:h4", 
"XmlaSoapMethodDiscover:h5", 
"XmlaSoapMessageHeader:h6", 
"CatalogConstants:h7", 
"DbSchemaConstants:ib", 
"CubeConstants:ih", 
"DimensionConstants:ii", 
"HierarchyConstants:ij", 
"InstanceConstants:ik", 
"KpiConstants:il", 
"LevelConstants:im", 
"MeasureConstants:io", 
"MeasureGroupConstants:ip", 
"MeasureGroupDimensionConstants:iq", 
"MemberConstants:ir", 
"OlapSchemaConstants:is", 
"CubeRestrictions:it", 
"DimensionRestrictions:iu", 
"HierarchyRestrictions:iv", 
"KpiRestrictions:iw", 
"LevelRestrictions:ix", 
"MeasureRestrictions:iy", 
"MeasureGroupRestrictions:iz", 
"MeasureGroupDimensionRestrictions:i0", 
"MemberRestrictions:i1", 
"XmlaConstants:i2", 
"XmlaNamespace:i3", 
"IXmlaDataProviderFactory:i9", 
"DefaultXmlaDataProviderFactory:ja", 
"XmlaConnection:jb", 
"RemoteXmlaDataProviderFactory:jc", 
"JsonRemoteRequestSerializer:jd", 
"IRemoteRequestSerializer:je", 
"RemoteRequest:jf", 
"RemoteRequestType:jg", 
"JavaScriptSerializer:jh", 
"RemoteRequestJsonTypeSeriazlier:ji", 
"JsonTypeSeriazlierBase$1:jj", 
"IJsonTypeSerializer$1:jk", 
"JsonSerializerUtils:jl", 
"jQueryUtils:jm", 
"JsonRemoteResponseSerializer:jn", 
"IRemoteResponseSerializer:jo", 
"ArrayJsonSerializer:jp", 
"CatalogJsonTypeSeriazlier:jq", 
"CubeJsonTypeSeriazlier:jr", 
"DimensionJsonTypeSeriazlier:js", 
"HierarchyJsonTypeSeriazlier:jt", 
"LevelJsonTypeSeriazlier:ju", 
"MeasureJsonTypeSeriazlier:jv", 
"MeasureGroupJsonTypeSeriazlier:jw", 
"MeasureGroupDimensionJsonTypeSeriazlier:jx", 
"MemberJsonTypeSeriazlier:jy", 
"KpiJsonTypeSeriazlier:jz", 
"ResultJsonTypeSeriazlier:j0", 
"AxisJsonTypeSeriazlier:j1", 
"TupleJsonTypeSeriazlier:j2", 
"AxisMemberJsonTypeSeriazlier:j3", 
"CellJsonTypeSeriazlier:j4", 
"RemoteXmlaDataProvder:j5", 
"XmlaDataSourceImpl:j6", 
"XmlaDataSourceOptions:j7", 
"RequestOptions:j8", 
"XmlaDiscoverProperties:j9", 
"CustomRemoteXmlaRequestSerializer:kb", 
"CustomRemoteXmlaResponseSerializer:kc", 
"OlapXmlaDataSource:kd", 
"DataSourceBase:ke", 
"AbstractEnumerable:kf", 
"Func$1:kg", 
"AbstractEnumerator:kh", 
"GenericEnumerable$1:ki", 
"GenericEnumerator$1:kj"]);


$.ig.util.defType('RemoteRequestType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "DiscoverCatalogs";
			case 1: return "DiscoverCubes";
			case 2: return "DiscoverDimensions";
			case 3: return "DiscoverHierarchies";
			case 4: return "DiscoverLevels";
			case 5: return "DiscoverMeasures";
			case 6: return "DiscoverMeasureGroups";
			case 7: return "DiscoverMeasureGroupDimensions";
			case 8: return "DiscoverMembers";
			case 9: return "DiscoverKpis";
			case 10: return "ExecuteStatement";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('RemoteRequestType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('MdxElementType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Member";
			case 1: return "MemberTree";
			case 2: return "Calculated";
			case 3: return "Filter";
			case 4: return "Collection";
			case 5: return "UserDefined";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('MdxElementType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapMessageHeader', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Session";
			case 2: return "BeginSession";
			case 3: return "EndSession";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('XmlaSoapMessageHeader', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('IXmlaDataProvider', 'Object', {
	$type: new $.ig.Type('IXmlaDataProvider', null, [$.ig.IOlapDiscoverDataProvider.prototype.$type, $.ig.IOlapExecuteCommandProvider.prototype.$type])
}, true);

$.ig.util.defType('MdxDataSourceImpl', 'OlapDataSource', {
	__mdxAxes: null,
	__useResultViewCache: false,
	init: function (options) {
		this.__mdxAxes = new $.ig.List$1($.ig.MdxDimensionAxisProvider.prototype.$type, 0);
		$.ig.OlapDataSource.prototype.init.call(this, options);
		this.mdxSettings(options.mdxSettings());
		this.mdxSourceOptions(options);
		this.dataProviderFactory(options.dataProviderFactory());
		this.__useResultViewCache = true;
		if (this.mdxSourceOptions() != null) {
			this.__useResultViewCache = this.mdxSourceOptions().enableResultCache() == true;
		}
	},
	_mdxSourceOptions: null,
	mdxSourceOptions: function (value) {
		if (arguments.length === 1) {
			this._mdxSourceOptions = value;
			return value;
		} else {
			return this._mdxSourceOptions;
		}
	}
	,
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	_catalog: null,
	catalog: function (value) {
		if (arguments.length === 1) {
			this._catalog = value;
			return value;
		} else {
			return this._catalog;
		}
	}
	,
	setCatalog: function (catalogName) {
		if (!this.isInitialized()) {
			throw new $.ig.InvalidOperationException(1, "Data source is not initialized.");
		}
		return this.onCatalogChanged(catalogName);
	}
	,
	removePositionsAtIndex: function (hostPosition, maxValidIndex) {
		if (hostPosition.positionItemIndex() == maxValidIndex) {
			hostPosition.clear();
		} else {
			var en = hostPosition.values().getEnumerator();
			while (en.moveNext()) {
				var positionItemInfo = en.current();
				this.removePositionsAtIndex(positionItemInfo, maxValidIndex);
			}
		}
	}
	,
	createSlicerAxis: function (columnsFilters, rowsFilters, filterFilters, mdxSettings) {
		var $self = this;
		var filters = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		var en = filterFilters.getEnumerator();
		while (en.moveNext()) {
			var filterFilter = en.current();
			var filterCopy = new $.ig.AxisElement(filterFilter.name(), new $.ig.List$1(String, 0));
			for (var i = 0; i < filterFilter.memberNames().count(); i++) {
				var memberName = filterFilter.memberNames().item(i);
				var member;
				if ((function () { var $ret = $self.cubeCache().tryGetMember1(memberName, member); member = $ret.p1; return $ret.ret; }())) {
					if (member.scope().hasValue() == false) {
						filterCopy.memberNames().add(memberName);
					}
				}
			}
			filters.add(filterCopy);
		}
		var rows = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		var en1 = rowsFilters.getEnumerator();
		while (en1.moveNext()) {
			var rowFilter = en1.current();
			var filterCopy1 = new $.ig.AxisElement(rowFilter.name(), new $.ig.List$1(String, 0));
			for (var i1 = 0; i1 < rowFilter.memberNames().count(); i1++) {
				var memberName1 = rowFilter.memberNames().item(i1);
				var member1;
				if ((function () { var $ret = $self.cubeCache().tryGetMember1(memberName1, member1); member1 = $ret.p1; return $ret.ret; }())) {
					if (member1.scope().hasValue() == false) {
						filterCopy1.memberNames().add(memberName1);
					}
				}
			}
			rows.add(filterCopy1);
		}
		var columns = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		var en2 = columnsFilters.getEnumerator();
		while (en2.moveNext()) {
			var columnsFilter = en2.current();
			var filterCopy2 = new $.ig.AxisElement(columnsFilter.name(), new $.ig.List$1(String, 0));
			for (var i2 = 0; i2 < columnsFilter.memberNames().count(); i2++) {
				var memberName2 = columnsFilter.memberNames().item(i2);
				var member2;
				if ((function () { var $ret = $self.cubeCache().tryGetMember1(memberName2, member2); member2 = $ret.p1; return $ret.ret; }())) {
					if (member2.scope().hasValue() == false) {
						filterCopy2.memberNames().add(memberName2);
					}
				}
			}
			columns.add(filterCopy2);
		}
		return new $.ig.MdxSlicerAxisProvider(this, columns, rows, filters, mdxSettings);
	}
	,
	getMembersOfLevel: function (levelUniqueName) {
		var $self = this;
		var levelName = levelUniqueName;
		var properties = this.getProviderDefaultProperties();
		var restrictions = this.getProviderDefaultRestrictions();
		if (restrictions != null) {
			restrictions.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key($.ig.MemberRestrictions.prototype.levelUniqueName);
				$ret.value(levelUniqueName);
				return $ret;
			}()));
		}
		var xmlaDataProvider = this.dataProviderFactory().createDataProvider();
		var getMembersTask = xmlaDataProvider.discoverMembersAsync(properties, restrictions);
		getMembersTask.continueWith1(function (t) {
			var en = t.result().getEnumerator();
			while (en.moveNext()) {
				var member = en.current();
				$self.cubeCache().addMember(member);
			}
			$self.cubeCache().setIsLevelLoaded(levelName);
		});
		return getMembersTask;
	}
	,
	getMembersOfHierarchy: function (hierarchyUniqueName) {
		var $self = this;
		var hierarchyName = hierarchyUniqueName;
		var properties = this.getProviderDefaultProperties();
		var restrictions = this.getProviderDefaultRestrictions();
		if (restrictions != null) {
			restrictions.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key($.ig.MemberRestrictions.prototype.hierarchyUniqueName);
				$ret.value(hierarchyUniqueName);
				return $ret;
			}()));
		}
		var xmlaDataProvider = this.dataProviderFactory().createDataProvider();
		var getMembersTask = xmlaDataProvider.discoverMembersAsync(properties, restrictions);
		getMembersTask.continueWith1(function (t) {
			var en = t.result().getEnumerator();
			while (en.moveNext()) {
				var member = en.current();
				$self.cubeCache().addMember(member);
			}
			$self.cubeCache().setIsHierarchyLoaded(hierarchyName);
		});
		return getMembersTask;
	}
	,
	getMembersOfMember: function (memberUniqueName) {
		var $self = this;
		var properties = this.getProviderDefaultProperties();
		var restrictions = this.getProviderDefaultRestrictions();
		if (restrictions != null) {
			restrictions.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key($.ig.MemberRestrictions.prototype.memberUniqueName);
				$ret.value(memberUniqueName);
				return $ret;
			}()));
			restrictions.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key($.ig.MemberRestrictions.prototype.treeOp);
				$ret.value("1");
				return $ret;
			}()));
		}
		var xmlaDataProvider = this.dataProviderFactory().createDataProvider();
		var getMembersTask = xmlaDataProvider.discoverMembersAsync(properties, restrictions);
		$.ig.TaskExtensions.prototype.continueWithTask$2($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Member.prototype.$type), $.ig.IEnumerable$1.prototype.$type.specialize($.ig.Member.prototype.$type), getMembersTask, function (t) { return $self.preloadLevelMembers(t.result()); });
		return getMembersTask;
	}
	,
	preloadLevelMembers: function (members) {
		var loadedMembers = new $.ig.List$1($.ig.Member.prototype.$type, 0);
		var en = members.getEnumerator();
		while (en.moveNext()) {
			var member = en.current();
			loadedMembers.add(member);
			break;
		}
		if (loadedMembers.count() > 0) {
			var levelUniqueName = loadedMembers.item(0).levelUniqueName();
			if (!this.cubeCache().isLevelLoaded(levelUniqueName)) {
				return this.getMembersOfLevel(levelUniqueName).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Member.prototype.$type), function (t1) { return members; });
			}
		}
		var tcs = new $.ig.TaskCompletionSource$1($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Member.prototype.$type), 0);
		tcs.setResult(members);
		return tcs.task();
	}
	,
	updateAxisMemberSortKeys: function (result) {
		var $self = this;
		if (result.isEmpty()) {
			return;
		}
		var en = result.axes().getEnumerator();
		while (en.moveNext()) {
			var axis = en.current();
			var tuples = axis.tuples();
			for (var i = 0; i < tuples.count(); i++) {
				var members = tuples.__inner[i].members();
				for (var j = 0; j < members.count(); j++) {
					var axisMember = members.item(j);
					var memberOrdinal;
					if ((function () { var $ret = $self.cubeCache().tryGetMemberOrdinal(axisMember.uniqueName(), memberOrdinal); memberOrdinal = $ret.p1; return $ret.ret; }())) {
						axisMember.ordinal(memberOrdinal);
					}
				}
			}
		}
	}
	,
	updateAxisProvidersCache1: function (hasColumns, hasRows) {
		var axisIndex = 0;
		var measureListIndex = this.measureListIndex();
		if (hasColumns) {
			var columnsAxisProvider = this.getAxisProvider(axisIndex);
			if (columnsAxisProvider != null && this.measureListLocation() == $.ig.MeasureListLocation.prototype.columns) {
				if (measureListIndex > -1) {
					var rootPosition = columnsAxisProvider.rootPositionInfo();
					if (rootPosition != null) {
						this.removePositionsAtIndex(rootPosition, measureListIndex - 1);
					}
					columnsAxisProvider.clearAxis();
				}
			}
			axisIndex++;
		}
		if (hasRows) {
			var rowsAxisProvider = this.getAxisProvider(axisIndex);
			if (rowsAxisProvider != null && this.measureListLocation() == $.ig.MeasureListLocation.prototype.rows) {
				if (measureListIndex > -1) {
					var rootPosition1 = rowsAxisProvider.rootPositionInfo();
					if (rootPosition1 != null) {
						this.removePositionsAtIndex(rootPosition1, measureListIndex - 1);
					}
					rowsAxisProvider.clearAxis();
				}
			}
		}
	}
	,
	updateAxisProvidersCache: function (result, hasRows, hasColumns) {
		var axisIndex = 0;
		if (hasColumns) {
			var axis = axisIndex < result.axes().count() ? result.axes().item(axisIndex) : null;
			var axisProvider = axis != null ? this.getAxisProvider(axisIndex) : null;
			if (axisProvider != null) {
				axisProvider.rootPositionInfo(axis.positionResolver().rootPositionInfo());
				axisProvider.clearAxis();
			}
			axisIndex++;
		}
		if (hasRows) {
			var axis1 = axisIndex < result.axes().count() ? result.axes().item(axisIndex) : null;
			var axisProvider1 = axis1 != null ? this.getAxisProvider(axisIndex) : null;
			if (axisProvider1 != null) {
				axisProvider1.rootPositionInfo(axis1.positionResolver().rootPositionInfo());
				axisProvider1.clearAxis();
			}
			axisIndex++;
		}
	}
	,
	expandCollapseTupleMembers: function (hasColumns, hasRows, useResultViewCahce, axisPositionsMap) {
		this.collapseTupleMembers(hasColumns, hasRows, useResultViewCahce);
		var axisToAdd = new $.ig.Dictionary$2(String, $.ig.Boolean.prototype.$type, 0);
		var positionsToExpand = this.getPositionsToExpand();
		axisPositionsMap = new $.ig.Dictionary$2(String, $.ig.IList$1.prototype.$type.specialize($.ig.PositionInfo.prototype.$type), 0);
		for (var i = 0; i < positionsToExpand.count(); i++) {
			var positionInfo = positionsToExpand.item(i);
			var axisPositions;
			if (!(function () { var $ret = axisPositionsMap.tryGetValue(positionInfo.axisName(), axisPositions); axisPositions = $ret.p1; return $ret.ret; }())) {
				axisPositions = new $.ig.List$1($.ig.PositionInfo.prototype.$type, 0);
				axisPositionsMap.add(positionInfo.axisName(), axisPositions);
			}
			axisPositions.add(positionInfo);
			if (this.resultView() == null) {
				this.resultView(new $.ig.OlapResultView(this.result(), this.result(), hasColumns, hasRows));
			}
			var resolvedCache = this.resultView().expandTupleMember(positionInfo.axisName(), positionInfo.tupleIndex(), positionInfo.memberIndex());
			if (resolvedCache == null) {
				axisToAdd.item(positionInfo.axisName(), true);
			} else {
				var oldResultView = this.resultView();
				this.resultView(resolvedCache);
				$.ig.ResultViewHelper.prototype.syncPositionExpansionState(positionInfo.axisName(), oldResultView, this.resultView());
				$.ig.ResultViewHelper.prototype.setPositionIsExpaned2(positionInfo.axisName(), positionInfo.tupleIndex(), positionInfo.memberIndex(), true, this.resultView());
				this.result(this.resultView().visibleResult());
				this.updateAxisMemberSortKeys(this.result());
				this.updateAxisProvidersCache(this.resultView().visibleResult(), hasRows, hasColumns);
			}
		}
		var axisesToExpand = new $.ig.List$1($.ig.IMdxAxis.prototype.$type, 0);
		var en = axisPositionsMap.getEnumerator();
		while (en.moveNext()) {
			var axisPositions1 = en.current();
			var resultAxis = this.getResultAxis1(axisPositions1.key());
			if (resultAxis == null) {
				continue;
			}
			if (resultAxis.positionResolver().hasUnregisterdPositions()) {
				resultAxis.positionResolver().completeRegisterPositions();
			}
			var positionsToExtend = new $.ig.List$1($.ig.IPositionItem$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), 0);
			var en1 = axisPositions1.value().getEnumerator();
			while (en1.moveNext()) {
				var positionInfo1 = en1.current();
				if (positionInfo1.tupleIndex() < resultAxis.tuples().count()) {
					var position = resultAxis.tuples().__inner[positionInfo1.tupleIndex()];
					var positionItem = position.item(positionInfo1.memberIndex());
					positionsToExtend.add(positionItem);
				}
			}
			if (positionsToExtend.count() > 0) {
				var axisToExtendIndex = this.result().axes().indexOf(resultAxis);
				var axisProvider = this.getAxisProvider(axisToExtendIndex);
				var mdxAxis = axisProvider.createAxis(axisToExtendIndex);
				var add = false;
				if ((function () { var $ret = axisToAdd.tryGetValue(resultAxis.name(), add); add = $ret.p1; return $ret.ret; }()) && add) {
					axisProvider = new $.ig.MdxDimensionAxisProvider(1, resultAxis, axisProvider.mdxSettings(), positionsToExtend);
					var partialAxis = axisProvider.extendAxis(mdxAxis, mdxAxis.axisIndex());
					axisesToExpand.add(partialAxis);
				}
			}
		}
		this.getPositionsToExpand().clear();
		return {
			ret: axisesToExpand,
			p3: axisPositionsMap
		};
	}
	,
	getMdxAxis: function (axisToUpdate, rebuildAxis, axisIndex) {
		var axisProvider = null;
		if (axisToUpdate.count() > 0) {
			axisProvider = this.getAxisProvider(axisIndex);
			if (rebuildAxis) {
				var rootPositionCache = null;
				if (axisProvider != null) {
					rootPositionCache = axisProvider.rootPositionInfo();
				}
				var settings = axisIndex == 0 ? this.mdxSettings().columnsAxisSettings() : this.mdxSettings().rowsAxisSettings();
				axisProvider = new $.ig.MdxDimensionAxisProvider(0, this, settings, axisToUpdate, rootPositionCache);
				this.setAxisProvider(axisIndex, axisProvider);
			}
		}
		if (axisProvider != null) {
			return axisProvider.createAxis(axisIndex);
		}
		return null;
	}
	,
	collapseTupleMembers: function (hasColumns, hasRows, useResultViewCahce) {
		if (this.result() != null) {
			var axisPositionsMap = new $.ig.Dictionary$2(String, $.ig.IList$1.prototype.$type.specialize($.ig.PositionInfo.prototype.$type), 0);
			var positionsToCollapse = this.getPositionsToCollapse();
			for (var i = 0; i < positionsToCollapse.count(); i++) {
				var positionInfo = positionsToCollapse.item(i);
				var axisPositions;
				if (!(function () { var $ret = axisPositionsMap.tryGetValue(positionInfo.axisName(), axisPositions); axisPositions = $ret.p1; return $ret.ret; }())) {
					axisPositions = new $.ig.List$1($.ig.PositionInfo.prototype.$type, 0);
					axisPositionsMap.add(positionInfo.axisName(), axisPositions);
				}
				axisPositions.add(positionInfo);
				if (useResultViewCahce) {
					if (this.resultView() == null) {
						this.resultView(new $.ig.OlapResultView(this.result(), this.result(), hasColumns, hasRows));
					} else {
						this.resultView(this.resultView().collapseTupleMember(positionInfo.axisName(), positionInfo.tupleIndex(), positionInfo.memberIndex()));
						this.result(this.resultView().visibleResult());
						this.updateAxisMemberSortKeys(this.result());
						this.updateAxisProvidersCache(this.resultView().visibleResult(), hasRows, hasColumns);
					}
				}
			}
			var axesToUpdate = new $.ig.List$1($.ig.OlapResultAxis.prototype.$type, 0);
			var en = axisPositionsMap.getEnumerator();
			while (en.moveNext()) {
				var positionGroup = en.current();
				var resultAxis = this.getResultAxis1(positionGroup.key());
				if (resultAxis == null) {
					continue;
				}
				if (resultAxis.positionResolver().hasUnregisterdPositions()) {
					resultAxis.positionResolver().completeRegisterPositions();
				}
				if (!axesToUpdate.contains(resultAxis)) {
					axesToUpdate.add(resultAxis);
				}
				if (!useResultViewCahce) {
					var en1 = positionGroup.value().getEnumerator();
					while (en1.moveNext()) {
						var positionInfo1 = en1.current();
						if (positionInfo1.tupleIndex() < resultAxis.tuples().count()) {
							var position = resultAxis.tuples().__inner[positionInfo1.tupleIndex()];
							var positionItem = position.item(positionInfo1.memberIndex());
							var hostItemInfo = resultAxis.positionResolver().getHostPositionItemInfo(position, positionInfo1.memberIndex());
							var itemInfo = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostItemInfo.values(), function (pi) { return pi.key() == positionItem.key(); }));
							if (itemInfo != null) {
								itemInfo.isExpanded(false);
							}
						}
					}
				}
			}
			if (axesToUpdate.count() > 0) {
				if (!useResultViewCahce && !this.result().isEmpty()) {
					this.updateAxisProvidersCache(this.result(), hasRows, hasColumns);
				}
				var en2 = axesToUpdate.getEnumerator();
				while (en2.moveNext()) {
					var axis = en2.current();
					var index = hasRows && axis.name() == "Axis1" ? 1 : 0;
					var axisProvider = this.getAxisProvider(index);
					axisProvider.clearAxis();
				}
			}
			this.getPositionsToCollapse().clear();
		}
	}
	,
	clearCatalogCache: function () {
		this.catalog(null);
		this.cubes(new $.ig.ArrayListCollection$1($.ig.Cube.prototype.$type));
		this.clearCubeCache();
	}
	,
	loadCatalogs: function () {
		var $self = this;
		var provider = this.dataProviderFactory().createDataProvider();
		var properties = this.getProviderDefaultProperties();
		var task = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Catalog.prototype.$type), $.ig.IEnumerable$1.prototype.$type.specialize($.ig.Catalog.prototype.$type), provider.discoverCatalogsAsync(properties, null), function (t) {
			$self.catalogs($self.toArrayListCollection$1($.ig.Catalog.prototype.$type, t.result()));
			return t;
		});
		return task;
	}
	,
	onCatalogChanged: function (catalogName) {
		if (String.isNullOrEmpty(catalogName)) {
			if (catalogName == null) {
				throw new $.ig.ArgumentNullException(0, "catalogName");
			}
			throw new $.ig.InvalidOperationException(1, "catalogName cannot be empty.");
		}
		var setCatalogCompleted = new $.ig.TaskCompletionSource$1($.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), 0);
		if (this.catalog() != null) {
			if (this.catalog().name() == catalogName) {
				setCatalogCompleted.setResult(this.cubes());
				return setCatalogCompleted.task();
			}
			this.clearCatalogCache();
		}
		var en = this.catalogs().getEnumerator();
		while (en.moveNext()) {
			var catalog = en.current();
			if (catalog.name() == catalogName) {
				this.catalog(catalog);
				break;
			}
		}
		if (this.cubes() == null || this.cubes().count() == 0) {
			this.loadCubes(catalogName).continueWith1(function (t1) {
				if (t1.exception() == null) {
					setCatalogCompleted.setResult(t1.result());
					return;
				}
				setCatalogCompleted.setException(t1.exception());
			});
		} else {
			setCatalogCompleted.setResult(this.cubes());
		}
		return setCatalogCompleted.task();
	}
	,
	onInitialize: function () {
		var $self = this;
		var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapMetadataTreeItem.prototype.$type, 0);
		if (this.isInitialized()) {
			tcs.setResult(this.metadataTree());
			return tcs.task();
		}
		var catalogName = this.mdxSourceOptions().catalog();
		var cubeName = this.mdxSourceOptions().cube();
		var $t = (this.columnAxis());
		$t.collectionChanged = $.ig.Delegate.prototype.combine($t.collectionChanged, this.columnsAxisChanged.runOn(this));
		var $t1 = (this.rowAxis());
		$t1.collectionChanged = $.ig.Delegate.prototype.combine($t1.collectionChanged, this.rowsAxisChanged.runOn(this));
		var $t2 = (this.filters());
		$t2.collectionChanged = $.ig.Delegate.prototype.combine($t2.collectionChanged, this.filtersAxisChanged.runOn(this));
		var $t3 = (this.measures());
		$t3.collectionChanged = $.ig.Delegate.prototype.combine($t3.collectionChanged, this.measuresAxisChanged.runOn(this));
		var loadCatalogsTask = this.loadCatalogs();
		if (String.isNullOrEmpty(catalogName)) {
			loadCatalogsTask.continueWith1(function (t) {
				if (t.exception() == null) {
					$self.initializeCompleted();
					var r1 = (function () {
						var $ret = new $.ig.OlapResult();
						$ret.isEmpty(true);
						return $ret;
					}());
					$self.resultView(new $.ig.OlapResultView(r1, r1, false, false));
					$self.result(r1);
					tcs.setResult(null);
					return;
				}
				tcs.setException(t.exception());
			});
			return tcs.task();
		}
		var setCatalogTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Catalog.prototype.$type), $.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), loadCatalogsTask, function (t) {
			if (t.exception() == null) {
				return $self.onCatalogChanged(catalogName);
			}
			var faultedTcs = new $.ig.TaskCompletionSource$1($.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), 0);
			faultedTcs.setException(t.exception());
			return faultedTcs.task();
		});
		if (String.isNullOrEmpty(cubeName)) {
			setCatalogTask.continueWith1(function (t) {
				if (t.exception() == null) {
					$self.initializeCompleted();
					var r2 = (function () {
						var $ret = new $.ig.OlapResult();
						$ret.isEmpty(true);
						return $ret;
					}());
					$self.resultView(new $.ig.OlapResultView(r2, r2, false, false));
					$self.result(r2);
					tcs.setResult(null);
					return;
				}
				tcs.setException(t.exception());
			});
			return tcs.task();
		}
		var setCubeTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.OlapMetadataTreeItem.prototype.$type, $.ig.OlapMetadataTreeItem.prototype.$type, $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), $.ig.OlapMetadataTreeItem.prototype.$type, setCatalogTask, function (t) { return $self.onSetCube(cubeName); }), function (t) { return $self.initializeAxes(); });
		return setCubeTask;
	}
	,
	onUpdate: function () {
		var $self = this;
		var hasColumnChanges = this.isColumnsAxisChanged();
		var hasRowChanges = this.isRowsAxisChanged();
		var hasMeasureChanges = this.isMeasuresAxisChanged();
		var hasFilterChanges = this.isFiltersAxisChanged();
		var hasPositionsToExpand = this.getPositionsToExpand().count() > 0;
		var hasPositionsToCollapse = this.getPositionsToCollapse().count() > 0;
		var hasMembersSelectionChanged = this._membersSelectionChangedList.count() > 0;
		if (!hasColumnChanges && !hasRowChanges && !hasMeasureChanges && !hasFilterChanges && !hasPositionsToExpand && !hasPositionsToCollapse && !hasMembersSelectionChanged) {
			var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
			tcs.setResult(this.result());
			return tcs.task();
		}
		var hasColumns = this.columnAxis().count() > 0;
		var hasRows = this.rowAxis().count() > 0;
		if (this.rowAxis().count() == 0 && this.columnAxis().count() == 0 && this.measures().count() == 0) {
			var result = (function () {
				var $ret = new $.ig.OlapResult();
				$ret.isEmpty(true);
				return $ret;
			}());
			var tcs1 = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
			tcs1.setResult(result);
			this.resultView(new $.ig.OlapResultView(result, result, false, false));
			this.result(result);
			this.updateAxisMemberSortKeys(result);
			this.updateAxisProvidersCache(this.resultView().visibleResult(), hasRows, hasColumns);
			this.clearPendingChanges();
			return tcs1.task();
		}
		if (this.isMeasuresAxisChanged()) {
			this.updateAxisProvidersCache1(hasColumns, hasRows);
		}
		this.isMeasuresAxisChanged(false);
		this.isFiltersAxisChanged(false);
		this.isMeasureListLocationChanged(false);
		var useResultViewCahce = false;
		if (this.__useResultViewCache && !this.isColumnsAxisChanged() && !this.isRowsAxisChanged()) {
			useResultViewCahce = true;
		}
		var mdxColumsAxis = null;
		var mdxRowsAxis = null;
		var axisIndex = 0;
		mdxColumsAxis = this.getMdxAxis(this.columnAxis(), this.isColumnsAxisChanged(), axisIndex);
		this.isColumnsAxisChanged(false);
		if (mdxColumsAxis != null) {
			axisIndex++;
		}
		mdxRowsAxis = this.getMdxAxis(this.rowAxis(), this.isRowsAxisChanged(), axisIndex);
		this.isRowsAxisChanged(false);
		if (mdxRowsAxis != null) {
			mdxRowsAxis.axisIndex(axisIndex);
			axisIndex++;
		}
		var axisPositionsMap;
		var axisesToExpand = (function () { var $ret = $self.expandCollapseTupleMembers(hasColumns, hasRows, useResultViewCahce, axisPositionsMap); axisPositionsMap = $ret.p3; return $ret.ret; }());
		var slicerAxisProvider = this.createSlicerAxis(this.columnFilters(), this.rowFilters(), this.filterFilters(), this.mdxSettings());
		var slicerAxis = slicerAxisProvider.createAxis(axisIndex);
		var mdxQueryBuilder = new $.ig.MdxQueryBuilder(1, this.mdxSettings());
		var properties = this.getProviderDefaultProperties();
		var restrictions = this.getProviderDefaultRestrictions();
		var provider = this.dataProviderFactory().createDataProvider();
		if (useResultViewCahce && !hasMembersSelectionChanged && (hasPositionsToExpand || hasPositionsToCollapse)) {
			var expandTask = null;
			if (mdxColumsAxis != null) {
				var partialColumnAxis = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.IMdxAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.IMdxAxis.prototype.$type, axisesToExpand, function (a) { return a.axisIndex() == mdxColumsAxis.axisIndex(); }));
				var columnPositionsToExpand;
				var $ret = axisPositionsMap.tryGetValue("Axis0", columnPositionsToExpand);
				columnPositionsToExpand = $ret.p1;
				if (partialColumnAxis != null) {
					var expandColumnsStatement = mdxQueryBuilder.buildMdxQuery(partialColumnAxis, mdxRowsAxis, slicerAxis, new $.ig.MdxCalculatedMembersCache(), this.cube().name());
					expandTask = provider.executeStatementAsync(expandColumnsStatement, properties, restrictions);
					expandTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.OlapResult.prototype.$type, $.ig.OlapResult.prototype.$type, expandTask, function (t) {
						if ($self.resultView() == null) {
							$self.resultView(new $.ig.OlapResultView($self.result(), $self.result(), hasColumns, hasRows));
						}
						var oldResultView = $self.resultView();
						$self.resultView($self.resultView().extend(t.result(), "Axis0"));
						$self.result($self.resultView().visibleResult());
						$self.updateAxisMemberSortKeys($self.result());
						$.ig.ResultViewHelper.prototype.syncPositionExpansionState("Axis0", oldResultView, $self.resultView());
						var en = columnPositionsToExpand.getEnumerator();
						while (en.moveNext()) {
							var positionInfo = en.current();
							$.ig.ResultViewHelper.prototype.setPositionIsExpaned2(positionInfo.axisName(), positionInfo.tupleIndex(), positionInfo.memberIndex(), true, $self.resultView());
						}
						var hasRowsValue = hasRows;
						var hasColumnsValue = hasColumns;
						$self.updateAxisProvidersCache($self.resultView().visibleResult(), hasRowsValue, hasColumnsValue);
						var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
						tcs.setResult($self.resultView().visibleResult());
						return tcs.task();
					});
				}
			}
			if (mdxRowsAxis != null) {
				var partialRowsAxis = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.IMdxAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.IMdxAxis.prototype.$type, axisesToExpand, function (a) { return a.axisIndex() == mdxRowsAxis.axisIndex(); }));
				var axisName = this.resultView().hasColumns() ? "Axis1" : "Axis0";
				var rowPositionsToExpand;
				var $ret1 = axisPositionsMap.tryGetValue(axisName, rowPositionsToExpand);
				rowPositionsToExpand = $ret1.p1;
				if (partialRowsAxis != null) {
					if (expandTask != null) {
						expandTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.OlapResult.prototype.$type, $.ig.OlapResult.prototype.$type, expandTask, function (t) {
							mdxColumsAxis = $self.getMdxAxis($self.columnAxis(), false, 0);
							var expandRowsStatement = mdxQueryBuilder.buildMdxQuery(mdxColumsAxis, partialRowsAxis, slicerAxis, new $.ig.MdxCalculatedMembersCache(), $self.cube().name());
							return provider.executeStatementAsync(expandRowsStatement, properties, restrictions);
						});
					} else {
						var expandRowsStatement = mdxQueryBuilder.buildMdxQuery(mdxColumsAxis, partialRowsAxis, slicerAxis, new $.ig.MdxCalculatedMembersCache(), this.cube().name());
						expandTask = provider.executeStatementAsync(expandRowsStatement, properties, restrictions);
					}
					expandTask = $.ig.TaskExtensions.prototype.continueWithTask$2($.ig.OlapResult.prototype.$type, $.ig.OlapResult.prototype.$type, expandTask, function (t) {
						if ($self.resultView() == null) {
							$self.resultView(new $.ig.OlapResultView($self.result(), $self.result(), hasColumns, hasRows));
						}
						var oldResultView = $self.resultView();
						$self.resultView($self.resultView().extend(t.result(), axisName));
						$self.result($self.resultView().visibleResult());
						$self.updateAxisMemberSortKeys($self.result());
						$.ig.ResultViewHelper.prototype.syncPositionExpansionState(axisName, oldResultView, $self.resultView());
						var en = rowPositionsToExpand.getEnumerator();
						while (en.moveNext()) {
							var positionInfo = en.current();
							$.ig.ResultViewHelper.prototype.setPositionIsExpaned2(positionInfo.axisName(), positionInfo.tupleIndex(), positionInfo.memberIndex(), true, $self.resultView());
						}
						var hasRowsValue = hasRows;
						var hasColumnsValue = hasColumns;
						$self.updateAxisProvidersCache($self.resultView().visibleResult(), hasRowsValue, hasColumnsValue);
						var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
						tcs.setResult($self.resultView().visibleResult());
						return tcs.task();
					});
				}
			}
			if (expandTask != null) {
				return expandTask;
			}
			var currentResultSource = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
			currentResultSource.setResult(this.result());
			return currentResultSource.task();
		} else {
			if (hasMembersSelectionChanged) {
				this._membersSelectionChangedList.clear();
			}
			{
				axisIndex = 0;
				mdxColumsAxis = this.getMdxAxis(this.columnAxis(), this.isColumnsAxisChanged(), axisIndex);
				if (mdxColumsAxis != null) {
					axisIndex++;
				}
				mdxRowsAxis = this.getMdxAxis(this.rowAxis(), this.isRowsAxisChanged(), axisIndex);
			}
			var statement = mdxQueryBuilder.buildMdxQuery(mdxColumsAxis, mdxRowsAxis, slicerAxis, new $.ig.MdxCalculatedMembersCache(), this.cube().name());
			return provider.executeStatementAsync(statement, properties, restrictions).continueWith$11($.ig.OlapResult.prototype.$type, function (t) {
				$self.resultView(new $.ig.OlapResultView(t.result(), t.result(), hasColumns, hasRows));
				$self.result($self.resultView().visibleResult());
				$self.updateAxisMemberSortKeys($self.result());
				var hasRowsValue = hasRows;
				var hasColumnsValue = hasColumns;
				$self.updateAxisProvidersCache($self.resultView().visibleResult(), hasRowsValue, hasColumnsValue);
				$self.clearPendingChanges();
				return $self.result();
			});
		}
	}
	,
	onSetCube: function (cubeName) {
		if (this.catalog() == null) {
			throw new $.ig.InvalidOperationException(1, "Data source has no Catalog set.");
		}
		return $.ig.OlapDataSource.prototype.onSetCube.call(this, cubeName);
	}
	,
	onSetMeasureGroup: function (measureGroupName) {
		if (this.catalog() == null) {
			throw new $.ig.InvalidOperationException(1, "Data source has no Catalog set.");
		}
		return $.ig.OlapDataSource.prototype.onSetMeasureGroup.call(this, measureGroupName);
	}
	,
	cacheCubeMetaItems: function () {
		var $self = this;
		var taskList = new $.ig.List$1($.ig.Task.prototype.$type, 0);
		var properties = this.getProviderDefaultProperties();
		var restrictions = this.getProviderDefaultRestrictions();
		var xmlaDataProvider = this.dataProviderFactory().createDataProvider();
		taskList.add(xmlaDataProvider.discoverMeasuresAsync(properties, restrictions));
		taskList.add(xmlaDataProvider.discoverKpisAsync(properties, restrictions));
		taskList.add(xmlaDataProvider.discoverDimensionsAsync(properties, restrictions));
		taskList.add(xmlaDataProvider.discoverHierarchiesAsync(properties, restrictions));
		taskList.add(xmlaDataProvider.discoverLevelsAsync(properties, restrictions));
		taskList.add(xmlaDataProvider.discoverMeasureGroupDimensionsAsync(properties, restrictions));
		taskList.add(this.loadMeasureGroups());
		var tasks = new Array(taskList.count());
		taskList.copyTo(tasks, 0);
		var tf = new $.ig.TaskFactory();
		var cubeCache = new $.ig.CubeMetaItemsCache();
		this.cubeCache(cubeCache);
		return tf.continueWhenAll(tasks, function (t) {
			var getMeasuresTask = t[0];
			var getKpisTask = t[1];
			var getDimensionsTask = t[2];
			var getHierarchiesTask = t[3];
			var getLevelsTask = t[4];
			var getMeasureGroupDimensionsTask = t[5];
			cubeCache.measures(getMeasuresTask.result());
			cubeCache.dimensionItems(getDimensionsTask.result());
			cubeCache.hierarchyItems(getHierarchiesTask.result());
			cubeCache.levelItems(getLevelsTask.result());
			cubeCache.kpis(getKpisTask.result());
			if ($self.measureGroups() != null) {
				cubeCache.measureGroupDimensions(getMeasureGroupDimensionsTask.result());
				cubeCache.measureGroupDimensionsCache(new $.ig.MeasureGroupMetaItemsCache(cubeCache.dimensionItems(), cubeCache.measures(), cubeCache.kpis(), cubeCache.measureGroupDimensions()));
			}
		});
	}
	,
	toArrayListCollection$1: function ($t, enumerable) {
		var res = new $.ig.ArrayListCollection$1($t);
		var en = enumerable.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			res.add(item);
		}
		return res;
	}
	,
	getProviderDefaultRestrictions: function () {
		var $self = this;
		var restrictions = $.ig.OlapDataSource.prototype.getProviderDefaultRestrictions.call(this);
		if (this.catalog() != null) {
			restrictions.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key($.ig.CubeRestrictions.prototype.catalogName);
				$ret.value($self.catalog().name());
				return $ret;
			}()));
			if (this.cube() != null) {
				restrictions.add((function () {
					var $ret = new $.ig.KeyValueItem();
					$ret.key($.ig.CubeRestrictions.prototype.cubeName);
					$ret.value($self.cube().name());
					return $ret;
				}()));
			}
		}
		return restrictions;
	}
	,
	columnsAxisChanged: function (sender, e) {
		this.isColumnsAxisChanged(true);
		this.updateMdxAxesItems(0, this.columnAxis(), this.rowAxis());
		this.updateAxisState(e, this.columnFilters());
		this.updateMeasureListIndex(e, this.columnAxis());
		if (e.action() != $.ig.NotifyCollectionChangedAction.prototype.reset && this.columnAxis() != null) {
			var validToIndex = Math.max(e.newStartingIndex(), e.oldStartingIndex());
			this.updateExpandCollapseCache("Axis0", validToIndex);
		}
	}
	,
	rowsAxisChanged: function (sender, e) {
		this.isRowsAxisChanged(true);
		this.updateMdxAxesItems(1, this.rowAxis(), this.columnAxis());
		this.updateAxisState(e, this.rowFilters());
		this.updateMeasureListIndex(e, this.rowAxis());
		if (e.action() != $.ig.NotifyCollectionChangedAction.prototype.reset && this.rowAxis() != null) {
			var axisName = this.columnAxis() == null ? "Axis0" : "Axis1";
			var validToIndex = Math.max(e.newStartingIndex(), e.oldStartingIndex());
			this.updateExpandCollapseCache(axisName, validToIndex);
		}
	}
	,
	updateExpandCollapseCache: function (axisName, validToIndex) {
		var positionsToCollapse = this.getPositionsToCollapse();
		for (var i = positionsToCollapse.count() - 1; i >= 0; i--) {
			var positionInfo = positionsToCollapse.item(i);
			if (positionInfo.axisName() == axisName && positionInfo.memberIndex() >= validToIndex) {
				positionsToCollapse.removeAt(i);
			}
		}
		var positionsToExpand = this.getPositionsToExpand();
		for (var i1 = positionsToExpand.count() - 1; i1 >= 0; i1--) {
			var positionInfo1 = positionsToExpand.item(i1);
			if (positionInfo1.axisName() == axisName && positionInfo1.memberIndex() >= validToIndex) {
				positionsToExpand.removeAt(i1);
			}
		}
	}
	,
	updateMdxAxesItems: function (mdxAxisIndex, axisToManageForMdx, opositeAxisToManage) {
		if (axisToManageForMdx.count() > 0) {
			if (opositeAxisToManage.count() > 0) {
				if (this.__mdxAxes.count() < 2) {
					this.__mdxAxes.insert(mdxAxisIndex, null);
				}
			} else {
				if (this.__mdxAxes.count() == 0) {
					this.__mdxAxes.insert(0, null);
				}
			}
		} else {
			if (this.__mdxAxes.count() > 0) {
				if (this.__mdxAxes.count() > 1) {
					this.__mdxAxes.removeAt(mdxAxisIndex);
				} else {
					this.__mdxAxes.removeAt(0);
				}
			}
		}
	}
	,
	filtersAxisChanged: function (sender, e) {
		this.isFiltersAxisChanged(true);
		this.updateAxisState(e, this.filterFilters());
	}
	,
	measuresAxisChanged: function (sender, e) {
		this.isMeasuresAxisChanged(true);
		this.updateMeasureList();
	}
	,
	ensureEntryValue: function (entries, key, value) {
		var keyValueItem = null;
		var en = entries.getEnumerator();
		while (en.moveNext()) {
			var p = en.current();
			if (p.key() == key) {
				keyValueItem = p;
				break;
			}
		}
		if (keyValueItem == null) {
			entries.add((function () {
				var $ret = new $.ig.KeyValueItem();
				$ret.key(key);
				$ret.value(value);
				return $ret;
			}()));
		}
	}
	,
	getResultAxis1: function (axisName) {
		if (this.result() != null && this.result().axes().count() > 0) {
			return $.ig.Enumerable.prototype.firstOrDefault$1($.ig.OlapResultAxis.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.OlapResultAxis.prototype.$type, this.result().axes(), function (ax) { return ax.name() == axisName; }));
		}
		return null;
	}
	,
	getAxisProvider: function (axisIndex) {
		if (axisIndex < this.__mdxAxes.count()) {
			return this.__mdxAxes.item(axisIndex);
		}
		return null;
	}
	,
	setAxisProvider: function (axisIndex, mdxAxis) {
		this.__mdxAxes.item(axisIndex, mdxAxis);
	}
	,
	$type: new $.ig.Type('MdxDataSourceImpl', $.ig.OlapDataSource.prototype.$type)
}, true);

$.ig.util.defType('MdxDataSourceOptions', 'DataSourceBaseOptions', {
	init: function () {
		$.ig.DataSourceBaseOptions.prototype.init.call(this);
	},
	_catalog: null,
	catalog: function (value) {
		if (arguments.length === 1) {
			this._catalog = value;
			return value;
		} else {
			return this._catalog;
		}
	}
	,
	_discoverProperties: null,
	discoverProperties: function (value) {
		if (arguments.length === 1) {
			this._discoverProperties = value;
			return value;
		} else {
			return this._discoverProperties;
		}
	}
	,
	_executeProperties: null,
	executeProperties: function (value) {
		if (arguments.length === 1) {
			this._executeProperties = value;
			return value;
		} else {
			return this._executeProperties;
		}
	}
	,
	_dataProviderFactory: null,
	dataProviderFactory: function (value) {
		if (arguments.length === 1) {
			this._dataProviderFactory = value;
			return value;
		} else {
			return this._dataProviderFactory;
		}
	}
	,
	_enableResultCache: false,
	enableResultCache: function (value) {
		if (arguments.length === 1) {
			this._enableResultCache = value;
			return value;
		} else {
			return this._enableResultCache;
		}
	}
	,
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	$type: new $.ig.Type('MdxDataSourceOptions', $.ig.DataSourceBaseOptions.prototype.$type)
}, true);

$.ig.util.defType('XmlaDataProvider', 'Object', {
	__xmlaConnection: null,
	__initXmlaMethod: null,
	init: function (xmlaConnection, initXmlaMethod) {
		$.ig.Object.prototype.init.call(this);
		this.__xmlaConnection = xmlaConnection;
		this.__initXmlaMethod = initXmlaMethod;
	},
	discoverCatalogsAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Catalog.prototype.$type, new $.ig.DatabaseXmlTypeSerializer(), $.ig.DbSchemaConstants.prototype.catalogs, properties, restrictions);
	}
	,
	discoverCubesAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Cube.prototype.$type, new $.ig.CubeXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.cubes, properties, restrictions);
	}
	,
	discoverDimensionsAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Dimension.prototype.$type, new $.ig.DimensionXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.dimensions, properties, restrictions);
	}
	,
	discoverHierarchiesAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Hierarchy.prototype.$type, new $.ig.HierarchyXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.hierarchies, properties, restrictions);
	}
	,
	discoverLevelsAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Level.prototype.$type, new $.ig.LevelXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.levels, properties, restrictions);
	}
	,
	discoverMeasuresAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Measure.prototype.$type, new $.ig.MeasureXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.measures, properties, restrictions);
	}
	,
	discoverMeasureGroupsAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.MeasureGroup.prototype.$type, new $.ig.MeasureGroupXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.measureGroups, properties, restrictions);
	}
	,
	discoverMeasureGroupDimensionsAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.MeasureGroupDimension.prototype.$type, new $.ig.MeasureGroupDimensionXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.measureGroupDimensions, properties, restrictions);
	}
	,
	discoverMembersAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Member.prototype.$type, new $.ig.MemberXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.members, properties, restrictions);
	}
	,
	discoverKpisAsync: function (properties, restrictions) {
		return this.discoverAsync$1($.ig.Kpi.prototype.$type, new $.ig.KpiXmlTypeSerializer(), $.ig.OlapSchemaConstants.prototype.kpis, properties, restrictions);
	}
	,
	executeStatementAsync: function (statement, properties, parameters) {
		var $self = this;
		var tcs = new $.ig.TaskCompletionSource$1($.ig.OlapResult.prototype.$type, 0);
		var client = this.__xmlaConnection.getXmlaSoapWebClient$1($.ig.ExecuteResponseResult.prototype.$type);
		var serializer = new $.ig.ResultXmlTypeSerializer();
		client.requestCompleted = $.ig.Delegate.prototype.combine(client.requestCompleted, function (s, args) { $self.executeAsyncCompleted$1($.ig.OlapResult.prototype.$type, serializer, args, tcs); });
		var method = new $.ig.XmlaSoapMethodExecute(statement);
		this.initializeExecuteMethod(method, properties, parameters);
		client.requestAsync(method);
		return tcs.task();
	}
	,
	discoverAsync$1: function ($t, serializer, requestType, properties, restrictions) {
		var $self = this;
		var tcs = new $.ig.TaskCompletionSource$1($.ig.IEnumerable$1.prototype.$type.specialize($t), 0);
		var client = this.__xmlaConnection.getXmlaSoapWebClient$1($.ig.DiscoverResponseResult.prototype.$type);
		client.requestCompleted = $.ig.Delegate.prototype.combine(client.requestCompleted, function (s, args) { $self.discoverAsyncCompleted$1($t, serializer, args, tcs); });
		var method = new $.ig.XmlaSoapMethodDiscover(requestType);
		this.initializeDiscoverMethod(method, properties, restrictions);
		client.requestAsync(method);
		return tcs.task();
	}
	,
	initializeDiscoverMethod: function (method, properties, restrictions) {
		if (restrictions != null) {
			var en = restrictions.getEnumerator();
			while (en.moveNext()) {
				var restriction = en.current();
				method.restrictions().add(restriction.key(), restriction.value());
			}
		}
		if (properties != null) {
			var en1 = properties.getEnumerator();
			while (en1.moveNext()) {
				var property = en1.current();
				method.properties().add$1(new $.ig.XmlaQueryProperty(1, property.key(), property.value()));
			}
		}
		if (this.__initXmlaMethod != null) {
			this.__initXmlaMethod(method);
		}
	}
	,
	initializeExecuteMethod: function (method, properties, parameters) {
		if (parameters != null) {
			var en = parameters.getEnumerator();
			while (en.moveNext()) {
				var restriction = en.current();
			}
		}
		if (properties != null) {
			var en1 = properties.getEnumerator();
			while (en1.moveNext()) {
				var property = en1.current();
				method.properties().add$1(new $.ig.XmlaQueryProperty(1, property.key(), property.value()));
			}
		}
		if (this.__initXmlaMethod != null) {
			this.__initXmlaMethod(method);
		}
	}
	,
	discoverAsyncCompleted$1: function ($t, ser, e, tcs) {
		if (e.error() != null) {
			tcs.setException(e.error());
		} else if (e.cancelled()) {
			tcs.setCanceled();
		} else {
			var result = e.result();
			if (result != null) {
				var arrSer = new $.ig.ArraySerializer();
				var x = arrSer.deserialize$1($t, e.result().returnElement(), $.ig.XName.prototype.get("row", $.ig.XmlaNamespace.prototype.rowset), ser);
				tcs.setResult(x);
			}
		}
	}
	,
	executeAsyncCompleted$1: function ($t, ser, e, tcs) {
		if (e.error() != null) {
			tcs.setException(e.error());
		} else if (e.cancelled()) {
			tcs.setCanceled();
		} else {
			var result = e.result();
			if (result != null) {
				var x = ser.deserialize(e.result().returnElement());
				tcs.setResult(x);
			}
		}
	}
	,
	$type: new $.ig.Type('XmlaDataProvider', $.ig.Object.prototype.$type, [$.ig.IXmlaDataProvider.prototype.$type])
}, true);

$.ig.util.defType('CatalogConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('CatalogConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DbSchemaConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('DbSchemaConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CubeConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('CubeConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DimensionConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('DimensionConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('HierarchyConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('HierarchyConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('InstanceConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('InstanceConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KpiConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('KpiConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LevelConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('LevelConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroupConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureGroupConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroupDimensionConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureGroupDimensionConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MemberConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MemberConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OlapSchemaConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('OlapSchemaConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('CubeRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('CubeRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DimensionRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('DimensionRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('HierarchyRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('HierarchyRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KpiRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('KpiRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('LevelRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('LevelRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroupRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureGroupRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureGroupDimensionRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MeasureGroupDimensionRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MemberRestrictions', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MemberRestrictions', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ICoreXmlaConnection', 'Object', {
	$type: new $.ig.Type('ICoreXmlaConnection', null, [$.ig.IConnection.prototype.$type])
}, true);

$.ig.util.defType('ArraySerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize$1: function ($t, element, rowElementName, serializer) {
		var result = new $.ig.List$1($t, 0);
		var en = element.elements1(rowElementName).getEnumerator();
		while (en.moveNext()) {
			var rowElement = en.current();
			result.add(serializer.deserialize(rowElement));
		}
		return result;
	}
	,
	$type: new $.ig.Type('ArraySerializer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IXmlTypeSerializer$1', 'Object', {
	$type: new $.ig.Type('IXmlTypeSerializer$1', null)
}, true);

$.ig.util.defType('AxisXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.AxisXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.AxisXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.AxisXmlTypeSerializer.prototype._xNamesCache.item("0", $.ig.XName.prototype.get("Tuple", $.ig.XmlaNamespace.prototype.mdDataset));
		$.ig.AxisXmlTypeSerializer.prototype._xNamesCache.item("1", $.ig.XName.prototype.get("Tuples", $.ig.XmlaNamespace.prototype.mdDataset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var axisName = $.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("name", "")));
		var tuplesElement = element.element($.ig.AxisXmlTypeSerializer.prototype._xNamesCache.item("1"));
		var arr = new $.ig.ArraySerializer();
		var tuples = arr.deserialize$1($.ig.OlapResultTuple.prototype.$type, tuplesElement, $.ig.AxisXmlTypeSerializer.prototype._xNamesCache.item("0"), new $.ig.TupleXmlTypeSerializer());
		var tupleSize = 0;
		if (tuples.count() > 0) {
			tupleSize = tuples.__inner[0].members().count();
		}
		return (function () {
			var $ret = new $.ig.OlapResultAxis(tuples, tupleSize);
			$ret.name(axisName);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('AxisXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.OlapResultAxis.prototype.$type)])
}, true);

$.ig.util.defType('TupleXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.TupleXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.TupleXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.TupleXmlTypeSerializer.prototype._xNamesCache.item("0", $.ig.XName.prototype.get("Member", $.ig.XmlaNamespace.prototype.mdDataset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var arr = new $.ig.ArraySerializer();
		var members = arr.deserialize$1($.ig.OlapResultAxisMember.prototype.$type, element, $.ig.TupleXmlTypeSerializer.prototype._xNamesCache.item("0"), new $.ig.AxisMemberXmlTypeSerializer());
		var tuple = new $.ig.OlapResultTuple(members);
		var en = members.getEnumerator();
		while (en.moveNext()) {
			var axisMember = en.current();
			axisMember.position(tuple);
		}
		return tuple;
	}
	,
	$type: new $.ig.Type('TupleXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.OlapResultTuple.prototype.$type)])
}, true);

$.ig.util.defType('AxisMemberXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("0", $.ig.XName.prototype.get("UName", $.ig.XmlaNamespace.prototype.mdDataset));
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("1", $.ig.XName.prototype.get("LName", $.ig.XmlaNamespace.prototype.mdDataset));
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("2", $.ig.XName.prototype.get("Caption", $.ig.XmlaNamespace.prototype.mdDataset));
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("3", $.ig.XName.prototype.get("LNum", $.ig.XmlaNamespace.prototype.mdDataset));
		$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("4", $.ig.XName.prototype.get("DisplayInfo", $.ig.XmlaNamespace.prototype.mdDataset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("0")));
		var levelUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("1")));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("2")));
		var levelDepth = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("3")));
		var displayInfo = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache.item("4")));
		var hierarchyUniqueName = element.attribute($.ig.XName.prototype.get("Hierarchy", "")).value();
		var tupleMember = (function () {
			var $ret = new $.ig.OlapResultAxisMember();
			$ret.hierarchyUniqueName(hierarchyUniqueName);
			$ret.levelUniqueName(levelUniqueName);
			$ret.levelNumber($.ig.Number.prototype.parseInt(levelDepth));
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.displayInfo(String.isNullOrEmpty(displayInfo) ? 0 : $.ig.util.intSToU($.ig.Number.prototype.parseInt(displayInfo)));
			return $ret;
		}());
		var en = element.elements().getEnumerator();
		while (en.moveNext()) {
			var xElement = en.current();
			switch (xElement.name().localName()) {
				case "UName":
				case "LName":
				case "Caption":
				case "LNum":
				case "DisplayInfo":
					continue;
				default:
					tupleMember.properties().add(xElement.name().localName(), xElement.value());
					break;
			}
		}
		return tupleMember;
	}
	,
	$type: new $.ig.Type('AxisMemberXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.OlapResultAxisMember.prototype.$type)])
}, true);

$.ig.util.defType('CellXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var cellOrdinal = element.attribute($.ig.XName.prototype.get("CellOrdinal", "")).value();
		var cell = (function () {
			var $ret = new $.ig.OlapResultCell();
			$ret.cellOrdinal($.ig.Number.prototype.parseInt(cellOrdinal));
			return $ret;
		}());
		var en = element.elements().getEnumerator();
		while (en.moveNext()) {
			var xElement = en.current();
			cell.properties().add(xElement.name().localName(), xElement.value());
		}
		return cell;
	}
	,
	$type: new $.ig.Type('CellXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.OlapResultCell.prototype.$type)])
}, true);

$.ig.util.defType('CubeXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.cubeName, $.ig.XmlaNamespace.prototype.rowset)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.cubeCaption, $.ig.XmlaNamespace.prototype.rowset)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset)));
		var cubeType;
		var cubeTypeString = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.cubeType, $.ig.XmlaNamespace.prototype.rowset)));
		switch (cubeTypeString) {
			case $.ig.XmlaConstants.prototype.cubeStringConstant:
				cubeType = $.ig.CubeType.prototype.cube;
				break;
			case $.ig.XmlaConstants.prototype.dimensionStringConstant:
				cubeType = $.ig.CubeType.prototype.dimension;
				break;
			default:
				cubeType = $.ig.CubeType.prototype.unknown;
				break;
		}
		var catalogName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.catalogName, $.ig.XmlaNamespace.prototype.rowset)));
		var lastProcessed = $.ig.SerializerUtils.prototype.getDateTime(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.lastDataUpdate, $.ig.XmlaNamespace.prototype.rowset)));
		var lastUpdated = $.ig.SerializerUtils.prototype.getDateTime(element.element($.ig.XName.prototype.get($.ig.CubeConstants.prototype.lastSchemaUpdate, $.ig.XmlaNamespace.prototype.rowset)));
		return (function () {
			var $ret = new $.ig.Cube();
			$ret.name(name);
			$ret.caption(caption);
			$ret.description(description);
			$ret.cubeType(cubeType);
			$ret.catalogName(catalogName);
			$ret.lastProcessed(lastProcessed);
			$ret.lastUpdated(lastUpdated);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('CubeXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Cube.prototype.$type)])
}, true);

$.ig.util.defType('DatabaseXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CatalogConstants.prototype.catalogName, $.ig.XmlaNamespace.prototype.rowset)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.CatalogConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset)));
		return (function () {
			var $ret = new $.ig.Catalog();
			$ret.name(name);
			$ret.caption(name);
			$ret.uniqueName(name);
			$ret.description(description);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('DatabaseXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Catalog.prototype.$type)])
}, true);

$.ig.util.defType('DimensionXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionName, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.dimensionName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionUniqueName, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.dimensionUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionCaption, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.dimensionCaption, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.description, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionType, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.dimensionType, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.defaultHierarchy, $.ig.XName.prototype.get($.ig.DimensionConstants.prototype.defaultHierarchy, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionName)));
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionUniqueName)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionCaption)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.description)));
		var typeString = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.dimensionType)));
		var dimensionType = ($.ig.Number.prototype.parseInt(typeString));
		var defaultHierarchy = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.DimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.DimensionConstants.prototype.defaultHierarchy)));
		return (function () {
			var $ret = new $.ig.Dimension();
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.description(description);
			$ret.dimensionType(dimensionType);
			$ret.defaultHierarchy(defaultHierarchy);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('DimensionXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Dimension.prototype.$type)])
}, true);

$.ig.util.defType('FaultError', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_errorCode: null,
	errorCode: function (value) {
		if (arguments.length === 1) {
			this._errorCode = value;
			return value;
		} else {
			return this._errorCode;
		}
	}
	,
	_description: null,
	description: function (value) {
		if (arguments.length === 1) {
			this._description = value;
			return value;
		} else {
			return this._description;
		}
	}
	,
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
	_helpFile: null,
	helpFile: function (value) {
		if (arguments.length === 1) {
			this._helpFile = value;
			return value;
		} else {
			return this._helpFile;
		}
	}
	,
	_severity: null,
	severity: function (value) {
		if (arguments.length === 1) {
			this._severity = value;
			return value;
		} else {
			return this._severity;
		}
	}
	,
	$type: new $.ig.Type('FaultError', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ErrorXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		var e = new $.ig.FaultError();
		var element = node;
		e.errorCode($.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("ErrorCode", ""))));
		e.description($.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("Description", ""))));
		e.source($.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("Source", ""))));
		e.helpFile($.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("HelpFile", ""))));
		e.severity($.ig.SerializerUtils.prototype.getText(element.attribute($.ig.XName.prototype.get("Severity", ""))));
		return e;
	}
	,
	$type: new $.ig.Type('ErrorXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.FaultError.prototype.$type)])
}, true);

$.ig.util.defType('HierarchyXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyName, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.hierarchyName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyUniqueName, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.hierarchyUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyCaption, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.hierarchyCaption, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.description, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.defaultMember, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.defaultMember, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.allMember, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.allMember, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.dimensionUniqueName, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.dimensionUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyDisplayFolder, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.hierarchyDisplayFolder, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyOrigin, $.ig.XName.prototype.get($.ig.HierarchyConstants.prototype.hierarchyOrigin, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyName)));
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyUniqueName)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyCaption)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.description)));
		var defaultMember = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.defaultMember)));
		var allMember = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.allMember)));
		var dimensionUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.dimensionUniqueName)));
		var displayFolder = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyDisplayFolder)));
		var hierarchyOriginString = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache.item($.ig.HierarchyConstants.prototype.hierarchyOrigin)));
		var hierarchyOrigin = (hierarchyOriginString == null ? 1 : $.ig.Number.prototype.parseInt(hierarchyOriginString));
		return (function () {
			var $ret = new $.ig.Hierarchy();
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.description(description);
			$ret.defaultMember(defaultMember);
			$ret.allMember(allMember);
			$ret.dimensionUniqueName(dimensionUniqueName);
			$ret.hierarchyDisplayFolder(displayFolder);
			$ret.hierarchyOrigin(hierarchyOrigin);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('HierarchyXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Hierarchy.prototype.$type)])
}, true);

$.ig.util.defType('KpiXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiName, $.ig.XmlaNamespace.prototype.rowset)));
		var uniqueName = name;
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiCaption, $.ig.XmlaNamespace.prototype.rowset)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiDescription, $.ig.XmlaNamespace.prototype.rowset)));
		var measureGroupName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.measureGroupName, $.ig.XmlaNamespace.prototype.rowset)));
		var displayFolder = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiDisplayFolder, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiValue = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiValue, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiGoal = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiGoal, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiStatus = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiStatus, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiTrend = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiTrend, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiWeight = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiWeight, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiStatusGraphic = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiStatusGraphic, $.ig.XmlaNamespace.prototype.rowset)));
		var kpiTrendGraphic = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiTrendGraphic, $.ig.XmlaNamespace.prototype.rowset)));
		var parentKpiName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiParentKpiName, $.ig.XmlaNamespace.prototype.rowset)));
		var currentTimeMember = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.KpiConstants.prototype.kpiCurrentTimeMember, $.ig.XmlaNamespace.prototype.rowset)));
		return (function () {
			var $ret = new $.ig.Kpi();
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.description(description);
			$ret.measureGroupName(measureGroupName);
			$ret.kpiDisplayFolder(displayFolder);
			$ret.kpiValue(kpiValue);
			$ret.kpiGoal(kpiGoal);
			$ret.kpiStatus(kpiStatus);
			$ret.kpiTrend(kpiTrend);
			$ret.kpiStatusGraphic(kpiStatusGraphic);
			$ret.kpiTrendGraphic(kpiTrendGraphic);
			$ret.kpiWeight(kpiWeight);
			$ret.parentKpiName(parentKpiName);
			$ret.currentTimeMember(currentTimeMember);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('KpiXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Kpi.prototype.$type)])
}, true);

$.ig.util.defType('LevelXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelName, $.ig.XmlaNamespace.prototype.rowset)));
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelUniqueName, $.ig.XmlaNamespace.prototype.rowset)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelCaption, $.ig.XmlaNamespace.prototype.rowset)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset)));
		var dimensionUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.dimensionUniqueName, $.ig.XmlaNamespace.prototype.rowset)));
		var hierarchyUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.hierarchyUniqueName, $.ig.XmlaNamespace.prototype.rowset)));
		var levelDepth = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelNumber, $.ig.XmlaNamespace.prototype.rowset)));
		var levelCardinality = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelCardinality, $.ig.XmlaNamespace.prototype.rowset)));
		var levelOriginValue = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelOrigin, $.ig.XmlaNamespace.prototype.rowset)));
		var levelOrderingProperty = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.LevelConstants.prototype.levelOrderingProperty, $.ig.XmlaNamespace.prototype.rowset)));
		return (function () {
			var $ret = new $.ig.Level();
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.description(description);
			$ret.dimensionUniqueName(dimensionUniqueName);
			$ret.hierarchyUniqueName(hierarchyUniqueName);
			$ret.depth($.ig.Number.prototype.parseInt(levelDepth));
			$ret.membersCount($.ig.Number.prototype.parseInt(levelCardinality));
			$ret.levelOrigin(String.isNullOrEmpty(levelOriginValue) ? 0 : $.ig.Number.prototype.parseInt(levelOriginValue));
			$ret.levelOrderingProperty(levelOrderingProperty);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('LevelXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Level.prototype.$type)])
}, true);

$.ig.util.defType('MeasureGroupDimensionXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.catalogName, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.catalogName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.cubeName, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.cubeName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.measureGroupName, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.measureGroupName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionUniqueName, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.measureGroupCardinality, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.measureGroupCardinality, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionCardinality, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionCardinality, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsVisible, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsVisible, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsFactDimension, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsFactDimension, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionPath, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionPath, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionGranularity, $.ig.XName.prototype.get($.ig.MeasureGroupDimensionConstants.prototype.dimensionGranularity, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var catalogName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.catalogName)));
		var cubeName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.cubeName)));
		var measureGroupName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.measureGroupName)));
		var dimensionUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionUniqueName)));
		var measureGroupCardinality = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.measureGroupCardinality)));
		var measureGroupCardinalityType = measureGroupCardinality == "ONE" ? $.ig.CardinalityType.prototype.one : $.ig.CardinalityType.prototype.many;
		var dimensionCardinality = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionCardinality)));
		var dimensionCardinalityType = dimensionCardinality == "ONE" ? $.ig.CardinalityType.prototype.one : $.ig.CardinalityType.prototype.many;
		var isDimensionVisible = $.ig.SerializerUtils.prototype.getBoolean(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsVisible)));
		var isFactDimension = $.ig.SerializerUtils.prototype.getBoolean(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionIsFactDimension)));
		var dimensionPath = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionPath)));
		var dimensionGranularity = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupDimensionConstants.prototype.dimensionGranularity)));
		return (function () {
			var $ret = new $.ig.MeasureGroupDimension();
			$ret.catalogName(catalogName);
			$ret.cubeName(cubeName);
			$ret.measureGroupName(measureGroupName);
			$ret.dimensionUniqueName(dimensionUniqueName);
			$ret.measureGroupCardinality(measureGroupCardinalityType);
			$ret.dimensionCardinality(dimensionCardinalityType);
			$ret.isDimensionVisible(isDimensionVisible);
			$ret.isFactDimension(isFactDimension);
			$ret.dimensionPath(dimensionPath);
			$ret.dimensionGranularity(dimensionGranularity);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('MeasureGroupDimensionXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.MeasureGroupDimension.prototype.$type)])
}, true);

$.ig.util.defType('MeasureGroupXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.catalogName, $.ig.XName.prototype.get($.ig.MeasureGroupConstants.prototype.catalogName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.cubeName, $.ig.XName.prototype.get($.ig.MeasureGroupConstants.prototype.cubeName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.measureGroupName, $.ig.XName.prototype.get($.ig.MeasureGroupConstants.prototype.measureGroupName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.measureGroupCaption, $.ig.XName.prototype.get($.ig.MeasureGroupConstants.prototype.measureGroupCaption, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.description, $.ig.XName.prototype.get($.ig.MeasureGroupConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var catalogName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.catalogName)));
		var cubeName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.cubeName)));
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.measureGroupName)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.measureGroupCaption)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureGroupConstants.prototype.description)));
		return (function () {
			var $ret = new $.ig.MeasureGroup();
			$ret.catalogName(catalogName);
			$ret.cubeName(cubeName);
			$ret.name(name);
			$ret.caption(caption);
			$ret.description(description);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('MeasureGroupXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type)])
}, true);

$.ig.util.defType('MeasureXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureName, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureUniqueName, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureCaption, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureCaption, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.description, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.defaultFormatString, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.defaultFormatString, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureGroupName, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureGroupName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureAggregator, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureAggregator, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureDisplayFolder, $.ig.XName.prototype.get($.ig.MeasureConstants.prototype.measureDisplayFolder, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureName)));
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureUniqueName)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureCaption)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.description)));
		var defaultFormatString = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.defaultFormatString)));
		var groupName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureGroupName)));
		var aggregatorTypeString = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureAggregator)));
		var aggregatorType = ($.ig.Number.prototype.parseInt(aggregatorTypeString));
		var displayFolder = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MeasureXmlTypeSerializer.prototype._xNamesCache.item($.ig.MeasureConstants.prototype.measureDisplayFolder)));
		return (function () {
			var $ret = new $.ig.Measure();
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.caption(caption);
			$ret.description(description);
			$ret.defaultFormatString(defaultFormatString);
			$ret.measureGroupName(groupName);
			$ret.aggregatorType(aggregatorType);
			$ret.measureDisplayFolder(displayFolder);
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('MeasureXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Measure.prototype.$type)])
}, true);

$.ig.util.defType('MemberXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		if ($.ig.MemberXmlTypeSerializer.prototype._xNamesCache != null) {
			return;
		}
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache = new $.ig.Dictionary$2(String, $.ig.XName.prototype.$type, 0);
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.catalogName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.catalogName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.cubeName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.cubeName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.memberName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberUniqueName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.memberUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberType, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.memberType, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.dimensionUniqueName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.dimensionUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.hierarchyUniqueName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.hierarchyUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.levelUniqueName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.levelUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberCaption, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.memberCaption, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.description, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.description, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.levelNumber, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.levelNumber, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.childrenCardinality, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.childrenCardinality, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.parentUniqueName, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.parentUniqueName, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.scope, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.scope, $.ig.XmlaNamespace.prototype.rowset));
		$.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.parentLevel, $.ig.XName.prototype.get($.ig.MemberConstants.prototype.parentLevel, $.ig.XmlaNamespace.prototype.rowset));
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var element = node;
		var catalogName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.catalogName)));
		var cubeName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.cubeName)));
		var name = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberName)));
		var uniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberUniqueName)));
		var memberTypeValue = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberType)));
		var dimensionUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.dimensionUniqueName)));
		var hierarchyUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.hierarchyUniqueName)));
		var levelUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.levelUniqueName)));
		var caption = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.memberCaption)));
		var description = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.description)));
		var levelDepth = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.levelNumber)));
		var childrenCardinality = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.childrenCardinality)));
		var parentUniqueName = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.parentUniqueName)));
		var scope = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.MemberXmlTypeSerializer.prototype._xNamesCache.item($.ig.MemberConstants.prototype.scope)));
		var parentLevelValue = $.ig.SerializerUtils.prototype.getText1(element.element($.ig.XName.prototype.get($.ig.MemberConstants.prototype.parentLevel, $.ig.XmlaNamespace.prototype.rowset)));
		var nullValue = $.ig.util.toNullable($.ig.Number.prototype.$type, null);
		return (function () {
			var $ret = new $.ig.Member();
			$ret.catalogName(catalogName);
			$ret.cubeName(cubeName);
			$ret.dimensionUniqueName(dimensionUniqueName);
			$ret.hierarchyUniqueName(hierarchyUniqueName);
			$ret.levelUniqueName(levelUniqueName);
			$ret.levelDepth($.ig.Number.prototype.parseInt(levelDepth));
			$ret.name(name);
			$ret.uniqueName(uniqueName);
			$ret.memberType($.ig.Number.prototype.parseInt(memberTypeValue));
			$ret.caption(caption);
			$ret.childrenCardinality($.ig.Number.prototype.parseInt(childrenCardinality));
			$ret.description(description);
			$ret.parentUniqueName(parentUniqueName);
			$ret.scope(scope == null ? nullValue : $.ig.util.toNullable($.ig.Number.prototype.$type, $.ig.Number.prototype.parseInt(scope)));
			$ret.parentLevel(String.isNullOrEmpty(parentLevelValue) ? 0 : $.ig.Number.prototype.parseInt(parentLevelValue));
			return $ret;
		}());
	}
	,
	$type: new $.ig.Type('MemberXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.Member.prototype.$type)])
}, true);

$.ig.util.defType('ResultXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		if (node.nodeType() != $.ig.XmlNodeType.prototype.element) {
			return null;
		}
		var result = node;
		var namespaceName = result.name().namespaceName();
		switch (namespaceName) {
			case $.ig.XmlaNamespace.prototype.empty: return (function () {
				var $ret = new $.ig.OlapResult();
				$ret.isEmpty(true);
				return $ret;
			}());
			case $.ig.XmlaNamespace.prototype.mdDataset:
				var axisInfo = result.element($.ig.XName.prototype.get("OlapInfo", $.ig.XmlaNamespace.prototype.mdDataset));
				var axesElement = result.element($.ig.XName.prototype.get("Axes", $.ig.XmlaNamespace.prototype.mdDataset));
				var arr = new $.ig.ArraySerializer();
				var axes = arr.deserialize$1($.ig.OlapResultAxis.prototype.$type, axesElement, $.ig.XName.prototype.get("Axis", $.ig.XmlaNamespace.prototype.mdDataset), new $.ig.AxisXmlTypeSerializer());
				var cellData = result.element($.ig.XName.prototype.get("CellData", $.ig.XmlaNamespace.prototype.mdDataset));
				var cells = arr.deserialize$1($.ig.OlapResultCell.prototype.$type, cellData, $.ig.XName.prototype.get("Cell", $.ig.XmlaNamespace.prototype.mdDataset), new $.ig.CellXmlTypeSerializer());
				return (function () {
					var $ret = new $.ig.OlapResult();
					$ret.axes(axes);
					$ret.cells(cells);
					return $ret;
				}());
		}
		return null;
	}
	,
	$type: new $.ig.Type('ResultXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.OlapResult.prototype.$type)])
}, true);

$.ig.util.defType('SerializerUtils', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getText1: function (element) {
		if (element != null) {
			return element.value();
		} else {
			return null;
		}
	}
	,
	getText: function (attribute) {
		if (attribute != null) {
			return attribute.value();
		} else {
			return null;
		}
	}
	,
	getBoolean: function (element) {
		var value = $.ig.SerializerUtils.prototype.getText1(element).toLowerCase();
		return value == "true";
	}
	,
	getDateTime: function (element) {
		if (element != null && !String.isNullOrEmpty(element.value())) {
			return $.ig.SerializerUtils.prototype.parseDate(element.value());
		} else {
			return $.ig.SerializerUtils.prototype.getDefaultDate();
		}
	}
	,
	parseDate: function (dateString) {
		var dateString_ = dateString;
		var date;
		date = (function (text) {
	var match = /^\s*(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(.*)$/.exec(text);
	if (!match) {
		throw 'Invalid textual date representation.';
	}

    var year = parseInt(match[1], 10);
    var month = parseInt(match[2], 10) - 1; //javascript months start from 0
    var day = parseInt(match[3], 10);
    var hours = parseInt(match[4], 10);
    var minutes = parseInt(match[5], 10);
    var seconds = parseInt(match[6], 10);
	
	return new Date(year, month, day, hours, minutes, seconds);
})(dateString_);;
		return date;
	}
	,
	getDefaultDate: function () {
		var date;
		date = new Date(2000, 0, 1);
		return date;
	}
	,
	$type: new $.ig.Type('SerializerUtils', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaConstants', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('XmlaConstants', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapFault', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_faultCode: null,
	faultCode: function (value) {
		if (arguments.length === 1) {
			this._faultCode = value;
			return value;
		} else {
			return this._faultCode;
		}
	}
	,
	_faultString: null,
	faultString: function (value) {
		if (arguments.length === 1) {
			this._faultString = value;
			return value;
		} else {
			return this._faultString;
		}
	}
	,
	_faultActor: null,
	faultActor: function (value) {
		if (arguments.length === 1) {
			this._faultActor = value;
			return value;
		} else {
			return this._faultActor;
		}
	}
	,
	_faultDetail: null,
	faultDetail: function (value) {
		if (arguments.length === 1) {
			this._faultDetail = value;
			return value;
		} else {
			return this._faultDetail;
		}
	}
	,
	$type: new $.ig.Type('XmlaSoapFault', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapFaultXmlTypeSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (node) {
		var faultElement = node;
		var fault = new $.ig.XmlaSoapFault();
		var en = faultElement.elements().getEnumerator();
		while (en.moveNext()) {
			var xElement = en.current();
			switch (xElement.name().localName()) {
				case "faultcode":
					fault.faultCode($.ig.SerializerUtils.prototype.getText1(xElement));
					break;
				case "faultstring":
					fault.faultString($.ig.SerializerUtils.prototype.getText1(xElement));
					break;
				case "faultactor":
					fault.faultActor($.ig.SerializerUtils.prototype.getText1(xElement));
					break;
			}
		}
		var detail = faultElement.element($.ig.XName.prototype.get("detail", ""));
		if (detail != null) {
			var errors = detail.elements1($.ig.XName.prototype.get("Error", ""));
			var errorsList = new $.ig.List$1($.ig.FaultError.prototype.$type, 0);
			var errorXmlSerializer = new $.ig.ErrorXmlTypeSerializer();
			var en1 = errors.getEnumerator();
			while (en1.moveNext()) {
				var error = en1.current();
				var e = errorXmlSerializer.deserialize(error);
				errorsList.add(e);
			}
			fault.faultDetail((function () {
				var $ret = new $.ig.FaultDetail();
				$ret.error(errorsList.toArray());
				return $ret;
			}()));
		}
		return fault;
	}
	,
	$type: new $.ig.Type('XmlaSoapFaultXmlTypeSerializer', $.ig.Object.prototype.$type, [$.ig.IXmlTypeSerializer$1.prototype.$type.specialize($.ig.XmlaSoapFault.prototype.$type)])
}, true);

$.ig.util.defType('IXmlaMethodResult', 'Object', {
	$type: new $.ig.Type('IXmlaMethodResult', null)
}, true);

$.ig.util.defType('XmlaSoapMethodResult', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	__returnField: null,
	returnElement: function (value) {
		if (arguments.length === 1) {
			this.__returnField = value;
			return value;
		} else {
			return this.__returnField;
		}
	}
	,
	_sessionId: null,
	sessionId: function (value) {
		if (arguments.length === 1) {
			this._sessionId = value;
			return value;
		} else {
			return this._sessionId;
		}
	}
	,
	$type: new $.ig.Type('XmlaSoapMethodResult', $.ig.Object.prototype.$type, [$.ig.IXmlaMethodResult.prototype.$type])
}, true);

$.ig.util.defType('DiscoverResponseResult', 'XmlaSoapMethodResult', {
	init: function () {
		$.ig.XmlaSoapMethodResult.prototype.init.call(this);
	},
	$type: new $.ig.Type('DiscoverResponseResult', $.ig.XmlaSoapMethodResult.prototype.$type)
}, true);

$.ig.util.defType('RequestCompletedEventArgs$1', 'AsyncCompletedEventArgs', {
	$t: null,
	__result: null,
	init: function ($t, result, exception, cancelled, userState) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.AsyncCompletedEventArgs.prototype.init.call(this, exception, cancelled, userState);
		this.__result = result;
	},
	result: function () {
		$.ig.AsyncCompletedEventArgs.prototype.raiseExceptionIfNecessary.call(this);
		return this.__result;
	}
	,
	$type: new $.ig.Type('RequestCompletedEventArgs$1', $.ig.AsyncCompletedEventArgs.prototype.$type)
}, true);

$.ig.util.defType('ExecuteResponseResult', 'XmlaSoapMethodResult', {
	init: function () {
		$.ig.XmlaSoapMethodResult.prototype.init.call(this);
	},
	$type: new $.ig.Type('ExecuteResponseResult', $.ig.XmlaSoapMethodResult.prototype.$type)
}, true);

$.ig.util.defType('FaultDetail', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_error: null,
	error: function (value) {
		if (arguments.length === 1) {
			this._error = value;
			return value;
		} else {
			return this._error;
		}
	}
	,
	$type: new $.ig.Type('FaultDetail', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IXmlaSoapMethod', 'Object', {
	$type: new $.ig.Type('IXmlaSoapMethod', null)
}, true);

$.ig.util.defType('XmlaNamespace', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('XmlaNamespace', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaQueryProperty', 'Object', {
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
	init1: function (initNumber, name, value) {
		$.ig.Object.prototype.init.call(this);
		this.propertyName(name);
		this.value(value);
	},
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
	_value: null,
	value: function (value) {
		if (arguments.length === 1) {
			this._value = value;
			return value;
		} else {
			return this._value;
		}
	}
	,
	toString: function () {
		return String.concat(this.propertyName(), " = ", this.value());
	}
	,
	$type: new $.ig.Type('XmlaQueryProperty', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaPropertiesCollection', 'Object', {
	__internalDictionary: null,
	__itemsMap: null,
	__items: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__internalDictionary = new $.ig.Dictionary$2(String, $.ig.XmlaQueryProperty.prototype.$type, 0);
		this.__itemsMap = new $.ig.Dictionary$2(String, $.ig.Number.prototype.$type, 0);
		this.__items = new $.ig.List$1($.ig.XmlaQueryProperty.prototype.$type, 0);
	},
	tryGetValue: function (name, value) {
		var $self = this;
		return {
			ret: (function () { var $ret = $self.__internalDictionary.tryGetValue(name, value); value = $ret.p1; return $ret.ret; }()),
			p1: value
		};
	}
	,
	contains1: function (name) {
		return this.__internalDictionary.containsKey(name);
	}
	,
	indexOf: function (item) {
		return this.__items.indexOf(item);
	}
	,
	insert: function (index, item) {
		this.__internalDictionary.add(item.propertyName(), item);
		this.__items.insert(index, item);
	}
	,
	removeAt: function (index) {
		if (index < this.__items.count()) {
			var item = this.__items.item(index);
			if (this.__internalDictionary.remove(item.propertyName())) {
				this.__items.removeAt(index);
			}
		}
	}
	,
	item: function (index, value) {
		if (arguments.length === 2) {
			var item = this.__items.item(index);
			this.__internalDictionary.remove(item.propertyName());
			this.__internalDictionary.add(value.propertyName(), value);
			this.__items.item(index, value);
			return value;
		} else {
			return this.__items.item(index);
		}
	}
	,
	add$1: function (item) {
		this.__internalDictionary.add(item.propertyName(), item);
		this.__items.add(item);
	}
	,
	clear: function () {
		this.__items.clear();
		this.__internalDictionary.clear();
	}
	,
	contains: function (item) {
		return this.__items.contains(item);
	}
	,
	copyTo$1: function (array, arrayIndex) {
		this.__items.copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.__items.count();
	}
	,
	isReadOnly: function () {
		return this.__items.isReadOnly();
	}
	,
	remove: function (item) {
		if (this.__items.remove(item)) {
			return this.__internalDictionary.remove(item.propertyName());
		}
		return false;
	}
	,
	getEnumerator: function () {
		return this.__items.getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.__items.getEnumerator();
	}
	,
	add: function (value) {
		this.add$1(value);
		return this.count() - 1;
	}
	,
	isFixedSize: function () {
		return false;
	}
	,
	copyTo: function (array, index) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	isSynchronized: function () {
		return (this.__items).isSynchronized();
	}
	,
	syncRoot: function () {
		return (this.__items).syncRoot();
	}
	,
	$type: new $.ig.Type('XmlaPropertiesCollection', $.ig.Object.prototype.$type, [$.ig.IList$1.prototype.$type.specialize($.ig.XmlaQueryProperty.prototype.$type), $.ig.IList.prototype.$type])
}, true);

$.ig.util.defType('XmlaSoapMessageBuilder', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_xmlaMethod: null,
	xmlaMethod: function (value) {
		if (arguments.length === 1) {
			this._xmlaMethod = value;
			return value;
		} else {
			return this._xmlaMethod;
		}
	}
	,
	build: function () {
		if (this.xmlaMethod() == null) {
			throw new $.ig.InvalidOperationException(1, "XmlaMethod is not specified.");
		}
		var envelopeNamespace = $.ig.XNamespace.prototype.get($.ig.XmlaSoapMessageBuilder.prototype._xmlnsEnvelope);
		this.xmlaMethod().construct(envelopeNamespace);
		var envelope = this.createEnvelope();
		return envelope.toString();
	}
	,
	createEnvelope: function () {
		var envelope = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMessageBuilder.prototype._envelopeConstant, $.ig.XmlaSoapMessageBuilder.prototype._xmlnsEnvelope));
		this.addHeader(envelope);
		this.addBody(envelope);
		return envelope;
	}
	,
	addHeader: function (parentElement) {
		var header = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMessageBuilder.prototype._headerConstant, parentElement.name().namespace().namespaceName()));
		parentElement.add(header);
		var en = this.xmlaMethod().headers().getEnumerator();
		while (en.moveNext()) {
			var element = en.current();
			header.add(element);
		}
	}
	,
	addBody: function (parentElement) {
		var body = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMessageBuilder.prototype._bodyConstant, parentElement.name().namespace().namespaceName()));
		parentElement.add(body);
		var methodElement = new $.ig.XElement(1, $.ig.XName.prototype.get(this.xmlaMethod().name(), $.ig.XmlaNamespace.prototype.analysis));
		body.add(methodElement);
		var en = this.xmlaMethod().parameters().getEnumerator();
		while (en.moveNext()) {
			var element = en.current();
			methodElement.add(element);
		}
	}
	,
	$type: new $.ig.Type('XmlaSoapMessageBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapMethod', 'Object', {
	__parameters: null,
	__headers: null,
	__parametersList: null,
	__headersList: null,
	__properties: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__parametersList = new $.ig.List$1($.ig.XElement.prototype.$type, 0);
		this.__headersList = new $.ig.List$1($.ig.XElement.prototype.$type, 0);
		this.__parameters = new $.ig.ReadOnlyCollection$1($.ig.XElement.prototype.$type, 1, this.__parametersList);
		this.__headers = new $.ig.ReadOnlyCollection$1($.ig.XElement.prototype.$type, 1, this.__headersList);
		this.__properties = new $.ig.XmlaPropertiesCollection();
	},
	properties: function () {
		return this.__properties;
	}
	,
	name: function () {
	}
	,
	parameters: function () {
		return this.__parameters;
	}
	,
	headers: function () {
		return this.__headers;
	}
	,
	parametersList: function () {
		return this.__parametersList;
	}
	,
	headersList: function () {
		return this.__headersList;
	}
	,
	construct: function (headerNamespace) {
	}
	,
	mergeProperties: function (properties) {
		var $self = this;
		if (properties == null) {
			return;
		}
		var en = properties.getEnumerator();
		while (en.moveNext()) {
			var xmlaQueryProperty = en.current();
			var propInstance;
			if ((function () { var $ret = $self.properties().tryGetValue(xmlaQueryProperty.propertyName(), propInstance); propInstance = $ret.p1; return $ret.ret; }())) {
				propInstance.value(xmlaQueryProperty.value());
			} else {
				this.properties().add$1(xmlaQueryProperty);
			}
		}
	}
	,
	$type: new $.ig.Type('XmlaSoapMethod', $.ig.Object.prototype.$type, [$.ig.IXmlaSoapMethod.prototype.$type])
}, true);

$.ig.util.defType('XmlaSoapMethodDiscover', 'XmlaSoapMethod', {
	__restrictions: null,
	init: function (requestType) {
		$.ig.XmlaSoapMethod.prototype.init.call(this);
		this.headerType($.ig.XmlaSoapMessageHeader.prototype.none);
		this.requestType(requestType);
		this.__restrictions = new $.ig.Dictionary$2(String, $.ig.Object.prototype.$type, 0);
	},
	restrictions: function () {
		return this.__restrictions;
	}
	,
	_requestType: null,
	requestType: function (value) {
		if (arguments.length === 1) {
			this._requestType = value;
			return value;
		} else {
			return this._requestType;
		}
	}
	,
	name: function () {
		return $.ig.XmlaSoapMethodDiscover.prototype._methodName;
	}
	,
	_headerType: 0,
	headerType: function (value) {
		if (arguments.length === 1) {
			this._headerType = value;
			return value;
		} else {
			return this._headerType;
		}
	}
	,
	_sessionId: null,
	sessionId: function (value) {
		if (arguments.length === 1) {
			this._sessionId = value;
			return value;
		} else {
			return this._sessionId;
		}
	}
	,
	construct: function (headerNamespace) {
		var analysis = $.ig.XNamespace.prototype.get($.ig.XmlaNamespace.prototype.analysis);
		var requestType = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethodDiscover.prototype._requestTypeConstant, analysis.namespaceName()));
		requestType.value(this.requestType());
		var properties = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethod.prototype.propertiesConstant, $.ig.XmlaNamespace.prototype.analysis));
		var propertyList = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethod.prototype.propertyListConstant, properties.name().namespace().namespaceName()));
		properties.add(propertyList);
		var restrictions = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethodDiscover.prototype._restrictionsConstant, $.ig.XmlaNamespace.prototype.analysis));
		var restrictionList = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethodDiscover.prototype._restrictionListConstant, restrictions.name().namespace().namespaceName()));
		restrictions.add(restrictionList);
		var en = this.properties().getEnumerator();
		while (en.moveNext()) {
			var property = en.current();
			propertyList.add(new $.ig.XElement(2, $.ig.XName.prototype.get(property.propertyName(), $.ig.XmlaNamespace.prototype.analysis), property.value()));
		}
		var en1 = this.__restrictions.getEnumerator();
		while (en1.moveNext()) {
			var restriction = en1.current();
			restrictionList.add(new $.ig.XElement(2, $.ig.XName.prototype.get(restriction.key(), ""), restriction.value()));
		}
		this.parametersList().add(requestType);
		this.parametersList().add(restrictions);
		this.parametersList().add(properties);
	}
	,
	$type: new $.ig.Type('XmlaSoapMethodDiscover', $.ig.XmlaSoapMethod.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapMethodExecute', 'XmlaSoapMethod', {
	init: function (statement) {
		$.ig.XmlaSoapMethod.prototype.init.call(this);
		this.statement(statement);
	},
	name: function () {
		return $.ig.XmlaSoapMethodExecute.prototype._methodName;
	}
	,
	_statement: null,
	statement: function (value) {
		if (arguments.length === 1) {
			this._statement = value;
			return value;
		} else {
			return this._statement;
		}
	}
	,
	construct: function (headerNamespace) {
		var analysis = $.ig.XNamespace.prototype.get($.ig.XmlaNamespace.prototype.analysis);
		var command = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethodExecute.prototype._commandConstant, analysis.namespaceName()));
		var statement = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethodExecute.prototype._statementConstant, analysis.namespaceName()));
		statement.value(this.statement());
		command.add(statement);
		var properties = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethod.prototype.propertiesConstant, $.ig.XmlaNamespace.prototype.analysis));
		var propertyList = new $.ig.XElement(1, $.ig.XName.prototype.get($.ig.XmlaSoapMethod.prototype.propertyListConstant, properties.name().namespace().namespaceName()));
		properties.add(propertyList);
		var en = this.properties().getEnumerator();
		while (en.moveNext()) {
			var property = en.current();
			propertyList.add(new $.ig.XElement(2, $.ig.XName.prototype.get(property.propertyName(), $.ig.XmlaNamespace.prototype.analysis), property.value()));
		}
		this.parametersList().add(command);
		this.parametersList().add(properties);
	}
	,
	$type: new $.ig.Type('XmlaSoapMethodExecute', $.ig.XmlaSoapMethod.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapWebClient', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		$.ig.XmlaSoapWebClient.prototype.__registeredPrefixes = new $.ig.List$1(String, 0);
	},
	$type: new $.ig.Type('XmlaSoapWebClient', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaSoapWebClient$1', 'XmlaSoapWebClient', {
	$t: null,
	__webClient: null,
	__requestCompleted: false,
	init: function ($t, serverUri, encoding) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.XmlaSoapWebClient.prototype.init.call(this);
		this.__webClient = new $.ig.WebClient();
		this.__webClient.encoding(encoding);
		var $t = this.__webClient;
		$t.uploadStringCompleted = $.ig.Delegate.prototype.combine($t.uploadStringCompleted, this.requestStringAsyncCompleted.runOn(this));
		this.serverUri(serverUri);
	},
	requestCompleted: null,
	_credentials: null,
	credentials: function (value) {
		if (arguments.length === 1) {
			this._credentials = value;
			return value;
		} else {
			return this._credentials;
		}
	}
	,
	_serverUri: null,
	serverUri: function (value) {
		if (arguments.length === 1) {
			this._serverUri = value;
			return value;
		} else {
			return this._serverUri;
		}
	}
	,
	isCompleted: function () {
		return this.__requestCompleted;
	}
	,
	requestAsync: function (xmlaSoapMethod) {
		this.requestAsync1(xmlaSoapMethod, null);
	}
	,
	requestAsync1: function (xmlaSoapMethod, userData) {
		var messageBuilder = new $.ig.XmlaSoapMessageBuilder();
		messageBuilder.xmlaMethod(xmlaSoapMethod);
		var xmlaRequest = messageBuilder.build();
		if (this.credentials() != null) {
			this.__webClient.credentials(this.credentials());
		} else {
			this.__webClient.credentials(null);
		}
		this.__webClient.headers().item("UserAgent", "XmlaClient");
		this.__webClient.headers().item("SOAPAction", "urn:schemas-microsoft-com:xml-analysis:Execute");
		this.__webClient.headers().item("Content-Type", "text/xml");
		this.__webClient.uploadStringAsync(this.serverUri(), "POST", xmlaRequest, userData);
	}
	,
	onRequestCompleted: function (args) {
		if (this.requestCompleted != null) {
			this.requestCompleted(this, args);
		}
	}
	,
	requestStringAsyncCompleted: function (sender, e) {
		this.__requestCompleted = true;
		var output = $.ig.XDocument.prototype.parse(e.result());
		this.onRequestCompletedImpl(output, e.error(), e.cancelled(), e.userState());
	}
	,
	onRequestCompletedImpl: function (resultDocument, errorObj, cancelled, userState) {
		var result = null;
		var faultException = null;
		var error = null;
		if (!cancelled && errorObj == null) {
			try {
				var envelope = resultDocument.element($.ig.XName.prototype.get("Envelope", "http://schemas.xmlsoap.org/soap/envelope/"));
				var body = envelope.element($.ig.XName.prototype.get("Body", "http://schemas.xmlsoap.org/soap/envelope/"));
				var faultElement = body.element($.ig.XName.prototype.get("Fault", "http://schemas.xmlsoap.org/soap/envelope/"));
				if (faultElement != null) {
					var faultSerializer = new $.ig.XmlaSoapFaultXmlTypeSerializer();
					var fault = faultSerializer.deserialize(faultElement);
					faultException = new $.ig.FaultException$1($.ig.XmlaSoapFault.prototype.$type, fault, new $.ig.FaultReason(fault.faultString()), new $.ig.FaultCode(fault.faultCode()), null);
				} else {
					if (this.$t == $.ig.DiscoverResponseResult.prototype.$type) {
						var discoverResponse = body.element($.ig.XName.prototype.get("DiscoverResponse", $.ig.XmlaNamespace.prototype.analysis));
						var returnElement = discoverResponse.element($.ig.XName.prototype.get("return", $.ig.XmlaNamespace.prototype.analysis));
						var root = returnElement.element($.ig.XName.prototype.get("root", $.ig.XmlaNamespace.prototype.rowset));
						var discoverResponseResult = new $.ig.DiscoverResponseResult();
						discoverResponseResult.returnElement(root);
						result = discoverResponseResult;
					}
					if (this.$t == $.ig.ExecuteResponseResult.prototype.$type) {
						var response = body.element($.ig.XName.prototype.get("ExecuteResponse", $.ig.XmlaNamespace.prototype.analysis));
						var returnElement1 = response.element($.ig.XName.prototype.get("return", $.ig.XmlaNamespace.prototype.analysis));
						var root1 = returnElement1.element($.ig.XName.prototype.get("root", $.ig.XmlaNamespace.prototype.mdDataset));
						var executeResponseResult = new $.ig.ExecuteResponseResult();
						executeResponseResult.returnElement(root1);
						result = executeResponseResult;
					}
				}
			}
			catch (e_) {
				error = e_;
			}
		}
		if (error == null) {
			error = errorObj != null ? errorObj : faultException;
		}
		var args = new $.ig.RequestCompletedEventArgs$1(this.$t, result, error, cancelled, userState);
		this.onRequestCompleted(args);
	}
	,
	$type: new $.ig.Type('XmlaSoapWebClient$1', $.ig.XmlaSoapWebClient.prototype.$type)
}, true);

$.ig.util.defType('IMdxExpression', 'Object', {
	$type: new $.ig.Type('IMdxExpression', null)
}, true);

$.ig.util.defType('IMdxElement', 'Object', {
	$type: new $.ig.Type('IMdxElement', null, [$.ig.IMdxExpression.prototype.$type])
}, true);

$.ig.util.defType('IAxisFilterElement', 'Object', {
	$type: new $.ig.Type('IAxisFilterElement', null, [$.ig.IMdxElement.prototype.$type])
}, true);

$.ig.util.defType('IMdxAxis', 'Object', {
	$type: new $.ig.Type('IMdxAxis', null, [$.ig.IMdxExpression.prototype.$type])
}, true);

$.ig.util.defType('IMdxAxisProvider', 'Object', {
	$type: new $.ig.Type('IMdxAxisProvider', null)
}, true);

$.ig.util.defType('IMdxCollectionElement', 'Object', {
	$type: new $.ig.Type('IMdxCollectionElement', null, [$.ig.IMdxElement.prototype.$type])
}, true);

$.ig.util.defType('IMdxDimensionAxisProvider', 'Object', {
	$type: new $.ig.Type('IMdxDimensionAxisProvider', null, [$.ig.IMdxAxisProvider.prototype.$type])
}, true);

$.ig.util.defType('IMdxFilterInfo', 'Object', {
	$type: new $.ig.Type('IMdxFilterInfo', null)
}, true);

$.ig.util.defType('IMdxExtendedFilterInfo', 'Object', {
	$type: new $.ig.Type('IMdxExtendedFilterInfo', null, [$.ig.IMdxFilterInfo.prototype.$type])
}, true);

$.ig.util.defType('IMdxItemElement', 'Object', {
	$type: new $.ig.Type('IMdxItemElement', null, [$.ig.IMdxElement.prototype.$type])
}, true);

$.ig.util.defType('IMdxSet', 'Object', {
	$type: new $.ig.Type('IMdxSet', null, [$.ig.IMdxExpression.prototype.$type])
}, true);

$.ig.util.defType('MdxAxis', 'Object', {
	__setsList: null,
	__sets: null,
	__isExpressionReady: false,
	__expression: null,
	__axisIndex: 0,
	__axisNames: null,
	init: function (initNumber, axisIndex) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.MdxAxis.prototype.init1.call(this, 1, axisIndex, new $.ig.MdxDimensionAxisSettings());
	},
	init1: function (initNumber, axisIndex, settings) {
		this.__axisNames = [ "COLUMNS", "ROWS", "PAGES" ];
		$.ig.Object.prototype.init.call(this);
		this.axisIndex(axisIndex);
		this.mdxSettings(settings != null ? settings : new $.ig.MdxDimensionAxisSettings());
		this.__setsList = new $.ig.List$1($.ig.IMdxSet.prototype.$type, 0);
		this.__sets = new $.ig.ReadOnlyCollection$1($.ig.IMdxSet.prototype.$type, 1, this.__setsList);
	},
	axisIndex: function (value) {
		if (arguments.length === 1) {
			if (this.__axisIndex != value) {
				this.__axisIndex = value;
				this.__isExpressionReady = false;
			}
			return value;
		} else {
			return this.__axisIndex;
		}
	}
	,
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	mdxDimensionAxisSettings: function () {
		return $.ig.util.cast($.ig.MdxDimensionAxisSettings.prototype.$type, this.mdxSettings());
	}
	,
	sets: function () {
		return this.__sets;
	}
	,
	mdxExpression: function () {
		if (!this.__isExpressionReady) {
			this.__expression = this.getMdxExpression();
			this.__isExpressionReady = true;
		}
		return this.__expression;
	}
	,
	rebuildExpression: function () {
		this.__expression = this.getMdxExpression();
		this.__isExpressionReady = true;
	}
	,
	getMdxSets: function () {
		var d__ = new $.ig.MdxAxis___GetMdxSets__IteratorClass(-2);
		d__.__4__this = this;
		return d__;
	}
	,
	addSet: function (mdxSet) {
		this.__setsList.add(mdxSet);
		this.__isExpressionReady = false;
	}
	,
	getMdxExpression: function () {
		var stringBuilder = new $.ig.StringBuilder(0);
		var addComma = false;
		var en = this.sets().getEnumerator();
		while (en.moveNext()) {
			var mdxSet = en.current();
			if (!mdxSet.isEnabled()) {
				continue;
			}
			if (addComma) {
				stringBuilder.append5(",");
				stringBuilder.append5($.ig.Environment.prototype.newLine());
			}
			stringBuilder.append5(mdxSet.mdxExpression());
			addComma = true;
		}
		if (this.sets().count() > 1) {
			stringBuilder.insert1(0, "{");
			stringBuilder.append5("}");
		}
		this.addDisctinct(stringBuilder, this.mdxDimensionAxisSettings());
		this.addNonEmpty(stringBuilder, this.mdxDimensionAxisSettings());
		this.addDimensionProperties(stringBuilder, this.mdxDimensionAxisSettings());
		this.addAxisName(stringBuilder);
		return stringBuilder.toString();
	}
	,
	addDisctinct: function (stringBuilder, settings) {
		if (settings.distinct()) {
			stringBuilder.insert1(0, "Distinct(");
			stringBuilder.append5(")");
		}
	}
	,
	addNonEmpty: function (stringBuilder, settings) {
		if (settings.nonEmpty()) {
			stringBuilder.insert1(0, $.ig.Environment.prototype.newLine());
			stringBuilder.insert1(0, "NON EMPTY");
		}
	}
	,
	addDimensionProperties: function (stringBuilder, settings) {
		if (settings.addDimensionProperties()) {
			stringBuilder.append5($.ig.Environment.prototype.newLine());
			stringBuilder.append5("DIMENSION PROPERTIES");
			var properties = settings.dimensionProperties();
			var addComma = false;
			var en = properties.getEnumerator();
			while (en.moveNext()) {
				var property = en.current();
				stringBuilder.append5($.ig.Environment.prototype.newLine());
				if (addComma) {
					stringBuilder.append5(",");
				}
				stringBuilder.append5(property);
				addComma = true;
			}
		}
	}
	,
	addAxisName: function (stringBuilder) {
		if (this.axisIndex() < 2) {
			stringBuilder.append5($.ig.Environment.prototype.newLine());
			stringBuilder.append5(String.concat("ON ", this.__axisNames[this.axisIndex()]));
		}
	}
	,
	$type: new $.ig.Type('MdxAxis', $.ig.Object.prototype.$type, [$.ig.IMdxAxis.prototype.$type])
}, true);

$.ig.util.defType('MdxAxisFilterElement', 'Object', {
	__filterCollection: null,
	__singleFilterElements: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__singleFilterElements = new $.ig.List$1($.ig.IMdxElement.prototype.$type, 0);
		this.singleFilterElements(new $.ig.ReadOnlyCollection$1($.ig.IMdxElement.prototype.$type, 1, this.__singleFilterElements));
		this.__filterCollection = new $.ig.MdxElementCollectionElement();
	},
	mdxExpression: function () {
		if (this.__filterCollection.collectionElements().count() > 1) {
			this.__filterCollection.wrapExpressionWithBrackets([ "(", ")" ]);
		}
		return this.__filterCollection.mdxExpression();
	}
	,
	_singleFilterElements: null,
	singleFilterElements: function (value) {
		if (arguments.length === 1) {
			this._singleFilterElements = value;
			return value;
		} else {
			return this._singleFilterElements;
		}
	}
	,
	filterCollection: function () {
		return this.__filterCollection.collectionElements();
	}
	,
	rebuildExpression: function () {
		this.__filterCollection.rebuildExpression();
	}
	,
	addFilterPart: function (filterCollection, addToSingleCollection) {
		if (filterCollection.collectionElements().count() > 0) {
			if (filterCollection.collectionElements().count() == 1 && addToSingleCollection) {
				this.__singleFilterElements.add(filterCollection.collectionElements().item(0));
				return;
			}
			this.__filterCollection.addElement(filterCollection);
		}
	}
	,
	elementType: function () {
		return $.ig.MdxElementType.prototype.filter;
	}
	,
	$type: new $.ig.Type('MdxAxisFilterElement', $.ig.Object.prototype.$type, [$.ig.IAxisFilterElement.prototype.$type])
}, true);

$.ig.util.defType('MdxCalculatedMembersCache', 'Object', {
	init: function () {
		this.__registeredMembers = new $.ig.List$1(String, 0);
		this.__memberExpression = new $.ig.Dictionary$2(String, String, 0);
		$.ig.Object.prototype.init.call(this);
	},
	__registeredMembers: null,
	__memberExpression: null,
	getCalculatedMembers: function (calculatedMembersCache) {
		var stringBuilder = new $.ig.StringBuilder(0);
		var en = calculatedMembersCache.getMemberAliasExpressionPairs().getEnumerator();
		while (en.moveNext()) {
			var aliasExpressionPair = en.current();
			$.ig.MdxCalculatedMembersCache.prototype.addCalculatedMemberWithClause(stringBuilder, aliasExpressionPair.key(), aliasExpressionPair.value());
		}
		return stringBuilder.toString();
	}
	,
	addMember: function (uniqueName, queryAlias, expression) {
		if (!this.__registeredMembers.contains(uniqueName)) {
			this.__registeredMembers.add(uniqueName);
		}
		if (!this.__memberExpression.containsKey(queryAlias)) {
			this.__memberExpression.add(queryAlias, expression);
		}
	}
	,
	getMemberAliasExpressionPairs: function () {
		var d__ = new $.ig.MdxCalculatedMembersCache___GetMemberAliasExpressionPairs__IteratorClass(-2);
		d__.__4__this = this;
		return d__;
	}
	,
	checkCacheMember: function (uniqueName) {
		return this.__registeredMembers.contains(uniqueName);
	}
	,
	addCalculatedMemberWithClause: function (stringBuilder, alias, expression) {
		stringBuilder.append5(String.concat("MEMBER ", alias, " AS", $.ig.Environment.prototype.newLine()));
		stringBuilder.append5(expression);
		stringBuilder.append5($.ig.Environment.prototype.newLine());
	}
	,
	$type: new $.ig.Type('MdxCalculatedMembersCache', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MdxElement', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.elementType($.ig.MdxElementType.prototype.userDefined);
	},
	mdxExpression: function () {
	}
	,
	_elementType: 0,
	elementType: function (value) {
		if (arguments.length === 1) {
			this._elementType = value;
			return value;
		} else {
			return this._elementType;
		}
	}
	,
	_sourceItemExpression: null,
	sourceItemExpression: function (value) {
		if (arguments.length === 1) {
			this._sourceItemExpression = value;
			return value;
		} else {
			return this._sourceItemExpression;
		}
	}
	,
	rebuildExpression: function () {
	}
	,
	$type: new $.ig.Type('MdxElement', $.ig.Object.prototype.$type, [$.ig.IMdxItemElement.prototype.$type])
}, true);

$.ig.util.defType('MdxSingleElement', 'MdxElement', {
	init: function (initNumber, elementExpression, elementUniqueName, memberDepth) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.MdxSingleElement.prototype.init1.call(this, 1, elementExpression, elementUniqueName, memberDepth, $.ig.MdxElementType.prototype.member);
	},
	init1: function (initNumber, elementExpression, elementUniqueName, memberDepth, elementType) {
		$.ig.MdxElement.prototype.init.call(this);
		this.memberDepth(memberDepth);
		switch (elementType) {
			case $.ig.MdxElementType.prototype.member:
			case $.ig.MdxElementType.prototype.calculated:
				this.sourceItemExpression(elementExpression);
				this.elementUniqueName(elementUniqueName);
				this.elementType(elementType);
				break;
			default: throw new $.ig.ArgumentException(2, "Element type could be MdxElementType.Member or MdxElementType.Calculated", "elementType");
		}
	},
	mdxExpression: function () {
		return this.sourceItemExpression();
	}
	,
	_elementUniqueName: null,
	elementUniqueName: function (value) {
		if (arguments.length === 1) {
			this._elementUniqueName = value;
			return value;
		} else {
			return this._elementUniqueName;
		}
	}
	,
	_memberDepth: 0,
	memberDepth: function (value) {
		if (arguments.length === 1) {
			this._memberDepth = value;
			return value;
		} else {
			return this._memberDepth;
		}
	}
	,
	$type: new $.ig.Type('MdxSingleElement', $.ig.MdxElement.prototype.$type)
}, true);

$.ig.util.defType('MdxDimensionMember', 'MdxSingleElement', {
	init: function (elementExpression, elementUniqueName, memberDepth, parentHierarchyUniqueName, parent) {
		$.ig.MdxSingleElement.prototype.init.call(this, 0, elementExpression, elementUniqueName, memberDepth);
		this.parentHierarchyUniqueName(parentHierarchyUniqueName);
		this.parent(parent);
	},
	_parentHierarchyUniqueName: null,
	parentHierarchyUniqueName: function (value) {
		if (arguments.length === 1) {
			this._parentHierarchyUniqueName = value;
			return value;
		} else {
			return this._parentHierarchyUniqueName;
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
	$type: new $.ig.Type('MdxDimensionMember', $.ig.MdxSingleElement.prototype.$type)
}, true);

$.ig.util.defType('MdxChildrenCollectionElement', 'MdxDimensionMember', {
	init: function (elementExpression, elementUniqueName, memberDepth, parentHierarchyUniqueName, parent) {
		$.ig.MdxDimensionMember.prototype.init.call(this, elementExpression, elementUniqueName, memberDepth, parentHierarchyUniqueName, parent);
	},
	mdxExpression: function () {
		return String.concat(this.sourceItemExpression(), ".Children");
	}
	,
	$type: new $.ig.Type('MdxChildrenCollectionElement', $.ig.MdxDimensionMember.prototype.$type)
}, true);

$.ig.util.defType('MdxElementCollectionElement', 'Object', {
	__collectionElementsList: null,
	__isExpressionReady: false,
	__expression: null,
	__wrapExpressionWithBrackets: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__collectionElementsList = new $.ig.List$1($.ig.IMdxElement.prototype.$type, 0);
		this.collectionElements(new $.ig.ReadOnlyCollection$1($.ig.IMdxElement.prototype.$type, 1, this.__collectionElementsList));
	},
	wrapExpressionWithBrackets: function (value) {
		if (arguments.length === 1) {
			if (this.__wrapExpressionWithBrackets != value) {
				this.__isExpressionReady = false;
				this.__wrapExpressionWithBrackets = value;
			}
			return value;
		} else {
			return this.__wrapExpressionWithBrackets;
		}
	}
	,
	_collectionElements: null,
	collectionElements: function (value) {
		if (arguments.length === 1) {
			this._collectionElements = value;
			return value;
		} else {
			return this._collectionElements;
		}
	}
	,
	elementType: function () {
		return $.ig.MdxElementType.prototype.collection;
	}
	,
	mdxExpression: function () {
		if (!this.__isExpressionReady) {
			this.__expression = this.getMdxExpression();
			this.__isExpressionReady = true;
		}
		return this.__expression;
	}
	,
	rebuildExpression: function () {
		this.__expression = this.getMdxExpression();
		this.__isExpressionReady = true;
	}
	,
	addElement: function (mdxElement) {
		this.__collectionElementsList.add(mdxElement);
		if (this.collectionElements().count() > 1) {
			this.wrapExpressionWithBrackets([ "{", "}" ]);
		}
		this.__isExpressionReady = false;
	}
	,
	getMdxExpression: function () {
		if (this.collectionElements().count() == 0) {
			return String.empty();
		}
		var stringBuilder = new $.ig.StringBuilder(0);
		var addComma = false;
		var en = this.collectionElements().getEnumerator();
		while (en.moveNext()) {
			var filterElementItem = en.current();
			if (addComma) {
				stringBuilder.append5(",");
				stringBuilder.append5($.ig.Environment.prototype.newLine());
			}
			stringBuilder.append5(filterElementItem.mdxExpression());
			addComma = true;
		}
		if (this.wrapExpressionWithBrackets() != null && this.wrapExpressionWithBrackets().length == 2) {
			stringBuilder.insert1(0, this.wrapExpressionWithBrackets()[0]);
			stringBuilder.append5(this.wrapExpressionWithBrackets()[1]);
		}
		return stringBuilder.toString();
	}
	,
	$type: new $.ig.Type('MdxElementCollectionElement', $.ig.Object.prototype.$type, [$.ig.IMdxCollectionElement.prototype.$type])
}, true);

$.ig.util.defType('MdxLevelMembersElement', 'MdxSingleElement', {
	init: function (elementExpression, elementUniqueName, memberDepth) {
		$.ig.MdxSingleElement.prototype.init.call(this, 0, elementExpression, elementUniqueName, memberDepth);
	},
	mdxExpression: function () {
		return String.concat(this.sourceItemExpression(), ".MEMBERS");
	}
	,
	$type: new $.ig.Type('MdxLevelMembersElement', $.ig.MdxSingleElement.prototype.$type)
}, true);

$.ig.util.defType('MdxQueryBuilder', 'Object', {
	__mdxSettings: null,
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.MdxQueryBuilder.prototype.init1.call(this, 1, new $.ig.MdxSettings());
	},
	init1: function (initNumber, settings) {
		$.ig.Object.prototype.init.call(this);
		this.mdxSettings(settings);
	},
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this.__mdxSettings = value;
			return value;
		} else {
			if (this.__mdxSettings == null) {
				this.__mdxSettings = new $.ig.MdxSettings();
			}
			return this.__mdxSettings;
		}
	}
	,
	buildMdxQuery: function (mdxColumnsAxis, mdxRowsAxis, mdxSlicerAxis, calculatedMembersCache, cubeName) {
		var filterInfo = mdxSlicerAxis;
		var withRegion = this.getCalculatedMembers(mdxColumnsAxis, mdxRowsAxis, filterInfo, calculatedMembersCache);
		var where = this.getWhereClause(filterInfo);
		var from = this.getFromClause(filterInfo, cubeName);
		var stringBuilder = new $.ig.StringBuilder(0);
		if (!String.isNullOrEmpty(withRegion)) {
			stringBuilder.append5(String.concat("WITH", $.ig.Environment.prototype.newLine()));
		}
		stringBuilder.append5(withRegion);
		stringBuilder.append5("SELECT");
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		if (mdxColumnsAxis != null) {
			stringBuilder.append5(mdxColumnsAxis.mdxExpression());
		}
		if (mdxRowsAxis != null) {
			if (mdxColumnsAxis == null) {
				mdxRowsAxis.axisIndex(0);
			} else {
				stringBuilder.append5(String.concat(",", $.ig.Environment.prototype.newLine()));
			}
			stringBuilder.append5(mdxRowsAxis.mdxExpression());
		}
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5(from);
		if (!String.isNullOrEmpty(where)) {
			stringBuilder.append5($.ig.Environment.prototype.newLine());
			stringBuilder.append5(where);
		}
		var query = stringBuilder.toString();
		return query;
	}
	,
	getCalculatedMembers: function (mdxColumnsAxis, mdxRowsAxis, filterInfo, calculatedMembersCache) {
		return $.ig.MdxCalculatedMembersCache.prototype.getCalculatedMembers(calculatedMembersCache);
	}
	,
	getFromClause: function (filterInfo, cubeName) {
		var fromClause = String.concat("FROM ", "[", cubeName, "]");
		var extendedFilterInfo = $.ig.util.cast($.ig.IMdxExtendedFilterInfo.prototype.$type, filterInfo);
		fromClause = this.getFilterFromClause(fromClause, filterInfo.filtersFilter(), null);
		if (filterInfo.columnsFilter() != null) {
			fromClause = this.getFilterFromClause(fromClause, filterInfo.columnsFilter(), filterInfo.rowsFilter());
		} else {
			fromClause = this.getFilterFromClause(fromClause, filterInfo.rowsFilter(), null);
		}
		if (extendedFilterInfo != null) {
			fromClause = this.getExtendedFilterInfoFromClause(fromClause, extendedFilterInfo);
		}
		return fromClause;
	}
	,
	getExtendedFilterInfoFromClause: function (fromClause, extendedFilterInfo) {
		if (extendedFilterInfo.filterElements() != null && extendedFilterInfo.filterElements().count() > 0) {
			var en = extendedFilterInfo.filterElements().getEnumerator();
			while (en.moveNext()) {
				var filterElement = en.current();
				var stringBuilder = new $.ig.StringBuilder(0);
				stringBuilder.insert1(0, String.concat([ "FROM", $.ig.Environment.prototype.newLine(), "(", $.ig.Environment.prototype.newLine(), "SELECT", $.ig.Environment.prototype.newLine() ]));
				stringBuilder.append5(String.concat(filterElement.mdxExpression(), " ON COLUMNS", $.ig.Environment.prototype.newLine()));
				stringBuilder.append5(fromClause);
				stringBuilder.append5(this.getWhereClause(extendedFilterInfo));
				stringBuilder.append5(String.concat(")", $.ig.Environment.prototype.newLine()));
				fromClause = stringBuilder.toString();
			}
		}
		return fromClause;
	}
	,
	getFilterFromClause: function (fromClause, filterInfo1, filterInfo2) {
		if (filterInfo1 == null || filterInfo1.filterCollection().count() == 0) {
			return fromClause;
		}
		var stringBuilder = new $.ig.StringBuilder(0);
		stringBuilder.append5("FROM");
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5("(");
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5("SELECT");
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5(filterInfo1.mdxExpression());
		stringBuilder.append5(" ON COLUMNS");
		if (filterInfo2 != null && filterInfo2.filterCollection().count() > 0) {
			stringBuilder.append5($.ig.Environment.prototype.newLine());
			stringBuilder.append5(", ");
			stringBuilder.append5(filterInfo2.mdxExpression());
			stringBuilder.append5(" ON ROWS");
		}
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5(fromClause);
		stringBuilder.append5($.ig.Environment.prototype.newLine());
		stringBuilder.append5(")");
		return stringBuilder.toString();
	}
	,
	getWhereClause: function (filterInfo) {
		var whereClauseCollection = new $.ig.MdxElementCollectionElement();
		if (filterInfo.measureFilter() != null && filterInfo.measureFilter().singleFilterElements().count() == 1) {
			whereClauseCollection.addElement(filterInfo.measureFilter().singleFilterElements().item(0));
		}
		if (filterInfo.filtersFilter() != null) {
			var en = filterInfo.filtersFilter().singleFilterElements().getEnumerator();
			while (en.moveNext()) {
				var singleFilterElement = en.current();
				whereClauseCollection.addElement(singleFilterElement);
			}
		}
		if (whereClauseCollection.collectionElements().count() == 0) {
			return String.empty();
		}
		whereClauseCollection.wrapExpressionWithBrackets([ "(", ")" ]);
		return String.concat("WHERE ", whereClauseCollection.mdxExpression());
	}
	,
	$type: new $.ig.Type('MdxQueryBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MdxSet', 'Object', {
	__elementsList: null,
	__elements: null,
	__isExpressionReady: false,
	__expression: null,
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.MdxSet.prototype.init1.call(this, 1, new $.ig.MdxSetSettings());
		this.__elementsList = new $.ig.List$1($.ig.IMdxElement.prototype.$type, 0);
		this.__elements = new $.ig.ReadOnlyCollection$1($.ig.IMdxElement.prototype.$type, 1, this.__elementsList);
		this.isEnabled(true);
	},
	init1: function (initNumber, mdxSettings) {
		$.ig.Object.prototype.init.call(this);
		this.__elementsList = new $.ig.List$1($.ig.IMdxElement.prototype.$type, 0);
		this.__elements = new $.ig.ReadOnlyCollection$1($.ig.IMdxElement.prototype.$type, 1, this.__elementsList);
		this.isEnabled(true);
		this.mdxSettings(mdxSettings != null ? mdxSettings : new $.ig.MdxSetSettings());
	},
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	_isEnabled: false,
	isEnabled: function (value) {
		if (arguments.length === 1) {
			this._isEnabled = value;
			return value;
		} else {
			return this._isEnabled;
		}
	}
	,
	elements: function () {
		return this.__elements;
	}
	,
	mdxExpression: function () {
		if (!this.__isExpressionReady) {
			this.__expression = this.getMdxExpression();
			this.__isExpressionReady = true;
		}
		return this.__expression;
	}
	,
	rebuildExpression: function () {
		this.__expression = this.getMdxExpression();
		this.__isExpressionReady = true;
	}
	,
	addElement: function (mdxElement) {
		if (this.supportsElementType(mdxElement)) {
			this.__elementsList.add(mdxElement);
			this.__isExpressionReady = false;
		}
	}
	,
	getMdxElements: function () {
		var d__ = new $.ig.MdxSet___GetMdxElements__IteratorClass(-2);
		d__.__4__this = this;
		return d__;
	}
	,
	supportsElementType: function (mdxElement) {
		switch (mdxElement.elementType()) {
			case $.ig.MdxElementType.prototype.memberTree:
			case $.ig.MdxElementType.prototype.member:
			case $.ig.MdxElementType.prototype.calculated:
			case $.ig.MdxElementType.prototype.collection:
				return true;
		}
		return false;
	}
	,
	getMdxExpression: function () {
		$.ig.Debug.prototype.assert1(this.elements().count() > 0, "MdxSet.Elements collection is empty.");
		var stringBuilder = new $.ig.StringBuilder(0);
		var topElement = this.elements().item(0);
		this.addElementExpression(stringBuilder, topElement);
		for (var i = 1; i < this.elements().count(); i++) {
			stringBuilder.insert1(0, "CrossJoin(");
			stringBuilder.insert1(10, $.ig.Environment.prototype.newLine());
			stringBuilder.append5(",");
			stringBuilder.append5($.ig.Environment.prototype.newLine());
			var mdxElement = this.elements().item(i);
			this.addElementExpression(stringBuilder, mdxElement);
			stringBuilder.append5(")");
		}
		return stringBuilder.toString();
	}
	,
	addElementExpression: function (stringBuilder, mdxElement) {
		var partBuilder = new $.ig.StringBuilder(0);
		switch (mdxElement.elementType()) {
			case $.ig.MdxElementType.prototype.calculated:
				var mdxCalculatedElement = mdxElement;
				var memberUniqueName = mdxCalculatedElement.elementUniqueName();
				partBuilder.append5("{");
				partBuilder.append5(memberUniqueName);
				partBuilder.append5("}");
				break;
			case $.ig.MdxElementType.prototype.member:
				partBuilder.append5("{");
				partBuilder.append5(mdxElement.mdxExpression());
				partBuilder.append5("}");
				break;
			case $.ig.MdxElementType.prototype.collection:
			case $.ig.MdxElementType.prototype.memberTree:
				partBuilder.append5(mdxElement.mdxExpression());
				break;
		}
		if ($.ig.util.cast($.ig.MdxLevelMembersElement.prototype.$type, mdxElement) !== null || $.ig.util.cast($.ig.MdxChildrenCollectionElement.prototype.$type, mdxElement) !== null) {
			this.addCalculatedMembers(partBuilder, mdxElement);
		}
		switch (mdxElement.elementType()) {
			case $.ig.MdxElementType.prototype.memberTree:
			case $.ig.MdxElementType.prototype.member:
				this.addVisualTotals(partBuilder, mdxElement);
				break;
		}
		stringBuilder.append7(partBuilder);
	}
	,
	addVisualTotals: function (stringBuilder, mdxElement) {
	}
	,
	addCalculatedMembers: function (stringBuilder, mdxElement) {
		if (this.mdxSettings().addCalculatedMembers()) {
			stringBuilder.insert1(0, "AddCalculatedMembers(");
			stringBuilder.append5(")");
		}
	}
	,
	clone: function () {
		var set = new $.ig.MdxSet(1, this.mdxSettings());
		var en = this.elements().getEnumerator();
		while (en.moveNext()) {
			var mdxElement = en.current();
			set.addElement(mdxElement);
		}
		return set;
	}
	,
	$type: new $.ig.Type('MdxSet', $.ig.Object.prototype.$type, [$.ig.IMdxSet.prototype.$type])
}, true);

$.ig.util.defType('MdxSettings', 'Object', {
	__addVisualTotalsOnRows: false,
	__addVisualTotalsOnColumns: false,
	__addCalculatedMembersOnRows: false,
	__addCalculatedMembersOnColumns: false,
	__hierarchizeRows: false,
	__hierarchizeColumns: false,
	__distinctOnRows: false,
	__distinctOnColumns: false,
	__nonEmptyOnRows: false,
	__nonEmptyOnColumns: false,
	__addDimensionPropertiesOnRows: false,
	__addDimensionPropertiesOnColumns: false,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.columnsAxisSettings(new $.ig.MdxDimensionAxisSettings());
		this.rowsAxisSettings(new $.ig.MdxDimensionAxisSettings());
		this.slicerAxisSettings(new $.ig.MdxSlicerAxisSettings());
		this.nonEmptyOnRows(true);
		this.nonEmptyOnColumns(true);
		var dimensionRowProperties = new $.ig.ObservableCollection$1(String, 0);
		dimensionRowProperties.collectionChanged = $.ig.Delegate.prototype.combine(dimensionRowProperties.collectionChanged, this.dimensionRowPropertiesCollectionChanged.runOn(this));
		this.dimensionPropertiesOnRows(dimensionRowProperties);
		var dimensionColumnProperties = new $.ig.ObservableCollection$1(String, 0);
		dimensionColumnProperties.collectionChanged = $.ig.Delegate.prototype.combine(dimensionColumnProperties.collectionChanged, this.dimensionColumnPropertiesCollectionChanged.runOn(this));
		this.dimensionPropertiesOnColumns(dimensionColumnProperties);
		this.addDimensionPropertiesOnRows(true);
		this.addDimensionPropertiesOnColumns(true);
		this.addCalculatedMembersOnRows(true);
		this.addCalculatedMembersOnColumns(true);
		this.resetToDefaultDimensionPropertiesImpl(this.dimensionPropertiesOnRows());
		this.resetToDefaultDimensionPropertiesImpl(this.dimensionPropertiesOnColumns());
	},
	dimensionRowPropertiesCollectionChanged: function (sender, e) {
		this.rowsAxisSettings().dimensionProperties().clear();
		var en = this.dimensionPropertiesOnRows().getEnumerator();
		while (en.moveNext()) {
			var rowDimProperty = en.current();
			this.rowsAxisSettings().dimensionProperties().add(rowDimProperty);
		}
	}
	,
	dimensionColumnPropertiesCollectionChanged: function (sender, e) {
		this.columnsAxisSettings().dimensionProperties().clear();
		var en = this.dimensionPropertiesOnColumns().getEnumerator();
		while (en.moveNext()) {
			var columnDimProperty = en.current();
			this.columnsAxisSettings().dimensionProperties().add(columnDimProperty);
		}
	}
	,
	_columnsAxisSettings: null,
	columnsAxisSettings: function (value) {
		if (arguments.length === 1) {
			this._columnsAxisSettings = value;
			return value;
		} else {
			return this._columnsAxisSettings;
		}
	}
	,
	_rowsAxisSettings: null,
	rowsAxisSettings: function (value) {
		if (arguments.length === 1) {
			this._rowsAxisSettings = value;
			return value;
		} else {
			return this._rowsAxisSettings;
		}
	}
	,
	_slicerAxisSettings: null,
	slicerAxisSettings: function (value) {
		if (arguments.length === 1) {
			this._slicerAxisSettings = value;
			return value;
		} else {
			return this._slicerAxisSettings;
		}
	}
	,
	visualTotalsOnRows: function (value) {
		if (arguments.length === 1) {
			if (this.__addVisualTotalsOnRows != value) {
				this.__addVisualTotalsOnRows = value;
				this.rowsAxisSettings().visualTotals(value);
			}
			return value;
		} else {
			return this.__addVisualTotalsOnRows;
		}
	}
	,
	visualTotalsOnColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__addVisualTotalsOnColumns != value) {
				this.__addVisualTotalsOnColumns = value;
				this.columnsAxisSettings().visualTotals(value);
			}
			return value;
		} else {
			return this.__addVisualTotalsOnColumns;
		}
	}
	,
	addCalculatedMembersOnRows: function (value) {
		if (arguments.length === 1) {
			if (this.__addCalculatedMembersOnRows != value) {
				this.__addCalculatedMembersOnRows = value;
				this.rowsAxisSettings().addCalculatedMembers(value);
			}
			return value;
		} else {
			return this.__addCalculatedMembersOnRows;
		}
	}
	,
	addCalculatedMembersOnColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__addCalculatedMembersOnColumns != value) {
				this.__addCalculatedMembersOnColumns = value;
				this.columnsAxisSettings().addCalculatedMembers(value);
			}
			return value;
		} else {
			return this.__addCalculatedMembersOnColumns;
		}
	}
	,
	hierarchizeRows: function (value) {
		if (arguments.length === 1) {
			if (this.__hierarchizeRows != value) {
				this.__hierarchizeRows = value;
				this.rowsAxisSettings().hierarchize(value);
			}
			return value;
		} else {
			return this.__hierarchizeRows;
		}
	}
	,
	hierarchizeColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__hierarchizeColumns != value) {
				this.__hierarchizeColumns = value;
				this.columnsAxisSettings().hierarchize(value);
			}
			return value;
		} else {
			return this.__hierarchizeColumns;
		}
	}
	,
	distinctOnRows: function (value) {
		if (arguments.length === 1) {
			if (this.__distinctOnRows != value) {
				this.__distinctOnRows = value;
				this.rowsAxisSettings().distinct(value);
			}
			return value;
		} else {
			return this.__distinctOnRows;
		}
	}
	,
	distinctOnColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__distinctOnColumns != value) {
				this.__distinctOnColumns = value;
				this.columnsAxisSettings().distinct(value);
			}
			return value;
		} else {
			return this.__distinctOnColumns;
		}
	}
	,
	nonEmptyOnRows: function (value) {
		if (arguments.length === 1) {
			if (this.__nonEmptyOnRows != value) {
				this.__nonEmptyOnRows = value;
				this.rowsAxisSettings().nonEmpty(value);
			}
			return value;
		} else {
			return this.__nonEmptyOnRows;
		}
	}
	,
	nonEmptyOnColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__nonEmptyOnColumns != value) {
				this.__nonEmptyOnColumns = value;
				this.columnsAxisSettings().nonEmpty(value);
			}
			return value;
		} else {
			return this.__nonEmptyOnColumns;
		}
	}
	,
	addDimensionPropertiesOnRows: function (value) {
		if (arguments.length === 1) {
			if (this.__addDimensionPropertiesOnRows != value) {
				this.__addDimensionPropertiesOnRows = value;
				this.rowsAxisSettings().addDimensionProperties(value);
			}
			return value;
		} else {
			return this.__addDimensionPropertiesOnRows;
		}
	}
	,
	addDimensionPropertiesOnColumns: function (value) {
		if (arguments.length === 1) {
			if (this.__addDimensionPropertiesOnColumns != value) {
				this.__addDimensionPropertiesOnColumns = value;
				this.columnsAxisSettings().addDimensionProperties(value);
			}
			return value;
		} else {
			return this.__addDimensionPropertiesOnColumns;
		}
	}
	,
	_dimensionPropertiesOnRows: null,
	dimensionPropertiesOnRows: function (value) {
		if (arguments.length === 1) {
			this._dimensionPropertiesOnRows = value;
			return value;
		} else {
			return this._dimensionPropertiesOnRows;
		}
	}
	,
	_dimensionPropertiesOnColumns: null,
	dimensionPropertiesOnColumns: function (value) {
		if (arguments.length === 1) {
			this._dimensionPropertiesOnColumns = value;
			return value;
		} else {
			return this._dimensionPropertiesOnColumns;
		}
	}
	,
	resetToDefaultDimensionProperties: function (dimensionProperties) {
		this.resetToDefaultDimensionPropertiesImpl(dimensionProperties);
	}
	,
	resetToDefaultDimensionPropertiesImpl: function (dimensionProperties) {
		dimensionProperties.clear();
		dimensionProperties.add("CHILDREN_CARDINALITY");
		dimensionProperties.add("PARENT_UNIQUE_NAME");
	}
	,
	$type: new $.ig.Type('MdxSettings', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MdxAxisSettings', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('MdxAxisSettings', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MdxSlicerAxisSettings', 'MdxAxisSettings', {
	init: function () {
		$.ig.MdxAxisSettings.prototype.init.call(this);
	},
	$type: new $.ig.Type('MdxSlicerAxisSettings', $.ig.MdxAxisSettings.prototype.$type)
}, true);

$.ig.util.defType('MdxDimensionAxisSettings', 'MdxAxisSettings', {
	__addVisualTotals: false,
	__addCalculatedMembers: false,
	init: function () {
		$.ig.MdxAxisSettings.prototype.init.call(this);
		this.dimensionProperties(new $.ig.List$1(String, 0));
		this.mdxSetSettings(new $.ig.MdxSetSettings());
	},
	_mdxSetSettings: null,
	mdxSetSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSetSettings = value;
			return value;
		} else {
			return this._mdxSetSettings;
		}
	}
	,
	_hierarchize: false,
	hierarchize: function (value) {
		if (arguments.length === 1) {
			this._hierarchize = value;
			return value;
		} else {
			return this._hierarchize;
		}
	}
	,
	_distinct: false,
	distinct: function (value) {
		if (arguments.length === 1) {
			this._distinct = value;
			return value;
		} else {
			return this._distinct;
		}
	}
	,
	_nonEmpty: false,
	nonEmpty: function (value) {
		if (arguments.length === 1) {
			this._nonEmpty = value;
			return value;
		} else {
			return this._nonEmpty;
		}
	}
	,
	_addDimensionProperties: false,
	addDimensionProperties: function (value) {
		if (arguments.length === 1) {
			this._addDimensionProperties = value;
			return value;
		} else {
			return this._addDimensionProperties;
		}
	}
	,
	visualTotals: function (value) {
		if (arguments.length === 1) {
			if (this.__addVisualTotals != value) {
				this.__addVisualTotals = value;
				this.mdxSetSettings().visualTotals(value);
			}
			return value;
		} else {
			return this.__addVisualTotals;
		}
	}
	,
	addCalculatedMembers: function (value) {
		if (arguments.length === 1) {
			if (this.__addCalculatedMembers != value) {
				this.__addCalculatedMembers = value;
				this.mdxSetSettings().addCalculatedMembers(value);
			}
			return value;
		} else {
			return this.__addCalculatedMembers;
		}
	}
	,
	_dimensionProperties: null,
	dimensionProperties: function (value) {
		if (arguments.length === 1) {
			this._dimensionProperties = value;
			return value;
		} else {
			return this._dimensionProperties;
		}
	}
	,
	$type: new $.ig.Type('MdxDimensionAxisSettings', $.ig.MdxAxisSettings.prototype.$type)
}, true);

$.ig.util.defType('MdxSetSettings', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_visualTotals: false,
	visualTotals: function (value) {
		if (arguments.length === 1) {
			this._visualTotals = value;
			return value;
		} else {
			return this._visualTotals;
		}
	}
	,
	_addCalculatedMembers: false,
	addCalculatedMembers: function (value) {
		if (arguments.length === 1) {
			this._addCalculatedMembers = value;
			return value;
		} else {
			return this._addCalculatedMembers;
		}
	}
	,
	$type: new $.ig.Type('MdxSetSettings', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IMdxSlicerAxis', 'Object', {
	$type: new $.ig.Type('IMdxSlicerAxis', null, [$.ig.IMdxExtendedFilterInfo.prototype.$type])
}, true);

$.ig.util.defType('MdxSlicerAxis', 'MdxAxis', {
	init: function (settings) {
		$.ig.MdxAxis.prototype.init1.call(this, 1, 2, settings);
	},
	_columnsFilter: null,
	columnsFilter: function (value) {
		if (arguments.length === 1) {
			this._columnsFilter = value;
			return value;
		} else {
			return this._columnsFilter;
		}
	}
	,
	_rowsFilter: null,
	rowsFilter: function (value) {
		if (arguments.length === 1) {
			this._rowsFilter = value;
			return value;
		} else {
			return this._rowsFilter;
		}
	}
	,
	_filtersFilter: null,
	filtersFilter: function (value) {
		if (arguments.length === 1) {
			this._filtersFilter = value;
			return value;
		} else {
			return this._filtersFilter;
		}
	}
	,
	_measureFilter: null,
	measureFilter: function (value) {
		if (arguments.length === 1) {
			this._measureFilter = value;
			return value;
		} else {
			return this._measureFilter;
		}
	}
	,
	_filterElements: null,
	filterElements: function (value) {
		if (arguments.length === 1) {
			this._filterElements = value;
			return value;
		} else {
			return this._filterElements;
		}
	}
	,
	$type: new $.ig.Type('MdxSlicerAxis', $.ig.MdxAxis.prototype.$type, [$.ig.IMdxSlicerAxis.prototype.$type])
}, true);

$.ig.util.defType('MdxSlicerSet', 'MdxSet', {
	init: function () {
		$.ig.MdxSet.prototype.init.call(this, 0);
	},
	supportsElementType: function (mdxElement) {
		return $.ig.util.cast($.ig.IAxisFilterElement.prototype.$type, mdxElement) !== null;
	}
	,
	$type: new $.ig.Type('MdxSlicerSet', $.ig.MdxSet.prototype.$type)
}, true);

$.ig.util.defType('MdxDimensionAxisProvider', 'Object', {
	__hierarchyLevelsCollection: null,
	__measureNames: null,
	__measureListIndex: 0,
	init: function (initNumber, xmlaDataSource, mdxSettings, sourceAxis, rootPositionInfo) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		this.__hierarchyLevelsCollection = new $.ig.Dictionary$2(String, $.ig.MdxElementCollectionElement.prototype.$type, 0);
		this.__measureNames = new $.ig.List$1(String, 0);
		this.__measureListIndex = -1;
		$.ig.Object.prototype.init.call(this);
		if (xmlaDataSource == null) {
			throw new $.ig.ArgumentNullException(0, "xmlaDataSource");
		}
		this.xmlaDataSource(xmlaDataSource);
		this.mdxSettings(mdxSettings);
		this.sourceAxis(sourceAxis);
		this.rootPositionInfo(rootPositionInfo);
	},
	init1: function (initNumber, curentResultAxis, mdxSettings, positionsToExtend) {
		this.__hierarchyLevelsCollection = new $.ig.Dictionary$2(String, $.ig.MdxElementCollectionElement.prototype.$type, 0);
		this.__measureNames = new $.ig.List$1(String, 0);
		this.__measureListIndex = -1;
		$.ig.Object.prototype.init.call(this);
		this.mdxSettings(mdxSettings);
		this.currentResultAxis(curentResultAxis);
		this.positionsToExtend(positionsToExtend);
	},
	_axis: null,
	axis: function (value) {
		if (arguments.length === 1) {
			this._axis = value;
			return value;
		} else {
			return this._axis;
		}
	}
	,
	__rootPositionInfo: null,
	rootPositionInfo: function (value) {
		if (arguments.length === 1) {
			this.__rootPositionInfo = value;
			return value;
		} else {
			return this.__rootPositionInfo;
		}
	}
	,
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	_xmlaDataSource: null,
	xmlaDataSource: function (value) {
		if (arguments.length === 1) {
			this._xmlaDataSource = value;
			return value;
		} else {
			return this._xmlaDataSource;
		}
	}
	,
	_sourceAxis: null,
	sourceAxis: function (value) {
		if (arguments.length === 1) {
			this._sourceAxis = value;
			return value;
		} else {
			return this._sourceAxis;
		}
	}
	,
	_positionsToExtend: null,
	positionsToExtend: function (value) {
		if (arguments.length === 1) {
			this._positionsToExtend = value;
			return value;
		} else {
			return this._positionsToExtend;
		}
	}
	,
	_currentResultAxis: null,
	currentResultAxis: function (value) {
		if (arguments.length === 1) {
			this._currentResultAxis = value;
			return value;
		} else {
			return this._currentResultAxis;
		}
	}
	,
	clearAxis: function () {
		this.axis(null);
	}
	,
	extendAxis: function (axisToExtend, axisIndex) {
		var axis = axisToExtend;
		var positionsToExtend = this.positionsToExtend();
		var positionResolver = this.currentResultAxis().positionResolver();
		var partialAxis = new $.ig.MdxAxis(1, axisToExtend.axisIndex(), this.mdxSettings());
		var en = positionsToExtend.getEnumerator();
		while (en.moveNext()) {
			var positionItemToExpand = en.current();
			var mdxSet = new $.ig.MdxSet(1, (axis.mdxSettings()).mdxSetSettings());
			var sets = new $.ig.List$1($.ig.IMdxSet.prototype.$type, 0);
			var hostPositionInfo = positionResolver.getHostPositionItemInfo(positionItemToExpand.position(), positionItemToExpand.position().indexOf(positionItemToExpand));
			var en1 = positionItemToExpand.position().getItems().getEnumerator();
			while (en1.moveNext()) {
				var positionItem = en1.current();
				var mdxElement;
				if (positionItem == positionItemToExpand) {
					mdxElement = new $.ig.MdxChildrenCollectionElement(positionItem.key(), positionItem.key(), -1, null, null);
					mdxSet.addElement(mdxElement);
					break;
				}
				mdxElement = new $.ig.MdxSingleElement(0, positionItem.key(), positionItem.key(), positionItem.depth());
				mdxSet.addElement(mdxElement);
			}
			var positionItemInfo = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionInfo.values(), function (pi) { return pi.key() == positionItemToExpand.key(); }));
			if (positionItemInfo != null) {
				if (positionItemInfo.count() == 0) {
					sets.add(mdxSet);
				} else {
					this.addRootPositionsToSet(positionItemInfo, mdxSet, sets, false, true, false);
				}
				var en2 = sets.getEnumerator();
				while (en2.moveNext()) {
					var set = en2.current();
					axisToExtend.addSet(set);
					partialAxis.addSet(set);
				}
			}
		}
		return partialAxis;
	}
	,
	generateSets: function (hostPositionItemInfo, sets) {
		if (this.__measureListIndex != 0) {
			var mainRootSet = new $.ig.MdxSet(1, this.mdxSettings().mdxSetSettings());
			if (this.__measureListIndex == -1) {
				sets.add(mainRootSet);
			}
			this.generateMainRootSet(hostPositionItemInfo, mainRootSet, sets);
			this.addRootPositionsToSet(hostPositionItemInfo, new $.ig.MdxSet(1, this.mdxSettings().mdxSetSettings()), sets, true, false, true);
		} else {
			this.addRootPositionsToSet(hostPositionItemInfo, new $.ig.MdxSet(1, this.mdxSettings().mdxSetSettings()), sets, false, false, true);
		}
	}
	,
	generateMainRootSet: function (hostPositionItemInfo, set, sets) {
		var rootItems = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == hostPositionItemInfo.positionItemDepthMin(); }));
		if (rootItems.count() == 0) {
			return;
		}
		if (this.__measureListIndex == hostPositionItemInfo.positionItemIndex() + 1) {
			var en = $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == 0; }).getEnumerator();
			while (en.moveNext()) {
				var positionItemInfo = en.current();
				var setClone = set.clone();
				var measureElement = new $.ig.MdxSingleElement(0, positionItemInfo.positionItem().key(), positionItemInfo.positionItem().key(), 0);
				setClone.addElement(measureElement);
				sets.add(setClone);
				this.validateHostedItems(positionItemInfo);
				if (positionItemInfo.count() == 0) {
					this.addElementsToSet(this.sourceAxis(), positionItemInfo.positionItemIndex() + 1, setClone);
				} else {
					this.generateMainRootSet(positionItemInfo, setClone, sets);
				}
			}
		} else {
			var rootSample = rootItems.item(0);
			var levelMembersElement = new $.ig.MdxLevelMembersElement(rootSample.hierarchyLevelKey(), rootSample.hierarchyLevelKey(), hostPositionItemInfo.positionItemDepthMin());
			set.addElement(levelMembersElement);
			this.validateHostedItems(rootSample);
			if (rootSample.count() == 0) {
				if (this.__measureListIndex != -1) {
					sets.add(set);
				}
				this.addElementsToSet(this.sourceAxis(), rootSample.positionItemIndex() + 1, set);
			} else {
				this.generateMainRootSet(rootSample, set, sets);
			}
		}
	}
	,
	createAxis: function (axisIndex) {
		if (this.axis() != null) {
			return this.axis();
		}
		if (this.rootPositionInfo() == null) {
			this.axis(this.createAxis1(axisIndex, this.sourceAxis()));
			return this.axis();
		}
		this.createValidationEntries(axisIndex);
		this.validateHostedItems(this.rootPositionInfo());
		if (this.rootPositionInfo().count() == 0) {
			this.axis(this.createAxis1(axisIndex, this.sourceAxis()));
			return this.axis();
		}
		var sets = new $.ig.List$1($.ig.IMdxSet.prototype.$type, 0);
		this.generateSets(this.rootPositionInfo(), sets);
		var axis = new $.ig.MdxAxis(1, axisIndex, this.mdxSettings());
		var en = sets.getEnumerator();
		while (en.moveNext()) {
			var mdxSet = en.current();
			axis.addSet(mdxSet);
		}
		this.axis(axis);
		return axis;
	}
	,
	createValidationEntries: function (axisIndex) {
		var hasColumns = this.xmlaDataSource().columnAxis().count() > 0;
		var hasRows = this.xmlaDataSource().rowAxis().count() > 0;
		var checkRoot = this.xmlaDataSource().measures().count() > 1;
		if (checkRoot) {
			if (axisIndex == 0) {
				if (hasColumns) {
					if (this.xmlaDataSource().measureListLocation() == $.ig.MeasureListLocation.prototype.columns) {
						this.__measureListIndex = this.xmlaDataSource().measureListIndex();
					}
				} else if (hasRows && this.xmlaDataSource().measureListLocation() == $.ig.MeasureListLocation.prototype.rows) {
					this.__measureListIndex = this.xmlaDataSource().measureListIndex();
				}
			} else if (axisIndex == 1 && hasRows && this.xmlaDataSource().measureListLocation() == $.ig.MeasureListLocation.prototype.rows) {
				this.__measureListIndex = this.xmlaDataSource().measureListIndex();
			}
		}
		var en = this.xmlaDataSource().measures().getEnumerator();
		while (en.moveNext()) {
			var measure = en.current();
			this.__measureNames.add(measure.uniqueName());
		}
	}
	,
	validateHostedItems: function (positionItemInfo) {
		if (this.sourceAxis() != null) {
			var itemIndex = positionItemInfo.positionItemIndex() + 1;
			if (itemIndex < this.sourceAxis().count()) {
				var axisCoreElement = this.sourceAxis().item(itemIndex);
				var measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, axisCoreElement);
				if (measureList != null) {
					var rootItemsInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values());
					var en = rootItemsInfo.getEnumerator();
					while (en.moveNext()) {
						var itemInfo = en.current();
						if (!this.__measureNames.contains(itemInfo.key())) {
							positionItemInfo.remove(itemInfo.key());
						}
					}
				}
				var hierarchy = $.ig.util.cast($.ig.Hierarchy.prototype.$type, axisCoreElement);
				if (hierarchy != null) {
					var hostedItemInfo = $.ig.Enumerable.prototype.firstOrDefault$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), positionItemInfo.values());
					if (hostedItemInfo != null) {
						if (hostedItemInfo.hierarchyKey() != hierarchy.uniqueName()) {
							positionItemInfo.clear();
						}
					}
				}
			} else {
				positionItemInfo.clear();
			}
		}
	}
	,
	createAxis1: function (axisIndex, dataSourceAxis) {
		var mdxSet = new $.ig.MdxSet(1, this.mdxSettings().mdxSetSettings());
		this.addElementsToSet(dataSourceAxis, 0, mdxSet);
		var axis = new $.ig.MdxAxis(1, axisIndex, this.mdxSettings());
		axis.addSet(mdxSet);
		return axis;
	}
	,
	addElementsToSet: function (dataSourceAxis, startIndex, mdxSet) {
		var $self = this;
		if (dataSourceAxis == null) {
			return;
		}
		for (var i = startIndex; i < dataSourceAxis.count(); i++) {
			var coreOlapElement = dataSourceAxis.item(i);
			var hierarchy = $.ig.util.cast($.ig.Hierarchy.prototype.$type, coreOlapElement);
			if (hierarchy != null) {
				if (hierarchy.allMember() == null) {
					var levels = $.ig.Enumerable.prototype.toList$1($.ig.ICoreOlapElement.prototype.$type, this.xmlaDataSource().getCoreElements(function (e) { return (e).hierarchyUniqueName() == hierarchy.uniqueName(); }, $.ig.Level.prototype.$type));
					var rootLevel = levels.item(0);
					mdxSet.addElement(new $.ig.MdxLevelMembersElement(rootLevel.uniqueName(), rootLevel.uniqueName(), 0));
				} else {
					mdxSet.addElement(new $.ig.MdxSingleElement(0, hierarchy.defaultMember(), hierarchy.defaultMember(), 0));
				}
				continue;
			}
			var level = $.ig.util.cast($.ig.Level.prototype.$type, coreOlapElement);
			if (level != null) {
				var levelsCollection;
				if (!(function () { var $ret = $self.__hierarchyLevelsCollection.tryGetValue(level.hierarchyUniqueName(), levelsCollection); levelsCollection = $ret.p1; return $ret.ret; }())) {
					levelsCollection = new $.ig.MdxElementCollectionElement();
					mdxSet.addElement(levelsCollection);
					this.__hierarchyLevelsCollection.add(level.hierarchyUniqueName(), levelsCollection);
				}
				levelsCollection.addElement(new $.ig.MdxLevelMembersElement(level.uniqueName(), level.uniqueName(), level.depth()));
				continue;
			}
			var measureList = $.ig.util.cast($.ig.MeasureList.prototype.$type, coreOlapElement);
			if (measureList != null) {
				var collectionElement = new $.ig.MdxElementCollectionElement();
				var en = measureList.measures().getEnumerator();
				while (en.moveNext()) {
					var measure = en.current();
					collectionElement.addElement(new $.ig.MdxSingleElement(0, measure.uniqueName(), measure.uniqueName(), 0));
				}
				mdxSet.addElement(collectionElement);
			}
		}
	}
	,
	addPositionToSet: function (hostPositionItemInfo, positionItemInfo, set, sets, addSetIfHasChildren, hasChildren, addChildren) {
		var setCopy = (set).clone();
		setCopy.addElement(new $.ig.MdxSingleElement(0, positionItemInfo.key(), positionItemInfo.key(), positionItemInfo.positionItemDepth()));
		this.validateHostedItems(positionItemInfo);
		if (positionItemInfo.count() == 0) {
			if (addSetIfHasChildren) {
				if (hasChildren) {
					sets.add(setCopy);
					this.addElementsToSet(this.sourceAxis(), positionItemInfo.positionItemIndex() + 1, setCopy);
				}
			} else {
				sets.add(setCopy);
				this.addElementsToSet(this.sourceAxis(), positionItemInfo.positionItemIndex() + 1, setCopy);
			}
		} else {
			this.addRootPositionsToSet(positionItemInfo, setCopy, sets, addSetIfHasChildren, hasChildren, addChildren);
		}
		if (addChildren && positionItemInfo.isExpanded()) {
			this.addChildrenPositionsToSet(positionItemInfo, sets, hostPositionItemInfo, set);
		}
	}
	,
	addChildrenPositionsToSet: function (positionItemInfo, sets, hostPositionItemInfo, setPrototype) {
		var childrenPositionsInfo = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.parentKey() == positionItemInfo.key(); }));
		if (childrenPositionsInfo.count() > 0) {
			var set2 = (setPrototype).clone();
			set2.addElement(new $.ig.MdxChildrenCollectionElement(positionItemInfo.key(), positionItemInfo.key(), positionItemInfo.positionItemDepth(), null, null));
			this.validateHostedItems(positionItemInfo);
			if (positionItemInfo.count() == 0) {
				sets.add(set2);
				this.addElementsToSet(this.sourceAxis(), positionItemInfo.positionItemIndex() + 1, set2);
			} else {
				this.addRootPositionsToSet(positionItemInfo, set2, sets, true, true, false);
			}
		}
		var en = childrenPositionsInfo.getEnumerator();
		while (en.moveNext()) {
			var childPositionItemInfo = en.current();
			var set3 = (setPrototype).clone();
			set3.addElement(new $.ig.MdxSingleElement(0, childPositionItemInfo.key(), childPositionItemInfo.key(), childPositionItemInfo.positionItemDepth()));
			this.validateHostedItems(childPositionItemInfo);
			if (childPositionItemInfo.isExpanded()) {
				this.addChildrenPositionsToSet(childPositionItemInfo, sets, hostPositionItemInfo, setPrototype);
			}
			this.addRootPositionsToSet(childPositionItemInfo, set3, sets, true, false, true);
		}
	}
	,
	addRootPositionsToSet: function (hostPositionItemInfo, set, sets, addSetIfHasChildren, hasChildren, addChildren) {
		var rootItems = $.ig.Enumerable.prototype.toList$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.Enumerable.prototype.where$1($.ig.PositionItemInfo$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), hostPositionItemInfo.values(), function (pi) { return pi.positionItemDepth() == hostPositionItemInfo.positionItemDepthMin(); }));
		if (rootItems.count() == 0) {
			return;
		}
		var en = rootItems.getEnumerator();
		while (en.moveNext()) {
			var positionItemInfo = en.current();
			this.addPositionToSet(hostPositionItemInfo, positionItemInfo, set, sets, addSetIfHasChildren, hasChildren, addChildren);
		}
	}
	,
	$type: new $.ig.Type('MdxDimensionAxisProvider', $.ig.Object.prototype.$type, [$.ig.IMdxDimensionAxisProvider.prototype.$type])
}, true);

$.ig.util.defType('MdxSlicerAxisProvider', 'Object', {
	__columnFilters: null,
	__rowFilters: null,
	__filterFilters: null,
	init: function (dataSource, columnsFilters, rowsFilters, filterFilters, mdxSettings) {
		this.__columnFilters = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		this.__rowFilters = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		this.__filterFilters = new $.ig.List$1($.ig.AxisElement.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
		this.dataSource(dataSource);
		this.mdxSettings(mdxSettings);
		this.__columnFilters = columnsFilters;
		this.__rowFilters = rowsFilters;
		this.__filterFilters = filterFilters;
	},
	_dataSource: null,
	dataSource: function (value) {
		if (arguments.length === 1) {
			this._dataSource = value;
			return value;
		} else {
			return this._dataSource;
		}
	}
	,
	_mdxSettings: null,
	mdxSettings: function (value) {
		if (arguments.length === 1) {
			this._mdxSettings = value;
			return value;
		} else {
			return this._mdxSettings;
		}
	}
	,
	createAxis: function (axisIndex) {
		var mdxSlicerAxis = new $.ig.MdxSlicerAxis(this.mdxSettings().slicerAxisSettings());
		mdxSlicerAxis.axisIndex(axisIndex);
		mdxSlicerAxis.columnsFilter($.ig.MdxSlicerAxisBuilder.prototype.createFilterElement(this.__columnFilters, mdxSlicerAxis));
		mdxSlicerAxis.rowsFilter($.ig.MdxSlicerAxisBuilder.prototype.createFilterElement(this.__rowFilters, mdxSlicerAxis));
		mdxSlicerAxis.filtersFilter($.ig.MdxSlicerAxisBuilder.prototype.createFilterElement(this.__filterFilters, mdxSlicerAxis));
		mdxSlicerAxis.measureFilter($.ig.MdxSlicerAxisBuilder.prototype.createMeasureFilterElement(this.dataSource().measures(), mdxSlicerAxis));
		return mdxSlicerAxis;
	}
	,
	$type: new $.ig.Type('MdxSlicerAxisProvider', $.ig.Object.prototype.$type, [$.ig.IMdxAxisProvider.prototype.$type])
}, true);

$.ig.util.defType('MdxSlicerAxisBuilder', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	createMeasureFilterElement: function (measures, slicerAxis) {
		if ($.ig.Enumerable.prototype.count$1($.ig.Measure.prototype.$type, measures) == 0) {
			return null;
		}
		var measureAxisFilter = new $.ig.MdxAxisFilterElement();
		var measuresFilterCollection = new $.ig.MdxElementCollectionElement();
		var en = measures.getEnumerator();
		while (en.moveNext()) {
			var measure = en.current();
			var measureMdxElement = new $.ig.MdxSingleElement(0, measure.uniqueName(), measure.uniqueName(), 0);
			measuresFilterCollection.addElement(measureMdxElement);
		}
		var measureSet = new $.ig.MdxSlicerSet();
		measureSet.addElement(measureAxisFilter);
		slicerAxis.addSet(measureSet);
		measureAxisFilter.addFilterPart(measuresFilterCollection, true);
		return measureAxisFilter;
	}
	,
	createFilterElement: function (axisElements, slicerAxis) {
		var axisFilterElement = new $.ig.MdxAxisFilterElement();
		for (var i = 0; i < axisElements.count(); i++) {
			var axisElement = axisElements.item(i);
			var filterElementCollection = new $.ig.MdxElementCollectionElement();
			var mdxDimensionMembers = new $.ig.Dictionary$2(String, $.ig.MdxSingleElement.prototype.$type, 0);
			var en = axisElement.memberNames().getEnumerator();
			while (en.moveNext()) {
				var filterMemberName = en.current();
				var mdxFilterElement;
				if (!(function () { var $ret = mdxDimensionMembers.tryGetValue(filterMemberName, mdxFilterElement); mdxFilterElement = $ret.p1; return $ret.ret; }())) {
					mdxFilterElement = new $.ig.MdxSingleElement(0, filterMemberName, filterMemberName, -1);
					mdxDimensionMembers.add(filterMemberName, mdxFilterElement);
				}
				filterElementCollection.addElement(mdxFilterElement);
			}
			axisFilterElement.addFilterPart(filterElementCollection, false);
		}
		if (axisFilterElement.filterCollection().count() == 0 && axisFilterElement.singleFilterElements().count() == 0) {
			return null;
		}
		var slicerSet = new $.ig.MdxSlicerSet();
		slicerSet.addElement(axisFilterElement);
		slicerAxis.addSet(slicerSet);
		return axisFilterElement;
	}
	,
	$type: new $.ig.Type('MdxSlicerAxisBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IXmlaDataProviderFactory', 'Object', {
	$type: new $.ig.Type('IXmlaDataProviderFactory', null, [$.ig.IOlapDataProviderFactory.prototype.$type])
}, true);

$.ig.util.defType('DefaultXmlaDataProviderFactory', 'Object', {
	__serverUrl: null,
	__credentials: null,
	init: function (serverUrl, credentials) {
		$.ig.Object.prototype.init.call(this);
		this.__serverUrl = serverUrl;
		this.__credentials = credentials;
	},
	createDataProvider: function () {
		return new $.ig.XmlaDataProvider(new $.ig.XmlaConnection(new $.ig.Uri(0, this.__serverUrl), this.__credentials), null);
	}
	,
	$type: new $.ig.Type('DefaultXmlaDataProviderFactory', $.ig.Object.prototype.$type, [$.ig.IXmlaDataProviderFactory.prototype.$type])
}, true);

$.ig.util.defType('RemoteXmlaDataProviderFactory', 'Object', {
	__requestSerializer: null,
	__responseSerializer: null,
	__serverUrl: null,
	__credentials: null,
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
		this.__requestSerializer = new $.ig.JsonRemoteRequestSerializer();
		this.__responseSerializer = new $.ig.JsonRemoteResponseSerializer();
	},
	init1: function (initNumber, serverUrl, credentials) {
		$.ig.Object.prototype.init.call(this);
		this.__requestSerializer = new $.ig.JsonRemoteRequestSerializer();
		this.__responseSerializer = new $.ig.JsonRemoteResponseSerializer();
		this.__serverUrl = serverUrl;
		this.__credentials = credentials;
	},
	init2: function (initNumber, requestSerializer, responseSerializer) {
		$.ig.Object.prototype.init.call(this);
		this.__requestSerializer = requestSerializer;
		this.__responseSerializer = responseSerializer;
	},
	createDataProvider: function () {
		return new $.ig.RemoteXmlaDataProvder(this.__serverUrl, this.__credentials, this.__requestSerializer, this.__responseSerializer);
	}
	,
	$type: new $.ig.Type('RemoteXmlaDataProviderFactory', $.ig.Object.prototype.$type, [$.ig.IXmlaDataProviderFactory.prototype.$type])
}, true);

$.ig.util.defType('IRemoteRequestSerializer', 'Object', {
	$type: new $.ig.Type('IRemoteRequestSerializer', null)
}, true);

$.ig.util.defType('IRemoteResponseSerializer', 'Object', {
	$type: new $.ig.Type('IRemoteResponseSerializer', null)
}, true);

$.ig.util.defType('JsonRemoteRequestSerializer', 'Object', {
	__serializer: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__serializer = new $.ig.JavaScriptSerializer();
	},
	serializeRequest: function (request) {
		return $.ig.RemoteRequestJsonTypeSeriazlier.prototype.instance().serialize(request, this.__serializer);
	}
	,
	deserializeRequest: function (request) {
		return $.ig.RemoteRequestJsonTypeSeriazlier.prototype.instance().deserialize(request, this.__serializer);
	}
	,
	$type: new $.ig.Type('JsonRemoteRequestSerializer', $.ig.Object.prototype.$type, [$.ig.IRemoteRequestSerializer.prototype.$type])
}, true);

$.ig.util.defType('JsonRemoteResponseSerializer', 'Object', {
	__serializer: null,
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.__serializer = new $.ig.JavaScriptSerializer();
	},
	serializeResponse: function (response, type) {
		switch (type) {
			case $.ig.RemoteRequestType.prototype.discoverCatalogs: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Catalog.prototype.$type, response, $.ig.CatalogJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverCubes: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Cube.prototype.$type, response, $.ig.CubeJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverDimensions: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Dimension.prototype.$type, response, $.ig.DimensionJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverHierarchies: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Hierarchy.prototype.$type, response, $.ig.HierarchyJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverLevels: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Level.prototype.$type, response, $.ig.LevelJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasures: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Measure.prototype.$type, response, $.ig.MeasureJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasureGroups: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.MeasureGroup.prototype.$type, response, $.ig.MeasureGroupJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasureGroupDimensions: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.MeasureGroupDimension.prototype.$type, response, $.ig.MeasureGroupDimensionJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMembers: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Member.prototype.$type, response, $.ig.MemberJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverKpis: return $.ig.ArrayJsonSerializer.prototype.instance().serialize$1($.ig.Kpi.prototype.$type, response, $.ig.KpiJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.executeStatement: return $.ig.ResultJsonTypeSeriazlier.prototype.instance().serialize(response, this.__serializer);
			default: throw new $.ig.Error(1, "Unknown RemoteXmlaRequestType.");
		}
	}
	,
	deserializeResponse: function (response, type) {
		switch (type) {
			case $.ig.RemoteRequestType.prototype.discoverCatalogs: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Catalog.prototype.$type, response, $.ig.CatalogJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverCubes: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Cube.prototype.$type, response, $.ig.CubeJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverDimensions: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Dimension.prototype.$type, response, $.ig.DimensionJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverHierarchies: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Hierarchy.prototype.$type, response, $.ig.HierarchyJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverLevels: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Level.prototype.$type, response, $.ig.LevelJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasures: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Measure.prototype.$type, response, $.ig.MeasureJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasureGroups: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.MeasureGroup.prototype.$type, response, $.ig.MeasureGroupJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMeasureGroupDimensions: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.MeasureGroupDimension.prototype.$type, response, $.ig.MeasureGroupDimensionJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverMembers: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Member.prototype.$type, response, $.ig.MemberJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.discoverKpis: return $.ig.ArrayJsonSerializer.prototype.instance().deserialize$1($.ig.Kpi.prototype.$type, response, $.ig.KpiJsonTypeSeriazlier.prototype.instance(), this.__serializer);
			case $.ig.RemoteRequestType.prototype.executeStatement: return $.ig.ResultJsonTypeSeriazlier.prototype.instance().deserialize(response, this.__serializer);
			default: throw new $.ig.Error(1, "Unknown RemoteXmlaRequestType.");
		}
	}
	,
	$type: new $.ig.Type('JsonRemoteResponseSerializer', $.ig.Object.prototype.$type, [$.ig.IRemoteResponseSerializer.prototype.$type])
}, true);

$.ig.util.defType('ArrayJsonSerializer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	instance: function () {
		return $.ig.ArrayJsonSerializer.prototype.__instance;
	}
	,
	deserialize$1: function ($t, s, jsonSerializer, serializer) {
		var arr = serializer.deserializeObject(s);
		return this.fromJsonArray$1($t, arr, jsonSerializer);
	}
	,
	serialize$1: function ($t, items, jsonSerializer, serializer) {
		var resArr = this.toJsonArray$1($t, items, jsonSerializer);
		return serializer.serialize(resArr);
	}
	,
	fromJsonArray$1: function ($t, arr, jsonSerializer) {
		var result = new $.ig.List$1($t, 0);
		for (var i = 0; i < arr.length; i++) {
			var o = arr[i];
			result.add(jsonSerializer.fromJsonObject(o));
		}
		return result;
	}
	,
	toJsonArray$1: function ($t, list, jsonSerializer) {
		var arr = $.ig.Enumerable.prototype.toArray$1($t, list);
		var resArr = new Array(arr.length);
		for (var i = 0; i < arr.length; i++) {
			resArr[i] = jsonSerializer.toJsonObject(arr[i]);
		}
		return resArr;
	}
	,
	$type: new $.ig.Type('ArrayJsonSerializer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IJsonTypeSerializer$1', 'Object', {
	$type: new $.ig.Type('IJsonTypeSerializer$1', null)
}, true);

$.ig.util.defType('JsonTypeSeriazlierBase$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
	},
	deserialize: function (s, serializer) {
		return this.fromJsonObject(serializer.deserializeObject(s));
	}
	,
	serialize: function (o, serializer) {
		return serializer.serialize(this.toJsonObject(o));
	}
	,
	fromJsonObject: function (o) {
	}
	,
	toJsonObject: function (o) {
	}
	,
	$type: new $.ig.Type('JsonTypeSeriazlierBase$1', $.ig.Object.prototype.$type, [$.ig.IJsonTypeSerializer$1.prototype.$type.specialize(0)])
}, true);

$.ig.util.defType('AxisJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.OlapResultAxis.prototype.$type);
	},
	instance: function () {
		return $.ig.AxisJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		var name = $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n");
		var tuples = $.ig.ArrayJsonSerializer.prototype.instance().fromJsonArray$1($.ig.OlapResultTuple.prototype.$type, $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "t"), $.ig.TupleJsonTypeSeriazlier.prototype.instance());
		var tupleSize = 0;
		if (tuples.count() > 0) {
			tupleSize = tuples.item(0).members().count();
		}
		return (function () {
			var $ret = new $.ig.OlapResultAxis(tuples, tupleSize);
			$ret.name(name);
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var tuples = $.ig.ArrayJsonSerializer.prototype.instance().toJsonArray$1($.ig.OlapResultTuple.prototype.$type, o.tuples(), $.ig.TupleJsonTypeSeriazlier.prototype.instance());
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "t", tuples);
		return jsonObj;
	}
	,
	$type: new $.ig.Type('AxisJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.OlapResultAxis.prototype.$type))
}, true);

$.ig.util.defType('AxisMemberJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.OlapResultAxisMember.prototype.$type);
	},
	instance: function () {
		return $.ig.AxisMemberJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.OlapResultAxisMember();
			$ret.hierarchyUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "hun"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.levelUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lun"));
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.levelNumber($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ln")));
			$ret.displayInfo($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "di")));
			$ret.properties($.ig.JsonSerializerUtils.prototype.getStringDictionaryProperty(o, "p"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "hun", o.hierarchyUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lun", o.levelUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ln", o.levelNumber());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "di", o.displayInfo());
		$.ig.JsonSerializerUtils.prototype.setStringDictionaryProperty(jsonObj, "p", o.properties());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('AxisMemberJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.OlapResultAxisMember.prototype.$type))
}, true);

$.ig.util.defType('CatalogJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Catalog.prototype.$type);
	},
	instance: function () {
		return $.ig.CatalogJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Catalog();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('CatalogJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Catalog.prototype.$type))
}, true);

$.ig.util.defType('CellJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.OlapResultCell.prototype.$type);
	},
	instance: function () {
		return $.ig.CellJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.OlapResultCell();
			$ret.cellOrdinal($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "co")));
			$ret.properties($.ig.JsonSerializerUtils.prototype.getObjectDictionaryProperty(o, "p"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "co", o.cellOrdinal());
		$.ig.JsonSerializerUtils.prototype.setObjectDictionaryProperty(jsonObj, "p", o.properties());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('CellJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.OlapResultCell.prototype.$type))
}, true);

$.ig.util.defType('CubeJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Cube.prototype.$type);
	},
	instance: function () {
		return $.ig.CubeJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Cube();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.catalogName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cn"));
			$ret.cubeType($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ct")));
			$ret.lastProcessed($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lp"));
			$ret.lastUpdated($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lu"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cn", o.catalogName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ct", $.ig.CubeType.prototype.getBox(o.cubeType()));
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lp", o.lastProcessed());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lu", o.lastUpdated());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('CubeJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Cube.prototype.$type))
}, true);

$.ig.util.defType('DimensionJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Dimension.prototype.$type);
	},
	instance: function () {
		return $.ig.DimensionJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Dimension();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.dimensionType($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dt")));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dt", $.ig.DimensionType.prototype.getBox(o.dimensionType()));
		return jsonObj;
	}
	,
	$type: new $.ig.Type('DimensionJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Dimension.prototype.$type))
}, true);

$.ig.util.defType('HierarchyJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Hierarchy.prototype.$type);
	},
	instance: function () {
		return $.ig.HierarchyJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Hierarchy();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.allMember($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "am"));
			$ret.defaultMember($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dm"));
			$ret.dimensionUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dun"));
			$ret.hierarchyDisplayFolder($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "hdf"));
			$ret.hierarchyOrigin($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ho")));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "am", o.allMember());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dm", o.defaultMember());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dun", o.dimensionUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "hdf", o.hierarchyDisplayFolder());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ho", $.ig.HierarchyOrigin.prototype.getBox(o.hierarchyOrigin()));
		return jsonObj;
	}
	,
	$type: new $.ig.Type('HierarchyJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Hierarchy.prototype.$type))
}, true);

$.ig.util.defType('JsonSerializerUtils', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	createObject: function () {
		return $.ig.jQueryUtils.prototype.createJavascriptObject();
	}
	,
	getObjectProperty: function (o, property) {
		return $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, property);
	}
	,
	setObjectProperty: function (o, property, value) {
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(o, property, value);
	}
	,
	getObjectDictionaryProperty: function (o, property) {
		return $.ig.jQueryUtils.prototype.convertToObjectDictionary($.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, property));
	}
	,
	setObjectDictionaryProperty: function (o, property, value) {
		var obj = $.ig.jQueryUtils.prototype.convertFromObjectDictionary(value);
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(o, property, obj);
	}
	,
	getStringDictionaryProperty: function (o, property) {
		return $.ig.jQueryUtils.prototype.convertToStringDictionary($.ig.jQueryUtils.prototype.getJavascriptObjectProperty(o, property));
	}
	,
	setStringDictionaryProperty: function (o, property, value) {
		var obj = $.ig.jQueryUtils.prototype.convertFromStringDictionary(value);
		$.ig.jQueryUtils.prototype.setJavascriptObjectProperty(o, property, obj);
	}
	,
	$type: new $.ig.Type('JsonSerializerUtils', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KpiJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Kpi.prototype.$type);
	},
	instance: function () {
		return $.ig.KpiJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Kpi();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.kpiDisplayFolder($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "kdf"));
			$ret.measureGroupName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mgn"));
			$ret.kpiStatusGraphic($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ksg"));
			$ret.kpiTrendGraphic($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ktg"));
			$ret.parentKpiName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "pkn"));
			$ret.kpiGoal($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "kg"));
			$ret.kpiStatus($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ks"));
			$ret.kpiTrend($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "kt"));
			$ret.kpiValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "kv"));
			$ret.kpiWeight($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "kw"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "kdf", o.kpiDisplayFolder());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mgn", o.measureGroupName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ksg", o.kpiStatusGraphic());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ktg", o.kpiTrendGraphic());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "pkn", o.parentKpiName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "kg", o.kpiGoal());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ks", o.kpiStatus());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "kt", o.kpiTrend());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "kv", o.kpiValue());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "kw", o.kpiWeight());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('KpiJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Kpi.prototype.$type))
}, true);

$.ig.util.defType('LevelJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Level.prototype.$type);
	},
	instance: function () {
		return $.ig.LevelJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Level();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.depth($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d")));
			$ret.dimensionUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dun"));
			$ret.hierarchyUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "hun"));
			$ret.levelOrderingProperty($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lop"));
			$ret.levelOrigin($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lo")));
			$ret.membersCount($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mc")));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.depth());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dun", o.dimensionUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "hun", o.hierarchyUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lop", o.levelOrderingProperty());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lo", o.levelOrigin());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mc", o.membersCount());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('LevelJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Level.prototype.$type))
}, true);

$.ig.util.defType('MeasureGroupDimensionJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.MeasureGroupDimension.prototype.$type);
	},
	instance: function () {
		return $.ig.MeasureGroupDimensionJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.MeasureGroupDimension();
			$ret.catalogName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cn"));
			$ret.cubeName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cbn"));
			$ret.dimensionCardinality($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dc")));
			$ret.dimensionGranularity($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dg"));
			$ret.dimensionPath($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dap"));
			$ret.dimensionUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dun"));
			$ret.isDimensionVisible($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "idv"));
			$ret.isFactDimension($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "iafd"));
			$ret.measureGroupCardinality($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mgc")));
			$ret.measureGroupName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mgn"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cn", o.catalogName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cbn", o.cubeName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dc", $.ig.CardinalityType.prototype.getBox(o.dimensionCardinality()));
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dg", o.dimensionGranularity());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dap", o.dimensionPath());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dun", o.dimensionUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "idv", o.isDimensionVisible());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "iafd", o.isFactDimension());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mgc", $.ig.CardinalityType.prototype.getBox(o.measureGroupCardinality()));
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mgn", o.measureGroupName());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('MeasureGroupDimensionJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.MeasureGroupDimension.prototype.$type))
}, true);

$.ig.util.defType('MeasureGroupJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.MeasureGroup.prototype.$type);
	},
	instance: function () {
		return $.ig.MeasureGroupJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.MeasureGroup();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.catalogName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cn"));
			$ret.cubeName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cbn"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cn", o.catalogName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cbn", o.cubeName());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('MeasureGroupJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type))
}, true);

$.ig.util.defType('MeasureJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Measure.prototype.$type);
	},
	instance: function () {
		return $.ig.MeasureJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		return (function () {
			var $ret = new $.ig.Measure();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.aggregatorType($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "at")));
			$ret.defaultFormatString($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dfs"));
			$ret.measureDisplayFolder($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mdf"));
			$ret.measureGroupName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mgn"));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "at", $.ig.AggregatorType.prototype.getBox(o.aggregatorType()));
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dfs", o.defaultFormatString());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mdf", o.measureDisplayFolder());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mgn", o.measureGroupName());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('MeasureJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Measure.prototype.$type))
}, true);

$.ig.util.defType('MemberJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.Member.prototype.$type);
	},
	instance: function () {
		return $.ig.MemberJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		var nullValue = $.ig.util.toNullable($.ig.Number.prototype.$type, null);
		var scope = $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "s");
		return (function () {
			var $ret = new $.ig.Member();
			$ret.caption($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"));
			$ret.description($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "d"));
			$ret.name($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "n"));
			$ret.uniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "un"));
			$ret.catalogName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cn"));
			$ret.childrenCardinality($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cc")));
			$ret.cubeName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "cbn"));
			$ret.dimensionUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "dun"));
			$ret.hierarchyUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "hun"));
			$ret.levelDepth($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ld")));
			$ret.levelUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "lun"));
			$ret.memberType($.ig.util.getEnumValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "mt")));
			$ret.parentLevel($.ig.util.getValue($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "pl")));
			$ret.parentUniqueName($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "pun"));
			$ret.scope(scope == null ? nullValue : $.ig.util.toNullable($.ig.Number.prototype.$type, $.ig.Number.prototype.parseInt(scope)));
			return $ret;
		}());
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", o.caption());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "d", o.description());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "n", o.name());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "un", o.uniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cn", o.catalogName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cc", o.childrenCardinality());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "cbn", o.cubeName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "dun", o.dimensionUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "hun", o.hierarchyUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ld", o.levelDepth());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "lun", o.levelUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "mt", $.ig.MemberType.prototype.getBox(o.memberType()));
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "pl", o.parentLevel());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "pun", o.parentUniqueName());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "s", $.ig.util.unwrapNullable(o.scope()));
		return jsonObj;
	}
	,
	$type: new $.ig.Type('MemberJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.Member.prototype.$type))
}, true);

$.ig.util.defType('RemoteRequest', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_type: 0,
	type: function (value) {
		if (arguments.length === 1) {
			this._type = value;
			return value;
		} else {
			return this._type;
		}
	}
	,
	_properties: null,
	properties: function (value) {
		if (arguments.length === 1) {
			this._properties = value;
			return value;
		} else {
			return this._properties;
		}
	}
	,
	_restrictions: null,
	restrictions: function (value) {
		if (arguments.length === 1) {
			this._restrictions = value;
			return value;
		} else {
			return this._restrictions;
		}
	}
	,
	_parameters: null,
	parameters: function (value) {
		if (arguments.length === 1) {
			this._parameters = value;
			return value;
		} else {
			return this._parameters;
		}
	}
	,
	_statement: null,
	statement: function (value) {
		if (arguments.length === 1) {
			this._statement = value;
			return value;
		} else {
			return this._statement;
		}
	}
	,
	$type: new $.ig.Type('RemoteRequest', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RemoteRequestJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.RemoteRequest.prototype.$type);
	},
	instance: function () {
		return $.ig.RemoteRequestJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		var rr = new $.ig.RemoteRequest();
		rr.type($.ig.util.getEnumValue((($.ig.util.cast($.ig.Dictionary$2.prototype.$type.specialize(String, $.ig.Object.prototype.$type), $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "t"))).item("_v"))));
		rr.properties($.ig.JsonSerializerUtils.prototype.getStringDictionaryProperty(o, "p"));
		rr.restrictions($.ig.JsonSerializerUtils.prototype.getStringDictionaryProperty(o, "r"));
		rr.parameters($.ig.JsonSerializerUtils.prototype.getStringDictionaryProperty(o, "par"));
		rr.statement($.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "s") != null ? $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "s") : null);
		return rr;
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "t", $.ig.RemoteRequestType.prototype.getBox(o.type()));
		$.ig.JsonSerializerUtils.prototype.setStringDictionaryProperty(jsonObj, "p", o.properties());
		$.ig.JsonSerializerUtils.prototype.setStringDictionaryProperty(jsonObj, "r", o.restrictions());
		$.ig.JsonSerializerUtils.prototype.setStringDictionaryProperty(jsonObj, "par", o.parameters());
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "s", o.statement());
		return jsonObj;
	}
	,
	$type: new $.ig.Type('RemoteRequestJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.RemoteRequest.prototype.$type))
}, true);

$.ig.util.defType('ResultJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.OlapResult.prototype.$type);
	},
	instance: function () {
		return $.ig.ResultJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		var isEmpty = $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "ie");
		if (isEmpty) {
			return (function () {
				var $ret = new $.ig.OlapResult();
				$ret.isEmpty(true);
				return $ret;
			}());
		} else {
			var axes = $.ig.ArrayJsonSerializer.prototype.instance().fromJsonArray$1($.ig.OlapResultAxis.prototype.$type, $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "a"), $.ig.AxisJsonTypeSeriazlier.prototype.instance());
			var cells = $.ig.ArrayJsonSerializer.prototype.instance().fromJsonArray$1($.ig.OlapResultCell.prototype.$type, $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "c"), $.ig.CellJsonTypeSeriazlier.prototype.instance());
			return (function () {
				var $ret = new $.ig.OlapResult();
				$ret.axes(axes);
				$ret.cells(cells);
				return $ret;
			}());
		}
	}
	,
	toJsonObject: function (o) {
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "ie", o.isEmpty());
		if (!o.isEmpty()) {
			var axes = $.ig.ArrayJsonSerializer.prototype.instance().toJsonArray$1($.ig.OlapResultAxis.prototype.$type, o.axes(), $.ig.AxisJsonTypeSeriazlier.prototype.instance());
			var cells = $.ig.ArrayJsonSerializer.prototype.instance().toJsonArray$1($.ig.OlapResultCell.prototype.$type, o.cells(), $.ig.CellJsonTypeSeriazlier.prototype.instance());
			$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "a", axes);
			$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "c", cells);
		}
		return jsonObj;
	}
	,
	$type: new $.ig.Type('ResultJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.OlapResult.prototype.$type))
}, true);

$.ig.util.defType('TupleJsonTypeSeriazlier', 'JsonTypeSeriazlierBase$1', {
	init: function () {
		$.ig.JsonTypeSeriazlierBase$1.prototype.init.call(this, $.ig.OlapResultTuple.prototype.$type);
	},
	instance: function () {
		return $.ig.TupleJsonTypeSeriazlier.prototype.__instance;
	}
	,
	fromJsonObject: function (o) {
		var members = $.ig.ArrayJsonSerializer.prototype.instance().fromJsonArray$1($.ig.OlapResultAxisMember.prototype.$type, $.ig.JsonSerializerUtils.prototype.getObjectProperty(o, "m"), $.ig.AxisMemberJsonTypeSeriazlier.prototype.instance());
		var tuple = new $.ig.OlapResultTuple(members);
		var en = members.getEnumerator();
		while (en.moveNext()) {
			var axisMember = en.current();
			axisMember.position(tuple);
		}
		return tuple;
	}
	,
	toJsonObject: function (o) {
		var members = $.ig.ArrayJsonSerializer.prototype.instance().toJsonArray$1($.ig.OlapResultAxisMember.prototype.$type, o.members(), $.ig.AxisMemberJsonTypeSeriazlier.prototype.instance());
		var jsonObj = $.ig.JsonSerializerUtils.prototype.createObject();
		$.ig.JsonSerializerUtils.prototype.setObjectProperty(jsonObj, "m", members);
		return jsonObj;
	}
	,
	$type: new $.ig.Type('TupleJsonTypeSeriazlier', $.ig.JsonTypeSeriazlierBase$1.prototype.$type.specialize($.ig.OlapResultTuple.prototype.$type))
}, true);

$.ig.util.defType('RemoteXmlaDataProvder', 'Object', {
	__serverUrl: null,
	__credentials: null,
	__requestSerializer: null,
	__responseSerializer: null,
	init: function (serverUrl, credentials, requestSerializer, responseSerializer) {
		$.ig.Object.prototype.init.call(this);
		this.__serverUrl = serverUrl;
		this.__credentials = credentials;
		this.__requestSerializer = requestSerializer;
		this.__responseSerializer = responseSerializer;
	},
	discoverCatalogsAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverCatalogs, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Catalog.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverCatalogs);
		});
	}
	,
	discoverCubesAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverCubes, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Cube.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverCubes);
		});
	}
	,
	discoverDimensionsAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverDimensions, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Dimension.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverDimensions);
		});
	}
	,
	discoverHierarchiesAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverHierarchies, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Hierarchy.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverHierarchies);
		});
	}
	,
	discoverLevelsAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverLevels, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Level.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverLevels);
		});
	}
	,
	discoverMeasuresAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverMeasures, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Measure.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverMeasures);
		});
	}
	,
	discoverMeasureGroupsAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverMeasureGroups, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverMeasureGroups);
		});
	}
	,
	discoverMeasureGroupDimensionsAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverMeasureGroupDimensions, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.MeasureGroupDimension.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverMeasureGroupDimensions);
		});
	}
	,
	discoverMembersAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverMembers, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Member.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverMembers);
		});
	}
	,
	discoverKpisAsync: function (properties, restrictions) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.discoverKpis, properties, restrictions, null, null).continueWith$11($.ig.IEnumerable$1.prototype.$type.specialize($.ig.Kpi.prototype.$type), function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.discoverKpis);
		});
	}
	,
	executeStatementAsync: function (statement, properties, parameters) {
		var responseSerializer = this.__responseSerializer;
		return this.sendRequest($.ig.RemoteRequestType.prototype.executeStatement, properties, null, parameters, statement).continueWith$11($.ig.OlapResult.prototype.$type, function (t) {
			return responseSerializer.deserializeResponse(t.result(), $.ig.RemoteRequestType.prototype.executeStatement);
		});
	}
	,
	sendRequest: function (type, properties, restrictions, parameters, statement) {
		var $self = this;
		var webClient = (function () {
			var $ret = new $.ig.WebClient();
			$ret.credentials($self.__credentials);
			return $ret;
		}());
		var requestData = this.__requestSerializer.serializeRequest((function () {
			var $ret = new $.ig.RemoteRequest();
			$ret.type(type);
			$ret.properties($self.dictionaryFromKVPCollection(properties));
			$ret.restrictions($self.dictionaryFromKVPCollection(restrictions));
			$ret.parameters($self.dictionaryFromKVPCollection(parameters));
			$ret.statement(statement);
			return $ret;
		}()));
		var tcs = new $.ig.TaskCompletionSource$1(String, 0);
		webClient.uploadStringCompleted = $.ig.Delegate.prototype.combine(webClient.uploadStringCompleted, this.uploadStringAsyncCompleted.runOn(this));
		webClient.uploadStringAsync(new $.ig.Uri(0, this.__serverUrl), "POST", requestData, tcs);
		return tcs.task();
	}
	,
	uploadStringAsyncCompleted: function (sender, e) {
		var tcs = e.userState();
		if (e.cancelled()) {
			tcs.setCanceled();
		} else if (e.error() != null) {
			tcs.setException(e.error());
		} else {
			tcs.setResult(e.result());
		}
	}
	,
	dictionaryFromKVPCollection: function (collection) {
		if (collection == null) {
			return null;
		}
		var dict = new $.ig.Dictionary$2(String, String, 0);
		var en = collection.getEnumerator();
		while (en.moveNext()) {
			var item = en.current();
			dict.add(item.key(), item.value());
		}
		return dict;
	}
	,
	$type: new $.ig.Type('RemoteXmlaDataProvder', $.ig.Object.prototype.$type, [$.ig.IXmlaDataProvider.prototype.$type])
}, true);

$.ig.util.defType('XmlaConnection', 'Object', {
	__serverUri: null,
	__credentials: null,
	init: function (serverUri, credentials) {
		$.ig.Object.prototype.init.call(this);
		this.__serverUri = serverUri;
		this.__credentials = credentials;
	},
	getXmlaSoapWebClient$1: function ($t) {
		var client = new $.ig.XmlaSoapWebClient$1($t, this.__serverUri, $.ig.Encoding.prototype.uTF8());
		client.credentials(this.__credentials);
		return client;
	}
	,
	$type: new $.ig.Type('XmlaConnection', $.ig.Object.prototype.$type, [$.ig.ICoreXmlaConnection.prototype.$type])
}, true);

$.ig.util.defType('XmlaDataSourceImpl', 'MdxDataSourceImpl', {
	init: function (options) {
		$.ig.MdxDataSourceImpl.prototype.init.call(this, options);
		this.xmlaSourceOptions(options);
	},
	_xmlaSourceOptions: null,
	xmlaSourceOptions: function (value) {
		if (arguments.length === 1) {
			this._xmlaSourceOptions = value;
			return value;
		} else {
			return this._xmlaSourceOptions;
		}
	}
	,
	loadCubes: function (catalogName) {
		var $self = this;
		var provider = this.dataProviderFactory().createDataProvider();
		var restrictions = this.getProviderDefaultRestrictions();
		this.ensureEntryValue(restrictions, $.ig.CubeRestrictions.prototype.catalogName, catalogName);
		var properties = this.getProviderDefaultProperties();
		this.ensureEntryValue(properties, $.ig.XmlaDiscoverProperties.prototype.catalog, catalogName);
		var task = provider.discoverCubesAsync(properties, restrictions).continueWith$11($.ig.IList$1.prototype.$type.specialize($.ig.Cube.prototype.$type), function (t) {
			$self.cubes($self.toArrayListCollection$1($.ig.Cube.prototype.$type, t.result()));
			return $self.cubes();
		});
		return task;
	}
	,
	loadMeasureGroups: function () {
		var $self = this;
		var catalogName = this.catalog().name();
		var cubeName = this.cube().name();
		var provider = this.dataProviderFactory().createDataProvider();
		var restrictions = this.getProviderDefaultRestrictions();
		this.ensureEntryValue(restrictions, $.ig.MeasureGroupRestrictions.prototype.catalogName, catalogName);
		this.ensureEntryValue(restrictions, $.ig.MeasureGroupRestrictions.prototype.cubeName, cubeName);
		var properties = this.getProviderDefaultProperties();
		this.ensureEntryValue(properties, $.ig.XmlaDiscoverProperties.prototype.catalog, catalogName);
		var task = provider.discoverMeasureGroupsAsync(properties, restrictions).continueWith$11($.ig.IList$1.prototype.$type.specialize($.ig.MeasureGroup.prototype.$type), function (t) {
			$self.measureGroups($self.toArrayListCollection$1($.ig.MeasureGroup.prototype.$type, t.result()));
			return $self.measureGroups();
		});
		return task;
	}
	,
	createOlapDataProviderFactory: function () {
		return new $.ig.DefaultXmlaDataProviderFactory(this.xmlaSourceOptions().serverUrl(), this.xmlaSourceOptions().requestOptions());
	}
	,
	getProviderDefaultProperties: function () {
		var properties = new $.ig.List$1($.ig.KeyValueItem.prototype.$type, 0);
		if (this.mdxSourceOptions().discoverProperties() != null) {
			var en = this.mdxSourceOptions().discoverProperties().getEnumerator();
			while (en.moveNext()) {
				var kvp = en.current();
				var xmlaQueryProperty = new $.ig.XmlaQueryProperty(1, kvp.key(), kvp.value());
				properties.add((function () {
					var $ret = new $.ig.KeyValueItem();
					$ret.key(xmlaQueryProperty.propertyName());
					$ret.value(xmlaQueryProperty.value());
					return $ret;
				}()));
			}
		}
		if (this.catalog() != null) {
			this.ensureEntryValue(properties, $.ig.XmlaDiscoverProperties.prototype.catalog, this.catalog().name());
		}
		return properties;
	}
	,
	$type: new $.ig.Type('XmlaDataSourceImpl', $.ig.MdxDataSourceImpl.prototype.$type)
}, true);

$.ig.util.defType('XmlaDiscoverProperties', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	$type: new $.ig.Type('XmlaDiscoverProperties', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XmlaDataSourceOptions', 'MdxDataSourceOptions', {
	init: function () {
		$.ig.MdxDataSourceOptions.prototype.init.call(this);
	},
	_serverUrl: null,
	serverUrl: function (value) {
		if (arguments.length === 1) {
			this._serverUrl = value;
			return value;
		} else {
			return this._serverUrl;
		}
	}
	,
	_requestOptions: null,
	requestOptions: function (value) {
		if (arguments.length === 1) {
			this._requestOptions = value;
			return value;
		} else {
			return this._requestOptions;
		}
	}
	,
	$type: new $.ig.Type('XmlaDataSourceOptions', $.ig.MdxDataSourceOptions.prototype.$type)
}, true);

$.ig.util.defType('RequestOptions', 'NetworkCredential', {
	init: function (initNumber, userName, password) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.RequestOptions.prototype.init1.call(this, 1, userName, password, String.empty());
	},
	init1: function (initNumber, userName, password, domain) {
		$.ig.NetworkCredential.prototype.init1.call(this, 1, userName, password, domain);
	},
	$type: new $.ig.Type('RequestOptions', $.ig.NetworkCredential.prototype.$type)
}, true);

$.ig.util.defType('CustomRemoteXmlaRequestSerializer', 'Object', {
	__serializeRequest: null,
	init: function (serializeRequest) {
		$.ig.Object.prototype.init.call(this);
		this.__serializeRequest = serializeRequest;
	},
	serializeRequest: function (request) {
		return this.__serializeRequest(request);
	}
	,
	deserializeRequest: function (request) {
		throw new $.ig.NotSupportedException(1);
	}
	,
	$type: new $.ig.Type('CustomRemoteXmlaRequestSerializer', $.ig.Object.prototype.$type, [$.ig.IRemoteRequestSerializer.prototype.$type])
}, true);

$.ig.util.defType('CustomRemoteXmlaResponseSerializer', 'Object', {
	__deserializeResponse: null,
	init: function (deserializeResponse) {
		$.ig.Object.prototype.init.call(this);
		this.__deserializeResponse = deserializeResponse;
	},
	serializeResponse: function (response, type) {
		throw new $.ig.NotSupportedException(1);
	}
	,
	deserializeResponse: function (response, type) {
		return this.__deserializeResponse(response, type);
	}
	,
	$type: new $.ig.Type('CustomRemoteXmlaResponseSerializer', $.ig.Object.prototype.$type, [$.ig.IRemoteResponseSerializer.prototype.$type])
}, true);

$.ig.util.defType('OlapXmlaDataSource', 'DataSourceBase', {
	staticInit: function () {
		$.ig.OlapXmlaDataSource.prototype.xmlaDataSourceIE8Fixes();
	},
	init: function (options) {
		$.ig.DataSourceBase.prototype.init.call(this);
		var serverUrl = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "serverUrl");
		var catalog = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "catalog");
		var cube = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "cube");
		var measures = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "measures");
		var measureGroup = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "measureGroup");
		var filters = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "filters");
		var columns = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "columns");
		var rows = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "rows");
		var requestOptionsObject = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "requestOptions");
		var enableResultCacheValue = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "enableResultCache");
		var enableResultCache = true;
		if (enableResultCacheValue != null) {
			enableResultCache = enableResultCacheValue;
		}
		var discoverProperties = $.ig.jQueryUtils.prototype.convertToStringDictionary($.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "discoverProperties"));
		var executeProperties = $.ig.jQueryUtils.prototype.convertToStringDictionary($.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "executeProperties"));
		var isRemote = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "isRemote");
		if (isRemote) {
			if (requestOptionsObject == null) {
				requestOptionsObject = {};
			}
			var ro_ = requestOptionsObject;
			ro_.isCrossDomain = false;
		}
		var serializeRequest = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "serializeRequest");
		var deserializeResponse = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "deserializeResponse");
		var mdxSettings = this.resolveMdxSettings(options);
		var dataSourceOptions = (function () {
			var $ret = new $.ig.XmlaDataSourceOptions();
			$ret.serverUrl(serverUrl);
			$ret.requestOptions(requestOptionsObject);
			$ret.catalog(catalog);
			$ret.cube(cube);
			$ret.measures(measures);
			$ret.measureGroup(measureGroup);
			$ret.filters(filters);
			$ret.columns(columns);
			$ret.rows(rows);
			$ret.discoverProperties(discoverProperties);
			$ret.executeProperties(executeProperties);
			$ret.enableResultCache(enableResultCache);
			$ret.mdxSettings(mdxSettings);
			return $ret;
		}());
		if (isRemote) {
			var requestSerializer = null;
			var responseSerializer = null;
			if (serializeRequest != null) {
				requestSerializer = new $.ig.CustomRemoteXmlaRequestSerializer(serializeRequest);
			}
			if (deserializeResponse != null) {
				responseSerializer = new $.ig.CustomRemoteXmlaResponseSerializer(deserializeResponse);
			}
			var remoteProviderFactory = new $.ig.RemoteXmlaDataProviderFactory(1, serverUrl, requestOptionsObject);
			dataSourceOptions.dataProviderFactory(remoteProviderFactory);
		}
		this.dataSource(new $.ig.XmlaDataSourceImpl(dataSourceOptions));
	},
	catalogs: function () {
		return ((this.dataSource()).catalogs()).inner();
	}
	,
	catalog: function () {
		return (this.dataSource()).catalog();
	}
	,
	setCatalog: function (catalogName) {
		return (this.dataSource()).setCatalog(catalogName).continueWith$11(Array, function (t) {
			return (t.result()).inner();
		}).promise();
	}
	,
	measureGroups: function () {
		return ((this.dataSource()).measureGroups()).inner();
	}
	,
	measureGroup: function () {
		return (this.dataSource()).measureGroup();
	}
	,
	setMeasureGroup: function (measureGroupName) {
		return (this.dataSource()).setMeasureGroup(measureGroupName).continueWith$11($.ig.OlapMetadataTreeItem.prototype.$type, function (t) {
			return t.result();
		}).promise();
	}
	,
	resolveMdxSettings: function (options) {
		var mdxSettings = new $.ig.MdxSettings();
		var mdxSettingsObject = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(options, "mdxSettings");
		if (mdxSettingsObject == null) {
			return mdxSettings;
		}
		var nonEmptyOnRows = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "nonEmptyOnRows");
		if (nonEmptyOnRows != null) {
			mdxSettings.nonEmptyOnRows(nonEmptyOnRows);
		}
		var nonEmptyOnColumns = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "nonEmptyOnColumns");
		if (nonEmptyOnColumns != null) {
			mdxSettings.nonEmptyOnColumns(nonEmptyOnColumns);
		}
		var addCalcMembersOnRows = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "addCalculatedMembersOnRows");
		if (addCalcMembersOnRows != null) {
			mdxSettings.addCalculatedMembersOnRows(addCalcMembersOnRows);
		}
		var addCalcMembersOnColumns = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "addCalculatedMembersOnColumns");
		if (addCalcMembersOnColumns != null) {
			mdxSettings.addCalculatedMembersOnColumns(addCalcMembersOnColumns);
		}
		var dimensionPropertiesOnRows = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "dimensionPropertiesOnRows");
		if (dimensionPropertiesOnRows != null) {
			for (var i = 0; i < dimensionPropertiesOnRows.count(); i++) {
				mdxSettings.dimensionPropertiesOnRows().add(dimensionPropertiesOnRows.item(i));
			}
		}
		var dimensionPropertiesOnColumns = $.ig.jQueryUtils.prototype.getJavascriptObjectProperty(mdxSettingsObject, "dimensionPropertiesOnColumns");
		if (dimensionPropertiesOnColumns != null) {
			for (var i1 = 0; i1 < dimensionPropertiesOnColumns.count(); i1++) {
				mdxSettings.dimensionPropertiesOnColumns().add(dimensionPropertiesOnColumns.item(i1));
			}
		}
		return mdxSettings;
	}
	,
	xmlaDataSourceIE8Fixes: function () {
		
            $.ig.XObject.prototype.toString = function () {
                if (this.backingNode() != null) {
                    return $.ig.XmlUtils.prototype.xmlNodeToString(this.backingNode());

                } else {
                    return '';
                }
            };
	}
	,
	$type: new $.ig.Type('OlapXmlaDataSource', $.ig.DataSourceBase.prototype.$type)
}, true);

$.ig.util.defType('MdxAxis___GetMdxSets__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: null,
	__e_5_0: null,
	__4__this: null,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__e_5_0 = this.__4__this.__sets.getEnumerator();
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__e_5_0.moveNext()) {
						this.__2__current = this.__e_5_0.current();
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
			d__ = new $.ig.MdxAxis___GetMdxSets__IteratorClass(0);
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
	$type: new $.ig.Type('MdxAxis___GetMdxSets__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.IMdxSet.prototype.$type), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize($.ig.IMdxSet.prototype.$type), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('MdxCalculatedMembersCache___GetMemberAliasExpressionPairs__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: null,
	__e_5_0: null,
	__4__this: null,
	init: function (_1__state) {
		this.__2__current = new $.ig.KeyValuePair$2(String, String);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__e_5_0 = this.__4__this.__memberExpression.getEnumerator();
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__e_5_0.moveNext()) {
						this.__2__current = this.__e_5_0.current();
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
			d__ = new $.ig.MdxCalculatedMembersCache___GetMemberAliasExpressionPairs__IteratorClass(0);
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
	$type: new $.ig.Type('MdxCalculatedMembersCache___GetMemberAliasExpressionPairs__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(String, String)), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize($.ig.KeyValuePair$2.prototype.$type.specialize(String, String)), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('MdxSet___GetMdxElements__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: null,
	__e_5_0: null,
	__4__this: null,
	init: function (_1__state) {
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	moveNext: function () {
		while (this.__1__state >= 0) {
			switch (this.__1__state) {
				case 0:
					this.__1__state = -1;
					this.__e_5_0 = this.__4__this.__elementsList.getEnumerator();
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__e_5_0.moveNext()) {
						this.__2__current = this.__e_5_0.current();
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
			d__ = new $.ig.MdxSet___GetMdxElements__IteratorClass(0);
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
	$type: new $.ig.Type('MdxSet___GetMdxElements__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.IMdxElement.prototype.$type), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize($.ig.IMdxElement.prototype.$type), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.RemoteRequestType.prototype.discoverCatalogs = 0;
$.ig.RemoteRequestType.prototype.discoverCubes = 1;
$.ig.RemoteRequestType.prototype.discoverDimensions = 2;
$.ig.RemoteRequestType.prototype.discoverHierarchies = 3;
$.ig.RemoteRequestType.prototype.discoverLevels = 4;
$.ig.RemoteRequestType.prototype.discoverMeasures = 5;
$.ig.RemoteRequestType.prototype.discoverMeasureGroups = 6;
$.ig.RemoteRequestType.prototype.discoverMeasureGroupDimensions = 7;
$.ig.RemoteRequestType.prototype.discoverMembers = 8;
$.ig.RemoteRequestType.prototype.discoverKpis = 9;
$.ig.RemoteRequestType.prototype.executeStatement = 10;

$.ig.MdxElementType.prototype.member = 0;
$.ig.MdxElementType.prototype.memberTree = 1;
$.ig.MdxElementType.prototype.calculated = 2;
$.ig.MdxElementType.prototype.filter = 3;
$.ig.MdxElementType.prototype.collection = 4;
$.ig.MdxElementType.prototype.userDefined = 5;

$.ig.XmlaSoapMessageHeader.prototype.none = 0;
$.ig.XmlaSoapMessageHeader.prototype.session = 1;
$.ig.XmlaSoapMessageHeader.prototype.beginSession = 2;
$.ig.XmlaSoapMessageHeader.prototype.endSession = 3;

$.ig.CatalogConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.CatalogConstants.prototype.description = "DESCRIPTION";
$.ig.CatalogConstants.prototype.roles = "ROLES";
$.ig.CatalogConstants.prototype.dateModified = "DATE_MODIFIED";

$.ig.DbSchemaConstants.prototype.catalogs = "DBSCHEMA_CATALOGS";
$.ig.DbSchemaConstants.prototype.columns = "DBSCHEMA_COLUMNS";
$.ig.DbSchemaConstants.prototype.tables = "DBSCHEMA_TABLES";
$.ig.DbSchemaConstants.prototype.providerTypes = "DBSCHEMA_PROVIDER_TYPES";

$.ig.CubeConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.CubeConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.CubeConstants.prototype.cubeName = "CUBE_NAME";
$.ig.CubeConstants.prototype.cubeType = "CUBE_TYPE";
$.ig.CubeConstants.prototype.lastSchemaUpdate = "LAST_SCHEMA_UPDATE";
$.ig.CubeConstants.prototype.lastDataUpdate = "LAST_DATA_UPDATE";
$.ig.CubeConstants.prototype.description = "DESCRIPTION";
$.ig.CubeConstants.prototype.isDrillThroughEnabled = "IS_DRILLTHROUGH_ENABLED";
$.ig.CubeConstants.prototype.isLinkable = "IS_LINKABLE";
$.ig.CubeConstants.prototype.isWriteEnabled = "IS_WRITE_ENABLED";
$.ig.CubeConstants.prototype.isSQLEnabledConstatnt = "IS_SQL_ENABLED";
$.ig.CubeConstants.prototype.cubeCaption = "CUBE_CAPTION";
$.ig.CubeConstants.prototype.baseCubeName = "BASE_CUBE_NAME";
$.ig.CubeConstants.prototype.annotations = "ANNOTATIONS";

$.ig.DimensionConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.DimensionConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.DimensionConstants.prototype.cubeName = "CUBE_NAME";
$.ig.DimensionConstants.prototype.dimensionName = "DIMENSION_NAME";
$.ig.DimensionConstants.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.DimensionConstants.prototype.dimensionCaption = "DIMENSION_CAPTION";
$.ig.DimensionConstants.prototype.dimensionOrdinal = "DIMENSION_ORDINAL";
$.ig.DimensionConstants.prototype.dimensionType = "DIMENSION_TYPE";
$.ig.DimensionConstants.prototype.dimensionCardinality = "DIMENSION_CARDINALITY";
$.ig.DimensionConstants.prototype.defaultHierarchy = "DEFAULT_HIERARCHY";
$.ig.DimensionConstants.prototype.description = "DESCRIPTION";
$.ig.DimensionConstants.prototype.isVirtual = "IS_VIRTUAL";
$.ig.DimensionConstants.prototype.isReadWrite = "IS_READWRITE";
$.ig.DimensionConstants.prototype.dimensionUniqueSettings = "DIMENSION_UNIQUE_SETTINGS";
$.ig.DimensionConstants.prototype.dimensionMasterName = "DIMENSION_MASTER_NAME";
$.ig.DimensionConstants.prototype.dimensionIsVisible = "DIMENSION_IS_VISIBLE";

$.ig.HierarchyConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.HierarchyConstants.prototype.cubeName = "CUBE_NAME";
$.ig.HierarchyConstants.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.HierarchyConstants.prototype.hierarchyName = "HIERARCHY_NAME";
$.ig.HierarchyConstants.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.HierarchyConstants.prototype.hierarchyCaption = "HIERARCHY_CAPTION";
$.ig.HierarchyConstants.prototype.dimensionType = "DIMENSION_TYPE";
$.ig.HierarchyConstants.prototype.hierarchyCardinality = "HIERARCHY_CARDINALITY";
$.ig.HierarchyConstants.prototype.defaultMember = "DEFAULT_MEMBER";
$.ig.HierarchyConstants.prototype.allMember = "ALL_MEMBER";
$.ig.HierarchyConstants.prototype.description = "DESCRIPTION";
$.ig.HierarchyConstants.prototype.isVirtual = "IS_VIRTUAL";
$.ig.HierarchyConstants.prototype.isReadWrite = "IS_READWRITE";
$.ig.HierarchyConstants.prototype.dimensionUniqueSettings = "DIMENSION_UNIQUE_SETTINGS";
$.ig.HierarchyConstants.prototype.dimensionIsVisible = "DIMENSION_IS_VISIBLE";
$.ig.HierarchyConstants.prototype.dimensionMasterUniqueName = "DIMENSION_MASTER_UNIQUE_NAME";
$.ig.HierarchyConstants.prototype.hierarchyOrdinal = "HIERARCHY_ORDINAL";
$.ig.HierarchyConstants.prototype.dimensionIsShared = "DIMENSION_IS_SHARED";
$.ig.HierarchyConstants.prototype.hierarchyIsVisible = "HIERARCHY_IS_VISIBLE";
$.ig.HierarchyConstants.prototype.hierarchyOrigin = "HIERARCHY_ORIGIN";
$.ig.HierarchyConstants.prototype.hierarchyDisplayFolder = "HIERARCHY_DISPLAY_FOLDER";
$.ig.HierarchyConstants.prototype.instanceSelection = "INSTANCE_SELECTION";
$.ig.HierarchyConstants.prototype.groupingBehavior = "GROUPING_BEHAVIOR";
$.ig.HierarchyConstants.prototype.structureType = "STRUCTURE_TYPE";

$.ig.InstanceConstants.prototype.instanceName = "INSTANCE_NAME";
$.ig.InstanceConstants.prototype.instancePortNumber = "INSTANCE_PORT_NUMBER";
$.ig.InstanceConstants.prototype.instanceState = "INSTANCE_STATE";

$.ig.KpiConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.KpiConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.KpiConstants.prototype.cubeName = "CUBE_NAME";
$.ig.KpiConstants.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.KpiConstants.prototype.kpiName = "KPI_NAME";
$.ig.KpiConstants.prototype.kpiCaption = "KPI_CAPTION";
$.ig.KpiConstants.prototype.kpiDescription = "KPI_DESCRIPTION";
$.ig.KpiConstants.prototype.kpiDisplayFolder = "KPI_DISPLAY_FOLDER";
$.ig.KpiConstants.prototype.kpiValue = "KPI_VALUE";
$.ig.KpiConstants.prototype.kpiGoal = "KPI_GOAL";
$.ig.KpiConstants.prototype.kpiStatus = "KPI_STATUS";
$.ig.KpiConstants.prototype.kpiTrend = "KPI_TREND";
$.ig.KpiConstants.prototype.kpiStatusGraphic = "KPI_STATUS_GRAPHIC";
$.ig.KpiConstants.prototype.kpiTrendGraphic = "KPI_TREND_GRAPHIC";
$.ig.KpiConstants.prototype.kpiWeight = "KPI_WEIGHT";
$.ig.KpiConstants.prototype.kpiCurrentTimeMember = "KPI_CURRENT_TIME_MEMBER";
$.ig.KpiConstants.prototype.kpiParentKpiName = "KPI_PARENT_KPI_NAME";

$.ig.LevelConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.LevelConstants.prototype.cubeName = "CUBE_NAME";
$.ig.LevelConstants.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.LevelConstants.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.LevelConstants.prototype.levelName = "LEVEL_NAME";
$.ig.LevelConstants.prototype.levelUniqueName = "LEVEL_UNIQUE_NAME";
$.ig.LevelConstants.prototype.levelCaption = "LEVEL_CAPTION";
$.ig.LevelConstants.prototype.levelNumber = "LEVEL_NUMBER";
$.ig.LevelConstants.prototype.levelCardinality = "LEVEL_CARDINALITY";
$.ig.LevelConstants.prototype.levelType = "LEVEL_TYPE";
$.ig.LevelConstants.prototype.description = "DESCRIPTION";
$.ig.LevelConstants.prototype.customRollupSettings = "CUSTOM_ROLLUP_SETTINGS";
$.ig.LevelConstants.prototype.levelUniqueSettings = "LEVEL_UNIQUE_SETTINGS";
$.ig.LevelConstants.prototype.levelIsVisible = "LEVEL_IS_VISIBLE";
$.ig.LevelConstants.prototype.levelOrderingProperty = "LEVEL_ORDERING_PROPERTY";
$.ig.LevelConstants.prototype.levelDbType = "LEVEL_DBTYPE";
$.ig.LevelConstants.prototype.levelKeyCardinality = "LEVEL_KEY_CARDINALITY";
$.ig.LevelConstants.prototype.levelOrigin = "LEVEL_ORIGIN";

$.ig.MeasureConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureConstants.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureConstants.prototype.measureName = "MEASURE_NAME";
$.ig.MeasureConstants.prototype.measureUniqueName = "MEASURE_UNIQUE_NAME";
$.ig.MeasureConstants.prototype.measureCaption = "MEASURE_CAPTION";
$.ig.MeasureConstants.prototype.measureAggregator = "MEASURE_AGGREGATOR";
$.ig.MeasureConstants.prototype.dataType = "DATA_TYPE";
$.ig.MeasureConstants.prototype.numericPrecision = "NUMERIC_PRECISION";
$.ig.MeasureConstants.prototype.numericScale = "NUMERIC_SCALE";
$.ig.MeasureConstants.prototype.expression = "EXPRESSION";
$.ig.MeasureConstants.prototype.description = "DESCRIPTION";
$.ig.MeasureConstants.prototype.measureIsVisible = "MEASURE_IS_VISIBLE";
$.ig.MeasureConstants.prototype.measureNameSqlCoulmnName = "MEASURE_NAME_SQL_COLUMN_NAME";
$.ig.MeasureConstants.prototype.measureUnqualifiedCaption = "MEASURE_UNQUALIFIED_CAPTION";
$.ig.MeasureConstants.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.MeasureConstants.prototype.measureDisplayFolder = "MEASURE_DISPLAY_FOLDER";
$.ig.MeasureConstants.prototype.defaultFormatString = "DEFAULT_FORMAT_STRING";

$.ig.MeasureGroupConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureGroupConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureGroupConstants.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureGroupConstants.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.MeasureGroupConstants.prototype.description = "DESCRIPTION";
$.ig.MeasureGroupConstants.prototype.measureGroupCaption = "MEASUREGROUP_CAPTION";

$.ig.MeasureGroupDimensionConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureGroupDimensionConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureGroupDimensionConstants.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureGroupDimensionConstants.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.MeasureGroupDimensionConstants.prototype.measureGroupCardinality = "MEASUREGROUP_CARDINALITY";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionCardinality = "DIMENSION_CARDINALITY";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionIsVisible = "DIMENSION_IS_VISIBLE";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionIsFactDimension = "DIMENSION_IS_FACT_DIMENSION";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionPath = "DIMENSION_PATH";
$.ig.MeasureGroupDimensionConstants.prototype.dimensionGranularity = "DIMENSION_GRANULARITY";

$.ig.MemberConstants.prototype.catalogName = "CATALOG_NAME";
$.ig.MemberConstants.prototype.schemaName = "SCHEMA_NAME";
$.ig.MemberConstants.prototype.cubeName = "CUBE_NAME";
$.ig.MemberConstants.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.MemberConstants.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.MemberConstants.prototype.levelUniqueName = "LEVEL_UNIQUE_NAME";
$.ig.MemberConstants.prototype.levelNumber = "LEVEL_NUMBER";
$.ig.MemberConstants.prototype.memberName = "MEMBER_NAME";
$.ig.MemberConstants.prototype.memberUniqueName = "MEMBER_UNIQUE_NAME";
$.ig.MemberConstants.prototype.memberType = "MEMBER_TYPE";
$.ig.MemberConstants.prototype.memberGuid = "MEMBER_GUID";
$.ig.MemberConstants.prototype.memberCaption = "MEMBER_CAPTION";
$.ig.MemberConstants.prototype.childrenCardinality = "CHILDREN_CARDINALITY";
$.ig.MemberConstants.prototype.parentLevel = "PARENT_LEVEL";
$.ig.MemberConstants.prototype.parentUniqueName = "PARENT_UNIQUE_NAME";
$.ig.MemberConstants.prototype.parentCount = "PARENT_COUNT";
$.ig.MemberConstants.prototype.description = "DESCRIPTION";
$.ig.MemberConstants.prototype.expression = "EXPRESSION";
$.ig.MemberConstants.prototype.memberKey = "MEMBER_KEY";
$.ig.MemberConstants.prototype.isPlaceholderMember = "IS_PLACEHOLDERMEMBER";
$.ig.MemberConstants.prototype.isDataMember = "IS_DATAMEMBER";
$.ig.MemberConstants.prototype.scope = "SCOPE";

$.ig.OlapSchemaConstants.prototype.cubes = "MDSCHEMA_CUBES";
$.ig.OlapSchemaConstants.prototype.dimensions = "MDSCHEMA_DIMENSIONS";
$.ig.OlapSchemaConstants.prototype.measureGroups = "MDSCHEMA_MEASUREGROUPS";
$.ig.OlapSchemaConstants.prototype.measureGroupDimensions = "MDSCHEMA_MEASUREGROUP_DIMENSIONS";
$.ig.OlapSchemaConstants.prototype.measures = "MDSCHEMA_MEASURES";
$.ig.OlapSchemaConstants.prototype.hierarchies = "MDSCHEMA_HIERARCHIES";
$.ig.OlapSchemaConstants.prototype.levels = "MDSCHEMA_LEVELS";
$.ig.OlapSchemaConstants.prototype.members = "MDSCHEMA_MEMBERS";
$.ig.OlapSchemaConstants.prototype.kpis = "MDSCHEMA_KPIS";

$.ig.CubeRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.CubeRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.CubeRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.CubeRestrictions.prototype.cubeSource = "CUBE_SOURCE";

$.ig.DimensionRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.DimensionRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.DimensionRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.DimensionRestrictions.prototype.dimensionName = "DIMENSION_NAME";
$.ig.DimensionRestrictions.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.DimensionRestrictions.prototype.cubeSource = "CUBE_SOURCE";
$.ig.DimensionRestrictions.prototype.dimensionVisibility = "DIMENSION_VISIBILITY";

$.ig.HierarchyRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.HierarchyRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.HierarchyRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.HierarchyRestrictions.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.HierarchyRestrictions.prototype.hierarchyName = "HIERARCHY_NAME";
$.ig.HierarchyRestrictions.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.HierarchyRestrictions.prototype.hierarchyOrigin = "HIERARCHY_ORIGIN";
$.ig.HierarchyRestrictions.prototype.cubeSource = "CUBE_SOURCE";
$.ig.HierarchyRestrictions.prototype.hierarchyVisibility = "HIERARCHY_VISIBILITY";

$.ig.KpiRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.KpiRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.KpiRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.KpiRestrictions.prototype.kpiName = "KPI_NAME";
$.ig.KpiRestrictions.prototype.cubeSource = "CUBE_SOURCE";

$.ig.LevelRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.LevelRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.LevelRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.LevelRestrictions.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.LevelRestrictions.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.LevelRestrictions.prototype.levelName = "LEVEL_NAME";
$.ig.LevelRestrictions.prototype.levelUniqueName = "LEVEL_UNIQUE_NAME";
$.ig.LevelRestrictions.prototype.levelOrigin = "LEVEL_ORIGIN";
$.ig.LevelRestrictions.prototype.cubeSource = "CUBE_SOURCE";
$.ig.LevelRestrictions.prototype.levelVisibility = "LEVEL_VISIBILITY";

$.ig.MeasureRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureRestrictions.prototype.measureName = "MEASURE_NAME";
$.ig.MeasureRestrictions.prototype.measureUniqueName = "MEASURE_UNIQUE_NAME";
$.ig.MeasureRestrictions.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.MeasureRestrictions.prototype.cubeSource = "CUBE_SOURCE";
$.ig.MeasureRestrictions.prototype.measureVisibility = "MEASURE_VISIBILITY";

$.ig.MeasureGroupRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureGroupRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureGroupRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureGroupRestrictions.prototype.measureGroupName = "MEASUREGROUP_NAME";

$.ig.MeasureGroupDimensionRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.MeasureGroupDimensionRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.MeasureGroupDimensionRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.MeasureGroupDimensionRestrictions.prototype.measureGroupName = "MEASUREGROUP_NAME";
$.ig.MeasureGroupDimensionRestrictions.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.MeasureGroupDimensionRestrictions.prototype.dimensionVisibility = "DIMENSION_VISIBILITY";

$.ig.MemberRestrictions.prototype.catalogName = "CATALOG_NAME";
$.ig.MemberRestrictions.prototype.schemaName = "SCHEMA_NAME";
$.ig.MemberRestrictions.prototype.cubeName = "CUBE_NAME";
$.ig.MemberRestrictions.prototype.dimensionUniqueName = "DIMENSION_UNIQUE_NAME";
$.ig.MemberRestrictions.prototype.hierarchyUniqueName = "HIERARCHY_UNIQUE_NAME";
$.ig.MemberRestrictions.prototype.levelUniqueName = "LEVEL_UNIQUE_NAME";
$.ig.MemberRestrictions.prototype.levelNumber = "LEVEL_NUMBER";
$.ig.MemberRestrictions.prototype.memberName = "MEMBER_NAME";
$.ig.MemberRestrictions.prototype.memberUniqueName = "MEMBER_UNIQUE_NAME";
$.ig.MemberRestrictions.prototype.memberType = "MEMBER_TYPE";
$.ig.MemberRestrictions.prototype.treeOp = "TREE_OP";
$.ig.MemberRestrictions.prototype.cubeSource = "CUBE_SOURCE";

$.ig.AxisXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.TupleXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.AxisMemberXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.DimensionXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.HierarchyXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.MeasureGroupDimensionXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.MeasureGroupXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.MeasureXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.MemberXmlTypeSerializer.prototype._xNamesCache = null;

$.ig.XmlaConstants.prototype.schemaCatalogsStringConstant = "DBSCHEMA_CATALOGS";
$.ig.XmlaConstants.prototype.schemaCubesStringConstant = "MDSCHEMA_CUBES";
$.ig.XmlaConstants.prototype.restrictionListStringConstant = "RestrictionList";
$.ig.XmlaConstants.prototype.propertyListStringConstant = "PropertyList";
$.ig.XmlaConstants.prototype.dataSourceInfoStringConstant = "DataSourceInfo";
$.ig.XmlaConstants.prototype.catalogPropertyNameStringConstant = "Catalog";
$.ig.XmlaConstants.prototype.schemaNameStringConstant = "SCHEMA_NAME";
$.ig.XmlaConstants.prototype.rowStringConstant = "row";
$.ig.XmlaConstants.prototype.descriptionStringConstant = "DESCRIPTION";
$.ig.XmlaConstants.prototype.cubeStringConstant = "CUBE";
$.ig.XmlaConstants.prototype.dimensionStringConstant = "DIMENSION";
$.ig.XmlaConstants.prototype.schemaDimensionsStringConstant = "MDSCHEMA_DIMENSIONS";
$.ig.XmlaConstants.prototype.schemaMeasureGroupsStringConstant = "MDSCHEMA_MEASUREGROUPS";
$.ig.XmlaConstants.prototype.measureGroupNameStringConstant = "MEASUREGROUP_NAME";
$.ig.XmlaConstants.prototype.measureGroupCaptionStringConstant = "MEASUREGROUP_CAPTION";
$.ig.XmlaConstants.prototype.schemaMeasureGroupDimensionsStringConstant = "MDSCHEMA_MEASUREGROUP_DIMENSIONS";
$.ig.XmlaConstants.prototype.dimensionNameStringConstant = "DIMENSION_NAME";
$.ig.XmlaConstants.prototype.dimensionUniqueNameStringConstant = "DIMENSION_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.dimensionCaptionStringConstant = "DIMENSION_CAPTION";
$.ig.XmlaConstants.prototype.schemaMeasuresStringConstant = "MDSCHEMA_MEASURES";
$.ig.XmlaConstants.prototype.measureNameStringConstant = "MEASURE_NAME";
$.ig.XmlaConstants.prototype.measureUniqueNameStringConstant = "MEASURE_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.measureCaptionStringConstant = "MEASURE_CAPTION";
$.ig.XmlaConstants.prototype.measureAggregatorStringConstant = "MEASURE_AGGREGATOR";
$.ig.XmlaConstants.prototype.dimensionTypeStringConstant = "DIMENSION_TYPE";
$.ig.XmlaConstants.prototype.schemaHierarchiesStringConstant = "MDSCHEMA_HIERARCHIES";
$.ig.XmlaConstants.prototype.hierarchyNameStringConstant = "HIERARCHY_NAME";
$.ig.XmlaConstants.prototype.hierarchyUniqueNameStringConstant = "HIERARCHY_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.hierarchyCaptionStringConstant = "HIERARCHY_CAPTION";
$.ig.XmlaConstants.prototype.hierarchyOriginStringConstant = "HIERARCHY_ORIGIN";
$.ig.XmlaConstants.prototype.defaultMemberStringConstant = "DEFAULT_MEMBER";
$.ig.XmlaConstants.prototype.allMemberStringConstant = "ALL_MEMBER";
$.ig.XmlaConstants.prototype.levelNameStringConstant = "LEVEL_NAME";
$.ig.XmlaConstants.prototype.levelCaptionStringConstant = "LEVEL_CAPTION";
$.ig.XmlaConstants.prototype.levelUniqueNameStringConstant = "LEVEL_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.levelNumberStringConstant = "LEVEL_NUMBER";
$.ig.XmlaConstants.prototype.levelCardinalityStringConstant = "LEVEL_CARDINALITY";
$.ig.XmlaConstants.prototype.schemaLevelsStringConstant = "MDSCHEMA_LEVELS";
$.ig.XmlaConstants.prototype.schemaMembersStringConstant = "MDSCHEMA_MEMBERS";
$.ig.XmlaConstants.prototype.treeOpStringConstant = "TREE_OP";
$.ig.XmlaConstants.prototype.memberNameStringConstant = "MEMBER_NAME";
$.ig.XmlaConstants.prototype.memberUniqueNameStringConstant = "MEMBER_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.memberCaptionStringConstant = "MEMBER_CAPTION";
$.ig.XmlaConstants.prototype.parentUniqueNameStringConstant = "PARENT_UNIQUE_NAME";
$.ig.XmlaConstants.prototype.childrenCardinalityStringConstant = "CHILDREN_CARDINALITY";
$.ig.XmlaConstants.prototype.defaultFormatStringConstant = "DEFAULT_FORMAT_STRING";
$.ig.XmlaConstants.prototype.hierarchyDisplayFolderStringConstant = "HIERARCHY_DISPLAY_FOLDER";
$.ig.XmlaConstants.prototype.scopeStringConstant = "SCOPE";

$.ig.XmlaSoapMethodResult.prototype._returnPropertyName = "return";

$.ig.XmlaNamespace.prototype.sql = "urn:schemas-microsoft-com:xml-sql";
$.ig.XmlaNamespace.prototype.analysis = "urn:schemas-microsoft-com:xml-analysis";
$.ig.XmlaNamespace.prototype.mdDataset = "urn:schemas-microsoft-com:xml-analysis:mddataset";
$.ig.XmlaNamespace.prototype.rowset = "urn:schemas-microsoft-com:xml-analysis:rowset";
$.ig.XmlaNamespace.prototype.empty = "urn:schemas-microsoft-com:xml-analysis:empty";
$.ig.XmlaNamespace.prototype.exception = "urn:schemas-microsoft-com:xml-analysis:exception";

$.ig.XmlaSoapMessageBuilder.prototype._envelopeConstant = "Envelope";
$.ig.XmlaSoapMessageBuilder.prototype._headerConstant = "Header";
$.ig.XmlaSoapMessageBuilder.prototype._bodyConstant = "Body";
$.ig.XmlaSoapMessageBuilder.prototype._soapPrefixConstant = "soap";
$.ig.XmlaSoapMessageBuilder.prototype._xmlnsEnvelope = "http://schemas.xmlsoap.org/soap/envelope/";

$.ig.XmlaSoapMethod.prototype.propertiesConstant = "Properties";
$.ig.XmlaSoapMethod.prototype.propertyListConstant = "PropertyList";

$.ig.XmlaSoapMethodDiscover.prototype._methodName = "Discover";
$.ig.XmlaSoapMethodDiscover.prototype._requestTypeConstant = "RequestType";
$.ig.XmlaSoapMethodDiscover.prototype._restrictionsConstant = "Restrictions";
$.ig.XmlaSoapMethodDiscover.prototype._restrictionListConstant = "RestrictionList";
$.ig.XmlaSoapMethodDiscover.prototype._sessionIdConstant = "SessionId";

$.ig.XmlaSoapMethodExecute.prototype._methodName = "Execute";
$.ig.XmlaSoapMethodExecute.prototype._commandConstant = "Command";
$.ig.XmlaSoapMethodExecute.prototype._statementConstant = "Statement";

$.ig.XmlaSoapWebClient.prototype.__registeredPrefixes = null;
if ($.ig.XmlaSoapWebClient.prototype.staticInit && !$.ig.XmlaSoapWebClient.prototype.xmlaSoapWebClientStaticInitCalled) { $.ig.XmlaSoapWebClient.prototype.staticInit(); $.ig.XmlaSoapWebClient.prototype.xmlaSoapWebClientStaticInitCalled = true; }

$.ig.ArrayJsonSerializer.prototype.__instance = new $.ig.ArrayJsonSerializer();

$.ig.AxisJsonTypeSeriazlier.prototype.__instance = new $.ig.AxisJsonTypeSeriazlier();

$.ig.AxisMemberJsonTypeSeriazlier.prototype.__instance = new $.ig.AxisMemberJsonTypeSeriazlier();

$.ig.CatalogJsonTypeSeriazlier.prototype.__instance = new $.ig.CatalogJsonTypeSeriazlier();

$.ig.CellJsonTypeSeriazlier.prototype.__instance = new $.ig.CellJsonTypeSeriazlier();

$.ig.CubeJsonTypeSeriazlier.prototype.__instance = new $.ig.CubeJsonTypeSeriazlier();

$.ig.DimensionJsonTypeSeriazlier.prototype.__instance = new $.ig.DimensionJsonTypeSeriazlier();

$.ig.HierarchyJsonTypeSeriazlier.prototype.__instance = new $.ig.HierarchyJsonTypeSeriazlier();

$.ig.KpiJsonTypeSeriazlier.prototype.__instance = new $.ig.KpiJsonTypeSeriazlier();

$.ig.LevelJsonTypeSeriazlier.prototype.__instance = new $.ig.LevelJsonTypeSeriazlier();

$.ig.MeasureGroupDimensionJsonTypeSeriazlier.prototype.__instance = new $.ig.MeasureGroupDimensionJsonTypeSeriazlier();

$.ig.MeasureGroupJsonTypeSeriazlier.prototype.__instance = new $.ig.MeasureGroupJsonTypeSeriazlier();

$.ig.MeasureJsonTypeSeriazlier.prototype.__instance = new $.ig.MeasureJsonTypeSeriazlier();

$.ig.MemberJsonTypeSeriazlier.prototype.__instance = new $.ig.MemberJsonTypeSeriazlier();

$.ig.RemoteRequestJsonTypeSeriazlier.prototype.__instance = new $.ig.RemoteRequestJsonTypeSeriazlier();

$.ig.ResultJsonTypeSeriazlier.prototype.__instance = new $.ig.ResultJsonTypeSeriazlier();

$.ig.TupleJsonTypeSeriazlier.prototype.__instance = new $.ig.TupleJsonTypeSeriazlier();

$.ig.XmlaDiscoverProperties.prototype.catalog = "Catalog";

if ($.ig.OlapXmlaDataSource.prototype.staticInit && !$.ig.OlapXmlaDataSource.prototype.olapXmlaDataSourceStaticInitCalled) { $.ig.OlapXmlaDataSource.prototype.staticInit(); $.ig.OlapXmlaDataSource.prototype.olapXmlaDataSourceStaticInitCalled = true; }

} (jQuery));


