module nts.uk.ui.at.kdw013.c {
	import getText = nts.uk.resource.getText;
	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	
    const COMPONENT_NAME = 'kdp013c';

    const DATE_FORMAT = 'YYYY-MM-DD';
    const DATE_TIME_FORMAT = 'YYYY-MM-DDT00:00:00.000\\Z';

    const style = `.edit-event {
        width: 380px;
    }
    .edit-event .header {
        box-sizing: border-box;
        position: relative;
        padding-bottom: 5px;
        line-height: 35px;
        margin-top: -5px;
    }
    .edit-event .header .actions {
        position: absolute;
        top: 0px;
        right: -5px;
    }
    .edit-event .header .actions button {
        margin: 0;
        padding: 0;
        box-shadow: none;
        border: none;
        border-radius: 50%;
        width: 30px;
    }
    .edit-event table {
        width: 370px;
    }
    .edit-event table>tbody>tr>td:first-child {
        vertical-align: top;
        padding-top: 6px;
    }
    .edit-event table>tbody>tr.functional>td {
        text-align: right;
    }
	.edit-event table>tbody>tr.functional>td a{
		color: #30cc40;
	}
    .edit-event table>tbody>tr>td>.ntsControl {
        width: 255px;
        display: block;
        box-sizing: border-box;
        margin-bottom: 10px;
    }
	.edit-event table>tbody>tr>td>.ntsControl.fix input.nts-input{
	    border: 1px solid #999;
	}
	.edit-event table>tbody>tr>td>.ntsControl.fix .error input.nts-input{
		border-color: #ff6666;
	}
    .edit-event table>tbody>tr>td>.ntsControl>input {
        width: 100%;
        box-sizing: border-box;
    }
    .edit-event table>tbody>tr>td>.ntsControl>textarea {
        width: 100%;
        height: 80px;
        display: block;
        box-sizing: border-box;
    }
    .edit-event .time-range-control input.nts-input {
        width: 60px;
        text-align: center;
        padding: 5px 3px;
    }
    .edit-event .time-range-control input.nts-input+span {
        margin-left: 7px;
        margin-right: 7px;
    }
    .edit-event .nts-dropdown .message,
    .edit-event .nts-description .message,
    .edit-event .time-range-control .message {
        display: none;
        color: #ff6666;
        font-size: 12px;
        padding-top: 3px;
    }
    .edit-event .nts-dropdown.error .message,
    .edit-event .nts-description.error .message,
    .edit-event .time-range-control.error .message {
        display: block;
    }
    .edit-event .nts-description.error textarea.nts-input,
    .edit-event .time-range-control.error input.nts-input {
        border: 1px solid #ff6666 !important;
    }
    .edit-event .nts-description:not(.error) textarea.nts-input,
    .edit-event .time-range-control:not(.error) input.nts-input {
        border: 1px solid #999 !important;
    }
    .edit-event table tr td:first-child {    
        max-width: 105px;
        line-break: anywhere;
		padding-left: 5px;
    }

`;

    const { randomId } = nts.uk.util;
    const { getTimeOfDate, setTimeOfDate } = share;

    const API: API = {
        START: '/screen/at/kdw013/c/start',
        SELECT: '/screen/at/kdw013/c/select'
    };

    @handler({
        bindingName: 'kdw-confirm',
        validatable: true,
        virtual: false
    })
    export class ConfirmBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KnockoutObservable<ConfirmContent | null>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const resource = valueAccessor();
            const msgid = $('<div>', { 'class': '' }).appendTo(element).get(0);
            const content = $('<div>', { 'class': 'content' }).prependTo(element).get(0);

            ko.applyBindingsToNode(msgid, {
                text: ko.computed({
                    read: () => {
                        const msg = ko.unwrap(resource);

                        if (msg) {
                            return msg.messageId;
                        }

                        return '';
                    },
                    disposeWhenNodeIsRemoved: element
                })
            }, bindingContext);

