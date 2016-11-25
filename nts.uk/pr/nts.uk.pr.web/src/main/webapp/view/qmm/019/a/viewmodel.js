// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.itemList = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.layouts = ko.observableArray([]);
                self.layoutsMax = ko.observableArray([]);
                self.category1 = ko.observable(new Category(ko.observableArray([
                    new Line("categoryId-1", ko.observableArray([
                        new ItemDetail("1", "123", false, 1),
                        new ItemDetail("2", "12323", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "456", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "789", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "235", false, 8),
                        new ItemDetail("9", "itemName", false, 9)
                    ]), "lineId-1", true, true, 1),
                    new Line("categoryId-1", ko.observableArray([
                        new ItemDetail("1", "23", false, 1),
                        new ItemDetail("2", "7863", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "45453453", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "3", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "Name1", false, 8),
                        new ItemDetail("9", "2323322", false, 9)
                    ]), "lineId-2", true, true, 2)
                ]), "categoryId-1", "categoryName-1", false));
                self.category2 = ko.observable(new Category(ko.observableArray([
                    new Line("categoryId-2", ko.observableArray([
                        new ItemDetail("1", "itemAb12", false, 1),
                        new ItemDetail("2", "itemAme3", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "itemAe14", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "iteMme15", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "itemNe16", false, 8),
                        new ItemDetail("9", "item17", true, 9)
                    ]), "lineId-1", true, true, 1),
                    new Line("categoryId-2", ko.observableArray([
                        new ItemDetail("1", "現在使用行数", false, 1),
                        new ItemDetail("2", "現在使用行数", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "現在使用行数", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "ghghghghwwww", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "itemYZ71", false, 8),
                        new ItemDetail("9", "itemYX61", true, 9)
                    ]), "lineId-2", true, true, 2)
                ]), "categoryId-2", "categoryName-2", false));
                self.category3 = ko.observable(new Category(ko.observableArray([
                    new Line("categoryId-3", ko.observableArray([
                        new ItemDetail("1", "item1x", false, 1),
                        new ItemDetail("2", "item1z", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "item1c", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "item1s", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "item1d", false, 8),
                        new ItemDetail("9", "item1g", false, 9)
                    ]), "lineId-1", true, true, 1),
                    new Line("categoryId-3", ko.observableArray([
                        new ItemDetail("1", "itemv1", false, 1),
                        new ItemDetail("2", "item1h", false, 2),
                        new ItemDetail("3", "+", false, 3),
                        new ItemDetail("4", "item1t", false, 4),
                        new ItemDetail("5", "+", false, 5),
                        new ItemDetail("6", "item1u", false, 6),
                        new ItemDetail("7", "+", false, 7),
                        new ItemDetail("8", "item1e", false, 8),
                        new ItemDetail("9", "item1j", false, 9)
                    ]), "lineId-2", true, true, 2)
                ]), "categoryId-3", "categoryName-3", true));
                self.categories = ko.observableArray([self.category1, self.category2, self.category3]);
                self.selectedTask = ko.observable();
            }
            ScreenModel.prototype.clearTask = function (data, event) {
                var self = this;
                if (data === self.selectedTask()) {
                    self.selectedTask(null);
                }
                if (data.name() === "") {
                    self.tasks.remove(data);
                }
            };
            ;
            ScreenModel.prototype.addTask = function () {
                var self = this;
                var task = new Task("new");
                self.selectedTask(task);
                self.tasks.push(task);
            };
            ;
            ScreenModel.prototype.isTaskSelected = function (task) {
                var self = this;
                return task === self.selectedTask();
            };
            ;
            ScreenModel.prototype.bindSortable = function () {
                $(".row").sortable({
                    items: "span:not(.ui-state-disabled)"
                });
                $(".all-line").sortable({
                    items: ".row"
                });
            };
            ScreenModel.prototype.destroySortable = function () {
                $(".row").sortable("destroy");
                $(".all-line").sortable("destroy");
            };
            // start function
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                a.service.getAllLayout().done(function (layouts) {
                    self.layouts(layouts);
                    a.service.getLayoutsWithMaxStartYm().done(function (layoutsMax) {
                        self.layoutsMax(layoutsMax);
                        self.buildTreeDataSource();
                        dfd.resolve();
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            };
            ScreenModel.prototype.buildTreeDataSource = function () {
                var self = this;
                self.itemList.removeAll();
                _.forEach(self.layoutsMax(), function (layoutMax) {
                    var children = [];
                    var childLayouts = _.filter(self.layouts(), function (layout) {
                        return layout.stmtCode === layoutMax.stmtCode;
                    });
                    _.forEach(childLayouts, function (child) {
                        children.push(new NodeTest(child.stmtCode + 1, child.stmtName, [], child.startYm + " ~ " + child.endYM));
                    });
                    self.itemList.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
                });
            };
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var NodeTest = (function () {
            function NodeTest(code, name, children, nodeText) {
                this.code = code;
                this.name = name;
                this.childs = children;
                this.nodeText = nodeText;
            }
            return NodeTest;
        }());
        a.NodeTest = NodeTest;
        var Category = (function () {
            function Category(lines, categoryId, categoryName, hasSetting) {
                this.lines = lines;
                this.categoryId = categoryId;
                this.categoryName = categoryName;
                this.hasSetting = hasSetting;
            }
            Category.prototype.categoryClick = function (data, event) {
                alert(data.categoryName);
            };
            Category.prototype.addLine = function () {
                var self = this;
                self.lines.push(new Line(self.categoryId, ko.observableArray([
                    new ItemDetail("1", "itemv1", false, 1),
                    new ItemDetail("2", "item1h", false, 2),
                    new ItemDetail("3", "+", false, 3),
                    new ItemDetail("4", "item1t", false, 4),
                    new ItemDetail("5", "+", false, 5),
                    new ItemDetail("6", "item1u", false, 6),
                    new ItemDetail("7", "+", false, 7),
                    new ItemDetail("8", "item1e", false, 8),
                    new ItemDetail("9", "item1j", false, 9)
                ]), "lineId-3", true, true, 2));
                ScreenModel.prototype.bindSortable();
                ScreenModel.prototype.destroySortable();
                ScreenModel.prototype.bindSortable();
            };
            return Category;
        }());
        a.Category = Category;
        var Line = (function () {
            function Line(categoryId, itemDetails, autoLineId, isDisplayOnPrint, hasRequiredItem, linePosition) {
                this.itemDetails = itemDetails;
                this.autoLineId = autoLineId;
                this.isDisplayOnPrint = isDisplayOnPrint;
                this.hasRequiredItem = hasRequiredItem;
                this.linePosition = linePosition;
                this.categoryId = categoryId;
            }
            Line.prototype.lineClick = function (data, event) {
                $("#" + data.autoLineId + data.categoryId).addClass("ground-gray");
            };
            return Line;
        }());
        a.Line = Line;
        var ItemDetail = (function () {
            function ItemDetail(itemCode, itemAbName, isRequired, itemPosColumn) {
                this.itemCode = ko.observable(itemCode);
                this.itemAbName = ko.observable(itemAbName);
                this.isRequired = ko.observable(isRequired);
                this.itemPosColumn = ko.observable(itemPosColumn);
            }
            ItemDetail.prototype.itemClick = function (data, event) {
                alert(data.itemAbName() + " ~~~ " + data.itemPosColumn());
            };
            return ItemDetail;
        }());
        a.ItemDetail = ItemDetail;
        var Task = (function () {
            function Task(name) {
                this.name = ko.observable(name);
            }
            return Task;
        }());
        a.Task = Task;
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
;
var VisibleAndSelectBindingHandler = (function () {
    /**
     * Constructor.
     */
    function VisibleAndSelectBindingHandler() {
    }
    VisibleAndSelectBindingHandler.prototype.update = function (element, valueAccessor) {
        //update: function(element, valueAccessor) {
        ko.bindingHandlers.visible.update(element, valueAccessor);
        if (valueAccessor()) {
            setTimeout(function () {
                $(element).find("input").focus().select();
            }, 0); //new tasks are not in DOM yet
        }
    };
    return VisibleAndSelectBindingHandler;
}());
;
//control visibility, give element focus, and select the contents (in order)
ko.bindingHandlers['visibleAndSelect'] = new VisibleAndSelectBindingHandler();
