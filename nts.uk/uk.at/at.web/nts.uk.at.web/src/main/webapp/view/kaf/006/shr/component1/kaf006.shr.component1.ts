module nts.uk.at.view.kaf006.shr.component1.viewmodel {

    @component({
        name: 'kaf006-shr-component1',
        template: `
        <div id="kaf006-shr-component1" class="control-group" 
            data-bind="visible: $parent.selectedType() !== 6 && ($parent.condition21 || $parent.condition22 || $parent.condition23 || $parent.condition24)" 
            style="border-bottom: 2px solid #B1B1B1; padding-bottom: 25px; margin: 15px 15px 0">
            <div class="cell" style="font-weight: bold" data-bind="text: $i18n('KAF006_97')"></div>
            <div class="space-between-table ">
                <!-- ko if: $parent.condition22 -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_70')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.subVacaHourRemain, click: openKDL005"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition23 -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_71')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.subVacaRemain, click: openKDL009"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition21 -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div>
                        <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_69')"></div>
                        <div data-bind="text: $parent.grantDaysOfYear" style="font-size: 0.7rem; margin-left: 12px"></div>
                    </div>
                    <a class="hyperlink" href="" data-bind="text: $parent.yearRemain, click: openKDL020"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition24 -->
                <div class="row-underline"  style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_72')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.remainingHours, click: openKDL029"></a>
                </div>
                <!-- /ko -->
            </div>
        </div>
        <div id="kaf006-shr-component1" class="control-group" 
            data-bind="visible: $parent.selectedType() === 6 && ($parent.condition19Over60 || $parent.condition19Annual || $parent.condition19ChildNursing || $parent.condition19Nursing || $parent.condition19Substitute)" 
            style="border-bottom: 2px solid #B1B1B1; padding-bottom: 25px; margin: 15px 15px 0">
            <div class="cell" style="font-weight: bold" data-bind="text: $i18n('KAF006_97')"></div>
            <div class="space-between-table ">
                <!-- ko if: $parent.condition19Over60 -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('Com_ExsessHoliday')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.over60HHourRemain, click: openKDL017"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition19Substitute -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_30')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.subVacaHourRemain, click: openKDL005"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition19Annual -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div>
                        <div data-bind="ntsFormLabel: {}, text: $i18n('KAF006_29')"></div>
                        <div data-bind="text: $parent.grantDaysOfYear" style="font-size: 0.7rem; margin-left: 12px"></div>
                    </div>
                    <a class="hyperlink" href="" data-bind="text: $parent.timeYearLeave, click: openKDL020"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition19ChildNursing -->
                <div class="row-underline"  style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('Com_ChildNurseHoliday')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.childNursingRemain, click: openKDL051"></a>
                </div>
                <!-- /ko -->
                <!-- ko if: $parent.condition19Nursing -->
                <div class="row-underline" style="display: flex; justify-content: space-between">
                    <div data-bind="ntsFormLabel: {}, text: $i18n('Com_CareHoliday')"></div>
                    <a class="hyperlink" href="" data-bind="text: $parent.nursingRemain, click: openKDL052"></a>
                </div>
                <!-- /ko -->
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