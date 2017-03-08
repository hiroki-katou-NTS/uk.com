module qmm003.a.viewmodel {
    export class ScreenModel {
        // data of items list - tree grid
        items: KnockoutObservableArray<Node>;
        singleSelectedCode: KnockoutObservable<string>;
        firstSelectedCode: KnockoutObservable<string> = ko.observable("");
        currentNode: KnockoutObservable<Node>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        editMode: boolean = true; // true là mode thêm mới, false là mode sửa 
        filteredData: KnockoutObservableArray<Node> = ko.observableArray([]);
        //selectedCodes: KnockoutObservableArray<any>;
        mode: KnockoutObservable<boolean>;
        testNode = [];
        nodeRegionPrefectures: KnockoutObservableArray<Node> = ko.observableArray([]);
        japanLocation: Array<service.model.RegionObject> = [];
        precfecture: Array<Node> = [];
        itemPrefecture: KnockoutObservableArray<Node> = ko.observableArray([]);
        residentalTaxList: KnockoutObservableArray<service.model.ResidentialTax> = ko.observableArray([]);
        currentResidential: KnockoutObservable<service.model.ResidentialTax>;
        currentResi: KnockoutObservable<service.model.ResidentialTax>;
        residentialReportCode: KnockoutObservable<string> = ko.observable("");
        constructor() {
            let self = this;
            self.init();
            //self.selectedCodes = ko.observableArray([]);
            self.singleSelectedCode.subscribe(function(newChange) {
                if (self.editMode) {
                    let currentNode: Node;
                    currentNode = self.findByCode(self.filteredData(), newChange);
                    self.currentNode(ko.mapping.fromJS(currentNode));
                    self.findPrefectureByResiTax(newChange);

                    self.currentResi(ko.mapping.fromJS(self.findResidentialByCode(self.residentalTaxList(), newChange)));
                    self.residentialReportCode(self.currentResi().resiTaxReportCode);
                    self.currentResidential(ko.mapping.fromJS(self.currentResi()));
                    console.log(self.currentResidential());
                    console.log(self.currentResi());

                } else {
                    self.editMode = true;
                }
            });
            self.selectedCode.subscribe(function(newChange) {
                console.log(newChange);
            });
        }

        // find Node By code (singleSelectedCode)
        findByCode(items: Array<Node>, newValue: string): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.code == newValue) {
                        node = obj;
                        $(document).ready(function(data) {
                            $("#A_INP_002").attr('disabled', 'true');
                            $("#A_INP_002").attr('readonly', 'true');
                        });
                    }
                }
            });

            return node;
        };

        // find  Node by resiTaxCode
        findResidentialByCode(items: Array<service.model.ResidentialTax>, newValue: string): service.model.ResidentialTax {
            let self = this;
            let objResi: service.model.ResidentialTax;
            _.find(items, function(obj: service.model.ResidentialTax) {
                if (!objResi) {
                    if (obj.resiTaxCode == newValue) {
                        objResi = obj;
                    }
                }
            });

            return objResi;
        };
        // find Node by name
        findByName(items: Array<Node>, name: string): Node {
            let self = this;
            let node: Node;
            _.find(items, function(obj: Node) {
                if (!node) {
                    if (obj.name === name) {
                        node = obj;
                    }
                }
            });
            return node;
        }
        //  set selectedcode by prefectureCode
        findPrefectureByResiTax(code: string): void {
            let self = this;
            _.each(self.items(), function(objRegion: Node) {
                _.each(objRegion.childs, function(objPrefecture: Node) {
                    _.each(objPrefecture.childs, function(obj: Node) {
                        if (obj.code === code) {
                            self.selectedCode(objPrefecture.code);
                            console.log(self.selectedCode());
                        }
                    });
                });
            });

        }
        // create array prefecture from japan location
        buildPrefectureArray(): void {
            let self = this;
            //(qmm003.b.service.getResidentialTax()).done(function(data: Array<qmm003.b.service.model.ResidentialTax>) {
            _.map(self.japanLocation, function(region: service.model.RegionObject) {
                _.each(region.prefectures, function(objPrefecture: service.model.PrefectureObject) {
                    return self.precfecture.push(new Node(objPrefecture.prefectureCode, objPrefecture.prefectureName, []));
                });
            });
            //  });

        }

        // reset Data
        resetData(): void {
            let self = this;
            self.editMode = false;
            self.currentNode(ko.mapping.fromJS(new Node('', '', [])));
            let node = new service.model.ResidentialTax();
            node.companyCode = '';
            node.resiTaxCode = '';
            node.resiTaxAutonomy = '';
            node.prefectureCode = '';
            node.resiTaxReportCode = '';
            node.registeredName = '';
            node.companyAccountNo = '';
            node.companySpecifiedNo = '';
            node.cordinatePostalCode = '';
            node.cordinatePostOffice = '';
            node.memo = '';
            self.currentResi(node);
            self.currentResidential(ko.mapping.fromJS(self.currentResi()));
            self.singleSelectedCode("");
            self.selectedCode("");
        }
        // init menu
        init(): void {
            let self = this;
            // data of treegrid
            self.items = ko.observableArray([]);
            self.mode = ko.observable(null);
            self.currentNode = ko.observable(ko.mapping.fromJS(new Node('022012', '青森市', [])));
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.singleSelectedCode = ko.observable("");
            self.selectedCode = ko.observable("11");
            let objResi = new service.model.ResidentialTax();
            objResi.companyCode = 'a';
            objResi.resiTaxCode = 'a';
            objResi.resiTaxAutonomy = 'a';
            objResi.prefectureCode = '42';
            objResi.resiTaxReportCode = '062014';
            objResi.registeredName = 'aaa';
            objResi.companyAccountNo = 'b';
            objResi.companySpecifiedNo = 'cccccc';
            objResi.cordinatePostalCode = '11111';
            objResi.cordinatePostOffice = 'bbbbb';
            objResi.memo = 'sssssssssssssssss';
            self.currentResi = ko.observable((objResi));
            self.currentResidential = ko.observable(ko.mapping.fromJS(self.currentResi()));
        }
        //BTN007
        openBDialog() {
            let self = this;
            let singleSelectedCode: any;
            let currentNode: Node;
            let selectedCode: string;
            nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
            nts.uk.ui.windows.sub.modeless('/view/qmm/003/b/index.xhtml', { title: '住民税納付先の登録＞住民税納付先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                singleSelectedCode = nts.uk.ui.windows.getShared("singleSelectedCode");
                currentNode = nts.uk.ui.windows.getShared("currentNode");
                selectedCode = nts.uk.ui.windows.getShared("selectedCode");
                console.log(currentNode);
                self.editMode = false;
                self.singleSelectedCode(singleSelectedCode);
                self.currentNode(ko.mapping.fromJS(currentNode));
                self.selectedCode(selectedCode);

            });
        }
        //BTN009
        openCDialog() {
            let self = this;
            let currentNode: Node;
            nts.uk.ui.windows.sub.modeless("/view/qmm/003/c/index.xhtml", { title: '住民税納付先の登録＞住民税報告先一覧', dialogClass: "no-close" }).onClosed(function(): any {
                currentNode = nts.uk.ui.windows.getShared('currentNode');
                self.residentialReportCode(currentNode.nodeText);

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
            //objResidential = ko.toJS
            objResidential = (ko.toJS(self.currentResidential()));
            let resiTaxCodes = [];
            resiTaxCodes.push(objResidential.resiTaxCode);
            qmm003.a.service.deleteResidential(resiTaxCodes).done(function(data) {
                console.log(data);
                self.items([]);
                self.nodeRegionPrefectures([]);
                self.start(undefined);

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
                    console.log(self.mode());
                    self.residentalTaxList(data);
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                        self.buildResidentalTaxTree();
                        let node: Array<Node> = [];
                        node = nts.uk.util.flatArray(self.nodeRegionPrefectures(), "childs");
                        self.filteredData(node);
                        self.items(self.nodeRegionPrefectures());
                        if(currentSelectedCode === undefined){
                            self.singleSelectedCode(self.firstSelectedCode());
                        }else{
                            self.singleSelectedCode(currentSelectedCode);
                        }
                    });


                } else {
                    self.resetData();
                    $(document).ready(function(data) {
                        $("#A_BTN_009").prop('disabled', 'false');
                    });
                    (qmm003.a.service.getRegionPrefecture()).done(function(locationData: Array<service.model.RegionObject>) {
                        self.japanLocation = locationData;
                        self.buildPrefectureArray();
                        self.itemPrefecture(self.precfecture);
                    });

                    self.mode(false)// false, new mode

                    console.log(self.mode());
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
            let node: Array<Node>;
            node = self.nodeRegionPrefectures()[0].childs;
            let node1: Array<Node>;
            node1 = (node[0].childs);
            console.log(node1[0]);
            self.firstSelectedCode(node1[0].code);
        }

        ClickRegister(): void {
            let self = this;
            let objResi = new service.model.ResidentialTax();
            let node = new Node('', '', []);
            node = ko.toJS(self.currentNode());
            objResi = ko.toJS(self.currentResidential());
            objResi.resiTaxCode = node.code;
            objResi.resiTaxAutonomy = node.name;
            objResi.prefectureCode = self.selectedCode();
            objResi.resiTaxReportCode = self.residentialReportCode().substring(0, 6);
            if (!self.mode()) {
                qmm003.a.service.addResidential(objResi).done(function() {
                    self.items([]);
                    self.nodeRegionPrefectures([]);
                    self.start(objResi.resiTaxCode);
                    $(document).ready(function(data) {
                        $("#A_BTN_009").removeAttr('disabled');
                        $("#A_BTN_009").prop('disabled', 'false');
                    });
                   // self.singleSelectedCode(objResi.resiTaxCode);

                });
            } else {
                qmm003.a.service.updateData(objResi).done(function() {
                    self.items([]);
                    self.nodeRegionPrefectures([]);
                    self.start(objResi.resiTaxCode);
                   // self.singleSelectedCode(objResi.resiTaxCode);
                });
            }



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
