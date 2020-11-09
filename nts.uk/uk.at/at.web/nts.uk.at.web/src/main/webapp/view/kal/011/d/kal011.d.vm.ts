module nts.uk.at.kal011.d {

    const PATH_API = {}

    @bean()
    export class Kal011DViewModel extends ko.ViewModel {

        modalDto: ModalDto = new ModalDto();
        isSuspenDed: KnockoutObservable<boolean> = ko.observable(false);

        constructor(props: any) {
            super();
            const vm = this;
            let modalData = nts.uk.ui.windows.getShared("KAL011DModalData");
            vm.modalDto.executionStartDateTime(modalData.executionStartDateTime);
            vm.modalDto.processingState(modalData.processingState);
            console.log(modalData);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        clickSuspension() {
            const vm = this;
            vm.isSuspenDed(true);
        }

        /**
         * This function is responsible to close the modal
         *
         * @return type void         *
         * */
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        /**
         * This function is responsible to perform modal data action
         *
         * @return type void         *
         * */
        decide(): any {
            var vm = this;
        }
    }

    class ModalDto {
        /*実行開始日時*/
        executionStartDateTime: KnockoutObservable<any>;
        /*処理状態*/
        processingState: KnockoutObservable<any>;
        /*実行終了日時*/
        executionEndtDateTime: KnockoutObservable<any>;
        /*経過時間*/
        elapsedTime: KnockoutObservable<any>;

        constructor() {
            this.executionStartDateTime = ko.observable('');
            this.processingState = ko.observable('');
            this.executionEndtDateTime = ko.observable('');
            this.elapsedTime = ko.observable('');
        }
    }
}