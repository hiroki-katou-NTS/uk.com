module nts.uk.at.view.knr002.c {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.knr002.c.service;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            // left-grid
            currentCode1: KnockoutObservable<any> = ko.observable();
            currentCode2: KnockoutObservable<any> = ko.observable().extend({ notify: 'always' });
            dataSource: KnockoutObservableArray<SelectionItemsDto> = ko.observableArray([]);
            columns1: KnockoutObservableArray<any>;
            columns2: KnockoutObservableArray<any>;
            bigItemData: KnockoutObservableArray<SelectionItemsDto> = ko.observableArray([]);
            smallItemData: KnockoutObservableArray<SelectionItemsDto> = ko.observableArray([]);
            rowData: KnockoutObservable<SelectionItemsDto> = ko.observable();

            totalRegisteredTer: any;

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
            ipAddress1: KnockoutObservable<number> = ko.observable();
            ipAddress2: KnockoutObservable<number> = ko.observable();
            ipAddress3: KnockoutObservable<number> = ko.observable();
            ipAddress4: KnockoutObservable<number> = ko.observable();
            ipUpdateValue: KnockoutObservable<string>;

            // mode selection
            currentValueList: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedCurrentValue: KnockoutObservable<string> = ko.observable();

            // currentUpdateList: KnockoutObservableArray<any> = ko.observableArray([]);
            updateValueList: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedUpdateValue: KnockoutObservable<string> = ko.observable();

            // grid setting data
            settingData: Array<SettingValue> = [];
            selectedRowIndex: KnockoutObservable<number> = ko.observable(-1);
            inputTimeError: boolean = false;
            inputModeValue: number = 0;

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
                });

                vm.ipUpdateValue = ko.computed(function() {
                    return `${vm.ipAddress1()}.${vm.ipAddress2()}.${vm.ipAddress3()}.${vm.ipAddress4()}`;
                });

                vm.currentCode2.subscribe((value) => {
                    // vm.clearError();
                    nts.uk.ui.errors.clearAll();
                    let rowData: SelectionItemsDto = ko.toJS(vm.smallItemData).filter((item: SelectionItemsDto) => item.smallClassification == value)[0]; 
                    vm.smallClassificationName(rowData.smallClassification);
                    vm.rowData(rowData);
                    vm.setInputMode(rowData.inputType);
                    vm.bindDataByType(rowData);
                });

                vm.updateValue.subscribe((value) => {
                    console.log(value, 'update value day');
                });

                $( "#C6_5" ).keyup((event: any) => {
                    vm.checkLetterModeInput(event);
                });

                $('#C7_7').keyup((event: any) => {
                    vm.checkTimeModeInput(event);
                });
            }

            private checkLetterModeInput(event: any) {
                const vm = this;
                let inputValue = $('#C6_5').val();
                console.log(inputValue.length, 'input lenght'); 
                if (event.keyCode  !== 8) {
                    if (vm.inputModeValue == 0 || vm.inputModeValue == 1 || vm.inputModeValue == 2) {
                        let reg = new RegExp('^[0-9]*$');
                        if (parseInt(inputValue) < parseInt(vm.fromLetter()) || parseInt(inputValue) > parseInt(vm.toLetter()) || inputValue.length > vm.numOfDigits() || !reg.test(inputValue)) {
                            nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                        }
                    }

                    if (vm.inputModeValue == 3) {
                        if (inputValue.length > vm.numOfDigits()) {
                            nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                        }
                    }
                }
            }

            private checkTimeModeInput(event: any) {
                const vm = this;
                let inputValue = $('#C7_7').val();
                if (event.keyCode !== 8) {
                    if (inputValue.length > 4) {
                        nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                    } else {
                        if (vm.rowData().inputRange == '9900') {
                            if (inputValue !== '9900') {
                                vm.timeModeCheckInput(inputValue.length, inputValue);
                            }
                        } else {
                            vm.timeModeCheckInput(inputValue.length, inputValue);
                        }
                    }
                }
            }

            private timeModeCheckInput(numOfDigit: number, input: string) {
                const vm = this;
                let reg = new RegExp('^[0-9]*$');
                if (!reg.test(input)) {
                    nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                    vm.inputTimeError = true;
                    return;
                }

                if (numOfDigit >= 4) {
                    if (parseInt(input.substr(0, 2)) > 23 || parseInt(input.substr(0, 2)) < 0 || parseInt(input.substr(2)) > 59 || parseInt(input.substr(2)) < 0) {
                        nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                        vm.inputTimeError = true;
                    } else {
                        vm.inputTimeError = false;
                    }
                }
            }

            private bindDataByType(rowData: any) {
                const vm = this;

                vm.currentValue(rowData.currentValue);
                vm.numOfDigits(rowData.numberOfDigits);
                
                switch(vm.inputMode()) {
                    case INPUT_TYPE.LETTER:
                        $('#C6_5').focus();
                        let inputRange = rowData.inputRange.split(':');
                        vm.fromLetter(inputRange[0]);
                        vm.toLetter(inputRange[1]);
                        if (rowData.updateValue.length == 0) {
                            vm.updateValue(rowData.currentValue);
                            break;
                        }
                        vm.updateValue(rowData.updateValue);
                        break;
                    case INPUT_TYPE.LETTER2:
                        $('#C6_5').focus();
                        if (rowData.updateValue.length == 0) {
                            vm.updateValue(rowData.currentValue);
                            break;
                        }
                        vm.updateValue(rowData.updateValue);
                        break;    
                    case INPUT_TYPE.TIME:
                        $('#C7_7').focus();
                        vm.timeInputRange(rowData.inputRange);
                        if (rowData.updateValue.length == 0) {
                            vm.updateValue(rowData.currentValue);
                            break;
                        }
                        vm.updateValue(rowData.updateValue);
                        break;
                    case INPUT_TYPE.IP:
                        $('#C8_5').focus();

                        let currentIpArr = vm.currentValue().split('.');
                        let newCurrentValue = currentIpArr.map(e => parseInt(e)).join('.');
                        vm.currentValue(newCurrentValue);
                        
                        if (rowData.updateValue.length == 0) {
                            if (rowData.currentValue.length == 0) {
                                vm.ipAddress1(null);
                                vm.ipAddress2(null);
                                vm.ipAddress3(null);
                                vm.ipAddress4(null);
                                break;
                            }
                            let ipArr = rowData.currentValue.split('.');
                            vm.ipAddress1(parseInt(ipArr[0]));
                            vm.ipAddress2(parseInt(ipArr[1]));
                            vm.ipAddress3(parseInt(ipArr[2]));
                            vm.ipAddress4(parseInt(ipArr[3]));
                            break;
                        }
                        let ipArr = rowData.updateValue.split('.');
                        vm.ipAddress1(parseInt(ipArr[0]));
                        vm.ipAddress2(parseInt(ipArr[1]));
                        vm.ipAddress3(parseInt(ipArr[2]));
                        vm.ipAddress4(parseInt(ipArr[3]));
                        break;
                    case INPUT_TYPE.SELECTION:
                        $('.radio-right').focus();
                        vm.selectedUpdateValue(null);
                        vm.updateValueList([]);
                        let inputRangeArr = rowData.inputRange.split('/');
                        vm.currentValueList(inputRangeArr.map((item: any) => {
                            let list = item.split('(');
                            return new BoxModel(list[0], list[1].substring(0, list[1].length -1));
                        }));
                        vm.selectedCurrentValue(rowData.currentValue);
                        vm.updateValueList(inputRangeArr.map((item: any) => {
                            let list = item.split('(');
                            return new BoxModel(list[0], list[1].substring(0, list[1].length -1));
                        }));
                        if (rowData.updateValue.length == 0) {
                          vm.selectedUpdateValue(rowData.currentValue);  
                          break;
                        } 
                        vm.selectedUpdateValue(rowData.updateValue);  
                        break;
                    case INPUT_TYPE.CHECK:
                        let inputRangeArrCheck = rowData.inputRange.split('/');
                        console.log(rowData, 'fix row data');
                        vm.currentValueList(inputRangeArrCheck.map((item: any, index: number) => new BoxModel(item.charAt(0), item.substring(2, item.length -1), rowData.currentValue.charAt(index) == item.split('(')[0] ? true : false)));
                        if (rowData.updateValue.length == 0) {
                            let updateValueList: any = [];
                            vm.currentValueList().forEach((item: BoxModel) => { 
                                let data = new BoxModel(item.id, item.name, item.checked());
                                updateValueList.push(data);
                            });
                            vm.updateValueList(updateValueList);
                            $('.update-check div')[0].focus();
                          break;
                        } 
                        vm.updateValueList(inputRangeArrCheck.map((item: any, index: number) => new BoxModel(item.charAt(0), item.substring(2, item.length -1), rowData.updateValue.charAt(index) == item.split('(')[0] ? true : false)));
                            $('.update-check div')[0].focus();
                        break;
                }
            }

            public startPage(): JQueryPromise<void> {
                var vm = this;
                
                var dfd = $.Deferred<void>();
                let data : any = getShared('knr002-c');
                vm.loadData(data);;
                dfd.resolve();
                return dfd.promise();
            }

            public registerAndSubmit() {
                const vm = this;

                $('#single-list_container').focus();
                if (nts.uk.ui.errors.hasError()) { 
                    setTimeout(() => {
                        if ($(window.parent.document).find(".ui-widget-overlay").length === 1) {
                            $("#func-notifier-errors").trigger("click");
                        }
                    }, 1);

                    return; 
                }

                let obj: any = {};
                vm.settingData.forEach((item: SettingValue) => {
                    if(obj[item.variableName] === undefined){
                        obj[item.variableName] = "";
                    } 
                    if (item.selectedIndex) {
                        obj[item.variableName] += item.selectedIndex;
                    } else {
                        obj[item.variableName] += item.updateValue;
                    }
                });

                let listTimeRecordSetUpdateDto = [];

                for (let key in obj) {
                    let timeRecordSetUpdateDto = new TimeRecordSetUpdateDto(key, obj[key]);
                    listTimeRecordSetUpdateDto.push(timeRecordSetUpdateDto); 
                }   
            
                let command: any = {
                    empInfoTerCode: [vm.empInfoTerCode()],
                    empInfoTerName: vm.dataSource()[0].empInfoTerName, 
                    romVersion: vm.dataSource()[0].romVersion,
                    modelEmpInfoTer: vm.dataSource()[0].modelEmpInfoTer,
                    listTimeRecordSetUpdateDto
                }

                let status: boolean = true;

                if (vm.settingData.length == 0 || vm.totalRegisteredTer == 1) {
                    status = false;
                }

                blockUI.invisible();
                service.register(command)
                .done((res: any) => {
                    let updateInput = { 
                        listEmpTerminalCode: [vm.empInfoTerCode()]
                    }
                    service.updateRemoteSettings(updateInput).done(() => {})
                    .fail(() => {})
                    .always(() => blockUI.clear());
                })
                .fail((err: any) => { 
                    nts.uk.ui.dialog.error({ messageId: err.messageId });
                    status = false;
                })
                .always(() => {
                    blockUI.clear();
                    if (status) {
                        nts.uk.ui.dialog.confirm({ messageId: "Msg_2028" })
                        .ifYes(() => {
                            command.displayName = vm.empInfoTerName();
                            setShared('KNR002D_command', command);
                                modal('/view/knr/002/d/index.xhtml', { title: 'D_Screen', }).onClosed(() => {
                                    nts.uk.ui.windows.close();
                                    blockUI.clear();
                                });
                        })
                        .ifNo(() => {
                            nts.uk.ui.windows.close();
                        })
                    } else {
                        nts.uk.ui.windows.close();
                    }
                });
            }

            private checkExistBeforeAdd(smallName: string, majorName?: string) {
                const vm = this;
                vm.settingData = vm.settingData.filter(item => (item.smallName + item.majorName) !== (smallName + majorName));
            }

            public addToSetting() {
                const vm = this;
                if (nts.uk.ui.errors.hasError()) { 
                    setTimeout(() => {
                        if ($(window.parent.document).find(".ui-widget-overlay").length === 1) {
                            $("#func-notifier-errors").trigger("click");
                        }
                    }, 1);

                    return; 
                }
                vm.validateErr();
                

                let checkLength = vm.settingData.length;

                vm.settingData.sort((item1: SettingValue, item2: SettingValue) => { 
                    if (item1.majorNo == item2.majorNo) {
                        return item1.smallNo - item2.smallNo;
                    }
                    return item1.majorNo - item2.majorNo;
                });

                let inputValue = $('#C6_5').val();

                switch(vm.inputMode()) {
                    case INPUT_TYPE.LETTER:
                    case INPUT_TYPE.TIME:  

                        let reg = new RegExp('^[0-9]*$');
                        
                        if (vm.inputModeValue == 0 || vm.inputModeValue == 1 || vm.inputModeValue == 2) {
                            if (parseInt(inputValue) < parseInt(vm.fromLetter()) || parseInt(inputValue) > parseInt(vm.toLetter()) || inputValue.length > vm.numOfDigits() || !reg.test(inputValue)) {
                                nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                                break;
                            }
                        }

                        let inputValue2 = $('#C7_7').val();
                        if (vm.inputModeValue == 4) {
                            if (inputValue2.length != 4) {
                                nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                                break;
                            } else {
                                if (vm.rowData().inputRange == '9900') {
                                    if (inputValue2 !== '9900') {
                                        vm.timeModeCheckInput(inputValue2.length, inputValue2);
                                    }
                                } else {
                                    vm.timeModeCheckInput(inputValue2.length, inputValue2);
                                }
                            }
                        }

                        if (!(vm.updateValue().length > 0) || vm.inputTimeError) {
                            vm.inputTimeError = false;
                            break;
                        }
                        vm.checkExistBeforeAdd(vm.rowData().smallClassification, vm.rowData().majorClassification);
                        let item = new SettingValue(Math.random(), vm.rowData().majorNo, vm.rowData().majorClassification, vm.rowData().smallNo, vm.rowData().smallClassification, vm.updateValue(), vm.rowData().inputRange, vm.rowData().variableName);
                        vm.settingData.push(item);     
                        break;
                    case INPUT_TYPE.LETTER2:  
                        if (vm.inputModeValue == 3) {
                            if (inputValue.length > vm.numOfDigits()) {
                                nts.uk.ui.dialog.error({messageId: "Msg_2184"});
                                break;
                            }
                        }
                        if (!(vm.updateValue().length > 0)) {
                            break;
                        }
                        vm.checkExistBeforeAdd(vm.rowData().smallClassification, vm.rowData().majorClassification);
                        let item6 = new SettingValue(Math.random(), vm.rowData().majorNo, vm.rowData().majorClassification, vm.rowData().smallNo, vm.rowData().smallClassification, vm.updateValue(), '', vm.rowData().variableName);
                        vm.settingData.push(item6);     
                        break;    
                    case INPUT_TYPE.IP:
                        if (!vm.checkIpAddress(vm.ipAddress1()) || !vm.checkIpAddress(vm.ipAddress2()) || !vm.checkIpAddress(vm.ipAddress3()) || !vm.checkIpAddress(vm.ipAddress4())) {
                            break;
                        }
                        vm.checkExistBeforeAdd(vm.rowData().smallClassification, vm.rowData().majorClassification);
                        let item2 = new SettingValue(Math.random(), vm.rowData().majorNo, vm.rowData().majorClassification, vm.rowData().smallNo, vm.rowData().smallClassification, vm.ipUpdateValue(), '', vm.rowData().variableName);
                        vm.settingData.push(item2);     
                        break;
                    case INPUT_TYPE.SELECTION:
                        vm.checkExistBeforeAdd(vm.rowData().smallClassification, vm.rowData().majorClassification);
                        let indexInputRange = _.findIndex(vm.updateValueList(), (item) => vm.selectedUpdateValue() ==  item.id);
                        if (indexInputRange == -1) {
                            break;
                        }
                        let newRow = new SettingValue(Math.random(), vm.rowData().majorNo, vm.rowData().majorClassification, vm.rowData().smallNo, vm.rowData().smallClassification, vm.selectedUpdateValue(), vm.rowData().inputRange, vm.rowData().variableName, vm.selectedUpdateValue());
                        vm.settingData.push(newRow);
                        break;
                    case INPUT_TYPE.CHECK:
                        vm.checkExistBeforeAdd(vm.rowData().smallClassification, vm.rowData().majorClassification);
                        let updateValueCheckMode = '';
                        vm.updateValueList().forEach((item) => {
                            if (item.checked()) {
                                updateValueCheckMode += item.id;
                            } else {
                                updateValueCheckMode += '0';
                            }
                        });
                        let rowCheckMode = new SettingValue(Math.random(), vm.rowData().majorNo, vm.rowData().majorClassification, vm.rowData().smallNo, vm.rowData().smallClassification, updateValueCheckMode, vm.rowData().inputRange, vm.rowData().variableName);
                        vm.settingData.push(rowCheckMode);

                        break;
                }

                vm.selectedRowIndex(-1)
                $('#grid').igGridSelection('selectRow', vm.selectedRowIndex());

                $("#grid").igGrid("dataSourceObject", vm.settingData).igGrid("dataBind");
                if (checkLength = 17) {
                    $("#grid").igGrid("option", "width", "599px");
                    $("#grid").igGrid("option", "width", "600px");
                }

                $('#grid_container #grid_scrollContainer').scrollTop(document.getElementById('grid_scrollContainer').scrollHeight);
            }

            public removeFromSetting() {
                const vm = this;
                if (vm.selectedRowIndex() == -1) {
                    return;
                }

                let checkLength = vm.settingData.length;

                vm.settingData.splice(vm.selectedRowIndex(), 1);

                $("#grid").igGrid("dataSourceObject", vm.settingData).igGrid("dataBind");  
                if (vm.selectedRowIndex() == vm.settingData.length) {
                    vm.selectedRowIndex(vm.selectedRowIndex() - 1);
                }
                $('#grid').igGridSelection('selectRow', vm.selectedRowIndex());
                if (checkLength = 18) {
                    $("#grid").igGrid("option", "width", "599px");
                    $("#grid").igGrid("option", "width", "600px");
                }
            }

            private loadSettingGrid() {
                let vm = this;

                // id: any;
                // majorName: string;
                // smallName: string;
                // updateValue: string;
                // inputRange: string;
                // selectedIndex?: string;
                // variableName: string;
                if (vm.settingData.length > 0) {
                    vm.settingData.sort((item1: SettingValue, item2: SettingValue) => { 
                        if (item1.majorNo == item2.majorNo) {
                            return item1.smallNo - item2.smallNo;
                        }
                        return item1.majorNo - item2.majorNo;
                    });
                }
                

                $("#grid").ntsGrid({
                    width: '600px', 
                    height: '415px',
                    dataSource: vm.settingData,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    autoFitWindow: true,
                    columns: [
                        { headerText: getText("KNR002_95"), key: 'id', dataType: 'num', width: '0'},
                        { headerText: getText("KNR002_95"), key: 'majorName', dataType: 'num', width: '120px'},
                        { headerText: getText("KNR002_96"), key: 'smallName', dataType: 'string', width: '150px'},
                        { headerText: getText("KNR002_97"), key: 'updateValue', dataType: 'string', width: '80px'},
                        { headerText: getText("KNR002_98"), key: 'inputRange', dataType: 'string', width: '240px'}
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
                $('#single-list_container').focus();
                // nts.uk.ui.errors.clearAll();
                nts.uk.ui.errors.clearAll();
                $(window.parent.document).find(".ui-dialog-buttonset button").trigger("click");
                nts.uk.ui.windows.close();
            }

            private loadData(data: any) {
                const vm = this;
                blockUI.invisible();

                
                // line 1
                vm.empInfoTerCode(data.empInfoTerCode);
                vm.empInfoTerName(data.empInfoTerName);
                vm.displayFlag(data.displayFlag);
                vm.totalRegisteredTer = data.totalRegisteredTer;

                service.getAll(data.empInfoTerCode)
                .done((res: RemoteSettingsDto) => {
                    if (res) {
                        if (!_.isNull(res.listSelectionItemsDto)) {
                            res.listSelectionItemsDto.sort((item1: any, item2: any) => { return item1.majorNo - item2.majorNo; });
                        }

                        if (!_.isNull(res.listContentToSendDto)) {
                            res.listContentToSendDto.forEach((item: ContentToSendDto) => {
                                let settingValue = new SettingValue(Math.random(), item.majorNo, item.majorClassification, item.smallNo, item.smallClassification, item.updateValue, item.inputRange, item.variableName);
                                vm.settingData.push(settingValue);
                            });
                        }
                        
                        vm.loadSettingGrid();
                        vm.dataSource(res.listSelectionItemsDto);
                        vm.bigItemData(_.uniqBy(vm.dataSource(), (item) => item.majorClassification));
                        if (vm.bigItemData().length > 0) {
                            vm.currentCode1(vm.bigItemData()[0].majorClassification);
                        }
                    }     
                })
                .fail(res => {})
                .always(() => blockUI.clear());
            } 

            private validateErr() {
                const vm = this;

                nts.uk.ui.errors.clearAll();

                switch(vm.inputMode()) {
                    case INPUT_TYPE.LETTER:
                    case INPUT_TYPE.LETTER2: 
                        $('#C6_5').ntsError("check");
                        break;
                    case INPUT_TYPE.TIME:  
                        $('#C7_7').ntsError("check");
                        break; 
                    case INPUT_TYPE.IP:
                        $('#C8_5').ntsError("check");
                        $('#C8_6').ntsError("check");
                        $('#C8_7').ntsError("check");
                        $('#C8_8').ntsError("check");
                        if (!vm.checkIpAddress(vm.ipAddress1()) || !vm.checkIpAddress(vm.ipAddress2()) || !vm.checkIpAddress(vm.ipAddress3()) || !vm.checkIpAddress(vm.ipAddress4())) {
                            nts.uk.ui.dialog.error({messageId: "Msg_2036"})
                        }
                        break;     
                }
            }

            private checkIpAddress(ipAddress: number): boolean {
                if(_.isNil(ipAddress) || ipAddress.toString().length == 0){
                    return false;    
                }
                return true;
            }
            private loadSmallGrid(majorName: string) {
                const vm = this;

                if (vm.dataSource()) {
                    let smallData = vm.dataSource().filter((item) => item.majorClassification == majorName).sort((item1: any, item2: any) => { return item1.smallNo - item2.smallNo; });
                    vm.smallItemData(smallData);
                    vm.currentCode2(vm.smallItemData()[0].smallClassification);
                }

            }

            private setInputMode(inputType: number) {
                const vm = this;

                vm.inputModeValue = inputType;

                switch (inputType) {
                    case 0:
                    case 1:
                    case 2:
                        vm.inputMode(INPUT_TYPE.LETTER);
                        break;
                    case 3:
                    case 6:
                        vm.inputMode(INPUT_TYPE.LETTER2);
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
            id: any;
            majorNo: number;
            majorName: string;
            smallNo: number;
            smallName: string;
            updateValue: string;
            inputRange: string;
            selectedIndex?: string;
            variableName: string;
            
            constructor(id: number, majorNo: number, majorName: string, smallNo: number, smallName: string, updateValue: string, inputRange: string, variableName: string, selectedIndex?: string) {
                const vm = this;
                vm.id = id;
                vm.majorNo = majorNo;
                vm.majorName = majorName;
                vm.smallNo = smallNo;
                vm.smallName = smallName;
                vm.updateValue = updateValue;
                vm.inputRange = inputRange;
                vm.variableName = variableName;
                vm.selectedIndex = selectedIndex;         
            }
        }
        class TimeRecordSetUpdateDto {
            variableName: string;
            updateValue?: string;

            constructor(variableName: string, updateValue?: string) {
                const vm = this;
                vm.variableName = variableName;
                vm.updateValue = updateValue;
            }
        }

        interface UpdateRemoteInput {
            listEmpTerminalCode: Array<String>;
        }
        interface SelectionItemsDto {
            majorNo: number;
            majorClassification: string;
            smallNo: number;
            smallClassification: string;
            variableName: string;
            inputType: number;
            numberOfDigits: number;
            settingValue: string;
            inputRange: string;
            currentValue: string;
            updateValue: string;
            empInfoTerName: string;
            romVersion: string;
            modelEmpInfoTer: number;
        }

        interface ContentToSendDto {
            majorNo: number;
            majorClassification: string;
            smallNo: number;
            smallClassification: string;
            variableName: string;
            inputRange: string;
            updateValue: string;
        }

        interface RemoteSettingsDto {
            listSelectionItemsDto: Array<SelectionItemsDto>,
            listContentToSendDto: Array<ContentToSendDto>
        }

        enum INPUT_TYPE {
            LETTER = 0,
            TIME = 1,
            IP = 2,
            SELECTION = 3,
            CHECK = 4,
            LETTER2 = 5
        }
    }
}