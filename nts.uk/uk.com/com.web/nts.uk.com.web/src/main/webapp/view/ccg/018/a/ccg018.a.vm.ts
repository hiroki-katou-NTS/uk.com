module ccg018.a.viewmodel {

    export class ScreenModel {
        title: KnockoutObservable<string> = ko.observable('');
        tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
            new TabModel({ id: 'A1', name: nts.uk.resource.getText('CCG018_1'), active: true, display: true }),
            new TabModel({ id: 'B', name: nts.uk.resource.getText('CCG018_2'), display: true }),
        ]);

        constructor() {
            let self = this;
            self.tabs().map((t) => {
                // set title for tab
                if (t.active() == true) {
                    self.title(t.name);
                }
            });

        };

        changeTab(tab: TabModel): any {
            let self = this, view: any = __viewContext.viewModel,
                oldtab: TabModel = _.find(self.tabs(), t => t.active());

            // cancel action if tab self click
            if (oldtab.id == tab.id) {
                return;
            }

            tab.active(true);
            self.title(tab.name);
            self.tabs().map(t => (t.id != tab.id) && t.active(false));
            // call start function on view at here
            switch (tab.id) {
                case 'A1':
                    if (!!view.viewmodelA1 && typeof view.viewmodelA1.start == 'function') {
                        view.viewmodelA1.start();
                    }
                    break;
                case 'B':
                    if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                        view.viewmodelB.start();
                    }
                    break;
            }
            $('.screen-' + tab.id.toLowerCase()).trigger('click');
        }
    }

    class TabModel {
        id: string;
        name: string;
        active: KnockoutObservable<boolean> = ko.observable(false);
        display: KnockoutObservable<boolean> = ko.observable(false);

        constructor(param: any) {
            this.id = param.id;
            this.name = param.name;
            this.active(param.active || false);
            this.display(param.display || false);
        }

        changeTab() {
            // call parent view action
            __viewContext.viewModel.viewmodelA.changeTab(this);
        }
    }
}