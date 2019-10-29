module kcp.share.tree {
    export interface UnitModel {
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        workplaceId: string;
//        id: string;
        //end
        code: string;
        name: string;
        nodeText?: string;
        level: number;
        hierarchyCode: string;
        isAlreadySetting?: boolean;
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        childs: Array<UnitModel>;
//        children: Array<UnitModel>;
        //end
    }

    export interface UnitAlreadySettingModel {
        /**
         * affiliation id ( department or workplace)
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        workplaceId: string;
//        id: string;
        //end
        /**
         * State setting:
         *  true: saved setting.
         *  false: parent setting, child does not setting.
         *  undefined || null: both parent and child do not setting.
         */
        isAlreadySetting: boolean;
    }

    export interface RowSelection {
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        workplaceId: string;
        workplaceCode: string;
//        id: string;
//        code: string;
        //end
    }

    export interface TreeComponentOption {
        /**
         * is Show Already setting.
         */
        isShowAlreadySet: boolean;

        /**
         * is Multi use (複数使用区分). Setting use multiple components?
         */
        isMultipleUse: boolean;

        /**
         * is Multi select (選択モード). Setting multiple selection in grid.
         */
        isMultiSelect: boolean;

        /**
         * Tree type, if not set, default is work place.
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        treeType?: TreeType;
//        startMode?: StartMode;
        //end
        /**
         * selected value.
         * May be string or Array<string>
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        selectedWorkplaceId: KnockoutObservable<any>;
//        selectedId: KnockoutObservable<any>;
        //end
        /**
         * Base date.
         */
        baseDate: KnockoutObservable<Date>;

        /**
         * Select mode
         */
        selectType: SelectionType;

        /**
         * isShowSelectButton
         * Show/hide button select all and selected sub parent
         */
        isShowSelectButton: boolean;

        /**
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;

        /**
         * Default padding of KCPs
         */
        hasPadding?: boolean;

        /**
         * Already setting list code. structure: {id: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitAlreadySettingModel>;

        /**
         * Limit display row
         */
        maxRows?: number;

        /**
         * set tabIndex
         */
        tabindex?: number;

        /**
         * system type
         */
        systemType: SystemType;

        // 参照範囲の絞
        restrictionOfReferenceRange?: boolean;

        /**
         * Check is show no select row in grid list.
         */
        isShowNoSelectRow?: boolean;

        /**
         * Show all levels of department workplace on start
         */
        isFullView?: boolean;
    }

    /**
     * System type ~ システム区分
     *
     */
    export class SystemType {

        // 個人情報
        static PERSONAL_INFORMATION: number = 1;

        // 就業
        static EMPLOYMENT: number = 2;

        // 給与
        static SALARY: number = 3;

        // 人事
        static HUMAN_RESOURCES: number = 4;

        // 管理者
        static ADMINISTRATOR: number = 5;
    }

    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
    export class TreeType {
        static WORK_PLACE = 1;
    }
//    export class StartMode {
//        static WORKPLACE = 0;
//
//        // 部門対応 #106784
//        static DEPARTMENT = 1;
//    }
    //end
    interface TreeStyle {
        width: number;
        height: number;
    }

    export class SelectionType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export class TreeComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        backupItemList: KnockoutObservableArray<UnitModel>;
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        selectedWorkplaceIds: KnockoutObservable<any>;
//        selectedIds: KnockoutObservable<any>;
        //end
        isShowSelectButton: boolean;
        treeComponentColumn: Array<any>;
        isMultipleUse: boolean;
        isMultiSelect: boolean;
        isDialog: boolean;
        hasPadding: boolean;
        hasBaseDate: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        levelList: Array<any>;
        levelSelected: KnockoutObservable<number>;
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        listWorkplaceId: Array<string>;
//        listId: Array<string>;
        //end
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        $input: JQuery;
        data: TreeComponentOption;
        maxRows: number;
        systemType: SystemType;
        isFullView: KnockoutObservable<boolean>;
        isShowNoSelectRow: boolean;

        tabindex: number;

        treeStyle: TreeStyle;
        restrictionOfReferenceRange: boolean;
        searchBoxId: string;

        // 部門対応 #106784
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//        startMode: StartMode;
//
//        $inputId: KnockoutObservable<string>;
        //end
        constructor() {
            let self = this;
            self.searchBoxId = nts.uk.util.randomId();
            self.itemList = ko.observableArray([]);
            self.backupItemList = ko.observableArray([]);
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            self.listWorkplaceId = [];
//            self.listId = [];
            //end
            self.hasBaseDate = ko.observable(false);
            self.alreadySettingList = ko.observableArray([]);
            self.levelList = [
                { level: 1, name: '1' },
                { level: 2, name: '2' },
                { level: 3, name: '3' },
                { level: 4, name: '4' },
                { level: 5, name: '5' },
                { level: 6, name: '6' },
                { level: 7, name: '7' },
                { level: 8, name: '8' },
                { level: 9, name: '9' },
                { level: 10, name: '10' }
            ];
            self.levelSelected = ko.observable(10);
            self.isMultipleUse = false;
            self.isMultiSelect = false;
            self.isFullView = ko.observable(true);

            self.treeStyle = {
                width: 412,
                height: 0
            };

            // 部門対応 #106784
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            self.startMode = StartMode.WORKPLACE;
//
//            self.$inputId = ko.observable("");
            //end
        }

        public init($input: JQuery, data: TreeComponentOption): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            self.data = data;
            self.isShowNoSelectRow = _.isNil(data.isShowNoSelectRow) ? false : data.isShowNoSelectRow;
            self.$input = $input;
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            self.$inputId($input[0].id);
            //end
            // set parameter
            self.isFullView(_.isNil(data.isFullView) ? false : data.isFullView); // default = false
            if (data.isMultipleUse) {
                self.isMultipleUse = data.isMultipleUse;
            }
            if (data.isMultiSelect) {
                self.isMultiSelect = data.isMultiSelect;
            }
            self.hasBaseDate(!self.isMultipleUse);
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            self.selectedWorkplaceIds = data.selectedWorkplaceId;
//            self.selectedIds = data.selectedId;
            //end
            self.isShowSelectButton = data.isShowSelectButton && data.isMultiSelect;
            self.isDialog = data.isDialog;
            self.hasPadding = _.isNil(data.hasPadding) ? true : data.hasPadding; // default = true
            self.baseDate = data.baseDate;
            self.restrictionOfReferenceRange = data.restrictionOfReferenceRange != undefined ? data.restrictionOfReferenceRange : true;
            self.systemType =  data.systemType;

            // 部門対応 #106784
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            self.startMode = _.isNil(data.startMode) ? StartMode.WORKPLACE : data.startMode; //default = workplace
            //end
            if (data.alreadySettingList) {
                self.alreadySettingList = data.alreadySettingList;
            }
            if (!data.maxRows) {
                data.maxRows = 12;
                self.maxRows = 12;
            } else {
                self.maxRows = data.maxRows;
            }
            self.tabindex = data.tabindex ? data.tabindex : 1;

            // subscribe change selected level
            self.levelSelected.subscribe(function(level) {
                self.filterData();
            });

            // subscribe change item list origin
            self.backupItemList.subscribe((newData) => {
                // data is empty, set selected work place id empty
                if (!newData || newData.length <= 0) {
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    self.selectedWorkplaceIds(self.isMultiSelect ? [] : '');
//                    self.selectedIds(self.isMultiSelect ? [] : '');
                    //end
                }
                self.createGlobalVarDataList();
            });

            // set current system date if baseDate is invalid
            const baseDate = self.$input.find('#work-place-base-date');
            baseDate.ntsError('check');
            if (baseDate.ntsError('hasError')) {
                self.baseDate(new Date());
            }

            // Find data.
            const param = <service.WorkplaceParam>{};
            param.baseDate = self.baseDate();
            param.systemType = self.systemType;
            param.restrictionOfReferenceRange = self.restrictionOfReferenceRange;
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            param.startMode = self.startMode;
            service.findWorkplaceTree(param).done(function(res: Array<UnitModel>) {
//            service.findDepWkpTree(param).done(function(res1: Array<any>) {
            //end
                if (res && res.length > 0) {
                    // Map already setting attr to data list.
                    self.addAlreadySettingAttr(res, self.alreadySettingList());

                    if (data.isShowAlreadySet) {
                        // subscribe when alreadySettingList update => reload component.
                        self.alreadySettingList.subscribe((newAlreadySettings: any) => {
                            self.addAlreadySettingAttr(self.backupItemList(), newAlreadySettings);
                            // filter data, not change selected department or workplace id
                            let subItemList = self.filterByLevel(self.backupItemList(), self.levelSelected(), new Array<UnitModel>());
                            self.itemList(subItemList);
                            self.createGlobalVarDataList();
                        });
                    }

                    // Init component.
                    self.itemList(res);
                    self.initNoSelectRow();
                    self.backupItemList(self.itemList());
                }
                // Set default value when initial component.
                self.initSelectedValue();

                self.loadTreeGrid().done(function() {
                    // Special command -> remove unuse.
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    $input.find('#multiple-tree-grid_tooltips_ruler').remove();
//                    $input.find('#multiple-tree-grid-' + self.$inputId() + '_tooltips_ruler').remove();
                    //end
                    dfd.resolve();
                });
                
                $(document).delegate('#' + self.getComIdSearchBox(), "igtreegridrowsrendered", function(evt: any) {
                    self.addIconToAlreadyCol();
                });
            });

            // defined function focus
            $.fn.focusTreeGridComponent = function() {
                if (self.hasBaseDate()) {
                    $('.base-date-editor').first().focus();
                } else {
                    $("#combo-box-tree-component").focus();
                }
            }

            // define function get row selected
            $.fn.getRowSelected = function(): Array<any> {
                let listRowSelected: Array<RowSelection> = [];
                self.findSelectionRowData(self.backupItemList(), listRowSelected);
                return listRowSelected;
            }

            return dfd.promise();
        }

        /**
         * Add No select row to list
         */
        private initNoSelectRow() {
            let self = this;
            let noSelectItem = {
                code: '',
                nodeText: nts.uk.resource.getText('KCP001_5'),
                name: nts.uk.resource.getText('KCP001_5'),
                isAlreadySetting: false,
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                workplaceId: '',
//                id: '',
                //end
                level: 1,
                hierarchyCode: '',
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                childs: []
//                children: []
                //end
            };

            // Remove No select row.
            self.itemList.remove(noSelectItem);

            // Check is show no select row.
            if (self.isShowNoSelectRow && !self.isMultiSelect) {
                self.itemList.unshift(noSelectItem);
            }
        }

        /**
         * Add columns to tree grid list.
         */
        private addColToGrid(data: TreeComponentOption, dataList: Array<UnitModel>) {
            let self = this;
            // Convert tree to array.
            //let maxSizeNameCol = Math.max(self.getMaxSizeOfTextList(self.convertTreeToArray(dataList)), 250);

            // calculate height tree
            self.calHeightTree(300, data);

            self.treeComponentColumn = [
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                { headerText: "", key: 'workplaceId', dataType: "string", hidden: true },
//                { headerText: "", key: 'id', dataType: "string", hidden: true },
            //end
                {
                    headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: 325, dataType: "string",
                    template: "<td class='tree-component-node-text-col'>${nodeText}</td>"
                }
            ];
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.treeComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP004_6'), key: 'isAlreadySetting', width: 70, dataType: 'string',
                    formatter: function(isAlreadySetting: string) {
                        if (isAlreadySetting == 'true') {
                            return '<div style="text-align: center;max-height: 18px;"><i class="icon icon icon-78"></i></div>';
                        }
                        if (isAlreadySetting == 'false') {
                            return '<div style="text-align: center;max-height: 18px;"><i class="icon icon icon-84"></i></div>';
                        }
                        return '';
                    }
                });
            }
        }

        /**
         * calHeightTree
         */
        private calHeightTree(widthColText: number, data) {
            let self = this;
            let heightRow = 24, heightScrollX = 0;

            // check has scroll-x
            if (widthColText > self.treeStyle.width) {
                heightScrollX = 18;
            }

            // calculate height tree
            self.treeStyle.height = heightRow * (self.maxRows + 1) + heightScrollX;
            if (self.isFullView()) {
                self.treeStyle.width = widthColText + data.isShowAlreadySet ? 100 : 30;

                // if width tree is small than 412 -> set to 412.
                self.treeStyle.width = self.treeStyle.width < 412 ? 412 : self.treeStyle.width;
            } else {
                self.treeStyle.width = 412;
            }
        }

        /**
         * Convert tree data to array.
         */
        private convertTreeToArray(dataList: Array<UnitModel>): Array<any> {
            let self = this;
            let res = [];
            _.forEach(dataList, function(item) {
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                if (item.childs && item.childs.length > 0) {
                    res = res.concat(self.convertTreeToArray(item.childs));
//                if (item.children && item.children.length > 0) {
//                    res = res.concat(self.convertTreeToArray(item.children));
                //end
                }
                res.push({ name: item.nodeText, level: item.level });
            })
            return res;
        }
        /**
         * Calculate real size of text.
         */
        private getMaxSizeOfTextList(textArray: Array<any>): number {
            var max = 0;
            var paddingPerLevel = 16;
            var defaultFontSize = 14;
            var defaultFontFamily = ['DroidSansMono', 'Meiryo'];
            _.forEach(textArray, function(item) {
                var o = $('<div id="test">' + item.name + '</div>')
                    .css({
                        'position': 'absolute', 'float': 'left', 'white-space': 'nowrap', 'visibility': 'hidden',
                        'font-size': defaultFontSize, 'font-family': defaultFontFamily
                    })
                    .appendTo($('body'))
                var w = o.width() + item.level * paddingPerLevel + 30;
                if (w > max) {
                    max = w;
                }
                o.remove();
            });
            return max;
        }

        /**
         * Initial select mode
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        private initSelectedValue() {
            let self = this;
            if (_.isEmpty(self.itemList())) {
                self.selectedWorkplaceIds(self.data.isMultiSelect ? [] : '');
                return;
            }
            switch (self.data.selectType) {
                case SelectionType.SELECT_BY_SELECTED_CODE:
                    if(_.isEmpty(self.selectedWorkplaceIds())) {
                        self.selectedWorkplaceIds(self.data.isMultiSelect ? [] : '');
                        break;
                    }
                    if (self.isMultiSelect) {
                        self.selectedWorkplaceIds(self.data.selectedWorkplaceId());
                    } else {
                        const selectedCode = _.isArray(self.data.selectedWorkplaceId()) ?
                            self.data.selectedWorkplaceId()[0] : self.data.selectedWorkplaceId();
                        self.selectedWorkplaceIds(selectedCode);
                    }
                    break;
                case SelectionType.SELECT_ALL:
                    if (self.isMultiSelect) {
                        self.selectAll();
                    }
                    break;
                case SelectionType.SELECT_FIRST_ITEM:
                    self.selectedWorkplaceIds(self.selectData(self.data, self.itemList()[0]));
                    break;
                case SelectionType.NO_SELECT:
                    self.selectedWorkplaceIds(self.data.isMultiSelect ? [] : '');
                    break;
                default:
                    self.selectedWorkplaceIds(self.data.isMultiSelect ? [] : '');
                    break
            }
        }
//        private initSelectedValue() {
//            let self = this;
//            if (_.isEmpty(self.itemList())) {
//                self.selectedIds(self.data.isMultiSelect ? [] : '');
//                return;
//            }
//            switch (self.data.selectType) {
//                case SelectionType.SELECT_BY_SELECTED_CODE:
//                    if(_.isEmpty(self.selectedIds())) {
//                        self.selectedIds(self.data.isMultiSelect ? [] : '');
//                        break;
//                    }
//
//                    if (self.isMultiSelect) {
//                        self.selectedIds(self.data.selectedId());
//                    } else {
//                        const selectedCode = _.isArray(self.data.selectedId()) ?
//                            self.data.selectedId()[0] : self.data.selectedId();
//                        self.selectedIds(selectedCode);
//                    }
//                    break;
//                case SelectionType.SELECT_ALL:
//                    if (self.isMultiSelect) {
//                        self.selectAll();
//                    }
//                    break;
//                case SelectionType.SELECT_FIRST_ITEM:
//                    self.selectedIds(self.selectData(self.data, self.itemList()[0]));
//                    break;
//                case SelectionType.NO_SELECT:
//                    self.selectedIds(self.data.isMultiSelect ? [] : '');
//                    break;
//                default:
//                    self.selectedIds(self.data.isMultiSelect ? [] : '');
//                    break
//            }
//        }
          //end
        /**
         * add icon by already setting
         */
        private addIconToAlreadyCol() {
            var icon84Link = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon84.png').serialize();
            $('.icon-84').attr('style', "background: url('" + icon84Link
                + "');width: 18px;height: 18px;background-size: 20px 20px;")

            var icon78Link = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon78.png').serialize();
            $('.icon-78').attr('style', "background: url('" + icon78Link
                + "');width: 18px;height: 18px;background-size: 20px 20px;")
        }

        /**
         * Add Already Setting Attr into data list.
         */
        private addAlreadySettingAttr(dataList: Array<UnitModel>, alreadySettingList: Array<UnitAlreadySettingModel>) {
            let mapAlreadySetting = _.reduce(alreadySettingList, function(hash, value) {
                let key = value['workplaceId'];
                hash[key] = value['isAlreadySetting'] == false ? null : value['isAlreadySetting'];
                return hash;
            }, {});
            this.updateTreeData(dataList, mapAlreadySetting);
        }

        /**
         * Update setting type for dataList
         */
        private updateTreeData(dataList: Array<UnitModel>, mapAlreadySetting: any, isAlreadySettingParent?: boolean,
                               hierarchyCodeParent?: string) {
            let self = this;
            for (let unitModel of dataList) {

                // add id work place
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                self.listWorkplaceId.push(unitModel.workplaceId);
//                self.listId.push(unitModel.id);
                //end
                // set level
                unitModel.level = unitModel.hierarchyCode.length / 3;

                // set node text
                unitModel.nodeText = unitModel.code + ' ' + unitModel.name;

                // set already setting 
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                let isAlreadySetting = mapAlreadySetting[unitModel.workplaceId];
//                let isAlreadySetting = mapAlreadySetting[unitModel.id];
                //end
                unitModel.isAlreadySetting = isAlreadySetting;

                let hierarchyCode: string = null;
                // if it is saved already setting, will be save hierarchyCode that it is parent hierarchyCode.
                if (isAlreadySetting == true) {
                    hierarchyCode = unitModel.hierarchyCode;
                }

                // check work place child
                if (hierarchyCodeParent && unitModel.hierarchyCode.startsWith(hierarchyCodeParent)) {

                    // if is work place child and it has not setting, it will set icon flag.
                    if (isAlreadySettingParent == true && typeof unitModel.isAlreadySetting != "boolean") {
                        unitModel.isAlreadySetting = false;
                    }
                    // if is not work place child and it has not setting, it will not set icon.
                    if (typeof isAlreadySettingParent != "boolean" && unitModel.isAlreadySetting == false) {
                        unitModel.isAlreadySetting = isAlreadySettingParent;
                    }
                }
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                if (unitModel.childs.length > 0) {
                    this.updateTreeData(unitModel.childs, mapAlreadySetting,
//                if (unitModel.children.length > 0) {
//                    this.updateTreeData(unitModel.children, mapAlreadySetting,
                //end
                        isAlreadySetting ? isAlreadySetting : isAlreadySettingParent,
                        hierarchyCode ? hierarchyCode : hierarchyCodeParent);
                }
            }
        }

        /**
         * Filter data by level
         */
        private filterData() {
            let self = this;
            if (self.backupItemList().length > 0) {
                // clear list selected work place id
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                self.listWorkplaceId = [];
//                self.listId = [];
                //end
                // find sub list unit model by level
                let subItemList = self.filterByLevel(self.backupItemList(), self.levelSelected(), new Array<UnitModel>());
                self.itemList(subItemList);
                self.initSelectedValue();
                
                self.reloadNtsTreeGrid();
                
                self.createGlobalVarDataList();
            }
        }

        private loadTreeGrid(): JQueryPromise<void> {
            let dfd = $.Deferred<void>();
            let self = this;
            self.addColToGrid(self.data, self.itemList());

            const initComponent = () => {
                self.$input.html(TREE_COMPONENT_HTML);
                ko.cleanNode(self.$input[0]);
                ko.applyBindings(self, self.$input[0]);

                
                let found;
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                const flat = function(wk) {
                    return [wk.workplaceId, _.flatMap(wk.childs, flat)];
                };
                const selectableList = _.flatMapDeep(self.itemList(), flat);
                if (self.isMultiSelect) {
                    found = _.filter(self.selectedWorkplaceIds(), id => _.includes(selectableList, id));
                } else {
                    found = _.find(selectableList, id => id == self.selectedWorkplaceIds());
                }
                self.selectedWorkplaceIds(found);
//                const flat = function(wk) {
//                    return [wk.id, _.flatMap(wk.children, flat)];
//                };
//                const selectableList = _.flatMapDeep(self.itemList(), flat);
//                if (self.isMultiSelect) {
//                    found = _.filter(self.selectedIds(), id => _.includes(selectableList, id));
//                } else {
//                    found = _.find(selectableList, id => id == self.selectedIds());
//                }
//                self.selectedIds(found);
                //end
                let options = {
                    width: self.isShowSelectButton ? '474px' : self.treeStyle.width,
                    dataSource: self.itemList(),
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    selectedValues: self.selectedWorkplaceIds(),
                    optionsValue: 'workplaceId',
                    optionsChild: 'childs',
//                    selectedValues: self.selectedIds(),
//                    optionsValue: 'id',
//                    optionsChild: 'children',
                    //end
                    optionsText: 'nodeText',
                    multiple: self.isMultiSelect,
                    virtualization: true,
                    rows: self.maxRows,
                    virtualizationMode: 'continuous',
                    extColumns: self.treeComponentColumn,
                    enable: true,
                    showCheckBox: self.isMultiSelect
                };
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                const searchBoxOptions = {
                    childField: 'childs',
                    targetKey: 'workplaceId',
                    comId: self.getComIdSearchBox(),
                    items: self.itemList(),
                    selected: self.selectedWorkplaceIds(),
                    selectedKey: 'workplaceId',
                    fields: ['nodeText', 'code'],
                    mode: 'igTree'
                };
//                const searchBoxOptions = {
//                    childField: 'children',
//                    targetKey: 'id',
//                    comId: self.getComIdSearchBox(),
//                    items: self.itemList(),
//                    selected: self.selectedIds(),
//                    selectedKey: 'id',
//                    fields: ['nodeText', 'code'],
//                    mode: 'igTree'
//                };
                //end
                // fix bug select incorrect element caused by auto generated element
                const generatedElement = $('#' + self.getComIdSearchBox() + '.cf.row-limited.ui-iggrid-table.ui-widget-content');
                if (!_.isEmpty(generatedElement)) {
                    generatedElement.remove();
                }
                
                $('#' + self.getComIdSearchBox()).ntsTreeGrid(options);
                $('#' + self.searchBoxId).ntsSearchBox(searchBoxOptions);

                // init event selected changed
                self.initEvent();

                // set selected workplaced
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                self.selectedWorkplaceIds.valueHasMutated();
//                self.selectedIds.valueHasMutated();
                //end
                // fix bug scroll on tree
                _.defer(() => {
                    $('#' + self.getComIdSearchBox()).igTreeGrid('dataBind');
                });

                // defined function get data list.
                self.createGlobalVarDataList();
                $.fn.getDataList = function(): Array<kcp.share.list.UnitModel> {
                    return window['dataList' + this.attr('id').replace(/-/gi, '')];
                }

                // Create method to full view.
                $.fn.fullView = function() {
                    self.isFullView(true);
                    self.filterData();
                }

                $.fn.scrollView = function() {
                    self.isFullView(false);
                    self.filterData();
                }
                
                dfd.resolve();
            };

            if (_.isNil(ko.dataFor(document.body))) {
                nts.uk.ui.viewModelApplied.add(initComponent);
            } else {
                initComponent();
            }

            return dfd.promise();
        }

        // set up on selected code changed event
        private initEvent(): void {
            let self = this;

            // Reload NtsTreeGrid when itemList changed.
            self.itemList.subscribe(vl => {
                self.reloadNtsTreeGrid();
            });

            $(document).delegate('#' + self.getComIdSearchBox(), "igtreegridselectionrowselectionchanged", (evt, ui) => {
                const selecteds = _.map(ui.selectedRows, o => o.id);
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)    
            if (self.isMultiSelect) {
                    self.selectedWorkplaceIds(selecteds);
                } else {
                    self.selectedWorkplaceIds(selecteds[0]);
                }
//                if (self.isMultiSelect) {
//                    self.selectedIds(selecteds);
//                } else {
//                    self.selectedIds(selecteds[0]);
//                }
            //end
            });
            
            $(document).delegate('#' + self.getComIdSearchBox(), "ntstreeselectionchanged", (evt, selectedId) => {
                // multiple-case: selectedId is an array
                // single-case: selectedId is a string
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                self.selectedWorkplaceIds(selectedId);
//                self.selectedIds(selectedId);
                //end
            });

            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            self.selectedWorkplaceIds.subscribe(ids => {
//            self.selectedIds.subscribe(ids => {
            //end
                const grid = $('#' + self.getComIdSearchBox());
                if (_.isNil(grid.data("igGrid"))) {
                    return;
                }
                grid.ntsTreeGrid('setSelected',
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    self.isMultiSelect ? [].slice.call(self.selectedWorkplaceIds()) : self.selectedWorkplaceIds());
//                    self.isMultiSelect ? [].slice.call(self.selectedIds()) : self.selectedIds());
                //end
            });
        }

        private createGlobalVarDataList() {
            var self = this;
            $('#script-for-' + self.$input.attr('id')).remove();
            var s = document.createElement("script");
            s.type = "text/javascript";
            s.innerHTML = 'var dataList' + self.$input.attr('id').replace(/-/gi, '') + ' = '
                + JSON.stringify(self.backupItemList());
            s.id = 'script-for-' + self.$input.attr('id');
            $("head").append(s);
        }

        private reloadNtsTreeGrid(): void {
            let self = this;
            const treeGrid = $('#' + self.getComIdSearchBox());
            const searchBox = $('#' + self.searchBoxId);
            if (!_.isEmpty(treeGrid) && !_.isEmpty(searchBox)) {
                treeGrid.ntsTreeGrid("setDataSource", self.itemList());
                searchBox.ntsSearchBox("setDataSource", self.itemList());
            }
        }

        /**
         * Find list work place by base date
         */
        public reload() {
            let self = this;

            // validate base date
            const baseDate = self.$input.find('#work-place-base-date');
            baseDate.ntsError('check');
            if (baseDate.ntsError('hasError')) {
                return;
            }
            const param = <service.WorkplaceParam>{};
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            param.startMode = self.startMode;
            //end
            param.baseDate = self.baseDate();
            param.systemType = self.systemType;
            param.restrictionOfReferenceRange = self.restrictionOfReferenceRange;
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            service.findWorkplaceTree(param).done(function(res: Array<UnitModel>) {
//            service.findDepWkpTree(param).done(function(res: Array<UnitModel>) {
            //end
                if (!res || res.length <= 0) {
                    self.itemList([]);
                    self.backupItemList([]);
                    return;
                }
                if (self.alreadySettingList) {
                    self.addAlreadySettingAttr(res, self.alreadySettingList());
                }
                self.itemList(res);
                self.initNoSelectRow();
                self.backupItemList(self.itemList());

                // Filter data
                self.filterData();
            });
        }

        /**
         * Select all
         */
        private selectAll() {
            let self = this;
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            this.selectedWorkplaceIds(this.listWorkplaceId);
//            this.selectedIds(this.listId);
            //end
        }

        /**
         * Select all children
         */
        private selectSubParent() {
            let self = this;
            let workplaceIdSet = new Set();

            let listModel = self.findUnitModelByListWorkplaceId();
            self.findListSubWorkplaceId(listModel, workplaceIdSet);
            if (workplaceIdSet.size > 0) {
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                self.selectedWorkplaceIds(self.convertSetToArray(workplaceIdSet));
//                self.selectedIds(self.convertSetToArray(workplaceIdSet));
                //end
            }
        }

        private convertSetToArray(workplaceIdSet: Set<string>) {
            let newArray = [];
            workplaceIdSet.forEach(element => {
                newArray.push(element);
            });
            return newArray;
        }
        /**
         * Find UnitModel By ListWorkplaceId
         */
        private findUnitModelByListWorkplaceId(dataList?: Array<UnitModel>): Array<UnitModel> {
            let self = this;
            let listModel: Array<UnitModel> = [];

            // get selected work place
            let listWorkplaceId = self.getSelectedWorkplace();

            for (let workplaceId of listWorkplaceId) {
                listModel = self.findUnitModelByWorkplaceId(dataList ? self.backupItemList() : self.itemList(),
                    workplaceId, listModel);
            }
            return listModel;
        }

        /**
         * Find list sub id of parent
         */
        private findListSubWorkplaceId(dataList: Array<UnitModel>, workplaceIdSet: Set<string>) {
            let self = this;
            for (let alreadySetting of dataList) {
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                workplaceIdSet.add(alreadySetting.workplaceId);
                if (alreadySetting.childs && alreadySetting.childs.length > 0) {
                    this.findListSubWorkplaceId(alreadySetting.childs, workplaceIdSet);
//                workplaceIdSet.add(alreadySetting.id);
//                if (alreadySetting.children && alreadySetting.children.length > 0) {
//                    this.findListSubWorkplaceId(alreadySetting.children, workplaceIdSet);
                //end
                }
            }
        }

        /**
         * Select data for multiple or not
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        private selectData(option: TreeComponentOption, data: UnitModel): any {
            if (this.isMultiSelect) {
                return [data.workplaceId];
            }
            return data.workplaceId;
        }
//        private selectData(option: TreeComponentOption, data: UnitModel): any {
//            if (this.isMultiSelect) {
//                return [data.id];
//            }
//            return data.id;
//        }
        //end
        /**
         * Get selected work place id
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        private getSelectedWorkplace(): any {
            if (this.isMultiSelect) {
                return this.selectedWorkplaceIds() ? this.selectedWorkplaceIds() : [];
            }
            return [this.selectedWorkplaceIds()];
        }
//        private getSelectedWorkplace(): any {
//            if (this.isMultiSelect) {
//                return this.selectedIds() ? this.selectedIds() : [];
//            }
//            return [this.selectedIds()];
//        }
        //end
        /**
         * Find UnitModel by id
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        private findUnitModelByWorkplaceId(dataList: Array<UnitModel>, workplaceId: string,
                                           listModel: Array<UnitModel>): Array<UnitModel> {
            let self = this;
            for (let item of dataList) {
                if (item.workplaceId == workplaceId) {
                    let modelString = JSON.stringify(listModel);
                    // Check item existed
                    if (modelString.indexOf(item.workplaceId) < 0) {
                        listModel.push(item);
                    }
                }
                if (item.childs.length > 0) {
                    this.findUnitModelByWorkplaceId(item.childs, workplaceId, listModel);
                }
            }
            return listModel;
        }
//       private findUnitModelByWorkplaceId(dataList: Array<UnitModel>, workplaceId: string,
//                                           listModel: Array<UnitModel>): Array<UnitModel> {
//            let self = this;
//            for (let item of dataList) {
//                if (item.id == workplaceId) {
//                    let modelString = JSON.stringify(listModel);
//                    // Check item existed
//                    if (modelString.indexOf(item.id) < 0) {
//                        listModel.push(item);
//                    }
//                }
//                if (item.children.length > 0) {
//                    this.findUnitModelByWorkplaceId(item.children, workplaceId, listModel);
//                }
//            }
//            return listModel;
//        }
        //end
        /**
         * Find selected row data
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        private findSelectionRowData(dataList: Array<UnitModel>, listRowData: Array<RowSelection>) {
            let self = this;
            let selectedWorkplaces = self.getSelectedWorkplace();
            for (let unitModel of dataList) {
                if (_.some(selectedWorkplaces, id => id == unitModel.workplaceId)) {
                    listRowData.push({
                        workplaceId: unitModel.workplaceId,
                        workplaceCode: unitModel.code
                    });
                }
                if (unitModel.childs.length > 0) {
                    this.findSelectionRowData(unitModel.childs, listRowData);
                }
            }
        }
//        private findSelectionRowData(dataList: Array<UnitModel>, listRowData: Array<RowSelection>) {
//            let self = this;
//            let selectedWorkplaces = self.getSelectedWorkplace();
//            for (let unitModel of dataList) {
//                if (_.some(selectedWorkplaces, id => id == unitModel.id)) {
//                    listRowData.push({
//                        id: unitModel.id,
//                        code: unitModel.code
//                    });
//                }
//                if (unitModel.children.length > 0) {
//                    this.findSelectionRowData(unitModel.children, listRowData);
//                }
//            }
//        }
        //end
        private getComIdSearchBox(): string {
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            if (this.isMultiSelect) {
                return 'multiple-tree-grid';
            }
            return 'single-tree-grid';
//            if (this.isMultiSelect) {
//                return 'multiple-tree-grid-' + this.$inputId();
//            }
//            return 'single-tree-grid-' + this.$inputId();
            //end
        }

        private filterByLevel(dataList: Array<UnitModel>, level: number, listModel: Array<UnitModel>): Array<UnitModel> {
            let self = this;
            for (let item of dataList) {
                let newItem: any = {};
                if (item.level <= level) {
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    self.listWorkplaceId.push(item.workplaceId);
//                    self.listId.push(item.id);
                    //end
                    newItem = JSON.parse(JSON.stringify(item));
                    listModel.push(newItem);
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    if (level == 1) {
                        newItem.childs = [];
                    } else if (item.childs && item.childs.length > 0) {
                       let tmpModels = this.filterByLevel(newItem.childs, level, new Array<UnitModel>());
                       newItem.childs = tmpModels;
                    }
//                    if (level == 1) {
//                        newItem.children = [];
//                    } else if (item.children && item.children.length > 0) {
//                        let tmpModels = this.filterByLevel(newItem.children, level, new Array<UnitModel>());
//                        newItem.children = tmpModels;
//                    }
                    //end
                }
            }
            return listModel;
        }
    }
    export module service {

        // Service paths.
        var servicePath = {
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            findWorkplaceTree: "bs/employee/workplace/config/info/findAll",
//            findDepWkpTree: "bs/employee/wkpdep/get-wkpdepinfo-kcp004",
            //end
        };

        /**
         * Find department or workplace list.
         */
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
        export function findWorkplaceTree(param: WorkplaceParam): JQueryPromise<Array<UnitModel>> {
//        export function findDepWkpTree(param: WorkplaceParam): JQueryPromise<Array<UnitModel>> {
        //end
            if (_.isNil(param.systemType)) {
                let dfd = $.Deferred<any>();
                dfd.resolve([]);
                return dfd.promise();
            }
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            return nts.uk.request.ajax('com', servicePath.findWorkplaceTree, param);
//            return nts.uk.request.ajax('com', servicePath.findDepWkpTree, param);
            //end
        }

        export interface WorkplaceParam {
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            startMode: StartMode,
            //end
            baseDate: Date;
            systemType: SystemType;
            restrictionOfReferenceRange: boolean;
        }
    }

    export class TreeComponentTextResource {
        static KCP004_2 = nts.uk.resource.getText('KCP004_2');
        static KCP004_3 = nts.uk.resource.getText('KCP004_3');
        static KCP004_4 = nts.uk.resource.getText('KCP004_4');
        static KCP004_7 = nts.uk.resource.getText('KCP004_7');
        static KCP004_8 = nts.uk.resource.getText('KCP004_8');
    }

