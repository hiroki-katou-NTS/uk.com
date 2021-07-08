module nts.uk.com.view.ccg025.a {  
    import ComponentModel = component.viewmodel.ComponentModel;
    __viewContext.ready(function() {
        const vm = new ViewModel();
        vm.componentViewmodel().startPage().done(function() {
            __viewContext.bind(vm); 
        });        
    });

    class ViewModel {
        roleType: KnockoutObservable<number>;
        roleAtr: KnockoutObservable<number>;
        multiple: KnockoutObservable<boolean>;
        isAlreadySetting: KnockoutObservable<boolean>;
        showEmptyItem: KnockoutObservable<boolean>;
        selectType: KnockoutObservable<number>;
        rows: KnockoutObservable<number>;
        componentViewmodel: KnockoutObservable<ComponentModel>;

        selectTypes: KnockoutObservableArray<any>;

        constructor() {
            const self = this;
            self.roleType = ko.observable(0);
            self.roleAtr = ko.observable(null);
            self.multiple = ko.observable(false);
            self.isAlreadySetting = ko.observable(false);
            self.showEmptyItem = ko.observable(false);
            self.selectType = ko.observable(1);
            self.rows = ko.observable(15);
            self.componentViewmodel = ko.observable(new ComponentModel({
                roleType: self.roleType(),
                roleAtr: self.roleAtr(),
                multiple: self.multiple(),
                isAlreadySetting: self.isAlreadySetting(),
                rows: self.rows(),
                tabindex: 0,
                showEmptyItem: self.showEmptyItem(),
                selectType: self.selectType()
            }));

            self.selectTypes = ko.observableArray([
                {value: 1, name: "Selected List"},
                {value: 2, name: "Select All", enable: self.multiple},
                {value: 3, name: "Select First"},
                {value: 4, name: "Select None"}
            ]);

            self.roleType.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: value,
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.roleAtr.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: value,
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.multiple.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: value,
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.isAlreadySetting.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: value,
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.rows.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: value,
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.showEmptyItem.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: value,
                    selectType: self.selectType()
                }));
                self.componentViewmodel().startPage();
            });
            self.selectType.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting(),
                    rows: self.rows(),
                    tabindex: 0,
                    showEmptyItem: self.showEmptyItem(),
                    selectType: value
                }));
                self.componentViewmodel().startPage();
            });
        }

        setConfigured() {
            const self = this;
            if (!_.isEmpty(self.componentViewmodel().currentCode())) {
                if (self.multiple()) {
                    self.componentViewmodel().listRole().forEach(role => {
                        if (self.componentViewmodel().currentCode().indexOf(role.roleId) >= 0) {
                            role.configured = 1;
                        }
                    });
                    self.componentViewmodel().listRole.valueHasMutated();
                } else {
                    self.componentViewmodel().listRole().forEach(role => {
                        if (self.componentViewmodel().currentCode() == role.roleId) {
                            role.configured = 1;
                        }
                    });
                    self.componentViewmodel().listRole.valueHasMutated();
                }
            }
        }

        unSetConfigured() {
            const self = this;
            if (!_.isEmpty(self.componentViewmodel().currentCode())) {
                if (self.multiple()) {
                    self.componentViewmodel().listRole().forEach(role => {
                        if (self.componentViewmodel().currentCode().indexOf(role.roleId) >= 0) {
                            role.configured = 0;
                        }
                    });
                    self.componentViewmodel().listRole.valueHasMutated();
                } else {
                    self.componentViewmodel().listRole().forEach(role => {
                        if (self.componentViewmodel().currentCode() == role.roleId) {
                            role.configured = 0;
                        }
                    });
                    self.componentViewmodel().listRole.valueHasMutated();
                }
            }
        }
    }
}