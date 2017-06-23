module kcp.share.list {
    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }
    
    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
    
    /**
     * Component option.
     */
    export interface ComponentOption {
        /**
         * is Show Already setting.
         */
        isShowAlreadySet: boolean;
        
        /**
         * is Multi select.
         */
        isMultiSelect: boolean;
        
        /**
         * list type.
         * 1. Employment list.
         * 2. Classification.
         * 3. Job title list.
         * 4. Employee list.
         */
        listType: ListType;
        
        /**
         * selected value.
         * May be string or Array<string>
         */
        selectedCode: KnockoutObservable<any>;
        
        /**
         * baseDate. Available for job title list only.
         */
        baseDate?: KnockoutObservable<Date>;
        
        /**
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;
        
        /**
         * Select Type.
         * 1 - Select by selected codes.
         * 2 - Select All (Cannot select all while single select).
         * 3 - Select First item.
         * 4 - No select.
         */
        selectType: SelectType;
        
        /**
         * Check is show no select row in grid list.
         */
        isShowNoSelectRow: boolean;
        
        /**
         * check is show select all button or not. Available for employee list only.
         */
        isShowSelectAllButton?: boolean;
        
        /**
         * check is show work place column. Available for employee list only.
         */
        isShowWorkPlaceName?: boolean;
        
        /**
         * Already setting list code. structure: {code: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        /**
         * Employee input list. Available for employee list only.
         * structure: {code: string, name: string, workplaceName: string}.
         */
        employeeInputList?: KnockoutObservableArray<UnitModel>;
    }
    
    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }
    
    /**
     * List Type
     */
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }
    
    export interface GridStyle {
        codeColumnSize: number;
        totalColumnSize: number;
        totalComponentSize: number;
        totalHeight: number;
    }
    
    /**
     * Screen Model.
     */
    export class ListComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        selectedCodes: KnockoutObservable<any>;
        listComponentColumn: Array<any>;
        isMultiple: boolean;
        isDialog: boolean;
        hasBaseDate: boolean;
        baseDate: KnockoutObservable<Date>;
        isHasButtonSelectAll: boolean;
        gridStyle: GridStyle;
        listType: ListType;
        componentGridId: string;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        searchOption: any;
        
        constructor() {
            this.itemList = ko.observableArray([]);
            this.listComponentColumn = [];
            this.isMultiple = false;
            this.componentGridId = (Date.now()).toString();
        }
        /**
         * Init component.
         */
        public init($input: JQuery, data: ComponentOption) :JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            self.isMultiple = data.isMultiSelect;
            if (data.isMultiSelect) {
                self.selectedCodes = ko.observableArray([]);
            } else {
                self.selectedCodes = data.selectedCode;
            }
            self.isDialog = data.isDialog;
            self.hasBaseDate = data.listType == ListType.JOB_TITLE && !data.isDialog && !data.isMultiSelect;
            self.isHasButtonSelectAll = data.listType == ListType.EMPLOYEE
                 && data.isMultiSelect && data.isShowSelectAllButton;
            self.initGridStyle(data);
            self.listType = data.listType;
            if (self.hasBaseDate) {
                self.baseDate = data.baseDate;
            } else {
                self.baseDate = ko.observable(new Date());
            }
            
            self.selectedCodes.subscribe(function(seletedVal: any) {
                if (!seletedVal) {
                    return;
                }
                if (data.isMultiSelect) {
                    // With multi-select => remove no select item.
                    var noSeletectIndex = (<Array<string>>seletedVal).indexOf('');
                    if (noSeletectIndex > -1) {
                        var dataSelected = seletedVal.slice();
                        (<Array<string>>dataSelected).splice(noSeletectIndex);
                        data.selectedCode(dataSelected);
                    } else {
                        data.selectedCode(seletedVal);
                    }
                }
            })
            
            // Setup list column.
            this.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP001_2'), prop: 'code', width: self.gridStyle.codeColumnSize});
            this.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP001_3'), prop: 'name', width: 170});
            // With Employee list, add column company name.
            if (data.listType == ListType.EMPLOYEE && data.isShowWorkPlaceName) {
                self.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP005_4'), prop: 'workplaceName', width: 150});
            }
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                self.alreadySettingList = data.alreadySettingList;
                // Add row already setting.
                self.listComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP001_4'), prop: 'isAlreadySetting', width: 70,
                    formatter: function(isAlreadySet: string) {
                        if (isAlreadySet == 'true') {
                            return '<div style="text-align: center;"><i class="icon icon-78"></i></div>';
                        }
                        return '';
                    }
                });
            }
            
            // With list type is employee list, use employee input.
            if (self.listType == ListType.EMPLOYEE) {
                self.initComponent(data, data.employeeInputList(), $input);
                data.employeeInputList.subscribe(dataList => {
                    self.initComponent(data, data.employeeInputList(), $input);
                })
                dfd.resolve();
                return dfd.promise();
            }
            
            // Find data list.
            this.findDataList(data.listType).done(function(dataList: Array<UnitModel>) {
                self.initComponent(data, dataList, $input);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        private initComponent(data: ComponentOption, dataList: Array<UnitModel>, $input: JQuery) {
            var self = this;
            // Set default value when init component.
            self.initSelectedValue(data, dataList);

            // Map already setting attr to data list.
            if (data.isShowAlreadySet) {
                self.addAreadySettingAttr(dataList, self.alreadySettingList());

                // subscribe when alreadySettingList update => reload component.
                self.alreadySettingList.subscribe((newSettings: Array<UnitModel>) => {
                    self.addAreadySettingAttr(dataList, newSettings);
                    self.itemList(dataList);
                })
            }
            
            
            
            // Init component.
            self.itemList(dataList);
            // Check is show no select row.
            if (data.isShowNoSelectRow) {
                self.itemList.unshift({code: '', name: nts.uk.resource.getText('KCP001_5'), isAlreadySetting: false});
            }
            self.searchOption = {
                searchMode: 'filter',
                targetKey: 'code',
                comId: self.componentGridId,
                items: self.itemList,
                selected: self.selectedCodes,
                selectedKey: 'code',
                fields: ['name', 'code'],
                mode: 'igGrid'
            }
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/list.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                $input.find('table').attr('id', self.componentGridId);
                ko.cleanNode($input[0]);
                ko.applyBindings(self, $input[0]);
                $('.base-date-editor').find('.nts-input').width(133);
                
            });
            
            $(document).delegate('#' + self.componentGridId, "iggridrowsrendered", function(evt, ui) {
                self.addIconToAlreadyCol();
            });
            
            // defined function get data list.
            $('#script-for-' + $input.attr('id')).remove();
            var s = document.createElement("script");
            s.type = "text/javascript";
            s.innerHTML = 'var dataList' + $input.attr('id').replace(/-/gi, '') + ' = ' + JSON.stringify(dataList);
            s.id = 'script-for-' + $input.attr('id');
            $("head").append(s);
            $.fn.getDataList = function(): Array<kcp.share.list.UnitModel> {
                return window['dataList' + this.attr('id').replace(/-/gi, '')];
            }
            
            // defined function focus
            $.fn.focusComponent = function() {
                if (self.hasBaseDate) {
                    $('.base-date-editor').find('.nts-input').first().focus();
                } else {
                    $(".ntsSearchBox").focus();
                }
            }
        }
        
        private addIconToAlreadyCol() {
            // Add icon to column already setting.
            var iconLink = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon78.png').serialize();
            $('.icon-78').attr('style', "background: url('" + iconLink + "');width: 20px;height: 20px;background-size: 20px 20px;")
        }
        
        private initSelectedValue(data: ComponentOption, dataList: Array<UnitModel>) {
            var self = this;
            switch(data.selectType) {
                case SelectType.SELECT_BY_SELECTED_CODE:
                    if (self.isMultiple) {
                        self.selectedCodes(data.selectedCode());
                    }
                    return;
                case SelectType.SELECT_ALL:
                    if (!self.isMultiple){
                        return;
                    }
                    self.selectedCodes(dataList.map(item => item.code));
                    return;
                case SelectType.SELECT_FIRST_ITEM:
                    self.selectedCodes(dataList.length > 0 ? self.selectData(data, dataList[0]) : null);
                    return;
                case SelectType.NO_SELECT:
                    self.selectedCodes(data.isMultiSelect ? [] : null);
                    return;
                default:
                    self.selectedCodes(data.isMultiSelect ? [] : null);
            }
        }
        
        /**
         * Add Aready Setting Attr into data list.
         */
        private addAreadySettingAttr(dataList: Array<UnitModel>, alreadySettingList: Array<UnitModel>) {
            var alreadyListCode = alreadySettingList.filter(item => item.isAlreadySetting).map(item => item.code);
            dataList.map((item => {
                item.isAlreadySetting = alreadyListCode.indexOf(item.code) > -1;
            }))
        }
        
        /**
         * Select data for multiple or not
         */
        private selectData(option: ComponentOption, data: UnitModel) :any {
            if (this.isMultiple) {
                return [data.code];
            }
            return data.code;
        }
        
        private initGridStyle(data: ComponentOption) {
            var codeColumnSize: number = 50;
            var companyColumnSize: number = 0;
            switch(data.listType) {
                case ListType.EMPLOYMENT:
                    break;
                case ListType.JOB_TITLE:
                    codeColumnSize = 70;
                    break;
                case ListType.Classification:
                    codeColumnSize = 150;
                    break;
                case ListType.EMPLOYEE:
                    codeColumnSize = 150;
                    companyColumnSize = data.isShowWorkPlaceName ? 150 : 0;
                    break;
                default:
                break;
            }
            var alreadySettingColSize = data.isShowAlreadySet ? 70 : 0;
            var multiSelectColSize = data.isMultiSelect ? 55 : 0;
            var selectAllButtonSize = this.isHasButtonSelectAll ? 60 : 0;
            var totalColumnSize: number = codeColumnSize + 170 + companyColumnSize
                + alreadySettingColSize + multiSelectColSize;
            var minTotalSize = this.isHasButtonSelectAll ? 415 : 350;
            var totalHeight: number = this.hasBaseDate ? 500 : 452;
            this.gridStyle = {
                codeColumnSize: codeColumnSize,
                totalColumnSize: Math.max(minTotalSize, totalColumnSize),
                totalComponentSize: Math.max(minTotalSize, totalColumnSize) + 2,
                totalHeight: totalHeight
            };
        }
        
        /**
         * Find data list.
         */
        private findDataList(listType: ListType):JQueryPromise<Array<UnitModel>> {
            switch(listType) {
                case ListType.EMPLOYMENT:
                    return service.findEmployments();
                case ListType.JOB_TITLE:
                    return service.findJobTitles(this.baseDate());
                case ListType.Classification:
                    return service.findClassifications();
                default:
                    return;
            }
        }
        
        /**
         * Select all.
         */
        public selectAll() {
            var self = this;
            if (self.itemList().length == 0 || !self.isMultiple) {
                return;
            }
            self.selectedCodes(self.itemList().map(item => item.code));
        }
        
        /**
         * Reload screen data.
         */
        public reload() {
            var self = this;
            self.findDataList(self.listType).done((data: UnitModel[]) => {
                if (self.alreadySettingList) {
                    self.addAreadySettingAttr(data, self.alreadySettingList());
                }
                self.itemList(data);
            });
        }
        
        public getItemNameForList(): string {
            switch(this.listType) {
                case ListType.EMPLOYMENT:
                    return '#[KCP001_1]';
                case ListType.JOB_TITLE:
                    return '#[KCP003_1]';
                case ListType.Classification:
                    return '#[KCP002_1]';
                case ListType.EMPLOYEE:
                    return '#[KCP005_1]';
                default:
                    return '';
            }
        }
        
        public getItemNameForBaseDate(): string {
            if (this.hasBaseDate) {
                return '#[KCP003_2 ]'
            }
            return '';
        }
    }
    
    /**
     * Service,
     */
    export module service {
        
        // Service paths.
        var servicePath = {
            findEmployments: "basic/company/organization/employment/findAll/",
            findJobTitles: 'basic/company/organization/jobtitle/findall/',
            findClassifications: 'basic/company/organization/classification/findAll/',
        }
        
        /**
         * Find Employment list.
         */
        export function findEmployments(): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax('com', servicePath.findEmployments);
        }
        
        /**
         * Find Job title.
         */
        export function findJobTitles(baseDate: Date): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax('com', servicePath.findJobTitles, {baseDate: baseDate});
        }
        
        /**
         * Find Classification list.
         */
        export function findClassifications(): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax('com', servicePath.findClassifications);
        }
        
    }
}

/**
 * Defined Jquery interface.
 */
interface JQuery {

    /**
     * Nts list component.
     * This Function used after apply binding only.
     */
    ntsListComponent(option: kcp.share.list.ComponentOption): JQueryPromise<void>;
    
    /**
     * Get data list in component.
     */
    getDataList(): Array<kcp.share.list.UnitModel>;
    
    /**
     * Focus component.
     */
    focusComponent(): void;
}

(function($: any) {
    $.fn.ntsListComponent = function(option: kcp.share.list.ComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp.share.list.ListComponentScreenModel().init(this, option);
    }
    
} (jQuery));