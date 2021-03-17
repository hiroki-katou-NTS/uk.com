/// <reference path="../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdp.share {

    interface Params {
    }

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
                    <button class="icon" data-bind="ntsIcon: { no: 160, width: 30, height: 30 }">
                    </button>
                </div>
            </div>
            <div class="content" data-bind="i18n: warningMessage"></div>
            <div>
                <button class="icon" data-bind="ntsIcon: { no: 161, width: 30, height: 30 }">
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

        created(params: Params) {
        }

        mounted() {
        }

		openPDialog(){
			const vm = this;
			vm.$window.modal('at', '/view/kdp/003/p/index.xhtml');
		}

        openRDialog(){
			const vm = this;
			vm.$window.modal('at', '/view/kdp/003/r/index.xhtml');
		}
    }
}
