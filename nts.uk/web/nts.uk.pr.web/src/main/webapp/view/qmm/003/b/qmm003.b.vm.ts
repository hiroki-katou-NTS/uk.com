module qmm003.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<RedensitalTaxNode>;
        singleSelectedCode: KnockoutObservable<string>;
        filteredData: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        nodeRegionPrefectures: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        japanLocation: Array<qmm003.b.service.model.RegionObject> = [];
        precfecture: Array<RedensitalTaxNode> = [];
        itemPrefecture: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.b.service.model.ResidentialTax> = ko.observableArray([]);
        currentResidential: service.model.ResidentialTax = (null);
        constructor() {
            let self = this;
            self.init();
            self.singleSelectedCode.subscribe(function(newValue) {
                if (newValue.length === 1) {
                    let index: number;
                    index = _.findIndex(self.items(), function(obj: RedensitalTaxNode) {
                        return obj.code === newValue;

                    })
                    self.singleSelectedCode(self.items()[index].childs[0].childs[0].code);
                    return;
                }
                if (newValue.length === 2) {

                    let array = [];
                    array = self.findIndex(self.items(), newValue);
                    self.singleSelectedCode(self.items()[array[0]].childs[array[1]].childs[0].code);
                    return;
                }
                if (newValue.length > 2) {
                    self.processWhenCurrentCodeChange(newValue);
                }
            });

        }
        processWhenCurrentCodeChange(newValue: string) {
            let self = this;
            service.getResidentialTaxDetail('0000', newValue).done(function(data: service.model.ResidentialTax) {
                if (data) {
                    self.currentResidential = data;
                } else {
                    return;
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
        clickButton(): any {
            let self = this;
            nts.uk.ui.windows.setShared('currentResidential', self.currentResidential, true);
            nts.uk.ui.windows.close();

        }
        cancelButton(): void {
            nts.uk.ui.windows.close();
        }
        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(nts.uk.ui.windows.getShared("singleSelectedCode"));
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.b.service.getResidentialTax()).done(function(data: Array<qmm003.b.service.model.ResidentialTax>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    (qmm003.b.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.itemPrefecture(self.precfecture);
                        console.log(self.itemPrefecture());
                        self.buildResidentalTaxTree();
                        let node: Array<RedensitalTaxNode> = [];
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
                                let chi = [];
                                self.nodeRegionPrefectures.push(new RedensitalTaxNode(objRegion.regionCode, objRegion.regionName, [new RedensitalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new RedensitalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                            }
                        }
                    });
                });

            });
        }

    }
    export class RedensitalTaxNode {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
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