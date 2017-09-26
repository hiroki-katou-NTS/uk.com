module nts.uk.com.view.cmm011.a {
    export module viewmodel {
        
        import TreeWorkplace = service.model.TreeWorkplace;
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
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
            
            
            constructor() {
                let self = this;
                
                self.strDWorkplace = ko.observable("2000/10/11");
                self.endDWorkplace = ko.observable("9999/12/31");
                self.treeWorkplace = ko.observable(new TreeWorkplaceModel(self));
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel());
                
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
                    if (self.isNewMode()) {
                        return false;
                    }
                    return !nts.uk.text.isNullOrEmpty(self.workplaceHistory().selectedWpkHistory());
                });
                
                // subscribe
                self.strDWorkplace.subscribe((newValue) => {
                    if (!newValue) {
                        self.configureWkpDialog();
                    }
                });
                self.workplaceName.subscribe((newValue) => {
                    let obj: any = self.treeWorkplace().findPathNameByWkpIdSelected();
                    self.wkpDisplayName(obj.wkpDisplayName + " " + newValue);
                    self.wkpFullName(obj.wkpFullName + " " + newValue);
                });
                self.workplaceHistory().selectedWpkHistory.subscribe((historyId) => {
                    //load workplace info by historyId
                    self.getAndBindWkpInfo(self.treeWorkplace().selectedWpkId(), self.workplaceHistory().selectedWpkHistory());
                });
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                let treeWorkplace: TreeWorkplaceModel = self.treeWorkplace();
                treeWorkplace.findLstWorkplace(new Date(self.strDWorkplace())).done(() => {
                    if (treeWorkplace.lstWorkplace().length > 0) {
                        treeWorkplace.selectFirst();
                        self.isNewMode(false);
                    }
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            /**
             * get And Bind WkpInfo data to screen 
             */
            private getAndBindWkpInfo(wkpId:string, historyId:string):void{
                var self = this;
                service.getWkpInfoByHistId(wkpId,historyId).done(function(data:any) {
                    if (data) {
                        self.workplaceCode(data.workplaceCode);
                        self.workplaceName(data.workplaceName);
                        self.wkpDisplayName(data.wkpDisplayName);
                        self.wkpFullName(data.wkpGenericName);
                        self.externalCode(data.outsideWkpCode);
                    }
                });
            }
            
            /**
             * configureWkpDialog
             */
            public configureWkpDialog() {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/011/b/index.xhtml').onClosed(() => {
                    let dialogData = nts.uk.ui.windows.getShared("ShareDateScreenParent");
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
                objTransfer.isLessTenthHeirarchy = self.treeWorkplace().selectedHeirarchyCd.length / 3 < 10;
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

            screenModel: ScreenModel;
            
            treeColumns: KnockoutObservableArray<any>;
            lstWorkplace: KnockoutObservableArray<TreeWorkplace>;
            selectedWpkId: KnockoutObservable<string>;
            
            treeArray: KnockoutObservableArray<any>;
            selectedHeirarchyCd: string;
            mapHeirarchy: any;

            constructor(screenModel: ScreenModel) {
                let self = this;
                self.screenModel = screenModel;
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
                });
                
                //subscribe selected wkp Id
                self.selectedWpkId.subscribe(newValue => {
                    self.selectedHeirarchyCd = self.getSelectedHeirarchyCd();
                    //TODO get wkp list hist by wkpId
                    service.getLstWkpHist(newValue).done(function(data: any) {

                        let lstWpkHistory = _.map(data.workplaceHistory, (item:any) => {
                            return { workplaceId: data.workplaceId, historyId: item.historyId, startDate: item.period.startDate, endDate: item.period.endDate };
                        });
                        //update list hist
                        if (self.screenModel) {
                            self.screenModel.workplaceHistory().init(lstWpkHistory);
                        }
                        //TODO
                    });
                });
                self.treeArray.subscribe(newArray => {
                    self.mapHeirarchy = self.convertMapHeirarchy();
                });
            }

            /**
             * findLstWorkplace
             */
            public findLstWorkplace(startDate: Date): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();
                service.findLstWorkPlace(startDate).done((res: Array<TreeWorkplace>) => {
                    self.lstWorkplace(res);
                    
                    // calculate width of cell nodeText
                    self.calWidthColText();
                    dfd.resolve();
                }).fail((res: any) => {
                    self.screenModel.showMessageError(res);
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
                while(index <= self.selectedHeirarchyCd.length) {
                    let parentHeirarchyCd: string = self.selectedHeirarchyCd.substr(0, index);
                    wkpFullName += " " + self.mapHeirarchy[parentHeirarchyCd];
                    index += 3;
                }
                obj.wkpFullName = wkpFullName.trim();
                obj.wkpDisplayName = self.mapHeirarchy[self.selectedHeirarchyCd];
                return obj;
            }
            
            /**
             * isValidLimitHeirachy
             */
            public isValidLimitHeirachy(): boolean {
                let self = this;
                let lengthElementSameHeirachies: number = self.treeArray().filter(item => {
                    if (item.hierarchyCode.length != self.selectedHeirarchyCd.length) {
                        return false;
                    }
                    let parentHeirarchyCd: string = item.hierarchyCode.substr(0, item.hierarchyCode.length - 3);
                    let parentSelectedHeirarchyCd: string = self.selectedHeirarchyCd.substr(0,
                        self.selectedHeirarchyCd.length - 3);
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
                    
                    if (item.childs) {
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
            
            constructor() {
                super();
                let self = this;
                //list is empty 
                self.init([]);
            }
            
            init(data: any) {
                let self = this;
                self.lstWpkHistory(data);
                self.selectFirst();
            }
            
            /**
             * addWkpHistoryDialog
             */
            public addWkpHistoryDialog() {
                let self = this;
                let startDateLatest: string = self.lstWpkHistory()[0].startDate;
                nts.uk.ui.windows.setShared("StartDateLatestHistory", startDateLatest);
                nts.uk.ui.windows.sub.modal('/view/cmm/011/d/index.xhtml').onClosed(() => {
                });
            }
            
            /**
             * updateWkpHistoryDialog
             */
            public updateWkpHistoryDialog() {
                let self = this;
                nts.uk.ui.windows.setShared("StartDateHistory", self.getSelectedHistoryByWkpId().startDate);
                nts.uk.ui.windows.sub.modal('/view/cmm/011/e/index.xhtml').onClosed(() => {
                });
            }
            
            /**
             * deleteWkpHistoryDialog
             */
            public deleteWkpHistoryDialog() {
            }
        }
    }
}