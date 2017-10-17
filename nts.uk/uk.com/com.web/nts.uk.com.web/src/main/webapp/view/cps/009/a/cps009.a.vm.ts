module nts.uk.com.view.cps009.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ViewModel {
        initValSettingLst: KnockoutObservableArray<any> = ko.observableArray([]);
        itemList: KnockoutObservableArray<any> = ko.observableArray([]);
        initValueList: KnockoutObservableArray<any> = ko.observableArray([]);
        initSettingId: KnockoutObservable<string> = ko.observable('');
        ctgColums: KnockoutObservableArray<any>;
        itemValueLst: KnockoutObservableArray<any>;
        selectionColumns: any;
        currentCategory: KnockoutObservable<InitValueSettingDetail>;
        comboItems: any;
        comboColumns: any;
        isUpdate: boolean = false;
        constructor() {

            let self = this;

            self.initValue();
            self.start();

            self.initSettingId.subscribe(function(value: string) {
                service.getAllCtg(value).done((data: any) => {
                    self.currentCategory().setData({
                        settingCode: data.settingCode,
                        settingName: data.settingName,
                        ctgList: data.ctgList
                    });
                    self.currentCategory.valueHasMutated();
                });
            });

        }

        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getAll().done((data: Array<any>) => {
                if (data.length > 0) {
                    self.isUpdate = true;
                    self.initValSettingLst.removeAll();
                    self.initValSettingLst(data);
                    self.initSettingId(self.initValSettingLst()[0].settingId);

                } else {
                    self.isUpdate = false;
                    self.openDDialog();
                    self.refresh();

                }
            });
            return dfd.promise();
        }

        refresh() {
            let self = this;

            service.getAll().done((data: Array<any>) => {
                self.initValSettingLst.removeAll();
                self.initValSettingLst(data);
                self.initSettingId(self.initValSettingLst()[0].settingId);

            });;
        }

        initValue() {
            let self = this;

            self.ctgColums = ko.observableArray([
                { headerText: 'settingId', key: 'settingId', width: 100, hidden: true },
                { headerText: text('CPS009_10'), key: 'settingCode', width: 80 },
                { headerText: text('CPS006_11'), key: 'settingName', width: 160 }
            ]);

            self.itemValueLst = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給2')]);

            self.selectionColumns = [{ prop: 'id', length: 4 },
                { prop: 'itemName', length: 8 }];

            self.currentCategory = ko.observable(new InitValueSettingDetail({
                settingCode: '', settingName: '', ctgList: [], itemList: []
            }));

            self.comboItems = [new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給2')];

            self.comboColumns = [{ prop: 'code', length: 4 },
                { prop: 'name', length: 8 }];

        }

        // thiet lap item hang loat
        openBDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());
            block.invisible();
            modal('/view/cps/009/b/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }

        // copy initVal
        openCDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());

            block.invisible();

            modal('/view/cps/009/c/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }


        // new initVal
        openDDialog() {

            let self = this;

            setShared('categoryInfo', self.currentCategory());

            block.invisible();

            modal('/view/cps/009/d/index.xhtml', { title: '' }).onClosed(function(): any {

                block.clear();
            });

        }
    }


    export class InitValueSettingDetail {
        settingCode: KnockoutObservable<string>;
        settingName: KnockoutObservable<string>;
        ctgList: KnockoutObservableArray<any>;
        currentItemId: KnockoutObservable<string> = ko.observable('');
        ctgColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'perInfoCtgId', width: 100, hidden: true },
            { headerText: text('CPS009_15'), key: 'setting', dataType: 'string', width: 50, formatter: makeIcon },
            { headerText: text('CPS009_16'), key: 'categoryName', width: 200 }
        ]);
        itemList: Array<any>;
        constructor(params: IInitValueSettingDetail) {
            this.settingCode = ko.observable(params.settingCode);
            this.settingName = ko.observable(params.settingName);
            this.ctgList = ko.observableArray(params.ctgList);
            this.itemList = params.itemList || [];

            this.currentItemId.subscribe(function(value: string) {
                if (value) {
                    service.getAllItemByCtgId(value).done((item: Array<IPerInfoInitValueSettingItemDto>) => {
                        if (item.length > 0) {
                            let itemConvert = _.map(item, function(obj: IPerInfoInitValueSettingItemDto) {
                                if (obj.refMethodType === 2) {
                                    if (obj.saveDataType === 1) {
                                        obj.value = obj.stringValue;
                                    }
                                    else if (obj.saveDataType === 2) {
                                        obj.value = obj.intValue;
                                    }
                                    else if (obj.saveDataType === 3) {
                                        obj.value = obj.dateValue;
                                    }
                                } else {
                                    obj.value = "";

                                }
                                return obj;

                            })
                            $("#grid2").igGrid("option", "dataSource", itemConvert);
                        }
                    })
                }




            });
        }

        setData(params: IInitValueSettingDetail) {
            this.settingCode(params.settingCode);
            this.settingName(params.settingName);
            this.ctgList(params.ctgList);
            if (this.ctgList().length > 0) {
                this.currentItemId(params.ctgList[0].perInfoCtgId);
            } else {
                this.currentItemId('');
            }
        }
    }


    // obj list bên trái
    export interface IInitValueSetting {
        companyId?: string;
        settingId: string;
        settingCode: string;
        settingName: string;
    }

    export class InitValueSetting {
        companyId: string;
        settingId: string;
        settingCode: string;
        settingName: string;
        constructor(params: IInitValueSetting) {
            this.settingId = params.settingId;
            this.settingCode = params.settingCode;
            this.settingName = params.settingName;
        }

    }

    export interface ICategoryInfo {
        perInfoCtgId: string;
        categoryName: string;
        setting: boolean;
    }

    export class CategoryInfo {
        perInfoCtgId: string;
        categoryName: string;
        setting: boolean;
        constructor(params: ICategoryInfo) {
            this.perInfoCtgId = params.perInfoCtgId;
            this.categoryName = params.categoryName;
            this.setting = params.setting;
        }
    }

    export interface IInitValue {
        id: string;
        itemName: string;
        comboxValue: string;
        value: string;
    }

    export class InitValue {
        id: string;
        itemName: string;
        comboxValue: string;
        value: string;
        constructor(params: IInitValue) {
            this.id = params.id;
            this.itemName = params.itemName;
            this.comboxValue = params.comboxValue;
            this.value = params.value;
        }
    }

    export interface IInitValueSettingDetail {
        settingCode: string;
        settingName: string;
        ctgList?: Array<any>;
        itemList?: Array<any>;
    }

    function makeIcon(value, row) {
        if (value == "false")
            return '';
        return '<i class=\"icon icon-dot\"></i>';
    }

    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export interface IPerInfoInitValueSettingItemDto {
        perInfoItemDefId: string;
        settingId?: string;
        perInfoCtgId: string;
        itemName: string;
        isRequired: number;
        refMethodType: number;
        saveDataType: number;
        stringValue?: string;
        intValue?: number;
        dateValue?: Date;
        value? any;
    }

    export class PerInfoInitValueSettingItemDto {
        perInfoItemDefId: string;
        settingId: string;
        perInfoCtgId: string;
        itemName: string;
        isRequired: number;
        refMethodType: number;
        saveDataType: number;
        stringValue: string;
        intValue: number;
        dateValue: Date;
        value: any = "";
        constructor(params: IPerInfoInitValueSettingItemDto) {
            let self = this;
            self.perInfoItemDefId = params.perInfoItemDefId || "";
            self.settingId = params.settingId || "";
            self.perInfoCtgId = params.perInfoCtgId || "";
            self.itemName = params.itemName || "";
            self.isRequired = params.isRequired || 0;
            self.refMethodType = params.refMethodType || 0;
            self.saveDataType = params.saveDataType || 0;
            self.stringValue = params.stringValue || "";
            self.intValue = params.intValue || 0;
            self.dateValue = params.dateValue || new Date("9999-12-21");
            if (params.refMethodType === 1) {
                self.value = params.stringValue;
            } else if (params.refMethodType === 2) {
                self.value = params.intValue;
            } else if (params.refMethodType === 3) {
                self.value = params.dateValue;
            }
        }
    }


}