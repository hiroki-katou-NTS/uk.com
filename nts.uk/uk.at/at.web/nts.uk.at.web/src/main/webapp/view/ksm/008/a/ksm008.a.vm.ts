module nts.uk.at.ksm008.a {


    const PATH_API = {
        getList: 'screen/at/ksm008/alarm_contidion/list',
        getMsg: 'screen/at/ksm008/alarm_contidion/getMsg',
        register: 'screen/at/ksm008/alarm_contidion/register',
    }

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        alarmList: KnockoutObservableArray<AlarmConditionKO> = ko.observableArray([]);
        tabindex: KnockoutObservable<number> = ko.observable(3);

        getDefaultMsg(code: string, subCode: string, message: MessageKO) {
            const vm = this;
            vm.$ajax(`${PATH_API.getMsg}/${code}`).then((res: any) => {
                const subCondition: { message: { defaultMsg: string, message: string } } = _.find(res.subConditions, {subCode});
                if (subCondition != undefined) {
                    message.message(subCondition.message.defaultMsg);
                }
            });
        }

        register() {
            const vm = this;
            const data: RegisterData = {alarmCheckCondition: []};
            ko.mapping.toJS(vm.alarmList).forEach((item: AlarmCondition) => {
                let msg: { code: string; msgLst: Array<any> };
                msg = {code: item.code, msgLst: []};
                item.subConditions.forEach(subItem => {
                    msg.msgLst.push({subCode: subItem.subCode, message: subItem.message.message});
                })
                data.alarmCheckCondition.push(msg);
            });
            vm.$ajax(PATH_API.register, data).then(() => {
                console.log("ok")
            });
        }

        constructor(props: any) {
            super();
        }


        toScreen(code: KnockoutObservable<string>) {
            const vm = this;
            switch (code()) {
                case "01":
                    vm.$jump("../b/index.xhtml");
                    break;
                case "02":
                    vm.$jump("../c/index.xhtml");
                    break;
                case "03":
                    vm.$jump("../f/index.xhtml");
                    break;
                case "04":
                    vm.$jump("../d/index.xhtml");
                    break;
                case "05":
                    vm.$jump("../g/index.xhtml");
                    break;
                case "06":
                    vm.$jump("../i/index.xhtml");
                    break;
                case "07":
                    vm.$jump("../k/index.xhtml");
                    break;
            }
        }

        created() {
            const vm = this;
            vm.$ajax(PATH_API.getList).then(data => {
                data.forEach((item: any) => {
                    this.alarmList.push(ko.mapping.fromJS(item));
                });
                // if ($("#pg-name").text() == '') $("#pg-name").text("KSM008A " + nts.uk.resource.getText("KSM008_1"));
            });
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({height: 480, width: 1200});
        }
    }

    interface RegisterData {
        alarmCheckCondition: Array<any>
    }

    /*Knockout*/
    interface AlarmConditionKO {
        code: KnockoutObservable<string>,
        name: KnockoutObservable<string>,
        subConditions: KnockoutObservableArray<SubConditionKO>
    }

    interface SubConditionKO {
        subCode: string,
        description: KnockoutObservable<string>,
        message: MessageKO,
    }

    interface MessageKO {
        defaultMsg: KnockoutObservable<string>,
        message: KnockoutObservable<string>,
    }


    /*Ts*/

    interface AlarmCondition {
        code: string,
        name: string,
        subConditions: Array<SubCondition>
    }

    interface SubCondition {
        subCode: string,
        description: string,
        message: Message,
    }

    interface Message {
        defaultMsg: string,
        message: string,
    }
}