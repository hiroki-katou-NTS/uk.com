// TreeGrid Node
module qmm019.a {

    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        singleSelectedCode: any;
        selectedCode: KnockoutObservableArray<NodeTest>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutsMax: KnockoutObservableArray<service.model.LayoutMasterDto>;

        categories: KnockoutObservableArray<Category>;
        tasks: KnockoutObservableArray<any>;
        category1: KnockoutObservableArray<any>;
        category2: KnockoutObservableArray<any>;
        category3: KnockoutObservableArray<any>;
        selectedTask: KnockoutObservable<any>;
        clearTask(data, event): void {
            var self = this;
            if (data === self.selectedTask()) {
                self.selectedTask(null);
            }
            if (data.name() === "") {
                self.tasks.remove(data);
            }
        };
        addTask(): void {
            var self = this;
            var task = new Task("new");
            self.selectedTask(task);
            self.tasks.push(task);
        };
        isTaskSelected(task): any {
            var self = this;
            return task === self.selectedTask();
        };


        constructor() {
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
            self.category1 = ko.observable(
                new Category(
                    ko.observableArray([
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
                        ]), "categoryId-1", "categoryName-1", false)
            )
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

        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            service.getAllLayout().done(function(layouts: Array<service.model.LayoutMasterDto>) {
                self.layouts(layouts);
                service.getLayoutsWithMaxStartYm().done(function(layoutsMax: Array<service.model.LayoutMasterDto>) {
                    self.layoutsMax(layoutsMax);
                    self.buildTreeDataSource();
                    dfd.resolve();
                });
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            // Return.
            return dfd.promise();
        }

        buildTreeDataSource(): any {
            var self = this;
            self.itemList.removeAll();
            _.forEach(self.layoutsMax(), function(layoutMax) {
                var children = [];

                var childLayouts = _.filter(self.layouts(), function(layout) {
                    return layout.stmtCode === layoutMax.stmtCode;
                });
                _.forEach(childLayouts, function(child) {
                    children.push(new NodeTest(child.stmtCode + 1, child.stmtName, [], child.startYm + " ~ " + child.endYM));
                });
                self.itemList.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
            });
        }
    }
    export class NodeTest {
        code: string;
        name: string;
        childs: Array<NodeTest>;
        nodeText: any;
        constructor(code: string, name: string, children: Array<NodeTest>, nodeText: string) {
            this.code = code;
            this.name = name;
            this.childs = children;
            this.nodeText = nodeText;
        }
    }

    export class Category {
        lines: KnockoutObservableArray<Line>;
        categoryId: string;
        categoryName: string;
        hasSetting: boolean;
        constructor(lines: KnockoutObservableArray<Line>, categoryId: string, categoryName: string, hasSetting: boolean) {
            this.lines = lines;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.hasSetting = hasSetting;
        }
    }

    export class Line {
        details: KnockoutObservableArray<ItemDetail>;
        autoLineId: string;
        isDisplayOnPrint: boolean;
        hasRequiredItem: boolean;
        constructor(details: KnockoutObservableArray<ItemDetail>, autoLineId: string, isDisplayOnPrint: boolean, hasRequiredItem: boolean) {
            this.details = details;
            this.autoLineId = autoLineId;
            this.isDisplayOnPrint = isDisplayOnPrint;
            this.hasRequiredItem = hasRequiredItem;
        }
    }

    export class ItemDetail {
        itemCode: string;
        itemAbName: string;
        isRequired: boolean;
        constructor(itemCode: string, itemAbName: string, isRequired: boolean) {
            this.itemCode = itemCode;
            this.itemAbName = itemAbName;
            this.isRequired = isRequired;
        }
    }

    export class Task {
        name: any;
        constructor(name: string) {
            this.name = ko.observable(name);
        }
    }

};


class VisibleAndSelectBindingHandler implements KnockoutBindingHandler {
    /**
     * Constructor.
     */
    constructor() {
    }
    update(element: any, valueAccessor: () => any): void {
        //update: function(element, valueAccessor) {
        ko.bindingHandlers.visible.update(element, valueAccessor);
        if (valueAccessor()) {
            setTimeout(function() {
                $(element).find("input").focus().select();
            }, 0); //new tasks are not in DOM yet
        }
    }
};


//control visibility, give element focus, and select the contents (in order)
ko.bindingHandlers['visibleAndSelect'] = new VisibleAndSelectBindingHandler();