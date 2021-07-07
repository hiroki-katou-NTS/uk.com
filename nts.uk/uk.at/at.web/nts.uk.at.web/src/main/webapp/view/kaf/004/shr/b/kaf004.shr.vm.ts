module nts.uk.at.view.kaf004_ref.shr.b.viewmodel {
    import WorkManagement = nts.uk.at.view.kaf004_ref.shr.common.viewmodel.WorkManagement;

    @component({
        name: 'kaf004_share',
        template: `
        <div class="table" style="margin-bottom: 5px">
            <div class="cell col-1" style="width: 120px; vertical-align: top;">
                <!-- A6_1 -->
                <div data-bind="ntsFormLabel: {
                        required: true,
                        text: $i18n('KAF004_13')
                    }"></div>
            </div>
            <div class="cell">
                <table class="table_content">
                    <tbody>
                        <tr>
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_2 -->
                                <span class="label" data-bind="text: $i18n('KAF004_69')"></span>

                                <!-- A6_3 -->
                                <span class="label" id="label-A6_3"
                                    data-bind="text: $parent.workManagement.scheAttendanceTime"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_4 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_64'),
                                value: $parent.workManagement.workTime,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS(!$parent.delete1() && ($parent.outputMode() === 1))
                            }" />
                                </span>
                                <!-- A6_5 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_6 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: $parent.condition9()"></span>

                                <!-- A6_7 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.delete1,
                                text: $i18n('KAF004_57'),
                                enable: $parent.outputMode() === 1
                                }, visible: $parent.condition9()"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_8 -->
                                <span class="label" data-bind="text: $i18n('KAF004_70')"></span>

                                <!-- A6_9 -->
                                <span class="label" id="label-A6_9" data-bind="text: $parent.workManagement.scheWorkTime"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_10 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_65'),
                                value: $parent.workManagement.leaveTime,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS(!$parent.delete2() && ($parent.outputMode() === 1))
                            }" />
                                </span>

                                <!-- A6_11 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_12 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: $parent.condition9()"></span>

                                <!-- A6_13 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.delete2,
                                text: $i18n('KAF004_58'),
                                enable: $parent.outputMode() === 1
                                }, visible: $parent.condition9()"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: ko.toJS($parent.condition2())">
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_14 -->
                                <span class="label" data-bind="text: $i18n('KAF004_71')"></span>

                                <!-- A6_15 -->
                                <span class="label" id="label-A6_15"
                                    data-bind="text: $parent.workManagement.scheAttendanceTime2"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_16 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_66'),
                                value: $parent.workManagement.workTime2,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS(!$parent.delete3() && ($parent.outputMode() === 1))
                            }" />
                                </span>

                                <!-- A6_17 -->
                                <span class="label" data-bind="text: $i18n('KAF004_54')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_18 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: $parent.condition2() && $parent.condition9()"></span>

                                <!-- A6_19 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.delete3,
                                text: $i18n('KAF004_60'),
                                enable: $parent.outputMode() === 1
                                }, visible: $parent.condition2() && $parent.condition9()"></span>
                            </td>
                        </tr>
                        <tr data-bind="visible: ko.toJS($parent.condition2())">
                            <td class="time-padding" style="width: 125px">
                                <!-- A6_20 -->
                                <span class="label" data-bind="text: $i18n('KAF004_72')"></span>

                                <!-- A6_21 -->
                                <span class="label" id="label-A6_21" data-bind="text: $parent.workManagement.scheWorkTime2"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_22 -->
                                <span>
                                    <input data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF004_67'),
                                value: $parent.workManagement.leaveTime2,
                                option: {timeWithDay: true, width: '90'},
                                constraint: 'TimeWithDayAttr',
                                enable: ko.toJS(!$parent.delete4() && ($parent.outputMode() === 1))
                            }" />
                                </span>

                                <!-- A6_23 -->
                                <span class="label" data-bind="text: $i18n('KAF004_55')"></span>
                            </td>
                            <td class="time-padding">
                                <!-- A6_24 -->
                                <span class="label"
                                    data-bind="text: $i18n('KAF004_56'), visible: $parent.condition2() && $parent.condition9()"></span>

                                <!-- A6_25 -->
                                <span data-bind="ntsCheckBox: {
                                checked: $parent.delete4,
                                text: $i18n('KAF004_61'),
                                enable: $parent.outputMode() === 1
                                }, visible: $parent.condition2() && $parent.condition9()"></span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
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