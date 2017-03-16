
var screenQmm019: KnockoutObservable<qmm019.a.ScreenModel>;

module qmm019.a {
    
    export class ScreenModel {
        //Khai bao bien
        itemList: KnockoutObservableArray<NodeTest>;
        itemListFull: Array<NodeTest> = [];
        itemListSearch: Array<NodeTest> = [];
        queueSearchResult: Array<NodeTest> = [];
        textSearch: string = "";
        singleSelectedCode: KnockoutObservable<string>;
        layouts: KnockoutObservableArray<service.model.LayoutMasterDto>;
        layoutsMax: KnockoutObservableArray<service.model.LayoutHeadDto>;
        layoutMaster: KnockoutObservable<service.model.LayoutMasterDto>;
        categories: KnockoutObservableArray<service.model.Category>;
        notHasKintai: KnockoutObservable<boolean> = ko.observable(false);
        notHasKiji: KnockoutObservable<boolean> = ko.observable(false);
        startYm: KnockoutObservable<string> = ko.observable("");
        endYm: KnockoutObservable<string> = ko.observable("");
        totalNormalLine: KnockoutObservable<string> = ko.observable("0陦�");
        totalNormalLineNumber: KnockoutObservable<number> = ko.observable(0);
        totalGrayLine: KnockoutObservable<string> = ko.observable("�ｼ�+髱櫁｡ｨ遉ｺ0陦鯉ｼ�");
        totalGrayLineNumber: KnockoutObservable<number> = ko.observable(0);
        allowClick: KnockoutObservable<boolean> = ko.observable(true);
        firstLayoutCode: string = ""; //Dﾃｹng cho select item ﾄ黛ｺｧu tiﾃｪn.
        
        constructor() {
            var self = this;
            screenQmm019 = ko.observable(self);
            self.itemList = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.layouts = ko.observableArray([]);
            self.layoutsMax = ko.observableArray([]);
            self.layoutMaster = ko.observable(new service.model.LayoutMasterDto());
            self.categories = ko.observableArray([new service.model.Category([], 0)]);

            self.singleSelectedCode.subscribe(function(codeChanged) {
                var layoutFind = _.find(self.layouts(), function(layout) {
                    return layout.stmtCode === codeChanged.split(';')[0] && layout.startYm === parseInt(codeChanged.split(';')[1]);
                });
                if (layoutFind !== undefined){
                    self.layoutMaster(layoutFind);  
                    self.layoutMaster(ko.mapping.fromJS(layoutFind)); 
                    self.startYm(nts.uk.time.formatYearMonth(self.layoutMaster().startYm));
                    self.endYm(nts.uk.time.formatYearMonth(self.layoutMaster().endYm));
                    service.getCategoryFull(layoutFind.stmtCode, layoutFind.historyId, layoutFind.startYm)
                        .done(function(listResult : Array<service.model.Category>){
                            self.categories(listResult);
                            self.calculateLine();
                            self.checkKintaiKiji();
                            self.bindSortable();
                    });
                }
            });
            
        }
        
