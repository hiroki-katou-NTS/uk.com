module nts.uk.pr.view.qpp021.e {

    import RefundPaddingOnceDto = service.model.RefundPaddingOnceDto;
    import PaymentReportQueryDto = service.model.PaymentReportQueryDto;

    export module viewmodel {
        export class ScreenModel {

            refundPaddingOnceModel: RefundPaddingOnceModel;
            sizeLimitOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            printtype: number;
            constructor() {
                var self = this;
                self.refundPaddingOnceModel = new RefundPaddingOnceModel();
                self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                self.printtype = nts.uk.ui.windows.getShared("printtype");
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

            public previewRefundPaddingOnce() {
                var self = this;
                var pageLayout: string = 'E1';
                switch(self.printtype){
                    case 0:
                        pageLayout = 'E1';
                        break;
                    case 4:
                        pageLayout = 'E2';
                        break;
                    case 5:
                        pageLayout = 'E3';
                        break;
                }
                service.previewRefundPaddingOnce(pageLayout);
            }

            public save() {
                let self = this;
                // Validate.
                if ($('.nts-input').ntsError('hasError')) {
                    return;
                }
                // Save.
                service.saveRefundPadding(self.refundPaddingOnceModel.toDto()).done(function() {
                    self.close();
                }).fail(function(error) {
                    self.close();
                });
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