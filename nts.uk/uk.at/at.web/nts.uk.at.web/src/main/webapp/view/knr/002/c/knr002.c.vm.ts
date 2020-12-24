module nts.uk.at.view.knr002.c {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.c.service;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            displayText: String = "hoi cham";
    
           
            // left-grid
            currentCode1: KnockoutObservable<any> = ko.observable();
            currentCode2: KnockoutObservable<any> = ko.observable();
            dataSource: KnockoutObservableArray<RemoteSettingsDto> = ko.observableArray([]);
            columns1: KnockoutObservableArray<any>;
            columns2: KnockoutObservableArray<any>;
            bigItemData: KnockoutObservableArray<RemoteSettingsDto> = ko.observableArray([]);
            smallItemData: KnockoutObservableArray<RemoteSettingsDto> = ko.observableArray([]);

            // line 1
            empInfoTerCode: KnockoutObservable<string> = ko.observable();
            empInfoTerName: KnockoutObservable<string> = ko.observable();
            displayFlag: KnockoutObservable<string> = ko.observable();
            

            constructor() {
                const vm = this;   

                this.columns1 = ko.observableArray([
                    { headerText: getText("KNR002_80"), prop: 'majorClassification', width: 200 },
                ]);
                this.columns2 = ko.observableArray([
                    { headerText: getText("KNR002_81"), prop: 'smallClassification', width: 200 },
                ]);
                
                vm.currentCode1.subscribe((value) => {
                    vm.loadSmallGrid(value);
                })
            }

            public startPage(): JQueryPromise<void> {
                var vm = this;
                var dfd = $.Deferred<void>();
                let data : any = getShared('knr002-c');
                vm.loadData(data);
                dfd.resolve();
                return dfd.promise();
            }

            public closeDialog() {
                nts.uk.ui.windows.close();
            }

            private loadData(data: any) {
                const vm = this;
                blockUI.invisible();

                // line 1
                vm.empInfoTerCode = data.empInfoTerCode;
                vm.empInfoTerName = data.empInfoTerName;
                vm.displayFlag    = data.displayFlag;

                service.getAll(data.empInfoTerCode)
                .done((res) => {
                    if (res) {
                        
                        res.sort((item1: any, item2: any) => { return item1.majorNo - item2.majorNo; });
                        console.log(res, 'screen-c res data');
                        vm.dataSource(res);
                        vm.bigItemData(_.uniqBy(vm.dataSource(), (item) => item.majorClassification));
                        vm.currentCode1(vm.bigItemData()[0].majorClassification);
                        // console.log(vm.bigItemData(), 'major name');
                        vm.loadSmallGrid(vm.currentCode1());
                    }     
                })
                .fail(res => console.log('fail roi'))
                .always(() => blockUI.clear());
            } 

            private loadSmallGrid(majorName: string) {
                const vm = this;
                if (vm.dataSource()) {
                    vm.smallItemData(vm.dataSource().filter((item) => item.majorClassification == majorName));
                    console.log(vm.smallItemData(), 'small item data');
                    vm.currentCode2(vm.smallItemData()[0].smallClassification);
                }
            }
        }

        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        } 

        interface RemoteSettingsDto {
            majorNo: number;
            majorClassification: string;
            smallNo: number;
            smallClassification: string;
            variableName: string;
            inputType: number;
            numberOfDigits: number;
            inputRange: string;
            currentValue: string;
            updateValue: string;
            empInfoTerName: string;
            romVersion: string;
            modelEmpInfoTer: number;
        }
    }
}