// TreeGrid Node
module qmm019.a {

    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        singleSelectedCode: any;
        selectedCode: KnockoutObservableArray<NodeTest>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutsMax: KnockoutObservableArray<service.model.LayoutMasterDto>;

        categories: KnockoutObservableArray<KnockoutObservable<Category>>;
        tasks: KnockoutObservableArray<any>;
        category1: KnockoutObservable<Category>;
        category2: KnockoutObservable<Category>;
        category3: KnockoutObservable<Category>;
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

            self.category1 = ko.observable(
                new Category(
                    ko.observableArray([
                        new Line(ko.observableArray([
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
                        new Line(ko.observableArray([
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
                    ]), "categoryId-1", "categoryName-1", false)
            );
            self.category2 = ko.observable(
                new Category(
                    ko.observableArray([
                        new Line(ko.observableArray([
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
                        new Line(ko.observableArray([
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
                    ]), "categoryId-2", "categoryName-2", false)
            );
            self.category3 = ko.observable(
                new Category(
                    ko.observableArray([
                        new Line(ko.observableArray([
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
                        new Line(ko.observableArray([
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
                    ]), "categoryId-3", "categoryName-3", true)
            );

            self.categories = ko.observableArray([self.category1, self.category2, self.category3]);

            self.selectedTask = ko.observable();
        }

        bindSortable() {
            $(".row").sortable({
                items: "span:not(.ui-state-disabled)"    
            });
            $(".line").sortable({
                items: ".row"    
            });
        }
        destroySortable() {
            $(".row").sortable("destroy");
            $(".line").sortable("destroy");    
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
        categoryClick(data, event) {
            alert(data.categoryName);    
        }
        addLine(){
            var self = this;
            self.lines.push(new Line(ko.observableArray([
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
        }
    }

    export class Line {
        itemDetails: KnockoutObservableArray<ItemDetail>;
        autoLineId: string;
        isDisplayOnPrint: boolean;
        hasRequiredItem: boolean;
        linePosition: number;
        constructor(itemDetails: KnockoutObservableArray<ItemDetail>, autoLineId: string,
            isDisplayOnPrint: boolean, hasRequiredItem: boolean, linePosition: number) {
            this.itemDetails = itemDetails;
            this.autoLineId = autoLineId;
            this.isDisplayOnPrint = isDisplayOnPrint;
            this.hasRequiredItem = hasRequiredItem;
            this.linePosition = linePosition;
        }
    }

    export class ItemDetail {
        itemCode: KnockoutObservable<string>;
        itemAbName: KnockoutObservable<string>;
        isRequired: KnockoutObservable<boolean>;
        itemPosColumn: KnockoutObservable<number>;
        constructor(itemCode: string, itemAbName: string, isRequired: boolean, itemPosColumn: number) {
            this.itemCode = ko.observable(itemCode);
            this.itemAbName = ko.observable(itemAbName);
            this.isRequired = ko.observable(isRequired);
            this.itemPosColumn = ko.observable(itemPosColumn);
        }
        itemClick(data, event) {
            alert(data.itemAbName() + " ~~~ " + data.itemPosColumn());    
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