__viewContext.ready(function () {
    class ScreenModel {
        dataSource: any;
        dataSource2: any;
        filteredData: any;
        filteredData2: any;
        singleSelectedCode: any;
        singleSelectedCode2: any;
        selectedCodes: any;
        selectedCodes2: any;
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
            self.dataSource2 = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
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
            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource2(),"childs"));
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedCode2 = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedCodes2 = ko.observableArray([]);            
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]);
        }              
    }
    
    class Node {
        code: string;
        name: string;
        nodeText: string;
        childs: any;
        constructor(code: string, name: string, childs: Array<Node>) {
            var self = this;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.childs = childs;         
        }
    }  
    this.bind(new ScreenModel());
    
});