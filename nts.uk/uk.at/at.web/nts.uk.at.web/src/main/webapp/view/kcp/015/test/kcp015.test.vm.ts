module nts.uk.at.view.kcp015.test {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            
            checkedA3_1 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_2 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_3 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_4 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_5 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_6 : KnockoutObservable<boolean> = ko.observable(true);
            enable      : KnockoutObservable<boolean> = ko.observable(true);
            
            constructor() {
                var self = this;
                self.itemList = ko.observableArray([
                    new BoxModel(1, 'have parameters'),
                    new BoxModel(2, 'no parameters')
                ]);
                self.selectedId = ko.observable(1);
                
                self.selectedId.subscribe(function(value) {
                    if (value == 1) {
                        self.checkedA3_1(true);
                        self.checkedA3_2(true);
                        self.checkedA3_3(true);
                        self.checkedA3_4(true);
                        self.checkedA3_5(true);
                        self.checkedA3_6(true);
                        self.enable(true);
                    } else {
                        self.checkedA3_1(false);
                        self.checkedA3_2(false);
                        self.checkedA3_3(false);
                        self.checkedA3_4(false);
                        self.checkedA3_5(false);
                        self.checkedA3_6(false);
                        self.enable(false);
                    }
                });
            }
            
            openKCP015() {
                let self = this;

                setShared('dataShareKCP015', {
                    haveData: self.selectedId() == 1 ? true : false,
                    checkedA3_1: self.checkedA3_1(),
                    checkedA3_2: self.checkedA3_2(),
                    checkedA3_3: self.checkedA3_3(),
                    checkedA3_4: self.checkedA3_4(),
                    checkedA3_5: self.checkedA3_5(),
                    checkedA3_6: self.checkedA3_6(),
                });
                
                nts.uk.ui.windows.sub.modal("/view/kcp/015/component/index.xhtml").onClosed(() => {});
            }
        }
    }
}

class BoxModel {
    id: number;
    name: string;
    constructor(id, name){
        var self = this;
        self.id = id;
        self.name = name;
    }
}