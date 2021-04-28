module nts.uk.at.view.kaf006.shr.component1.viewmodel {

    @component({
        name: 'kaf006-shr-component1',
        template: `
        <div id="kaf006-shr-component1" data-bind="visible: $parent.selectedType() !== 6 && ($parent.condition21 || $parent.condition22 || $parent.condition23 || $parent.condition24)" 
            style="border-bottom: 2px solid #B1B1B1; padding-bottom: 25px; margin: 15px 15px 0">
            <div class="table" style="padding-bottom: 10px;">
                <div class="cell" data-bind="text: $i18n('KAF006_97')" style="font-weight: bold"></div>
            </div>
            <div class="panel panel-frame" style="margin: 0 10px">
                <div class="table" style="width: 100%;">
                    <!-- A14_2 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_70'), ntsFormLabel: {}, visible: $parent.condition22"></div>
                    <!-- A14_6 -->
                    <div class="text-right cell" data-bind="visible: $parent.condition22">
                        <a class="hyperlink" href="" data-bind="text: $i18n('KAF006_46', [$parent.subHdRemain()]), click: openKDL005"></a>
                    </div>
                </div>
                <hr style="width: 90%;" data-bind="visible: $parent.condition22">
                <div class="table" style="width: 100%;">
                    <!-- A14_3 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_71'), ntsFormLabel: {}, visible: $parent.condition23"></div>
                    <!-- A14_7 -->
                    <div class="text-right cell" data-bind="visible: $parent.condition23">
                        <a class="hyperlink" href="" data-bind="text: $i18n('KAF006_46', [$parent.subVacaRemain()]), click: openKDL009"></a>
                    </div>
                </div>
                <hr style="width: 90%;" data-bind="visible: $parent.condition23">
                <div class="table" style="width: 100%;">
                    <!-- A14_1 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_69'), ntsFormLabel: {}, visible: $parent.condition21"></div>
                    <!-- A14_5 -->
                    <div class="text-right cell" data-bind="visible: $parent.condition21">
                        <a class="hyperlink" href="" data-bind="text: $i18n('KAF006_46', [$parent.yearRemain()]), click: openKDL020"></a>
                    </div>
                </div>
                <hr style="width: 90%;" data-bind="visible: $parent.condition21 && $parent.condition24">
                <div class="table" style="width: 100%;">
                    <!-- A14_4 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_72'), ntsFormLabel: {}, visible: $parent.condition24"></div>
                    <!-- A14_8 -->
                    <div class="text-right cell" data-bind="visible: $parent.condition24">
                        <a class="hyperlink" href="" data-bind="text: $i18n('KAF006_46', [$parent.remainingHours()]), click: openKDL029"></a>
                    </div>
                </div>
            </div>
        </div>
        <div id="kaf006-shr-component1" data-bind="visible: $parent.selectedType() === 6" style="border-bottom: 2px solid #B1B1B1; padding-bottom: 25px; margin: 15px 15px 0">
            <div class="table" style="padding-bottom: 10px;">
                <div class="cell" data-bind="text: $i18n('KAF006_97')" style="font-weight: bold"></div>
            </div>
            <div class="panel panel-frame" style="margin: 0 10px">
                <div class="table" style="width: 100%;">
                    <!-- A9_18 -->
                    <div class="text-left cell" data-bind="text: $i18n('Com_ExsessHoliday'), ntsFormLabel: {}"></div>
                    <!-- A9_19 -->
                    <div class="text-right cell"><a class="hyperlink" href="" data-bind="text: $parent.over60HHourRemain, click: openKDL017"></a></div>
                </div>
                <hr style="width: 90%;">
                <div class="table" style="width: 100%;">
                    <!-- A9_20 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_30'), ntsFormLabel: {}"></div>
                    <!-- A9_21 -->
                    <div class="text-right cell">
                        <a class="hyperlink" href="" data-bind="text: $parent.subVacaHourRemain, click: openKDL005"></a>
                    </div>
                </div>
                <hr style="width: 90%;">
                <div class="table" style="width: 100%;">
                    <!-- A9_22 -->
                    <div class="text-left cell" data-bind="text: $i18n('KAF006_29'), ntsFormLabel: {}"></div>
                    <!-- A9_23 -->
                    <div class="text-right cell">
                        <a class="hyperlink" href="" data-bind="text: $parent.timeYearLeave, click: openKDL020"></a>
                    </div>
                </div>
                <hr style="width: 90%;">
                <div class="table" style="width: 100%;">
                    <!-- A9_24 -->
                    <div class="text-left cell" data-bind="text: $i18n('Com_ChildNurseHoliday'), ntsFormLabel: {}"></div>
                    <!-- A9_25 -->
                    <div class="text-right cell"><a class="hyperlink" href="" data-bind="text: $parent.childNursingRemain, click: openKDL051"></a></div>
                </div>
                <hr style="width: 90%;">
                <div class="table" style="width: 100%;">
                    <!-- A9_26 -->
                    <div class="text-left" data-bind="text: $i18n('Com_CareHoliday'), ntsFormLabel: {}"></div>
                    <!-- A9_27 -->
                    <div class="text-right"><a class="hyperlink" href="" data-bind="text: $parent.nursingRemain, click: openKDL052"></a></div>
                </div>
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