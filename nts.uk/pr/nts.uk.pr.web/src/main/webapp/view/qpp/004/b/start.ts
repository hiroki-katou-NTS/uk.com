__viewContext.ready(function() {
    function User(code, name) {
        this.code = ko.observable(code);
        this.name = ko.observable(name);
        this.name.subscribe(function(val) { alert(val); });
    }
    function Step(id, content) {
        this.id = ko.observable(id);
        this.content = ko.observable(content);
    }
    function Node(code, name, childs) {
        this.code = code;
        this.name = name;
        this.nodeText = code + ' ' + name;
        this.childs = childs;
        this.custom = 'Random' + new Date().getTime();
    }
    //checkbox item
    function BoxModel(id, name) {
        this.id = ko.observable(id);
        this.name = ko.observable(name);
    }
    var vm = {
        wizard: {
            stepList: ko.observableArray([
                new Step('step-1', '.step-1'),
                new Step('step-2', '.step-2'),
                new Step('step-3', '.step-3'),
                new Step('step-4', '.step-4')
            ]),
            stepSelected: ko.observable(new Step('step-2', '.step-2')),
            user: ko.observable(new User('U1', 'User 1')),
            currentProcessingYearMonth: ko.observable('2016/11'),
            begin: function() {
                (<any>$('#wizard')).begin();
            },
            end: function() {
                $('#wizard').end();
            },
            next: function() {
                $('#wizard').steps('next');
                //console.log("next");
            },
            previous: function() {
                $('#wizard').steps('previous');
            },
            getCurrentStep: function() {
                alert($('#wizard').steps('getCurrentIndex'));
            },
            gotoStep: function() {
                var index = this.stepList().indexOf(this.stepSelected());
                $('#wizard').setStep(index);
            },
            step1: function() {
                $('#wizard').setStep(0);
            },
            step2: function() {
                $('#wizard').setStep(1);
            },
            step3: function() {
                $('#wizard').setStep(2);
            },
            step4: function() {
                $('#wizard').setStep(3);
            },
        },
        grid: {
            items1: ko.observableArray([
                new Node('A00000000001', '日通　社員１',[]), 
                new Node('A00000000002', '日通　社員2',[]), 
                new Node('A00000000003', '日通　社員3',[]), 
                new Node('A00000000004', '日通　社員4',[]), 
                new Node('A00000000005', '日通　社員5',[]), 
                new Node('A00000000006', '日通　社員6',[]), 
                new Node('A00000000007', '日通　社員7',[]), 
                new Node('A00000000008', '日通　社員8',[]), 
                new Node('A00000000009', '日通　社員9',[]), 
                new Node('A000000000010', '日通　社員１0',[]), 
            ]),
            selectedCode: ko.observableArray([]),
            singleSelectedCode: ko.observable(null),
            index: 0,
        },
    }; // developer's view model
    this.bind(vm);

});