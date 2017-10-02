module nts.uk.ui.menu {
    let SEPARATOR: string = "menu_item_separator";
    
    /**
     * Menu item.
     */
    class MenuItem {
        name: string;
        path: string;
        constructor(name: string, path?: string) {
            this.name = name;
            this.path = path;
        }
    }
    
    /**
     * Create menu selection.
     */
    function createMenuSelect($menuNav: JQuery) {
        let $cate = $("<li class='category'/>").addClass("menu-select").appendTo($menuNav);
        let $cateName = $("<div class='category-name'/>").html("&#9776;").appendTo($cate);
        let $menuItems = $("<ul class='menu-items'/>").appendTo($cate);
        let items = [ new MenuItem("メニュー選択"), new MenuItem(SEPARATOR), 
                        new MenuItem("就業"), new MenuItem("給与"), new MenuItem("人事"), new MenuItem("会計") ];
        _.forEach(items, function(item, i) {
            if (item.name === SEPARATOR) {
                $menuItems.append($("<hr/>").css({ margin: "5px 0px" }));
                return;
            }
            $menuItems.append($("<li class='menu-item' path='" + item.path + "'/>").text(item.name));
        });
        $menuItems.append("<br/>");
    }
    
    /**
     * Request.
     */
    export function request() {
        nts.uk.request.ajax(constants.MenuDataPath).done(function(data) {
            generate(data);
            displayUserInfo();
            getProgram();
        });
    }
    
    /**
     * Generate.
     */
    function generate(menuSet: any) {
        if (!menuSet) return;
        let $menuNav = $("<ul/>").attr("id", "menu-nav").appendTo($("#nav-area"));
        createMenuSelect($menuNav);
        _.forEach(menuSet.categories, function(category: any) {
            let $cate = $("<li class='category'/>").appendTo($menuNav);
            let $cateName = $("<div class='category-name'/>").css("background", category.backgroundColor)
                                .text(category.name).appendTo($cate);
            let $menuItems = $("<ul class='menu-items'/>").appendTo($cate);
            if (category.items && category.items.length > 0) {
                _.forEach(category.items, function(item: any) {
                    $menuItems.append($("<li class='menu-item' path='" + item.path + "'/>").text(item.name));
                });
            } else if (category.titles && category.titles.length > 0) {
                titleMenu.createTitles($menuItems, category.titles);
            }
        });
        init();
    }
    
    /**
     * Display user info.
     */
    export function displayUserInfo() {
        let $userInfo = $("#user-info");
        let $company = $userInfo.find("#company");
        let $user = $userInfo.find("#user");
        
        let notThen = function($container: JQuery, target: any, op: any) {
            if (!$container.is(target) && $container.has(target).length === 0) {
                op();
            }
        };
        
        nts.uk.request.ajax(constants.Companies).done(function(companies: any) {
            if (!companies || companies.length === 0) return;
            let $companyName = $("<span/>").attr("id", "company-name");
            $companyName.text(companies[0]).appendTo($company);
            let $companySelect = $("<div/>").addClass("company-select cf");
            $companySelect.appendTo($company);
            $("<div/>").addClass("ui-icon ui-icon-caret-1-s").appendTo($companySelect);
            let $companyList = $("<ul class='menu-items company-list'/>").appendTo($companySelect);
            _.forEach(companies, function(comp: any, i: number) {
                $("<li class='menu-item company-item'/>").text(comp).appendTo($companyList);
            });
            $companySelect.on(constants.CLICK, function() {
                if ($companyList.css("display") === "none") {
                    $companyList.fadeIn(100);
                    return;
                }
                $companyList.fadeOut(100);
            });
            
            nts.uk.request.ajax(constants.UserName).done(function(userName: any) {
                let $userImage = $("<div/>").addClass("ui-icon ui-icon-person").appendTo($user);
                $userImage.css("margin-right", "6px");
                let $userName = $("<span/>").attr("id", "user-name").text(userName.value).appendTo($user);
                let $userSettings = $("<div/>").addClass("user-settings cf").appendTo($user);
                $("<div class='ui-icon ui-icon-caret-1-s'/>").appendTo($userSettings);
                let userOptions = [ new MenuItem("個人情報の設定"), new MenuItem("ログアウト") ];
                let $userOptions = $("<ul class='menu-items user-options'/>").appendTo($userSettings);
                _.forEach(userOptions, function(option: any, i: number) {
                    $userOptions.append($("<li class='menu-item'/>").text(option.name));
                });
                $userSettings.on(constants.CLICK, function() {
                    if ($userOptions.css("display") === "none") {
                        $userOptions.fadeIn(100);
                        return;
                    }
                    $userOptions.fadeOut(100);
                });
                
                $(document).on(constants.CLICK, function(evt: any) {
                    notThen($companySelect, evt.target, function() {
                        $companyList.fadeOut(100);
                    });
                    notThen($userSettings, evt.target, function() {
                        $userOptions.fadeOut(100);
                    });
                });
            });
        });
    }
    
    /**
     * Get program.
     */
    function getProgram() {
        nts.uk.request.ajax(constants.PG).done(function(pg: any) {
            let $pgArea = $("#pg-area");
            $("<div/>").attr("id", "pg-name").text(pg.value).appendTo($pgArea);
            let $manualArea = $("<div/>").attr("id", "manual").appendTo($pgArea);
            let $manualBtn = $("<button class='manual-button'/>").text("?").appendTo($manualArea);
            $manualBtn.on(constants.CLICK, function() {
                // TODO:
            });
            
            let $tglBtn = $("<div class='tgl cf'/>").appendTo($manualArea);
            $tglBtn.append($("<div class='ui-icon ui-icon-caret-1-s'/>"));
            $tglBtn.on(constants.CLICK, function() {
                
            });
        });
    }
    
    /**
     * Init.
     */
    function init() {
        let showingItem;
        let $navArea = $("#nav-area");
        let $menuItems = $("#menu-nav li.category");
        function closeItem() {
            let $item = $("#menu-nav li.category:eq(" + showingItem + ")");
            $item.find(".category-name").removeClass("opening");
            $item.find("ul, div.title-menu").fadeOut(100);
        }
        
        function openItem($item: JQuery) {
            $item.find(".category-name").addClass("opening");
            $item.find("ul, div.title-menu").fadeIn(100);
        }
        
        $(document).on(constants.CLICK, function(evt) {
            if (!$navArea.is(evt.target) && $navArea.has(evt.target).length === 0
                && !util.isNullOrUndefined(showingItem)) {
                closeItem();
                showingItem = undefined;
            }
        });
        
        $menuItems.hover(function() {
            let $item = $(this);
            let ith = $item.index();
            if (util.isNullOrUndefined(showingItem) || showingItem === ith) return;
            closeItem();
            openItem($item);
            showingItem = ith;
        });
        
        $menuItems.on(constants.CLICK, function(event) {
            let $item = $(this);
            showingItem = $item.index();
            if ($item.find(".category-name").hasClass("opening") && showingItem === 0) {
                closeItem();
                return;
            }
            openItem($item);
        });
    
        $(".menu-item").on(constants.CLICK, function() {
            let path = $(this).data('path');
            nts.uk.request.jump(path);
        });   
    }
    
    module titleMenu {
        export let WIDTH: number = 180;
        export let FR: number = 20;
        
        /**
         * Create titles.
         */
        export function createTitles($category: JQuery, titles: any) {
            let $title = $("<div/>").addClass("title-menu").appendTo($category);
            let width = 0, height, maxHeight = 0;
            _.forEach(titles, function(t, i) {
                height = 60;
                let left = WIDTH * i + 3;
                if (i > 0) {
                    left += FR * i;
                }
                if (i === titles.length - 1) {
                    width = left + WIDTH + 7;
                }
                let $titleDiv = $("<div/>").addClass("title-div").css({ left: left }).appendTo($title);
                let $titleName = $("<div/>").addClass("title-name").text(t.name).css({ background: t.titleColor }).appendTo($titleDiv);
                let $titleImage = $("<img/>").addClass("title-image").hide();
                $titleDiv.append($titleImage);
                if (!_.isNull(t.imagePath) && !_.isUndefined(t.imagePath) && !_.isEmpty(t.imagePath)) {
                    $titleImage.attr("src", t.imagePath).show();
                    height += 80;
                }
                if (t.items && t.items.length > 0) {
                    _.forEach(t.items, function(item, i) {
                        if (item.name === SEPARATOR) {
                            $titleDiv.append($("<hr/>").css({ margin: "14px 0px" }));
                            height += 30;
                            return;
                        }
                        let $item = $("<li class='title-item' path='" + item.path + "'/>").text(item.name);
                        $titleDiv.append($item);
                        height += 40;
                    });
                }
                maxHeight = Math.max(maxHeight, height); 
            });
            maxHeight += 20;
            $title.css({ height: maxHeight + "px", width: width + "px" });
        }
    }
    
    
    module constants {
        export let CLICK = "click";
        export let MenuDataPath = "/shared/menu/get";
        export let Companies = "/shared/menu/companies";
        export let UserName = "/shared/menu/username";
        export let PG = "/shared/menu/program";
    }
    
}