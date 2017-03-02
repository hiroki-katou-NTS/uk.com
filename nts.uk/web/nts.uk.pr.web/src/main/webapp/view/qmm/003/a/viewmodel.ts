module qmm003.a.viewmodel {
    export class ScreenModel {
        // data of items list - tree grid
        items: any;
        item1s: any;
        singleSelectedCode: KnockoutObservable<string>;
        headers: any;
        curentNode: any;
        currentNode: any;
        nameBySelectedCode: any;
        arrayAfterFilter: any;
        labelSubsub: any; // show label sub sub of root


        // data of itemList - combox
        itemList: KnockoutObservableArray<Node>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: KnockoutObservableArray<any> = ko.observableArray([]);
        filteredData1: KnockoutObservableArray<Node>;
        filteredData2: KnockoutObservableArray<Node>;
        selectedCodes: KnockoutObservableArray<any>;
        Value: KnockoutObservable<string>;
        mode: KnockoutObservable<boolean>;
        region: KnockoutObservableArray<Node>;
        precfecture: KnockoutObservableArray<Node>;
        regionPrefect: KnockoutObservableArray<Node>;
        testNode = [];
        test: KnockoutObservableArray<Node> = ko.observableArray([]);
        count = 0;
        japanLocation: Array<service.model.RegionObject> = [];
        residentalTaxList: KnockoutObservableArray<service.model.ResidentialTax> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.currentNode = ko.observable('');
            self.init();
            console.log(self.filteredData());
            self.selectedCodes = ko.observableArray([]);
            self.singleSelectedCode.subscribe(function(newChange) {
                self.filteredData = nts.uk.util.flatArray(self.items(), "childs");
                console.log(self.filteredData);
                self.curentNode = self.findByCode(self.filteredData(), newChange);


            });




        }

        findByCode(items: Array<Node>, newValue: string): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.code == newValue) {
                        node = obj;
                    }
                }
            });
            return node;
        };

        resetData(): void {
            let self = this;
            self.editMode = false;
            self.curentNode(new Node("", "", []));
            self.singleSelectedCode("");
            self.selectedCode("");
            self.labelSubsub("");
        }
        search(): void {

            let inputSearch = $("#search").find("input.ntsSearchBox").val();
            if (inputSearch == "") {
                $('#search').ntsError('set', 'inputSearch が入力されていません。');
            } else {
                $('#search').ntsError('clear');
            }

            // errror search
            let error: boolean;
            _.find(this.filteredData2(), function(obj: Node) {
                if (obj.code !== inputSearch) {
                    error = true;
                }
            });
            if (error = true) {
                $('#search').ntsError('set', '対象データがありません。');
            } else {
                $('#search').ntsError('clear');
            }


        }
        init(): void {
            let self = this;
            // 11.初期データ取得処理 11. Initial data acquisition processing [住民税納付先マスタ.SEL-1] 
            self.itemList = ko.observableArray([
                new Node('1', '青森市', []),
                new Node('2', '秋田市', []),
                new Node('3', '山形市', []),
                new Node('4', '福島市', []),
                new Node('5', '水戸市', []),
                new Node('6', '宇都宮市', []),
                new Node('7', '川越市', []),
                new Node('8', '熊谷市', []),
                new Node('9', '浦和市', [])
            ]);

            // data of treegrid
            self.items = ko.observableArray([]);
            self.mode = ko.observable(null);
            self.currentCode = ko.observable(null);
            self.item1s = ko.observable(new Node('', '', []));
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.nameBySelectedCode = ko.observable(null);
            self.Value = ko.observable(null);

            self.singleSelectedCode = ko.observable("022012");
            self.curentNode = ko.observable(new Node('022012', '青森市', []));
            self.labelSubsub = ko.observable(new Node('052019', '秋田市', []));
            self.selectedCode = ko.observable("1");
            //self.filteredData = ko.observableArray([]);

        }
        openBDialog() {
            let self = this;
            let singleSelectedCode: any;
            let curentNode: any;
            nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                curentNode = nts.uk.ui.windows.getShared("curentNode");
                self.editMode = false;
                self.singleSelectedCode(singleSelectedCode);
                self.curentNode(curentNode);
            });
        }
        openCDialog() {
            let self = this;
            let labelSubsub: any;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                //             self.editMode = false;
                self.labelSubsub(labelSubsub);
                console.log(labelSubsub);
            });
        }
        openDDialog() {
            let self = this;
            let items: any;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/d/index.xhtml", { title: '住民税納付先の登録　＞　一括削除', dialogClass: "no-close" }).onClosed(function(): any {
                items = nts.uk.ui.windows.getShared('items');
                self.items([]);
                self.items(items);
                console.log(items);
                console.log(self.items());
            });
        }
        openEDialog() {

            let self = this;
            let labelSubsub: any;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/e/index.xhtml", { title: '住民税納付先の登録＞納付先の統合', dialogClass: "no-close" }).onClosed(function(): any {
                labelSubsub = nts.uk.ui.windows.getShared('labelSubsub');
                self.labelSubsub(labelSubsub);
                console.log(labelSubsub);
            });
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.a.service.getResidentialTax()).done(function(data: Array<qmm003.a.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildResidentalTaxTree();
                        self.filteredData = ko.observableArray([]);
                        self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.test(), "childs"));
                        console.log(self.filteredData());
                        self.items(self.test());
                        console.log(self.items());
                    });

                    self.mode(true);// true, update mode 
                } else {
                    self.mode(false)// false, new mode
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
                    let cout: boolean = false;
                    let coutPre: boolean = false;
                    _.each(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            _.each(self.test(), function(obj: Node) {
                                if (obj.code === objRegion.regionCode) {
                                    _.each(obj.childs, function(objChild: Node) {
                                        if (objChild.code === objPrefecture.prefectureCode) {
                                            objChild.childs.push(new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                            coutPre = true;
                                        }
                                    });
                                    if (coutPre === false) {
                                        obj.childs.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                    }
                                    cout = true;
                                }
                            });
                            if (cout === false) {
                                let chi = [];
                                self.test.push(new Node(objRegion.regionCode, objRegion.regionName, [new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                            }
                        }
                    });
                });

            });
        }



    }
    export class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }
};
