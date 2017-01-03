/*!@license
* Infragistics.Web.ClientUI Barcode localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.Barcode) {
	    $.ig.Barcode = {
		    locale: {
			    aILength: "AI は 2 つのディジット以上にする必要があります。",
			    badFormedUCCValue: "UCC バーコードの Data プロパティが有効な値に設定されていません。(AI)GTIN の書式に設定してください。",
			    code39_NonNumericError: "文字 '{0}' は CODE39 の Data プロパティで無効です。有効な文字は {1} です。",
			    countryError: "国コードの変換に失敗しました。数値にする必要があります。",
			    emptyValueMsg: "Data 値は空です。",
			    encodingError: "変換でエラーが発生しました。有効なプロパティ値についてはヘルプを参照してください。",
			    errorMessageText: "無効な値です。ヘルプを参照して、バーコードの有効な Data 値を確認してください。",
			    gS1ExMaxAlphanumNumber: "GS1 DataBar Expanded のバーコードは 41 英数字以下の文字をエンコードできます。",
			    gS1ExMaxNumericNumber: "GS1 DataBar Expanded のバーコードは 74 数字以下の文字をエンコードできます。",
			    gS1Length: "GS1 DataBar の Data プロパティは GTIN のために使用されます。長さは 7、11、12、または 13 に設定する必要があります。最後の数字はチェックサムです。",
			    gS1LimitedFirstChar: "GS1 DataBar Limited のバーコードの最初の数字は 0 または 1 に設定する必要があります。1 より大きい Indicator 値を持つ GTIN-14 データをエンコードするときに、バーコードのタイプは Omnidirectional、Stacked、Stacked Omnidirectional、または Truncated に設定する必要があります。",
			    i25Length: "Interleaved2of5 バーコードは偶数の数字を持つ必要があります。奇数の場合、0 を数字の最初に追加します。",
			    intelligentMailLength: "Intelligent Mail バーコードの Data プロパティの長さは 20、25、29、または 31 桁に設定する必要があります。トラック コードは 20 桁 (バーコードの ID は 2 数字、サービス タイプの ID は 3 数字、メーラーの ID は 6 または 9 数字、シリアル数は 9 または 6 数字) に設定して、郵便番号は 0、5、9、または 11 桁に設定します。",
			    intelligentMailSecondDigit: "2 番目の数字は 0～4 の範囲に設定する必要があります。",
			    invalidAI: "アプリケーション識別子要素の文字列は無効です。データの AI 文字列を有効な文字列に設定してください。",
			    invalidCharacter: "'{0}' の文字が現在のバーコードのタイプで無効です。有効な文字は {1} です。",
			    invalidDimension: "Stretch、BarsFillMode と XDimension のプロパティ値の組み合わせが無効なため、バーコードの寸法は確定できません。",
			    invalidHeight: "バーコード グリッドの {0} 行は {1} ピクセルの高さに設定できません。",
			    invalidLength: "バーコードの Data プロパティは、{0} 桁である必要があります。",
			    invalidPostalCode: "無効な PostalCode 値 - モード 2 は、5 桁または 9 桁の米国の郵便番号をエンコードします。モード 3 は 6 文字 (英数字) コード以下をエンコードします。",
			    invalidPropertyValue: "{0} プロパティ値は {1} ～ {2} の範囲内に設定する必要があります。",
			    invalidVersion: "現在のエンコード モードおよびエラーの修正レベルでは、SizeVersion 数はデータをエンコードするために十分なセルを生成しません。",
			    invalidWidth: "バーコード グリッドの {0} 列は {1} ピクセルの幅に設定できません。XDimension と WidthToHeightRatio の両方、またはそのいずれかのプロパティ値をチェックしてください。",
			    invalidXDimensionValue: "現在のバーコード タイプは XDimension 値が {0} から {1} の範囲内である必要があります。",
			    maxLength: "{0} は現在のバーコード タイプでエンコードできる最大長を超えています。エンコード可能な最大長は {1} 文字です。",
			    notSupportedEncoding: "{0} {1} に対するエンコード化はサポートされていません。",
			    pDF417InvalidRowsColumnsCombination: "(Data およびエラー修正の) コードワードの長さが行列 {0} x {1} の記号でエンコード可能な最大値を超えています。",
			    primaryMessageError: "最初のメッセージ データを Data 値から取得できません。構造についてはヘルプを参照してください。",
			    serviceClassError: "サービス クラスの変換に失敗しました。数値にする必要があります。",
			    smallSize: "定義された Stretch 設定の場合、グリッドを Size({0}, {1}) に合わせません。",
			    unencodableCharacter: "'{0}' の文字はエンコードできません。",
			    uPCEFirstDigit: "UPCE の最初の数字は 0 に設定する必要があります。",
			    warningString: "Barcode 警告: ",
			    wrongCompactionMode: "Data メッセージを {0} モードで圧縮することはできません。",
			    notLoadedEncoding: "{0} エンコードは読み込まれません。"
		    }
	    };
    }
})(jQuery);

/*!@license
* Infragistics.Web.ClientUI Barcode 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*     jquery-1.8.3.js
*     jquery.ui.core.js
*     jquery.ui.widget.js
*     infragistics.util.js
*     infragistics.dv.simple.core.js
*     infragistics.barcode_qrcodebarcode.js
*/

