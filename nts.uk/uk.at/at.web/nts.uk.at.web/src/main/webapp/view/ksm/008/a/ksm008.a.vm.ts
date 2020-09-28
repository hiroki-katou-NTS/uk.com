module nts.uk.at.ksm008.a {

    const PATH_API = {
        getList: 'screen/at/ksm008/alarm_contidion/list'
    }

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {

        simpleValue: KnockoutObservable<string>;
        alarmList: KnockoutObservableArray<AlarmCondition> =  ko.observableArray([]);

        constructor(params: any) {
            super();
            var vm = this;
            vm.simpleValue = ko.observable("123");
            vm.$ajax(PATH_API.getList).then(data => vm.alarmList = data);
        }

        created() {
            var vm = this;
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({height: 300, width: 1200});
        }

    }

    interface AlarmCondition {

    }
}