module nts.uk.pr.view.qmm003.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        
        items2: any;
        singleSelectedCode: any;
        headers: any;

        constructor() {
            let self = this;
            self.items2 = ko.observableArray([
                new Node('0001', 'サービス部', [
                    new Node('0001-1', 'サービス部1', []),
                    new Node('0001-2', 'サービス部2', []),
                    new Node('0001-3', 'サービス部3', [])
                ]), 
                new Node('0002', '開発部', [])
            ]);
            self.singleSelectedCode = ko.observable(null);
            self.headers = ko.observableArray(["Item Value Header","Item Text Header"]); 
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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
    
}

