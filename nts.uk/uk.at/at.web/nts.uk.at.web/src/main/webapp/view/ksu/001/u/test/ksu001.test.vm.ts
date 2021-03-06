module nts.uk.at.view.ksu001.u.test {
	import setShare = nts.uk.ui.windows.setShared;
	// Import
	export module viewmodel {
		export class ScreenModel {
			options: Option;
			currentScreen: any = null;
			enable: KnockoutObservable<boolean>;
			required: KnockoutObservable<boolean>;
			date: KnockoutObservable<string>;		
			dateValue: KnockoutObservable<any>;
			startDateString: KnockoutObservable<string>;
			endDateString: KnockoutObservable<string>;	
			workplaceGroupList: KnockoutObservable<any> = ko.observable([]);
			currentIds: KnockoutObservable<any> = ko.observable(null);
			currentCodes: KnockoutObservable<any> = ko.observable([]);
			currentNames: KnockoutObservable<any> = ko.observable([]);
			treeGrid: TreeComponentOption;
			selectedWorkplaceId: KnockoutObservable<string>;
			baseDate: KnockoutObservable<Date>;
			unit: string = '';
			workPlace: KnockoutObservable<boolean> = ko.observable(false);
			workPlaceGroup: KnockoutObservable<boolean> = ko.observable(false);
			isWorkPlace: KnockoutObservable<boolean> = ko.observable(true);
			isWorkPlaceGroup: KnockoutObservable<boolean> = ko.observable(true);
			check: KnockoutObservable<boolean> = ko.observable(true);
			check1: KnockoutObservable<boolean> = ko.observable(true);
			constructor() {
				var self = this;
				self.enable = ko.observable(true);
				self.required = ko.observable(true);
				self.baseDate = ko.observable(new Date());
				self.selectedWorkplaceId = ko.observable('');				
				self.date = ko.observable('2020/01/01');

				self.startDateString = ko.observable("2020/10/01");
				self.endDateString = ko.observable("2020/10/31");
				self.dateValue = ko.observable({});
				self.workPlace.subscribe((x) => {
					self.isWorkPlaceGroup(!x);
					self.check1(!x)
				});
				self.workPlaceGroup.subscribe((x) => {
					self.isWorkPlace(!x);
					self.check(!x)
				});

				self.startDateString.subscribe(function(value) {
					self.dateValue().startDate = value;
					self.dateValue.valueHasMutated();
				});

				self.endDateString.subscribe(function(value) {
					self.dateValue().endDate = value;
					self.dateValue.valueHasMutated();
				});
			
				self.treeGrid = {
					isMultipleUse: true,
					isMultiSelect: false,
					treeType: 1,
					selectedId: self.selectedWorkplaceId,
					baseDate: self.baseDate,
					selectType: 4,
					isShowSelectButton: false,
					isDialog: false,
					maxRows: 15,
					tabindex: 1,
					systemType: 2

				};

				$('#tree-grid').ntsTreeComponent(self.treeGrid);
				self.options = {
					itemList: self.workplaceGroupList,
					currentCodes: self.currentCodes,
					currentNames: self.currentNames,
					currentIds: self.currentIds,
					multiple: false,
					tabindex: 2,
					isAlreadySetting: false,
					showEmptyItem: false,
					reloadData: ko.observable(''),
					height: 373,
					selectedMode: 1
				};			
			}
			openDialog(): void {
				let self = this;
				let request: any = {};			

				if (self.workPlace()) {
					self.unit = '0';
				}
				if (self.workPlaceGroup()) {
					self.unit = '1';
				}

				request.unit = self.unit;
				if (self.unit === '1') {
					request.workplaceGroupId = self.currentIds();
				} else {
					request.workplaceId = self.selectedWorkplaceId();
				}

                if (self.unit == '' || self.unit == undefined ) {
                    alert('Please choose target is WorkPlace or WorkPlaceGroup ?');
                    
                    if (request.workplaceId == '' || request.workplaceId == undefined ) {
                        alert('Please choose WorkPlace');                        
                    }
                    if (request.workplaceGroupId == ''|| request.workplaceGroupId == undefined) {
                            alert('Please choose WorkPlaceGroup');                            
                    }
                    return;
                }
				
				// request.baseDate = moment(self.date()).format('YYYY/MM/DD');	
				request.startDate = self.dateValue().startDate.format('YYYY/MM/DD');
				request.endDate = self.dateValue().endDate.format('YYYY/MM/DD');
				setShare('dataShareDialogU', request);		
				self.currentScreen = nts.uk.ui.windows.sub.modal("/view/ksu/001/u/index.xhtml");
			}
			public startPage(): JQueryPromise<any> {
				let self = this,
					dfd = $.Deferred();

				dfd.resolve();
				return dfd.promise();
			}
		}
	}
}