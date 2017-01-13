module nts.uk.pr.view.qmm002_1.a {
    export module service {
        var paths = {
            getBankList: ""
        };
        
        export function getBankList() {
            var dfd = $.Deferred<Array<any>>();
            
            var result = [
                    new viewmodel.Node('0001','1', 'Hanoi Vietnam', []),
                    new viewmodel.Node('0003','1', 'Bangkok Thailand', []),
                    new viewmodel.Node('0004','1', 'Tokyo Japan', []),
                    new viewmodel.Node('0005','1', 'Jakarta Indonesia', []), 
                    new viewmodel.Node('0002','1', 'Seoul Korea', []),
                    new viewmodel.Node('0006','1', 'Paris France', []),
                    new viewmodel.Node('0007','1', 'United States', [
                            new viewmodel.Node('0008','0007', 'Washington US', []),
                            new viewmodel.Node('0009','0007', 'Newyork US', [])]),                             
                    new viewmodel.Node('0010','1', 'Beijing China', []),
                    new viewmodel.Node('0011','1', 'London United Kingdom', []),
                    new viewmodel.Node('0012','1', '', [])
                ];
            
            dfd.resolve(result);
            
            return dfd.promise(); 
        }
    }
    
}