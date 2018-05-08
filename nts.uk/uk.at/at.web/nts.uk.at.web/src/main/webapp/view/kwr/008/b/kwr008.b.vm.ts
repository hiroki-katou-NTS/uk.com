module nts.uk.at.view.kwr008.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kwr008.share.model;
    
    export class ScreenModel {
        //enum mode
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);

        //enum value output format
        valueOutputFormat: KnockoutObservableArray<any> = ko.observableArray([]);

        //B5_1
        excessTime: KnockoutObservable<boolean> = ko.observable(false);

        //B2_2
        listStandardImportSetting: KnockoutObservableArray<model.OutputSettingCodeDto> = ko.observableArray([]);
        selectedCode: KnockoutObservable<any> = ko.observable();

        //B3_2 B3_3
        inputSettingCode: KnockoutObservable<string> = ko.observable('');
        inputProjectName: KnockoutObservable<string> = ko.observable('');

        //B5_3
        itemRadio: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedItemRadio: KnockoutObservable<number> = ko.observable(0);

        //B4
        outputItem: KnockoutObservableArray<any> = ko.observableArray([]);


        constructor() {
            var self = this;

            //B5_3
            self.itemRadio = ko.observableArray([
                new model.ItemModel(0, getText('KWR008_37')),
                new model.ItemModel(1, getText('KWR008_38')),
                new model.ItemModel(2, getText('KWR008_39'))
            ]);
            
            //table fixed
            $('#fixed-table').ntsFixedTable({ height: 304, width: 900 });
        }

        public startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            
            block.invisible();

            //fill data B2_2
            service.getOutItemSettingCode().done((data) => {
                for (let i = 0, count = data.length; i < count; i++) {
                    self.listStandardImportSetting.push(new setOutputSettingCode(data[i].cd, data[i].name, data[i].outNumExceedTime36Agr, data[i].displayFormat));
                }
                
                self.listStandardImportSetting.sort((a, b) => {
                    return (a.cd === b.cd) ? 0 : (a.cd < b.cd) ? -1 : 1;
                });

                self.checkListItemOutput();
            });

            service.getValueOutputFormat().then(data => {
                for (let i = 0, count = data.length; i < count; i++) {
                    self.valueOutputFormat.push(new model.ItemModel(data[i].value, data[i].localizedName));
                }

                $('#output-item').ntsGrid({
                    width: '685px',
                    height: '320px',
                    dataSource: self.outputItem(),
                    primaryKey: 'cd',
                    hidePrimaryKey: true,
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'cd', dataType: 'number', ntsControl: 'Label' },
                        { headerText: '', key: 'useClassification', dataType: 'boolean', width: '35px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                        { headerText: getText('KWR008_28'), key: 'headingName', dataType: 'string', width: '160px', ntsControl: 'TextEditor' },
                        { headerText: '', key: 'open', dataType: 'string', width: '55px', unbound: true, ntsControl: 'Button' },
                        { headerText: getText('KWR008_30'), key: 'valueOutputFormat', dataType: 'string', width: '195px', ntsControl: 'Combobox' },
                        { headerText: getText('KWR008_29'), key: 'outputTargetItem', dataType: 'string', width: '220px' }
                    ],
                    features: [
                        { name: 'Resizing', columnSettings: [
                            { columnKey: 'cd', allowResizing: false, minimumWidth: 0 },
                            { columnKey: 'useClassification', allowResizing: false, minimumWidth: 0 },
                            { columnKey: 'headingName', allowResizing: false, minimumWidth: 0 },
                            { columnKey: 'open', allowResizing: false, minimumWidth: 0 },
                            { columnKey: 'valueOutputFormat', allowResizing: false, minimumWidth: 0 },
                            { columnKey: 'outputTargetItem', allowResizing: false, minimumWidth: 0 }
                        ] }
                    ],
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1 }, optionsValue: 'value', controlType: 'CheckBox', enable: true },
                        { name: 'TextEditor', value: 'headingName', controlType: 'TextEditor', constraint: { valueType: 'String' } },
                        { name: 'Button', text: getText('KWR008_34'), click: data => { self.openKDW007(data) }, controlType: 'Button' },
                        { name: 'Combobox', options: self.valueOutputFormat, optionsValue: 'code', optionsText: 'name', columns: [{ prop: 'name', length: 3 }], controlType: 'ComboBox', enable: true },
                    ]
                });

            });

            //event select change
            self.selectedCode.subscribe((data) => {
                self.outputItem.removeAll();
                //service.getListItemOutput(data).done(r => {
                //    self.outputItem.push(new OutputItemData(r.cd, r.useClass, r.headingName, r.valOutFormat, ''));
               // });
                nts.uk.ui.errors.clearAll()
                self.listStandardImportSetting.sort((a, b) => {
                    return (a.cd === b.cd) ? 0 : (a.cd < b.cd) ? -1 : 1;
                });
                self.updateMode(data);
            });
            
            block.clear();

            dfd.resolve(self);
            return dfd.promise();
        }

        //Open dialog KDW007
        openKDW007(data) {
            var self = this;

            console.log(data);
        }


        checkListItemOutput() {
            var self = this;

            if (self.listStandardImportSetting().length == 0) {
                self.screenMode(model.SCREEN_MODE.NEW);
                self.registerMode();
            } else {
                self.screenMode(model.SCREEN_MODE.UPDATE);
                self.selectedCode(self.listStandardImportSetting()[0].cd);
                self.updateMode(self.listStandardImportSetting()[0].cd);
                $('#B3_2').attr('disabled', 'disabled');
            }
        }

        //mode update
        updateMode(data: string) {
            var self = this;

            let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd == data; });

            self.screenMode(model.SCREEN_MODE.UPDATE);

            //B3_2
            self.inputSettingCode(self.listStandardImportSetting()[selectedIndex].cd + '');

            //B3_3
            self.inputProjectName(self.listStandardImportSetting()[selectedIndex].name);

            //B5_1
            self.excessTime(self.listStandardImportSetting()[selectedIndex].outNumExceedTime36Agr);

            //B5_2
            self.selectedItemRadio(+self.listStandardImportSetting()[selectedIndex].displayFormat);

            $('#B3_2').attr('disabled', 'disabled');

            $('#B3_3').focus();

            self.listStandardImportSetting.sort();

            self.selectedCode(self.listStandardImportSetting()[selectedIndex].cd);
            
        }


        //mode register
        registerMode() {
            var self = this;

            self.screenMode(model.SCREEN_MODE.NEW);

            $("#B3_2").removeAttr("disabled");

            $("#B3_2").focus();

            $('#listStandardImportSetting').ntsGridList('deselectAll');

            //B3_2
            self.inputSettingCode('');

            //B3_3
            self.inputProjectName('');

            //B5_1
            self.excessTime(false);

            //B5_2
            self.selectedItemRadio(0);
            
            for (var i = 1; i <= 10; i++) {
                self.outputItem.push(new OutputItemData(i, false, '', 0, ''));
            }
        }

        //do register
        doRegister() {
            var self = this;
            
            let itemOut : any = _.filter(self.outputItem(), v=>{return v.headingName.trim() != '';});
            
//            if(itemOut.length == 0){
//                $('#output-item').ntsError('set', {messageId:"Msg_881"});
//            }
            
            let data : model.OutputSettingCodeDto = new setOutputSettingCode(
                self.inputSettingCode(),
                self.inputProjectName(),
                (self.excessTime()) ? 1 : 0,
                self.selectedItemRadio(),
                itemOut
            );

            
            if(nts.uk.ui.errors.hasError())
                return;

            if (self.screenMode() == model.SCREEN_MODE.NEW) {
                service.registerOutputItemSetting(data).done(() => {
                    self.listStandardImportSetting.push(data);
                    self.updateMode(self.inputSettingCode());
                    info({ messageId: 'Msg_15' });
                }).fail(err=>{
                    $('#B3_2').ntsError('set', err);
                })
            } else {
                service.updateOutputItemSetting(data).done(() => {
                    let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd == self.selectedCode(); });
                    self.listStandardImportSetting.replace(self.listStandardImportSetting()[selectedIndex], data);
                    info({ messageId: 'Msg_15' });
                });
            }


        }

        //do delete
        doDelete() {
            var self = this;

            confirm({ messageId: 'Msg_18' }).ifYes(() => {

                let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd == self.selectedCode(); });

                let data = ko.toJS(self.listStandardImportSetting()[selectedIndex]);
                // send request remove item
                service.deleteOutputItemSetting(data).done(() => {
                    self.checkListItemOutput();
                    info({ messageId: 'Msg_16' });
                    if (selectedIndex + 1 == self.listStandardImportSetting().length) {
                        self.selectedCode(self.listStandardImportSetting()[selectedIndex - 1].cd);
                    } else {
                        self.selectedCode(self.listStandardImportSetting()[selectedIndex + 1].cd);
                    }
                    self.listStandardImportSetting.splice(selectedIndex, 1);
                })

            })
        }

        //cancel register
        doCancel() {
            nts.uk.request.jump("/view/kwr/008/a/index.xhtml");
        }


    }

    class OutputItemData {
        cd: number;
        useClassification: boolean;
        headingName: string;
        valueOutputFormat: number;
        outputTargetItem: string;
        constructor(cd: number, useClassification: boolean, headingName: string, valueOutputFormat: number, outputTargetItem: string) {
            this.cd = cd;
            this.useClassification = useClassification;
            this.headingName = headingName;
            this.valueOutputFormat = valueOutputFormat;
            this.outputTargetItem = outputTargetItem;
        }
    }
    
    export class setOutputSettingCode implements model.OutputSettingCodeDto {
        cd: string;
        name: string;
        outNumExceedTime36Agr: number;
        displayFormat: number;
        listItemOutput : OutputItemData;


        constructor(cd: string, name: string, outNumExceedTime36Agr: number, displayFormat: number, listItemOutput : OutputItemData) {
            this.cd = cd;
            this.name = name;
            this.outNumExceedTime36Agr = outNumExceedTime36Agr;
            this.displayFormat = displayFormat;
            this.listItemOutput = listItemOutput;
        }
    }
}
