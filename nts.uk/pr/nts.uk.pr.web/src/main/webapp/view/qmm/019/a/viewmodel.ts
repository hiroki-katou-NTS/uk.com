// TreeGrid Node
module qmm019.a {

    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        singleSelectedCode: KnockoutObservable<string>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutsMax: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutMaster: KnockoutObservable<service.model.LayoutMasterDto>;
        categories: KnockoutObservableArray<service.model.Category>;
        notHasKintai: KnockoutObservable<boolean> = ko.observable(false);
        notHasKiji: KnockoutObservable<boolean> = ko.observable(false);
        startYm: KnockoutObservable<string> = ko.observable("");
        endYm: KnockoutObservable<string> = ko.observable("");
        totalNormalLine: KnockoutObservable<string> = ko.observable("0行");
        totalNormalLineNumber: KnockoutObservable<number> = ko.observable(0);
        totalGrayLine: KnockoutObservable<string> = ko.observable("（+非表示0行）");
        totalGrayLineNumber: KnockoutObservable<number> = ko.observable(0);
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.layouts = ko.observableArray([]);
            self.layoutsMax = ko.observableArray([]);
            self.layoutMaster = ko.observable(new service.model.LayoutMasterDto());
            self.categories = ko.observableArray([new service.model.Category([], 0, self)]);

            self.singleSelectedCode.subscribe(function(codeChanged) {
                var layoutFind = _.find(self.layouts(), function(layout) {
                    return layout.stmtCode === codeChanged.split(';')[0] && layout.startYm === parseInt(codeChanged.split(';')[1]);
                });
                if (layoutFind !== undefined){
                    self.layoutMaster(layoutFind);  
                    self.startYm(nts.uk.time.formatYearMonth(self.layoutMaster().startYm));
                    self.endYm(nts.uk.time.formatYearMonth(self.layoutMaster().endYm));
                    service.getCategoryFull(layoutFind.stmtCode, layoutFind.startYm, self)
                        .done(function(listResult : Array<service.model.Category>){
                            self.categories(listResult);
                            self.calculateLine();
                            self.checkKintaiKiji();
                            self.bindSortable();
                    });
                }
            });
        }
        calculateLine() {
            var self = this;
            self.totalNormalLineNumber(0);
            self.totalGrayLineNumber(0);
            for (let category of self.categories()) {
                for (let line of category.lines()){
                    if (line.isRemoved || category.isRemoved) continue;
                    if (!line.isDisplayOnPrint)
                        self.totalGrayLineNumber(self.totalGrayLineNumber() + 1);
                    else 
                        self.totalNormalLineNumber(self.totalNormalLineNumber() + 1);    
                }
            }
            self.totalNormalLine(self.totalNormalLineNumber() + "行");
            self.totalGrayLine("（+非表示" + self.totalGrayLineNumber() + "行）");    
        }        
        checkKintaiKiji(){
            var self = this;
            var findKintai = _.find(self.categories(), function(category){
               return category.categoryAtr === 2; 
            });
            self.notHasKintai(findKintai === undefined);
            
            var findKiji = _.find(self.categories(), function(category){
               return category.categoryAtr === 3; 
            });
            self.notHasKiji(findKiji === undefined);    
        }
        bindSortable() {
            var self = this;
            $(".row").sortable({
                items: "span:not(.ui-state-disabled)"
            });
            $(".all-line").sortable({
                items: ".row"
            });
        }
        
        destroySortable() {
            $(".row").sortable("destroy");
            $(".all-line").sortable("destroy");    
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
                    let firstLayout: service.model.LayoutMasterDto = _.first(self.layouts());
                    self.singleSelectedCode(firstLayout.stmtCode + ";" + firstLayout.startYm);
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
                    children.push(new NodeTest(child.stmtCode + ";" + child.startYm, child.stmtName, [], 
                            nts.uk.time.formatYearMonth(child.startYm) + " ~ " + nts.uk.time.formatYearMonth(child.endYm)));
                });
                self.itemList.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
            });
        }
        
        registerLayout() {
            var self = this;
            service.registerLayout(self.layoutMaster(), self.categories()).done(function (res) {
                service.getCategoryFull(self.layoutMaster().stmtCode, self.layoutMaster().startYm, self)
                    .done(function(listResult : Array<service.model.Category>){
                        self.categories(listResult);
                        self.checkKintaiKiji();
                        self.bindSortable();
                });
            }).fail(function(err){
                alert(err);    
            });
        }
        
        addKintaiCategory() {
            var self = this;
            let category: service.model.Category = new service.model.Category([], 2, self);
            self.categories.push(category);
            self.notHasKintai(false);
            self.bindSortable();
        }
        
        addKijiCategory() {
            var self = this;
            let category: service.model.Category = new service.model.Category([], 3, self);
            self.categories.push(category);
            self.notHasKiji(false);
            self.bindSortable();
        }
        
        openADialog() {
            var self = this;
            if(self.singleSelectedCode() == null)
                return;
            var singleSelectedCode = self.singleSelectedCode().split(';');
            nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/d/index.xhtml', {title: '明細レイアウトの作成＞履歴追加'}).onClosed(function(): any {
                self.start();
            });
        }
        openEDialog(){
            var self = this;
            if(self.singleSelectedCode() == null)
                return;
            var singleSelectedCode = self.singleSelectedCode().split(';');
            nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.setShared('startYm', singleSelectedCode[1]);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/e/index.xhtml', {title: '明細レイアウトの作成＞履歴の編集'}).onClosed(function(): any  {
                self.start();
            });
        }
        openGDialog(){
            var self = this;
            nts.uk.ui.windows.sub.modal('/view/qmm/019/g/index.xhtml', {title: '明細レイアウトの作成＞新規登録'}).onClosed(function(): any  {
                self.start();
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

};
