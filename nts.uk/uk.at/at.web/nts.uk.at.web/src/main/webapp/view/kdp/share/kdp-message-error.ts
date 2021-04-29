/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kdp.share {

    const API = {
    };

    const template = `
    <div class="kdp-message-error">
        <div class="company" data-bind="style: { 'color': headOfficeNotice.textColor, 
                                'background-color': headOfficeNotice.backGroudColor }">
            <span data-bind="i18n: headOfficeNotice.title"></span>
            <span class="text-company" data-bind:"i18n: headOfficeNotice.contentMessager"></span>
        </div>
        <div data-bind="style: { 'color': workplaceNotice.textColor, 
                                    'background-color': workplaceNotice.backGroudColor }">
            <div class="workPlace">
                <div class="title">
                    <div class="name-title">
                        <div style:"box-sizing: border-box" data-bind="i18n: workplaceNotice.title"></div>
                    </div>
                    <div class="btn-title">
                        <button class="icon" data-bind="ntsIcon: { no: 160, width: 30, height: 30 }, click: events.registerNoti.click">
                        </button>
                    </div>
                </div>
                <div class="content">
                    <div class="text-content" data-bind="i18n: workplaceNotice.contentMessager"></div>
                        <button class="btn-content" data-bind="ntsIcon: { no: 161, width: 30, height: 30 }, click: events.shoNoti.click">
                        </button>
                <div>
            </div>
        </div>
    </div>
    <style>
        .kdp-message-error {
            max-width: 700px;
            padding: 0px 5px;
        }

        .kdp-message-error .company {
            padding: 3px;
            height: 66px;
            max-height: 66px;
        }

        .kdp-message-error .workPlace {
            padding: 3px;
            margin-top: 5px;
            height: 66px;
            max-height: 66px;
        }

        .kdp-message-error .workPlace .title {
            box-sizing: border-box;
            width: 64px;
            height: 100px;
            float: left;
        }

        .kdp-message-error .workPlace .text-company {
            box-sizing: border-box;
            margin-right:40px;
            max-height:66px;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        .kdp-message-error .workPlace .title .name-title {
            box-sizing: border-box;
        }
        
        .kdp-message-error .workPlace .content {
            box-sizing: border-box;
            position: relative;
            margin-left: 40px;
        }

        .kdp-message-error .workPlace .text-content {
            box-sizing: border-box;
            margin-right:40px;
            max-height:66px;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        .kdp-message-error .workPlace .btn-content {
            position: absolute;
            top: 0px;
            right: 6px;
            border: none;
            background-color: transparent;
            box-shadow: 0 0px rgb(0 0 0 / 40%);
        }

        .icon {
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
                if (params.messageNoti) {
                    vm.messageNoti(params.messageNoti);
                }

                if(ko.unwrap(params.notiSet)) {
                    vm.notiSet(ko.unwrap(params.notiSet).noticeSetDto);
                }
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
                        } else {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                headOfficeNoticeList[0].notificationMessage);
                        }

                    } else {

                        if (ko.unwrap(vm.notiSet)) {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                '',
                                ko.unwrap(vm.notiSet));
                        } else {
                            vm.headOfficeNotice.update(DestinationClassification.ALL,
                                '');
                        }
                    }

                    if (workplaceNoticeList.length > 0) {

                        if (ko.unwrap(vm.notiSet)) {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                workplaceNoticeList[0].notificationMessage,
                                ko.unwrap(vm.notiSet));
                        } else {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                workplaceNoticeList[0].notificationMessage);
                        }

                    } else {


                        if (ko.unwrap(vm.notiSet)) {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                '',
                                ko.unwrap(vm.notiSet));
                        } else {
                            vm.workplaceNotice.update(DestinationClassification.WORKPLACE,
                                '');
                        }
                    }
                })
                .always(() => {
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

            if (type == DestinationClassification.ALL) {
                if (setting) {
                    vm.title(setting.companyTitle + ':');
                    vm.textColor(setting.comMsgColor.textColor);
                    vm.backGroudColor(setting.comMsgColor.backGroundColor);
                }
            }

            if (type == DestinationClassification.WORKPLACE) {
                if (setting) {
                    vm.title(setting.wkpTitle + ':');
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
