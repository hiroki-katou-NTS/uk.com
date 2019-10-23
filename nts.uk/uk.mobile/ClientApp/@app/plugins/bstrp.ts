import { dom } from '@app/utils';
import { Vue, VueConstructor } from '@app/provider';

const bstrp = {
    install(vue: VueConstructor<Vue>) {
        vue.mixin({
            mounted() {
                let root = this.$el as HTMLElement;

                if (root.nodeType != 8) {
                    // tabs
                    ((el: HTMLElement) => {
                        [].slice.call(el.querySelectorAll('.nav.nav-tabs .nav-link.active, .nav.nav-pills .nav-link.active'))
                            .forEach((link: HTMLElement) => link.click());
                    })(root);

                    // checkgroup
                    ((el: HTMLElement) => {
                        [].slice.call(el.querySelectorAll('.btn-group-toggle input'))
                            .forEach((input: HTMLInputElement) => {
                                let label = input.parentElement as HTMLElement;

                                dom.setAttr(label, 'class', 'btn btn-secondary');

                                if (label) {
                                    if (input.checked) {
                                        dom.addClass(label, 'btn-primary');
                                        dom.removeClass(label, 'btn-secondary');
                                    } else {
                                        dom.removeClass(label, 'btn-primary');
                                        dom.addClass(label, 'btn-secondary');
                                    }
                                }
                            });
                    })(root);
                }
            }
        });

        document.addEventListener('click', function (e) {
            let clicked: HTMLElement | null = null;

            // dropdown menu
            ((evt: MouseEvent) => {
                for (let node = evt.target as HTMLElement; node != document.body; node = node.parentElement as HTMLElement) {
                    if (!node || !node.parentElement) {
                        break;
                    }

                    if (dom.getAttr(node, 'data-dismiss') == 'false') {
                        clicked = node;
                        break;
                    } else if (dom.hasClass(node, 'dropdown') || dom.hasClass(node, 'dropdown-toggle') || dom.getAttr(node, 'data-toggle') == 'dropdown') {
                        clicked = node;
                        break;
                    }
                }

                if (clicked) {
                    [].slice.call(document.querySelectorAll('.dropdown-toggle, [data-toggle="dropdown"]'))
                        .forEach((element: HTMLElement) => {
                            let parent = element.parentElement as HTMLElement,
                                dropdown = parent.querySelector('.dropdown-menu') as HTMLElement | null;

                            dom.addClass(parent, 'dropdown');
                            dom.removeClass(parent, 'dropup');

                            if (dropdown) {
                                if (clicked == element) {
                                    if (!dom.hasClass(dropdown, 'show')) {
                                        dom.addClass(dropdown, 'show');

                                        let scrollTop = window.scrollY,
                                            scrollHeight = window.innerHeight,
                                            offsetTop = parent.offsetTop + parent.clientHeight,
                                            offsetHeight = dropdown.offsetHeight;

                                        if (scrollTop + scrollHeight <= offsetTop + offsetHeight) {
                                            dom.addClass(parent, 'dropup');
                                            dom.removeClass(parent, 'dropdown');
                                        } else {
                                            dom.addClass(parent, 'dropdown');
                                            dom.removeClass(parent, 'dropup');
                                        }
                                    } else {
                                        dom.removeClass(dropdown, 'show');
                                    }
                                } else {
                                    if (!clicked || clicked.getAttribute('data-dismiss') != 'false') {
                                        dom.removeClass(dropdown, 'show');
                                    }
                                }
                            }
                        });
                } else {
                    [].slice.call(document.querySelectorAll('.dropdown-menu'))
                        .forEach((element: HTMLElement) => {
                            dom.removeClass(element, 'show');

                            let parent = element.parentElement as HTMLElement;

                            if (parent) {
                                dom.removeClass(parent, 'dropdown dropup');
                            }
                        });
                }
            })(e);

            // tabs
            ((evt: MouseEvent) => {
                let target = evt.target as HTMLElement;

                if (dom.hasClass(target, 'nav-link') && !dom.hasClass(target, 'disabled')) {
                    let parent = target.closest('.nav.nav-tabs') || target.closest('.nav.nav-pills'),
                        href = dom.getAttr(target, 'href');

                    if (parent) {
                        [].slice.call(parent.querySelectorAll('.nav-link'))
                            .forEach((element: HTMLElement) => {
                                dom.removeClass(element, 'active');
                            });

                        dom.addClass(target, 'active');

                        let siblings = parent.nextElementSibling as HTMLElement;

                        if (siblings && href.match(/#.+/)) {
                            let tab = siblings.querySelector(href) as HTMLElement;

                            [].slice.call(siblings.querySelectorAll('.tab-pane'))
                                .forEach((element: HTMLElement) => {
                                    if (tab == element) {
                                        dom.addClass(element, 'show active');
                                    } else {
                                        dom.removeClass(element, 'show active');
                                    }
                                });
                        }
                        evt.preventDefault();
                    }
                    evt.preventDefault();
                }
            })(e);

            // other event
            // checkbox & radio
            // btn-group-toggle
            ((evt: MouseEvent) => {
                let input = evt.target as HTMLElement,
                    group = input.closest('.btn-group-toggle') as HTMLElement;

                if (group && input.tagName === 'INPUT' && ['radio', 'checkbox'].indexOf(dom.getAttr(input, 'type')) > -1) {
                    [].slice.call(group.querySelectorAll('input'))
                        .forEach((element: HTMLInputElement) => {
                            let btn = element.closest('.btn') as HTMLElement;

                            if (btn) {
                                if (element.checked) {
                                    dom.addClass(btn, 'btn-primary');
                                    dom.removeClass(btn, 'btn-secondary');
                                } else {
                                    dom.removeClass(btn, 'btn-primary');
                                    dom.addClass(btn, 'btn-secondary');
                                }
                            }
                        });
                }
            })(e);
        }, false);

        window.addEventListener('scroll', (evt) => {
            // toggle dropdown/dropup menu
            [].slice.call(document.querySelectorAll('.dropdown-menu.show'))
                .forEach((dropdown: HTMLElement) => {
                    let parent = dropdown.closest('.dropdown, .dropup') as HTMLElement;
                    if (parent) {

                        let scrollTop = window.scrollY,
                            scrollHeight = window.innerHeight,
                            offsetTop = parent.offsetTop,
                            offsetHeight = dropdown.offsetHeight;

                        if (scrollTop + scrollHeight <= offsetTop + offsetHeight + 35) {
                            dom.addClass(parent, 'dropup');
                            dom.removeClass(parent, 'dropdown');
                        } else {
                            dom.addClass(parent, 'dropdown');
                            dom.removeClass(parent, 'dropup');
                        }
                    }
                });
        });
    }
};

export { bstrp };