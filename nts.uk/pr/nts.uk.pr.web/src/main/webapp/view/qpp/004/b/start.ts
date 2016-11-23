__viewContext.ready(function () {
    function User(code, name) {
        this.code = ko.observable(code);
        this.name = ko.observable(name);
        this.name.subscribe(function (val) { alert(val); });
    }
    function Step(id, content) {
        this.id = ko.observable(id);
        this.content = ko.observable(content);
    }
    var vm = {
        wizard: {
            stepList : ko.observableArray([
                new Step('step-1', '.step-1'),
                new Step('step-2', '.step-2'),
                new Step('step-3', '.step-3'),
                new Step('step-4', '.step-4'),
                new Step('step-5', '.step-5'),
                new Step('step-6', '.step-6')
            ]),
            stepSelected : ko.observable(new Step('step-1', '.step-1')),
            user : ko.observable(new User('U1', 'User 1')),
            begin :  function () {
                (<any>$('#wizard')).begin();
            },
            end : function () {
                $('#wizard').end();
            },
            next : function () {
                $('#wizard').steps('next');
                //console.log("next");
            },
            previous : function () {
                $('#wizard').steps('previous');
            },
            getCurrentStep : function () {
                alert($('#wizard').steps('getCurrentIndex'));
            },
            gotoStep : function () {
                var index = this.stepList().indexOf(this.stepSelected());
                $('#wizard').setStep(index);
            },
        }
    }; // developer's view model
    this.bind(vm);
    
});