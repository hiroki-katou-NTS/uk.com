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
    import formatDate = nts.uk.time.formatDate;

    import primitiveConst = CPS009Constraint.primitiveConst;

    export class ViewModel {
        initValSettingLst: KnockoutObservableArray<any> = ko.observableArray([]);
        initValueList: KnockoutObservableArray<any> = ko.observableArray([]);
        initSettingId: KnockoutObservable<string> = ko.observable('');
        settingColums: KnockoutObservableArray<any>;
        itemValueLst: KnockoutObservableArray<any>;
        selectionColumns: any;
        currentCategory: KnockoutObservable<InitValueSettingDetail>;
        comboItems: any;
        comboColumns: any;
        isUpdate: boolean = false;
        //History reference date
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        lstItemFilter: Array<any> = [];
        ctgIdUpdate: KnockoutObservable<boolean> = ko.observable(false);
        currentItemId: KnockoutObservable<string> = ko.observable('');
        errorList: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {

            let self = this;
            self.initValue();
            self.start(undefined);
            self.initSettingId.subscribe(function(value: string) {

                nts.uk.ui.errors.clearAll();
                self.errorList([]);
                self.currentCategory().ctgList.removeAll();
                if (value) {
                    service.getAllCtg(value).done((data: any) => {
                        self.currentCategory().setData({
                            settingCode: data.settingCode,
                            settingName: data.settingName,
                            ctgList: data.ctgList
                        });
                        if (!self.ctgIdUpdate()) {
                            //perInfoCtgId
                            if (data.ctgList.length > 0) {
                                self.currentItemId(data.ctgList[0].perInfoCtgId);
                            } else {
                                self.currentItemId(undefined);
                            }
                        }
                        else {
                            self.ctgIdUpdate(false);
                        }

                        self.getItemList(value, self.currentItemId());
                    })


                } else {

                    return;
                }

                $('#ctgName').focus();

            });

            self.currentItemId.subscribe(function(value: string) {
                nts.uk.ui.errors.clearAll();
                self.errorList([]);

                if (value) {

                    self.getItemList(self.initSettingId(), value);


                } else {
                    return;
                }

            });


        }

        getTitleName(itemName: string) {
            return ko.computed(() => {
                return itemName.length > 5 ? itemName : "";
            });
        }

        // get item list
        getItemList(settingId: string, ctgId: string) {
            let self = this;
            self.currentCategory().itemList.removeAll();
            service.getAllItemByCtgId(settingId, ctgId).done((item: Array<IPerInfoInitValueSettingItemDto>) => {
                if (item.length > 0) {
                    let itemConvert = _.map(item, function(obj: IPerInfoInitValueSettingItemDto) {
                        primitiveConst(obj);
                        return new PerInfoInitValueSettingItemDto({
                            fixedItem: obj.fixedItem,
                            perInfoItemDefId: obj.perInfoItemDefId,
                            settingId: obj.settingId,
                            perInfoCtgId: obj.perInfoCtgId,
                            itemName: obj.itemName,
                            isRequired: obj.isRequired,
                            refMethodType: obj.refMethodType,
                            saveDataType: obj.saveDataType,
                            stringValue: obj.stringValue,
                            intValue: obj.intValue,
                            dateValue: obj.dateValue,
                            itemType: obj.itemType,
                            dataType: obj.dataType,
                            itemCode: obj.itemCode,
                            ctgCode: obj.ctgCode,
                            constraint: obj.constraint,
                            numberIntegerPart: obj.numberIntegerPart,
                            numberDecimalPart: obj.numberDecimalPart,
                            timeItemMin: obj.timeItemMin,
                            timeItemMax: obj.timeItemMax,
                            selectionItemId: obj.selectionItemId,
                            selection: obj.selection,
                            selectionItemRefType: obj.selectionItemRefType,
                            dateType: obj.dateType,
                            timepointItemMin: obj.timepointItemMin,
                            timepointItemMax: obj.timepointItemMax,
                            dateWithDay: obj.intValue,
                            numericItemMin: obj.numericItemMin,
                            numericItemMax: obj.numericItemMax,
                            stringItemType: obj.stringItemType,
                            stringItemLength: obj.stringItemLength,
                            stringItemDataType: obj.stringItemDataType
                        });
                    });

                    self.currentCategory().itemList.removeAll();
                    self.currentCategory().itemList(itemConvert);
                    self.lstItemFilter = itemConvert;
                } else {
                    self.currentCategory().itemList.removeAll();
                    self.currentCategory().itemList([]);

                }
            })
        }

        start(id: string): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getAll().done((data: Array<IPerInfoInitValueSettingDto>) => {
                self.ctgIdUpdate(false);
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
                    block.clear();

                } else {
                    self.isUpdate = false;
                    self.openDDialog();
                    self.refresh(undefined);
                    block.clear();

                }
            });
            return dfd.promise();
        }

        refresh(id: string) {
            let self = this;
            block.invisible();
            service.getAll().done((data: Array<any>) => {
                self.initValSettingLst.removeAll();
                self.initValSettingLst(data);
                if (self.initValSettingLst().length > 0) {
                    if (id === undefined) {
                        self.initSettingId(self.initValSettingLst()[0].settingId);
                    } else {
                        self.initSettingId(id);
                    }
                } else {
                    self.initSettingId("");

                }
                block.clear();

            });;
        }

        initValue() {
            let self = this;

            self.settingColums = ko.observableArray([
                { headerText: 'settingId', key: 'settingId', width: 100, hidden: true },
                { headerText: text('CPS009_10'), key: 'settingCode', width: 80 },
                { headerText: text('CPS009_11'), key: 'settingName', width: 160 }
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
                ctgCurrent = self.findCtg(self.currentCategory().ctgList(), self.currentItemId()),
                params = {
                    settingId: self.initSettingId(),
                    ctgName: ctgCurrent != undefined ? ko.toJS(ctgCurrent.categoryName) : '',
                    categoryId: self.currentItemId()
                };
            self.ctgIdUpdate(false);
            setShared('CPS009B_PARAMS', params);
            block.invisible();
            modal('/view/cps/009/b/index.xhtml', { title: '' }).onClosed(function(): any {
                $('#ctgName').focus();
                let itemSelected = getShared('CPS009B_DATA');
                if (itemSelected.isCancel) {
                    return;
                } else {
                    let itemLst: Array<any> = _.map(ko.toJS(self.currentCategory().itemList()), function(obj) {
                        return obj.perInfoItemDefId;
                    });
                    if (itemSelected.lstItem.length > 0) {
                        _.each(itemSelected.lstItem, function(item) {
                            let i: number = _.indexOf(itemLst , item);
                            if (i > -1) {
                                self.currentCategory().itemList()[i].selectedRuleCode(Number(itemSelected.refMethodType));
                            }
                        });
                    }
                }

                self.start(params.settingId);
                block.clear();
            });

        }

        // copy initVal
        openCDialog() {

            let self = this,
                params = {
                    settingId: ko.toJS(self.initSettingId()),
                    settingCode: ko.toJS(self.currentCategory().settingCode),
                    settingName: ko.toJS(self.currentCategory().settingName)
                };
            self.ctgIdUpdate(false);
            setShared('CPS009C_PARAMS', params);

            block.invisible();

            modal('/view/cps/009/c/index.xhtml', { title: '' }).onClosed(function(): any {
                $('#ctgName').focus();
                let initSetId: string = getShared('CPS009C_COPY');
                if (initSetId !== undefined) {
                    self.refresh(initSetId);
                }

                block.clear();
            });

        }

        // new initVal
        openDDialog() {

            let self = this;
            self.ctgIdUpdate(false);
            block.invisible();
            modal('/view/cps/009/d/index.xhtml', { title: '' }).onClosed(function(): any {
                let id: string = getShared('CPS009D_PARAMS');
                if (id !== undefined) {
                    self.refresh(id);
                }
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
            self.ctgIdUpdate(false);
            block.invisible();
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteInitVal(objDelete).done(function(data) {
                    dialog.info({ messageId: "Msg_16" }).then(function() {
                        $('#ctgName').focus();
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
                    });
                });
            }).ifNo(() => {
                $('#ctgName').focus();
                block.clear();
                return;
            });
        }

        // cap nhat init value
        update() {
            let self = this,
                currentCtg = self.findCtg(self.currentCategory().ctgList(), self.currentItemId()),
                updateObj = {
                    settingId: self.initSettingId(),
                    settingName: self.currentCategory().settingName(),
                    perInfoCtgId: self.currentItemId(),
                    isSetting: currentCtg.setting,
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
                            dateVal: obj.dateValue,
                            dateWithDay: obj.dateWithDay,
                            timePoint: obj.timePoint,
                            value: obj.value,
                            selectedRuleCode: obj.selectedRuleCode,
                            selectionId: obj.selectedCode,
                            numberValue: obj.numbereditor.value,
                            dateType: obj.dateType,
                            time: obj.dateWithDay

                        };
                    })
                },
                dateInputList = $(".table-container").find('tbody').find('tr').find('#date'),
                dateInputListOfYear = $(".table-container").find('tbody').find('tr').find('#datey'),
                itemList: Array<any> = _.filter(ko.toJS(self.currentCategory().itemList()), function(item: PerInfoInitValueSettingItemDto) {
                    return item.dataType === 3 && item.selectedRuleCode === 2;
                });
            if (dateInputList.length > 0 || dateInputListOfYear.length > 0) {
                let i: number = 0;
                _.each(itemList, function(item: PerInfoInitValueSettingItemDto) {
                    let $input1 = $(".table-container").find('tbody').find('tr').find('#date')[i],
                        $input2 = $(".table-container").find('tbody').find('tr').find('#datey')[i];
                    if ($input1 != undefined) {
                        $input1.setAttribute("nameid", item.itemName);
                    }
                    else if ($input2 != undefined) {
                        $input2.setAttribute("nameid", item.itemName);
                    }
                    i++;
                });
            }

            block.invisible();
            service.update(updateObj).done(function(data) {
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    $('#ctgName').focus();
                    self.initSettingId("");
                    self.refresh(updateObj.settingId);
                    self.initSettingId(updateObj.settingId);
                    self.currentItemId("");
                    self.currentItemId(updateObj.perInfoCtgId);
                    self.ctgIdUpdate(true);

                });
                self.currentItemId(updateObj.perInfoCtgId);
                $('.bundled-errors-alert .ntsClose').trigger('click');
                block.clear();
            }).fail(function(res: any) {
                $('.bundled-errors-alert .ntsClose').trigger('click');
                self.errorList(res);
                nts.uk.ui.dialog.bundledErrors(self.errorList());
                block.clear();
            });

        }

        //履歴参照基準日を適用する (Áp dụng ngày chuẩn để tham chiếu lịch sử)
        historyFilter_Lan() {
            let self = this,
                baseDate = moment(self.baseDate()).format('YYYY-MM-DD'),
                itemSelection: Array<PerInfoInitValueSettingItemDto> = _.filter(self.currentCategory().itemList(),
                    function(item: PerInfoInitValueSettingItemDto) {
                        return item.selectedRuleCode() == 2 && item.dataType() == 6 && (item.selectionItemRefType == 2 || item.selectionItemRefType == 1);
                    }),
                itemIdLst = _.map(itemSelection, function(obj: IPerInfoInitValueSettingItemDto) {
                    return {
                        selectionItemId: obj.selectionItemId,
                        selectionItemRefType: obj.selectionItemRefType,
                        baseDate: baseDate
                    };
                });

            if (itemIdLst.length > 0) {
                _.each(itemIdLst, function(item) {

                    let itemList: Array<any> = ko.toJS(self.currentCategory().itemList()),
                        indexList: Array<any> = [],
                        itemIndex: number = 0;
                    _.each(itemList, function(obj: PerInfoInitValueSettingItemDto) {
                        if (obj.selectionItemId === item.selectionItemId) {
                            indexList.push(itemIndex);
                        }
                        itemIndex++;
                    });


                    if (indexList.length > 0) {
                        service.getAllComboxByHistory(item).done(function(data: Array<any>) {
                            if (data) {
                                _.each(indexList, function(index) {
                                    self.currentCategory().itemList()[index].selection([]);
                                    self.currentCategory().itemList()[index].selection(data);
                                    self.currentCategory().itemList()[index].selection.valueHasMutated();
                                });

                            }
                        });
                    }


                });
            }

        }

        /**
         * check item co thoa man dieu kien de loc khong?
         */
        checkFilter(objItem: PerInfoInitValueSettingItemDto): boolean {
            //画面項目「個人情報初期値設定区分（A3_22）」で、「固定値」を選択している項目をチェックする(Kiểm tra Item mà có 「個人情報初期値設定区分（A3_22）」 là 「固定値」)
            if (objItem.selectedRuleCode() != 2) {
                return false;
            }
            //「固定値」になっているかつ、項目のデータ型＝選択項目かつ、参照区分！＝Enum参照条件の項目があるかチェックする(Kiểm tra những Item để là 「固定値」 và có Type là Selection có mục 参照区分 != Enum参照条件)
            //Type là Selection
            if (objItem.dataType() != 6) {
                return false
            }
            //参照区分 != Enum参照条件 && 参照区分＝コード名称参照条件の場合
            if (objItem.selectionItemRefType != 1) {
                return false;
            }
            return true;
        }

        /**
         * find item by perInfoItemDefId
         */
        findItem(lstITem: Array<any>, perInfoItemDefId: string): PerInfoInitValueSettingItemDto {
            return _.find(lstITem, function(obj) {
                return obj.perInfoItemDefId == perInfoItemDefId;
            });
        }

        /**
         * find category is selected
         */
        findCtg(lstCtg: Array<any>, ctgId: string): any {
            return _.find(lstCtg, function(obj) {
                return obj.perInfoCtgId == ctgId;
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
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
        listComboItem?: Array<any>;

        //trường này dùng để phân biệt item đó thuộc kiểu dữ liệu nào number or string
        dataType: number;

        // đoạn này dùng để lưu dữ liệu        
        saveDataType: number;
        stringValue?: string;
        intValue?: number;
        dateValue?: string;
        dateWithDay?: number;
        timePoint?: string;

        // xác định contraint của item đó
        itemCode: string;
        ctgCode: string;
        constraint: string;

        // xác định nếu item thuộc kiểu number thì thuộc loại integer hay decimal
        numberDecimalPart: number;
        numberIntegerPart: number;
        // timepoint
        timeItemMin?: number;

        timeItemMax?: number;

        // lưu giá trị của integer value or decimal value of numberic type
        numbereditor: any;

        // selectionItemId để kết nối với bảng SelectionItem
        selectionItemId?: string;

        selectionItemRefType?: number;

        selection?: Array<any>;

        // xác định dateType thuộc kiểu ngày tháng năm hay năm tháng hay năm
        dateType?: number;

        timepointItemMin?: number;

        timepointItemMax?: number;

        numericItemMin?: number;

        numericItemMax?: number;

        stringItemType?: number;

        stringItemLength?: number;

        stringItemDataType?: number;
        fixedItem: boolean;
    }

    export class PerInfoInitValueSettingItemDto {
        fixedItem: boolean;
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

        //dateType
        dateType: number;
        dateValue: KnockoutObservable<String>;

        dateWithDay: KnockoutObservable<number>;
        timePoint: KnockoutObservable<string>;

        // trường hợp datatype là kiểu selection
        selection: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;

        //constraint
        itemCode: KnockoutObservable<string>;
        ctgCode: KnockoutObservable<string>;
        constraint: KnockoutObservable<string>;

        // kiểu number có 2 loại là số nguyên với số thực
        numbericItem: NumbericItem;
        numbereditor: any;

        // timepoint
        timeItemMin: number;

        timeItemMax: number;

        //selectionItemId? : string;
        selectionItemId: string;
        selectionItemRefType: number;

        timepointItemMin: number;

        timepointItemMax: number;

        numericItemMin: number;

        numericItemMax: number;

        stringItemType: number;

        stringItemLength: number;

        stringItemDataType: number;
        getTitle: KnockoutObservable<string> = ko.observable("");
        constructor(params: IPerInfoInitValueSettingItemDto) {
            let self = this;
            self.getTitle(self.getWidthText(params.itemName) > 200 ? params.itemName : "");
            self.fixedItem = params.fixedItem;
            self.perInfoItemDefId = ko.observable(params.perInfoItemDefId || "");
            self.settingId = ko.observable(params.settingId || "");
            self.perInfoCtgId = ko.observable(params.perInfoCtgId || "");
            self.itemName = ko.observable(params.itemName || "");

            self.isRequired = ko.observable(params.isRequired || 0);
            self.refMethodType = ko.observable(params.refMethodType || 0);

            self.saveDataType = ko.observable(params.saveDataType || 0);
            self.stringValue = ko.observable(params.stringValue || null);
            self.intValue = ko.observable(params.intValue || 0);


            self.dateWithDay = ko.observable(params.dateWithDay || 0);
            self.timePoint = ko.observable(params.timePoint || "");

            self.timeItemMin = params.timeItemMin || undefined;
            self.timeItemMax = params.timeItemMax || undefined;

            self.timepointItemMin = params.timepointItemMin || undefined;

            self.timepointItemMax = params.timepointItemMax || undefined;

            self.numericItemMin = params.numericItemMin || undefined;

            self.numericItemMax = params.numericItemMax || undefined;

            self.itemType = ko.observable(params.itemType || undefined);
            self.dataType = ko.observable(params.dataType || undefined);

            if (params.dataType === 3) {
                if (params.dateType === 1) {
                    self.dateValue = ko.observable(params.dateValue || undefined);
                } else if (params.dateType === 2) {
                    if (params.dateValue === null) {
                        self.dateValue = ko.observable(undefined);
                    } else {
                        self.dateValue = ko.observable(formatDate(new Date(params.dateValue), "yyyy/MM"));
                    }
                } else if (params.dateType === 3) {
                    if (params.dateValue === null) {
                        self.dateValue = ko.observable(undefined);
                    } else {
                        self.dateValue = ko.observable(formatDate(new Date(params.dateValue), "yyyy") || undefined);
                    }
                }

                self.dateValue.subscribe(x => {
                    let itemName: string = this.itemName();
                    if (__viewContext["viewModel"].errorList().errors !== undefined) {
                        if (__viewContext["viewModel"].errorList().errors.length > 0) {
                            $('.bundled-errors-alert .ntsClose').trigger('click');
                            let res = _.remove(__viewContext["viewModel"].errorList().errors, function(n) {
                                return n.parameterIds[0] === itemName;
                            });
                            if (__viewContext["viewModel"].errorList().errors.length > 0) {
                                nts.uk.ui.dialog.bundledErrors(__viewContext["viewModel"].errorList());
                            } else {
                                $('.bundled-errors-alert .ntsClose').trigger('click');
                            }
                        }
                    }
                });
            }

            self.selectedRuleCode = ko.observable(params.refMethodType || 1);

            if (params.dataType === 6) {
                self.selectionItemId = params.selectionItemId || undefined;

                self.selectionItemRefType = params.selectionItemRefType || undefined;

                self.selection = ko.observableArray(params.selection || []);
                self.selectedCode = ko.observable(params.stringValue || undefined);
            }

            self.dateType = params.dateType || undefined;

            if (params.dataType === 3 || params.dataType === 4 || params.dataType === 5) {
                self.listComboItem = ko.observableArray([
                    { code: 1, name: ReferenceMethodType.NOSETTING },
                    { code: 2, name: ReferenceMethodType.FIXEDVALUE },
                    { code: 3, name: ReferenceMethodType.SAMEASLOGIN },
                    { code: 4, name: ReferenceMethodType.SAMEASEMPLOYMENTDATE },
                    { code: 6, name: ReferenceMethodType.SAMEASSYSTEMDATE }]);
            } else {
                self.listComboItem = ko.observableArray([
                    { code: 1, name: ReferenceMethodType.NOSETTING },
                    { code: 2, name: ReferenceMethodType.FIXEDVALUE },
                    { code: 3, name: ReferenceMethodType.SAMEASLOGIN }]);
            }


            self.itemCode = ko.observable(params.itemCode || "");
            self.ctgCode = ko.observable(params.ctgCode || "");
            self.constraint = ko.observable(params.constraint || "");


            self.numbericItem = new NumbericItem(params.dataType,
                {
                    numberDecimalPart: params.numberDecimalPart,
                    numberIntegerPart: params.numberIntegerPart
                }) || null;

            if (params.numberDecimalPart === 0 && (params.numberIntegerPart === 0 || params.numberIntegerPart === null)) {
                self.numbereditor = {
                    value: ko.observable(params.intValue || 0),
                    constraint: params.itemCode,
                    option: new nts.uk.ui.option.NumberEditorOption({
                        grouplength: 3,
                        decimallength: 0,
                        width: "",
                        textalign: "left",
                        currencyformat: "JPY"
                    }),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            } else {

                self.numbereditor = {
                    value: ko.observable(params.intValue || 0),
                    constraint: params.itemCode,
                    option: new nts.uk.ui.option.NumberEditorOption({
                        grouplength: 3,
                        decimallength: params.numberDecimalPart,
                        width: "",
                        textalign: "left",
                        currencyformat: "JPY"
                    }),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            }

            if (params.dataType === 1) {
                self.stringItemType = params.stringItemType || undefined;
                self.stringItemLength = params.stringItemLength || undefined;
                self.stringItemDataType = params.stringItemDataType || undefined;
                self.numericItemMin = params.numericItemMin || undefined;
                self.numericItemMax = params.numericItemMax || undefined;
                self.stringValue.subscribe(x => {
                    let itemName: string = this.itemName();
                    if (__viewContext["viewModel"].errorList().errors !== undefined) {
                        if (__viewContext["viewModel"].errorList().errors.length > 0) {
                            $('.bundled-errors-alert .ntsClose').trigger('click');
                            let res = _.remove(__viewContext["viewModel"].errorList().errors, function(n) {
                                return n.parameterIds[0] === itemName;
                            });
                            if (__viewContext["viewModel"].errorList().errors.length > 0) {
                                nts.uk.ui.dialog.bundledErrors(__viewContext["viewModel"].errorList());
                            } else {
                                $('.bundled-errors-alert .ntsClose').trigger('click');
                            }
                        }
                    }

                });
            }

            self.selectedRuleCode.subscribe(value => {
                nts.uk.ui.errors.clearAll();
            });



        }
        getWidthText(str: string): number {
            let div = $('<span>').text(str).appendTo('body'), width = div.width(); div.remove();
            return width;
        }
    }

    export interface IPerInfoInitValueSettingDto {
        companyId?: string;
        settingId: string;
        settingCode: string;
        settingName: string;

    }

    export interface INumbericItem {
        numberDecimalPart: number;
        numberIntegerPart: number;

    }

    export class NumbericItem {
        numberDecimalPart: number;
        numberIntegerPart: number;
        constructor(params: number, params2: INumbericItem) {
            let self = this;
            if (params === 2) {
                this.numberIntegerPart = params2.numberIntegerPart;
                this.numberDecimalPart = params2.numberDecimalPart;
            }
        }

    }


    function makeIcon(value, row) {
        if (value == "false")
            return '';
        return '●';
    }
    export enum ReferenceMethodType {
        /** (設定なし):1 */
        NOSETTING = '設定なし',
        /** 固定値): 2 **/
        FIXEDVALUE = '固定値',
        /** (ログイン者と同じ):3 */
        SAMEASLOGIN = 'ログイン者と同じ',
        /** (入社日と同じ): 4*/
        SAMEASEMPLOYMENTDATE = '入社日と同じ',
        /** (社員コードと同じ):5 */
        SAMEASEMPLOYEECODE = '社員コードと同じ',
        /** (システム日付):6 */
        SAMEASSYSTEMDATE = 'システム日付と同じ',
        /** (氏名と同じ ):7 */
        SAMEASNAME = '氏名と同じ ',
        /** (氏名（カナ）と同じ):8 */
        SAMEASKANANAME = '氏名（カナ）と同じ'
    }
}