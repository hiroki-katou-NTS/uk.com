module nts.uk.pr.view.qpp021.g {
    import option = nts.uk.ui.option;

    import RefundPaddingThreeDto = service.model.RefundPaddingThreeDto;

    export module viewmodel {

        export class ScreenModel {
            refundPaddingThreeModel: RefundPaddingThreeModel;
            sizeLimitOption: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            spliteLineOutput: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.refundPaddingThreeModel = new RefundPaddingThreeModel();
                self.sizeLimitOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                self.spliteLineOutput = ko.observableArray([
                    { code: 0, name: 'する' },
                    { code: 1, name: 'しない' },
                ]);
            }

            //start page
            startPage(): JQueryPromise<this> {
                var self = this;
                var dfd = $.Deferred<this>();
                service.findRefundPadding().done(data => {
                    self.refundPaddingThreeModel.updateDataDto(data);
                    dfd.resolve(self);
                }).fail(function(error){
                    console.log(error);    
                });
                return dfd.promise();
            }

            //save RefundPaddingThree
            saveRefundPaddingThree(): void {
                var self = this;
                service.saveRefundPadding(self.refundPaddingThreeModel.toDto());
            }
            
            closeRefundPaddingThree() : void{
                nts.uk.ui.windows.close();    
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
                this.upperAreaPaddingTop(dto.underAreaPaddingTop);
                this.middleAreaPaddingTop(dto.middleAreaPaddingTop);
                this.underAreaPaddingTop(dto.underAreaPaddingTop);
                this.paddingLeft(dto.paddingLeft);
                this.breakLineMarginTop(dto.breakLineMarginTop);
                this.breakLineMarginButtom(dto.breakLineMarginButtom);
                this.isShowBreakLine(dto.isShowBreakLine);
            }

            toDto(): RefundPaddingThreeDto {
                var dto: RefundPaddingThreeDto;
                dto = new RefundPaddingThreeDto();
                dto.upperAreaPaddingTop = this.upperAreaPaddingTop();
                dto.middleAreaPaddingTop = this.middleAreaPaddingTop();
                dto.underAreaPaddingTop = this.underAreaPaddingTop();
                dto.paddingLeft = this.paddingLeft();
                dto.breakLineMarginTop = this.breakLineMarginTop();
                dto.breakLineMarginButtom = this.breakLineMarginButtom();
                dto.isShowBreakLine = this.isShowBreakLine();
                return dto;
            }
        }
    }
}