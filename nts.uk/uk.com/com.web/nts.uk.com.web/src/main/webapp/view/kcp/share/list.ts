module kcp.share.list {
    export interface UnitModel {
        code: string;
        name?: string;
        companyName?: string;
        isAlreadySetting?: boolean;
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
         * is dialog, if is main screen, set false,
         */
        isDialog: boolean;
        
        /**
         * Already setting list code. structure: {code: string, isAlreadySetting: boolean}
         * ignore when isShowAlreadySet = false.
         */
        alreadySettingList?: KnockoutObservableArray<UnitModel>;
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
    
    /**
     * Screen Model.
     */
    export class ListComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        selectedCodes: KnockoutObservable<any>;
        listComponentColumn: Array<any>;
        isMultiple: boolean;
        isDialog: boolean;
        
        constructor() {
            this.itemList = ko.observableArray([]);
            this.listComponentColumn = [];
            this.isMultiple = false;
            
            // Setup list column.
            this.listComponentColumn.push({headerText: 'コード', prop: 'code', width: 50});
            this.listComponentColumn.push({headerText: '名称', prop: 'name', width: 170});
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
            
            // With Employee list, add column company name.
            if (data.listType == ListType.EMPLOYEE) {
                self.listComponentColumn.push({headerText: '所属', prop: 'companyName', width: 50});
            }
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.listComponentColumn.push({
                    headerText: '設定済', prop: 'isAlreadySetting', width: 30,
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
                    self.addAreadySettingAttr(dataList, data.alreadySettingList());
                    
                    // subscribe when alreadySettingList update => reload component.
                    data.alreadySettingList.subscribe((newSettings: Array<UnitModel>) => {
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
        
        /**
         * Find data list.
         */
        public findDataList(listType: ListType):JQueryPromise<Array<UnitModel>> {
            switch(listType) {
                case ListType.EMPLOYMENT:
                    return service.findEmployments();
                case ListType.JOB_TITLE:
                    return service.findJobTitles();
                case ListType.EMPLOYEE:
                    return service.findClassifications();
            }
        }
        
    }
    
    /**
     * Service,
     */
    export module service {
        
        // Service paths.
        var servicePath = {
            findEmployments: "basic/company/organization/employment/findAll/",
            findJobTitles: '???',
            findClassifications: 'basic/company/organization/classification/findAll/',
        }
        
        /**
         * Find Employment list.
         */
        export function findEmployments(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            return nts.uk.request.ajax(servicePath.findEmployments);
        }
        
        export function findJobTitles(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * Find Classification list.
         */
        export function findClassifications(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            dfd.resolve();
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