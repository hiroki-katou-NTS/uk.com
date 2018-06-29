var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var menu;
            (function (menu) {
                var DATA_TITLEITEM_PGID = "pgid";
                var DATA_TITLEITEM_PGNAME = "pgname";
                var showingItem;
                var MenuItem = (function () {
                    function MenuItem(name, path) {
                        this.name = name;
                        this.path = path;
                    }
                    return MenuItem;
                }());
                function createMenuSelect($menuNav, menuSet) {
                    var $cate = $("<li class='category'/>").addClass("menu-select").appendTo($menuNav);
                    var $cateName = $("<div class='category-name'/>").html("&#9776;").appendTo($cate);
                    var $menuItems = $("<ul class='menu-items'/>").appendTo($cate);
                    $menuItems.append($("<li class='menu-item'/>").text("メニュー選択"));
                    $menuItems.append($("<hr/>").css({ margin: "5px 0px" }));
                    _.forEach(menuSet, function (item, i) {
                        $menuItems.append($("<li class='menu-item'/>")
                            .data("code", item.companyId + ":" + item.webMenuCode)
                            .text(item.webMenuName).on(constants.CLICK, function () {
                            uk.localStorage.setItem(constants.MENU, $(this).data("code"));
                            $menuNav.find(".category:eq(0)").off();
                            $menuNav.find(".category:gt(0)").remove();
                            generate($menuNav, item);
                            _.defer(function () {
                                showingItem = undefined;
                            });
                        }));
                    });
                    $menuItems.append("<br/>");
                }
                function request() {
                    $("#logo").on(constants.CLICK, function () {
                        uk.request.jumpToTopPage();
                    });
                    displayUserInfo();
                    nts.uk.request.ajax(constants.APP_ID, constants.MenuDataPath).done(function (menuSet) {
                        var $menuNav = $("<ul/>").attr("id", "menu-nav").appendTo($("#nav-area"));
                        if (!menuSet || menuSet.length === 0)
                            return;
                        createMenuSelect($menuNav, menuSet);
                        var menuCode = uk.localStorage.getItem(constants.MENU);
                        if (menuCode.isPresent()) {
                            var parts_1 = menuCode.get().split(":");
                            var selectedMenu = _.find(menuSet, function (m) {
                                return m.companyId === parts_1[0] && m.webMenuCode === parts_1[1];
                            });
                            !uk.util.isNullOrUndefined(selectedMenu) ? generate($menuNav, selectedMenu)
                                : generate($menuNav, menuSet[0]);
                        }
                        else {
                            generate($menuNav, menuSet[0]);
                        }
                    });
                    getProgram();
                }
                menu.request = request;
                function generate($menuNav, menuSet) {
                    _.forEach(menuSet.menuBar, function (category) {
                        var $cate = $("<li class='category'/>").appendTo($menuNav);
                        if (category.selectedAttr === 1) {
                            $cate.addClass("direct").data("path", category.link).on(constants.CLICK, function () {
                                uk.request.jumpToMenu(category.link);
                            });
                        }
                        var $cateName = $("<div class='category-name'/>")
                            .css({ background: category.backgroundColor, color: category.textColor || "#FFF" })
                            .text(category.menuBarName).appendTo($cate);
                        var $menuItems = $("<ul class='menu-items'/>").appendTo($cate);
                        if (category.items && category.items.length > 0) {
                            _.forEach(category.items, function (item) {
                                $menuItems.append($("<li class='menu-item' path='" + item.path + "'/>").text(item.name));
                            });
                        }
                        else if (category.titleMenu && category.titleMenu.length > 0) {
                            titleMenu.createTitles($menuItems, category.titleMenu);
                        }
                    });
                    init();
                }
                function displayUserInfo() {
                    var $userInfo = $("#user-info");
                    var $company = $userInfo.find("#company");
                    var $user = $userInfo.find("#user");
                    var $userName;
                    var notThen = function ($container, target, op) {
                        if (!$container.is(target) && $container.has(target).length === 0) {
                            op();
                        }
                    };
                    nts.uk.request.ajax(constants.APP_ID, constants.Companies).done(function (companies) {
                        if (!companies || companies.length === 0)
                            return;
                        var $companyName = $("<span/>").attr("id", "company-name");
                        nts.uk.request.ajax(constants.APP_ID, constants.Company).done(function (companyId) {
                            var comp = _.find(companies, function (c) {
                                return c.companyId === companyId;
                            });
                            if (comp)
                                $companyName.text(comp.companyName).appendTo($company);
                        });
                        var $companySelect = $("<div/>").addClass("company-select cf");
                        $companySelect.appendTo($company);
                        $("<div/>").addClass("ui-icon ui-icon-caret-1-s").appendTo($companySelect);
                        var $companyList = $("<ul class='menu-items company-list'/>").appendTo($companySelect);
                        _.forEach(companies, function (comp, i) {
                            var $compItem = $("<li class='menu-item company-item'/>").text(comp.companyName).appendTo($companyList);
                            $compItem.on(constants.CLICK, function () {
                                nts.uk.request.ajax(constants.APP_ID, constants.ChangeCompany, comp.companyId)
                                    .done(function (personName) {
                                    $companyName.text(comp.companyName);
                                    $userName.text(personName);
                                    $companyList.css("right", $user.outerWidth() + 30);
                                });
                            });
                        });
                        $companySelect.on(constants.CLICK, function () {
                            if ($companyList.css("display") === "none") {
                                $companyList.fadeIn(100);
                                return;
                            }
                            $companyList.fadeOut(100);
                        });
                        nts.uk.request.ajax(constants.APP_ID, constants.UserName).done(function (userName) {
                            var $userImage = $("<div/>").attr("id", "user-image").addClass("ui-icon ui-icon-person").appendTo($user);
                            $userImage.css("margin-right", "6px").on(constants.CLICK, function () {
                            });
                            $userName = $("<span/>").attr("id", "user-name").text(userName).appendTo($user);
                            var $userSettings = $("<div/>").addClass("user-settings cf").appendTo($user);
                            $("<div class='ui-icon ui-icon-caret-1-s'/>").appendTo($userSettings);
                            var userOptions = [new MenuItem("個人情報の設定"), new MenuItem("ログアウト")];
                            var $userOptions = $("<ul class='menu-items user-options'/>").appendTo($userSettings);
                            _.forEach(userOptions, function (option, i) {
                                var $li = $("<li class='menu-item'/>").text(option.name);
                                $userOptions.append($li);
                                if (i === 0) {
                                    $li.on(constants.CLICK, function () {
                                    });
                                    return;
                                }
                                $li.on(constants.CLICK, function () {
                                    nts.uk.request.ajax(constants.APP_ID, constants.Logout).done(function () {
                                        nts.uk.cookie.remove("nts.uk.sescon", { path: "/" });
                                        nts.uk.request.login.jumpToUsedLoginPage();
                                    });
                                });
                            });
                            $companyList.css("right", $user.outerWidth() + 30);
                            $userSettings.on(constants.CLICK, function () {
                                if ($userOptions.css("display") === "none") {
                                    $userOptions.fadeIn(100);
                                    return;
                                }
                                $userOptions.fadeOut(100);
                            });
                            $(document).on(constants.CLICK, function (evt) {
                                notThen($companySelect, evt.target, function () {
                                    $companyList.fadeOut(100);
                                });
                                notThen($userSettings, evt.target, function () {
                                    $userOptions.fadeOut(100);
                                });
                            });
                        });
                    });
                }
                menu.displayUserInfo = displayUserInfo;
                function getProgram() {
                    nts.uk.request.ajax(constants.APP_ID, constants.PG).done(function (pg) {
                        var programName = "";
                        var queryString = __viewContext.program.queryString;
                        if (queryString) {
                            var program = _.find(pg, function (p) {
                                return p.param === queryString;
                            });
                            if (program) {
                                programName = program.name;
                            }
                        }
                        else if (programName === "" && pg && pg.length > 0) {
                            programName = pg[0].name;
                        }
                        ui.viewModelBuilt.add(function () {
                            ui._viewModel.kiban.programName(programName);
                        });
                        var $pgArea = $("#pg-area");
                        $("<div/>").attr("id", "pg-name").text(programName).appendTo($pgArea);
                        var $manualArea = $("<div/>").attr("id", "manual").appendTo($pgArea);
                        var $manualBtn = $("<button class='manual-button'/>").text("?").appendTo($manualArea);
                        $manualBtn.on(constants.CLICK, function () {
                        });
                        var $tglBtn = $("<div class='tgl cf'/>").appendTo($manualArea);
                        $tglBtn.append($("<div class='ui-icon ui-icon-caret-1-s'/>"));
                        $tglBtn.on(constants.CLICK, function () {
                        });
                    });
                }
                function init() {
                    var $navArea = $("#nav-area");
                    var $menuItems = $("#menu-nav li.category:not(.direct)");
                    function closeItem() {
                        var $item = $("#menu-nav li.category:eq(" + showingItem + ")");
                        $item.find(".category-name").removeClass("opening");
                        $item.find("ul, div.title-menu").fadeOut(100);
                    }
                    function openItem($item) {
                        $item.find(".category-name").addClass("opening");
                        $item.find("ul, div.title-menu").fadeIn(100);
                    }
                    $(document).on(constants.CLICK, function (evt) {
                        if (!$navArea.is(evt.target) && $navArea.has(evt.target).length === 0
                            && !uk.util.isNullOrUndefined(showingItem)) {
                            closeItem();
                            showingItem = undefined;
                        }
                    });
                    $menuItems.hover(function () {
                        var $item = $(this);
                        var ith = $item.index();
                        if (uk.util.isNullOrUndefined(showingItem) || showingItem === ith)
                            return;
                        closeItem();
                        setTimeout(function () {
                            openItem($item);
                        }, 14);
                        showingItem = ith;
                    });
                    $menuItems.on(constants.CLICK, function (event) {
                        var $item = $(this);
                        showingItem = $item.index();
                        if ($item.find(".category-name").hasClass("opening") && showingItem === 0) {
                            closeItem();
                            return;
                        }
                        openItem($item);
                    });
                    $(".menu-item").on(constants.CLICK, function () {
                        var path = $(this).data('path');
                        if (path)
                            nts.uk.request.jump(path);
                    });
                }
                var titleMenu;
                (function (titleMenu) {
                    titleMenu.WIDTH = 192;
                    titleMenu.FR = 20;
                    function createTitles($category, titles) {
                        var $title = $("<div/>").addClass("title-menu").appendTo($category);
                        var width = 0, height, maxHeight = 0;
                        _.forEach(titles, function (t, i) {
                            height = 60;
                            var left = titleMenu.WIDTH * i + 3;
                            if (i > 0) {
                                left += titleMenu.FR * i;
                            }
                            if (i === titles.length - 1) {
                                width = left + titleMenu.WIDTH + 7;
                            }
                            var $titleDiv = $("<div/>").addClass("title-div").css({ left: left }).appendTo($title);
                            var $titleName = $("<div/>").addClass("title-name").text(t.titleMenuName)
                                .css({ background: t.backgroundColor, color: t.textColor }).appendTo($titleDiv);
                            var $titleImage = $("<img/>").addClass("title-image").hide();
                            $titleDiv.append($titleImage);
                            if (!_.isNull(t.imageFile) && !_.isUndefined(t.imageFile) && !_.isEmpty(t.imageFile)) {
                                var fqpImage = nts.uk.request.file.pathToGet(t.imageFile);
                                $titleImage.attr("src", fqpImage).show();
                                height += 80;
                            }
                            if (t.treeMenu && t.treeMenu.length > 0) {
                                _.forEach(t.treeMenu, function (item, i) {
                                    if (item.menuAttr === 1) {
                                        $titleDiv.append($("<hr/>").css({ margin: "14px 0px" }));
                                        height += 30;
                                        return;
                                    }
                                    var nameToShow = item.displayName || item.defaultName;
                                    var $item = $("<li class='title-item'/>")
                                        .data("path", !uk.util.isNullOrUndefined(item.queryString) ? (item.url + "?" + item.queryString) : item.url)
                                        .data(DATA_TITLEITEM_PGID, item.programId + item.screenId)
                                        .data(DATA_TITLEITEM_PGNAME, nameToShow)
                                        .text(nameToShow);
                                    $item.on(constants.CLICK, function () {
                                        var path = $(this).data("path");
                                        if (path && path.indexOf("http") !== 0) {
                                            uk.request.jumpToMenu(path);
                                            return;
                                        }
                                        window.location.href = path;
                                    });
                                    $titleDiv.append($item);
                                    height += (34 + (Math.ceil($item.text().length / 12) - 1) * 20);
                                });
                            }
                            maxHeight = Math.max(maxHeight, height);
                        });
                        maxHeight += 20;
                        $title.css({ height: maxHeight + "px", width: width + "px" });
                    }
                    titleMenu.createTitles = createTitles;
                })(titleMenu || (titleMenu = {}));
                $(function () {
                    var showsName = true;
                    $(window)
                        .onkey("down", uk.KeyCodes.Ctrl, function () {
                        if (!showsName || $(".category-name.opening").length === 0)
                            return;
                        $(".title-item").each(function () {
                            $(this).text($(this).data(DATA_TITLEITEM_PGID));
                        });
                        showsName = false;
                    })
                        .onkey("up", uk.KeyCodes.Ctrl, function () {
                        if (showsName)
                            return;
                        $(".title-item").each(function () {
                            $(this).text($(this).data(DATA_TITLEITEM_PGNAME));
                        });
                        showsName = true;
                    });
                });
                var constants;
                (function (constants) {
                    constants.APP_ID = "com";
                    constants.MENU = "UK-Menu";
                    constants.CLICK = "click";
                    constants.MenuDataPath = "/sys/portal/webmenu/finddetails";
                    constants.Company = "/sys/portal/webmenu/currentCompany";
                    constants.Companies = "sys/portal/webmenu/companies";
                    constants.ChangeCompany = "sys/portal/webmenu/changeCompany";
                    constants.UserName = "sys/portal/webmenu/username";
                    constants.Logout = "sys/portal/webmenu/logout";
                    constants.PG = "sys/portal/webmenu/program";
                })(constants || (constants = {}));
            })(menu = ui.menu || (ui.menu = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=menu.js.map