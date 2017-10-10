module nts.uk.at.view.kmk002.c {
    import DailyAttendanceItemDto = service.model.DailyAttendanceItemDto;
    import SelectDailyAttendanceItemDto = service.model.SelectDailyAttendanceItemDto;
    export module viewmodel {
        export class ScreenModel {

            lstDailyAttendanceItem: KnockoutObservableArray<DailyAttendanceItemDto>;
            selectCodeDailyAttendanceItem: KnockoutObservableArray<number>;
            checkSelectDailyAttendanceItem: KnockoutObservable<number>;
            checkMul: KnockoutObservable<boolean>;
            columns: any;
            columnsRight: any;
            lstSelectDailyAttendanceItem: KnockoutObservableArray<SelectDailyAttendanceItemDto>;
            formulaAtr: string;
            formulaName: string;

            constructor() {
                var self = this;
                let param = nts.uk.ui.windows.getShared('paramToC');
                self.formulaAtr = param.formulaAtr;
                self.formulaName = param.formulaName;
                self.checkSelectDailyAttendanceItem = ko.observable(0);
                self.lstDailyAttendanceItem = ko.observableArray([]);
                self.selectCodeDailyAttendanceItem = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);
                self.columnsRight = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK002_58'), key: 'operatorText', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_7'), key: 'attendanceItemId', width: 50 },
                    { headerText: nts.uk.resource.getText('KMK002_8'), key: 'attendanceItemName', width: 100 }
                ]);
                self.lstSelectDailyAttendanceItem = ko.observableArray([]);
                self.checkMul = ko.observable(true);
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
                var selectData: SelectDailyAttendanceItemDto[] = self.lstSelectDailyAttendanceItem();
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
                var selectData: SelectDailyAttendanceItemDto[] = self.lstSelectDailyAttendanceItem();
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
                var selectData: SelectDailyAttendanceItemDto[] = [];
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
             * to select data object
             */
            private toSelectDto(data: DailyAttendanceItemDto, method: number): SelectDailyAttendanceItemDto {
                var operatorText: string = '';
                if (method == AddSubOperator.ADD) {
                    operatorText = nts.uk.resource.getText('KMK002_56');
                }
                else {
                    operatorText = nts.uk.resource.getText('KMK002_57');
                }
                var dto: SelectDailyAttendanceItemDto = {
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
            private toBackDto(data: SelectDailyAttendanceItemDto): DailyAttendanceItemDto {
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