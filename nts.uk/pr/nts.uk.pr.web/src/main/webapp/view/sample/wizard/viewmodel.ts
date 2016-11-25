module sample.wizard.viewmodel {
    
    export class ScreenModel {
        winzard: any;
        stepList: Array<Step>;
        stepSelected: KnockoutObservable<Step>;
        user: KnockoutObservable<User>;
        
        /**
         * Constructor.
         */
        constructor() {
            var self = this;
            self.stepList = [
                new Step('step-1', '.step-1'),
                new Step('step-2', '.step-2'),
                new Step('step-3', '.step-3'),
                new Step('step-4', '.step-4'),
                new Step('step-5', '.step-5'),
                new Step('step-6', '.step-6')
            ];
            self.stepSelected = ko.observable(new Step('step-1', '.step-1'));
            self.user = ko.observable(new User('U1', 'User 1'));
        }
        
        begin() {
            $('#wizard').begin();
        }
        end() {
            $('#wizard').end();
        }
        next() {
            $('#wizard').steps('next');
        }
        previous() {
            $('#wizard').steps('previous');
        }
        getCurrentStep() {
            alert($('#wizard').steps('getCurrentIndex'));
        }
        goto() {
            var self = this;
            var index = self.stepList.indexOf(self.stepSelected());
            $('#wizard').setStep(index);
        }
    }
    
    /**
     * Class Step model.
     */
    export class Step {
        id: string;
        content: string;
        
        constructor(id: string, content: string) {
            this.id = id;
            this.content = content;
        }
    }
    
    export class User {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        
        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.name.subscribe((val) => {alert(val)});
        }
    }
}