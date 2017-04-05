module qmm003.a.viewmodel {
    export class ScreenModel {
        // data of items list - tree grid
        items: KnockoutObservableArray<RedensitalTaxNode>;
        singleSelectedCode: KnockoutObservable<string>;
        firstSelectedCode: KnockoutObservable<string> = ko.observable("");
        isEnable: KnockoutObservable<boolean>;
        enableBTN007: KnockoutObservable<boolean>;
        enableBTN009: KnockoutObservable<boolean>;
        enableInp002: KnockoutObservable<boolean> = ko.observable(true);
        isEditable: KnockoutObservable<boolean>;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        mode: KnockoutObservable<boolean>;
        nodeRegionPrefectures: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        japanLocation: Array<service.model.RegionObject> = [];
        precfecture: Array<RedensitalTaxNode> = [];
        itemPrefecture: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<service.model.ResidentialTax> = ko.observableArray([]);
        currentResidential: KnockoutObservable<service.model.ResidentialTax> = ko.observable(null);
        resiTaxReportCode: KnockoutObservable<string> = ko.observable("");
        resiTaxCode: KnockoutObservable<string> = ko.observable("");
        resiTaxAutonomy: KnockoutObservable<string> = ko.observable("");
        prefectureCode: KnockoutObservable<string> = ko.observable("");
        dirtyObject: nts.uk.ui.DirtyChecker;
        previousCurrentCode: string = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
        hasFocus: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            self.init();
            self.singleSelectedCode.subscribe(function(newValue) {
                if (newValue.length === 1) {
                    let index: number;
                    index = _.findIndex(self.items(), function(obj: RedensitalTaxNode) {
                        return obj.code === newValue;

                    })
                   // self.singleSelectedCode(self.items()[index].childs[0].childs[0].code);
                    self.processWhenCurrentCodeChange(self.items()[index].childs[0].childs[0].code);
                    return;
                }
                if (newValue.length === 2) {

                    let array = [];
                    array = self.findIndex(self.items(), newValue);
                    //self.singleSelectedCode(self.items()[array[0]].childs[array[1]].childs[0].code);
                    self.processWhenCurrentCodeChange(self.items()[array[0]].childs[array[1]].childs[0].code);
                    return;
                }

                if (!nts.uk.text.isNullOrEmpty(newValue) && (self.singleSelectedCode() !== self.previousCurrentCode) && self.editMode) {
                    if (self.dirtyObject.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                            self.processWhenCurrentCodeChange(newValue);
                        }).ifCancel(function() {
                            self.items([]);
                            self.nodeRegionPrefectures([]);
                            self.start(self.previousCurrentCode);
                        })

                    } else {
                        self.processWhenCurrentCodeChange(newValue);
                    }
                } else {
                    self.editMode = true;
                }
            });
        }

        // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
        findIndex(items: Array<RedensitalTaxNode>, newValue: string): any {
            let index: number;
            let count: number = -1;
            let array = [];
            _.each(items, function(obj: RedensitalTaxNode) {
                count++;
                index = _.findIndex(obj.childs, function(obj1: RedensitalTaxNode) {
                    return obj1.code === newValue;
                });
                if (index > -1) {
                    array.push(count, index);
                }

            });

            return array;
        }

        processWhenCurrentCodeChange(newValue: string) {
            let self = this;
            service.getResidentialTaxDetail(newValue).done(function(data: service.model.ResidentialTax) {
                if (data) {
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.save-error').ntsError('clear');
                    }
                    if (!self.enableBTN009()) {
                        self.enableBTN009(true);
                    }
                    self.enableInp002(false);
                    self.currentResidential(ko.mapping.fromJS(data));
                    self.previousCurrentCode = newValue;
                    self.dirtyObject.reset();
                } else {
                    self.resetData();
                }
            });
        }

        // create array prefecture from japan location
        buildPrefectureArray(): void {
            let self = this;
            _.map(self.japanLocation, function(region: service.model.RegionObject) {
                _.each(region.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                    return self.precfecture.push(new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                });
            });

        }

        // reset Data
        resetData(): void {
            let self = this;
            self.editMode = false;
            self.enableBTN007(true);
            self.enableBTN009(true);
            self.enableInp002(true);
            let node = new service.model.ResidentialTax();
            node.companyCode = '';
            node.resiTaxCode = '';
            node.resiTaxAutonomy = '';
            node.prefectureCode = '1';
            node.resiTaxReportCode = '';
            node.registeredName = '';
            node.companyAccountNo = '';
            node.companySpecifiedNo = '';
            node.cordinatePostalCode = '';
            node.cordinatePostOffice = '';
            node.memo = '';
            self.currentResidential(ko.mapping.fromJS(node));
            self.previousCurrentCode = '';
            self.singleSelectedCode("");
            self.hasFocus(true);
            self.dirtyObject.reset();
        }
        // init menu
        init(): void {
            let self = this;
            // data of treegrid
            self.items = ko.observableArray([]);
            self.mode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.enableBTN007 = ko.observable(true);
            self.enableBTN009 = ko.observable(null);
            self.isEditable = ko.observable(true);
            self.singleSelectedCode = ko.observable("");
            let objResi = new service.model.ResidentialTax();
            objResi.companyCode = '';
            objResi.resiTaxCode = '';
            objResi.resiTaxAutonomy = '';
            objResi.prefectureCode = '';
            objResi.resiTaxReportCode = '';
            objResi.registeredName = '';
            objResi.companyAccountNo = '';
            objResi.companySpecifiedNo = '';
            objResi.cordinatePostalCode = '';
            objResi.cordinatePostOffice = '';
            objResi.memo = '';
            self.currentResidential = ko.observable(ko.mapping.fromJS(objResi));
        }
        //BTN007
        openBDialog() {
            let self = this;
            let currentResidential: service.model.ResidentialTax;
            nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode, true);
            nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                currentResidential = nts.uk.ui.windows.getShared("currentResidential");
                self.editMode = false;
                self.resiTaxCode(currentResidential.resiTaxCode);
                self.resiTaxAutonomy(currentResidential.resiTaxAutonomy);
                self.prefectureCode(currentResidential.prefectureCode);
            });
        }

        //BTN009
        openCDialog() {
            let self = this;
            let currentResidential: service.model.ResidentialTax;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                currentResidential = nts.uk.ui.windows.getShared('currentResidential');
                self.resiTaxReportCode(currentResidential.resiTaxCode);
            });

        }

        //BTN006
        openDDialog() {
            let self = this;
            let items: any;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function(): any {
                items = nts.uk.ui.windows.getShared('items');
                console.log(items);
                self.items([]);
                self.nodeRegionPrefectures([]);
                self.start(undefined);
            });
        }
        //BTN005
        deleteAResidential(): void {
            let self = this;
            let objResidential: service.model.ResidentialTax;
            objResidential = (ko.toJS(self.currentResidential()));
            let resiTaxCodes = [];
            resiTaxCodes.push(objResidential.resiTaxCode);
            qmm003.a.service.deleteResidential(resiTaxCodes).done(function(data) {
                self.items([]);
                self.nodeRegionPrefectures([]);
                self.reload(self.firstSelectedCode());
            });
        }
        openEDialog() {

            let self = this;
            let labelSubsub: any;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function(): any {
                labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');

            });
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(currentSelectedCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;

            (qmm003.a.service.getResidentialTax()).done(function(data: Array<qmm003.a.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.mode(true); // true, update mode 
                    self.residentalTaxList(data);
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                        self.buildResidentalTaxTree();
                        let node: Array<RedensitalTaxNode> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.items(self.nodeRegionPrefectures());
                        self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentResidential);
                        if (currentSelectedCode === undefined) {
                            self.singleSelectedCode(self.firstSelectedCode());
                        } else {
                            self.singleSelectedCode(currentSelectedCode);
                        }
                        self.enableBTN007(false);
                        self.enableBTN009(true);
                        self.enableInp002(false)
                    });


                } else {
                    //self.resetData();
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                    });
                    self.mode(false)// false, new mode
                    self.enableBTN007(true);
                    self.enableBTN009(true);
                }

                dfd.resolve();

            }).fail(function(res) {

            });

            return dfd.promise();
        }

        reload(currentSelectedCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;

            (qmm003.a.service.getResidentialTax()).done(function(data: Array<qmm003.a.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.mode(true); // true, update mode 
                    self.residentalTaxList(data);
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                        self.buildResidentalTaxTree();
                        let node: Array<RedensitalTaxNode> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.items(self.nodeRegionPrefectures());
                        self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentResidential);
                        self.singleSelectedCode(currentSelectedCode);
                        self.enableBTN007(false);
                        self.enableBTN009(true);
                        self.enableInp002(false)
                    });


                } else {
                    self.resetData();
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                    });
                    self.mode(false)// false, new mode
                    self.enableBTN007(true);
                    self.enableBTN009(true);
                }

                dfd.resolve();

            }).fail(function(res) {

            });

            return dfd.promise();
        }

        buildResidentalTaxTree() {
            let self = this;
            var child = [];
            let i = 0;
            _.each(self.residentalTaxList(), function(objResi: qmm003.a.service.model.ResidentialTax) {
                _.each(self.japanLocation, function(objRegion: service.model.RegionObject) {
                    let isChild: boolean = false;
                    let isPrefecture: boolean = false;
                    _.each(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            _.each(self.nodeRegionPrefectures(), function(obj: RedensitalTaxNode) {
                                if (obj.code === objRegion.regionCode) {
                                    _.each(obj.childs, function(objChild: RedensitalTaxNode) {
                                        if (objChild.code === objPrefecture.prefectureCode) {
                                            objChild.childs.push(new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                            isPrefecture = true;
                                        }
                                    });
                                    if (isPrefecture === false) {
                                        obj.childs.push(new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                    }
                                    isChild = true;
                                }
                            });
                            if (isChild === false) {
                                let child = [];
                                self.nodeRegionPrefectures.push(new RedensitalTaxNode(objRegion.regionCode, objRegion.regionName, [new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                            }
                        }
                    });
                });

            });
            let node: Array<RedensitalTaxNode>;
            node = self.nodeRegionPrefectures()[0].childs;
            let node1: Array<RedensitalTaxNode>;
            node1 = (node[0].childs);
            self.firstSelectedCode(self.nodeRegionPrefectures()[0].childs[0].childs[0].code);
        }

        validateData(): boolean {
            $(".nts-editor").ntsEditor("validate");
            $("#A_INP_002").ntsEditor("validate");
            $("#A_INP_003").ntsEditor("validate");
            $("#A_INP_004").ntsEditor("validate");
            $("#A_INP_005").ntsEditor("validate");
            $("#A_INP_006").ntsEditor("validate");
            $("#A_INP_007").ntsEditor("validate");
            $("#A_INP_008").ntsEditor("validate");
            $("#A_SEL_001").ntsEditor("validate");
            let errorA: boolean = false;
            errorA = $("#A_INP_002").ntsError('hasError') || $("#A_INP_003").ntsError('hasError')
                || $("#A_INP_004").ntsError('hasError') || $("#A_INP_005").ntsError('hasError')
                || $("#A_INP_006").ntsError('hasError') || $("#A_INP_007").ntsError('hasError')
                || $("#A_INP_008").ntsError('hasError') || $("#A_SEL_001").ntsError('hasError');
            if ($(".nts-editor").ntsError('hasError') || errorA) {
                return false;
            }
            return true;
        }

        clickRegister(): void {
            let self = this;
            let objResi = new service.model.ResidentialTax();
            let node = new RedensitalTaxNode('', '', []);
            if (!self.validateData()) {
                return;
            }
            objResi = ko.toJS(self.currentResidential());
            if (!self.mode()) {
                qmm003.a.service.addResidential(objResi).done(function() {
                    self.items([]);
                    self.nodeRegionPrefectures([]);
                    self.start(objResi.resiTaxCode);

                });
            } else {
                qmm003.a.service.updateData(objResi).done(function() {
                    self.items([]);
                    self.nodeRegionPrefectures([]);
                    self.start(objResi.resiTaxCode);
                });
            }
        }
    }
    export class RedensitalTaxNode {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<RedensitalTaxNode>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }
};
