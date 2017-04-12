/// <reference path="../reference.ts"/>

module nts.uk.ui.errors {

    export class ErrorsViewModel {
        title: string;
        errors: KnockoutObservableArray<ErrorListItem>;
        option: any;
        occurs: KnockoutComputed<boolean>;
        allResolved: JQueryCallback;

        constructor() {
            this.title = "エラー一覧"
            this.errors = ko.observableArray([]);
            this.errors.extend({ rateLimit: 1 });
            this.option = ko.mapping.fromJS(new option.ErrorDialogOption());
            this.occurs = ko.computed(() => this.errors().length !== 0);
            this.allResolved = $.Callbacks();

            this.errors.subscribe(() => {
                if (this.errors().length === 0) {
                    this.allResolved.fire();
                }
            });

            this.allResolved.add(() => {
                this.hide();
            });
        }

        closeButtonClicked() {
        }

        open() {
            this.option.show(true);
        }

        hide() {
            this.option.show(false);
        }

        addError(error: ErrorListItem) {
            // defer無しでerrorsを呼び出すと、なぜか全てのKnockoutBindingHandlerのupdateが呼ばれてしまうので、
            // 原因がわかるまでひとまずdeferを使っておく
            //            _.defer(() => {
            var duplicate = _.filter(this.errors(), e => e.$control.is(error.$control) && e.message == error.message);
            if (duplicate.length == 0)
                this.errors.push(error);
            //            });
        }

        removeErrorByElement($element: JQuery) {
            // addErrorと同じ対応
            //            _.defer(() => {
            var removeds = _.filter(this.errors(), e => e.$control.is($element));
            this.errors.removeAll(removeds);
            //            });
        }
    }

    export interface ErrorListItem {
        tab?: string;
        location: string;
        message: string;
        $control?: JQuery;
    }

    export class ErrorHeader {
        name: string;
        text: string;
        width: number;
        visible: boolean;

        constructor(name: string, text: string, width: number, visible: boolean) {
            this.name = name;
            this.text = text;
            this.width = width;
            this.visible = visible;
        }
    }

    export function errorsViewModel(): ErrorsViewModel {
        return nts.uk.ui._viewModel.kiban.errorDialogViewModel;
    }

    export function show() {
        errorsViewModel().open();
    }

    export function hide() {
        errorsViewModel().hide();
    }

    export function add(error: ErrorListItem) {
        errorsViewModel().addError(error);
    }

    export function removeByElement($control: JQuery) {
        errorsViewModel().removeErrorByElement($control);
    }
}