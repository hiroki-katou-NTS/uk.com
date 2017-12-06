module nts.uk.at.view.kdl006.a {

    export module viewModel {

        import ConfirmClsStatus = service.model.ConfirmClsStatus;

        import CheckBoxItem = service.model.CheckBoxItem;

        import PersonInfo = service.model.PersonInfo;
        import Closure = service.model.Closure;
        import WorkplaceInfo = service.model.WorkplaceInfo;
        import WorkFixed = service.model.WorkFixed;
        import SaveWorkFixedCommand = service.model.SaveWorkFixedCommand;

        export class ScreenModel {

            // Data
            currentPersonName: string;
            listClosure: Closure[];
            listWorkplace: WorkplaceInfo[];
            listWorkFixed: WorkFixed[];
            listSaveWorkFixed: WorkFixed[];

            // UI 
            columnHeader1: CheckBoxItem;
            columnHeader2: CheckBoxItem;
            columnHeader3: CheckBoxItem;
            columnHeader4: CheckBoxItem;
            columnHeader5: CheckBoxItem;
            tableBody: string;

            constructor() {
                let _self = this;

                // Data
                _self.currentPersonName = null;
                _self.listClosure = [];
                _self.listWorkplace = [];
                _self.listWorkFixed = [];
                _self.listSaveWorkFixed = [];

                // UI
                _self.columnHeader1 = new CheckBoxItem();
                _self.columnHeader2 = new CheckBoxItem();
                _self.columnHeader3 = new CheckBoxItem();
                _self.columnHeader4 = new CheckBoxItem();
                _self.columnHeader5 = new CheckBoxItem();
                _self.tableBody = "";
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                // Load closure                
                nts.uk.ui.block.grayout();
                $.when(_self.findCurrentPersonName(), _self.loadClosure(), _self.loadWorkplaceInfo())
                    .done(() => {
                        _self.loadWorkFixed()
                            .then(() => {                               
                                // Build table
                                _self.buildTable();

                                // Enable resize
                                var currentScreen = nts.uk.ui.windows.getSelf();
                                $("#grid-list").resizable({
                                    minHeight: 380,
                                    handles: "s",
                                    alsoResize: "#grid-data-body",
                                    resize: function(event, ui) {
                                        // If table was not overflowed, add more rows 
                                        if ($('#grid-data-body').prop('scrollHeight') <= $('#grid-data-body').prop('clientHeight')) {
                                            _self.addRowImedially();
                                        }
                                        currentScreen.setHeight(ui.size.height + 140);
                                    }
                                });
                                
                                nts.uk.ui.block.clear();
                                dfd.resolve();
                            });
                    })
                    .fail((res: any) => {
                        nts.uk.ui.block.clear();
                        dfd.reject(res);
                    });

                return dfd.promise();
            }

            /**
             * Data: Load current person info
             */
            private findCurrentPersonName(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findCurrentPersonName()
                    .done((data: PersonInfo) => {
                        _self.currentPersonName = data.employeeName;
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }

            /**
             * Data: Load Closure
             */
            private loadClosure(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findCurrentClosure()
                    .done((data: Closure[]) => {
                        _self.listClosure = data;
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }

            /**
             * Data: Load WorkplaceInfo
             */
            private loadWorkplaceInfo(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                service.findWorkplaceInfo()
                    .done((data: WorkplaceInfo[]) => {
                        _self.listWorkplace = data;
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        dfd.reject(res);
                    });
                return dfd.promise();
            }

            /**
             * Data: Load WorkFixed and update into WorkplaceInfo
             */
            private loadWorkFixed(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                // Create WorkFixed list
                for (let workplace of _self.listWorkplace) {
                    for (let closure of _self.listClosure) {
                        _self.listWorkFixed.push(new WorkFixed(false, closure.closureId, workplace.workplaceId));
                    }
                }

                // Get WorkFixed info, map into current list
                service.findWorkFixedInfo(_self.listWorkFixed)
                    .done((data: WorkFixed[]) => {
                        _self.listWorkFixed = data;
                        let workplaceIndex = 0;
                        let totalClosure = _self.listClosure.length;
                        for (let workplace of _self.listWorkplace) {
                            let currentIndex = workplaceIndex * totalClosure;
                            let currentWorkFixed: WorkFixed[] = _.slice(_self.listWorkFixed, currentIndex, currentIndex + totalClosure);

                            if (!workplace.listWorkFixed) {
                                workplace.listWorkFixed = [];
                            }

                            for (let closure of _self.listClosure) {
                                let workFixed: WorkFixed = _.find(currentWorkFixed, (o) => { return o.closureId === closure.closureId; });
                                if (workFixed) {
                                    workFixed.isEdited = true;
                                    workplace.listWorkFixed.push(workFixed);
                                } else {
                                    // Add default WorkFixed
                                    let newItem: WorkFixed = new WorkFixed(false, closure.closureId, workplace.workplaceId);
                                    newItem.confirmClsStatus = ConfirmClsStatus.PENDING;
                                    newItem.processDate = closure.processingDate;
                                    workplace.listWorkFixed.push(newItem);
                                }
                            }
                            workplaceIndex++;
                        }
                        dfd.resolve();
                    });
                return dfd.promise();
            }

            /**
             * Table: Build grid table
             */
            private buildTable(): void {
                let _self = this;

                // Build header
                if (_self.listClosure[0]) {
                    _self.columnHeader1.updateData(false, true, _self.getHeader(_self.listClosure[0]));
                }
                if (_self.listClosure[1]) {
                    _self.columnHeader2.updateData(false, true, _self.getHeader(_self.listClosure[1]));
                }
                if (_self.listClosure[2]) {
                    _self.columnHeader3.updateData(false, true, _self.getHeader(_self.listClosure[2]));
                }
                if (_self.listClosure[3]) {
                    _self.columnHeader4.updateData(false, true, _self.getHeader(_self.listClosure[3]));
                }
                if (_self.listClosure[4]) {
                    _self.columnHeader5.updateData(false, true, _self.getHeader(_self.listClosure[4]));
                }

                // Build body
                let workplaceIndex = 0;
                for (let item of _self.listWorkplace) {
                    _self.addRow(workplaceIndex, item);
                    workplaceIndex++;
                }

                // Add empty row if row < 10
                while (workplaceIndex < 10) {
                    _self.addEmptyRow();
                    workplaceIndex++;
                }

                $('#grid-data-body').html(_self.tableBody);
                _self.tableBody = "";

                // Update header binding
                _self.setHeaderBinding();
            }

            /**
             * Table: Add row
             */
            private addRow(index: number, item: WorkplaceInfo): void {
                let _self = this;
                if (!item.workplaceCode || !item.workplaceName) {
                    return;
                }
                if (!item.listWorkFixed) {
                    item.listWorkFixed = [];
                }
                _self.tableBody += '<tr>';
                _self.tableBody += '<td class="header-workplace">' + _self.getRowTitle(item) + '</td>';
                _self.tableBody += '<td class="header-closure">' + _self.getCell(index, 0, _self.listClosure[0], item.listWorkFixed[0]) + '</td>';
                _self.tableBody += '<td class="header-closure">' + _self.getCell(index, 1, _self.listClosure[1], item.listWorkFixed[1]) + '</td>';
                _self.tableBody += '<td class="header-closure">' + _self.getCell(index, 2, _self.listClosure[2], item.listWorkFixed[2]) + '</td>';
                _self.tableBody += '<td class="header-closure">' + _self.getCell(index, 3, _self.listClosure[3], item.listWorkFixed[3]) + '</td>';
                _self.tableBody += '<td class="header-closure">' + _self.getCell(index, 4, _self.listClosure[4], item.listWorkFixed[4]) + '</td>';
                _self.tableBody += '</tr>';
            }

            /**
             * Table: Add empty row
             */
            private addEmptyRow(): void {
                let _self = this;
                _self.tableBody += '<tr>';
                _self.tableBody += '<td class="header-workplace"></td>';
                _self.tableBody += '<td class="header-closure"></td>';
                _self.tableBody += '<td class="header-closure"></td>';
                _self.tableBody += '<td class="header-closure"></td>';
                _self.tableBody += '<td class="header-closure"></td>';
                _self.tableBody += '<td class="header-closure"></td>';
                _self.tableBody += '</tr>';
            }

            /**
             * Table: Add row imedially
             */
            private addRowImedially(): void {
                let row: string = '';
                // Add 5 empty rows at a time
                for (let i = 0; i < 5; i++) {
                    row += '<tr><td class="header-workplace"></td><td class="header-closure"></td>';
                    row += '<td class="header-closure"></td><td class="header-closure"></td>';
                    row += '<td class="header-closure"></td><td class="header-closure"></td></tr>';
                }
                $('#grid-data-body').append(row);
            }

            /**
             * Table: Remove row imedially
             */
            private removeRowImedially(): void {
                $('#grid-data-body tr:last-child').remove();
            }

            /**
             * Data: Save WorkFixed info
             */
            public save(): void {
                let _self = this;

                nts.uk.ui.block.grayout();
                service.saveWorkFixedInfo(_self.buildSaveCommand())
                    .done((data: any) => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    })
                    .fail((res: any) => {
                        // Nothing happen
                    })
                    .always(() => {
                        nts.uk.ui.block.clear();
                    });
            }

            /**
             * Data: Build save command
             */
            private buildSaveCommand(): SaveWorkFixedCommand[] {
                let _self = this;
                let result: SaveWorkFixedCommand[] = [];

                for (let item of _self.listSaveWorkFixed) {
                    result.push(new SaveWorkFixedCommand(item.isEdited, item.closureId, item.wkpId, item.confirmClsStatus, item.processDate));
                }
                return result;
            }

            /**
             * Close this dialog
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }

            /**
             * UI: Get header
             */
            private getHeader(item: Closure): string {
                if (!item) {
                    return '';
                }
                return item.closureName + "<br>" + item.startDate.slice(5, 10) + ' ~ ' + item.endDate.slice(5, 10);
            }

            /**
             * UI: Get row title
             */
            private getRowTitle(item: WorkplaceInfo): string {
                if (!item.workplaceCode || !item.workplaceName) {
                    return '';
                }
                return item.workplaceCode + ' ' + item.workplaceName;
            }

            /**
             * UI: Get cell text
             */
            private getCell(rowIndex: number, columnIndex: number, currentClosure: Closure, item: WorkFixed): string {
                let _self = this;

                if (!currentClosure || !item) {
                    return '';
                }
                item.checked = item.confirmClsStatus === ConfirmClsStatus.CONFIRM ? ko.observable(true) : ko.observable(false);
                item.text = ko.observable(item.employeeName);

                // Handler when cell/item got checked/unchecked
                item.checked.subscribe((newValue) => {
                    item.confirmClsStatus = newValue ? ConfirmClsStatus.CONFIRM : ConfirmClsStatus.PENDING;
                    item.processDate = currentClosure.processingDate;
                    item.employeeName = _self.currentPersonName;
                    item.text(_self.currentPersonName);

                    // Add item into save list 
                    let index = _.findIndex(_self.listSaveWorkFixed, { closureId: item.closureId, wkpId: item.wkpId });
                    if (index === -1) {
                        // Add new item
                        _self.listSaveWorkFixed.push(item);
                    } else {
                        // Replace exist item
                        _self.listSaveWorkFixed.splice(index, 1, item);
                    }
                });

                // Add unchecked item into save list
                if (!item.isEdited && item.confirmClsStatus === ConfirmClsStatus.PENDING) {
                    _self.listSaveWorkFixed.push(item);
                }

                let htmlParse: string = '<div class="header-closure" style="display: inline-flex;"><div data-bind="ntsCheckBox: { checked: listWorkplace[' + rowIndex + '].listWorkFixed[' + columnIndex + '].checked }"></div>';
                htmlParse += '<span class="limited-label" data-bind="visible: listWorkplace[' + rowIndex + '].listWorkFixed[' + columnIndex + '].checked,';
                htmlParse += ' text: listWorkplace[' + rowIndex + '].listWorkFixed[' + columnIndex + '].text"></span></div>';
                
                return htmlParse;
            }

            /**
             * UI: Set header binding
             */
            private setHeaderBinding(): void {
                let _self = this;

                _self.columnHeader1.checked = ko.computed({
                    read: () => _self.computedHeaderCheckbox(0),
                    write: (newValue) => _self.checkAll(newValue, 0)
                });
                _self.columnHeader2.checked = ko.computed({
                    read: () => _self.computedHeaderCheckbox(1),
                    write: (newValue) => _self.checkAll(newValue, 1)
                });
                _self.columnHeader3.checked = ko.computed({
                    read: () => _self.computedHeaderCheckbox(2),
                    write: (newValue) => _self.checkAll(newValue, 2)
                });
                _self.columnHeader4.checked = ko.computed({
                    read: () => _self.computedHeaderCheckbox(3),
                    write: (newValue) => _self.checkAll(newValue, 3)
                });
                _self.columnHeader5.checked = ko.computed({
                    read: () => _self.computedHeaderCheckbox(4),
                    write: (newValue) => _self.checkAll(newValue, 4)
                });
            }

            /**
             * Ko.computed - Read: calculated Header Checkbox
             * columnIndex: 0 - 1 - 2 - 3 - 4
             */
            private computedHeaderCheckbox(columnIndex: number): boolean {
                let _self = this;

                let currentItem = _self.listWorkFixed[columnIndex];
                let listClosureLength = _self.listClosure.length;
                while (currentItem) {
                    if (!currentItem.checked()) {
                        return false;
                    }
                    columnIndex += listClosureLength;
                    currentItem = _self.listWorkFixed[columnIndex];
                }
                return true;
            }

            /**
             * Ko.computed - Write: Check all
             * columnIndex: 0 - 1 - 2 - 3 - 4
             */
            private checkAll(check: boolean, columnIndex: number): void {
                let _self = this;

                // Set all check value to true
                let currentItem = _self.listWorkFixed[columnIndex];
                let listClosureLength = _self.listClosure.length;
                while (currentItem) {
                    if (currentItem.checked) {
                        currentItem.checked(check);
                    }
                    columnIndex += listClosureLength;
                    currentItem = _self.listWorkFixed[columnIndex];
                }
            }
                
            private refreshTable(): void {
                let _self = this;
                let $tableGrid: any = $("#grid-data-body");
                ko.cleanNode($tableGrid[0]);
                ko.applyBindings(_self, $tableGrid[0]);
            }
        }
    }
}