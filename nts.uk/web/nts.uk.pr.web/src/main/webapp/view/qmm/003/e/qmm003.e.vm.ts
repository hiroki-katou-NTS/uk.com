module qmm003.e.viewmodel {
    export class ScreenModel {
        treeLeft: KnockoutObservableArray<ResidentialTaxNode>;
        treeRight: KnockoutObservableArray<ResidentialTaxNode>;
        resiTaxCodeLeft: KnockoutObservableArray<string>;
        resiTaxCodeRight: KnockoutObservable<string>;
        filteredData: KnockoutObservableArray<ResidentialTaxNode> = ko.observableArray([]);
        currentNode: KnockoutObservable<ResidentialTaxNode>;
        nodeRegionPrefectures: KnockoutObservableArray<ResidentialTaxNode> = ko.observableArray([]);
        japanLocation: Array<qmm003.b.service.model.RegionObject> = [];
        precfecture: Array<ResidentialTaxNode> = [];
        itemPrefecture: KnockoutObservableArray<ResidentialTaxNode> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.b.service.model.ResidentialTax> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable("");
        year: KnockoutObservable<number> = ko.observable(null);
        constructor() {
            let self = this;
            self.init();
            self.resiTaxCodeLeft.subscribe(function(newValue) {
                console.log(newValue);
                //self.processWhenCurrentCodeChange(newValue);
            });
            self.resiTaxCodeRight.subscribe(function(newValue) {
                console.log(newValue);
                //self.processWhenCurrentCodeChange(newValue);
            });

        }
        processWhenCurrentCodeChange(newValue: string) {
            let self = this;
            service.getPersonResidentialTax(self.year(), newValue).done(function(data: any) {
                // console.log(data);
            });
            self.currentNode(self.findByCode(self.filteredData(), newValue));
            self.findPrefectureByResiTax(newValue);
        }
        findByCode(items: Array<ResidentialTaxNode>, newValue: string): ResidentialTaxNode {
            let self = this;
            let _node: ResidentialTaxNode;
            _.find(items, function(_obj: ResidentialTaxNode) {
                if (!_node) {
                    if (_obj.code == newValue) {
                        _node = _obj;

                    }
                }
            });

            return _node;
        };
        clickButton(): any {
            let self = this;
            service.getPersonResidentialTax(self.year(), self.resiTaxCodeLeft()[0]).done(function(data: any) {
                console.log(data);
            });
            service.getResidentalTaxListByReportCode(self.resiTaxCodeLeft()[0]).done(function(data: any) {
                console.log(data);
            })

            nts.uk.ui.windows.close();

        }
        cancelButton(): void {
            nts.uk.ui.windows.close();
        }
        init(): void {
            let self = this;
            self.treeLeft = ko.observableArray([]);
            self.treeRight = ko.observableArray([]);
            self.resiTaxCodeLeft = ko.observableArray([]);
            self.resiTaxCodeRight = ko.observable("");
            self.currentNode = ko.observable((new ResidentialTaxNode("", "", [])));
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.e.service.getResidentialTax()).done(function(data: Array<qmm003.e.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    (qmm003.e.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.itemPrefecture(self.precfecture);
                        // console.log(self.itemPrefecture());
                        self.buildResidentalTaxTree();
                        let node: Array<ResidentialTaxNode> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.treeLeft(self.nodeRegionPrefectures());
                        self.treeRight(self.nodeRegionPrefectures());
                        //console.log(self.treeLeft());
                        //console.log(self.treeRight())
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
            _.each(self.residentalTaxList(), function(objResi: qmm003.a.service.model.ResidentialTaxDetailDto) {
                _.each(self.japanLocation, function(objRegion: service.model.RegionObject) {
                    let cout: boolean = false;
                    let coutPre: boolean = false;
                    _.each(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            _.each(self.nodeRegionPrefectures(), function(obj: ResidentialTaxNode) {
                                if (obj.code === objRegion.regionCode) {
                                    _.each(obj.childs, function(objChild: ResidentialTaxNode) {
                                        if (objChild.code === objPrefecture.prefectureCode) {
                                            objChild.childs.push(new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                            coutPre = true;
                                        }
                                    });
                                    if (coutPre === false) {
                                        obj.childs.push(new ResidentialTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                    }
                                    cout = true;
                                }
                            });
                            if (cout === false) {
                                let chi = [];
                                self.nodeRegionPrefectures.push(new ResidentialTaxNode(objRegion.regionCode, objRegion.regionName, [new ResidentialTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentialTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                            }
                        }
                    });
                });

            });
        }
        findPrefectureByResiTax(code: string): void {
            let self = this;
            let node: ResidentialTaxNode;
            _.each(self.treeLeft(), function(objRegion: ResidentialTaxNode) {
                _.each(objRegion.childs, function(objPrefecture: ResidentialTaxNode) {
                    _.each(objPrefecture.childs, function(obj: ResidentialTaxNode) {
                        if (obj.code === code) {
                            self.selectedCode(objPrefecture.code);
                        }
                    });
                });
            });

        }
    }
    export class ResidentialTaxNode {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<ResidentialTaxNode>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
        }
    }


};