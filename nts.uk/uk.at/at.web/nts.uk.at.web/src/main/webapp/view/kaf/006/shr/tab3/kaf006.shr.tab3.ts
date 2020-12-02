module nts.uk.at.view.kaf006.shr.tab3.viewmodel {

    @component({
        name: 'kaf006-shr-tab3',
        template: `
        <div id="kaf006tab1">
            <div class="table">
                <div class="cell col-1">
                    <div class="cell valign-center required" data-bind="ntsFormLabel:{ required: true }, text: $i18n('KAF006_16')"></div>
                </div>
                <div class="cell">
                    <div style="vertical-align: bottom;" data-bind="ntsComboBox: {
                        name: $i18n('KAF006_16'),
                        options: workTypeLst,
                        optionsValue: 'workTypeCode',
                        optionsText: 'name',
                        value: selectedWorkTypeCD,
                        required: true
                    }"></div>
                </div>
            </div>
            <div style="margin-top: 10px;" data-bind="ntsCheckBox: {
                checked: isChagneWorkHour,
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
                            <button style="margin-right: 5px;" data-bind="text: $i18n('KAF006_20')"></button>
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
                                value: startTime1,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_59'),
                                value: endTime1,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }" />
                        </div>
                    </div>
                    <div style="padding-bottom: 5px;">
                        <div class="cell col-1">
                            <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_23')"></div>
                        </div>
                        <div class="cell">
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_60'),
                                value: startTime2,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_61'),
                                value: endTime2,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="table">
                <div class="cell">
                    <div class="table">
                        <div class="cell col-1">
                            <div class="cell valign-center required" data-bind="ntsFormLabel:{ required: true }, text: $i18n('KAF006_33')"></div>
                        </div>
                        <div class="cell">
                            <div style="vertical-align: bottom;" data-bind="ntsComboBox: {
                                name: $i18n('KAF006_33'),
                                options: dateSpecHdRelationLst,
                                optionsValue: 'relationCD',
                                optionsText: 'relationName',
                                value: selectedDateSpec,
                                required: true
                            }"></div>
                        </div>
                        <div class="cell valign-center" data-bind="ntsCheckBox: {
                            checked: true,
                            text: $i18n('KAF006_34'),
                            enable: $parent.isDispMourn()
                        }"></div>
                    </div>
                    <div class="table" style="margin-top: 5px;">
                        <div class="cell col-1">
                            <div class="cell valign-center required" data-bind="ntsFormLabel:{ required: true }, text: $i18n('KAF006_43')"></div>
                        </div>
                        <div class="cell">
                            <input style="width: 350px; vertical-align: bottom;" data-bind="ntsTextEditor: {
                                value: relationshipReason,
                                name: $i18n('KAF006_43'),
                                option: {
                                    placeholder: $i18n('KAF006_45')
                                },
                
                            }" />
                        </div>
                    </div>
                </div>
                <div class="cell vertical-align">
                    <div style="margin-left: 10px;" class="panel panel-frame">休暇申請起動時の表示情報．特別休暇表示情報．上限日数</div>
                </div>
            </div>
        </div>
        `
    })

    class Kaf006Tab3ViewModel extends ko.ViewModel {
        workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedWorkTypeCD: KnockoutObservable<any> = ko.observable();
        isChagneWorkHour: KnockoutObservable<boolean> = ko.observable(true);
        startTime1: KnockoutObservable<number> = ko.observable();
        endTime1: KnockoutObservable<number> = ko.observable();
        startTime2: KnockoutObservable<number> = ko.observable();
        endTime2: KnockoutObservable<number> = ko.observable();
        dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedDateSpec: KnockoutObservable<any> = ko.observable();
        relationshipReason: KnockoutObservable<string> = ko.observable();

        created(params: any) {
            const vm = this;

            if (params) {
                vm.workTypeLst = params.workTypeLst;
                vm.selectedWorkTypeCD = params.selectedWorkTypeCD;
                vm.dateSpecHdRelationLst = params.dateSpecHdRelationLst;
                vm.selectedDateSpec = params.selectedDateSpec;
            }
        }

        mounted() {

        }
    }
}