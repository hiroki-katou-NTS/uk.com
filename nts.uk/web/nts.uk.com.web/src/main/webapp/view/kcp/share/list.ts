module kcp.share.list {
    export interface UnitModel {
        code: string;
        name?: string;
        companyName?: string;
        isAlreadySetting?: boolean;
    }
    
    export interface ComponentOption {
        isShowAlreadySet: boolean;
        isMultiSelect: boolean;
        listType: ListType;
        selectedCode: KnockoutObservable<any>;
        alreadySettingList?: KnockoutObservableArray<UnitModel>;
    }
    
    export class ListType {
        static EMPLOYMENT = 1;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }
    
    export class ListComponentScreenModel {
        itemList: KnockoutObservableArray<UnitModel>;
        selectedCode: KnockoutObservable<any>;
        listComponentColumn: Array<any>;
        multiple: boolean;
        
        constructor() {
            this.itemList = ko.observableArray([]);
            this.listComponentColumn = [];
            this.multiple = false;
            this.selectedCode = ko.observable(null);
            
            // Setup list column.
            this.listComponentColumn.push({headerText: 'コード', prop: 'code', width: 50});
            this.listComponentColumn.push({headerText: '名称', prop: 'name', width: 100});
        }
        /**
         * Init component.
         */
        public init($input: JQuery, data: ComponentOption) {
            ko.cleanNode($input[0]);
            var self = this;
            self.multiple = data.isMultiSelect;
            self.selectedCode(data.selectedCode());
            
            // With Employee list, add column company name.
            if (data.listType == ListType.EMPLOYEE) {
                self.listComponentColumn.push({headerText: '所属', prop: 'companyName', width: 100});
            }
            
            // If show Already setting.
            if (data.isShowAlreadySet) {
                // Add row already setting.
                self.listComponentColumn.push({
                    headerText: '設定済', prop: 'isAlreadySetting', width: 30,
                    formatter: function(isAlreadySet: string) {
                        if (isAlreadySet == 'true') {
                            return '<div class="center"><i class="icon icon-dot"></i></div>';
                        }
                        return '';
                    }
                });
            }
            
            // Find data list.
            this.findDataList(data.listType).done(function(dataList: Array<UnitModel>) {
                
                // Map already setting attr to data list.
                if (data.isShowAlreadySet) {
                    var alreadyListCode = data.alreadySettingList().filter(item => item.isAlreadySetting).map(item => item.code);
                    dataList.forEach((item => {
                        item.isAlreadySetting = alreadyListCode.indexOf(item.code) > 0;
                    }))
                }
                
                // Init component.
                self.itemList(dataList);
                $input.appendTo($('body'))
                    .load(nts.uk.request.location.appRoot.rawUrl + '/view/kcp/share/list.xhtml', function() {
                        ko.applyBindings(self, this);
                    })
            });
        }
        
        /**
         * Update component.
         */
        public update($input: JQuery, data: ComponentOption) {
            
        }
        
        public findDataList(listType: ListType):JQueryPromise<Array<UnitModel>> {
            switch(listType) {
                case ListType.EMPLOYMENT:
                    return service.findEmployments();
                case ListType.JOB_TITLE:
                    return service.findJobTitles();
                case ListType.EMPLOYEE:
                    return service.findEmployees();
            }
        }
        
    }
    
    export module service {
        
        // Service paths.
        var servicePath = {
            findEmployments: '???',
            findJobTitles: '???',
            findEmployees: '???'
        }
        
        export function findEmployments(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            var data: Array<UnitModel> = [
                    {code: 'EMC1', name: 'Employment 1'},
                    {code: 'EMC2', name: 'Employment 2'},
                    {code: 'EMC3', name: 'Employment 3'},
                    {code: 'EMC4', name: 'Employment 4'},
                    {code: 'EMC5', name: 'Employment 5'},
                    {code: 'EMC6', name: 'Employment 6'},
                ]
            dfd.resolve(data);
            return dfd.promise();
        }
        
        export function findJobTitles(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
        export function findEmployees(): JQueryPromise<Array<UnitModel>> {
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
    }
    
    /**
     * Base Editor
     */
    class ListComponentBindingHandler implements KnockoutBindingHandler {

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new ListComponentScreenModel().init($(element), valueAccessor());
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            new ListComponentScreenModel().update($(element), valueAccessor());
        }
    }
    
    ko.bindingHandlers['ntsListComponent'] = new ListComponentBindingHandler();
}