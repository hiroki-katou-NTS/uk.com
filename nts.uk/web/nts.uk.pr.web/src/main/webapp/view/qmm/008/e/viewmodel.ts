module nts.uk.pr.view.qmm008.e {
    export module viewmodel {
        export class ScreenModel {

            index: number;
            dataSource: any;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
            headers: any;
            //for tab panel
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            modalValue: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            leftShow : KnockoutObservable<boolean>;
            rightShow : KnockoutObservable<boolean>;
            leftBtnText: any;
            rightBtnText: any;
            constructor() {
                var self = this;
                self.listOptions = ko.observableArray([new optionsModel(1, "Ã¦Å“â‚¬Ã¦â€“Â°Ã£ï¿½Â®Ã¥Â±Â¥Ã¦Â­Â´(2016/04)Ã£ï¿½â€¹Ã£â€šâ€°Ã¥Â¼â€¢Ã£ï¿½ï¿½Ã§Â¶â„¢Ã£ï¿½ï¿½"), new optionsModel(2, "Ã¥Ë†ï¿½Ã£â€šï¿½Ã£ï¿½â€¹Ã£â€šâ€°Ã¤Â½Å“Ã¦Ë†ï¿½Ã£ï¿½â„¢Ã£â€šâ€¹")]);
                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.modalValue = ko.observable("Goodbye world!");
                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value 
                self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                    new Node('0003', 'Bangkok Thailand', []),
                    new Node('0004', 'Tokyo Japan', []),
                    new Node('0005', 'Jakarta Indonesia', []),
                    new Node('0002', 'Seoul Korea', []),
                    new Node('0006', 'Paris France', []),
                    new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                    new Node('0010', 'Beijing China', []),
                    new Node('0011', 'London United Kingdom', []),
                    new Node('0012', '', [])]);

                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([]);
                self.index = 0;
                self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                //panel
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: 'åŸºæœ¬æƒ…å ±', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: 'ä¿�é™ºãƒžã‚¹ã‚¿ã�®æƒ…å ±', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.leftShow = ko.observable(true);
                self.rightShow = ko.observable(true);
                self.leftBtnText = ko.computed(function() {if(self.leftShow()) return "-"; return "+";});
                self.rightBtnText = ko.computed(function() {if(self.rightShow()) return "-"; return "+";});
            }
            
            leftToggle() {
                this.leftShow(!this.leftShow());
            }
            rightToggle() {
                this.rightShow(!this.rightShow());
            }

            CloseModalSubWindow() {
                // Set child value
                nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                nts.uk.ui.windows.close();
            }
        }
        export class optionsModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }

        export class Node {
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
}