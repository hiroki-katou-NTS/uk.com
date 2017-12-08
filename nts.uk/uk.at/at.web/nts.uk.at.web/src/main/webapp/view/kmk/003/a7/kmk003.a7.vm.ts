module a7 {
    class ScreenModel {

        fixTableOption: any;
        dataSource: KnockoutObservableArray<any>;
        itemList: KnockoutObservableArray<any>;
        isFixedOrDiffTime: KnockoutObservable<boolean>;
        
        useFixedRestTimeOptions: KnockoutObservableArray<any>;
        useFixedRestTime: KnockoutObservable<string>;
        /**
        * Constructor.
        */
        constructor(data: any, screenMode: string, settingMethod: any, workTimeCode: string) {
            let self = this;

            self.dataSource = ko.observableArray([]);
            self.dataSource.subscribe((newList) => {
                console.log(newList);
            });
            self.itemList = ko.observableArray([
                { code: 1, name: '基本給1' },
                { code: 2, name: '役職手当2' },
                { code: 3, name: '基本給3' }
            ]);
            self.fixTableOption = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSource,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 10
            };
            self.isFixedOrDiffTime = ko.computed(function() {
                return (settingMethod() == "1") || (settingMethod() == "2");
            });
            
            self.useFixedRestTimeOptions = ko.observableArray([
                { code: 1, name: 　nts.uk.resource.getText("KMK003_142") },
                { code: 2, name: 　nts.uk.resource.getText("KMK003_143") },
            ]);
            self.useFixedRestTime = ko.observable('1');
        }

        private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), key: "column1", defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }), width: 243, template: `<div data-bind="ntsTimeRangeEditor: {
                        required: true, enable: true, inputFormat: 'time'}"/>`}
            ];
        }
    }

    class KMK003A7BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a7/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let data = input.data;
            let screenMode = ko.unwrap(input.screenMode);
            let settingMethod = input.settingMethod;
            let workTimeCode = input.workTimeCode;

            var screenModel = new ScreenModel(data, screenMode, settingMethod, workTimeCode);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);

                // TODO: need to check
//                $('#nts-fix-table-a7').ntsFixTableCustom(screenModel.fixTableOption);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A7'] = new KMK003A7BindingHandler();

}
