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
                
                self.strDWorkplace = ko.observable("2016/04/01");
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
            
            static fakeDataWorkplace(): Array<TreeWorkplace> {
                return [
                    new TreeWorkplace("001", '001', 'Name001', "001", [
                        new TreeWorkplace('001001', '001001', 'Name001001', '001001', [
                            new TreeWorkplace('001001001', '001001001', 'Name001001001', '001001001', [])
                        ]),
                        new TreeWorkplace('001002', '001002', 'Name001002', '001002', [])
                    ]),
                    new TreeWorkplace('002', '002', 'Name002', '002', [
                        new TreeWorkplace('002001', '002001', 'Name002001', '002001', []),
                        new TreeWorkplace('002002', '002002', 'Name002002', '002002', [
                            new TreeWorkplace('002002001', '002002001', 'Name002002001', '002002001', [])
                        ])
                    ]),
                    new TreeWorkplace('003', '003', 'Name003', '003', [
                        new TreeWorkplace('003001', '003001', 'Name003001', '003001', []),
                        new TreeWorkplace('003002', '003002', 'Name003002', '003002', [
                            new TreeWorkplace('003002001', '003002001', 'Name003002001', '003002001', [])
                        ])
                    ]),
                    new TreeWorkplace('004', '004', 'Name004', '004', []),
                    new TreeWorkplace('005', '005', 'Name005', '005', [
                        new TreeWorkplace('005001', '005001', 'Name005001', '005001', []),
                        new TreeWorkplace('005002', '005002', 'Name005002', '005002', [
                            new TreeWorkplace('005002001', '005002001', 'Name005002001', '005002001', [
                                new TreeWorkplace('005002001001', '005002001001', 'Name005002001001', '005002001001', [
                                    new TreeWorkplace('005002001001001', '005002001001001', 'Name005002001001001', '005002001001001', []),
                                    new TreeWorkplace('005002001001002', '005002001001002', 'Name005002001001002', '005002001001002', [
                                        new TreeWorkplace('005002001001001001', '005002001001001001', '005002001001001001', '005002001001001001', [])
                                    ])
                                ])
                            ])
                        ])
                    ]),
                ];
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

                self.treeColumns = ko.observableArray([
                    { headerText: "", key: 'workplaceId', dataType: "string", hidden: true },
                    { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: 250, dataType: "string" }
                ]);
                self.lstWorkplace = ko.observableArray([]);
                self.selectedWpkId = ko.observable(null);
                self.treeArray = ko.observableArray([]);
                
                // subscribe
                self.lstWorkplace.subscribe(dataList => {
                    self.treeArray(self.convertTreeToArray(dataList));
                });
                self.selectedWpkId.subscribe(newValue => {
                    self.selectedHeirarchyCd = self.getSelectedHeirarchyCd();
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
                    self.calWidthColText()
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
                self.selectedWpkId(self.lstWorkplace()[0].workplaceId);
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
                    if (item.heirarchyCode.length != self.selectedHeirarchyCd.length) {
                        return false;
                    }
                    let parentHeirarchyCd: string = item.heirarchyCode.substr(0, item.heirarchyCode.length - 3);
                    let parentSelectedHeirarchyCd: string = self.selectedHeirarchyCd.substr(0,
                        self.selectedHeirarchyCd.length - 3);
                    return parentHeirarchyCd == parentSelectedHeirarchyCd;
                }).length;
                let maxHeirarchies: number = 999;
                return lengthElementSameHeirachies < maxHeirarchies;
            }
            
            /**
             * getSelectedHeirarchyCd
             */
            private getSelectedHeirarchyCd(): string {
                let self = this;
                let heirarchyCode: string = "";
                for(let item of self.treeArray()) {
                    if (item.workplaceId == self.selectedWpkId()) {
                        heirarchyCode = item.heirarchyCode;
                        break;
                    }
                }
                return heirarchyCode;
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
                    let key: any = value['heirarchyCode'];
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
                        heirarchyCode: item.heirarchyCode,
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
                
                self.init();
            }
            
            init() {
                let self = this;
                let lstWpkHistory: Array<IHistory> = [
                    {workplaceId: "ABC1", historyId: "ABC1", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC2", historyId: "ABC2", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC3", historyId: "ABC3", startDate: "2015/04/01", endDate: "9999/12/31"},
                    {workplaceId: "ABC4", historyId: "ABC4", startDate: "2015/04/01", endDate: "9999/12/31"}
                ]
                self.lstWpkHistory(lstWpkHistory);
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