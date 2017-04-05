module qmm003.d.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<RedensitalTaxNode>;
        selectedCode: KnockoutObservableArray<string>;
        filteredData: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        arrayNode: KnockoutObservableArray<string> = ko.observableArray([]);
        nodeRegionPrefectures: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        japanLocation: Array<qmm003.d.service.model.RegionObject> = [];
        precfecture: Array<RedensitalTaxNode> = [];
        itemPrefecture: KnockoutObservableArray<RedensitalTaxNode> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<qmm003.d.service.model.ResidentialTax> = ko.observableArray([]);
        currentResidential: KnockoutObservable<service.model.ResidentialTax> = ko.observable(null);
        indexOfRoot = [];
        indexOfPrefecture = [];

        constructor() {
            let self = this;
            self.init();
            self.selectedCode.subscribe(function(newValue) {
                self.arrayNode(newValue);
            });
        }
        // tìm index để khi chọn root thì ra hiển thị ra thằng đầu tiên của 1 thằng root
        findIndex(items: Array<RedensitalTaxNode>, newValue: string): any {
            let indexOfValue1: number;
            if (newValue.length === 1) {
                indexOfValue1 = _.findIndex(items, function(obj: RedensitalTaxNode) {
                    return obj.code === newValue;

                });
               this.indexOfRoot.push(indexOfValue1);

            } else if (newValue.length === 2) {
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
                this.indexOfPrefecture = array;
                return array;
            }
        }
        
        init(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.selectedCode = ko.observableArray([]);
        }

        clickButton(): void {
            let self = this;
            let resiTaxCodes = [];
            let resiTax =[];
            console.log(self.arrayNode());
            
            for (let i = 0; i < self.arrayNode().length; i++) {
                resiTaxCodes.push(self.arrayNode()[i]);
                this.findIndex(this.items(), self.arrayNode()[i]);
            }
            console.log(this.indexOfRoot);
            for(let i = 0; i < (self.indexOfRoot.length ); i++){
                _.each(self.items()[i], function(obj){
                    console.log(obj.childs);
                    _.each(obj.childs, function(obj1){
                      console.log(obj1);  
                    });
                })
            
            }
            console.log(this.indexOfPrefecture);
            console.log(resiTaxCodes);
            qmm003.d.service.deleteResidential(resiTaxCodes).done(function(data) {
                console.log(data);
                self.items([]);
                self.nodeRegionPrefectures([]);
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
            _.each(self.residentalTaxList(), function(objResi: qmm003.d.service.model.ResidentialTax) {
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
        childs: any;
        constructor(code: string, name: string, childs: Array<RedensitalTaxNode>) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;

        }
    }

}