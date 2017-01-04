/*!@license
* Infragistics.Web.ClientUI infragistics.encoding.core.js 16.1.20161.2145
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
"APIFactory:ds", 
"Point:dt", 
"Rect:du", 
"Size:dv", 
"Color:dw", 
"Environment:d5", 
"UTF8Encoding:eh", 
"Encoding:ei", 
"UnicodeEncoding:ej", 
"AsciiEncoding:ek", 
"Decoder:el", 
"DefaultDecoder:em", 
"UTF8Encoding_UTF8Decoder:en", 
"StringBuilder:eu"]);


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

$.ig.Encoding.prototype.__utfEncoding = null;
$.ig.Encoding.prototype.__utf8Unmarked = null;
$.ig.Encoding.prototype.__unicodeEncoding = null;
$.ig.Encoding.prototype.__asciiEncoding = null;
$.ig.Encoding.prototype.__bigEndianUnicodeEncoding = null;

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["IEncoding:a", 
"String:b", 
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
"Enum:s", 
"Array:t", 
"IList:u", 
"ICollection:v", 
"IEnumerable:w", 
"IEnumerator:x", 
"Void:y", 
"NotSupportedException:z", 
"Error:aa", 
"Number:ab", 
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
"Encoding:bb", 
"UTF8Encoding:bc", 
"InvalidOperationException:bd", 
"NotImplementedException:be", 
"Script:bf", 
"Decoder:bg", 
"UnicodeEncoding:bh", 
"Math:bi", 
"AsciiEncoding:bj", 
"ArgumentNullException:bk", 
"DefaultDecoder:bl", 
"ArgumentException:bm", 
"Dictionary$2:bn", 
"IDictionary$2:bo", 
"ICollection$1:bp", 
"IDictionary:bq", 
"Func$2:br", 
"MulticastDelegate:bs", 
"IntPtr:bt", 
"KeyValuePair$2:bu", 
"Enumerable:bv", 
"Thread:bw", 
"ThreadStart:bx", 
"Func$3:by", 
"IList$1:bz", 
"IOrderedEnumerable$1:b0", 
"SortedList$1:b1", 
"List$1:b2", 
"IArray:b3", 
"IArrayList:b4", 
"Array:b5", 
"CompareCallback:b6", 
"Action$1:b7", 
"Comparer$1:b8", 
"IComparer:b9", 
"IComparer$1:ca", 
"DefaultComparer$1:cb", 
"Comparison$1:cc", 
"ReadOnlyCollection$1:cd", 
"Predicate$1:ce", 
"IEqualityComparer$1:cf", 
"EqualityComparer$1:cg", 
"IEqualityComparer:ch", 
"DefaultEqualityComparer$1:ci", 
"StringBuilder:cj", 
"Environment:ck", 
"RuntimeHelpers:co", 
"RuntimeFieldHandle:cp", 
"AbstractEnumerable:dj", 
"Func$1:dk", 
"AbstractEnumerator:dl", 
"GenericEnumerable$1:dm", 
"GenericEnumerator$1:dn"]);


$.ig.util.defType('IEncoding', 'Object', {
	$type: new $.ig.Type('IEncoding', null)
}, true);

} (jQuery));


