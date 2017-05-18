module nts.uk.pr.view.qpp021.f {

    import RefundPaddingTwoDto = service.model.RefundPaddingTwoDto;

    export module viewmodel {
        export class ScreenModel {
            refundPaddingTwoModel: RefundPaddingTwoModel;
            splitLineOutput: KnockoutObservable<any>;
            sizeLimitOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            constructor() {
                var self = this;
                self.refundPaddingTwoModel = new RefundPaddingTwoModel();
                self.splitLineOutput = ko.observableArray<SelectionModel>([
                    new SelectionModel('0', 'する'),
                    new SelectionModel('1', 'しない')
                ]);
                self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findRefundPadding().done(data => {
                    self.refundPaddingTwoModel.updateDataDto(data);
                    dfd.resolve(self);
                }).fail(function(error) {
                    console.log(error);
                });
                return dfd.promise();
            }

            /**
             * Event when click save button.
             */
            public onSaveBtnClicked(): void {
                let self = this;
                // Validate.
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                // Save.
                service.saveRefundPadding(self.refundPaddingTwoModel.toDto()).done(function(){
                    self.onCloseBtnClicked();
                }).fail(function(){
                    self.onCloseBtnClicked();    
                })
            }

            /**
             * Event when click close button.
             */
            public onCloseBtnClicked(): void {
                nts.uk.ui.windows.close();
            }
            
            private previewRefundPaddingTwo(): void {
                console.log('YES');
                service.previewRefundPaddingTwo();    
            }

        }

        export class RefundPaddingTwoModel {

            /** The upper area padding top. */
            upperAreaPaddingTop: KnockoutObservable<number>;

            /** The under area padding top. */
            underAreaPaddingTop: KnockoutObservable<number>;

            /** The padding left. */
            paddingLeft: KnockoutObservable<number>;

            /** The break line margin. */
            breakLineMargin: KnockoutObservable<number>;

            /** The is show break line. */
            isShowBreakLine: KnockoutObservable<number>;

            constructor() {
                this.upperAreaPaddingTop = ko.observable(0);
                this.underAreaPaddingTop = ko.observable(0);
                this.paddingLeft = ko.observable(0);
                this.breakLineMargin = ko.observable(0);
                this.isShowBreakLine = ko.observable(0);
            }

            updateDataDto(dto: RefundPaddingTwoDto): void {
                this.upperAreaPaddingTop(dto.upperAreaPaddingTop);
                this.underAreaPaddingTop(dto.underAreaPaddingTop);
                this.paddingLeft(dto.paddingLeft);
                this.breakLineMargin(dto.breakLineMargin);
                this.isShowBreakLine(dto.isShowBreakLine);
            }

            toDto(): RefundPaddingTwoDto {
                var dto: RefundPaddingTwoDto;
                dto = new RefundPaddingTwoDto();
                dto.upperAreaPaddingTop = this.upperAreaPaddingTop();
                dto.underAreaPaddingTop = this.underAreaPaddingTop();
                dto.paddingLeft = this.paddingLeft();
                dto.breakLineMargin = this.breakLineMargin();
                dto.isShowBreakLine = this.isShowBreakLine();
                return dto;
            }
        }

        /**
         *  Class SelectionModel.
         */
        export class SelectionModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}