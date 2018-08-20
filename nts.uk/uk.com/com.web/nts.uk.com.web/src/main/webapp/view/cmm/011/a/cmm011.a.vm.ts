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
            isSelectedWpkId: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;

                self.isNewMode = ko.observable(false);
                
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

                self.isWkpHistoryLatest = ko.computed(function() {
                    self.workplaceHistory().selectedHistoryId();
                    return self.workplaceHistory().isSelectedLatestHistory();
                });
                self.isWkpConfigHistLatest = ko.observable(false);
                
                self.isSelectedWpkId = ko.computed(() => {
                    if (!nts.uk.text.isNullOrEmpty(self.treeWorkplace().selectedWpkId())) {
                        return true;
                    }
                    // check when new mode
                    if (self.treeWorkplace().lstWorkplace().length < 1
                        || self.treeWorkplace().lstWorkplace().length > 0 && self.workplaceHistory().lstWpkHistory().length > 0) {
                        return self.isNewMode();
                    }
                    // check case unselect item in grid
                    return !self.isNewMode();
                })
                
                // subscribe
                self.strDWorkplace.subscribe((newValue) => {
                    
                    // check null or empty
                    if (nts.uk.text.isNullOrEmpty(newValue)) {
                        self.openWkpConfigDialog();
                        return;
                    }
                    // load again tree workplace
                    self.treeWorkplace().findLstWorkplace(newValue).done(() => {
                        if (self.treeWorkplace().lstWorkplace() && self.treeWorkplace().lstWorkplace().length > 0) {
                            
                            // reload workplace history
                            let wkpIdFirst: string = self.treeWorkplace().lstWorkplace()[0].workplaceId;
                            if (wkpIdFirst == self.treeWorkplace().selectedWpkId()) {
                                self.treeWorkplace().selectedWpkId.valueHasMutated();
                            }
                            self.treeWorkplace().selectFirst();
                        }
                    });
                });
                self.workplaceName.subscribe((newValue: string) => {
                    
                    // set workplace name, workplace full name
                    let wkpFullName: string = self.treeWorkplace().findPathNameByWkpIdSelected();
                    if (wkpFullName) {
                        wkpFullName += " " + newValue;
                    } else {
                        wkpFullName = newValue;
                    }
                    if (!_.isEmpty(self.workplaceName())) {
                        if (_.isEmpty(self.wkpDisplayName())) {
                        self.wkpDisplayName(newValue);    
                        }
                        if (_.isEmpty(self.wkpFullName())) {
                            self.wkpFullName(wkpFullName);    
                        }
                        
                        // clear error
                        self.clearError();
                        $('#wkpDisplayName').ntsEditor("validate");
                        $('#wkpFullName').ntsEditor("validate");    
                    }
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
                
                // find list workplace config history
                service.findLstWkpConfigHistory().done(function(data: Array<IHistory>) {
                    nts.uk.ui.block.clear();
                    
                    // set start date, end date
                    if (data && data.length > 0) {
                        self.isWkpConfigHistLatest(true);
                        self.wkpConfigHistId = data[0].historyId;
                        self.strDWorkplace(data[0].startDate);
                        self.endDWorkplace(data[0].endDate);
                    }
                    // case no has workplace config
                    else {
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
                
                // show message confirm
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                
                    let parentWorkplaceId: string = self.getParentWorkplaceId(self.treeWorkplace().selectedWpkId());
                    
                    // to JsObject
                    let command: any = {};
                    command.historyIdWkpConfigInfo = self.wkpConfigHistId;
                    command.startDWkpConfigInfo = new Date(self.strDWorkplace());
                    command.wkpIdSelected = self.treeWorkplace().selectedWpkId();

                    nts.uk.ui.block.grayout();
                    
                    // remove workplace
                    service.removeWkp(command).done(function() {
                        nts.uk.ui.block.clear();
                        
                        // show message notice
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                            
                            // find list workplace
                            self.treeWorkplace().findLstWorkplace(self.strDWorkplace()).done(() => {
                                
                                // set choose item first of list
                                if (self.treeWorkplace().lstWorkplace().length > 0) {
                                    self.treeWorkplace().selectFirst();
//                                    if (nts.uk.util.isNullOrEmpty(parentWorkplaceId)) {
//                                        self.treeWorkplace().selectFirst();
//                                    } else {
//                                        self.treeWorkplace().selectedWpkId(parentWorkplaceId);
//                                    }                                 
                                }
                            });
                        });
                    }).fail((res: any) => {
                        nts.uk.ui.block.clear();
                        self.showMessageError(res);
                    });
                });
            }

            /**
             * save workplace
             */
            public saveWorkplace() {
                let self = this;

                // validate
                if (!self.validate()) {
                    return;
                }

                // get JsObject
                let command: any = self.toJsonObject();

                nts.uk.ui.block.grayout();
                
                // insert or update workplace
                service.saveWkp(command).done(function() {
                    nts.uk.ui.block.clear();
                    
                    // reset setting create workplace
                    self.creationType = null;
                    
                    // notice success
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        
                        // find list workplace
                        self.treeWorkplace().findLstWorkplace(self.strDWorkplace()).done(() => {
                            
                            // check new mode
                            if (self.isNewMode()) {
                                self.treeWorkplace().selectedWpkId(command.wkpIdSelected);
                            }
                            // update mode
                            else {
                                self.treeWorkplace().selectedWpkId(self.treeWorkplace()
                                    .findWkpId(command.wkpInfor.workplaceCode));
                            }
                        });
                    });
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }
            
            /**
             * getParentWorkplaceId
             */        
            private getParentWorkplaceId(childWorkplaceId: string): string {
                let self = this;
                
                let workplaces: any[] = self.treeWorkplace().treeArray();
                let childWorkplace: any = _.find(workplaces, (wk) => { return wk.workplaceId === childWorkplaceId; });
                let parentWorkplaceHierarchy: string = childWorkplace.hierarchyCode.slice(0, -3);
                
                if (nts.uk.util.isNullOrEmpty(parentWorkplaceHierarchy)) {
                    return null;
                }
                
                let parentWorkplace: any = _.find(workplaces, (wk) => { return wk.hierarchyCode === parentWorkplaceHierarchy; });
                if (nts.uk.util.isNullOrUndefined(parentWorkplace)) {
                    return null;
                }
                return parentWorkplace.workplaceId;
            }

            /**
             * toJsonObject
             */
            public toJsonObject(): any {
                let self = this;

                // to JsObject
                let command: any = {};
                command.isAddMode = self.isNewMode();
                command.createType = self.creationType;
                command.startDate = new Date(self.strDWorkplace());
                command.wkpConfigInfoHistId = self.wkpConfigHistId;
                
                if (self.isNewMode()) {
                    command.wkpIdSelected = self.treeWorkplace().currentWpkId();
                } else {
                    command.wkpIdSelected = self.treeWorkplace().selectedWpkId();
                }

                // data workplace
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
                
                // set workplace
                command.workplace = workplace;

                // data workplace infor
                let wkpInfor: any = {};
                wkpInfor.historyId = self.workplaceHistory().selectedHistoryId();
                wkpInfor.workplaceCode = self.workplaceCode();
                wkpInfor.workplaceName = self.workplaceName();
                wkpInfor.wkpGenericName = self.wkpFullName();
                wkpInfor.wkpDisplayName = self.wkpDisplayName();
                wkpInfor.outsideWkpCode = self.externalCode();
                
                // set workplace infor
                command.wkpInfor = wkpInfor;

                return command;
            }

            /**
             * openWkpConfigDialog
             */
            public openWkpConfigDialog() {
                let self = this;
                
                // to JsObject
                let dateRange: any = {};
                dateRange.start = self.strDWorkplace();
                dateRange.end = self.endDWorkplace();
                
                // share date range
                nts.uk.ui.windows.setShared("DateRange", dateRange);
                
                nts.uk.ui.windows.sub.modal('/view/cmm/011/b/index.xhtml').onClosed(() => {
                    
                    // get respond data
                    let dialogData = nts.uk.ui.windows.getShared("ShareDateScreenParent");
                    if (!dialogData) {
                        return;
                    }
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

                // to JsObject
                let condition: any = {};
                condition.startDate = new Date(self.strDWorkplace());
                condition.workplaceId = self.treeWorkplace().selectedWpkId();

                nts.uk.ui.block.invisible();
                
                service.checkWorkplaceState(condition).done((res: any) => {
                    nts.uk.ui.block.clear();
                    
                    // to JsObject
                    let objTransfer: any = {};
                    objTransfer.code = self.workplaceCode();
                    objTransfer.name = self.workplaceName();
                    objTransfer.isLess999Hierarchies = res.isLessMaxSiblings;
                    objTransfer.isLessTenthHierarchy = res.isLessMaxHierarchy;
                    
                    // share data dialog.
                    nts.uk.ui.windows.setShared("ObjectTransfer", objTransfer);

                    nts.uk.ui.windows.sub.modal('/view/cmm/011/f/index.xhtml').onClosed(() => {
                        
                        // get creation type
                        let creationType: CreationType = nts.uk.ui.windows.getShared("CreatedWorkplaceCondition");
                        if (creationType) {
                            self.isNewMode(true);
                            self.creationType = creationType;
                            
                            // set current select item grid
                            self.treeWorkplace().currentWpkId(self.treeWorkplace().selectedWpkId());
                            
                            // reset selected item grid
                            self.treeWorkplace().selectedWpkId(null);
                            
                            // in new mode, find hierarchy before select
                            self.treeWorkplace().selectedHierarchyCd = self.treeWorkplace()
                                .findSelectedHierarchyCd(self.treeWorkplace().currentWpkId());
                            
                            // new workplace history
                            self.workplaceHistory().newHistory();
                            
                            // select item 
                            self.workplaceHistory().setSelectionHistSuitable();
                        }
                        // Focus
                        $('#wkpCd').focus();
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

                // clear error
                self.clearError();

                // validate
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
                // has data
                if (data) {
                    self.workplaceCode(data.workplaceCode);
                    self.workplaceName(data.workplaceName);
                    self.wkpDisplayName(data.wkpDisplayName);
                    self.wkpFullName(data.wkpGenericName);
                    self.externalCode(data.outsideWkpCode);
                }
                // no data
                else {
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
                let dfd = $.Deferred<any>();
                
                // check error business exception
                if (!res.businessException) {
                    return;
                }
                
                // show error message
                if (Array.isArray(res.errors)) {
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

            treeColumns: Array<any>;
            lstWorkplace: KnockoutObservableArray<TreeWorkplace>;
            selectedWpkId: KnockoutObservable<string>;
            currentWpkId: KnockoutObservable<string>;
            
            treeArray: KnockoutObservableArray<any>;
            selectedHierarchyCd: string;
            mapHierarchy: any;
            
            treeStyle: TreeStyle;
            
            constructor(parentModel: ScreenModel) {
                let self = this;

                self.parentModel = parentModel;

                self.lstWorkplace = ko.observableArray([]);
                self.selectedWpkId = ko.observable(null);
                self.currentWpkId = ko.observable(null);
                
                self.treeArray = ko.observableArray([]);
                
                self.treeColumns = self.treeColumns = [
                    { headerText: "", key: 'workplaceId', dataType: "string", hidden: true},
                    { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: 250,
                        dataType: "string" }
                ];
                self.treeStyle = {
                    width: 385,
                    height: 504
                };
                
                // subscribe
                self.lstWorkplace.subscribe(dataList => {
                    if (!dataList || dataList.length < 1) {
                        
                        // didn't exist workplace
                        self.parentModel.isNewMode(true);
                        
                        // set focus
                        $('#wkpCd').focus();
                        
                        // create new workplace history
                        self.parentModel.workplaceHistory().newHistory();
                        return;
                    }

                    // update nodeText, level of tree
                    self.updateTree(dataList);

                    // convert tree to array
                    self.treeArray(self.convertTreeToArray(dataList));
                    
                    // calculate with column name
                    self.calWidthColText();
                    
                    // existed workplace
                    self.parentModel.isNewMode(false);
                });

                //subscribe selected wkp Id
                self.selectedWpkId.subscribe(newValue => {
                    
                    // validate null or empty
                    if (nts.uk.text.isNullOrEmpty(newValue)) {
                        
                        self.parentModel.isNewMode(true);
                        
                        // reset data case unselect item in grid
                        self.selectedHierarchyCd = null;
                        self.parentModel.initData(null);
                        if (self.lstWorkplace().length > 0) {
                            self.parentModel.workplaceHistory().reset();
                        }
                        return;
                    }
                    
                    // set update mode
                    self.parentModel.isNewMode(false);
                    
                    // get hierarchy code selected.
                    self.selectedHierarchyCd = self.findSelectedHierarchyCd(newValue);

                    // get wkp list hist by wkpId
                    self.parentModel.workplaceHistory().loadWkpHistoryByWkpId(newValue);
                });
                self.treeArray.subscribe(newArray => {
                    
                    // convert tree to map
                    self.mapHierarchy = self.convertMapHierarchy();
                });
            }

            /**
             * findLstWorkplace
             */
            public findLstWorkplace(startDate: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();

                // check null or empty
                if (nts.uk.text.isNullOrEmpty(startDate)) {
                    return dfd.promise();
                }

                // find workplace by basedate
                nts.uk.ui.block.grayout();
                service.findLstWorkPlace(new Date(startDate)).done((res: Array<TreeWorkplace>) => {
                    nts.uk.ui.block.clear();
                    
                    // set data
                    self.lstWorkplace(res);
                    
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();

                    if (res.messageId == 'Msg_373') {
                        nts.uk.ui.dialog.info({messageId: res.messageId}).then(() => {
                            self.lstWorkplace([]);
                            // reset selected workplace when list empty
                            self.parentModel.treeWorkplace().selectedWpkId(null);
                        });
                    } else {
                        self.parentModel.showMessageError(res);
                    }
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
             * findPathNameByWkpIdSelected
             */
            public findPathNameByWkpIdSelected(): string {
                let self = this;
                let index: number = 3;
                let wkpFullName: string = "";
                
                let isCreateWkpChild: boolean = self.parentModel.creationType == CreationType.CREATE_TO_CHILD;
                
                // check null empty or it is workplace parent 
                if (nts.uk.text.isNullOrEmpty(self.selectedHierarchyCd)
                        || (!isCreateWkpChild && self.selectedHierarchyCd.length <= 3)) {
                    return null;
                }
                
                let maxLength: number = self.selectedHierarchyCd.length;
                
                // find path name parent workplace
                while ((!isCreateWkpChild && index < maxLength) || (isCreateWkpChild && index <= maxLength)) {
                    let parentHierarchyCd: string = self.selectedHierarchyCd.substr(0, index);
                    wkpFullName += " " + self.mapHierarchy[parentHierarchyCd];
                    index += 3;
                }
                return wkpFullName.trim();
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
             * getSelectedHierarchyCd
             */
            public findSelectedHierarchyCd(wpkId: string): string {
                let self = this;
                let hierarchyCode: string = "";
                for (let item of self.treeArray()) {
                    if (item.workplaceId == wpkId) {
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
                
                //  calculate max with column text 
                let maxSizeNameCol: number = Math.max(self.getMaxSizeOfTextList(self.lstWorkplace()), 250);
                
                // calculate height tree
                self.calHeightTree(maxSizeNameCol);
                
                // set properties tree columns
                self.treeColumns = [
                    { headerText: "", key: 'workplaceId', dataType: "string", hidden: true},
                    { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: maxSizeNameCol,
                        dataType: "string" }
                ];
                
                // re-binding
                let $treeGrid: any = $("#single-tree-grid");
                ko.cleanNode($treeGrid[0]);
                ko.applyBindings(self, $treeGrid[0]);
            }

            /**
             * calHeightTree
             */
            private calHeightTree(widthColText: number) {
                let self = this;
                let heightRow = 24, heightScrollX = 0, maxRows = 20, heightHeader = 24;

                // check has scroll-x
                if (widthColText > self.treeStyle.width) {
                    heightScrollX = 18;
                }

                // calculate height tree
                self.treeStyle.height = heightRow * maxRows + heightHeader + heightScrollX;
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
             * convertMapHierarchy
             */
            private convertMapHierarchy(): any {
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
                    if (nts.uk.text.isNullOrEmpty(newHistoryId)) {
                        self.parentModel.initData(null);
                        return;
                    }
                    // load workplace info by historyId
                    self.loadWkpHistoryInfo(self.parentModel.treeWorkplace().selectedWpkId(), newHistoryId);
                });
            }

            /**
             * Reset data
             */
            public reset() {
                let self = this;
                self.lstWpkHistory([]);
                self.selectedHistoryId(null);
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
                        console.log(self.lstWpkHistory());
                        
                    }
                });
            }

            /**
             * updateWkpHistoryDialog
             */
            public updateWkpHistoryDialog() {
                let self = this;
                
                // toJsObject
                let objectTransfer: any = {};
                objectTransfer.wkpId = self.parentModel.treeWorkplace().selectedWpkId();
                objectTransfer.historyId = self.selectedHistoryId();
                objectTransfer.startDate = self.getSelectedHistoryByHistId().startDate;

                // share data for dialog
                nts.uk.ui.windows.setShared("WokplaceHistoryInfor", objectTransfer);
                
                // open dialog
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
                
                // show message confirm
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                    
                    // to JsObject
                    let command: any = {};
                    command.workplaceId = self.parentModel.treeWorkplace().selectedWpkId();
                    command.historyId = self.selectedHistoryId();

                    // remove workplace history
                    service.removeWorkplaceHistory(command).done(() => {
                        
                        // find workplace history
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
                    
                    // map data
                    let lstWpkHistory = _.map(data.workplaceHistory, (item: any) => {
                        return {
                            workplaceId: data.workplaceId, historyId: item.historyId,
                            startDate: item.startDate, endDate: item.endDate
                        };
                    });
                    //update list hist
                    self.lstWpkHistory(lstWpkHistory);
                    
                    // select item 
                    self.setSelectionHistSuitable();

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
                let newHist: IHistory = {
                    historyId: '',
                    startDate: self.parentModel.strDWorkplace(),
                    endDate: nts.uk.resource.getText("CMM011_27")
                };
                self.lstWpkHistory([newHist]);
                self.selectedHistoryId(newHist.historyId);
            }

            /**
             * After remove history, choose item in time range of workplace config history.
             */
            public setSelectionHistSuitable() {
                let self = this;
                
                let result: Array<IHistory> = self.lstWpkHistory()
                    .filter(item => item.startDate <= self.parentModel.strDWorkplace()
                        && item.endDate >= self.parentModel.strDWorkplace());
                
                if (result.length <= 0) {
                    self.selectFirst();
                } else {
                    // sort start date ASC
                    result.sort(function(a: IHistory, b: IHistory) {
                        if (a.startDate < b.startDate) {
                            return -1;
                        } else if (a.startDate > b.startDate) {
                            return 1;
                        }
                        return 0;
                    });
                    self.selectedHistoryId(self.lstWpkHistory()[0].historyId);
                }
                
            }
            
            /**
             * loadWkpHistoryInfo
             */
            private loadWkpHistoryInfo(wkpId: string, historyId: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                nts.uk.ui.block.grayout();
                
                service.getWkpInfoByHistId(wkpId, historyId).done(function(data: any) {
                    nts.uk.ui.block.clear();
                    
                    // set data
                    self.parentModel.initData(data);
                    
                    // set focus
                    $('#wkpName').focus();
                    
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.parentModel.showMessageError(res);
                });
                return dfd.promise();
            }
        }
        
        /**
         * TreeStyle
         */
        interface TreeStyle {
            width: number;
            height: number;
        }
    }
}