module nts.uk.at.view.kaf004_ref.shr.b.viewmodel {
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;

    @component({
        name: 'kaf004_share',
        template: `
                    <div class="fixed-flex-layout-left">
                <!-- A6_1 -->
                <div data-bind="ntsFormLabel: {
                        required: true,
                        text: $i18n('KAF004_13')
                    }"></div>
            </div>
            <div class="fixed-flex-layout-right">
                <table class="table_content">
                    <tbody>
                        <tr>
                            <td class="padding-5">
                                <!-- A6_2 -->
                                <span class="label" data-bind="text: $i18n('KAF004_21')"></span>

                                <!-- A6_3 -->
                                <span class="label" id="label-A6_3"
                                    data-bind="text: $parent.workManagement.scheAttendanceTime"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_4 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_42'),
                                value: $parent.workManagement.workTime,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS($parent.condition8(1))
                            }" />
                                </span>
                                <!-- A6_5 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_6 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: ko.toJS($parent.condition10Display(1)) && ko.toJS($parent.condition9())"></span>

                                <!-- A6_7 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.lateOrEarlyInfo1().isCheck,
                                text: $i18n('KAF004_57'),
                                enable: ko.toJS($parent.condition10Activation(1))
                                }, visible: ko.toJS($parent.condition10Display(1)) && ko.toJS($parent.condition9())"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="padding-5">
                                <!-- A6_8 -->
                                <span class="label" data-bind="text: $i18n('KAF004_21')"></span>

                                <!-- A6_9 -->
                                <span class="label" id="label-A6_9" data-bind="text: $parent.workManagement.scheWorkTime"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_10 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_42'),
                                value: $parent.workManagement.leaveTime,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS($parent.condition8(2))
                            }" />
                                </span>

                                <!-- A6_11 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_12 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: ko.toJS($parent.condition10Display(2)) && ko.toJS($parent.condition9())"></span>

                                <!-- A6_13 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.lateOrEarlyInfo2().isCheck,
                                text: $i18n('KAF004_58'),
                                enable: ko.toJS($parent.condition10Activation(2))
                                }, visible: ko.toJS($parent.condition10Display(2)) && ko.toJS($parent.condition9())"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: ko.toJS($parent.condition2())">
                            <td class="padding-5">
                                <!-- A6_14 -->
                                <span class="label" data-bind="text: $i18n('KAF004_21')"></span>

                                <!-- A6_15 -->
                                <span class="label" id="label-A6_15"
                                    data-bind="text: $parent.workManagement.scheAttendanceTime2"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_16 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_42'),
                                value: $parent.workManagement.workTime2,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS($parent.condition8(3))
                            }" />
                                </span>

                                <!-- A6_17 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_18 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible:ko.toJS($parent.condition2_9_10(3))"></span>

                                <!-- A6_19 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.lateOrEarlyInfo3().isCheck,
                                text: $i18n('KAF004_60'),
                                enable: ko.toJS($parent.condition10Activation(3))
                                }, visible: ko.toJS($parent.condition2_9_10(3))"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: ko.toJS($parent.condition2())">
                            <td class="padding-5">
                                <!-- A6_20 -->
                                <span class="label" data-bind="text: $i18n('KAF004_21')"></span>

                                <!-- A6_21 -->
                                <span class="label" id="label-A6_21" data-bind="text: $parent.workManagement.scheWorkTime2"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_22 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_42'),
                                value: $parent.workManagement.leaveTime2,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS($parent.condition8(4))
                            }" />
                                </span>

                                <!-- A6_23 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="padding-5">
                                <!-- A6_24 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: ko.toJS($parent.condition2_9_10(4))"></span>

                                <!-- A6_25 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.lateOrEarlyInfo4().isCheck,
                                text: $i18n('KAF004_61'),
                                enable: ko.toJS($parent.condition10Activation(4))
                                }, visible: ko.toJS($parent.condition2_9_10(4))"></span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        `
    })

    class Kaf004ShareViewModel extends ko.ViewModel {
        // workManagement: WorkManagement;
        created(params: any) {
            const vm = this;
        }

        mounted() {

        }
    }
}