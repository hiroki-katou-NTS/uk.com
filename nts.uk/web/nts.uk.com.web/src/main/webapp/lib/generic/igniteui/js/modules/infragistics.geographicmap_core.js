/*!@license
* Infragistics.Web.ClientUI infragistics.geographicmap_core.js 16.1.20161.2145
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
"Script:bm", 
"Array:bo", 
"Action$1:br", 
"NotImplementedException:bz", 
"Math:cb", 
"ArgumentNullException:cc", 
"Dictionary_EnumerableCollection$3:ch", 
"InvalidOperationException:ci", 
"GenericEnumerable$1:cj", 
"GenericEnumerator$1:ck", 
"EventArgs:cp", 
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
"AsyncCompletedEventArgs:d0", 
"AsyncCompletedEventHandler:d1", 
"Convert:d3", 
"BinaryReader:ef", 
"UTF8Encoding:eh", 
"Encoding:ei", 
"UnicodeEncoding:ej", 
"AsciiEncoding:ek", 
"Decoder:el", 
"DefaultDecoder:em", 
"UTF8Encoding_UTF8Decoder:en", 
"Monitor:es", 
"Uri:ex", 
"UriKind:ey", 
"BinaryFileDownloader:f5", 
"UIElement:gd", 
"Transform:ge", 
"FrameworkElement:gg", 
"Visibility:gh", 
"Style:gi", 
"Image:gx"]);


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

$.ig.util.defType('Convert', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	toDouble5: function (value) {
		return value;
	}
	,
	toDouble1: function (value) {
		return value;
	}
	,
	toDouble: function (value) {
		return value;
	}
	,
	toDouble2: function (value) {
		return value;
	}
	,
	toDecimal: function (value) {
		return value;
	}
	,
	toDecimal2: function (value) {
		return value;
	}
	,
	toDecimal1: function (value) {
		return value;
	}
	,
	toInt32: function (value) {
		if (value >= 0) {
			var ret = $.ig.truncate(Math.floor(value));
			var diff1 = value - ret;
			var diff2 = Math.ceil(value) - value;
			if (diff1 > diff2 || ((diff1 == diff2) && (ret & 1) > 0)) {
				ret++;
			}
			return ret;
		} else {
			var ret1 = $.ig.truncate(Math.ceil(value));
			var diff11 = ret1 - value;
			var diff21 = value - Math.floor(value);
			if (diff11 > diff21 || ((diff11 == diff21) && (ret1 & 1) > 0)) {
				ret1--;
			}
			return ret1;
		}
	}
	,
	toInt322: function (value) {
		return parseInt(value);
	}
	,
	toDouble3: function (value) {
		return $.ig.Convert.prototype.toDouble4(value, $.ig.CultureInfo.prototype.currentCulture());
	}
	,
	toDouble4: function (value, provider) {
		var valueResolved = $.ig.util.getValue($.ig.util.unwrapNullable(value));
		if (valueResolved == null) {
			return 0;
		}
		var result = +valueResolved;
		if ($.ig.util.isNaN(result)) {
			return (valueResolved).toDouble(provider);
		}
		return result;
	}
	,
	toInt321: function (value) {
		var valueResolved = $.ig.util.getValue($.ig.util.unwrapNullable(value));
		if (valueResolved == null) {
			return 0;
		}
		var result = +valueResolved;
		if ($.ig.util.isNaN(result)) {
			return (valueResolved).toInt32($.ig.CultureInfo.prototype.currentCulture());
		}
		return result;
	}
	,
	toByte: function (value) {
		return (value ? 1 : 0);
	}
	,
	toByte1: function (value) {
		var valueResolved = $.ig.util.getValue($.ig.util.unwrapNullable(value));
		if (valueResolved == null) {
			return 0;
		}
		var result = +valueResolved;
		if ($.ig.util.isNaN(result)) {
			return (valueResolved).toByte($.ig.CultureInfo.prototype.currentCulture());
		}
		return result;
	}
	,
	toChar: function (value) {
		return String.fromCharCode(value);
	}
	,
	toChar1: function (value) {
		return String.fromCharCode(value);
	}
	,
	toDouble6: function (value, provider) {
		return $.ig.util.parseNumber(value, provider);
	}
	,
	toUInt16: function (value) {
		return (value ? 1 : 0);
	}
	,
	toBoolean: function (value) {
		return value != 0;
	}
	,
	toUInt32: function (value) {
		return value;
	}
	,
	fromBase64String: function (s) {
		return $.ig.util.b64toUint8Array(s);
	}
	,
	toBase64String: function (inArray) {
		return $.ig.util.uint8ArraytoB64(inArray);
	}
	,
	toByte2: function (value, fromBase) {
		throw new $.ig.NotImplementedException(0);
		return 0;
	}
	,
	$type: new $.ig.Type('Convert', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BinaryReader', 'Object', {
	__data: null,
	__isBigEndian: false,
	__currOffset: 0,
	canRead: function () {
		return this.__currOffset < this.__data.length;
	}
	,
	currentPosition: function () {
		return this.__currOffset;
	}
	,
	length: function () {
		if (this.__useStream) {
			return this.__stream.length();
		}
		return this.__data.length;
	}
	,
	__useArray: false,
	__useStream: false,
	init: function (initNumber, dataString, isBigEndian) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		this.__data = null;
		this.__isBigEndian = false;
		this.__currOffset = 0;
		this.__useArray = false;
		this.__useStream = false;
		$.ig.Object.prototype.init.call(this);
		this.__data = dataString;
		this.__isBigEndian = isBigEndian;
		var data_ = this.__data;
		if (typeof Uint8Array != 'undefined' && data_ instanceof Uint8Array) {
			this.__useArray = true;
		}
	},
	getByte: function (offset_) {
		if (this.__useStream) {
			this.__stream.position(offset_);
			return this.__stream.readByte();
		} else if (this.__useArray) {
			return this.__data[offset_] & 0xFF;
		} else {
			return this.__data.charCodeAt(offset_) & 0xFF;
		}
	}
	,
	getBytes: function (offset_, length) {
		var ret = new Array(length);
		if (this.__useStream) {
			this.__stream.position(offset_);
			this.__stream.read(ret, 0, length);
		} else if (this.__useArray) {
			for (var i_ = 0; i_ < length; i_++) {
				ret[i_] = this.__data[offset_ + i_] & 0xFF;
			}
		} else {
			for (var i_ = 0; i_ < length; i_++) {
				ret[i_] = this.__data.charCodeAt(offset_ + i_) & 0xFF;
			}
		}
		return ret;
	}
	,
	getBytesBackwards: function (offset_, length_) {
		var ret = new Array(length_);
		if (this.__useStream) {
			this.__stream.position(offset_);
			this.__stream.read(ret, 0, length_);
			$.ig.Array.prototype.reverse(ret);
		} else if (this.__useArray) {
			for (var i_ = 0; i_ < length_; i_++) {
				ret[i_] = this.__data[offset_ + (length_ - 1 - i_)] & 0xFF;
			}
		} else {
			for (var i_ = 0; i_ < length_; i_++) {
				ret[i_] = this.__data.charCodeAt(offset_ + (length_ - 1 - i_)) & 0xFF;
			}
		}
		return ret;
	}
	,
	readByte: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var ret = this.getByte(this.__currOffset);
		this.__currOffset = this.__currOffset + 1;
		return ret;
	}
	,
	readBytes: function (count) {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var ret = this.getBytes(this.__currOffset, count);
		this.__currOffset = this.__currOffset + count;
		return ret;
	}
	,
	readUInt32: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var num = 0;
		if (this.__isBigEndian) {
			num = num + (this.getByte(this.__currOffset) << 24);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 16);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
		} else {
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 16);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 24);
			this.__currOffset = this.__currOffset + 1;
		}
		if (num < 0) {
			num = num + 0xFFFFFFFF + 1;
		}
		return num;
	}
	,
	readUInt16: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var num = 0;
		if (this.__isBigEndian) {
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
		} else {
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
		}
		return num;
	}
	,
	readInt32: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var num = 0;
		if (this.__isBigEndian) {
			num = num + (this.getByte(this.__currOffset) << 24);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 16);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
		} else {
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 16);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 24);
			this.__currOffset = this.__currOffset + 1;
		}
		if (num > 2147483647) {
			num = num - 0xFFFFFFFF - 1;
		}
		return num;
	}
	,
	readDouble: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var bytes = this.getBytesBackwards(this.__currOffset, 8);
		this.__currOffset = this.__currOffset + 8;
		var sign = bytes[0] >>> 7;
		var exponent = 0;
		exponent = exponent + (bytes[1] >>> 4);
		exponent = exponent + ((bytes[0] & 127) << 4);
		var fraction = 1;
		var currByte = bytes[1];
		var factor = 1 / 2;
		var currPos = 0;
		var hasFraction = false;
		for (currPos = 5; currPos <= 8; currPos++) {
			if ((currByte & (1 << (8 - currPos))) > 0) {
				fraction = fraction + factor;
				hasFraction = true;
			}
			factor = factor / 2;
		}
		for (var bytePos = 2; bytePos < 8; bytePos++) {
			currByte = bytes[bytePos];
			for (currPos = 1; currPos <= 8; currPos++) {
				if ((currByte & (1 << (8 - currPos))) > 0) {
					fraction = fraction + factor;
					hasFraction = true;
				}
				factor = factor / 2;
			}
		}
		if (exponent == 0 && !hasFraction) {
			return 0;
		}
		if (exponent == 0 && hasFraction) {
			exponent = 1;
			fraction = fraction - 1;
		}
		if (exponent == 1860 && !hasFraction) {
			if (sign == 1) {
				return $.ig.Number.prototype.nEGATIVE_INFINITY;
			} else {
				return $.ig.Number.prototype.pOSITIVE_INFINITY;
			}
		}
		if (exponent == 1860 && hasFraction) {
			return NaN;
		}
		var biasedExponent = exponent - 1023;
		return Math.pow(-1, sign) * Math.pow(2, biasedExponent) * fraction;
	}
	,
	readSingle: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var bytes = this.getBytesBackwards(this.__currOffset, 4);
		this.__currOffset = this.__currOffset + 4;
		var sign = bytes[0] >>> 7;
		var exponent = 0;
		exponent = exponent + (bytes[1] >>> 7);
		exponent = exponent + ((bytes[0] & 127) << 1);
		var fraction = 1;
		var currByte = bytes[1];
		var factor = 1 / 2;
		var currPos = 0;
		var hasFraction = false;
		for (currPos = 2; currPos <= 8; currPos++) {
			if ((currByte & (1 << (8 - currPos))) > 0) {
				fraction = fraction + factor;
				hasFraction = true;
			}
			factor = factor / 2;
		}
		for (var bytePos = 2; bytePos < 4; bytePos++) {
			currByte = bytes[bytePos];
			for (currPos = 1; currPos <= 8; currPos++) {
				if ((currByte & (1 << (8 - currPos))) > 0) {
					fraction = fraction + factor;
					hasFraction = true;
				}
				factor = factor / 2;
			}
		}
		if (exponent == 0 && !hasFraction) {
			return 0;
		}
		if (exponent == 0 && hasFraction) {
			exponent = 1;
			fraction = fraction - 1;
		}
		if (exponent == 255 && !hasFraction) {
			if (sign == 1) {
				return $.ig.Number.prototype.nEGATIVE_INFINITY;
			} else {
				return $.ig.Number.prototype.pOSITIVE_INFINITY;
			}
		}
		if (exponent == 255 && hasFraction) {
			return NaN;
		}
		var biasedExponent = exponent - 127;
		return (Math.pow(-1, sign)) * (Math.pow(2, biasedExponent)) * fraction;
	}
	,
	__stream: null,
	init1: function (initNumber, input) {
		this.__data = null;
		this.__isBigEndian = false;
		this.__currOffset = 0;
		this.__useArray = false;
		this.__useStream = false;
		$.ig.Object.prototype.init.call(this);
		this.__stream = input;
		this.__useStream = true;
	},
	baseStream: function () {
		return this.__stream;
	}
	,
	readBoolean: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var ret = this.getByte(this.__currOffset);
		this.__currOffset = this.__currOffset + 1;
		return ret != 0;
	}
	,
	readInt16: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var num = 0;
		if (this.__isBigEndian) {
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
		} else {
			num = num + this.getByte(this.__currOffset);
			this.__currOffset = this.__currOffset + 1;
			num = num + (this.getByte(this.__currOffset) << 8);
			this.__currOffset = this.__currOffset + 1;
		}
		if (num > 32767) {
			num -= (65535 + 1);
		}
		return num;
	}
	,
	readSByte: function () {
		if (this.__useStream) {
			this.__currOffset = this.__stream.position();
		}
		var ret = this.getByte(this.__currOffset);
		this.__currOffset = this.__currOffset + 1;
		if (ret > 127) {
			return (ret - 255 + 1);
		}
		return ret;
	}
	,
	close: function () {
		(this).dispose();
	}
	,
	dispose: function () {
		if (this.__useStream) {
			this.__stream.dispose();
		}
	}
	,
	$type: new $.ig.Type('BinaryReader', $.ig.Object.prototype.$type, [$.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('BinaryFileDownloader', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	downloadFile: function (uri_, callback_, errorCallback_) {
		$.ig.util.getBinary(uri_, callback_, errorCallback_);
	}
	,
	$type: new $.ig.Type('BinaryFileDownloader', $.ig.Object.prototype.$type)
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

$.ig.UriKind.prototype.relativeOrAbsolute = 0;
$.ig.UriKind.prototype.absolute = 1;
$.ig.UriKind.prototype.relative = 2;

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
"IFastItemColumnPropertyName:du", 
"IFastItemColumn$1:dv", 
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
"DependencyObjectNotifier:gm", 
"INotifyPropertyChanged:gn", 
"PropertyChangedEventHandler:go", 
"PropertyChangedEventArgs:gp", 
"PropertyChangedEventArgs$1:gu", 
"DivElement:gy", 
"ImageElement:g1", 
"RectUtil:g2", 
"MathUtil:g3", 
"RuntimeHelpers:g4", 
"RuntimeFieldHandle:g5", 
"Random:g8", 
"ObservableCollection$1:hb", 
"INotifyCollectionChanged:hc", 
"NotifyCollectionChangedEventHandler:hd", 
"PointCollectionUtil:hh", 
"Flattener:hi", 
"Stack$1:hj", 
"ReverseArrayEnumerator$1:hk", 
"SpiralTodo:hl", 
"FlattenerSettings:hm", 
"Clipper:hn", 
"EdgeClipper:ho", 
"LeftClipper:hp", 
"BottomClipper:hq", 
"RightClipper:hr", 
"TopClipper:hs", 
"PolySimplification:ht", 
"ISmartPlaceable:hu", 
"SmartPosition:hv", 
"SmartPlaceableWrapper$1:hw", 
"SmartPlacer:hx", 
"XamMultiScaleImageView:hy", 
"XamMultiScaleImage:hz", 
"IMapRenderDeferralHandler:h0", 
"XamMultiScaleTileSource:h1", 
"Uri:h2", 
"UriKind:h3", 
"IEasingFunction:h4", 
"Tile:h5", 
"Image:h6", 
"Pair$2:h7", 
"WriteableBitmap:h8", 
"Convert:h9", 
"StackPool$1:ia", 
"Func$1:ib", 
"Debug:ic", 
"MapTileSource:id", 
"BingMapsTileSource:ig", 
"StringBuilder:ih", 
"Environment:ii", 
"CloudMadeTileSource:ij", 
"OpenStreetMapTileSource:ik", 
"ErrorBarCalculatorReference:it", 
"ErrorBarCalculatorType:iu", 
"IErrorBarCalculator:iv", 
"Triangle:iy", 
"TriangulationSource:iz", 
"TriangulationSourcePointRecord:i0", 
"Triangulator:i1", 
"TriangulatorContext:i2", 
"LinkedList$1:i3", 
"LinkedListNode$1:i4", 
"HalfEdgeSet:i5", 
"HalfEdge:i6", 
"EdgeComparer:i7", 
"PointTester:i8", 
"TriangulationStatusEventHandler:i9", 
"TriangulationStatusEventArgs:ja", 
"BinaryReader:jb", 
"RearrangedList$1:js", 
"AbstractEnumerable:kp", 
"AbstractEnumerator:kq", 
"GenericEnumerable$1:kr", 
"GenericEnumerator$1:ks"]);


$.ig.util.defType('ErrorBarCalculatorType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Fixed";
			case 1: return "Percentage";
			case 2: return "Data";
			case 3: return "StandardDeviation";
			case 4: return "StandardError";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ErrorBarCalculatorType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('ErrorBarCalculatorReference', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "X";
			case 1: return "Y";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ErrorBarCalculatorReference', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('SmartPosition', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "LeftTop";
			case 1: return "CenterTop";
			case 2: return "RightTop";
			case 3: return "LeftCenter";
			case 4: return "CenterCenter";
			case 5: return "RightCenter";
			case 6: return "LeftBottom";
			case 7: return "CenterBottom";
			case 8: return "RightBottom";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('SmartPosition', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('DependencyObjectNotifier', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	onPropertyChanged: function (propertyName) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('DependencyObjectNotifier', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('PointCollectionUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	flattenTo: function (points, list, E) {
		if (list == null) {
			return;
		}
		list.clear();
		if (points.count() >= 2) {
			var indices = $.ig.Flattener.prototype.flatten3(points.count(), function (i) {
				return points.item(i).__x;
			}, function (i) {
				return points.item(i).__y;
			}, E);
			var en = indices.getEnumerator();
			while (en.moveNext()) {
				var i = en.current();
				list.add(points.item(i));
			}
		}
	}
	,
	getBounds: function (points) {
		var xmin = Number.POSITIVE_INFINITY;
		var ymin = Number.POSITIVE_INFINITY;
		var xmax = Number.NEGATIVE_INFINITY;
		var ymax = Number.NEGATIVE_INFINITY;
		var en = points.getEnumerator();
		while (en.moveNext()) {
			var point = en.current();
			xmin = Math.min(xmin, point.__x);
			ymin = Math.min(ymin, point.__y);
			xmax = Math.max(xmax, point.__x);
			ymax = Math.max(ymax, point.__y);
		}
		if (Number.isInfinity(xmin) || Number.isInfinity(ymin) || Number.isInfinity(ymin) || Number.isInfinity(ymax)) {
			return $.ig.Rect.prototype.empty();
		}
		return new $.ig.Rect(0, xmin, ymin, xmax - xmin, ymax - ymin);
	}
	,
	getBounds1: function (points) {
		var result = $.ig.Rect.prototype.empty();
		var en = points.getEnumerator();
		while (en.moveNext()) {
			var ring = en.current();
			result.union($.ig.PointCollectionUtil.prototype.getBounds(ring));
		}
		return result;
	}
	,
	getBounds2: function (points) {
		var xmin = Number.POSITIVE_INFINITY;
		var ymin = Number.POSITIVE_INFINITY;
		var xmax = Number.NEGATIVE_INFINITY;
		var ymax = Number.NEGATIVE_INFINITY;
		var p;
		for (var i = 0; i < points.count(); i++) {
			p = points.item(i);
			xmin = Math.min(xmin, p.__x);
			ymin = Math.min(ymin, p.__y);
			xmax = Math.max(xmax, p.__x);
			ymax = Math.max(ymax, p.__y);
		}
		if (Number.isInfinity(xmin) || Number.isInfinity(ymin) || Number.isInfinity(ymin) || Number.isInfinity(ymax)) {
			return $.ig.Rect.prototype.empty();
		}
		return new $.ig.Rect(0, xmin, ymin, xmax - xmin, ymax - ymin);
	}
	,
	getBounds3: function (points) {
		var result = $.ig.Rect.prototype.empty();
		var ring;
		for (var i = 0; i < points.count(); i++) {
			ring = points.__inner[i];
			result.union($.ig.PointCollectionUtil.prototype.getBounds2(ring));
		}
		return result;
	}
	,
	getBounds4: function (points) {
		var result = $.ig.Rect.prototype.empty();
		var ring;
		for (var i = 0; i < points.count(); i++) {
			ring = points.__inner[i];
			result.union($.ig.PointCollectionUtil.prototype.getBounds2(ring));
		}
		return result;
	}
	,
	clipTo: function (points, list, clipper) {
		var pointCount = points.count();
		for (var i = 0; i < pointCount; i++) {
			clipper.add(points.item(i));
		}
		clipper.target(null);
	}
	,
	getCentroid: function (points) {
		var x = 0;
		var y = 0;
		var c = 0;
		var en = points.getEnumerator();
		while (en.moveNext()) {
			var point = en.current();
			x += point.__x;
			y += point.__y;
			c += 1;
		}
		return { __x: x / c, __y: y / c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	toPointCollection: function (points) {
		var result = new $.ig.PointCollection(0);
		var en = points.getEnumerator();
		while (en.moveNext()) {
			var p = en.current();
			result.add(p);
		}
		return result;
	}
	,
	toPointList: function (points) {
		var result = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		var en = points.getEnumerator();
		while (en.moveNext()) {
			var p = en.current();
			result.add(p);
		}
		return result;
	}
	,
	toPointCollections: function (points) {
		var ret = new $.ig.List$1($.ig.PointCollection.prototype.$type, 0);
		var pointColl;
		var count = points.count();
		for (var i = 0; i < count; i++) {
			pointColl = points.__inner[i];
			var coll = new $.ig.PointCollection(1, pointColl);
			ret.add(coll);
		}
		return ret;
	}
	,
	$type: new $.ig.Type('PointCollectionUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('PolySimplification', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	vertexReduction: function (points, tolerance) {
		var x = points.item1();
		var y = points.item2();
		if (x.length == 0) {
			return 0;
		}
		var insertIndex = 0;
		var refIndex = 0;
		var squareTolerance = tolerance * tolerance;
		insertIndex++;
		var dx;
		var dy;
		var dist;
		for (var i = 0; i < x.length; i++) {
			dx = x[i] - x[refIndex];
			dy = y[i] - y[refIndex];
			dist = (dx * dx) + (dy * dy);
			if (dist > squareTolerance) {
				x[insertIndex] = x[i];
				y[insertIndex] = y[i];
				insertIndex++;
				refIndex = i;
			}
		}
		return insertIndex;
	}
	,
	squareDistance: function (p1, p2) {
		var dx = p2.__x - p1.__x;
		var dy = p2.__y - p1.__y;
		return (dx * dx) + (dy * dy);
	}
	,
	$type: new $.ig.Type('PolySimplification', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ISmartPlaceable', 'Object', {
	$type: new $.ig.Type('ISmartPlaceable', null)
}, true);

$.ig.util.defType('SmartPlaceableWrapper$1', 'Object', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		this._elementDesiredSize = new $.ig.Size();
		$.ig.Object.prototype.init.call(this);
		this.noWiggle(false);
	},
	_noWiggle: false,
	noWiggle: function (value) {
		if (arguments.length === 1) {
			this._noWiggle = value;
			return value;
		} else {
			return this._noWiggle;
		}
	}
	,
	__element: null,
	element: function (value) {
		if (arguments.length === 1) {
			this.__element = value;
			return value;
		} else {
			return this.__element;
		}
	}
	,
	_elementLocationResult: null,
	elementLocationResult: function (value) {
		if (arguments.length === 1) {
			this._elementLocationResult = value;
			return value;
		} else {
			return this._elementLocationResult;
		}
	}
	,
	_originalLocation: null,
	originalLocation: function (value) {
		if (arguments.length === 1) {
			this._originalLocation = value;
			return value;
		} else {
			return this._originalLocation;
		}
	}
	,
	getSmartPositions: function () {
		if (this.noWiggle()) {
			return this.$type.getStaticFields($.ig.SmartPlaceableWrapper$1.prototype.$type)._smartPositionDefault;
		} else {
			return this.$type.getStaticFields($.ig.SmartPlaceableWrapper$1.prototype.$type)._smartPositions;
		}
	}
	,
	_elementDesiredSize: null,
	elementDesiredSize: function (value) {
		if (arguments.length === 1) {
			this._elementDesiredSize = value;
			return value;
		} else {
			return this._elementDesiredSize;
		}
	}
	,
	getSmartElementSize: function () {
		return this.elementDesiredSize();
	}
	,
	getSmartBounds: function (position) {
		var s = this.getSmartElementSize();
		var w = s.width();
		var h = s.height();
		var d;
		{
			d = this.getOffset(position, w, h);
		}
		return new $.ig.Rect(0, this.originalLocation().__x + d.__x, this.originalLocation().__y + d.__y, w, h);
	}
	,
	opacity: function (value) {
		if (arguments.length === 1) {
			this.element().__opacity = value;
			return value;
		} else {
			return this.element().__opacity;
		}
	}
	,
	smartPosition: function (value) {
		if (arguments.length === 1) {
			this._smartPosition = value;
			var s = this.getSmartElementSize();
			var h = s.height();
			var w = s.width();
			var d;
			{
				d = this.getOffset(this._smartPosition, w, h);
			}
			this.elementLocationResult({ __x: this.originalLocation().__x + d.__x, __y: this.originalLocation().__y + d.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			return value;
		} else {
			return this._smartPosition;
		}
	}
	,
	_smartPosition: 0,
	getOffset: function (position, w, h) {
		var c = 0.25;
		switch (position) {
			case $.ig.SmartPosition.prototype.leftTop: return { __x: -w * c, __y: -h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.centerTop: return { __x: 0, __y: -h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.rightTop: return { __x: w * c, __y: -h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.leftCenter: return { __x: -w * c, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.centerCenter: return { __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.rightCenter: return { __x: w * c, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.leftBottom: return { __x: -w * c, __y: h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			case $.ig.SmartPosition.prototype.centerBottom: return { __x: 0, __y: h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			default: return { __x: w * c, __y: h * c, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
	}
	,
	$type: new $.ig.Type('SmartPlaceableWrapper$1', $.ig.Object.prototype.$type, [$.ig.ISmartPlaceable.prototype.$type], function () {
		this._smartPositionDefault = [ $.ig.SmartPosition.prototype.centerCenter ];
		this._smartPositions = [ $.ig.SmartPosition.prototype.centerCenter, $.ig.SmartPosition.prototype.rightCenter, $.ig.SmartPosition.prototype.rightTop, $.ig.SmartPosition.prototype.centerTop, $.ig.SmartPosition.prototype.rightBottom, $.ig.SmartPosition.prototype.centerBottom, $.ig.SmartPosition.prototype.leftTop, $.ig.SmartPosition.prototype.leftCenter, $.ig.SmartPosition.prototype.leftBottom ];
	})
}, true);

$.ig.util.defType('SmartPlacer', 'Object', {
	init: function () {
		this._placed = new $.ig.List$1($.ig.Rect.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
		this.bounds($.ig.Rect.prototype.empty());
		this.overlap(0.3);
		this.fade(2);
	},
	_bounds: null,
	bounds: function (value) {
		if (arguments.length === 1) {
			this._bounds = value;
			return value;
		} else {
			return this._bounds;
		}
	}
	,
	_overlap: 0,
	overlap: function (value) {
		if (arguments.length === 1) {
			this._overlap = value;
			return value;
		} else {
			return this._overlap;
		}
	}
	,
	_fade: 0,
	fade: function (value) {
		if (arguments.length === 1) {
			this._fade = value;
			return value;
		} else {
			return this._fade;
		}
	}
	,
	place: function (smartPlaceable) {
		if (smartPlaceable == null) {
			return;
		}
		var minScore = 1.7976931348623157E+308;
		var minBounds = $.ig.Rect.prototype.empty();
		var minPosition = $.ig.SmartPosition.prototype.centerBottom;
		var hasMinPosition = false;
		var $t = smartPlaceable.getSmartPositions();
		for (var i = 0; i < $t.length; i++) {
			var position = $t[i];
			var bounds = smartPlaceable.getSmartBounds(position);
			if (this.bounds().isEmpty() || this.bounds().containsRect(bounds)) {
				var score = 0;
				var en = this._placed.getEnumerator();
				while (en.moveNext()) {
					var rect = en.current();
					score += $.ig.RectUtil.prototype.intersectionArea(bounds, rect);
				}
				if (score == 0) {
					minScore = score;
					minPosition = position;
					minBounds = bounds;
					hasMinPosition = true;
					break;
				}
				if (score < minScore) {
					minScore = score;
					minPosition = position;
					minBounds = bounds;
					hasMinPosition = true;
				}
			}
		}
		var overlap = 0;
		if (hasMinPosition) {
			overlap = minScore / $.ig.RectUtil.prototype.getArea(minBounds);
		}
		if (!hasMinPosition || overlap > this.overlap()) {
			smartPlaceable.opacity(0);
		} else {
			if (minScore > 0) {
				smartPlaceable.opacity(Math.pow(1 - $.ig.MathUtil.prototype.clamp(0, overlap, 1), this.fade()));
			} else {
				smartPlaceable.opacity(1);
			}
			smartPlaceable.smartPosition(minPosition);
			this._placed.add(minBounds);
		}
	}
	,
	_placed: null,
	$type: new $.ig.Type('SmartPlacer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamMultiScaleImageView', 'Object', {
	_model: null,
	model: function (value) {
		if (arguments.length === 1) {
			this._model = value;
			return value;
		} else {
			return this._model;
		}
	}
	,
	init: function (model) {
		var $self = this;
		this._pendingTiles = new $.ig.List$1($.ig.Tile.prototype.$type, 0);
		this._downloadTile = new $.ig.List$1($.ig.Tile.prototype.$type, 0);
		this._springInterval = $.ig.XamMultiScaleImageView.prototype._emptyInterval;
		this._fadeInterval = $.ig.XamMultiScaleImageView.prototype._emptyInterval;
		this.__previousRender = $.ig.Rect.prototype.empty();
		$.ig.Object.prototype.init.call(this);
		this.model(model);
		this.model().tileScheduler().register(this);
		this.displayedImages(new $.ig.List$1($.ig.Image.prototype.$type, 0));
		this.model()._imagePool = (function () {
			var $ret = new $.ig.StackPool$1($.ig.Image.prototype.$type);
			$ret.create($self.image_Create.runOn($self));
			$ret.activate($self.image_Activate.runOn($self));
			$ret.deactivate($self.image_Disactivate.runOn($self));
			$ret.destroy($self.image_Destroy.runOn($self));
			return $ret;
		}());
		for (var i = 0; i < 4; ++i) {
			this._downloadTile.add(null);
		}
	},
	_displayedImages: null,
	displayedImages: function (value) {
		if (arguments.length === 1) {
			this._displayedImages = value;
			return value;
		} else {
			return this._displayedImages;
		}
	}
	,
	image_Create: function () {
		return new $.ig.Image();
	}
	,
	image_Activate: function (image) {
		this.displayedImages().add(image);
	}
	,
	image_Disactivate: function (image) {
		this.displayedImages().remove(image);
	}
	,
	image_Destroy: function (image) {
	}
	,
	setImagePosition: function (image, il, it) {
		image.canvasLeft(il);
		image.canvasTop(it);
	}
	,
	cancelDownload: function (tile) {
		for (var i = 0; i < this._downloadTile.count(); ++i) {
			if (tile == this._downloadTile.__inner[i]) {
				if (tile._image != null && tile._image.source() != null) {
					var bmp = tile._image.source();
					var ele = bmp.source();
					var jo = $(ele);
					jo.unbind("load");
					jo.unbind("readystatechange");
				}
				this._downloadTile.__inner[i] = null;
				return;
			}
		}
		for (var i1 = 0; i1 < this._pendingTiles.count(); ++i1) {
			if (this._pendingTiles.__inner[i1] == tile) {
				this._pendingTiles.removeAt(i1);
				break;
			}
		}
	}
	,
	download: function (tile) {
		this._pendingTiles.add(tile);
		this._pendingTiles.sort2(function (a, b) {
			var sa = 0;
			var sb = 0;
			if (a._ghostImage != null) {
				sa = (a._ghostImage.source()).width();
			}
			if (b._ghostImage != null) {
				sb = (b._ghostImage.source()).width();
			}
			if (sa < sb) {
				return -1;
			} else if (sa > sb) {
				return 1;
			}
			return 0;
		});
		this.bumpDownload();
	}
	,
	bumpDownload: function () {
		var index_ = -1;
		if (this._pendingTiles.count() > 0) {
			for (var i = 0; i < this._downloadTile.count(); ++i) {
				if (this._downloadTile.__inner[i] == null) {
					index_ = i;
					break;
				}
			}
		}
		if (index_ >= 0) {
			this._downloadTile.__inner[index_] = this._pendingTiles.__inner[0];
			var bmp = new $.ig.WriteableBitmap(this.model().source().tileWidth(), this.model().source().tileHeight());
			this._downloadTile.__inner[index_]._image.source(bmp);
			var ele_ = $("<img></img>");
			var img = ele_[0];
			bmp.source(img);
			var self_ = this;
			ele_.bind('load readystatechange', function(e) { if (this.complete || (this.readyState == 'complete' && e.type == 'readystatechange')) { self_.downloadCompleted(e, index_) }});
			ele_.bind('error', function(e) { self_.downloadError(e, index_); });
			this._pendingTiles.removeAt(0);
			var uri = this.model().source().getTileUri(this._downloadTile.__inner[index_]._z + 8, this._downloadTile.__inner[index_]._x, this._downloadTile.__inner[index_]._y);
			img.src = uri.value();
		}
	}
	,
	_pendingTiles: null,
	_downloadTile: null,
	downloadError: function (e, index) {
		var downloadTile = this._downloadTile.__inner[index];
		this._downloadTile.__inner[index] = null;
		if (downloadTile != null && downloadTile._image != null && downloadTile._image.source() != null) {
			var img = $(downloadTile._image.source());
			img.unbind("load");
			img.unbind("readystatechange");
		}
		this.bumpDownload();
		this.makeDirty();
	}
	,
	downloadCompleted: function (e, index) {
		var downloadTile = this._downloadTile.__inner[index];
		var isInvalid = true;
		if (downloadTile != null && downloadTile._image != null && downloadTile._image.source() != null) {
			isInvalid = false;
			var ele_ = ((downloadTile._image.source()).source());
			if (!ele_.complete) {
				isInvalid = true;
			}
			if (ele_.width == 0 && ele_.height == 0) {
				isInvalid = true;
			}
		}
		this._downloadTile.__inner[index] = null;
		if (isInvalid) {
			this.bumpDownload();
			this.makeDirty();
			return;
		}
		var img = $(downloadTile._image.source());
		img.unbind("load");
		img.unbind("readystatechange");
		this.model().cacheBitmap(downloadTile, downloadTile._image.source());
		if (downloadTile._image != null) {
			this.model().startFade(downloadTile);
		}
		this.bumpDownload();
		this.makeDirty();
	}
	,
	getGhostImage: function (size, donor, left, top) {
		var bitmap = new $.ig.WriteableBitmap(size, size);
		bitmap.source(donor.source());
		if (!donor.subDimensions().isEmpty()) {
			left += $.ig.truncate(Math.round(donor.subDimensions().left()));
			top += $.ig.truncate(Math.round(donor.subDimensions().top()));
		}
		bitmap.subDimensions(new $.ig.Rect(0, left, top, size, size));
		return bitmap;
	}
	,
	sendToBackground: function (image) {
		image.canvasZIndex(0);
	}
	,
	sendToForeground: function (image) {
		image.canvasZIndex(1);
	}
	,
	ready: function () {
		return true;
	}
	,
	defer: function (work) {
		if (this.model().deferralHandler() != null) {
			this.model().deferralHandler().deferredRefresh();
		} else {
			window.setTimeout(work, 0);
		}
	}
	,
	_springInterval: 0,
	_fadeInterval: 0,
	startSpringTimer: function () {
		if (this._springInterval == $.ig.XamMultiScaleImageView.prototype._emptyInterval) {
			this._springInterval = window.setInterval(this.model().springTimer_Tick.runOn(this.model()), 50);
		}
	}
	,
	stopSpringTimer: function () {
		if (this._springInterval != $.ig.XamMultiScaleImageView.prototype._emptyInterval) {
			window.clearInterval(this._springInterval);
			this._springInterval = $.ig.XamMultiScaleImageView.prototype._emptyInterval;
		}
	}
	,
	startFadeTimer: function () {
		if (this._fadeInterval == $.ig.XamMultiScaleImageView.prototype._emptyInterval) {
			this._fadeInterval = window.setInterval(this.model().fadeTimer_Tick.runOn(this.model()), 50);
		}
	}
	,
	stopFadeTimer: function () {
		if (this._fadeInterval != $.ig.XamMultiScaleImageView.prototype._emptyInterval) {
			window.clearInterval(this._fadeInterval);
			this._fadeInterval = $.ig.XamMultiScaleImageView.prototype._emptyInterval;
		}
	}
	,
	disableSprings: function () {
		if (this._springInterval != $.ig.XamMultiScaleImageView.prototype._emptyInterval) {
			this.stopFadeTimer();
			this.model().onSpringsDisabled();
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
	_mainCanvas: null,
	mainCanvas: function (value) {
		if (arguments.length === 1) {
			this._mainCanvas = value;
			return value;
		} else {
			return this._mainCanvas;
		}
	}
	,
	_mainContext: null,
	mainContext: function (value) {
		if (arguments.length === 1) {
			this._mainContext = value;
			return value;
		} else {
			return this._mainContext;
		}
	}
	,
	onContainerProvided: function (container) {
		this.container($(container));
		this.container().css("position", "relative");
		this.mainCanvas($("<canvas style=\"position : absolute; top : 0; left : 0\" />"));
		this.container().append(this.mainCanvas());
		this.mainContext(new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), (this.mainCanvas()[0]).getContext("2d")));
		this.onContainerResized(this.container().width(), this.container().height());
	}
	,
	onContainerResized: function (width, height) {
		this.mainCanvas().attr("width", width.toString());
		this.mainCanvas().attr("height", height.toString());
		this.model().canvasSize(new $.ig.Rect(0, 0, 0, width, height));
		this.model().refresh();
	}
	,
	refreshCompleted: function () {
		this.makeDirty();
	}
	,
	index: function () {
		return 0;
	}
	,
	postRender: function () {
	}
	,
	_isDirty: false,
	isDirty: function (value) {
		if (arguments.length === 1) {
			this._isDirty = value;
			return value;
		} else {
			return this._isDirty;
		}
	}
	,
	makeDirty: function () {
		if (this.mainContext() == null) {
			return;
		}
		if (!this.isDirty()) {
			this.isDirty(true);
			this.model().tileScheduler().schedule();
		}
	}
	,
	undirty: function (clearRect) {
		this.isDirty(false);
		this.render();
	}
	,
	__previousRender: null,
	render: function () {
		if (this.mainContext() == null) {
			return;
		}
		if (!this.__previousRender.isEmpty()) {
			this.mainContext().clearRectangle(this.__previousRender.left(), this.__previousRender.top(), this.__previousRender.width(), this.__previousRender.height());
		}
		this.__previousRender = this.model().canvasSize();
		for (var i = 0; i < this.displayedImages().count(); i++) {
			var image = this.displayedImages().__inner[i];
			if (image.canvasZIndex() == 0) {
				this.renderImage(image);
			}
		}
		for (var i1 = 0; i1 < this.displayedImages().count(); i1++) {
			var image1 = this.displayedImages().__inner[i1];
			if (image1.canvasZIndex() == 1) {
				this.renderImage(image1);
			}
		}
		this.model().notifyReady();
		this.model().onImagesChanged();
	}
	,
	imagesReady: function () {
		var ready = true;
		for (var i = 0; i < this._downloadTile.count(); i++) {
			if (this._downloadTile.__inner[i] != null) {
				ready = false;
			}
		}
		return ready;
	}
	,
	renderImage: function (image) {
		if (this.mainContext() == null) {
			return;
		}
		var bitmap = image.source();
		var opacity = image.__opacity;
		if (bitmap == null || bitmap.source() == null) {
			return;
		}
		if (this.isInvalid(bitmap.source())) {
			return;
		}
		if (!bitmap.subDimensions().isEmpty()) {
			if (bitmap.subDimensions().width() < 1 || bitmap.subDimensions().height() < 1) {
				return;
			}
			this.mainContext().drawImage1(bitmap.source(), opacity, bitmap.subDimensions().left(), bitmap.subDimensions().top(), bitmap.subDimensions().width(), bitmap.subDimensions().height(), Math.round(image.canvasLeft() + this.model().canvasSize().left()), Math.round(image.canvasTop() + this.model().canvasSize().top()), image.width(), image.height());
		} else {
			this.mainContext().drawImage(bitmap.source(), opacity, Math.round(image.canvasLeft() + this.model().canvasSize().left()), Math.round(image.canvasTop() + this.model().canvasSize().top()), image.width(), image.height());
		}
	}
	,
	isInvalid: function (image) {
		var img_ = image;
		if (!img_.complete) {
			return true;
		}
		if (img_.width == 0 && img_.height == 0) {
			return true;
		}
		return false;
	}
	,
	fadingChanged: function () {
		this.makeDirty();
	}
	,
	onContextProvided: function (context) {
		this.mainContext(context);
		this.makeDirty();
	}
	,
	onViewportProvided: function (mapViewport) {
		this.model().canvasSize(mapViewport);
		this.model().refresh();
	}
	,
	preRender: function () {
	}
	,
	isValid: function () {
		return true;
	}
	,
	$type: new $.ig.Type('XamMultiScaleImageView', $.ig.Object.prototype.$type, [$.ig.ISchedulableRender.prototype.$type])
}, true);

$.ig.util.defType('XamMultiScaleTileSource', 'DependencyObject', {
	init: function (imageWidth, imageHeight, tileWidth, tileHeight, tileOverlap) {
		$.ig.DependencyObject.prototype.init.call(this);
		this.__imageWidth = imageWidth;
		this.__imageHeight = imageHeight;
		this.tileWidth(tileWidth);
		this.tileHeight(tileHeight);
		this.tileOverlap(tileOverlap);
	},
	__imageWidth: 0,
	imageWidth: function (value) {
		if (arguments.length === 1) {
			this.__imageWidth = value;
			this.invalidateTileLayer(0, 0, 0, 0);
			return value;
		} else {
			return this.__imageWidth;
		}
	}
	,
	__imageHeight: 0,
	imageHeight: function (value) {
		if (arguments.length === 1) {
			this.__imageHeight = value;
			this.invalidateTileLayer(0, 0, 0, 0);
			return value;
		} else {
			return this.__imageHeight;
		}
	}
	,
	_tileWidth: 0,
	tileWidth: function (value) {
		if (arguments.length === 1) {
			this._tileWidth = value;
			return value;
		} else {
			return this._tileWidth;
		}
	}
	,
	_tileHeight: 0,
	tileHeight: function (value) {
		if (arguments.length === 1) {
			this._tileHeight = value;
			return value;
		} else {
			return this._tileHeight;
		}
	}
	,
	_tileOverlap: 0,
	tileOverlap: function (value) {
		if (arguments.length === 1) {
			this._tileOverlap = value;
			return value;
		} else {
			return this._tileOverlap;
		}
	}
	,
	_multiScaleImage: null,
	getTileUri: function (tileLevel, tilePositionX, tilePositionY) {
		var tileImageLayerSources = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		this.getTileLayers(tileLevel, tilePositionX, tilePositionY, tileImageLayerSources);
		var uri = null;
		if (tileImageLayerSources.count() > 0) {
			uri = $.ig.util.cast($.ig.Uri.prototype.$type, tileImageLayerSources.__inner[0]);
		}
		return uri;
	}
	,
	getTileLayers: function (tileLevel, tilePositionX, tilePositionY, tileImageLayerSources) {
	}
	,
	invalidateTileLayer: function (level, tilePositionX, tilePositionY, tileLayer) {
		if (this._multiScaleImage != null) {
			this._multiScaleImage.invalidateTileLayer(level, tilePositionX, tilePositionY, tileLayer);
		}
	}
	,
	$type: new $.ig.Type('XamMultiScaleTileSource', $.ig.DependencyObject.prototype.$type)
}, true);

$.ig.util.defType('MapTileSource', 'XamMultiScaleTileSource', {
	init: function (imageWidth, imageHeight, tileWidth, tileHeight, tileOverlap) {
		$.ig.XamMultiScaleTileSource.prototype.init.call(this, imageWidth, imageHeight, tileWidth, tileHeight, tileOverlap);
	},
	$type: new $.ig.Type('MapTileSource', $.ig.XamMultiScaleTileSource.prototype.$type)
}, true);

$.ig.util.defType('BingMapsTileSource', 'MapTileSource', {
	init: function (initNumber) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.MapTileSource.prototype.init.call(this, 256 << 22, 256 << 22, 256, 256, 0);
	},
	init1: function (initNumber, tilePath, subDomains) {
		$.ig.BingMapsTileSource.prototype.init.call(this, 0);
		this.tilePath(tilePath);
		this.subDomains(subDomains);
	},
	tilePath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsTileSource.prototype.tilePathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsTileSource.prototype.tilePathProperty);
		}
	}
	,
	subDomains: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsTileSource.prototype.subDomainsProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsTileSource.prototype.subDomainsProperty);
		}
	}
	,
	subdomains_CollectionChanged: function (sender, e) {
	}
	,
	__cultureName: null,
	cultureName: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsTileSource.prototype.cultureNameProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsTileSource.prototype.cultureNameProperty);
		}
	}
	,
	getTileLayers: function (tileLevel, tilePositionX, tilePositionY, tileImageLayerSources) {
		if (!this.validateTileSourceProperties()) {
			tileImageLayerSources.clear();
			return;
		}
		if (this.tilePath() == null) {
			return;
		}
		tileLevel -= 8;
		if (tileLevel > 0) {
			var quadKey = this.getQuadKey(tileLevel, tilePositionX, tilePositionY);
			var uriString = this.tilePath();
			uriString = $.ig.util.replace(uriString, "{culture}", this.__cultureName);
			uriString = $.ig.util.replace(uriString, "{quadkey}", quadKey);
			var index = $.ig.Number.prototype.parseInt(quadKey.substr(quadKey.length - 1, 1));
			uriString = $.ig.util.replace(uriString, "{subdomain}", this.subDomains().__inner[index]);
			uriString = $.ig.util.replace(uriString, "&token={token}", "");
			tileImageLayerSources.add(new $.ig.Uri(0, uriString));
		}
	}
	,
	validateTileSourceProperties: function () {
		var isValid = true;
		return isValid;
	}
	,
	getQuadKey: function (tileLevel, tilePositionX, tilePositionY) {
		var quadKey = new $.ig.StringBuilder(0);
		for (var i = tileLevel; i > 0; --i) {
			var digit = '0';
			var mask = 1 << (i - 1);
			if ((tilePositionX & mask) != 0) {
				(function () { var $value = digit; digit = String.fromCharCode($value.charCodeAt(0) + 1); return $value; }());
			}
			if ((tilePositionY & mask) != 0) {
				(function () { var $value = digit; digit = String.fromCharCode($value.charCodeAt(0) + 1); return $value; }());
				(function () { var $value = digit; digit = String.fromCharCode($value.charCodeAt(0) + 1); return $value; }());
			}
			quadKey.append1(digit);
		}
		return quadKey.toString();
	}
	,
	onPropertyChanged: function (d, e) {
		var bing = d;
		if ((e.property() == $.ig.BingMapsTileSource.prototype.subDomainsProperty) || (e.property() == $.ig.BingMapsTileSource.prototype.tilePathProperty) | (e.property() == $.ig.BingMapsTileSource.prototype.cultureNameProperty)) {
			if (e.property() == $.ig.BingMapsTileSource.prototype.subDomainsProperty) {
				var oldSubdomains = $.ig.util.cast($.ig.ObservableCollection$1.prototype.$type.specialize(String), e.oldValue());
				var subdomains = $.ig.util.cast($.ig.ObservableCollection$1.prototype.$type.specialize(String), e.newValue());
				if (oldSubdomains != null) {
					oldSubdomains.collectionChanged = $.ig.Delegate.prototype.remove(oldSubdomains.collectionChanged, bing.subdomains_CollectionChanged.runOn(bing));
				}
				if (subdomains != null) {
					subdomains.collectionChanged = $.ig.Delegate.prototype.combine(subdomains.collectionChanged, bing.subdomains_CollectionChanged.runOn(bing));
				}
			}
			if (e.property() == $.ig.BingMapsTileSource.prototype.cultureNameProperty) {
				bing.__cultureName = e.newValue();
			}
			bing.invalidateTileLayer(0, 0, 0, 0);
		}
	}
	,
	$type: new $.ig.Type('BingMapsTileSource', $.ig.MapTileSource.prototype.$type)
}, true);

$.ig.util.defType('CloudMadeTileSource', 'MapTileSource', {
	__random: null,
	init: function () {
		$.ig.MapTileSource.prototype.init.call(this, 134217728, 134217728, 256, 256, 0);
		this.__random = new $.ig.Random(0);
	},
	key: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CloudMadeTileSource.prototype.keyProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CloudMadeTileSource.prototype.keyProperty);
		}
	}
	,
	parameter: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CloudMadeTileSource.prototype.parameterProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CloudMadeTileSource.prototype.parameterProperty);
		}
	}
	,
	getTileLayers: function (tileLevel, tilePositionX, tilePositionY, tileImageLayerSources) {
		var zoom = tileLevel - 8;
		if (zoom > 0) {
			var servers = [ "a", "b", "c" ];
			var uriString = $.ig.CloudMadeTileSource.prototype._tilePathMapnik;
			uriString = $.ig.util.replace(uriString, "{S}", servers[this.__random.next1(servers.length)]);
			uriString = $.ig.util.replace(uriString, "{K}", this.key() == null ? "" : this.key());
			uriString = $.ig.util.replace(uriString, "{P}", this.parameter() == null ? "" : this.parameter());
			uriString = $.ig.util.replace(uriString, "{Z}", zoom.toString());
			uriString = $.ig.util.replace(uriString, "{X}", tilePositionX.toString());
			uriString = $.ig.util.replace(uriString, "{Y}", tilePositionY.toString());
			tileImageLayerSources.add(new $.ig.Uri(0, uriString));
		}
	}
	,
	onPropertyChanged: function (d, e) {
		var cloud = d;
		if (e.property() == $.ig.CloudMadeTileSource.prototype.parameterProperty) {
			cloud.invalidateTileLayer(0, 0, 0, 0);
		}
	}
	,
	$type: new $.ig.Type('CloudMadeTileSource', $.ig.MapTileSource.prototype.$type)
}, true);

$.ig.util.defType('OpenStreetMapTileSource', 'MapTileSource', {
	init: function () {
		$.ig.MapTileSource.prototype.init.call(this, 134217728, 134217728, 256, 256, 0);
	},
	getTileLayers: function (tileLevel, tilePositionX, tilePositionY, tileImageLayerSources) {
		var zoom = tileLevel - 8;
		if (zoom > 0) {
			var uriString = $.ig.OpenStreetMapTileSource.prototype._tilePathMapnik;
			uriString = $.ig.util.replace(uriString, "{Z}", zoom.toString());
			uriString = $.ig.util.replace(uriString, "{X}", tilePositionX.toString());
			uriString = $.ig.util.replace(uriString, "{Y}", tilePositionY.toString());
			tileImageLayerSources.add(new $.ig.Uri(0, uriString));
		}
	}
	,
	$type: new $.ig.Type('OpenStreetMapTileSource', $.ig.MapTileSource.prototype.$type)
}, true);

$.ig.util.defType('Tile', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_x: 0,
	_y: 0,
	_z: 0,
	_image: null,
	_ghostImage: null,
	_fadeStart: new Date(),
	rect: function () {
		var width = Math.pow(2, -this._z);
		var height = Math.pow(2, -this._z);
		return new $.ig.Rect(0, this._x * width, this._y * height, width, height);
	}
	,
	$type: new $.ig.Type('Tile', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('WriteableBitmap', 'Object', {
	init: function (width, height) {
		$.ig.Object.prototype.init.call(this);
		this.width(width);
		this.height(height);
		this.subDimensions($.ig.Rect.prototype.empty());
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
	_subDimensions: null,
	subDimensions: function (value) {
		if (arguments.length === 1) {
			this._subDimensions = value;
			return value;
		} else {
			return this._subDimensions;
		}
	}
	,
	$type: new $.ig.Type('WriteableBitmap', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IMapRenderDeferralHandler', 'Object', {
	$type: new $.ig.Type('IMapRenderDeferralHandler', null)
}, true);

$.ig.util.defType('IEasingFunction', 'Object', {
	$type: new $.ig.Type('IEasingFunction', null)
}, true);

$.ig.util.defType('XamMultiScaleImage', 'Control', {
	init: function () {
		this._activeTiles = new $.ig.List$1($.ig.Tile.prototype.$type, 0);
		this._refreshDeferred = false;
		this._cache = new $.ig.List$1($.ig.Pair$2.prototype.$type.specialize($.ig.Tile.prototype.$type, $.ig.WriteableBitmap.prototype.$type), 0);
		this._fadingTiles = new $.ig.List$1($.ig.Tile.prototype.$type, 0);
		this.__lastReady = false;
		$.ig.Control.prototype.init.call(this);
		this.canvasSize($.ig.Rect.prototype.empty());
		this.tileScheduler(new $.ig.CanvasRenderScheduler());
		this.view(new $.ig.XamMultiScaleImageView(this));
		this.defaultStyleKey($.ig.XamMultiScaleImage.prototype.$type);
		this.actualViewportOrigin(this.viewportOrigin());
		this.actualViewportWidth(this.viewportWidth());
	},
	_imagePool: null,
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
	__deferralHandler: null,
	deferralHandler: function (value) {
		if (arguments.length === 1) {
			if (this.__deferralHandler != null) {
				this.__deferralHandler.unRegister(this);
			}
			this.__deferralHandler = value;
			if (this.__deferralHandler != null) {
				this.__deferralHandler.register(this, this.refreshInternal.runOn(this));
			}
			return value;
		} else {
			return this.__deferralHandler;
		}
	}
	,
	source: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamMultiScaleImage.prototype.sourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamMultiScaleImage.prototype.sourceProperty);
		}
	}
	,
	viewportOrigin: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamMultiScaleImage.prototype.viewportOriginProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamMultiScaleImage.prototype.viewportOriginProperty);
		}
	}
	,
	_actualViewportOrigin: null,
	actualViewportOrigin: function (value) {
		if (arguments.length === 1) {
			this._actualViewportOrigin = value;
			return value;
		} else {
			return this._actualViewportOrigin;
		}
	}
	,
	viewportWidth: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamMultiScaleImage.prototype.viewportWidthProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamMultiScaleImage.prototype.viewportWidthProperty);
		}
	}
	,
	_actualViewportWidth: 0,
	actualViewportWidth: function (value) {
		if (arguments.length === 1) {
			this._actualViewportWidth = value;
			return value;
		} else {
			return this._actualViewportWidth;
		}
	}
	,
	useSprings: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamMultiScaleImage.prototype.useSpringsProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamMultiScaleImage.prototype.useSpringsProperty);
		}
	}
	,
	springsEasingFunction: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamMultiScaleImage.prototype.springsEasingFunctionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamMultiScaleImage.prototype.springsEasingFunctionProperty);
		}
	}
	,
	propertyChanged: null,
	onPropertyChanged: function (ea) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, ea);
		}
		switch (ea.propertyName()) {
			case $.ig.XamMultiScaleImage.prototype.sourcePropertyName:
				if (this.source() != null) {
					this.source()._multiScaleImage = this;
				}
				this.purgeCache();
				this.resetTiles();
				this.refresh();
				break;
			case $.ig.XamMultiScaleImage.prototype.viewportOriginPropertyName:
				this.spring();
				break;
			case $.ig.XamMultiScaleImage.prototype.viewportWidthPropertyName:
				this.spring();
				break;
			case $.ig.XamMultiScaleImage.prototype.useSpringsPropertyName:
				if (!this.useSprings()) {
					this.view().disableSprings();
				}
				break;
		}
	}
	,
	_levelOffset: 0,
	levelOffset: function (value) {
		if (arguments.length === 1) {
			this._levelOffset = value;
			return value;
		} else {
			return this._levelOffset;
		}
	}
	,
	_maxLevel: 0,
	maxLevel: function (value) {
		if (arguments.length === 1) {
			this._maxLevel = value;
			return value;
		} else {
			return this._maxLevel;
		}
	}
	,
	resetTiles: function () {
		this.trashActiveTiles();
		if (this.source() != null) {
			this.levelOffset($.ig.Convert.prototype.toInt32(Math.logBase(this.source().tileWidth(), 2)));
			this.maxLevel($.ig.Convert.prototype.toInt32(Math.logBase(this.source().imageWidth(), 2)));
		}
	}
	,
	invalidateTileLayer: function (level, tilePositionX, tilePositionY, tileLayer) {
		this.purgeCache();
		this.resetTiles();
		this.refresh();
	}
	,
	_springStart: new Date(),
	_anchorViewportOrigin: null,
	_anchorViewportWidth: 0,
	spring: function () {
		if (this.useSprings()) {
			this._springStart = $.ig.Date.prototype.now();
			this._anchorViewportOrigin = this.actualViewportOrigin();
			this._anchorViewportWidth = this.actualViewportWidth();
			this.view().startSpringTimer();
		} else {
			this.actualViewportOrigin(this.viewportOrigin());
			this.actualViewportWidth(this.viewportWidth());
			this.refresh();
		}
	}
	,
	springTimer_Tick: function () {
		var duration = 2;
		var totalMilliseconds = $.ig.Date.prototype.now().getTime() - this._springStart.getTime();
		var totalSeconds = totalMilliseconds / 1000;
		var p = $.ig.MathUtil.prototype.clamp((totalSeconds) / duration, 0, 1);
		var p1 = this.springsEasingFunction() != null ? this.springsEasingFunction().ease(p) : p;
		var p0 = 1 - p1;
		this.actualViewportWidth(this._anchorViewportWidth * p0 + this.viewportWidth() * p1);
		this.actualViewportOrigin({ __x: this._anchorViewportOrigin.__x * p0 + this.viewportOrigin().__x * p1, __y: this._anchorViewportOrigin.__y * p0 + this.viewportOrigin().__y * p1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		if (p >= 1) {
			this.view().stopSpringTimer();
		} else {
		}
		this.refresh();
	}
	,
	_activeTiles: null,
	activeTileIndex: function (x, y, z) {
		for (var i = 0; i < this._activeTiles.count(); ++i) {
			if (this._activeTiles.__inner[i]._x == x && this._activeTiles.__inner[i]._y == y && this._activeTiles.__inner[i]._z == z) {
				return i;
			}
		}
		return -1;
	}
	,
	refreshInternal: function (animate) {
		var $self = this;
		this._refreshDeferred = false;
		this.__lastReady = false;
		if (this.source() == null || !this.view().ready() || this.canvasSize().width() == 0 || this.canvasSize().height() == 0) {
			return;
		}
		var horizontalCount = $.ig.truncate(Math.ceil(this.canvasSize().width() / this.source().tileWidth()));
		var zz = $.ig.truncate(Math.max(1, Math.floor(-Math.logBase(this.actualViewportWidth() / horizontalCount, 2))));
		if (zz >= this.maxLevel() - 8) {
			zz = (this.maxLevel() - 8) - 1;
		}
		var maxTiles = $.ig.truncate(Math.round(Math.pow(2, zz)));
		var width = this.actualViewportWidth();
		var height = this.canvasSize().height() * width / this.canvasSize().width();
		var wx = this.source().imageWidth() / Math.pow(2, zz);
		var wy = this.source().imageHeight() / Math.pow(2, zz);
		var u0 = Math.max($.ig.truncate(Math.floor((this.actualViewportOrigin().__x * this.source().imageWidth()) / wx)), 0);
		var u1 = Math.min($.ig.truncate(Math.ceil(((this.actualViewportOrigin().__x + width) * this.source().imageWidth()) / wx)), maxTiles);
		var v0 = Math.max($.ig.truncate(Math.floor((this.actualViewportOrigin().__y * this.source().imageHeight()) / wy)), 0);
		var v1 = Math.min($.ig.truncate(Math.ceil(((this.actualViewportOrigin().__y + height) * this.source().imageWidth()) / wy)), maxTiles);
		var ox = ((u0 * wx) - (this.actualViewportOrigin().__x * this.source().imageWidth())) / wx;
		var oy = ((v0 * wy) - (this.actualViewportOrigin().__y * this.source().imageHeight())) / wy;
		var s = (width * this.source().imageWidth() / wx) * (this.source().tileWidth() / this.canvasSize().width());
		var newTiles = new $.ig.List$1($.ig.Tile.prototype.$type, 0);
		for (var u = u0; u < u1; ++u) {
			for (var v = v0; v < v1; ++v) {
				var index = this.activeTileIndex(u, v, zz);
				if (index >= 0) {
					newTiles.add(this._activeTiles.__inner[index]);
					this._activeTiles.removeAt(index);
				} else {
					newTiles.add((function () {
						var $ret = new $.ig.Tile();
						$ret._x = u;
						$ret._y = v;
						$ret._z = zz;
						return $ret;
					}()));
				}
			}
		}
		this._imagePool.deferDisactivate(true);
		this.trashActiveTiles();
		this._activeTiles = newTiles;
		for (var i = 0; i < this._activeTiles.count(); ++i) {
			if (this._activeTiles.__inner[i]._image == null) {
				$.ig.Debug.prototype.assert(this._activeTiles.__inner[i]._ghostImage == null);
				this._activeTiles.__inner[i]._image = this._imagePool.pop();
				this._activeTiles.__inner[i]._image.__opacity = 1;
				this.view().sendToBackground(this._activeTiles.__inner[i]._image);
				var bitmap = this.getCachedBitmap(this._activeTiles.__inner[i]);
				if (bitmap != null) {
					this._activeTiles.__inner[i]._image.source(bitmap);
				} else {
					var donor = null;
					var tile = (function () {
						var $ret = new $.ig.Tile();
						$ret._x = $self._activeTiles.__inner[i]._x;
						$ret._y = $self._activeTiles.__inner[i]._y;
						$ret._z = $self._activeTiles.__inner[i]._z;
						return $ret;
					}());
					while (tile._z >= 0 && donor == null) {
						tile._x = tile._x >> 1;
						tile._y = tile._y >> 1;
						tile._z = tile._z - 1;
						donor = this.getCachedBitmap(tile);
					}
					if (donor != null) {
						var q = $.ig.truncate(Math.pow(2, this._activeTiles.__inner[i]._z - tile._z));
						var size = $.ig.intDivide(256, q);
						var left = size * (this._activeTiles.__inner[i]._x % q);
						var top = size * (this._activeTiles.__inner[i]._y % q);
						this._activeTiles.__inner[i]._ghostImage = this._imagePool.pop();
						this._activeTiles.__inner[i]._ghostImage.__opacity = 1;
						this.view().sendToForeground(this._activeTiles.__inner[i]._ghostImage);
						bitmap = this.view().getGhostImage(size, donor, left, top);
						this._activeTiles.__inner[i]._ghostImage.source(bitmap);
					}
					this.view().download(this._activeTiles.__inner[i]);
				}
			}
			var iw = this.source().tileWidth() / s;
			var ih = this.source().tileHeight() / s;
			var il = (this._activeTiles.__inner[i]._x - u0 + ox) * iw;
			var it = (this._activeTiles.__inner[i]._y - v0 + oy) * ih;
			this._activeTiles.__inner[i]._image.width(iw + 0.5);
			this._activeTiles.__inner[i]._image.height(ih + 0.5);
			this.view().setImagePosition(this._activeTiles.__inner[i]._image, il, it);
			if (this._activeTiles.__inner[i]._ghostImage != null) {
				this._activeTiles.__inner[i]._ghostImage.width(iw + 0.5);
				this._activeTiles.__inner[i]._ghostImage.height(ih + 0.5);
				this.view().setImagePosition(this._activeTiles.__inner[i]._ghostImage, il, it);
			}
		}
		this._imagePool.deferDisactivate(false);
		this.view().refreshCompleted();
	}
	,
	_refreshDeferred: false,
	refresh: function () {
		if (this.source() == null || !this.view().ready() || this.canvasSize().width() == 0 || this.canvasSize().height() == 0) {
			return;
		}
		if (this._refreshDeferred) {
			return;
		}
		this._refreshDeferred = true;
		this.view().defer(this.refreshInternal.runOn(this));
	}
	,
	trashActiveTiles: function () {
		for (var i = 0; i < this._activeTiles.count(); ++i) {
			this.view().cancelDownload(this._activeTiles.__inner[i]);
			this.cancelFade(this._activeTiles.__inner[i]);
			if (this._activeTiles.__inner[i]._image != null) {
				this._imagePool.push(this._activeTiles.__inner[i]._image);
				this._activeTiles.__inner[i]._image.source(null);
				this._activeTiles.__inner[i]._image = null;
			}
			$.ig.Debug.prototype.assert(this._activeTiles.__inner[i]._image == null);
			$.ig.Debug.prototype.assert(this._activeTiles.__inner[i]._ghostImage == null);
		}
	}
	,
	purgeCache: function () {
		this._cache.clear();
	}
	,
	getCachedBitmap: function (tile) {
		for (var i = 0; i < this._cache.count(); ++i) {
			if (this._cache.__inner[i].first()._x == tile._x && this._cache.__inner[i].first()._y == tile._y && this._cache.__inner[i].first()._z == tile._z) {
				return this._cache.__inner[i].second();
			}
		}
		return null;
	}
	,
	cacheBitmap: function (tile, bitmap) {
		this._cache.add(new $.ig.Pair$2($.ig.Tile.prototype.$type, $.ig.WriteableBitmap.prototype.$type, tile, bitmap));
	}
	,
	_cache: null,
	_fadingTiles: null,
	isDuringFade: function () {
		return this._fadingTiles.count() != 0;
	}
	,
	imageTilesReady: null,
	__lastReady: false,
	notifyReady: function () {
		var ready = true;
		if (this.isDuringFade()) {
			ready = false;
		}
		if (!this.view().imagesReady()) {
			ready = false;
		}
		if (ready && !this.__lastReady) {
			if (this.imageTilesReady != null) {
				this.imageTilesReady(this, new $.ig.EventArgs());
			}
		}
		this.__lastReady = ready;
	}
	,
	startFade: function (tile) {
		$.ig.Debug.prototype.assert(tile._image != null);
		if (tile._ghostImage != null) {
			tile._fadeStart = $.ig.Date.prototype.now();
			this._fadingTiles.add(tile);
			this.view().startFadeTimer();
		} else {
			this.notifyReady();
		}
	}
	,
	cancelFade: function (tile) {
		if (tile._ghostImage != null) {
			this._imagePool.push(tile._ghostImage);
			tile._ghostImage.source(null);
			tile._ghostImage = null;
			for (var i = 0; i < this._fadingTiles.count(); ++i) {
				if (this._fadingTiles.__inner[i] == tile) {
					this._fadingTiles.removeAt(i);
					break;
				}
			}
			if (this._fadingTiles.count() == 0) {
				this.view().stopFadeTimer();
			}
			$.ig.Debug.prototype.assert(tile._ghostImage == null);
		}
	}
	,
	fadeTimer_Tick: function () {
		var now = $.ig.Date.prototype.now();
		var fadeDuration = 0.5;
		for (var i = 0; i < this._fadingTiles.count(); ) {
			var totalMilliseconds = now.getTime() - this._fadingTiles.__inner[i]._fadeStart.getTime();
			var totalSeconds = totalMilliseconds / 1000;
			var p = (totalSeconds) / fadeDuration;
			p = $.ig.MathUtil.prototype.clamp(p, 0, 1);
			this._fadingTiles.__inner[i]._ghostImage.__opacity = 1 - p;
			if (p >= 1) {
				this._imagePool.push(this._fadingTiles.__inner[i]._ghostImage);
				this._fadingTiles.__inner[i]._ghostImage.source(null);
				this._fadingTiles.__inner[i]._ghostImage = null;
				this._fadingTiles.removeAt(i);
			} else {
				++i;
			}
			this.view().fadingChanged();
		}
		if (this._fadingTiles.count() == 0) {
			this.view().stopFadeTimer();
		}
	}
	,
	_canvasSize: null,
	canvasSize: function (value) {
		if (arguments.length === 1) {
			this._canvasSize = value;
			return value;
		} else {
			return this._canvasSize;
		}
	}
	,
	onSpringsDisabled: function () {
		this.actualViewportWidth(this.viewportWidth());
		this.actualViewportOrigin(this.viewportOrigin());
		this.refresh();
	}
	,
	provideContainer: function (container) {
		this.view().onContainerProvided(container);
	}
	,
	provideContext: function (context) {
		this.view().onContextProvided(context);
	}
	,
	provideViewport: function (mapViewport) {
		this.view().onViewportProvided(mapViewport);
	}
	,
	_tileScheduler: null,
	tileScheduler: function (value) {
		if (arguments.length === 1) {
			this._tileScheduler = value;
			return value;
		} else {
			return this._tileScheduler;
		}
	}
	,
	onImagesChanged: function () {
		if (this.imagesChanged != null) {
			this.imagesChanged(this, new $.ig.EventArgs());
		}
	}
	,
	imagesChanged: null,
	deferCancelled: function () {
		this._refreshDeferred = false;
	}
	,
	$type: new $.ig.Type('XamMultiScaleImage', $.ig.Control.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('Pair$2', 'Object', {
	$t1: null,
	$t2: null,
	init: function ($t1, $t2, first, second) {
		this.$t1 = $t1;
		this.$t2 = $t2;
		this.$type = this.$type.specialize(this.$t1, this.$t2);
		$.ig.Object.prototype.init.call(this);
		this.first(first);
		this.second(second);
	},
	_first: null,
	first: function (value) {
		if (arguments.length === 1) {
			this._first = value;
			return value;
		} else {
			return this._first;
		}
	}
	,
	_second: null,
	second: function (value) {
		if (arguments.length === 1) {
			this._second = value;
			return value;
		} else {
			return this._second;
		}
	}
	,
	$type: new $.ig.Type('Pair$2', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IErrorBarCalculator', 'Object', {
	$type: new $.ig.Type('IErrorBarCalculator', null)
}, true);

$.ig.util.defType('Triangle', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	v1: 0,
	v2: 0,
	v3: 0,
	$type: new $.ig.Type('Triangle', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TriangulationSource', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	create: function (count, getXY, getValue) {
		var result = new $.ig.TriangulationSource();
		var points = new Array(count);
		var record;
		var p;
		var xColumn = new Array(count);
		var yColumn = new Array(count);
		var x = 0;
		var y = 0;
		for (var ii = 0; ii < count; ii++) {
			record = new $.ig.TriangulationSourcePointRecord();
			p = getXY(ii);
			x = p.__x;
			y = p.__y;
			record.pointX = x;
			record.pointY = y;
			record.value = getValue(ii);
			points[ii] = record;
			xColumn[ii] = x;
			yColumn[ii] = y;
		}
		result.points(points);
		var triangulator = new $.ig.Triangulator(count, xColumn, yColumn);
		triangulator.triangulate();
		result.triangles(triangulator.getResult());
		return result;
	}
	,
	loadItf: function (reader) {
		reader.readBytes(5);
		var pointCount = reader.readInt32();
		var triangleCount = reader.readInt32();
		var dataOffset = reader.readInt32();
		var crsLength = reader.readInt32();
		reader.readBytes(crsLength);
		var result = new $.ig.TriangulationSource();
		var points = new Array(pointCount);
		var point;
		for (var ii = 0; ii < pointCount; ii++) {
			point = new $.ig.TriangulationSourcePointRecord();
			point.pointX = reader.readDouble();
			point.pointY = reader.readDouble();
			point.value = reader.readSingle();
			points[ii] = point;
		}
		result.points(points);
		var triangles = new Array(triangleCount);
		var newTriangle;
		for (var ii1 = 0; ii1 < triangleCount; ii1++) {
			newTriangle = new $.ig.Triangle();
			newTriangle.v1 = reader.readInt32();
			newTriangle.v2 = reader.readInt32();
			newTriangle.v3 = reader.readInt32();
			triangles[ii1] = newTriangle;
		}
		result.triangles(triangles);
		return result;
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
	_triangles: null,
	triangles: function (value) {
		if (arguments.length === 1) {
			this._triangles = value;
			return value;
		} else {
			return this._triangles;
		}
	}
	,
	$type: new $.ig.Type('TriangulationSource', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TriangulatorContext', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_count: 0,
	_xColumn: null,
	_yColumn: null,
	_result: null,
	_s0: null,
	_s1: null,
	_s2: null,
	_triangleList: null,
	_completedList: null,
	_indices: null,
	_edgeBuffer: null,
	_pointTester: null,
	_stepSize: 0,
	_currentStart: 0,
	_currentEnd: 0,
	_async: false,
	$type: new $.ig.Type('TriangulatorContext', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Triangulator', 'DependencyObject', {
	__count: 0,
	__xColumn: null,
	__yColumn: null,
	init: function (count, xColumn, yColumn) {
		this.__status = 0;
		$.ig.DependencyObject.prototype.init.call(this);
		this.__count = count;
		this.__xColumn = xColumn;
		this.__yColumn = yColumn;
	},
	triangulateAsync: function () {
		{
			this.setupContext();
			this.context()._async = true;
			this.scheduleStep();
		}
	}
	,
	triangulate: function () {
		{
			this.setupContext();
			this.context()._async = false;
			this.scheduleStep();
		}
	}
	,
	setupContext: function () {
		var count = this.__count;
		var xColumn = this.__xColumn;
		var yColumn = this.__yColumn;
		var result = new $.ig.List$1($.ig.Triangle.prototype.$type, 0);
		if (count >= 3) {
			var indices = (function () {
				var $ret = new $.ig.List$1($.ig.Number.prototype.$type, 0);
				$ret.capacity(count);
				return $ret;
			}());
			for (var i = 0; i < count; ++i) {
				indices.add(i);
			}
			var comparison = function (a, b) {
				if (xColumn.item(a) < xColumn.item(b)) {
					return -1;
				} else if (xColumn.item(a) > xColumn.item(b)) {
					return 1;
				}
				if (yColumn.item(a) < yColumn.item(b)) {
					return -1;
				} else if (yColumn.item(a) > yColumn.item(b)) {
					return 1;
				}
				return 0;
			};
			indices.sort2(comparison);
			var xmin = xColumn.item(indices.__inner[0]);
			var xmax = xColumn.item(indices.__inner[count - 1]);
			var ymin = yColumn.item(indices.__inner[0]);
			var ymax = ymin;
			for (var i1 = 1; i1 < count; i1++) {
				ymin = Math.min(ymin, yColumn.item(indices.__inner[i1]));
				ymax = Math.max(ymax, xColumn.item(indices.__inner[i1]));
			}
			var dx = xmax - xmin;
			var dy = ymax - ymin;
			var dmax = Math.max(dx, dy);
			var xmid = (xmax + xmin) / 2;
			var ymid = (ymax + ymin) / 2;
			var s0 = { __x: xmid - 20 * dmax, __y: ymid - dmax, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var s1 = { __x: xmid, __y: ymid + 20 * dmax, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var s2 = { __x: xmid + 20 * dmax, __y: ymid - dmax, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var triangleList = new $.ig.LinkedList$1($.ig.Triangle.prototype.$type);
			var completedList = new $.ig.LinkedList$1($.ig.Triangle.prototype.$type);
			var edgeBuffer = new $.ig.HalfEdgeSet();
			var pointTester = new $.ig.PointTester();
			triangleList.addFirst((function () {
				var $ret = new $.ig.Triangle();
				$ret.v1 = count;
				$ret.v2 = count + 1;
				$ret.v3 = count + 2;
				return $ret;
			}()));
			var context = new $.ig.TriangulatorContext();
			context._count = count;
			context._s0 = s0;
			context._s1 = s1;
			context._s2 = s2;
			context._completedList = completedList;
			context._edgeBuffer = edgeBuffer;
			context._indices = indices;
			context._pointTester = pointTester;
			context._result = result;
			context._triangleList = triangleList;
			context._xColumn = xColumn;
			context._yColumn = yColumn;
			var numSteps = 1;
			if (count > 3000) {
				numSteps = 20;
			}
			context._stepSize = $.ig.truncate(Math.ceil(count / numSteps));
			context._currentStart = 0;
			context._currentEnd = context._stepSize;
			this.__status = 0;
			this.notifyStatus();
			this.context(context);
		}
	}
	,
	notifyStatus: function () {
		this.doNotifyStatus();
	}
	,
	doNotifyStatus: function () {
		if (this.triangulationStatusChanged != null) {
			this.triangulationStatusChanged(this, new $.ig.TriangulationStatusEventArgs(this.__status));
		}
	}
	,
	getResult: function () {
		if (this.context() == null) {
			return null;
		}
		return this.context()._result;
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
	step: function () {
		{
			if (this.context() == null) {
				return;
			}
			var context = this.context();
			var count = this.context()._count;
			var xColumn = this.context()._xColumn;
			var yColumn = this.context()._yColumn;
			var indices = this.context()._indices;
			var s0 = this.context()._s0;
			var s1 = this.context()._s1;
			var s2 = this.context()._s2;
			var result = this.context()._result;
			var triangleList = this.context()._triangleList;
			var completedList = this.context()._completedList;
			var pointTester = this.context()._pointTester;
			var edgeBuffer = this.context()._edgeBuffer;
			var currentStart = this.context()._currentStart;
			var currentEnd = this.context()._currentEnd;
			var px;
			var py;
			var v1index;
			var v1x;
			var v1y;
			var v2index;
			var v2x;
			var v2y;
			var v3index;
			var v3x;
			var v3y;
			var currentTriangle;
			for (var i = currentStart; i < currentEnd; ++i) {
				edgeBuffer.clear();
				if (i < count) {
					px = xColumn.item(indices.__inner[i]);
					py = yColumn.item(indices.__inner[i]);
				} else if (i == count) {
					px = s0.__x;
					py = s0.__y;
				} else if (i == count + 1) {
					px = s1.__x;
					py = s1.__y;
				} else {
					px = s2.__x;
					py = s2.__y;
				}
				var next = null;
				for (var curr = triangleList.first(); curr != null; curr = next) {
					next = curr._next;
					currentTriangle = curr.value();
					v1index = currentTriangle.v1;
					v2index = currentTriangle.v2;
					v3index = currentTriangle.v3;
					if (v1index < count) {
						v1x = xColumn.item(indices.__inner[v1index]);
						v1y = yColumn.item(indices.__inner[v1index]);
					} else if (v1index == count) {
						v1x = s0.__x;
						v1y = s0.__y;
					} else if (v1index == count + 1) {
						v1x = s1.__x;
						v1y = s1.__y;
					} else {
						v1x = s2.__x;
						v1y = s2.__y;
					}
					if (v2index < count) {
						v2x = xColumn.item(indices.__inner[v2index]);
						v2y = yColumn.item(indices.__inner[v2index]);
					} else if (v2index == count) {
						v2x = s0.__x;
						v2y = s0.__y;
					} else if (v2index == count + 1) {
						v2x = s1.__x;
						v2y = s1.__y;
					} else {
						v2x = s2.__x;
						v2y = s2.__y;
					}
					if (v3index < count) {
						v3x = xColumn.item(indices.__inner[v3index]);
						v3y = yColumn.item(indices.__inner[v3index]);
					} else if (v3index == count) {
						v3x = s0.__x;
						v3y = s0.__y;
					} else if (v3index == count + 1) {
						v3x = s1.__x;
						v3y = s1.__y;
					} else {
						v3x = s2.__x;
						v3y = s2.__y;
					}
					pointTester.test(px, py, v1x, v1y, v2x, v2y, v3x, v3y);
					if (pointTester._complete) {
						completedList.addLast(currentTriangle);
						triangleList.remove(curr);
					}
					if (pointTester._inside) {
						var e;
						e = new $.ig.HalfEdge(v1index, v2index);
						if (edgeBuffer.contains(e)) {
							edgeBuffer.remove(e);
						} else {
							edgeBuffer.add(e);
						}
						e = new $.ig.HalfEdge(v2index, v3index);
						if (edgeBuffer.contains(e)) {
							edgeBuffer.remove(e);
						} else {
							edgeBuffer.add(e);
						}
						e = new $.ig.HalfEdge(v3index, v1index);
						if (edgeBuffer.contains(e)) {
							edgeBuffer.remove(e);
						} else {
							edgeBuffer.add(e);
						}
						triangleList.remove(curr);
					}
				}
				var en = edgeBuffer.getEnumerator();
				while (en.moveNext()) {
					var edge = en.current();
					var newTriangle = new $.ig.Triangle();
					newTriangle.v1 = edge.b();
					newTriangle.v2 = edge.e();
					newTriangle.v3 = i;
					triangleList.addLast(newTriangle);
				}
			}
			if (currentEnd == count) {
				for (var curr1 = completedList.first(); curr1 != null; curr1 = curr1._next) {
					currentTriangle = curr1.value();
					if (currentTriangle.v1 < count && currentTriangle.v2 < count && currentTriangle.v3 < count) {
						result.add((function () {
							var $ret = new $.ig.Triangle();
							$ret.v1 = indices.__inner[currentTriangle.v1];
							$ret.v2 = indices.__inner[currentTriangle.v2];
							$ret.v3 = indices.__inner[currentTriangle.v3];
							return $ret;
						}()));
					}
				}
				for (var curr2 = triangleList.first(); curr2 != null; curr2 = curr2._next) {
					currentTriangle = curr2.value();
					if (currentTriangle.v1 < count && currentTriangle.v2 < count && currentTriangle.v3 < count) {
						result.add((function () {
							var $ret = new $.ig.Triangle();
							$ret.v1 = indices.__inner[currentTriangle.v1];
							$ret.v2 = indices.__inner[currentTriangle.v2];
							$ret.v3 = indices.__inner[currentTriangle.v3];
							return $ret;
						}()));
					}
				}
				this.__status = 100;
				this.notifyStatus();
			} else {
				currentStart = currentEnd;
				currentEnd += context._stepSize;
				if (currentEnd > count) {
					currentEnd = count;
				}
				context._currentStart = currentStart;
				context._currentEnd = currentEnd;
				this.__status += $.ig.truncate(Math.floor(100 / 20));
				this.notifyStatus();
				this.scheduleStep();
			}
		}
	}
	,
	cancel: function () {
		{
			this.context(null);
		}
	}
	,
	scheduleStep: function () {
		{
			if (this.context()._async) {
				window.setTimeout(this.step.runOn(this), 0);
			} else {
				this.step();
			}
		}
	}
	,
	__status: 0,
	status: function () {
		return this.__status;
	}
	,
	triangulationStatusChanged: null,
	$type: new $.ig.Type('Triangulator', $.ig.DependencyObject.prototype.$type)
}, true);

$.ig.util.defType('TriangulationStatusEventArgs', 'EventArgs', {
	_currentStatus: 0,
	currentStatus: function (value) {
		if (arguments.length === 1) {
			this._currentStatus = value;
			return value;
		} else {
			return this._currentStatus;
		}
	}
	,
	init: function (currentStatus) {
		$.ig.EventArgs.prototype.init.call(this);
		this.currentStatus(currentStatus);
	},
	$type: new $.ig.Type('TriangulationStatusEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('HalfEdge', 'Object', {
	init: function (b, e) {
		$.ig.Object.prototype.init.call(this);
		this.b(b);
		this.e(e);
	},
	_b: 0,
	b: function (value) {
		if (arguments.length === 1) {
			this._b = value;
			return value;
		} else {
			return this._b;
		}
	}
	,
	_e: 0,
	e: function (value) {
		if (arguments.length === 1) {
			this._e = value;
			return value;
		} else {
			return this._e;
		}
	}
	,
	$type: new $.ig.Type('HalfEdge', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('HalfEdgeSet', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this._edges = new $.ig.Dictionary$2($.ig.HalfEdge.prototype.$type, $.ig.Object.prototype.$type, 2, new $.ig.EdgeComparer());
	},
	getEnumerator: function () {
		return this._edges.keys().getEnumerator();
	}
	,
	add: function (edge) {
		this._edges.add(edge, null);
	}
	,
	remove: function (edge) {
		this._edges.remove(edge);
	}
	,
	clear: function () {
		this._edges.clear();
	}
	,
	count: function () {
		return this._edges.count();
	}
	,
	contains: function (edge) {
		return this._edges.containsKey(edge);
	}
	,
	_edges: null,
	$type: new $.ig.Type('HalfEdgeSet', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize($.ig.HalfEdge.prototype.$type)])
}, true);

$.ig.util.defType('EdgeComparer', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	equalsC: function (e1, e2) {
		return (e1.b() == e2.b() && e1.e() == e2.e()) || (e1.b() == e2.e() && e1.e() == e2.b());
	}
	,
	getHashCodeC: function (e1) {
		return 65536 * Math.max(e1.b(), e1.e()) + Math.min(e1.b(), e1.e());
	}
	,
	$type: new $.ig.Type('EdgeComparer', $.ig.Object.prototype.$type, [$.ig.IEqualityComparer$1.prototype.$type.specialize($.ig.HalfEdge.prototype.$type)])
}, true);

$.ig.util.defType('PointTester', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	test: function (PX, PY, P1X, P1Y, P2X, P2Y, P3X, P3Y) {
		var fabsy1y2 = Math.abs(P1Y - P2Y);
		var fabsy2y3 = Math.abs(P2Y - P3Y);
		var xc = 0;
		var yc = 0;
		if (fabsy1y2 == 0 && fabsy2y3 == 0) {
			return false;
		}
		if (fabsy1y2 == 0 && fabsy2y3 != 0) {
			xc = (P2X + P1X) / 2;
			yc = (-(P3X - P2X) / (P3Y - P2Y)) * (xc - ((P2X + P3X) / 2)) + ((P2Y + P3Y) / 2);
		}
		if (fabsy1y2 != 0 && fabsy2y3 == 0) {
			xc = (P3X + P2X) / 2;
			yc = (-(P2X - P1X) / (P2Y - P1Y)) * (xc - ((P1X + P2X) / 2)) + ((P1Y + P2Y) / 2);
		}
		if (fabsy1y2 != 0 && fabsy2y3 != 0) {
			var m1 = -(P2X - P1X) / (P2Y - P1Y);
			var m2 = -(P3X - P2X) / (P3Y - P2Y);
			var mx1 = (P1X + P2X) / 2;
			var mx2 = (P2X + P3X) / 2;
			var my1 = (P1Y + P2Y) / 2;
			var my2 = (P2Y + P3Y) / 2;
			xc = (m1 * mx1 - m2 * mx2 + my2 - my1) / (m1 - m2);
			yc = fabsy1y2 > fabsy2y3 ? m1 * (xc - mx1) + my1 : m2 * (xc - mx2) + my2;
		}
		var dx = P2X - xc;
		var dy = P2Y - yc;
		var rsqr = dx * dx + dy * dy;
		dx = PX - xc;
		dy = PY - yc;
		var drsqr = dx * dx + dy * dy;
		this._inside = drsqr <= rsqr;
		this._complete = xc < PX && ((PX - xc) * (PX - xc)) > rsqr;
		return true;
	}
	,
	_complete: false,
	_inside: false,
	$type: new $.ig.Type('PointTester', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TriangulationSourcePointRecord', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	pointX: 0,
	pointY: 0,
	value: 0,
	$type: new $.ig.Type('TriangulationSourcePointRecord', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('RearrangedList$1', 'Object', {
	$t: null,
	__inner: null,
	__indexes: null,
	init: function ($t, inner, indexes) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__inner = inner;
		this.__indexes = indexes;
	},
	indexOf: function (item) {
		var innerIndex = this.__inner.indexOf(item);
		if (innerIndex == -1) {
			return -1;
		}
		return this.__indexes.indexOf(innerIndex);
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
	item: function (index, value) {
		if (arguments.length === 2) {
			throw new $.ig.NotImplementedException(0);
			return value;
		} else {
			return this.__inner.item(this.__indexes.item(index));
		}
	}
	,
	add: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	clear: function () {
		this.__indexes.clear();
	}
	,
	contains: function (item) {
		return this.__inner.contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	count: function () {
		return this.__indexes.count();
	}
	,
	isReadOnly: function () {
		return true;
	}
	,
	remove: function (item) {
		throw new $.ig.NotImplementedException(0);
	}
	,
	getEnumerator: function () {
		var d__ = new $.ig.RearrangedList___GetEnumerator__IteratorClass$1(this.$t, 0);
		d__.__4__this = this;
		return d__;
	}
	,
	getEnumerator: function () {
		var d__ = new $.ig.RearrangedList___GetEnumerator__IteratorClass1$1(this.$t, 0);
		d__.__4__this = this;
		return d__;
	}
	,
	$type: new $.ig.Type('RearrangedList$1', $.ig.Object.prototype.$type, [$.ig.IList$1.prototype.$type.specialize(0)])
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

$.ig.util.defType('RearrangedList___GetEnumerator__IteratorClass$1', 'Object', {
	$t: null,
	__1__state: 0,
	__2__current: null,
	_indEnumerator: null,
	__ind_5_0: 0,
	__4__this: null,
	init: function ($t, _1__state) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._indEnumerator != null) {
			this._indEnumerator.dispose();
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
						this._indEnumerator = (this.__4__this.__indexes).getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._indEnumerator.moveNext()) {
							this.__ind_5_0 = this._indEnumerator.current();
							this.__2__current = this.__4__this.__inner.item(this.__ind_5_0);
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
		return $.ig.util.getBoxIfEnum(this.$t, this.__2__current);
	}
	,
	$type: new $.ig.Type('RearrangedList___GetEnumerator__IteratorClass$1', $.ig.Object.prototype.$type, [$.ig.IEnumerator$1.prototype.$type.specialize(0), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.util.defType('RearrangedList___GetEnumerator__IteratorClass1$1', 'Object', {
	$t: null,
	__1__state: 0,
	__2__current: null,
	_indEnumerator: null,
	__ind_5_0: 0,
	__4__this: null,
	init: function ($t, _1__state) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Object.prototype.init.call(this);
		this.__1__state = _1__state;
	},
	_m_Finally0: function () {
		this.__1__state = -1;
		if (this._indEnumerator != null) {
			this._indEnumerator.dispose();
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
						this._indEnumerator = (this.__4__this.__indexes).getEnumerator();
						this.__1__state = 2;
						break;
					case 2:
						this.__1__state = 1;
						if (this._indEnumerator.moveNext()) {
							this.__ind_5_0 = this._indEnumerator.current();
							this.__2__current = $.ig.util.getBoxIfEnum(this.$t, this.__4__this.__inner.item(this.__ind_5_0));
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
	$type: new $.ig.Type('RearrangedList___GetEnumerator__IteratorClass1$1', $.ig.Object.prototype.$type, [$.ig.IEnumerator$1.prototype.$type.specialize($.ig.Object.prototype.$type), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.ErrorBarCalculatorType.prototype.fixed = 0;
$.ig.ErrorBarCalculatorType.prototype.percentage = 1;
$.ig.ErrorBarCalculatorType.prototype.data = 2;
$.ig.ErrorBarCalculatorType.prototype.standardDeviation = 3;
$.ig.ErrorBarCalculatorType.prototype.standardError = 4;

$.ig.ErrorBarCalculatorReference.prototype.x = 0;
$.ig.ErrorBarCalculatorReference.prototype.y = 1;

$.ig.SmartPosition.prototype.leftTop = 0;
$.ig.SmartPosition.prototype.centerTop = 1;
$.ig.SmartPosition.prototype.rightTop = 2;
$.ig.SmartPosition.prototype.leftCenter = 3;
$.ig.SmartPosition.prototype.centerCenter = 4;
$.ig.SmartPosition.prototype.rightCenter = 5;
$.ig.SmartPosition.prototype.leftBottom = 6;
$.ig.SmartPosition.prototype.centerBottom = 7;
$.ig.SmartPosition.prototype.rightBottom = 8;

$.ig.XamMultiScaleImageView.prototype._emptyInterval = -1;

$.ig.BingMapsTileSource.prototype.tilePathProperty = $.ig.DependencyProperty.prototype.register("TilePath", String, $.ig.BingMapsTileSource.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.BingMapsTileSource.prototype.onPropertyChanged));
$.ig.BingMapsTileSource.prototype.subDomainsProperty = $.ig.DependencyProperty.prototype.register("SubDomains", $.ig.ObservableCollection$1.prototype.$type.specialize(String), $.ig.BingMapsTileSource.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.BingMapsTileSource.prototype.onPropertyChanged));
$.ig.BingMapsTileSource.prototype.cultureNameProperty = $.ig.DependencyProperty.prototype.register("CultureName", String, $.ig.BingMapsTileSource.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.BingMapsTileSource.prototype.onPropertyChanged));

$.ig.CloudMadeTileSource.prototype._tilePathMapnik = "http://{S}.tile.cloudmade.com/{K}/{P}/256/{Z}/{X}/{Y}.png";
$.ig.CloudMadeTileSource.prototype._keyPropertyName = "Key";
$.ig.CloudMadeTileSource.prototype._parameterPropertyName = "Parameter";
$.ig.CloudMadeTileSource.prototype.keyProperty = $.ig.DependencyProperty.prototype.register($.ig.CloudMadeTileSource.prototype._keyPropertyName, String, $.ig.CloudMadeTileSource.prototype.$type, new $.ig.PropertyMetadata(1, null));
$.ig.CloudMadeTileSource.prototype.parameterProperty = $.ig.DependencyProperty.prototype.register($.ig.CloudMadeTileSource.prototype._parameterPropertyName, String, $.ig.CloudMadeTileSource.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.CloudMadeTileSource.prototype.onPropertyChanged));

$.ig.OpenStreetMapTileSource.prototype._tilePathMapnik = "http://tile.openstreetmap.org/{Z}/{X}/{Y}.png";

$.ig.XamMultiScaleImage.prototype.sourcePropertyName = "Source";
$.ig.XamMultiScaleImage.prototype.viewportOriginPropertyName = "ViewportOrigin";
$.ig.XamMultiScaleImage.prototype.viewportWidthPropertyName = "ViewportWidth";
$.ig.XamMultiScaleImage.prototype.useSpringsPropertyName = "UseSprings";
$.ig.XamMultiScaleImage.prototype.springsEasingFunctionPropertyName = "SpringsEasingFunction";
$.ig.XamMultiScaleImage.prototype.sourceProperty = $.ig.DependencyProperty.prototype.register($.ig.XamMultiScaleImage.prototype.sourcePropertyName, $.ig.XamMultiScaleTileSource.prototype.$type, $.ig.XamMultiScaleImage.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).onPropertyChanged(new $.ig.PropertyChangedEventArgs$1($.ig.XamMultiScaleTileSource.prototype.$type, $.ig.XamMultiScaleImage.prototype.sourcePropertyName, $.ig.util.cast($.ig.XamMultiScaleTileSource.prototype.$type, e.oldValue()), $.ig.util.cast($.ig.XamMultiScaleTileSource.prototype.$type, e.newValue())));
}));
$.ig.XamMultiScaleImage.prototype.viewportOriginProperty = $.ig.DependencyProperty.prototype.register($.ig.XamMultiScaleImage.prototype.viewportOriginPropertyName, $.ig.Point.prototype.$type, $.ig.XamMultiScaleImage.prototype.$type, new $.ig.PropertyMetadata(2, { __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, function (sender, e) {
	(sender).onPropertyChanged(new $.ig.PropertyChangedEventArgs$1($.ig.Point.prototype.$type, $.ig.XamMultiScaleImage.prototype.viewportOriginPropertyName, e.oldValue(), e.newValue()));
}));
$.ig.XamMultiScaleImage.prototype.viewportWidthProperty = $.ig.DependencyProperty.prototype.register($.ig.XamMultiScaleImage.prototype.viewportWidthPropertyName, Number, $.ig.XamMultiScaleImage.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (sender, e) {
	(sender).onPropertyChanged(new $.ig.PropertyChangedEventArgs$1(Number, $.ig.XamMultiScaleImage.prototype.viewportWidthPropertyName, e.oldValue(), e.newValue()));
}));
$.ig.XamMultiScaleImage.prototype.useSpringsProperty = $.ig.DependencyProperty.prototype.register($.ig.XamMultiScaleImage.prototype.useSpringsPropertyName, $.ig.Boolean.prototype.$type, $.ig.XamMultiScaleImage.prototype.$type, new $.ig.PropertyMetadata(2, false, function (sender, e) {
	(sender).onPropertyChanged(new $.ig.PropertyChangedEventArgs$1($.ig.Boolean.prototype.$type, $.ig.XamMultiScaleImage.prototype.useSpringsPropertyName, e.oldValue(), e.newValue()));
}));
$.ig.XamMultiScaleImage.prototype.springsEasingFunctionProperty = $.ig.DependencyProperty.prototype.register($.ig.XamMultiScaleImage.prototype.springsEasingFunctionPropertyName, $.ig.IEasingFunction.prototype.$type, $.ig.XamMultiScaleImage.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).onPropertyChanged(new $.ig.PropertyChangedEventArgs$1($.ig.IEasingFunction.prototype.$type, $.ig.XamMultiScaleImage.prototype.springsEasingFunctionPropertyName, e.oldValue(), e.newValue()));
}));

$.ig.TriangulationSource.prototype._cRS = "LOCAL_CS[]";

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["IProvidesViewport:a", 
"Void:b", 
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
"Series:be", 
"Control:bf", 
"FrameworkElement:bg", 
"UIElement:bh", 
"DependencyObject:bi", 
"Dictionary:bj", 
"DependencyProperty:bk", 
"PropertyMetadata:bl", 
"PropertyChangedCallback:bm", 
"MulticastDelegate:bn", 
"IntPtr:bo", 
"DependencyPropertyChangedEventArgs:bp", 
"DependencyPropertiesCollection:bq", 
"UnsetValue:br", 
"Script:bs", 
"Binding:bt", 
"PropertyPath:bu", 
"Transform:bv", 
"Visibility:bw", 
"Style:bx", 
"Thickness:by", 
"HorizontalAlignment:bz", 
"VerticalAlignment:b0", 
"INotifyPropertyChanged:b1", 
"PropertyChangedEventHandler:b2", 
"PropertyChangedEventArgs:b3", 
"SeriesView:b4", 
"ISchedulableRender:b5", 
"SeriesViewer:b6", 
"SeriesViewerView:b7", 
"CanvasRenderScheduler:b8", 
"List$1:b9", 
"IList$1:ca", 
"ICollection$1:cb", 
"IArray:cc", 
"IArrayList:cd", 
"Array:ce", 
"CompareCallback:cf", 
"Func$3:cg", 
"Action$1:ch", 
"Comparer$1:ci", 
"IComparer:cj", 
"IComparer$1:ck", 
"DefaultComparer$1:cl", 
"Comparison$1:cm", 
"ReadOnlyCollection$1:cn", 
"Predicate$1:co", 
"NotImplementedException:cp", 
"Callback:cq", 
"window:cr", 
"RenderingContext:cs", 
"IRenderer:ct", 
"Rectangle:cu", 
"Shape:cv", 
"Brush:cw", 
"Color:cx", 
"ArgumentException:cy", 
"DoubleCollection:cz", 
"Path:c0", 
"Geometry:c1", 
"GeometryType:c2", 
"TextBlock:c3", 
"Polygon:c4", 
"PointCollection:c5", 
"Polyline:c6", 
"DataTemplateRenderInfo:c7", 
"DataTemplatePassInfo:c8", 
"ContentControl:c9", 
"DataTemplate:da", 
"DataTemplateRenderHandler:db", 
"DataTemplateMeasureHandler:dc", 
"DataTemplateMeasureInfo:dd", 
"DataTemplatePassHandler:de", 
"Line:df", 
"FontInfo:dg", 
"XamOverviewPlusDetailPane:dh", 
"XamOverviewPlusDetailPaneView:di", 
"XamOverviewPlusDetailPaneViewManager:dj", 
"JQueryObject:dk", 
"Element:dl", 
"ElementAttributeCollection:dm", 
"ElementCollection:dn", 
"WebStyle:dp", 
"ElementNodeType:dq", 
"Document:dr", 
"EventListener:ds", 
"IElementEventHandler:dt", 
"ElementEventHandler:du", 
"ElementAttribute:dv", 
"JQueryPosition:dw", 
"JQueryCallback:dx", 
"JQueryEvent:dy", 
"JQueryUICallback:dz", 
"EventProxy:d0", 
"ModifierKeys:d1", 
"Func$2:d2", 
"MouseWheelHandler:d3", 
"Delegate:d4", 
"Interlocked:d5", 
"GestureHandler:d6", 
"ZoomGestureHandler:d7", 
"FlingGestureHandler:d8", 
"ContactHandler:d9", 
"TouchHandler:ea", 
"MouseOverHandler:eb", 
"MouseHandler:ec", 
"KeyHandler:ed", 
"Key:ee", 
"JQuery:ef", 
"JQueryDeferred:eg", 
"JQueryPromise:eh", 
"Action:ei", 
"CanvasViewRenderer:ej", 
"CanvasContext2D:ek", 
"CanvasContext:el", 
"TextMetrics:em", 
"ImageData:en", 
"CanvasElement:eo", 
"Gradient:ep", 
"LinearGradientBrush:eq", 
"GradientStop:er", 
"GeometryGroup:es", 
"GeometryCollection:et", 
"FillRule:eu", 
"PathGeometry:ev", 
"PathFigureCollection:ew", 
"LineGeometry:ex", 
"RectangleGeometry:ey", 
"EllipseGeometry:ez", 
"ArcSegment:e0", 
"PathSegment:e1", 
"PathSegmentType:e2", 
"SweepDirection:e3", 
"PathFigure:e4", 
"PathSegmentCollection:e5", 
"LineSegment:e6", 
"PolyLineSegment:e7", 
"BezierSegment:e8", 
"PolyBezierSegment:e9", 
"GeometryUtil:fa", 
"Tuple$2:fb", 
"TransformGroup:fc", 
"TransformCollection:fd", 
"TranslateTransform:fe", 
"RotateTransform:ff", 
"ScaleTransform:fg", 
"DivElement:fh", 
"BaseDOMEventProxy:fi", 
"DOMEventProxy:fj", 
"MSGesture:fk", 
"MouseEventArgs:fl", 
"EventArgs:fm", 
"DoubleAnimator:fn", 
"EasingFunctionHandler:fo", 
"ImageElement:fp", 
"RectUtil:fq", 
"MathUtil:fr", 
"RuntimeHelpers:fs", 
"RuntimeFieldHandle:ft", 
"PropertyChangedEventArgs$1:fu", 
"InteractionState:fv", 
"OverviewPlusDetailPaneMode:fw", 
"IOverviewPlusDetailControl:fx", 
"EventHandler$1:fy", 
"ArgumentNullException:fz", 
"OverviewPlusDetailViewportHost:f0", 
"XamDataChart:f1", 
"GridMode:f2", 
"BrushCollection:f3", 
"ObservableCollection$1:f4", 
"INotifyCollectionChanged:f5", 
"NotifyCollectionChangedEventHandler:f6", 
"NotifyCollectionChangedEventArgs:f7", 
"NotifyCollectionChangedAction:f8", 
"InterpolationMode:f9", 
"Random:ga", 
"ColorUtil:gb", 
"AxisCollection:gc", 
"XamDataChartView:gd", 
"SeriesViewerViewManager:ge", 
"AxisTitlePosition:gf", 
"PointerTooltipStyle:gg", 
"Dictionary$2:gh", 
"IDictionary$2:gi", 
"IDictionary:gj", 
"KeyValuePair$2:gk", 
"Enumerable:gl", 
"Thread:gm", 
"ThreadStart:gn", 
"IOrderedEnumerable$1:go", 
"SortedList$1:gp", 
"IEqualityComparer$1:gq", 
"EqualityComparer$1:gr", 
"IEqualityComparer:gs", 
"DefaultEqualityComparer$1:gt", 
"InvalidOperationException:gu", 
"CanvasGestureDOMEventProxy:gv", 
"TouchPointInfo:gw", 
"DOMExecutionContext:gx", 
"IExecutionContext:gy", 
"ExecutionContextExecuteCallback:gz", 
"TouchGestureRecognizer:g0", 
"TouchGestureState:g1", 
"TouchVelocityTracker:g2", 
"TouchHistoryItem:g3", 
"TouchVelocityReading:g4", 
"TouchGestureEventHandler:g5", 
"TouchGestureEventArgs:g6", 
"CancelableTouchGestureEventHandler:g7", 
"CssHelper:g8", 
"CssGradientUtil:g9", 
"FontUtil:ha", 
"TileZoomTile:hb", 
"TileZoomTileInfo:hc", 
"TileZoomTileCache:hd", 
"TileZoomManager:he", 
"RectChangedEventHandler:hf", 
"RectChangedEventArgs:hg", 
"Debug:hh", 
"TileZoomInfo:hi", 
"LinkedList$1:hj", 
"LinkedListNode$1:hk", 
"RenderSurface:hl", 
"FragmentBase:hm", 
"HorizontalAnchoredCategorySeries:hn", 
"AnchoredCategorySeries:ho", 
"CategorySeries:hp", 
"MarkerSeries:hq", 
"MarkerSeriesView:hr", 
"Marker:hs", 
"DataContext:ht", 
"MarkerTemplates:hu", 
"HashPool$2:hv", 
"IHashPool$2:hw", 
"IPool$1:hx", 
"Func$1:hy", 
"Pool$1:hz", 
"IIndexedPool$1:h0", 
"MarkerType:h1", 
"SeriesVisualData:h2", 
"PrimitiveVisualDataList:h3", 
"IVisualData:h4", 
"PrimitiveVisualData:h5", 
"PrimitiveAppearanceData:h6", 
"BrushAppearanceData:h7", 
"StringBuilder:h8", 
"Environment:h9", 
"AppearanceHelper:ia", 
"LinearGradientBrushAppearanceData:ib", 
"GradientStopAppearanceData:ic", 
"SolidBrushAppearanceData:id", 
"GeometryData:ie", 
"GetPointsSettings:ig", 
"EllipseGeometryData:ih", 
"RectangleGeometryData:ii", 
"LineGeometryData:ij", 
"PathGeometryData:ik", 
"PathFigureData:il", 
"SegmentData:im", 
"LineSegmentData:io", 
"PolylineSegmentData:ip", 
"ArcSegmentData:iq", 
"PolyBezierSegmentData:ir", 
"BezierSegmentData:is", 
"LabelAppearanceData:it", 
"ShapeTags:iu", 
"PointerTooltipVisualDataList:iv", 
"MarkerVisualDataList:iw", 
"MarkerVisualData:ix", 
"PointerTooltipVisualData:iy", 
"RectangleVisualData:iz", 
"PolygonVisualData:i0", 
"PolyLineVisualData:i1", 
"IFastItemsSource:i2", 
"IFastItemColumn$1:i3", 
"IFastItemColumnPropertyName:i4", 
"FastItemsSourceEventArgs:i5", 
"FastItemsSourceEventAction:i6", 
"IHasCategoryModePreference:i7", 
"IHasCategoryAxis:i8", 
"CategoryAxisBase:i9", 
"Axis:ja", 
"AxisView:jb", 
"StackedSeriesBase:jc", 
"IIsCategoryBased:jd", 
"CategoryMode:je", 
"ICategoryScaler:jf", 
"IScaler:jg", 
"ScalerParams:jh", 
"IBucketizer:ji", 
"IDetectsCollisions:jj", 
"StackedSeriesView:jk", 
"CategorySeriesView:jl", 
"ISupportsMarkers:jm", 
"CategoryBucketCalculator:jn", 
"ISortingAxis:jo", 
"CategoryFrame:jp", 
"Frame:jq", 
"BrushUtil:jr", 
"Canvas:js", 
"Panel:jt", 
"UIElementCollection:ju", 
"StackedBucketCalculator:jv", 
"StackedSeriesManager:jw", 
"StackedSeriesCollection:jx", 
"StackedFragmentSeries:jy", 
"PenLineCap:jz", 
"PropertyUpdatedEventHandler:j0", 
"PropertyUpdatedEventArgs:j1", 
"StackedAreaSeries:j2", 
"HorizontalStackedSeriesBase:j3", 
"NumericYAxis:j4", 
"StraightNumericAxisBase:j5", 
"NumericAxisBase:j6", 
"NumericAxisBaseView:j7", 
"NumericAxisRenderer:j8", 
"AxisRendererBase:j9", 
"ShouldRenderHandler:ka", 
"ScaleValueHandler:kb", 
"AxisRenderingParametersBase:kc", 
"RangeInfo:kd", 
"TickmarkValues:ke", 
"TickmarkValuesInitializationParameters:kf", 
"Func$4:kg", 
"GetGroupCenterHandler:kh", 
"GetUnscaledGroupCenterHandler:ki", 
"PathRenderingInfo:kj", 
"RenderStripHandler:kk", 
"RenderLineHandler:kl", 
"ShouldRenderLinesHandler:km", 
"ShouldRenderContentHandler:kn", 
"RenderAxisLineHandler:ko", 
"DetermineCrossingValueHandler:kp", 
"ShouldRenderLabelHandler:kq", 
"GetLabelLocationHandler:kr", 
"LabelPosition:ks", 
"TransformToLabelValueHandler:kt", 
"AxisLabelManager:ku", 
"AxisLabelPanelBase:kv", 
"AxisLabelPanelBaseView:kw", 
"AxisLabelSettings:kx", 
"AxisLabelsLocation:ky", 
"TitleSettings:kz", 
"GetLabelForItemHandler:k0", 
"CreateRenderingParamsHandler:k1", 
"SnapMajorValueHandler:k2", 
"AdjustMajorValueHandler:k3", 
"CategoryAxisRenderingParameters:k4", 
"LogarithmicTickmarkValues:k5", 
"LogarithmicNumericSnapper:k6", 
"Snapper:k7", 
"LinearTickmarkValues:k8", 
"LinearNumericSnapper:k9", 
"AxisRangeChangedEventArgs:la", 
"AxisRange:lb", 
"AutoRangeCalculator:lc", 
"NumericRadiusAxis:ld", 
"NumericRadiusAxisView:le", 
"NumericAngleAxis:lf", 
"IAngleScaler:lg", 
"NumericAngleAxisView:lh", 
"PolarAxisRenderingManager:li", 
"ViewportUtils:lj", 
"PolarAxisRenderingParameters:lk", 
"NumericAxisRenderingParameters:ll", 
"IPolarRadialRenderingParameters:lm", 
"RadialAxisRenderingParameters:ln", 
"AxisOrientation:lo", 
"AngleAxisLabelPanel:lp", 
"AngleAxisLabelPanelView:lq", 
"Extensions:lr", 
"CategoryAngleAxis:ls", 
"CategoryAngleAxisView:lt", 
"CategoryAxisBaseView:lu", 
"CategoryAxisRenderer:lv", 
"LinearCategorySnapper:lw", 
"CategoryTickmarkValues:lx", 
"RadialAxisLabelPanel:ly", 
"HorizontalAxisLabelPanelBase:lz", 
"HorizontalAxisLabelPanelBaseView:l0", 
"RadialAxisLabelPanelView:l1", 
"NumericScaler:l2", 
"StraightNumericAxisBaseView:l3", 
"NumericScaleMode:l4", 
"LogarithmicScaler:l5", 
"NumericXAxis:l6", 
"NumericXAxisView:l7", 
"HorizontalSmartAxisLabelPanel:l8", 
"AxisExtentType:l9", 
"SmartAxisLabelDisplayType:ma", 
"HorizontalSmartAxisLabelPanelView:mb", 
"FontMappingInfo:mc", 
"CategoryDateTimeXAxis:md", 
"CategoryDateTimeXAxisView:me", 
"TimeAxisDisplayType:mf", 
"FastItemDateTimeColumn:mg", 
"IFastItemColumnInternal:mh", 
"FastItemColumn:mi", 
"FastReflectionHelper:mj", 
"HorizontalAxisLabelPanel:mk", 
"CoercionInfo:ml", 
"SortedListView$1:mm", 
"ArrayUtil:mn", 
"HorizontalLogarithmicScaler:mo", 
"HorizontalLinearScaler:mp", 
"LinearScaler:mq", 
"NumericYAxisView:mr", 
"VerticalAxisLabelPanel:ms", 
"VerticalAxisLabelPanelView:mt", 
"VerticalLogarithmicScaler:mu", 
"VerticalLinearScaler:mv", 
"CategoryFramePreparerBase:mw", 
"FramePreparer:mx", 
"ISupportsErrorBars:my", 
"DefaultSupportsMarkers:mz", 
"DefaultProvidesViewport:m0", 
"DefaultSupportsErrorBars:m1", 
"PreparationParams:m2", 
"CategoryYAxis:m3", 
"CategoryYAxisView:m4", 
"SyncSettings:m5", 
"ValuesHolder:m6", 
"LineSeries:m7", 
"LineSeriesView:m8", 
"AnchoredCategorySeriesView:m9", 
"CategoryTrendLineManagerBase:na", 
"TrendLineManagerBase$1:nb", 
"TrendLineType:nc", 
"Clipper:nd", 
"EdgeClipper:ne", 
"LeftClipper:nf", 
"BottomClipper:ng", 
"RightClipper:nh", 
"TopClipper:ni", 
"TrendResolutionParams:nj", 
"Flattener:nk", 
"Stack$1:nl", 
"ReverseArrayEnumerator$1:nm", 
"SpiralTodo:nn", 
"FlattenerSettings:no", 
"IPreparesCategoryTrendline:np", 
"SortingTrendLineManager:nq", 
"TrendFitCalculator:nr", 
"LeastSquaresFit:ns", 
"Numeric:nt", 
"TrendAverageCalculator:nu", 
"CategoryTrendLineManager:nv", 
"AnchoredCategoryBucketCalculator:nw", 
"UnknownValuePlotting:nx", 
"CategoryLineRasterizer:ny", 
"Action$5:nz", 
"PathVisualData:n0", 
"CategorySeriesRenderManager:n1", 
"AssigningCategoryStyleEventArgs:n2", 
"AssigningCategoryStyleEventArgsBase:n3", 
"GetCategoryItemsHandler:n4", 
"HighlightingInfo:n5", 
"HighlightingState:n6", 
"AssigningCategoryMarkerStyleEventArgs:n7", 
"HighlightingManager:n8", 
"SplineSeriesBase:n9", 
"SplineSeriesBaseView:oa", 
"SplineType:ob", 
"CollisionAvoider:oc", 
"SafeSortedReadOnlyDoubleCollection:od", 
"SafeReadOnlyDoubleCollection:oe", 
"SafeEnumerable:of", 
"AreaSeries:og", 
"AreaSeriesView:oh", 
"LegendTemplates:oi", 
"PieChartBase:oj", 
"PieChartBaseView:ok", 
"PieChartViewManager:ol", 
"PieChartVisualData:om", 
"PieSliceVisualDataList:on", 
"PieSliceVisualData:oo", 
"PieSliceDataContext:op", 
"Slice:oq", 
"SliceView:or", 
"PieLabel:os", 
"LabelsPosition:ot", 
"MouseButtonEventArgs:ou", 
"FastItemsSource:ov", 
"ColumnReference:ow", 
"FastItemObjectColumn:ox", 
"FastItemIntColumn:oy", 
"LeaderLineType:oz", 
"OthersCategoryType:o0", 
"IndexCollection:o1", 
"LegendBase:o2", 
"LegendBaseView:o3", 
"LegendBaseViewManager:o4", 
"GradientData:o5", 
"GradientStopData:o6", 
"DataChartLegendMouseButtonEventArgs:o7", 
"DataChartMouseButtonEventArgs:o8", 
"ChartLegendMouseEventArgs:o9", 
"ChartMouseEventArgs:pa", 
"DataChartLegendMouseButtonEventHandler:pb", 
"DataChartLegendMouseEventHandler:pc", 
"LegendVisualData:pd", 
"LegendVisualDataList:pe", 
"LegendItemVisualData:pf", 
"FunnelSliceDataContext:pg", 
"PieChartFormatLabelHandler:ph", 
"LabelClickEventHandler:pi", 
"LabelClickEventArgs:pj", 
"SliceClickEventHandler:pk", 
"SliceClickEventArgs:pl", 
"ItemLegend:pm", 
"ItemLegendView:pn", 
"LegendItemInfo:po", 
"BubbleSeries:pp", 
"ScatterBase:pq", 
"ScatterBaseView:pr", 
"MarkerManagerBase:ps", 
"OwnedPoint:pt", 
"MarkerManagerBucket:pu", 
"ScatterTrendLineManager:pv", 
"NumericMarkerManager:pw", 
"CollisionAvoidanceType:px", 
"SmartPlacer:py", 
"ISmartPlaceable:pz", 
"SmartPosition:p0", 
"SmartPlaceableWrapper$1:p1", 
"ScatterAxisInfoCache:p2", 
"ScatterErrorBarSettings:p3", 
"ErrorBarSettingsBase:p4", 
"EnableErrorBars:p5", 
"ErrorBarCalculatorReference:p6", 
"IErrorBarCalculator:p7", 
"ErrorBarCalculatorType:p8", 
"ScatterFrame:p9", 
"ScatterFrameBase$1:qa", 
"DictInterpolator$3:qb", 
"Action$6:qc", 
"SeriesHitTestMode:qd", 
"SyncLink:qe", 
"IFastItemsSourceProvider:qf", 
"ChartCollection:qg", 
"FastItemsSourceReference:qh", 
"SyncManager:qi", 
"SyncLinkManager:qj", 
"ErrorBarsHelper:qk", 
"BubbleSeriesView:ql", 
"BubbleMarkerManager:qm", 
"SizeScale:qn", 
"BrushScale:qo", 
"ScaleLegend:qp", 
"ScaleLegendView:qq", 
"CustomPaletteBrushScale:qr", 
"BrushSelectionMode:qs", 
"ValueBrushScale:qt", 
"RingSeriesBase:qu", 
"XamDoughnutChart:qv", 
"RingCollection:qw", 
"Ring:qx", 
"RingControl:qy", 
"RingControlView:qz", 
"Arc:q0", 
"ArcView:q1", 
"ArcItem:q2", 
"SliceItem:q3", 
"RingSeriesBaseView:q4", 
"Nullable$1:q5", 
"RingSeriesCollection:q6", 
"SliceCollection:q7", 
"XamDoughnutChartView:q8", 
"Action$2:q9", 
"DoughnutChartVisualData:ra", 
"RingSeriesVisualDataList:rb", 
"RingSeriesVisualData:rc", 
"RingVisualDataList:rd", 
"RingVisualData:re", 
"ArcVisualDataList:rf", 
"ArcVisualData:rg", 
"SliceVisualDataList:rh", 
"SliceVisualData:ri", 
"DoughnutChartLabelVisualData:rj", 
"HoleDimensionsChangedEventHandler:rk", 
"HoleDimensionsChangedEventArgs:rl", 
"XamFunnelChart:rm", 
"IItemProvider:rn", 
"MessageHandler:ro", 
"MessageHandlerEventHandler:rp", 
"Message:rq", 
"ServiceProvider:rr", 
"MessageChannel:rs", 
"MessageEventHandler:rt", 
"Queue$1:ru", 
"XamFunnelConnector:rv", 
"XamFunnelController:rw", 
"SliceInfoList:rx", 
"SliceInfo:ry", 
"SliceAppearance:rz", 
"PointList:r0", 
"FunnelSliceVisualData:r1", 
"PointData:r2", 
"SliceInfoUnaryComparison:r3", 
"Bezier:r4", 
"BezierPoint:r5", 
"BezierOp:r6", 
"BezierPointComparison:r7", 
"DoubleColumn:r8", 
"ObjectColumn:r9", 
"XamFunnelView:sa", 
"IOuterLabelWidthDecider:sb", 
"IFunnelLabelSizeDecider:sc", 
"MouseLeaveMessage:sd", 
"InteractionMessage:se", 
"MouseMoveMessage:sf", 
"MouseButtonMessage:sg", 
"MouseButtonAction:sh", 
"MouseButtonType:si", 
"SetAreaSizeMessage:sj", 
"RenderingMessage:sk", 
"RenderSliceMessage:sl", 
"RenderOuterLabelMessage:sm", 
"TooltipValueChangedMessage:sn", 
"TooltipUpdateMessage:so", 
"FunnelDataContext:sp", 
"PropertyChangedMessage:sq", 
"ConfigurationMessage:sr", 
"ClearMessage:ss", 
"ClearTooltipMessage:st", 
"ContainerSizeChangedMessage:su", 
"ViewportChangedMessage:sv", 
"ViewPropertyChangedMessage:sw", 
"OuterLabelAlignment:sx", 
"FunnelSliceDisplay:sy", 
"SliceSelectionManager:sz", 
"DataUpdatedMessage:s0", 
"ItemsSourceAction:s1", 
"FunnelFrame:s2", 
"UserSelectedItemsChangedMessage:s3", 
"LabelSizeChangedMessage:s4", 
"FrameRenderCompleteMessage:s5", 
"IntColumn:s6", 
"IntColumnComparison:s7", 
"Convert:s8", 
"SelectedItemsChangedMessage:s9", 
"ModelUpdateMessage:ta", 
"SliceClickedMessage:tb", 
"FunnelSliceClickedEventHandler:tc", 
"FunnelSliceClickedEventArgs:td", 
"FunnelChartVisualData:te", 
"FunnelSliceVisualDataList:tf", 
"RingSeries:tg", 
"WaterfallSeries:th", 
"WaterfallSeriesView:ti", 
"CategoryTransitionInMode:tj", 
"FinancialSeries:tk", 
"FinancialSeriesView:tl", 
"FinancialBucketCalculator:tm", 
"CategoryTransitionSourceFramePreparer:tn", 
"TransitionInSpeedType:to", 
"FinancialCalculationDataSource:tp", 
"CalculatedColumn:tq", 
"FinancialEventArgs:tr", 
"FinancialCalculationSupportingCalculations:ts", 
"ColumnSupportingCalculation:tt", 
"SupportingCalculation$1:tu", 
"SupportingCalculationStrategy:tv", 
"DataSourceSupportingCalculation:tw", 
"ProvideColumnValuesStrategy:tx", 
"AssigningCategoryStyleEventHandler:ty", 
"FinancialValueList:tz", 
"CategoryXAxis:t0", 
"CategoryXAxisView:t1", 
"FinancialEventHandler:t2", 
"StepLineSeries:t3", 
"StepLineSeriesView:t4", 
"StepAreaSeries:t5", 
"StepAreaSeriesView:t6", 
"RangeAreaSeries:t7", 
"HorizontalRangeCategorySeries:t8", 
"RangeCategorySeries:t9", 
"IHasHighLowValueCategory:ua", 
"RangeCategorySeriesView:ub", 
"RangeCategoryBucketCalculator:uc", 
"RangeCategoryFramePreparer:ud", 
"IHasCategoryTrendline:ue", 
"IHasTrendline:uf", 
"DefaultCategoryTrendlineHost:ug", 
"DefaultCategoryTrendlinePreparer:uh", 
"DefaultHighLowValueProvider:ui", 
"HighLowValuesHolder:uj", 
"CategoryMarkerManager:uk", 
"RangeValueList:ul", 
"RangeAreaSeriesView:um", 
"LineFragment:un", 
"LineFragmentView:uo", 
"LineFragmentBucketCalculator:up", 
"IStacked100Series:uq", 
"AreaFragment:ur", 
"AreaFragmentView:us", 
"AreaFragmentBucketCalculator:ut", 
"StackedSplineAreaSeries:uu", 
"SplineAreaFragment:uv", 
"SplineFragmentBase:uw", 
"SplineAreaFragmentView:ux", 
"StackedColumnSeries:uy", 
"StackedColumnSeriesView:uz", 
"StackedColumnBucketCalculator:u0", 
"ColumnFragment:u1", 
"ColumnFragmentView:u2", 
"StackedBarSeries:u3", 
"VerticalStackedSeriesBase:u4", 
"IBarSeries:u5", 
"StackedBarSeriesView:u6", 
"StackedBarBucketCalculator:u7", 
"BarFragment:u8", 
"StackedLineSeries:u9", 
"StackedSplineSeries:va", 
"SplineFragment:vb", 
"SplineFragmentView:vc", 
"SplineFragmentBucketCalculator:vd", 
"StackedSeriesFramePreparer:ve", 
"CategoryFramePreparer:vf", 
"IHasSingleValueCategory:vg", 
"DefaultSingleValueProvider:vh", 
"SingleValuesHolder:vi", 
"StackedSeriesCreatedEventHandler:vj", 
"StackedSeriesCreatedEventArgs:vk", 
"StackedSeriesVisualData:vl", 
"SeriesVisualDataList:vm", 
"AxisComponentsForView:vn", 
"AxisComponentsFromView:vo", 
"AxisFormatLabelHandler:vp", 
"VisualExportHelper:vq", 
"ContentInfo:vr", 
"LabelFontHeuristics:vs", 
"AxisRangeChangedEventHandler:vt", 
"ChartContentManager:vu", 
"ChartContentType:vv", 
"RenderRequestedEventArgs:vw", 
"ChartTitleVisualData:vx", 
"VisualDataSerializer:vy", 
"AxisVisualData:vz", 
"AxisLabelVisualDataList:v0", 
"AxisLabelVisualData:v1", 
"AssigningCategoryMarkerStyleEventHandler:v2", 
"SeriesComponentsForView:v3", 
"CategorySeriesMarkerCollisionAvoidance:v4", 
"NonCollisionAvoider:v5", 
"DataChartAxisRangeChangedEventHandler:v6", 
"ChartAxisRangeChangedEventArgs:v7", 
"ChartVisualData:v8", 
"AxisVisualDataList:v9", 
"RadialBase:wa", 
"RadialBaseView:wb", 
"RadialBucketCalculator:wc", 
"SeriesRenderer$2:wd", 
"SeriesRenderingArguments:we", 
"RadialFrame:wf", 
"RadialAxes:wg", 
"PolarBase:wh", 
"PolarBaseView:wi", 
"PolarTrendLineManager:wj", 
"PolarLinePlanner:wk", 
"AngleRadiusPair:wl", 
"PolarAxisInfoCache:wm", 
"PolarFrame:wn", 
"PolarAxes:wo", 
"SeriesCollection:wp", 
"SeriesViewerComponentsFromView:wq", 
"SeriesViewerSurfaceViewer:wr", 
"LabelPanelArranger:ws", 
"LabelPanelsArrangeState:wt", 
"ChartHitTestMode:wu", 
"WindowResponse:wv", 
"ViewerSurfaceUsage:ww", 
"SeriesViewerComponentsForView:wx", 
"DataChartCursorEventHandler:wy", 
"ChartCursorEventArgs:wz", 
"DataChartMouseButtonEventHandler:w0", 
"DataChartMouseEventHandler:w1", 
"AnnotationLayer:w2", 
"AnnotationLayerView:w3", 
"RefreshCompletedEventHandler:w4", 
"SeriesComponentsFromView:w5", 
"EasingFunctions:w6", 
"PolygonUtil:w7", 
"TrendCalculators:w8", 
"HighDensityScatterSeries:xp", 
"HighDensityScatterSeriesView:xq", 
"KDTree2D:xr", 
"KDTreeNode2D:xs", 
"KDPointData:xt", 
"SearchData:xu", 
"Monitor:xv", 
"KDTreeThunk:xw", 
"KNearestResults:xx", 
"KNearestResult:xy", 
"SearchArgs:xz", 
"ProgressiveLoadStatusEventArgs:x0", 
"IFlattener:yw", 
"DefaultFlattener:yy", 
"RearrangedList$1:yz", 
"ScatterSeries:acm", 
"ScatterSeriesView:acn", 
"AbstractEnumerable:adb", 
"AbstractEnumerator:adc", 
"GenericEnumerable$1:add", 
"GenericEnumerator$1:ade"]);


$.ig.util.defType('EnableErrorBars', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Both";
			case 2: return "Positive";
			case 3: return "Negative";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('EnableErrorBars', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('BrushSelectionMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Select";
			case 1: return "Interpolate";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('BrushSelectionMode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('CollisionAvoidanceType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Omit";
			case 2: return "Fade";
			case 3: return "OmitAndShift";
			case 4: return "FadeAndShift";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('CollisionAvoidanceType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HighDensityScatterSeries', 'Series', {
	init: function () {
		this.__minR = 0;
		this.__minG = 0;
		this.__minB = 0;
		this.__maxR = 255;
		this.__maxG = 0;
		this.__maxB = 0;
		this.__rangeR = 255;
		this.__rangeG = 0;
		this.__rangeB = 0;
		this.__tree = null;
		this.__expectedLevels = 0;
		this.__currentLevel = 0;
		this.__rendered = 0;
		this.__hasEffectiveViewport = false;
		this.__itemIndexes = null;
		this.__values = null;
		this.__alphas = null;
		$.ig.Series.prototype.init.call(this);
		this.defaultStyleKey($.ig.HighDensityScatterSeries.prototype.$type);
	},
	isScatter: function () {
		return true;
	}
	,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.xAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.xAxisProperty);
		}
	}
	,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.yAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.yAxisProperty);
		}
	}
	,
	xMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.xMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.xMemberPathProperty);
		}
	}
	,
	xColumn: function (value) {
		if (arguments.length === 1) {
			if (this._xColumn != value) {
				var oldXColumn = this.xColumn();
				this._xColumn = value;
				this.raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.xColumnPropertyName, oldXColumn, this.xColumn());
			}
			return value;
		} else {
			return this._xColumn;
		}
	}
	,
	_xColumn: null,
	yMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.yMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.yMemberPathProperty);
		}
	}
	,
	yColumn: function (value) {
		if (arguments.length === 1) {
			if (this._yColumn != value) {
				var oldYColumn = this.yColumn();
				this._yColumn = value;
				this.raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.yColumnPropertyName, oldYColumn, this.yColumn());
			}
			return value;
		} else {
			return this._yColumn;
		}
	}
	,
	_yColumn: null,
	useBruteForce: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.useBruteForceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.useBruteForceProperty);
		}
	}
	,
	progressiveLoad: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.progressiveLoadProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.progressiveLoadProperty);
		}
	}
	,
	mouseOverEnabled: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.mouseOverEnabledProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.mouseOverEnabledProperty);
		}
	}
	,
	maxRenderDepth: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.maxRenderDepthProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.HighDensityScatterSeries.prototype.maxRenderDepthProperty));
		}
	}
	,
	heatMinimum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.heatMinimumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.heatMinimumProperty);
		}
	}
	,
	heatMaximum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.heatMaximumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.heatMaximumProperty);
		}
	}
	,
	heatMinimumColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.heatMinimumColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.heatMinimumColorProperty);
		}
	}
	,
	heatMaximumColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.heatMaximumColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.HighDensityScatterSeries.prototype.heatMaximumColorProperty);
		}
	}
	,
	pointExtent: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.HighDensityScatterSeries.prototype.pointExtentProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.HighDensityScatterSeries.prototype.pointExtentProperty));
		}
	}
	,
	resetTree: function () {
		if (this.__tree == null) {
			return;
		}
		this.__tree.cancelLoad();
		var $t = this.__tree;
		$t.progressiveThunkCompleted = $.ig.Delegate.prototype.remove($t.progressiveThunkCompleted, this._tree_ProgressiveThunkCompleted.runOn(this));
		this.__tree = null;
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.Series.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				if (this.seriesViewer() != null) {
					var ev = this.getEffectiveViewport1(this.view());
					this.__hasEffectiveViewport = !ev.isEmpty();
				}
				break;
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				this.resetTree();
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue) != null) {
					(oldValue).deregisterColumn(this.xColumn());
					(oldValue).deregisterColumn(this.yColumn());
					this.xColumn(null);
					this.yColumn(null);
				}
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue) != null) {
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				if ((this.yAxis() != null && !this.yAxis().updateRange()) || (this.xAxis() != null && !this.xAxis().updateRange())) {
					this.renderSeries(false);
				}
				this.notifyThumbnailDataChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.xAxisPropertyName:
				var oldAxis = this.__xAxis;
				this.__xAxis = this.xAxis();
				this.resetTree();
				if (oldAxis != null) {
					oldAxis.rangeChanged = $.ig.Delegate.prototype.remove(oldAxis.rangeChanged, this.axis_RangeChanged.runOn(this));
				}
				if (this.__xAxis != null) {
					var $t = this.__xAxis;
					$t.rangeChanged = $.ig.Delegate.prototype.combine($t.rangeChanged, this.axis_RangeChanged.runOn(this));
				}
				if (oldValue != null) {
					(oldValue).deregisterSeries(this);
				}
				if (newValue != null) {
					(newValue).registerSeries(this);
				}
				if ((this.xAxis() != null && !this.xAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				break;
			case $.ig.HighDensityScatterSeries.prototype.yAxisPropertyName:
				var oldYAxis = this.__yAxis;
				this.__yAxis = this.yAxis();
				this.resetTree();
				if (oldYAxis != null) {
					oldYAxis.rangeChanged = $.ig.Delegate.prototype.remove(oldYAxis.rangeChanged, this.axis_RangeChanged.runOn(this));
				}
				if (this.__yAxis != null) {
					var $t1 = this.__yAxis;
					$t1.rangeChanged = $.ig.Delegate.prototype.combine($t1.rangeChanged, this.axis_RangeChanged.runOn(this));
				}
				if (oldValue != null) {
					(oldValue).deregisterSeries(this);
				}
				if (newValue != null) {
					(newValue).registerSeries(this);
				}
				if ((this.yAxis() != null && !this.yAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				break;
			case $.ig.HighDensityScatterSeries.prototype.mouseOverEnabledPropertyName:
				this.__mouseOverEnabled = this.mouseOverEnabled();
				this.renderSeries(false);
				break;
			case $.ig.HighDensityScatterSeries.prototype.xMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.xColumn());
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
				}
				break;
			case $.ig.HighDensityScatterSeries.prototype.xColumnPropertyName:
				this.resetTree();
				if (this.xAxis() != null && !this.xAxis().updateRange()) {
					this.renderSeries(false);
				}
				this.notifyThumbnailDataChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.yMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.yColumn());
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				break;
			case $.ig.HighDensityScatterSeries.prototype.yColumnPropertyName:
				this.resetTree();
				if (this.yAxis() != null && !this.yAxis().updateRange()) {
					this.renderSeries(false);
				}
				this.notifyThumbnailDataChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.useBruteForcePropertyName:
				this.resetTree();
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.heatMinimumPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.heatMaximumPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.maxRenderDepthPropertyName:
				this.renderSeries(false);
				break;
			case $.ig.HighDensityScatterSeries.prototype.heatMaximumColorPropertyName:
			case $.ig.HighDensityScatterSeries.prototype.heatMinimumColorPropertyName:
				if (!this.superView().colorScaleValid(this.heatMinimumColor(), this.heatMaximumColor())) {
					return;
				}
				this.__minR = this.heatMinimumColor().r();
				this.__minG = this.heatMinimumColor().g();
				this.__minB = this.heatMinimumColor().b();
				this.__maxR = this.heatMaximumColor().r();
				this.__maxG = this.heatMaximumColor().g();
				this.__maxB = this.heatMaximumColor().b();
				this.__rangeR = this.__maxR - this.__minR;
				this.__rangeG = this.__maxG - this.__minG;
				this.__rangeB = this.__maxB - this.__minB;
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.pointExtentPropertyName:
				this.__pointExtent = Math.max($.ig.util.getValue(newValue) - 1, 0);
				this.__drawExtent = this.__pointExtent > 0;
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.HighDensityScatterSeries.prototype.progressiveLoadPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	canUseAsYAxis: function (axis) {
		if ($.ig.util.cast($.ig.NumericYAxis.prototype.$type, axis) !== null) {
			return true;
		}
		return false;
	}
	,
	canUseAsXAxis: function (axis) {
		if ($.ig.util.cast($.ig.NumericXAxis.prototype.$type, axis) !== null) {
			return true;
		}
		return false;
	}
	,
	axis_RangeChanged: function (sender, e) {
		this.resetTree();
		this.renderSeries(false);
	}
	,
	__pointExtent: 0,
	__drawExtent: false,
	__minR: 0,
	__minG: 0,
	__minB: 0,
	__maxR: 0,
	__maxG: 0,
	__maxB: 0,
	__rangeR: 0,
	__rangeG: 0,
	__rangeB: 0,
	__tree: null,
	__scalerParamsX: null,
	__scalerParamsY: null,
	__xAxis: null,
	__yAxis: null,
	__mouseOverEnabled: false,
	validateSeries: function (viewportRect, windowRect, view) {
		var ret = $.ig.Series.prototype.validateSeries.call(this, viewportRect, windowRect, view);
		if (this.yAxis() == null || this.xAxis() == null || this.xAxis().seriesViewer() == null || this.yAxis().seriesViewer() == null || this.yColumn() == null || this.xColumn() == null || this.yColumn().count() < 1 || this.xColumn().count() < 1 || this.yColumn().count() != this.xColumn().count() || this.xAxis().actualMinimumValue() == this.xAxis().actualMaximumValue() || this.yAxis().actualMinimumValue() == this.yAxis().actualMaximumValue() || this.viewport().isEmpty()) {
			ret = false;
		}
		return ret;
	}
	,
	__progressiveStatus: 0,
	progressiveStatus: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__progressiveStatus;
			this.__progressiveStatus = value;
			this.raisePropertyChanged("ProgressiveStatus", oldValue, this.__progressiveStatus);
			return value;
		} else {
			return this.__progressiveStatus;
		}
	}
	,
	renderSeriesOverride: function (animate) {
		$.ig.Series.prototype.renderSeriesOverride.call(this, animate);
		this.doRender(animate, this.view());
	}
	,
	doRender: function (animate, view) {
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		if (this.__tree == null && !this.useBruteForce()) {
			var points = new $.ig.List$1($.ig.KDPointData.prototype.$type, 2, this.xColumn().count());
			var xAxis = this.xAxis();
			var yAxis = this.yAxis();
			var window = new $.ig.Rect(0, 0, 0, 1, 1);
			var viewport = new $.ig.Rect(0, 0, 0, 1, 1);
			var xParams = new $.ig.ScalerParams(1, window, viewport, xAxis.isInverted());
			var yParams = new $.ig.ScalerParams(1, window, viewport, yAxis.isInverted());
			var xVal;
			var yVal;
			for (var i = 0; i < this.xColumn().count(); i++) {
				xVal = xAxis.getScaledValue(this.xColumn().item(i), xParams);
				yVal = yAxis.getScaledValue(this.yColumn().item(i), yParams);
				if (!$.ig.util.isNaN(xVal) && !$.ig.util.isNaN(yVal) && !Number.isInfinity(xVal) && !Number.isInfinity(yVal)) {
					points.add((function () {
						var $ret = new $.ig.KDPointData();
						$ret._x = xVal;
						$ret._y = yVal;
						$ret._index = i;
						return $ret;
					}()));
				}
			}
			if (this.progressiveLoad()) {
				this.__currentLevel = 1;
				this.__expectedLevels = $.ig.truncate(Math.logBase(points.count(), 2)) + 3;
				this.progressiveStatus($.ig.truncate(((this.__currentLevel / this.__expectedLevels) * 100)));
				if (this.progressiveLoadStatusChanged != null) {
					this.progressiveLoadStatusChanged(this, new $.ig.ProgressiveLoadStatusEventArgs(this.progressiveStatus()));
				}
				this.__tree = $.ig.KDTree2D.prototype.getProgressive(points.toArray(), 1);
				var $t = this.__tree;
				$t.progressiveThunkCompleted = $.ig.Delegate.prototype.combine($t.progressiveThunkCompleted, this._tree_ProgressiveThunkCompleted.runOn(this));
				if (!this.__tree.progressiveStep()) {
					var $t1 = this.__tree;
					$t1.progressiveThunkCompleted = $.ig.Delegate.prototype.remove($t1.progressiveThunkCompleted, this._tree_ProgressiveThunkCompleted.runOn(this));
				}
				this.notifyThumbnailAppearanceChanged();
			} else {
				var before = $.ig.Date.prototype.now();
				this.__tree = new $.ig.KDTree2D(0, points.toArray(), 1);
				var after = $.ig.Date.prototype.now();
			}
		}
		if (this.progressiveLoad() && !this.useBruteForce()) {
			this.lockedRender(view);
		} else {
			this.renderBitmap(view);
		}
	}
	,
	__resolution: 0,
	__expectedLevels: 0,
	__currentLevel: 0,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		$.ig.Series.prototype.dataUpdatedOverride.call(this, action, position, count, propertyName);
		this.resetTree();
		var refresh = false;
		if (this.xAxis() != null && !this.xAxis().updateRange()) {
			refresh = true;
		}
		if (this.yAxis() != null && !this.yAxis().updateRange()) {
			refresh = true;
		}
		if (refresh) {
			this.renderSeries(false);
		}
		this.notifyThumbnailDataChanged();
	}
	,
	assertMouseOver: function (view) {
		var hdView = view;
		var pixelCount = this.__imageWidth * this.__imageHeight;
		if (this.__mouseOverEnabled) {
			if (this.__itemIndexes == null || this.__itemIndexes.length != pixelCount) {
				this.__itemIndexes = new Array(pixelCount);
				for (var i = 0; i < pixelCount; i++) {
					this.__itemIndexes[i] = 0;
				}
			} else {
				for (var i1 = 0; i1 < pixelCount; i1++) {
					this.__itemIndexes[i1] = 0;
				}
			}
		}
	}
	,
	renderBitmap: function (view) {
		var window;
		var viewport;
		var oldIndexes = null;
		if (view.isThumbnailView()) {
			oldIndexes = this.__itemIndexes;
		}
		var $ret = view.getViewInfo(viewport, window);
		viewport = $ret.p0;
		window = $ret.p1;
		var superView = view;
		var effectiveViewport = this.getEffectiveViewport1(view);
		this.__scalerParamsX = new $.ig.ScalerParams(0, window, viewport, this.__xAxis.isInverted(), effectiveViewport);
		this.__scalerParamsY = new $.ig.ScalerParams(0, window, viewport, this.__yAxis.isInverted(), effectiveViewport);
		this.assertBitmap(view);
		this.assertMouseOver(view);
		var pixelCount = this.__imageWidth * this.__imageHeight;
		if (this.__values == null || this.__values.length != pixelCount) {
			this.__values = new Array(pixelCount);
			this.__alphas = new Array(pixelCount);
		}
		var valuesLength = this.__values.length;
		var values = this.__values;
		var alphas = this.__alphas;
		for (var i = 0; i < valuesLength; i++) {
			values[i] = 0;
			alphas[i] = 0;
		}
		this.__resolution = $.ig.truncate(Math.round(this.resolution()));
		this.__pixels = superView.getPixelBuffer();
		var pixelsLength = this.__pixels.length;
		var pixels = this.__pixels;
		for (var i1 = 0; i1 < pixelsLength; i1++) {
			pixels[i1] = 0;
		}
		var hmin = this.heatMinimum();
		var hmax = this.heatMaximum();
		if ($.ig.util.isNaN(hmin) || Number.isInfinity(hmin)) {
			hmin = 0;
		}
		if ($.ig.util.isNaN(hmax) || Number.isInfinity(hmax)) {
			hmax = 50;
		}
		this.__heatMinimum = Math.min(hmin, hmax);
		this.__heatMaximum = Math.max(hmin, hmax);
		if (this.__heatMinimum < 0) {
			this.__heatMinimum = 0;
		}
		if (this.__heatMaximum < 0) {
			this.__heatMaximum = 0;
		}
		this.__heatRange = this.__heatMaximum - this.__heatMinimum;
		if (this.useBruteForce()) {
			this.bruteForceRender(view);
		} else {
			this.useTree(view);
		}
		superView.updateBitmap();
		if (view.isThumbnailView()) {
			this.__itemIndexes = oldIndexes;
			this.superView().updateImageValues();
		}
	}
	,
	lockedRender: function (view) {
		if (this.__tree == null) {
			return;
		}
		this.renderBitmap(view);
	}
	,
	progressiveLoadStatusChanged: null,
	_tree_ProgressiveThunkCompleted: function (sender, e) {
		var $self = this;
		this.superView().defer(function () {
			if ($self.__currentLevel < $self.__expectedLevels - 1) {
				$self.__currentLevel++;
			}
			$self.progressiveStatus($.ig.truncate((($self.__currentLevel / $self.__expectedLevels) * 100)));
			if ($self.progressiveLoadStatusChanged != null) {
				$self.progressiveLoadStatusChanged($self, new $.ig.ProgressiveLoadStatusEventArgs($self.progressiveStatus()));
			}
			$self.lockedRender($self.view());
			$self.notifyThumbnailAppearanceChanged();
			if ($self.__tree != null) {
				if (!$self.__tree.progressiveStep()) {
					var $t = $self.__tree;
					$t.progressiveThunkCompleted = $.ig.Delegate.prototype.remove($t.progressiveThunkCompleted, $self._tree_ProgressiveThunkCompleted.runOn($self));
					$self.progressiveStatus(100);
					if ($self.progressiveLoadStatusChanged != null) {
						$self.progressiveLoadStatusChanged($self, new $.ig.ProgressiveLoadStatusEventArgs($self.progressiveStatus()));
					}
				}
			}
		});
	}
	,
	bruteForceRender: function (view) {
		var xValues = this.xColumn().asArray().clone();
		this.xAxis().getScaledValueList(xValues, 0, xValues.length, this.__scalerParamsX);
		var yValues = this.yColumn().asArray().clone();
		this.__rendered = 0;
		var pointExtent = this.__pointExtent;
		var drawExtent = this.__drawExtent;
		var valLength = this.__values.length;
		this.yAxis().getScaledValueList(yValues, 0, yValues.length, this.__scalerParamsY);
		var color = 255 << 24 | $.ig.truncate(this.__minR) << 16 | $.ig.truncate(this.__minG) << 8 | $.ig.truncate(this.__minB);
		for (var i = 0; i < xValues.length; i++) {
			var posX = $.ig.truncate(xValues[i]);
			var posY = $.ig.truncate(yValues[i]);
			posX = $.ig.truncate(Math.floor(posX));
			posY = $.ig.truncate(Math.floor(posY));
			if (posX < 0 || posX >= this.__imageWidth || posY < 0 || posY >= this.__imageHeight) {
				continue;
			}
			if (drawExtent) {
				var minX = posX - pointExtent;
				var maxX = posX + pointExtent;
				maxX = maxX > (this.__imageWidth - 1) ? (this.__imageWidth - 1) : maxX;
				minX = minX < 0 ? 0 : minX;
				var maxY = posY + pointExtent;
				var minY = posY - pointExtent;
				for (var x = minX; x <= maxX; x++) {
					for (var y = minY; y <= maxY; y++) {
						var pos = (y * this.__imageWidth) + x;
						if (pos < 0 || pos > valLength - 1) {
							continue;
						}
						this.__values[pos] = this.__values[pos] + 1;
						this.__alphas[pos] = 1;
						this.__rendered++;
						if (this.__mouseOverEnabled) {
							this.__itemIndexes[pos] = i + 1;
						}
					}
				}
			} else {
				var pos1 = (posY * this.__imageWidth) + posX;
				this.__values[pos1] = this.__values[pos1] + 1;
				this.__alphas[pos1] = 1;
				this.__rendered++;
				if (this.__mouseOverEnabled) {
					this.__itemIndexes[pos1] = i + 1;
				}
			}
		}
		this.renderImage();
	}
	,
	__nodes: null,
	useTree: function (view) {
		var $self = this;
		var viewport;
		var window;
		var $ret = view.getViewInfo(viewport, window);
		viewport = $ret.p0;
		window = $ret.p1;
		if (this.__tree == null) {
			return;
		}
		if (this.__nodes == null) {
			this.__nodes = new $.ig.List$1($.ig.KDTreeNode2D.prototype.$type, 2, $.ig.truncate(Math.round(viewport.width() * viewport.height())));
		} else {
			this.__nodes.clear();
		}
		var minX = window.left();
		var maxX = window.right();
		var minY = window.top();
		var maxY = window.bottom();
		this.__windowTop = window.top();
		this.__windowHeight = window.height();
		this.__windowLeft = window.left();
		this.__windowWidth = window.width();
		this.__viewportTop = viewport.top();
		this.__viewportHeight = viewport.height();
		this.__viewportLeft = viewport.left();
		this.__viewportWidth = viewport.width();
		var effectiveViewport = this.getEffectiveViewport1(view);
		this.__effectiveViewportLeft = (effectiveViewport.left() - this.__viewportLeft) / this.__viewportWidth;
		this.__effectiveViewportTop = (effectiveViewport.top() - this.__viewportTop) / this.__viewportHeight;
		var effectiveRight = (effectiveViewport.right() - this.__viewportLeft) / this.__viewportWidth;
		this.__effectiveViewportWidth = effectiveRight - this.__effectiveViewportLeft;
		var effectiveBottom = (effectiveViewport.bottom() - this.__viewportTop) / this.__viewportHeight;
		this.__effectiveViewportHeight = effectiveBottom - this.__effectiveViewportTop;
		if (this.__hasEffectiveViewport) {
			minX = (minX - this.__effectiveViewportLeft) / this.__effectiveViewportWidth;
			maxX = (maxX - this.__effectiveViewportLeft) / this.__effectiveViewportWidth;
			minY = (minY - this.__effectiveViewportTop) / this.__effectiveViewportHeight;
			maxY = (maxY - this.__effectiveViewportTop) / this.__effectiveViewportHeight;
		}
		var onePixelX = this.resolution() / viewport.width() * window.width();
		var onePixelY = this.resolution() / viewport.height() * window.height();
		var pizelSize = Math.min(onePixelX, onePixelY);
		var args = (function () {
			var $ret = new $.ig.SearchArgs();
			$ret._minX = minX;
			$ret._maxX = maxX;
			$ret._minY = minY;
			$ret._maxY = maxY;
			$ret._pixelSizeX = onePixelX;
			$ret._pixelSizeY = onePixelY;
			$ret._maxRenderDepth = $self.maxRenderDepth();
			return $ret;
		}());
		this.__tree.getVisible(this.__nodes, args, 0, 1, 0, 1);
		var current;
		this.__rendered = 0;
		for (var i = 0; i < this.__nodes.count(); i++) {
			current = this.__nodes.__inner[i];
			this.renderNode(current);
		}
		this.renderImage();
	}
	,
	__heatMinimum: 0,
	__heatMaximum: 0,
	__heatRange: 0,
	__viewportTop: 0,
	__viewportHeight: 0,
	__viewportLeft: 0,
	__viewportWidth: 0,
	__windowLeft: 0,
	__windowWidth: 0,
	__windowTop: 0,
	__windowHeight: 0,
	__effectiveViewportLeft: 0,
	__effectiveViewportTop: 0,
	__effectiveViewportWidth: 0,
	__effectiveViewportHeight: 0,
	renderNode: function (current) {
		if (current._unfinished) {
			return;
		}
		var pixelCutoff = current._searchData != null && current._searchData._isCutoff;
		var otherCount = current._otherPoints == null ? 0 : current._otherPoints.length;
		var val = (current._descendantCount - otherCount) + 1;
		if (pixelCutoff && val > 0) {
		} else {
			val = 1;
		}
		this.renderPointData(current._median, val, pixelCutoff, current._searchData);
		if (otherCount > 0 && !pixelCutoff) {
			var other;
			for (var i = 0; i < otherCount; i++) {
				other = current._otherPoints[i];
				this.renderPointData(other, val, false, current._searchData);
			}
		}
		if (current._searchData != null) {
			current._searchData._isCutoff = false;
		}
	}
	,
	scrollIntoView: function (item) {
		var windowRect = this.seriesViewer() != null ? this.seriesViewer().actualWindowRect() : $.ig.Rect.prototype.empty();
		var viewportRect = this.seriesViewer() != null ? this.seriesViewer().viewportRect() : $.ig.Rect.prototype.empty();
		var unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
		var effectiveViewportRect = this.getEffectiveViewportForUnitViewport(this.view());
		var xParams = new $.ig.ScalerParams(0, unitRect, unitRect, this.xAxis().isInverted(), effectiveViewportRect);
		var yParams = new $.ig.ScalerParams(0, unitRect, unitRect, this.yAxis().isInverted(), effectiveViewportRect);
		var index = !windowRect.isEmpty() && !viewportRect.isEmpty() && this.fastItemsSource() != null ? this.fastItemsSource().indexOf(item) : -1;
		var cx = this.xAxis() != null && this.xColumn() != null && index < this.xColumn().count() ? this.xAxis().getScaledValue(this.xColumn().item(index), xParams) : NaN;
		var cy = this.yAxis() != null && this.yColumn() != null && index < this.yColumn().count() ? this.yAxis().getScaledValue(this.yColumn().item(index), yParams) : NaN;
		if (!$.ig.util.isNaN(cx)) {
			if (cx < windowRect.left() + 0.1 * windowRect.width()) {
				cx = cx + 0.4 * windowRect.width();
				windowRect.x(cx - 0.5 * windowRect.width());
			}
			if (cx > windowRect.right() - 0.1 * windowRect.width()) {
				cx = cx - 0.4 * windowRect.width();
				windowRect.x(cx - 0.5 * windowRect.width());
			}
		}
		if (!$.ig.util.isNaN(cy)) {
			if (cy < windowRect.top() + 0.1 * windowRect.height()) {
				cy = cy + 0.4 * windowRect.height();
				windowRect.y(cy - 0.5 * windowRect.height());
			}
			if (cy > windowRect.bottom() - 0.1 * windowRect.height()) {
				cy = cy - 0.4 * windowRect.height();
				windowRect.y(cy - 0.5 * windowRect.height());
			}
		}
		if (this.syncLink() != null) {
			this.syncLink().windowNotify(this.seriesViewer(), windowRect);
		}
		return index >= 0;
	}
	,
	renderPointData: function (pointData, p, isCutoff, searchData) {
		var color = this.getColorFromValue(p);
		var index = pointData._index;
		var pointExtent = this.__pointExtent;
		if (isCutoff) {
			var minXVal = searchData._minX;
			var maxXVal = searchData._maxX;
			var minYVal = searchData._minY;
			var maxYVal = searchData._maxY;
			if (this.__hasEffectiveViewport) {
				minXVal = this.__effectiveViewportLeft + this.__effectiveViewportWidth * minXVal;
				maxXVal = this.__effectiveViewportLeft + this.__effectiveViewportWidth * maxXVal;
				minYVal = this.__effectiveViewportTop + this.__effectiveViewportHeight * minYVal;
				maxYVal = this.__effectiveViewportTop + this.__effectiveViewportHeight * maxYVal;
			}
			var minX = $.ig.truncate((this.__viewportLeft + this.__viewportWidth * (minXVal - this.__windowLeft) / this.__windowWidth));
			var maxX = $.ig.truncate((this.__viewportLeft + this.__viewportWidth * (maxXVal - this.__windowLeft) / this.__windowWidth));
			var minY = $.ig.truncate((this.__viewportTop + this.__viewportHeight * (minYVal - this.__windowTop) / this.__windowHeight));
			var maxY = $.ig.truncate((this.__viewportTop + this.__viewportHeight * (maxYVal - this.__windowTop) / this.__windowHeight));
			minX = $.ig.truncate(Math.floor(minX));
			maxX = $.ig.truncate(Math.floor(maxX));
			minY = $.ig.truncate(Math.floor(minY));
			maxY = $.ig.truncate(Math.floor(maxY));
			if (this.__drawExtent) {
				minX -= pointExtent;
				maxX += pointExtent;
				minY -= pointExtent;
				maxY += pointExtent;
			}
			var area = ((maxX - minX) + 1) * ((maxY - minY) + 1);
			var dens = p / area;
			var alpha = dens;
			for (var i = minX; i <= maxX; i++) {
				for (var j = minY; j <= maxY; j++) {
					this.renderPixel(index, i, j, dens, alpha);
				}
			}
		} else {
			var xVal = pointData._x;
			var yVal = pointData._y;
			if (this.__hasEffectiveViewport) {
				xVal = this.__effectiveViewportLeft + this.__effectiveViewportWidth * xVal;
				yVal = this.__effectiveViewportTop + this.__effectiveViewportHeight * yVal;
			}
			var posX = $.ig.truncate((this.__viewportLeft + this.__viewportWidth * (xVal - this.__windowLeft) / this.__windowWidth));
			var posY = $.ig.truncate((this.__viewportTop + this.__viewportHeight * (yVal - this.__windowTop) / this.__windowHeight));
			posX = $.ig.truncate(Math.floor(posX));
			posY = $.ig.truncate(Math.floor(posY));
			if (this.__drawExtent) {
				var minX1 = posX - pointExtent;
				var maxX1 = posX + pointExtent;
				var maxY1 = posY + pointExtent;
				var minY1 = posY - pointExtent;
				var area1 = ((maxX1 - minX1) + 1) * ((maxY1 - minY1) + 1);
				var dens1 = p / area1;
				var alpha1 = dens1;
				for (var i1 = minX1; i1 <= maxX1; i1++) {
					for (var j1 = minY1; j1 <= maxY1; j1++) {
						this.renderPixel(index, i1, j1, dens1, alpha1);
					}
				}
			} else {
				this.renderPixel(index, posX, posY, p, 1);
			}
		}
	}
	,
	getAlphaColorFromValue: function (p, alpha) {
		return $.ig.truncate((Math.floor(255 * alpha))) << 24 | $.ig.truncate(Math.floor(alpha * (this.__minR + p * this.__rangeR))) << 16 | $.ig.truncate(Math.floor(alpha * (this.__minG + p * this.__rangeG))) << 8 | $.ig.truncate(Math.floor(alpha * (this.__minB + p * this.__rangeB)));
	}
	,
	getColorFromValue: function (p) {
		return 255 << 24 | $.ig.truncate(Math.floor((this.__minR + p * this.__rangeR))) << 16 | $.ig.truncate(Math.floor((this.__minG + p * this.__rangeG))) << 8 | $.ig.truncate(Math.floor((this.__minB + p * this.__rangeB)));
	}
	,
	getValueFromColor: function (color) {
		return (color >> 24 & 255) / 255;
	}
	,
	renderPixel: function (index, posX, posY, p, alpha) {
		if (posX < 0 || posX >= this.__imageWidth || posY < 0 || posY >= this.__imageHeight) {
			return;
		}
		var pos = (posY * this.__imageWidth) + posX;
		this.__values[pos] = this.__values[pos] + p;
		this.__alphas[pos] = this.__alphas[pos] + alpha;
		if (this.__mouseOverEnabled) {
			this.__itemIndexes[pos] = index + 1;
		}
		this.__rendered += $.ig.truncate(p);
	}
	,
	renderImage: function () {
		var val = 0;
		var alpha = 0;
		var heatRange = this.__heatRange;
		var heatMinimum = this.__heatMinimum;
		var heatMaximum = this.__heatMaximum;
		var valuesLength = this.__values.length;
		var values = this.__values;
		var alphas = this.__alphas;
		var pixels = this.__pixels;
		var lastVal = NaN;
		var lastAlpha = NaN;
		var color = 0;
		var pixelPos = 0;
		for (var i = 0; i < valuesLength; i++) {
			val = values[i];
			if (val != 0) {
				alpha = alphas[i];
				if (val >= heatMaximum) {
					val = 1;
				} else if (val <= heatMinimum) {
					val = 0;
				} else {
					val = (val - heatMinimum) / (heatRange);
				}
				if (alpha > 1) {
					alpha = 1;
				}
				if (alpha < 0.2) {
					alpha = 0.2;
				}
				if (lastVal != val || lastAlpha != alpha) {
					color = this.getAlphaColorFromValue(val, alpha);
					lastVal = val;
					lastAlpha = alpha;
				}
				pixels[pixelPos] = color >> 16 & 255;
				pixels[pixelPos + 1] = color >> 8 & 255;
				pixels[pixelPos + 2] = color & 255;
				pixels[pixelPos + 3] = color >> 24 & 255;
			}
			pixelPos += 4;
		}
	}
	,
	__imageWidth: 0,
	__imageHeight: 0,
	__pixels: null,
	__rendered: 0,
	assertBitmap: function (view) {
		var hdView = view;
		hdView.assertBitmap();
	}
	,
	createView: function () {
		return new $.ig.HighDensityScatterSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.Series.prototype.onViewCreated.call(this, view);
		this.superView(view);
	}
	,
	_superView: null,
	superView: function (value) {
		if (arguments.length === 1) {
			this._superView = value;
			return value;
		} else {
			return this._superView;
		}
	}
	,
	getRange: function (axis) {
		if (axis != null && axis == this.xAxis() && this.xColumn() != null) {
			return new $.ig.AxisRange(this.xColumn().minimum(), this.xColumn().maximum());
		}
		if (axis != null && axis == this.yAxis() && this.yColumn() != null) {
			return new $.ig.AxisRange(this.yColumn().minimum(), this.yColumn().maximum());
		}
		return null;
	}
	,
	__hasEffectiveViewport: false,
	viewportRectChangedOverride: function (oldViewportRect, newViewportRect) {
		if (this.seriesViewer() != null) {
			var ev = this.getEffectiveViewport1(this.view());
			this.__hasEffectiveViewport = !ev.isEmpty();
		}
		this.renderSeries(false);
	}
	,
	windowRectChangedOverride: function (oldWindowRect, newWindowRect) {
		this.renderSeries(false);
	}
	,
	__itemIndexes: null,
	__values: null,
	__alphas: null,
	getItem: function (world) {
		if (!this.__mouseOverEnabled || this.__itemIndexes == null || this.seriesViewer() == null || this.fastItemsSource() == null || this.__itemIndexes.length != (this.__imageWidth * this.__imageHeight)) {
			return null;
		}
		var windowRect = this.seriesViewer().actualWindowRect();
		var windowX = (world.__x - windowRect.left()) / windowRect.width();
		var windowY = (world.__y - windowRect.top()) / windowRect.height();
		var pixelX = $.ig.truncate(Math.round(this.viewport().left() + (this.viewport().width() * windowX)));
		var pixelY = $.ig.truncate(Math.round(this.viewport().top() + (this.viewport().height() * windowY)));
		var index = this.tryGetIndex(pixelX, pixelY);
		if (index < 0 || index > this.__itemIndexes.length - 1) {
			return null;
		}
		var itemIndex = this.__itemIndexes[index] - 1;
		if (itemIndex < 0 || itemIndex > this.fastItemsSource().count()) {
			return null;
		}
		return this.fastItemsSource().item(itemIndex);
	}
	,
	tryGetIndex: function (pixelX, pixelY) {
		var index = (this.__imageWidth * pixelY) + pixelX;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		var dist = 1;
		index = (this.__imageWidth * (pixelY - dist)) + pixelX;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY + dist)) + pixelX;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY)) + pixelX - 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY)) + pixelX + 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY - dist)) + pixelX - 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY + dist)) + pixelX + 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY - dist)) + pixelX + 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		index = (this.__imageWidth * (pixelY + dist)) + pixelX - 1;
		if (index > 0 && index < this.__itemIndexes.length && this.__itemIndexes[index] > 0) {
			return index;
		}
		return 0;
	}
	,
	updateImageValues: function (pixels, imageWidth, imageHeight) {
		this.__pixels = pixels;
		this.__imageWidth = imageWidth;
		this.__imageHeight = imageHeight;
	}
	,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.Series.prototype.clearRendering.call(this, wipeClean, view);
		this.superView().clearBitmap();
		if (this.thumbnailView() != null) {
			(this.thumbnailView()).clearBitmap();
		}
		this.notifyThumbnailAppearanceChanged();
	}
	,
	renderThumbnail: function (viewportRect, surface) {
		$.ig.Series.prototype.renderThumbnail.call(this, viewportRect, surface);
		if (!this.thumbnailDirty()) {
			this.view().prepSurface(surface);
			return;
		}
		this.view().prepSurface(surface);
		if (this.clearAndAbortIfInvalid1(this.thumbnailView())) {
			return;
		}
		var thumbnailView = $.ig.util.cast($.ig.HighDensityScatterSeriesView.prototype.$type, this.thumbnailView());
		var frame = new $.ig.ScatterFrame();
		this.doRender(false, thumbnailView);
		this.thumbnailDirty(false);
	}
	,
	_alternateFrame: null,
	alternateFrame: function (value) {
		if (arguments.length === 1) {
			this._alternateFrame = value;
			return value;
		} else {
			return this._alternateFrame;
		}
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.Series.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		var highDensityScatterSeriesView = view;
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(highDensityScatterSeriesView)) {
			return;
		}
		if (this.alternateFrame() == null) {
			this.alternateFrame(new $.ig.ScatterFrame());
		}
		this.doRender(false, view);
	}
	,
	$type: new $.ig.Type('HighDensityScatterSeries', $.ig.Series.prototype.$type)
}, true);

$.ig.util.defType('ProgressiveLoadStatusEventArgs', 'EventArgs', {
	_currentStatus: 0,
	currentStatus: function (value) {
		if (arguments.length === 1) {
			this._currentStatus = value;
			return value;
		} else {
			return this._currentStatus;
		}
	}
	,
	init: function (currentStatus) {
		$.ig.EventArgs.prototype.init.call(this);
		this.currentStatus(currentStatus);
	},
	$type: new $.ig.Type('ProgressiveLoadStatusEventArgs', $.ig.EventArgs.prototype.$type)
}, true);

$.ig.util.defType('HighDensityScatterSeriesView', 'SeriesView', {
	init: function (model) {
		$.ig.SeriesView.prototype.init.call(this, model);
		this.highDensityScatterModel(model);
	},
	onInit: function () {
		$.ig.SeriesView.prototype.onInit.call(this);
		if (!this.isThumbnailView()) {
			this.model().resolution(4);
			this.highDensityScatterModel().heatMinimumColor($.ig.Color.prototype.fromArgb(255, 0, 0, 0));
			this.highDensityScatterModel().heatMaximumColor($.ig.Color.prototype.fromArgb(255, 255, 0, 0));
		}
	}
	,
	updateImageValues: function () {
		this.highDensityScatterModel().updateImageValues(this.__pixels, this.__imageWidth, this.__imageHeight);
	}
	,
	_highDensityScatterModel: null,
	highDensityScatterModel: function (value) {
		if (arguments.length === 1) {
			this._highDensityScatterModel = value;
			return value;
		} else {
			return this._highDensityScatterModel;
		}
	}
	,
	__pixels: null,
	__imageWidth: 0,
	__imageHeight: 0,
	_offscreen: null,
	offscreen: function (value) {
		if (arguments.length === 1) {
			this._offscreen = value;
			return value;
		} else {
			return this._offscreen;
		}
	}
	,
	_imageData: null,
	imageData: function (value) {
		if (arguments.length === 1) {
			this._imageData = value;
			return value;
		} else {
			return this._imageData;
		}
	}
	,
	_offscreenContext: null,
	offscreenContext: function (value) {
		if (arguments.length === 1) {
			this._offscreenContext = value;
			return value;
		} else {
			return this._offscreenContext;
		}
	}
	,
	assertBitmap: function () {
		if (this.offscreen() == null) {
			this.offscreen($("<canvas></canvas>"));
			var cont = (this.offscreen()[0]).getContext("2d");
			this.offscreenContext(new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), cont));
		}
		var rect;
		if (this.isAlternateView()) {
			rect = this.viewport();
		} else {
			rect = this.model().seriesViewer().getContainerRect();
		}
		var viewportLeft = $.ig.truncate(Math.round(rect.left()));
		var viewportTop = $.ig.truncate(Math.round(rect.top()));
		var viewportWidth = $.ig.truncate(Math.round(rect.width()));
		var viewportHeight = $.ig.truncate(Math.round(rect.height()));
		var lastImageWidth = this.__imageWidth;
		var lastImageHeight = this.__imageHeight;
		this.__imageWidth = viewportWidth;
		this.__imageHeight = viewportHeight;
		if (lastImageWidth != this.__imageWidth || lastImageHeight != this.__imageHeight || this.__pixels == null) {
			this.offscreen().attr("width", viewportWidth.toString());
			this.offscreen().attr("height", viewportHeight.toString());
			this.imageData((this.offscreenContext().getUnderlyingContext()).getImageData(0, 0, viewportWidth, viewportHeight));
			this.__pixels = this.imageData().data;
		}
		this.highDensityScatterModel().updateImageValues(this.__pixels, this.__imageWidth, this.__imageHeight);
	}
	,
	getPixelBuffer: function () {
		return this.__pixels;
	}
	,
	updateBitmap: function () {
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		(this.offscreenContext().getUnderlyingContext()).putImageData(this.imageData(), 0, 0);
		this.makeDirty();
	}
	,
	defer: function (action) {
		window.setTimeout(action, 0);
	}
	,
	renderOverride: function (context, isHitContext) {
		$.ig.SeriesView.prototype.renderOverride.call(this, context, isHitContext);
		if (isHitContext) {
			if (!this.highDensityScatterModel().mouseOverEnabled()) {
				return;
			}
			var rect = new $.ig.Rectangle();
			rect.canvasLeft(this.viewport().left());
			rect.canvasTop(this.viewport().top());
			rect.width(this.viewport().width());
			rect.height(this.viewport().height());
			rect.__fill = this.getHitBrush();
			context.renderRectangle(rect);
			return;
		}
		if (this.offscreen() == null) {
			return;
		}
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().top()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		this.context().drawImage1(this.offscreen()[0], this.model().__opacity, viewportLeft, viewportTop, viewportWidth, viewportHeight, viewportLeft, viewportTop, viewportWidth, viewportHeight);
	}
	,
	colorScaleValid: function (HeatMinimumColor, HeatMaximumColor) {
		return $.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, HeatMinimumColor), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) && $.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, HeatMaximumColor), $.ig.util.toNullable($.ig.Color.prototype.$type, null));
	}
	,
	clearBitmap: function () {
		if (this.__pixels != null) {
			for (var i = 0; i < this.__pixels.length; i++) {
				this.__pixels[i] = 0;
			}
			this.updateBitmap();
		}
	}
	,
	exportViewShapes: function (svd) {
		$.ig.SeriesView.prototype.exportViewShapes.call(this, svd);
		if (this.__pixels == null) {
			svd.pixels(null);
			return;
		}
		var packed = new Array($.ig.intDivide(this.__pixels.length, 4));
		var pos;
		for (var i = 0; i < $.ig.intDivide(this.__pixels.length, 4); i++) {
			pos = i * 4;
			packed[i] = this.__pixels[pos + 3] << 24 | this.__pixels[pos] << 16 | this.__pixels[pos + 1] << 8 | this.__pixels[pos + 2];
		}
		svd.pixels(packed);
		svd.pixelWidth(this.__imageWidth);
	}
	,
	$type: new $.ig.Type('HighDensityScatterSeriesView', $.ig.SeriesView.prototype.$type)
}, true);

$.ig.util.defType('KDTree2D', 'Object', {
	init: function (initNumber, points, maxLeafSize) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		this.__currentProgressiveLevel = 0;
		this.__lock = {};
		this.__rand = new $.ig.Random(0);
		$.ig.Object.prototype.init.call(this);
		this.root(this.kDTreeHelper(points, 0, points.length - 1, 0, maxLeafSize));
	},
	init1: function (initNumber) {
		this.__currentProgressiveLevel = 0;
		this.__lock = {};
		this.__rand = new $.ig.Random(0);
		$.ig.Object.prototype.init.call(this);
	},
	__progressivePoints: null,
	__progressiveThunks: null,
	__toProcess: null,
	__currentProgressiveLevel: 0,
	__lock: null,
	syncLock: function () {
		return this.__lock;
	}
	,
	progressiveThunkCompleted: null,
	getProgressive: function (points, maxLeafSize) {
		var ret = new $.ig.KDTree2D(1);
		ret.root(new $.ig.KDTreeNode2D());
		ret.root()._unfinished = true;
		ret.__progressivePoints = points;
		ret.__progressiveThunks = new $.ig.Stack$1($.ig.KDTreeThunk.prototype.$type);
		ret.__toProcess = new $.ig.List$1($.ig.KDTreeThunk.prototype.$type, 0);
		var thunk = new $.ig.KDTreeThunk();
		thunk._startIndex = 0;
		thunk._endIndex = points.length - 1;
		thunk._level = 0;
		thunk._maxLeafSize = maxLeafSize;
		thunk._node = ret.root();
		ret.__progressiveThunks.push(thunk);
		return ret;
	}
	,
	progressiveStep: function () {
		if (this.__progressiveThunks.count() == 0 && this.__toProcess.count() == 0) {
			this.__progressivePoints = null;
			return false;
		}
		if (this.__progressiveThunks.count() == 0 && this.__toProcess.count() > 0) {
			return true;
		}
		this.__currentProgressiveLevel = this.__progressiveThunks.peek()._level;
		while (this.__progressiveThunks.count() > 0 && this.__progressiveThunks.peek()._level == this.__currentProgressiveLevel) {
			this.__toProcess.add(this.__progressiveThunks.pop());
		}
		window.setTimeout(this.processThunks.runOn(this), 0);
		return true;
	}
	,
	__isCancelled: false,
	cancelLoad: function () {
		this.__isCancelled = true;
	}
	,
	processThunks: function () {
		var t;
		for (var i = 0; i < this.__toProcess.count(); i++) {
			if (this.__isCancelled || this.__progressivePoints == null) {
				return;
			}
			if (this.__progressivePoints.length == 0) {
				continue;
			}
			t = this.__toProcess.__inner[i];
			this.kDTreeHelperProgressive(t._node, this.__progressivePoints, t._startIndex, t._endIndex, t._level, t._maxLeafSize);
		}
		this.__toProcess.clear();
		if (this.progressiveThunkCompleted != null) {
			this.progressiveThunkCompleted(this, new $.ig.EventArgs());
		}
	}
	,
	kDTreeHelperProgressive: function (node, points, startIndex, endIndex, level, maxLeafSize) {
		node._unfinished = false;
		node._isX = (level % 2) == 0;
		node._descendantCount = (endIndex - startIndex);
		if (startIndex == endIndex) {
			node._median = points[startIndex];
			return;
		}
		if (startIndex > endIndex) {
			node._unfinished = true;
			return;
		}
		if ((endIndex - startIndex) + 1 <= maxLeafSize) {
			node._median = points[startIndex];
			node._otherPoints = new Array((endIndex - startIndex) + 1);
			var j = 0;
			for (var i = startIndex; i <= endIndex; i++) {
				node._otherPoints[j++] = points[i];
			}
			return;
		}
		var k = Math.max($.ig.intDivide((endIndex - startIndex), 2), 1);
		var medianIndex = this.select(points, startIndex, endIndex, node._isX, k);
		node._median = points[medianIndex];
		if (startIndex <= medianIndex - 1) {
			node._leftChild = (function () {
				var $ret = new $.ig.KDTreeNode2D();
				$ret._unfinished = true;
				return $ret;
			}());
			node._leftChild._descendantCount = ((medianIndex - 1) - startIndex) + 1;
			this.__progressiveThunks.push((function () {
				var $ret = new $.ig.KDTreeThunk();
				$ret._startIndex = startIndex;
				$ret._endIndex = medianIndex - 1;
				$ret._level = level + 1;
				$ret._maxLeafSize = maxLeafSize;
				$ret._node = node._leftChild;
				return $ret;
			}()));
		} else {
			node._leftChild = null;
		}
		if (medianIndex + 1 <= endIndex) {
			node._rightChild = (function () {
				var $ret = new $.ig.KDTreeNode2D();
				$ret._unfinished = true;
				return $ret;
			}());
			node._rightChild._descendantCount = (endIndex - (medianIndex + 1)) + 1;
			this.__progressiveThunks.push((function () {
				var $ret = new $.ig.KDTreeThunk();
				$ret._startIndex = medianIndex + 1;
				$ret._endIndex = endIndex;
				$ret._level = level + 1;
				$ret._maxLeafSize = maxLeafSize;
				$ret._node = node._rightChild;
				return $ret;
			}()));
		} else {
			node._rightChild = null;
		}
	}
	,
	kDTreeHelper: function (points, startIndex, endIndex, level, maxLeafSize) {
		var node = new $.ig.KDTreeNode2D();
		node._isX = (level % 2) == 0;
		node._descendantCount = (endIndex - startIndex);
		if (startIndex == endIndex) {
			node._median = points[startIndex];
			return node;
		}
		if (startIndex > endIndex) {
			return null;
		}
		if ((endIndex - startIndex) + 1 <= maxLeafSize) {
			node._median = points[startIndex];
			node._otherPoints = new Array(endIndex - startIndex + 1);
			var j = 0;
			for (var i = startIndex; i <= endIndex; i++) {
				node._otherPoints[j++] = points[i];
			}
			return node;
		}
		var k = Math.max($.ig.intDivide((endIndex - startIndex), 2), 1);
		var medianIndex = this.select(points, startIndex, endIndex, node._isX, k);
		node._median = points[medianIndex];
		node._leftChild = this.kDTreeHelper(points, startIndex, medianIndex - 1, level + 1, maxLeafSize);
		node._rightChild = this.kDTreeHelper(points, medianIndex + 1, endIndex, level + 1, maxLeafSize);
		return node;
	}
	,
	partition: function (points, isX, startIndex, endIndex, pivotIndex) {
		var pivotValue = isX ? points[pivotIndex]._x : points[pivotIndex]._y;
		var temp = points[pivotIndex];
		points[pivotIndex] = points[endIndex];
		points[endIndex] = temp;
		var storeIndex = startIndex;
		for (var i = startIndex; i < endIndex; i++) {
			var val;
			if (isX) {
				val = points[i]._x;
			} else {
				val = points[i]._y;
			}
			if (val <= pivotValue) {
				temp = points[storeIndex];
				points[storeIndex] = points[i];
				points[i] = temp;
				storeIndex++;
			}
		}
		temp = points[endIndex];
		points[endIndex] = points[storeIndex];
		points[storeIndex] = temp;
		return storeIndex;
	}
	,
	__rand: null,
	select: function (points, startIndex, endIndex, isX, k) {
		if (startIndex == endIndex) {
			return startIndex;
		}
		var pivotIndex = this.__rand.next2(startIndex, endIndex);
		var newIndex = this.partition(points, isX, startIndex, endIndex, pivotIndex);
		var pivotDistance = newIndex - startIndex + 1;
		if (pivotDistance == k) {
			return newIndex;
		} else if (k < pivotDistance) {
			return this.select(points, startIndex, newIndex - 1, isX, k);
		} else {
			return this.select(points, newIndex + 1, endIndex, isX, k - pivotDistance);
		}
	}
	,
	kNearest: function (results, xValue, yValue, k) {
		this.kNearestHelper(results, xValue, yValue, k, this.root());
	}
	,
	kNearestHelper: function (results, xValue, yValue, k, current) {
		if (current == null || current._unfinished) {
			return;
		}
		if (current._leftChild == null && current._rightChild == null) {
			this.addNearest(results, xValue, yValue, current, current._median, true, 0, k);
			if (results._breakOut) {
				return;
			}
			if (current._otherPoints != null && current._otherPoints.length > 0) {
				for (var i = 0; i < current._otherPoints.length; i++) {
					this.addNearest(results, xValue, yValue, current, current._otherPoints[i], false, i, k);
					if (results._breakOut) {
						return;
					}
				}
			}
			return;
		}
		this.addNearest(results, xValue, yValue, current, current._median, true, 0, k);
		if (results._breakOut) {
			return;
		}
		if (current._isX) {
			if (xValue <= current._median._x) {
				this.kNearestHelper(results, xValue, yValue, k, current._leftChild);
				if (results._breakOut) {
					return;
				}
				if (this.dist(xValue, yValue, current._median._x, yValue) < results._currentFurthestDist) {
					this.kNearestHelper(results, xValue, yValue, k, current._rightChild);
				}
				if (results._breakOut) {
					return;
				}
			} else {
				this.kNearestHelper(results, xValue, yValue, k, current._rightChild);
				if (results._breakOut) {
					return;
				}
				if (this.dist(xValue, yValue, current._median._x, yValue) < results._currentFurthestDist) {
					this.kNearestHelper(results, xValue, yValue, k, current._leftChild);
				}
				if (results._breakOut) {
					return;
				}
			}
		} else {
			if (yValue <= current._median._y) {
				this.kNearestHelper(results, xValue, yValue, k, current._leftChild);
				if (results._breakOut) {
					return;
				}
				if (this.dist(xValue, yValue, xValue, current._median._y) < results._currentFurthestDist) {
					this.kNearestHelper(results, xValue, yValue, k, current._rightChild);
				}
				if (results._breakOut) {
					return;
				}
			} else {
				this.kNearestHelper(results, xValue, yValue, k, current._rightChild);
				if (results._breakOut) {
					return;
				}
				if (this.dist(xValue, yValue, xValue, current._median._y) < results._currentFurthestDist) {
					this.kNearestHelper(results, xValue, yValue, k, current._leftChild);
				}
				if (results._breakOut) {
					return;
				}
			}
		}
	}
	,
	addNearest: function (results, xValue, yValue, current, pointData, isMedian, index, k) {
		if (results._breakOut) {
			return;
		}
		if (results._consideredCount > results._consideredCutoff) {
			results._breakOut = true;
			return;
		}
		if (results._results.count() < k) {
			if ($.ig.util.isNaN(results._currentNearestDist)) {
				results._currentNearestDist = this.dist(xValue, yValue, pointData._x, pointData._y);
				results._currentFurthestDist = results._currentNearestDist;
				results._currentFurthestIndex = 0;
			}
			results._results.add((function () {
				var $ret = new $.ig.KNearestResult();
				$ret._isMedian = isMedian;
				$ret._index = index;
				$ret._node = current;
				$ret._x = pointData._x;
				$ret._y = pointData._y;
				return $ret;
			}()));
			results._consideredCount++;
			var dist = this.dist(xValue, yValue, pointData._x, pointData._y);
			if (dist < results._currentNearestDist) {
				results._currentNearestDist = dist;
			}
			if (dist > results._currentFurthestDist) {
				results._currentFurthestDist = dist;
				results._currentFurthestIndex = results._results.count() - 1;
			}
			return;
		}
		var newDist = 0;
		if (newDist < results._currentFurthestDist) {
			if (newDist < results._currentNearestDist) {
				results._currentNearestDist = newDist;
			}
			results._results.__inner[results._currentFurthestIndex] = (function () {
				var $ret = new $.ig.KNearestResult();
				$ret._isMedian = isMedian;
				$ret._index = index;
				$ret._node = current;
				$ret._x = pointData._x;
				$ret._y = pointData._y;
				return $ret;
			}());
			var maxDist = 0;
			var maxIndex = 0;
			for (var i = 0; i < results._results.count(); i++) {
				var currDist = this.dist(xValue, yValue, results._results.__inner[i]._x, results._results.__inner[i]._y);
				if (currDist > maxDist) {
					maxDist = currDist;
					maxIndex = i;
				}
			}
			results._consideredCount++;
		}
	}
	,
	dist: function (xValue, yValue, xValue2, yValue2) {
		return (xValue - xValue2) * (xValue - xValue2) + (yValue - yValue2) * (yValue - yValue2);
	}
	,
	getVisible: function (nodes, args, xMinimum, xMaximum, yMinimum, yMaximum) {
		this.getVisibleHelper(nodes, this.root(), args, xMinimum, xMaximum, yMinimum, yMaximum, false, 0);
	}
	,
	getVisibleHelper: function (nodes, currentNode, args, currentXMinimum, currentXMaximum, currentYMinimum, currentYMaximum, report, depth) {
		if (currentNode == null) {
			return;
		}
		var sd = currentNode._searchData;
		if (depth > args._maxRenderDepth || ((currentYMaximum - currentYMinimum) < args._pixelSizeY && (currentXMaximum - currentXMinimum) < args._pixelSizeX)) {
			if (currentNode._searchData == null) {
				currentNode._searchData = new $.ig.SearchData();
			}
			sd = currentNode._searchData;
			sd._isCutoff = true;
			sd._minX = currentXMinimum;
			sd._maxX = currentXMaximum;
			sd._minY = currentYMinimum;
			sd._maxY = currentYMaximum;
			nodes.add(currentNode);
			return;
		}
		if (sd != null) {
			sd._isCutoff = false;
		}
		if (currentNode._leftChild == null && currentNode._rightChild == null) {
			nodes.add(currentNode);
			return;
		}
		var leftXMinimum;
		var leftXMaximum;
		var leftYMinimum;
		var leftYMaximum;
		var rightXMinimum;
		var rightXMaximum;
		var rightYMinimum;
		var rightYMaximum;
		if (currentNode._isX) {
			leftXMinimum = currentXMinimum;
			leftXMaximum = currentNode._median._x;
			leftYMinimum = currentYMinimum;
			leftYMaximum = currentYMaximum;
			rightXMinimum = currentNode._median._x;
			rightXMaximum = currentXMaximum;
			rightYMinimum = currentYMinimum;
			rightYMaximum = currentYMaximum;
		} else {
			leftXMinimum = currentXMinimum;
			leftXMaximum = currentXMaximum;
			leftYMinimum = currentYMinimum;
			leftYMaximum = currentNode._median._y;
			rightXMinimum = currentXMinimum;
			rightXMaximum = currentXMaximum;
			rightYMinimum = currentNode._median._y;
			rightYMaximum = currentYMaximum;
		}
		if (report) {
			nodes.add(currentNode);
			this.getVisibleHelper(nodes, currentNode._leftChild, args, leftXMinimum, leftXMaximum, leftYMinimum, leftYMaximum, true, depth + 1);
			this.getVisibleHelper(nodes, currentNode._rightChild, args, rightXMinimum, rightXMaximum, rightYMinimum, rightYMaximum, true, depth + 1);
		} else {
			var addedCurrent = false;
			if (leftXMinimum >= args._minX && leftXMaximum <= args._maxX && leftYMinimum >= args._minY && leftYMaximum <= args._maxY) {
				addedCurrent = true;
				nodes.add(currentNode);
				this.getVisibleHelper(nodes, currentNode._leftChild, args, leftXMinimum, leftXMaximum, leftYMinimum, leftYMaximum, true, depth + 1);
			} else if (!(args._minX > leftXMaximum || args._maxX < leftXMinimum || args._minY > leftYMaximum || args._maxY < leftYMinimum)) {
				addedCurrent = true;
				nodes.add(currentNode);
				this.getVisibleHelper(nodes, currentNode._leftChild, args, leftXMinimum, leftXMaximum, leftYMinimum, leftYMaximum, false, depth + 1);
			}
			if (rightXMinimum >= args._minX && rightXMaximum <= args._maxX && rightYMinimum >= args._minY && rightYMaximum <= args._maxY) {
				if (!addedCurrent) {
					nodes.add(currentNode);
				}
				this.getVisibleHelper(nodes, currentNode._rightChild, args, rightXMinimum, rightXMaximum, rightYMinimum, rightYMaximum, true, depth + 1);
			} else if (!(args._minX > rightXMaximum || args._maxX < rightXMinimum || args._minY > rightYMaximum || args._maxY < rightYMinimum)) {
				if (!addedCurrent) {
					nodes.add(currentNode);
				}
				this.getVisibleHelper(nodes, currentNode._rightChild, args, rightXMinimum, rightXMaximum, rightYMinimum, rightYMaximum, false, depth + 1);
			}
		}
	}
	,
	_root: null,
	root: function (value) {
		if (arguments.length === 1) {
			this._root = value;
			return value;
		} else {
			return this._root;
		}
	}
	,
	validate: function () {
		this.validateHelper(this.root());
	}
	,
	validateHelper: function (Root) {
		if (Root == null) {
			return 0;
		}
		if (this.validateHelper(Root._leftChild) + this.validateHelper(Root._rightChild) != Root._descendantCount) {
			var a = 0;
		}
		return Root._descendantCount + 1;
	}
	,
	$type: new $.ig.Type('KDTree2D', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KNearestResults', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_consideredCount: 0,
	_consideredCutoff: 0,
	_results: null,
	_breakOut: false,
	_currentNearestDist: 0,
	_currentFurthestDist: 0,
	_currentFurthestIndex: 0,
	$type: new $.ig.Type('KNearestResults', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KNearestResult', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_index: 0,
	_isMedian: false,
	_x: 0,
	_y: 0,
	_node: null,
	$type: new $.ig.Type('KNearestResult', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KDTreeThunk', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_startIndex: 0,
	_endIndex: 0,
	_level: 0,
	_maxLeafSize: 0,
	_node: null,
	$type: new $.ig.Type('KDTreeThunk', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SearchArgs', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_minX: 0,
	_minY: 0,
	_maxX: 0,
	_maxY: 0,
	_pixelSizeX: 0,
	_pixelSizeY: 0,
	_maxRenderDepth: 0,
	$type: new $.ig.Type('SearchArgs', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KDTreeNode2D', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_unfinished: false,
	_isX: false,
	_descendantCount: 0,
	_median: null,
	_leftChild: null,
	_rightChild: null,
	_otherPoints: null,
	_searchData: null,
	$type: new $.ig.Type('KDTreeNode2D', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('SearchData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_isCutoff: false,
	_minX: 0,
	_maxX: 0,
	_minY: 0,
	_maxY: 0,
	$type: new $.ig.Type('SearchData', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('KDPointData', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_x: 0,
	_y: 0,
	_index: 0,
	$type: new $.ig.Type('KDPointData', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('MarkerManagerBase', 'Object', {
	_getItemLocationsStrategy: null,
	getItemLocationsStrategy: function (value) {
		if (arguments.length === 1) {
			this._getItemLocationsStrategy = value;
			return value;
		} else {
			return this._getItemLocationsStrategy;
		}
	}
	,
	_provideMarkerStrategy: null,
	provideMarkerStrategy: function (value) {
		if (arguments.length === 1) {
			this._provideMarkerStrategy = value;
			return value;
		} else {
			return this._provideMarkerStrategy;
		}
	}
	,
	_removeUnusedMarkers: null,
	removeUnusedMarkers: function (value) {
		if (arguments.length === 1) {
			this._removeUnusedMarkers = value;
			return value;
		} else {
			return this._removeUnusedMarkers;
		}
	}
	,
	_provideItemStrategy: null,
	provideItemStrategy: function (value) {
		if (arguments.length === 1) {
			this._provideItemStrategy = value;
			return value;
		} else {
			return this._provideItemStrategy;
		}
	}
	,
	_activeMarkerIndexesStrategy: null,
	activeMarkerIndexesStrategy: function (value) {
		if (arguments.length === 1) {
			this._activeMarkerIndexesStrategy = value;
			return value;
		} else {
			return this._activeMarkerIndexesStrategy;
		}
	}
	,
	_useDeterministicSelection: false,
	useDeterministicSelection: function (value) {
		if (arguments.length === 1) {
			this._useDeterministicSelection = value;
			return value;
		} else {
			return this._useDeterministicSelection;
		}
	}
	,
	init: function (provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy) {
		$.ig.Object.prototype.init.call(this);
		this.provideMarkerStrategy(provideMarkerStrategy);
		this.provideItemStrategy(provideItemStrategy);
		this.removeUnusedMarkers(removeUnusedMarkers);
		this.getItemLocationsStrategy(getItemLocationsStrategy);
		this.activeMarkerIndexesStrategy(activeMarkerIndexesStrategy);
	},
	winnowMarkers: function (markers, maximumMarkers, windowRect, viewportRect, currentResolution) {
	}
	,
	render: function (markers, lightweight) {
	}
	,
	activeFirstKeys: function (buckets, keys) {
		var first = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var second = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var en = keys.getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (buckets.item(key).priorityItems().count() > 0) {
				first.add(key);
			} else {
				second.add(key);
			}
		}
		var ret = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		ret.addRange(first);
		ret.addRange(second);
		return ret;
	}
	,
	selectMarkerItems: function (numToSelect, buckets, keys, markerItems) {
		while (numToSelect > 0) {
			if (numToSelect < keys.count()) {
				if (!$.ig.MarkerManagerBase.prototype.useDeterministicSelection()) {
					var ikeys = keys;
					$.ig.ArrayUtil.prototype.shuffle$1($.ig.Number.prototype.$type, ikeys);
				}
				keys = this.activeFirstKeys(buckets, keys);
				var count = numToSelect;
				for (var i = 0; i < count; i++) {
					var keyIndex = i;
					var bucket = buckets.item(keys.__inner[keyIndex]);
					var wasPriority;
					var index = (function () { var $ret = bucket.getItem(wasPriority); wasPriority = $ret.p0; return $ret.ret; }());
					markerItems.add(index);
					numToSelect--;
					if (bucket.isEmpty()) {
						buckets.remove(keys.__inner[keyIndex]);
					}
				}
			} else {
				var en = keys.getEnumerator();
				while (en.moveNext()) {
					var key = en.current();
					var bucket1 = buckets.item(key);
					var wasPriority1;
					var index1 = (function () { var $ret = bucket1.getItem(wasPriority1); wasPriority1 = $ret.p0; return $ret.ret; }());
					markerItems.add(index1);
					numToSelect--;
					if (bucket1.isEmpty()) {
						buckets.remove(key);
					}
				}
				keys = new $.ig.List$1($.ig.Number.prototype.$type, 1, buckets.keys());
			}
		}
	}
	,
	getVisibleItems: function (windowRect, viewportRect, itemLocations, visibleItems) {
		var left = viewportRect.left();
		var right = viewportRect.right();
		var top = viewportRect.top();
		var bottom = viewportRect.bottom();
		if (!windowRect.isEmpty() && !viewportRect.isEmpty()) {
			for (var i = 0; i < itemLocations.count(); ++i) {
				var x = itemLocations.item(i).__x;
				if ($.ig.util.isNaN(x)) {
					continue;
				}
				var y = itemLocations.item(i).__y;
				if ($.ig.util.isNaN(y)) {
					continue;
				}
				if (x < left || x > right) {
					continue;
				}
				if (y < top || y > bottom) {
					continue;
				}
				visibleItems.add(i);
			}
		}
	}
	,
	getBuckets: function (viewportRect, visibleItems, resolution, itemLocations) {
		var wasActive = new Array(itemLocations.count());
		var en = this.activeMarkerIndexesStrategy()().getEnumerator();
		while (en.moveNext()) {
			var index = en.current();
			if (index != -1) {
				wasActive[index] = true;
			}
		}
		var rowSize = $.ig.truncate(Math.floor(viewportRect.width() / resolution));
		var buckets = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.MarkerManagerBucket.prototype.$type, 0);
		var en1 = visibleItems.getEnumerator();
		while (en1.moveNext()) {
			var index1 = en1.current();
			var xVal = itemLocations.item(index1).__x;
			var yVal = itemLocations.item(index1).__y;
			var rowNumber = $.ig.truncate(Math.floor(yVal / resolution));
			var colNumber = $.ig.truncate(Math.floor(xVal / resolution));
			var offset = (rowNumber * rowSize) + colNumber;
			var bucket;
			if (!(function () { var $ret = buckets.tryGetValue(offset, bucket); bucket = $ret.p1; return $ret.ret; }())) {
				bucket = new $.ig.MarkerManagerBucket();
				buckets.add(offset, bucket);
			}
			if (wasActive[index1]) {
				bucket.priorityItems().add(index1);
			} else {
				bucket.items().add(index1);
			}
		}
		return buckets;
	}
	,
	$type: new $.ig.Type('MarkerManagerBase', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BubbleMarkerManager', 'MarkerManagerBase', {
	_radiusColumn: null,
	radiusColumn: function (value) {
		if (arguments.length === 1) {
			this._radiusColumn = value;
			return value;
		} else {
			return this._radiusColumn;
		}
	}
	,
	_actualRadiusColumn: null,
	actualRadiusColumn: function (value) {
		if (arguments.length === 1) {
			this._actualRadiusColumn = value;
			return value;
		} else {
			return this._actualRadiusColumn;
		}
	}
	,
	_actualMarkers: null,
	actualMarkers: function (value) {
		if (arguments.length === 1) {
			this._actualMarkers = value;
			return value;
		} else {
			return this._actualMarkers;
		}
	}
	,
	init: function (provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy) {
		$.ig.MarkerManagerBase.prototype.init.call(this, provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy);
		this.actualRadiusColumn(new $.ig.List$1(Number, 0));
		this.actualMarkers(new $.ig.List$1($.ig.Marker.prototype.$type, 0));
	},
	winnowMarkers: function (markers, maximumMarkers, windowRect, viewportRect, currentResolution) {
		var itemLocations = this.getItemLocationsStrategy()();
		markers.clear();
		this.actualRadiusColumn().clear();
		this.actualMarkers().clear();
		var visibleItems = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		maximumMarkers = Math.max(0, maximumMarkers);
		var markerItems = null;
		this.getVisibleItems(windowRect, viewportRect, itemLocations, visibleItems);
		if (maximumMarkers >= visibleItems.count()) {
			markerItems = visibleItems;
		} else {
			markerItems = new $.ig.List$1($.ig.Number.prototype.$type, 0);
			var resolution = Math.max(8, currentResolution);
			var buckets = this.getBuckets(viewportRect, visibleItems, resolution, itemLocations);
			var keys = new $.ig.List$1($.ig.Number.prototype.$type, 1, buckets.keys());
			if ($.ig.MarkerManagerBase.prototype.useDeterministicSelection()) {
				keys.sort();
			}
			this.selectMarkerItems(maximumMarkers, buckets, keys, markerItems);
		}
		for (var i = 0; i < markerItems.count(); ++i) {
			var x = itemLocations[markerItems.__inner[i]].__x;
			var y = itemLocations[markerItems.__inner[i]].__y;
			var radius = this.radiusColumn().item(markerItems.__inner[i]);
			this.actualRadiusColumn().add(radius);
			var marker = this.provideMarkerStrategy()(this.provideItemStrategy()(markerItems.__inner[i]));
			(marker.content()).item(this.provideItemStrategy()(markerItems.__inner[i]));
			var mp = new $.ig.OwnedPoint();
			mp.ownerItem((marker.content()).item());
			mp.point({ __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			if (!markers.containsKey(mp.ownerItem())) {
				markers.add(mp.ownerItem(), mp);
				this.actualMarkers().add(marker);
			}
		}
	}
	,
	render: function (markers, lightweight) {
		var keys = new $.ig.List$1($.ig.Object.prototype.$type, 1, markers.keys());
		if ($.ig.MarkerManagerBase.prototype.useDeterministicSelection()) {
			keys.sort2(function (o1, o2) {
				var point1 = markers.item(o1);
				var point2 = markers.item(o2);
				var dist1 = Math.pow(point1.point().__x, 2) + Math.pow(point1.point().__y, 2);
				var dist2 = Math.pow(point2.point().__x, 2) + Math.pow(point2.point().__y, 2);
				return (dist1).compareTo(dist2);
			});
		}
		var en = keys.getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			var point = markers.item(key);
			var marker = this.provideMarkerStrategy()(point.ownerItem());
			marker.canvasZIndex(keys.indexOf1(key));
			marker.canvasLeft(point.point().__x);
			marker.canvasTop(point.point().__y);
		}
		this.removeUnusedMarkers()(markers);
	}
	,
	$type: new $.ig.Type('BubbleMarkerManager', $.ig.MarkerManagerBase.prototype.$type)
}, true);

$.ig.util.defType('ErrorBarSettingsBase', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
		this.defaultErrorBarStyle(new $.ig.Style());
	},
	defaultErrorBarStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStyleProperty);
		}
	}
	,
	propertyChanged: null,
	propertyUpdated: null,
	raisePropertyChanged: function (name, oldValue, newValue) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(name));
		}
		if (this.propertyUpdated != null) {
			this.propertyUpdated(this, new $.ig.PropertyUpdatedEventArgs(name, oldValue, newValue));
		}
	}
	,
	$type: new $.ig.Type('ErrorBarSettingsBase', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('ScatterErrorBarSettings', 'ErrorBarSettingsBase', {
	init: function () {
		$.ig.ErrorBarSettingsBase.prototype.init.call(this);
		this.propertyUpdated = $.ig.Delegate.prototype.combine(this.propertyUpdated, this.scatterErrorBarSettings_PropertyUpdated.runOn(this));
	},
	enableErrorBarsHorizontal: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.enableErrorBarsHorizontalProperty, $.ig.EnableErrorBars.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.enableErrorBarsHorizontalProperty));
		}
	}
	,
	horizontalCalculatorReference: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorReferenceProperty, $.ig.ErrorBarCalculatorReference.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorReferenceProperty));
		}
	}
	,
	horizontalCalculator: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorProperty);
		}
	}
	,
	horizontalErrorBarCapLength: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthProperty));
		}
	}
	,
	horizontalStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalStrokeProperty);
		}
	}
	,
	horizontalStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalStrokeThicknessProperty);
		}
	}
	,
	horizontalErrorBarStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStyleProperty);
		}
	}
	,
	enableErrorBarsVertical: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.enableErrorBarsVerticalProperty, $.ig.EnableErrorBars.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.enableErrorBarsVerticalProperty));
		}
	}
	,
	verticalCalculatorReference: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalCalculatorReferenceProperty, $.ig.ErrorBarCalculatorReference.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalCalculatorReferenceProperty));
		}
	}
	,
	verticalCalculator: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalCalculatorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalCalculatorProperty);
		}
	}
	,
	verticalErrorBarCapLength: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthProperty));
		}
	}
	,
	verticalStroke: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalStrokeProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalStrokeProperty);
		}
	}
	,
	verticalStrokeThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalStrokeThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalStrokeThicknessProperty);
		}
	}
	,
	verticalErrorBarStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStyleProperty);
		}
	}
	,
	__series: null,
	series: function (value) {
		if (arguments.length === 1) {
			this.__series = value;
			return value;
		} else {
			return this.__series;
		}
	}
	,
	scatterErrorBarSettings_PropertyUpdated: function (sender, e) {
		switch (e.propertyName()) {
			case $.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._verticalCalculatorPropertyName:
				var oldCalc = $.ig.util.cast($.ig.IErrorBarCalculator.prototype.$type, e.oldValue());
				if (oldCalc != null) {
					oldCalc.changed = $.ig.Delegate.prototype.remove(oldCalc.changed, this.calculator_Changed.runOn(this));
				}
				if (this.series() != null) {
					this.series().renderSeries(false);
					if (this.series().seriesViewer() != null) {
						this.series().notifyThumbnailAppearanceChanged();
					}
				}
				var newCalc = $.ig.util.cast($.ig.IErrorBarCalculator.prototype.$type, e.newValue());
				if (newCalc != null) {
					newCalc.changed = $.ig.Delegate.prototype.combine(newCalc.changed, this.calculator_Changed.runOn(this));
				}
				break;
			case $.ig.ScatterErrorBarSettings.prototype._enableErrorBarsHorizontalPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._enableErrorBarsVerticalPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorReferencePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStylePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._horizontalStrokePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._horizontalStrokeThicknessPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._verticalCalculatorReferencePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthPropertyName:
			case $.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStylePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._verticalStrokePropertyName:
			case $.ig.ScatterErrorBarSettings.prototype._verticalStrokeThicknessPropertyName:
				if (this.series() != null) {
					this.series().renderSeries(false);
					if (this.series().seriesViewer() != null) {
						this.series().notifyThumbnailAppearanceChanged();
					}
				}
				break;
		}
	}
	,
	calculator_Changed: function (sender, e) {
		var calculator = $.ig.util.cast($.ig.IErrorBarCalculator.prototype.$type, sender);
		if (calculator != null) {
			calculator.changed = $.ig.Delegate.prototype.remove(calculator.changed, this.calculator_Changed.runOn(this));
			if (this.series() != null) {
				this.series().renderSeries(false);
			}
			calculator.changed = $.ig.Delegate.prototype.combine(calculator.changed, this.calculator_Changed.runOn(this));
		}
	}
	,
	$type: new $.ig.Type('ScatterErrorBarSettings', $.ig.ErrorBarSettingsBase.prototype.$type)
}, true);

$.ig.util.defType('ScatterBase', 'MarkerSeries', {
	createView: function () {
		return new $.ig.ScatterBaseView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.MarkerSeries.prototype.onViewCreated.call(this, view);
		this.scatterView(view);
	}
	,
	_scatterView: null,
	scatterView: function (value) {
		if (arguments.length === 1) {
			this._scatterView = value;
			return value;
		} else {
			return this._scatterView;
		}
	}
	,
	isScatter: function () {
		return true;
	}
	,
	getResolvedHitTestMode: function () {
		if (this.hitTestMode() == $.ig.SeriesHitTestMode.prototype.auto) {
			if (this.maximumMarkers() <= 2000) {
				return $.ig.SeriesHitTestMode.prototype.computational;
			} else {
				return $.ig.MarkerSeries.prototype.getResolvedHitTestMode.call(this);
			}
		} else {
			return $.ig.MarkerSeries.prototype.getResolvedHitTestMode.call(this);
		}
	}
	,
	getSeriesValueMarkerBoundingBox: function (world) {
		if (!this.hasMarkers() || !this.shouldDisplayMarkers()) {
			return $.ig.Rect.prototype.empty();
		}
		var marker = this.markerView().getHitMarker(this.fromWorldPosition(world));
		var ret = null;
		if (marker != null) {
			return this.markerView().getBoundingBoxForMarker(marker);
		}
		return $.ig.Rect.prototype.empty();
	}
	,
	testHit: function (position, isFinger) {
		if (this.testMarkersOver(position, isFinger)) {
			return true;
		}
		return false;
	}
	,
	init: function () {
		$.ig.MarkerSeries.prototype.init.call(this);
		this.thumbnailFrame(new $.ig.ScatterFrame());
		this.__cachedWindowRect = $.ig.Rect.prototype.empty();
		this.__cachedViewportRect = $.ig.Rect.prototype.empty();
	},
	_axisInfoCache: null,
	axisInfoCache: function (value) {
		if (arguments.length === 1) {
			this._axisInfoCache = value;
			return value;
		} else {
			return this._axisInfoCache;
		}
	}
	,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.xAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.xAxisProperty);
		}
	}
	,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.yAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.yAxisProperty);
		}
	}
	,
	xMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.xMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.xMemberPathProperty);
		}
	}
	,
	xColumn: function (value) {
		if (arguments.length === 1) {
			if (this._xColumn != value) {
				var oldXColumn = this.xColumn();
				this._xColumn = value;
				this.raisePropertyChanged($.ig.ScatterBase.prototype.xColumnPropertyName, oldXColumn, this.xColumn());
			}
			return value;
		} else {
			return this._xColumn;
		}
	}
	,
	_xColumn: null,
	yMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.yMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.yMemberPathProperty);
		}
	}
	,
	yColumn: function (value) {
		if (arguments.length === 1) {
			if (this._yColumn != value) {
				var oldYColumn = this.yColumn();
				this._yColumn = value;
				this.raisePropertyChanged($.ig.ScatterBase.prototype.yColumnPropertyName, oldYColumn, this.yColumn());
			}
			return value;
		} else {
			return this._yColumn;
		}
	}
	,
	_yColumn: null,
	trendLineType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineTypeProperty, $.ig.TrendLineType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterBase.prototype.trendLineTypeProperty));
		}
	}
	,
	trendLineBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.trendLineBrushProperty);
		}
	}
	,
	actualTrendLineBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.actualTrendLineBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.actualTrendLineBrushProperty);
		}
	}
	,
	trendLineThickness: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineThicknessProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.trendLineThicknessProperty);
		}
	}
	,
	trendLineDashCap: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineDashCapProperty, $.ig.PenLineCap.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterBase.prototype.trendLineDashCapProperty));
		}
	}
	,
	trendLineDashArray: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineDashArrayProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.trendLineDashArrayProperty);
		}
	}
	,
	trendLinePeriod: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLinePeriodProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ScatterBase.prototype.trendLinePeriodProperty));
		}
	}
	,
	markerCollisionAvoidance: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.markerCollisionAvoidanceProperty, $.ig.CollisionAvoidanceType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScatterBase.prototype.markerCollisionAvoidanceProperty));
		}
	}
	,
	trendLineZIndex: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.trendLineZIndexProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ScatterBase.prototype.trendLineZIndexProperty));
		}
	}
	,
	maximumMarkers: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.maximumMarkersProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ScatterBase.prototype.maximumMarkersProperty));
		}
	}
	,
	invalidateAxes: function () {
		$.ig.MarkerSeries.prototype.invalidateAxes.call(this);
		if (this.xAxis() != null) {
			this.xAxis().renderAxis1(false);
		}
		if (this.yAxis() != null) {
			this.yAxis().renderAxis1(false);
		}
	}
	,
	errorBarSettings: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterBase.prototype.errorBarSettingsProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterBase.prototype.errorBarSettingsProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.MarkerSeries.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		if (this.scatterView().trendLineManager().propertyUpdated(sender, propertyName, oldValue, newValue)) {
			this.renderSeries(false);
			this.notifyThumbnailAppearanceChanged();
		}
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue) != null) {
					(oldValue).deregisterColumn(this.xColumn());
					(oldValue).deregisterColumn(this.yColumn());
					this.xColumn(null);
					this.yColumn(null);
				}
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue) != null) {
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				if ((this.yAxis() != null && !this.yAxis().updateRange()) || (this.xAxis() != null && !this.xAxis().updateRange())) {
					this.renderSeries(false);
				}
				break;
			case $.ig.ScatterBase.prototype.xAxisPropertyName:
				if (oldValue != null) {
					(oldValue).deregisterSeries(this);
				}
				if (newValue != null) {
					(newValue).registerSeries(this);
				}
				if ((this.xAxis() != null && !this.xAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ScatterBase.prototype.yAxisPropertyName:
				if (oldValue != null) {
					(oldValue).deregisterSeries(this);
				}
				if (newValue != null) {
					(newValue).registerSeries(this);
				}
				if ((this.yAxis() != null && !this.yAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ScatterBase.prototype.xMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.xColumn());
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
				}
				break;
			case $.ig.ScatterBase.prototype.xColumnPropertyName:
				this.scatterView().trendLineManager().reset();
				if (this.xAxis() != null && !this.xAxis().updateRange()) {
					this.renderSeries(false);
				}
				break;
			case $.ig.ScatterBase.prototype.yMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.yColumn());
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				break;
			case $.ig.ScatterBase.prototype.yColumnPropertyName:
				this.scatterView().trendLineManager().reset();
				if (this.yAxis() != null && !this.yAxis().updateRange()) {
					this.renderSeries(false);
				}
				break;
			case $.ig.ScatterBase.prototype.markerCollisionAvoidancePropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ScatterBase.prototype.maximumMarkersPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.transitionProgressPropertyName:
				this._transitionFrame.interpolate3(this.transitionProgress(), this._previousFrame, this._currentFrame);
				this.cacheViewInfo();
				try {
					if (this.clearAndAbortIfInvalid1(this.view())) {
						return;
					}
					if ((Math.round(this.transitionProgress() * 100000) / 100000) == 1) {
						this.renderFrame(this._currentFrame, this.scatterView());
					} else {
						this.renderFrame(this._transitionFrame, this.scatterView());
					}
				}
				finally {
					this.unCacheViewInfo();
				}
				break;
			case $.ig.Series.prototype.trendLineBrushPropertyName:
				this.updateIndexedProperties();
				break;
			case $.ig.ScatterBase.prototype._errorBarSettingsPropertyName:
				if (this.errorBarSettings() != null) {
					this.errorBarSettings().series(this);
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.trendLineTypePropertyName:
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	canUseAsYAxis: function (axis) {
		if ($.ig.util.cast($.ig.NumericYAxis.prototype.$type, axis) !== null) {
			return true;
		}
		return false;
	}
	,
	canUseAsXAxis: function (axis) {
		if ($.ig.util.cast($.ig.NumericXAxis.prototype.$type, axis) !== null) {
			return true;
		}
		return false;
	}
	,
	unCacheViewInfo: function () {
		this.__cachedViewportRect = $.ig.Rect.prototype.empty();
		this.__cachedWindowRect = $.ig.Rect.prototype.empty();
	}
	,
	__cachedViewportRect: null,
	__cachedWindowRect: null,
	cacheViewInfo: function () {
		var $ret = this.getViewInfo(this.__cachedViewportRect, this.__cachedWindowRect);
		this.__cachedViewportRect = $ret.p0;
		this.__cachedWindowRect = $ret.p1;
	}
	,
	mustReact: function (propertyName, action) {
		if (action != $.ig.FastItemsSourceEventAction.prototype.change) {
			return true;
		}
		if (propertyName == null) {
			return true;
		}
		if (this.xMemberPath() == propertyName || this.yMemberPath() == propertyName) {
			return true;
		}
		return false;
	}
	,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		var refresh = false;
		if (!this.mustReact(propertyName, action)) {
			return;
		}
		this.scatterView().trendLineManager().dataUpdated(action, position, count, propertyName);
		if (this.xAxis() != null && !this.xAxis().updateRange()) {
			refresh = true;
		}
		if (this.yAxis() != null && !this.yAxis().updateRange()) {
			refresh = true;
		}
		if (refresh) {
			this.renderSeries(true);
		}
	}
	,
	prepLinePoints: function (frame) {
		this.prepLinePoints1(frame, null);
	}
	,
	prepLinePoints1: function (frame, clipper) {
		var xCount = this.xColumn() != null ? this.xColumn().count() : 0;
		var yCount = this.yColumn() != null ? this.yColumn().count() : 0;
		var count = Math.min(xCount, yCount);
		if (count <= this.maximumMarkers()) {
			frame.points().clear();
			var linePoints = new $.ig.List$1($.ig.OwnedPoint.prototype.$type, 0);
			var en = frame.linePoints().values().getEnumerator();
			while (en.moveNext()) {
				var point = en.current();
				linePoints.add(point);
			}
			var fastItemsSource = this.fastItemsSource();
			linePoints.sort2(function (p1, p2) {
				var index1 = fastItemsSource.indexOf(p1.ownerItem());
				var index2 = fastItemsSource.indexOf(p2.ownerItem());
				if (index1 < index2) {
					return -1;
				}
				if (index1 > index2) {
					return 1;
				}
				return 0;
			});
			if (clipper != null) {
				clipper.target(frame.points());
			}
			var en1 = linePoints.getEnumerator();
			while (en1.moveNext()) {
				var point1 = en1.current();
				if (fastItemsSource.indexOf(point1.ownerItem()) >= 0) {
					if (clipper != null) {
						clipper.add(point1.point());
					} else {
						frame.points().add({ __x: point1.point().__x, __y: point1.point().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
					}
				}
			}
		}
	}
	,
	getRange: function (axis) {
		if (axis != null && axis == this.xAxis() && this.xColumn() != null) {
			return new $.ig.AxisRange(this.xColumn().minimum(), this.xColumn().maximum());
		}
		if (axis != null && axis == this.yAxis() && this.yColumn() != null) {
			return new $.ig.AxisRange(this.yColumn().minimum(), this.yColumn().maximum());
		}
		return null;
	}
	,
	getItem: function (world) {
		return null;
	}
	,
	getItemIndex: function (world) {
		return -1;
	}
	,
	scrollIntoView: function (item) {
		var windowRect = this.seriesViewer() != null ? this.seriesViewer().actualWindowRect() : $.ig.Rect.prototype.empty();
		var viewportRect = this.seriesViewer() != null ? this.seriesViewer().viewportRect() : $.ig.Rect.prototype.empty();
		var unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
		var effectiveViewportRect = this.getEffectiveViewportForUnitViewport(this.view());
		var xParams = new $.ig.ScalerParams(0, unitRect, unitRect, this.xAxis().isInverted(), effectiveViewportRect);
		var yParams = new $.ig.ScalerParams(0, unitRect, unitRect, this.yAxis().isInverted(), effectiveViewportRect);
		var index = !windowRect.isEmpty() && !viewportRect.isEmpty() && this.fastItemsSource() != null ? this.fastItemsSource().indexOf(item) : -1;
		var cx = this.xAxis() != null && this.xColumn() != null && index < this.xColumn().count() ? this.xAxis().getScaledValue(this.xColumn().item(index), xParams) : NaN;
		var cy = this.yAxis() != null && this.yColumn() != null && index < this.yColumn().count() ? this.yAxis().getScaledValue(this.yColumn().item(index), yParams) : NaN;
		if (!$.ig.util.isNaN(cx)) {
			if (cx < windowRect.left() + 0.1 * windowRect.width()) {
				cx = cx + 0.4 * windowRect.width();
				windowRect.x(cx - 0.5 * windowRect.width());
			}
			if (cx > windowRect.right() - 0.1 * windowRect.width()) {
				cx = cx - 0.4 * windowRect.width();
				windowRect.x(cx - 0.5 * windowRect.width());
			}
		}
		if (!$.ig.util.isNaN(cy)) {
			if (cy < windowRect.top() + 0.1 * windowRect.height()) {
				cy = cy + 0.4 * windowRect.height();
				windowRect.y(cy - 0.5 * windowRect.height());
			}
			if (cy > windowRect.bottom() - 0.1 * windowRect.height()) {
				cy = cy - 0.4 * windowRect.height();
				windowRect.y(cy - 0.5 * windowRect.height());
			}
		}
		if (this.syncLink() != null) {
			this.syncLink().windowNotify(this.seriesViewer(), windowRect);
		}
		return index >= 0;
	}
	,
	viewportRectChangedOverride: function (oldViewportRect, newViewportRect) {
		this.renderSeries(false);
	}
	,
	windowRectChangedOverride: function (oldWindowRect, newWindowRect) {
		this.renderSeries(false);
	}
	,
	_previousFrame: null,
	_transitionFrame: null,
	_currentFrame: null,
	calculateCachedPoints: function (view, frame, count, windowRect, viewportRect) {
		var $self = this;
		frame.cachedPoints(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 1, count));
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var itemsSource = this.fastItemsSource();
		var x;
		var y;
		var xParams = (function () {
			var $ret = new $.ig.ScalerParams(0, windowRect, viewportRect, $self.axisInfoCache().xAxisIsInverted(), effectiveViewportRect);
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		var yParams = (function () {
			var $ret = new $.ig.ScalerParams(0, windowRect, viewportRect, $self.axisInfoCache().yAxisIsInverted(), effectiveViewportRect);
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		for (var i = 0; i < count; i++) {
			x = this.xColumn().item(i);
			y = this.yColumn().item(i);
			var point = { __x: this.axisInfoCache().xAxis().getScaledValue(x, xParams), __y: this.axisInfoCache().yAxis().getScaledValue(y, yParams), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			if (!Number.isInfinity(point.__x) && !Number.isInfinity(point.__y)) {
				var item = itemsSource.item(i);
				if (!frame.cachedPoints().containsKey(item)) {
					var columnValues = { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
					var p = { __x: point.__x, __y: point.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
					frame.cachedPoints().add(item, (function () {
						var $ret = new $.ig.OwnedPoint();
						$ret.ownerItem(item);
						$ret.columnValues(columnValues);
						$ret.point(p);
						return $ret;
					}()));
				}
			}
		}
	}
	,
	prepareFrame: function (frame, view) {
		var $self = this;
		frame.markers().clear();
		frame.trendLine().clear();
		frame.horizontalErrorBars().clear();
		frame.verticalErrorBars().clear();
		frame.horizontalErrorBarWidths().clear();
		frame.verticalErrorBarHeights().clear();
		var count = Math.min(this.xColumn() != null ? this.xColumn().count() : 0, this.yColumn() != null ? this.yColumn().count() : 0);
		var windowRect = view.windowRect();
		var viewportRect = view.viewport();
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var xParams = new $.ig.ScalerParams(0, windowRect, viewportRect, this.xAxis().isInverted(), effectiveViewportRect);
		xParams._effectiveViewportRect = this.getEffectiveViewport1(view);
		var yParams = new $.ig.ScalerParams(0, windowRect, viewportRect, this.yAxis().isInverted(), effectiveViewportRect);
		yParams._effectiveViewportRect = this.getEffectiveViewport1(view);
		if (count < 1) {
			return;
		}
		this.axisInfoCache((function () {
			var $ret = new $.ig.ScatterAxisInfoCache();
			$ret.xAxis($self.xAxis());
			$ret.yAxis($self.yAxis());
			$ret.xAxisIsInverted($self.xAxis().isInverted());
			$ret.yAxisIsInverted($self.yAxis().isInverted());
			$ret.fastItemsSource($self.fastItemsSource());
			return $ret;
		}()));
		var scatterView = view;
		if (count <= this.maximumMarkers()) {
			this.calculateCachedPoints(scatterView, frame, count, windowRect, viewportRect);
		}
		if (this.shouldDisplayMarkers()) {
			view.markerManager().winnowMarkers(frame.markers(), this.maximumMarkers(), windowRect, viewportRect, this.resolution());
		}
		var clipper = (function () {
			var $ret = new $.ig.Clipper(0, viewportRect, false);
			$ret.target(frame.trendLine());
			return $ret;
		}());
		var contentViewport = this.getContentViewport(view);
		var xmin = this.xAxis().getUnscaledValue(contentViewport.left(), xParams);
		var xmax = this.xAxis().getUnscaledValue(contentViewport.right(), xParams);
		view.trendLineManager().prepareLine(frame.trendLine(), this.trendLineType(), this.xColumn(), this.yColumn(), this.trendLinePeriod(), function (x) { return $self.xAxis().getScaledValue(x, xParams); }, function (y) { return $self.yAxis().getScaledValue(y, yParams); }, (function () {
			var $ret = new $.ig.TrendResolutionParams();
			$ret.resolution($self.resolution());
			$ret.viewport(viewportRect);
			$ret.window(windowRect);
			return $ret;
		}()), clipper, xmin, xmax);
		this.prepareErrorBars(frame, view);
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.MarkerSeries.prototype.clearRendering.call(this, wipeClean, view);
		var scatterView = view;
		scatterView.clearRendering(wipeClean);
	}
	,
	renderFrame: function (frame, view) {
		var $self = this;
		var viewportRect = view.viewport();
		this.axisInfoCache((function () {
			var $ret = new $.ig.ScatterAxisInfoCache();
			$ret.xAxis($self.xAxis());
			$ret.yAxis($self.yAxis());
			$ret.xAxisIsInverted($self.xAxis().isInverted());
			$ret.yAxisIsInverted($self.yAxis().isInverted());
			return $ret;
		}()));
		if (this.shouldDisplayMarkers()) {
			view.markerManager().render(frame.markers(), this.useLightweightMarkers());
		}
		view.renderMarkers();
		var clipper = (function () {
			var $ret = new $.ig.Clipper(1, NaN, viewportRect.bottom(), NaN, viewportRect.top(), false);
			$ret.target(view.trendLineManager().trendPolyline().points());
			return $ret;
		}());
		view.trendLineManager().rasterizeTrendLine1(frame.trendLine(), clipper);
		this.renderErrorBars(frame, view);
	}
	,
	prepareErrorBars: function (frame, view) {
		var errorBarsHelper = new $.ig.ErrorBarsHelper(this, view);
		if (this.errorBarSettings() == null) {
			return;
		}
		var horizontalCalculator = this.errorBarSettings().horizontalCalculator();
		var verticalCalculator = this.errorBarSettings().verticalCalculator();
		var errorBarPositionX = 0;
		var errorBarPositionY = 0;
		var errorBarPositiveSize = 0;
		var errorBarNegativeSize = 0;
		var en = frame.markers().keys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			var point = frame.markers().item(key);
			if (horizontalCalculator != null) {
				switch (horizontalCalculator.getCalculatorType()) {
					case $.ig.ErrorBarCalculatorType.prototype.percentage:
						var $ret = this.preparePercentageErrorBarSize(point, errorBarsHelper, horizontalCalculator, errorBarPositiveSize, errorBarNegativeSize, true);
						errorBarPositiveSize = $ret.p3;
						errorBarNegativeSize = $ret.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.fixed:
						var $ret1 = this.prepareFixedErrorBarSize(point, errorBarsHelper, horizontalCalculator, errorBarPositiveSize, errorBarNegativeSize, true);
						errorBarPositiveSize = $ret1.p3;
						errorBarNegativeSize = $ret1.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.data:
						var $ret2 = this.prepareDataErrorBarSize(point, errorBarsHelper, horizontalCalculator, key, errorBarPositiveSize, errorBarNegativeSize, true);
						errorBarPositiveSize = $ret2.p4;
						errorBarNegativeSize = $ret2.p5;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.standardDeviation:
						var $ret3 = this.prepareStandardDevErrorBarSize(errorBarsHelper, horizontalCalculator, errorBarPositionX, errorBarPositiveSize, errorBarNegativeSize, true);
						errorBarPositionX = $ret3.p2;
						errorBarPositiveSize = $ret3.p3;
						errorBarNegativeSize = $ret3.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.standardError:
						var $ret4 = this.prepareFixedErrorBarSize(point, errorBarsHelper, horizontalCalculator, errorBarPositiveSize, errorBarNegativeSize, true);
						errorBarPositiveSize = $ret4.p3;
						errorBarNegativeSize = $ret4.p4;
						break;
				}
				;
				var p = new $.ig.OwnedPoint();
				p.point(errorBarsHelper.calculateErrorBarCenterHorizontal(horizontalCalculator, this.axisInfoCache().xAxis(), point.point(), errorBarPositionX));
				;
				p.ownerItem(point.ownerItem());
				frame.horizontalErrorBars().add(key, p);
				frame.horizontalErrorBarWidths().add(key, [ errorBarPositiveSize, errorBarNegativeSize ]);
			}
			if (verticalCalculator != null) {
				switch (verticalCalculator.getCalculatorType()) {
					case $.ig.ErrorBarCalculatorType.prototype.percentage:
						var $ret5 = this.preparePercentageErrorBarSize(point, errorBarsHelper, verticalCalculator, errorBarPositiveSize, errorBarNegativeSize, false);
						errorBarPositiveSize = $ret5.p3;
						errorBarNegativeSize = $ret5.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.fixed:
						var $ret6 = this.prepareFixedErrorBarSize(point, errorBarsHelper, verticalCalculator, errorBarPositiveSize, errorBarNegativeSize, false);
						errorBarPositiveSize = $ret6.p3;
						errorBarNegativeSize = $ret6.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.data:
						var $ret7 = this.prepareDataErrorBarSize(point, errorBarsHelper, verticalCalculator, key, errorBarPositiveSize, errorBarNegativeSize, false);
						errorBarPositiveSize = $ret7.p4;
						errorBarNegativeSize = $ret7.p5;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.standardDeviation:
						var $ret8 = this.prepareStandardDevErrorBarSize(errorBarsHelper, verticalCalculator, errorBarPositionY, errorBarPositiveSize, errorBarNegativeSize, false);
						errorBarPositionY = $ret8.p2;
						errorBarPositiveSize = $ret8.p3;
						errorBarNegativeSize = $ret8.p4;
						break;
					case $.ig.ErrorBarCalculatorType.prototype.standardError:
						var $ret9 = this.prepareFixedErrorBarSize(point, errorBarsHelper, verticalCalculator, errorBarPositiveSize, errorBarNegativeSize, false);
						errorBarPositiveSize = $ret9.p3;
						errorBarNegativeSize = $ret9.p4;
						break;
				}
				;
				var p1 = new $.ig.OwnedPoint();
				p1.point(errorBarsHelper.calculateErrorBarCenterVertical(verticalCalculator, this.axisInfoCache().yAxis(), point.point(), errorBarPositionY));
				;
				p1.ownerItem(point.ownerItem());
				frame.verticalErrorBars().add(key, p1);
				frame.verticalErrorBarHeights().add(key, [ errorBarPositiveSize, errorBarNegativeSize ]);
			}
		}
	}
	,
	preparePercentageErrorBarSize: function (point, helper, calculator, positiveSize, negativeSize, horizontal) {
		if (horizontal) {
			var value;
			var refAxis, targetAxis;
			targetAxis = this.axisInfoCache().xAxis();
			if (this.errorBarSettings().horizontalCalculatorReference() == $.ig.ErrorBarCalculatorReference.prototype.x) {
				value = point.point().__x;
				refAxis = this.axisInfoCache().xAxis();
			} else {
				value = point.point().__y;
				refAxis = this.axisInfoCache().yAxis();
			}
			var $ret = helper.calculateDependentErrorBarSize(value, calculator, refAxis, targetAxis, positiveSize, negativeSize);
			positiveSize = $ret.p4;
			negativeSize = $ret.p5;
		} else {
			var value1;
			var refAxis1, targetAxis1;
			targetAxis1 = this.axisInfoCache().yAxis();
			if (this.errorBarSettings().verticalCalculatorReference() == $.ig.ErrorBarCalculatorReference.prototype.x) {
				value1 = point.point().__x;
				refAxis1 = this.axisInfoCache().xAxis();
			} else {
				value1 = point.point().__y;
				refAxis1 = this.axisInfoCache().yAxis();
			}
			var $ret1 = helper.calculateDependentErrorBarSize(value1, calculator, refAxis1, targetAxis1, positiveSize, negativeSize);
			positiveSize = $ret1.p4;
			negativeSize = $ret1.p5;
		}
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	prepareFixedErrorBarSize: function (point, helper, calculator, positiveSize, negativeSize, horizontal) {
		if (horizontal) {
			var $ret = helper.calculateIndependentErrorBarSize(point.point().__x, calculator, this.axisInfoCache().xAxis(), positiveSize, negativeSize);
			positiveSize = $ret.p3;
			negativeSize = $ret.p4;
		} else {
			var $ret1 = helper.calculateIndependentErrorBarSize(point.point().__y, calculator, this.axisInfoCache().yAxis(), positiveSize, negativeSize);
			positiveSize = $ret1.p3;
			negativeSize = $ret1.p4;
		}
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	prepareDataErrorBarSize: function (point, helper, calculator, key, positiveSize, negativeSize, horizontal) {
		var errorColumn = calculator.getItemColumn();
		var index = this.fastItemsSource().indexOf(key);
		if (errorColumn != null && index < errorColumn.count()) {
			var unscaledValue = errorColumn.item(index);
			if (horizontal) {
				var $ret = helper.calculateFromUnscaledErrorBarValue(point.point().__x, unscaledValue, this.axisInfoCache().xAxis(), positiveSize, negativeSize);
				positiveSize = $ret.p3;
				negativeSize = $ret.p4;
			} else {
				var $ret1 = helper.calculateFromUnscaledErrorBarValue(point.point().__y, unscaledValue, this.axisInfoCache().yAxis(), positiveSize, negativeSize);
				positiveSize = $ret1.p3;
				negativeSize = $ret1.p4;
			}
		} else {
			positiveSize = negativeSize = NaN;
		}
		return {
			p4: positiveSize,
			p5: negativeSize
		};
	}
	,
	prepareStandardDevErrorBarSize: function (helper, calculator, position, positiveSize, negativeSize, horizontal) {
		var $ret = helper.calculateIndependentErrorBarPosition(calculator, position);
		position = $ret.p1;
		if (horizontal) {
			var $ret1 = helper.calculateStdDevErrorBarSize(position, calculator, this.axisInfoCache().xAxis(), positiveSize, negativeSize);
			positiveSize = $ret1.p3;
			negativeSize = $ret1.p4;
		} else {
			var $ret2 = helper.calculateStdDevErrorBarSize(position, calculator, this.axisInfoCache().yAxis(), positiveSize, negativeSize);
			positiveSize = $ret2.p3;
			negativeSize = $ret2.p4;
		}
		return {
			p2: position,
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	renderErrorBars: function (frame, view) {
		if (!view.hasSurface() || this.errorBarSettings() == null) {
			view.hideErrorBars();
			return;
		}
		this.renderErrorBarsHorizontal(frame, view);
		this.renderErrorBarsVertical(frame, view);
	}
	,
	renderErrorBarsHorizontal: function (frame, view) {
		view.attachHorizontalErrorBars();
		var errorBarsHelper = new $.ig.ErrorBarsHelper(this, view);
		var horizontalErrorBarsGeometry = new $.ig.PathGeometry();
		var horizontalCalculator = this.errorBarSettings().horizontalCalculator();
		var en = frame.markers().keys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (horizontalCalculator != null && frame.horizontalErrorBarWidths().containsKey(key)) {
				var errorBarPositiveSize = frame.horizontalErrorBarWidths().item(key)[0];
				var errorBarNegativeSize = frame.horizontalErrorBarWidths().item(key)[1];
				if (!$.ig.util.isNaN(errorBarPositiveSize) && !$.ig.util.isNaN(errorBarNegativeSize)) {
					var centerHorizontal = frame.horizontalErrorBars().item(key).point();
					if (this.errorBarSettings().enableErrorBarsHorizontal() == $.ig.EnableErrorBars.prototype.both || this.errorBarSettings().enableErrorBarsHorizontal() == $.ig.EnableErrorBars.prototype.positive) {
						errorBarsHelper.addErrorBarHorizontal(horizontalErrorBarsGeometry, centerHorizontal, errorBarPositiveSize, true);
					}
					if (this.errorBarSettings().enableErrorBarsHorizontal() == $.ig.EnableErrorBars.prototype.both || this.errorBarSettings().enableErrorBarsHorizontal() == $.ig.EnableErrorBars.prototype.negative) {
						errorBarsHelper.addErrorBarHorizontal(horizontalErrorBarsGeometry, centerHorizontal, errorBarNegativeSize, false);
					}
				}
			}
		}
		view.provideHorizontalErrorBarGeometry(horizontalErrorBarsGeometry);
	}
	,
	renderErrorBarsVertical: function (frame, view) {
		view.attachVerticalErrorBars();
		var errorBarsHelper = new $.ig.ErrorBarsHelper(this, view);
		var verticalErrorBarsGeometry = new $.ig.PathGeometry();
		var verticalCalculator = this.errorBarSettings().verticalCalculator();
		var en = frame.markers().keys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (verticalCalculator != null && frame.verticalErrorBarHeights().containsKey(key)) {
				var errorBarPositiveSize = frame.verticalErrorBarHeights().item(key)[0];
				var errorBarNegativeSize = frame.verticalErrorBarHeights().item(key)[1];
				if (!$.ig.util.isNaN(errorBarPositiveSize) && !$.ig.util.isNaN(errorBarNegativeSize)) {
					var centerVertical = frame.verticalErrorBars().item(key).point();
					if (this.errorBarSettings().enableErrorBarsVertical() == $.ig.EnableErrorBars.prototype.both || this.errorBarSettings().enableErrorBarsVertical() == $.ig.EnableErrorBars.prototype.positive) {
						errorBarsHelper.addErrorBarVertical(verticalErrorBarsGeometry, centerVertical, errorBarPositiveSize, true);
					}
					if (this.errorBarSettings().enableErrorBarsVertical() == $.ig.EnableErrorBars.prototype.both || this.errorBarSettings().enableErrorBarsVertical() == $.ig.EnableErrorBars.prototype.negative) {
						errorBarsHelper.addErrorBarVertical(verticalErrorBarsGeometry, centerVertical, errorBarNegativeSize, false);
					}
				}
			}
		}
	}
	,
	validateSeries: function (viewportRect, windowRect, view) {
		var isValid = true;
		if (!$.ig.MarkerSeries.prototype.validateSeries.call(this, viewportRect, windowRect, view) || windowRect.isEmpty() || viewportRect.isEmpty() || this.xAxis() == null || this.yAxis() == null || this.xAxis().seriesViewer() == null || this.yAxis().seriesViewer() == null || this.xColumn() == null || this.yColumn() == null || this.xColumn().count() == 0 || this.yColumn().count() == 0 || this.fastItemsSource() == null || this.fastItemsSource().count() != this.xColumn().count() || this.fastItemsSource().count() != this.yColumn().count() || this.xAxis().seriesViewer() == null || this.yAxis().seriesViewer() == null || this.xAxis().actualMinimumValue() == this.xAxis().actualMaximumValue() || this.yAxis().actualMinimumValue() == this.yAxis().actualMaximumValue()) {
			isValid = false;
		}
		return isValid;
	}
	,
	getViewInfo: function (viewportRect, windowRect) {
		if (!this.__cachedViewportRect.isEmpty() && !this.__cachedWindowRect.isEmpty()) {
			viewportRect = this.__cachedViewportRect;
			windowRect = this.__cachedWindowRect;
			return {
				p0: viewportRect,
				p1: windowRect
			};
		}
		viewportRect = this.view().viewport();
		windowRect = this.view().windowRect();
		return {
			p0: viewportRect,
			p1: windowRect
		};
	}
	,
	renderSeriesOverride: function (animate) {
		this.cacheViewInfo();
		try {
			if (this.clearAndAbortIfInvalid1(this.view())) {
				return;
			}
			if (this.fastItemsSource() != null && this.fastItemsSource().count() > this.maximumMarkers()) {
				animate = false;
			}
			if (this.shouldAnimate(animate) && !this.skipPrepare()) {
				var previous = this._previousFrame;
				if (this.animationActive()) {
					if (this.animator().needsFlush()) {
						this.animator().flush();
					}
					this._previousFrame = this._transitionFrame;
					this._transitionFrame = previous;
				} else {
					this._previousFrame = this._currentFrame;
					this._currentFrame = previous;
				}
				this.prepareFrame(this._currentFrame, this.scatterView());
				this.startAnimation();
			} else {
				if (!this.skipPrepare()) {
					this.prepareFrame(this._currentFrame, this.scatterView());
				}
				this.renderFrame(this._currentFrame, this.scatterView());
			}
		}
		finally {
			this.unCacheViewInfo();
		}
	}
	,
	updateIndexedProperties: function () {
		$.ig.MarkerSeries.prototype.updateIndexedProperties.call(this);
		if (this.index() < 0) {
			return;
		}
		this.scatterView().updateTrendlineBrush();
	}
	,
	_thumbnailFrame: null,
	thumbnailFrame: function (value) {
		if (arguments.length === 1) {
			this._thumbnailFrame = value;
			return value;
		} else {
			return this._thumbnailFrame;
		}
	}
	,
	renderThumbnail: function (viewportRect, surface) {
		$.ig.MarkerSeries.prototype.renderThumbnail.call(this, viewportRect, surface);
		if (!this.thumbnailDirty()) {
			this.view().prepSurface(surface);
			return;
		}
		this.view().prepSurface(surface);
		if (this.clearAndAbortIfInvalid1(this.thumbnailView())) {
			return;
		}
		var thumbnailView = $.ig.util.cast($.ig.ScatterBaseView.prototype.$type, this.thumbnailView());
		if (!this.skipThumbnailPrepare()) {
			this.thumbnailFrame(new $.ig.ScatterFrame());
			this.prepareFrame(this.thumbnailFrame(), thumbnailView);
		}
		this.skipThumbnailPrepare(false);
		this.renderFrame(this.thumbnailFrame(), thumbnailView);
		this.thumbnailDirty(false);
	}
	,
	_alternateFrame: null,
	alternateFrame: function (value) {
		if (arguments.length === 1) {
			this._alternateFrame = value;
			return value;
		} else {
			return this._alternateFrame;
		}
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.MarkerSeries.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		var scatterSeriesView = view;
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(scatterSeriesView)) {
			return;
		}
		if (this.alternateFrame() == null) {
			this.alternateFrame(new $.ig.ScatterFrame());
		}
		this.prepareFrame(this.alternateFrame(), scatterSeriesView);
		this.renderFrame(this.alternateFrame(), scatterSeriesView);
	}
	,
	removeUnusedMarkers: function (list, markers) {
		var remove = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		var en = markers.activeKeys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (!list.containsKey(key)) {
				remove.add1(key);
			}
		}
		var en1 = remove.getEnumerator();
		while (en1.moveNext()) {
			var key1 = en1.current();
			markers.remove(key1);
		}
	}
	,
	getMarkerLocations: function (view, markers, locations, windowRect, viewportRect) {
		var $self = this;
		if (locations == null || locations.length != this.axisInfoCache().fastItemsSource().count()) {
			locations = new Array(this.axisInfoCache().fastItemsSource().count());
			for (var i = 0; i < this.axisInfoCache().fastItemsSource().count(); i++) {
				locations[i] = new $.ig.Point(0);
			}
		}
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var xParams = (function () {
			var $ret = new $.ig.ScalerParams(0, windowRect, viewportRect, $self.xAxis().isInverted(), effectiveViewportRect);
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		var yParams = (function () {
			var $ret = new $.ig.ScalerParams(0, windowRect, viewportRect, $self.yAxis().isInverted(), effectiveViewportRect);
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		var contentViewport = this.getContentViewport(view);
		var minX = this.axisInfoCache().xAxis().getUnscaledValue(contentViewport.left(), xParams);
		var maxX = this.axisInfoCache().xAxis().getUnscaledValue(contentViewport.right(), xParams);
		var minY = this.axisInfoCache().yAxis().getUnscaledValue(contentViewport.bottom(), yParams);
		var maxY = this.axisInfoCache().yAxis().getUnscaledValue(contentViewport.top(), yParams);
		if (this.axisInfoCache().xAxisIsInverted()) {
			var swap = minX;
			minX = maxX;
			maxX = swap;
		}
		if (this.axisInfoCache().yAxisIsInverted()) {
			var swap1 = minY;
			minY = maxY;
			maxY = swap1;
		}
		var cache = this.axisInfoCache();
		var xAxis = cache.xAxis();
		var yAxis = cache.yAxis();
		var x;
		var y;
		var xColumn = this.xColumn();
		var yColumn = this.yColumn();
		for (var i1 = 0; i1 < this.axisInfoCache().fastItemsSource().count(); i1++) {
			x = xColumn.item(i1);
			y = yColumn.item(i1);
			if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
				locations[i1].__x = xAxis.getScaledValue(x, xParams);
				locations[i1].__y = yAxis.getScaledValue(y, yParams);
			} else {
				locations[i1].__x = NaN;
				locations[i1].__y = NaN;
			}
		}
		return locations;
	}
	,
	getActiveIndexes: function (markers, indexes) {
		if (indexes == null || indexes.length != markers.activeCount()) {
			indexes = new Array(markers.activeCount());
		}
		var i = 0;
		var source = this.fastItemsSource();
		var en = markers.activeKeys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			indexes[i] = source.indexOf(key);
			i++;
		}
		return indexes;
	}
	,
	exportVisualDataOverride: function (svd) {
		$.ig.MarkerSeries.prototype.exportVisualDataOverride.call(this, svd);
		var trendShape = new $.ig.PolyLineVisualData(1, "trendLine", this.scatterView().trendLineManager().trendPolyline());
		trendShape.tags().add("Trend");
		svd.shapes().add(trendShape);
	}
	,
	$type: new $.ig.Type('ScatterBase', $.ig.MarkerSeries.prototype.$type, [$.ig.ISupportsErrorBars.prototype.$type])
}, true);

$.ig.util.defType('SizeScale', 'DependencyObject', {
	init: function () {
		var $self = this;
		$.ig.DependencyObject.prototype.init.call(this);
		this.series(new $.ig.List$1($.ig.Series.prototype.$type, 0));
		this.propertyUpdated = $.ig.Delegate.prototype.combine(this.propertyUpdated, function (o, e) { $self.propertyUpdatedOverride(o, e.propertyName(), e.oldValue(), e.newValue()); });
	},
	_series: null,
	series: function (value) {
		if (arguments.length === 1) {
			this._series = value;
			return value;
		} else {
			return this._series;
		}
	}
	,
	minimumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.SizeScale.prototype.minimumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.SizeScale.prototype.minimumValueProperty);
		}
	}
	,
	maximumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.SizeScale.prototype.maximumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.SizeScale.prototype.maximumValueProperty);
		}
	}
	,
	isLogarithmic: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.SizeScale.prototype.isLogarithmicProperty, value);
			return value;
		} else {
			return this.getValue($.ig.SizeScale.prototype.isLogarithmicProperty);
		}
	}
	,
	logarithmBase: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.SizeScale.prototype.logarithmBaseProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.SizeScale.prototype.logarithmBaseProperty));
		}
	}
	,
	propertyChanged: null,
	propertyUpdated: null,
	raisePropertyChanged: function (name, oldValue, newValue) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(name));
		}
		if (this.propertyUpdated != null) {
			this.propertyUpdated(this, new $.ig.PropertyUpdatedEventArgs(name, oldValue, newValue));
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		var en = this.series().getEnumerator();
		while (en.moveNext()) {
			var series = en.current();
			series.renderSeries(false);
			if (series.seriesViewer() != null) {
				series.notifyThumbnailAppearanceChanged();
				series.notifySizeScalesDirty();
			}
		}
	}
	,
	$type: new $.ig.Type('SizeScale', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('BrushScale', 'DependencyObject', {
	init: function () {
		var $self = this;
		this.__brushes = null;
		$.ig.DependencyObject.prototype.init.call(this);
		this.series(new $.ig.List$1($.ig.Series.prototype.$type, 0));
		this.brushes(new $.ig.BrushCollection());
		var $t = this.brushes();
		$t.collectionChanged = $.ig.Delegate.prototype.combine($t.collectionChanged, this.brushes_CollectionChanged.runOn(this));
		this.propertyUpdated = $.ig.Delegate.prototype.combine(this.propertyUpdated, function (o, e) { $self.propertyUpdatedOverride(o, e.propertyName(), e.oldValue(), e.newValue()); });
	},
	brushes_CollectionChanged: function (sender, e) {
		var en = this.series().getEnumerator();
		while (en.moveNext()) {
			var series = en.current();
			series.renderSeries(false);
			series.notifyBrushScalesDirty();
			series.notifyThumbnailAppearanceChanged();
		}
	}
	,
	brushes: function (value) {
		if (arguments.length === 1) {
			if (this.__brushes != null) {
				var $t = this.__brushes;
				$t.collectionChanged = $.ig.Delegate.prototype.remove($t.collectionChanged, this.brushes_CollectionChanged.runOn(this));
			}
			this.__brushes = value;
			if (this.__brushes != null) {
				var $t1 = this.__brushes;
				$t1.collectionChanged = $.ig.Delegate.prototype.combine($t1.collectionChanged, this.brushes_CollectionChanged.runOn(this));
			}
			var en = this.series().getEnumerator();
			while (en.moveNext()) {
				var series = en.current();
				series.renderSeries(false);
				series.notifyBrushScalesDirty();
				series.notifyThumbnailAppearanceChanged();
			}
			return value;
		} else {
			return this.__brushes;
		}
	}
	,
	__brushes: null,
	_series: null,
	series: function (value) {
		if (arguments.length === 1) {
			this._series = value;
			return value;
		} else {
			return this._series;
		}
	}
	,
	registerSeries: function (series) {
		var present = this.series().contains(series);
		if (!present) {
			this.series().add(series);
		}
	}
	,
	unregisterSeries: function (series) {
		var present = this.series().contains(series);
		if (present) {
			this.series().remove(series);
		}
	}
	,
	getBrush: function (index) {
		if (this.brushes() == null || index < 0 || index >= this.brushes().count()) {
			return null;
		}
		return this.brushes().item(index);
	}
	,
	isReady: function () {
		return true;
	}
	,
	getInterpolatedBrush: function (index) {
		if (this.brushes() == null || this.brushes().count() == 0 || index < 0) {
			return null;
		}
		return this.brushes().getInterpolatedBrush(index);
	}
	,
	propertyChanged: null,
	propertyUpdated: null,
	raisePropertyChanged: function (name, oldValue, newValue) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(name));
		}
		if (this.propertyUpdated != null) {
			this.propertyUpdated(this, new $.ig.PropertyUpdatedEventArgs(name, oldValue, newValue));
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		var en = this.series().getEnumerator();
		while (en.moveNext()) {
			var series = en.current();
			series.renderSeries(false);
			series.notifyBrushScalesDirty();
			series.notifyThumbnailAppearanceChanged();
		}
	}
	,
	$type: new $.ig.Type('BrushScale', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('BubbleSeries', 'ScatterBase', {
	createView: function () {
		return new $.ig.BubbleSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.ScatterBase.prototype.onViewCreated.call(this, view);
		this.bubbleView(view);
	}
	,
	_bubbleView: null,
	bubbleView: function (value) {
		if (arguments.length === 1) {
			this._bubbleView = value;
			return value;
		} else {
			return this._bubbleView;
		}
	}
	,
	init: function () {
		$.ig.ScatterBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.BubbleSeries.prototype.$type);
		this._previousFrame = new $.ig.ScatterFrame();
		this._transitionFrame = new $.ig.ScatterFrame();
		this._currentFrame = new $.ig.ScatterFrame();
	},
	_operatingWindowRect: null,
	operatingWindowRect: function (value) {
		if (arguments.length === 1) {
			this._operatingWindowRect = value;
			return value;
		} else {
			return this._operatingWindowRect;
		}
	}
	,
	_operatingViewportRect: null,
	operatingViewportRect: function (value) {
		if (arguments.length === 1) {
			this._operatingViewportRect = value;
			return value;
		} else {
			return this._operatingViewportRect;
		}
	}
	,
	internalRadiusColumn: function () {
		return this.radiusColumn();
	}
	,
	_legendItems: null,
	legendItems: function (value) {
		if (arguments.length === 1) {
			this._legendItems = value;
			return value;
		} else {
			return this._legendItems;
		}
	}
	,
	radiusMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BubbleSeries.prototype.radiusMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BubbleSeries.prototype.radiusMemberPathProperty);
		}
	}
	,
	radiusColumn: function (value) {
		if (arguments.length === 1) {
			if (this.__radiusColumn != value) {
				var oldZColumn = this.radiusColumn();
				this.__radiusColumn = value;
				this.raisePropertyChanged($.ig.BubbleSeries.prototype.radiusColumnPropertyName, oldZColumn, this.radiusColumn());
			}
			return value;
		} else {
			return this.__radiusColumn;
		}
	}
	,
	__radiusColumn: null,
	radiusScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BubbleSeries.prototype.radiusScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BubbleSeries.prototype.radiusScaleProperty);
		}
	}
	,
	labelMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BubbleSeries.prototype.labelMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BubbleSeries.prototype.labelMemberPathProperty);
		}
	}
	,
	__labelColumn: null,
	labelColumn: function (value) {
		if (arguments.length === 1) {
			if (this.__labelColumn != value) {
				var oldColumn = this.labelColumn();
				this.__labelColumn = value;
				this.raisePropertyChanged($.ig.BubbleSeries.prototype.labelColumnPropertyName, oldColumn, this.labelColumn());
			}
			return value;
		} else {
			return this.__labelColumn;
		}
	}
	,
	fillMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BubbleSeries.prototype.fillMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BubbleSeries.prototype.fillMemberPathProperty);
		}
	}
	,
	fillScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BubbleSeries.prototype.fillScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BubbleSeries.prototype.fillScaleProperty);
		}
	}
	,
	__fillColumn: null,
	fillColumn: function (value) {
		if (arguments.length === 1) {
			if (this.__fillColumn != value) {
				var oldZColumn = this.fillColumn();
				this.__fillColumn = value;
				this.raisePropertyChanged($.ig.BubbleSeries.prototype.fillColumnPropertyName, oldZColumn, this.fillColumn());
			}
			return value;
		} else {
			return this.__fillColumn;
		}
	}
	,
	calculateCachedPoints: function (view, frame, count, windowRect, viewportRect) {
		if (count <= this.maximumMarkers()) {
			frame.cachedPoints(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 1, count));
		}
		var maximumMarkers = this.maximumMarkers();
		var itemsSource = this.fastItemsSource();
		var x;
		var y;
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var px = new $.ig.ScalerParams(0, windowRect, viewportRect, this.axisInfoCache().xAxisIsInverted(), effectiveViewportRect);
		var py = new $.ig.ScalerParams(0, windowRect, viewportRect, this.axisInfoCache().yAxisIsInverted(), effectiveViewportRect);
		for (var i = 0; i < count; i++) {
			x = this.xColumn().item(i);
			y = this.yColumn().item(i);
			var point = { __x: this.axisInfoCache().xAxis().getScaledValue(x, px), __y: this.axisInfoCache().yAxis().getScaledValue(y, py), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			if (!Number.isInfinity(point.__x) && !Number.isInfinity(point.__y)) {
				var item = itemsSource.item(i);
				if (count <= maximumMarkers) {
					if (!frame.cachedPoints().containsKey(item)) {
						var columnValues = { __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
						frame.cachedPoints().add(item, (function () {
							var $ret = new $.ig.OwnedPoint();
							$ret.ownerItem(item);
							$ret.columnValues(columnValues);
							$ret.point(point);
							return $ret;
						}()));
					}
				}
			}
		}
	}
	,
	renderFrame: function (frame, view) {
		var $self = this;
		this.axisInfoCache((function () {
			var $ret = new $.ig.ScatterAxisInfoCache();
			$ret.xAxis($self.xAxis());
			$ret.yAxis($self.yAxis());
			$ret.xAxisIsInverted($self.xAxis().isInverted());
			$ret.yAxisIsInverted($self.yAxis().isInverted());
			return $ret;
		}()));
		var bubbleView = $.ig.util.cast($.ig.BubbleSeriesView.prototype.$type, view);
		bubbleView.markerManager().render(frame.markers(), this.useLightweightMarkers());
		view.renderMarkers();
		var clipper = (function () {
			var $ret = new $.ig.Clipper(1, NaN, view.viewport().bottom(), NaN, view.viewport().top(), false);
			$ret.target(view.trendLineManager().trendPolyline().points());
			return $ret;
		}());
		view.trendLineManager().rasterizeTrendLine1(frame.trendLine(), clipper);
	}
	,
	prepareFrame: function (frame, view) {
		var $self = this;
		frame.markers().clear();
		frame.trendLine().clear();
		var bubbleView = $.ig.util.cast($.ig.BubbleSeriesView.prototype.$type, view);
		var bubbleManager = bubbleView.markerManager();
		bubbleManager.radiusColumn(this.radiusColumn());
		var count = Math.min(this.xColumn() != null ? this.xColumn().count() : 0, this.yColumn() != null ? this.yColumn().count() : 0);
		var windowRect = view.windowRect();
		var viewportRect = view.viewport();
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var contentViewport = this.getContentViewport(view);
		var xParams = new $.ig.ScalerParams(0, windowRect, viewportRect, this.xAxis().isInverted(), effectiveViewportRect);
		var yParams = new $.ig.ScalerParams(0, windowRect, viewportRect, this.yAxis().isInverted(), effectiveViewportRect);
		if (count < 1) {
			return;
		}
		this.axisInfoCache((function () {
			var $ret = new $.ig.ScatterAxisInfoCache();
			$ret.xAxis($self.xAxis());
			$ret.yAxis($self.yAxis());
			$ret.xAxisIsInverted($self.xAxis().isInverted());
			$ret.yAxisIsInverted($self.yAxis().isInverted());
			$ret.fastItemsSource($self.fastItemsSource());
			return $ret;
		}()));
		this.calculateCachedPoints(bubbleView, frame, count, windowRect, viewportRect);
		bubbleView.markerManager().winnowMarkers(frame.markers(), this.maximumMarkers(), windowRect, viewportRect, this.resolution());
		bubbleView.createMarkerSizes();
		bubbleView.setMarkerColors();
		var clipper = (function () {
			var $ret = new $.ig.Clipper(0, viewportRect, false);
			$ret.target(frame.trendLine());
			return $ret;
		}());
		var xmin = this.xAxis().getUnscaledValue(contentViewport.left(), xParams);
		var xmax = this.xAxis().getUnscaledValue(contentViewport.right(), xParams);
		bubbleView.trendLineManager().prepareLine(frame.trendLine(), this.trendLineType(), this.xColumn(), this.yColumn(), this.trendLinePeriod(), function (x) { return $self.xAxis().getScaledValue(x, xParams); }, function (y) { return $self.yAxis().getScaledValue(y, yParams); }, (function () {
			var $ret = new $.ig.TrendResolutionParams();
			$ret.resolution($self.resolution());
			$ret.viewport(viewportRect);
			$ret.window(windowRect);
			return $ret;
		}()), clipper, xmin, xmax);
	}
	,
	drawLegend: function () {
		if (this.seriesViewer() == null) {
			return;
		}
		var itemLegend = $.ig.util.cast($.ig.ItemLegend.prototype.$type, this.actualLegend());
		if (itemLegend != null) {
			itemLegend.clearLegendItems(this);
			this.createLegendItems();
			itemLegend.renderLegend(this);
		}
		var scaleLegend = $.ig.util.cast($.ig.ScaleLegend.prototype.$type, this.actualLegend());
		if (scaleLegend != null) {
			scaleLegend.restoreOriginalState();
			scaleLegend.initializeLegend(this);
		}
	}
	,
	getLinearSize: function (min, max, smallSize, largeSize, value) {
		if (value <= min || $.ig.util.isNaN(value) || Number.isInfinity(value)) {
			return smallSize;
		}
		if (value >= max) {
			return largeSize;
		}
		var result = smallSize + ((largeSize - smallSize) / (max - min)) * (value - min);
		return result;
	}
	,
	getLogarithmicSize: function (min, max, smallSize, largeSize, logBase, value) {
		var newValue = Math.logBase(value, logBase);
		var newMin = Math.logBase(min, logBase);
		var newMax = Math.logBase(max, logBase);
		return $.ig.BubbleSeries.prototype.getLinearSize(newMin, newMax, smallSize, largeSize, newValue);
	}
	,
	validateSeries: function (viewportRect, windowRect, view) {
		var isValid = $.ig.ScatterBase.prototype.validateSeries.call(this, viewportRect, windowRect, view);
		if (this.radiusColumn() == null || this.fastItemsSource() == null || this.radiusColumn().count() == 0 || this.fastItemsSource().count() != this.radiusColumn().count()) {
			isValid = false;
		}
		return isValid;
	}
	,
	mustReact: function (propertyName, action) {
		if (action != $.ig.FastItemsSourceEventAction.prototype.change) {
			return true;
		}
		if (propertyName == null) {
			return true;
		}
		if (this.xMemberPath() == propertyName || this.yMemberPath() == propertyName || this.radiusMemberPath() == propertyName) {
			return true;
		}
		return false;
	}
	,
	createLegendItems: function () {
		var $self = this;
		var itemLegend = $.ig.util.cast($.ig.ItemLegend.prototype.$type, this.actualLegend());
		if (itemLegend == null || this.fastItemsSource() == null) {
			return;
		}
		this.legendItems(new $.ig.List$1($.ig.UIElement.prototype.$type, 0));
		var fastItemsSource = this.fastItemsSource();
		for (var i = 0; i < fastItemsSource.count(); i++) {
			var brush = null;
			if ($.ig.util.cast($.ig.ValueBrushScale.prototype.$type, this.fillScale()) !== null && this.fillColumn() != null) {
				brush = (this.fillScale()).getBrushByIndex(i, this.fillColumn());
			} else if ($.ig.util.cast($.ig.CustomPaletteBrushScale.prototype.$type, this.fillScale()) !== null) {
				brush = (this.fillScale()).getBrush1(i, fastItemsSource.count());
			} else if (this.fillScale() != null) {
				brush = this.fillScale().getBrush(i);
			}
			var item = new $.ig.ContentControl();
			var itemLabel = this.labelColumn() != null && this.labelColumn().item(i) != null ? this.labelColumn().item(i).toString() : "";
			item.content((function () {
				var $ret = new $.ig.DataContext();
				$ret.series($self);
				$ret.item(fastItemsSource.item(i));
				$ret.itemBrush(brush);
				$ret.itemLabel(itemLabel);
				return $ret;
			}()));
			item.contentTemplate(this.discreteLegendItemTemplate());
			this.legendItems().add(item);
		}
	}
	,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		$.ig.ScatterBase.prototype.dataUpdatedOverride.call(this, action, position, count, propertyName);
		this.drawLegend();
	}
	,
	notifyBrushScalesDirty: function () {
		$.ig.ScatterBase.prototype.notifyBrushScalesDirty.call(this);
		this.drawLegend();
	}
	,
	notifySizeScalesDirty: function () {
		$.ig.ScatterBase.prototype.notifySizeScalesDirty.call(this);
		this.drawLegend();
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.ScatterBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue) != null) {
					(oldValue).deregisterColumn(this.radiusColumn());
					(oldValue).deregisterColumn(this.fillColumn());
					(oldValue).deregisterColumn(this.labelColumn());
					this.radiusColumn(null);
					this.fillColumn(null);
					this.labelColumn(null);
				}
				if ($.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue) != null) {
					this.radiusColumn(this.registerDoubleColumn(this.radiusMemberPath()));
					if (!String.isNullOrEmpty(this.fillMemberPath())) {
						this.fillColumn(this.registerDoubleColumn(this.fillMemberPath()));
					}
					this.labelColumn(this.registerObjectColumn(this.labelMemberPath()));
				}
				this.renderSeries(false);
				this.drawLegend();
				break;
			case $.ig.BubbleSeries.prototype.radiusMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.radiusColumn());
					this.radiusColumn(this.registerDoubleColumn(this.radiusMemberPath()));
					this.drawLegend();
				}
				break;
			case $.ig.BubbleSeries.prototype.radiusColumnPropertyName:
				this.scatterView().trendLineManager().reset();
				this.renderSeries(false);
				this.notifyThumbnailDataChanged();
				break;
			case $.ig.BubbleSeries.prototype.radiusScalePropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.discreteLegendItemTemplatePropertyName:
				this.drawLegend();
				break;
			case $.ig.BubbleSeries.prototype.fillScalePropertyName:
				this.renderSeries(false);
				this.drawLegend();
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.BubbleSeries.prototype.fillColumnPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.BubbleSeries.prototype.fillMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.fillColumn());
					this.fillColumn(this.registerDoubleColumn(this.fillMemberPath()));
					this.drawLegend();
				}
				break;
			case $.ig.BubbleSeries.prototype.labelMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.labelColumn());
					this.labelColumn(this.registerObjectColumn(this.labelMemberPath()));
					this.drawLegend();
				}
				break;
			case $.ig.Series.prototype.actualLegendPropertyName:
				var legend = $.ig.util.cast($.ig.ItemLegend.prototype.$type, oldValue);
				if (legend != null) {
					legend.clearLegendItems(this);
				}
				var scaleLegend = $.ig.util.cast($.ig.ScaleLegend.prototype.$type, oldValue);
				if (scaleLegend != null) {
					var restoreLegend = true;
					var series = null;
					if (this.seriesViewer() != null) {
						var en = this.seriesViewer().series().getEnumerator();
						while (en.moveNext()) {
							var currentSeries = en.current();
							if (currentSeries.legend() == scaleLegend) {
								series = currentSeries;
								restoreLegend = false;
							}
						}
					}
					if (restoreLegend) {
						scaleLegend.restoreOriginalState();
					} else {
						scaleLegend.initializeLegend(series);
					}
				}
				this.drawLegend();
				break;
		}
	}
	,
	sizeBubbles: function (actualMarkers, actualRadiusColumn, viewportRect, isThumbnail) {
		var min = this.radiusColumn().minimum();
		var max = this.radiusColumn().maximum();
		if (this.radiusScale() != null) {
			var smallSize = this.radiusScale().minimumValue();
			var largeSize = this.radiusScale().maximumValue();
			var logBase = this.radiusScale().logarithmBase();
			if (!this.radiusScale().series().contains(this)) {
				this.radiusScale().series().add(this);
			}
			if (this.radiusScale().isLogarithmic()) {
				for (var i = 0; i < actualRadiusColumn.count(); i++) {
					actualRadiusColumn.__inner[i] = $.ig.BubbleSeries.prototype.getLogarithmicSize(min, max, smallSize, largeSize, logBase, actualRadiusColumn.__inner[i]);
				}
			} else {
				for (var i1 = 0; i1 < actualRadiusColumn.count(); i1++) {
					actualRadiusColumn.__inner[i1] = $.ig.BubbleSeries.prototype.getLinearSize(min, max, smallSize, largeSize, actualRadiusColumn.__inner[i1]);
				}
			}
		}
		if (isThumbnail) {
			var fullWidth = viewportRect.width();
			if (!this.view().viewport().isEmpty()) {
				fullWidth = this.view().viewport().width();
			} else if (this.seriesViewer() != null && !this.seriesViewer().viewportRect().isEmpty()) {
				fullWidth = this.seriesViewer().viewportRect().width();
			}
			var scale = viewportRect.width() / fullWidth;
			for (var ii = 0; ii < actualRadiusColumn.count(); ii++) {
				actualRadiusColumn.__inner[ii] = actualRadiusColumn.__inner[ii] * scale;
			}
		}
		for (var i2 = 0; i2 < actualMarkers.count(); i2++) {
			var marker = actualMarkers.__inner[i2];
			marker.width(Math.max(0, actualRadiusColumn.__inner[i2]));
			marker.height(Math.max(0, actualRadiusColumn.__inner[i2]));
		}
	}
	,
	setMarkerColors: function (actualMarkers) {
		if (this.fillScale() != null && !this.fillScale().series().contains(this)) {
			this.fillScale().series().add(this);
		}
		var customPaletteColorAxis = $.ig.util.cast($.ig.CustomPaletteBrushScale.prototype.$type, this.fillScale());
		var valueBrushScale = $.ig.util.cast($.ig.ValueBrushScale.prototype.$type, this.fillScale());
		var brushScale = this.fillScale();
		var clearMarkerBrushes = this.fillScale() == null || !this.fillScale().isReady() || (valueBrushScale != null && this.fillMemberPath() == null);
		if (clearMarkerBrushes) {
			this.bubbleView().clearMarkerBrushes();
			var bubbleThumbnailView = $.ig.util.cast($.ig.BubbleSeriesView.prototype.$type, this.thumbnailView());
			if (bubbleThumbnailView != null) {
				bubbleThumbnailView.clearMarkerBrushes();
			}
			return;
		}
		var markerCount = actualMarkers.count();
		var fastItemsSource = this.fastItemsSource();
		for (var i = 0; i < markerCount; i++) {
			var marker = actualMarkers.__inner[i];
			var markerContext = $.ig.util.cast($.ig.DataContext.prototype.$type, marker.content());
			if (markerContext != null) {
				var brush = null;
				var markerIndex = fastItemsSource.indexOf(markerContext.item());
				if (customPaletteColorAxis != null) {
					brush = customPaletteColorAxis.getBrush1(markerIndex, fastItemsSource.count());
				} else if (valueBrushScale != null) {
					brush = valueBrushScale.getBrushByIndex(markerIndex, this.fillColumn());
				} else if (brushScale != null) {
					brush = brushScale.getBrush(markerIndex);
				}
				markerContext.itemBrush(brush);
			}
		}
	}
	,
	$type: new $.ig.Type('BubbleSeries', $.ig.ScatterBase.prototype.$type)
}, true);

$.ig.util.defType('CustomPaletteBrushScale', 'BrushScale', {
	init: function () {
		$.ig.BrushScale.prototype.init.call(this);
	},
	brushSelectionMode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CustomPaletteBrushScale.prototype.brushSelectionModeProperty, $.ig.BrushSelectionMode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.CustomPaletteBrushScale.prototype.brushSelectionModeProperty));
		}
	}
	,
	getBrush1: function (index, total) {
		if (this.brushes() == null || this.brushes().count() == 0) {
			return null;
		}
		if (this.brushSelectionMode() == $.ig.BrushSelectionMode.prototype.select) {
			return $.ig.BrushScale.prototype.getBrush.call(this, index % this.brushes().count());
		}
		var scaledIndex = $.ig.BubbleSeries.prototype.getLinearSize(0, total - 1, 0, this.brushes().count() - 1, index);
		return this.getInterpolatedBrush(scaledIndex);
	}
	,
	isReady: function () {
		return this.brushes() != null && this.brushes().count() > 0;
	}
	,
	$type: new $.ig.Type('CustomPaletteBrushScale', $.ig.BrushScale.prototype.$type)
}, true);

$.ig.util.defType('ValueBrushScale', 'BrushScale', {
	init: function () {
		this.__currentIsLogarithmic = false;
		this.__currentLogarithmBase = 10;
		$.ig.BrushScale.prototype.init.call(this);
	},
	minimumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ValueBrushScale.prototype.minimumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ValueBrushScale.prototype.minimumValueProperty);
		}
	}
	,
	maximumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ValueBrushScale.prototype.maximumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ValueBrushScale.prototype.maximumValueProperty);
		}
	}
	,
	isLogarithmic: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ValueBrushScale.prototype.isLogarithmicProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ValueBrushScale.prototype.isLogarithmicProperty);
		}
	}
	,
	__currentIsLogarithmic: false,
	logarithmBase: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ValueBrushScale.prototype.logarithmBaseProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.ValueBrushScale.prototype.logarithmBaseProperty));
		}
	}
	,
	__currentLogarithmBase: 0,
	getBrushByIndex: function (index, FillColumn) {
		if (FillColumn == null || this.brushes() == null || this.brushes().count() == 0 || index < 0 || index >= FillColumn.count()) {
			return null;
		}
		if (FillColumn.count() == 0) {
			return this.brushes().item(0);
		}
		var min = $.ig.util.isNaN(this.minimumValue()) || Number.isInfinity(this.minimumValue()) ? FillColumn.minimum() : this.minimumValue();
		var max = $.ig.util.isNaN(this.maximumValue()) || Number.isInfinity(this.maximumValue()) ? FillColumn.maximum() : this.maximumValue();
		var value = FillColumn.item(index);
		if (min == max) {
			return value == min ? this.brushes().item(0) : null;
		}
		return this.getInterpolatedBrushLogarithmic(min, max, value);
	}
	,
	getBrushByValue: function (value, FillColumn) {
		if (FillColumn == null || this.brushes() == null || this.brushes().count() == 0) {
			return null;
		}
		if (FillColumn.count() <= 1) {
			return this.brushes().item(0);
		}
		var min = $.ig.util.isNaN(this.minimumValue()) || Number.isInfinity(this.minimumValue()) ? FillColumn.minimum() : this.minimumValue();
		var max = $.ig.util.isNaN(this.maximumValue()) || Number.isInfinity(this.maximumValue()) ? FillColumn.maximum() : this.maximumValue();
		if (value < min) {
			return null;
		}
		if (value > max) {
			return null;
		}
		return this.getInterpolatedBrushLogarithmic(min, max, value);
	}
	,
	getInterpolatedBrushLogarithmic: function (min, max, value) {
		if (this.__currentIsLogarithmic && this.__currentLogarithmBase > 0) {
			var newMin = Math.logBase(min, this.__currentLogarithmBase);
			var newMax = Math.logBase(max, this.__currentLogarithmBase);
			var newValue = Math.logBase(value, this.__currentLogarithmBase);
			return this.getInterpolatedBrushLinear(newMin, newMax, newValue);
		}
		return this.getInterpolatedBrushLinear(min, max, value);
	}
	,
	getInterpolatedBrushLinear: function (min, max, value) {
		if (value < min || value > max) {
			return null;
		}
		var scaledValue = (value - min) / (max - min);
		var scaledBrushIndex = scaledValue * (this.brushes().count() - 1);
		return this.getInterpolatedBrush(scaledBrushIndex);
	}
	,
	isReady: function () {
		return this.brushes() != null && this.brushes().count() > 0;
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.ValueBrushScale.prototype.logarithmBasePropertyName:
				this.__currentLogarithmBase = $.ig.util.getValue(newValue);
				break;
			case $.ig.ValueBrushScale.prototype.isLogarithmicPropertyName:
				this.__currentIsLogarithmic = newValue;
				break;
		}
		$.ig.BrushScale.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
	}
	,
	$type: new $.ig.Type('ValueBrushScale', $.ig.BrushScale.prototype.$type)
}, true);

$.ig.util.defType('NumericMarkerManager', 'MarkerManagerBase', {
	init: function (initNumber, provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.NumericMarkerManager.prototype.init1.call(this, 1, provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy, function () {
			return $.ig.CollisionAvoidanceType.prototype.none;
		});
	},
	init1: function (initNumber, provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy, getCollisionAvoidanceStrategy) {
		$.ig.MarkerManagerBase.prototype.init.call(this, provideMarkerStrategy, provideItemStrategy, removeUnusedMarkers, getItemLocationsStrategy, activeMarkerIndexesStrategy);
		this.populateColumnValues(false);
		this.getColumnValues(function (i) { return { __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }; });
		this.getCollisionAvoidanceStrategy(getCollisionAvoidanceStrategy);
	},
	_populateColumnValues: false,
	populateColumnValues: function (value) {
		if (arguments.length === 1) {
			this._populateColumnValues = value;
			return value;
		} else {
			return this._populateColumnValues;
		}
	}
	,
	_getColumnValues: null,
	getColumnValues: function (value) {
		if (arguments.length === 1) {
			this._getColumnValues = value;
			return value;
		} else {
			return this._getColumnValues;
		}
	}
	,
	_getCollisionAvoidanceStrategy: null,
	getCollisionAvoidanceStrategy: function (value) {
		if (arguments.length === 1) {
			this._getCollisionAvoidanceStrategy = value;
			return value;
		} else {
			return this._getCollisionAvoidanceStrategy;
		}
	}
	,
	_getMarkerDesiredSize: null,
	getMarkerDesiredSize: function (value) {
		if (arguments.length === 1) {
			this._getMarkerDesiredSize = value;
			return value;
		} else {
			return this._getMarkerDesiredSize;
		}
	}
	,
	winnowMarkers: function (markers, maximumMarkers, windowRect, viewportRect, currentResolution) {
		var itemLocations = this.getItemLocationsStrategy()();
		markers.clear();
		var visibleItems = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		maximumMarkers = Math.max(0, maximumMarkers);
		var markerItems = null;
		this.getVisibleItems(windowRect, viewportRect, itemLocations, visibleItems);
		if (maximumMarkers >= visibleItems.count()) {
			markerItems = visibleItems;
		} else {
			markerItems = new $.ig.List$1($.ig.Number.prototype.$type, 0);
			var resolution = Math.max(8, currentResolution);
			var buckets = this.getBuckets(viewportRect, visibleItems, resolution, itemLocations);
			var keys = new $.ig.List$1($.ig.Number.prototype.$type, 1, buckets.keys());
			if ($.ig.MarkerManagerBase.prototype.useDeterministicSelection()) {
				keys.sort();
			}
			this.selectMarkerItems(maximumMarkers, buckets, keys, markerItems);
		}
		this.assignMarkers(markers, itemLocations, markerItems);
	}
	,
	assignMarkers: function (markers, itemLocations, markerItems) {
		for (var i = 0; i < markerItems.count(); ++i) {
			var index = markerItems.__inner[i];
			var point = itemLocations[index];
			var item = this.provideItemStrategy()(index);
			var marker = this.provideMarkerStrategy()(item);
			if (marker.content() != null) {
				(marker.content()).item(item);
			}
			var mp = new $.ig.OwnedPoint();
			if (this.populateColumnValues()) {
				mp.columnValues(this.getColumnValues()(index));
			}
			mp.ownerItem(item);
			mp.point({ __x: point.__x, __y: point.__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			if (!markers.containsKey(item)) {
				markers.add(item, mp);
			}
		}
	}
	,
	render: function (markers, lightweight) {
		var keys = markers.keys();
		if ($.ig.MarkerManagerBase.prototype.useDeterministicSelection()) {
			var keysList = new $.ig.List$1($.ig.Object.prototype.$type, 1, markers.keys());
			keysList.sort2(function (o1, o2) {
				var point1 = markers.item(o1);
				var point2 = markers.item(o2);
				var dist1 = Math.pow(point1.point().__x, 2) + Math.pow(point1.point().__y, 2);
				var dist2 = Math.pow(point2.point().__x, 2) + Math.pow(point2.point().__y, 2);
				return (dist1).compareTo(dist2);
			});
			keys = keysList;
		}
		var smartPlacer = null;
		var wrapper = null;
		switch (this.getCollisionAvoidanceStrategy()()) {
			case $.ig.CollisionAvoidanceType.prototype.none: break;
			case $.ig.CollisionAvoidanceType.prototype.omit:
				smartPlacer = (function () {
					var $ret = new $.ig.SmartPlacer();
					$ret.overlap(0.3);
					$ret.fade(0);
					return $ret;
				}());
				wrapper = new $.ig.SmartPlaceableWrapper$1($.ig.Marker.prototype.$type);
				wrapper.noWiggle(true);
				break;
			case $.ig.CollisionAvoidanceType.prototype.fade:
				smartPlacer = (function () {
					var $ret = new $.ig.SmartPlacer();
					$ret.overlap(0.6);
					$ret.fade(2);
					return $ret;
				}());
				wrapper = new $.ig.SmartPlaceableWrapper$1($.ig.Marker.prototype.$type);
				wrapper.noWiggle(true);
				break;
			case $.ig.CollisionAvoidanceType.prototype.omitAndShift:
				smartPlacer = (function () {
					var $ret = new $.ig.SmartPlacer();
					$ret.overlap(0.3);
					$ret.fade(0);
					return $ret;
				}());
				wrapper = new $.ig.SmartPlaceableWrapper$1($.ig.Marker.prototype.$type);
				break;
			case $.ig.CollisionAvoidanceType.prototype.fadeAndShift:
				smartPlacer = (function () {
					var $ret = new $.ig.SmartPlacer();
					$ret.overlap(0.6);
					$ret.fade(2);
					return $ret;
				}());
				wrapper = new $.ig.SmartPlaceableWrapper$1($.ig.Marker.prototype.$type);
				break;
		}
		var en = keys.getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			var point = markers.item(key);
			var marker = this.provideMarkerStrategy()(point.ownerItem());
			if (smartPlacer != null && wrapper != null) {
				wrapper.element(marker);
				wrapper.elementDesiredSize(this.getMarkerDesiredSize()(marker));
				wrapper.originalLocation(point.point());
				smartPlacer.place(wrapper);
				if (wrapper.opacity() == 0) {
					wrapper.smartPosition(wrapper.smartPosition());
				}
				point.point(wrapper.elementLocationResult());
			} else {
				marker.__opacity = 1;
				marker.__visibility = $.ig.Visibility.prototype.visible;
			}
			this.updateMarkerPosition(marker, point, lightweight);
		}
		this.removeUnusedMarkers()(markers);
	}
	,
	updateMarkerPosition: function (marker, point, lightweight) {
		marker.canvasLeft(point.point().__x);
		marker.canvasTop(point.point().__y);
	}
	,
	_series: null,
	series: function (value) {
		if (arguments.length === 1) {
			this._series = value;
			return value;
		} else {
			return this._series;
		}
	}
	,
	$type: new $.ig.Type('NumericMarkerManager', $.ig.MarkerManagerBase.prototype.$type)
}, true);

$.ig.util.defType('MarkerManagerBucket', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	__items: null,
	items: function () {
		if (this.__items == null) {
			this.__items = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		}
		return this.__items;
	}
	,
	__priorityItems: null,
	priorityItems: function () {
		if (this.__priorityItems == null) {
			this.__priorityItems = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		}
		return this.__priorityItems;
	}
	,
	getItem: function (wasPriority) {
		if (this.priorityItems().count() > 0) {
			var priorityIndex = this.priorityItems().__inner[this.priorityItems().count() - 1];
			this.priorityItems().removeAt(this.priorityItems().count() - 1);
			wasPriority = true;
			return {
				ret: priorityIndex,
				p0: wasPriority
			};
		}
		var index = this.items().__inner[this.items().count() - 1];
		this.items().removeAt(this.items().count() - 1);
		wasPriority = false;
		return {
			ret: index,
			p0: wasPriority
		};
	}
	,
	isEmpty: function () {
		return this.items().count() == 0 && this.priorityItems().count() == 0;
	}
	,
	$type: new $.ig.Type('MarkerManagerBucket', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('IFlattener', 'Object', {
	$type: new $.ig.Type('IFlattener', null)
}, true);

$.ig.util.defType('DefaultFlattener', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	flattenHelper: function (result, X, Y, b, e, E) {
		var indices = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var start = b;
		var end = e;
		var toFlatten = end - start + 1;
		while (toFlatten > 0) {
			if (toFlatten <= $.ig.DefaultFlattener.prototype._flattenerChunking) {
				$.ig.Flattener.prototype.flatten2(indices, X, Y, start, end, E);
				start = end + 1;
			} else {
				var currentEnd = start + $.ig.DefaultFlattener.prototype._flattenerChunking - 1;
				$.ig.Flattener.prototype.flatten2(indices, X, Y, start, currentEnd, E);
				start = currentEnd + 1;
			}
			toFlatten = end - start + 1;
		}
		return indices;
	}
	,
	fastFlattenHelper: function (X, Y, b, e, E) {
		var indices = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		var start = b;
		var end = e;
		var toFlatten = end - start + 1;
		while (toFlatten > 0) {
			if (toFlatten <= $.ig.DefaultFlattener.prototype._flattenerChunking) {
				$.ig.Flattener.prototype.fastFlatten2(indices, X, Y, start, end, E);
				start = end + 1;
			} else {
				var currentEnd = start + $.ig.DefaultFlattener.prototype._flattenerChunking - 1;
				$.ig.Flattener.prototype.fastFlatten2(indices, X, Y, start, currentEnd, E);
				start = currentEnd + 1;
			}
			toFlatten = end - start + 1;
		}
		return indices;
	}
	,
	flatten: function (points, resolution) {
		var $self = this;
		var x = function (i) { return $self.getX(points, i); };
		var y = function (i) { return $self.getY(points, i); };
		return this.getFlattened(points, resolution, x, y);
	}
	,
	fastFlatten: function (x, y, count, resolution) {
		return this.getFastFlattened(x, y, count, resolution);
	}
	,
	getFlattened: function (pointsList, resolution, x, y) {
		var indices = this.flattenHelper(new $.ig.List$1($.ig.Number.prototype.$type, 0), x, y, 0, pointsList.count() - 1, resolution);
		var reordered = new $.ig.RearrangedList$1($.ig.Point.prototype.$type, pointsList, indices);
		return reordered;
	}
	,
	getFastFlattened: function (x, y, count, resolution) {
		var indices = this.fastFlattenHelper(x, y, 0, count - 1, resolution);
		var ret = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		for (var i = 0; i < indices.count(); i++) {
			ret.add({ __x: x[indices.__inner[i]], __y: y[indices.__inner[i]], $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
		return ret;
	}
	,
	getX: function (list, i) {
		return list.item(i).__x;
	}
	,
	getY: function (list, i) {
		return list.item(i).__y;
	}
	,
	$type: new $.ig.Type('DefaultFlattener', $.ig.Object.prototype.$type, [$.ig.IFlattener.prototype.$type])
}, true);

$.ig.util.defType('ScatterTrendLineManager', 'TrendLineManagerBase$1', {
	init: function () {
		$.ig.TrendLineManagerBase$1.prototype.init.call(this, $.ig.Point.prototype.$type);
	},
	prepareLine: function (flattenedPoints, trendLineType, XColumn, YColumn, period, GetScaledXValue, GetScaledYValue, trendResolutionParams, clipper, min, max) {
		var xmin = min;
		var xmax = max;
		var trend = new $.ig.List$1($.ig.Point.prototype.$type, 0);
		var count = 0;
		if (XColumn != null) {
			count = XColumn.count();
		}
		if (YColumn != null) {
			count = Math.min(count, YColumn.count());
		}
		if (!trendResolutionParams.window().isEmpty() && !trendResolutionParams.viewport().isEmpty()) {
			if (trendLineType == $.ig.TrendLineType.prototype.none) {
				this.trendCoefficients(null);
				this.trendColumn().clear();
			} else if (this.isFit(trendLineType)) {
				this.trendColumn().clear();
				this.trendCoefficients($.ig.TrendFitCalculator.prototype.calculateFit(trend, trendLineType, trendResolutionParams, this.trendCoefficients(), count, function (i) { return XColumn.item(i); }, function (i) { return YColumn.item(i); }, GetScaledXValue, GetScaledYValue, xmin, xmax));
			} else if (this.isAverage(trendLineType)) {
				this.trendCoefficients(null);
				this.trendColumn().clear();
				$.ig.TrendAverageCalculator.prototype.calculateXYAverage(trendLineType, this.trendColumn(), XColumn, YColumn, period);
				var en = this.trendColumn().getEnumerator();
				while (en.moveNext()) {
					var point = en.current();
					var xi = GetScaledXValue(point.__x);
					var yi = GetScaledYValue(point.__y);
					if (!$.ig.util.isNaN(xi) && !$.ig.util.isNaN(yi)) {
						trend.add({ __x: xi, __y: yi, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
					}
				}
			}
			this.flattenTrendLine1(trend, trendResolutionParams, flattenedPoints, clipper);
		}
	}
	,
	$type: new $.ig.Type('ScatterTrendLineManager', $.ig.TrendLineManagerBase$1.prototype.$type.specialize($.ig.Point.prototype.$type))
}, true);

$.ig.util.defType('ErrorBarsHelper', 'Object', {
	init: function (errorBarsHost, viewportHost) {
		$.ig.Object.prototype.init.call(this);
		this.errorBarsHost(errorBarsHost);
		this.viewportHost(viewportHost);
	},
	_errorBarsHost: null,
	errorBarsHost: function (value) {
		if (arguments.length === 1) {
			this._errorBarsHost = value;
			return value;
		} else {
			return this._errorBarsHost;
		}
	}
	,
	_viewportHost: null,
	viewportHost: function (value) {
		if (arguments.length === 1) {
			this._viewportHost = value;
			return value;
		} else {
			return this._viewportHost;
		}
	}
	,
	isCalculatorIndependent: function (calculator) {
		var type = calculator.getCalculatorType();
		if (type == $.ig.ErrorBarCalculatorType.prototype.percentage || type == $.ig.ErrorBarCalculatorType.prototype.data) {
			return false;
		} else {
			return true;
		}
	}
	,
	calculateIndependentErrorBarPosition: function (calculator, position) {
		if (calculator.hasConstantPosition()) {
			position = calculator.getPosition();
		}
		return {
			p1: position
		};
	}
	,
	calculateStdDevErrorBarSize: function (unscaledValue, calculator, axis, positiveSize, negativeSize) {
		var windowRect;
		var viewportRect;
		var effectiveViewportRect;
		var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		effectiveViewportRect = this.viewportHost().getEffectiveViewport();
		var sParams = new $.ig.ScalerParams(0, windowRect, viewportRect, axis.isInverted(), effectiveViewportRect);
		var scaledValue = axis.getScaledValue(unscaledValue, sParams);
		var errorBarValue = calculator.getIndependentValue();
		positiveSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue + errorBarValue, sParams) - scaledValue));
		negativeSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue - errorBarValue, sParams) - scaledValue));
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	calculateIndependentErrorBarSize: function (scaledValue, calculator, axis, positiveSize, negativeSize) {
		var windowRect;
		var viewportRect;
		var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var sParams = new $.ig.ScalerParams(1, windowRect, viewportRect, axis.isInverted());
		var unscaledValue = axis.getUnscaledValue(scaledValue, sParams);
		var errorBarValue = calculator.getIndependentValue();
		positiveSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue + errorBarValue, sParams) - scaledValue));
		negativeSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue - errorBarValue, sParams) - scaledValue));
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	calculateDependentErrorBarSize1: function (scaledValue, calculator, axis, positiveSize, negativeSize) {
		var windowRect;
		var viewportRect;
		var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var sParams = new $.ig.ScalerParams(1, windowRect, viewportRect, axis.isInverted());
		var unscaledValue = axis.getUnscaledValue(scaledValue, sParams);
		var errorBarValue = calculator.getDependentValue(unscaledValue);
		positiveSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue + errorBarValue, sParams) - scaledValue));
		negativeSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue - errorBarValue, sParams) - scaledValue));
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	calculateDependentErrorBarSize: function (scaledValue, calculator, refAxis, targetAxis, positiveSize, negativeSize) {
		var windowRect;
		var viewportRect;
		var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var refParams = new $.ig.ScalerParams(1, windowRect, viewportRect, refAxis.isInverted());
		var targetParams = new $.ig.ScalerParams(1, windowRect, viewportRect, targetAxis.isInverted());
		var unscaledValue = refAxis.getUnscaledValue(scaledValue, refParams);
		var errorBarValue = calculator.getDependentValue(unscaledValue);
		positiveSize = Math.abs(Math.round(refAxis.getScaledValue(unscaledValue + errorBarValue, targetParams) - scaledValue));
		negativeSize = Math.abs(Math.round(refAxis.getScaledValue(unscaledValue - errorBarValue, targetParams) - scaledValue));
		return {
			p4: positiveSize,
			p5: negativeSize
		};
	}
	,
	calculateFromUnscaledErrorBarValue: function (value, unscaledErrorBarValue, axis, positiveSize, negativeSize) {
		var windowRect;
		var viewportRect;
		var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var sParams = new $.ig.ScalerParams(1, windowRect, viewportRect, axis.isInverted());
		var unscaledValue = axis.getUnscaledValue(value, sParams);
		positiveSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue + unscaledErrorBarValue, sParams) - value));
		negativeSize = Math.abs(Math.round(axis.getScaledValue(unscaledValue - unscaledErrorBarValue, sParams) - value));
		return {
			p3: positiveSize,
			p4: negativeSize
		};
	}
	,
	addErrorBarVertical: function (errorBarsGeometry, position, errorBarLength, positive) {
	}
	,
	addErrorBarHorizontal: function (errorBarsGeomety, position, errorBarLength, positive) {
	}
	,
	calculateErrorBarCenterHorizontal: function (calculator, axis, point, mean) {
		var center = new $.ig.Point(0);
		if (calculator.getCalculatorType() == $.ig.ErrorBarCalculatorType.prototype.standardDeviation) {
			var windowRect;
			var viewportRect;
			var effectiveViewportRect;
			var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
			viewportRect = $ret.p0;
			windowRect = $ret.p1;
			effectiveViewportRect = this.viewportHost().getEffectiveViewport();
			var sParams = new $.ig.ScalerParams(0, windowRect, viewportRect, axis.isInverted(), effectiveViewportRect);
			center.__x = Math.round(axis.getScaledValue(mean, sParams));
			center.__y = Math.round(point.__y);
		} else {
			center.__x = Math.round(point.__x);
			center.__y = Math.round(point.__y);
		}
		return center;
	}
	,
	calculateErrorBarCenterVertical: function (calculator, axis, point, mean) {
		var center = new $.ig.Point(0);
		if (calculator.getCalculatorType() == $.ig.ErrorBarCalculatorType.prototype.standardDeviation) {
			var windowRect;
			var viewportRect;
			var effectiveViewportRect;
			var $ret = this.viewportHost().getViewInfo(viewportRect, windowRect);
			viewportRect = $ret.p0;
			windowRect = $ret.p1;
			effectiveViewportRect = this.viewportHost().getEffectiveViewport();
			var sParams = new $.ig.ScalerParams(0, windowRect, viewportRect, axis.isInverted(), effectiveViewportRect);
			center.__x = Math.round(point.__x);
			center.__y = Math.round(axis.getScaledValue(mean, sParams));
		} else {
			center.__x = Math.round(point.__x);
			center.__y = Math.round(point.__y);
		}
		return center;
	}
	,
	$type: new $.ig.Type('ErrorBarsHelper', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ScaleLegend', 'LegendBase', {
	createView: function () {
		return new $.ig.ScaleLegendView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.LegendBase.prototype.onViewCreated.call(this, view);
		this.scaleView(view);
	}
	,
	_scaleView: null,
	scaleView: function (value) {
		if (arguments.length === 1) {
			this._scaleView = value;
			return value;
		} else {
			return this._scaleView;
		}
	}
	,
	legendScaleElement: function () {
		return this.scaleView().legendScaleElement();
	}
	,
	minText: function () {
		return this.scaleView().minText();
	}
	,
	maxText: function () {
		return this.scaleView().maxText();
	}
	,
	init: function () {
		$.ig.LegendBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.ScaleLegend.prototype.$type);
	},
	parentVisibility: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScaleLegend.prototype.parentVisibilityProperty, $.ig.Visibility.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ScaleLegend.prototype.parentVisibilityProperty));
		}
	}
	,
	seriesMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScaleLegend.prototype.seriesMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScaleLegend.prototype.seriesMarkerBrushProperty);
		}
	}
	,
	_minimumValue: 0,
	minimumValue: function (value) {
		if (arguments.length === 1) {
			this._minimumValue = value;
			return value;
		} else {
			return this._minimumValue;
		}
	}
	,
	_maximumValue: 0,
	maximumValue: function (value) {
		if (arguments.length === 1) {
			this._maximumValue = value;
			return value;
		} else {
			return this._maximumValue;
		}
	}
	,
	_brushes: null,
	brushes: function (value) {
		if (arguments.length === 1) {
			this._brushes = value;
			return value;
		} else {
			return this._brushes;
		}
	}
	,
	_sizeValueColumn: null,
	sizeValueColumn: function (value) {
		if (arguments.length === 1) {
			this._sizeValueColumn = value;
			return value;
		} else {
			return this._sizeValueColumn;
		}
	}
	,
	_brushValueColumn: null,
	brushValueColumn: function (value) {
		if (arguments.length === 1) {
			this._brushValueColumn = value;
			return value;
		} else {
			return this._brushValueColumn;
		}
	}
	,
	_brushScale: null,
	brushScale: function (value) {
		if (arguments.length === 1) {
			this._brushScale = value;
			return value;
		} else {
			return this._brushScale;
		}
	}
	,
	__series: null,
	series: function (value) {
		if (arguments.length === 1) {
			var $self = this;
			this.__series = value;
			this.setBinding($.ig.ScaleLegend.prototype.parentVisibilityProperty, (function () {
				var $ret = new $.ig.Binding(1, "Visibility");
				$ret.source($self.__series);
				return $ret;
			}()));
			this.setBinding($.ig.ScaleLegend.prototype.seriesMarkerBrushProperty, (function () {
				var $ret = new $.ig.Binding(1, $.ig.MarkerSeries.prototype.markerBrushPropertyName);
				$ret.source($self.__series);
				return $ret;
			}()));
			return value;
		} else {
			return this.__series;
		}
	}
	,
	_series_PropertyChanged: function (sender, e) {
		switch (e.propertyName()) {
			case "Visibility":
				this.parentVisibility(this.__series.__visibility);
				break;
			case $.ig.MarkerSeries.prototype.actualMarkerBrushPropertyName:
				this.seriesMarkerBrush(this.__series.actualMarkerBrush());
				break;
		}
	}
	,
	restoreOriginalState: function () {
		this.scaleView().restoreOriginalState();
	}
	,
	getBrush: function (index) {
		if (this.series() == null) {
			return null;
		}
		var customPaletteBrushScale = $.ig.util.cast($.ig.CustomPaletteBrushScale.prototype.$type, this.series().fillScale());
		var valueBrushScale = $.ig.util.cast($.ig.ValueBrushScale.prototype.$type, this.series().fillScale());
		var brushScale = this.series().fillScale();
		if (customPaletteBrushScale != null && this.series().fastItemsSource() != null) {
			return customPaletteBrushScale.getBrush1(index, this.series().fastItemsSource().count());
		} else if (valueBrushScale != null) {
			return valueBrushScale.getBrushByIndex(index, this.brushValueColumn());
		} else if (brushScale != null) {
			return brushScale.getBrush(index);
		}
		return null;
	}
	,
	getFirstColor: function (brush) {
		if (brush == null) {
			return this.scaleView().getTransparentBrush();
		}
		return $.ig.ColorUtil.prototype.getColor(brush);
	}
	,
	initializeLegend: function (series) {
		var bubbleSeries = $.ig.util.cast($.ig.BubbleSeries.prototype.$type, series);
		if (bubbleSeries == null || series.__visibility != $.ig.Visibility.prototype.visible) {
			return;
		}
		this.sizeValueColumn(bubbleSeries.internalRadiusColumn());
		this.brushScale(bubbleSeries.fillScale());
		this.brushValueColumn(bubbleSeries.fillColumn());
		this.series(bubbleSeries);
		this.brushes(new $.ig.ObservableCollection$1($.ig.Brush.prototype.$type, 0));
		this.renderLegend();
	}
	,
	renderLegend: function () {
		if (this.legendScaleElement() == null || this.sizeValueColumn() == null || this.sizeValueColumn().count() == 0) {
			return;
		}
		if (this.series() == null || this.series().actualLegend() != this) {
			return;
		}
		var useSeriesBrush = false;
		var legendScaleShapeElement = $.ig.util.cast($.ig.Shape.prototype.$type, this.legendScaleElement());
		if (legendScaleShapeElement != null) {
			var gradient = this.scaleView().buildGradient();
			for (var i = 0; i < this.sizeValueColumn().count(); i++) {
				if (this.brushScale() == null || !this.brushScale().isReady()) {
					if (this.series() != null) {
						useSeriesBrush = true;
					}
					break;
				}
				var scaledColorIndex = NaN;
				if (this.brushValueColumn() != null) {
					scaledColorIndex = (this.brushValueColumn().item(i) - this.brushValueColumn().minimum()) / (this.brushValueColumn().maximum() - this.brushValueColumn().minimum());
				}
				var colorOffset = (this.sizeValueColumn().item(i) - this.sizeValueColumn().minimum()) / (this.sizeValueColumn().maximum() - this.sizeValueColumn().minimum());
				if ($.ig.util.isNaN(scaledColorIndex)) {
					scaledColorIndex = 0;
				}
				if ($.ig.util.isNaN(colorOffset)) {
					colorOffset = scaledColorIndex;
				}
				var defaultColor = this.series() != null ? this.getFirstColor(this.series().actualMarkerBrush()) : this.scaleView().getTransparentBrush();
				var brush = this.getBrush(i);
				var color = brush != null ? this.getFirstColor(brush) : defaultColor;
				this.scaleView().addGradientStop(gradient, color, colorOffset);
			}
			this.scaleView().setScaleFill(legendScaleShapeElement, useSeriesBrush, gradient);
		}
		if (this.minText() != null) {
			this.minText().text((Math.round(this.sizeValueColumn().minimum() * 1000) / 1000).toString());
		}
		if (this.maxText() != null) {
			this.maxText().text((Math.round(this.sizeValueColumn().maximum() * 1000) / 1000).toString());
		}
	}
	,
	$type: new $.ig.Type('ScaleLegend', $.ig.LegendBase.prototype.$type)
}, true);

$.ig.util.defType('ScatterFrameBase$1', 'Frame', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Frame.prototype.init.call(this);
		this.points(new $.ig.List$1($.ig.Point.prototype.$type, 0));
		this.cachedPoints(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0));
		this.markers(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0));
		this.trendLine(new $.ig.List$1($.ig.Point.prototype.$type, 0));
		this.horizontalErrorBars(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0));
		this.verticalErrorBars(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0));
		this.horizontalErrorBarWidths(new $.ig.Dictionary$2($.ig.Object.prototype.$type, Array, 0));
		this.verticalErrorBarHeights(new $.ig.Dictionary$2($.ig.Object.prototype.$type, Array, 0));
		this.getNewMinValue(function (maxPoint, minFrame, maxFrame) { return maxPoint; });
		this.ownedPointDictInterpolator(new $.ig.DictInterpolator$3($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, this.$t, this.interpolatePoint.runOn(this), function (p) { return p.ownerItem(); }, function (p) { return !$.ig.util.isNaN(p.point().__x) && !$.ig.util.isNaN(p.point().__y); }, function () { return new $.ig.OwnedPoint(); }));
	},
	_ownedPointDictInterpolator: null,
	ownedPointDictInterpolator: function (value) {
		if (arguments.length === 1) {
			this._ownedPointDictInterpolator = value;
			return value;
		} else {
			return this._ownedPointDictInterpolator;
		}
	}
	,
	interpolate3: function (p, minFrame, maxFrame) {
		var min = minFrame;
		var max = maxFrame;
		if ($.ig.util.getBoxIfEnum(this.$t, min) == null || $.ig.util.getBoxIfEnum(this.$t, max) == null) {
			return;
		}
		this.ownedPointDictInterpolator().interpolate(this.cachedPoints(), p, min.cachedPoints(), max.cachedPoints(), min, max);
		$.ig.Frame.prototype.interpolate(this.points(), p, min.points(), max.points());
		this.ownedPointDictInterpolator().interpolate(this.markers(), p, min.markers(), max.markers(), min, max);
		this.ownedPointDictInterpolator().interpolate(this.horizontalErrorBars(), p, min.horizontalErrorBars(), max.horizontalErrorBars(), min, max);
		this.ownedPointDictInterpolator().interpolate(this.verticalErrorBars(), p, min.verticalErrorBars(), max.verticalErrorBars(), min, max);
		this.addPointsThatSweepThroughTheView(this.markers(), p, min, max);
		$.ig.Frame.prototype.interpolate(this.trendLine(), p, min.trendLine(), max.trendLine());
		this.horizontalErrorBarWidths(max.horizontalErrorBarWidths());
		this.verticalErrorBarHeights(max.verticalErrorBarHeights());
		this.interpolateOverride(p, min, max);
	}
	,
	interpolateOverride: function (p, minFrame, maxFrame) {
	}
	,
	addPointsThatSweepThroughTheView: function (markers, p, minFrame, maxFrame) {
		var en = $.ig.Enumerable.prototype.where$1($.ig.OwnedPoint.prototype.$type, minFrame.cachedPoints().values(), function (changedPoint) { return !markers.containsKey(changedPoint.ownerItem()); }).getEnumerator();
		while (en.moveNext()) {
			var changedPoint = en.current();
			var maxPoint;
			if (!(function () { var $ret = maxFrame.cachedPoints().tryGetValue(changedPoint.ownerItem(), maxPoint); maxPoint = $ret.p1; return $ret.ret; }()) || (maxPoint.columnValues().__x == changedPoint.columnValues().__x && maxPoint.columnValues().__y == changedPoint.columnValues().__y)) {
				continue;
			}
			var newPoint = new $.ig.OwnedPoint();
			this.interpolatePoint(newPoint, p, changedPoint, maxPoint, minFrame, maxFrame);
			if ($.ig.util.isNaN(newPoint.point().__x) || $.ig.util.isNaN(newPoint.point().__y)) {
				continue;
			}
			markers.add(newPoint.ownerItem(), newPoint);
		}
	}
	,
	_getNewMinValue: null,
	getNewMinValue: function (value) {
		if (arguments.length === 1) {
			this._getNewMinValue = value;
			return value;
		} else {
			return this._getNewMinValue;
		}
	}
	,
	interpolateColumnValues: function (point, p, minPoint, maxPoint) {
		if (minPoint != null) {
			point.columnValues({ __x: minPoint.columnValues().__x, __y: minPoint.columnValues().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		} else if (maxPoint != null) {
			point.columnValues({ __x: maxPoint.columnValues().__x, __y: maxPoint.columnValues().__y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		}
	}
	,
	interpolatePoint: function (point, p, minPoint, maxPoint, minFrame, maxFrame) {
		var min;
		var max;
		if (minPoint == null) {
			if (maxPoint != null) {
				var minValue;
				if ((function () { var $ret = minFrame.cachedPoints().tryGetValue(maxPoint.ownerItem(), minValue); minValue = $ret.p1; return $ret.ret; }())) {
					min = minValue;
				} else {
					min = this.getNewMinValue()(maxPoint, minFrame, maxFrame);
				}
			} else {
				point.point({ __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				return;
			}
		} else {
			min = minPoint;
			if (point.ownerItem() == null) {
				point.ownerItem(minPoint.ownerItem());
			}
		}
		if (maxPoint == null) {
			if (minPoint != null) {
				var maxValue;
				if ((function () { var $ret = maxFrame.cachedPoints().tryGetValue(minPoint.ownerItem(), maxValue); maxValue = $ret.p1; return $ret.ret; }())) {
					max = maxValue;
				} else {
					point.point({ __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
					return;
				}
			} else {
				point.point({ __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				return;
			}
		} else {
			max = maxPoint;
			if (point.ownerItem() == null) {
				point.ownerItem(maxPoint.ownerItem());
			}
		}
		this.interpolateColumnValues(point, p, min, max);
		if ($.ig.util.isNaN(min.point().__x) || $.ig.util.isNaN(min.point().__y)) {
			min = max;
		}
		this.interpolatePointOverride(point, p, min, max);
	}
	,
	interpolatePointOverride: function (point, p, min, max) {
		var q = 1 - p;
		point.point({ __x: min.point().__x * q + max.point().__x * p, __y: min.point().__y * q + max.point().__y * p, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
	}
	,
	_markers: null,
	markers: function (value) {
		if (arguments.length === 1) {
			this._markers = value;
			return value;
		} else {
			return this._markers;
		}
	}
	,
	_cachedPoints: null,
	cachedPoints: function (value) {
		if (arguments.length === 1) {
			this._cachedPoints = value;
			return value;
		} else {
			return this._cachedPoints;
		}
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
	_trendLine: null,
	trendLine: function (value) {
		if (arguments.length === 1) {
			this._trendLine = value;
			return value;
		} else {
			return this._trendLine;
		}
	}
	,
	_horizontalErrorBars: null,
	horizontalErrorBars: function (value) {
		if (arguments.length === 1) {
			this._horizontalErrorBars = value;
			return value;
		} else {
			return this._horizontalErrorBars;
		}
	}
	,
	_verticalErrorBars: null,
	verticalErrorBars: function (value) {
		if (arguments.length === 1) {
			this._verticalErrorBars = value;
			return value;
		} else {
			return this._verticalErrorBars;
		}
	}
	,
	_horizontalErrorBarWidths: null,
	horizontalErrorBarWidths: function (value) {
		if (arguments.length === 1) {
			this._horizontalErrorBarWidths = value;
			return value;
		} else {
			return this._horizontalErrorBarWidths;
		}
	}
	,
	_verticalErrorBarHeights: null,
	verticalErrorBarHeights: function (value) {
		if (arguments.length === 1) {
			this._verticalErrorBarHeights = value;
			return value;
		} else {
			return this._verticalErrorBarHeights;
		}
	}
	,
	$type: new $.ig.Type('ScatterFrameBase$1', $.ig.Frame.prototype.$type)
}, true);

$.ig.util.defType('ScatterAxisInfoCache', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_xAxis: null,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this._xAxis = value;
			return value;
		} else {
			return this._xAxis;
		}
	}
	,
	_yAxis: null,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this._yAxis = value;
			return value;
		} else {
			return this._yAxis;
		}
	}
	,
	_xAxisIsInverted: false,
	xAxisIsInverted: function (value) {
		if (arguments.length === 1) {
			this._xAxisIsInverted = value;
			return value;
		} else {
			return this._xAxisIsInverted;
		}
	}
	,
	_yAxisIsInverted: false,
	yAxisIsInverted: function (value) {
		if (arguments.length === 1) {
			this._yAxisIsInverted = value;
			return value;
		} else {
			return this._yAxisIsInverted;
		}
	}
	,
	_fastItemsSource: null,
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	$type: new $.ig.Type('ScatterAxisInfoCache', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('DictInterpolator$3', 'Object', {
	$tKey: null,
	$tValue: null,
	$tFrame: null,
	init: function ($tKey, $tValue, $tFrame, interpolatePointStrat, getKeyStrat, validPointStrat, createValueStrat) {
		this.$tKey = $tKey;
		this.$tValue = $tValue;
		this.$tFrame = $tFrame;
		this.$type = this.$type.specialize(this.$tKey, this.$tValue, this.$tFrame);
		$.ig.Object.prototype.init.call(this);
		this.interpolatePointStrat(interpolatePointStrat);
		this.getKeyStrat(getKeyStrat);
		this.validPointStrat(validPointStrat);
		this.createValueStrat(createValueStrat);
	},
	_interpolatePointStrat: null,
	interpolatePointStrat: function (value) {
		if (arguments.length === 1) {
			this._interpolatePointStrat = value;
			return value;
		} else {
			return this._interpolatePointStrat;
		}
	}
	,
	_getKeyStrat: null,
	getKeyStrat: function (value) {
		if (arguments.length === 1) {
			this._getKeyStrat = value;
			return value;
		} else {
			return this._getKeyStrat;
		}
	}
	,
	_validPointStrat: null,
	validPointStrat: function (value) {
		if (arguments.length === 1) {
			this._validPointStrat = value;
			return value;
		} else {
			return this._validPointStrat;
		}
	}
	,
	_createValueStrat: null,
	createValueStrat: function (value) {
		if (arguments.length === 1) {
			this._createValueStrat = value;
			return value;
		} else {
			return this._createValueStrat;
		}
	}
	,
	interpolate: function (target, p, min, max, minFrame, maxFrame) {
		var removeFromTarget = new $.ig.List$1(this.$tKey, 0);
		var en = target.keys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			var minValue;
			var maxValue;
			var targetValue = target.item(key);
			var minContains = (function () { var $ret = min.tryGetValue(key, minValue); minValue = $ret.p1; return $ret.ret; }());
			var maxContains = (function () { var $ret = max.tryGetValue(key, maxValue); maxValue = $ret.p1; return $ret.ret; }());
			if (!minContains && !maxContains) {
				removeFromTarget.add(key);
			} else {
				this.interpolatePointStrat()(targetValue, p, minValue, maxValue, minFrame, maxFrame);
				if (!this.validPointStrat()(targetValue)) {
					removeFromTarget.add(key);
				}
			}
		}
		var en1 = removeFromTarget.getEnumerator();
		while (en1.moveNext()) {
			var key1 = en1.current();
			target.remove(key1);
		}
		var en2 = min.keys().getEnumerator();
		while (en2.moveNext()) {
			var key2 = en2.current();
			var minValue1 = min.item(key2);
			var maxValue1;
			var targetValue1;
			var $ret = max.tryGetValue(key2, maxValue1);
			maxValue1 = $ret.p1;
			var targetContains = (function () { var $ret = target.tryGetValue(key2, targetValue1); targetValue1 = $ret.p1; return $ret.ret; }());
			if (!targetContains) {
				targetValue1 = this.createValueStrat()();
				this.interpolatePointStrat()(targetValue1, p, minValue1, maxValue1, minFrame, maxFrame);
				if (!this.validPointStrat()(targetValue1)) {
					continue;
				}
				target.add(this.getKeyStrat()(targetValue1), targetValue1);
			}
		}
		var en3 = max.keys().getEnumerator();
		while (en3.moveNext()) {
			var key3 = en3.current();
			var maxValue2 = max.item(key3);
			var minValue2;
			var targetValue2;
			var minContains1 = (function () { var $ret = min.tryGetValue(key3, minValue2); minValue2 = $ret.p1; return $ret.ret; }());
			var targetContains1 = (function () { var $ret = target.tryGetValue(key3, targetValue2); targetValue2 = $ret.p1; return $ret.ret; }());
			if (!targetContains1 && !minContains1) {
				targetValue2 = this.createValueStrat()();
				this.interpolatePointStrat()(targetValue2, p, minValue2, maxValue2, minFrame, maxFrame);
				if (!this.validPointStrat()(targetValue2)) {
					continue;
				}
				target.add(this.getKeyStrat()(targetValue2), targetValue2);
			}
		}
	}
	,
	$type: new $.ig.Type('DictInterpolator$3', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('OwnedPoint', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
		this.columnValues({ __x: 0, __y: 0, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
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
	_ownerItem: null,
	ownerItem: function (value) {
		if (arguments.length === 1) {
			this._ownerItem = value;
			return value;
		} else {
			return this._ownerItem;
		}
	}
	,
	_columnValues: null,
	columnValues: function (value) {
		if (arguments.length === 1) {
			this._columnValues = value;
			return value;
		} else {
			return this._columnValues;
		}
	}
	,
	$type: new $.ig.Type('OwnedPoint', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ScatterFrame', 'ScatterFrameBase$1', {
	init: function () {
		$.ig.ScatterFrameBase$1.prototype.init.call(this, $.ig.ScatterFrame.prototype.$type);
		this.linePoints(new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0));
	},
	_linePoints: null,
	linePoints: function (value) {
		if (arguments.length === 1) {
			this._linePoints = value;
			return value;
		} else {
			return this._linePoints;
		}
	}
	,
	interpolateOverride: function (p, minFrame, maxFrame) {
		$.ig.ScatterFrameBase$1.prototype.interpolateOverride.call(this, p, minFrame, maxFrame);
		var min = $.ig.util.cast($.ig.ScatterFrame.prototype.$type, minFrame);
		var max = $.ig.util.cast($.ig.ScatterFrame.prototype.$type, maxFrame);
		if (min == null || max == null) {
			return;
		}
		this.ownedPointDictInterpolator().interpolate(this.linePoints(), p, min.linePoints(), max.linePoints(), min, max);
	}
	,
	$type: new $.ig.Type('ScatterFrame', $.ig.ScatterFrameBase$1.prototype.$type.specialize(-1))
}, true);

$.ig.ScatterFrame.prototype.$type.initSelfReferences();

$.ig.util.defType('ScatterSeries', 'ScatterBase', {
	createView: function () {
		return new $.ig.ScatterSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.ScatterBase.prototype.onViewCreated.call(this, view);
		this.scatterSeriesView(view);
	}
	,
	_scatterSeriesView: null,
	scatterSeriesView: function (value) {
		if (arguments.length === 1) {
			this._scatterSeriesView = value;
			return value;
		} else {
			return this._scatterSeriesView;
		}
	}
	,
	init: function () {
		$.ig.ScatterBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.ScatterSeries.prototype.$type);
		this._previousFrame = new $.ig.ScatterFrame();
		this._transitionFrame = new $.ig.ScatterFrame();
		this._currentFrame = new $.ig.ScatterFrame();
	},
	$type: new $.ig.Type('ScatterSeries', $.ig.ScatterBase.prototype.$type)
}, true);

$.ig.util.defType('ScatterBaseView', 'MarkerSeriesView', {
	_markerManager: null,
	markerManager: function (value) {
		if (arguments.length === 1) {
			this._markerManager = value;
			return value;
		} else {
			return this._markerManager;
		}
	}
	,
	_locations: null,
	locations: function (value) {
		if (arguments.length === 1) {
			this._locations = value;
			return value;
		} else {
			return this._locations;
		}
	}
	,
	_scatterModel: null,
	scatterModel: function (value) {
		if (arguments.length === 1) {
			this._scatterModel = value;
			return value;
		} else {
			return this._scatterModel;
		}
	}
	,
	_indexes: null,
	indexes: function (value) {
		if (arguments.length === 1) {
			this._indexes = value;
			return value;
		} else {
			return this._indexes;
		}
	}
	,
	init: function (model) {
		this.__markerMeasureInfo = null;
		$.ig.MarkerSeriesView.prototype.init.call(this, model);
		this.scatterModel(model);
		this.markers(new $.ig.HashPool$2($.ig.Object.prototype.$type, $.ig.Marker.prototype.$type));
		this.initMarkers(this.markers());
		this.trendLineManager(new $.ig.ScatterTrendLineManager());
	},
	_horizontalErrorBarsPath: null,
	horizontalErrorBarsPath: function (value) {
		if (arguments.length === 1) {
			this._horizontalErrorBarsPath = value;
			return value;
		} else {
			return this._horizontalErrorBarsPath;
		}
	}
	,
	_verticalErrorBarsPath: null,
	verticalErrorBarsPath: function (value) {
		if (arguments.length === 1) {
			this._verticalErrorBarsPath = value;
			return value;
		} else {
			return this._verticalErrorBarsPath;
		}
	}
	,
	_trendLineManager: null,
	trendLineManager: function (value) {
		if (arguments.length === 1) {
			this._trendLineManager = value;
			return value;
		} else {
			return this._trendLineManager;
		}
	}
	,
	onInit: function () {
		$.ig.MarkerSeriesView.prototype.onInit.call(this);
		this.markerManager(this.createMarkerManager());
		this.horizontalErrorBarsPath(new $.ig.Path());
		this.verticalErrorBarsPath(new $.ig.Path());
		if (!this.isThumbnailView()) {
			this.scatterModel().maximumMarkers(2000);
		}
	}
	,
	__markerMeasureInfo: null,
	getMarkerDesiredSize: function (marker) {
		if (this.__markerMeasureInfo == null) {
			this.__markerMeasureInfo = new $.ig.DataTemplateMeasureInfo();
			this.__markerMeasureInfo.context = this.context().getUnderlyingContext();
		}
		this.__markerMeasureInfo.width = marker.width();
		this.__markerMeasureInfo.height = marker.height();
		this.__markerMeasureInfo.data = marker.content();
		var template = marker.contentTemplate();
		if (template.measure() != null) {
			template.measure()(this.__markerMeasureInfo);
		}
		return new $.ig.Size(1, this.__markerMeasureInfo.width, this.__markerMeasureInfo.height);
	}
	,
	createMarkerManager: function () {
		var $self = this;
		var markerManager = new $.ig.NumericMarkerManager(1, function (o) { return $self.markers().item(o); }, function (i) { return $self.scatterModel().axisInfoCache().fastItemsSource().item(i); }, this.removeUnusedMarkers.runOn(this), this.getMarkerLocations.runOn(this), this.getActiveIndexes.runOn(this), function () { return $self.scatterModel().markerCollisionAvoidance(); });
		markerManager.getMarkerDesiredSize(this.getMarkerDesiredSize.runOn(this));
		return markerManager;
	}
	,
	removeUnusedMarkers: function (list) {
		this.scatterModel().removeUnusedMarkers(list, this.markers());
	}
	,
	getVisibleMarkersInternal: function () {
		return this.visibleMarkers();
	}
	,
	getMarkerLocationsInternal: function () {
		return this.locations();
	}
	,
	getMarkerLocations: function () {
		this.locations(this.scatterModel().getMarkerLocations(this, this.markers(), this.locations(), this.windowRect(), this.viewport()));
		return this.locations();
	}
	,
	getActiveIndexes: function () {
		this.indexes(this.scatterModel().getActiveIndexes(this.markers(), this.indexes()));
		return this.indexes();
	}
	,
	attachHorizontalErrorBars: function () {
	}
	,
	provideHorizontalErrorBarGeometry: function (horizontalErrorBarsGeometry) {
	}
	,
	attachVerticalErrorBars: function () {
	}
	,
	provideVerticalErrorBarGeometry: function (verticalErrorBarsGeometry) {
	}
	,
	updateTrendlineBrush: function () {
		if (this.scatterModel().trendLineBrush() != null) {
			this.scatterModel().actualTrendLineBrush(this.scatterModel().trendLineBrush());
		} else {
			this.scatterModel().actualTrendLineBrush(this.scatterModel().actualBrush());
		}
	}
	,
	_markers: null,
	markers: function (value) {
		if (arguments.length === 1) {
			this._markers = value;
			return value;
		} else {
			return this._markers;
		}
	}
	,
	doToAllMarkers: function (action) {
		this.markers().doToAll(action);
	}
	,
	hideErrorBars: function () {
	}
	,
	renderMarkersOverride: function (context, isHitContext) {
		$.ig.MarkerSeriesView.prototype.renderMarkersOverride.call(this, context, isHitContext);
		if (context.shouldRender()) {
			if (this.scatterModel().trendLineType() != $.ig.TrendLineType.prototype.none && !isHitContext) {
				var polyline = this.trendLineManager().trendPolyline();
				polyline.strokeThickness(this.scatterModel().trendLineThickness());
				polyline.__stroke = this.scatterModel().actualTrendLineBrush();
				polyline.strokeDashArray(this.scatterModel().trendLineDashArray());
				polyline.strokeDashCap(this.scatterModel().trendLineDashCap());
				context.renderPolyline(polyline);
			}
		}
	}
	,
	clearRendering: function (wipeClean) {
		if (wipeClean) {
			this.hideErrorBars();
			this.markers().clear();
		}
		this.trendLineManager().clearPoints();
		this.makeDirty();
	}
	,
	getDefaultTooltipTemplate: function () {
		var tooltipTemplate = "<div class='ui-chart-default-tooltip-content'><span";
		var nonTransparentOutline = this.model().actualOutline() != null && $.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.model().actualOutline().color()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) && this.model().actualOutline().color().a() > 0;
		if (nonTransparentOutline) {
			tooltipTemplate += " style='color:" + this.model().actualOutline().__fill + "'";
		}
		tooltipTemplate += ">" + this.scatterModel().title() + "</span><br/><span>" + "x: </span><span class='ui-priority-primary'>${item." + this.scatterModel().xMemberPath() + "}</span><br/><span>" + "y: </span><span class='ui-priority-primary'>${item." + this.scatterModel().yMemberPath() + "}</span></div>";
		return tooltipTemplate;
	}
	,
	$type: new $.ig.Type('ScatterBaseView', $.ig.MarkerSeriesView.prototype.$type)
}, true);

$.ig.util.defType('BubbleSeriesView', 'ScatterBaseView', {
	_bubbleModel: null,
	bubbleModel: function (value) {
		if (arguments.length === 1) {
			this._bubbleModel = value;
			return value;
		} else {
			return this._bubbleModel;
		}
	}
	,
	init: function (model) {
		$.ig.ScatterBaseView.prototype.init.call(this, model);
		this.bubbleModel(model);
		this.trendLineManager(new $.ig.ScatterTrendLineManager());
	},
	onInit: function () {
		$.ig.ScatterBaseView.prototype.onInit.call(this);
		if (!this.isThumbnailView()) {
			this.markerModel().markerType($.ig.MarkerType.prototype.automatic);
			this.model().legendItemBadgeTemplate((function () {
				var $ret = new $.ig.DataTemplate();
				$ret.render($.ig.LegendTemplates.prototype.pointBadgeTemplate);
				$ret.measure($.ig.LegendTemplates.prototype.legendItemBadgeMeasure);
				return $ret;
			}()));
		}
	}
	,
	createMarkerManager: function () {
		var $self = this;
		return new $.ig.BubbleMarkerManager(function (o) { return $self.markers().item(o); }, function (i) { return $self.scatterModel().axisInfoCache().fastItemsSource().item(i); }, this.removeUnusedMarkers.runOn(this), this.getMarkerLocations.runOn(this), this.getActiveIndexes.runOn(this));
	}
	,
	createMarkerSizes: function () {
		var bubbleManager = this.markerManager();
		this.bubbleModel().sizeBubbles(bubbleManager.actualMarkers(), bubbleManager.actualRadiusColumn(), this.viewport(), this == this.model().thumbnailView());
		this.makeDirty();
	}
	,
	setMarkerColors: function () {
		var bubbleManager = this.markerManager();
		this.bubbleModel().setMarkerColors(bubbleManager.actualMarkers());
	}
	,
	clearMarkerBrushes: function () {
		var bubbleManager = this.markerManager();
		var en = bubbleManager.actualMarkers().getEnumerator();
		while (en.moveNext()) {
			var marker = en.current();
			var markerContext = $.ig.util.cast($.ig.DataContext.prototype.$type, marker.content());
			if (markerContext != null) {
				markerContext.itemBrush(null);
			}
		}
	}
	,
	getDefaultTooltipTemplate: function () {
		var tooltipTemplate = "<div class='ui-chart-default-tooltip-content'><span";
		var nonTransparentOutline = this.model().actualOutline() != null && $.ig.Color.prototype.l_op_Inequality_Lifted($.ig.util.toNullable($.ig.Color.prototype.$type, this.model().actualOutline().color()), $.ig.util.toNullable($.ig.Color.prototype.$type, null)) && this.model().actualOutline().color().a() > 0;
		if (nonTransparentOutline) {
			tooltipTemplate += " style='color:" + this.model().actualOutline().__fill + "'";
		}
		tooltipTemplate += ">" + this.bubbleModel().title() + "</span><br/><span>" + "(${item." + this.bubbleModel().xMemberPath() + "}, ${item." + this.bubbleModel().yMemberPath() + "})</span>";
		if (!String.isNullOrEmpty(this.bubbleModel().radiusMemberPath())) {
			tooltipTemplate += "<span>, Radius: ${item." + this.bubbleModel().radiusMemberPath() + "}</span>";
		}
		tooltipTemplate += "</div>";
		return tooltipTemplate;
	}
	,
	$type: new $.ig.Type('BubbleSeriesView', $.ig.ScatterBaseView.prototype.$type)
}, true);

$.ig.util.defType('ScaleLegendView', 'LegendBaseView', {
	init: function (model) {
		this._scaleSize = new $.ig.Size();
		this._isDirty = false;
		$.ig.LegendBaseView.prototype.init.call(this, model);
		this.scaleModel(model);
		this.minText(new $.ig.TextBlock());
		this.maxText(new $.ig.TextBlock());
		this.legendScaleElement(new $.ig.Polygon());
	},
	_scaleModel: null,
	scaleModel: function (value) {
		if (arguments.length === 1) {
			this._scaleModel = value;
			return value;
		} else {
			return this._scaleModel;
		}
	}
	,
	_legendScaleElement: null,
	legendScaleElement: function (value) {
		if (arguments.length === 1) {
			this._legendScaleElement = value;
			return value;
		} else {
			return this._legendScaleElement;
		}
	}
	,
	_minText: null,
	minText: function (value) {
		if (arguments.length === 1) {
			this._minText = value;
			return value;
		} else {
			return this._minText;
		}
	}
	,
	_maxText: null,
	maxText: function (value) {
		if (arguments.length === 1) {
			this._maxText = value;
			return value;
		} else {
			return this._maxText;
		}
	}
	,
	restoreOriginalState: function () {
	}
	,
	detachContent: function () {
	}
	,
	getTransparentBrush: function () {
		return $.ig.Color.prototype.fromArgb(0, 0, 0, 0);
	}
	,
	buildGradient: function () {
		return new $.ig.GradientData();
	}
	,
	addGradientStop: function (gradient, color, colorOffset) {
		var data = gradient;
		data.gradientStops().add((function () {
			var $ret = new $.ig.GradientStopData();
			$ret.brush((function () {
				var $ret = new $.ig.Brush();
				$ret.color(color);
				return $ret;
			}()));
			$ret.offset(colorOffset);
			return $ret;
		}()));
	}
	,
	setScaleFill: function (legendScaleShapeElement, useSeriesBrush, gradient) {
		if (useSeriesBrush) {
			this.currBrush(this.scaleModel().series().actualMarkerBrush());
			this.currGradient(null);
		} else {
			this.currBrush(null);
			this.currGradient(gradient);
		}
		this.makeDirty();
	}
	,
	_currGradient: null,
	currGradient: function (value) {
		if (arguments.length === 1) {
			this._currGradient = value;
			return value;
		} else {
			return this._currGradient;
		}
	}
	,
	_scaleContext: null,
	scaleContext: function (value) {
		if (arguments.length === 1) {
			this._scaleContext = value;
			return value;
		} else {
			return this._scaleContext;
		}
	}
	,
	_scaleSize: null,
	scaleSize: function (value) {
		if (arguments.length === 1) {
			this._scaleSize = value;
			return value;
		} else {
			return this._scaleSize;
		}
	}
	,
	onContainerProvided: function (container) {
		$.ig.LegendBaseView.prototype.onContainerProvided.call(this, container);
		this.scaleContext(this.viewManager().getScaleContext(container));
		this.scaleSize(this.viewManager().getScaleContainerSize());
		this.makeDirty();
	}
	,
	_isDirty: false,
	makeDirty: function () {
		if (!this._isDirty) {
			this._isDirty = true;
			window.setTimeout(this.undirty.runOn(this), 0);
		}
	}
	,
	undirty: function () {
		if (this._isDirty) {
			this._isDirty = false;
			this.render();
		}
	}
	,
	render: function () {
		if (this.scaleContext() == null) {
			return;
		}
		this.refreshScaleShape();
	}
	,
	getDesiredWidth: function (element) {
		var tb = $.ig.util.cast($.ig.TextBlock.prototype.$type, element);
		if (tb != null && tb.text() != null) {
			return this.scaleContext().measureTextWidth(tb.text()) + $.ig.ScaleLegendView.prototype.tEXT_MARGIN;
		}
		return 0;
	}
	,
	getDesiredHeight: function (element) {
		return this.fontHeight() + $.ig.ScaleLegendView.prototype.tEXT_MARGIN;
	}
	,
	_fontHeight: 0,
	fontHeight: function (value) {
		if (arguments.length === 1) {
			this._fontHeight = value;
			return value;
		} else {
			return this._fontHeight;
		}
	}
	,
	_fontBrush: null,
	fontBrush: function (value) {
		if (arguments.length === 1) {
			this._fontBrush = value;
			return value;
		} else {
			return this._fontBrush;
		}
	}
	,
	refreshScaleShape: function () {
		if (this.scaleModel().series() == null || this.scaleModel().series().seriesViewer() == null || (this.currGradient() == null && this.currBrush() == null)) {
			return;
		}
		if (this.scaleContext().shouldRender()) {
			this.scaleContext().setFontInfo(this.scaleModel().series().seriesViewer().getFontInfo());
			this.fontHeight(this.scaleModel().series().seriesViewer().view().fontHeight());
			this.fontBrush(this.scaleModel().series().seriesViewer().getFontBrush());
			var minWidth = this.getDesiredWidth(this.minText());
			var maxWidth = this.getDesiredWidth(this.maxText());
			var textWidth = Math.max(minWidth, maxWidth) + 4;
			if (textWidth >= this.scaleSize().width()) {
				textWidth = 0;
			}
			var scaleWidth = this.scaleSize().width() - textWidth;
			var left = 2;
			var top = 2;
			scaleWidth = scaleWidth - 4;
			var scaleHeight = this.scaleSize().height() - 4;
			var textLeft = left + scaleWidth + 4;
			var textTop = top;
			var textHeight = scaleHeight;
			var point1 = { __x: left + (3 / 5) * scaleWidth, __y: top, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var point2 = { __x: left + (5 / 5) * scaleWidth, __y: top, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var point3 = { __x: left + (5 / 5) * scaleWidth, __y: scaleHeight, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var point4 = { __x: left, __y: scaleHeight, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var p = this.legendScaleElement();
			p.points().clear();
			p.points().add(point1);
			p.points().add(point2);
			p.points().add(point3);
			p.points().add(point4);
			this.minText().canvasLeft(textLeft);
			this.minText().canvasTop(textTop);
			this.minText().fill(this.fontBrush());
			this.maxText().canvasLeft(textLeft);
			this.maxText().canvasTop(textTop + textHeight - (this.getDesiredHeight(this.minText())));
			this.maxText().fill(this.fontBrush());
			this.scaleContext().clearRectangle(0, 0, this.scaleSize().width(), this.scaleSize().height());
			if (this.currGradient() == null && this.currBrush() != null) {
				p.__fill = this.currBrush();
				this.scaleContext().renderPolygon(p);
			} else {
				this.viewManager().renderGradientShape(this.scaleContext(), p, this.currGradient(), new $.ig.Rect(0, top, left, scaleWidth, scaleHeight));
			}
			if (textWidth > 0) {
				this.scaleContext().renderTextBlock(this.minText());
				this.scaleContext().renderTextBlock(this.maxText());
			}
		}
	}
	,
	_currBrush: null,
	currBrush: function (value) {
		if (arguments.length === 1) {
			this._currBrush = value;
			return value;
		} else {
			return this._currBrush;
		}
	}
	,
	onSizeChanged: function () {
		$.ig.LegendBaseView.prototype.onSizeChanged.call(this);
	}
	,
	$type: new $.ig.Type('ScaleLegendView', $.ig.LegendBaseView.prototype.$type)
}, true);

$.ig.util.defType('ScatterSeriesView', 'ScatterBaseView', {
	_scatterSeriesModel: null,
	scatterSeriesModel: function (value) {
		if (arguments.length === 1) {
			this._scatterSeriesModel = value;
			return value;
		} else {
			return this._scatterSeriesModel;
		}
	}
	,
	init: function (model) {
		$.ig.ScatterBaseView.prototype.init.call(this, model);
		this.scatterSeriesModel(model);
	},
	onInit: function () {
		$.ig.ScatterBaseView.prototype.onInit.call(this);
		if (!this.isThumbnailView()) {
			this.markerModel().markerType($.ig.MarkerType.prototype.automatic);
			this.model().legendItemBadgeTemplate((function () {
				var $ret = new $.ig.DataTemplate();
				$ret.render($.ig.LegendTemplates.prototype.pointBadgeTemplate);
				$ret.measure($.ig.LegendTemplates.prototype.legendItemBadgeMeasure);
				return $ret;
			}()));
		}
	}
	,
	applyDropShadowDefaultSettings: function () {
		var color = new $.ig.Color();
		color.colorString("rgba(95,95,95,0.5)");
		this.model().shadowColor(color);
		this.model().shadowBlur(3);
		this.model().shadowOffsetX(2);
		this.model().shadowOffsetY(2);
		this.model().useSingleShadow(false);
	}
	,
	$type: new $.ig.Type('ScatterSeriesView', $.ig.ScatterBaseView.prototype.$type)
}, true);

$.ig.EnableErrorBars.prototype.none = 0;
$.ig.EnableErrorBars.prototype.both = 1;
$.ig.EnableErrorBars.prototype.positive = 2;
$.ig.EnableErrorBars.prototype.negative = 3;

$.ig.BrushSelectionMode.prototype.select = 0;
$.ig.BrushSelectionMode.prototype.interpolate = 1;

$.ig.CollisionAvoidanceType.prototype.none = 0;
$.ig.CollisionAvoidanceType.prototype.omit = 1;
$.ig.CollisionAvoidanceType.prototype.fade = 2;
$.ig.CollisionAvoidanceType.prototype.omitAndShift = 3;
$.ig.CollisionAvoidanceType.prototype.fadeAndShift = 4;

$.ig.HighDensityScatterSeries.prototype.xAxisPropertyName = "XAxis";
$.ig.HighDensityScatterSeries.prototype.yAxisPropertyName = "YAxis";
$.ig.HighDensityScatterSeries.prototype.xMemberPathPropertyName = "XMemberPath";
$.ig.HighDensityScatterSeries.prototype.xColumnPropertyName = "XColumn";
$.ig.HighDensityScatterSeries.prototype.yMemberPathPropertyName = "YMemberPath";
$.ig.HighDensityScatterSeries.prototype.yColumnPropertyName = "YColumn";
$.ig.HighDensityScatterSeries.prototype.useBruteForcePropertyName = "UseBruteForce";
$.ig.HighDensityScatterSeries.prototype.progressiveLoadPropertyName = "ProgressiveLoad";
$.ig.HighDensityScatterSeries.prototype.mouseOverEnabledPropertyName = "MouseOverEnabled";
$.ig.HighDensityScatterSeries.prototype.maxRenderDepthPropertyName = "MaxRenderDepth";
$.ig.HighDensityScatterSeries.prototype.heatMinimumPropertyName = "HeatMinimum";
$.ig.HighDensityScatterSeries.prototype.heatMaximumPropertyName = "HeatMaximum";
$.ig.HighDensityScatterSeries.prototype.heatMinimumColorPropertyName = "HeatMinimumColor";
$.ig.HighDensityScatterSeries.prototype.heatMaximumColorPropertyName = "HeatMaximumColor";
$.ig.HighDensityScatterSeries.prototype.pointExtentPropertyName = "PointExtent";
$.ig.HighDensityScatterSeries.prototype.xAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.xAxisPropertyName, $.ig.NumericXAxis.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.xAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.yAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.yAxisPropertyName, $.ig.NumericYAxis.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.yAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.xMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.xMemberPathPropertyName, String, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.xMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.yMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.yMemberPathPropertyName, String, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.yMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.useBruteForceProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.useBruteForcePropertyName, $.ig.Boolean.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, false, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.useBruteForcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.progressiveLoadProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.progressiveLoadPropertyName, $.ig.Boolean.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, true, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.progressiveLoadPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.mouseOverEnabledProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.mouseOverEnabledPropertyName, $.ig.Boolean.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, false, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.mouseOverEnabledPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.maxRenderDepthProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.maxRenderDepthPropertyName, $.ig.Number.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 0x7FFFFFFF, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.maxRenderDepthPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.heatMinimumProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.heatMinimumPropertyName, Number, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.heatMinimumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.heatMaximumProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.heatMaximumPropertyName, Number, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 50, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.heatMaximumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.heatMinimumColorProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.heatMinimumColorPropertyName, $.ig.Color.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.heatMinimumColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.heatMaximumColorProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.heatMaximumColorPropertyName, $.ig.Color.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.heatMaximumColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.HighDensityScatterSeries.prototype.pointExtentProperty = $.ig.DependencyProperty.prototype.register($.ig.HighDensityScatterSeries.prototype.pointExtentPropertyName, $.ig.Number.prototype.$type, $.ig.HighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.HighDensityScatterSeries.prototype.pointExtentPropertyName, e.oldValue(), e.newValue());
}));

$.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStylePropertyName = "DefaultErrorBarStyle";
$.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStylePropertyName, $.ig.Style.prototype.$type, $.ig.ErrorBarSettingsBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ErrorBarSettingsBase.prototype.defaultErrorBarStylePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ScatterErrorBarSettings.prototype._enableErrorBarsHorizontalPropertyName = "EnableErrorBarsHorizontal";
$.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorReferencePropertyName = "HorizontalCalculatorReference";
$.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorPropertyName = "HorizontalCalculator";
$.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthPropertyName = "HorizontalErrorBarCapLength";
$.ig.ScatterErrorBarSettings.prototype._horizontalStrokePropertyName = "HorizontalStroke";
$.ig.ScatterErrorBarSettings.prototype._horizontalStrokeThicknessPropertyName = "HorizontalStrokeThickness";
$.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStylePropertyName = "HorizontalErrorBarStyle";
$.ig.ScatterErrorBarSettings.prototype._enableErrorBarsVerticalPropertyName = "EnableErrorBarsVertical";
$.ig.ScatterErrorBarSettings.prototype._verticalCalculatorReferencePropertyName = "VerticalCalculatorReference";
$.ig.ScatterErrorBarSettings.prototype._verticalCalculatorPropertyName = "VerticalCalculator";
$.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthPropertyName = "VerticalErrorBarCapLength";
$.ig.ScatterErrorBarSettings.prototype._verticalStrokePropertyName = "VerticalStroke";
$.ig.ScatterErrorBarSettings.prototype._verticalStrokeThicknessPropertyName = "VerticalStrokeThickness";
$.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStylePropertyName = "VerticalErrorBarStyle";
$.ig.ScatterErrorBarSettings.prototype.enableErrorBarsHorizontalProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._enableErrorBarsHorizontalPropertyName, $.ig.EnableErrorBars.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.EnableErrorBars.prototype.getBox($.ig.EnableErrorBars.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._enableErrorBarsHorizontalPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorReferenceProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorReferencePropertyName, $.ig.ErrorBarCalculatorReference.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.ErrorBarCalculatorReference.prototype.getBox($.ig.ErrorBarCalculatorReference.prototype.x), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorReferencePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalCalculatorProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorPropertyName, $.ig.IErrorBarCalculator.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._horizontalCalculatorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthPropertyName, $.ig.Number.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, 6, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarCapLengthPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._horizontalStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._horizontalStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._horizontalStrokeThicknessPropertyName, Number, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._horizontalStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStylePropertyName, $.ig.Style.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype.horizontalErrorBarStylePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.enableErrorBarsVerticalProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._enableErrorBarsVerticalPropertyName, $.ig.EnableErrorBars.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.EnableErrorBars.prototype.getBox($.ig.EnableErrorBars.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._enableErrorBarsVerticalPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalCalculatorReferenceProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._verticalCalculatorReferencePropertyName, $.ig.ErrorBarCalculatorReference.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.ErrorBarCalculatorReference.prototype.getBox($.ig.ErrorBarCalculatorReference.prototype.y), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._verticalCalculatorReferencePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalCalculatorProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._verticalCalculatorPropertyName, $.ig.IErrorBarCalculator.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._verticalCalculatorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthPropertyName, $.ig.Number.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, 6, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarCapLengthPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalStrokeProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._verticalStrokePropertyName, $.ig.Brush.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._verticalStrokePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalStrokeThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype._verticalStrokeThicknessPropertyName, Number, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype._verticalStrokeThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStylePropertyName, $.ig.Style.prototype.$type, $.ig.ScatterErrorBarSettings.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterErrorBarSettings.prototype.verticalErrorBarStylePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ScatterBase.prototype.xAxisPropertyName = "XAxis";
$.ig.ScatterBase.prototype.yAxisPropertyName = "YAxis";
$.ig.ScatterBase.prototype.xMemberPathPropertyName = "XMemberPath";
$.ig.ScatterBase.prototype.xColumnPropertyName = "XColumn";
$.ig.ScatterBase.prototype.yMemberPathPropertyName = "YMemberPath";
$.ig.ScatterBase.prototype.yColumnPropertyName = "YColumn";
$.ig.ScatterBase.prototype.markerCollisionAvoidancePropertyName = "MarkerCollisionAvoidance";
$.ig.ScatterBase.prototype.maximumMarkersPropertyName = "MaximumMarkers";
$.ig.ScatterBase.prototype._errorBarSettingsPropertyName = "ErrorBarSettings";
$.ig.ScatterBase.prototype.xAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.xAxisPropertyName, $.ig.NumericXAxis.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.xAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.yAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.yAxisPropertyName, $.ig.NumericYAxis.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.yAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.xMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.xMemberPathPropertyName, String, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.xMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.yMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.yMemberPathPropertyName, String, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.yMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineTypePropertyName, $.ig.TrendLineType.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.TrendLineType.prototype.getBox($.ig.TrendLineType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.actualTrendLineBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineActualBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineActualBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineThicknessProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineThicknessPropertyName, Number, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, 1.5, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineThicknessPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineDashCapProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineDashCapPropertyName, $.ig.PenLineCap.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.PenLineCap.prototype.getBox($.ig.PenLineCap.prototype.flat), function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineDashCapPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineDashArrayProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineDashArrayPropertyName, $.ig.DoubleCollection.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineDashArrayPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLinePeriodProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLinePeriodPropertyName, $.ig.Number.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, 7, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLinePeriodPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.markerCollisionAvoidanceProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.markerCollisionAvoidancePropertyName, $.ig.CollisionAvoidanceType.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.CollisionAvoidanceType.prototype.getBox($.ig.CollisionAvoidanceType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.markerCollisionAvoidancePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.trendLineZIndexProperty = $.ig.DependencyProperty.prototype.register($.ig.Series.prototype.trendLineZIndexPropertyName, $.ig.Number.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, 1001, function (sender, e) {
	(sender).raisePropertyChanged($.ig.Series.prototype.trendLineZIndexPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.maximumMarkersProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype.maximumMarkersPropertyName, $.ig.Number.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, 400, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype.maximumMarkersPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ScatterBase.prototype.errorBarSettingsProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterBase.prototype._errorBarSettingsPropertyName, $.ig.ScatterErrorBarSettings.prototype.$type, $.ig.ScatterBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterBase.prototype._errorBarSettingsPropertyName, e.oldValue(), e.newValue());
}));

$.ig.SizeScale.prototype.minimumValuePropertyName = "MinimumValue";
$.ig.SizeScale.prototype.maximumValuePropertyName = "MaximumValue";
$.ig.SizeScale.prototype.isLogarithmicPropertyName = "IsLogarithmic";
$.ig.SizeScale.prototype.logarithmBasePropertyName = "LogarithmBase";
$.ig.SizeScale.prototype.minimumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.SizeScale.prototype.minimumValuePropertyName, Number, $.ig.SizeScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	(o).raisePropertyChanged($.ig.SizeScale.prototype.minimumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.SizeScale.prototype.maximumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.SizeScale.prototype.maximumValuePropertyName, Number, $.ig.SizeScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	(o).raisePropertyChanged($.ig.SizeScale.prototype.maximumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.SizeScale.prototype.isLogarithmicProperty = $.ig.DependencyProperty.prototype.register($.ig.SizeScale.prototype.isLogarithmicPropertyName, $.ig.Boolean.prototype.$type, $.ig.SizeScale.prototype.$type, new $.ig.PropertyMetadata(2, false, function (o, e) {
	(o).raisePropertyChanged($.ig.SizeScale.prototype.isLogarithmicPropertyName, e.oldValue(), e.newValue());
}));
$.ig.SizeScale.prototype.logarithmBaseProperty = $.ig.DependencyProperty.prototype.register($.ig.SizeScale.prototype.logarithmBasePropertyName, $.ig.Number.prototype.$type, $.ig.SizeScale.prototype.$type, new $.ig.PropertyMetadata(2, 10, function (o, e) {
	(o).raisePropertyChanged($.ig.SizeScale.prototype.logarithmBasePropertyName, e.oldValue(), e.newValue());
}));

$.ig.BubbleSeries.prototype.radiusMemberPathPropertyName = "RadiusMemberPath";
$.ig.BubbleSeries.prototype.radiusColumnPropertyName = "RadiusColumn";
$.ig.BubbleSeries.prototype.radiusScalePropertyName = "RadiusScale";
$.ig.BubbleSeries.prototype.labelMemberPathPropertyName = "LabelMemberPath";
$.ig.BubbleSeries.prototype.labelColumnPropertyName = "LabelColumn";
$.ig.BubbleSeries.prototype.fillMemberPathPropertyName = "FillMemberPath";
$.ig.BubbleSeries.prototype.fillScalePropertyName = "FillScale";
$.ig.BubbleSeries.prototype.fillColumnPropertyName = "FillColumn";
$.ig.BubbleSeries.prototype.radiusMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.BubbleSeries.prototype.radiusMemberPathPropertyName, String, $.ig.BubbleSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.BubbleSeries.prototype.radiusMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.BubbleSeries.prototype.radiusScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.BubbleSeries.prototype.radiusScalePropertyName, $.ig.SizeScale.prototype.$type, $.ig.BubbleSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.BubbleSeries.prototype.radiusScalePropertyName, e.oldValue(), e.newValue());
}));
$.ig.BubbleSeries.prototype.labelMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.BubbleSeries.prototype.labelMemberPathPropertyName, String, $.ig.BubbleSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.BubbleSeries.prototype.labelMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.BubbleSeries.prototype.fillMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.BubbleSeries.prototype.fillMemberPathPropertyName, String, $.ig.BubbleSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.BubbleSeries.prototype.fillMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.BubbleSeries.prototype.fillScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.BubbleSeries.prototype.fillScalePropertyName, $.ig.BrushScale.prototype.$type, $.ig.BubbleSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.BubbleSeries.prototype.fillScalePropertyName, e.oldValue(), e.newValue());
}));

$.ig.CustomPaletteBrushScale.prototype.brushSelectionModePropertyName = "BrushSelectionMode";
$.ig.CustomPaletteBrushScale.prototype.brushSelectionModeProperty = $.ig.DependencyProperty.prototype.register($.ig.CustomPaletteBrushScale.prototype.brushSelectionModePropertyName, $.ig.BrushSelectionMode.prototype.$type, $.ig.CustomPaletteBrushScale.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.BrushSelectionMode.prototype.getBox($.ig.BrushSelectionMode.prototype.select), function (o, e) {
	(o).raisePropertyChanged($.ig.CustomPaletteBrushScale.prototype.brushSelectionModePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ValueBrushScale.prototype.minimumValuePropertyName = "MinimumValue";
$.ig.ValueBrushScale.prototype.maximumValuePropertyName = "MaximumValue";
$.ig.ValueBrushScale.prototype.isLogarithmicPropertyName = "IsLogarithmic";
$.ig.ValueBrushScale.prototype.logarithmBasePropertyName = "LogarithmBase";
$.ig.ValueBrushScale.prototype.minimumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.ValueBrushScale.prototype.minimumValuePropertyName, Number, $.ig.ValueBrushScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	(o).raisePropertyChanged($.ig.ValueBrushScale.prototype.minimumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ValueBrushScale.prototype.maximumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.ValueBrushScale.prototype.maximumValuePropertyName, Number, $.ig.ValueBrushScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (o, e) {
	(o).raisePropertyChanged($.ig.ValueBrushScale.prototype.maximumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ValueBrushScale.prototype.isLogarithmicProperty = $.ig.DependencyProperty.prototype.register($.ig.ValueBrushScale.prototype.isLogarithmicPropertyName, $.ig.Boolean.prototype.$type, $.ig.ValueBrushScale.prototype.$type, new $.ig.PropertyMetadata(2, false, function (o, e) {
	(o).raisePropertyChanged($.ig.ValueBrushScale.prototype.isLogarithmicPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ValueBrushScale.prototype.logarithmBaseProperty = $.ig.DependencyProperty.prototype.register($.ig.ValueBrushScale.prototype.logarithmBasePropertyName, $.ig.Number.prototype.$type, $.ig.ValueBrushScale.prototype.$type, new $.ig.PropertyMetadata(2, 10, function (o, e) {
	(o).raisePropertyChanged($.ig.ValueBrushScale.prototype.logarithmBasePropertyName, e.oldValue(), e.newValue());
}));

$.ig.DefaultFlattener.prototype._flattenerChunking = 512;

$.ig.ScaleLegend.prototype.parentVisibilityPropertyName = "ParentVisibility";
$.ig.ScaleLegend.prototype.seriesMarkerBrushPropertyName = "SeriesMarkerBrush";
$.ig.ScaleLegend.prototype.parentVisibilityProperty = $.ig.DependencyProperty.prototype.register($.ig.ScaleLegend.prototype.parentVisibilityPropertyName, $.ig.Visibility.prototype.$type, $.ig.ScaleLegend.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Visibility.prototype.getBox($.ig.Visibility.prototype.visible), function (o, e) {
	if ($.ig.util.getEnumValue(e.newValue()) != $.ig.Visibility.prototype.visible) {
		(o).restoreOriginalState();
	} else {
		(o).renderLegend();
	}
}));
$.ig.ScaleLegend.prototype.seriesMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.ScaleLegend.prototype.seriesMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.ScaleLegend.prototype.$type, new $.ig.PropertyMetadata(1, function (o, e) {
	(o).renderLegend();
}));

$.ig.ScaleLegendView.prototype._legendScaleElementName = "LegendScale";
$.ig.ScaleLegendView.prototype.tEXT_MARGIN = 0;

} (jQuery));



(function ($) {
$.ig = $.ig || {};
var $$t = {}
$.ig.$currDefinitions = $$t;
$.ig.util.bulkDefine(["HostSeriesView$1:a", 
"SeriesView:b", 
"Object:c", 
"Type:d", 
"Boolean:e", 
"ValueType:f", 
"Void:g", 
"IConvertible:h", 
"IFormatProvider:i", 
"Number:j", 
"String:k", 
"IComparable:l", 
"Number:m", 
"IComparable$1:n", 
"IEquatable$1:o", 
"Number:p", 
"Number:q", 
"Number:r", 
"NumberStyles:s", 
"Enum:t", 
"Array:u", 
"IList:v", 
"ICollection:w", 
"IEnumerable:x", 
"IEnumerator:y", 
"NotSupportedException:z", 
"Error:aa", 
"Number:ab", 
"String:ac", 
"StringComparison:ad", 
"RegExp:ae", 
"CultureInfo:af", 
"DateTimeFormatInfo:ag", 
"Calendar:ah", 
"Date:ai", 
"Number:aj", 
"DayOfWeek:ak", 
"DateTimeKind:al", 
"CalendarWeekRule:am", 
"NumberFormatInfo:an", 
"CompareInfo:ao", 
"CompareOptions:ap", 
"IEnumerable$1:aq", 
"IEnumerator$1:ar", 
"IDisposable:as", 
"StringSplitOptions:at", 
"Number:au", 
"Number:av", 
"Number:aw", 
"Number:ax", 
"Number:ay", 
"Number:az", 
"Assembly:a0", 
"Stream:a1", 
"SeekOrigin:a2", 
"RuntimeTypeHandle:a3", 
"MethodInfo:a4", 
"MethodBase:a5", 
"MemberInfo:a6", 
"ParameterInfo:a7", 
"TypeCode:a8", 
"ConstructorInfo:a9", 
"PropertyInfo:ba", 
"ISchedulableRender:bb", 
"IProvidesViewport:bc", 
"Rect:bd", 
"Size:be", 
"Point:bf", 
"Math:bg", 
"Series:bh", 
"Control:bi", 
"FrameworkElement:bj", 
"UIElement:bk", 
"DependencyObject:bl", 
"Dictionary:bm", 
"DependencyProperty:bn", 
"PropertyMetadata:bo", 
"PropertyChangedCallback:bp", 
"MulticastDelegate:bq", 
"IntPtr:br", 
"DependencyPropertyChangedEventArgs:bs", 
"DependencyPropertiesCollection:bt", 
"UnsetValue:bu", 
"Script:bv", 
"Binding:bw", 
"PropertyPath:bx", 
"Transform:by", 
"Visibility:bz", 
"Style:b0", 
"Thickness:b1", 
"HorizontalAlignment:b2", 
"VerticalAlignment:b3", 
"INotifyPropertyChanged:b4", 
"PropertyChangedEventHandler:b5", 
"PropertyChangedEventArgs:b6", 
"DoubleAnimator:b7", 
"EasingFunctionHandler:b8", 
"Action$1:b9", 
"Callback:ca", 
"Delegate:cb", 
"Interlocked:cc", 
"Canvas:cd", 
"Panel:ce", 
"UIElementCollection:cf", 
"ObservableCollection$1:cg", 
"List$1:ch", 
"IList$1:ci", 
"ICollection$1:cj", 
"IArray:ck", 
"IArrayList:cl", 
"Array:cm", 
"CompareCallback:cn", 
"Func$3:co", 
"Comparer$1:cp", 
"IComparer:cq", 
"IComparer$1:cr", 
"DefaultComparer$1:cs", 
"Comparison$1:ct", 
"ReadOnlyCollection$1:cu", 
"Predicate$1:cv", 
"NotImplementedException:cw", 
"INotifyCollectionChanged:cx", 
"NotifyCollectionChangedEventHandler:cy", 
"NotifyCollectionChangedEventArgs:cz", 
"EventArgs:c0", 
"NotifyCollectionChangedAction:c1", 
"Dictionary$2:c2", 
"IDictionary$2:c3", 
"IDictionary:c4", 
"Func$2:c5", 
"KeyValuePair$2:c6", 
"Enumerable:c7", 
"Thread:c8", 
"ThreadStart:c9", 
"IOrderedEnumerable$1:da", 
"SortedList$1:db", 
"ArgumentNullException:dc", 
"IEqualityComparer$1:dd", 
"EqualityComparer$1:de", 
"IEqualityComparer:df", 
"DefaultEqualityComparer$1:dg", 
"InvalidOperationException:dh", 
"ArgumentException:di", 
"ContentInfo:dj", 
"Axis:dk", 
"AxisView:dl", 
"Path:dm", 
"Shape:dn", 
"Brush:dp", 
"Color:dq", 
"DoubleCollection:dr", 
"Geometry:ds", 
"GeometryType:dt", 
"SeriesViewer:du", 
"SeriesViewerView:dv", 
"CanvasRenderScheduler:dw", 
"window:dx", 
"RenderingContext:dy", 
"IRenderer:dz", 
"Rectangle:d0", 
"TextBlock:d1", 
"Polygon:d2", 
"PointCollection:d3", 
"Polyline:d4", 
"DataTemplateRenderInfo:d5", 
"DataTemplatePassInfo:d6", 
"ContentControl:d7", 
"DataTemplate:d8", 
"DataTemplateRenderHandler:d9", 
"DataTemplateMeasureHandler:ea", 
"DataTemplateMeasureInfo:eb", 
"DataTemplatePassHandler:ec", 
"Line:ed", 
"FontInfo:ee", 
"XamOverviewPlusDetailPane:ef", 
"XamOverviewPlusDetailPaneView:eg", 
"XamOverviewPlusDetailPaneViewManager:eh", 
"JQueryObject:ei", 
"Element:ej", 
"ElementAttributeCollection:ek", 
"ElementCollection:el", 
"WebStyle:em", 
"ElementNodeType:en", 
"Document:eo", 
"EventListener:ep", 
"IElementEventHandler:eq", 
"ElementEventHandler:er", 
"ElementAttribute:es", 
"JQueryPosition:et", 
"JQueryCallback:eu", 
"JQueryEvent:ev", 
"JQueryUICallback:ew", 
"EventProxy:ex", 
"ModifierKeys:ey", 
"MouseWheelHandler:ez", 
"GestureHandler:e0", 
"ZoomGestureHandler:e1", 
"FlingGestureHandler:e2", 
"ContactHandler:e3", 
"TouchHandler:e4", 
"MouseOverHandler:e5", 
"MouseHandler:e6", 
"KeyHandler:e7", 
"Key:e8", 
"JQuery:e9", 
"JQueryDeferred:fa", 
"JQueryPromise:fb", 
"Action:fc", 
"CanvasViewRenderer:fd", 
"CanvasContext2D:fe", 
"CanvasContext:ff", 
"TextMetrics:fg", 
"ImageData:fh", 
"CanvasElement:fi", 
"Gradient:fj", 
"LinearGradientBrush:fk", 
"GradientStop:fl", 
"GeometryGroup:fm", 
"GeometryCollection:fn", 
"FillRule:fo", 
"PathGeometry:fp", 
"PathFigureCollection:fq", 
"LineGeometry:fr", 
"RectangleGeometry:fs", 
"EllipseGeometry:ft", 
"ArcSegment:fu", 
"PathSegment:fv", 
"PathSegmentType:fw", 
"SweepDirection:fx", 
"PathFigure:fy", 
"PathSegmentCollection:fz", 
"LineSegment:f0", 
"PolyLineSegment:f1", 
"BezierSegment:f2", 
"PolyBezierSegment:f3", 
"GeometryUtil:f4", 
"Tuple$2:f5", 
"TransformGroup:f6", 
"TransformCollection:f7", 
"TranslateTransform:f8", 
"RotateTransform:f9", 
"ScaleTransform:ga", 
"DivElement:gb", 
"BaseDOMEventProxy:gc", 
"DOMEventProxy:gd", 
"MSGesture:ge", 
"MouseEventArgs:gf", 
"ImageElement:gg", 
"RectUtil:gh", 
"MathUtil:gi", 
"RuntimeHelpers:gj", 
"RuntimeFieldHandle:gk", 
"PropertyChangedEventArgs$1:gl", 
"InteractionState:gm", 
"OverviewPlusDetailPaneMode:gn", 
"IOverviewPlusDetailControl:go", 
"EventHandler$1:gp", 
"OverviewPlusDetailViewportHost:gq", 
"XamDataChart:gr", 
"GridMode:gs", 
"BrushCollection:gt", 
"InterpolationMode:gu", 
"Random:gv", 
"ColorUtil:gw", 
"AxisCollection:gx", 
"XamDataChartView:gy", 
"SeriesViewerViewManager:gz", 
"AxisTitlePosition:g0", 
"PointerTooltipStyle:g1", 
"CanvasGestureDOMEventProxy:g2", 
"TouchPointInfo:g3", 
"DOMExecutionContext:g4", 
"IExecutionContext:g5", 
"ExecutionContextExecuteCallback:g6", 
"TouchGestureRecognizer:g7", 
"TouchGestureState:g8", 
"TouchVelocityTracker:g9", 
"TouchHistoryItem:ha", 
"TouchVelocityReading:hb", 
"TouchGestureEventHandler:hc", 
"TouchGestureEventArgs:hd", 
"CancelableTouchGestureEventHandler:he", 
"CssHelper:hf", 
"CssGradientUtil:hg", 
"FontUtil:hh", 
"TileZoomTile:hi", 
"TileZoomTileInfo:hj", 
"TileZoomTileCache:hk", 
"TileZoomManager:hl", 
"RectChangedEventHandler:hm", 
"RectChangedEventArgs:hn", 
"Debug:ho", 
"TileZoomInfo:hp", 
"LinkedList$1:hq", 
"LinkedListNode$1:hr", 
"RenderSurface:hs", 
"FragmentBase:ht", 
"HorizontalAnchoredCategorySeries:hu", 
"AnchoredCategorySeries:hv", 
"CategorySeries:hw", 
"MarkerSeries:hx", 
"MarkerSeriesView:hy", 
"Marker:hz", 
"DataContext:h0", 
"MarkerTemplates:h1", 
"HashPool$2:h2", 
"IHashPool$2:h3", 
"IPool$1:h4", 
"Func$1:h5", 
"Pool$1:h6", 
"IIndexedPool$1:h7", 
"MarkerType:h8", 
"SeriesVisualData:h9", 
"PrimitiveVisualDataList:ia", 
"IVisualData:ib", 
"PrimitiveVisualData:ic", 
"PrimitiveAppearanceData:id", 
"BrushAppearanceData:ie", 
"StringBuilder:ig", 
"Environment:ih", 
"AppearanceHelper:ii", 
"LinearGradientBrushAppearanceData:ij", 
"GradientStopAppearanceData:ik", 
"SolidBrushAppearanceData:il", 
"GeometryData:im", 
"GetPointsSettings:io", 
"EllipseGeometryData:ip", 
"RectangleGeometryData:iq", 
"LineGeometryData:ir", 
"PathGeometryData:is", 
"PathFigureData:it", 
"SegmentData:iu", 
"LineSegmentData:iv", 
"PolylineSegmentData:iw", 
"ArcSegmentData:ix", 
"PolyBezierSegmentData:iy", 
"BezierSegmentData:iz", 
"LabelAppearanceData:i0", 
"ShapeTags:i1", 
"PointerTooltipVisualDataList:i2", 
"MarkerVisualDataList:i3", 
"MarkerVisualData:i4", 
"PointerTooltipVisualData:i5", 
"RectangleVisualData:i6", 
"PolygonVisualData:i7", 
"PolyLineVisualData:i8", 
"IFastItemsSource:i9", 
"IFastItemColumn$1:ja", 
"IFastItemColumnPropertyName:jb", 
"FastItemsSourceEventArgs:jc", 
"FastItemsSourceEventAction:jd", 
"IHasCategoryModePreference:je", 
"IHasCategoryAxis:jf", 
"CategoryAxisBase:jg", 
"ICategoryScaler:jh", 
"IScaler:ji", 
"ScalerParams:jj", 
"CategoryMode:jk", 
"CategoryAxisBaseView:jl", 
"IFastItemsSourceProvider:jm", 
"AxisRangeChangedEventArgs:jn", 
"AxisLabelSettings:jo", 
"AxisLabelsLocation:jp", 
"PropertyUpdatedEventHandler:jq", 
"PropertyUpdatedEventArgs:jr", 
"ISupportsErrorBars:js", 
"CategoryFramePreparer:jt", 
"CategoryFramePreparerBase:ju", 
"FramePreparer:jv", 
"ISupportsMarkers:jw", 
"DefaultSupportsMarkers:jx", 
"DefaultProvidesViewport:jy", 
"DefaultSupportsErrorBars:jz", 
"Frame:j0", 
"BrushUtil:j1", 
"IBucketizer:j2", 
"IIsCategoryBased:j3", 
"IDetectsCollisions:j4", 
"CategoryFrame:j5", 
"PreparationParams:j6", 
"ISortingAxis:j7", 
"CategoryYAxis:j8", 
"CategoryYAxisView:j9", 
"AxisOrientation:ka", 
"NumericScaler:kb", 
"NumericAxisBase:kc", 
"NumericAxisBaseView:kd", 
"NumericAxisRenderer:ke", 
"AxisRendererBase:kf", 
"ShouldRenderHandler:kg", 
"ScaleValueHandler:kh", 
"AxisRenderingParametersBase:ki", 
"RangeInfo:kj", 
"TickmarkValues:kk", 
"TickmarkValuesInitializationParameters:kl", 
"Func$4:km", 
"GetGroupCenterHandler:kn", 
"GetUnscaledGroupCenterHandler:ko", 
"PathRenderingInfo:kp", 
"RenderStripHandler:kq", 
"RenderLineHandler:kr", 
"ShouldRenderLinesHandler:ks", 
"ShouldRenderContentHandler:kt", 
"RenderAxisLineHandler:ku", 
"DetermineCrossingValueHandler:kv", 
"ShouldRenderLabelHandler:kw", 
"GetLabelLocationHandler:kx", 
"LabelPosition:ky", 
"TransformToLabelValueHandler:kz", 
"AxisLabelManager:k0", 
"AxisLabelPanelBase:k1", 
"AxisLabelPanelBaseView:k2", 
"TitleSettings:k3", 
"GetLabelForItemHandler:k4", 
"CreateRenderingParamsHandler:k5", 
"SnapMajorValueHandler:k6", 
"AdjustMajorValueHandler:k7", 
"CategoryAxisRenderingParameters:k8", 
"LogarithmicTickmarkValues:k9", 
"LogarithmicNumericSnapper:la", 
"Snapper:lb", 
"LinearTickmarkValues:lc", 
"LinearNumericSnapper:ld", 
"AxisRange:le", 
"AutoRangeCalculator:lf", 
"NumericYAxis:lg", 
"StraightNumericAxisBase:lh", 
"StraightNumericAxisBaseView:li", 
"NumericScaleMode:lj", 
"LogarithmicScaler:lk", 
"NumericXAxis:ll", 
"NumericXAxisView:lm", 
"HorizontalSmartAxisLabelPanel:ln", 
"HorizontalAxisLabelPanelBase:lo", 
"HorizontalAxisLabelPanelBaseView:lp", 
"AxisExtentType:lq", 
"SmartAxisLabelDisplayType:lr", 
"HorizontalSmartAxisLabelPanelView:ls", 
"FontMappingInfo:lt", 
"CategoryDateTimeXAxis:lu", 
"CategoryDateTimeXAxisView:lv", 
"TimeAxisDisplayType:lw", 
"FastItemDateTimeColumn:lx", 
"IFastItemColumnInternal:ly", 
"FastItemColumn:lz", 
"FastReflectionHelper:l0", 
"HorizontalAxisLabelPanel:l1", 
"CoercionInfo:l2", 
"SortedListView$1:l3", 
"ArrayUtil:l4", 
"NumericAxisRenderingParameters:l5", 
"HorizontalLogarithmicScaler:l6", 
"HorizontalLinearScaler:l7", 
"LinearScaler:l8", 
"NumericYAxisView:l9", 
"VerticalAxisLabelPanel:ma", 
"VerticalAxisLabelPanelView:mb", 
"VerticalLogarithmicScaler:mc", 
"VerticalLinearScaler:md", 
"NumericRadiusAxis:me", 
"NumericRadiusAxisView:mf", 
"NumericAngleAxis:mg", 
"IAngleScaler:mh", 
"NumericAngleAxisView:mi", 
"PolarAxisRenderingManager:mj", 
"ViewportUtils:mk", 
"PolarAxisRenderingParameters:ml", 
"IPolarRadialRenderingParameters:mm", 
"RadialAxisRenderingParameters:mn", 
"AngleAxisLabelPanel:mo", 
"AngleAxisLabelPanelView:mp", 
"Extensions:mq", 
"CategoryAngleAxis:mr", 
"CategoryAngleAxisView:ms", 
"CategoryAxisRenderer:mt", 
"LinearCategorySnapper:mu", 
"CategoryTickmarkValues:mv", 
"RadialAxisLabelPanel:mw", 
"RadialAxisLabelPanelView:mx", 
"SyncSettings:my", 
"ValuesHolder:mz", 
"LineSeries:m0", 
"LineSeriesView:m1", 
"AnchoredCategorySeriesView:m2", 
"CategorySeriesView:m3", 
"CategoryBucketCalculator:m4", 
"CategoryTrendLineManagerBase:m5", 
"TrendLineManagerBase$1:m6", 
"TrendLineType:m7", 
"Clipper:m8", 
"EdgeClipper:m9", 
"LeftClipper:na", 
"BottomClipper:nb", 
"RightClipper:nc", 
"TopClipper:nd", 
"TrendResolutionParams:ne", 
"Flattener:nf", 
"Stack$1:ng", 
"ReverseArrayEnumerator$1:nh", 
"SpiralTodo:ni", 
"FlattenerSettings:nj", 
"IPreparesCategoryTrendline:nk", 
"SortingTrendLineManager:nl", 
"TrendFitCalculator:nm", 
"LeastSquaresFit:nn", 
"Numeric:no", 
"TrendAverageCalculator:np", 
"CategoryTrendLineManager:nq", 
"AnchoredCategoryBucketCalculator:nr", 
"UnknownValuePlotting:ns", 
"CategoryLineRasterizer:nt", 
"Action$5:nu", 
"PathVisualData:nv", 
"CategorySeriesRenderManager:nw", 
"AssigningCategoryStyleEventArgs:nx", 
"AssigningCategoryStyleEventArgsBase:ny", 
"GetCategoryItemsHandler:nz", 
"HighlightingInfo:n0", 
"HighlightingState:n1", 
"PenLineCap:n2", 
"AssigningCategoryMarkerStyleEventArgs:n3", 
"HighlightingManager:n4", 
"SplineSeriesBase:n5", 
"SplineSeriesBaseView:n6", 
"SplineType:n7", 
"CollisionAvoider:n8", 
"SafeSortedReadOnlyDoubleCollection:n9", 
"SafeReadOnlyDoubleCollection:oa", 
"SafeEnumerable:ob", 
"AreaSeries:oc", 
"AreaSeriesView:od", 
"LegendTemplates:oe", 
"PieChartBase:of", 
"PieChartBaseView:og", 
"PieChartViewManager:oh", 
"PieChartVisualData:oi", 
"PieSliceVisualDataList:oj", 
"PieSliceVisualData:ok", 
"PieSliceDataContext:ol", 
"Slice:om", 
"SliceView:on", 
"PieLabel:oo", 
"LabelsPosition:op", 
"MouseButtonEventArgs:oq", 
"FastItemsSource:or", 
"ColumnReference:os", 
"FastItemObjectColumn:ot", 
"FastItemIntColumn:ou", 
"LeaderLineType:ov", 
"OthersCategoryType:ow", 
"IndexCollection:ox", 
"LegendBase:oy", 
"LegendBaseView:oz", 
"LegendBaseViewManager:o0", 
"GradientData:o1", 
"GradientStopData:o2", 
"DataChartLegendMouseButtonEventArgs:o3", 
"DataChartMouseButtonEventArgs:o4", 
"ChartLegendMouseEventArgs:o5", 
"ChartMouseEventArgs:o6", 
"DataChartLegendMouseButtonEventHandler:o7", 
"DataChartLegendMouseEventHandler:o8", 
"LegendVisualData:o9", 
"LegendVisualDataList:pa", 
"LegendItemVisualData:pb", 
"FunnelSliceDataContext:pc", 
"PieChartFormatLabelHandler:pd", 
"LabelClickEventHandler:pe", 
"LabelClickEventArgs:pf", 
"SliceClickEventHandler:pg", 
"SliceClickEventArgs:ph", 
"ItemLegend:pi", 
"ItemLegendView:pj", 
"LegendItemInfo:pk", 
"BubbleSeries:pl", 
"ScatterBase:pm", 
"ScatterBaseView:pn", 
"MarkerManagerBase:po", 
"OwnedPoint:pp", 
"MarkerManagerBucket:pq", 
"ScatterTrendLineManager:pr", 
"NumericMarkerManager:ps", 
"CollisionAvoidanceType:pt", 
"SmartPlacer:pu", 
"ISmartPlaceable:pv", 
"SmartPosition:pw", 
"SmartPlaceableWrapper$1:px", 
"ScatterAxisInfoCache:py", 
"ScatterErrorBarSettings:pz", 
"ErrorBarSettingsBase:p0", 
"EnableErrorBars:p1", 
"ErrorBarCalculatorReference:p2", 
"IErrorBarCalculator:p3", 
"ErrorBarCalculatorType:p4", 
"ScatterFrame:p5", 
"ScatterFrameBase$1:p6", 
"DictInterpolator$3:p7", 
"Action$6:p8", 
"SeriesHitTestMode:p9", 
"SyncLink:qa", 
"ChartCollection:qb", 
"FastItemsSourceReference:qc", 
"SyncManager:qd", 
"SyncLinkManager:qe", 
"ErrorBarsHelper:qf", 
"BubbleSeriesView:qg", 
"BubbleMarkerManager:qh", 
"SizeScale:qi", 
"BrushScale:qj", 
"ScaleLegend:qk", 
"ScaleLegendView:ql", 
"CustomPaletteBrushScale:qm", 
"BrushSelectionMode:qn", 
"ValueBrushScale:qo", 
"RingSeriesBase:qp", 
"XamDoughnutChart:qq", 
"RingCollection:qr", 
"Ring:qs", 
"RingControl:qt", 
"RingControlView:qu", 
"Arc:qv", 
"ArcView:qw", 
"ArcItem:qx", 
"SliceItem:qy", 
"RingSeriesBaseView:qz", 
"Nullable$1:q0", 
"RingSeriesCollection:q1", 
"SliceCollection:q2", 
"XamDoughnutChartView:q3", 
"Action$2:q4", 
"DoughnutChartVisualData:q5", 
"RingSeriesVisualDataList:q6", 
"RingSeriesVisualData:q7", 
"RingVisualDataList:q8", 
"RingVisualData:q9", 
"ArcVisualDataList:ra", 
"ArcVisualData:rb", 
"SliceVisualDataList:rc", 
"SliceVisualData:rd", 
"DoughnutChartLabelVisualData:re", 
"HoleDimensionsChangedEventHandler:rf", 
"HoleDimensionsChangedEventArgs:rg", 
"XamFunnelChart:rh", 
"IItemProvider:ri", 
"MessageHandler:rj", 
"MessageHandlerEventHandler:rk", 
"Message:rl", 
"ServiceProvider:rm", 
"MessageChannel:rn", 
"MessageEventHandler:ro", 
"Queue$1:rp", 
"XamFunnelConnector:rq", 
"XamFunnelController:rr", 
"SliceInfoList:rs", 
"SliceInfo:rt", 
"SliceAppearance:ru", 
"PointList:rv", 
"FunnelSliceVisualData:rw", 
"PointData:rx", 
"SliceInfoUnaryComparison:ry", 
"Bezier:rz", 
"BezierPoint:r0", 
"BezierOp:r1", 
"BezierPointComparison:r2", 
"DoubleColumn:r3", 
"ObjectColumn:r4", 
"XamFunnelView:r5", 
"IOuterLabelWidthDecider:r6", 
"IFunnelLabelSizeDecider:r7", 
"MouseLeaveMessage:r8", 
"InteractionMessage:r9", 
"MouseMoveMessage:sa", 
"MouseButtonMessage:sb", 
"MouseButtonAction:sc", 
"MouseButtonType:sd", 
"SetAreaSizeMessage:se", 
"RenderingMessage:sf", 
"RenderSliceMessage:sg", 
"RenderOuterLabelMessage:sh", 
"TooltipValueChangedMessage:si", 
"TooltipUpdateMessage:sj", 
"FunnelDataContext:sk", 
"PropertyChangedMessage:sl", 
"ConfigurationMessage:sm", 
"ClearMessage:sn", 
"ClearTooltipMessage:so", 
"ContainerSizeChangedMessage:sp", 
"ViewportChangedMessage:sq", 
"ViewPropertyChangedMessage:sr", 
"OuterLabelAlignment:ss", 
"FunnelSliceDisplay:st", 
"SliceSelectionManager:su", 
"DataUpdatedMessage:sv", 
"ItemsSourceAction:sw", 
"FunnelFrame:sx", 
"UserSelectedItemsChangedMessage:sy", 
"LabelSizeChangedMessage:sz", 
"FrameRenderCompleteMessage:s0", 
"IntColumn:s1", 
"IntColumnComparison:s2", 
"Convert:s3", 
"SelectedItemsChangedMessage:s4", 
"ModelUpdateMessage:s5", 
"SliceClickedMessage:s6", 
"FunnelSliceClickedEventHandler:s7", 
"FunnelSliceClickedEventArgs:s8", 
"FunnelChartVisualData:s9", 
"FunnelSliceVisualDataList:ta", 
"RingSeries:tb", 
"WaterfallSeries:tc", 
"WaterfallSeriesView:td", 
"CategoryTransitionInMode:te", 
"FinancialSeries:tf", 
"FinancialSeriesView:tg", 
"FinancialBucketCalculator:th", 
"CategoryTransitionSourceFramePreparer:ti", 
"TransitionInSpeedType:tj", 
"FinancialCalculationDataSource:tk", 
"CalculatedColumn:tl", 
"FinancialEventArgs:tm", 
"FinancialCalculationSupportingCalculations:tn", 
"ColumnSupportingCalculation:to", 
"SupportingCalculation$1:tp", 
"SupportingCalculationStrategy:tq", 
"DataSourceSupportingCalculation:tr", 
"ProvideColumnValuesStrategy:ts", 
"AssigningCategoryStyleEventHandler:tt", 
"FinancialValueList:tu", 
"CategoryXAxis:tv", 
"CategoryXAxisView:tw", 
"FinancialEventHandler:tx", 
"StepLineSeries:ty", 
"StepLineSeriesView:tz", 
"StepAreaSeries:t0", 
"StepAreaSeriesView:t1", 
"RangeAreaSeries:t2", 
"HorizontalRangeCategorySeries:t3", 
"RangeCategorySeries:t4", 
"IHasHighLowValueCategory:t5", 
"RangeCategorySeriesView:t6", 
"RangeCategoryBucketCalculator:t7", 
"RangeCategoryFramePreparer:t8", 
"IHasCategoryTrendline:t9", 
"IHasTrendline:ua", 
"DefaultCategoryTrendlineHost:ub", 
"DefaultCategoryTrendlinePreparer:uc", 
"DefaultHighLowValueProvider:ud", 
"HighLowValuesHolder:ue", 
"CategoryMarkerManager:uf", 
"RangeValueList:ug", 
"RangeAreaSeriesView:uh", 
"LineFragment:ui", 
"LineFragmentView:uj", 
"StackedSeriesBase:uk", 
"StackedSeriesView:ul", 
"StackedBucketCalculator:um", 
"StackedSeriesManager:un", 
"StackedSeriesCollection:uo", 
"StackedFragmentSeries:up", 
"StackedAreaSeries:uq", 
"HorizontalStackedSeriesBase:ur", 
"StackedSplineAreaSeries:us", 
"AreaFragment:ut", 
"AreaFragmentView:uu", 
"AreaFragmentBucketCalculator:uv", 
"IStacked100Series:uw", 
"SplineAreaFragment:ux", 
"SplineFragmentBase:uy", 
"SplineAreaFragmentView:uz", 
"StackedColumnSeries:u0", 
"StackedColumnSeriesView:u1", 
"StackedColumnBucketCalculator:u2", 
"ColumnFragment:u3", 
"ColumnFragmentView:u4", 
"StackedBarSeries:u5", 
"VerticalStackedSeriesBase:u6", 
"IBarSeries:u7", 
"StackedBarSeriesView:u8", 
"StackedBarBucketCalculator:u9", 
"BarFragment:va", 
"StackedLineSeries:vb", 
"StackedSplineSeries:vc", 
"SplineFragment:vd", 
"SplineFragmentView:ve", 
"SplineFragmentBucketCalculator:vf", 
"StackedSeriesFramePreparer:vg", 
"SingleValuesHolder:vh", 
"IHasSingleValueCategory:vi", 
"StackedSeriesCreatedEventHandler:vj", 
"StackedSeriesCreatedEventArgs:vk", 
"StackedSeriesVisualData:vl", 
"SeriesVisualDataList:vm", 
"LineFragmentBucketCalculator:vn", 
"DefaultSingleValueProvider:vo", 
"AssigningCategoryMarkerStyleEventHandler:vp", 
"SeriesComponentsForView:vq", 
"CategorySeriesMarkerCollisionAvoidance:vr", 
"NonCollisionAvoider:vs", 
"AxisRangeChangedEventHandler:vt", 
"DataChartAxisRangeChangedEventHandler:vu", 
"ChartAxisRangeChangedEventArgs:vv", 
"ChartVisualData:vw", 
"AxisVisualDataList:vx", 
"ChartTitleVisualData:vy", 
"VisualDataSerializer:vz", 
"AxisVisualData:v0", 
"AxisLabelVisualDataList:v1", 
"AxisLabelVisualData:v2", 
"RadialBase:v3", 
"RadialBaseView:v4", 
"RadialBucketCalculator:v5", 
"SeriesRenderer$2:v6", 
"SeriesRenderingArguments:v7", 
"RadialFrame:v8", 
"RadialAxes:v9", 
"PolarBase:wa", 
"PolarBaseView:wb", 
"PolarTrendLineManager:wc", 
"PolarLinePlanner:wd", 
"AngleRadiusPair:we", 
"PolarAxisInfoCache:wf", 
"PolarFrame:wg", 
"PolarAxes:wh", 
"SeriesCollection:wi", 
"SeriesViewerComponentsFromView:wj", 
"SeriesViewerSurfaceViewer:wk", 
"ChartContentManager:wl", 
"ChartContentType:wm", 
"LabelPanelArranger:wn", 
"LabelPanelsArrangeState:wo", 
"VisualExportHelper:wp", 
"ChartHitTestMode:wq", 
"WindowResponse:wr", 
"ViewerSurfaceUsage:ws", 
"SeriesViewerComponentsForView:wt", 
"DataChartCursorEventHandler:wu", 
"ChartCursorEventArgs:wv", 
"DataChartMouseButtonEventHandler:ww", 
"DataChartMouseEventHandler:wx", 
"AnnotationLayer:wy", 
"AnnotationLayerView:wz", 
"RenderRequestedEventArgs:w0", 
"RefreshCompletedEventHandler:w1", 
"AxisComponentsForView:w2", 
"AxisComponentsFromView:w3", 
"AxisFormatLabelHandler:w4", 
"LabelFontHeuristics:w5", 
"PolygonUtil:w6", 
"TrendCalculators:w7", 
"SeriesComponentsFromView:w8", 
"EasingFunctions:w9", 
"GeographicMapSeriesHost$1:xa", 
"GeographicShapeSeriesBaseView:xb", 
"GeographicShapeSeriesBase:xc", 
"ShapeSeriesBase:xd", 
"ShapeSeriesViewBase:xe", 
"FlattenedShape:xf", 
"ShapeHitRegion:xg", 
"StyleSelector:xh", 
"PointCollectionUtil:xi", 
"PolySimplification:xj", 
"DefaultFlattener:xk", 
"IFlattener:xl", 
"RearrangedList$1:xm", 
"XamGeographicMap:xn", 
"IMapRenderDeferralHandler:xo", 
"GeographicMapImagery:xp", 
"GeographicMapImageryView:xq", 
"XamMultiScaleImage:xr", 
"XamMultiScaleImageView:xs", 
"Image:xt", 
"Tile:xu", 
"StackPool$1:xv", 
"WriteableBitmap:xw", 
"XamMultiScaleTileSource:xx", 
"Uri:xy", 
"UriKind:xz", 
"IEasingFunction:x0", 
"Pair$2:x1", 
"XamGeographicMapView:x2", 
"SphericalMercatorVerticalScaler:x3", 
"OpenStreetMapImagery:x4", 
"OpenStreetMapTileSource:x5", 
"MapTileSource:x6", 
"SphericalMercatorHorizontalScaler:x7", 
"ItfConverter:x8", 
"TriangulationSource:x9", 
"TriangulationSourcePointRecord:ya", 
"Triangle:yb", 
"Triangulator:yc", 
"TriangulatorContext:yd", 
"HalfEdgeSet:ye", 
"HalfEdge:yf", 
"EdgeComparer:yg", 
"PointTester:yh", 
"TriangulationStatusEventHandler:yi", 
"TriangulationStatusEventArgs:yj", 
"BinaryReader:yk", 
"AsyncCompletedEventArgs:yl", 
"BinaryFileDownloader:ym", 
"AsyncCompletedEventHandler:yn", 
"GeographicScatterAreaSeries:yo", 
"GeographicXYTriangulatingSeries:yp", 
"GeographicXYTriangulatingSeriesView:yq", 
"XYTriangulatingSeries:yr", 
"ColorScale:ys", 
"ScatterAreaSeries:yt", 
"ScatterAreaSeriesView:yu", 
"TriangleRasterizer:yv", 
"CustomPaletteColorScale:yw", 
"ColorScaleInterpolationMode:yx", 
"GeographicScatterAreaSeriesView:yy", 
"GeographicPolylineSeriesView:yz", 
"GeographicPolylineSeries:y0", 
"PolylineSeries:y1", 
"PolylineSeriesView:y2", 
"GeographicTileSeries:y3", 
"TileSeries:y4", 
"TileSeriesView:y5", 
"BingMapsMapImageryView:y6", 
"BingMapsMapImagery:y7", 
"BingMapsImageryStyle:y8", 
"BingMapsTileSource:y9", 
"CloudMadeMapImageryView:za", 
"CloudMadeMapImagery:zb", 
"CloudMadeTileSource:zc", 
"GeographicProportionalSymbolSeriesView:zd", 
"GeographicProportionalSymbolSeries:ze", 
"GeographicContourLineSeriesView:zf", 
"GeographicContourLineSeries:zg", 
"ContourLineSeries:zh", 
"ContourLineSeriesView:zi", 
"ContourValueResolver:zj", 
"LinearContourValueResolver:zk", 
"ContourBuilder:zl", 
"PolylineBuilder:zm", 
"GeographicHighDensityScatterSeriesView:zn", 
"GeographicHighDensityScatterSeries:zo", 
"HighDensityScatterSeries:zp", 
"HighDensityScatterSeriesView:zq", 
"KDTree2D:zr", 
"KDTreeNode2D:zs", 
"KDPointData:zt", 
"SearchData:zu", 
"Monitor:zv", 
"KDTreeThunk:zw", 
"KNearestResults:zx", 
"KNearestResult:zy", 
"SearchArgs:zz", 
"ProgressiveLoadStatusEventArgs:z0", 
"GeographicShapeSeriesView:z1", 
"GeographicShapeSeries:z2", 
"ShapeSeries:z3", 
"ShapeSeriesView:z4", 
"ShapeAxisInfoCache:z5", 
"GeographicSymbolSeriesView:z6", 
"GeographicSymbolSeries:z7", 
"ScatterSeries:z8", 
"ScatterSeriesView:z9", 
"ShapefileConverter:aad", 
"DependencyObjectNotifier:aae", 
"ShapefileRecord:aaf", 
"ShapeFileUtil:aag", 
"Header:aah", 
"ShapeType:aai", 
"XBaseField:aaj", 
"XBaseDataType:aak", 
"Encoding:aal", 
"UTF8Encoding:aam", 
"Decoder:aan", 
"UnicodeEncoding:aao", 
"AsciiEncoding:aap", 
"DefaultDecoder:aaq", 
"AbstractEnumerable:aar", 
"AbstractEnumerator:aas", 
"GenericEnumerable$1:aat", 
"GenericEnumerator$1:aau"]);


$.ig.util.defType('ShapeType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "None";
			case 1: return "Point";
			case 3: return "PolyLine";
			case 5: return "Polygon";
			case 8: return "PolyPoint";
			case 11: return "PointZ";
			case 13: return "PolyLineZ";
			case 15: return "PolygonZ";
			case 18: return "PolyPointZ";
			case 21: return "PointM";
			case 23: return "PolyLineM";
			case 25: return "PolygonM";
			case 28: return "PolyPointM";
			case 31: return "PolyPatch";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ShapeType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('XBaseDataType', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Character";
			case 1: return "Number";
			case 2: return "Logical";
			case 3: return "Date";
			case 4: return "Memo";
			case 5: return "FloatingPoint";
			case 6: return "Binary";
			case 7: return "General";
			case 8: return "Picture";
			case 9: return "Currency";
			case 10: return "DateTime";
			case 11: return "Integer";
			case 12: return "VariField";
			case 13: return "Variant";
			case 14: return "Timestamp";
			case 15: return "Double";
			case 16: return "AutoIncrement";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('XBaseDataType', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('BingMapsImageryStyle', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Aerial";
			case 1: return "AerialWithLabels";
			case 2: return "Road";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('BingMapsImageryStyle', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('ColorScaleInterpolationMode', 'Enum', {
	init: function (v) {
		this._v = v;
	},
	$getName: function (v) {
		switch (v) {
			case 0: return "Select";
			case 1: return "InterpolateRGB";
			case 2: return "InterpolateHSV";
			default: return v.toString();
		}
	},
	$value: function () {
		return this._v;
	},
	$type: new $.ig.Type('ColorScaleInterpolationMode', $.ig.Enum.prototype.$type)
}, true);

$.ig.util.defType('HostSeriesView$1', 'SeriesView', {
	$t: null,
	init: function ($t, model) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.SeriesView.prototype.init.call(this, model);
		this.hostModel(model);
	},
	_hostModel: null,
	hostModel: function (value) {
		if (arguments.length === 1) {
			this._hostModel = value;
			return value;
		} else {
			return this._hostModel;
		}
	}
	,
	onContextProvided: function (context, hitContext) {
		$.ig.SeriesView.prototype.onContextProvided.call(this, context, hitContext);
	}
	,
	onHostedSeriesUpdated: function () {
		this.hostModel().hostedSeries().itemsSource(this.hostModel().itemsSource());
		this.hostModel().hostedSeries().index(this.hostModel().index());
		this.hostModel().hostedSeries().brush(this.hostModel().brush());
		this.hostModel().hostedSeries().outline(this.hostModel().outline());
		this.hostModel().hostedSeries().resolution(this.hostModel().resolution());
		this.hostModel().hostedSeries().transitionDuration(this.hostModel().transitionDuration());
		this.hostModel().hostedSeries().__opacity = this.hostModel().__opacity;
	}
	,
	onItemsSourceUpdated: function () {
		this.hostModel().hostedSeries().itemsSource(this.hostModel().itemsSource());
	}
	,
	onResolutionUpdated: function () {
		this.hostModel().hostedSeries().resolution(this.hostModel().resolution());
	}
	,
	onTransitionDurationUpdated: function () {
		this.hostModel().hostedSeries().transitionDuration(this.hostModel().transitionDuration());
	}
	,
	onOpacityUpdated: function () {
		this.hostModel().hostedSeries().__opacity = this.hostModel().__opacity;
	}
	,
	$type: new $.ig.Type('HostSeriesView$1', $.ig.SeriesView.prototype.$type)
}, true);

$.ig.util.defType('ShapeSeriesBase', 'Series', {
	init: function () {
		this.__xAxis = null;
		this.__yAxis = null;
		this.__boundsLeft = null;
		this.__boundsTop = null;
		this.__boundsRight = null;
		this.__boundsBottom = null;
		$.ig.Series.prototype.init.call(this);
		this.shapeFilterResolutionCached(this.shapeFilterResolution());
		this._clipRect = $.ig.Rect.prototype.empty();
	},
	shapeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.shapeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.shapeMemberPathProperty);
		}
	}
	,
	__shapeColumn: null,
	shapeColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.shapeColumn() != value;
			if (changed) {
				var oldValue = this.shapeColumn();
				this.__shapeColumn = value;
				this.raisePropertyChanged($.ig.ShapeSeriesBase.prototype._shapeColumnPropertyName, oldValue, this.shapeColumn());
			}
			return value;
		} else {
			return this.__shapeColumn;
		}
	}
	,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.xAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.xAxisProperty);
		}
	}
	,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.yAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.yAxisProperty);
		}
	}
	,
	isArray: function (array_) {
		var isArr = $.isArray(array_);
		return isArr;
	}
	,
	convertToListList: function (val) {
		var ret = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 0);
		var shape;
		var curr_;
		var x;
		var y;
		for (var i = 0; i < val.length; i++) {
			shape = val[i];
			var ring = new $.ig.List$1($.ig.Point.prototype.$type, 0);
			for (var j = 0; j < shape.length; j++) {
				curr_ = shape[j];
				x = curr_.x;
				y = curr_.y;
				ring.add({ __x: x, __y: y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
			}
			ret.add(ring);
		}
		return ret;
	}
	,
	registerObjectColumn: function (memberPath) {
		if (this.fastItemsSource() != null && this.isArray(this.itemsSource()) && memberPath != null && memberPath.split('!').length != 2) {
			if (this.coercionMethods() == null) {
				this.coercionMethods({});
			}
			var methods_ = this.coercionMethods();
			methods_.convertToListList = this.convertToListList;
			memberPath += "!convertToListList";
		}
		return $.ig.Series.prototype.registerObjectColumn.call(this, memberPath);
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.Series.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				var oldFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue);
				if (oldFastItemsSource != null) {
					oldFastItemsSource.deregisterColumn(this.shapeColumn());
					oldFastItemsSource.deregisterColumn(this.fillColumn());
					this.shapeColumn(null);
					this.fillColumn(null);
				}
				var newFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue);
				if (newFastItemsSource != null) {
					this.shapeColumn(this.registerObjectColumn(this.shapeMemberPath()));
					this.fillColumn(this.registerDoubleColumn(this.fillMemberPath()));
				}
				this.renderSeries(false);
				break;
			case $.ig.ShapeSeriesBase.prototype.shapeMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.shapeColumn());
					this.shapeColumn(this.registerObjectColumn(this.shapeMemberPath()));
				}
				break;
			case $.ig.ShapeSeriesBase.prototype._shapeColumnPropertyName:
				this.resetBoundsColumn();
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ShapeSeriesBase.prototype._fillMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.fillColumn());
					this.fillColumn(this.registerDoubleColumn(this.fillMemberPath()));
				}
				break;
			case $.ig.ShapeSeriesBase.prototype.xAxisPropertyName:
			case $.ig.ShapeSeriesBase.prototype.yAxisPropertyName:
				this.__xAxis = this.xAxis();
				this.__yAxis = this.yAxis();
				var oldAxis = oldValue;
				var newAxis = newValue;
				this.unInitializeAxis(oldAxis);
				this.initializeAxis(newAxis);
				if ((newAxis != null && !newAxis.updateRange()) || (newAxis == null && oldAxis != null)) {
					this.renderSeries(false);
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ShapeSeries.prototype.markerCollisionAvoidancePropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.resolutionPropertyName:
				this.__resolution = this.resolution();
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.actualBrushPropertyName:
				this.renderSeries(false);
				break;
			case $.ig.ShapeSeriesBase.prototype.shapeFilterResolutionPropertyName:
				this.shapeFilterResolutionCached(this.shapeFilterResolution());
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	__xAxis: null,
	__yAxis: null,
	__resolution: 0,
	_boundsColumn: null,
	boundsColumn: function (value) {
		if (arguments.length === 1) {
			this._boundsColumn = value;
			return value;
		} else {
			return this._boundsColumn;
		}
	}
	,
	resetBoundsColumn: function () {
		this.boundsColumn(null);
		if (this.shapeColumn() == null) {
			return;
		}
		this.boundsColumn(new $.ig.List$1($.ig.Rect.prototype.$type, 2, this.shapeColumn().count()));
		for (var i = 0; i < this.shapeColumn().count(); i++) {
			this.boundsColumn().add($.ig.PointCollectionUtil.prototype.getBounds3((this.shapeColumn().item(i))));
		}
	}
	,
	getPlotPoints: function (view, clipper, itemPoints, xParams, yParams) {
		{
			var points = itemPoints;
			return this.mapAndFlatten1(view, clipper, points, xParams, yParams);
		}
	}
	,
	__boundsLeft: null,
	__boundsTop: null,
	__boundsRight: null,
	__boundsBottom: null,
	isClosed: function () {
		return true;
	}
	,
	shouldRecordMarkerPositions: function () {
		return false;
	}
	,
	renderSeriesOverride: function (animate) {
		this.renderWithView(this.view());
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.Series.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		var shapeViewBase = view;
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		this.renderWithView(view);
	}
	,
	renderWithView: function (view) {
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		var windowRect;
		var viewportRect;
		var shapeSeriesView = view;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		if (viewportRect.width() < 1 || viewportRect.height() < 1 || this.shapeColumn() == null) {
			return;
		}
		var xaxis = this.xAxis();
		var yaxis = this.yAxis();
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var xParams = new $.ig.ScalerParams(1, windowRect, viewportRect, xaxis.isInverted());
		xParams._effectiveViewportRect = effectiveViewportRect;
		var yParams = new $.ig.ScalerParams(1, windowRect, viewportRect, yaxis.isInverted());
		yParams._effectiveViewportRect = effectiveViewportRect;
		shapeSeriesView.initializeShapes();
		var mappedBounds = new $.ig.Rect(0, 0, 0, 0, 0);
		var top;
		var bottom;
		var left;
		var right;
		var shapeCount = this.shapeColumn().count();
		if (this.__boundsTop == null || this.__boundsTop.length != shapeCount) {
			this.__boundsTop = new Array(shapeCount);
			this.__boundsLeft = new Array(shapeCount);
			this.__boundsRight = new Array(shapeCount);
			this.__boundsBottom = new Array(shapeCount);
		}
		var boundsTop = this.__boundsTop;
		var boundsLeft = this.__boundsLeft;
		var boundsRight = this.__boundsRight;
		var boundsBottom = this.__boundsBottom;
		var bounds;
		for (var i = 0; i < shapeCount; i++) {
			bounds = this.boundsColumn().__inner[i];
			boundsTop[i] = bounds.top();
			boundsLeft[i] = bounds.left();
			boundsRight[i] = bounds.right();
			boundsBottom[i] = bounds.bottom();
		}
		this.__xAxis.getScaledValueList(boundsLeft, 0, boundsLeft.length, xParams);
		this.__xAxis.getScaledValueList(boundsRight, 0, boundsRight.length, xParams);
		this.__yAxis.getScaledValueList(boundsTop, 0, boundsTop.length, yParams);
		this.__yAxis.getScaledValueList(boundsBottom, 0, boundsBottom.length, yParams);
		shapeSeriesView.provideBounds(boundsLeft, boundsTop, boundsRight, boundsBottom);
		var shapeColumn = this.shapeColumn();
		var fastItemsSource = this.fastItemsSource();
		this.updateClipRect(shapeSeriesView);
		var viewportLeft = viewportRect.left();
		var viewportRight = viewportRect.right();
		var viewportTop = viewportRect.top();
		var viewportBottom = viewportRect.bottom();
		var clipper = null;
		if (this._clipRect.isEmpty()) {
			clipper = null;
		} else {
			clipper = new $.ig.Clipper(1, this._clipRect.left(), this._clipRect.bottom(), this._clipRect.right(), this._clipRect.top(), this.isClosed());
		}
		var shouldRecordMarkerPositions = this.shouldRecordMarkerPositions();
		if (shouldRecordMarkerPositions) {
			shapeSeriesView.markerPositions().clear();
		}
		var actualClipper;
		for (var i1 = 0; i1 < shapeCount; i1++) {
			left = boundsLeft[i1];
			top = boundsTop[i1];
			bottom = boundsBottom[i1];
			right = boundsRight[i1];
			mappedBounds.x(Math.min(left, right));
			mappedBounds.y(Math.min(top, bottom));
			mappedBounds.width(Math.max(left, right) - mappedBounds.x());
			mappedBounds.height(Math.max(top, bottom) - mappedBounds.y());
			if (mappedBounds.intersectsWith(viewportRect)) {
				if (mappedBounds.x() < viewportLeft || mappedBounds.y() < viewportTop || mappedBounds.x() > viewportRight || mappedBounds.y() > viewportBottom) {
					actualClipper = clipper;
				} else {
					actualClipper = null;
				}
				var plotPoints = this.getPlotPoints(view, actualClipper, shapeColumn.item(i1), xParams, yParams);
				if (shouldRecordMarkerPositions) {
					var maxArea = 0;
					var maxShape = null;
					for (var k = 0; k < plotPoints.count(); k++) {
						var s = plotPoints.__inner[k];
						var area = s.bounds().width() * s.bounds().height();
						if (area > maxArea) {
							maxArea = area;
							maxShape = s;
						}
					}
					shapeSeriesView.markerPositions().add(i1, maxShape);
				}
				shapeSeriesView.addShape(i1, fastItemsSource.item(i1), mappedBounds, plotPoints);
			}
		}
		shapeSeriesView.finalizeShapes();
	}
	,
	invalidateAxes: function () {
		$.ig.Series.prototype.invalidateAxes.call(this);
		if (this.xAxis() != null) {
			this.xAxis().renderAxis();
		}
		if (this.yAxis() != null) {
			this.yAxis().renderAxis();
		}
	}
	,
	windowRectChangedOverride: function (oldWindowRect, newWindowRect) {
		this.renderSeries(false);
	}
	,
	viewportRectChangedOverride: function (oldViewportRect, newViewportRect) {
		this.renderSeries(false);
	}
	,
	fillScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.fillScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.fillScaleProperty);
		}
	}
	,
	fillMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.fillMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.fillMemberPathProperty);
		}
	}
	,
	__fillColumn: null,
	fillColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.fillColumn() != value;
			if (changed) {
				var oldValue = this.fillColumn();
				this.__fillColumn = value;
				this.raisePropertyChanged($.ig.ShapeSeriesBase.prototype._fillColumnPropertyName, oldValue, this.fillColumn());
			}
			return value;
		} else {
			return this.__fillColumn;
		}
	}
	,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		$.ig.Series.prototype.dataUpdatedOverride.call(this, action, position, count, propertyName);
		switch (action) {
			case $.ig.FastItemsSourceEventAction.prototype.change:
				if (propertyName == this.shapeMemberPath()) {
					this.boundsColumn().__inner[position] = $.ig.PointCollectionUtil.prototype.getBounds3((this.shapeColumn().item(position)));
				}
				break;
			case $.ig.FastItemsSourceEventAction.prototype.insert:
				for (var b = position; b < position + count; b++) {
					this.boundsColumn().insert(b, $.ig.PointCollectionUtil.prototype.getBounds3((this.shapeColumn().item(b))));
				}
				break;
			case $.ig.FastItemsSourceEventAction.prototype.remove:
				this.boundsColumn().removeRange(position, count);
				break;
			case $.ig.FastItemsSourceEventAction.prototype.replace:
				for (var c = position; c < position + count; c++) {
					this.boundsColumn().__inner[c] = $.ig.PointCollectionUtil.prototype.getBounds3((this.shapeColumn().item(c)));
				}
				break;
			case $.ig.FastItemsSourceEventAction.prototype.reset:
				this.resetBoundsColumn();
				break;
		}
		this.renderSeries(false);
	}
	,
	validateSeries: function (viewportRect, windowRect, view) {
		return $.ig.Series.prototype.validateSeries.call(this, viewportRect, windowRect, view) && (this.shapeColumn() != null || !this.requiresShapes()) && this.xAxis() != null && this.yAxis() != null && this.boundsColumn() != null && this.boundsColumn().count() == this.shapeColumn().count() && viewportRect.width() > 0 && viewportRect.height() > 0;
	}
	,
	requiresShapes: function () {
		return true;
	}
	,
	onViewCreated: function (view) {
		$.ig.Series.prototype.onViewCreated.call(this, view);
		this.shapeSeriesView($.ig.util.cast($.ig.ShapeSeriesViewBase.prototype.$type, view));
	}
	,
	_shapeSeriesView: null,
	shapeSeriesView: function (value) {
		if (arguments.length === 1) {
			this._shapeSeriesView = value;
			return value;
		} else {
			return this._shapeSeriesView;
		}
	}
	,
	mapAndFlatten1: function (view, clipper, points, xParams, yParams) {
		var rings = new $.ig.List$1($.ig.FlattenedShape.prototype.$type, 0);
		var ring;
		var count = points.count();
		for (var i = 0; i < count; i++) {
			ring = points.__inner[i];
			var s = this.mapAndFlatten(view, clipper, ring, xParams, yParams);
			if (s == null) {
				continue;
			}
			rings.add(s);
		}
		return rings;
	}
	,
	mapAndFlatten: function (view, clipper, points, xParams, yParams) {
		var mappedPoints = this.mapPoints(points, xParams, yParams);
		var mappedX = mappedPoints.item1();
		var mappedY = mappedPoints.item2();
		var mappedCount = mappedX.length;
		var minX = 1.7976931348623157E+308;
		var minY = 1.7976931348623157E+308;
		var maxX = -1.7976931348623157E+308;
		var maxY = -1.7976931348623157E+308;
		var currX;
		var currY;
		for (var i = 0; i < mappedCount; i++) {
			currX = mappedX[i];
			currY = mappedY[i];
			minX = currX < minX ? currX : minX;
			minY = currY < minY ? currY : minY;
			maxX = currX > maxX ? currX : maxX;
			maxY = currY > maxY ? currY : maxY;
		}
		var intersects = !(minX > this._clipRect.right() || maxX < this._clipRect.left() || minY > this._clipRect.bottom() || maxY < this._clipRect.top());
		if (!intersects) {
			return null;
		}
		var needsClip = clipper != null && (minX < this._clipRect.left() || minY < this._clipRect.top() || maxX > this._clipRect.right() || maxY > this._clipRect.bottom());
		var fullBounds = new $.ig.Rect(0, minX, minY, maxX - minX, maxY - minY);
		if (!(view).shouldRenderShape(fullBounds)) {
			return null;
		}
		minX = minX < this._clipRect.left() ? this._clipRect.left() : minX;
		maxX = maxX > this._clipRect.right() ? this._clipRect.right() : maxX;
		minY = minY < this._clipRect.top() ? this._clipRect.top() : minY;
		maxY = maxY > this._clipRect.bottom() ? this._clipRect.bottom() : maxY;
		var numReduced = $.ig.PolySimplification.prototype.vertexReduction(mappedPoints, this.resolution());
		var result = new $.ig.DefaultFlattener().fastFlatten(mappedPoints.item1(), mappedPoints.item2(), numReduced, this.__resolution);
		var count = result.count();
		if (needsClip) {
			var clippedResult = new $.ig.List$1($.ig.Point.prototype.$type, 0);
			clipper.target(clippedResult);
			for (var i1 = 0; i1 < count; i1++) {
				clipper.add(result.__inner[i1]);
			}
			clipper.target(null);
			result = clippedResult;
		}
		var s = new $.ig.FlattenedShape();
		s.shape(result);
		s.bounds(new $.ig.Rect(0, minX, minY, maxX - minX, maxY - minY));
		s.fullBounds(fullBounds);
		return s;
	}
	,
	mapPoints: function (points, xParams, yParams) {
		var xAxis = this.__xAxis;
		var yAxis = this.__yAxis;
		var count = points.count();
		var xvals = new Array(count);
		var yvals = new Array(count);
		for (var i = 0; i < count; i++) {
			xvals[i] = points.__inner[i].__x;
			yvals[i] = points.__inner[i].__y;
		}
		xAxis.getScaledValueList(xvals, 0, xvals.length, xParams);
		yAxis.getScaledValueList(yvals, 0, yvals.length, yParams);
		return new $.ig.Tuple$2(Array, Array, xvals, yvals);
	}
	,
	_clipRect: null,
	updateClipRect: function (view) {
		view.updateClipRect();
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.Series.prototype.clearRendering.call(this, wipeClean, view);
		var shapeSeriesView = view;
		shapeSeriesView.clearRendering();
	}
	,
	initializeAxis: function (axis) {
		if (axis != null) {
			axis.registerSeries(this);
		}
	}
	,
	unInitializeAxis: function (axis) {
		if (axis != null) {
			axis.deregisterSeries(this);
		}
	}
	,
	getHitDataContext: function (position) {
		var shape = this.shapeSeriesView().getHitShape(position);
		var ret = null;
		if (shape != null) {
			ret = shape.dataContext();
		}
		if (ret != null) {
			return ret;
		}
		return $.ig.Series.prototype.getHitDataContext.call(this, position);
	}
	,
	getItemAt: function (index) {
		return this.fastItemsSource().item(index);
	}
	,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	shapeFilterResolution: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeriesBase.prototype.shapeFilterResolutionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeriesBase.prototype.shapeFilterResolutionProperty);
		}
	}
	,
	_shapeFilterResolutionCached: 0,
	shapeFilterResolutionCached: function (value) {
		if (arguments.length === 1) {
			this._shapeFilterResolutionCached = value;
			return value;
		} else {
			return this._shapeFilterResolutionCached;
		}
	}
	,
	$type: new $.ig.Type('ShapeSeriesBase', $.ig.Series.prototype.$type)
}, true);

$.ig.util.defType('GeographicShapeSeriesBaseView', 'HostSeriesView$1', {
	init: function (model) {
		$.ig.HostSeriesView$1.prototype.init.call(this, $.ig.ShapeSeriesBase.prototype.$type, model);
		this.shapeBaseModel(model);
	},
	_shapeBaseModel: null,
	shapeBaseModel: function (value) {
		if (arguments.length === 1) {
			this._shapeBaseModel = value;
			return value;
		} else {
			return this._shapeBaseModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.HostSeriesView$1.prototype.onHostedSeriesUpdated.call(this);
		this.shapeBaseModel().hostedSeries().shapeMemberPath(this.shapeBaseModel().shapeMemberPath());
		this.shapeBaseModel().hostedSeries().brush(this.shapeBaseModel().actualBrush());
		this.shapeBaseModel().hostedSeries().outline(this.shapeBaseModel().actualOutline());
	}
	,
	onSeriesViewerUpdated: function () {
		if (this.shapeBaseModel().seriesViewer() == null) {
			this.shapeBaseModel().hostedSeries().xAxis(null);
			this.shapeBaseModel().hostedSeries().yAxis(null);
			return;
		}
		this.shapeBaseModel().hostedSeries().xAxis((this.shapeBaseModel().seriesViewer()).xAxis());
		this.shapeBaseModel().hostedSeries().yAxis((this.shapeBaseModel().seriesViewer()).yAxis());
	}
	,
	actualBrushUpdated: function () {
		this.shapeBaseModel().hostedSeries().brush(this.shapeBaseModel().actualBrush());
	}
	,
	actualOutlineUpdated: function () {
		this.shapeBaseModel().hostedSeries().outline(this.shapeBaseModel().actualOutline());
	}
	,
	shapeMemberPathUpdated: function () {
		this.shapeBaseModel().hostedSeries().shapeMemberPath(this.shapeBaseModel().shapeMemberPath());
	}
	,
	thicknessUpdated: function () {
		this.shapeBaseModel().hostedSeries().thickness(this.shapeBaseModel().thickness());
	}
	,
	shapeFilterResolutionUpdated: function () {
		this.shapeBaseModel().hostedSeries().shapeFilterResolution(this.shapeBaseModel().shapeFilterResolution());
	}
	,
	$type: new $.ig.Type('GeographicShapeSeriesBaseView', $.ig.HostSeriesView$1.prototype.$type.specialize($.ig.ShapeSeriesBase.prototype.$type))
}, true);

$.ig.util.defType('GeographicPolylineSeriesView', 'GeographicShapeSeriesBaseView', {
	init: function (model) {
		$.ig.GeographicShapeSeriesBaseView.prototype.init.call(this, model);
		this.polylineModel(model);
	},
	_polylineModel: null,
	polylineModel: function (value) {
		if (arguments.length === 1) {
			this._polylineModel = value;
			return value;
		} else {
			return this._polylineModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicShapeSeriesBaseView.prototype.onHostedSeriesUpdated.call(this);
		(this.polylineModel().hostedSeries()).shapeStyleSelector(this.polylineModel().shapeStyleSelector());
		(this.polylineModel().hostedSeries()).shapeStyle(this.polylineModel().shapeStyle());
	}
	,
	shapeStyleSelectorUpdated: function () {
		(this.polylineModel().hostedSeries()).shapeStyleSelector(this.polylineModel().shapeStyleSelector());
	}
	,
	shapeStyleUpdated: function () {
		(this.polylineModel().hostedSeries()).shapeStyle(this.polylineModel().shapeStyle());
	}
	,
	$type: new $.ig.Type('GeographicPolylineSeriesView', $.ig.GeographicShapeSeriesBaseView.prototype.$type)
}, true);

$.ig.util.defType('GeographicMapSeriesHost$1', 'Series', {
	$t: null,
	init: function ($t) {
		this.$t = $t;
		this.$type = this.$type.specialize(this.$t);
		$.ig.Series.prototype.init.call(this);
		this.hostedSeries(this.createSeries());
	},
	__hostedSeries: null,
	isGeographic: function () {
		return true;
	}
	,
	resolveHostedSeries: function () {
		return this.hostedSeries();
	}
	,
	hostedSeries: function (value) {
		if (arguments.length === 1) {
			var changed = $.ig.util.getBoxIfEnum(this.$t, this.hostedSeries()) != $.ig.util.getBoxIfEnum(this.$t, value);
			if (changed) {
				this.__hostedSeries = value;
				this.onHostedSeriesUpdated();
			}
			return value;
		} else {
			return this.__hostedSeries;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		this.disableCursorEventsForSeries(this.hostedSeries());
		this.hostView().onHostedSeriesUpdated();
	}
	,
	getItem: function (world) {
		return this.getSeriesItem(this.hostedSeries(), world);
	}
	,
	createSeries: function () {
	}
	,
	createView: function () {
		return new $.ig.HostSeriesView$1(this.$t, this);
	}
	,
	onViewCreated: function (view) {
		$.ig.Series.prototype.onViewCreated.call(this, view);
		this.hostView(view);
	}
	,
	_hostView: null,
	hostView: function (value) {
		if (arguments.length === 1) {
			this._hostView = value;
			return value;
		} else {
			return this._hostView;
		}
	}
	,
	visibleFromScale: function (value) {
		if (arguments.length === 1) {
			this.setValue(this.$type.getStaticFields($.ig.GeographicMapSeriesHost$1.prototype.$type).visibleFromScaleProperty, value);
			return value;
		} else {
			return this.getValue(this.$type.getStaticFields($.ig.GeographicMapSeriesHost$1.prototype.$type).visibleFromScaleProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.Series.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.hostedSeries().seriesViewer(this.seriesViewer());
				if (oldValue != null) {
					(oldValue).removeSeries(this.hostedSeries());
				}
				if (newValue != null) {
					this.seriesViewer().attachSeries(this.hostedSeries());
				}
				this.hostedSeries().provideViewport(this.viewport());
				this.hostedSeries().index(this.index());
				this.forceIndexUpdate(this.hostedSeries());
				this._uniqueIndex = this.hostedSeries()._uniqueIndex;
				var oldSeriesViewer = $.ig.util.cast($.ig.SeriesViewer.prototype.$type, oldValue);
				if (oldSeriesViewer != null) {
					oldSeriesViewer.actualWindowRectChanged = $.ig.Delegate.prototype.remove(oldSeriesViewer.actualWindowRectChanged, this.seriesViewer_ActualWindowRectChanged.runOn(this));
				}
				if (this.seriesViewer() != null) {
					var $t = this.seriesViewer();
					$t.actualWindowRectChanged = $.ig.Delegate.prototype.combine($t.actualWindowRectChanged, this.seriesViewer_ActualWindowRectChanged.runOn(this));
				}
				this.applyVisibleFromScale();
				break;
			case $.ig.Series.prototype.syncLinkPropertyName:
				this.hostedSeries().syncLink(this.syncLink());
				break;
			case $.ig.GeographicMapSeriesHost$1.prototype._visibleFromScalePropertyName:
				this.applyVisibleFromScale();
				break;
			case $.ig.Series.prototype.indexPropertyName:
				this.hostedSeries().index(this.index());
				break;
			case $.ig.Series.prototype.itemsSourcePropertyName:
				this.hostView().onItemsSourceUpdated();
				break;
			case $.ig.Series.prototype.resolutionPropertyName:
				this.hostView().onResolutionUpdated();
				break;
			case $.ig.Series.prototype.transitionDurationPropertyName:
				this.hostView().onTransitionDurationUpdated();
				break;
			case "Opacity":
				this.hostView().onOpacityUpdated();
				break;
		}
	}
	,
	applyVisibleFromScale: function () {
		if (this.seriesViewer() == null || $.ig.util.getBoxIfEnum(this.$t, this.hostedSeries()) == null) {
			return;
		}
		if (this.seriesViewer().actualWindowRect().width() > this.visibleFromScale()) {
			this.hostedSeries().__visibility = $.ig.Visibility.prototype.collapsed;
		} else {
			this.hostedSeries().__visibility = $.ig.Visibility.prototype.visible;
		}
	}
	,
	seriesViewer_ActualWindowRectChanged: function (sender, e) {
		this.applyVisibleFromScale();
	}
	,
	renderSeries: function (animate) {
		$.ig.Series.prototype.renderSeries.call(this, animate);
		if ($.ig.util.getBoxIfEnum(this.$t, this.hostedSeries()) != null) {
			this.hostedSeries().renderSeries(animate);
		}
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		if ($.ig.util.getBoxIfEnum(this.$t, this.hostedSeries()) != null) {
			this.hostedSeries().renderAlternateView(viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		}
	}
	,
	getHitDataContext: function (position) {
		return this.hostedSeries().getHitDataContext(position);
	}
	,
	styleUpdated: function () {
		$.ig.Series.prototype.styleUpdated.call(this);
		this.hostedSeries().styleUpdated();
	}
	,
	coercionMethods: function (value) {
		if (arguments.length === 1) {
			this.__coercionMethods = value;
			this.hostedSeries().coercionMethods(value);
			return value;
		} else {
			return this.__coercionMethods;
		}
	}
	,
	exportVisualDataOverride: function (svd) {
		$.ig.Series.prototype.exportVisualDataOverride.call(this, svd);
		if ($.ig.util.getBoxIfEnum(this.$t, this.hostedSeries()) != null) {
			var vd = this.hostedSeries().exportVisualData();
			var en = vd.shapes().getEnumerator();
			while (en.moveNext()) {
				var shape = en.current();
				svd.shapes().add(shape);
			}
			var en1 = vd.markerShapes().getEnumerator();
			while (en1.moveNext()) {
				var marker = en1.current();
				svd.markerShapes().add(marker);
			}
			svd.pixels(vd.pixels());
			svd.pixelWidth(vd.pixelWidth());
		}
	}
	,
	$type: new $.ig.Type('GeographicMapSeriesHost$1', $.ig.Series.prototype.$type, null, function () {
		this.visibleFromScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicMapSeriesHost$1.prototype._visibleFromScalePropertyName, Number, $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize(arguments[0]), new $.ig.PropertyMetadata(2, 1, function (sender, e) {
			(sender).raisePropertyChanged($.ig.GeographicMapSeriesHost$1.prototype._visibleFromScalePropertyName, e.oldValue(), e.newValue());
		}));
	})
}, true);

$.ig.util.defType('GeographicShapeSeriesBase', 'GeographicMapSeriesHost$1', {
	init: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.init.call(this, $.ig.ShapeSeriesBase.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicShapeSeriesBaseView(this);
	}
	,
	_shapeBaseView: null,
	shapeBaseView: function (value) {
		if (arguments.length === 1) {
			this._shapeBaseView = value;
			return value;
		} else {
			return this._shapeBaseView;
		}
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapSeriesHost$1.prototype.onViewCreated.call(this, view);
		this.shapeBaseView(view);
	}
	,
	shapeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicMapSeriesHost$1.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.shapeBaseView().onSeriesViewerUpdated();
				break;
			case "ActualBrush":
				this.shapeBaseView().actualBrushUpdated();
				break;
			case "ActualOutline":
				this.shapeBaseView().actualOutlineUpdated();
				break;
			case $.ig.ShapeSeries.prototype.shapeMemberPathPropertyName:
				this.shapeBaseView().shapeMemberPathUpdated();
				break;
			case $.ig.Series.prototype.thicknessPropertyName:
				this.shapeBaseView().thicknessUpdated();
				break;
			case $.ig.GeographicShapeSeriesBase.prototype._shapeFilterResolutionPropertyName:
				this.shapeBaseView().shapeFilterResolutionUpdated();
				break;
		}
	}
	,
	shapeFilterResolution: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeriesBase.prototype.shapeFilterResolutionProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeriesBase.prototype.shapeFilterResolutionProperty);
		}
	}
	,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	$type: new $.ig.Type('GeographicShapeSeriesBase', $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize($.ig.ShapeSeriesBase.prototype.$type))
}, true);

$.ig.util.defType('GeographicTileSeries', 'GeographicShapeSeriesBase', {
	init: function () {
		this.__tilesSoruce = null;
		$.ig.GeographicShapeSeriesBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicTileSeries.prototype.$type);
	},
	tileImagery: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.tileImagery();
			if (changed) {
				var oldValue = this.tileImagery();
				this.__tileImagery = value;
				this.raisePropertyChanged($.ig.GeographicTileSeries.prototype.tileImageryPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__tileImagery;
		}
	}
	,
	__tileImagery: null,
	clearTileCache: function () {
		if (this.tileImagery() != null) {
			this.tileImagery().clearTileCache();
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.GeographicTileSeries.prototype.tileImageryPropertyName:
				if (this.hostedSeries() != null) {
					(this.hostedSeries()).tileImagery(this.tileImagery());
				}
				break;
		}
	}
	,
	__tilesSoruce: null,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicShapeSeriesBase.prototype.onHostedSeriesUpdated.call(this);
		(this.hostedSeries()).tileImagery(this.tileImagery());
		if (this.__tilesSoruce != null) {
			var $t = this.__tilesSoruce;
			$t.imageTilesReady = $.ig.Delegate.prototype.remove($t.imageTilesReady, this._tilesSoruce_ImageTilesReady.runOn(this));
		}
		this.__tilesSoruce = this.hostedSeries();
		if (this.__tilesSoruce != null) {
			var $t1 = this.__tilesSoruce;
			$t1.imageTilesReady = $.ig.Delegate.prototype.combine($t1.imageTilesReady, this._tilesSoruce_ImageTilesReady.runOn(this));
		}
	}
	,
	createSeries: function () {
		return new $.ig.TileSeries();
	}
	,
	imageTilesReady: null,
	_tilesSoruce_ImageTilesReady: function (sender, e) {
		if (this.imageTilesReady != null) {
			this.imageTilesReady(this, new $.ig.EventArgs());
		}
	}
	,
	$type: new $.ig.Type('GeographicTileSeries', $.ig.GeographicShapeSeriesBase.prototype.$type)
}, true);

$.ig.util.defType('TileSeries', 'ShapeSeriesBase', {
	init: function () {
		this.__tileRefreshAction = null;
		this.__avoidRerender = false;
		$.ig.ShapeSeriesBase.prototype.init.call(this);
	},
	createView: function () {
		return new $.ig.TileSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.ShapeSeriesBase.prototype.onViewCreated.call(this, view);
		this.tileView(view);
	}
	,
	requiresShapes: function () {
		return false;
	}
	,
	_tileView: null,
	tileView: function (value) {
		if (arguments.length === 1) {
			this._tileView = value;
			return value;
		} else {
			return this._tileView;
		}
	}
	,
	tileImagery: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.tileImagery();
			if (changed) {
				var oldValue = this.tileImagery();
				this.__tileImagery = value;
				this.raisePropertyChanged($.ig.TileSeries.prototype.tileImageryPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__tileImagery;
		}
	}
	,
	__tileImagery: null,
	__actualTileImagery: null,
	actualTileImagery: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__actualTileImagery;
			this.__actualTileImagery = value;
			this.raisePropertyChanged("ActualTileImagery", oldValue, this.__actualTileImagery);
			return value;
		} else {
			return this.__actualTileImagery;
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.ShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		var oldImagery;
		var newImagery;
		switch (propertyName) {
			case $.ig.TileSeries.prototype.tileImageryPropertyName:
				oldImagery = oldValue;
				newImagery = newValue;
				if (oldImagery != null) {
					oldImagery.propertyChanged = $.ig.Delegate.prototype.remove(oldImagery.propertyChanged, this.imagery_PropertyChanged.runOn(this));
				}
				if (newImagery != null) {
					newImagery.propertyChanged = $.ig.Delegate.prototype.combine(newImagery.propertyChanged, this.imagery_PropertyChanged.runOn(this));
				}
				this.tileView().onTileImageryProvided(oldImagery, newImagery);
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case "ActualTileImagery":
				oldImagery = oldValue;
				newImagery = newValue;
				if (oldImagery != null) {
					oldImagery.imageTilesReady = $.ig.Delegate.prototype.remove(oldImagery.imageTilesReady, this.msi_ImageTilesReady.runOn(this));
					oldImagery.deferralHandler(null);
				}
				if (newImagery != null) {
					newImagery.imageTilesReady = $.ig.Delegate.prototype.combine(newImagery.imageTilesReady, this.msi_ImageTilesReady.runOn(this));
					newImagery.deferralHandler(this);
				}
				this.tileView().onBackgroundImageryProvided(oldImagery, newImagery);
				if (newImagery != null && $.ig.util.cast($.ig.XamGeographicMap.prototype.$type, this.seriesViewer()) !== null) {
					newImagery.geographicMap(this.seriesViewer());
					this.updateActualTileImagery(this.view());
					this.tileView().actualWindowRectUpdated(this.seriesViewer().actualWindowRect());
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case "ActualWindowRect":
				this.tileView().actualWindowRectUpdated(newValue);
				break;
			case "WorldRect":
				this.tileView().worldRectUpdated(newValue);
				break;
		}
	}
	,
	imagery_PropertyChanged: function (sender, e) {
		if (e.propertyName() == "MultiScaleImage") {
			this.updateActualTileImagery(this.view());
		}
	}
	,
	imageTilesReady: null,
	msi_ImageTilesReady: function (sender, e) {
		if (this.imageTilesReady != null) {
			this.imageTilesReady(this, new $.ig.EventArgs());
		}
		if (!this.__avoidRerender) {
			this.tileView().tilesDirty();
		}
	}
	,
	__tileRefreshAction: null,
	register: function (source, refresh) {
		this.__tileRefreshAction = refresh;
	}
	,
	unRegister: function (source) {
		this.__tileRefreshAction = null;
	}
	,
	deferredRefresh: function () {
		this.renderSeries(false);
	}
	,
	viewportRectChangedOverride: function (oldViewportRect, newViewportRect) {
		this.tileView().viewportUpdated();
		$.ig.ShapeSeriesBase.prototype.viewportRectChangedOverride.call(this, oldViewportRect, newViewportRect);
	}
	,
	__avoidRerender: false,
	renderSeriesOverride: function (animate) {
		$.ig.ShapeSeriesBase.prototype.renderSeriesOverride.call(this, animate);
		this.renderWithView1(animate, this.view());
	}
	,
	renderWithView1: function (animate, view) {
		var viewport;
		var window;
		var tileView = view;
		var $ret = view.getViewInfo(viewport, window);
		viewport = $ret.p0;
		window = $ret.p1;
		this.updateActualTileImagery(view);
		if (this.__tileRefreshAction != null) {
			this.__avoidRerender = true;
			this.__tileRefreshAction(animate);
			this.__avoidRerender = false;
		}
		tileView.tilesDirty();
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.ShapeSeriesBase.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		this.renderWithView(view);
	}
	,
	updateActualTileImagery: function (view) {
		var viewport;
		var window;
		var $ret = view.getViewInfo(viewport, window);
		viewport = $ret.p0;
		window = $ret.p1;
		if (this.tileImagery() != null && this.tileImagery().multiScaleImage() != null) {
			if (this.tileImagery() != this.actualTileImagery()) {
				this.actualTileImagery(this.tileImagery());
			}
			if (this.actualTileImagery() != null && !viewport.isEmpty()) {
				if (this.actualTileImagery().width() != viewport.width()) {
					this.actualTileImagery().width(viewport.width());
				}
				if (this.actualTileImagery().height() != viewport.height()) {
					this.actualTileImagery().height(viewport.height());
				}
			}
		}
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.ShapeSeriesBase.prototype.clearRendering.call(this, wipeClean, view);
		(view).clearClipping();
	}
	,
	$type: new $.ig.Type('TileSeries', $.ig.ShapeSeriesBase.prototype.$type, [$.ig.IMapRenderDeferralHandler.prototype.$type])
}, true);

$.ig.util.defType('ShapeSeriesViewBase', 'SeriesView', {
	init: function (model) {
		this.__shapeHitRegions = new $.ig.List$1($.ig.ShapeHitRegion.prototype.$type, 0);
		this.__styleSelector = null;
		this.__shapeStyle = null;
		$.ig.SeriesView.prototype.init.call(this, model);
		this.shapeModel(model);
		this.elements(this.createElementsPool());
		this.visibleElements(new $.ig.List$1($.ig.Path.prototype.$type, 0));
		this.markerPositions(new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.FlattenedShape.prototype.$type, 0));
	},
	_markerPositions: null,
	markerPositions: function (value) {
		if (arguments.length === 1) {
			this._markerPositions = value;
			return value;
		} else {
			return this._markerPositions;
		}
	}
	,
	createElementsPool: function () {
		var $self = this;
		return (function () {
			var $ret = new $.ig.Pool$1($.ig.FrameworkElement.prototype.$type);
			$ret.create($self.elementCreate.runOn($self));
			$ret.activate($self.elementActivate.runOn($self));
			$ret.disactivate($self.elementDeactivate.runOn($self));
			$ret.destroy($self.elementDestroy.runOn($self));
			return $ret;
		}());
	}
	,
	shouldRenderShape: function (bounds) {
		return bounds.width() >= this.shapeModel().shapeFilterResolutionCached() && bounds.height() >= this.shapeModel().shapeFilterResolutionCached();
	}
	,
	toPointCollections: function (points) {
		var ret = new $.ig.List$1($.ig.PointCollection.prototype.$type, 0);
		var pointColl;
		var count = points.count();
		for (var i = 0; i < count; i++) {
			pointColl = points.__inner[i];
			var coll = new $.ig.PointCollection(1, pointColl.shape());
			ret.add(coll);
		}
		return ret;
	}
	,
	_shapeModel: null,
	shapeModel: function (value) {
		if (arguments.length === 1) {
			this._shapeModel = value;
			return value;
		} else {
			return this._shapeModel;
		}
	}
	,
	__shapeHitRegions: null,
	addShape: function (i, item, bounds, points) {
		if (points != null && points.count() > 0) {
			var rings = $.ig.ShapeSeriesViewBase.prototype.toPointCollections(points);
			bounds.intersect(this.viewport());
			if (!this.shouldRenderShape(bounds)) {
			} else {
				var shape = this.getShapeGeometry(i, rings);
				if (shape != null) {
					var element = this.getShapeElement(this.shapeCount(), item);
					for (var j = 0; j < points.count(); j++) {
						var r = new $.ig.ShapeHitRegion();
						r._bounds = points.__inner[j].bounds();
						r._points = points.__inner[j].shape();
						r._index = i;
						r._element = element;
						this.__shapeHitRegions.add(r);
					}
					this.clearValues(element);
					this.applyData(element, shape);
					this.applyStyling1(element, item);
					this.shapeCount(this.shapeCount() + 1);
				}
			}
		}
	}
	,
	getShapeElement: function (i, item) {
		var element = this.elements().item(this.shapeCount());
		if (element.dataContext() != null) {
			(element.dataContext()).item(item);
		}
		return element;
	}
	,
	applyData: function (element, data) {
	}
	,
	applyStyling: function (view, shape, item) {
		if (shape.__fill == null) {
			shape.__fill = view.model().actualBrush();
		}
		if (shape.__stroke == null) {
			shape.__stroke = view.model().actualOutline();
		}
	}
	,
	applyStyling1: function (shape, item) {
		if (shape == null) {
			return;
		}
		if (this.__styleSelector != null) {
			shape.style(this.__styleSelector.selectStyle(item, shape));
		}
		if (shape.style() == null) {
			shape.style(this.__shapeStyle);
		}
	}
	,
	clearValues: function (element) {
	}
	,
	addSmallShape: function (bounds, brush) {
		var left = $.ig.truncate(Math.floor(bounds.left()));
		var right = $.ig.truncate(Math.ceil(bounds.right()));
		var top = $.ig.truncate(Math.floor(bounds.top()));
		var bottom = $.ig.truncate(Math.ceil(bounds.bottom()));
		for (var x = left; x < right; x++) {
			for (var y = top; y < bottom; y++) {
				this.setPixel(x, y, brush);
			}
		}
	}
	,
	setPixel: function (x, y, brush) {
	}
	,
	getShapeGeometry: function (i, points) {
	}
	,
	initializeShapes: function () {
		this.shapeCount(0);
	}
	,
	finalizeShapes: function () {
		this.elements().count(this.shapeCount());
		this.makeDirty();
	}
	,
	_shapeCount: 0,
	shapeCount: function (value) {
		if (arguments.length === 1) {
			this._shapeCount = value;
			return value;
		} else {
			return this._shapeCount;
		}
	}
	,
	regenerateBitmap: function (width, height) {
	}
	,
	clearRendering: function () {
		this.elements().count(0);
		this.makeDirty();
	}
	,
	_visibleElements: null,
	visibleElements: function (value) {
		if (arguments.length === 1) {
			this._visibleElements = value;
			return value;
		} else {
			return this._visibleElements;
		}
	}
	,
	elementCreate: function () {
		var s = this.shapeModel();
		var path = (function () {
			var $ret = new $.ig.Path();
			$ret.dataContext((function () {
				var $ret = new $.ig.DataContext();
				$ret.series(s);
				return $ret;
			}()));
			return $ret;
		}());
		this.visibleElements().add(path);
		return path;
	}
	,
	elementActivate: function (path) {
		path.__visibility = $.ig.Visibility.prototype.visible;
	}
	,
	elementDeactivate: function (path) {
		(path.dataContext()).item(null);
		path.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	elementDestroy: function (path) {
		this.visibleElements().remove(path);
	}
	,
	_elements: null,
	elements: function (value) {
		if (arguments.length === 1) {
			this._elements = value;
			return value;
		} else {
			return this._elements;
		}
	}
	,
	getShapeGeometry1: function (i, points, fill) {
		if (points == null) {
			return null;
		}
		var pathGeometry = new $.ig.PathGeometry();
		var ring;
		var count = points.count();
		for (var j = 0; j < count; j++) {
			ring = points.__inner[j];
			if (ring.count() < 1) {
				continue;
			}
			var polylineSegment = new $.ig.PolyLineSegment();
			polylineSegment.__points = ring;
			var pathFigure = new $.ig.PathFigure();
			pathFigure.__isFilled = fill;
			pathFigure.__isClosed = fill;
			pathFigure.__startPoint = ring.__inner[0];
			pathFigure.__segments.add(polylineSegment);
			pathGeometry.figures().add(pathFigure);
		}
		return pathGeometry;
	}
	,
	setupItemAppearanceOverride: function (item, index) {
		$.ig.SeriesView.prototype.setupItemAppearanceOverride.call(this, item, index);
		var path = item;
		path.__fill = this.model().actualBrush();
		path.__stroke = this.model().actualOutline();
		path.strokeThickness(this.model().thickness());
		if (path.style() != null) {
			this.context().applyStyle(path, path.style());
		}
	}
	,
	setupItemHitAppearanceOverride: function (item, index) {
		$.ig.SeriesView.prototype.setupItemHitAppearanceOverride.call(this, item, index);
		var path = item;
		var hitBrush = this.getHitBrush1(index);
		path.__fill = hitBrush;
		path.__stroke = hitBrush;
		path.strokeThickness(this.model().thickness() + $.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1);
	}
	,
	renderOverride: function (context, isHitContext) {
		$.ig.SeriesView.prototype.renderOverride.call(this, context, isHitContext);
		for (var i = 0; i < this.visibleElements().count(); i++) {
			var path = this.visibleElements().__inner[i];
			if (path.__visibility != $.ig.Visibility.prototype.collapsed) {
				this.setupItemAppearance(path, i, isHitContext);
				context.renderPath(path);
			}
		}
	}
	,
	__styleSelector: null,
	__shapeStyle: null,
	shapeStyleSelectorChanged: function (styleSelector) {
		this.__styleSelector = styleSelector;
	}
	,
	shapeStyleChanged: function (shapeStyle) {
		this.__shapeStyle = shapeStyle;
	}
	,
	getHitShape: function (position) {
		if (this.__shapeHitRegions.count() == 0) {
			return null;
		}
		var xVal = position.__x;
		var yVal = position.__y;
		var hitRegionsCount = this.__shapeHitRegions.count();
		var regions = this.__shapeHitRegions;
		var currRegion = null;
		for (var i = 0; i < hitRegionsCount; i++) {
			currRegion = regions.__inner[i];
			if (xVal >= currRegion._bounds.left() && xVal <= currRegion._bounds.right() && yVal >= currRegion._bounds.top() && yVal <= currRegion._bounds.bottom()) {
				if ($.ig.PolygonUtil.prototype.polygonContainsPoint(currRegion._points, position)) {
					return currRegion._element;
				}
			}
		}
		return null;
	}
	,
	__boundsLeft: null,
	__boundsTop: null,
	__boundsRight: null,
	__boundsBottom: null,
	provideBounds: function (boundsLeft, boundsTop, boundsRight, boundsBottom) {
		this.__boundsLeft = boundsLeft;
		this.__boundsTop = boundsTop;
		this.__boundsRight = boundsRight;
		this.__boundsBottom = boundsBottom;
		this.__shapeHitRegions.clear();
	}
	,
	updateClipRect: function () {
		var $self = this;
		var r = this.viewport();
		var p = (function () {
			var $ret = new $.ig.Path();
			$ret.style($self.__shapeStyle);
			return $ret;
		}());
		p.strokeThickness(1);
		if (this.context() != null) {
			this.context().applyStyle(p, p.style());
		}
		this.shapeModel()._clipRect = $.ig.RectUtil.prototype.inflate(r, p.strokeThickness());
	}
	,
	exportViewShapes: function (svd) {
		$.ig.SeriesView.prototype.exportViewShapes.call(this, svd);
		var toSort = new $.ig.List$1($.ig.Tuple$2.prototype.$type.specialize($.ig.Point.prototype.$type, $.ig.Path.prototype.$type), 0);
		for (var i = 0; i < this.__shapeHitRegions.count(); i++) {
			var hr = this.__shapeHitRegions.__inner[i];
			toSort.add(new $.ig.Tuple$2($.ig.Point.prototype.$type, $.ig.Path.prototype.$type, { __x: hr._bounds.left(), __y: hr._bounds.top(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, hr._element));
		}
		toSort.sort2(function (p1, p2) {
			if (p1.item1().__x < p2.item1().__x) {
				return -1;
			}
			if (p1.item1().__x > p2.item1().__x) {
				return 1;
			}
			if (p1.item1().__y < p2.item1().__y) {
				return -1;
			}
			if (p1.item1().__y > p2.item1().__y) {
				return 1;
			}
			return 0;
		});
		for (var i1 = 0; i1 < toSort.count(); i1++) {
			if (toSort.__inner[i1].item2().__visibility != $.ig.Visibility.prototype.collapsed) {
				var pd = new $.ig.PathVisualData(1, "shape" + i1, toSort.__inner[i1].item2());
				pd.tags().add("Main");
				svd.shapes().add(pd);
			}
		}
	}
	,
	$type: new $.ig.Type('ShapeSeriesViewBase', $.ig.SeriesView.prototype.$type)
}, true);

$.ig.util.defType('TileSeriesView', 'ShapeSeriesViewBase', {
	init: function (model) {
		this.__clipGeometry = new $.ig.GeometryGroup();
		$.ig.ShapeSeriesViewBase.prototype.init.call(this, model);
		this.tileModel(model);
	},
	_tileModel: null,
	tileModel: function (value) {
		if (arguments.length === 1) {
			this._tileModel = value;
			return value;
		} else {
			return this._tileModel;
		}
	}
	,
	applyData: function (element, data) {
		if (data.figures().count() > 0) {
			this.__clipGeometry.children().add(data);
		}
	}
	,
	getShapeGeometry: function (i, points) {
		return this.getShapeGeometry1(i, points, true);
	}
	,
	getShapeElement: function (i, item) {
		return null;
	}
	,
	clearValues: function (element) {
	}
	,
	applyStyling1: function (shape, item) {
	}
	,
	__clipGeometry: null,
	initializeShapes: function () {
		$.ig.ShapeSeriesViewBase.prototype.initializeShapes.call(this);
		this.__clipGeometry.children().clear();
	}
	,
	finalizeShapes: function () {
		$.ig.ShapeSeriesViewBase.prototype.finalizeShapes.call(this);
		this.makeDirty();
	}
	,
	actualWindowRectUpdated: function (actualWindowRect) {
		if (this.tileModel().tileImagery() != null) {
			this.tileModel().tileImagery().windowRect(actualWindowRect);
		}
	}
	,
	_offscreen: null,
	offscreen: function (value) {
		if (arguments.length === 1) {
			this._offscreen = value;
			return value;
		} else {
			return this._offscreen;
		}
	}
	,
	_offscreenContext: null,
	offscreenContext: function (value) {
		if (arguments.length === 1) {
			this._offscreenContext = value;
			return value;
		} else {
			return this._offscreenContext;
		}
	}
	,
	onBackgroundImageryProvided: function (oldImagery, newImagery) {
		if (this.offscreen() == null) {
			this.offscreen($("<canvas></canvas>"));
			var cont = (this.offscreen()[0]).getContext("2d");
			this.offscreenContext(new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), cont));
			this.offscreen().attr("width", this.viewport().width().toString());
			this.offscreen().attr("height", this.viewport().height().toString());
		}
		if (oldImagery != null) {
			oldImagery.provideContext(null);
			oldImagery.imagesChanged = $.ig.Delegate.prototype.remove(oldImagery.imagesChanged, this.newImagery_ImagesChanged.runOn(this));
		}
		if (newImagery != null) {
			newImagery.provideContext(this.offscreenContext());
			newImagery.provideViewport(this.viewport());
			newImagery.imagesChanged = $.ig.Delegate.prototype.combine(newImagery.imagesChanged, this.newImagery_ImagesChanged.runOn(this));
		}
	}
	,
	newImagery_ImagesChanged: function (sender, e) {
		this.tileModel().renderSeries(false);
	}
	,
	onTileImageryProvided: function (oldImagery, newImagery) {
	}
	,
	viewportUpdated: function () {
		if (this.offscreen() != null) {
			this.offscreen().attr("width", this.viewport().width().toString());
			this.offscreen().attr("height", this.viewport().height().toString());
		}
		if (this.tileModel().tileImagery() != null) {
			this.tileModel().tileImagery().provideViewport(this.viewport());
		}
	}
	,
	renderOverride: function (context, isHitContext) {
		$.ig.ShapeSeriesViewBase.prototype.renderOverride.call(this, context, isHitContext);
		if (isHitContext) {
			return;
		}
		if (this.offscreen() == null) {
			return;
		}
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().top()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		this.context().drawImage1(this.offscreen()[0], 1, viewportLeft, viewportTop, viewportWidth, viewportHeight, viewportLeft, viewportTop, viewportWidth, viewportHeight);
	}
	,
	tilesDirty: function () {
		this.makeDirty();
	}
	,
	clearClipping: function () {
	}
	,
	worldRectUpdated: function (rect) {
		if (this.tileModel().tileImagery() != null) {
			this.tileModel().tileImagery().needsRefresh();
		}
	}
	,
	$type: new $.ig.Type('TileSeriesView', $.ig.ShapeSeriesViewBase.prototype.$type)
}, true);

$.ig.util.defType('GeographicMapImageryView', 'Object', {
	init: function (model) {
		$.ig.Object.prototype.init.call(this);
		this.mapViewport($.ig.Rect.prototype.empty());
		this.model(model);
		this.model().multiScaleImage(new $.ig.XamMultiScaleImage());
		this.model().multiScaleImage().source(this.model().tileSource());
		this.model().onMSIProvided();
	},
	_model: null,
	model: function (value) {
		if (arguments.length === 1) {
			this._model = value;
			return value;
		} else {
			return this._model;
		}
	}
	,
	needsRefresh: function () {
		if (this.mapViewport().isEmpty()) {
			return;
		}
		this.model().refresh(new $.ig.Size(1, this.mapViewport().width(), this.mapViewport().height()));
	}
	,
	_mapViewport: null,
	mapViewport: function (value) {
		if (arguments.length === 1) {
			this._mapViewport = value;
			return value;
		} else {
			return this._mapViewport;
		}
	}
	,
	onContextProvided: function (context) {
		this.model().multiScaleImage().provideContext(context);
	}
	,
	onViewportProvided: function (mapViewport) {
		this.mapViewport(mapViewport);
		this.model().multiScaleImage().provideViewport(mapViewport);
		this.model().needsRefresh();
	}
	,
	$type: new $.ig.Type('GeographicMapImageryView', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('BingMapsMapImageryView', 'GeographicMapImageryView', {
	init: function (model) {
		$.ig.GeographicMapImageryView.prototype.init.call(this, model);
		this.bingModel(model);
	},
	_bingModel: null,
	bingModel: function (value) {
		if (arguments.length === 1) {
			this._bingModel = value;
			return value;
		} else {
			return this._bingModel;
		}
	}
	,
	onInit: function () {
		this.bingModel().subDomains(new $.ig.ObservableCollection$1(String, 0));
		(this.bingModel().tileSource()).tilePath(this.bingModel().actualTilePath());
		(this.bingModel().tileSource()).subDomains(this.bingModel().actualSubDomains());
		(this.bingModel().tileSource()).cultureName(this.bingModel().cultureName());
	}
	,
	onTilePathPropertyChanged: function () {
		(this.bingModel().tileSource()).tilePath(this.bingModel().actualTilePath());
	}
	,
	onCultureNamePropertyChanged: function () {
		(this.bingModel().tileSource()).cultureName(this.bingModel().cultureName());
	}
	,
	onSubDomainsPropertyChanged: function () {
		(this.bingModel().tileSource()).subDomains(this.bingModel().actualSubDomains());
	}
	,
	$type: new $.ig.Type('BingMapsMapImageryView', $.ig.GeographicMapImageryView.prototype.$type)
}, true);

$.ig.util.defType('CloudMadeMapImageryView', 'GeographicMapImageryView', {
	init: function (model) {
		$.ig.GeographicMapImageryView.prototype.init.call(this, model);
		this.cloudModel(model);
	},
	_cloudModel: null,
	cloudModel: function (value) {
		if (arguments.length === 1) {
			this._cloudModel = value;
			return value;
		} else {
			return this._cloudModel;
		}
	}
	,
	onKeyPropertyChanged: function () {
		(this.cloudModel().tileSource()).key(this.cloudModel().key());
	}
	,
	onParameterPropertyChanged: function () {
		(this.cloudModel().tileSource()).parameter(this.cloudModel().parameter());
	}
	,
	onInit: function () {
		(this.cloudModel().tileSource()).key(this.cloudModel().key());
		(this.cloudModel().tileSource()).parameter(this.cloudModel().parameter());
	}
	,
	$type: new $.ig.Type('CloudMadeMapImageryView', $.ig.GeographicMapImageryView.prototype.$type)
}, true);

$.ig.util.defType('GeographicProportionalSymbolSeriesView', 'HostSeriesView$1', {
	init: function (model) {
		$.ig.HostSeriesView$1.prototype.init.call(this, $.ig.BubbleSeries.prototype.$type, model);
		this.proportionalModel(model);
	},
	_proportionalModel: null,
	proportionalModel: function (value) {
		if (arguments.length === 1) {
			this._proportionalModel = value;
			return value;
		} else {
			return this._proportionalModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.HostSeriesView$1.prototype.onHostedSeriesUpdated.call(this);
		this.proportionalModel().hostedSeries().xMemberPath(this.proportionalModel().longitudeMemberPath());
		this.proportionalModel().hostedSeries().yMemberPath(this.proportionalModel().latitudeMemberPath());
		this.proportionalModel().hostedSeries().markerType(this.proportionalModel().markerType());
		this.proportionalModel().hostedSeries().markerTemplate(this.proportionalModel().markerTemplate());
		this.proportionalModel().hostedSeries().radiusMemberPath(this.proportionalModel().radiusMemberPath());
		this.proportionalModel().hostedSeries().radiusScale(this.proportionalModel().radiusScale());
		this.proportionalModel().hostedSeries().labelMemberPath(this.proportionalModel().labelMemberPath());
		this.proportionalModel().hostedSeries().fillMemberPath(this.proportionalModel().fillMemberPath());
		this.proportionalModel().hostedSeries().fillScale(this.proportionalModel().fillScale());
	}
	,
	onLongitudeMemberPathUpdated: function () {
		this.proportionalModel().hostedSeries().xMemberPath(this.proportionalModel().longitudeMemberPath());
	}
	,
	onLatitudeMemberPathUpdated: function () {
		this.proportionalModel().hostedSeries().yMemberPath(this.proportionalModel().latitudeMemberPath());
	}
	,
	onMarkerTypeUpdated: function () {
		this.proportionalModel().hostedSeries().markerType(this.proportionalModel().markerType());
	}
	,
	onMarkerTemplateUpdated: function () {
		this.proportionalModel().hostedSeries().markerTemplate(this.proportionalModel().markerTemplate());
	}
	,
	onMaximumMarkersUpdated: function () {
		this.proportionalModel().hostedSeries().maximumMarkers(this.proportionalModel().maximumMarkers());
	}
	,
	onXAxisUpdated: function () {
		this.proportionalModel().hostedSeries().xAxis((this.proportionalModel().seriesViewer()).xAxis());
	}
	,
	onYAxisUpdated: function () {
		this.proportionalModel().hostedSeries().yAxis((this.proportionalModel().seriesViewer()).yAxis());
	}
	,
	onSeriesViewerUpdated: function () {
		if (this.proportionalModel().seriesViewer() == null) {
			this.proportionalModel().hostedSeries().xAxis(null);
			this.proportionalModel().hostedSeries().yAxis(null);
			return;
		}
		this.proportionalModel().hostedSeries().xAxis((this.proportionalModel().seriesViewer()).xAxis());
		this.proportionalModel().hostedSeries().yAxis((this.proportionalModel().seriesViewer()).yAxis());
	}
	,
	onMarkerBrushUpdated: function () {
		this.proportionalModel().hostedSeries().markerBrush(this.proportionalModel().markerBrush());
	}
	,
	onMarkerOutlineUpdated: function () {
		this.proportionalModel().hostedSeries().markerOutline(this.proportionalModel().markerOutline());
	}
	,
	radiusMemberPathUpdated: function () {
		this.proportionalModel().hostedSeries().radiusMemberPath(this.proportionalModel().radiusMemberPath());
	}
	,
	radiusScaleUpdated: function () {
		this.proportionalModel().hostedSeries().radiusScale(this.proportionalModel().radiusScale());
	}
	,
	labelMemberPathUpdated: function () {
		this.proportionalModel().hostedSeries().labelMemberPath(this.proportionalModel().labelMemberPath());
	}
	,
	fillScaleUpdated: function () {
		this.proportionalModel().hostedSeries().fillScale(this.proportionalModel().fillScale());
	}
	,
	fillMemberPathUpdated: function () {
		this.proportionalModel().hostedSeries().fillMemberPath(this.proportionalModel().fillMemberPath());
	}
	,
	$type: new $.ig.Type('GeographicProportionalSymbolSeriesView', $.ig.HostSeriesView$1.prototype.$type.specialize($.ig.BubbleSeries.prototype.$type))
}, true);

$.ig.util.defType('XYTriangulatingSeries', 'Series', {
	init: function () {
		$.ig.Series.prototype.init.call(this);
	},
	xMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.xMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.xMemberPathProperty);
		}
	}
	,
	yMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.yMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.yMemberPathProperty);
		}
	}
	,
	__xColumn: null,
	xColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.xColumn() != value;
			if (changed) {
				var oldValue = this.xColumn();
				this.__xColumn = value;
				this.raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._xColumnPropertyName, oldValue, this.xColumn());
			}
			return value;
		} else {
			return this.__xColumn;
		}
	}
	,
	__yColumn: null,
	yColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.yColumn() != value;
			if (changed) {
				var oldValue = this.yColumn();
				this.__yColumn = value;
				this.raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._yColumnPropertyName, oldValue, this.yColumn());
			}
			return value;
		} else {
			return this.__yColumn;
		}
	}
	,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.xAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.xAxisProperty);
		}
	}
	,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.yAxisProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.yAxisProperty);
		}
	}
	,
	_autoTriangulated: false,
	autoTriangulated: function (value) {
		if (arguments.length === 1) {
			this._autoTriangulated = value;
			return value;
		} else {
			return this._autoTriangulated;
		}
	}
	,
	renderSeriesOverride: function (animate) {
		var needsTriangulation = this.fastItemsSource() != null && this.fastTrianglesSource() == null && this.triangulation() == null && this.xColumn() != null && this.yColumn() != null && this.xColumn().count() >= 3 && this.yColumn().count() >= 3 && !this.autoTriangulated();
		if (needsTriangulation) {
			this.startTriangulation();
			this.autoTriangulated(true);
		}
	}
	,
	startTriangulation: function () {
		this.__triangulator = new $.ig.Triangulator(this.fastItemsSource().count(), this.xColumn(), this.yColumn());
		var $t = this.__triangulator;
		$t.triangulationStatusChanged = $.ig.Delegate.prototype.combine($t.triangulationStatusChanged, this.triangulator_TriangulationStatusChanged.runOn(this));
		this.__triangulator.triangulateAsync();
	}
	,
	cancelTriangulation: function () {
		if (this.__triangulator == null) {
			return;
		}
		this.__triangulator.cancel();
		var $t = this.__triangulator;
		$t.triangulationStatusChanged = $.ig.Delegate.prototype.remove($t.triangulationStatusChanged, this.triangulator_TriangulationStatusChanged.runOn(this));
		this.__triangulator = null;
	}
	,
	__triangulator: null,
	triangulationStatusChanged: null,
	triangulator_TriangulationStatusChanged: function (sender, args) {
		var $self = this;
		if (this.triangulationStatusChanged != null) {
			this.triangulationStatusChanged(this, new $.ig.TriangulationStatusEventArgs(args.currentStatus()));
		}
		if (args.currentStatus() >= 100) {
			if (this.__triangulator == null) {
				return;
			}
			var $t = this.__triangulator;
			$t.triangulationStatusChanged = $.ig.Delegate.prototype.remove($t.triangulationStatusChanged, this.triangulator_TriangulationStatusChanged.runOn(this));
			this.triangulation(this.__triangulator.getResult());
			this.__triangulator = null;
			var internalFastItemsSource = (function () {
				var $ret = new $.ig.FastItemsSource();
				$ret.itemsSource($self.triangulation());
				return $ret;
			}());
			this.triangleVertexColumn1(this.registerTriangleIntColumn(internalFastItemsSource, "v1"));
			this.triangleVertexColumn2(this.registerTriangleIntColumn(internalFastItemsSource, "v2"));
			this.triangleVertexColumn3(this.registerTriangleIntColumn(internalFastItemsSource, "v3"));
			this.renderSeries(false);
		}
	}
	,
	registerTriangleIntColumn: function (itemsSource, memberPath) {
		if (memberPath == null) {
			return itemsSource.registerColumnInt(null, null, false);
		}
		var coercionMethod = null;
		var info = $.ig.SeriesViewer.prototype.getCoercionMethod(memberPath, this.coercionMethods());
		memberPath = info.memberPath();
		coercionMethod = info.coercionMethod();
		return itemsSource.registerColumnInt(memberPath, coercionMethod, this.expectFunctions());
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		var $self = this;
		$.ig.Series.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				var oldFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue);
				if (oldFastItemsSource != null) {
					oldFastItemsSource.deregisterColumn(this.xColumn());
					oldFastItemsSource.deregisterColumn(this.yColumn());
					this.xColumn(null);
					this.yColumn(null);
				}
				this.refreshAutoTriangulation();
				var newFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue);
				if (newFastItemsSource != null) {
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				this.renderSeries(false);
				break;
			case $.ig.XYTriangulatingSeries.prototype._xMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.xColumn());
					this.xColumn(this.registerDoubleColumn(this.xMemberPath()));
				}
				this.refreshAutoTriangulation();
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype._yMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.yColumn());
					this.yColumn(this.registerDoubleColumn(this.yMemberPath()));
				}
				this.refreshAutoTriangulation();
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype.xAxisPropertyName:
				if (oldValue != null) {
					($.ig.util.cast($.ig.Axis.prototype.$type, oldValue)).deregisterSeries(this);
				}
				if (newValue != null) {
					($.ig.util.cast($.ig.Axis.prototype.$type, newValue)).registerSeries(this);
				}
				if ((this.xAxis() != null && !this.xAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype.yAxisPropertyName:
				if (oldValue != null) {
					($.ig.util.cast($.ig.Axis.prototype.$type, oldValue)).deregisterSeries(this);
				}
				if (newValue != null) {
					($.ig.util.cast($.ig.Axis.prototype.$type, newValue)).registerSeries(this);
				}
				if ((this.yAxis() != null && !this.yAxis().updateRange()) || (newValue == null && oldValue != null)) {
					this.renderSeries(false);
				}
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype._trianglesSourcePropertyName:
				if (this.trianglesSource() != null) {
					this.cancelTriangulation();
					this.fastTrianglesSource((function () {
						var $ret = new $.ig.FastItemsSource();
						$ret.itemsSource($self.trianglesSource());
						return $ret;
					}()));
				} else {
					this.fastTrianglesSource(null);
				}
				break;
			case $.ig.XYTriangulatingSeries.prototype._fastTrianglesSourcePropertyName:
				var oldFastTrianglesSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue);
				if (oldFastTrianglesSource != null) {
					oldFastTrianglesSource.deregisterColumn(this.triangleVertexColumn1());
					oldFastTrianglesSource.deregisterColumn(this.triangleVertexColumn2());
					oldFastTrianglesSource.deregisterColumn(this.triangleVertexColumn3());
					this.triangleVertexColumn1(null);
					this.triangleVertexColumn2(null);
					this.triangleVertexColumn3(null);
				}
				var newFastTrianglesSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue);
				if (newFastTrianglesSource != null) {
					this.triangleVertexColumn1(this.registerTriangleIntColumn(newFastTrianglesSource, this.triangleVertexMemberPath1()));
					this.triangleVertexColumn2(this.registerTriangleIntColumn(newFastTrianglesSource, this.triangleVertexMemberPath2()));
					this.triangleVertexColumn3(this.registerTriangleIntColumn(newFastTrianglesSource, this.triangleVertexMemberPath3()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath1PropertyName:
				if (this.fastTrianglesSource() != null) {
					this.fastTrianglesSource().deregisterColumn(this.triangleVertexColumn1());
					this.triangleVertexColumn1(this.registerTriangleIntColumn(this.fastTrianglesSource(), this.triangleVertexMemberPath1()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath2PropertyName:
				if (this.fastTrianglesSource() != null) {
					this.fastTrianglesSource().deregisterColumn(this.triangleVertexColumn2());
					this.triangleVertexColumn2(this.registerTriangleIntColumn(this.fastTrianglesSource(), this.triangleVertexMemberPath2()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath3PropertyName:
				if (this.fastTrianglesSource() != null) {
					this.fastTrianglesSource().deregisterColumn(this.triangleVertexColumn3());
					this.triangleVertexColumn3(this.registerTriangleIntColumn(this.fastTrianglesSource(), this.triangleVertexMemberPath3()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	validateSeries: function (viewportRect, windowRect, view) {
		return $.ig.Series.prototype.validateSeries.call(this, viewportRect, windowRect, view) && this.fastItemsSource() != null && this.xAxis() != null && this.yAxis() != null && viewportRect.width() > 0 && viewportRect.height() > 0 && this.triangleVertexColumn1() != null && this.triangleVertexColumn1().count() > 0 && this.triangleVertexColumn2() != null && this.triangleVertexColumn2().count() > 0 && this.triangleVertexColumn3() != null && this.triangleVertexColumn3().count() > 0 && this.xColumn() != null && this.xColumn().count() > 0 && this.yColumn() != null && this.yColumn().count() > 0;
	}
	,
	refreshAutoTriangulation: function () {
		if (this.trianglesSource() == null) {
			this.triangulation(null);
			this.triangleVertexColumn1(null);
			this.triangleVertexColumn2(null);
			this.triangleVertexColumn3(null);
			this.autoTriangulated(false);
		}
	}
	,
	_triangulation: null,
	triangulation: function (value) {
		if (arguments.length === 1) {
			this._triangulation = value;
			return value;
		} else {
			return this._triangulation;
		}
	}
	,
	invalidateAxes: function () {
		$.ig.Series.prototype.invalidateAxes.call(this);
		if (this.xAxis() != null) {
			this.xAxis().renderAxis();
		}
		if (this.yAxis() != null) {
			this.yAxis().renderAxis();
		}
	}
	,
	dataUpdatedOverride: function (action, position, count, propertyName) {
		$.ig.Series.prototype.dataUpdatedOverride.call(this, action, position, count, propertyName);
		this.refreshAutoTriangulation();
		this.renderSeries(false);
	}
	,
	getRange: function (axis) {
		if (axis != null && axis == this.xAxis() && this.xColumn() != null) {
			return new $.ig.AxisRange(this.xColumn().minimum(), this.xColumn().maximum());
		}
		if (axis != null && axis == this.yAxis() && this.yColumn() != null) {
			return new $.ig.AxisRange(this.yColumn().minimum(), this.yColumn().maximum());
		}
		return null;
	}
	,
	windowRectChangedOverride: function (oldWindowRect, newWindowRect) {
		this.renderSeries(false);
	}
	,
	viewportRectChangedOverride: function (oldViewportRect, newViewportRect) {
		this.renderSeries(false);
	}
	,
	trianglesSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.trianglesSourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.trianglesSourceProperty);
		}
	}
	,
	fastTrianglesSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.fastTrianglesSourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.fastTrianglesSourceProperty);
		}
	}
	,
	triangleVertexMemberPath1: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath1Property, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath1Property);
		}
	}
	,
	triangleVertexMemberPath2: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath2Property, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath2Property);
		}
	}
	,
	triangleVertexMemberPath3: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath3Property, value);
			return value;
		} else {
			return this.getValue($.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath3Property);
		}
	}
	,
	__triangleVertexColumn1: null,
	triangleVertexColumn1: function (value) {
		if (arguments.length === 1) {
			var changed = this.triangleVertexColumn1() != value;
			if (changed) {
				var oldValue = this.triangleVertexColumn1();
				this.__triangleVertexColumn1 = value;
				this.raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexColumn1PropertyName, oldValue, this.triangleVertexColumn1());
			}
			return value;
		} else {
			return this.__triangleVertexColumn1;
		}
	}
	,
	__triangleVertexColumn2: null,
	triangleVertexColumn2: function (value) {
		if (arguments.length === 1) {
			var changed = this.triangleVertexColumn2() != value;
			if (changed) {
				var oldValue = this.triangleVertexColumn2();
				this.__triangleVertexColumn2 = value;
				this.raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexColumn2PropertyName, oldValue, this.triangleVertexColumn2());
			}
			return value;
		} else {
			return this.__triangleVertexColumn2;
		}
	}
	,
	__triangleVertexColumn3: null,
	triangleVertexColumn3: function (value) {
		if (arguments.length === 1) {
			var changed = this.triangleVertexColumn3() != value;
			if (changed) {
				var oldValue = this.triangleVertexColumn3();
				this.__triangleVertexColumn3 = value;
				this.raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexColumn3PropertyName, oldValue, this.triangleVertexColumn3());
			}
			return value;
		} else {
			return this.__triangleVertexColumn3;
		}
	}
	,
	$type: new $.ig.Type('XYTriangulatingSeries', $.ig.Series.prototype.$type)
}, true);

$.ig.util.defType('GeographicXYTriangulatingSeriesView', 'HostSeriesView$1', {
	init: function (model) {
		$.ig.HostSeriesView$1.prototype.init.call(this, $.ig.XYTriangulatingSeries.prototype.$type, model);
		this.geographicXYModel(model);
	},
	_geographicXYModel: null,
	geographicXYModel: function (value) {
		if (arguments.length === 1) {
			this._geographicXYModel = value;
			return value;
		} else {
			return this._geographicXYModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.HostSeriesView$1.prototype.onHostedSeriesUpdated.call(this);
		this.geographicXYModel().hostedSeries().xMemberPath(this.geographicXYModel().longitudeMemberPath());
		this.geographicXYModel().hostedSeries().yMemberPath(this.geographicXYModel().latitudeMemberPath());
		this.geographicXYModel().hostedSeries().trianglesSource(this.geographicXYModel().trianglesSource());
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath1(this.geographicXYModel().triangleVertexMemberPath1());
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath2(this.geographicXYModel().triangleVertexMemberPath2());
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath3(this.geographicXYModel().triangleVertexMemberPath3());
	}
	,
	onSeriesViewerUpdated: function () {
		if (this.geographicXYModel().seriesViewer() == null) {
			this.geographicXYModel().hostedSeries().xAxis(null);
			this.geographicXYModel().hostedSeries().yAxis(null);
			return;
		}
		this.geographicXYModel().hostedSeries().xAxis((this.geographicXYModel().seriesViewer()).xAxis());
		this.geographicXYModel().hostedSeries().yAxis((this.geographicXYModel().seriesViewer()).yAxis());
	}
	,
	longitudeMemberPathUpdated: function () {
		this.geographicXYModel().hostedSeries().xMemberPath(this.geographicXYModel().longitudeMemberPath());
	}
	,
	latitudeMemberPathUpdated: function () {
		this.geographicXYModel().hostedSeries().yMemberPath(this.geographicXYModel().latitudeMemberPath());
	}
	,
	trianglesSourceUpdated: function () {
		this.geographicXYModel().hostedSeries().trianglesSource(this.geographicXYModel().trianglesSource());
	}
	,
	triangleVertexMemberPath1Updated: function () {
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath1(this.geographicXYModel().triangleVertexMemberPath1());
	}
	,
	triangleVertexMemberPath2Updated: function () {
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath2(this.geographicXYModel().triangleVertexMemberPath2());
	}
	,
	triangleVertexMemberPath3Updated: function () {
		this.geographicXYModel().hostedSeries().triangleVertexMemberPath3(this.geographicXYModel().triangleVertexMemberPath3());
	}
	,
	$type: new $.ig.Type('GeographicXYTriangulatingSeriesView', $.ig.HostSeriesView$1.prototype.$type.specialize($.ig.XYTriangulatingSeries.prototype.$type))
}, true);

$.ig.util.defType('GeographicContourLineSeriesView', 'GeographicXYTriangulatingSeriesView', {
	init: function (model) {
		$.ig.GeographicXYTriangulatingSeriesView.prototype.init.call(this, model);
		this.geographicContourLineModel(model);
	},
	_geographicContourLineModel: null,
	geographicContourLineModel: function (value) {
		if (arguments.length === 1) {
			this._geographicContourLineModel = value;
			return value;
		} else {
			return this._geographicContourLineModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicXYTriangulatingSeriesView.prototype.onHostedSeriesUpdated.call(this);
		if (this.geographicContourLineModel().hostedSeries() != null) {
			this.geographicContourLineModel().hostedContourLineSeries().fillScale(this.geographicContourLineModel().fillScale());
			this.geographicContourLineModel().hostedContourLineSeries().valueMemberPath(this.geographicContourLineModel().valueMemberPath());
		}
	}
	,
	fillScaleUpdated: function () {
		if (this.geographicContourLineModel().hostedContourLineSeries() != null) {
			this.geographicContourLineModel().hostedContourLineSeries().fillScale(this.geographicContourLineModel().fillScale());
		}
	}
	,
	valueMemberPathUpdated: function () {
		if (this.geographicContourLineModel().hostedContourLineSeries() != null) {
			this.geographicContourLineModel().hostedContourLineSeries().valueMemberPath(this.geographicContourLineModel().valueMemberPath());
		}
	}
	,
	$type: new $.ig.Type('GeographicContourLineSeriesView', $.ig.GeographicXYTriangulatingSeriesView.prototype.$type)
}, true);

$.ig.util.defType('GeographicHighDensityScatterSeriesView', 'HostSeriesView$1', {
	init: function (model) {
		$.ig.HostSeriesView$1.prototype.init.call(this, $.ig.HighDensityScatterSeries.prototype.$type, model);
		this.geographicHighDensityScatterModel(model);
	},
	_geographicHighDensityScatterModel: null,
	geographicHighDensityScatterModel: function (value) {
		if (arguments.length === 1) {
			this._geographicHighDensityScatterModel = value;
			return value;
		} else {
			return this._geographicHighDensityScatterModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.HostSeriesView$1.prototype.onHostedSeriesUpdated.call(this);
		this.geographicHighDensityScatterModel().hostedSeries().xMemberPath(this.geographicHighDensityScatterModel().longitudeMemberPath());
		this.geographicHighDensityScatterModel().hostedSeries().yMemberPath(this.geographicHighDensityScatterModel().latitudeMemberPath());
		this.geographicHighDensityScatterModel().hostedSeries().useBruteForce(this.geographicHighDensityScatterModel().useBruteForce());
		this.geographicHighDensityScatterModel().hostedSeries().progressiveLoad(this.geographicHighDensityScatterModel().progressiveLoad());
		this.geographicHighDensityScatterModel().hostedSeries().mouseOverEnabled(this.geographicHighDensityScatterModel().mouseOverEnabled());
		this.geographicHighDensityScatterModel().hostedSeries().heatMinimum(this.geographicHighDensityScatterModel().heatMinimum());
		this.geographicHighDensityScatterModel().hostedSeries().heatMaximum(this.geographicHighDensityScatterModel().heatMaximum());
	}
	,
	onLongitudeMemberPathUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().xMemberPath(this.geographicHighDensityScatterModel().longitudeMemberPath());
	}
	,
	onLatitudeMemberPathUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().yMemberPath(this.geographicHighDensityScatterModel().latitudeMemberPath());
	}
	,
	onUseBruteForceUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().useBruteForce(this.geographicHighDensityScatterModel().useBruteForce());
	}
	,
	onProgressiveLoadUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().progressiveLoad(this.geographicHighDensityScatterModel().progressiveLoad());
	}
	,
	onMouseOverEnabledUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().mouseOverEnabled(this.geographicHighDensityScatterModel().mouseOverEnabled());
	}
	,
	onHeatMinimumPropertyUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().heatMinimum(this.geographicHighDensityScatterModel().heatMinimum());
	}
	,
	onHeatMaximumPropertyUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().heatMaximum(this.geographicHighDensityScatterModel().heatMaximum());
	}
	,
	onSeriesViewerUpdated: function () {
		if (this.geographicHighDensityScatterModel().seriesViewer() == null) {
			this.geographicHighDensityScatterModel().hostedSeries().xAxis(null);
			this.geographicHighDensityScatterModel().hostedSeries().yAxis(null);
			return;
		}
		this.geographicHighDensityScatterModel().hostedSeries().xAxis((this.geographicHighDensityScatterModel().seriesViewer()).xAxis());
		this.geographicHighDensityScatterModel().hostedSeries().yAxis((this.geographicHighDensityScatterModel().seriesViewer()).yAxis());
	}
	,
	onXAxisUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().xAxis((this.geographicHighDensityScatterModel().seriesViewer()).xAxis());
	}
	,
	onYAxisUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().yAxis((this.geographicHighDensityScatterModel().seriesViewer()).yAxis());
	}
	,
	onHeatMinimumColorPropertyUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().heatMinimumColor(this.geographicHighDensityScatterModel().heatMinimumColor());
	}
	,
	onHeatMaximumColorPropertyUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().heatMaximumColor(this.geographicHighDensityScatterModel().heatMaximumColor());
	}
	,
	onPointExtentPropertUpdated: function () {
		this.geographicHighDensityScatterModel().hostedSeries().pointExtent(this.geographicHighDensityScatterModel().pointExtent());
	}
	,
	$type: new $.ig.Type('GeographicHighDensityScatterSeriesView', $.ig.HostSeriesView$1.prototype.$type.specialize($.ig.HighDensityScatterSeries.prototype.$type))
}, true);

$.ig.util.defType('GeographicScatterAreaSeriesView', 'GeographicXYTriangulatingSeriesView', {
	init: function (model) {
		$.ig.GeographicXYTriangulatingSeriesView.prototype.init.call(this, model);
		this.geographicScatterAreaModel(model);
	},
	_geographicScatterAreaModel: null,
	geographicScatterAreaModel: function (value) {
		if (arguments.length === 1) {
			this._geographicScatterAreaModel = value;
			return value;
		} else {
			return this._geographicScatterAreaModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicXYTriangulatingSeriesView.prototype.onHostedSeriesUpdated.call(this);
		if (this.geographicScatterAreaModel().hostedScatterAreaSeries() != null) {
			this.geographicScatterAreaModel().hostedScatterAreaSeries().colorMemberPath(this.geographicScatterAreaModel().colorMemberPath());
			this.geographicScatterAreaModel().hostedScatterAreaSeries().colorScale(this.geographicScatterAreaModel().colorScale());
		}
	}
	,
	colorMemberPathUpdated: function () {
		if (this.geographicScatterAreaModel().hostedScatterAreaSeries() != null) {
			this.geographicScatterAreaModel().hostedScatterAreaSeries().colorMemberPath(this.geographicScatterAreaModel().colorMemberPath());
		}
	}
	,
	colorScaleUpdated: function () {
		if (this.geographicScatterAreaModel().hostedScatterAreaSeries() != null) {
			this.geographicScatterAreaModel().hostedScatterAreaSeries().colorScale(this.geographicScatterAreaModel().colorScale());
		}
	}
	,
	$type: new $.ig.Type('GeographicScatterAreaSeriesView', $.ig.GeographicXYTriangulatingSeriesView.prototype.$type)
}, true);

$.ig.util.defType('GeographicShapeSeriesView', 'GeographicShapeSeriesBaseView', {
	init: function (model) {
		$.ig.GeographicShapeSeriesBaseView.prototype.init.call(this, model);
		this.shapeModel(model);
		if (!this.isThumbnailView()) {
			this.shapeModel().shapeMemberPath("points");
		}
	},
	_shapeModel: null,
	shapeModel: function (value) {
		if (arguments.length === 1) {
			this._shapeModel = value;
			return value;
		} else {
			return this._shapeModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicShapeSeriesBaseView.prototype.onHostedSeriesUpdated.call(this);
		(this.shapeModel().hostedSeries()).shapeStyle(this.shapeModel().shapeStyle());
		(this.shapeModel().hostedSeries()).shapeStyleSelector(this.shapeModel().shapeStyleSelector());
		(this.shapeModel().hostedSeries()).markerTemplate(this.shapeModel().markerTemplate());
		(this.shapeModel().hostedSeries()).markerCollisionAvoidance(this.shapeModel().markerCollisionAvoidance());
	}
	,
	markerTemplatePropertyUpdated: function () {
		(this.shapeModel().hostedSeries()).markerTemplate(this.shapeModel().markerTemplate());
	}
	,
	markerCollisionAvoidanceUpdated: function () {
		(this.shapeModel().hostedSeries()).markerCollisionAvoidance(this.shapeModel().markerCollisionAvoidance());
	}
	,
	markerTypeUpdated: function () {
		(this.shapeModel().hostedSeries()).markerType(this.shapeModel().markerType());
	}
	,
	markerBrushUpdated: function () {
		(this.shapeModel().hostedSeries()).markerBrush(this.shapeModel().markerBrush());
	}
	,
	markerOutlineUpdated: function () {
		(this.shapeModel().hostedSeries()).markerOutline(this.shapeModel().markerOutline());
	}
	,
	markerStyleUpdated: function () {
		(this.shapeModel().hostedSeries()).markerStyle(this.shapeModel().markerStyle());
	}
	,
	shapeStyleSelectorUpdated: function () {
		(this.shapeModel().hostedSeries()).shapeStyleSelector(this.shapeModel().shapeStyleSelector());
	}
	,
	shapeStyleUpdated: function () {
		(this.shapeModel().hostedSeries()).shapeStyle(this.shapeModel().shapeStyle());
	}
	,
	$type: new $.ig.Type('GeographicShapeSeriesView', $.ig.GeographicShapeSeriesBaseView.prototype.$type)
}, true);

$.ig.util.defType('GeographicSymbolSeriesView', 'HostSeriesView$1', {
	init: function (model) {
		$.ig.HostSeriesView$1.prototype.init.call(this, $.ig.ScatterSeries.prototype.$type, model);
		this.symbolModel(model);
	},
	_symbolModel: null,
	symbolModel: function (value) {
		if (arguments.length === 1) {
			this._symbolModel = value;
			return value;
		} else {
			return this._symbolModel;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.HostSeriesView$1.prototype.onHostedSeriesUpdated.call(this);
		this.symbolModel().hostedSeries().xMemberPath(this.symbolModel().longitudeMemberPath());
		this.symbolModel().hostedSeries().yMemberPath(this.symbolModel().latitudeMemberPath());
		this.symbolModel().hostedSeries().markerType(this.symbolModel().markerType());
		this.symbolModel().hostedSeries().markerTemplate(this.symbolModel().markerTemplate());
	}
	,
	onLongitudeMemberPathUpdated: function () {
		this.symbolModel().hostedSeries().xMemberPath(this.symbolModel().longitudeMemberPath());
	}
	,
	onLatitudeMemberPathUpdated: function () {
		this.symbolModel().hostedSeries().yMemberPath(this.symbolModel().latitudeMemberPath());
	}
	,
	onMarkerTypeUpdated: function () {
		this.symbolModel().hostedSeries().markerType(this.symbolModel().markerType());
	}
	,
	onMarkerTemplateUpdated: function () {
		this.symbolModel().hostedSeries().markerTemplate(this.symbolModel().markerTemplate());
	}
	,
	onMaximumMarkersUpdated: function () {
		this.symbolModel().hostedSeries().maximumMarkers(this.symbolModel().maximumMarkers());
	}
	,
	onXAxisUpdated: function () {
		this.symbolModel().hostedSeries().xAxis((this.symbolModel().seriesViewer()).xAxis());
	}
	,
	onYAxisUpdated: function () {
		this.symbolModel().hostedSeries().yAxis((this.symbolModel().seriesViewer()).yAxis());
	}
	,
	onSeriesViewerUpdated: function () {
		if (this.symbolModel().seriesViewer() == null) {
			this.symbolModel().hostedSeries().xAxis(null);
			this.symbolModel().hostedSeries().yAxis(null);
			return;
		}
		this.symbolModel().hostedSeries().xAxis((this.symbolModel().seriesViewer()).xAxis());
		this.symbolModel().hostedSeries().yAxis((this.symbolModel().seriesViewer()).yAxis());
	}
	,
	onMarkerCollisionAvoidanceUpdated: function () {
		this.symbolModel().hostedSeries().markerCollisionAvoidance(this.symbolModel().markerCollisionAvoidance());
	}
	,
	onMarkerBrushUpdated: function () {
		this.symbolModel().hostedSeries().markerBrush(this.symbolModel().markerBrush());
	}
	,
	onMarkerOutlineUpdated: function () {
		this.symbolModel().hostedSeries().markerOutline(this.symbolModel().markerOutline());
	}
	,
	$type: new $.ig.Type('GeographicSymbolSeriesView', $.ig.HostSeriesView$1.prototype.$type.specialize($.ig.ScatterSeries.prototype.$type))
}, true);

$.ig.util.defType('ContourLineSeriesView', 'SeriesView', {
	_paths: null,
	paths: function (value) {
		if (arguments.length === 1) {
			this._paths = value;
			return value;
		} else {
			return this._paths;
		}
	}
	,
	_contourLineModel: null,
	contourLineModel: function (value) {
		if (arguments.length === 1) {
			this._contourLineModel = value;
			return value;
		} else {
			return this._contourLineModel;
		}
	}
	,
	init: function (model) {
		var $self = this;
		this.__contourBrushes = new $.ig.List$1($.ig.Brush.prototype.$type, 0);
		this.__lastCountourValues = null;
		$.ig.SeriesView.prototype.init.call(this, model);
		this.visiblePaths(new $.ig.List$1($.ig.Path.prototype.$type, 0));
		this.contourLineModel(model);
		this.paths((function () {
			var $ret = new $.ig.Pool$1($.ig.Path.prototype.$type);
			$ret.create($self.pathCreate.runOn($self));
			$ret.destroy($self.pathDestroy.runOn($self));
			$ret.activate($self.pathActivate.runOn($self));
			$ret.disactivate($self.pathDeactivate.runOn($self));
			return $ret;
		}()));
	},
	_visiblePaths: null,
	visiblePaths: function (value) {
		if (arguments.length === 1) {
			this._visiblePaths = value;
			return value;
		} else {
			return this._visiblePaths;
		}
	}
	,
	pathCreate: function () {
		var $self = this;
		var pth = new $.ig.Path();
		pth.dataContext((function () {
			var $ret = new $.ig.DataContext();
			$ret.series($self.model());
			return $ret;
		}()));
		this.visiblePaths().add(pth);
		return pth;
	}
	,
	pathActivate: function (pth) {
		pth.__visibility = $.ig.Visibility.prototype.visible;
	}
	,
	pathDeactivate: function (pth) {
		pth.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	pathDestroy: function (pth) {
		this.visiblePaths().remove(pth);
	}
	,
	__contourBrushes: null,
	__lastCountourValues: null,
	applyVisuals: function (contours, contourValues) {
		var counter = 0;
		this.__lastCountourValues = contourValues;
		this.__contourBrushes.clear();
		for (var i = 0; i < contourValues.length; i++) {
			var contourValue = contourValues[i];
			var contour = contours.__inner[counter];
			var pathGeometry = new $.ig.PathGeometry();
			for (var j = 0; j < contour.count(); j++) {
				var segment = contour.__inner[j];
				if (segment.count() == 0) {
					continue;
				}
				var pointCollection = $.ig.PointCollectionUtil.prototype.toPointCollection(segment);
				var pathFigure = (function () {
					var $ret = new $.ig.PathFigure();
					$ret.isClosed(false);
					$ret.isFilled(false);
					$ret.startPoint(pointCollection.__inner[0]);
					return $ret;
				}());
				pathFigure.__segments.add((function () {
					var $ret = new $.ig.PolyLineSegment();
					$ret.points(pointCollection);
					return $ret;
				}()));
				pathGeometry.figures().add(pathFigure);
			}
			this.paths().item(counter).data(pathGeometry);
			if (this.paths().item(counter).dataContext() != null) {
				(this.paths().item(counter).dataContext()).item(contourValue);
			}
			if (this.contourLineModel().fillScale() != null) {
				var stroke = this.contourLineModel().fillScale().getBrushByValue(contourValue, this.contourLineModel().valueColumn());
				if (stroke == null) {
					stroke = this.model().actualBrush();
				}
				this.__contourBrushes.add(stroke);
			} else {
				this.__contourBrushes.add(this.model().actualBrush());
			}
			counter++;
		}
		this.paths().count(counter);
		this.makeDirty();
	}
	,
	setupItemAppearanceOverride: function (item, index) {
		$.ig.SeriesView.prototype.setupItemAppearanceOverride.call(this, item, index);
		var path = item;
		path.__stroke = this.__contourBrushes.__inner[index];
		path.strokeThickness(this.model().thickness());
		if (path.style() != null) {
			this.context().applyStyle(path, path.style());
		}
	}
	,
	setupItemHitAppearanceOverride: function (item, index) {
		$.ig.SeriesView.prototype.setupItemHitAppearanceOverride.call(this, item, index);
		var path = item;
		var hitBrush = this.getHitBrush1(index);
		path.__stroke = hitBrush;
		path.strokeThickness(this.model().thickness() + $.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1);
	}
	,
	renderOverride: function (context, isHitContext) {
		$.ig.SeriesView.prototype.renderOverride.call(this, context, isHitContext);
		for (var i = 0; i < this.visiblePaths().count(); i++) {
			var path = this.visiblePaths().__inner[i];
			if (path.__visibility != $.ig.Visibility.prototype.collapsed) {
				this.setupItemAppearance(path, i, isHitContext);
				context.renderPath(path);
			}
		}
	}
	,
	clearContours: function (wipeClean) {
		this.paths().count(0);
		this.makeDirty();
	}
	,
	exportViewShapes: function (svd) {
		$.ig.SeriesView.prototype.exportViewShapes.call(this, svd);
		var toSort = new $.ig.List$1($.ig.Tuple$2.prototype.$type.specialize(Number, $.ig.Path.prototype.$type), 0);
		if (this.__lastCountourValues != null) {
			for (var i = 0; i < this.paths().count(); i++) {
				toSort.add(new $.ig.Tuple$2(Number, $.ig.Path.prototype.$type, this.__lastCountourValues[i], this.paths().item(i)));
			}
		}
		toSort.sort2(function (p1, p2) {
			if (p1.item1() < p2.item1()) {
				return -1;
			} else if (p1.item1() > p2.item1()) {
				return 1;
			}
			return 0;
		});
		for (var i1 = 0; i1 < toSort.count(); i1++) {
			if (toSort.__inner[i1].item2().__visibility != $.ig.Visibility.prototype.collapsed) {
				var pd = new $.ig.PathVisualData(1, "contour" + i1, toSort.__inner[i1].item2());
				pd.tags().add("Main");
				svd.shapes().add(pd);
			}
		}
	}
	,
	$type: new $.ig.Type('ContourLineSeriesView', $.ig.SeriesView.prototype.$type)
}, true);

$.ig.util.defType('PolylineSeriesView', 'ShapeSeriesViewBase', {
	init: function (model) {
		$.ig.ShapeSeriesViewBase.prototype.init.call(this, model);
	},
	getShapeGeometry: function (i, points) {
		return this.getShapeGeometry1(i, points, false);
	}
	,
	applyStyling1: function (shape, item) {
		$.ig.ShapeSeriesViewBase.prototype.applyStyling1.call(this, shape, item);
		$.ig.ShapeSeriesViewBase.prototype.applyStyling(this, shape, item);
	}
	,
	applyData: function (element, data) {
		var shape = $.ig.util.cast($.ig.Path.prototype.$type, element);
		if (shape == null) {
			return;
		}
		shape.data(data);
	}
	,
	clearValues: function (element) {
		$.ig.ShapeSeriesViewBase.prototype.clearValues.call(this, element);
	}
	,
	setupItemAppearanceOverride: function (item, index) {
		$.ig.ShapeSeriesViewBase.prototype.setupItemAppearanceOverride.call(this, item, index);
		var path = item;
		path.__fill = null;
	}
	,
	setupItemHitAppearanceOverride: function (item, index) {
		$.ig.ShapeSeriesViewBase.prototype.setupItemHitAppearanceOverride.call(this, item, index);
		var path = item;
		path.__fill = null;
	}
	,
	shouldRenderShape: function (bounds) {
		return bounds.width() >= this.shapeModel().shapeFilterResolutionCached() || bounds.height() >= this.shapeModel().shapeFilterResolutionCached();
	}
	,
	$type: new $.ig.Type('PolylineSeriesView', $.ig.ShapeSeriesViewBase.prototype.$type)
}, true);

$.ig.util.defType('ScatterAreaSeriesView', 'SeriesView', {
	_scatterAreaModel: null,
	scatterAreaModel: function (value) {
		if (arguments.length === 1) {
			this._scatterAreaModel = value;
			return value;
		} else {
			return this._scatterAreaModel;
		}
	}
	,
	__itemIndexes: null,
	itemIndexes: function (value) {
		if (arguments.length === 1) {
			this.__itemIndexes = value;
			return value;
		} else {
			return this.__itemIndexes;
		}
	}
	,
	init: function (model) {
		$.ig.SeriesView.prototype.init.call(this, model);
		this.scatterAreaModel(model);
	},
	regenerateBitmap: function (width, height) {
	}
	,
	_imageData: null,
	imageData: function (value) {
		if (arguments.length === 1) {
			this._imageData = value;
			return value;
		} else {
			return this._imageData;
		}
	}
	,
	_offscreen: null,
	offscreen: function (value) {
		if (arguments.length === 1) {
			this._offscreen = value;
			return value;
		} else {
			return this._offscreen;
		}
	}
	,
	_offscreenContext: null,
	offscreenContext: function (value) {
		if (arguments.length === 1) {
			this._offscreenContext = value;
			return value;
		} else {
			return this._offscreenContext;
		}
	}
	,
	getBitmap: function () {
		this.ensureOffscreenContext();
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		this.offscreen().attr("width", viewportWidth.toString());
		this.offscreen().attr("height", viewportHeight.toString());
		this.imageData((this.offscreenContext().getUnderlyingContext()).getImageData(0, 0, viewportWidth, viewportHeight));
		return this.imageData().data;
	}
	,
	ensureOffscreenContext: function () {
		if (this.offscreen() == null) {
			this.offscreen($("<canvas></canvas>"));
			var cont = (this.offscreen()[0]).getContext("2d");
			this.offscreenContext(new $.ig.RenderingContext(new $.ig.CanvasViewRenderer(), cont));
		}
	}
	,
	getRasterizer: function (viewportRect, actualColorScale, colorColumn) {
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		return new $.ig.TriangleRasterizer(this.getBitmap(), actualColorScale, colorColumn.minimum(), colorColumn.maximum(), colorColumn, viewportWidth, viewportHeight);
	}
	,
	closeRasterizer: function (triceratops) {
		this.setBitmap(null);
	}
	,
	clearBitmap: function () {
		if (this.offscreenContext() != null) {
			this.offscreenContext().clearRectangle(this.viewport().left(), this.viewport().top(), this.viewport().width(), this.viewport().height());
		}
		this.makeDirty();
	}
	,
	renderOverride: function (context, isHitContext) {
		$.ig.SeriesView.prototype.renderOverride.call(this, context, isHitContext);
		if (this.offscreen() == null) {
			return;
		}
		if (isHitContext) {
			var rect = new $.ig.Rectangle();
			rect.canvasLeft(this.viewport().left());
			rect.canvasTop(this.viewport().top());
			rect.width(this.viewport().width());
			rect.height(this.viewport().height());
			rect.__fill = this.getHitBrush();
			context.renderRectangle(rect);
		} else {
			var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
			var viewportTop = $.ig.truncate(Math.round(this.viewport().left()));
			var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
			var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
			context.drawImage(this.offscreen()[0], 1, viewportLeft, viewportTop, viewportWidth, viewportHeight);
		}
	}
	,
	setBitmap: function (bitmap) {
		var viewportLeft = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportTop = $.ig.truncate(Math.round(this.viewport().left()));
		var viewportWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var viewportHeight = $.ig.truncate(Math.round(this.viewport().height()));
		this.ensureOffscreenContext();
		(this.offscreenContext().getUnderlyingContext()).putImageData(this.imageData(), 0, 0);
		this.makeDirty();
	}
	,
	$type: new $.ig.Type('ScatterAreaSeriesView', $.ig.SeriesView.prototype.$type)
}, true);

$.ig.util.defType('ShapeSeriesView', 'ShapeSeriesViewBase', {
	init: function (model) {
		var $self = this;
		this.__lightweightMode = false;
		this.__markerMeasureInfo = null;
		$.ig.ShapeSeriesViewBase.prototype.init.call(this, model);
		this.shapeSeriesModel(model);
		this.markers(new $.ig.HashPool$2($.ig.Object.prototype.$type, $.ig.Marker.prototype.$type));
		this.visibleMarkers(new $.ig.List$1($.ig.Marker.prototype.$type, 0));
		this.initMarkers(this.markers());
		this._markerManager = new $.ig.NumericMarkerManager(1, function (o) { return $self.markers().item(o); }, function (i) { return $self.shapeSeriesModel().axisInfoCache().fastItemsSource().item(i); }, this.removeUnusedMarkers.runOn(this), this.getMarkerLocations.runOn(this), this.getActiveIndexes.runOn(this), function () { return $self.shapeSeriesModel().markerCollisionAvoidance(); });
		this._markerManager.getMarkerDesiredSize(this.getMarkerDesiredSize.runOn(this));
	},
	markerManager: function () {
		return this._markerManager;
	}
	,
	removeUnusedMarkers: function (list) {
		this.shapeSeriesModel().removeUnusedMarkers(list, this.markers());
	}
	,
	getMarkerLocations: function () {
		this._locations = this.shapeSeriesModel().getMarkerLocations(this, this.markers(), this._locations, this.windowRect(), this.viewport());
		return this._locations;
	}
	,
	getActiveIndexes: function () {
		this._indexes = this.shapeSeriesModel().getActiveIndexes(this.markers(), this._indexes);
		return this._indexes;
	}
	,
	_locations: null,
	_indexes: null,
	_shapeSeriesModel: null,
	shapeSeriesModel: function (value) {
		if (arguments.length === 1) {
			this._shapeSeriesModel = value;
			return value;
		} else {
			return this._shapeSeriesModel;
		}
	}
	,
	_markerManager: null,
	getShapeGeometry: function (i, points) {
		return this.getShapeGeometry1(i, points, true);
	}
	,
	applyStyling1: function (shape, item) {
		$.ig.ShapeSeriesViewBase.prototype.applyStyling1.call(this, shape, item);
		$.ig.ShapeSeriesViewBase.prototype.applyStyling(this, shape, item);
	}
	,
	applyData: function (element, data) {
		var shape = element;
		if (shape == null) {
			return;
		}
		shape.data(data);
	}
	,
	__lightweightMode: false,
	_markers: null,
	markers: function (value) {
		if (arguments.length === 1) {
			this._markers = value;
			return value;
		} else {
			return this._markers;
		}
	}
	,
	doToAllMarkers: function (action) {
		this.markers().doToAll(action);
	}
	,
	initMarkers: function (Markers) {
		Markers.create(this.markerCreate.runOn(this));
		Markers.destroy(this.markerDestroy.runOn(this));
		Markers.activate(this.markerActivate.runOn(this));
		Markers.disactivate(this.markerDisactivate.runOn(this));
	}
	,
	markerCreate: function () {
		var $self = this;
		var marker = new $.ig.Marker();
		if (!this.__lightweightMode) {
			marker.content((function () {
				var $ret = new $.ig.DataContext();
				$ret.series($self.model());
				return $ret;
			}()));
		}
		marker.contentTemplate((this.model()).actualMarkerTemplate());
		this.visibleMarkers().add(marker);
		return marker;
	}
	,
	_visibleMarkers: null,
	visibleMarkers: function (value) {
		if (arguments.length === 1) {
			this._visibleMarkers = value;
			return value;
		} else {
			return this._visibleMarkers;
		}
	}
	,
	markerDestroy: function (marker) {
		marker.content(null);
		this.visibleMarkers().remove(marker);
	}
	,
	markerActivate: function (marker) {
		marker.__visibility = $.ig.Visibility.prototype.visible;
	}
	,
	markerDisactivate: function (marker) {
		if (marker.content() != null) {
			($.ig.util.cast($.ig.DataContext.prototype.$type, marker.content())).item(null);
		}
		marker.__visibility = $.ig.Visibility.prototype.collapsed;
	}
	,
	setUseLightweightMode: function (useLightweight) {
		this.__lightweightMode = useLightweight;
	}
	,
	clearValues: function (element) {
		$.ig.ShapeSeriesViewBase.prototype.clearValues.call(this, element);
	}
	,
	setupMarkerAppearanceOverride: function (item, index) {
		$.ig.ShapeSeriesViewBase.prototype.setupMarkerAppearanceOverride.call(this, item, index);
		var marker = item;
		var context = marker.content();
		if (context != null) {
			if (this.shapeSeriesModel().actualMarkerBrush() != null) {
				context.actualItemBrush(this.shapeSeriesModel().actualMarkerBrush());
			} else {
				context.actualItemBrush(this.shapeModel().actualBrush());
			}
			if (this.shapeSeriesModel().actualMarkerBrush() != null) {
				context.outline(this.shapeSeriesModel().actualMarkerOutline());
			} else {
				context.outline(this.shapeModel().actualOutline());
			}
			context.thickness(0.5);
		}
	}
	,
	setupMarkerHitAppearanceOverride: function (item, index) {
		$.ig.ShapeSeriesViewBase.prototype.setupMarkerHitAppearanceOverride.call(this, item, index);
		var marker = item;
		var hitBrush = this.getHitBrush1(index);
		var context = marker.content();
		if (context != null) {
			context.actualItemBrush(hitBrush);
			context.outline(hitBrush);
			context.thickness(1 + $.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1);
		}
	}
	,
	renderMarkersOverride: function (context, isHitContext) {
		$.ig.ShapeSeriesViewBase.prototype.renderMarkersOverride.call(this, context, isHitContext);
		var passInfo = new $.ig.DataTemplatePassInfo();
		passInfo.isHitTestRender = isHitContext;
		passInfo.context = context.getUnderlyingContext();
		passInfo.viewportTop = this.viewport().top();
		passInfo.viewportLeft = this.viewport().left();
		passInfo.viewportWidth = this.viewport().width();
		passInfo.viewportHeight = this.viewport().height();
		passInfo.passID = "Markers";
		var renderInfo = new $.ig.DataTemplateRenderInfo();
		renderInfo.passInfo = passInfo;
		renderInfo.isHitTestRender = isHitContext;
		var measureInfo = new $.ig.DataTemplateMeasureInfo();
		measureInfo.passInfo = passInfo;
		var isConstant = false;
		var cont = context.getUnderlyingContext();
		measureInfo.context = cont;
		renderInfo.context = cont;
		var constantWidth = 0;
		var constantHeight = 0;
		var first = true;
		if (this.shapeSeriesModel().actualMarkerTemplate() != null && this.shapeSeriesModel().actualMarkerTemplate().passStarting() != null) {
			this.shapeSeriesModel().actualMarkerTemplate().passStarting()(passInfo);
		}
		for (var i = 0; i < this.visibleMarkers().count(); i++) {
			var marker = this.visibleMarkers().__inner[i];
			if (marker.__visibility == $.ig.Visibility.prototype.collapsed) {
				continue;
			}
			this.setupMarkerAppearance(marker, i, isHitContext);
			if (!isConstant) {
				measureInfo.width = marker.width();
				measureInfo.height = marker.height();
				measureInfo.renderOffsetX = 0;
				measureInfo.renderOffsetY = 0;
				measureInfo.renderContext = context;
				var template = marker.contentTemplate();
				if (template.measure() != null) {
					measureInfo.data = marker.content();
					template.measure()(measureInfo);
					isConstant = measureInfo.isConstant;
					if (isConstant) {
						constantWidth = measureInfo.width;
						constantHeight = measureInfo.height;
					}
				}
				renderInfo.availableWidth = measureInfo.width;
				renderInfo.availableHeight = measureInfo.height;
				renderInfo.renderOffsetX = measureInfo.renderOffsetX;
				renderInfo.renderOffsetY = measureInfo.renderOffsetY;
				renderInfo.renderContext = context;
			} else {
				renderInfo.availableWidth = constantWidth;
				renderInfo.availableHeight = constantHeight;
			}
			if (!$.ig.util.isNaN(marker.width()) && !Number.isInfinity(marker.width())) {
				renderInfo.availableWidth = marker.width();
			}
			if (!$.ig.util.isNaN(marker.height()) && !Number.isInfinity(marker.height())) {
				renderInfo.availableHeight = marker.height();
			}
			first = false;
			context.renderContentControl(renderInfo, marker);
			marker.actualWidth(renderInfo.availableWidth);
			marker.actualHeight(renderInfo.availableHeight);
			marker._renderOffsetX = renderInfo.renderOffsetX;
			marker._renderOffsetY = renderInfo.renderOffsetY;
		}
		if (this.shapeSeriesModel().actualMarkerTemplate() != null && this.shapeSeriesModel().actualMarkerTemplate().passCompleted() != null) {
			this.shapeSeriesModel().actualMarkerTemplate().passCompleted()(passInfo);
		}
	}
	,
	bindMarkerTemplateToActual: function () {
		this.shapeSeriesModel().actualMarkerTemplate(null);
		this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().markerTemplate());
	}
	,
	bindMarkerBrushToActual: function () {
		this.shapeSeriesModel().actualMarkerBrush(null);
		this.shapeSeriesModel().actualMarkerBrush(this.shapeSeriesModel().markerBrush());
	}
	,
	bindMarkerOutlineToActual: function () {
		this.shapeSeriesModel().actualMarkerOutline(null);
		this.shapeSeriesModel().actualMarkerOutline(this.shapeSeriesModel().markerOutline());
	}
	,
	bindActualToMarkerTemplate: function (markerTemplatePropertyName) {
		switch (markerTemplatePropertyName) {
			case "CircleMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().circleMarkerTemplate());
				break;
			case "TriangleMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().triangleMarkerTemplate());
				break;
			case "PyramidMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().pyramidMarkerTemplate());
				break;
			case "SquareMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().squareMarkerTemplate());
				break;
			case "DiamondMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().diamondMarkerTemplate());
				break;
			case "PentagonMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().pentagonMarkerTemplate());
				break;
			case "HexagonMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().hexagonMarkerTemplate());
				break;
			case "TetragramMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().pentagonMarkerTemplate());
				break;
			case "PentagramMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().pentagramMarkerTemplate());
				break;
			case "HexagramMarkerTemplate":
				this.shapeSeriesModel().actualMarkerTemplate(this.shapeSeriesModel().seriesViewer().hexagramMarkerTemplate());
				break;
		}
	}
	,
	doUpdateMarkerTemplates: function () {
		var en = this.visibleMarkers().getEnumerator();
		while (en.moveNext()) {
			var marker = en.current();
			marker.contentTemplate(this.shapeSeriesModel().actualMarkerTemplate());
		}
		this.makeDirty();
	}
	,
	__markerMeasureInfo: null,
	getMarkerDesiredSize: function (marker) {
		if (this.__markerMeasureInfo == null) {
			this.__markerMeasureInfo = new $.ig.DataTemplateMeasureInfo();
			this.__markerMeasureInfo.context = this.context().getUnderlyingContext();
		}
		this.__markerMeasureInfo.width = marker.width();
		this.__markerMeasureInfo.height = marker.height();
		this.__markerMeasureInfo.data = marker.content();
		var template = marker.contentTemplate();
		if (template.measure() != null) {
			template.measure()(this.__markerMeasureInfo);
		}
		return new $.ig.Size(1, this.__markerMeasureInfo.width, this.__markerMeasureInfo.height);
	}
	,
	getHitMarker: function (point) {
		var halfWidth;
		var halfHeight;
		var offsetX;
		var offsetY;
		for (var i = this.visibleMarkers().count() - 1; i >= 0; i--) {
			var marker = this.visibleMarkers().__inner[i];
			if (marker.__visibility == $.ig.Visibility.prototype.collapsed || marker.__opacity == 0) {
				continue;
			}
			halfWidth = (marker.actualWidth() / 2) + $.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1;
			halfHeight = (marker.actualHeight() / 2) + $.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1;
			offsetX = marker._renderOffsetX;
			offsetY = marker._renderOffsetY;
			if ((marker.canvasLeft() + offsetX) - halfWidth <= point.__x && (marker.canvasLeft() + offsetX) + halfWidth >= point.__x && (marker.canvasTop() + offsetY) - halfHeight <= point.__y && (marker.canvasTop() + offsetY) + halfHeight >= point.__y) {
				return marker;
			}
		}
		return null;
	}
	,
	$type: new $.ig.Type('ShapeSeriesView', $.ig.ShapeSeriesViewBase.prototype.$type)
}, true);

$.ig.util.defType('ShapeHitRegion', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_bounds: null,
	_points: null,
	_index: 0,
	_element: null,
	$type: new $.ig.Type('ShapeHitRegion', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('StyleSelector', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	selectStyle: function (item, container) {
		return null;
	}
	,
	$type: new $.ig.Type('StyleSelector', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XamGeographicMapView', 'SeriesViewerView', {
	init: function (model) {
		$.ig.SeriesViewerView.prototype.init.call(this, model);
		this.mapViewport($.ig.Rect.prototype.empty());
		this.mapModel(model);
		this.mapModel().dragModifier($.ig.ModifierKeys.prototype.control);
		this.mapModel().windowRectMinWidth(5E-06);
	},
	resolveDefaultInteraction: function (isFinger) {
		return $.ig.InteractionState.prototype.dragPan;
	}
	,
	actualMobileModeChanged: function (actualMobileMode) {
		this.viewManager().updateMobileMode(actualMobileMode);
		this.mapModel().doUpdateOPDMobileMode(actualMobileMode);
		this.updateOverviewPlusDetailPaneVisibility();
	}
	,
	_mapModel: null,
	mapModel: function (value) {
		if (arguments.length === 1) {
			this._mapModel = value;
			return value;
		} else {
			return this._mapModel;
		}
	}
	,
	requiresBackground: function () {
		return true;
	}
	,
	setDefaultBrushes: function () {
		var brushes;
		var outlines;
		var fontBrush;
		var font;
		var axisLineBrush;
		var $ret = this.viewManager().getDefaultPalette(brushes, outlines, fontBrush, font, axisLineBrush);
		brushes = $ret.p0;
		outlines = $ret.p1;
		fontBrush = $ret.p2;
		font = $ret.p3;
		axisLineBrush = $ret.p4;
		this.fontBrush(fontBrush);
		this.font(font);
		this.axisLineBrush(axisLineBrush);
	}
	,
	getMarkerBrushByIndex: function (index) {
		return (function () {
			var $ret = new $.ig.Brush();
			$ret.fill("rgba(0,0,0,1)");
			return $ret;
		}());
	}
	,
	getMarkerOutlineByIndex: function (index) {
		return (function () {
			var $ret = new $.ig.Brush();
			$ret.fill("white");
			return $ret;
		}());
	}
	,
	getBrushByIndex: function (index) {
		return (function () {
			var $ret = new $.ig.Brush();
			$ret.fill("rgba(50,50,50,.5)");
			return $ret;
		}());
	}
	,
	getOutlineByIndex: function (index) {
		return (function () {
			var $ret = new $.ig.Brush();
			$ret.fill("white");
			return $ret;
		}());
	}
	,
	provideBackgroundContext: function (context) {
		$.ig.SeriesViewerView.prototype.provideBackgroundContext.call(this, context);
		if (this.mapModel().backgroundContent() != null) {
			var imagery = this.mapModel().backgroundContent();
			imagery.provideContext(context);
		}
	}
	,
	_mapViewport: null,
	mapViewport: function (value) {
		if (arguments.length === 1) {
			this._mapViewport = value;
			return value;
		} else {
			return this._mapViewport;
		}
	}
	,
	provideBackgroundViewport: function (viewport) {
		$.ig.SeriesViewerView.prototype.provideBackgroundViewport.call(this, viewport);
		this.mapViewport(viewport);
		if (this.mapModel().backgroundContent() != null) {
			var imagery = this.mapModel().backgroundContent();
			imagery.provideViewport(this.mapViewport());
		}
	}
	,
	getViewport: function () {
		return new $.ig.Rect(0, 0, 0, this.mapViewport().width(), this.mapViewport().height());
	}
	,
	backgroundContentNeedsRefresh: function () {
		if (this.mapModel().backgroundContent() != null) {
			var imagery = this.mapModel().backgroundContent();
			imagery.needsRefresh();
		}
	}
	,
	_fontBrush: null,
	fontBrush: function (value) {
		if (arguments.length === 1) {
			this._fontBrush = value;
			return value;
		} else {
			return this._fontBrush;
		}
	}
	,
	_axisLineBrush: null,
	axisLineBrush: function (value) {
		if (arguments.length === 1) {
			this._axisLineBrush = value;
			return value;
		} else {
			return this._axisLineBrush;
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
	actualWindowRectUpdated: function (actualWindowRect) {
		if (this.mapModel().backgroundImagery() != null) {
			this.mapModel().backgroundImagery().windowRect(actualWindowRect);
		}
	}
	,
	onBackgroundImageryProvided: function (oldImagery, newImagery) {
		if (this.mapModel().backgroundImagery() != null) {
			this.mapModel().backgroundImagery().windowRect(this.mapModel().actualWindowRect());
		}
		if (oldImagery != null) {
			var msi = oldImagery.multiScaleImage();
			if (msi != null) {
				this.mapModel().canvasRenderScheduler().dependsOn().remove(msi.tileScheduler());
			}
			oldImagery.provideContext(null);
		}
		if (newImagery != null) {
			var msi1 = newImagery.multiScaleImage();
			if (msi1 != null) {
				this.mapModel().canvasRenderScheduler().dependsOn().add(msi1.tileScheduler());
			}
			if (!this.mapViewport().isEmpty()) {
				this.provideBackgroundViewport(this.mapViewport());
			}
			if (this.backgroundContext() != null) {
				this.provideBackgroundContext(this.backgroundContext());
			}
		}
		if (newImagery == null) {
			this.clearBackgroundContent(this.backgroundContext());
		}
	}
	,
	clearBackgroundContent: function (BackgroundContext) {
		BackgroundContext.clearRectangle(this.mapViewport().left(), this.mapViewport().top(), this.mapViewport().width(), this.mapViewport().height());
	}
	,
	renderOverride: function () {
		this.horizontalCrosshairLine().strokeThickness(1);
		this.verticalCrosshairLine().strokeThickness(1);
		this.horizontalCrosshairLine().__stroke = this.fontBrush();
		this.verticalCrosshairLine().__stroke = this.fontBrush();
		$.ig.SeriesViewerView.prototype.renderOverride.call(this);
	}
	,
	onSeriesRemoved: function () {
		$.ig.SeriesViewerView.prototype.onSeriesRemoved.call(this);
	}
	,
	$type: new $.ig.Type('XamGeographicMapView', $.ig.SeriesViewerView.prototype.$type)
}, true);

$.ig.util.defType('SphericalMercatorHorizontalScaler', 'HorizontalLinearScaler', {
	__unitRect: null,
	init: function () {
		$.ig.HorizontalLinearScaler.prototype.init.call(this);
		this.__unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
	},
	getUnscaledValue: function (scaledValue, p) {
		if (!p._effectiveViewportRect.isEmpty()) {
			var u = scaledValue * p._windowRect.width() + p._windowRect.left() * p._viewportRect.width();
			var unscaledValue = this.getUnscaledValue1(u, this.__unitRect, p._effectiveViewportRect, p._isInverted);
			return unscaledValue;
		} else {
			return this.getUnscaledValue1(scaledValue, p._windowRect, p._viewportRect, p._isInverted);
		}
	}
	,
	getScaledValue: function (unscaledValue, p) {
		if (!p._effectiveViewportRect.isEmpty()) {
			var u = this.getScaledValue1(unscaledValue, this.__unitRect, p._effectiveViewportRect, p._isInverted);
			var scaledValue = (u - (p._windowRect.left() * p._viewportRect.width())) / p._windowRect.width();
			return scaledValue;
		} else {
			return this.getScaledValue1(unscaledValue, p._windowRect, p._viewportRect, p._isInverted);
		}
	}
	,
	asArray: function (values_) {
		var arr = $.isArray(values_) ? values_ : null;;
		return arr;
		return null;
	}
	,
	getScaledValueList: function (unscaledValues, startIndex, count, p) {
		var unscaledValue;
		var windowRect = p._windowRect;
		var viewportRect = p._viewportRect;
		var effectiveViewportRect = p._effectiveViewportRect;
		var isInverted = p._isInverted;
		var useEffectiveRect = !effectiveViewportRect.isEmpty();
		var actualRange = this.actualRange();
		var minimumValue = this._cachedActualMinimumValue;
		var effectiveLeft = effectiveViewportRect.left();
		var effectiveWidth = effectiveViewportRect.width();
		var windowLeft = windowRect.left();
		var windowWidth = windowRect.width();
		var viewportLeft = viewportRect.left();
		var viewportWidth = viewportRect.width();
		var unitLeft = this.__unitRect.left();
		var unitWidth = this.__unitRect.width();
		var input = this.asArray(unscaledValues);
		var useArray = false;
		if (input != null) {
			useArray = true;
		}
		for (var i = startIndex; i < count; i++) {
			if (useArray) {
				unscaledValue = input[i];
			} else {
				unscaledValue = unscaledValues.item(i);
			}
			if (useEffectiveRect) {
				var u = (unscaledValue - minimumValue) / (actualRange);
				if (isInverted) {
					u = 1 - u;
				}
				u = effectiveLeft + effectiveWidth * (u - unitLeft) / unitWidth;
				var scaledValue = (u - (windowLeft * viewportWidth)) / windowWidth;
				if (useArray) {
					input[i] = scaledValue;
				} else {
					unscaledValues.item(i, scaledValue);
				}
			} else {
				var scaledValue1 = (unscaledValue - minimumValue) / (actualRange);
				if (isInverted) {
					scaledValue1 = 1 - scaledValue1;
				}
				scaledValue1 = viewportLeft + viewportWidth * (scaledValue1 - windowLeft) / windowWidth;
				if (useArray) {
					input[i] = scaledValue1;
				} else {
					unscaledValues.item(i, scaledValue1);
				}
			}
		}
	}
	,
	calculateRange: function (target, minimumValue, maximumValue, actualMinimumValue, actualMaximumValue) {
		actualMinimumValue = target.minimumValue();
		actualMaximumValue = target.maximumValue();
		return {
			p3: actualMinimumValue,
			p4: actualMaximumValue
		};
	}
	,
	getUnscaledValue1: function (scaledValue, windowRect, viewportRect, isInverted) {
		var unscaledValue = windowRect.left() + windowRect.width() * (scaledValue - viewportRect.left()) / viewportRect.width();
		if (isInverted) {
			unscaledValue = 1 - unscaledValue;
		}
		return this._cachedActualMinimumValue + unscaledValue * (this.actualRange());
	}
	,
	getScaledValue1: function (unscaledValue, windowRect, viewportRect, isInverted) {
		var scaledValue = (unscaledValue - this._cachedActualMinimumValue) / (this.actualRange());
		if (isInverted) {
			scaledValue = 1 - scaledValue;
		}
		return viewportRect.left() + viewportRect.width() * (scaledValue - windowRect.left()) / windowRect.width();
	}
	,
	getScaledValue2: function (unscaledValue, windowRect, viewportRect, effectiveViewportRect, isInverted) {
		var scaledValue = (unscaledValue - this._cachedActualMinimumValue) / (this.actualRange());
		if (isInverted) {
			scaledValue = 1 - scaledValue;
		}
		return viewportRect.left() + viewportRect.width() * (scaledValue - windowRect.left()) / windowRect.width();
	}
	,
	$type: new $.ig.Type('SphericalMercatorHorizontalScaler', $.ig.HorizontalLinearScaler.prototype.$type)
}, true);

$.ig.util.defType('SphericalMercatorVerticalScaler', 'VerticalLinearScaler', {
	__unitRect: null,
	getUnscaledValue: function (scaledValue, p) {
		if (!p._effectiveViewportRect.isEmpty()) {
			var u = scaledValue * p._windowRect.height() + p._windowRect.top() * p._viewportRect.height();
			var unscaledValue = this.getUnscaledValue1(u, this.__unitRect, p._effectiveViewportRect, p._isInverted);
			return unscaledValue;
		} else {
			return this.getUnscaledValue1(scaledValue, p._windowRect, p._viewportRect, p._isInverted);
		}
	}
	,
	getScaledValue: function (unscaledValue, p) {
		if (!p._effectiveViewportRect.isEmpty()) {
			var scaledValue = this.getScaledValue1(unscaledValue, this.__unitRect, p._effectiveViewportRect, p._isInverted);
			scaledValue = (scaledValue - (p._windowRect.top() * p._viewportRect.height())) / p._windowRect.height();
			return scaledValue;
		} else {
			return this.getScaledValue1(unscaledValue, p._windowRect, p._viewportRect, p._isInverted);
		}
	}
	,
	asArray: function (values_) {
		var arr = $.isArray(values_) ? values_ : null;;
		return arr;
		return null;
	}
	,
	getScaledValueList: function (unscaledValues, startIndex, count, p) {
		var unscaledValue;
		var windowRect = p._windowRect;
		var viewportRect = p._viewportRect;
		var effectiveViewportRect = p._effectiveViewportRect;
		var isInverted = p._isInverted;
		var useEffectiveRect = !effectiveViewportRect.isEmpty();
		var actualRange = this.actualRange();
		var minimumValue = this._cachedActualMinimumValue;
		var effectiveTop = effectiveViewportRect.top();
		var effectiveHeight = effectiveViewportRect.height();
		var windowTop = windowRect.top();
		var windowHeight = windowRect.height();
		var viewportTop = viewportRect.top();
		var viewportHeight = viewportRect.height();
		var unitTop = this.__unitRect.top();
		var unitHeight = this.__unitRect.height();
		var input = this.asArray(unscaledValues);
		var degreeAsRadian = $.ig.SphericalMercatorVerticalScaler.prototype._degreeAsRadian;
		var valueR;
		var valueSin;
		var projectedValue;
		var scaledValue;
		var u;
		var useArray = false;
		if (input != null) {
			useArray = true;
		}
		for (var i = startIndex; i < count; i++) {
			if (useArray) {
				unscaledValue = input[i];
			} else {
				unscaledValue = unscaledValues.item(i);
			}
			if (useEffectiveRect) {
				if (unscaledValue < $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue) {
					unscaledValue = $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue;
				}
				if (unscaledValue > $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue) {
					unscaledValue = $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue;
				}
				valueR = unscaledValue * (degreeAsRadian);
				valueSin = Math.sin(valueR);
				projectedValue = 0.5 * Math.log((1 + valueSin) / (1 - valueSin));
				scaledValue = (this.__projectedMaximum - projectedValue) * effectiveHeight / this.__projectedRange;
				scaledValue = effectiveTop + scaledValue;
				u = (scaledValue - unitTop * effectiveHeight) / unitHeight;
				scaledValue = (u - (windowTop * viewportHeight)) / windowHeight;
				if (useArray) {
					input[i] = scaledValue;
				} else {
					unscaledValues.item(i, scaledValue);
				}
			} else {
				if (unscaledValue < $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue) {
					unscaledValue = $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue;
				}
				if (unscaledValue > $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue) {
					unscaledValue = $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue;
				}
				valueR = unscaledValue * (degreeAsRadian);
				valueSin = Math.sin(valueR);
				projectedValue = 0.5 * Math.log((1 + valueSin) / (1 - valueSin));
				scaledValue = (this.__projectedMaximum - projectedValue) * viewportHeight / this.__projectedRange;
				scaledValue = viewportTop + scaledValue;
				scaledValue = (scaledValue - windowTop * viewportHeight) / windowHeight;
				if (useArray) {
					input[i] = scaledValue;
				} else {
					unscaledValues.item(i, scaledValue);
				}
			}
		}
	}
	,
	calculateRange: function (target, minimumValue, maximumValue, actualMinimumValue, actualMaximumValue) {
		actualMinimumValue = this.clamp(target.minimumValue());
		actualMaximumValue = this.clamp(target.maximumValue());
		return {
			p3: actualMinimumValue,
			p4: actualMaximumValue
		};
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		$.ig.VerticalLinearScaler.prototype.onPropertyChanged.call(this, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.NumericScaler.prototype.actualMinimumValuePropertyName:
			case $.ig.NumericScaler.prototype.actualMaximumValuePropertyName:
				this.__projectedMaximum = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(this._cachedActualMaximumValue);
				this.__projectedRange = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(this._cachedActualMaximumValue) - $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(this._cachedActualMinimumValue);
				break;
		}
	}
	,
	__projectedMaximum: 0,
	__projectedRange: 0,
	maximumValue: function () {
		return $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue;
	}
	,
	minimumValue: function () {
		return $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue;
	}
	,
	init: function () {
		$.ig.VerticalLinearScaler.prototype.init.call(this);
		this.__unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
	},
	getUnscaledValue1: function (scaledValue, windowRect, viewportRect, isInverted) {
		var unscaledValue = (scaledValue - viewportRect.top()) * windowRect.width() + windowRect.top() * viewportRect.height();
		unscaledValue = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(this._cachedActualMaximumValue) - unscaledValue / (viewportRect.height() / this.__projectedRange);
		return $.ig.SphericalMercatorVerticalScaler.prototype.getUnprojectedValue(unscaledValue);
	}
	,
	getScaledValue1: function (unscaledValue, windowRect, viewportRect, isInverted) {
		var scaledValue = viewportRect.top() + this.getScaledValue2(unscaledValue, viewportRect.height());
		return (scaledValue - windowRect.top() * viewportRect.height()) / windowRect.width();
	}
	,
	getScaledValue3: function (unscaledValue, windowRect, viewportRect, effectiveViewportRect, isInverted) {
		var scaledValue = this.getScaledValue2(unscaledValue, effectiveViewportRect.height());
		return (scaledValue - windowRect.top() * viewportRect.height()) / windowRect.width();
	}
	,
	getScaledValue2: function (unscaledValue, viewportHeight) {
		unscaledValue = unscaledValue > $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue ? (unscaledValue < $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue ? unscaledValue : $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue) : $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue;
		var projectedValue = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(unscaledValue);
		var scaledValue = (this.__projectedMaximum - projectedValue) * viewportHeight / this.__projectedRange;
		return (scaledValue);
	}
	,
	getProjectedValue: function (value) {
		var valueR = value * ($.ig.SphericalMercatorVerticalScaler.prototype._degreeAsRadian);
		var valueSin = Math.sin(valueR);
		var projectedValue = 0.5 * Math.log((1 + valueSin) / (1 - valueSin));
		return projectedValue;
	}
	,
	getUnprojectedValue: function (value) {
		value = Math.exp(2 * value);
		return Math.asin((value - 1) / (value + 1)) / $.ig.SphericalMercatorVerticalScaler.prototype._degreeAsRadian;
	}
	,
	clamp: function (value) {
		if (value < $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue) {
			return $.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue;
		}
		if (value > $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue) {
			return $.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue;
		}
		return value;
	}
	,
	$type: new $.ig.Type('SphericalMercatorVerticalScaler', $.ig.VerticalLinearScaler.prototype.$type)
}, true);

$.ig.util.defType('ColorScale', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	getColor: function (value, defaultMinimum, defaultMaximum, valueColumn) {
	}
	,
	propertyUpdated: function (propertyName, oldValue, newValue) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('ColorScale', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('CustomPaletteColorScale', 'ColorScale', {
	init: function () {
		this.__palette = null;
		this.__transparent = new $.ig.Color();
		this.__cachedMinimum = NaN;
		this.__cachedMaximum = NaN;
		this.__cachedMinimumIsNaN = true;
		this.__cachedMaximumIsNaN = true;
		this.__cachedInterpolationMode = $.ig.ColorScaleInterpolationMode.prototype.select;
		$.ig.ColorScale.prototype.init.call(this);
		this.__transparent = $.ig.Color.prototype.fromArgb(0, 0, 0, 0);
		this.__palette = new $.ig.ObservableCollection$1($.ig.Color.prototype.$type, 0);
		var $t = this.__palette;
		$t.collectionChanged = $.ig.Delegate.prototype.combine($t.collectionChanged, this.palette_CollectionChanged.runOn(this));
	},
	minimumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CustomPaletteColorScale.prototype.minimumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CustomPaletteColorScale.prototype.minimumValueProperty);
		}
	}
	,
	maximumValue: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CustomPaletteColorScale.prototype.maximumValueProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CustomPaletteColorScale.prototype.maximumValueProperty);
		}
	}
	,
	__palette: null,
	palette: function (value) {
		if (arguments.length === 1) {
			if (this.__palette != null) {
				var $t = this.__palette;
				$t.collectionChanged = $.ig.Delegate.prototype.remove($t.collectionChanged, this.palette_CollectionChanged.runOn(this));
			}
			this.__palette = value;
			if (this.__palette != null) {
				var $t1 = this.__palette;
				$t1.collectionChanged = $.ig.Delegate.prototype.combine($t1.collectionChanged, this.palette_CollectionChanged.runOn(this));
			}
			this.propertyUpdated($.ig.CustomPaletteColorScale.prototype._palettePropertyName, this.palette(), this.palette());
			return value;
		} else {
			return this.__palette;
		}
	}
	,
	palette_CollectionChanged: function (sender, e) {
		this.propertyUpdated($.ig.CustomPaletteColorScale.prototype._palettePropertyName, this.palette(), this.palette());
	}
	,
	__transparent: null,
	propertyUpdated: function (propertyName, oldValue, newValue) {
		$.ig.ColorScale.prototype.propertyUpdated.call(this, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.CustomPaletteColorScale.prototype._minimumValuePropertyName:
				this.__cachedMinimum = this.minimumValue();
				this.__cachedMinimumIsNaN = $.ig.util.isNaN(this.__cachedMinimum);
				break;
			case $.ig.CustomPaletteColorScale.prototype._maximumValuePropertyName:
				this.__cachedMaximum = this.maximumValue();
				this.__cachedMaximumIsNaN = $.ig.util.isNaN(this.__cachedMaximum);
				break;
			case $.ig.CustomPaletteColorScale.prototype._interpolationModePropertyName:
				this.__cachedInterpolationMode = this.interpolationMode();
				break;
		}
	}
	,
	__cachedMinimum: 0,
	__cachedMaximum: 0,
	__cachedMinimumIsNaN: false,
	__cachedMaximumIsNaN: false,
	__cachedInterpolationMode: 0,
	getColor: function (value, defaultMinimum, defaultMaximum, valueColumn) {
		if (((this.__palette == null) || (this.__palette.count() == 0)) || valueColumn == null) {
			return this.__transparent;
		}
		var minimumValue = this.__cachedMinimumIsNaN ? defaultMinimum : this.__cachedMinimum;
		var maximumValue = this.__cachedMaximumIsNaN ? defaultMaximum : this.__cachedMaximum;
		var normalizedValue = (value - minimumValue) / (maximumValue - minimumValue);
		if ($.ig.util.isNaN(normalizedValue) || normalizedValue < 0 || normalizedValue > 1) {
			return this.__transparent;
		}
		var index = normalizedValue * (this.__palette.count() - 1);
		if (this.__cachedInterpolationMode == $.ig.ColorScaleInterpolationMode.prototype.interpolateHSV || this.__cachedInterpolationMode == $.ig.ColorScaleInterpolationMode.prototype.interpolateRGB) {
			var floor = Math.floor(index);
			var ceiling = Math.ceil(index);
			var preceding = this.__palette.__inner[$.ig.truncate(floor)];
			var subsequent = this.__palette.__inner[$.ig.truncate(ceiling)];
			var decimalPortion = index - floor;
			var colorUtilInteprolationMode = this.__cachedInterpolationMode == $.ig.ColorScaleInterpolationMode.prototype.interpolateHSV ? $.ig.InterpolationMode.prototype.hSV : $.ig.InterpolationMode.prototype.rGB;
			return $.ig.ColorUtil.prototype.getInterpolation(preceding, decimalPortion, subsequent, colorUtilInteprolationMode);
		} else {
			var roundedIndex = $.ig.truncate(Math.round(index));
			return this.__palette.__inner[roundedIndex];
		}
	}
	,
	interpolationMode: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CustomPaletteColorScale.prototype.interpolationModeProperty, $.ig.ColorScaleInterpolationMode.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.CustomPaletteColorScale.prototype.interpolationModeProperty));
		}
	}
	,
	providePalette: function (colors) {
		var colorColl = new $.ig.ObservableCollection$1($.ig.Color.prototype.$type, 0);
		for (var i = 0; i < colors.length; i++) {
			var color = colors[i];
			var c = (function () {
				var $ret = new $.ig.Color();
				$ret.colorString(color);
				return $ret;
			}());
			colorColl.add(c);
		}
		this.palette(colorColl);
	}
	,
	$type: new $.ig.Type('CustomPaletteColorScale', $.ig.ColorScale.prototype.$type)
}, true);

$.ig.util.defType('XamGeographicMap', 'SeriesViewer', {
	init: function () {
		var $self = this;
		this.__geographicTopLeftPeg = { __x: NaN, __y: NaN, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		this.__actualWindowScale = 1;
		this.__pendingZoomChange = $.ig.Rect.prototype.empty();
		$.ig.SeriesViewer.prototype.init.call(this);
		this.__defaultWorldRect_projectedHeight = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue($.ig.XamGeographicMap.prototype._defaultWorldRect.bottom()) - $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue($.ig.XamGeographicMap.prototype._defaultWorldRect.top());
		this.defaultStyleKey($.ig.XamGeographicMap.prototype.$type);
		this.backgroundContent(new $.ig.OpenStreetMapImagery());
		var xAxis = (function () {
			var $ret = new $.ig.NumericXAxis();
			$ret.seriesViewer($self);
			return $ret;
		}());
		xAxis.isDisabled(true);
		this.xAxis(xAxis);
		this.xAxis().scaler(new $.ig.SphericalMercatorHorizontalScaler());
		var yAxis = (function () {
			var $ret = new $.ig.NumericYAxis();
			$ret.seriesViewer($self);
			return $ret;
		}());
		yAxis.isDisabled(true);
		this.yAxis(yAxis);
		this.yAxis().scaler(new $.ig.SphericalMercatorVerticalScaler());
		this.invalidateActualWorldRect();
		this.updateAxisRange();
	},
	isZoomingHorizontallyEnabled: function () {
		return this.zoomable();
	}
	,
	isZoomingVerticallyEnabled: function () {
		return this.zoomable();
	}
	,
	zoomable: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGeographicMap.prototype.zoomableProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamGeographicMap.prototype.zoomableProperty);
		}
	}
	,
	__defaultWorldRect_projectedHeight: 0,
	worldRect: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGeographicMap.prototype.worldRectProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamGeographicMap.prototype.worldRectProperty);
		}
	}
	,
	__actualWorldRect: null,
	actualWorldRect: function (value) {
		if (arguments.length === 1) {
			var changed = $.ig.Rect.prototype.l_op_Inequality(this.__actualWorldRect, value);
			if (changed) {
				var oldValue = this.__actualWorldRect;
				this.__actualWorldRect = value;
				this.raisePropertyChanged($.ig.XamGeographicMap.prototype.actualWorldRectPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__actualWorldRect;
		}
	}
	,
	invalidateActualWorldRect: function () {
		if (this.worldRect().isEmpty()) {
			return;
		}
		this.actualWorldRect(this.padGeographicRect(this.worldRect()));
	}
	,
	padGeographicRect: function (input) {
		var T = input.top();
		var L = input.left();
		var W = input.width();
		var H = input.height();
		var projectedBottom = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(input.bottom());
		var projectedTop = $.ig.SphericalMercatorVerticalScaler.prototype.getProjectedValue(input.top());
		var projectedHeight = projectedBottom - projectedTop;
		var scaleWidth = input.width() / $.ig.XamGeographicMap.prototype._defaultWorldRect.width();
		var scaleRatio = projectedHeight / scaleWidth;
		if (projectedHeight / scaleWidth > this.__defaultWorldRect_projectedHeight) {
			var newScaleWidth = projectedHeight / this.__defaultWorldRect_projectedHeight;
			var diff = newScaleWidth - scaleWidth;
			L = L - (diff / 2) * $.ig.XamGeographicMap.prototype._defaultWorldRect.width();
			W = newScaleWidth * $.ig.XamGeographicMap.prototype._defaultWorldRect.width();
		} else if (projectedHeight / scaleWidth < this.__defaultWorldRect_projectedHeight) {
			var newProjectedHeight = this.__defaultWorldRect_projectedHeight * scaleWidth;
			var diff1 = newProjectedHeight - projectedHeight;
			var newProjectedTop = projectedTop - (diff1 / 2);
			var newProjectedBottom = projectedBottom + (diff1 / 2);
			T = $.ig.SphericalMercatorVerticalScaler.prototype.getUnprojectedValue(newProjectedTop);
			H = $.ig.SphericalMercatorVerticalScaler.prototype.getUnprojectedValue(newProjectedBottom) - T;
		}
		var newWorldRect = new $.ig.Rect(0, L, T, W, H);
		return newWorldRect;
	}
	,
	backgroundContent: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.backgroundContent();
			if (changed) {
				var oldValue = this.backgroundContent();
				this.__backgroundContent = value;
				this.raisePropertyChanged($.ig.XamGeographicMap.prototype.backgroundContentPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__backgroundContent;
		}
	}
	,
	__backgroundContent: null,
	xAxis: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.xAxis();
			if (changed) {
				var oldValue = this.xAxis();
				this.__xAxis = value;
				this.raisePropertyChanged($.ig.XamGeographicMap.prototype.xAxisPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__xAxis;
		}
	}
	,
	__xAxis: null,
	yAxis: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.yAxis();
			if (changed) {
				var oldValue = this.yAxis();
				this.__yAxis = value;
				this.raisePropertyChanged($.ig.XamGeographicMap.prototype.yAxisPropertyName, oldValue, value);
			}
			return value;
		} else {
			return this.__yAxis;
		}
	}
	,
	__yAxis: null,
	_backgroundImagery: null,
	backgroundImagery: function (value) {
		if (arguments.length === 1) {
			this._backgroundImagery = value;
			return value;
		} else {
			return this._backgroundImagery;
		}
	}
	,
	getZoomRectFromGeoRect: function (geographic) {
		return this.getZoomFromGeographic1(geographic);
	}
	,
	getZoomFromGeographic1: function (geographic) {
		var paddedGeo = this.padGeographicRect(geographic);
		var xaxis = this.xAxis();
		var yaxis = this.yAxis();
		var xParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), xaxis.isInverted());
		xParams._effectiveViewportRect = this.effectiveViewport();
		var yParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), yaxis.isInverted());
		yParams._effectiveViewportRect = this.effectiveViewport();
		var gL = this.xAxis().getScaledValue(paddedGeo.left(), xParams);
		var gR = this.xAxis().getScaledValue(paddedGeo.right(), xParams);
		var gT = this.yAxis().getScaledValue(paddedGeo.top(), yParams);
		var gB = this.yAxis().getScaledValue(paddedGeo.bottom(), yParams);
		var L = gL / this.viewportRect().width();
		var T = gB / this.viewportRect().height();
		var W = (gR - gL) / this.viewportRect().width();
		var H = (gT - gB) / this.viewportRect().height();
		var result = new $.ig.Rect(0, L, T, W, H);
		result.intersect($.ig.XamGeographicMap.prototype.__unitRect);
		if (result.isEmpty()) {
			result = $.ig.XamGeographicMap.prototype.__unitRect;
		}
		return result;
	}
	,
	getZoomFromGeographic: function (northWest, southEast) {
		var X = northWest.__x;
		var Y = southEast.__y;
		var W = southEast.__x - northWest.__x;
		var H = northWest.__y - southEast.__y;
		if (W < 0 || H < 0) {
			return $.ig.XamGeographicMap.prototype.__unitRect;
		} else {
			var geographic = new $.ig.Rect(0, X, Y, W, H);
			return this.getZoomFromGeographic1(geographic);
		}
	}
	,
	getGeographicFromZoom: function (windowRect) {
		var xaxis = this.xAxis();
		var yaxis = this.yAxis();
		var xParams = new $.ig.ScalerParams(1, windowRect, this.viewportRect(), xaxis.isInverted());
		xParams._effectiveViewportRect = this.effectiveViewport();
		var yParams = new $.ig.ScalerParams(1, windowRect, this.viewportRect(), yaxis.isInverted());
		yParams._effectiveViewportRect = this.effectiveViewport();
		var contentViewport = this.contentViewport();
		var L = xaxis.getUnscaledValue(contentViewport.left(), xParams);
		var T = yaxis.getUnscaledValue(contentViewport.top(), yParams);
		var R = xaxis.getUnscaledValue(contentViewport.right(), xParams);
		var B = yaxis.getUnscaledValue(contentViewport.bottom(), yParams);
		var W = R - L;
		var H = T - B;
		var result = new $.ig.Rect(0, L, B, W, H);
		result.intersect($.ig.XamGeographicMap.prototype._defaultWorldRect);
		if (result.isEmpty()) {
			result = $.ig.XamGeographicMap.prototype._defaultWorldRect;
		}
		return result;
	}
	,
	getGeographicPoint: function (windowCoordinate) {
		var geoX = this.xAxis().unscaleValue(windowCoordinate.__x);
		var geoY = this.yAxis().unscaleValue(windowCoordinate.__y);
		return { __x: geoX, __y: geoY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	getWindowPoint: function (geographicCoordinate) {
		var winX = this.xAxis().scaleValue(geographicCoordinate.__x);
		var winY = this.yAxis().scaleValue(geographicCoordinate.__y);
		return { __x: winX, __y: winY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	clearTileCache: function () {
		var imagery = $.ig.util.cast($.ig.GeographicMapImagery.prototype.$type, this.backgroundContent());
		if (imagery != null) {
			imagery.clearTileCache();
		}
	}
	,
	prepareBrush: function (b) {
	}
	,
	getMarkerBrushByIndex: function (index) {
		return this.xamGeographicMapView().getMarkerBrushByIndex(index);
	}
	,
	getMarkerOutlineByIndex: function (index) {
		return this.xamGeographicMapView().getMarkerOutlineByIndex(index);
	}
	,
	useFixedAspectZoom: function () {
		return true;
	}
	,
	styleUpdated: function () {
		this.xamGeographicMapView().styleUpdated();
		var en = this.series().getEnumerator();
		while (en.moveNext()) {
			var series = en.current();
			series.styleUpdated();
		}
	}
	,
	__geographicTopLeftPeg: null,
	updateGeographicPeg: function () {
		if (!this.viewportRect().isEmpty() && this.viewportRect().width() > 0 && this.viewportRect().height() > 0) {
			var X = this.xAxis().unscaleValue(this.viewportRect().left());
			var Y = this.yAxis().unscaleValue(this.viewportRect().top());
			this.__geographicTopLeftPeg = { __x: X, __y: Y, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
		}
	}
	,
	slideActualWindowRectToGeographicPeg: function () {
		if ($.ig.util.isNaN(this.__geographicTopLeftPeg.__x) || $.ig.util.isNaN(this.__geographicTopLeftPeg.__y)) {
			this.updateGeographicPeg();
		}
		var geoX = this.__geographicTopLeftPeg.__x;
		var geoY = this.__geographicTopLeftPeg.__y;
		if (!$.ig.util.isNaN(geoX) & !$.ig.util.isNaN(geoY)) {
			var left = this.actualWindowRect().left();
			var top = this.actualWindowRect().top();
			var width = this.actualWindowRect().width();
			var height = this.actualWindowRect().height();
			if (width > this.__scaleTilesetRect.width()) {
			} else {
				var xParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), this.xAxis().isInverted());
				xParams._effectiveViewportRect = this.effectiveViewport();
				var pixelX = this.xAxis().getScaledValue(geoX, xParams);
				left = pixelX / this.viewportRect().width();
				if (left + width > this.__scaleTilesetRect.right()) {
					left = this.__scaleTilesetRect.right() - width;
				} else if (left < this.__scaleTilesetRect.left()) {
					left = this.__scaleTilesetRect.left();
				}
			}
			if (height > this.__scaleTilesetRect.height()) {
			} else {
				var yParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), this.yAxis().isInverted());
				yParams._effectiveViewportRect = this.effectiveViewport();
				var pixelY = this.yAxis().getScaledValue(geoY, yParams);
				top = pixelY / this.viewportRect().height();
				if (top + height > this.__scaleTilesetRect.bottom()) {
					top = this.__scaleTilesetRect.bottom() - height;
				} else if (top < this.__scaleTilesetRect.top()) {
					top = this.__scaleTilesetRect.top();
				}
			}
			this.actualWindowRect(new $.ig.Rect(0, left, top, width, height));
		}
	}
	,
	isMap: function () {
		return true;
	}
	,
	__scaleTilesetRect: null,
	calculateActualWindowRect: function () {
		var baseRect = $.ig.SeriesViewer.prototype.calculateActualWindowRect.call(this);
		if (this.xAxis() == null || this.yAxis() == null) {
			return baseRect;
		}
		var xParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), this.xAxis().isInverted());
		xParams._effectiveViewportRect = this.effectiveViewport();
		var yParams = new $.ig.ScalerParams(1, $.ig.XamGeographicMap.prototype.__unitRect, this.viewportRect(), this.yAxis().isInverted());
		yParams._effectiveViewportRect = this.effectiveViewport();
		var tsLeft = this.xAxis().getScaledValue($.ig.XamGeographicMap.prototype._defaultWorldRect.left(), xParams);
		var tsRight = this.xAxis().getScaledValue($.ig.XamGeographicMap.prototype._defaultWorldRect.right(), xParams);
		var tsBottom = this.yAxis().getScaledValue($.ig.XamGeographicMap.prototype._defaultWorldRect.top(), yParams);
		var tsTop = this.yAxis().getScaledValue($.ig.XamGeographicMap.prototype._defaultWorldRect.bottom(), yParams);
		this.__scaleTilesetRect = new $.ig.Rect(0, tsLeft / this.viewportRect().width(), tsTop / this.viewportRect().height(), (tsRight - tsLeft) / this.viewportRect().width(), (tsBottom - tsTop) / this.viewportRect().height());
		var left = baseRect.left();
		var top = baseRect.top();
		if (this.windowRect().width() > this.__scaleTilesetRect.width()) {
			left = 0.5 - (this.windowRect().width() / 2);
		} else if (left + baseRect.width() > this.__scaleTilesetRect.right()) {
			left = this.__scaleTilesetRect.right() - this.windowRect().width();
		} else if (left < this.__scaleTilesetRect.left()) {
			left = this.__scaleTilesetRect.left();
		}
		if (this.windowRect().height() > this.__scaleTilesetRect.height()) {
			top = 0.5 - (this.windowRect().height() / 2);
		} else if (top + this.windowRect().height() > this.__scaleTilesetRect.bottom()) {
			top = this.__scaleTilesetRect.bottom() - this.windowRect().height();
		} else if (top < this.__scaleTilesetRect.top()) {
			top = this.__scaleTilesetRect.top();
		}
		return new $.ig.Rect(0, left, top, baseRect.width(), baseRect.height());
	}
	,
	computeEffectiveViewport: function (viewportRect) {
		if (viewportRect.isEmpty()) {
			return $.ig.Rect.prototype.empty();
		}
		var effectiveViewportRect;
		if (viewportRect.width() > viewportRect.height()) {
			var offset = $.ig.intDivide($.ig.truncate((viewportRect.width() - viewportRect.height())), 2);
			effectiveViewportRect = new $.ig.Rect(0, viewportRect.left() + offset, viewportRect.top(), viewportRect.height(), viewportRect.height());
		} else {
			var offset1 = $.ig.intDivide($.ig.truncate((viewportRect.height() - viewportRect.width())), 2);
			effectiveViewportRect = new $.ig.Rect(0, viewportRect.left(), viewportRect.top() + offset1, viewportRect.width(), viewportRect.width());
		}
		return effectiveViewportRect;
	}
	,
	getEffectiveViewport: function (viewportRect) {
		return this.computeEffectiveViewport(viewportRect);
	}
	,
	createView: function () {
		return new $.ig.XamGeographicMapView(this);
	}
	,
	getBrushByIndex: function (index) {
		return this.xamGeographicMapView().getBrushByIndex(index);
	}
	,
	getOutlineByIndex: function (index) {
		return this.xamGeographicMapView().getOutlineByIndex(index);
	}
	,
	initializeAxis: function (axis) {
		if (axis != null) {
			axis.seriesViewer(this);
		}
	}
	,
	onViewCreated: function (view) {
		$.ig.SeriesViewer.prototype.onViewCreated.call(this, view);
		this.xamGeographicMapView(view);
	}
	,
	processPlotAreaSizeChanged: function (oldGridAreaRect, newGridAreaRect) {
		$.ig.SeriesViewer.prototype.processPlotAreaSizeChanged.call(this, oldGridAreaRect, newGridAreaRect);
		this.updateAxisViewport(this.xAxis());
		this.updateAxisViewport(this.yAxis());
		if ($.ig.Rect.prototype.l_op_Inequality(oldGridAreaRect, newGridAreaRect)) {
			this.slideActualWindowRectToGeographicPeg();
		}
		this.zoomImmediate();
	}
	,
	windowScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.XamGeographicMap.prototype.windowScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.XamGeographicMap.prototype.windowScaleProperty);
		}
	}
	,
	__actualWindowScale: 0,
	actualWindowScale: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__actualWindowScale;
			this.__actualWindowScale = value;
			this.raisePropertyChanged($.ig.XamGeographicMap.prototype.actualWindowScalePropertyName, oldValue, this.__actualWindowScale);
			return value;
		} else {
			return this.__actualWindowScale;
		}
	}
	,
	getActualWindowScaleHorizontal: function () {
		return this.actualWindowScale();
	}
	,
	getActualWindowScaleVertical: function () {
		return this.actualWindowScale();
	}
	,
	updateAcutalWindowProperties: function () {
		$.ig.SeriesViewer.prototype.updateAcutalWindowProperties.call(this);
		var scale = Math.min(this.actualWindowRect().width(), this.actualWindowRect().height());
		this.actualWindowScale(scale);
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.SeriesViewer.prototype.windowRectPropertyName:
				this.updateGeographicPeg();
				break;
		}
		$.ig.SeriesViewer.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.XamGeographicMap.prototype.backgroundContentPropertyName:
				if (this.backgroundImagery() != null) {
					var $t = this.backgroundImagery();
					$t.imageTilesReady = $.ig.Delegate.prototype.remove($t.imageTilesReady, this.msi_ImageTilesReady.runOn(this));
					this.backgroundImagery().deferralHandler(null);
				}
				this.backgroundImagery($.ig.util.cast($.ig.GeographicMapImagery.prototype.$type, this.backgroundContent()));
				if (this.backgroundImagery() != null) {
					var $t1 = this.backgroundImagery();
					$t1.imageTilesReady = $.ig.Delegate.prototype.combine($t1.imageTilesReady, this.msi_ImageTilesReady.runOn(this));
					this.backgroundImagery().deferralHandler(this);
				}
				this.xamGeographicMapView().onBackgroundImageryProvided($.ig.util.cast($.ig.GeographicMapImagery.prototype.$type, oldValue), $.ig.util.cast($.ig.GeographicMapImagery.prototype.$type, newValue));
				if (this.backgroundImagery() != null) {
					this.backgroundImagery().geographicMap(this);
					this.xamGeographicMapView().actualWindowRectUpdated(this.actualWindowRect());
				}
				break;
			case $.ig.XamGeographicMap.prototype.yAxisPropertyName:
			case $.ig.XamGeographicMap.prototype.xAxisPropertyName:
				this.unInitializeAxis(oldValue);
				this.initializeAxis(newValue);
				break;
			case $.ig.XamGeographicMap.prototype._worldRectPropertyName:
				this.windowRect($.ig.XamGeographicMap.prototype.__unitRect);
				this.invalidateActualWorldRect();
				this.updateAxisRange();
				this.updateGeographicPeg();
				this.actualWindowRect(this.calculateActualWindowRect());
				this.xamGeographicMapView().backgroundContentNeedsRefresh();
				break;
			case $.ig.SeriesViewer.prototype.actualWindowRectPropertyName:
				this.xamGeographicMapView().actualWindowRectUpdated(this.actualWindowRect());
				break;
			case $.ig.XamGeographicMap.prototype.actualWorldRectPropertyName:
				var en = this.series().getEnumerator();
				while (en.moveNext()) {
					var ss = en.current();
					ss.renderSeries(false);
				}
				break;
			case $.ig.XamGeographicMap.prototype.windowScalePropertyName:
				this.actualWindowScale(this.windowScale());
				break;
			case $.ig.XamGeographicMap.prototype.actualWindowScalePropertyName:
				if (!this.suspendWindowRect()) {
					this.updateWindowRect(this.getActualWindowScaleHorizontal(), this.getActualWindowScaleVertical());
				}
				break;
			case $.ig.XamGeographicMap.prototype.zoomablePropertyName:
				this.updateOverviewPlusDetailPaneControlPanelVisibility();
				break;
		}
	}
	,
	imageTilesReady: null,
	msi_ImageTilesReady: function (sender, e) {
		if (this.imageTilesReady != null) {
			this.imageTilesReady(this, new $.ig.EventArgs());
		}
	}
	,
	unInitializeAxis: function (axis) {
		if (axis != null) {
			axis.seriesViewer(null);
		}
	}
	,
	updateAxisRange: function () {
		if (this.xAxis() != null) {
			this.xAxis().minimumValue(this.actualWorldRect().left());
			this.xAxis().maximumValue(this.actualWorldRect().right());
		}
		if (this.yAxis() != null) {
			this.yAxis().minimumValue(this.actualWorldRect().top());
			this.yAxis().maximumValue(this.actualWorldRect().bottom());
		}
		this.xAxis().updateRange1(true);
		this.yAxis().updateRange1(true);
	}
	,
	_xamGeographicMapView: null,
	xamGeographicMapView: function (value) {
		if (arguments.length === 1) {
			this._xamGeographicMapView = value;
			return value;
		} else {
			return this._xamGeographicMapView;
		}
	}
	,
	__fontInfo: null,
	getFontInfo: function () {
		this.__fontInfo = this.xamGeographicMapView().font();
		return this.__fontInfo;
	}
	,
	getAxisLineBrush: function () {
		return this.xamGeographicMapView().axisLineBrush();
	}
	,
	getFontBrush: function () {
		return this.xamGeographicMapView().fontBrush();
	}
	,
	setDataSourceForSeries: function (target, source) {
		if ($.ig.util.cast($.ig.ItfConverter.prototype.$type, source) !== null) {
			var itf = (source);
			var scatter = (target);
			scatter.itemsSource(itf.triangulationSource().points());
			scatter.trianglesSource(itf.triangulationSource().triangles());
		} else {
			$.ig.SeriesViewer.prototype.setDataSourceForSeries.call(this, target, source);
		}
	}
	,
	register: function (source, handler) {
		this.registerBackgroundContent(source, handler);
	}
	,
	unRegister: function (source) {
		this.unRegisterBackgroundContent(source);
	}
	,
	deferredRefresh: function () {
		this.deferBackgroundRefresh();
	}
	,
	doUpdateOPDMobileMode: function (mobileMode) {
		this.updateOPDMobileMode(mobileMode);
	}
	,
	exportVisualData: function () {
		var cvd = new $.ig.ChartVisualData();
		var xvd = this.xAxis().exportVisualData();
		var yvd = this.yAxis().exportVisualData();
		cvd.axes().add(xvd);
		cvd.axes().add(yvd);
		for (var i = 0; i < this.series().count(); i++) {
			var svd = this.series().__inner[i].exportVisualData();
			cvd.series().add(svd);
		}
		cvd.name(this.name());
		this.exportTitleData(cvd);
		cvd.contentArea(this.viewportRect());
		cvd.centralArea(this.viewportRect());
		cvd.plotArea(this.viewportRect());
		return cvd;
	}
	,
	getCurrentContentViewport: function (viewportRect, effectiveViewportRect, windowRect) {
		return viewportRect;
	}
	,
	onImageTilesReady: function () {
	}
	,
	__pendingZoomChange: null,
	pendingZoomChange: function (value) {
		if (arguments.length === 1) {
			this.__pendingZoomChange = value;
			return value;
		} else {
			return this.__pendingZoomChange;
		}
	}
	,
	zoomToGeographic: function (geographic) {
		this.pendingZoomChange(geographic);
		this.zoomImmediate();
	}
	,
	zoomImmediate: function () {
		if (this.zoomIsReady() && !this.pendingZoomChange().isEmpty()) {
			var window = this.getZoomFromGeographic1(this.pendingZoomChange());
			this.pendingZoomChange($.ig.Rect.prototype.empty());
			this.windowNotify(window, false);
		}
	}
	,
	zoomIsReady: function () {
		return !this.viewportRect().isEmpty() && !this.effectiveViewport().isEmpty();
	}
	,
	$type: new $.ig.Type('XamGeographicMap', $.ig.SeriesViewer.prototype.$type, [$.ig.IMapRenderDeferralHandler.prototype.$type])
}, true);

$.ig.util.defType('GeographicMapImagery', 'Control', {
	init: function (tileSource) {
		this.__multiScaleImage = null;
		$.ig.Control.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicMapImagery.prototype.$type);
		this.tileSource(tileSource);
		this.view(this.createView());
		this.onViewCreated(this.view());
	},
	onViewCreated: function (view) {
	}
	,
	createView: function () {
		return new $.ig.GeographicMapImageryView(this);
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
	__deferralHandler: null,
	deferralHandler: function (value) {
		if (arguments.length === 1) {
			this.__deferralHandler = value;
			if (this.multiScaleImage() != null) {
				this.multiScaleImage().deferralHandler(value);
			}
			return value;
		} else {
			return this.__deferralHandler;
		}
	}
	,
	multiScaleImage_ImageTilesReady: function (sender, e) {
		if (this.imageTilesReady != null) {
			this.imageTilesReady(this, new $.ig.EventArgs());
		}
	}
	,
	onMSIProvided: function () {
		var $t = this.multiScaleImage();
		$t.imageTilesReady = $.ig.Delegate.prototype.combine($t.imageTilesReady, this.multiScaleImage_ImageTilesReady.runOn(this));
		this.multiScaleImage().deferralHandler(this.deferralHandler());
		var $t1 = this.multiScaleImage();
		$t1.imagesChanged = $.ig.Delegate.prototype.combine($t1.imagesChanged, this.multiScaleImage_ImagesChanged.runOn(this));
	}
	,
	updateWindowRect: function () {
		if (this.multiScaleImage() != null) {
			this.view().needsRefresh();
		}
	}
	,
	onPropertyUpdated: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.GeographicMapImagery.prototype._windowRectPropertyName:
				this.updateWindowRect();
				break;
			case $.ig.GeographicMapImagery.prototype._geographicMapPropertyName:
				this.view().needsRefresh();
				break;
		}
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	windowRect: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicMapImagery.prototype.windowRectProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicMapImagery.prototype.windowRectProperty);
		}
	}
	,
	geographicMap: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicMapImagery.prototype.geographicMapProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicMapImagery.prototype.geographicMapProperty);
		}
	}
	,
	onGeographicMapChanged1: function (d, e) {
		(d).onGeographicMapChanged(e.oldValue(), e.newValue());
	}
	,
	onGeographicMapChanged: function (oldValue, newValue) {
		this.onPropertyUpdated($.ig.GeographicMapImagery.prototype._geographicMapPropertyName, oldValue, newValue);
	}
	,
	clearTileCache: function () {
		if (this.multiScaleImage() != null) {
			this.multiScaleImage().invalidateTileLayer(0, 0, 0, 0);
		}
	}
	,
	imageTilesReady: null,
	__multiScaleImage: null,
	multiScaleImage: function (value) {
		if (arguments.length === 1) {
			this.__multiScaleImage = value;
			if (this.propertyChanged != null) {
				this.propertyChanged(this, new $.ig.PropertyChangedEventArgs("MultiScaleImage"));
			}
			return value;
		} else {
			return this.__multiScaleImage;
		}
	}
	,
	_tileSource: null,
	tileSource: function (value) {
		if (arguments.length === 1) {
			this._tileSource = value;
			return value;
		} else {
			return this._tileSource;
		}
	}
	,
	propertyChanged: null,
	refresh: function (finalSize) {
		if (this.geographicMap() == null || finalSize.width() < 1 || finalSize.height() < 1) {
			return finalSize;
		}
		var viewportRect = new $.ig.Rect(0, 0, 0, finalSize.width(), finalSize.height());
		var effectiveViewportRect = this.geographicMap().getEffectiveViewport(viewportRect);
		var defaultWorldRect = $.ig.XamGeographicMap.prototype._defaultWorldRect;
		var actualWorldRect = this.geographicMap().actualWorldRect();
		var windowRectZoom = Math.min(this.windowRect().height(), this.windowRect().width());
		var worldRectZoom = actualWorldRect.width() / defaultWorldRect.width();
		this.multiScaleImage().viewportWidth((viewportRect.width() / effectiveViewportRect.width()) * windowRectZoom * worldRectZoom);
		var xaxis = this.geographicMap().xAxis();
		var yaxis = this.geographicMap().yAxis();
		var xParams = new $.ig.ScalerParams(1, this.windowRect(), viewportRect, xaxis.isInverted());
		xParams._effectiveViewportRect = effectiveViewportRect;
		var yParams = new $.ig.ScalerParams(1, this.windowRect(), viewportRect, yaxis.isInverted());
		yParams._effectiveViewportRect = effectiveViewportRect;
		var pixelOffsetX = xaxis.getScaledValue(defaultWorldRect.left(), xParams);
		var pixelOffsetY = yaxis.getScaledValue(defaultWorldRect.bottom(), yParams);
		var scaleOffsetX = (-pixelOffsetX / viewportRect.width()) * this.multiScaleImage().viewportWidth();
		var scaleOffsetY = (-pixelOffsetY / viewportRect.height()) * this.multiScaleImage().viewportWidth() * (viewportRect.height() / viewportRect.width());
		this.multiScaleImage().viewportOrigin({ __x: scaleOffsetX, __y: scaleOffsetY, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
		return finalSize;
	}
	,
	provideContext: function (context) {
		this.view().onContextProvided(context);
	}
	,
	provideViewport: function (MapViewport) {
		this.view().onViewportProvided(MapViewport);
	}
	,
	imagesChanged: null,
	multiScaleImage_ImagesChanged: function (sender, e) {
		if (this.imagesChanged != null) {
			this.imagesChanged(this, e);
		}
	}
	,
	needsRefresh: function () {
		this.view().needsRefresh();
	}
	,
	$type: new $.ig.Type('GeographicMapImagery', $.ig.Control.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('BingMapsMapImagery', 'GeographicMapImagery', {
	init: function () {
		$.ig.GeographicMapImagery.prototype.init.call(this, new $.ig.BingMapsTileSource(0));
		this.bingView().onInit();
		this.actualBingImageryRestUri($.ig.BingMapsMapImagery.prototype._defaultBingUri);
	},
	createView: function () {
		return new $.ig.BingMapsMapImageryView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapImagery.prototype.onViewCreated.call(this, view);
		this.bingView(view);
	}
	,
	_bingView: null,
	bingView: function (value) {
		if (arguments.length === 1) {
			this._bingView = value;
			return value;
		} else {
			return this._bingView;
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
	isDeferredLoad: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.isDeferredLoadProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsMapImagery.prototype.isDeferredLoadProperty);
		}
	}
	,
	onIsDeferredLoadChanged: function (oldValue, newValue) {
		this.validate();
	}
	,
	tilePath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.tilePathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsMapImagery.prototype.tilePathProperty);
		}
	}
	,
	onTilePathChanged: function (d, e) {
		(d).onPropertyChanged($.ig.BingMapsMapImagery.prototype.tilePathPropertyName, e.oldValue(), e.newValue());
	}
	,
	subDomains: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.subDomainsProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsMapImagery.prototype.subDomainsProperty);
		}
	}
	,
	onSubDomainsChanged: function (d, e) {
		(d).onPropertyChanged($.ig.BingMapsMapImagery.prototype.subDomainsPropertyName, e.oldValue(), e.newValue());
	}
	,
	__actualTilePath: null,
	actualTilePath: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__actualTilePath;
			this.__actualTilePath = value;
			this.onPropertyChanged($.ig.BingMapsMapImagery.prototype.actualTilePathPropertyName, oldValue, this.__actualTilePath);
			return value;
		} else {
			return this.__actualTilePath;
		}
	}
	,
	__actualSubDomains: null,
	actualSubDomains: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__actualSubDomains;
			this.__actualSubDomains = value;
			this.onPropertyChanged($.ig.BingMapsMapImagery.prototype.actualSubDomainsPropertyName, oldValue, this.__actualSubDomains);
			return value;
		} else {
			return this.__actualSubDomains;
		}
	}
	,
	__bingImageryRestUri: null,
	bingImageryRestUri: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__bingImageryRestUri;
			this.__bingImageryRestUri = value;
			this.onPropertyChanged($.ig.BingMapsMapImagery.prototype.bingImageryRestUriPropertyName, oldValue, this.__bingImageryRestUri);
			return value;
		} else {
			return this.__bingImageryRestUri;
		}
	}
	,
	__actualBingImageryRestUri: null,
	actualBingImageryRestUri: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__actualBingImageryRestUri;
			this.__actualBingImageryRestUri = value;
			this.onPropertyUpdated($.ig.BingMapsMapImagery.prototype.actualBingImageryRestUriPropertyName, oldValue, this.__actualBingImageryRestUri);
			return value;
		} else {
			return this.__actualBingImageryRestUri;
		}
	}
	,
	cultureName: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.cultureNameProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsMapImagery.prototype.cultureNameProperty);
		}
	}
	,
	onCultureNameChanged: function (d, e) {
		(d).onPropertyChanged($.ig.BingMapsMapImagery.prototype.cultureNamePropertyName, e.oldValue(), e.newValue());
	}
	,
	apiKey: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.apiKeyProperty, value);
			return value;
		} else {
			return this.getValue($.ig.BingMapsMapImagery.prototype.apiKeyProperty);
		}
	}
	,
	onApiKeyChanged: function (oldValue, newValue) {
		this.validate();
	}
	,
	imageryStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.BingMapsMapImagery.prototype.imageryStyleProperty, $.ig.BingMapsImageryStyle.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.BingMapsMapImagery.prototype.imageryStyleProperty));
		}
	}
	,
	onImageryStylePropertyChanged: function (oldValue, newValue) {
		this.validate();
	}
	,
	requestMapSettings: function () {
		this.requestMapSettings1(true);
	}
	,
	requestMapSettings1: function (calledByUser) {
	}
	,
	shouldRestBeUsed: function () {
		if ((this.tilePath() == null) && (this.subDomains() == null)) {
			return true;
		}
		return false;
	}
	,
	validate: function () {
		this.isInitialized(false);
		$.ig.Debug.prototype.writeLine("Validating");
		if (!this.isValidApiKey()) {
			this.updateActualTilePathAndSubDomain("", null);
			return;
		}
		if (!this.isDeferredLoad()) {
			this.requestMapSettings1(false);
		}
	}
	,
	isValidApiKey: function () {
		if (String.isNullOrEmpty(this.apiKey()) || this.apiKey().length < 20) {
			return false;
		}
		return true;
	}
	,
	cancelPendingRequest: function () {
	}
	,
	updateActualTilePathAndSubDomain: function (tilePath, subDomains) {
	}
	,
	internalRequestMapSettings: function (calledByUser) {
	}
	,
	actualSubDomains_CollectionChanged: function (sender, e) {
		this.view().needsRefresh();
		this.validate();
		this.onPropertyChanged($.ig.BingMapsMapImagery.prototype.subDomainsPropertyName, this.actualSubDomains(), this.actualSubDomains());
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.BingMapsMapImagery.prototype.tilePathPropertyName:
				this.cancelPendingRequest();
				this.actualTilePath(newValue);
				this.bingView().onTilePathPropertyChanged();
				this.view().needsRefresh();
				break;
			case $.ig.BingMapsMapImagery.prototype.cultureNamePropertyName:
				this.bingView().onCultureNamePropertyChanged();
				this.view().needsRefresh();
				break;
			case $.ig.BingMapsMapImagery.prototype.imageryStylePropertyName:
				this.validate();
				break;
			case $.ig.BingMapsMapImagery.prototype.subDomainsPropertyName:
				this.cancelPendingRequest();
				var oldSubDomains = oldValue;
				var newSubDomains = newValue;
				if (this.actualSubDomains() != null) {
					var $t = this.actualSubDomains();
					$t.collectionChanged = $.ig.Delegate.prototype.remove($t.collectionChanged, this.actualSubDomains_CollectionChanged.runOn(this));
				}
				this.actualSubDomains(newSubDomains);
				if (this.actualSubDomains() != null) {
					newSubDomains.collectionChanged = $.ig.Delegate.prototype.combine(newSubDomains.collectionChanged, this.actualSubDomains_CollectionChanged.runOn(this));
				}
				this.bingView().onSubDomainsPropertyChanged();
				this.view().needsRefresh();
				break;
			case $.ig.BingMapsMapImagery.prototype.actualTilePathPropertyName:
				var newPath = newValue;
				if (String.isNullOrEmpty(newPath)) {
					this.validate();
				}
				break;
			case $.ig.BingMapsMapImagery.prototype.bingImageryRestUriPropertyName:
				this.actualBingImageryRestUri(newValue);
				this.cancelPendingRequest();
				this.validate();
				break;
			case $.ig.BingMapsMapImagery.prototype.actualBingImageryRestUriPropertyName:
				this.cancelPendingRequest();
				this.validate();
				break;
		}
		$.ig.GeographicMapImagery.prototype.onPropertyUpdated.call(this, propertyName, oldValue, newValue);
	}
	,
	$type: new $.ig.Type('BingMapsMapImagery', $.ig.GeographicMapImagery.prototype.$type)
}, true);

$.ig.util.defType('CloudMadeMapImagery', 'GeographicMapImagery', {
	init: function () {
		$.ig.GeographicMapImagery.prototype.init.call(this, new $.ig.CloudMadeTileSource());
		this.cloudView().onInit();
	},
	createView: function () {
		return new $.ig.CloudMadeMapImageryView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapImagery.prototype.onViewCreated.call(this, view);
		this.cloudView(view);
	}
	,
	_cloudView: null,
	cloudView: function (value) {
		if (arguments.length === 1) {
			this._cloudView = value;
			return value;
		} else {
			return this._cloudView;
		}
	}
	,
	key: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CloudMadeMapImagery.prototype.keyProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CloudMadeMapImagery.prototype.keyProperty);
		}
	}
	,
	onKeyChanged: function (d, e) {
		(d).onPropertyChanged($.ig.CloudMadeMapImagery.prototype.keyPropertyName, e.oldValue(), e.newValue());
	}
	,
	parameter: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.CloudMadeMapImagery.prototype.parameterProperty, value);
			return value;
		} else {
			return this.getValue($.ig.CloudMadeMapImagery.prototype.parameterProperty);
		}
	}
	,
	onParameterChanged: function (d, e) {
		(d).onPropertyChanged($.ig.CloudMadeMapImagery.prototype.parameterPropertyName, e.oldValue(), e.newValue());
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.CloudMadeMapImagery.prototype.keyPropertyName:
				this.cloudView().onKeyPropertyChanged();
				this.view().needsRefresh();
				break;
			case $.ig.CloudMadeMapImagery.prototype.parameterPropertyName:
				this.cloudView().onParameterPropertyChanged();
				this.view().needsRefresh();
				break;
		}
		$.ig.GeographicMapImagery.prototype.onPropertyUpdated.call(this, propertyName, oldValue, newValue);
	}
	,
	$type: new $.ig.Type('CloudMadeMapImagery', $.ig.GeographicMapImagery.prototype.$type)
}, true);

$.ig.util.defType('OpenStreetMapImagery', 'GeographicMapImagery', {
	init: function () {
		$.ig.GeographicMapImagery.prototype.init.call(this, new $.ig.OpenStreetMapTileSource());
	},
	$type: new $.ig.Type('OpenStreetMapImagery', $.ig.GeographicMapImagery.prototype.$type)
}, true);

$.ig.util.defType('GeographicHighDensityScatterSeries', 'GeographicMapSeriesHost$1', {
	init: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.init.call(this, $.ig.HighDensityScatterSeries.prototype.$type);
		this.defaultStyleKey($.ig.GeographicHighDensityScatterSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicHighDensityScatterSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapSeriesHost$1.prototype.onViewCreated.call(this, view);
		this.highDensityScatterView(view);
	}
	,
	_highDensityScatterView: null,
	highDensityScatterView: function (value) {
		if (arguments.length === 1) {
			this._highDensityScatterView = value;
			return value;
		} else {
			return this._highDensityScatterView;
		}
	}
	,
	createSeries: function () {
		return new $.ig.HighDensityScatterSeries();
	}
	,
	_highDensityScatterSeries: null,
	highDensityScatterSeries: function (value) {
		if (arguments.length === 1) {
			this._highDensityScatterSeries = value;
			return value;
		} else {
			return this._highDensityScatterSeries;
		}
	}
	,
	latitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathProperty);
		}
	}
	,
	longitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathProperty);
		}
	}
	,
	useBruteForce: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.useBruteForceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.useBruteForceProperty);
		}
	}
	,
	progressiveLoad: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadProperty);
		}
	}
	,
	mouseOverEnabled: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledProperty);
		}
	}
	,
	heatMinimum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumProperty);
		}
	}
	,
	heatMaximum: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumProperty);
		}
	}
	,
	heatMinimumColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorProperty);
		}
	}
	,
	heatMaximumColor: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorProperty);
		}
	}
	,
	pointExtent: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicHighDensityScatterSeries.prototype.pointExtentProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.GeographicHighDensityScatterSeries.prototype.pointExtentProperty));
		}
	}
	,
	progressiveLoadStatusChanged: null,
	__progressiveStatus: 0,
	progressiveStatus: function (value) {
		if (arguments.length === 1) {
			var oldValue = this.__progressiveStatus;
			this.__progressiveStatus = value;
			this.raisePropertyChanged("ProgressiveStatus", oldValue, this.__progressiveStatus);
			return value;
		} else {
			return this.__progressiveStatus;
		}
	}
	,
	onHostedSeriesUpdated: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.onHostedSeriesUpdated.call(this);
		var $t = this.hostedSeries();
		$t.progressiveLoadStatusChanged = $.ig.Delegate.prototype.combine($t.progressiveLoadStatusChanged, this.onHostedSeriesProgressiveLoadStatusChanged.runOn(this));
	}
	,
	onHostedSeriesProgressiveLoadStatusChanged: function (sender, e) {
		this.progressiveStatus(e.currentStatus());
		if (this.progressiveLoadStatusChanged != null) {
			this.progressiveLoadStatusChanged(this, e);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicMapSeriesHost$1.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.highDensityScatterView().onSeriesViewerUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathPropertyName:
				this.highDensityScatterView().onLongitudeMemberPathUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathPropertyName:
				this.highDensityScatterView().onLatitudeMemberPathUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.useBruteForcePropertyName:
				this.highDensityScatterView().onUseBruteForceUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadPropertyName:
				this.highDensityScatterView().onProgressiveLoadUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledPropertyName:
				this.highDensityScatterView().onMouseOverEnabledUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.xAxisPropertyName:
				this.highDensityScatterView().onXAxisUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.yAxisPropertyName:
				this.highDensityScatterView().onYAxisUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumPropertyName:
				this.highDensityScatterView().onHeatMinimumPropertyUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumPropertyName:
				this.highDensityScatterView().onHeatMaximumPropertyUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorPropertyName:
				this.highDensityScatterView().onHeatMinimumColorPropertyUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorPropertyName:
				this.highDensityScatterView().onHeatMaximumColorPropertyUpdated();
				break;
			case $.ig.GeographicHighDensityScatterSeries.prototype.pointExtentPropertyName:
				this.highDensityScatterView().onPointExtentPropertUpdated();
				break;
		}
	}
	,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	$type: new $.ig.Type('GeographicHighDensityScatterSeries', $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize($.ig.HighDensityScatterSeries.prototype.$type))
}, true);

$.ig.util.defType('GeographicPolylineSeries', 'GeographicShapeSeriesBase', {
	init: function () {
		$.ig.GeographicShapeSeriesBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicPolylineSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicPolylineSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicShapeSeriesBase.prototype.onViewCreated.call(this, view);
		this.polylineView(view);
	}
	,
	_polylineView: null,
	polylineView: function (value) {
		if (arguments.length === 1) {
			this._polylineView = value;
			return value;
		} else {
			return this._polylineView;
		}
	}
	,
	shapeStyleSelector: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorProperty);
		}
	}
	,
	shapeStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicPolylineSeries.prototype.shapeStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicPolylineSeries.prototype.shapeStyleProperty);
		}
	}
	,
	createSeries: function () {
		return new $.ig.PolylineSeries();
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorPropertyName:
				this.polylineView().shapeStyleSelectorUpdated();
				break;
			case $.ig.GeographicPolylineSeries.prototype.shapeStylePropertyName:
				this.polylineView().shapeStyleUpdated();
				break;
		}
	}
	,
	$type: new $.ig.Type('GeographicPolylineSeries', $.ig.GeographicShapeSeriesBase.prototype.$type)
}, true);

$.ig.util.defType('GeographicProportionalSymbolSeries', 'GeographicMapSeriesHost$1', {
	init: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.init.call(this, $.ig.BubbleSeries.prototype.$type);
		this.defaultStyleKey($.ig.GeographicProportionalSymbolSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicProportionalSymbolSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapSeriesHost$1.prototype.onViewCreated.call(this, view);
		this.proportionalView(view);
	}
	,
	_proportionalView: null,
	proportionalView: function (value) {
		if (arguments.length === 1) {
			this._proportionalView = value;
			return value;
		} else {
			return this._proportionalView;
		}
	}
	,
	createSeries: function () {
		return new $.ig.BubbleSeries();
	}
	,
	_bubbleSeries: null,
	bubbleSeries: function (value) {
		if (arguments.length === 1) {
			this._bubbleSeries = value;
			return value;
		} else {
			return this._bubbleSeries;
		}
	}
	,
	latitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathProperty);
		}
	}
	,
	longitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathProperty);
		}
	}
	,
	markerType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.markerTypeProperty, $.ig.MarkerType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.markerTypeProperty));
		}
	}
	,
	markerTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.markerTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.markerTemplateProperty);
		}
	}
	,
	markerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.markerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.markerBrushProperty);
		}
	}
	,
	markerOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.markerOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.markerOutlineProperty);
		}
	}
	,
	maximumMarkers: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersProperty));
		}
	}
	,
	radiusMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathProperty);
		}
	}
	,
	radiusScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.radiusScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.radiusScaleProperty);
		}
	}
	,
	labelMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathProperty);
		}
	}
	,
	fillMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathProperty);
		}
	}
	,
	fillScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicProportionalSymbolSeries.prototype.fillScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicProportionalSymbolSeries.prototype.fillScaleProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicMapSeriesHost$1.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.proportionalView().onSeriesViewerUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathPropertyName:
				this.proportionalView().onLongitudeMemberPathUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathPropertyName:
				this.proportionalView().onLatitudeMemberPathUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.markerTypePropertyName:
				this.proportionalView().onMarkerTypeUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.markerTemplatePropertyName:
				this.proportionalView().onMarkerTemplateUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersPropertyName:
				this.proportionalView().onMaximumMarkersUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.xAxisPropertyName:
				this.proportionalView().onXAxisUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.yAxisPropertyName:
				this.proportionalView().onYAxisUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.markerBrushPropertyName:
				this.proportionalView().onMarkerBrushUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.markerOutlinePropertyName:
				this.proportionalView().onMarkerOutlineUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathPropertyName:
				this.proportionalView().radiusMemberPathUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.radiusScalePropertyName:
				this.proportionalView().radiusScaleUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathPropertyName:
				this.proportionalView().labelMemberPathUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathPropertyName:
				this.proportionalView().fillMemberPathUpdated();
				break;
			case $.ig.GeographicProportionalSymbolSeries.prototype.fillScalePropertyName:
				this.proportionalView().fillScaleUpdated();
				break;
		}
	}
	,
	$type: new $.ig.Type('GeographicProportionalSymbolSeries', $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize($.ig.BubbleSeries.prototype.$type))
}, true);

$.ig.util.defType('GeographicXYTriangulatingSeries', 'GeographicMapSeriesHost$1', {
	init: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.init.call(this, $.ig.XYTriangulatingSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicXYTriangulatingSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapSeriesHost$1.prototype.onViewCreated.call(this, view);
		this.geographicXYView(view);
	}
	,
	_geographicXYView: null,
	geographicXYView: function (value) {
		if (arguments.length === 1) {
			this._geographicXYView = value;
			return value;
		} else {
			return this._geographicXYView;
		}
	}
	,
	longitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathProperty);
		}
	}
	,
	latitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathProperty);
		}
	}
	,
	trianglesSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourceProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourceProperty);
		}
	}
	,
	triangleVertexMemberPath1: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1Property, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1Property);
		}
	}
	,
	triangleVertexMemberPath2: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2Property, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2Property);
		}
	}
	,
	triangleVertexMemberPath3: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3Property, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3Property);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicMapSeriesHost$1.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.geographicXYView().onSeriesViewerUpdated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathPropertyName:
				this.geographicXYView().longitudeMemberPathUpdated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathPropertyName:
				this.geographicXYView().latitudeMemberPathUpdated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourcePropertyName:
				this.geographicXYView().trianglesSourceUpdated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1PropertyName:
				this.geographicXYView().triangleVertexMemberPath1Updated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2PropertyName:
				this.geographicXYView().triangleVertexMemberPath2Updated();
				break;
			case $.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3PropertyName:
				this.geographicXYView().triangleVertexMemberPath3Updated();
				break;
		}
	}
	,
	$type: new $.ig.Type('GeographicXYTriangulatingSeries', $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize($.ig.XYTriangulatingSeries.prototype.$type))
}, true);

$.ig.util.defType('GeographicScatterAreaSeries', 'GeographicXYTriangulatingSeries', {
	init: function () {
		$.ig.GeographicXYTriangulatingSeries.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicScatterAreaSeries.prototype.$type);
	},
	colorMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicScatterAreaSeries.prototype.colorMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicScatterAreaSeries.prototype.colorMemberPathProperty);
		}
	}
	,
	__colorScale: null,
	colorScale: function (value) {
		if (arguments.length === 1) {
			var changed = this.__colorScale != value;
			if (changed) {
				var oldValue = this.__colorScale;
				this.__colorScale = value;
				this.raisePropertyChanged($.ig.GeographicScatterAreaSeries.prototype._colorScalePropertyName, oldValue, this.__colorScale);
				if (this.hostedScatterAreaSeries() != null) {
					this.hostedScatterAreaSeries().colorScale(value);
				}
			}
			return value;
		} else {
			return this.__colorScale;
		}
	}
	,
	_hostedScatterAreaSeries: null,
	hostedScatterAreaSeries: function (value) {
		if (arguments.length === 1) {
			this._hostedScatterAreaSeries = value;
			return value;
		} else {
			return this._hostedScatterAreaSeries;
		}
	}
	,
	createSeries: function () {
		this.hostedScatterAreaSeries(new $.ig.ScatterAreaSeries());
		var $t = this.hostedScatterAreaSeries();
		$t.triangulationStatusChanged = $.ig.Delegate.prototype.combine($t.triangulationStatusChanged, this.hostedScatterAreaSeries_TriangulationStatusChanged.runOn(this));
		return this.hostedScatterAreaSeries();
	}
	,
	hostedScatterAreaSeries_TriangulationStatusChanged: function (sender, args) {
		if (this.triangulationStatusChanged != null) {
			this.triangulationStatusChanged(this, args);
		}
	}
	,
	createView: function () {
		return new $.ig.GeographicScatterAreaSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicXYTriangulatingSeries.prototype.onViewCreated.call(this, view);
		this.geographicScatterAreaView(view);
	}
	,
	_geographicScatterAreaView: null,
	geographicScatterAreaView: function (value) {
		if (arguments.length === 1) {
			this._geographicScatterAreaView = value;
			return value;
		} else {
			return this._geographicScatterAreaView;
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicXYTriangulatingSeries.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.GeographicScatterAreaSeries.prototype.colorMemberPathPropertyName:
				this.geographicScatterAreaView().colorMemberPathUpdated();
				break;
			case $.ig.GeographicScatterAreaSeries.prototype._colorScalePropertyName:
				this.geographicScatterAreaView().colorScaleUpdated();
				break;
		}
	}
	,
	triangulationStatusChanged: null,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	$type: new $.ig.Type('GeographicScatterAreaSeries', $.ig.GeographicXYTriangulatingSeries.prototype.$type)
}, true);

$.ig.util.defType('ContourLineSeries', 'XYTriangulatingSeries', {
	init: function () {
		$.ig.XYTriangulatingSeries.prototype.init.call(this);
		this.defaultStyleKey($.ig.ContourLineSeries.prototype.$type);
		this.valueResolver(new $.ig.LinearContourValueResolver());
	},
	_contourLineView: null,
	contourLineView: function (value) {
		if (arguments.length === 1) {
			this._contourLineView = value;
			return value;
		} else {
			return this._contourLineView;
		}
	}
	,
	createView: function () {
		this.contourLineView(new $.ig.ContourLineSeriesView(this));
		return this.contourLineView();
	}
	,
	valueMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ContourLineSeries.prototype.valueMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ContourLineSeries.prototype.valueMemberPathProperty);
		}
	}
	,
	__valueColumn: null,
	valueColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.valueColumn() != value;
			if (changed) {
				var oldValue = this.valueColumn();
				this.__valueColumn = value;
				this.raisePropertyChanged($.ig.ContourLineSeries.prototype._valueColumnPropertyName, oldValue, this.valueColumn());
			}
			return value;
		} else {
			return this.__valueColumn;
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.XYTriangulatingSeries.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				var oldFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue);
				if (oldFastItemsSource != null) {
					oldFastItemsSource.deregisterColumn(this.valueColumn());
					this.valueColumn(null);
				}
				var newFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue);
				if (newFastItemsSource != null) {
					this.valueColumn(this.registerDoubleColumn(this.valueMemberPath()));
				}
				this.renderSeries(false);
				break;
			case $.ig.ContourLineSeries.prototype._valueMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.valueColumn());
					this.valueColumn(this.registerDoubleColumn(this.valueMemberPath()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ContourLineSeries.prototype._valueResolverPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ContourLineSeries.prototype._fillScalePropertyName:
				var oldScale = oldValue;
				var newScale = newValue;
				if (oldScale != null) {
					oldScale.unregisterSeries(this);
				}
				if (newScale != null) {
					newScale.registerSeries(this);
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.Series.prototype.actualBrushPropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.XYTriangulatingSeries.prototype.clearRendering.call(this, wipeClean, view);
		if (wipeClean) {
			(view).clearContours(wipeClean);
		}
	}
	,
	renderSeriesOverride: function (animate) {
		$.ig.XYTriangulatingSeries.prototype.renderSeriesOverride.call(this, animate);
		this.renderWithView(this.view());
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.XYTriangulatingSeries.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		this.renderWithView(view);
	}
	,
	renderWithView: function (view) {
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		var windowRect;
		var viewportRect;
		var contourLineView = view;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var xParams = new $.ig.ScalerParams(1, windowRect, viewportRect, this.xAxis().isInverted());
		xParams._effectiveViewportRect = this.getEffectiveViewport1(view);
		var yParams = new $.ig.ScalerParams(1, windowRect, viewportRect, this.yAxis().isInverted());
		yParams._effectiveViewportRect = this.getEffectiveViewport1(view);
		var xAxis = this.xAxis();
		var yAxis = this.yAxis();
		var trCount = this.triangleVertexColumn1().count();
		var xSource;
		var xArr_ = this.xColumn().asArray();
		xSource = xArr_.slice(0);
		var ySource;
		var yArr_ = this.yColumn().asArray();
		ySource = yArr_.slice(0);
		var valueSource = this.valueColumn().asArray();
		var triangleVertexSource1 = this.triangleVertexColumn1().asArray();
		var triangleVertexSource2 = this.triangleVertexColumn2().asArray();
		var triangleVertexSource3 = this.triangleVertexColumn3().asArray();
		var contourValuesEnum = this.valueResolver().getContourValues(this.valueColumn());
		var contourValues = new $.ig.List$1(Number, 1, contourValuesEnum).toArray();
		xAxis.getScaledValueList(xSource, 0, xSource.length, xParams);
		yAxis.getScaledValueList(ySource, 0, ySource.length, yParams);
		var count = contourValues.length;
		var contours = new Array(count);
		for (var i = 0; i < count; ++i) {
			contours[i] = new $.ig.ContourBuilder();
		}
		var viewportTop = viewportRect.top();
		var viewportLeft = viewportRect.left();
		var viewportRight = viewportRect.right();
		var viewportBottom = viewportRect.bottom();
		var minY;
		var maxY;
		var minX;
		var maxX;
		var xSourceCount = xSource.length;
		for (var ii = 0; ii < trCount; ii++) {
			var v1 = triangleVertexSource1[ii];
			var v2 = triangleVertexSource2[ii];
			var v3 = triangleVertexSource3[ii];
			if ((v1 >= xSourceCount) || (v2 >= xSourceCount) || (v3 >= xSourceCount) || (v1 < 0) || (v2 < 0) || (v3 < 0)) {
				continue;
			}
			var x1 = xSource[v1];
			var y1 = ySource[v1];
			var pt1 = { __x: x1, __y: y1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var x2 = xSource[v2];
			var y2 = ySource[v2];
			var pt2 = { __x: x2, __y: y2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var x3 = xSource[v3];
			var y3 = ySource[v3];
			var pt3 = { __x: x3, __y: y3, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			minY = pt2.__y < pt3.__y ? pt2.__y : pt3.__y;
			minY = pt1.__y < minY ? pt1.__y : minY;
			maxY = pt2.__y > pt3.__y ? pt2.__y : pt3.__y;
			maxY = pt1.__y > maxY ? pt1.__y : maxY;
			minX = pt2.__x < pt3.__x ? pt2.__x : pt3.__x;
			minX = pt1.__x < minX ? pt1.__x : minX;
			maxX = pt2.__x > pt3.__x ? pt2.__x : pt3.__x;
			maxX = pt1.__x > maxX ? pt1.__x : maxX;
			if (minY < viewportBottom && maxY > viewportTop && minX < viewportRight && maxX > viewportLeft) {
				var value0 = valueSource[v1];
				var value1 = valueSource[v2];
				var value2 = valueSource[v3];
				if ($.ig.util.isNaN(value0) || $.ig.util.isNaN(value1) || $.ig.util.isNaN(value2)) {
					continue;
				}
				for (var j = 0; j < count; j++) {
					var contourValue = contourValues[j];
					var contour = contours[j];
					switch ((value0 < contourValue ? 1 : 0) | (value1 < contourValue ? 2 : 0) | (value2 < contourValue ? 4 : 0)) {
						case 0: break;
						case 1:
							contour.addSegment(contour.point(v1, pt1.__x, pt1.__y, value0, v2, pt2.__x, pt2.__y, value1, contourValue), contour.point(v3, pt3.__x, pt3.__y, value2, v1, pt1.__x, pt1.__y, value0, contourValue));
							break;
						case 2:
							contour.addSegment(contour.point(v2, pt2.__x, pt2.__y, value1, v3, pt3.__x, pt3.__y, value2, contourValue), contour.point(v1, pt1.__x, pt1.__y, value0, v2, pt2.__x, pt2.__y, value1, contourValue));
							break;
						case 3:
							contour.addSegment(contour.point(v2, pt2.__x, pt2.__y, value1, v3, pt3.__x, pt3.__y, value2, contourValue), contour.point(v3, pt3.__x, pt3.__y, value2, v1, pt1.__x, pt1.__y, value0, contourValue));
							break;
						case 4:
							contour.addSegment(contour.point(v3, pt3.__x, pt3.__y, value2, v1, pt1.__x, pt1.__y, value0, contourValue), contour.point(v2, pt2.__x, pt2.__y, value1, v3, pt3.__x, pt3.__y, value2, contourValue));
							break;
						case 5:
							contour.addSegment(contour.point(v1, pt1.__x, pt1.__y, value0, v2, pt2.__x, pt2.__y, value1, contourValue), contour.point(v2, pt2.__x, pt2.__y, value1, v3, pt3.__x, pt3.__y, value2, contourValue));
							break;
						case 6:
							contour.addSegment(contour.point(v3, pt3.__x, pt3.__y, value2, v1, pt1.__x, pt1.__y, value0, contourValue), contour.point(v1, pt1.__x, pt1.__y, value0, v2, pt2.__x, pt2.__y, value1, contourValue));
							break;
						case 7: break;
					}
				}
			}
		}
		var clipper = new $.ig.Clipper(0, $.ig.RectUtil.prototype.inflate(viewportRect, 2), false);
		var allPoints = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type)), 0);
		for (var i1 = 0; i1 < count; ++i1) {
			var contour1 = contours[i1];
			var rings = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 0);
			var polylines = contour1.getPolylines();
			for (var j1 = 0; j1 < polylines.count(); j1++) {
				var indices = polylines.__inner[j1];
				var pointCollection = new $.ig.List$1($.ig.Point.prototype.$type, 0);
				clipper.target(pointCollection);
				var en = indices.getEnumerator();
				while (en.moveNext()) {
					var index = en.current();
					clipper.add({ __x: contour1.x().item(index), __y: contour1.y().item(index), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
				}
				clipper.target(null);
				rings.add(pointCollection);
			}
			allPoints.add(rings);
		}
		contourLineView.applyVisuals(allPoints, contourValues);
	}
	,
	fillScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ContourLineSeries.prototype.fillScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ContourLineSeries.prototype.fillScaleProperty);
		}
	}
	,
	__valueResolver: null,
	valueResolver: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.valueResolver();
			if (changed) {
				if (this.__valueResolver != null) {
					var $t = this.__valueResolver;
					$t.updated = $.ig.Delegate.prototype.remove($t.updated, this.valueResolverEvent.runOn(this));
				}
				var oldValue = this.__valueResolver;
				this.__valueResolver = value;
				if (this.__valueResolver != null) {
					var $t1 = this.__valueResolver;
					$t1.updated = $.ig.Delegate.prototype.combine($t1.updated, this.valueResolverEvent.runOn(this));
				}
				this.raisePropertyChanged($.ig.ContourLineSeries.prototype._valueResolverPropertyName, oldValue, value);
				this.renderSeries(false);
			}
			return value;
		} else {
			return this.__valueResolver;
		}
	}
	,
	valueResolverEvent: function (sender, e) {
		this.renderSeries(false);
	}
	,
	validateSeries: function (viewportRect, windowRect, view) {
		return $.ig.XYTriangulatingSeries.prototype.validateSeries.call(this, viewportRect, windowRect, view) && this.valueResolver() != null && this.xColumn() != null && this.yColumn() != null && this.triangleVertexColumn1() != null && this.triangleVertexColumn2() != null && this.triangleVertexColumn3() != null && this.xColumn().count() > 0 && this.yColumn().count() > 0 && this.triangleVertexColumn1().count() > 0 && this.triangleVertexColumn2().count() > 0 && this.triangleVertexColumn3().count() > 0;
	}
	,
	$type: new $.ig.Type('ContourLineSeries', $.ig.XYTriangulatingSeries.prototype.$type)
}, true);

$.ig.util.defType('PolylineBuilder', 'Object', {
	init: function () {
		this._byBegin = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.List$1.prototype.$type.specialize($.ig.Number.prototype.$type), 0);
		this._byEnd = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.List$1.prototype.$type.specialize($.ig.Number.prototype.$type), 0);
		$.ig.Object.prototype.init.call(this);
	},
	clear: function () {
		this._byBegin.clear();
		this._byEnd.clear();
	}
	,
	getPolylines: function () {
		var ret = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Number.prototype.$type), 0);
		var en = this._byBegin.values().getEnumerator();
		while (en.moveNext()) {
			var val = en.current();
			ret.add(val);
		}
		return ret;
	}
	,
	addSegment: function (begin, end) {
		var before = null;
		var after = null;
		var $ret = this._byEnd.tryGetValue(begin, before);
		before = $ret.p1;
		var $ret1 = this._byBegin.tryGetValue(end, after);
		after = $ret1.p1;
		if (before == null && after == null) {
			var p2 = new $.ig.List$1($.ig.Number.prototype.$type, 0);
			p2.add(begin);
			p2.add(end);
			this._byBegin.add(begin, p2);
			this._byEnd.add(end, p2);
		}
		if (before == null && after != null) {
			this._byBegin.remove(end);
			after.insert(0, begin);
			this._byBegin.add(begin, after);
		}
		if (before != null && after == null) {
			this._byEnd.remove(begin);
			before.add(end);
			this._byEnd.add(end, before);
		}
		if (before != null && after != null) {
			if (before == after) {
				before.add(end);
				this._byEnd.remove(begin);
			} else {
				this._byBegin.remove(after.__inner[0]);
				this._byEnd.remove(after.__inner[after.count() - 1]);
				this._byEnd.remove(before.__inner[before.count() - 1]);
				before.addRange(after);
				this._byEnd.add(before.__inner[before.count() - 1], before);
			}
		}
	}
	,
	_byBegin: null,
	_byEnd: null,
	$type: new $.ig.Type('PolylineBuilder', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ContourBuilder', 'PolylineBuilder', {
	init: function () {
		this._dictionary = new $.ig.Dictionary$2($.ig.Number.prototype.$type, $.ig.Number.prototype.$type, 0);
		this._x = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		this._y = new $.ig.List$1($.ig.Number.prototype.$type, 0);
		$.ig.PolylineBuilder.prototype.init.call(this);
	},
	clear: function () {
		$.ig.PolylineBuilder.prototype.clear.call(this);
		this._dictionary.clear();
		this._x.clear();
		this._y.clear();
	}
	,
	x: function () {
		return this._x;
	}
	,
	y: function () {
		return this._y;
	}
	,
	point: function (b, xb, yb, zb, e, xe, ye, ze, z) {
		var $self = this;
		var hash = Math.min(b, e) + $.ig.ContourBuilder.prototype._hASH_CONSTANT * Math.max(b, e);
		var index = -1;
		if (!(function () { var $ret = $self._dictionary.tryGetValue(hash, index); index = $ret.p1; return $ret.ret; }())) {
			index = this._x.count();
			var p = (z - zb) / (ze - zb);
			this._dictionary.add(hash, index);
			this._x.add((xb + p * (xe - xb)));
			this._y.add((yb + p * (ye - yb)));
		}
		return index;
	}
	,
	_dictionary: null,
	_x: null,
	_y: null,
	$type: new $.ig.Type('ContourBuilder', $.ig.PolylineBuilder.prototype.$type)
}, true);

$.ig.util.defType('ContourValueResolver', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	getContourValues: function (valueColumn) {
	}
	,
	propertyUpdated: function (propertyName, oldValue, newValue) {
		if (this.updated != null) {
			this.updated(this, $.ig.EventArgs.prototype.empty);
		}
	}
	,
	updated: null,
	$type: new $.ig.Type('ContourValueResolver', $.ig.DependencyObject.prototype.$type)
}, true);

$.ig.util.defType('GeographicContourLineSeries', 'GeographicXYTriangulatingSeries', {
	init: function () {
		$.ig.GeographicXYTriangulatingSeries.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicContourLineSeries.prototype.$type);
	},
	onHostedSeriesUpdated: function () {
		var $self = this;
		$.ig.GeographicXYTriangulatingSeries.prototype.onHostedSeriesUpdated.call(this);
		this.hostedSeries().setBinding($.ig.ContourLineSeries.prototype.valueMemberPathProperty, (function () {
			var $ret = new $.ig.Binding(1, $.ig.GeographicContourLineSeries.prototype._valueMemberPathPropertyName);
			$ret.source($self);
			return $ret;
		}()));
		this.hostedSeries().setBinding($.ig.ContourLineSeries.prototype.fillScaleProperty, (function () {
			var $ret = new $.ig.Binding(1, $.ig.GeographicContourLineSeries.prototype._fillScalePropertyName);
			$ret.source($self);
			return $ret;
		}()));
	}
	,
	_hostedContourLineSeries: null,
	hostedContourLineSeries: function (value) {
		if (arguments.length === 1) {
			this._hostedContourLineSeries = value;
			return value;
		} else {
			return this._hostedContourLineSeries;
		}
	}
	,
	createSeries: function () {
		this.hostedContourLineSeries(new $.ig.ContourLineSeries());
		var $t = this.hostedContourLineSeries();
		$t.triangulationStatusChanged = $.ig.Delegate.prototype.combine($t.triangulationStatusChanged, this.hostedContourLineSeries_TriangulationStatusChanged.runOn(this));
		return this.hostedContourLineSeries();
	}
	,
	hostedContourLineSeries_TriangulationStatusChanged: function (sender, args) {
		if (this.triangulationStatusChanged != null) {
			this.triangulationStatusChanged(this, args);
		}
	}
	,
	valueMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicContourLineSeries.prototype.valueMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicContourLineSeries.prototype.valueMemberPathProperty);
		}
	}
	,
	fillScale: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicContourLineSeries.prototype.fillScaleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicContourLineSeries.prototype.fillScaleProperty);
		}
	}
	,
	createView: function () {
		return new $.ig.GeographicContourLineSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicXYTriangulatingSeries.prototype.onViewCreated.call(this, view);
		this.geographicContourLineSeriesView(view);
	}
	,
	_geographicContourLineSeriesView: null,
	geographicContourLineSeriesView: function (value) {
		if (arguments.length === 1) {
			this._geographicContourLineSeriesView = value;
			return value;
		} else {
			return this._geographicContourLineSeriesView;
		}
	}
	,
	valueResolver: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicContourLineSeries.prototype.valueResolverProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicContourLineSeries.prototype.valueResolverProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicXYTriangulatingSeries.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.GeographicContourLineSeries.prototype._fillScalePropertyName:
				this.geographicContourLineSeriesView().fillScaleUpdated();
				break;
			case $.ig.GeographicContourLineSeries.prototype._valueMemberPathPropertyName:
				this.geographicContourLineSeriesView().valueMemberPathUpdated();
				break;
			case $.ig.GeographicContourLineSeries.prototype._valueResolverPropertyName:
				this.hostedContourLineSeries().valueResolver(this.valueResolver());
				break;
			case $.ig.Series.prototype.thicknessPropertyName:
				this.hostedContourLineSeries().thickness(this.thickness());
				break;
			case $.ig.Series.prototype.dashArrayPropertyName:
				this.hostedContourLineSeries().dashArray(this.dashArray());
				break;
			case $.ig.Series.prototype.dashCapPropertyName:
				this.hostedContourLineSeries().dashCap(this.dashCap());
				break;
			case $.ig.Series.prototype.miterLimitPropertyName:
				this.hostedContourLineSeries().miterLimit(this.miterLimit());
				break;
		}
	}
	,
	triangulationStatusChanged: null,
	$type: new $.ig.Type('GeographicContourLineSeries', $.ig.GeographicXYTriangulatingSeries.prototype.$type)
}, true);

$.ig.util.defType('GeographicShapeSeries', 'GeographicShapeSeriesBase', {
	init: function () {
		$.ig.GeographicShapeSeriesBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.GeographicShapeSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicShapeSeriesView(this);
	}
	,
	_shapeView: null,
	shapeView: function (value) {
		if (arguments.length === 1) {
			this._shapeView = value;
			return value;
		} else {
			return this._shapeView;
		}
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicShapeSeriesBase.prototype.onViewCreated.call(this, view);
		this.shapeView(view);
	}
	,
	createSeries: function () {
		return new $.ig.ShapeSeries();
	}
	,
	shapeStyleSelector: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.shapeStyleSelectorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.shapeStyleSelectorProperty);
		}
	}
	,
	shapeStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.shapeStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.shapeStyleProperty);
		}
	}
	,
	markerType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerTypeProperty, $.ig.MarkerType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.GeographicShapeSeries.prototype.markerTypeProperty));
		}
	}
	,
	markerTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.markerTemplateProperty);
		}
	}
	,
	markerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.markerBrushProperty);
		}
	}
	,
	markerOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.markerOutlineProperty);
		}
	}
	,
	markerStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicShapeSeries.prototype.markerStyleProperty);
		}
	}
	,
	markerCollisionAvoidance: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicShapeSeries.prototype.markerCollisionAvoidanceProperty, $.ig.CollisionAvoidanceType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.GeographicShapeSeries.prototype.markerCollisionAvoidanceProperty));
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.GeographicShapeSeries.prototype.markerTemplatePropertyName:
				this.shapeView().markerTemplatePropertyUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.markerCollisionAvoidancePropertyName:
				this.shapeView().markerCollisionAvoidanceUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.markerTypePropertyName:
				this.shapeView().markerTypeUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.markerBrushPropertyName:
				this.shapeView().markerBrushUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.markerOutlinePropertyName:
				this.shapeView().markerOutlineUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.markerStylePropertyName:
				this.shapeView().markerStyleUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.shapeStyleSelectorPropertyName:
				this.shapeView().shapeStyleSelectorUpdated();
				break;
			case $.ig.GeographicShapeSeries.prototype.shapeStylePropertyName:
				this.shapeView().shapeStyleUpdated();
				break;
		}
	}
	,
	$type: new $.ig.Type('GeographicShapeSeries', $.ig.GeographicShapeSeriesBase.prototype.$type)
}, true);

$.ig.util.defType('LinearContourValueResolver', 'ContourValueResolver', {
	init: function () {
		$.ig.ContourValueResolver.prototype.init.call(this);
	},
	valueCount: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.LinearContourValueResolver.prototype.valueCountProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.LinearContourValueResolver.prototype.valueCountProperty));
		}
	}
	,
	getContourValues: function (valueColumn) {
		var d__ = new $.ig.LinearContourValueResolver___GetContourValues__IteratorClass(-2);
		d__.__4__this = this;
		d__.__3__valueColumn = valueColumn;
		return d__;
	}
	,
	$type: new $.ig.Type('LinearContourValueResolver', $.ig.ContourValueResolver.prototype.$type)
}, true);

$.ig.util.defType('PolylineSeries', 'ShapeSeriesBase', {
	init: function () {
		$.ig.ShapeSeriesBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.PolylineSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.PolylineSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.ShapeSeriesBase.prototype.onViewCreated.call(this, view);
		this.polylineView(view);
	}
	,
	_polylineView: null,
	polylineView: function (value) {
		if (arguments.length === 1) {
			this._polylineView = value;
			return value;
		} else {
			return this._polylineView;
		}
	}
	,
	isClosed: function () {
		return false;
	}
	,
	shapeStyleSelector: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.PolylineSeries.prototype.shapeStyleSelectorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.PolylineSeries.prototype.shapeStyleSelectorProperty);
		}
	}
	,
	shapeStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.PolylineSeries.prototype.shapeStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.PolylineSeries.prototype.shapeStyleProperty);
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.ShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.PolylineSeries.prototype._shapeStylePropertyName:
				this.polylineView().shapeStyleChanged(this.shapeStyle());
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.PolylineSeries.prototype._shapeStyleSelectorPropertyName:
				this.polylineView().shapeStyleSelectorChanged(this.shapeStyleSelector());
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	renderWithView: function (view) {
		var shapeView = view;
		shapeView.shapeStyleChanged(this.shapeStyle());
		shapeView.shapeStyleSelectorChanged(this.shapeStyleSelector());
		$.ig.ShapeSeriesBase.prototype.renderWithView.call(this, view);
	}
	,
	$type: new $.ig.Type('PolylineSeries', $.ig.ShapeSeriesBase.prototype.$type)
}, true);

$.ig.util.defType('FlattenedShape', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_shape: null,
	shape: function (value) {
		if (arguments.length === 1) {
			this._shape = value;
			return value;
		} else {
			return this._shape;
		}
	}
	,
	_bounds: null,
	bounds: function (value) {
		if (arguments.length === 1) {
			this._bounds = value;
			return value;
		} else {
			return this._bounds;
		}
	}
	,
	_fullBounds: null,
	fullBounds: function (value) {
		if (arguments.length === 1) {
			this._fullBounds = value;
			return value;
		} else {
			return this._fullBounds;
		}
	}
	,
	$type: new $.ig.Type('FlattenedShape', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ScatterAreaSeries', 'XYTriangulatingSeries', {
	init: function () {
		$.ig.XYTriangulatingSeries.prototype.init.call(this);
		this.defaultStyleKey($.ig.ScatterAreaSeries.prototype.$type);
	},
	__colorScale: null,
	colorScale: function (value) {
		if (arguments.length === 1) {
			var changed = this.__colorScale != value;
			if (changed) {
				var oldValue = this.__colorScale;
				if (this.__colorScale != null) {
					var $t = this.__colorScale;
					$t.propertyChanged = $.ig.Delegate.prototype.remove($t.propertyChanged, this.colorScale_PropertyChanged.runOn(this));
				}
				this.__colorScale = value;
				if (this.__colorScale != null) {
					var $t1 = this.__colorScale;
					$t1.propertyChanged = $.ig.Delegate.prototype.combine($t1.propertyChanged, this.colorScale_PropertyChanged.runOn(this));
				}
				this.raisePropertyChanged($.ig.ScatterAreaSeries.prototype._colorScalePropertyName, oldValue, this.__colorScale);
			}
			return value;
		} else {
			return this.__colorScale;
		}
	}
	,
	colorScale_PropertyChanged: function (sender, e) {
		this.renderSeries(false);
	}
	,
	colorMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ScatterAreaSeries.prototype.colorMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ScatterAreaSeries.prototype.colorMemberPathProperty);
		}
	}
	,
	__colorColumn: null,
	colorColumn: function (value) {
		if (arguments.length === 1) {
			var changed = this.colorColumn() != value;
			if (changed) {
				var oldValue = this.colorColumn();
				this.__colorColumn = value;
				this.raisePropertyChanged($.ig.ScatterAreaSeries.prototype._colorColumnPropertyName, oldValue, this.colorColumn());
			}
			return value;
		} else {
			return this.__colorColumn;
		}
	}
	,
	useDeferredMouseEnterAndLeave: function (value) {
		if (arguments.length === 1) {
			$.ig.Series.prototype.useDeferredMouseEnterAndLeave.call(this, value);
			return value;
		} else {
			return true;
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.XYTriangulatingSeries.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.fastItemsSourcePropertyName:
				var oldFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, oldValue);
				if (oldFastItemsSource != null) {
					oldFastItemsSource.deregisterColumn(this.colorColumn());
					this.colorColumn(null);
				}
				var newFastItemsSource = $.ig.util.cast($.ig.IFastItemsSource.prototype.$type, newValue);
				if (newFastItemsSource != null) {
					this.colorColumn(this.registerDoubleColumn(this.colorMemberPath()));
				}
				this.renderSeries(false);
				break;
			case $.ig.ScatterAreaSeries.prototype._colorMemberPathPropertyName:
				if (this.fastItemsSource() != null) {
					this.fastItemsSource().deregisterColumn(this.colorColumn());
					this.colorColumn(this.registerDoubleColumn(this.colorMemberPath()));
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ScatterAreaSeries.prototype._colorScalePropertyName:
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	renderSeriesOverride: function (animate) {
		$.ig.XYTriangulatingSeries.prototype.renderSeriesOverride.call(this, animate);
		this.renderWithView(this.view());
	}
	,
	renderAlternateView: function (viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio) {
		$.ig.XYTriangulatingSeries.prototype.renderAlternateView.call(this, viewportRect, windowRect, surface, viewIdentifier, effectiveScalingRatio);
		var view = this.alternateViews().item(viewIdentifier);
		view.prepAltSurface(surface);
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		this.renderWithView(view);
	}
	,
	renderWithView: function (view) {
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		var windowRect;
		var viewportRect;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var scatterAreaSeriesView = view;
		var triangleDataBitmap = null;
		var tri = null;
		var xaxis = this.xAxis();
		var yaxis = this.yAxis();
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		var xParams = new $.ig.ScalerParams(1, windowRect, viewportRect, xaxis.isInverted());
		xParams._effectiveViewportRect = effectiveViewportRect;
		var yParams = new $.ig.ScalerParams(1, windowRect, viewportRect, yaxis.isInverted());
		yParams._effectiveViewportRect = effectiveViewportRect;
		tri = this.createBitmap(view, xParams, yParams);
		if (tri != null) {
			scatterAreaSeriesView.closeRasterizer(tri);
		} else {
			scatterAreaSeriesView.setBitmap(triangleDataBitmap);
		}
	}
	,
	assertMouseOver: function (view) {
		var viewportRect;
		var windowRect;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var numPixels = $.ig.truncate(Math.round(viewportRect.width())) * $.ig.truncate(Math.round(viewportRect.height()));
		var itemIndexes = view.itemIndexes();
		if (itemIndexes == null || itemIndexes.length != numPixels) {
			itemIndexes = new Array(numPixels);
			for (var i = 0; i < numPixels; i++) {
				itemIndexes[i] = 0;
			}
		} else {
			for (var i1 = 0; i1 < numPixels; i1++) {
				itemIndexes[i1] = 0;
			}
		}
		view.itemIndexes(itemIndexes);
	}
	,
	getItem: function (world) {
		var imageWidth = $.ig.truncate(Math.round(this.viewport().width()));
		var imageHeight = $.ig.truncate(Math.round(this.viewport().height()));
		var itemIndexes = (this.view()).itemIndexes();
		if (itemIndexes == null || itemIndexes.length != (imageWidth * imageHeight) || this.triangleVertexColumn1() == null || this.triangleVertexColumn1().count() < 1 || this.triangleVertexColumn2() == null || this.triangleVertexColumn2().count() < 1 || this.triangleVertexColumn3() == null || this.triangleVertexColumn3().count() < 1) {
			return null;
		}
		var windowRect = this.seriesViewer().windowRect();
		var windowX = (world.__x - windowRect.left()) / windowRect.width();
		var windowY = (world.__y - windowRect.top()) / windowRect.height();
		var pixelX = $.ig.truncate(Math.round(imageWidth * windowX));
		var pixelY = $.ig.truncate(Math.round(imageHeight * windowY));
		var index = (imageWidth * pixelY) + pixelX;
		if (index < 0 || index > itemIndexes.length - 1) {
			return null;
		}
		var itemIndex = itemIndexes[index] - 1;
		if (itemIndex < 0 || itemIndex > this.triangleVertexColumn1().count()) {
			return null;
		}
		var xSourceCount = this.xSource().length;
		var v1 = this.triangleVertexColumn1().item(itemIndex);
		var v2 = this.triangleVertexColumn2().item(itemIndex);
		var v3 = this.triangleVertexColumn3().item(itemIndex);
		if ((v1 >= xSourceCount) || (v2 >= xSourceCount) || (v3 >= xSourceCount)) {
			return null;
		}
		var x1 = (this.xSource()[v1] - this.viewport().left()) / this.viewport().width();
		var y1 = (this.ySource()[v1] - this.viewport().top()) / this.viewport().height();
		var x2 = (this.xSource()[v2] - this.viewport().left()) / this.viewport().width();
		var y2 = (this.ySource()[v2] - this.viewport().top()) / this.viewport().height();
		var x3 = (this.xSource()[v3] - this.viewport().left()) / this.viewport().width();
		var y3 = (this.ySource()[v3] - this.viewport().top()) / this.viewport().height();
		var d1 = Math.pow(x1 - windowX, 2) + Math.pow(y1 - windowY, 2);
		var d2 = Math.pow(x2 - windowX, 2) + Math.pow(y2 - windowY, 2);
		var d3 = Math.pow(x3 - windowX, 2) + Math.pow(y3 - windowY, 2);
		if (d1 < d2 && d1 < d3) {
			return this.fastItemsSource().item(v1);
		}
		if (d2 < d1 && d2 < d3) {
			return this.fastItemsSource().item(v2);
		}
		if (d3 < d1 && d3 < d2) {
			return this.fastItemsSource().item(v3);
		}
		return this.fastItemsSource().item(v1);
	}
	,
	_xSource: null,
	xSource: function (value) {
		if (arguments.length === 1) {
			this._xSource = value;
			return value;
		} else {
			return this._xSource;
		}
	}
	,
	_ySource: null,
	ySource: function (value) {
		if (arguments.length === 1) {
			this._ySource = value;
			return value;
		} else {
			return this._ySource;
		}
	}
	,
	createBitmap: function (view, xParams, yParams) {
		var windowRect;
		var viewportRect;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var scatterAreaSeriesView = view;
		var xAxis = this.xAxis();
		var yAxis = this.yAxis();
		var count = this.triangleVertexColumn1().count();
		var xSource;
		var xArr_ = this.xColumn().asArray();
		xSource = xArr_.slice(0);
		var ySource;
		var yArr_ = this.yColumn().asArray();
		ySource = yArr_.slice(0);
		var colorSource = this.colorColumn().asArray();
		var triangleVertexSource1 = this.triangleVertexColumn1().asArray();
		var triangleVertexSource2 = this.triangleVertexColumn2().asArray();
		var triangleVertexSource3 = this.triangleVertexColumn3().asArray();
		this.xSource(xSource);
		this.ySource(ySource);
		xAxis.getScaledValueList(xSource, 0, xSource.length, xParams);
		yAxis.getScaledValueList(ySource, 0, ySource.length, yParams);
		var actualColorScale = this.colorScale();
		if (actualColorScale == null) {
			actualColorScale = (function () {
				var $ret = new $.ig.CustomPaletteColorScale();
				$ret.palette(new $.ig.ObservableCollection$1($.ig.Color.prototype.$type, 1, [ $.ig.Color.prototype.fromArgb(0, 0, 0, 0), $.ig.Color.prototype.fromArgb(1, 0, 0, 0) ]));
				$ret.interpolationMode($.ig.ColorScaleInterpolationMode.prototype.interpolateHSV);
				return $ret;
			}());
		}
		var triceratops = scatterAreaSeriesView.getRasterizer(xParams._viewportRect, actualColorScale, this.colorColumn());
		var viewportTop = viewportRect.top();
		var viewportLeft = viewportRect.left();
		var viewportRight = viewportRect.right();
		var viewportBottom = viewportRect.bottom();
		var minY;
		var maxY;
		var minX;
		var maxX;
		this.assertMouseOver(scatterAreaSeriesView);
		var itemIndexes = scatterAreaSeriesView.itemIndexes();
		var xSourceCount = xSource.length;
		for (var ii = 0; ii < count; ii++) {
			var v1 = triangleVertexSource1[ii];
			var v2 = triangleVertexSource2[ii];
			var v3 = triangleVertexSource3[ii];
			if ((v1 >= xSourceCount) || (v2 >= xSourceCount) || (v3 >= xSourceCount) || (v1 < 0) || (v2 < 0) || (v3 < 0)) {
				continue;
			}
			var x1 = xSource[v1];
			var y1 = ySource[v1];
			var pt1 = { __x: x1, __y: y1, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var x2 = xSource[v2];
			var y2 = ySource[v2];
			var pt2 = { __x: x2, __y: y2, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			var x3 = xSource[v3];
			var y3 = ySource[v3];
			var pt3 = { __x: x3, __y: y3, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
			minY = pt2.__y < pt3.__y ? pt2.__y : pt3.__y;
			minY = pt1.__y < minY ? pt1.__y : minY;
			maxY = pt2.__y > pt3.__y ? pt2.__y : pt3.__y;
			maxY = pt1.__y > maxY ? pt1.__y : maxY;
			minX = pt2.__x < pt3.__x ? pt2.__x : pt3.__x;
			minX = pt1.__x < minX ? pt1.__x : minX;
			maxX = pt2.__x > pt3.__x ? pt2.__x : pt3.__x;
			maxX = pt1.__x > maxX ? pt1.__x : maxX;
			if (minY < viewportBottom && maxY > viewportTop && minX < viewportRight && maxX > viewportLeft) {
				var value0 = colorSource[v1];
				var value1 = colorSource[v2];
				var value2 = colorSource[v3];
				triceratops.rasterizeTriangle(ii, itemIndexes, pt1, pt2, pt3, value0, value1, value2);
			}
		}
		return triceratops;
	}
	,
	clearRendering: function (wipeClean, view) {
		$.ig.XYTriangulatingSeries.prototype.clearRendering.call(this, wipeClean, view);
		var scatterView = view;
		scatterView.clearBitmap();
	}
	,
	createView: function () {
		return new $.ig.ScatterAreaSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.XYTriangulatingSeries.prototype.onViewCreated.call(this, view);
		this.scatterAreaSeriesView($.ig.util.cast($.ig.ScatterAreaSeriesView.prototype.$type, view));
	}
	,
	_scatterAreaSeriesView: null,
	scatterAreaSeriesView: function (value) {
		if (arguments.length === 1) {
			this._scatterAreaSeriesView = value;
			return value;
		} else {
			return this._scatterAreaSeriesView;
		}
	}
	,
	$type: new $.ig.Type('ScatterAreaSeries', $.ig.XYTriangulatingSeries.prototype.$type)
}, true);

$.ig.util.defType('TriangleRasterizer', 'Object', {
	init: function (imageData, colorScale, defaultMinimum, defaultMaximum, colorColumn, pixelWidth, pixelHeight) {
		this.__pixelWidth = 0;
		this.__pixelHeight = 0;
		this.__colorScale = null;
		this.__colorColumn = null;
		$.ig.Object.prototype.init.call(this);
		if (imageData == null || colorScale == null || colorColumn == null) {
			throw new $.ig.Error(0);
		}
		this._pixelData = imageData;
		this.colorScale(colorScale);
		this.colorColumn(colorColumn);
		this.pixelWidth(pixelWidth);
		this.pixelHeight(pixelHeight);
		this.__defaultMinimum = defaultMinimum;
		this.__defaultMaximum = defaultMaximum;
	},
	_pixelData: null,
	__pixelWidth: 0,
	pixelWidth: function (value) {
		if (arguments.length === 1) {
			this.__pixelWidth = value;
			return value;
		} else {
			return this.__pixelWidth;
		}
	}
	,
	__pixelHeight: 0,
	pixelHeight: function (value) {
		if (arguments.length === 1) {
			this.__pixelHeight = value;
			return value;
		} else {
			return this.__pixelHeight;
		}
	}
	,
	__defaultMinimum: 0,
	defaultMinimum: function (value) {
		if (arguments.length === 1) {
			this.__defaultMinimum = value;
			return value;
		} else {
			return this.__defaultMinimum;
		}
	}
	,
	__defaultMaximum: 0,
	defaultMaximum: function (value) {
		if (arguments.length === 1) {
			this.__defaultMaximum = value;
			return value;
		} else {
			return this.__defaultMaximum;
		}
	}
	,
	__colorScale: null,
	colorScale: function (value) {
		if (arguments.length === 1) {
			this.__colorScale = value;
			return value;
		} else {
			return this.__colorScale;
		}
	}
	,
	__colorColumn: null,
	colorColumn: function (value) {
		if (arguments.length === 1) {
			this.__colorColumn = value;
			return value;
		} else {
			return this.__colorColumn;
		}
	}
	,
	rasterizeTriangle: function (index, indexBuffer, p0, p1, p2, value0, value1, value2) {
		var pixelWidth = this.__pixelWidth;
		var pixelHeight = this.__pixelHeight;
		var pTemp;
		var valueTemp;
		if (p1.__y > p2.__y) {
			pTemp = p1;
			p1 = p2;
			p2 = pTemp;
			valueTemp = value1;
			value1 = value2;
			value2 = valueTemp;
		}
		if (p0.__y > p2.__y) {
			pTemp = p0;
			p0 = p2;
			p2 = pTemp;
			valueTemp = value0;
			value0 = value2;
			value2 = valueTemp;
		}
		if (p0.__y > p1.__y) {
			pTemp = p0;
			p0 = p1;
			p1 = pTemp;
			valueTemp = value0;
			value0 = value1;
			value1 = valueTemp;
		}
		var Y0 = $.ig.truncate(Math.round(p0.__y));
		var Y1 = $.ig.truncate(Math.round(p1.__y));
		var Y2 = $.ig.truncate(Math.round(p2.__y));
		if (Y2 == Y0) {
			return;
		}
		if ($.ig.util.isNaN(value0) || $.ig.util.isNaN(value1) || $.ig.util.isNaN(value2)) {
			return;
		}
		var clockwise = (p1.__x - p0.__x) * (p2.__y - p0.__y) - (p2.__x - p0.__x) * (p1.__y - p0.__y) >= 0;
		var yStart1 = Math.max(0, Y0);
		var yEnd1 = Math.min(pixelHeight - 1, Y1);
		for (var y = yStart1; y < yEnd1; ++y) {
			var p01 = (y - Y0) / (Y1 - Y0);
			var x01 = p0.__x + p01 * (p1.__x - p0.__x);
			var v01 = value0 + p01 * (value1 - value0);
			var p02 = (y - Y0) / (Y2 - Y0);
			var x02 = p0.__x + p02 * (p2.__x - p0.__x);
			var v02 = value0 + p02 * (value2 - value0);
			if (clockwise) {
				this.rasterizeLine(index, indexBuffer, y, $.ig.truncate(Math.floor(x02)), $.ig.truncate(Math.ceil(x01)), v02, v01);
			} else {
				this.rasterizeLine(index, indexBuffer, y, $.ig.truncate(Math.floor(x01)), $.ig.truncate(Math.ceil(x02)), v01, v02);
			}
		}
		{
			var yStart2 = Math.min(pixelHeight - 1, Math.max(0, Y1));
			var yEnd2 = Math.min(pixelHeight - 1, Y2);
			var p12 = 0;
			var x12 = p1.__x + p12 * (p2.__x - p1.__x);
			var v12 = value1 + p12 * (value2 - value1);
			var y1 = yStart2;
			var yRange02 = (Y2 - Y0);
			var p021 = (y1 - Y0) / yRange02;
			var x021 = p0.__x + p021 * (p2.__x - p0.__x);
			var v021 = value0 + p021 * (value2 - value0);
			do {
				if (clockwise) {
					this.rasterizeLine(index, indexBuffer, y1, $.ig.truncate(Math.floor(x021)), $.ig.truncate(Math.ceil(x12)), v021, v12);
				} else {
					this.rasterizeLine(index, indexBuffer, y1, $.ig.truncate(Math.floor(x12)), $.ig.truncate(Math.ceil(x021)), v12, v021);
				}
				++y1;
				p12 = (y1 - Y1) / (Y2 - Y1);
				x12 = p1.__x + p12 * (p2.__x - p1.__x);
				v12 = value1 + p12 * (value2 - value1);
				p021 = (y1 - Y0) / yRange02;
				x021 = p0.__x + p021 * (p2.__x - p0.__x);
				v021 = value0 + p021 * (value2 - value0);
			} while (y1 < yEnd2);
		}
	}
	,
	rasterizeLine: function (index, indexBuffer, y, x0, x1, value0, value1) {
		var pixelWidth = this.__pixelWidth;
		var xStart = x0 > 0 ? x0 : 0;
		var xEnd = x1 < pixelWidth - 1 ? x1 : pixelWidth - 1;
		var linePosition = y * pixelWidth * 4 + (xStart * 4);
		var valueRange = value1 - value0;
		var xRange = 1 / (x1 - x0);
		var colorColumn = this.__colorColumn;
		var defaultMinimum = this.__defaultMinimum;
		var defaultMaximum = this.__defaultMaximum;
		var indexPosition = y * pixelWidth;
		for (var xx = xStart; xx <= xEnd; xx++) {
			var value = value0 + valueRange * (xx - x0) * xRange;
			var color_ = this.__colorScale.getColor(value, defaultMinimum, defaultMaximum, colorColumn);
			this._pixelData[linePosition] = color_.__r;
			this._pixelData[linePosition + 1] = color_.__g;
			this._pixelData[linePosition + 2] = color_.__b;
			this._pixelData[linePosition + 3] = color_.__a;
			indexBuffer[indexPosition + xx] = index + 1;
			linePosition += 4;
		}
	}
	,
	postRasterize: function () {
	}
	,
	$type: new $.ig.Type('TriangleRasterizer', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('TriangleRasterizer_PointAndValue', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
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
	$type: new $.ig.Type('TriangleRasterizer_PointAndValue', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ShapeSeries', 'ShapeSeriesBase', {
	init: function () {
		this._xParams = new $.ig.ScalerParams(1, $.ig.ShapeSeries.prototype._unitRect, $.ig.ShapeSeries.prototype._unitRect, false);
		this._yParams = new $.ig.ScalerParams(1, $.ig.ShapeSeries.prototype._unitRect, $.ig.ShapeSeries.prototype._unitRect, false);
		$.ig.ShapeSeriesBase.prototype.init.call(this);
		this.defaultStyleKey($.ig.ShapeSeries.prototype.$type);
	},
	shouldRecordMarkerPositions: function () {
		return this.shouldDisplayMarkers();
	}
	,
	renderWithView: function (view) {
		var $self = this;
		var shapeView = view;
		shapeView.shapeStyleChanged(this.shapeStyle());
		shapeView.shapeStyleSelectorChanged(this.shapeStyleSelector());
		$.ig.ShapeSeriesBase.prototype.renderWithView.call(this, view);
		if (this.clearAndAbortIfInvalid1(view)) {
			return;
		}
		var viewportRect, windowRect;
		var $ret = view.getViewInfo(viewportRect, windowRect);
		viewportRect = $ret.p0;
		windowRect = $ret.p1;
		var effectiveViewportRect = this.getEffectiveViewport1(view);
		this._xParams = new $.ig.ScalerParams(1, windowRect, viewportRect, this.xAxis().isInverted());
		this._xParams._effectiveViewportRect = effectiveViewportRect;
		this._yParams = new $.ig.ScalerParams(1, windowRect, viewportRect, this.yAxis().isInverted());
		this._yParams._effectiveViewportRect = effectiveViewportRect;
		if (viewportRect.width() < 1 || viewportRect.height() < 1) {
			return;
		}
		this.axisInfoCache((function () {
			var $ret = new $.ig.ShapeAxisInfoCache();
			$ret.xAxis($self.xAxis());
			$ret.yAxis($self.yAxis());
			$ret.xAxisIsInverted($self.xAxis().isInverted());
			$ret.yAxisIsInverted($self.yAxis().isInverted());
			$ret.fastItemsSource($self.fastItemsSource());
			$ret.shapeColumn($self.shapeColumn());
			return $ret;
		}()));
		if (this.shouldDisplayMarkers()) {
			var markers = new $.ig.Dictionary$2($.ig.Object.prototype.$type, $.ig.OwnedPoint.prototype.$type, 0);
			shapeView.markerManager().winnowMarkers(markers, 400, windowRect, viewportRect, this.resolution());
			shapeView.markerManager().render(markers, false);
		}
	}
	,
	shouldDisplayMarkers: function () {
		return this.actualMarkerTemplate() != null && ((this.markerType() != $.ig.MarkerType.prototype.none && this.markerType() != $.ig.MarkerType.prototype.unset) || this.markerTemplate() != null);
	}
	,
	createView: function () {
		return new $.ig.ShapeSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.ShapeSeriesBase.prototype.onViewCreated.call(this, view);
		this.shapeView(view);
	}
	,
	_shapeView: null,
	shapeView: function (value) {
		if (arguments.length === 1) {
			this._shapeView = value;
			return value;
		} else {
			return this._shapeView;
		}
	}
	,
	_axisInfoCache: null,
	axisInfoCache: function (value) {
		if (arguments.length === 1) {
			this._axisInfoCache = value;
			return value;
		} else {
			return this._axisInfoCache;
		}
	}
	,
	_xParams: null,
	_yParams: null,
	hasMarkers: function () {
		return true;
	}
	,
	getActualMarkerBrush: function () {
		return this.actualMarkerBrush();
	}
	,
	getActualMarkerOutlineBrush: function () {
		return this.actualMarkerOutline();
	}
	,
	getActualMarkerTemplate: function () {
		return this.actualMarkerTemplate();
	}
	,
	shapeStyleSelector: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.shapeStyleSelectorProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.shapeStyleSelectorProperty);
		}
	}
	,
	shapeStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.shapeStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.shapeStyleProperty);
		}
	}
	,
	markerType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerTypeProperty, $.ig.MarkerType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ShapeSeries.prototype.markerTypeProperty));
		}
	}
	,
	markerTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.markerTemplateProperty);
		}
	}
	,
	actualMarkerTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.actualMarkerTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.actualMarkerTemplateProperty);
		}
	}
	,
	markerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.markerBrushProperty);
		}
	}
	,
	actualMarkerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.actualMarkerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.actualMarkerBrushProperty);
		}
	}
	,
	markerOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.markerOutlineProperty);
		}
	}
	,
	actualMarkerOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.actualMarkerOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.actualMarkerOutlineProperty);
		}
	}
	,
	markerStyle: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerStyleProperty, value);
			return value;
		} else {
			return this.getValue($.ig.ShapeSeries.prototype.markerStyleProperty);
		}
	}
	,
	markerCollisionAvoidance: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapeSeries.prototype.markerCollisionAvoidanceProperty, $.ig.CollisionAvoidanceType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.ShapeSeries.prototype.markerCollisionAvoidanceProperty));
		}
	}
	,
	updateIndexedProperties: function () {
		$.ig.ShapeSeriesBase.prototype.updateIndexedProperties.call(this);
		if (this.markerTemplate() != null) {
			this.shapeView().bindMarkerTemplateToActual();
		} else {
			var markerType = $.ig.MarkerSeries.prototype.resolveMarkerType(this, this.markerType());
			var markerTemplatePropertyName = $.ig.MarkerSeries.prototype.getMarkerTemplatePropertyName(markerType);
			if (markerTemplatePropertyName == null) {
				this.actualMarkerTemplate($.ig.MarkerSeries.prototype.nullMarkerTemplate());
			} else {
				this.shapeView().bindActualToMarkerTemplate(markerTemplatePropertyName);
			}
		}
		if (this.markerBrush() != null) {
			this.shapeView().bindMarkerBrushToActual();
		} else {
			this.actualMarkerBrush(this.seriesViewer() == null ? null : this.seriesViewer().getMarkerBrushByIndex(this.index()));
		}
		if (this.markerOutline() != null) {
			this.shapeView().bindMarkerOutlineToActual();
		} else {
			this.actualMarkerOutline(this.seriesViewer() == null ? null : this.seriesViewer().getMarkerOutlineByIndex(this.index()));
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.ShapeSeriesBase.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.ShapeSeries.prototype.markerBrushPropertyName:
			case $.ig.ShapeSeries.prototype.markerTypePropertyName:
			case $.ig.ShapeSeries.prototype.markerOutlinePropertyName:
			case $.ig.ShapeSeries.prototype.markerTemplatePropertyName:
				this.updateIndexedProperties();
				this.renderSeries(false);
				break;
			case $.ig.ShapeSeries.prototype.actualMarkerTemplatePropertyName:
				if (oldValue == $.ig.MarkerSeries.prototype.nullMarkerTemplate() || newValue == $.ig.MarkerSeries.prototype.nullMarkerTemplate() || (oldValue == null || newValue != null)) {
					this.shapeView().doUpdateMarkerTemplates();
					this.renderSeries(false);
				}
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ShapeSeries.prototype._shapeStylePropertyName:
				this.shapeView().shapeStyleChanged(this.shapeStyle());
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
			case $.ig.ShapeSeries.prototype._shapeStyleSelectorPropertyName:
				this.shapeView().shapeStyleSelectorChanged(this.shapeStyleSelector());
				this.renderSeries(false);
				this.notifyThumbnailAppearanceChanged();
				break;
		}
	}
	,
	getHitDataContext: function (position) {
		var marker = this.shapeView().getHitMarker(position);
		var ret = null;
		if (marker != null) {
			ret = marker.content();
		}
		if (ret != null) {
			return ret;
		}
		return $.ig.ShapeSeriesBase.prototype.getHitDataContext.call(this, position);
	}
	,
	removeUnusedMarkers: function (list, markers) {
		var remove = new $.ig.List$1($.ig.Object.prototype.$type, 0);
		var en = markers.activeKeys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			if (!list.containsKey(key)) {
				remove.add1(key);
			}
		}
		var en1 = remove.getEnumerator();
		while (en1.moveNext()) {
			var key1 = en1.current();
			markers.remove(key1);
		}
	}
	,
	getMarkerLocations: function (view, markers, locations, windowRect, viewport) {
		var $self = this;
		if (locations == null || locations.length != this.axisInfoCache().fastItemsSource().count()) {
			locations = new Array(this.axisInfoCache().fastItemsSource().count());
			for (var i = 0; i < this.axisInfoCache().fastItemsSource().count(); i++) {
				locations[i] = new $.ig.Point(0);
			}
		}
		var xParms = (function () {
			var $ret = new $.ig.ScalerParams(1, windowRect, viewport, $self.xAxis().isInverted());
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		var yParms = (function () {
			var $ret = new $.ig.ScalerParams(1, windowRect, viewport, $self.yAxis().isInverted());
			$ret._effectiveViewportRect = $self.getEffectiveViewport1(view);
			return $ret;
		}());
		var minX = this.axisInfoCache().xAxis().getUnscaledValue(xParms._viewportRect.left(), xParms);
		var maxX = this.axisInfoCache().xAxis().getUnscaledValue(xParms._viewportRect.right(), xParms);
		var minY = this.axisInfoCache().yAxis().getUnscaledValue(yParms._viewportRect.bottom(), yParms);
		var maxY = this.axisInfoCache().yAxis().getUnscaledValue(yParms._viewportRect.top(), yParms);
		if (this.axisInfoCache().xAxisIsInverted()) {
			var swap = minX;
			minX = maxX;
			maxX = swap;
		}
		if (this.axisInfoCache().yAxisIsInverted()) {
			var swap1 = minY;
			minY = maxY;
			maxY = swap1;
		}
		var cache = this.axisInfoCache();
		var xAxis = cache.xAxis();
		var yAxis = cache.yAxis();
		var x;
		var y;
		var viewportLeft = xParms._viewportRect.left();
		var viewportRight = xParms._viewportRect.right();
		var viewportTop = yParms._viewportRect.top();
		var viewportBottom = yParms._viewportRect.bottom();
		for (var i1 = 0; i1 < this.axisInfoCache().fastItemsSource().count(); i1++) {
			x = 0;
			y = 0;
			var shape = null;
			var $ret = view.markerPositions().tryGetValue(i1, shape);
			shape = $ret.p1;
			if (shape == null) {
				locations[i1].__x = NaN;
				locations[i1].__y = NaN;
			} else {
				var bounds = shape.fullBounds();
				var center = $.ig.RectUtil.prototype.getCenter(bounds);
				x = center.__x;
				y = center.__y;
				if (x >= viewportLeft && x <= viewportRight && y >= viewportTop && y <= viewportBottom) {
					locations[i1].__x = x;
					locations[i1].__y = y;
				} else {
					locations[i1].__x = NaN;
					locations[i1].__y = NaN;
				}
			}
		}
		return locations;
	}
	,
	getActiveIndexes: function (markers, indexes) {
		if (indexes == null || indexes.length != markers.activeCount()) {
			indexes = new Array(markers.activeCount());
		}
		var i = 0;
		var source = this.fastItemsSource();
		var en = markers.activeKeys().getEnumerator();
		while (en.moveNext()) {
			var key = en.current();
			indexes[i] = source.indexOf(key);
			i++;
		}
		return indexes;
	}
	,
	$type: new $.ig.Type('ShapeSeries', $.ig.ShapeSeriesBase.prototype.$type)
}, true);

$.ig.util.defType('ShapeAxisInfoCache', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_xAxis: null,
	xAxis: function (value) {
		if (arguments.length === 1) {
			this._xAxis = value;
			return value;
		} else {
			return this._xAxis;
		}
	}
	,
	_yAxis: null,
	yAxis: function (value) {
		if (arguments.length === 1) {
			this._yAxis = value;
			return value;
		} else {
			return this._yAxis;
		}
	}
	,
	_xAxisIsInverted: false,
	xAxisIsInverted: function (value) {
		if (arguments.length === 1) {
			this._xAxisIsInverted = value;
			return value;
		} else {
			return this._xAxisIsInverted;
		}
	}
	,
	_yAxisIsInverted: false,
	yAxisIsInverted: function (value) {
		if (arguments.length === 1) {
			this._yAxisIsInverted = value;
			return value;
		} else {
			return this._yAxisIsInverted;
		}
	}
	,
	_fastItemsSource: null,
	fastItemsSource: function (value) {
		if (arguments.length === 1) {
			this._fastItemsSource = value;
			return value;
		} else {
			return this._fastItemsSource;
		}
	}
	,
	_shapeColumn: null,
	shapeColumn: function (value) {
		if (arguments.length === 1) {
			this._shapeColumn = value;
			return value;
		} else {
			return this._shapeColumn;
		}
	}
	,
	$type: new $.ig.Type('ShapeAxisInfoCache', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('GeographicSymbolSeries', 'GeographicMapSeriesHost$1', {
	init: function () {
		$.ig.GeographicMapSeriesHost$1.prototype.init.call(this, $.ig.ScatterSeries.prototype.$type);
		this.defaultStyleKey($.ig.GeographicSymbolSeries.prototype.$type);
	},
	createView: function () {
		return new $.ig.GeographicSymbolSeriesView(this);
	}
	,
	onViewCreated: function (view) {
		$.ig.GeographicMapSeriesHost$1.prototype.onViewCreated.call(this, view);
		this.symbolView(view);
	}
	,
	_symbolView: null,
	symbolView: function (value) {
		if (arguments.length === 1) {
			this._symbolView = value;
			return value;
		} else {
			return this._symbolView;
		}
	}
	,
	createSeries: function () {
		return new $.ig.ScatterSeries();
	}
	,
	_scatterSeries: null,
	scatterSeries: function (value) {
		if (arguments.length === 1) {
			this._scatterSeries = value;
			return value;
		} else {
			return this._scatterSeries;
		}
	}
	,
	latitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.latitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicSymbolSeries.prototype.latitudeMemberPathProperty);
		}
	}
	,
	longitudeMemberPath: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.longitudeMemberPathProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicSymbolSeries.prototype.longitudeMemberPathProperty);
		}
	}
	,
	markerType: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.markerTypeProperty, $.ig.MarkerType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.GeographicSymbolSeries.prototype.markerTypeProperty));
		}
	}
	,
	markerCollisionAvoidance: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidanceProperty, $.ig.CollisionAvoidanceType.prototype.getBox(value));
			return value;
		} else {
			return $.ig.util.getEnumValue(this.getValue($.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidanceProperty));
		}
	}
	,
	markerTemplate: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.markerTemplateProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicSymbolSeries.prototype.markerTemplateProperty);
		}
	}
	,
	markerBrush: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.markerBrushProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicSymbolSeries.prototype.markerBrushProperty);
		}
	}
	,
	markerOutline: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.markerOutlineProperty, value);
			return value;
		} else {
			return this.getValue($.ig.GeographicSymbolSeries.prototype.markerOutlineProperty);
		}
	}
	,
	maximumMarkers: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.GeographicSymbolSeries.prototype.maximumMarkersProperty, value);
			return value;
		} else {
			return $.ig.util.getValue(this.getValue($.ig.GeographicSymbolSeries.prototype.maximumMarkersProperty));
		}
	}
	,
	propertyUpdatedOverride: function (sender, propertyName, oldValue, newValue) {
		$.ig.GeographicMapSeriesHost$1.prototype.propertyUpdatedOverride.call(this, sender, propertyName, oldValue, newValue);
		switch (propertyName) {
			case $.ig.Series.prototype.seriesViewerPropertyName:
				this.symbolView().onSeriesViewerUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.longitudeMemberPathPropertyName:
				this.symbolView().onLongitudeMemberPathUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.latitudeMemberPathPropertyName:
				this.symbolView().onLatitudeMemberPathUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidancePropertyName:
				this.symbolView().onMarkerCollisionAvoidanceUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.markerTypePropertyName:
				this.symbolView().onMarkerTypeUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.markerTemplatePropertyName:
				this.symbolView().onMarkerTemplateUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.maximumMarkersPropertyName:
				this.symbolView().onMaximumMarkersUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.xAxisPropertyName:
				this.symbolView().onXAxisUpdated();
				break;
			case $.ig.XamGeographicMap.prototype.yAxisPropertyName:
				this.symbolView().onYAxisUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.markerBrushPropertyName:
				this.symbolView().onMarkerBrushUpdated();
				break;
			case $.ig.GeographicSymbolSeries.prototype.markerOutlinePropertyName:
				this.symbolView().onMarkerOutlineUpdated();
				break;
		}
	}
	,
	$type: new $.ig.Type('GeographicSymbolSeries', $.ig.GeographicMapSeriesHost$1.prototype.$type.specialize($.ig.ScatterSeries.prototype.$type))
}, true);

$.ig.util.defType('ShapefileRecord', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	fields: null,
	points: null,
	onPropertyChanged: function (propertyName) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('ShapefileRecord', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('ShapefileConverter', 'DependencyObjectNotifier', {
	init: function () {
		var $self = this;
		$.ig.DependencyObjectNotifier.prototype.init.call(this);
		this.records(new $.ig.ObservableCollection$1($.ig.ShapefileRecord.prototype.$type, 0));
		var $t = this.records();
		$t.collectionChanged = $.ig.Delegate.prototype.combine($t.collectionChanged, function (sender, e) {
			if ($self.collectionChanged != null) {
				$self.collectionChanged($self, e);
			}
		});
	},
	worldRect: function (value) {
		if (arguments.length === 1) {
			if ($.ig.Rect.prototype.l_op_Inequality(this.__worldRect, value)) {
				var oldValue = this.__worldRect;
				this.__worldRect = value;
				this.propertyUpdated($.ig.ShapefileConverter.prototype._worldRectPropertyName, oldValue, this.__worldRect);
			}
			return value;
		} else {
			return this.__worldRect;
		}
	}
	,
	__worldRect: null,
	shapefileSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapefileConverter.prototype.shapefileSourceProperty, value);
			return value;
		} else {
			return $.ig.util.cast($.ig.Uri.prototype.$type, this.getValue($.ig.ShapefileConverter.prototype.shapefileSourceProperty));
		}
	}
	,
	databaseSource: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ShapefileConverter.prototype.databaseSourceProperty, value);
			return value;
		} else {
			return $.ig.util.cast($.ig.Uri.prototype.$type, this.getValue($.ig.ShapefileConverter.prototype.databaseSourceProperty));
		}
	}
	,
	propertyUpdated: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.ShapefileConverter.prototype._shapefileSourcePropertyName:
			case $.ig.ShapefileConverter.prototype._databaseSourcePropertyName:
				if (this.shapefileSource() != null && this.databaseSource() != null) {
					this.importAsync();
				}
				break;
		}
		this.onPropertyChanged(propertyName);
	}
	,
	importCompleted: null,
	onImportCompleted: function (e) {
		if (this.importCompleted != null) {
			this.importCompleted(this, e);
		}
	}
	,
	importAsync: function () {
		var $self = this;
		var shpReader = null;
		var dbfReader = null;
		var finished = function () {
			if (shpReader != null && dbfReader != null) {
				$self.parseShapes(shpReader, dbfReader);
				$self.onImportCompleted(new $.ig.AsyncCompletedEventArgs(null, false, null));
			}
		};
		$.ig.BinaryFileDownloader.prototype.downloadFile(this.shapefileSource().value(), function (txt) {
			shpReader = new $.ig.BinaryReader(0, txt, false);
			finished();
		}, function (txt) {
			throw new $.ig.Error(1, "shape file download error: " + txt);
		});
		$.ig.BinaryFileDownloader.prototype.downloadFile(this.databaseSource().value(), function (txt) {
			dbfReader = new $.ig.BinaryReader(0, txt, false);
			finished();
		}, function (txt) {
			throw new $.ig.Error(1, "dbf file download error: " + txt);
		});
	}
	,
	parseShapes: function (shpReader, dbfReader) {
		var header = $.ig.ShapeFileUtil.prototype.readHeader(shpReader, dbfReader);
		this.worldRect(header.bounds());
		var position = shpReader.currentPosition();
		var length = shpReader.length();
		while (position < length) {
			var record = $.ig.ShapeFileUtil.prototype.readShape(header, shpReader, dbfReader);
			var record_ = record;
			this.records().add(record);
			position = shpReader.currentPosition();
			length = shpReader.length();
		}
	}
	,
	__records: null,
	records: function (value) {
		if (arguments.length === 1) {
			this.__records = value;
			return value;
		} else {
			return this.__records;
		}
	}
	,
	collectionChanged: null,
	indexOf: function (item) {
		return this.__records.indexOf(item);
	}
	,
	insert: function (index, item) {
		this.__records.insert(index, item);
	}
	,
	removeAt: function (index) {
		this.__records.removeAt(index);
	}
	,
	item: function (index, value) {
		if (arguments.length === 2) {
			this.__records.__inner[index] = value;
			return value;
		} else {
			return this.__records.__inner[index];
		}
	}
	,
	add: function (item) {
		this.__records.add(item);
	}
	,
	clear: function () {
		this.__records.clear();
	}
	,
	contains: function (item) {
		return this.__records.contains(item);
	}
	,
	copyTo: function (array, arrayIndex) {
		this.__records.copyTo(array, arrayIndex);
	}
	,
	count: function () {
		return this.__records.count();
	}
	,
	isReadOnly: function () {
		return (this.__records).isReadOnly();
	}
	,
	remove: function (item) {
		return this.__records.remove(item);
	}
	,
	getEnumerator: function () {
		return this.__records.getEnumerator();
	}
	,
	getEnumerator: function () {
		return this.__records.getEnumerator();
	}
	,
	$type: new $.ig.Type('ShapefileConverter', $.ig.DependencyObjectNotifier.prototype.$type, [$.ig.IList$1.prototype.$type.specialize($.ig.ShapefileRecord.prototype.$type), $.ig.INotifyCollectionChanged.prototype.$type])
}, true);

$.ig.util.defType('ShapeFileUtil', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	readHeader: function (shpReader, dbfReader) {
		if ($.ig.ShapeFileUtil.prototype._dbfBaseDataTypes == null) {
			$.ig.ShapeFileUtil.prototype.initDataTypes();
		}
		var shapeHeader = new $.ig.Header();
		var filecode = $.ig.ShapeFileUtil.prototype.swap(shpReader.readInt32());
		if (filecode != 9994) {
			throw new $.ig.Error(1, "Corrupt Shp file - incorrect file code");
		}
		shpReader.readInt32();
		shpReader.readInt32();
		shpReader.readInt32();
		shpReader.readInt32();
		shpReader.readInt32();
		$.ig.ShapeFileUtil.prototype.swap(shpReader.readInt32());
		shpReader.readInt32();
		shapeHeader.shapeType(shpReader.readInt32());
		var xymin = new $.ig.Point(0);
		var xymax = new $.ig.Point(0);
		xymin.__x = shpReader.readDouble();
		xymin.__y = shpReader.readDouble();
		xymax.__x = shpReader.readDouble();
		xymax.__y = shpReader.readDouble();
		shpReader.readDouble();
		shpReader.readDouble();
		shpReader.readDouble();
		shpReader.readDouble();
		shapeHeader.bounds(new $.ig.Rect(0, xymin.__x, xymin.__y, xymax.__x - xymin.__x, xymax.__y - xymin.__y));
		if (dbfReader != null) {
			var version = dbfReader.readByte();
			if (version != 3) {
				throw new $.ig.Error(1, "Corrupt Dbf file - wrong version number");
			}
			dbfReader.readByte();
			dbfReader.readByte();
			dbfReader.readByte();
			dbfReader.readUInt32();
			dbfReader.readUInt16();
			var lengthOfEachRecord = dbfReader.readUInt16();
			dbfReader.readBytes(2);
			dbfReader.readBytes(1);
			dbfReader.readBytes(1);
			dbfReader.readBytes(4);
			dbfReader.readBytes(8);
			dbfReader.readBytes(1);
			dbfReader.readBytes(1);
			dbfReader.readBytes(2);
			var totalFieldLength = 0;
			while (totalFieldLength < lengthOfEachRecord - 1) {
				var dbfField = new $.ig.XBaseField();
				dbfField._name = $.ig.util.replace($.ig.Encoding.prototype.uTF8().getString1(dbfReader.readBytes(11), 0, 10), "\0", "");
				var key = $.ig.Encoding.prototype.uTF8().getString1(dbfReader.readBytes(1), 0, 1).charAt(0);
				dbfField._type = $.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item(key);
				dbfReader.readBytes(4);
				dbfField._length = dbfReader.readByte();
				dbfReader.readBytes(15);
				shapeHeader._dbfBaseFields.add(dbfField);
				totalFieldLength += dbfField._length;
			}
			if (dbfReader.readByte() != 13) {
				throw new $.ig.Error(1, "Corrup Dbf file - Missing field descriptor array terminator");
			}
		}
		return shapeHeader;
	}
	,
	readShape: function (shapeHeader, shpReader, dbfReader) {
		var number = $.ig.ShapeFileUtil.prototype.swap(shpReader.readInt32());
		var length = $.ig.ShapeFileUtil.prototype.swap(shpReader.readInt32());
		var shapeType = shpReader.readInt32();
		var row = null;
		switch (shapeType) {
			case $.ig.ShapeType.prototype.none: break;
			case $.ig.ShapeType.prototype.point:
				row = $.ig.ShapeFileUtil.prototype.readSymbol(shpReader);
				break;
			case $.ig.ShapeType.prototype.polyLine:
				row = $.ig.ShapeFileUtil.prototype.readPath(shpReader);
				break;
			case $.ig.ShapeType.prototype.polygon:
				row = $.ig.ShapeFileUtil.prototype.readSurface(shpReader);
				break;
			case $.ig.ShapeType.prototype.polyPoint: break;
			case $.ig.ShapeType.prototype.pointZ: break;
			case $.ig.ShapeType.prototype.polyLineZ:
				row = $.ig.ShapeFileUtil.prototype.readPathZ(shpReader);
				break;
			case $.ig.ShapeType.prototype.polygonZ:
				row = $.ig.ShapeFileUtil.prototype.readSurfaceZ(shpReader);
				break;
			case $.ig.ShapeType.prototype.polyPointZ: break;
			case $.ig.ShapeType.prototype.pointM: break;
			case $.ig.ShapeType.prototype.polyLineM: break;
			case $.ig.ShapeType.prototype.polygonM: break;
			case $.ig.ShapeType.prototype.polyPointM: break;
			case $.ig.ShapeType.prototype.polyPatch: break;
			default: break;
		}
		if (row == null) {
			throw new $.ig.Error(1, "Corrupt Shp file - failed to read " + $.ig.ShapeType.prototype.$getName(shapeType));
		}
		if (dbfReader != null) {
			dbfReader.readBytes(1);
			row.fields = new $.ig.Dictionary$2(String, $.ig.Object.prototype.$type, 0);
			var en = shapeHeader._dbfBaseFields.getEnumerator();
			while (en.moveNext()) {
				var baseField = en.current();
				var baseFieldBytes = dbfReader.readBytes(baseField._length);
				var baseFieldString = $.ig.Encoding.prototype.uTF8().getString1(baseFieldBytes, 0, baseField._length);
				var d;
				var f;
				var dt;
				switch (baseField._type) {
					case $.ig.XBaseDataType.prototype.number:
						var val = parseFloat(baseFieldString);
						if (!$.ig.util.isNaN(val)) {
							row.fields.add(baseField._name, val);
							$.ig.ShapeFileUtil.prototype.flattenFieldOnRow(row, baseField._name, val);
						}
						break;
					case $.ig.XBaseDataType.prototype.floatingPoint:
						var fval = parseFloat(baseFieldString);
						if (!$.ig.util.isNaN(fval)) {
							row.fields.add(baseField._name, fval);
							$.ig.ShapeFileUtil.prototype.flattenFieldOnRow(row, baseField._name, fval);
						}
						break;
					case $.ig.XBaseDataType.prototype.character:
						var sVal = baseFieldString.trim([]);
						row.fields.add(baseField._name, sVal);
						$.ig.ShapeFileUtil.prototype.flattenFieldOnRow(row, baseField._name, sVal);
						break;
					case $.ig.XBaseDataType.prototype.date:
						var str_ = baseFieldString;
						var dval = Date.parse(str_);
						row.fields.add(baseField._name, dval);
						$.ig.ShapeFileUtil.prototype.flattenFieldOnRow(row, baseField._name, dval);
						break;
					default: throw new $.ig.Error(1, "Unrecognized field type");
				}
			}
		}
		return row;
	}
	,
	flattenFieldOnRow: function (row_, property_, val_) {
		if (!row_.fieldValues) {
            row_.fieldValues = {};
            };
		row_.fieldValues[property_] = val_;
	}
	,
	swap: function (i) {
		return (((i & 255) << 24) | ((i & 65280) << 8) | ((i & 16711680) >> 8) | (($.ig.util.u32BitwiseAnd(i, 4278190080)) >> 24));
	}
	,
	readSymbol: function (shpReader) {
		var p0 = (function () {
			var $ret = new $.ig.Point(0);
			$ret.x(shpReader.readDouble());
			$ret.y(shpReader.readDouble());
			return $ret;
		}());
		return (function () {
			var $ret = new $.ig.ShapefileRecord();
			$ret.points = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 1, [ new $.ig.List$1($.ig.Point.prototype.$type, 1, [ p0 ]) ]);
			return $ret;
		}());
	}
	,
	readPathZ: function (shpReader) {
		var numParts, numPoints;
		var result = (function () { var $ret = $.ig.ShapeFileUtil.prototype.readPath1(shpReader, numParts, numPoints); numParts = $ret.p1; numPoints = $ret.p2; return $ret.ret; }());
		shpReader.readDouble();
		shpReader.readDouble();
		for (var current = 0; current < numPoints; current++) {
			shpReader.readDouble();
		}
		shpReader.readDouble();
		shpReader.readDouble();
		for (var current1 = 0; current1 < numPoints; current1++) {
			shpReader.readDouble();
		}
		return result;
	}
	,
	readPath1: function (shpReader, numParts, numPoints) {
		var p = new $.ig.Point(0);
		var xymin = (function () {
			var $ret = new $.ig.Point(0);
			$ret.x(shpReader.readDouble());
			$ret.y(shpReader.readDouble());
			return $ret;
		}());
		var xymax = (function () {
			var $ret = new $.ig.Point(0);
			$ret.x(shpReader.readDouble());
			$ret.y(shpReader.readDouble());
			return $ret;
		}());
		var bounds = new $.ig.Rect(0, xymin.__x, xymin.__y, xymax.__x - xymin.__x, xymax.__y - xymin.__y);
		numParts = shpReader.readInt32();
		numPoints = shpReader.readInt32();
		var partindex = new Array(numParts);
		for (var i = 0; i < numParts; ++i) {
			partindex[i] = shpReader.readInt32();
		}
		var pointCollections = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 0);
		for (var i1 = 0; i1 < numParts; ++i1) {
			var partsize = (i1 < numParts - 1 ? partindex[i1 + 1] : numPoints) - partindex[i1];
			var pointCollection = new $.ig.List$1($.ig.Point.prototype.$type, 2, partsize - 1);
			for (var j = 0; j < partsize; ++j) {
				p = new $.ig.Point(0);
				p.__x = shpReader.readDouble();
				p.__y = shpReader.readDouble();
				pointCollection.add(p);
			}
			if (pointCollection.count() > 0) {
				pointCollections.add(pointCollection);
			}
		}
		return {
			ret: (function () {
				var $ret = new $.ig.ShapefileRecord();
				$ret.points = pointCollections;
				return $ret;
			}()),
			p1: numParts,
			p2: numPoints
		};
	}
	,
	readPath: function (shpReader) {
		var numParts, numPoints;
		return (function () { var $ret = $.ig.ShapeFileUtil.prototype.readPath1(shpReader, numParts, numPoints); numParts = $ret.p1; numPoints = $ret.p2; return $ret.ret; }());
	}
	,
	readSurfaceZ: function (shpReader) {
		var numParts, numPoints;
		var result = (function () { var $ret = $.ig.ShapeFileUtil.prototype.readSurface1(shpReader, numParts, numPoints); numParts = $ret.p1; numPoints = $ret.p2; return $ret.ret; }());
		shpReader.readDouble();
		shpReader.readDouble();
		for (var current = 0; current < numPoints; current++) {
			shpReader.readDouble();
		}
		shpReader.readDouble();
		shpReader.readDouble();
		for (var current1 = 0; current1 < numPoints; current1++) {
			shpReader.readDouble();
		}
		return result;
	}
	,
	readSurface: function (shpReader) {
		var numParts, numPoints;
		return (function () { var $ret = $.ig.ShapeFileUtil.prototype.readSurface1(shpReader, numParts, numPoints); numParts = $ret.p1; numPoints = $ret.p2; return $ret.ret; }());
	}
	,
	readSurface1: function (shpReader, numParts, numPoints) {
		var xymin = (function () {
			var $ret = new $.ig.Point(0);
			$ret.x(shpReader.readDouble());
			$ret.y(shpReader.readDouble());
			return $ret;
		}());
		var xymax = (function () {
			var $ret = new $.ig.Point(0);
			$ret.x(shpReader.readDouble());
			$ret.y(shpReader.readDouble());
			return $ret;
		}());
		var bounds = new $.ig.Rect(0, xymin.__x, xymin.__y, xymax.__x - xymin.__x, xymax.__y - xymin.__y);
		var p = new $.ig.Point(0);
		numParts = shpReader.readInt32();
		numPoints = shpReader.readInt32();
		var partindex = new Array(numParts);
		for (var i = 0; i < numParts; ++i) {
			partindex[i] = shpReader.readInt32();
		}
		var pointCollections = new $.ig.List$1($.ig.List$1.prototype.$type.specialize($.ig.Point.prototype.$type), 0);
		for (var i1 = 0; i1 < numParts; ++i1) {
			var partsize = (i1 < numParts - 1 ? partindex[i1 + 1] : numPoints) - partindex[i1];
			var pointCollection = new $.ig.List$1($.ig.Point.prototype.$type, 2, partsize);
			p = new $.ig.Point(0);
			p.__x = shpReader.readDouble();
			p.__y = shpReader.readDouble();
			for (var j = 1; j < partsize; ++j) {
				p = new $.ig.Point(0);
				p.__x = shpReader.readDouble();
				p.__y = shpReader.readDouble();
				pointCollection.add(p);
			}
			if (pointCollection.count() > 1) {
				pointCollections.add(pointCollection);
			}
		}
		return {
			ret: (function () {
				var $ret = new $.ig.ShapefileRecord();
				$ret.points = pointCollections;
				return $ret;
			}()),
			p1: numParts,
			p2: numPoints
		};
	}
	,
	initDataTypes: function () {
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes = new $.ig.Dictionary$2($.ig.String.prototype.$type, $.ig.XBaseDataType.prototype.$type, 0);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('C', $.ig.XBaseDataType.prototype.character);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('N', $.ig.XBaseDataType.prototype.number);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('L', $.ig.XBaseDataType.prototype.logical);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('D', $.ig.XBaseDataType.prototype.date);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('M', $.ig.XBaseDataType.prototype.memo);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('F', $.ig.XBaseDataType.prototype.floatingPoint);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('B', $.ig.XBaseDataType.prototype.binary);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('G', $.ig.XBaseDataType.prototype.general);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('P', $.ig.XBaseDataType.prototype.picture);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('Y', $.ig.XBaseDataType.prototype.currency);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('T', $.ig.XBaseDataType.prototype.dateTime);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('I', $.ig.XBaseDataType.prototype.integer);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('V', $.ig.XBaseDataType.prototype.variField);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('X', $.ig.XBaseDataType.prototype.variant);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('@', $.ig.XBaseDataType.prototype.timestamp);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('O', $.ig.XBaseDataType.prototype.double1);
		$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes.item('+', $.ig.XBaseDataType.prototype.autoIncrement);
	}
	,
	staticInit: function () {
		$.ig.ShapeFileUtil.prototype.initDataTypes();
	},
	$type: new $.ig.Type('ShapeFileUtil', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('Header', 'Object', {
	init: function () {
		this._dbfBaseFields = new $.ig.List$1($.ig.XBaseField.prototype.$type, 0);
		$.ig.Object.prototype.init.call(this);
	},
	_shapeType: 0,
	shapeType: function (value) {
		if (arguments.length === 1) {
			this._shapeType = value;
			return value;
		} else {
			return this._shapeType;
		}
	}
	,
	_bounds: null,
	bounds: function (value) {
		if (arguments.length === 1) {
			this._bounds = value;
			return value;
		} else {
			return this._bounds;
		}
	}
	,
	xYMin: function () {
		return { __x: this.bounds().left(), __y: this.bounds().top(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	xYMax: function () {
		return { __x: this.bounds().right(), __y: this.bounds().bottom(), $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName };
	}
	,
	_dbfBaseFields: null,
	$type: new $.ig.Type('Header', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('XBaseField', 'Object', {
	init: function () {
		$.ig.Object.prototype.init.call(this);
	},
	_name: null,
	_type: 0,
	_length: 0,
	$type: new $.ig.Type('XBaseField', $.ig.Object.prototype.$type)
}, true);

$.ig.util.defType('ItfConverter', 'DependencyObject', {
	init: function () {
		$.ig.DependencyObject.prototype.init.call(this);
	},
	source: function (value) {
		if (arguments.length === 1) {
			this.setValue($.ig.ItfConverter.prototype.sourceProperty, value);
			return value;
		} else {
			return $.ig.util.cast($.ig.Uri.prototype.$type, this.getValue($.ig.ItfConverter.prototype.sourceProperty));
		}
	}
	,
	__triangulationSource: null,
	triangulationSource: function (value) {
		if (arguments.length === 1) {
			var changed = value != this.triangulationSource();
			if (changed) {
				var oldValue = this.__triangulationSource;
				this.__triangulationSource = value;
				this.onPropertyChanged($.ig.ItfConverter.prototype._triangulationSourcePropertyName, oldValue, this.triangulationSource());
			}
			return value;
		} else {
			return this.__triangulationSource;
		}
	}
	,
	importAsync: function () {
		var $self = this;
		var itfReader = null;
		var finished = function () {
			if (itfReader != null) {
				$self.triangulationSource($.ig.TriangulationSource.prototype.loadItf(itfReader));
			}
			$self.onImportCompleted(new $.ig.AsyncCompletedEventArgs(null, false, null));
		};
		$.ig.BinaryFileDownloader.prototype.downloadFile(this.source().value(), function (txt) {
			itfReader = new $.ig.BinaryReader(0, txt, false);
			finished();
		}, function (txt) {
			throw new $.ig.Error(1, "itf file download error: " + txt);
		});
	}
	,
	importCompleted: null,
	onImportCompleted: function (e) {
		if (this.importCompleted != null) {
			this.importCompleted(this, e);
		}
	}
	,
	propertyUpdated: function (propertyName, oldValue, newValue) {
		switch (propertyName) {
			case $.ig.ItfConverter.prototype._sourcePropertyName:
				if (this.source() != null) {
					this.importAsync();
				}
				break;
		}
	}
	,
	onPropertyChanged: function (propertyName, oldValue, newValue) {
		if (this.propertyChanged != null) {
			this.propertyChanged(this, new $.ig.PropertyChangedEventArgs(propertyName));
		}
		this.propertyUpdated(propertyName, oldValue, newValue);
	}
	,
	propertyChanged: null,
	$type: new $.ig.Type('ItfConverter', $.ig.DependencyObject.prototype.$type, [$.ig.INotifyPropertyChanged.prototype.$type])
}, true);

$.ig.util.defType('LinearContourValueResolver___GetContourValues__IteratorClass', 'Object', {
	__1__state: 0,
	__2__current: 0,
	__i_5_0: 0,
	_valueColumn: null,
	__3__valueColumn: null,
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
					this.__i_5_0 = 0;
					this.__1__state = 1;
					break;
				case 1:
					this.__1__state = -1;
					if (this.__i_5_0 < this.__4__this.valueCount()) {
						this.__2__current = this._valueColumn.minimum() + (this._valueColumn.maximum() - this._valueColumn.minimum()) * (this.__i_5_0 + 1) / (this.__4__this.valueCount() + 1);
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
			d__ = new $.ig.LinearContourValueResolver___GetContourValues__IteratorClass(0);
			d__.__4__this = this.__4__this;
		}
		d__._valueColumn = this.__3__valueColumn;
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
	$type: new $.ig.Type('LinearContourValueResolver___GetContourValues__IteratorClass', $.ig.Object.prototype.$type, [$.ig.IEnumerable$1.prototype.$type.specialize(Number), $.ig.IEnumerable.prototype.$type, $.ig.IEnumerator$1.prototype.$type.specialize(Number), $.ig.IEnumerator.prototype.$type, $.ig.IDisposable.prototype.$type])
}, true);

$.ig.ShapeType.prototype.none = 0;
$.ig.ShapeType.prototype.point = 1;
$.ig.ShapeType.prototype.polyLine = 3;
$.ig.ShapeType.prototype.polygon = 5;
$.ig.ShapeType.prototype.polyPoint = 8;
$.ig.ShapeType.prototype.pointZ = 11;
$.ig.ShapeType.prototype.polyLineZ = 13;
$.ig.ShapeType.prototype.polygonZ = 15;
$.ig.ShapeType.prototype.polyPointZ = 18;
$.ig.ShapeType.prototype.pointM = 21;
$.ig.ShapeType.prototype.polyLineM = 23;
$.ig.ShapeType.prototype.polygonM = 25;
$.ig.ShapeType.prototype.polyPointM = 28;
$.ig.ShapeType.prototype.polyPatch = 31;

$.ig.XBaseDataType.prototype.character = 0;
$.ig.XBaseDataType.prototype.number = 1;
$.ig.XBaseDataType.prototype.logical = 2;
$.ig.XBaseDataType.prototype.date = 3;
$.ig.XBaseDataType.prototype.memo = 4;
$.ig.XBaseDataType.prototype.floatingPoint = 5;
$.ig.XBaseDataType.prototype.binary = 6;
$.ig.XBaseDataType.prototype.general = 7;
$.ig.XBaseDataType.prototype.picture = 8;
$.ig.XBaseDataType.prototype.currency = 9;
$.ig.XBaseDataType.prototype.dateTime = 10;
$.ig.XBaseDataType.prototype.integer = 11;
$.ig.XBaseDataType.prototype.variField = 12;
$.ig.XBaseDataType.prototype.variant = 13;
$.ig.XBaseDataType.prototype.timestamp = 14;
$.ig.XBaseDataType.prototype.double1 = 15;
$.ig.XBaseDataType.prototype.autoIncrement = 16;

$.ig.BingMapsImageryStyle.prototype.aerial = 0;
$.ig.BingMapsImageryStyle.prototype.aerialWithLabels = 1;
$.ig.BingMapsImageryStyle.prototype.road = 2;

$.ig.ColorScaleInterpolationMode.prototype.select = 0;
$.ig.ColorScaleInterpolationMode.prototype.interpolateRGB = 1;
$.ig.ColorScaleInterpolationMode.prototype.interpolateHSV = 2;

$.ig.ShapeSeriesBase.prototype.shapeMemberPathPropertyName = "ShapeMemberPath";
$.ig.ShapeSeriesBase.prototype._shapeColumnPropertyName = "ShapeColumn";
$.ig.ShapeSeriesBase.prototype.xAxisPropertyName = "XAxis";
$.ig.ShapeSeriesBase.prototype.yAxisPropertyName = "YAxis";
$.ig.ShapeSeriesBase.prototype._fillScalePropertyName = "FillScale";
$.ig.ShapeSeriesBase.prototype._fillMemberPathPropertyName = "FillMemberPath";
$.ig.ShapeSeriesBase.prototype._fillColumnPropertyName = "FillColumn";
$.ig.ShapeSeriesBase.prototype.shapeFilterResolutionPropertyName = "ShapeFilterResolution";
$.ig.ShapeSeriesBase.prototype.shapeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype.shapeMemberPathPropertyName, String, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.ShapeSeriesBase.prototype.shapeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeriesBase.prototype.xAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype.xAxisPropertyName, $.ig.NumericXAxis.prototype.$type, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.ShapeSeriesBase.prototype.xAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeriesBase.prototype.yAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype.yAxisPropertyName, $.ig.NumericYAxis.prototype.$type, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.ShapeSeriesBase.prototype.yAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeriesBase.prototype.fillScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype._fillScalePropertyName, $.ig.ValueBrushScale.prototype.$type, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeriesBase.prototype._fillScalePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeriesBase.prototype.fillMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype._fillMemberPathPropertyName, String, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.ShapeSeriesBase.prototype._fillMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeriesBase.prototype.shapeFilterResolutionProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeriesBase.prototype.shapeFilterResolutionPropertyName, Number, $.ig.ShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, 2, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeriesBase.prototype.shapeFilterResolutionPropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicMapSeriesHost$1.prototype._visibleFromScalePropertyName = "VisibleFromScale";

$.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathPropertyName = "ShapeMemberPath";
$.ig.GeographicShapeSeriesBase.prototype._shapeFilterResolutionPropertyName = "ShapeFilterResolution";
$.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathPropertyName, String, $.ig.GeographicShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, "points", function (sender, e) {
	($.ig.util.cast($.ig.GeographicShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.GeographicShapeSeriesBase.prototype.shapeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeriesBase.prototype.shapeFilterResolutionProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeriesBase.prototype._shapeFilterResolutionPropertyName, Number, $.ig.GeographicShapeSeriesBase.prototype.$type, new $.ig.PropertyMetadata(2, 2, function (sender, e) {
	($.ig.util.cast($.ig.GeographicShapeSeriesBase.prototype.$type, sender)).raisePropertyChanged($.ig.GeographicShapeSeriesBase.prototype._shapeFilterResolutionPropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicTileSeries.prototype.tileImageryPropertyName = "TileImagery";

$.ig.TileSeries.prototype.tileImageryPropertyName = "TileImagery";

$.ig.ShapeSeriesViewBase.prototype.hIT_THICKNESS_AUGMENT1 = 3;

$.ig.XYTriangulatingSeries.prototype._xMemberPathPropertyName = "XMemberPath";
$.ig.XYTriangulatingSeries.prototype._yMemberPathPropertyName = "YMemberPath";
$.ig.XYTriangulatingSeries.prototype._xColumnPropertyName = "XColumn";
$.ig.XYTriangulatingSeries.prototype._yColumnPropertyName = "YColumn";
$.ig.XYTriangulatingSeries.prototype.xAxisPropertyName = "XAxis";
$.ig.XYTriangulatingSeries.prototype.yAxisPropertyName = "YAxis";
$.ig.XYTriangulatingSeries.prototype._trianglesSourcePropertyName = "TrianglesSource";
$.ig.XYTriangulatingSeries.prototype._fastTrianglesSourcePropertyName = "FastTrianglesSource";
$.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath1PropertyName = "TriangleVertexMemberPath1";
$.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath2PropertyName = "TriangleVertexMemberPath2";
$.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath3PropertyName = "TriangleVertexMemberPath3";
$.ig.XYTriangulatingSeries.prototype._triangleVertexColumn1PropertyName = "TriangleVertexColumn1";
$.ig.XYTriangulatingSeries.prototype._triangleVertexColumn2PropertyName = "TriangleVertexColumn2";
$.ig.XYTriangulatingSeries.prototype._triangleVertexColumn3PropertyName = "TriangleVertexColumn3";
$.ig.XYTriangulatingSeries.prototype.xMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._xMemberPathPropertyName, String, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._xMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.yMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._yMemberPathPropertyName, String, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._yMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.xAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype.xAxisPropertyName, $.ig.NumericXAxis.prototype.$type, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype.xAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.yAxisProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype.yAxisPropertyName, $.ig.NumericYAxis.prototype.$type, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype.yAxisPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.trianglesSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._trianglesSourcePropertyName, $.ig.IEnumerable.prototype.$type, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._trianglesSourcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.fastTrianglesSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._fastTrianglesSourcePropertyName, $.ig.IFastItemsSource.prototype.$type, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._fastTrianglesSourcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath1Property = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath1PropertyName, String, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath1PropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath2Property = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath2PropertyName, String, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.XYTriangulatingSeries.prototype.$type, sender)).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath2PropertyName, e.oldValue(), e.newValue());
}));
$.ig.XYTriangulatingSeries.prototype.triangleVertexMemberPath3Property = $.ig.DependencyProperty.prototype.register($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath3PropertyName, String, $.ig.XYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XYTriangulatingSeries.prototype._triangleVertexMemberPath3PropertyName, e.oldValue(), e.newValue());
}));

$.ig.ContourLineSeriesView.prototype.hIT_THICKNESS_AUGMENT1 = 3;

$.ig.SphericalMercatorVerticalScaler.prototype._degreeAsRadian = Math.PI / 180;
$.ig.SphericalMercatorVerticalScaler.prototype.__maximumValue = 85.05112878;
$.ig.SphericalMercatorVerticalScaler.prototype.__minimumValue = -85.05112878;

$.ig.CustomPaletteColorScale.prototype._minimumValuePropertyName = "MinimumValue";
$.ig.CustomPaletteColorScale.prototype._maximumValuePropertyName = "MaximumValue";
$.ig.CustomPaletteColorScale.prototype._palettePropertyName = "Palette";
$.ig.CustomPaletteColorScale.prototype._interpolationModePropertyName = "InterpolationMode";
$.ig.CustomPaletteColorScale.prototype.minimumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.CustomPaletteColorScale.prototype._minimumValuePropertyName, Number, $.ig.CustomPaletteColorScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (sender, e) {
	($.ig.util.cast($.ig.CustomPaletteColorScale.prototype.$type, sender)).propertyUpdated($.ig.CustomPaletteColorScale.prototype._minimumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.CustomPaletteColorScale.prototype.maximumValueProperty = $.ig.DependencyProperty.prototype.register($.ig.CustomPaletteColorScale.prototype._maximumValuePropertyName, Number, $.ig.CustomPaletteColorScale.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (sender, e) {
	($.ig.util.cast($.ig.CustomPaletteColorScale.prototype.$type, sender)).propertyUpdated($.ig.CustomPaletteColorScale.prototype._maximumValuePropertyName, e.oldValue(), e.newValue());
}));
$.ig.CustomPaletteColorScale.prototype.interpolationModeProperty = $.ig.DependencyProperty.prototype.register($.ig.CustomPaletteColorScale.prototype._interpolationModePropertyName, $.ig.ColorScaleInterpolationMode.prototype.$type, $.ig.CustomPaletteColorScale.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.ColorScaleInterpolationMode.prototype.getBox($.ig.ColorScaleInterpolationMode.prototype.select), function (sender, e) {
	($.ig.util.cast($.ig.CustomPaletteColorScale.prototype.$type, sender)).propertyUpdated($.ig.CustomPaletteColorScale.prototype._interpolationModePropertyName, e.oldValue(), e.newValue());
}));

$.ig.XamGeographicMap.prototype.zoomablePropertyName = "Zoomable";
$.ig.XamGeographicMap.prototype._worldRectPropertyName = "WorldRect";
$.ig.XamGeographicMap.prototype.actualWorldRectPropertyName = "ActualWorldRect";
$.ig.XamGeographicMap.prototype.backgroundContentPropertyName = "BackgroundContent";
$.ig.XamGeographicMap.prototype.xAxisPropertyName = "XAxis";
$.ig.XamGeographicMap.prototype.yAxisPropertyName = "YAxis";
$.ig.XamGeographicMap.prototype.windowScalePropertyName = "WindowScale";
$.ig.XamGeographicMap.prototype.actualWindowScalePropertyName = "ActualWindowScale";
$.ig.XamGeographicMap.prototype.zoomableProperty = $.ig.DependencyProperty.prototype.register($.ig.XamGeographicMap.prototype.zoomablePropertyName, $.ig.Boolean.prototype.$type, $.ig.XamGeographicMap.prototype.$type, new $.ig.PropertyMetadata(2, true, function (sender, e) {
	($.ig.util.cast($.ig.XamGeographicMap.prototype.$type, sender)).raisePropertyChanged($.ig.XamGeographicMap.prototype.zoomablePropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamGeographicMap.prototype._defaultWorldRect = new $.ig.Rect(2, { __x: -180, __y: -85.05112878, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName }, { __x: 180, __y: 85.05112878, $type: $.ig.Point.prototype.$type, getType: $.ig.Object.prototype.getType, getGetHashCode: $.ig.Object.prototype.getGetHashCode, typeName: $.ig.Object.prototype.typeName });
$.ig.XamGeographicMap.prototype.worldRectProperty = $.ig.DependencyProperty.prototype.register($.ig.XamGeographicMap.prototype._worldRectPropertyName, $.ig.Rect.prototype.$type, $.ig.XamGeographicMap.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.XamGeographicMap.prototype._defaultWorldRect, function (sender, e) {
	(sender).raisePropertyChanged($.ig.XamGeographicMap.prototype._worldRectPropertyName, e.oldValue(), e.newValue());
}));
$.ig.XamGeographicMap.prototype.__unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
$.ig.XamGeographicMap.prototype.windowScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.XamGeographicMap.prototype.windowScalePropertyName, Number, $.ig.XamGeographicMap.prototype.$type, new $.ig.PropertyMetadata(2, NaN, function (sender, e) {
	($.ig.util.cast($.ig.XamGeographicMap.prototype.$type, sender)).raisePropertyChanged($.ig.XamGeographicMap.prototype.windowScalePropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicMapImagery.prototype._multiScaleImageTemplatePartName = "MultiScaleImage";
$.ig.GeographicMapImagery.prototype._windowRectPropertyName = "WindowRect";
$.ig.GeographicMapImagery.prototype._geographicMapPropertyName = "GeographicMap";
$.ig.GeographicMapImagery.prototype.windowRectProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicMapImagery.prototype._windowRectPropertyName, $.ig.Rect.prototype.$type, $.ig.GeographicMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.Rect.prototype.empty(), function (sender, e) {
	($.ig.util.cast($.ig.GeographicMapImagery.prototype.$type, sender)).onPropertyUpdated($.ig.GeographicMapImagery.prototype._windowRectPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicMapImagery.prototype.geographicMapProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicMapImagery.prototype._geographicMapPropertyName, $.ig.XamGeographicMap.prototype.$type, $.ig.GeographicMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.GeographicMapImagery.prototype.onGeographicMapChanged1));

$.ig.BingMapsMapImagery.prototype._defaultBingUri = "http://dev.virtualearth.net/REST/v1/Imagery/Metadata/";
$.ig.BingMapsMapImagery.prototype.isDeferredLoadPropertyName = "IsDeferredLoad";
$.ig.BingMapsMapImagery.prototype.tilePathPropertyName = "TilePath";
$.ig.BingMapsMapImagery.prototype.subDomainsPropertyName = "SubDomains";
$.ig.BingMapsMapImagery.prototype.actualTilePathPropertyName = "ActualTilePath";
$.ig.BingMapsMapImagery.prototype.actualSubDomainsPropertyName = "ActualSubDomains";
$.ig.BingMapsMapImagery.prototype.bingImageryRestUriPropertyName = "BingImageryRestUri";
$.ig.BingMapsMapImagery.prototype.actualBingImageryRestUriPropertyName = "ActualBingImageryRestUri";
$.ig.BingMapsMapImagery.prototype.cultureNamePropertyName = "CultureName";
$.ig.BingMapsMapImagery.prototype.apiKeyPropertyName = "ApiKey";
$.ig.BingMapsMapImagery.prototype.imageryStylePropertyName = "ImageryStyle";
$.ig.BingMapsMapImagery.prototype.isDeferredLoadProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.isDeferredLoadPropertyName, $.ig.Boolean.prototype.$type, $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, false, function (o, e) { ($.ig.util.cast($.ig.BingMapsMapImagery.prototype.$type, o)).onIsDeferredLoadChanged(e.oldValue(), e.newValue()); }));
$.ig.BingMapsMapImagery.prototype.tilePathProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.tilePathPropertyName, String, $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.BingMapsMapImagery.prototype.onTilePathChanged));
$.ig.BingMapsMapImagery.prototype.subDomainsProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.subDomainsPropertyName, $.ig.ObservableCollection$1.prototype.$type.specialize(String), $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.BingMapsMapImagery.prototype.onSubDomainsChanged));
$.ig.BingMapsMapImagery.prototype.cultureNameProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.cultureNamePropertyName, String, $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, "en-US", $.ig.BingMapsMapImagery.prototype.onCultureNameChanged));
$.ig.BingMapsMapImagery.prototype.apiKeyProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.apiKeyPropertyName, String, $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, String.empty(), function (o, e) { ($.ig.util.cast($.ig.BingMapsMapImagery.prototype.$type, o)).onApiKeyChanged(e.oldValue(), e.newValue()); }));
$.ig.BingMapsMapImagery.prototype.imageryStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.BingMapsMapImagery.prototype.imageryStylePropertyName, $.ig.BingMapsImageryStyle.prototype.$type, $.ig.BingMapsMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.BingMapsImageryStyle.prototype.getBox($.ig.BingMapsImageryStyle.prototype.aerialWithLabels), function (o, e) { ($.ig.util.cast($.ig.BingMapsMapImagery.prototype.$type, o)).onImageryStylePropertyChanged($.ig.util.getEnumValue(e.oldValue()), $.ig.util.getEnumValue(e.newValue())); }));

$.ig.CloudMadeMapImagery.prototype.keyPropertyName = "Key";
$.ig.CloudMadeMapImagery.prototype.parameterPropertyName = "Parameter";
$.ig.CloudMadeMapImagery.prototype.keyProperty = $.ig.DependencyProperty.prototype.register($.ig.CloudMadeMapImagery.prototype.keyPropertyName, String, $.ig.CloudMadeMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.CloudMadeMapImagery.prototype.onKeyChanged));
$.ig.CloudMadeMapImagery.prototype.parameterProperty = $.ig.DependencyProperty.prototype.register($.ig.CloudMadeMapImagery.prototype.parameterPropertyName, String, $.ig.CloudMadeMapImagery.prototype.$type, new $.ig.PropertyMetadata(2, null, $.ig.CloudMadeMapImagery.prototype.onParameterChanged));

$.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathPropertyName = "LatitudeMemberPath";
$.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathPropertyName = "LongitudeMemberPath";
$.ig.GeographicHighDensityScatterSeries.prototype.useBruteForcePropertyName = "UseBruteForce";
$.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadPropertyName = "ProgressiveLoad";
$.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledPropertyName = "MouseOverEnabled";
$.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumPropertyName = "HeatMinimum";
$.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumPropertyName = "HeatMaximum";
$.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorPropertyName = "HeatMinimumColor";
$.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorPropertyName = "HeatMaximumColor";
$.ig.GeographicHighDensityScatterSeries.prototype.pointExtentPropertyName = "PointExtent";
$.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathPropertyName, String, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.latitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathPropertyName, String, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.longitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.useBruteForceProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.useBruteForcePropertyName, $.ig.Boolean.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, false, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.useBruteForcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadPropertyName, $.ig.Boolean.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, true, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.progressiveLoadPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledPropertyName, $.ig.Boolean.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, false, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.mouseOverEnabledPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumPropertyName, Number, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 0, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumPropertyName, Number, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 50, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorPropertyName, $.ig.Color.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.heatMinimumColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorPropertyName, $.ig.Color.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.heatMaximumColorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicHighDensityScatterSeries.prototype.pointExtentProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicHighDensityScatterSeries.prototype.pointExtentPropertyName, $.ig.Number.prototype.$type, $.ig.GeographicHighDensityScatterSeries.prototype.$type, new $.ig.PropertyMetadata(2, 1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicHighDensityScatterSeries.prototype.pointExtentPropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorPropertyName = "ShapeStyleSelector";
$.ig.GeographicPolylineSeries.prototype.shapeStylePropertyName = "ShapeStyle";
$.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorPropertyName, $.ig.StyleSelector.prototype.$type, $.ig.GeographicPolylineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicPolylineSeries.prototype.shapeStyleSelectorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicPolylineSeries.prototype.shapeStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicPolylineSeries.prototype.shapeStylePropertyName, $.ig.Style.prototype.$type, $.ig.GeographicPolylineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicPolylineSeries.prototype.shapeStylePropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathPropertyName = "LatitudeMemberPath";
$.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathPropertyName = "LongitudeMemberPath";
$.ig.GeographicProportionalSymbolSeries.prototype.markerTypePropertyName = "MarkerType";
$.ig.GeographicProportionalSymbolSeries.prototype.markerTemplatePropertyName = "MarkerTemplate";
$.ig.GeographicProportionalSymbolSeries.prototype.markerBrushPropertyName = "MarkerBrush";
$.ig.GeographicProportionalSymbolSeries.prototype.markerOutlinePropertyName = "MarkerOutline";
$.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersPropertyName = "MaximumMarkers";
$.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathPropertyName = "RadiusMemberPath";
$.ig.GeographicProportionalSymbolSeries.prototype.radiusScalePropertyName = "RadiusScale";
$.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathPropertyName = "LabelMemberPath";
$.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathPropertyName = "FillMemberPath";
$.ig.GeographicProportionalSymbolSeries.prototype.fillScalePropertyName = "FillScale";
$.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathPropertyName, String, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.latitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathPropertyName, String, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.longitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.markerTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.markerTypePropertyName, $.ig.MarkerType.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.MarkerType.prototype.getBox($.ig.MarkerType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.markerTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.markerTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.markerTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.markerTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.markerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.markerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.markerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.markerOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.markerOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.markerOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersPropertyName, $.ig.Number.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, 400, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.maximumMarkersPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathPropertyName, String, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.radiusMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.radiusScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.radiusScalePropertyName, $.ig.SizeScale.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.radiusScalePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathPropertyName, String, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.labelMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathPropertyName, String, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.fillMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicProportionalSymbolSeries.prototype.fillScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicProportionalSymbolSeries.prototype.fillScalePropertyName, $.ig.BrushScale.prototype.$type, $.ig.GeographicProportionalSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicProportionalSymbolSeries.prototype.fillScalePropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathPropertyName = "LongitudeMemberPath";
$.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathPropertyName = "LatitudeMemberPath";
$.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourcePropertyName = "TrianglesSource";
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1PropertyName = "TriangleVertexMemberPath1";
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2PropertyName = "TriangleVertexMemberPath2";
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3PropertyName = "TriangleVertexMemberPath3";
$.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathPropertyName, String, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, "pointX", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.longitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathPropertyName, String, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, "pointY", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.latitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourcePropertyName, $.ig.IEnumerable.prototype.$type, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.trianglesSourcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1Property = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1PropertyName, String, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, "v1", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath1PropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2Property = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2PropertyName, String, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, "v2", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath2PropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3Property = $.ig.DependencyProperty.prototype.register($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3PropertyName, String, $.ig.GeographicXYTriangulatingSeries.prototype.$type, new $.ig.PropertyMetadata(2, "v3", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicXYTriangulatingSeries.prototype.triangleVertexMemberPath3PropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicScatterAreaSeries.prototype.colorMemberPathPropertyName = "ColorMemberPath";
$.ig.GeographicScatterAreaSeries.prototype._colorScalePropertyName = "ColorScale";
$.ig.GeographicScatterAreaSeries.prototype.colorMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicScatterAreaSeries.prototype.colorMemberPathPropertyName, String, $.ig.GeographicScatterAreaSeries.prototype.$type, new $.ig.PropertyMetadata(2, "value", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicScatterAreaSeries.prototype.colorMemberPathPropertyName, e.oldValue(), e.newValue());
}));

$.ig.ContourLineSeries.prototype._valueMemberPathPropertyName = "ValueMemberPath";
$.ig.ContourLineSeries.prototype._valueColumnPropertyName = "ValueColumn";
$.ig.ContourLineSeries.prototype._fillScalePropertyName = "FillScale";
$.ig.ContourLineSeries.prototype._valueResolverPropertyName = "ValueResolver";
$.ig.ContourLineSeries.prototype.valueMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ContourLineSeries.prototype._valueMemberPathPropertyName, String, $.ig.ContourLineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ContourLineSeries.prototype._valueMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ContourLineSeries.prototype.fillScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.ContourLineSeries.prototype._fillScalePropertyName, $.ig.ValueBrushScale.prototype.$type, $.ig.ContourLineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ContourLineSeries.prototype._fillScalePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ContourBuilder.prototype._hASH_CONSTANT = 4294967296;

$.ig.GeographicContourLineSeries.prototype._valueMemberPathPropertyName = "ValueMemberPath";
$.ig.GeographicContourLineSeries.prototype._fillScalePropertyName = "FillScale";
$.ig.GeographicContourLineSeries.prototype._valueResolverPropertyName = "ValueResolver";
$.ig.GeographicContourLineSeries.prototype.valueMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicContourLineSeries.prototype._valueMemberPathPropertyName, String, $.ig.GeographicContourLineSeries.prototype.$type, new $.ig.PropertyMetadata(2, "value", function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicContourLineSeries.prototype._valueMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicContourLineSeries.prototype.fillScaleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicContourLineSeries.prototype._fillScalePropertyName, $.ig.ValueBrushScale.prototype.$type, $.ig.GeographicContourLineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicContourLineSeries.prototype._fillScalePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicContourLineSeries.prototype.valueResolverProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicContourLineSeries.prototype._valueResolverPropertyName, $.ig.ContourValueResolver.prototype.$type, $.ig.GeographicContourLineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicContourLineSeries.prototype._valueResolverPropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicShapeSeries.prototype.shapeStyleSelectorPropertyName = "ShapeStyleSelector";
$.ig.GeographicShapeSeries.prototype.shapeStylePropertyName = "ShapeStyle";
$.ig.GeographicShapeSeries.prototype.markerTypePropertyName = "MarkerType";
$.ig.GeographicShapeSeries.prototype.markerTemplatePropertyName = "MarkerTemplate";
$.ig.GeographicShapeSeries.prototype.markerBrushPropertyName = "MarkerBrush";
$.ig.GeographicShapeSeries.prototype.markerOutlinePropertyName = "MarkerOutline";
$.ig.GeographicShapeSeries.prototype.markerStylePropertyName = "MarkerStyle";
$.ig.GeographicShapeSeries.prototype.markerCollisionAvoidancePropertyName = "MarkerCollisionAvoidance";
$.ig.GeographicShapeSeries.prototype.shapeStyleSelectorProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.shapeStyleSelectorPropertyName, $.ig.StyleSelector.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.shapeStyleSelectorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.shapeStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.shapeStylePropertyName, $.ig.Style.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.shapeStylePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerTypePropertyName, $.ig.MarkerType.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.MarkerType.prototype.getBox($.ig.MarkerType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerStylePropertyName, $.ig.Style.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerStylePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicShapeSeries.prototype.markerCollisionAvoidanceProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicShapeSeries.prototype.markerCollisionAvoidancePropertyName, $.ig.CollisionAvoidanceType.prototype.$type, $.ig.GeographicShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.CollisionAvoidanceType.prototype.getBox($.ig.CollisionAvoidanceType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicShapeSeries.prototype.markerCollisionAvoidancePropertyName, e.oldValue(), e.newValue());
}));

$.ig.LinearContourValueResolver.prototype._valueCountPropertyName = "ValueCount";
$.ig.LinearContourValueResolver.prototype.valueCountProperty = $.ig.DependencyProperty.prototype.register($.ig.LinearContourValueResolver.prototype._valueCountPropertyName, $.ig.Number.prototype.$type, $.ig.LinearContourValueResolver.prototype.$type, new $.ig.PropertyMetadata(2, 10, function (sender, e) {
	(sender).propertyUpdated($.ig.LinearContourValueResolver.prototype._valueCountPropertyName, e.oldValue(), e.newValue());
}));

$.ig.PolylineSeries.prototype._shapeStyleSelectorPropertyName = "ShapeStyleSelector";
$.ig.PolylineSeries.prototype._shapeStylePropertyName = "ShapeStyle";
$.ig.PolylineSeries.prototype.shapeStyleSelectorProperty = $.ig.DependencyProperty.prototype.register($.ig.PolylineSeries.prototype._shapeStyleSelectorPropertyName, $.ig.StyleSelector.prototype.$type, $.ig.PolylineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.PolylineSeries.prototype._shapeStyleSelectorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.PolylineSeries.prototype.shapeStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.PolylineSeries.prototype._shapeStylePropertyName, $.ig.Style.prototype.$type, $.ig.PolylineSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.PolylineSeries.prototype._shapeStylePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ScatterAreaSeries.prototype._colorScalePropertyName = "ColorScale";
$.ig.ScatterAreaSeries.prototype._colorMemberPathPropertyName = "ColorMemberPath";
$.ig.ScatterAreaSeries.prototype._colorColumnPropertyName = "ColorColumn";
$.ig.ScatterAreaSeries.prototype.colorMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.ScatterAreaSeries.prototype._colorMemberPathPropertyName, String, $.ig.ScatterAreaSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ScatterAreaSeries.prototype._colorMemberPathPropertyName, e.oldValue(), e.newValue());
}));

$.ig.ShapeSeries.prototype._shapeStyleSelectorPropertyName = "ShapeStyleSelector";
$.ig.ShapeSeries.prototype._shapeStylePropertyName = "ShapeStyle";
$.ig.ShapeSeries.prototype.markerTypePropertyName = "MarkerType";
$.ig.ShapeSeries.prototype.markerTemplatePropertyName = "MarkerTemplate";
$.ig.ShapeSeries.prototype.actualMarkerTemplatePropertyName = "ActualMarkerTemplate";
$.ig.ShapeSeries.prototype.markerBrushPropertyName = "MarkerBrush";
$.ig.ShapeSeries.prototype.actualMarkerBrushPropertyName = "ActualMarkerBrush";
$.ig.ShapeSeries.prototype.markerOutlinePropertyName = "MarkerOutline";
$.ig.ShapeSeries.prototype.actualMarkerOutlinePropertyName = "ActualMarkerOutline";
$.ig.ShapeSeries.prototype.markerStylePropertyName = "MarkerStyle";
$.ig.ShapeSeries.prototype.markerCollisionAvoidancePropertyName = "MarkerCollisionAvoidance";
$.ig.ShapeSeries.prototype._unitRect = new $.ig.Rect(0, 0, 0, 1, 1);
$.ig.ShapeSeries.prototype.shapeStyleSelectorProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype._shapeStyleSelectorPropertyName, $.ig.StyleSelector.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype._shapeStyleSelectorPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.shapeStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype._shapeStylePropertyName, $.ig.Style.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype._shapeStylePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerTypePropertyName, $.ig.MarkerType.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.MarkerType.prototype.getBox($.ig.MarkerType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.markerTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.markerTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.actualMarkerTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.actualMarkerTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.actualMarkerTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.markerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.actualMarkerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.actualMarkerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.actualMarkerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.markerOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.actualMarkerOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.actualMarkerOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.actualMarkerOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerStyleProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerStylePropertyName, $.ig.Style.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.ShapeSeries.prototype.markerStylePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapeSeries.prototype.markerCollisionAvoidanceProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapeSeries.prototype.markerCollisionAvoidancePropertyName, $.ig.CollisionAvoidanceType.prototype.$type, $.ig.ShapeSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.CollisionAvoidanceType.prototype.getBox($.ig.CollisionAvoidanceType.prototype.none), function (sender, e) {
	($.ig.util.cast($.ig.ShapeSeries.prototype.$type, sender)).raisePropertyChanged($.ig.ShapeSeries.prototype.markerCollisionAvoidancePropertyName, e.oldValue(), e.newValue());
}));

$.ig.GeographicSymbolSeries.prototype.latitudeMemberPathPropertyName = "LatitudeMemberPath";
$.ig.GeographicSymbolSeries.prototype.longitudeMemberPathPropertyName = "LongitudeMemberPath";
$.ig.GeographicSymbolSeries.prototype.markerTypePropertyName = "MarkerType";
$.ig.GeographicSymbolSeries.prototype.markerTemplatePropertyName = "MarkerTemplate";
$.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidancePropertyName = "MarkerCollisionAvoidance";
$.ig.GeographicSymbolSeries.prototype.markerBrushPropertyName = "MarkerBrush";
$.ig.GeographicSymbolSeries.prototype.markerOutlinePropertyName = "MarkerOutline";
$.ig.GeographicSymbolSeries.prototype.maximumMarkersPropertyName = "MaximumMarkers";
$.ig.GeographicSymbolSeries.prototype.latitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.latitudeMemberPathPropertyName, String, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.latitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.longitudeMemberPathProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.longitudeMemberPathPropertyName, String, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.longitudeMemberPathPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.markerTypeProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.markerTypePropertyName, $.ig.MarkerType.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.MarkerType.prototype.getBox($.ig.MarkerType.prototype.automatic), function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.markerTypePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.markerTemplateProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.markerTemplatePropertyName, $.ig.DataTemplate.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.markerTemplatePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidanceProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidancePropertyName, $.ig.CollisionAvoidanceType.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, $.ig.CollisionAvoidanceType.prototype.getBox($.ig.CollisionAvoidanceType.prototype.none), function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.markerCollisionAvoidancePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.markerBrushProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.markerBrushPropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.markerBrushPropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.markerOutlineProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.markerOutlinePropertyName, $.ig.Brush.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(1, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.markerOutlinePropertyName, e.oldValue(), e.newValue());
}));
$.ig.GeographicSymbolSeries.prototype.maximumMarkersProperty = $.ig.DependencyProperty.prototype.register($.ig.GeographicSymbolSeries.prototype.maximumMarkersPropertyName, $.ig.Number.prototype.$type, $.ig.GeographicSymbolSeries.prototype.$type, new $.ig.PropertyMetadata(2, 400, function (sender, e) {
	(sender).raisePropertyChanged($.ig.GeographicSymbolSeries.prototype.maximumMarkersPropertyName, e.oldValue(), e.newValue());
}));

$.ig.ShapefileConverter.prototype._worldRectPropertyName = "WorldRect";
$.ig.ShapefileConverter.prototype._shapefileSourcePropertyName = "ShapefileSource";
$.ig.ShapefileConverter.prototype._databaseSourcePropertyName = "DatabaseSource";
$.ig.ShapefileConverter.prototype.shapefileSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapefileConverter.prototype._shapefileSourcePropertyName, $.ig.Uri.prototype.$type, $.ig.ShapefileConverter.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapefileConverter.prototype.$type, sender)).propertyUpdated($.ig.ShapefileConverter.prototype._shapefileSourcePropertyName, e.oldValue(), e.newValue());
}));
$.ig.ShapefileConverter.prototype.databaseSourceProperty = $.ig.DependencyProperty.prototype.register($.ig.ShapefileConverter.prototype._databaseSourcePropertyName, $.ig.Uri.prototype.$type, $.ig.ShapefileConverter.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ShapefileConverter.prototype.$type, sender)).propertyUpdated($.ig.ShapefileConverter.prototype._databaseSourcePropertyName, e.oldValue(), e.newValue());
}));

$.ig.ShapeFileUtil.prototype._dbfBaseDataTypes = null;
if ($.ig.ShapeFileUtil.prototype.staticInit && !$.ig.ShapeFileUtil.prototype.shapeFileUtilStaticInitCalled) { $.ig.ShapeFileUtil.prototype.staticInit(); $.ig.ShapeFileUtil.prototype.shapeFileUtilStaticInitCalled = true; }

$.ig.ItfConverter.prototype._sourcePropertyName = "Source";
$.ig.ItfConverter.prototype._triangulationSourcePropertyName = "TriangulationSource";
$.ig.ItfConverter.prototype.sourceProperty = $.ig.DependencyProperty.prototype.register($.ig.ItfConverter.prototype._sourcePropertyName, $.ig.Uri.prototype.$type, $.ig.ItfConverter.prototype.$type, new $.ig.PropertyMetadata(2, null, function (sender, e) {
	($.ig.util.cast($.ig.ItfConverter.prototype.$type, sender)).propertyUpdated($.ig.ItfConverter.prototype._sourcePropertyName, e.oldValue(), e.newValue());
}));

} (jQuery));


