module qmm003.a.viewmodel {
    export class ScreenModel {
        // data of items list - tree grid
        items: any;
        item1s: any;
        singleSelectedCode: KnockoutObservable<any>;
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
        filteredData: any;
        filteredData1: any;
        filteredData2: any;
        selectedCodes: any;
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
            self.currentNode = ko.observable(null);
            self.init();


            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.filteredData1 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
            self.removeData(self.filteredData2());
            self.selectedCodes = ko.observableArray([]);
            self.singleSelectedCode.subscribe(function(newValue) {
                console.log(newValue);
                self.Value(newValue);
                if (self.editMode) {
                    let count = 0;
                    self.curentNode(self.findByCode(self.filteredData2(), newValue, count));
                    self.nameBySelectedCode(self.findByName(self.filteredData2()));
                    self.selectedCode(self.nameBySelectedCode().code);
                    let co = 0, co1 = 0;
                    _.each(self.filteredData2(), function(obj: Node) {

                        if (obj.code != self.curentNode().code) {
                            co++;
                        } else {
                            if (co < ((_.size(self.filteredData2())) - 1)) {
                                co1 = co + 1;

                            } else {
                                co1 = co;
                            }
                        }
                    });

                    self.labelSubsub(self.filteredData2()[co1]);
                    if (self.labelSubsub() == null) {
                        self.labelSubsub(new Node("11", "22", [], null, null, null));
                    }
                } else {
                    self.editMode = true;
                }


            });



        }

        findByCode(items: Array<Node>, newValue: string, count: number): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.code == newValue) {
                        node = obj;
                        count = count + 1;
                        //console.log(count);
                    }
                }
            });
            return node;
        };

        findByName(items: Array<Node>): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.name == self.curentNode().name) {
                        node = obj;
                    }
                }
            });
            return node;
        };
        removeNodeByCode(items: Array<Node>): any {
            let self = this;
            _.remove(items, function(obj: Node) {
                if (obj.code == self.Value()) {
                    return obj.code == self.Value();
                } else {
                    return self.removeNodeByCode(obj.childs);

                }
            })

        };
        // remove data: return array of subsub tree
        removeData(items: Array<Node>): any {
            _.remove(items, function(obj: Node) {
                return _.size(obj.code) < 3;
            });
        }
        deleteData(): any {
            let self = this;
            self.removeNodeByCode(self.items());
            self.item1s(self.items());
            self.items([]);
            self.items(self.item1s());
        }
        Confirm() {
            let self = this;
            nts.uk.ui.dialog.confirm("Do you want to delete node \"?")
                .ifYes(function() {
                    self.deleteData();
                });
        }
        resetData(): void {
            let self = this;
            self.editMode = false;
            self.curentNode(new Node("", "", [], null, null, null));
            self.singleSelectedCode("");
            self.selectedCode("");
            self.labelSubsub("");
            //            self.items([]);
            //            self.items(self.item1s());
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
                new Node('1', '青森市', [], null, null, null),
                new Node('2', '秋田市', [], null, null, null),
                new Node('3', '山形市', [], null, null, null),
                new Node('4', '福島市', [], null, null, null),
                new Node('5', '水戸市', [], null, null, null),
                new Node('6', '宇都宮市', [], null, null, null),
                new Node('7', '川越市', [], null, null, null),
                new Node('8', '熊谷市', [], null, null, null),
                new Node('9', '浦和市', [], null, null, null)
            ]);

            // data of treegrid
            self.items = ko.observableArray([]);
            self.mode = ko.observable(null);
            self.currentCode = ko.observable(null);
            self.item1s = ko.observable(new Node('', '', [], null, null, null));
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.nameBySelectedCode = ko.observable(null);
            self.Value = ko.observable(null);

            self.singleSelectedCode = ko.observable("022012");
            self.curentNode = ko.observable(new Node('022012', '青森市', [], null, null, null));
            self.labelSubsub = ko.observable(new Node('052019', '秋田市', [], null, null, null));
            self.selectedCode = ko.observable("1");
            //self.testNode = ko.observable([]);

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
                        console.log(self.test());
                        self.items(self.test());
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
             var child= [];
            //console.log(self.residentalTaxList());
            console.log(self.japanLocation);

            _.forEach(self.residentalTaxList(), function(objResi: qmm003.a.service.model.ResidentialTax) {
                console.log(objResi.prefectureCode);
                _.forEach(self.japanLocation, function(objRegion: service.model.RegionObject) {
                    let cout: boolean = false;
                    //console.log(objRegi);
                    _.forEach(objRegion.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                        if (objPrefecture.prefectureCode === objResi.prefectureCode) {
                            console.log(objPrefecture.prefectureCode);
                            _.forEach(self.test(), function(obj: Node) {
                                if (obj.code === objRegion.regionCode) {
                                    _.forEach(obj.childs, function(objChild: Node){
                                        if(objChild.code === objPrefecture.prefectureCode){
                                        objChild.childs.push(new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [], '', '', ''));
                                            }
                                        else{
                                          obj.childs.push(new Node(objPrefecture.prefectureCode,objPrefecture.prefectureName,[new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy, [], '', '', '')],'','',''));
                                           console.log(obj.childs); 
                                        }
                                    });
                                    cout= true;
                                }
                            });
                            if (cout === false) {
                                var chi =[];
                                self.test.push(new Node(objRegion.regionCode, objRegion.regionName, [new Node(objPrefecture.prefectureCode,objPrefecture.prefectureName,[new Node(objResi.resiTaxCode, objResi.resiTaxAutonomy,[],'','','')],'','','')], '', '', ''));
                            }
                        }
                    });
                });

            });
        }


        removeNotUseObj(items: Array<Node>, prefecture: string): void {
            _.remove(items, function(obj: Node) {
                return obj.code === prefecture;

            });

        }



    }
    export class Node {
        code: string;
        name: string;
        nodeText: string;
        parentCode: string;
        parentName: string;
        treeCode: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>, parentCode: string, parentName: string, treeCode: string) {
            let self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.parentCode = parentCode;
            self.parentName = parentName;
            self.treeCode = treeCode;
            self.childs = childs;

        }
    }
};
