/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdp.share {

    const API = {
    };

    const template = `
    <div class="kdp-message-error">
        <div class="info-message" data-bind="i18n: message1">
        </div>
        <div class="error">
            <div class="title" data-bind="i18n: 'KDP003_91'">:</div>
            <div class="content" data-bind="i18n: errorMessage"></div>
        </div>
        <div class="warning-message">
            <div class="title">
                <div data-bind="i18n: '店長より：'"></div>
                <div>
                    <button class="icon" data-bind="ntsIcon: { no: 160, width: 30, height: 30 }, click: events.registerNoti.click">
                    </button>
                </div>
            </div>
            <div class="content" data-bind="i18n: warningMessage"></div>
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

        message1: KnockoutObservable<string> = ko.observable('本部より：全職場に表示される会社のメッセージです。２行目改行の表示テストデータ１２３４５６７８９');
        warningMessage: KnockoutObservable<string> = ko.observable('職場管理者からのお願いです。この画面で編集するコメント。１２３４５６７８９０１２');
        errorMessage: KnockoutObservable<string> = ko.observable('職場管理者からのお願いです。この画面で編集するコメント。１２３４５６７８９０１２');

        events!: ClickEvent;

        created(params?: MessageParam) {
            const vm = this;

            debugger;

            if (params) {
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
    }

    export interface MessageParam {
        events?: ClickEvent;
    }

    export interface ClickEvent {
        registerNoti: () => void | {
            click: () => void;
        };
        shoNoti: () => void | {
            click: () => void;
        };
    }
}