/*global jQuery */
if (typeof (jQuery) === "undefined") {
       throw new Error("jQuery is undefined");
}

(function ($) {
    // igQRCodeBarcode definition
       $.widget('ui.igQRCodeBarcode', {
        css: {
           /* Class applied to main element, shown when the QRCodeBarcode is opened in a non HTML5 compatible browser. */
           unsupportedBrowserClass: "ui-html5-non-html5-supported-message ui-helper-clearfix ui-html5-non-html5",
		   /* Class applied to main element: ui-barcode ui-corner-all ui-widget-content */
           barcode: "ui-barcode",
           /* Class applying background-color and border-color to the backing element */
           barcodeBacking: "ui-barcode-backing",
           /* Class applying background-color to the bar elements */
           barcodeBar: "ui-barcode-bar",
           /* Class applying background-color to the text elements */
           barcodeLabel: "ui-barcode-label"
		},

		events: {
					/* cancel="false" Occurs when an error has happened.
	Function takes first argument evt and second argument ui.
	Use ui.owner to obtain reference to the barcode widget.
	Use ui.errorMessage to get or set the error message that is to be shown.
	*/
	errorMessageDisplaying: null,
	/* cancel="false" Occurs when the data has changed.
	Function takes first argument evt and second argument ui.
	Use ui.owner to obtain reference to the barcode widget.
	Use ui.newData to obtain the new data.
	*/
	dataChanged: null

		},
		options: {
			/* type="string|number" The width of the barcode. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number
			*/
			width: null,
			/* type="string|number" The height of the barcode. It can be set as a number in pixels, string (px) or percentage (%).
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number
			*/
			height: null,
			  		/* type="string" Gets or sets the brush to use to fill the backing of the barcode.
	*/
	backingBrush: "white",
	/* type="string" Gets or sets the brush to use for the outline of the backing.
	*/
	backingOutline: "transparent",
	/* type="number" Gets or sets the stroke thickness of the backing outline.
	*/
	backingStrokeThickness: 0,
	/* type="string" Gets or sets the brush to use to fill the background of the bars.
	*/
	barBrush: "black",
	/* type="string" Gets or sets the brush to use for the label font.
	*/
	fontBrush: null,
	/* type="string" Gets or sets the font of the text displayed by the control.
	*/
	font: null,
	/* type="string" Gets or sets the data value.
	*/
	data: "",
	/* type="string" Gets or sets the message text displayed when some error occurs.
	*/
	errorMessageText: "",
	/* type="none|fill|uniform|uniformToFill" Gets or sets the stretch.
	none type="string" 
	fill type="string" 
	uniform type="string" 
	uniformToFill type="string" 
	*/
	stretch: "uniform",
	/* type="fillSpace|ensureEqualSize" Gets or sets a value which specifies how the grid fills within the barcode control dimensions.
	fillSpace type="string" FillSpace mode ensures that the barcode grid fills the control dimensions.
	ensureEqualSize type="string" EnsureEqualSize mode ensures that every grid column/row has the same pixels number width/height. The sum of all columns/rows pixels may be less than the width/height of the control.
	*/
	barsFillMode: "fillSpace",
	/* type="number" Gets or sets the width (XDimension) to height (YDimension) ratio. It accepts only positive values. This property does not apply for the QR barcode.
	*/
	widthToHeightRatio: 3,
	/* type="number" Gets or sets the X-dimension (narrow element width) for a symbol in mm. It accepts values from 0.01 to 100.
	*/
	xDimension: 0.25,
	/* type="low|medium|quartil|high" Gets or sets the error correction level of the QR Code symbol.
	low type="string" Low error correction level allows recovery of 7% of the symbol codewords.
	medium type="string" Medium error correction level allows recovery of 15% of the symbol codewords.
	quartil type="string" Quartil error correction level allows recovery of 25% of the symbol codewords.
	high type="string" High error correction level allows recovery of 30% of the symbol codewords.
	*/
	errorCorrectionLevel: "medium",
	/* type="undefined|version1|version2|version3|version4|version5|version6|version7|version8|version9|version10|version11|version12|version13|version14|version15|version16|version17|version18|version19|version20|version21|version22|version23|version24|version25|version26|version27|version28|version29|version30|version31|version32|version33|version34|version35|version36|version37|version38|version39|version40" Gets or sets the size version of the QR Code symbol.
	undefined type="string" If set, the QR code barcode sets internally the smallest version that will accommodate the data.
	version1 type="string" Version1 defines size of 21x21 modules for the symbol.
	version2 type="string" Version2 defines size of 25x25 modules for the symbol.
	version3 type="string" Version3 defines size of 29x29 modules for the symbol.
	version4 type="string" Version4 defines size of 33x33 modules for the symbol.
	version5 type="string" Version5 defines size of 37x37 modules for the symbol.
	version6 type="string" Version6 defines size of 41x41 modules for the symbol.
	version7 type="string" Version7 defines size of 45x45 modules for the symbol.
	version8 type="string" Version8 defines size of 49x49 modules for the symbol.
	version9 type="string" Version9 defines size of 53x53 modules for the symbol.
	version10 type="string" Version10 defines size of 57x57 modules for the symbol.
	version11 type="string" Version11 defines size of 61x61 modules for the symbol.
	version12 type="string" Version12 defines size of 65x65 modules for the symbol.
	version13 type="string" Version13 defines size of 69x69 modules for the symbol.
	version14 type="string" Version14 defines size of 73x73 modules for the symbol.
	version15 type="string" Version15 defines size of 77x77 modules for the symbol.
	version16 type="string" Version16 defines size of 81x81 modules for the symbol.
	version17 type="string" Version17 defines size of 85x85 modules for the symbol.
	version18 type="string" Version18 defines size of 89x89 modules for the symbol.
	version19 type="string" Version19 defines size of 93x93 modules for the symbol.
	version20 type="string" Version20 defines size of 97x97 modules for the symbol.
	version21 type="string" Version21 defines size of 101x101 modules for the symbol.
	version22 type="string" Version22 defines size of 105x105 modules for the symbol.
	version23 type="string" Version23 defines size of 109x109 modules for the symbol.
	version24 type="string" Version24 defines size of 113x113 modules for the symbol.
	version25 type="string" Version25 defines size of 117x117 modules for the symbol.
	version26 type="string" Version26 defines size of 121x121 modules for the symbol.
	version27 type="string" Version27 defines size of 125x125 modules for the symbol.
	version28 type="string" Version28 defines size of 129x129 modules for the symbol.
	version29 type="string" Version29 defines size of 133x133 modules for the symbol.
	version30 type="string" Version30 defines size of 137x137 modules for the symbol.
	version31 type="string" Version31 defines size of 141x141 modules for the symbol.
	version32 type="string" Version32 defines size of 145x145 modules for the symbol.
	version33 type="string" Version33 defines size of 149x149 modules for the symbol.
	version34 type="string" Version34 defines size of 153x153 modules for the symbol.
	version35 type="string" Version35 defines size of 157x157 modules for the symbol.
	version36 type="string" Version36 defines size of 161x161 modules for the symbol.
	version37 type="string" Version37 defines size of 165x165 modules for the symbol.
	version38 type="string" Version38 defines size of 169x169 modules for the symbol.
	version39 type="string" Version39 defines size of 173x173 modules for the symbol.
	version40 type="string" Version40 defines size of 177x177 modules for the symbol.
	*/
	sizeVersion: "undefined",
	/* type="undefined|numeric|alphanumeric|byte|kanji" Gets or sets the encoding mode for compaction of the QR Code symbol data. The default value is undefined if the Shift_JIS encoding is loaded. Otherwise the default value is byte.
	undefined type="string" When Undefined encoding mode is set, the QR code barcode internally switches between modes as necessary in order to achieve the most efficient conversion of data into a binary string.
	numeric type="string" Numeric mode encodes data from decimal digit set (0-9). Normally 3 data characters are represented by 10 bits.
	alphanumeric type="string" Alphanumerc mode encodes data from a set of 45 characters (digits 0-9, upper case letters A-Z, nine other characters: space, $ % * + _ . / : ). Normally two input characters are represented by 11 bits.
	byte type="string" In Byte mode the data is encoded at 8 bits per character. The character set of the Byte encoding mode is byte data (by default it is ISO/IEC 8859-1 character set).
	kanji type="string" The Kanji mode efficiently encodes Kanji characters in accordance with the Shift JIS system based on JIS X 0208. Each two-byte character value is compactedd to a 13-bit binary codeword.
	*/
	encodingMode: "undefined",
	/* type="number" Each Extended Channel Interpretation (ECI) is designated by a six-digit assignment number: 000000 - 999999. 
	The default value depends on the loaded encodings. The default is ECI 000003 (representing ISO/IEC 8859-1) if the ISO/IEC 8859-1 character set is loaded. Otherwise the default value is 000026 (representing UTF-8).
	*/
	eciNumber: 3,
	/* type="hide|show" Gets or sets a value indicating whether to show the ECI header.
	hide type="string" Hide the header.
	show type="string" Show the header.
	*/
	eciHeaderDisplayMode: "hide",
	/* type="none|gs1|industry" Gets or sets the FNC1 mode indicator which identifies symbols encoding messages formatted according to specific predefined industry or application specificatoins.
	none type="string" Do not use any Fnc1 symbols, i.e. the data is not identified according to specific predefined industry or application specifications.
	gs1 type="string" Uses Fnc1 symbol in the first position of the character in Code 128 symbols and designates data formatted in accordance with the GS1 General Specification.
	industry type="string" Uses Fnc1 symbol in the second position of the character in Code 128 symbols and designates data formatted in accordance with a specific indystry application previously agreed with AIM Inc.
	*/
	fnc1Mode: "none",
	/* type="string" Gets or sets the Application Indicator assigned to identify the specification concerned by AIM International.
	The value is respected only when the Fnc1Mode is set to Industry. Its value may take the form of any single Latin alphabetic character from the set {a - z, A - Z} or a two-digit number.
	*/
	applicationIndicator: ""

			},
           				
		_setOption: function (key, value, checkPrev) {
                     var qRCodeBarcode = this._qRCodeBarcode, o = this.options;
                     if (checkPrev && o[key] === value) {
                           return;
                     }
                     $.Widget.prototype._setOption.apply(this, arguments);

					 if (this._set_option(qRCodeBarcode, key, value)) {
						return this;
					 }

					this._set_generated_option(qRCodeBarcode, key, value);
					 
					return this;
				},

		_set_generated_option: function (qRCodeBarcode, key, value) {
							switch (key) {
		case "backingBrush":
			if (value == null) {
				qRCodeBarcode.backingBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				qRCodeBarcode.backingBrush($tempBrush);
			}
			return true;
		case "backingOutline":
			if (value == null) {
				qRCodeBarcode.backingOutline(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				qRCodeBarcode.backingOutline($tempBrush);
			}
			return true;
		case "backingStrokeThickness":
			qRCodeBarcode.backingStrokeThickness(value);
			return true;
		case "barBrush":
			if (value == null) {
				qRCodeBarcode.barBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				qRCodeBarcode.barBrush($tempBrush);
			}
			return true;
		case "fontBrush":
			if (value == null) {
				qRCodeBarcode.fontBrush(null);
			} else {
				var $tempBrush = $.ig.Brush.prototype.create(value);
				qRCodeBarcode.fontBrush($tempBrush);
			}
			return true;
		case "font":
			qRCodeBarcode.font(value);
			return true;
		case "data":
			qRCodeBarcode.data(value);
			return true;
		case "errorMessageText":
			qRCodeBarcode.errorMessageText(value);
			return true;
		case "stretch":
			switch(value) {
				case "none":
					qRCodeBarcode.stretch(0);
					break;
				case "fill":
					qRCodeBarcode.stretch(1);
					break;
				case "uniform":
					qRCodeBarcode.stretch(2);
					break;
				case "uniformToFill":
					qRCodeBarcode.stretch(3);
					break;
			}
			return true;
		case "barsFillMode":
			switch(value) {
				case "fillSpace":
					qRCodeBarcode.barsFillMode(0);
					break;
				case "ensureEqualSize":
					qRCodeBarcode.barsFillMode(1);
					break;
			}
			return true;
		case "widthToHeightRatio":
			qRCodeBarcode.widthToHeightRatio(value);
			return true;
		case "xDimension":
			qRCodeBarcode.xDimension(value);
			return true;
		case "errorCorrectionLevel":
			switch(value) {
				case "low":
					qRCodeBarcode.errorCorrectionLevel(1);
					break;
				case "medium":
					qRCodeBarcode.errorCorrectionLevel(0);
					break;
				case "quartil":
					qRCodeBarcode.errorCorrectionLevel(3);
					break;
				case "high":
					qRCodeBarcode.errorCorrectionLevel(2);
					break;
			}
			return true;
		case "sizeVersion":
			switch(value) {
				case "undefined":
					qRCodeBarcode.sizeVersion(0);
					break;
				case "version1":
					qRCodeBarcode.sizeVersion(1);
					break;
				case "version2":
					qRCodeBarcode.sizeVersion(2);
					break;
				case "version3":
					qRCodeBarcode.sizeVersion(3);
					break;
				case "version4":
					qRCodeBarcode.sizeVersion(4);
					break;
				case "version5":
					qRCodeBarcode.sizeVersion(5);
					break;
				case "version6":
					qRCodeBarcode.sizeVersion(6);
					break;
				case "version7":
					qRCodeBarcode.sizeVersion(7);
					break;
				case "version8":
					qRCodeBarcode.sizeVersion(8);
					break;
				case "version9":
					qRCodeBarcode.sizeVersion(9);
					break;
				case "version10":
					qRCodeBarcode.sizeVersion(10);
					break;
				case "version11":
					qRCodeBarcode.sizeVersion(11);
					break;
				case "version12":
					qRCodeBarcode.sizeVersion(12);
					break;
				case "version13":
					qRCodeBarcode.sizeVersion(13);
					break;
				case "version14":
					qRCodeBarcode.sizeVersion(14);
					break;
				case "version15":
					qRCodeBarcode.sizeVersion(15);
					break;
				case "version16":
					qRCodeBarcode.sizeVersion(16);
					break;
				case "version17":
					qRCodeBarcode.sizeVersion(17);
					break;
				case "version18":
					qRCodeBarcode.sizeVersion(18);
					break;
				case "version19":
					qRCodeBarcode.sizeVersion(19);
					break;
				case "version20":
					qRCodeBarcode.sizeVersion(20);
					break;
				case "version21":
					qRCodeBarcode.sizeVersion(21);
					break;
				case "version22":
					qRCodeBarcode.sizeVersion(22);
					break;
				case "version23":
					qRCodeBarcode.sizeVersion(23);
					break;
				case "version24":
					qRCodeBarcode.sizeVersion(24);
					break;
				case "version25":
					qRCodeBarcode.sizeVersion(25);
					break;
				case "version26":
					qRCodeBarcode.sizeVersion(26);
					break;
				case "version27":
					qRCodeBarcode.sizeVersion(27);
					break;
				case "version28":
					qRCodeBarcode.sizeVersion(28);
					break;
				case "version29":
					qRCodeBarcode.sizeVersion(29);
					break;
				case "version30":
					qRCodeBarcode.sizeVersion(30);
					break;
				case "version31":
					qRCodeBarcode.sizeVersion(31);
					break;
				case "version32":
					qRCodeBarcode.sizeVersion(32);
					break;
				case "version33":
					qRCodeBarcode.sizeVersion(33);
					break;
				case "version34":
					qRCodeBarcode.sizeVersion(34);
					break;
				case "version35":
					qRCodeBarcode.sizeVersion(35);
					break;
				case "version36":
					qRCodeBarcode.sizeVersion(36);
					break;
				case "version37":
					qRCodeBarcode.sizeVersion(37);
					break;
				case "version38":
					qRCodeBarcode.sizeVersion(38);
					break;
				case "version39":
					qRCodeBarcode.sizeVersion(39);
					break;
				case "version40":
					qRCodeBarcode.sizeVersion(40);
					break;
			}
			return true;
		case "encodingMode":
			switch(value) {
				case "undefined":
					qRCodeBarcode.encodingMode(-1);
					break;
				case "numeric":
					qRCodeBarcode.encodingMode(0);
					break;
				case "alphanumeric":
					qRCodeBarcode.encodingMode(1);
					break;
				case "byte":
					qRCodeBarcode.encodingMode(2);
					break;
				case "kanji":
					qRCodeBarcode.encodingMode(3);
					break;
			}
			return true;
		case "eciNumber":
			qRCodeBarcode.eciNumber(value);
			return true;
		case "eciHeaderDisplayMode":
			switch(value) {
				case "hide":
					qRCodeBarcode.eciHeaderDisplayMode(0);
					break;
				case "show":
					qRCodeBarcode.eciHeaderDisplayMode(1);
					break;
			}
			return true;
		case "fnc1Mode":
			switch(value) {
				case "none":
					qRCodeBarcode.fnc1Mode(0);
					break;
				case "gs1":
					qRCodeBarcode.fnc1Mode(1);
					break;
				case "industry":
					qRCodeBarcode.fnc1Mode(2);
					break;
			}
			return true;
		case "applicationIndicator":
			qRCodeBarcode.applicationIndicator(value);
			return true;
	}

				},

		_set_option: function (qRCodeBarcode, key, value) {
					var currentKey;

					switch (key) {
					    case "width":
					        this._setSize(qRCodeBarcode, "width", value);
					        return true;
					    case "height":
					        this._setSize(qRCodeBarcode, "height", value);
					        return true;
					}
              },

		_creationOptions: null,
		_qRCodeBarcode: null,
		_createWidget: function (options, element, widget) {
					this._creationOptions = options;
					
					$.Widget.prototype._createWidget.apply(this, [options, element]);
			  },

        _create: function () {
				var key, v, size, qRCodeBarcode, width, height,
				i = -1,
				self = this,
				elem = self.element,
				style = elem[0].style,
				o = this._creationOptions;

				// variable which holds initial attributes/styles of target widget, which are used to
				// restore target element on destroy
				self._old_state = {
					// extended widget may add other attributes to that member and they will be processed within destroy
					style: { position: style.position, width: style.width, height: style.height },
					css: elem[0].className,
					elems: elem.find("*")
				};
				if (!$.ig.util._isCanvasSupported()) {
					$.ig.util._renderUnsupportedBrowser(this);
					return;
				}
				qRCodeBarcode = this._createBarcode();
				self._qRCodeBarcode = qRCodeBarcode;
				
							qRCodeBarcode.errorMessageDisplaying = $.ig.Delegate.prototype.combine(qRCodeBarcode.errorMessageDisplaying, jQuery.proxy(this._fireQRCodeBarcode_errorMessageDisplaying, this));
			qRCodeBarcode.dataChanged = $.ig.Delegate.prototype.combine(qRCodeBarcode.dataChanged, jQuery.proxy(this._fireQRCodeBarcode_dataChanged, this));

								
				if (o.hasOwnProperty("width"))
				    elem[0].style.width = o["width"];
				if (o.hasOwnProperty("height"))
				    elem[0].style.height = o["height"];

				qRCodeBarcode.provideContainer(elem[0]);
				for (key in o) {
					if (o.hasOwnProperty(key)) {
						v = o[key];
						if (v !== null) {
							this._setOption(key, v, false);
						}
					}
				}	

				while (i++ < 1) {
					key = i === 0 ? "width" : "height";
					if (o[key]) {
					    size = key;
					    v = o[key];
					} else {			
					    v = elem[0].style[key];
					}
					if (v && v.indexOf("%") > 0) {
						self._setSize(qRCodeBarcode, size = key, v);
					}					
				}
				//_setSize should be called at least once: support for initially invisible container of char
				if (!size) {
					self._setSize(qRCodeBarcode, "width");
				}
				
				if (self.css && self.css.qRCodeBarcode) {
				    elem.addClass(self.css.qRCodeBarcode);
				}			
		},
		_createBarcode: function () {
			return new $.ig.XamQRCodeBarcode();
		},

        _fireQRCodeBarcode_dataChanged: function (barcode, evt) {
			var opts = {};
			opts.newData = evt.newData();
			opts.owner = this;

			this._trigger("dataChanged", null, opts);
		},

        _fireQRCodeBarcode_errorMessageDisplaying: function (barcode, evt) {
			var opts = {};
			opts.errorMessage = evt.errorMessage();
			opts.owner = this;

			this._trigger("errorMessageDisplaying", null, opts);

            evt.errorMessage(opts.errorMessage);
		},
		
		// sets width and height options.
		// params:
		// chart-reference to chart object
		// key-name of option/property (width or height only)
		// value-value of option
		_setSize: function (qRCodeBarcode, key, val) {
			$.ig.util.setSize(this.element, key, val, qRCodeBarcode, this._getNotifyResizeName());
		},

		_getNotifyResizeName: function () {
			return "containerResized";
		},
		
		exportVisualData: function () {
            /* Returns information about how the barcode is rendered.
			   returnType="object" a JavaScript object containing the visual data.
			*/
		    if (this._qRCodeBarcode)
		        return this._qRCodeBarcode.exportVisualData();
		},

		flush: function () {
            /* Causes all pending changes of the barcode e.g. by changed property values to be rendered immediately. */
		    if (this._qRCodeBarcode && this._qRCodeBarcode.view())
		        this._qRCodeBarcode.view().flush();
		},

        destroy: function () {
            /* Destroys widget. */
            var key, style,
				qRCodeBarcode = this._qRCodeBarcode,
				old = this._old_state,
				elem = this.element;
            if (!old) {
                return;
            }
            // remove children created by qRCodeBarcode
            elem.find("*").not(old.elems).remove();
            // restore className modified by qRCodeBarcode
            if (this.css.qRCodeBarcode) {
                elem.removeClass(this.css.qRCodeBarcode);
            }
            // restore css style attributes modified by qRCodeBarcode
            old = old.style;
            style = elem[0].style;
            for (key in old) {
                if (old.hasOwnProperty(key)) {
                    if (style[key] !== old[key]) {
                        style[key] = old[key];
                    }
                }
            }
            if (qRCodeBarcode) {
                this._setSize(qRCodeBarcode);
            }
            $.Widget.prototype.destroy.apply(this, arguments);
            if (qRCodeBarcode && qRCodeBarcode.destroy) {
                qRCodeBarcode.destroy();
            }
            delete this._qRCodeBarcode;
            delete this._old_state;
        },

        styleUpdated: function () {
            /* Re-polls the css styles for the widget. Use this method when the css styles have been modified. */
            if (this._qRCodeBarcode && this._qRCodeBarcode.view()) {
                this._qRCodeBarcode.view().styleUpdated();
            }
        }
       });
       $.extend($.ui.igQRCodeBarcode, {version: '16.1.20161.2145'});
   } (jQuery));



