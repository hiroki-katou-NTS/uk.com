module nts.uk.ui.at.kdw013.c {
	import getText = nts.uk.resource.getText;
	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	import error = nts.uk.ui.dialog.error;
	
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
	.edit-event table>tbody>tr.functional>td>span{
		float: left;
	}
    .edit-event table>tbody>tr>td>.ntsControl {
        width: 255px;
        display: block;
        box-sizing: border-box;
        margin-bottom: 10px;
    }
	.edit-event table>tbody>tr>td>.ntsControl.fix textarea.nts-input{
		height: 54px;
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
    const { getTimeOfDate, setTimeOfDate, number2String } = share;

    const API: API = {
        START: '/screen/at/kdw013/common/start',
        SELECT: '/screen/at/kdw013/c/select',
		START_F: '',
        ADD_FAV_TASK_F: ''
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

//            element.removeAttribute('data-bind');

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
                            <div class="ntsControl">
								<input data-bind="ntsTimeEditor: {
									name: nts.uk.resource.getText('KDW013_14'),
									value: taskBlocks.caltimeSpanView.start,
									constraint: 'AttendanceTime', 
									mode: 'time',
									inputFormat: 'time',
									required: true,
									enable: true,
									option: {width: '40px'}
									}" />
								<span data-bind="text: nts.uk.resource.getText('KDW013_30')"></span>
								<input data-bind="ntsTimeEditor: {
									name: nts.uk.resource.getText('KDW013_31'),
									value: taskBlocks.caltimeSpanView.end,
									constraint: 'AttendanceTime', 
									mode: 'time',
									inputFormat: 'time',
									required: true,
									enable: true,
									option: {width: '40px'}
									}" />
								<span data-bind="visible: !showInputTime(), text: taskBlocks.caltimeSpanView.range"></span>
							</div>
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
											name: nts.uk.resource.getText('KDW013_25'),
											value: value,
											constraint: 'AttendanceTime', 
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
                                <td><div data-bind="ntsComboBox: {
										options: options,
										optionsValue: 'code',
										name: lable,
										value: value,
										optionsText: 'name',
										editable: true,
										required: true,
										dropDownAttachedToBody: false,
										visibleItemsCount: 10,
										width : '255px',
										columns: [
											{ prop: 'code', length: 1 },
											{ prop: 'name', length: 8 },
										]}
                                    "></div></td>
                            </tr>
                        <!-- /ko -->
                        <!-- ko if: (itemId == 5 || itemId == 6 || itemId == 7 || itemId == 8) && use -->
                            <tr>
                                <td data-bind="text: lable"></td>
                                <td><div data-bind="ntsComboBox: {
										options: options,
										optionsValue: 'code',
										name: lable,
										value: value,
										optionsText: 'name',
										editable: true,
										dropDownAttachedToBody: false,
										visibleItemsCount: 10,
										width : '255px',
										columns: [
											{ prop: 'code'},
											{ prop: 'name', length: 8 },
										]}
                                    "></div></td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if:  (type == 0 && itemId > 8) && use -->
                            <tr>
                                <td data-bind="text: lable"></td>
                                <td><div data-bind="ntsComboBox: {
										options: options,
										optionsValue: 'code',
										name: lable,
										value: value,
										optionsText: 'name',
										editable: true,
										dropDownAttachedToBody: false,
										visibleItemsCount: 5,
										width : '255px',
										columns: [
											{ prop: 'code', length: 1 },
											{ prop: 'name', length: 8 },
										]}
                                    "></div></td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: type == 2 && itemId > 8 && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											name: lable,
											constraint: primitiveValue, 
											value: value,
											option: {width: '233px',
													unitID: 'TIMES'},
										}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (type == 3 && itemId > 8) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											name: lable,
											constraint: primitiveValue, 
											value: value,
											option: {
												width: '223px', 
												numberGroup: true, 
												decimallength: 2, 
												currencyformat: 'JPY',
												currencyposition: 'left'
											}
										}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (type == 5 && itemId > 8) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsTimeEditor: {
											name: lable,
											constraint: primitiveValue, 
											value: value,
											mode: 'time',
											inputFormat: 'time',
											option: {width: '233px'}
											}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (type == 6 && itemId > 8) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsTimeWithDayEditor: { 
											name: lable,
											constraint: primitiveValue, 
											constraint:'TimeWithDayAttr', 
											value: value, 
											option: {
												width: '233px',
												timeWithDay: true
											}
										}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (type == 7 && itemId > 8) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<textarea data-bind="ntsMultilineEditor: {
											name: lable,
											constraint: primitiveValue, 
											value: value,
											option: {width: '233px'}}" />
									</div>
								</td>
                            </tr>
                        <!-- /ko -->
						<!-- ko if: (type == 9 && itemId > 8) && use -->
							<tr>
                                <td data-bind="text: lable"></td>
                                <td>
									<div class="ntsControl fix">
										<input data-bind="ntsNumberEditor: {
											name: lable,
											constraint: primitiveValue, 
											value: value,
											option: {width: '233px'}}" />
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
							<span></span>
							<a href="#" data-bind="i18n: 'KDW013_69', click: addTaskDetails"></a>
                            <br />
							<button class="proceed" data-bind="i18n: 'KDW013_43', click: function() { $component.save.apply($component, []) }, enable: errors"></button>
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
			.edit-event .header .btn-error{
				position: absolute;
			}
        </style>
        `;

    @component({
        name: COMPONENT_NAME,
        template: html
    })
    export class ViewModel extends ko.ViewModel {
		
        taskFrameSettings: KnockoutObservableArray<a.TaskFrameSettingDto> = ko.observableArray([]);
		showInputTime: KnockoutObservable<boolean> = ko.observable(false);
		range: KnockoutObservable<number | null> = ko.observable(null);
		taskBlocks: ManHrPerformanceTaskBlockView = 
			new ManHrPerformanceTaskBlockView(
				{ 
					caltimeSpan: {start: null, end: null}, 
					taskDetails: []
				}, 
                __viewContext.user.employeeId,
//                this.flag,
				this.showInputTime,
			);
		frameNos:KnockoutObservableArray<number> = ko.observableArray([]);
		
		errors: KnockoutComputed<boolean> = ko.computed({
			read: () => {
				const hasError = ko.unwrap(nts.uk.ui.errors.hasError());
				return !hasError;
			}
		});
		
		
        constructor(public params: Params) {
            super();

            const vm = this;
        
            vm.taskFrameSettings.subscribe((t: a.TaskFrameSettingDto[]) => vm.taskBlocks.updateSetting(t));

//            this.flag.subscribe(() => {
//                vm.checkError();
//            });
			this.range.subscribe(() => {
                //$('.inputRange').trigger('validate');
            });

			this.taskBlocks.caltimeSpanView.start.subscribe(()=>{
				
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
					return;
				}
            });
			resetHeight();
    		vm.updatePopupSize();
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
					nts.uk.ui.errors.clearAll();
					setTimeout(() => {
						jQuery('button.btn-error.small.danger').appendTo('.edit-event .functional td>span');
					}, 500);
                    const {extendedProps, start} = event as any as calendar.EventRaw;
                    let {displayManHrRecordItems, taskBlock, employeeId} = extendedProps;
					vm.frameNos(extendedProps.frameNos);
					if(taskBlock.taskDetails[0].supNo == null){
						taskBlock.taskDetails[0].supNo = vm.generateFrameNo();
					}
                    vm.taskFrameSettings(extendedProps.taskFrameUsageSetting.taskFrameUsageSetting.frameSettingList);
					let param = {
						refDate: start,
						itemIds: _.filter(_.map(displayManHrRecordItems, i => i.itemId), t => t > 8)
					}

					block.grayout();
		            ajax('at', API.START, param).done((data: StartWorkInputPanelDto) => {
		            	vm.taskBlocks.update(taskBlock, employeeId, data, vm.taskFrameSettings());
						setTimeout(() => {
							vm.updatePopupSize();
						}, 150);
					}).always(() => block.clear());
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
	
		generateFrameNo(): number{
			let vm = this
			let frameNo;
			for(var i = 1; i < 21; i++){
				var no = _.find(vm.frameNos(), n => n == i);
				if(!no){
					frameNo = i;
					vm.frameNos.push(i);
					break;
				}
			}
			return frameNo;
		};

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
									nts.uk.ui.errors.clearAll();
									setTimeout(() => {
										jQuery('button.btn-error.small.danger').appendTo('#functions-area');									
									}, 100);
                                    vm.params.close("yes");
                                }
                            });
                    } else {						
                        if (vm.changed()) {
                            vm.$dialog
                                .confirm({ messageId: 'Msg_2094' })
                                .then((v: 'yes' | 'no') => {
                                    if (v === 'yes') {
										nts.uk.ui.errors.clearAll();
										setTimeout(() => {
											jQuery('button.btn-error.small.danger').appendTo('#functions-area');									
										}, 100);
                                         params.close();
                                    }
                                });
                        } else {
							nts.uk.ui.errors.clearAll();
							setTimeout(() => {
								jQuery('button.btn-error.small.danger').appendTo('#functions-area');									
							}, 100);
                            params.close();
                        }
                    }
                });
        }

		addTaskDetails(){
			const vm = this;
			if(vm.frameNos().length >= 20) {
				return;
			}
			vm.taskBlocks.addTaskDetailsView(vm.generateFrameNo());
		}
		
		sumTotalTime():number{
			let vm = this;
			let totalTime = 0; 
			_.forEach(vm.taskBlocks.taskDetailsView(), (taskdetail: ManHrTaskDetailView) => {
				totalTime = totalTime + taskdetail.getTime();	
			});
			return totalTime;
		}
    
        save() {
            const vm = this;
            const { params } = vm;
            const { data } = params;
            const event = data();
            const { employeeId } = vm.$user;
            $.Deferred()
                .resolve(true)
                .then(() => {
					$(vm.$el).find('input').trigger('blur'); 
					$('.ui-igcombo').ntsError('check'); })
                .then(() => nts.uk.ui.errors.hasError())
                .then((invalid: boolean) => {
                    if (!invalid) {
						if(vm.sumTotalTime() > (vm.taskBlocks.caltimeSpanView.end() - vm.taskBlocks.caltimeSpanView.start())){
							error({ messageId: "Msg_2217"});
							return;
						}
                        if (event) {
                            const { start } = event;
                            const tr = vm.taskBlocks.caltimeSpanView;
                            const task = vm.taskBlocks.getTaskInfo();
                            event.setStart(setTimeOfDate(start, tr.start()));
                            event.setEnd(setTimeOfDate(start, tr.end()));
                            if (!event.extendedProps.id) {
                                event.setExtendedProp('id', randomId());
                            }
                            event.setExtendedProp('status', 'update');

                            if (task) {
                                const { displayInfo } = task;
                                if (displayInfo) {
                                    const {color} = displayInfo;
                                    event.setProp('backgroundColor', color);
                                }
                            }
                            event.setProp('title', vm.taskBlocks.getTitles());
                            event.setExtendedProp('sId', employeeId);
                            event.setExtendedProp('workingHours', (tr.start()) - (tr.start()));
                            event.setExtendedProp('taskBlock', vm.taskBlocks.getTaskDetails());
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
        caltimeSpanView: {start: KnockoutObservable<number>, end: KnockoutObservable<number>, range: KnockoutObservable<string>} = {start: ko.observable(null), end: ko.observable(null), range: ko.observable(null)};
		employeeId: string = '';
		setting: a.TaskFrameSettingDto[] = [];
		data: StartWorkInputPanelDto = null;
		constructor(taskBlocks: IManHrPerformanceTaskBlock, employeeId: string, private showInputTime: KnockoutObservable<boolean>) {
			super(taskBlocks);
			const vm = this;
			vm.employeeId = employeeId;
			vm.taskDetailsView = ko.observableArray(
				_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetailView(t, taskBlocks.caltimeSpan.start, employeeId, showInputTime, vm.data, []))
			);
            if(taskBlocks.caltimeSpan.start && taskBlocks.caltimeSpan.end){
                vm.caltimeSpanView.start(getTimeOfDate(taskBlocks.caltimeSpan.start));
				vm.caltimeSpanView.end(getTimeOfDate(taskBlocks.caltimeSpan.end));
            }
            vm.taskDetailsView.subscribe((tasks: ManHrTaskDetailView[])=>{
				let item = 0;
				_.forEach(tasks, (task: ITaskItemValue[])=>{
					_.forEach(task, ()=>{
						item++;
					});
				});
                setTimeout(() => {
//					flag(!flag());
				}, item);
				setTimeout(() => {
					resetHeight();
//					flag(!flag());
				}, 1);
            });
			vm.caltimeSpanView.start.subscribe(() => {
				vm.calTimeRange();
			});
			vm.caltimeSpanView.end.subscribe(() => {
				vm.calTimeRange();
			});
        }
		
		calTimeRange(): void{
			let vm = this;
			if(_.isNumber(vm.caltimeSpanView.start()) && _.isNumber(vm.caltimeSpanView.end())){
				vm.caltimeSpanView.range(getText('KDW013_25') + ' '+ number2String(vm.caltimeSpanView.end() - vm.caltimeSpanView.start()));
			}else{
				vm.caltimeSpanView.range('');
			}
		}
        
        update(taskBlocks: IManHrPerformanceTaskBlock, employeeId: string, data: StartWorkInputPanelDto, setting: a.TaskFrameSettingDto[]) {
			const vm = this;
			vm.setting = setting;
			vm.data = data;
			vm.employeeId = employeeId;
			vm.taskDetails(_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetail(t)));
			vm.taskDetailsView(
				_.map(taskBlocks.taskDetails, (t: IManHrTaskDetail) => new ManHrTaskDetailView(t, taskBlocks.caltimeSpan.start, vm.employeeId, vm.showInputTime, vm.data, setting))
			);
			vm.caltimeSpan = new TimeSpanForCalc(taskBlocks.caltimeSpan);
            if(taskBlocks.caltimeSpan.start && taskBlocks.caltimeSpan.end){
                vm.caltimeSpanView.start(_.isDate(taskBlocks.caltimeSpan.start) ? getTimeOfDate(taskBlocks.caltimeSpan.start):taskBlocks.caltimeSpan.start); 
				vm.caltimeSpanView.end(_.isDate(taskBlocks.caltimeSpan.end) ? getTimeOfDate(taskBlocks.caltimeSpan.end): taskBlocks.caltimeSpan.end);
            }
        }
        
        updateSetting(setting: a.TaskFrameSettingDto[]){
            const vm = this;
			vm.setting = setting;
            _.forEach(vm.taskDetailsView(), (t: ManHrTaskDetailView) => {
                t.setLableUses(setting);
            });
        }

		addTaskDetailsView(supNo: number):void {
			const vm = this;
			let taskItemValues: ITaskItemValue[] = [];
			_.forEach(vm.taskDetailsView()[0].taskItemValues(), (taskItemValue: TaskItemValue)=>{
				taskItemValues.push({ itemId: taskItemValue.itemId, value: '' });
			});
			let newTaskDetails: IManHrTaskDetail = { supNo: supNo, taskItemValues: taskItemValues }
			vm.taskDetailsView.push(new ManHrTaskDetailView(newTaskDetails, vm.caltimeSpan.start, vm.employeeId, vm.showInputTime, vm.data, vm.setting));
		}

		isChangedTime(): boolean{
			const vm = this;
			if(vm.caltimeSpanView.start() == getTimeOfDate(vm.caltimeSpan.start) && 
				vm.caltimeSpanView.end() == getTimeOfDate(vm.caltimeSpan.end))
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
            let titles: string[] = [];
            _.each(vm.taskDetailsView(), (task: ManHrTaskDetailView) => {
                titles.push(task.getTitles());
            });
			if(titles.length == 1){
				titles[0] = number2String(vm.caltimeSpanView.end() - vm.caltimeSpanView.start())+ '\n' + titles[0];
			}
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
				let taskItemValues = task.getTaskItemValue();
				let start = _.find(taskItemValues, i => i.itemId == 1);
				let end = _.find(taskItemValues, i => i.itemId == 2);
				let range = _.find(taskItemValues, i => i.itemId == 3);
				start.value = vm.caltimeSpanView.start() ;
				end.value = vm.caltimeSpanView.end();
				if(range.value == null){
					range.value = vm.caltimeSpanView.end() - vm.caltimeSpanView.start();
				}
                taskDetails.push({supNo: task.supNo, taskItemValues: taskItemValues});
            });
            return {
                caltimeSpan: { 
                    start: setTimeOfDate(vm.caltimeSpan.start, vm.caltimeSpanView.start()), 
                    end:  setTimeOfDate(vm.caltimeSpan.start, vm.caltimeSpanView.end())
                }, 
                taskDetails};
        }
        
	}
	
	export class ManHrTaskDetailView extends ManHrTaskDetail {
		employeeId: string;
        itemBeforChange: ITaskItemValue[];
		constructor(manHrTaskDetail: IManHrTaskDetail, private start: Date, employeeId: string, showInputTime: KnockoutObservable<boolean>, data: StartWorkInputPanelDto | null, setting: a.TaskFrameSettingDto[]) {
			super(manHrTaskDetail, data);
			const vm = this;
			vm.itemBeforChange = manHrTaskDetail.taskItemValues;
			vm.employeeId = employeeId;
			
            const [first, second, thirt, four, five] = setting;

			_.each(vm.taskItemValues(), (item: TaskItemValue) => {
                if(item.itemId == 3) {
                    item.use = showInputTime;
					item.lable(getText("KDW013_25"));
					item.value.subscribe(() => {
//	                    vm.flag(!vm.flag());
                	});
				}else if(item.itemId == 4) {
                    if (first) {
                        vm.setLableUse(item, first);
                    }else{
						item.use(false);
					}
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(5, 2, value);
                        }
						setTimeout(() => {
//                        	vm.flag(!vm.flag());
						}, 1);
                	});
				}else if(item.itemId == 5){
                    if (second) {
                        vm.setLableUse(item, second);
                    }else{
						item.use(false);
					}
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(6, 3, value);
	                    }
                	});
				}else if(item.itemId == 6){
                    if (thirt) {
                        vm.setLableUse(item, thirt);
                    }else{
						item.use(false);
					}
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(7, 4, value);
	                    }
                	});
				}else if(item.itemId == 7){
                    if (four) {
                        vm.setLableUse(item, four);
                    }else{
						item.use(false);
					}
					item.value.subscribe((value: string) => {
	                    if (value) {
                            vm.setWorkList(8, 5, value);
	                    }
                	});
				}
				else if(item.itemId == 8){
                    if (five) {
                        vm.setLableUse(item, five);
                    }else{
						item.use(false);
					}
				}else if(item.itemId >= 9 && data){
					let infor : ManHourRecordItemDto = _.find(data.manHourRecordItems, i => i.itemId == item.itemId);
					if(infor){
						item.lable(infor.name);
						item.use(infor.useAtr == 1);
						let attendanceItemLink = _.find(data.manHourRecordAndAttendanceItemLink, l => l.frameNo == vm.supNo && l.itemId == item.itemId);
						if(attendanceItemLink){
							let attendanceItem: DailyAttendanceItemDto = _.find(data.attendanceItems, a => a.attendanceItemId == attendanceItemLink.attendanceItemId);
							if(attendanceItem){
								item.primitiveValue = getPrimitiveValue(attendanceItem.primitiveValue);
							}
						}
					}
					if(item.itemId == 9 ){
						item.options(
							vm.convertWorkLocationList(
								_.map(data.workLocation, (i: a.WorkLocationDto) => {
									return {code: i.workLocationCD, name: i.workLocationName};
								}), 
								item.value
							));	
					}else if(item.itemId >= 25 && item.itemId <= 29){
						let taskSupInfoChoicesDetail : TaskSupInfoChoicesDetailDto[] = _.filter(data.taskSupInfoChoicesDetails, { 'itemId': item.itemId});
						if(taskSupInfoChoicesDetail && taskSupInfoChoicesDetail.length > 0){
							item.options(vm.convertWorkLocationList(
								_.map(taskSupInfoChoicesDetail, (i: TaskSupInfoChoicesDetailDto) => {
									return {code: i.code, name: i.name};
								}), 
								item.value
							));
						}
					}
				}
            });
			if(data){
				vm.setWorkLists(data);
			}
        }

		getTime(): number{
			let vm =this;
			let item = _.find(vm.taskItemValues(), (i: TaskItemValue) => i.itemId == 3);
			if(item){
				return parseInt(item.value());
			}else{
				return 0;
			}
		}
		
		convertWorkLocationList(option: {code: string, name: string}[], code: KnockoutObservable<string> | undefined): DropdownItem[]{
            const lst: DropdownItem[] = [{ id: '', code: '', name: getText('KDW013_40'), $raw: null, selected: false }];
            if (code && code()) {
                const taskSelected = _.find(option, { 'code': code() });
                if (!taskSelected) {
                    lst.push({ id: code(), code: code(), name: getText('KDW013_41'), selected: false, $raw: null });
                }
            }
            _.each(option, (t: {code: string, name: string}) => {
                lst.push({ id: t.code, code: t.code, name: t.name , selected: false, $raw: null });
            });
            return lst;
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
            _.each(vm.taskItemValues(), (item: TaskItemValue) => {
                result.push({itemId : item.itemId, value: item.value()});
            });
            return result;
        }

		setWorkList(nextItemId: number, taskFrameNoSetOption: number, value: string): void{
			const vm = this;
            const param: SelectWorkItemParam = {
                refDate: moment(vm.start).format(DATE_TIME_FORMAT),
                employeeId: vm.employeeId,
                taskCode: value,
                taskFrameNo: taskFrameNoSetOption
            };
			const itemNext = _.find(vm.taskItemValues(), (i) => {return i.itemId == nextItemId});
			if(itemNext){
				block.grayout();
	            return ajax('at', API.SELECT, param).done((data: TaskDto[]) => {
					itemNext.options(vm.getMapperList(data, itemNext.value));
					block.clear();
	            }).always(() => block.clear());
			}
        }
        
	    getTitles(): string{
            const vm = this;
            const title: string[] = [];
            _.each(vm.taskItemValues(), (item: TaskItemValue) => {
                if(item.itemId == 3 && item.value() && item.value() != '') {
                    title.push(number2String(parseInt(item.value())));
				}else if(item.itemId == 4 && item.value() != '') {
                    const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 5 && item.value() != ''){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 6 && item.value() != ''){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 7 && item.value() != ''){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}else if(item.itemId == 8 && item.value() != ''){
					const selected = _.find(ko.unwrap(item.options), ({ id }) => item.value() === id);
                    if (selected) {
                        title.push(selected.name);
                    }
				}            });
            
             
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
			if(item1 && (item1.value() == '' || item1.value() == null)){
				return true;
			}	
			return false;
		}
		setWorkLists(taskList: StartWorkInputPanelDto): void{
			const vm = this;
			const { taskFrameNo1, taskFrameNo2, taskFrameNo3, taskFrameNo4, taskFrameNo5 } = taskList;
			_.each(vm.taskItemValues(), (i: TaskItemValue) => {
        		if(i.itemId == 4){
					i.options(vm.getMapperList(taskFrameNo1, i.value));
				}else if(i.itemId == 5){
					i.options(vm.getMapperList(taskFrameNo2, i.value));
				}
				else if(i.itemId == 6){
					i.options(vm.getMapperList(taskFrameNo3, i.value));
				}
				else if(i.itemId == 7){
					i.options(vm.getMapperList(taskFrameNo4, i.value));
				}
				else if(i.itemId == 8){
					i.options(vm.getMapperList(taskFrameNo5, i.value));
				}
            });
		}
		getMapperList(tasks: TaskDto[], code: KnockoutObservable<string> | undefined): DropdownItem[]{
			const vm = this;
            const lst: DropdownItem[] = [vm.mapper(null)];
            if (code && code()) {
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
			let vm = this;
			if($raw == null){
				return { id: '', code: '', name: getText('KDW013_41'), $raw: null, selected: false };
			}
            return { id: $raw.code, code: $raw.code, name: vm.getName($raw.displayInfo) , selected: false, $raw: $raw };
        }
		getName(displayInfo: TaskDisplayInfoDto): string{
			if(displayInfo.taskNote && displayInfo.taskNote!= ''){
				return displayInfo.taskName + ' ' + displayInfo.taskNote;
			}
			return displayInfo.taskName;
		}
	}
	
	let primitiveValueDaily: any[] = __viewContext.enums.PrimitiveValueDaily;
	
	let getPrimitiveValue = (primitiveValue: number): string =>{
		if(primitiveValue){
			return _.find(primitiveValueDaily, (p: any) => p.value == primitiveValue).name.replace('Enum_PrimitiveValueDaily_','');	
		}
		return '';
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