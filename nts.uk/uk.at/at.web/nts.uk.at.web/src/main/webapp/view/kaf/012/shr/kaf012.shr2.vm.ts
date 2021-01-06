module nts.uk.at.view.kaf012.shr.viewmodel2 {
    import getText = nts.uk.resource.getText;
    import LeaveType = nts.uk.at.view.kaf012.shr.viewmodel1.LeaveType;
    import ReflectSetting = nts.uk.at.view.kaf012.shr.viewmodel1.ReflectSetting;
    import TimeLeaveManagement = nts.uk.at.view.kaf012.shr.viewmodel1.TimeLeaveManagement;
    import TimeLeaveRemaining = nts.uk.at.view.kaf012.shr.viewmodel1.TimeLeaveRemaining;

    const API = {
        changeSpecialFrame: "at/request/application/timeLeave/changeSpecialFrame"
    };

    const template = `
    <div id="kaf012-share-component2">
        <div class="control-group valign-center">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_46')}"></div>
            <div data-bind="ntsSwitchButton: {
						name: $i18n('KAF012_5'),
						options: switchOptions,
						optionsValue: 'code',
						optionsText: 'name',
						value: leaveType,
						enable: true,
						required: true }">
			</div>
        </div>
        <div class="control-group valign-center" data-bind="if: displaySpecialLeaveFrames">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_47')}"/>
            <div data-bind="ntsComboBox: {
                    name: $i18n('KAF012_47'),
                    options: specialLeaveFrames,
                    optionsValue: 'specialHdFrameNo',
                    optionsText: 'specialHdFrameName',
                    value: specialLeaveFrame,
                    columns: [{ prop: 'specialHdFrameName', length: 20 }],
                    required: true }">  
            </div>
        </div>
        <div class="control-group valign-top">
            <div data-bind="ntsFormLabel: {required:true , text: $i18n('KAF012_6')}"></div>
            <div style="display: inline-block;">
                <table id="kaf012-input-table" class="pull-left">
                    <thead>
                        <tr data-bind="if: leaveType() == 6">
                            <th style="border: 0; padding-bottom: 6px" colspan="2"/>
                        </tr>
                        <tr class="bg-green">
                            <th>
                            <th data-bind="text: $i18n('KAF012_7')"/>
                        </tr>
                    </thead>
                    <tbody data-bind="foreach: applyTimeData">
                        <tr data-bind="if: display">
                            <td class="bg-green" data-bind="text: appTimeTypeName"/>
                            <td>
                                <div class="control-group valign-center">
                                    <span data-bind="text: scheduledTimeLabel"/>
                                    <span data-bind="text: scheduledTime" style="font-weight: bold;"/>
                                </div>
                                <ul data-bind="foreach: timeZone">
                                    <li data-bind="if: display">
                                        <div  data-bind="if: $parent.appTimeType < 4" class="control-group valign-center">
                                            <input class="time-input"
                                                    data-bind="ntsTimeEditor: {
                                                                    name: $parent.appTimeType < 4 ? $parent.appTimeTypeName : $vm.$i18n('KAF012_29'), 
                                                                    constraint: 'AttendanceClock', 
                                                                    value: startTime, 
                                                                    inputFormat: 'time', 
                                                                    mode: 'time'
                                                                }"/>
                                            <span data-bind="text: $parent.attendLeaveLabel"/>
                                            <span data-bind="text: $parent.lateTimeLabel"/>
                                            <span data-bind="text: $parent.lateTime" style="font-weight: bold;"/>
                                        </div>
                                        <div  data-bind="ifnot: $parent.appTimeType < 4" class="control-group valign-center">
                                            <input class="time-input"
                                                    data-bind="ntsTimeEditor: {
                                                                    name: $parent.appTimeType < 4 ? $parent.appTimeTypeName : $vm.$i18n('KAF012_29'), 
                                                                    constraint: 'AttendanceClock', 
                                                                    value: startTime, 
                                                                    inputFormat: 'time', 
                                                                    mode: 'time'
                                                                }"/>
                                            <span class="label" data-bind="text: $vm.$i18n('KAF012_30')"/>
                                            <input class="time-input"
                                                    data-bind="ntsTimeEditor: {
                                                                    name: $parent.appTimeType < 4 ? $parent.appTimeTypeName : $vm.$i18n('KAF012_31'), 
                                                                    constraint: 'AttendanceClock', 
                                                                    value: endTime, 
                                                                    inputFormat: 'time', 
                                                                    mode: 'time'
                                                                }"/>
                                            <input style="width: 80px" data-bind="ntsComboBox: {
                                                                        name: $vm.$i18n('KAF012_32'),
                                                                        options: ko.observableArray([
                                                                            {value: 4, name: $vm.$i18n('KAF012_33')},
                                                                            {value: 5, name: $vm.$i18n('KAF012_34')}
                                                                        ]),
                                                                        optionsValue: 'value',
                                                                        optionsText: 'name',
                                                                        value: appTimeType,
                                                                        columns: [{ prop: 'name', length: 4 }],
                                                                        required: false 
                                                                    },
                                                                    css: { hidden: !displayCombobox() }">  
                                        </div>
                                    </li>
                                </ul>
                                <a class="hyperlink" href="" data-bind="text: $vm.$i18n('KAF012_37'), click: showMore, css: { hidden: !displayShowMore() }"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div style="display: inline-block; width: 100px; text-align: center;" class="pull-left">
                    <button class="proceed caret-right" style="height: 300px;" data-bind="text: $i18n('KAF012_38')"/>
                </div>
                <table id="kaf012-calc-table">
                    <thead>
                        <tr data-bind="if: leaveType() == 6">
                            <th style="border: 0;"/>
                            <th colspan="6" class="bg-green" data-bind="text: $i18n('KAF012_8')"/>
                        </tr>
                        <tr>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 6}">
                                <span data-bind="text: $i18n('KAF012_40')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 0 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('KAF012_3') : $i18n('KAF012_8')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 1 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('KAF012_4') : $i18n('KAF012_8')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 2 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('Com_ChildNurseHoliday') : $i18n('KAF012_8')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 3 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('Com_CareHoliday') : $i18n('KAF012_8')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 4 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('Com_ExsessHoliday') : $i18n('KAF012_8')"/>
                            </th>
                            <th class="bg-green" data-bind="css: {hidden: leaveType() != 5 &amp;&amp; leaveType() != 6}">
                                <span data-bind="text: leaveType() == 6 ? $i18n('KAF012_46') : $i18n('KAF012_8')"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody data-bind="foreach: applyTimeData">
                        <tr data-bind="if: display(), attr: {height: !display() ? '0' : appTimeType < 4 ? '85px' : displayShowMore() ? '192px' : '464px'}">
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 6}, foreach: applyTime">
                                <div>
                                    <span data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                    css: {hidden: appTimeType < 4}"/>
                                    <span>0:00</span>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 0 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                        css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: substituteAppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 1 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                            css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: annualAppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 2 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                        css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: childCareAppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 3 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                        css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: careAppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 4 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                        css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: super60AppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                            <td data-bind="style: {'vertical-align': appTimeType < 4 ? 'middle' : 'initial'}, css: {hidden: $parent.leaveType() != 5 &amp;&amp; $parent.leaveType() != 6}, foreach: applyTime">
                                <div data-bind="if: display, attr: {class: appTimeType < 4 ? '' : 'control-group'}">
                                    <span style="display: flex" data-bind="text: appTimeType == 4 ? $vm.$i18n('KAF012_33') : $vm.$i18n('KAF012_34'), 
                                                        css: {hidden: appTimeType < 4}"/>
                                    <input class="time-input"
                                            data-bind="ntsTimeEditor: {
                                                            name: inputName, 
                                                            constraint: 'AttendanceClock', 
                                                            value: specialAppTime, 
                                                            inputFormat: 'time', 
                                                            mode: 'time'
                                                        }"/>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    `;

    @component({
        name: 'kaf012-share-component2',
        template: template
    })
    class Kaf012Share2ViewModel extends ko.ViewModel {
        reflectSetting: KnockoutObservable<ReflectSetting>;
        timeLeaveManagement: KnockoutObservable<TimeLeaveManagement>;
        timeLeaveRemaining: KnockoutObservable<TimeLeaveRemaining>;
        appDispInfoStartupOutput: KnockoutObservable<any>;
        application: KnockoutObservable<any>;

        switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
        leaveType: KnockoutObservable<number>;
        specialLeaveFrame: KnockoutObservable<number>;
        specialLeaveFrames: KnockoutObservableArray<any> = ko.observableArray([]);
        displaySpecialLeaveFrames: KnockoutObservable<boolean>;

        applyTimeData: KnockoutObservableArray<DataModel>;

        created(params: Params) {
            const vm = this;
            vm.reflectSetting = params.reflectSetting;
            vm.timeLeaveManagement = params.timeLeaveManagement;
            vm.timeLeaveRemaining = params.timeLeaveRemaining;
            vm.appDispInfoStartupOutput = params.appDispInfoStartupOutput;
            vm.application = params.application;
            vm.leaveType = params.leaveType;
            vm.specialLeaveFrame = params.specialLeaveFrame;
            vm.displaySpecialLeaveFrames = ko.computed(() => {
                return vm.leaveType() == LeaveType.SPECIAL
                    || (
                        vm.leaveType() == LeaveType.COMBINATION
                        && !!vm.timeLeaveManagement()
                        && vm.timeLeaveManagement().timeSpecialLeaveMng.timeSpecialLeaveMngAtr
                        && !!vm.reflectSetting()
                        && vm.reflectSetting().condition.specialVacationTime == 1
                    );
            });
            vm.applyTimeData = params.applyTimeData;
        }

        mounted() {
            const vm = this;
            vm.leaveType.subscribe(value => {
                vm.$errors("clear");
            });
            vm.timeLeaveManagement.subscribe(value => {
                if (value) {
                    if (value.timeSpecialLeaveMng) {
                        vm.specialLeaveFrames(value.timeSpecialLeaveMng.listSpecialFrame || []);
                    }
                    const switchOptions = [
                        {
                            code: 0,
                            name: vm.$i18n('KAF012_3'),
                            display: value.timeSubstituteLeaveMng.timeSubstituteLeaveMngAtr
                                    && !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.substituteLeaveTime == 1
                        },
                        {
                            code: 1,
                            name: vm.$i18n('KAF012_4'),
                            display: value.timeAnnualLeaveMng.timeAnnualLeaveMngAtr
                                    && !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.annualVacationTime == 1
                        },
                        {
                            code: 2,
                            name: vm.$i18n('Com_ChildNurseHoliday'),
                            display: !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.childNursing == 1
                        },
                        {
                            code: 3,
                            name: vm.$i18n('Com_CareHoliday'),
                            display: !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.nursing == 1
                        },
                        {
                            code: 4,
                            name: vm.$i18n('Com_ExsessHoliday'),
                            display: value.super60HLeaveMng.super60HLeaveMngAtr
                                    && !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.superHoliday60H == 1
                        },
                        {
                            code: 5,
                            name: vm.$i18n('KAF012_46'),
                            display: value.timeSpecialLeaveMng.timeSpecialLeaveMngAtr
                                    && !!vm.reflectSetting()
                                    && vm.reflectSetting().condition.specialVacationTime == 1
                        }
                    ];
                    const result = switchOptions.filter(i => i.display);
                    if (result.length > 1) {
                        result.push({
                            code: 6,
                            name: vm.$i18n('KAF012_39'),
                            display: true
                        });
                        vm.switchOptions(result);
                    } else {
                        vm.switchOptions(result);
                    }
                    if (!vm.leaveType()) vm.leaveType(vm.switchOptions()[0].code);
                }
            });
            vm.specialLeaveFrame.subscribe(value => {
                vm.handleChangeSpecialLeaveFrame(value);
            });
        }

        handleChangeSpecialLeaveFrame(value: any) {
            const vm = this;
            const params = {
                specialLeaveFrameNo: vm.specialLeaveFrame(),
                timeLeaveAppDisplayInfo: {
                    appDispInfoStartupOutput: vm.appDispInfoStartupOutput(),
                    timeLeaveManagement: vm.timeLeaveManagement(),
                    timeLeaveRemaining: vm.timeLeaveRemaining(),
                    reflectSetting: vm.reflectSetting()
                }
            };
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingStart).toISOString();
            params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd = new Date(params.timeLeaveAppDisplayInfo.timeLeaveRemaining.remainingEnd).toISOString();
            return vm.$blockui("show")
                .then(() => vm.$ajax(API.changeSpecialFrame, params))
                .done((res: any) => {
                    if (res) {
                        vm.timeLeaveRemaining(res.timeLeaveRemaining);
                    }
                }).fail((error: any) => {
                    vm.$dialog.error(error);
                }).always(() => vm.$blockui("hide"));
        }
    }
    
    interface Params {
        reflectSetting: KnockoutObservable<ReflectSetting>,
        timeLeaveManagement: KnockoutObservable<TimeLeaveManagement>,
        timeLeaveRemaining: KnockoutObservable<TimeLeaveRemaining>,
        leaveType: KnockoutObservable<number>,
        appDispInfoStartupOutput: KnockoutObservable<any>,
        application: KnockoutObservable<any>,
        applyTimeData: KnockoutObservableArray<DataModel>,
        specialLeaveFrame: KnockoutObservable<number>
    }

    export enum AppTimeType {
        ATWORK = 0, /**	出勤前 */
        OFFWORK = 1, /**	退勤後 */
        ATWORK2 = 2, /**	出勤前2 */
        OFFWORK2 = 3, /**	退勤後2 */
        PRIVATE = 4, /**	私用外出 */
        UNION = 5 /**	組合外出 */
    }
    
    class TimeZone {
        appTimeType: KnockoutObservable<number>;
        workNo: number;
        startTime: KnockoutObservable<number>;
        endTime: KnockoutObservable<number>;
        display: KnockoutObservable<boolean>;
        displayCombobox: KnockoutObservable<boolean>;
        constructor(appTimeType: number, workNo: number, reflectSetting?: KnockoutObservable<ReflectSetting>) {
            this.appTimeType = ko.observable(appTimeType);
            this.workNo = workNo;
            this.startTime = ko.observable(null);
            this.endTime = ko.observable(null);
            this.display = ko.observable(workNo < 4);
            this.displayCombobox = ko.computed(() => {
                return !!reflectSetting
                    && !!reflectSetting()
                    && reflectSetting().destination.privateGoingOut == 1
                    && reflectSetting().destination.unionGoingOut == 1;
            });
        }
    }

    class ApplyTime {
        appTimeType: number;
        inputName: string;
        display: KnockoutObservable<boolean>;
        substituteAppTime: KnockoutObservable<number>;
        annualAppTime: KnockoutObservable<number>;
        childCareAppTime: KnockoutObservable<number>;
        careAppTime: KnockoutObservable<number>;
        super60AppTime: KnockoutObservable<number>;
        specialAppTime: KnockoutObservable<number>;
        specialLeaveFrameNo: KnockoutObservable<number>;
        constructor(appTimeType: number, reflectSetting?: KnockoutObservable<ReflectSetting>) {
            this.appTimeType = appTimeType;
            this.substituteAppTime = ko.observable(0);
            this.annualAppTime = ko.observable(0);
            this.childCareAppTime = ko.observable(0);
            this.careAppTime = ko.observable(0);
            this.super60AppTime = ko.observable(0);
            this.specialAppTime = ko.observable(0);
            this.specialLeaveFrameNo = ko.observable(null);
            switch (this.appTimeType) {
                case AppTimeType.ATWORK:
                    this.inputName = getText("KAF012_14");
                    break;
                case AppTimeType.OFFWORK:
                    this.inputName = getText("KAF012_20");
                    break;
                case AppTimeType.ATWORK2:
                    this.inputName = getText("KAF012_23");
                    break;
                case AppTimeType.OFFWORK2:
                    this.inputName = getText("KAF012_26");
                    break;
                case AppTimeType.PRIVATE:
                    this.inputName = getText("KAF012_35");
                    break;
                case AppTimeType.UNION:
                    this.inputName = getText("KAF012_36");
                    break;
                default:
                    this.inputName = "";
                    break;
            }
            this.display = ko.computed(() => {
                if (appTimeType >= 4) {
                    if (appTimeType == AppTimeType.PRIVATE && reflectSetting && reflectSetting() && reflectSetting().destination.privateGoingOut == 1)
                        return true;
                    else if (appTimeType == AppTimeType.UNION && reflectSetting && reflectSetting() && reflectSetting().destination.unionGoingOut == 1)
                        return true;
                    else return false;
                }
                return true;
            });
        }
    }

    export class DataModel {
        appTimeType: number;
        appTimeTypeName: string;
        scheduledTimeLabel: string;
        scheduledTime: KnockoutObservable<string>;
        attendLeaveLabel: KnockoutObservable<string>;
        lateTimeLabel: KnockoutObservable<string>;
        lateTime: KnockoutObservable<string>;
        timeZone: Array<TimeZone>;
        applyTime: Array<ApplyTime>;
        display: KnockoutObservable<boolean>;
        displayShowMore: KnockoutObservable<boolean>;

        constructor(appTimeType: number, reflectSetting: KnockoutObservable<ReflectSetting>, appDispInfoStartupOutput: KnockoutObservable<any>, application: KnockoutObservable<any>) {
            this.appTimeType = appTimeType;
            switch (appTimeType) {
                case AppTimeType.ATWORK:
                    this.appTimeTypeName = getText('KAF012_9');
                    this.scheduledTimeLabel = getText("KAF012_10");
                    this.timeZone = [new TimeZone(AppTimeType.ATWORK, 1)];
                    this.applyTime = [new ApplyTime(AppTimeType.ATWORK)];
                    this.display = ko.computed(() => {
                        return reflectSetting() && reflectSetting().destination.firstBeforeWork == 1;
                    });
                    this.displayShowMore = ko.observable(false);
                    break;
                case AppTimeType.OFFWORK:
                    this.appTimeTypeName = getText('KAF012_15');
                    this.scheduledTimeLabel = getText("KAF012_16");
                    this.timeZone = [new TimeZone(AppTimeType.OFFWORK, 1)];
                    this.applyTime = [new ApplyTime(AppTimeType.OFFWORK)];
                    this.display = ko.computed(() => {
                        return reflectSetting() && reflectSetting().destination.firstAfterWork == 1;
                    });
                    this.displayShowMore = ko.observable(false);
                    break;
                case AppTimeType.ATWORK2:
                    this.appTimeTypeName = getText('KAF012_21');
                    this.scheduledTimeLabel = getText("KAF012_10");
                    this.timeZone = [new TimeZone(AppTimeType.ATWORK2, 1)];
                    this.applyTime = [new ApplyTime(AppTimeType.ATWORK2)];
                    this.display = ko.computed(() => {
                        return reflectSetting() && reflectSetting().destination.secondBeforeWork == 1;
                    });
                    this.displayShowMore = ko.observable(false);
                    break;
                case AppTimeType.OFFWORK2:
                    this.appTimeTypeName = getText('KAF012_24');
                    this.scheduledTimeLabel = getText("KAF012_16");
                    this.timeZone = [new TimeZone(AppTimeType.OFFWORK2, 1)];
                    this.applyTime = [new ApplyTime(AppTimeType.OFFWORK2)];
                    this.display = ko.computed(() => {
                        return reflectSetting() && reflectSetting().destination.secondAfterWork == 1;
                    });
                    this.displayShowMore = ko.observable(false);
                    break;
                case 4: // PRIVATE and UNION
                    this.appTimeTypeName = getText('KAF012_27');
                    this.scheduledTimeLabel = getText('KAF012_28');
                    this.timeZone = [];
                    for (let i = 1; i <= 10; i++) {
                        this.timeZone.push(new TimeZone(AppTimeType.PRIVATE, i, reflectSetting));
                    }
                    this.applyTime = [
                        new ApplyTime(AppTimeType.PRIVATE, reflectSetting),
                        new ApplyTime(AppTimeType.UNION, reflectSetting)
                    ];
                    this.display = ko.computed(() => {
                        return reflectSetting() && reflectSetting().destination.privateGoingOut == 1
                            || reflectSetting() && reflectSetting().destination.unionGoingOut == 1;
                    });
                    this.displayShowMore = ko.observable(true);
                    break;
                default:
                    break;
            }
            this.scheduledTime = ko.computed(() => {
                if (appDispInfoStartupOutput
                    && appDispInfoStartupOutput()
                    && !_.isEmpty(appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst)
                    && appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail) {
                    let value = null;
                    switch (appTimeType) {
                        case AppTimeType.ATWORK:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime1;
                            break;
                        case AppTimeType.OFFWORK:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime1;
                            break;
                        case AppTimeType.ATWORK2:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheAttendanceTime2;
                            break;
                        case AppTimeType.OFFWORK2:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.achievementEarly.scheDepartureTime2;
                            break;
                        default:
                            break;
                    }
                    return value == null ? "" : nts.uk.time.format.byId("Clock_Short_HM", value);
                }
                return "";
            });
            this.attendLeaveLabel = ko.computed(() => {
                switch (appTimeType) {
                    case AppTimeType.ATWORK:
                    case AppTimeType.ATWORK2:
                        if (application && application().prePostAtr() == 0)
                            return getText("KAF012_11");
                        if (application && application().prePostAtr() == 1)
                            return getText("KAF012_12");
                        return "";
                    case AppTimeType.OFFWORK:
                    case AppTimeType.OFFWORK2:
                        if (application && application().prePostAtr() == 0)
                            return getText("KAF012_17");
                        if (application && application().prePostAtr() == 1)
                            return getText("KAF012_18");
                        return "";
                    default:
                        return "";
                }
            });
            this.lateTimeLabel = ko.computed(() => {
                if (appDispInfoStartupOutput
                    && appDispInfoStartupOutput()
                    && !_.isEmpty(appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst)
                    && appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail) {
                    switch (appTimeType) {
                        case AppTimeType.ATWORK:
                            return appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.lateTime > 0 && application().prePostAtr() == 1 ? getText("KAF012_13") : "";
                        case AppTimeType.OFFWORK:
                            return appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.earlyLeaveTime > 0 && application().prePostAtr() == 1 ? getText("KAF012_19") : "";
                        case AppTimeType.ATWORK2:
                            return appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.lateTime2 > 0 && application().prePostAtr() == 1 ? getText("KAF012_22") : "";
                        case AppTimeType.OFFWORK2:
                            return appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.earlyLeaveTime2 > 0 && application().prePostAtr() == 1 ? getText("KAF012_25") : "";
                        default:
                            break;
                    }
                }
                return "";
            });
            this.lateTime = ko.computed(() => {
                if (appDispInfoStartupOutput
                    && appDispInfoStartupOutput()
                    && !_.isEmpty(appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst)
                    && appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail) {
                    let value = null;
                    switch (appTimeType) {
                        case AppTimeType.ATWORK:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.lateTime;
                            break;
                        case AppTimeType.OFFWORK:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.earlyLeaveTime;
                            break;
                        case AppTimeType.ATWORK2:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.lateTime2;
                            break;
                        case AppTimeType.OFFWORK2:
                            value = appDispInfoStartupOutput().appDispInfoWithDateOutput.opActualContentDisplayLst[0].opAchievementDetail.timeContentOutput.earlyLeaveTime2;
                            break;
                        default:
                            break;
                    }
                    return value == null ? "" : nts.uk.time.format.byId("Clock_Short_HM", value);
                }
                return "";
            });
        }

        showMore() {
            const vm = this;
            vm.timeZone.forEach(i => {
                i.display(true);
            });
            vm.displayShowMore(false);
        }
    }
}