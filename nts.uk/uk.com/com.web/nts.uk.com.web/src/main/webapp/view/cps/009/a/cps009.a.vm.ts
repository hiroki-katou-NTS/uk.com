module nts.uk.com.view.cps009.a.viewmodel {
    import error = nts.uk.ui.errors;
    import text = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ViewModel {
        initValSettingLst: KnockoutObservableArray<any> = ko.observableArray([]);
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
            self.start(undefined);

            self.initSettingId.subscribe(function(value: string) {
                if (value) {
                    service.getAllCtg(value).done((data: any) => {
                        self.currentCategory().setData({
                            settingCode: data.settingCode,
                            settingName: data.settingName,
                            ctgList: data.ctgList
                        });
                        self.currentCategory.valueHasMutated();
                    });
                    if (self.currentCategory().currentItemId() === undefined || self.currentCategory().currentItemId() === "") {
                        return;
                    } else {
                        self.getItemList(value, self.currentCategory().currentItemId());
                    }
                } else {

                    return;
                }
            });

            self.currentCategory().currentItemId.subscribe(function(value: string) {
                if (value) {
                    self.getItemList(self.initSettingId(), value);
                } else {
                    return;
                }
            });

        }

        // get item list
        getItemList(settingId: string, ctgId: string) {
            let self = this;

            self.currentCategory().itemList.removeAll();
            service.getAllItemByCtgId(settingId, ctgId).done((item: Array<IPerInfoInitValueSettingItemDto>) => {
                if (item.length > 0) {
                    let itemConvert = _.map(item, function(obj: IPerInfoInitValueSettingItemDto) {
                        return new PerInfoInitValueSettingItemDto({
                            perInfoItemDefId: obj.perInfoItemDefId, settingId: obj.settingId,
                            perInfoCtgId: obj.perInfoCtgId, itemName: obj.itemName,
                            isRequired: obj.isRequired, refMethodType: obj.refMethodType,
                            saveDataType: obj.saveDataType, stringValue: obj.stringValue,
                            intValue: obj.intValue, dateValue: obj.dateValue,
                            value: obj.value, itemType: obj.itemType,
                            dataType: obj.dataType
                        });

                    });
                    self.currentCategory().itemList.removeAll();
                    self.currentCategory().itemList(itemConvert);
                    self.currentCategory().itemList.valueHasMutated();
                } else {
                    self.currentCategory().itemList.removeAll();
                    self.currentCategory().itemList([]);
                    self.currentCategory().itemList.valueHasMutated();

                }
            });

            self.currentCategory().itemList.valueHasMutated();

        }

        start(id: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getAll().done((data: Array<IPerInfoInitValueSettingDto>) => {
                if (data.length > 0) {
                    self.isUpdate = true;
                    self.initValSettingLst.removeAll();
                    self.initValSettingLst(data);
                    if (id === undefined) {
                        if (self.initValSettingLst().length > 0) {
                            self.initSettingId(self.initValSettingLst()[0].settingId);
                        } else {
                            self.initSettingId("");
                        }
                    } else {
                        self.initSettingId(id);
                    }

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
                if (self.initValSettingLst().length > 0) {
                    self.initSettingId(self.initValSettingLst()[0].settingId);
                } else {
                    self.initSettingId("");

                }

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

            let self = this,
                PARAMS = { categoryId: self.currentCategory().currentItemId() };
            console.log(PARAMS);

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

            //setShared('categoryInfo', self.currentCategory());

            block.invisible();

            modal('/view/cps/009/d/index.xhtml', { title: '' }).onClosed(function(): any {
                self.start(undefined);
                block.clear();
            });

        }

        //delete init value

        deleteInitValue() {

            let self = this,
                objDelete = {
                    settingId: self.initSettingId(),
                    settingCode: self.currentCategory().settingCode()
                };
            block.invisible();
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteInitVal(objDelete).done(function(data) {
                    dialog.info({ messageId: "Msg_16" }).then(function() {
                        var sourceLength = self.initValSettingLst().length;
                        var i = _.findIndex(self.initValSettingLst(), function(init: IPerInfoInitValueSettingDto) { return init.settingId === self.initSettingId(); });
                        var evens = _.remove(self.initValSettingLst(), function(init: IPerInfoInitValueSettingDto) {
                            return init.settingId !== self.initSettingId();
                        });
                        var newLength = evens.length;

                        if (newLength > 0) {
                            if (i === (sourceLength - 1)) {
                                i = newLength - 1;
                            }

                            self.start(evens[i].settingId);
                        } else {
                            self.start(undefined);

                        }
                        block.clear();
                        close();
                    });
                });
            }).ifNo(() => {
                block.clear();
                return;
            });
        }

        // cap nhat init value
        update() {
            let self = this,
                updateObj = {
                    settingId: self.initSettingId(),
                    settingName: self.currentCategory().settingName(),
                    perInfoCtgId: self.currentCategory().currentItemId(),
                    itemLst: _.map(ko.toJS(self.currentCategory().itemList()), function(obj: PerInfoInitValueSettingItemDto) {
                        return {
                            perInfoItemDefId: obj.perInfoItemDefId,
                            itemName: obj.itemName,
                            isRequired: obj.isRequired,
                            refMethodType: obj.refMethodType,
                            itemType: obj.itemType,
                            dataType: obj.dataType,
                            saveDataType: obj.saveDataType,
                            stringValue: obj.stringValue,
                            intValue: obj.intValue,
                            dateValue: obj.dateValue,
                            dateWithDay: obj.dateWithDay,
                            timePoint: obj.timePoint,
                            value: obj.value,
                            selectedRuleCode: obj.selectedRuleCode,
                            selectedCode: obj.selectedCode
                        };
                    })
                };
            //            block.invisible();
            service.update(updateObj).done(function(data) {
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start(updateObj.settingId);
                    self.currentCategory().currentItemId(updateObj.perInfoCtgId);
                });

                //                block.clear();
            }).fail(function(res: any) {
                dialog.alertError({ messageId: res.messageId });
                //                block.clear();
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
        itemList: KnockoutObservableArray<any>;
        constructor(params: IInitValueSettingDetail) {
            let self = this;
            self.settingCode = ko.observable(params.settingCode);
            self.settingName = ko.observable(params.settingName);
            self.ctgList = ko.observableArray(params.ctgList);
            self.itemList = ko.observableArray(params.itemList || []);


        }

        setData(params: IInitValueSettingDetail) {
            let self = this;
            self.settingCode(params.settingCode);
            self.settingName(params.settingName);
            self.ctgList(params.ctgList);
            if (self.ctgList().length > 0) {
                self.currentItemId(params.ctgList[0].perInfoCtgId);
            } else {
                self.currentItemId('');
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

        // đoạn này dùng để  hiển thị
        perInfoItemDefId: string;
        settingId?: string;
        perInfoCtgId: string;
        itemName: string;
        isRequired: number;
        refMethodType: number;
        //dành cho cột 2 - combo
        itemType: number; //日付　型-1; 統合ログインコード-2; 口座名１～口座名５-3; .....
        listComboItem: Array<any> = [];

        //trường này dùng để dataType
        dataType: number;

        // đoạn này dùng để lưu dữ liệu        
        saveDataType: number;
        stringValue?: string;
        intValue?: number;
        dateValue?: string;
        dateWithDay?: number;
        timePoint?: string;

        itemCode: string;
        ctgCode: string;
        constraint: string;
        value? any;
    }

    export class PerInfoInitValueSettingItemDto {
        perInfoItemDefId: KnockoutObservable<string>;
        settingId: KnockoutObservable<string>;
        perInfoCtgId: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        isRequired: KnockoutObservable<number>;

        refMethodType: KnockoutObservable<number>;
        itemType: KnockoutObservable<number>;
        listComboItem: KnockoutObservableArray<any>;
        selectedRuleCode: KnockoutObservable<number>;


        dataType: KnockoutObservable<number>;

        saveDataType: KnockoutObservable<number>;
        stringValue: KnockoutObservable<string>;
        intValue: KnockoutObservable<number>;
        dateValue: KnockoutObservable<String>;
        dateWithDay: KnockoutObservable<number>;
        timePoint: KnockoutObservable<string>;
        value: KnockoutObservable<string> = ko.observable("");

        // trường hợp datatype là kiểu selection
        selection: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;

        //constraint
        itemCode: KnockoutObservable<string>;
        ctgCode: KnockoutObservable<string>;
        constraint: KnockoutObservable<string>;
        constructor(params: IPerInfoInitValueSettingItemDto) {
            let self = this;
            self.perInfoItemDefId = ko.observable(params.perInfoItemDefId || "");
            self.settingId = ko.observable(params.settingId || "");
            self.perInfoCtgId = ko.observable(params.perInfoCtgId || "");
            self.itemName = ko.observable(params.itemName || "");

            self.isRequired = ko.observable(params.isRequired || 0);
            self.refMethodType = ko.observable(params.refMethodType || 0);

            self.saveDataType = ko.observable(params.saveDataType || 0);
            self.stringValue = ko.observable(params.stringValue || "");
            self.intValue = ko.observable(params.intValue || 0);

            self.dateValue = ko.observable(params.dateValue || "99991221");
            self.dateWithDay = ko.observable(params.dateWithDay || 0);
            self.timePoint = ko.observable(params.timePoint || "");


            self.itemType = ko.observable(params.itemType || 0);
            self.dataType = ko.observable(params.dataType || 0);
            self.selectedRuleCode = ko.observable(params.refMethodType || 1);
            if (params.itemType === 0) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "ログイン者と同じ" },
                    { code: 4, name: "入力日と同じ" },
                    { code: 5, name: "システム日付と同じ" }]);
            } else if (params.itemType === 1) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "社員コードと同じ" }]);
            } else if (params.itemType === 2) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "氏名と同じ" }]);
            } else if (params.itemType === 3) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "氏名（カナ）と同じ" }]);
            } else if (params.itemType === 4) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "ログイン者と同じ" }]);
            } else if (params.itemType === 5 || params.itemType === 6) {
                self.listComboItem = ko.observableArray([{ code: 1, name: "設定なし" },
                    { code: 2, name: "固定値" },
                    { code: 3, name: "ログイン者と同じ" }]);
            }


            if (params.refMethodType === 1) {
                ko.observable(params.stringValue);
            } else if (params.refMethodType === 2) {
                ko.observable(params.intValue);
            } else if (params.refMethodType === 3) {
                ko.observable(params.dateValue);
            }

            self.selection = ko.observableArray([{ code: 1, name: "設定なし" },
                { code: 2, name: "固定値" },
                { code: 3, name: "ログイン者と同じ" }]);
            self.selectedCode = ko.observable("");
            
            self.itemCode = ko.observable(params.itemCode || "");
            self.ctgCode = ko.observable(params.ctgCode || "");
            self.constraint = ko.observable(params.constraint || "");

        }
    }

    export interface IPerInfoInitValueSettingDto {
        companyId?: string;
        settingId: string;
        settingCode: string;
        settingName: string;

    }

}