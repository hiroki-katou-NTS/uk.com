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
        items = _(new Array(10)).map((x, i) => new GridItem(i + 1)).value();
        constructor() {

            let self = this;

            self.initValue();
            self.start();

            self.initSettingId.subscribe(function(value: string) {
                service.getAllCtg().done((data: Array<any>) => {
                    self.currentCategory().setData({
                        settingCode: value,
                        settingName: value,
                        itemList: data
                    });
                    if (data.length > 0) {
                        service.getAllItemByCtgId(data[0].perInfoCtgId).done((item: Array<any>) => {
                            console.log(item);

                        })
                    }

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
                settingCode: '', settingName: '', itemList: []
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
        itemList: KnockoutObservableArray<any>;
        currentItemId: KnockoutObservable<string> = ko.observable('');
        itemColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: 'id', key: 'perInfoCtgId', width: 100, hidden: true },
            { headerText: text('CPS009_15'), key: 'setting', dataType: 'string', width: 50, formatter: makeIcon },
            { headerText: text('CPS009_16'), key: 'categoryName', width: 200 }
        ]);
        constructor(params: IInitValueSettingDetail) {
            this.settingCode = ko.observable(params.settingCode);
            this.settingName = ko.observable(params.settingName);
            this.itemList = ko.observableArray(params.itemList);
        }

        setData(params: IInitValueSettingDetail) {
            this.settingCode(params.settingCode);
            this.settingName(params.settingName);
            this.itemList(params.itemList);
            if (this.itemList().length > 0) {
                this.currentItemId(params.itemList[0].perInfoCtgId);
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
        itemList?: Array<any>;
    }


    class GridItem {
        id: number;
        flag: boolean;
        ruleCode: string;
        combo: string;
        text1: string;
        constructor(index: number) {
            this.id = index;
            this.flag = index % 2 == 0;
            this.ruleCode = String(index % 3 + 1);
            this.combo = String(index % 3 + 1);
            this.text1 = "TEXT";
        }
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


}