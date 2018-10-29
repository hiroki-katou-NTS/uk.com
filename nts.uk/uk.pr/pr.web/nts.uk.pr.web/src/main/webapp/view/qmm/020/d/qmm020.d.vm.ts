module nts.uk.pr.view.qmm020.d.viewmodel {

    import getText = nts.uk.resource.getText;

    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        index: number;
        selectedCode: any;
        singleSelectedCode: any;
        columns: any;
        selectedCodes2: any;


        constructor(){
            let self = this;
            self.items = ko.observableArray([]);
            for(let i = 1; i <= 50; i++) {
                let level1 = new Node('0000' + i, 'サービス部 サービス部 サービス部  vサービス部  サービス部サービス部 サービス部 サービス部 サービス部サービス部 サービス部サービス部' + i, []);
                for(let j = 1; j <= 2; j++) {
                    let ij = i + "" + j;
                    let level2 = new Node('0000' + ij, 'サービス部' + ij, []);
                    level1.childs.push(level2);
                    for(let k = 1; k <= 2; k++) {
                        let  ijk = ij + "" + k;
                        let level3 = new Node('0000' + ijk, 'サービス部' + ijk, []);
                        level2.childs.push(level3);
                        for(let l = 1; l <= 2; l++) {
                            let  ijkl = ijk + "" + l;
                            let level4 = new Node('0000' + ijkl, 'サービス部' + ijkl, []);
                            level3.childs.push(level4);
                            for(let n = 1; n <= 2; n++) {
                                let  ijkln = ijkl + "" + n;
                                let level5 = new Node('0000' + ijkln, 'サービス部' + ijkln, []);
                                level4.childs.push(level5);
                            }
                        }
                    }
                }
                self.items.push(level1);
            }
            self.selectedCode = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.selectedCodes2 = ko.observable([]);
            self.index = 0;
            self.columns = ko.observableArray([{ headerText: "Item Code", width: "250px", key: 'code', dataType: "string", hidden: false },
                { headerText: "Item Text", key: 'nodeText', width: "200px", dataType: "string" }]);
            self.columns = ko.observableArray([{ headerText: "Item Code", width: "250px", key: 'code', dataType: "string", hidden: false },
                { headerText: "Item Text", key: 'nodeText', width: "250px", dataType: "string" },
                { headerText: "Item Auto Generated Field", key: 'custom', width: "200px", dataType: "string" }]);
        }

    }
    export  class ItemModel {
        empCode: string;
        empName: string;
        display1: string;
        display2: string;
        constructor(empCode: string, empName: string, display1: string, display2: string) {
            this.empCode = empCode;
            this.empName = empName;
            this.display1 = display1;
            this.display2 = display2;
        }
    }

    class Node {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: Array<Node>;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
            self.custom = 'Random' + new Date().getTime();
        }
    }

}