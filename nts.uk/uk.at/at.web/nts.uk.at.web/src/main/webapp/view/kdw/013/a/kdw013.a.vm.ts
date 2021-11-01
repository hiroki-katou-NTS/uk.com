module nts.uk.ui.at.kdw013.a {
    /**
     * 日別勤怠の編集状態
     */
    enum EditStateSetting {
        /** 手修正（本人） */
        HAND_CORRECTION_MYSELF = 0,
        /** 手修正（他人） */
        HAND_CORRECTION_OTHER = 1,
        /** 申請反映 */
        REFLECT_APPLICATION = 2,
        /** 打刻反映 */
        IMPRINT = 3,
        /** 申告反映 */
        DECLARE_APPLICATION = 4

    };

    const { formatTime, setTimeOfDate, getTimeOfDate, getTask, getBackground, getTitles } = share;
    const { randomId } = nts.uk.util;

    const DATE_FORMAT = 'YYYY-MM-DD';
    const DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm:00.000\\Z';

    const API: API = {
        ADD: '/screen/at/kdw013/a/add_confirm',
        // DeleteWorkResultConfirmationCommand
        DELETE: '/screen/at/kdw013/a/delete_confirm',
        // start page query params
        START: '/screen/at/kdw013/a/start',
        // 対象社員を選択する
        // 日付を変更する
        // Chọn nhân viên ở A2_4
        // Chọn ngày ở [画面イメージ]A6_1/[固有部品]A1_1
        CHANGE_DATE: '/screen/at/kdw013/a/changeDate',
        // RegisterWorkContentCommand
        REGISTER: '/screen/at/kdw013/a/register_work_content',

        // POPUP F
        // 作業お気に入り登録を起動する
        START_F: '/screen/at/kdw013/f/start_task_fav_register',
        // 作業お気に入りを新規追加する
        ADD_FAV_TASK_F: '/screen/at/kdw013/f/create_task_fav',
        // 作業お気に入り名称を変更する
        UPDATE_TASK_NAME_F: '/screen/at/kdw013/f/update_task_name',
        // POPUP G
        // 1日作業お気に入り登録を起動する
        START_G: '/screen/at/kdw013/g/start_task_fav_register',
        // 1日作業お気に入りを新規追加する
        ADD_FAV_TASK_G: '/screen/at/kdw013/g/create_task_fav',
        // 1日作業お気に入り名称を変更する
        UPDATE_TASK_NAME_G: '/screen/at/kdw013/g/update_task_name'
    };

    const initialCache = (): ChangeDateParam => ({
        displayPeriod: {
            end: '',
            start: ''
        },
        employeeId: '',
        refDate: ''
    })

    @handler({
        bindingName: 'kdw-toggle',
        validatable: true,
        virtual: false
    })
    export class KDWToggleBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLButtonElement, valueAccessor: () => KnockoutComputed<boolean>) {
            const toggler = valueAccessor();
            const visible = ko.computed({
                read: () => {
                    const visible = ko.unwrap(toggler);

                    return visible;
                },
                disposeWhenNodeIsRemoved: element
            });
            const disable = ko.computed({
                read: () => {
                    const enable = ko.unwrap(toggler);

                    return !enable;
                },
                disposeWhenNodeIsRemoved: element
            });

            ko.applyBindingsToNode(element, { visible, disable });

            element.removeAttribute('data-bind');
        }
    }

    @bean()
    export class ViewModel extends ko.ViewModel {
        $toggle!: {
            save: KnockoutComputed<boolean>;
            remove: KnockoutComputed<boolean>;
            confirm: KnockoutComputed<boolean>;
        };

        events: KnockoutObservableArray<calendar.EventApi> = ko.observableArray([]);

        breakTime= ko.observableArray([]);

        businessHours= ko.observableArray([]);

        weekends: KnockoutObservable<boolean> = ko.observable(true);
        editable: KnockoutObservable<boolean> = ko.observable(true);
        firstDay: KnockoutObservable<number> = ko.observable(1);
        scrollTime: KnockoutObservable<number> = ko.observable(420);
        slotDuration: KnockoutObservable<number> = ko.observable(30);
        initialDate: KnockoutObservable<Date> = ko.observable(new Date());
        isShowBreakTime: KnockoutObservable<boolean> = ko.observable(false);
        dateRange: KnockoutObservable<Partial<calendar.DatesSet>> = ko.observable({});
        initialView: KnockoutObservable<string> = ko.observable('oneDay');
        availableView: KnockoutObservableArray<calendar.InitialView> = ko.observableArray(['oneDay', 'fullWeek']);
        validRange: KnockoutObservable<Partial<calendar.DatesSet>> = ko.observable({end: '9999-12-32'});

        employee: KnockoutObservable<string> = ko.observable('');

        confirmers!: KnockoutComputed<calendar.Employee[]>;
        // need map with [KDW013_21, KDW013_22, KDW013_23, KDW013_24] resource
        attendanceTimes!: KnockoutComputed<calendar.AttendanceTime[]>;

        // Change date range data model
        $datas: KnockoutObservable<ChangeDateDto | null> = ko.observable(null);

        // settings (first load data)
        $settings: KnockoutObservable<StartProcessDto | null> = ko.observable(null);
    
        dataChanged: KnockoutObservable<boolean> = ko.observable(false);
        favTaskName: KnockoutObservable<string> = ko.observable('');
        oneDayFavTaskName: KnockoutObservable<string> = ko.observable('');

        // F画面を起動する
        favoriteTaskItem: KnockoutObservable<FavoriteTaskItemDto | null> = ko.observable(null);

        // F画面: add new 
        taskContents: KnockoutObservableArray<TaskContentDto> = ko.observableArray();

        // G画面を起動する
        oneDayFavoriteSet: KnockoutObservable<OneDayFavoriteSetDto | null> = ko.observable(null);

        // G画面: add new 
        taskBlocks: KnockoutObservableArray<TaskBlockDetailContentDto> = ko.observableArray();

        // 作業枠利用設定
        taskSettings: KnockoutObservableArray<a.TaskFrameSettingDto> = ko.observableArray();

        // I画面
        taskInfos: KnockoutObservableArray<TaskInfo> = ko.observableArray();
        
        // 作業リスト
        taskDtos: KnockoutObservableArray<TaskDto> =  ko.observableArray();

        // 日別勤怠の応援作業時間
        ouenWorkTimes: KnockoutObservableArray<OuenWorkTimeOfDailyAttendance> =  ko.observableArray();

        // 日別勤怠の応援作業時間帯
        ouenWorkTimeSheets: KnockoutObservableArray<OuenWorkTimeSheetOfDailyAttendance> =  ko.observableArray();

        //対象日
        targetDate: KnockoutObservable<Date> =  ko.observable();

        constructor() {
            super();
            const vm = this;
            vm.createWarning();
            let $query = vm.getQuery();
            const { employee } = vm;
            const { mode } = $query;
            const cache: ChangeDateParam & { pair: -1 | 0 | 1 | 2 } = { ...initialCache(), pair: 0 };
            const sameCache = (params: ChangeDateParam): -1 | 0 | 1 | 2 => {
                if (cache.refDate !== params.refDate) {
                    if (cache.displayPeriod.end === params.displayPeriod.end) {
                        if (cache.displayPeriod.start === params.displayPeriod.start) {
                            return -1;
                        }
                    }
                }

                if (cache.employeeId !== params.employeeId) {
                    if (params.displayPeriod.start && params.displayPeriod.end) {
                        return 0;
                    }
                }

                if (cache.displayPeriod.end === params.displayPeriod.end) {
                    if (cache.displayPeriod.start === params.displayPeriod.start) {
                        if (cache.refDate === params.refDate) {
                            return 2;
                        }

                        return 1;
                    }
                }

                return 0;
            };
            const updateCache = (params: ChangeDateParam) => {
                cache.displayPeriod.end = params.displayPeriod.end;
                cache.displayPeriod.start = params.displayPeriod.start;

                cache.refDate = params.refDate;
            };
            const computedEvents = (data: SelectTargetEmployeeDto | null, settings: StartProcessDto | null) => {
                if (cache.pair === -1) {
                    return;
                }
                const { tasks } = settings;
                if (data) {
                    let events = [];
                    _.forEach(_.get(data, 'lstIntegrationOfDaily'), ld => {
                        
                        
                        let frameNos =[];
                        
                        let hrTask = _.find(_.get(data, 'dailyManHrTasks', []), dt => moment(dt.ymd).isSame(moment(ld.ymd)));
                        
                        
                        let {manHrContents} = _.find(_.get(data, 'convertRes'), cr => moment(cr.ymd).isSame(moment(ld.ymd), 'days'));
                        
                        _.forEach(_.get(ld, 'breakTime.breakTimeSheets',[]), bt => {
                            frameNos.push(bt.no);
                            events.push(
                                {
                                    start: setTimeOfDate(moment(ld.ymd).toDate(), bt.start),
                                    end: setTimeOfDate(moment(ld.ymd).toDate(), bt.end),
                                    title: vm.$i18n('KDW013_79'),
                                    backgroundColor: '#fbb3fb',
                                    textColor: '',
                                    extendedProps: {
                                        no: bt.no,
                                        breakTime: bt.breakTime,
                                        id: randomId(),
                                        status: 'normal' as any,
                                        isTimeBreak: true,
                                        isChanged: false,
                                        taskBlock: {
                                            manHrContents,
                                            taskDetails: []
                                        }
                                    } as any
                                }

                            );
                        })
                        
                        _.forEach(_.get(ld, 'ouenTimeSheet', []), ts => {
                            let {timeSheet, workContent, workNo} = ts;
                            let start = _.get(timeSheet, 'start.timeWithDay');
                            let end = _.get(timeSheet, 'end.timeWithDay');
                            let taskBlock = _.find(_.get(hrTask, 'taskBlocks', []), tb => td.caltimeSpan.start == start && td.caltimeSpan.start == end);
                            let work = _.get(workContent, 'work');
                            let {taskList} = _.find(_.get(data, 'convertRes'), cr => moment(cr.ymd).isSame(moment(ld.ymd), 'days'));
                            let task = _.find(taskList, t => t.supNo == workNo);  
                            frameNos.push(vm.getFrameNo(events));
                            events.push({
                                taskFrameUsageSetting: ko.unwrap((vm.$settings)),
                                period: { start, end },
                                displayManHrRecordItems: _.get(ko.unwrap((vm.$settings)), 'manHrInputDisplayFormat.displayManHrRecordItems', []),
                                employeeId: vm.employee() || vm.$user.employeeId,
                                start: setTimeOfDate(moment(ld.ymd).toDate(), start),
                                end: setTimeOfDate(moment(ld.ymd).toDate(), end),
                                title: work ? getTitles(work, tasks) : '',
                                backgroundColor: work ? getBackground(work, tasks) : '',
                                textColor: '',
                                extendedProps: {
                                    frameNo: vm.getFrameNo(events),
                                    frameNos,
                                    id: randomId(),
                                    isTimeBreak: false,
                                    isChanged: false,
                                    status: 'update' as any,
                                    taskBlock: {
                                        caltimeSpan: { start, end },
                                        taskDetails: [{ supNo: workNo, taskItemValues: task.taskItemValues }]
                                    }
                                }
                            });
                        });
                        
                        
                        
                        
                    });

                    vm.events(events);
                    return;
                }

                vm.events([]);
            };

            vm.$datas
                .subscribe((datas) => {
                    computedEvents(datas, ko.unwrap(vm.$settings))

                });
    
            vm.events.subscribe((datas) => vm.dataChanged(true));

            vm.$settings
                .subscribe((settings) => computedEvents(ko.unwrap(vm.$datas), settings));

            vm.$toggle = {
                save: ko.computed({
                    read: () => {
                        const $settings = ko.unwrap(vm.$settings);
                        
                        if (!vm.dataChanged()) {
                            return false;
                        }

                        if (!$settings) {
                            return true;
                        }

                        const { startManHourInputResultDto } = $settings;

                        if (!startManHourInputResultDto) {
                            return true;
                        }

                        const { taskFrameUsageSetting } = startManHourInputResultDto;

                        if (!taskFrameUsageSetting) {

                            return true;
                        }
                        

//                        const { frameSettingList } = taskFrameUsageSetting;

//                        if (frameSettingList && frameSettingList.length) {
//                            return !!_.find(frameSettingList, ({ useAtr, frameNo }) => frameNo === 2 && useAtr === 1);
//                        }

                        return true;
                    }
                }),
                remove: ko.computed({
                    read: () => {
                        
                        let confirms = _.get(vm.$datas(),'lstComfirmerDto');
                        const editable = ko.unwrap(vm.editable);
                        let confimer = _.find(confirms, ['confirmSID',vm.$user.employeeId]);
                        return !editable && !!confimer;
                    }
                }),
                confirm: ko.computed({
                    read: () => {
                        let confirms = _.get(vm.$datas(),'lstComfirmerDto');
                        if (!_.isEmpty(confirms) && confirms.length >= 5) {
                            return false;
                        }
                        if (_.find(confirms, { confirmSID: vm.$user.employeeId })) {
                            return false;
                        }
                        const editable = ko.unwrap(vm.editable);
                        let confimer = _.find(_.get(vm.$datas(),'lstComfirmerDto'), ['confirmSID',vm.$user.employeeId]);
                        return !editable && !confimer;
                    }
                }),
            };

            if (mode) {
                // URLの値元に画面モードを判定する
                vm.editable(mode === '0');
            }

            ko.computed({
                read: () => {
                    const employeeId = ko.unwrap(vm.editable) === false ? ko.unwrap(vm.employee) : vm.$user.employeeId;
                    const date = ko.unwrap(vm.initialDate);
                    const dateRange = ko.unwrap(vm.dateRange);
                    const { start, end } = dateRange;
                    const setting = ko.unwrap(vm.$settings);

                    if (!employeeId || !setting) {
                        return;
                    }
                    
                    let itemIds = _.map(_.get(setting, 'manHrInputDisplayFormat.displayManHrRecordItems', []), item => { return item.itemId });

                    if (!!start && !!end && moment(date).isBetween(start, end, undefined, '[)')) {
                        const params: ChangeDateParam = {
                            employeeId,
                            refDate: moment(date).startOf('day').format(DATE_TIME_FORMAT),
                            displayPeriod: {
                                start: moment(start).startOf('day').format(DATE_TIME_FORMAT),
                                end: moment(end).subtract(1, 'day').startOf('day').format(DATE_TIME_FORMAT)
                            },
                            itemIds
                        };
                        cache.pair = sameCache(params);

                        if (cache.pair !== 2) {
                            updateCache(params);
                        }

                        if (cache.pair <= 0) {
                            // s.h.i.t
                            // vm.$datas(null);

                            vm
                                .$blockui('grayout')
                                .then(() => vm.$ajax('at', API.CHANGE_DATE, params))
                                .then((data: ChangeDateDto) => {
                                    vm.$datas(data);
                                    vm.dataChanged(false);
                                })
                                .always(() => vm.$blockui('clear'));
                        }
                    }
                }
            }).extend({ rateLimit: 250 });

            vm.confirmers = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);
                    const $date = ko.unwrap(vm.initialDate);
                    const $moment = moment($date).format(DATE_FORMAT);

                    if (datas) {
                        const { lstComfirmerDto } = datas;

                        return _
                            .chain(lstComfirmerDto)
                            .map(({
                                confirmSID: id,
                                confirmSCD: code,
                                businessName: name
                            }) => ({
                                id,
                                code,
                                name,
                                selected: false
                            }))
                            .value();
                    }

                    return [] as calendar.Employee[];
                }
            }).extend({ rateLimit: 500 });
    
            vm.breakTime = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);

                    if (datas) {

                        const { lstWorkRecordDetailDto } = datas;

                        return _
                            .chain(lstWorkRecordDetailDto)
                            .filter(({actualContent}) => { return !!actualContent.breakTimeSheets.length })
                            .map(({actualContent, date}) => {
                                const {breakTimeSheets} = actualContent;
                                return {
                                    dayOfWeek: vm.getDOW(date),
                                    breakTimes: _.map(breakTimeSheets, ({start, end}) => { return { start, end }; })
                                };
                            }).value();

                    }

                    return [];
                }
            });
    
            vm.businessHours = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);

                    if (datas) {

                        const { lstWorkRecordDetailDto } = datas;

                        return _
                            .chain(lstWorkRecordDetailDto)
                            .filter(({actualContent}) => { return !!actualContent.start.timeWithDay || !!actualContent.end.timeWithDay })
                            .map(({actualContent, date}) => {
                                const {start, end} = actualContent;
                                return {
                                    dayOfWeek: vm.getDOW(date),
                                    start: start.timeWithDay,
                                    end: end.timeWithDay
                                };
                            }).value();

                    }

                    return [];
                }
            });

            vm.attendanceTimes = ko.computed({
                read: () => {
                    const datas = ko.unwrap(vm.$datas);
                    const employee = ko.unwrap(vm.employee);

                    // need update by employId if: mode=1
                    const employeeId = employee || vm.$user.employeeId;

                    if (datas) {
                        const { lstWorkRecordDetailDto } = datas;

                        return _
                            .chain(lstWorkRecordDetailDto)
                            // .orderBy(['date'])
                            .filter(({ employeeId }) => employeeId === employeeId)
                            .map(({
                                date: strDate,
                                actualContent,
                            }) => {
                                const events: string[] = [];
                                const date = moment(strDate, DATE_FORMAT).toDate();
                                const { breakHours, end, start, totalWorkingHours } = actualContent;

                                if (start) {
                                    const { timeWithDay } = start;

                                    if (_.isNumber(timeWithDay)) {
                                        events.push(vm.$i18n('KDW013_21', [formatTime(timeWithDay, 'Time_Short_HM')]));
                                    }
                                }

                                if (end) {
                                    const { timeWithDay } = end;

                                    if (_.isNumber(timeWithDay)) {
                                        events.push(vm.$i18n('KDW013_22', [formatTime(timeWithDay, 'Time_Short_HM')]));
                                    }
                                }

                                if (_.isNumber(breakHours)) {
                                    events.push(vm.$i18n('KDW013_23', [formatTime(breakHours, 'Time_Short_HM')]));
                                }

                                if (_.isNumber(totalWorkingHours)) {
                                    events.push(vm.$i18n('KDW013_24', [formatTime(totalWorkingHours, 'Time_Short_HM')]));
                                }

                                return { date, events, };
                            })
                            .value();
                    }

                    return [] as calendar.AttendanceTime[];
                }
            }).extend({ rateLimit: 500 });

            // get settings Msg_1960
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.START))
                .fail(function(error) {
                    vm.$dialog.error({ messageId: error.messageId });
                })
                .then((response: StartProcessDto) => {

                    vm.$window
                        .storage('KDW013_SETTING')
                        .then((value: any) => {
                            if (value) {
                                vm.initialView(value.initialView || 'oneDay');
                                vm.firstDay(value.firstDay !== undefined ? value.firstDay : 1);
                                vm.scrollTime(value.scrollTime || 420);
                                vm.slotDuration(value.slotDuration || 30);
                            }
                        });


                    vm.$settings(response);
                })
                .always(() => vm.$blockui('clear'));

            // Init popup
            $(".popup-area-f").ntsPopup({
                trigger: ".popupButton-f",
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: ".popupButton-f"
                },
                showOnStart: false,
                dismissible: true
            });

            $(".popup-area-g").ntsPopup({
                trigger: ".popupButton-g",
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: ".popupButton-g"
                },
                showOnStart: false,
                dismissible: true
            });

            $(".popup-area-i").ntsPopup({
                trigger: ".popupButton-i",
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: ".popupButton-i"
                },
                showOnStart: false,
                dismissible: true
            });
        
        }

            getFrameNo(events){
                let maxNo = 20;
                let resultNo = 1;
                for (let i = 1; i < maxNo; i++) {
                    let event = _.find(events, e => _.get(e, 'extendedProps.frameNo') == i);
                    if (!event) {
                        resultNo = i;
                        break;
                    }
                }
                return resultNo;
            }

        getTaskValues(){
            const vm = this;
                 let items = [];

                 _.forEach(_.get(ko.unwrap((vm.$settings)), 'manHrInputDisplayFormat.displayManHrRecordItems', []), function(item) {
                     items.push({ itemId: item.itemId, value: null });
                 });
    
                return items;
        }

        reLoad(){
            const vm = this;
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.START))
                .fail(function(error) {
                    vm.$dialog.error({ messageId: error.messageId });
                })
                .then((response: StartProcessDto) => {
        
                    vm.$window
                        .storage('KDW013_SETTING')
                        .then((value: any) => {
                            if (value) {
                                vm.initialView(value.initialView || 'oneDay');
                                vm.firstDay(value.firstDay !== undefined ? value.firstDay : 1);
                                vm.scrollTime(value.scrollTime || 420);
                                vm.slotDuration(value.slotDuration || 30);
                            }
                        });
        
        
                    vm.$settings(response);
                })
                .always(() => vm.$blockui('clear'));
        }

        getDOW(date){
            const vm = this;
            const dateRange = ko.unwrap(vm.dateRange);
            if (dateRange) {
                const start = moment(dateRange.start);
                const end = moment(dateRange.end);
                let range = end.diff(start, 'days');
                let dates = [] ;
                for (let i = 0; i <= range; i++) {
                    dates.push(start.clone().add(i, 'days').format('YYYY/MM/DD'));
                }
                return _.indexOf(dates, date) + 1;
            }
           
                return 0;
        }

        mounted() {
            const vm = this;

            _.extend(window, { vm });
        }

        getQuery(){
            let query = location.search.substring(1);
            if (!query || !query.match(/=/)) {
                return {};
            }
            return JSON.parse('{"' + decodeURI(query).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}');
    
        }

        equipmentInput(){
            console.log('equipmentInput click');
        }

        getManHrlst(dates){
    
            let result = [];
            const vm = this;
            _.forEach(dates, date => {
                const eventInday = _.filter(vm.events(), (e) => { return moment(e.start).isSame(date, 'day') });

                if (eventInday.length) {


                    let listTaskDetails = [];

                    let  manHrContents = _.get(_.find(_.get(vm.$datas(), 'convertRes'), cr => moment(cr.ymd).isSame(moment(date), 'days')), 'manHrContents', []);
                    
                    _.forEach(_.filter(eventInday, e => _.get(e, 'extendedProps.isTimeBreak', false) == false), e => {
                        let {taskDetails} = _.get(e, 'extendedProps.taskBlock');
                        _.forEach(taskDetails, td => {

                            _.forEach(td.taskItemValues, ti => {
                                const start = (moment(e.start).hour() * 60) + moment(e.start).minute();
                                const end = (moment(e.end).hour() * 60) + moment(e.end).minute();
                                if (ti.itemId == 1) { ti.value = taskDetails.length > 1 ? null : start };
                                if (ti.itemId == 2) { ti.value = taskDetails.length > 1 ? null : end };
                                if (taskDetails.length == 1) {
                                    if (ti.itemId == 3) { ti.value = end - start };
                                }

                            });

                        });

                        listTaskDetails.push(...taskDetails);
                    });

                    result.push({ ymd: date, taskList: listTaskDetails, manHrContents });
                }
            });

            return result;
        }

        saveData() {
            const vm = this;
            const { events, dateRange } = vm;
            const { HAND_CORRECTION_MYSELF, HAND_CORRECTION_OTHER } = EditStateSetting;
            const { start, end } = ko.unwrap(dateRange);

            if (!start || !end) {
                return;
            }

            const $events = ko.unwrap(events);
            const dateRanges = () => {
                const dates: Date[] = [];
                const begin = moment(start);

                while (begin.isBefore(end, 'day')) {
                    dates.push(begin.toDate());

                    begin.add(1, 'day');
                }

                return dates;
            };
    
            let employeeId = vm.employee() ? vm.employee() : vm.$user.employeeId;
    
            let editStateSetting = !vm.employee() ? HAND_CORRECTION_MYSELF : vm.employee() == vm.$user.employeeId ? HAND_CORRECTION_MYSELF : HAND_CORRECTION_OTHER;
    
            let mode =  vm.editable() ? 0 : vm.employee() === vm.$user.employeeId ? 0 : 1;

            let changedDates = _.chain(dateRanges()).map(date => {
                const events = _.filter($events, (e) => { return moment(e.start).isSame(date, 'day') });
                const data = _.find(vm.$datas().lstWorkRecordDetailDto, (e) => { return moment(e.date).isSame(date, 'day') });

                if (events.length != _.size(_.get(data, 'lstWorkDetailsParamDto'))) {
                    return { date: date, changed: true };
                }


                const isChanged = _.find(events, (e) => { return _.get(e, 'extendedProps.isChanged') });

                if (isChanged) {
                    return { date: date, changed: true };
                }

                return { date: date, changed: false };
            }).filter(d => { return d.changed }).map(d => moment(d.date).format(DATE_TIME_FORMAT)).value();
    
    
            let workDetails = vm.createWorkDetails(dateRanges()); 
    
            let manHrlst = vm.getManHrlst(dateRanges());

            let integrationOfDailys = vm.createIDaily(dateRanges());
            
            const command: nts.uk.ui.at.kdw013.RegisterWorkContentCommand = {
                changedDates,
                editStateSetting,
                employeeId,
                manHrlst,
                integrationOfDailys,
                mode,
                workDetails
            };
    
    
            console.log(command);
            vm                .$blockui('grayout')                 //作業を登録する                .then(() => vm.$ajax('at', API.REGISTER, command))                .then((response: RegisterWorkContentDto) => {                    vm.dataChanged(false);                    if (response) {                            const { lstErrorMessageInfo, lstOvertimeLeaveTime } = response;                        if (!lstErrorMessageInfo || lstErrorMessageInfo.length === 0) {                            return vm.$dialog                                .info({ messageId: 'Msg_15' })                                .then(() => lstOvertimeLeaveTime)                                .then(() => {vm.dataChanged(false);                                            vm.reLoad();                                })                        } else {                            let errors = lstErrorMessageInfo.map(x => {                                return {                                    message: x.messageError,                                    messageId: x.resourceID,                                    supplements: {}                                };                            });                            nts.uk.ui.dialog.bundledErrors({ errors });                        }                    }                    return $                        .Deferred()                        .resolve()                        .then(() => null);                })                .fail((response: ErrorMessage) => {                    const { messageId, parameterIds } = response;                    return vm.$dialog                         Msg_2066, Msg_2080                        .error({ messageId, messageParams: parameterIds })                        .then(() => null);                })                .then((data: OvertimeLeaveTime[] | null) => {                    if (data && data.length) {                        vm.openDialogCaculationResult(data);                    }                })                .always(() => vm.$blockui('clear'));
        }

        // 日付を変更する
        // UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.日付を変更する
        datesSet(start: Date, end: Date) {
            const vm = this;

            vm.dateRange({ start, end });
        }

        createWorkDetails(dates){
            let vm = this;
            let result = [];

            _.forEach(dates, date => {
                const lstWorkDetailsParamCommand= []
                
                const eventHas2Task = _
                    .chain(vm.events())
                    .filter(({ start }) => moment(start).isSame(date, 'day'))
                    .filter(({ extendedProps }) => _.get(extendedProps, 'taskBlock.taskDetails', []).length > 1).value();
                    
                _.forEach(eventHas2Task, ({ start, end, extendedProps }) => {
                    const {
                        taskBlock
                    } = extendedProps;
                    
                    _.forEach(_.get(taskBlock, 'taskDetails',[]), td => {

                        lstWorkDetailsParamCommand.push({
                            supportFrameNo: td.supNo,
                            timeZone: {
                                end: getTimeOfDate(end),
                                start: getTimeOfDate(start)
                            }
                        });
                    });
                    
                      
                });
                
                if (lstWorkDetailsParamCommand.length) {
                    result.push({
                        date: moment(date).format(DATE_TIME_FORMAT),
                        lstWorkDetailsParamCommand
                    });
                }
            });

            return result;
        }

        createIDaily(dates){
            const vm = this;
            let result = [];

            let ids = _.get(vm.$datas(), 'lstIntegrationOfDaily', []);
            _.forEach(dates, date => {

                const id = _.find(ids, id => moment(id.ymd).isSame(moment(date), 'days'));

                
                if (id) {

                    //mapping break time
                    const breakTimes = _.filter(vm.events(), e => moment(e.start).isSame(date, 'day') && _.get(e, 'extendedProps.isTimeBreak', false) == true);

                    let breakTime = _.get(id, 'breakTime');
                    breakTime.breakTimeSheets = _.map(breakTimes, bt => {

                        return {
                            no: _.get(bt, 'extendedProps.no'),
                            breakTime: _.get(bt, 'extendedProps.breakTime'),
                            start: (moment(bt.start).hour() * 60) + moment(bt.start).minute(),
                            end: (moment(bt.end).hour() * 60) + moment(bt.end).minute(),
                        };
                    });
                    //mapping normal block   
                    const breakTimes = _.filter(vm.events(), e => moment(e.start).isSame(date, 'day') && _.get(e, 'extendedProps.isTimeBreak', false) == false);
                }
            });

            return ids;
        }
        // 作業実績を確認する
        confirm() {
            const vm = this;
            const { $user, $datas, employee, initialDate } = vm;
            const date = ko.unwrap(initialDate);
            const employeeId = ko.unwrap(employee);

            if (employeeId) {
                const command: AddWorkRecodConfirmationCommand = {
                    //対象者
                    // get from A2_5 control
                    employeeId,
                    //対象日
                    // get from initialDate
                    date: moment(date).toISOString(),
                    //確認者
                    // 作業詳細.作業グループ
                    confirmerId: $user.employeeId
                };

                vm
                    .$blockui('grayout')
                    // 作業実績を確認する
                    .then(() => vm.$ajax('at', API.ADD, command))
                    .then((lstComfirmerDto: ConfirmerDto[]) => {
                        const _datas = ko.unwrap($datas);

                        if (_datas) {
                            _datas.lstComfirmerDto = lstComfirmerDto;

                            // update confirmers
                            $datas.valueHasMutated();
                            vm.dataChanged(false);
                        } else {
                            $datas({ lstComfirmerDto, lstWorkRecordDetailDto: [], workCorrectionStartDate: '', workGroupDtos: [] });
                        }
                    })
                    //.then(() => vm.editable.valueHasMutated())
                    .always(() => vm.$blockui('clear'));
            }
        }

        // 作業実績の確認を解除する
        removeConfirm() {
            const vm = this;
            const { $user, $datas, employee, initialDate } = vm;
            const date = ko.unwrap(initialDate);
            const employeeId = ko.unwrap(employee);

            if (employeeId) {
                const command = {
                    //対象者
                    // get from A2_5 control
                    employeeId,
                    //対象日
                    // get from initialDate
                    date: moment(date).toISOString(),
                    //確認者
                    // 作業詳細.作業グループ
                    confirmerId: $user.employeeId
                };

                vm
                    .$blockui('grayout')
                    // 作業実績の確認を解除する
                    .then(() => vm.$ajax('at', API.DELETE, command))
                    .then((lstComfirmerDto: ConfirmerDto[]) => {
                        const _datas = ko.unwrap($datas);

                        if (_datas) {
                            _datas.lstComfirmerDto = lstComfirmerDto;

                            // update confirmers
                            $datas.valueHasMutated();
                            vm.dataChanged(false);
                        } else {
                            $datas({ lstComfirmerDto, lstWorkRecordDetailDto: [], workCorrectionStartDate: '', workGroupDtos: [] });
                        }
                    })
                    // trigger reload event on child component
                    //.then(() => vm.editable.valueHasMutated())
                    .always(() => vm.$blockui('clear'));
            }
        }

        private openDialogCaculationResult(data: OvertimeLeaveTime[]) {
            const vm = this;

            vm.$window
                .modal('at', '/view/kdw/013/d/index.xhtml', data)
                .then(() => { });
        }

        // Popup F:
        updateFavName() {
            const vm = this;

            const updateFavNameCommand: UpdateFavNameCommand = {
                favId: vm.favoriteTaskItem().favoriteId,
                favName: vm.favTaskName()
            }

            vm.$blockui('show');
            vm.$validate(".input-f").then((valid: boolean) => {
				if (valid) {
                    vm.$ajax('at', API.UPDATE_TASK_NAME_F, updateFavNameCommand)
                    .done(() => {
                        vm.$dialog.info({ messageId: 'Msg_15' }).then(()=>{
                            vm.reLoad();    
                        }); 
                    }).fail((error: any) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });

                } else {
                    vm.$blockui("clear");
                }
            });

        }

        // Popup G:
        registerOneDayFavTask() {
            const vm = this;
            let favId = vm.oneDayFavoriteSet().favId;

            if (favId =='') {
                vm.addOneDayFavTask();
            } else {
                vm.updateOneDayFavName(favId);
            }
        }

        updateOneDayFavName(favTaskId: string) {
            const vm = this;

            const updateFavNameCommand: UpdateFavNameCommand = {
                favId: favTaskId,
                favName: vm.oneDayFavTaskName()
            }

            vm.$blockui('show');
            vm.$validate(".input-g").then((valid: boolean) => {
				if (valid) {
                    vm.$ajax('at', API.UPDATE_TASK_NAME_G, updateFavNameCommand)
                    .done(() => {
                        vm.$dialog.info({ messageId: 'Msg_15' }).then(()=>{
                            vm.reLoad();    
                        }); 
                    }).fail((error: any) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });

                } else {
                    vm.$blockui("clear");
                }
            });

        }

        addOneDayFavTask(){
            const vm = this;

            vm.taskBlocks([
                {
                    startTime: 1,
                    endTime: 2,
                    taskContents: [{
                        frameNo: 1,
                        taskContent:{
                            itemId: 1,
                            taskCode: "1"
                        }
                    }
                ]
            }]);

            const registerFavoriteForOneDayCommand : RegisterFavoriteForOneDayCommand = {
                employeeId: vm.$user.employeeId,
                taskName: vm.oneDayFavTaskName(),
                contents: ko.unwrap(vm.taskBlocks)

            }

            vm.$blockui('show');
            vm.$validate(".input-g").then((valid: boolean) => {
				if (valid) {
                    vm.$ajax('at', API.ADD_FAV_TASK_G, registerFavoriteForOneDayCommand)
                    .done(() => {
                        vm.$dialog.info({ messageId: 'Msg_15' }).then(()=>{
                            vm.reLoad();    
                        }); 
                    }).fail((error: any) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });

                } else {
                    vm.$blockui("clear");
                }
            });
        }

        createWarning() {
            const vm = this;

            vm.taskDtos([
                {
                    code : "1",
                    taskFrameNo: 1,
                    displayInfo: {
                        taskName: "taskName1",
                        taskAbName: "taskAbName1",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "9",
                    taskFrameNo: 1,
                    displayInfo: {
                        taskName: "taskName11",
                        taskAbName: "taskAbName11",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "2",
                    taskFrameNo: 1,
                    displayInfo: {
                        taskName: "taskName111",
                        taskAbName: "taskAbName111",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "2",
                    taskFrameNo: 2,
                    displayInfo: {
                        taskName: "taskName2",
                        taskAbName: "taskAbName2",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "3",
                    taskFrameNo: 3,
                    displayInfo: {
                        taskName: "taskName3",
                        taskAbName: "taskAbName3",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "4",
                    taskFrameNo: 4,
                    displayInfo: {
                        taskName: "taskName4",
                        taskAbName: "taskAbName4",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                },
                {
                    code : "5",
                    taskFrameNo: 5,
                    displayInfo: {
                        taskName: "taskName5",
                        taskAbName: "taskAbName5",
                        color: "red",
                        taskNote: "taskNote"
                    },
                    childTaskList: [""],
                    expirationStartDate: "",
                    expirationEndDate: "",
                    cooperationInfo: null
                }

            ]);

            vm.ouenWorkTimes([
                {
                    workNo: 1,
                    workTime: {
                        totalTime: 11
                    }
                },
                {
                    workNo: 2,
                    workTime: {
                        totalTime: 22
                    }
                },
                {
                    workNo: 3,
                    workTime: {
                        totalTime: 33
                    }
                }
                
            ]);

            vm.ouenWorkTimeSheets([
                {
                    workNo: 1,
                    workContent: {
                        work: {
                            workCD1: "9",
                            workCD2: "2",
                            workCD3: "3",
                            workCD4: "4",
                            workCD5: "5",
                        }
                    },
                    timeSheet: {
                        workNo: 1,
                        start: {
                            timeWithDay: 100
                        },
                        end: {
                            timeWithDay: 300
                        }
                    }
                },
                {
                    workNo: 2,
                    workContent: {
                        work: {
                            workCD1: "1",
                            workCD2: "2",
                            workCD3: "3",
                            workCD4: "4",
                            workCD5: "5",
                        }
                    },
                        timeSheet: {
                            workNo: 2,
                            start: {
                                timeWithDay: 100
                            },
                            end: {
                                timeWithDay: 300
                            }
                        }
                   
                },
                {
                    workNo: 3,
                    workContent: {
                        work: {
                            workCD1: "1",
                            workCD2: "2",
                            workCD3: "3",
                            workCD4: "4",
                            workCD5: "5",
                        }
                    },
                    timeSheet: {
                        workNo:3,
                        start: {
                            timeWithDay: 100
                        },
                        end: {
                            timeWithDay: 300
                        }
                    }
                }

            ]);

            var warnings: TaskInfo[] = [ ];

            _.forEach(vm.ouenWorkTimes(), wt => {

                let ouenWorkTimeSheet: OuenWorkTimeSheetOfDailyAttendance  = ko.utils.arrayFirst(vm.ouenWorkTimeSheets(), function (e) {return e.workNo == wt.workNo});
                let workCD1 = ouenWorkTimeSheet.workContent.work.workCD1;
                let workCD2 = ouenWorkTimeSheet.workContent.work.workCD2;
                let workCD3 = ouenWorkTimeSheet.workContent.work.workCD3;
                let workCD4 = ouenWorkTimeSheet.workContent.work.workCD4;
                let workCD5 = ouenWorkTimeSheet.workContent.work.workCD5;
                let taskName1 = '';
                let taskName2 = '';
                let taskName3 = '';
                let taskName4 = '';
                let taskName5 = '';

                _.forEach(vm.taskDtos(), task => {
                    if (task.taskFrameNo == 1 && task.code == workCD1) {
                        taskName1 = task.displayInfo.taskName;
                    }

                    if (task.taskFrameNo == 2 && task.code == workCD2) {
                        taskName2 = task.displayInfo.taskName;
                    }

                    if (task.taskFrameNo == 3 && task.code == workCD3) {
                        taskName3 = task.displayInfo.taskName;
                    }

                    if (task.taskFrameNo == 4 && task.code == workCD4) {
                        taskName4 = task.displayInfo.taskName;
                    }

                    if (task.taskFrameNo == 5 && task.code == workCD5) {
                        taskName5 = task.displayInfo.taskName;
                    }
                });
                
                let taskNames =
                    (taskName1 != '' ? taskName1 + ' ' : '') +
                    (taskName2 != '' ? taskName2 + ' ' : '') + 
                    (taskName3 != '' ? taskName3 + ' ' : '') +
                    (taskName4 != '' ? taskName4 + ' ' : '') +
                    taskName5;
                
                    warnings.push({
                        workNo: wt.workNo,
                        name: taskNames,
                        time: wt.workTime.totalTime
                });
               
            });

            vm.taskInfos(warnings);
        }

        openEDialog(data: any, vm: any) {
            
            let param = {
                workNo: data.workNo,
                taskDtos: ko.unwrap(vm.taskDtos),
                ouenWorkTimes: ko.unwrap(vm.ouenWorkTimes),
                ouenWorkTimeSheets: ko.unwrap(vm.ouenWorkTimeSheets),
                taskSettings: ko.unwrap(vm.taskSettings),
                //対象日
                date: vm.targetDate()
            }
		
            vm.$window.modal('at', '/view/kdw/013/e/index.xhtml', param).then(() => {});
        }
    }

    
}
