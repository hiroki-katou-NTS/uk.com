module qmm003.c.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ResidentalTaxNode>;
        singleSelectedCode: KnockoutObservable<string>;
        filteredData: KnockoutObservableArray<ResidentalTaxNode> = ko.observableArray([]);
        currentNode: ResidentalTaxNode = null;
        nodeRegionPrefectures: KnockoutObservableArray<ResidentalTaxNode> = ko.observableArray([]);
        japanLocation: Array<qmm003.c.service.model.RegionObject> = [];
        precfecture: Array<ResidentalTaxNode> = [];
        itemPrefecture: KnockoutObservableArray<ResidentalTaxNode> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.c.service.model.ResidentialTaxDto> = ko.observableArray([]);
        yes: boolean = null;
        constructor() {
            let self = this;
            self.init();
            self.singleSelectedCode.subscribe(function(newValue) {
                if (newValue.length > 2) {
                    self.processWhenCurrentCodeChange(newValue);
                }
            });

        }
        processWhenCurrentCodeChange(newValue: string) {
            let self = this;
            let node: ResidentalTaxNode;
            node = _.find(self.filteredData(), function(obj: ResidentalTaxNode) {
                return obj.code = newValue;
            });
            self.currentNode = node;
        }

        clickButton(): any {
            let self = this;
            self.yes = true;
            nts.uk.ui.windows.setShared('yes', self.yes, true);
            nts.uk.ui.windows.setShared('currentNode', self.currentNode, true);
            nts.uk.ui.windows.setShared('items', self.items(), true);
            nts.uk.ui.windows.close();

        }
        cancelButton(): void {
            let self = this;
            self.yes = false;
            nts.uk.ui.windows.setShared('yes', self.yes, true);
            nts.uk.ui.windows.setShared('items', this.items(), true);
            nts.uk.ui.windows.close();
        }
        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
        }
        //11.初期データ取得処理 11. Initial data acquisition processing
        start(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let self = this;
            (qmm003.c.service.getResidentialTax()).done(function(data: Array<qmm003.c.service.model.ResidentialTaxDto>) {
                if (data.length > 0) {
                    self.residentalTaxList(data);
                    console.log(data);
                    (qmm003.c.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildResidentalTaxTree();
                        let node: Array<ResidentalTaxNode> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.items(self.nodeRegionPrefectures());
                    });
                } else {
                    nts.uk.ui.dialog.alert("対象データがありません。");
                }
                dfd.resolve();

            }).fail(function(res) {

            });

            return dfd.promise();
        }

        // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
        findIndex(items: Array<ResidentalTaxNode>, newValue: string): any {
            let index: number;
            let count: number = -1;
            let prefectureArray = [];
            _.each(items, function(obj: ResidentalTaxNode) {
                count++;
                index = _.findIndex(obj.childs, function(obj1: ResidentalTaxNode) {
                    return obj1.code === newValue;
                });
                if (index > -1) {
                    prefectureArray.push(count, index);
                }

            });

            return prefectureArray;
        }

        buildResidentalTaxTree() {
            let self = this;
            var child = [];
            let i = 0;
            _.each(self.residentalTaxList(), function(objResi: qmm003.c.service.model.ResidentialTax) {
                _.each(self.japanLocation, function(objRegion: service.model.RegionObject) {
                    let isChild: boolean = false;
                    let isPrefecture: boolean = false;
                    _.each(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            _.each(self.nodeRegionPrefectures(), function(obj: ResidentalTaxNode) {
                                if (obj.code === objRegion.regionCode) {
                                    _.each(obj.childs, function(objChild: ResidentalTaxNode) {
                                        if (objChild.code === objPrefecture.prefectureCode) {
                                            objChild.childs.push(new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, []));
                                            isPrefecture = true;
                                        }
                                    });
                                    if (isPrefecture === false) {
                                        obj.childs.push(new ResidentalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])]));
                                    }
                                    isChild = true;
                                }
                            });
                            if (isChild === false) {
                                let chi = [];
                                self.nodeRegionPrefectures.push(new ResidentalTaxNode(objRegion.regionCode, objRegion.regionName, [new ResidentalTaxNode(objPrefecture.prefectureCode, objPrefecture.prefectureName, [new ResidentalTaxNode(objResi.resiTaxCode, objResi.resiTaxAutonomy, [])])]));
                            }
                        }
                    });
                });

            });
        }

    }
    export class ResidentalTaxNode {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<ResidentalTaxNode>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
        }
    }


};