import { dom } from '@app/utils';

document.addEventListener("click", function (e) {
    let clicked: HTMLElement | null = null;

    // dropdown menu
    ((evt: MouseEvent) => {
        for (let node = evt.target as HTMLElement; node != document.body; node = node.parentNode as HTMLElement) {
            if (!node || !node.parentNode) {
                break;
            }

            if (dom.getAttr(node, 'data-dismiss') == "false") {
                clicked = node;
                break;
            } else if (dom.hasClass(node, 'dropdown') || dom.hasClass(node, 'dropdown-toggle') || dom.getAttr(node, 'data-toggle') == "dropdown") {
                clicked = node;
                break;
            }
        }

        if (clicked) {
            [].slice.call(document.querySelectorAll('.dropdown-toggle, [data-toggle="dropdown"]'))
                .forEach((element: HTMLElement) => {
                    let parent = element.parentNode as HTMLElement,
                        dropdown = parent.querySelector('.dropdown-menu') as HTMLElement | null;

                    dom.addClass(parent, 'dropdown');
                    dom.removeClass(parent, 'dropup');

                    if (dropdown) {
                        if (clicked == element) {
                            if (!dom.hasClass(dropdown, 'show')) {
                                dom.addClass(dropdown, 'show');

                                let scrollTop = window.scrollY,
                                    scrollHeight = window.innerHeight,
                                    offsetTop = dropdown.getBoundingClientRect().top,
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

                let siblings = parent.nextSibling as HTMLElement;

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
            }
            evt.preventDefault();
        }
    })(e);

    // navbar-toggler
    if (dom.parents(e.target as HTMLElement, '.navbar')) {
        let toggler = () => {
            let target = e.target as HTMLElement;

            if (dom.hasClass(target, 'fa-caret-down') || dom.hasClass(target, 'navbar-toggler') || dom.getAttr(target, 'data-toggle') === 'collapse') {
                return target;
            }

            return null;
        };

        ((evt: MouseEvent) => {
            let target = toggler();
            if (target) {
                let parent = target.closest('.navbar') as HTMLElement;

                if (parent) {
                    let collapse = parent.querySelector('.collapse.navbar-collapse') as HTMLElement;

                    if (collapse) {
                        dom.toggleClass(collapse, 'show');
                    }
                }
            }
        })(e);

        let target = toggler(),
            collapse = document.querySelector('.navbar .collapse.navbar-collapse.show') as HTMLElement;

        if (!target && collapse) {
            let _targ = e.target as HTMLElement;

            if (dom.hasClass(_targ, 'dropdown')) {
                let dropdown = _targ.querySelector('dropdown-menu') as HTMLElement;

                if (dropdown) {
                    dom.toggleClass(dropdown, 'show');
                }
            } else if (dom.hasClass(_targ, 'dropdown-toggle')) {
                let dropdown = dom.next(_targ) as HTMLElement;

                if (dropdown) {
                    dom.toggleClass(dropdown, 'show');
                }
            } else {
                dom.removeClass(collapse, 'show');
            }
        }

        //side menu
        ((evt: MouseEvent) => {
            let target = evt.target as HTMLElement,
                btn = dom.parent(target) as HTMLElement;

            if ((target.tagName === 'SPAN' && dom.hasClass(btn, 'navbar-btn')) || dom.hasClass(target, 'navbar-btn')) {
                let sidebar = document.querySelector('#sidebar') as HTMLDivElement;

                if (sidebar) {
                    if (!dom.hasClass(sidebar, 'active')) {
                        if (!dom.hasClass(target, 'navbar-btn')) {
                            dom.addClass(btn, 'active');
                        } else {
                            dom.addClass(target, 'active');
                        }
                        dom.addClass(sidebar, 'active');
                    } else {
                        if (!dom.hasClass(target, 'navbar-btn')) {
                            dom.removeClass(btn, 'active');
                        } else {
                            dom.removeClass(target, 'active');
                        }
                        dom.removeClass(sidebar, 'active');
                    }
                }
            }
        })(e);
    }

    // other event
});
