module qmm019.a {
    export module service {
        var paths: any = {
            getAllLayout: "pr/proto/layout/findalllayout",
            getLayoutsWithMaxStartYm: "pr/proto/layout/findlayoutwithmaxstartym",
            getCategoryFull: "pr/proto/layout/findCategoies/full"
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
         * Get list payment date processing.
         */
        export function getCategoryFull(layoutCode, startYm): JQueryPromise<Array<model.Category>> {
            var dfd = $.Deferred<Array<model.Category>>();
            nts.uk.request.ajax(paths.getCategoryFull + "/" + layoutCode + "/" + startYm)
                .done(function(res: Array<model.Category>) {
                    var result = _.map(res, function(category: model.Category) {
                        return new model.Category(category.lines, category.categoryAtr);
                    });
                    dfd.resolve(result);
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
                constructor() {
                }
            }
                    
            export class Category {
                lines: Array<Line>;
                categoryAtr: number;
                categoryName: string;
                hasSetting: boolean = false;
                
                constructor(lines: Array<Line>, categoryAtr: number) {
                    this.lines = _.map(lines, function(line: model.Line) {
                        var details = 
                            _.map(line.details, function(detail: model.ItemDetail){
                                return new model.ItemDetail(detail);  
                            });
                        return new model.Line(line.categoryAtr, details, line.autoLineId, line.lineDispayAtr, line.linePosition);
                    });
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
                    //TODO: di den man hinh ...
                    alert(data.categoryName);    
                }
                addLine(){
                    var self = this;
                    let autoLineId : string = "lineId;" + self.lines.length;
                    var itemDetailObj1 = {itemCode: "1", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj2 = {itemCode: "2", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj3 = {itemCode: "3", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj4 = {itemCode: "4", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj5 = {itemCode: "5", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj6 = {itemCode: "6", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj7 = {itemCode: "7", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj8 = {itemCode: "8", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    var itemDetailObj9 = {itemCode: "9", itemAbName: "+", isRequired: false, itemPosColumn: 1,
                                        categoryAtr: self.categoryAtr, autoLineId: autoLineId, sumScopeAtr: 0, calculationMethod: 0,
                                        distributeSet: 0, distributeWay: 0, personalWageCode: "", isUseHighError: 0,
                                        errRangeHigh: 0, isUseLowError: 0, errRangeLow: 0, isUseHighAlam: 0,
                                        alamRangeHigh: 0, isUseLowAlam: 0, alamRangeLow: 0};
                    self.lines.push(
                            new Line(self.categoryAtr, ([
                                new ItemDetail(itemDetailObj1),
                                new ItemDetail(itemDetailObj2),
                                new ItemDetail(itemDetailObj3),
                                new ItemDetail(itemDetailObj4),
                                new ItemDetail(itemDetailObj5),
                                new ItemDetail(itemDetailObj6),
                                new ItemDetail(itemDetailObj7),
                                new ItemDetail(itemDetailObj8),
                                new ItemDetail(itemDetailObj9)
                    ]), autoLineId, 1, self.lines.length));
        
                    ScreenModel.prototype.bindSortable();
                    ScreenModel.prototype.destroySortable();
                    ScreenModel.prototype.bindSortable();
                }
            }
        
            export class Line {
                categoryAtr: number;
                autoLineId: string;
                details: Array<ItemDetail>;
                lineDispayAtr: number;
                linePosition: number;
                isDisplayOnPrint: boolean;
                hasRequiredItem: boolean = false;
                
                constructor(categoryAtr: number, itemDetails: Array<ItemDetail>, 
                    autoLineId: string, lineDispayAtr: number, linePosition: number) {
                    this.details = itemDetails;
                    this.autoLineId = autoLineId;
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
                lineClick(data, event) {
                    //TODO: goi man hinh khac
                    if (data.hasRequiredItem === false) {
                        $("#" + data.autoLineId + data.categoryAtr).addClass("ground-gray");
                    }    
                }
            }
        
            export class ItemDetail {
                itemCode: KnockoutObservable<string>;
                itemAbName: KnockoutObservable<string>;
                isRequired: KnockoutObservable<boolean> = ko.observable(false);
                itemPosColumn: KnockoutObservable<number>;
                categoryAtr: KnockoutObservable<number>;
                autoLineId: KnockoutObservable<string>;
                sumScopeAtr: KnockoutObservable<number>;
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
                
//                itemCode: string, itemAbName: string, isRequired: boolean, itemPosColumn: number,
//                            categoryAtr: number, autoLineId: string, sumScopeAtr: number, calculationMethod: number,
//                            distributeSet: number, distributeWay: number, personalWageCode: string, isUseHighError: number,
//                            errRangeHigh: number, isUseLowError: number, errRangeLow: number, isUseHighAlam: number,
//                            alamRangeHigh: number, isUseLowAlam: number, alamRangeLow: number
                constructor(itemObject: any) {
                    this.itemCode = ko.observable(itemObject.itemCode);
                    this.itemAbName = ko.observable(itemObject.itemAbName);
                    if(itemObject.itemCode === "F003" || itemObject.itemCode === "F114"){
                        this.isRequired = ko.observable(true);
                    } 
                    this.itemPosColumn = ko.observable(itemObject.itemPosColumn);
                    this.categoryAtr = ko.observable(itemObject.categoryAtr);
                    this.autoLineId = ko.observable(itemObject.autoLineId);
                    this.sumScopeAtr = ko.observable(itemObject.sumScopeAtr);
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
                //TODO: goi man hinh chi tiet
                itemClick(data, event) {
                    alert(data.itemAbName() + " ~~~ " + data.itemPosColumn());    
                }
            }
        }
    }

}