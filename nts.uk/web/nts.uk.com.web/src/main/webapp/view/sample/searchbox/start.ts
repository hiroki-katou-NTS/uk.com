__viewContext.ready(function () {
    class ScreenModel {
        index: number;
        dataSource: any;
        filteredData: any;
        singleSelectedCode: any;
        selectedCodes: any;
        headers: any;
        constructor() {
            var self = this;
            self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                new Node('0003', 'Bangkok Thailand', []),
                new Node('0004', 'Tokyo Japan', []),
                new Node('0005', 'Jakarta Indonesia', []), 
                new Node('0002', 'Seoul Korea', []),
                new Node('0006', 'Paris France', []),
                new Node('0007', 'United States', [new Node('0008', 'Washington US', []),new Node('0009', 'Newyork US', [])]),                             
                new Node('0010', 'Beijing China', []),
                new Node('0011', 'London United Kingdom', []),
                new Node('0012', '', [])]);
             
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(),"childs"));
            self.singleSelectedCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.index = 0;
            self.headers = ko.observableArray(["Item Value Header","Item Text Header", "Auto generated Field"]);
        }       
        resetSelection(): void {
            var self = this;          
            self.filteredData(self.dataSource());
            self.singleSelectedCode('0002');
            self.selectedCodes(['002']);           
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
            self.dataSource(newArrays);
            self.filteredData(newArrays);
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