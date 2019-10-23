import { IRule } from 'declarations';
import { resources } from '@app/plugins/i18n';
import { Vue, VueConstructor, VNode, DirectiveFunction } from '@app/provider';

import { $, dom, TimeDuration, TimeWithDay } from '@app/utils';
import * as validators from '@app/plugins/validate/validators';

$.merge(resources, {
    vi: {
        required: '#{field} là trường bắt buộc!'
    }
});

const DIRTY = 'dirty',
    defReact = Vue.util.defineReactive,
    validate = {
        install(vue: VueConstructor<Vue>) {
            const isRule = (rule: IRule) => {
                rule = $.cloneObject(rule);

                return !!$.values(rule).map((r) => {
                    if ($.isObject(r)) {
                        let keys = $.keys(r);

                        if (keys.length == 2 && keys.indexOf('test') > -1 && keys.indexOf('message') > -1) {
                            return true;
                        }

                        return false;
                    }

                    return true;
                }).filter((m) => !m).length;
            }, updateValidator = function (rule: IRule): any {
                let validations: IRule = this;

                $.objectForEach(rule, (key: string, value: IRule) => {
                    if (isRule(value)) {
                        updateValidator.apply((validations[key] || (validations[key] = {})), [value]);
                    } else {
                        $.objectForEach(value, (k: string, r: any) => {
                            if (!$.isNull(r)) {
                                (validations[key] || (validations[key] = {}))[k] = r;
                            } else if (validations[key]) {
                                delete validations[key][k];
                            }
                        });
                    }
                });

                return validations;
            }, paths = (model: any) => {
                return $.pathsOfObject(model)
                    .filter((path: string) => !(path as any).endsWith('.test'))
                    .filter((m) => {
                        let rule: IRule = $.get(model, m),
                            keysOfRule: Array<string> = $.keys(rule).filter((k) => DIRTY !== k);

                        if (keysOfRule.length == 2) {
                            return (keysOfRule.indexOf('test') == -1 && keysOfRule.indexOf('message') == -1 && keysOfRule.indexOf('messageId') == -1);
                        }

                        return true;
                    })
                    .filter((m) => {
                        let rule: IRule = $.get(model, m);

                        return $.values(rule)
                            .filter((c) => {
                                if (!$.isObject(c)) {
                                    return true;
                                } else {
                                    let keys = $.keys(c);

                                    if (keys.filter((k) => ['test', 'message', 'messageId']
                                        .indexOf(k) > -1).length == keys.length) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }
                            }).length;
                    });
            };

            vue.mixin({
                beforeCreate() {
                    let self: Vue = this,
                        validations = self.$options.validations || {};

                    // init errors & $valid obser
                    defReact(self, '$errors', {});
                    // store all watchers of validators
                    defReact(self, '$validators', {});
                    // store all validators define in decorator as react obsv
                    defReact(self, 'validations', validations);

                    // valid flag for check all validate item in view model
                    Object.defineProperty(self, '$valid', {
                        get() {
                            let c = $.cloneObject,
                                $errors = c(self.$errors),
                                $isValid = (p: string) => !$.size($.get($errors, p));

                            return !paths(self.validations).map($isValid).filter((f) => !f).length;
                        }
                    });

                    // define obser for validations
                    let errors = {};

                    paths(validations)
                        .forEach((path: string) => {
                            $.set(errors, path, {});
                        });

                    vue.set(self, '$errors', errors);

                    delete self.$options.validations;

                    if (self.$options.constraints && self.$options.constraints.length) {
                        self.$http
                            .post('/validate/constraints/map', self.$options.constraints)
                            .then((resp: { data: Array<{ [key: string]: any }> }) => {
                                paths(validations)
                                    .forEach((path: string) => {
                                        let validation: IRule = $.get(validations, path, {});

                                        if (validation.constraint) {
                                            let cstr: { [key: string]: any } = resp.data.filter((c) => c.name === validation.constraint)[0];

                                            if (cstr) {
                                                ['path', 'name']
                                                    .forEach((v) => {
                                                        cstr = $.omit(cstr, v);
                                                    });

                                                if (cstr.valueType === 'String' || (cstr.minLength || cstr.maxLength)) {
                                                    if (!cstr.charType) {
                                                        cstr.charType = 'Any';
                                                    }
                                                }

                                                switch (validation.constraint) {
                                                    case 'EmployeeCode':
                                                        cstr.charType = 'EmployeeCode';
                                                        break;
                                                    case 'WorkplaceCode':
                                                        cstr.charType = 'WorkplaceCode';
                                                        break;
                                                }

                                                ['min', 'max', 'minLength', 'maxLength', 'mantissaMaxLength']
                                                    .forEach((v) => {
                                                        if ($.has(cstr, v)) {
                                                            if (['Decimal', 'Integer'].indexOf($.get(cstr, 'valueType')) > -1) {
                                                                $.set(cstr, v, Number($.get(cstr, v)));
                                                            } else if (['Clock'].indexOf($.get(cstr, 'valueType')) > -1) {
                                                                $.set(cstr, v, TimeWithDay.from($.get(cstr, v)).toNumber());
                                                            } else if (['Time'].indexOf($.get(cstr, 'valueType')) > -1) {
                                                                $.set(cstr, v, TimeDuration.from($.get(cstr, v)).toNumber());
                                                            } else {
                                                                // date ?
                                                            }
                                                        }
                                                    });

                                                self.$updateValidator(path, cstr);
                                            }
                                        }
                                    });
                            });
                    }
                },
                created() {
                    let self: Vue = this;

                    // clean error when change validation
                    self.$watch(() => self.validations, (validations) => {
                        validations = $.cloneObject(validations);

                        // update error object
                        let $paths = paths(validations),
                            errors = $.cloneObject(self.$errors),
                            $validators: {
                                [key: number]: {
                                    path: string;
                                    watch: (value: any) => void;
                                    unwatch: () => void;
                                }
                            } = (self as any).$validators;

                        $.objectForEach($validators, (k: string, validtor: {
                            path: string;
                            watch: (value: any) => void;
                            unwatch: () => void;
                        }) => {
                            validtor.unwatch();
                        });

                        $validators = {};

                        $paths.forEach((path: string, idx: number) => {
                            $.merge(errors, $.set({}, path, {}));

                            let watch = function (value: any) {
                                let errors = $.cloneObject(self.$errors),
                                    models = $.get(errors, path),
                                    rule = $.get($.cloneObject(self.validations), path, {}),
                                    msgkey = $.keys(models)[0];

                                // re validate
                                if (msgkey) {
                                    $.objectForEach(validators, (key: string, vldtor: (...params: any) => string) => {
                                        if (key == msgkey) {
                                            let params: Array<any> = $.isArray(rule[key]) ? rule[key] : [rule[key]],
                                                message = vldtor.apply(self, [value, ...params, rule]);

                                            if (!message) {
                                                $.omit(models, key);
                                            } else {
                                                if (!$.size(models)) {
                                                    $.set(models, key, message);
                                                }
                                            }
                                        }
                                    });

                                    let vldtor: { test: RegExp | Function; message?: string; messageId?: string; } = rule[msgkey];

                                    if (vldtor) {
                                        if ($.isFunction(vldtor.test)) {
                                            if (!vldtor.test.apply(self, [value])) {
                                                if (!$.size(models)) {
                                                    $.set(models, msgkey, vldtor.message || vldtor.messageId);
                                                }
                                            } else {
                                                $.omit(models, msgkey);
                                            }
                                        } else if ($.isRegExp(vldtor.test)) {
                                            if (value && !vldtor.test.test(value)) {
                                                if (!$.size(models)) {
                                                    $.set(models, msgkey, vldtor.message || vldtor.messageId);
                                                }
                                            } else {
                                                $.omit(models, msgkey);
                                            }
                                        }
                                    }
                                }

                                // check fixed validators
                                $.objectForEach(validators, (key: string, vldtor: (...params: any) => string) => {
                                    if ($.has(rule, key)) {
                                        let params: Array<any> = $.isArray(rule[key]) ? rule[key] : [rule[key]],
                                            message = vldtor.apply(self, [value, ...params, rule]);

                                        if (!message) {
                                            $.omit(models, key);
                                        } else {
                                            if (!$.size(models)) {
                                                $.set(models, key, message);
                                            }
                                        }
                                    }
                                });

                                // check custom validators of dev
                                $.keys(rule)
                                    .filter((f) => $.keys(validators).indexOf(f) == -1)
                                    .forEach((key: string) => {
                                        let vldtor: { test: RegExp | Function; message?: string; messageId: string; } = rule[key];

                                        if ($.isFunction(vldtor.test)) {
                                            if (!vldtor.test.apply(self, [value])) {
                                                if (!$.size(models)) {
                                                    $.set(models, key, vldtor.message || vldtor.messageId);
                                                }
                                            } else {
                                                $.omit(models, key);
                                            }
                                        } else if ($.isRegExp(vldtor.test)) {
                                            if (value && !vldtor.test.test(value)) {
                                                if (!$.size(models)) {
                                                    $.set(models, key, vldtor.message || vldtor.messageId);
                                                }
                                            } else {
                                                $.omit(models, key);
                                            }
                                        }
                                    });

                                vue.set(self, '$errors', errors);
                            };

                            // add new watchers
                            $validators[idx] = {
                                path,
                                watch,
                                unwatch: self.$watch(path, watch)
                            };
                        });

                        vue.set(self, '$errors', $.cloneObject(errors));
                        vue.set(self, '$validators', $.cloneObject($validators));
                    }, { deep: true });

                    vue.set(self, 'validations', $.cloneObject(self.validations));

                    setTimeout(() => self.$validate('clear'), 100);
                }
            });

            vue.directive('validate', {
                update(el, binding: any) {
                    // if arg is always, skip all errors
                    if (!($.has(binding.value, 'always') && $.has(binding.value, 'errors'))) {
                        if (binding.arg !== 'always') {
                            if (el.getAttribute('disabled')) {
                                vue.set(binding, 'value', {});
                            }
                        }

                        // toggle class css error
                        if ($.size(binding.value)) {
                            dom.addClass(el, 'is-invalid');
                        } else {
                            dom.removeClass(el, 'is-invalid');
                        }
                    } else if ($.has(binding.value, 'always') && $.has(binding.value, 'errors')) {
                        if (!binding.value.always) {
                            if (el.getAttribute('disabled')) {
                                vue.set(binding.value, 'errors', {});
                            }
                        }

                        // toggle class css error
                        if ($.size(binding.value.errors)) {
                            dom.addClass(el, 'is-invalid');
                        } else {
                            dom.removeClass(el, 'is-invalid');
                        }
                    }
                }
            });

            vue.component('v-errors', {
                props: ['data', 'name'],
                template: `<span class="invalid-feedback">{{ resource | i18n(params) }}</span>`,
                computed: {
                    params() {
                        let self = this,
                            data = self.data;

                        if (!$.isArray(data)) {
                            return [self.name];
                        } else {
                            let $data = data.slice(1);

                            return [self.name, ...$data];
                        }
                    },
                    resource() {
                        return $.isString(this.data) ? this.data : ($.isArray(this.data) ? this.data[0] : '');
                    }
                }
            });

            // define $validate instance method
            vue.prototype.$validate = function (field?: string | 'clear') {
                let self: Vue = this,
                    errors = $.cloneObject(self.$errors),
                    validations = $.cloneObject(self.validations);

                if (field) { // check match field name
                    if (field === 'clear') {
                        let $validators: Array<{
                            path: string;
                            watch: (value: any) => void;
                            unwatch: () => void;
                        }> = (self as any).$validators,
                            errors = {};

                        $.objectForEach($validators, (k: string, validator: {
                            path: string;
                            watch: (value: any) => void;
                            unwatch: () => void;
                        }) => {
                            $.set(errors, validator.path, {});
                        });

                        setTimeout(() => {
                            vue.set(self, '$errors', errors);
                        }, 100);
                    } else {
                        let error = $.get(errors, field, null),
                            validate = $.get(validations, field, null);

                        if (error && validate) {
                        }
                    }
                } else { // check all
                    let $validators: Array<{
                        path: string;
                        watch: (value: any) => void;
                        unwatch: () => void;
                    }> = (self as any).$validators;

                    $.objectForEach($validators, (k: string, validtor: {
                        path: string;
                        watch: (value: any) => void;
                        unwatch: () => void;
                    }) => {
                        validtor.watch($.get(self, validtor.path));
                    });
                }
            };

            vue.prototype.$updateValidator = function (pathOrRule: string | IRule, rule?: Array<Date | number | string> | Date | number | boolean | IRule | {
                test: RegExp | Function;
                message: string;
            } | {
                test: RegExp | Function;
                messageId: string;
            }) {
                let self: Vue = this,
                    validations = $.cloneObject(self.validations);

                if (!$.isString(pathOrRule) && $.isObject(pathOrRule)) {
                    vue.set(self, 'validations', updateValidator.apply(validations, [pathOrRule]));
                } else if ($.isString(pathOrRule) && $.isObject(rule)) {
                    $.update(validations, pathOrRule, rule);

                    vue.set(self, 'validations', validations);
                }
            };
        }
    };

export { validate };