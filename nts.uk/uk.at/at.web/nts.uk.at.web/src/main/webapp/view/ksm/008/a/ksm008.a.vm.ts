/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.ksm008.a {

    const PATH_API = {
        getList: 'screen/at/ksm008/alarm_contidion/list',
        getMsg: 'screen/at/ksm008/alarm_contidion/getMsg',
        register: 'screen/at/ksm008/alarm_contidion/register',
    }

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {
        alarmList: KnockoutObservableArray<AlarmConditionKO> = ko.observableArray([]);

        getDefaultMsg(code: string, subCode: string, message: MessageKO) {
            const vm = this;

            vm.$blockui('grayout');
            vm.$ajax(`${PATH_API.getMsg}/${code}`).done((res: any) => {
                const subCondition: { message: { defaultMsg: string, message: string } } = _.find(res.subConditions, {subCode});
                if (subCondition != undefined) {
                    $("#" + code + subCode).ntsError("clear");
                    message.message(subCondition.message.defaultMsg);
                }
            }).always(() => vm.$blockui('hide'));
        }

        register() {
            const vm = this;

            vm.$validate(['.nts-input']).then((valid: boolean) => {
                if (valid) {
                    vm.$blockui('grayout');
                    const data: RegisterData = {alarmCheckCondition: []};
                    ko.mapping.toJS(vm.alarmList).forEach((item: AlarmCondition) => {
                        let msg: { code: string; msgLst: Array<any> };
                        msg = {code: item.code, msgLst: []};
                        item.subConditions.forEach(subItem => {
                            msg.msgLst.push({subCode: subItem.subCode, message: subItem.message.message});
                        });
                        data.alarmCheckCondition.push(msg);
                    });
                    vm.$ajax(PATH_API.register, data).done(() => {
                        vm.$dialog.info({messageId: "Msg_15"});
                    }).fail((err) => {
                        vm.$dialog.error(err);
                    }).always(() => vm.$blockui('hide'));
                }
            });
        }

        constructor(props: any) {
            super();
        }


        toScreen(code: KnockoutObservable<string>) {
            const vm = this;
            const selectedCode = ko.toJS(code());
            const dataTransfer = {code: ko.toJS(code())};
            switch (dataTransfer.code) {
                case "01":
                    vm.$jump("/view/ksm/008/b/index.xhtml", dataTransfer);
                    break;
                case "02":
                    vm.$jump("/view/ksm/008/c/index.xhtml", dataTransfer);
                    break;
                case "03":
                    vm.$jump("/view/ksm/008/f/index.xhtml", dataTransfer);
                    break;
                case "04":
                    vm.$jump("/view/ksm/008/d/index.xhtml", dataTransfer);
                    break;
                case "05":
                    vm.$jump("/view/ksm/008/g/index.xhtml", dataTransfer);
                    break;
                case "06":
                    vm.$jump("/view/ksm/008/i/index.xhtml", dataTransfer);
                    break;
                case "07":
                    vm.$jump("/view/ksm/008/k/index.xhtml", dataTransfer);
                    break;
            }
        }

        created() {
            const vm = this;
            vm.$blockui('grayout');
            vm.$ajax(PATH_API.getList).then(data => {
                if (data && data.length) {
                    const listMenu = _.orderBy(data, ['code'], ['asc']);
                    listMenu.forEach((item: any) => {
                        item.isNoBorder = ko.observable(_.isEmpty(item.subConditions));
                        _.forEach(item.subConditions, subCondition => {
                            let explanation: string = "" + subCondition.explanation;
                            explanation = explanation.replace(/\\r/g, "\r");
                            explanation = explanation.replace(/\\n/g, "\n");
                            subCondition.explanation = explanation;
                        });
                        switch (item.code) {
                            case "01":
                                item.image = '../resource/01.png';
                                break;
                            case "02":
                                item.image = '../resource/02.png';
                                break;
                            case "03":
                                item.image = '../resource/03.png';
                                break;
                            case "04":
                                item.image = '../resource/04.png';
                                break;
                            case "05":
                                item.image = '../resource/05.png';
                                break;
                            case "06":
                                item.image = '../resource/06.png';
                                break;
                            case "07":
                                item.image = '../resource/07.png';
                                break;
                            default:
                                item.image = '';
                        }
                        this.alarmList.push(ko.mapping.fromJS(item));
                    });
                }
            }).always(() => {
                vm.$blockui('hide');
                let listMessage = $('.message');
                if (listMessage && listMessage.length) {
                    listMessage[0].focus();
                    listMessage[0].setSelectionRange(0, 0);
                }
            });
        }

        mounted() {
            $("#fixed-table").ntsFixedTable({height: 480, width: 1200});
        }

        checkDisplayButton(data: any) {
            const lstCodeButtonDisplay = ["01", "02", "03", "04", "05", "06", "07"];

            if (_.includes(lstCodeButtonDisplay, data())) {
                return true;
            }
            return false;
        }
    }

    interface RegisterData {
        alarmCheckCondition: Array<any>
    }

    /*Knockout*/
    interface AlarmConditionKO {
        /** ????????? */
        code: KnockoutObservable<string>,
        /** ????????? */
        name: KnockoutObservable<string>,

        image: string,
        /** ????????????????????? */
        subConditions: KnockoutObservableArray<SubConditionKO>,

        isNoBorder: KnockoutObservable<boolean>
    }

    interface SubConditionKO {
        /** ??????????????? */
        subCode: string,
        /** ?????? */
        description: KnockoutObservable<string>,
        /** ???????????????????????? */
        message: MessageKO,
    }

    interface MessageKO {
        /** ????????????????????? */
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