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
    const BREAKTIME_COLOR = '#ff99ff';
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
        
        GET_FAV_TASK: '/screen/at/kdw013/a/get-fav-task',
        // 作業お気に入りを新規追加する
        ADD_FAV_TASK_F: '/screen/at/kdw013/f/create_task_fav',
        // 作業お気に入り名称を変更する
        UPDATE_TASK_NAME_F: '/screen/at/kdw013/f/update_task_name',
        GET_FAV_ONE_DAY: '/screen/at/kdw013/a/get-fav-one-day',
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
    
    export class StartProcess {
        attItemName: Array<any>;
        dailyAttendanceItem: Array<any>;
        divergenceReasonInputMethods: Array<any>;
        divergenceTimeRoots: Array<any>;
        employeeInfos: Array<any>;
        favTaskDisplayOrders: any
        favTaskItems: Array<any>;
        lstEmployeeInfo: Array<any>;
        manHrInputDisplayFormat: any
        oneDayFavSets: Array<any>;
        oneDayFavTaskDisplayOrders: any;
        taskFrameUsageSetting: any;
        tasks: Array<any>;
        workTimeSettings: Array<any>;
        workTypes: Array<any>;
        workplaceInfos: Array<any>;

        constructor(data) {
            this.attItemName = data.attItemName;
            this.dailyAttendanceItem = data.dailyAttendanceItem;
            this.divergenceReasonInputMethods = data.divergenceReasonInputMethods;
            this.divergenceTimeRoots = data.divergenceTimeRoots;
            this.employeeInfos = data.employeeInfos;
            this.favTaskDisplayOrders = data.favTaskDisplayOrders;
            this.favTaskItems = data.favTaskItems;
            this.lstEmployeeInfo = data.lstEmployeeInfo;
            this.manHrInputDisplayFormat = data.manHrInputDisplayFormat;
            this.oneDayFavSets = data.oneDayFavSets;
            this.oneDayFavTaskDisplayOrders = data.oneDayFavTaskDisplayOrders;
            this.taskFrameUsageSetting = data.taskFrameUsageSetting;
            this.tasks = data.tasks;
            this.workTimeSettings = data.workTimeSettings;
            this.workTypes = data.workTypes;
            this.workplaceInfos = data.workplaceInfos;
        }

        updateFavTask(data) {
            this.favTaskDisplayOrders = data.favTaskDisplayOrders;
            this.favTaskItems = data.favTaskItems;
        }
        updateFavOneday(data) {
            this.oneDayFavSets = data.oneDayFavSets;
            this.oneDayFavTaskDisplayOrders = data.oneDayFavTaskDisplayOrders;
        }
    }

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
        //biến này để phục vụ việc lấy data khi thay đổi ở màn K  
        inputDate: KnockoutObservable<Date> = ko.observable();
    
        employee: KnockoutObservable<string> = ko.observable('');

        confirmers!: KnockoutComputed<calendar.Employee[]>;
        // need map with [KDW013_21, KDW013_22, KDW013_23, KDW013_24] resource
        attendanceTimes!: KnockoutComputed<calendar.AttendanceTime[]>;

        // Change date range data model
        $datas: KnockoutObservable<ChangeDateDto | null> = ko.observable(null);

        // settings (first load data)
        $settings: KnockoutObservable<StartProcess | null> = ko.observable(null);
    
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
        
        // 作業リスト
        taskDtos: KnockoutObservableArray<TaskDto> =  ko.observableArray();

        // 日別勤怠の応援作業時間
        ouenWorkTimes: KnockoutObservableArray<OuenWorkTimeOfDailyAttendance> =  ko.observableArray();

        // 日別勤怠の応援作業時間帯
        ouenWorkTimeSheets: KnockoutObservableArray<OuenWorkTimeSheetOfDailyAttendance> =  ko.observableArray();

        //対象日
        targetDate: KnockoutObservable<Date> =  ko.observable();
    
        fullCalendar : KnockoutObservable<FullCalendarComponent> = ko.observable();

        constructor() {
            super();
            const vm = this;
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
                        
                        let hrTask = _.find(_.get(data, 'dailyManHrTasks', []), dt => moment(dt.date).isSame(moment(ld.ymd),'days'));
                        
                        
                        let {manHrContents} = _.find(_.get(data, 'convertRes'), cr => moment(cr.ymd).isSame(moment(ld.ymd), 'days'));
                        if (ko.unwrap(vm.isShowBreakTime)) {
                            _.forEach(_.get(ld, 'breakTime.breakTimeSheets', []), bt => {
                                frameNos.push(bt.no);
                                events.push(
                                    {
                                        start: setTimeOfDate(moment(ld.ymd).toDate(), bt.start),
                                        end: setTimeOfDate(moment(ld.ymd).toDate(), bt.end),
                                        title: vm.$i18n('KDW013_79'),
                                        backgroundColor: BREAKTIME_COLOR,
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
                            });
                        }
                        _.forEach(_.get(hrTask, 'taskBlocks', []), tb => {
                            const {taskDetails, caltimeSpan} = tb;
                            const ts = _.find(_.get(ld, 'ouenTimeSheet', []), ot => ot.workNo == _.get(taskDetails[0], 'supNo', null));
                            const {start, end} = caltimeSpan;
                            const work = _.get(ts, 'workContent.work');
                           
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
                                        taskDetails: taskDetails
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
            
            vm.inputDate.subscribe((date)=>{
                vm.reLoad();
            });

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
                    const setting = ko.unwrap(vm.$settings);
                    
                    const employee = ko.unwrap(vm.employee);
                    const { start,end } = ko.unwrap(vm.dateRange);
                    const dateRanges = () => {
                        const dates: Date[] = [];
                        const begin = moment(start);

                        while (begin.isBefore(end, 'day')) {
                            dates.push(begin.toDate());

                            begin.add(1, 'day');
                        }

                        return dates;
                    };

                    // need update by employId if: mode=1
                    const employeeId = employee || vm.$user.employeeId;

                    if (datas) {
                        
                      return   _.chain(dateRanges())
                            .map(date => {
                                let events: string[] = [];
                                let convert = _.find(_.get(datas, 'convertRes', []), cvr => { return moment(cvr.ymd).isSame(moment(date), 'days'); } )
                                let manHrContents = _.get(convert, 'manHrContents', []);
                                let attItemName = _.get(setting, 'attItemName', []);
                                const workTypes = _.get(setting, 'workTypes');
                                
                                let wkTypeCd = _.get(_.find(manHrContents, hr => { return hr.itemId == 28 }), 'value');
                                    
                                if (wkTypeCd) {
                                    
                                    let name = _.get(_.find(workTypes, wt => { return wt.workTypeCode == wkTypeCd }), 'name');
                                    //PC3_2 PC3_3
                                    events.push({ title: vm.$i18n('KDW013_67'), text: wkTypeCd + ' ' + (name ? name : vm.$i18n('KDW013_40')) });
                                }

                                let start = _.get(_.find(manHrContents, hr => { return hr.itemId == 31 }), 'value');
                                let end = _.get(_.find(manHrContents, hr => { return hr.itemId == 34 }), 'value');

                                if (start && end) {
                                    //PC3_4 PC3_5
                                    events.push({ title: vm.$i18n('KDW013_68'), text: vm.$i18n('KDW013_73', [formatTime(start, 'Time_Short_HM'), formatTime(end, 'Time_Short_HM')]) });
                                }

                                let rdis = _.sortBy(_.get(setting, 'manHrInputDisplayFormat.recordColumnDisplayItems', []), ['order']);

                                _.forEach(rdis, rdi => {

                                    let value = _.get(_.find(manHrContents, hr => { return hr.itemId == rdi.attendanceItemId }), 'value');
                                    //PC3_6 PC3_7
                                    if (!_.isNil(value)) {
                                        events.push({ title: rdi.displayName, text: !_.isNaN(Number(value)) ? (formatTime(value, 'Time_Short_HM')) : value });
                                    }

                                });

                                return { date, events };
                            })
                            .value();
                    }

                    return [] as calendar.AttendanceTime[];
                }
            }).extend({ rateLimit: 500 });
            let inputDate = ko.unwrap(vm.inputDate);
            // get settings Msg_1960
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.START, { inputDate }))
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


                    vm.$settings(new StartProcess(response));
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
            let inputDate = ko.unwrap(vm.inputDate);
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.START, { inputDate }))
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

        getChangedDates(dates){
    
            let vm = this;
            const $events = ko.unwrap(vm.events);
           return _.chain(dates).map(date => {
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

            let changedDates = vm.getChangedDates(dateRanges());
    
    
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
                    
                    let nos =[];
                    _.forEach(_.get(taskBlock, 'taskDetails',[]), td => {
                        nos.push(td.supNo);
                    });
                    
                    lstWorkDetailsParamCommand.push({
                            supportFrameNos: nos,
                            timeZone: {
                                end: getTimeOfDate(end),
                                start: getTimeOfDate(start)
                            }
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
                            vm.reloadTaskFav();    
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
        reloadTaskFav(){
            const vm = this;
    
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.GET_FAV_TASK).done(data => {
                    vm.$settings().updateFavTask(data);
                    vm.fullCalendar().computedTaskDragItems(ko.unwrap(vm.$datas), ko.unwrap(vm.$settings));
                }))
                .always(() => vm.$blockui('clear'));
        }

        // Popup G:
        registerOneDayFavTask() {
            const vm = this;
            let favId = _.get(vm.oneDayFavoriteSet(), 'favId', '');

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
                            vm.reloadOneDayFav();    
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

        reloadOneDayFav(){
            const vm = this;
    
            vm
                .$blockui('grayout')
                .then(() => vm.$ajax('at', API.GET_FAV_ONE_DAY).done(data => {
                    vm.$settings().updateFavOneday(data);
                    vm.fullCalendar().computedOnedayDragItems(ko.unwrap(vm.$datas), ko.unwrap(vm.$settings));
                }))
                .always(() => vm.$blockui('clear'));
        }

        addOneDayFavTask(){
            const vm = this;

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
                            vm.reloadOneDayFav();    
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

           taskInfos(){
                let vm = this;
                let warnings = [];
                _.forEach(vm.ouenWorkTimes(), wt => {

                    let os: OuenWorkTimeSheetOfDailyAttendance = ko.utils.arrayFirst(vm.ouenWorkTimeSheets(), function(e) { return e.workNo == wt.no });
                    let workCD1 = _.get(os, 'workContent.work.workCD1', null);
                    let workCD2 = _.get(os, 'workContent.work.workCD2', null);
                    let workCD3 = _.get(os, 'workContent.work.workCD3', null);
                    let workCD4 = _.get(os, 'workContent.work.workCD4', null);
                    let workCD5 = _.get(os, 'workContent.work.workCD5', null);
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
                    
                    if (!!os) {
                        warnings.push({
                            workNo: wt.no,
                            name: taskNames,
                            time: wt.workTime.totalTime
                        });
                    }
                });
                return warnings;
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
