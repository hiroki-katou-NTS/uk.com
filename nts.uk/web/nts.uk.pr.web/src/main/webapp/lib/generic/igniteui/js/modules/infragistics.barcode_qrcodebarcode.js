/*!@license
* Infragistics.Web.ClientUI infragistics.barcode_qrcodebarcode.js 16.1.20161.2145
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
"Collection$1:b0", 
"Math:cb", 
"ArgumentNullException:cc", 
"Dictionary_EnumerableCollection$3:ch", 
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
"Environment:d5", 
"UTF8Encoding:eh", 
"Encoding:ei", 
"UnicodeEncoding:ej", 
"AsciiEncoding:ek", 
"Decoder:el", 
"DefaultDecoder:em", 
"UTF8Encoding_UTF8Decoder:en", 
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
"Canvas:gw", 
"Stretch:hu"]);


$.ig.util.defType('Stretch', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Fill";
			case 2: return "Uniform";
			case 3: return "UniformToFill";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('Stretch', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('Collection$1', 'List$1', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.List$1.prototype.init.call(this, this.$t, 0);
	},
	items: function () {
		return this;
	}
	,
	$type: new $.ig.Type('Collection$1', $.ig.List$1.prototype.$type.specialize(0))
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

$.ig.util.defType('Canvas', 'Panel', {
	init: function () {
		$.ig.Panel.prototype.init.call(this);
	},
	$type: new $.ig.Type('Canvas', $.ig.Panel.prototype.$type)
}, true);

$.ig.Stretch.prototype.none = 0;
$.ig.Stretch.prototype.fill = 1;
$.ig.Stretch.prototype.uniform = 2;
$.ig.Stretch.prototype.uniformToFill = 3;

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
"ErrorMessageDisplayingEventArgs:ds", 
"EventHandler$1:dy", 
"NotifyCollectionChangedEventArgs:d1", 
"NotifyCollectionChangedAction:d2", 
"ArgumentNullException:eh", 
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
"Func$1:ib", 
"StringBuilder:ih", 
"Environment:ii", 
"Panel:jg", 
"UIElementCollection:jh", 
"IVisualData:jt", 
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

$.ig.util.defType('ErrorMessageDisplayingEventArgs', 'EventArgs', {
	init: function (errorMessage) {
		$.ig.EventArgs.prototype.init.call(this);
		this.errorMessage(errorMessage);
	},
	__errorMessage: null,
	errorMessage: function (value) {
		if (arguments.length === 1) {
			this.__errorMessage = value;
			return value;
		} else {
			return this.__errorMessage;
		}
	}
	,
	$type: new $.ig.Type('ErrorMessageDisplayingEventArgs', $.ig.EventArgs.prototype.$type)
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
$.ig.util.bulkDefine(["EncodingService:a", 
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
"Windows1250Encoding:ba", 
"SingleByteEncoding:bb", 
"Encoding:bc", 
"UTF8Encoding:bd", 
"InvalidOperationException:be", 
"NotImplementedException:bf", 
"Script:bg", 
"Decoder:bh", 
"UnicodeEncoding:bi", 
"Math:bj", 
"AsciiEncoding:bk", 
"ArgumentNullException:bl", 
"DefaultDecoder:bm", 
"ArgumentException:bn", 
"IEncoding:bo", 
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
"StringBuilder:cl", 
"Environment:cm", 
"RuntimeHelpers:cn", 
"RuntimeFieldHandle:co", 
"Windows1251Encoding:cp", 
"Windows1252Encoding:cq", 
"Windows1256Encoding:cr", 
"UsAsciiEncoding:cs", 
"Big5Encoding:ct", 
"DoubleByteEncoding:cu", 
"Big5EncodingExtended:cv", 
"Big5EncodingExtended2:cw", 
"Windows936Encoding:cx", 
"Windows936EncodingExtended:cy", 
"Windows936EncodingExtended2:cz", 
"Windows936EncodingExtended3:c0", 
"Ksc5601Encoding:c1", 
"Ksc5601EncodingExtended:c2", 
"Ksc5601EncodingExtended2:c3", 
"Ksc5601EncodingExtended3:c4", 
"Iso8859Dash1:c5", 
"Iso8859Dash2:c6", 
"Iso8859Dash3:c7", 
"Iso8859Dash4:c8", 
"Iso8859Dash5:c9", 
"Iso8859Dash6:da", 
"Iso8859Dash7:db", 
"Iso8859Dash8:dc", 
"Iso8859Dash9:dd", 
"Iso8859Dash11:de", 
"Iso8859Dash13:df", 
"Iso8859Dash15:dg", 
"Windows932Encoding:dh", 
"Windows932EncodingExtended:di", 
"CodePage437Encoding:dj", 
"Ecc10H:dk", 
"Ecc10L:dl", 
"Ecc10M:dm", 
"Ecc10Q:dn", 
"Ecc11H:dp", 
"Ecc11L:dq", 
"Ecc11M:dr", 
"Ecc11Q:ds", 
"Ecc12H:dt", 
"Ecc12L:du", 
"Ecc12M:dv", 
"Ecc12Q:dw", 
"Ecc13H:dx", 
"Ecc13L:dy", 
"Ecc13M:dz", 
"Ecc13Q:d0", 
"Ecc14H:d1", 
"Ecc14L:d2", 
"Ecc14M:d3", 
"Ecc14Q:d4", 
"Ecc15H:d5", 
"Ecc15L:d6", 
"Ecc15M:d7", 
"Ecc15Q:d8", 
"Ecc16H:d9", 
"Ecc16L:ea", 
"Ecc16M:eb", 
"Ecc16Q:ec", 
"Ecc17H:ed", 
"Ecc17L:ee", 
"Ecc17M:ef", 
"Ecc17Q:eg", 
"Ecc18H:eh", 
"Ecc18L:ei", 
"Ecc18M:ej", 
"Ecc18Q:ek", 
"Ecc19H:el", 
"Ecc19L:em", 
"Ecc19M:en", 
"Ecc19Q:eo", 
"Ecc1H:ep", 
"Ecc1L:eq", 
"Ecc1M:er", 
"Ecc1Q:es", 
"Ecc20H:et", 
"Ecc20L:eu", 
"Ecc20M:ev", 
"Ecc20Q:ew", 
"Ecc21H:ex", 
"Ecc21L:ey", 
"Ecc21M:ez", 
"Ecc21Q:e0", 
"Ecc22H:e1", 
"Ecc22L:e2", 
"Ecc22M:e3", 
"Ecc22Q:e4", 
"Ecc23H:e5", 
"Ecc23L:e6", 
"Ecc23M:e7", 
"Ecc23Q:e8", 
"Ecc24H:e9", 
"Ecc24L:fa", 
"Ecc24M:fb", 
"Ecc24Q:fc", 
"Ecc25H:fd", 
"Ecc25L:fe", 
"Ecc25M:ff", 
"Ecc25Q:fg", 
"Ecc26H:fh", 
"Ecc26L:fi", 
"Ecc26M:fj", 
"Ecc26Q:fk", 
"Ecc27H:fl", 
"Ecc27L:fm", 
"Ecc27M:fn", 
"Ecc27Q:fo", 
"Ecc28H:fp", 
"Ecc28L:fq", 
"Ecc28M:fr", 
"Ecc28Q:fs", 
"Ecc29H:ft", 
"Ecc29L:fu", 
"Ecc29M:fv", 
"Ecc29Q:fw", 
"Ecc2H:fx", 
"Ecc2L:fy", 
"Ecc2M:fz", 
"Ecc2Q:f0", 
"Ecc30H:f1", 
"Ecc30L:f2", 
"Ecc30M:f3", 
"Ecc30Q:f4", 
"Ecc31H:f5", 
"Ecc31L:f6", 
"Ecc31M:f7", 
"Ecc31Q:f8", 
"Ecc32H:f9", 
"Ecc32L:ga", 
"Ecc32M:gb", 
"Ecc32Q:gc", 
"Ecc33H:gd", 
"Ecc33L:ge", 
"Ecc33M:gf", 
"Ecc33Q:gg", 
"Ecc34H:gh", 
"Ecc34L:gi", 
"Ecc34M:gj", 
"Ecc34Q:gk", 
"Ecc35H:gl", 
"Ecc35L:gm", 
"Ecc35M:gn", 
"Ecc35Q:go", 
"Ecc36H:gp", 
"Ecc36L:gq", 
"Ecc36M:gr", 
"Ecc36Q:gs", 
"Ecc37H:gt", 
"Ecc37L:gu", 
"Ecc37M:gv", 
"Ecc37Q:gw", 
"Ecc38H:gx", 
"Ecc38L:gy", 
"Ecc38M:gz", 
"Ecc38Q:g0", 
"Ecc39H:g1", 
"Ecc39L:g2", 
"Ecc39M:g3", 
"Ecc39Q:g4", 
"Ecc3H:g5", 
"Ecc3L:g6", 
"Ecc3M:g7", 
"Ecc3Q:g8", 
"Ecc40H:g9", 
"Ecc40L:ha", 
"Ecc40M:hb", 
"Ecc40Q:hc", 
"Ecc4H:hd", 
"Ecc4L:he", 
"Ecc4M:hf", 
"Ecc4Q:hg", 
"Ecc5H:hh", 
"Ecc5L:hi", 
"Ecc5M:hj", 
"Ecc5Q:hk", 
"Ecc6H:hl", 
"Ecc6L:hm", 
"Ecc6M:hn", 
"Ecc6Q:ho", 
"Ecc7H:hp", 
"Ecc7L:hq", 
"Ecc7M:hr", 
"Ecc7Q:hs", 
"Ecc8H:ht", 
"Ecc8L:hu", 
"Ecc8M:hv", 
"Ecc8Q:hw", 
"Ecc9H:hx", 
"Ecc9L:hy", 
"Ecc9M:hz", 
"Ecc9Q:h0", 
"RSP10:h1", 
"RSP13:h2", 
"RSP15:h3", 
"RSP16:h4", 
"RSP17:h5", 
"RSP18:h6", 
"RSP20:h7", 
"RSP22:h8", 
"RSP24:h9", 
"RSP26:ia", 
"RSP28:ib", 
"RSP30:ic", 
"RSP7:id", 
"QRCodeBarcodeResources:ie", 
"XamBarcodeView:ig", 
"XamBarcode:ih", 
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
"Stretch:i7", 
"SR:i8", 
"EventHandler$1:i9", 
"DataChangedEventArgs:ja", 
"EventArgs:jb", 
"TextBlock:jc", 
"ErrorMessageDisplayingEventArgs:jd", 
"BarcodeVisualData:je", 
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
"BrushUtil:km", 
"LinearGradientBrush:kn", 
"GradientStop:ko", 
"ColorUtil:kp", 
"Random:kq", 
"InterpolationMode:kr", 
"MathUtil:ks", 
"BrushCollection:kt", 
"ObservableCollection$1:ku", 
"INotifyCollectionChanged:kv", 
"NotifyCollectionChangedEventHandler:kw", 
"NotifyCollectionChangedEventArgs:kx", 
"NotifyCollectionChangedAction:ky", 
"INotifyPropertyChanged:kz", 
"PropertyChangedEventHandler:k0", 
"PropertyChangedEventArgs:k1", 
"CssHelper:k2", 
"CssGradientUtil:k3", 
"GeometryUtil:k4", 
"Tuple$2:k5", 
"Callback:k6", 
"window:k7", 
"DivElement:k8", 
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
"BarcodeAlgorithm:l3", 
"BarcodeEncodedAlgorithm:l4", 
"GridBarcodeVisualData:l5", 
"PrimitiveVisualData:l6", 
"IVisualData:l7", 
"PrimitiveAppearanceData:l8", 
"BrushAppearanceData:l9", 
"AppearanceHelper:ma", 
"LinearGradientBrushAppearanceData:mb", 
"GradientStopAppearanceData:mc", 
"SolidBrushAppearanceData:md", 
"GeometryData:me", 
"GetPointsSettings:mf", 
"EllipseGeometryData:mg", 
"RectangleGeometryData:mh", 
"LineGeometryData:mi", 
"PathGeometryData:mj", 
"PathFigureData:mk", 
"SegmentData:ml", 
"LineSegmentData:mm", 
"PolylineSegmentData:mn", 
"ArcSegmentData:mo", 
"PolyBezierSegmentData:mp", 
"BezierSegmentData:mq", 
"LabelAppearanceData:mr", 
"ShapeTags:ms", 
"Bch:mt", 
"GfArithmetics:mu", 
"Gf256:mv", 
"ReedSolomon:mw", 
"QRReedSolomon:mx", 
"Gs1Helper:my", 
"MeasureHelper:mz", 
"QRCodeAlgorithm:m0", 
"QRCodeEncoder:m1", 
"XamQRCodeBarcode:m2", 
"XamGridBarcode:m3", 
"BarcodeGrid:m4", 
"Panel:m5", 
"UIElementCollection:m6", 
"Module:m7", 
"Collection$1:m8", 
"ColumnDefinition:m9", 
"GridLength:na", 
"GridUnitType:nb", 
"RowDefinition:nc", 
"Canvas:nd", 
"BarsFillMode:ne", 
"XamGridBarcodeView:nf", 
"PathVisualData:ng", 
"QRCodeErrorCorrectionLevel:nh", 
"SizeVersion:ni", 
"EncodingMode:nj", 
"HeaderDisplayMode:nk", 
"Fnc1Mode:nl", 
"QRMask:nm", 
"EncodingSequence:nn", 
"QRModeIndicator:no", 
"AbstractEnumerable:np", 
"Func$1:nq", 
"AbstractEnumerator:nr", 
"GenericEnumerable$1:ns", 
"GenericEnumerator$1:nt"]);


$.ig.util.defType('GridUnitType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Auto";
			case 1: return "Pixel";
			case 2: return "Star";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('GridUnitType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('QRModeIndicator', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 111: return "Eci";
			case 1: return "Numeric";
			case 10: return "Alphanumeric";
			case 100: return "Byte";
			case 1000: return "Kanji";
			case 11: return "StructuredAppend";
			case 101: return "Fnc1First";
			case 1001: return "Fnc1Second";
			case 0: return "Terminator";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('QRModeIndicator', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('Fnc1Mode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Gs1";
			case 2: return "Industry";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('Fnc1Mode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('EncodingMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case -1: return "Undefined";
			case 0: return "Numeric";
			case 1: return "Alphanumeric";
			case 2: return "Byte";
			case 3: return "Kanji";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('EncodingMode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('QRCodeErrorCorrectionLevel', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 1: return "Low";
			case 0: return "Medium";
			case 3: return "Quartil";
			case 2: return "High";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('QRCodeErrorCorrectionLevel', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('SizeVersion', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Undefined";
			case 1: return "Version1";
			case 2: return "Version2";
			case 3: return "Version3";
			case 4: return "Version4";
			case 5: return "Version5";
			case 6: return "Version6";
			case 7: return "Version7";
			case 8: return "Version8";
			case 9: return "Version9";
			case 10: return "Version10";
			case 11: return "Version11";
			case 12: return "Version12";
			case 13: return "Version13";
			case 14: return "Version14";
			case 15: return "Version15";
			case 16: return "Version16";
			case 17: return "Version17";
			case 18: return "Version18";
			case 19: return "Version19";
			case 20: return "Version20";
			case 21: return "Version21";
			case 22: return "Version22";
			case 23: return "Version23";
			case 24: return "Version24";
			case 25: return "Version25";
			case 26: return "Version26";
			case 27: return "Version27";
			case 28: return "Version28";
			case 29: return "Version29";
			case 30: return "Version30";
			case 31: return "Version31";
			case 32: return "Version32";
			case 33: return "Version33";
			case 34: return "Version34";
			case 35: return "Version35";
			case 36: return "Version36";
			case 37: return "Version37";
			case 38: return "Version38";
			case 39: return "Version39";
			case 40: return "Version40";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('SizeVersion', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HeaderDisplayMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Hide";
			case 1: return "Show";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('HeaderDisplayMode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('BarsFillMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "FillSpace";
			case 1: return "EnsureEqualSize";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('BarsFillMode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('EncodingService', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		$.ig.EncodingService.prototype.encodingFriendlyNames = new $.ig.Dictionary$2(String, String, 0);
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("Cp437", "CP437");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-1", "ISO-8859-1");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-2", "ISO-8859-2");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-3", "ISO-8859-3");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-4", "ISO-8859-4");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-5", "ISO-8859-5");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-6", "ISO-8859-6");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-7", "ISO-8859-7");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-8", "ISO-8859-8");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-9", "ISO-8859-9");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-11", "ISO-8859-11");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-13", "ISO-8859-13");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("iso-8859-15", "ISO-8859-15");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("shift_jis", "Shift_JIS");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("windows-1250", "Windows-1250");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("windows-1251", "Windows-1251");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("windows-1252", "Windows-1252");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("windows-1256", "Windows-1256");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("ISO-10646-UCS-2", "ISO-10646-UCS-2");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("UTF-8", "UTF-8");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("ISO646-US", "ISO646-US");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("Big5", "Big5");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("gb2312", "GB2312");
		$.ig.EncodingService.prototype.encodingFriendlyNames.item("KSC5601", "KSC5601");
	},
	getEncoding: function (name) {
		if (name == "UTF-8") {
			return $.ig.Encoding.prototype.uTF8();
		}
		if (name == "ISO-10646-UCS-2") {
			return $.ig.Encoding.prototype.unicode();
		}
		var encoding_ = null;
		switch (name) {
			case "windows-1250":
				if ($.ig.EncodingService.prototype.__windows1250Encoding == null) {
					$.ig.EncodingService.prototype.__windows1250Encoding = new $.ig.Windows1250Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows1250Encoding;
				break;
			case "windows-1251":
				if ($.ig.EncodingService.prototype.__windows1251Encoding == null) {
					$.ig.EncodingService.prototype.__windows1251Encoding = new $.ig.Windows1251Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows1251Encoding;
				break;
			case "windows-1252":
				if ($.ig.EncodingService.prototype.__windows1252Encoding == null) {
					$.ig.EncodingService.prototype.__windows1252Encoding = new $.ig.Windows1252Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows1252Encoding;
				break;
			case "windows-1256":
				if ($.ig.EncodingService.prototype.__windows1256Encoding == null) {
					$.ig.EncodingService.prototype.__windows1256Encoding = new $.ig.Windows1256Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows1256Encoding;
				break;
			case "ISO646-US":
				if ($.ig.EncodingService.prototype.__usAsciiEncoding == null) {
					$.ig.EncodingService.prototype.__usAsciiEncoding = new $.ig.UsAsciiEncoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__usAsciiEncoding;
				break;
			case "Big5":
				if ($.ig.EncodingService.prototype.__big5Encoding == null) {
					$.ig.EncodingService.prototype.__big5Encoding = new $.ig.Big5Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__big5Encoding;
				break;
			case "gb2312":
				if ($.ig.EncodingService.prototype.__windows936Encoding == null) {
					$.ig.EncodingService.prototype.__windows936Encoding = new $.ig.Windows936Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows936Encoding;
				break;
			case "KSC5601":
				if ($.ig.EncodingService.prototype.__ksc5601Encoding == null) {
					$.ig.EncodingService.prototype.__ksc5601Encoding = new $.ig.Ksc5601Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__ksc5601Encoding;
				break;
			case "iso-8859-1":
				if ($.ig.EncodingService.prototype.__iso8859Dash1 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash1 = new $.ig.Iso8859Dash1();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash1;
				break;
			case "iso-8859-2":
				if ($.ig.EncodingService.prototype.__iso8859Dash2 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash2 = new $.ig.Iso8859Dash2();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash2;
				break;
			case "iso-8859-3":
				if ($.ig.EncodingService.prototype.__iso8859Dash3 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash3 = new $.ig.Iso8859Dash3();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash3;
				break;
			case "iso-8859-4":
				if ($.ig.EncodingService.prototype.__iso8859Dash4 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash4 = new $.ig.Iso8859Dash4();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash4;
				break;
			case "iso-8859-5":
				if ($.ig.EncodingService.prototype.__iso8859Dash5 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash5 = new $.ig.Iso8859Dash5();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash5;
				break;
			case "iso-8859-6":
				if ($.ig.EncodingService.prototype.__iso8859Dash6 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash6 = new $.ig.Iso8859Dash6();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash6;
				break;
			case "iso-8859-7":
				if ($.ig.EncodingService.prototype.__iso8859Dash7 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash7 = new $.ig.Iso8859Dash7();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash7;
				break;
			case "iso-8859-8":
				if ($.ig.EncodingService.prototype.__iso8859Dash8 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash8 = new $.ig.Iso8859Dash8();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash8;
				break;
			case "iso-8859-9":
				if ($.ig.EncodingService.prototype.__iso8859Dash9 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash9 = new $.ig.Iso8859Dash9();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash9;
				break;
			case "iso-8859-11":
				if ($.ig.EncodingService.prototype.__iso8859Dash11 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash11 = new $.ig.Iso8859Dash11();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash11;
				break;
			case "iso-8859-13":
				if ($.ig.EncodingService.prototype.__iso8859Dash13 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash13 = new $.ig.Iso8859Dash13();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash13;
				break;
			case "iso-8859-15":
				if ($.ig.EncodingService.prototype.__iso8859Dash15 == null) {
					$.ig.EncodingService.prototype.__iso8859Dash15 = new $.ig.Iso8859Dash15();
				}
				encoding_ = $.ig.EncodingService.prototype.__iso8859Dash15;
				break;
			case "shift_jis":
				if ($.ig.EncodingService.prototype.__windows932Encoding == null) {
					$.ig.EncodingService.prototype.__windows932Encoding = new $.ig.Windows932Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__windows932Encoding;
				break;
			case "Cp437":
				if ($.ig.EncodingService.prototype.__codePage437Encoding == null) {
					$.ig.EncodingService.prototype.__codePage437Encoding = new $.ig.CodePage437Encoding();
				}
				encoding_ = $.ig.EncodingService.prototype.__codePage437Encoding;
				break;
		}
		;
		return encoding_;
	}
	,
	$type: new $.ig.Type('EncodingService', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc10H', 'Object', {
	init: function () {
		this._resource = [ "KysrKysrLCw=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc10H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc10L', 'Object', {
	init: function () {
		this._resource = [ "VlZXVw==", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc10L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc10M', 'Object', {
	init: function () {
		this._resource = [ "RUVFRUY=", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc10M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc10Q', 'Object', {
	init: function () {
		this._resource = [ "KysrKysrLCw=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc10Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc11H', 'Object', {
	init: function () {
		this._resource = [ "JCQkJSUlJSUlJSU=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc11H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc11L', 'Object', {
	init: function () {
		this._resource = [ "ZWVlZQ==", "FA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc11L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc11M', 'Object', {
	init: function () {
		this._resource = [ "UFFRUVE=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc11M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc11Q', 'Object', {
	init: function () {
		this._resource = [ "MjIyMjMzMzM=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc11Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc12H', 'Object', {
	init: function () {
		this._resource = [ "KioqKioqKisrKys=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc12H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc12L', 'Object', {
	init: function () {
		this._resource = [ "dHR1dQ==", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc12L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc12M', 'Object', {
	init: function () {
		this._resource = [ "Ojo6Ojo6Ozs=", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc12M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc12Q', 'Object', {
	init: function () {
		this._resource = [ "Li4uLi8vLy8vLw==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc12Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc13H', 'Object', {
	init: function () {
		this._resource = [ "ISEhISEhISEhISEhIiIiIg==", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc13H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc13L', 'Object', {
	init: function () {
		this._resource = [ "hYWFhQ==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc13L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc13M', 'Object', {
	init: function () {
		this._resource = [ "Ozs7Ozs7Ozs8", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc13M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc13Q', 'Object', {
	init: function () {
		this._resource = [ "LCwsLCwsLCwtLS0t", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc13Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc14H', 'Object', {
	init: function () {
		this._resource = [ "JCQkJCQkJCQkJCQlJSUlJQ==", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc14H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc14L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc14L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc14M', 'Object', {
	init: function () {
		this._resource = [ "QEBAQEFBQUFB", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc14M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc14Q', 'Object', {
	init: function () {
		this._resource = [ "JCQkJCQkJCQkJCQlJSUlJQ==", "FA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc14Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc15H', 'Object', {
	init: function () {
		this._resource = [ "JCQkJCQkJCQkJCQlJSUlJSUl", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc15H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc15L', 'Object', {
	init: function () {
		this._resource = [ "bW1tbW1u", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc15L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc15M', 'Object', {
	init: function () {
		this._resource = [ "QUFBQUFCQkJCQg==", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc15M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc15Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY3Nzc3Nzc3", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc15Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc16H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLi4uLi4uLi4uLi4uLg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc16H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc16L', 'Object', {
	init: function () {
		this._resource = [ "enp6enp7", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc16L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc16M', 'Object', {
	init: function () {
		this._resource = [ "SUlJSUlJSUpKSg==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc16M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc16Q', 'Object', {
	init: function () {
		this._resource = [ "KysrKysrKysrKysrKysrLCw=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc16Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc17H', 'Object', {
	init: function () {
		this._resource = [ "KiorKysrKysrKysrKysrKysrKw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc17H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc17L', 'Object', {
	init: function () {
		this._resource = [ "h4iIiIiI", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc17L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc17M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSks=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc17M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc17Q', 'Object', {
	init: function () {
		this._resource = [ "MjMzMzMzMzMzMzMzMzMzMw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc17Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc18H', 'Object', {
	init: function () {
		this._resource = [ "KiorKysrKysrKysrKysrKysrKysr", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc18H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc18L', 'Object', {
	init: function () {
		this._resource = [ "lpaWlpaX", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc18L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc18M', 'Object', {
	init: function () {
		this._resource = [ "RUVFRUVFRUVFRkZGRg==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc18M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc18Q', 'Object', {
	init: function () {
		this._resource = [ "MjIyMjIyMjIyMjIyMjIyMjIz", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc18Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc19H', 'Object', {
	init: function () {
		this._resource = [ "JycnJycnJycnKCgoKCgoKCgoKCgoKCgoKA==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc19H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc19L', 'Object', {
	init: function () {
		this._resource = [ "jY2Njo6Ojg==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc19L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc19M', 'Object', {
	init: function () {
		this._resource = [ "RkZGR0dHR0dHR0dHR0c=", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc19M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc19Q', 'Object', {
	init: function () {
		this._resource = [ "Ly8vLy8vLy8vLy8vLy8vLy8wMDAw", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc19Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc1H', 'Object', {
	init: function () {
		this._resource = [ "Gg==", "EQ==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc1H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc1L', 'Object', {
	init: function () {
		this._resource = [ "Gg==", "Bw==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc1L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc1M', 'Object', {
	init: function () {
		this._resource = [ "Gg==", "Cg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc1M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc1Q', 'Object', {
	init: function () {
		this._resource = [ "Gg==", "DQ==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc1Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc20H', 'Object', {
	init: function () {
		this._resource = [ "KysrKysrKysrKysrKysrLCwsLCwsLCwsLA==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc20H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc20L', 'Object', {
	init: function () {
		this._resource = [ "h4eHiIiIiIg=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc20L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc20M', 'Object', {
	init: function () {
		this._resource = [ "Q0NDRERERERERERERERERA==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc20M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc20Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc20Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc21H', 'Object', {
	init: function () {
		this._resource = [ "Li4uLi4uLi4uLi4uLi4uLi4uLi8vLy8vLw==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc21H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc21L', 'Object', {
	init: function () {
		this._resource = [ "kJCQkJGRkZE=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc21L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc21M', 'Object', {
	init: function () {
		this._resource = [ "REREREREREREREREREREREQ=", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc21M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc21Q', 'Object', {
	init: function () {
		this._resource = [ "MjIyMjIyMjIyMjIyMjIyMjIzMzMzMzM=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc21Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc22H', 'Object', {
	init: function () {
		this._resource = [ "JSUlJSUlJSUlJSUlJSUlJSUlJSUlJSUlJSUlJSUlJSUlJQ==", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc22H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc22L', 'Object', {
	init: function () {
		this._resource = [ "i4uMjIyMjIyM", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc22L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc22M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSkpKSko=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc22M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc22Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc22Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc23H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc23H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc23L', 'Object', {
	init: function () {
		this._resource = [ "l5eXl5iYmJiY", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc23L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc23M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0xMTExMTExMTExMTExM", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc23M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc23Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY3Nzc3Nzc3Nzc3Nzc3Nw==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc23Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc24H', 'Object', {
	init: function () {
		this._resource = [ "Li4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLy8=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc24H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc24L', 'Object', {
	init: function () {
		this._resource = [ "k5OTk5OTlJSUlA==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc24L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc24M', 'Object', {
	init: function () {
		this._resource = [ "SUlJSUlJSkpKSkpKSkpKSkpKSko=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc24M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc24Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY3Nzc3Nzc3Nzc3Nzc3Nzc3", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc24Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc25H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc25H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc25L', 'Object', {
	init: function () {
		this._resource = [ "hISEhISEhISFhYWF", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc25L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc25M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLS0tMTExMTExMTExMTExM", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc25M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc25Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc25Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc26H', 'Object', {
	init: function () {
		this._resource = [ "Li4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLy8vLw==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc26H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc26L', 'Object', {
	init: function () {
		this._resource = [ "jo6Ojo6Ojo6Ojo+P", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc26L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc26M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSkpKSkpKSktLS0s=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc26M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc26Q', 'Object', {
	init: function () {
		this._resource = [ "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjMzMzMzMw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc26Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc27H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc27H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc27L', 'Object', {
	init: function () {
		this._resource = [ "mJiYmJiYmJiZmZmZ", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc27L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc27M', 'Object', {
	init: function () {
		this._resource = [ "SUlJSUlJSUlJSUlJSUlJSUlJSUlJSUpKSg==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc27M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc27Q', 'Object', {
	init: function () {
		this._resource = [ "NTU1NTU1NTU2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Ng==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc27Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc28H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc28H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc28L', 'Object', {
	init: function () {
		this._resource = [ "k5OTlJSUlJSUlJSUlA==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc28L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc28M', 'Object', {
	init: function () {
		this._resource = [ "SUlJSkpKSkpKSkpKSkpKSkpKSkpKSkpKSko=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc28M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc28Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc28Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc29H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc29H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc29L', 'Object', {
	init: function () {
		this._resource = [ "kpKSkpKSkpOTk5OTk5M=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc29L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc29M', 'Object', {
	init: function () {
		this._resource = [ "SUlJSUlJSUlJSUlJSUlJSUlJSUlJSkpKSkpKSg==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc29M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc29Q', 'Object', {
	init: function () {
		this._resource = [ "NTY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc29Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc2H', 'Object', {
	init: function () {
		this._resource = [ "LA==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc2H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc2L', 'Object', {
	init: function () {
		this._resource = [ "LA==", "Cg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc2L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc2M', 'Object', {
	init: function () {
		this._resource = [ "LA==", "EA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc2M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc2Q', 'Object', {
	init: function () {
		this._resource = [ "LA==", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc2Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc30H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc30H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc30L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkZGSkpKSkpKSkpKS", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc30L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc30M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLS0tLS0tLS0tLS0tLS0xMTExMTExMTEw=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc30M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc30Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nw==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc30Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc31H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc31H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc31L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkZGRkZGRkZGRkZKSkg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc31L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc31M', 'Object', {
	init: function () {
		this._resource = [ "SkpLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLSw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc31M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc31Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Nw==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc31Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc32H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc32H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc32L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkZGRkZGRkZGRkZGRkZE=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc32L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc32M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSktLS0tLS0tLS0tLS0tLS0tLS0tLS0tL", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc32M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc32Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc32Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc33H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc33H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc33L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkZGRkZGRkZGRkZGRkZGS", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc33L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc33M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSkpLS0tLS0tLS0tLS0tLS0tLS0tLS0s=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc33M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc33Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc33Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc34H', 'Object', {
	init: function () {
		this._resource = [ "Li4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4v", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc34H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc34L', 'Object', {
	init: function () {
		this._resource = [ "kZGRkZGRkZGRkZGRkZKSkpKSkg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc34L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc34M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSkpLS0tLS0tLS0tLS0tLS0tLS0tLS0tLSw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc34M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc34Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY3Nzc3Nzc3", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc34Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc35H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc35H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc35L', 'Object', {
	init: function () {
		this._resource = [ "l5eXl5eXl5eXl5eXmJiYmJiYmA==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc35L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc35M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLS0tLS0tLTExMTExMTExMTExMTExMTExMTExMTExMTEw=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc35M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc35Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc35Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc36H', 'Object', {
	init: function () {
		this._resource = [ "LS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc36H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc36L', 'Object', {
	init: function () {
		this._resource = [ "l5eXl5eXmJiYmJiYmJiYmJiYmJg=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc36L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc36M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTA==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc36M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc36Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Njc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc36Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc37H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLg==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc37H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc37L', 'Object', {
	init: function () {
		this._resource = [ "mJiYmJiYmJiYmJiYmJiYmJiZmZmZ", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc37L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc37M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpKSkpLS0tLS0tLS0tLS0tLSw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc37M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc37Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Njc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc37Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc38H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc38H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc38L', 'Object', {
	init: function () {
		this._resource = [ "mJiYmJmZmZmZmZmZmZmZmZmZmZmZmQ==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc38L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc38M', 'Object', {
	init: function () {
		this._resource = [ "SkpKSkpKSkpKSkpKSktLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tL", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc38M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc38Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc38Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc39H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc39H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc39L', 'Object', {
	init: function () {
		this._resource = [ "k5OTk5OTk5OTk5OTk5OTk5OTk5OUlJSU", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc39L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc39M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0xMTExMTEw=", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc39M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc39Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc39Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc3H', 'Object', {
	init: function () {
		this._resource = [ "IyM=", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc3H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc3L', 'Object', {
	init: function () {
		this._resource = [ "Rg==", "Dw==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc3L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc3M', 'Object', {
	init: function () {
		this._resource = [ "Rg==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc3M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc3Q', 'Object', {
	init: function () {
		this._resource = [ "IyM=", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc3Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc40H', 'Object', {
	init: function () {
		this._resource = [ "LS0tLS0tLS0tLS0tLS0tLS0tLS0uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4uLi4u", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc40H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc40L', 'Object', {
	init: function () {
		this._resource = [ "lJSUlJSUlJSUlJSUlJSUlJSUlJWVlZWVlQ==", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc40L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc40M', 'Object', {
	init: function () {
		this._resource = [ "S0tLS0tLS0tLS0tLS0tLS0tLTExMTExMTExMTExMTExMTExMTExMTExMTExMTExMTA==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc40M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc40Q', 'Object', {
	init: function () {
		this._resource = [ "NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2Njc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc40Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc4H', 'Object', {
	init: function () {
		this._resource = [ "GRkZGQ==", "EA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc4H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc4L', 'Object', {
	init: function () {
		this._resource = [ "ZA==", "FA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc4L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc4M', 'Object', {
	init: function () {
		this._resource = [ "MjI=", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc4M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc4Q', 'Object', {
	init: function () {
		this._resource = [ "MjI=", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc4Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc5H', 'Object', {
	init: function () {
		this._resource = [ "ISEiIg==", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc5H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc5L', 'Object', {
	init: function () {
		this._resource = [ "hg==", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc5L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc5M', 'Object', {
	init: function () {
		this._resource = [ "Q0M=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc5M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc5Q', 'Object', {
	init: function () {
		this._resource = [ "ISEiIg==", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc5Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc6H', 'Object', {
	init: function () {
		this._resource = [ "KysrKw==", "HA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc6H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc6L', 'Object', {
	init: function () {
		this._resource = [ "VlY=", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc6L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc6M', 'Object', {
	init: function () {
		this._resource = [ "KysrKw==", "EA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc6M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc6Q', 'Object', {
	init: function () {
		this._resource = [ "KysrKw==", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc6Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc7H', 'Object', {
	init: function () {
		this._resource = [ "JycnJyg=", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc7H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc7L', 'Object', {
	init: function () {
		this._resource = [ "YmI=", "FA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc7L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc7M', 'Object', {
	init: function () {
		this._resource = [ "MTExMQ==", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc7M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc7Q', 'Object', {
	init: function () {
		this._resource = [ "ICAhISEh", "Eg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc7Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc8H', 'Object', {
	init: function () {
		this._resource = [ "KCgoKCkp", "Gg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc8H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc8L', 'Object', {
	init: function () {
		this._resource = [ "eXk=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc8L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc8M', 'Object', {
	init: function () {
		this._resource = [ "PDw9PQ==", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc8M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc8Q', 'Object', {
	init: function () {
		this._resource = [ "KCgoKCkp", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc8Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc9H', 'Object', {
	init: function () {
		this._resource = [ "JCQkJCUlJSU=", "GA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc9H', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc9L', 'Object', {
	init: function () {
		this._resource = [ "kpI=", "Hg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc9L', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc9M', 'Object', {
	init: function () {
		this._resource = [ "Ojo6Ozs=", "Fg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc9M', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Ecc9Q', 'Object', {
	init: function () {
		this._resource = [ "JCQkJCUlJSU=", "FA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('Ecc9Q', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP10', 'Object', {
	init: function () {
		this._resource = [ "2MKfb8deX3GdwQ==", "2MKfb8deX3GdwQ==", "rZkj3pO8vuInnw==", "dVu8sVTi4ZO6Xg==", "Ry9GoTtlYdlOIw==", "n+3Zzvw7PqjT4g==", "6rZlf6jZ3ztpvA==", "MnT6EG+HgEr0fQ==", "jl6MX3bKwq+cRg==", "VpwTMLGUnd4Bhw==", "I8evgeV2fE272Q==", "+wUw7iIoIzwmGA==", "yXHK/k2vo3bSZQ==", "EbNVkYrx/AdPpA==", "ZOjpIN4THZT1+g==", "vCp2TxlNQuVoOw==", "AbwFvuyJmUMljA==", "2X6a0SvXxjK4TQ==", "rCUmYH81J6ECEw==", "dOe5D7hreNCf0g==", "RpNDH9fs+Jprrw==", "nlHccBCyp+v2bg==", "6wpgwURQRnhMMA==", "M8j/roMOGQnR8Q==", "j+KJ4ZpDW+y5yg==", "VyAWjl0dBJ0kCw==", "InuqPwn/5Q6eVQ==", "+rk1UM6hun8DlA==", "yM3PQKEmOjX36Q==", "EA9QL2Z4ZURqKA==", "ZVTsnjKahNfQdg==", "vZZz8fXE26ZNtw==", "AmUKYcUPL4ZKBQ==", "2qeVDgJRcPfXxA==", "r/wpv1azkWRtmg==", "dz620JHtzhXwWw==", "RUpMwP5qTl8EJg==", "nYjTrzk0ES6Z5w==", "6NNvHm3W8L0juQ==", "MBHwcaqIr8y+eA==", "jDuGPrPF7SnWQw==", "VPkZUXSbslhLgg==", "IaKl4CB5U8vx3A==", "+WA6j+cnDLpsHQ==", "yxTAn4igjPCYYA==", "E9Zf8E/+04EFoQ==", "Zo3jQRscMhK//w==", "vk98LtxCbWMiPg==", "A9kP3ymGtsVviQ==", "2xuQsO7Y6bTySA==", "rkAsAbo6CCdIFg==", "doKzbn1kV1bV1w==", "RPZJfhLj1xwhqg==", "nDTWEdW9iG28aw==", "6W9qoIFfaf4GNQ==", "Ma31z0YBNo+b9A==", "jYeDgF9MdGrzzw==", "VUUc75gSKxtuDg==", "IB6gXszwyojUUA==", "+Nw/MQuulflJkQ==", "yqjFIWQpFbO97A==", "EmpaTqN3SsIgLQ==", "ZzHm//eVq1Gacw==", "v/N5kDDL9CAHsg==", "BMoUwpceXhGUCg==", "3AiLrVBAAWAJyw==", "qVM3HASi4POzlQ==", "cZGoc8P8v4IuVA==", "Q+VSY6x7P8jaKQ==", "myfNDGslYLlH6A==", "7nxxvT/HgSr9tg==", "Nr7u0viZ3ltgdw==", "ipSYneHUnL4ITA==", "UlYH8iaKw8+VjQ==", "Jw27Q3JoIlwv0w==", "/88kLLU2fS2yEg==", "zbvePNqx/WdGbw==", "FXlBUx3vohbbrg==", "YCL94kkNQ4Vh8A==", "uOBijY5THPT8MQ==", "BXYRfHuXx1Kxhg==", "3bSOE7zJmCMsRw==", "qO8yougrebCWGQ==", "cC2tzS91JsEL2A==", "QllX3UDypov/pQ==", "mpvIsoes+fpiZA==", "78B0A9NOGGnYOg==", "NwLrbBQQRxhF+w==", "iyidIw1dBf0twA==", "U+oCTMoDWoywAQ==", "JrG+/Z7hux8KXw==", "/nMhklm/5G6Xng==", "zAfbgjY4ZCRj4w==", "FMVE7fFmO1X+Ig==", "YZ74XKWE2sZEfA==", "uVxnM2LahbfZvQ==", "Bq8eo1IRcZfeDw==", "3m2BzJVPLuZDzg==", "qzY9fcGtz3X5kA==", "c/SiEgbzkARkUQ==", "QYBYAml0EE6QLA==", "mULHba4qTz8N7Q==", "7Bl73PrIrqy3sw==", "NNvksz2W8d0qcg==", "iPGS/CTbszhCSQ==", "UDMNk+OF7EnfiA==", "JWixIrdnDdpl1g==", "/aouTXA5Uqv4Fw==", "z97UXR++0uEMag==", "FxxLMtjgjZCRqw==", "Ykf3g4wCbAMr9Q==", "uoVo7EtcM3K2NA==", "BxMbHb6Y6NT7gw==", "39GEcnnGt6VmQg==", "qoo4wy0kVjbcHA==", "ckinrOp6CUdB3Q==", "QDxdvIX9iQ21oA==", "mP7C00Kj1nwoYQ==", "7aV+YhZBN++SPw==", "NWfhDdEfaJ4P/g==", "iU2XQshSKntnxQ==", "UY8ILQ8MdQr6BA==", "JNS0nFvulJlAWg==", "/BYr85ywy+jdmw==", "zmLR4/M3S6Ip5g==", "FqBOjDRpFNO0Jw==", "Y/vyPWCL9UAOeQ==", "uzltUqfVqjGTuA==", "CIkomTM8vCI1FA==", "0Eu39vRi41Oo1Q==", "pRALR6CAAsASiw==", "fdKUKGfeXbGPSg==", "T6ZuOAhZ3ft7Nw==", "l2TxV88Hgorm9g==", "4j9N5pvlYxlcqA==", "Ov3SiVy7PGjBaQ==", "htekxkX2fo2pUg==", "XhU7qYKoIfw0kw==", "K06HGNZKwG+OzQ==", "84wYdxEUnx4TDA==", "wfjiZ36TH1TncQ==", "GTp9CLnNQCV6sA==", "bGHBue0vobbA7g==", "tKNe1ipx/sddLw==", "CTUtJ9+1JWEQmA==", "0feySBjrehCNWQ==", "pKwO+UwJm4M3Bw==", "fG6RlotXxPKqxg==", "ThprhuTQRLheuw==", "ltj06SOOG8nDeg==", "44NIWHds+lp5JA==", "O0HXN7AypSvk5Q==", "h2uheKl/586M3g==", "X6k+F24huL8RHw==", "KvKCpjrDWSyrQQ==", "8jAdyf2dBl02gA==", "wETn2ZIahhfC/Q==", "GIZ4tlVE2WZfPA==", "bd3EBwGmOPXlYg==", "tR9baMb4Z4R4ow==", "Cuwi+PYzk6R/EQ==", "0i69lzFtzNXi0A==", "p3UBJmWPLUZYjg==", "f7eeSaLRcjfFTw==", "TcNkWc1W8n0xMg==", "lQH7NgoIrQys8w==", "4FpHh17qTJ8WrQ==", "OJjY6Jm0E+6LbA==", "hLKup4D5UQvjVw==", "XHAxyEenDnp+lg==", "KSuNeRNF7+nEyA==", "8ekSFtQbsJhZCQ==", "w53oBrucMNKtdA==", "G193aXzCb6MwtQ==", "bgTL2CggjjCK6w==", "tsZUt+9+0UEXKg==", "C1AnRhq6CudanQ==", "05K4Kd3kVZbHXA==", "pskEmIkGtAV9Ag==", "fgub905Y63Tgww==", "TH9h5yHfaz4Uvg==", "lL3+iOaBNE+Jfw==", "4eZCObJj1dwzIQ==", "OSTdVnU9iq2u4A==", "hQ6rGWxwyEjG2w==", "Xcw0dqsulzlbGg==", "KJeIx//MdqrhRA==", "8FUXqDiSKdt8hQ==", "wiHtuFcVqZGI+A==", "GuNy15BL9uAVOQ==", "b7jOZsSpF3OvZw==", "t3pRCQP3SAIypg==", "DEM8W6Qi4jOhHg==", "1IGjNGN8vUI83w==", "odofhTeeXNGGgQ==", "eRiA6vDAA6AbQA==", "S2x6+p9Hg+rvPQ==", "k67llVgZ3Jty/A==", "5vVZJAz7PQjIog==", "PjfGS8ulYnlVYw==", "gh2wBNLoIJw9WA==", "Wt8vaxW2f+2gmQ==", "L4ST2kFUnn4axw==", "90YMtYYKwQ+HBg==", "xTL2pemNQUVzew==", "HfBpyi7THjTuug==", "aKvVe3ox/6dU5A==", "sGlKFL1voNbJJQ==", "Df855Uire3CEkg==", "1T2mio/1JAEZUw==", "oGYaO9sXxZKjDQ==", "eKSFVBxJmuM+zA==", "StB/RHPOGqnKsQ==", "khLgK7SQRdhXcA==", "50lcmuBypEvtLg==", "P4vD9Scs+zpw7w==", "g6G1uj5hud8Y1A==", "W2Mq1fk/5q6FFQ==", "LjiWZK3dBz0/Sw==", "9voJC2qDWEyiig==", "xI7zGwUE2AZW9w==", "HExsdMJah3fLNg==", "aRfQxZa4ZuRxaA==", "sdVPqlHmOZXsqQ==", "DiY2OmEtzbXrGw==", "1uSpVaZzksR22g==", "o78V5PKRc1fMhA==", "e32KizXPLCZRRQ==", "SQlwm1pIrGylOA==", "kcvv9J0W8x04+Q==", "5JBTRcn0Eo6Cpw==", "PFLMKg6qTf8fZg==", "gHi6ZRfnDxp3XQ==", "WLolCtC5UGvqnA==", "LeGZu4RbsfhQwg==", "9SMG1EMF7onNAw==", "x1f8xCyCbsM5fg==", "H5Vjq+vcMbKkvw==", "as7fGr8+0CEe4Q==", "sgxAdXhgj1CDIA==", "D5ozhI2kVPbOlw==", "11is60r6C4dTVg==", "ogMQWh4Y6hTpCA==", "esGPNdlGtWV0yQ==", "SLV1JbbBNS+AtA==", "kHfqSnGfal4ddQ==", "5SxW+yV9i82nKw==", "Pe7JlOIj1Lw66g==", "gcS/2/tulllS0Q==", "WQYgtDwwySjPEA==", "LF2cBWjSKLt1Tg==", "9J8Daq+Md8rojw==", "xuv5esAL94Ac8g==", "HilmFQdVqPGBMw==", "a3LapFO3SWI7bQ==", "s7BFy5TpFhOmrA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP10', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP13', 'Object', {
	init: function () {
		this._resource = [ "iUnjEbERNA0uK1OEeA==", "iUnjEbERNA0uK1OEeA==", "D5LbIn8iaBpcVqYV8A==", "hts4M84zXBdyffWRiA==", "HjmrRP5E0DS4rFEq/Q==", "l3BIVU9V5DmWhwKuhQ==", "EatwZoFmuC7k+vc/DQ==", "mOKTdzB3jCPK0aS7dQ==", "PHJLiOGIvWhtRaJU5w==", "tTuomVCZiWVDbvHQnw==", "M+CQqp6q1XIxEwRBFw==", "uqlzuy+74X8fOFfFbw==", "IkvgzB/MbVzV6fN+Gg==", "qwID3a7dWVH7wqD6Yg==", "Ldk77mDuBUaJv1Vr6g==", "pJDY/9H/MUunlAbvkg==", "eOSWDd8NZ9Dailmo0w==", "8a11HG4cU930oQosqw==", "d3ZNL6AvD8qG3P+9Iw==", "/j+uPhE+O8eo96w5Ww==", "Zt09SSFJt+RiJgiCLg==", "75TeWJBYg+lMDVsGVg==", "aU/ma15r3/4+cK6X3g==", "4AYFeu966/MQW/0Tpg==", "RJbdhT6F2ri3z/v8NA==", "zd8+lI+U7rWZ5Kh4TA==", "SwQGp0GnsqLrmV3pxA==", "wk3ltvC2hq/Fsg5tvA==", "Wq92wcDBCowPY6rWyQ==", "0+aV0HHQPoEhSPlSsQ==", "VT2t47/jYpZTNQzDOQ==", "3HRO8g7yVpt9Hl9HQQ==", "8NUxGqMazr2pCbJNuw==", "eZzSCxIL+rCHIuHJww==", "/0fqONw4pqf1XxRYSw==", "dg4JKW0pkqrbdEfcMw==", "7uyaXl1eHokRpeNnRg==", "Z6V5T+xPKoQ/jrDjPg==", "4X5BfCJ8dpNN80Vytg==", "aDeibZNtQp5j2Bb2zg==", "zKd6kkKSc9XETBAZXA==", "Re6Zg/ODR9jqZ0OdJA==", "wzWhsD2wG8+YGrYMrA==", "SnxCoYyhL8K2MeWI1A==", "0p7R1rzWo+F84EEzoQ==", "W9cyxw3Hl+xSyxK32Q==", "3QwK9MP0y/sgtucmUQ==", "VEXp5XLl//YOnbSiKQ==", "iDGnF3wXqW1zg+vlaA==", "AXhEBs0GnWBdqLhhEA==", "h6N8NQM1wXcv1U3wmA==", "DuqfJLIk9XoB/h504A==", "lggMU4JTeVnLL7rPlQ==", "H0HvQjNCTVTlBOlL7Q==", "mZrXcf1xEUOXeRzaZQ==", "ENM0YExgJU65Uk9eHQ==", "tEPsn52fFAUexkmxjw==", "PQoPjiyOIAgw7Ro19w==", "u9E3veK9fB9CkO+kfw==", "MpjUrFOsSBJsu7wgBw==", "qnpH22PbxDGmahibcg==", "IzOkytLK8DyIQUsfCg==", "peic+Rz5rCv6PL6Ogg==", "LKF/6K3omCbUF+0K+g==", "/bdiNFs0gWdPEnmaaw==", "dP6BJeoltWphOSoeEw==", "8iW5FiQW6X0TRN+Pmw==", "e2xaB5UH3XA9b4wL4w==", "447JcKVwUVP3viiwlg==", "ascqYRRhZV7ZlXs07g==", "7BwSUtpSOUmr6I6lZg==", "ZVXxQ2tDDUSFw90hHg==", "wcUpvLq8PA8iV9vOjA==", "SIzKrQutCAIMfIhK9A==", "zlfynsWeVBV+AX3bfA==", "Rx4Rj3SPYBhQKi5fBA==", "3/yC+ET47Dua+4rkcQ==", "VrVh6fXp2Da00NlgCQ==", "0G5Z2jvahCHGrSzxgQ==", "WSe6y4rLsCzohn91+Q==", "hVP0OYQ55reVmCAyuA==", "DBoXKDUo0rq7s3O2wA==", "isEvG/sbjq3JzoYnSA==", "A4jMCkoKuqDn5dWjMA==", "m2pffXp9NoMtNHEYRQ==", "EiO8bMtsAo4DHyKcPQ==", "lPiEXwVfXplxYtcNtQ==", "HbFnTrROapRfSYSJzQ==", "uSG/sWWxW9/43YJmXw==", "MGhcoNSgb9LW9tHiJw==", "trNkkxqTM8WkiyRzrw==", "P/qHgquCB8iKoHf31w==", "pxgU9Zv1i+tAcdNMog==", "LlH35Crkv+ZuWoDI2g==", "qIrP1+TX4/EcJ3VZUg==", "IcMsxlXG1/wyDCbdKg==", "DWJTLvguT9rmG8vX0A==", "hCuwP0k/e9fIMJhTqA==", "AvCIDIcMJ8C6TW3CIA==", "i7lrHTYdE82UZj5GWA==", "E1v4agZqn+5et5r9LQ==", "mhIbe7d7q+NwnMl5VQ==", "HMkjSHlI9/QC4Tzo3Q==", "lYDAWchZw/ksym9spQ==", "MRAYphmm8rKLXmmDNw==", "uFn7t6i3xr+ldToHTw==", "PoLDhGaEmqjXCM+Wxw==", "t8sgldeVrqX5I5wSvw==", "Lymz4ufiIoYz8jipyg==", "pmBQ81bzFosd2Wstsg==", "ILtowJjASpxvpJ68Og==", "qfKL0SnRfpFBj804Qg==", "dYbFIycjKAo8kZJ/Aw==", "/M8mMpYyHAcSusH7ew==", "ehQeAVgBQBBgxzRq8w==", "8139EOkQdB1O7Gfuiw==", "a79uZ9ln+D6EPcNV/g==", "4vaNdmh2zDOqFpDRhg==", "ZC21RaZFkCTYa2VADg==", "7WRWVBdUpCn2QDbEdg==", "SfSOq8arlWJR1DAr5A==", "wL1tune6oW9//2OvnA==", "RmZVibmJ/XgNgpY+FA==", "zy+2mAiYyXUjqcW6bA==", "V80l7zjvRVbpeGEBGQ==", "3oTG/on+cVvHUzKFYQ==", "WF/+zUfNLUy1LscU6Q==", "0RYd3PbcGUGbBZSQkQ==", "53PEaLZoH86eJPIp1g==", "bjoneQd5K8OwD6Gtrg==", "6OEfSslKd9TCclQ8Jg==", "Yaj8W3hbQ9nsWQe4Xg==", "+UpvLEgsz/omiKMDKw==", "cAOMPfk9+/cIo/CHUw==", "9ti0DjcOp+B63gUW2w==", "f5FXH4Yfk+1U9VaSow==", "2wGP4FfgoqbzYVB9MQ==", "Ukhs8ebxlqvdSgP5SQ==", "1JNUwijCyryvN/ZowQ==", "Xdq305nT/rGBHKXsuQ==", "xTgkpKmkcpJLzQFXzA==", "THHHtRi1Rp9l5lLTtA==", "yqr/htaGGogXm6dCPA==", "Q+Mcl2eXLoU5sPTGRA==", "n5dSZWlleB5ErquBBQ==", "Ft6xdNh0TBNqhfgFfQ==", "kAWJRxZHEAQY+A2U9Q==", "GUxqVqdWJAk2014QjQ==", "ga75IZchqCr8Avqr+A==", "COcaMCYwnCfSKakvgA==", "jjwiA+gDwDCgVFy+CA==", "B3XBElkS9D2Ofw86cA==", "o+UZ7YjtxXYp6wnV4g==", "Kqz6/Dn88XsHwFpRmg==", "rHfCz/fPrWx1va/AEg==", "JT4h3kbemWFblvxEag==", "vdyyqXapFUKRR1j/Hw==", "NJVRuMe4IU+/bAt7Zw==", "sk5piwmLfVjNEf7q7w==", "OweKmriaSVXjOq1ulw==", "F6b1chVy0XM3LUBkbQ==", "nu8WY6Rj5X4ZBhPgFQ==", "GDQuUGpQuWlre+ZxnQ==", "kX3NQdtBjWRFULX15Q==", "CZ9eNus2AUePgRFOkA==", "gNa9J1onNUqhqkLK6A==", "Bg2FFJQUaV3T17dbYA==", "j0RmBSUFXVD9/OTfGA==", "K9S++vT6bBtaaOIwig==", "op1d60XrWBZ0Q7G08g==", "JEZl2IvYBAEGPkQleg==", "rQ+GyTrJMAwoFRehAg==", "Ne0Vvgq+vC/ixLMadw==", "vKT2r7uviCLM7+CeDw==", "On/OnHWc1DW+khUPhw==", "szYtjcSN4DiQuUaL/w==", "b0Jjf8p/tqPtpxnMvg==", "5guAbntugq7DjEpIxg==", "YNC4XbVd3rmx8b/ZTg==", "6ZlbTARM6rSf2uxdNg==", "cXvIOzQ7ZpdVC0jmQw==", "+DIrKoUqUpp7IBtiOw==", "fukTGUsZDo0JXe7zsw==", "96DwCPoIOoAndr13yw==", "UzAo9yv3C8uA4ruYWQ==", "2nnL5prmP8auyegcIQ==", "XKLz1VTVY9HctB2NqQ==", "1esQxOXEV9zyn04J0Q==", "TQmDs9Wz2/84TuqypA==", "xEBgomSi7/IWZbk23A==", "QptYkaqRs+VkGEynVA==", "y9K7gBuAh+hKMx8jLA==", "GsSmXO1cnqnRNouzvQ==", "k41FTVxNqqT/Hdg3xQ==", "FVZ9fpJ+9rONYC2mTQ==", "nB+ebyNvwr6jS34iNQ==", "BP0NGBMYTp1pmtqZQA==", "jbTuCaIJepBHsYkdOA==", "C2/WOmw6Joc1zHyMsA==", "giY1K90rEoob5y8IyA==", "Jrbt1AzUI8G8cynnWg==", "r/8Oxb3FF8ySWHpjIg==", "KSQ29nP2S9vgJY/yqg==", "oG3V58Lnf9bODtx20g==", "OI9GkPKQ8/UE33jNpw==", "scalgUOBx/gq9CtJ3w==", "Nx2dso2ym+9Yid7YVw==", "vlR+ozyjr+J2oo1cLw==", "YiAwUTJR+XkLvNIbbg==", "62nTQINAzXQll4GfFg==", "bbLrc01zkWNX6nQOng==", "5PsIYvxipW55wSeK5g==", "fBmbFcwVKU2zEIMxkw==", "9VB4BH0EHUCdO9C16w==", "c4tAN7M3QVfvRiUkYw==", "+sKjJgImdVrBbXagGw==", "XlJ72dPZRBFm+XBPiQ==", "1xuYyGLIcBxI0iPL8Q==", "UcCg+6z7LAs6r9ZaeQ==", "2IlD6h3qGAYUhIXeAQ==", "QGvQnS2dlCXeVSFldA==", "ySIzjJyMoCjwfnLhDA==", "T/kLv1K//D+CA4dwhA==", "xrDoruOuyDKsKNT0/A==", "6hGXRk5GUBR4Pzn+Bg==", "Y1h0V/9XZBlWFGp6fg==", "5YNMZDFkOA4kaZ/r9g==", "bMqvdYB1DAMKQsxvjg==", "9Cg8ArACgCDAk2jU+w==", "fWHfEwETtC3uuDtQgw==", "+7rnIM8g6Dqcxc7BCw==", "cvMEMX4x3Dey7p1Fcw==", "1mPczq/O7XwVepuq4Q==", "Xyo/3x7f2XE7UcgumQ==", "2fEH7NDshWZJLD2/EQ==", "ULjk/WH9sWtnB247aQ==", "yFp3ilGKPUit1sqAHA==", "QROUm+CbCUWD/ZkEZA==", "x8isqC6oVVLxgGyV7A==", "ToFPuZ+5YV/fqz8RlA==", "kvUBS5FLN8SitWBW1Q==", "G7ziWiBaA8mMnjPSrQ==", "nWfaae5pX97+48ZDJQ==", "FC45eF94a9PQyJXHXQ==", "jMyqD28P5/AaGTF8KA==", "BYVJHt4e0/00MmL4UA==", "g15xLRAtj+pGT5dp2A==", "CheSPKE8u+doZMTtoA==", "rodKw3DDiqzP8MICMg==", "J86p0sHSvqHh25GGSg==", "oRWR4Q/h4raTpmQXwg==", "KFxy8L7w1ru9jTeTug==", "sL7hh46HWph3XJMozw==", "OfcClj+WbpVZd8Cstw==", "vyw6pfGlMoIrCjU9Pw==", "NmXZtEC0Bo8FIWa5Rw==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP13', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP15', 'Object', {
	init: function () {
		this._resource = [ "HcRvo3BKCmlpi4SXIIYa", "HcRvo3BKCmlpi4SXIIYa", "OpXeW+CUFNLSCxUzQBE0", "J1Gx+JDeHru7gJGkYJcu", "dDehtt01KLm5FipmgCJo", "afPOFa1/ItDQna7xoKRy", "TqJ/7T2hPGtrHT9VwDNc", "U2YQTk3rNgIClrvC4LVG", "6G5fcadqUG9vLFTMHUTQ", "9aow0tcgWgYGp9BbPcLK", "0vuBKkf+RL29J0H/XVXk", "zz/uiTe0TtTUrMVofdP+", "nFn+x3pfeNbWOn6qnWa4", "gZ2RZAoVcr+/sfo9veCi", "pswgnJrLbAQEMWuZ3XeM", "uwhPP+qBZm1tuu8O/fGW", "zdy+4lPUoN7eWKiFOoi9", "0BjRQSOeqre30ywSGg6n", "90lgubNAtAwMU722epmJ", "6o0PGsMKvmVl2DkhWh+T", "uesfVI7hiGdnToLjuqrV", "pC9w9/6rgg4OxQZ0mizP", "g37BD251nLW1RZfQ+rvh", "nrqurB4/ltzczhNH2j37", "JbLhk/S+8LGxdPxJJ8xt", "OHaOMIT0+tjY/3jeB0p3", "Hyc/yBQq5GNjf+l6Z91Z", "AuNQa2Rg7goK9G3tR1tD", "UYVAJSmL2AgIYtYvp+4F", "TEEvhlnB0mFh6VK4h2gf", "axCefskfzNraacMc5/8x", "dtTx3blVxrOz4keLx3kr", "h6Vh2aa1XaGhsE0XdA1n", "mmEOetb/V8jIO8mAVIt9", "vTC/gkYhSXNzu1gkNBxT", "oPTQITZrQxoaMNyzFJpJ", "85LAb3uAdRgYpmdx9C8P", "7lavzAvKf3FxLePm1KkV", "yQceNJsUYcrKrXJCtD47", "1MNxl+tea6OjJvbVlLgh", "b8s+qAHfDc7OnBnbaUm3", "cg9RC3GVB6enF51MSc+t", "VV7g8+FLGRwclwzoKViD", "SJqPUJEBE3V1HIh/Cd6Z", "G/yfHtzqJXd3ijO96Wvf", "BjjwvaygLx4eAbcqye3F", "IWlBRTx+MaWlgSaOqXrr", "PK0u5kw0O8zMCqIZifzx", "SnnfO/Vh/X9/6OWSToXa", "V72wmIUr9xYWY2EFbgPA", "cOwBYBX16a2t4/ChDpTu", "bShuw2W/48TEaHQ2LhL0", "Pk5+jShU1cbG/s/0zqey", "I4oRLlge36+vdUtj7iGo", "BNug1sjAwRQU9drHjraG", "GR/PdbiKy319fl5QrjCc", "oheASlILrRAQxLFeU8EK", "v9Pv6SJBp3l5TzXJc0cQ", "mIJeEbKfucLCz6RtE9A+", "hUYxssLVs6urRCD6M1Yk", "1iAh/I8+hamp0ps40+Ni", "y+ROX/90j8DAWR+v82V4", "7LX/p2+qkXt72Y4Lk/JW", "8XGQBB/gmxISUgqcs3RM", "E1fCr1F3ul9ffZou6BrO", "DpOtDCE9sDY29h65yJzU", "KcIc9LHjro2Ndo8dqAv6", "NAZzV8GppOTk/QuKiI3g", "Z2BjGYxCkubma7BIaDim", "eqQMuvwImI+P4DTfSL68", "XfW9QmzWhjQ0YKV7KCmS", "QDHS4RycjF1d6yHsCK+I", "+zmd3vYd6jAwUc7i9V4e", "5v3yfYZX4FlZ2kp11dgE", "waxDhRaJ/uLiWtvRtU8q", "3GgsJmbD9IuL0V9Glckw", "jw48aCsowomJR+SEdXx2", "kspTy1tiyODgzGATVfps", "tZviM8u81ltbTPG3NW1C", "qF+NkLv23DIyx3UgFetY", "3ot8TQKjGoGBJTKr0pJz", "w08T7nLpEOjorrY88hRp", "5B6iFuI3DlNTLieYkoNH", "+drNtZJ9BDo6paMPsgVd", "qrzd+9+WMjg4MxjNUrAb", "t3iyWK/cOFFRuJxacjYB", "kCkDoD8CJurqOA3+EqEv", "je1sA09ILIODs4lpMic1", "NuUjPKXJSu7uCWZnz9aj", "KyFMn9WDQIeHguLw71C5", "DHD9Z0VdXjw8AnNUj8eX", "EbSSxDUXVFVViffDr0GN", "QtKCinj8YldXH0wBT/TL", "XxbtKQi2aD4+lMiWb3LR", "eEdc0ZhodoWFFFkyD+X/", "ZYMzcugifOzsn92lL2Pl", "lPKjdvfC5/7+zdc5nBep", "iTbM1YeI7ZeXRlOuvJGz", "rmd9LRdW8ywsxsIK3Aad", "s6MSjmcc+UVFTUad/ICH", "4MUCwCr3z0dH2/1fHDXB", "/QFtY1q9xS4uUHnIPLPb", "2lDcm8pj25WV0OhsXCT1", "x5SzOLop0fz8W2z7fKLv", "fJz8B1Cot5GR4YP1gVN5", "YViTpCDivfj4agdiodVj", "RgkiXLA8o0ND6pbGwUJN", "W81N/8B2qSoqYRJR4cRX", "CKtdsY2dnygo96mTAXER", "FW8yEv3XlUFBfC0EIfcL", "Mj6D6m0Ji/r6/LygQWAl", "L/rsSR1DgZOTdzg3YeY/", "WS4dlKQWRyAglX+8pp8U", "ROpyN9RcTUlJHvsrhhkO", "Y7vDz0SCU/LynmqP5o4g", "fn+sbDTIWZubFe4Yxgg6", "LRm8Inkjb5mZg1XaJr18", "MN3TgQlpZfDwCNFNBjtm", "F4xieZm3e0tLiEDpZqxI", "CkgN2un9cSIiA8R+RipS", "sUBC5QN8F09PuStwu9vE", "rIQtRnM2HSYmMq/nm13e", "i9WcvuPoA52dsj5D+8rw", "lhHzHZOiCfT0ObrU20zq", "xXfjU95JP/b2rwEWO/ms", "2LOM8K4DNZ+fJIWBG3+2", "/+I9CD7dKyQkpBQle+iY", "4iZSq06XIU1NL5CyW26C", "Jq6ZQ6Luab6++ilczTSB", "O2r24NKkY9fXca3L7bKb", "HDtHGEJ6fWxs8TxvjSW1", "Af8ouzIwdwUFerj4raOv", "Upk49X/bQQcH7AM6TRbp", "T11XVg+RS25uZ4etbZDz", "aAzmrp9PVdXV5xYJDQfd", "dciJDe8FX7y8bJKeLYHH", "zsDGMgWEOdHR1n2Q0HBR", "0wSpkXXOM7i4XfkH8PZL", "9FUYaeUQLQMD3WijkGFl", "6ZF3ypVaJ2pqVuw0sOd/", "uvdnhNixEWhowFf2UFI5", "pzMIJ6j7GwEBS9NhcNQj", "gGK53zglBbq6y0LFEEMN", "nabWfEhvD9PTQMZSMMUX", "63InofE6yWBgooHZ97w8", "9rZIAoFwwwkJKQVO1zom", "0ef5+hGu3bKyqZTqt60I", "zCOWWWHk19vbIhB9lysS", "n0WGFywP4dnZtKu/d55U", "goHptFxF67CwPy8oVxhO", "pdBYTMyb9QsLv76MN49g", "uBQ377zR/2JiNDobFwl6", "Axx40FZQmQ8PjtUV6vjs", "HtgXcyYak2ZmBVGCyn72", "OYmmi7bEjd3dhcAmqunY", "JE3JKMaOh7S0DkSxim/C", "dyvZZotlsba2mP9zatqE", "au+2xfsvu9/fE3vkSlye", "Tb4HPWvxpWRkk+pAKsuw", "UHponhu7rw0NGG7XCk2q", "oQv4mgRbNB8fSmRLuTnm", "vM+XOXQRPnZ2weDcmb/8", "m54mweTPIM3NQXF4+SjS", "hlpJYpSFKqSkyvXv2a7I", "1TxZLNluHKamXE4tORuO", "yPg2j6kkFs/P18q6GZ2U", "76mHdzn6CHR0V1seeQq6", "8m3o1EmwAh0d3N+JWYyg", "SWWn66MxZHBwZjCHpH02", "VKHISNN7bhkZ7bQQhPss", "c/B5sEOlcKKibSW05GwC", "bjQWEzPvesvL5qEjxOoY", "PVIGXX4ETMnJcBrhJF9e", "IJZp/g5ORqCg+552BNlE", "B8fYBp6QWBsbew/SZE5q", "GgO3pe7aUnJy8ItFRMhw", "bNdGeFePlMHBEszOg7Fb", "cRMp2yfFnqiomUhZozdB", "VkKYI7cbgBMTGdn9w6Bv", "S4b3gMdRinp6kl1q4yZ1", "GODnzoq6vHh4BOaoA5Mz", "BSSIbfrwthERj2I/IxUp", "InU5lWouqKqqD/ObQ4IH", "P7FWNhpkosPDhHcMYwQd", "hLkZCfDlxK6uPpgCnvWL", "mX12qoCvzsfHtRyVvnOR", "vizHUhBx0Hx8NY0x3uS/", "o+io8WA72hUVvgmm/mKl", "8I64vy3Q7BcXKLJkHtfj", "7UrXHF2a5n5+ozbzPlH5", "yhtm5M1E+MXFI6dXXsbX", "198JR70O8qysqCPAfkDN", "Nflb7POZ0+Hhh7NyJS5P", "KD00T4PT2YiIDDflBahV", "D2yFtxMNxzMzjKZBZT97", "EqjqFGNHzVpaByLWRblh", "Qc76Wi6s+1hYkZkUpQwn", "XAqV+V7m8TExGh2DhYo9", "e1skAc4474qKmown5R0T", "Zp9Lor5y5ePjEQiwxZsJ", "3ZcEnVTzg46Oq+e+OGqf", "wFNrPiS5iefnIGMpGOyF", "5wLaxrRnl1xcoPKNeHur", "+sa1ZcQtnTU1K3YaWP2x", "qaClK4nGqzc3vc3YuEj3", "tGTKiPmMoV5eNklPmM7t", "kzV7cGlSv+Xlttjr+FnD", "jvEU0xkYtYyMPVx82N/Z", "+CXlDqBNcz8/3xv3H6by", "5eGKrdAHeVZWVJ9gPyDo", "wrA7VUDZZ+3t1A7EX7fG", "33RU9jCTbYSEX4pTfzHc", "jBJEuH14W4aGyTGRn4Sa", "kdYrGw0yUe/vQrUGvwKA", "toea453sT1RUwiSi35Wu", "q0P1QO2mRT09SaA1/xO0", "EEu6fwcnI1BQ8087AuIi", "DY/V3HdtKTk5eMusImQ4", "Kt5kJOezN4KC+FoIQvMW", "NxoLh5f5Pevrc96fYnUM", "ZHwbydoSC+np5WVdgsBK", "ebh0aqpYAYCAbuHKokZQ", "XunFkjqGHzs77nBuwtF+", "Qy2qMUrMFVJSZfT54ldk", "slw6NVUsjkBAN/5lUSMo", "r5hVliVmhCkpvHrycaUy", "iMnkbrW4mpKSPOtWETIc", "lQ2LzcXykPv7t2/BMbQG", "xmubg4gZpvn5IdQD0QFA", "26/0IPhTrJCQqlCU8Yda", "/P5F2GiNsisrKsEwkRB0", "4ToqexjHuEJCoUWnsZZu", "WjJlRPJG3i8vG6qpTGf4", "R/YK54IM1EZGkC4+bOHi", "YKe7HxLSyv39EL+aDHbM", "fWPUvGKYwJSUmzsNLPDW", "LgXE8i9z9paWDYDPzEWQ", "M8GrUV85/P//hgRY7MOK", "FJAaqc/n4kREBpX8jFSk", "CVR1Cr+t6C0tjRFrrNK+", "f4CE1wb4Lp6eb1bga6uV", "YkTrdHayJPf35NJ3Sy2P", "RRVajOZsOkxMZEPTK7qh", "WNE1L5YmMCUl78dECzy7", "C7clYdvNBicneXyG64n9", "FnNKwquHDE5O8vgRyw/n", "MSL7OjtZEvX1cmm1q5jJ", "LOaUmUsTGJyc+e0iix7T", "l+7bpqGSfvHxQwIsdu9F", "iiq0BdHYdJiYyIa7Vmlf", "rXsF/UEGaiMjSBcfNv5x", "sL9qXjFMYEpKw5OIFnhr", "49l6EHynVkhIVShK9s0t", "/h0VswztXCEh3qzd1ks3", "2UykS5wzQpqaXj15ttwZ", "xIjL6Ox5SPPz1bnulloD" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP15', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP16', 'Object', {
	init: function () {
		this._resource = [ "Ow1ovUTRHgijQSnlYjIkOw==", "Ow1ovUTRHgijQSnlYjIkOw==", "dhrQZ4i/PBBbglLXxGRIdg==", "TRe42sxuIhj4w3syplZsTQ==", "7DS9zg1jeCC2GaSzlciQ7A==", "1znVc0myZigVWI1W9/q01w==", "mi5tqYXcRDDtm/ZkUazYmg==", "oSMFFMENWjhO2t+BM578oQ==", "xWhngRrG8EBxMlV7N409xQ==", "/mUPPF4X7kjSc3yeVb8Z/g==", "s3K35pJ5zFAqsAes8+l1sw==", "iH/fW9ao0liJ8S5JkdtRiA==", "KVzaTxeliGDHK/HIokWtKQ==", "ElGy8lN0lmhkatgtwHeJEg==", "X0YKKJ8atHCcqaMfZiHlXw==", "ZEtildvLqng/6Ir6BBPBZA==", "l9DOHzSR/YDiZKr2bgd6lw==", "rN2monBA44hBJYMTDDVerA==", "4coeeLwuwZC55vghqmMy4Q==", "2sd2xfj/35gap9HEyFEW2g==", "e+Rz0TnyhaBUfQ5F+8/qew==", "QOkbbH0jm6j3PCegmf3OQA==", "Df6jtrFNubAP/1ySP6uiDQ==", "NvPLC/Wcp7isvnV3XZmGNg==", "Uripni5XDcCTVv+NWYpHUg==", "abXBI2qGE8gwF9ZoO7hjaQ==", "JKJ5+aboMdDI1K1ane4PJA==", "H68RROI5L9hrlYS//9wrHw==", "vowUUCM0deAlT1s+zELXvg==", "hYF87Wfla+iGDnLbrnDzhQ==", "yJbEN6uLSfB+zQnpCCafyA==", "85usiu9aV/jdjCAMahS78w==", "M72BPmg/5x3ZyEnx3A70Mw==", "CLDpgyzu+RV6iWAUvjzQCA==", "RadRWeCA2w2CShsmGGq8RQ==", "fqo55KRRxQUhCzLDeliYfg==", "34k88GVcnz1v0e1CScZk3w==", "5IRUTSGNgTXMkMSnK/RA5A==", "qZPsl+3joy00U7+VjaIsqQ==", "kp6EKqkyvSWXEpZw75AIkg==", "9tXmv3L5F12o+hyK64PJ9g==", "zdiOAjYoCVULuzVvibHtzQ==", "gM822PpGK03zeE5dL+eBgA==", "u8JeZb6XNUVQOWe4TdWluw==", "GuFbcX+ab30e47g5fktZGg==", "IewzzDtLcXW9opHcHHl9IQ==", "bPuLFvclU21FYeruui8RbA==", "V/bjq7P0TWXmIMML2B01Vw==", "pG1PIVyuGp07rOMHsgmOpA==", "n2AnnBh/BJWY7cri0Duqnw==", "0nefRtQRJo1gLrHQdm3G0g==", "6Xr3+5DAOIXDb5g1FF/i6Q==", "SFny71HNYr2NtUe0J8EeSA==", "c1SaUhUcfLUu9G5RRfM6cw==", "PkMiiNlyXq3WNxVj46VWPg==", "BU5KNZ2jQKV1djyGgZdyBQ==", "YQUooEZo6t1KnrZ8hYSzYQ==", "WghAHQK59NXp35+Z57aXWg==", "Fx/4x87X1s0RHOSrQeD7Fw==", "LBKQeooGyMWyXc1OI9LfLA==", "jTGVbksLkv38hxLPEEwjjQ==", "tjz90w/ajPVfxjsqcn4Htg==", "+ytFCcO0ru2nBUAY1Chr+w==", "wCYttIdlsOUERGn9thpPwA==", "ZmcffNB+0zqvjZL/pRz1Zg==", "XWp3wZSvzTIMzLsaxy7RXQ==", "EH3PG1jB7yr0D8AoYXi9EA==", "K3CnphwQ8SJXTunNA0qZKw==", "ilOist0dqxoZlDZMMNRlig==", "sV7KD5nMtRK61R+pUuZBsQ==", "/Ely1VWilwpCFmSb9LAt/A==", "x0QaaBFziQLhV01+loIJxw==", "ow94/cq4I3rev8eEkpHIow==", "mAIQQI5pPXJ9/u5h8KPsmA==", "1RWomkIHH2qFPZVTVvWA1Q==", "7hjAJwbWAWImfLy2NMek7g==", "TzvFM8fbW1popmM3B1lYTw==", "dDatjoMKRVLL50rSZWt8dA==", "OSEVVE9kZ0ozJDHgwz0QOQ==", "Aix96Qu1eUKQZRgFoQ80Ag==", "8bfRY+TvLrpN6TgJyxuP8Q==", "yrq53qA+MLLuqBHsqSmryg==", "h60BBGxQEqoWa2reD3/Hhw==", "vKBpuSiBDKK1KkM7bU3jvA==", "HYNsremMVpr78Jy6XtMfHQ==", "Jo4EEK1dSJJYsbVfPOE7Jg==", "a5m8ymEzaoqgcs5tmrdXaw==", "UJTUdyXidIIDM+eI+IVzUA==", "NN+24v4p3vo8221y/JayNA==", "D9LeX7r4wPKfmkSXnqSWDw==", "QsVmhXaW4upnWT+lOPL6Qg==", "ecgOODJH/OLEGBZAWsDeeQ==", "2OsLLPNKptqKwsnBaV4i2A==", "4+ZjkbebuNIpg+AkC2wG4w==", "rvHbS3v1msrRQJsWrTpqrg==", "lfyz9j8khMJyAbLzzwhOlQ==", "VdqeQrhBNCd2RdsOeRIBVQ==", "btf2//yQKi/VBPLrGyAlbg==", "I8BOJTD+CDctx4nZvXZJIw==", "GM0mmHQvFj+OhqA830RtGA==", "ue4jjLUiTAfAXH+97NqRuQ==", "guNLMfHzUg9jHVZYjui1gg==", "z/Tz6z2dcBeb3i1qKL7Zzw==", "9PmbVnlMbh84nwSPSoz99A==", "kLL5w6KHxGcHd451Tp88kA==", "q7+RfuZW2m+kNqeQLK0Yqw==", "5qgppCo4+Hdc9dyiivt05g==", "3aVBGW7p5n//tPVH6MlQ3Q==", "fIZEDa/kvEexbirG21esfA==", "R4sssOs1ok8SLwMjuWWIRw==", "CpyUaidbgFfq7HgRHzPkCg==", "MZH812OKnl9JrVH0fQHAMQ==", "wgpQXYzQyaeUIXH4FxV7wg==", "+Qc44MgB1683YFgddSdf+Q==", "tBCAOgRv9bfPoyMv03EztA==", "jx3oh0C+679s4grKsUMXjw==", "Lj7tk4GzsYciONVLgt3rLg==", "FTOFLsVir4+Befyu4O/PFQ==", "WCQ99AkMjZd5uoecRrmjWA==", "YylVSU3dk5/a+655JIuHYw==", "B2I33JYWOeflEySDIJhGBw==", "PG9fYdLHJ+9GUg1mQqpiPA==", "cXjnux6pBfe+kXZU5PwOcQ==", "SnWPBlp4G/8d0F+xhs4qSg==", "61aKEpt1QcdTCoAwtVDW6w==", "0Fvir9+kX8/wS6nV12Ly0A==", "nUxadRPKfdcIiNLncTSenQ==", "pkEyyFcbY9+ryfsCEwa6pg==", "zM4++L38u3RDBznjVzj3zA==", "98NWRfktpXzgRhAGNQrT9w==", "utTunzVDh2QYhWs0k1y/ug==", "gdmGInGSmWy7xELR8W6bgQ==", "IPqDNrCfw1T1Hp1QwvBnIA==", "G/fri/RO3VxWX7S1oMJDGw==", "VuBTUTgg/0SunM+HBpQvVg==", "be077Hzx4UwN3eZiZKYLbQ==", "CaZZeac6SzQyNWyYYLXKCQ==", "MqsxxOPrVTyRdEV9AofuMg==", "f7yJHi+FdyRptz5PpNGCfw==", "RLHho2tUaSzK9heqxuOmRA==", "5ZLkt6pZMxSELMgr9X1a5Q==", "3p+MCu6ILRwnbeHOl09+3g==", "k4g00CLmDwTfrpr8MRkSkw==", "qIVcbWY3EQx877MZUys2qA==", "Wx7w54ltRvShY5MVOT+NWw==", "YBOYWs28WPwCIrrwWw2pYA==", "LQQggAHSeuT64cHC/VvFLQ==", "FglIPUUDZOxZoOgnn2nhFg==", "typNKYQOPtQXejemrPcdtw==", "jCcllMDfINy0Ox5DzsU5jA==", "wTCdTgyxAsRM+GVxaJNVwQ==", "+j3180hgHMzvuUyUCqFx+g==", "nnaXZpOrtrTQUcZuDrKwng==", "pXv/29d6qLxzEO+LbICUpQ==", "6GxHARsUiqSL05S5ytb46A==", "02EvvF/FlKwokr1cqOTc0w==", "ckIqqJ7IzpRmSGLdm3ogcg==", "SU9CFdoZ0JzFCUs4+UgESQ==", "BFj6zxZ38oQ9yjAKXx5oBA==", "P1WSclKm7IyeixnvPSxMPw==", "/3O/xtXDXGmaz3ASizYD/w==", "xH7Xe5ESQmE5jln36QQnxA==", "iWlvoV18YHnBTSLFT1JLiQ==", "smQHHBmtfnFiDAsgLWBvsg==", "E0cCCNigJEks1tShHv6TEw==", "KEpqtZxxOkGPl/1EfMy3KA==", "ZV3Sb1AfGFl3VIZ22prbZQ==", "XlC60hTOBlHUFa+TuKj/Xg==", "OhvYR88FrCnr/SVpvLs+Og==", "ARaw+ovUsiFIvAyM3okaAQ==", "TAEIIEe6kDmwf3e+eN92TA==", "dwxgnQNrjjETPl5bGu1Sdw==", "1i9licJm1Ald5IHaKXOu1g==", "7SINNIa3ygH+pag/S0GK7Q==", "oDW17krZ6BkGZtMN7RfmoA==", "mzjdUw4I9hGlJ/rojyXCmw==", "aKNx2eFSoel4q9rk5TF5aA==", "U64ZZKWDv+Hb6vMBhwNdUw==", "HrmhvmntnfkjKYgzIVUxHg==", "JbTJAy08g/GAaKHWQ2cVJQ==", "hJfMF+wx2cnOsn5XcPnphA==", "v5qkqqjgx8Ft81eyEsvNvw==", "8o0ccGSO5dmVMCyAtJ2h8g==", "yYB0zSBf+9E2cQVl1q+FyQ==", "rcsWWPuUUakJmY+f0rxErQ==", "lsZ+5b9FT6Gq2KZ6sI5glg==", "29HGP3MrbblSG91IFtgM2w==", "4Nyugjf6c7HxWvStdOoo4A==", "Qf+rlvb3KYm/gCssR3TUQQ==", "evLDK7ImN4EcwQLJJUbweg==", "N+V78X5IFZnkAnn7gxCcNw==", "DOgTTDqZC5FHQ1Ae4SK4DA==", "qqkhhG2CaE7siqsc8iQCqg==", "kaRJOSlTdkZPy4L5kBYmkQ==", "3LPx4+U9VF63CPnLNkBK3A==", "576ZXqHsSlYUSdAuVHJu5w==", "Rp2cSmDhEG5akw+vZ+ySRg==", "fZD09yQwDmb50iZKBd62fQ==", "MIdMLeheLH4BEV14o4jaMA==", "C4okkKyPMnaiUHSdwbr+Cw==", "b8FGBXdEmA6duP5nxak/bw==", "VMwuuDOVhgY++deCp5sbVA==", "GduWYv/7pB7GOqywAc13GQ==", "Itb+37squhZle4VVY/9TIg==", "g/X7y3on4C4roVrUUGGvgw==", "uPiTdj72/iaI4HMxMlOLuA==", "9e8rrPKY3D5wIwgDlAXn9Q==", "zuJDEbZJwjbTYiHm9jfDzg==", "PXnvm1kTlc4O7gHqnCN4PQ==", "BnSHJh3Ci8atrygP/hFcBg==", "S2M//NGsqd5VbFM9WEcwSw==", "cG5XQZV9t9b2LXrYOnUUcA==", "0U1SVVRw7e6496VZCevo0Q==", "6kA66BCh8+Ybtoy8a9nM6g==", "p1eCMtzP0f7jdfeOzY+gpw==", "nFrqj5gez/ZANN5rr72EnA==", "+BGIGkPVZY5/3FSRq65F+A==", "wxzgpwcEe4bcnX10yZxhww==", "jgtYfctqWZ4kXgZGb8oNjg==", "tQYwwI+7R5aHHy+jDfgptQ==", "FCU11E62Ha7JxfAiPmbVFA==", "LyhdaQpnA6ZqhNnHXFTxLw==", "Yj/ls8YJIb6SR6L1+gKdYg==", "WTKNDoLYP7YxBosQmDC5WQ==", "mRSgugW9j1M1QuLtLir2mQ==", "ohnIB0FskVuWA8sITBjSog==", "7w5w3Y0Cs0NuwLA66k6+7w==", "1AMYYMnTrUvNgZnfiHya1A==", "dSAddAje93ODW0Zeu+JmdQ==", "Ti11yUwP6XsgGm+72dBCTg==", "AzrNE4Bhy2PY2RSJf4YuAw==", "ODelrsSw1Wt7mD1sHbQKOA==", "XHzHOx97fxNEcLeWGafLXA==", "Z3GvhluqYRvnMZ5ze5XvZw==", "KmYXXJfEQwMf8uVB3cODKg==", "EWt/4dMVXQu8s8ykv/GnEQ==", "sEh69RIYBzPyaRMljG9bsA==", "i0USSFbJGTtRKDrA7l1/iw==", "xlKqkpqnOyOp60HySAsTxg==", "/V/CL952JSsKqmgXKjk3/Q==", "DsRupTEsctPXJkgbQC2MDg==", "NckGGHX9bNt0Z2H+Ih+oNQ==", "eN6+wrmTTsOMpBrMhEnEeA==", "Q9PWf/1CUMsv5TMp5nvgQw==", "4vDTazxPCvNhP+yo1eUc4g==", "2f271nieFPvCfsVNt9c42Q==", "lOoDDLTwNuM6vb5/EYFUlA==", "r+drsfAhKOuZ/Jeac7Nwrw==", "y6wJJCvqgpOmFB1gd6Cxyw==", "8KFhmW87nJsFVTSFFZKV8A==", "vbbZQ6NVvoP9lk+3s8T5vQ==", "hrux/ueEoIte12ZS0fbdhg==", "J5i06iaJ+rMQDbnT4mghJw==", "HJXcV2JY5LuzTJA2gFoFHA==", "UYJkja42xqNLj+sEJgxpUQ==", "ao8MMOrn2KvozsLhRD5Nag==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP16', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP17', 'Object', {
	init: function () {
		this._resource = [ "d0JTeHcWxVP5KY+GVTV9Y08=", "d0JTeHcWxVP5KY+GVTV9Y08=", "7oSm8O4sl6bvUgMRqmr6xp4=", "mcb1iJk6UvUWe4yX/1+HpdE=", "wRVR/cFYM1HDpAYiSdTpkSE=", "tlcChbZO9gI6jYmkHOGU8m4=", "L5H3DS90pPcs9gUz474TV78=", "WNOkdVhiYaTV34q1totuNPA=", "nyqi55+wZqKbVQxEkrXPP0I=", "6Gjxn+imo/FifIPCx4CyXA0=", "ca4EF3Gc8QR0Bw9VON81+dw=", "BuxXbwaKNFeNLoDTbepImpM=", "Xj/zGl7oVfNY8Qpm22EmrmM=", "KX2gYin+kKCh2IXgjlRbzSw=", "sLtV6rDEwlW3owl3cQvcaP0=", "x/kGksfSBwZOiobxJD6hC7I=", "I1RZ0yN9zFkrqhiIOXeDfoQ=", "VBYKq1RrCQrSg5cObEL+Hcs=", "zdD/I81RW//E+BuZkx15uBo=", "upKsW7pHnqw90ZQfxigE21U=", "4kEILuIl/wjoDh6qcKNq76U=", "lQNbVpUzOlsRJ5EsJZYXjOo=", "DMWu3gwJaK4HXB272smQKTs=", "e4f9pnsfrf3+dZI9j/ztSnQ=", "vH77NLzNqvuw/xTMq8JMQcY=", "yzyoTMvbb6hJ1ptK/vcxIok=", "UvpdxFLhPV1frRfdAai2h1g=", "JbgOvCX3+A6mhJhbVJ3L5Bc=", "fWuqyX2VmapzWxLu4hal0Oc=", "Cin5sQqDXPmKcp1otyPYs6g=", "k+8MOZO5DgycCRH/SHxfFnk=", "5K1fQeSvy19lIJ55HUkidTY=", "Rqiyu0b6hbJWSTANcu4b/BU=", "MerhwzHsQOGvYL+LJ9tmn1o=", "qCwUS6jWEhS5GzMc2IThOos=", "325HM9/A10dAMryajbGcWcQ=", "h73jRoeituOV7TYvOzrybTQ=", "8P+wPvC0c7BsxLmpbg+PDns=", "aTlFtmmOIUV6vzU+kVAIq6o=", "HnsWzh6Y5BaDlrq4xGV1yOU=", "2YIQXNlK4xDNHDxJ4FvUw1c=", "rsBDJK5cJkM0NbPPtW6poBg=", "Nwa2rDdmdLYiTj9YSjEuBck=", "QETl1EBwseXbZ7DeHwRTZoY=", "GJdBoRgS0EEOuDprqY89UnY=", "b9US2W8EFRL3kbXt/LpAMTk=", "9hPnUfY+R+fh6jl6A+XHlOg=", "gVG0KYEogrQYw7b8VtC696c=", "ZfzraGWHSet94yiFS5mYgpE=", "Er64EBKRjLiEyqcDHqzl4d4=", "i3hNmIur3k2SsSuU4fNiRA8=", "/Doe4Py9Gx5rmKQStMYfJ0A=", "pOm6laTferq+Ry6nAk1xE7A=", "06vp7dPJv+lHbqEhV3gMcP8=", "Sm0cZUrz7RxRFS22qCeL1S4=", "PS9PHT3lKE+oPKIw/RL2tmE=", "+tZJj/o3L0nmtiTB2SxXvdM=", "jZQa940h6hofn6tHjBkq3pw=", "FFLvfxQbuO8J5CfQc0ate00=", "YxC8B2MNfbzwzahWJnPQGAI=", "O8MYcjtvHBglEiLjkPi+LPI=", "TIFLCkx52UvcO61lxc3DT70=", "1Ue+gtVDi77KQCHyOpJE6mw=", "ogXt+qJVTu0zaa50b6c5iSM=", "jE15a4zpF3mskmAa5ME25So=", "+w8qE/v/0ipVu++csfRLhmU=", "Ysnfm2LFgN9DwGMLTqvMI7Q=", "FYuM4xXTRYy66eyNG56xQPs=", "TVgolk2xJChvNmY4rRXfdAs=", "Ohp77jqn4XuWH+m++CCiF0Q=", "o9yOZqOds46AZGUpB38lspU=", "1J7dHtSLdt15TeqvUkpY0do=", "E2fbjBNZcds3x2xednT52mg=", "ZCWI9GRPtIjO7uPYI0GEuSc=", "/eN9fP115n3YlW9P3B4DHPY=", "iqEuBIpjIy4hvODJiSt+f7k=", "0nKKcdIBQor0Y2p8P6AQS0k=", "pTDZCaUXh9kNSuX6apVtKAY=", "PPYsgTwt1SwbMWltlcrqjdc=", "S7R/+Us7EH/iGObrwP+X7pg=", "rxkguK+U2yCHOHiS3ba1m64=", "2FtzwNiCHnN+EfcUiIPI+OE=", "QZ2GSEG4TIZoanuDd9xPXTA=", "Nt/VMDauidWRQ/QFIukyPn8=", "bgxxRW7M6HFEnH6wlGJcCo8=", "GU4iPRnaLSK9tfE2wVchacA=", "gIjXtYDgf9erzn2hPgimzBE=", "98qEzff2uoRS5/Inaz3br14=", "MDOCXzAkvYIcbXTWTwN6pOw=", "R3HRJ0cyeNHlRPtQGjYHx6M=", "3rckr94IKiTzP3fH5WmAYnI=", "qfV316ke73cKFvhBsFz9AT0=", "8SbTovF8jtPfyXL0BteTNc0=", "hmSA2oZqS4Am4P1yU+LuVoI=", "H6J1Uh9QGXUwm3HlrL1p81M=", "aOAmKmhG3CbJsv5j+YgUkBw=", "yuXL0MoTksv621AXli8tGT8=", "vaeYqL0FV5gD8t+RwxpQenA=", "JGFtICQ/BW0ViVMGPEXX36E=", "UyM+WFMpwD7soNyAaXCqvO4=", "C/CaLQtLoZo5f1Y13/vEiB4=", "fLLJVXxdZMnAVtmzis6561E=", "5XQ83eVnNjzWLVUkdZE+ToA=", "kjZvpZJx828vBNqiIKRDLc8=", "Vc9pN1Wj9GlhjlxTBJriJn0=", "Io06TyK1MTqYp9PVUa+fRTI=", "u0vPx7uPY8+O3F9CrvAY4OM=", "zAmcv8yZppx39dDE+8Vlg6w=", "lNo4ypT7xziiKlpxTU4Lt1w=", "45hrsuPtAmtbA9X3GHt21BM=", "el6eOnrXUJ5NeFlg5yTxccI=", "DRzNQg3Blc20UdbmshGMEo0=", "6bGSA+luXpLRcUifr1iuZ7s=", "nvPBe554m8EoWMcZ+m3TBPQ=", "BzU08wdCyTQ+I0uOBTJUoSU=", "cHdni3BUDGfHCsQIUAcpwmo=", "KKTD/ig2bcMS1U695oxH9po=", "X+aQhl8gqJDr/ME7s7k6ldU=", "xiBlDsYa+mX9h02sTOa9MAQ=", "sWI2drEMPzYErsIqGdPAU0s=", "dpsw5HbeODBKJETbPe1hWPk=", "AdljnAHI/WOzDctdaNgcO7Y=", "mB+WFJjyr5aldkfKl4ebnmc=", "713FbO/kasVcX8hMwrLm/Sg=", "t45hGbeGC2GJgEL5dDmIydg=", "wMwyYcCQzjJwqc1/IQz1qpc=", "WQrH6VmqnMdm0kHo3lNyD0Y=", "LkiUkS68WZSf+85ui2YPbAk=", "BZry1gXPLvJFOcA01Z9s11Q=", "ctihrnLZ66G8EE+ygKoRtBs=", "6x5UJuvjuVSqa8Mlf/WWEco=", "nFwHXpz1fAdTQkyjKsDrcoU=", "xI+jK8SXHaOGncYWnEuFRnU=", "s83wU7OB2PB/tEmQyX74JTo=", "KgsF2yq7igVpz8UHNiF/gOs=", "XUlWo12tT1aQ5kqBYxQC46Q=", "mrBQMZp/SFDebMxwRyqj6BY=", "7fIDSe1pjQMnRUP2Eh/ei1k=", "dDT2wXRT3/YxPs9h7UBZLog=", "A3aluQNFGqXIF0DnuHUkTcc=", "W6UBzFsnewEdyMpSDv5KeTc=", "LOdStCwxvlLk4UXUW8s3Gng=", "tSGnPLUL7KfymslDpJSwv6k=", "wmP0RMIdKfQLs0bF8aHN3OY=", "Js6rBSay4qtuk9i87OjvqdA=", "UYz4fVGkJ/iXulc6ud2Syp8=", "yEoN9ciedQ2BwdutRoIVb04=", "vwhejb+IsF546FQrE7doDAE=", "59v6+Ofq0fqtN96epTwGOPE=", "kJmpgJD8FKlUHlEY8Al7W74=", "CV9cCAnGRlxCZd2PD1b8/m8=", "fh0PcH7Qgw+7TFIJWmOBnSA=", "ueQJ4rkChAn1xtT4fl0glpI=", "zqZams4UQVoM71t+K2hd9d0=", "V2CvElcuE68alNfp1DfaUAw=", "ICL8aiA41vzjvVhvgQKnM0M=", "ePFYH3hat1g2YtLaN4nJB7M=", "D7MLZw9McgvPS11cYry0ZPw=", "lnX+75Z2IP7ZMNHLneMzwS0=", "4Tetl+Fg5a0gGV5NyNZOomI=", "QzJAbUM1q0ATcPA5p3F3K0E=", "NHATFTQjbhPqWX+/8kQKSA4=", "rbbmna0ZPOb8IvMoDRuN7d8=", "2vS15doP+bUFC3yuWC7wjpA=", "gicRkIJtmBHQ1PYb7qWeumA=", "9WVC6PV7XUIp/Xmdu5Dj2S8=", "bKO3YGxBD7c/hvUKRM9kfP4=", "G+HkGBtXyuTGr3qMEfoZH7E=", "3BjiityFzeKIJfx9NcS4FAM=", "q1qx8quTCLFxDHP7YPHFd0w=", "MpxEejKpWkRnd/9sn65C0p0=", "Rd4XAkW/nxeeXnDqyps/sdI=", "HQ2zdx3d/rNLgfpffBBRhSI=", "ak/gD2rLO+CyqHXZKSUs5m0=", "84kVh/PxaRWk0/lO1nqrQ7w=", "hMtG/4TnrEZd+nbIg0/WIPM=", "YGYZvmBIZxk42uixngb0VcU=", "FyRKxhdeokrB82c3yzOJNoo=", "juK/To5k8L/XiOugNGwOk1s=", "+aDsNvlyNewuoWQmYVlz8BQ=", "oXNIQ6EQVEj7fu6T19IdxOQ=", "1jEbO9YGkRsCV2EVgudgp6s=", "T/fus088w+4ULO2CfbjnAno=", "OLW9yzgqBr3tBWIEKI2aYTU=", "/0y7Wf/4Abujj+T1DLM7aoc=", "iA7oIYjuxOhapmtzWYZGCcg=", "EcgdqRHUlh1M3efkptnBrBk=", "ZopO0WbCU0619Ghi8+y8z1Y=", "PlnqpD6gMupgK+LXRWfS+6Y=", "SRu53Em297mZAm1REFKvmOk=", "0N1MVNCMpUyPeeHG7w0oPTg=", "p58fLKeaYB92UG5AujhVXnc=", "ideLvYkmOYvpq6AuMV5aMn4=", "/pXYxf4w/NgQgi+oZGsnUTE=", "Z1MtTWcKri0G+aM/mzSg9OA=", "EBF+NRAca37/0Cy5zgHdl68=", "SMLaQEh+CtoqD6YMeIqzo18=", "P4CJOD9oz4nTJimKLb/OwBA=", "pkZ8sKZSnXzFXaUd0uBJZcE=", "0QQvyNFEWC88dCqbh9U0Bo4=", "Fv0pWhaWXyly/qxqo+uVDTw=", "Yb96ImGAmnqL1yPs9t7obnM=", "+HmPqvi6yI+drK97CYFvy6I=", "jzvc0o+sDdxkhSD9XLQSqO0=", "1+h4p9fObHixWqpI6j98nB0=", "oKor36DYqStIcyXOvwoB/1I=", "OWzeVzni+95eCKlZQFWGWoM=", "Ti6NL070Po2nISbfFWD7Ocw=", "qoPSbqpb9dLCAbimCCnZTPo=", "3cGBFt1NMIE7KDcgXRykL7U=", "RAd0nkR3YnQtU7u3okMjimQ=", "M0Un5jNhpyfUejQx93Ze6Ss=", "a5aDk2sDxoMBpb6EQf0w3ds=", "HNTQ6xwVA9D4jDECFMhNvpQ=", "hRIlY4UvUSXu972V65fKG0U=", "8lB2G/I5lHYX3jITvqK3eAo=", "NalwiTXrk3BZVLTimpwWc7g=", "Qusj8UL9ViOgfTtkz6lrEPc=", "2y3WedvHBNa2BrfzMPbstSY=", "rG+FAazRwYVPLzh1ZcOR1mk=", "9LwhdPSzoCGa8LLA00j/4pk=", "g/5yDIOlZXJj2T1Ghn2CgdY=", "GjiHhBqfN4d1orHReSIFJAc=", "bXrU/G2J8tSMiz5XLBd4R0g=", "z385Bs/cvDm/4pAjQ7BBzms=", "uD1qfrjKeWpGyx+lFoU8rSQ=", "Ifuf9iHwK59QsJMy6dq7CPU=", "VrnMjlbm7sypmRy0vO/Ga7o=", "Dmpo+w6Ej2h8RpYBCmSoX0o=", "eSg7g3mSSjuFbxmHX1HVPAU=", "4O7OC+CoGM6TFJUQoA5SmdQ=", "l6ydc5e+3Z1qPRqW9Tsv+ps=", "UFWb4VBs2pskt5xn0QWO8Sk=", "JxfImSd6H8jdnhPhhDDzkmY=", "vtE9Eb5ATT3L5Z92e290N7c=", "yZNuaclWiG4yzBDwLloJVPg=", "kUDKHJE06crnE5pFmNFnYAg=", "5gKZZOYiLJkeOhXDzeQaA0c=", "f8Rs7H8YfmwIQZlUMrudppY=", "CIY/lAgOuz/xaBbSZ47gxdk=", "7Ctg1eyhcGCUSIiresfCsO8=", "m2kzrZu3tTNtYQctL/K/06A=", "Aq/GJQKN58Z7Gou60K04dnE=", "de2VXXWbIpWCMwQ8hZhFFT4=", "LT4xKC35QzFX7I6JMxMrIc4=", "WnxiUFrvhmKuxQEPZiZWQoE=", "w7qX2MPV1Je4vo2YmXnR51A=", "tPjEoLTDEcRBlwIezEyshB8=", "cwHCMnMRFsIPHYTv6HINj60=", "BEORSgQH05H2NAtpvUdw7OI=", "nYVkwp09gWTgT4f+Qhj3STM=", "6sc3uuorRDcZZgh4Fy2KKnw=", "shSTz7JJJZPMuYLNoabkHow=", "xVbAt8Vf4MA1kA1L9JOZfcM=", "XJA1P1xlsjUj64HcC8we2BI=", "K9JmRytzd2bawg5aXvlju10=" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP17', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP18', 'Object', {
	init: function () {
		this._resource = [ "7/u3cZWvx9fw3ElSrUsgQ9mS", "7/u3cZWvx9fw3ElSrUsgQ9mS", "w+tz4jdDk7P9pZKkR5ZAhq85", "LBDEk6LsVGQNedv26t1gxXar", "m8vm2W6GO3vnVzlVjjGAEUNy", "dDBRqPsp/KwXi3AHI3qgUprg", "WCCVO1nFqMga8qvxyafAl+xL", "t9siSsxqbx/qLuKjZOzg1DXZ", "K4vRr9wRdvbTrnKqAWIdIobk", "xHBm3km+sSEjcjv4rCk9YV92", "6GCiTetS5UUuC+AORvRdpCnd", "B5sVPH79IpLe16lc67995/BP", "sEA3drKXTY00+Uv/j1OdM8WW", "X7uAByc4ilrEJQKtIhi9cBwE", "c6tElIXU3j7JXNlbyMXdtWqv", "nFDz5RB7Gek5gJAJZY799rM9", "Vgu/Q6Ui7PG7QeRJAsQ6RBHV", "ufAIMjCNKyZLna0br48aB8hH", "leDMoZJhf0JG5HbtRVJ6wr7s", "eht70AfOuJW2OD+/6BlagWd+", "zcBZmsuk14pcFt0cjPW6VVKn", "Ijvu614LEF2sypROIb6aFos1", "DisqePznRDmhs0+4y2P60/2e", "4dCdCWlIg+5RbwbqZijakCQM", "fYBu7Hkzmgdo75bjA6YnZpcx", "knvZneycXdCYM9+xru0HJU6j", "vmsdDk5wCbSVSgRHRDBn4DgI", "UZCqf9vfzmNllk0V6XtHo+Ga", "5kuINRe1oXyPuK+2jZend9RD", "CbA/RIIaZqt/ZObkINyHNA3R", "JaD71yD2Ms9yHT0SygHn8Xt6", "yltMprVZ9RiCwXRAZ0rHsqLo", "rBZjhldExf9rgtWSBJV0iCK3", "Q+3U98LrAiibXpzAqd5Uy/sl", "b/0QZGAHVkyWJ0c2QwM0Do2O", "gAanFfWokZtm+w5k7kgUTVQc", "N92FXznC/oSM1ezHiqT0mWHF", "2CYyLqxtOVN8CaWVJ+/U2rhX", "9Db2vQ6BbTdxcH5jzTK0H878", "G81BzJsuquCBrDcxYHmUXBdu", "h52yKYtVswm4LKc4BfdpqqRT", "aGYFWB76dN5I8O5qqLxJ6X3B", "RHbBy7wWILpFiTWcQmEpLAtq", "q412uim55221VXzO7yoJb9L4", "HFZU8OXTiHJfe55ti8bpu+ch", "863jgXB8T6Wvp9c/Jo3J+D6z", "370nEtKQG8Gi3gzJzFCpPUgY", "MEaQY0c/3BZSAkWbYRuJfpGK", "+h3cxfJmKQ7QwzHbBlFOzDNi", "FeZrtGfJ7tkgH3iJqxpuj+rw", "OfavJ8Ulur0tZqN/QccOSpxb", "1g0YVlCKfWrduuot7IwuCUXJ", "YdY6HJzgEnU3lAiOiGDO3XAQ", "ji2NbQlP1aLHSEHcJSvunqmC", "oj1J/qujgcbKMZoqz/aOW98p", "Tcb+jz4MRhE67dN4Yr2uGAa7", "0ZYNai53X/gDbUNxBzNT7rWG", "Pm26G7vYmC/zsQojqnhzrWwU", "En1+iBk0zEv+yNHVQKUTaBq/", "/YbJ+YybC5wOFJiH7e4zK8Mt", "Sl3rs0DxZIPkOnokiQLT//b0", "paZcwtVeo1QU5jN2JEnzvC9m", "ibaYUXey9zAZn+iAzpSTeVnN", "Zk0vIOIdMOfpQ6HSY9+zOoBf", "RSzGEa6Il+PWGbc5CDfoDURz", "qtdxYDsnUDQmxf5rpXzITp3h", "hse185nLBFArvCWdT6Goi+tK", "aTwCggxkw4fbYGzP4uqIyDLY", "3ucgyMAOrJgxTo5shgZoHAcB", "MRyXuVWha0/Bksc+K01IX96T", "HQxTKvdNPyvM6xzIwZAomqg4", "8vfkW2Li+Pw8N1WabNsI2XGq", "bqcXvnKZ4RUFt8WTCVX1L8KX", "gVygz+c2JsL1a4zBpB7VbBsF", "rUxkXEXacqb4Elc3TsO1qW2u", "QrfTLdB1tXEIzh5l44iV6rQ8", "9WzxZxwf2m7i4PzGh2R1PoHl", "GpdGFomwHbkSPLWUKi9VfVh3", "NoeChStcSd0fRW5iwPI1uC7c", "2Xw19L7zjgrvmScwbbkV+/dO", "Eyd5UguqexJtWFNwCvPSSVWm", "/NzOI54FvMWdhBoip7jyCow0", "0MwKsDzp6KGQ/cHUTWWSz/qf", "Pze9walGL3ZgIYiG4C6yjCMN", "iOyfi2UsQGmKD2olhMJSWBbU", "Zxco+vCDh7560yN3KYlyG89G", "SwfsaVJv09p3qviBw1QS3rnt", "pPxbGMfAFA2HdrHTbh8ynWB/", "OKyo/de7DeS+9iHaC5HPa9NC", "11cfjEIUyjNOKmiIptrvKArQ", "+0fbH+D4nldDU7N+TAeP7Xx7", "FLxsbnVXWYCzj/os4UyvrqXp", "o2dOJLk9Np9ZoRiPhaBPepAw", "TJz5VSyS8UipfVHdKOtvOUmi", "YIw9xo5+pSykBIorwjYP/D8J", "j3eKtxvRYvtU2MN5b30vv+ab", "6Tqll/nMUhy9m2KrDKKchWbE", "BsES5mxjlctNRyv5oem8xr9W", "KtHWdc6Pwa9APvAPSzTcA8n9", "xSphBFsgBniw4rld5n/8QBBv", "cvFDTpdKaWdazFv+gpMclCW2", "nQr0PwLlrrCqEBKsL9g81/wk", "sRowrKAJ+tSnaclaxQVcEoqP", "XuGH3TWmPQNXtYAIaE58UVMd", "wrF0OCXdJOpuNRABDcCBp+Ag", "LUrDSbBy4z2e6VlToIuh5Dmy", "AVoH2hKet1mTkIKlSlbBIU8Z", "7qGwq4cxcI5jTMv35x3hYpaL", "WXqS4UtbH5GJYilUg/EBtqNS", "toElkN702EZ5vmAGLroh9XrA", "mpHhA3wYjCJ0x7vwxGdBMAxr", "dWpWcum3S/WEG/KiaSxhc9X5", "vzEa1Fzuvu0G2obiDmamwXcR", "UMqtpclBeTr2Bs+woy2Ggq6D", "fNppNmutLV77fxRGSfDmR9go", "kyHeR/4C6okLo10U5LvGBAG6", "JPr8DTJohZbhjb+3gFcm0DRj", "ywFLfKfHQkERUfblLRwGk+3x", "5xGP7wUrFiUcKC0Tx8FmVpta", "COo4npCE0fLs9GRBaopGFULI", "lLrLe4D/yBvVdPRIDwS74/H1", "e0F8ChVQD8wlqL0aok+boChn", "V1G4mbe8W6go0WbsSJL7ZV7M", "uKoP6CITnH/YDS++5dnbJode", "D3Etou5582AyI80dgTU78rKH", "4Iqa03vWNLfC/4RPLH4bsWsV", "zJpeQNk6YNPPhl+5xqN7dB2+", "I2HpMUyVpwQ/Whbra+hbN8Qs", "iliRIkENM9uxMnNyEG7NGojm", "ZaMmU9Si9AxB7jogvSXtWVF0", "SbPiwHZOoGhMl+HWV/iNnCff", "pkhVsePhZ7+8S6iE+rOt3/5N", "EZN3+y+LCKBWZUonnl9NC8uU", "/mjAirokz3emuQN1MxRtSBIG", "0ngEGRjImxOrwNiD2ckNjWSt", "PYOzaI1nXMRbHJHRdIItzr0/", "odNAjZ0cRS1inAHYEQzQOA4C", "Tij3/AizgvqSQEiKvEfwe9eQ", "Yjgzb6pf1p6fOZN8VpqQvqE7", "jcOEHj/wEUlv5dou+9Gw/Xip", "OhimVPOaflaFyziNnz1QKU1w", "1eMRJWY1uYF1F3HfMnZwapTi", "+fPVtsTZ7eV4bqop2KsQr+JJ", "Fghix1F2KjKIsuN7deAw7Dvb", "3FMuYeQv3yoKc5c7Eqr3Xpkz", "M6iZEHGAGP36r95pv+HXHUCh", "H7hdg9NsTJn31gWfVTy32DYK", "8EPq8kbDi04HCkzN+HeXm++Y", "R5jIuIqp5FHtJK5unJt3T9pB", "qGN/yR8GI4Yd+Oc8MdBXDAPT", "hHO7Wr3qd+IQgTzK2w03yXV4", "a4gMKyhFsDXgXXWYdkYXiqzq", "99j/zjg+qdzZ3eWRE8jqfB/X", "GCNIv62RbgspAazDvoPKP8ZF", "NDOMLA99Om8keHc1VF6q+rDu", "28g7XZrS/bjUpD5n+RWKuWl8", "bBMZF1a4kqc+itzEnflqbVyl", "g+iuZsMXVXDOVpWWMLJKLoU3", "r/hq9WH7ARTDL05g2m8q6/Oc", "QAPdhPRUxsMz8wcydyQKqCoO", "Jk7ypBZJ9iTasKbgFPu5kqpR", "ybVF1YPmMfMqbO+yubCZ0XPD", "5aWBRiEKZZcnFTREU235FAVo", "Cl42N7SlokDXyX0W/ibZV9z6", "vYUUfXjPzV8955+1mso5g+kj", "Un6jDO1gCojNO9bnN4EZwDCx", "fm5nn0+MXuzAQg0R3Vx5BUYa", "kZXQ7tojmTswnkRDcBdZRp+I", "DcUjC8pYgNIJHtRKFZmksCy1", "4j6Uel/3RwX5wp0YuNKE8/Un", "zi5Q6f0bE2H0u0buUg/kNoOM", "IdXnmGi01LYEZw+8/0TEdVoe", "lg7F0qTeu6nuSe0fm6gkoW/H", "efVyozFxfH4elaRNNuME4rZV", "VeW2MJOdKBoT7H+73D5kJ8D+", "uh4BQQYy783jMDbpcXVEZBls", "cEVN57NrGtVh8UKpFj+D1ruE", "n776libE3QKRLQv7u3SjlWIW", "s64+BYQoiWacVNANUanDUBS9", "XFWJdBGHTrFsiJlf/OLjE80v", "646rPt3tIa6Gpnv8mA4Dx/j2", "BHUcT0hC5nl2ejKuNUUjhCFk", "KGXY3Oqush17A+lY35hDQVfP", "x55vrX8BdcqL36AKctNjAo5d", "W86cSG96bCOyXzADF12e9D1g", "tDUrOfrVq/RCg3lRuha+t+Ty", "mCXvqlg5/5BP+qKnUMvecpJZ", "d95Y282WOEe/Juv1/YD+MUvL", "wAV6kQH8V1hVCAlWmWwe5X4S", "L/7N4JRTkI+l1EAENCc+pqeA", "A+4Jcza/xOuorZvy3vpeY9Er", "7BW+AqMQAzxYcdKgc7F+IAi5", "z3RXM++FpDhnK8RLGFklF8yV", "II/gQnoqY++X940ZtRIFVBUH", "DJ8k0djGN4uajlbvX89lkWOs", "42SToE1p8FxqUh+98oRF0ro+", "VL+x6oEDn0OAfP0elmilBo/n", "u0QGmxSsWJRwoLRMOyOFRVZ1", "l1TCCLZADPB92W+60f7lgCDe", "eK91eSPvyyeNBSbofLXFw/lM", "5P+GnDOU0s60hbbhGTs4NUpx", "CwQx7aY7FRlEWf+ztHAYdpPj", "JxT1fgTXQX1JICRFXq14s+VI", "yO9CD5F4hqq5/G0X8+ZY8Dza", "fzRgRV0S6bVT0o+0lwq4JAkD", "kM/XNMi9LmKjDsbmOkGYZ9CR", "vN8Tp2pRegaudx0Q0Jz4oqY6", "UySk1v/+vdFeq1RCfdfY4X+o", "mX/ocEqnSMncaiACGp0fU91A", "doRfAd8Ijx4stmlQt9Y/EATS", "WpSbkn3k23ohz7KmXQtf1XJ5", "tW8s4+hLHK3RE/v08EB/lqvr", "ArQOqSQhc7I7PRlXlKyfQp4y", "7U+52LGOtGXL4VAFOee/AUeg", "wV99SxNi4AHGmIvz0zrfxDEL", "LqTKOobNJ9Y2RMKhfnH/h+iZ", "svQ535a2Pj8PxFKoG/8CcVuk", "XQ+OrgMZ+ej/GBv6trQiMoI2", "cR9KPaH1rYzyYcAMXGlC9/Sd", "nuT9TDRaalsCvYle8SJitC0P", "KT/fBvgwBUTok2v9lc6CYBjW", "xsRod22fwpMYTyKvOIWiI8FE", "6tSs5M9zlvcVNvlZ0ljC5rfv", "BS8blVrcUSDl6rALfxPipW59", "Y2I0tbjBYccMqRHZHMxRn+4i", "jJmDxC1uphD8dViLsYdx3Dew", "oIlHV4+C8nTxDIN9W1oRGUEb", "T3LwJhotNaMB0Mov9hExWpiJ", "+KnSbNZHWrzr/iiMkv3Rjq1Q", "F1JlHUPonWsbImHeP7bxzXTC", "O0KhjuEEyQ8WW7oo1WuRCAJp", "1LkW/3SrDtjmh/N6eCCxS9v7", "SOnlGmTQFzHfB2NzHa5MvWjG", "pxJSa/F/0OYv2yohsOVs/rFU", "iwKW+FOThIIiovHXWjgMO8f/", "ZPkhicY8Q1XSfriF93MseB5t", "0yIDwwpWLEo4UFomk5/MrCu0", "PNm0sp/5653IjBN0PtTs7/Im", "EMlwIT0Vv/nF9ciC1AmMKoSN", "/zLHUKi6eC41KYHQeUKsaV0f", "NWmL9h3jjTa36PWQHghr2//3", "2pI8h4hMSuFHNLzCs0NLmCZl", "9oL4FCqgHoVKTWc0WZ4rXVDO", "GXlPZb8P2VK6kS5m9NULHolc", "rqJtL3Nltk1Qv8zFkDnryryF", "QVnaXubKcZqgY4WXPXLLiWUX", "bUkezUQmJf6tGl5h16+rTBO8", "grKpvNGJ4ildxhczeuSLD8ou", "HuJaWcHy+8BkRoc6H2p2+XkT", "8RntKFRdPBeUms5osiFWuqCB", "3Qkpu/axaHOZ4xWeWPw2f9Yq", "MvKeymMer6RpP1zM9bcWPA+4", "hSm8gK90wLuDEb5vkVv26Dph", "atIL8TrbB2xzzfc9PBDWq+Pz", "RsLPYpg3Uwh+tCzL1s22bpVY", "qTl4Ew2YlN+OaGWZe4aWLUzK" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP18', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP20', 'Object', {
	init: function () {
		this._resource = [ "mLnwBW9jBtxwlkUkuxbkxnl5pa4=", "mLnwBW9jBtxwlkUkuxbkxnl5pa4=", "LW/9Ct7GDKXgMYpIayzVkfLyV0E=", "tdYND7GlCnmQp89s0DoxV4uL8u8=", "Wt7nFKGRGFfdYgmQ1li3P/n5roI=", "wmcXEc7yHout9Ey0bU5T+YCACyw=", "d7EaHn9XFPI9U4PYvXRirgsL+cM=", "7wjqGxA0Ei5Nxcb8BmKGaHJyXG0=", "tKHTKF8/MK6nxBI9sbBzfu/vQRk=", "LBgjLTBcNnLXUlcZCqaXuJaW5Lc=", "mc4uIoH5PAtH9Zh12pym7x0dFlg=", "AXfeJ+6aOtc3Y91RYYpCKWRks/Y=", "7n80PP6uKPl6phutZ+jEQRYW75s=", "dsbEOZHNLiUKMF6J3P4gh29vSjU=", "wxDJNiBoJFyal5HlDMQR0OTkuNo=", "W6k5M08LIoDqAdTBt9L1Fp2dHXQ=", "dV+7UL5+YEFTlSR6f33m/MPDgjI=", "7eZLVdEdZp0jA2FexGsCOrq6J5w=", "WDBGWmC4bOSzpK4yFFEzbTEx1XM=", "wIm2Xw/bajjDMusWr0fXq0hIcN0=", "L4FcRB/veBaO9y3qqSVRwzo6LLA=", "tzisQXCMfsr+YWjOEjO1BUNDiR4=", "Au6hTsEpdLNuxqeiwgmEUsjIe/E=", "mldRS65Kcm8eUOKGeR9glLGx3l8=", "wf5oeOFBUO/0UTZHzs2Vgiwswys=", "WUeYfY4iVjOEx3NjddtxRFVVZoU=", "7JGVcj+HXEoUYLwPpeFAE97elGo=", "dChld1DkWpZk9vkrHvek1aenMcQ=", "myCPbEDQSLgpMz/XGJUivdXVbak=", "A5l/aS+zTmRZpXrzo4PGe6ysyAc=", "tk9yZp4WRB3JArWfc7n3LCcnOug=", "LvaCY/F1QsG5lPC7yK8T6l5en0Y=", "6r5roGH8wIKmN0j0/vrR5ZubGWQ=", "cgebpQ6fxl7WoQ3QRew1I+LivMo=", "x9GWqr86zCdGBsK8ldYEdGlpTiU=", "X2hmr9BZyvs2kIeYLsDgshAQ64s=", "sGCMtMBt2NV7VUFkKKJm2mJit+Y=", "KNl8sa8O3gkLwwRAk7SCHBsbEkg=", "nQ9xvh6r1HCbZMssQ46zS5CQ4Kc=", "BbaBu3HI0qzr8o4I+JhXjenpRQk=", "Xh+4iD7D8CwB81rJT0qim3R0WH0=", "xqZIjVGg9vBxZR/t9FxGXQ0N/dM=", "c3BFguAF/InhwtCBJGZ3CoaGDzw=", "68m1h49m+lWRVJWln3CTzP//qpI=", "BMFfnJ9S6HvckVNZmRIVpI2N9v8=", "nHivmfAx7qesBxZ9IgTxYvT0U1E=", "Ka6ilkGU5N48oNkR8j7ANX9/ob4=", "sRdSky734gJMNpw1SSgk8wYGBBA=", "n+HQ8N+CoMP1omyOgYc3GVhYm1Y=", "B1gg9bDhph+FNCmqOpHT3yEhPvg=", "so4t+gFErGYVk+bG6qviiKqqzBc=", "Kjfd/24nqrplBaPiUb0GTtPTabk=", "xT835H4TuJQowGUeV9+AJqGhNdQ=", "XYbH4RFwvkhYViA67Mlk4NjYkHo=", "6FDK7qDVtDHI8e9WPPNVt1NTYpU=", "cOk668+2su24Z6pyh+WxcSoqxzs=", "K0AD2IC9kG1SZn6zMDdEZ7e32k8=", "s/nz3e/elrEi8DuXiyGgoc7Of+E=", "Bi/+0l57nMiyV/T7WxuR9kVFjQ4=", "npYO1zEYmhTCwbHf4A11MDw8KKA=", "cZ7kzCEsiDqPBHcj5m/zWE5OdM0=", "6ScUyU5Pjub/kjIHXXkXnjc30WM=", "XPEZxv/qhJ9vNf1rjUMmyby8I4w=", "xEjpw5CJgkMfo7hPNlXCD8XFhiI=", "yWHWXcLlnRlRbpD14em/1ysrMsg=", "UdgmWK2Gm8Uh+NXRWv9bEVJSl2Y=", "5A4rVxwjkbyxXxq9isVqRtnZZYk=", "fLfbUnNAl2DByV+ZMdOOgKCgwCc=", "k78xSWN0hU6MDJllN7EI6NLSnEo=", "CwbBTAwXg5L8mtxBjKfsLqurOeQ=", "vtDMQ72yietsPRMtXJ3deSAgyws=", "Jmk8RtLRjzccq1YJ54s5v1lZbqU=", "fcAFdZ3arbf2qoLIUFnMqcTEc9E=", "5Xn1cPK5q2uGPMfs608ob7291n8=", "UK/4f0McoRIWmwiAO3UZODY2JJA=", "yBYIeix/p85mDU2kgGP9/k9PgT4=", "Jx7iYTxLteAryItYhgF7lj093VM=", "v6cSZFMoszxbXs58PRefUEREeP0=", "CnEfa+KNuUXL+QEQ7S2uB8/PihI=", "ksjvbo3uv5m7b0Q0VjtKwba2L7w=", "vD5tDXyb/VgC+7SPnpRZK+josPo=", "JIedCBP4+4RybfGrJYK97ZGRFVQ=", "kVGQB6Jd8f3iyj7H9biMuhoa57s=", "CehgAs0+9yGSXHvjTq5ofGNjQhU=", "5uCKGd0K5Q/fmb0fSMzuFBERHng=", "fll6HLJp49OvD/g789oK0mhou9Y=", "y493EwPM6ao/qDdXI+A7hePjSTk=", "UzaHFmyv73ZPPnJzmPbfQ5qa7Jc=", "CJ++JSOkzfalP6ayLyQqVQcH8eM=", "kCZOIEzHyyrVqeOWlDLOk35+VE0=", "JfBDL/1iwVNFDiz6RAj/xPX1pqI=", "vUmzKpIBx481mGne/x4bAoyMAww=", "UkFZMYI11aF4Xa8i+Xydav7+X2E=", "yvipNO1W030Iy+oGQmp5rIeH+s8=", "fy6kO1zz2QSYbCVqklBI+wwMCCA=", "55dUPjOQ39jo+mBOKUasPXV1rY4=", "I9+9/aMZXZv3WdgBHxNuMrCwK6w=", "u2ZN+Mx6W0eHz50lpAWK9MnJjgI=", "DrBA933fUT4XaFJJdD+7o0JCfO0=", "lgmw8hK8V+Jn/hdtzylfZTs72UM=", "eQFa6QKIRcwqO9GRyUvZDUlJhS4=", "4biq7G3rQxBarZS1cl09yzAwIIA=", "VG6n49xOSWnKClvZomcMnLu70m8=", "zNdX5rMtT7W6nB79GXHoWsLCd8E=", "l35u1fwmbTVQnco8rqMdTF9farU=", "D8ee0JNFa+kgC48YFbX5iiYmzxs=", "uhGT3yLgYZCwrEB0xY/I3a2tPfQ=", "Iqhj2k2DZ0zAOgVQfpksG9TUmFo=", "zaCJwV23dWKN/8OsePuqc6amxDc=", "VRl5xDLUc779aYaIw+1Otd/fYZk=", "4M90y4NxecdtzknkE9d/4lRUk3Y=", "eHaEzuwSfxsdWAzAqMGbJC0tNtg=", "VoAGrR1nPdqkzPx7YG6IznNzqZ4=", "zjn2qHIEOwbUWrlf23hsCAoKDDA=", "e+/7p8OhMX9E/XYzC0JdX4GB/t8=", "41YLoqzCN6M0azMXsFS5mfj4W3E=", "DF7hubz2JY15rvXrtjY/8YqKBxw=", "lOcRvNOVI1EJOLDPDSDbN/PzorI=", "ITEcs2IwKSiZn3+j3RrqYHh4UF0=", "uYjstg1TL/TpCTqHZgwOpgEB9fM=", "4iHVhUJYDXQDCO5G0d77sJyc6Ic=", "epglgC07C6hznqtiasgfduXlTSk=", "z04oj5yeAdHjOWQOuvIuIW5uv8Y=", "V/fYivP9Bw2TryEqAeTK5xcXGmg=", "uP8ykePJFSPeaufWB4ZMj2VlRgU=", "IEbClIyqE/+u/KLyvJCoSRwc46s=", "lZDPmz0PGYY+W22ebKqZHpeXEUQ=", "DSk/nlJsH1pOzSi617x92O7utOo=", "j8KxupnXJzKi3D33389js1ZWZI0=", "F3tBv/a0Ie7SSnjTZNmHdS8vwSM=", "oq1MsEcRK5dC7be/tOO2IqSkM8w=", "OhS8tShyLUsye/KbD/VS5N3dlmI=", "1RxWrjhGP2V/vjRnCZfUjK+vyg8=", "TaWmq1clObkPKHFDsoEwStbWb6E=", "+HOrpOaAM8Cfj74vYrsBHV1dnU4=", "YMpboYnjNRzvGfsL2a3l2yQkOOA=", "O2NiksboF5wFGC/Kbn8Qzbm5JZQ=", "o9qSl6mLEUB1jmru1Wn0C8DAgDo=", "FgyfmBguGznlKaWCBVPFXEtLctU=", "jrVvnXdNHeWVv+CmvkUhmjIy13s=", "Yb2Fhmd5D8vYeiZauCen8kBAixY=", "+QR1gwgaCReo7GN+AzFDNDk5Lrg=", "TNJ4jLm/A244S6wS0wtyY7Ky3Fc=", "1GuIidbcBbJI3ek2aB2WpcvLefk=", "+p0K6iepR3PxSRmNoLKFT5WV5r8=", "YiT670jKQa+B31ypG6RhiezsQxE=", "1/L34PlvS9YReJPFy55Q3mdnsf4=", "T0sH5ZYMTQph7tbhcIi0GB4eFFA=", "oEPt/oY4XyQsKxAdduoycGxsSD0=", "OPod++lbWfhcvVU5zfzWthUV7ZM=", "jSwQ9Fj+U4HMGppVHcbn4Z6eH3w=", "FZXg8TedVV28jN9xptADJ+fnutI=", "TjzZwniWd91WjQuwEQL2MXp6p6Y=", "1oUpxxf1cQEmG06UqhQS9wMDAgg=", "Y1MkyKZQe3i2vIH4ei4joIiI8Oc=", "++rUzckzfaTGKsTcwTjHZvHxVUk=", "FOI+1tkHb4qL7wIgx1pBDoODCSQ=", "jFvO07ZkaVb7eUcEfEylyPr6rIo=", "OY3D3AfBYy9r3ohorHaUn3FxXmU=", "oTQz2WiiZfMbSM1MF2BwWQgI+8s=", "ZXzaGvgr57AE63UDITWyVs3Nfek=", "/cUqH5dI4Wx0fTAnmiNWkLS02Ec=", "SBMnECbt6xXk2v9LShlnxz8/Kqg=", "0KrXFUmO7cmUTLpv8Q+DAUZGjwY=", "P6I9Dlm6/+fZiXyT920FaTQ002s=", "pxvNCzbZ+TupHzm3THvhr01NdsU=", "Es3ABId880I5uPbbnEHQ+MbGhCo=", "inQwAegf9Z5JLrP/J1c0Pr+/IYQ=", "0d0JMqcU1x6jL2c+kIXBKCIiPPA=", "SWT5N8h30cLTuSIaK5Ml7ltbmV4=", "/LL0OHnS27tDHu12+6kUudDQa7E=", "ZAsEPRax3WcziKhSQL/wf6mpzh8=", "iwPuJgaFz0l+TW6uRt12F9vbknI=", "E7oeI2nmyZUO2yuK/cuS0aKiN9w=", "pmwTLNhDw+yefOTmLfGjhikpxTM=", "PtXjKbcgxTDu6qHCludHQFBQYJ0=", "ECNhSkZVh/FXflF5XkhUqg4O/9s=", "iJqRTyk2gS0n6BRd5V6wbHd3WnU=", "PUycQJiTi1S3T9sxNWSBO/z8qJo=", "pfVsRffwjYjH2Z4VjnJl/YWFDTQ=", "Sv2GXufEn6aKHFjpiBDjlff3UVk=", "0kR2W4inmXr6ih3NMwYHU46O9Pc=", "Z5J7VDkCkwNqLdKh4zw2BAUFBhg=", "/yuLUVZhld8au5eFWCrSwnx8o7Y=", "pIKyYhlqt1/wukNE7/gn1OHhvsI=", "PDtCZ3YJsYOALAZgVO7DEpiYG2w=", "ie1PaMesu/oQi8kMhNTyRRMT6YM=", "EVS/bajPvSZgHYwoP8IWg2pqTC0=", "/lxVdrj7rwgt2ErUOaCQ6xgYEEA=", "ZuWlc9eYqdRdTg/wgrZ0LWFhte4=", "0zOofGY9o63N6cCcUoxFeurqRwE=", "S4pYeQlepXG9f4W46ZqhvJOT4q8=", "RqNn51syuivzsq0CPibcZH19VkU=", "3hqX4jRRvPeDJOgmhTA4ogQE8+s=", "a8ya7YX0to4TgydKVQoJ9Y+PAQQ=", "83Vq6OqXsFJjFWJu7hztM/b2pKo=", "HH2A8/qjonwu0KSS6H5rW4SE+Mc=", "hMRw9pXApKBeRuG2U2iPnf39XWk=", "MRJ9+SRlrtnO4S7ag1K+ynZ2r4Y=", "qauN/EsGqAW+d2v+OERaDA8PCig=", "8gK0zwQNioVUdr8/j5avGpKSF1w=", "artEymtujFkk4PobNIBL3OvrsvI=", "321JxdrLhiC0RzV35Lp6i2BgQB0=", "R9S5wLWogPzE0XBTX6yeTRkZ5bM=", "qNxT26WcktKJFLavWc4YJWtrud4=", "MGWj3sr/lA75gvOL4tj84xISHHA=", "hbOu0XtanndpJTznMuLNtJmZ7p8=", "HQpe1BQ5mKsZs3nDifQpcuDgSzE=", "M/zct+VM2mqgJ4l4QVs6mL6+1Hc=", "q0Ussoov3LbQscxc+k3eXsfHcdk=", "HpMhvTuK1s9AFgMwKnfvCUxMgzY=", "hirRuFTp0BMwgEYUkWELzzU1Jpg=", "aSI7o0Tdwj19RYDolwONp0dHevU=", "8ZvLpiu+xOEN08XMLBVpYT4+31s=", "RE3GqZobzpiddAqg/C9YNrW1LbQ=", "3PQ2rPV4yETt4k+ERzm88MzMiBo=", "h10Pn7pz6sQH45tF8OtJ5lFRlW4=", "H+T/mtUQ7Bh3dd5hS/2tICgoMMA=", "qjLylWS15mHn0hENm8ecd6Ojwi8=", "MosCkAvW4L2XRFQpINF4sdraZ4E=", "3YPoixvi8pPagZLVJrP+2aioO+w=", "RToYjnSB9E+qF9fxnaUaH9HRnkI=", "8OwVgcUk/jY6sBidTZ8rSFpabK0=", "aFXlhKpH+OpKJl259onPjiMjyQM=", "rB0MRzrOeqlVheX2wNwNgebmTyE=", "NKT8QlWtfHUlE6DSe8rpR5+f6o8=", "gXLxTeQIdgy1tG++q/DYEBQUGGA=", "GcsBSItrcNDFIiqaEOY81m1tvc4=", "9sPrU5tfYv6I5+xmFoS6vh8f4aM=", "bnobVvQ8ZCL4calCrZJeeGZmRA0=", "26wWWUWZblto1mYufahvL+3ttuI=", "QxXmXCr6aIcYQCMKxr6L6ZSUE0w=", "GLzfb2XxSgfyQffLcWx+/wkJDjg=", "gAUvagqSTNuC17LvynqaOXBwq5Y=", "NdMiZbs3RqIScH2DGkCrbvv7WXk=", "rWrSYNRUQH5i5jinoVZPqIKC/Nc=", "QmI4e8RgUlAvI/5bpzTJwPDwoLo=", "2tvIfqsDVIxftbt/HCItBomJBRQ=", "bw3FcRqmXvXPEnQTzBgcUQIC9/s=", "97Q1dHXFWCm/hDE3dw74l3t7UlU=", "2UK3F4SwGugGEMGMv6HrfSUlzRM=", "QftHEuvTHDR2hoSoBLcPu1xcaL0=", "9C1KHVp2Fk3mIUvE1I0+7NfXmlI=", "bJS6GDUVEJGWtw7gb5vaKq6uP/w=", "g5xQAyUhAr/bcsgcaflcQtzcY5E=", "GyWgBkpCBGOr5I040u+4hKWlxj8=", "rvOtCfvnDho7Q0JUAtWJ0y4uNNA=", "NkpdDJSECMZL1QdwucNtFVdXkX4=", "beNkP9uPKkah1NOxDhGYA8rKjAo=", "9VqUOrTsLJrRQpaVtQd8xbOzKaQ=", "QIyZNQVJJuNB5Vn5ZT1Nkjg420s=", "2DVpMGoqID8xcxzd3iupVEFBfuU=", "Nz2DK3oeMhF8ttoh2EkvPDMzIog=", "r4RzLhV9NM0MIJ8FY1/L+kpKhyY=", "GlJ+IaTYPrSch1Bps2X6rcHBdck=", "guuOJMu7OGjsERVNCHMea7i40Gc=" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP20', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP22', 'Object', {
	init: function () {
		this._resource = [ "WbODsLb0E71FKByJHXtD/Vba5hqR9Q==", "WbODsLb0E71FKByJHXtD/Vba5hqR9Q==", "snsbfXH1JmeKUDgPOvaG56yp0TQ/9w==", "68iYzccBNdrPeCSGJ43FGvpzNy6uAg==", "efY2+uL3TM4JoHAedPER00VPv2h+8w==", "IEW1SlQDX3NMiGyXaYpSLhOVWXLvBg==", "y40th5MCaqmD8EgRTgeXNOnmblxBBA==", "kj6uNyX2eRTG2FSYU3zUyb88iEbQ8Q==", "8vFs6dnzmIESXeA86P8iu4qeY9D8+w==", "q0LvWW8HizxXdfy19YRhRtxEhcptDg==", "QIp3lKgGvuaYDdgz0gmkXCY3suTDDA==", "GTn0JB7yrVvdJcS6z3LnoXDtVP5S+Q==", "iwdaEzsE1E8b/ZAinA4zaM/R3LiCCA==", "0rTZo43wx/Je1YyrgXVwlZkLOqIT/Q==", "OXxBbkrx8iiRragtpvi1j2N4DYy9/w==", "YM/C3vwF4ZXUhbSku4P2cjWi65YsCg==", "+f/Yz6/7LR8kut14zeNEawkhxr3l6w==", "oExbfxkPPqJhksHx0JgHll/7IKd0Hg==", "S4TDst4OC3iu6uV39xXCjKWIF4naHA==", "EjdAAmj6GMXrwvn+6m6BcfNS8ZNL6Q==", "gAnuNU0MYdEtGq1muRJVuExuedWbGA==", "2bpthfv4cmxoMrHvpGkWRRq0n88K7Q==", "MnL1SDz5R7anSpVpg+TTX+DHqOGk7w==", "a8F2+IoNVAviYongnp+QorYdTvs1Gg==", "Cw60JnYItZ425z1EJRxm0IO/pW0ZEA==", "Ur03lsD8piNzzyHNOGclLdVlQ3eI5Q==", "uXWvWwf9k/m8twVLH+rgNy8WdFkm5w==", "4MYs67EJgET5nxnCApGjynnMkkO3Eg==", "cviC3JT/+VA/R01aUe13A8bwGgVn4w==", "K0sBbCIL6u16b1HTTJY0/pAq/B/2Fg==", "wIOZoeUK3ze1F3VVaxvx5GpZyzFYFA==", "mTAaEVP+zIrwP2ncdmCyGTyDLSvJ4Q==", "7+Otg0PrWj5Iaafwh9uI1hJCkWfXyw==", "tlAuM/UfSYMNQbt5mqDLK0SYd31GPg==", "XZi2/jIefFnCOZ//vS0OMb7rQFPoPA==", "BCs1ToTqb+SHEYN2oFZNzOgxpkl5yQ==", "lhWbeaEcFvBBydfu8yqZBVcNLg+pOA==", "z6YYyRfoBU0E4ctn7lHa+AHXyBU4zQ==", "JG6ABNDpMJfLme/hydwf4vuk/zuWzw==", "fd0DtGYdIyqOsfNo1KdcH61+GSEHOg==", "HRLBapoYwr9aNEfMbySqbZjc8rcrMA==", "RKFC2izs0QIfHFtFcl/pkM4GFK26xQ==", "r2naF+vt5NjQZH/DVdIsijR1I4MUxw==", "9tpZp10Z92WVTGNKSKlvd2KvxZmFMg==", "ZOT3kHjvjnFTlDfSG9W7vt2TTd9Vww==", "PVd0IM4bncwWvCtbBq74Q4tJq8XENg==", "1p/s7QkaqBbZxA/dISM9WXE6nOtqNA==", "jyxvXb/uu6uc7BNUPFh+pCfgevH7wQ==", "Fhx1TOwQdyFs03qISjjMvRtjV9oyIA==", "T6/2/FrkZJwp+2YBV0OPQE25scCj1Q==", "pGduMZ3lUUbmg0KHcM5KWrfKhu4N1w==", "/dTtgSsRQvujq14ObbUJp+EQYPScIg==", "b+pDtg7nO+9lcwqWPsndbl4s6LJM0w==", "NlnABrgTKFIgWxYfI7Kekwj2DqjdJg==", "3ZFYy38SHYjvIzKZBD9bifKFOYZzJA==", "hCLbe8nmDjWqCy4QGUQYdKRf35zi0Q==", "5O0ZpTXj76B+jpq0osfuBpH9NArO2w==", "vV6aFYMX/B07poY9v7yt+8cn0hBfLg==", "VpYC2EQWycf03qK7mDFo4T1U5T7xLA==", "DyWBaPLi2nqx9r4yhUorHGuOAyRg2Q==", "nRsvX9cUo253Luqq1jb/1dSyi2KwKA==", "xKis72HgsNMyBvYjy028KIJobXgh3Q==", "L2A0IqbhhQn9ftKl7MB5MngbWlaP3w==", "dtO3khAVlrS4Vs4s8bs6zy7BvEweKg==", "w9tHG4bLtHyQ0lP9E6sNsSSEP86ziw==", "mmjEqzA/p8HV+k90DtBOTHJe2dQifg==", "caBcZvc+khsagmvyKV2LVogt7vqMfA==", "KBPf1kHKgaZfqnd7NCbIq973COAdiQ==", "ui1x4WQ8+LKZciPjZ1ocYmHLgKbNeA==", "457yUdLI6w/cWj9qeiFfnzcRZrxcjQ==", "CFZqnBXJ3tUTIhvsXayahc1iUZLyjw==", "UeXpLKM9zWhWCgdlQNfZeJu4t4hjeg==", "MSor8l84LP2Cj7PB+1QvCq4aXB5PcA==", "aJmoQunMP0DHp69I5i9s9/jAugTehQ==", "g1Ewjy7NCpoI34vOwaKp7QKzjSpwhw==", "2uKzP5g5GSdN95dH3NnqEFRpazDhcg==", "SNwdCL3PYDOLL8Pfj6U+2etV43Yxgw==", "EW+euAs7c47OB99Wkt59JL2PBWygdg==", "+qcGdcw6RlQBf/vQtVO4Pkf8MkIOdA==", "oxSFxXrOVelEV+dZqCj7wxEm1FifgQ==", "OiSf1CkwmWO0aI6F3khJ2i2l+XNWYA==", "Y5ccZJ/Eit7xQJIMwzMKJ3t/H2nHlQ==", "iF+EqVjFvwQ+OLaK5L7PPYEMKEdplw==", "0ewHGe4xrLl7EKoD+cWMwNfWzl34Yg==", "Q9KpLsvH1a29yP6bqrlYCWjqRhsokw==", "GmEqnn0zxhD44OISt8Ib9D4woAG5Zg==", "8amyU7oy88o3mMaUkE/e7sRDly8XZA==", "qBox4wzG4HdysNodjTSdE5KZcTWGkQ==", "yNXzPfDDAeKmNW65NrdrYac7mqOqmw==", "kWZwjUY3El/jHXIwK8wonPHhfLk7bg==", "eq7oQIE2J4UsZVa2DEHthguSS5eVbA==", "Ix1r8DfCNDhpTUo/ETque11IrY0EmQ==", "sSPFxxI0TSyvlR6nQkZ6suJ0JcvUaA==", "6JBGd6TAXpHqvQIuXz05T7Suw9FFnQ==", "A1jeumPBa0slxSaoeLD8VU7d9P/rnw==", "WutdCtU1ePZg7TohZcu/qBgHEuV6ag==", "LDjqmMUg7kLYu/QNlHCFZzbGrqlkQA==", "dYtpKHPU/f+dk+iEiQvGmmAcSLP1tQ==", "nkPx5bTVyCVS68wCroYDgJpvf51btw==", "x/ByVQIh25gXw9CLs/1Afcy1mYfKQg==", "Vc7cYifXoozRG4QT4IGUtHOJEcEasw==", "DH1f0pEjsTGUM5ia/frXSSVT99uLRg==", "57XHH1YihOtbS7wc2ncSU98gwPUlRA==", "vgZEr+DWl1YeY6CVxwxRron6Ju+0sQ==", "3smGcRzTdsPK5hQxfI+n3LxYzXmYuw==", "h3oFwaonZX6Pzgi4YfTkIeqCK2MJTg==", "bLKdDG0mUKRAtiw+RnkhOxDxHE2nTA==", "NQEevNvSQxkFnjC3WwJixkYr+lc2uQ==", "pz+wi/4kOg3DRmQvCH62D/kXchHmSA==", "/owzO0jQKbCGbnimFQX18q/NlAt3vQ==", "FUSr9o/RHGpJFlwgMogw6FW+oyXZvw==", "TPcoRjklD9cMPkCpL/NzFQNkRT9ISg==", "1ccyV2rbw138ASl1WZPBDD/naBSBqw==", "jHSx59wv0OC5KTX8ROiC8Wk9jg4QXg==", "Z7wpKhsu5Tp2URF6Y2VH65NOuSC+XA==", "Pg+qmq3a9oczeQ3zfh4EFsWUXzovqQ==", "rDEErYgsj5P1oVlrLWLQ33qo13z/WA==", "9YKHHT7YnC6wiUXiMBmTIixyMWZurQ==", "Hkof0PnZqfR/8WFkF5RWONYBBkjArw==", "R/mcYE8tukk62X3tCu8VxYDb4FJRWg==", "JzZevrMoW9zuXMlJsWzjt7V5C8R9UA==", "foXdDgXcSGGrdNXArBegSuOj7d7spQ==", "lU1Fw8LdfbtkDPFGi5plUBnQ2vBCpw==", "zP7Gc3QpbgYhJO3PluEmrU8KPOrTUg==", "XsBoRFHfFxLn/LlXxZ3yZPA2tKwDow==", "B3Pr9OcrBK+i1KXe2OaxmabsUraSVg==", "7LtzOSAqMXVtrIFY/2t0g1yfZZg8VA==", "tQjwiZbeIsgohJ3R4hA3fgpFg4KtoQ==", "m6uONhGLdfg9uabnJksaf0gVfoF7Cw==", "whgNhqd/ZkV4kbpuOzBZgh7PmJvq/g==", "KdCVS2B+U5+36Z7oHL2cmOS8r7VE/A==", "cGMW+9aKQCLywYJhAcbfZbJmSa/VCQ==", "4l24zPN8OTY0Gdb5UroLrA1awekF+A==", "u+47fEWIKotxMcpwT8FIUVuAJ/OUDQ==", "UCajsYKJH1G+Se72aEyNS6HzEN06Dw==", "CZUgATR9DOz7YfJ/dTfOtvcp9ser+g==", "aVri38h47Xkv5EbbzrQ4xMKLHVGH8A==", "MOlhb36M/sRqzFpS0897OZRR+0sWBQ==", "2yH5ormNyx6ltH7U9EK+I24izGW4Bw==", "gpJ6Eg952KPgnGJd6Tn93jj4Kn8p8g==", "EKzUJSqPobcmRDbFukUpF4fEojn5Aw==", "SR9XlZx7sgpjbCpMpz5q6tEeRCNo9g==", "otfPWFt6h9CsFA7KgLOv8Cttcw3G9A==", "+2RM6O2OlG3pPBJDncjsDX23lRdXAQ==", "YlRW+b5wWOcZA3uf66heFEE0uDye4A==", "O+fVSQiES1pcK2cW9tMd6RfuXiYPFQ==", "0C9NhM+FfoCTU0OQ0V7Y8+2daQihFw==", "iZzONHlxbT3We18ZzCWbDrtHjxIw4g==", "G6JgA1yHFCkQowuBn1lPxwR7B1TgEw==", "QhHjs+pzB5RVixcIgiIMOlKh4U5x5g==", "qdl7fi1yMk6a8zOOpa/JIKjS1mDf5A==", "8Gr4zpuGIfPf2y8HuNSK3f4IMHpOEQ==", "kKU6EGeDwGYLXpujA1d8r8uq2+xiGw==", "yRa5oNF309tOdocqHiw/Up1wPfbz7g==", "It4hbRZ25gGBDqOsOaH6SGcDCthd7A==", "e22i3aCC9bzEJr8lJNq5tTHZ7MLMGQ==", "6VMM6oV0jKgC/uu9d6ZtfI7lZIQc6A==", "sOCPWjOAnxVH1vc0at0ugdg/gp6NHQ==", "WygXl/SBqs+IrtOyTVDrmyJMtbAjHw==", "ApuUJ0J1uXLNhs87UCuoZnSWU6qy6g==", "dEgjtVJgL8Z10AEXoZCSqVpX7+aswA==", "LfugBeSUPHsw+B2evOvRVAyNCfw9NQ==", "xjM4yCOVCaH/gDkYm2YUTvb+PtKTNw==", "n4C7eJVhGhy6qCWRhh1Xs6Ak2MgCwg==", "Db4VT7CXYwh8cHEJ1WGDeh8YUI7SMw==", "VA2W/wZjcLU5WG2AyBrAh0nCtpRDxg==", "v8UOMsFiRW/2IEkG75cFnbOxgbrtxA==", "5naNgneWVtKzCFWP8uxGYOVrZ6B8MQ==", "hrlPXIuTt0dnjeErSW+wEtDJjDZQOw==", "3wrM7D1npPoipf2iVBTz74YTaizBzg==", "NMJUIfpmkSDt3dkkc5k29XxgXQJvzA==", "bXHXkUySgp2o9cWtbuJ1CCq6uxj+OQ==", "/095pmlk+4luLZE1PZ6hwZWGM14uyA==", "pvz6Ft+Q6DQrBY28IOXiPMNc1US/PQ==", "TTRi2xiR3e7kfak6B2gnJjkv4moRPw==", "FIfha65lzlOhVbWzGhNk22/1BHCAyg==", "jbf7ev2bAtlRatxvbHPWwlN2KVtJKw==", "1AR4yktvEWQUQsDmcQiVPwWsz0HY3g==", "P8zgB4xuJL7bOuRgVoVQJf/f+G923A==", "Zn9jtzqaNwOeEvjpS/4T2KkFHnXnKQ==", "9EHNgB9sThdYyqxxGILHERY5ljM32A==", "rfJOMKmYXaod4rD4BfmE7EDjcCmmLQ==", "RjrW/W6ZaHDSmpR+InRB9rqQRwcILw==", "H4lVTdhte82Xsoj3Pw8CC+xKoR2Z2g==", "f0aXkyRomlhDNzxThIz0ednoSou10A==", "JvUUI5KcieUGHyDamfe3hI8yrJEkJQ==", "zT2M7lWdvD/JZwRcvnpynnVBm7+KJw==", "lI4PXuNpr4KMTxjVowExYyObfaUb0g==", "BrChacaf1pZKl0xN8H3lqpyn9ePLIw==", "XwMi2XBrxSsPv1DE7QamV8p9E/la1g==", "tMu6FLdq8PHAx3RCyotjTTAOJNf01A==", "7Xg5pAGe40yF72jL1/AgsGbUws1lIQ==", "WHDJLZdAwYSta/UaNeAXzmyRQU/IgA==", "AcNKnSG00jnoQ+mTKJtUMzpLp1VZdQ==", "6gvSUOa15+MnO80VDxaRKcA4kHv3dw==", "s7hR4FBB9F5iE9GcEm3S1JbidmFmgg==", "IYb/13W3jUqky4UEQREGHSne/ie2cw==", "eDV8Z8NDnvfh45mNXGpF4H8EGD0nhg==", "k/3kqgRCqy0um70Le+eA+oV3LxOJhA==", "yk5nGrK2uJBrs6GCZpzDB9OtyQkYcQ==", "qoGlxE6zWQW/NhUm3R81deYPIp80ew==", "8zImdPhHSrj6HgmvwGR2iLDVxIWljg==", "GPq+uT9Gf2I1Zi0p5+mzkkqm86sLjA==", "QUk9CYmybN9wTjGg+pLwbxx8FbGaeQ==", "03eTPqxEFcu2lmU4qe4kpqNAnfdKiA==", "isQQjhqwBnbzvnmxtJVnW/Wae+3bfQ==", "YQyIQ92xM6w8xl03kxiiQQ/pTMN1fw==", "OL8L82tFIBF57kG+jmPhvFkzqtnkig==", "oY8R4ji77JuJ0Shi+ANTpWWwh/Itaw==", "+DySUo5P/ybM+TTr5XgQWDNqYei8ng==", "E/QKn0lOyvwDgRBtwvXVQskZVsYSnA==", "SkeJL/+62UFGqQzk346Wv5/DsNyDaQ==", "2HknGNpMoFWAcVh8jPJCdiD/OJpTmA==", "gcqkqGy4s+jFWUT1kYkBi3Yl3oDCbQ==", "agI8Zau5hjIKIWBztgTEkYxW6a5sbw==", "M7G/1R1NlY9PCXz6q3+HbNqMD7T9mg==", "U359C+FIdBqbjMheEPxxHu8u5CLRkA==", "Cs3+u1e8Z6fepNTXDYcy47n0AjhAZQ==", "4QVmdpC9Un0R3PBRKgr3+UOHNRbuZw==", "uLblxiZJQcBU9OzYN3G0BBVd0wx/kg==", "KohL8QO/ONSSLLhAZA1gzaphW0qvYw==", "czvIQbVLK2nXBKTJeXYjMPy7vVA+lg==", "mPNQjHJKHrMYfIBPXvvmKgbIin6QlA==", "wUDTPMS+DQ5dVJzGQ4Cl11ASbGQBYQ==", "t5NkrtSrm7rlAlLqsjufGH7T0CgfSw==", "7iDnHmJfiAegKk5jr0Dc5SgJNjKOvg==", "Beh/06Vevd1vUmrliM0Z/9J6ARwgvA==", "XFv8YxOqrmAqenZslbZaAoSg5waxSQ==", "zmVSVDZc13TsoiL0xsqOyzucb0BhuA==", "l9bR5ICoxMmpij5927HNNm1GiVrwTQ==", "fB5JKUep8RNm8hr7/DwILJc1vnReTw==", "Ja3KmfFd4q4j2gZy4UdL0cHvWG7Pug==", "RWIIRw1YAzv3X7LWWsS9o/RNs/jjsA==", "HNGL97usEIayd65fR7/+XqKXVeJyRQ==", "9xkTOnytJVx9D4rZYDI7RFjkYszcRw==", "rqqQispZNuE4J5ZQfUl4uQ4+hNZNsg==", "PJQ+ve+vT/X+/8LILjWscLECDJCdQw==", "ZSe9DVlbXEi7195BM07vjefY6ooMtg==", "ju8lwJ5aaZJ0r/rHFMMqlx2r3aSitA==", "11ymcCiuei8xh+ZOCbhpaktxO74zQQ==", "Tmy8YXtQtqXBuI+Sf9jbc3fyFpX6oA==", "F98/0c2kpRiEkJMbYqOYjiEo8I9rVQ==", "/BenHAqlkMJL6LedRS5dlNtbx6HFVw==", "paQkrLxRg38OwKsUWFUeaY2BIbtUog==", "N5qKm5mn+mvIGP+MCynKoDK9qf2EUw==", "bikJKy9T6daNMOMFFlKJXWRnT+cVpg==", "heGR5uhS3AxCSMeDMd9MR54UeMm7pA==", "3FISVl6mz7EHYNsKLKQPusjOntMqUQ==", "vJ3QiKKjLiTT5W+ulyf5yP1sdUUGWw==", "5S5TOBRXPZmWzXMnily6Nau2k1+Xrg==", "DubL9dNWCENZtVehrdF/L1HFpHE5rA==", "V1VIRWWiG/4cnUsosKo80gcfQmuoWQ==", "xWvmckBUYuraRR+w49boG7gjyi14qA==", "nNhlwvagcVefbQM5/q2r5u75LDfpXQ==", "dxD9DzGhRI1QFSe/2SBu/BSKGxlHXw==", "LqN+v4dVVzAVPTs2xFstAUJQ/QPWqg==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP22', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP24', 'Object', {
	init: function () {
		this._resource = [ "enapRrLt2GZzluVJgkg9K84B7fd/2ZB1", "enapRrLt2GZzluVJgkg9K84B7fd/2ZB1", "9OxPjHnHrczmMdeSGZB6VoECx/P+rz3q", "jprmyssqdaqVpzLbm9hHfU8DKgSBdq2f", "9cWeBfKTR4XRYrM5Mj30rB8Ek/vhQ3rJ", "j7M3Q0B+n+Oi9FZwsHXJh9EFfgyemuq8", "ASnRiYtU6kk3U2SrK62O+p4GVAgf7Ecj", "e194zzm5Mi9ExYHiqeWz0VAHuf9gNddW", "95chCvk7jhe/xHtyZHr1RT4IO+vfhvSP", "jeGITEvWVnHMUp475jLIbvAJ1hygX2T6", "A3tuhoD8I9tZ9azgfeqPE78K/BghKcll", "eQ3HwDIR+70qY0mp/6KyOHELEe9e8FkQ", "AlK/DwuoyZJupshLVkcB6SEMqBA+xY5G", "eCQWSblFEfQdMC0C1A88wu8NRedBHB4z", "9r7wg3JvZF6Ilx/ZT9d7v6AOb+PAarOs", "jMhZxcCCvDj7AfqQzZ9GlG4PghS/syPZ", "8zNCFO92AS5jlfbkyPT3inwQdsujEfUD", "iUXrUl2b2UgQAxOtSrzKobIRmzzcyGV2", "B98NmJaxrOKFpCF20WSN3P0SsThdvsjp", "famk3iRcdIT2MsQ/Uyyw9zMTXM8iZ1ic", "BvbcER3lRquy90Xd+skDJmMU5TBCUo/K", "fIB1V68Ins3BYaCUeIE+Da0VCMc9ix+/", "8hqTnWQi62dUxpJP41l5cOIWIsO8/bIg", "iGw629bPMwEnUHcGYRFEWywXzzTDJCJV", "BKRjHhZNjzncUY2WrI4Cz0IYTSB8lwGM", "ftLKWKSgV1+vx2jfLsY/5IwZoNcDTpH5", "8Egskm+KIvU6YFoEtR54mcMaitOCODxm", "ij6F1N1n+pNJ9r9NN1ZFsg0bZyT94awT", "8WH9G+TeyLwNMz6vnrP2Y10c3tud1HtF", "ixdUXVYzENp+pdvmHPvLSJMdMyziDesw", "BY2yl50ZZXDrAuk9hyOMNdweGShje0av", "f/sb0S/0vRaYlAx0BWuxHhIf9N8cotba", "+2aEKMPsAlzGN/HVjfXzCfgg7ItbIvcG", "gRAtbnEB2jq1oRScD73OIjYhAXwk+2dz", "D4rLpLorr5AgBiZHlGWJX3kiK3iljcrs", "dfxi4gjGd/ZTkMMOFi20dLcjxo/aVFqZ", "DqMaLTF/RdkXVULsv8gHpeckf3C6YY3P", "dNWza4OSnb9kw6elPYA6jiklkofFuB26", "+k9VoUi46BXxZJV+plh982YmuINEzrAl", "gDn85/pVMHOC8nA3JBBA2KgnVXQ7FyBQ", "DPGlIjrXjEt584qn6Y8GTMYo12CEpAOJ", "docMZIg6VC0KZW/ua8c7ZwgpOpf7fZP8", "+B3qrkMQIYefwl018B98GkcqEJN6Cz5j", "gmtD6PH9+eHsVLh8cldBMYkr/WQF0q4W", "+TQ7J8hEy86okTme27Ly4NksRJtl53lA", "g0KSYXqpE6jbB9zXWfrPyxctqWwaPuk1", "Ddh0q7GDZgJOoO4MwiKItlgug2ibSESq", "d67d7QNuvmQ9NgtFQGq1nZYvbp/kkdTf", "CFXGPCyaA3KlogcxRQEEg4QwmkD4MwIF", "ciNvep532xTWNOJ4x0k5qEoxd7eH6pJw", "/LmJsFVdrr5Dk9CjXJF+1QUyXbMGnD/v", "hs8g9uewdtgwBTXq3tlD/sszsER5Ra+a", "/ZBYOd4JRPd0wLQIdzzwL5s0CbsZcHjM", "h+bxf2zknJEHVlFB9XTNBFU15Exmqei5", "CXwXtafO6TuS8WOabqyKeRo2zkjn30Um", "cwq+8xUjMV3hZ4bT7OS3UtQ3I7+YBtVT", "/8LnNtWhjWUaZnxDIXvxxro4oasntfaK", "hbROcGdMVQNp8JkKozPM7XQ5TFxYbGb/", "Cy6ouqxmIKn8V6vROOuLkDs6ZljZGstg", "cVgB/B6L+M+PwU6YuqO2u/U7i6+mw1sV", "Cgd5MycyyuDLBM96E0YFaqU8MlDG9oxD", "cHHQdZXfEoa4kiozkQ44QWs936e5Lxw2", "/us2v171ZywtNRjoCtZ/PCQ+9aM4WbGp", "hJ2f+ewYv0peo/2hiJ5CF+o/GFRHgCHc", "68wVUJvFBLiRbv+3B/f7Eu1AxQu2RPMM", "kbq8Fiko3N7i+Br+hb/GOSNBKPzJnWN5", "HyBa3OICqXR3XyglHmeBRGxCAvhI687m", "ZVbzmlDvcRIEyc1snC+8b6JD7w83Ml6T", "HgmLVWlWQz1ADEyONcoPvvJEVvBXB4nF", "ZH8iE9u7m1szmqnHt4IylTxFuwco3hmw", "6uXE2RCR7vGmPZscLFp16HNGkQOpqLQv", "kJNtn6J8NpfVq35VrhJIw71HfPTWcSRa", "HFs0WmL+iq8uqoTFY40OV9NI/uBpwgeD", "Zi2dHNATUsldPGGM4cUzfB1JExcWG5f2", "6Ld71hs5J2PIm1NXeh10AVJKOROXbTpp", "ksHSkKnU/wW7DbYe+FVJKpxL1OTotKoc", "6Z6qX5BtzSr/yDf8UbD6+8xMbRuIgX1K", "k+gDGSKAFUyMXtK10/jH0AJNgOz3WO0/", "HXLl0+mqYOYZ+eBuSCCArU1Oquh2LkCg", "ZwRMlVtHuIBqbwUnymi9hoNPRx8J99DV", "GP9XRHSzBZby+wlTzwMMmJFQs8AVVQYP", "Yon+AsZe3fCBbewaTUsxs19RXjdqjJZ6", "7BMYyA10qFoUyt7B1pN2zhBSdDPr+jvl", "lmWxjr+ZcDxnXDuIVNtL5d5TmcSUI6uQ", "7TrJQYYgQhMjmbpq/T74NI5UIDv0FnzG", "l0xgBzTNmnVQD18jf3bFH0BVzcyLz+yz", "GdaGzf/n79/FqG345K6CYg9W58gKuUEs", "Y6Avi00KN7m2PoixZua/ScFXCj91YNFZ", "72h2To2Ii4FNP3Ihq3n53a9YiCvK0/KA", "lR7fCD9lU+c+qZdoKTHE9mFZZdy1CmL1", "G4Q5wvRPJk2rDqWzsumDiy5aT9g0fM9q", "YfKQhEai/ivYmED6MKG+oOBboi9LpV8f", "Gq3oS38bzAScXcEYmUQNcbBcG9ArkIhJ", "YNtBDc32FGLvyyRRGwwwWn5d9idUSRg8", "7kGnxwbcYch6bBaKgNR3JzFe3CPVP7Wj", "lDcOgbQxua4J+vPDApxKDP9fMdSq5iXW", "EKqReFgpBuRXWQ5iigIIGxVgKYDtZgQK", "atw4PurE3oIkz+srCEo1MNthxHeSv5R/", "5Ebe9CHuqyixaNnwk5JyTZRi7nMTyTng", "njB3spMDc07C/jy5EdpPZlpjA4RsEKmV", "5W8Pfaq6QWGGO71buD/8twpkunsMJX7D", "nxmmOxhXmQf1rVgSOnfBnMRlV4xz/O62", "EYNA8dN97K1gCmrJoa+G4YtmfYjyikMp", "a/Xpt2GQNMsTnI+AI+e7ykVnkH+NU9Nc", "5z2wcqESiPPonXUQ7nj9XitoEmsy4PCF", "nUsZNBP/UJWbC5BZbDDAdeVp/5xNOWDw", "E9H//tjVJT8OrKKC9+iHCKpq1ZjMT81v", "aadWuGo4/Vl9OkfLdaC6I2RrOG+zll0a", "Evgud1OBz3Y5/8Yp3EUJ8jRsgZDTo4pM", "aI6HMeFsFxBKaSNgXg002fptbGeseho5", "5hRh+ypGYrrfzhG7xdVzpLVuRmMtDLem", "nGLIvZirutysWPTyR51Oj3tvq5RS1SfT", "45nTbLdfB8o0zPiGQvb/kWlwX0tOd/EJ", "me96KgWy36xHWh3PwL7CuqdxsrwxrmF8", "F3Wc4M6YqgbS/S8UW2aFx+hymLiw2Mzj", "bQM1pnx1cmCha8pd2S647CZzdU/PAVyW", "FlxNaUXMQE/lrku/cMsLPXZ0zLCvNIvA", "bCrkL/chmCmWOK728oM2Frh1IUfQ7Ru1", "4rAC5TwL7YMDn5wtaVtxa/d2C0NRm7Yq", "mMaro47mNeVwCXlk6xNMQDl35rQuQiZf", "FA7yZk5kid2LCIP0JowK1Fd4ZKCR8QWG", "bnhbIPyJUbv4nma9pMQ3/5l5iVfuKJXz", "4OK96jejJBFtOVRmPxxwgtZ6o1NvXjhs", "mpQUrIVO/Hcer7EvvVRNqRh7TqQQh6gZ", "4ctsY7z3zlhaajDNFLH+eEh891twsn9P", "m73FJQ4aFj4p/NWElvnDU4Z9GqwPa+86", "FScj78UwY5S8W+dfDSGELsl+MKiOHUKl", "b1GKqXfdu/LPzQIWj2m5BQd/3V/xxNLQ", "y4UqoCuXCG0/3ONzDvPrJMeAlxZxiPsY", "sfOD5pl60AtMSgY6jLvWDwmBeuEOUWtt", "P2llLFJQpaHZ7TThF2ORckaCUOWPJ8by", "RR/MauC9fceqe9GolSusWYiDvRLw/laH", "PkC0pdkET+juvlBKPM4fiNiEBO2Qy4HR", "RDYd42vpl46dKLUDvoYioxaF6RrvEhGk", "yqz7KaDD4iQIj4fYJV5l3lmGwx5uZLw7", "sNpSbxIuOkJ7GWKRpxZY9ZeHLukRvSxO", "PBILqtKshnqAGJgBaokeYfmIrP2uDg+X", "RmSi7GBBXhzzjn1I6MEjSjeJQQrR15/i", "yP5EJqtrK7ZmKU+TcxlkN3iKaw5QoTJ9", "sojtYBmG89AVv6ra8VFZHLaLhvkveKII", "ydeVryA/wf9Reis4WLTqzeaMPwZPTXVe", "s6E86ZLSGZki7M5x2vzX5iiN0vEwlOUr", "PTvaI1n4bDO3S/yqQSSQm2eO+PWx4ki0", "R01zZesVtFXE3Rnjw2ytsKmPFQLOO9jB", "OLZotMThCUNcSRWXxgccrruQ4d3SmQ4b", "QsDB8nYM0SUv3/DeRE8hhXWRDCqtQJ5u", "zFonOL0mpI+6eMIF35dm+DqSJi4sNjPx", "tiyOfg/LfOnJ7idMXd9b0/STy9lT76OE", "zXP2sTZyTsaNK6au9DroAqSUciYz2nTS", "twVf94SflqD+vUPndnLVKWqVn9FMA+Sn", "OZ+5PU+14wprGnE87aqSVCWWtdXNdUk4", "Q+kQe/1YO2wYjJR1b+Kvf+uXWCKyrNlN", "zyFJvj3ah1TjjW7lon3p64WY2jYNH/qU", "tVfg+I83XzKQG4usIDXUwEuZN8Fyxmrh", "O80GMkQdKpgFvLl3u+2TvQSaHcXzsMd+", "QbuvdPbw8v52Klw+OaWulsqb8DKMaVcL", "OuTXu89JwNEy793ckEAdR5qcSc3sXIBd", "QJJ+/X2kGLdBeTiVEgggbFSdpDqThRAo", "zgiYN7aObR3U3gpOidBnERuejj4S8723", "tH4xcQRjtXunSO8HC5haOtWfY8ltKi3C", "MOOuiOh7CjH56xKmgwYYLT+ge50qqgwe", "SpUHzlqW0leKfffvAU4lBvGhlmpVc5xr", "xA/hBJG8p/0f2sU0mpZie76ivG7UBTH0", "vnlIQiNRf5tsTCB9GN5fUHCjUZmr3KGB", "xSYwjRroTbQoiaGfsTvsgSCk6GbL6XbX", "v1CZy6gFldJbH0TWM3PRqu6lBZG0MOai", "Mcp/AWMv4HjOuHYNqKuW16GmL5U1Rks9", "S7zWR9HCOB69LpNEKuOr/G+nwmJKn9tI", "x3SPghFAhCZGL2nU53ztaAGoQHb1LPiR", "vQImxKOtXEA1uYydZTTQQ8+prYGK9Wjk", "M5jADmiHKeqgHr5G/uyXPoCqh4ULg8V7", "Se5pSNpq8YzTiFsPfKSqFU6ranJ0WlUO", "MrERh+PTw6OXTdrt1UEZxB6s040Ub4JY", "SMe4wVE+G8Xk2z+kVwkk79CtPnprthIt", "xl1eC5oUbm9xfA1/zNFjkp+uFH7qwL+y", "vCv3TSj5tgkC6ug2TpleuVGv+YmVGS/H", "w9DsnAcNCx+afuRCS/Lvp0OwDVaJu/kd", "uaZF2rXg03np6AELybrSjI2x4KH2Ymlo", "NzyjEH7KptN8TzPQUmKV8cKyyqV3FMT3", "TUoKVswnfrUP2daZ0Cqo2gyzJ1IIzVSC", "NhVymfWeTJpLHFd7ec8bC1y0nq1o+IPU", "TGPb30dzlPw4irIy+4cmIJK1c1oXIROh", "wvk9FYxZ4VatLYDpYF9hXd22WV6WV74+", "uI+UUz60OTDeu2Wg4hdcdhO3tKnpji5L", "NEfNlv42hQglup8wL4ga4n24Nr1WPQ2S", "TjFk0EzbXW5WLHp5rcAnybO520op5J3n", "wKuCGofxKMTDi0iiNhhgtPy68U6okjB4", "ut0rXDUc8KKwHa3rtFBdnzK7HLnXS6AN", "wYJTkwylwo302CwJHbXuTmK8pUa3fndb", "u/T61b5IGuuHTslAn/3TZay9SLHIp+cu", "NW4cH3Vib0ES6fubBCWUGOO+YrVJ0Uqx", "Txi1WcePtydhfx7Shm2pMy2/j0I2CNrE", "IEk/8LBSDNWushzECQQQNirAUh3HzAgU", "Wj+WtgK/1LPdJPmNi0wtHeTBv+q4FZhh", "1KVwfMmVoRlIg8tWEJRqYKvCle45YzX+", "rtPZOnt4eX87FS4fktxXS2XDeBlGuqWL", "1Yyh9ULBS1B/0K/9OznkmjXEweYmj3Ld", "r/oIs/AskzYMRkq0uXHZsfvFLBFZVuKo", "IWDueTsG5pyZ4XhvIqmezLTGBhXYIE83", "WxZHP4nrPvrqd50moOGj53rH6+Kn+d9C", "194e+klpgsIRdme2bX7lcxTIafYYSvyb", "rai3vPuEWqRi4IL/7zbYWNrJhAFnk2zu", "IzJRdjCuLw73R7AkdO6fJZXKrgXm5cFx", "WUT4MIJD92iE0VVt9qaiDlvLQ/KZPFEE", "IhuA/7v6xUfAFNSPX0MR3wvM+g35CYZS", "WG0puQkXHSGzgjHG3Qss9MXNF/qG0BYn", "1vfPc8I9aIsmJQMdRtNriYrOPf4Hpru4", "rIFmNXDQsO1Vs+ZUxJtWokTP0Al4fyvN", "03p95F8kDfvNJ+ogwfDnvFbQJNZk3f0X", "qQzUou3J1Z2+sQ9pQ7jal5jRySEbBG1i", "J5YyaCbjoDcrFj2y2GCd6tfS4yWacsD9", "XeCbLpQOeFFYgNj7WiigwRnTDtLlq1CI", "Jr/j4a23Sn4cRVkZ880TEEnUty2Fnofe", "XMlKpx9akhhv07xQcYUuO4fVWtr6Rxer", "0lOsbdRw57L6dI6L6l1pRsjWcN57Mbo0", "qCUFK2adP9SJ4mvCaBVUbQbXnSkE6CpB", "JO1c7qYfg+xy45FSpYoS+WjYHz27WwmY", "Xpv1qBTyW4oBdXQbJ8Iv0qbZ8srEgpnt", "0AETYt/YLiCU0kbAvBpor+na2M5F9DRy", "qne6JG019kbnRKOJPlJVhCfbNTk6LaQH", "0SjC61SMxGmjgSJrl7fmVXfcjMZaGHNR", "q15rreZhHA/QF8ciFf/bfrndYTElweMk", "JcSNZy1LaaVFsPX5jiecA/beSzWkt067", "X7IkIZ+mscM2JhCwDG+hKDjfpsLbbt7O", "2y+72HO+Dolohe0RhPHjP9Lgvpac7v8S", "oVkSnsFT1u8bEwhYBrneFBzhU2HjN29n", "L8P0VAp5o0WOtDqDnWGZaVPieWViQcL4", "VbVdEriUeyP9It/KHymkQp3jlJIdmFKN", "Luol3YEtSQy5514otswXk83kLW19rYXb", "VJyMmzPAkWrKcbthNIQquAPlwJoCdBWu", "2gZqUfjq5MBf1om6r1xtxUzm6p6DArgx", "oHDDF0oHPKYsQGzzLRRQ7oLnB2n82yhE", "LLia0oqFgJ7XQZZj4IsWeuzohX1DaAud", "Vs4zlDhoWPik13MqYsMrUSLpaIo8sZvo", "2FTVXvNCLVIxcEHx+RtsLG3qQo69xzZ3", "oiJ8GEGv9TRC5qS4e1NRB6Prr3nCHqYC", "2X0E13gWxxsGIyVa0rbi1vPsFoaiK3FU", "owutkcr7H311tcATUP7f/T3t+3Hd8uEh", "LZFLWwHRatfgEvLIyyaYgHLu0XVchEy+", "V+fiHbM8srGThBeBSW6lq7zvPIIjXdzL", "KBz5zJzID6cLEBv1TAUUta7wyF0//woR", "UmpQii4l18F4hv68zk0pnmDxJapAJppk", "3PC2QOUPomvtIcxnVZVu4y/yD67BUDf7", "poYfBlfieg2etyku191TyOHz4lm+iaeO", "3dlnyW5bSCLacqjMfjjgGbH0W6bevHDY", "p6/Oj9y2kESp5E2F/HDdMn/1tlGhZeCt", "KTUoRRec5e48Q39eZ6iaTzD2nFUgE00y", "U0OBA6VxPYhP1ZoX5eCnZP73caJfyt1H", "34vYxmXzgbC01GCHKH/h8JD487bgef6e", "pf1xgNceWdbHQoXOqjfc2175HkGfoG7r", "K2eXShw0LHxS5bcVMe+bphH6NEUe1sN0", "URE+DK7Z9Bohc1Jcs6emjd/72bJhD1MB", "Kk5Gw5dgxjVlttO+GkIVXI/8YE0BOoRX", "UDjvhSWNHlMWIDb3mAood0H9jbp+4xQi", "3qIJT+6na/mDhwQsA9JvCg7+p77/lbm9", "pNSgCVxKs5/wEeFlgZpSIcD/SkmATCnI" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP24', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP26', 'Object', {
	init: function () {
		this._resource = [ "9jO3BIhix5hNOM4YkSjRdekqh0RGkJJNK14=", "9jO3BIhix5hNOM4YkSjRdekqh0RGkJJNK14=", "8WZzCA3Eky2acIEwP1C/6s9UE4iMPTmaVrw=", "B1XEDIWmVLXXSE8ornhunyZ+lMzKravXfeI=", "/8zmEBqVO1op4B9gfqBjyYOoJg0FenIprGU=", "Cf9RFJL3/MJk2NF474iyvGqCoUlD6uBkhzs=", "DqqVGBdRqHezkJ5QQfDcI0z8NYWJR0uz+tk=", "+JkiHJ8zb+/+qFBI0NgNVqXWssHP19n+0Yc=", "44XRIDQ3drRS3T7A/F3GjxtNTBoK9ORSRco=", "FbZmJLxVsSwf5fDYbXUX+vJny15MZHYfbpQ=", "EuOiKDnz5ZnIrb/www15ZdQZX5KGyd3IE3Y=", "5NAVLLGRIgGFlXHoUiWoED0z2NbAWU+FOCg=", "HEk3MC6iTe57PSGggv2lRpjlahcPjpZ76a8=", "6nqANKbAinY2Be+4E9V0M3HP7VNJHgQ2wvE=", "7S9EOCNm3sPhTaCQva0arFexeZ+Ds6/hvxM=", "GxzzPKsEGVusdW6ILIXL2b6b/tvFIz2slE0=", "2xe/QGhu7HWkp3yd5bqRAzaamDQU9dWkiok=", "LSQIROAMK+3pn7KFdJJAdt+wH3BSZUfpodc=", "KnHMSGWqf1g+1/2t2uou6fnOi7yYyOw+3DU=", "3EJ7TO3IuMBz7zO1S8L/nBDkDPjeWH5z92s=", "JNtZUHL71y+NR2P9mxryyrUyvjkRj6eNJuw=", "0ujuVPqZELfAf63lCjIjv1wYOX1XHzXADbI=", "1b0qWH8/RAIXN+LNpEpNIHpmrbGdsp4XcFA=", "I46dXPddg5paDyzVNWKcVZNMKvXbIgxaWw4=", "OJJuYFxZmsH2ekJdGedXjC3X1C4eATH2z0M=", "zqHZZNQ7XVm7QoxFiM+G+cT9U2pYkaO75B0=", "yfQdaFGdCexsCsNtJrfoZuKDx6aSPAhsmf8=", "P8eqbNn/znQhMg11t585EwupQOLUrJohsqE=", "x16IcEbMoZvfml09Z0c0Ra5/8iMbe0PfYyY=", "MW0/dM6uZgOSopMl9m/lMEdVdWdd69GSSHg=", "Njj7eEsIMrZF6twNWBeLr2Er4auXRnpFNZo=", "wAtMfMNq9S4I0hIVyT9a2ogBZu/R1ugIHsQ=", "qy5jgNDcxepVU/gn12k/BmwpLWgo97dVCQ8=", "XR3UhFi+AnIYazY/RkHuc4UDqixuZyUYIlE=", "WkgQiN0YVsfPI3kX6DmA7KN9PuCkyo7PX7M=", "rHunjFV6kV+CG7cPeRFRmUpXuaTiWhyCdO0=", "VOKFkMpJ/rB8s+dHqclcz++BC2UtjcV8pWo=", "otEylEIrOSgxiylfOOGNugarjCFrHVcxjjQ=", "pYT2mMeNbZ3mw2Z3lpnjJSDVGO2hsPzm89Y=", "U7dBnE/vqgWr+6hvB7EyUMn/n6nnIG6r2Ig=", "SKuyoOTrs14HjsbnKzT5iXdkYXIiA1MHTMU=", "vpgFpGyJdMZKtgj/uhwo/J5O5jZkk8FKZ5s=", "uc3BqOkvIHOd/kfXFGRGY7gwcvquPmqdGnk=", "T/52rGFN5+vQxonPhUyXFlEa9b7orvjQMSc=", "t2dUsP5+iAQubtmHVZSaQPTMR38neSEu4KA=", "QVTjtHYcT5xjVhefxLxLNR3mwDth6bNjy/4=", "RgEnuPO6Gym0Hli3asQlqjuYVPerRBi0thw=", "sDKQvHvY3LH5Jpav++z039Ky07Pt1Ir5nUI=", "cDncwLiyKZ/x9IS6MtOuBVqztVw8AmLxg4Y=", "hgprxDDQ7ge8zEqio/t/cLOZMhh6kvC8qNg=", "gV+vyLV2urJrhAWKDYMR75XnptSwP1tr1To=", "d2wYzD0UfSomvMuSnKvAmnzNIZD2r8km/mQ=", "j/U60KInEsXYFJvaTHPNzNkbk1E5eBDYL+M=", "ecaN1CpF1V2VLFXC3VscuTAxFBV/6IKVBL0=", "fpNJ2K/jgehCZBrqcyNyJhZPgNm1RSlCeV8=", "iKD+3CeBRnAPXNTy4gujU/9lB53z1bsPUgE=", "k7wN4IyFXyujKbp6zo5oikH++UY29oajxkw=", "ZY+65ATnmLPuEXRiX6a5/6jUfgJwZhTu7RI=", "Ytp+6IFBzAY5WTtK8d7XYI6q6s66y785kPA=", "lOnJ7AkjC550YfVSYPYGFWeAbYr8Wy10u64=", "bHDr8JYQZHGKyaUasC4LQ8JW30szjPSKaik=", "mkNc9B5yo+nH8WsCIQbaNit8WA91HGbHQXc=", "nRaY+JvU91wQuSQqj360qQ0CzMO/sc0QPJU=", "ayUv/BO2MMRdgeoyHlZl3OQoS4f5IV9dF8s=", "S1zGHb2ll8mqpu1Os9J+DNhSWtBQ83OqEh4=", "vW9xGTXHUFHnniNWIvqveTF43ZQWY+HnOUA=", "ujq1FbBhBOQw1mx+jILB5hcGSVjczkowRKI=", "TAkCETgDw3x97qJmHaoQk/4szhyaXth9b/w=", "tJAgDacwrJODRvIuzXIdxVv6fN1ViQGDvns=", "QqOXCS9SawvOfjw2XFrMsLLQ+5kTGZPOlSU=", "RfZTBar0P74ZNnMe8iKiL5Sub1XZtDgZ6Mc=", "s8XkASKW+CZUDr0GYwpzWn2E6BGfJKpUw5k=", "qNkXPYmS4X34e9OOT4+4g8MfFspaB5f4V9Q=", "XuqgOQHwJuW1Qx2W3qdp9io1kY4clwW1fIo=", "Wb9kNYRWclBiC1K+cN8HaQxLBULWOq5iAWg=", "r4zTMQw0tcgvM5ym4ffWHOVhggaQqjwvKjY=", "VxXxLZMH2ifRm8zuMS/bSkC3MMdffeXR+7E=", "oSZGKRtlHb+cowL2oAcKP6mdt4MZ7Xec0O8=", "pnOCJZ7DSQpL603eDn9koI/jI0/TQNxLrQ0=", "UEA1IRahjpIG04PGn1e11WbJpAuV0E4GhlM=", "kEt5XdXLe7wOAZHTVmjvD+7IwuREBqYOmJc=", "ZnjOWV2pvCRDOV/Lx0A+egfiRaACljRDs8k=", "YS0KVdgP6JGUcRDjaThQ5SGc0WzIO5+Uzis=", "lx69UVBtLwnZSd77+BCBkMi2ViiOqw3Z5XU=", "b4efTc9eQOYn4Y6zKMiMxm1g5OlBfNQnNPI=", "mbQoSUc8h35q2UCrueBds4RKY60H7EZqH6w=", "nuHsRcKa08u9kQ+DF5gzLKI092HNQe29Yk4=", "aNJbQUr4FFPwqcGbhrDiWUsecCWL0X/wSRA=", "c86ofeH8DQhc3K8TqjUpgPWFjv5O8kJc3V0=", "hf0feWmeypAR5GELOx349RyvCboIYtAR9gM=", "gqjbdew4niXGrC4jlWWWajrRnXbCz3vGi+E=", "dJtscWRaWb2LlOA7BE1HH9P7GjKEX+mLoL8=", "jAJObftpNlJ1PLBz1JVKSXYtqPNLiDB1cTg=", "ejH5aXML8co4BH5rRb2bPJ8HL7cNGKI4WmY=", "fWQ9ZfatpX/vTDFD68X1o7l5u3vHtQnvJ4Q=", "i1eKYX7PYueidP9beu0k1lBTPD+BJZuiDNo=", "4HKlnW15UiP/9RVpZLtBCrR7d7h4BMT/GxE=", "FkESmeUblbuyzdtx9ZOQf11R8Pw+lFayME8=", "ERTWlWC9wQ5lhZRZW+v+4HsvZDD0Of1lTa0=", "5ydhkejfBpYovVpBysMvlZIF43SyqW8oZvM=", "H75DjXfsaXnWFQoJGhsiwzfTUbV9frbWt3Q=", "6Y30if+OruGbLcQRizPztt751vE77iSbnCo=", "7tgwhXoo+lRMZYs5JUudKfiHQj3xQ49M4cg=", "GOuHgfJKPcwBXUUhtGNMXBGtxXm30x0BypY=", "A/d0vVlOJJetKCupmOaHha82O6Jy8CCtXts=", "9cTDudEs4w/gEOWxCc5W8EYcvOY0YLLgdYU=", "8pEHtVSKt7o3WKqZp7Y4b2BiKCr+zRk3CGc=", "BKKwsdzocCJ6YGSBNp7pGolIr264XYt6Izk=", "/DuSrUPbH82EyDTJ5kbkTCyeHa93ilKE8r4=", "Cgglqcu52FXJ8PrRd241OcW0musxGsDJ2eA=", "DV3hpU4fjOAeuLX52RZbpuPKDif7t2sepAI=", "+25WocZ9S3hTgHvhSD6K0wrgiWO9J/lTj1w=", "O2Ua3QUXvlZbUmn0gQHQCYLh74xs8RFbkZg=", "zVat2Y11ec4WaqfsECkBfGvLaMgqYYMWusY=", "ygNp1QjTLXvBIujEvlFv4021/ATgzCjBxyQ=", "PDDe0YCx6uOMGibcL3m+lqSfe0CmXLqM7Ho=", "xKn8zR+ChQxysnaU/6GzwAFJyYFpi2NyPf0=", "MppLyZfgQpQ/iriMbolitehjTsUvG/E/FqM=", "Nc+PxRJGFiHowvekwPEMKs4d2gnltlroa0E=", "w/w4wZok0bml+jm8UdndXyc3XU2jJsilQB8=", "2ODL/TEgyOIJj1c0fVwWhpmso5ZmBfUJ1FI=", "LtN8+blCD3pEt5ks7HTH83CGJNIglWdE/ww=", "KYa49TzkW8+T/9YEQgypbFb4sB7qOMyTgu4=", "37UP8bSGnFfexxgc0yR4Gb/SN1qsqF7eqbA=", "Jywt7Su187ggb0hUA/x1TxoEhZtjf4cgeDc=", "0R+a6aPXNCBtV4ZMktSkOvMuAt8l7xVtU2k=", "1kpe5SZxYJW6H8lkPKzKpdVQlhPvQr66Los=", "IHnp4a4Tpw33Jwd8rYQb0Dx6EVep0iz3BdU=", "lriROmdXM49JUcece7n8GK2ktL2g++ZJJDw=", "YIsmPu819BcEaQmE6pEtbUSOM/nma3QED2I=", "Z97iMmqToKLTIUasROlD8mLwpzUsxt/TcoA=", "ke1VNuLxZzqeGYi01cGSh4vaIHFqVk2eWd4=", "aXR3Kn3CCNVgsdj8BRmf0S4MkrClgZRgiFk=", "n0fALvWgz00tiRbklDFOpMcmFfTjEQYtowc=", "mBIEInAGm/j6wVnMOkkgO+FYgTgpvK363uU=", "biGzJvhkXGC3+ZfUq2HxTghyBnxvLD+39bs=", "dT1AGlNgRTsbjPlch+Q6l7bp+KeqDwIbYfY=", "gw73HtsCgqNWtDdEFszr4l/Df+Psn5BWSqg=", "hFszEl6k1haB/HhsuLSFfXm96y8mMjuBN0o=", "cmiEFtbGEY7MxLZ0KZxUCJCXbGtgoqnMHBQ=", "ivGmCkn1fmEybOY8+URZXjVB3qqvdXAyzZM=", "fMIRDsGXufl/VCgkaGyIK9xrWe7p5eJ/5s0=", "e5fVAkQx7UyoHGcMxhTmtPoVzSIjSEmomy8=", "jaRiBsxTKtTlJKkUVzw3wRM/SmZl2NvlsHE=", "Ta8ueg853/rt9rsBngNtG5s+LIm0DjPtrrU=", "u5yZfodbGGKgznUZDyu8bnIUq83ynqGghes=", "vMldcgL9TNd3hjoxoVPS8VRqPwE4Mwp3+Ak=", "Svrqdoqfi086vvQpMHsDhL1AuEV+o5g601c=", "smPIahWs5KDEFqRh4KMO0hiWCoSxdEHEAtA=", "RFB/bp3OIziJLmp5cYvfp/G8jcD35NOJKY4=", "QwW7Yhhod41eZiVR3/OxONfCGQw9SXheVGw=", "tTYMZpAKsBUTXutJTttgTT7onkh72eoTfzI=", "rir/WjsOqU6/K4XBYl6rlIBzYJO++te/638=", "WBlIXrNsbtbyE0vZ83Z64WlZ59f4akXywCE=", "X0yMUjbKOmMlWwTxXQ4Ufk8ncxsyx+4lvcM=", "qX87Vr6o/ftoY8rpzCbFC6YN9F90V3xolp0=", "UeYZSiGbkhSWy5qhHP7IXQPbRp67gKWWRxo=", "p9WuTqn5VYzb81S5jdYZKOrxwdr9EDfbbEQ=", "oIBqQixfATkMuxuRI653t8yPVRY3vZwMEaY=", "VrPdRqQ9xqFBg9WJsoamwiWl0lJxLQ5BOvg=", "PZbyureL9mUcAj+7rNDDHsGNmdWIDFEcLTM=", "y6VFvj/pMf1ROvGjPfgSayinHpHOnMNRBm0=", "zPCBsrpPZUiGcr6Lk4B89A7Zil0EMWiGe48=", "OsM2tjItotDLSnCTAqitgefzDRlCofrLUNE=", "wloUqq0ezT814iDb0nCg10Ilv9iNdiM1gVY=", "NGmjriV8Cqd42u7DQ1hxoqsPOJzL5rF4qgg=", "MzxnoqDaXhKvkqHr7SAfPY1xrFABSxqv1+o=", "xQ/Qpii4mYriqm/zfAjOSGRbKxRH24ji/LQ=", "3hMjmoO8gNFO3wF7UI0FkdrA1c+C+LVOaPk=", "KCCUngveR0kD589jwaXU5DPqUovEaCcDQ6c=", "L3VQko54E/zUr4BLb926exWUxkcOxYzUPkU=", "2UbnlgYa1GSZl05T/vVrDvy+QQNIVR6ZFRs=", "Id/Fipkpu4tnPx4bLi1mWFlo88KHgsdnxJw=", "1+xyjhFLfBMqB9ADvwW3LbBCdIbBElUq78I=", "0Lm2gpTtKKb9T58rEX3ZspY84EoLv/79kiA=", "JooBhhyP7z6wd1EzgFUIx38WZw5NL2ywuX4=", "5oFN+t/lGhC4pUMmSWpSHfcXAeGc+YS4p7o=", "ELL6/leH3Yj1nY0+2EKDaB49hqXaaRb1jOQ=", "F+c+8tIhiT0i1cIWdjrt9zhDEmkQxL0i8QY=", "4dSJ9lpDTqVv7QwO5xI8gtFplS1WVC9v2lg=", "GU2r6sVwIUqRRVxGN8ox1HS/J+yZg/aRC98=", "734c7k0S5tLcfZJepuLgoZ2VoKjfE2TcIIE=", "6CvY4si0smcLNd12CJqOPrvrNGQVvs8LXWM=", "Hhhv5kDWdf9GDRNumbJfS1LBsyBTLl1Gdj0=", "BQSc2uvSbKTqeH3mtTeUkuxaTfuWDWDq4nA=", "8zcr3mOwqzynQLP+JB9F5wVwyr/QnfKnyS4=", "9GLv0uYW/4lwCPzWimcreCMOXnMaMFlwtMw=", "AlFY1m50OBE9MDLOG0/6Dcok2TdcoMs9n5I=", "+sh6yvFHV/7DmGKGy5f3W2/ya/aTdxLDThU=", "DPvNznklkGaOoKyeWr8mLobY7LLV54COZUs=", "C64JwvyDxNNZ6OO29MdIsaCmeH4fSitZGKk=", "/Z2+xnThA0sU0C2uZe+ZxEmM/zpZ2rkUM/c=", "3eRXJ9rypEbj9yrSyGuCFHX27m3wCJXjNiI=", "K9fgI1KQY96uz+TKWUNTYZzcaSm2mAeuHXw=", "LIIkL9c2N2t5h6vi9zs9/rqi/eV8Nax5YJ4=", "2rGTK19U8PM0v2X6ZhPsi1OIeqE6pT40S8A=", "IiixN8BnnxzKFzWytsvh3fZeyGD1cufKmkc=", "1BsGM0gFWISHL/uqJ+MwqB90TySz4nWHsRk=", "007CP82jDDFQZ7SCiZteNzkK2+h5T95QzPs=", "JX11O0XBy6kdX3qaGLOPQtAgXKw/30wd56U=", "PmGGB+7F0vKxKhQSNDZEm267onf6/HGxc+g=", "yFIxA2anFWr8EtoKpR6V7oeRJTO8bOP8WLY=", "zwf1D+MBQd8rWpUiC2b7caHvsf92wUgrJVQ=", "OTRCC2tjhkdmYls6mk4qBEjFNrswUdpmDgo=", "wa1gF/RQ6aiYygtySpYnUu0ThHr/hgOY340=", "N57XE3wyLjDV8sVq2772JwQ5Az65FpHV9NM=", "MMsTH/mUeoUCuopCdcaYuCJHl/JzuzoCiTE=", "xvikG3H2vR1PgkRa5O5JzcttELY1K6hPom8=", "BvPoZ7KcSDNHUFZPLdETF0Nsdlnk/UBHvKs=", "8MBfYzr+j6sKaJhXvPnCYqpG8R2ibdIKl/U=", "95Wbb79Y2x7dINd/EoGs/Yw4ZdFowHnd6hc=", "AaYsazc6HIaQGBlng6l9iGUS4pUuUOuQwUk=", "+T8Od6gJc2lusEkvU3Fw3sDEUFThhzJuEM4=", "Dwy5cyBrtPEjiIc3wlmhqynu1xCnF6AjO5A=", "CFl9f6XN4ET0wMgfbCHPNA+QQ9xtugv0RnI=", "/mrKey2vJ9y5+AYH/QkeQea6xJgrKpm5bSw=", "5XY5R4arPocVjWiP0YzVmFghOkPuCaQV+WE=", "E0WOQw7J+R9YtaaXQKQE7bELvQeomTZY0j8=", "FBBKT4tvraqP/em/7txqcpd1KctiNJ2Pr90=", "4iP9SwMNajLCxSenf/S7B35fro8kpA/ChIM=", "GrrfV5w+Bd08bXfvryy2UduJHE7rc9Y8VQQ=", "7IloUxRcwkVxVbn3PgRnJDKjmwqt40Rxflo=", "69ysX5H6lvCmHfbfkHwJuxTdD8ZnTu+mA7g=", "He8bWxmYUWjrJTjHAVTYzv33iIIh3n3rKOY=", "dso0pwouYay2pNL1HwK9EhnfwwXY/yK2Py0=", "gPmDo4JMpjT7nBztjipsZ/D1REGeb7D7FHM=", "h6xHrwfq8oEs1FPFIFIC+NaL0I1UwhssaZE=", "cZ/wq4+INRlh7J3dsXrTjT+hV8kSUolhQs8=", "iQbStxC7WvafRM2VYaLe25p35QjdhVCfk0g=", "fzVls5jZnW7SfAON8IoPrnNdYkybFcLSuBY=", "eGChvx1/ydsFNEylXvJhMVUj9oBRuGkFxfQ=", "jlMWu5UdDkNIDIK9z9qwRLwJccQXKPtI7qo=", "lU/lhz4ZFxjkeew14197nQKSjx/SC8bkeuc=", "Y3xSg7Z70ICpQSItcneq6Ou4CFuUm1SpUbk=", "ZCmWjzPdhDV+CW0F3A/Ed83GnJdeNv9+LFs=", "khohi7u/Q60zMaMdTScVAiTsG9MYpm0zBwU=", "aoMDlySMLELNmfNVnf8YVIE6qRLXcbTN1oI=", "nLC0k6zu69qAoT1NDNfJIWgQLlaR4SaA/dw=", "m+VwnylIv29X6XJloq+nvk5uuppbTI1XgD4=", "bdbHm6EqePca0bx9M4d2y6dEPd4d3B8aq2A=", "rd2L52JAjdkSA65o+rgsES9FWzHMCvcStaQ=", "W+484+oiSkFfO2Bwa5D9ZMZv3HWKmmVfnvo=", "XLv472+EHvSIcy9YxeiT++ARSLlAN86I4xg=", "qohP6+fm2WzFS+FAVMBCjgk7z/0Gp1zFyEY=", "UhFt93jVtoM747EIhBhP2KztfTzJcIU7GcE=", "pCLa8/C3cRt2238QFTCerUXH+niP4Bd2Mp8=", "o3ce/3URJa6hkzA4u0jwMmO5brRFTbyhT30=", "VUSp+/1z4jbsq/4gKmAhR4qT6fAD3S7sZCM=", "Tlhax1Z3+21A3pCoBuXqnjQIFyvG/hNA8G4=", "uGvtw94VPPUN5l6wl807690ikG+AboEN2zA=", "vz4pz1uzaEDarhGYObVVdPtcBKNKwyraptI=", "SQ2ey9PRr9iXlt+AqJ2EARJ2g+cMU7iXjYw=", "sZS810ziwDdpPo/IeEWJV7egMSbDhGFpXAs=", "R6cL08SAB68kBkHQ6W1YIl6KtmKFFPMkd1U=", "QPLP30EmUxrzTg74RxU2vXj0Iq5PuVjzCrc=", "tsF428lElIK+dsDg1j3nyJHepeoJKcq+Iek=" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP26', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP28', 'Object', {
	init: function () {
		this._resource = [ "/AkcDRL70JZnrmQppwz3OHV36X+1ZHmTsEo6xQ==", "/AkcDRL70JZnrmQppwz3OHV36X+1ZHmTsEo6xQ==", "5RI4GiTrvTHOQchSUxjzcOruz/53yPI7fZR0lw==", "GRskFzYQbaep76x79BQESJ+ZJoHCrIuozd5OUg==", "1yRwNEjLZ2KBgo2kpjD74MnBg+Hujfl2+jXoMw==", "Ky1sOVowt/TmLOmNATwM2Ly2ap5b6YDlSn/S9g==", "MjZILmwg2lNPw0X29SgIkCMvTB+ZRQtNh6GcpA==", "zj9UI37bCsUobSHfUiT/qFZYpWAsIXLeN+umYQ==", "s0jgaJCLzsQfGQdVUWDr3Y+fG9/BB+/s6WrNZg==", "T0H8ZYJwHlJ4t2N89mwc5fro8qB0Y5Z/WSD3ow==", "VlrYcrRgc/XRWM8HAngYrWVx1CG2zx3XlP658Q==", "qlPEf6abo2O29qsupXTvlRAGPV4Dq2REJLSDNA==", "ZGyQXNhAqaaem4rx91AQPUZemD4vihaaE18lVQ==", "mGWMUcq7eTD5Ne7YUFznBTMpcUGa7m8JoxUfkA==", "gX6oRvyrFJdQ2kKjpEjjTaywV8BYQuShbstRwg==", "fXe0S+5QxAE3dCaKA0QUddnHvr/tJp0y3oFrBw==", "e5Dd0D0LgZU+Mg6qosDLpwMjNqOfDsPFz9SHzA==", "h5nB3S/wUQNZnGqDBcw8n3ZU39wqarpWf569CQ==", "noLlyhngPKTwc8b48dg41+nN+V3oxjH+skDzWw==", "Yov5xwsb7DKX3aLRVtTP75y6ECJdokhtAgrJng==", "rLSt5HXA5ve/sIMOBPAwR8ritUJxgzqzNeFv/w==", "UL2x6Wc7NmHYHucno/zHf7+VXD3E50MghatVOg==", "SaaV/lErW8Zx8UtcV+jDNyAMerwGS8iISHUbaA==", "ta+J80PQi1AWXy918OQ0D1V7k8OzL7Eb+D8hrQ==", "yNg9uK2AT1EhKwn/86Ageoy8LXxeCSwpJr5Kqg==", "NNEhtb97n8dGhW3WVKzXQvnLxAPrbVW6lvRwbw==", "LcoFoolr8mDvasGtoLjTCmZS4oIpwd4SWyo+PQ==", "0cMZr5uQIvaIxKWEB7QkMhMlC/2cpaeB62AE+A==", "H/xNjOVLKDOgqYRbVZDbmkV9rp2whNVf3IuimQ==", "4/VRgfew+KXHB+By8pwsojAKR+IF4KzMbMGYXA==", "+u51lsGglQJu6EwJBogo6q+TYWPHTCdkoR/WDg==", "Budpm9NbRZQJRiggoYTf0trkiBxyKF73EVXsyw==", "9j2nvXoWHzd8ZBxJWZ2LUwZGbFsjHJuXg7UThQ==", "CjS7sGjtz6Ebynhg/pF8a3MxhSSWeOIEM/8pQA==", "Ey+fp179ogayJdQbCoV4I+yoo6VU1Gms/iFnEg==", "7yaDqkwGcpDVi7AyrYmPG5nfStrhsBA/Tmtd1w==", "IRnXiTLdeFX95pHt/61ws8+H77rNkWLheYD7tg==", "3RDLhCAmqMOaSPXEWKGHi7rwBsV49RtyycrBcw==", "xAvvkxY2xWQzp1m/rLWDwyVpIES6WZDaBBSPIQ==", "OALzngTNFfJUCT2WC7l0+1AeyTsPPelJtF615A==", "RXVH1eqd0fNjfRscCP1gjonZd4TiG3R7at/e4w==", "uXxb2PhmAWUE0381r/GXtvyunvtXfw3o2pXkJg==", "oGd/z852bMKtPNNOW+WT/mM3uHqV04ZAF0uqdA==", "XG5jwtyNvFTKkrdn/OlkxhZAUQUgt//TpwGQsQ==", "klE34aJWtpHi/5a4rs2bbkAY9GUMlo0NkOo20A==", "blgr7LCtZgeFUfKRCcFsVjVvHRq58vSeIKAMFQ==", "d0MP+4a9C6Asvl7q/dVoHqr2O5t7Xn827X5CRw==", "i0oT9pRG2zZLEDrDWtmfJt+B0uTOOgalXTR4gg==", "ja16bUcdnqJCVhLj+11A9AVlWvi8ElhSTGGUSQ==", "caRmYFXmTjQl+HbKXFG3zHASs4cJdiHB/CuujA==", "aL9Cd2P2I5OMF9qxqEWzhO+LlQbL2qppMfXg3g==", "lLZeenEN8wXrub6YD0lEvJr8fHl+vtP6gb/aGw==", "WokKWQ/W+cDD1J9HXW27FMyk2RlSn6EktlR8eg==", "poAWVB0tKVakevtu+mFMLLnTMGbn+9i3Bh5Gvw==", "v5syQys9RPENlVcVDnVIZCZKFuclV1Mfy8AI7Q==", "Q5IuTjnGlGdqOzM8qXm/XFM9/5iQMyqMe4oyKA==", "PuWaBdeWUGZdTxW2qj2rKYr6QSd9Fbe+pQtZLw==", "wuyGCMVtgPA64XGfDTFcEf+NqFjIcc4tFUFj6g==", "2/eiH/N97VeTDt3k+SVYWWAUjtkK3UWF2J8tuA==", "J/6+EuGGPcH0oLnNXimvYRVjZ6a/uTwWaNUXfQ==", "6cHqMZ9dNwTczZgSDA1QyUM7wsaTmE7IXz6xHA==", "Fcj2PI2m55K7Y/w7qwGn8TZMK7km/Ddb73SL2Q==", "DNPSK7u2ijUSjFBAXxWjuanVDTjkULzzIqrFiw==", "8NrOJqlNWqN1IjRp+BlUgdyi5EdRNMVgkuD/Tg==", "8XpTZ/QsPm74yDiSsicLpgyM2LZGOCszG3cmFw==", "DXNPaubX7vifZly7FSv8nnn7McnzXFKgqz0c0g==", "FGhrfdDHg182ifDA4T/41uZiF0gx8NkIZuNSgA==", "6GF3cMI8U8lRJ5TpRjMP7pMV/jeElKCb1qloRQ==", "Jl4jU7znWQx5SrU2FBfwRsVNW1eotdJF4ULOJA==", "2lc/Xq4ciZoe5NEfsxsHfrA6sigd0avWUQj04Q==", "w0wbSZgM5D23C31kRw8DNi+jlKnffSB+nNa6sw==", "P0UHRIr3NKvQpRlN4AP0DlrUfdZqGVntLJyAdg==", "QjKzD2Sn8Krn0T/H40fge4MTw2mHP8Tf8h3rcQ==", "vjuvAnZcIDyAf1vuREsXQ/ZkKhYyW71MQlfRtA==", "pyCLFUBMTZspkPeVsF8TC2n9DJfw9zbkj4mf5g==", "WymXGFK3nQ1OPpO8F1PkMxyK5ehFk093P8OlIw==", "lRbDOyxsl8hmU7JjRXcbm0rSQIhpsj2pCCgDQg==", "aR/fNj6XR14B/dZK4nvsoz+lqffc1kQ6uGI5hw==", "cAT7IQiHKvmoEnoxFm/o66A8j3Yees+Sdbx31Q==", "jA3nLBp8+m/PvB4YsWMf09VLZgmrHrYBxfZNEA==", "iuqOt8knv/vG+jY4EOfAAQ+v7hXZNuj21KOh2w==", "duOSutvcb22hVFIRt+s3OXrYB2psUpFlZOmbHg==", "b/i2re3MAsoIu/5qQ/8zceVBIeuu/hrNqTfVTA==", "k/GqoP830lxvFZpD5PPESZA2yJQbmmNeGX3viQ==", "Xc7+g4Hs2JlHeLucttc74cZubfQ3uxGALpZJ6A==", "ocfijpMXCA8g1t+1EdvM2bMZhIuC32gTntxzLQ==", "uNzGmaUHZaiJOXPO5c/IkSyAogpAc+O7UwI9fw==", "RNXalLf8tT7ulxfnQsM/qVn3S3X1F5oo40gHug==", "OaJu31mscT/Z4zFtQYcr3IAw9coYMQcaPclsvQ==", "xaty0ktXoam+TVVE5ovc5PVHHLWtVX6JjYNWeA==", "3LBWxX1HzA4Xovk/Ep/YrGreOjRv+fUhQF0YKg==", "ILlKyG+8HJhwDJ0WtZMvlB+p00vanYyy8Bci7w==", "7oYe6xFnFl1YYbzJ57fQPEnxdiv2vP5sx/yEjg==", "Eo8C5gOcxss/z9jgQLsnBDyGn1RD2If/d7a+Sw==", "C5Qm8TWMq2yWIHSbtK8jTKMfudWBdAxXumjwGQ==", "9506/Cd3e/rxjhCyE6PUdNZoUKo0EHXECiLK3A==", "B0f02o46IVmErCTb67qA9QrKtO1lJLCkmMI1kg==", "+07o15zB8c/jAkDyTLZ3zX+9XZLQQMk3KIgPVw==", "4lXMwKrRnGhK7eyJuKJzheAkexMS7EKf5VZBBQ==", "HlzQzbgqTP4tQ4igH66EvZVTkmyniDsMVRx7wA==", "0GOE7sbxRjsFLql/TYp7FcMLNwyLqUnSYvfdoQ==", "LGqY49QKlq1igM1W6oaMLbZ83nM+zTBB0r3nZA==", "NXG89OIa+wrLb2EtHpKIZSnl+PL8YbvpH2OpNg==", "yXig+fDhK5yswQUEuZ5/XVySEY1JBcJ6rymT8w==", "tA8Ush6x752btSOOutprKIVVrzKkI19Icaj49A==", "SAYIvwxKPwv8G0enHdacEPAiRk0RRybbweLCMQ==", "UR0sqDpaUqxV9Ovc6cKYWG+7YMzT661zDDyMYw==", "rRQwpSihgjoyWo/1Ts5vYBrMibNmj9TgvHa2pg==", "YytkhlZ6iP8aN64qHOqQyEyULNNKrqY+i50Qxw==", "nyJ4i0SBWGl9mcoDu+Zn8Dnjxaz/yt+tO9cqAg==", "hjlcnHKRNc7UdmZ4T/JjuKZ64y09ZlQF9glkUA==", "ejBAkWBq5Viz2AJR6P6UgNMNClKIAi2WRkNelQ==", "fNcpCrMxoMy6nipxSXpLUgnpgk76KnNhVxayXg==", "gN41B6HKcFrdME5Y7na8anyeazFPTgry51yImw==", "mcUREJfaHf103+IjGmK4IuMHTbCN4oFaKoLGyQ==", "ZcwNHYUhzWsTcYYKvW5PGpZwpM84hvjJmsj8DA==", "q/NZPvv6x647HKfV70qwssAoAa8Up4oXrSNabQ==", "V/pFM+kBFzhcssP8SEZHirVf6NChw/OEHWlgqA==", "TuFhJN8Rep/1XW+HvFJDwirGzlFjb3gs0Lcu+g==", "suh9Kc3qqgmS8wuuG160+l+xJy7WCwG/YP0UPw==", "z5/JYiO6bgilhy0kGBqgj4Z2mZE7LZyNvnx/OA==", "M5bVbzFBvp7CKUkNvxZXt/MBcO6OSeUeDjZF/Q==", "Ko3xeAdR0zlrxuV2SwJT/2yYVm9M5W62w+gLrw==", "1oTtdRWqA68MaIFf7A6kxxnvvxD5gRclc6Ixag==", "GLu5VmtxCWokBaCAvipbb0+3GnDVoGX7REmXCw==", "5LKlW3mK2fxDq8SpGSasVzrA8w9gxBxo9AOtzg==", "/amBTE+atFvqRGjS7TKoH6VZ1Y6iaJfAOd3jnA==", "AaCdQV1hZM2N6gz7Sj5fJ9AuPPEXDO5TiZfZWQ==", "//SmzvVYfNztjXA5eU4WURgFrXGMcFZmNu5MLg==", "A/26w+ejrEqKIxQQ3kLhaW1yRA45FC/1hqR26w==", "Guae1NGzwe0jzLhrKlblIfLrYo/7uKRdS3o4uQ==", "5u+C2cNIEXtEYtxCjVoSGYeci/BO3N3O+zACfA==", "KNDW+r2TG75sD/2d337tsdHELpBi/a8QzNukHQ==", "1NnK969oyygLoZm0eHIaiaSzx+/XmdaDfJGe2A==", "zcLu4Jl4po+iTjXPjGYewTsq4W4VNV0rsU/Qig==", "Mcvy7YuDdhnF4FHmK2rp+U5dCBGgUSS4AQXqTw==", "TLxGpmXTshjylHdsKC79jJeatq5Nd7mK34SBSA==", "sLVaq3coYo6VOhNFjyIKtOLtX9H4E8AZb867jQ==", "qa5+vEE4Dyk81b8+ezYO/H10eVA6v0uxohD13w==", "VadisVPD379be9sX3Dr5xAgDkC+P2zIiElrPGg==", "m5g2ki0Y1XpzFvrIjh4GbF5bNU+j+kD8JbFpew==", "Z5Eqnz/jBewUuJ7hKRLxVCss3DAWnjlvlftTvg==", "fooOiAnzaEu9VzKa3Qb1HLS1+rHUMrLHWCUd7A==", "goMShRsIuN3a+VazegoCJMHCE85hVstU6G8nKQ==", "hGR7HshT/UnTv36T247d9hsmm9ITfpWj+TrL4g==", "eG1nE9qoLd+0ERq6fIIqzm5Rcq2mGuwwSXDxJw==", "YXZDBOy4QHgd/rbBiJYuhvHIVCxktmeYhK6/dQ==", "nX9fCf5DkO56UNLoL5rZvoS/vVPR0h4LNOSFsA==", "U0ALKoCYmitSPfM3fb4mFtLnGDP982zVAw8j0Q==", "r0kXJ5JjSr01k5ce2rLRLqeQ8UxIlxVGs0UZFA==", "tlIzMKRzJxqcfDtlLqbVZjgJ182KO57ufptXRg==", "SlsvPbaI94z70l9MiaoiXk1+PrI/X+d9ztFtgw==", "NyybdljYM43MpnnGiu42K5S5gA3SeXpPEFAGhA==", "yyWHe0oj4xurCB3vLeLBE+HOaXJnHQPcoBo8QQ==", "0j6jbHwzjrwC57GU2fbFW35XT/OlsYh0bcRyEw==", "Lje/YW7IXiplSdW9fvoyYwsgpowQ1fHn3Y5I1g==", "4AjrQhATVO9NJPRiLN7Ny114A+w89IM56mXutw==", "HAH3TwLohHkqipBLi9I68ygP6pOJkPqqWi/Ucg==", "BRrTWDT46d6DZTwwf8Y+u7eWzBJLPHECl/GaIA==", "+RPPVSYDOUjky1gZ2MrJg8LhJW3+WAiRJ7ug5Q==", "CckBc49OY+uR6WxwINOdAh5DwSqvbM3xtVtfqw==", "9cAdfp21s332RwhZh99qOms0KFUaCLRiBRFlbg==", "7Ns5aaul3tpfqKQic8tucvStDtTYpD/KyM8rPA==", "ENIlZLleDkw4BsAL1MeZSoHa56ttwEZZeIUR+Q==", "3u1xR8eFBIkQa+HUhuNm4teCQstB4TSHT263mA==", "IuRtStV+1B93xYX9Ie+R2qL1q7T0hU0U/ySNXQ==", "O/9JXeNuubjeKimG1fuVkj1sjTU2Kca8MvrDDw==", "x/ZVUPGVaS65hE2vcvdiqkgbZEqDTb8vgrD5yg==", "uoHhGx/FrS+O8GslcbN235Hc2vVuayIdXDGSzQ==", "Roj9Fg0+fbnpXg8M1r+B5+SrM4rbD1uO7HuoCA==", "X5PZATsuEB5AsaN3IquFr3syFQsZo9AmIaXmWg==", "o5rFDCnVwIgnH8dehadylw5F/HSsx6m1ke/cnw==", "baWRL1cOyk0PcuaB14ONP1gdWRSA5ttrpgR6/g==", "kayNIkX1Gtto3IKocI96By1qsGs1gqL4Fk5AOw==", "iLepNXPld3zBMy7ThJt+T7Lzlur3LilQ25AOaQ==", "dL61OGEep+qmnUr6I5eJd8eEf5VCSlDDa9o0rA==", "clnco7JF4n6v22LaghNWpR1g94kwYg40eo/YZw==", "jlDArqC+MujIdQbzJR+hnWgXHvaFBnenysXiog==", "l0vkuZauX09hmqqI0Qul1feOOHdHqvwPBxus8A==", "a0L4tIRVj9kGNM6hdgdS7YL50QjyzoWct1GWNQ==", "pX2sl/qOhRwuWe9+JCOtRdShdGje7/dCgLowVA==", "WXSwmuh1VYpJ94tXgy9afaHWnRdri47RMPAKkQ==", "QG+Ujd5lOC3gGCcsdzteNT5Pu5apJwV5/S5Eww==", "vGaIgMye6LuHtkMF0DepDUs4UukcQ3zqTWR+Bg==", "wRE8yyLOLLqwwmWP03O9eJL/7FbxZeHYk+UVAQ==", "PRggxjA1/CzXbAGmdH9KQOeIBSlEAZhLI68vxA==", "JAME0QYlkYt+g63dgGtOCHgRI6iGrRPj7nFhlg==", "2AoY3BTeQR0ZLcn0J2e5MA1mytczyWpwXjtbUw==", "FjVM/2oFS9gxQOgrdUNGmFs+b7cf6BiuadD9Mg==", "6jxQ8nj+m05W7owC0k+xoC5JhsiqjGE92ZrH9w==", "8yd05U7u9un/ASB5Jlu16LHQoEloIOqVFESJpQ==", "Dy5o6FwVJn+Yr0RQgVdC0MSnSTbdRJMGpA6zYA==", "Do71qQF0QrIVRUiry2kd9xSJdcfKSH1VLZlqOQ==", "8ofppBOPkiRy6yyCbGXqz2H+nLh/LATGndNQ/A==", "65zNsyWf/4PbBID5mHHuh/5nujm9gI9uUA0erg==", "F5XRvjdkLxW8quTQP30Zv4sQU0YI5Pb94Eckaw==", "2aqFnUm/JdCUx8UPbVnmF91I9iYkxYQj16yCCg==", "JaOZkFtE9UbzaaEmylURL6g/H1mRof2wZ+a4zw==", "PLi9h21UmOFahg1dPkEVZzemOdhTDXYYqjj2nQ==", "wLGhin+vSHc9KGl0mU3iX0LR0KfmaQ+LGnLMWA==", "vcYVwZH/jHYKXE/+mgn2KpsWbhgLT5K5xPOnXw==", "Qc8JzIMEXOBt8ivXPQUBEu5hh2e+K+sqdLmdmg==", "WNQt27UUMUfEHYesyREFWnH4oeZ8h2CCuWfTyA==", "pN0x1qfv4dGjs+OFbh3yYgSPSJnJ4xkRCS3pDQ==", "auJl9dk06xSL3sJaPDkNylLX7fnlwmvPPsZPbA==", "lut5+MvPO4LscKZzmzX68iegBIZQphJcjox1qQ==", "j/Bd7/3fViVFnwoIbyH+urg5IgeSCpn0Q1I7+w==", "c/lB4u8khrMiMW4hyC0Jgs1Oy3gnbuBn8xgBPg==", "dR4oeTx/wycrd0YBaanWUBeqQ2RVRr6Q4k3t9Q==", "iRc0dC6EE7FM2SIozqUhaGLdqhvgIscDUgfXMA==", "kAwQYxiUfhblNo5TOrElIP1EjJoijkyrn9mZYg==", "bAUMbgpvroCCmOp6nb3SGIgzZeWX6jU4L5Ojpw==", "ojpYTXS0pEWq9culz5ktsN5rwIW7y0fmGHgFxg==", "XjNEQGZPdNPNW6+MaJXaiKscKfoOrz51qDI/Aw==", "RyhgV1BfGXRktAP3nIHewDSFD3vMA7XdZexxUQ==", "uyF8WkKkyeIDGmfeO40p+EHy5gR5Z8xO1aZLlA==", "xlbIEaz0DeM0bkFUOMk9jZg1WLuUQVF8Cycgkw==", "Ol/UHL4P3XVTwCV9n8XKte1CscQhJSjvu20aVg==", "I0TwC4gfsNL6L4kGa9HO/XLbl0XjiaNHdrNUBA==", "303sBprkYESdge0vzN05xQesfjpW7drUxvluwQ==", "EXK4JeQ/aoG17MzwnvnGbVH021p6zKgK8RLIoA==", "7XukKPbEuhfSQqjZOfUxVSSDMiXPqNGZQVjyZQ==", "9GCAP8DU17B7rQSizeE1HbsaFKQNBFoxjIa8Nw==", "CGmcMtIvByYcA2CLau3CJc5t/du4YCOiPMyG8g==", "+LNSFHtiXYVpIVTikvSWpBLPGZzpVObCrix5vA==", "BLpOGWmZjRMOjzDLNfhhnGe48ONcMJ9RHmZDeQ==", "HaFqDl+J4LSnYJywwexl1Pgh1mKenBT507gNKw==", "4ah2A01yMCLAzviZZuCS7I1WPx0r+G1qY/I37g==", "L5ciIDOpOufoo9lGNMRtRNsOmn0H2R+0VBmRjw==", "054+LSFS6nGPDb1vk8iafK55cwKyvWYn5FOrSg==", "yoUaOhdCh9Ym4hEUZ9yeNDHgVYNwEe2PKY3lGA==", "NowGNwW5V0BBTHU9wNBpDESXvPzFdZQcmcff3Q==", "S/uyfOvpk0F2OFO3w5R9eZ1QAkMoUwkuR0a02g==", "t/KucfkSQ9cRljeeZJiKQegn6zydN3C99wyOHw==", "rumKZs8CLnC4eZvlkIyOCXe+zb1fm/sVOtLATQ==", "UuCWa935/ubf1//MN4B5MQLJJMLq/4KGipj6iA==", "nN/CSKMi9CP3ut4TZaSGmVSRgaLG3vBYvXNc6Q==", "YNbeRbHZJLWQFLo6wqhxoSHmaN1zuonLDTlmLA==", "ec36UofJSRI5+xZBNrx16b5/TlyxFgJjwOcofg==", "hcTmX5UymYReVXJokbCC0csIpyMEcnvwcK0Suw==", "gyOPxEZp3BBXE1pIMDRdAxHsLz92WiUHYfj+cA==", "fyqTyVSSDIYwvT5hlziqO2SbxkDDPlyU0bLEtQ==", "ZjG33mKCYSGZUpIaYyyuc/sC4MEBktc8HGyK5w==", "mjir03B5sbf+/PYzxCBZS451Cb609q6vrCawIg==", "VAf/8A6iu3LWkdfslgSm49gtrN6Y19xxm80WQw==", "qA7j/RxZa+SxP7PFMQhR261aRaEts6XiK4cshg==", "sRXH6ipJBkMY0B++xRxVkzLDYyDvHy5K5lli1A==", "TRzb5ziy1tV/fnuXYhCiq0e0il9ae1fZVhNYEQ==", "MGtvrNbiEtRICl0dYVS23p5zNOC3XcrriJIzFg==", "zGJzocQZwkIvpDk0xlhB5usE3Z8CObN4ONgJ0w==", "1XlXtvIJr+WGS5VPMkxFrnSd+x7AlTjQ9QZHgQ==", "KXBLu+Dyf3Ph5fFmlUCylgHqEmF18UFDRUx9RA==", "508fmJ4pdbbJiNC5x2RNPleytwFZ0DOdcqfbJQ==", "G0YDlYzSpSCuJrSQYGi6BiLFXn7stEoOwu3h4A==", "Al0ngrrCyIcHyRjrlHy+Tr1ceP8uGMGmDzOvsg==", "/lQ7j6g5GBFgZ3zCM3BJdsgrkYCbfLg1v3mVdw==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP28', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP30', 'Object', {
	init: function () {
		this._resource = [ "1PZNScPAS2IFRmexFtmKM7X2SBkSLuRK2MMLaoKW", "1PZNScPAS2IFRmexFtmKM7X2SBkSLuRK2MMLaoKW", "tfGakpudlsQKjM5/LK8JZnfxkDIkXNWUrZsW1Bkx", "YQfX21hd3aYPyqnOOnaDVcIH2Cs2cjHedVgdvpun", "d/8pOSsnMZUUBYH+WEMSzO7/PWRIuLc1RysstTJi", "owlkcOjnevcRQ+ZPTpqY/1sJdX1allN/n+gn37D0", "wg6zq7C6p1EeiU+BdOwbqpkOrVZs5GKh6rA6YStT", "Fvj+4nN67DMbzygwYjWRmSz45U9+yobrMnMxC6nF", "7uNSclZOYjcoCh/hsIYkhcHjesiQbXNqjlZYd2TE", "OhUfO5WOKVUtTHhQpl+utnQVMtGCQ5cgVpVTHeZS", "WxLI4M3T9PMihtGenCkt47YS6vq0Mab+I81Oo331", "j+SFqQ4Tv5EnwLYvivCn0APkouOmH0K0+w5Fyf9j", "mRx7S31pU6I8D54f6MU2SS8cR6zY1cRfyX10wlam", "Teo2Ar6pGMA5Sfmu/hy8eprqD7XK+yAVEb5/qNQw", "LO3h2eb0xWY2g1BgxGo/L1jt1578iRHLZOZiFk+X", "+BuskCU0jgQzxTfR0rO1HO0bn4fup/WBvCVpfM0B", "wduk5KycxG5QFD7ffRFIF5/b9I092ubUAayw7siV", "FS3prW9cjwxVUllua8jCJCotvJQv9AKe2W+7hEoD", "dCo+djcBUqpamPCgUb5BcegqZL8ZhjNArDemOtGk", "oNxzP/TBGchf3pcRR2fLQl3cLKYLqNcKdPStUFMy", "tiSN3Ye79ftEEb8hJVJa23Ekyel1YlHhRoecW/r3", "YtLAlER7vplBV9iQM4vQ6MTSgfBnTLWrnkSXMXhh", "A9UXTxwmYz9OnXFeCf1TvQbVWdtRPoR16xyKj+PG", "1yNaBt/mKF1L2xbvHyTZjrMjEcJDEGA/M9+B5WFQ", "Lzj2lvrSpll4HiE+zZdskl44jkWtt5W+j/romaxR", "+8673zkS7Tt9WEaP207moevOxly/mXH0Vznj8y7H", "mslsBGFPMJ1yku9B4Thl9CnJHneJ60AqImH+TbVg", "Tj8hTaKPe/931Ijw9+Hvx5w/Vm6bxaRg+qL1Jzf2", "WMffr9H1l8xsG6DAldR+XrDHsyHlDyKLyNHELJ4z", "jDGS5hI13K5pXcdxgw30bQUx+zj3IcbBEBLPRhyl", "7TZFPUpoAQhml26/uXt3OMc2IxPBU/cfZUrS+IcC", "OcAIdImoSmpj0QkOr6L9C3LAawrTfRNVvYnZkgWU", "n6tV1UUlldygKHyj+iKQLiOr9Qd6qdG1AkV9wY03", "S10YnIbl3r6lbhsS7PsaHZZdvR5ohzX/2oZ2qw+h", "KlrPR964AxiqpLLc1o2ZSFRaZTVe9QQhr95rFZQG", "/qyCDh14SHqv4tVtwFQTe+GsLSxM2+Brdx1gfxaQ", "6FR87G4CpEm0Lf1domGC4s1UyGMyEWaARW5RdL9V", "PKIxpa3C7yuxa5rstLgI0XiigHogP4LKna1aHj3D", "XaXmfvWfMo2+oTMijs6LhLqlWFEWTbMU6PVHoKZk", "iVOrNzZfee+751STmBcBtw9TEEgEY1deMDZMyiTy", "cUgHpxNr9+uIImNCSqS0q+JIj8/qxKLfjBMltunz", "pb5K7tCrvImNZATzXH0+mFe+x9b46kaVVNAu3Gtl", "xLmdNYj2YS+Crq09Zgu9zZW5H/3OmHdLIYgzYvDC", "EE/QfEs2Kk2H6MqMcNI3/iBPV+TctpMB+Us4CHJU", "BrcunjhMxn6cJ+K8EuemZwy3squifBXqyzgJA9uR", "0kFj1/uMjRyZYYUNBD4sVLlB+rKwUvGgE/sCaVkH", "s0a0DKPRULqWqyzDPkivAXtGIpmGIMB+ZqMf18Kg", "Z7D5RWARG9iT7UtyKJElMs6waoCUDiQ0vmAUvUA2", "XnDxMem5UbLwPEJ8hzPYObxwAYpHczdhA+nNL0Wi", "ioa8eCp5GtD1eiXNkepSCgmGSZNVXdMr2yrGRcc0", "64Fro3Ikx3b6sIwDq5zRX8uBkbhjL+L1rnLb+1yT", "P3cm6rHkjBT/9uuyvUVbbH532aFxAQa/drHQkd4F", "KY/YCMKeYCfkOcOC33DK9VKPPO4Py4BURMLhmnfA", "/XmVQQFeK0Xhf6QzyalAxud5dPcd5WQenAHq8PVW", "nH5CmlkD9uPutQ3989/DkyV+rNwrl1XA6Vn3Tm7x", "SIgP05rDvYHr82pM5QZJoJCI5MU5ubGKMZr8JOxn", "sJOjQ7/3M4XYNl2dN7X8vH2Te0LXHkQLjb+VWCFm", "ZGXuCnw3eOfdcDosIWx2j8hlM1vFMKBBVXyeMqPw", "BWI50SRqpUHSupPiGxr12gpi63DzQpGfICSDjDhX", "0ZR0mOeq7iPX/PRTDcN/6b+Uo2nhbHXV+OeI5rrB", "x2yKepTQAhDMM9xjb/bucJNsRiafpvM+ypS57RME", "E5rHM1cQSXLJdbvSeS9kQyaaDj+NiBd0Eleyh5GS", "cp0Q6A9NlNTGvxIcQ1nnFuSd1hS7+iaqZw+vOQo1", "pmtdocyN37bD+XWtVYBtJVFrng2p1MLgv8ykU4ij", "I0uqt4pKN6VdUPhb6UQ9XEZL9w70T793BIr6nwdu", "973n/kmKfMdYFp/q/523b/O9vxfmYVs93Enx9YX4", "lrowJRHXoWFX3DYkxes0OjG6ZzzQE2rjqRHsSx5f", "Qkx9bNIX6gNSmlGV0zK+CYRMLyXCPY6pcdLnIZzJ", "VLSDjqFtBjBJVXmlsQcvkKi0ymq89whCQ6HWKjUM", "gELOx2KtTVJMEx4Up96lox1CgnOu2ewIm2LdQLea", "4UUZHDrwkPRD2bfanagm9t9FWliYq93W7jrA/iw9", "NbNUVfkw25ZGn9Bri3GsxWqzEkGKhTmcNvnLlK6r", "zaj4xdwEVZJ1Wue6WcIZ2YeojcZkIswdityi6GOq", "GV61jB/EHvBwHIALTxuT6jJexd92DChXUh+pguE8", "eFliV0eZw1Z/1inFdW0Qv/BZHfRAfhmJJ0e0PHqb", "rK8vHoRZiDR6kE50Y7SajEWvVe1SUP3D/4S/VvgN", "ulfR/PcjZAdhX2ZEAYELFWlXsKIsmnsozfeOXVHI", "bqGctTTjL2VkGQH1F1iBJtyh+Ls+tJ9iFTSFN9Ne", "D6ZLbmy+8sNr06g7LS4Ccx6mIJAIxq68YGyYiUj5", "21AGJ69+uaFulc+KO/eIQKtQaIka6Er2uK+T48pv", "4pAOUybW88sNRMaElFV1S9mQA4PJlVmjBSZKcc/7", "NmZDGuUWuKkIAqE1goz/eGxmS5rbu73p3eVBG01t", "V2GUwb1LZQ8HyAj7uPp8La5hk7HtyYw3qL1cpdbK", "g5fZiH6LLm0Cjm9KriP2HhuX26j/52h9cH5Xz1Rc", "lW8nag3xwl4ZQUd6zBZnhzdvPueBLe6WQg1mxP2Z", "QZlqI84xiTwcByDL2s/ttIKZdv6TAwrcms5trn8P", "IJ69+JZsVJoTzYkF4Llu4UCertWlcTsC75ZwEOSo", "9GjwsVWsH/gWi+609mDk0vVo5sy3X99IN1V7emY+", "DHNcIXCYkfwlTtllJNNRzhhzeUtZ+CrJi3ASBqs/", "2IURaLNY2p4gCL7UMgrb/a2FMVJL1s6DU7MZbCmp", "uYLGs+sFBzgvwhcaCHxYqG+C6Xl9pP9dJusE0rIO", "bXSL+ijFTFoqhHCrHqXSm9p0oWBvihsX/igPuDCY", "e4x1GFu/oGkxS1ibfJBDAvaMRC8RQJ38zFs+s5ld", "r3o4UZh/6ws0DT8qaknJMUN6DDYDbnm2FJg12RvL", "zn3visAiNq07x5bkUD9KZIF91B01HEhoYcAoZ4Bs", "GouiwwPifc8+gfFVRubAVzSLnAQnMqwiuQMjDQL6", "vOD/Ys9vonn9eIT4E2atcmXgAgmO5m7CBs+HXopZ", "aBayKwyv6Rv4PuNJBb8nQdAWShCcyIqI3gyMNAjP", "CRFl8FTyNL339EqHP8mkFBIRkjuqurtWq1SRipNo", "3ecouZcyf9/ysi02KRAuJ6fn2iK4lF8cc5ea4BH+", "yx/WW+RIk+zpfQUGSyW/vosfP23GXtn3QeSr67g7", "H+mbEieI2I7sO2K3Xfw1jT7pd3TUcD29mSeggTqt", "fu5MyX/VBSjj8ct5Z4q22Pzur1/iAgxj7H+9P6EK", "qhgBgLwVTkrmt6zIcVM860kY50bwLOgpNLy2VSOc", "UgOtEJkhwE7VcpsZo+CJ96QDeMEeix2oiJnfKe6d", "hvXgWVrhiyzQNPyotTkDxBH1MNgMpfniUFrUQ2wL", "5/I3ggK8Vorf/lVmj0+AkdPy6PM618g8JQLJ/fes", "MwR6y8F8HejauDLXmZYKomYEoOoo+Sx2/cHCl3U6", "JfyEKbIG8dvBdxrn+6ObO0r8RaVWM6qdz7LznNz/", "8QrJYHHGurnEMX1W7XoRCP8KDbxEHU7XF3H49l5p", "kA0euymbZx/L+9SY1wySXT0N1Zdyb38JYinlSMXO", "RPtT8upbLH3OvbMpwdUYboj7nY5gQZtDuuruIkdY", "fTtbhmPzZhetbLonbnflZfo79oSzPIgWB2M3sELM", "qc0Wz6AzLXWoKt2WeK5vVk/Nvp2hEmxc36A82sBa", "yMrBFPhu8NOn4HRYQtjsA43KZraXYF2CqvghZFv9", "HDyMXTuuu7GiphPpVAFmMDg8Lq+FTrnIcjsqDtlr", "CsRyv0jUV4K5aTvZNjT3qRTEy+D7hD8jQEgbBXCu", "3jI/9osUHOC8L1xoIO19mqEyg/npqttpmIsQb/I4", "vzXoLdNJwUaz5fWmGpv+z2M1W9Lf2Oq37dMN0Wmf", "a8OlZBCJiiS2o5IXDEJ0/NbDE8vN9g79NRAGu+sJ", "k9gJ9DW9BCCFZqXG3vHB4DvYjEwjUft8iTVvxyYI", "Ry5EvfZ9T0KAIMJ3yChL044uxFUxfx82UfZkraSe", "JimTZq4gkuSP6mu58l7IhkwpHH4HDS7oJK55Ez85", "8t/eL23g2YaKrAwI5IdCtfnfVGcVI8qi/G1yeb2v", "5CcgzR6aNbWRYyQ4hrLTLNUnsShr6UxJzh5DchRq", "MNFthN1afteUJUOJkGtZH2DR+TF5x6gDFt1IGJb8", "Uda6X4UHo3Gb7+pHqh3aSqLWIRpPtZndY4VVpg1b", "hSD3FkbH6BOeqY32vMRQeRcgaQNdm32Xu0ZezI/N", "RpZJcwmUble6oO22z4h6uIyW8xz1nmPuCAnpIw7c", "kmAEOspUJTW/5ooH2VHwizlguwXnsIek0MriSYxK", "82fT4ZIJ+JOwLCPJ4ydz3vtnYy7RwrZ6pZL/9xft", "J5GeqFHJs/G1akR49f757U6RKzfD7FIwfVH0nZV7", "MWlgSiKzX8KupWxIl8todGJpzni9JtTbTyLFljy+", "5Z8tA+FzFKCr4wv5gRLiR9efhmGvCDCRl+HO/L4o", "hJj62LkuyQakKaI3u2RhEhWYXkqZegFP4rnTQiWP", "UG63kXrugmShb8WGrb3rIaBuFlOLVOUFOnrYKKcZ", "qHUbAV/aDGCSqvJXfw5ePU11idRl8xCEhl+xVGoY", "fINWSJwaRwKX7JXmadfUDviDwc133fTOXpy6PuiO", "HYSBk8RHmqSYJjwoU6FXWzqEGeZBr8UQK8SngHMp", "yXLM2geH0cadYFuZRXjdaI9yUf9TgSFa8wes6vG/", "34oyOHT9PfWGr3OpJ01M8aOKtLAtS6exwXSd4Vh6", "C3x/cbc9dpeD6RQYMZTGwhZ8/Kk/ZUP7GbeWi9rs", "anuoqu9gqzGMI73WC+JFl9R7JIIJF3IlbO+LNUFL", "vo3l4yyg4FOJZdpnHTvPpGGNbJsbOZZvtCyAX8Pd", "h03tl6UIqjnqtNNpspkyrxNNB5HIRIU6CaVZzcZJ", "U7ug3mbI4Vvv8rTYpEC4nKa7T4jaamFw0WZSp0Tf", "Mrx3BT6VPP3gOB0WnjY7yWS8l6PsGFCupD5PGd94", "5ko6TP1Vd5/lfnqniO+x+tFK37r+NrTkfP1Ec13u", "8LLEro4vm6z+sVKX6togY/2yOvWA/DIPTo51ePQr", "JESJ503v0M779zUm/AOqUEhEcuyS0tZFlk1+Ena9", "RUNePBWyDWj0PZzoxnUpBYpDqsekoOeb4xVjrO0a", "kbUTddZyRgrxe/tZ0KyjNj+14t62jgPRO9Zoxm+M", "aa6/5fNGyA7CvsyIAh8WKtKufVlYKfZQh/MBuqKN", "vVjyrDCGg2zH+Ks5FMacGWdYNUBKBxIaXzAK0CAb", "3F8ld2jbXsrIMgL3LrAfTKVf7Wt8dSPEKmgXbru8", "CKloPqsbFajNdGVGOGmVfxCppXJuW8eO8qscBDkq", "HlGW3Nhh+ZvWu012WlwE5jxRQD0QkUFlwNgtD5Dv", "yqfblRuhsvnT/SrHTIWO1YmnCCQCv6UvGBsmZRJ5", "q6AMTkP8b1/cN4MJdvMNgEug0A80zZTxbUM724ne", "f1ZBB4A8JD3ZceS4YCqHs/5WmBYm43C7tYAwsQtI", "2T0cpkyx+4saiJEVNarqlq89BhuPN7JbCkyU4oPr", "DctR749xsOkfzvakI3NgpRrLTgKdGVYR0o+fiAF9", "bMyGNNcsbU8QBF9qGQXj8NjMlimra2fPp9eCNpra", "uDrLfRTsJi0VQjjbD9xpw2063jC5RYOFfxSJXBhM", "rsI1n2eWyh4OjRDrben4WkHCO3/HjwVuTWe4V7GJ", "ejR41qRWgXwLy3daezByafQ0c2bVoeEklaSzPTMf", "GzOvDfwLXNoEAd6UQUbxPDYzq03j09D64Pyug6i4", "z8XiRD/LF7gBR7klV597D4PF41Tx/TSwOD+l6Sou", "N95O1Br/mbwygo70hSzOE27efNMfWsExhBrMlecv", "4ygDndk/0t43xOlFk/VEINsoNMoNdCV7XNnH/2W5", "gi/URoFiD3g4DkCLqYPHdRkv7OE7BhSlKYHaQf4e", "VtmZD0KiRBo9SCc6v1pNRqzZpPgpKPDv8ULRK3yI", "QCFn7THYqCkmhw8K3W/c34AhQbdX4nYEwzHgINVN", "lNcqpPIY40sjwWi7y7ZW7DXXCa5FzJJOG/LrSlfb", "9dD9f6pFPu0sC8F18cDVuffQ0YVzvqOQbqr29Mx8", "ISawNmmFdY8pTabE5xlfikImmZxhkEfatmn9nk7q", "GOa4QuAtP+VKnK/KSLuigTDm8pay7VSPC+AkDEt+", "zBD1CyPtdIdP2sh7XmIosoUQuo+gw7DF0yMvZsno", "rRci0HuwqSFAEGG1ZBSr50cXYqSWsYEbpnsy2FJP", "eeFvmbhw4kNFVgYEcs0h1PLhKr2En2VRfrg5stDZ", "bxmRe8sKDnBemS40EPiwTd4Zz/L6VeO6TMsIuXkc", "u+/cMgjKRRJb30mFBiE6fmvvh+voewfwlAgD0/uK", "2ugL6VCXmLRUFeBLPFe5K6noX8DeCTYu4VAebWAt", "Dh5GoJNX09ZRU4f6Ko4zGBweF9nMJ9JkOZMVB+K7", "9gXqMLZjXdJilrAr+D2GBPEFiF4igCflhbZ8ey+6", "IvOneXWjFrBn0Nea7uQMN0TzwEcwrsOvXXV3Ea0s", "Q/Rwoi3+yxZoGn5U1JKPYob0GGwG3PJxKC1qrzaL", "lwI96+4+gHRtXBnlwksFUTMCUHUU8hY78O5hxbQd", "gfrDCZ1EbEd2kzHVoH6UyB/6tTpqOJDQwp1Qzh3Y", "VQyOQF6EJyVz1VZktqce+6oM/SN4FnSaGl5bpJ9O", "NAtZmwbZ+oN8H/+qjNGdrmgLJQhOZEVEbwZGGgTp", "4P0U0sUZseF5WZgbmggXnd39bRFcSqEOt8VNcIZ/", "Zd3jxIPeWfLn8BXtJsxH5MrdBBIB0dyZDIMTvAmy", "sSuujUAeEpDitnJcMBXN138rTAsT/zjT1EAY1osk", "0Cx5VhhDzzbtfNuSCmNOgr0slCAljQkNoRgFaBCD", "BNo0H9uDhFToOrwjHLrEsQja3Dk3o+1HedsOApIV", "EiLK/aj5aGfz9ZQTfo9VKCQiOXZJaWusS6g/CTvQ", "xtSHtGs5IwX2s/OiaFbfG5HUcW9bR4/mk2s0Y7lG", "p9NQbzNk/qP5eVpsUiBcTlPTqURtNb445jMp3SLh", "cyUdJvCktcH8Pz3dRPnWfeYl4V1/G1pyPvAit6B3", "iz6xttWQO8XP+goMlkpjYQs+ftqRvK/zgtVLy212", "X8j8/xZQcKfKvG29gJPpUr7INsODkku5WhZAoe/g", "Ps8rJE4NrQHFdsRzuuVqB3zP7ui14HpnL05dH3RH", "6jlmbY3N5mPAMKPCrDzgNMk5pvGnzp4t941WdfbR", "/MGYj/63ClDb/4vyzglxreXBQ77ZBBjGxf5nfl8U", "KDfVxj13QTLeuexD2ND7nlA3C6fLKvyMHT1sFN2C", "STACHWUqnJTRc0WN4qZ4y5Iw04z9WM1SaGVxqkYl", "ncZPVKbq1/bUNSI89H/y+CfGm5XvdikYsKZ6wMSz", "pAZHIC9CnZy35CsyW90P81UG8J88CzpNDS+jUsEn", "cPAKaeyC1v6yokyDTQSFwODwuIYuJd4H1eyoOEOx", "EffdsrTfC1i9aOVNd3IGlSL3YK0YV+/ZoLS1htgW", "xQGQ+3cfQDq4LoL8YauMppcBKLQKeQuTeHe+7FqA", "0/luGQRlrAmj4arMA54dP7v5zft0s414SgSP5/NF", "Bw8jUMel52ump819FUeXDA4PheJmnWkykseEjXHT", "Zgj0i5/4Os2pbWSzLzEUWcwIXclQ71js55+ZM+p0", "sv65wlw4ca+sKwMCOeieann+FdBCwbymP1ySWWji", "SuUVUnkM/6uf7jTT61srdpTlilesZkkng3n7JaXj", "nhNYG7rMtMmaqFNi/YKhRSETwk6+SK1tW7rwTyd1", "/xSPwOKRaW+VYvqsx/QiEOMUGmWIOpyzLuLt8bzS", "K+LCiSFRIg2QJJ0d0S2oI1biUnyaFHj59iHmmz5E", "PRo8a1Irzj6L67Utsxg5unoatzPk3v4SxFLXkJeB", "6exxIpHrhVyOrdKcpcGzic/s/yr28BpYHJHc+hUX", "iOum+cm2WPqBZ3tSn7cw3A3rJwHAgiuGacnBRI6w", "XB3rsAp2E5iEIRzjiW6677gdbxjSrM/MsQrKLgwm", "+na2Ecb7zC5H2GlO3O7Xyul28RV7eA0sDsZufYSF", "LoD7WAU7h0xCng7/yjdd+VyAuQxpVulm1gVlFwYT", "T4csg11mWupNVKcx8EHerJ6HYSdfJNi4o114qZ20", "m3Fhyp6mEYhIEsCA5phUnytxKT5NCjzye55zwx8i", "jYmfKO3c/btT3eiwhK3FBgeJzHEzwLoZSe1CyLbn", "WX/SYS4cttlWm48BknRPNbJ/hGgh7l5TkS5JojRx", "OHgFunZBa39ZUSbPqALMYHB4XEMXnG+N5HZUHK/W", "7I5I87WBIB1cF0F+vttGU8WOFFoFsovHPLVfdi1A", "FJXkY5C1rhlv0navbGjzTyiVi93rFX5GgJA2CuBB", "wGOpKlN15XtqlBEeerF5fJ1jw8T5O5oMWFM9YGLX", "oWR+8QsoON1lXrjQQMf6KV9kG+/PSavSLQsg3vlw", "dZIzuMjoc79gGN9hVh5wGuqSU/bdZ0+Y9cgrtHvm", "Y2rNWruSn4x71/dRNCvhg8Zqtrmjrclzx7sav9Ij", "t5yAE3hS1O5+kZDgIvJrsHOc/qCxgy05H3gR1VC1", "1ptXyCAPCUhxWzkuGITo5bGbJouH8RznaiAMa8sS", "Am0agePPQip0HV6fDl1i1gRtbpKV3/itsuMHAUmE", "O60S9WpnCEAXzFeRof+f3XatBZhGouv4D2rek0wQ", "71tfvKmnQyISijAgtyYV7sNbTYFUjA+y16nV+c6G", "jlyIZ/H6noQdQJnujVCWuwFclapi/j5sovHIR1Uh", "WqrFLjI61eYYBv5fm4kciLSq3bNw0NomejLDLde3", "TFI7zEFAOdUDydZv+byNEZhSOPwOGlzNSEHyJn5y", "mKR2hYKAcrcGj7He72UHIi2kcOUcNLiHkIL5TPzk", "+aOhXtrdrxEJRRgQ1ROEd++jqM4qRolZ5drk8mdD", "LVXsFxkd5HMMA3+hw8oORFpV4Nc4aG0TPRnvmOXV", "1U5Ahzwpanc/xkhwEXm7WLdOf1DWz5iSgTyG5CjU", "AbgNzv/pIRU6gC/BB6AxawK4N0nE4XzYWf+NjqpC", "YL/aFae0/LM1SoYPPdayPsC/72Lyk00GLKeQMDHl", "tEmXXGR0t9EwDOG+Kw84DXVJp3vgvalM9GSbWrNz", "orFpvhcOW+Irw8mOSTqplFmxQjSedy+nxheqURq2", "dkck99TOEIAuha4/X+Mjp+xHCi2MWcvtHtShO5gg", "F0DzLIyTzSYhTwfxZZWg8i5A0ga6K/oza4y8hQOH", "w7a+ZU9ThkQkCWBAc0wqwZu2mh+oBR55s0+374ER" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP30', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RSP7', 'Object', {
	init: function () {
		this._resource = [ "f3qapAtEdQ==", "f3qapAtEdQ==", "/vQpVRaI6g==", "gY6z8R3Mnw==", "4fVSqiwNyQ==", "no/IDidJvA==", "HwF7/zqFIw==", "YHvhWzHBVg==", "3/ekSVgajw==", "oI0+7VNe+g==", "IQONHE6SZQ==", "XnkXuEXWEA==", "PgL243QXRg==", "QXhsR39TMw==", "wPbftmKfrA==", "v4xFEmnb2Q==", "o/NVkrA0Aw==", "3InPNrtwdg==", "XQd8x6a86Q==", "In3mY634nA==", "QgYHOJw5yg==", "PXydnJd9vw==", "vPIubYqxIA==", "w4i0yYH1VQ==", "fATx2+gujA==", "A35rf+Nq+Q==", "gvDYjv6mZg==", "/YpCKvXiEw==", "nfGjccQjRQ==", "4os51c9nMA==", "YwWKJNKrrw==", "HH8QgNnv2g==", "W/uqOX1oBg==", "JIEwnXYscw==", "pQ+DbGvg7A==", "2nUZyGCkmQ==", "ug74k1Flzw==", "xXRiN1ohug==", "RPrRxkftJQ==", "O4BLYkypUA==", "hAwOcCVyiQ==", "+3aU1C42/A==", "evgnJTP6Yw==", "BYK9gTi+Fg==", "Zflc2gl/QA==", "GoPGfgI7NQ==", "mw11jx/3qg==", "5HfvKxSz3w==", "+Aj/q81cBQ==", "h3JlD8YYcA==", "BvzW/tvU7w==", "eYZMWtCQmg==", "Gf2tAeFRzA==", "Zoc3peoVuQ==", "5wmEVPfZJg==", "mHMe8PydUw==", "J/9b4pVGig==", "WIXBRp4C/w==", "2Qtyt4POYA==", "pnHoE4iKFQ==", "xgoJSLlLQw==", "uXCT7LIPNg==", "OP4gHa/DqQ==", "R4S6uaSH3A==", "tutJcvrQDA==", "yZHT1vGUeQ==", "SB9gJ+xY5g==", "N2X6g+cckw==", "Vx4b2NbdxQ==", "KGSBfN2ZsA==", "qeoyjcBVLw==", "1pCoKcsRWg==", "aRztO6LKgw==", "FmZ3n6mO9g==", "l+jEbrRCaQ==", "6JJeyr8GHA==", "iOm/kY7HSg==", "95MlNYWDPw==", "dh2WxJhPoA==", "CWcMYJML1Q==", "FRgc4ErkDw==", "amKGREGgeg==", "6+w1tVxs5Q==", "lJavEVcokA==", "9O1OSmbpxg==", "i5fU7m2tsw==", "ChlnH3BhLA==", "dWP9u3slWQ==", "yu+4qRL+gA==", "tZUiDRm69Q==", "NBuR/AR2ag==", "S2ELWA8yHw==", "KxrqAz7zSQ==", "VGBwpzW3PA==", "1e7DVih7ow==", "qpRZ8iM/1g==", "7RDjS4e4Cg==", "kmp574z8fw==", "E+TKHpEw4A==", "bJ5Qupp0lQ==", "DOWx4au1ww==", "c58rRaDxtg==", "8hGYtL09KQ==", "jWsCELZ5XA==", "MudHAt+ihQ==", "TZ3dptTm8A==", "zBNuV8kqbw==", "s2n088JuGg==", "0xIVqPOvTA==", "rGiPDPjrOQ==", "LeY8/eUnpg==", "UpymWe5j0w==", "TuO22TeMCQ==", "MZksfTzIfA==", "sBefjCEE4w==", "z20FKCpAlg==", "rxbkcxuBwA==", "0Gx+1xDFtQ==", "UeLNJg0JKg==", "LphXggZNXw==", "kRQSkG+Whg==", "7m6INGTS8w==", "b+A7xXkebA==", "EJqhYXJaGQ==", "cOFAOkObTw==", "D5vankjfOg==", "jhVpb1UTpQ==", "8W/zy15X0A==", "ccuS5Om9GA==", "DrEIQOL5bQ==", "jz+7sf818g==", "8EUhFfRxhw==", "kD7ATsWw0Q==", "70Ra6s70pA==", "bsrpG9M4Ow==", "EbBzv9h8Tg==", "rjw2rbGnlw==", "0UasCbrj4g==", "UMgf+KcvfQ==", "L7KFXKxrCA==", "T8lkB52qXg==", "MLP+o5buKw==", "sT1NUositA==", "zkfX9oBmwQ==", "0jjHdlmJGw==", "rUJd0lLNbg==", "LMzuI08B8Q==", "U7Z0h0RFhA==", "M82V3HWE0g==", "TLcPeH7Apw==", "zTm8iWMMOA==", "skMmLWhITQ==", "Dc9jPwGTlA==", "crX5mwrX4Q==", "8ztKahcbfg==", "jEHQzhxfCw==", "7DoxlS2eXQ==", "k0CrMSbaKA==", "Es4YwDsWtw==", "bbSCZDBSwg==", "KjA43ZTVHg==", "VUqieZ+Raw==", "1MQRiIJd9A==", "q76LLIkZgQ==", "y8Vqd7jY1w==", "tL/w07Ocog==", "NTFDIq5QPQ==", "SkvZhqUUSA==", "9ceclMzPkQ==", "ir0GMMeL5A==", "CzO1wdpHew==", "dEkvZdEDDg==", "FDLOPuDCWA==", "a0hUmuuGLQ==", "6sbna/ZKsg==", "lbx9z/0Oxw==", "icNtTyThHQ==", "9rn36y+laA==", "dzdEGjJp9w==", "CE3evjktgg==", "aDY/5Qjs1A==", "F0ylQQOooQ==", "lsIWsB5kPg==", "6biMFBUgSw==", "VjTJBnz7kg==", "KU5Tone/5w==", "qMDgU2pzeA==", "17p692E3DQ==", "t8GbrFD2Ww==", "yLsBCFuyLg==", "STWy+UZ+sQ==", "Nk8oXU06xA==", "xyDblhNtFA==", "uFpBMhgpYQ==", "OdTywwXl/g==", "Rq5oZw6hiw==", "JtWJPD9g3Q==", "Wa8TmDQkqA==", "2CGgaSnoNw==", "p1s6zSKsQg==", "GNd/30t3mw==", "Z63le0Az7g==", "5iNWil3/cQ==", "mVnMLla7BA==", "+SItdWd6Ug==", "hli30Ww+Jw==", "B9YEIHHyuA==", "eKyehHq2zQ==", "ZNOOBKNZFw==", "G6kUoKgdYg==", "mienUbXR/Q==", "5V099b6ViA==", "hSbcro9U3g==", "+lxGCoQQqw==", "e9L1+5ncNA==", "BKhvX5KYQQ==", "uyQqTftDmA==", "xF6w6fAH7Q==", "RdADGO3Lcg==", "OqqZvOaPBw==", "WtF459dOUQ==", "JaviQ9wKJA==", "pCVRssHGuw==", "21/LFsqCzg==", "nNtxr24FEg==", "46HrC2VBZw==", "Yi9Y+niN+A==", "HVXCXnPJjQ==", "fS4jBUII2w==", "AlS5oUlMrg==", "g9oKUFSAMQ==", "/KCQ9F/ERA==", "QyzV5jYfnQ==", "PFZPQj1b6A==", "vdj8syCXdw==", "wqJmFyvTAg==", "otmHTBoSVA==", "3aMd6BFWIQ==", "XC2uGQyavg==", "I1c0vQfeyw==", "PygkPd4xEQ==", "QFK+mdV1ZA==", "wdwNaMi5+w==", "vqaXzMP9jg==", "3t12l/I82A==", "oafsM/l4rQ==", "IClfwuS0Mg==", "X1PFZu/wRw==", "4N+AdIYrng==", "n6Ua0I1v6w==", "HiupIZCjdA==", "YVEzhZvnAQ==", "ASrS3qomVw==", "flBIeqFiIg==", "/977i7yuvQ==", "gKRhL7fqyA==" ];
		$.ig.Object.prototype.init.call(this);
	},
	_resource: null,
	$type: new $.ig.Type('RSP7', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('QRCodeBarcodeResources', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getByte64: function (s, i) {
		return $.ig.QRCodeBarcodeResources.prototype._aLPHA.indexOf(s.charAt(i));
	}
	,
	decodeBase64: function (s) {
		var x = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var pads, i, b10;
		var imax = s.length;
		pads = 0;
		if (s.charAt(imax - 1) == $.ig.QRCodeBarcodeResources.prototype._pADCHAR) {
			pads = 1;
			if (s.charAt(imax - 2) == $.ig.QRCodeBarcodeResources.prototype._pADCHAR) {
				pads = 2;
			}
			imax -= 4;
		}
		for (i = 0; i < imax; i += 4) {
			b10 = ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i) << 18) | ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 1) << 12) | ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 2) << 6) | $.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 3);
			x.add1(b10 >> 16);
			x.add1((b10 >> 8) & 255);
			x.add1(b10 & 255);
		}
		switch (pads) {
			case 1:
				b10 = ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i) << 18) | ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 1) << 12) | ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 2) << 6);
				x.add1(b10 >> 16);
				x.add1((b10 >> 8) & 255);
				break;
			case 2:
				b10 = ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i) << 18) | ($.ig.QRCodeBarcodeResources.prototype.getByte64(s, i + 1) << 12);
				x.add1(b10 >> 16);
				break;
		}
		return x.toArray();
	}
	,
	getResource: function (name_) {
		var res = new $.ig[name_]()._resource;
		var arr = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			arr[i] = $.ig.QRCodeBarcodeResources.prototype.decodeBase64(res[i]);
		}
		return arr;
	}
	,
	$type: new $.ig.Type('QRCodeBarcodeResources', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamBarcodeView', 'Object', {
	init: function (barcode) {
		this.__errorTextBlock = new $.ig.TextBlock();
		this.__brushNames = [ "backing", "bar", "label" ];
		this.__ignoreBrushChanged = false;
		this.__scheduled = false;
		this.__font = null;
		this.__fontBrush = new $.ig.Brush();
		this.__labelBrush = (function () {
			var $ret = new $.ig.Brush();
			$ret.fill("black");
			return $ret;
		}());
		this.__currentFontHeight = NaN;
		this.__canvasWidth = -1;
		this.__canvasHeight = -1;
		$.ig.Object.prototype.init.call(this);
		this.barcode(barcode);
		this.__actualBrushes = new $.ig.Dictionary$2(String, $.ig.Dictionary$2.prototype.$type.specialize(String, $.ig.Brush.prototype.$type), 0);
	},
	_barcode: null,
	barcode: function (value) {
		if (arguments.length === 1) {
			this._barcode = value;
			return value;
		} else {
			return this._barcode;
		}
	}
	,
	_cssSpan: null,
	__errorTextBlock: null,
	getErrorTextBlock: function () {
		return this.__errorTextBlock;
	}
	,
	ready: function () {
		return true;
	}
	,
	getDesiredWidth: function (element) {
		var tb = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
		if (tb != null && tb.text() != null) {
			return this.context().measureTextWidth(tb.text()) + $.ig.XamBarcodeView.prototype._tEXT_MARGIN;
		}
		return 0;
	}
	,
	getLabelSize: function (tb) {
		if ($.ig.util.isNaN(this.__currentFontHeight)) {
			this.__currentFontHeight = this.getCurrentFontHeight(null);
		}
		var width = this.getDesiredWidth(tb);
		var height = this.__currentFontHeight;
		return new $.ig.Size(1, width, height);
	}
	,
	__actualBrushes: null,
	__brushNames: null,
	getDefaultColors: function () {
		this.__ignoreBrushChanged = true;
		var clrs = [ "white", "black", "black" ];
		var outline = [ "transparent", null, null ];
		var defaults = (function () {
			var $ret = new $.ig.List$1(Array, 0);
			$ret.add(clrs);
			$ret.add(outline);
			return $ret;
		}());
		this.__actualBrushes.clear();
		var namesCount = this.__brushNames.length;
		for (var i = 0; i < namesCount; i++) {
			var name = this.__brushNames[i];
			var fillBrush = $.ig.BrushUtil.prototype.getGradientBrush("ui-barcode-" + name + "-fill", "ui-barcode-" + name, "background-color", this.container(), clrs[i]);
			var outlineBrush = $.ig.BrushUtil.prototype.getGradientBrush("ui-barcode-" + name + "-outline", "ui-barcode-" + name, "border-top-color", this.container(), outline[i]);
			var dict = new $.ig.Dictionary$2(String, $.ig.Brush.prototype.$type, 0);
			dict.item("fill", fillBrush);
			dict.item("outline", outlineBrush);
			this.__actualBrushes.item(this.__brushNames[i], dict);
		}
		this.updateBrushes();
		this.__ignoreBrushChanged = false;
	}
	,
	updateBrushes: function () {
		this.__ignoreBrushChanged = true;
		var namesCount = this.__brushNames.length;
		for (var i = 0; i < namesCount; i++) {
			var name = this.__brushNames[i];
			this.updateBrush(name, this.__actualBrushes.item(name));
		}
		this.__ignoreBrushChanged = false;
	}
	,
	updateBrush: function (name, dict) {
		var colorBrush = dict.item("fill");
		var outlineBrush = dict.item("outline");
		switch (name) {
			case "backing":
				if (this.__userBackingBrush == null) {
					this.barcode().backingBrush(colorBrush);
				}
				if (this.__userBackingOutline == null) {
					this.barcode().backingOutline(outlineBrush);
				}
				break;
			case "bar":
				if (this.__userBarBrush == null) {
					this.barcode().barBrush(colorBrush);
				}
				break;
			case "label":
				this.__labelBrush = colorBrush;
				break;
		}
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
			old[i0] = prop[i0] == "font" ? null : span.css(prop[i0]);
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
	__ignoreBrushChanged: false,
	onInit: function () {
		this.__ignoreBrushChanged = true;
		this.__ignoreBrushChanged = false;
	}
	,
	__scheduled: false,
	scheduleArrange: function () {
		if (!this.__scheduled) {
			this.__scheduled = true;
			window.setTimeout(this.arrange.runOn(this), 0);
		}
	}
	,
	arrange: function () {
		if (this.__scheduled) {
			this.__scheduled = false;
			this.barcode().arrangeBarcode();
		}
	}
	,
	flush: function () {
		if (this.__scheduled) {
			this.arrange();
		}
	}
	,
	positionLabel: function (tb, x, y) {
		tb.canvasLeft(x);
		tb.canvasTop(y);
	}
	,
	_canvas: null,
	canvas: function (value) {
		if (arguments.length === 1) {
			this._canvas = value;
			return value;
		} else {
			return this._canvas;
		}
	}
	,
	_context: null,
	context: function (value) {
		if (arguments.length === 1) {
			this._context = value;
			return value;
		} else {
			return this._context;
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
	__font: null,
	__fontBrush: null,
	onContainerProvided: function (container) {
		var cont = $(container);
		this.container(cont);
		var width = Math.round(cont.width());
		var height = Math.round(cont.height());
		this.container().css("position", "relative");
		var canv = $("<canvas style='position:absolute'></canvas>");
		this.container().append(canv);
		this.__canvasWidth = $.ig.truncate(width);
		this.__canvasHeight = $.ig.truncate(height);
		canv.attr("width", width.toString());
		canv.attr("height", height.toString());
		if (this._cssSpan == null) {
			this._cssSpan = $("<span style='position:absolute;display:none' />");
		}
		this.container().append(this._cssSpan);
		this.barcode().viewport(new $.ig.Rect(0, 0, 0, width, height));
		this.canvas(canv);
		var context = (this.canvas()[0]).getContext("2d");
		this.context(new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), context));
		this.getDefaultColors();
		this.updateStyle();
		this.context().setFontInfo(this.__font);
		this.render();
	}
	,
	getLabelFont: function () {
		return this.__font;
	}
	,
	setTextBrush: function (tb, b) {
		if (b != null) {
			tb.fill(b);
		} else {
			tb.fill(this.__labelBrush);
		}
	}
	,
	styleUpdated: function () {
		this.getDefaultColors();
		this.updateStyle();
		this.scheduleArrange();
	}
	,
	updateStyle: function () {
		this.__font = $.ig.FontUtil.prototype.getFont(this.container());
		if (this.barcode().font() != null) {
			this.__font = this.barcode().getFontInfo();
		}
		this.__currentFontHeight = this.getCurrentFontHeight(this.__font);
		this.__fontBrush = new $.ig.Brush();
		this.__fontBrush.__fill = this.container().css("color");
		if (this.context() != null) {
			this.context().setFontInfo(this.__font);
		}
	}
	,
	arrangeComplete: function () {
		this.render();
	}
	,
	__labelBrush: null,
	render: function () {
		var $self = this;
		if (this.__canvasWidth != this.barcode().viewport().width() || this.__canvasHeight != this.barcode().viewport().height()) {
			this.canvas().attr("width", this.barcode().viewport().width().toString());
			this.canvas().attr("height", this.barcode().viewport().height().toString());
			this.__canvasWidth = $.ig.truncate(Math.round(this.barcode().viewport().width()));
			this.__canvasHeight = $.ig.truncate(Math.round(this.barcode().viewport().height()));
		}
		this.context().clearRectangle(this.barcode().viewport().left(), this.barcode().viewport().top(), this.barcode().viewport().width(), this.barcode().viewport().height());
		var backingPath = new $.ig.Path();
		backingPath.data((function () {
			var $ret = new $.ig.RectangleGeometry();
			$ret.rect(new $.ig.Rect(0, $self.barcode().viewport().left(), $self.barcode().viewport().top(), $self.barcode().viewport().width(), $self.barcode().viewport().height()));
			return $ret;
		}()));
		backingPath.__fill = this.barcode().backingBrush();
		backingPath.__stroke = this.barcode().backingOutline();
		backingPath.strokeThickness(this.barcode().backingStrokeThickness() * 2);
		this.context().renderPath(backingPath);
		if (this.__errorTextBlock.text() != null && this.__errorTextBlock.__visibility == $.ig.Visibility.prototype.visible) {
			var words = this.__errorTextBlock.text().split(' ');
			var line = "";
			var y = this.barcode().backingStrokeThickness();
			var x = this.barcode().backingStrokeThickness();
			if ($.ig.util.isNaN(this.__currentFontHeight)) {
				this.__currentFontHeight = this.getCurrentFontHeight(null);
			}
			var lineHeight = this.__currentFontHeight * 1.2;
			var maxWidth = this.barcode().viewport().width() - 2 * this.barcode().backingStrokeThickness();
			for (var n = 0; n < words.length; n++) {
				var testLine = line + words[n] + ' ';
				var testWidth = this.context().measureTextWidth(testLine);
				if (testWidth > maxWidth) {
					var txt = (function () {
						var $ret = new $.ig.TextBlock();
						$ret.text(line);
						$ret.canvasTop(y);
						$ret.canvasLeft(x);
						return $ret;
					}());
					this.setTextBrush(txt, this.barcode().fontBrush());
					this.context().setFontInfo(this.__font);
					this.context().renderTextBlock(txt);
					line = words[n] + ' ';
					y += lineHeight;
				} else {
					line = testLine;
				}
			}
			var textBlock = (function () {
				var $ret = new $.ig.TextBlock();
				$ret.text(line);
				$ret.canvasTop(y);
				$ret.canvasLeft(x);
				return $ret;
			}());
			this.setTextBrush(textBlock, this.barcode().fontBrush());
			this.context().setFontInfo(this.__font);
			this.context().renderTextBlock(textBlock);
		}
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
	__canvasWidth: 0,
	__canvasHeight: 0,
	onContainerResized: function () {
		var width = Math.round(this.container().width());
		var height = Math.round(this.container().height());
		this.barcode().viewport(new $.ig.Rect(0, 0, 0, width, height));
	}
	,
	__userBackingBrush: null,
	__userBackingOutline: null,
	__userBarBrush: null,
	onBrushChanged: function (propertyName, oldValue, newValue) {
		var oldBrush = oldValue;
		var newBrush = newValue;
		if (!this.__ignoreBrushChanged) {
			switch (propertyName) {
				case $.ig.XamBarcode.prototype.backingBrushPropertyName:
					this.__userBackingBrush = newBrush;
					break;
				case $.ig.XamBarcode.prototype.backingOutlinePropertyName:
					this.__userBackingOutline = newBrush;
					break;
				case $.ig.XamBarcode.prototype.barBrushPropertyName:
					this.__userBarBrush = newBrush;
					break;
			}
			if (newBrush == null) {
				this.updateBrushes();
			}
		}
	}
	,
	$type: new $.ig.Type('XamBarcodeView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BarcodeAlgorithm', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		$.ig.BarcodeAlgorithm.prototype.codePageByEci = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(-1, 28591);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(0, 437);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(1, 28591);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(2, $.ig.BarcodeAlgorithm.prototype.codePageByEci.item(0));
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(3, $.ig.BarcodeAlgorithm.prototype.codePageByEci.item(1));
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(4, 28592);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(5, 28593);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(6, 28594);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(7, 28595);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(8, 28596);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(9, 28597);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(10, 28598);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(11, 28599);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(13, 874);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(15, 28603);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(17, 28605);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(20, 932);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(21, 1250);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(22, 1251);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(23, 1252);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(24, 1256);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(25, 1200);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(26, 65001);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(27, 20127);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(28, 950);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(29, 936);
		$.ig.BarcodeAlgorithm.prototype.codePageByEci.item(30, 949);
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci = new $.ig.Dictionary$2($.ig.Number.prototype.$type, String, 0);
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(-1, "iso-8859-1");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(0, "Cp437");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(1, $.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(-1));
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(2, "Cp437");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(3, $.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(1));
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(4, "iso-8859-2");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(5, "iso-8859-3");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(6, "iso-8859-4");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(7, "iso-8859-5");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(8, "iso-8859-6");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(9, "iso-8859-7");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(10, "iso-8859-8");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(11, "iso-8859-9");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(13, "iso-8859-11");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(15, "iso-8859-13");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(17, "iso-8859-15");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(20, "shift_jis");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(21, "windows-1250");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(22, "windows-1251");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(23, "windows-1252");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(24, "windows-1256");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(25, "ISO-10646-UCS-2");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(26, "UTF-8");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(27, "ISO646-US");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(28, "Big5");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(29, "gb2312");
		$.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(30, "KSC5601");
		var k;
		var sb = new $.ig.StringBuilder(0);
		for (k = 0; k < 256; k++) {
			sb.append1(String.fromCharCode(k));
		}
		$.ig.BarcodeAlgorithm.prototype.asciiChars = sb.toString();
	},
	baseConvert: function (to, text, from) {
		text = text.toUpperCase();
		var inputLength = text.length;
		var fs = new Array(inputLength);
		var k = 0;
		for (var i = text.length - 1; i >= 0; i--) {
			fs[k++] = text.charAt(i).charCodeAt(0) <= '9'.charCodeAt(0) ? (text.charCodeAt(i) - ('0').charCodeAt(0)) : 10 + (text.charCodeAt(i) - ('A').charCodeAt(0));
		}
		var outputLength = inputLength * ($.ig.intDivide(from, to) + 1);
		var ts = new Array(outputLength + 10);
		var cums = new Array(outputLength + 10);
		for (var i1 = 0; i1 < cums.length; i1++) {
			cums[i1] = 0;
		}
		for (var i2 = 0; i2 < ts.length; i2++) {
			ts[i2] = 0;
		}
		ts[0] = 1;
		for (var i3 = 0; i3 < inputLength; i3++) {
			for (var j = 0; j < outputLength; j++) {
				cums[j] += ts[j] * fs[i3];
				var temp = cums[j];
				var ip = j;
				do {
					var rem = $.ig.intDivide(temp, to);
					cums[ip] = temp - rem * to;
					ip++;
					cums[ip] += rem;
					temp = cums[ip];
				} while (temp >= to);
			}
			for (var j1 = 0; j1 < outputLength; j1++) {
				ts[j1] = ts[j1] * from;
			}
			for (var j2 = 0; j2 < outputLength; j2++) {
				var temp1 = ts[j2];
				var ip1 = j2;
				do {
					var rem1 = $.ig.intDivide(temp1, to);
					ts[ip1] = temp1 - rem1 * to;
					ip1++;
					ts[ip1] += rem1;
					temp1 = ts[ip1];
				} while (temp1 >= to);
			}
		}
		var sout = new $.ig.StringBuilder(0);
		var first = false;
		for (var i4 = outputLength; i4 >= 0; i4--) {
			if (cums[i4] != 0) {
				first = true;
			}
			if (first) {
				if (cums[i4] < 10) {
					sout.append1(String.fromCharCode(cums[i4] + ('0').charCodeAt(0)));
				} else {
					sout.append1(String.fromCharCode(cums[i4] + ('A').charCodeAt(0) - 10));
				}
			}
		}
		if (String.isNullOrEmpty(sout.toString())) {
			return "0";
		}
		return sout.toString();
	}
	,
	pow: function (baseValue, powerValue) {
		var res = 1;
		while (powerValue > 0) {
			res *= baseValue;
			powerValue--;
		}
		return res;
	}
	,
	reverseInt: function (value) {
		var reversedValue = 0;
		for (var k = 0; k < 16; k++) {
			reversedValue <<= 1;
			reversedValue |= (value & 1);
			value >>= 1;
		}
		return reversedValue;
	}
	,
	convertHexToIntValue: function (value) {
		return $.ig.Number.prototype.parseInt(value, 16);
	}
	,
	convertDecimalToHexValue: function (number) {
		return $.ig.BarcodeAlgorithm.prototype.baseConvert(16, number, 10);
	}
	,
	convertHexToDecimalValue: function (hex) {
		return $.ig.BarcodeAlgorithm.prototype.baseConvert(10, hex, 16);
	}
	,
	getRemainder: function (number, divider) {
		if (divider == 0 || String.isNullOrEmpty(number)) {
			return String.empty();
		}
		var dividerLength = divider.toString().length;
		if (number.length < dividerLength || (number.length == dividerLength && $.ig.Number.prototype.parseInt(number) < divider)) {
			return number;
		}
		var rest;
		do {
			var t = $.ig.Number.prototype.parseInt(number.substr(0, dividerLength));
			if ($.ig.intDivide(t, divider) > 0) {
				rest = number.substr(dividerLength);
			} else {
				t = $.ig.Number.prototype.parseInt(number.substr(0, dividerLength + 1));
				rest = number.substr(dividerLength + 1);
			}
			number = (t % divider).toString() + rest;
			if (number.length <= dividerLength) {
				if ($.ig.Number.prototype.parseInt(number) < divider) {
					return number;
				}
			}
		} while (!String.isNullOrEmpty(rest));
		return rest;
	}
	,
	getDivisor: function (number, divider) {
		if (divider == 0 || String.isNullOrEmpty(number)) {
			return String.empty();
		}
		var dividerLength = divider.toString().length;
		if (number.length < dividerLength || (number.length == dividerLength && $.ig.Number.prototype.parseInt(number) < divider)) {
			return "0";
		}
		var sb = new $.ig.StringBuilder(0);
		var rest;
		var addZeros = 0;
		do {
			var t = $.ig.Number.prototype.parseInt(number.substr(0, dividerLength));
			if ($.ig.intDivide(t, divider) > 0) {
				rest = number.substr(dividerLength);
				if (addZeros > 1) {
					for (var k = 1; k < addZeros; k++) {
						sb.append5("0");
					}
				}
			} else {
				if (addZeros > 0) {
					for (var k1 = 0; k1 < addZeros; k1++) {
						sb.append5("0");
					}
					var numberLength = dividerLength + addZeros > number.length ? number.length : dividerLength + addZeros;
					t = $.ig.Number.prototype.parseInt(number.substr(0, numberLength));
					rest = number.substr(numberLength);
				} else {
					t = $.ig.Number.prototype.parseInt(number.substr(0, dividerLength + 1));
					rest = number.substr(dividerLength + 1);
				}
			}
			sb.append5(($.ig.intDivide(t, divider)).toString());
			var tmp = t % divider;
			addZeros = tmp.toString().length == dividerLength ? 0 : dividerLength - tmp.toString().length;
			number = tmp.toString() + rest;
			if (number.length <= dividerLength) {
				if ($.ig.Number.prototype.parseInt(number) < divider) {
					var difference = number.length + addZeros - dividerLength;
					if (difference > 0) {
						for (var k2 = 0; k2 < difference; k2++) {
							sb.append5("0");
						}
					}
					break;
				}
			}
		} while (!String.isNullOrEmpty(rest));
		return sb.toString();
	}
	,
	areCharsValid1: function (code, validChars) {
		if (String.isNullOrEmpty(code) || String.isNullOrEmpty(validChars)) {
			return false;
		}
		for (var k = 0; k < code.length; k++) {
			var found = false;
			for (var i = 0; i < validChars.length; i++) {
				if (code.charAt(k).equals(validChars.charAt(i))) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}
	,
	areCharsValid: function (dataBytes, validString, encoding) {
		for (var i = 0; i < dataBytes.length; i++) {
			var b = dataBytes[i];
			var found = false;
			var $t = encoding.getBytes1(validString);
			for (var i1 = 0; i1 < $t.length; i1++) {
				var c = $t[i1];
				if (b.equals(c)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		return true;
	}
	,
	validateNumericCodeLength: function (code, length) {
		if (String.isNullOrEmpty(code) || code.length != length) {
			return false;
		}
		return $.ig.BarcodeAlgorithm.prototype.areCharsValid1(code, $.ig.BarcodeAlgorithm.prototype.numericChars);
	}
	,
	countConsecutiveDigits: function (msg, startPos) {
		var number = 0;
		var index = startPos;
		if (startPos < msg.length) {
			while ($.ig.util.isDigit(msg.charAt(index)) && index < msg.length) {
				number++;
				index++;
				if (index == msg.length) {
					break;
				}
			}
		}
		return number;
	}
	,
	$type: new $.ig.Type('BarcodeAlgorithm', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BarcodeEncodedAlgorithm', 'BarcodeAlgorithm', {
	_barcode: null,
	barcode: function (value) {
		if (arguments.length === 1) {
			this._barcode = value;
			return value;
		} else {
			return this._barcode;
		}
	}
	,
	init: function (barcode) {
		$.ig.BarcodeAlgorithm.prototype.init.call(this);
		this.barcode(barcode);
	},
	getEncoding: function (codePage) {
		return null;
	}
	,
	getEncoding1: function (name) {
		var encoding_ = $.ig.EncodingService.prototype.getEncoding(name);
		var isEncodingLoaded = encoding_.getBytes2;
		if (!isEncodingLoaded) {
			throw new $.ig.Error(1, $.ig.util.replace($.ig.SR.prototype.getString("notLoadedEncoding"), "{0}", $.ig.EncodingService.prototype.encodingFriendlyNames.item(name)));
		}
		return encoding_;
	}
	,
	$type: new $.ig.Type('BarcodeEncodedAlgorithm', $.ig.BarcodeAlgorithm.prototype.$type)
}, true);

$.ig.util.defType('BarcodeVisualData', 'Object', {
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
	$type: new $.ig.Type('BarcodeVisualData', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GridBarcodeVisualData', 'BarcodeVisualData', {
	init: function () {
		$.ig.BarcodeVisualData.prototype.init.call(this);
	},
	_figuresPath: null,
	figuresPath: function (value) {
		if (arguments.length === 1) {
			this._figuresPath = value;
			return value;
		} else {
			return this._figuresPath;
		}
	}
	,
	_figuresPathRect: null,
	figuresPathRect: function (value) {
		if (arguments.length === 1) {
			this._figuresPathRect = value;
			return value;
		} else {
			return this._figuresPathRect;
		}
	}
	,
	scaleByViewport: function () {
		if (this.figuresPath() != null) {
			this.figuresPath().scaleByViewport(this.viewport());
		}
		if (this.figuresPathRect() != null) {
			this.figuresPathRect().scaleByViewport(this.viewport());
		}
	}
	,
	serialize: function () {
		var sb = new $.ig.StringBuilder(0);
		var first = true;
		sb.appendLine1("{");
		if (this.figuresPath() != null) {
			if (first) {
				first = false;
			} else {
				sb.append5(", ");
			}
			sb.append5("figuresPath: ");
			sb.appendLine1(this.figuresPath().serialize());
		}
		if (this.figuresPathRect() != null) {
			if (first) {
				first = false;
			} else {
				sb.append5(", ");
			}
			sb.append5("figuresPathRect: ");
			sb.appendLine1(this.figuresPathRect().serialize());
		}
		if (first) {
			first = false;
		} else {
			sb.append5(", ");
		}
		sb.append5("viewport: {");
		sb.append5("left: " + this.viewport().left() + ", top: " + this.viewport().top() + ", width: " + this.viewport().width() + ", height: " + this.viewport().height());
		sb.appendLine1("}");
		sb.appendLine1("}");
		return sb.toString();
	}
	,
	$type: new $.ig.Type('GridBarcodeVisualData', $.ig.BarcodeVisualData.prototype.$type)
}, true);

$.ig.util.defType('Bch', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getBchCode: function (data, polynom) {
		var polynomMsbSet = $.ig.Bch.prototype.getMostSignificantBitSet(polynom);
		data <<= polynomMsbSet - 1;
		while ($.ig.Bch.prototype.getMostSignificantBitSet(data) >= polynomMsbSet) {
			data ^= polynom << ($.ig.Bch.prototype.getMostSignificantBitSet(data) - polynomMsbSet);
		}
		return data;
	}
	,
	getMostSignificantBitSet: function (data) {
		var uData = $.ig.util.intSToU(data);
		var digitsNumber = 0;
		while (uData != 0) {
			uData >>>= 1;
			digitsNumber++;
		}
		return digitsNumber;
	}
	,
	$type: new $.ig.Type('Bch', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GfArithmetics', 'Object', {
	init: function (gf) {
		$.ig.Object.prototype.init.call(this);
		this.gfInstance(gf);
	},
	_log: null,
	_aLog: null,
	_gfInstance: 0,
	gfInstance: function (value) {
		if (arguments.length === 1) {
			this._gfInstance = value;
			return value;
		} else {
			return this._gfInstance;
		}
	}
	,
	createLogArrays: function (primePolynom) {
		this._log = new Array(this.gfInstance());
		this._aLog = new Array(this.gfInstance());
		this._log[0] = 1 - this.gfInstance();
		this._aLog[0] = 1;
		for (var i = 1; i < this.gfInstance(); i++) {
			this._aLog[i] = (this._aLog[i - 1] * 2);
			if (this._aLog[i] >= this.gfInstance()) {
				this._aLog[i] ^= primePolynom;
			}
			this._log[this._aLog[i]] = i;
		}
	}
	,
	sum2: function (a, b) {
		return (a ^ b);
	}
	,
	sum: function (aCoefficients, bCoefficients) {
		var lowDegree = aCoefficients;
		var highDegree = bCoefficients;
		if (lowDegree.length > highDegree.length) {
			var temp = lowDegree;
			lowDegree = highDegree;
			highDegree = temp;
		}
		var result = new Array(highDegree.length);
		var lengthDiff = highDegree.length - lowDegree.length;
		$.ig.util.arrayCopy1(highDegree, 0, result, 0, lengthDiff);
		for (var i = lengthDiff; i < highDegree.length; i++) {
			result[i] = $.ig.GfArithmetics.prototype.sum2(lowDegree[i - lengthDiff], highDegree[i]);
		}
		return $.ig.GfArithmetics.prototype.removeLeadingZeros(result);
	}
	,
	sum1: function (a, b) {
		return (a ^ b);
	}
	,
	calculateSumOfBits: function (xa, xb) {
		var xl;
		var xs;
		if (xa.length > xb.length) {
			xl = new Array(xa.length);
			$.ig.util.arrayCopy1(xa, 0, xl, 0, xa.length);
			xs = new Array(xb.length);
			$.ig.util.arrayCopy1(xb, 0, xs, 0, xb.length);
		} else {
			xl = new Array(xb.length);
			$.ig.util.arrayCopy1(xb, 0, xl, 0, xb.length);
			xs = new Array(xa.length);
			$.ig.util.arrayCopy1(xa, 0, xs, 0, xa.length);
		}
		var ll = xl.length;
		var ls = xs.length;
		var xorBits = new Array(ll);
		for (var i = 0; i < ll; i++) {
			if (i < ls) {
				xorBits[i] = $.ig.GfArithmetics.prototype.sum1(xl[i], xs[i]);
			} else {
				xorBits[i] = xl[i];
			}
		}
		return xorBits;
	}
	,
	difference: function (a, b) {
		return $.ig.GfArithmetics.prototype.sum2(a, b);
	}
	,
	product2: function (a, b) {
		if ((a == 0) || (b == 0)) {
			return (0);
		}
		return (this._aLog[(this._log[a] + this._log[b]) % (this.gfInstance() - 1)]);
	}
	,
	product: function (aCoefficients, bCoefficients) {
		if (aCoefficients.length < 1 || bCoefficients.length < 1) {
			return null;
		}
		if ((aCoefficients.length == 1 && aCoefficients[0] == 0) || (bCoefficients.length == 1 && bCoefficients[0] == 0)) {
			return [ 0 ];
		}
		var aLength = aCoefficients.length;
		var bLength = bCoefficients.length;
		var result = new Array(aLength + bLength - 1);
		for (var i = 0; i < aLength; i++) {
			for (var j = 0; j < bLength; j++) {
				result[i + j] = $.ig.GfArithmetics.prototype.sum2(result[i + j], this.product2(aCoefficients[i], bCoefficients[j]));
			}
		}
		return result;
	}
	,
	product1: function (coefficients, scalar) {
		if (scalar == 0) {
			return [ 0 ];
		}
		if (scalar == 1) {
			return coefficients;
		}
		var size = coefficients.length;
		var result = new Array(size);
		for (var i = 0; i < size; i++) {
			result[i] = this.product2(coefficients[i], scalar);
		}
		return result;
	}
	,
	quotient: function (a, b) {
		if (b == 0) {
			return (1 - this.gfInstance());
		}
		if (a == 0) {
			return (0);
		}
		return (this._aLog[(this._log[a] - this._log[b] + (this.gfInstance() - 1)) % (this.gfInstance() - 1)]);
	}
	,
	inverse: function (a) {
		if (a == 0) {
			throw new $.ig.Error(0);
		}
		return this._aLog[255 - this._log[a]];
	}
	,
	getALog: function (index) {
		if (this._aLog.length < index || index < 0) {
			throw new $.ig.ArgumentException(1, "Invalid index!");
		}
		return this._aLog[index];
	}
	,
	getLog: function (index) {
		if (this._log.length < index || index < 0) {
			throw new $.ig.ArgumentException(1, "Invalid index!");
		}
		return this._log[index];
	}
	,
	removeLeadingZeros: function (coefficients) {
		var coefficientzNoZeroStart = coefficients;
		var degree = coefficients.length - 1;
		if (degree > 0 && coefficients[0] == 0) {
			var firstNonZeroIndex = 1;
			while (firstNonZeroIndex <= degree && coefficients[firstNonZeroIndex] == 0) {
				firstNonZeroIndex++;
			}
			if (firstNonZeroIndex == degree + 1) {
				coefficientzNoZeroStart = [ 0 ];
			} else {
				coefficientzNoZeroStart = new Array(degree + 1 - firstNonZeroIndex);
				$.ig.util.arrayCopy1(coefficients, firstNonZeroIndex, coefficientzNoZeroStart, 0, coefficientzNoZeroStart.length);
			}
		}
		return coefficientzNoZeroStart;
	}
	,
	$type: new $.ig.Type('GfArithmetics', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Gf256', 'GfArithmetics', {
	init: function (primePolynom) {
		$.ig.GfArithmetics.prototype.init.call(this, $.ig.Gf256.prototype.gfNumber);
		this.createLogArrays(primePolynom);
	},
	calculatePolynomialAt: function (polynomialCoefficients, point) {
		if (polynomialCoefficients.length == 0) {
			return 0;
		}
		if (point == 0) {
			return polynomialCoefficients[0];
		}
		var degree = polynomialCoefficients.length - 1;
		var result = 0;
		if (point == 1) {
			for (var i = 0; i <= degree; i++) {
				result = $.ig.GfArithmetics.prototype.sum2(result, polynomialCoefficients[i]);
			}
			return result;
		}
		result = polynomialCoefficients[0];
		for (var i1 = 1; i1 <= degree; i1++) {
			result = $.ig.GfArithmetics.prototype.sum2(this.product2(point, result), polynomialCoefficients[i1]);
		}
		return result;
	}
	,
	multiplyByMonomial: function (coefficients, degree, monomialCoef) {
		if (degree < 0) {
			return null;
		}
		if (monomialCoef == 0) {
			return [ 0 ];
		}
		var size = coefficients.length;
		var product = new Array(size + degree);
		for (var i = 0; i < size; i++) {
			product[i] = this.product2(coefficients[i], monomialCoef);
		}
		return product;
	}
	,
	getMonomialCoefficients: function (degree, coefficient) {
		if (degree < 0) {
			return null;
		}
		if (coefficient == 0) {
			return [ 0 ];
		}
		var coefficients = new Array(degree + 1);
		coefficients[0] = coefficient;
		return coefficients;
	}
	,
	$type: new $.ig.Type('Gf256', $.ig.GfArithmetics.prototype.$type)
}, true);

$.ig.util.defType('ReedSolomon', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	calculateCheckwords: function (data, startIndex, checkwordsNumber, p, gf) {
		if (data.length < startIndex + checkwordsNumber) {
			throw new $.ig.ArgumentException(1, $.ig.SR.prototype.getString("EncodingError"));
		}
		var index;
		for (index = startIndex; index < (startIndex + checkwordsNumber); index++) {
			data[index] = 0;
		}
		for (index = 0; index < startIndex; index++) {
			var a = $.ig.GfArithmetics.prototype.sum2(data[startIndex], data[index]);
			for (var i = 0; i < checkwordsNumber; i++) {
				data[startIndex + i] = $.ig.GfArithmetics.prototype.sum2(data[(startIndex + i) + 1], gf.product2(a, p[i]));
			}
			data[(startIndex + checkwordsNumber) - 1] = gf.product2(a, p[checkwordsNumber - 1]);
		}
	}
	,
	$type: new $.ig.Type('ReedSolomon', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('QRReedSolomon', 'ReedSolomon', {
	_gf256: null,
	init: function () {
		$.ig.ReedSolomon.prototype.init.call(this);
		this._gf256 = new $.ig.Gf256(285);
	},
	generateErrorCorrectionCodewords: function (codewords, rsEccCodewords, rsBlockOrder, maxDataCodewords, maxCodewords) {
		var rsPolinomial = $.ig.QRReedSolomon.prototype.setPolinomial(rsEccCodewords);
		if (rsPolinomial == null) {
			return null;
		}
		var j = 0;
		var blockNumber = 0;
		var ecDataBytesPerBlock = new Array(rsBlockOrder.length);
		var index = 0;
		while (index < rsBlockOrder.length) {
			ecDataBytesPerBlock[index] = new Array((rsBlockOrder[index] & $.ig.Gf256.prototype.n) - rsEccCodewords);
			index++;
		}
		index = 0;
		while (index < maxDataCodewords) {
			ecDataBytesPerBlock[blockNumber][j] = codewords[index];
			j++;
			if (j >= (rsBlockOrder[blockNumber] & $.ig.Gf256.prototype.n) - rsEccCodewords) {
				j = 0;
				blockNumber++;
			}
			index++;
		}
		blockNumber = 0;
		var ecBytesPerBlock = new Array(rsBlockOrder.length);
		while (blockNumber < rsBlockOrder.length) {
			var ecBytes = new Array((ecDataBytesPerBlock[blockNumber]).length);
			$.ig.util.arrayCopy1(ecDataBytesPerBlock[blockNumber], 0, ecBytes, 0, (ecDataBytesPerBlock[blockNumber]).length);
			var rsCodewords = (rsBlockOrder[blockNumber] & $.ig.Gf256.prototype.n);
			var rsDataCodewords = rsCodewords - rsEccCodewords;
			j = rsDataCodewords;
			while (j > 0) {
				var first = ecBytes[0];
				if (first != 0) {
					var restChars = new Array(ecBytes.length - 1);
					$.ig.util.arrayCopy1(ecBytes, 1, restChars, 0, ecBytes.length - 1);
					var cal = rsPolinomial[(first & $.ig.Gf256.prototype.n)];
					ecBytes = $.ig.GfArithmetics.prototype.calculateSumOfBits(restChars, cal);
				} else {
					if (rsEccCodewords < ecBytes.length) {
						var rsTempNew = new Array(ecBytes.length - 1);
						$.ig.util.arrayCopy1(ecBytes, 1, rsTempNew, 0, ecBytes.length - 1);
						ecBytes = new Array(rsTempNew.length);
						$.ig.util.arrayCopy1(rsTempNew, 0, ecBytes, 0, rsTempNew.length);
					} else {
						var rsTempNew1 = new Array(rsEccCodewords);
						$.ig.util.arrayCopy1(ecBytes, 1, rsTempNew1, 0, ecBytes.length - 1);
						rsTempNew1[rsEccCodewords - 1] = 0;
						ecBytes = new Array(rsTempNew1.length);
						$.ig.util.arrayCopy1(rsTempNew1, 0, ecBytes, 0, rsTempNew1.length);
					}
				}
				j--;
			}
			ecBytesPerBlock[blockNumber] = ecBytes;
			blockNumber++;
		}
		return $.ig.QRReedSolomon.prototype.assembleDataAndErrorCorrectionBytes(ecDataBytesPerBlock, ecBytesPerBlock, rsBlockOrder, maxDataCodewords, maxCodewords);
	}
	,
	disassembleDataBlocksCodewords: function (codewords, blocksOrder, eccNumberPerBlock, dataCodewordsPerBlock) {
		var codewordsPerBlock = new Array(blocksOrder.length);
		dataCodewordsPerBlock = new Array(blocksOrder.length);
		for (var k = 0; k < blocksOrder.length; k++) {
			codewordsPerBlock[k] = new Array(blocksOrder[k]);
		}
		var shortBlocksCodewordsNumber = blocksOrder[0];
		var longBlocksStartIndex = blocksOrder.length;
		while (longBlocksStartIndex > 0 && blocksOrder[longBlocksStartIndex - 1] != shortBlocksCodewordsNumber) {
			longBlocksStartIndex--;
		}
		var shorterBlocksNumDataCodewords = shortBlocksCodewordsNumber - eccNumberPerBlock;
		var codewordsOffset = 0;
		for (var codewordIndex = 0; codewordIndex < shorterBlocksNumDataCodewords; codewordIndex++) {
			for (var blockIndex = 0; blockIndex < blocksOrder.length; blockIndex++) {
				codewordsPerBlock[blockIndex][codewordIndex] = codewords[codewordsOffset++];
			}
		}
		for (var blockIndex1 = 0; blockIndex1 < longBlocksStartIndex; blockIndex1++) {
			dataCodewordsPerBlock[blockIndex1] = shorterBlocksNumDataCodewords;
		}
		for (var blockIndex2 = longBlocksStartIndex; blockIndex2 < blocksOrder.length; blockIndex2++) {
			codewordsPerBlock[blockIndex2][shorterBlocksNumDataCodewords] = codewords[codewordsOffset++];
			dataCodewordsPerBlock[blockIndex2] = shorterBlocksNumDataCodewords + 1;
		}
		for (var i = shorterBlocksNumDataCodewords; i < (codewordsPerBlock[0]).length; i++) {
			for (var blockIndex3 = 0; blockIndex3 < blocksOrder.length; blockIndex3++) {
				var iOffset = blockIndex3 < longBlocksStartIndex ? i : i + 1;
				codewordsPerBlock[blockIndex3][iOffset] = codewords[codewordsOffset++];
			}
		}
		return {
			ret: codewordsPerBlock,
			p3: dataCodewordsPerBlock
		};
	}
	,
	correctBlockDataBytes: function (blockDataBytes, eccNumber) {
		var syndromeCoefficients = new Array(eccNumber);
		var shouldCorrect = false;
		var blockDataInt = new Array(blockDataBytes.length);
		var index = 0;
		for (var i = 0; i < blockDataBytes.length; i++) {
			var b = blockDataBytes[i];
			blockDataInt[index++] = b;
		}
		for (var i1 = 0; i1 < eccNumber; i1++) {
			var coef = this._gf256.calculatePolynomialAt(blockDataInt, this._gf256.getALog(i1));
			syndromeCoefficients[syndromeCoefficients.length - 1 - i1] = coef;
			if (coef != 0) {
				shouldCorrect = true;
			}
		}
		if (shouldCorrect == false) {
			return true;
		}
		var monomialCoef = $.ig.Gf256.prototype.getMonomialCoefficients(eccNumber, 1);
		if (monomialCoef == null) {
			return false;
		}
		var sigmaOmega = this.runEuclideanAlgorithm(monomialCoef, syndromeCoefficients, eccNumber);
		if (sigmaOmega == null) {
			return false;
		}
		var sigma = sigmaOmega[0];
		var omega = sigmaOmega[1];
		var errorPositions = this.findErrorPositions(sigma);
		var errorsSize = this.findErrorsSize(omega, errorPositions);
		if (errorPositions == null || errorsSize == null) {
			return false;
		}
		for (var i2 = 0; i2 < errorPositions.length; i2++) {
			var position = blockDataBytes.length - 1 - this._gf256.getLog(errorPositions[i2]);
			if (position < 0) {
				return false;
			}
			blockDataBytes[position] = $.ig.GfArithmetics.prototype.difference(blockDataInt[position], errorsSize[i2]);
		}
		return true;
	}
	,
	findErrorsSize: function (errorEvaluator, errorPositions) {
		var s = errorPositions.length;
		var result = new Array(s);
		for (var index = 0; index < s; index++) {
			var inversedXi = this._gf256.inverse(errorPositions[index]);
			var denominator = 1;
			for (var j = 0; j < s; j++) {
				if (index != j) {
					denominator = this._gf256.product2(denominator, $.ig.GfArithmetics.prototype.sum2(1, this._gf256.product2(errorPositions[j], inversedXi)));
				}
			}
			result[index] = this._gf256.product2(this._gf256.calculatePolynomialAt(errorEvaluator, inversedXi), this._gf256.inverse(denominator));
		}
		return result;
	}
	,
	findErrorPositions: function (errorLocator) {
		var errorsNumber = errorLocator.length - 1;
		if (errorsNumber == 1) {
			return [ errorLocator[0] ];
		}
		var result = new Array(errorsNumber);
		var e = 0;
		for (var i = 1; i < 256 && e < errorsNumber; i++) {
			if (this._gf256.calculatePolynomialAt(errorLocator, i) == 0) {
				result[e] = this._gf256.inverse(i);
				e++;
			}
		}
		if (e != errorsNumber) {
			return null;
		}
		return result;
	}
	,
	runEuclideanAlgorithm: function (aCoefficients, bCoefficients, degree) {
		var highDegree = aCoefficients;
		var lowDegree = bCoefficients;
		if (lowDegree.length > highDegree.length) {
			var temp = lowDegree;
			lowDegree = highDegree;
			highDegree = temp;
		}
		var rLast = highDegree;
		var r = lowDegree;
		var sLast = [ 1 ];
		var s = [ 0 ];
		var tLast = [ 0 ];
		var t = [ 1 ];
		while (r.length > $.ig.intDivide(degree, 2)) {
			var rLastLast = rLast;
			var sLastLast = sLast;
			var tLastLast = tLast;
			rLast = r;
			sLast = s;
			tLast = t;
			if (rLast.length == 1 && rLast[0] == 0) {
				return null;
			}
			r = rLastLast;
			var q = [ 0 ];
			var denominatorLeadingTerm = rLast[0];
			var dltInverse = this._gf256.inverse(denominatorLeadingTerm);
			while (r.length >= rLast.length && !(r.length == 0 && r[0] == 0)) {
				var degreeDiff = r.length - rLast.length;
				var scale = this._gf256.product2(r[0], dltInverse);
				q = $.ig.GfArithmetics.prototype.sum(q, $.ig.Gf256.prototype.getMonomialCoefficients(degreeDiff, scale));
				r = $.ig.GfArithmetics.prototype.sum(r, this._gf256.multiplyByMonomial(rLast, degreeDiff, scale));
				if (r == null || q == null) {
					return null;
				}
			}
			s = $.ig.GfArithmetics.prototype.sum(this._gf256.product(q, sLast), sLastLast);
			t = $.ig.GfArithmetics.prototype.sum(this._gf256.product(q, tLast), tLastLast);
		}
		var sigmaZeroCoef = t[t.length - 1];
		if (sigmaZeroCoef == 0) {
			return null;
		}
		var inversed = this._gf256.inverse(sigmaZeroCoef);
		var sigmaCoefficients = this._gf256.product1(t, inversed);
		var omegaCoefficients = this._gf256.product1(r, inversed);
		return [ sigmaCoefficients, omegaCoefficients ];
	}
	,
	setPolinomial: function (eccCodewordsNumber) {
		var rsPolinomialByte = new Array(256);
		for (var i = 0; i < 256; i++) {
			rsPolinomialByte[i] = new Array(eccCodewordsNumber);
		}
		rsPolinomialByte = $.ig.QRCodeBarcodeResources.prototype.getResource("RSP" + eccCodewordsNumber);
		return rsPolinomialByte;
	}
	,
	assembleDataAndErrorCorrectionBytes: function (ecDataBytesPerBlock, ecBytesPerBlock, rsBlockOrder, maxDataCodewords, maxCodewords) {
		var index = 0;
		var codewordIndex = 0;
		var res = new Array(maxCodewords);
		var blockIndex = 0;
		while (index < maxDataCodewords) {
			for (blockIndex = 0; blockIndex < rsBlockOrder.length; blockIndex++) {
				if ((ecDataBytesPerBlock[blockIndex]).length > codewordIndex) {
					res[index] = ecDataBytesPerBlock[blockIndex][codewordIndex] & $.ig.Gf256.prototype.n;
					index++;
				}
			}
			codewordIndex++;
		}
		codewordIndex = 0;
		while (index < maxCodewords) {
			for (blockIndex = 0; blockIndex < rsBlockOrder.length; blockIndex++) {
				if ((ecBytesPerBlock[blockIndex]).length > codewordIndex) {
					res[index] = ecBytesPerBlock[blockIndex][codewordIndex] & $.ig.Gf256.prototype.n;
					index++;
				}
			}
			codewordIndex++;
		}
		return res;
	}
	,
	$type: new $.ig.Type('QRReedSolomon', $.ig.ReedSolomon.prototype.$type)
}, true);

$.ig.util.defType('Gs1Helper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		var k;
		$.ig.Gs1Helper.prototype.aIs = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		$.ig.Gs1Helper.prototype.aIs.item(0, 20);
		$.ig.Gs1Helper.prototype.aIs.item(1, 16);
		$.ig.Gs1Helper.prototype.aIs.item(2, 16);
		$.ig.Gs1Helper.prototype.aIs.item(10, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(11, 8);
		$.ig.Gs1Helper.prototype.aIs.item(12, 8);
		$.ig.Gs1Helper.prototype.aIs.item(13, 8);
		$.ig.Gs1Helper.prototype.aIs.item(15, 8);
		$.ig.Gs1Helper.prototype.aIs.item(17, 8);
		$.ig.Gs1Helper.prototype.aIs.item(20, 4);
		$.ig.Gs1Helper.prototype.aIs.item(21, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(22, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(240, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(241, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(242, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(250, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(251, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(253, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(254, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(30, $.ig.Gs1Helper.prototype.fnc1Number);
		for (k = 3100; k < 3170; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, 10);
		}
		for (k = 3200; k < 3380; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, 10);
		}
		for (k = 3400; k < 3580; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, 10);
		}
		for (k = 3600; k < 3700; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, 10);
		}
		$.ig.Gs1Helper.prototype.aIs.item(37, $.ig.Gs1Helper.prototype.fnc1Number);
		for (k = 3900; k < 3940; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, $.ig.Gs1Helper.prototype.fnc1Number);
		}
		$.ig.Gs1Helper.prototype.aIs.item(400, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(401, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(402, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(403, $.ig.Gs1Helper.prototype.fnc1Number);
		for (k = 410; k < 416; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, 16);
		}
		for (k = 420; k < 427; k++) {
			$.ig.Gs1Helper.prototype.aIs.item(k, $.ig.Gs1Helper.prototype.fnc1Number);
		}
		$.ig.Gs1Helper.prototype.aIs.item(7001, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(7002, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(7003, $.ig.Gs1Helper.prototype.fnc1Number);
		for (k = 7030; k < 7040; k++) {
			$.ig.Gs1Helper.prototype.aIs.item(k, $.ig.Gs1Helper.prototype.fnc1Number);
		}
		for (k = 8001; k < 8009; k++) {
			$.ig.Gs1Helper.prototype.aIs.item(k, $.ig.Gs1Helper.prototype.fnc1Number);
		}
		$.ig.Gs1Helper.prototype.aIs.item(8018, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(8020, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(8100, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(8101, $.ig.Gs1Helper.prototype.fnc1Number);
		$.ig.Gs1Helper.prototype.aIs.item(8102, $.ig.Gs1Helper.prototype.fnc1Number);
		for (k = 90; k < 100; ++k) {
			$.ig.Gs1Helper.prototype.aIs.item(k, $.ig.Gs1Helper.prototype.fnc1Number);
		}
	},
	verifyApplicationIdentifier: function (code, fnc1) {
		if (String.isNullOrEmpty(code)) {
			return String.empty();
		}
		if (code.startsWith("(")) {
			var start = 0;
			var finalValue = "";
			while (start >= 0) {
				var end = code.indexOf(')', start);
				if (end < 0) {
					return String.empty();
				}
				var aiText = code.substr(start + 1, end - (start + 1));
				if (aiText.length < 2 || !$.ig.Gs1Helper.prototype.areDigits(aiText)) {
					return String.empty();
				}
				var aiDigits = $.ig.Number.prototype.parseInt(aiText);
				if ($.ig.Gs1Helper.prototype.aIs.containsKey(aiDigits) == false) {
					return String.empty();
				}
				var len = $.ig.Gs1Helper.prototype.aIs.item(aiDigits);
				if (len == 0) {
					return String.empty();
				}
				start = code.indexOf('(', end);
				var next = (start < 0 ? code.length : start);
				finalValue += aiText + code.substr(end + 1, next - (end + 1));
				if (len < 0) {
					if (start >= 0) {
						finalValue += fnc1;
					}
				} else if (next - end - 1 + aiText.length != len) {
					return String.empty();
				}
			}
			return finalValue;
		}
		return String.empty();
	}
	,
	areDigits: function (aiText) {
		for (var i = 0; i < aiText.length; i++) {
			if ($.ig.util.isDigit(aiText.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	,
	generateCheckDigit: function (code) {
		if (String.isNullOrEmpty(code)) {
			return String.empty();
		}
		var chk = 0;
		var a, b;
		if (code.length % 2 != 0) {
			a = 3;
			b = 1;
		} else {
			a = 1;
			b = 3;
		}
		for (var k = 0; k < code.length; k++) {
			var i = code.charAt(k).charCodeAt(0) - '0'.charCodeAt(0);
			if (k % 2 == 0) {
				chk += a * i;
			} else {
				chk += b * i;
			}
		}
		var result = 0;
		while ((chk + result) % 10 != 0) {
			result++;
		}
		return result.toString();
	}
	,
	$type: new $.ig.Type('Gs1Helper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MeasureHelper', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	xDpi: function () {
		return 96;
	}
	,
	yDpi: function () {
		return 96;
	}
	,
	getXPixels: function (milimeters) {
		var xDpi = $.ig.MeasureHelper.prototype.xDpi();
		return $.ig.MeasureHelper.prototype.convertToInch(milimeters * xDpi);
	}
	,
	getYPixels: function (milimeters) {
		var yDpi = $.ig.MeasureHelper.prototype.yDpi();
		return $.ig.MeasureHelper.prototype.convertToInch(milimeters * yDpi);
	}
	,
	getXMilimeters: function (pixels) {
		return $.ig.MeasureHelper.prototype.convertToMilimeters(pixels / $.ig.MeasureHelper.prototype.xDpi());
	}
	,
	getYMilimenters: function (pixels) {
		return $.ig.MeasureHelper.prototype.convertToMilimeters(pixels / $.ig.MeasureHelper.prototype.yDpi());
	}
	,
	convertToInch: function (milimeters) {
		return milimeters / 25.4;
	}
	,
	convertToMilimeters: function (inches) {
		return inches * 25.4;
	}
	,
	$type: new $.ig.Type('MeasureHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('QRCodeAlgorithm', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	staticInit: function () {
		$.ig.QRCodeAlgorithm.prototype.maxDataLength = [ [ 5596, 7089, 3057, 3993 ], [ 3391, 4296, 1852, 2420 ], [ 2331, 2953, 1273, 1663 ], [ 1435, 1817, 784, 1024 ] ];
		$.ig.QRCodeAlgorithm.prototype.finderPattern = [ [ 1, 1, 1, 1, 1, 1, 1 ], [ 1, 0, 0, 0, 0, 0, 1 ], [ 1, 0, 1, 1, 1, 0, 1 ], [ 1, 0, 1, 1, 1, 0, 1 ], [ 1, 0, 1, 1, 1, 0, 1 ], [ 1, 0, 0, 0, 0, 0, 1 ], [ 1, 1, 1, 1, 1, 1, 1 ] ];
		$.ig.QRCodeAlgorithm.prototype.alignmentPattern = [ [ 1, 1, 1, 1, 1 ], [ 1, 0, 0, 0, 1 ], [ 1, 0, 1, 0, 1 ], [ 1, 0, 0, 0, 1 ], [ 1, 1, 1, 1, 1 ] ];
		$.ig.QRCodeAlgorithm.prototype.horizontalSeparatorPattern = [ [ 0, 0, 0, 0, 0, 0, 0, 0 ] ];
		$.ig.QRCodeAlgorithm.prototype.verticalSeparatorPattern = [ [ 0 ], [ 0 ], [ 0 ], [ 0 ], [ 0 ], [ 0 ], [ 0 ] ];
		$.ig.QRCodeAlgorithm.prototype.alignmentPatternsIndexes = [ new Array(0), [ 6, 18 ], [ 6, 22 ], [ 6, 26 ], [ 6, 30 ], [ 6, 34 ], [ 6, 22, 38 ], [ 6, 24, 42 ], [ 6, 26, 46 ], [ 6, 28, 50 ], [ 6, 30, 54 ], [ 6, 32, 58 ], [ 6, 34, 62 ], [ 6, 26, 46, 66 ], [ 6, 26, 48, 70 ], [ 6, 26, 50, 74 ], [ 6, 30, 54, 78 ], [ 6, 30, 56, 82 ], [ 6, 30, 58, 86 ], [ 6, 34, 62, 90 ], [ 6, 28, 50, 72, 94 ], [ 6, 26, 50, 74, 98 ], [ 6, 30, 54, 78, 102 ], [ 6, 28, 54, 80, 106 ], [ 6, 32, 58, 84, 110 ], [ 6, 30, 58, 86, 114 ], [ 6, 34, 62, 90, 118 ], [ 6, 26, 50, 74, 98, 122 ], [ 6, 30, 54, 78, 102, 126 ], [ 6, 26, 52, 78, 104, 130 ], [ 6, 30, 56, 82, 108, 134 ], [ 6, 34, 60, 86, 112, 138 ], [ 6, 30, 58, 86, 114, 142 ], [ 6, 34, 62, 90, 118, 146 ], [ 6, 30, 54, 78, 102, 126, 150 ], [ 6, 24, 50, 76, 102, 128, 154 ], [ 6, 28, 54, 80, 106, 132, 158 ], [ 6, 32, 58, 84, 110, 136, 162 ], [ 6, 26, 54, 82, 110, 138, 166 ], [ 6, 30, 58, 86, 114, 142, 170 ] ];
		$.ig.QRCodeAlgorithm.prototype.formatInfoCells = [ [ 8, 0 ], [ 8, 1 ], [ 8, 2 ], [ 8, 3 ], [ 8, 4 ], [ 8, 5 ], [ 8, 7 ], [ 8, 8 ], [ 7, 8 ], [ 5, 8 ], [ 4, 8 ], [ 3, 8 ], [ 2, 8 ], [ 1, 8 ], [ 0, 8 ] ];
		var sb = new $.ig.StringBuilder(0);
		for (var k = $.ig.QRCodeAlgorithm.prototype._trailingKanjiSetStart; k <= $.ig.QRCodeAlgorithm.prototype._trailingKanjiSetEnd; k++) {
			sb.append1(String.fromCharCode(k));
		}
		for (var k1 = $.ig.QRCodeAlgorithm.prototype._leadingKanjiSetStart; k1 <= $.ig.QRCodeAlgorithm.prototype._leadingKanjiSetEnd; k1++) {
			sb.append1(String.fromCharCode(k1));
		}
		$.ig.QRCodeAlgorithm.prototype.validQrKanjiChars = $.ig.BarcodeAlgorithm.prototype.asciiChars + sb.toString();
		$.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber = [ [ 10, 12, 14 ], [ 9, 11, 13 ], [ 8, 16, 16 ], [ 8, 10, 12 ] ];
		$.ig.QRCodeAlgorithm.prototype.dataBitsCapacity = [ [ 0, 128, 224, 352, 512, 688, 864, 992, 1232, 1456, 1728, 2032, 2320, 2672, 2920, 3320, 3624, 4056, 4504, 5016, 5352, 5712, 6256, 6880, 7312, 8000, 8496, 9024, 9544, 10136, 10984, 11640, 12328, 13048, 13800, 14496, 15312, 15936, 16816, 17728, 18672 ], [ 0, 152, 272, 440, 640, 864, 1088, 1248, 1552, 1856, 2192, 2592, 2960, 3424, 3688, 4184, 4712, 5176, 5768, 6360, 6888, 7456, 8048, 8752, 9392, 10208, 10960, 11744, 12248, 13048, 13880, 14744, 15640, 16568, 17528, 18448, 19472, 20528, 21616, 22496, 23648 ], [ 0, 72, 128, 208, 288, 368, 480, 528, 688, 800, 976, 1120, 1264, 1440, 1576, 1784, 2024, 2264, 2504, 2728, 3080, 3248, 3536, 3712, 4112, 4304, 4768, 5024, 5288, 5608, 5960, 6344, 6760, 7208, 7688, 7888, 8432, 8768, 9136, 9776, 10208 ], [ 0, 104, 176, 272, 384, 496, 608, 704, 880, 1056, 1232, 1440, 1648, 1952, 2088, 2360, 2600, 2936, 3176, 3560, 3880, 4096, 4544, 4912, 5312, 5744, 6032, 6464, 6968, 7288, 7880, 8264, 8920, 9368, 9848, 10288, 10832, 11408, 12016, 12656, 13328 ] ];
		$.ig.QRCodeAlgorithm.prototype.dataCodewordsCapacity = [ 0, 26, 44, 70, 100, 134, 172, 196, 242, 292, 346, 404, 466, 532, 581, 655, 733, 815, 901, 991, 1085, 1156, 1258, 1364, 1474, 1588, 1706, 1828, 1921, 2051, 2185, 2323, 2465, 2611, 2761, 2876, 3034, 3196, 3362, 3532, 3706 ];
		$.ig.QRCodeAlgorithm.prototype.remainderBits = [ 0, 0, 7, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0 ];
	},
	$type: new $.ig.Type('QRCodeAlgorithm', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('QRCodeEncoder', 'BarcodeEncodedAlgorithm', {
	init: function (barcode) {
		$.ig.BarcodeEncodedAlgorithm.prototype.init.call(this, barcode);
		this.barcode(barcode);
	},
	encodeData: function () {
		this.setProperties();
		var data = this.barcode().data();
		var version = this.barcode().sizeVersion();
		var level = this.barcode().errorCorrectionLevel();
		var result = new $.ig.StringBuilder(0);
		if (this.barcode().eciHeaderDisplayMode() == $.ig.HeaderDisplayMode.prototype.show) {
			result.append5($.ig.QRCodeEncoder.prototype.getEciHeader(this.barcode().eciNumber()));
		}
		var fnc1Mode = this.barcode().fnc1Mode();
		result.append5($.ig.QRCodeEncoder.prototype.setFnc1Mode(fnc1Mode, this.barcode().applicationIndicator()));
		if (fnc1Mode == $.ig.Fnc1Mode.prototype.gs1 || fnc1Mode == $.ig.Fnc1Mode.prototype.industry) {
			var fnc1Char = $.ig.QRCodeEncoder.prototype.getFnc1Character(this.barcode().encodingMode());
			var fnc1Data = data;
			if (fnc1Mode == $.ig.Fnc1Mode.prototype.gs1) {
				fnc1Data = $.ig.Gs1Helper.prototype.verifyApplicationIdentifier(fnc1Data, fnc1Char);
				if (String.isNullOrEmpty(fnc1Data)) {
					var errorMsg = $.ig.SR.prototype.getString("InvalidAI");
					var ex = new $.ig.ArgumentException(1, errorMsg);
					throw ex;
				}
			}
			if (data.equals(fnc1Data) == false) {
				this.saveFnc1Positions(data, fnc1Data, fnc1Char);
				data = fnc1Data;
			}
		}
		var encodedData = this.convertData(data, version, this.barcode().encodingMode());
		var encodedDataString = $.ig.QRCodeEncoder.prototype.getStringDataOfList(encodedData);
		var versionNumber = $.ig.QRCodeEncoder.prototype.getVersionNumber(version, level, encodedDataString.length + result.toString().length);
		var encodedDataPerVersion = this.fixEncodedData(encodedData, versionNumber);
		while (encodedDataString.length != encodedDataPerVersion.length) {
			encodedDataString = encodedDataPerVersion;
			versionNumber = $.ig.QRCodeEncoder.prototype.getVersionNumber(version, level, encodedDataPerVersion.length + result.toString().length);
			encodedDataPerVersion = this.fixEncodedData(encodedData, versionNumber);
		}
		var maxDataBits = $.ig.QRCodeAlgorithm.prototype.dataBitsCapacity[level][versionNumber];
		result.append5(encodedDataPerVersion);
		result.append5($.ig.QRCodeEncoder.prototype.getTerminator(result.toString(), level, versionNumber));
		var codewords = $.ig.QRCodeEncoder.prototype.divideIntoCodewords(result.toString(), maxDataBits);
		var maxDataCodewords = $.ig.QRCodeEncoder.prototype.fillWidthPads(codewords, maxDataBits);
		var maxCodewordsNumber = $.ig.QRCodeAlgorithm.prototype.dataCodewordsCapacity[versionNumber];
		var blocksOrder;
		var eccNumber;
		var levelString = "";
		switch (level) {
			case $.ig.QRCodeErrorCorrectionLevel.prototype.high:
				levelString = "H";
				break;
			case $.ig.QRCodeErrorCorrectionLevel.prototype.low:
				levelString = "L";
				break;
			case $.ig.QRCodeErrorCorrectionLevel.prototype.medium:
				levelString = "M";
				break;
			case $.ig.QRCodeErrorCorrectionLevel.prototype.quartil:
				levelString = "Q";
				break;
		}
		var eccData = $.ig.QRCodeBarcodeResources.prototype.getResource("Ecc" + versionNumber + levelString);
		blocksOrder = eccData[0];
		eccNumber = eccData[1];
		var finalCodewords = $.ig.QRCodeEncoder.prototype.addErrorCorrectionCodewords(maxDataCodewords, maxCodewordsNumber, eccNumber, blocksOrder);
		var modulesNumberPerSide = 17 + (versionNumber << 2);
		var bitMatrixDat = $.ig.QRCodeEncoder.prototype.createMatrix($.ig.QRCodeEncoder.prototype.getStringDataOfList(new $.ig.List$1(String, 1, finalCodewords)), modulesNumberPerSide, versionNumber, level);
		return bitMatrixDat;
	}
	,
	saveFnc1Positions: function (data, fnc1Data, fnc1Char) {
		var index = 0;
		for (var k = 0; k < fnc1Data.length; k++) {
			if (index == data.length) {
				break;
			}
			if (this.fnc1Mode() == $.ig.Fnc1Mode.prototype.gs1 && (data.charAt(index) == '(' || data.charAt(index) == ')')) {
				index++;
			}
			var d = fnc1Data.charAt(k);
			if (d.equals(data.charAt(index)) == false && d.equals(fnc1Char)) {
				this._fnc1Positions.add(k);
				continue;
			}
			index++;
		}
	}
	,
	getTerminator: function (data, level, version) {
		var terminator = String.empty();
		var terminatorLength = $.ig.QRCodeEncoder.prototype.getTerminatorLength(data.length, level, version);
		if (terminatorLength > 0) {
			terminator = ($.ig.QRModeIndicator.prototype.terminator).toString().padLeft(terminatorLength, '0');
		}
		return terminator;
	}
	,
	getTerminatorLength: function (dataLength, level, version) {
		var maxDataLength = version == 0 ? $.ig.QRCodeAlgorithm.prototype.dataBitsCapacity[level][40] : $.ig.QRCodeAlgorithm.prototype.dataBitsCapacity[level][version];
		if (dataLength == maxDataLength) {
			return 0;
		}
		var terminatorLength = dataLength % 8;
		terminatorLength = terminatorLength != 0 ? 8 - terminatorLength : terminatorLength;
		if (terminatorLength < 4 && version != 0) {
			if (maxDataLength - (terminatorLength + dataLength) >= 8) {
				terminatorLength += 8;
			}
		}
		return terminatorLength;
	}
	,
	fixEncodedData: function (encodedData, version) {
		var dataString = $.ig.QRCodeEncoder.prototype.getStringDataOfList(encodedData);
		var versionIndex = version < 10 ? 0 : version < 27 ? 1 : 2;
		var isCharacterCountIndicatorChanged = false;
		for (var k = 0; k < encodedData.count(); k += 3) {
			var currentMode = encodedData.__inner[k].trimStart([ '0' ]).length - 1;
			var characterCountIndicatorBits = $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[currentMode][versionIndex];
			if (encodedData.__inner[k + 1].length != characterCountIndicatorBits) {
				encodedData.__inner[k + 1] = $.ig.BarcodeAlgorithm.prototype.baseConvert(2, this.dataSize().__inner[$.ig.intDivide(k, 3)].toString(), 10).padLeft(characterCountIndicatorBits, '0');
				isCharacterCountIndicatorChanged = true;
			}
		}
		if (isCharacterCountIndicatorChanged) {
			dataString = $.ig.QRCodeEncoder.prototype.getStringDataOfList(encodedData);
		}
		return dataString;
	}
	,
	setProperties: function () {
		this.setEncoding();
		this._fnc1Positions = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		this.dataSize(new $.ig.List$1($.ig.Number.prototype.$type, 0));
	}
	,
	setFnc1Mode: function (mode, applicationIndicator) {
		var result = new $.ig.StringBuilder(0);
		switch (mode) {
			case $.ig.Fnc1Mode.prototype.gs1:
				result.append5(($.ig.QRModeIndicator.prototype.fnc1First).toString().padLeft(4, '0'));
				break;
			case $.ig.Fnc1Mode.prototype.industry:
				result.append5(($.ig.QRModeIndicator.prototype.fnc1Second).toString().padLeft(4, '0'));
				var aiCodeword = $.ig.QRCodeEncoder.prototype.getApplicationIndicatorCodeword(applicationIndicator);
				if (String.isNullOrEmpty(aiCodeword)) {
					var errorMsg = $.ig.util.replace($.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidPropertyValue"), "{0}", "ApplicationIndicator"), "{1}", "{a-z, A-Z, 00"), "{2}", "99}");
					var ex = new $.ig.ArgumentException(1, errorMsg);
					throw ex;
				}
				result.append5(aiCodeword);
				break;
			default: break;
		}
		return result.toString();
	}
	,
	setEncoding: function () {
		var encoding;
		var name = String.empty();
		try {
			name = $.ig.BarcodeAlgorithm.prototype.encodingNameByEci.item(this.barcode().eciNumber());
		}
		catch (ex) {
			var msg = $.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("NotSupportedEncoding"), "{0}", "Eci number"), "{1}", this.barcode().eciNumber().toString());
			throw new $.ig.ArgumentException(1, msg);
		}
		encoding = this.getEncoding1(name);
		this.qrEncoding(encoding);
	}
	,
	getVersionNumber: function (version, level, encodedDataLength) {
		var versionNumber = version;
		var terminatorLength = $.ig.QRCodeEncoder.prototype.getTerminatorLength(encodedDataLength, level, versionNumber);
		if (versionNumber == 0) {
			versionNumber = 1;
			for (var i = 1; i < 40; i++) {
				if (($.ig.QRCodeAlgorithm.prototype.dataBitsCapacity[level][i]) >= encodedDataLength + terminatorLength) {
					break;
				}
				versionNumber++;
			}
		}
		return versionNumber;
	}
	,
	getStringDataOfList: function (encodedData) {
		var data = new $.ig.StringBuilder(0);
		var en = encodedData.getEnumerator();
		while (en.moveNext()) {
			var part = en.current();
			data.append5(part);
		}
		return data.toString();
	}
	,
	createMatrix: function (dataBitsString, modulesNumberPerSide, versionNumber, level) {
		var mask = $.ig.QRCodeEncoder.prototype.calculateMask(dataBitsString, modulesNumberPerSide, level, versionNumber);
		var matrix = $.ig.QRCodeEncoder.prototype.createFunctionPatternsMatrix(modulesNumberPerSide, versionNumber);
		matrix = $.ig.QRCodeEncoder.prototype.fillFormatInfo(level, mask, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillVersionInfo(versionNumber, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillDataBits(dataBitsString, mask, matrix);
		return matrix;
	}
	,
	createFunctionPatternsMatrix: function (modulesNumberPerSide, version) {
		var matrix = $.ig.QRCodeEncoder.prototype.createEmptyMatrix(modulesNumberPerSide);
		matrix = $.ig.QRCodeEncoder.prototype.fillFinderPatternsAndSeparators(matrix);
		matrix[matrix.length - 8][8] = 1;
		matrix = $.ig.QRCodeEncoder.prototype.fillAlignmentPatterns(version, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillTimingPatterns(matrix);
		return matrix;
	}
	,
	fillTimingPatterns: function (matrix) {
		for (var i = 8; i < matrix.length - 8; ++i) {
			if ((matrix[i][6] != $.ig.QRCodeEncoder.prototype._emptyCell && matrix[i][6] != $.ig.QRCodeAlgorithm.prototype.hiddenCell && matrix[i][6] != 0 && matrix[i][6] != 1) || (matrix[6][i] != $.ig.QRCodeEncoder.prototype._emptyCell && matrix[6][i] != $.ig.QRCodeAlgorithm.prototype.hiddenCell && matrix[6][i] != 0 && matrix[6][i] != 1)) {
				var errorMsg = $.ig.SR.prototype.getString("EncodingError");
				throw new $.ig.ArgumentException(1, errorMsg);
			}
			var bit = (i + 1) % 2;
			if (matrix[i][6] == $.ig.QRCodeEncoder.prototype._emptyCell) {
				matrix[i][6] = bit;
			}
			if (matrix[6][i] == $.ig.QRCodeEncoder.prototype._emptyCell) {
				matrix[6][i] = bit;
			}
		}
		return matrix;
	}
	,
	fillAlignmentPatterns: function (version, matrix) {
		if (version >= 2) {
			var index = version - 1;
			var coordinates = $.ig.QRCodeAlgorithm.prototype.alignmentPatternsIndexes[index];
			var numCoordinates = ($.ig.QRCodeAlgorithm.prototype.alignmentPatternsIndexes[index]).length;
			for (var i = 0; i < numCoordinates; ++i) {
				for (var j = 0; j < numCoordinates; ++j) {
					var y = coordinates[i];
					var x = coordinates[j];
					if (matrix[y][x] == $.ig.QRCodeEncoder.prototype._emptyCell) {
						var xStartIndex = x - 2;
						var yStartIndex = y - 2;
						for (var row = 0; row < 5; row++) {
							for (var col = 0; col < 5; col++) {
								if (matrix[yStartIndex + row][xStartIndex + col] == $.ig.QRCodeEncoder.prototype._emptyCell) {
									matrix[yStartIndex + row][xStartIndex + col] = $.ig.QRCodeAlgorithm.prototype.alignmentPattern[row][col];
								}
							}
						}
					}
				}
			}
		}
		return matrix;
	}
	,
	fillFinderPatternsAndSeparators: function (matrix) {
		var fpWidth = ($.ig.QRCodeAlgorithm.prototype.finderPattern[0]).length;
		matrix = $.ig.QRCodeEncoder.prototype.fillFinderPattern(0, 0, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillFinderPattern(matrix.length - fpWidth, 0, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillFinderPattern(0, matrix.length - fpWidth, matrix);
		var spWidth = ($.ig.QRCodeAlgorithm.prototype.horizontalSeparatorPattern[0]).length;
		matrix = $.ig.QRCodeEncoder.prototype.fillHorizontalSeparationPattern(0, spWidth - 1, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillHorizontalSeparationPattern(matrix.length - spWidth, spWidth - 1, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillHorizontalSeparationPattern(0, matrix.length - spWidth, matrix);
		var spHeight = $.ig.QRCodeAlgorithm.prototype.verticalSeparatorPattern.length;
		matrix = $.ig.QRCodeEncoder.prototype.fillVerticalSeparationPattern(spHeight, 0, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillVerticalSeparationPattern(matrix.length - spHeight - 1, 0, matrix);
		matrix = $.ig.QRCodeEncoder.prototype.fillVerticalSeparationPattern(spHeight, matrix.length - spHeight, matrix);
		return matrix;
	}
	,
	fillVerticalSeparationPattern: function (xIndex, yIndex, matrix) {
		for (var k = 0; k < 7; k++) {
			if (matrix[yIndex + k][xIndex] != $.ig.QRCodeEncoder.prototype._emptyCell) {
				var errorMsg = $.ig.SR.prototype.getString("EncodingError");
				throw new $.ig.ArgumentException(1, errorMsg);
			}
			matrix[yIndex + k][xIndex] = $.ig.QRCodeAlgorithm.prototype.verticalSeparatorPattern[k][0];
		}
		return matrix;
	}
	,
	fillHorizontalSeparationPattern: function (xIndex, yIndex, matrix) {
		for (var k = 0; k < 8; k++) {
			if (matrix[yIndex][xIndex + k] != $.ig.QRCodeEncoder.prototype._emptyCell) {
				var errorMsg = $.ig.SR.prototype.getString("EncodingError");
				throw new $.ig.ArgumentException(1, errorMsg);
			}
			matrix[yIndex][xIndex + k] = $.ig.QRCodeAlgorithm.prototype.horizontalSeparatorPattern[0][k];
		}
		return matrix;
	}
	,
	fillFinderPattern: function (xIndex, yIndex, matrix) {
		for (var j = 0; j < 7; j++) {
			for (var i = 0; i < 7; i++) {
				matrix[yIndex + j][xIndex + i] = $.ig.QRCodeAlgorithm.prototype.finderPattern[j][i];
			}
		}
		return matrix;
	}
	,
	fillFormatInfo: function (level, mask, matrix) {
		var foramtInfoBits = $.ig.QRCodeEncoder.prototype.createFormatInfo(level, mask);
		for (var i = 0; i < foramtInfoBits.length; i++) {
			var bit = foramtInfoBits[foramtInfoBits.length - 1 - i];
			var x1 = $.ig.QRCodeAlgorithm.prototype.formatInfoCells[i][0];
			var y1 = $.ig.QRCodeAlgorithm.prototype.formatInfoCells[i][1];
			matrix[y1][x1] = bit;
			if (i < 8) {
				var x2 = matrix.length - i - 1;
				matrix[8][x2] = bit;
			} else {
				var y2 = matrix.length - 7 + (i - 8);
				matrix[y2][8] = bit;
			}
		}
		return matrix;
	}
	,
	createFormatInfo: function (level, mask) {
		var bits = new $.ig.StringBuilder(0);
		var formatInfo = (level << 3) | mask;
		bits.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, formatInfo.toString(), 10).padLeft(5, '0'));
		var bchCode = $.ig.Bch.prototype.getBchCode(formatInfo, $.ig.QRCodeAlgorithm.prototype.formatInfoGenPolynom);
		bits.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, bchCode.toString(), 10).padLeft(10, '0'));
		var maskBits = $.ig.BarcodeAlgorithm.prototype.baseConvert(2, $.ig.QRCodeAlgorithm.prototype.formatInfoMaskPattern.toString(), 10).padLeft(15, '0');
		var bitsArray = $.ig.GfArithmetics.prototype.calculateSumOfBits($.ig.QRCodeEncoder.prototype.getBitsArray(bits.toString()), $.ig.QRCodeEncoder.prototype.getBitsArray(maskBits));
		return bitsArray;
	}
	,
	getBitsArray: function (bits) {
		var bitsArray = new Array(bits.length);
		for (var k = 0; k < bits.length; k++) {
			bitsArray[k] = $.ig.Number.prototype.parseInt(bits.charAt(k).toString());
		}
		return bitsArray;
	}
	,
	fillVersionInfo: function (version, matrix) {
		if (version >= 7) {
			var versionInfoBits = $.ig.QRCodeEncoder.prototype.createVersionInfo(version);
			var bitIndex = 6 * 3 - 1;
			for (var i = 0; i < 6; ++i) {
				for (var j = 0; j < 3; ++j) {
					var bit = versionInfoBits.charAt(bitIndex).charCodeAt(0) - '0'.charCodeAt(0);
					bitIndex--;
					matrix[matrix.length - 11 + j][i] = bit;
					matrix[i][matrix.length - 11 + j] = bit;
				}
			}
		}
		return matrix;
	}
	,
	createVersionInfo: function (version) {
		var bits = new $.ig.StringBuilder(0);
		bits.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, version.toString(), 10).padLeft(6, '0'));
		var bchCode = $.ig.Bch.prototype.getBchCode(version, $.ig.QRCodeAlgorithm.prototype.versionInfoGenPolynom);
		bits.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, bchCode.toString(), 10).padLeft(12, '0'));
		return bits.toString();
	}
	,
	fillDataBits: function (dataBits, mask, matrix) {
		var bitIndex = 0;
		var direction = -1;
		var col = matrix.length - 1;
		var row = col;
		while (col > 0) {
			if (col == $.ig.QRCodeAlgorithm.prototype.verticalTimingPatternIndex) {
				col -= 1;
			}
			while (row >= 0 && row < matrix.length) {
				for (var i = 0; i < 2; ++i) {
					var symbolCol = col - i;
					if (matrix[row][symbolCol] != $.ig.QRCodeEncoder.prototype._emptyCell && matrix[row][symbolCol] != $.ig.QRCodeAlgorithm.prototype.hiddenCell) {
						continue;
					}
					var bit;
					if (bitIndex < dataBits.length) {
						bit = dataBits.charCodeAt(bitIndex) - 48;
						++bitIndex;
					} else {
						bit = 0;
					}
					if ($.ig.QRMask.prototype.shouldMaskTheBit(mask, row, symbolCol)) {
						bit ^= 1;
					}
					matrix[row][symbolCol] = bit;
				}
				row += direction;
			}
			direction = -direction;
			row += direction;
			col -= 2;
		}
		return $.ig.QRCodeEncoder.prototype.rotate(matrix);
	}
	,
	rotate: function (matrix) {
		var resultMatrix = new Array(matrix.length);
		for (var row = 0; row < matrix.length; row++) {
			for (var col = 0; col < (matrix[row]).length; col++) {
				if (resultMatrix[col] == null) {
					resultMatrix[col] = new Array((matrix[row]).length);
				}
				resultMatrix[col][row] = matrix[row][col];
			}
		}
		return resultMatrix;
	}
	,
	createEmptyMatrix: function (length) {
		var matrix = new Array(length);
		for (var i = 0; i < length; i++) {
			matrix[i] = new Array(length);
			for (var j = 0; j < length; j++) {
				matrix[i][j] = $.ig.QRCodeEncoder.prototype._emptyCell;
			}
		}
		return matrix;
	}
	,
	calculateMask: function (dataBitsString, modulesNumberPerSide, level, version) {
		var minPenalty = 0x7FFFFFFF;
		var bestMask = 0;
		for (var mask = $.ig.QRMask.prototype.minMaskIndex; mask < $.ig.QRMask.prototype.maxMaskIndex; mask++) {
			var matrix = $.ig.QRCodeEncoder.prototype.createFunctionPatternsMatrix(modulesNumberPerSide, version);
			matrix = $.ig.QRCodeEncoder.prototype.fillFormatInfo(level, mask, matrix);
			matrix = $.ig.QRCodeEncoder.prototype.fillVersionInfo(version, matrix);
			matrix = $.ig.QRCodeEncoder.prototype.fillDataBits(dataBitsString, mask, matrix);
			var penalty = $.ig.QRMask.prototype.getPenalty(matrix);
			if (penalty < minPenalty) {
				minPenalty = penalty;
				bestMask = mask;
			}
		}
		return bestMask;
	}
	,
	addErrorCorrectionCodewords: function (maxDataCodewords, maxCodewordsNumber, rsEccCodewords, rsBlockOrderTemp) {
		var ptr = 0;
		var dataCodewords = new Array(maxDataCodewords.length);
		for (var i = 0; i < maxDataCodewords.length; i++) {
			var dataCodeword = maxDataCodewords[i];
			dataCodewords[ptr] = $.ig.Number.prototype.parseInt($.ig.BarcodeAlgorithm.prototype.baseConvert(10, dataCodeword, 2));
			ptr++;
		}
		var rsBlockOrderLength = 1;
		for (var i1 = 1; i1 < 128; i1++) {
			if (rsBlockOrderTemp.length == i1 || rsBlockOrderTemp[i1] == 0) {
				rsBlockOrderLength = i1;
				break;
			}
		}
		var rsBlockOrder = new Array(rsBlockOrderLength);
		$.ig.util.arrayCopy1(rsBlockOrderTemp, 0, rsBlockOrder, 0, rsBlockOrderLength);
		var rs = new $.ig.QRReedSolomon();
		var dataAndEccBytes = rs.generateErrorCorrectionCodewords(dataCodewords, rsEccCodewords[0], rsBlockOrder, maxDataCodewords.length, maxCodewordsNumber);
		if (dataAndEccBytes == null) {
			return null;
		}
		var result = new Array(maxCodewordsNumber);
		for (var k = 0; k < dataAndEccBytes.length; k++) {
			result[k] = $.ig.BarcodeAlgorithm.prototype.baseConvert(2, dataAndEccBytes[k].toString(), 10).padLeft(8, '0');
		}
		return result;
	}
	,
	divideIntoCodewords: function (bitStream, maxDataBits) {
		var ptr = 0;
		var codewords = new Array($.ig.intDivide(bitStream.length, 8));
		while (ptr < bitStream.length) {
			var word = new $.ig.StringBuilder(0);
			for (var k = 0; k < 8; k++) {
				word.append1(bitStream.charAt(ptr + k));
			}
			codewords[$.ig.intDivide(ptr, 8)] = word.toString();
			ptr += 8;
		}
		if ($.ig.intDivide(maxDataBits, 8) < codewords.length) {
			var errorMsg = $.ig.SR.prototype.getString("InvalidVersion");
			var e = new $.ig.ArgumentException(1, errorMsg);
			throw e;
		}
		return codewords;
	}
	,
	fillWidthPads: function (codewords, maxDataBits) {
		var maxLength = $.ig.intDivide(maxDataBits, 8);
		if (codewords.length == maxLength) {
			return codewords;
		}
		var maxBitCodewords = new Array(maxLength);
		$.ig.util.arrayCopy1(codewords, 0, maxBitCodewords, 0, codewords.length);
		var flag = true;
		var ptr = codewords.length;
		while (ptr < maxBitCodewords.length) {
			maxBitCodewords[ptr] = flag ? "11101100" : "00010001";
			flag = !(flag);
			ptr++;
		}
		return maxBitCodewords;
	}
	,
	convertData: function (data, version, mode) {
		var versionIndex = version < 10 ? 0 : version < 27 ? 1 : 2;
		var characterCountIndicatorBits = mode != $.ig.EncodingMode.prototype.undefined ? $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[mode][versionIndex] : 0;
		var result = new $.ig.List$1(String, 0);
		switch (mode) {
			case $.ig.EncodingMode.prototype.numeric:
				var digitsNumber = $.ig.BarcodeAlgorithm.prototype.countConsecutiveDigits(data, 0);
				if (digitsNumber != data.length) {
					var errorMsg = $.ig.util.replace($.ig.SR.prototype.getString("WrongCompactionMode"), "{0}", "Numeric");
					var ex = new $.ig.ArgumentException(1, errorMsg);
					throw ex;
				}
				var length = data.length;
				this.dataSize().add(length);
				var r = length % 3 == 0 ? 0 : length % 3 == 1 ? 4 : 7;
				result.add(($.ig.QRModeIndicator.prototype.numeric).toString().padLeft(4, '0'));
				result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, this.dataSize().__inner[0].toString(), 10).padLeft(characterCountIndicatorBits, '0'));
				result.add(this.encodeNumeric(data, r));
				break;
			case $.ig.EncodingMode.prototype.alphanumeric:
				result.add(($.ig.QRModeIndicator.prototype.alphanumeric).toString().padLeft(4, '0'));
				if (this.fnc1Mode() != $.ig.Fnc1Mode.prototype.none) {
					data = this.escapePercentCharacter(data, $.ig.QRCodeEncoder.prototype.getFnc1Character($.ig.EncodingMode.prototype.alphanumeric), 0);
				}
				this.dataSize().add(data.length);
				result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, this.dataSize().__inner[0].toString(), 10).padLeft(characterCountIndicatorBits, '0'));
				result.add(this.encodeAlphanumeric(data));
				break;
			case $.ig.EncodingMode.prototype.byte1:
				result.add(($.ig.QRModeIndicator.prototype.byte1).toString().padLeft(4, '0'));
				if (this.fnc1Mode() != $.ig.Fnc1Mode.prototype.none) {
					data = this.escapePercentCharacter(data, $.ig.QRCodeEncoder.prototype.getFnc1Character($.ig.EncodingMode.prototype.byte1), 0);
				}
				var encodedData = this.encodeByte(data);
				var bytesCount = $.ig.intDivide(encodedData.length, 8);
				this.dataSize().add(bytesCount);
				result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, bytesCount.toString(), 10).padLeft(characterCountIndicatorBits, '0'));
				result.add(encodedData);
				break;
			case $.ig.EncodingMode.prototype.kanji:
				result.add(($.ig.QRModeIndicator.prototype.kanji).toString().padLeft(4, '0'));
				this.dataSize().add(data.length);
				result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, this.dataSize().__inner[0].toString(), 10).padLeft(characterCountIndicatorBits, '0'));
				result.add(this.encodeKanji(data));
				break;
			default: return this.encodeMixed(data, version);
		}
		return result;
	}
	,
	getFnc1Character: function (mode) {
		if (mode == $.ig.EncodingMode.prototype.byte1) {
			return '\u001d';
		}
		return '%';
	}
	,
	getApplicationIndicatorCodeword: function (applicationIdicator) {
		if ($.ig.QRCodeEncoder.prototype.isApplicationIndicatorValid(applicationIdicator)) {
			if (applicationIdicator.length == 2) {
				return $.ig.BarcodeAlgorithm.prototype.baseConvert(2, applicationIdicator, 10).padLeft(8, '0');
			}
			if (applicationIdicator.length == 1) {
				var asciiAI = applicationIdicator.charAt(0);
				var result = (asciiAI.charCodeAt(0) + 100).toString();
				return $.ig.BarcodeAlgorithm.prototype.baseConvert(2, result, 10).padLeft(8, '0');
			}
		}
		return String.empty();
	}
	,
	escapePercentCharacter: function (data, fnc1, currentIndex) {
		var escapedPersent = new $.ig.StringBuilder(0);
		for (var k = 0; k < data.length; k++) {
			var c = data.charAt(k);
			if (c.equals(fnc1) && this._fnc1Positions.contains(k + currentIndex) == false) {
				escapedPersent.append1(c);
			}
			escapedPersent.append1(c);
		}
		return escapedPersent.toString();
	}
	,
	getEciHeader: function (eciNumber) {
		var eciIndicator = ($.ig.QRModeIndicator.prototype.eci).toString().padLeft(4, '0');
		var eciAssignmentNumber;
		var eciBitsNumber;
		if (eciNumber < 128) {
			eciBitsNumber = 7;
			eciAssignmentNumber = "0";
		} else if (eciNumber < 16384) {
			eciBitsNumber = 8 + 6;
			eciAssignmentNumber = "10";
		} else {
			eciBitsNumber = 8 + 8 + 5;
			eciAssignmentNumber = "110";
		}
		var eciBinaryNumber = $.ig.BarcodeAlgorithm.prototype.baseConvert(2, eciNumber.toString(), 10).padLeft(eciBitsNumber, '0');
		eciAssignmentNumber += eciBinaryNumber;
		return eciIndicator + eciAssignmentNumber;
	}
	,
	encodeMixed: function (data, version) {
		var sequence = new $.ig.EncodingSequence(1, new $.ig.List$1($.ig.EncodingMode.prototype.$type, 0), new $.ig.List$1($.ig.Number.prototype.$type, 0));
		var currentMode = this.selectInitialMode(data, version);
		var ptr = 0;
		while (ptr < data.length) {
			var lastMode = $.ig.QRCodeEncoder.prototype.getLastEncodingType(sequence);
			var newMode;
			var shouldAddByteCharacter = false;
			;
			switch (currentMode) {
				case $.ig.EncodingMode.prototype.byte1:
					sequence = this.whileInByte(data, version, ptr, sequence);
					newMode = $.ig.QRCodeEncoder.prototype.getLastEncodingType(sequence);
					if (newMode != $.ig.EncodingMode.prototype.byte1 && newMode != $.ig.EncodingMode.prototype.undefined) {
						currentMode = newMode;
						ptr += sequence.bytesNumber().__inner[sequence.bytesNumber().count() - 1];
						shouldAddByteCharacter = false;
						break;
					}
					shouldAddByteCharacter = true;
					break;
				case $.ig.EncodingMode.prototype.alphanumeric:
					sequence = this.whileInAlphanumeric(data, version, ptr, sequence);
					newMode = $.ig.QRCodeEncoder.prototype.getLastEncodingType(sequence);
					if (newMode != $.ig.EncodingMode.prototype.alphanumeric && newMode != $.ig.EncodingMode.prototype.undefined) {
						currentMode = newMode;
						ptr += sequence.bytesNumber().__inner[sequence.bytesNumber().count() - 1];
						shouldAddByteCharacter = false;
						break;
					}
					shouldAddByteCharacter = true;
					break;
				case $.ig.EncodingMode.prototype.numeric:
					sequence = this.whileInNumeric(data, ptr, sequence);
					newMode = $.ig.QRCodeEncoder.prototype.getLastEncodingType(sequence);
					if (newMode != $.ig.EncodingMode.prototype.numeric && newMode != $.ig.EncodingMode.prototype.undefined) {
						currentMode = newMode;
						ptr += sequence.bytesNumber().__inner[sequence.bytesNumber().count() - 1];
						shouldAddByteCharacter = false;
						break;
					}
					shouldAddByteCharacter = true;
					break;
				case $.ig.EncodingMode.prototype.kanji:
					sequence = this.whileInKanji(data, ptr, sequence);
					newMode = $.ig.QRCodeEncoder.prototype.getLastEncodingType(sequence);
					if (newMode != $.ig.EncodingMode.prototype.kanji && newMode != $.ig.EncodingMode.prototype.undefined) {
						currentMode = newMode;
						ptr += sequence.bytesNumber().__inner[sequence.bytesNumber().count() - 1];
						shouldAddByteCharacter = false;
						break;
					}
					shouldAddByteCharacter = true;
					break;
			}
			if (shouldAddByteCharacter) {
				if (lastMode != currentMode) {
					sequence.encodingType().add($.ig.EncodingMode.prototype.getBox(currentMode));
					sequence.bytesNumber().add(0);
				}
				sequence.bytesNumber().__inner[sequence.bytesNumber().count() - 1]++;
				ptr++;
			}
		}
		return this.appendSequences(sequence, data, version);
	}
	,
	appendSequences: function (sequence, data, version) {
		var result = new $.ig.List$1(String, 0);
		var versionIndex = version < 10 ? 0 : version < 27 ? 1 : 2;
		var ptr = 0;
		for (var k = 0; k < sequence.encodingType().count(); k++) {
			if (sequence.bytesNumber().__inner[k] == 0) {
				continue;
			}
			var modeSequence = $.ig.util.getEnumValue(sequence.encodingType().item(k));
			var dataSequence = data.substr(ptr, sequence.bytesNumber().__inner[k]);
			if ((modeSequence == $.ig.EncodingMode.prototype.alphanumeric || modeSequence == $.ig.EncodingMode.prototype.byte1) && this.fnc1Mode() != $.ig.Fnc1Mode.prototype.none) {
				dataSequence = this.escapePercentCharacter(dataSequence, $.ig.QRCodeEncoder.prototype.getFnc1Character(modeSequence), ptr);
			}
			var characterCountIndicatorBits;
			switch (modeSequence) {
				case $.ig.EncodingMode.prototype.kanji:
					result.add(($.ig.QRModeIndicator.prototype.kanji).toString().padLeft(4, '0'));
					characterCountIndicatorBits = $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[$.ig.EncodingMode.prototype.kanji][versionIndex];
					this.dataSize().add(dataSequence.length);
					result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, dataSequence.length.toString(), 10).padLeft(characterCountIndicatorBits, '0'));
					result.add(this.encodeKanji(dataSequence));
					break;
				case $.ig.EncodingMode.prototype.byte1:
					result.add(($.ig.QRModeIndicator.prototype.byte1).toString().padLeft(4, '0'));
					characterCountIndicatorBits = $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[$.ig.EncodingMode.prototype.byte1][versionIndex];
					var encodedData = this.encodeByte(dataSequence);
					var bytesNumber = $.ig.intDivide(encodedData.length, 8);
					this.dataSize().add(bytesNumber);
					result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, bytesNumber.toString(), 10).padLeft(characterCountIndicatorBits, '0'));
					result.add(encodedData);
					break;
				case $.ig.EncodingMode.prototype.alphanumeric:
					result.add(($.ig.QRModeIndicator.prototype.alphanumeric).toString().padLeft(4, '0'));
					characterCountIndicatorBits = $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[$.ig.EncodingMode.prototype.alphanumeric][versionIndex];
					this.dataSize().add(dataSequence.length);
					result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, dataSequence.length.toString(), 10).padLeft(characterCountIndicatorBits, '0'));
					result.add(this.encodeAlphanumeric(dataSequence));
					break;
				case $.ig.EncodingMode.prototype.numeric:
					result.add(($.ig.QRModeIndicator.prototype.numeric).toString().padLeft(4, '0'));
					characterCountIndicatorBits = $.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber[$.ig.EncodingMode.prototype.numeric][versionIndex];
					this.dataSize().add(dataSequence.length);
					result.add($.ig.BarcodeAlgorithm.prototype.baseConvert(2, dataSequence.length.toString(), 10).padLeft(characterCountIndicatorBits, '0'));
					var length = dataSequence.length;
					var r = length % 3 == 0 ? 0 : length % 3 == 1 ? 4 : 7;
					result.add(this.encodeNumeric(dataSequence, r));
					break;
			}
			ptr += dataSequence.length;
		}
		return result;
	}
	,
	whileInKanji: function (data, ptr, sequence) {
		if (data.length <= ptr) {
			return sequence;
		}
		var subString = data.substr(ptr, 1);
		if (!this.areAllKanji(subString)) {
			var bytes = this.kanjiEncoding().getBytes1(subString);
			if (bytes.length < 2) {
				bytes = this.qrEncoding().getBytes1(subString);
				if (bytes.length == 1) {
					if ($.ig.QRCodeEncoder.prototype.isAscii(bytes[0])) {
						sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.byte1));
						sequence.bytesNumber().add(1);
					}
				}
				return sequence;
			}
		}
		return sequence;
	}
	,
	whileInNumeric: function (data, start, sequence) {
		if (data.length <= start) {
			return sequence;
		}
		var areEncoded = false;
		var kanjiBytes = this.kanjiEncoding().getBytes1(data.charAt(start).toString());
		var couldBeKanji = this.couldBeKanji();
		if (couldBeKanji && $.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable(kanjiBytes)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.kanji));
			sequence.bytesNumber().add(1);
			areEncoded = true;
		}
		var currentBitBytes = this.qrEncoding().getBytes1(data.charAt(start).toString());
		var currentByte = currentBitBytes.length > 0 ? currentBitBytes[0] : $.ig.QRCodeEncoder.prototype._invalidValue;
		if (!areEncoded && $.ig.QRCodeEncoder.prototype.isInByteExclSubset(currentByte)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.byte1));
			sequence.bytesNumber().add(1);
			areEncoded = true;
		}
		if (!areEncoded && $.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(currentByte)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.alphanumeric));
			sequence.bytesNumber().add(1);
		}
		return sequence;
	}
	,
	whileInAlphanumeric: function (data, version, start, sequence) {
		if (data.length <= start) {
			return sequence;
		}
		var areEncoded = false;
		var couldBeKanji = this.couldBeKanji();
		var kanjiBytes = this.kanjiEncoding().getBytes1(data.charAt(start).toString());
		if (couldBeKanji && $.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable(kanjiBytes)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.kanji));
			sequence.bytesNumber().add(1);
			areEncoded = true;
		}
		var currentBitBytes = this.qrEncoding().getBytes1(data.charAt(start).toString());
		var currentByte = currentBitBytes.length > 0 ? currentBitBytes[0] : $.ig.QRCodeEncoder.prototype._invalidValue;
		if (!areEncoded && $.ig.QRCodeEncoder.prototype.isInByteExclSubset(currentByte)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.byte1));
			sequence.bytesNumber().add(1);
			areEncoded = true;
		}
		var charsNumber = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 13, 15, 17 ], version);
		var exclusiveByte = this.checkBytesTypeByVersion(charsNumber, "N", data, start);
		if (!areEncoded && data.length > charsNumber + start) {
			currentBitBytes = this.qrEncoding().getBytes1(data.charAt(start + charsNumber).toString());
			currentByte = currentBitBytes.length > 0 ? currentBitBytes[0] : $.ig.QRCodeEncoder.prototype._invalidValue;
			if (exclusiveByte == $.ig.QRCodeEncoder.prototype._allInclusive && $.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(currentByte)) {
				sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.numeric));
				sequence.bytesNumber().add(charsNumber);
			}
		}
		return sequence;
	}
	,
	whileInByte: function (data, version, start, sequence) {
		if (data.length <= start) {
			return sequence;
		}
		var couldBeKanji = this.couldBeKanji();
		var areEncoded = false;
		var kanjiBytes = this.kanjiEncoding().getBytes1(data.charAt(start).toString());
		if (couldBeKanji && $.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable(kanjiBytes)) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.kanji));
			sequence.bytesNumber().add(1);
			areEncoded = true;
		}
		var charsNumber = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 11, 15, 16 ], version);
		var exclusiveByte = this.checkBytesTypeByVersion(charsNumber, "EA", data, start);
		if (!areEncoded && exclusiveByte == $.ig.QRCodeEncoder.prototype._allInclusive) {
			sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.alphanumeric));
			sequence.bytesNumber().add(charsNumber);
			areEncoded = true;
		}
		var currentBitBytes;
		var currentByte;
		charsNumber = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 6, 8, 9 ], version);
		exclusiveByte = this.checkBytesTypeByVersion(charsNumber, "N", data, start);
		if (!areEncoded && data.length > start + charsNumber) {
			currentBitBytes = this.qrEncoding().getBytes1(data.charAt(start + charsNumber).toString());
			currentByte = currentBitBytes.length > 0 ? currentBitBytes[0] : $.ig.QRCodeEncoder.prototype._invalidValue;
			if (exclusiveByte == $.ig.QRCodeEncoder.prototype._allInclusive && $.ig.QRCodeEncoder.prototype.isInByteExclSubset(currentByte)) {
				sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.numeric));
				sequence.bytesNumber().add(charsNumber);
				areEncoded = true;
			}
		}
		charsNumber = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 6, 7, 8 ], version);
		exclusiveByte = this.checkBytesTypeByVersion(charsNumber, "N", data, start);
		if (!areEncoded && data.length > start + charsNumber) {
			currentBitBytes = this.qrEncoding().getBytes1(data.charAt(start + charsNumber).toString());
			currentByte = currentBitBytes.length > 0 ? currentBitBytes[0] : $.ig.QRCodeEncoder.prototype._invalidValue;
			if (exclusiveByte == $.ig.QRCodeEncoder.prototype._allInclusive && ($.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(currentByte) || $.ig.QRCodeEncoder.prototype.isInNumericExclSubset(currentByte))) {
				sequence.encodingType().add($.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.numeric));
				sequence.bytesNumber().add(charsNumber);
			}
		}
		return sequence;
	}
	,
	getCharsNumberByVersion: function (vesrionApplicableArray, version) {
		var charsNumberToCheck;
		if (version < 9) {
			charsNumberToCheck = vesrionApplicableArray[0];
		} else if (version < 27) {
			charsNumberToCheck = vesrionApplicableArray[1];
		} else {
			charsNumberToCheck = vesrionApplicableArray[2];
		}
		return charsNumberToCheck;
	}
	,
	getLastEncodingType: function (encodingSequence) {
		if (encodingSequence.encodingType().count() == 0) {
			return $.ig.EncodingMode.prototype.undefined;
		}
		return $.ig.util.getEnumValue(encodingSequence.encodingType().item(encodingSequence.encodingType().count() - 1));
	}
	,
	selectInitialMode: function (data, version) {
		var couldBeKanji = this.couldBeKanji();
		if (couldBeKanji && this.hasKanjiCharacters(data) && this.areAllKanji(data)) {
			return $.ig.EncodingMode.prototype.kanji;
		}
		if (couldBeKanji && $.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable(this.kanjiEncoding().getBytes1(data))) {
			return this.hasKanjiCharacters(data.charAt(0).toString()) ? $.ig.EncodingMode.prototype.kanji : $.ig.EncodingMode.prototype.byte1;
		}
		if ($.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(data.charAt(0).charCodeAt(0))) {
			var charsNumber = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 6, 7, 8 ], version);
			var exclusiveByte = this.checkBytesTypeByVersion(charsNumber, "EA", data.substr(1, data.length - 1), 0);
			if (exclusiveByte != $.ig.QRCodeEncoder.prototype._allInclusive && exclusiveByte != $.ig.QRCodeEncoder.prototype._invalidValue && $.ig.QRCodeEncoder.prototype.isInByteExclSubset(exclusiveByte)) {
				return $.ig.EncodingMode.prototype.byte1;
			}
			if (exclusiveByte == $.ig.QRCodeEncoder.prototype._invalidValue) {
				exclusiveByte = this.checkBytesTypeByVersion(data.length - 1, "EA", data.substr(1, data.length - 1), 0);
				if ($.ig.QRCodeEncoder.prototype.isInByteExclSubset(exclusiveByte)) {
					return $.ig.EncodingMode.prototype.byte1;
				}
			}
			return $.ig.EncodingMode.prototype.alphanumeric;
		}
		if ($.ig.QRCodeEncoder.prototype.isInNumericExclSubset(data.charAt(0).charCodeAt(0))) {
			var charsNumber1 = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 4, 4, 5 ], version);
			var exclusiveByte1 = this.checkBytesTypeByVersion(charsNumber1, "N", data.substr(1, data.length - 1), 0);
			if (exclusiveByte1 != $.ig.QRCodeEncoder.prototype._invalidValue && exclusiveByte1 != $.ig.QRCodeEncoder.prototype._allInclusive && $.ig.QRCodeEncoder.prototype.isInByteExclSubset(exclusiveByte1)) {
				return $.ig.EncodingMode.prototype.byte1;
			}
			if (exclusiveByte1 == $.ig.QRCodeEncoder.prototype._invalidValue) {
				exclusiveByte1 = this.checkBytesTypeByVersion(data.length - 1, "N", data.substr(1, data.length - 1), 0);
				if ($.ig.QRCodeEncoder.prototype.isInByteExclSubset(exclusiveByte1)) {
					return $.ig.EncodingMode.prototype.byte1;
				}
			}
			charsNumber1 = $.ig.QRCodeEncoder.prototype.getCharsNumberByVersion([ 7, 8, 9 ], version);
			exclusiveByte1 = this.checkBytesTypeByVersion(charsNumber1, "N", data.substr(1, data.length - 1), 0);
			if (exclusiveByte1 != $.ig.QRCodeEncoder.prototype._invalidValue && exclusiveByte1 != $.ig.QRCodeEncoder.prototype._allInclusive && $.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(exclusiveByte1)) {
				return $.ig.EncodingMode.prototype.alphanumeric;
			}
			if (exclusiveByte1 == $.ig.QRCodeEncoder.prototype._invalidValue) {
				exclusiveByte1 = this.checkBytesTypeByVersion(data.length - 1, "N", data.substr(1, data.length - 1), 0);
				if ($.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(exclusiveByte1)) {
					return $.ig.EncodingMode.prototype.alphanumeric;
				}
			}
			return $.ig.EncodingMode.prototype.numeric;
		}
		return $.ig.EncodingMode.prototype.byte1;
	}
	,
	couldBeKanji: function () {
		var name = String.empty();
		var iencoding = $.ig.util.cast($.ig.IEncoding.prototype.$type, this.qrEncoding());
		if (iencoding != null) {
			name = iencoding.name();
		}
		return name.equals("iso-8859-1") || name.equals("shift_jis");
	}
	,
	checkBytesTypeByVersion: function (charsNumberToCheck, mode, data, start) {
		if (start + charsNumberToCheck > data.length) {
			return $.ig.QRCodeEncoder.prototype._invalidValue;
		}
		var kanjiBytes = new Array(data.length * 2);
		var isDoubleByte = false;
		if (mode.equals("K")) {
			var substr = data.substr(start, charsNumberToCheck);
			kanjiBytes = this.kanjiEncoding().getBytes1(substr);
			isDoubleByte = kanjiBytes.length == substr.length * 2;
		}
		var dataBytes = this.qrEncoding().getBytes1(data.substr(start));
		for (var aiPtr = 0; aiPtr < charsNumberToCheck; aiPtr++) {
			var dataByte = dataBytes[aiPtr];
			if (mode.equals("B") && !$.ig.QRCodeEncoder.prototype.isAscii(dataByte)) {
				return dataByte;
			}
			if (mode.equals("EB") && !$.ig.QRCodeEncoder.prototype.isInByteExclSubset(dataByte)) {
				return dataByte;
			}
			if (mode.equals("K") && (isDoubleByte ? !$.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable([ kanjiBytes[2 * aiPtr], kanjiBytes[2 * aiPtr + 1] ]) : true)) {
				return dataByte;
			}
			if (mode.equals("A") && !$.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(dataByte)) {
				return dataByte;
			}
			if (mode.equals("EA") && !$.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(dataByte) && !$.ig.QRCodeEncoder.prototype.isInNumericExclSubset(dataByte)) {
				return dataByte;
			}
			if (mode.equals("N") && !$.ig.QRCodeEncoder.prototype.isInNumericExclSubset(dataByte)) {
				return dataByte;
			}
		}
		return $.ig.QRCodeEncoder.prototype._allInclusive;
	}
	,
	encodeKanji: function (data) {
		if (!this.areAllKanji(data)) {
			throw new $.ig.ArgumentException(1, $.ig.util.replace($.ig.SR.prototype.getString("WrongCompactionMode"), "{0}", "Kanji"));
		}
		var dataBytes = this.kanjiEncoding().getBytes1(data);
		return $.ig.QRCodeEncoder.prototype.encodeKanjiBytes(dataBytes);
	}
	,
	encodeKanjiBytes: function (dataBytes) {
		var result = new $.ig.StringBuilder(0);
		var length = dataBytes.length;
		for (var i = 0; i < length; i += 2) {
			var byte1 = dataBytes[i] & $.ig.QRCodeAlgorithm.prototype.n;
			var byte2 = dataBytes[i + 1] & $.ig.QRCodeAlgorithm.prototype.n;
			var code = (byte1 << 8) | byte2;
			var subtracted = -1;
			if (code >= 33088 && code <= 40956) {
				subtracted = code - 33088;
			} else if (code >= 57408 && code <= 60351) {
				subtracted = code - 49472;
			}
			if (subtracted == -1) {
				throw new $.ig.ArgumentException(1, "Invalid byte sequence");
			}
			var encoded = ((subtracted >> 8) * 192) + (subtracted & $.ig.QRCodeAlgorithm.prototype.n);
			result.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, encoded.toString(), 10).padLeft(13, '0'));
		}
		return result.toString();
	}
	,
	encodeByte: function (data) {
		return $.ig.QRCodeEncoder.prototype.encodeByteBytes(this.qrEncoding().getBytes1(data));
	}
	,
	encodeByteBytes: function (dataBytes) {
		var result = new $.ig.StringBuilder(0);
		for (var i = 0; i < dataBytes.length; i++) {
			var bit = dataBytes[i];
			result.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, (bit & $.ig.QRCodeAlgorithm.prototype.n).toString(), 10).padLeft(8, '0'));
		}
		return result.toString();
	}
	,
	encodeNumeric: function (data, bitsToFill) {
		var dataBytes = this.qrEncoding().getBytes1(data);
		return $.ig.QRCodeEncoder.prototype.encodeNumericBytes(dataBytes, bitsToFill);
	}
	,
	encodeNumericBytes: function (dataBytes, bitsToFill) {
		var numericResult = new $.ig.StringBuilder(0);
		var ptr = 0;
		while (ptr < dataBytes.length) {
			var remainder = dataBytes.length - ptr;
			var triad = String.empty();
			if (remainder >= 3) {
				triad = "" + String.fromCharCode(dataBytes[ptr]) + String.fromCharCode(dataBytes[ptr + 1]) + String.fromCharCode(dataBytes[ptr + 2]);
				numericResult.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, triad, 10).padLeft(10, '0'));
				ptr += 3;
				continue;
			}
			for (var k = 0; k < remainder; k++) {
				triad += "" + String.fromCharCode(dataBytes[ptr + k]);
			}
			numericResult.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, triad, 10).padLeft(bitsToFill, '0'));
			break;
		}
		return numericResult.toString();
	}
	,
	encodeAlphanumeric: function (data) {
		var dataBytes = this.qrEncoding().getBytes1(data);
		return $.ig.QRCodeEncoder.prototype.encodeAlphanumericBytes(dataBytes);
	}
	,
	encodeAlphanumericBytes: function (dataBytes) {
		var encodingAlphanumercTable = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		for (var i = 0; i < dataBytes.length; i++) {
			var current = dataBytes[i];
			if (!$.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(current) && !$.ig.QRCodeEncoder.prototype.isInNumericExclSubset(current)) {
				var errorMsg = $.ig.util.replace($.ig.SR.prototype.getString("WrongCompactionMode"), "{0}", "Alphanumeric");
				throw new $.ig.ArgumentException(1, errorMsg);
			}
			var currentValue = $.ig.QRCodeEncoder.prototype.encodeAlphanumericValue(current);
			encodingAlphanumercTable.item(current, currentValue);
		}
		var result = new $.ig.StringBuilder(0);
		var ptr = 0;
		while (ptr < dataBytes.length) {
			var number;
			if (dataBytes.length - ptr >= 2) {
				var pair = [ dataBytes[ptr], dataBytes[ptr + 1] ];
				number = encodingAlphanumercTable.item(pair[0]) * 45 + encodingAlphanumercTable.item(pair[1]);
				ptr += 2;
			} else {
				number = encodingAlphanumercTable.item(dataBytes[ptr]);
				ptr++;
			}
			result.append5($.ig.BarcodeAlgorithm.prototype.baseConvert(2, number.toString(), 10).padLeft(ptr % 2 == 0 ? 11 : 6, '0'));
		}
		return result.toString();
	}
	,
	encodeAlphanumericValue: function (index) {
		var result = 0;
		if (index >= 48 && index < 58) {
			result = index - 48;
		} else {
			if (index >= 65 && index < 91) {
				result = index - 55;
			} else {
				if (index == 32) {
					result = 36;
				}
				if (index == 36) {
					result = 37;
				}
				if (index == 37) {
					result = 38;
				}
				if (index == 42) {
					result = 39;
				}
				if (index == 43) {
					result = 40;
				}
				if (index == 45) {
					result = 41;
				}
				if (index == 46) {
					result = 42;
				}
				if (index == 47) {
					result = 43;
				}
				if (index == 58) {
					result = 44;
				}
			}
		}
		return result;
	}
	,
	hasKanjiCharacters: function (data) {
		for (var i = 0; i < data.length; i++) {
			var bytes = this.kanjiEncoding().getBytes1(data.charAt(i).toString());
			if (bytes.length == 2 && $.ig.QRCodeEncoder.prototype.areBytesKanjiEncodable(bytes)) {
				return true;
			}
		}
		return false;
	}
	,
	areAllKanji: function (data) {
		var bytes = this.kanjiEncoding().getBytes1(data);
		var length = bytes.length;
		if (length % 2 != 0) {
			return false;
		}
		for (var i = 0; i < length; i += 2) {
			var byte1 = bytes[i] & 255;
			if ((byte1 < 129 || byte1 > 159) && (byte1 < 224 || byte1 > 235)) {
				return false;
			}
		}
		return true;
	}
	,
	isInNumericExclSubset: function (input) {
		return input >= 48 && input <= 57;
	}
	,
	areBytesKanjiEncodable: function (bytes) {
		if (bytes.length < 2) {
			return false;
		}
		for (var i = 0; i < bytes.length; i += 2) {
			var byte1 = bytes[i] & 255;
			if ((byte1 >= 129 && byte1 <= 159) || (byte1 >= 224 && byte1 <= 235)) {
				return true;
			}
		}
		return false;
	}
	,
	isAscii: function (input) {
		return input >= 0 && input <= 255;
	}
	,
	isInByteExclSubset: function (input) {
		return $.ig.QRCodeEncoder.prototype.isAscii(input) && !$.ig.QRCodeEncoder.prototype.isInAlphanumericExclSubset(input) && !$.ig.QRCodeEncoder.prototype.isInNumericExclSubset(input);
	}
	,
	isInAlphanumericExclSubset: function (input) {
		return input >= 65 && input <= 90 || input == 32 || input == 36 || input == 37 || input == 42 || input == 43 || input == 45 || input == 46 || input == 47 || input == 58;
	}
	,
	isEciInRange: function (eci) {
		return eci == $.ig.QRCodeAlgorithm.prototype.defaultEciNumber || (eci >= $.ig.QRCodeAlgorithm.prototype.minEciNumber && eci <= $.ig.QRCodeAlgorithm.prototype.maxEciNumber);
	}
	,
	isApplicationIndicatorValid: function (ai) {
		return (ai.length == 2 && $.ig.util.isDigit1(ai, 0) && $.ig.util.isDigit1(ai, 1)) || (ai.length == 1 && ((ai.charAt(0).charCodeAt(0) >= 'a'.charCodeAt(0) && ai.charAt(0).charCodeAt(0) <= 'z'.charCodeAt(0)) || (ai.charAt(0).charCodeAt(0) >= 'A'.charCodeAt(0) && ai.charAt(0).charCodeAt(0) <= 'Z'.charCodeAt(0))));
	}
	,
	_barcode: null,
	barcode: function (value) {
		if (arguments.length === 1) {
			this._barcode = value;
			return value;
		} else {
			return this._barcode;
		}
	}
	,
	_qrEncoding: null,
	qrEncoding: function (value) {
		if (arguments.length === 1) {
			this._qrEncoding = value;
			return value;
		} else {
			return this._qrEncoding;
		}
	}
	,
	__kanjiEncoding: null,
	kanjiEncoding: function () {
		if (this.__kanjiEncoding == null) {
			this.__kanjiEncoding = this.getEncoding1("shift_jis");
		}
		return this.__kanjiEncoding;
	}
	,
	fnc1Mode: function () {
		return this.barcode().fnc1Mode();
	}
	,
	_fnc1Positions: null,
	_dataSize: null,
	dataSize: function (value) {
		if (arguments.length === 1) {
			this._dataSize = value;
			return value;
		} else {
			return this._dataSize;
		}
	}
	,
	__modeIndicator: 0,
	$type: new $.ig.Type('QRCodeEncoder', $.ig.BarcodeEncodedAlgorithm.prototype.$type)
}, true);

$.ig.util.defType('EncodingSequence', 'ValueType', {
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
	_encodingType: null,
	_bytesNumber: null,
	init1: function (initNumber, mode, number) {
		$.ig.ValueType.prototype.init.call(this);
		this._encodingType = mode;
		this._bytesNumber = number;
	},
	encodingType: function (value) {
		if (arguments.length === 1) {
			this._encodingType = value;
			return value;
		} else {
			return this._encodingType;
		}
	}
	,
	bytesNumber: function (value) {
		if (arguments.length === 1) {
			this._bytesNumber = value;
			return value;
		} else {
			return this._bytesNumber;
		}
	}
	,
	$type: new $.ig.Type('EncodingSequence', $.ig.ValueType.prototype.$type)
}, true);

$.ig.util.defType('QRMask', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	shouldMaskTheBit: function (mask, row, col) {
		var condition, temp;
		switch (mask) {
			case 0:
				condition = (row + col) & 1;
				break;
			case 1:
				condition = row & 1;
				break;
			case 2:
				condition = col % 3;
				break;
			case 3:
				condition = (row + col) % 3;
				break;
			case 4:
				condition = ((row >> 1) + ($.ig.intDivide(col, 3))) & 1;
				break;
			case 5:
				temp = row * col;
				condition = (temp & 1) + (temp % 3);
				break;
			case 6:
				temp = row * col;
				condition = (((temp & 1) + (temp % 3)) & 1);
				break;
			default:
				temp = row * col;
				condition = (((temp % 3) + ((row + col) & 1)) & 1);
				break;
		}
		return condition == 0;
	}
	,
	getPenalty: function (matrix) {
		return $.ig.QRMask.prototype.getFeature1(matrix) + $.ig.QRMask.prototype.getFeature2(matrix) + $.ig.QRMask.prototype.getFeature3(matrix) + $.ig.QRMask.prototype.getFeature4(matrix);
	}
	,
	getFeature4: function (matrix) {
		var darkCellsCount = 0;
		for (var row = 0; row < matrix.length; row++) {
			for (var col = 0; col < (matrix[row]).length; col++) {
				if (matrix[row][col] == 1) {
					darkCellsCount += 1;
				}
			}
		}
		var matrixCellsCount = 2 * matrix.length;
		var darkRatio = darkCellsCount / matrixCellsCount;
		return $.ig.truncate(Math.floor(Math.abs($.ig.truncate((darkRatio * 100 - 50)) / 5))) * $.ig.QRMask.prototype._n4;
	}
	,
	getFeature3: function (matrix) {
		var points = 0;
		for (var row = 0; row < matrix.length; row++) {
			for (var col = 0; col < (matrix[row]).length; col++) {
				if (col + 6 < matrix.length && matrix[row][col] == 1 && matrix[row][col + 1] == 0 && matrix[row][col + 2] == 1 && matrix[row][col + 3] == 1 && matrix[row][col + 4] == 1 && matrix[row][col + 5] == 0 && matrix[row][col + 6] == 1 && ((col + 10 < matrix.length && matrix[row][col + 7] == 0 && matrix[row][col + 8] == 0 && matrix[row][col + 9] == 0 && matrix[row][col + 10] == 0) || (col - 4 >= 0 && matrix[row][col - 1] == 0 && matrix[row][col - 2] == 0 && matrix[row][col - 3] == 0 && matrix[row][col - 4] == 0))) {
					points += $.ig.QRMask.prototype._n3;
				}
				if (row + 6 < (matrix[row]).length && matrix[row][col] == 1 && matrix[row + 1][col] == 0 && matrix[row + 2][col] == 1 && matrix[row + 3][col] == 1 && matrix[row + 4][col] == 1 && matrix[row + 5][col] == 0 && matrix[row + 6][col] == 1 && ((row + 10 < (matrix[row]).length && matrix[row + 7][col] == 0 && matrix[row + 8][col] == 0 && matrix[row + 9][col] == 0 && matrix[row + 10][col] == 0) || (row - 4 >= 0 && matrix[row - 1][col] == 0 && matrix[row - 2][col] == 0 && matrix[row - 3][col] == 0 && matrix[row - 4][col] == 0))) {
					points += $.ig.QRMask.prototype._n3;
				}
			}
		}
		return points;
	}
	,
	getFeature2: function (matrix) {
		var points = 0;
		for (var row = 0; row < matrix.length - 1; row++) {
			for (var col = 0; col < (matrix[row]).length - 1; col++) {
				var value = matrix[row][col];
				if (value == $.ig.QRCodeAlgorithm.prototype.hiddenCell) {
					continue;
				}
				if (value == matrix[row][col + 1] && value == matrix[row + 1][col] && value == matrix[row + 1][col + 1]) {
					points += $.ig.QRMask.prototype._n2;
				}
			}
		}
		return points;
	}
	,
	getFeature1: function (matrix) {
		return $.ig.QRMask.prototype.getAdjacentSameColorModules(matrix, true) + $.ig.QRMask.prototype.getAdjacentSameColorModules(matrix, false);
	}
	,
	getAdjacentSameColorModules: function (matrix, isRow) {
		var points = 0;
		var sameColorCellsNumber = 0;
		var prevColor = -1;
		var modulesNumCondition = 5;
		for (var i = 0; i < matrix.length; i++) {
			for (var j = 0; j < (matrix[i]).length; j++) {
				var color = isRow ? matrix[i][j] : matrix[j][i];
				if (color == $.ig.QRCodeAlgorithm.prototype.hiddenCell) {
					continue;
				}
				if (color == prevColor) {
					sameColorCellsNumber += 1;
					if (sameColorCellsNumber == modulesNumCondition) {
						points += $.ig.QRMask.prototype._n1;
					} else if (sameColorCellsNumber > modulesNumCondition) {
						points += 1;
					}
				} else {
					sameColorCellsNumber = 1;
					prevColor = color;
				}
			}
			sameColorCellsNumber = 0;
		}
		return points;
	}
	,
	$type: new $.ig.Type('QRMask', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BarcodeGrid', 'Panel', {
	_barsOffset: null,
	_isTextLonger: false,
	init: function () {
		this.__textSize = new $.ig.Size();
		$.ig.Panel.prototype.init.call(this);
		this.rowsDefinition(new $.ig.Collection$1($.ig.RowDefinition.prototype.$type));
		this.columnsDefinition(new $.ig.Collection$1($.ig.ColumnDefinition.prototype.$type));
		this.modules(new $.ig.List$1($.ig.Module.prototype.$type, 0));
		this.textSize(new $.ig.Size(1, 0, 0));
		this.textPositions(new $.ig.List$1($.ig.Module.prototype.$type, 0));
		this.figuresPath(new $.ig.Path());
		this.figuresPath().strokeThickness(0);
		var canvas = new $.ig.Canvas();
		canvas.children().add(this.figuresPath());
		this.children().add(canvas);
	},
	_figuresPath: null,
	figuresPath: function (value) {
		if (arguments.length === 1) {
			this._figuresPath = value;
			return value;
		} else {
			return this._figuresPath;
		}
	}
	,
	_modules: null,
	modules: function (value) {
		if (arguments.length === 1) {
			this._modules = value;
			return value;
		} else {
			return this._modules;
		}
	}
	,
	_textPositions: null,
	textPositions: function (value) {
		if (arguments.length === 1) {
			this._textPositions = value;
			return value;
		} else {
			return this._textPositions;
		}
	}
	,
	__textSize: null,
	textSize: function (value) {
		if (arguments.length === 1) {
			this.__textSize = value;
			return value;
		} else {
			return this.__textSize;
		}
	}
	,
	__rowPixelHeights: null,
	rowPixelHeights: function (value) {
		if (arguments.length === 1) {
			this.__rowPixelHeights = value;
			return value;
		} else {
			return this.__rowPixelHeights;
		}
	}
	,
	__columnPixelWidths: null,
	columnPixelWidths: function (value) {
		if (arguments.length === 1) {
			this.__columnPixelWidths = value;
			return value;
		} else {
			return this.__columnPixelWidths;
		}
	}
	,
	__autoRowsNumber: 0,
	autoRowsNumber: function (value) {
		if (arguments.length === 1) {
			this.__autoRowsNumber = value;
			return value;
		} else {
			return this.__autoRowsNumber;
		}
	}
	,
	__barcode: null,
	barcode: function (value) {
		if (arguments.length === 1) {
			this.__barcode = value;
			return value;
		} else {
			return this.__barcode;
		}
	}
	,
	__columnsDefinition: null,
	columnsDefinition: function (value) {
		if (arguments.length === 1) {
			this.__columnsDefinition = value;
			return value;
		} else {
			return this.__columnsDefinition;
		}
	}
	,
	__rowsDefinition: null,
	rowsDefinition: function (value) {
		if (arguments.length === 1) {
			this.__rowsDefinition = value;
			return value;
		} else {
			return this.__rowsDefinition;
		}
	}
	,
	arrangeBarcodeGrid: function (finalSize) {
		if (this.barcode() == null) {
			return finalSize;
		}
		var shouldHide = finalSize.width() == 0 || finalSize.height() == 0;
		var group = new $.ig.GeometryGroup();
		if (this.barcode().isValid() && shouldHide == false) {
			var en = this.modules().getEnumerator();
			while (en.moveNext()) {
				var module = en.current();
				group.children().add(this.getBarGeomertry(module));
			}
		}
		var textBlockIndex = 0;
		var en1 = this.children().getEnumerator();
		while (en1.moveNext()) {
			var element = en1.current();
			var text = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
			if (text != null) {
				this.arrangeTextBlock(text, finalSize, shouldHide, this.textPositions().__inner[textBlockIndex]);
				textBlockIndex++;
			}
		}
		this.figuresPath().data(group);
		return finalSize;
	}
	,
	measureBarcodeGrid: function (availableSize) {
		var maxSize = new $.ig.Size(1, 1.7976931348623157E+308, 1.7976931348623157E+308);
		var en = this.children().getEnumerator();
		while (en.moveNext()) {
			var element = en.current();
			var textBlock = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
			if (textBlock != null) {
				this.textSize(this.barcode().view().getLabelSize(textBlock));
			}
		}
		if (this.barcode() != null) {
			var controlSize = this.correctControlSize(this.getControlSize(availableSize), availableSize);
			if (this.barcode().barsFillMode() == $.ig.BarsFillMode.prototype.ensureEqualSize) {
				this.areEnsureEqualSizeValidSettings(availableSize);
			}
			if (this.barcode().isValid()) {
				return controlSize;
			}
		}
		return availableSize;
	}
	,
	clear: function () {
		this.rowsDefinition().clear();
		this.columnsDefinition().clear();
		this.modules().clear();
		this.clearTextBlock();
		this.figuresPath().data(null);
	}
	,
	clearTextBlock: function () {
		var textBlocks = new $.ig.List$1($.ig.TextBlock.prototype.$type, 0);
		var en = this.children().getEnumerator();
		while (en.moveNext()) {
			var element = en.current();
			var text = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
			textBlocks.add(text);
		}
		var en1 = textBlocks.getEnumerator();
		while (en1.moveNext()) {
			var t = en1.current();
			this.children().remove(t);
		}
		this.textPositions().clear();
	}
	,
	areEnsureEqualSizeValidSettings: function (finalSize) {
		if (this.barcode().stretch() == $.ig.Stretch.prototype.none) {
			return true;
		}
		var controlSize = this.getSumSize();
		var errorMsg;
		if (controlSize.height() == 0 || controlSize.width() == 0) {
			if (controlSize.height() == 0 && this.barcode().isValid()) {
				errorMsg = $.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidHeight"), "{0}", this.rowsDefinition().count().toString()), "{1}", finalSize.height().toString());
			} else {
				errorMsg = $.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidWidth"), "{0}", this.columnsDefinition().count().toString()), "{1}", finalSize.width().toString());
			}
			if (this.barcode().isValid()) {
				var ex = new $.ig.ArgumentException(1, errorMsg);
				this.barcode().errorMessage(ex);
				return false;
			}
		}
		var doesFit = this.checkDoesFit(finalSize, controlSize);
		if (!doesFit) {
			errorMsg = $.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("SmallSize"), "{0}", finalSize.width().toString()), "{1}", finalSize.height().toString());
			if (this.barcode().isValid()) {
				var ex1 = new $.ig.ArgumentException(1, errorMsg);
				this.barcode().errorMessage(ex1);
				return false;
			}
		}
		if (this.barcode().isValid() == false) {
			var shouldClear = this.checkShouldClear(finalSize, controlSize);
			if (shouldClear) {
				this.barcode().clearWarnings();
				return false;
			}
		}
		return true;
	}
	,
	checkDoesFit: function (finalSize, controlSize) {
		if (controlSize.width() == 0 || controlSize.height() == 0) {
			return true;
		}
		switch (this.barcode().stretch()) {
			case $.ig.Stretch.prototype.uniformToFill:
				if (finalSize.width() <= controlSize.width()) {
					return finalSize.height() >= this.autoRowsNumber();
				}
				if (finalSize.height() <= controlSize.height()) {
					return finalSize.width() >= this.columnsDefinition().count();
				}
				return true;
			default: return (finalSize.width() >= this.columnsDefinition().count() && finalSize.height() >= this.autoRowsNumber());
		}
	}
	,
	checkShouldClear: function (finalSize, controlSize) {
		var minHeight = controlSize.height();
		var minWidth = controlSize.width();
		var doesFit = minHeight > 0 && minWidth > 0;
		if (this.barcode().stretch() == $.ig.Stretch.prototype.uniformToFill) {
			return (doesFit && (minWidth <= finalSize.width() || minHeight <= finalSize.height()));
		}
		return (doesFit && (minWidth <= finalSize.width() && minHeight <= finalSize.height()));
	}
	,
	getBarGeomertry: function (module) {
		var top = this._barsOffset.__y + this.getTopOffset(module.row());
		var left = this._barsOffset.__x + this.getLeftOffset(module.column());
		var width = this.getWidth(module.column(), module.colSpan());
		var height = this.getHeight(module.row(), module.rowSpan());
		var rect = new $.ig.Rect(0, left, top, width, height);
		var rectGeom = (function () {
			var $ret = new $.ig.RectangleGeometry();
			$ret.rect(rect);
			return $ret;
		}());
		return rectGeom;
	}
	,
	arrangeTextBlock: function (text, finalSize, shouldHide, textPosition) {
		var controlSize = this.getSumSize();
		var controlOffset = this.barcode().calculateOffset(finalSize, controlSize);
		if (this._isTextLonger) {
			controlOffset.__x -= this._barsOffset.__x;
		}
		var row = textPosition.row();
		var column = textPosition.column();
		var colSpan = textPosition.colSpan();
		var top = this.getTopOffset(row) + controlOffset.__y;
		var left = this.getLeftOffset(column) + controlOffset.__x;
		var width = shouldHide ? 0 : this.getWidth(column, colSpan);
		if (colSpan == this.columnsDefinition().count() && width < this.textSize().width() && this.barcode().isValid()) {
			width = this.textSize().width();
		}
		if (width == $.ig.BarcodeGrid.prototype._invalidValue) {
			width = controlSize.width();
		}
		var height = this.textSize().height();
		var offset = (width - this.textSize().width()) / 2;
		text.canvasLeft(left + offset);
		text.canvasTop(top);
	}
	,
	getLeftOffset: function (columnNumber) {
		var size = 0;
		if (this.columnPixelWidths() == null) {
			return size;
		}
		if (this.rowsDefinition().count() > 0) {
			for (var i = 0; i < columnNumber; i++) {
				size += this.columnPixelWidths().__inner[i];
			}
		}
		return size;
	}
	,
	getTopOffset: function (rowNumber) {
		var size = 0;
		if (this.rowPixelHeights() == null) {
			return size;
		}
		if (this.columnsDefinition().count() > 0) {
			for (var i = 0; i < rowNumber; i++) {
				size += this.rowPixelHeights().__inner[i];
			}
		}
		return size;
	}
	,
	correctControlSize: function (controlSize, availableSize) {
		this._isTextLonger = false;
		if (controlSize.width() < this.textSize().width() && controlSize.width() <= availableSize.width() && controlSize.width() != 0) {
			this._barsOffset = this.barcode().calculateOffset(this.textSize(), controlSize);
			controlSize.width(this.textSize().width());
			this._isTextLonger = true;
		} else if (controlSize.width() > availableSize.width() || controlSize.height() > availableSize.height()) {
			this._barsOffset = this.barcode().calculateOffset(availableSize, controlSize);
		} else {
			this._barsOffset = this.barcode().calculateOffset(availableSize, controlSize);
		}
		if (controlSize.width() > availableSize.width()) {
			controlSize.width(availableSize.width());
		}
		if (controlSize.height() > availableSize.height()) {
			controlSize.height(availableSize.height());
		}
		return controlSize;
	}
	,
	getControlSize: function (availableSize) {
		var gridSize = this.correctAspectRatio(availableSize);
		var calculatedSize = this.calculateSize(gridSize);
		return calculatedSize;
	}
	,
	correctAspectRatio: function (size) {
		if (size.width() == 0 || size.height() == 0) {
			return size;
		}
		var barsAspectRatio = this.barcode().calculateAspectRatio();
		var columnHeight = size.height();
		var hasText = false;
		if (this.barcode().textBlock() != null && this.barcode().textBlock().__visibility == $.ig.Visibility.prototype.visible && columnHeight > this.textSize().height() && !Number.isInfinity(columnHeight)) {
			columnHeight -= this.textSize().height();
			hasText = true;
		}
		var width, height;
		switch (this.barcode().stretch()) {
			case $.ig.Stretch.prototype.fill: return new $.ig.Size(1, size.width(), size.height());
			case $.ig.Stretch.prototype.none: return this.barcode().calculateDefaultSize(barsAspectRatio);
			case $.ig.Stretch.prototype.uniformToFill:
				if (columnHeight * barsAspectRatio > size.width()) {
					height = columnHeight;
					width = barsAspectRatio * height;
				} else {
					width = size.width();
					height = width / barsAspectRatio;
				}
				break;
			default:
				if (columnHeight * barsAspectRatio > size.width()) {
					width = size.width();
					height = width / barsAspectRatio;
				} else {
					height = columnHeight;
					width = barsAspectRatio * height;
				}
				break;
		}
		if (hasText) {
			height += this.textSize().height();
		}
		width = Math.floor(width + 0.5);
		height = Math.floor(height + 0.5);
		return new $.ig.Size(1, width, height);
	}
	,
	calculateSize: function (finalSize) {
		if (finalSize.width() == 0 || finalSize.height() == 0) {
			return finalSize;
		}
		var totalHeight = this.calculateHeight(finalSize.height());
		var totalWidth = this.calculateWidth(finalSize.width());
		return new $.ig.Size(1, totalWidth, totalHeight);
	}
	,
	getSumSize: function () {
		var sumWidth = this.getSumOfColumnsWidth();
		sumWidth = sumWidth == $.ig.BarcodeGrid.prototype._invalidValue ? 0 : sumWidth;
		var sumHeight = this.getSumOfRowsHeight();
		sumHeight = sumHeight == $.ig.BarcodeGrid.prototype._invalidValue ? 0 : sumHeight;
		return new $.ig.Size(1, sumWidth, sumHeight);
	}
	,
	getSumOfColumnsWidth: function () {
		if (this.columnPixelWidths() == null) {
			return $.ig.BarcodeGrid.prototype._invalidValue;
		}
		return $.ig.Enumerable.prototype.sum((this.columnPixelWidths()));
	}
	,
	getSumOfRowsHeight: function () {
		if (this.rowPixelHeights() == null) {
			return $.ig.BarcodeGrid.prototype._invalidValue;
		}
		return $.ig.Enumerable.prototype.sum((this.rowPixelHeights()));
	}
	,
	calculateHeight: function (totalHeight) {
		if (totalHeight == 0) {
			return totalHeight;
		}
		var rowStarsNumber = $.ig.BarcodeGrid.prototype.getRowsStarsNumber(this.rowsDefinition());
		this.saveRowHeights(totalHeight, rowStarsNumber);
		var finalTotalHeight;
		var counter = 1;
		do {
			finalTotalHeight = this.getSumOfRowsHeight();
			if (finalTotalHeight <= 0) {
				break;
			}
			if (finalTotalHeight > totalHeight) {
				var newHeight = totalHeight - counter;
				if (newHeight > 0) {
					this.saveRowHeights(newHeight, rowStarsNumber);
					counter++;
				} else {
					break;
				}
			}
		} while (finalTotalHeight > totalHeight);
		return finalTotalHeight;
	}
	,
	saveRowHeights: function (totalHeight, rowStarsNumber) {
		this.rowPixelHeights(new $.ig.List$1(Number, 0));
		if (rowStarsNumber == 0) {
			return;
		}
		totalHeight = totalHeight - this.textSize().height();
		if (totalHeight < 0) {
			totalHeight = 0;
		}
		this.autoRowsNumber(0);
		var starRowValue = totalHeight / rowStarsNumber;
		var prevTotalHeightPixels = 0;
		var currentRowStarValue = 0;
		var autoRowsIndexes = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		for (var k = 0; k < this.rowsDefinition().count(); k++) {
			var row = this.rowsDefinition().__inner[k];
			var heightPixels;
			if (row.height().isStar()) {
				switch (this.barcode().barsFillMode()) {
					case $.ig.BarsFillMode.prototype.fillSpace:
						currentRowStarValue += row.height().value();
						var totalHeightPixels = Math.round(starRowValue * currentRowStarValue);
						heightPixels = totalHeightPixels - prevTotalHeightPixels;
						prevTotalHeightPixels = totalHeightPixels;
						break;
					default:
						heightPixels = Math.floor(starRowValue * row.height().value());
						break;
				}
			} else {
				heightPixels = 0;
				this.autoRowsNumber(this.autoRowsNumber() + 1);
				autoRowsIndexes.add(k);
			}
			this.rowPixelHeights().add(heightPixels);
		}
		for (var k1 = 0; k1 < this.autoRowsNumber(); k1++) {
			this.rowPixelHeights().__inner[autoRowsIndexes.__inner[k1]] = this.textSize().height() / this.autoRowsNumber();
		}
	}
	,
	calculateWidth: function (totalWidth) {
		if (totalWidth == 0) {
			return totalWidth;
		}
		var columnStarsNumber = $.ig.BarcodeGrid.prototype.getColumnsStarsNumber(this.columnsDefinition());
		var maxPossibleWidth = totalWidth;
		if (this.barcode().barsFillMode() == $.ig.BarsFillMode.prototype.ensureEqualSize && columnStarsNumber > 0) {
			maxPossibleWidth = this.getMaxWidthForColumns(totalWidth);
		}
		this.saveColumnsWidths(maxPossibleWidth, columnStarsNumber);
		return maxPossibleWidth;
	}
	,
	getMaxWidthForColumns: function (totalWidth) {
		var remainder = totalWidth % this.columnsDefinition().count();
		return totalWidth - remainder;
	}
	,
	saveColumnsWidths: function (totalWidth, columnStarsNumber) {
		this.columnPixelWidths(new $.ig.List$1(Number, 0));
		if (columnStarsNumber == 0) {
			return;
		}
		var prevTotalWidthPixels = 0;
		var currentColumnStarValue = 0;
		var starColumnValue = totalWidth / columnStarsNumber;
		var en = this.columnsDefinition().getEnumerator();
		while (en.moveNext()) {
			var column = en.current();
			var widthPixels = 0;
			if (column.width().isStar()) {
				switch (this.barcode().barsFillMode()) {
					case $.ig.BarsFillMode.prototype.fillSpace:
						currentColumnStarValue += column.width().value();
						var totalWidthPixels = Math.round(starColumnValue * currentColumnStarValue);
						widthPixels = totalWidthPixels - prevTotalWidthPixels;
						prevTotalWidthPixels = totalWidthPixels;
						break;
					case $.ig.BarsFillMode.prototype.ensureEqualSize:
					default:
						widthPixels = Math.round(starColumnValue * column.width().value());
						break;
				}
			}
			this.columnPixelWidths().add(widthPixels);
		}
	}
	,
	getHeight: function (row, rowSpan) {
		if (this.rowPixelHeights() == null) {
			return 0;
		}
		if (this.rowsDefinition().count() > 0 && row + rowSpan <= this.rowsDefinition().count()) {
			var height = 0;
			for (var k = 0; k < rowSpan; k++) {
				height += this.rowPixelHeights().__inner[row + k];
			}
			return height;
		}
		return $.ig.BarcodeGrid.prototype._invalidValue;
	}
	,
	getWidth: function (column, colSpan) {
		if (this.columnPixelWidths() == null) {
			return 0;
		}
		if (this.columnsDefinition().count() > 0 && column + colSpan <= this.columnsDefinition().count()) {
			var width = 0;
			for (var k = 0; k < colSpan; k++) {
				width += this.columnPixelWidths().__inner[column + k];
			}
			return width;
		}
		return $.ig.BarcodeGrid.prototype._invalidValue;
	}
	,
	getRowsStarsNumber: function (rowDefinitionCollection) {
		return $.ig.Enumerable.prototype.sum$1($.ig.RowDefinition.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.RowDefinition.prototype.$type, rowDefinitionCollection, function (rDef) { return rDef.height().isStar(); }), function (rDef) { return rDef.height().value(); });
	}
	,
	getColumnsStarsNumber: function (columnDefinitionCollection) {
		return $.ig.Enumerable.prototype.sum$1($.ig.ColumnDefinition.prototype.$type, $.ig.Enumerable.prototype.where$1($.ig.ColumnDefinition.prototype.$type, columnDefinitionCollection, function (cDef) { return cDef.width().isStar(); }), function (cDef) { return cDef.width().value(); });
	}
	,
	$type: new $.ig.Type('BarcodeGrid', $.ig.Panel.prototype.$type)
}, true);

$.ig.util.defType('Module', 'Object', {
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
	init1: function (initNumber, row, column, rowSpan, colSpan) {
		$.ig.Object.prototype.init.call(this);
		this.row(row);
		this.column(column);
		this.rowSpan(rowSpan);
		this.colSpan(colSpan);
	},
	_row: 0,
	row: function (value) {
		if (arguments.length === 1) {
			this._row = value;
			return value;
		} else {
			return this._row;
		}
	}
	,
	_column: 0,
	column: function (value) {
		if (arguments.length === 1) {
			this._column = value;
			return value;
		} else {
			return this._column;
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
	_colSpan: 0,
	colSpan: function (value) {
		if (arguments.length === 1) {
			this._colSpan = value;
			return value;
		} else {
			return this._colSpan;
		}
	}
	,
	$type: new $.ig.Type('Module', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DataChangedEventArgs', 'EventArgs', {
	init: function (newData) {
		$.ig.EventArgs.prototype.init.call(this);
		this.newData(newData);
	},
	__newData: null,
	newData: function (value) {
		if (arguments.length === 1) {
			this.__newData = value;
			return value;
		} else {
			return this.__newData;
		}
	}
	,
	$type: new $.ig.Type('DataChangedEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('XamBarcode', 'Control', {
	createView: function () {
		return new $.ig.XamBarcodeView(this);
	}
	,
	onViewCreated: function (view) {
		this.view(view);
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
	staticInit: function () {
	},
	init: function () {
		this._layoutUpdated = false;
		this._isDataChanged = false;
		$.ig.Control.prototype.init.call(this);
		this.__viewport = $.ig.Rect.prototype.empty();
		var view = this.createView();
		this.onViewCreated(view);
		view.onInit();
		this.errorMessageText($.ig.SR.prototype.getString("ErrorMessageText"));
	},
	_layoutUpdated: false,
	_isDataChanged: false,
	__suspendDataUpdates: false,
	suspendDataUpdates: function (value) {
		if (arguments.length === 1) {
			this.__suspendDataUpdates = value;
			return value;
		} else {
			return this.__suspendDataUpdates;
		}
	}
	,
	__isDataFake: false,
	isDataFake: function (value) {
		if (arguments.length === 1) {
			this.__isDataFake = value;
			return value;
		} else {
			return this.__isDataFake;
		}
	}
	,
	__propertyExceptionString: null,
	propertyExceptionString: function (value) {
		if (arguments.length === 1) {
			this.__propertyExceptionString = value;
			return value;
		} else {
			return this.__propertyExceptionString;
		}
	}
	,
	__isValid: false,
	isValid: function (value) {
		if (arguments.length === 1) {
			if (value != this.__isValid) {
				this.__isValid = value;
			}
			return value;
		} else {
			return this.__isValid;
		}
	}
	,
	__viewport: null,
	viewport: function (value) {
		if (arguments.length === 1) {
			var oldViewport = this.__viewport;
			this.__viewport = value;
			if (!oldViewport.equals1(this.__viewport)) {
				this.onViewportChanged(oldViewport, this.__viewport);
			}
			return value;
		} else {
			return this.__viewport;
		}
	}
	,
	onViewportChanged: function (oldViewport, newViewport) {
		this.view().scheduleArrange();
	}
	,
	backingBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.backingBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.backingBrushProperty);
		}
	}
	,
	backingOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.backingOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.backingOutlineProperty);
		}
	}
	,
	backingStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.backingStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.backingStrokeThicknessProperty);
		}
	}
	,
	barBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.barBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.barBrushProperty);
		}
	}
	,
	fontBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.fontBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.fontBrushProperty);
		}
	}
	,
	font: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.fontProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.fontProperty);
		}
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.XamBarcode.prototype.backingBrushPropertyName:
			case $.ig.XamBarcode.prototype.backingOutlinePropertyName:
			case $.ig.XamBarcode.prototype.barBrushPropertyName:
				this.view().onBrushChanged(propertyName, oldValue, newValue);
				break;
		}
		switch (propertyName) {
			case $.ig.XamBarcode.prototype.backingBrushPropertyName:
			case $.ig.XamBarcode.prototype.backingOutlinePropertyName:
			case $.ig.XamBarcode.prototype.backingStrokeThicknessPropertyName:
			case $.ig.XamBarcode.prototype.barBrushPropertyName:
				this.view().scheduleArrange();
				break;
			case $.ig.XamBarcode.prototype.fontBrushPropertyName:
			case $.ig.XamBarcode.prototype.fontPropertyName:
				this.view().updateStyle();
				this.view().scheduleArrange();
				break;
		}
	}
	,
	data: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.dataProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.dataProperty);
		}
	}
	,
	onDataChanged1: function (d, e) {
		(d).onDataChanged(e.oldValue(), e.newValue());
	}
	,
	onDataChanged: function (oldValue, newValue) {
		if (!String.isNullOrEmpty(newValue)) {
			this.isDataFake(false);
		}
		if (this.suspendDataUpdates()) {
			return;
		}
		this.view().scheduleArrange();
		if (this.dataChanged != null) {
			this.dataChanged(this, new $.ig.DataChangedEventArgs(newValue));
		}
		this._isDataChanged = true;
	}
	,
	errorMessageText: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.errorMessageTextProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamBarcode.prototype.errorMessageTextProperty);
		}
	}
	,
	onErrorMessageTextChanged1: function (d, e) {
		(d).onErrorMessageTextChanged(e.oldValue(), e.newValue());
	}
	,
	onErrorMessageTextChanged: function (oldValue, newValue) {
	}
	,
	stretch: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamBarcode.prototype.stretchProperty, $.ig.Stretch.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamBarcode.prototype.stretchProperty));
		}
	}
	,
	onStretchChanged: function (d, e) {
		(d).onStretchChanged1($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onStretchChanged1: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	arrangeBarcode: function () {
		var isValid = this.validateParameters();
		if (isValid == false) {
			var ex;
			if (String.isNullOrEmpty(this.propertyExceptionString())) {
				ex = new $.ig.Error(1, this.errorMessageText());
			} else {
				ex = new $.ig.ArgumentException(1, this.propertyExceptionString());
				this.propertyExceptionString(String.empty());
			}
			this.errorMessage(ex);
		} else {
			this.clearWarnings();
		}
	}
	,
	errorMessage1: function (errorMessage) {
		this.isValid(false);
		var errorTextBlock = this.view().getErrorTextBlock();
		if (errorTextBlock != null) {
			errorTextBlock.text(errorMessage);
			errorTextBlock.__visibility = $.ig.Visibility.prototype.visible;
		} else {
			this.propertyExceptionString(errorMessage);
		}
	}
	,
	clearWarnings: function () {
		this.isValid(true);
		var errorTextBlock = this.view().getErrorTextBlock();
		errorTextBlock.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	validateData: function (data) {
		var isEmpty = String.isNullOrEmpty(data);
		return !isEmpty;
	}
	,
	validateParameters: function () {
		return this.validateData(this.data());
	}
	,
	onErrorMessageDisplaying: function (args) {
		if (this.errorMessageDisplaying != null) {
			this.errorMessageDisplaying(this, args);
		}
	}
	,
	exportVisualDataInternal: function (barcodeVisualData) {
		barcodeVisualData.viewport(this.viewport());
	}
	,
	errorMessage: function (e_) {
		var warning_ = $.ig.SR.prototype.getString("WarningString");
		var message = warning_ + (($.type(e_.message) === 'string') ? e_.message : e_.message());
		var args = new $.ig.ErrorMessageDisplayingEventArgs(message);
		this.onErrorMessageDisplaying(args);
		this.errorMessage1(args.errorMessage());
	}
	,
	calculateOffset: function (finalSize, controlSize) {
		var offset = { __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		offset.__x = (finalSize.width() - controlSize.width()) / 2;
		offset.__y = (finalSize.height() - controlSize.height()) / 2;
		offset.__x = Math.round(offset.__x - 0.5);
		offset.__y = Math.round(offset.__y - 0.5);
		return offset;
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
	flush: function () {
		this.view().flush();
	}
	,
	errorMessageDisplaying: null,
	dataChanged: null,
	getFontInfo: function () {
		return $.ig.FontUtil.prototype.toFontInfo(this.font());
	}
	,
	$type: new $.ig.Type('XamBarcode', $.ig.Control.prototype.$type)
}, true);

$.ig.util.defType('XamGridBarcode', 'XamBarcode', {
	__leftQuietZoneWidth: 0,
	leftQuietZoneWidth: function (value) {
		if (arguments.length === 1) {
			this.__leftQuietZoneWidth = value;
			return value;
		} else {
			return this.__leftQuietZoneWidth;
		}
	}
	,
	__rightQuietZoneWidth: 0,
	rightQuietZoneWidth: function (value) {
		if (arguments.length === 1) {
			this.__rightQuietZoneWidth = value;
			return value;
		} else {
			return this.__rightQuietZoneWidth;
		}
	}
	,
	_barcodeGrid: null,
	barcodeGrid: function (value) {
		if (arguments.length === 1) {
			this._barcodeGrid = value;
			return value;
		} else {
			return this._barcodeGrid;
		}
	}
	,
	_textBlock: null,
	textBlock: function (value) {
		if (arguments.length === 1) {
			this._textBlock = value;
			return value;
		} else {
			return this._textBlock;
		}
	}
	,
	__isSymbolGenerated: false,
	isSymbolGenerated: function (value) {
		if (arguments.length === 1) {
			this.__isSymbolGenerated = value;
			return value;
		} else {
			return this.__isSymbolGenerated;
		}
	}
	,
	_barcodeAlgorithm: null,
	barcodeAlgorithm: function (value) {
		if (arguments.length === 1) {
			this._barcodeAlgorithm = value;
			return value;
		} else {
			return this._barcodeAlgorithm;
		}
	}
	,
	createView: function () {
		return new $.ig.XamGridBarcodeView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.XamBarcode.prototype.onViewCreated.call(this, view);
		this.gridBarcodeView(view);
	}
	,
	_gridBarcodeView: null,
	gridBarcodeView: function (value) {
		if (arguments.length === 1) {
			this._gridBarcodeView = value;
			return value;
		} else {
			return this._gridBarcodeView;
		}
	}
	,
	init: function () {
		this.__isSymbolGenerated = true;
		$.ig.XamBarcode.prototype.init.call(this);
		this.defaultStyleKey($.ig.XamGridBarcode.prototype.$type);
		this.barcodeAlgorithm(new $.ig.BarcodeAlgorithm());
		var pixels = 1;
		while ($.ig.MeasureHelper.prototype.getXPixels(this.xDimension()) < 1) {
			this.xDimension(Math.round($.ig.MeasureHelper.prototype.getXMilimeters(pixels) * 1000) / 1000);
			pixels += $.ig.BarcodeAlgorithm.prototype.minXDimension;
		}
	},
	barsFillMode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGridBarcode.prototype.barsFillModeProperty, $.ig.BarsFillMode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamGridBarcode.prototype.barsFillModeProperty));
		}
	}
	,
	onBarsFillModeChanged1: function (d, e) {
		(d).onBarsFillModeChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onBarsFillModeChanged: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	widthToHeightRatio: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGridBarcode.prototype.widthToHeightRatioProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamGridBarcode.prototype.widthToHeightRatioProperty);
		}
	}
	,
	onWidthToHeightRatioChanged1: function (d, e) {
		(d).onWidthToHeightRatioChanged(e.oldValue(), e.newValue());
	}
	,
	onWidthToHeightRatioChanged: function (oldValue, newValue) {
		if (newValue <= 0) {
			var errorMsg = $.ig.util.replace($.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidPropertyValue"), "{0}", "WidthToHeightRatio"), "{1}", $.ig.BarcodeAlgorithm.prototype.minWidthToHeightRatio.toString()), "{2}", $.ig.BarcodeAlgorithm.prototype.maxWidthToHeightRatio.toString());
			this.gridBarcodeView().errorMessage(errorMsg);
		} else {
			this.view().scheduleArrange();
		}
	}
	,
	xDimension: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGridBarcode.prototype.xDimensionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamGridBarcode.prototype.xDimensionProperty);
		}
	}
	,
	onXDimensionChanged1: function (d, e) {
		(d).onXDimensionChanged(e.oldValue(), e.newValue());
	}
	,
	onXDimensionChanged: function (oldValue, newValue) {
		if (newValue < $.ig.BarcodeAlgorithm.prototype.minXDimension || newValue > $.ig.BarcodeAlgorithm.prototype.maxXDimension) {
			var errorMsg = $.ig.util.replace($.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidPropertyValue"), "{0}", "XDimension"), "{1}", $.ig.BarcodeAlgorithm.prototype.minXDimension.toString()), "{2}", $.ig.BarcodeAlgorithm.prototype.maxXDimension.toString());
			this.gridBarcodeView().errorMessage(errorMsg);
		} else {
			this.view().scheduleArrange();
		}
	}
	,
	validateParameters: function () {
		return $.ig.XamBarcode.prototype.validateParameters.call(this) && this.widthToHeightRatio() > 0 && this.xDimension() >= $.ig.BarcodeAlgorithm.prototype.minXDimension && this.xDimension() <= $.ig.BarcodeAlgorithm.prototype.maxXDimension;
	}
	,
	onStretchChanged1: function (oldValue, newValue) {
		$.ig.XamBarcode.prototype.onStretchChanged1.call(this, oldValue, newValue);
		if (this.barcodeGrid() != null) {
			this.view().scheduleArrange();
		}
	}
	,
	arrangeBarcode: function () {
		if (this.barcodeGrid() == null) {
			return;
		}
		$.ig.XamBarcode.prototype.arrangeBarcode.call(this);
		if (this.isValid() == false) {
			this.clearBarcodeGrid();
		} else if (!this.isDimensionValid()) {
			var errorMsg = $.ig.SR.prototype.getString("InvalidDimension");
			var ex = new $.ig.ArgumentException(1, errorMsg);
			this.errorMessage(ex);
			this.clearBarcodeGrid();
		}
		if (this.isValid() == false || !this.isSymbolGenerated()) {
			this.gridBarcodeView().measureBarcodeGrid();
			this.view().arrangeComplete();
			return;
		}
		this.isSymbolGenerated(false);
		try {
			this.beginDrawBarcode();
			this.drawBarcode();
			this.endDrawBarcode();
		}
		catch (ex_) {
			this.errorMessage(ex_);
		}
		this.isSymbolGenerated(true);
		this.gridBarcodeView().measureBarcodeGrid();
		this.view().arrangeComplete();
	}
	,
	beginDrawBarcode: function () {
		this.clearBarcodeGrid();
	}
	,
	drawBarcode: function () {
	}
	,
	calculateDefaultSize: function (aspectRatio) {
		var width = 150, height = 100;
		if (!this.isValid()) {
			if (!$.ig.util.isNaN(this.width())) {
				width = this.width();
			}
			if (!$.ig.util.isNaN(this.height())) {
				height = this.height();
			}
			return new $.ig.Size(1, width, height);
		}
		width = this.barcodeGrid().columnsDefinition().count() * $.ig.MeasureHelper.prototype.getXPixels(this.xDimension());
		height = this.widthToHeightRatio() * $.ig.MeasureHelper.prototype.getYPixels(this.xDimension());
		if (this.textBlock() != null && this.textBlock().__visibility == $.ig.Visibility.prototype.visible) {
			var desiredTextSize = this.view().getLabelSize(this.textBlock());
			if (desiredTextSize.width() > width) {
				width = desiredTextSize.width();
				height = width / aspectRatio;
			}
			height += desiredTextSize.height();
		}
		width = Math.round(width - 0.5);
		height = Math.round(height - 0.5);
		return new $.ig.Size(1, width, height);
	}
	,
	calculateAspectRatio: function () {
		if (!this.isValid()) {
			return 1 / $.ig.BarcodeAlgorithm.prototype.defaultAspectRatio;
		}
		var widthModules = this.barcodeGrid().columnsDefinition().count();
		var heightModules = this.widthToHeightRatio();
		return widthModules / heightModules;
	}
	,
	exportSerializedVisualData: function () {
		var cvd = this.exportVisualData();
		cvd.scaleByViewport();
		return cvd.serialize();
	}
	,
	exportVisualData: function () {
		var gridBarcodeVisualData = new $.ig.GridBarcodeVisualData();
		this.exportVisualDataInternal(gridBarcodeVisualData);
		return gridBarcodeVisualData;
	}
	,
	exportVisualDataInternal: function (barcodeVisualData) {
		$.ig.XamBarcode.prototype.exportVisualDataInternal.call(this, barcodeVisualData);
		var gridBarcodeVisualData = (barcodeVisualData);
		gridBarcodeVisualData.figuresPath(new $.ig.PathVisualData(1, "Figures", this.barcodeGrid().figuresPath()));
		gridBarcodeVisualData.figuresPathRect(new $.ig.PathVisualData(1, "BarcodeGrid", this.gridBarcodeView().exportFiguresPathRect()));
	}
	,
	shouldShowText: function () {
		return true;
	}
	,
	clearBarcodeGrid: function () {
		if (this.barcodeGrid() != null) {
			this.barcodeGrid().clear();
		}
	}
	,
	addText: function (text, startColumn, columnSpan, startRow, rowSpan) {
		if (this.shouldShowText() == false) {
			return null;
		}
		var label = (function () {
			var $ret = new $.ig.TextBlock();
			$ret.text(text);
			return $ret;
		}());
		this.barcodeGrid().textPositions().add(new $.ig.Module(1, startRow, startColumn, rowSpan, columnSpan));
		this.barcodeGrid().children().add(label);
		return label;
	}
	,
	addBarsColumns: function (bars) {
		if (bars == null) {
			return;
		}
		for (var i = 0; i < bars.length; i++) {
			var barWidth = bars[i];
			if (barWidth == 0) {
				break;
			}
			this.addColumns(barWidth);
		}
	}
	,
	addColumns: function (number) {
		for (var k = 0; k < number; k++) {
			var column = (function () {
				var $ret = new $.ig.ColumnDefinition();
				$ret.width(new $.ig.GridLength(1, 1, $.ig.GridUnitType.prototype.star));
				return $ret;
			}());
			this.barcodeGrid().columnsDefinition().add(column);
		}
	}
	,
	addRow: function (height, type) {
		this.barcodeGrid().rowsDefinition().add((function () {
			var $ret = new $.ig.RowDefinition();
			$ret.height(new $.ig.GridLength(1, height, type));
			return $ret;
		}()));
	}
	,
	fillBars: function (bars, isFirstBarPrinted, startRow, rowSpan, startColumn) {
		if (bars == null) {
			return;
		}
		for (var i = 0; i < bars.length; i++) {
			var currentBar = bars[i];
			if (this.barcodeGrid().columnsDefinition().count() + currentBar <= startColumn || this.barcodeGrid().rowsDefinition().count() + currentBar <= startRow) {
				break;
			}
			this.fillCell(startColumn, currentBar, startRow, rowSpan, isFirstBarPrinted);
			startColumn += currentBar;
			isFirstBarPrinted = !isFirstBarPrinted;
		}
	}
	,
	fillCell: function (columnNumber, colSpan, rowNumber, rowSpan, isBarPrinted) {
		if (isBarPrinted) {
			var module = new $.ig.Module(1, rowNumber, columnNumber, rowSpan, colSpan);
			this.barcodeGrid().modules().add(module);
		}
	}
	,
	addHorizontalQuietZone: function (modulesNumber) {
		for (var k = 0; k < modulesNumber; k++) {
			this.addColumns(1);
		}
	}
	,
	addVerticalQuietZone: function (modulesNumber) {
		for (var k = 0; k < modulesNumber; k++) {
			this.addRow(1, $.ig.GridUnitType.prototype.star);
		}
	}
	,
	endDrawBarcode: function () {
		if (this.actualWidth() == 0 || this.actualHeight() == 0) {
			return;
		}
	}
	,
	isDimensionValid: function () {
		if (this.barsFillMode() == $.ig.BarsFillMode.prototype.ensureEqualSize && this.stretch() == $.ig.Stretch.prototype.none) {
			return $.ig.MeasureHelper.prototype.getXPixels(this.xDimension()) >= 1;
		}
		return true;
	}
	,
	$type: new $.ig.Type('XamGridBarcode', $.ig.XamBarcode.prototype.$type)
}, true);

$.ig.util.defType('XamQRCodeBarcode', 'XamGridBarcode', {
	_encoder: null,
	encoder: function (value) {
		if (arguments.length === 1) {
			this._encoder = value;
			return value;
		} else {
			return this._encoder;
		}
	}
	,
	init: function () {
		$.ig.XamGridBarcode.prototype.init.call(this);
		this.defaultStyleKey($.ig.XamQRCodeBarcode.prototype.$type);
		this.encoder(new $.ig.QRCodeEncoder(this));
		this.xDimension(0.7);
		var iso8859Dash1_ = $.ig.EncodingService.prototype.getEncoding("iso-8859-1");
		var isIso8859Dash1Loaded = iso8859Dash1_.getBytes2;
		if (isIso8859Dash1Loaded) {
			this.eciNumber(3);
		} else {
			this.eciNumber(26);
		}
		var shiftJis_ = $.ig.EncodingService.prototype.getEncoding("shift_jis");
		var isShiftJisLoaded = shiftJis_.getBytes2;
		if (isShiftJisLoaded) {
			this.encodingMode($.ig.EncodingMode.prototype.undefined);
		} else {
			this.encodingMode($.ig.EncodingMode.prototype.byte1);
		}
	},
	validateData: function (data) {
		var isValid = $.ig.XamGridBarcode.prototype.validateData.call(this, data);
		if (isValid) {
			var maxDataLength = this.encodingMode() != $.ig.EncodingMode.prototype.undefined ? $.ig.QRCodeAlgorithm.prototype.maxDataLength[this.encodingMode()][this.errorCorrectionLevel()] : 0;
			if (this.encodingMode() != $.ig.EncodingMode.prototype.undefined && data.length > maxDataLength) {
				isValid = false;
			}
		}
		return isValid;
	}
	,
	validateParameters: function () {
		var isValid = $.ig.XamGridBarcode.prototype.validateParameters.call(this);
		if (this.fnc1Mode() == $.ig.Fnc1Mode.prototype.industry && isValid) {
			isValid = $.ig.QRCodeEncoder.prototype.isApplicationIndicatorValid(this.applicationIndicator());
		}
		return isValid && $.ig.QRCodeEncoder.prototype.isEciInRange(this.eciNumber());
	}
	,
	drawBarcode: function () {
		if (this.encoder() == null) {
			return;
		}
		this.addVerticalQuietZone($.ig.QRCodeAlgorithm.prototype.quietVZone);
		this.addHorizontalQuietZone($.ig.QRCodeAlgorithm.prototype.quietHZone);
		try {
			var bitStream = this.encoder().encodeData();
			this.transformBitsIntoBars(bitStream);
		}
		catch (ex_) {
			this.errorMessage(ex_);
			this.clearBarcodeGrid();
			return;
		}
		this.addHorizontalQuietZone($.ig.QRCodeAlgorithm.prototype.quietHZone);
		this.addVerticalQuietZone($.ig.QRCodeAlgorithm.prototype.quietVZone);
	}
	,
	calculateDefaultSize: function (aspectRatio) {
		if (!this.isValid()) {
			return $.ig.XamGridBarcode.prototype.calculateDefaultSize.call(this, aspectRatio);
		}
		var width;
		var height;
		width = this.barcodeGrid().columnsDefinition().count() * $.ig.MeasureHelper.prototype.getXPixels(this.xDimension());
		height = this.barcodeGrid().rowsDefinition().count() * $.ig.MeasureHelper.prototype.getYPixels(this.xDimension());
		return new $.ig.Size(1, Math.floor(width), Math.floor(height));
	}
	,
	calculateAspectRatio: function () {
		return 1;
	}
	,
	transformBitsIntoBars: function (bitStream) {
		var areColumnsAdded = false;
		for (var i = 0; i < bitStream.length; i++) {
			this.addRow(1, $.ig.GridUnitType.prototype.star);
			if (!areColumnsAdded) {
				for (var j = 0; j < bitStream.length; j++) {
					this.addColumns(1);
				}
				areColumnsAdded = true;
			}
		}
		for (var i1 = 0; i1 < bitStream.length; i1++) {
			for (var j1 = 0; j1 < bitStream.length; j1++) {
				if (bitStream[j1][i1] != 0) {
					this.fillCell(j1 + $.ig.QRCodeAlgorithm.prototype.quietVZone, 1, i1 + $.ig.QRCodeAlgorithm.prototype.quietHZone, 1, true);
				} else {
				}
			}
		}
	}
	,
	errorCorrectionLevel: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.errorCorrectionLevelProperty, $.ig.QRCodeErrorCorrectionLevel.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamQRCodeBarcode.prototype.errorCorrectionLevelProperty));
		}
	}
	,
	onErrorCorrectionLevelChanged1: function (d, e) {
		(d).onErrorCorrectionLevelChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onErrorCorrectionLevelChanged: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	sizeVersion: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.sizeVersionProperty, $.ig.SizeVersion.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamQRCodeBarcode.prototype.sizeVersionProperty));
		}
	}
	,
	onSizeVersionChanged1: function (d, e) {
		(d).onSizeVersionChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onSizeVersionChanged: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	encodingMode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.encodingModeProperty, $.ig.EncodingMode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamQRCodeBarcode.prototype.encodingModeProperty));
		}
	}
	,
	onEncodingModeChanged1: function (d, e) {
		(d).onEncodingModeChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onEncodingModeChanged: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	eciNumber: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.eciNumberProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.XamQRCodeBarcode.prototype.eciNumberProperty));
		}
	}
	,
	onEciNumberChanged1: function (d, e) {
		(d).onEciNumberChanged($.ig.util.getValue(e.oldValue()), $.ig.util.getValue(e.newValue()));
	}
	,
	onEciNumberChanged: function (oldValue, newValue) {
		if (newValue != oldValue) {
			if (!$.ig.QRCodeEncoder.prototype.isEciInRange(newValue)) {
				var errorMsg = $.ig.util.replace($.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidPropertyValue"), "{0}", "EciNumber"), "{1}", $.ig.QRCodeAlgorithm.prototype.minEciNumber.toString().padLeft(6, '0')), "{2}", $.ig.QRCodeAlgorithm.prototype.maxEciNumber.toString());
				this.gridBarcodeView().errorMessage(errorMsg);
				return;
			}
			this.view().scheduleArrange();
		}
	}
	,
	eciHeaderDisplayMode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.eciHeaderDisplayModeProperty, $.ig.HeaderDisplayMode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamQRCodeBarcode.prototype.eciHeaderDisplayModeProperty));
		}
	}
	,
	onEciHeaderDisplayModeChanged1: function (d, e) {
		(d).onEciHeaderDisplayModeChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onEciHeaderDisplayModeChanged: function (oldValue, newValue) {
		if (newValue != oldValue) {
			this.view().scheduleArrange();
		}
	}
	,
	fnc1Mode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.fnc1ModeProperty, $.ig.Fnc1Mode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.XamQRCodeBarcode.prototype.fnc1ModeProperty));
		}
	}
	,
	onFnc1ModeChanged1: function (d, e) {
		(d).onFnc1ModeChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue()));
	}
	,
	onFnc1ModeChanged: function (oldValue, newValue) {
		this.view().scheduleArrange();
	}
	,
	applicationIndicator: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamQRCodeBarcode.prototype.applicationIndicatorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamQRCodeBarcode.prototype.applicationIndicatorProperty);
		}
	}
	,
	onApplicationIndicatorChanged1: function (d, e) {
		(d).onApplicationIndicatorChanged(e.oldValue(), e.newValue());
	}
	,
	onApplicationIndicatorChanged: function (oldValue, newValue) {
		if (String.isNullOrEmpty(newValue) && !$.ig.QRCodeEncoder.prototype.isApplicationIndicatorValid(newValue)) {
			var errorMsg = $.ig.util.replace($.ig.util.replace($.ig.util.replace($.ig.SR.prototype.getString("InvalidPropertyValue"), "{0}", "ApplicationIndicator"), "{1}", "{a-z, A-Z, 00"), "{2}", "99}");
			this.gridBarcodeView().errorMessage(errorMsg);
			return;
		}
		this.view().scheduleArrange();
	}
	,
	$type: new $.ig.Type('XamQRCodeBarcode', $.ig.XamGridBarcode.prototype.$type)
}, true);

$.ig.util.defType('SR', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	getString: function (resourceName_) {
		var resource = String.empty();
		resourceName_ = resourceName_.charAt(0).toLowerCase() + resourceName_.substr(1);
		resource = $.ig.Barcode.locale[resourceName_];
		return resource;
	}
	,
	$type: new $.ig.Type('SR', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ColumnDefinition', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_maxWidth: 0,
	maxWidth: function (value) {
		if (arguments.length === 1) {
			this._maxWidth = value;
			return value;
		} else {
			return this._maxWidth;
		}
	}
	,
	_minWidth: 0,
	minWidth: function (value) {
		if (arguments.length === 1) {
			this._minWidth = value;
			return value;
		} else {
			return this._minWidth;
		}
	}
	,
	_width: null,
	width: function (value) {
		if (arguments.length === 1) {
			this._width = value;
			return value;
		} else {
			return this._width;
		}
	}
	,
	$type: new $.ig.Type('ColumnDefinition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GridLength', 'Object', {
	init: function (initNumber, pixels) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Object.prototype.init.call(this);
		this.__value = pixels;
		this.__gridUnitType = $.ig.GridUnitType.prototype.pixel;
	},
	init1: function (initNumber, value, type) {
		$.ig.Object.prototype.init.call(this);
		this.__value = value;
		this.__gridUnitType = type;
	},
	__gridUnitType: 0,
	gridUnitType: function () {
		return this.__gridUnitType;
	}
	,
	__value: 0,
	value: function () {
		return this.__value;
	}
	,
	isStar: function () {
		return this.__gridUnitType == $.ig.GridUnitType.prototype.star;
	}
	,
	$type: new $.ig.Type('GridLength', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RowDefinition', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_height: null,
	height: function (value) {
		if (arguments.length === 1) {
			this._height = value;
			return value;
		} else {
			return this._height;
		}
	}
	,
	_maxHeight: 0,
	maxHeight: function (value) {
		if (arguments.length === 1) {
			this._maxHeight = value;
			return value;
		} else {
			return this._maxHeight;
		}
	}
	,
	_minHeight: 0,
	minHeight: function (value) {
		if (arguments.length === 1) {
			this._minHeight = value;
			return value;
		} else {
			return this._minHeight;
		}
	}
	,
	$type: new $.ig.Type('RowDefinition', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamGridBarcodeView', 'XamBarcodeView', {
	_xamGridBarcode: null,
	xamGridBarcode: function (value) {
		if (arguments.length === 1) {
			this._xamGridBarcode = value;
			return value;
		} else {
			return this._xamGridBarcode;
		}
	}
	,
	init: function (barcode) {
		$.ig.XamBarcodeView.prototype.init.call(this, barcode);
		this.xamGridBarcode(barcode);
		this.xamGridBarcode().barcodeGrid(new $.ig.BarcodeGrid());
		this.xamGridBarcode().barcodeGrid().barcode(this.xamGridBarcode());
	},
	render: function () {
		$.ig.XamBarcodeView.prototype.render.call(this);
		var path = this.xamGridBarcode().barcodeGrid().figuresPath();
		path.__fill = this.barcode().barBrush();
		this.context().renderPath(path);
		var en = this.xamGridBarcode().barcodeGrid().children().getEnumerator();
		while (en.moveNext()) {
			var element = en.current();
			var textBlock = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
			if (textBlock != null) {
				this.setTextBrush(textBlock, this.barcode().fontBrush());
				this.context().setFontInfo(this.getLabelFont());
				this.context().renderTextBlock(textBlock);
			}
		}
	}
	,
	errorMessage: function (errorMessage) {
		this.barcode().propertyExceptionString(errorMessage);
		this.scheduleArrange();
	}
	,
	measureBarcodeGrid: function () {
		var availableSize = new $.ig.Size(1, this.xamGridBarcode().viewport().width(), this.xamGridBarcode().viewport().height());
		var finalSize = this.xamGridBarcode().barcodeGrid().measureBarcodeGrid(availableSize);
		this.xamGridBarcode().barcodeGrid().arrangeBarcodeGrid(availableSize);
	}
	,
	exportFiguresPathRect: function () {
		var geometryGroup = this.xamGridBarcode().barcodeGrid().figuresPath().data();
		var geometries = geometryGroup.children();
		var maxX = -1.7976931348623157E+308;
		var maxY = -1.7976931348623157E+308;
		var minX = 1.7976931348623157E+308;
		var minY = 1.7976931348623157E+308;
		var en = geometries.getEnumerator();
		while (en.moveNext()) {
			var geometry = en.current();
			var rect = geometry.rect();
			var currMinX = rect.left();
			var currMaxX = rect.right();
			var currMinY = rect.top();
			var currMaxY = rect.bottom();
			minX = currMinX < minX ? currMinX : minX;
			minY = currMinY < minY ? currMinY : minY;
			maxX = currMaxX > maxX ? currMaxX : maxX;
			maxY = currMaxY > maxY ? currMaxY : maxY;
		}
		var boundsRect = new $.ig.Rect(0, minX, minY, maxX - minX, maxY - minY);
		var path = new $.ig.Path();
		path.data((function () {
			var $ret = new $.ig.RectangleGeometry();
			$ret.rect(boundsRect);
			return $ret;
		}()));
		return path;
	}
	,
	$type: new $.ig.Type('XamGridBarcodeView', $.ig.XamBarcodeView.prototype.$type)
}, true);

$.ig.GridUnitType.prototype.auto = 0;
$.ig.GridUnitType.prototype.pixel = 1;
$.ig.GridUnitType.prototype.star = 2;

$.ig.QRModeIndicator.prototype.eci = 111;
$.ig.QRModeIndicator.prototype.numeric = 1;
$.ig.QRModeIndicator.prototype.alphanumeric = 10;
$.ig.QRModeIndicator.prototype.byte1 = 100;
$.ig.QRModeIndicator.prototype.kanji = 1000;
$.ig.QRModeIndicator.prototype.structuredAppend = 11;
$.ig.QRModeIndicator.prototype.fnc1First = 101;
$.ig.QRModeIndicator.prototype.fnc1Second = 1001;
$.ig.QRModeIndicator.prototype.terminator = 0;

$.ig.Fnc1Mode.prototype.none = 0;
$.ig.Fnc1Mode.prototype.gs1 = 1;
$.ig.Fnc1Mode.prototype.industry = 2;

$.ig.EncodingMode.prototype.undefined = -1;
$.ig.EncodingMode.prototype.numeric = 0;
$.ig.EncodingMode.prototype.alphanumeric = 1;
$.ig.EncodingMode.prototype.byte1 = 2;
$.ig.EncodingMode.prototype.kanji = 3;

$.ig.QRCodeErrorCorrectionLevel.prototype.low = 1;
$.ig.QRCodeErrorCorrectionLevel.prototype.medium = 0;
$.ig.QRCodeErrorCorrectionLevel.prototype.quartil = 3;
$.ig.QRCodeErrorCorrectionLevel.prototype.high = 2;

$.ig.SizeVersion.prototype.undefined = 0;
$.ig.SizeVersion.prototype.version1 = 1;
$.ig.SizeVersion.prototype.version2 = 2;
$.ig.SizeVersion.prototype.version3 = 3;
$.ig.SizeVersion.prototype.version4 = 4;
$.ig.SizeVersion.prototype.version5 = 5;
$.ig.SizeVersion.prototype.version6 = 6;
$.ig.SizeVersion.prototype.version7 = 7;
$.ig.SizeVersion.prototype.version8 = 8;
$.ig.SizeVersion.prototype.version9 = 9;
$.ig.SizeVersion.prototype.version10 = 10;
$.ig.SizeVersion.prototype.version11 = 11;
$.ig.SizeVersion.prototype.version12 = 12;
$.ig.SizeVersion.prototype.version13 = 13;
$.ig.SizeVersion.prototype.version14 = 14;
$.ig.SizeVersion.prototype.version15 = 15;
$.ig.SizeVersion.prototype.version16 = 16;
$.ig.SizeVersion.prototype.version17 = 17;
$.ig.SizeVersion.prototype.version18 = 18;
$.ig.SizeVersion.prototype.version19 = 19;
$.ig.SizeVersion.prototype.version20 = 20;
$.ig.SizeVersion.prototype.version21 = 21;
$.ig.SizeVersion.prototype.version22 = 22;
$.ig.SizeVersion.prototype.version23 = 23;
$.ig.SizeVersion.prototype.version24 = 24;
$.ig.SizeVersion.prototype.version25 = 25;
$.ig.SizeVersion.prototype.version26 = 26;
$.ig.SizeVersion.prototype.version27 = 27;
$.ig.SizeVersion.prototype.version28 = 28;
$.ig.SizeVersion.prototype.version29 = 29;
$.ig.SizeVersion.prototype.version30 = 30;
$.ig.SizeVersion.prototype.version31 = 31;
$.ig.SizeVersion.prototype.version32 = 32;
$.ig.SizeVersion.prototype.version33 = 33;
$.ig.SizeVersion.prototype.version34 = 34;
$.ig.SizeVersion.prototype.version35 = 35;
$.ig.SizeVersion.prototype.version36 = 36;
$.ig.SizeVersion.prototype.version37 = 37;
$.ig.SizeVersion.prototype.version38 = 38;
$.ig.SizeVersion.prototype.version39 = 39;
$.ig.SizeVersion.prototype.version40 = 40;

$.ig.HeaderDisplayMode.prototype.hide = 0;
$.ig.HeaderDisplayMode.prototype.show = 1;

$.ig.BarsFillMode.prototype.fillSpace = 0;
$.ig.BarsFillMode.prototype.ensureEqualSize = 1;

$.ig.EncodingService.prototype.__windows1250Encoding = null;
$.ig.EncodingService.prototype.__windows1251Encoding = null;
$.ig.EncodingService.prototype.__windows1252Encoding = null;
$.ig.EncodingService.prototype.__windows1256Encoding = null;
$.ig.EncodingService.prototype.__usAsciiEncoding = null;
$.ig.EncodingService.prototype.__big5Encoding = null;
$.ig.EncodingService.prototype.__windows936Encoding = null;
$.ig.EncodingService.prototype.__ksc5601Encoding = null;
$.ig.EncodingService.prototype.__iso8859Dash1 = null;
$.ig.EncodingService.prototype.__iso8859Dash2 = null;
$.ig.EncodingService.prototype.__iso8859Dash3 = null;
$.ig.EncodingService.prototype.__iso8859Dash4 = null;
$.ig.EncodingService.prototype.__iso8859Dash5 = null;
$.ig.EncodingService.prototype.__iso8859Dash6 = null;
$.ig.EncodingService.prototype.__iso8859Dash7 = null;
$.ig.EncodingService.prototype.__iso8859Dash8 = null;
$.ig.EncodingService.prototype.__iso8859Dash9 = null;
$.ig.EncodingService.prototype.__iso8859Dash11 = null;
$.ig.EncodingService.prototype.__iso8859Dash13 = null;
$.ig.EncodingService.prototype.__iso8859Dash15 = null;
$.ig.EncodingService.prototype.__windows932Encoding = null;
$.ig.EncodingService.prototype.__codePage437Encoding = null;
$.ig.EncodingService.prototype.encodingFriendlyNames = null;
if ($.ig.EncodingService.prototype.staticInit && !$.ig.EncodingService.prototype.encodingServiceStaticInitCalled) { $.ig.EncodingService.prototype.staticInit(); $.ig.EncodingService.prototype.encodingServiceStaticInitCalled = true; }

$.ig.QRCodeBarcodeResources.prototype._pADCHAR = '=';
$.ig.QRCodeBarcodeResources.prototype._aLPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

$.ig.XamBarcodeView.prototype._tEXT_MARGIN = 0;

$.ig.BarcodeAlgorithm.prototype.numericChars = "0123456789";
$.ig.BarcodeAlgorithm.prototype.alphanumericChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
$.ig.BarcodeAlgorithm.prototype.minWidthToHeightRatio = 0.001;
$.ig.BarcodeAlgorithm.prototype.maxWidthToHeightRatio = 1000;
$.ig.BarcodeAlgorithm.prototype.defaultAspectRatio = 0.5;
$.ig.BarcodeAlgorithm.prototype.minXDimension = 0.01;
$.ig.BarcodeAlgorithm.prototype.maxXDimension = 100;
$.ig.BarcodeAlgorithm.prototype.defaultWidthToHeightRatio = 3;
$.ig.BarcodeAlgorithm.prototype.defaultXDimension = 0.25;
$.ig.BarcodeAlgorithm.prototype.asciiChars = null;
$.ig.BarcodeAlgorithm.prototype.codePageByEci = null;
$.ig.BarcodeAlgorithm.prototype.encodingNameByEci = null;
if ($.ig.BarcodeAlgorithm.prototype.staticInit && !$.ig.BarcodeAlgorithm.prototype.barcodeAlgorithmStaticInitCalled) { $.ig.BarcodeAlgorithm.prototype.staticInit(); $.ig.BarcodeAlgorithm.prototype.barcodeAlgorithmStaticInitCalled = true; }

$.ig.Gf256.prototype.gfNumber = 256;
$.ig.Gf256.prototype.n = $.ig.Gf256.prototype.gfNumber - 1;

$.ig.Gs1Helper.prototype.fnc1Number = -1;
$.ig.Gs1Helper.prototype.aIs = null;
if ($.ig.Gs1Helper.prototype.staticInit && !$.ig.Gs1Helper.prototype.gs1HelperStaticInitCalled) { $.ig.Gs1Helper.prototype.staticInit(); $.ig.Gs1Helper.prototype.gs1HelperStaticInitCalled = true; }

$.ig.QRCodeAlgorithm.prototype._trailingKanjiSetStart = 57408;
$.ig.QRCodeAlgorithm.prototype._trailingKanjiSetEnd = 60351;
$.ig.QRCodeAlgorithm.prototype._leadingKanjiSetStart = 33088;
$.ig.QRCodeAlgorithm.prototype._leadingKanjiSetEnd = 40956;
$.ig.QRCodeAlgorithm.prototype.verticalTimingPatternIndex = 6;
$.ig.QRCodeAlgorithm.prototype.formatInfoGenPolynom = 1335;
$.ig.QRCodeAlgorithm.prototype.formatInfoMaskPattern = 21522;
$.ig.QRCodeAlgorithm.prototype.versionInfoGenPolynom = 7973;
$.ig.QRCodeAlgorithm.prototype.hiddenCell = -2;
$.ig.QRCodeAlgorithm.prototype.n = $.ig.Gf256.prototype.n;
$.ig.QRCodeAlgorithm.prototype.validQrAlphanumercChars = $.ig.BarcodeAlgorithm.prototype.alphanumericChars + "$%*+_./:";
$.ig.QRCodeAlgorithm.prototype.quietVZone = 4;
$.ig.QRCodeAlgorithm.prototype.quietHZone = 4;
$.ig.QRCodeAlgorithm.prototype.defaultEciNumber = 3;
$.ig.QRCodeAlgorithm.prototype.maxEciNumber = 999999;
$.ig.QRCodeAlgorithm.prototype.minEciNumber = -1;
$.ig.QRCodeAlgorithm.prototype.validQrByteChars = $.ig.BarcodeAlgorithm.prototype.asciiChars;
$.ig.QRCodeAlgorithm.prototype.validQrKanjiChars = null;
$.ig.QRCodeAlgorithm.prototype.maxDataLength = null;
$.ig.QRCodeAlgorithm.prototype.dataBitsCapacity = null;
$.ig.QRCodeAlgorithm.prototype.characterCountIndicatorBitsNumber = null;
$.ig.QRCodeAlgorithm.prototype.dataCodewordsCapacity = null;
$.ig.QRCodeAlgorithm.prototype.remainderBits = null;
$.ig.QRCodeAlgorithm.prototype.formatInfoCells = null;
$.ig.QRCodeAlgorithm.prototype.finderPattern = null;
$.ig.QRCodeAlgorithm.prototype.horizontalSeparatorPattern = null;
$.ig.QRCodeAlgorithm.prototype.verticalSeparatorPattern = null;
$.ig.QRCodeAlgorithm.prototype.alignmentPatternsIndexes = null;
$.ig.QRCodeAlgorithm.prototype.alignmentPattern = null;
if ($.ig.QRCodeAlgorithm.prototype.staticInit && !$.ig.QRCodeAlgorithm.prototype.qRCodeAlgorithmStaticInitCalled) { $.ig.QRCodeAlgorithm.prototype.staticInit(); $.ig.QRCodeAlgorithm.prototype.qRCodeAlgorithmStaticInitCalled = true; }

$.ig.QRCodeEncoder.prototype._invalidValue = -2;
$.ig.QRCodeEncoder.prototype._allInclusive = -1;
$.ig.QRCodeEncoder.prototype._emptyCell = -1;

$.ig.QRMask.prototype.minMaskIndex = 0;
$.ig.QRMask.prototype.maxMaskIndex = 7;
$.ig.QRMask.prototype._n1 = 3;
$.ig.QRMask.prototype._n2 = 3;
$.ig.QRMask.prototype._n3 = 40;
$.ig.QRMask.prototype._n4 = 10;

$.ig.BarcodeGrid.prototype._invalidValue = -1;

$.ig.XamBarcode.prototype._rootGridName = "RootGrid";
$.ig.XamBarcode.prototype._errorTextBlockName = "ErrorTextBlock";
$.ig.XamBarcode.prototype.backingBrushPropertyName = "BackingBrush";
$.ig.XamBarcode.prototype.backingOutlinePropertyName = "BackingOutline";
$.ig.XamBarcode.prototype.backingStrokeThicknessPropertyName = "BackingStrokeThickness";
$.ig.XamBarcode.prototype.barBrushPropertyName = "BarBrush";
$.ig.XamBarcode.prototype.fontBrushPropertyName = "FontBrush";
$.ig.XamBarcode.prototype.fontPropertyName = "Font";
$.ig.XamBarcode.prototype.backingBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.backingBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.backingBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.backingOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.backingOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.backingOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.backingStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.backingStrokeThicknessPropertyName, Number, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.backingStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.barBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.barBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.barBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.fontBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.fontBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.fontBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.fontProperty = $.ig.DependencyProperty.prototype.register($.ig.XamBarcode.prototype.fontPropertyName, String, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, null, function (o, e) {
	(o).onPropertyChanged($.ig.XamBarcode.prototype.fontPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamBarcode.prototype.dataProperty = $.ig.DependencyProperty.prototype.register("Data", String, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, String.empty(), $.ig.XamBarcode.prototype.onDataChanged1));
$.ig.XamBarcode.prototype.errorMessageTextProperty = $.ig.DependencyProperty.prototype.register("ErrorMessageText", String, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, String.empty(), $.ig.XamBarcode.prototype.onErrorMessageTextChanged1));
$.ig.XamBarcode.prototype.stretchProperty = $.ig.DependencyProperty.prototype.register("Stretch", $.ig.Stretch.prototype.$type, $.ig.XamBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Stretch.prototype.getBox($.ig.Stretch.prototype.uniform), $.ig.XamBarcode.prototype.onStretchChanged));
if ($.ig.XamBarcode.prototype.staticInit && !$.ig.XamBarcode.prototype.xamBarcodeStaticInitCalled) { $.ig.XamBarcode.prototype.staticInit(); $.ig.XamBarcode.prototype.xamBarcodeStaticInitCalled = true; }

$.ig.XamGridBarcode.prototype.barcodeGridName = "BarcodeGrid";
$.ig.XamGridBarcode.prototype.barsFillModeProperty = $.ig.DependencyProperty.prototype.register("BarsFillMode", $.ig.BarsFillMode.prototype.$type, $.ig.XamGridBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.BarsFillMode.prototype.getBox($.ig.BarsFillMode.prototype.fillSpace), $.ig.XamGridBarcode.prototype.onBarsFillModeChanged1));
$.ig.XamGridBarcode.prototype.widthToHeightRatioProperty = $.ig.DependencyProperty.prototype.register("WidthToHeightRatio", Number, $.ig.XamGridBarcode.prototype.$type, new $.ig.PropertyMetadata(2, 3, $.ig.XamGridBarcode.prototype.onWidthToHeightRatioChanged1));
$.ig.XamGridBarcode.prototype.xDimensionProperty = $.ig.DependencyProperty.prototype.register("XDimension", Number, $.ig.XamGridBarcode.prototype.$type, new $.ig.PropertyMetadata(2, 0.25, $.ig.XamGridBarcode.prototype.onXDimensionChanged1));

$.ig.XamQRCodeBarcode.prototype.errorCorrectionLevelProperty = $.ig.DependencyProperty.prototype.register("ErrorCorrectionLevel", $.ig.QRCodeErrorCorrectionLevel.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.QRCodeErrorCorrectionLevel.prototype.getBox($.ig.QRCodeErrorCorrectionLevel.prototype.medium), $.ig.XamQRCodeBarcode.prototype.onErrorCorrectionLevelChanged1));
$.ig.XamQRCodeBarcode.prototype.sizeVersionProperty = $.ig.DependencyProperty.prototype.register("SizeVersion", $.ig.SizeVersion.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.SizeVersion.prototype.getBox($.ig.SizeVersion.prototype.undefined), $.ig.XamQRCodeBarcode.prototype.onSizeVersionChanged1));
$.ig.XamQRCodeBarcode.prototype.encodingModeProperty = $.ig.DependencyProperty.prototype.register("EncodingMode", $.ig.EncodingMode.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.EncodingMode.prototype.getBox($.ig.EncodingMode.prototype.undefined), $.ig.XamQRCodeBarcode.prototype.onEncodingModeChanged1));
$.ig.XamQRCodeBarcode.prototype.eciNumberProperty = $.ig.DependencyProperty.prototype.register("EciNumber", $.ig.Number.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.QRCodeAlgorithm.prototype.defaultEciNumber, $.ig.XamQRCodeBarcode.prototype.onEciNumberChanged1));
$.ig.XamQRCodeBarcode.prototype.eciHeaderDisplayModeProperty = $.ig.DependencyProperty.prototype.register("EciHeaderDisplayMode", $.ig.HeaderDisplayMode.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.HeaderDisplayMode.prototype.getBox($.ig.HeaderDisplayMode.prototype.hide), $.ig.XamQRCodeBarcode.prototype.onEciHeaderDisplayModeChanged1));
$.ig.XamQRCodeBarcode.prototype.fnc1ModeProperty = $.ig.DependencyProperty.prototype.register("Fnc1Mode", $.ig.Fnc1Mode.prototype.$type, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Fnc1Mode.prototype.getBox($.ig.Fnc1Mode.prototype.none), $.ig.XamQRCodeBarcode.prototype.onFnc1ModeChanged1));
$.ig.XamQRCodeBarcode.prototype.applicationIndicatorProperty = $.ig.DependencyProperty.prototype.register("ApplicationIndicator", String, $.ig.XamQRCodeBarcode.prototype.$type, new $.ig.PropertyMetadata(2, String.empty(), $.ig.XamQRCodeBarcode.prototype.onApplicationIndicatorChanged1));

} (jQuery));


