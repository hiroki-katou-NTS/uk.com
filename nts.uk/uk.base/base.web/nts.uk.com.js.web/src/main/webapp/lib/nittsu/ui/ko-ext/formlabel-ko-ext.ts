/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    interface Accessor {
        inline?: KnockoutObservable<boolean> | boolean;
        enable?: KnockoutObservable<boolean> | boolean;
        required?: KnockoutObservable<boolean> | boolean;
        text?: KnockoutObservable<string> | string;
        cssClass?: KnockoutObservable<string> | string;
        constraint?: KnockoutObservable<string | string[]> | string | string[];
    }
    /**
     * FormLabel
     */
    @handler({
        bindingName: 'ntsFormLabel'
    })
    export class NtsFormLabelBindingHandler implements KnockoutBindingHandler { /**
        * Init.
        */
        init(element: HTMLElement): void {
            element.classList.add('form-label');
        }
        /**
         * Update
         */
        update = (element: HTMLElement, valueAccessor: () => Accessor, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, _bindingContext: KnockoutBindingContext): void => {
            const accessor = valueAccessor();
            let label = element.querySelector('label');
            let constraint = element.querySelector('i');
            const isInline = ko.unwrap(accessor.inline) === true;
            const isEnable = ko.unwrap(accessor.enable) !== false;
            const isRequired = ko.unwrap(accessor.required) === true;
            const text = !_.isNil(accessor.text) ? ko.unwrap(accessor.text) : (!!label ? label.innerHTML : element.innerHTML);
            const cssClass = !_.isNil(accessor.cssClass) ? ko.unwrap(accessor.cssClass) : '';
            const primitive = !_.isNil(accessor.constraint) ? ko.unwrap(accessor.constraint) : '';

            const { primitiveValueConstraints } = __viewContext;

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
                // fix height (not inline mode)
                element.style.height = null;
                element.style.lineHeight = null;
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
                    let miss = _.map(primitive, (p: string) => primitiveValueConstraints[p]);

                    if (miss.indexOf(undefined) > -1) {
                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                    } else {
                        constraint.innerHTML = util.getConstraintMes(primitive);
                    }
                } else {
                    if (!primitiveValueConstraints[primitive]) {
                        constraint.innerHTML = 'UNKNOW_PRIMITIVE';
                    } else {
                        constraint.innerHTML = util.getConstraintMes(primitive);
                    }
                }
            }
        }
    }
}