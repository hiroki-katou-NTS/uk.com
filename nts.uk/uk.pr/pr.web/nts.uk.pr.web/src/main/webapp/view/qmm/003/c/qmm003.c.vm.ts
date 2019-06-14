module nts.uk.pr.view.qmm003.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import constants = qmm003.share.constants;

    export class ScreenModel {
        
        items2: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        headers: any;
        listRegions: Array<any> = constants.listRegions;
        listPrefectures: Array<any>;
        listRsdTaxPayee: Array<any> = [];
        enable: any;

        constructor() {
            let self = this;
            self.items2 = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.headers = ko.observableArray([getText("QMM003_9")]);
            self.listPrefectures = constants.listPrefectures;
            self.enable = ko.computed(() => {
                return self.selectedCode().length > 0 && self.selectedCode().indexOf("_") != 0;
            }, this);
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllResidentTaxPayee().done((data: Array<any>) => {
                let listNodes = [];
                self.listRegions.forEach(r => {
                    let regionNode = new Node(r.code, r.name, [], 0);
                    let prefectures = self.listPrefectures.filter(pr => {return pr.region == r.code});
                    let prefectureNodes = [];
                    prefectures.forEach(pr => {
                        let prefectureNode = new Node(pr.code < 10 ? "_0" + pr.code : "_" + pr.code, pr.name, [], 1);
                        if (data.length > 0) {
                            let residentTaxPayees = data.filter(d => {return d.prefectures == pr.code});
                            let residentNodes = _.map(residentTaxPayees, rs => {
                                let node = new Node(rs.code, rs.name, [], 2);
                                self.listRsdTaxPayee.push(node);
                                return node;
                            });
                            prefectureNode.children = residentNodes;
                        }
                        prefectureNodes.push(prefectureNode);
                    });
                    regionNode.children = prefectureNodes;
                    listNodes.push(regionNode);
                });
                self.items2(listNodes);
                let selected = getShared("QMM003CParam");
                if (!_.isEmpty(selected)) self.selectedCode(selected);
                dfd.resolve();
            }).fail(error => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        select() {
            let self = this;
            let selected = _.find(self.listRsdTaxPayee, r => {return r.code == self.selectedCode()});
            setShared("QMM003CResult", _.isEmpty(selected) ? null : {code: selected.code, name: selected.name});
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

    }

    class Node {
        code: string;
        searchCode: string;
        name: string;
        nodeText: string;
        children: any;
        level: number; //0: region, 1: prefecture, 2: resident
        
        constructor(code: string, name: string, children: Array<Node>, level?: number) {
            let self = this;
            self.code = code;
            self.searchCode = level == 2 ? code : "";
            self.name = level == 2 ? name : "";
            self.nodeText = level == 2 ? _.escape(code + ' ' + name) : _.escape(name);
            self.children = children;
            if (level != null) self.level = level;
        }
    }
    
}