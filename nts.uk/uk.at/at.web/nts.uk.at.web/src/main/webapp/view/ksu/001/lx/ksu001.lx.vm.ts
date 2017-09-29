module ksu001.lx.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        listTeam: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedTeam: KnockoutObservable<any> = ko.observable();
        columnsTeam: KnockoutObservableArray<NtsGridListColumn> = ko.observableArray([
            { headerText: nts.uk.resource.getText("KSU001_1110"), key: 'code', width: 60 },
            { headerText: nts.uk.resource.getText("KSU001_1111"), key: 'name', width: 120 }
        ]);
        teamCode: KnockoutObservable<string> = ko.observable('');
        teamName: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            for (let i = 1; i < 20; i++) {
                self.listTeam.push(new ItemModel('00' + i, '基本給'));
            }
            self.selectedTeam.subscribe(function(newValue) {
                
                nts.uk.ui.errors.clearAll();
                let currentItem = _.find(self.listTeam(),{'code': newValue});
                if (newValue) {
                      self.teamCode(currentItem.code);
                      self.teamName(currentItem.name);
                }
            });
        }

    
        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
} 