module nts.uk.pr.view.qpp021.g {
    import option = nts.uk.ui.option;

    import RefundPaddingThreeDto = service.model.RefundPaddingThreeDto;

    export module viewmodel {

        export class ScreenModel {
            refundPaddingThreeModel: RefundPaddingThreeModel;
            marginTop: KnockoutObservable<string>;
            marginMiddle: KnockoutObservable<string>;
            marginXyz: KnockoutObservable<string>;
            marginLeft: KnockoutObservable<string>;
            lineCorrect: KnockoutObservable<string>;
            underLine: KnockoutObservable<string>;
            textEditorOption: KnockoutObservable<any>;
            spliteLineOutput: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<number>;

            constructor() {
                var self = this;
                self.refundPaddingThreeModel = new RefundPaddingThreeModel();
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.spliteLineOutput = ko.observableArray([
                    { code: 0, name: 'する' },
                    { code: 1, name: 'しない' },
                ]);
            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                dfd.resolve(self);
                return dfd.promise();
            }
        }

        export class RefundPaddingThreeModel {
            /** The upper area padding top. */
            upperAreaPaddingTop: KnockoutObservable<number>;

            /** The middle area padding top. */
            middleAreaPaddingTop: KnockoutObservable<number>;

            /** The under area padding top. */
            underAreaPaddingTop: KnockoutObservable<number>;

            /** The padding left. */
            paddingLeft: KnockoutObservable<number>;

            /** The break line margin top. */
            breakLineMarginTop: KnockoutObservable<number>;

            /** The break line margin buttom. */
            breakLineMarginButtom: KnockoutObservable<number>;

            /** The is show break line. */
            isShowBreakLine: KnockoutObservable<number>;

            constructor() {
                this.upperAreaPaddingTop = ko.observable(0);
                this.middleAreaPaddingTop = ko.observable(0);
                this.underAreaPaddingTop = ko.observable(0);
                this.paddingLeft = ko.observable(0);
                this.breakLineMarginTop = ko.observable(0);
                this.breakLineMarginButtom = ko.observable(0);
                this.isShowBreakLine = ko.observable(0);
            }

            updateDataDto(dto: RefundPaddingThreeDto) {
                this.upperAreaPaddingTop(dto.upperAreaPaddingTop);
                this.middleAreaPaddingTop(dto.middleAreaPaddingTop);
                this.paddingLeft(dto.paddingLeft);
                this.breakLineMarginTop(dto.breakLineMarginTop);
                this.breakLineMarginButtom(dto.breakLineMarginButtom);
                this.isShowBreakLine(dto.isShowBreakLine);
            }
        }
    }
}