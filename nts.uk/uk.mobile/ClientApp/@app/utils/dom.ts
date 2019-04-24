import { obj } from '@app/utils';

const dom = {
    setHtml: (element: HTMLElement, html: string | number) => {
        element.innerHTML = html.toString();
    },
    setTextContent: (element: HTMLElement, html: string | number) => {
        element.textContent = html.toString();
    },
    create: (tag: string, options?: { [key: string]: string | number }) => {
        let element = document.createElement(tag);

        if (options) {
            Object.keys(options).forEach((key: string) => {
                let value = options[key];

                switch (key) {
                    case 'html':
                        dom.setHtml(element, value.toString());
                        break;
                    case 'text':
                        dom.setTextContent(element, value.toString());
                        break;
                    default:
                        dom.setAttr(element, key, value);
                        break;
                }
            });
        }

        return element;
    },
    empty: (element: HTMLElement) => {
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
    },
    remove: (element: HTMLElement) => {
        let parent: Node | null = element.parentNode;

        if (parent) {
            parent.removeChild(element);
            return true;
        }

        return false;
    },
    isEmpty: (element: HTMLElement) => {
        return element.hasChildNodes();
    },
    next: (element: HTMLElement) => {
        return element.nextSibling;
    },
    preview: (element: HTMLElement) => {
        return element.previousSibling;
    },
    setAttr: (element: HTMLElement, key: string, value: string | number) => {
        element && element.setAttribute && element.setAttribute(key, value.toString());
    },
    getAttr: (element: HTMLElement, key: string) => {
        return element && element.getAttribute && element.getAttribute(key);
    },
    removeAttr: (element: HTMLElement, key: string) => {
        element && element.removeAttribute && element.removeAttribute(key);
    },
    hasClass: (element: HTMLElement, classCss: string) => {
        return element && element.className && element.classList.contains(classCss.trim());
    },
    addClass: (element: HTMLElement, classCss: Array<string> | string) => {
        if (element) {
            if (typeof classCss == 'string') {
                if (classCss.indexOf(' ') == -1) {
                    classCss = [classCss];
                } else {
                    classCss = [].slice.call(classCss.split(/\s/));
                }
            }

            [].slice.call(classCss)
                .forEach((c: string) => element && element.classList && element.classList.add(c.trim()));
        }
    },
    removeClass: (element: HTMLElement, classCss: Array<string> | string) => {
        if (element) {
            if (typeof classCss == 'string') {
                if (classCss.indexOf(' ') == -1) {
                    classCss = [classCss];
                } else {
                    classCss = [].slice.call(classCss.split(/\s/));
                }
            }

            [].slice.call(classCss)
                .forEach((css: string) => element.classList.remove(css));

            if (!dom.getAttr(element, 'class')) {
                dom.removeAttr(element, 'class');
            }
        }
    },
    toggleClass: (element: HTMLElement, classCss: Array<string> | string) => {
        if (element) {
            if (typeof classCss == 'string') {
                if (classCss.indexOf(' ') == -1) {
                    classCss = [classCss];
                } else {
                    classCss = [].slice.call(classCss.split(/\s/));
                }
            }

            [].slice.call(classCss)
                .forEach((css: string) => {
                    if (!dom.hasClass(element, css)) {
                        dom.addClass(element, css);
                    } else {
                        dom.removeClass(element, css);
                    }
                });
        }
    },
    animate: (element: HTMLElement, classAnimated: string, removeAfterEnd: boolean = false) => {
        let rmClass = dom.removeClass,
            addClass = dom.addClass;

        if (element) {
            if (classAnimated.indexOf('animated') == -1) {
                classAnimated = `animated ${classAnimated.trim()}`;
            }

            rmClass(element, classAnimated);

            setTimeout(() => {
                addClass(element, classAnimated);

                if (removeAfterEnd) {
                    dom.registerOnceEventHandler(element, 'animationend', function () {
                        rmClass(element, classAnimated);
                    });
                }
            }, 10);
        }
    },
    getScroll: (element: HTMLElement, side: string = 'top') => {
        if (element.nodeName === 'BODY' || element.nodeName === 'HTML') {
            let html = element.ownerDocument!.documentElement,
                scrollingElement = element.ownerDocument!.scrollingElement || html;

            if (scrollingElement) {
                return side === 'top' ? scrollingElement.scrollTop : scrollingElement.scrollLeft;
            }
        }

        return side === 'top' ? element.scrollTop : element.scrollLeft;
    },
    parent: (element: HTMLElement) => {
        return element.parentNode as HTMLElement;
    },
    parents: (element: HTMLElement, helper: string) => {
        return element.closest(helper) as HTMLElement;
    },
    removeEventHandler: (element: Window | Document | HTMLElement, eventType: string | any, handler: (evt: any) => any) => {
        element.removeEventListener(eventType, handler, false);
    },
    registerEventHandler: (element: Window | Document | HTMLElement, eventType: string | any, handler: (evt: any) => any) => {
        element.addEventListener(eventType, handler, false);
    },
    registerOnceEventHandler: (element: Window | Document | HTMLElement, eventType: string | any, handler: (evt: any) => any) => {
        dom.registerEventHandler(element, eventType, function handlerWrapper(evt: any) {
            handler(evt);
            dom.removeEventHandler(element, eventType, handlerWrapper);
        });
    },
    registerOnceClickOutEventHandler(element: HTMLElement, handler: () => any) {
        dom.registerOnceEventHandler(document, 'click', (evt: MouseEvent) => {
            let clo,
                target = event.target as HTMLElement;

            while (target) {
                if (target === element) {
                    clo = target;
                }

                target = target.parentNode as HTMLElement;
            }

            if (!clo) {
                return dom.dispatchEvent(element, evt, handler);
            }
        });
    },
    registerGlobalEventHandler(element: Document | HTMLElement, eventName: string, matcher: string, handler: (evt: any) => any) {
        dom.registerEventHandler(element, eventName, (event: any) => {
            [].slice.call(element.querySelectorAll(matcher))
                .forEach((match) => {
                    let target = event.target as HTMLElement;

                    while (target && target !== element) {
                        if (target === match) {
                            Object.defineProperty(event,
                                'target', {
                                    value: match,
                                    writable: false
                                });

                            return handler.call(match, event);
                        }

                        target = target.parentNode as HTMLElement;
                    }
                });
        });
    },
    registerGlobalClickOutEventHandler(element: Document | HTMLElement, matcher: string, handler: (evt: any) => any) {
        dom.registerEventHandler(element, 'click', (event: any) => {
            [].slice.call(element.querySelectorAll(matcher))
                .forEach((match) => {
                    let clo,
                        target = event.target as HTMLElement;

                    while (target) {
                        if (target === match) {
                            clo = target;
                        }

                        target = target.parentNode as HTMLElement;
                    }
                    
                    if (!clo) {
                        if (event.target != match) {
                            return dom.dispatchEvent(match, event, handler);
                        } else {
                            return null;
                        }
                    }
                });
        });
    },
    dispatchEvent(element: HTMLElement, event: Event, handler: (evt: Event) => any) {
        // try {
        Object.defineProperty(event,
            'target', {
                value: element,
                writable: false
            });
        // } catch {
        // }

        return handler.call(element, event);
    },
    data: (function () {
        let dataStoreKeyExpandoPropertyName = '__nts__' + (new Date()).getTime(),
            getDataForNode = function (node, createIfNotFound) {
                let dataForNode = node[dataStoreKeyExpandoPropertyName];
                if (!dataForNode && createIfNotFound) {
                    dataForNode = node[dataStoreKeyExpandoPropertyName] = {};
                }
                return dataForNode;
            },
            clear = function (node) {
                if (node[dataStoreKeyExpandoPropertyName]) {
                    delete node[dataStoreKeyExpandoPropertyName];
                    return true; // Exposing "did clean" flag purely so specs can infer whether things have been cleaned up as intended
                }
                return false;
            };

        return {
            get (node: HTMLElement, key: string) {
                let dataForNode = getDataForNode(node, false);
                return dataForNode && dataForNode[key];
            },
            set (node: HTMLElement, key: string, value: any) {
                // Make sure we don't actually create a new domData key if we are actually deleting a value
                let dataForNode = getDataForNode(node, value !== undefined /* createIfNotFound */);
                dataForNode && (dataForNode[key] = value);
            },
            getOrSet (node: HTMLElement, key: string, value: any) {
                let dataForNode = getDataForNode(node, true /* createIfNotFound */);
                return dataForNode[key] || (dataForNode[key] = value);
            },
            clear
        };
    })()
};

export { dom };