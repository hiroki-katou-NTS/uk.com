module nts.uk.at.view.kaf006.shr.component1.viewmodel {

    @component({
        name: 'kaf006-shr-component1',
        template: `
        <div id="kaf006-shr-component1" data-bind="visible: $parent.selectedType() !== 6">
            <table>
                <tr class="text-center bg-green">
                    <!-- A14_1 -->
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_69'), visible: $parent.condition21"></td>
                    <!-- A14_2 -->
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_70'), visible: $parent.condition22"></td>
                    <!-- A14_3 -->
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_71'), visible: $parent.condition23"></td>
                    <!-- A14_4 -->
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_72'), visible: $parent.condition24"></td>
                </tr>
                <tr class="text-right">
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.yearRemain, visible: $parent.condition21"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.subHdRemain, visible: $parent.condition22"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.subVacaRemain, visible: $parent.condition23"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.remainingHours, visible: $parent.condition24"></td>
                </tr>
            </table>
        </div>
        <div id="kaf006-shr-component1" data-bind="visible: $parent.selectedType() === 6">
            <table>
                <tr class="text-center bg-green">
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('Com_ExsessHoliday')"></td>
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_30')"></td>
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('KAF006_29')"></td>
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('Com_ChildNurseHoliday')"></td>
                    <td class="table-border py-10 text-center" data-bind="text: $i18n('Com_CareHoliday')"></td>
                </tr>
                <tr class="text-right">
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.over60HHourRemain"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.subVacaHourRemain"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.timeYearLeave"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.childNursingRemain"></td>
                    <td class="table-border text-right py-5 text-center" data-bind="text: $parent.nursingRemain"></td>
                </tr>
            </table>
        </div>
        `
    })

    class Kaf006Component1ViewModel extends ko.ViewModel {
        created(params: any) {

        }

        mounted() {

        }
    }
}