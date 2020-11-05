/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.kwr002.f {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const API = {
        findCopyAttendance: 'com/function/attendancerecord/duplicate/findCopyAttendance',
        executeCopy: 'com/function/attendancerecord/duplicate/executeCopy',
    }

    @bean()
    export class KWR002FViewModel extends ko.ViewModel {
        selectedCode: KnockoutObservable<string>;
        selectedName: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        duplicateCode: KnockoutObservable<string>;
        duplicateName: KnockoutObservable<string>;
        layoutId: string;
        selectionType: string;

        created() {
            const vm = this;
            vm.selectedCode = ko.observable('');
            vm.isEnable = ko.observable(true);
            vm.duplicateCode = ko.observable('');
            vm.duplicateName = ko.observable('');
            vm.isEditable = ko.observable(true);
            vm.selectedName  = ko.observable('');

            let dataFromScreenB = getShared('dataFromScreenB');
            vm.selectionType = dataFromScreenB.itemSelectedType;
            vm.layoutId = dataFromScreenB.layoutId;

            vm.$blockui('grayout');

            const param = {
                code: dataFromScreenB.code,
                name: dataFromScreenB.name,
                selectedType: dataFromScreenB.itemSelectedType,
                layoutId: dataFromScreenB.layoutId
            }

            vm.$ajax('at',API.findCopyAttendance, param)
                .then((response: DataInfoReturnDto) => {
                    vm.selectedCode(response.code);
                    vm.selectedName(response.name);
                })
                .always(() => vm.$blockui('hide'));

        }

        executeCopy() {
            let vm = this;
            $('.save-error').ntsError('check');
            if (!vm.duplicateCode() || !vm.duplicateName() || nts.uk.ui.errors.hasError()) {
                return;
            }

            let dataCopy = new AttendanceDuplicateDto({
                code: vm.selectedCode(),
                name: vm.selectedName(),
                duplicateCode: vm.duplicateCode(),
                duplicateName: vm.duplicateName(),
                selectedType: vm.selectionType,
                layoutId: vm.layoutId,
            });

            if (_.isEqual(vm.selectedCode(), vm.duplicateCode())) {
                return vm.$dialog.alert({ messageId: 'Msg_355' })
            }

            vm.$blockui('show');

            vm.$ajax('at', API.executeCopy, dataCopy).then(() => {
                vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
                    // Set shared param to share with screen B
                    setShared('duplicateItem', {
                        code: vm.duplicateCode(),
                        name: vm.duplicateName(),
                        layoutId: vm.layoutId
                    });
                    // Close screen
                    vm.$window.close()
                });
            })
            .fail(err => vm.$dialog.alert({ messageId: err.messageId }))
            .always(() => vm.$blockui('hide'));
        }

        closeDialog() {
            let vm = this;
            vm.$window.close();
        }

    }

    class AttendanceDuplicateDto {
        code: string;
        name: string;
        duplicateCode: string;
        duplicateName: string;
        selectedType: string;
        layoutId: string;

        constructor(init?: Partial<AttendanceDuplicateDto>) {
            $.extend(this, init);
        }
    }

    class DataInfoReturnDto {
        code: string;
        name: string;
    }

}