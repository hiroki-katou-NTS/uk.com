module nts.uk.at.view.kaf006.shr.component1.viewmodel {

    @component({
        name: 'kaf006-shr-component1',
        template: `
            <div id="kaf006-shr-component1" class="control-group ui-iggrid right-panel-block">
                <div class="header" data-bind="text: $i18n('KAF006_97')"></div>
                <div class="content">
                    <table class="space-between-table clickable-row left-aligned">
                        <tbody>
                            <!-- ko if: $parent.condition22 -->
                            <tr data-bind="click: openKDL005">
                                <td><div data-bind="text: $i18n('KAF006_70')"></div></td>
                                <td><span data-bind="text: $parent.subVacaHourRemain"></span></td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition23 -->
                            <tr data-bind="click: openKDL009">
                                <td><div data-bind="text: $i18n('KAF006_71')"></div></td>
                                <td><span data-bind="text: $parent.subVacaRemain"></span></td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition21 -->
                            <tr data-bind="click: openKDL020">
                                <td><div data-bind="text: $i18n('KAF006_69')"></div></td>
                                <td>
                                    <span data-bind="text: $parent.yearRemain"></span>
                                    <div data-bind="text: $parent.grantDaysOfYear" style="font-size: 10px"></div>
                                </td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition24 -->
                            <tr data-bind="click: openKDL029">
                                <td><div data-bind="text: $i18n('KAF006_72')"></div></td>
                                <td><span data-bind="text: $parent.remainingHours"></span></td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition19_1Over60 -->
                            <tr data-bind="click: openKDL017">
                                <td><div data-bind="text: $i18n('Com_ExsessHoliday')"></div></td>
                                <td><span data-bind="text: $parent.over60HHourRemain"></span></td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition19_1ChildNursing -->
                            <tr data-bind="click: openKDL051">
                                <td><div data-bind="text: $i18n('Com_ChildNurseHoliday')"></div></td>
                                <td><span data-bind="text: $parent.childNursingRemain"></span></td>
                            </tr>
                            <!-- /ko -->
                            <!-- ko if: $parent.condition19_1Nursing -->
                            <tr data-bind="click: openKDL052">
                                <td><div data-bind="text: $i18n('Com_CareHoliday')"></div></td>
                                <td><span data-bind="text: $parent.nursingRemain"></span></td>
                            </tr>
                            <!-- /ko -->
                        </tbody>
                    </table>
                </div>
            </div>
        `
    })

    class Kaf006Component1ViewModel extends ko.ViewModel {
        created(params: any) {

        }

        mounted() {

        }

        openKDL020() {
            ko.contextFor(this.$el).$data.openKDL020();
        }

        openKDL029() {
            ko.contextFor(this.$el).$data.openKDL029();
        }

        openKDL005() {
            ko.contextFor(this.$el).$data.openKDL005();
        }

        openKDL051() {
            ko.contextFor(this.$el).$data.openKDL051();
        }

        openKDL052() {
            ko.contextFor(this.$el).$data.openKDL052();
        }

        openKDL017() {
            ko.contextFor(this.$el).$data.openKDL017();
        }

        openKDL009() {
            ko.contextFor(this.$el).$data.openKDL009();
        }
    }
}