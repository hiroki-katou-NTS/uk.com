module qmm019.a {
    export module service {
        var paths: any = {
            getAllLayout: "pr/proto/layout/findalllayout",
            getLayoutsWithMaxStartYm: "pr/proto/layout/findlayoutwithmaxstartym",
            getCategoryFull: "pr/proto/layout/findCategoies/full",
            registerLayout: "pr/proto/layout/register"
        }


        /**
         * Get list payment date processing.
         */
        export function getAllLayout(): JQueryPromise<Array<model.LayoutMasterDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getAllLayout)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        /**
         * Get list payment date processing.
         */
        export function getLayoutsWithMaxStartYm(): JQueryPromise<Array<model.LayoutMasterDto>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getLayoutsWithMaxStartYm)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
         * Get list getCategoryFull.
         */
        export function getCategoryFull(layoutCode, startYm, screenModel: ScreenModel): JQueryPromise<Array<model.Category>> {
            var dfd = $.Deferred<Array<model.Category>>();
            nts.uk.request.ajax(paths.getCategoryFull + "/" + layoutCode + "/" + startYm)
                .done(function(res: Array<model.Category>) {
                    var result = _.map(res, function(category: any) {
                        return new model.Category(category.lines, category.categoryAtr, screenModel);
                    });
                    dfd.resolve(result);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        /**
         * Register Layout
         */
        export function registerLayout(layout: model.LayoutMasterDto, categories: Array<model.Category>): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var categoryCommand = [], lineCommand = [], detailCommand = [];
            var listCategoryAtrDeleted = [], listAutoLineIdDeleted = [], listItemCodeDeleted = [];
            var categoryPosition = 1;
            for (let category of categories) {
                if (category.isRemoved === true) {
                    // Truong hop remove category thi remove luon line va detail
                    listCategoryAtrDeleted.push(category.categoryAtr);
                } else {
                    categoryCommand.push({categoryAtr: category.categoryAtr, categoryPosition: categoryPosition});
                    let linePosition = 1;
                    let sortedLines = $("#" + category.categoryAtr).sortable("toArray");
                    for (let itemLine of sortedLines) {
                        let line : model.Line = _.find(category.lines(), function(lineDetail){
                            return lineDetail.rowId === itemLine.toString();
                        });
                        if (line.isRemoved !== true) {
                            lineCommand.push({categoryAtr: category.categoryAtr, 
                                                autoLineId: line.autoLineId, 
                                                linePosition: linePosition, 
                                                lineDisplayAtr: line.lineDispayAtr});
                        } else {
                            if (_.includes(line.autoLineId, "lineIdTemp-") === false) {
                                listAutoLineIdDeleted.push({categoryAtr: category.categoryAtr, autoLineId: line.autoLineId});
                                continue;
                            }
                        }    
                        linePosition++;
                        let itemPosColumn = 1;
                        let sortedItemCodes = $("#" + line.rowId).sortable("toArray");
                        // Vì item mà required thì ko được sortable nên cần kiểm tra để thêm item này vào còn save.
                        if (line.hasRequiredItem) {
                            let detailRequired = _.last(line.details);
                            sortedItemCodes.push(detailRequired.itemCode());
                        }
                        for (let item of sortedItemCodes) {
                            let detail : model.ItemDetail = _.find(line.details, function(itemDetail){
                                return itemDetail.itemCode() === item.toString();
                            });
                            if (detail.isRemoved !== true) {
                                detailCommand.push({
                                    categoryAtr: category.categoryAtr, 
                                    itemCode: detail.itemCode(),
                                    updateItemCode: detail.updateItemCode(),
                                    added: detail.added(),
                                    autoLineId: detail.autoLineId(),
                                    itemPosColumn: itemPosColumn,
                                    calculationMethod: detail.calculationMethod(),
                                    displayAtr: line.lineDispayAtr,
                                    sumScopeAtr: detail.sumScopeAtr(),
                                    setOffItemCode: detail.setOffItemCode(),
                                    commuteAtr: detail.commuteAtr(),
                                    personalWageCode: detail.personalWageCode(),
                                    distributeWay: detail.distributeWay(),
                                    distributeSet: detail.distributeSet(),
                                    isErrorUseHigh: detail.isUseHighError(),
                                    errorRangeHigh: detail.errRangeHigh(),
                                    isErrorUserLow: detail.isUseLowError(),
                                    errorRangeLow: detail.errRangeLow(),
                                    isAlamUseHigh: detail.isUseHighAlam(),
                                    alamRangeHigh: detail.alamRangeHigh(),
                                    isAlamUseLow: detail.isUseLowAlam(),
                                    alamRangeLow: detail.alamRangeLow()});
                            } else {
                                listItemCodeDeleted.push({categoryAtr: category.categoryAtr, itemCode: detail.itemCode()});
                            }
                            itemPosColumn++;
                        }
                    }
                }
                categoryPosition++;
            }
            var command = {
                layoutCommand: {
                    stmtCode: layout.stmtCode,
                    startYm: layout.startYm,
                    stmtName: layout.stmtName,
                    endYm: layout.endYm,
                    historyId: layout.historyId
                },
                categoryCommand: categoryCommand,
                lineCommand: lineCommand,
                detailCommand: detailCommand,
                listCategoryAtrDeleted: listCategoryAtrDeleted,
                listAutoLineIdDeleted: listAutoLineIdDeleted,
                listItemCodeDeleted: listItemCodeDeleted
            }
            nts.uk.request.ajax(paths.registerLayout, command)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        /**
           * Model namespace.
        */
        export module model {
            
            // layout
            export class LayoutMasterDto {
                companyCode: string;
                stmtCode: string;
                startYm: number;
                stmtName: string;
                endYm: number;
                layoutAtr: number;
                historyId: string;
                constructor() {
                }
            }
                    
            export class Category {
                lines: KnockoutObservableArray<Line>;
                categoryAtr: number;
                categoryName: string;
                hasSetting: boolean = false;
                isRemoved: boolean = false;
                screenModel: KnockoutObservable<ScreenModel>;
                
                constructor(lines: Array<Line>, categoryAtr: number, screenModel: ScreenModel) {
                    this.screenModel = ko.observable(screenModel);
                    this.lines = ko.observableArray([]);
                    this.lines(_.map(lines, function(line: model.Line) {
                        var details = 
                            _.map(line.details, function(detail: model.ItemDetail){
                                return new model.ItemDetail(detail, screenModel);  
                            });
                        return new model.Line(line.categoryAtr, details, line.autoLineId, line.lineDispayAtr, line.linePosition, screenModel);
                    }));
                    this.categoryAtr = categoryAtr;
                    switch (categoryAtr){
                        case 0:
                            this.categoryName = "支給";
                            break;
                        case 1:
                            this.categoryName = "控除";
                            break;
                        case 2:
                            this.categoryName = "勤怠";
                            this.hasSetting = true;
                            break;
                        case 3:
                            this.categoryName = "記事";
                            this.hasSetting = true;
                            break;
                        default:
                            this.categoryName = "その他";
                            this.hasSetting = true;
                            break;        
                    }
                }
                categoryClick(data, event) {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/k/index.xhtml',{title: '明細レイアウトの作成＞カテゴリの設定'}).onClosed(() => {
                        var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                        if (selectedCode === "1") {
                            // cho phep print all row
                            for (let line of self.lines()){
                                line.setPrint(true);
                            }
                        } else if (selectedCode === "2") {
                            // Gray - Khong cho print all row
                            for (let line of self.lines()){
                                line.setPrint(false);
                            }                            
                        } else if (selectedCode === "3") {
                            // Xoa category
                            $("#group-" + data.categoryAtr).addClass("removed");
                            self.isRemoved = true;
                            if (data.categoryAtr === 2)
                                self.screenModel().notHasKintai(true);
                            if (data.categoryAtr === 3)
                                self.screenModel().notHasKiji(true);
                        }
                        self.screenModel().calculateLine();
                        return this;
                    });
                }
                addLine(){
                    var self = this;
                    if (self.screenModel().totalNormalLineNumber() + self.screenModel().totalGrayLineNumber() === 10) {return this;}
                    
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/i/index.xhtml',{title: '明細レイアウトの作成＞＋行追加'}).onClosed(() => {
                        var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                        if (selectedCode === undefined) return this;
                        
                        let autoLineId : string = "lineIdTemp-" + self.lines().length;
                        let listItemDetail: Array<ItemDetail> = new Array;
                        for(let i: number = 1; i <= 9; i++) {
                            listItemDetail.push(new ItemDetail(
                                {itemCode: "itemTemp-" + i, itemAbName: "+", isRequired: false, itemPosColumn: i,
                                categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, 
                                setOffItemCode: "", commuteAtr: 0, calculationMethod: 0,
                                distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0}, self.screenModel()            
                                ));
                        }
                        let line: Line = new Line(self.categoryAtr, listItemDetail, autoLineId, 1, self.lines.length, self.screenModel());
                        if (selectedCode === "1") {
                            // cho phep print
                            line.setPrint(true);
                        } else if (selectedCode === "2") {
                            // Gray - Khong cho print
                            line.setPrint(false);                           
                        }
                        self.lines.push(line);
                        self.screenModel().calculateLine();
                        
                        self.screenModel().bindSortable();
                        self.screenModel().destroySortable();
                        self.screenModel().bindSortable();
    
                        return this;
                    });
                }
            }
        
            export class Line {
                categoryAtr: number;
                autoLineId: string;
                rowId: string;
                details: Array<ItemDetail>;
                lineDispayAtr: number;
                linePosition: number;
                isDisplayOnPrint: boolean;
                hasRequiredItem: boolean = false;
                isRemoved: boolean = false;
                screenModel: KnockoutObservable<ScreenModel>;
                
                constructor(categoryAtr: number, itemDetails: Array<ItemDetail>, 
                    autoLineId: string, lineDispayAtr: number, linePosition: number, screenModel: ScreenModel) {
                    this.screenModel = ko.observable(screenModel);
                    this.details = itemDetails;
                    this.autoLineId = autoLineId;
                    this.rowId = categoryAtr + autoLineId;
                    this.lineDispayAtr = lineDispayAtr;
                    if(lineDispayAtr === 0) {
                        this.isDisplayOnPrint = false;
                    } 
                    else {
                        this.isDisplayOnPrint = true;
                    }
                    var checkRequired = _.find(itemDetails, function(findItem) {
                        return findItem.isRequired() === true;    
                    });
                    if(checkRequired !== undefined) {
                        this.hasRequiredItem = true;    
                    }
                    
                    this.linePosition = linePosition;
                    this.categoryAtr = categoryAtr;
                }
                lineClick(data: Line, event) {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/j/index.xhtml', {title: '明細レイアウトの作成＞行の設定'}).onClosed(() => {
                        var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                        if (selectedCode === "1") {
                            // cho phep print
                            self.setPrint(true);
                        } else if (selectedCode === "2") {
                            // Gray - Khong cho print
                            self.setPrint(false);                            
                        } else if (selectedCode === "3") {
                            // Xoa line
                            if (data.hasRequiredItem === false) {
                                $("#" + data.rowId).addClass("removed");
                                self.isRemoved = true;
                            }
                        }
                        self.screenModel().calculateLine();
                        return this;
                    });
                }
                setPrint(allowPrint: boolean) {
                    var self = this;
                    if (allowPrint === true) {
                        // cho phep print
                        $("#" + self.rowId).removeClass("ground-gray");
                        self.isDisplayOnPrint = true;
                        self.lineDispayAtr = 1;
                    } else {
                        // Gray - Khong cho print
                        $("#" + self.rowId).addClass("ground-gray");
                        self.isDisplayOnPrint = false;
                        self.lineDispayAtr = 0;                            
                    }    
                }
            }
        
            export class ItemDetail {
                itemCode: KnockoutObservable<string>;
                updateItemCode:  KnockoutObservable<string> = ko.observable("");
                added: KnockoutObservable<boolean> = ko.observable(false);
                itemAbName: KnockoutObservable<string>;
                isRequired: KnockoutObservable<boolean> = ko.observable(false);
                itemPosColumn: KnockoutObservable<number>;
                categoryAtr: KnockoutObservable<number>;
                autoLineId: KnockoutObservable<string>;
                sumScopeAtr: KnockoutObservable<number>;
                setOffItemCode: KnockoutObservable<string>;
                commuteAtr: KnockoutObservable<number>;
                calculationMethod: KnockoutObservable<number>;
                distributeSet: KnockoutObservable<number>;
                distributeWay: KnockoutObservable<number>;
                personalWageCode: KnockoutObservable<string>;
                isUseHighError: KnockoutObservable<number>;
                errRangeHigh: KnockoutObservable<number>;
                isUseLowError: KnockoutObservable<number>;
                errRangeLow: KnockoutObservable<number>;
                isUseHighAlam: KnockoutObservable<number>;
                alamRangeHigh: KnockoutObservable<number>;
                isUseLowAlam: KnockoutObservable<number>;
                alamRangeLow: KnockoutObservable<number>;
                isRemoved: boolean = false;
                screenModel: KnockoutObservable<ScreenModel>;
                
                constructor(itemObject: any, screenModel: ScreenModel) {
                    this.screenModel = ko.observable(screenModel);
                    this.itemCode = ko.observable(itemObject.itemCode);
                    this.itemAbName = ko.observable(itemObject.itemAbName);
                    if (itemObject.categoryAtr === 0 && 
                            (itemObject.itemCode === "F001" || itemObject.itemCode === "F002" || itemObject.itemCode === "F003")){
                            this.isRequired = ko.observable(true);
                    }
                    if (itemObject.categoryAtr === 1 && 
                            (itemObject.itemCode === "F114")){
                            this.isRequired = ko.observable(true);
                    }
                    this.itemPosColumn = ko.observable(itemObject.itemPosColumn);
                    this.categoryAtr = ko.observable(itemObject.categoryAtr);
                    this.autoLineId = ko.observable(itemObject.autoLineId);
                    this.sumScopeAtr = ko.observable(itemObject.sumScopeAtr);
                    this.setOffItemCode = ko.observable(itemObject.setOffItemCode);
                    this.commuteAtr = ko.observable(itemObject.commuteAtr);
                    this.calculationMethod = ko.observable(itemObject.calculationMethod);
                    this.distributeSet = ko.observable(itemObject.distributeSet);
                    this.distributeWay = ko.observable(itemObject.distributeWay);
                    this.personalWageCode = ko.observable(itemObject.personalWageCode);
                    this.isUseHighError = ko.observable(itemObject.isUseHighError);
                    this.errRangeHigh = ko.observable(itemObject.errRangeHigh);
                    this.isUseLowError = ko.observable(itemObject.isUseLowError);
                    this.errRangeLow = ko.observable(itemObject.errRangeLow);
                    this.isUseHighAlam = ko.observable(itemObject.isUseHighAlam);
                    this.alamRangeHigh = ko.observable(itemObject.alamRangeHigh);
                    this.isUseLowAlam = ko.observable(itemObject.isUseLowAlam);
                    this.alamRangeLow = ko.observable(itemObject.alamRangeLow);
                }
                itemClick(data, event) {
                    var self = this;
                    var param = {
                        categoryId: data.categoryAtr(),
                        itemCode: data.itemCode(),
                        isUpdate: data.itemAbName() === "+" ? false : true,
                        startYm: self.screenModel().layoutMaster().startYm,
                        stmtCode: self.screenModel().layoutMaster().stmtCode
                    };    
                    nts.uk.ui.windows.setShared('param', param);
                    nts.uk.ui.windows.sub.modal('/view/qmm/019/f/index.xhtml',{title: '項目の選択・設定'}).onClosed(() => {
                        var itemResult: qmm019.f.service.model.ItemDetailModel = nts.uk.ui.windows.getShared('itemResult');
                        
                        if (itemResult === undefined) return this;
                        
                        if (data.itemAbName() === "+") {
                            // Them moi
                            self.itemCode(itemResult.itemCode);
                            self.added(true);
                        } else {
                            if (self.added()) {
                                // Sửa một detail đang được Thêm mới
                                self.itemCode(itemResult.itemCode);    
                            } else if (itemResult.itemCode !== self.itemCode()){
                                // Update
                                self.updateItemCode(itemResult.itemCode);    
                            }
                        }
                        
                        self.itemAbName(itemResult.itemAbName);
                        self.sumScopeAtr(itemResult.sumScopeAtr);
                        //self.setOffItemCode(itemResult.setOffItemCode);
                        //self.commuteAtr(itemResult.commuteAtr);
                        self.calculationMethod(itemResult.calculationMethod);
                        self.distributeSet(itemResult.distributeSet);
                        self.distributeWay(itemResult.distributeWay);
                        self.personalWageCode(itemResult.personalWageCode);
                        self.isUseHighError(itemResult.isUseHighError ? 1 : 0);
                        self.errRangeHigh(itemResult.errRangeHigh);
                        self.isUseLowError(itemResult.isUseLowError ? 1 : 0);
                        self.errRangeLow(itemResult.errRangeLow);
                        self.isUseHighAlam(itemResult.isUseHighAlam ? 1 : 0);
                        self.alamRangeHigh(itemResult.alamRangeHigh);
                        self.isUseLowAlam(itemResult.isUseLowAlam ? 1 : 0);
                        self.alamRangeLow(itemResult.alamRangeLow);
                        
                        return this;
                    });
                }
            }
        }
    }

}



