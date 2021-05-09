module nts.uk.at.view.kdl046.u.test {
	import setShare = nts.uk.ui.windows.setShared;
	// Import
	export module viewmodel {
		export class ScreenModel {
            options: any;
            treeGrid: any;
            componentName: KnockoutObservable<string> = ko.observable('workplace-group');

			multiple: KnockoutObservable<boolean> = ko.observable(false);
            showBaseDate: KnockoutObservable<boolean> = ko.observable(false);
			date: KnockoutObservable<string> = ko.observable(new Date().toISOString());
            unit: KnockoutObservable<number> = ko.observable(0);

            selectedWkpId: KnockoutObservable<string> = ko.observable(null);
			selectedWkpGroupId: KnockoutObservable<string> = ko.observable(null);

            selectedWkpIds: KnockoutObservableArray<string> = ko.observableArray([]);
            selectedWkpGroupIds: KnockoutObservableArray<string> = ko.observableArray([]);

            result: KnockoutObservable<string> = ko.observable('');

			constructor() {
				var self = this;
				self.initKCP004();
                self.initKCP011();

				self.multiple.subscribe(value => {
                    self.initKCP004();
					self.initKCP011();
				    self.componentName.valueHasMutated();
                });
                self.unit.subscribe(value => {
                    if (value == 1 && $("#workplace-group-pannel input.ntsSearchBox").width() == 0)
                        $("#workplace-group-pannel input.ntsSearchBox").css("width", "auto");
                });
			}

			initKCP004() {
			    const self = this;
                self.treeGrid = {
                    isMultipleUse: true,
                    isMultiSelect: self.multiple(),
                    treeType: 1,
                    selectedId: self.multiple() ? self.selectedWkpIds : self.selectedWkpId,
                    baseDate: self.date,
                    selectType: 4,
                    isShowSelectButton: false,
                    isDialog: false,
                    maxRows: 15,
                    tabindex: 1,
                    systemType: 2
                };

                $('#tree-grid').ntsTreeComponent(self.treeGrid);
            }

            initKCP011() {
				const self = this;
                self.options = {
                    currentIds: self.multiple() ? self.selectedWkpGroupIds : self.selectedWkpGroupId,
                    multiple: self.multiple(),
                    tabindex: 2,
                    isAlreadySetting: false,
                    showEmptyItem: false,
                    reloadData: ko.observable(''),
                    height: 373,
                    selectedMode: 0
                };
			}

			openDialog(): void {
				let self = this;
				let request: any = {
					unit: self.unit(),
                    baseDate: self.date(),
                    showBaseDate: self.showBaseDate(),
                    isMultiple: self.multiple(),
                    workplaceId: self.multiple() ? self.selectedWkpIds() : self.selectedWkpId(),
                    workplaceGroupId: self.multiple() ? self.selectedWkpGroupIds() : self.selectedWkpGroupId(),
				};
				
				console.log(request);

                setShare('dataShareDialog046', request);
                nts.uk.ui.windows.sub.modal("/view/kdl/046/a/index.xhtml").onClosed(() => {
                    let result = nts.uk.ui.windows.getShared('dataShareKDL046');
                    console.log(result);
                    // let show = '';
                    // for (const property in result) {
                    //    show = show + '  ' +`${property}: ${result[property]}`;
                    // }
                    // self.result(show);
                });
			}
		}
	}
}