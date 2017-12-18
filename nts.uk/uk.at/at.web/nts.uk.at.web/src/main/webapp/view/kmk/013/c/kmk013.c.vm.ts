module nts.uk.at.view.kmk013.c {
    export module viewmodel {
        export class ScreenModel {
            selectedValueC23: KnockoutObservable<any>;
            inline: KnockoutObservable<boolean>;
            //c3
            roundingC34: KnockoutObservableArray<any>;
            selectedC34: any;
            roundingC38: KnockoutObservableArray<any>;
            selectedC38: any;
            roundingC312: KnockoutObservableArray<any>;
            selectedC312: any;
            //c4
            roundingC44: KnockoutObservableArray<any>;
            selectedC44: any;
            roundingC48: KnockoutObservableArray<any>;
            selectedC48: any;
            roundingC412: KnockoutObservableArray<any>;
            selectedC412: any;
            //c5
            roundingC54: KnockoutObservableArray<any>;
            selectedC54: any;
            roundingC58: KnockoutObservableArray<any>;
            selectedC58: any;
            roundingC512: KnockoutObservableArray<any>;
            selectedC512: any;
            //c6
            roundingC64: KnockoutObservableArray<any>;
            selectedC64: any;
            roundingC68: KnockoutObservableArray<any>;
            selectedC68: any;
            roundingC612: KnockoutObservableArray<any>;
            selectedC612: any;
            //tab
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            //
            comboItems = [new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給2')];
            comboColumns = [{ prop: 'name', length: 4 }];
            constructor() {
                var self = this;
                self.selectedValueC23 = ko.observable('1');
                self.inline = ko.observable(true);
                //c3
                self.roundingC34 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_96') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_97') },
                ]);
                self.selectedC34 = ko.observable(1);
                self.roundingC38 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_100') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_101') },
                ]);
                self.selectedC38 = ko.observable(1);
                self.roundingC312 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_104') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_105') },
                ]);
                self.selectedC312 = ko.observable(1);
                //c4
                self.roundingC44 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_109') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_110') },
                ]);
                self.selectedC44 = ko.observable(1);
                self.roundingC48 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_113') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_114') },
                ]);
                self.selectedC48 = ko.observable(1);
                self.roundingC412 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_117') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_118') },
                ]);
                self.selectedC412 = ko.observable(1);
                //c5
                self.roundingC54 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_122') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_123') },
                ]);
                self.selectedC54 = ko.observable(1);
                self.roundingC58 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_126') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_127') },
                ]);
                self.selectedC58 = ko.observable(1);
                self.roundingC512 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_130') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_131') },
                ]);
                self.selectedC512 = ko.observable(1);
                //c6
                self.roundingC64 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_135') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_136') },
                ]);
                self.selectedC64 = ko.observable(1);
                self.roundingC68 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_139') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_140') },
                ]);
                self.selectedC68 = ko.observable(1);
                self.roundingC612 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_143') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_144') },
                ]);
                self.selectedC612 = ko.observable(1);
                //tab
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK013_145"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK013_146"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK013_147"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initTabC8();
                 self.initTabC9();
                 self.initTabC10();
                dfd.resolve();
                return dfd.promise();
            }
            initTabC8(): void {
                var self = this;
                var items = [new GridItem(1)];
                $("#grid8").ntsGrid({
                    height: '500px',
                    dataSource: items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText:nts.uk.resource.getText('KMK013_148'), key: 'col1', dataType: 'string', width: '230px' },
                        { headerText: nts.uk.resource.getText('KMK013_149'), key: 'col2', dataType: 'string', width: '150', ntsControl: 'Combobox1' },
                        { headerText: nts.uk.resource.getText('KMK013_150'), key: 'col3', dataType: 'string', width: '150px', ntsControl: 'Combobox2' },
                        { headerText: nts.uk.resource.getText('KMK013_151'), key: 'col4', dataType: 'string', width: '150px', ntsControl: 'Combobox3' },
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],
                    ntsControls: [{ name: 'Combobox1', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox2', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox3', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true }]
                });
            }
            initTabC9(): void {
                var self = this;
                var items = [new GridItem(1)];
                $("#grid9").ntsGrid({
                    height: '500px',
                    dataSource: items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText:nts.uk.resource.getText('KMK013_156'), key: 'col1', dataType: 'string', width: '230px' },
                        { headerText: '', key: 'col2', dataType: 'string', width: '150', ntsControl: 'Combobox1' },
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],
                    ntsControls: [{ name: 'Combobox1', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox2', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox3', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true }]
                });
            }
            initTabC10(): void {
                var self = this;
                var items = [new GridItem(1)];
                $("#grid10").ntsGrid({
                    height: '500px',
                    dataSource: items,
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText:nts.uk.resource.getText('KMK013_159'), key: 'col1', dataType: 'string', width: '230px' },
                        { headerText: nts.uk.resource.getText('KMK013_160'), key: 'col2', dataType: 'string', width: '150', ntsControl: 'Combobox1' },
                        { headerText: nts.uk.resource.getText('KMK013_161'), key: 'col3', dataType: 'string', width: '150px', ntsControl: 'Combobox2' },
                        { headerText: nts.uk.resource.getText('KMK013_162'), key: 'col4', dataType: 'string', width: '150px', ntsControl: 'Combobox3' },
                    ],
                    features: [{ name: 'Sorting', type: 'local' }],
                    ntsControls: [{ name: 'Combobox1', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox2', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true },
                        { name: 'Combobox3', options: self.comboItems, optionsValue: 'code', optionsText: 'name', columns: self.comboColumns, controlType: 'ComboBox', enable: true }]
                });
            }

        }
        class ItemModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        class GridItem {
            col1: string;
            col2: string;
            col3: string;
            col4: string;
            constructor(index: number) {
                this.col1 = 'row' ;
                this.col2 = String(index % 3 + 1);
                this.col3 = String(index % 3 + 1);
                this.col4 = String(index % 3 + 1);
            }
        }
    }
}