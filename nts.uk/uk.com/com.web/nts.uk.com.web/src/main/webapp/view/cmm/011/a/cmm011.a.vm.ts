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
            
            public configureWkpDialog() {
                nts.uk.ui.windows.sub.modal('/view/cmm/011/b/index.xhtml').onClosed(() => {
                });
            }
            
            public createWkpDialog() {
                let self = this;
                
                let workplace: Workplace = {code: self.workplaceCode(), name: self.workplaceName()};
                nts.uk.ui.windows.setShared("WorkplaceInfor", workplace);
                
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
            
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
        
        class TreeWorkplaceModel {

            screenModel: ScreenModel;
            
            treeColumns: KnockoutObservableArray<any>;
            lstWorkplace: KnockoutObservableArray<TreeWorkplace>;
            selectedWpkId: KnockoutObservable<string>;
            
            treeArray: KnockoutObservableArray<any>;

            constructor(screenModel: ScreenModel) {
                let self = this;

                self.treeColumns = ko.observableArray([
                    { headerText: "", key: 'workplaceId', dataType: "string", hidden: true },
                    { headerText: "コード/名称", key: 'nodeText', width: 250, dataType: "string" }
                ]);
                self.lstWorkplace = ko.observableArray([]);
                self.selectedWpkId = ko.observable(null);
                self.treeArray = ko.observableArray([]);
            }

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

            public selectFirst() {
                let self = this;
                self.selectedWpkId(self.lstWorkplace()[0].workplaceId);
            }
            
            public calHeightTree(): number {
                let heightCell: number = 26;
                return heightCell * 20;
            }
            
            public findPathNameByWkpIdSelected(): any {
                let self = this;
                
                let selectedHeirarchyCd: string = null;
                let obj: any = {};
                let mapHeirarchy: any = _.reduce(self.treeArray(), function(hash, value) {
                    let key = value['heirarchyCode'];
                    hash[key] = value['name'];
                    if (value['workplaceId'] == self.selectedWpkId()) {
                        obj.wkpDisplayName = value['name'];
                        selectedHeirarchyCd = key;
                    }
                    return hash;
                }, {});
                
                let index: number = 3;
                let wkpFullName: string = "";
                while(index <= selectedHeirarchyCd.length) {
                    let parentHeirarchyCd: string = selectedHeirarchyCd.substr(0, index);
                    wkpFullName += " " + mapHeirarchy[parentHeirarchyCd];
                    index += 3;
                }
                obj.wkpFullName = wkpFullName.trim();
                return obj;
            }
            
            private calWidthColText() {
                let self = this;
                let maxSizeNameCol: number = Math.max(self.getMaxSizeOfTextList(self.lstWorkplace()), 250);
                self.treeColumns()[1].width = maxSizeNameCol;
            }
            
            private getMaxSizeOfTextList(dataList: Array<TreeWorkplace>): number {
                let self = this;
                let max: number = 0;
                let paddingPerLevel: number = 32;
                let defaultFontSize: number = 14;
                let defaultFontFamily: Array<string> = ['DroidSansMono', 'Meiryo'];
                
                // convert tree to array
                let textArray: Array<any> = self.convertTreeToArray(dataList);
                self.treeArray(textArray);
                
                _.forEach(textArray, function(item) {
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
            
            public addWkpHistoryDialog() {
                let self = this;
                let startDateLatest: string = self.lstWpkHistory()[0].startDate;
                nts.uk.ui.windows.setShared("StartDateLatestHistory", startDateLatest);
                nts.uk.ui.windows.sub.modal('/view/cmm/011/d/index.xhtml').onClosed(() => {
                });
            }
            
            public updateWkpHistoryDialog() {
                nts.uk.ui.windows.sub.modal('/view/cmm/011/e/index.xhtml').onClosed(() => {
                });
            }
            
            public deleteWkpHistoryDialog() {
            }
        }
    }
}