        searchLayout() {
            var self = this;
            var textSearch: string = $("#A_INP_001").val().trim();
            
            if (textSearch.length === 0){
                nts.uk.ui.dialog.alert("繧ｳ繝ｼ繝�/蜷咲ｧｰ縺悟�･蜉帙＆繧後※縺�縺ｾ縺帙ｓ縲�");
            } else {
                if (self.textSearch !== textSearch) {
                    self.itemListSearch = _.filter(self.itemListFull, function(item) {
                        return _.includes(item.code, textSearch) || _.includes(item.name, textSearch);
                    });
                    self.queueSearchResult = [];
                    for (let item of self.itemListSearch) {
                        for (let child of item.childs) {
                            self.queueSearchResult.push(child);    
                        }
                    }
                    self.textSearch = textSearch;
                }
                if (self.itemListSearch.length === 0){
                    nts.uk.ui.dialog.alert("蟇ｾ雎｡繝�繝ｼ繧ｿ縺後≠繧翫∪縺帙ｓ縲�");
                } else {
                    let firstResult: NodeTest = _.first(self.queueSearchResult);
                    self.singleSelectedCode(firstResult.code);
                    self.queueSearchResult.shift();
                    self.queueSearchResult.push(firstResult);
                }
            }
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
            self.totalNormalLine(self.totalNormalLineNumber() + "陦�");
            self.totalGrayLine("�ｼ�+髱櫁｡ｨ遉ｺ" + self.totalGrayLineNumber() + "陦鯉ｼ�");    
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
        start(currentLayoutSelectedCode: string): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();

            service.getAllLayoutHist().done(function(layouts: Array<service.model.LayoutMasterDto>) {
                if (layouts.length > 0) {
                
                    self.layouts(layouts);
                    service.getAllLayoutHead().done(function(layoutsMax: Array<service.model.LayoutHeadDto>) {
                        self.layoutsMax(layoutsMax);
                        self.buildTreeDataSource();
                        //let firstLayout: service.model.LayoutMasterDto = _.first(self.layouts());
                        if (currentLayoutSelectedCode === undefined) {
                            self.singleSelectedCode(self.firstLayoutCode);
                        } else {
                            self.singleSelectedCode(currentLayoutSelectedCode);
                        }
                        dfd.resolve();
                    });
                    
                } else {
                    self.allowClick(false);
                    dfd.resolve();    
                }
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            // Return.
            return dfd.promise();
        }

        buildTreeDataSource(): any {
            var self = this;
            var items = [];
            _.forEach(self.layoutsMax(), function(layoutMax) {
                var children = [];

                var childLayouts = _.filter(self.layouts(), function(layout) {
                    return layout.stmtCode === layoutMax.stmtCode;
                });
                _.forEach(childLayouts, function(child) {
                    if (self.firstLayoutCode === "") self.firstLayoutCode = child.stmtCode + ";" + child.startYm;
                    
                    children.push(new NodeTest(child.stmtCode + ";" + child.startYm, child.stmtName, [], 
                            nts.uk.time.formatYearMonth(child.startYm) + " ~ " + nts.uk.time.formatYearMonth(child.endYm)));
                });
                items.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
            });
            self.itemList(items);
            self.itemListFull = items;
        }
        
        registerLayout() {
            var self = this;
            service.registerLayout(self.layoutMaster(), self.categories()).done(function (res) {
                service.getCategoryFull(self.layoutMaster().stmtCode, self.layoutMaster().historyId, self.layoutMaster().startYm)
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
            let category: service.model.Category = new service.model.Category([], 2);
            self.categories.push(category);
            self.notHasKintai(false);
            self.bindSortable();
        }
        
        addKijiCategory() {
            var self = this;
            let category: service.model.Category = new service.model.Category([], 3);
            self.categories.push(category);
            self.notHasKiji(false);
            self.bindSortable();
        }
        
        openADialog() {
            var self = this;
            if(self.singleSelectedCode() == null)
                return false;
            var singleSelectedCode = self.singleSelectedCode().split(';');
            nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/d/index.xhtml', {title: '譏守ｴｰ繝ｬ繧､繧｢繧ｦ繝医�ｮ菴懈�撰ｼ槫ｱ･豁ｴ霑ｽ蜉�'}).onClosed(function(): any {
                self.start(self.singleSelectedCode());
            });
        }
        openEDialog(){
            var self = this;
            if(self.singleSelectedCode() === null)
                return false;
            var singleSelectedCode = self.singleSelectedCode().split(';');
            if(singleSelectedCode[0] === undefined
                || singleSelectedCode[1] === undefined
                ||self.layoutMaster().historyId === undefined)
                return false;
            nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.setShared('startYm', singleSelectedCode[1]);
            nts.uk.ui.windows.setShared('historyId', self.layoutMaster().historyId);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/e/index.xhtml', {title: '譏守ｴｰ繝ｬ繧､繧｢繧ｦ繝医�ｮ菴懈�撰ｼ槫ｱ･豁ｴ縺ｮ邱ｨ髮�' }).onClosed(function(): any  {
                self.start(self.singleSelectedCode());
            });
        }
        openGDialog(){
            var self = this;
             nts.uk.ui.windows.sub.modal('/view/qmm/019/g/index.xhtml', option);
            nts.uk.ui.windows.sub.modal('/view/qmm/019/g/index.xhtml', {title: '譏守ｴｰ繝ｬ繧､繧｢繧ｦ繝医�ｮ菴懈�撰ｼ樊眠隕冗匳骭ｲ'}).onClosed(function(): any  {
                self.start(undefined);
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
