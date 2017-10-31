module nts.uk.at.view.kmk002.c {
    import DailyAttendanceItemDto = service.model.AttendanceItemDto;
    import AttdItemLinkRequest = service.model.AttdItemLinkRequest;
    import AttendanceItemDto = nts.uk.at.view.kmk002.a.service.model.AttendanceItemDto;
    import ItemSelectionDto = nts.uk.at.view.kmk002.a.service.model.ItemSelectionDto;
    import ParamToC = nts.uk.at.view.kmk002.a.viewmodel.ParamToC;

    export module viewmodel {
        export class ScreenModel {

            leftItems: KnockoutObservableArray<DailyAttendanceItemDto>;
            rightItems: KnockoutObservableArray<AttendanceItemDto>;
            selectedLeftItem: KnockoutObservableArray<number>;
            selectedRightItem: KnockoutObservable<number>;
            minusSegment: KnockoutObservable<boolean>;
            columnsLeft: any;
            columnsRight: any;
            formulaAtr: string;
            formulaName: string;

            constructor() {
                let self = this;
                self.selectedRightItem = ko.observable(null);
                self.leftItems = ko.observableArray([]);
                self.selectedLeftItem = ko.observableArray([]);
                self.rightItems = ko.observableArray([]);
                self.minusSegment = ko.observable(false);

                // data source
                self.columnsLeft = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);
                self.columnsRight = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_58'), key: 'operatorText', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);

            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // Get param from parent screen
                let param: ParamToC = nts.uk.ui.windows.getShared('paramToC');

                // Set param to view model.
                self.fromDto(param);

                // load attendance items
                self.loadAttendanceItems(param).done(() => dfd.resolve());

                return dfd.promise();
            }

            /**
             * Load attendance items.
             */
            private loadAttendanceItems(param: ParamToC): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // create request object
                let request = <AttdItemLinkRequest>{};
                request.anyItemNos = param.selectableOptItemNos;
                request.formulaAtr = param.formulaAtr;
                request.performanceAtr = param.performanceAtr;

                // get list attendance item
                if (param.performanceAtr == 1) {
                    // get list daily attendance item
                    service.findDailyAttdItem(request).done(data => {
                        self.leftItems(data);

                        // remove selected items from left table.
                        self.removeItemFromLeftTable(param.itemSelection.attendanceItems);

                        dfd.resolve();
                    });
                } else {
                    // get list monthly attendance item
                    service.findMonthlyAttdItem(request).done(data => {
                        self.leftItems(data);

                        // remove selected items from left table.
                        self.removeItemFromLeftTable(param.itemSelection.attendanceItems);

                        dfd.resolve();
                    });
                }
                return dfd.promise();
            }

            /**
             * Submit and close dialog.
             */
            public submit(): void {
                let self = this;

                // set return value
                let dto = <ItemSelectionDto>{};
                dto.minusSegment = self.minusSegment() == true ? 1 : 0;
                dto.attendanceItems = self.rightItems();
                nts.uk.ui.windows.setShared('returnFromC', dto);

                // close dialog.
                self.close();
            }

            /**
             * Close dialog.
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * Remove selected items from left table.
             */
            private removeItemFromLeftTable(listItem: Array<AttendanceItemDto>): void {
                let self = this;
                _.forEach(listItem, item => {
                    self.leftItems.remove(i => i.attendanceItemId == item.attendanceItemId);
                });
            }

            /**
             * Event on click add button
             */
            private onClickAdd(): void {
                let self = this;
                if (self.selectedLeftItem()) {
                    _.forEach(self.selectedLeftItem(), id => {
                        // get selected item by selected id
                        let item = _.find(self.leftItems(), item => item.attendanceItemId == id);

                        // push item to right table
                        self.rightItems.push(self.toSelectDto(item, AddSubOperator.ADD));

                        // remove item from left table
                        self.leftItems.remove(item);
                    });
                    // sort right table
                    self.sortRightTable();

                    // clear selected item
                    self.selectedLeftItem([]);
                }
            }

            /**
             * Event on click subtract button
             */
            private onClickSubtract(): void {
                let self = this;
                if (self.selectedLeftItem()) {
                    _.forEach(self.selectedLeftItem(), id => {
                        // get selected item by selected id
                        let item = _.find(self.leftItems(), item => item.attendanceItemId == id);

                        // push item to right table
                        self.rightItems.push(self.toSelectDto(item, AddSubOperator.SUBTRACT));

                        // remove item from left table
                        self.leftItems.remove(item);
                    });
                    // sort right table
                    self.sortRightTable();

                    // clear selected item
                    self.selectedLeftItem([]);
                }
            }

            /**
             * Event on click back button
             */
            private onClickBack(): void {
                let self = this;
                if (self.selectedRightItem()) {
                    let item = _.find(self.rightItems(),
                        item => item.attendanceItemId == self.selectedRightItem());

                    // push item to left table
                    self.leftItems.push(self.toBackDto(item));

                    // remove item from right table
                    self.rightItems.remove(item);

                    // sort left table
                    self.sortLeftTable();

                    // clear selected item.
                    self.selectedRightItem(null);
                }
            }

            /**
             * Sort list attendance item by attendance item Id.
             */
            private sortLeftTable(): void {
                let self = this;
                let sortedList = _.sortBy(self.leftItems(), item => item.attendanceItemId);
                self.leftItems(sortedList);
            }

            /**
             * Sort right table.
             */
            private sortRightTable(): void {
                let self = this;
                let sortedList = _.sortBy(self.rightItems(), item => item.attendanceItemId);
                self.rightItems(sortedList);
            }

            /**
             * Convert dto to view model.
             */
            private fromDto(dto: ParamToC): void {
                let self = this;
                self.formulaAtr = dto.formulaAtrName;
                self.formulaName = dto.formulaName;
                self.rightItems(dto.itemSelection.attendanceItems);
                self.minusSegment(dto.itemSelection.minusSegment == 1 ? true : false);
            }

            /**
             * to select data object
             */
            private toSelectDto(data: DailyAttendanceItemDto, method: number): AttendanceItemDto {
                var operatorText: string = '';
                if (method == AddSubOperator.ADD) {
                    operatorText = nts.uk.resource.getText('KMK002_56');
                }
                else {
                    operatorText = nts.uk.resource.getText('KMK002_57');
                }
                var dto: AttendanceItemDto = {
                    operator: method,
                    operatorText: operatorText,
                    attendanceItemId: data.attendanceItemId,
                    attendanceItemName: data.attendanceItemName
                };
                return dto;
            }

            /**
             * to back data object
             */
            private toBackDto(data: AttendanceItemDto): DailyAttendanceItemDto {
                var dto: DailyAttendanceItemDto = {
                    attendanceItemId: data.attendanceItemId,
                    attendanceItemName: data.attendanceItemName
                };
                return dto;
            }

        }

        export enum AddSubOperator {
            ADD = 0,
            SUBTRACT = 1
        }

    }
}