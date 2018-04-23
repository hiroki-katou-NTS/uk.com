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
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;


        //B2_2
        listStandardImportSetting: KnockoutObservableArray<model.ItemModel>;
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
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);

            //B3_2 B3_3
            self.inputSettingCode = ko.observable('');
            self.inputProjectName = ko.observable('');

            //B5_3
            self.itemRadio = ko.observableArray([
                { id: 0, name: getText('CMF001_37') },
                { id: 1, name: getText('CMF001_38') },
                { id: 2, name: getText('CMF001_39') },
            ]);
            self.selectedItemRadio = ko.observable(1);

            //B4
            self.outputItem = ko.observableArray([]);

            //table fixed
            $('#fixed-table').ntsFixedTable({ height: 304, width: 900 });
        }

        public startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();

            //fill data B2_2
            self.listStandardImportSetting([
                new model.ItemModel(2, 'a'),
                new model.ItemModel(3, 'b'),
                new model.ItemModel(4, 'c'),
                new model.ItemModel(5, 'd'),
                new model.ItemModel(6, 'e'),
                new model.ItemModel(7, 'f'),
                new model.ItemModel(8, 'g'),
                new model.ItemModel(9, 'h'),
                new model.ItemModel(10, 'i'),
                new model.ItemModel(11, 'k'),
                new model.ItemModel(12, 'l'),
                new model.ItemModel(13, 'm'),
                new model.ItemModel(14, 'n')
            ]);

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
                        { headerText: '', key: 'useClassification', dataType: 'boolean', width: '35px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                        { headerText: 'ID', key: 'id', dataType: 'number', width: '50px', ntsControl: 'Label' },
                        { headerText: getText('CMF001_27'), key: 'headingName', dataType: 'string', width: '160px' },
                        { headerText: '', key: 'open', dataType: 'string', width: '55px', unbound: true, ntsControl: 'Button' },
                        { headerText: getText('CMF001_30'), key: 'valueOutputFormat', dataType: 'string', width: '205px', ntsControl: 'Combobox', tabIndex: 0 },
                        { headerText: getText('CMF001_29'), key: 'outputTargetItem', dataType: 'string', width: '260px' }
                    ],
                    features: [],
                    ntsControls: [
                        { name: 'Checkbox', options: { value: 1 }, optionsValue: 'value', controlType: 'CheckBox', enable: true },
                        { name: 'Button', text: getText('CMF001_34'), click: data => {self.openKDW007(data)}, controlType: 'Button' },
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
        openKDW007(data){
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
                self.selectedCode(self.listStandardImportSetting()[0].code);
                self.updateMode(self.listStandardImportSetting()[0].code);
                $('#B3_2').attr('disabled', 'disabled');
            }
        }

        //mode update
        updateMode(data: number) {
            var self = this;

            let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.code == data; });

            self.screenMode(model.SCREEN_MODE.UPDATE);

            //B3_2
            self.inputSettingCode(self.listStandardImportSetting()[selectedIndex].code);

            //B3_3
            self.inputProjectName(self.listStandardImportSetting()[selectedIndex].code);

            $('#B3_2').attr('disabled', 'disabled');
        }


        //mode register
        registerMode() {
            var self = this;

            $('#B3_2').removeAttr('disabled');

            $('#B3_2').focus();

            $('#listStandardImportSetting').ntsGridList('deselectAll');

            //B3_2
            self.inputSettingCode('');

            //B3_3
            self.inputProjectName('');

            self.screenMode(model.SCREEN_MODE.NEW);
        }

        //do register
        doRegister() {
            var self = this;
            self.listStandardImportSetting().push(new model.ItemModel(+self.inputSettingCode(), self.inputProjectName()));
            self.updateMode(+self.inputSettingCode());
        }

        //do delete
        doDelete() {
            var self = this;

            confirm({ messageId: 'Msg_18' }).ifYes(() => {
                // send request remove item
                //call to webservice

                let selectedIndex = _.findIndex(self.listStandardImportSetting(), (obj) => { return obj.code == self.selectedCode(); });

                self.listStandardImportSetting.splice(selectedIndex, 1);

                self.screenMode(model.SCREEN_MODE.NEW);

                info({ messageId: 'Msg_35' });

            }).ifNo(() => {
                self.screenMode(model.SCREEN_MODE.UPDATE);

                info({ messageId: 'Msg_36' });
            });
        }

        //cancel register
        doCancel() {
            var self = this;
            self.screenMode(model.SCREEN_MODE.UPDATE);
            self.selectedCode(self.listStandardImportSetting()[0].code);
            self.updateMode(self.listStandardImportSetting()[0].code);
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
