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
                self.tasks = ko.observableArray([
                    new Task("Get dog food"),
                    new Task("Mow lawn"),
                    new Task("Fix car"),
                    new Task("Fix fence"),
                    new Task("Walk dog"),
                    new Task("Read book")
                ]);
                self.category1 = ko.observable(new Category(ko.observableArray([
                    new Line(ko.observableArray([
                        new ItemDetail("1", "itemAbName1", false),
                        new ItemDetail("2", "itemAbName1", false),
                        new ItemDetail("3", "+", false),
                        new ItemDetail("4", "itemAbName1", false),
                        new ItemDetail("5", "+", false),
                        new ItemDetail("6", "itemAbName1", false),
                        new ItemDetail("7", "+", false),
                        new ItemDetail("8", "itemAbName1", false),
                        new ItemDetail("9", "itemAbName1", false)
                    ]), "lineId-1", true, true),
                    new Line()
                ]), "categoryId-1", "categoryName-1", false));
                self.category2 = ko.observableArray([
                    new Task("Get dog food"),
                    new Task("Mow lawn"),
                    new Task("Fix car"),
                    new Task("Fix fence"),
                    new Task("Walk dog"),
                    new Task("Read book")
                ]);
                self.category3 = ko.observableArray([
                    new Task("Get dog food"),
                    new Task("Mow lawn"),
                    new Task("Fix car"),
                    new Task("Fix fence"),
                    new Task("Walk dog"),
                    new Task("Read book")
                ]);
                self.categories = ko.observableArray();
                self.selectedTask = ko.observable();
                $("#category1").sortable();
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
            return Category;
        }());
        a.Category = Category;
        var Line = (function () {
            function Line(details, autoLineId, isDisplayOnPrint, hasRequiredItem) {
                this.details = details;
                this.autoLineId = autoLineId;
                this.isDisplayOnPrint = isDisplayOnPrint;
                this.hasRequiredItem = hasRequiredItem;
            }
            return Line;
        }());
        a.Line = Line;
        var ItemDetail = (function () {
            function ItemDetail(itemCode, itemAbName, isRequired) {
                this.itemCode = itemCode;
                this.itemAbName = itemAbName;
                this.isRequired = isRequired;
            }
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
