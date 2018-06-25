/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    /**
     * FormLabel
     */
    class NtsFormLabelBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            element.classList.add('form-label');
        }

        /**
         * Update
         */
        update(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let accessor: any = valueAccessor(),
                label = element.querySelector('label'),
                constraint = element.querySelector('i'),
                isInline = ko.unwrap(accessor.inline) === true,
                isEnable = ko.unwrap(accessor.enable) !== false,
                isRequired = ko.unwrap(accessor.required) === true,
                text: string = !_.isNil(accessor.text) ? ko.unwrap(accessor.text) : (!!label ? label.innerHTML : element.innerHTML),
                cssClass: string = !_.isNil(accessor.cssClass) ? ko.unwrap(accessor.cssClass) : '',
                primitive: string = !_.isNil(accessor.constraint) ? ko.unwrap(accessor.constraint) : '';

            // clear old html
            element.innerHTML = '';

            // show enable or disabled style
            if (!isEnable) {
                element.classList.add('disabled');
            } else {
                element.classList.remove('disabled');
            }

            // show inline mode or broken mode
            if (!!isRequired) {
                element.classList.add('required');
            } else {
                element.classList.remove('required');
            }

            if (!!isInline) {
                element.classList.add('inline');
                element.classList.remove('broken');
                // fix height (inline mode)
                element.style.height = '37px';
                element.style.lineHeight = '37px';
            } else {
                element.classList.remove('inline');
            }

            // init new label element
            if (!label) {
                label = document.createElement('label');
            }

            // init new constraint element
            if (!constraint) {
                constraint = document.createElement('i');
            }

            // append label tag to control
            element
                .appendChild(label);
            label.innerHTML = text;

            // add css class to label
            if (!!cssClass) {
                label.classList.add(cssClass);
            }


            // show primitive constraint if exist
            if (!!primitive) {
                if (!isInline) {
                    element.classList.add('broken');
                }

                // append constraint
                element.appendChild(constraint);
                if (_.isArray(primitive)) {
                    let miss = _.map(primitive, p => __viewContext.primitiveValueConstraints[p]);

                    if (miss.indexOf(false) > -1) {
                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                    } else {
                        constraint.innerHTML = util.getConstraintMes(primitive);
                    }
                } else {
                    getConstraintText(primitive).done(constraintText => {
                        if (!__viewContext.primitiveValueConstraints[primitive]) {
                            constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                        } else {
                            constraint.innerHTML = constraintText;
                        }
                    });
                }
            }
        }
    }

    let getConstraintText = (constraint: any) => {
        let dfd = $.Deferred();
        if (constraint === "EmployeeCode") {
            request.ajax("com", "/bs/employee/setting/code/find").done(res => {
                // if not have primitive, create new
                if (!__viewContext.primitiveValueConstraints) {
                    __viewContext.primitiveValueConstraints = {
                        EmployeeCode: {
                            valueType: "String",
                            charType: "AlphaNumeric",
                            maxLength: res.numberOfDigits
                        }
                    };
                } else {
                    // extend primitive constraint
                    _.extend(__viewContext.primitiveValueConstraints, {
                        EmployeeCode: {
                            valueType: "String",
                            charType: "AlphaNumeric",
                            maxLength: res.numberOfDigits
                        }
                    });
                }

                dfd.resolve(util.getConstraintMes(constraint));
            });
        } else {
            dfd.resolve(util.getConstraintMes(constraint));
        }

        return dfd.promise();
    };

    ko.bindingHandlers['ntsFormLabel'] = new NtsFormLabelBindingHandler();
}
