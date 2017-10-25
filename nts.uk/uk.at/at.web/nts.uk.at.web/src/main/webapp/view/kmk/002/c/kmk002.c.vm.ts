module nts.uk.at.view.kmk002.c {
    import DailyAttendanceItemDto = service.model.DailyAttendanceItemDto;
    import AttendanceItemDto = nts.uk.at.view.kmk002.a.service.model.AttendanceItemDto;
    import ItemSelectionDto = nts.uk.at.view.kmk002.a.service.model.ItemSelectionDto;
    import ParamToC = nts.uk.at.view.kmk002.a.viewmodel.ParamToC;

    export module viewmodel {
        export class ScreenModel {

            lstDailyAttendanceItem: KnockoutObservableArray<DailyAttendanceItemDto>;
            selectCodeDailyAttendanceItem: KnockoutObservableArray<number>;
            checkSelectDailyAttendanceItem: KnockoutObservable<number>;
            checkMul: KnockoutObservable<boolean>;
            columns: any;
            columnsRight: any;
            lstSelectDailyAttendanceItem: KnockoutObservableArray<AttendanceItemDto>;
            formulaAtr: string;
            formulaName: string;

            constructor() {
                var self = this;
                self.checkSelectDailyAttendanceItem = ko.observable(0);
                self.lstDailyAttendanceItem = ko.observableArray([]);
                self.selectCodeDailyAttendanceItem = ko.observableArray([]);
                self.lstSelectDailyAttendanceItem = ko.observableArray([]);
                self.checkMul = ko.observable(false);

                // data source
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);
                self.columnsRight = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_58'), key: 'operatorText', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);

                // Get param from parent screen
                let param = nts.uk.ui.windows.getShared('paramToC');

                // Set param to view model.
                self.fromDto(param);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.findAllDailyAttendanceItem().done(function(data) {
                    self.lstDailyAttendanceItem(data);
                });
                dfd.resolve();
                return dfd.promise();
            }

            public submit(): void {
                let self = this;

                // set return value
                let dto = <ItemSelectionDto>{};
                dto.minusSegment = self.checkMul() == true ? 1 : 0;
                dto.attendanceItems = self.lstSelectDailyAttendanceItem();
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
             * add method add by click button ADD
             */
            private addMethodAdd(): void {
                var self = this;
                var updateData: DailyAttendanceItemDto[] = [];
                var selectData: AttendanceItemDto[] = self.lstSelectDailyAttendanceItem();
                for (var item of self.lstDailyAttendanceItem()) {
                    var selected: boolean = false;
                    for (var itemSelect of self.selectCodeDailyAttendanceItem()) {
                        if (item.attendanceItemId == itemSelect) {
                            selected = true;
                        }
                    }
                    if (!selected) {
                        updateData.push(item);
                    } else {
                        selectData.push(self.toSelectDto(item, AddSubOperator.ADD));
                    }
                }
                self.lstDailyAttendanceItem(updateData);
                self.lstSelectDailyAttendanceItem(selectData);
            }

            /**
             * add method sub by click button SUB
             */
            private addMethodSub(): void {
                var self = this;
                var updateData: DailyAttendanceItemDto[] = [];
                var selectData: AttendanceItemDto[] = self.lstSelectDailyAttendanceItem();
                for (var item of self.lstDailyAttendanceItem()) {
                    var selected: boolean = false;
                    for (var itemSelect of self.selectCodeDailyAttendanceItem()) {
                        if (item.attendanceItemId == itemSelect) {
                            selected = true;
                        }
                    }
                    if (!selected) {
                        updateData.push(item);
                    } else {
                        selectData.push(self.toSelectDto(item, AddSubOperator.SUBTRACT));
                    }
                }
                self.lstDailyAttendanceItem(updateData);
                self.lstSelectDailyAttendanceItem(selectData);
            }

            /**
             * function by click button back
             */
            private gobackSelectItem(): void {
                var self = this;
                var updateData: DailyAttendanceItemDto[] = self.lstDailyAttendanceItem();
                var selectData: AttendanceItemDto[] = [];
                for (var item of self.lstSelectDailyAttendanceItem()) {
                    if (item.attendanceItemId == self.checkSelectDailyAttendanceItem()) {
                        updateData.push(self.toBackDto(item));
                    } else {
                        selectData.push(item);
                    }
                }
                self.lstDailyAttendanceItem(updateData);
                self.selectCodeDailyAttendanceItem([]);
                self.lstSelectDailyAttendanceItem(selectData);
            }

            /**
             * Convert dto to view model.
             */
            private fromDto(dto: ParamToC): void {
                let self = this;
                self.formulaAtr = dto.formulaAtr;
                self.formulaName = dto.formulaName;
                self.lstSelectDailyAttendanceItem(dto.itemSelection.attendanceItems);
                self.checkMul(dto.itemSelection.minusSegment == 1 ? true : false);
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