__viewContext.ready(function () {
    class ScreenModel {
        index: number;
        items1: any;
        items2: any;
        selectedCode: any;
        singleSelectedCode: any;
        columns: any;
        columns2: any;
    
        constructor() {
            var self = this;
            self.items1 = ko.observableArray([new Node('0001', 'サービス部', [
                new Node('0001-1', 'サービス部1', []),
                new Node('0001-2', 'サービス部2', []),
                new Node('0001-3', 'サービス部3', [])
            ]), new Node('0002', '開発部', [])]);
            self.items2 = ko.observableArray(self.items1());
            self.selectedCode = ko.observableArray([]);
            self.singleSelectedCode = ko.observable(null);
            self.index = 0;
            self.columns = ko.observableArray([{ headerText: "Item Code", width: "150px", key: 'code', dataType: "string", hidden: false },
            { headerText: "Item Text", key: 'nodeText', width: "200px", dataType: "string" }]);
            self.columns2 = ko.observableArray([{ headerText: "Item Code", width: "150px", key: 'code', dataType: "string", hidden: false },
            { headerText: "Item Text", key: 'nodeText', width: "200px", dataType: "string" },
            { headerText: "Item Auto Generated Field", key: 'custom', width: "200px", dataType: "string" }]);            
        }
        
        resetSelection(): void {
            var self = this;
            self.items1([new Node('0001', 'サービス部', [
                new Node('0001-1', 'サービス部1', []),
                new Node('0001-2', 'サービス部2', []),
                new Node('0001-3', 'サービス部3', [])
            ]), new Node('0002', '開発部', [])]);
            self.items2(self.items1());
            self.singleSelectedCode('0002');
            self.selectedCode(['0001-1', '0002']);
        }
        
        changeDataSource(): void {
            var self = this;
            var i = 0;
            var newArrays = new Array();
            while (i < 50) {
                self.index ++;
                i++;
                newArrays.push(new Node(self.index.toString(), 'Name ' + self.index.toString(), []));
            };
            self.items1(newArrays);
            self.items2(newArrays);
        }
    }
    
    class Node {
        code: string;
        name: string;
        nodeText: string;
        custom: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;
            self.custom = 'Random' + new Date().getTime();
        }
    }
    
    this.bind(new ScreenModel());
    
});