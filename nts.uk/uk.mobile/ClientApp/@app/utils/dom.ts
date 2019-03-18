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
                .forEach((c: string) => element &&  element.classList && element.classList.add(c.trim()));
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
            addClass = dom.addClass,
            rgOneEvent = (element: HTMLElement, name: string, trigger: Function) => {

            }; ///ko.utils.registerOnceEventHandler;

        if (element) {
            if (classAnimated.indexOf('animated') == -1) {
                classAnimated = `animated ${classAnimated.trim()}`;
            }

            rmClass(element, classAnimated);

            setTimeout(() => {
                addClass(element, classAnimated);

                if (removeAfterEnd) {
                    rgOneEvent(element, 'animationend', function () {
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
    removeEventHandler: (element: any, eventType: string | any, handler: (evt: any) => any) => {
        element.removeEventListener(eventType, handler, false);
    },
    registerEventHandler: (element: HTMLElement, eventType: string | any, handler: (evt: any) => any) => {
        element.addEventListener(eventType, handler, false);
    },
    registerOnceEventHandler: (element: HTMLElement, eventType: string | any, handler: (evt: any) => any) => {
        dom.registerEventHandler(element, eventType, function handlerWrapper(evt: any) {
            handler(evt);
            dom.removeEventHandler(element, eventType, handlerWrapper);
        });
    },
};

export { dom };