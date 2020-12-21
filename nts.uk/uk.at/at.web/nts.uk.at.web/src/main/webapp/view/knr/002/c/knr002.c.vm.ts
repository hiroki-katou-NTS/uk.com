module nts.uk.at.view.knr002.c {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.c.service;
    import modal =  nts.uk.ui.windows.sub.modal;

    export module viewmodel{
        export class ScreenModel{
            displayText: String = "hoi cham";

            conttructor() {
                const vm = this;
            }

            public startPage(): JQueryPromise<void>{
                var vm = this;										
                var dfd = $.Deferred<void>();
                // blockUI.invisible();
                // vm.loadData();
                // setInterval(vm.loadData.bind(vm), 300000);
                console.log('start page c');													
                dfd.resolve();											
                return dfd.promise();											
            }
        }
    }
}