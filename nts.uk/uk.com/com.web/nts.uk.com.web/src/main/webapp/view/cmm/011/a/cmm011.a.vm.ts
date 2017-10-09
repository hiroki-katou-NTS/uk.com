module nts.uk.com.view.cmm011.a {
    export module viewmodel {
        
        import TreeWorkplace = service.model.TreeWorkplace;
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = base.IHistory;
        import Workplace = base.IWorkplace;
        import CreationType = base.CreationWorkplaceType;
        
        export class ScreenModel {
            
            strDWorkplace: KnockoutObservable<string>; // A2_3
            endDWorkplace: KnockoutObservable<string>; // A2_5
            
            treeWorkplace: KnockoutObservable<TreeWorkplaceModel>;
            workplaceHistory: KnockoutObservable<WorkplaceHistoryModel>;
            
            workplaceCode: KnockoutObservable<string>; // A6_2
            externalCode: KnockoutObservable<string>; // A7_2
            workplaceName: KnockoutObservable<string>; // A8_2
            wkpDisplayName: KnockoutObservable<string>; // A9_2
            wkpFullName: KnockoutObservable<string>; // A10_2
            
            creationType: CreationType;
            
            isNewMode: KnockoutObservable<boolean>;
            isValidWorplace: KnockoutObservable<boolean>;
            isValidWorplaceHistory: KnockoutObservable<boolean>;
            
            isLoadedScreen: boolean;
            
            constructor() {
                let self = this;
                
                self.strDWorkplace = ko.observable(null);
                self.endDWorkplace = ko.observable("9999/12/31");
                self.treeWorkplace = ko.observable(new TreeWorkplaceModel(self));
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel(self));
                
                self.workplaceCode = ko.observable(null);
                self.externalCode = ko.observable(null);
                self.workplaceName = ko.observable(null);
                self.wkpDisplayName = ko.observable(null);
                self.wkpFullName = ko.observable(null);
                
                self.creationType = null;
                
                self.isNewMode = ko.observable(true);
                self.isValidWorplace = ko.computed(function() {
                    if (self.isNewMode()) {
                        return false;
                    }
                    return !nts.uk.text.isNullOrEmpty(self.strDWorkplace());
                });
                self.isValidWorplaceHistory = ko.computed(function() {
                    self.workplaceHistory().selectedHistoryId();
                    if (self.isNewMode()) {
                        return false;
                    }
                    return self.workplaceHistory().isSelectedLatestHistory();
                });
                
                self.isLoadedScreen = false;
                
                // subscribe
                self.strDWorkplace.subscribe((newValue) => {
                    if (!newValue) {
                        self.configureWkpDialog();
                    }
                    // load again tree workplace
                    if (self.isLoadedScreen) {
                        self.treeWorkplace().findLstWorkplace(new Date(newValue));
                    }
                });
                self.workplaceName.subscribe((newValue) => {
                    let obj: any = self.treeWorkplace().findPathNameByWkpIdSelected();
                    self.wkpDisplayName(obj.wkpDisplayName + " " + newValue);
                    self.wkpFullName(obj.wkpFullName + " " + newValue);
                });
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                nts.uk.ui.block.grayout();
                // find all history master
                self.findAllHistory().done(() => {
                    
                    // load tree workplace
                    self.treeWorkplace().findLstWorkplace(new Date(self.strDWorkplace())).done(() => {
                        nts.uk.ui.block.clear();
                        dfd.resolve();
                    });
                });
                
                return dfd.promise();
            }
            
            /**
             * findAllHistory
             */
            private findAllHistory(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                nts.uk.ui.block.grayout();
                service.findLstWkpConfigHistory().done(function(data: Array<IHistory>) {
                    // set start date, end date
                    if (data && data.length > 0) {
                        self.strDWorkplace(data[0].startDate);
                        self.endDWorkplace(data[0].endDate);
                    }
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
                return dfd.promise();
            }
            
            /**
             * save workplace
             */
            public saveWorkplace() {
                let self = this;
                service.registerWkp(self.collectWkpData()).done(function() {

                });
            }

            /**
             * Collect workplace data 
             */
            public collectWkpData() {
                let self = this;
            }
            
            /**
             * configureWkpDialog
             */
            public configureWkpDialog() {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/011/b/index.xhtml').onClosed(() => {
                    let dialogData = nts.uk.ui.windows.getShared("ShareDateScreenParent");
                    if (!dialogData) {
                        return;
                    }
                    self.strDWorkplace(dialogData.startDate);
                    self.endDWorkplace(dialogData.endDate);
                });
            }
            
            /**
             * createWkpDialog
             */
            public createWkpDialog() {
                let self = this;
                
                let objTransfer: any = {};
                objTransfer.code = self.workplaceCode();
                objTransfer.name = self.workplaceName();
                objTransfer.isLess999Heirarchies = self.treeWorkplace().isValidLimitHeirachy();
                objTransfer.isLessTenthHeirarchy = self.treeWorkplace().selectedHierarchyCd.length / 3 < 10;
                nts.uk.ui.windows.setShared("ObjectTransfer", objTransfer);
                
                nts.uk.ui.windows.sub.modal('/view/cmm/011/f/index.xhtml').onClosed(() => {
                    let creationType: CreationType = nts.uk.ui.windows.getShared("CreatedWorkplaceCondition");
                    if (creationType) {
                        self.isNewMode(false);
                        self.creationType = creationType;
                    }
                });
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        /**
         * TreeWorkplaceModel
         */
        class TreeWorkplaceModel {

            parentModel: ScreenModel;
            
            treeColumns: KnockoutObservableArray<any>;
            lstWorkplace: KnockoutObservableArray<TreeWorkplace>;
            selectedWpkId: KnockoutObservable<string>;
            
            treeArray: KnockoutObservableArray<any>;
            selectedHierarchyCd: string;
            mapHierarchy: any;

            constructor(parentModel: ScreenModel) {
                let self = this;
                
                self.parentModel = parentModel;
                
                self.treeColumns = ko.observableArray([
                    { headerText: "", key: 'workplaceId', dataType: "string", hidden: true },
                    { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: 250, dataType: "string" }
                ]);
                self.lstWorkplace = ko.observableArray([]);
                self.selectedWpkId = ko.observable(null);
                self.treeArray = ko.observableArray([]);
                
                // subscribe
                self.lstWorkplace.subscribe(dataList => {
                    // update nodeText, level of tree
                    self.updateTree(self.lstWorkplace());
                    
                    // convert tree to array
                    self.treeArray(self.convertTreeToArray(dataList));
                    
                    // existed workplace
                    if (dataList && dataList.length > 0) {
                        self.selectFirst();
                        self.parentModel.isNewMode(false);
                    }
                });
                
                //subscribe selected wkp Id
                self.selectedWpkId.subscribe(newValue => {
                    self.selectedHierarchyCd = self.getSelectedHeirarchyCd();
                    
                    // get wkp list hist by wkpId
                    self.parentModel.workplaceHistory().loadWkpHistoryByWkpId(newValue);
                });
                self.treeArray.subscribe(newArray => {
                    self.mapHierarchy = self.convertMapHeirarchy();
                });
            }

            /**
             * findLstWorkplace
             */
            public findLstWorkplace(startDate: Date): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();
                nts.uk.ui.block.grayout();
                service.findLstWorkPlace(startDate).done((res: Array<TreeWorkplace>) => {
                    self.lstWorkplace(res);
                    
                    // calculate width of cell nodeText
                    self.calWidthColText();
                    
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }

            /**
             * selectFirst
             */
            public selectFirst() {
                let self = this;
                if (self.lstWorkplace() && (self.lstWorkplace().length > 0)) {
                    self.selectedWpkId(self.lstWorkplace()[0].workplaceId);
                }
            }
            
            /**
             * calHeightTree
             */
            public calHeightTree(): number {
                let heightCell: number = 26;
                return heightCell * 20;
            }
            
            /**
             * findPathNameByWkpIdSelected
             */
            public findPathNameByWkpIdSelected(): any {
                let self = this;
                let obj: any = {};
                let index: number = 3;
                let wkpFullName: string = "";
                while(index <= self.selectedHierarchyCd.length) {
                    let parentHeirarchyCd: string = self.selectedHierarchyCd.substr(0, index);
                    wkpFullName += " " + self.mapHierarchy[parentHeirarchyCd];
                    index += 3;
                }
                obj.wkpFullName = wkpFullName.trim();
                obj.wkpDisplayName = self.mapHierarchy[self.selectedHierarchyCd];
                return obj;
            }
            
            /**
             * isValidLimitHeirachy
             */
            public isValidLimitHeirachy(): boolean {
                let self = this;
                let lengthElementSameHeirachies: number = self.treeArray().filter(item => {
                    if (item.hierarchyCode.length != self.selectedHierarchyCd.length) {
                        return false;
                    }
                    let parentHeirarchyCd: string = item.hierarchyCode.substr(0, item.hierarchyCode.length - 3);
                    let parentSelectedHeirarchyCd: string = self.selectedHierarchyCd.substr(0,
                        self.selectedHierarchyCd.length - 3);
                    return parentHeirarchyCd == parentSelectedHeirarchyCd;
                }).length;
                let maxHeirarchies: number = 999;
                return lengthElementSameHeirachies < maxHeirarchies;
            }
            
            /**
             * updateTree
             */
            private updateTree(dataList: Array<TreeWorkplace>) {
                let self = this;
                for (let item of dataList) {
                    item.nodeText = item.code + " " + item.name;
                    item.level = item.hierarchyCode.length / 3;
                    
                    if (item.childs && item.childs.length > 0) {
                        self.updateTree(item.childs);
                    }
                }
            }
            
            /**
             * getSelectedHeirarchyCd
             */
            private getSelectedHeirarchyCd(): string {
                let self = this;
                let hierarchyCode: string = "";
                for(let item of self.treeArray()) {
                    if (item.workplaceId == self.selectedWpkId()) {
                        hierarchyCode = item.hierarchyCode;
                        break;
                    }
                }
                return hierarchyCode;
            }
            
            /**
             * calWidthColText
             */
            private calWidthColText() {
                let self = this;
                let maxSizeNameCol: number = Math.max(self.getMaxSizeOfTextList(self.lstWorkplace()), 250);
                self.treeColumns()[1].width = maxSizeNameCol;
            }
            
            /**
             * getMaxSizeOfTextList
             */
            private getMaxSizeOfTextList(dataList: Array<TreeWorkplace>): number {
                let self = this;
                let max: number = 0;
                let paddingPerLevel: number = 32;
                let defaultFontSize: number = 14;
                let defaultFontFamily: Array<string> = ['DroidSansMono', 'Meiryo'];
                
                _.forEach(self.treeArray(), function(item) {
                    let o: any = $('<div id="test">' + item.nodeText + '</div>')
                        .css({
                            'position': 'absolute', 'float': 'left', 'white-space': 'nowrap', 'visibility': 'hidden',
                            'font-size': defaultFontSize, 'font-family': defaultFontFamily
                        })
                        .appendTo($('body'))
                    let w: number = o.width() + item.level * paddingPerLevel + 10;
                    if (w > max) {
                        max = w;
                    }
                    o.remove();
                });
                return max;
            }
            
            /**
             * convertMapHeirarchy
             */
            private convertMapHeirarchy(): any {
                let self = this;
                return _.reduce(self.treeArray(), function(hash: any, value: any) {
                    let key: any = value['hierarchyCode'];
                    hash[key] = value['name'];
                    return hash;
                }, {});
            }
            
            /**
             * convertTreeToArray
             */
            private convertTreeToArray(dataList: Array<TreeWorkplace>): Array<any> {
                let self = this;
                let res: Array<any> = [];
                _.forEach(dataList, function(item) {
                    if (item.childs && item.childs.length > 0) {
                        res = res.concat(self.convertTreeToArray(item.childs));
                    }
                    res.push({
                        workplaceId: item.workplaceId,
                        name: item.name,
                        nodeText: item.nodeText,
                        hierarchyCode: item.hierarchyCode,
                        level: item.level,
                    });
                })
                return res;
            }
        }
        
        /**
         * WorkplaceHistoryModel
         */
        class WorkplaceHistoryModel extends WorkplaceHistory {
            
            parentModel : ScreenModel;
            
            constructor(parentModel : ScreenModel) {
                super();
                let self = this;
                self.parentModel = parentModel;
                
                // subscribe
                self.selectedHistoryId.subscribe((newHistoryId) => {
                    // load workplace info by historyId
                    self.loadWkpHistoryInfo(self.parentModel.treeWorkplace().selectedWpkId(), newHistoryId);
                });
            }
            
            /**
             * addWkpHistoryDialog
             */
            public addWkpHistoryDialog() {
                let self = this;
                nts.uk.ui.windows.setShared("selectedWkpId", self.parentModel.treeWorkplace().selectedWpkId());
                nts.uk.ui.windows.sub.modal('/view/cmm/011/d/index.xhtml').onClosed(() => {
                    let isModeAdd: boolean = nts.uk.ui.windows.getShared("ModeAddHistory");
                    
                    // reload workplace history
                    if (isModeAdd) {
                        self.loadWkpHistoryByWkpId(self.parentModel.treeWorkplace().selectedWpkId());
                    }
                });
            }
            
            /**
             * updateWkpHistoryDialog
             */
            public updateWkpHistoryDialog() {
                let self = this;
                let objectTransfer: any = {};
                objectTransfer.wkpId = self.parentModel.treeWorkplace().selectedWpkId();
                objectTransfer.historyId = self.selectedHistoryId();
                objectTransfer.startDate = self.getSelectedHistoryByHistId().startDate;
                
                nts.uk.ui.windows.setShared("WokplaceHistoryInfor", objectTransfer);
                nts.uk.ui.windows.sub.modal('/view/cmm/011/e/index.xhtml').onClosed(() => {
                    let isUpdateMode: boolean = nts.uk.ui.windows.getShared("ModeUpdateHistory");
                    
                    // reload workplace history
                    if (isUpdateMode) {
                        self.loadWkpHistoryByWkpId(self.parentModel.treeWorkplace().selectedWpkId());
                    }
                });
            }
            
            /**
             * deleteWkpHistoryDialog
             */
            public deleteWkpHistory() {
                let self = this;
                nts.uk.ui.dialog.confirm({messageId: 'Msg_18'}).ifYes(() => {
                    let command: any = {};
                    command.workplaceId = self.parentModel.treeWorkplace().selectedWpkId();
                    command.historyId = self.selectedHistoryId();
                    
                    service.removeWorkplaceHistory(command).done(() => {
                        self.loadWkpHistoryByWkpId(self.parentModel.treeWorkplace().selectedWpkId());
                    }).fail((res: any) => {
                        nts.uk.ui.dialog.bundledErrors(res); 
                    })
                });
            }
            
            /**
             * loadWkpHistoryByWkpId
             */
            public loadWkpHistoryByWkpId(wkpId: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                nts.uk.ui.block.grayout();
                service.getLstWkpHist(wkpId).done(function(data: any) {
                    let lstWpkHistory = _.map(data.workplaceHistory, (item: any) => {
                        return { workplaceId: data.workplaceId, historyId: item.historyId,
                            startDate: item.period.startDate, endDate: item.period.endDate };
                    });
                    //update list hist
                    self.lstWpkHistory(lstWpkHistory);
                    
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }
            
            /**
             * loadWkpHistoryInfo
             */
            private loadWkpHistoryInfo(wkpId:string, historyId:string): JQueryPromise<void>{
                let self = this;
                let dfd = $.Deferred<any>();
                nts.uk.ui.block.grayout();
                service.getWkpInfoByHistId(wkpId, historyId).done(function(data:any) {
                    if (data) {
                        self.parentModel.workplaceCode(data.workplaceCode);
                        self.parentModel.workplaceName(data.workplaceName);
                        self.parentModel.wkpDisplayName(data.wkpDisplayName);
                        self.parentModel.wkpFullName(data.wkpGenericName);
                        self.parentModel.externalCode(data.outsideWkpCode);
                    }
                    
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }
        }
    }
}