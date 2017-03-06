module nts.uk.pr.view.qmm002.b {
    export module service {
        var paths = {
            getBankList: "",
            removeListBank: "basic/system/bank/remove/list"
        };
        
        export function getBankList() {
            var dfd = $.Deferred<Array<any>>();
            
            var result = [
                    new viewmodel.Node('0001', 'Hanoi Vietnam', []),
                    new viewmodel.Node('0003', 'Bangkok Thailand', []),
                    new viewmodel.Node('0004', 'Tokyo Japan', []),
                    new viewmodel.Node('0005', 'Jakarta Indonesia', []), 
                    new viewmodel.Node('0002', 'Seoul Korea', []),
                    new viewmodel.Node('0006', 'Paris France', []),
                    new viewmodel.Node('0007', 'United States', [
                            new viewmodel.Node('0008', 'Washington US', []),
                            new viewmodel.Node('0009', 'Newyork US', [])]),                             
                    new viewmodel.Node('0010', 'Beijing China', []),
                    new viewmodel.Node('0011', 'London United Kingdom', []),
                    new viewmodel.Node('0012', 'dfsdfs', [])
                ];
            
            dfd.resolve(result);
            
            return dfd.promise(); 
        }
        
        export function removeBank(data): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var path = paths.removeListBank;
            nts.uk.request.ajax("com", path, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
    }    
}