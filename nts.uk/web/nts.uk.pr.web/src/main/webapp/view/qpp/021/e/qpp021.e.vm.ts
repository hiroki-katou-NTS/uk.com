module nts.uk.pr.view.qpp021.e {

    import RefundPaddingOnceDto = service.model.RefundPaddingOnceDto;

    export module viewmodel {
        export class ScreenModel {

            refundPaddingOnceModel: RefundPaddingOnceModel;
            sizeLimitOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;

            constructor() {
                var self = this;
                self.refundPaddingOnceModel = new RefundPaddingOnceModel();
                self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
            }

            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findRefundPadding().done(data => {
                    self.refundPaddingOnceModel.updateDataDto(data);
                    dfd.resolve(self);
                }).fail(function(error) {
                    console.log(error);
                });
                return dfd.promise();
            }

            public preview() {

            }

            public save() {
                let self = this;
                // Validate.
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                // Save.
                service.saveRefundPadding(self.refundPaddingOnceModel.toDto());
            }

            public close(): void {
                nts.uk.ui.windows.close();
            }
        }

        export class RefundPaddingOnceModel {

            paddingTop: KnockoutObservable<number>;
            paddingLeft: KnockoutObservable<number>;

            constructor() {
                this.paddingTop = ko.observable(0);
                this.paddingLeft = ko.observable(0);
            }

            updateDataDto(dto: RefundPaddingOnceDto): void {
                this.paddingTop(dto.paddingTop);
                this.paddingLeft(dto.paddingLeft);
            }

            toDto(): RefundPaddingOnceDto {
                var dto: RefundPaddingOnceDto;
                dto = new RefundPaddingOnceDto();
                dto.paddingTop = this.paddingTop();
                dto.paddingLeft = this.paddingLeft();
                return dto;
            }
        }
    }
}