            ko.applyBindingsToNode(content, {
                text: ko.computed({
                    read: () => {
                        const msg = ko.unwrap(resource);

                        if (msg) {
                            const { messageId, messageParams } = msg;

                            return nts.uk.resource.getMessage(messageId, messageParams);
                        }

                        return '';
                    },
                    disposeWhenNodeIsRemoved: element
                })
            }, bindingContext);

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: 'kdw-ttg',
        validatable: true,
        virtual: false
    })
    export class KdwToggleBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KnockoutObservable<boolean>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const accessor = valueAccessor();

            const $if = ko.computed({
                read: () => {
                    return ko.unwrap(accessor);
                },
                disposeWhenNodeIsRemoved: element
            });

            const hidden = ko.computed({
                read: () => {
                    return !ko.unwrap(accessor);
                },
                disposeWhenNodeIsRemoved: element
            });

            ko.applyBindingsToNode(element, { if: $if, css: { hidden } }, bindingContext);
            return { controlsDescendantBindings: true };
        }
    }

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class BindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const params = valueAccessor();

            ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

            return { controlsDescendantBindings: true };
        }

    }

	const html = `
        <div class="edit-event">
            <div class="header">
                <div data-bind="i18n: 'KDW013_26'"></div>
                <div class="actions">
                    <button class="close" tabindex="-1" data-bind="click: $component.close, icon: 202, size: 12"></button>
                </div>
            </div>
			<table class="timePeriod">
                <colgroup>
                    <col width="105px" />
                </colgroup>
                <tbody>
                    <tr>
                        <td data-bind="i18n: 'KDW013_27'"></td>
                        <td class="caltimeSpanView">
                            <div data-bind="
                                    kdw-timerange: taskBlocks.caltimeSpanView,
                                    update: flag,
                                    hasError: $component.timeError,
                                    exclude-times: $component.params.excludeTimes,
									range: range,
									showInputTime: showInputTime
                                "></div>
                        </td>
                    </tr>
				</tbody>
			</table>
			<div class="taskDetails" data-bind="foreach: taskBlocks.taskDetailsView">
                <table>
	                <colgroup>
	                    <col width="105px" />
	                </colgroup>
                    <tbody data-bind = "foreach: taskItemValues">
						<!-- ko if: (itemId == 3) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input class="inputRange" data-bind="ntsTimeEditor: {
											value: value,
											mode: 'time',
											inputFormat: 'time',
											required: true,
											enable: true,
											option: {width: '40px'}
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
                        <!-- ko if: (itemId == 4) && use-->
                            <tr>
                                <td data-bind="text: lable"></td>
                                <td><div data-bind="
                                        dropdown: value,
                                        items: options,
                                        required: true,
                                        name: lable,
                                        hasError: ko.observable(false),
										flagError: $parent.flag,
                                        visibleItemsCount:10
                                    "></div></td>
                            </tr>
                        <!-- /ko -->
                        <!-- ko if: (itemId == 5 || itemId == 6 || itemId == 7 || itemId == 8) && use -->
                            <tr>
                                <td data-bind="text: lable"></td>
                                <td><div data-bind="
                                        dropdown: value,
                                        name: lable,
                                        items: options,
                                        visibleItemsCount:10
                                    "></div></td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (itemId == 9) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsTimeWithDayEditor: { 
											name: 'Time With Day', 
											constraint:'TimeWithDayAttr', 
											value: value, 
											enable: true, 
											required: false,
											option: {
												width: '233px',
												timeWithDay: true
											}
											 }" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (itemId == 10) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsTextEditor: {
											value: value,
											option: {width: '233px'},
											required: false,
											enable: true,
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (itemId == 11) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											value: value,
											option: {
												width: '223px', 
												numberGroup: true, 
												decimallength: 2, 
												currencyformat: 'JPY',
												currencyposition: 'left'
											},
											required: false,
											enable: true,
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (itemId == 12) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											value: value,
											option: {width: '233px'},
											required: false,
											enable: true,
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: itemId == 13 || itemId == 14 || itemId == 15 || itemId == 16 || 
						itemId == 17 || itemId == 18 || itemId == 19 || itemId == 20 || itemId == 21 || 
						itemId == 22 || itemId == 23 || itemId == 24 || itemId == 25 || itemId == 26 || 
						itemId == 27 || itemId == 28 || itemId == 29 -->
							<tr>
                                <td data-bind="text: itemId"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											value: value,
											option: {width: '233px'},
											required: false,
											enable: true,
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
	                </tbody>
	            </table>
			</div>
			<table>
				<tbody>
					<tr class="functional">
                        <td>
							<a href="#" data-bind="i18n: 'KDW013_57', click: addTaskDetails"></a>
                            <br />
							<button class="proceed" data-bind="i18n: 'KDW013_43', click: function() { $component.save.apply($component, []) }, disable: timeError || errors || !$root.errors.isEmpty()"></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <style>
            .message.overlay {
                position: fixed;
                display: none;
                top: 0;
                left:0;
                right: 0;
                bottom: 0;
                background-color: #aaaaaa;
                opacity: 0.3;
            }
            .message.container {
                position: fixed;
                display: none;
                border: 1px solid #767171;
                width: 310px;
                box-sizing: border-box;
                background-color: #fff;
                position: fixed;
                top: calc(50% - 67.5px);
                left: calc(50% - 65px);
            }
            .message.overlay.show,
            .message.container.show {
                display: block;
            }
            .message.overlay+.container .title {
                background-color: #F2F2F2;
                border-bottom: 1px solid #767171;
                padding: 5px 12px;
                box-sizing: border-box;
                font-size: 1rem;
                font-weight: 600;
            }
            .message.overlay+.container .body {
                padding: 20px 10px 10px 10px;
                border-bottom: 1px solid #767171;
                box-sizing: border-box;
            }
            .message.overlay+.container .body>div:last-child {
                text-align: right;
                box-sizing: border-box;
            }
            .message.overlay+.container .foot {
                text-align: center;
                padding: 10px 0;
                box-sizing: border-box;
            }
            .message.overlay+.container .foot button:first-child {
                margin-right: 10px;
            }
			.taskDetails{
				overflow-y: scroll;
			}
			.taskDetails::-webkit-scrollbar{
				width: 8px;
			}
			.taskDetails::-webkit-scrollbar-thumb {
				border-radius: 4px;
				background-color: #dededfa6;
			}
			.taskDetails::-webkit-scrollbar-thumb:hover
			{
				background-color: #c1c1c1;
			}
			.taskDetails table{
				border: 1px solid #999;
   				margin-bottom: 5px;
			}
			.taskDetails table:nth-last-child(1){
   				margin-bottom: 0px;
			}
			.taskDetails table tr:first-child td:first-child{
   				top: 10px;
				position: relative
			}
			.taskDetails table tr:first-child td>div{
   				margin-top: 10px;
			}
        </style>
        `;

    @component({
        name: COMPONENT_NAME,
        template: html
    })
    export class ViewModel extends ko.ViewModel {
		
        errors: KnockoutObservable<boolean> = ko.observable(false);
        timeError: KnockoutObservable<boolean> = ko.observable(false);
        taskFrameSettings!: KnockoutComputed<a.TaskFrameSettingDto[]>;
        flag: KnockoutObservable<boolean> = ko.observable(false);
		showInputTime: KnockoutObservable<boolean> = ko.observable(false);
		range: KnockoutObservable<number | null> = ko.observable(null);
		taskBlocks: ManHrPerformanceTaskBlockView = 
			new ManHrPerformanceTaskBlockView(
				{ 
					caltimeSpan: {start: null, end: null}, 
					taskDetails: []
				}, 
                __viewContext.user.employeeId,
                this.flag,
				this.showInputTime,
                false
			);
		
        constructor(public params: Params) {
            super();

            const vm = this;
            const { $settings } = params;
        
            vm.taskFrameSettings = ko.computed({
                read: () => {
                    const settings = ko.unwrap($settings);
                    if (settings) {
                        return settings.startManHourInputResultDto.taskFrameUsageSetting.frameSettingList;
                    }
                    //return [];
					// fake data
					return [{frameNo: 1, frameName: "物件名称", useAtr: 1},
							{frameNo: 2, frameName: "プロジェクト", useAtr: 1},
							{frameNo: 3, frameName: "設計フェーズ", useAtr: 1},
							{frameNo: 4, frameName: "製造フェーズ", useAtr: 1},
							{frameNo: 5, frameName: "ﾃｽﾄﾌｪｰｽﾞ", useAtr: 1}] 
                }
            });
            this.taskFrameSettings.subscribe((t: a.TaskFrameSettingDto[]) => vm.taskBlocks.updateSetting(t));

            this.timeError.subscribe(() => {
                vm.updatePopupSize();
            });
            this.flag.subscribe(() => {
                vm.checkError();
            });
			this.range.subscribe(() => {
                $('.inputRange').trigger('validate');
            });
			
			vm.taskBlocks.taskDetailsView.subscribe((taskDetails: ManHrTaskDetailView[]) => {
				vm.showInputTime(taskDetails.length > 1);
				let interval = setInterval(function () {
                    vm.updatePopupSize();
					resetHeight();
                });	
				setTimeout(() => {
					clearInterval(interval);
				}, 1000);
            });

			$(window).resize(function () {
				resetHeight();
			});
        }
		
		// update popup size
        updatePopupSize(){
			const vm = this;
            vm.params.position.valueHasMutated();
        }

		checkError(){
            const vm = this;
			_.each(vm.taskBlocks.taskDetailsView(), (task: ManHrTaskDetailView)=>{
				if(task.isErorr()){
					vm.errors(true);
					return;
				}
            });
			resetHeight();
            vm.updatePopupSize();
		}

        fakeData(start: Date, end: Date):IManHrPerformanceTaskBlock{
            return { caltimeSpan: { start, end }, 
                    taskDetails: [
                        { 
                            supNo: 0, 
                            taskItemValues: [
                                { itemId: 3, value: '', type: 0 },
                                { itemId: 4, value: '', type: 0 },
                                { itemId: 5, value: '', type: 0 },
                                { itemId: 6, value: '', type: 0 },
                                { itemId: 7, value: '', type: 0 },
                                { itemId: 8, value: '', type: 0 },
								{ itemId: 9, value: '', type: 0 },
								{ itemId: 10, value: '', type: 0 },
								{ itemId: 11, value: '', type: 0 },
								{ itemId: 12, value: '', type: 0 },
								{ itemId: 13, value: '', type: 0 },
                                { itemId: 14, value: '', type: 0 },
                                { itemId: 15, value: '', type: 0 },
                                { itemId: 16, value: '', type: 0 },
                                { itemId: 17, value: '', type: 0 },
                                { itemId: 18, value: '', type: 0 },
								{ itemId: 19, value: '', type: 0 },
								{ itemId: 20, value: '', type: 0 },
								{ itemId: 21, value: '', type: 0 },
								{ itemId: 22, value: '', type: 0 },
								{ itemId: 23, value: '', type: 0 },
                                { itemId: 24, value: '', type: 0 },
                                { itemId: 25, value: '', type: 0 },
                                { itemId: 26, value: '', type: 0 },
                                { itemId: 27, value: '', type: 0 },
                                { itemId: 28, value: '', type: 0 },
								{ itemId: 29, value: '', type: 0 }
                            ] 
                        }
                    ]
                };
        }

        mounted() {
            const vm = this;
            const { $el, params} = vm;
            const { view, position, data } = params;

            const cache = {
                view: ko.unwrap(view),
                position: ko.unwrap(position)
            };
			data.subscribe((event: FullCalendar.EventApi| null) => {
				if (event) {
                    const {extendedProps, employeeId, start, end } = event as any as calendar.EventRaw;
                    let {taskBlocks} = extendedProps;
                    taskBlocks = vm.fakeData(start, end),
                    vm.taskBlocks.update(taskBlocks, employeeId, true, this.taskFrameSettings());
                    setTimeout(() => {
						vm.updatePopupSize();
					}, 150);
				}
			});

            // focus to first input element
            ko.computed({
                read: () => {
                    const _v = ko.unwrap(view);
                    if (_v === 'edit' && cache.view !== _v) {
                        $($el).find('input:first').focus();
                    }
                    cache.view = _v;
                },
                disposeWhenNodeIsRemoved: $el
            });
            position
                .subscribe((p: any) => {
                    if (!p) {
                        vm.timeError(false);
						vm.errors(false);
                        cache.view = 'view';
                    }
                    if (p && cache.position !== p) {
                        $($el).find('input:first').focus();
                    }
                    cache.position = p;
                });
            const $ctn = $($el);
            $ctn
                // prevent tabable to out of popup control
                .on("keydown", ":tabbable", (evt: JQueryKeyEventObject) => {
                    const fable = $ctn.find(":tabbable:not(.close)").toArray();
                    const last = _.last(fable);
                    const first = _.first(fable);
                    if (evt.keyCode === 9) {
                        if ($(evt.target).is(last) && evt.shiftKey === false) {
                            first.focus();
                            evt.preventDefault();
                        } else if ($(evt.target).is(first) && evt.shiftKey === true) {
                            last.focus();
                            evt.preventDefault();
                        }
                    }
                });
            if (!$(`style#${COMPONENT_NAME}`).length) {
                $('<style>', { id: COMPONENT_NAME, html: style }).appendTo('head');
            }
            _.extend(window, { pp: vm });
        }

        close() {
            const vm = this;
            const { params} = vm;
            const { data } = params;
            $.Deferred()
                .resolve(true)
                .then(() => {
                    const { title, extendedProps } = ko.unwrap(data);
                    return _.isEmpty(extendedProps) || (!title && extendedProps.status === 'new');
                })
                .then((isNew: boolean | null) => {
                    if (isNew) {
                        vm.$dialog
                            .confirm({ messageId: 'Msg_2094' })
                            .then((v: 'yes' | 'no') => {
                                if (v === 'yes') {
									$(vm.$el).find('.inputRange').ntsError("clear");
                                    vm.params.close("yes");
                                }
                            });
                    } else {						
                        if (vm.changed()) {
                            vm.$dialog
                                .confirm({ messageId: 'Msg_2094' })
                                .then((v: 'yes' | 'no') => {
                                    if (v === 'yes') {
										$(vm.$el).find('.inputRange').ntsError("clear");
                                         params.close();
                                    }
                                });
                        } else {
							$(vm.$el).find('.inputRange').ntsError("clear");	
                            params.close();
                        }
                    }
                });
        }

		addTaskDetails(){
			const vm = this;
			if(vm.taskBlocks.taskDetailsView().length == 1){
				_.find(vm.taskBlocks.taskDetailsView()[0].taskItemValues(), (i: TaskItemValue) => {return i.itemId == 3}).value(vm.range().toString());	
			}
			vm.taskBlocks.addTaskDetailsView();
		}
    
        save() {
            const vm = this;
            const { params } = vm;
            const { data } = params;
            const event = data();
            const { employeeId } = vm.$user;
            $.Deferred()
                .resolve(true)
                .then(() => {$(vm.$el).find('input').trigger('blur') && $(vm.$el).find('.inputRange').ntsError("hasError")})
                .then(() => vm.errors() && vm.timeError())
                .then((invalid: boolean) => {
                    if (!invalid) {
                        if (event) {
                            const { start } = event;
                            const tr = vm.taskBlocks.caltimeSpanView();
                            const task = vm.taskBlocks.getTaskInfo();
                            event.setStart(setTimeOfDate(start, tr.start as number));
                            event.setEnd(setTimeOfDate(start, tr.end as number));
                            const { status } = event.extendedProps;
                            if (!event.extendedProps.id) {
                                event.setExtendedProp('id', randomId());
                            }
                            if (['new', 'add'].indexOf(status) === -1) {
                                event.setExtendedProp('status', 'add');
                            } else {
                                event.setExtendedProp('status', 'update');
                            }

                            if (task) {
                                const { displayInfo } = task;
                                if (displayInfo) {
                                    const {color} = displayInfo;
                                    event.setProp('backgroundColor', color);
                                }
                            }
                            event.setProp('title', vm.taskBlocks.getTitles());
                            event.setExtendedProp('sId', employeeId);
                            event.setExtendedProp('workingHours', (tr.start) - (tr.start));
                            event.setExtendedProp('taskBlocks', vm.taskBlocks.getTaskDetails());
                        }

                        // close popup
                        params.close();
                    }
                });
        }
    
        changed(){
            return this.taskBlocks.isChangedTime() || this.taskBlocks.isChangeTasks();
        }
	}
	
	export class ManHrPerformanceTaskBlockView extends ManHrPerformanceTaskBlock{
		taskDetailsView: KnockoutObservableArray<ManHrTaskDetailView>;
        caltimeSpanView: KnockoutObservable<{start: number, end: number}> = ko.observable({start: null, end: null});
		employeeId: string = '';
		setting: a.TaskFrameSettingDto[] = [];
		constructor(taskBlocks: IManHrPerformanceTaskBlock, employeeId: string, private flag: KnockoutObservable<boolean>, private showInputTime: KnockoutObservable<boolean>, loadData: boolean) {
			super(taskBlocks);
			const vm = this;
			vm.employeeId = employeeId;
			vm.taskDetailsView = ko.observableArray(
				_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetailView(t, taskBlocks.caltimeSpan.start, employeeId, flag, showInputTime, loadData, []))
			);
            if(taskBlocks.caltimeSpan.start && taskBlocks.caltimeSpan.end){
                vm.caltimeSpanView({start: getTimeOfDate(taskBlocks.caltimeSpan.start), end: getTimeOfDate(taskBlocks.caltimeSpan.end)});
            }
            vm.taskDetailsView.subscribe((tasks: ManHrTaskDetailView[])=>{
				let item = 0;
				_.forEach(tasks, (task: ITaskItemValue[])=>{
					_.forEach(task, ()=>{
						item++;
					});
				});
                setTimeout(() => {
					flag(!flag());
				}, item);
				setTimeout(() => {
					resetHeight();
					flag(!flag());
				}, 1);
            });
        }
        
        update(taskBlocks: IManHrPerformanceTaskBlock, employeeId: string, loadData: boolean, setting: a.TaskFrameSettingDto[]) {
			const vm = this;
			vm.setting = setting;
			vm.employeeId = employeeId;
			vm.taskDetails(_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetail(t)));
			vm.taskDetailsView(
				_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetailView(t, taskBlocks.caltimeSpan.start, vm.employeeId, vm.flag, vm.showInputTime, loadData, setting))
			);
			vm.caltimeSpan = new TimeSpanForCalc(taskBlocks.caltimeSpan);
            if(taskBlocks.caltimeSpan.start && taskBlocks.caltimeSpan.end){
                vm.caltimeSpanView({start: getTimeOfDate(taskBlocks.caltimeSpan.start), end: getTimeOfDate(taskBlocks.caltimeSpan.end)});
            }
        }
        
        updateSetting(setting: a.TaskFrameSettingDto[]){
            const vm = this;
			vm.setting = setting;
            _.forEach(vm.taskDetailsView(), (t: ManHrTaskDetailView) => {
                t.setLableUses(setting);
            });
        }

		addTaskDetailsView():void {
			const vm = this;
			let taskItemValues: ITaskItemValue[] = [];
			_.forEach(vm.taskDetailsView()[0].taskItemValues(), (taskItemValue: TaskItemValue)=>{
				taskItemValues.push({ itemId: taskItemValue.itemId, value: '', type: taskItemValue.type });
			});
			let newTaskDetails: IManHrTaskDetail = { supNo: 0, taskItemValues: taskItemValues }
			vm.taskDetailsView.push(new ManHrTaskDetailView(newTaskDetails, vm.caltimeSpan.start, vm.employeeId, vm.flag, vm.showInputTime, true, vm.setting));
		}

		isChangedTime(): boolean{
			const vm = this;
			if(vm.caltimeSpanView().start == getTimeOfDate(vm.caltimeSpan.start) && 
				vm.caltimeSpanView().start == getTimeOfDate(vm.caltimeSpan.end))
				return true;
			return false;
		}
		isChangeTasks():boolean{
            const vm = this;
            _.each(vm.taskDetailsView(), (taskDetail: ManHrTaskDetailView) => {
                if(taskDetail.isChangedItemValues()){
                    return true;
                }
            })
			return false;
        }
        getTitles(): string{
            const vm = this;
            const titles: string[] = [];
            _.each(vm.taskDetailsView(), (task: ManHrTaskDetailView) => {
                titles.push(task.getTitles());
            });
            return titles.join("\n\n");
        }
        getTaskInfo(): any{
            const vm = this;
            if (vm.taskDetailsView().length > 0 && vm.taskDetailsView()[0].taskItemValues().length > 0) {
                const selected = _.find(vm.taskDetailsView()[0].taskItemValues(), ({ itemId }) => itemId === 4);
                if (selected) {
                    const selectedInfor = _.find(selected.options(), ({ id }) => selected.value() === id);
                    return selectedInfor.$raw;
                }
            }
            return null;
        }
        getTaskDetails():IManHrPerformanceTaskBlock{
            const vm = this;
            const taskDetails :IManHrTaskDetail[] = [];
            _.each((vm.taskDetailsView()), (task: ManHrTaskDetailView) => {
                taskDetails.push({supNo: task.supNo, taskItemValues: task.getTaskItemValue()});
            });
            return {
                caltimeSpan: { 
                    start: setTimeOfDate(vm.caltimeSpan.start, vm.caltimeSpanView().start), 
                    end:  setTimeOfDate(vm.caltimeSpan.start, vm.caltimeSpanView().end)
                }, 
                taskDetails};
        }
        
	}
	
	export class ManHrTaskDetailView extends ManHrTaskDetail {
		employeeId: string;
        itemBeforChange: ITaskItemValue[];
		constructor(manHrTaskDetail: IManHrTaskDetail, private start: Date, employeeId: string, private flag: KnockoutObservable<boolean>, showInputTime: KnockoutObservable<boolean>, loadData: boolean, setting: a.TaskFrameSettingDto[]) {
			super(manHrTaskDetail);
			this.employeeId = employeeId;
			this.itemBeforChange = manHrTaskDetail.taskItemValues;
            const vm = this;
            const [first, second, thirt, four, five] = setting;

			let workCD1, workCD2, workCD3, workCD4, workCD5;
			_.each(vm.taskItemValues(), (item: TaskItemValue) => {
                if(item.itemId == 3) {
                    item.use = showInputTime;
					workCD1 = item.value();
					item.lable(getText("KDW013_25"));
					item.value.subscribe(() => {
	                    vm.flag(!vm.flag());
                	});
				}else if(item.itemId == 4) {
                    if (first) {
                        vm.setLableUse(item, first);
                    }else{
						item.use(false);
					}
					workCD1 = item.value();
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(2, value);
                        }
						setTimeout(() => {
                        	vm.flag(!vm.flag());
						}, 1);
                	});
				}else if(item.itemId == 5){
                    if (second) {
                        vm.setLableUse(item, second);
                    }else{
						item.use(false);
					}
					workCD2 = item.value();
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(3, value);
	                    }
                	});
				}else if(item.itemId == 6){
                    if (thirt) {
                        vm.setLableUse(item, thirt);
                    }else{
						item.use(false);
					}
					workCD3 = item.value();
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(4, value);
	                    }
                	});
				}else if(item.itemId == 7){
                    if (four) {
                        vm.setLableUse(item, four);
                    }else{
						item.use(false);
					}
					workCD4 = item.value();
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(5, value);
	                    }
                	});
				}
				else if(item.itemId == 8){
                    if (five) {
                        vm.setLableUse(item, five);
                    }else{
						item.use(false);
					}
					workCD5 = item.value();
				}
            });
			if(loadData){
				const params: StartWorkInputPanelParam = {
	                refDate: moment(start).toISOString(),
	                employeeId: employeeId,
	                workGroupDto: { workCD1, workCD2, workCD3, workCD4, workCD5 }
            	};
				ajax('at', API.START, params).done((response: StartWorkInputPanelDto | null)=>{
					if (response) {
						vm.setWorkLists(response);
					}                       
				});
			}
			
        }

		setLableUse(item: TaskItemValue, settings: a.TaskFrameSettingDto){
            item.lable(settings.frameName);
            item.use(settings.useAtr === 1);
		}

        setLableUses(settings: a.TaskFrameSettingDto[]) {
			const vm = this;
            const [first, second, thirt, four, five] = settings;
            _.each(vm.taskItemValues(), (item: TaskItemValue) => {
                if(item.itemId == 4 && first) {
                    vm.setLableUse(item, first);
				}else if(item.itemId == 5 && second){
					vm.setLableUse(item, second);
				}else if(item.itemId == 6 && thirt){
					vm.setLableUse(item, thirt);
				}else if(item.itemId == 7 && four){
					vm.setLableUse(item, four);
				}else if(item.itemId == 8 && five){
					vm.setLableUse(item, five);
                }
            });
        };
        
        getTaskItemValue():ITaskItemValue[]{
            const vm = this;
            const result :ITaskItemValue[] = [];
            _.each((vm.taskItemValues), (item: TaskItemValue) => {
                if(item.value() != null && item.value() != undefined && item.value() != ''){
                    result.push({itemId : item.itemId, value: item.value(), type: item.type});
                }
            });
            return result;
        }

		setWorkList(nextItemId: number, value: string): void{
			const vm = this;
            const param: SelectWorkItemParam = {
                refDate: moment(vm.start).format(DATE_TIME_FORMAT),
                employeeId: vm.employeeId,
                taskCode: value,
                taskFrameNo: nextItemId
            };
			const itemNext = _.find(vm.taskItemValues(), (i) => {return i.itemId == nextItemId});
			if(itemNext){
				block.grayout();
	            return ajax('at', API.SELECT, param).done((data: TaskDto[]) => {
					itemNext.options(vm.getMapperList(data, itemNext.value));
	            }).always(() => block.clear());
			}
        }
        
	    getTitles(): string{
            const vm = this;
            const title: string[] = [];
            _.each(vm.taskItemValues(), (item: TaskItemValue) => {
                if(item.itemId == 4) {
                    const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 5){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 6){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 7){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 8){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}
            });
            return title.join("\n");
        }               
		
		isChangedItemValues(): boolean{
			const vm = this;
			_.each(vm.taskItemValues(), (itemValue: TaskItemValue)=>{
				const item = _.find(vm.itemBeforChange, (i) => {return i.itemId == itemValue.itemId});
				if(item.value != itemValue.value()){
					return true;
				}	
			});
			return false;
		}
		isErorr(): boolean{
			const vm = this;
			const item1 = _.find(vm.taskItemValues(), (i) => {return i.itemId == 4});
			if(item1.value() == '' || item1.value() == null){
				return true;
			}	
			return false;
		}
		setWorkLists(taskList: StartWorkInputPanelDto): void{
			const vm = this;
			const { taskListDto1, taskListDto2, taskListDto3, taskListDto4, taskListDto5 } = taskList;
			_.each(vm.taskItemValues(), (i: TaskItemValue) => {
        		if(i.itemId == 4){
					i.options(vm.getMapperList(taskListDto1, i.value));
				}else if(i.itemId == 5){
					i.options(vm.getMapperList(taskListDto2, i.value));
				}
				else if(i.itemId == 6){
					i.options(vm.getMapperList(taskListDto3, i.value));
				}
				else if(i.itemId == 7){
					i.options(vm.getMapperList(taskListDto4, i.value));
				}
				else if(i.itemId == 8){
					i.options(vm.getMapperList(taskListDto5, i.value));
				}
            });
		}
		getMapperList(tasks: TaskDto[], code: KnockoutObservable<string> | undefined): DropdownItem[]{
			const vm = this;
            const lst: DropdownItem[] = [vm.mapper(null)];
            if (code) {
                const taskSelected = _.find(tasks, { 'code': code() });
                if (!taskSelected) {
                    lst.push({ id: code(), code: code(), name: getText('KDW013_40'), selected: false, $raw: null });
                }
            }
            _.each(tasks, (t: TaskDto) => {
                lst.push(vm.mapper(t));
            });
            return lst;
        }

		mapper($raw: TaskDto | null): DropdownItem {
			if($raw == null){
				return { id: '', code: '', name: getText('KDW013_41'), $raw: null, selected: false };
			}
            return { id: $raw.code, code: $raw.code, name: $raw.displayInfo.taskName, selected: false, $raw: $raw };
        }
	}

    type Params = {
        close: (result?: 'yes' | 'cancel' | null) => void;
        remove: () => void;
        mode: KnockoutObservable<boolean>;
        view: KnockoutObservable<'view' | 'edit'>;
        data: KnockoutObservable<FullCalendar.EventApi>;
        position: KnockoutObservable<null | any>;
        excludeTimes: KnockoutObservableArray<share.BussinessTime>;
        $settings: KnockoutObservable<a.StartProcessDto | null>;
        $share: KnockoutObservable<StartWorkInputPanelDto | null>;
    }

    export type DropdownItem = {
        id: string;
        code: string;
        name: string;
        selected: boolean;
        $raw: any;
    };
	export function resetHeight():void {
		let caltimeSpanViewHeight = $('.caltimeSpanView').height();
		let innerHeight = window.innerHeight
		let heightTaskDetails = -5; 
		_.each($('.taskDetails table'),(table:any)=>{
			heightTaskDetails = heightTaskDetails + table.offsetHeight + 5;
		});
		
		let aboveBelow = 160;
		if(caltimeSpanViewHeight > 40){
			aboveBelow = aboveBelow + caltimeSpanViewHeight - 40;
		}
		if(innerHeight - aboveBelow >= heightTaskDetails){
			$('.taskDetails').css({ "overflow-y": "hidden"});
			$('.taskDetails').css({ "max-height": heightTaskDetails + 'px' });
		}else if(innerHeight - aboveBelow < heightTaskDetails){
			$('.taskDetails').css({ "overflow-y": "scroll"});
			$('.taskDetails').css({ "max-height": (innerHeight - aboveBelow - 10) + 'px' });
		}
	}
}