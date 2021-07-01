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
        componentViewmodel: KnockoutObservable<ComponentModel>;

        constructor() {
            const self = this;
            self.roleType = ko.observable(0);
            self.roleAtr = ko.observable(null);
            self.multiple = ko.observable(false);
            self.isAlreadySetting = ko.observable(false);
            self.componentViewmodel = ko.observable(new ComponentModel({
                roleType: self.roleType(),
                roleAtr: self.roleAtr(),
                multiple: self.multiple(),
                isAlreadySetting: self.isAlreadySetting()
            }));

            self.roleType.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: value,
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting()
                }));
                self.componentViewmodel().startPage();
            });
            self.roleAtr.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: value,
                    multiple: self.multiple(),
                    isAlreadySetting: self.isAlreadySetting()
                }));
                self.componentViewmodel().startPage();
            });
            self.multiple.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: value,
                    isAlreadySetting: self.isAlreadySetting()
                }));
                self.componentViewmodel().startPage();
            });self.isAlreadySetting.subscribe(value => {
                self.componentViewmodel(new ComponentModel({
                    roleType: self.roleType(),
                    roleAtr: self.roleAtr(),
                    multiple: self.multiple(),
                    isAlreadySetting: value
                }));
                self.componentViewmodel().startPage();
            });

        }
    }
}