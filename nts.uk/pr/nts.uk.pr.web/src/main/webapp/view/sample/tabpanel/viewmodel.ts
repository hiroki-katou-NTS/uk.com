module sample.tabpanel.viewmodel {
    
    export class ScreenModel {
        tabs: KnockoutObservableArray<TabModel>;
        selectedTab: KnockoutObservable<string>;
        user: KnockoutObservable<User>;
        /**
         * Constructor.
         */
        constructor() {
            var self = this;
            self.tabs = ko.observableArray([new TabModel('tab-1', 'Tab Title 1', '.tab-content-1'),
                new TabModel('tab-2', 'Tab Title 2', '.tab-content-2'),
                new TabModel('tab-3', 'Tab Title 3', '.tab-content-3'),
                new TabModel('tab-4', 'Tab Title 4', '.tab-content-4')]);
            self.user = ko.observable(new User(''));
            self.selectedTab = ko.observable('tab-2');
            
            // Force update tabs when update element.
            self.tabs().forEach(tab => {
                tab.enable.subscribe((val) => {
                    self.tabs.valueHasMutated();
                })
                tab.visible.subscribe((val) => {
                    self.tabs.valueHasMutated();
                })
            })
        }
    }
    
    /**
     * Class Item model.
     */
    export class TabModel {
        id: string;
        title: string;
        content: string;
        visible: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        
        constructor(id: string, title: string, content: string) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.visible = ko.observable(true);
            this.enable = ko.observable(true);
        }
    }
    
    export class User {
        name: KnockoutObservable<string>;
        
        constructor(name: string){
            this.name = ko.observable(name);
        }
    }
}