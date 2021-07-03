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
		apprSttComfirmSet: any;
		apprSttExeDtoLst: Array<ApprSttExecutionDto> = [];
		currentApprSttExeDto: KnockoutObservable<ApprSttExecutionDto> = ko.observable(null);
		headers: Array<any> = [];
		columns: Array<any> = [];
		features: Array<any> = [];
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
	                { color: '', background: '#BFEA60', icon: vm.$i18n('KAF018_560'), text: vm.$i18n('KAF018_403') },
	                { color: '#FF2D2D', background: '', icon: vm.$i18n('KAF018_561'), text: vm.$i18n('KAF018_404') },
	                { color: '', background: '', icon: vm.$i18n('KAF018_562'), text: vm.$i18n('KAF018_405') },
					{ color: '#a9a9a9', background: '#a9a9a9', icon: '未', text: vm.$i18n('KAF018_406') },
	            ],
	            template : `<div>
								<div style="color: #{color}; background-color: #{background}; border: 1px solid #a9a9a9; display: inline-block;">#{icon}</div>
								<div style="display: inline-block;">#{text}</div>
							</div>`	
			}
			vm.$blockui('show');
			vm.closureItem = params.closureItem;
			vm.startDate = params.startDate;
			vm.endDate = params.endDate;
			vm.apprSttComfirmSet = params.apprSttComfirmSet;
			vm.apprSttExeDtoLst = params.apprSttExeDtoLst;
			vm.currentApprSttExeDto(_.find(params.apprSttExeDtoLst, o => o.wkpID == params.currentWkpID));
			let empNameColumnWidth = 500;
			vm.columns.push(
				{ 
					headerText: '', 
					key: 'empID', 
					dataType: 'string',
					width: 1,
					hidden: true
				}
			);
			if(!vm.apprSttComfirmSet.usePersonConfirm && !vm.apprSttComfirmSet.useBossConfirm) {
				vm.columns.push(
					{ 
						headerText: vm.$i18n('KAF018_407'),
						key: 'empName',
						headerCssClass: 'kaf018-f-header-empName',
						formatter: (key: string, object: EmpConfirmInfo) =>  {
							return vm.getDispEmpName(object.empID);
						}
					}
				);
			} else {
				vm.columns.push(
					{ 
						headerText: vm.$i18n('KAF018_407'),
						key: 'empName',
						width: empNameColumnWidth,
						headerCssClass: 'kaf018-f-header-empName',
						formatter: (key: string, object: EmpConfirmInfo) =>  {
							return vm.getDispEmpName(object.empID);
						}
					}
				);
				vm.features =  
				[
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
				];
			}
			
			if(vm.apprSttComfirmSet.monthlyIdentityConfirm) {
				vm.columns.push(
					{
						headerText: vm.$i18n('KAF018_408'),
						key: 'sttUnConfirmMonth',
						width: 45,
						headerCssClass: 'kaf018-f-header-stt',
						columnCssClass: 'kaf018-f-column-stt',
					}
				);	
			}
			if(vm.apprSttComfirmSet.monthlyConfirm) {
				vm.columns.push(
					{ 
						headerText: vm.$i18n('KAF018_409'),
						key: 'sttUnApprMonth',
						width: 45,
						headerCssClass: 'kaf018-f-header-stt',
						columnCssClass: 'kaf018-f-column-stt',
					}
				);	
			}
			if(vm.apprSttComfirmSet.usePersonConfirm) {
				vm.columns.push(
					{ 
						headerText: vm.$i18n('KAF018_410'),
						key: 'sttUnConfirmDay',
						width: 45,
						headerCssClass: 'kaf018-f-header-stt',
						columnCssClass: 'kaf018-f-column-stt',
					}
				);	
			}
			if(vm.apprSttComfirmSet.useBossConfirm) {
				vm.columns.push(
					{ 
						headerText: vm.$i18n('KAF018_411'),
						key: 'sttUnApprDay',
						width: 45,
						headerCssClass: 'kaf018-f-header-stt',
						columnCssClass: 'kaf018-f-column-stt',
					}
				);	
			}
			if(!(!vm.apprSttComfirmSet.usePersonConfirm && !vm.apprSttComfirmSet.useBossConfirm)) {
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
										width: '40px',
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
										width: '57px',
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
			}
			vm.dataSource.push(new EmpConfirmInfo(null, vm));
			$("#fGrid").css('visibility','hidden');
			$('#kaf018-f-dynamic-header').css('visibility','hidden');
			vm.createIggrid();
			vm.refreshDataSource();
			$(window).resize(() => {
				let topRange = document.getElementById('fGrid').getBoundingClientRect().top,
					bottomRange = document.getElementById('functions-area-bottom').getBoundingClientRect().height,
					height = window.innerHeight - topRange - bottomRange - 10;
				$("#fGrid").igGrid("option", "height", height + "px");
				$("#fGrid").igGrid("option", "width", window.innerWidth - 40 + "px");
				let dynamicWidth = $('#fGrid_table_headers_v').width();
				$('#kaf018-f-dynamic-header').css('margin-right', (dynamicWidth/2 - 50) < 0 ? 0 : (dynamicWidth/2 - 50) + 'px');
			});
		}
		
		mounted() {
			let topRange = document.getElementById('fGrid').getBoundingClientRect().top,
				bottomRange = document.getElementById('functions-area-bottom').getBoundingClientRect().height,
				height = window.innerHeight - topRange - bottomRange - 10;
			$("#fGrid").igGrid("option", "height", height + "px");
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
				switch(itemValue.status) {
					//実績確認済      
			        case CONFIRMSTATUS.CONFIRMED: 
						return vm.$i18n('KAF018_560');
			        //実績上司未確認
			        case CONFIRMSTATUS.BOSS_UNCONFIRMED: 
						return vm.$i18n('KAF018_561');
			        //本人未確認
			        case CONFIRMSTATUS.SELF_UNCONFIRMED: 
						return vm.$i18n('KAF018_562');
			        //実績対象外
			        case CONFIRMSTATUS.NO_TARGET: 
						return '';
					default:
						return '';
				}
			}
			return '';
		}
		
		updateCellStyles() {
			const vm = this;
			_.forEach(vm.dataSource, (item: EmpConfirmInfo) => {
				if(!vm.apprSttComfirmSet.usePersonConfirm && !vm.apprSttComfirmSet.useBossConfirm) {
					return;
				}
				for(let i=0; i<item.dateInfoLst.length; i++) {
					let dateInfoItem = item.dateInfoLst[i];
					$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.remove('kaf018-f-stt-confirmed');
					$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.remove('kaf018-f-stt-boss-unconfirmed');
					$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.remove('kaf018-f-stt-no-target');
					switch(dateInfoItem.status) {
						//実績確認済      
				        case CONFIRMSTATUS.CONFIRMED:
							
							$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.add('kaf018-f-stt-confirmed');
							break;
				        //実績上司未確認
				        case CONFIRMSTATUS.BOSS_UNCONFIRMED:
							$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.add('kaf018-f-stt-boss-unconfirmed');
							break;
				        //本人未確認
				        case CONFIRMSTATUS.SELF_UNCONFIRMED:
							break;
				        //実績対象外
				        case CONFIRMSTATUS.NO_TARGET:
							$('#fGrid').igGrid("cellById", item.empID, "dateInfoLst").get(i).classList.add('kaf018-f-stt-no-target');
							break;
						default:
							return '';
					}	
				}
			});
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
					vm.updateCellStyles();
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
				features: vm.features,
			});
		}
		
		cellGridClick(evt: any, ui: any) {
			const vm = this;
			if(ui.colKey=="empName") {
				let empInfoLst = vm.dataSource,
					closureItem = vm.closureItem,
					startDate = vm.startDate,
					endDate = vm.endDate,
					currentWkpID = vm.currentApprSttExeDto().wkpID,
					apprSttComfirmSet = vm.apprSttComfirmSet,
					currentEmpID = ui.rowKey,
					gParam: KAF018GParam = { empInfoLst, closureItem, startDate, endDate, currentWkpID, apprSttComfirmSet, currentEmpID };
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
				apprSttComfirmSet = vm.apprSttComfirmSet,
				yearMonth = vm.closureItem.processingYm,
				closureId = vm.closureItem.closureId,
				closureDay =  vm.closureItem.closureDay,
				lastDayOfMonth = vm.closureItem.lastDayOfMonth,
				wsParam = { wkpID, startDate, endDate, empPeriodLst, apprSttComfirmSet, yearMonth, closureId, closureDay, lastDayOfMonth };
			vm.$blockui('show');
			vm.$ajax(API.getConfirmSttByEmp, wsParam).done((data: Array<ApprSttConfirmEmp>) => {
				let a: Array<EmpConfirmInfo> = [];
				_.forEach(empPeriodLst, item => {
					let apprSttConfirmEmp = _.find(data, o => {
						return o.empID==item.empID;
					});
					if(apprSttConfirmEmp) {
						a.push(new EmpConfirmInfo(apprSttConfirmEmp, vm));	
					}
				});
				vm.dataSource = _.sortBy(a, 'empCD');
				$("#fGrid").igGrid("option", "dataSource", vm.dataSource);
				$("#fGrid").css('visibility','visible');
				if(!(!vm.apprSttComfirmSet.usePersonConfirm && !vm.apprSttComfirmSet.useBossConfirm)) {
					$('#kaf018-f-dynamic-header').css('visibility','visible');	
				}
			});
		}
	}
	
	export interface KAF018FParam {
		closureItem: ClosureItem;
		startDate: string;
		endDate: string;
		apprSttExeDtoLst: Array<ApprSttExecutionDto>;
		currentWkpID: string;
		apprSttComfirmSet: any;
	}
	
	interface ApprSttConfirmEmp {
		listDailyConfirm: Array<DailyConfirmOutput>;
		empCD: string;
		empName: string;
		monthConfirm: boolean;
		monthApproval: number;
		empID: string;
	}
	
	interface DailyConfirmOutput {
		/**
		 * 職場ID
		 */
		wkpID: string;
		/**
		 * 社員ID
		 */
		empID: string;
		/**
		 * 対象日
		 */
		targetDate: string;
		/**
		 * 本人確認
		 */
		personConfirm: boolean;
		/**
		 * 上司確認
		 */
		bossConfirm: number;
	}
	
	export class EmpConfirmInfo {
		empID: string;
		empCD: string;
		empName: string;
		sttUnConfirmDay: string;
		sttUnApprDay: string;
		sttUnConfirmMonth: string;
		sttUnApprMonth: string;
		dateInfoLst: Array<DateInfo>;
		constructor(apprSttConfirmEmp: ApprSttConfirmEmp, vm: any) {
			if(apprSttConfirmEmp) {
				this.empID = apprSttConfirmEmp.empID;
				this.empCD = apprSttConfirmEmp.empCD;
				this.empName = apprSttConfirmEmp.empName;
				this.sttUnConfirmDay = _.chain(apprSttConfirmEmp.listDailyConfirm).filter(o => !o.personConfirm).isEmpty().value() ? vm.$i18n('KAF018_530') : "";
				this.sttUnApprDay = _.chain(apprSttConfirmEmp.listDailyConfirm).filter(o => o.bossConfirm!=DailyConfirmAtr.ALREADY_APPROVED).isEmpty().value() ? vm.$i18n('KAF018_530') : "";
				this.sttUnConfirmMonth = apprSttConfirmEmp.monthConfirm ? vm.$i18n('KAF018_530') : "";
				this.sttUnApprMonth = apprSttConfirmEmp.monthApproval==DailyConfirmAtr.ALREADY_APPROVED ? vm.$i18n('KAF018_530') : "";
				let a: Array<DateInfo> = [],
					apprSttComfirmSet = vm.apprSttComfirmSet,
					dateRangeNumber = moment(vm.endDate,'YYYY/MM/DD').diff(moment(vm.startDate,'YYYY/MM/DD'), 'days');
				for(let i = 0; i <= dateRangeNumber; i++) {
					let loopDate = moment(moment(vm.startDate,'YYYY/MM/DD').add(i, 'd')),
						item = _.find(apprSttConfirmEmp.listDailyConfirm, o => moment(o.targetDate,'YYYY/MM/DD').isSame(loopDate));	
					if(item) {
						if(apprSttComfirmSet.usePersonConfirm && apprSttComfirmSet.useBossConfirm) {
							if(_.isNull(item.bossConfirm) && _.isNull(item.personConfirm)) {
								a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.NO_TARGET));	
							} else {
								if(item.bossConfirm==DailyConfirmAtr.ALREADY_APPROVED) {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.CONFIRMED));	
								} else if(item.personConfirm) {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.BOSS_UNCONFIRMED));	
								} else {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.SELF_UNCONFIRMED));	
								}		
							}
							
							
						} else if(apprSttComfirmSet.usePersonConfirm && !apprSttComfirmSet.useBossConfirm) {
							if(_.isNull(item.personConfirm)) {
								a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.NO_TARGET));	
							} else {
								if(item.personConfirm) {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.CONFIRMED));		
								} else {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.SELF_UNCONFIRMED));		
								}		
							}
						} else if(!apprSttComfirmSet.usePersonConfirm && apprSttComfirmSet.useBossConfirm) {
							if(_.isNull(item.bossConfirm)) {
								a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.NO_TARGET));	
							} else {
								if(item.bossConfirm==DailyConfirmAtr.ALREADY_APPROVED) {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.CONFIRMED));	
								} else {
									a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.BOSS_UNCONFIRMED));	
								}	
							}
						} else {
							a.push(new DateInfo(item.targetDate, CONFIRMSTATUS.NO_TARGET));	
						}	
					} else {
						a.push(new DateInfo(loopDate.format('YYYY/MM/DD'), CONFIRMSTATUS.NO_TARGET));
					}
				}
				this.dateInfoLst = a;
			} else {
				this.empID = "";
				this.empCD = "";
				this.empName = "";
				this.sttUnConfirmDay = "";
				this.sttUnApprDay = "";
				this.sttUnConfirmMonth = "";
				this.sttUnApprMonth = "";
				this.dateInfoLst = [];
			}
		}
	}
	
	class DateInfo {
		date: string;
		status: CONFIRMSTATUS;
		constructor(date: string, status: CONFIRMSTATUS) {
			this.date = date;
			this.status = status;
		}
	}
	
	enum CONFIRMSTATUS {
        //実績確認済      
        CONFIRMED = 0,
        //実績上司未確認
        BOSS_UNCONFIRMED = 1,
        //本人未確認
        SELF_UNCONFIRMED = 2,
        //実績対象外
        NO_TARGET = 3
    }

	enum DailyConfirmAtr {
		// 未承認 
		UNAPPROVED = 0,
		// 承認中 
		ON_APPROVED = 1,
		// 承認済 
		ALREADY_APPROVED = 2,
	}

	const API = {
		getConfirmSttByEmp: "at/request/application/approvalstatus/getConfirmApprSttByEmp",
	}
}