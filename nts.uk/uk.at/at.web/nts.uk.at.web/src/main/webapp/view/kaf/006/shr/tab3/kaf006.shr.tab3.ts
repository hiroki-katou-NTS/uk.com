module nts.uk.at.view.kaf006.shr.tab3.viewmodel {

    @component({
        name: 'kaf006-shr-tab3',
        template: `
        <div id="kaf006tab3">
            <div style="margin-top: 10px;" data-bind="ntsCheckBox: {
                checked: isChangeWorkHour,
                text: $i18n('KAF006_18')
            }, visible: $parent.condition11"></div>
            <hr style="width: 700px; margin-inline-start: initial;" data-bind="visible: $parent.condition11" />
            <div class="table" data-bind="visible: $parent.condition11">
                <div class="cell col-1"></div>
                <div class="cell">
                    <div style="padding-bottom: 5px;">
                        <div class="cell col-1">
                            <div class="valign-center cell" data-bind="ntsFormLabel:{ required: false }, text: $i18n('KAF006_19')"></div>
                        </div>
                        <div class="cell">
                            <button style="margin-right: 5px;" data-bind="text: $i18n('KAF006_20'), enable: $parent.isChangeWorkHour, click: openKDL003"></button>
                        </div>
                        <div class="cell" data-bind="text: $parent.selectedWorkTimeDisp"></div>
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
                            }, enable: $parent.condition30" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_59'),
                                value: $parent.endTime1,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.condition30" />
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
                            }, enable: $parent.condition30" />
                            <span data-bind="text: $i18n('KAF006_47')"></span>
                            <input style="width: 120px;" data-bind="ntsTimeWithDayEditor: {
                                name: $i18n('KAF006_61'),
                                value: $parent.endTime2,
                                constraint: 'TimeWithDayAttr',
                                options: {
                                    timeWithDay: true,
                                    width: '120'
                                }
                            }, enable: $parent.condition30" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="table">
                <div class="cell">
                    <div class="table">
                        <div class="cell col-1" data-bind="visible: $parent.condition6">
                            <div class="cell valign-center required" data-bind="ntsFormLabel:{ required: true }, text: $i18n('KAF006_33')"></div>
                        </div>
                        <div class="cell" data-bind="visible: $parent.condition6">
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
                            checked: $parent.isCheckMourn,
                            text: $i18n('KAF006_34'),
                            enable: $parent.isDispMourn
                        }, visible: $parent.condition7"></div>
                    </div>
                    <div class="table" style="margin-top: 5px;" data-bind="visible: $parent.condition8">
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
                <div class="cell vertical-align" data-bind="visible: $parent.condition9">
                    <div style="margin-left: 10px; white-space: pre-wrap;" class="panel panel-frame" data-bind="text: $parent.maxNumberOfDay"></div>
                </div>
            </div>
        </div>
        `
    })

    class Kaf006Tab3ViewModel extends ko.ViewModel {
        // workTypeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        // selectedWorkTypeCD: KnockoutObservable<any>;
        isChangeWorkHour: KnockoutObservable<boolean> = ko.observable(false);
        dateSpecHdRelationLst: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedDateSpec: KnockoutObservable<any> = ko.observable();
        relationshipReason: KnockoutObservable<string> = ko.observable();

        created(params: any) {
            const vm = this;

            if (params) {
                // vm.workTypeLst = params.workTypeLst;
                // vm.selectedWorkTypeCD = params.selectedWorkTypeCD;
                vm.dateSpecHdRelationLst = params.dateSpecHdRelationLst;
                vm.selectedDateSpec = params.selectedDateSpec;
                vm.relationshipReason = params.relationshipReason;
                vm.isChangeWorkHour = params.isChangeWorkHour;
            }
        }

        mounted() {

        }

        openKDL003() {
            ko.contextFor(this.$el).$data.openKDL003();
        }
    }
}