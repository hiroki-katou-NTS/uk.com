module kcp.share.tree {
    export interface UnitModel {
        code: string;
        name: string;
        nodeText?: string;
        level: number;
        heirarchyCode: string;
        settingType: SettingType;
        childs: Array<UnitModel>;
    }
    
    export interface UnitAlreadySettingModel {
        code: string;
        settingType: SettingType;
    }
    
    export interface TreeComponentOption {
        /**
         * is Show Already setting.
         */
        isShowAlreadySet: boolean;
        
        /**
         * is Multi select.
         */
        isMultiSelect: boolean;
        
        /**
         * Tree type, if not set, default is work place.
         */
        treeType?: TreeType;
        
        /**
         * selected value.
         * May be string or Array<string>
         */
        selectedCode: KnockoutObservable<any>;
        
        /**
         * Base date.
         */
        baseDate: KnockoutObservable<Date>;
        
        /**
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;
        
        /**
         * Already setting list code. structure: {code: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitAlreadySettingModel>;
    }
    
    export class SettingType {
        static NO_SETTING = 0;
        static ALREADY_SETTING = 1;
        static USE_PARRENT_SETTING =2;
    }
    
    export class TreeType {
        static WORK_PLACE = 1;
    }
    
    export class TreeComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        backupItemList: KnockoutObservableArray<UnitModel>;
        selectedCodes: KnockoutObservable<any>;
        treeComponentColumn: Array<any>;
        isMultiple: boolean;
        isDialog: boolean;
        hasBaseDate: KnockoutObservable<boolean>;
        baseDate: KnockoutObservable<Date>;
        levelList: Array<any>;
        levelSelected: KnockoutObservable<number>;
        listCode: Array<string>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        constructor() {
            let self = this;
            self.itemList = ko.observableArray([]);
            self.backupItemList = ko.observableArray([]);
            self.baseDate = ko.observable(new Date());
            self.listCode = [];
            self.hasBaseDate = ko.observable(false);
            self.alreadySettingList = ko.observableArray([]);
            self.treeComponentColumn = [
                { headerText: nts.uk.resource.getText("KCP004_5"), key: 'code', dataType: "string", hidden: true },
                { headerText: nts.uk.resource.getText("KCP004_5"), key: 'nodeText', width: "90%", dataType: "string" }
            ];
            self.levelList = [
                {level: 1, name: '1'},
                {level: 2, name: '2'},
                {level: 3, name: '3'},
                {level: 4, name: '4'},
                {level: 5, name: '5'},
                {level: 6, name: '6'},
                {level: 7, name: '7'},
                {level: 8, name: '8'},
                {level: 9, name: '9'},
                {level: 10, name: '10'}
            ];
            self.levelSelected = ko.observable(10);
        }
        
        public init($input: JQuery, data: TreeComponentOption) :JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            self.isMultiple = data.isMultiSelect;
            self.hasBaseDate(!self.isMultiple);
            self.selectedCodes = data.selectedCode;
            self.isDialog = data.isDialog;
            if (self.hasBaseDate()) {
                self.baseDate = data.baseDate;
            }
            if (data.alreadySettingList) {
                self.alreadySettingList = data.alreadySettingList;
            }
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.treeComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP004_6'), key: 'settingType', width: "15%",
                    formatter: function(settingType: number) {
                        if (settingType == SettingType.USE_PARRENT_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon icon-78"></i></div>';
                        }
                        if (settingType == SettingType.ALREADY_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon icon-84"></i></div>';
                        }
                        return '';
                    }
                });
            }
            // subscribe change selected level
            self.levelSelected.subscribe(function(level) {
                if (self.backupItemList().length > 0) {
                    let subItemList = self.filterByLevel(self.backupItemList(), level, new Array<UnitModel>());
                    if (subItemList.length > 0) {
                        self.itemList(subItemList);
                    }
                }
            });
            
            // Find data.
            service.findWorkplaceTree(self.baseDate()).done(function(res: Array<UnitModel>) {
                if (res) {
                    // Set default value when init component.
                    self.selectedCodes = data.selectedCode;
                    
                    // Map already setting attr to data list.
                    self.addAlreadySettingAttr(res, self.alreadySettingList());
                    
                    if (data.isShowAlreadySet) { 
                        // subscribe when alreadySettingList update => reload component.
                        self.alreadySettingList.subscribe((newAlreadySettings: any) => {
                            self.addAlreadySettingAttr(res, newAlreadySettings);
                            self.itemList(res);
                            self.backupItemList(res);
                        });
                    }
                    // Init component.
                    self.itemList(res);
                    self.backupItemList(res);
                    var webserviceLocator = nts.uk.request.location.siteRoot
                        .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                        .mergeRelativePath('/view/kcp/share/tree.xhtml').serialize();
                    $input.load(webserviceLocator, function() {
                        ko.cleanNode($input[0]);
                        ko.applyBindings(self, $input[0]);
                        dfd.resolve();
                    });
                    
                    $(document).delegate('#' + self.getComIdSearchBox(), "igtreegridrowsrendered", function(evt, ui) {
                       self.addIconToAlreadyCol();
                    });
                    // defined function focus
                    $.fn.focusComponent = function() {
                        if (self.hasBaseDate()) {
                            $('.base-date-editor').first().focus();
                        } else {
                            $(".ntsSearchBox").focus();
                        }
                    }
                }
            });
            
            // defined function get data list.
            $.fn.getDataList = function(): Array<kcp.share.list.UnitModel> {
                return self.backupItemList();
            }
            
            return dfd.promise();
        }
        
        private addIconToAlreadyCol() {
            var icon84Link = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon84.png').serialize();
            $('.icon-84').attr('style', "background: url('"+ icon84Link +"');width: 20px;height: 20px;background-size: 20px 20px;")
            
            var icon78Link = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon78.png').serialize();
            $('.icon-78').attr('style', "background: url('"+ icon78Link +"');width: 20px;height: 20px;background-size: 20px 20px;")
        }
        
        /**
         * Add Already Setting Attr into data list.
         */
        private addAlreadySettingAttr(dataList: Array<UnitModel>, alreadySettingList: Array<UnitAlreadySettingModel>) {
            let mapAlreadySetting = _.reduce(alreadySettingList, function(hash, value) {
                let key = value['code'];
                hash[key] = value['settingType'];
                return hash;
            }, {});
            this.mapAlreadySetting(dataList, mapAlreadySetting);
        }
        
        /**
         * Update setting type for dataList
         */
        private mapAlreadySetting(dataList: Array<UnitModel>, mapAlreadySetting: any) {
            let self = this;
            for (let alreadySetting of dataList) {
                // add code work place
                self.listCode.push(alreadySetting.code);
                
                // set level
                alreadySetting.level = alreadySetting.heirarchyCode.length / 3;
                
                // set node text
                alreadySetting.nodeText = alreadySetting.code + ' ' + alreadySetting.name; 
                
                alreadySetting.settingType = mapAlreadySetting[alreadySetting.code];
                if (alreadySetting.childs.length > 0) {
                    this.mapAlreadySetting(alreadySetting.childs, mapAlreadySetting);
                }
            }
        }
        
        /**
         * Find list work place by base date
         */
        private reload() {
            let self = this;
            service.findWorkplaceTree(self.baseDate()).done(function(res: Array<UnitModel>) {
                if (res) {
                    if (self.alreadySettingList) {
                        self.addAlreadySettingAttr(res, self.alreadySettingList());
                    }
                    self.itemList(res);
                    self.backupItemList(res);
                }
            });
        }
        
        /**
         * Select all
         */
        private selectAll() {
            this.selectedCodes(this.listCode);
        }
        
        /**
         * Select all children
         */
        private selectSubParent() {
            let self = this;
            let listSubCode: Array<string> = [];
            
            for (let code of self.selectedCodes()) {
                let listModel: Array<UnitModel> = [];
                listModel = self.findUnitModelByCode(self.itemList(), code, listModel);
                self.findListSubCode(listModel, listSubCode);
            }
            if (listSubCode.length > 0) {
                self.selectedCodes(listSubCode);
            }
        }
        
        /**
         * Find list sub code of parent
         */
        private findListSubCode(dataList: Array<UnitModel>, listSubCode: Array<string>) {
            let self = this;
            for (let alreadySetting of dataList) {
                listSubCode.push(alreadySetting.code);
                if (alreadySetting.childs && alreadySetting.childs.length > 0) {
                    this.findListSubCode(alreadySetting.childs, listSubCode);
                }
            }
        }
        
        /**
         * Select data for multiple or not
         */
        private selectData(option: TreeComponentOption, data: UnitModel) :any {
            if (this.isMultiple) {
                return [data.code];
            }
            return data.code;
        }
        
        /**
         * Find UnitModel by code
         */
        private findUnitModelByCode(dataList: Array<UnitModel>, code: string,
            listModel: Array<UnitModel>) :Array<UnitModel> {
            let self = this;
            for (let item of dataList) {
                if (item.code == code) {
                    listModel.push(item); 
                }
                if (item.childs.length > 0) {
                    this.findUnitModelByCode(item.childs, code, listModel);
                }
            }
            return listModel;
        }
        
        /**
         * Get ComId Search Box by multiple choice
         */
        private getComIdSearchBox(): string {
            if (this.isMultiple) {
                return 'multiple-tree-grid';
            }
            return 'single-tree-grid';
        }
        
        /**
         * Filter list work place follow selected level
         */
        private filterByLevel(dataList: Array<UnitModel>, level: number, listModel: Array<UnitModel>): Array<UnitModel> {
            let self = this;
            for (let item of dataList) {
                let newItem: any = null;
                if (item.level <= level) {
                    newItem = JSON.parse(JSON.stringify(item));
                    listModel.push(newItem);
                }
                if (level == 1) {
                    newItem.childs = [];
                } else if (item.childs && item.childs.length > 0) {
                    let tmpModels = this.filterByLevel(newItem.childs, level, new Array<UnitModel>());
                    newItem.childs = tmpModels;
                }
            }
            return listModel;
        }
        
    }
     export module service {
        
        // Service paths.
        var servicePath = {
            findWorkplaceTree: "basic/company/organization/workplace/find",
        }
        
        /**
         * Find workplace list.
         */
        export function findWorkplaceTree(baseDate: Date): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax('com', servicePath.findWorkplaceTree, { baseDate: baseDate });
        }
    }
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
    getDataList(): Array<kcp.share.list.UnitModel>;
    
    /**
     * Focus component.
     */
    focusComponent(): void;
}

(function($: any) {
    $.fn.ntsTreeComponent = function(option: kcp.share.tree.TreeComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp.share.tree.TreeComponentScreenModel().init(this, option);;
    }
} (jQuery));