/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    /**
     * FormLabel
     */
    @handler({
        bindingName: 'ntsFormLabel'
    })
    export class NtsFormLabelBindingHandler implements KnockoutBindingHandler {
        /**
         * Update
         */
        update = (element: HTMLElement, valueAccessor: () => any, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, _bindingContext: KnockoutBindingContext): void => {
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
                    let miss = _.map(primitive, (p: string) => __viewContext.primitiveValueConstraints[p]);

                    if (miss.indexOf(undefined) > -1) {
                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                    } else {
                        constraint.innerHTML = util.getConstraintMes(primitive);
                    }
                } else {
                    if (!__viewContext.primitiveValueConstraints[primitive]) {
                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                    } else {
                        constraint.innerHTML = util.getConstraintMes(primitive);
                    }
                }
            }
        }
    }
}