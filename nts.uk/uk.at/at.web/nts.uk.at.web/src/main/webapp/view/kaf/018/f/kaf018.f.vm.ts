 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.f.viewmodel {
	import ApprSttExecutionDto = nts.uk.at.view.kaf018.b.viewmodel.ApprSttExecutionDto;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	import KAF018GParam = nts.uk.at.view.kaf018.g.viewmodel.KAF018GParam;
	
	@bean()
	class Kaf018FViewModel extends ko.ViewModel {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		apprSttExeDtoLst: Array<ApprSttExecutionDto> = [];
		currentApprSttExeDto: KnockoutObservable<ApprSttExecutionDto> = ko.observable(null);
		headers: Array<any> = [];
		columns: Array<any> = [];
		dataSource: Array<EmpConfirmInfo> = [];
		enableBack: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeDtoLst, vm.currentApprSttExeDto())
			if(index > 0) {
				return true;
			} else {
				return false;
			}	
		});
		enableNext: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeDtoLst, vm.currentApprSttExeDto());
			if(index < vm.apprSttExeDtoLst.length-1) {
				return true;
			} else {
				return false;
			}
		});
		legendWithTemplateOptions: any = {};
		
		created(params: KAF018FParam) {
			const vm = this;
			vm.legendWithTemplateOptions = {
				items: [
	                { colorCode: '#ff0000', labelText: vm.$i18n("KAF018_403") },
	                { colorCode: '#00AA00', labelText: vm.$i18n("KAF018_404") },
	                { colorCode: '#0000FF', labelText: vm.$i18n("KAF018_405") }
	            ],
	            template : '<div style="color: #{colorCode}; "> #{labelText} </div>'	
			}
			vm.$blockui('show');
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.apprSttExeDtoLst = params.apprSttExeDtoLst;
			vm.currentApprSttExeDto(_.find(params.apprSttExeDtoLst, o => o.wkpID == params.currentWkpID));
			vm.columns.push(
				{ 
					headerText: '', 
					key: 'empID', 
					dataType: 'string',
					width: 1,
					hidden: true
				},
				{ 
					headerText: vm.$i18n('KAF018_407'),
					key: 'empName',
					width: window.innerWidth - 1100 < 300 ? 300 : window.innerWidth - 1100,
					headerCssClass: 'kaf018-f-header-empName',
					formatter: (key: string, object: EmpConfirmInfo) =>  {
						return vm.getDispEmpName(object.empID);
					}
				},
				{
					headerText: vm.$i18n('KAF018_408'),
					key: 'sttUnConfirmDay',
					width: 40,
					headerCssClass: 'kaf018-f-header-stt',
					columnCssClass: 'kaf018-f-column-stt',
				},
				{ 
					headerText: vm.$i18n('KAF018_409'),
					key: 'sttUnApprDay',
					width: 40,
					headerCssClass: 'kaf018-f-header-stt',
					columnCssClass: 'kaf018-f-column-stt',
				},
				{ 
					headerText: vm.$i18n('KAF018_410'),
					key: 'sttUnConfirmMonth',
					width: 40,
					headerCssClass: 'kaf018-f-header-stt',
					columnCssClass: 'kaf018-f-column-stt',
				},
				{ 
					headerText: vm.$i18n('KAF018_411'),
					key: 'sttUnApprMonth',
					width: 40,
					headerCssClass: 'kaf018-f-header-stt',
					columnCssClass: 'kaf018-f-column-stt',
				}
			);
			let dateRangeNumber = moment(vm.endDate,'YYYY/MM/DD').diff(moment(vm.startDate,'YYYY/MM/DD'), 'days'),
				dateColumnLst = [];
			for(let i = 0; i <= dateRangeNumber; i++) {
				if(i < dateRangeNumber) {
					dateColumnLst.push(
						{ 
							headerText: moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')).date(),
							headerCssClass: vm.getHeaderCss(i),
							group: [
								{ 
									headerText: moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')).format('ddd'),
									key: 'dateInfoLst',
									width: '30px',
									headerCssClass: vm.getHeaderCss(i),
									columnCssClass: 'kaf018-f-column-date',
									formatter: (value: any) => vm.getStatusByDay(value, i)
								}
							]
						}
					);	
				} else {
					dateColumnLst.push(
						{ 
							headerText: moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')).date(),
							headerCssClass: vm.getHeaderCss(i),
							group: [
								{ 
									headerText: moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')).format('ddd'),
									key: 'dateInfoLst',
									width: '47px',
									headerCssClass: vm.getHeaderCss(i),
									columnCssClass: 'kaf018-f-column-date',
									formatter: (value: any) => vm.getStatusByDay(value, i)
								}
							]
						}
					);
				}
				
			}
			vm.columns.push(
				{ 
					headerText: '',
					group: dateColumnLst
				}
			);
			let empID = '', empCD = '', empName = '', sttUnConfirmDay = 0, sttUnApprDay = 0, sttUnConfirmMonth = 0, sttUnApprMonth = 0, dateInfoLst: Array<DateInfo> = [];
			for(let j = 0; j <= dateRangeNumber; j++) {
				dateInfoLst.push({
					date: moment(moment(vm.startDate,'YYYY/MM/DD').add(j, 'd')).format('YYYY/MM/DD'),
					status: ''
				});
			}
			vm.dataSource.push({ empID, empCD, empName, sttUnConfirmDay, sttUnApprDay, sttUnConfirmMonth, sttUnApprMonth, dateInfoLst });
			$("fGrid").css('visibility','hidden');
			vm.createIggrid();
			vm.refreshDataSource();
		}
		
		getDispEmpName(value: string) {
			const vm = this;
			let	empInfo: EmpConfirmInfo = _.find(vm.dataSource, o => o.empID==value);
			return '<span class="kaf018-f-column-empName">' + empInfo.empCD + '</span>' + '　　　　' + '<span class="kaf018-f-column-empName">' + empInfo.empName + '</span>';
		}
		
		getHeaderCss(value: any) {
			const vm = this;
			let dayOfWeek: number = parseInt(moment(moment(vm.startDate,'YYYY/MM/DD').add(value, 'd')).format('e'));
			if(dayOfWeek==6) {
				return 'kaf018-f-header-saturday';
			}
			if(dayOfWeek==0) {
				return 'kaf018-f-header-sunday';
			}
			return 'kaf018-f-header-date';
		}
		
		getStatusByDay(value: Array<DateInfo>, i: number) {
			const vm = this;
			let key = moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')).format('YYYY/MM/DD'),
				itemValue = _.find(value, o => o.date == key);
			if(itemValue) {
				return itemValue.status;
			}
			return '';
		}
		
		createIggrid() {
			const vm = this;
			$("#fGrid").igGrid({
				height: 552,
				width: window.innerWidth - 40,
				dataSource: vm.dataSource,
				primaryKey: 'empID',
				primaryKeyDataType: 'string',
				virtualization: true,
				virtualizationMode: 'continuous',
				dataRendered: () => {
					vm.$nextTick(() => {
						vm.$blockui('hide');
					});
				},
				rendered: () => {
					let dynamicWidth = $('#fGrid_table_headers_v').width();
					$('#kaf018-f-dynamic-header').css('margin-right', (dynamicWidth/2 - 50) < 0 ? 0 : (dynamicWidth/2 - 50) + 'px');
			   		if($("#fGrid").css('visibility')=='hidden'){
						vm.$nextTick(() => {
							vm.$blockui('show');
							$('#kaf018-f-cancel-btn').focus();
						});
					} else {
						vm.$nextTick(() => {
							vm.$blockui('hide');
						});
					}
			    },
				cellClick: (evt: any, ui: any) => {
					vm.cellGridClick(evt, ui); 
				},
				columns: vm.columns,
				features: [
					{
						name: 'MultiColumnHeaders'
					},
					{
						name: 'ColumnFixing', 
						fixingDirection: 'left',
						showFixButtons: false,
						columnSettings: [
							{ columnKey: 'empName', isFixed: true },
							{ columnKey: 'sttUnConfirmDay', isFixed: true },
							{ columnKey: 'sttUnApprDay', isFixed: true },
							{ columnKey: 'sttUnConfirmMonth', isFixed: true },
							{ columnKey: 'sttUnApprMonth', isFixed: true }
						]
					}
				],
			});
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="empName") {
				let empInfoLst = vm.dataSource,
					startDate = vm.startDate,
					endDate = vm.endDate,
					currentEmpID = ui.rowKey,
					gParam: KAF018GParam = { empInfoLst, startDate, endDate, currentEmpID };
				vm.$window.modal('/view/kaf/018/g/index.xhtml', gParam);
			}
		}
		
		close() {
			const vm = this;
			vm.$window.close({});
		}
		
		back() {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeDtoLst, vm.currentApprSttExeDto());
			if (index > 0) {
				vm.currentApprSttExeDto(vm.apprSttExeDtoLst[index - 1]);
				vm.refreshDataSource();
			}
		}

		next() {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeDtoLst, vm.currentApprSttExeDto());
			if (index < (vm.apprSttExeDtoLst.length-1)) {
				vm.currentApprSttExeDto(vm.apprSttExeDtoLst[index + 1]);
				vm.refreshDataSource();
			}
		}
		
		refreshDataSource() {
			const vm = this;
			let wkpID = vm.currentApprSttExeDto().wkpID,
				startDate = vm.startDate,
				endDate = vm.endDate,
				empPeriodLst = vm.currentApprSttExeDto().empPeriodLst,
				wsParam = { wkpID, startDate, endDate, empPeriodLst };
			vm.$blockui('show');
			vm.$ajax(API.getApprSttStartByEmp, wsParam).done((data) => {
				vm.dataSource = data;
				$("#fGrid").igGrid("option", "dataSource", vm.dataSource);
				$("#fGrid").css('visibility','visible');
			});
		}
	}
	
	export interface KAF018FParam {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		apprSttExeDtoLst: Array<ApprSttExecutionDto>;
		currentWkpID: string;
	}
	
	export interface EmpConfirmInfo {
		empID: string;
		empCD: string;
		empName: string;
		sttUnConfirmDay: string;
		sttUnApprDay: string;
		sttUnConfirmMonth: string;
		sttUnApprMonth: string;
		dateInfoLst: Array<DateInfo>;
	}
	
	interface DateInfo {
		date: string;
		status: string;
	}

	const API = {
		getApprSttStartByEmp: "at/request/application/approvalstatus/getApprSttStartByEmp",
	}
}