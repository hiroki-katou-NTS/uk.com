module nts.uk.at.view.kaf007_ref.shr.viewmodel {

    @component({
        name: 'kaf007-share',
        template: `
        <div id="kaf007-share">
            <div class="table" style="margin-bottom: 5px;">
                <div class="cell" style="position: absolute; margin-left: 120px; margin-top: 4px;">
                    <!--A6_5 勤務就業選択-->
                    <button style="height: 70px;" id="workSelect-kaf007" data-bind="text: $i18n('KAF007_24'),
                                click: openKDL003Click, 
                                enable: isEdit">
                    </button>
                </div>
                <div class="cell valign-center">
                    <div class="table" style="margin-bottom: 5px;">
                        <div class="cell col-1">
                            <!--A6_1 勤務種類-->
                            <div class="cell valign-center" data-bind="ntsFormLabel:{required:true}, text: $i18n('KAF007_22')">
                            </div>
                        </div>
                        <div class="cell col-2" style="margin-bottom: 5px;">
                            <!--A6_2 勤務種類名-->
                            <div class="cell valign-center">
                                <span data-bind="text: appWorkChange.workTypeCode"></span>
                                <span data-bind="text: ' '"></span>
                                <span data-bind="text: appWorkChange.workTypeName"></span>
                            </div>
                        </div>
                    </div>
                    <div class="table" style="margin-top: 5px;">
                        <div class="cell col-1">
                            <!--A6_3 就業時間帯-->
                            <div class="cell valign-center" data-bind="ntsFormLabel:{required:true}, text: $i18n('KAF007_23')">
                            </div>
                        </div>
                        <div class="cell col-2" style="margin-top: 5px;">
                            <!--A6_4 就業時間帯名-->
                            <div class="cell valign-center">
                                <span data-bind="text: appWorkChange.workTimeCode"></span>
                                <span data-bind="text: ' '"></span>
                                <span data-bind="text: appWorkChange.workTimeName"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="table" style="margin-top: 5px;"
                data-bind="visible: reflectWorkChange.whetherReflectAttendance() == 1">
                <div class="cell col-1">
                    <!-- A7 -->
                    <div class="cell valign-center" data-bind="ntsFormLabel: {required:true}, text: $i18n('KAF007_13')"></div>
                </div>
                <div class="cell valign-center col-3">
                    <!-- A7_1 -->
                    <input id="time1Start" class="inputTime" data-bind="ntsTimeWithDayEditor: { 
                                                    constraint:'TimeWithDayAttr',
                                                    value: appWorkChange.startTime1,
                                                    readonly: false,
                                                    required: true,
                                                    name: $i18n('KAF007_62'),
                                                    enable: (isEdit() && model() !== null && model().setupType() !== null && model().setupType() === 0 && model().reflectWorkChangeAppDto().whetherReflectAttendance === 1) }" />
                </div>
                <!-- A7_2 -->
                <div class="cell" style="padding-right: 5px;" data-bind="text: $i18n('KAF007_14')"></div>
                <div class="cell valign-center">
                    <!-- A7_3 -->
                    <input id="time1End" class="inputTime" data-bind="ntsTimeWithDayEditor: { 
                                                    constraint:'TimeWithDayAttr',
                                                    value: appWorkChange.endTime1,
                                                    readonly: false,
                                                    required: true,
                                                    name: $i18n('KAF007_63'),
                                                    enable: (isEdit() && model() !== null && model().setupType() !== null && model().setupType() === 0 && model().reflectWorkChangeAppDto().whetherReflectAttendance === 1) }" />
                    <!-- A7_6	 -->
                    <span class="label comment2" data-bind="text: $vm.comment2"></span>
                </div>
            </div>
            <div class="table" style="margin-top: 5px;" data-bind="visible: $vm.appDispInfoStartupOutput().appDispInfoNoDateOutput.managementMultipleWorkCycles">
                <div class="cell col-1">
                    <!-- A8 -->
                    <div class="cell valign-center" data-bind="ntsFormLabel: {required:false}, text: $i18n('KAF007_20')"></div>
                </div>
                <div class="cell valign-center col-3">
                    <!-- A8_1 -->
                    <input id="time2Start" class="inputTime" data-bind="ntsTimeWithDayEditor: { 
                                                    constraint:'TimeWithDayAttr',
                                                    value: appWorkChange.startTime2,
                                                    readonly: false,
                                                    required: false,
                                                    name: $i18n('KAF007_64'),
                                                    enable: (isEdit() && model() !== null && model().setupType() !== null && model().setupType() === 0 && model().reflectWorkChangeAppDto().whetherReflectAttendance === 1) }" />
                </div>
                <!-- A8_2 -->
                <div class="cell" style="padding-right: 5px;" data-bind="text: $i18n('KAF007_14')"></div>
                <div class="cell valign-center">
                    <!-- A8_3 -->
                    <input id="time2End" class="inputTime" data-bind="ntsTimeWithDayEditor: { 
                                                    constraint:'TimeWithDayAttr',
                                                    value: appWorkChange.endTime2,
                                                    readonly: false,
                                                    required: false,
                                                    name: $i18n('KAF007_65'),
                                                    enable: (isEdit() && model() !== null && model().setupType() !== null && model().setupType() === 0 && model().reflectWorkChangeAppDto().whetherReflectAttendance === 1) }" />
                    <!-- A8_6	 -->
                    <span class="label comment2" data-bind="text: $vm.comment2, visible: reflectWorkChange.whetherReflectAttendance() == 1"></span>
        
                </div>
            </div>
            <div class="table" style="margin-top: 5px;">
                <div class="cell col-1"></div>
                <!-- A8_4 -->
                <div class="cell valign-center col-3" style="display: block;" data-bind="ntsCheckBox: {
                    text: $i18n('KAF007_16'),
                    checked: isStraightGo, 
                    enable: (model() !== null && model().setupType() === 0 && isEdit)
                }"></div>
                <div class="cell" style="padding-right: 5px;"></div>
                <!-- A8_5 -->
                <div class="cell valign-center" data-bind="ntsCheckBox: {
                    text: $i18n('KAF007_18'),
                    checked: isStraightBack, 
                    enable: (model() !== null && model().setupType() === 0 && isEdit)
                }"></div>
            </div>
        </div>`
    })
    class Kaf007ShareViewModel extends ko.ViewModel {
        model: KnockoutObservable<ModelDto>;
        mode: number;
        appWorkChange: AppWorkChange;
        reflectWorkChange: ReflectWorkChangeApp;
        isStraightGo: KnockoutObservable<boolean>;
        isStraightBack: KnockoutObservable<boolean>;
        isEdit: KnockoutObservable<boolean> = ko.observable(true);
        created(params: any) {
            const vm = this;

            vm.model = params.model;
            vm.mode = params.mode;
            vm.appWorkChange = params.appWorkChange;
            vm.reflectWorkChange = params.reflectWorkChange;
            vm.isStraightBack = params.isStraightBack;
            vm.isStraightGo = params.isStraightGo;

            if(params.isEdit) {
                vm.isEdit = params.isEdit;
            }
        }

        mounted() {
            const vm = this;
            
        }

        openKDL003Click() {
            const vm = this;

            vm.$window.storage('parentCodes', {
                workTypeCodes: _.map(_.uniqBy(vm.model().workTypeLst, e => e.workTypeCode), item => item.workTypeCode),
                selectedWorkTypeCode: vm.appWorkChange.workTypeCode(),
                workTimeCodes: _.map(vm.model().appDispInfoStartupOutput().appDispInfoWithDateOutput.opWorkTimeLst, item => item.worktimeCode),
                selectedWorkTimeCode: vm.appWorkChange.workTimeCode()
            });

            vm.$window.modal('/view/kdl/003/a/index.xhtml').then((result: any) => {
                vm.$window.storage('childData').then(rs => {
                    if (rs) {
                        console.log(rs);
                        vm.appWorkChange.workTypeCode(rs.selectedWorkTypeCode);
                        vm.model().workTypeCD(rs.selectedWorkTypeCode);
                        vm.appWorkChange.workTypeName(rs.selectedWorkTypeName);
                        vm.appWorkChange.workTimeCode(rs.selectedWorkTimeCode);
                        vm.model().workTimeCD(rs.selectedWorkTimeCode);
                        vm.appWorkChange.workTimeName(rs.selectedWorkTimeName);

                        if(vm.model().appWorkChangeSet.initDisplayWorktimeAtr !== 1) {
                            if(rs.first) {
                                vm.appWorkChange.startTime1(rs.first.start);
                                vm.appWorkChange.endTime1(rs.first.end)
                            } else {
                                vm.appWorkChange.startTime1(null);
                                vm.appWorkChange.endTime1(null)
                            }
                            if(rs.second) {
                                vm.appWorkChange.startTime2(rs.second.start);
                                vm.appWorkChange.endTime2(rs.second.end)
                            } else {
                                vm.appWorkChange.startTime2(null);
                                vm.appWorkChange.endTime2(null)
                            }
                        } else {
                            vm.appWorkChange.startTime1(null);
                            vm.appWorkChange.endTime1(null);
                            vm.appWorkChange.startTime2(null);
                            vm.appWorkChange.endTime2(null);
                        }
                    }
                }).then(() => {
                    return vm.$ajax(API.getByWorkType + "/" + vm.model().workTypeCD())
                }).done((res) => {
                    if(res !== null || res !== undefined) {
                        vm.$errors("clear");
                        vm.model().setupType(res);
                    }
                }).fail(fail => console.log(fail))
                .always(() => {
                    if(vm.isEdit() && vm.model().setupType() === 0 && ko.toJS(vm.model().reflectWorkChangeAppDto().whetherReflectAttendance) === 1) {
                        $('#time1Start').focus();
                    }
                })
                
            });
        }
    }

    const API = {
        getByWorkType: "at/schedule/basicschedule/isWorkTimeSettingNeeded"
    }

    export class AppWorkChange {
        workTypeCode: KnockoutObservable<string>;
        workTypeName: KnockoutObservable<String>;
        workTimeCode: KnockoutObservable<string>;
        workTimeName: KnockoutObservable<String>;
        startTime1: KnockoutObservable<number>;
        endTime1: KnockoutObservable<number>;
        startTime2: KnockoutObservable<number>;
        endTime2: KnockoutObservable<number>;
        constructor(workTypeCode: string, workTypeName: string, workTimeCode: string, workTimeName: string, startTime1: number, endTime1: number, startTime2: number, endTime2: number) {
            this.workTypeCode = ko.observable(workTypeCode);
            this.workTypeName = ko.observable(workTypeName);
            this.workTimeCode = ko.observable(workTimeCode);
            this.workTimeName = ko.observable(workTimeName);
            this.startTime1 = ko.observable(startTime1);
            this.endTime1 = ko.observable(endTime1);
            this.startTime2 = ko.observable(startTime2);
            this.endTime2 = ko.observable(endTime2);
        }
    }

    export class ReflectWorkChangeApp {
        companyId: string;
        whetherReflectAttendance: KnockoutObservable<number>;

        constructor(companyID: string, whetherReflectAttendance: number) {
            this.companyId = companyID;
            this.whetherReflectAttendance = ko.observable(whetherReflectAttendance);
        }
    }

    export class ModelDto {

        workTypeCD: KnockoutObservable<string>;

        workTimeCD: KnockoutObservable<string>;

        appDispInfoStartupOutput: KnockoutObservable<any>;

        reflectWorkChangeAppDto: KnockoutObservable<ReflectWorkChangeApp> = ko.observable(null);

        workTypeLst: any;

        setupType: KnockoutObservable<number>;

        predetemineTimeSetting: KnockoutObservable<any>;

        appWorkChangeSet: any;
    }
    
}