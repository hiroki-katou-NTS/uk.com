module nts.uk.ui.at.kdw013.b {
	import getText = nts.uk.resource.getText;
	import ajax = nts.uk.request.ajax;
	import block = nts.uk.ui.block;
	import error = nts.uk.ui.dialog.error;
	const API: API = {
        START: '/screen/at/kdw013/common/start',
        SELECT: '/screen/at/kdw013/c/select'
    };

    const COMPONENT_NAME = 'kdp013b';
    const { getTimeOfDate, number2String } = share;

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

	let template = `
        <div class="detail-event">
            <div class="header">
                <div data-bind="i18n: 'KDW013_26'"></div>
                <div class="actions">
                    <button id='edit' data-bind="click: $component.params.update, icon: 204, size: 12"></button>
                    <button data-bind="click: $component.remove, icon: 203, size: 12"></button>
					<button data-bind="click: $component.openF, icon: 229, size: 12"></button>
                    <button data-bind="click: $component.params.close, icon: 202, size: 12"></button>
                </div>
            </div>
			<table class="timePeriod">
                <colgroup>
                    <col width="90px" />
                </colgroup>
                <tbody>
                    <tr>
                        <td data-bind="i18n: 'KDW013_27'"></td>
                        <td data-bind="text: time"></td>
                    </tr>
				</tbody>
			</table>
			<div class="taskDetails" data-bind="foreach: dataSources">
	            <table>
	                <colgroup>
	                    <col width="90px" />
	                </colgroup>
	                <tbody data-bind="foreach: items }">
	                    <tr>
	                        <td ><div data-bind="i18n: key"> </div></td>
	                        <td ><div data-bind="text: value"> </div></td>
	                    </tr>
	                </tbody>
	            </table>
			</div>
        </div>
        <style>
            .detail-event {
                width: 320px;
            }
            .detail-event .header {
                box-sizing: border-box;
                position: relative;
                padding-bottom: 5px;
                line-height: 35px;
                margin-top: -5px;
            }
            .detail-event .header .actions {
                position: absolute;
                top: 0px;
                right: -5px;
            }
            .detail-event .header .actions button {
                margin: 0;
                padding: 0;
                box-shadow: none;
                border: none;
                border-radius: 50%;
                width: 30px;
            }
            .detail-event .header .actions button:focus {
                background-color:#f7f7f7;
                margin: 0;
                padding: 0;
                box-shadow: none;
                border: none;
                border-radius: 50%;
                width: 30px;
            }
            .detail-event table {
                width: 100%;
            }
            .detail-event table tr {
                height: 34px;
            }
            .detail-event table tr>td:first-child {
                vertical-align: top;
                padding-top: 6px;
            }
            .detail-event table tr>td>div {
                max-height: 120px;
                overflow-y: auto;
                word-break: break-all;
            }
        </style>
        `;

    @component({
        name: COMPONENT_NAME,
        template: template
    })
    export class ViewModel extends ko.ViewModel {
        dataSources: KnockoutObservableArray<TaskDetailB> = ko.observableArray([]);
        taskFrameSettings!: KnockoutComputed<a.TaskFrameSettingDto[]>;
		time: KnockoutObservable<string> = ko.observable('');

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
                    return [];
                }
            });
        }

        mounted() {
            const vm = this;
            const { params } = vm;
            const { data } = params;

            ko.computed({
                read: () => {
                    const taskDetails: TaskDetailB[] = [];
                    const event = ko.unwrap(data);

                    if (event) {
                        const { extendedProps, start, end } = event as any as calendar.EventRaw;
						const startTime = getTimeOfDate(start);
                        const endTime = getTimeOfDate(end);
						vm.time(`${number2String(startTime)}${vm.$i18n('KDW013_30')}${number2String(endTime)}`);

                        let {taskBlocks} = extendedProps;
                    	//taskBlocks = vm.fakeData(start, end),
						let param ={
							refDate: start,
							itemIds: [9, 10, 11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29]
						}
						block.grayout();
			            ajax('at', API.START, param).done((data: StartWorkInputPanelDto) => {
							_.forEach(taskBlocks.taskDetails, taskDetail =>{
								taskDetails.push(vm.setlableValueItems(taskDetail,data));
							});
							vm.dataSources(taskDetails);
							setTimeout(() => {
								vm.updatePopupSize();
							}, 150);
						}).always(() => block.clear());
                    } else {
                        vm.dataSources(taskDetails);
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            });
        }
		// update popup size
        updatePopupSize(){
			const vm = this;
            vm.params.position.valueHasMutated();
        }
    
		setlableValueItems(taskDetail: IManHrTaskDetail, data: StartWorkInputPanelDto): TaskDetailB {
			let vm = this;
			let items: KeyValue[] = [];

			let range = _.find(taskDetail.taskItemValues, i => i.itemId == 3).value;
			items.push({ key: 'KDW013_25', value: number2String(parseInt(range)) });			
			
			const [first, second, thirt, four, five] = vm.taskFrameSettings();
			
			if (first && first.useAtr === 1) {
				let value = _.find(taskDetail.taskItemValues, i => i.itemId == 4).value;
                items.push(vm.setTaskData(first, value, data.taskListDto1));
            }
            if (second && second.useAtr === 1) {
                let value = _.find(taskDetail.taskItemValues, i => i.itemId == 5).value;
                items.push(vm.setTaskData(second, value, data.taskListDto2));
            }
            if (thirt && thirt.useAtr === 1) {
                let value = _.find(taskDetail.taskItemValues, i => i.itemId == 6).value;
                items.push(vm.setTaskData(thirt, value, data.taskListDto3));
            }
            if (four && four.useAtr === 1) {
                let value = _.find(taskDetail.taskItemValues, i => i.itemId == 7).value;
                items.push(vm.setTaskData(four, value, data.taskListDto4));
            }
            if (five && five.useAtr === 1) {
                let value = _.find(taskDetail.taskItemValues, i => i.itemId == 8).value;
                items.push(vm.setTaskData(five, value, data.taskListDto5));
            }
			//loai bo item co dinh
			taskDetail.taskItemValues = _.filter(taskDetail.taskItemValues, (i: ITaskItemValue) => i.itemId > 9);
			
			// cho vao day de sap xep
			let manHrTaskDetail = new ManHrTaskDetail(taskDetail, data);
			
			_.forEach(manHrTaskDetail.taskItemValues(), (item: TaskItemValue) => {
				
				let infor : ManHourRecordItemDto = _.find(data.manHourRecordItems, i => i.itemId == item.itemId);
				if(infor && infor.useAtr == 1){
					if(item.itemId == 9){
						// work plate
						let workLocation = _.find(data.workLocation, w => w.workLocationCD == item.value());
						if(workLocation){
							items.push({key: infor.name, value: item.value() + ' ' + workLocation.workLocationName});	
						}else{
							items.push({key: infor.name, value: item.value() + ' ' + getText('KDW013_40')});
						}
					}else if(item.itemId >= 25 && item.itemId >= 29){
						let taskSupInfoChoicesDetail : TaskSupInfoChoicesDetailDto[] = _.filter(data.taskSupInfoChoicesDetails, i => i.itemId == item.itemId);
						if(taskSupInfoChoicesDetail && taskSupInfoChoicesDetail.length > 0){
							let selected = _.find(taskSupInfoChoicesDetail, w => w.code == item.value());
							if(selected){
								items.push({key: infor.name, value:  item.value() + ' ' +  selected.name});	
							}else{
								items.push({key: infor.name, value:  item.value() + ' ' + getText('KDW013_40')});
							}
						}
					}else{
						items.push({key: infor.name, value:  item.value() });	
					}
				}
			});
			
			return {items : items};
		}

        setTaskData(setting: a.TaskFrameSettingDto, value: string, option: TaskDto[]):KeyValue{
			let item: KeyValue = {key: setting.frameName, value: ''};
			if(value){
                const exist = _.find(option, o => o.code === value);
                if (exist) {
					item.value = value + ' ' + exist.displayInfo.taskName;                    
                }else{
					item.value = value + ' ' + getText('KDW013_40');
				}
                
            }
			return item;
        }

		openF() {
            const vm = this;
           
        }

        remove() {
            const vm = this;
            $.Deferred()
                .resolve()
                .then(() => {
                    vm.params.remove();
                });
        }
    }
	
	type TaskDetailB = {
        items: KeyValue[];
    }

    type KeyValue = {
        key: string;
        value: string;
    }

    type Params = {
        close: () => void;
        update: () => void;
        remove: () => void;
        mode: KnockoutObservable<boolean>;
        data: KnockoutObservable<FullCalendar.EventApi>;
		position: KnockoutObservable<null | any>;
        $settings: KnockoutObservable<a.StartProcessDto | null>;
        $share: KnockoutObservable<nts.uk.ui.at.kdw013.StartWorkInputPanelDto | null>;
    }
}