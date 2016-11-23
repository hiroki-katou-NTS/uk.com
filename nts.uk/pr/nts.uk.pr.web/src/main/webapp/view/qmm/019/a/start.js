__viewContext.ready(function () {
    //import { ZipCodeValidator } from "./ZipCodeValidator";
    var vm = {
        treegrid: {
            items2: ko.observableArray([
                new qmm019.a.viewmodel.NodeTest('0001', 'サービス部', [
                    new qmm019.a.viewmodel.NodeTest('0001-1', 'サービス部1', []),
                    new qmm019.a.viewmodel.NodeTest('0001-2', 'サービス部2', []),
                    new qmm019.a.viewmodel.NodeTest('0001-3', 'サービス部3', []),
                    new qmm019.a.viewmodel.NodeTest('0001-4', 'サービス部4', [])]),
                new qmm019.a.viewmodel.NodeTest('0002', '開発部', [])]),
            selectedCode: ko.observableArray([]),
            singleSelectedCode: ko.observable(null),
            index: 0
        },
        textSearch: ko.observable('')
    }; // developer's view model
    this.bind(vm);
});
