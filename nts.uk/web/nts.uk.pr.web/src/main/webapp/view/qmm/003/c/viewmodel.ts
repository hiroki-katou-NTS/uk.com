module qmm003.c.viewmodel {
    export class ScreenModel {
        items: any;
        singleSelectedCode: KnockoutObservable<string>;
        filteredData : KnockoutObservableArray<Node> = ko.observableArray([]);
        currentNode: KnockoutObservable<Node>;
        testNode = [];
        nodeRegionPrefectures: KnockoutObservableArray<Node> = ko.observableArray([]);
        japanLocation: Array<qmm003.c.service.model.RegionObject> = [];
        precfecture: Array<Node> = [];
        itemPrefecture: KnockoutObservableArray<Node> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.c.service.model.ResidentialTax> = ko.observableArray([]);
        constructor() {
            let self = this;
            self.init();
            self.singleSelectedCode.subscribe(function(newValue) {
                let node: Node;
                node = self.findByCode(self.filteredData(),newValue);
                console.log(node);
                self.currentNode(node);
            });
        }

        findByCode(items: Array<Node>, newValue: string): Node {
            let self = this;
            let _node: Node;
            _.find(items, function(_obj: Node) {
                if (!_node) {
                    if (_obj.code == newValue) {
                        _node = _obj;
                    }
                }
            });
            return _node;
        }
        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.singleSelectedCode = ko.observable("11");
            self.currentNode = ko.observable(new Node("","",[]));
        }
        clickButton(): void {
            let self = this;
            //nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
            nts.uk.ui.windows.setShared('currentNode',self.currentNode(),true);
            nts.uk.ui.windows.close();
        }
        cancelButton(): void {
            nts.uk.ui.windows.close();
        }
        register(): void {
            let inputSearch = $("#C_SCH_001").find("input.ntsSearchBox").val();
            if (inputSearch == "") {
                $('#C_SCH_001').ntsError('set', 'inputSearch が入力されていません。');
            } else {
                $('#C_SCH_001').ntsError('clear');
            }

            // errror search
            let error: boolean;
            _.find(this.filteredData(), function(obj: Node) {
                if (obj.code !== inputSearch) {
                    error = true;
                }
            });
            if (error = true) {
                $('#C_SCH_001').ntsError('set', '対象データがありません。');
            } else {
                $('#C_SCH_001').ntsError('clear');
            }


        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.c.service.getResidentialTax()).done(function(data: Array<qmm003.c.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    (qmm003.c.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildResidentalTaxTree();
                        let node : Array<Node> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.items(self.nodeRegionPrefectures());
                    });
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
            _.each(self.residentalTaxList(), function(objResi: qmm003.c.service.model.ResidentialTax) {
                _.each(self.japanLocation, function(objRegion: service.model.RegionObject) {
                    let cout: boolean = false;
                    let coutPre: boolean = false;
                    _.each(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            _.each(self.nodeRegionPrefectures(), function(obj: Node) {
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
                                self.nodeRegionPrefectures.push(new Node(objRegion.regionCode, objRegion.regionName, [new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
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
        custom: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }

}