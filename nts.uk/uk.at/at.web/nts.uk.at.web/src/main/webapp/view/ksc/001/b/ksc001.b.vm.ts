/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksc001.b {

	import NtsWizardStep = service.model.NtsWizardStep;
	import ScheduleExecutionLogSaveDto = service.model.ScheduleExecutionLogSaveDto;
	import baseService = nts.uk.at.view.kdl023.base.service;
	import DailyPatternSetting = baseService.model.DailyPatternSetting;
	import ReflectionSetting = baseService.model.ReflectionSetting;
	import ReflectionMethod = baseService.model.ReflectionMethod;
	import DayOffSetting = baseService.model.DayOffSetting;

	export module viewmodel {

		export class ScreenModel extends ko.ViewModel {

			// step setup
			stepList: Array<NtsWizardStep>;
			stepSelected: KnockoutObservable<NtsWizardStep>;

			// setup ccg001
			ccgcomponent: GroupOption;

			// Options
			baseDate: KnockoutObservable<Date> = ko.observable( new Date() );
			selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray( [] );

			selectRebuildAtr: KnockoutObservableArray<RadioBoxModel> = ko.observableArray( [] );
			selectImplementAtr: KnockoutObservableArray<RadioBoxModel> = ko.observableArray( [] );
			selectedImplementAtrCode: KnockoutObservable<number> = ko.observable( 0 );
			selectRebuildAtrCode: KnockoutObservable<number> = ko.observable( 0 );
			checkReCreateAtrAllCase: KnockoutObservable<number> = ko.observable( 0 );
			checkProcessExecutionAtrRebuild: KnockoutObservable<number> = ko.observable( 0 );
			checkCreateMethodAtrPersonalInfo: KnockoutObservable<number> = ko.observable( 0 );
			resetMasterInfo: KnockoutObservable<boolean> = ko.observable( false );
			confirm: KnockoutObservable<boolean> = ko.observable( false );

			recreateConverter: KnockoutObservable<boolean> = ko.observable( false );
			recreateEmployeeOffWork: KnockoutObservable<boolean> = ko.observable( false );
			recreateDirectBouncer: KnockoutObservable<boolean> = ko.observable( false );
			recreateShortTermEmployee: KnockoutObservable<boolean> = ko.observable( false );
			recreateWorkTypeChange: KnockoutObservable<boolean> = ko.observable( false );
			recreateShortTimeWorkers: KnockoutObservable<boolean> = ko.observable( false );

			overwriteConfirmedData: KnockoutObservable<boolean> = ko.observable( false );
			createAfterDeleting: KnockoutObservable<boolean> = ko.observable( false );

			resetWorkingHours: KnockoutObservable<boolean> = ko.observable( false );
			resetStartEndTime: KnockoutObservable<boolean> = ko.observable( false );
			resetTimeAssignment: KnockoutObservable<boolean> = ko.observable( false );

			periodDate: KnockoutObservable<any> = ko.observable( {} );
			copyStartDate: KnockoutObservable<any> = ko.observable( new Date() );
			startDateString: KnockoutObservable<string> = ko.observable( "" );
			endDateString: KnockoutObservable<string> = ko.observable( "" );

			lstLabelInfomationB: KnockoutObservableArray<string> = ko.observableArray( [] );
			lstLabelInfomationC: KnockoutObservableArray<string> = ko.observableArray( [] );
			infoCreateMethod: KnockoutObservable<string> = ko.observable( null );
			infoPeriodDate: KnockoutObservable<string> = ko.observable( null );
			lengthEmployeeSelected: KnockoutObservable<string> = ko.observable( null );

			// Employee tab
			lstPersonComponentOption: any;
			selectedEmployeeCode: KnockoutObservableArray<string> = ko.observableArray( [] );
			employeeName: KnockoutObservable<string> = ko.observable( null );
			employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray( [] );
			alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray( [] );
			ccgcomponentPerson: GroupOption;
			personalScheduleInfo: KnockoutObservable<PersonalSchedule> = ko.observable( new PersonalSchedule() );
			responeReflectionSetting: KnockoutObservable<ReflectionSetting> = ko.observable( null );
			responeDailyPatternSetting: KnockoutObservable<DailyPatternSetting> = ko.observable( null );
			employeeId: KnockoutObservable<string> = ko.observable( null );
			//for control field
			isReCreate: KnockoutObservable<boolean> = ko.observable( false );
			isReSetting: KnockoutObservable<boolean> = ko.observable( false );
			isRebuildTargetOnly: KnockoutObservable<boolean> = ko.observable( false );
			isEnableRadioboxRebuildAtr: KnockoutObservable<boolean> = ko.observable( false );

			//list
			lstCreateMethod: KnockoutObservableArray<any> = ko.observableArray( [] );
			lstReCreate: KnockoutObservableArray<any> = ko.observableArray( [] );
			lstProcessExecution: KnockoutObservableArray<any> = ko.observableArray( [] );
			periodStartDate: KnockoutObservable<moment.Moment> = ko.observable( moment() );
			periodEndDate: KnockoutObservable<moment.Moment> = ko.observable( moment() );

			//update
			selectedRadio: KnockoutObservable<number> = ko.observable();
			isFixedSchedules: KnockoutObservable<boolean> = ko.observable( false );
			isHandleUpdateSchedule: KnockoutObservable<boolean> = ko.observable( false );
			isMonthlyPatternRq: KnockoutObservable<boolean> = ko.observable( false );
			hasChangedCondition: KnockoutObservable<boolean> = ko.observable( false );
			monthlyPatternCode: KnockoutObservable<string> = ko.observable();
			creationMethodCode: KnockoutObservable<number> = ko.observable();
			monthlyPatternOpts: KnockoutObservableArray<MonthlyPatternModel> = ko.observableArray( [] );
			creationMethodReference: KnockoutObservableArray<any> = ko.observableArray( [] );
			isMonthlyPattern: KnockoutObservable<boolean> = ko.observable( false );
			isCreationMethod: KnockoutObservable<boolean> = ko.observable( false );
			isEnableNextPageC: KnockoutObservable<boolean> = ko.observable( true );
			isEnableNextPageD: KnockoutObservable<boolean> = ko.observable( true );
			isConfirmedCreation: KnockoutObservable<boolean> = ko.observable( false );
			isCopyStartDate: KnockoutObservable<boolean> = ko.observable( false );
			isAttendance: KnockoutObservable<boolean> = ko.observable( false );
			implementAtr: KnockoutObservable<number> = ko.observable( 0 );
			createMethodAtr: KnockoutObservable<number> = ko.observable( 0 );
			employeeIds: KnockoutObservableArray<string> = ko.observableArray( [] );
			kcp005EmployeeList: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray( [] );
			isKCP005EmployeeSelectedAll: KnockoutObservable<boolean> = ko.observable( false );

			fullSizeSpace: string = "　　";

			constructor() {
				super();

				let self = this;
				let lstRadioBoxModelImplementAtr: RadioBoxModel[] = [];
				let lstRadioBoxModelRebuildAtr: RadioBoxModel[] = [];

				self.stepList = [
					{ content : '.step-1' },
					{ content : '.step-2' },
					{ content : '.step-3' },
					{ content : '.step-4' }
				];

				self.reloadCcg001();

				self.stepSelected = ko.observable( { id : 'step-1', content : '.step-1' } );

				//radios Button Group B4_3
				lstRadioBoxModelImplementAtr.push( new RadioBoxModel( ImplementAtr.GENERALLY_CREATED, self.$i18n( "KSC001_74" ) ) );
				lstRadioBoxModelImplementAtr.push( new RadioBoxModel( ImplementAtr.RECREATE, self.$i18n( "KSC001_75" ) ) );
				self.selectImplementAtr = ko.observableArray( lstRadioBoxModelImplementAtr );
				self.selectedImplementAtrCode = ko.observable( ImplementAtr.GENERALLY_CREATED );

				//ntsSwitchButton B4_6
				lstRadioBoxModelRebuildAtr.push( new RadioBoxModel( ReBuildAtr.REBUILD_ALL, self.$i18n( "KSC001_89" ) ) );
				lstRadioBoxModelRebuildAtr.push( new RadioBoxModel( ReBuildAtr.REBUILD_TARGET_ONLY, self.$i18n( "KSC001_90" ) ) );
				self.selectRebuildAtr = ko.observableArray( lstRadioBoxModelRebuildAtr );
				self.selectRebuildAtrCode = ko.observable( ReBuildAtr.REBUILD_ALL );

				//for control field
				self.isReCreate = ko.computed( function() {
					return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
				} );

				// for is reseting
				self.isReSetting = ko.computed( function() {
					return self.checkProcessExecutionAtrRebuild() == ProcessExecutionAtr.RECONFIG && self.isReCreate();
				} );

				self.isEnableRadioboxRebuildAtr = ko.computed(() => {
					return self.checkProcessExecutionAtrRebuild() == ProcessExecutionAtr.REBUILD && self.isReCreate();
				} );

				self.isRebuildTargetOnly = ko.computed(() => {
					return self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_TARGET_ONLY
						&& self.isEnableRadioboxRebuildAtr();
				} );

				self.isFixedSchedules = ko.computed(() => {
					return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
				} );

				//B4_3 => B4_5
				self.isHandleUpdateSchedule = ko.computed(() => {
					return self.selectedImplementAtrCode() == ImplementAtr.RECREATE;
				} );

				self.periodDate.subscribe(( newValue ) => {
					let newDate =( {} );
					if( newValue.startDate ) {
						self.copyStartDate( newValue.startDate );
						newDate = {
							startDate : moment.utc( newValue.startDate ),
							endDate : moment.utc( newValue.endDate )
						}
						self.periodStartDate( newDate.startDate );
						self.periodEndDate( newDate.endDate );
					}
					self.hasChangedCondition(true);
				} );

				self.lstCreateMethod = ko.observableArray( __viewContext.enums.CreateMethodAtr );
				self.lstReCreate = ko.observableArray( __viewContext.enums.ReCreateAtr );
				self.lstProcessExecution = ko.observableArray( __viewContext.enums.ProcessExecutionAtr );

				self.selectedRadio = ko.observable( 1 );
				self.isMonthlyPatternRq = ko.computed(() => {
					return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE &&
						self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN;
				} );

				self.isCreationMethod = ko.computed(() => {
					return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE;
				} );

				self.isCopyStartDate = ko.computed(() => {
					return self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE;
				} );

				self.monthlyPatternCode( null );
				self.creationMethodCode( null );

				self.isEnableNextPageC = ko.computed(() => {
					if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE
						&& self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN
						&& nts.uk.util.isNullOrEmpty( self.monthlyPatternCode() ) ) {
						return false;
					} else
						return true;
				} );

				self.isEnableNextPageD = ko.computed(() => {
					return self.selectedEmployeeCode().length > 0;
				} );

				self.creationMethodCode.subscribe(( value ) => {
					//self.$errors('clear').then((valid: boolean) => {});
					nts.uk.ui.errors.clearAll()
					self.isMonthlyPattern( CreationMethodRef.MONTHLY_PATTERN == value );
					$( '.monthly-pattern-code' ).focus();

					self.hasChangedCondition(true);
				} );

				//update emplyees listing if has any changed conditions
				self.selectedImplementAtrCode.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.selectRebuildAtrCode.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.recreateConverter.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.recreateEmployeeOffWork.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.recreateShortTimeWorkers.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.recreateDirectBouncer.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.overwriteConfirmedData.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.createAfterDeleting.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.monthlyPatternCode.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.copyStartDate.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});
				self.isConfirmedCreation.subscribe(( value ) => {
					self.hasChangedCondition(true);
				});

				self.checkCreateMethodAtrPersonalInfo.subscribe(( value ) => {
					nts.uk.ui.errors.clearAll();
					if( value === CreateMethodAtr.COPY_PAST_SCHEDULE
						&& nts.uk.util.isNullOrEmpty( self.copyStartDate() ) ) {
						$( '#copy-start-date' ).focus();
						if( self.isInValidCopyPasteSchedule() ) return;
					}

					if( value !== CreateMethodAtr.PATTERN_SCHEDULE ) {
						self.isMonthlyPattern( false );
						self.monthlyPatternCode( null );
					} else {
						$( '.monthly-pattern-code' ).focus();
						if( self.creationMethodCode() === CreationMethodRef.MONTHLY_PATTERN ) {
							self.isMonthlyPattern( true );
						}
					}

					self.hasChangedCondition(true);

					//self.$errors('clear').then((valid: boolean) => {});
				} );

				self.creationMethodReference( [
					{ code : CreationMethodRef.COMPANY_CALENDAR, name : self.$i18n( 'KSC001_108' ) }, //会社カレンダー
					{ code : CreationMethodRef.WORKPLACE_CALENDAR, name : self.$i18n( 'KSC001_109' ) }, //職場カレンダー
					{ code : CreationMethodRef.CLASSIFICATION_CALENDAR, name : self.$i18n( 'KSC001_110' ) }, //分類カレンダー
					{ code : CreationMethodRef.MONTHLY_PATTERN, name : self.$i18n( 'KSC001_111' ) }, //月間パターン
				] );

				self.monthlyPatternOpts( [] );
				self.employeeIds( [] );
				//get monthly pattern
				self.getMonthlyPattern();
			}

			/**
			 * save to client service PersonalSchedule by employeeId
			 */
			private savePersonalScheduleByEmployeeId( employeeId: string, data: PersonalSchedule ): void {
				nts.uk.characteristics.save( "PersonalSchedule_" + employeeId, data );
			}

			/**
			 * save to client service PersonalSchedule
			 */
			private savePersonalSchedule( data: PersonalSchedule ): void {
				let self = this,
					user: any = self.$user; //__viewContext.user

				self.savePersonalScheduleByEmployeeId( user.employeeId, data );
			}

			/**
			 * find by client service PersonalSchedule by employee
			 */
			private findPersonalScheduleByEmployeeId( employeeId: string ): JQueryPromise<PersonalSchedule> {
				return nts.uk.characteristics.restore( "PersonalSchedule_" + employeeId );
			}

			/**
			 * find by client service PersonalSchedule
			 */
			private findPersonalSchedule(): JQueryPromise<PersonalSchedule> {
				let self = this,
					user: any = self.$user;//__viewContext.user;
				return nts.uk.characteristics.restore( "PersonalSchedule_" + user.employeeId );
			}

			/**
			 * function next wizard by on click button
			 */
			private next(): JQueryPromise<void> {
				return $( '#wizard' ).ntsWizard( "next" );
			}

			/**
			 * function previous wizard by on click button
			 */
			private previous(): JQueryPromise<void> {
				return $( '#wizard' ).ntsWizard( "prev" );
			}

			/**
			 * function next two page wizard by on click button
			 */
			private nextTwo(): JQueryPromise<void> {
				let index = $( '#wizard' ).ntsWizard( "getCurrentStep" );
				index = index + 2;
				return $( '#wizard' ).ntsWizard( "goto", index );
			}

			/**
			 * function previous wizard by on click button
			 */
			private previousTwo(): JQueryPromise<void> {
				let index = $( '#wizard' ).ntsWizard( "getCurrentStep" );
				index = index - 2;
				return $( '#wizard' ).ntsWizard( "goto", index );
			}

			/**
			 * function convert string to Date
			 */
			private toDate( strDate: string ): Date {
				return moment( strDate, 'YYYY/MM/DD' ).toDate();
			}

			public reloadCcg001(): void {
				let self = this;
				if( $( '.ccg-sample-has-error' ).ntsError( 'hasError' ) ) {
					return;
				}

				self.ccgcomponent = {
					/** Common properties */
					systemType : 2, // システム区分
					showEmployeeSelection : false, // 検索タイプ
					showQuickSearchTab : true, // クイック検索
					showAdvancedSearchTab : true, // 詳細検索
					showBaseDate : false, // 基準日利用
					showClosure : false, // 就業締め日利用
					showAllClosure : false, // 全締め表示
					showPeriod : true, // 対象期間利用
					periodFormatYM : false, // 対象期間精度

					/** Required parameter */
					baseDate : self.baseDate().toISOString(), // 基準日
					periodStartDate : self.periodStartDate().toISOString(), // 対象期間開始日
					periodEndDate : self.periodEndDate().toISOString(), // 対象期間終了日
					inService : true, // 在職区分
					leaveOfAbsence : true, // 休職区分
					closed : true, // 休業区分
					retirement : true, // 退職区分

					/** Quick search tab options */
					showAllReferableEmployee : true, // 参照可能な社員すべて
					showOnlyMe : true, // 自分だけ
					showSameWorkplace : true, // 同じ職場の社員
					showSameWorkplaceAndChild : true, // 同じ職場とその配下の社員

					/** Advanced search properties */
					showEmployment : true, // 雇用条件
					showWorkplace : true, // 職場条件
					showClassification : true, // 分類条件
					showJobTitle : true, // 職位条件
					showWorktype : true, // 勤種条件
					isMutipleCheck : true, // 選択モード

					/** Return data */
					returnDataFromCcg001 : function( data: any ) {
						const mappedEmployeeList = _.map( data.listEmployee, employeeSearch => {
							return {
								code : employeeSearch.employeeCode,
								name : employeeSearch.employeeName,
								affiliationName : employeeSearch.affiliationName
							}
						} );

						self.employeeList( mappedEmployeeList );
						self.selectedEmployee( data.listEmployee );
						self.applyKCP005ContentSearch( data.listEmployee );
					}
				}
			}

			/**
			 * start page data
			 */
			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();
				// block ui
				self.$blockui("show");
				//get startdate and enddate for a schedule
				service.getInitialDate().done(( data ) => {
					// update start date end date to ccg001
					self.periodDate( {
						startDate : data.startDate,
						endDate : data.endDate
					} );
					//self.reloadCcg001();
					dfd.resolve( self );
				}).always( () => self.$blockui("hide") );

				//init Schedule for personal
				self.displayPersonalInfor();
				self.isAttendance(self.$user.role.isInCharge.attendance);
					//__viewContext.user.role.isInCharge.attendance;
				if( !self.isAttendance() ) {
					self.isConfirmedCreation( false );
					self.overwriteConfirmedData( false );
				}

				//fix screen on 1280
				if( window.outerWidth <= 1400 ) {
					$( '#contents-area' ).addClass( 'fix-2180' );
				}

				return dfd.promise();
			}

			/**
			 * apply ccg001 search data to kcp005
			 */
			public applyKCP005ContentSearch( dataList: EmployeeSearchDto[] ): JQueryPromise<void> {
				let self = this,
					employeeSearchs: UnitModel[] = [],
					listSelectedEmpCode: any = [],
					oldListSelectedEmpCode: any = [],
					employeeIds: Array<string> = [],
					newListEmployees: Array<string> = [];

				let dfd = $.Deferred<void>();

				self.$blockui("grayout");

				self.employeeList( [] );
				self.selectedEmployeeCode( [] );
				self.employeeIds( [] );
				oldListSelectedEmpCode = dataList;
				//save to reload
				self.kcp005EmployeeList( dataList );

				_.each( dataList,( employeeSearch ) => {
					let employeeCode = employeeSearch.employeeCode.trim ();
					let isExistedEmployeeCode = _.find(listSelectedEmpCode, (x) => x === employeeCode);

					if ( _.isNil(isExistedEmployeeCode) ) {
						employeeIds.push ( employeeSearch.employeeId );
						employeeSearchs.push ( {
							code : employeeSearch.employeeCode,
							name : employeeSearch.employeeName,
							affiliationName : employeeSearch.affiliationName
						} );
						listSelectedEmpCode.push(employeeCode );
					}
				});

				// update employee list by ccg001 search
				self.employeeIds( employeeIds );

				//filter personal with new conditions
				let startDate = moment.utc( self.periodStartDate(), "YYYY/MM/DD" );
				let endDate = moment.utc( self.periodEndDate(), "YYYY/MM/DD" );

				self.isKCP005EmployeeSelectedAll(false);

				if(self.selectedImplementAtrCode() === ImplementAtr.GENERALLY_CREATED
					|| (self.selectedImplementAtrCode() === ImplementAtr.RECREATE &&
					self.selectRebuildAtrCode() === ReBuildAtr.REBUILD_ALL) ) {

					self.employeeList( employeeSearchs );
					self.selectedEmployeeCode( listSelectedEmpCode );
					//self.isKCP005EmployeeSelectedAll(false);
					self.$blockui("hide");
					dfd.resolve();
				} else {
					//self.isKCP005EmployeeSelectedAll(true);

					let listEmployeeFilter: ListEmployeeIds = new ListEmployeeIds(
						employeeIds,
						self.recreateConverter(),
						self.recreateEmployeeOffWork(),
						self.recreateShortTimeWorkers(),
						self.recreateDirectBouncer(),
						startDate, endDate
					);

					if( employeeIds.length > 0 ) {
						self.employeeIds( [] );
						service.getEmployeeListAfterFilter( listEmployeeFilter )
							.done(( response ) => {
								newListEmployees = response.listEmployeeId;
								//reset data listing after filtered
								employeeSearchs = [];
								employeeIds = [];
								listSelectedEmpCode = [];
								if( newListEmployees.length > 0 ) {
									for( let i = 0 ; i < newListEmployees.length ; i++ ) {
										for( let j = 0 ; j < oldListSelectedEmpCode.length ; j++ ) {
											if( oldListSelectedEmpCode[ j ].employeeId == newListEmployees[ i ] ) {
												employeeSearchs.push( {
													code : oldListSelectedEmpCode[ j ].employeeCode,
													name : oldListSelectedEmpCode[ j ].employeeName,
													affiliationName : oldListSelectedEmpCode[ j ].affiliationName
												} );
												listSelectedEmpCode.push( oldListSelectedEmpCode[ j ].employeeCode.trim() );
												employeeIds.push( oldListSelectedEmpCode[ j ].employeeId );
											}
										}
									}

									self.employeeList( employeeSearchs );
									self.selectedEmployeeCode( listSelectedEmpCode );
									self.employeeIds( employeeIds );
								} else {
									self.$dialog.error({ messageId: "Msg_1779" }).then(() => {});
								}

								self.$blockui("hide");
								dfd.resolve();
							})
							.fail(() => {
								self.$blockui("hide");
								dfd.reject();
							})
							.always(() => {
								self.$blockui("hide");
								dfd.resolve();
							});
					} else {
						self.$blockui("hide");
						dfd.resolve();
					}
				}

				// update kc005
				self.lstPersonComponentOption = {
					isShowAlreadySet : false, //設定済表示
					isMultiSelect : true,
					listType : ListType.EMPLOYEE,
					employeeInputList : self.employeeList,
					selectType : SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode : self.selectedEmployeeCode,
					isDialog : true,
					isShowNoSelectRow : false, //未選択表示
					alreadySettingList : self.alreadySettingPersonal,
					isShowWorkPlaceName : true, //職場表示
					isShowSelectAllButton : false, //全選択表示
					isSelectAllAfterReload : true,
					maxWidth : 550,
					maxRows : 10,
					tabindex : -1
				};

				return dfd.promise();
			}

			/**
			 * function next page by selection employee goto page(C)
			 */
			private nextPageEmployee(): void {
				var self = this;
				if( $( '.nts-input' ).ntsError( 'hasError' ) ) {
					return;
				}
				// check selection employee
				if( self.selectedEmployeeCode && self.selectedEmployee() && self.selectedEmployeeCode().length > 0 ) {
					var user: any = self.$user;
					self.findPersonalScheduleByEmployeeId( user.employeeId ).done( function( data ) {
						self.updatePersonalScheduleData( data );
						// focus by done
						self.next().done( function() {
							$( '#inputSelectImplementAtr' ).focus();
						} );
					} ).fail( function( error ) {
						console.log( error );
					} );
				}
				else {
					// show message by not choose employee of kcp005
					self.$dialog.error( { messageId : 'Msg_206' } ).then(()=>{});
				}
			}

			/**
			 * update PersonalSchedule by find by employee id login
			 */
			private updatePersonalScheduleData( data: PersonalSchedule ): void {
				var self = this;
				if( data ) {
					self.personalScheduleInfo( data );
				}
			}

			/**
			 * convert ui to PersonalSchedule
			 */
			private toPersonalScheduleData(): PersonalSchedule {
				var self = this;
				var user: any = self.$user; // __viewContext.user;
				var data: PersonalSchedule = new PersonalSchedule();

				let Converter: boolean = false,
					EmployeeOffWork: boolean = false,
					ShortTimeWorkers: boolean = false,
					DirectBouncer: boolean = false,
					ConfirmedData: boolean = false,
					createAfterDeleted: boolean = false,
					creationMethodCode: number = 0,
					monthlyPatternCode: string = null;

				if( self.selectedImplementAtrCode() === ImplementAtr.RECREATE ) {
					if(self.selectRebuildAtrCode() === ReBuildAtr.REBUILD_TARGET_ONLY ) {
						Converter = self.recreateConverter ();
						EmployeeOffWork = self.recreateEmployeeOffWork ();
						ShortTimeWorkers = self.recreateShortTimeWorkers ();
						DirectBouncer = self.recreateDirectBouncer ();
					}
					ConfirmedData =  self.overwriteConfirmedData();
					createAfterDeleted = self.createAfterDeleting();
				}

				if(self.checkCreateMethodAtrPersonalInfo() === CreateMethodAtr.PATTERN_SCHEDULE) {
					creationMethodCode =  self.creationMethodCode();
					if( creationMethodCode ===  CreationMethodRef.MONTHLY_PATTERN )
						monthlyPatternCode = self.monthlyPatternCode();
				}

				data.employeeId = user.employeeId;
				data.implementAtr = self.selectedImplementAtrCode();
				data.reCreateAtr = self.checkReCreateAtrAllCase();
				data.processExecutionAtr = self.checkProcessExecutionAtrRebuild();
				data.rebuildTargetAtr = self.selectRebuildAtrCode();
				data.recreateConverter = Converter; //self.recreateConverter();
				data.recreateEmployeeOffWork = EmployeeOffWork; //self.recreateEmployeeOffWork();
				data.recreateDirectBouncer = DirectBouncer; //self.recreateDirectBouncer();
				data.recreateShortTermEmployee = self.recreateShortTermEmployee();
				data.recreateWorkTypeChange = self.recreateWorkTypeChange();
				data.recreateShortTimeWorkers = ShortTimeWorkers; //self.recreateShortTimeWorkers();

				data.resetWorkingHours = self.resetWorkingHours();
				data.resetStartEndTime = self.resetStartEndTime();
				data.resetMasterInfo = self.resetMasterInfo();
				data.resetTimeAssignment = self.resetTimeAssignment();
				data.confirm = self.isConfirmedCreation();//self.confirm();
				data.createMethodAtr = self.checkCreateMethodAtrPersonalInfo();

				data.overwriteConfirmedData = ConfirmedData;//self.overwriteConfirmedData();
				data.createAfterDeleting = createAfterDeleted;//self.createAfterDeleting();
				data.selectedImplementAtrCode = self.selectedImplementAtrCode();
				data.selectRebuildAtrCode = self.selectRebuildAtrCode();
				data.checkCreateMethodAtrPersonalInfo = self.checkCreateMethodAtrPersonalInfo();
				data.creationMethodCode = creationMethodCode;//self.creationMethodCode();
				data.monthlyPatternCode = monthlyPatternCode;//self.monthlyPatternCode();
				data.isConfirmedCreation = self.isConfirmedCreation();

				return data;
			}

			/**
			 * function previous page by selection employee goto page(C)
			 */
			private previousPageC(): void {
				var self = this;
				nts.uk.ui.errors.clearAll();
				self.previous();
				$('.ntsStartDatePicker').focus();
			}

			/**
			 * function next page by selection employee goto next page
			 */
			private nextPageB(): void {
				var self = this;

				self.builDataForScreenC();

				if ((self.selectedImplementAtrCode() == ImplementAtr.RECREATE)
					&& self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_TARGET_ONLY) {
					if (!self.recreateConverter() && !self.recreateEmployeeOffWork()
						&& !self.recreateDirectBouncer() && !self.recreateShortTimeWorkers()) {
						nts.uk.ui.dialog.error({messageId: "Msg_1734"});
						$('.b51checkLists .checkBoxGroup ').focus();
						return;
					}
				}

				if (self.selectedImplementAtrCode() == ImplementAtr.RECREATE
					&& self.createAfterDeleting()) {
					nts.uk.ui.dialog.confirmDanger({messageId: "Msg_1735"})
						.ifYes(() => {
							//goto screen C
							self.next().done(function () {
								$('#inputCreateMethodAtr').focus();
							});
						}).ifNo(() => {
						return;
					});
				} else {
					//goto screen C
					self.next ().done ( function () {
						$('#inputCreateMethodAtr').focus();
					} );
				}
			}

			private builDataForScreenC() {
				let self = this;
				let lstLabelInfomation: Array<string> = [];

				//{0}　～　{1} -> KSC001_46
				self.infoPeriodDate( self.$i18n( "KSC001_46", [ self.periodDate().startDate, self.periodDate().endDate ] ) );

				//build from B for E
				//B4_4 -> #KSC001_74, B4_5 -> #KSC001_75
				if( self.selectedImplementAtrCode() == ImplementAtr.GENERALLY_CREATED )
					lstLabelInfomation.push( self.$i18n( "KSC001_74" ) );
				else {
					lstLabelInfomation.push( self.$i18n( "KSC001_75" ) );

					//B4_7 -> #KSC001_37 + #KSC001_89, B4_8 -> #KSC001_37 + #KSC001_90
					if( self.selectRebuildAtrCode() == ReBuildAtr.REBUILD_ALL )
						lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_89" ) );
					else {
						lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_90" ) );

						//B5_2 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_91
						if( self.recreateConverter() )
							lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_91" ) );
						//B5_3 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_92
						if( self.recreateEmployeeOffWork() )
							lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_92" ) );
						//B5_4 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_93
						if( self.recreateShortTimeWorkers() )
							lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_93" ) );
						//B5_5 -> 「▲」:fullSizeSpace +  #KSC001_38 + #KSC001_94
						if( self.recreateDirectBouncer() )
							lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_94" ) );
					}

					//B6_3 Or B6_4: [#KSC001_37 + #KSC001_104] or [#KSC001_37 + #KSC001_105]
					if( self.overwriteConfirmedData() ) {
						lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_104" ) );
					} else if( self.createAfterDeleting() )
						lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_105" ) );
					//B6_3
					if( self.overwriteConfirmedData() )
						lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_105" ) );
					//B6_4
					if( self.createAfterDeleting() )
						lstLabelInfomation.push( self.fullSizeSpace + self.$i18n( "KSC001_38" ) + self.$i18n( "KSC001_106" ) );
				}
				//apply from B -> E1_6
				self.lstLabelInfomationB( lstLabelInfomation );
			}

			/**
			 * function previous page by selection employee goto page(B)
			 */
			private previousPageB(): void {
				var self = this;
				self.previous();
			}

			/**
			 * function previous page by selection employee goto page(D)
			 */
			private nextPageC(): void {
				var self = this;

				self.isKCP005EmployeeSelectedAll(false);

				if( self.isInValidCopyPasteSchedule() ) {
					return;
				} else {
					self.buildString();
					self.next().done( function() {
						$('#employeeSearch .nts-gridlist').attr('tabindex', '-1');
						if( self.kcp005EmployeeList().length <= 0 )
							$('.ccg-lbl-search-drawer').click();

						if( self.kcp005EmployeeList().length > 0 && self.hasChangedCondition() ) {
							self.applyKCP005ContentSearch( self.kcp005EmployeeList() );
						}
						self.hasChangedCondition(false);
					} );
				}
			}

			/**
			 * Validate copy paste schedule
			 */
			private isInValidCopyPasteSchedule(): boolean {
				let self = this,
					hasError : boolean = false;

				if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE ) {
					$( '#copy-start-date' ).ntsEditor( 'validate' );
				} else if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PATTERN_SCHEDULE
					&& self.creationMethodCode() == CreationMethodRef.MONTHLY_PATTERN
					&& nts.uk.util.isNullOrEmpty( self.monthlyPatternCode() ) ) {
					$( '.monthly-pattern-code' ).ntsEditor( 'validate' );
				}

				return nts.uk.ui.errors.hasError();
			}

			/**
			 * function previous page by selection employee goto page(D)
			 */
			private previousPageD(): void {
				var self = this;
				if( self.isInValidCopyPasteSchedule() ) {
					return;
				}
				self.previous().then( () => {
					$('#inputCreateMethodAtr').focus();
				});
			}

			/**
			 * function next page by selection employee goto page(E)
			 */
			private nextPageD(): void {

				let self = this,
					selectedEmployeeCode = self.selectedEmployeeCode(),
					employeeList = self.employeeList(),
					kcp005EmployeeList = self.kcp005EmployeeList();

				//reset selected code before sent do F
				if ( selectedEmployeeCode.length <= 0 ) {
					self.$dialog.error ( { messageId : "Msg_206" } ).then ( () => {
						return;
					} );
				} else {
						let totalEmployees = 0,
							newEmployeesIds: Array<string> = [];

						_.forEach ( selectedEmployeeCode, ( item ) => {
							let findInEmployeeList = _.find(employeeList, (x) => { return x.code == item });
							if( !_.isNil(findInEmployeeList)) {
								let foundEmployee = _.find ( kcp005EmployeeList, ( x ) => {
									return x.employeeCode === item
								} );
								if ( !_.isNil ( foundEmployee ) ) newEmployeesIds.push ( foundEmployee.employeeId );
							}
						} );

						self.employeeIds( newEmployeesIds );
						totalEmployees = newEmployeesIds.length;
						self.lengthEmployeeSelected ( self.$i18n ( "KSC001_47", [ totalEmployees.toString () ] ) );
						self.openDialogPageE ();
				}
			}

			/**
			 * Find all pattern
			 */
			private findAllPattern(): void {
				let self = this;
				_.defer(() => {
					self.$blockui( "show" );
					baseService.findAllPattern()
						.done( allData => {
							// next page E by pattern code of all data first
							if( allData && allData.length > 0 ) {
								self.responeDailyPatternSetting( allData[ 0 ] );
								self.openDialogPageE();
							} else {
								// show message error 531
								self.$dialog.error( { messageId : 'Msg_531' } ).then(() => {
								} );
							}
						} )
						.always(() => self.$blockui( "hide" ) )
						.fail(() => self.$blockui( "hide" ) );
				} );
			}

			/**
			 * find by pattern code and open dialog E
			 */
			private findByPatternCodeAndOpenPageE( patternCode: string ): void {
				var self = this;
				_.defer(() => {
					self.$blockui( "show" );
					baseService.findPatternByCode( patternCode ).done( function( res ) {
						if( res && res != null ) {
							self.responeDailyPatternSetting( res );
							self.openDialogPageE();
						}
						else {
							self.$dialog.error( { messageId : 'Msg_531' } ).then(() => {
							} );
						}
					} ).always(() => self.$blockui( "hide" ) );
				} );
			}

			/**
			 * open dialog E
			 */
			private openDialogPageE(): void {
				var self = this;
				//self.buildString();
				self.next().done( function() {
					$( '#buttonFinishPageE' ).focus();
				} );
			}

			/**
			 * function previous page by selection employee goto page(E)
			 */
			private previousPageE(): void {
				var self = this;
				self.previous();
			}

			/**
			 * finish next page by selection employee goto page(F)
			 */
			private finish(): void {
				var self = this;
				self.$blockui( "show" );
				service.checkThreeMonth( self.toDate( self.periodDate().startDate ) )
					.done( function( check ) {
						self.$blockui( "hide" );
						if( check ) {
							// show message confirm 567
							self.$dialog.confirm({ messageId: "Msg_567" }).then((result: 'no' | 'yes' | 'cancel') => {
								if (result === 'yes') {
									self.createByCheckMaxMonth();
								} else
									return;
							});
						} else {
							self.createByCheckMaxMonth();
						}
					} ).fail( function( error ) {
					self.$blockui( "hide" );
				} ).always( function() {
					self.$blockui( "hide" );
				} );
			}

			/**
			 * function build string to page E
			 */
			private buildString() {
				var self = this;
				var lstLabelInfomation: string[] = [];

				if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.PERSONAL_INFO ) {
					lstLabelInfomation.push( self.$i18n( "KSC001_22" ) ); //#KSC001_22
				} else if( self.checkCreateMethodAtrPersonalInfo() == CreateMethodAtr.COPY_PAST_SCHEDULE ) {
					lstLabelInfomation.push( self.$i18n( "KSC001_24" ) );
					//#KSC001_37+#KSC001_26+#KSC001_114+「C2_15」
					let copyStartDate = moment( self.copyStartDate() ).format( 'YYYY/MM/DD' );
					lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_26" ) + self.$i18n( "KSC001_114" ) + copyStartDate );
				} else {
					lstLabelInfomation.push( self.$i18n( "KSC001_23" ) );
					switch( self.creationMethodCode() ) {
						case 0:
							//#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_108
							lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_113" ) + self.$i18n( "KSC001_114" ) + self.$i18n( "KSC001_108" ) );
							break
						case 1:
							//#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_109
							lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_113" ) + self.$i18n( "KSC001_114" ) + self.$i18n( "KSC001_109" ) );
							break;
						case 2:
							//#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_109
							lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_113" ) + self.$i18n( "KSC001_114" ) + self.$i18n( "KSC001_110" ) );
							break;
						case 3:
							//#KSC001_37+#KSC001_113+#KSC001_114+#KSC001_111
							lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_113" ) + self.$i18n( "KSC001_114" ) + self.$i18n( "KSC001_111" ) );
							//#KSC001_37+#KSC001_111+#KSC001_114+「C2_12」+「▲」+「C2_13」
							let monthlyPattern = _.find( self.monthlyPatternOpts(), (element) => { return element.code == self.monthlyPatternCode() });
							let monthlyPatternText = !_.isNil(monthlyPattern) ? monthlyPattern.name : '';
							lstLabelInfomation.push( self.$i18n( "KSC001_37" ) + self.$i18n( "KSC001_111" ) + self.$i18n( "KSC001_114" ) + monthlyPatternText );
							break;
					}
				}

				//C3_2
				if( self.isConfirmedCreation() ) {
					lstLabelInfomation.push( self.$i18n( "KSC001_17" ) ); //#KSC001_17
				}

				//apply from C -> E1_9
				self.lstLabelInfomationC( lstLabelInfomation );
			}

			/**
			 * function createPersonalSchedule to client by check month max
			 */
			private createByCheckMaxMonth(): void {
				let self = this;
				_.defer(() => {
					self.$blockui("show");
					service.checkMonthMax( self.toDate( self.periodDate().startDate ) ).done( checkMax => {
						self.$blockui("hide");
						if( checkMax ) {
							self.$dialog.confirm({ messageId: "Msg_568" }).then((result) => {
								if (result === 'yes') {
									self.createPersonalSchedule();
								} else
									return;
							});
						} else {
							self.createPersonalSchedule();
						}
					}).always( () => { self.$blockui("hide"); } );
				} );
			}

			/**
			 * function createPersonalSchedule to client
			 */
			private createPersonalSchedule(): void {
				let self = this;
				self.$dialog.confirm({ messageId: "Msg_569" }).then((result) => {
					if (result === 'yes') {
						self.savePersonalScheduleData();
					} else
						return;
				});
			}

			/**
			 * save PersonalSchedule data
			 */
			private savePersonalScheduleData(): void {
				let self = this;
				self.savePersonalSchedule( self.toPersonalScheduleData() );
				service.addScheduleExecutionLog( self.scheduleCollectionData() ).done( function( data ) {
					self.$window.storage( 'inputData', data );
					self.$window.modal( "/view/ksc/001/f/index.xhtml" ).then( () => {});
				} );
			}

			/**
			 * open dialog KDL023
			 */
			private showDialogKDL023(): void {
				let self = this,
					data: PersonalSchedule = new PersonalSchedule();
				self.findPersonalSchedule().done( function( dataInfo ) {
					if( dataInfo && dataInfo != null ) {
						data = dataInfo;
					}
					self.$window.storage( 'reflectionSetting', self.convertPersonalScheduleToReflectionSetting( data ) );
					self.$window.modal( '/view/kdl/023/b/index.xhtml' ).then(() => {
						let dto = self.$window.storage( 'returnedData' );
						self.responeReflectionSetting( dto );
					} );
				} );

			}

			/**
			 * convert data personal schedule to refelctionSetting
			 */
			private convertPersonalScheduleToReflectionSetting( data: PersonalSchedule ): ReflectionSetting {
				let self = this,
					dto: ReflectionSetting = {
						calendarStartDate : self.periodDate().startDate,
						calendarEndDate : self.periodDate().endDate,
						selectedPatternCd : data.patternCode,
						patternStartDate : self.periodDate().startDate,
						reflectionMethod : data.holidayReflect,
						statutorySetting : self.convertWorktypeSetting( data.statutoryHolidayUseAtr, data.statutoryHolidayWorkType ),
						holidaySetting : self.convertWorktypeSetting( data.holidayUseAtr, data.holidayWorkType ),
						nonStatutorySetting : self.convertWorktypeSetting( data.legalHolidayUseAtr, data.legalHolidayWorkType )
					};
				return dto;
			}

			/**
			 * find employee id in selected
			 */
			public findEmployeeIdByCode( employeeCode: string ): string {
				let self = this,
					employeeId = '';
				for( let employee of self.selectedEmployee() ) {
					if( employee.employeeCode === employeeCode ) {
						employeeId = employee.employeeId;
					}
				}
				return employeeId;
			}

			/**
			 * find employee id in selection employee code
			 */
			public findEmployeeIdsByCode( employeeCodes: string[] ): string[] {
				let self = this,
					employeeIds: string[] = [];
				for( let employeeCode of employeeCodes ) {
					let employeeId = self.findEmployeeIdByCode( employeeCode );
					if( employeeId && !( employeeId === '' ) ) {
						employeeIds.push( employeeId );
					}
				}
				return employeeIds;
			}

			/**
			 * collection data => command save
			 */
			private scheduleCollectionData(): ScheduleExecutionLogSaveDto {
				var self = this,
					user: any = self.$user,//__viewContext.user,
					data: PersonalSchedule = self.toPersonalScheduleData(),
					isCopyStartDate : boolean = true;

				if( self.checkCreateMethodAtrPersonalInfo() !== CreateMethodAtr.COPY_PAST_SCHEDULE ) {
					isCopyStartDate = false;
				}

				let dto: ScheduleExecutionLogSaveDto = {
						periodStartDate : self.toDate( self.periodDate().startDate ),
						periodEndDate : self.toDate( self.periodDate().endDate ),
						creationType : data.selectedImplementAtrCode, //実施区分
						reTargetAtr : data.selectRebuildAtrCode, //作り直す対象区分
						referenceMaster : data.creationMethodCode, //作成方法ドロップダウンリスト
						reTargetTransfer : data.recreateConverter, //異動者（指定期間で職場異動がある人）
						reTargetLeave : data.recreateEmployeeOffWork, //休職休業者（指定期間に休職休業期間が１日でもある人）
						reTargetShortWork : data.recreateShortTimeWorkers, //短時間勤務者
						reTargetLaborChange : data.recreateDirectBouncer, //労働条件変更者
						reOverwriteConfirmed : data.overwriteConfirmedData, //確定済みのスケジュールも対象とする
						reOverwriteRevised : data.createAfterDeleting, //手修正・申請反映した日のスケジュールも対象とする
						monthlyPatternId : data.monthlyPatternCode, //月間パターンコード
						beConfirmed : data.confirm, //作成時の同時確定
						creationMethod : data.checkCreateMethodAtrPersonalInfo, //作成方法区分
						copyStartYmd : (isCopyStartDate ? self.toDate( self.copyStartDate() ) : null ), //コピー開始日
						employeeIds : self.employeeIds(),
						employeeIdLogin : user.employeeIdLogin
					};
				return dto;
			}

			/**
			 * collection data => command save
			 */
			private collectionData(): ScheduleExecutionLogSaveDto {
				var self = this,
					data: PersonalSchedule = self.toPersonalScheduleData(),
					dto: ScheduleExecutionLogSaveDto = {
						periodStartDate : self.toDate( self.periodDate().startDate ),
						periodEndDate : self.toDate( self.periodDate().endDate ),
						implementAtr : data.implementAtr,
						reCreateAtr : data.reCreateAtr,
						processExecutionAtr : data.processExecutionAtr,
						reTargetAtr : data.rebuildTargetAtr,
						resetWorkingHours : data.resetWorkingHours,
						resetMasterInfo : data.resetMasterInfo,
						reTimeAssignment : data.resetTimeAssignment,
						reConverter : data.recreateConverter,
						reStartEndTime : data.resetStartEndTime,
						reEmpOffWork : data.recreateEmployeeOffWork,
						reShortTermEmp : data.recreateShortTermEmployee,
						reWorkTypeChange : data.recreateWorkTypeChange,
						reDirectBouncer : data.recreateDirectBouncer,
						// TODO
						protectHandCorrect : null,
						confirm : data.confirm,
						createMethodAtr : data.createMethodAtr,
						copyStartYmd : self.toDate( self.copyStartDate() ),
						employeeIds : self.findEmployeeIdsByCode( self.selectedEmployeeCode() )
					};
				return dto;
			}

			/**
			 * convert work type setting
			 */
			private convertWorktypeSetting( use: number, worktypeCode: string ): DayOffSetting {
				let data: DayOffSetting = {
					useClassification : use == UseAtr.USE,
					workTypeCode : worktypeCode
				};
				return data;
			}

			//get personal infor from local storage
			private displayPersonalInfor() {
				let self = this;
				let user: any = self.$user;//__viewContext.user;

				self.findPersonalScheduleByEmployeeId( user.employeeId ).done(( data ) => {
					if( typeof data !== 'undefined' && data ) {
						self.recreateConverter( data.recreateConverter );//異動者
						self.recreateDirectBouncer( data.recreateDirectBouncer ); //休職休業者
						self.recreateShortTimeWorkers( data.recreateShortTimeWorkers );//短時間勤務者
						self.recreateWorkTypeChange( data.recreateWorkTypeChange );//労働条件変更者

						self.recreateEmployeeOffWork( data.recreateEmployeeOffWork );
						self.recreateShortTermEmployee( data.recreateShortTermEmployee );
						//Page B
						self.createAfterDeleting( data.createAfterDeleting );
						self.overwriteConfirmedData( data.overwriteConfirmedData );
						self.selectedImplementAtrCode( data.selectedImplementAtrCode );
						self.selectRebuildAtrCode( data.selectRebuildAtrCode );
						self.checkCreateMethodAtrPersonalInfo( data.checkCreateMethodAtrPersonalInfo );
						self.isConfirmedCreation( data.isConfirmedCreation );
						self.monthlyPatternCode( data.monthlyPatternCode );
						self.creationMethodCode( data.creationMethodCode );
						self.confirm( data.confirm );
						self.createMethodAtr( data.createMethodAtr );
						self.employeeId( data.employeeId );
						self.implementAtr( data.implementAtr );
						self.resetMasterInfo( data.resetMasterInfo );
						self.resetStartEndTime( data.resetStartEndTime );
						self.resetTimeAssignment( data.resetTimeAssignment );
						self.resetWorkingHours( data.resetWorkingHours );
						//self.holidayReflect(data.holidayReflect);
						///self.holidayUseAtr(data.holidayUseAtr);
						//self.holidayWorkType(data.holidayWorkType);
						//self.legalHolidayUseAtr(data.legalHolidayUseAtr);
						//self.legalHolidayWorkType(data.legalHolidayWorkType);
						//self.patternCode(data.patternCode);
						//self.patternStartDate(data.patternStartDate);
						//self.processExecutionAtr(data.processExecutionAtr);
						//self.reCreateAtr(data.reCreateAtr);
						//self.statutoryHolidayUseAtr(data.statutoryHolidayUseAtr);
						//self.statutoryHolidayWorkType(data.statutoryHolidayWorkType);
					}
				} );
			}

			private getMonthlyPattern() {
				let self = this;
				service.getMonthlyPattern()
					.done(( response ) => {
						if( typeof response !== 'undefined' && response.listMonthlyPattern.length > 0 ) {
							let monthlyOptions = [];
							//monthlyOptions.push( new MonthlyPatternModel());
							response.listMonthlyPattern.map(( item, i ) => {
								monthlyOptions.push( new MonthlyPatternModel(
									item.monthlyPatternCode, item.monthlyPatternName, item.companyId
								) );
							} );

							self.monthlyPatternOpts( monthlyOptions );
						}
					} )
					.always()
					.fail(( error ) => {
						console.log( error );
					} );
			}

			private listEmployeeFilter( data: ListEmployeeIds ) {
				//getEmployeeListAfterFilter
				let results: Array<string> = [];

				let self = this;
				service.getEmployeeListAfterFilter( data )
					.done(( response ) => {
						results = response.listEmployeeId;
					} )
					.fail(( error ) => {
						results = error;
					} );

				return results;
			}
		}


		// 実施区分
		export enum ImplementAtr {
			// 通常作成
			GENERALLY_CREATED = 0,

			// 再作成
			RECREATE = 1
		}

		// 再作成区分
		export enum ReCreateAtr {
			// 全件
			ALLCASE = 0,

			// 未確定データのみ
			ONLYUNCONFIRM = 1
		}

		// 作成方法区分
		export enum CreateMethodAtr {
			// 個人情報
			PERSONAL_INFO = 0,

			// パターンスケジュール
			PATTERN_SCHEDULE = 1,

			// 過去スケジュールコピー
			COPY_PAST_SCHEDULE = 2
		}

		// 処理実行区分
		export enum ProcessExecutionAtr {
			// もう一度作り直す
			REBUILD = 0,

			// 再設定する
			RECONFIG = 1
		}

		// 再作成対象区分
		export enum ReBuildAtr {
			// 全員
			REBUILD_ALL = 0,

			// 対象者のみ
			REBUILD_TARGET_ONLY = 1
		}

		// 利用区分
		export enum UseAtr {
			// 使用しない
			NOTUSE = 0,

			// 使用する
			USE = 1
		}

		// 作成方法（参照先）
		export enum CreationMethodRef {
			// 会社カレンダー
			COMPANY_CALENDAR = 0,

			// 職場カレンダー
			WORKPLACE_CALENDAR = 1,

			//分類カレンダー
			CLASSIFICATION_CALENDAR = 2,

			//月間パターン
			MONTHLY_PATTERN = 3
		}

		// 個人スケジュールの作成
		export class PersonalSchedule {
			// 社員ID
			employeeId: string;

			//実施区分
			implementAtr: ImplementAtr;

			// 再作成区分
			reCreateAtr: ReCreateAtr;

			// 処理実行区分
			processExecutionAtr: ProcessExecutionAtr;

			// 再作成対象区分
			rebuildTargetAtr: ReBuildAtr;

			// 異動者を再作成
			recreateConverter: boolean;

			// 休職休業者を再作成
			recreateEmployeeOffWork: boolean;

			// 直行直帰者を再作成
			recreateDirectBouncer: boolean;

			// 短時間勤務者を再作成
			recreateShortTermEmployee: boolean;

			// 勤務種別変更者を再作成
			recreateWorkTypeChange: boolean;

			// 勤務開始・終了時刻を再設定
			resetWorkingHours: boolean;

			// 休憩開始・終了時刻を再設定
			resetStartEndTime: boolean;

			// マスタ情報を再設定
			resetMasterInfo: boolean;

			// 申し送り時間を再設定
			resetTimeAssignment: boolean;

			// 作成時に確定済みにする
			confirm: boolean;

			// 作成方法区分
			createMethodAtr: CreateMethodAtr

			// パターンコード
			patternCode: string;

			// 休日反映方法
			holidayReflect: ReflectionMethod;

			// パターン開始日
			patternStartDate: Date;

			// 法内休日利用区分
			legalHolidayUseAtr: UseAtr

			// 法内休日勤務種類
			legalHolidayWorkType: string;

			// 法外休日利用区分
			statutoryHolidayUseAtr: UseAtr;

			//法外休日勤務種類
			statutoryHolidayWorkType: string

			// 祝日利用区分
			holidayUseAtr: UseAtr;

			// 祝日勤務種類
			holidayWorkType: string;

			recreateShortTimeWorkers: boolean;
			//確定データを上書き
			overwriteConfirmedData: boolean;
			//削除してから作成
			createAfterDeleting: boolean;
			selectedImplementAtrCode: number;
			selectRebuildAtrCode: number;
			checkCreateMethodAtrPersonalInfo: number;
			creationMethodCode: number;
			monthlyPatternCode: string;
			isConfirmedCreation: boolean;

			constructor() {
				let self = this;

				self.employeeId = '';
				self.implementAtr = ImplementAtr.GENERALLY_CREATED;
				self.reCreateAtr = ReCreateAtr.ALLCASE;
				self.processExecutionAtr = ProcessExecutionAtr.REBUILD;
				self.rebuildTargetAtr = ReBuildAtr.REBUILD_ALL;
				self.recreateConverter = false;
				self.recreateEmployeeOffWork = false;
				self.recreateDirectBouncer = false;
				self.recreateShortTermEmployee = false;
				self.recreateWorkTypeChange = false;
				self.resetWorkingHours = false;
				self.resetStartEndTime = false;
				self.resetMasterInfo = false;
				self.resetTimeAssignment = false;
				self.confirm = false;
				self.createMethodAtr = CreateMethodAtr.PERSONAL_INFO;
				self.patternCode = '02';
				self.holidayReflect = ReflectionMethod.Overwrite;
				self.patternStartDate = new Date();
				self.legalHolidayUseAtr = UseAtr.NOTUSE;
				self.legalHolidayWorkType = '';
				self.statutoryHolidayUseAtr = UseAtr.NOTUSE;
				self.statutoryHolidayWorkType = '';
				self.holidayUseAtr = UseAtr.NOTUSE;
				self.holidayWorkType = '';

				self.selectedImplementAtrCode = ImplementAtr.GENERALLY_CREATED;
				self.selectRebuildAtrCode = ReBuildAtr.REBUILD_ALL;
				self.overwriteConfirmedData = false;
				self.createAfterDeleting = false;
				self.checkCreateMethodAtrPersonalInfo = 0;
				self.creationMethodCode = 0;
				self.monthlyPatternCode = null;
				self.isConfirmedCreation = false;
			}
		}

		export class MonthlyPatternModel {
			code: string;
			name: string;
			company: string;

			constructor( code: string = null, name: string = null, company: string = null ) {
				this.code = code;
				this.name =( code !== null ) ? code + "　　" + name : name;
				this.company = company;
			}
		}

		export class RadioBoxModel {
			id: number;
			name: string;

			constructor( id: number, name: string ) {
				this.id = id;
				this.name = name;
			}
		}

		export class ListType {
			static EMPLOYMENT = 1;
			static Classification = 2;
			static JOB_TITLE = 3;
			static EMPLOYEE = 4;
		}

		export interface UnitModel {
			code: string;
			name?: string;
			affiliationName?: string;
			isAlreadySetting?: boolean;
		}

		export class SelectType {
			static SELECT_BY_SELECTED_CODE = 1;
			static SELECT_ALL = 2;
			static SELECT_FIRST_ITEM = 3;
			static NO_SELECT = 4;
		}

		export interface UnitAlreadySettingModel {
			code: string;
			isAlreadySetting: boolean;
		}

		export interface EmployeeSearchDto {
			employeeId: string;

			employeeCode: string;

			employeeName: string;

			workplaceCode: string;

			workplaceId: string;

			workplaceName: string;
		}

		export interface GroupOption {
			baseDate?: KnockoutObservable<Date>;
			// クイック検索タブ
			isQuickSearchTab: boolean;
			// 参照可能な社員すべて
			isAllReferableEmployee: boolean;
			//自分だけ
			isOnlyMe: boolean;
			//おなじ部門の社員
			isEmployeeOfWorkplace: boolean;
			//おなじ＋配下部門の社員
			isEmployeeWorkplaceFollow: boolean;
			// 詳細検索タブ
			isAdvancedSearchTab: boolean;
			//複数選択
			isMutipleCheck: boolean;

			//社員指定タイプ or 全社員タイプ
			isSelectAllEmployee: boolean;

			onSearchAllClicked:( data: EmployeeSearchDto[] ) => void;

			onSearchOnlyClicked:( data: EmployeeSearchDto ) => void;

			onSearchOfWorkplaceClicked:( data: EmployeeSearchDto[] ) => void;

			onSearchWorkplaceChildClicked:( data: EmployeeSearchDto[] ) => void;

			onApplyEmployee:( data: EmployeeSearchDto[] ) => void;
		}

		export class ListEmployeeIds {

			listEmployeeId: Array<string>;
			/** 異動者. */
			transfer: boolean;
			/** 休職休業者. */
			leaveOfAbsence: boolean;
			/** 短時間勤務者. */
			shortWorkingHours: boolean;
			/** 労働条件変更者. */
			changedWorkingConditions: boolean;
			startDate: string; //YYYY/MM/DD UTC
			endDate: string; //YYYY/MM/DD UTC

			constructor( employeeIds: Array<string>, transfer: boolean,
			              leaveOfAbsence: boolean, shortWorkingHours: boolean,
			              changedWorkingConditions: boolean, startDate: string, endDate: string ) {
				this.listEmployeeId = employeeIds;
				this.transfer = transfer;
				this.leaveOfAbsence = leaveOfAbsence;
				this.shortWorkingHours = shortWorkingHours;
				this.changedWorkingConditions = changedWorkingConditions;
				this.startDate = startDate;
				this.endDate = endDate;

			}
		}
	}
}