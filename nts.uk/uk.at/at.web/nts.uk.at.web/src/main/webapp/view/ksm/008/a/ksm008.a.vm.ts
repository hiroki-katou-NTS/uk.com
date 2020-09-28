module nts.uk.at.ksm008.a {

    const PATH_API = {
        getList: 'screen/at/ksm008/alarm_contidion/list'
    }

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        alarmList: KnockoutObservableArray<AlarmCondition> = ko.observableArray([]);

        toScreenB() {
            const vm = this;
            vm.$jump("../b/index.xhtml");
        }


        constructor(params: any) {
            super();
        }


        created() {
            const vm = this;
            vm.$ajax(PATH_API.getList).then(data => {
                console.log(data);
                vm.alarmList(data);
            });
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({height: 300, width: 1200});
        }


    }

    interface AlarmCondition {
        code: string,
        name: string,
        subConditionList: KnockoutObservableArray<SubCondition>
    }

    interface SubCondition {
        subCode: string,
        description: string,
        message: string,
    }
}