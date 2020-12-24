/// <reference path="../reference.ts"/>

module nts.uk.ui {

    export interface WindowSize {
        width: number;
        height: number;
    }
    export interface RootViewModel {
        kiban: KibanViewModel;
        content: nts.uk.ui.vm.ViewModel;
        errors: {
            isEmpty: KnockoutComputed<boolean>;
        };
    }

    export let _viewModel!: RootViewModel;

    /** Event to notify document ready to initialize UI. */
    export const documentReady = $.Callbacks();

    /** Event to notify ViewModel built to bind. */
    export const viewModelBuilt = $.Callbacks();

    /** Event to notify ViewModel applied bindings. */
    export const viewModelApplied = $.Callbacks();

    // Kiban ViewModel
    class KibanViewModel {
        // deprecate
        title!: KnockoutComputed<string>;

        systemName: KnockoutObservable<string> = ko.observable("");
        programName: KnockoutObservable<string> = ko.observable("");

        // error model
        errorDialogViewModel!: errors.ErrorsViewModel;

        // set page as view or modal
        mode: KnockoutObservable<'view' | 'modal'> = ko.observable(undefined);

        // subscriber windows size
        size: KnockoutObservable<WindowSize> = ko.observable({ width: window.innerWidth, height: window.innerHeight });

        // show or hide header
        header: KnockoutObservable<boolean> = ko.observable(true);

        // show or hide notification
        notification: KnockoutObservable<string> = ko.observable('');

        constructor(dialogOptions?: any) {
            const vm = this;

            vm.title = ko.computed({
                read: () => {
                    document.title = ko.unwrap(vm.systemName);

                    return document.title;
                }
            });

            vm.errorDialogViewModel = new errors.ErrorsViewModel(dialogOptions);

            vm.mode
                .subscribe((m) => {
                    if (m === 'view') {
                        $('body>div:first-child').addClass('view');
                        $('body>div:first-child').removeClass('modal');
                    } else {
                        $('body>div:first-child').addClass('modal');
                        $('body>div:first-child').removeClass('view');
                    }
                });

            vm.header
                .subscribe((c) => {
                    if (!c) {
                        $('body #header').addClass('hidden');
                    } else {
                        $('body #header').removeClass('hidden');
                    }
                });
        }
    }

    export module init {
        let _start: () => void;

        _.extend(__viewContext, {
            ready: (callback: () => void) => _start = callback,
            bind: function (content: any, dialogOptions?: any) {
                const { systemName } = __viewContext.env;
                const kiban = new KibanViewModel(dialogOptions);
                const isEmpty = ko.computed(() => !kiban.errorDialogViewModel.occurs());

                // update title of name
                kiban.systemName(systemName);

                // update mode of view
                kiban.mode(window === window.top ? 'view' : 'modal');

                // update header 
                kiban.header(!__viewContext.noHeader);

                // update notification
                kiban.notification(__viewContext.program.operationSetting.message);

                _.extend(nts.uk.ui, { _viewModel: { kiban, content, errors: { isEmpty } } });

                viewModelBuilt.fire(_viewModel);

                // bind viewmodel to document body
                ko.applyBindings(_viewModel, document.body);

                viewModelApplied.fire(_viewModel);

                // off event reset for class reset-not-apply
                $(".reset-not-apply").find(".reset-element").off("reset");

                nts.uk.cookie.remove("startfrommenu", { path: "/" });

                $('div[id^=functions-area]')
                    .each((__: number, e: HTMLElement) => {
                        if (!e.classList.contains('functions-area')) {
                            ko.applyBindingsToNode(e,
                                {
                                    'ui-function-bar': e.className.match(/bottom$/) ? 'bottom' : 'top',
                                    title: e.getAttribute('data-title') || true,
                                    back: e.getAttribute('data-url')
                                }, _viewModel);

                            e.removeAttribute('data-url');
                            e.removeAttribute('data-title');
                        }
                    });

                $('div[id^=contents-area]')
                    .each((__: number, e: HTMLElement) => {
                        if (!e.classList.contains('contents-area')) {
                            ko.applyBindingsToNode(e, { 'ui-contents': 0 }, _viewModel);
                        }
                    });

                // update size
                $(window)
                    .on('wd.resize', () => {
                        kiban.size({
                            width: window.innerWidth,
                            height: window.innerHeight
                        });
                    })
                    .on('resize', () => $(window).trigger('wd.resize'));
            }
        });

