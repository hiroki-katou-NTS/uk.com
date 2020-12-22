module nts.uk.at.view.kaf006.shr.tab4.viewmodel {
    import Kaf006AViewModel =  nts.uk.at.view.kaf006_ref.a.viewmodel.Kaf006AViewModel;

    @component({
        name: 'kaf006-shr-tab4',
        template: `
        <div id="kaf006tab4">
            <div style="margin-top: 10px;" data-bind="ntsCheckBox: {
                checked: isChangeWorkHour,
                text: $i18n('KAF006_18'),
                visible: $parent.condition11
            }"></div>
            <hr style="width: 700px; margin-inline-start: initial;" data-bind="visible: $parent.condition11" />
            <div class="table" data-bind="visible: $parent.condition11">
                <div class="cell col-1"></div>
                <div class="cell">
                    <div style="padding-bottom: 5px;">
                        <div class="cell col-1">
                            <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_19')"></div>
                        </div>
                        <div class="cell">
                            <button style="margin-right: 5px;" data-bind="text: $i18n('KAF006_20'), enable: $parent.isChangeWorkHour"></button>
                        </div>
                        <div class="cell" data-bind="text: $i18n('KAF006_21')"></div>
                    </div>
                    <div style="padding-bottom: 5px;">
                        <div class="cell col-1">
                            <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_22')"></div>
                        </div>
                        <div class="cell">
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_58'),
                                value: $parent.startTime1,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.checkCondition30" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_59'),
                                value: $parent.endTime1,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.checkCondition30" />
                        </div>
                    </div>
                    <div style="padding-bottom: 5px;" data-bind="visible: $parent.condition12">
                        <div class="cell col-1">
                            <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_23')"></div>
                        </div>
                        <div class="cell">
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_60'),
                                value: $parent.startTime2,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.checkCondition30" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_61'),
                                value: $parent.endTime2,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.checkCondition30" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="table">
                <div class="cell col-1">
                    <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_88')"></div>
                </div>
                <button style="width: 60px; margin-right: 5px;" class="cell" data-bind="text: $i18n('KAF006_50'), click: openKDL036"></button>
                <div class="cell" data-bind="text: $i18n('KAF006_89')"></div>
            </div>
            <div class="table" style="margin: 10px 0;">
                <div class="cell col-1"></div>
                <div class="cell">
                    <table>
                        <thead>
                            <tr class="text-center bg-green">
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_53')"></th>
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_87')"></th>
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_55')"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="py-10">0</td>
                                <td class="py-10">0</td>
                                <td class="py-10">0</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="table">
                <div class="cell col-1">
                    <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_48')"></div>
                </div>
                <button style="width: 60px; margin-right: 5px;" class="cell" data-bind="text: $i18n('KAF006_50'), click: openKDL035"></button>
                <div class="cell" data-bind="text: $i18n('KAF006_81')"></div>
            </div>
            <div class="table" style="margin: 10px 0;">
                <div class="cell col-1"></div>
                <div class="cell">
                    <table>
                        <thead>
                            <tr class="bg-green">
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_52')"></th>
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_46')"></th>
                                <th style="width: 100px;" class="py-10" data-bind="text: $i18n('KAF006_55')"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="py-10">0</td>
                                <td class="py-10">0</td>
                                <td class="py-10">0</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        `
    })

    class Kaf006Tab4ViewModel extends ko.ViewModel {
        // workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        // selectedWorkTypeCD: KnockoutObservable<any>;
        isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;

            if (params) {
                // vm.workTypeLst = params.workTypeLst;
                // vm.selectedWorkTypeCD = params.selectedWorkTypeCD;
                vm.isChangeWorkHour = params.isChangeWorkHour;
            }
        }

        mounted() {

        }

        public openKDL036() {
            ko.contextFor(this.$el).$parent.content.openKDL036();
        }

        public openKDL035() {
            ko.contextFor(this.$el).$parent.content.openKDL035();
        }
    }
}