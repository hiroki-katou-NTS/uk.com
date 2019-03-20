import { dom } from '@app/utils';

const matches = ((doc: any) =>
    doc.matchesSelector ||
    doc.webkitMatchesSelector ||
    doc.mozMatchesSelector ||
    doc.oMatchesSelector ||
    doc.msMatchesSelector
)(document.documentElement);

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

    // other event
}, false);


dom.registerGlobalEventHandler(document, 'click', '.navbar>.navbar-btn', function showOrHide(evt: MouseEvent) {
    let btn = evt.target as HTMLElement,
        sidebar = document.querySelector('.wrapper>.sidebar') as HTMLDivElement;

    if (sidebar) {
        if (!dom.hasClass(sidebar, 'active')) {
            dom.addClass(btn, 'active');
            dom.addClass(sidebar, 'active');

            // if click out side collapse, close it
            dom.registerOnceClickOutEventHandler(sidebar, () => {
                dom.dispatchEvent(btn, evt, showOrHide);
            });
        } else {
            setTimeout(() => {
                dom.removeClass(btn, 'active');
                dom.removeClass(sidebar, 'active');
            }, 100);
        }
    }
});

dom.registerGlobalEventHandler(document, 'click', '.navbar>.navbar-toggler', function showOrHide(evt: MouseEvent) {
    let btn = (<HTMLElement>evt.target) as HTMLElement,
        parent = btn.closest('.navbar') as HTMLElement,
        icon = btn.querySelector('i.fas') as HTMLElement;

    if (parent) {
        let collapse = parent.querySelector('.collapse.navbar-collapse') as HTMLElement;

        if (collapse) {
            if (!dom.hasClass(collapse, 'show')) {
                // if click out side collapse, close it
                dom.registerOnceClickOutEventHandler(collapse, () => {
                    dom.dispatchEvent(btn, evt, showOrHide);
                });

                dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                    dom.removeAttr(collapse, 'style');
                    dom.removeClass(collapse, 'collapsing');

                    if (icon) {
                        dom.setAttr(icon, 'class', 'fas fa-caret-up');
                    }
                });

                dom.addClass(collapse, 'collapsing show');

                setTimeout(() => {
                    dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
                }, 100);
            } else {
                dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                    dom.removeClass(collapse, 'collapsing show');

                    if (icon) {
                        dom.setAttr(icon, 'class', 'fas fa-caret-down');
                    }
                });

                dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
                dom.addClass(collapse, 'collapsing');

                setTimeout(() => {
                    dom.removeAttr(collapse, 'style');
                }, 200);
            }
        }
    }
});

dom.registerGlobalEventHandler(document, 'click', '.navbar-collapse.show a:not(.dropdown-toggle)', evt => {
    let collapse = (<HTMLElement>evt.target).closest('.navbar-collapse.show') as HTMLElement;

    if (collapse) {
        let btn = collapse.previousElementSibling as HTMLElement;

        if (btn) {
            let icon = btn.querySelector('i.fas') as HTMLElement;
            dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                dom.removeClass(collapse, 'collapsing show');

                if (icon) {
                    dom.setAttr(icon, 'class', 'fas fa-caret-down');
                }
            });

            dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
            dom.addClass(collapse, 'collapsing');

            setTimeout(() => {
                dom.removeAttr(collapse, 'style');
            }, 200);
        }
    }
});