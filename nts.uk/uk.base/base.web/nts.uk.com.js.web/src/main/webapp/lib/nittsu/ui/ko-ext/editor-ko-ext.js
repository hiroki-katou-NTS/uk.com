var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var validation = nts.uk.ui.validation;
                var disable;
                (function (disable) {
                    var DATA_API_SET_VALUE = "api-set-value-for-disable";
                    var DATA_DEFAULT_VALUE = "default-value-for-disable";
                    function saveApiSetValue($input, value) {
                        $input.data(DATA_API_SET_VALUE, value);
                    }
                    disable.saveApiSetValue = saveApiSetValue;
                    function saveDefaultValue($input, value) {
                        $input.data(DATA_DEFAULT_VALUE, value);
                    }
                    disable.saveDefaultValue = saveDefaultValue;
                    function on($input) {
                        $input.attr('disabled', 'disabled');
                    }
                    disable.on = on;
                    function off($input) {
                        $input.removeAttr('disabled');
                    }
                    disable.off = off;
                })(disable || (disable = {}));
                var valueChanging;
                (function (valueChanging) {
                    var DATA_CHANGED_BY_USER = "changed-by-user";
                    var DATA_CURRENT_VALUE = "current-value";
                    function markUserChange($input) {
                        $input.data(DATA_CHANGED_BY_USER, true);
                    }
                    valueChanging.markUserChange = markUserChange;
                    function unmarkUserChange($input) {
                        $input.data(DATA_CHANGED_BY_USER, false);
                    }
                    valueChanging.unmarkUserChange = unmarkUserChange;
                    function setNewValue($input, value) {
                        $input.data(DATA_CURRENT_VALUE, value);
                    }
                    valueChanging.setNewValue = setNewValue;
                    function isChangingValueByApi($input, newValue) {
                        return !isUserChange($input) && isChangedValue($input, newValue);
                    }
                    valueChanging.isChangingValueByApi = isChangingValueByApi;
                    function isUserChange($input) {
                        return $input.data(DATA_CHANGED_BY_USER) === true;
                    }
                    function isChangedValue($input, newValue) {
                        return $input.data(DATA_CURRENT_VALUE) !== newValue;
                    }
                })(valueChanging || (valueChanging = {}));
                var EditorProcessor = (function () {
                    function EditorProcessor() {
                    }
                    EditorProcessor.prototype.init = function ($input, data) {
                        var _this = this;
                        var self = this;
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        var immediate = false;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var valueUpdate = (immediate === true) ? 'input' : 'change';
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var setValOnRequiredError = (data.setValOnRequiredError !== undefined) ? ko.unwrap(data.setValOnRequiredError) : false;
                        $input.data("setValOnRequiredError", setValOnRequiredError);
                        var characterWidth = 9;
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        setEnterHandlerIfRequired($input, data);
                        $input.on("keydown", function (e) {
                            if (ko.unwrap(data.readonly) && e.keyCode === 8) {
                                e.preventDefault();
                            }
                        });
                        $input.on(valueUpdate, function (e) {
                            var newText = $input.val();
                            var validator = _this.getValidator(data);
                            var result = validator.validate(newText);
                            if (result.isValid) {
                                $input.ntsError('clear');
                                valueChanging.markUserChange($input);
                                value(result.parsedValue);
                                valueChanging.markUserChange($input);
                                value.valueHasMutated();
                            }
                            else {
                                var error = $input.ntsError('getError');
                                if (nts.uk.util.isNullOrEmpty(error) || error.messageText !== result.errorMessage) {
                                    $input.ntsError('clear');
                                    $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                }
                                if ($input.data("setValOnRequiredError") && nts.uk.util.isNullOrEmpty(newText)) {
                                    valueChanging.markUserChange($input);
                                }
                                value(newText);
                            }
                        });
                        $input.blur(function () {
                            if (!$input.attr('readonly')) {
                                var formatter = self.getFormatter(data);
                                var newText = $input.val();
                                var validator = self.getValidator(data);
                                var result = validator.validate(newText);
                                if (result.isValid) {
                                    $input.ntsError('clearKibanError');
                                    $input.val(formatter.format(result.parsedValue));
                                }
                                else {
                                    var error = $input.ntsError('getError');
                                    if (nts.uk.util.isNullOrEmpty(error) || error.messageText !== result.errorMessage) {
                                        $input.ntsError('clearKibanError');
                                        $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                    }
                                }
                            }
                        });
                        $input.on('validate', (function (e) {
                            var newText = $input.val();
                            var validator = self.getValidator(data);
                            var result = validator.validate(newText);
                            $input.ntsError('clearKibanError');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                            }
                        }));
                        var tabIndex = $input.attr("tabindex");
                        $input.data("tabindex", tabIndex);
                    };
                    EditorProcessor.prototype.update = function ($input, data) {
                        var value = ko.unwrap(data.value);
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : {};
                        this.editorOption = $.extend(this.getDefaultOption(), option);
                        var placeholder = this.editorOption.placeholder;
                        var textalign = this.editorOption.textalign;
                        var width = this.editorOption.width;
                        var setValOnRequiredError = (data.setValOnRequiredError !== undefined) ? ko.unwrap(data.setValOnRequiredError) : false;
                        var constraint = !_.isNil(data.constraint) ? ko.unwrap(data.constraint) : undefined;
                        $input.data("setValOnRequiredError", setValOnRequiredError);
                        disable.saveDefaultValue($input, option.defaultValue);
                        if (valueChanging.isChangingValueByApi($input, value)) {
                            disable.saveApiSetValue($input, value);
                        }
                        valueChanging.setNewValue($input, value);
                        valueChanging.unmarkUserChange($input);
                        if (enable !== false) {
                            disable.off($input);
                        }
                        else {
                            disable.on($input);
                        }
                        if (readonly === false) {
                            $input.removeAttr('readonly');
                            if ($input.data("tabindex") !== undefined) {
                                $input.attr("tabindex", $input.data("tabindex"));
                            }
                            else {
                                $input.removeAttr("tabindex");
                            }
                        }
                        else {
                            $input.attr('readonly', 'readonly');
                            $input.attr("tabindex", -1);
                        }
                        $input.attr('placeholder', placeholder);
                        $input.css('text-align', textalign);
                        if (width.trim() != "")
                            $input.width(width);
                        if (constraint !== "StampNumber" && constraint !== "EmployeeCode") {
                            var formatted = $input.ntsError('hasError') ? value : this.getFormatter(data).format(value);
                            $input.val(formatted);
                        }
                        else {
                            $input.val(value);
                        }
                    };
                    EditorProcessor.prototype.getDefaultOption = function () {
                        return {};
                    };
                    EditorProcessor.prototype.getFormatter = function (data) {
                        return new uk.format.NoFormatter();
                    };
                    EditorProcessor.prototype.getValidator = function (data) {
                        return new validation.NoValidator();
                    };
                    return EditorProcessor;
                }());
                var TextEditorProcessor = (function (_super) {
                    __extends(TextEditorProcessor, _super);
                    function TextEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TextEditorProcessor.prototype.init = function ($input, data) {
                        var self = this;
                        var value = data.value;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var setWidthByConstraint = (data.setWidthByConstraint !== undefined) ? ko.unwrap(data.setWidthByConstraint) : false;
                        var readonly = (data.readonly !== undefined) ? ko.unwrap(data.readonly) : false;
                        var setValOnRequiredError = (data.setValOnRequiredError !== undefined) ? ko.unwrap(data.setValOnRequiredError) : false;
                        var constraint = !_.isNil(data.constraint) ? ko.unwrap(data.constraint) : undefined;
                        $input.data("setValOnRequiredError", setValOnRequiredError);
                        self.setWidthByConstraint(constraintName, $input);
                        $input.addClass('nts-editor nts-input');
                        $input.wrap("<span class= 'nts-editor-wrapped ntsControl'/>");
                        setEnterHandlerIfRequired($input, data);
                        $input.on("keydown", function (e) {
                            if (ko.unwrap(data.readonly) && e.keyCode === 8) {
                                e.preventDefault();
                            }
                        });
                        $input.on("keyup", function (e) {
                            if ($input.attr('readonly')) {
                                return;
                            }
                            var code = e.keyCode || e.which;
                            if (_.includes(uk.KeyCodes.NotValueKeys, code)) {
                                return;
                            }
                            var validator = self.getValidator(data);
                            var newText = $input.val();
                            var result = validator.validate(newText, { isCheckExpression: true });
                            $input.ntsError('clear');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                            }
                        });
                        $input.blur(function () {
                            if (!$input.attr('readonly')) {
                                var validator = self.getValidator(data);
                                var newText = $input.val();
                                var result = validator.validate(newText, { isCheckExpression: true });
                                if (!result.isValid) {
                                    var oldError = $input.ntsError('getError');
                                    if (nts.uk.util.isNullOrEmpty(oldError)) {
                                        $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                    }
                                    else {
                                        var inListError = _.find(oldError, function (o) { return o.errorCode === result.errorCode; });
                                        if (nts.uk.util.isNullOrUndefined(inListError)) {
                                            $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                        }
                                    }
                                }
                                else {
                                    $input.ntsError('clearKibanError');
                                    if (constraint === "StampNumber" || constraint === "EmployeeCode") {
                                        var formatter = self.getFormatter(data);
                                        var formatted = formatter.format(result.parsedValue);
                                        value(formatted);
                                    }
                                }
                            }
                        });
                        $input.on("change", function (e) {
                            if (!$input.attr('readonly')) {
                                var validator = self.getValidator(data);
                                var newText = $input.val();
                                var result = validator.validate(newText, { isCheckExpression: true });
                                if (result.isValid) {
                                    $input.ntsError('clear');
                                    if (value() === result.parsedValue) {
                                        $input.val(result.parsedValue);
                                    }
                                    else {
                                        valueChanging.markUserChange($input);
                                        value(result.parsedValue);
                                    }
                                }
                                else {
                                    var oldError = $input.ntsError('getError');
                                    if (nts.uk.util.isNullOrEmpty(oldError)) {
                                        $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                    }
                                    else {
                                        var inListError = _.find(oldError, function (o) { return o.errorCode === result.errorCode; });
                                        if (nts.uk.util.isNullOrUndefined(inListError)) {
                                            $input.ntsError('set', result.errorMessage, result.errorCode, false);
                                        }
                                    }
                                    if ($input.data("setValOnRequiredError") && nts.uk.util.isNullOrEmpty(newText)) {
                                        valueChanging.markUserChange($input);
                                    }
                                    value(newText);
                                }
                            }
                        });
                        $input.on('validate', (function (e) {
                            var validator = self.getValidator(data);
                            var newText = $input.val();
                            var result = validator.validate(newText);
                            $input.ntsError('clearKibanError');
                            if (!result.isValid) {
                                $input.ntsError('set', result.errorMessage, result.errorCode, false);
                            }
                        }));
                        new nts.uk.util.value.DefaultValue().onReset($input, data.value);
                        var tabIndex = $input.attr("tabindex");
                        $input.data("tabindex", tabIndex);
                        $input.tooltipWhenReadonly();
                    };
                    TextEditorProcessor.prototype.update = function ($input, data) {
                        var self = this;
                        self.$input = $input;
                        _super.prototype.update.call(this, $input, data);
                        var textmode = this.editorOption.textmode;
                        $input.attr('type', textmode);
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var setWidthByConstraint = (data.setWidthByConstraint !== undefined) ? ko.unwrap(data.setWidthByConstraint) : false;
                        if (setWidthByConstraint) {
                            self.setWidthByConstraint(constraintName, $input);
                        }
                        if (!$input.ntsError('hasError') && data.value() !== $input.val()) {
                            valueChanging.markUserChange($input);
                            data.value($input.val());
                        }
                    };
                    TextEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TextEditorOption();
                    };
                    TextEditorProcessor.prototype.getFormatter = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        this.editorOption = this.editorOption || {};
                        if (constraint && constraint.formatOption) {
                            $.extend(this.editorOption, constraint.formatOption);
                        }
                        this.editorOption.autofill = (constraint && constraint.isZeroPadded) ? constraint.isZeroPadded : this.editorOption.autofill;
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    };
                    TextEditorProcessor.prototype.getValidator = function (data) {
                        var name = data.name !== undefined ? ko.unwrap(data.name) : "";
                        name = nts.uk.resource.getControlName(name);
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        if (data.constraint == "WorkplaceCode") {
                            return new validation.WorkplaceCodeValidator(name, constraintName, { required: required });
                        }
                        if (data.constraint == "DepartmentCode") {
                            return new validation.DepartmentCodeValidator(name, constraintName, { required: required });
                        }
                        if (data.constraint == "PostCode") {
                            return new validation.PostCodeValidator(name, constraintName, { required: required });
                        }
                        if (data.constraint == "StampNumber") {
                            return new validation.PunchCardNoValidator(name, constraintName, { required: required });
                        }
                        if (data.constraint === "EmployeeCode") {
                            return new validation.EmployeeCodeValidator(name, { required: required });
                        }
                        return new validation.StringValidator(name, constraintName, { required: required });
                    };
                    TextEditorProcessor.prototype.setWidthByConstraint = function (constraintName, $input) {
                        var self = this;
                        var characterWidth = 9;
                        var constraint = validation.getConstraint(constraintName);
                        if (constraint && constraint.maxLength && !$input.is("textarea")) {
                            var autoWidth = constraint.maxLength * characterWidth;
                            $input.width(autoWidth);
                        }
                    };
                    return TextEditorProcessor;
                }(EditorProcessor));
                var MultilineEditorProcessor = (function (_super) {
                    __extends(MultilineEditorProcessor, _super);
                    function MultilineEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    MultilineEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var resizeable = this.editorOption.resizeable;
                        $input.css('resize', (resizeable) ? "both" : "none");
                    };
                    MultilineEditorProcessor.prototype.getDefaultOption = function () {
                        return new ui.option.MultilineEditorOption();
                    };
                    MultilineEditorProcessor.prototype.getFormatter = function (data) {
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var constraint = validation.getConstraint(constraintName);
                        return new uk.text.StringFormatter({ constraintName: constraintName, constraint: constraint, editorOption: this.editorOption });
                    };
                    MultilineEditorProcessor.prototype.getValidator = function (data) {
                        var name = data.name !== undefined ? ko.unwrap(data.name) : "";
                        name = nts.uk.resource.getControlName(name);
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        return new validation.StringValidator(name, constraintName, { required: required });
                    };
                    return MultilineEditorProcessor;
                }(EditorProcessor));
                var NumberEditorProcessor = (function (_super) {
                    __extends(NumberEditorProcessor, _super);
                    function NumberEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    NumberEditorProcessor.prototype.init = function ($input, data) {
                        _super.prototype.init.call(this, $input, data);
                        $input.on('focus', function () {
                            if (!$input.attr('readonly')) {
                                $input.val(data.value());
                                if (ui.keyboardStream.wasKeyDown(uk.KeyCodes.Tab, 500)) {
                                    $input.select();
                                }
                            }
                        });
                        $input.on('keydown', function (evt) {
                            var rd = ko.toJS(data), target = evt.target, val = target.value, ss = target.selectionStart, se = target.selectionEnd, constraint = rd.constraint;
                            if ([8, 9, 13, 16, 17, 20, 27, 33, 34, 35, 36, 37, 38, 39, 40, 45, 46].indexOf(evt.keyCode) == -1) {
                                if (!evt.key.match(/[\-\.0-9]/g)) {
                                    evt.preventDefault();
                                }
                                else {
                                    if (ss == se) {
                                        if (se == 0) {
                                            val = evt.key + val;
                                        }
                                        else if (se == val.length) {
                                            val += evt.key;
                                        }
                                        else {
                                            val = val.substring(0, ss) + evt.key + val.substring(se, val.length);
                                        }
                                    }
                                    else {
                                        val = val.replace(val.substring(ss, se), evt.key);
                                    }
                                    if (evt.key == '-' && (ss || target.value.indexOf('-') > -1)) {
                                        evt.preventDefault();
                                        return;
                                    }
                                    if (evt.key == '.' && target.value.indexOf('.') > -1) {
                                        evt.preventDefault();
                                        return;
                                    }
                                    if (constraint) {
                                        var primitive = window['__viewContext'].primitiveValueConstraints[constraint];
                                        if (primitive) {
                                            var nval = parseFloat(val), min = primitive.min, max = primitive.max, dlen = primitive.mantissaMaxLength;
                                            if (evt.key == '-' && min >= 0) {
                                                evt.preventDefault();
                                                return;
                                            }
                                            if (rd.option && rd.option.decimallength < 1) {
                                                primitive.valueType = 'Integer';
                                            }
                                            switch (primitive.valueType) {
                                                case "String":
                                                    var maxL = primitive.maxLength;
                                                    if (val.length > maxL || ['.', '-'].indexOf(evt.key) > -1) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    break;
                                                case "Integer":
                                                    if (evt.key == '.') {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    if (nval > max || nval < min) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    break;
                                                case "HalfInt":
                                                    var milen = val.replace('-', '').replace(/\d+/, '').replace('.', '').length;
                                                    if (milen > 1 || (se == val.length - 1 && milen == 1 && ['0', '5'].indexOf(evt.key) == -1)) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    if (nval > max || nval < min) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    break;
                                                case "Decimal":
                                                    var mdlen = val.replace('-', '').replace(/\d+/, '').replace('.', '').length;
                                                    if (mdlen > primitive.mantissaMaxLength) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    if (nval > max || nval < min) {
                                                        evt.preventDefault();
                                                        return;
                                                    }
                                                    break;
                                            }
                                        }
                                    }
                                    else {
                                        var dlen = rd.option.decimallength, mdlen = val.replace('-', '').replace(/\d+/, '').replace('.', '').length;
                                        if (dlen < mdlen) {
                                            evt.preventDefault();
                                            return;
                                        }
                                    }
                                }
                            }
                            else if ([8, 46].indexOf(evt.keyCode) > -1 && constraint) {
                                var primitive = window['__viewContext'].primitiveValueConstraints[constraint];
                                if (primitive) {
                                    var min = primitive.min, max = primitive.max;
                                    if (ss == se) {
                                        if (evt.keyCode == 8) {
                                            var _num = parseFloat(val.substring(0, ss - 1) + val.substring(se, val.length));
                                            if (_num < min || _num > max) {
                                                evt.preventDefault();
                                                return;
                                            }
                                        }
                                        else {
                                            var _num = parseFloat(val.substring(0, ss) + val.substring(se + 1, val.length));
                                            if (_num < min || _num > max) {
                                                evt.preventDefault();
                                                return;
                                            }
                                        }
                                    }
                                    else {
                                        var _num = parseFloat(val.substring(0, ss) + val.substring(se, val.length));
                                        if (_num < min || _num > max) {
                                            evt.preventDefault();
                                            return;
                                        }
                                    }
                                }
                            }
                        });
                    };
                    NumberEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var $parent = $input.parent();
                        var width = this.editorOption.width;
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        if (this.editorOption.currencyformat !== undefined && this.editorOption.currencyformat !== null) {
                            $parent.addClass("symbol").addClass(this.editorOption.currencyposition === 'left' ? 'symbol-left' : 'symbol-right');
                            var format = this.editorOption.currencyformat === "JPY" ? "\u00A5" : '$';
                            $parent.attr("data-content", format);
                        }
                        else {
                            if (!nts.uk.util.isNullOrEmpty(this.editorOption.unitID)) {
                                var unit = uk.text.getNumberUnit(this.editorOption.unitID);
                                this.editorOption.symbolChar = unit.unitText;
                                this.editorOption.symbolPosition = unit.position;
                                this.setupUnit($input, width);
                            }
                            else if (!nts.uk.util.isNullOrEmpty(this.editorOption.symbolChar) && !nts.uk.util.isNullOrEmpty(this.editorOption.symbolPosition)) {
                                this.setupUnit($input, width);
                            }
                        }
                        if (!nts.uk.util.isNullOrEmpty(this.editorOption.defaultValue)
                            && nts.uk.util.isNullOrEmpty(data.value())) {
                            data.value(this.editorOption.defaultValue);
                        }
                    };
                    NumberEditorProcessor.prototype.setupUnit = function ($input, width) {
                        var $parent = $input.parent();
                        var padding = nts.uk.text.countHalf(this.editorOption.symbolChar) * 8;
                        if (padding < 20) {
                            padding = 20;
                        }
                        $parent.addClass("symbol").addClass(this.editorOption.symbolPosition === 'right' ? 'symbol-right' : 'symbol-left');
                        $parent.attr("data-content", this.editorOption.symbolChar);
                        var css = this.editorOption.symbolPosition === 'right' ? { "padding-right": padding + "px" } : { "padding-left": padding + "px" };
                        $input.css(css);
                        if (width.trim() != "") {
                            $input.innerWidth(parseInt(width) - 2);
                        }
                    };
                    NumberEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.NumberEditorOption();
                    };
                    NumberEditorProcessor.prototype.getFormatter = function (data) {
                        return new uk.text.NumberFormatter({ option: this.editorOption });
                    };
                    NumberEditorProcessor.prototype.getValidator = function (data) {
                        var option = !nts.uk.util.isNullOrUndefined(data.option) ? ko.toJS(data.option) : {}, eOption = $.extend(this.getDefaultOption(), option), required = (data.required !== undefined) ? ko.unwrap(data.required) : false, constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "", name = nts.uk.resource.getControlName(data.name !== undefined ? ko.unwrap(data.name) : "");
                        $.extend(this.editorOption, {
                            required: required,
                            decimallength: Number(eOption.decimallength),
                            grouplength: Number(eOption.grouplength),
                            decimalseperator: eOption.decimalseperator
                        });
                        return new validation.NumberValidator(name, constraintName, this.editorOption);
                    };
                    return NumberEditorProcessor;
                }(EditorProcessor));
                var TimeEditorProcessor = (function (_super) {
                    __extends(TimeEditorProcessor, _super);
                    function TimeEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TimeEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var width = option.width;
                        var $parent = $input.parent();
                        var parentTag = $parent.parent().prop("tagName").toLowerCase();
                        if (parentTag === "td" || parentTag === "th" || parentTag === "a" || width === "100%") {
                            $parent.css({ 'width': '100%' });
                        }
                        if (!nts.uk.util.isNullOrEmpty(data.mode) && (data.mode === "year" || data.mode === "fiscal")) {
                            var symbolText = data.mode === "year" ? nts.uk.text.getNumberUnit("YEARS") : nts.uk.text.getNumberUnit("FIS_YEAR");
                            $parent.addClass("symbol").addClass('symbol-right');
                            $parent.attr("data-content", symbolText.unitText);
                            var css = data.mode === "year" ? { "padding-right": "20px" } : { "padding-right": "35px" };
                            $input.css(css);
                        }
                        if (!nts.uk.util.isNullOrEmpty(option.defaultValue)
                            && nts.uk.util.isNullOrEmpty(data.value())) {
                            data.value(option.defaultValue);
                        }
                    };
                    TimeEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeEditorOption();
                    };
                    TimeEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        var mode = (data.mode !== undefined) ? ko.unwrap(data.mode) : "";
                        return new uk.text.TimeFormatter({ inputFormat: inputFormat, mode: mode });
                    };
                    TimeEditorProcessor.prototype.getValidator = function (data) {
                        var name = data.name !== undefined ? ko.unwrap(data.name) : "";
                        name = nts.uk.resource.getControlName(name);
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        var inputFormat = (data.inputFormat !== undefined) ? ko.unwrap(data.inputFormat) : option.inputFormat;
                        var mode = (data.mode !== undefined) ? ko.unwrap(data.mode) : "";
                        var validateOption = $.extend({ required: required, outputFormat: inputFormat, mode: mode }, option);
                        return new validation.TimeValidator(name, constraintName, validateOption);
                    };
                    return TimeEditorProcessor;
                }(EditorProcessor));
                var TimeWithDayAttrEditorProcessor = (function (_super) {
                    __extends(TimeWithDayAttrEditorProcessor, _super);
                    function TimeWithDayAttrEditorProcessor() {
                        _super.apply(this, arguments);
                    }
                    TimeWithDayAttrEditorProcessor.prototype.init = function ($input, data) {
                        _super.prototype.init.call(this, $input, data);
                        $input.focus(function () {
                            if ($input.attr('readonly')) {
                                return;
                            }
                            if ($input.ntsError('hasError')) {
                                return;
                            }
                            if (!nts.uk.util.isNullOrEmpty(data.value())) {
                                var timeWithDayAttr = uk.time.minutesBased.clock.dayattr.create(data.value());
                                $input.val(timeWithDayAttr.shortText);
                            }
                            else {
                                $input.val("");
                            }
                            if (ui.keyboardStream.wasKeyDown(uk.KeyCodes.Tab, 500)) {
                                $input.select();
                            }
                        });
                    };
                    TimeWithDayAttrEditorProcessor.prototype.update = function ($input, data) {
                        _super.prototype.update.call(this, $input, data);
                        var value = ko.unwrap(data.value);
                        if ($input.ntsError("hasError") && typeof (value) === "number") {
                            $input.ntsError("clearKibanError");
                            $input.val(this.getFormatter(data).format(value));
                        }
                    };
                    TimeWithDayAttrEditorProcessor.prototype.getDefaultOption = function () {
                        return new nts.uk.ui.option.TimeWithDayAttrEditorOption();
                    };
                    TimeWithDayAttrEditorProcessor.prototype.getFormatter = function (data) {
                        var option = (data.option !== undefined) ? ko.mapping.toJS(data.option) : this.getDefaultOption();
                        return new uk.text.TimeWithDayFormatter(option);
                    };
                    TimeWithDayAttrEditorProcessor.prototype.getValidator = function (data) {
                        var name = data.name !== undefined ? ko.unwrap(data.name) : "";
                        name = nts.uk.resource.getControlName(name);
                        var constraintName = (data.constraint !== undefined) ? ko.unwrap(data.constraint) : "";
                        var required = (data.required !== undefined) ? ko.unwrap(data.required) : false;
                        return new validation.TimeWithDayValidator(name, constraintName, { required: required });
                    };
                    return TimeWithDayAttrEditorProcessor;
                }(EditorProcessor));
                var NtsEditorBindingHandler = (function () {
                    function NtsEditorBindingHandler() {
                    }
                    NtsEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().init($(element), valueAccessor());
                    };
                    NtsEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new EditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsEditorBindingHandler;
                }());
                var NtsTextEditorBindingHandler = (function (_super) {
                    __extends(NtsTextEditorBindingHandler, _super);
                    function NtsTextEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsTextEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsTextEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TextEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTextEditorBindingHandler;
                }(NtsEditorBindingHandler));
                var NtsNumberEditorBindingHandler = (function () {
                    function NtsNumberEditorBindingHandler() {
                    }
                    NtsNumberEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsNumberEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new NumberEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsNumberEditorBindingHandler;
                }());
                var NtsTimeEditorBindingHandler = (function () {
                    function NtsTimeEditorBindingHandler() {
                    }
                    NtsTimeEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsTimeEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTimeEditorBindingHandler;
                }());
                var NtsMultilineEditorBindingHandler = (function (_super) {
                    __extends(NtsMultilineEditorBindingHandler, _super);
                    function NtsMultilineEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsMultilineEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsMultilineEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new MultilineEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsMultilineEditorBindingHandler;
                }(NtsEditorBindingHandler));
                var NtsTimeWithDayAttrEditorBindingHandler = (function (_super) {
                    __extends(NtsTimeWithDayAttrEditorBindingHandler, _super);
                    function NtsTimeWithDayAttrEditorBindingHandler() {
                        _super.apply(this, arguments);
                    }
                    NtsTimeWithDayAttrEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeWithDayAttrEditorProcessor().init($(element), valueAccessor());
                    };
                    NtsTimeWithDayAttrEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        new TimeWithDayAttrEditorProcessor().update($(element), valueAccessor());
                    };
                    return NtsTimeWithDayAttrEditorBindingHandler;
                }(NtsEditorBindingHandler));
                function setEnterHandlerIfRequired($input, data) {
                    var handlesEnterKey = (typeof data.enterkey === "function");
                    var onEnterKey = handlesEnterKey ? data.enterkey : $.noop;
                    if (handlesEnterKey) {
                        $input.addClass("enterkey")
                            .onkey("down", uk.KeyCodes.Enter, function (e) {
                            if ($(".blockUI").length > 0) {
                                return;
                            }
                            $input.change();
                            onEnterKey.call(ko.dataFor(e.target), e);
                        });
                    }
                }
                ko.bindingHandlers['ntsTextEditor'] = new NtsTextEditorBindingHandler();
                ko.bindingHandlers['ntsNumberEditor'] = new NtsNumberEditorBindingHandler();
                ko.bindingHandlers['ntsTimeEditor'] = new NtsTimeEditorBindingHandler();
                ko.bindingHandlers['ntsMultilineEditor'] = new NtsMultilineEditorBindingHandler();
                ko.bindingHandlers['ntsTimeWithDayEditor'] = new NtsTimeWithDayAttrEditorBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
