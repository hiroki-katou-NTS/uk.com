module kcp.share.tree {
    export interface UnitModel {
        code: string;
        name: string;
        nodeText?: string;
        level: number;
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
        hasBaseDate: boolean;
        baseDate: KnockoutObservable<Date>;
        levelList: Array<any>;
        levelSelected: KnockoutObservable<number>;
        listCode: KnockoutObservableArray<string>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        constructor() {
            let self = this;
            self.itemList = ko.observableArray([]);
            self.backupItemList = ko.observableArray([]);
            self.baseDate = ko.observable(new Date());
            self.listCode = ko.observableArray([]);
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
            self.selectedCodes = data.selectedCode;
            self.isDialog = data.isDialog;
            self.baseDate = data.baseDate;
            self.alreadySettingList = data.alreadySettingList;
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.treeComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP004_6'), key: 'settingType', width: "10%",
                    formatter: function(settingType: number) {
                        if (settingType == SettingType.USE_PARRENT_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon-close"></i></div>';
                        }
                        if (settingType == SettingType.ALREADY_SETTING) {
                            return '<div style="text-align: center;"><i class="icon icon-dot"></i></div>';
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
            service.findWorkplaceTree(self).done(function(res: Array<UnitModel>) {
                
                // fake data
                res = self.fake();
                
                // Set default value when init component.
                if (!data.selectedCode() || data.selectedCode().length == 0) {
                    self.selectedCodes(res.length > 0 ? self.selectData(data, res[0]) : null);
                }
                
                // Map already setting attr to data list.
                if (data.isShowAlreadySet) {
                    self.addAreadySettingAttr(res, self.alreadySettingList());
                }
                
                // Init component.
                self.itemList(res);
                self.backupItemList(res);
                $input.load(nts.uk.request.location.appRoot.rawUrl + '/view/kcp/share/tree.xhtml', function() {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                });
                dfd.resolve();
            });
            
            return dfd.promise();
        }
        
        /**
         * Add Already Setting Attr into data list.
         */
        private addAreadySettingAttr(dataList: Array<UnitModel>, alreadySettingList: Array<UnitAlreadySettingModel>) {
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
                self.listCode().push(alreadySetting.code);
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
            service.findWorkplaceTree(self).done(function(res: Array<UnitModel>) {
                if (self.alreadySettingList) {
                    self.addAreadySettingAttr(res, self.alreadySettingList());
                }
                self.itemList(res);
                self.backupItemList(res);
            });
        }
        
        /**
         * Select all
         */
        private selectAll() {
            this.selectedCodes(this.listCode());
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
                if (alreadySetting.childs.length > 0) {
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
        private findUnitModelByCode(dataList: Array<UnitModel>, code: string, listModel: Array<UnitModel>): Array<UnitModel> {
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
                } else if (item.childs.length > 0) {
                    let tmpModels = this.filterByLevel(newItem.childs, level, new Array<UnitModel>());
                    newItem.childs = tmpModels;
                }
            }
            return listModel;
        }
        
        /**
         * Fake list work place
         */
        private fake(): Array<UnitModel> {
            return [
                {code: '001', name: 'Name001', nodeText: '001 Name001', level: 1, settingType: null, childs: [
                    {code: '001001', name: 'Name001001', nodeText: '001001 Name001001', level: 2, settingType: null, childs: [
                        {code: '001001001', name: 'Name001001001', nodeText: '001001001 Name001001001', level: 3, settingType: null, childs: []}
                    ]},
                    {code: '001002', name: 'Name001002', nodeText: '001002 Name001002', level: 2, settingType: null, childs: []}
                ]},
                {code: '002', name: 'Name002', nodeText: '002 Name002', level: 1, settingType: null, childs: [
                    {code: '002001', name: 'Name002001', nodeText: '002001 Name002001', level: 2, settingType: null, childs: []},
                    {code: '002002', name: 'Name002002', nodeText: '002002 Name002002', level: 2, settingType: null, childs: [
                        {code: '002002001', name: 'Name002002001', nodeText: '002002001 Name002002001', level: 3, settingType: null, childs: []}
                    ]}
                ]},
                {code: '003', name: 'Name003', nodeText: '003 Name003', level: 1, settingType: null, childs: [
                    {code: '003001', name: 'Name003001', nodeText: '003001 Name003001', level: 2, settingType: null, childs: []},
                    {code: '003002', name: 'Name003002', nodeText: '003002 Name003002', level: 2, settingType: null, childs: [
                        {code: '003002001', name: 'Name003002001', nodeText: '003002001 Name003002001', level: 3, settingType: null, childs: []}
                    ]}
                ]},
                {code: '004', name: 'Name004', nodeText: '004 Name004', level: 1, settingType: null, childs: []}
            ];
        }
        
    }
     export module service {
        
        // Service paths.
        var servicePath = {
            findWorkplaceTree: "basic/company/organization/employment/findAll/",
        }
        
        /**
         * Find Employment list.
         */
        export function findWorkplaceTree(screenModel: TreeComponentScreenModel): JQueryPromise<Array<UnitModel>> {
            // TODO: find list work place by base date
            return nts.uk.request.ajax(servicePath.findWorkplaceTree);
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
}

(function($: any) {
    $.fn.ntsTreeComponent = function(option: kcp.share.tree.TreeComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp.share.tree.TreeComponentScreenModel().init(this, option);;
    }
} (jQuery));