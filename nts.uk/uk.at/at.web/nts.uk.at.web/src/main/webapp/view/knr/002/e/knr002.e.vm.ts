module nts.uk.at.view.knr002.e {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.e.service;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            displayText: String = "hoi cham";

            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: any;
    
            constructor() {
                const vm = this;   

                vm.roundingRules = ko.observableArray([
                    { code: '1', name: '     バックアップ     ' },
                    { code: '2', name: '         復旧         ' },
                ]);
                vm.selectedRuleCode = ko.observable(1);
            }

            public startPage(): JQueryPromise<void> {
                var vm = this;
                var dfd = $.Deferred<void>();
                // let data : any = getShared('knr002-c');
                // vm.loadData(data);
                console.log('E screen');
                dfd.resolve();
                return dfd.promise();
            }

            public closeDialog() {
                nts.uk.ui.windows.close();
            }

            
        }
    }
}