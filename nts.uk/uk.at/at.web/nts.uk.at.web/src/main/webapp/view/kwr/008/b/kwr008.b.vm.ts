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
    //    import service = nts.uk.at.view.kwr008.b.servic    
    export class ScreenModel {
        //        systemTypes: KnockoutObservableArray<model.BoxModel> = ko.observableArray([]);
        //        systemType: KnockoutObsermber>;

        //enum mode
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);

        //enum value output format
        valueOutputFormat: KnockoutObservableArray<any>;

        //B5_1
        excessTime: KnockoutObservable<boolean>;

        //B2_2
        listStandardImportSetting: KnockoutObservableArray<model.OutputSettingCodeDto>;
        selectedCode: KnockoutObservable<any>;

        //B3_2 B3_3
        inputSettingCode: KnockoutObservable<string>;
        inputProjectName: KnockoutObservable<string>;

        //B5_3
        itemRadio: KnockoutObservableArray<any>;
        selectedItemRadio: KnockoutObservable<number>;

        //B4
        outputItem: KnockoutObservableArray<any>;


        constructor() {
            var self = this;
            //init enum
            this.valueOutputFormat = ko.observableArray([]);

            //B2_2
            self.listStandardImportSetting = ko.observableArray([]);
            self.selectedCode = ko.observable();

            //B5_1
            self.excessTime = ko.observable(0);

            //B3_2 B3_3
            self.inputSettingCode = ko.observable('');
            self.inputProjectName = ko.observable('');

            //B5_3
            self.itemRadio = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_37')),
                new model.ItemModel(1, getText('CMF001_38')),
                new model.ItemModel(2, getText('CMF001_39'))
            ]);
            self.selectedItemRadio = ko.observable(-1);

            //B4
            self.outputItem = ko.observableArray([]);

            //table fixed
            $('#fixed-table').ntsFixedTable({ height: 304, width: 900 });
        }

        public startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            //fill data B2_2
            service.getOutItemSettingCode().done((data) => {
                for (let i = 0, count = data.length; i < count; i++) {
                    self.listStandardImportSetting.push(new model.setOutputSettingCode(data[i].cd, data[i].name, data[i].outNumExceedTime36Agr, data[i].displayFormat));
                }
            });

            //fill data B3
            self.outputItem([
                new OutputItemData(1, true, 'a', 1, 'a'),
                new OutputItemData(2, false, 'b', 0, 'b')
            ]);

            service.getValueOutputFormat().then(data => {
                for (let i = 0, count = data.length; i < count; i++) {
                    self.valueOutputFormat().push(new model.ItemModel(data[i].value, data[i].localizedName));
                }

                $('#output-item').ntsGrid({
                    width: '735px',
                    height: '400px',
                    dataSource: self.outputItem(),
                    primaryKey: 'id',
                    hidePrimaryKey: true,
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                        { headerText: '', key: 'useClassification', dataType: 'boolean', width: '35px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                        { headerText: getText('CMF001_27'), key: 'headingName', dataType: 'string', width: '160px' },
                        { headerText: '', key: 'open', dataType: 'string', width: '55px', unbound: true, ntsControl: 'Button' },
                        { headerText: getText('CMF001_30'), key: 'valueOutputFormat', dataType: 'string', width: '205px', ntsControl: 'Combobox', tabIndex: 0 },
                        { headerText: getText('CMF001_29'), key: 'outputTargetItem', dataType: 'string', width: '260px' }
                    ],
                    features: [],
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1 }, optionsValue: 'value', controlType: 'CheckBox', enable: true },
                        { name: 'Button', text: getText('CMF001_34'), click: data => { self.openKDW007(data) }, controlType: 'Button' },
                        { name: 'Combobox', options: self.valueOutputFormat, optionsValue: 'code', optionsText: 'name', columns: [{ prop: 'name', length: 1 }], controlType: 'ComboBox', enable: true },
                    ]
                });

            });

            //event select change
            self.selectedCode.subscribe((data) => {
                self.updateMode(data);
            });

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
        updateMode(data: number) {
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
            self.selectedItemRadio(self.listStandardImportSetting()[selectedIndex].displayFormat);

            $('#B3_2').attr('disabled', 'disabled');
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
            self.excessTime(0);

            //B5_2
            self.selectedItemRadio(-1);
        }

        //do register
        doRegister() {
            var self = this;
            let data: model.OutputSettingCodeDto = new model.setOutputSettingCode(
                +self.inputSettingCode(),
                self.inputProjectName(),
                (self.excessTime()) ? 1 : 0,
                self.selectedItemRadio()
            );

            service.registerOutputItemSetting(data).done(() => {
                self.listStandardImportSetting.push(data);
                self.updateMode(+self.inputSettingCode());
            })
        }

        //do delete
        doDelete() {
            var self = this;

            confirm({ messageId: 'Msg_18' }).ifYes(() => {

                let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.cd == self.selectedCode(); });

                info({ messageId: 'Msg_35' }).then(() => {
                    let data = ko.toJS(self.listStandardImportSetting()[selectedIndex]);
                    // send request remove item
                    service.deleteOutputItemSetting(data).done(() => {
                        self.checkListItemOutput();
                        info({ messageId: 'Msg_16' });
                        self.listStandardImportSetting.splice(selectedIndex, 1);
                    })
                });
            }).ifNo(() => {
                info({ messageId: 'Msg_36' });
            });
        }

        //cancel register
        doCancel() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.UPDATE);
            self.selectedCode(self.listStandardImportSetting()[0].cd);
            self.updateMode(self.listStandardImportSetting()[0].cd);
        }


    }

    class OutputItemData {
        id: number;
        useClassification: boolean;
        headingName: string;
        valueOutputFormat: number;
        outputTargetItem: string;
        constructor(id: number, useClassification: boolean, headingName: string, valueOutputFormat: number, outputTargetItem: string) {
            this.id = id;
            this.useClassification = useClassification;
            this.headingName = headingName;
            this.valueOutputFormat = valueOutputFormat;
            this.outputTargetItem = outputTargetItem;
        }
    }
}
