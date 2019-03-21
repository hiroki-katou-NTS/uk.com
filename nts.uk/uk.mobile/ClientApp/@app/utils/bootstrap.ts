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

// show side bar on click
dom.registerGlobalEventHandler(document, 'click', '.sidebar .navbar-btn', evt => {
    let btn = evt.target as HTMLElement,
        sidebar = document.querySelector('.wrapper>.sidebar') as HTMLDivElement,
        hide = (sidebar: HTMLElement, mask: HTMLElement) => {
            dom.removeClass(btn, 'show');
            dom.removeClass(sidebar, 'show');

            if (mask) {
                if (document.body.contains(mask)) {
                    document.body.removeChild(mask);
                }
            }
            dom.removeClass(document.body, 'show-side-bar');
        };

    if (sidebar) {
        if (!dom.hasClass(sidebar, 'show')) {
            let mask = dom.create('div', { 'class': 'modal-backdrop show' });
            dom.data.set(sidebar, 'mask', mask);

            document.body.appendChild(mask);

            dom.registerOnceEventHandler(mask, 'click', evt => {
                hide(sidebar, evt.target);
            });

            dom.registerOnceEventHandler(mask, 'touch', evt => {
                hide(sidebar, evt.target);
            });

            setTimeout(() => {
                dom.addClass(btn, 'show');
                dom.addClass(sidebar, 'show');
            }, 100);
            dom.addClass(document.body, 'show-side-bar');
        } else {
            let mask = dom.data.get(sidebar, 'mask') as HTMLElement;
            hide(sidebar, mask)
        }
    }
});

dom.registerGlobalEventHandler(document, 'click', '.sidebar a', evt => {
    let toggle = evt.target as HTMLElement,
        container = toggle.closest('.sidebar');

    if (toggle) {
        if (dom.hasClass(toggle, 'dropdown-toggle')) {
            let collapse = (<HTMLElement>toggle.parentNode).querySelector('.collapse.list-unstyled') as HTMLElement;

            if (collapse) {
                if (!dom.hasClass(collapse, 'show')) {
                    dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                        dom.removeAttr(collapse, 'style');
                        dom.removeClass(collapse, 'collapsing');

                        dom.addClass(toggle, 'show');
                    });

                    dom.addClass(collapse, 'collapsing show');

                    setTimeout(() => {
                        dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
                    }, 100);
                } else {
                    dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                        dom.removeClass(collapse, 'collapsing show');

                        dom.removeClass(toggle, 'show');
                    });
        
                    dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
                    dom.addClass(collapse, 'collapsing');
        
                    setTimeout(() => {
                        dom.removeAttr(collapse, 'style');
                    }, 200);
                }
            }
        } else {

        }
    }
});

// show navbar-collapse if click to self button toggler
dom.registerGlobalEventHandler(document, 'click', '.navbar>.navbar-toggler', function showOrHide(evt: MouseEvent) {
    let btn = (<HTMLElement>evt.target) as HTMLElement,
        parent = btn.closest('.navbar') as HTMLElement,
        hide = (collapse: HTMLElement, mask?: HTMLElement) => {
            dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                dom.removeClass(collapse, 'collapsing show');
                dom.removeClass(btn, 'show');

                if (mask) {
                    if (document.body.contains(mask)) {
                        document.body.removeChild(mask);
                    }
                }
                dom.removeClass(document.body, 'show-menu-top');
            });

            dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
            dom.addClass(collapse, 'collapsing');

            setTimeout(() => {
                dom.removeAttr(collapse, 'style');
            }, 200);
        };

    if (parent) {
        let collapse = parent.querySelector('.collapse.navbar-collapse') as HTMLElement;

        if (collapse) {
            if (!dom.hasClass(collapse, 'show')) {
                let mask = dom.create('div', { 'class': 'modal-backdrop show' });
                dom.data.set(collapse, 'mask', mask);
                dom.addClass(document.body, 'show-menu-top');

                dom.registerOnceEventHandler(collapse, 'transitionend', evt => {
                    dom.removeAttr(collapse, 'style');
                    dom.removeClass(collapse, 'collapsing');

                    dom.addClass(btn, 'show');
                });

                dom.addClass(collapse, 'collapsing show');

                document.body.appendChild(mask);

                dom.registerOnceEventHandler(mask, 'click', evt => {
                    hide(collapse, evt.target);
                });

                setTimeout(() => {
                    dom.setAttr(collapse, 'style', 'height: ' + collapse.scrollHeight + 'px');
                }, 100);
            } else {
                let mask = dom.data.get(collapse, 'mask') as HTMLElement;

                hide(collapse, mask);
            }
        }
    }
});

dom.registerGlobalEventHandler(document, 'click', '.navbar-collapse.show a', evt => {
    let target = evt.target as HTMLElement;

    if (dom.hasClass(target, 'dropdown-toggle')) {
        debugger;
    } else {

    }
});