import { routes } from '@app/core/routes';
import { Vue, ComponentOptions } from '@app/provider';

import { $, dom } from '@app/utils';
import { resources, language } from '@app/plugins';
import { cssbeautify } from '@app/utils/css';
import classDecorator from 'vue-class-component';
import { Prop, Watch, Model, Provide, Emit, Mixins, Inject } from 'vue-property-decorator';

declare type VueClass<V> = {
    new(...args: any[]): V & Vue;
} & typeof Vue;

const styles: Array<string> = [];

export function component(options: ComponentOptions<Vue>): any {
    return function (Component: VueClass<Vue>) {
        // inject resouce
        if (options.resource) {
            $.merge(resources, options.resource);

            delete options.resource;
        }

        (options.mixins || (options.mixins = [])).push({
            components: {
                markdown: {
                    template: `<div class="markdown-content" v-html="markdown"></div>`,
                    computed: {
                        markdown() {
                            return ($.isString(options.markdown) ? options.markdown : options.markdown[language.current]) || '';
                        }
                    }
                }
            }
        });

        // render template 
        if (typeof options.template === "object") {
            const { render, staticRenderFns } = (<any>options.template).default;

            options.render = render;
            options.staticRenderFns = staticRenderFns;

            delete options.template;
        }

        // inject style sheet css
        if (options.style) {
            const head = document.querySelector('head'),
                $st = document.createElement('style');

            $st.setAttribute('type', 'text/css');
            $st.setAttribute('rel', 'stylesheet');

            $st.textContent = `\n${cssbeautify(options.style, {
                indent: '  ',
                openbrace: 'separate-line',
                autosemicolon: true
            })}`;

            delete options.style;

            (options.mixins || (options.mixins = [])).push({
                beforeCreate: function () {
                    styles.push(options.name);

                    if (styles.indexOf(options.name) > -1
                        && styles.indexOf(options.name) == styles.lastIndexOf(options.name)) {
                        if (head && $st.textContent != '') {
                            head.appendChild($st);
                        }
                    }
                },
                beforeDestroy: function () {
                    styles.splice(styles.indexOf(options.name), 1);

                    if (styles.indexOf(options.name) == -1) {
                        if (head && head.contains($st)) {
                            head.removeChild($st);
                        }
                    }
                }
            });
        }

        // call super decorator (vue-class-component)
        classDecorator(options)(Component);

        // initial route for component
        if (options.route) {
            if (typeof options.route === 'string') {
                let rt = $.find(routes, r => r.name === options.name),
                    mask = dom.create('div', { 'class': 'modal-backdrop show' });

                if (!rt) {
                    routes.push({
                        name: options.name,
                        path: options.route,
                        component: options
                    });

                    (options.mixins || (options.mixins = [])).push({
                        beforeMount() {
                            //document.body.appendChild(mask);
                        },
                        mounted() {
                            this.pgName = options.name;
                            /*if (document.body.contains(mask)) {
                                setTimeout(() => {
                                    document.body.removeChild(mask);
                                }, 50);
                            }*/
                        }
                    });
                } else {
                    console.error('Dupplicate view name: ' + options.name);
                }
            } else {
                if (options.route.parent) {
                    let rt = routes.find(r => r.path === (<any>options.route).parent),
                        mask = dom.create('div', { 'class': 'modal-backdrop show' });

                    if (rt) {
                        (rt.children || (rt.children = [])).push({
                            name: options.name,
                            path: options.route.url.replace(/^\/+/, ''),
                            component: options
                        });
                    } else {
                        routes.push({
                            name: options.name,
                            path: `${options.route.parent}/${options.route.url}`.replace(/\/+/g, '/'),
                            component: options
                        });
                    }

                    (options.mixins || (options.mixins = [])).push({
                        beforeMount() {
                            //document.body.appendChild(mask);
                        },
                        mounted() {
                            this.pgName = options.name;
                            /*if (document.body.contains(mask)) {
                                setTimeout(() => {
                                    document.body.removeChild(mask);
                                }, 50);
                            }*/
                        }
                    });
                }
            }

            delete options.route;
        }

        return options;
    }
};

export { Prop, Watch, Model, Provide, Emit, Mixins, Inject };