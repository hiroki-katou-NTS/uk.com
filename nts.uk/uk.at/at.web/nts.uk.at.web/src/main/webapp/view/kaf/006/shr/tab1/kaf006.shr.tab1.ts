module nts.uk.at.view.kaf006.shr.tab1.viewmodel {

    @component({
        name: 'kaf006-shr-tab1',
        template: `
        <div id="kaf006tab1" data-bind="visible: $parent.condition11">
            <div style="margin-top: 10px;" data-bind="ntsCheckBox: {
                checked: isChangeWorkHour,
                text: $i18n('KAF006_18')
            }"></div>
            <hr style="width: 700px; margin-inline-start: initial;" />
            <div class="table">
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
        </div>
        `
    })

    class Kaf006Tab1ViewModel extends ko.ViewModel {
        // workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        // selectedWorkTypeCD: KnockoutObservable<any>;
        isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);
        // startTime1: KnockoutObservable<number> = ko.observable();
        // endTime1: KnockoutObservable<number> = ko.observable();
        // startTime2: KnockoutObservable<number> = ko.observable();
        // endTime2: KnockoutObservable<number> = ko.observable();

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
    }
}