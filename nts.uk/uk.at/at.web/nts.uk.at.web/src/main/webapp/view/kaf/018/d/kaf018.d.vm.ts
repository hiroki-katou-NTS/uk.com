 /// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kaf018.d.viewmodel {
	import ApprSttExecutionOutput = nts.uk.at.view.kaf018.b.viewmodel.ApprSttExecutionOutput;
	import ClosureItem = nts.uk.at.view.kaf018.a.viewmodel.ClosureItem;
	
	@bean()
	class Kaf018DViewModel extends ko.ViewModel {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		apprSttExeOutputLst: Array<ApprSttExecutionOutput> = [];
		currentApprSttExeOutput: KnockoutObservable<ApprSttExecutionOutput> = ko.observable(null);
		headers: Array<any> = [];
		columns: Array<any> = [];
		dataSource: Array<EmpInfo> = [];
		enableBack: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeOutputLst, vm.currentApprSttExeOutput())
			if(index > 0) {
				return true;
			} else {
				return false;
			}	
		});
		enableNext: KnockoutObservable<boolean> = ko.pureComputed(() => {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeOutputLst, vm.currentApprSttExeOutput());
			if(index < vm.apprSttExeOutputLst.length-1) {
				return true;
			} else {
				return false;
			}
		});
		
		created(params: KAF018DParam) {
			const vm = this;
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.apprSttExeOutputLst = params.apprSttExeOutputLst;
			vm.currentApprSttExeOutput(_.head(params.apprSttExeOutputLst));
			vm.dataSource = vm.getDataSource();
			vm.columns.push(
				{ 
					headerText: '', 
					key: 'empID', 
					dataType: 'string',
					hidden: true
				},
				{ 
					headerText: vm.$i18n('KAF018_373'),
					key: 'empName',
					headerCssClass: 'kaf018-d-header-empName',
					columnCssClass: 'kaf018-d-column-empName'
				}
			);
			let dateRangeNumber = moment(vm.endDate).diff(vm.startDate, 'days');
			for(let i = 0; i <= dateRangeNumber; i++) {
				vm.columns.push(
					{ 
						headerText: moment(moment(vm.startDate).add(i, 'd')).date(),
						headerCssClass: 'kaf018-d-header-date',
						group: [
							{ 
								headerText: moment(moment(vm.startDate).add(i, 'd')).format('ddd'),
								key: 'dateInfoLst',
								width: '28px',
								headerCssClass: 'kaf018-d-header-date',
								columnCssClass: 'kaf018-d-column-date',
								formatter: (value: any) => vm.getStatusByDay(value, i)
							}
						]
					}
				);
			}
			vm.createMGrid();
			$("#dpGrid").igGrid("option", "dataSource", vm.dataSource);
		}
		
		getStatusByDay(value: Array<DateInfo>, i: number) {
			const vm = this;
			let key = moment(moment(vm.startDate).add(i, 'd')).format('YYYY/MM/DD');
			return _.find(value, o => o.date == key).status;
		}
		
		createMGrid() {
			const vm = this;
			$("#dpGrid").igGrid({
				height: 508,
				width: screen.availWidth - 70,
				dataSource: vm.dataSource,
				primaryKey: 'empID',
				primaryKeyDataType: 'string',
				virtualization: true,
				virtualizationMode: 'continuous',
				cellClick: (evt: any, ui: any) => {
					vm.cellGridClick(evt, ui); 
				},
				columns: vm.columns,
				features: [
					{
						name: 'MultiColumnHeaders'
					}
				],
			});
		}
		
		formatSubHeader(value: any) {
			const vm = this;
			return value+1;
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="empName") {
				vm.$window.modal('/view/kaf/018/e/index.xhtml');
			}
		}
		
		close() {
			const vm = this;
			vm.$window.close({});
		}
		
		back() {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeOutputLst, vm.currentApprSttExeOutput());
			if (index > 0) {
				vm.currentApprSttExeOutput(vm.apprSttExeOutputLst[index - 1]);
				vm.dataSource = vm.getDataSource();
				$("#dpGrid").igGrid("option", "dataSource", vm.dataSource);
			}
		}

		next() {
			const vm = this;
			let index = _.indexOf(vm.apprSttExeOutputLst, vm.currentApprSttExeOutput());
			if (index < (vm.apprSttExeOutputLst.length-1)) {
				vm.currentApprSttExeOutput(vm.apprSttExeOutputLst[index + 1]);
				vm.dataSource = vm.getDataSource();
				$("#dpGrid").igGrid("option", "dataSource", vm.dataSource);
			}
		}
		
		getDataSource(): Array<EmpInfo> {
			const vm = this;
			let newDataSource: Array<EmpInfo> = [];
			for(let i = 1; i <= 20; i++) {
				let empID = 'empID' + i,
					empName = vm.currentApprSttExeOutput().wkpName + 'empName' + i,
					dateInfoLst: Array<DateInfo> = [],
					dateRangeNumber = moment(vm.endDate).diff(vm.startDate, 'days');
				for(let j = 0; j <= dateRangeNumber; j++) {
					dateInfoLst.push({
						date: moment(moment(vm.startDate).add(j, 'd')).format('YYYY/MM/DD'),
						status: (empName.length + i + j).toString()
					});
				}
				newDataSource.push({ empID, empName, dateInfoLst });
			}
			return newDataSource;
		}
	}
	
	export interface KAF018DParam {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		apprSttExeOutputLst: Array<ApprSttExecutionOutput>;
	}
	
	interface EmpInfo {
		empID: string;
		empName: string;
		dateInfoLst: Array<DateInfo>;
	}
	
	interface DateInfo {
		date: string;
		status: string;
	}

	const API = {
	
	}
}