var TREE_COMPONENT_HTML = `<style type="text/css">
#nts-component-list .nts-searchbbox-wrapper {
    float: left;
}

/* fix bug show unexpected selector column on page with sidebar on IE */
#single-tree-grid_container .ui-iggrid-rowselector-header {
    border: 0px;
}
#single-tree-grid_container .ui-iggrid-rowselector-class {
    border: 0px;
}

</style>
    <div id="nts-component-tree" style="border-radius: 5px;" tabindex="-1"
        data-bind="css: {'caret-right caret-background bg-green' : !isDialog},
            style: {padding: hasPadding ? '20px' : '0px'}">
        <!-- ko if: !isDialog -->
            <i class="icon icon-searchbox"></i>
        <!-- /ko -->
       <div style ="width: 430px; height: 40px" data-bind="visible: !isMultipleUse">
        <div data-bind="visible: !isMultipleUse" style= "float: left">
            <div data-bind="ntsFormLabel: {}">`+TreeComponentTextResource.KCP004_2+`</div>
            <div class="base-date-editor" id="work-place-base-date"
                style="margin-left: -9px; margin-right: 5px;"
                data-bind="attr: {tabindex: tabindex},
                ntsDatePicker: {dateFormat: 'YYYY/MM/DD', value: baseDate, name:'#[KCP004_2]', required: true}"></div>
            <button
                data-bind="click: reload, attr: {tabindex: tabindex}"
                style="min-width: 65px">`+TreeComponentTextResource.KCP004_3+`</button>
        </div>
        <div style="margin-top: 10px; margin-bottom: 10px;">
            <div data-bind="ntsFormLabel: {}" style="margin-left: 10px; float: left">`+TreeComponentTextResource.KCP004_4+`</div>
            <div id="combo-box-tree-component"
                style="width: 60px; margin-left: 5px; float: left"
                data-bind="attr: {tabindex: tabindex}, ntsComboBox: {
                    options: levelList,
                    optionsValue: 'level',
                    value: levelSelected,
                    optionsText: 'name',
                    editable: false,
                    enable: true,
                    columns: [
                        { prop: 'name', length: 4 },
                    ]}"></div>
        </div>
        </div>
        <div style="margin-top:10px " data-bind="style: { width: isShowSelectButton ? '474px' : '420px' }">
            <div style="display: inline-block; float: left" data-bind="attr: {id: searchBoxId, tabindex: tabindex}, style: { width : !isMultipleUse ? '327px' : '268px'}">
            </div>
            <div style="display: inline-block; margin-left: 2px; float: left">
                <!-- ko if: isShowSelectButton -->
                    <button
                        data-bind="click: selectSubParent, attr: {tabindex: tabindex}">`+TreeComponentTextResource.KCP004_8+`</button>
                <!-- /ko -->
            </div>
            <div style="margin-top: 10px; margin-bottom: 10px;" data-bind="visible: isMultipleUse">
                        <div data-bind="ntsFormLabel: {}" style="margin-left: 10px; float: left">`+TreeComponentTextResource.KCP004_4+`</div>
                        <div id="combo-box-tree-component"
                            style="width: 60px; margin-left: 5px; float: left"
                            data-bind="attr: {tabindex: tabindex}, ntsComboBox: {
                                options: levelList,
                                optionsValue: 'level',
                                value: levelSelected,
                                optionsText: 'name',
                                editable: false,
                                enable: true,
                                columns: [
                                    { prop: 'name', length: 4 },
                                ]}"></div>
             </div>
        </div>
        <div class="cf"></div>
        <!-- ko if: !isMultiSelect -->
            <table id="single-tree-grid" class="cf" data-bind="attr: {tabindex: tabindex}">
            </table>
        <!-- /ko -->
        <!-- ko if: isMultiSelect -->
            <table id="multiple-tree-grid" class="cf" data-bind="attr: {tabindex: tabindex}">
            </table>
        <!-- /ko -->
    </div>`;
}

/**
 * Defined Jquery interface.
 */
interface JQuery {

    /**
     * Nts tree component.
     */
    ntsTreeComponent(option: kcp.share.tree.TreeComponentOption): JQueryPromise<void>;

    /**
     * Get Data List
     */
    getDataList(): Array<kcp.share.tree.UnitModel>;

    /**
     * Get row selected
     */
    getRowSelected(): Array<any>;

    /**
     * Focus component.
     */
    focusTreeGridComponent(): void;

    /**
     * Go to full view mode.
     */
    fullView(): void;

    /**
     * Go to scroll
     */
    scrollView(): void;
}

(function($: any) {
    $.fn.ntsTreeComponent = function(option: kcp.share.tree.TreeComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp.share.tree.TreeComponentScreenModel().init(this, option);
    }
} (jQuery));
