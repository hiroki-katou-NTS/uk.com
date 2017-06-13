module kcp.share.list {
    export interface UnitModel {
        code: string;
        name?: string;
        companyName?: string;
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
         * 2. ???
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
         * baseDate
         */
        baseDate?: KnockoutObservable<Date>;
        
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
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        
        constructor() {
            this.itemList = ko.observableArray([]);
            this.listComponentColumn = [];
            this.isMultiple = false;
        }
        /**
         * Init component.
         */
        public init($input: JQuery, data: ComponentOption) :JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            self.isMultiple = data.isMultiSelect;
            self.selectedCodes = data.selectedCode;
            self.isDialog = data.isDialog;
            self.hasBaseDate = data.listType == ListType.JOB_TITLE;
            self.isHasButtonSelectAll = data.listType == ListType.EMPLOYEE && data.isMultiSelect;
            self.initGridStyle(data);
            self.listType = data.listType;
            if (data.baseDate) {
                self.baseDate = data.baseDate;
            }
            
            // Setup list column.
            this.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP001_2'), prop: 'code', width: self.gridStyle.codeColumnSize});
            this.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP001_3'), prop: 'name', width: 170});
            // With Employee list, add column company name.
            if (data.listType == ListType.EMPLOYEE) {
                self.listComponentColumn.push({headerText: nts.uk.resource.getText('KCP005_4'), prop: 'companyName', width: 150});
            }
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                self.alreadySettingList = data.alreadySettingList;
                // Add row already setting.
                self.listComponentColumn.push({
                    headerText: nts.uk.resource.getText('KCP001_4'), prop: 'isAlreadySetting', width: 70,
                    formatter: function(isAlreadySet: string) {
                        if (isAlreadySet == 'true') {
                            return '<div style="text-align: center;"><i class="icon icon-dot"></i></div>';
                        }
                        return '';
                    }
                });
            }
            
            // Find data list.
            this.findDataList(data.listType).done(function(dataList: Array<UnitModel>) {
                // Set default value when init component.
                if (!data.selectedCode() || data.selectedCode().length == 0) {
                    self.selectedCodes(dataList.length > 0 ? self.selectData(data, dataList[0]) : null);
                }
                
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
                $input.load(nts.uk.request.location.appRoot.rawUrl + '/view/kcp/share/list.xhtml', function() {
                    ko.cleanNode($input[0]);
                    ko.applyBindings(self, $input[0]);
                    $(".ntsSearchBox").focus();
                    $('.base-date-editor').find('.nts-input').width(133);
                });
                dfd.resolve();
            });
            return dfd.promise();
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
                    companyColumnSize = 150;
                    break;
                default:
                break;
            }
            var alreadySettingColSize = data.isShowAlreadySet ? 70 : 0;
            var multiSelectColSize = data.isMultiSelect ? 65 : 0;
            var selectAllButtonSize = this.isHasButtonSelectAll ? 60 : 0;
            var totalColumnSize: number = codeColumnSize + 170 + companyColumnSize
                + alreadySettingColSize + multiSelectColSize + selectAllButtonSize;
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
            return nts.uk.request.ajax(servicePath.findEmployments);
        }
        
        /**
         * Find Job title.
         */
        export function findJobTitles(baseDate: Date): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax(servicePath.findJobTitles, {baseDate: baseDate});
        }
        
        /**
         * Find Classification list.
         */
        export function findClassifications(): JQueryPromise<Array<UnitModel>> {
            return nts.uk.request.ajax(servicePath.findClassifications);
        }
        
    }
}

/**
 * Defined Jquery interface.
 */
interface JQuery {

    /**
     * Nts list component.
     */
    ntsListComponent(option: kcp.share.list.ComponentOption): JQueryPromise<void>;
}

(function($: any) {
    $.fn.ntsListComponent = function(option: kcp.share.list.ComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp.share.list.ListComponentScreenModel().init(this, option);;
    }
} (jQuery));