        const startP = function () {
            if (!cantCall()) {
                _start.apply(__viewContext, [__viewContext]);
            } else {
                loadEmployeeCodeConstraints()
                    .always(() => _start.apply(__viewContext, [__viewContext]));
            }

            // Menu
            /*if ($(document).find("#header").length > 0) {
                menu.request();
            } else if (!util.isInFrame() && !__viewContext.noHeader) {
                let header = "<div id='header'><div id='menu-header'>"
                    + "<div id='logo-area' class='cf'>"
                    + "<div id='logo'>勤次郎</div>"
                    + "<div id='user-info' class='cf'>"
                    + "<div id='company' class='cf' />"
                    + "<div id='user' class='cf' />"
                    + "</div></div>"
                    + "<div id='nav-area' class='cf' />"
                    + "<div id='pg-area' class='cf' />"
                    + "</div></div>";
                $("#master-wrapper").prepend(header);
                menu.request();
            }*/
        };

        const noSessionWebScreens = [
            "/view/sample/",
            "/view/common/error/",
            "/view/spr/index.xhtml",
            "/view/ccg/007/",
            "/view/kdw/003/a/index.xhtml",
            "/view/ccg/033/index.xhtml",
            "/view/kdp/003/a/index.xhtml",
            "/view/kdp/003/f/index.xhtml",
            "/view/kdp/004/a/index.xhtml",
            "/view/kdp/005/a/index.xhtml"
        ];

        let cantCall = function () {
            return !_.some(noSessionWebScreens, (w: string) => request.location.current.rawUrl.indexOf(w) > -1) || request.location.current.rawUrl.indexOf("/view/sample/component/editor/text-editor.xhtml") > -1;
        };

        let getEmployeeSetting = function () {
            const EMP_SESSION = 'nts.uk.session.EMPLOYEE_SETTING';

            let dfd = $.Deferred(),
                es = nts.uk.sessionStorage.getItem(EMP_SESSION);
            if (es.isPresent()) {
                dfd.resolve(JSON.parse(es.get()));
            } else {
                request
                    .ajax("com", "/bs/employee/setting/code/find")
                    .done((constraints: any) => {
                        nts.uk.sessionStorage.setItemAsJson(EMP_SESSION, constraints);
                        dfd.resolve(constraints);
                    });
            }

            return dfd.promise();
        };

        let loadEmployeeCodeConstraints = function () {
            let self = this,
                dfd = $.Deferred();

            getEmployeeSetting().done(res => {

                let formatOption: any = {
                    autofill: true
                };

                if (res.ceMethodAttr === 0) {
                    formatOption.filldirection = "left";
                    formatOption.fillcharacter = "0";
                } else if (res.ceMethodAttr === 1) {
                    formatOption.filldirection = "right";
                    formatOption.fillcharacter = "0";
                } else if (res.ceMethodAttr === 2) {
                    formatOption.filldirection = "left";
                    formatOption.fillcharacter = " ";
                } else {
                    formatOption.filldirection = "right";
                    formatOption.fillcharacter = " ";
                }

                // if not have primitive, create new
                if (!__viewContext.primitiveValueConstraints) {
                    _.extend(__viewContext, {
                        primitiveValueConstraints: {
                            EmployeeCode: {
                                valueType: "String",
                                charType: "AlphaNumeric",
                                maxLength: res.numberOfDigits,
                                formatOption: formatOption
                            }
                        }
                    });
                } else {
                    // extend primitive constraint
                    _.extend(__viewContext.primitiveValueConstraints, {
                        EmployeeCode: {
                            valueType: "String",
                            charType: "AlphaNumeric",
                            maxLength: res.numberOfDigits,
                            formatOption: formatOption
                        }
                    });
                }

                dfd.resolve();
            }).fail(res => {
                dfd.reject();
            });

            return dfd.promise();
        };

        $(function () {
            _.extend(__viewContext, {
                noHeader: (__viewContext.noHeader === true) || $("body").hasClass("no-header"),
                transferred: uk.sessionStorage
                    .getItem(uk.request.STORAGE_KEY_TRANSFER_DATA)
                    .map(v => JSON.parse(v))
            });

            documentReady.fire();

            if ($(".html-loading").length <= 0) {
                startP();
                return;
            }

            let dfd: JQueryDeferred<any>[] = [];

            _.forEach($(".html-loading").toArray(), (e: HTMLElement) => {
                let $container = $(e);
                let dX = $.Deferred();

                $container
                    .load($container.attr("link"), () => dX.resolve());

                dfd.push(dX);
                dX.promise();
            });

            $.when(...dfd)
                .then(function (data, textStatus, jqXHR) {
                    $('.html-loading').contents().unwrap();
                    startP();
                });
        });


        $(function () {
            let lastPause: number = Date.now();

            $(window)
                .keydown((e: JQueryEventObject) => {
                    if (e.keyCode === 19) {
                        const now: number = Date.now();

                        if (now - lastPause < 500) {
                            ui.dialog.version();
                        }

                        lastPause = now;
                    }
                });
        });
    }
}
