__viewContext.ready(function () {
    class ScreenModel {
        
        password: KnockoutObservable<String>;
        salt: KnockoutObservable<String>;
        hash: KnockoutObservable<String>;

        constructor() {
            this.password = ko.observable('');
            this.salt = ko.observable('');
            this.hash = ko.observable('');
        }
        
        generate() {
            this.hash('');
            nts.uk.request.ajax('/sample/passwordhash/generate', {
                passwordPlainText: this.password(),
                salt: this.salt()
            }).done(res => {
                this.hash(res);
            });
        }
    }
    
    this.bind(new ScreenModel());
    
});