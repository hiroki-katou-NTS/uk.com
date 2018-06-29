var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var NtsFormLabelBindingHandler = (function () {
                    function NtsFormLabelBindingHandler() {
                    }
                    NtsFormLabelBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        element.classList.add('form-label');
                    };
                    NtsFormLabelBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var accessor = valueAccessor(), label = element.querySelector('label'), constraint = element.querySelector('i'), isInline = ko.unwrap(accessor.inline) === true, isEnable = ko.unwrap(accessor.enable) !== false, isRequired = ko.unwrap(accessor.required) === true, text = !_.isNil(accessor.text) ? ko.unwrap(accessor.text) : (!!label ? label.innerHTML : element.innerHTML), cssClass = !_.isNil(accessor.cssClass) ? ko.unwrap(accessor.cssClass) : '', primitive = !_.isNil(accessor.constraint) ? ko.unwrap(accessor.constraint) : '';
                        element.innerHTML = '';
                        if (!isEnable) {
                            element.classList.add('disabled');
                        }
                        else {
                            element.classList.remove('disabled');
                        }
                        if (!!isRequired) {
                            element.classList.add('required');
                        }
                        else {
                            element.classList.remove('required');
                        }
                        if (!!isInline) {
                            element.classList.add('inline');
                            element.classList.remove('broken');
                            element.style.height = '37px';
                            element.style.lineHeight = '37px';
                        }
                        else {
                            element.classList.remove('inline');
                        }
                        if (!label) {
                            label = document.createElement('label');
                        }
                        if (!constraint) {
                            constraint = document.createElement('i');
                        }
                        element
                            .appendChild(label);
                        label.innerHTML = text;
                        if (!!cssClass) {
                            label.classList.add(cssClass);
                        }
                        if (!!primitive) {
                            if (!isInline) {
                                element.classList.add('broken');
                            }
                            element.appendChild(constraint);
                            if (_.isArray(primitive)) {
                                var miss = _.map(primitive, function (p) { return __viewContext.primitiveValueConstraints[p]; });
                                if (miss.indexOf(false) > -1) {
                                    constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                                }
                                else {
                                    constraint.innerHTML = uk.util.getConstraintMes(primitive);
                                }
                            }
                            else {
                                getConstraintText(primitive).done(function (constraintText) {
                                    if (!__viewContext.primitiveValueConstraints[primitive]) {
                                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                                    }
                                    else {
                                        constraint.innerHTML = constraintText;
                                    }
                                });
                            }
                        }
                    };
                    return NtsFormLabelBindingHandler;
                }());
                function getConstraintText(constraint) {
                    var dfd = $.Deferred();
                    if (constraint === "EmployeeCode" && (!__viewContext.primitiveValueConstraints
                        || !__viewContext.primitiveValueConstraints.EmployeeCode)) {
                        uk.request.ajax("com", "/bs/employee/setting/code/find").done(function (res) {
                            if (!__viewContext.primitiveValueConstraints) {
                                __viewContext.primitiveValueConstraints = {};
                            }
                            var employeeCodeConstr = {
                                valueType: "String",
                                charType: "AlphaNumeric",
                                maxLength: res.numberOfDigits
                            };
                            __viewContext.primitiveValueConstraints.EmployeeCode = employeeCodeConstr;
                            var label = uk.text.getCharTypeByType(employeeCodeConstr.charType).buildConstraintText(res.numberOfDigits);
                            dfd.resolve(label);
                        });
                    }
                    else {
                        var label = uk.util.getConstraintMes(constraint);
                        dfd.resolve(label);
                    }
                    return dfd.promise();
                }
                ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=formlabel-ko-ext.js.map