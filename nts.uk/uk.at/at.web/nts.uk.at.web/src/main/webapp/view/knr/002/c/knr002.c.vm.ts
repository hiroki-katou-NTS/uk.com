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
            rowData: KnockoutObservable<RemoteSettingsDto> = ko.observable();

            // line 1
            empInfoTerCode: KnockoutObservable<string> = ko.observable();
            empInfoTerName: KnockoutObservable<string> = ko.observable();
            displayFlag: KnockoutObservable<string> = ko.observable();

            inputMode: KnockoutObservable<number> = ko.observable();

            // mode letter
            smallClassificationName: KnockoutObservable<string> = ko.observable('');
            currentValue: KnockoutObservable<string> = ko.observable('');
            updateValue: KnockoutObservable<string> =  ko.observable(''); 
            numOfDigits: KnockoutObservable<number> = ko.observable();
            fromLetter: KnockoutObservable<string> = ko.observable();
            toLetter: KnockoutObservable<string> = ko.observable();

            // mode time
            timeInputRange: KnockoutObservable<string> = ko.observable();

            // mode ip
            ipAddress1: KnockoutObservable<string> = ko.observable('');
            ipAddress2: KnockoutObservable<string> = ko.observable('');
            ipAddress3: KnockoutObservable<string> = ko.observable('');
            ipAddress4: KnockoutObservable<string> = ko.observable('');
            ipUpdateValue: KnockoutObservable<string>;

            // mode selection
            currentValueList: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedCurrentValue: KnockoutObservable<string> = ko.observable();
            // currentUpdateList: KnockoutObservableArray<any> = ko.observableArray([]);
            updateValueList: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedUpdateValue: KnockoutObservable<string> = ko.observable();

            // grid setting data
            settingData: Array<SettingValue> = [];
            selectedRowIndex: KnockoutObservable<number> = ko.observable();

            constructor() {
                const vm = this;   

                this.columns1 = ko.observableArray([
                    { headerText: getText("KNR002_80"), prop: 'majorClassification', width: 245 },
                ]);
                this.columns2 = ko.observableArray([
                    { headerText: getText("KNR002_81"), prop: 'smallClassification', width: 245 },
                    { headerText: 'id', prop: 'smallNo', width: 0 }
                ]);
                
                vm.currentCode1.subscribe((value) => {
                    vm.loadSmallGrid(value);
                })

                vm.ipUpdateValue = ko.computed(function() {
                    return `${vm.ipAddress1()}.${vm.ipAddress2()}.${vm.ipAddress3()}.${vm.ipAddress4()}`;
                });

                vm.currentCode2.subscribe((value) => {
                    let rowData = ko.toJS(vm.smallItemData).find((item: any) => item.smallNo == value);
                    vm.rowData(rowData);
                    vm.setInputMode(rowData.inputType);
                    vm.smallClassificationName(rowData.smallClassification);

                    vm.bindDataByType(rowData);
                })

                vm.loadSettingGrid();
            }

            private bindDataByType(rowData: any) {
                const vm = this;

                vm.currentValue(rowData.currentValue);
                vm.numOfDigits(rowData.numberOfDigits);
                

                switch(vm.inputMode()) {
                    case INPUT_TYPE.LETTER:
                        let inputRange = rowData.inputRange.split(':');
                        vm.fromLetter(inputRange[0]);
                        vm.toLetter(inputRange[1]);
                        if (rowData.updateValue.length == 0) {
                            vm.updateValue(rowData.currentValue);
                            break;
                        }
                        vm.updateValue(rowData.updateValue);
                        break;
                    case INPUT_TYPE.TIME:
                        vm.timeInputRange(rowData.inputRange);
                        if (rowData.updateValue.length == 0) {
                            vm.updateValue(rowData.currentValue);
                            break;
                        }
                        vm.updateValue(rowData.updateValue);
                        break;
                    case INPUT_TYPE.IP:
                        if (rowData.updateValue.length == 0) {
                            let ipArr = rowData.currentValue.split('.');
                            vm.ipAddress1(ipArr[0]);
                            vm.ipAddress2(ipArr[1]);
                            vm.ipAddress3(ipArr[2]);
                            vm.ipAddress4(ipArr[3]);
                            break;
                        }
                        let ipArr = rowData.updateValue.split('.');
                        vm.ipAddress1(ipArr[0]);
                        vm.ipAddress2(ipArr[1]);
                        vm.ipAddress3(ipArr[2]);
                        vm.ipAddress4(ipArr[3]);
                        break;
                    case INPUT_TYPE.SELECTION:
                        let inputRangeArr = rowData.inputRange.split('/');
                        vm.currentValueList(inputRangeArr.map((item: any) => new BoxModel(item.charAt(0), item.substring(2, item.length -1))));
                        vm.selectedCurrentValue(rowData.currentValue);
                        vm.updateValueList(inputRangeArr.map((item: any) => new BoxModel(item.charAt(0), item.substring(2, item.length -1))));
                        if (rowData.updateValue.length == 0) {
                          vm.selectedUpdateValue(rowData.currentValue);  
                          break;
                        } 
                        vm.selectedUpdateValue(rowData.updateValue);  
                        break;
                    case INPUT_TYPE.CHECK:
                        let inputRangeArrCheck = rowData.inputRange.split('/');
                        vm.currentValueList(inputRangeArrCheck.map((item: any, index: number) => new BoxModel(item.charAt(0), item.substring(2, item.length -1), rowData.currentValue.indexOf(item.charAt(0)) !== -1 ? true : false)));
                        if (rowData.updateValue.length == 0) {
                        //   vm.updateValueList([...vm.currentValueList()]);  
                            // vm.updateValueList(_.cloneDeep(vm.currentValueList()));
                            let updateValueList: any = [];
                            vm.currentValueList().forEach((item: BoxModel) => { 
                                let data = new BoxModel(item.id, item.name, item.checked());
                                updateValueList.push(data);
                            });
                            vm.updateValueList(updateValueList);
                          break;
                        } 
                        vm.updateValueList(inputRangeArrCheck.map((item: any, index: number) => new BoxModel(item.charAt(0), item.substring(2, item.length -1), rowData.updateValue.indexOf(item.charAt(0)) !== -1 ? true : false)));
                        break;
                }
            }

            public startPage(): JQueryPromise<void> {
                var vm = this;
                
                var dfd = $.Deferred<void>();
                let data : any = getShared('knr002-c');
                vm.loadData(data);
                dfd.resolve();
                return dfd.promise();
            }

            public registerAndSubmit() {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_2028" })
                    .ifYes(() => {
                        console.log('yes');
                    })
                    .ifNo(() => {
                        console.log('no');
                    })

            }

            public addToSetting() {
                const vm = this;
                switch(vm.inputMode()) {
                    case INPUT_TYPE.LETTER:
                    case INPUT_TYPE.TIME:  
                        let item = new SettingValue(vm.settingData.length, vm.rowData().majorClassification, vm.rowData().smallClassification, vm.updateValue(), vm.rowData().inputRange);
                        vm.settingData.push(item);     
                        break;
                    case INPUT_TYPE.IP:
                        let item2 = new SettingValue(vm.settingData.length, vm.rowData().majorClassification, vm.rowData().smallClassification, vm.ipUpdateValue(), '');
                        vm.settingData.push(item2);     
                        break;
                    case INPUT_TYPE.SELECTION:

                        // console.log(vm.updateValueList(), 'updateValueList');
                        
                        break;
                    case INPUT_TYPE.CHECK:
                        vm.updateValueList().forEach(item => {
                            if (item.checked()) {
                                let newRow = new SettingValue(vm.settingData.length, vm.rowData().majorClassification, vm.rowData().smallClassification, 'yes', '☑' + item.name);
                                vm.settingData.push(newRow);
                            }
                        });
                        break;
                }

                $("#grid").igGrid("dataSourceObject", vm.settingData).igGrid("dataBind");

            }

            public removeFromSetting() {
                const vm = this;
                vm.settingData.splice(vm.selectedRowIndex(), 1);
                $("#grid").igGrid("dataSourceObject", vm.settingData).igGrid("dataBind");  
            }

            private loadSettingGrid() {
                let vm = this;

                $("#grid").ntsGrid({
                    width: '545px', 
                    height: '412px',
                    dataSource: vm.settingData,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    autoFitWindow: true,
                    columns: [
                        { headerText: getText("KNR002_95"), key: 'id', dataType: 'num', width: '0'},
                        { headerText: getText("KNR002_95"), key: 'majorName', dataType: 'num', width: '115px'},
                        { headerText: getText("KNR002_96"), key: 'smallName', dataType: 'string', width: '130px'},
                        { headerText: getText("KNR002_97"), key: 'updateValue', dataType: 'string', width: '80px'},
                        { headerText: getText("KNR002_98"), key: 'inputRange', dataType: 'string', width: '220px'}
                    ],
                    features: [
                        {
                            name: 'Selection',
                            mode: 'row',
                            multipleSelection: false,
                            rowSelectionChanged: function(event: any, ui: any) {
                                vm.selectedRowIndex(ui.row.index);
                             }
                        }
                    ],
                })
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
                        console.log(res, 'aaaaaa');
                        res.sort((item1: any, item2: any) => { return item1.majorNo - item2.majorNo; });
                        vm.dataSource(res);
                        vm.bigItemData(_.uniqBy(vm.dataSource(), (item) => item.majorClassification));
                        vm.currentCode1(vm.bigItemData()[0].majorClassification);
                    }     
                })
                .fail(res => console.log('fail roi'))
                .always(() => blockUI.clear());
            } 

            private loadSmallGrid(majorName: string) {
                const vm = this;

                if (vm.dataSource()) {
                    vm.smallItemData(vm.dataSource().filter((item) => item.majorClassification == majorName));
                    vm.currentCode2(vm.smallItemData()[0].smallNo);
                }
            }

            private setInputMode(inputType: number) {
                const vm = this;

                switch (inputType) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 6:
                        vm.inputMode(INPUT_TYPE.LETTER);
                        break;
                    case 4: 
                        vm.inputMode(INPUT_TYPE.TIME);
                        break;
                    case 5: 
                        vm.inputMode(INPUT_TYPE.IP);
                        break;
                    case 7: 
                    case 8: 
                        vm.inputMode(INPUT_TYPE.SELECTION);
                        break;
                    case 9: 
                        vm.inputMode(INPUT_TYPE.CHECK);
                        break;
                }
            }
        }
        class BoxModel {
            name: string;
            id: string;
            checked?: KnockoutObservable<boolean>; 

            constructor(id: string, name: string, checked?: boolean) {
                const vm = this;
                vm.id = id;
                vm.name = name;
                vm.checked = ko.observable(checked);
            }
        }
        class SettingValue {
            id: number;
            majorName: string;
            smallName: string;
            updateValue: string;
            inputRange: string;

            constructor(id: number, majorName: string, smallName: string, updateValue: string, inputRange: string) {
                const vm = this;
                vm.id = id;
                vm.majorName = majorName;
                vm.smallName = smallName;
                vm.updateValue = updateValue;
                vm.inputRange = inputRange;
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

        enum INPUT_TYPE {
            LETTER = 0,
            TIME = 1,
            IP = 2,
            SELECTION = 3,
            CHECK = 4,
        }
    }
}