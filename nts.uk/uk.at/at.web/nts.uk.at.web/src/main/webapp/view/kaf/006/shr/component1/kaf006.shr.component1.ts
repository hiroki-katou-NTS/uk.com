module nts.uk.at.view.kaf006.shr.component1.viewmodel {

    @component({
        name: 'kaf006-shr-component1',
        template: `
        <div>
            <table>
                <tr class="text-center bg-green">
                    <!-- A14_1 -->
                    <td class="table-border py-10" data-bind="text: $i18n('KAF006_69')"></td>
                    <!-- A14_2 -->
                    <td class="table-border py-10" data-bind="text: $i18n('KAF006_70')"></td>
                    <!-- A14_3 -->
                    <td class="table-border py-10" data-bind="text: $i18n('KAF006_71')"></td>
                    <!-- A14_4 -->
                    <td class="table-border py-10" data-bind="text: $i18n('KAF006_72')"></td>
                </tr>
                <tr class="text-right">
                    <td class="table-border text-right py-5">1</td>
                    <td class="table-border text-right py-5">2</td>
                    <td class="table-border text-right py-5">3</td>
                    <td class="table-border text-right py-5">4</td>
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