/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {

    const API = {
    };

    const template = `
    <div class="kdp-message-error">
        <div class="company" data-bind="style: { 'color': headOfficeNotice.textColor, 
                                'background-color': headOfficeNotice.backGroudColor }">
            <span data-bind="i18n: headOfficeNotice.title"></span>
            <span>:</span>
            <span data-bind:"i18n: headOfficeNotice.contentMessager"></span>
        </div>
        <div class="workPlace" data-bind="style: { 'color': workplaceNotice.textColor, 
                                    'background-color': workplaceNotice.backGroudColor }">
            <div data-bind="i18n: workplaceNotice.title"></div>
            <div>:</div>
            <div>
                <button class="icon" data-bind="ntsIcon: { no: 160, width: 30, height: 30 }, click: events.registerNoti.click">
                </button>
            </div>
            <span data-bind="i18n: workplaceNotice.contentMessager"></span>
            <div>
                <button class="icon" data-bind="ntsIcon: { no: 161, width: 30, height: 30 }, click: events.shoNoti.click">
                </button>
            </div>
        </div>
    </div>
    <style>
        .kdp-message-error {
            max-width: 720px;
        }

        .kdp-message-error .company {
            padding: 3px;
        }

        .kdp-message-error .workPlace {
            padding: 3px;
            margin-top: 5px;
        }

        .kdp-message-error .info-message {
            background: #E2F0D9;
            padding: 10px;
            font-weight: bold;
            margin:5px;
        }

        .kdp-message-error .warning-message {
            background: #E2F0D9;
            padding: 10px;
            font-weight: bold;
            margin:5px;
            padding-bottom: 23px;
        }

        .kdp-message-error .warning-message .title {
            box-sizing: border-box;
            float: left;
        }

        .kdp-message-error .warning-message .content {
            float: left;
            width: calc(100% - 105px);
        }

        .kdp-message-error .error {
            background: #E2F0D9;
            padding: 10px;
            font-weight: bold;
            margin:5px;
        }

        .kdp-message-error .error .content {
            text-align: center;
        }

        .kdp-message-error .error .title {
            box-sizing: border-box;
            float: left;
        }

        .kdp-message-error .warning-message .icon {
            border: none;
            background-color: transparent;
            box-shadow: 0 0px rgb(0 0 0 / 40%);
        }
        
    </style>
    `;

    @component({
        name: 'kdp-message-error',
        template
    })

    class BoxYear extends ko.ViewModel {

        // headOfficeNoticeList: KnockoutObservable<IDisplayResult> = ko.observable();

        // workplaceNoticeList: KnockoutObservable<DisplayResult> = ko.observable();

        // R1 本部見内容
        headOfficeNotice: Model = new Model(DestinationClassification.ALL);

        // R2 職場メッセージ
        workplaceNotice: Model = new Model(DestinationClassification.WORKPLACE);


        messageNoti: KnockoutObservable<IMessage> = ko.observable();
        notiSet: KnockoutObservable<INoticeSet> = ko.observable();

        events!: ClickEvent;

        created(params?: MessageParam) {
            const vm = this;

            if (params) {

                console.log(params);

                vm.messageNoti(params.messageNoti);
                vm.notiSet(ko.unwrap(params.notiSet).noticeSetDto);

                const { events } = params;

                if (events) {
                    // convert setting event to binding object
                    if (_.isFunction(events.registerNoti)) {
                        const click = events.registerNoti;

                        events.registerNoti = {
                            click
                        } as any;
                    }

                    // convert company event to binding object
                    if (_.isFunction(events.shoNoti)) {
                        const click = events.shoNoti;

                        events.shoNoti = {
                            click
                        } as any;
                    }

                    vm.events = events;
                } else {
                    vm.events = {
                        registerNoti: {
                            click: () => { }
                        } as any,
                        shoNoti: {
                            click: () => { }
                        } as any
                    };
                }
            }
        }

        mounted() {
            const vm = this;

            vm.$blockui('invisible')
                .then(() => {

                    let headOfficeNoticeList = _.filter(
                        ko.unwrap(ko.unwrap(vm.messageNoti)).messageNotices,
                        n => n.targetInformation.destination == DestinationClassification.ALL);
                    let workplaceNoticeList = _.filter(
                        ko.unwrap(ko.unwrap(vm.messageNoti)).messageNotices,
                        n => n.targetInformation.destination == DestinationClassification.WORKPLACE);

                    if (headOfficeNoticeList.length > 0) {

                        if (ko.unwrap(vm.notiSet)) {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                headOfficeNoticeList[0].notificationMessage,
                                ko.unwrap(vm.notiSet));
                        }else {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                headOfficeNoticeList[0].notificationMessage);
                        }

                    } else {

                        if (ko.unwrap(vm.notiSet)) {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                '',
                                ko.unwrap(vm.notiSet));
                        }else {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                '');
                        }
                    }

                    if (workplaceNoticeList.length > 0) {

                        if (ko.unwrap(vm.notiSet)) {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                workplaceNoticeList[0].notificationMessage,
                                ko.unwrap(vm.notiSet));
                        }else {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                workplaceNoticeList[0].notificationMessage);
                        }

                    } else {

                        
                        if (ko.unwrap(vm.notiSet)) {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                '',
                                ko.unwrap(vm.notiSet));
                        }else {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                '',);
                        }
                    }
                })
                .then(() => {
                    vm.$blockui('clear');
                });
        }
    }

    class Model {
        title: KnockoutObservable<string> = ko.observable('本部より');
        contentMessager: KnockoutObservable<string> = ko.observable('');
        textColor: KnockoutObservable<string> = ko.observable('#123123');
        backGroudColor: KnockoutObservable<string> = ko.observable('#123123');

        constructor(type: DestinationClassification) {
            const vm = this;

            vm.update(type)
        }

        public update(type: DestinationClassification, content?: string, setting?: INoticeSet) {
            const vm = this;

            console.log(setting);

            if (type == DestinationClassification.ALL) {
                if (setting) {
                    vm.title(setting.companyTitle);
                    vm.textColor(setting.comMsgColor.textColor);
                    vm.backGroudColor(setting.comMsgColor.backGroundColor);
                }
            }

            if (type == DestinationClassification.WORKPLACE) {
                if (setting) {
                    vm.title(setting.wkpTitle);
                    vm.textColor(setting.wkpMsgColor.textColor);
                    vm.backGroudColor(setting.wkpMsgColor.backGroundColor);
                }
            }

            if (content) {
                vm.contentMessager(content);
            }
        }
    }

    export interface MessageParam {
        events?: ClickEvent;
        notiSet: any;
        messageNoti: IMessage;
        viewShow: String;
    }

    export interface ClickEvent {
        registerNoti: () => void | {
            click: () => void;
        };
        shoNoti: () => void | {
            click: () => void;
        };
    }

    interface INoticeSet {
        comMsgColor: IColorSetting;
        companyTitle: string;
        personMsgColor: IColorSetting;
        wkpMsgColor: IColorSetting;
        wkpTitle: string;
        displayAtr: number;
    }

    interface IColorSetting {
        textColor: string;
        backGroundColor: string;
    }

    interface IMessage {
        messageNotices: IMessageNotice[];
    }

    interface IMessageNotice {
        creatorID: string;
        inputDate: Date;
        modifiedDate: Date;
        targetInformation: ITargetInformation;
        startDate: Date;
        endDate: Date;
        employeeIdSeen: string[];
        notificationMessage: string;
    }

    interface ITargetInformation {
        targetSIDs: string[];
        targetWpids: string[];
        destination: number | null;
    }

    enum DestinationClassification {
        // 0 全社員
        ALL = 0,
        // 1 職場選択
        WORKPLACE = 1,
        // 2 社員選択
        EMPLOYEE = 2
    }
}
