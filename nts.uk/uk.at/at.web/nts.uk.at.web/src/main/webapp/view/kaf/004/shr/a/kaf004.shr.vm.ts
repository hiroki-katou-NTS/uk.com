module nts.uk.at.view.kaf004_ref.shr.a.viewmodel {

    const template = `
                <div class="cell" style="width: 120px; vertical-align: top;">
                    <!-- A6_1 -->
                    <div data-bind="ntsFormLabel: {
                            required: true,
                            text: $i18n('KAF004_13')
                        }"></div>
                </div>
                <div class="cell">
                    <table class="table_content">
                        <tr>
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_2 -->
                                <span class="label" data-bind="text: $i18n('KAF004_69')"></span>

                                <!-- A6_3 -->
                                <span class="label" id="label-A6_3" data-bind="text: $vm.workManagement.scheAttendanceTime"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_4 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                    name: $i18n('KAF004_64'),
                                    value: $vm.workManagement.workTime,
                                    option: {timeWithDay: true, width: '90'},
                                    constraint: 'TimeWithDayAttr',
                                    enable: $vm.application().prePostAtr() && !$vm.delete1()
                                }" />
                                </span>

                                <!-- A6_5 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_6 -->
                                <span class="label" data-bind="text: $i18n('KAF004_56'), visible: $vm.condition8()"></span>

                                <!-- A6_7 -->
                                <span data-bind="ntsCheckBox: {
                                    checked: $vm.delete1,
                                    text: $i18n('KAF004_57'),
                                }, visible: $vm.condition8()"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_8 -->
                                <span class="label" data-bind="text: $i18n('KAF004_70')"></span>

                                <!-- A6_9 -->
                                <span class="label" id="label-A6_9" data-bind="text: $vm.workManagement.scheWorkTime"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_10 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                    name: $i18n('KAF004_65'),
                                    value: $vm.workManagement.leaveTime,
                                    option: {timeWithDay: true, width: '90'},
                                    constraint: 'TimeWithDayAttr',
                                    enable: $vm.application().prePostAtr() && !$vm.delete2()
                                }" />
                                </span>

                                <!-- A6_11 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_12 -->
                                <span class="label" data-bind="text: $i18n('KAF004_56'), visible: $vm.condition8()"></span>

                                <!-- A6_13 -->
                                <span data-bind="ntsCheckBox: {
                                    checked: $vm.delete2,
                                    text: $i18n('KAF004_58'),
                                    }, visible: $vm.condition8()"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: $vm.condition2()">
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_14 -->
                                <span class="label" data-bind="text: $i18n('KAF004_71')"></span>

                                <!-- A6_15 -->
                                <span class="label" id="label-A6_15" data-bind="text: $vm.workManagement.scheAttendanceTime2"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_16 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                    name: $i18n('KAF004_66'),
                                    value: $vm.workManagement.workTime2,
                                    option: {timeWithDay: true, width: '90'},
                                    constraint: 'TimeWithDayAttr',
                                    enable: $vm.application().prePostAtr() && !$vm.delete3()
                                }" />
                                </span>

                                <!-- A6_17 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_18 -->
                                <span class="label" data-bind="text: $i18n('KAF004_56'), visible: $vm.condition2() && $vm.condition8()"></span>

                                <!-- A6_19 -->
                                <span data-bind="ntsCheckBox: {
                                    checked: $vm.delete3,
                                    text: $i18n('KAF004_60'),
                                    }, visible: $vm.condition2() && $vm.condition8()"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: $vm.condition2()">
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_20 -->
                                <span class="label" data-bind="text: $i18n('KAF004_72')"></span>

                                <!-- A6_21 -->
                                <span class="label" id="label-A6_21" data-bind="text: $vm.workManagement.scheWorkTime2"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_22 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                    name: $i18n('KAF004_67'),
                                    value: $vm.workManagement.leaveTime2,
                                    option: {timeWithDay: true, width: '90'},
                                    constraint: 'TimeWithDayAttr',
                                    enable: $vm.application().prePostAtr() && !$vm.delete4()
                                }" />
                                </span>

                                <!-- A6_23 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_24 -->
                            <span class="label" data-bind="text: $i18n('KAF004_56'), visible: $vm.condition2() && $vm.condition8()"></span>

                            <!-- A6_25 -->
                            <span data-bind="ntsCheckBox: {
                                    checked: $vm.delete4,
                                    text: $i18n('KAF004_61'),
                                    }, visible: $vm.condition2() && $vm.condition8()"></span>
                            </td>
                        </tr>
                    </table>
                </div>
                `
    @component({
        name: 'kaf004_share',
        template: template
    })

    class Kaf004ShareViewModel extends ko.ViewModel {

        created(params: any) {
            const vm = this;
        }

        mounted() {

        }
    }
}