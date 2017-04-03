module qmm003.d.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<Node>;
        selectedCode: KnockoutObservableArray<string>;
        filteredData: KnockoutObservableArray<Node> = ko.observableArray([]);
        currentNode: KnockoutObservable<Node>;
        testNode = [];
        arrayNode: KnockoutObservableArray<string> = ko.observableArray([]);
        nodeRegionPrefectures: KnockoutObservableArray<Node> = ko.observableArray([]);
        japanLocation: Array<qmm003.d.service.model.RegionObject> = [];
        precfecture: Array<Node> = [];
        itemPrefecture: KnockoutObservableArray<Node> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.d.service.model.ResidentialTax> = ko.observableArray([]);
        constructor() {
            let self = this;
            self.init();
            self.selectedCode.subscribe(function(newValue) {
                self.arrayNode(newValue);
                self.currentNode(self.findByCode(self.items(), self.arrayNode()[0]));
                console.log(self.currentNode());


            });
        }

        findByCode(items: Array<Node>, newValue: string): Node {
            let self = this;
            let node: Node;
            _.each(items, function(objRegion: Node) {
                _.each(objRegion.childs, function(objPrefecture: Node) {
                    _.each(objPrefecture.childs, function(obj: Node) {
                        if (obj.code === newValue) {
                            node = obj;

                        }


                    });



                });

            });
            return node;

        }
        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.selectedCode = ko.observableArray([]);
            self.currentNode = ko.observable(new Node("", "", []));
        }
        clickButton(): void {
            let self = this;
            let resiTaxCodes = [];
//            qmm003.d.service.getResidentialTaxByResiTaxCode(self.arrayNode()[0],self.arrayNode()[0]).done(function(data){
//                console.log(data);
//            
//            });
            for (let i = 0; i < self.arrayNode().length; i++) {
                resiTaxCodes.push(self.arrayNode()[i]);
            }
            console.log(resiTaxCodes);
                qmm003.d.service.deleteResidential(resiTaxCodes).done(function(data) {
                    console.log(data);
                    self.items([]);
                    self.nodeRegionPrefectures([]);
                    //self.start();

                });
            nts.uk.ui.windows.close();
        }
        cancelButton(): void {
            nts.uk.ui.windows.close();
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.d.service.getResidentialTax()).done(function(data: Array<qmm003.d.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    (qmm003.d.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildResidentalTaxTree();
                        let node: Array<Node> = [];
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
            _.each(self.residentalTaxList(), function(objResi: qmm003.d.service.model.ResidentialTax) {
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