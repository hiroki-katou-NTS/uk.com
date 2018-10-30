module nts.uk.pr.view.qmm020.l.viewmodel {

    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        masterUse: KnockoutObservableArray<any>;
        selectedMasterUse: any;

        usageMaster: KnockoutObservableArray<any>;
        selectedUsageMaster: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.masterUse = ko.observableArray([
                { code: '1', name: getText('QMM020_74') },
                { code: '2', name: getText('QMM020_77') }
            ]);
            self.selectedMasterUse = ko.observable(1);

            self.usageMaster = ko.observableArray([
                new BoxModel(0, getText('QMM020_6')),
                new BoxModel(1, getText('QMM020_78')),
                new BoxModel(2, getText('QMM020_8')),
                new BoxModel(3, getText('QMM020_9')),
                new BoxModel(4, getText('QMM020_10')),
            ]);
            self.selectedUsageMaster = ko.observable(1);
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
}