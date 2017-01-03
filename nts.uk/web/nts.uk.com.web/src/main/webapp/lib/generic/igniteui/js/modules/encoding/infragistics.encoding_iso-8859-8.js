/*!@license
* Infragistics.Web.ClientUI infragistics.encoding_iso-8859-8.js 16.1.20161.2145
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
"Array:bo", 
"GenericEnumerable$1:cj", 
"GenericEnumerator$1:ck"]);


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
"SingleByteEncoding:cl", 
"RuntimeHelpers:co", 
"RuntimeFieldHandle:cp", 
"Iso8859Dash8:c2", 
"AbstractEnumerable:dj", 
"Func$1:dk", 
"AbstractEnumerator:dl", 
"GenericEnumerable$1:dm", 
"GenericEnumerator$1:dn"]);


$.ig.util.defType('SingleByteEncoding', 'Encoding', {
	_reverseCodePage: null,
	_codePageLayout: null,
	__codePage: 0,
	__name: null,
	codePageLayout: function () {
	}
	,
	init: function (initNumber, codePage) {
		if (initNumber > 0) {
			switch (initNumber) {
				case 1:
					this.init1.apply(this, arguments);
					break;
			}
			return;
		}
		$.ig.Encoding.prototype.init.call(this);
		this.setCodePage(codePage);
	},
	init1: function (initNumber, codePage, name) {
		$.ig.Encoding.prototype.init.call(this);
		this.setCodePage(codePage);
		this.__name = name;
	},
	setCodePage: function (codePage) {
		this.__codePage = codePage;
		this._codePageLayout = this.codePageLayout();
		if (this._codePageLayout == null) {
			return;
		}
		this._reverseCodePage = new $.ig.Dictionary$2($.ig.String.prototype.$type, $.ig.Number.prototype.$type, 0);
		for (var i = 0; i < this._codePageLayout.length; i++) {
			var c = this._codePageLayout[i];
			if (c != '\uffff') {
				this._reverseCodePage.add(c, i);
			}
		}
	}
	,
	fallbackCharacter: function () {
		return $.ig.SingleByteEncoding.prototype._questionMark;
	}
	,
	codePage: function () {
		return this.__codePage;
	}
	,
	name: function () {
		return this.__name;
	}
	,
	getByteCount: function (chars, index, count) {
		return count;
	}
	,
	getBytes2: function (chars, charIndex, charCount, bytes, byteIndex) {
		for (var i = charIndex; i < charIndex + charCount; i++) {
			if (this._reverseCodePage.containsKey(chars[i])) {
				bytes[byteIndex + i - charIndex] = this._reverseCodePage.item(chars[i]);
			} else {
				bytes[byteIndex + i - charIndex] = this.getBytes1(this.fallbackCharacter().toString())[0];
			}
		}
		return charCount;
	}
	,
	getString1: function (bytes, index, count) {
		var layout = this._codePageLayout;
		var sb = new $.ig.StringBuilder(0);
		for (var i = index; i < index + count; i++) {
			if (layout[bytes[i]] != '\uffff') {
				sb.append1(layout[bytes[i]]);
			}
		}
		return sb.toString();
	}
	,
	$type: new $.ig.Type('SingleByteEncoding', $.ig.Encoding.prototype.$type, [$.ig.IEncoding.prototype.$type])
}, true);

$.ig.util.defType('Iso8859Dash8', 'SingleByteEncoding', {
	__codePageLayout: null,
	codePageLayout: function () {
		return this.__codePageLayout;
	}
	,
	init: function () {
		this.__codePageLayout = [ '\0', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\b', '\t', '\n', '\v', '\f', '\r', '\u000e', '\u000f', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001a', '\u001b', '\u001c', '\u001d', '\u001e', '\u001f', ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '\u007f', '\u0080', '\u0081', '\u0082', '\u0083', '\u0084', '\u0085', '\u0086', '\u0087', '\u0088', '\u0089', '\u008a', '\u008b', '\u008c', '\u008d', '\u008e', '\u008f', '\u0090', '\u0091', '\u0092', '\u0093', '\u0094', '\u0095', '\u0096', '\u0097', '\u0098', '\u0099', '\u009a', '\u009b', '\u009c', '\u009d', '\u009e', '\u009f', ' ', '', '¢', '£', '¤', '¥', '¦', '§', '¨', '©', '×', '«', '¬', '­', '®', '‾', '°', '±', '²', '³', '´', 'µ', '¶', '·', '¸', '¹', '÷', '»', '¼', '½', '¾', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '‗', 'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'ך', 'כ', 'ל', 'ם', 'מ', 'ן', 'נ', 'ס', 'ע', 'ף', 'פ', 'ץ', 'צ', 'ק', 'ר', 'ש', 'ת', '', '', '', '', '' ];
		$.ig.SingleByteEncoding.prototype.init1.call(this, 1, 28598, "iso-8859-8");
	},
	$type: new $.ig.Type('Iso8859Dash8', $.ig.SingleByteEncoding.prototype.$type)
}, true);

$.ig.SingleByteEncoding.prototype._questionMark = '?';

} (jQuery));


