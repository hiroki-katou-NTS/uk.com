module nts.uk.com.view.cmm011.a {
    export module viewmodel {

        import TreeWorkplace = service.model.TreeWorkplace;
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = base.IHistory;
        import Workplace = base.IWorkplace;
        import CreationType = base.CreationWorkplaceType;

        export class ScreenModel {

            wkpConfigHistId: string;
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
            isWkpHistoryLatest: KnockoutObservable<boolean>;
            isWkpConfigHistLatest: KnockoutObservable<boolean>;
            
            // when delete workplace config history (screen B)
            isDeleteWkpConfigHistory: KnockoutObservable<boolean>;

            constructor() {
                let self = this;

                self.wkpConfigHistId = null;
                self.strDWorkplace = ko.observable(null);
                self.endDWorkplace = ko.observable(nts.uk.resource.getText("CMM011_27"));
                self.treeWorkplace = ko.observable(new TreeWorkplaceModel(self));
                self.workplaceHistory = ko.observable(new WorkplaceHistoryModel(self));

                self.workplaceCode = ko.observable(null);
                self.externalCode = ko.observable(null);
                self.workplaceName = ko.observable('');
                self.wkpDisplayName = ko.observable(null);
                self.wkpFullName = ko.observable(null);

                self.creationType = null;

                self.isNewMode = ko.observable(false);
                self.isWkpHistoryLatest = ko.computed(function() {
                    self.workplaceHistory().selectedHistoryId();
                    return self.workplaceHistory().isSelectedLatestHistory();
                });
                self.isWkpConfigHistLatest = ko.observable(false);
                self.isDeleteWkpConfigHistory =  ko.observable(false);
                
                // subscribe
                self.strDWorkplace.subscribe((newValue) => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) {
                        self.openWkpConfigDialog();
                        return;
                    }
                    // load again tree workplace
                    self.treeWorkplace().findLstWorkplace(newValue).done(() => {
                        if (self.treeWorkplace().lstWorkplace() && self.treeWorkplace().lstWorkplace().length > 0) {
                            
                            // reload workplace history
                            if (self.treeWorkplace().lstWorkplace()[0].workplaceId == self.treeWorkplace().selectedWpkId()) {
                                self.isDeleteWkpConfigHistory.valueHasMutated;
                            }
                            self.treeWorkplace().selectFirst();
                        }
                    });
                });
                self.workplaceName.subscribe((newValue: string) => {
                    let obj: any = self.treeWorkplace().findPathNameByWkpIdSelected();
                    let wkpDisplayName: string = "";
                    let wkpFullName: string = "";
                    if (obj) {
                        wkpDisplayName = obj.wkpDisplayName + " ";
                        wkpFullName = obj.wkpFullName + " ";
                    }
                    self.wkpDisplayName(wkpDisplayName + newValue);
                    self.wkpFullName(wkpFullName + newValue);
                });
                self.isDeleteWkpConfigHistory.subscribe(newValue => {
                    if (!newValue) {
                        return;
                    }
                    // reload workplace history
                    self.treeWorkplace().selectedWpkId.valueHasMutated();
                });
            }

            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();

                // find all history master
                self.findAllHistory().done(() => {
                    dfd.resolve();
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
                    nts.uk.ui.block.clear();
                    // set start date, end date
                    if (data && data.length > 0) {
                        self.isWkpConfigHistLatest(true);
                        self.wkpConfigHistId = data[0].historyId;
                        self.strDWorkplace(data[0].startDate);
                        self.endDWorkplace(data[0].endDate);
                    } else {
                        self.strDWorkplace.valueHasMutated();
                    }
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
                return dfd.promise();
            }

            /**
             * removeWorkplace 
             */
            public removeWorkplace() {
                let self = this;

                let command: any = {};
                command.historyIdWkpConfigInfo = self.wkpConfigHistId;
                command.startDWkpConfigInfo = new Date(self.strDWorkplace());
                command.wkpIdSelected = self.treeWorkplace().selectedWpkId();

                nts.uk.ui.block.grayout();
                service.removeWkp(command).done(function() {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    self.treeWorkplace().findLstWorkplace(self.strDWorkplace()).done(() => {
                        if (self.treeWorkplace().lstWorkplace.length > 0) {
                            self.treeWorkplace().selectFirst();
                        }
                    });
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }

            /**
             * save workplace
             */
            public saveWorkplace() {
                let self = this;

                if (!self.validate()) {
                    return;
                }

                let command: any = self.toJsonObject();

                nts.uk.ui.block.grayout();
                service.saveWkp(command).done(function() {
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });

                    self.treeWorkplace().findLstWorkplace(self.strDWorkplace()).done(() => {
                        if (self.isNewMode()) {
                            self.treeWorkplace().selectedWpkId(command.wkpIdSelected);
                        } else {
                            self.treeWorkplace().selectedWpkId(self.treeWorkplace().findWkpId(command.wkpInfor.workplaceCode));
                        }
                    });
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }

            /**
             * toJsonObject
             */
            public toJsonObject(): any {
                let self = this;

                let command: any = {};
                command.isAddMode = self.isNewMode();
                command.createType = self.creationType;
                command.startDate = new Date(self.strDWorkplace());
                command.wkpConfigInfoHistId = self.wkpConfigHistId;
                command.wkpIdSelected = self.treeWorkplace().selectedWpkId();

                let workplace: any = {};
                workplace.workplaceId = null;
                let wkpHistory: any = {
                    historyId: self.workplaceHistory().selectedHistoryId(),
                    period: {
                        startDate: new Date(self.workplaceHistory().getSelectedHistoryByHistId().startDate),
                        endDate: new Date(self.workplaceHistory().getSelectedHistoryByHistId().endDate)
                    }
                };
                workplace.wkpHistory = wkpHistory;
                command.workplace = workplace;

                let wkpInfor: any = {};
                wkpInfor.historyId = self.workplaceHistory().selectedHistoryId();
                wkpInfor.workplaceCode = self.workplaceCode();
                wkpInfor.workplaceName = self.workplaceName();
                wkpInfor.wkpGenericName = self.wkpFullName();
                wkpInfor.wkpDisplayName = self.wkpDisplayName();
                wkpInfor.outsideWkpCode = self.externalCode();
                command.wkpInfor = wkpInfor;

                return command;
            }

            /**
             * openWkpConfigDialog
             */
            public openWkpConfigDialog() {
                let self = this;
                nts.uk.ui.windows.sub.modal('/view/cmm/011/b/index.xhtml').onClosed(() => {
                    let dialogData = nts.uk.ui.windows.getShared("ShareDateScreenParent");
                    if (!dialogData) {
                        return;
                    }
                    self.isDeleteWkpConfigHistory(dialogData.isDeletionMode);
                    self.isWkpConfigHistLatest(dialogData.isWkpConfigHistLatest);
                    self.wkpConfigHistId = dialogData.historyId;
                    self.strDWorkplace(dialogData.startDate);
                    self.endDWorkplace(dialogData.endDate);
                });
            }

            /**
             * createWkpDialog
             */
            public createWkpDialog() {
                let self = this;

                let condition: any = {};
                condition.startDate = new Date(self.strDWorkplace());
                condition.workplaceId = self.treeWorkplace().selectedWpkId();

                nts.uk.ui.block.invisible();
                service.checkWorkplaceState(condition).done((res: any) => {
                    nts.uk.ui.block.clear();
                    let objTransfer: any = {};
                    objTransfer.code = self.workplaceCode();
                    objTransfer.name = self.workplaceName();
                    objTransfer.isLess999Heirarchies = res.isLessMaxSiblings;
                    objTransfer.isLessTenthHeirarchy = res.isLessMaxHierarchy;
                    nts.uk.ui.windows.setShared("ObjectTransfer", objTransfer);

                    nts.uk.ui.windows.sub.modal('/view/cmm/011/f/index.xhtml').onClosed(() => {
                        let creationType: CreationType = nts.uk.ui.windows.getShared("CreatedWorkplaceCondition");
                        if (creationType) {
                            self.isNewMode(true);
                            self.creationType = creationType;
                            self.workplaceHistory().newHistory();
                        }
                    });
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }

            /**
             * validate
             */
            private validate() {
                let self = this;

                self.clearError();

                $('#wkpCd').ntsEditor('validate');
                $('#wkpName').ntsEditor('validate');
                $('#wkpDisplayName').ntsEditor('validate');
                $('#wkpFullName').ntsEditor('validate');

                return !$('.nts-input').ntsError('hasError');
            }

            /**
             * clearError
             */
            private clearError() {
                $('#wkpCd').ntsError('clear');
                $('#wkpName').ntsError('clear');
                $('#wkpDisplayName').ntsError('clear');
                $('#wkpFullName').ntsError('clear');
            }

            /**
             * initData
             */
            public initData(data: any) {
                let self = this;
                if (data) {
                    self.workplaceCode(data.workplaceCode);
                    self.workplaceName(data.workplaceName);
                    self.wkpDisplayName(data.wkpDisplayName);
                    self.wkpFullName(data.wkpGenericName);
                    self.externalCode(data.outsideWkpCode);
                } else {
                    self.workplaceCode(null);
                    self.workplaceName(null);
                    self.wkpDisplayName(null);
                    self.wkpFullName(null);
                    self.externalCode(null);
                }
            }

            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                if (!res.businessException) {
                    return;
                }
                if (Array.isArray(res.messageId)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
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

                    if (!dataList || dataList.length < 1) {
                        $('#wkpCd').focus();
                        self.parentModel.isNewMode(true);
                        self.parentModel.workplaceHistory().newHistory();
                        return;
                    }

                    // update nodeText, level of tree
                    self.updateTree(self.lstWorkplace());

                    // convert tree to array
                    self.treeArray(self.convertTreeToArray(dataList));

                    // calculate width of cell nodeText
                    self.calWidthColText();

                    // existed workplace
                    self.parentModel.isNewMode(false);
                });

                //subscribe selected wkp Id
                self.selectedWpkId.subscribe(newValue => {
                    self.parentModel.isNewMode(false);
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
            public findLstWorkplace(startDate: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();

                if (nts.uk.text.isNullOrEmpty(startDate)) {
                    return dfd.promise();
                }

                nts.uk.ui.block.grayout();
                service.findLstWorkPlace(new Date(startDate)).done((res: Array<TreeWorkplace>) => {
                    nts.uk.ui.block.clear();
                    self.lstWorkplace(res);
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
                if (nts.uk.text.isNullOrEmpty(self.selectedHierarchyCd)) {
                    return null;
                }
                while (index <= self.selectedHierarchyCd.length) {
                    let parentHeirarchyCd: string = self.selectedHierarchyCd.substr(0, index);
                    wkpFullName += " " + self.mapHierarchy[parentHeirarchyCd];
                    index += 3;
                }
                obj.wkpFullName = wkpFullName.trim();
                obj.wkpDisplayName = self.mapHierarchy[self.selectedHierarchyCd];
                return obj;
            }

            /**
             * findWkpId
             */
            public findWkpId(wkpCd: string): string {
                let self = this;
                let wkpIdSelected: string = "";
                for (let item of self.treeArray()) {
                    if (item.code == wkpCd) {
                        wkpIdSelected = item.workplaceId;
                        break;
                    }
                }
                return wkpIdSelected;
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
                for (let item of self.treeArray()) {
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
                        code: item.code,
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

            parentModel: ScreenModel;

            constructor(parentModel: ScreenModel) {
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
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
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
                        return {
                            workplaceId: data.workplaceId, historyId: item.historyId,
                            startDate: item.startDate, endDate: item.endDate
                        };
                    });
                    //update list hist
                    self.lstWpkHistory(lstWpkHistory);

                    $('#wkpName').focus();

                    nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }

            /**
             * newHistory
             */
            public newHistory() {
                let self = this;
                self.lstWpkHistory([]);
                let newHist: IHistory = { historyId: null, startDate: self.parentModel.strDWorkplace(), endDate: "9999/12/31" };
                self.lstWpkHistory.push(newHist);
            }

            /**
             * loadWkpHistoryInfo
             */
            private loadWkpHistoryInfo(wkpId: string, historyId: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();
//                nts.uk.ui.block.grayout(); // cause: loose focus item selected combo
                service.getWkpInfoByHistId(wkpId, historyId).done(function(data: any) {
//                    nts.uk.ui.block.clear();
                    self.parentModel.initData(data);
                    dfd.resolve();
                }).fail((res: any) => {
//                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }
        }
    }
}